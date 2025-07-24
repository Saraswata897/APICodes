package com.example.api.tests; // Package declaration

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test; // Changed from JUnit's @Test
import org.testng.Assert; // Changed from JUnit's Assertions

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RestAssuredStatusCodeVerification {

    @Test // TestNG's @Test annotation
    public void verifyBookingApiStatusCode() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        String requestBody = "";
        try {
            // Read the request body from the separate JSON file
            // Assuming booking_input.json is in the project root or accessible path
            requestBody = new String(Files.readAllBytes(Paths.get("booking_input.json")));
            System.out.println("Request Body loaded from file:\n" + requestBody);
        } catch (IOException e) {
            System.err.println("Error reading booking_input.json: " + e.getMessage());
            // TestNG's Assert.fail
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

        // Verify the response status code using TestNG's Assert.assertEquals
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());

        System.out.println("\nStatus code verification successful!");
    }
}
