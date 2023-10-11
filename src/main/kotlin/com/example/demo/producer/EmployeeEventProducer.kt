package com.example.demo.producer

import com.example.demo.config.MESSAGE_TOPIC_NAME
import com.example.demo.event.EmployeeEventInterface
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class EmployeeEventProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    fun send(employeeEvent: EmployeeEventInterface): Mono<Void> {
        kafkaTemplate.send(MESSAGE_TOPIC_NAME, employeeEvent.toString())

        return Mono.empty()
    }
}