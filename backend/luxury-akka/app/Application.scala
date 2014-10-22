import akka.actor.{Props, ActorSystem}
import akka.routing.FromConfig
import com.laplacian.luxuryakka.module.log.action.actor.ActionLogActor

object Application extends App
{
  val actorSystem = ActorSystem("aaaaa")
  actorSystem.actorOf(Props[ActionLogActor].withRouter(FromConfig()), name = "actionLogActorRouter")
}
