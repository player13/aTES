package com.github.player13.ates.task.task.event

import com.github.player13.ates.event.task.TaskAdded
import com.github.player13.ates.event.task.TaskAssigned
import com.github.player13.ates.event.task.TaskCompleted
import com.github.player13.ates.event.task.TaskCreated
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
    @Value("\${event.task.business}")
    private val businessTopic: String,
) {

    fun send(event: TaskCreated) {
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.id)
                .setHeader(KafkaHeaders.TOPIC, streamingTopic)
                .build()
        )
    }

    fun send(event: TaskAdded) {
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.id)
                .setHeader(KafkaHeaders.TOPIC, businessTopic)
                .build()
        )
    }

    fun send(event: TaskAssigned) {
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.id)
                .setHeader(KafkaHeaders.TOPIC, businessTopic)
                .build()
        )
    }

    fun send(event: TaskCompleted) {
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.id)
                .setHeader(KafkaHeaders.TOPIC, businessTopic)
                .build()
        )
    }
}