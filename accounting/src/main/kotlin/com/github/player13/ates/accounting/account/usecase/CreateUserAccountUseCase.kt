package com.github.player13.ates.accounting.account.usecase

import com.github.player13.ates.accounting.account.UserAccount
import com.github.player13.ates.accounting.account.dao.UserAccountRepository
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CreateUserAccountUseCase(
    private val userAccountRepository: UserAccountRepository,
) {

    fun create(command: CreateUserAccountCommand) =
        userAccountRepository.save(command.toUser())

    companion object {

        private fun CreateUserAccountCommand.toUser() =
            UserAccount(
                userPublicId = publicId,
                balance = 0,
            )
    }
}

data class CreateUserAccountCommand(
    val publicId: UUID,
    val login: String,
)
