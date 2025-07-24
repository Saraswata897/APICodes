package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ValidateLogicalOperations { // New class for performing logical operations

    @Test
    public void performLogicalAssertionsOnBookingResponse() {
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

        System.out.println("\n--- Performing Logical Assertions on Response ---");

        // Validate status code first
        response.then().assertThat().statusCode(200);
        System.out.println("Validated status code is 200.");

        // Example 1: Using 'and' (all conditions must be true)
        // Check if firstname is "Jim" AND lastname is "Brown"
        response.then().assertThat()
                .body("booking.firstname", equalTo("Jim"))
                .and() // 'and()' explicitly chains assertions, though often implicit
                .body("booking.lastname", equalTo("Brown"));
        System.out.println("Validated firstname is 'Jim' AND lastname is 'Brown'.");

        // Example 2: Using 'or' (at least one condition must be true)
        // Check if totalprice is 111 OR totalprice is 100 (one of them should be true)
        response.then().assertThat()
                .body("booking.totalprice", anyOf(equalTo(111), equalTo(100)));
        System.out.println("Validated totalprice is 111 OR 100.");

        // Example 3: Using 'not' (the condition must be false)
        // Check if depositpaid is NOT false (meaning it must be true)
        response.then().assertThat()
                .body("booking.depositpaid", not(equalTo(false)));
        System.out.println("Validated depositpaid is NOT false.");

        // Example 4: Combining multiple logical operations
        // Check if checkin is "2018-01-01" AND checkout is NOT "2020-01-01"
        response.then().assertThat()
                .body("booking.bookingdates.checkin", equalTo("2018-01-01"))
                .body("booking.bookingdates.checkout", not(equalTo("2020-01-01")));
        System.out.println("Validated checkin is '2018-01-01' AND checkout is NOT '2020-01-01'.");

        System.out.println("\nLogical operations validation successful!");
    }
}
