package com.laplacian.luxuryakka.configuration

import akka.actor.{Props, ActorSystem}
import akka.routing.FromConfig
import com.laplacian.luxuryakka.module.log.action.actor.ActionLogActor
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import play.api.db.DB

@Configuration
@ComponentScan(basePackages = Array("controllers", "com.laplacian.luxuryakka"))
@EnableTransactionManagement
class SpringConfiguration
{
  val actorSystem = ActorSystem("luxuryakka")
  actorSystem.actorOf(Props[ActionLogActor].withRouter(FromConfig()), name = "actionLogActorRouter")

  @Bean
  def dataSource =
  {
    import play.api.Play.current
    DB.getDataSource()
  }

  @Bean
  def transactionManager =
  {
    val transactionManager = new DataSourceTransactionManager
    transactionManager.setDataSource(dataSource)
    transactionManager
  }

  @Bean
  def luxuryAkkaActorSystem() =
  {
    this.actorSystem
  }
}
