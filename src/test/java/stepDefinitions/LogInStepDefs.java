package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.locators.RelativeLocator;
import pages.LoginPage;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;

public class LogInStepDefs {

    @When("User is on the login page")
    public void user_is_on_the_login_page() {
        Assert.assertEquals("http://qa-duobank.us-east-2.elasticbeanstalk.com/index.php", Driver.getDriver().getCurrentUrl());
    }
    @Then("User should be greeted with a welcome message")
    public void user_should_be_greeted_with_a_welcome_message() {
       Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//h4[text()]")).getText(), "Welcome Back!");
    }

    @Then("User should see that sign in page includes two input fields, for email and password")
    public void userShouldSeeThatSignUpPageIncludesTwoInputFieldsForEmailAndPassword() {
        for (WebElement link : new LoginPage().getInputLinks()) {
            Assert.assertTrue(link.isDisplayed());
        }
    }

    @When("User doesn't enter any data in the email and password fields")
    public void userTriesToLoginWithoutEnteringAnyData() {
        new LoginPage().login("","");
    }

    @And("User clicks on Sign In Button")
    public void userClicksOnSingInButton() {
        new LoginPage().getSingInButton().click();
    }

    @Then("User should see warning indicating that these fields are required and cannot be left blank")
    public void userShouldSeeWarningsNextToTheEmailAndPasswordFieldsIndicatingThatTheseFieldsAreRequiredAndCannotBeLeftBlank() throws InterruptedException {
       String pageS =  Driver.getDriver().getPageSource();
        boolean isCommentedOut = pageS.contains("<!-- <input class='form-check-input'");

        Assert.assertTrue("The element is not present in the page source as commented out.", isCommentedOut);
    }


    @When("User enters {string} in the email address field")
    public void userEntersInTheEmailAddressField(String invaliEmail) {
        new LoginPage().getEmail().sendKeys(invaliEmail, Keys.ENTER);
    }

    @Then("User should see an error message \"Please enter a valid email address\"")
    public void userShouldSeeAnErrorMessage() throws InterruptedException {
//        Thread.sleep(2000);
//        Driver.getDriver().switchTo().frame(0);
        JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
        WebElement errorMessage = (WebElement) js.executeScript("return document.querySelector('span.error-message');");
        System.out.println(errorMessage);
        Assert.assertTrue(errorMessage.isDisplayed());

    }

    @When("User inputs the password")
    public void userInputsThePassword() {
        new LoginPage().getPassword().sendKeys(ConfigReader.getProperty("password"));
    }

    @Then("The password field masks entered characters, showing dots instead to maintain privacy")
    public void thePasswordFieldMasksEnteredCharactersShowingDotsInsteadToMaintainPrivacy() {
        Assert.assertEquals("password", Driver.getDriver().findElement(By.id("password")).getAttribute("type"));
    }

    @When("User enters valid email and password")
    public void userEnterstValidEmailAndPassword() {
        new LoginPage().login();
    }

    @Then("User should be redirected to the homepage of application")
    public void userShouldBeRedirectedToTheHomepageOfApplication() {
        Assert.assertEquals("Duobank Mortgage Application", Driver.getDriver().getTitle());

    }
}
