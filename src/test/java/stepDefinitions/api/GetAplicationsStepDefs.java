package stepDefinitions.api;

import com.github.javafaker.Faker;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.common.mapper.TypeRef;
import io.restassured.path.json.JsonPath;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import pojo.LoanOfficer;
import stepDefinitions.SharedData;
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
        String query = "select * from tbl_user where id='12390'";

        List<Map<String, Object>> queryResult = DBUtils.getQueryResultListOfMaps(query);

        int actualId = (int) queryResult.get(0).get("id");
        Assert.assertEquals(id, actualId);
        DBUtils.close();

        sharedData.setOfficerID(id);
    }
   

    @And("user is a loan officer with type set to {int}")
    public void userIsALoanOfficerWithTypeSetTo(int type) {

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
        sharedData.setOfficerEmail(jsonPath.getString("email"));
        sharedData.setOfficerPassword("Hello123");

    }


    @Given("a temporary JWT token is generated")//WHY CANT SHARE TOKEN BETWEEN STEPS????
    public void the_loan_officer_has_a_valid_jwt_token_from_the_login_endpoint() {
        String accessToken = sharedData.getResponse().path("access_token");

        sharedData.setOfficerJWT(accessToken);
        System.out.println(sharedData.getOfficerJWT());

    }

    @And("the request {string} header is set with users JWT token")
    public void theRequestHeaderIsSetWithUsersJWTToken(String header) {
        sharedData.setOfficerJWT("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOlwvXC9sb2NhbGhvc3RcL2xvYW5cL2FwaSIsImF1ZCI6Imh0dHA6XC9cL2xvY2FsaG9zdFwvbG9hblwvYXBpIiwiaWF0IjoxNzE0NTA5Njc3LCJleHAiOjE3MTQ1MTMyNzcsImRhdGEiOnsidXNlcl9pZCI6IjEyMzkwIiwidHlwZSI6IjEifX0.s-ivRDpbkwdYLoppzyZwrfPpneXqlvtOgCVvP2AWqbQ");
        sharedData.getRequestSpecification().header(header, sharedData.getOfficerJWT());
    }

    @And("the request body is set as following")
    public void theRequestBodyIsSetAsFollowing(Map<String, String> payload) {
        sharedData.getRequestSpecification().body(payload);

    }

    @Given("the user provides an invalid JWT token")
    public void theUserProvidesAnInvalidJWTToken() {
        sharedData.getRequestSpecification().header("Authorization", "Hello");

    }

    @Then("the response should include a list of applications")
    public void theResponseShouldIncludeAListOfApplications() {
        LoanOfficer loanOfficer = sharedData.getResponse().then().extract().as(LoanOfficer.class);
        System.out.println(loanOfficer.getMortagage_applications());
        System.out.println(loanOfficer);
    }

    @And("each application should contain")
    public void eachApplicationShouldContainTheApplicationIDBorrowerSFullNameAndTotalLoanAmount(List <String> infoList) {
        LoanOfficer loanOfficer = sharedData.getResponse().then().extract().as(LoanOfficer.class);
        System.out.println(loanOfficer.getMortagage_applications());
        System.out.println(loanOfficer);
    }
}
