package com.laplacian.luxuryakka.configuration.actor

import akka.actor.{Props, ActorSystem}
import akka.routing.FromConfig
import com.laplacian.luxuryakka.module.log.action.actor.ActionLogActor

object ActorFactory
{
  val actorSystem = ActorSystem("luxuryakka")

  val actionLogActorRouter = actorSystem.actorOf(Props[ActionLogActor].withRouter(FromConfig()), name = "actionLogActorRouter")
}
