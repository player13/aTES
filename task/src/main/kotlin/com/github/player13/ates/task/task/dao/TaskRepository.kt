package com.github.player13.ates.task.task.dao

import com.github.player13.ates.task.task.Status
import com.github.player13.ates.task.task.Task
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, UUID> {

    fun findAllByStatus(status: Status): List<Task>

    fun findAllByExecutorIdAndStatus(executorId: UUID, status: Status): List<Task>
}