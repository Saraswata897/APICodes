package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for verifying an API endpoint that returns a 301 status code.
 */
public class BasicAuthApiTest3 {

    // Define the base URI for the API
    private static final String BASE_URI = "https://the-internet.herokuapp.com";
    // Define the endpoint for the 301 status code
    private static final String STATUS_CODE_301_ENDPOINT = "/status_codes/301";

    /**
     * Test method to verify the status code 301 API.
     * It performs a GET request to the specified endpoint,
     * and verifies that the status code is 301.
     */
    @Test
    public void testStatusCode301Api() {
        // Set the base URI for all requests in this test
        RestAssured.baseURI = BASE_URI;

        // IMPORTANT: Relax HTTPS validation to bypass SSLHandshakeException in test environments.
        // This is generally NOT recommended for production code due to security implications.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Starting test: testStatusCode301Api");
        System.out.println("Target URL: " + BASE_URI + STATUS_CODE_301_ENDPOINT);

        // Perform the GET request
        // given(): Starts the request specification
        // redirects().follow(false): Instructs Rest Assured NOT to follow redirects automatically.
        //                            This is crucial for testing 3xx status codes directly when
        //                            the server doesn't provide a Location header.
        // when().get(STATUS_CODE_301_ENDPOINT): Specifies the HTTP method and endpoint
        Response response = given()
                                .redirects().follow(false) // Disable automatic redirection
                            .when()
                                .get(STATUS_CODE_301_ENDPOINT);

        System.out.println("Response received. Status Code: " + response.getStatusCode());
        System.out.println("Response Body: " + response.getBody().asString());

        // Verify the status code is 301 (Moved Permanently)
        // then(): Starts the response validation
        // statusCode(301): Asserts that the HTTP status code is 301
        response.then().statusCode(301);
        System.out.println("Status code 301 verified successfully.");

        System.out.println("Test testStatusCode301Api completed successfully.");
    }
}
