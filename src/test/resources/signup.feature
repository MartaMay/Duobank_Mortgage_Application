
Feature: Account sign up for Bank Mortgage Application
  As a potential customer,
  I want to sign up for an account on the bank mortgage application
  So that I can start the process of applying for a mortgage.


  Background:

  Given User is on the login page of the bank mortgage application
  And User navigates to the sign up page


  Scenario: Verify input fields
    Then User should see that sign up page includes input fields for the user's First Name, Last Name, Email Address, and Password

  Scenario: Attempt to sign up with missing required fields
    When User doesn't enter any data in the First Name, Last Name, Email Address, and Password fields
    And User attempts to click the Sign Up button
    Then User should see validation errors for all required fields


  Scenario: Sign up with valid data
    When User fills all required fields for First Name, Last Name, Email and Password with the following data
      |Mark|
      |Johnson|
      |mark.johnson@example.com|
      |Password123|
    And User clicks the Sign Up button
    Then User should see a Registration Successful message
    And User should be redirected to the Login page

  Scenario: Attempt to sign up with an existing email address
    When User attempts to sign up with existing email
    Then User should see an error message This email already used

  Scenario: Navigate to Sign In page from Sign Up page
    When User clicks on the Already have an account? Sign in link
    Then User should be redirected to the Sign In page





