package com.github.player13.ates.task.user.usecase

import com.github.player13.ates.task.user.Role
import com.github.player13.ates.task.user.User
import com.github.player13.ates.task.user.dao.UserRepository
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CreateUserUseCase(
    private val userRepository: UserRepository,
) {

    fun create(command: CreateUserCommand) =
        userRepository.save(command.toUser())

    companion object {

        private fun CreateUserCommand.toUser() =
            User(
                id = id,
                login = login,
                role = role,
            )
    }
}

data class CreateUserCommand(
    val id: UUID,
    val login: String,
    val role: Role,
)