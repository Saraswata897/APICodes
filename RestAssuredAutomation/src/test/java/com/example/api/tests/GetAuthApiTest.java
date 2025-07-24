package com.example.api.tests; // Adjust package as needed

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*; // For Hamcrest matchers
import static io.restassured.RestAssured.given;

public class GetAuthApiTest {

    @Test
    public void testGetAuthApi() {
        // 1. Set the base URL (optional, but good practice for multiple requests)
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // 2. Make the GET request
        Response response = given()
                            .when()
                                .get("/auth"); // Endpoint for the GET request

        // 3. Print the response for debugging (optional)
        System.out.println("Response Body: " + response.getBody().asString());
        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Headers: " + response.getHeaders());

        // 4. Verify the response
        // Verify status code
        response.then().assertThat().statusCode(200); // Expecting HTTP 200 OK

        // Verify a specific header (example: Content-Type)
        response.then().assertThat().header("Content-Type", containsString("application/json"));

        // For the /auth endpoint, the response body is typically a JSON object
        // with a 'token' if authentication is successful. If you're just hitting
        // the /auth endpoint without credentials, you might get a 405 Method Not Allowed
        // or a simple message. Let's assume a successful token response for demonstration.

        // Example: Verify a field in the JSON response (if applicable for /auth)
        // If /auth returns a JSON with a "reason" or "message" field for unauthorized requests:
        response.then().assertThat().body("reason", equalTo("Bad credentials"));
        // OR if it returns a token upon successful authentication (which is not how /auth usually works without a POST body):
        // response.then().assertThat().body("token", notNullValue());
        // response.then().assertThat().body("token", instanceOf(String.class));

        // A more realistic scenario for /auth would be a POST request to get a token.
        // For a simple GET on /auth, it might return a 405 or information about the endpoint.
        // Let's modify the expectation based on a typical GET /auth response without credentials.
        // Often, a GET on /auth without credentials would result in a 405 Method Not Allowed
        // or an informational message.

        // Let's adjust the assertion to expect a 405 if you just do a GET on /auth without a body.
        // If the API truly allows GET on /auth and returns data, adjust the assertions accordingly.
        // Based on common REST API practices, /auth is usually a POST for obtaining a token.
        // If a GET is allowed, it might return general authentication info or an error.
        // For the 'restful-booker' API, a GET to /auth typically results in a 405.
        // Let's adjust the test to expect a 405. If your actual API behaves differently,
        // update the assertions.

        response.then().assertThat().statusCode(405); // Expecting HTTP 405 Method Not Allowed
        response.then().assertThat().body(equalTo("Method Not Allowed")); // Verify the body content
    }
}