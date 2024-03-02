package com.github.player13.ates.auth.user.event

import com.github.player13.ates.auth.user.Role
import java.util.UUID

data class UserRegisteredEvent(
    override val id: UUID,
    val login: String,
    val role: Role,
) : UserBusinessEvent
