package com.github.player13.ates.auth.user

import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "\"user\"")
data class User(
    @Id
    val id: UUID,
    val login: String,
    val password: String,
    @Enumerated(STRING)
    val role: Role,
)
