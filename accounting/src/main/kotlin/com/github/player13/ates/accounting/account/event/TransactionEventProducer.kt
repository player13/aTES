package com.github.player13.ates.accounting.account.event

import com.github.player13.ates.accounting.AccountingApp
import com.github.player13.ates.event.transaction.TransactionApplied
import com.github.player13.ates.event.transaction.TransactionAppliedMeta
import com.github.player13.ates.event.transaction.TransactionAppliedPayload
import com.github.player13.ates.event.transaction.TransactionAppliedVersion
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

    fun send(payload: TransactionAppliedPayload) {
        val event = TransactionApplied.newBuilder()
            .setMeta(
                TransactionAppliedMeta.newBuilder()
                    .setVersion(TransactionAppliedVersion.v1)
                    .setSender(AccountingApp::class.simpleName)
                    .build()
            )
            .setPayload(payload)
            .build()
        kafkaTemplate.send(
            MessageBuilder.withPayload(event)
                .setHeader(KafkaHeaders.KEY, payload.userPublicId)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build()
        )
    }
}
