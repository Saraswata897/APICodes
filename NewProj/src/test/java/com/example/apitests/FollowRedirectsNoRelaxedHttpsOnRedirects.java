package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

public class FollowRedirectsNoRelaxedHttpsOnRedirects { // New class for controlling redirects and HTTPS validation

    @Test
    public void testFollowRedirectsWithSpecificHttpsValidation() {
        // This line is included as per your request to relax HTTPS validation globally.
        // The .and().relaxedHTTPSValidation() specifically applies to redirects.
        RestAssured.useRelaxedHTTPSValidation();

        // Define the base URI for the API
        RestAssured.baseURI = "https://simple-tool-rental-api.glitch.me";

        System.out.println("\n--- Testing API with specific redirect and HTTPS validation configuration ---");
        System.out.println("Note: 'https://simple-tool-rental-api.glitch.me/status' typically returns 200 OK directly and does not perform redirects.");
        System.out.println("Therefore, 'followRedirects(true)' will not change the request flow for this specific URL,");
        System.out.println("and 'relaxedHTTPSValidation()' on redirects will only be relevant if a redirect *were* to occur to an invalid HTTPS endpoint.");


        // Perform the GET request, configuring redirects and HTTPS validation
        Response response = RestAssured.given()
                .redirects().follow(true).and().relaxedHTTPSValidation() // Follow redirects, but apply relaxed HTTPS validation to redirected requests
                .log().all() // Log all request details for debugging
                .get("/status"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Validate the response status code.
        // For 'https://simple-tool-rental-api.glitch.me/status', it should be 200 OK.
        response.then().assertThat().statusCode(equalTo(200));
        System.out.println("\nValidated status code is 200 (OK) with redirect and HTTPS validation configuration applied.");

        System.out.println("\nAPI response with specific redirect and HTTPS validation configuration validated successfully!");
    }
}
