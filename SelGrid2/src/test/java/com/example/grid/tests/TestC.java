package com.example.grid.tests;

import com.example.grid.base.Base;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestC extends Base {
    @Test(description = "Verify Amazon homepage title")
    public void testC_AmazonTitle() throws InterruptedException {
        // Navigate to Amazon India
        getDriver().get("https://www.amazon.in/");
        String title = getDriver().getTitle();
        // Print current test details
        System.out.println("TestClassC - Amazon Title: '" + title + "' on thread: " + Thread.currentThread().getId() + " with browser: " + ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName());
        // Assert that the title contains "Amazon"
        Assert.assertTrue(title.contains("Amazon"), "Amazon title does not contain 'Amazon'");
        Thread.sleep(2500); // Simulate some user interaction time
    }

    @Test(description = "Verify Flipkart homepage title")
    public void testC_FlipkartTitle() throws InterruptedException {
        // Navigate to Flipkart
        getDriver().get("https://www.flipkart.com/");
        String title = getDriver().getTitle();
        // Print current test details
        System.out.println("TestClassC - Flipkart Title: '" + title + "' on thread: " + Thread.currentThread().getId() + " with browser: " + ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName());
        // Assert that the title contains "Flipkart"
        Assert.assertTrue(title.contains("Flipkart"), "Flipkart title does not contain 'Flipkart'");
        Thread.sleep(1700); // Simulate some user interaction time
    }
}
