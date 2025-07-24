package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath; // Import JsonPath for extracting parts of JSON
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map; // Import Map for object properties

public class ValidateListSize { // New class for validating list/object size

    @Test
    public void validateBookingResponseListSize() {
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

        System.out.println("\n--- Validating List/Object Size in Response ---");

        // Validate status code first
        response.then().assertThat().statusCode(200);
        System.out.println("Validated status code is 200.");

        // --- Demonstrating "size" check for an object's properties ---
        // The POST /booking API returns a JSON object, not a direct list/array.
        // We can check the number of properties within a specific object.
        JsonPath jsonPath = response.jsonPath();
        Map<String, Object> bookingObject = jsonPath.getMap("booking");

        // Assert the number of properties in the 'booking' object
        // Expected properties: firstname, lastname, totalprice, depositpaid, bookingdates, additionalneeds (6 properties)
        Assert.assertEquals(bookingObject.size(), 6, "Expected 'booking' object to have 6 properties.");
        System.out.println("Validated 'booking' object has 6 properties.");

        // --- Example of how to use hasSize() if there was an actual array ---
        // If, for example, 'additionalneeds' was an array like ["Breakfast", "Wifi"],
        // you would validate its size like this:
        // response.then().assertThat().body("booking.additionalneeds", hasSize(2));
        // System.out.println("Validated 'additionalneeds' array has size 2.");
        // (This line is commented out as 'additionalneeds' is a string in the current schema)

        System.out.println("\nList/Object size validation successful!");
    }
}
