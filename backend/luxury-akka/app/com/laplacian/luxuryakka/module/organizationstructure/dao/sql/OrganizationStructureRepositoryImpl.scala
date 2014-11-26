package com.laplacian.luxuryakka.module.organizationstructure.dao.sql

import com.laplacian.luxuryakka.core.{Asserts, GeneratedId}
import com.laplacian.luxuryakka.module.organizationstructure.dao.OrganizationStructureRepository
import com.laplacian.luxuryakka.module.organizationstructure.dao.sql.mapper.OrganizationStructureMapper.OrganizationStructureDetailsTableDescriptor
import com.laplacian.luxuryakka.module.organizationstructure.domain._
import org.springframework.stereotype.Repository
import play.api.db.DB
import scala.slick.driver.PostgresDriver.simple._
import play.api.Play.current
import com.laplacian.luxuryakka.module.organizationstructure.dao.sql.mapper.OrganizationStructureMapper.organizationStructureType
import scala.slick.jdbc.StaticQuery._

@Repository
class OrganizationStructureRepositoryImpl extends OrganizationStructureRepository
{
  private lazy val db = Database.forDataSource(DB.getDataSource())

  override def insert(item: OrganizationStructureCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    db.withTransaction {
      implicit session =>
        val generatedIdCandidateValue = OrganizationStructureRepositoryQuery.INSERT_QUERY(item).as[Long].firstOption
        Asserts.argumentIsTrue(generatedIdCandidateValue.isDefined)

        GeneratedId(generatedIdCandidateValue.get)
    }
  }

  override def update(item: OrganizationStructureUpdateEntity)
  {
    Asserts.argumentIsNotNull(item)

    db.withTransaction {
      implicit session =>
        OrganizationStructureRepositoryQuery.UPDATE_QUERY(item).asUpdate.execute
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

  override def findByName(name: String): Option[OrganizationStructureDetailsEntity] =
  {
    Asserts.argumentIsNotNull(name)

    db.withSession {
      implicit session =>

        OrganizationStructureDetailsTableDescriptor.query.filter(_.name === name).list.headOption
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
    Asserts.argumentIsNotNull(entityType)

    db.withSession {
      implicit session =>
        OrganizationStructureDetailsTableDescriptor.query.filter(_.entityType === entityType).list
    }
  }

  override def findAllLookupByType(entityType: OrganizationStructureType): List[OrganizationStructureLookupEntity] =
  {
    Asserts.argumentIsNotNull(entityType)

    db.withSession {
      implicit session =>
        OrganizationStructureDetailsTableDescriptor.query.filter(_.entityType === entityType).map(o =>
          (o.id, o.name, o.entityType)
        ).run.map((OrganizationStructureLookupEntity.apply _).tupled).toList
    }
  }

  override def findAllByParent(parentId: Long): List[OrganizationStructureDetailsEntity] =
  {
    db.withSession {
      implicit session =>
        OrganizationStructureDetailsTableDescriptor.query.filter(_.parentId === parentId).list
    }
  }
}

private object OrganizationStructureRepositoryQuery
{
  def INSERT_QUERY(item: OrganizationStructureCreateEntity) = {
    sql"""
        INSERT INTO organization_structure(name, parent_id, entity_type_id, description, short_description)
        VALUES (${item.name}, ${item.parentId}, ${Long.unbox(item.entityType.id)}, ${item.description}, ${item.shortDescription})
        RETURNING id
    """
  }

  def UPDATE_QUERY(item: OrganizationStructureUpdateEntity) = {
    sql"""
        UPDATE organization_structure SET
        name = ${item.name},
        description = ${item.description},
        short_description = ${item.shortDescription}
        WHERE id = ${item.id}
    """
  }
}
