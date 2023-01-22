package com.enjubarte.auth.filter

import com.enjubarte.auth.repository.UserRepository
import com.enjubarte.auth.service.JWTService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter

class JWTAuthenticationFilter: OncePerRequestFilter() {
    private lateinit var jwtService: JWTService
    private lateinit var userRepository: UserRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val header: String? = request.getHeader("Authorization")

        if (header == null || !header.startsWith("Bearer ") )
            filterChain.doFilter(request,response)

        val token = header?.substring(7)
        val userID = token?.let { jwtService.getIdByToken(it) }

        if (userID != null && SecurityContextHolder.getContext() == null){
            val user = userRepository.findById(userID)
            if (user.isPresent) {
                val auth = UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.get().authorities
                )
                auth.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = auth
                filterChain.doFilter(request, response)
            }
        }

        filterChain.doFilter(request,response)
    }
}