package com.doradora.doraapi.service

import com.doradora.doraapi.data.request.LoginCredential
import com.doradora.doraapi.security.jwt.JwtTokenProvider
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService,
    private val authenticationManager: AuthenticationManager
) {

    fun login(cre: LoginCredential): String {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(cre.username, cre.password))
        val userDetail = userDetailsService.loadUserByUsername(cre.username)
        return jwtTokenProvider.generateToken(userDetail)
    }
}