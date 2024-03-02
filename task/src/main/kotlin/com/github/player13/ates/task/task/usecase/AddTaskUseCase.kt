package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import com.github.player13.ates.task.task.event.TaskAddedEvent
import com.github.player13.ates.task.task.event.TaskBusinessEventProducer
import com.github.player13.ates.task.task.event.TaskCreatedEvent
import com.github.player13.ates.task.task.event.TaskStreamingEventProducer
import com.github.player13.ates.task.user.User
import com.github.player13.ates.task.user.usecase.GetAllEmployeesUseCase
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class AddTaskUseCase(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    private val taskStreamingEventProducer: TaskStreamingEventProducer,
    private val taskBusinessEventProducer: TaskBusinessEventProducer,
    private val taskRepository: TaskRepository,
) {

    fun add(command: AddTaskCommand): Task {
        val executor = getAllEmployeesUseCase.get().random()
        val task = taskRepository.save(command.toTask(executor))
        taskStreamingEventProducer.send(task.toTaskCreatedEvent())
        taskBusinessEventProducer.send(task.toTaskAddedEvent())
        return task
    }

    companion object {

        private fun AddTaskCommand.toTask(executor: User) =
            Task(
                id = UUID.randomUUID(),
                summary = summary,
                description = description,
                status = Status.IN_PROGRESS,
                executor = executor,
            )

        private fun Task.toTaskCreatedEvent() =
            TaskCreatedEvent(
                id = id,
                summary = summary,
                description = description,
                status = status,
            )

        private fun Task.toTaskAddedEvent() =
            TaskAddedEvent(
                id = id,
                executorUserId = executor.id,
            )
    }
}

data class AddTaskCommand(
    val summary: String,
    val description: String,
)
