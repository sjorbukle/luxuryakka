package com.laplacian.luxuryakka.core.response

import com.laplacian.luxuryakka.core.Asserts
import play.api.libs.functional.syntax._
import play.api.libs.json._

case class RestResponse[TItem]
(
  data      : Option[TItem],
  messages  : Option[MessagesRestResponse] = None
)
{
  Asserts.argumentIsNotNull(data)
  Asserts.argumentIsNotNull(messages)

  lazy val json = Json.toJson(this)
}

object RestResponse
{
  def data[TItem](data: TItem) =
  {
    RestResponse(data = Some(data))
  }

  def messages(messagesRestResponse: MessagesRestResponse) =
  {
    RestResponse(
      data      = None,
      messages  = Some(messagesRestResponse)
    )
  }

  def of[TItem](data: TItem, messagesRestResponse: MessagesRestResponse) =
  {
    RestResponse(
      data      = Some(data),
      messages  = Some(messagesRestResponse)
    )
  }

  implicit def writes[TItem: Writes]: Writes[RestResponse[TItem]] = (
      (__ \ 'data).writeNullable[TItem] and
      (__ \ 'messages).writeNullable[MessagesRestResponse]
    )(unlift(RestResponse.unapply[TItem]))
}
