package com.enjubarte.auth.mapper

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.model.User
import java.util.*
import java.util.function.Function

class UserToDTO: Function<User, UserDTO>{
    override fun apply(user: User): UserDTO {
        return UserDTO(
            user.email,
            user.senha
        )
    }
}

class UserDTOToUser: Function<UserDTO, User>{
    override fun apply(user: UserDTO): User {
        return User(
            UUID.randomUUID(),
            user.email,
            user.senha
        )
    }
}