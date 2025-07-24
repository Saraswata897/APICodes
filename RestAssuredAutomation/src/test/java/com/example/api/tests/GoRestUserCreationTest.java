package com.example.api.tests; // Adjust package as needed

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GoRestUserCreationTest {

    // IMPORTANT: Replace "YOUR_ACCESS_TOKEN_HERE" with your actual GoRest API Access Token
    private static final String GOREST_ACCESS_TOKEN = "1465cd40d948174df817997d9f578d02aab8772e220feeb3ff2a172698901153";
    private static final String BASE_URL = "https://gorest.co.in/public/v2";

    // This static variable will act as our "global variable" (like Postman's APIID)
    // to store the ID of the created user, which can be used by other tests if needed.
    public static String createdUserId;
    public String getToken() {
    	return GOREST_ACCESS_TOKEN;
    }

    @BeforeClass
    public void setup() {
    	
    	RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
        // Ensure the token is not a placeholder before running tests
        if (GOREST_ACCESS_TOKEN.equals("1465cd40d948174df817997d9f578d02aab8772e220feeb3ff2a172698901153")) {
            System.err.println("WARNING: GoRest API Access Token is not set. Please replace 'YOUR_ACCESS_TOKEN_HERE' in GoRestUserCreationTest.java with your actual token.");
            // Optionally, you might want to fail the test setup if the token is missing
            // throw new RuntimeException("GoRest API Access Token is not configured.");
        }
    }

    @Test
    public void testCreateNewUser() {
        // Step 7: In Body section, copy paste the sample request body
        // We'll define a sample request body directly in the code.
        // In a real project, this might come from a data provider or a JSON file.
        String requestBody = "{\n" +
                             "    \"name\": \"Test User " + System.currentTimeMillis() + "\",\n" + // Unique name
                             "    \"gender\": \"male\",\n" +
                             "    \"email\": \"testuser" + System.currentTimeMillis() + "@example.com\",\n" + // Unique email
                             "    \"status\": \"active\"\n" +
                             "}";

        System.out.println("Sending POST Request Body:\n" + requestBody);

        // Step 5: Get the response for the API https://gorest.co.in/public/v2/users with method name as POST
        // Step 6: Use auth type as bearer token and paste the token from step 3
        Response response = given()
                                .contentType(ContentType.JSON) // Set Content-Type header
                                .header("Accept", ContentType.JSON) // Request JSON response
                                .header("Authorization", "Bearer " + GOREST_ACCESS_TOKEN) // Add Bearer Token
                                .body(requestBody) // Set the request body
                            .when()
                                .post("/users"); // Send POST request to the users endpoint

        // Print response for debugging
        System.out.println("POST /users Response Body: " + response.getBody().asString());
        System.out.println("POST /users Status Code: " + response.getStatusCode());
        System.out.println("POST /users Headers: " + response.getHeaders());

        // Step 9: Click on the send button and verify if the http status code is 201
        response.then().assertThat()
                .statusCode(201); // Expecting HTTP 201 Created

        // Step 10: Verify if the response body
        response.then().assertThat()
                .body("id", notNullValue()) // Verify that an ID is generated and not null
                .body("name", containsString("Test User")) // Verify name contains "Test User"
                .body("email", containsString("@example.com")) // Verify email format
                .body("gender", equalTo("male")) // Verify gender
                .body("status", equalTo("active")); // Verify status

        // Step 8: In scripts tab, in post script capture the ID in a global variable like APIID
        createdUserId =String.valueOf(response.jsonPath().getInt("id"));
        System.out.println("Captured User ID (APIID equivalent): " + createdUserId);
        Properties props = new Properties();
        props.setProperty("APIID",createdUserId);
        try {

        	FileOutputStream out = new FileOutputStream(".//src/test/resources/config.properties");
        	// Write properties to file
			props.store(out, "Configuration Settings");
			System.out.println("Properties file written successfully.");

        }catch(IOException e){
        	e.getMessage();
        }
        // You can now use 'createdUserId' in subsequent test methods if needed.
        // For example, in another test to GET, PUT, or DELETE this user.
    }

    // Example of another test method that could use the createdUserId
    @Test(dependsOnMethods = {"testCreateNewUser"}) // This test depends on successful user creation
    public void testGetUserById() {
        if (createdUserId.equals("0")) {
            System.err.println("No user ID captured from previous test. Skipping testGetUserById.");
            return; // Skip if no ID was captured
        }
        System.out.println("\nAttempting to retrieve user with ID: " + createdUserId);

        given()
            .header("Authorization", "Bearer " + GOREST_ACCESS_TOKEN)
            .pathParam("id", Integer.parseInt(createdUserId))
        .when()
            .get("/users/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(Integer.parseInt(createdUserId)))
            .body("name", containsString("Test User"))
            .log().body();
    }
}