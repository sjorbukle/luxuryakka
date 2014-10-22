package com.laplacian.luxuryakka.module.log.action.domain

import com.laplacian.luxuryakka.core.Asserts
import org.joda.time.DateTime
import play.api.libs.json.{Json, Writes, JsValue}

case class ActionLogEntity
(
  id          : Option[Long] = None,
  userId      : Option[Long],
  domainType  : ActionDomainType,
  domainId    : Long,
  actionType  : ActionType,
  before      : Option[JsValue],
  after       : Option[JsValue],
  createdOn   : DateTime = DateTime.now
)
{
  Asserts.argumentIsNotNull(id)
  Asserts.argumentIsNotNull(domainType)
  Asserts.argumentIsNotNull(actionType)
  Asserts.argumentIsNotNull(before)
  Asserts.argumentIsNotNull(after)
  Asserts.argumentIsNotNull(createdOn)
}

object ActionLogEntity
{
  def of[TBefore: Writes, TAfter: Writes]
  (
    userId      : Option[Long],
    domainType  : ActionDomainType,
    domainId    : Long,
    actionType  : ActionType,
    before      : Option[TBefore],
    after       : Option[TAfter]
  ): ActionLogEntity =
  {
    Asserts.argumentIsNotNull(userId)
    Asserts.argumentIsNotNull(domainType)
    Asserts.argumentIsNotNull(actionType)
    Asserts.argumentIsNotNull(before)
    Asserts.argumentIsNotNull(after)

    ActionLogEntity(
      userId      = userId,
      domainType  = domainType,
      domainId    = domainId,
      actionType  = actionType,
      before      = if(before.isDefined) Some(Json.toJson(before.get)) else Option.empty[JsValue],
      after       = if(after.isDefined) Some(Json.toJson(after.get)) else Option.empty[JsValue]
    )
  }
}
