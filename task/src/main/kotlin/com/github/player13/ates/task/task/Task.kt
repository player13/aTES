package com.github.player13.ates.task.task

import com.github.player13.ates.task.user.User
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
data class Task(
    @Id
    val id: UUID,
    val summary: String,
    val description: String,
    @Enumerated(STRING)
    val status: Status,
    @ManyToOne
    @JoinColumn
    val executor: User,
)
