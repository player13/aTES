package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.event.task.TaskCompletedPayload
import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import com.github.player13.ates.task.task.event.TaskEventProducer
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CompleteTaskUseCase(
    private val taskEventProducer: TaskEventProducer,
    private val taskRepository: TaskRepository,
) {

    fun complete(command: CompleteTaskCommand): Task {
        val task = taskRepository.findByPublicId(command.taskPublicId)
            ?: error("Unknown task ${command.taskPublicId}")
        check(task.executor.publicId == command.executorPublicId) {
            "Only currently assigned executor can complete task"
        }
        val completed = taskRepository.save(task.copy(status = Status.COMPLETED))
        taskEventProducer.send(task.toTaskCompletedEvent())
        return completed
    }

    companion object {

        private fun Task.toTaskCompletedEvent() =
            TaskCompletedPayload.newBuilder()
                .setPublicId(publicId)
                .setExecutorPublicId(executor.publicId)
                .build()
    }
}

data class CompleteTaskCommand(
    val taskPublicId: UUID,
    val executorPublicId: UUID,
)
