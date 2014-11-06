package com.laplacian.luxuryakka.module.organizationstructure.domain

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.json.customfomatters.CustomFormatter.Enum
import play.api.libs.json._

case class OrganizationStructureDetailsEntity
(
  id                : Long,
  name              : String,
  entityType        : OrganizationStructureType,
  parentId          : Option[Long],
  description       : String,
  shortDescription  : String
)
{
  Asserts.argumentIsNotNull(name)
  Asserts.argumentIsNotNull(entityType)
  Asserts.argumentIsNotNull(parentId)
  Asserts.argumentIsNotNull(description)
  Asserts.argumentIsNotNull(shortDescription)
}

object OrganizationStructureDetailsEntity
{
  implicit val organizationStructureTypeWrites= Enum.enumWritesByName[OrganizationStructureType]
  implicit val organizationStructureTypeReads = Enum.enumReadsByName[OrganizationStructureType]

  implicit val jsonFormat = Json.format[OrganizationStructureDetailsEntity]
}
