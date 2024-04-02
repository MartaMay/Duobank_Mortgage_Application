package pages;

import lombok.Data;
import org.openqa.selenium.WebElement;
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





}
