package com.github.player13.ates.accounting.task.usecase

import com.github.player13.ates.accounting.task.Task
import com.github.player13.ates.accounting.task.dao.TaskRepository
import com.github.player13.ates.accounting.task.event.TaskEventProducer
import com.github.player13.ates.event.task.TaskUpdatedPayload
import java.util.UUID
import kotlin.random.Random
import org.springframework.stereotype.Component

@Component
class CreateTaskUseCase(
    private val taskEventProducer: TaskEventProducer,
    private val taskRepository: TaskRepository,
) {

    fun create(command: CreateTaskCommand) =
        taskRepository.save(command.toTask())
            .also { taskEventProducer.send(it.toTaskUpdatedEvent()) }

    companion object {

        private fun CreateTaskCommand.toTask() =
            Task(
                publicId = publicId,
                summary = summary,
                description = description,
                assignmentFee = Random.nextInt(0, 20).toByte(),
                completionReward = Random.nextInt(20, 40).toByte(),
            )

        private fun Task.toTaskUpdatedEvent() =
            TaskUpdatedPayload.newBuilder()
                .setPublicId(publicId)
                .setAssignmentFee(assignmentFee.toInt())
                .setCompletionReward(completionReward.toInt())
                .build()
    }
}

data class CreateTaskCommand(
    val publicId: UUID,
    val summary: String,
    val description: String,
)
