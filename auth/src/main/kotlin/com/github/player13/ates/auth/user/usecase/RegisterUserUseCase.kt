package com.github.player13.ates.auth.user.usecase

import com.github.player13.ates.auth.user.Role
import com.github.player13.ates.auth.user.User
import com.github.player13.ates.auth.user.dao.UserRepository
import com.github.player13.ates.auth.user.event.UserCreatedEvent
import com.github.player13.ates.auth.user.event.UserRegisteredEvent
import com.github.player13.ates.auth.user.event.UserRegisteredEventProducer
import com.github.player13.ates.auth.user.event.UserStreamingEventProducer
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val userStreamingEventProducer: UserStreamingEventProducer,
    private val userRegisteredEventProducer: UserRegisteredEventProducer,
) {

    fun register(command: RegisterUserCommand): User {
        val user = userRepository.save(command.toUser())
        userStreamingEventProducer.send(user.toUserCreatedEvent())
        userRegisteredEventProducer.send(user.toUserRegisteredEvent())
        return user
    }

    companion object {

        private fun RegisterUserCommand.toUser() =
            User(
                id = UUID.randomUUID(),
                login = login,
                password = password,
                role = role,
            )

        private fun User.toUserCreatedEvent() =
            UserCreatedEvent(
                id = id,
                login = login,
                role = role,
            )

        private fun User.toUserRegisteredEvent() =
            UserRegisteredEvent(
                id = id,
                login = login,
                role = role,
            )
    }
}

data class RegisterUserCommand(
    val login: String,
    val password: String,
    val role: Role,
)
