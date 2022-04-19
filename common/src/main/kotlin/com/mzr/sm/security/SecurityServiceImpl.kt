package com.mzr.sm.security

import com.mzr.sm.security.service.ServiceAuthenticationProvider
import com.mzr.sm.security.service.ServiceAuthenticationToken
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Component

@Component
class SecurityServiceImpl(
    @Value("\${app.security.service.token}")
    private val serviceToken: String,
    private val authenticationManager: ServiceAuthenticationProvider,
) {

    fun authenticateAsServiceAccount() {
        val authenticationResult = authenticationManager.authenticate(ServiceAuthenticationToken(serviceToken))
        val context = SecurityContextHolder.createEmptyContext()
        context.authentication = authenticationResult
        SecurityContextHolder.setContext(context)
    }

    fun createHeadersWithAuthentication(): HttpHeaders {
        val headers = HttpHeaders()
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication is ServiceAuthenticationToken) {
            headers.set("Authorization", "Service ${authentication.token}")
        } else if (authentication is JwtAuthenticationToken) {
            headers.set("Authorization", "Bearer ${authentication.token.tokenValue}")
        }
        return headers
    }

}