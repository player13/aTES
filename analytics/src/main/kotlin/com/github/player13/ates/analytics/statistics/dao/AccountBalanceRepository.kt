package com.github.player13.ates.analytics.statistics.dao

import com.github.player13.ates.analytics.statistics.AccountBalance
import com.github.player13.ates.analytics.statistics.MostExpensiveTask
import jakarta.persistence.LockModeType
import java.time.LocalDate
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface AccountBalanceRepository : JpaRepository<AccountBalance, LocalDate> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from AccountBalance where userId = :userId")
    fun findByIdWithLock(userId: UUID): AccountBalance?
}
