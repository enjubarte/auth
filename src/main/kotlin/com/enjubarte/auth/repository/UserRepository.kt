package com.enjubarte.auth.repository

import com.enjubarte.auth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User,UUID> {
    fun findUserByEmailOrNull(email: String): User?
    fun existByEmail(email: String): Boolean
}