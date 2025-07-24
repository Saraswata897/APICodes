package com.newproject.apitests; // New package for your classes

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath; // Import for JSON schema validation

public class ValidateJsonSchemaDataTypes {

    @Test
    public void validateJsonPlaceholderPostSchema() {
        // Relax HTTPS validation (as per previous instructions, though not strictly needed for jsonplaceholder)
        RestAssured.useRelaxedHTTPSValidation();

        // Define the base URI for the API
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        System.out.println("\n--- Sending GET request to JSONPlaceholder /posts/2 ---");

        // Send GET request for the URI
        Response response = RestAssured.given()
                .log().all() // Log all request details for debugging
                .get("/posts/2"); // Get a specific post

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Validate the response status code
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated status code is 200.");

        // Ensure the response data types against input json schema data types are matching
        // by using Rest Assured's JsonSchemaValidator.
        // The schema file 'PostSchema.json' should be in src/test/resources/
        response.then().assertThat().body(matchesJsonSchemaInClasspath("PostSchema.json"));
        System.out.println("Validated response JSON against 'PostSchema.json' successfully!");

        System.out.println("\nJSON schema data type validation successful!");
    }
}
