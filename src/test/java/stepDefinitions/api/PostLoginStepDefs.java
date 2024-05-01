package stepDefinitions.api;

import io.cucumber.core.internal.com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.junit.Assert;
import pojo.User;
import stepDefinitions.SharedData;
import utilities.ConfigReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class PostLoginStepDefs {

    public PostLoginStepDefs(SharedData sharedData) {
        this.sharedData = sharedData;
    }

    SharedData sharedData;

    @Given("the request is authenticated with a valid API key")
    public void the_request_is_authenticated_with_a_valid_api_key() {

        sharedData.getRequestSpecification().
                queryParam("api_key", ConfigReader.getProperty("api.key"));
    }

    @And("the request {string} header is set to {string}")
    public void theRequestHeaderIsSetTo(String key, String value) {
        sharedData.getRequestSpecification().header(key, value);
    }

    @Given("the request body is set with email and password")
    public void the_user_provides_a_valid_email_and_password() {
        sharedData.setEmail(ConfigReader.getProperty("email"));
        sharedData.setPassword(ConfigReader.getProperty("password"));

        sharedData.getRequestSpecification().body(User.builder().email(sharedData.getEmail()).password(sharedData.getPassword()).build());

    }

    @When("the user sends a {string} request to {string}")
    public void the_user_sends_a_request_to(String requestType, String endpoint) {


        switch (requestType) {
            case "GET" -> sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().get(endpoint));
            case "POST" ->
                    sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().post(endpoint));
            case "PUT" -> sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().put(endpoint));
            case "PATCH" ->
                    sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().patch(endpoint));
            case "DELETE" ->
                    sharedData.setResponse(sharedData.getRequestSpecification().when().log().all().delete(endpoint));
        }
    }

    @Then("the response log should be displayed")
    public void theResponseLogShouldBeDisplayed() {
        sharedData.getResponse().then().log().all();
    }

    @Then("the response status should be {int}")
    public void the_response_should_be(Integer code) {
//        sharedData.getResponse().then().statusCode(code);
        if (code == 200) {
            sharedData.getResponse().then().statusCode(code);
        } else {
        JsonPath jsonPath = sharedData.getResponse().then().extract().jsonPath();
        Integer actual = jsonPath.get("status");
        System.out.println(actual);
        Assert.assertEquals(code, actual);
    }
    }

    @Given("the request specification is reset")
    public void theRequestSpecificationIsReset() {
        sharedData.setRequestSpecification(given());

    }

    @Given("the user does not provide an API key")
    public void theUserDoesNotProvideAnAPIKey() {
        sharedData.getRequestSpecification().
                queryParam("api_key", "");
    }


    @And("the response body should have {string} field with value {string}")
    public void theResponseBodyShouldHaveFieldWithValue(String key, Object value) {
        sharedData.getResponse().then().body(key, equalTo(value));
    }

    @And("the response {string} header should be {string}")
    public void theResponseHeaderShouldBe(String key, String value) {
        sharedData.getResponse().then().header(key, value);

    }


    @And("the API should generate a temporary JWT token")
    public void theJWTTokenFromTheResponseIsStored() {

        String accessToken = sharedData.getResponse().path("access_token");
        System.out.println(accessToken);

        sharedData.setJWToken(accessToken);
    }

    @And("the user provides an email {string} and a valid password")
    public void theUserProvidesAnEmailAndAValidPassword(String email) {

        sharedData.getRequestSpecification().body(email).body(sharedData.getPassword());
    }

    @And("the request body is set as the following payload")
    public void theRequestBodyIsSetAsTheFollowingPayload(String payload) {

        sharedData.getRequestSpecification().body(String.format(payload));
    }


    @Then("The API response payload should be in the format:")
    public void theAPIResponsePayloadShouldBeInTheFormat(String payload){
        String actual = sharedData.getResponse().then().extract().asPrettyString();
        String accessToken = sharedData.getResponse().path("access_token");
        String expected = String.format("""
  {
    "success": true,
    "message": "You've successfully logged in!",
    "access_token": "%s",
    "token_type": "Bearer",
    "expires_in": 3600
}
  """, accessToken);

        System.out.println(actual);
        System.out.println(accessToken);

        Assert.assertEquals(expected, actual);





    }

    @And("the server encounters an unexpected error")
    public void theServerEncountersAnUnexpectedError() {
        RestAssured.baseURI = "http//duotify.us-east-2.elasticbeanstalk.com?";

    }

    @Then("the response time should be less than {int} ms")
    public void theResponseTimeShouldBeLessThanMs(int time) {
        sharedData.getRequestSpecification().then().time(Matchers.lessThan((long) time));
    }

    @And("the requested body is set as")
    public void theRequestedBodyIsSetAs(List<String> emailList) {
        sharedData.getRequestSpecification().body(emailList);
    }
}