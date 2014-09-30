import java.util.TimeZone

import com.laplacian.luxuryakka.core.communication.CORSFilter
import org.joda.time.DateTimeZone
import play.api.mvc.WithFilters
import play.api.{Application, GlobalSettings}

object Global extends WithFilters(CORSFilter) with GlobalSettings
{
  override def beforeStart(app: Application)
  {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    DateTimeZone.setDefault(DateTimeZone.UTC)
  }
}
