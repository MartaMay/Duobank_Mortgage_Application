package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
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
