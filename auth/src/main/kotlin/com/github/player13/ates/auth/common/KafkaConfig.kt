package com.github.player13.ates.auth.common

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.converter.RecordMessageConverter
import org.springframework.kafka.support.converter.StringJsonMessageConverter

@Configuration(proxyBeanMethods = false)
class KafkaConfig {

    @Bean
    fun kafkaJsonListenerContainerFactory(
        objectMapper: ObjectMapper,
    ): RecordMessageConverter =
        StringJsonMessageConverter(objectMapper)
}