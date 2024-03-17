package com.github.player13.ates.analytics.statistics

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
data class CompanyProfit(
    @Id
    val date: LocalDate,
    val profit: Long,
)
