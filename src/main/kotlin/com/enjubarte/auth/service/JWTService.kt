package com.enjubarte.auth.service


import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.enjubarte.auth.model.User
import org.springframework.stereotype.Service
import java.util.*
import java.util.concurrent.TimeUnit

@Service
class JWTService {
    private val secret: String = UUID.randomUUID().toString()

    fun generateToken(id: UUID): String{
        return JWT.create()
            .withSubject(id.toString())
            .withExpiresAt(Date(System.currentTimeMillis() + TimeUnit.HOURS.toMillis(24)))
            .sign(Algorithm.HMAC512(secret))
    }

    fun getIdByToken(token: String): UUID{
        return UUID.fromString(JWT.decode(token.replace("Bearer ", "")).subject)
    }

    private  fun isExpired(token: String): Boolean = JWT.decode(token).expiresAt.before(Date())

    fun isValid(token: String, user: Optional<User>?): Boolean = getIdByToken(token) == user?.get()?.id && isExpired(token)

}