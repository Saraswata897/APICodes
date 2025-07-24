package com.example.api.tests; // Adjust package as needed

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PostBookingApiTest {

    private static String authToken;
    private static final String BASE_URL = "https://restful-booker.herokuapp.com";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;
        // Step 1: Obtain the authentication token
        authToken = getAuthToken("admin", "password123");
        System.out.println("Obtained Auth Token: " + authToken);
    }

    /**
     * Helper method to get the authentication token.
     * This simulates getting the token from a previous test case.
     *
     * @param username The username for authentication.
     * @param password The password for authentication.
     * @return The authentication token string.
     */
    private String getAuthToken(String username, String password) {
        String requestBody = "{\n" +
                             "    \"username\" : \"" + username + "\",\n" +
                             "    \"password\" : \"" + password + "\"\n" +
                             "}";

        Response response = given()
                                .contentType(ContentType.JSON)
                                .body(requestBody)
                            .when()
                                .post("/auth");

        response.then().assertThat()
                .statusCode(200)
                .body("token", notNullValue());

        return response.jsonPath().getString("token");
    }

    @Test
    public void testPostBookingApi() {
        // Step 9: Get the request body from the input file column
        // For simplicity, we'll use a hardcoded JSON string or read from a resource file.
        // In a real scenario, "input file column" might imply reading from CSV/Excel
        // or a dedicated JSON file for test data.
        String requestBody = "";
        try {
            // Assuming your JSON payload is in a file named "createBookingPayload.json"
            // located in src/test/resources.
            // You might need to adjust the path based on your project structure.
            File jsonFile = new File("src/test/resources/createBookingPayload.json");
            requestBody = new String(Files.readAllBytes(Paths.get(jsonFile.getAbsolutePath())));
            System.out.println("Request Body read from file:\n" + requestBody);
        } catch (IOException e) {
            System.err.println("Error reading JSON payload from file: " + e.getMessage());
            // Fallback to a hardcoded string if file reading fails or file is not present
            requestBody = "{\n" +
                          "    \"firstname\" : \"Jim\",\n" +
                          "    \"lastname\" : \"Brown\",\n" +
                          "    \"totalprice\" : 111,\n" +
                          "    \"depositpaid\" : true,\n" +
                          "    \"bookingdates\" : {\n" +
                          "        \"checkin\" : \"2024-01-01\",\n" +
                          "        \"checkout\" : \"2024-01-05\"\n" +
                          "    },\n" +
                          "    \"additionalneeds\" : \"Breakfast\"\n" +
                          "}";
            System.out.println("Using hardcoded request body as a fallback.");
        }


        // Step 6: Give the method as POST
        // Step 7: Get the token from the above test case and utilize as bearer token
        // Step 8: Give the URL as https://restful-booker.herokuapp.com/booking
        // Step 10: Click on Send button and verify the response
        Response response = given()
                                .contentType(ContentType.JSON)
                                .header("Accept", ContentType.JSON) // Good practice to specify Accept header
                                .header("Authorization", "Bearer " + authToken) // Use the obtained token
                                .body(requestBody)
                            .when()
                                .post("/booking");

        // Print response for debugging
        System.out.println("POST /booking Response Body: " + response.getBody().asString());
        System.out.println("POST /booking Status Code: " + response.getStatusCode());
        System.out.println("POST /booking Headers: " + response.getHeaders());

        // Verify the response
        response.then().assertThat()
                .statusCode(200) // Expecting HTTP 200 OK for successful creation
                .body("bookingid", notNullValue()) // Verify bookingid is present and not null
                .body("booking.firstname", equalTo("Jim")) // Verify data returned in the 'booking' object
                .body("booking.lastname", equalTo("Brown"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2024-01-01"))
                .body("booking.bookingdates.checkout", equalTo("2024-01-05"))
                .body("booking.additionalneeds", equalTo("Breakfast"));
    }
}