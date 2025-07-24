package com.newproject.apitests; // Using your existing API tests package

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.Assert;

public class ParallelMethodsTest { // New class for parallel method execution

    @Test
    public void getGoogleStatusCode() {
        RestAssured.useRelaxedHTTPSValidation();
        long startTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - Executing getGoogleStatusCode()");
        Response response = RestAssured.given().log().all().get("https://www.google.com");
        Assert.assertEquals(response.getStatusCode(), 200);
        long endTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - getGoogleStatusCode() finished in " + (endTime - startTime) + "ms");
    }

    @Test
    public void getYahooStatusCode() {
        RestAssured.useRelaxedHTTPSValidation();
        long startTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - Executing getYahooStatusCode()");
        Response response = RestAssured.given().log().all().get("https://www.yahoo.com");
        Assert.assertEquals(response.getStatusCode(), 200);
        long endTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - getYahooStatusCode() finished in " + (endTime - startTime) + "ms");
    }

    @Test
    public void getBingStatusCode() {
        RestAssured.useRelaxedHTTPSValidation();
        long startTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - Executing getBingStatusCode()");
        Response response = RestAssured.given().log().all().get("https://www.bing.com");
        Assert.assertEquals(response.getStatusCode(), 200);
        long endTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - getBingStatusCode() finished in " + (endTime - startTime) + "ms");
    }

    @Test
    public void getWikipediaStatusCode() {
        RestAssured.useRelaxedHTTPSValidation();
        long startTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - Executing getWikipediaStatusCode()");
        Response response = RestAssured.given().log().all().get("https://www.wikipedia.org");
        Assert.assertEquals(response.getStatusCode(), 200);
        long endTime = System.currentTimeMillis();
        System.out.println("Thread ID: " + Thread.currentThread().getId() + " - getWikipediaStatusCode() finished in " + (endTime - startTime) + "ms");
    }
}
