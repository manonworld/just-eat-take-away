package com.example.demo.event

import java.util.*

class EmployeeDeletedEvent(
    override var id: UUID
): EmployeeEventInterface {
    var message: String =  "An employee has been deleted"
}