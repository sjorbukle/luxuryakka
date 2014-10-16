package com.laplacian.luxuryakka.core.response

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.json.Json

case class LocalMessagesRestResponse
(
  formIdentifier  : String,
  info            : List[String] = List.empty,
  warnings        : List[String] = List.empty,
  errors          : List[String] = List.empty
)
{
  Asserts.argumentIsNotNull(formIdentifier)
  Asserts.argumentIsNotNull(info)
  Asserts.argumentIsNotNull(warnings)
  Asserts.argumentIsNotNull(errors)
}

object LocalMessagesRestResponse
{
  implicit val jsonFormat = Json.format[LocalMessagesRestResponse]
}
