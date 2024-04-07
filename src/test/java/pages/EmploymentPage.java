package pages;

import lombok.Data;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    @FindBy(name="purpose_loan")
    private WebElement loanPurpose;

    @FindBy(name="est_purchase_price")
    private WebElement estPurchasePrice;

    @FindBy(id="downpayment")
    private WebElement downpayment;

    @FindBy(id="downpaymentpercentage")
    private WebElement downpaymentPercentage;

    @FindBy(id="additionalfunds")
    private WebElement additionalFunds;

    @FindBy(xpath="//a[text()='Next']")
    private WebElement nextButton;



//    public void mortgageApplication() throws InterruptedException {
//
//        if(getCheckboxYes().isSelected()){
//            getRealtorNo().click();
//        }else{
//            realtorInfo.sendKeys("John Doe, 756-980-7654");
//        }
//        estPurchasePrice.sendKeys("300000");
//        Thread.sleep(1000);
////        try {
////            Alert alert = Driver.getDriver().switchTo().alert();
////            alert.accept();
////            Driver.getDriver().findElement(By.linkText("Ok")).click();
////        }catch (UnhandledAlertException e){
////            System.out.println("No alert was present");
////
////        }
//        downpayment.sendKeys("30000");
//        nextButton.click();
//
//    }

}
