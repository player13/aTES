package com.github.player13.ates.auth.user.event

import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class UserRegisteredEventProducer(
    private val kafkaTemplate: KafkaTemplate<UUID, UserBusinessEvent>,
    @Value("\${event.user.business}")
    private val topic: String,
) {

    fun send(event: UserBusinessEvent) {
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.id)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build()
        )
    }
}
