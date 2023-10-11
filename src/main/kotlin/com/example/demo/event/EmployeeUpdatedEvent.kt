package com.example.demo.event

import java.util.*

data class EmployeeUpdatedEvent(
    override var id: UUID
): EmployeeEventInterface {
    var message: String = "An employee has been updated"
}