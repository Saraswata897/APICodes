package com.example.bitcoinapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class BitcoinRateApiTestTwo {

    private static final String BASE_URI = "https://api.coindesk.com";
    private static final String CURRENT_PRICE_ENDPOINT = "/v1/bpi/currentprice.json";
    // A deliberately incorrect endpoint to trigger a 404 for failure scenario
    private static final String NON_EXISTENT_ENDPOINT = "/v1/bpi/nonexistentprice.json";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
        // Optionally, add a default content type if most requests will be JSON
        // RestAssured.requestSpecification = RestAssured.given().contentType(ContentType.JSON);
    }

    @Test(description = "Verify successful status code 200 for Bitcoin rate against USD and Non-USD currencies")
    public void verifyBitcoinRateSuccessStatusCode200() {
        // Send GET request to the current price endpoint
        Response response = RestAssured.given()
                .when()
                .get(CURRENT_PRICE_ENDPOINT);

        // Print response for debugging (optional, but good practice)
        System.out.println("--- Test: verifyBitcoinRateSuccessStatusCode200 ---");
        System.out.println("Response Body for 200: " + response.getBody().asString());
        System.out.println("Status Code for 200: " + response.getStatusCode());

        // Verify Status Code is 200 using TestNG assertion
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());

        // Optional: Further assertions to verify content (e.g., check for USD and other currencies)
        // The currentprice.json endpoint usually returns USD, GBP, and EUR by default.
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("USD"), "Response body should contain USD currency data");
        Assert.assertTrue(responseBody.contains("GBP"), "Response body should contain GBP currency data");
        Assert.assertTrue(responseBody.contains("EUR"), "Response body should contain EUR currency data");
        System.out.println("--- End Test: verifyBitcoinRateSuccessStatusCode200 ---\n");
    }

    @Test(description = "Verify failure status code 404 for a non-existent Bitcoin rate endpoint")
    public void verifyBitcoinRateFailureStatusCode404() {
        // Send GET request to a non-existent endpoint to trigger a 404
        Response response = RestAssured.given()
                .when()
                .get(NON_EXISTENT_ENDPOINT);

        // Print response for debugging (optional)
        System.out.println("--- Test: verifyBitcoinRateFailureStatusCode404 ---");
        System.out.println("Response Body for 404: " + response.getBody().asString());
        System.out.println("Status Code for 404: " + response.getStatusCode());

        // Verify Status Code is 404 using TestNG assertion
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 but got " + response.getStatusCode());

        // Optional: Verify that the response body indicates a "Not Found" error
        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("404"), "Response body should contain '404' indication");
        Assert.assertTrue(responseBody.toLowerCase().contains("not found"), "Response body should contain 'not found' message");
        System.out.println("--- End Test: verifyBitcoinRateFailureStatusCode404 ---\n");
    }

    @Test(description = "Print Bitcoin rate details for USD currency")
    public void printBitcoinRateUSD() {
        // Send GET request
        Response response = RestAssured.given()
                .when()
                .get(CURRENT_PRICE_ENDPOINT);

        // Assert successful response first
        Assert.assertEquals(response.getStatusCode(), 200, "API did not return 200 OK for USD rate");

        // Parse JSON response using JsonPath
        JsonPath jsonPathEvaluator = response.jsonPath();

        System.out.println("--- Bitcoin Rate Details for USD ---");
        // Print 'code' for USD
        String usdCode = jsonPathEvaluator.getString("bpi.USD.code");
        System.out.println("Code: " + usdCode);

        // Print 'symbol' for USD
        String usdSymbol = jsonPathEvaluator.getString("bpi.USD.symbol");
        // The symbol comes with HTML entity for dollar, so we'll replace it for cleaner output
        System.out.println("Symbol: " + usdSymbol.replace("&#36;", "$"));

        // Print 'rate' for USD
        String usdRate = jsonPathEvaluator.getString("bpi.USD.rate");
        System.out.println("Rate: " + usdRate);

        // Print 'description' for USD
        String usdDescription = jsonPathEvaluator.getString("bpi.USD.description");
        System.out.println("Description: " + usdDescription);

        // Print 'USD rate' (which is the same as 'rate' here, for clarity as per prompt)
        System.out.println("USD Rate (string): " + usdRate);

        // Print 'rate_float' for USD
        Float usdRateFloat = jsonPathEvaluator.getFloat("bpi.USD.rate_float");
        System.out.println("Rate Float: " + usdRateFloat);
        System.out.println("----------------------------------\n");
    }

    @Test(description = "Print Bitcoin rate details for GBP currency")
    public void printBitcoinRateGBP() {
        // Send GET request
        Response response = RestAssured.given()
                .when()
                .get(CURRENT_PRICE_ENDPOINT);

        // Assert successful response first
        Assert.assertEquals(response.getStatusCode(), 200, "API did not return 200 OK for GBP rate");

        // Parse JSON response using JsonPath
        JsonPath jsonPathEvaluator = response.jsonPath();

        System.out.println("--- Bitcoin Rate Details for GBP ---");
        // Print 'code' for GBP
        String gbpCode = jsonPathEvaluator.getString("bpi.GBP.code");
        System.out.println("Code: " + gbpCode);

        // Print 'symbol' for GBP
        String gbpSymbol = jsonPathEvaluator.getString("bpi.GBP.symbol");
        // The symbol comes with HTML entity for pound, so we'll replace it for cleaner output
        System.out.println("Symbol: " + gbpSymbol.replace("&#163;", "£"));

        // Print 'rate' for GBP
        String gbpRate = jsonPathEvaluator.getString("bpi.GBP.rate");
        System.out.println("Rate: " + gbpRate);

        // Print 'description' for GBP
        String gbpDescription = jsonPathEvaluator.getString("bpi.GBP.description");
        System.out.println("Description: " + gbpDescription);

        // Print 'GBP rate' (which is the same as 'rate' here)
        System.out.println("GBP Rate (string): " + gbpRate);

        // Print 'rate_float' for GBP
        Float gbpRateFloat = jsonPathEvaluator.getFloat("bpi.GBP.rate_float");
        System.out.println("Rate Float: " + gbpRateFloat);
        System.out.println("----------------------------------\n");
    }

    @Test(description = "Print Bitcoin rate details for EUR currency")
    public void printBitcoinRateEUR() {
        // Send GET request
        Response response = RestAssured.given()
                .when()
                .get(CURRENT_PRICE_ENDPOINT);

        // Assert successful response first
        Assert.assertEquals(response.getStatusCode(), 200, "API did not return 200 OK for EUR rate");

        // Parse JSON response using JsonPath
        JsonPath jsonPathEvaluator = response.jsonPath();

        System.out.println("--- Bitcoin Rate Details for EUR ---");
        // Print 'code' for EUR
        String eurCode = jsonPathEvaluator.getString("bpi.EUR.code");
        System.out.println("Code: " + eurCode);

        // Print 'symbol' for EUR
        String eurSymbol = jsonPathEvaluator.getString("bpi.EUR.symbol");
        // The symbol comes with HTML entity for euro, so we'll replace it for cleaner output
        System.out.println("Symbol: " + eurSymbol.replace("&#8364;", "€"));

        // Print 'rate' for EUR
        String eurRate = jsonPathEvaluator.getString("bpi.EUR.rate");
        System.out.println("Rate: " + eurRate);

        // Print 'description' for EUR
        String eurDescription = jsonPathEvaluator.getString("bpi.EUR.description");
        System.out.println("Description: " + eurDescription);

        // Print 'EUR rate' (which is the same as 'rate' here)
        System.out.println("EUR Rate (string): " + eurRate);

        // Print 'rate_float' for EUR
        Float eurRateFloat = jsonPathEvaluator.getFloat("bpi.EUR.rate_float");
        System.out.println("Rate Float: " + eurRateFloat);
        System.out.println("----------------------------------\n");
    }
}
