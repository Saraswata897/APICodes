package com.newproject.apitests; // Using the package from your existing project structure

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions

public class BasicAuthApiTest {

    @Test
    public void verifyBasicAuthenticationApi() {
        // Ensure HTTPS validation is relaxed as per your request
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Sending GET request to basic-auth endpoint with Basic Authentication ---");

        // Define the base URI for the API
        RestAssured.baseURI = "https://postman-echo.com";

        // Step 3: Now get the response for the API https://postman-echo.com/basic-auth with method name as GET
        // Step 4: Use the basic auth with credentials as Username - postman and password - password
        Response response = RestAssured.given()
                .auth().preemptive().basic("postman", "password") // Set Basic Authentication credentials
                .log().all() // Log all request details (headers, method, URI) for debugging
                .get("/basic-auth"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Step 5: Verify the status code of the Basic Auth is 200
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated HTTP status code is 200.");

        // Step 6: Verify the response of the Basic Auth
        // The postman-echo basic-auth endpoint returns {"authenticated": true} on success
        response.then().assertThat().body("authenticated", equalTo(true));
        System.out.println("Validated response body contains 'authenticated: true'.");

        System.out.println("\nBasic Authentication API verification successful!");
    }
}
