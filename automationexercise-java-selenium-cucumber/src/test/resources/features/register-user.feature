@Smoke
Feature: Register a new user

  Background:
    Given the user navigates to URL
    Then the home page should be displayed successfully

  Scenario: Successfully register and delete a new user account
    When the user clicks the Signup / Login button
    Then the New User Signup! section should be visible

    When the user signs up as a new user
    Then the ENTER ACCOUNT INFORMATION heading should be visible

    When the user enters valid account information
    And the user subscribes to the newsletter and partner offers
    And the user enters valid address information
    And the user clicks the Create Account button

    Then the ACCOUNT CREATED! message should be visible

    When the user clicks the Continue button
    Then the user should be logged in successfully

    When the user clicks the Delete Account" button
    Then the ACCOUNT DELETED! message should be visible
