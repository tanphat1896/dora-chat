package com.doradora.doraapi.security.jwt

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthFilter(
    private val userDetailsService: UserDetailsService,
    private val jwtTokenProvider: JwtTokenProvider
) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = getToken(request)
        if (token == null) {
            println("Invalid token")
            return filterChain.doFilter(request, response)
        }

        if (SecurityContextHolder.getContext().authentication != null) {
            return filterChain.doFilter(request, response)
        }

        val username = try {
            jwtTokenProvider.getUsernameFrom(token)
        } catch (e: Exception) {
            println("Cannot find username ==> ${e.message}")
            e.printStackTrace()
            return filterChain.doFilter(request, response)
        }


        val userDetail = userDetailsService.loadUserByUsername(username)
        if (jwtTokenProvider.isTokenExpired(token)) {
            println(">>> Token expired")
            return filterChain.doFilter(request, response)
        }

        UsernamePasswordAuthenticationToken(userDetail, null, userDetail.authorities).apply {
            this.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = this
        }
        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val authParam = request.getParameter("token")
        val authHeader = authParam ?: request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null
        }
        return authHeader.drop(7)
    }
}