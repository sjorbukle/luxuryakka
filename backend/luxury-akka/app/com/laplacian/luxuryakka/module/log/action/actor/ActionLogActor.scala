package com.laplacian.luxuryakka.module.log.action.actor

import akka.actor.{ActorLogging, Actor}
import com.laplacian.luxuryakka.module.log.action.dao.mapper.ActionLogMapper.ActionLogMapper
import com.laplacian.luxuryakka.module.log.action.domain.ActionLogEntity
import play.api.db.DB
import play.api.Play.current
import scala.slick.driver.PostgresDriver.simple._
import scala.concurrent._
import ExecutionContext.Implicits.global

case class ActionLogCreateMsg(actionLog: ActionLogEntity)

class ActionLogActor extends Actor with ActorLogging
{
  private lazy val db = Database.forDataSource(DB.getDataSource())

  def receive = {
    case ActionLogCreateMsg(actionLog) =>
      log.debug(s"ActionLog received.")

      val actionInsertFuture = Future(
        db.withTransaction {
          implicit session =>
            val userId = (ActionLogMapper.query returning ActionLogMapper.query.map(_.id)) += actionLog
            if(!(userId.toLong > 0)) {
              log.error(s"ActionLog insert failed for: DomainType: '${actionLog.domainType.name}', DomainId: '${actionLog.domainId}', Action: '${actionLog.actionType.name}'")
            }
        }
      )
      actionInsertFuture.onSuccess {
        case e =>
          log.debug(s"Action logged. DomainType: '${actionLog.domainType.name}', DomainId: '${actionLog.domainId}', Action: '${actionLog.actionType.name}'")
      }
      actionInsertFuture.onFailure {
        case e =>
          log.debug(s"Action insert failed.Exception: '${e.toString}'")
      }

    case _ => log.error("ActionLogActor received invalid message.")
  }
}
