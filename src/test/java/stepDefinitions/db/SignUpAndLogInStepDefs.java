package stepDefinitions.db;

import com.github.javafaker.Faker;
import io.cucumber.java.AfterStep;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import pages.SignUpPage;
import stepDefinitions.SharedData;
import utilities.ConfigReader;
import utilities.DBUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SignUpAndLogInStepDefs {

    public SignUpAndLogInStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    //String email;
    SharedData sharedData;


    @Then("{string} table should have the following columns:")
    public void table_should_have_the_following_columns_with_appropriate_types(String tableName, List<String> columnNames) {

        List<String> actualColumnNames = DBUtils.getColumnNames(String.format("SELECT * from %s", tableName));
        Assert.assertEquals(columnNames, actualColumnNames);

    }

    @When("User fills out fields with valid information")
    public void userFillsUpTheFieldsWithValidInfo() {
        SignUpPage signupPage = new SignUpPage();

        Faker faker = new Faker();
        sharedData.setFirst(faker.name().firstName());
        sharedData.setLast(faker.name().lastName());
        sharedData.setEmail(faker.internet().emailAddress());
        sharedData.setPassword("123Password");
        // sharedData.setPassword(faker.internet().password());
        signupPage.validSignUp(
                sharedData.getFirst(), sharedData.getLast(), sharedData.getEmail(), sharedData.getPassword()
        );
    }

    @And("User should be created in the database")
    public void userShouldBeCreatedInTheDatabase() {
        String query = "SELECT * FROM tbl_user where email='" + sharedData.getEmail() + "'";
        List<List<Object>> result = DBUtils.getQueryResultAsListOfLists(query);
        Assert.assertTrue(!result.isEmpty());
    }

    @Then("The data should be mapped correctly to the following columns in the {string} table:")
    public void theDataShouldBeMappedCorrectlyToTheFollowingColumnsInTheTable(String tableName, List<String> expectedColumns) {
        StringBuilder columnNames = new StringBuilder();
        expectedColumns.forEach(s -> columnNames.append(s + ","));
        columnNames.deleteCharAt(columnNames.length() - 1);

        String query = String.format("SELECT %s from %s where email='%s'",
                columnNames,
                tableName,
                sharedData.getEmail()
        );

        List<Map<String, Object>> queryResult = DBUtils.getQueryResultListOfMaps(query);

        Map<String, Object> map = queryResult.get(0);

        List<String> actualColumns = new ArrayList<>(map.keySet());
        Assert.assertEquals(expectedColumns, actualColumns);

    }

    @Given("there is already an account registered with the email {string}")
    public void thereIsAlreadyAnAccountRegisteredWithTheEmail(String email) {
        email = ConfigReader.getProperty("email");
        List<Map<String, Object>> result = DBUtils.getQueryResultListOfMaps(String.format("SELECT * FROM tbl_user where email='%s'", email));
        System.out.println(result);

        Assert.assertEquals(email, result.get(0).get("email"));

    }

    @When("user tries to register another account with the email {string}")
    public void anAttemptIsMadeToRegisterAnotherAccountWithTheEmail(String email) throws SQLException {
        email = ConfigReader.getProperty("email");
        Faker faker = new Faker();

        String query = String.format("INSERT INTO tbl_user " +
                        "(email, password, first_name, last_name, phone, image, type, created_at, modified_at, zone_id, church_id, country_id)" +
                        " VALUES ('%s', '%s', '%s', '%s', '%s', '%s','%d', '%s', '%s', '%d', '%d','%d')",

                email,
                DigestUtils.md5Hex(faker.internet().password()),
                faker.name().firstName(),
                faker.name().lastName(),
                faker.phoneNumber(),
                "assets/images/profile-pics/head_",
                0,
                LocalDateTime.now(),
                "0",
                0,
                0,
                0
        );
        DBUtils.executeUpdate(query);
    }

    @Then("the registration should fail and system should have only one user with registered email")
    public void theRegistrationShouldFail() {

        List<Map<String, Object>> uniqueUser = DBUtils.getQueryResultListOfMaps("select * from tbl_user where email='" + ConfigReader.getProperty("email") + "'");

        int count = DBUtils.getRowCount(uniqueUser);
        Assert.assertEquals("Duplicate user was created", 1, count);

    }

    @Then("The system should store the current timestamp in the {string} table, column {string}")
    public void theSystemShouldStoreTheCurrentTimestampInTheColumn(String tableName, String timeStamp) {

        List<Map<String, Object>> queryResultListOfMaps =
                DBUtils.getQueryResultListOfMaps(String.format("select * from %s where first_name='%s'",
                tableName, sharedData.getFirst()));

        String dbTimestamp = LocalDateTime.parse(queryResultListOfMaps.get(0).get(timeStamp).toString(),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        Assert.assertEquals(dbTimestamp, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }


    @Then("The password should be stored as an MD5 hash in DB")
    public void thePasswordShouldBeStoredAsAnMDHash() {
        List<Map<String, Object>> queryResultListOfMaps =
                DBUtils.getQueryResultListOfMaps(String.format("select * from tbl_user where first_name='%s'",
                        sharedData.getFirst()));

        Assert.assertEquals(queryResultListOfMaps.get(0).get("password"), DigestUtils.md5Hex(sharedData.getPassword()));

    }
}
