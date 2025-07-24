package com.newproject.apitests; // Using your existing API tests package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonFilePostTest { // New class for POST request using JSON data from a file

    @Test
    public void createObjectWithJsonFromFile() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Creating Object with JSON Request Body from File ---");

        String requestBody = "";
        try {
            // Step 3: Place the attached json file in the suite (src/test/resources/)
            // Read the request body from the classpath
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("samsung_edge_payload.json");
            if (inputStream == null) {
                throw new IOException("Resource 'samsung_edge_payload.json' not found on classpath.");
            }
            requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body loaded from file:\n" + requestBody);
        } catch (IOException e) {
            System.err.println("Error reading samsung_edge_payload.json: " + e.getMessage());
            Assert.fail("Failed to read request body from file: " + e.getMessage());
        }

        // Define the base URI for the API
        RestAssured.baseURI = "https://api.restful-api.dev";

        // Step 4: Use the above file as json body and send POST request for the URI
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Attach the request body read from file
                .log().all() // Log all request details for debugging
                .post("/objects"); // Specify the endpoint

        // Step 5: Verify the Status code 200 displayed
        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());

        // Verify the response status code
        response.then().assertThat().statusCode(200);
        System.out.println("\nStatus code verification successful! Expected 200, Got " + response.getStatusCode());

        // Step 6: Print the entire response
        System.out.println("\nEntire Response Body:\n" + response.getBody().asString());

        // Optional: Basic assertion to ensure a new object was created (check for 'id' and 'createdAt')
        response.then().assertThat().body("id", org.hamcrest.Matchers.notNullValue());
        response.then().assertThat().body("createdAt", org.hamcrest.Matchers.notNullValue());
        System.out.println("Validated that 'id' and 'createdAt' fields exist in the response.");

        System.out.println("\nObject creation with JSON from file and verification successful!");
    }
}
