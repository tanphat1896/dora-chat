package com.doradora.doraapi.repository

import com.doradora.doraapi.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}