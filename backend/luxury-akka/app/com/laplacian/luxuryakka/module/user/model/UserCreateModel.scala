package com.laplacian.luxuryakka.module.user.model

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.Field
import play.api.libs.json.{JsValue, Json}

case class UserCreateModel
(
  id        : Option[JsValue],
  firstName : Option[JsValue],
  lastName  : Option[JsValue],
  email     : Option[JsValue],
  username  : Option[JsValue],
  password  : Option[JsValue]
)
{
  Asserts.argumentIsNotNull(firstName)
  Asserts.argumentIsNotNull(lastName)
  Asserts.argumentIsNotNull(email)
  Asserts.argumentIsNotNull(username)
  Asserts.argumentIsNotNull(password)
}

object UserCreateModel
{
  implicit val jsonFormat = Json.format[UserCreateModel]

  val FIRST_NAME_FIELD  = Field.nonEmptyText("firstName")
  val LAST_NAME_FIELD   = Field.nonEmptyText("lastName")
  val EMAIL_FIELD       = Field.nonEmptyText("email")
  val USERNAME_FIELD    = Field.nonEmptyText("username")
  val PASSWORD_FIELD    = Field.text("password")
}
