package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for verifying a basic authentication API endpoint.
 */
public class BasicAuthApiTest {

    // Define the base URI for the API
    private static final String BASE_URI = "https://postman-echo.com";
    // Define the endpoint for basic authentication
    private static final String BASIC_AUTH_ENDPOINT = "/basic-auth";
    // Define the username for basic authentication
    private static final String USERNAME = "postman";
    // Define the password for basic authentication
    private static final String PASSWORD = "password";

    /**
     * Test method to verify the basic authentication API.
     * It performs a GET request with basic authentication,
     * verifies the status code, and checks the response body.
     */
    @Test
    public void testBasicAuthApi() {
        // Set the base URI for all requests in this test
        RestAssured.baseURI = BASE_URI;

        // IMPORTANT: Relax HTTPS validation to bypass SSLHandshakeException in test environments.
        // This is generally NOT recommended for production code due to security implications.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Starting test: testBasicAuthApi");
        System.out.println("Target URL: " + BASE_URI + BASIC_AUTH_ENDPOINT);
        System.out.println("Using Basic Auth with Username: " + USERNAME);

        // Perform the GET request with basic authentication
        // given(): Starts the request specification
        // auth().basic(USERNAME, PASSWORD): Specifies basic authentication credentials
        // when().get(BASIC_AUTH_ENDPOINT): Specifies the HTTP method and endpoint
        Response response = given()
                                .auth().basic(USERNAME, PASSWORD)
                            .when()
                                .get(BASIC_AUTH_ENDPOINT);

        System.out.println("Response received. Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Verify the status code is 200 (OK)
        // then(): Starts the response validation
        // statusCode(200): Asserts that the HTTP status code is 200
        response.then().statusCode(200);
        System.out.println("Status code 200 verified successfully.");

        // Verify the response body contains "authenticated": true
        // body("authenticated", equalTo(true)): Asserts that the 'authenticated' field in the JSON response
        // is equal to boolean true.
        response.then().body("authenticated", equalTo(true));
        System.out.println("Response body 'authenticated: true' verified successfully.");

        System.out.println("Test testBasicAuthApi completed successfully.");
    }
}
