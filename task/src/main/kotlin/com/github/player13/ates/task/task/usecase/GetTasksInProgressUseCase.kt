package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class GetTasksInProgressUseCase(
    private val taskRepository: TaskRepository,
) {

    fun get(command: GetTasksInProgressCommand): List<Task> =
        taskRepository.findAllByExecutorPublicIdAndStatus(command.executorPublicId, Status.IN_PROGRESS)
}

data class GetTasksInProgressCommand(
    val executorPublicId: UUID,
)
