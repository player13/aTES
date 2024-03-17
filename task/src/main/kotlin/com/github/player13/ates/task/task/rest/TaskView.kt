package com.github.player13.ates.task.task.rest

import com.github.player13.ates.task.task.Status
import java.util.UUID

data class TaskView(
    val publicId: UUID,
    val summary: String,
    val description: String,
    val status: Status,
)
