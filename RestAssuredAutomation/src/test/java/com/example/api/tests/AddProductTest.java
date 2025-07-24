package com.example.api.tests;

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

public class AddProductTest {

    private static final String BASE_URI = "https://api.restful-api.dev";
    // Define the path to your external JSON file
    private static final String REQUEST_BODY_FILE_PATH = "src/test/resources/addProductRequestBody.json";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test
    public void testAddProductDetailsAndVerifyStatusCode() {
        String requestBody;
        try {
            // Read the request body from the external JSON file
            requestBody = readJsonFile(REQUEST_BODY_FILE_PATH);
        } catch (IOException e) {
            // Handle the exception, e.g., by failing the test or logging an error
            System.err.println("Failed to read request body from file: " + REQUEST_BODY_FILE_PATH);
            e.printStackTrace();
            org.testng.Assert.fail("Test setup failed: Could not read request body file.");
            return; // Exit the test method
        }

        System.out.println("Sending POST Request Body from file:\n" + requestBody);

        Response response = given()
                                .contentType(ContentType.JSON)
                                .accept(ContentType.JSON)
                                .body(requestBody) // Now using the content read from the file
                            .when()
                                .post("/objects");

        System.out.println("POST /objects Response Body: " + response.getBody().asString());
        System.out.println("POST /objects Status Code: " + response.getStatusCode());
        System.out.println("POST /objects Headers: " + response.getHeaders());

        response.then().assertThat()
                .statusCode(200); // Assert status code is 200 (OK)

        response.then().assertThat()
                .body("id", notNullValue())
                .body("name", equalTo("Apple MacBook Pro 16"))
                .body("data.year", equalTo(2019))
                .body("data.price", equalTo(1849.99F))
                .body("data.'CPU model'", equalTo("Intel Core i9"))
                .body("data.'Hard disk size'", equalTo("1 TB"));

        System.out.println("Product added successfully with ID: " + response.jsonPath().getString("id"));
    }

    /**
     * Helper method to read the content of a JSON file into a String.
     * @param filePath The path to the JSON file.
     * @return The content of the file as a String.
     * @throws IOException If an I/O error occurs reading from the file.
     */
    private String readJsonFile(String filePath) throws IOException {
        // Paths.get(filePath) converts the string path to a Path object
        // Files.readAllBytes reads all bytes from the file
        // new String(...) converts the byte array to a String using UTF-8 encoding
        return new String(Files.readAllBytes(Paths.get(filePath)), "UTF-8");
    }
}