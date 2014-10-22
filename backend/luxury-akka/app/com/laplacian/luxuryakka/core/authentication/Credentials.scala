package com.laplacian.luxuryakka.core.authentication

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.json.{Json, Format}

case class Credentials
(
  username : String,
  password : String
  )
{
  Asserts.argumentIsNotNull(username)
  Asserts.argumentIsNotNull(password)
}

object Credentials
{
  implicit val jsonFormat : Format[Credentials] = Json.format[Credentials]
}
