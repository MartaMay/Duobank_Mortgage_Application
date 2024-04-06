package pages;

import lombok.Data;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import utilities.Driver;

@Data
public class PersonalInfoPage {
    public PersonalInfoPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id="coborrower1")
    private WebElement coborrowerYes;

    @FindBy(id="coborrower2")
    private WebElement coborrowerNo;

    @FindBy(id="b_firstName")
    private WebElement b_FirstName;

    @FindBy(id="b_middleName")
    private WebElement b_MiddleName;

    @FindBy(id="b_lastName")
    private WebElement b_LastName;

    @FindBy(id="b_suffix")
    private WebElement b_Suffix;

    @FindBy(id="b_email")
    private WebElement b_Email;

    @FindBy(id="b_dob")
    private WebElement b_dob;

    @FindBy(id="b_ssn")
    private WebElement b_ssn;

    @FindBy(id="b_marital")
    private WebElement b_marital;

    @FindBy(id="b_cell")
    private WebElement b_cell;

    @FindBy(id="b_home")
    private WebElement b_home;

    @FindBy(xpath=" //label[@for='privacypolicy']")
    private WebElement  privacypolicy;

    @FindBy(id="c_firstName")
    private WebElement c_FirstName;

    @FindBy(id="c_middleName")
    private WebElement c_MiddleName;

    @FindBy(id="c_lastName")
    private WebElement c_LastName;

    @FindBy(id="c_suffix")
    private WebElement c_Suffix;

    @FindBy(id="c_email")
    private WebElement c_Email;

    @FindBy(id="c_dob")
    private WebElement c_dob;

    @FindBy(id="c_ssn")
    private WebElement c_ssn;

    @FindBy(id="c_marital")
    private WebElement c_marital;

    @FindBy(id="c_cell")
    private WebElement c_cell;

    @FindBy(id="c_home")
    private WebElement c_home;

    @FindBy(xpath = "//a[text()='Next']")
    private WebElement nextButton;



    public void borrowersPersonalInfoEntry(String first, String last, String email, String dob, String ssn, String cell) {

        b_FirstName.sendKeys(first);
        b_LastName.sendKeys(last);
        new Select(b_Suffix).selectByVisibleText("Sr.");
        b_Email.sendKeys(email);
        b_dob.sendKeys(dob);
        b_ssn.sendKeys(ssn);
        new Select(b_marital).selectByVisibleText("Married");
        b_cell.sendKeys(cell);
    }

    public void c_borrowersPersonalInfoEntry(String first, String last, String email, String dob, String ssn, String cell) {

        c_FirstName.sendKeys(first);
        c_LastName.sendKeys(last);
        new Select(c_Suffix).selectByVisibleText("Jr.");
        c_Email.sendKeys(email);
        c_dob.sendKeys(dob);
        c_ssn.sendKeys(ssn);
        new Select(c_marital).selectByVisibleText("Single");
        c_cell.sendKeys(cell);
    }

}
