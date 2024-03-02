package com.github.player13.ates.task.task.rest

import com.github.player13.ates.task.task.Status
import java.util.UUID

data class TaskWithExecutorView(
    val id: UUID,
    val summary: String,
    val description: String,
    val status: Status,
    val executor: ExecutorView,
) {
    data class ExecutorView(
        val id: UUID,
        val login: String,
    )
}
