package com.laplacian.luxuryakka.core

import com.laplacian.luxuryakka.core.messages.Messages
import com.laplacian.luxuryakka.core.response.ResponseTools
import play.api.libs.json.Writes

trait Validator[T]
{
  def validate(item:T) : ValidationResult[T]
}

case class ValidationResult[T: Writes]
(
  validatedItem : T,
  messages      : Messages
)
{
  Asserts.argumentIsNotNull(validatedItem)
  Asserts.argumentIsNotNull(messages)

  def isValid :Boolean =
  {
    !messages.hasErrors
  }

  def errorsRestResponse =
  {
    Asserts.argumentIsTrue(!this.isValid)

    ResponseTools.of(
      data      = Some(this.validatedItem),
      messages  = Some(messages)
    )
  }
}
