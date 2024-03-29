package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.event.task.TaskCompleted
import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import com.github.player13.ates.task.task.event.TaskEventProducer
import java.util.UUID
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class CompleteTaskUseCase(
    private val taskEventProducer: TaskEventProducer,
    private val taskRepository: TaskRepository,
) {

    fun complete(command: CompleteTaskCommand): Task {
        val task = taskRepository.findByIdOrNull(command.taskId)
            ?: error("Unknown task")
        check(task.executor.id == command.executorUserId) {
            "Only currently assigned executor can complete task"
        }
        val completed = taskRepository.save(task.copy(status = Status.COMPLETED))
        taskEventProducer.send(task.toTaskCompletedEvent())
        return completed
    }

    companion object {

        private fun Task.toTaskCompletedEvent() =
            TaskCompleted.newBuilder()
                .setId(id)
                .setExecutorUserId(executor.id)
                .build()
    }
}

data class CompleteTaskCommand(
    val taskId: UUID,
    val executorUserId: UUID,
)
