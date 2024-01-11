package com.example.demo.producer

import com.example.demo.config.MESSAGE_TOPIC_NAME
import com.example.demo.event.EmployeeEventInterface
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult

@Component
class EmployeeEventProducer(
    private val kafkaTemplate: ReactiveKafkaProducerTemplate<String, String>
) {
    fun send(employeeEvent: EmployeeEventInterface): Mono<SenderResult<Void>> {
        return kafkaTemplate.send(MESSAGE_TOPIC_NAME, employeeEvent.toString())
    }
}