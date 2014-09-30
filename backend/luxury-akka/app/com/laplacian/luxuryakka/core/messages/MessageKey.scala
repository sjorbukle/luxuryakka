package com.laplacian.luxuryakka.core.messages

import com.laplacian.luxuryakka.core.Asserts._
import play.api.libs.json.Json

case class MessageKey(value: String)
{
  argumentIsNotNullNorEmpty(value)
}

object MessageKey
{
  implicit val jsonWrites = Json.writes[MessageKey]
}
