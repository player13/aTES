package com.github.player13.ates.auth.user.event

import com.github.player13.ates.auth.user.Role
import java.util.UUID

interface UserStreamingEvent {
    val id: UUID
    val login: String? // null if not changed
    val role: Role? // null if not changed
}
