package com.laplacian.luxuryakka.module.generalsettings.dao

import com.laplacian.luxuryakka.module.generalsettings.domain.GeneralSettingsDetailsEntity

trait GeneralSettingsRepository
{
  def findById(id: Long): Option[GeneralSettingsDetailsEntity]
}
