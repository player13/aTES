package com.github.player13.ates.analytics.statistics.usecase

import com.github.player13.ates.analytics.statistics.AccountBalance
import com.github.player13.ates.analytics.statistics.NegativeBalanceCount
import com.github.player13.ates.analytics.statistics.dao.AccountBalanceRepository
import com.github.player13.ates.analytics.statistics.dao.NegativeBalanceCountRepository
import java.time.LocalDate
import java.util.UUID
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CalculateNegativeBalanceCountUseCase(
    private val accountBalanceRepository: AccountBalanceRepository,
    private val negativeBalanceCountRepository: NegativeBalanceCountRepository,
) {

    @Transactional
    fun calculate(command: CalculateNegativeBalanceCountCommand) {
        val accountBalance = accountBalanceRepository.findByIdWithLock(command.userId)
        if (accountBalance == null) {
            accountBalanceRepository.save(AccountBalance(command.userId, command.balance))
        }

        if ((accountBalance == null || accountBalance.balance >= 0) && command.balance < 0) {
            val negativeBalanceCount = negativeBalanceCountRepository.findByIdWithLock(command.date)
            negativeBalanceCountRepository.save(
                negativeBalanceCount?.copy(count = negativeBalanceCount.count + 1)
                    ?: NegativeBalanceCount(date = command.date, count = 1)
            )
        }
    }
}

data class CalculateNegativeBalanceCountCommand(
    val date: LocalDate,
    val userId: UUID,
    val balance: Long,
)
