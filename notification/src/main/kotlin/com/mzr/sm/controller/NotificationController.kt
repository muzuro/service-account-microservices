package com.mzr.sm.controller

import com.mzr.sm.security.service.SecurityServiceImpl
import mu.KotlinLogging
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class NotificationController(
    val securityService: SecurityServiceImpl
) {

    private val logger = KotlinLogging.logger {  }

    @PostMapping("/verifyEmail")
    fun verifyEmail() {
        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(
            "http://localhost:8087/internal/userDetails",
            HttpMethod.GET,
            HttpEntity<Any>(securityService.createHeadersWithAuthentication()),
            object : ParameterizedTypeReference<String>() {})
        logger.info { "TODO: sent verify email to ${response.body}" }
    }
}