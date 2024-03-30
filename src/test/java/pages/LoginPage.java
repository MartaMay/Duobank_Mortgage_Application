package pages;

import lombok.Data;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utilities.ConfigReader;
import utilities.Driver;

import java.util.List;

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

    @FindBy (xpath = "//form//div")
    private List<WebElement> inputLinks;

    public void login(){
        email.sendKeys(ConfigReader.getProperty("email"), Keys.TAB, ConfigReader.getProperty("password"));
    }
    public void login(String email, String password){
        this.email.sendKeys(email, Keys.TAB, password);
    }
}
