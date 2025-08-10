Feature: Add to cart functionality

  Scenario: Verify User adds a book to the cart
    Given user is on the eBay homepage
    When user searches for "book"
    And selects the first product
    And adds the product to the cart
    Then the product should be added to the cart