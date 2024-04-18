package stepDefinitions.db;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import pages.LoginPage;
import pages.MortgagePage;
import stepDefinitions.SharedData;
import utilities.DBUtils;

import java.sql.SQLException;
import java.util.*;

import static org.junit.Assert.assertTrue;

public class PreaprovalDetailsDBStepDefs {

    public PreaprovalDetailsDBStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    SharedData sharedData;


    @Then("the {string} column in {string} table should have an auto-incrementing primary key")
    public void the_column_should_have_an_auto_incrementing_primary_key(String columnName, String tableName) {

        List<Map<String, Object>> queryResultListOfMaps =
                DBUtils.getQueryResultListOfMaps(String.format("DESCRIBE %s", tableName));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(queryResultListOfMaps.get(0).get("Key")).isEqualTo("Key");
        softAssertions.assertThat(queryResultListOfMaps.get(0).get("Extra")).isEqualTo("PRI");

    }

    @Then("{string} table should store {string} as {int} and {string} as {int} in the database")
    public void theShouldBeStoredAsAndAsInTheDatabase(String tableName, String realtor_status, Integer int1, String loan_officer_status, Integer int2) {
        List<Map<String, Object>> queryResultListOfMaps =
                DBUtils.getQueryResultListOfMaps(String.format("DESCRIBE %s", tableName));

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(queryResultListOfMaps.get(1).get("Default")).isEqualTo("1");
        softAssertions.assertThat(queryResultListOfMaps.get(1).get("Type")).isEqualTo("int");
        softAssertions.assertThat(queryResultListOfMaps.get(3).get("Default")).isEqualTo("2");
        softAssertions.assertThat(queryResultListOfMaps.get(3).get("Type")).isEqualTo("int");
        softAssertions.assertAll();


    }

    @Then("The {string} should be stored as strings in the {string} table database")
    public void theShouldBeStoredAsStringsInTheDatabase(String columnName, String tableName) {

        List<Map<String, Object>> queryResultListOfMaps =
                DBUtils.getQueryResultListOfMaps(String.format("SELECT COLUMN_NAME," +
                                " DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '%s' " +
                                "AND COLUMN_NAME IN ('%s')"
                        , tableName, columnName));

        assertTrue(queryResultListOfMaps.get(0).get("DATA_TYPE").equals("varchar")
                || queryResultListOfMaps.get(0).get("DATA_TYPE").equals("text"));
    }


    @Then("The {string}, {string}, {string}, {string} and {string} should be stored as integer values in the {string} table")
    public void theAndShouldBeStoredAsIntegerValues(String est_purchase_price, String down_payment, String down_payment_percent, String total_loan_amount, String add_fund_available, String tableName) {
//        List<Map<String, Object>> queryResultListOfMaps =
//                DBUtils.getQueryResultListOfMaps(String.format("SELECT COLUMN_NAME," +
//                                " DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '%s' " +
//                                "AND COLUMN_NAME IN ('%s','%s','%s','%s','%s')"
//                        , tableName, est_purchase_price,down_payment,down_payment_percent,total_loan_amount,add_fund_available));
        //java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0


        List<Map<String, Object>> queryResultListOfMaps1 = DBUtils.getQueryResultListOfMaps("  SELECT COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS" +
                " WHERE TABLE_NAME = 'tbl_mortagage' AND COLUMN_NAME IN" +
                " ('est_purchase_price', 'down_payment','down_payment_percent', 'total_loan_amount', 'add_fund_available' );\n");

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(queryResultListOfMaps1.get(0).get("DATA_TYPE")).isEqualTo("int");
        softAssertions.assertThat(queryResultListOfMaps1.get(1).get("DATA_TYPE")).isEqualTo("int");
        softAssertions.assertThat(queryResultListOfMaps1.get(2).get("DATA_TYPE")).isEqualTo("int");
        softAssertions.assertThat(queryResultListOfMaps1.get(3).get("DATA_TYPE")).isEqualTo("int");
        softAssertions.assertThat(queryResultListOfMaps1.get(4).get("DATA_TYPE")).isEqualTo("int");
        softAssertions.assertAll();
    }

    @Then("each entry should have a unique {string}")
    public void eachEntryShouldHaveAUnique(String columnName) {

        List<List<Object>> result = DBUtils.getQueryResultAsListOfLists(String.format("SELECT %s FROM tbl_mortagage", columnName));

        List<String> actual = new ArrayList<>();
        for (List<Object> row : result) {
            if (!row.isEmpty()) {
                actual.add(row.get(0).toString());
            }
        }
        Set<String> setFromList = new HashSet<>(actual);
        Assert.assertTrue("List should not contain duplicates", actual.size() == setFromList.size());
    }

    @And("User logs in")
    public void userLogsIn() throws InterruptedException {

        new LoginPage().login(sharedData.getEmail(), sharedData.getPassword());
        new LoginPage().getSingInButton().click();
        System.out.println(sharedData.getEmail());
    }

    @Then("The data should be mapped correctly to the following columns in the {string} table")
    public void theDataShouldBeMappedCorrectlyToTheFollowingColumnsInTheTable(String tableName, List<String> expectedColumns) {

        StringBuilder columnNames = new StringBuilder();
        expectedColumns.forEach(s -> columnNames.append(s + ","));
        columnNames.deleteCharAt(columnNames.length() - 1);

        String query = String.format("SELECT %s from %s where b_email='%s'",
                columnNames,
                tableName,
                sharedData.getEmail()
        );

        List<Map<String, Object>> queryResult = DBUtils.getQueryResultListOfMaps(query);

        Map<String, Object> map = queryResult.get(0);

        List<String> actualColumns = new ArrayList<>(map.keySet());
        Assert.assertEquals(expectedColumns, actualColumns);
    }

    @Given("User with id = {int} exists in the {string} table db")
    public void userWithIdExistsInTheTableDb(int id, String table) {
        List<Map<String, Object>> listOfMaps = DBUtils.getQueryResultListOfMaps(String.format("select * from %s where id = %d",
                table, id));
        Assert.assertEquals(listOfMaps.get(0).get("id"), id);
    }

    @When("I enter non-alphabetical characters in the first, middle, and last name fields")
    public void i_enter_non_alphabetical_characters_in_the_first_middle_and_last_name_fields() throws SQLException {
       DBUtils.executeUpdate("UPDATE tbl_mortagage SET b_firstName = '1$3',\n" +
               " b_middleName=\"4%6\", b_lastName=\"7!9\" WHERE id = 1;");
    }
    @Then("the entry should be rejected")
    public void the_entry_should_be_rejected() {
        List<Map<String, Object>> listOfMaps = DBUtils.getQueryResultListOfMaps("select*from tbl_mortagage where id = 1");

       String name = String.valueOf(listOfMaps.get(0).get("b_firstName"));
        if (name.matches("^[A-Za-z]+$")) {
            System.out.println("The name contains only letters.");
        } else {
            System.out.println("The name contains invalid characters.");
        }

        Assert.assertTrue("The name contains invalid characters.", name.matches("^[A-Za-z]+$"));
    }
}

