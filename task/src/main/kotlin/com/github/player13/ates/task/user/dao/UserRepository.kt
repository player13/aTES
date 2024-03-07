package com.github.player13.ates.task.user.dao

import com.github.player13.ates.task.user.Role
import com.github.player13.ates.task.user.User
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, UUID> {

    fun findAllByRole(role: Role): List<User>
}