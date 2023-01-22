package com.enjubarte.auth.mapper

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.model.User
import java.util.*

fun User.toDTO() = UserDTO(this.email,this.senha)

fun UserDTO.toUser() = User(UUID.randomUUID(), this.email, this.senha)