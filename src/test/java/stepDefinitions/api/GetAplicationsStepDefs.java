package stepDefinitions.api;
import java.lang.reflect.Field;
import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.sl.In;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import pojo.Applications;
import pojo.LoanOfficer;
import pojo.User;
import stepDefinitions.SharedData;
import utilities.ConfigReader;
import utilities.DBUtils;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class GetAplicationsStepDefs {

    public GetAplicationsStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    SharedData sharedData;


    @Given("There is an existing user in the database with id {int}")
    public void thereIsAnExistingUserInTheDatabaseWithId(int id) {
        DBUtils.createConnection();
        String query = String.format("select * from tbl_user where id='%s'", id);

        List<Map<String, Object>> queryResult = DBUtils.getQueryResultListOfMaps(query);

        int actualId = (int) queryResult.get(0).get("id");
        Assert.assertEquals(id, actualId);
        DBUtils.close();

        sharedData.setOfficerID(id);
    }
   

    @And("user is a {string} with type set to {int}")
    public void userIsALoanOfficerWithTypeSetTo(String admin, int type) {

        DBUtils.createConnection();
        String query = "select type from tbl_user where id='"+sharedData.getOfficerID()+"'";

        List<Map<String, Object>> queryResult = DBUtils.getQueryResultListOfMaps(query);
        Assert.assertEquals(type, queryResult.get(0).get("type"));
        DBUtils.close();

    }


    @And("the request {string} queryParam is set to users id")
    public void theRequestQueryParamIsSetToUsersId(String parameter) {
        sharedData.getRequestSpecification().queryParam(parameter, sharedData.getOfficerID());
    }

    @Then("the response should contain users email")
    public void theResponseShouldContainUsersEmailAndPassword() {


        JsonPath jsonPath = sharedData.getResponse().then().extract().jsonPath();
        String email = jsonPath.getString("email");
        sharedData.setOfficerEmail(email);
        sharedData.setOfficerPassword("Hello123");

    }


    @Given("a temporary JWT token is generated")//WHY CANT SHARE TOKEN BETWEEN STEPS????
    public void the_loan_officer_has_a_valid_jwt_token_from_the_login_endpoint() {
        String accessToken = sharedData.getResponse().path("access_token");

        sharedData.setOfficerJWT(accessToken);
        System.out.println(sharedData.getOfficerJWT());

    }

    @And("the request {string} header is set with admin JWT token")
    public void theRequestHeaderIsSetWithAdminsJWTToken(String header) {
        sharedData.setOfficerJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2xvYW5cL2FwaSIsImF1ZCI6Imh0dHA6XC9cL2xvY2FsaG9zdFwvbG9hblwvYXBpIiwiaWF0IjoxNzE0NTk4OTU2LCJleHAiOjE3MTQ2MDI1NTYsImRhdGEiOnsidXNlcl9pZCI6IjEyMzkwIiwidHlwZSI6IjEifX0.t7x4d2gwOEhCnqPTHimNWgMSq2xFpCEl0-N0HB4WR-Y");
        sharedData.getRequestSpecification().header(header, sharedData.getOfficerJWT());
    }

    @And("the request body is set as following")
    public void theRequestBodyIsSetAsFollowing(Map<String, String> payload) {
        sharedData.getRequestSpecification().
                body(User.builder().email(payload.get("email")).password(payload.get("password")).build());

    }

    @Given("the user provides an invalid JWT token")
    public void theUserProvidesAnInvalidJWTToken() {
        sharedData.getRequestSpecification().header("Authorization", "Hello");

    }

    @Then("the response should include a list of applications")
    public void theResponseShouldIncludeAListOfApplications() {
        LoanOfficer loanOfficer = sharedData.getResponse().then().extract().as(LoanOfficer.class);
        System.out.println(loanOfficer.getMortagage_applications());
        Assert.assertFalse(loanOfficer.getMortagage_applications().isEmpty());
    }

    @And("each application should contain")
    public void eachApplicationShouldContainTheApplicationIDBorrowerSFullNameAndTotalLoanAmount(List <String> infoList) {
        JsonPath jsonPath = sharedData.getResponse().then().extract().jsonPath();
        List<Map<String, String>> mortgage = jsonPath.get("mortagage_applications");
        Map<String, String> first = mortgage.get(0);
        List<String> actualKeys = new ArrayList<>(first.keySet());
        Assert.assertEquals(infoList, actualKeys);
    }

    @Given("non-admin user is retrieved from the database")
    public void updateUsersTypeToNonAdminInTheDatabase(){
        int expectedType = 2;

        DBUtils.createConnection();
        String query = "select * from tbl_user where email='" + ConfigReader.getProperty("email") +"'";

        List<Map<String, Object>> queryResult = DBUtils.getQueryResultListOfMaps(query);
        Assert.assertEquals(expectedType, queryResult.get(0).get("type"));
        DBUtils.close();
    }

    @And("JWT token is generated for non-admin user")
    public void jwtTokenIsGeneratedForNonAdminUser() {

        String userToken = given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                body(String.format("""
                        {
                         "email": "%s",
                         "password": "%s"
                          }""", ConfigReader.getProperty("email"), ConfigReader.getProperty("password"))).
                when().
                log().all().
                post("/login").
                then().
                log().all().
                extract().path("access_token");
        System.out.println(userToken);
        sharedData.setUserJWT(userToken);


    }

    @And("the request {string} header is set with users JWT token")
    public void theRequestHeaderIsSetWithUsersJWTToken(String header) {
//        eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2xvYW5cL2FwaSIsImF1ZCI6Imh0dHA6XC9cL2xvY2FsaG9zdFwvbG9hblwvYXBpIiwiaWF0IjoxNzE0NTg4NTc0LCJleHAiOjE3MTQ1OTIxNzQsImRhdGEiOnsidXNlcl9pZCI6IjExOTYyIiwidHlwZSI6IjIifX0.ZjJIAz5hyjb5vHKPSwCYT9YaxcVVriOHeqcUAmXSAJE
        sharedData.getRequestSpecification().header(header, sharedData.getUserJWT());
    }

    @Then("the response log should display only users applications")
    public void theResponseLogShouldDisplayOnlyUsersApplications() {
        JsonPath jsonPath = sharedData.getResponse().then().extract().jsonPath();
        List<Object> userFirstNAme = jsonPath.getList("mortagage_applications.b_firstName");
        List<Object> userLastNAme = jsonPath.getList("mortagage_applications.b_lastName");

        userFirstNAme.forEach(e -> Assert.assertEquals(sharedData.getFirst(), e));
        userLastNAme.forEach(e -> Assert.assertEquals(sharedData.getLast(), e));


    }
}
