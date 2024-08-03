
#@REGRESSION
Feature: As a loan officer,
  I want to be able to retrieve a list of mortgage applications for a specific user,
  So that I can review their application status and history.

  Background: Info
    Given There is an existing user in the database with id 12390
    And user is a "admin" with type set to 1


  Scenario: Retrieve loan officers information

    And the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "id" queryParam is set to users id
    When the user sends a "GET" request to "/user"
    Then the response log should be displayed
    Then the response should contain users email



    Scenario: Generate JWT token for loan officer

    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "id" queryParam is set to users id
    And the request body is set as following
         |email   | jane.sage@hotmail.com |
         |password| Hello123 |
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the response status should be 200
    And a temporary JWT token is generated


  Scenario: Access the endpoint via GET request
    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "Authorization" header is set with admin JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response status should be 200
    And the response time should be less than 1000 ms

  Scenario: Ensure JSON response and appropriate Content-Type
    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "Authorization" header is set with admin JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response status should be 200
    And the request "Content-type" header is set to "application/json"


  Scenario: Require an API key for authentication
    Given the request specification is reset
    And the user does not provide an API key
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response status should be 401
    And the response body should have "message" field with value "Invalid or missing API Key. Provide a valid api key with 'api_key' query parameter."


  Scenario: Require a valid JWT token
    Given the request specification is reset
    And the request is authenticated with a valid API key
    Given the user provides an invalid JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response status should be 403
    And the response body should have "message" field with value "Invalid or missing JSON Web Token. Please log in with your credentials to obtain a valid JWT token and provide it in the 'Authorization' header of your request."


  Scenario: Admin user retrieves all applications
    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "Authorization" header is set with admin JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response should include a list of applications
#
#  Scenario: Order applications by creation date
#    When the user sends a GET request to "/applications"
#    Then the applications should be ordered by creation date from newest to oldest
#
  Scenario: List of mortgage applications contains all required details
    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "Authorization" header is set with admin JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response should include a list of applications
    And each application should contain
      | id                |
      | b_firstName       |
      | b_lastName        |
      | b_middleName      |
      | total_loan_amount |


  @d
  Scenario: Non-admin user retrieves only their applications
    Given User is on the login page of the bank mortgage application
    When User enters valid email and password and clicks Sign In
    Then User should be redirected to the homepage of application
    And Users first and last name is displayed in the top right corner
    Given non-admin user is retrieved from the database
    Given the request specification is reset
    And JWT token is generated for non-admin user
    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "Authorization" header is set with users JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response log should display only users applications

