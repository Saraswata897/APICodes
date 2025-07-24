package com.newproject.apitests; // Using your existing API tests package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.filter.log.ResponseLoggingFilter; // Import ResponseLoggingFilter
import org.testng.annotations.Test;
import org.testng.Assert;

import java.io.ByteArrayOutputStream; // Import ByteArrayOutputStream to capture logs
import java.io.PrintStream;         // Import PrintStream for logging to console
import java.nio.charset.StandardCharsets; // Import StandardCharsets for byte array conversion
import java.util.List;               // Import for List

public class CountUniversitiesResponseLog { // Renamed class to focus on France

    private static final String BASE_URI = "http://universities.hipolabs.com";

    @Test
    public void countUniversitiesInFranceAndLogResponse() { // Only one method, focused on France
        RestAssured.useRelaxedHTTPSValidation();
        System.out.println("\n--- Sending GET request to count universities in France ---");

        // Create a ByteArrayOutputStream to capture the logs from the filter
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8);

        // Send GET request for the URI: http://universities.hipolabs.com/search?country=France
        Response response = RestAssured.given()
                .queryParam("country", "France") // Set the query parameter for the country to France
                .log().all() // Log all request details
                .filter(new ResponseLoggingFilter(printStream)) // Apply ResponseLoggingFilter to capture response logs
                .get(BASE_URI + "/search"); // Specify the endpoint

        // Print the captured response logs from the filter
        System.out.println("\n--- Captured Response Log (from ResponseLoggingFilter) ---");
        System.out.println(byteArrayOutputStream.toString(StandardCharsets.UTF_8));
        System.out.println("----------------------------------------------------------");

        // Verify the response status code
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated HTTP status code is 200.");

        // Get number of universities in France
        List<Object> universities = response.jsonPath().getList(""); // Get the root as a list

        int numberOfUniversities = universities.size();
        System.out.println("\nNumber of universities in France: " + numberOfUniversities);

        // Basic assertion to ensure the list is not empty
        Assert.assertTrue(numberOfUniversities > 0, "Expected to find more than 0 universities in France.");

        System.out.println("\nUniversity count in France and response logging successful!");
    }
}
