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

public class PatchProductNameTest {

    private static final String BASE_URI = "https://api.restful-api.dev";
    private static final String PATCH_REQUEST_BODY_FILE_PATH = "src/test/resources/patchNameRequestBody.json";

    // This variable will hold the ID of the product we create before patching
    private String productIdToPatch;
    // Store initial product details to verify unchanged fields after PATCH
    private String initialName = "Original MacBook Pro";
    private int initialYear = 2020;
    private float initialPrice = 2500.00F;
    private String initialCpuModel = "Initial CPU Model";
    private String initialHardDisk = "512 GB";


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        // As requested, include relaxed HTTPS validation
        RestAssured.useRelaxedHTTPSValidation();

        // Step 1: Create a product first to get an ID for the PATCH request
        productIdToPatch = createProductAndGetId();
        System.out.println("Created Product ID for PATCH request: " + productIdToPatch);
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
     * This ensures we have a valid ID to perform the PATCH operation on.
     * Stores initial details to verify partial update later.
     */
    private String createProductAndGetId() {
        String createRequestBody = "{\n" +
                                   "    \"name\": \"" + initialName + "\",\n" +
                                   "    \"data\": {\n" +
                                   "        \"year\": " + initialYear + ",\n" +
                                   "        \"price\": " + initialPrice + ",\n" +
                                   "        \"CPU model\": \"" + initialCpuModel + "\",\n" +
                                   "        \"Hard disk size\": \"" + initialHardDisk + "\"\n" +
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
    public void testPatchProductNameAndVerifyStatusCode() {
        String requestBody;
        try {
            // Step 3: Use the given json data as body (read from file)
            requestBody = readJsonFile(PATCH_REQUEST_BODY_FILE_PATH);
        } catch (IOException e) {
            System.err.println("Failed to read patch request body from file: " + PATCH_REQUEST_BODY_FILE_PATH);
            e.printStackTrace();
            org.testng.Assert.fail("Test setup failed: Could not read request body file.");
            return;
        }

        System.out.println("Sending PATCH Request Body from file '" + PATCH_REQUEST_BODY_FILE_PATH + "':\n" + requestBody);
        System.out.println("Patching product with ID: " + productIdToPatch);

        // Step 4: Send PATCH request through RestAssured for the URI: https://api.restful-api.dev/objects/:id
        Response response = given()
                                .contentType(ContentType.JSON) // Set Content-Type header
                                .accept(ContentType.JSON)      // Indicate that we accept JSON response
                                .body(requestBody)             // Set the request body
                                .pathParam("id", productIdToPatch) // Use the dynamically obtained ID
                            .when()
                                .patch("/objects/{id}");       // Send PATCH request to the specific object ID

        // Print response details for debugging
        System.out.println("PATCH /objects/{id} Response Body: " + response.getBody().asString());
        System.out.println("PATCH /objects/{id} Status Code: " + response.getStatusCode());
        System.out.println("PATCH /objects/{id} Headers: " + response.getHeaders());

        // Step 5: Verify whether the Status Code 200 displayed by using TestNG assertion.
        response.then().assertThat()
                .statusCode(200); // Assert status code is 200 (OK)

        // Optional: Further verify the response body to ensure only the name was updated
        response.then().assertThat()
                .body("id", equalTo(productIdToPatch)) // ID should remain the same
                .body("name", equalTo("Apple MacBook Pro 16 (Updated Name)")) // Verify the updated name
                // Verify that other fields from initial creation remain unchanged (characteristic of PATCH)
                .body("data.year", equalTo(initialYear))
                .body("data.price", equalTo(initialPrice))
                .body("data.'CPU model'", equalTo(initialCpuModel))
                .body("data.'Hard disk size'", equalTo(initialHardDisk));

        System.out.println("Product name updated successfully for ID: " + productIdToPatch);
    }
}