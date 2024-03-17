package com.github.player13.ates.analytics.statistics.usecase

import com.github.player13.ates.analytics.statistics.CompanyProfit
import com.github.player13.ates.analytics.statistics.dao.CompanyProfitRepository
import java.time.LocalDate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CalculateCompanyProfitUseCase(
    private val companyProfitRepository: CompanyProfitRepository,
) {

    @Transactional
    fun calculate(command: CalculateCompanyProfitCommand) {
        val profit = companyProfitRepository.findByIdWithLock(command.date)
        companyProfitRepository.save(
            profit?.copy(profit = profit.profit)
                ?: CompanyProfit(command.date, command.profitChange)
        )
    }
}

data class CalculateCompanyProfitCommand(
    val date: LocalDate,
    val profitChange: Long,
)
