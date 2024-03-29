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
public class SignUpPage {

    public SignUpPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//small[text()='Sign up']")
    private WebElement signUpPage;

    @FindBy(linkText = "Sign in")
    private WebElement signInPage;

    @FindBy(xpath = "//input[@class='form-control']")
    private List<WebElement> inputFields;

    @FindBy(xpath = "//input[@name='first_name']")
    private WebElement firstName;

    @FindBy(id = "inputlastname4")
    private WebElement lastName;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "exampleInputPassword1")
    private WebElement password;

    @FindBy(id = "register")
    private WebElement signUpButton;

    public void inputFirstName(String firstname){
        firstName.click();
    }

    public void inputLastName(){
        lastName.sendKeys(ConfigReader.getProperty("lastName"),
                        Keys.TAB);
    }
    public void inputEmail(){
        email.sendKeys(ConfigReader.getProperty("email"),
                Keys.TAB);
    }
    public void inputPassword(){
        password.sendKeys(ConfigReader.getProperty("password"),
                Keys.ENTER);
    }

    public void validSignUp(String first, String last, String email, String password){
        firstName.sendKeys(first);
        lastName.sendKeys(last);
        this.email.sendKeys(email);
        this.password.sendKeys(password);
    }

}
