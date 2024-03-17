package com.github.player13.ates.auth.user.rest

import com.github.player13.ates.auth.user.User
import com.github.player13.ates.auth.user.usecase.RegisterUserCommand
import com.github.player13.ates.auth.user.usecase.RegisterUserUseCase
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val registerUserUseCase: RegisterUserUseCase,
) {

    @GetMapping("/current")
    fun getCurrent(@AuthenticationPrincipal(expression = "user") user: User): UserView =
        user.toUserView()

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    fun register(@RequestBody request: RegisterUserRequest): UserView =
        registerUserUseCase.register(request.toCommand()).toUserView()

    companion object {

        private fun User.toUserView() =
            UserView(
                publicId = publicId,
                login = login,
                role = role,
            )

        private fun RegisterUserRequest.toCommand() =
            RegisterUserCommand(
                login = login,
                password = password,
                role = role,
            )
    }
}
