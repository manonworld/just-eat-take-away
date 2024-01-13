package com.example.demo.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverOptions
import java.time.Duration
import java.util.*

@Component
@EnableConfigurationProperties
class ReactiveKafkaConsumerConfiguration(
    @Value("\${spring.kafka.bootstrap-servers}")
    private val servers: String,

    @Value("\${spring.kafka.consumer.key-deserializer}")
    private val keyDeserializer: String,

    @Value("\${spring.kafka.consumer.value-deserializer}")
    private val valueDeserializer: String,

    @Value("\${spring.kafka.consumer.group}")
    private val group: String,

    @Value("\${spring.kafka.consumer.client}")
    private val client: String,

    @Value("\${spring.kafka.consumer.auto-offset-reset}")
    private val offsetReset: String,

    @Value("\${spring.kafka.consumer.commitInterval}")
    private val commitInterval: String,

    @Value("\${spring.kafka.consumer.retryBackoffMs}")
    private val retryBackoffMs: String,

    @Value("\${spring.kafka.consumer.maxPollRecords}")
    private val maxPollRecords: String,

    @Value("\${spring.kafka.consumer.maxPollIntervalMs}")
    private val maxPollIntervalMs: String,

    @Value("\${spring.kafka.consumer.sessionTimeoutMs}")
    private val sessionTimeoutMs: String,

    @Value("\${spring.kafka.consumer.heartbeatIntervalMs}")
    private val heartbeatIntervalMs: String,

    @Value("\${spring.kafka.consumer.requestTimeoutMs}")
    private val requestTimeoutMs: String,

    @Value("\${spring.kafka.consumer.autoCommitIntervalMs}")
    private val autoCommitIntervalMs: String,

    @Value("\${spring.kafka.consumer.commitBatchSize}")
    private val commitBatchSize: Int,

    @Value("\${spring.kafka.consumer.receiverTopic}")
    private val receiverTopic: String,
) {

    private fun getOptions(): ReceiverOptions<String, ByteArray> {
        val properties = Properties()

        properties[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = servers
        properties[ConsumerConfig.GROUP_ID_CONFIG] = group
        properties[ConsumerConfig.CLIENT_ID_CONFIG] = client
        properties[ConsumerConfig.RETRY_BACKOFF_MS_CONFIG] = retryBackoffMs
        properties[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = offsetReset
        properties[ConsumerConfig.MAX_POLL_RECORDS_CONFIG] = maxPollRecords
        properties[ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG] = maxPollIntervalMs
        properties[ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG] = sessionTimeoutMs
        properties[ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG] = heartbeatIntervalMs
        properties[ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG] = requestTimeoutMs
        properties[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = keyDeserializer
        properties[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = valueDeserializer
        properties[ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG] = autoCommitIntervalMs

        log.info("Kafka consumer properties configured : {}", properties)

        return ReceiverOptions.create<String?, ByteArray>(properties)
            .commitInterval(Duration.ofMillis(commitInterval.toLong()))
            .commitBatchSize(commitBatchSize)
    }

    @Bean
    fun kafkaReceiver(receiverConfiguration: ReactiveKafkaConsumerConfiguration): KafkaReceiver<String, ByteArray> {
        return KafkaReceiver.create(
            receiverConfiguration.getOptions().subscription(
                listOf(
                    receiverTopic
                )
            )
        )
    }

    companion object {
        private val log = LoggerFactory.getLogger(ReactiveKafkaConsumerConfiguration::class.java)
    }

}