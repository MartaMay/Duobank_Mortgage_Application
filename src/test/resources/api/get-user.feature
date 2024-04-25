
  Feature: As a user of the mortgage application,
    I want to be able to retrieve a specific user's information by ID through an API endpoint.


    Background:
      Given There is an existing user in the database with id 11962

    Scenario: Get User Through a GET Request
      When client sends a GET request to /user with a valid API key and a valid user ID,
      Then client should receive a response indicating success (HTTP 200 OK).

    Scenario 2: Authentication Required
      When client sends a GET request to /user without an API key,
      Then client should receive a 401 Unauthorized status code and an error message "API key required".

    Scenario 3: JSON Response Format
      When client sends a GET request to /user,
      Then the response should be in JSON format.

    Scenario 4: Valid User ID Required
      When client sends a GET request to /user without a user ID,
      Then client should receive a 400 Bad Request status code and a message "Bad request. User ID is not provided."

    Scenario 5: Handle Invalid User ID
      When client sends a GET request to /user with an invalid user ID (e.g., non-existent or malformed),
      Then client should receive a 400 Bad Request response with a message "Bad request. User ID is not provided."

    Scenario 6: Valid User ID But User Not Found
      Given I have a valid API key and a user ID that does not correspond to any user in the database,
      When client sends a GET request to /user,
      Then client should receive a 404 Not Found status code and a message "User not found."

    Scenario 7: Valid User ID and User Found
      When client sends a GET request to /user,
      Then client should receive a 200 OK response, and the response should include JSON formatted user data with properties like:
        | id         | The user's ID                            |
        | first_name | The user's first name                    |
        | last_name  | The user's last name                     |
        | email      | The user's email address                 |
        | created_at | The timestamp when the user was created  |
        | modified_at| The timestamp when the user was modified |
        | type       | The user's type                          |
        | active     | Whether the user is active or not        |

    Scenario 8: Content-Type Header Check
      Given I have made a valid request to /user,
      When the server responds,
      Then the Content-Type header should be "application/json".

    Scenario 9: Response Time Less Than 2000ms
      Given I have a valid API key and a valid user ID,
      When client sends a GET request to /user,
      Then the response time should be less than 2000 milliseconds.

    Scenario: Verify that the response does not include sensitive information like passwords.
      When a client sends a GET request to /user,
      Then the response should not contain any passwords or sensitive data fields.