package com.github.player13.ates.task.task.event

import com.github.player13.ates.task.task.Status
import java.util.UUID

data class TaskUpdatedEvent(
    override val id: UUID,
    override val status: Status,
) : TaskStreamingEvent {
    override val summary = null
    override val description = null
}
