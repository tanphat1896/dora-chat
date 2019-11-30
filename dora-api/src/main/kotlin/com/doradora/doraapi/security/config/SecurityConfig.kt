package com.doradora.doraapi.security.config

import com.doradora.doraapi.security.jwt.JwtAuthEntryPoint
import com.doradora.doraapi.security.jwt.JwtAuthFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import java.util.*


@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val authEntryPoint: JwtAuthEntryPoint,
    private val userDetailsService: UserDetailsService,
    private val jwtAuthFilter: JwtAuthFilter
) : WebSecurityConfigurerAdapter() {

    @Bean fun passwordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean override fun authenticationManagerBean(): AuthenticationManager =
        super.authenticationManagerBean()

    @Autowired fun configureGlobal(authMan: AuthenticationManagerBuilder) =
        authMan.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())

    override fun configure(http: HttpSecurity) {
        http.cors()
        http.csrf().disable()
        http.authorizeRequests()
            .antMatchers("/auth", "/ws-dora/**").permitAll()
            .anyRequest().authenticated()

        http.exceptionHandling().authenticationEntryPoint(authEntryPoint)

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}