package com.enjubarte.auth.dto

import jakarta.annotation.Nonnull
import java.io.Serializable

data class UserDTO(
    @Nonnull
    val email: String,
    @Nonnull
    val senha: String,
): Serializable
