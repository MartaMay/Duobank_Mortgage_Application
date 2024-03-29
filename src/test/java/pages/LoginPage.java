package pages;

import lombok.Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ConfigReader;
import utilities.Driver;

@Data
public class LoginPage {

    public LoginPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(id = "exampleInputEmail1")
    private WebElement email;

    @FindBy(id = "id=\"exampleInputPassword1\"")
    private WebElement password;

    @FindBy (xpath = "//button[@name='login']")
    private WebElement singInButton;

    public void login(){
        email.sendKeys(ConfigReader.getProperty("email"), Keys.TAB, ConfigReader.getProperty("password"));
    }
}
