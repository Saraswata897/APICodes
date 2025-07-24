package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ValidateMultipleAssertions { // New class for performing multiple assertions

    @Test
    public void performMultipleAssertionsOnBookingResponse() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        String requestBody = "";
        try {
            // Read the request body from the classpath
            // The file should be placed in src/test/resources/booking_input.json
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("booking_input.json");
            if (inputStream == null) {
                throw new IOException("Resource 'booking_input.json' not found on classpath.");
            }
            requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body loaded from file:\n" + requestBody);
        } catch (IOException e) {
            System.err.println("Error reading booking_input.json: " + e.getMessage());
            Assert.fail("Failed to read request body from file: " + e.getMessage());
        }

        // Define the base URI for the API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Perform the POST request and get the response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Attach the request body
                .log().all() // Log all request details for debugging
                .post("/booking"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        System.out.println("\n--- Performing Multiple Assertions on Response ---");

        // Perform multiple assertions on the response using a single 'then()' block
        response.then().assertThat()
                .statusCode(200) // Validate status code
                .header("Content-Type", containsString("application/json")) // Validate header
                .header("Server", equalTo("Heroku")) // Validate another header
                .body("bookingid", notNullValue()) // Validate existence of bookingid
                .body("booking.firstname", equalTo("Jim")) // Validate specific field value
                .body("booking.lastname", equalTo("Brown")) // Validate another specific field value
                .body("booking.totalprice", equalTo(111)) // Validate numeric field value
                .body("booking.bookingdates.checkin", equalTo("2018-01-01")) // Validate nested field
                .body("booking", hasKey("additionalneeds")); // Validate existence of nested key

        System.out.println("\nAll multiple assertions passed successfully!");
    }
}
