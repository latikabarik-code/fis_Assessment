Feature: Bitcoin API Validation

  Scenario: Validate bitcoin data from coingecko
    Given I send a request to the bitcoin API
    Then I should see USD, GBP, and EUR prices
    And the market cap and total volume are present
    And price change in last 24 hours is available
    And the homepage URL is not empty
    Then generate bitcoin API report