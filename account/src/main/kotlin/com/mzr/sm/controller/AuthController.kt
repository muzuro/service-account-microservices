package com.mzr.sm.controller

import com.mzr.sm.domain.dto.LoginResult
import com.mzr.sm.jwt.JwtHelper
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class AuthController(
    private val jwtHelper: JwtHelper,
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) {
    @PostMapping(path = ["login"], consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE])
    fun login(
        @RequestParam username: String,
        @RequestParam password: String
    ): LoginResult {
        val userDetails = try {
            userDetailsService.loadUserByUsername(username)
        } catch (e: UsernameNotFoundException) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found")
        }
        if (passwordEncoder.matches(password, userDetails.password)) {
            val claims: MutableMap<String, String> = HashMap()
            claims["username"] = username
            val authorities = userDetails.authorities.joinToString { it.authority.toString() }
            claims["authorities"] = authorities
            claims["userId"] = 1.toString()
            val jwt = jwtHelper.createJwtForClaims(username, claims)
            return LoginResult(jwt)
        }
        throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated")
    }
}