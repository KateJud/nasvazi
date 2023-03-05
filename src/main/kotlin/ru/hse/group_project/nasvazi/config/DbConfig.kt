package ru.hse.group_project.nasvazi.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class DbConfig {
    @Bean
    @Qualifier("pg")
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSourceProperties(): DataSourceProperties = DataSourceProperties()

    @Bean
    @Qualifier("pg")
    @Primary
    fun dataSource(): DataSource = dataSourceProperties().initializeDataSourceBuilder().build()

    @Bean
    @Qualifier("pg")
    fun namedParameterJdbcTemplate(
        @Qualifier("pg") dataSource: DataSource
    ): NamedParameterJdbcTemplate = NamedParameterJdbcTemplate(dataSource)
}
