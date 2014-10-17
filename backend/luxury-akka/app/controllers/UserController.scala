package controllers

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.messages.Messages
import com.laplacian.luxuryakka.core.response.{ResponseTools, RestResponse}
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.module.user.converter.UserCreateModelToUserCreateConverter
import com.laplacian.luxuryakka.module.user.domain.{UserDetailsEntity, UserCreateEntity}
import com.laplacian.luxuryakka.module.user.model.UserCreateModel
import com.laplacian.luxuryakka.module.user.service.domain.UserDomainService
import com.laplacian.luxuryakka.module.user.validation.UserCreateValidator
import controllers.core.SecuredController
import org.springframework.stereotype
import org.springframework.beans.factory.annotation.Autowired
import play.api.mvc.Action
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@stereotype.Controller
class UserController @Autowired
(
  private val userDomainService   : UserDomainService,
  private val userCreateValidator : UserCreateValidator
)
(
  implicit private val authenticationService: AuthenticationService
) extends SecuredController
{
  Asserts.argumentIsNotNull(userDomainService)
  Asserts.argumentIsNotNull(userCreateValidator)
  Asserts.argumentIsNotNull(authenticationService)

  def read(id: Long) = AuthenticatedAction {
    request =>
      val userCandidate = this.userDomainService.tryGetById(id)
      if(!userCandidate.isDefined) {
        Future(NotFound(ResponseTools.errorToRestResponse("User with this id does not exist.").json))
      }
      else {
        val userForResponse = userCandidate.get.copy(password = "n/a")
        Future(Ok(ResponseTools.data(userForResponse).json))
      }
  }

  def create = Action.async(parse.json) {
    implicit request =>
      request.body.validate[UserCreateModel].map {
        case userCreateModel: UserCreateModel =>
          val validationResult = this.userCreateValidator.validate(userCreateModel)
          if(validationResult.isValid) {

            val userCreateEntity = UserCreateModelToUserCreateConverter.convert(validationResult.validatedItem)
            val generatedId = this.userDomainService.create(userCreateEntity)
            val createdUser = this.userDomainService.getById(generatedId.id)

            Future.successful(Ok(ResponseTools.of(createdUser, Some(validationResult.messages)).json))
          } else {
            Future.successful(BadRequest(validationResult.errorsRestResponse.json))
          }
      }.recoverTotal {
        error =>
          Future.successful(
            BadRequest(ResponseTools.errorToRestResponse(error.errors.map(_._2).flatten.map(_.message).head).json)
          )
      }
  }
}
