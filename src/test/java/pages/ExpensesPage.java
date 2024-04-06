package pages;

import lombok.Data;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

@Data
public class ExpensesPage {

    public ExpensesPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "expense1")
    private WebElement rentCheckbox;

    @FindBy(id = "expense2")
    private WebElement ownCheckbox;

    @FindBy (id = "monthlyrentalpayment")
    private WebElement monthlyRentalPayment;

    @FindBy (id = "firtmortagagetotalpayment")
    private WebElement monthlyMortagagePayment;

    @FindBy(xpath = "//a[text()='Next']")
    private WebElement nextButton;

}
