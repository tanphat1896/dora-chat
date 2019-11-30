package com.doradora.doraapi.service

import com.doradora.doraapi.repository.UserRepository
import com.doradora.doraapi.security.UserDetail
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository
            .findByUsername(username)
            .orElseThrow { UsernameNotFoundException(username) }
        return UserDetail(user)
    }
}