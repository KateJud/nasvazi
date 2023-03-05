package ru.hse.group_project.nasvazi.config

import org.springframework.security.core.context.SecurityContextHolder

// @Configuration
// open class SecurityConfig : GlobalMethodSecurityConfiguration() {
// }

val currentUser: String
    get() = (SecurityContextHolder.getContext()?.authentication?.principal as String?) ?: "TEST"
