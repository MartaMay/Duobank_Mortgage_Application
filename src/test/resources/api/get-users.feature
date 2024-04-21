
  Feature: As a user of the mortgage application,

    I want to be able to retrieve a list of all users of the application through an API endpoint

    So that I can perform analysis on the user data.

    Background:
      Given the server is running

    Scenario: Accessing API via HTTP GET Request
      When a client sends a GET request to /users,
      Then the server should respond with a status code indicating success (e.g., 200 OK).

    Scenario: Verify that the endpoint requires an API key and responds with a 401 error if it's missing.
      When a client sends a GET request to /users without an API key,
      Then the server should respond with a 401 Unauthorized status and an error message "API key required".

    Scenario: Verify that the response includes all user data from the database.
      When a client sends a GET request to /users,
      Then the server should respond with JSON data of all users, including all relevant fields.

    Scenario: Ensure the API only accepts GET requests.
      When a client sends a POST request to /users,
      Then the server should return a 405 HTTP status and an error message "Invalid request method".

    Scenario: Verify that the response does not include sensitive information like passwords.
      When a client sends a GET request to /users,
      Then the response should not contain any passwords or sensitive data fields.

    Scenario: Check that the API returns user data as an array of JSON objects.
      When a client sends a GET request to /users,
      Then the response should be an array of JSON objects.

    Scenario: Test that the API supports pagination through a 'limit' query parameter.
      When a client sends a GET request to /users with a 'limit' query parameter,
      Then the server should return only the specified number of user entries.

    Scenario: Ensure the server returns a header indicating the total count of users.
      When a client sends a GET request to /users,
      Then the server should include an X-Total-Count header with the total number of users.

    Scenario: Check that the response time is within the required limit.
      When a client sends a GET request to /users,
      Then the response time should be less than 2000ms.