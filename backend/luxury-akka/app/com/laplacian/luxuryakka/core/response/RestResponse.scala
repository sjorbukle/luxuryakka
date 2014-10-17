package com.laplacian.luxuryakka.core.response

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class RestResponse[TItem: Writes]
(
  data      : Option[TItem],
  messages  : Option[MessagesRestResponse] = None
)
{
  selfRef =>
  Asserts.argumentIsNotNull(data)
  Asserts.argumentIsNotNull(messages)

  lazy val json = Json.toJson(selfRef)
}

object RestResponse
{
  implicit def writes[TItem: Writes]: Writes[RestResponse[TItem]] = (
      (__ \ 'data).writeNullable[TItem] and
      (__ \ 'messages).writeNullable[MessagesRestResponse]
    )(unlift(RestResponse.unapply[TItem]))
}
