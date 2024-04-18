package stepDefinitions.db;

import io.cucumber.java.en.Then;
import org.junit.Assert;
import utilities.DBUtils;

import java.util.List;

public class PreaprovalBDStepDefs {
    @Then("the {string} table should have the following columns:")
    public void theTableShouldHaveTheFollowingColumns(String tableName, List<String> columnNames) {

        List<String> actualColumnNames = DBUtils.getColumnNames(String.format("SELECT * from %s", tableName));

        Assert.assertTrue(actualColumnNames.containsAll(columnNames));


    }
}
