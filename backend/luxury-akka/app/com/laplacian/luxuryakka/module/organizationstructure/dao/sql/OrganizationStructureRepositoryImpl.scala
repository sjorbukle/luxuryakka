package com.laplacian.luxuryakka.module.organizationstructure.dao.sql

import com.laplacian.luxuryakka.core.{Asserts, GeneratedId}
import com.laplacian.luxuryakka.module.organizationstructure.dao.OrganizationStructureRepository
import com.laplacian.luxuryakka.module.organizationstructure.dao.sql.mapper.OrganizationStructureMapper.OrganizationStructureDetailsTableDescriptor
import com.laplacian.luxuryakka.module.organizationstructure.domain.{OrganizationStructureLookupEntity, OrganizationStructureType, OrganizationStructureDetailsEntity, OrganizationStructureCreateEntity}
import org.springframework.stereotype.Repository
import play.api.db.DB
import scala.slick.driver.PostgresDriver.simple._
import play.api.Play.current
import com.laplacian.luxuryakka.module.organizationstructure.dao.sql.mapper.OrganizationStructureMapper.organizationStructureType

@Repository
class OrganizationStructureRepositoryImpl extends OrganizationStructureRepository
{
  private lazy val db = Database.forDataSource(DB.getDataSource())

  override def insert(item: OrganizationStructureCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    db.withTransaction {
      implicit session =>
        val generatedIdCandidateValue = (OrganizationStructureDetailsTableDescriptor.query.map(
          s => (s.name, s.entityType)
        ) returning OrganizationStructureDetailsTableDescriptor.query.map(_.id)) += (item.name, item.entityType)

        GeneratedId(generatedIdCandidateValue.toLong)
    }
  }

  override def findById(id: Long): Option[OrganizationStructureDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    db.withSession {
      implicit session =>

        OrganizationStructureDetailsTableDescriptor.query.filter(_.id === id).list.headOption
    }
  }

  override def findAll: List[OrganizationStructureDetailsEntity] =
  {
    db.withSession {
      implicit session =>
        OrganizationStructureDetailsTableDescriptor.query.list
    }
  }

  override def findAllByType(entityType: OrganizationStructureType): List[OrganizationStructureDetailsEntity] =
  {
    db.withSession {
      implicit session =>
        OrganizationStructureDetailsTableDescriptor.query.filter(
          x =>
            x.entityType === (entityType: OrganizationStructureType)
        ).list
    }
  }

  override def findAllLookupByType(entityType: OrganizationStructureType): List[OrganizationStructureLookupEntity] =
  {
    db.withSession {
      implicit session =>
        OrganizationStructureDetailsTableDescriptor.query.filter(_.entityType === entityType).map(o =>
          (o.id, o.name, o.entityType)
        ).run.map(
          record =>
            OrganizationStructureLookupEntity(
              id         = record._1,
              name       = record._2,
              entityType = record._3
            )
        ).toList
    }
  }
}
