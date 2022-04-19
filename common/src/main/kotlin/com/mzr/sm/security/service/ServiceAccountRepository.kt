package com.mzr.sm.security.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ServiceAccountRepository(
    @Value("\${app.security.service.token}")
    private val serviceToken: String,
) {

    val storage = listOf(ServiceAccount(serviceToken, listOf("users.read")))

    fun findServiceAccount(token: String) = storage.firstOrNull { it.token == token }

}