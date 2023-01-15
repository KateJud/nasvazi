package ru.hse.group_project.nasvazi.service.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.filter.OncePerRequestFilter

class OauthAuthenticationFilter(
) : OncePerRequestFilter() {
    // todo  private val log = KotlinLogging.logger {}

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
    // todo Implement
        chain.doFilter(request, response)
    }
}
