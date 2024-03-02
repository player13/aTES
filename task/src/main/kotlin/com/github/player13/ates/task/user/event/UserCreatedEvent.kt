package com.github.player13.ates.task.user.event

import com.github.player13.ates.task.user.Role
import java.util.UUID

data class UserCreatedEvent(
    override val id: UUID,
    override val login: String,
    override val role: Role,
) : UserStreamingEvent
