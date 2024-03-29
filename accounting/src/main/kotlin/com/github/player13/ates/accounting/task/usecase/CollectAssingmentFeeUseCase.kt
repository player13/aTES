package com.github.player13.ates.accounting.task.usecase

import com.github.player13.ates.accounting.account.usecase.ApplyTransactionCommand
import com.github.player13.ates.accounting.account.usecase.ApplyTransactionUseCase
import com.github.player13.ates.accounting.task.dao.TaskRepository
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CollectAssignmentFeeUseCase(
    private val applyTransactionUseCase: ApplyTransactionUseCase,
    private val taskRepository: TaskRepository,
) {

    fun collect(command: CollectAssignmentFeeCommand) {
        val task = taskRepository.findByIdOrNull(command.taskId)
            ?: error("Task ${command.taskId} not found")

        applyTransactionUseCase.apply(
            ApplyTransactionCommand(
                userId = command.userId,
                amount = -task.assignmentFee.toLong(),
                reason = "Удержание комиссии за назначение задачи '${task.summary}'",
            )
        )
    }
}

data class CollectAssignmentFeeCommand(
    val taskId: UUID,
    val userId: UUID,
)