package com.example.grid.tests;

import com.example.grid.base.Base;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestB extends Base {
    @Test(description = "Verify DuckDuckGo homepage title")
    public void testB_DuckDuckGoTitle() throws InterruptedException {
        // Navigate to DuckDuckGo
        getDriver().get("https://duckduckgo.com/");
        String title = getDriver().getTitle();
        // Print current test details
        System.out.println("TestClassB - DuckDuckGo Title: '" + title + "' on thread: " + Thread.currentThread().getId() + " with browser: " + ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName());
        // Assert that the title contains "DuckDuckGo"
        Assert.assertTrue(title.contains("DuckDuckGo"), "DuckDuckGo title does not contain 'DuckDuckGo'");
        Thread.sleep(1800); // Simulate some user interaction time
    }

    @Test(description = "Verify Wikipedia homepage title")
    public void testB_WikipediaTitle() throws InterruptedException {
        // Navigate to Wikipedia
        getDriver().get("https://www.wikipedia.org/");
        String title = getDriver().getTitle();
        // Print current test details
        System.out.println("TestClassB - Wikipedia Title: '" + title + "' on thread: " + Thread.currentThread().getId() + " with browser: " + ((RemoteWebDriver) getDriver()).getCapabilities().getBrowserName());
        // Assert that the title contains "Wikipedia"
        Assert.assertTrue(title.contains("Wikipedia"), "Wikipedia title does not contain 'Wikipedia'");
        Thread.sleep(2200); // Simulate some user interaction time
    }
}
