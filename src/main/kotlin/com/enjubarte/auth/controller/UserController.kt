package com.enjubarte.auth.controller

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.service.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(private val userService: UserService) {

    @GetMapping("/{id}")
    fun findByID(@PathVariable("id") id: String) = userService.findById(UUID.fromString(id))

    @GetMapping("/search")
    fun findByEmail(@RequestParam("email") email: String) = userService.findByEmail(email)

    @GetMapping
    fun findAll() = userService.findAll()

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: String, @RequestHeader("Authorization") token: String, @RequestBody userDTO: UserDTO) = userService.update(UUID.fromString(id), token, userDTO)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: String, @RequestHeader("Authorization") token: String) = userService.delete(UUID.fromString(id), token)
}