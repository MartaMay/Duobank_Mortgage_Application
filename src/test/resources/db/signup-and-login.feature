#@REGRESSION
Feature: As a user of Duobank Mortgage Application,
  I want to be able to sign up and log in securely using a database system that protects my personal
  and financial information,
  So that I can complete the mortgage application process with confidence.

  @db_only
  Scenario: Validate tbl_users table schema and data storage
    Then "tbl_user" table should have the following columns:
      | id          |
      | email       |
      | password    |
      | first_name  |
      | last_name   |
      | phone       |
      | image       |
      | type        |
      | created_at  |
      | modified_at |
      | zone_id     |
      | church_id   |
      | country_id  |
      | active      |

  @db
    @db_only
  Scenario: Verify data mapping of user registration process

    Given User is on the login page of the bank mortgage application
    And User navigates to the sign up page
    When User fills out fields with valid information
    And User clicks the Sign Up button
    And User should be created in the database
    Then The data should be mapped correctly to the following columns in the "tbl_user" table:
      | first_name |
      | last_name  |
      | email      |
      | password   |
  @db
    @db_only
  Scenario: : Unique Email Validation
    Given there is already an account registered with the email "mark.johnson@example.com"
    When user tries to register another account with the email "mark.johnson@example.com"
    Then the registration should fail and system should have only one user with registered email


  @db
    @db_only
  Scenario: Password encryption in DB
    Given User is on the login page of the bank mortgage application
    And User navigates to the sign up page
    When User fills out fields with valid information
    And User clicks the Sign Up button
    And User should be created in the database
    Then The password should be stored as an MD5 hash in DB
