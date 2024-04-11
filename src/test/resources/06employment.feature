@REGRESSION
  Feature:  As a user applying for a mortgage loan,
    I want to provide my employment and income details to the bank,
    So that they can evaluate my eligibility for a loan.

    Background:

      Given User is logged into the bank mortgage application.
      And User fills out mortgage application
      And User fills out Personal Information page
      And User fills out required fields for Current Monthly Housing Expenses
      And User is on the Employment Information section

    Scenario: Ensure all employment fields are displayed correctly

      Then EMPLOYER NAME, POSITION, CITY, STATE, START DATE, and END DATE fields should be displayed
      And EMPLOYER NAME field should be required
      And STATE dropdown should list all 50 US states with two-letter abbreviations
      And The This is my current job checkbox should be unchecked by default

  Scenario: Add current Employment Information
    Then User fills out current Employment Information Section with following data:
      | BrightSide Corp     | Manager   | Boston   | Massachusetts (MA)    | 02/01/2022 |
    And User clicks Add Another Employer


    Scenario: Add Past Employers
      When User fills out current Employment Information Section with following data:
        | BrightSide Corp     | Manager   | Boston   | Massachusetts (MA)    | 02/01/2022 |
      And User clicks Add Another Employer
      Then User adds previous Employment Information:
        | employerName | Digital Corp  | NewOn Inc          | Mash LLC     |
        | position     | Developer     | Manager            | QA           |
        | city         | New York      | Boston             | Miami        |
        | state        | New York (NY) | Massachusetts (MA) | Florida (FL) |
        | startDate    | 07/01/2020    | 11/05/2015         | 02/17/2013   |
        | endDate      | 01/01/2022    | 07/03/2020         | 10/27/2015   |

    Scenario: Clear employment information
      When User fills out current Employment Information Section with following data:
        | BrightSide Corp     | Manager   | Boston   | Massachusetts (MA)    | 02/01/2022 |
      And User clicks the Clear button
      Then A warning popup should be displayed confirming the action
      And Clicking Yes! on the popup should clear only the information in that section

@M
    Scenario Outline: Validate Gross Monthly Employment Income
      When User enters income information

    | GROSS_MONTHLY_INCOME | MONTHLY_OVERTIME | MONTHLY_BONUSES | MONTHLY_COMMISSIONS | MONTHLY_DIVIDENDS_INTEREST | TOTAL_MONTHLY_INCOME |
    | <gross>              | <overtime>       | <bonuses>       | <commissions>       | <dividends_interest>       | <total_monthly_income> |

      Then the "Borrower Total Monthly Income" should be calculated correctly
      Examples:
        | gross  | overtime | bonuses | commissions | dividends_interest | total_monthly_income |
        | 5000.00| 300.00   | 250.00  | 150.00      | 100.00             | 5800.00              |


    Scenario: Validate Additional Gross Monthly Income section
      Then Section should contain three sets of INCOME SOURCE dropdowns
      And INCOME SOURCE dropdown should include options like Alimony, Child Support and Social Security, Disability Income
@M
    Scenario: Incomplete required fields
      When Not all required fields are filled in
      And User clicks on "Next" button
      Then An error message should indicate the required fields that need to be filled in

