package com.enjubarte.auth.service

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.model.User
import com.enjubarte.auth.util.JWTResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtService: JWTService,
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager
) {

    fun signIn(userDTO: UserDTO): ResponseEntity<Any> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userDTO.email,
                userDTO.senha
            )
        )

        val user: User? = userService.findByEmail(userDTO.email)

        return  if (user != null) ResponseEntity(
                JWTResponse(jwtService.generateToken(user.id)),
                HttpStatus.OK
            )
        else ResponseEntity("DEU MERDA AMIGO!!!!", HttpStatus.BAD_REQUEST)

    }

    fun signUp(userDTO: UserDTO): ResponseEntity<Any>{
       return if (userService.userExists(userDTO)) ResponseEntity("USUÁRIO JÁ EXISTE NO SISTEMA", HttpStatus.BAD_REQUEST)
       else userService.create(userDTO)
    }
}