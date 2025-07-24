package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class VerifyStatusCodeWithAssertThat {

    @Test
    public void verifyBookingApiStatusCodeUsingAssertThat() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        String requestBody = "";
        try {
            // Read the request body from the classpath
            // The file should be placed in src/test/resources/booking_input_scenario2.json
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("booking_input.json");
            if (inputStream == null) {
                throw new IOException("Resource 'booking_input.json' not found on classpath.");
            }
            requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body loaded from file:\n" + requestBody);
        } catch (IOException e) {
            System.err.println("Error reading booking_input_scenario2.json: " + e.getMessage());
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

        // Verify the response status code using Rest Assured's assertThat method
        response.then().assertThat().statusCode(200);

        System.out.println("\nStatus code verification successful using assertThat for new scenario!");
    }
}
