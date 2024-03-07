package com.github.player13.ates.task.task.event

import com.github.player13.ates.task.task.Status
import java.util.UUID

data class TaskCreatedEvent(
    override val id: UUID,
    override val summary: String,
    override val description: String,
    override val status: Status,
) : TaskStreamingEvent
