package com.enjubarte.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.io.Serializable

data class UserDTO(

    @Email
    @NotBlank(message = "Email Obrigatório!")
    val email: String,

    @NotBlank(message = "Senha Obrigatória!")
    val senha: String,

): Serializable
