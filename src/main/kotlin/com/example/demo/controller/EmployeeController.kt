package com.example.demo.controller

import com.example.demo.entity.Employee
import com.example.demo.event.EmployeeCreatedEvent
import com.example.demo.event.EmployeeDeletedEvent
import com.example.demo.event.EmployeeUpdatedEvent
import com.example.demo.producer.EmployeeEventProducer
import com.example.demo.service.EmployeeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kafka.sender.SenderResult
import java.util.UUID

@RestController
class EmployeeController(
    private val service: EmployeeService,
    private val producer: EmployeeEventProducer,
) {
    @GetMapping("/")
    fun getAll(): Flux<Employee> = service.allEmployees()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): Mono<Employee> = service.findEmployeeById(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/")
    fun create(@Valid @RequestBody employee: Employee): Mono<Employee?>? = service
        .save(employee)
        .flatMap {
            producer.send(EmployeeCreatedEvent(it?.getEmployeeIdAsUuid() as UUID))
        }
        .thenReturn(employee)


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): SenderResult<Void>? = service
        .delByEmpId(id)
        .then(
            producer.send(EmployeeDeletedEvent(id))
        )
        .block()

    @PutMapping("/{id}")
    fun update(@Valid @RequestBody employee: Employee, @PathVariable id: UUID) = service
        .update(employee, id)
        .map {
            producer.send(EmployeeUpdatedEvent(id))

            return@map it
        }
        .block()
}