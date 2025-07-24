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
import static org.hamcrest.Matchers.*;

public class UpdateProductTest {

    private static final String BASE_URI = "https://api.restful-api.dev";
    private static final String UPDATE_REQUEST_BODY_FILE_PATH = "src/test/resources/updateProductRequestBody.json";

    // This variable will hold the ID of the product we create before updating
    private String productIdToUpdate;

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        // As requested, include relaxed HTTPS validation
        RestAssured.useRelaxedHTTPSValidation();

        // Step 1: Create a product first to get an ID for the PUT request
        productIdToUpdate = createProductAndGetId();
        System.out.println("Created Product ID for PUT request: " + productIdToUpdate);
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

    /**
     * Helper method to create a product via POST and return its ID.
     * This ensures we have a valid ID to perform the PUT operation on.
     */
    private String createProductAndGetId() {
        String createRequestBody = "{\n" +
                                   "    \"name\": \"Temporary Product for Update\",\n" +
                                   "    \"data\": {\n" +
                                   "        \"year\": 2020,\n" +
                                   "        \"price\": 1000.00,\n" +
                                   "        \"CPU model\": \"Dummy CPU\",\n" +
                                   "        \"Hard disk size\": \"500 GB\"\n" +
                                   "    }\n" +
                                   "}";

        Response createResponse = given()
                                    .contentType(ContentType.JSON)
                                    .accept(ContentType.JSON)
                                    .body(createRequestBody)
                                .when()
                                    .post("/objects");

        createResponse.then().assertThat()
                      .statusCode(200) // Expect 200 for successful creation on this API
                      .body("id", notNullValue());

        return createResponse.jsonPath().getString("id");
    }

    @Test
    public void testUpdateProductDetailsAndVerifyStatusCode() {
        String requestBody;
        try {
            // Step 3: Use the given json data as body (read from file)
            requestBody = readJsonFile(UPDATE_REQUEST_BODY_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to read update request body from file: " + UPDATE_REQUEST_BODY_FILE_PATH);
            e.printStackTrace();
            org.testng.Assert.fail("Test setup failed: Could not read request body file.");
            return;
        }

        System.out.println("Sending PUT Request Body from file '" + UPDATE_REQUEST_BODY_FILE_PATH + "':\n" + requestBody);
        System.out.println("Updating product with ID: " + productIdToUpdate);

        // Step 4: Send PUT request through RestAssured for the URI: https://api.restful-api.dev/objects/:id
        Response response = given()
                                .contentType(ContentType.JSON) // Set Content-Type header
                                .accept(ContentType.JSON)      // Indicate that we accept JSON response
                                .body(requestBody)             // Set the request body
                                .pathParam("id", productIdToUpdate) // Use the dynamically obtained ID
                            .when()
                                .put("/objects/{id}");         // Send PUT request to the specific object ID

        // Print response details for debugging
        System.out.println("PUT /objects/{id} Response Body: " + response.getBody().asString());
        System.out.println("PUT /objects/{id} Status Code: " + response.getStatusCode());
        System.out.println("PUT /objects/{id} Headers: " + response.getHeaders());

        // Step 5: Verify whether the Status Code 200 displayed by using TestNG assertion.
        response.then().assertThat()
                .statusCode(200); // Assert status code is 200 (OK)

        // Optional: Further verify the response body to ensure the product was updated as expected
        response.then().assertThat()
                .body("id", equalTo(productIdToUpdate)) // Verify the ID in response matches the updated ID
                .body("name", equalTo("Apple MacBook Pro 16")) // Verify the updated name
                .body("data.year", equalTo(2019)) // Verify updated year
                .body("data.price", equalTo(3000.99F)) // Verify updated price (use F for float)
                .body("data.'CPU model'", equalTo("Intel Core i9")) // Verify updated CPU model
                .body("data.'Hard disk size'", equalTo("1 TB")) // Verify updated Hard disk size
                .body("data.color", equalTo("silver")); // Verify new 'color' field
    }
}