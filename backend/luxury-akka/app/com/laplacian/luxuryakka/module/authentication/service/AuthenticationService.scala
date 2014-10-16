package com.laplacian.luxuryakka.module.authentication.service

import com.laplacian.luxuryakka.core.jwt.ResponseToken
import com.laplacian.luxuryakka.module.user.domain.UserDetailsEntity

trait AuthenticationService
{
  def authenticate(username: String, password: String): Option[ResponseToken]
  def refreshToken(token : String) : Option[ResponseToken]
  def validateToken(token: String): Boolean
  def getUserFromToken(token : String) : UserDetailsEntity
}
