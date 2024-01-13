package com.example.demo.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.stereotype.Component

@Component
@EnableKafka
@EnableConfigurationProperties
class ConcurrentKafkaConsumerConfiguration(

    @Value("\${spring.kafka.bootstrap-servers}")
    private val servers: String,

    @Value("\${spring.kafka.consumer.key-deserializer}")
    private val deserializer: String,

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    private val offsetReset: String,
) {
    @Bean
    fun consumerFactory(): ConsumerFactory<String?, Any?> {
        val props: MutableMap<String, Any> = HashMap()

        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = servers
        props[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = deserializer
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = deserializer
        props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = offsetReset

        return DefaultKafkaConsumerFactory(props)
    }

    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, String>? {
        val factory = ConcurrentKafkaListenerContainerFactory<String, String>()

        factory.consumerFactory = consumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        factory.containerProperties.isSyncCommits = true

        return factory
    }

}