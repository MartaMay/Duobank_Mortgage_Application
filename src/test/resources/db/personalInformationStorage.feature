#@REGRESSION
Feature: As a user of Duobank Mortgage Application,
  I want to store the personal information of the borrower and co-borrower (if applicable) entered on
  the Personal Information page of the mortgage application form in the database.

  @db_only
  Scenario:  Validate columns in tbl_mortagage table
    Then the "tbl_mortagage" table should have the following columns:
      | apply_co_borrower |
      | b_firstName       |
      | b_middleName      |
      | b_lastName        |
      | b_suffix          |
      | b_email           |
      | b_dob             |
      | b_ssn             |
      | b_marital         |
      | b_cell            |
      | b_home            |
  @db_only
  Scenario: Ensure ID column is an auto-incremented primary key
    Then the "id" column in "tbl_mortagage" table should have an auto-incrementing primary key
  @db_only
  Scenario: Validate alphabetical entry in name columns
    Given User with id = 1 exists in the "tbl_mortagage" table db
    When I enter non-alphabetical characters in the first, middle, and last name fields
    Then the entry should be rejected
