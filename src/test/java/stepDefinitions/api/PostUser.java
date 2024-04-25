package stepDefinitions.api;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigReader;
import utilities.DBUtils;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class PostUser {

    @BeforeClass
    public void setupClass() {
        RestAssured.baseURI = "http://qa-duobank.us-east-2.elasticbeanstalk.com/api";
    }


//    Faker faker = new Faker();
//
//    String username = faker.name().username();
//    String firstName = faker.name().firstName();
//    String lastName = faker.name().lastName();
//    String email = faker.internet().emailAddress();
//    String password = "Password321!!!";


    String username = "jenny.sage";
    String firstName = "Jenny";
    String lastName = "Sage";
    String email = "jenny.sage@outlook.com";
    String password = "Password321!!!";


    @Test
    public void postUserMissingFields() {

        given().
                body(String.format("""
                        {
                         "username": "%s",
                         "first_name": "%s",
                         "last_name": "%s",
                         "email": "%s",
                         "password": "%s"
                         
                          }""", username, firstName, "", email, password)).
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/user").
                then().
                log().all().
                statusCode(422).
                body("message", equalTo("Missing or Invalid Required Fields!"));


    }

    @Test
    public void postUserInvalidPassword() {

        given().
                body(String.format("""
                        {
                         "username": "%s",
                         "first_name": "%s",
                         "last_name": "%s",
                         "email": "%s",
                         "password": "%s"
                         
                          }""", username, firstName, lastName, email, "helloyou")).
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/user").
                then().
                log().all().
                statusCode(422).
                body("message",
                        equalTo("Password must be at least 8 characters long and contain at least one uppercase character, one lowercase character, one number, and one special symbol (!@#$%^&*()-_=+{};:,<.>)!"));
    }

    @Test
    public void postUserInvalidEmail() {

        given().
                body(String.format("""
                        {
                         "username": "%s",
                         "first_name": "%s",
                         "last_name": "%s",
                         "email": "%s",
                         "password": "%s"
                         
                          }""", username, firstName, lastName, "@something.com123", password)).
                header("accept", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/user").
                then().
                log().all().
                statusCode(422).
                body("message",
                        equalTo("Invalid Email Address!"));
    }

    @Test
    public void postUserInvalidFirstName() {

        given().
                body(String.format("""
                        {
                         "username": "%s",
                         "first_name": "%s",
                         "last_name": "%s",
                         "email": "%s",
                         "password": "%s"
                         
                          }""", username, "J", lastName, email, password)).
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/user").
                then().
                log().all().
                statusCode(422).
                body("message", equalTo("First name must be at least 2 characters long!"));


    }
    @Test
    public void postUserShortLastName() {

        given().
                body(String.format("""
                        {
                         "username": "%s",
                         "first_name": "%s",
                         "last_name": "%s",
                         "email": "%s",
                         "password": "%s"
                         
                          }""", username, firstName, "D", email, password)).
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/user").
                then().
                log().all().
                statusCode(422).
                body("message", equalTo("Last name must be at least 2 characters long!"));

    }


    @Test
    public void postUserValidFields() {

        given().
                body(String.format("""
                        {
                         "username": "%s",
                         "first_name": "%s",
                         "last_name": "%s",
                         "email": "%s",
                         "password": "%s"
                         
                          }""", username, firstName, lastName, email, password)).
                header("accept", "application/json").
                header("Content-Type", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/user").
                then().
                log().all().
                statusCode(201).
                body("status", equalTo(1)).
                body("http_code", equalTo(201)).
                body("message", equalTo("The user has been created.")).
                body("user_id", is(notNullValue()));


    }
    @Test
    public void postUserSameEmail() {

//        DBUtils.createConnection();
//        List<Map<String, Object>> result = DBUtils.getQueryResultListOfMaps
//                ("select email from tbl_user where id='11962'");
//        String existingEmail = String.valueOf(result.get(0).get("id"));
//
//        DBUtils.close();

        given().
                body(String.format("""
                        {
                         "username": "%s",
                         "first_name": "%s",
                         "last_name": "%s",
                         "email": "%s",
                         "password": "%s"
                         
                          }""", username, firstName, lastName, ConfigReader.getProperty("email"), password)).
                header("accept", "application/json").
                header("Content-Type", "application/json").
                queryParam("api_key", "c8a912d7d1c5a5a99c508f865b5eaae14a5b484f5bfe2d8f48c40e46289b47f3").
                when().
                log().all().
                post("/user").
                then().
                log().all().
                statusCode(422).
                body("message", equalTo("This e-mail is already in use!"));

    }}
