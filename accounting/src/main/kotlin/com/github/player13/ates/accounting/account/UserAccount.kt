package com.github.player13.ates.accounting.account

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class UserAccount(
    @Id
    val userId: UUID,
    val balance: Long,
)
