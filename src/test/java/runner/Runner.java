package runner;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions (

        tags = " @api_test",
        features = "src/test/resources",
        glue = "stepDefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-report/report.html"
        }
        ,publish = true
//      ,dryRun = true
)
@RunWith(Cucumber.class)
public class Runner {
}

//mvn test -D parallel=methods -D useUnlimitedThreads=true
//mvn test -D browser=chrome-headless
