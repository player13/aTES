package com.github.player13.ates.accounting.account.rest

import com.github.player13.ates.accounting.account.usecase.GetCompanyProfitUseCase
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/statistics")
@SecurityRequirement(name = "security_auth")
class TransactionController(
    private val getCompanyProfitUseCase: GetCompanyProfitUseCase,
) {

    @GetMapping("/profit")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR','ACCOUNTANT')")
    fun getCompanyProfit() =
        getCompanyProfitUseCase.get()
}
