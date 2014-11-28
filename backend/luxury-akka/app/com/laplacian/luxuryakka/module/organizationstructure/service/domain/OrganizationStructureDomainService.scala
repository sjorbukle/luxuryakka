package com.laplacian.luxuryakka.module.organizationstructure.service.domain

import com.laplacian.luxuryakka.core.{Asserts, GeneratedId}
import com.laplacian.luxuryakka.module.organizationstructure.domain._

trait OrganizationStructureDomainService
{
  def create(item: OrganizationStructureCreateEntity): GeneratedId

  def update(item: OrganizationStructureUpdateEntity): OrganizationStructureDetailsEntity

  def tryGetById(id: Long): Option[OrganizationStructureDetailsEntity]
  def tryGetByName(name: String): Option[OrganizationStructureDetailsEntity]

  def getAll: List[OrganizationStructureDetailsEntity]
  def getAllByType(entityType: OrganizationStructureType): List[OrganizationStructureDetailsEntity]
  def getAllLookupByType(entityType: OrganizationStructureType): List[OrganizationStructureLookupEntity]
  def getAllByParent(parentId: Long): List[OrganizationStructureDetailsEntity]

  def getById(id: Long): OrganizationStructureDetailsEntity =
  {
    Asserts.argumentIsNotNull(id)

    this.tryGetById(id).getOrElse(throw new RuntimeException("OrganizationStructure with this id does not exist"))
  }

  def getByName(name: String): OrganizationStructureDetailsEntity =
  {
    Asserts.argumentIsNotNull(name)

    this.tryGetByName(name).getOrElse(throw new RuntimeException("OrganizationStructure with this name does not exist"))
  }

  def doesExistByName(name: String): Boolean =
  {
    Asserts.argumentIsNotNull(name)

    val itemCandidate = this.tryGetByName(name)
    itemCandidate.isDefined
  }
}
