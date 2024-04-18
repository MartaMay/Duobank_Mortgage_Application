Feature: Preapproval Details in Database
  As a user of Duobank Mortgage Application
  I want my Preapproval Details page data to be stored in the database
  So that I can access it later and the bank can process my mortgage application

  @db_only

  Scenario: Verify data mapping of Preapproval Details page

    Given User is on the login page of the bank mortgage application
#    And User navigates to the sign up page
#    When User fills out fields with valid information
#    And User clicks the Sign Up button
#    And User logs in
    And User is logged into the bank mortgage application.
    And User navigates to the Mortgage page
    And User fills out mortgage application
    And User clicks on "Next" button
    Then The data should be mapped correctly to the following columns in the "tbl_mortagage" table
      | id                    |
      | realtor_status        |
      | realtor_info          |
      | loan_officer_status   |
      | purpose_loan          |
      | est_purchase_price    |
      | down_payment          |
      | down_payment_percent  |
      | total_loan_amount     |
      | src_down_payment      |
      | add_fund_available    |

  @db_only
  Scenario: Id column should be an auto-incrementing primary key
    Then the "id" column in "tbl_mortagage" table should have an auto-incrementing primary key

  @db_only
  Scenario: Store realtor and loan officer status as integers
    Then "tbl_mortagage" table should store "realtor_status" as 1 and "loan_officer_status" as 2 in the database

  @db_only
  Scenario: Realtor information, purpose of loan, and source of down payment should be stored as strings
    Then The "realtor_info" should be stored as strings in the "tbl_mortagage" table database
    Then The "purpose_loan" should be stored as strings in the "tbl_mortagage" table database
    Then The "src_down_payment" should be stored as strings in the "tbl_mortagage" table database

  @db_only
  Scenario: Store financial details as integers in DB
    Then The "est_purchase_price", "down_payment", "down_payment_percent", "total_loan_amount" and "add_fund_available" should be stored as integer values in the "tbl_mortgage" table

  @db_only
  Scenario: Ensure unique identifier for each application
    Then each entry should have a unique "id"

