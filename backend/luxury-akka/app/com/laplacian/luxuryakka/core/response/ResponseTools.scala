package com.laplacian.luxuryakka.core.response

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.messages.{MessageKey, Messages}
import play.api.libs.json.{JsError, JsValue, Writes}

object ResponseTools
{
  val GLOBAL_MESSAGE_KEY = MessageKey("GLOBAL_MESSAGE")

  def restResponseOf[T: Writes](data: T, messages: Messages): RestResponse[T] =
  {
    val messagesRestResponse = ResponseTools.messagesToMessagesRestResponse(messages)

    RestResponse[T](
      data      = Some(data),
      messages  = Some(messagesRestResponse)
    )
  }

  def messagesToMessagesRestResponse(messages: Messages): MessagesRestResponse =
  {
    val global = Helper.messagesToGlobalMessagesRestResponse(messages)
    val local = Helper.messagesToLocalMessagesRestResponse(messages)

    MessagesRestResponse(
      global  = Some(global),
      local   = local
    )
  }

  def data[TItem: Writes](data: TItem) =
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

  def of[TItem: Writes](data: TItem, messages: Option[Messages]) =
  {
    Asserts.argumentIsNotNull(data)
    Asserts.argumentIsNotNull(messages)

    RestResponse[TItem](
      data      = Some(data),
      messages  = messages.map(ResponseTools.messagesToMessagesRestResponse)
    )
  }

  def jsErrorToRestResponse(errors: JsError) =
  {
    Asserts.argumentIsNotNull(errors)

    ResponseTools.errorsToRestResponse(errors.errors.map(_._2).flatten.map(_.message).toList)
  }

  def errorsToRestResponse(errors: List[String]) =
  {
    Asserts.argumentIsNotNull(errors)

    val messagesResponse = MessagesRestResponse(
      global = Some(GlobalMessagesRestResponse(errors = errors))
    )
    ResponseTools.messages(messagesResponse)
  }

  def errorToRestResponse(error: String) =
  {
    Asserts.argumentIsNotNull(error)

    ResponseTools.errorsToRestResponse(List(error))
  }

  private object Helper
  {
    def messagesToGlobalMessagesRestResponse(messages: Messages): GlobalMessagesRestResponse =
    {
      val allBindMessages = messages.bindMessages()
      val allGlobalBindMessage = allBindMessages.filter(_.key == Some(GLOBAL_MESSAGE_KEY)).toList

      val globalInfo = allGlobalBindMessage.filter(_.messageType.isInformation).map(_.text)
      val globalWarnings = allGlobalBindMessage.filter(_.messageType.isWarning).map(_.text)
      val globalErrors = allGlobalBindMessage.filter(_.messageType.isError).map(_.text)

      GlobalMessagesRestResponse(
        info      = globalInfo,
        warnings  = globalWarnings,
        errors    = globalErrors
      )
    }

    def messagesToLocalMessagesRestResponse(messages: Messages): List[LocalMessagesRestResponse] =
    {
      val allBindMessages = messages.bindMessages()
      val allNonGlobalMessages = allBindMessages.filter(_.key.get != GLOBAL_MESSAGE_KEY).toList
      val messagesGroupedByKey = allNonGlobalMessages.groupBy(_.key.get)

      messagesGroupedByKey.map(record =>
        LocalMessagesRestResponse(
          formIdentifier  = record._1.value,
          info            = record._2.filter(_.messageType.isInformation).map(_.text),
          warnings        = record._2.filter(_.messageType.isWarning).map(_.text),
          errors          = record._2.filter(_.messageType.isError).map(_.text)
        )
      ).toList
    }
  }
}
