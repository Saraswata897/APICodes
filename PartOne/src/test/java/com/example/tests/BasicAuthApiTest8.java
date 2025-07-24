package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for retrieving API response with a single query parameter.
 */
public class BasicAuthApiTest8 {

    // Define the base URI for the API
    private static final String BASE_URI = "https://reqres.in";
    // Define the endpoint for users API
    private static final String USERS_ENDPOINT = "/api/users";

    /**
     * Test method to retrieve and print the JSON response for the /api/users API
     * with a 'page' query parameter.
     * It performs a GET request to the specified endpoint with the parameter,
     * retrieves the JSON response body, and prints its values.
     */
    @Test
    public void testGetUsersWithQueryParameter() {
        // Set the base URI for all requests in this test
        RestAssured.baseURI = BASE_URI;

        // IMPORTANT: Relax HTTPS validation to bypass SSLHandshakeException in test environments.
        // This is generally NOT recommended for production code due to security implications.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Starting test: testGetUsersWithQueryParameter");
        System.out.println("Target URL: " + BASE_URI + USERS_ENDPOINT);
        System.out.println("Adding query parameter: page=2");

        // Perform the GET request with a query parameter
        // given(): Starts the request specification
        // queryParam("page", 2): Adds a query parameter named 'page' with value '2'
        // when().get(USERS_ENDPOINT): Specifies the HTTP method and endpoint
        Response response = given()
                                .queryParam("page", 2) // Add the single query parameter
                            .when()
                                .get(USERS_ENDPOINT);

        System.out.println("Response received. Status Code: " + response.getStatusCode());

        // Verify the status code is 200 (OK)
        response.then().statusCode(200);
        System.out.println("Status code 200 verified successfully.");

        // Retrieve the response body and print it as JSON
        System.out.println("\n--- API Response JSON with Query Parameter ---");
        response.prettyPrint(); // This method prints the JSON body in a nicely formatted way
        System.out.println("----------------------------------------------\n");

        System.out.println("Test testGetUsersWithQueryParameter completed successfully.");
    }
}
