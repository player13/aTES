package com.github.player13.ates.analytics.statistics.usecase

import com.github.player13.ates.analytics.statistics.MostExpensiveTask
import com.github.player13.ates.analytics.statistics.dao.MostExpensiveTaskRepository
import java.time.LocalDate
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CalculateMostExpensiveTaskUseCase(
    private val mostExpensiveTaskRepository: MostExpensiveTaskRepository,
) {

    @Transactional
    fun calculate(command: CalculateMostExpensiveTaskCommand) {
        val task = mostExpensiveTaskRepository.findByIdWithLock(command.date)
        if (task == null || task.reward < command.reward) {
            mostExpensiveTaskRepository.save(
                task?.copy(reward = command.reward)
                    ?: MostExpensiveTask(command.date, command.reward),
            )
        }
    }
}

data class CalculateMostExpensiveTaskCommand(
    val date: LocalDate,
    val reward: Byte,
)
