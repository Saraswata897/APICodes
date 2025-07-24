package com.example.api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteProductTest {

    private static final String BASE_URI = "https://api.restful-api.dev";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.useRelaxedHTTPSValidation();
    }

    private String createProductAndGetId() {
        String createRequestBody = "{\n" +
                                   "    \"name\": \"Product for Deletion\",\n" +
                                   "    \"data\": {\n" +
                                   "        \"year\": 2024,\n" +
                                   "        \"price\": 1234.56,\n" +
                                   "        \"CPU model\": \"DeleteMe CPU\",\n" +
                                   "        \"Hard disk size\": \"128 GB\"\n" +
                                   "    }\n" +
                                   "}";

        Response createResponse = given()
                                    .contentType(ContentType.JSON)
                                    .accept(ContentType.JSON)
                                    .body(createRequestBody)
                                .when()
                                    .post("/objects");

        createResponse.then().assertThat()
                      .statusCode(200)
                      .body("id", notNullValue());

        return createResponse.jsonPath().getString("id");
    }

    @Test
    public void testDeleteExistingProductAndVerify200() {
        // Create a product to ensure we have a valid ID to delete
        String productIdToDelete = createProductAndGetId();
        System.out.println("\nAttempting to delete existing product with ID: " + productIdToDelete);

        // Send DELETE request for the existing product
        Response deleteResponse = given()
                                    .pathParam("id", productIdToDelete)
                                .when()
                                    .delete("/objects/{id}");

        // Print response details for debugging
        System.out.println("DELETE /objects/{id} Response Body: " + deleteResponse.getBody().asString());
        System.out.println("DELETE /objects/{id} Status Code: " + deleteResponse.getStatusCode());

        // Verify whether the Status Code 200 is displayed
        deleteResponse.then().assertThat()
                      .statusCode(200) // Assert status code is 200 (OK)
                      // *** CORRECTED LINE HERE ***
                      .body("message", containsString("has been deleted.")); // The API returns a message like "Object with id = X has been deleted."

        System.out.println("Successfully deleted product with ID: " + productIdToDelete);

        // Optional: Verify that the product is actually gone (expect 404 on GET)
        System.out.println("Verifying deletion by attempting GET on the same ID...");
        given()
            .pathParam("id", productIdToDelete)
        .when()
            .get("/objects/{id}")
        .then()
            .statusCode(404) // Expect 404 Not Found after deletion
            // This assertion seems to be correct based on your previous logs, but double check exact casing/spacing
            .body("error", equalTo("Oject with id=" + productIdToDelete + " was not found."));
//        .body("error", equalTo("Oject with id=ff8081819782e69e0197caee79244a25 was not found."));
        System.out.println("Confirmed product with ID: " + productIdToDelete + " is no longer found.");
    }

    @Test
    public void testDeleteNonExistentProductAndVerify404() {
        String nonExistentId = "nonexistent_id_1234567890abcdef"; // Or use UUID.randomUUID().toString(); for a truly unique ID
        System.out.println("\nAttempting to delete non-existent product with ID: " + nonExistentId);

        Response deleteResponse = given()
                                    .pathParam("id", nonExistentId)
                                .when()
                                    .delete("/objects/{id}");

        System.out.println("DELETE /objects/{id} Response Body: " + deleteResponse.getBody().asString());
        System.out.println("DELETE /objects/{id} Status Code: " + deleteResponse.getStatusCode());

        deleteResponse.then().assertThat()
                      .statusCode(404); // Assert status code is 404 (Not Found)

        // Corrected assertion for the JSON error structure and message for DELETE
        deleteResponse.then().assertThat()
                      .body("error", equalTo("Object with id = " + nonExistentId + " doesn't exist."));

        System.out.println("Confirmed deletion attempt on non-existent product with ID: " + nonExistentId + " returned 404.");
    }
}