package com.github.player13.ates.auth.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.player13.ates.auth.user.event.UserCreatedEvent
import com.github.player13.ates.auth.user.event.UserRegisteredEvent
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
                        // business
                        "USER_REGISTERED" to UserRegisteredEvent::class.java,
                        // cud
                        "USER_CREATED" to UserCreatedEvent::class.java,
                    )
            }

    private fun simpleHeaderMapper() =
        SimpleKafkaHeaderMapper()
            .apply {
                setRawMappedHeaders(mapOf(AbstractJavaTypeMapper.DEFAULT_CLASSID_FIELD_NAME to true))
            }
}
