package com.newproject.apitests; // New package for your classes

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.not; // Import Hamcrest's 'not' matcher

public class ValidateJsonSchemaMismatchedDataTypes {

    @Test
    public void validateJsonPlaceholderPostSchemaMismatch() {
        // Relax HTTPS validation (as per previous instructions, though not strictly needed for jsonplaceholder)
        RestAssured.useRelaxedHTTPSValidation();

        // Define the base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        System.out.println("\n--- Sending GET request to JSONPlaceholder /posts/2 for schema mismatch validation ---");

        // Send GET request for the URI
        Response response = RestAssured.given()
                .log().all() // Log all request details for debugging
                .get("/posts/2"); // Get a specific post

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Validate the response status code (should still be 200 OK)
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated status code is 200.");

        // Ensure the response data types against input json schema data types are NOT matching
        // We expect this validation to FAIL if we were to use matchesJsonSchemaInClasspath directly.
        // By using 'not(matchesJsonSchemaInClasspath(...))', we assert that the validation FAILS,
        // which means the data types are indeed NOT matching the provided schema.
        try {
            response.then().assertThat().body(not(matchesJsonSchemaInClasspath("MismatchedPostSchema.json")));
            System.out.println("Successfully asserted that response JSON does NOT match 'MismatchedPostSchema.json' (expected mismatch).");
        } catch (AssertionError e) {
            // This catch block will execute if the schema *did* match, which is unexpected for this test.
            System.err.println("ERROR: Expected schema mismatch, but the schema unexpectedly matched! " + e.getMessage());
            Assert.fail("Schema unexpectedly matched: " + e.getMessage());
        }

        System.out.println("\nJSON schema data type mismatch validation successful!");
    }
}
