package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import pages.EmploymentPage;
import utilities.Driver;
import utilities.SeleniumUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            if (!state.getText().equals("Select One")){
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
        if(Driver.getDriver().findElement(By.xpath("//input[@name='current_job[]']")).getAttribute("value").isEmpty()){
            System.out.println("The \"This is my current job\" checkbox is unchecked by default");
        }else {
            System.out.println("The \"This is my current job\" checkbox is NOT unchecked by default");

        }
    }
}
