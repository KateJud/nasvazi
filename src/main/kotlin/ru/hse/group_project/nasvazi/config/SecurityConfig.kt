package ru.hse.group_project.nasvazi.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration

@Configuration
open class SecurityConfig : GlobalMethodSecurityConfiguration() {
}
