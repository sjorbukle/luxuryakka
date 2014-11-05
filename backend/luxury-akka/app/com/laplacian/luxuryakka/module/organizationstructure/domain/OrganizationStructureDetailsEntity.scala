package com.laplacian.luxuryakka.module.organizationstructure.domain

import com.laplacian.luxuryakka.core.Asserts

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
