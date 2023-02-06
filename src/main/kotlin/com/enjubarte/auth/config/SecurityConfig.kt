package com.enjubarte.auth.config

import com.enjubarte.auth.filter.JWTAuthenticationFilter
import com.enjubarte.auth.repository.UserRepository
import com.enjubarte.auth.service.UserService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig {

    @field: Autowired
    private lateinit var service: UserService
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http.csrf().disable()

        http.exceptionHandling().authenticationEntryPoint { _, response, _ ->
               response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"UNAUTHORIZED")
            }.and()

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

        http.authorizeHttpRequests().requestMatchers("/api/v1/auth/**").permitAll()
            .anyRequest().authenticated().and()

        http.addFilterBefore(JWTAuthenticationFilter(), BasicAuthenticationFilter::class.java)
        return http.build()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider {
        val auth = DaoAuthenticationProvider()
        auth.setUserDetailsService(service)
        auth.setPasswordEncoder(crypt())
        return auth
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager{
        return config.authenticationManager
    }

    @Bean
    fun crypt() = BCryptPasswordEncoder()
}