package ru.hse.group_project.nasvazi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.http.HttpClient

@Configuration
class ApplicationConfig {
    @Bean
    fun httpClient(): HttpClient = HttpClient.newBuilder().build()
}
