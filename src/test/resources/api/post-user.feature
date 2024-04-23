
  Feature: As a mortgage application user,
    I want to be able to create a new user account,
    So that I can apply for a mortgage and access my account information.


    Feature: Client Creates New User Account
    As a client of the mortgage application
    The client wants to be able to create new user accounts
    So that they can apply for mortgages and manage account information

    Background:
      Given the API is running
      And a valid API key is available

    Scenario: Attempt to create user without API key
      When the client sends a POST request to "/user" without an API key
      Then the API should return a 401 Unauthorized status code
      And the response should include the message "API key required"

    Scenario: Attempt to create user with missing fields
      Given the client sends a POST request to "/user" with empty last_name data
      Then the API should return a 422 Unprocessable Entity status code
      And the response should include a message "Missing or empty required fields!"

    Scenario: Attempt to create user with invalid email format
      Given the client sends a POST request to "/user" with an invalid email address
      Then the API should return a 422 Unprocessable Entity status code
      And the response should indicate that the email address is invalid

    Scenario: Attempt to create user with inadequate password
      Given the client sends a POST request to "/user" with a password lacking required elements
      Then the API should return a 422 Unprocessable Entity status code
      And the response should detail the password validation errors

    Scenario: Attempt to create user with excessively short first name
      Given the client sends a POST request to "/user" with a first name "J"
      Then the API should return a 422 Unprocessable Entity status code
      And the response should indicate that the first name is too short

    Scenario: Attempt to create user with excessively short last name
      Given the client sends a POST request to "/user" with a last name "D"
      Then the API should return a 422 Unprocessable Entity status code
      And the response should indicate that the last name is too short

    Scenario: Attempt to create user with already used email
      Given the client sends a POST request to "/user" with an email address that already exists in the database
      Then the API should return a 422 Unprocessable Entity status code
      And the response should indicate that the email address is already in use

    Scenario: Successfully create a new user
      Given the client sends a POST request to "/user" with valid data
      Then the API should return a 201 Created status code
      And the response should include the user's ID
      And the response should match the expected creation message

    Scenario: Validate response time for user creation
      When the client sends a POST request to "/user" with valid data
      Then the response time should be less than 2000 milliseconds

    Scenario: Validate response content type
      When the client sends a POST request to "/user"
      Then the response should have a Content-Type header with value "application/json"