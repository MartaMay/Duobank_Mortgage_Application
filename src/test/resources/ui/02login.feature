@REGRESSION
  Feature: As a user, I want to be able to sign in to my mortgage account, so that I
    can access my account information and perform necessary actions

    Background:
      Given User is on the login page of the bank mortgage application


    Scenario: Greeting Message on Login Page
      When User is on the login page
      Then User should be greeted with a welcome message

    Scenario: Verify login input fields
      Then User should see that sign in page includes two input fields, for email and password

    @M
    Scenario: Required Input Fields
       When User doesn't enter any data in the email and password fields
       And User clicks on Sing In Button
       Then User should see warning indicating that these fields are required and cannot be left blank

    Scenario: Email Address Format Check
      When User enters "user#example.com" in the email address field
      Then User should see an error message "Please enter a valid email address"

    Scenario: Masking Password Input
      When User inputs the password
      Then The password field masks entered characters, showing dots instead to maintain privacy

    Scenario: Sing in with valid credentials
      When User enters valid email and password and clicks Sign In
      Then User should be redirected to the homepage of application

