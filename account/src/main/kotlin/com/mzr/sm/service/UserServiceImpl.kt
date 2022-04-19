package com.mzr.sm.service

import mu.KotlinLogging
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class UserServiceImpl {

    private val logger = KotlinLogging.logger {  }

    @PreAuthorize("hasAuthority('user.read')")
    fun getUser(): String {
        logger.info { "TODO: obtain user name for user" }
        return "John Doe"
    }

    @PreAuthorize("hasAuthority('users.read')")
    fun getUsers(): List<String> {
        logger.info { "TODO: obtain all users" }
        return listOf("user@mail.com")
    }

}