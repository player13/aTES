package com.github.player13.ates.accounting.task.event

import com.github.player13.ates.accounting.task.usecase.CreateTaskCommand
import com.github.player13.ates.accounting.task.usecase.CreateTaskUseCase
import com.github.player13.ates.event.task.TaskCreated
import org.apache.avro.specific.SpecificRecord
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class TaskStreamingEventConsumer(
    private val createTaskUseCase: CreateTaskUseCase,
) {

    @KafkaListener(topics = ["\${event.task.streaming}"])
    fun receive(event: SpecificRecord) {
        when (event) {
            is TaskCreated -> createTaskUseCase.create(event.toCommand())
        }
    }

    companion object {

        private fun TaskCreated.toCommand() =
            CreateTaskCommand(
                id = id,
                summary = summary,
                description = description,
            )
    }
}