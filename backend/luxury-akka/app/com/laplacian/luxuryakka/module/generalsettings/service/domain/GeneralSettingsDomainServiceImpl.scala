package com.laplacian.luxuryakka.module.generalsettings.service.domain

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.module.generalsettings.dao.GeneralSettingsRepository
import com.laplacian.luxuryakka.module.generalsettings.domain.GeneralSettingsDetailsEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GeneralSettingsDomainServiceImpl @Autowired
(
  private final val entityRepository: GeneralSettingsRepository
) extends GeneralSettingsDomainService
{
  Asserts.argumentIsNotNull(entityRepository)

  def getGeneralSettings: GeneralSettingsDetailsEntity =
  {
    this.entityRepository.findById(GeneralSettingsDetailsEntity.SINGLETON_ID)
    .getOrElse(throw new RuntimeException(s"GeneralSettings with this id: ${GeneralSettingsDetailsEntity.SINGLETON_ID} does not exist"))
  }
}
