package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath; // Import JsonPath for extracting values
import org.testng.annotations.Test;
import org.testng.Assert; // Keep TestNG Assert for assertEquals
import static org.hamcrest.MatcherAssert.assertThat; // Import Hamcrest's assertThat
import static org.hamcrest.Matchers.*; // Import other Hamcrest Matchers for assertions

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors; // For collecting map values into a list

public class ValidateListOfValues { // New class for validating a list of values

    @Test
    public void validateResponseListOfValues() {
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

        System.out.println("\n--- Validating List of Values in Response ---");

        // Validate status code first
        response.then().assertThat().statusCode(200);
        System.out.println("Validated status code is 200.");

        // Convert the response into a Json document (JsonPath object)
        JsonPath jsonPath = response.jsonPath();

        // Extract the 'booking' object as a Map
        Map<String, Object> bookingDetailsMap = jsonPath.getMap("booking");

        // Convert the values of the 'booking' map into a List for validation
        List<Object> bookingValues = bookingDetailsMap.values().stream().collect(Collectors.toList());

        System.out.println("Extracted values from 'booking' object: " + bookingValues);

        // Validate properties of this list using org.hamcrest.MatcherAssert.assertThat
        // Check if the list contains specific items (values from the booking object)
        assertThat(bookingValues, hasItems("Jim", "Brown", 111, true, "Breakfast"));
        System.out.println("Validated that the list of booking values contains 'Jim', 'Brown', 111, true, 'Breakfast'.");

        // Check the size of the list (number of properties in the booking object)
        assertThat(bookingValues, hasSize(6)); // firstname, lastname, totalprice, depositpaid, bookingdates (object), additionalneeds
        System.out.println("Validated the list of booking values has size 6.");

        // Check if the list is not empty
        assertThat(bookingValues, not(empty()));
        System.out.println("Validated the list of booking values is not empty.");

        // You could also validate specific items at specific positions if the order was guaranteed
        // (Note: Map values order is not guaranteed, but for demonstration, if you knew the order)
        // assertThat(bookingValues.get(0), equalTo("Jim")); // This would depend on Map iteration order

        System.out.println("\nList of values validation successful!");
    }
}
