package com.example.demo.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document
data class Employee(
    @JsonIgnore @Id var id: String?,

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private var employeeId: UUID?,

    @field:NotBlank @field:NotNull @field:Email @Indexed(unique = true) var email: String?,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @field:NotBlank @field:NotNull @field:Pattern(regexp = "^[a-zA-Z0-9]{2,255}$")
    var firstName: String?,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(accessMode = Schema.AccessMode.WRITE_ONLY)
    @field:NotBlank @field:NotNull @field:Pattern(regexp = "^[a-zA-Z0-9]{2,255}$")
    var lastName: String?,

    @field:NotBlank @field:NotNull
    @JsonFormat(pattern = "YYYY-MM-DD")
    @field:Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$")
    var birthDay: String?,

    @field:Valid
    @field:NotNull
    var hobbies: List<Hobby>?
) {
    fun setEmployeeId(id: UUID?) {
        this.employeeId = id
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    fun getFullName(): String = this.firstName + " " + this.lastName

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    fun getEmployeeId(): String = this.employeeId.toString()

    @JsonIgnore
    fun getEmployeeIdAsUuid(): UUID? = this.employeeId

}