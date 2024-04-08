@my
Feature:
  As a potential homebuyer,
  I want to use the Expenses page of my bank's mortgage application
  to provide information about my current living situation and expenses,
  So that I can move forward with the mortgage application process.


  Background:
    Given User is logged into the bank mortgage application.
    And User fills out mortgage application
    And User fills out Personal Information page
    And User is on the "Current Monthly Housing Expenses" page

  Scenario: Verify Checkbox Functionality for Housing Status

    When User reads the "A monthly housing expense refers" field
    Then There should be two checkboxes labeled "Rent" and "Own"

  Scenario: Rental Payment Validation
    When User has selected "Rent" on the Expenses page
    And User enters negative value "-1500" into the "Monthly Rental Payment" field
    And User should see an error message Please enter a value greater than or equal to 0
    Then User enters non numeric characters "@#$" into the "Monthly Rental Payment" field
    And User should see an error message Please enter a valid number." for a non-numeric value

  Scenario: Mortgage Payment Validation
    When User has selected "Own" on the Expenses page
    And User enters negative value "-2500" into the "Monthly Mortgage Payment" field
    And User should see an error message Please enter a value greater than or equal to 0 for Monthly Mortgage Payment

  Scenario: Fill out Current Monthly Housing Expenses
    When User fills out required fields for Current Monthly Housing Expenses
    Then User should be redirected to the Employment and Income Page