package controllers.api.v1

import com.laplacian.luxuryakka.core.Asserts
import com.laplacian.luxuryakka.core.response.ResponseTools
import com.laplacian.luxuryakka.module.authentication.service.AuthenticationService
import com.laplacian.luxuryakka.module.generalsettings.service.domain.GeneralSettingsDomainService
import controllers.core.SecuredController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@stereotype.Controller
class GeneralSettingsController @Autowired
(
  private val generalSettingsDomainService: GeneralSettingsDomainService
)
(
  implicit private val authenticationService: AuthenticationService
) extends SecuredController
{
  Asserts.argumentIsNotNull(generalSettingsDomainService)
  Asserts.argumentIsNotNull(authenticationService)

  def read = AuthenticatedAction {
    request =>
      val item = this.generalSettingsDomainService.getGeneralSettings

      Future(Ok(ResponseTools.data(item).json))
  }
}
