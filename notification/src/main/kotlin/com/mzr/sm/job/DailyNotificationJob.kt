package com.mzr.sm.job

import com.mzr.sm.security.SecurityServiceImpl
import mu.KotlinLogging
import org.apache.commons.lang3.time.DateUtils
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class DailyNotificationJob(
    val securityService: SecurityServiceImpl
) {

    private val logger = KotlinLogging.logger {  }

    @Scheduled(fixedDelay = DateUtils.MILLIS_PER_DAY)
    fun process() {
        securityService.authenticateAsServiceAccount()

        val restTemplate = RestTemplate()
        val response = restTemplate.exchange(
            "http://localhost:8087/internal/users",
            HttpMethod.GET,
            HttpEntity<Any>(securityService.createHeadersWithAuthentication()),
            object : ParameterizedTypeReference<List<String>>() {})
        logger.info { "TODO: notify user: ${response.body}" }
    }
}