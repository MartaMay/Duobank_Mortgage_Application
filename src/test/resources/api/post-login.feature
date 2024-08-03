#@REGRESSION
Feature: As a user of the mortgage application,
  I want to be able to login using my email and password through an API endpoint,
  so that I can access my personal information and apply for a mortgage loan.


  Scenario: Accept POST request at /login

  Given the request is authenticated with a valid API key
  And the request "accept" header is set to "application/json"
  And the request "Content-type" header is set to "application/json"
  And the request body is set with email and password
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
    And the request body is set with email and password
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    And the response "Content-Type" header should be "application/json; charset=UTF-8"



  Scenario: Reject non-POST methods
#bug
    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set with email and password
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
             "password": "123HelloHello"
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
    And the request body is set with email and password
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the API should generate a temporary JWT token

  Scenario: Successful login response

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set with email and password
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    And the response body should have "message" field with value "You've successfully logged in!"

@api_test
  Scenario: API response format on successful login

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request "accept" header is set to "application/json"
    And the request "Content-type" header is set to "application/json"
    And the request body is set with email and password
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then The API response payload should be in the format:
  """
  {
    "success": true,
    "message": "You've successfully logged in!",
    "access_token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2xvYW5cL2FwaSIsImF1ZCI6Imh0dHA6XC9cL2xvY2FsaG9zdFwvbG9hblwvYXBpIiwiaWF0IjoxNzE0NjAwNDEzLCJleHAiOjE3MTQ2MDQwMTMsImRhdGEiOnsidXNlcl9pZCI6IjExOTYyIiwidHlwZSI6IjIifX0.9YogDeVJLj7o_xkuuN_96MkO6kaJKr-gp9dfNpENmb0",
    "token_type": "Bearer",
    "expires_in": 3600
}
  """


  @api_test
  Scenario: Ensure quick response time

    Given the request specification is reset
    Given the request is authenticated with a valid API key
    And the request body is set with email and password
    When the user sends a "POST" request to "/login"
    Then the response log should be displayed
    Then the response time should be less than 1000 ms

    @api_bad_server
  Scenario: Handle server-side errors

    Given the request specification is reset
    And the server encounters an unexpected error
    Given the request is authenticated with a valid API key
#    And the request body is set as the following payload
#         """
#          {
#            "email": "mark.johnson@example.com",
#             "password": "123"
#          }
#          """
      And the requested body is set as
      |email1@gmail.com|
      |email2@gmail.com|
      |email3@gmail.com|
    When the user sends a "GET" request to "/login"
    Then the response log should be displayed
    Then the response status should be 500
