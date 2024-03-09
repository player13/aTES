package com.github.player13.ates.accounting.account.rest

data class GetBalanceAndTransactionsResponse(
    val balance: Long,
    val transactions: List<TransactionDto>,
) {

    data class TransactionDto(
        val reason: String,
        val amount: Long,
    )
}