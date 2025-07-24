package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for verifying an API endpoint that returns a 200 status code.
 */
public class BasicAuthApiTest2 {

    // Define the base URI for the API
    private static final String BASE_URI = "https://the-internet.herokuapp.com";
    // Define the endpoint for the 200 status code
    private static final String STATUS_CODE_200_ENDPOINT = "/status_codes/200";

    /**
     * Test method to verify the status code 200 API.
     * It performs a GET request to the specified endpoint,
     * and verifies that the status code is 200.
     */
    @Test
    public void testStatusCode200Api() {
        // Set the base URI for all requests in this test
        RestAssured.baseURI = BASE_URI;

        // IMPORTANT: Relax HTTPS validation to bypass SSLHandshakeException in test environments.
        // This is generally NOT recommended for production code due to security implications.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Starting test: testStatusCode200Api");
        System.out.println("Target URL: " + BASE_URI + STATUS_CODE_200_ENDPOINT);

        // Perform the GET request
        // given(): Starts the request specification (no specific setup needed here)
        // when().get(STATUS_CODE_200_ENDPOINT): Specifies the HTTP method and endpoint
        Response response = given()
                            .when()
                                .get(STATUS_CODE_200_ENDPOINT);

        System.out.println("Response received. Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Verify the status code is 200 (OK)
        // then(): Starts the response validation
        // statusCode(200): Asserts that the HTTP status code is 200
        response.then().statusCode(200);
        System.out.println("Status code 200 verified successfully.");

        System.out.println("Test testStatusCode200Api completed successfully.");
    }
}
