package com.example.grid.tests;

import com.example.grid.base.Base;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestA extends Base {
    @Test(description = "Verify Google homepage title")
    public void testA_GoogleTitle() throws InterruptedException {
        // Navigate to Google
        getDriver().get("https://www.google.com");
        String title = getDriver().getTitle();
        // Print current test details to console for debugging/tracking parallel execution
        System.out.println("TestClassA - Google Title: '" + title + "' on thread: " + Thread.currentThread().getId() + " with browser: " + ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName());
        // Assert that the title contains "Google"
        Assert.assertTrue(title.contains("Google"), "Google title does not contain 'Google'");
        Thread.sleep(1500); // Simulate some user interaction time (e.g., waiting for elements, scrolling)
    }

    @Test(description = "Verify Bing homepage title")
    public void testA_BingTitle() throws InterruptedException {
        // Navigate to Bing
        getDriver().get("https://www.bing.com");
        String title = getDriver().getTitle();
        // Print current test details
        System.out.println("TestClassA - Bing Title: '" + title + "' on thread: " + Thread.currentThread().getId() + " with browser: " + ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName());
        // Assert that the title contains "Bing"
        Assert.assertTrue(title.contains("Bing"), "Bing title does not contain 'Bing'");
        Thread.sleep(2000); // Simulate some user interaction time
    }
}
