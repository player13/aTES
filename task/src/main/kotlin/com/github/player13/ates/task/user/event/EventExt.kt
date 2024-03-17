package com.github.player13.ates.task.user.event

import com.github.player13.ates.event.user.Role as RoleInEvent
import com.github.player13.ates.task.user.Role

fun RoleInEvent.toDomain() =
    when (this) {
        RoleInEvent.ADMINISTRATOR -> Role.ADMINISTRATOR
        RoleInEvent.ACCOUNTANT -> Role.ACCOUNTANT
        RoleInEvent.MANAGER -> Role.MANAGER
        RoleInEvent.EMPLOYEE -> Role.EMPLOYEE
    }
