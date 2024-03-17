package com.github.player13.ates.accounting.account.dao

import com.github.player13.ates.accounting.account.UserAccount
import jakarta.persistence.LockModeType
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserAccountRepository : JpaRepository<UserAccount, Long> {

    fun findByUserPublicId(userPublicId: UUID): UserAccount?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from UserAccount where userPublicId = :userPublicId")
    fun findByUserPublicIdWithLock(userPublicId: UUID): UserAccount?

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("from UserAccount where balance > 0")
    fun findAllByPositiveBalanceWithLock(): List<UserAccount>
}
