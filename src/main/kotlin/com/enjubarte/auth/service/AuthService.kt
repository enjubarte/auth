package com.enjubarte.auth.service

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.mapper.UserDTOToUser
import com.enjubarte.auth.util.JWTResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AuthService {
    private lateinit var jwtService: JWTService
    private lateinit var userService: UserService


    fun signIn(userDTO: UserDTO): ResponseEntity<Any> {
        return ResponseEntity(JWTResponse(jwtService.generateToken(UserDTOToUser().apply(userDTO).id)), HttpStatus.OK)
    }

    fun signUp(userDTO: UserDTO): ResponseEntity<Any>{
       return if (userService.userExists(userDTO))
            ResponseEntity("USUÁRIO JÁ EXISTE NO SISTEMA", HttpStatus.BAD_REQUEST)
       else
            ResponseEntity(userService.create(userDTO), HttpStatus.CREATED)
    }
}