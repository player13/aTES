package com.github.player13.ates.accounting.task.event

import com.github.player13.ates.accounting.task.usecase.CollectAssignmentFeeCommand
import com.github.player13.ates.accounting.task.usecase.CollectAssignmentFeeUseCase
import com.github.player13.ates.accounting.task.usecase.PayCompletionRewardCommand
import com.github.player13.ates.accounting.task.usecase.PayCompletionRewardUseCase
import com.github.player13.ates.event.task.TaskAdded
import com.github.player13.ates.event.task.TaskAssigned
import com.github.player13.ates.event.task.TaskCompleted
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TaskBusinessEventConsumer(
    private val collectAssignmentFeeUseCase: CollectAssignmentFeeUseCase,
    private val payCompletionRewardUseCase: PayCompletionRewardUseCase,
) {

    @KafkaListener(topics = ["\${event.task.business}"])
    fun receive(event: SpecificRecord) {
        when (event) {
            is TaskAdded -> collectAssignmentFeeUseCase.collect(event.toCommand())
            is TaskAssigned -> collectAssignmentFeeUseCase.collect(event.toCommand())
            is TaskCompleted -> payCompletionRewardUseCase.pay(event.toCommand())
        }
    }

    companion object {

        private fun TaskAdded.toCommand() =
            CollectAssignmentFeeCommand(
                taskId = id,
                userId = executorUserId,
            )

        private fun TaskAssigned.toCommand() =
            CollectAssignmentFeeCommand(
                taskId = id,
                userId = executorUserId,
            )

        private fun TaskCompleted.toCommand() =
            PayCompletionRewardCommand(
                taskId = id,
                userId = executorUserId,
            )
    }
}