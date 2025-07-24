package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // <-- ADDED THIS IMPORT STATEMENT

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class GetSingleHeaderPost { // New class for getting response with single header and POST

    @Test
    public void getResponseWithSingleHeaderAndPost() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        String requestBody = "";
        try {
            // Read the request body from the classpath
            // The file should be placed in src/test/resources/auth_input.json
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("auth_input.json");
            if (inputStream == null) {
                throw new IOException("Resource 'auth_input.json' not found on classpath.");
            }
            requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body loaded from file:\n" + requestBody);
        } catch (IOException e) {
            System.err.println("Error reading auth_input.json: " + e.getMessage());
            Assert.fail("Failed to read request body from file: " + e.getMessage());
        }

        // Define the base URI for the API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Perform the POST request with Content-Type header and request body
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Pass Content-Type header
                .body(requestBody) // Pass the request body
                .log().all() // Log all request details for debugging
                .post("/auth"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Validate the response status code (e.g., 200 OK for successful authentication)
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated status code is 200 (OK).");

        // Optionally, validate the presence of the token in the response body
        response.then().assertThat().body("token", notNullValue());
        System.out.println("Validated that 'token' exists in the response body.");

        System.out.println("\nAPI response with single header and POST method validated successfully!");
    }
}
