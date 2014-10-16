package controllers

import com.laplacian.luxuryakka.core.response.{GlobalMessagesRestResponse, MessagesRestResponse, RestResponse}
import org.springframework.stereotype
import org.springframework.beans.factory.annotation.Autowired
import com.laplacian.luxuryakka.module.user.service.domain.UserDomainService
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.json.JsError
import play.api.mvc.{Controller, Action}
import com.laplacian.luxuryakka.core.authentication.Credentials
import com.laplacian.luxuryakka.core.jwt.ResponseToken

@stereotype.Controller
class AuthenticationController @Autowired
(
  private val userDomainService     : UserDomainService,
  private val authenticationService : AuthenticationService
  ) extends Controller
{
  Asserts.argumentIsNotNull(userDomainService)
  Asserts.argumentIsNotNull(authenticationService)

  def authenticate = Action(parse.json)
  {
    implicit request =>

      request.body.validate[Credentials].map {
        case credentials: Credentials =>
        {
          authenticationService.authenticate(credentials.username, credentials.password).map{ token =>
            Ok(RestResponse.data(token))
          }.getOrElse(Unauthorized("Bad username or password"))
        }
      }.recoverTotal
      {
        error =>
          BadRequest(Helper.jsErrorToGlobalMessagesErrorResponse(error).json)
      }
  }

  def refreshToken = Action(parse.json)
  {
    implicit request =>

      request.body.validate[ResponseToken].map {
        case authenticationToken: ResponseToken =>
        {
          authenticationService.refreshToken(authenticationToken.token).map{ token =>
            Ok(RestResponse.data(token))
          }.getOrElse(Unauthorized("Bad username or password"))
        }
      }.recoverTotal
      {
        error => BadRequest(Helper.jsErrorToGlobalMessagesErrorResponse(error).json)
      }
  }

  private object Helper
  {
    def jsErrorToGlobalMessagesErrorResponse(error: JsError) =
    {
      val messagesResponse = MessagesRestResponse(
        global = Some(
          GlobalMessagesRestResponse(
            errors = error.errors.map(_._2).flatten.map(_.message).toList
          )
        )
      )
      RestResponse.messages(messagesResponse)
    }
  }
}
