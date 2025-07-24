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
import java.util.Base64; // For Basic Auth encoding

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PatchBookingApiTest {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";
    private int bookingId; // To store the ID of the created booking
    private String basicAuthHeader; // To store the encoded Basic Auth header

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;

        // 1. Generate Basic Auth Header
        String username = "admin";
        String password = "password123";
        String credentials = username + ":" + password;
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
        System.out.println("Generated Basic Auth Header: " + basicAuthHeader);

        // First, create a booking to get an ID for the PATCH request
        bookingId = createBookingAndGetId();
        System.out.println("Created Booking ID for PATCH: " + bookingId);
    }

    /**
     * Helper method to create a booking and return its ID.
     * This is necessary because PATCH operates on an existing resource.
     */
    private int createBookingAndGetId() {
        String createBookingPayload = "{\n" +
                                      "    \"firstname\" : \"InitialJim\",\n" +
                                      "    \"lastname\" : \"InitialBrown\",\n" +
                                      "    \"totalprice\" : 100,\n" +
                                      "    \"depositpaid\" : true,\n" +
                                      "    \"bookingdates\" : {\n" +
                                      "        \"checkin\" : \"2024-01-01\",\n" +
                                      "        \"checkout\" : \"2024-01-05\"\n" +
                                      "    },\n" +
                                      "    \"additionalneeds\" : \"Dinner\"\n" +
                                      "}";

        Response response = given()
                                .contentType(ContentType.JSON)
                                .header("Accept", ContentType.JSON)
                            .body(createBookingPayload)
                            .when()
                                .post("/booking");

        response.then().assertThat()
                .statusCode(200)
                .body("bookingid", notNullValue());

        return response.jsonPath().getInt("bookingid");
    }

    @Test
    public void testPatchBookingApi() {
        // Step 9: Get the request body from the input file column
        String patchRequestBody = "";
        try {
            File jsonFile = new File(".//src/test/resources/updateBookingPartialPayload.json");
            patchRequestBody = new String(Files.readAllBytes(Paths.get(jsonFile.getAbsolutePath())));
            System.out.println("PATCH Request Body read from file:\n" + patchRequestBody);
        } catch (IOException e) {
            System.err.println("Error reading PATCH JSON payload from file: " + e.getMessage());
            // Fallback to a hardcoded string if file reading fails
            patchRequestBody = "{\n" +
                               "    \"firstname\" : \"FallbackJim\",\n" +
                               "    \"additionalneeds\" : \"Fallback Wifi\"\n" +
                               "}";
            System.out.println("Using hardcoded PATCH request body as a fallback.");
        }

        // Step 6: Give the method as PATCH
        // Step 7: Give Authorization as Basic Auth
        // Step 8: Give the URL as https://restful-booker.herokuapp.com/booking/:id
        // Step 10: Click on Send button and verify the response
        Response response = given()
                                .contentType(ContentType.JSON)
                                .header("Accept", ContentType.JSON)
                                .header("Authorization", basicAuthHeader) // Use the generated Basic Auth header
                                .body(patchRequestBody)
                            .when()
                                .patch("/booking/" + bookingId); // Use the created booking ID in the URL

        // Print response for debugging
        System.out.println("PATCH /booking Response Body: " + response.getBody().asString());
        System.out.println("PATCH /booking Status Code: " + response.getStatusCode());
        System.out.println("PATCH /booking Headers: " + response.getHeaders());

        // Verify the response
        response.then().assertThat()
                .statusCode(200) // Expecting HTTP 200 OK for successful partial update
                .body("firstname", equalTo("UpdatedJim")) // Verify the updated firstname
                .body("lastname", equalTo("InitialBrown")) // Verify lastname is unchanged
                .body("totalprice", equalTo(100)) // Verify totalprice is unchanged
                .body("bookingdates.checkin", equalTo("2024-01-01")) // Verify checkin is unchanged
                .body("additionalneeds", equalTo("Breakfast and Wifi")); // Verify the updated additionalneeds
    }
}