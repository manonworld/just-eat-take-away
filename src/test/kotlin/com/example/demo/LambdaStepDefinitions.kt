package com.example.demo

import io.cucumber.java8.En
import io.restassured.RestAssured.*
import io.restassured.http.ContentType
import io.restassured.response.ValidatableResponse
import org.hamcrest.Matchers.*

class LambdaStepDefinitions: En {

    lateinit var json: String
    lateinit var fixedJson: String
    lateinit var call: ValidatableResponse
    lateinit var employeeIdToDelete: String
    lateinit var employeeIdToUpdate: String
    lateinit var updateJson: String

    init {
        val alphabet: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        val randomString: String = List(20) { alphabet.random() }.joinToString("")

        Given("I have an employee data") {
            // Should be moved to data table inside the .feature file but this just for the sake of interview task
            json =
                "{ \"email\": \"test18@$randomString.de\", \"firstName\": \"first18\", \"lastName\": \"last18\", \"birthDay\": \"1984-01-02\", \"hobbies\": [ { \"hobby\": \"tennis\" } ] }"
        }

        Given("I have an already existing employee data") {
            // Should be moved to data table inside the .feature file but this just for the sake of interview task
            fixedJson = "{ \"email\": \"test18@fixed.de\", \"firstName\": \"first18\", \"lastName\": \"last18\", \"birthDay\": \"1984-01-02\", \"hobbies\": [ { \"hobby\": \"tennis\" } ] }"
            updateJson = "{ \"email\": \"test18@fixed.de\", \"firstName\": \"updatedFirstName\", \"lastName\": \"last18\", \"birthDay\": \"1984-01-02\", \"hobbies\": [ { \"hobby\": \"tennis\" } ] }"
        }

        Given("I have the required employees information") {
            val employees: ArrayList<LinkedHashMap<String, String>> = given()
                .contentType(ContentType.JSON)
                .get("/")
                .jsonPath()
                .get("$")

            for (employee: LinkedHashMap<String, String> in employees) {
                if(employee.get("email") == "test18@fixed.de") {
                    employeeIdToDelete = employee.get("employeeId")!!
                    employeeIdToUpdate = employee.get("employeeId")!!
                }
            }
        }

        When("Employee is registered already") {
            given()
                .contentType(ContentType.JSON)
                .body(fixedJson)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .post("/")
                .then()

            call = given()
                .contentType(ContentType.JSON)
                .body(fixedJson)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .post("/")
                .then()
        }

        When("I insert his record") {
            call = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .body(json)
                .post("/")
                .then()
        }

        When("I list all employees registered on the system") {
            call = given()
                .contentType(ContentType.JSON)
                .get("/")
                .then()
        }

        When("I delete this employees data") {
            call = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .delete("/" + employeeIdToDelete)
                .then()
        }

        When("I try to find this specific employee") {
            val employees: ArrayList<LinkedHashMap<String, String>> = given()
                .contentType(ContentType.JSON)
                .get("/")
                .jsonPath()
                .get("$")

            call = given()
                .contentType(ContentType.JSON)
                .get("/" + employees[0].get("employeeId"))
                .then()
        }

        When("I try to find this specific employee but with wrong data") {
            call = given()
                .contentType(ContentType.JSON)
                .get("/930d31de-9993-4d70-8352-ca1620588475") // Non-Existing ID
                .then()
        }

        When("I try to delete this specific employee but with wrong data") {
            call = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .delete("/930d31de-9993-4d70-8352-ca1620588475") // Non-Existing ID
                .then()
        }

        When("I try to update this specific employee with the correct data") {
            call = given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .body(updateJson)
                .put("/" + employeeIdToUpdate)
                .then()
        }

        When("I try to update this specific employee with incorrect data") {
            call = given()
                .contentType(ContentType.JSON)
                .body(updateJson)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .put("/930d31de-9993-4d70-8352-ca1620588475") // Non-Existing ID
                .then()
        }

        Then("I should be notified that the employee already exists") {
            call
                .statusCode(409)
        }

        Then("I should find the employee is created") {
            call
                .statusCode(201)
        }

        Then("I should find the employee data") {
            call
                .statusCode(200)
                .body("size()", greaterThan(0))
        }

        Then("I should get deletion confirmation") {
            call
                .statusCode(200)

            // reinsert it again so that tests can succeed when invoking it again.

            given()
                .contentType(ContentType.JSON)
                .body(fixedJson)
                .header("Authorization", "Basic dXNlcjpwYXNz")
                .post("/")
                .then()
        }

        Then("I should not find the employee data") {
            call
                .statusCode(404)
        }

        Then("I should be notified that the employee has been updated") {
            call
                .statusCode(200)
        }
    }

}