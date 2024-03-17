package com.github.player13.ates.accounting.account.usecase

import java.util.UUID
import org.springframework.stereotype.Component

@Component
class SendPayslipByEmailUseCase {

    fun send(command: SendPayslipByEmailCommand) {
        // todo: e-mail server integration
    }
}

data class SendPayslipByEmailCommand(
    val userPublicId: UUID, // should be e-mail and full name here?
    val amount: Long,
)
