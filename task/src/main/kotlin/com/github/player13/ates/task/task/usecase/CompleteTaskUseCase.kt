package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import com.github.player13.ates.task.task.event.TaskBusinessEventProducer
import com.github.player13.ates.task.task.event.TaskCompletedEvent
import com.github.player13.ates.task.task.event.TaskStreamingEventProducer
import com.github.player13.ates.task.task.event.TaskUpdatedEvent
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CompleteTaskUseCase(
    private val taskStreamingEventProducer: TaskStreamingEventProducer,
    private val taskBusinessEventProducer: TaskBusinessEventProducer,
    private val taskRepository: TaskRepository,
) {

    fun complete(command: CompleteTaskCommand): Task {
        val task = taskRepository.findByIdOrNull(command.taskId)
            ?: error("Unknown task")
        check(task.executor.id == command.executorUserId) {
            "Only currently assigned executor can complete task"
        }
        val completed = taskRepository.save(task.copy(status = Status.COMPLETED))
        taskStreamingEventProducer.send(task.toTaskUpdatedEvent())
        taskBusinessEventProducer.send(task.toTaskCompletedEvent())
        return completed
    }

    companion object {

        private fun Task.toTaskUpdatedEvent() =
            TaskUpdatedEvent(
                id = id,
                status = status,
            )

        private fun Task.toTaskCompletedEvent() =
            TaskCompletedEvent(
                id = id,
            )
    }
}

data class CompleteTaskCommand(
    val taskId: UUID,
    val executorUserId: UUID,
)
