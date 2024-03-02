package com.github.player13.ates.auth.user

import com.github.player13.ates.auth.user.dao.UserRepository
import com.github.player13.ates.auth.user.usecase.RegisterUserCommand
import com.github.player13.ates.auth.user.usecase.RegisterUserUseCase
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class DevUsersInitializer(
    private val registerUserUseCase: RegisterUserUseCase,
    private val userRepository: UserRepository,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments?) {
        if (userRepository.count() == 0L) {
            commands.forEach { registerUserUseCase.register(it) }
        }
    }

    companion object {

        private val commands = listOf(
            RegisterUserCommand(
                login = "admin",
                password = "password",
                role = Role.ADMINISTRATOR,
            ),
            RegisterUserCommand(
                login = "accountant",
                password = "password",
                role = Role.ACCOUNTANT,
            ),
            RegisterUserCommand(
                login = "manager",
                password = "password",
                role = Role.MANAGER,
            ),
            RegisterUserCommand(
                login = "employee0",
                password = "password",
                role = Role.EMPLOYEE,
            ),
            RegisterUserCommand(
                login = "employee1",
                password = "password",
                role = Role.EMPLOYEE,
            ),
            RegisterUserCommand(
                login = "employee2",
                password = "password",
                role = Role.EMPLOYEE,
            ),
        )
    }
}
