package com.github.player13.ates.analytics.statistics.usecase

import com.github.player13.ates.analytics.statistics.dao.CompanyProfitRepository
import com.github.player13.ates.analytics.statistics.dao.MostExpensiveTaskRepository
import com.github.player13.ates.analytics.statistics.dao.NegativeBalanceCountRepository
import org.springframework.stereotype.Component

@Component
class GetStatisticsUseCase(
    private val companyProfitRepository: CompanyProfitRepository,
    private val mostExpensiveTaskRepository: MostExpensiveTaskRepository,
    private val negativeBalanceCountRepository: NegativeBalanceCountRepository,
) {

    fun get() =
        Triple(
            companyProfitRepository.findAll(),
            mostExpensiveTaskRepository.findAll(),
            negativeBalanceCountRepository.findAll(),
        )
}