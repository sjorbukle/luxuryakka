package com.laplacian.luxuryakka.module.organizationstructure.domain

import com.laplacian.luxuryakka.core.Asserts

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
