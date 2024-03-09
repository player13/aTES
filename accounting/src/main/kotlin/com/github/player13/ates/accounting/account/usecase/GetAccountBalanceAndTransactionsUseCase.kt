package com.github.player13.ates.accounting.account.usecase

import com.github.player13.ates.accounting.account.dao.TransactionRepository
import com.github.player13.ates.accounting.account.dao.UserAccountRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class GetAccountBalanceAndTransactionsUseCase(
    private val userAccountRepository: UserAccountRepository,
    private val transactionRepository: TransactionRepository,
) {

    fun get(command: GetAccountBalanceAndTransactionsCommand) =
        with(command) {
            val userAccount = userAccountRepository.findByIdOrNull(userId)
                ?: error("User $userId not found")
            val transactions = transactionRepository.findAllByUserAccountOrderByTimestampDesc(userAccount)
            userAccount to transactions
        }
}

data class GetAccountBalanceAndTransactionsCommand(
    val userId: UUID,
)