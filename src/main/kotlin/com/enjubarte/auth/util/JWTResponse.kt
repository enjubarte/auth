package com.enjubarte.auth.util

import java.io.Serializable

data class JWTResponse(
    val token: String
): Serializable
