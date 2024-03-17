package com.github.player13.ates.accounting.account

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.OffsetDateTime

@Entity
@Table(name = "\"transaction\"")
data class Transaction(
    @ManyToOne
    @JoinColumn(name = "user_id")
    val userAccount: UserAccount,
    val reason: String,
    val amount: Long,
    val timestamp: OffsetDateTime,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
