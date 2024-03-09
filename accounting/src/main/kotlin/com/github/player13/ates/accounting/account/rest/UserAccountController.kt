package com.github.player13.ates.accounting.account.rest

import com.github.player13.ates.accounting.account.rest.GetBalanceAndTransactionsResponse.TransactionDto
import com.github.player13.ates.accounting.account.usecase.GetAccountBalanceAndTransactionsCommand
import com.github.player13.ates.accounting.account.usecase.GetAccountBalanceAndTransactionsUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import java.util.UUID
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/accounts")
@SecurityRequirement(name = "security_auth")
class UserAccountController(
    private val getAccountBalanceAndTransactionsUseCase: GetAccountBalanceAndTransactionsUseCase,
) {

    @GetMapping
    fun getOwnBalanceAndTransactions(authentication: Authentication) =
        authentication.name.let { UUID.fromString(it) }
            .let { getAccountBalanceAndTransactionsUseCase.get(GetAccountBalanceAndTransactionsCommand(it)) }
            .let { (account, transactions) ->
                GetBalanceAndTransactionsResponse(
                    balance = account.balance,
                    transactions = transactions.map { TransactionDto(reason = it.reason, amount = it.amount) },
                )
            }
}
