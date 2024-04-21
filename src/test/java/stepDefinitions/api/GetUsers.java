package stepDefinitions.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class GetUsers {


    @BeforeClass
    public void setupClass(){
        RestAssured.baseURI = "http://qa-duobank.us-east-2.elasticbeanstalk.com/api";
    }

    @Test
    public void getUsersWithKey(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200);

    }
    @Test
    public void getUsersNoKey(){

        given().
                header("accept", "application/json").
                queryParam("api_key", " ").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(401);

    }

    @Test
    public void getUsersJsonFormat(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).
                header("content-type", "application/json");

    }
    @Test
    public void getUsersAllFields(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).
                body("id", not(empty())).
                body("email", everyItem(notNullValue())).
                body("password", everyItem(notNullValue())).
                body("first_name", everyItem(notNullValue())).
                body("last_name", everyItem(notNullValue())).
                body("phone", everyItem(notNullValue())).
                body("image", everyItem(notNullValue())).
                body("type", everyItem(notNullValue())).
                body("created_at", everyItem(notNullValue())).
                body("modified_at", everyItem(notNullValue())).
                body("zone_id", everyItem(notNullValue())).
                body("church_id", everyItem(notNullValue())).
                body("country_id", everyItem(notNullValue())).
                body("active", everyItem(notNullValue()));

    }

    @Test
    public void getUsersEmptyDB(){
//HOW TO IMPLEMENT?
        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200);

    }

    @Test
    public void getUsersPostRequest(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/users").
                then().
                log().all().
                statusCode(405);

    }

    @Test
    public void getUsersSensitiveData(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).
//                body("password", is(empty()));// "message": "Invalid request method"
                body(not(containsString("password")));
    }

    @Test
    public void getUsersReturnsArray(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).
                body("", isA(ArrayList.class));

    }
    @Test
    public void getUsersPagination(){

        int limit = 10;

        Integer response = given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                queryParam("limit", limit).
        when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).extract().path("size()");

        Assert.assertEquals(response, limit);

    }

    @Test
    public void getUsersXTotalCount(){

       given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).
                header("X-Total-Count", not(emptyString())).
                header("X-Total-Count", not(emptyOrNullString()));

    }

    @Test
    public void getUsersResponseTime(){

            given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).
//                extract().response().time();
                time(Matchers.lessThan(2000L));

    }



}
