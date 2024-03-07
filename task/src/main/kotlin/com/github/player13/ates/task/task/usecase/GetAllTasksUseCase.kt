package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import org.springframework.stereotype.Component

@Component
class GetAllTasksUseCase(
    private val taskRepository: TaskRepository,
) {

    fun get(): List<Task> =
        taskRepository.findAll()
}
