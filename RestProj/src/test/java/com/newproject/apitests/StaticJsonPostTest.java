package com.newproject.apitests; // Using your existing API tests package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.newproject.data.JsonData; // Import the new JsonData class

public class StaticJsonPostTest { // New class for POST request using static JSON data

    @Test
    public void createObjectWithStaticJson() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Creating Object with Static JSON Request Body ---");

        // Step 3: Use the static string variable from the separate class
        String requestBody = JsonData.VIVO_PRO_16_JSON;

        System.out.println("Request Body (from static variable):\n" + requestBody);

        // Define the base URI for the API
        RestAssured.baseURI = "https://api.restful-api.dev";

        // Step 4: Use the above variable as json body and send POST request for the URI
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Attach the request body from the static variable
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

        System.out.println("\nObject creation with static JSON and verification successful!");
    }
}
