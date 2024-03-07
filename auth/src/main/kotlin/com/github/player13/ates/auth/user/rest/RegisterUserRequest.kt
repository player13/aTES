package com.github.player13.ates.auth.user.rest

import com.github.player13.ates.auth.user.Role

data class RegisterUserRequest(
    val login: String,
    val password: String,
    val role: Role,
)
