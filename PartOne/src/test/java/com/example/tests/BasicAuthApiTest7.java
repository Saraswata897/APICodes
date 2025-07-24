package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for retrieving and printing XML response from an API endpoint.
 */
public class BasicAuthApiTest7 {

    // Define the base URI for the API
    private static final String BASE_URI = "https://restful-booker.herokuapp.com";
    // Define the endpoint for getting all bookings
    private static final String GET_ALL_BOOKINGS_ENDPOINT = "/booking";
    // Define the endpoint for getting a specific booking by ID (ID will be dynamic)
    private static final String GET_BOOKING_BY_ID_BASE_ENDPOINT = "/booking/";

    /**
     * Test method to retrieve and print the XML response for a specific booking API.
     * It first retrieves a list of booking IDs, then uses one of those IDs to
     * perform a GET request with Accept and Content-Type headers for XML,
     * retrieves the XML response body, and prints its values.
     */
    @Test
    public void testGetBookingXmlResponseApi() {
        // Set the base URI for all requests in this test
        RestAssured.baseURI = BASE_URI;

        // IMPORTANT: Relax HTTPS validation to bypass SSLHandshakeException in test environments.
        // This is generally NOT recommended for production code due to security implications.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("Starting test: testGetBookingXmlResponseApi");

        // Step 1: Get a list of all bookings to find a valid ID
        System.out.println("Attempting to retrieve a valid booking ID from: " + BASE_URI + GET_ALL_BOOKINGS_ENDPOINT);
        Response allBookingsResponse = given()
                                       .when()
                                           .get(GET_ALL_BOOKINGS_ENDPOINT);

        allBookingsResponse.then().statusCode(200);
        System.out.println("Successfully retrieved all bookings. Status Code: " + allBookingsResponse.getStatusCode());

        // Extract the first booking ID from the response
        // The /booking endpoint returns a JSON array like [{"bookingid": 123}, {"bookingid": 456}]
        Integer bookingId = allBookingsResponse.jsonPath().getInt("[0].bookingid");

        if (bookingId == null) {
            System.out.println("No booking IDs found. Cannot proceed with XML retrieval test.");
            // Optionally, fail the test if no IDs are found
            // fail("No booking IDs found to test XML retrieval.");
            return; // Exit the test method if no ID is found
        }

        System.out.println("Found booking ID: " + bookingId);
        String specificBookingEndpoint = GET_BOOKING_BY_ID_BASE_ENDPOINT + bookingId;
        System.out.println("Target URL for XML retrieval: " + BASE_URI + specificBookingEndpoint);

        // Step 2: Perform the GET request for the specific booking with Accept and Content-Type headers for XML
        Response response = given()
                                .header("Accept", "application/xml")
                                .header("Content-Type", "application/xml")
                            .when()
                                .get(specificBookingEndpoint);

        System.out.println("Response received for XML request. Status Code: " + response.getStatusCode());

        // Verify the status code is 200 (OK)
        response.then().statusCode(200);
        System.out.println("Status code 200 verified successfully for XML response.");

        // Retrieve the response body and print it as XML
        System.out.println("\n--- API Response XML ---");
        response.prettyPrint(); // This method prints the XML body in a nicely formatted way
        System.out.println("-------------------------\n");

        System.out.println("Test testGetBookingXmlResponseApi completed successfully.");
    }
}
