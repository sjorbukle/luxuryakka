package com.laplacian.luxuryakka.module.organizationstructure.domain

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.json.customfomatters.CustomFormatter.Enum
import play.api.libs.json.Json

case class OrganizationStructureLookupEntity
(
  id          : Long,
  name        : String,
  entityType  : OrganizationStructureType
)
{
  Asserts.argumentIsNotNull(id)
  Asserts.argumentIsNotNull(name)
  Asserts.argumentIsNotNull(entityType)
}

object OrganizationStructureLookupEntity
{
  implicit val organizationStructureTypeWrites= Enum.enumWritesByName[OrganizationStructureType]
  implicit val organizationStructureTypeReads = Enum.enumReadsByName[OrganizationStructureType]

  implicit val jsonFormat = Json.format[OrganizationStructureLookupEntity]
}
