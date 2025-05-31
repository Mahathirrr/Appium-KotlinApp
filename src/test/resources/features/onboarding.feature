Feature: Onboarding Flow
  As a new user
  I want to see the onboarding screens
  So that I can understand the app's features

  Background:
    Given the app is launched
    And I am on the onboarding screen

  @smoke @ui
  Scenario: Display onboarding screens
    Then I should see the onboarding page
    And I should see the phone repairs page first
    And I should see the next button
    And I should see the skip button

  @functional
  Scenario: Navigate through onboarding using next button
    When I click the next button
    Then I should see the laptop services page
    When I click the next button
    Then I should see the TV repairs page
    And I should see the get started button

  @functional
  Scenario: Navigate through onboarding using swipe
    When I swipe to the next page
    Then I should see the laptop services page
    When I swipe to the next page
    Then I should see the TV repairs page
    When I swipe to the previous page
    Then I should see the laptop services page

  @functional
  Scenario: Skip onboarding
    When I click the skip button
    Then I should be navigated to the welcome page

  @functional
  Scenario: Complete onboarding
    When I click the next button
    And I click the next button
    And I click the get started button
    Then I should be navigated to the welcome page

  @ui
  Scenario: Verify onboarding indicators
    Then I should see 3 page indicators
    When I click the next button
    Then the page indicator should update