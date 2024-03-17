package com.github.player13.ates.task.task.dao

import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findByPublicId(publicId: UUID): Task?

    fun findAllByStatus(status: Status): List<Task>

    fun findAllByExecutorPublicIdAndStatus(executorPublicId: UUID, status: Status): List<Task>
}