package com.github.player13.ates.analytics.statistics.dao

import com.github.player13.ates.analytics.statistics.MostExpensiveTask
import jakarta.persistence.LockModeType
import java.time.LocalDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface MostExpensiveTaskRepository : JpaRepository<MostExpensiveTask, LocalDate> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from MostExpensiveTask where date = :date")
    fun findByIdWithLock(date: LocalDate): MostExpensiveTask?
}
