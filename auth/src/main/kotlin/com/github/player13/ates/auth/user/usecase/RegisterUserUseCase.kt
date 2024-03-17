package com.github.player13.ates.auth.user.usecase

import com.github.player13.ates.auth.user.Role
import com.github.player13.ates.auth.user.User
import com.github.player13.ates.auth.user.dao.UserRepository
import com.github.player13.ates.auth.user.event.UserEventProducer
import com.github.player13.ates.auth.user.event.toEvent
import com.github.player13.ates.event.user.UserCreated
import com.github.player13.ates.event.user.UserCreatedPayload
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class RegisterUserUseCase(
    private val userRepository: UserRepository,
    private val userEventProducer: UserEventProducer,
) {

    fun register(command: RegisterUserCommand): User {
        val user = userRepository.save(command.toUser())
        userEventProducer.send(user.toUserCreatedEvent())
        return user
    }

    companion object {

        private fun RegisterUserCommand.toUser() =
            User(
                publicId = UUID.randomUUID(),
                login = login,
                password = password,
                role = role,
            )

        private fun User.toUserCreatedEvent() =
            UserCreatedPayload.newBuilder()
                .setPublicId(publicId)
                .setLogin(login)
                .setRole(role.toEvent())
                .build()
    }
}

data class RegisterUserCommand(
    val login: String,
    val password: String,
    val role: Role,
)
