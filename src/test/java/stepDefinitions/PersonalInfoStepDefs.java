package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.MortgagePage;
import pages.PersonalInfoPage;
import utilities.Driver;
import utilities.SeleniumUtils;

import java.util.List;
import java.util.Map;

public class PersonalInfoStepDefs {

    @Given("User fills out mortgage application")
    public void user_fills_out_mortgage_application() throws InterruptedException {
        new MortgagePage().getMortgage().click();
        new MortgagePage().mortgageApplication();
    }
    @Given("User is on the Personal Information page")
    public void navigates_to_the_personal_information_page() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertNotEquals("Personal Information",
                Driver.getDriver().findElement(By.xpath("//h6[text()='Personal Information']")).getText());
    }

    @When("User encounters the \"Are you applying with a co-borrower?\" question")
    public void userEncountersTheQuestion() {
        Driver.getDriver().findElement(By.xpath("//div[@class='form-group']/label[text()='Are you applying with a co-borrower?']"));

    }

    @Then("There should be checkboxes with options \"Yes\" and \"No\" for the user to select.")
    public void thereShouldBeCheckboxesWithOptionsAndForTheUserToSelect() {

     Assert.assertTrue(new PersonalInfoPage().getCoborrowerYes().isDisplayed());
     Assert.assertTrue(new PersonalInfoPage().getCoborrowerNo().isDisplayed());

    }

    @When("User selects \"Yes\" for the co-borrower question")
    public void userSelectsForTheCoBorrowerQuestion() {
        if (!new PersonalInfoPage().getCoborrowerYes().isSelected()) {
            SeleniumUtils.jsClick(new PersonalInfoPage().getCoborrowerYes());
        }
    }

    @Then("An additional section for entering co-borrower's information should appear on the page.")
    public void anAdditionalSectionForEnteringCoBorrowerSInformationShouldAppearOnThePage() {
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//div[@class='co-borrower']")).isDisplayed());
    }

    @When("User clicks on \"Next\" button")
    public void userClicksOnButton() {
        Driver.getDriver().findElement(By.xpath("//a[text()='Next']")).click();
    }

    @Then("The first name, last name, email, date of birth, SSN, marital status, and cell phone fields should be marked as required and not allow the user to proceed without completing them.")
    public void theFirstNameLastNameEmailDateOfBirthSSNMaritalStatusAndCellPhoneFieldsShouldBeMarkedAsRequiredAndNotAllowTheUserToProceedWithoutCompletingThem() {
    Assert.assertTrue(Driver.getDriver().findElement(By.id("b_firstName-error")).isDisplayed());
    Assert.assertTrue(Driver.getDriver().findElement(By.id("b_lastName-error")).isDisplayed());
    Assert.assertTrue(Driver.getDriver().findElement(By.id("b_email-error")).isDisplayed());
    Assert.assertTrue(Driver.getDriver().findElement(By.id("b_ssn-error")).isDisplayed());
    Assert.assertTrue(Driver.getDriver().findElement(By.id("b_marital-error")).isDisplayed());
    Assert.assertTrue(Driver.getDriver().findElement(By.id("b_cell-error")).isDisplayed());
    }


    @When("User clicks on Privacy Policy section")
    public void userClicksOnPrivacyPolicySection() {
        Driver.getDriver().findElement(By.xpath("//a[text()='Privacy Policy']")).click();
    }

    @And("User is redirected to Privacy terms window")
    public void userIsRedirectedToPrivacyTermsWindow() {
        Assert.assertEquals("Privacy Policy", Driver.getDriver().getTitle());
    }

    @When("User is navigating back")
    public void userIsNavigatingBackToPersonalInformationPage() throws InterruptedException {
        Driver.getDriver().navigate().back();
        Thread.sleep(1000);

        new MortgagePage().getNextButton().click();
    }

    @And("User has read and accepted the terms of the Privacy Policy")
    public void userShouldSeeACheckboxWithTheStatementIWeHaveReadAndAcceptedTheTermsOfThePrivacyPolicyThatMustBeChecked() {
       new PersonalInfoPage().getPrivacypolicy().click();

    }

    @When("User selects \"No\" for applying with a co-borrower")
    public void the_user_selects_for_applying_with_a_co_borrower() {
        if (!new PersonalInfoPage().getCoborrowerNo().isSelected()) {
            SeleniumUtils.jsClick(new PersonalInfoPage().getCoborrowerNo());
        }
    }
    @When("User enters personal information:")
    public void the_user_enters_personal_information(List<Map<String,String>> borrowerInfo) {
        new PersonalInfoPage().borrowersPersonalInfoEntry(borrowerInfo.get(0).get("FirstName"),
                borrowerInfo.get(0).get("LastName"), borrowerInfo.get(0).get("Email"), borrowerInfo.get(0).get("DOB"),
                borrowerInfo.get(0).get("SSN"), borrowerInfo.get(0).get("CellPhone"));
        new PersonalInfoPage().getNextButton().click();
    }

    @Then("the form should be submitted successfully and User redirected to the \"Current Monthly Housing Expenses\" page")
    public void the_form_should_be_submitted_successfully() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertEquals("Current Monthly Housing Expenses",
                Driver.getDriver().findElement(By.xpath("//h6[text()='Current Monthly Housing Expenses']")).getText());
    }

    @When("User enters co-borrower's information:")
    public void user_enters_co_borrower_s_information(List<Map<String,String>> c_borrowerInfo) {
        new PersonalInfoPage().c_borrowersPersonalInfoEntry(c_borrowerInfo.get(0).get("FirstName"),
                c_borrowerInfo.get(0).get("LastName"), c_borrowerInfo.get(0).get("Email"), c_borrowerInfo.get(0).get("DOB"),
                c_borrowerInfo.get(0).get("SSN"), c_borrowerInfo.get(0).get("CellPhone"));
        new PersonalInfoPage().getNextButton().click();

    }

}
