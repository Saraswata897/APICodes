# src/test/resources/features/api_tests.feature
@api
Feature: API Test Scenarios

  Scenario: Verify GET API status code 200 for USA Population 2021
    Given the API base URI is "https://datausa.io"
    And the API endpoint is "/api/data"
    And the query parameters are "drilldowns=Nation&measures=Population&Year=2021"
    When I send a GET request
    Then the response status code should be 200
