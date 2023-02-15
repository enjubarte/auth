package com.enjubarte.auth.service

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.mapper.DTOToUser
import com.enjubarte.auth.model.User
import com.enjubarte.auth.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtService: JWTService
): UserDetailsService{


    fun findAll(): ResponseEntity<Any> = ResponseEntity(userRepository.findAll(), HttpStatus.OK)

    fun findById(id: UUID): ResponseEntity<Any> =
        ResponseEntity(userRepository.findById(id).get(), HttpStatus.OK)

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)

    fun create(userDTO: UserDTO): ResponseEntity<Any> = ResponseEntity(userRepository.save(DTOToUser().apply(userDTO)), HttpStatus.CREATED)

    fun update(id: UUID, token: String, userDTO: UserDTO): ResponseEntity<Any> {
        return if (userRepository.existsById(id) && jwtService.isValid(token, userRepository.findById(id)))
           ResponseEntity(userRepository.save(DTOToUser().apply(userDTO)), HttpStatus.OK)
        else ResponseEntity("USER NOT FOUND", HttpStatus.NOT_FOUND)
    }

    fun delete(id: UUID, token: String): ResponseEntity<Any> {
       return if (userRepository.existsById(id) && jwtService.isValid(token, userRepository.findById(id))){
           userRepository.deleteById(jwtService.getIdByToken(token))
           ResponseEntity(HttpStatus.NO_CONTENT)
       }
       else ResponseEntity("USER NOT FOUND", HttpStatus.NOT_FOUND)
    }

    fun userExists(userDTO: UserDTO): Boolean = userRepository.findByEmail(userDTO.email) != null

    override fun loadUserByUsername(email: String?): User? =
        email?.let { userRepository.findByEmail(it) }
}