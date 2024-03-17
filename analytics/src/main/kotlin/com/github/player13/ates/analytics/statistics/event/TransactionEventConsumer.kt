package com.github.player13.ates.analytics.statistics.event

import com.github.player13.ates.analytics.statistics.usecase.CalculateCompanyProfitCommand
import com.github.player13.ates.analytics.statistics.usecase.CalculateCompanyProfitUseCase
import com.github.player13.ates.analytics.statistics.usecase.CalculateMostExpensiveTaskCommand
import com.github.player13.ates.analytics.statistics.usecase.CalculateMostExpensiveTaskUseCase
import com.github.player13.ates.analytics.statistics.usecase.CalculateNegativeBalanceCountCommand
import com.github.player13.ates.analytics.statistics.usecase.CalculateNegativeBalanceCountUseCase
import com.github.player13.ates.event.transaction.TransactionApplied
import com.github.player13.ates.event.transaction.TransactionAppliedPayload
import com.github.player13.ates.event.transaction.TransactionAppliedVersion
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
            is TransactionApplied -> consume(event)
        }
    }

    private fun consume(event: TransactionApplied) {
        when (event.meta.version) {
            TransactionAppliedVersion.v1 -> consume(event.payload)
            else -> {}
        }
    }

    private fun consume(payload: TransactionAppliedPayload) {
        val date = LocalDate.ofInstant(payload.timestamp, ZoneOffset.UTC)

        calculateCompanyProfitUseCase.calculate(
            CalculateCompanyProfitCommand(
                date = date,
                profitChange = payload.amount,
            )
        )

        if (payload.amount > 0) {
            calculateMostExpensiveTaskUseCase.calculate(
                CalculateMostExpensiveTaskCommand(
                    date = date,
                    reward = payload.amount.toByte(),
                )
            )
        }

        calculateNegativeBalanceCountUseCase.calculate(
            CalculateNegativeBalanceCountCommand(
                date = date,
                userId = payload.userPublicId,
                balance = payload.resultingBalance,
            )
        )
    }
}
