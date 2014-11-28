package com.laplacian.luxuryakka.module.organizationstructure.validation

import com.laplacian.luxuryakka.core.messages.Messages
import com.laplacian.luxuryakka.core.utils.ValidateUtils
import com.laplacian.luxuryakka.core.{Asserts, ValidationResult, Validator}
import com.laplacian.luxuryakka.module.organizationstructure.domain.{OrganizationStructureUpdateEntity, OrganizationStructureCreateEntity}
import com.laplacian.luxuryakka.module.organizationstructure.service.domain.OrganizationStructureDomainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OrganizationStructureUpdateValidator @Autowired
(
  private val entityDomainService: OrganizationStructureDomainService
) extends Validator[OrganizationStructureUpdateEntity]
{
  Asserts.argumentIsNotNull(entityDomainService)

  override def validate(item: OrganizationStructureUpdateEntity): ValidationResult[OrganizationStructureUpdateEntity] =
  {
    Asserts.argumentIsNotNull(item)

    val validationMessages = Messages.of

    validateName    (item, validationMessages)

    ValidationResult(
      validatedItem = item,
      messages      = validationMessages
    )
  }

  private def validateName(item: OrganizationStructureUpdateEntity, validationMessages: Messages)
  {
    val localMessages = Messages.of(validationMessages)

    val fieldValue = item.name
    val formId = OrganizationStructureCreateEntity.NAME_FORM_ID.value

    ValidateUtils.validateLengthIsLessThanOrEqual(
      fieldValue,
      80,
      localMessages,
      formId,
      "must be less than or equal to 80 character"
    )

    val itemFromDb = this.entityDomainService.getById(item.id)
    if(itemFromDb.name != item.name) {
      val doesExistWithEmail = this.entityDomainService.doesExistByName(fieldValue)
      ValidateUtils.isFalse(
        doesExistWithEmail,
        localMessages,
        formId,
        "OrganizationStructure already exists with this name"
      )
    }
  }
}
