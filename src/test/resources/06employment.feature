
  Feature:
    As a user applying for a mortgage loan,
    I want to provide my employment and income details to the bank,
    So that they can evaluate my eligibility for a loan.

    Background:

      Given User is logged into the bank mortgage application.
      And User fills out mortgage application
      And User fills out Personal Information page
      And User fills out required fields for Current Monthly Housing Expenses
      And User is on the Employment Information section
@M
    Scenario: Ensure all employment fields are displayed correctly

      Then EMPLOYER NAME, POSITION, CITY, STATE, START DATE, and END DATE fields should be displayed
      And EMPLOYER NAME field should be required
      And STATE dropdown should list all 50 US states with two-letter abbreviations
      And The This is my current job checkbox should be unchecked by default