#@Smoke
Feature: Login User with correct email and password

  Background:
    Given the user navigates to URL
    Then the home page should be displayed successfully

  Scenario: Successfully login with correct credentials
    When the user clicks the Signup / Login button
    Then the Login to your account section should be visible
    When the user enters incorrect email "joedoe@yahoo.com" and password "654321$"
    Then Verify error 'Your email or password is incorrect!' is visible