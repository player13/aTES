package com.github.player13.ates.accounting.task

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class Task(
    @Id
    val id: UUID,
    val summary: String,
    val description: String,
    val assignmentFee: Byte,
    val completionReward: Byte,
)
