package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map; // Import Map for user data
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for retrieving API response with a specific path parameter.
 */
public class BasicAuthApiTest10 {

    // Define the base URI for the API
    private static final String BASE_URI = "https://jsonplaceholder.typicode.com";
    // Define the base endpoint for posts API, with a placeholder for the path parameter
    private static final String POSTS_ENDPOINT = "/posts/{id}";

    /**
     * Test method to retrieve the JSON response for the /posts API
     * with an 'id' path parameter.
     * It performs a GET request to the specified endpoint with the path parameter,
     * retrieves the JSON response body, and then prints its values.
     */
    @Test
    public void testGetPostWithPathParameter() { // Refactored method name for path parameter test
        // Set the base URI for all requests in this test
        RestAssured.baseURI = BASE_URI;

        // IMPORTANT: Relax HTTPS validation to bypass SSLHandshakeException in test environments.
        // This is generally NOT recommended for production code due to security implications.
        RestAssured.useRelaxedHTTPSValidation();

        // --- Optional: Proxy Configuration to bypass potential firewall/proxy issues ---
        // If you are behind a corporate proxy that requires authentication,
        // uncomment and configure the following lines with your proxy details.
        // Replace "your_proxy_host", 8080, "your_proxy_username", and "your_proxy_password"
        // with your actual proxy server's host, port, username, and password.
        /*
        String proxyHost = "your_proxy_host"; // e.g., "http://proxy.example.com"
        int proxyPort = 8080; // e.g., 8080
        String proxyUsername = "your_proxy_username";
        String proxyPassword = "your_proxy_password";

        RestAssured.proxy(proxyHost, proxyPort, proxyUsername, proxyPassword);
        System.out.println("Configured proxy: " + proxyHost + ":" + proxyPort + " with authentication.");
        */
        // -------------------------------------------------------------------------------


        int targetId = 5; // The path parameter value

        System.out.println("Starting test: testGetPostWithPathParameter");
        System.out.println("Target API: " + BASE_URI + "/posts/" + targetId); // Construct the full URL for logging
        System.out.println("Using path parameter: id=" + targetId);

        // Perform the GET request with the 'id' path parameter
        // given(): Starts the request specification
        // pathParam("id", targetId): Sets the path parameter named 'id' with value '5'
        // when().get(POSTS_ENDPOINT): Specifies the HTTP method and endpoint template
        Response response = given()
                                .pathParam("id", targetId) // Set the path parameter
                            .when()
                                .get(POSTS_ENDPOINT); // Use the endpoint with the placeholder

        System.out.println("Response received. Status Code: " + response.getStatusCode());

        // Verify the status code is 200 (OK)
        response.then().statusCode(200);
        System.out.println("Status code 200 verified successfully for post ID " + targetId + ".");

        // Retrieve the response body and print it as JSON
        System.out.println("\n--- API Response JSON for Post ID " + targetId + " ---");
        response.prettyPrint(); // This method prints the JSON body in a nicely formatted way
        System.out.println("------------------------------------------------------\n");

        System.out.println("Test testGetPostWithPathParameter completed successfully.");
    }
}
