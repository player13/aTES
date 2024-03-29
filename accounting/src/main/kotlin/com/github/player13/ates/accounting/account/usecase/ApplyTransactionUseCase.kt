package com.github.player13.ates.accounting.account.usecase

import com.github.player13.ates.accounting.account.Transaction
import com.github.player13.ates.accounting.account.UserAccount
import com.github.player13.ates.accounting.account.dao.TransactionRepository
import com.github.player13.ates.accounting.account.dao.UserAccountRepository
import com.github.player13.ates.accounting.account.event.TransactionEventProducer
import com.github.player13.ates.event.transaction.TransactionApplied
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ApplyTransactionUseCase(
    private val transactionEventProducer: TransactionEventProducer,
    private val userAccountRepository: UserAccountRepository,
    private val transactionRepository: TransactionRepository,
) {

    @Transactional
    fun apply(command: ApplyTransactionCommand) {
        val userAccount = userAccountRepository.findByUserIdWithLock(command.userId)
            ?.let { userAccountRepository.save(it.copy(balance = it.balance + command.amount)) }
            ?: error("User ${command.userId} not found")

        val transaction = transactionRepository.save(command.toTransaction(userAccount))
        transactionEventProducer.send(transaction.toEvent())
    }

    companion object {

        private fun ApplyTransactionCommand.toTransaction(userAccount: UserAccount) =
            Transaction(
                userAccount = userAccount,
                reason = reason,
                amount = amount,
                timestamp = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS),
            )

        private fun Transaction.toEvent() =
            TransactionApplied.newBuilder()
                .setUserId(userAccount.userId)
                .setReason(reason)
                .setAmount(amount)
                .setResultingBalance(userAccount.balance)
                .setTimestamp(timestamp.toInstant())
                .build()
    }
}

data class ApplyTransactionCommand(
    val userId: UUID,
    val amount: Long,
    val reason: String,
)
