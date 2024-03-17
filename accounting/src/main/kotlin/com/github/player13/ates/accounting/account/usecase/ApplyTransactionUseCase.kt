package com.github.player13.ates.accounting.account.usecase

import com.github.player13.ates.accounting.account.Transaction
import com.github.player13.ates.accounting.account.UserAccount
import com.github.player13.ates.accounting.account.dao.TransactionRepository
import com.github.player13.ates.accounting.account.dao.UserAccountRepository
import com.github.player13.ates.accounting.account.event.TransactionEventProducer
import com.github.player13.ates.accounting.task.Task
import com.github.player13.ates.accounting.task.dao.TaskRepository
import com.github.player13.ates.event.transaction.TransactionApplied
import com.github.player13.ates.event.transaction.TransactionAppliedPayload
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ApplyTransactionUseCase(
    private val transactionEventProducer: TransactionEventProducer,
    private val userAccountRepository: UserAccountRepository,
    private val taskRepository: TaskRepository,
    private val transactionRepository: TransactionRepository,
) {

    @Transactional
    fun apply(command: ApplyTransactionCommand) {
        val userAccount = userAccountRepository.findByUserPublicIdWithLock(command.userPublicId)
            ?.let { userAccountRepository.save(it.copy(balance = it.balance + command.amount)) }
            ?: error("User ${command.userPublicId} not found")
        val task = command.taskPublicId
            ?.let {
                taskRepository.findByPublicId(it)
                    ?: error("Task ${command.taskPublicId} not found")
            }

        val transaction = transactionRepository.save(command.toTransaction(userAccount, task))
        transactionEventProducer.send(transaction.toEvent())
    }

    companion object {

        private fun ApplyTransactionCommand.toTransaction(userAccount: UserAccount, task: Task?) =
            Transaction(
                publicId = UUID.randomUUID(),
                userAccount = userAccount,
                task = task,
                reason = reason,
                amount = amount,
                timestamp = OffsetDateTime.now().truncatedTo(ChronoUnit.MILLIS),
            )

        private fun Transaction.toEvent() =
            TransactionAppliedPayload.newBuilder()
                .setUserPublicId(userAccount.userPublicId)
                .setTaskPublicId(task?.publicId)
                .setReason(reason)
                .setAmount(amount)
                .setResultingBalance(userAccount.balance)
                .setTimestamp(timestamp.toInstant())
                .build()
    }
}

data class ApplyTransactionCommand(
    val userPublicId: UUID,
    val taskPublicId: UUID?,
    val amount: Long,
    val reason: String,
)
