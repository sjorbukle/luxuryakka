package com.laplacian.luxuryakka.module.log.action.actor

import akka.actor.{ActorLogging, Actor}
import anorm.SqlParser._
import com.laplacian.luxuryakka.module.log.action.domain.ActionLogEntity
import com.laplacian.luxuryakka.core.utils.DateUtils
import play.api.db.DB
import play.api.Play.current
import play.api.libs.json.Json
import scala.slick.driver.PostgresDriver.simple._
import scala.concurrent._
import ExecutionContext.Implicits.global
import anorm.SQL

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
            val actionLogIdCandidate = SQL(ActionLogActor.INSERT_QUERY).on(
              'userId     -> actionLog.userId,
              'domainType -> actionLog.domainType.name,
              'domainId   -> actionLog.domainId,
              'actionType -> actionLog.actionType.name,
              'before     -> actionLog.before.map(Json.stringify),
              'after      -> actionLog.after.map(Json.stringify),
              'createdOn  -> DateUtils.jodaDateTimeToJavaDate(actionLog.createdOn)
            ).executeInsert(scalar[Long].singleOpt)(session.conn)

            if(!actionLogIdCandidate.isDefined) {
              log.error(s"ActionLog insert failed for: DomainType: '${actionLog.domainType.name}', DomainId: '${actionLog.domainId}', Action: '${actionLog.actionType.name}'")
            }
        }
      )
      actionInsertFuture.onSuccess {
        case e =>
          log.info(s"Action logged. DomainType: '${actionLog.domainType.name}', DomainId: '${actionLog.domainId}', Action: '${actionLog.actionType.name}'")
      }
      actionInsertFuture.onFailure {
        case e =>
          log.info(s"Action insert failed.Exception: '${e.toString}'")
      }

    case _ => log.error("ActionLogActor received invalid message.")
  }
}

object ActionLogActor
{
  private final val INSERT_QUERY =
    """
      |INSERT INTO action_log
      |(
      |   user_id,
      |   domain_type,
      |   domain_id,
      |   action_type,
      |   before,
      |   after,
      |   created_on
      |)  VALUES
      |(
      |   {userId}, {domainType}, {domainId}, {actionType},{before}::JSON,{after}::JSON, {createdOn}
      |)
    """.stripMargin
}
