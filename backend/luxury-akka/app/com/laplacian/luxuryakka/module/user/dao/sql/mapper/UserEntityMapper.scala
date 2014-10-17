package com.laplacian.luxuryakka.module.user.dao.sql.mapper

import com.laplacian.luxuryakka.module.user.domain.{UserDetailsEntity, UserCreateEntity}

import scala.slick.driver.PostgresDriver.simple._

object UserEntityMapper
{
  final val USERS_TABLE_NAME = "users"

  final val USER_ID_COLUMN          = "id"
  final val USER_FIRST_NAME_COLUMN  = "first_name"
  final val USER_LAST_NAME_COLUMN   = "last_name"
  final val USER_USERNAME_COLUMN    = "username"
  final val USER_EMAIL_COLUMN       = "email"
  final val USER_PASSWORD_COLUMN    = "password"

  class UserCreateEntityMapper(tag: Tag) extends Table[UserCreateEntity](tag, USERS_TABLE_NAME)
  {
    def id        = column[Long]  (USER_ID_COLUMN,          O.PrimaryKey, O.AutoInc )
    def firstName = column[String](USER_FIRST_NAME_COLUMN,  O.NotNull)
    def lastName  = column[String](USER_LAST_NAME_COLUMN,   O.NotNull)
    def username  = column[String](USER_USERNAME_COLUMN,    O.NotNull)
    def email     = column[String](USER_EMAIL_COLUMN,       O.NotNull)
    def password  = column[String](USER_PASSWORD_COLUMN,    O.NotNull)

    def * = (
      id.?,
      firstName,
      lastName,
      username,
      email,
      password
    ) <> ((UserCreateEntity.apply _).tupled, UserCreateEntity.unapply)
  }
  object UserCreateEntityMapper
  {
    def query = TableQuery[UserCreateEntityMapper]
  }

  class UserDetailsEntityMapper(tag: Tag) extends Table[UserDetailsEntity](tag, USERS_TABLE_NAME)
  {
    def id        = column[Long]  (USER_ID_COLUMN,          O.PrimaryKey, O.AutoInc )
    def firstName = column[String](USER_FIRST_NAME_COLUMN,  O.NotNull               )
    def lastName  = column[String](USER_LAST_NAME_COLUMN,   O.NotNull               )
    def username  = column[String](USER_USERNAME_COLUMN,    O.NotNull               )
    def email     = column[String](USER_EMAIL_COLUMN,       O.NotNull               )
    def password  = column[String](USER_PASSWORD_COLUMN,    O.NotNull               )

    def * = (
      id,
      firstName,
      lastName,
      username,
      email,
      password
      ) <> ((UserDetailsEntity.apply _).tupled, UserDetailsEntity.unapply)
  }
  object UserDetailsEntityMapper
  {
    def query = TableQuery[UserDetailsEntityMapper]
  }
}

