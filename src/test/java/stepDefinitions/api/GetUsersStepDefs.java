package stepDefinitions.api;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class GetUsersStepDefs {


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
    public void getUsersArray(){

        given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).
                header("content-type", "application/json").
                body("", hasSize(greaterThan(0))).
                body("size()", greaterThan(0)).
                body("[0]", allOf(hasKey("id"), hasKey("email"), hasKey("password"), hasKey("first_name"), hasKey("last_name"),
                        hasKey("phone"), hasKey("image"), hasKey("type"), hasKey("created_at"), hasKey("modified_at"), hasKey("zone_id"),
                        hasKey("church_id"), hasKey("country_id"), hasKey("active")));

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
                body("", not(hasItem("password")));
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

        JsonPath jsonPath = given().
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                queryParam("limit", limit).
                when().
                log().all().
                get("/users").
                then().
                log().all().
                statusCode(200).extract().jsonPath();

        List<Map<String, Object>> jsonPathList = jsonPath.getList("");
        Assert.assertEquals(jsonPathList.size(), limit);

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
                header("X-Total-Count", notNullValue());

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
                time(Matchers.lessThan(2000L));

    }



}
