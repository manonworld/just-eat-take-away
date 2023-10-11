package com.example.demo.repository

import com.example.demo.entity.Employee
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono
import java.util.*


interface ReactiveEmployeeRepository: ReactiveMongoRepository<Employee, UUID> {
    fun findByEmployeeId(employeeId: UUID?): Mono<Employee>

    fun deleteByEmployeeId(id: UUID?): Mono<Void>
}