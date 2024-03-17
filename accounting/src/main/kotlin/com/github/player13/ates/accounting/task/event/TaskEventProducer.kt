package com.github.player13.ates.accounting.task.event

import com.github.player13.ates.accounting.AccountingApp
import com.github.player13.ates.accounting.task.dao.TaskRepository
import com.github.player13.ates.event.task.TaskUpdated
import com.github.player13.ates.event.task.TaskUpdatedMeta
import com.github.player13.ates.event.task.TaskUpdatedPayload
import com.github.player13.ates.event.task.TaskUpdatedVersion
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class TaskEventProducer(
    private val kafkaTemplate: KafkaTemplate<UUID, Any>,
    @Value("\${event.task.streaming}")
    private val streamingTopic: String,
) {

    fun send(payload: TaskUpdatedPayload) {
        val event = TaskUpdated.newBuilder()
            .setMeta(
                TaskUpdatedMeta.newBuilder()
                    .setVersion(TaskUpdatedVersion.v1)
                    .setSender(AccountingApp::class.simpleName)
                    .build()
            )
            .setPayload(payload)
            .build()
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, payload.publicId)
                .setHeader(KafkaHeaders.TOPIC, streamingTopic)
                .build()
        )
    }
}