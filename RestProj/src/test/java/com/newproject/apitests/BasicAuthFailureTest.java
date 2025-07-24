package com.newproject.apitests; // Using the package from your existing project structure

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions

public class BasicAuthFailureTest {

    @Test
    public void verifyBasicAuthenticationFailure() {
        // Ensure HTTPS validation is relaxed as per your request
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Sending GET request to basic-auth endpoint with INCORRECT Basic Authentication ---");

        // Define the base URI for the API
        RestAssured.baseURI = "https://postman-echo.com";

        // Step 2: Now get the response for the API https://postman-echo.com/basic-auth with method name as GET
        // Step 3: Use the basic auth with INCORRECT credentials (e.g., Username - postman and password - wrongpassword)
        Response response = RestAssured.given()
                .auth().preemptive().basic("postman", "wrongpassword") // Using incorrect password for failure scenario
                .log().all() // Log all request details (headers, method, URI) for debugging
                .get("/basic-auth"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Step 4: Verify if the http status code is 401
        response.then().assertThat().statusCode(401);
        System.out.println("\nValidated HTTP status code is 401 (Unauthorized).");

        // Step 5: Verify if the response body displays as 'Unauthorized'
        // The postman-echo basic-auth endpoint returns "Unauthorized" for incorrect credentials
        response.then().assertThat().body(equalTo("Unauthorized"));
        System.out.println("Validated response body is 'Unauthorized'.");

        System.out.println("\nBasic Authentication API failure verification successful!");
    }
}
