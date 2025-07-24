package com.example.api.tests; // Adjust package as needed

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.example.api.utils.CsvDataReader; // Import your CSV reader utility

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ParameterizedPostTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String CSV_FILE_PATH = ".//src/test/resources/postman_input.csv";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
    }

    /**
     * TestNG DataProvider to read data from the CSV file.
     * Each row in the CSV will provide data for one test iteration.
     *
     * @return 2D array of Objects containing test data (title, body, userId).
     */
    @DataProvider(name = "postDataFromCsv")
    public Object[][] getPostData() {
        // Use the utility to read data from the CSV file
        return CsvDataReader.getCSVData(CSV_FILE_PATH);
    }

    /**
     * Parameterized test method to create a new post using data from CSV.
     *
     * @param title The title for the post.
     * @param body The body content for the post.
     * @param userId The user ID for the post.
     */
    @Test(dataProvider = "postDataFromCsv")
    public void testCreateNewPostFromCsv(String title, String body, int userId) {
        // Construct the request body using the parameterized data
        String requestBody = String.format("{\n" +
                                           "    \"title\": \"%s\",\n" +
                                           "    \"body\": \"%s\",\n" +
                                           "    \"userId\": %d\n" +
                                           "}", title, body, userId);

        System.out.println("\n--- Running test with data ---");
        System.out.println("Title: " + title + ", Body: " + body + ", UserId: " + userId);
        System.out.println("Sending POST Request Body:\n" + requestBody);

        // Send POST request
        Response response = given()
                                .contentType(ContentType.JSON) // Set Content-Type header
                                .body(requestBody) // Set the request body
                            .when()
                                .post("/posts"); // Send POST request to the /posts endpoint

        // Print response for debugging
        System.out.println("POST /posts Response Body: " + response.getBody().asString());
        System.out.println("POST /posts Status Code: " + response.getStatusCode());

        // Verify the response
        response.then().assertThat()
                .statusCode(201) // Expecting HTTP 201 Created
                .body("id", notNullValue()) // Verify that an 'id' is generated and not null
                .body("id", instanceOf(Integer.class)) // Verify 'id' is an Integer
                .body("title", equalTo(title)) // Verify the title matches the input
                .body("body", equalTo(body)) // Verify the body matches the input
                .body("userId", equalTo(userId)); // Verify the userId matches the input

        System.out.println("Successfully created post with ID: " + response.jsonPath().getInt("id"));
    }
}