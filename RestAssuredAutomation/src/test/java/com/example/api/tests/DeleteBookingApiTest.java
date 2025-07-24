package com.example.api.tests; // Adjust package as needed

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Base64; // For Basic Auth encoding

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class DeleteBookingApiTest {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";
    private int bookingIdToDelete; // To store the ID of the booking to be deleted
    private String basicAuthHeader; // To store the encoded Basic Auth header

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URL;

        // Step 7: Give Authorization as Basic Auth
        String username = "admin";
        String password = "password123";
        String credentials = username + ":" + password;
        basicAuthHeader = "Basic " + Base64.getEncoder().encodeToString(credentials.getBytes());
        System.out.println("Generated Basic Auth Header: " + basicAuthHeader);

        // First, create a booking to get an ID that we can then delete
        bookingIdToDelete = createBookingAndGetId();
        System.out.println("Created Booking ID for DELETE: " + bookingIdToDelete);
    }

    /**
     * Helper method to create a booking and return its ID.
     * This is necessary because DELETE operates on an existing resource.
     */
    private int createBookingAndGetId() {
        String createBookingPayload = "{\n" +
                                      "    \"firstname\" : \"DeleteMe\",\n" +
                                      "    \"lastname\" : \"Booking\",\n" +
                                      "    \"totalprice\" : 999,\n" +
                                      "    \"depositpaid\" : false,\n" +
                                      "    \"bookingdates\" : {\n" +
                                      "        \"checkin\" : \"2025-07-01\",\n" + // Updated to current year
                                      "        \"checkout\" : \"2025-07-05\"\n" +
                                      "    },\n" +
                                      "    \"additionalneeds\" : \"Late Checkout\"\n" +
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
    public void testDeleteBookingApi() {
        // Step 9: "Get the request body from the input file column"
        // For DELETE requests to restful-booker, there is typically NO request body.
        // If an API expects a body for DELETE, you would read it like in POST/PATCH examples.
        // For restful-booker, a body on DELETE is ignored or might cause a bad request.
        // So, we will not send a request body for this DELETE.

        // Step 6: Give the method as DELETE
        // Step 7: Give Authorization as Basic Auth
        // Step 8: Give the URL as https://restful-booker.herokuapp.com/booking/:id
        // Step 10: Click on Send button and verify the response
        Response deleteResponse = given()
                                        .header("Authorization", basicAuthHeader) // Use the generated Basic Auth header
                                    .when()
                                        .delete("/booking/" + bookingIdToDelete); // Use the created booking ID in the URL

        // Print response for debugging
        System.out.println("DELETE /booking Response Body: " + deleteResponse.getBody().asString());
        System.out.println("DELETE /booking Status Code: " + deleteResponse.getStatusCode());
        System.out.println("DELETE /booking Headers: " + deleteResponse.getHeaders());

        // Verify the deletion response
        // restful-booker returns 201 Created for a successful DELETE operation.
        deleteResponse.then().assertThat()
                      .statusCode(201); // Expected status code for successful DELETE on restful-booker

        // OPTIONAL: Verify that the booking no longer exists by attempting a GET request
        System.out.println("\nVerifying deletion by attempting GET on the same ID...");
        Response getAfterDeleteResponse = given()
                                            .when()
                                                .get("/booking/" + bookingIdToDelete);

        System.out.println("GET after DELETE Status Code: " + getAfterDeleteResponse.getStatusCode());
        System.out.println("GET after DELETE Response Body: " + getAfterDeleteResponse.getBody().asString());

        getAfterDeleteResponse.then().assertThat()
                              .statusCode(404) // Expecting 404 Not Found after deletion
                              .body(equalTo("Not Found")); // Verify the response body for 404
    }
}