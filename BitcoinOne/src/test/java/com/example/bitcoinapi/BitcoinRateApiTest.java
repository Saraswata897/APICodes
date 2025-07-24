package com.example.bitcoinapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BitcoinRateApiTest {

    private static final String BASE_URI = "https://api.coindesk.com";
    private static final String CURRENT_PRICE_ENDPOINT = "/v1/bpi/currentprice.json";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    @Test(description = "Verify successful status code 200 for Bitcoin rate against USD and Non-USD currencies")
    public void verifyBitcoinRateSuccessStatusCode200() {
        // Send GET request
        Response response = RestAssured.given()
                .when()
                .get(CURRENT_PRICE_ENDPOINT);

        // Print response for debugging (optional)
        System.out.println("Response Body for 200: " + response.getBody().asString());
        System.out.println("Status Code for 200: " + response.getStatusCode());

        // Verify Status Code is 200 using TestNG assertion
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());

        // Optional: Further assertions to verify content (e.g., check for USD and other currencies)
        // This demonstrates checking for the presence of "USD" and "EUR" in the response JSON
        Assert.assertTrue(response.getBody().asString().contains("USD"), "Response body should contain USD currency data");
        Assert.assertTrue(response.getBody().asString().contains("EUR"), "Response body should contain EUR currency data (if available in the currentprice endpoint)");
        // Note: The currentprice.json endpoint usually returns USD, GBP, and EUR by default.
    }
}