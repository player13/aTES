package com.github.player13.ates.accounting.account.event

import com.github.player13.ates.event.transaction.TransactionApplied
import java.util.UUID
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component

@Component
class TransactionEventProducer(
    private val kafkaTemplate: KafkaTemplate<UUID, Any>,
    @Value("\${event.transaction.business}")
    private val topic: String,
) {

    fun send(event: TransactionApplied) {
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, event.userId)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build()
        )
    }
}
