import java.util.TimeZone

import com.laplacian.luxuryakka.core.communication.CORSFilter
import com.laplacian.luxuryakka.configuration.SpringConfiguration
import org.joda.time.DateTimeZone
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import play.api.mvc.WithFilters
import play.api.{Application, GlobalSettings}

object Global extends WithFilters(CORSFilter) with GlobalSettings
{
  val context = new AnnotationConfigApplicationContext

  override def beforeStart(app: Application)
  {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    DateTimeZone.setDefault(DateTimeZone.UTC)
  }

  override def onStart(app: play.api.Application)
  {
    context.register(classOf[SpringConfiguration])
    context.refresh
    context.start
  }

  override def onStop(app: play.api.Application)
  {
    context.stop
  }

  override def getControllerInstance[A](controllerClass: Class[A]): A =
  {
    context.getBean(controllerClass)
  }
}
