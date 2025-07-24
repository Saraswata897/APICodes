package com.newproject.apitests; // Using the package from your existing project structure

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.ResponseSpecification; // Import ResponseSpecification
import io.restassured.builder.ResponseSpecBuilder; // Import ResponseSpecBuilder
import io.restassured.http.ContentType; // Import ContentType for JSON validation
import org.testng.annotations.BeforeClass; // Import BeforeClass annotation
import org.testng.annotations.Test;
import org.testng.Assert;

import java.util.List;
import java.util.Map; // For handling JSON objects/maps

public class USPopulationDetailsResponseSpecBuilderTest { // New class name

    private static ResponseSpecification responseSpec; // Declare ResponseSpecification

    @BeforeClass // Step 4: Execute the ResponseSpecBuilder method @Beforeclass
    public void setupResponseSpecification() {
        // Ensure HTTPS validation is relaxed as per your request
        RestAssured.useRelaxedHTTPSValidation();

        // Step 3: Create ResponseSpecBuilder method for status code and Content type as JSON
        ResponseSpecBuilder builder = new ResponseSpecBuilder();
        builder.expectStatusCode(200); // Expect HTTP status code 200 OK
        builder.expectContentType(ContentType.JSON); // Expect Content-Type to be JSON
        // Removed: builder.logAll(); // This method is not directly available on ResponseSpecBuilder
                                    // Logging is typically done via .log().all() in the request chain
                                    // or using specific logging filters.

        responseSpec = builder.build(); // Build the ResponseSpecification
    }

    @Test
    public void getUSPopulationDetails2019AndValidateResponseSpec() {
        System.out.println("\n--- Sending GET request for US Population details for 2019 ---");

        Response response = null;
        try {
            // Step 5: Send GET request
            response = RestAssured.given()
                    .baseUri("https://datausa.io")
                    .basePath("/api/data")
                    .queryParam("drilldowns", "Nation")
                    .queryParam("measures", "Population")
                    .queryParam("year", "2019") // Query for year 2019
                    .log().all() // Log request details (and implicitly the response after .then().spec())
                    .when()
                    .get()
                    .then()
                    .spec(responseSpec) // Apply the ResponseSpecification here
                    .extract().response(); // Extract the response after applying spec

            // Print the response details for verification (already logged by responseSpec, but useful for direct access)
            System.out.println("\nResponse Status Code (from Response object): " + response.getStatusCode());
            System.out.println("Response Body (from Response object):\n" + response.getBody().asString());

            System.out.println("\nValidated HTTP status code is 200 and Content-Type is JSON via Response Specification.");

            // Extract data from the "data" array in the response
            List<Map<String, Object>> data = response.jsonPath().getList("data");

            // Assert that data is not empty and contains at least one entry
            Assert.assertFalse(data.isEmpty(), "Expected 'data' array to not be empty.");
            System.out.println("Validated 'data' array is not empty.");

            // Assuming we are interested in the first entry in the data array for 2019 US population
            Map<String, Object> populationDetails = data.get(0);

            System.out.println("\n--- US Population Details for 2019 (from Response) ---");

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
            Assert.assertEquals(idYear, Integer.valueOf(2019), "Expected ID Year to be 2019");

            // Step 9: Print Population
            Long population = ((Number) populationDetails.get("Population")).longValue(); // Handle as Long
            System.out.println("Population: " + population);
            Assert.assertTrue(population > 0, "Expected Population to be greater than 0");

            // Step 10: Print Slug Nation
            String slugNation = (String) populationDetails.get("Slug Nation");
            System.out.println("Slug Nation: " + slugNation);
            Assert.assertEquals(slugNation, "united-states", "Expected Slug Nation to be 'united-states'");

            // Step 11: Print source_name & source_description
            List<Map<String, Object>> sources = response.jsonPath().getList("source");
            Assert.assertFalse(sources.isEmpty(), "Expected 'source' array to not be empty.");
            Map<String, Object> firstSource = sources.get(0);

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

            System.out.println("\nUS Population details retrieval and verification successful for 2019!");

        } catch (Exception e) {
            System.err.println("\n--- ERROR DURING API CALL ---");
            System.err.println("An error occurred while making the API request or processing the response: " + e.getMessage());
            if (response != null) {
                System.err.println("Received Status Code: " + response.getStatusCode());
                System.err.println("Received Response Body: " + response.getBody().asString());
            }
            System.err.println("This might be a temporary issue with the datausa.io API (e.g., network issues, 5xx errors).");
            System.err.println("Please try re-running the test. If the issue persists, check the API's status.");
            Assert.fail("API test failed due to an unexpected error: " + e.getMessage());
        }
    }
}
