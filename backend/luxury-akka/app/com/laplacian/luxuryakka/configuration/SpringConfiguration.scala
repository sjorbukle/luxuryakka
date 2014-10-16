package com.laplacian.luxuryakka.configuration

import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import play.api.db.DB

@Configuration
@ComponentScan(basePackages = Array("controllers", "com.toptal.expensetracker"))
@EnableTransactionManagement
class SpringConfiguration
{
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
  def jdbcTemplate =
  {
    new NamedParameterJdbcTemplate(dataSource)
  }

}
