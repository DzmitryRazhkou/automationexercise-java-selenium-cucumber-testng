@Smoke
Feature: Login User with correct email and password

  Background:
    Given the user navigates to URL
    Then the home page should be displayed successfully

  Scenario: Successfully login with correct credentials
    When the user clicks the Signup / Login button
    Then the Login to your account section should be visible
    When the user enters email "joedoe@gmail.com" and password "123456$"
    Then the user should be logged in successfully as "Joe Doe"