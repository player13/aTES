package com.github.player13.ates.accounting.account

import com.github.player13.ates.accounting.account.usecase.PayoutUseCase
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CloseBillingCycleScheduler(
    private val payoutUseCase: PayoutUseCase,
) {

    @Scheduled(cron = "@midnight")
    fun close() {
        payoutUseCase.payout()
    }
}