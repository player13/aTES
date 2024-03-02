package com.github.player13.ates.task.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.player13.ates.task.task.event.TaskAddedEvent
import com.github.player13.ates.task.task.event.TaskAssignedEvent
import com.github.player13.ates.task.task.event.TaskCompletedEvent
import com.github.player13.ates.task.task.event.TaskCreatedEvent
import com.github.player13.ates.task.task.event.TaskUpdatedEvent
import com.github.player13.ates.task.user.event.UserCreatedEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.SimpleKafkaHeaderMapper
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter
import org.springframework.kafka.support.mapping.AbstractJavaTypeMapper
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper

@Configuration(proxyBeanMethods = false)
class KafkaConfig {

    @Bean
    fun kafkaJsonListenerContainerFactory(
        objectMapper: ObjectMapper,
    ): RecordMessageConverter =
        StringJsonMessageConverter(objectMapper)
            .apply {
                setHeaderMapper(simpleHeaderMapper())
                typeMapper.typePrecedence = Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID
                (typeMapper as DefaultJackson2JavaTypeMapper).idClassMapping =
                    mapOf(
                        // user
                        // cud
                        "USER_CREATED" to UserCreatedEvent::class.java,

                        // task
                        // business
                        "TASK_ADDED" to TaskAddedEvent::class.java,
                        "TASK_ASSIGNED" to TaskAssignedEvent::class.java,
                        "TASK_COMPLETED" to TaskCompletedEvent::class.java,
                        // cud
                        "TASK_CREATED" to TaskCreatedEvent::class.java,
                        "TASK_UPDATED" to TaskUpdatedEvent::class.java,
                    )
            }

    private fun simpleHeaderMapper() =
        SimpleKafkaHeaderMapper()
            .apply {
                setRawMappedHeaders(mapOf(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME to true))
            }
}
