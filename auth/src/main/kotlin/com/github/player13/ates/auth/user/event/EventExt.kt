package com.github.player13.ates.auth.user.event

import com.github.player13.ates.event.user.Role as RoleInEvent
import com.github.player13.ates.auth.user.Role

fun Role.toEvent() =
    when (this) {
        Role.ADMINISTRATOR -> RoleInEvent.ADMINISTRATOR
        Role.ACCOUNTANT -> RoleInEvent.ACCOUNTANT
        Role.MANAGER -> RoleInEvent.MANAGER
        Role.EMPLOYEE -> RoleInEvent.EMPLOYEE
    }
