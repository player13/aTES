package com.github.player13.ates.accounting.task.usecase

import com.github.player13.ates.accounting.task.Task
import com.github.player13.ates.accounting.task.dao.TaskRepository
import java.util.UUID
import kotlin.random.Random
import org.springframework.stereotype.Component

@Component
class CreateTaskUseCase(
    private val taskRepository: TaskRepository,
) {

    fun create(command: CreateTaskCommand) =
        taskRepository.save(command.toTask())

    companion object {

        private fun CreateTaskCommand.toTask() =
            Task(
                id = id,
                summary = summary,
                description = description,
                assignmentFee = Random.nextInt(0, 20).toByte(),
                completionReward = Random.nextInt(20, 40).toByte(),
            )
    }
}

data class CreateTaskCommand(
    val id: UUID,
    val summary: String,
    val description: String,
)
