package com.example.apitests; // Assuming this is your package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.http.Cookies; // Import Cookies class
import io.restassured.path.json.JsonPath; // Import JsonPath for JSON body extraction
import org.testng.annotations.Test;
import org.testng.Assert;
import static org.hamcrest.Matchers.*; // Import Hamcrest Matchers for assertions

public class ValidateCookies { // New class for validating cookies

    @Test
    public void validateResponseCookies() {
        // This line is included as per your request to relax HTTPS validation.
        RestAssured.useRelaxedHTTPSValidation();

        // Define the base URI for the API
        RestAssured.baseURI = "https://postman-echo.com";

        // Perform the GET request with a specific cookie
        Response response = RestAssured.given()
                .cookie("skill", "Communication") // Set the cookie for the request
                .log().all() // Log all request details for debugging
                .get("/cookies/set"); // Specify the endpoint

        // Print the response details for verification
        System.out.println("\nResponse Status Code: " + response.getStatusCode());
        System.out.println("Response Body:\n" + response.getBody().asString());

        System.out.println("\n--- Validating Cookies in Response ---");

        // Validate status code first
        response.then().assertThat().statusCode(200);
        System.out.println("Validated status code is 200.");

        // Extract all cookies from the response and print them
        // Note: getDetailedCookies() retrieves cookies from the Set-Cookie header.
        // postman-echo.com/cookies/set does not set the 'skill' cookie in Set-Cookie,
        // but rather includes it in the JSON response body.
        Cookies allCookies = response.getDetailedCookies();
        System.out.println("\nAll Response Cookies (from Set-Cookie header):");
        if (allCookies.size() > 0) {
            allCookies.forEach(cookie -> System.out.println("Name: " + cookie.getName() + ", Value: " + cookie.getValue() + ", Path: " + cookie.getPath() + ", Domain: " + cookie.getDomain()));
        } else {
            System.out.println("No cookies found in Set-Cookie header.");
        }


        // --- IMPORTANT CHANGE: Extracting cookie value from the JSON response body ---
        // The postman-echo.com/cookies/set API returns the sent cookies in its JSON response body.
        JsonPath jsonPath = response.jsonPath();
        String skillCookieValueFromBody = jsonPath.getString("cookies.skill");
        System.out.println("\nSpecific Cookie 'skill' value (from response body): " + skillCookieValueFromBody);

        // Validate the specific cookie's value from the response body
        Assert.assertEquals(skillCookieValueFromBody, "Communication", "Expected 'skill' cookie value from body to be 'Communication'.");
        System.out.println("Validated 'skill' cookie value from response body is 'Communication'.");

        // Validate the specific cookie exists in the response body
        response.then().assertThat().body("cookies.skill", notNullValue());
        System.out.println("Validated 'skill' cookie exists in the response body.");

        System.out.println("\nCookie validation successful!");
    }
}
