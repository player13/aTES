package com.github.player13.ates.accounting.account.usecase

import com.github.player13.ates.accounting.account.dao.TransactionRepository
import org.springframework.stereotype.Component

@Component
class GetCompanyProfitUseCase(
    private val transactionRepository: TransactionRepository,
) {

    fun get() =
        transactionRepository.findSumGroupByDay()
}