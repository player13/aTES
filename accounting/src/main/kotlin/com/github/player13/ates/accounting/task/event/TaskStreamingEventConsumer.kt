package com.github.player13.ates.accounting.task.event

import com.github.player13.ates.accounting.task.usecase.CreateTaskCommand
import com.github.player13.ates.accounting.task.usecase.CreateTaskUseCase
import com.github.player13.ates.event.task.TaskCreated
import com.github.player13.ates.event.task.TaskCreatedVersion
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
            is TaskCreated -> consume(event)
        }
    }

    private fun consume(event: TaskCreated) {
        when (event.meta.version) {
            TaskCreatedVersion.v1 -> createTaskUseCase.create(event.toCommand())
            else -> {}
        }
    }

    companion object {

        private fun TaskCreated.toCommand() =
            with(payload) {
                CreateTaskCommand(
                    publicId = publicId,
                    summary = summary,
                    description = description,
                )
            }
    }
}