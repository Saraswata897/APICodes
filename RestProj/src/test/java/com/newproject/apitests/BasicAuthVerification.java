package com.newproject.apitests; // Using the package from your existing project structure

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions

public class BasicAuthVerification {

    @Test
    public void verifyBasicAuthApi() {
        System.out.println("\n--- Sending GET request to basic-auth endpoint with Basic Authentication ---");

        // Define the base URI for the API
        RestAssured.baseURI = "https://postman-echo.com";
        RestAssured.useRelaxedHTTPSValidation();
        // Perform the GET request with basic authentication
        Response response = RestAssured.given()
                .auth().preemptive().basic("postman", "password") // Set Basic Authentication credentials
                .log().all() // Log all request details (headers, method, URI) for debugging
                .get("/basic-auth"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Step 6: Verify if the http status code is 200
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated HTTP status code is 200.");

        // Step 7: Verify if the response body is visible and contains the success message
        // The postman-echo basic-auth endpoint returns {"authenticated": true} on success
        response.then().assertThat().body("authenticated", equalTo(true));
        System.out.println("Validated response body contains 'authenticated: true'.");

        System.out.println("\nBasic Authentication API verification successful!");
    }
}
