package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ValidateJsonFields { // New class for validating individual JSON fields

    @Test
    public void validateIndividualJsonFields() {
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

        // Validate individual fields in the JSON response using the body method
        System.out.println("\n--- Validating Individual JSON Fields ---");

        // Validate top-level elements
        response.then().assertThat().statusCode(200); // Ensure success before validating body
        response.then().assertThat().body("bookingid", notNullValue()); // bookingid should be present
        System.out.println("Validated 'bookingid' is not null.");

        // Validate fields within the 'booking' object
        response.then().assertThat().body("booking.firstname", equalTo("Jim"));
        System.out.println("Validated 'booking.firstname' is 'Jim'.");

        response.then().assertThat().body("booking.lastname", equalTo("Brown"));
        System.out.println("Validated 'booking.lastname' is 'Brown'.");

        response.then().assertThat().body("booking.totalprice", equalTo(111));
        System.out.println("Validated 'booking.totalprice' is '111'.");

        response.then().assertThat().body("booking.depositpaid", equalTo(true));
        System.out.println("Validated 'booking.depositpaid' is 'true'.");

        // Validate fields within the nested 'bookingdates' object
        response.then().assertThat().body("booking.bookingdates.checkin", equalTo("2018-01-01"));
        System.out.println("Validated 'booking.bookingdates.checkin' is '2018-01-01'.");

        response.then().assertThat().body("booking.bookingdates.checkout", equalTo("2019-01-01"));
        System.out.println("Validated 'booking.bookingdates.checkout' is '2019-01-01'.");

        response.then().assertThat().body("booking.additionalneeds", equalTo("Breakfast"));
        System.out.println("Validated 'booking.additionalneeds' is 'Breakfast'.");

        System.out.println("\nField-by-field JSON validation successful!");
    }
}
