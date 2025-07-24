package com.example.api.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class RestAssuredDemoTest {

    // Base URI for the API
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";
    
    @BeforeTest
    public static void setup() {
        // This will bypass SSL certificate validation. Use with caution!
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void testGetAllPosts() {
        // Set the base URI for all requests
        RestAssured.baseURI = BASE_URI;
//        RestAssured.useRelaxedHTTPSValidation();

        // Send a GET request to the /posts endpoint
        Response response = given()
                                .when()
                                .get("/posts")
                                .then()
                                .statusCode(200) // Assert status code is 200
                                .body("size()", greaterThan(0)) // Assert the response body is not empty
                                .log().all() // Log all request/response details (optional, good for debugging)
                                .extract().response(); // Extract the response for further assertions

        // Further assertions using TestNG or Hamcrest matchers on the Response object
        Assert.assertNotNull(response.jsonPath().get("[0].id"), "First post ID should not be null");
        Assert.assertTrue(response.jsonPath().getList("title").size() > 0, "Should have titles in the response");

        System.out.println("Successfully retrieved " + response.jsonPath().getList("id").size() + " posts.");
    }

    @Test
    public void testGetSinglePostById() {
        RestAssured.baseURI = BASE_URI;
        int postId = 1; // The ID of the post we want to retrieve

        given()
                .pathParam("id", postId) // Set path parameter
                .when()
                .get("/posts/{id}") // Use path parameter in the URL
                .then()
                .statusCode(200) // Assert status code is 200
                .body("id", equalTo(postId)) // Assert the 'id' in the response matches the requested ID
                .body("userId", notNullValue()) // Assert userId is not null
                .body("title", not(emptyString())) // Assert title is not empty
                .log().body(); // Log only the response body
    }

    @Test
    public void testCreateNewPost() {
        RestAssured.baseURI = BASE_URI;

        // Define the request body as a JSON string
        String requestBody = "{\n" +
                             "    \"title\": \"foo\",\n" +
                             "    \"body\": \"bar\",\n" +
                             "    \"userId\": 1\n" +
                             "}";

        given()
                .header("Content-Type", "application/json") // Specify content type
                .body(requestBody) // Set the request body
                .when()
                .post("/posts") // Send POST request
                .then()
                .statusCode(201) // Assert status code is 201 for resource creation
                .body("id", notNullValue()) // Assert that an ID is assigned
                .body("title", equalTo("foo")) // Assert title matches what was sent
                .log().all(); // Log all details
    }
}