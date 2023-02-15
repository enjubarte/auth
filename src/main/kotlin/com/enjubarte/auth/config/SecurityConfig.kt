package com.enjubarte.auth.config

import com.enjubarte.auth.filter.JWTAuthenticationFilter
import com.enjubarte.auth.service.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private val service: UserService
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http.csrf().disable()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

        http.authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll()
            .anyRequest().authenticated().and()

        http.authenticationProvider(authenticationProvider())

        http.addFilterBefore(JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val auth = DaoAuthenticationProvider()
        auth.setUserDetailsService(service)
        auth.setPasswordEncoder(crypt())
        return auth
    }

    @Bean
    fun autheticationManager(config: AuthenticationConfiguration): AuthenticationManager
        = config.authenticationManager

    @Bean
    fun crypt(): BCryptPasswordEncoder = BCryptPasswordEncoder()
}