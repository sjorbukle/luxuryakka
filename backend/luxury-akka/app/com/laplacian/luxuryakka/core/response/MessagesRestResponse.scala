package com.laplacian.luxuryakka.core.response

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.json.Json

case class MessagesRestResponse
(
  global    : Option[GlobalMessagesRestResponse]  = None,
  local     : List[LocalMessagesRestResponse]     = List.empty,
  authError : Option[String]                      = None
)
{
  Asserts.argumentIsNotNull(global)
  Asserts.argumentIsNotNull(local)
  Asserts.argumentIsNotNull(authError)
}

object MessagesRestResponse
{
  implicit val jsonFormat = Json.format[MessagesRestResponse]
}
