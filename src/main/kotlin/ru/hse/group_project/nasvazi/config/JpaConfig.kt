package ru.hse.group_project.nasvazi.config

import org.hibernate.jpa.HibernatePersistenceProvider
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableJpaRepositories(
    basePackages = ["ru.hse.group_project.nasvazi.repository"],
    entityManagerFactoryRef = "entityManagerFactory",
    transactionManagerRef = "transactionManager"
)
@EnableTransactionManagement
@EnableConfigurationProperties(JpaProperties::class)
open class JpaConfig(private val jpaProperties: JpaProperties) {

    @Bean
    open fun entityManagerFactory(dataSource: DataSource): LocalContainerEntityManagerFactoryBean {
        val factory = LocalContainerEntityManagerFactoryBean()
        factory.dataSource = dataSource
        factory.setPackagesToScan(
            "ru.yandex.market.wrap.eda.model.entity",
        )
        factory.persistenceProvider = HibernatePersistenceProvider()
        factory.jpaPropertyMap = jpaProperties.properties as Map<String, Any>
        factory.afterPropertiesSet()
        return factory
    }
}
