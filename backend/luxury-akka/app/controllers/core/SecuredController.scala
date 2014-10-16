package controllers.core

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.module.user.domain.UserDetailsEntity
import play.api.http.HeaderNames._
import play.api.mvc._

import scala.concurrent.Future

abstract class SecuredController
(
  implicit private val authenticationService : AuthenticationService
) extends Controller
{
  Asserts.argumentIsNotNull(authenticationService)

  private final val INVALID_TOKEN = "Invalid authentication token"
  private final val MISSING_TOKEN = "Missing authentication token"

  object AuthenticatedAction extends ActionBuilder[Request] {
    def invokeBlock[A](request: Request[A], block: (Request[A]) => Future[Result]) = {

      request.headers.get(AUTHORIZATION).map(token => {
        val validationResult = authenticationService.validateToken(token)
        if (!validationResult) Future.successful(Unauthorized(INVALID_TOKEN))
        else                   block(request)
      }).getOrElse(Future.successful(Unauthorized(MISSING_TOKEN)))
    }
  }
}

object SecuredController
{
  implicit def userFromSecuredRequest(implicit request : Request[AnyContent], authenticationService : AuthenticationService) : UserDetailsEntity =
  {
    Asserts.argumentIsNotNull(request)
    Asserts.argumentIsNotNull(authenticationService)

    val token = request.headers.get(AUTHORIZATION)
      .getOrElse(throw new IllegalStateException("Authorization token not found in secured endpoint"))

    authenticationService.getUserFromToken(token)
  }
}
