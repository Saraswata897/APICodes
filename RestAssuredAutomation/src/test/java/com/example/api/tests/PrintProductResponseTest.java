package com.example.api.tests; // Adjust package as needed

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*; // For Hamcrest assertions

public class PrintProductResponseTest {

    // Base URI for the API
    private static final String BASE_URI = "https://api.restful-api.dev";
    // Define the path to your external JSON file (reusable for other tests)
    private static final String PRODUCT_DETAILS_JSON_FILE_PATH = "src/test/resources/productDetails.json";

    @BeforeClass
    public void setup() {
        // Set the base URI for all requests in this test class
        RestAssured.baseURI = BASE_URI;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void testAddProductAndPrintSuccessResponse() {
        String requestBody;
        try {
            // Read the request body from the external JSON file
            requestBody = readJsonFile(PRODUCT_DETAILS_JSON_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to read request body from file: " + PRODUCT_DETAILS_JSON_FILE_PATH);
            e.printStackTrace();
            org.testng.Assert.fail("Test setup failed: Could not read request body file.");
            return;
        }

        System.out.println("Sending POST Request Body from file '" + PRODUCT_DETAILS_JSON_FILE_PATH + "':\n" + requestBody);

        Response response = given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(requestBody) // Now using the content read from the file
                            .when()
                                .post("/objects");

        // Assert that the status code is 200 for success
        response.then().assertThat()
                .statusCode(200);

        // Print the entire success response body
        System.out.println("\n--- Success Response Body ---");
        response.prettyPrint();
        System.out.println("\n--- End of Response ---");

        // Optional: Further verify specific parts of the response body
        response.then().assertThat()
                .body("id", notNullValue())
                .body("name", equalTo("Dell I5"))
                .body("data.year", equalTo(2023))
                .body("data.price", equalTo(20000)) // Use Float for decimal comparison
                .body("data.'CPU model'", equalTo("Intel Core i9"))
                .body("data.'Hard disk size'", equalTo("2 TB"));

        System.out.println("Product added successfully with ID: " + response.jsonPath().getString("id"));
    }

    /**
     * Helper method to read the content of a file into a String.
     * This can be reused across different test classes that need to read file content.
     * @param filePath The path to the file.
     * @return The content of the file as a String.
     * @throws IOException If an I/O error occurs reading from the file.
     */
    public static String readJsonFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
    }
}