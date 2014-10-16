package com.laplacian.luxuryakka.module.user.validation

import com.laplacian.luxuryakka.core.{ValidationResult, Validator, Asserts}
import com.laplacian.luxuryakka.core.messages.Messages
import com.laplacian.luxuryakka.module.user.model.UserCreateModel
import com.laplacian.luxuryakka.module.user.service.domain.UserDomainService
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.laplacian.luxuryakka.core.utils.ValidateUtils

@Component
class UserCreateValidator @Autowired
(
  private val userDomainService: UserDomainService
) extends Validator[UserCreateModel]
{
  Asserts.argumentIsNotNull(userDomainService)

  override def validate(item: UserCreateModel): ValidationResult[UserCreateModel] =
  {
    Asserts.argumentIsNotNull(item)

    val validationMessages = Messages.of

    validateFirstName (item, validationMessages)
    validateLastName  (item, validationMessages)
    validateEmail     (item, validationMessages)
    validateUserName  (item, validationMessages)
    validatePassword  (item, validationMessages)

    ValidationResult(
      validatedItem = item,
      messages      = validationMessages
    )
  }

  private def validateFirstName(item: UserCreateModel, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val valueCandidate = item.firstName

    UserCreateModel.FIRST_NAME_FIELD.evaluateBindJsValue(valueCandidate, localMessages)
    if(!localMessages.hasErrors()) {
      val description = UserCreateModel.FIRST_NAME_FIELD.bindJsValue(valueCandidate)
      ValidateUtils.validateLengthIsLessThanOrEqual(
        description,
        80,
        localMessages,
        UserCreateModel.FIRST_NAME_FIELD.key,
        "must be less than or equal to 80 character"
      )
    }
  }

  private def validateLastName(item: UserCreateModel, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val valueCandidate = item.lastName

    UserCreateModel.LAST_NAME_FIELD.evaluateBindJsValue(valueCandidate, localMessages)
    if(!localMessages.hasErrors()) {
      val lastName = UserCreateModel.LAST_NAME_FIELD.bindJsValue(valueCandidate)
      ValidateUtils.validateLengthIsLessThanOrEqual(
        lastName,
        80,
        localMessages,
        UserCreateModel.LAST_NAME_FIELD.key,
        "must be less than or equal to 80 character"
      )
    }
  }

  private def validateEmail(item: UserCreateModel, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val valueCandidate = item.email

    UserCreateModel.EMAIL_FIELD.evaluateBindJsValue(valueCandidate, localMessages)
    if(!localMessages.hasErrors)
    {
      val value = UserCreateModel.EMAIL_FIELD.bindJsValue(valueCandidate)
      ValidateUtils.validateEmail(value, UserCreateModel.EMAIL_FIELD.messageKey, localMessages)
      if(!localMessages.hasErrors)
      {
        val doesExistWithEmail = this.userDomainService.doesExistByByEmail(value)
        ValidateUtils.isFalse(
          doesExistWithEmail,
          localMessages,
          UserCreateModel.EMAIL_FIELD.key,
          "User already exists with this email"
        )
      }

      ValidateUtils.validateLengthIsLessThanOrEqual(
        value,
        80,
        localMessages,
        UserCreateModel.EMAIL_FIELD.key,
        "must be less than or equal to 80 character"
      )
    }
  }

  private def validateUserName(item: UserCreateModel, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val valueCandidate = item.username

    UserCreateModel.USERNAME_FIELD.evaluateBindJsValue(valueCandidate, localMessages)
    if(!localMessages.hasErrors)
    {
      val value = UserCreateModel.USERNAME_FIELD.bindJsValue(valueCandidate)

      val doesExistWithUsername = this.userDomainService.doesExistByUsername(value)
      ValidateUtils.isFalse(
        doesExistWithUsername,
        localMessages,
        UserCreateModel.USERNAME_FIELD.key,
        "User already exists with this username"
      )

      ValidateUtils.validateLengthIsLessThanOrEqual(
        value,
        80,
        localMessages,
        UserCreateModel.USERNAME_FIELD.key,
        "must be less than or equal to 80 character"
      )
    }
  }

  private def validatePassword(item: UserCreateModel, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val valueCandidate = item.password

    UserCreateModel.PASSWORD_FIELD.evaluateBindJsValue(valueCandidate, localMessages)
    if(!localMessages.hasErrors()) {
      val password = UserCreateModel.PASSWORD_FIELD.bindJsValue(valueCandidate)
      ValidateUtils.validateLengthIsLessThanOrEqual(
        password,
        80,
        localMessages,
        UserCreateModel.PASSWORD_FIELD.key,
        "must be less than or equal to 80 character"
      )
    }
  }
}