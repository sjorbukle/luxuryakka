package controllers.core

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.response.{ResponseTools, RestResponse}
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.module.user.domain.UserDetailsEntity
import play.api.mvc._

import scala.concurrent.Future

abstract class SecuredController
(
  implicit private val authenticationService : AuthenticationService
) extends Controller
{
  Asserts.argumentIsNotNull(authenticationService)

  private final val INVALID_TOKEN_ERROR = "Invalid authentication token"
  private final val MISSING_TOKEN_ERROR = "Missing authentication token"
  private final val AUTH_TOKEN_NOT_FOUND_ERROR = "Authorization token not found in secured endpoint"

  def AuthenticatedAction(block: Request[AnyContent] => Future[Result]): Action[AnyContent] =
  {
    AuthenticatedAction(parse.anyContent)(block)
  }

  def AuthenticatedAction[A](bodyParser: BodyParser[A])(block: Request[A] => Future[Result]): Action[A] = {
    Action.async(bodyParser) {
      request =>
        request.headers.get(AUTHORIZATION).map(token => {
          val validationResult = authenticationService.validateToken(token)
          if (!validationResult)
            Future.successful(
              Unauthorized(ResponseTools.errorToRestResponse(INVALID_TOKEN_ERROR).json)
            )
          else block(request)
        }).getOrElse(
            Future.successful(
              Unauthorized(ResponseTools.errorToRestResponse(MISSING_TOKEN_ERROR).json)
          ))
    }
  }

  implicit def userFromSecuredRequest(implicit request : Request[AnyContent]) : UserDetailsEntity =
  {
    Asserts.argumentIsNotNull(request)

    val token = request.headers.get(AUTHORIZATION)
      .getOrElse(throw new IllegalStateException(AUTH_TOKEN_NOT_FOUND_ERROR))

    this.authenticationService.getUserFromToken(token)
  }
}
