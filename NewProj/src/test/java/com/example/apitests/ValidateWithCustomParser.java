package com.example.apitests; // This class remains in its original package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import com.example.parsers.BookingResponsePojo;

import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

 // Import from the parsers package

public class ValidateWithCustomParser { // Class for validating with custom parsers (POJO mapping)

    @Test
    public void validateResponseWithPojoMapping() { // Renamed method for clarity
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        // No need to register a custom parser for standard JSON to POJO mapping.
        // Rest Assured uses Jackson (from your pom.xml) by default for .as(Class.class).

        String requestBody = "";
        try {
            // Read the request body from the classpath
            // The file should be placed in src/test/resources/booking_input.json
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("booking_input.json");
            if (inputStream == null) {
                throw new IOException("Resource 'booking_input.json' not found on classpath.");
            }
            requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("Request Body loaded from file:\n" + requestBody);
        } catch (IOException e) {
            System.err.println("Error reading booking_input.json: " + e.getMessage());
            Assert.fail("Failed to read request body from file: " + e.getMessage());
        }

        // Define the base URI for the API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Perform the POST request and get the response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBody) // Attach the request body
                .log().all() // Log all request details for debugging
                .post("/booking"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        System.out.println("\n--- Validating Response using POJO Mapping ---");

        // Validate status code first
        response.then().assertThat().statusCode(200);
        System.out.println("Validated status code is 200.");

        // Use Rest Assured's built-in POJO mapping to deserialize the response
        BookingResponsePojo bookingResponse = response.as(BookingResponsePojo.class);

        // Now, perform assertions directly on the POJO fields
        Assert.assertNotNull(bookingResponse, "Booking response POJO should not be null.");
        Assert.assertTrue(bookingResponse.getBookingid() > 0, "Booking ID should be greater than 0.");
        Assert.assertNotNull(bookingResponse.getBooking(), "Booking details object should not be null.");

        // Validate fields from the nested 'booking' object
        Assert.assertEquals(bookingResponse.getBooking().getFirstname(), "Jim", "First name should be Jim.");
        Assert.assertEquals(bookingResponse.getBooking().getLastname(), "Brown", "Last name should be Brown.");
        Assert.assertEquals(bookingResponse.getBooking().getTotalprice(), 111, "Total price should be 111.");
        Assert.assertTrue(bookingResponse.getBooking().isDepositpaid(), "Deposit paid should be true.");
        Assert.assertNotNull(bookingResponse.getBooking().getBookingdates(), "Booking dates object should not be null.");
        Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckin(), "2018-01-01", "Check-in date should be 2018-01-01.");
        Assert.assertEquals(bookingResponse.getBooking().getBookingdates().getCheckout(), "2019-01-01", "Check-out date should be 2019-01-01.");
        Assert.assertEquals(bookingResponse.getBooking().getAdditionalneeds(), "Breakfast", "Additional needs should be Breakfast.");

        System.out.println("\nPOJO mapping and assertions successful!");
    }
}
