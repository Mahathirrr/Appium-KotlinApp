Feature: Welcome Screen
  As a new user
  I want to see the welcome screen
  So that I can choose to create account or sign in

  Background:
    Given the app is launched
    And I am on the welcome screen

  @smoke @ui
  Scenario: Display welcome screen
    Then I should see the welcome page
    And I should see the app logo
    And I should see the create account button
    And I should see the sign in button

  @ui
  Scenario: Verify welcome screen elements
    Then the create account button should have text
    And the sign in button should have text
    And both buttons should be enabled
    And both buttons should be clickable

  @functional @navigation
  Scenario: Navigate to register page
    When I click the create account button
    Then I should be navigated to the register page

  @functional @navigation
  Scenario: Navigate to login page
    When I click the sign in button
    Then I should be navigated to the login page

  @ui
  Scenario: Verify branding elements
    Then I should see the app title
    And I should see the welcome title
    And I should see the logo