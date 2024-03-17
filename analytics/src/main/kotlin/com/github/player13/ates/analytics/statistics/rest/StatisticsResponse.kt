package com.github.player13.ates.analytics.statistics.rest

import java.time.LocalDate

data class StatisticsResponse(
    val companyProfits: Map<LocalDate, Long>,
    val mostExpensiveTasks: Map<LocalDate, Byte>,
    val negativeBalanceCounts: Map<LocalDate, Long>,
)