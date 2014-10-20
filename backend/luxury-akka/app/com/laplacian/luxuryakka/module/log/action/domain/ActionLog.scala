package com.laplacian.luxuryakka.module.log.action.domain

import com.laplacian.luxuryakka.core.Asserts
import org.joda.time.DateTime
import play.api.libs.json.JsValue

case class ActionLog
(
  id          : Option[Long] = None,
  userId      : Long,
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
