package com.laplacian.luxuryakka.module.generalsettings.domain

import play.api.libs.json.Json

case class BusinessData(profitPercentage: Int)

object BusinessData
{
  implicit val jsonFormat = Json.format[BusinessData]
}
