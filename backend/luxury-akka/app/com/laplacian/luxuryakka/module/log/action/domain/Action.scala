package com.laplacian.luxuryakka.module.log.action.domain

import com.laplacian.luxuryakka.core.Asserts
import org.joda.time.DateTime
import play.api.libs.json.{Json, Writes}

case class Action[TBefore: Writes, TAfter: Writes]
(
  actionId     : Option[Long] = None,
  userId       : Long,
  actionDomain : String,
  domainId     : Long,
  actionType   : String,
  before       : Option[TBefore],
  after        : Option[TAfter],
  createdOn    : DateTime = DateTime.now
)
{
  Asserts.argumentIsNotNull(actionId)
  Asserts.argumentIsNotNull(userId)
  Asserts.argumentIsNotNullNorEmpty(actionDomain)
  Asserts.argumentIsNotNull(domainId)
  Asserts.argumentIsNotNullNorEmpty(actionType)
  Asserts.argumentIsNotNull(before)
  Asserts.argumentIsNotNull(after)
  Asserts.argumentIsNotNull(createdOn)

  lazy val beforeJson = Json.toJson(before)
  lazy val afterJson  = Json.toJson(after)
}
