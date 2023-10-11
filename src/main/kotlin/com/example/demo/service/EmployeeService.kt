package com.example.demo.service

import com.example.demo.entity.Employee
import com.example.demo.repository.ReactiveEmployeeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.util.*


@Service
class EmployeeService(val db: ReactiveEmployeeRepository) {

    fun allEmployees(): Flux<Employee> = db.findAll()

    fun findEmployeeById(id: UUID?): Mono<Employee> {
        return db.findByEmployeeId(id)
            .switchIfEmpty(Mono.error(
                ResponseStatusException(
                    HttpStatus.NOT_FOUND
                ))
            )
    }

    fun delByEmpId(id: UUID): Mono<Void> {
        return this.findEmployeeById(id).let {
            db.deleteByEmployeeId(it.block()?.getEmployeeIdAsUuid())
        }
    }

    fun save(employee: Employee): Mono<Employee?> {
        employee.setEmployeeId(UUID.randomUUID())

        return db.save(employee)
            .onErrorResume {
                Mono.error( ResponseStatusException(
                    HttpStatus.CONFLICT
                ) )
            }
    }

    fun update(employee: Employee, id: UUID): Mono<Employee?> {
        return db.findByEmployeeId(id).let {
            if (it.block() == null) {
                return Mono.error(
                    ResponseStatusException(
                        HttpStatus.NOT_FOUND
                    ))
            }

            val oldEmpData = it.block() as Employee

            if (employee.email != null) oldEmpData.email = employee.email
            if (employee.firstName != null) oldEmpData.firstName = employee.firstName
            if (employee.lastName != null) oldEmpData.lastName = employee.lastName
            if (employee.birthDay != null) oldEmpData.birthDay = employee.birthDay
            if (employee.hobbies!!.isNotEmpty()) oldEmpData.hobbies = employee.hobbies

            oldEmpData.setEmployeeId(id)

            db.save(oldEmpData)
        }.switchIfEmpty(Mono.error(
            ResponseStatusException(
                HttpStatus.NOT_FOUND
            )))
    }
}