package com.github.player13.ates.auth.user.dao

import com.github.player13.ates.auth.user.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    fun findByLogin(login: String): User?
}