package com.github.player13.ates.task.user.usecase

import com.github.player13.ates.task.user.Role
import com.github.player13.ates.task.user.dao.UserRepository
import org.springframework.stereotype.Component

@Component
class GetAllEmployeesUseCase(
    private val userRepository: UserRepository,
) {

    fun get() =
        userRepository.findAllByRole(Role.EMPLOYEE)
}
