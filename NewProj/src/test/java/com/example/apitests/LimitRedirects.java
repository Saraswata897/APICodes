package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.apache.http.client.RedirectException; // Keep this import for checking the cause if the URL ever redirects

public class LimitRedirects { // Class for demonstrating limiting redirects

    @Test
    public void testApiWithLimitedRedirects() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        // Define the base URI for the API as requested
        RestAssured.baseURI = "https://simple-tool-rental-api.glitch.me";

        System.out.println("\n--- Testing API with LIMITED Redirects (max 1) ---");
        System.out.println("Note: The API 'https://simple-tool-rental-api.glitch.me/status' typically returns 200 OK directly and does not perform redirects.");
        System.out.println("Therefore, setting redirects().max() will not cause a RedirectException for this specific URL.");

        Response response = null;
        boolean exceptionCaught = false; // Renamed for broader exception type
        String exceptionMessage = "";
        Throwable caughtCause = null;

        try {
            // Perform the GET request, limiting the number of redirects to 1
            // For this URL, it will simply return 200 OK without redirects.
            response = RestAssured.given()
                    .redirects().max(1) // Limit automatic redirection to 1 (will not be hit for this URL)
                    .log().all() // Log all request details for debugging
                    .get("/status"); // Specify the endpoint
        } catch (Exception e) { // Catch any exception that might occur
            System.out.println("\nCaught an exception: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            exceptionCaught = true;
            exceptionMessage = e.getMessage();
            caughtCause = e.getCause();
            // If an exception is caught, it means something unexpected happened or the API started redirecting.
            Assert.fail("An unexpected exception occurred: " + e.getMessage());
        }

        // Validate that no exception was caught (since this URL doesn't redirect)
        Assert.assertFalse(exceptionCaught, "An unexpected exception was caught.");

        // Validate the response status code.
        // For 'https://simple-tool-rental-api.glitch.me/status', it should be 200 OK.
        Assert.assertNotNull(response, "Response object should not be null.");
        response.then().assertThat().statusCode(equalTo(200));
        System.out.println("\nValidated status code is 200 (OK) after attempting to limit redirects.");

        System.out.println("\nAPI response with limited redirects (on a non-redirecting API) validated successfully!");
    }
}
