Feature: User Registration
  As a new user
  I want to register for an account
  So that I can use the ElektroniCare services

  Background:
    Given the app is launched
    And I am on the register screen

  @smoke @ui
  Scenario: Display register screen
    Then I should see the register page
    And I should see the full name field
    And I should see the mobile field
    And I should see the email field
    And I should see the password field
    And I should see the create account button

  @functional
  Scenario: Register with empty fields
    When I click the create account button
    Then I should see full name error
    And I should see email error
    And I should see password error

  @functional
  Scenario: Register with invalid email
    When I enter full name "Test User"
    And I enter email "invalid-email"
    And I enter password "password123"
    And I click the create account button
    Then I should see email validation error

  @functional
  Scenario: Register with invalid phone number
    When I enter full name "Test User"
    And I enter mobile "123"
    And I enter email "test@example.com"
    And I enter password "password123"
    And I click the create account button
    Then I should see mobile validation error

  @functional
  Scenario: Register with short password
    When I enter full name "Test User"
    And I enter mobile "081234567890"
    And I enter email "test@example.com"
    And I enter password "123"
    And I click the create account button
    Then I should see password validation error
    And the error should mention "6 characters"

  @functional
  Scenario: Toggle password visibility
    When I enter password "testpassword"
    And I click the password visibility toggle
    Then the password should be visible

  @functional
  Scenario: Register with valid data
    When I enter full name "Test User"
    And I enter mobile "081234567890"
    And I enter email "test@example.com"
    And I enter password "password123"
    And I click the create account button
    Then the registration should be submitted

  @functional @navigation
  Scenario: Navigate to login page
    When I click the sign in link
    Then I should be navigated to the login page

  @functional @navigation
  Scenario: Navigate back
    When I click the back button
    Then I should navigate back

  @functional
  Scenario: Google Sign-In
    When I click the Google sign in button
    Then the Google sign in should be initiated

  @functional
  Scenario: Phone number formatting
    When I enter mobile "8123456789"
    Then the phone number should be formatted correctly