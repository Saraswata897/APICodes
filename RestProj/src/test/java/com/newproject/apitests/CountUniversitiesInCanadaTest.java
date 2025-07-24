package com.newproject.apitests; // Using the package from your existing project structure

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;
import io.restassured.filter.log.ResponseLoggingFilter; // Import ResponseLoggingFilter

import java.util.List; // Import for List
import java.io.PrintStream; // Import PrintStream for logging
import java.io.FileOutputStream; // Import FileOutputStream for writing to a file
import java.io.FileNotFoundException; // Import FileNotFoundException
import java.nio.charset.StandardCharsets; // Import StandardCharsets for byte array conversion
import java.util.concurrent.TimeUnit; // Import TimeUnit for timeouts

// Imports for Rest Assured config components for timeouts
import io.restassured.config.ConnectionConfig;
import io.restassured.config.RestAssuredConfig;

public class CountUniversitiesInCanadaTest { // New class name

    @Test
    public void countUniversitiesInCanadaAndLogToFile() {
        // Ensure HTTPS validation is relaxed as per your request
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Sending GET request to count universities in Canada ---");

        // Define the base URI for the API
        RestAssured.baseURI = "http://universities.hipolabs.com";

        // Configure Rest Assured to set a connection and read timeout
        // This makes the test more robust for larger responses or slower networks.
//        RestAssuredConfig config = RestAssured.config()
//                .connectionConfig(ConnectionConfig.connectionConfig()
//                        .connectTimeout(30, TimeUnit.SECONDS) // Increased connection timeout to 30 seconds
//                        .readTimeout(30, TimeUnit.SECONDS));  // Increased read timeout to 30 seconds

        PrintStream filePrintStream = null;
        try {
            // Create a FileOutputStream to write logs to a text file
            // The log file will be created in your project's root directory
            filePrintStream = new PrintStream(new FileOutputStream("canada_universities_response_log.txt"), true, StandardCharsets.UTF_8);

            // Send GET request for the URI through RestAssured: http://universities.hipolabs.com/search?country=canada
            Response response = RestAssured.given()
//                    .config(config) // Apply the custom configuration with timeouts
                    .queryParam("country", "Canada") // Set the query parameter for the country as "Canada"
                    .log().all() // Log all request details for debugging (still useful for request)
                    .filter(new ResponseLoggingFilter(filePrintStream)) // Apply ResponseLoggingFilter to write logs to file
                    .get("/search"); // Specify the endpoint

            // Print a confirmation that logs are written to file
            System.out.println("\n--- Response Log written to canada_universities_response_log.txt ---");

            // Print the response details for verification (still useful for console output)
            System.out.println("\nResponse Status Code (from Response object): " + response.getStatusCode());
            System.out.println("Response Body (first 500 chars from Response object):\n" + response.getBody().asString().substring(0, Math.min(response.getBody().asString().length(), 500)) + "...");

            // Validate the response status code
            response.then().assertThat().statusCode(200);
            System.out.println("\nValidated HTTP status code is 200.");

            // Get number of universities in Canada
            List<Object> universities = response.jsonPath().getList(""); // Get the root as a list

            int numberOfUniversities = universities.size();
            System.out.println("\nNumber of universities in Canada: " + numberOfUniversities);

            // Basic assertion to ensure the list is not empty
            Assert.assertTrue(numberOfUniversities > 0, "Expected to find more than 0 universities in Canada.");

            System.out.println("\nUniversity count in Canada and response logging to file successful!");

        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not create the log file. " + e.getMessage());
            Assert.fail("Failed to write response log to file: " + e.getMessage());
        } finally {
            if (filePrintStream != null) {
                filePrintStream.close(); // Ensure the PrintStream is closed
            }
        }
    }
}
