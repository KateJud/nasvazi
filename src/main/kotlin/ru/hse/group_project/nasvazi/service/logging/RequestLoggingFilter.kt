package ru.hse.group_project.nasvazi.service.logging

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.util.AntPathMatcher
import org.springframework.web.filter.AbstractRequestLoggingFilter

open class RequestLoggingFilter(
    private val dontLogUri: Set<String>,
    private val pathMatcher: AntPathMatcher = AntPathMatcher()
) : AbstractRequestLoggingFilter() {

    override fun shouldLog(request: HttpServletRequest): Boolean {
        val requestURI = request.requestURI
        return dontLogUri.none { pattern: String ->
            pathMatcher.match(
                pattern,
                requestURI
            )
        }
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        log.info(message)
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        log.info(message)
    }

    companion object {
        private val log = LoggerFactory.getLogger(RequestLoggingFilter::class.java)
    }
}
