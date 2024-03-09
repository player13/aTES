package com.github.player13.ates.analytics.statistics.event

import com.github.player13.ates.analytics.statistics.usecase.CalculateCompanyProfitCommand
import com.github.player13.ates.analytics.statistics.usecase.CalculateCompanyProfitUseCase
import com.github.player13.ates.analytics.statistics.usecase.CalculateMostExpensiveTaskCommand
import com.github.player13.ates.analytics.statistics.usecase.CalculateMostExpensiveTaskUseCase
import com.github.player13.ates.analytics.statistics.usecase.CalculateNegativeBalanceCountCommand
import com.github.player13.ates.analytics.statistics.usecase.CalculateNegativeBalanceCountUseCase
import com.github.player13.ates.event.transaction.TransactionApplied
import java.time.LocalDate
import java.time.ZoneOffset
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TransactionEventConsumer(
    private val calculateCompanyProfitUseCase: CalculateCompanyProfitUseCase,
    private val calculateMostExpensiveTaskUseCase: CalculateMostExpensiveTaskUseCase,
    private val calculateNegativeBalanceCountUseCase: CalculateNegativeBalanceCountUseCase,
) {

    @KafkaListener(topics = ["\${event.transaction.business}"])
    fun receive(event: SpecificRecord) {
        when (event) {
            is TransactionApplied -> run {

                val date = LocalDate.ofInstant(event.timestamp, ZoneOffset.UTC)

                calculateCompanyProfitUseCase.calculate(
                    CalculateCompanyProfitCommand(
                        date = date,
                        profitChange = event.amount,
                    )
                )

                if (event.amount > 0) {
                    calculateMostExpensiveTaskUseCase.calculate(
                        CalculateMostExpensiveTaskCommand(
                            date = date,
                            reward = event.amount.toByte(),
                        )
                    )
                }

                calculateNegativeBalanceCountUseCase.calculate(
                    CalculateNegativeBalanceCountCommand(
                        date = date,
                        userId = event.userId,
                        balance = event.resultingBalance,
                    )
                )
            }
        }
    }
}
