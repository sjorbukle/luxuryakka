package com.laplacian.luxuryakka.module.user.domain

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.json.Json

case class UserDetailsEntity
(
  id        : Long,
  firstName : String,
  lastName  : String,
  username  : String,
  email     : String,
  password  : String
)
{
  Asserts.argumentIsNotNull(firstName)
  Asserts.argumentIsNotNull(lastName)
  Asserts.argumentIsNotNull(email)
  Asserts.argumentIsNotNull(username)
  Asserts.argumentIsNotNull(password)
}

object UserDetailsEntity
{
  implicit val jsonFormat = Json.format[UserDetailsEntity]
}