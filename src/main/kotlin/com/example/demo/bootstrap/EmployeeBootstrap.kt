package com.example.demo.bootstrap

import com.example.demo.entity.Employee
import com.example.demo.entity.Hobby
import com.example.demo.repository.ReactiveEmployeeRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class EmployeeBootstrap(val reactiveMessageRepository: ReactiveEmployeeRepository): CommandLineRunner {
    override fun run(vararg args: String?) {
        if (reactiveMessageRepository.count().block()?.toInt() == 0) {
            println("### Started loading data on bootstrap... ###")

            val employee1 = Employee(null, UUID.randomUUID(), "test1@email.de", "first1", "last1", "1984-02-12", listOf(
                Hobby("tennis")
            ))

            val employee2 = Employee(null, UUID.randomUUID(), "test2@email.de", "first2", "last2", "1984-02-14", listOf(
                Hobby("basket")
            ))

            val employee3 = Employee(null, UUID.randomUUID(), "test3@email.de", "first3", "last3", "1984-02-16", listOf(
                Hobby("chess")
            ))

            reactiveMessageRepository.save(employee1).block()
            reactiveMessageRepository.save(employee2).block()
            reactiveMessageRepository.save(employee3).block()

            println("### Finished loading data on bootstrap... ###")
        }
    }
}