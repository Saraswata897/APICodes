package com.example.api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class JsonPlaceholderPostTest {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    // This static variable can hold the ID of the created post
    public static int createdPostId; // Still useful if you wanted to just log it

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void testCreateNewPost() {
        String requestBody = "{\n" +
                             "    \"title\": \"foo\",\n" +
                             "    \"body\": \"bar\",\n" +
                             "    \"userId\": 1\n" +
                             "}";

        System.out.println("Sending POST Request Body:\n" + requestBody);

        Response response = given()
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                            .when()
                                .post("/posts");

        System.out.println("POST /posts Response Body: " + response.getBody().asString());
        System.out.println("POST /posts Status Code: " + response.getStatusCode());
        System.out.println("POST /posts Headers: " + response.getHeaders());

        response.then().assertThat()
                .statusCode(201); // Expecting HTTP 201 Created

        response.then().assertThat()
                .body("id", notNullValue()) // Verify that an 'id' is present and not null
                .body("id", instanceOf(Integer.class)) // Verify 'id' is an Integer
                .body("title", equalTo("foo")) // Verify the title
                .body("body", equalTo("bar")) // Verify the body content
                .body("userId", equalTo(1)); // Verify the userId

        createdPostId = response.jsonPath().getInt("id");
        System.out.println("Captured Created Post ID: " + createdPostId);
    }

    // Removed the testGetCreatedPost method as it will consistently fail
    // due to jsonplaceholder's non-persistent nature for POSTed data.
}