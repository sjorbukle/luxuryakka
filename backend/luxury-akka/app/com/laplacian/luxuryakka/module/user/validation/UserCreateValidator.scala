package com.laplacian.luxuryakka.module.user.validation

import com.laplacian.luxuryakka.core.{ValidationResult, Validator, Asserts}
import com.laplacian.luxuryakka.core.messages.Messages
import com.laplacian.luxuryakka.module.user.domain.UserCreateEntity
import com.laplacian.luxuryakka.module.user.service.domain.UserDomainService
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.laplacian.luxuryakka.core.utils.ValidateUtils

@Component
class UserCreateValidator @Autowired
(
  private val userDomainService: UserDomainService
) extends Validator[UserCreateEntity]
{
  Asserts.argumentIsNotNull(userDomainService)

  override def validate(item: UserCreateEntity): ValidationResult[UserCreateEntity] =
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

  private def validateFirstName(item: UserCreateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.firstName

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      80,
      localMessages,
      UserCreateEntity.FIRST_NAME_FORM_ID.value,
      "must be less than or equal to 80 character"
    )
  }

  private def validateLastName(item: UserCreateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.lastName

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      80,
      localMessages,
      UserCreateEntity.LAST_NAME_FORM_ID.value,
      "must be less than or equal to 80 character"
    )
  }

  private def validateEmail(item: UserCreateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.email

    ValidateUtils.validateEmail(
      fieldValue,
      UserCreateEntity.EMAIL_FORM_ID,
      localMessages
    )

    val doesExistWithEmail = this.userDomainService.doesExistByByEmail(fieldValue)
    val aaa = this.userDomainService.getAll
    ValidateUtils.isFalse(
      doesExistWithEmail,
      localMessages,
      UserCreateEntity.EMAIL_FORM_ID.value,
      "User already exists with this email"
    )

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      80,
      localMessages,
      UserCreateEntity.EMAIL_FORM_ID.value,
      "must be less than or equal to 80 character"
    )
  }

  private def validateUserName(item: UserCreateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.username

    val doesExistWithUsername = this.userDomainService.doesExistByUsername(fieldValue)
    ValidateUtils.isFalse(
      doesExistWithUsername,
      localMessages,
      UserCreateEntity.USERNAME_FORM_ID.value,
      "User already exists with this username"
    )

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      80,
      localMessages,
      UserCreateEntity.USERNAME_FORM_ID.value,
      "must be less than or equal to 80 character"
    )
  }

  private def validatePassword(item: UserCreateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.password

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      80,
      localMessages,
      UserCreateEntity.PASSWORD_FORM_ID.value,
      "must be less than or equal to 80 character"
    )
  }
}