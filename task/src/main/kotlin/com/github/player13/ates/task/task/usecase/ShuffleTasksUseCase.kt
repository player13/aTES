package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.event.task.TaskAssigned
import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import com.github.player13.ates.task.task.event.TaskEventProducer
import com.github.player13.ates.task.user.usecase.GetAllEmployeesUseCase
import org.springframework.stereotype.Component

@Component
class ShuffleTasksUseCase(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    private val taskEventProducer: TaskEventProducer,
    private val taskRepository: TaskRepository,
) {

    fun shuffle() {
        val employees = getAllEmployeesUseCase.get()
        taskRepository.findAllByStatus(Status.IN_PROGRESS)
            .map {
                it.copy(executor = employees.random())
            }
            .let {
                taskRepository.saveAll(it)
            }
            .forEach {
                taskEventProducer.send(it.toTaskAssignedEvent())
            }
    }

    companion object {

        private fun Task.toTaskAssignedEvent() =
            TaskAssigned.newBuilder()
                .setId(id)
                .setExecutorUserId(executor.id)
                .build()
    }
}