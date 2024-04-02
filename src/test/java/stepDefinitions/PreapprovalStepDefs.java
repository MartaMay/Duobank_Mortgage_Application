package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.MortgagePage;
import utilities.Driver;

public class PreapprovalStepDefs {
    @And("User navigates to the Mortgage page")
    public void userNavigatesToTheMortgagePage() {
        new MortgagePage().clickMortgage();
    }

    @When("User encounters the Are you working with a realtor? field")
    public void userEncountersTheAreYouWorkingWithARealtorField() {
        Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//label[text()='Are you working with a realtor?']")).getText(), "Are you working with a realtor?");
    }

    @Then("User should see checkboxes with options Yes and No")
    public void userShouldSeeCheckboxesWithOptionsYesAndNo() {
        Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//label[@for='realtor1']")).getText(), "Yes");
    }


    @When("User selects Yes for the realtor question")
    public void userSelectsYesForTheRealtorQuestion() {
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//label[@for='realtor1']")).isSelected());
    }


    @Then("the Realtor Information field should be enabled, allowing user to enter the realtor's name and contact details")
    public void theRealtorInformationFieldShouldBeEnabledAllowingUserToEnterTheRealtorSNameAndContactDetails() {
        Assert.assertEquals(Driver.getDriver().findElement(By.xpath("//input[@id='realtorinfo']")).getAttribute("required"), "required");
    }
}
