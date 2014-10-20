package com.laplacian.luxuryakka.module.log.action.dao.mapper

import com.laplacian.luxuryakka.module.log.action.domain.{ActionType, ActionDomainType, ActionLog}
import org.joda.time.DateTime
import play.api.libs.json.JsValue
import scala.slick.driver.PostgresDriver.simple._
import com.laplacian.luxuryakka.core.slick.custommapper.CustomSlickMapper.Postgres._

object ActionLogMapper
{
  final val ACTION_LOG_TABLE_NAME = "action_log"

  final val ID_COLUMN           = "id"
  final val USER_ID_COLUMN      = "user_id"
  final val DOMAIN_TYPE_COLUMN  = "domain_type"
  final val DOMAIN_ID_COLUMN    = "domain_id"
  final val ACTION_TYPE_COLUMN  = "action_type"
  final val BEFORE_COLUMN       = "before"
  final val AFTER_COLUMN        = "after"
  final val CREATED_ON_COLUMN   = "created_on"

  implicit val actionDomainTypeColumnType = enumColumnType[ActionDomainType]
  implicit val actionTypeColumnType       = enumColumnType[ActionType]

  class ActionLogMapper(tag: Tag) extends Table[ActionLog](tag, ACTION_LOG_TABLE_NAME)
  {
    def id          = column[Long]            (ID_COLUMN,           O.PrimaryKey, O.AutoInc )
    def userId      = column[Long]            (USER_ID_COLUMN,      O.NotNull)
    def domainType  = column[ActionDomainType](DOMAIN_TYPE_COLUMN,  O.NotNull)
    def domainId    = column[Long]            (DOMAIN_ID_COLUMN,    O.NotNull)
    def actionType  = column[ActionType]      (ACTION_TYPE_COLUMN,  O.NotNull)
    def before      = column[JsValue]         (BEFORE_COLUMN,       O.NotNull)
    def after       = column[JsValue]         (AFTER_COLUMN,        O.NotNull)
    def createdOn   = column[DateTime]        (AFTER_COLUMN,        O.NotNull)

    def * = (
      id.?,
      userId,
      domainType,
      domainId,
      actionType,
      before.?,
      after.?,
      createdOn
    ) <> ((ActionLog.apply _).tupled, ActionLog.unapply)
  }
  object ActionLogMapper
  {
    def query = TableQuery[ActionLogMapper]
  }
}
