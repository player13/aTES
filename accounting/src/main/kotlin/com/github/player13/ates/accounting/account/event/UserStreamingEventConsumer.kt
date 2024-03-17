package com.github.player13.ates.accounting.account.event

import com.github.player13.ates.accounting.account.usecase.CreateUserAccountCommand
import com.github.player13.ates.accounting.account.usecase.CreateUserAccountUseCase
import com.github.player13.ates.event.user.UserCreated
import com.github.player13.ates.event.user.UserCreatedVersion
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class UserStreamingEventConsumer(
    private val createUserAccountUseCase: CreateUserAccountUseCase,
) {

    @KafkaListener(topics = ["\${event.user.streaming}"])
    fun receive(event: SpecificRecord) {
        when (event) {
            is UserCreated -> consume(event)
        }
    }

    private fun consume(event: UserCreated) {
        when (event.meta.version) {
            UserCreatedVersion.v1 -> createUserAccountUseCase.create(event.toCommand())
            else -> {}
        }
    }

    companion object {

        private fun UserCreated.toCommand() =
            with(payload) {
                CreateUserAccountCommand(
                    publicId = publicId,
                    login = login,
                )
            }
    }
}
