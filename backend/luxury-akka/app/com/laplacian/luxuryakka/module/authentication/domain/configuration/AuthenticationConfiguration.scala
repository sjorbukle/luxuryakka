package com.laplacian.luxuryakka.module.authentication.domain.configuration

import com.laplacian.luxuryakka.core.Asserts

class AuthenticationConfiguration
(
  final val secret            : String,
  final val tokenHoursToLive  : Int
)
{
  Asserts.argumentIsNotNullNorEmpty(secret)
  Asserts.argumentIsNotNull(tokenHoursToLive)
  Asserts.argumentIsTrue(tokenHoursToLive > 0)
}
