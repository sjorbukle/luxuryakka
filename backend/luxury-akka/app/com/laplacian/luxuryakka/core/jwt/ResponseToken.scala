package com.laplacian.luxuryakka.core.jwt

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.json.{Format, Json}

case class ResponseToken(token: String)
{
  Asserts.argumentIsNotNullNorEmpty(token)
}

object ResponseToken
{
  implicit val jsonFormat : Format[ResponseToken] = Json.format[ResponseToken]
}
