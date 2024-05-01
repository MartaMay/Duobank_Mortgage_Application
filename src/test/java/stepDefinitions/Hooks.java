package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utilities.ConfigReader;
import utilities.DBUtils;
import utilities.Driver;

import java.time.Duration;

public class Hooks {


    @Before("not @db_only")
    public void setupScenario(){
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Driver.getDriver().manage().window().maximize();
    }

    @Before ("@d or @db_only")
    public void db(){
        DBUtils.createConnection();
    }

    @Before ("@API")
    public void setupAPI(){
        RestAssured.baseURI = ConfigReader.getProperty("api.base.uri");
    }


    @After ("@d or @db_only")
    public void db2(){
        DBUtils.close();
    }

    @After("not @db_only")
    public void tearDownScenario(Scenario scenario){
        if(scenario.isFailed()){
            scenario.attach(((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png", "failed");
        }

       Driver.quitDriver();
    }


}
