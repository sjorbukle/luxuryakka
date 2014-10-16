package com.laplacian.luxuryakka.core

import com.laplacian.luxuryakka.core.messages.Messages

trait Validator[TItem]
{
  def validate(item:TItem) : ValidationResult[TItem]
}

case class ValidationResult[TItem]
(
  validatedItem : TItem,
  messages      : Messages
)
{
  Asserts.argumentIsNotNull(validatedItem)
  Asserts.argumentIsNotNull(messages)

  def isValid :Boolean =
  {
    !messages.hasErrors
  }
}
