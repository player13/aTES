package com.github.player13.ates.task.task.event

import java.util.UUID

data class TaskCompletedEvent(
    override val id: UUID,
): TaskBusinessEvent