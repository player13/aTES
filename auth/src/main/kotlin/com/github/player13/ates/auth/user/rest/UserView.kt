package com.github.player13.ates.auth.user.rest

import com.github.player13.ates.auth.user.Role
import java.util.UUID

data class UserView(
    val publicId: UUID,
    val login: String,
    val role: Role,
)
