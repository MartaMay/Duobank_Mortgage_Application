package stepDefinitions;

import com.github.javafaker.Faker;
import io.cucumber.java.cs.Ale;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.EmploymentPage;
import utilities.Driver;
import utilities.SeleniumUtils;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EmploymentStepDefs {

    @Given("User is on the Employment Information section")
    public void user_is_on_the_employment_information_section() {
        Assert.assertEquals("EMPLOYMENT AND INCOME",
                Driver.getDriver().findElement(By.xpath("//span[text()='Employment and Income']")).getText());
    }

    @Then("EMPLOYER NAME, POSITION, CITY, STATE, START DATE, and END DATE fields should be displayed")
    public void employer_name_position_city_state_start_date_and_end_date_fields_should_be_displayed() throws InterruptedException {
        Thread.sleep(1000);
        Assert.assertEquals(SeleniumUtils.getElementsText(new EmploymentPage().getInputFields()),
                Arrays.asList("EMPLOYER NAME", "POSITION", "CITY", "STATE", "START DATE"));
    }

    @And("EMPLOYER NAME field should be required")
    public void employer_name_field_should_be_required() {
        new EmploymentPage().getEmployername1().sendKeys("");
        new EmploymentPage().getNextButton().click();
        Assert.assertTrue(Driver.getDriver().findElement(By.id("employername1-error")).isDisplayed());
    }

    @And("STATE dropdown should list all 50 US states with two-letter abbreviations")
    public void state_dropdown_should_list_all_us_states_with_two_letter_abbreviations() {
        Select select = new Select(new EmploymentPage().getState());

        List<WebElement> statesWeb = select.getOptions();

        List<String> statesString = new ArrayList<>();
        for (WebElement state : statesWeb) {
            if (!state.getText().equals("Select One")) {
                statesString.add(state.getAttribute("value"));
            }
        }
        List<String> actual = Arrays.asList("AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "FL", "GA",
                "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD",
                "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ",
                "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC",
                "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
        Assert.assertEquals(actual, statesString);

    }

    @And("The This is my current job checkbox should be unchecked by default")
    public void the_this_is_my_current_job_checkbox_should_be_unchecked_by_default() throws InterruptedException {
        Thread.sleep(1000);
        if (Driver.getDriver().findElement(By.xpath("//input[@name='current_job[]']")).getAttribute("value").isEmpty()) {
            System.out.println("The \"This is my current job\" checkbox is unchecked by default");
        } else {
            System.out.println("The \"This is my current job\" checkbox is NOT unchecked by default");

        }
    }
    List<List<String>> currentEmploymentInfo;
    @Then("User fills out current Employment Information Section with following data:")
    public void userFillsOutCurrentEmploymentInformationSectionWithFollowingData(List<List<String>> currentEmploymentInfo) {
        this.currentEmploymentInfo=currentEmploymentInfo;
        new EmploymentPage().currentEmploymentInfo(currentEmploymentInfo.get(0).get(0),
                currentEmploymentInfo.get(0).get(1),currentEmploymentInfo.get(0).get(2),
                currentEmploymentInfo.get(0).get(3), currentEmploymentInfo.get(0).get(4));
    }

    @And("User clicks Add Another Employer")
    public void userClicksAddAnotherEmployer() {
        new EmploymentPage().getAddemployer().click();
    }

    @Then("User adds previous Employment Information:")
    public void userAddsPreviousEmploymentInformation(Map<String, List<String>> previousEmployment) throws InterruptedException {
        Thread.sleep(1000);
        int index = 2;
        for (int i = 0; i < previousEmployment.get("employerName").size(); i++) {
            new EmploymentPage().enterPastEmploymentDetails(previousEmployment.get("employerName").get(i),
                    previousEmployment.get("position").get(i), previousEmployment.get("city").get(i),
                    previousEmployment.get("state").get(i), previousEmployment.get("startDate").get(i),
                    previousEmployment.get("endDate").get(i), index);
            new EmploymentPage().getAddemployer().click();

            index++;
        }
    }
    @When("User clicks the Clear button")
    public void user_clicks_the_button() {
     new EmploymentPage().getClear1().click();
    }
    @Then("A warning popup should be displayed confirming the action")
    public void a_warning_popup_should_be_displayed_confirming_the_action() {

        Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//h2[text()='Clear Employer1  Data?']")).isDisplayed());
    }
    @Then("Clicking Yes! on the popup should clear only the information in that section")
    public void clicking_on_the_popup_should_clear_only_the_information_in_that_section() {
        Driver.getDriver().findElement(By.xpath("//button[text()='Yes!']")).click();
     Assert.assertTrue(Driver.getDriver().findElement(By.xpath("//div[@id='employer1']//input[@type='text']")).getText().isEmpty());

    }


    List<Map<String, String>> incomeInfo;

    @When("User enters income information")
    public void user_enters_following_income_information(List<Map<String, String>> incomeInfo) {
        this.incomeInfo = incomeInfo;
        System.out.println(incomeInfo);

        new EmploymentPage().monthlyIncome(
                incomeInfo.get(0).get("GROSS_MONTHLY_INCOME"),
                incomeInfo.get(0).get("MONTHLY_OVERTIME"),
                incomeInfo.get(0).get("MONTHLY_BONUSES"),
                incomeInfo.get(0).get("MONTHLY_COMMISSIONS"),
                incomeInfo.get(0).get("MONTHLY_DIVIDENDS_INTEREST"));

        System.out.println(incomeInfo.get(0).get("TOTAL_MONTHLY_INCOME") + incomeInfo.get(0).get("MONTHLY_DIVIDENDS_INTEREST"));
    }

    @Then("the \"Borrower Total Monthly Income\" should be calculated correctly")
    public void the_should_be_calculated_correctly() {
        String total = new EmploymentPage().getTotalMonthlyIncome().getText();
        String actual = incomeInfo.get(0).get("TOTAL_MONTHLY_INCOME");
        Assert.assertEquals(actual.replace(".00", ""), total.replace(" $", ""));
    }

      @Then("Section should contain three sets of INCOME SOURCE dropdowns")
    public void section_should_contain_three_sets_of_income_source_dropdowns_and_amount_fields() throws InterruptedException {

        List<WebElement> dropdowns = Driver.getDriver().findElements(By.cssSelector("div[class='col-md-4 belong']"));
        Assert.assertEquals(3, dropdowns.size());

    }
    @Then("INCOME SOURCE dropdown should include options like Alimony, Child Support and Social Security, Disability Income")
    public void income_source_dropdown_should_include_options_like_alimony_child_support_and_social_security_disability_income() {
       new EmploymentPage().getIncomesource1().click();
        Select select = new Select(new EmploymentPage().getIncomesource1());
        List <WebElement> incomeSources = select.getOptions();
        List<String> incomeString = new ArrayList<>();
        for (WebElement income : incomeSources) {
            if (!income.getText().equals("Select One")) {
                incomeString.add(income.getAttribute("value"));
            }
        }
        List<String> actual = Arrays.asList( "Alimony/Child Support", "Social Security/Disability Income",
                "Unemployment Benefits", "Interest and Dividends", "VA Compensation", "Royalty Payments", "Other Types of Income");
        Assert.assertEquals(actual, incomeString);

    }

}
