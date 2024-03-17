package com.github.player13.ates.auth.user.event

import com.github.player13.ates.auth.AuthApp
import com.github.player13.ates.event.user.UserCreated
import com.github.player13.ates.event.user.UserCreatedMeta
import com.github.player13.ates.event.user.UserCreatedPayload
import com.github.player13.ates.event.user.UserCreatedVersion
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class UserEventProducer(
    private val kafkaTemplate: KafkaTemplate<UUID, Any>,
    @Value("\${event.user.streaming}")
    private val topic: String,
) {

    fun send(payload: UserCreatedPayload) {
        val event = UserCreated.newBuilder()
            .setMeta(
                UserCreatedMeta.newBuilder()
                    .setVersion(UserCreatedVersion.v1)
                    .setSender(AuthApp::class.simpleName)
                    .build()
            )
            .setPayload(payload)
            .build()
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, payload.publicId)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build()
        )
    }
}
