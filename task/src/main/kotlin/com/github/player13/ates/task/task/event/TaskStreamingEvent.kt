package com.github.player13.ates.task.task.event

import com.github.player13.ates.task.task.Status
import java.util.UUID

interface TaskStreamingEvent {
    val id: UUID
    val summary: String? // null if not changed
    val description: String? // null if not changed
    val status: Status? // null if not changed
}
