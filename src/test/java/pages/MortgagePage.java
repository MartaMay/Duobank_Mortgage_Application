package pages;

import lombok.Data;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.Driver;

@Data
public class MortgagePage {
    public MortgagePage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(linkText="Mortgage Application")
    private WebElement mortgage;

    @FindBy(xpath="//input[@type='checkbox']//label[@for='realtor1']")
    private WebElement realtorYes;

    @FindBy(xpath="//input[@type='checkbox']")
    private WebElement checkboxYes;

    @FindBy(xpath="//label[@for='realtor2']")
    private WebElement realtorNo;

    @FindBy(xpath="/input[@id='realtor2'] ")
    private WebElement checkboxNo;

    @FindBy(id="realtorinfo")
    private WebElement realtorInfo;

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



    public void mortgageApplication() throws InterruptedException {

        if(getCheckboxYes().isSelected()){
            getRealtorNo().click();
        }else{
            realtorInfo.sendKeys("John Doe, 756-980-7654");
        }
        estPurchasePrice.sendKeys("300000");
        Thread.sleep(1000);
//        try {
//            Alert alert = Driver.getDriver().switchTo().alert();
//            alert.accept();
//            Driver.getDriver().findElement(By.linkText("Ok")).click();
//        }catch (UnhandledAlertException e){
//            System.out.println("No alert was present");
//
//        }
        downpayment.sendKeys("30000");
        nextButton.click();

    }

}
