# src/test/resources/features/web_tests.feature
@web
Feature: Web Test Scenarios

  Scenario: Verify Google search page title
    Given I open the browser and navigate to "https://www.google.com"
    Then the page title should be "Google"
