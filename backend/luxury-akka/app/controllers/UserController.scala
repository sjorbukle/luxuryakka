package controllers

import akka.actor.{ActorRef, ActorSystem}
import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.response.ResponseTools
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.module.log.action.actor.ActionLogCreateMsg
import com.laplacian.luxuryakka.module.log.action.domain.{ActionType, ActionDomainType, ActionLogEntity}
import com.laplacian.luxuryakka.module.user.domain.{UserDetailsEntity, UserCreateEntity}
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
  private val luxuryAkkaActorSystem : ActorSystem,
  private val userDomainService     : UserDomainService,
  private val userCreateValidator   : UserCreateValidator
)
(
  implicit private val authenticationService: AuthenticationService
) extends SecuredController
{
  Asserts.argumentIsNotNull(luxuryAkkaActorSystem)
  Asserts.argumentIsNotNull(userDomainService)
  Asserts.argumentIsNotNull(userCreateValidator)
  Asserts.argumentIsNotNull(authenticationService)

  private final val actionLogActorRouter = luxuryAkkaActorSystem.actorSelection("user/actionLogActorRouter")

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

  def create = MutateJsonAction[UserCreateEntity](userCreateValidator) {
    (request, validationResult) =>
      val generatedId = this.userDomainService.create(validationResult.validatedItem)
      val createdUser = this.userDomainService.getById(generatedId.id)

      val userCreatedAction = ActionLogEntity.of[UserDetailsEntity, UserDetailsEntity](
          userId      = None,
          domainType  = ActionDomainType.USER,
          domainId    = createdUser.id,
          actionType  = ActionType.CREATED,
          before      = None,
          after       = Some(createdUser)
      )
      actionLogActorRouter.tell(ActionLogCreateMsg(userCreatedAction), ActorRef.noSender)

      Future.successful(Ok(ResponseTools.of(createdUser, Some(validationResult.messages)).json))
  }
}
