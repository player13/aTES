package com.github.player13.ates.task.task

import com.github.player13.ates.task.user.User
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.util.UUID

@Entity
data class Task(
    val publicId: UUID,
    val summary: String,
    val description: String,
    @Enumerated(STRING)
    val status: Status,
    @ManyToOne
    @JoinColumn(name = "executor_user_id")
    val executor: User,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
