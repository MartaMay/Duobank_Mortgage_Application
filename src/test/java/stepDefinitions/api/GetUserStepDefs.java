package stepDefinitions.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class GetUserStepDefs {

    @BeforeClass
    public void setupClass(){
        RestAssured.baseURI = "http://qa-duobank.us-east-2.elasticbeanstalk.com/api";
    }

    Integer userId = 11962;
    String first_name;
    String last_name;
    String email;
    String created_at;
    String modified_at;
    String type;
    String active;

    @Test
    public void getUser(){
        DBUtils.createConnection();
    List<Map<String, Object>> result = DBUtils.getQueryResultListOfMaps("select * from tbl_user where email='" + ConfigReader.getProperty("email") + "'");

    String s = String.valueOf(result.get(0).get("id"));
    userId = Integer.parseInt(s);
    first_name = String.valueOf(result.get(0).get("id"));
    last_name = String.valueOf(result.get(0).get("id"));
    email = String.valueOf(result.get(0).get("id"));
    created_at = String.valueOf(result.get(0).get("id"));
    modified_at = String.valueOf(result.get(0).get("id"));
    type = String.valueOf(result.get(0).get("id"));
    active = String.valueOf(result.get(0).get("id"));

        DBUtils.close();

    }
    @Test
    public void getUserGetValidUser(){



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
                body("id", equalTo(userId.toString()));
    }


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
    public void getUserSensitiveData(){

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
    public void getUserNotFound(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                queryParam("id", 1234567890).
                when().
                log().all().
                get("/user").
                then().
                log().all().
                statusCode(404).
                body("message", equalTo("User not found."));
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
                body("id", equalTo(userId.toString())).
                body("first_name", equalTo("Mark")).
                body("last_name", equalTo("Johnson")).
                body("email", equalTo("mark.johnson@example.com")).
                body("created_at", equalTo("2024-03-29 16:37:15")).
                body("modified_at", equalTo("")).
                body("type", equalTo("2")).
                body("active", equalTo("1"));
    }

}
