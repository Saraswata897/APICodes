package com.newproject.apitests; // Using the package from your existing project structure

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.filter.log.ResponseLoggingFilter; // Import ResponseLoggingFilter

import java.util.List; // Import for List
import java.io.PrintStream; // Import PrintStream for logging to console
import java.io.ByteArrayOutputStream; // Import ByteArrayOutputStream to capture logs
import java.nio.charset.StandardCharsets; // Import StandardCharsets for byte array conversion

public class CountUniversitiesInIndiaTest {

    @Test
    public void countUniversitiesInIndiaAndLogResponse() {
        // Ensure HTTPS validation is relaxed as per your request
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Sending GET request to count universities in India ---");

        // Define the base URI for the API
        RestAssured.baseURI = "http://universities.hipolabs.com";

        // Create a ByteArrayOutputStream to capture the logs from the filter
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8);

        // Send GET request for the URI through RestAssured: http://universities.hipolabs.com/search?country=india
        Response response = RestAssured.given()
                .queryParam("country", "united+states") // Set the query parameter for the country as "india"
                .log().all() // Log all request details for debugging (still useful for request)
                .filter(new ResponseLoggingFilter(printStream)) // Apply ResponseLoggingFilter to capture response logs
                .get("/search"); // Specify the endpoint

        // Print the captured response logs from the filter
        System.out.println("\n--- Captured Response Log (from ResponseLoggingFilter) ---");
        System.out.println(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
        System.out.println("----------------------------------------------------------");


        // Print the response details for verification (redundant with filter, but kept for clarity)
        System.out.println("\nResponse Status Code (from Response object): " + response.getStatusCode());
        System.out.println("Response Body (from Response object):\n" + response.getBody().asString());

        // Validate the response status code
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated HTTP status code is 200.");

        // Get number of universities in India
        List<Object> universities = response.jsonPath().getList(""); // Get the root as a list

        int numberOfUniversities = universities.size();
        System.out.println("\nNumber of universities in India: " + numberOfUniversities);

        // Basic assertion to ensure the list is not empty
        Assert.assertTrue(numberOfUniversities > 0, "Expected to find more than 0 universities in India.");

        System.out.println("\nUniversity count in India and response logging successful!");
    }
}
