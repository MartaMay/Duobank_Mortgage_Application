package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.LoginPage;
import pages.SignUpPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;

public class SignUpStepDefs {

    @Given("User is on the login page of the bank mortgage application")
    public void user_is_on_the_login_page_of_the_bank_mortgage_application() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
    }
    @And("User navigates to the sign up page")
    public void user_navigates_to_the_sign_up_page() {
       new SignUpPage().getSignUpPage().click();
    }


    @Then("User should see that sign up page includes input fields for the user's First Name, Last Name, Email Address, and Password")
    public void user_should_see_that_sign_up_page_includes_input_fields_for_the_user_s_first_name_last_name_email_address_and_password() {
        for (WebElement e : new SignUpPage().getInputFields()) {
            Assert.assertTrue(e.isDisplayed());
        }
    }

    @When("User doesn't enter any data in the First Name, Last Name, Email Address, and Password fields")
    public void user_doesn_t_enter_any_data_in_the_first_name_last_name_email_address_and_password_fields() {
       new SignUpPage().validSignUp("","","","");
    }

    @When("User attempts to click the Sign Up button")
    public void user_attempts_to_click_the_sign_up_button() {
       new SignUpPage().getSignUpButton().click();
    }

    @Then("User should see validation errors for all required fields")
    public void user_should_see_validation_errors_for_all_required_fields() {
        System.out.println("find assert");
    }

    @When("User fills all required fields for First Name, Last Name, Email and Password with the following data")
    public void user_fills_all_required_fields_with_the_following_data(List<String> input) {
        new SignUpPage().validSignUp(input.get(0), input.get(1), input.get(2), input.get(3));

    }

    @And("User clicks the Sign Up button")
    public void user_clicks_the_sign_up_button() {
       new SignUpPage().getSignUpButton().click();
    }

    @Then("User should see a Registration Successful message")
    public void user_should_see_a_message() {
        Assert.assertTrue(Driver.getDriver().findElement(By.linkText("Registration Successful")).isDisplayed());
    }
    @Then("User should be redirected to the Login page")
    public void user_should_be_redirected_to_the_login_page() {
        Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//h4[text()]")).getText(), "Welcome Back!");
    }

    @When("User attempts to sign up with existing email")
    public void user_attempts_to_sign_up_with_the_email() {
       new SignUpPage().validSignUp("Mark", "Johnson", "mark.johnson@example.com", "Password123");
    }
    @Then("User should see an error message This email already used")
    public void user_should_see_an_error_message() {
        Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//span[text()]")).getText(), "This email already used");
    }

    @When("User clicks on the Already have an account? Sign in link")
    public void user_clicks_on_the_link() {
        new SignUpPage().getSignInPage().click();
    }
    @Then("User should be redirected to the Sign In page")
    public void user_should_be_redirected_to_the_sign_in_page() {
        Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//h4[text()]")).getText(), "Welcome Back!");
    }
}
