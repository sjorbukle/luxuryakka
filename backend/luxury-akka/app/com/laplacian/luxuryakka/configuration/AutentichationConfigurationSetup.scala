package com.laplacian.luxuryakka.configuration

import com.laplacian.luxuryakka.core.utils.ConfigurationUtils
import com.laplacian.luxuryakka.module.authentication.domain.configuration.AuthenticationConfiguration
import org.springframework.stereotype.Component

@Component
class AutentichationConfigurationSetup extends AuthenticationConfiguration(
  secret            = ConfigurationUtils.getConfigurationByKey[String]("jwt.token.secret"),
  tokenHoursToLive  = ConfigurationUtils.getConfigurationByKey[Int]("jwt.token.hoursToLive")
)
