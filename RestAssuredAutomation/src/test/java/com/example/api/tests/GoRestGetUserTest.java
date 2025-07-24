package com.example.api.tests; // Adjust package as needed

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GoRestGetUserTest {

    // The base URL for the GoRest API
    private static final String BASE_URL = "https://gorest.co.in/public/v2";

    // IMPORTANT: The access token should be the same one used in GoRestUserCreationTest
    // For simplicity, we'll assume it's set in GoRestUserCreationTest and we're just using it.
    // In a real framework, you might load this from a shared config or a TestData class.
    private static final String GOREST_ACCESS_TOKEN = (new GoRestUserCreationTest()).getToken();


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
        // Basic check to ensure the token is set
        if (GOREST_ACCESS_TOKEN.equals("YOUR_ACCESS_TOKEN_HERE")) {
            System.err.println("WARNING: GoRest API Access Token is not set. Please ensure it's configured in GoRestUserCreationTest.java.");
            // If the token is critical and not set, you might want to throw an exception
            // throw new RuntimeException("GoRest API Access Token is not configured.");
        }
    }

    @Test()
    public void testGetUserById() {
        // Step 4: Now get the ID from response using pre script and extract the value of ID from environment variables
        // In Java, we directly use the static variable 'createdUserId' from the user creation test.
        // This is the programmatic equivalent of a "global variable" or "environment variable" in Postman.
//    	FileInputStream fis = null;
    	int userIdToRetrieve=0;
		try {
			FileInputStream fis = new FileInputStream(".//src/test/resources/config.properties");
			Properties p = new Properties();
			p.load(fis);
			userIdToRetrieve = Integer.parseInt(p.getProperty("APIID"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // Ensure a user ID was successfully captured from the creation test
        if (userIdToRetrieve == 0) {
            System.err.println("Error: No user ID was captured from GoRestUserCreationTest.testCreateNewUser. Skipping testGetUserById.");
            // You might want to fail the test explicitly if this is a critical dependency
            // Assert.fail("User ID not available for retrieval.");
            return; // Exit the test method gracefully
        }

        System.out.println("\nAttempting to retrieve user with ID: " + userIdToRetrieve);

        // Step 3: Now get the response for the API https://gorest.co.in/public/v2/users/{{APIID}} with method name as GET
        // Step 5: Use auth type as bearer token and paste the token from gorest.co.in URL
        Response response = given()
                                .header("Authorization", "Bearer " + GOREST_ACCESS_TOKEN) // Add Bearer Token
                                .pathParam("id", userIdToRetrieve) // Set the dynamic ID in the path
                            .when()
                                .get("/users/{id}"); // Send GET request to the specific user endpoint

        // Print response for debugging
        System.out.println("GET /users/{id} Response Body: " + response.getBody().asString());
        System.out.println("GET /users/{id} Status Code: " + response.getStatusCode());
        System.out.println("GET /users/{id} Headers: " + response.getHeaders());

        // Step 6: Click on the send button and verify if the http status code is 200
        response.then().assertThat()
                .statusCode(200); // Expecting HTTP 200 OK

        // Step 7: Verify if the response body
        response.then().assertThat()
                .body("id", equalTo(userIdToRetrieve)) // Verify the ID matches the requested ID
                .body("name", containsString("Test User")) // Verify name (should match what was created)
                .body("email", containsString("@example.com")) // Verify email (should match what was created)
                .body("gender", equalTo("male")) // Verify gender
                .body("status", equalTo("active")); // Verify status

        System.out.println("Successfully retrieved user with ID: " + userIdToRetrieve);
    }
}