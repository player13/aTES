package com.github.player13.ates.accounting.task.dao

import com.github.player13.ates.accounting.task.Task
import java.util.UUID
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : JpaRepository<Task, UUID>