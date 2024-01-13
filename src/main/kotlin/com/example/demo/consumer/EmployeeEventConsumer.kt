package com.example.demo.consumer

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import reactor.core.Disposables
import reactor.core.publisher.Flux
import reactor.kafka.receiver.KafkaReceiver
import reactor.kafka.receiver.ReceiverRecord

@Component
class EmployeeEventConsumer(
    private val kafkaReceiver: KafkaReceiver<String, ByteArray>,
) {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    private val disposables = Disposables.composite()

    @PostConstruct
    fun connect() {
        disposables.add(
            receive()
                .subscribe()
        )
    }

    fun receive(): Flux<ReceiverRecord<String, ByteArray>> {
        return kafkaReceiver.receive()
            .doOnNext { logger.info("Receiving record : $it") }

    }

    @PreDestroy
    fun disconnect() {
        this.disposables.dispose()
    }

}