package com.newproject.stepdefs; // New package for step definitions

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class ApiStepDefinitions {

    private RequestSpecification request;
    private Response response;
    private String baseUri;
    private String endpoint;
    private Map<String, String> queryParamsMap;

    @Given("the API base URI is {string}")
    public void theApiBaseUriIs(String uri) {
        this.baseUri = uri;
        RestAssured.useRelaxedHTTPSValidation(); // Relax HTTPS validation
        request = RestAssured.given();
        queryParamsMap = new HashMap<>();
    }

    @Given("the API endpoint is {string}")
    public void theApiEndpointIs(String endpoint) {
        this.endpoint = endpoint;
    }

    @Given("the query parameters are {string}")
    public void theQueryParamsAre(String queryParams) {
        // Parse query parameters from string "key1=value1&key2=value2"
        String[] pairs = queryParams.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                queryParamsMap.put(keyValue[0], keyValue[1]);
            }
        }
        request.queryParams(queryParamsMap);
    }

    @When("I send a GET request")
    public void iSendAGetRequest() {
        response = request.baseUri(baseUri).get(endpoint);
        System.out.println("API Response Status Code: " + response.getStatusCode());
        System.out.println("API Response Body: " + response.getBody().asString());
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode,
                "Expected status code " + expectedStatusCode + " but got " + response.getStatusCode());
    }
}
