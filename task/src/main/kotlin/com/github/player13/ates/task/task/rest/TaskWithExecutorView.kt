package com.github.player13.ates.task.task.rest

import com.github.player13.ates.task.task.Status
import java.util.UUID

data class TaskWithExecutorView(
    val publicId: UUID,
    val summary: String,
    val description: String,
    val status: Status,
    val executor: ExecutorUserView,
) {
    data class ExecutorUserView(
        val publicId: UUID,
        val login: String,
    )
}
