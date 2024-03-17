package com.github.player13.ates.accounting.account.usecase

import com.github.player13.ates.accounting.account.dao.UserAccountRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PayoutUseCase(
    private val sendPayslipByEmailUseCase: SendPayslipByEmailUseCase,
    private val applyTransactionUseCase: ApplyTransactionUseCase,
    private val userAccountRepository: UserAccountRepository,
) {

    @Transactional
    fun payout() {
        userAccountRepository.findAllByPositiveBalanceWithLock()
            .onEach {
                sendPayslipByEmailUseCase.send(
                    SendPayslipByEmailCommand(
                        userPublicId = it.userPublicId,
                        amount = it.balance,
                    )
                )
            }
            .forEach {
                applyTransactionUseCase.apply(
                    ApplyTransactionCommand(
                        userPublicId = it.userPublicId,
                        taskPublicId = null,
                        amount = -it.balance,
                        reason = "Выплата заработной платы",
                    )
                )
            }
    }
}