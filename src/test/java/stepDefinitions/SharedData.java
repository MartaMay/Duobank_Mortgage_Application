package stepDefinitions;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Data;

import static io.restassured.RestAssured.given;

@Data
public class SharedData {

    private String first;

    private String last;

    private String email;

    private String password;
    private String userJWT;

    private Object fieldValue;

    private RequestSpecification requestSpecification = given();
    private Response response;

    private String JWToken;

    private String officerEmail;
    private String officerPassword;
    private String officerJWT;
    private Integer officerID;
}
