package com.newproject.apitests; // Using the package from your existing project structure

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification; // Import RequestSpecification
import org.testng.annotations.BeforeClass; // Import BeforeClass annotation
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.List;
import java.util.Map; // For handling JSON objects/maps

public class USPopulationDetailsTest {

    private static RequestSpecification requestSpec; // Declare RequestSpecification

    @BeforeClass // Step 4: Execute the request specification method @Beforeclass
    public void setupRequestSpecification() {
        // Ensure HTTPS validation is relaxed as per your request
        RestAssured.useRelaxedHTTPSValidation();

        // Step 3: Create Request Specification method for the URI
        requestSpec = RestAssured.given()
                .baseUri("https://datausa.io") // Set the base URI
                .queryParam("drilldowns", "Nation") // Set drilldowns parameter
                .queryParam("measures", "Population") // Set measures parameter
                .queryParam("year", "2022") // Set year parameter
                .log().all(); // Log all request details for debugging
    }

    @Test
    public void getUSPopulationDetails2022() {
        System.out.println("\n--- Sending GET request for US Population details for 2022 ---");

        // Step 5: Send GET request using the Request Specification
        Response response = requestSpec.get("/api/data"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Validate the response status code
        response.then().assertThat().statusCode(200);
        System.out.println("\nValidated HTTP status code is 200.");

        // Extract data from the "data" array in the response
        List<Map<String, Object>> data = response.jsonPath().getList("data");

        // Assert that data is not empty and contains at least one entry
        Assert.assertFalse(data.isEmpty(), "Expected 'data' array to not be empty.");
        System.out.println("Validated 'data' array is not empty.");

        // Assuming we are interested in the first entry in the data array for 2022 US population
        Map<String, Object> populationDetails = data.get(0);

        System.out.println("\n--- US Population Details for 2022 ---");

        // Step 6: Print ID Nation
        String idNation = (String) populationDetails.get("ID Nation");
        System.out.println("ID Nation: " + idNation);
        Assert.assertEquals(idNation, "01000US", "Expected ID Nation to be '01000US'");

        // Step 7: Print Nation
        String nation = (String) populationDetails.get("Nation");
        System.out.println("Nation: " + nation);
        Assert.assertEquals(nation, "United States", "Expected Nation to be 'United States'");

        // Step 8: Print ID Year
        Integer idYear = (Integer) populationDetails.get("ID Year");
        System.out.println("ID Year: " + idYear);
        Assert.assertEquals(idYear, Integer.valueOf(2022), "Expected ID Year to be 2022");

        // Step 9: Print Population
        Long population = ((Number) populationDetails.get("Population")).longValue(); // Handle as Long
        System.out.println("Population: " + population);
        Assert.assertTrue(population > 0, "Expected Population to be greater than 0");

        // Step 10: Print Slug Nation
        String slugNation = (String) populationDetails.get("Slug Nation");
        System.out.println("Slug Nation: " + slugNation);
        Assert.assertEquals(slugNation, "united-states", "Expected Slug Nation to be 'united-states'");

        // Step 11: Print source_name & source_description
        // Corrected extraction for nested annotations.source_name and annotations.source_description
        List<Map<String, Object>> sources = response.jsonPath().getList("source");
        Assert.assertFalse(sources.isEmpty(), "Expected 'source' array to not be empty.");
        Map<String, Object> firstSource = sources.get(0);

        // Accessing nested fields using direct JSONPath or by casting nested maps
        // The 'annotations' is a map within 'firstSource'
        Map<String, Object> annotations = (Map<String, Object>) firstSource.get("annotations");

        String sourceName = null;
        String sourceDescription = null;

        if (annotations != null) {
            sourceName = (String) annotations.get("source_name");
            sourceDescription = (String) annotations.get("source_description");
        }

        System.out.println("Source Name: " + sourceName);
        System.out.println("Source Description: " + sourceDescription);
        Assert.assertNotNull(sourceName, "Source Name should not be null");
        Assert.assertNotNull(sourceDescription, "Source Description should not be null");

        System.out.println("\nUS Population details retrieval and verification successful!");
    }
}
