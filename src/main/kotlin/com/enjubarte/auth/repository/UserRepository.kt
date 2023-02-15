package com.enjubarte.auth.repository

import com.enjubarte.auth.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User,UUID> {
    fun findByEmail(email: String): User?
}