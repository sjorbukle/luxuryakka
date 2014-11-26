package com.laplacian.luxuryakka.module.organizationstructure.domain

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.messages.MessageKey
import play.api.libs.json.Json

case class OrganizationStructureUpdateEntity
(
  id                : Long,
  name              : String,
  description       : String,
  shortDescription  : String
)
{
  Asserts.argumentIsNotNull(name)
  Asserts.argumentIsNotNull(description)
  Asserts.argumentIsNotNull(shortDescription)
}

object OrganizationStructureUpdateEntity
{
  implicit val jsonFormat = Json.format[OrganizationStructureUpdateEntity]

  val IDE_FORM_ID               = MessageKey("id")
  val NAME_FORM_ID              = MessageKey("name")
  val DESCRIPTION_FORM_ID       = MessageKey("description")
  val SHORT_DESCRIPTION_FORM_ID = MessageKey("shortDescription")
}
