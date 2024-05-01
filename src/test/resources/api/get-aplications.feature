
@API
Feature: As a loan officer,
  I want to be able to retrieve a list of mortgage applications for a specific user,
  So that I can review their application status and history.

  Background: Info
    Given There is an existing user in the database with id 12390
    And user is a loan officer with type set to 1


  Scenario: Retrieve loan officers information

    And the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "id" queryParam is set to users id
    When the user sends a "GET" request to "/user"
    Then the response log should be displayed
    Then the response should contain users email


@d
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

  @d

  Scenario: Access the endpoint via GET request
    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "Authorization" header is set with users JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response status should be 200


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

  @api_test
  Scenario: Return list of mortgage applications with required details
    Given the request specification is reset
    And the request is authenticated with a valid API key
    And the request "Authorization" header is set with users JWT token
    When the user sends a "GET" request to "/applications"
    Then the response log should be displayed
    Then the response should include a list of applications
#    And each application should contain
      | id                |
      | b_firstName       |
      | b_lastName        |
      | b_middleName      |
      | total_loan_amount |
#
#  Scenario: Order applications by creation date
#    When the user sends a GET request to "/applications"
#    Then the applications should be ordered by creation date from newest to oldest
#
#  Scenario: Admin user retrieves all applications
#    Given the user is identified as an administrator
#    When the user sends a GET request to "/applications"
#    Then all mortgage applications in the system should be returned
#
#  Scenario: Non-admin user retrieves only their applications
#    Given the user is not identified as an administrator
#    When the user sends a GET request to "/applications"
#    Then only mortgage applications associated with the user's ID should be returned
#
#  Scenario: Return empty list if no applications are associated with the user ID
#    Given the user has no associated mortgage applications
#    When the user sends a GET request to "/applications"
#    Then an empty list should be returned
#
#  Scenario: Ensure JSON response and appropriate Content-Type
#    When the user sends a GET request to "/applications"
#    Then the response header should have Content-Type as "application/json"
#    And the response format should be JSON
#
#  Scenario: Response time should be under 1000 milliseconds
#    When the user sends a GET request to "/applications"
#    Then the response time should be less than 1000 milliseconds