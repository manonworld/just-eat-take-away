package com.example.demo.event

import java.util.*

data class EmployeeCreatedEvent(
    override var id: UUID,
): EmployeeEventInterface {
    var message: String =  "An employee has been created"
}