package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class ValidateExistence { // New class for validating key existence

    @Test
    public void validateJsonKeyExistence() {
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

        System.out.println("\n--- Validating Key Existence in Response ---");

        // Validate status code first
        response.then().assertThat().statusCode(200);
        System.out.println("Validated status code is 200.");

        // Check if top-level keys exist
        response.then().assertThat()
                .body("$", hasKey("bookingid")) // Check if 'bookingid' key exists at root
                .body("$", hasKey("booking"));    // Check if 'booking' key exists at root
        System.out.println("Validated 'bookingid' and 'booking' keys exist at the root level.");

        // Check if nested keys exist within the 'booking' object
        response.then().assertThat()
                .body("booking", hasKey("firstname"))
                .body("booking", hasKey("lastname"))
                .body("booking", hasKey("totalprice"))
                .body("booking", hasKey("depositpaid"))
                .body("booking", hasKey("bookingdates"))
                .body("booking", hasKey("additionalneeds"));
        System.out.println("Validated 'firstname', 'lastname', 'totalprice', 'depositpaid', 'bookingdates', 'additionalneeds' keys exist within the 'booking' object.");

        // Check if nested keys exist within the 'bookingdates' object
        response.then().assertThat()
                .body("booking.bookingdates", hasKey("checkin"))
                .body("booking.bookingdates", hasKey("checkout"));
        System.out.println("Validated 'checkin' and 'checkout' keys exist within the 'bookingdates' object.");

        System.out.println("\nKey existence validation successful!");
    }
}
