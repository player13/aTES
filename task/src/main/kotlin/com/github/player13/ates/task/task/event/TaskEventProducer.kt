package com.github.player13.ates.task.task.event

import com.github.player13.ates.event.task.TaskAdded
import com.github.player13.ates.event.task.TaskAddedMeta
import com.github.player13.ates.event.task.TaskAddedPayload
import com.github.player13.ates.event.task.TaskAddedVersion
import com.github.player13.ates.event.task.TaskAssigned
import com.github.player13.ates.event.task.TaskAssignedMeta
import com.github.player13.ates.event.task.TaskAssignedPayload
import com.github.player13.ates.event.task.TaskAssignedVersion
import com.github.player13.ates.event.task.TaskCompleted
import com.github.player13.ates.event.task.TaskCompletedMeta
import com.github.player13.ates.event.task.TaskCompletedPayload
import com.github.player13.ates.event.task.TaskCompletedVersion
import com.github.player13.ates.event.task.TaskCreated
import com.github.player13.ates.event.task.TaskCreatedMeta
import com.github.player13.ates.event.task.TaskCreatedPayload
import com.github.player13.ates.event.task.TaskCreatedVersion
import com.github.player13.ates.task.TaskApp
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

    fun send(payload: TaskCreatedPayload) {
        val event = TaskCreated.newBuilder()
            .setMeta(
                TaskCreatedMeta.newBuilder()
                    .setVersion(TaskCreatedVersion.v1)
                    .setSender(TaskApp::class.simpleName)
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

    fun send(payload: TaskAddedPayload) {
        val event = TaskAdded.newBuilder()
            .setMeta(
                TaskAddedMeta.newBuilder()
                    .setVersion(TaskAddedVersion.v1)
                    .setSender(TaskApp::class.simpleName)
                    .build()
            )
            .setPayload(payload)
            .build()
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, payload.publicId)
                .setHeader(KafkaHeaders.TOPIC, businessTopic)
                .build()
        )
    }

    fun send(payload: TaskAssignedPayload) {
        val event = TaskAssigned.newBuilder()
            .setMeta(
                TaskAssignedMeta.newBuilder()
                    .setVersion(TaskAssignedVersion.v1)
                    .setSender(TaskApp::class.simpleName)
                    .build()
            )
            .setPayload(payload)
            .build()
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, payload.publicId)
                .setHeader(KafkaHeaders.TOPIC, businessTopic)
                .build()
        )
    }

    fun send(payload: TaskCompletedPayload) {
        val event = TaskCompleted.newBuilder()
            .setMeta(
                TaskCompletedMeta.newBuilder()
                    .setVersion(TaskCompletedVersion.v1)
                    .setSender(TaskApp::class.simpleName)
                    .build()
            )
            .setPayload(payload)
            .build()
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, payload.publicId)
                .setHeader(KafkaHeaders.TOPIC, businessTopic)
                .build()
        )
    }
}