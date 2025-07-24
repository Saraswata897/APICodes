package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ValidateJsonStructure { // New class for validating JSON structure

    @SuppressWarnings("deprecation")
	@Test
    public void validateBookingJsonStructure() {
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

        // Validate structural elements by setting a root path and checking for existence
        System.out.println("\n--- Validating JSON Structure ---");

        // First, validate the top-level bookingid exists and is not null
        response.then().assertThat().statusCode(200); // Ensure success before validating body
        response.then().assertThat().body("bookingid", notNullValue());
        System.out.println("Validated 'bookingid' exists and is not null.");

        // Set the root path to "booking" to validate fields within it
        response.then().root("booking").body(
            "firstname", notNullValue(),
            "lastname", notNullValue(),
            "totalprice", notNullValue(),
            "depositpaid", notNullValue(),
            "bookingdates", notNullValue(),
            "additionalneeds", notNullValue()
        );
        System.out.println("Validated existence of 'firstname', 'lastname', 'totalprice', 'depositpaid', 'bookingdates', 'additionalneeds' within 'booking' object.");

        // You can also set a nested root, e.g., for bookingdates
        response.then().root("booking.bookingdates").body(
            "checkin", notNullValue(),
            "checkout", notNullValue()
        );
        System.out.println("Validated existence of 'checkin' and 'checkout' within 'bookingdates' object.");


        System.out.println("\nJSON Structural validation successful!");
    }
}
