package com.example.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.List;
import java.util.Map; // Import Map for user data
import static org.hamcrest.Matchers.*; // For Hamcrest assertions
import static io.restassured.RestAssured.given; // Static import for given()

/**
 * This class contains a TestNG test for retrieving API response with a specific query parameter
 * and then verifying and printing only the data for a specific ID within the paginated results.
 */
public class BasicAuthApiTest9 {

    // Define the base URI for the API
    private static final String BASE_URI = "https://reqres.in";
    // Define the endpoint for users API
    private static final String USERS_ENDPOINT = "/api/users";

    /**
     * Test method to retrieve the JSON response for the /api/users API
     * with 'page' query parameter, then extract and print data for a specific 'id'.
     * It performs a GET request to the specified endpoint,
     * retrieves the JSON response body, and then prints only the data for the desired ID.
     */
    @Test
    public void testGetUsersAndPrintSpecificIdData() { // Refactored method name
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


        int targetPage = 2;
        int targetId = 9;

        System.out.println("Starting test: testGetUsersAndPrintSpecificIdData");
        System.out.println("Target URL: " + BASE_URI + USERS_ENDPOINT);
        System.out.println("Requesting page: " + targetPage);
        System.out.println("Attempting to retrieve and print data for ID: " + targetId + " on this page.");

        // Perform the GET request with the 'page' query parameter
        Response response = given()
                                .queryParam("page", targetPage)
                            .when()
                                .get(USERS_ENDPOINT);

        System.out.println("Response received. Status Code: " + response.getStatusCode());

        // Verify the status code is 200 (OK) for the page request
        response.then().statusCode(200);
        System.out.println("Status code 200 verified successfully for page " + targetPage + ".");

        // Extract the specific user data for targetId from the 'data' array
        // This uses GPath to find the element in the 'data' list where 'id' matches targetId
        Map<String, Object> userData = response.jsonPath().getMap("data.find { it.id == " + targetId + " }");

        if (userData != null && !userData.isEmpty()) {
            System.out.println("\n--- User Data for ID " + targetId + " on Page " + targetPage + " ---");
            // Print the extracted user data
            userData.forEach((key, value) -> System.out.println(key + ": " + value));
            System.out.println("-------------------------------------------------------------------\n");
            System.out.println("Verification successful: User with ID " + targetId + " found and data printed.");
        } else {
            System.out.println("\n--- User with ID " + targetId + " not found on Page " + targetPage + " ---");
            // If the user is not found, you might want to fail the test explicitly
            // For example:
            // Assert.fail("User with ID " + targetId + " not found on page " + targetPage);
            System.out.println("Verification failed: User with ID " + targetId + " not found on page " + targetPage + ".");
        }

        System.out.println("Test testGetUsersAndPrintSpecificIdData completed successfully.");
    }
}
