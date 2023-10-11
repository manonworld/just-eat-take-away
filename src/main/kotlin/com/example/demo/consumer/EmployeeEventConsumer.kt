package com.example.demo.consumer

import com.example.demo.config.GROUP_ID
import com.example.demo.config.MESSAGE_TOPIC_NAME
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class EmployeeEventConsumer {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    @KafkaListener(topics = [MESSAGE_TOPIC_NAME], groupId = GROUP_ID)
    fun listen(employeeEvent: String) {
        logger.info("Message received: [${employeeEvent}]")
    }

}