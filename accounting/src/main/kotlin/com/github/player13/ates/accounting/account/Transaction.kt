package com.github.player13.ates.accounting.account

import com.github.player13.ates.accounting.task.Task
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.OffsetDateTime
import java.util.UUID

@Entity
@Table(name = "\"transaction\"")
data class Transaction(
    val publicId: UUID,
    @ManyToOne
    @JoinColumn(name = "user_account_id")
    val userAccount: UserAccount,
    @ManyToOne(optional = true)
    @JoinColumn(name = "task_id")
    val task: Task?,
    val reason: String,
    val amount: Long,
    val timestamp: OffsetDateTime,
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
