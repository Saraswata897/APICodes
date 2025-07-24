package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

public class DisableRedirects { // New class for demonstrating disabling redirects

    @Test
    public void testApiWithRedirectsDisabled() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        // Define the base URI for the API
        RestAssured.baseURI = "https://simple-tool-rental-api.glitch.me";

        System.out.println("\n--- Testing API with Redirects DISABLED ---");

        // Perform the GET request, explicitly disabling redirects
        Response response = RestAssured.given()
                .redirects().follow(false) // Disable automatic redirection
                .log().all() // Log all request details for debugging
                .get("/status"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Headers:\n" + response.getHeaders().toString());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Validate the response status code.
        // If the API normally redirects, disabling follow() will show the 3xx status code.
        // If it doesn't redirect, it will show 200.
        // For 'https://simple-tool-rental-api.glitch.me/status', it typically returns 200 OK directly.
        response.then().assertThat().statusCode(equalTo(200));
        System.out.println("\nValidated status code is 200 (OK) with redirects disabled.");

        // Optionally, if you expect a redirect, you would assert on a 3xx status code
        // For example:
        // response.then().assertThat().statusCode(anyOf(equalTo(301), equalTo(302), equalTo(303), equalTo(307), equalTo(308)));
        // And then check for the 'Location' header:
        // response.then().assertThat().header("Location", notNullValue());

        System.out.println("\nAPI response with redirects disabled validated successfully!");
    }
}
