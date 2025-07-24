package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.Headers; // Import Headers class
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GetAllHeaders { // Renamed class to reflect functionality

    @Test
    public void getAllHeadersFromBookingApi() {
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

        // Extract and print all headers
        Headers allHeaders = response.headers();
        System.out.println("\n--- All Response Headers ---");
        allHeaders.forEach(header -> System.out.println(header.getName() + ": " + header.getValue()));
        System.out.println("--------------------------");

        // Verify the status code (as a basic check for successful response)
        response.then().assertThat().statusCode(200);

        // Optional: Verify presence of a common header
        Assert.assertNotNull(allHeaders.get("Content-Type"), "Content-Type header should be present");
        Assert.assertTrue(allHeaders.get("Content-Type").getValue().contains("application/json"), "Content-Type header should contain 'application/json'");

        System.out.println("\nAll headers extracted and printed successfully!");
    }
}
