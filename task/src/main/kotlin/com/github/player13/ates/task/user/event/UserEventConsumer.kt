package com.github.player13.ates.task.user.event

import com.github.player13.ates.event.user.UserCreated
import com.github.player13.ates.task.user.usecase.CreateUserCommand
import com.github.player13.ates.task.user.usecase.CreateUserUseCase
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserEventConsumer(
    private val createUserUseCase: CreateUserUseCase,
) {

    @KafkaListener(topics = ["\${event.user.streaming}"])
    // revealing avro class because when using Any then spring injects ConsumerRecord instead of event object
    fun receive(event: SpecificRecord) {
        when (event) {
            is UserCreated -> createUserUseCase.create(event.toCommand())
        }
    }

    companion object {

        private fun UserCreated.toCommand() =
            CreateUserCommand(
                id = id,
                login = login,
                role = role.toDomain(),
            )
    }
}
