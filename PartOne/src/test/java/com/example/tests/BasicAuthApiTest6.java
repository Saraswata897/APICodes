package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for retrieving and printing JSON response from an API endpoint.
 */
public class BasicAuthApiTest6 {

    // Define the base URI for the API
    private static final String BASE_URI = "https://restful-booker.herokuapp.com";
    // Define the endpoint for getting all bookings
    private static final String GET_BOOKINGS_ENDPOINT = "/booking";

    /**
     * Test method to retrieve and print the JSON response for the /booking API.
     * It performs a GET request to the specified endpoint,
     * retrieves the JSON response body, and prints its values.
     */
    @Test
    public void testGetAllBookingsApi() {
        // Set the base URI for all requests in this test
        RestAssured.baseURI = BASE_URI;

        // IMPORTANT: Relax HTTPS validation to bypass SSLHandshakeException in test environments.
        // This is generally NOT recommended for production code due to security implications.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Starting test: testGetAllBookingsApi");
        System.out.println("Target URL: " + BASE_URI + GET_BOOKINGS_ENDPOINT);

        // Perform the GET request
        // given(): Starts the request specification (no specific setup needed here)
        // when().get(GET_BOOKINGS_ENDPOINT): Specifies the HTTP method and endpoint
        Response response = given()
                            .when()
                                .get(GET_BOOKINGS_ENDPOINT);

        System.out.println("Response received. Status Code: " + response.getStatusCode());

        // Verify the status code is 200 (OK)
        response.then().statusCode(200);
        System.out.println("Status code 200 verified successfully.");

        // Retrieve the response body and parse it into JSON (Rest Assured does this automatically)
        // Print the entire JSON response body for verification
        System.out.println("\n--- API Response JSON ---");
        response.prettyPrint(); // This method prints the JSON body in a nicely formatted way
        System.out.println("-------------------------\n");

        System.out.println("Test testGetAllBookingsApi completed successfully.");
    }
}
