package com.laplacian.luxuryakka.module.generalsettings.dao.sql

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.module.generalsettings.dao.GeneralSettingsRepository
import com.laplacian.luxuryakka.module.generalsettings.dao.sql.mapper.GeneralSettingsMapper
import GeneralSettingsMapper.GeneralSettingsDetailsEntityTableDescriptor
import com.laplacian.luxuryakka.module.generalsettings.domain.GeneralSettingsDetailsEntity
import org.springframework.stereotype.Repository
import play.api.db.DB
import scala.slick.driver.PostgresDriver.simple._
import play.api.Play.current

@Repository
class GeneralSettingsRepositoryImpl extends GeneralSettingsRepository
{
  private lazy val db = Database.forDataSource(DB.getDataSource())

  override def findById(id: Long): Option[GeneralSettingsDetailsEntity] =
  {
    Asserts.argumentIsTrue(id >= 0)

    db.withSession {
      implicit session =>

        GeneralSettingsDetailsEntityTableDescriptor.query.filter(_.id === id).list.headOption
    }
  }
}
