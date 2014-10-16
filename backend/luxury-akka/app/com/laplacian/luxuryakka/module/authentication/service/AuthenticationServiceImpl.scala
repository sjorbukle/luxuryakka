package com.laplacian.luxuryakka.module.authentication.service

import com.laplacian.luxuryakka.core.utils.DateUtils
import com.laplacian.luxuryakka.module.user.service.domain.UserDomainService
import com.laplacian.luxuryakka.core.Asserts
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired
import com.laplacian.luxuryakka.module.authentication.domain.configuration.AuthenticationConfiguration
import com.laplacian.luxuryakka.core.jwt.{ResponseToken, JwtUtil, TokenPayload, JwtSecret}
import com.laplacian.luxuryakka.module.user.domain.{UserDetailsEntity, UserCreateEntity}

@Service
@Transactional
class AuthenticationServiceImpl @Autowired
(
  userDomainService           : UserDomainService,
  authenticationConfiguration : AuthenticationConfiguration
) extends AuthenticationService
{
  Asserts.argumentIsNotNull(userDomainService)
  Asserts.argumentIsNotNull(authenticationConfiguration)

  implicit private final val SECRET = JwtSecret(authenticationConfiguration.secret)

  
  override def authenticate(username: String, password: String): Option[ResponseToken] =
  {
    Asserts.argumentIsNotNull(username)
    Asserts.argumentIsNotNull(password)

    this.userDomainService.tryGetByUsername(username).filter(_.password == password).map(u => createAuthenticationToken(u))
  }

  override def validateToken(token: String): Boolean =
  {
    Asserts.argumentIsNotNull(token)

    JwtUtil.getPayloadIfValidToken[TokenPayload](token).filter(_.expiration.isAfterNow).isDefined
  }

  override def refreshToken(token : String) : Option[ResponseToken] =
  {
    Asserts.argumentIsNotNullNorEmpty(token)

    JwtUtil.getPayloadIfValidToken[TokenPayload](token).map(payload =>
      this.userDomainService.getById(payload.userId)
    ).map(u => createAuthenticationToken(u))
  }

  private def createAuthenticationToken(user : UserDetailsEntity) : ResponseToken =
  {
    Asserts.argumentIsNotNull(user)

    val tokenPayload = TokenPayload(
      userId      = user.id,
      username    = user.username,
      expiration  = DateUtils.nowDateTimeUTC.plusHours(this.authenticationConfiguration.tokenHoursToLive))

    ResponseToken(JwtUtil.signJwtPayload(tokenPayload))
  }

  def getUserFromToken(token : String) : UserDetailsEntity =
  {
    Asserts.argumentIsNotNullNorEmpty(token)

    val userId = JwtUtil.getPayloadIfValidToken[TokenPayload](token).get.userId
    this.userDomainService.getById(userId)
  }
}
