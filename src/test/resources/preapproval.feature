@REGRESSION
Feature: As a potential homebuyer, I want to use the Preapproval Details page of
  my bank's mortgage application to provide the necessary information to
  get pre-approved for a mortgage loan, including information about my
  realtor, loan purpose, purchase price, down payment amount and
  percentage, loan amount, source of down payment, and additional
  available funds for closing costs or reserves.

@mine
  Background:
    Given User is on the login page of the bank mortgage application
    And User navigates to the Mortgage page

  Scenario: Checkbox Implementation for Realtor Question
    When User encounters the Are you working with a realtor? field
    Then User should see checkboxes with options Yes and No

  Scenario: Enable Realtor Information
    When User selects Yes for the realtor question
    Then the Realtor Information field should be enabled, allowing user to enter the realtor's name and contact details

  Scenario: Realtor Information Field Requirement
    Given User selects Yes for the realtor question
    When User tries to proceed to the next page
    Then the Realtor Information field should be required before moving forward