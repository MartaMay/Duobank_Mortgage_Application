@REGRESSION
Feature: As a potential homebuyer,
  I want to use the Personal Information page of my bank's mortgage application
  to provide my personal details and co- borrower's details (if applicable)
  So that I can move forward with the mortgage application process.

  Background:
    Given User is logged into the bank mortgage application.
    And User fills out mortgage application
    And User is on the Personal Information page

  Scenario: Co-borrower Application Option
    When User encounters the "Are you applying with a co-borrower?" question
    Then There should be checkboxes with options "Yes" and "No" for the user to select.

  Scenario: Displaying Co-borrower Information Section
    When User selects "Yes" for the co-borrower question
    Then An additional section for entering co-borrower's information should appear on the page.

  Scenario: Required Fields Validation
    When User clicks on "Next" button
    Then The first name, last name, email, date of birth, SSN, marital status, and cell phone fields should be marked as required and not allow the user to proceed without completing them.

  Scenario: Privacy Policy Acknowledgment
    When User clicks on Privacy Policy section
    And User is redirected to Privacy terms window
    When User is navigating back
    Then User is on the Personal Information page
    And User has read and accepted the terms of the Privacy Policy

  Scenario Outline: Entering personal information for Borrower only
    When User selects "No" for applying with a co-borrower
    And User enters personal information:
      | FirstName   | LastName   | Email   | DOB   | SSN   | CellPhone    |
      | <FirstName> | <LastName> | <Email> | <DOB> | <SSN> | <CellPhone>  |
    Then User clicks on "Next" button
    Then User is on the "Expenses" page
    Examples:
      | FirstName | LastName | Email              | DOB        | SSN         | CellPhone    |
      | John      | Doe      | john.doe@email.com | 01/01/1980 | 123-45-6789 | 123-456-7890 |

  Scenario Outline: Entering personal information for Borrower with Co-Borrower
    When User selects "Yes" for the co-borrower question
    And User enters personal information:
      | FirstName   | LastName   | Email   | DOB   | SSN   | CellPhone   |
      | <FirstName> | <LastName> | <Email> | <DOB> | <SSN> | <CellPhone> |
    And User enters co-borrower's information:
      | FirstName     | LastName     | Email     | DOB     | SSN     | CellPhone     |
      | <CoFirstName> | <CoLastName> | <CoEmail> | <CoDOB> | <CoSSN> | <CoCellPhone> |
    And User has read and accepted the terms of the Privacy Policy
    Then User clicks on "Next" button
    Then User is on the "Expenses" page

    Examples:
      | CoFirstName | CoLastName | CoEmail               | CoDOB      | CoSSN       | CoCellPhone  |
      | Bob         | Johnson    | bob.johnson@email.com | 03/03/1985 | 222-33-4444 | 987-123-4567 |

  Scenario: User fills out Personal Information page
    When User fills out Personal Information page
    Then User is on the "Expenses" page