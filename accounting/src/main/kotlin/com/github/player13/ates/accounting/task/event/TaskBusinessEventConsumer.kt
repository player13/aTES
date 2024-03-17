package com.github.player13.ates.accounting.task.event

import com.github.player13.ates.accounting.task.usecase.CollectAssignmentFeeCommand
import com.github.player13.ates.accounting.task.usecase.CollectAssignmentFeeUseCase
import com.github.player13.ates.accounting.task.usecase.PayCompletionRewardCommand
import com.github.player13.ates.accounting.task.usecase.PayCompletionRewardUseCase
import com.github.player13.ates.event.task.TaskAdded
import com.github.player13.ates.event.task.TaskAddedVersion
import com.github.player13.ates.event.task.TaskAssigned
import com.github.player13.ates.event.task.TaskAssignedVersion
import com.github.player13.ates.event.task.TaskCompleted
import com.github.player13.ates.event.task.TaskCompletedVersion
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
            is TaskAdded -> consume(event)
            is TaskAssigned -> consume(event)
            is TaskCompleted -> consume(event)
        }
    }

    private fun consume(event: TaskAdded) {
        when (event.meta.version) {
            TaskAddedVersion.v1 -> collectAssignmentFeeUseCase.collect(event.toCommand())
            else -> {}
        }
    }

    private fun consume(event: TaskAssigned) {
        when (event.meta.version) {
            TaskAssignedVersion.v1 -> collectAssignmentFeeUseCase.collect(event.toCommand())
            else -> {}
        }
    }

    private fun consume(event: TaskCompleted) {
        when (event.meta.version) {
            TaskCompletedVersion.v1 -> payCompletionRewardUseCase.pay(event.toCommand())
            else -> {}
        }
    }

    companion object {

        private fun TaskAdded.toCommand() =
            with(payload) {
                CollectAssignmentFeeCommand(
                    taskPublicId = publicId,
                    userPublicId = executorPublicId,
                )
            }

        private fun TaskAssigned.toCommand() =
            with(payload) {
                CollectAssignmentFeeCommand(
                    taskPublicId = publicId,
                    userPublicId = executorPublicId,
                )
            }

        private fun TaskCompleted.toCommand() =
            with(payload) {
                PayCompletionRewardCommand(
                    taskPublicId = publicId,
                    userPublicId = executorPublicId,
                )
            }
    }
}