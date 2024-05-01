
@API
Feature: As a user of the mortgage application,
  I want to be able to login using my email and password through an API endpoint,
  so that I can access my personal information and apply for a mortgage loan.

  Background:
    Given User is on the login page of the bank mortgage application


  Scenario: Accept POST request at /login

  Given the request is authenticated with a valid API key
  And the request "accept" header is set to "application/json"
  And the request "Content-type" header is set to "application/json"
  And the request body is set to the following payload as

  """
  {
  "email": "%s",
  "password": "%s"
}
  """
    When the user sends a "POST" request to "/login"
  Then the response log should be displayed
  Then the response status should be 200


  Scenario: Require API key authentication

    Given the request specification is reset
    Given the user does not provide an API key
    And the request "Content-type" header is set to "application/json"
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
#    Then the response status should be 401
    And the response body should have "message" field with value "Invalid or missing API Key. Provide a valid api key with 'api_key' query parameter."


  Scenario: Return JSON response with appropriate headers

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request body is set to the following payload as

         """
          {
            "email": "%s",
             "password": "%s"
          }
          """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    And the response "Content-Type" header should be "application/json; charset=UTF-8"



  Scenario: Reject non-POST methods
#bug
    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set to the following payload as
         """
          {
            "email": "%s",
             "password": "%s"
          }
          """
    When the user sends a "DELETE" request to "/login"
    Then the response log should be displayed
    Then the response status should be 405
    And the response body should have "message" field with value "Method Not Allowed"


  Scenario: Missing password

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set as the following payload
         """
          {
            "email": "mark.johnson@example.com",
             "password": ""
          }
          """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the response status should be 422
    And the response body should have "message" field with value "Please fill in all required fields!"


  Scenario: Validate email format

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request body is set as the following payload
         """
          {
            "email": "useratexample",
             "password": "Password123"
          }
          """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the response status should be 422
    And the response body should have "message" field with value "Invalid Email Address!"



  Scenario: Invalid password
#    bug
    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request body is set as the following payload
         """
          {
            "email": "mark.johnson@example.com",
             "password": "123"
          }
          """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the response status should be 422
    And the response body should have "message" field with value "Invalid Password!"


  Scenario: Generate JWT token for valid credentials

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set to the following payload as

  """
  {
  "email": "%s",
  "password": "%s"
}
  """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the API should generate a temporary JWT token

  Scenario: Successful login response

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set to the following payload as

  """
  {
  "email": "%s",
  "password": "%s"
}
  """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    And the response body should have "message" field with value "You've successfully logged in!"


  Scenario: API response format on successful login

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set to the following payload as
  """
  {
  "email": "%s",
  "password": "%s"
}
  """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then The API response payload should be in the format:
  """
  {
    "success": true,
    "message": "You've successfully logged in!",
    "access_token": "%s",
    "token_type": "Bearer",
    "expires_in": 3600
}
  """


  Scenario: Ensure quick response time

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request body is set to the following payload as
  """
  {
  "email": "%s",
  "password": "%s"
}
  """
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the response time should be less than 1000 ms

    @api_bad_server
  Scenario: Handle server-side errors

    Given the request specification is reset
    And the server encounters an unexpected error
    Given the request is authenticated with a valid API key
    And the request body is set as the following payload
         """
          {
            "email": "mark.johnson@example.com",
             "password": "123"
          }
          """
    When the user sends a "GET" request to "/login"
    Then the response log should be displayed
    Then the response status should be 500
