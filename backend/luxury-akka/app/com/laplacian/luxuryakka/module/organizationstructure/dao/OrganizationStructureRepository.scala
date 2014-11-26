package com.laplacian.luxuryakka.module.organizationstructure.dao

import com.laplacian.luxuryakka.core.GeneratedId
import com.laplacian.luxuryakka.module.organizationstructure.domain._

trait OrganizationStructureRepository
{
  def insert(item: OrganizationStructureCreateEntity): GeneratedId

  def update(item: OrganizationStructureUpdateEntity)

  def findById(id: Long): Option[OrganizationStructureDetailsEntity]
  def findByName(name: String): Option[OrganizationStructureDetailsEntity]

  def findAll: List[OrganizationStructureDetailsEntity]
  def findAllByType(entityType: OrganizationStructureType): List[OrganizationStructureDetailsEntity]
  def findAllLookupByType(entityType: OrganizationStructureType): List[OrganizationStructureLookupEntity]
  def findAllByParent(parentId: Long): List[OrganizationStructureDetailsEntity]
}
