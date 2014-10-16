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
  selfInstance =>
  Asserts.argumentIsNotNull(data)
  Asserts.argumentIsNotNull(messages)
}

object RestResponse
{
  def data[TItem](data: TItem) =
  {
    Asserts.argumentIsNotNull(data)

    RestResponse[TItem](data = Some(data))
  }

  def messages(messagesRestResponse: MessagesRestResponse) =
  {
    Asserts.argumentIsNotNull(messagesRestResponse)

    RestResponse(
      data      = Option.empty[JsValue],
      messages  = Some(messagesRestResponse)
    )
  }

  def of[TItem](data: TItem, messagesRestResponse: MessagesRestResponse) =
  {
    Asserts.argumentIsNotNull(data)
    Asserts.argumentIsNotNull(messagesRestResponse)

    RestResponse[TItem](
      data      = Some(data),
      messages  = Some(messagesRestResponse)
    )
  }

  def jsErrorToRestResponse(errors: JsError) =
  {
    Asserts.argumentIsNotNull(errors)

    RestResponse.errorsToRestResponse(errors.errors.map(_._2).flatten.map(_.message).toList)
  }

  def errorsToRestResponse(errors: List[String]) =
  {
    Asserts.argumentIsNotNull(errors)

    val messagesResponse = MessagesRestResponse(
      global = Some(GlobalMessagesRestResponse(errors = errors))
    )
    RestResponse.messages(messagesResponse)
  }

  def errorToRestResponse(error: String) =
  {
    Asserts.argumentIsNotNull(error)

    RestResponse.errorsToRestResponse(List(error))
  }

  implicit def writes[TItem: Writes]: Writes[RestResponse[TItem]] = (
      (__ \ 'data).writeNullable[TItem] and
      (__ \ 'messages).writeNullable[MessagesRestResponse]
    )(unlift(RestResponse.unapply[TItem]))
}
