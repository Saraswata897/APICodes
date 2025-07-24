package com.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class AnotherClass {

    // Use ThreadLocal to ensure each thread has its own WebDriver instance
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final String HUB_URL = "http://localhost:4444/";

    @BeforeMethod
    @Parameters({"browser"}) // Get browser parameter from testng.xml
    public void setup(String browser) throws MalformedURLException {
        System.out.println("Starting test on browser: " + browser + " in thread: " + Thread.currentThread().getId());
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            // Add any specific Chrome capabilities you need from your TOML files
            // e.g., chromeOptions.setBrowserVersion("120");
            chromeOptions.setPlatformName("WINDOWS");
            capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
            driver.set(new RemoteWebDriver(new URL(HUB_URL), chromeOptions));
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, firefoxOptions);
            driver.set(new RemoteWebDriver(new URL(HUB_URL), firefoxOptions));
        } else if (browser.equalsIgnoreCase("edge")) {
            EdgeOptions edgeOptions = new EdgeOptions();
            capabilities.setCapability(EdgeOptions.CAPABILITY, edgeOptions);
            driver.set(new RemoteWebDriver(new URL(HUB_URL), edgeOptions));
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        getDriver().manage().window().maximize();
    }

    public WebDriver getDriver() {
        return driver.get();
    }

    @Test
    public void testGoogleSearch() {
        getDriver().get("https://www.amazon.com/");
        System.out.println("Test in Thread " + Thread.currentThread().getId() + ": Title is " + getDriver().getTitle());
        assert getDriver().getTitle().contains("Amazon");
    }

    // Add more @Test methods or create more test classes for more parallel execution

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); // Important for ThreadLocal
        }
        System.out.println("Finished test in thread: " + Thread.currentThread().getId());
    }
}
