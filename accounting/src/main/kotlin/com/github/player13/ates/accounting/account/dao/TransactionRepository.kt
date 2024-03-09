package com.github.player13.ates.accounting.account.dao

import com.github.player13.ates.accounting.account.Transaction
import com.github.player13.ates.accounting.account.UserAccount
import java.time.LocalDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface TransactionRepository : JpaRepository<Transaction, Long> {

    fun findAllByUserAccountOrderByTimestampDesc(userAccount: UserAccount): List<Transaction>

    // SUBSTRING(d.dateCreation, 1, 4)
    @Query(
        """
            select cast(date_trunc('day', timestamp) as date) as date, sum(amount) as sum
            from "transaction"
            group by date_trunc('day', timestamp)
            order by date_trunc('day', timestamp) desc
        """,
        nativeQuery = true,
    ) // jpql don't have functions to truncate dates
    fun findSumGroupByDay(): List<SumGroupedByDay>

    interface SumGroupedByDay {
        val date: LocalDate
        val sum: Long
    }
}
