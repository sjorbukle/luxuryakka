package com.laplacian.luxuryakka.module.generalsettings.service.domain

import com.laplacian.luxuryakka.module.generalsettings.domain.GeneralSettingsDetailsEntity

trait GeneralSettingsDomainService
{
  def getGeneralSettings: GeneralSettingsDetailsEntity
}
