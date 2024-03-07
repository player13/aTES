package com.github.player13.ates.task.task.rest

import com.github.player13.ates.task.task.rest.TaskWithExecutorView.ExecutorView
import com.github.player13.ates.task.task.Task
import com.github.player13.ates.task.task.usecase.AddTaskCommand
import com.github.player13.ates.task.task.usecase.AddTaskUseCase
import com.github.player13.ates.task.task.usecase.CompleteTaskCommand
import com.github.player13.ates.task.task.usecase.CompleteTaskUseCase
import com.github.player13.ates.task.task.usecase.GetAllTasksUseCase
import com.github.player13.ates.task.task.usecase.GetTasksInProgressCommand
import com.github.player13.ates.task.task.usecase.GetTasksInProgressUseCase
import com.github.player13.ates.task.task.usecase.ShuffleTasksUseCase
import com.github.player13.ates.task.user.User
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import java.util.UUID
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/tasks")
@SecurityRequirement(name = "security_auth")
class TaskController(
    private val getTasksInProgressUseCase: GetTasksInProgressUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val shuffleTasksUseCase: ShuffleTasksUseCase,
    private val completeTasksUseCase: CompleteTaskUseCase,
) {

    @GetMapping("/my")
    fun getAssigned(authentication: Authentication): List<TaskView> = // todo: customize user injection
        authentication.name.let { UUID.fromString(it) }
            .let { getTasksInProgressUseCase.get(GetTasksInProgressCommand(it)) }
            .map { it.toTaskView() }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    fun getAll(): List<TaskWithExecutorView> =
        getAllTasksUseCase.get().map { it.toTaskWithExecutorView() }

    @PostMapping
    fun create(@RequestBody request: AddTaskRequest): TaskView =
        addTaskUseCase.add(request.toCommand()).toTaskView()

    @PostMapping("/shuffle")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','MANAGER')")
    fun shuffle() =
        shuffleTasksUseCase.shuffle()

    @PostMapping("/{id}/complete")
    fun complete(@PathVariable id: UUID, authentication: Authentication): TaskView = // todo: customize user injection
        authentication.name.let { UUID.fromString(it) }
            .let { completeTasksUseCase.complete(CompleteTaskCommand(taskId = id, executorUserId = it)) }
            .toTaskView()

    companion object {

        private fun Task.toTaskView() =
            TaskView(
                id = id,
                summary = summary,
                description = description,
                status = status,
            )

        private fun Task.toTaskWithExecutorView() =
            TaskWithExecutorView(
                id = id,
                summary = summary,
                description = description,
                status = status,
                executor = executor.toExecutorView(),
            )

        private fun User.toExecutorView() =
            ExecutorView(
                id = id,
                login = login,
            )

        private fun AddTaskRequest.toCommand() =
            AddTaskCommand(
                summary = summary,
                description = description,
            )
    }
}
