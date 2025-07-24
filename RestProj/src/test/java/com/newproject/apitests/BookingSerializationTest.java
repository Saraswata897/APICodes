package com.newproject.apitests; // Using your existing API tests package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

import com.fasterxml.jackson.databind.ObjectMapper; // Import ObjectMapper
import com.newproject.pojos.Booking; // Import your Booking POJO
import com.newproject.pojos.BookingDates; // Import your BookingDates POJO

public class BookingSerializationTest {

    @Test
    public void createBookingUsingSerialization() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        System.out.println("\n--- Creating Booking using POJO Serialization ---");

        // Step 1: Create a Java class for serialization and pass the values from serialize POJO class
        // Create an instance of the nested BookingDates POJO
        BookingDates bookingDates = new BookingDates("2018-01-01", "2019-01-01");

        // Create an instance of the main Booking POJO, populating it with data
        Booking bookingPayload = new Booking(
            "Jim",
            "Brown",
            111,
            true,
            bookingDates,
            "Breakfast"
        );

        // Step 4: Utilize the Object Mapper interface and utilize the writeValueAsString method to create the request body
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBodyJson = "";
        try {
            requestBodyJson = objectMapper.writeValueAsString(bookingPayload);
            System.out.println("Request Body (serialized from POJO):\n" + requestBodyJson);
        } catch (Exception e) {
            System.err.println("Error serializing POJO to JSON: " + e.getMessage());
            Assert.fail("Failed to serialize booking POJO: " + e.getMessage());
        }

        // Define the base URI for the API
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        // Step 2: Get the response body for the API using POST method
        // Perform the POST request with the serialized JSON body
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set Content-Type header
                .body(requestBodyJson) // Attach the serialized request body
                .log().all() // Log all request details for debugging
                .post("/booking"); // Specify the endpoint

        // Step 5: Execute the java class and print the response in the console
        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        // Basic validation: Verify the response status code
        response.then().assertThat().statusCode(200);
        System.out.println("\nStatus code verification successful!");

        // Optional: Further validate response body to ensure booking was created as expected
        response.then().assertThat().body("bookingid", org.hamcrest.Matchers.notNullValue());
        response.then().assertThat().body("booking.firstname", org.hamcrest.Matchers.equalTo("Jim"));
        System.out.println("Validated booking creation in response body.");

        System.out.println("\nBooking creation using POJO serialization successful!");
    }
}
