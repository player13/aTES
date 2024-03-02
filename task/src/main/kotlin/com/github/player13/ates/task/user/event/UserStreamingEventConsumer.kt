package com.github.player13.ates.task.user.event

import com.github.player13.ates.task.user.usecase.CreateUserCommand
import com.github.player13.ates.task.user.usecase.CreateUserUseCase
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserStreamingEventConsumer(
    private val createUserUseCase: CreateUserUseCase,
) {

    @KafkaListener(topics = ["\${event.user.streaming}"])
    fun receive(event: UserStreamingEvent) {
        when (event) {
            is UserCreatedEvent -> createUserUseCase.create(event.toCommand())
        }
    }

    companion object {
        private fun UserCreatedEvent.toCommand() =
            CreateUserCommand(
                id = id,
                login = login,
                role = role,
            )
    }
}
