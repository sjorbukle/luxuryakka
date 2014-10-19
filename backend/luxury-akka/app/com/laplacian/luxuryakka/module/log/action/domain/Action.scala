package com.laplacian.luxuryakka.module.log.action.domain

import com.laplacian.luxuryakka.core.Asserts
import org.joda.time.DateTime
import play.api.libs.json.{Json, Writes}

case class Action[TBefore: Writes, TAfter: Writes]
(
  id          : Option[Long] = None,
  userId      : Long,
  domainType  : DomainType,
  domainId    : Long,
  actionType  : String,
  before      : Option[TBefore],
  after       : Option[TAfter],
  createdOn   : DateTime = DateTime.now
)
{
  Asserts.argumentIsNotNull(id)
  Asserts.argumentIsNotNull(domainType)
  Asserts.argumentIsNotNullNorEmpty(actionType)
  Asserts.argumentIsNotNull(before)
  Asserts.argumentIsNotNull(after)
  Asserts.argumentIsNotNull(createdOn)

  lazy val beforeJson = Json.toJson(before)
  lazy val afterJson  = Json.toJson(after)
}
