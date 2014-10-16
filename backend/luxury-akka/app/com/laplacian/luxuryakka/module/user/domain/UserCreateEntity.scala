package com.laplacian.luxuryakka.module.user.domain

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.json.Json

case class UserCreateEntity
(
  firstName : String,
  lastName  : String,
  email     : String,
  username  : String,
  password  : String
)
{
  Asserts.argumentIsNotNull(firstName)
  Asserts.argumentIsNotNull(lastName)
  Asserts.argumentIsNotNull(email)
  Asserts.argumentIsNotNull(username)
  Asserts.argumentIsNotNull(password)
}

object UserCreateEntity
{
  implicit val jsonFormat = Json.format[UserCreateEntity]
}
