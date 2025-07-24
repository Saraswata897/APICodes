package com.example.api.tests;

import com.example.api.config.TestData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class AuthAndBookingTest {

    private String basicAuthHeader;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = TestData.BASE_URL; // Using base URL from config

        String credentials = TestData.ADMIN_USERNAME + ":" + TestData.ADMIN_PASSWORD;
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());

        // Get token and store it in TestData if needed for other tests
        TestData.AUTH_TOKEN = getAuthToken(TestData.ADMIN_USERNAME, TestData.ADMIN_PASSWORD);
        System.out.println("Obtained Auth Token: " + TestData.AUTH_TOKEN);
    }

    private String getAuthToken(String username, String password) {
        String requestBody = "{\n" +
                             "    \"username\" : \"" + username + "\",\n" +
                             "    \"password\" : \"" + password + "\"\n" +
                             "}";

        Response response = given()
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                            .when()
                                .post("/auth");

        response.then().assertThat()
                .statusCode(200)
                .body("token", notNullValue());

        return response.jsonPath().getString("token");
    }

    @Test
    public void testSomeBookingOperation() {
        // Now you can use TestData.AUTH_TOKEN and basicAuthHeader in your test
        System.out.println("Using Auth Token: " + TestData.AUTH_TOKEN);
        System.out.println("Using Basic Auth Header: " + basicAuthHeader);

        // Example: a simple GET request that might need auth
        given()
            .header("Authorization", "Bearer " + TestData.AUTH_TOKEN)
        .when()
            .get("/ping") // An example authenticated endpoint (health check)
        .then()
            .statusCode(201); // Ping endpoint on restful-booker returns 201
    }
}