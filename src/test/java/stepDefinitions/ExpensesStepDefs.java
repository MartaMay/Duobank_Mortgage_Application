package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import pages.ExpensesPage;
import utilities.Driver;
import utilities.SeleniumUtils;

public class ExpensesStepDefs {

    @When("User reads the \"A monthly housing expense refers\" field")
    public void user_views_the_do_you_currently_rent_or_own_field() {
        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//div[@class='form-group']//p[text()]")).isDisplayed());

    }
    @Then("There should be two checkboxes labeled \"Rent\" and \"Own\"")
    public void there_should_be_two_checkboxes_labeled_rent_and_own() {
        Assert.assertEquals("Rent", Driver.getDriver().findElement(By.xpath("//label[@for='expense1']")).getText());
        Assert.assertEquals("Own", Driver.getDriver().findElement(By.xpath("//label[@for='expense2']")).getText());
    }

    @When("User has selected \"Rent\" on the Expenses page")
    public void userHasSelectedRentOnTheExpensesPage()  {
        if (!new ExpensesPage().getRentCheckbox().isSelected()){
            new ExpensesPage().getRentCheckbox().click();
        }
    }

    @And("User enters negative value {string} into the \"Monthly Rental Payment\" field")
    public void userEntersVariousValuesIntoTheMonthlyRentalPaymentFieldIncludingNegativeValueCharactersEmptyValue(String negative){
         new ExpensesPage().getMonthlyRentalPayment().sendKeys(negative);
         new ExpensesPage().getNextButton().click();

    }
    @Then("User should see an error message Please enter a value greater than or equal to 0")
    public void userShouldSeeAnErrorMessagePleaseEnterAValueGreaterThanOrEqualToForANegativeNumber() throws InterruptedException {
        if (Driver.getDriver().findElement(By.xpath("//label[@for='monthlyrentalpayment']")).isDisplayed()){
            Thread.sleep(1000);
        Assert.assertTrue(Driver.getDriver().findElement(By.id("monthlyrentalpayment-error")).isDisplayed());
       }else {
            Assert.assertTrue(Driver.getDriver().findElement(By.id("firtmortagagetotalpayment-error")).isDisplayed());

        }
    }

    @Then("User enters non numeric characters {string} into the \"Monthly Rental Payment\" field")
    public void userEntersNonNumericCharactersIntoTheField(String nonNum) {
        new ExpensesPage().getMonthlyRentalPayment().sendKeys(nonNum);

    }

    @And("User should see an error message Please enter a valid number.\" for a non-numeric value")
    public void userShouldSeeAnErrorMessagePleaseEnterAValidNumberForANonNumericValue() {
        Assert.assertTrue(new ExpensesPage().getMonthlyRentalPayment().getText().isEmpty());
    }

    @When("User has selected \"Own\" on the Expenses page")
    public void userHasSelectedOwnOnTheExpensesPage() {

        SeleniumUtils.jsClick(new ExpensesPage().getOwnCheckbox());

    }

    @And("User enters {string} into the \"Monthly Rental Payment\" field")
    public void userEntersIntoTheMonthlyRentalPaymentField(String rent) {
        new ExpensesPage().getMonthlyRentalPayment().sendKeys(rent);

        }

    @And("User should be redirected to the Employment and Income Page")
    public void userShouldBeRedirectedToTheEmploymentAndIncomePage() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertEquals("EMPLOYMENT AND INCOME",
                Driver.getDriver().findElement(By.xpath("//span[text()='Employment and Income']")).getText());
    }

    @And("User enters negative value {string} into the \"Monthly Mortgage Payment\" field")
    public void userEntersNegativeValueIntoTheField(String mortgage) {
        new ExpensesPage().getMonthlyMortagagePayment().sendKeys(mortgage);
        new ExpensesPage().getNextButton().click();

    }

    @Then("User should see an error message Please enter a value greater than or equal to 0 for Monthly Mortgage Payment")
    public void userShouldSeeAnErrorMessagePleaseEnterAValueGreaterThanOrEqualToForANegativeNumberForMor() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertTrue(Driver.getDriver().findElement(By.id("firtmortagagetotalpayment-error")).isDisplayed());

        }

    @When("User fills out required fields for Current Monthly Housing Expenses")
    public void userFillsOutRequiredFieldsForCurrentMonthlyHousingExpenses() {
        if (!new ExpensesPage().getRentCheckbox().isSelected()){
            new ExpensesPage().getRentCheckbox().click();
        }
        new ExpensesPage().getMonthlyRentalPayment().sendKeys("1750");
        new ExpensesPage().getNextButton().click();


    }
}

