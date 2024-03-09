package com.github.player13.ates.analytics.statistics

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.UUID

@Entity
data class AccountBalance(
    @Id
    val userId: UUID,
    val balance: Long,
)
