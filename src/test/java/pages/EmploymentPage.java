package pages;

import com.github.javafaker.Faker;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utilities.Driver;

import java.util.List;

@Data
public class EmploymentPage {
    public EmploymentPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id="employername1")
    private WebElement employername1;

    @FindBy(id="position1")
    private WebElement position1;

    @FindBy(id="city")
    private WebElement city;

    @FindBy(name="state[]")
    private WebElement state;

    @FindBy(id="start_date1")
    private WebElement start_date1;

    @FindBy(xpath="//div[@id='employer1']//div[@class='form-group']//label[text()]")
    private List<WebElement> inputFields;

    @FindBy(id="addemployer")
    private WebElement addemployer;

    @FindBy(id="grossmonthlyincome")
    private WebElement grossMonthlyIncome;

    @FindBy(id="monthlyovertime")
    private WebElement monthlyOvertime;

    @FindBy(id="monthlybonuses")
    private WebElement monthlyBonuses;

    @FindBy(id="monthlycommission")
    private WebElement monthlyCommission;

    @FindBy(id="monthlydividents")
    private WebElement monthlyDividents;

    @FindBy(className="borrowertotalmonthlyincome")
    private WebElement totalMonthlyIncome;

    @FindBy(xpath="//a[text()='Next']")
    private WebElement nextButton;



    public void currentEmploymentInfo(String name, String position, String city, String state, String date){
        getEmployername1().sendKeys(name);
        getPosition1().sendKeys(position);
        getCity().sendKeys(city);
        getState().click();
        Select select = new Select(getState());
        select.selectByVisibleText(state);
        getStart_date1().sendKeys(date);


    }

    public void enterPastEmploymentDetails(String employerName, String position, String city, String state, String startDate, String endDate, int index) {
        Driver.getDriver().findElement(By.id("employername" + index)).sendKeys(employerName);
        Driver.getDriver().findElement(By.id("position" + index)).sendKeys(position);
        Driver.getDriver().findElement(By.id("city" + index)).sendKeys(city);
        Select select = new Select(Driver.getDriver().findElement(By.id("state" + index)));
        select.selectByVisibleText(state);
        Driver.getDriver().findElement(By.id("start_date" + index)).sendKeys(startDate);
        Driver.getDriver().findElement(By.id("end_date" + index)).sendKeys(endDate);
    }
    public void monthlyIncome(String gross, String overtime, String bonuses, String commission, String dividents){
        grossMonthlyIncome.sendKeys(gross);
        getMonthlyOvertime().sendKeys(overtime);
        getMonthlyBonuses().sendKeys(bonuses);
        getMonthlyCommission().sendKeys(commission);
        getMonthlyDividents().sendKeys(dividents);
    }


}
