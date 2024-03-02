package com.github.player13.ates.task.task.event

import java.util.UUID

data class TaskAssignedEvent(
    override val id: UUID,
    val executorUserId: UUID,
): TaskBusinessEvent