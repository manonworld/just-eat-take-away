Feature: Message

  Background:
    Given I have an employee data
    And I have an already existing employee data
    And I have the required employees information

  Scenario: Creating a user
    When I insert his record
    Then I should find the employee is created

  Scenario: Creating a user which already exists
    When Employee is registered already
    Then I should be notified that the employee already exists

  Scenario: Employee data should be listed
    When I list all employees registered on the system
    Then I should find the employee data

  Scenario: Employee data should be deleted
    When I delete this employees data
    Then I should get deletion confirmation

  Scenario: Get a specific existing employee
    When I try to find this specific employee
    Then I should find the employee data

  Scenario: Get an employee which does not exist
    When I try to find this specific employee but with wrong data
    Then I should not find the employee data

  Scenario: Delete non existing employee
    When I try to delete this specific employee but with wrong data
    Then I should not find the employee data

  Scenario: Update an existing employee
    When I try to update this specific employee with the correct data
    Then I should be notified that the employee has been updated

  Scenario: Update non existing employee
    When I try to update this specific employee with incorrect data
    Then I should not find the employee data