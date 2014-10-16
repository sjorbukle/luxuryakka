package controllers

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.response.RestResponse
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.module.user.service.domain.UserDomainService
import com.laplacian.luxuryakka.module.user.validation.UserCreateValidator
import controllers.core.SecuredController
import org.springframework.stereotype
import org.springframework.beans.factory.annotation.Autowired
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
      if(!userCandidate.isDefined)
      {
        Future(NotFound(RestResponse.errorToRestResponse("User with this id does not exist.").json))
      }
      else
      {
        val userForResponse = userCandidate.get.copy(password = "n/a")
        Future(Ok(RestResponse.data(userForResponse).json))
      }
  }
}
