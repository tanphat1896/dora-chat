package com.doradora.doraapi.controller

import com.doradora.doraapi.data.request.LoginCredential
import com.doradora.doraapi.data.response.AuthenticatedToken
import com.doradora.doraapi.service.AuthService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@CrossOrigin
@RestController
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/auth")
    fun login(
        @RequestBody credential: LoginCredential,
        res: HttpServletResponse
    ): AuthenticatedToken {
        val token = authService.login(credential)
//        res.setHeader("Token", token)
        return AuthenticatedToken(token)
    }
}