package com.github.player13.ates.task.task.usecase

import com.github.player13.ates.event.task.TaskAdded
import com.github.player13.ates.event.task.TaskCreated
import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.dao.TaskRepository
import com.github.player13.ates.task.task.event.TaskEventProducer
import com.github.player13.ates.task.task.event.toEvent
import com.github.player13.ates.task.user.User
import com.github.player13.ates.task.user.usecase.GetAllEmployeesUseCase
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class AddTaskUseCase(
    private val getAllEmployeesUseCase: GetAllEmployeesUseCase,
    private val taskEventProducer: TaskEventProducer,
    private val taskRepository: TaskRepository,
) {

    fun add(command: AddTaskCommand): Task {
        val executor = getAllEmployeesUseCase.get().random()
        val task = taskRepository.save(command.toTask(executor))
        taskEventProducer.send(task.toTaskCreatedEvent())
        taskEventProducer.send(task.toTaskAddedEvent())
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
            TaskCreated.newBuilder()
                .setId(id)
                .setSummary(summary)
                .setDescription(description)
                .setStatus(status.toEvent())
                .build()

        private fun Task.toTaskAddedEvent() =
            TaskAdded.newBuilder()
                .setId(id)
                .setSummary(summary)
                .setDescription(description)
                .setExecutorUserId(executor.id)
                .build()
    }
}

data class AddTaskCommand(
    val summary: String,
    val description: String,
)
