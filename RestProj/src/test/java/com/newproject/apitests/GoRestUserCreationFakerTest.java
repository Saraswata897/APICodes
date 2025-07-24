package com.newproject.apitests; // Using your existing API tests package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import com.github.javafaker.Faker; // Import Faker library

public class GoRestUserCreationFakerTest {

    // IMPORTANT: Replace with your actual Bearer Token from gorest.co.in
    // You must generate a token from https://gorest.co.in/consumer/login and replace this placeholder.
    private static final String GOREST_ACCESS_TOKEN = "1465cd40d948174df817997d9f578d02aab8772e220feeb3ff2a172698901153"; // <<< REPLACE THIS

    @Test
    public void createUserWithFakerData() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Creating User on GoRest API using Faker Data ---");

        // Step 1 & 3: Create a sample request body using faker
        Faker faker = new Faker();
        String name = faker.name().fullName();
        // Generate a unique email using Faker's internet methods combined with a timestamp
        String email = faker.internet().emailAddress().replace("@", System.nanoTime() + "@");
        String gender = "male"; // Can also be faker.options().option("male", "female");
        String status = "active"; // Can also be faker.options().option("active", "inactive");

        String requestBody = String.format("{\n" +
                                           "    \"name\": \"%s\",\n" +
                                           "    \"gender\": \"%s\",\n" +
                                           "    \"email\": \"%s\",\n" +
                                           "    \"status\": \"%s\"\n" +
                                           "}", name, gender, email, status);

        System.out.println("Generated Request Body:\n" + requestBody);

        // Define the base URI for the API
        RestAssured.baseURI = "https://gorest.co.in";

        // Step 2 & 4: Use auth type as bearer token and paste the token, Get the response for the API with POST method
        Response response = null;
        try {
            response = RestAssured.given()
                    .header("Content-Type", "application/json") // Set Content-Type header
                    .header("Authorization", "Bearer " + GOREST_ACCESS_TOKEN) // Set Authorization header with Bearer Token
                    .body(requestBody) // Attach the dynamically generated request body
                    .log().all() // Log all request details for debugging
                    .post("/public/v2/users"); // Specify the endpoint
        } catch (Exception e) {
            System.err.println("Error sending request: " + e.getMessage());
            Assert.fail("Failed to send POST request to GoRest API: " + e.getMessage());
        }

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Step 5: Verify the status code is 201
        response.then().assertThat().statusCode(201); // GoRest API returns 201 Created for successful user creation
        System.out.println("\nStatus code verification successful! Expected 201, Got " + response.getStatusCode());

        // Step 6: Verify the values of the response body
        // Extract values from the response
        String responseName = response.jsonPath().getString("name");
        String responseEmail = response.jsonPath().getString("email");
        String responseGender = response.jsonPath().getString("gender");
        String responseStatus = response.jsonPath().getString("status");
        Integer userId = response.jsonPath().getInt("id");


        System.out.println("\n--- Extracted Response Values ---");
        System.out.println("Created User ID: " + userId);
        System.out.println("Response Name: " + responseName);
        System.out.println("Response Email: " + responseEmail);
        System.out.println("Response Gender: " + responseGender);
        System.out.println("Response Status: " + responseStatus);

        // Assertions to ensure values match the sent data or are as expected
        Assert.assertNotNull(userId, "User ID should not be null");
        Assert.assertTrue(userId > 0, "User ID should be a positive integer");
        Assert.assertEquals(responseName, name, "User name mismatch");
        Assert.assertEquals(responseEmail, email, "User email mismatch");
        Assert.assertEquals(responseGender, gender, "User gender mismatch");
        Assert.assertEquals(responseStatus, status, "User status mismatch");

        System.out.println("\nUser creation using Faker data and verification successful!");
    }
}
