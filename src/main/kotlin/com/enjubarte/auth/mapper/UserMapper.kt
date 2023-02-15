package com.enjubarte.auth.mapper

import com.enjubarte.auth.dto.UserDTO
import com.enjubarte.auth.model.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
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

class DTOToUser: Function<UserDTO, User>{
    override fun apply(userDto: UserDTO): User {
        return User(
            UUID.randomUUID(),
            userDto.email,
            BCryptPasswordEncoder().encode(userDto.senha),
        )
    }
}