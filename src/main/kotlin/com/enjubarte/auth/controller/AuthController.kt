package com.enjubarte.auth.controller

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.service.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController {
    private lateinit var authService: AuthService

    @PostMapping("/sing-in")
    fun signIn(@RequestBody() userDTO: UserDTO) = authService.signIn(userDTO)

    @PostMapping("/sign-up")
    fun signUp(@RequestBody() userDTO: UserDTO) = authService.signUp(userDTO)
}