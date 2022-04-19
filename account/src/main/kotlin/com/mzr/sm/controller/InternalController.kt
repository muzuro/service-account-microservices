package com.mzr.sm.controller

import com.mzr.sm.service.UserServiceImpl
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/internal")
class InternalController(
    val userService: UserServiceImpl
) {

    @GetMapping("/userDetails")
    fun getUser() = userService.getUser()

    @GetMapping("/users")
    fun getUsers() = userService.getUsers()
}