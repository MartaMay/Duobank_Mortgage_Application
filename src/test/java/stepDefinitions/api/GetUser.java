package stepDefinitions.api;

import io.cucumber.java.lv.Tad;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class GetUser {

    @BeforeClass
    public void setupClass(){
        RestAssured.baseURI = "http://qa-duobank.us-east-2.elasticbeanstalk.com/api";
    }

    String userId = "11962";


    @Test
    public void getUserIdRequired(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                queryParam("id").
                when().
                log().all().
                get("/user").
                then().
                log().all().
                statusCode(400).
                body("message", equalTo("Bad request. User id is not provided."));

    }

    @Test
    public void getUserIdInvalid(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                queryParam("id", "!!!11962").
                when().
                log().all().
                get("/user").
                then().
                log().all().
                statusCode(400).
        body("message", equalTo("Bad request. User id is not provided."));
    }
    @Test
    public void getUserSensativeData(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                queryParam("id", userId).
                when().
                log().all().
                get("/user").
                then().
                log().all().
                statusCode(200).
                body(not(containsString("password")));
    }

    @Test
    public void getUserId(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                queryParam("id", userId).
                when().
                log().all().
                get("/user").
                then().
                log().all().
                statusCode(200).
                body("id", equalTo(userId)).
                body("first_name", equalTo("Mark")).
                body("last_name", equalTo("Johnson")).
                body("email", equalTo("mark.johnson@example.com")).
                body("created_at", equalTo("2024-03-29 16:37:15")).
                body("modified_at", equalTo("")).
                body("type", equalTo("2")).
                body("active", equalTo("1"));
    }

}
