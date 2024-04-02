package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pages.LoginPage;
import pages.MortgagePage;
import utilities.ConfigReader;
import utilities.Driver;

public class PreapprovalStepDefs {

    @Given("User is logged in into the bank mortgage application")
    public void userIsLoggedInIntoTheBankMortgageApplication() {
        Driver.getDriver().get(ConfigReader.getProperty("url"));
        new LoginPage().login();
    }
    @And("User navigates to the Mortgage page")
    public void userNavigatesToTheMortgagePage() {
        new MortgagePage().getMortgage().click();
    }

    @When("User encounters the Are you working with a realtor? field")
    public void userEncountersTheAreYouWorkingWithARealtorField() {
        Assert.assertEquals("ARE YOU WORKING WITH A REALTOR?", Driver.getDriver().findElement(
                By.xpath("//label[@for='firstName12']")).getText().toUpperCase());
    }

    @Then("User should see checkboxes with options Yes and No")
    public void userShouldSeeCheckboxesWithOptionsYesAndNo() {
        Assert.assertEquals("Yes", Driver.getDriver().findElement(By.xpath("//label[@for='realtor1']")).getText());
    }


    @When("User selects Yes for the realtor question")
    public void userSelectsYesForTheRealtorQuestion() {
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//input[@id='realtor1']")).isSelected());
    }


    @Then("the Realtor Information field should be enabled, allowing user to enter the realtor's name and contact details")
    public void theRealtorInformationFieldShouldBeEnabledAllowingUserToEnterTheRealtorSNameAndContactDetails() {
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//input[@name='realtor_info']")).isEnabled());
    }


    @When("User tries to proceed to the next page")
    public void userTriesToProceedToTheNextPage() {
        Driver.getDriver().findElement(By.linkText("PERSONAL INFORMATION")).click();

    }

    @Then("the Realtor Information field should be required before moving forward")
    public void theRealtorInformationFieldShouldBeRequiredBeforeMovingForward() {
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//label[@id='realtorinfo-error']")).isDisplayed());
    }
}
