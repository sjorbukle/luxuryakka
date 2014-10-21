package com.laplacian.luxuryakka.module.user.dao.sql

import com.laplacian.luxuryakka.module.user.dao.UserRepository
import com.laplacian.luxuryakka.module.user.dao.sql.mapper.UserEntityMapper._
import com.laplacian.luxuryakka.module.user.domain.{UserDetailsEntity, UserCreateEntity}
import com.laplacian.luxuryakka.core.{Asserts, GeneratedId}
import org.springframework.stereotype.Repository
import play.api.db.DB
import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._


@Repository
class UserRepositoryImpl() extends UserRepository
{
  private lazy val db = Database.forDataSource(DB.getDataSource())

  override def insert(item: UserCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    db.withTransaction {
      implicit session =>
        val userId = (UserCreateEntityMapper.query returning UserCreateEntityMapper.query.map(_.id)) += item

        GeneratedId(userId.toLong)
    }
  }

  override def findById(id: Long): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    db.withSession {
      implicit session =>

      UserDetailsEntityMapper.query.filter(_.id === id).list.headOption
    }
  }

  override def findByUsername(username: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(username)

    db.withSession {
      implicit session =>

        UserDetailsEntityMapper.query.filter(_.username === username).list.headOption
    }
  }

  override def findByEmail(email: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNullNorEmpty(email)

    db.withSession {
      implicit session =>

        UserDetailsEntityMapper.query.filter(_.email === email).list.headOption
    }
  }

  override def findAll: List[UserDetailsEntity] =
  {
    db.withSession {
      implicit session =>

        UserDetailsEntityMapper.query.list
    }
  }
}
