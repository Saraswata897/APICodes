package com.example.grid.base;

import com.example.grid.utils.GridConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public abstract class Base {
    // ThreadLocal ensures that each test thread has its own WebDriver instance.
    // This is crucial for parallel test execution to prevent concurrent access issues.
    protected ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Sets up the WebDriver instance before each test method.
     * This method supports two modes:
     * 1. "grid" mode (default): Connects to a Selenium Grid Hub using RemoteWebDriver.
     * 2. "local" mode: Runs tests locally using WebDriverManager to manage browser drivers.
     * The mode is determined by the "run.mode" system property.
     *
     * @param browser The browser type (e.g., "chrome", "edge") passed from testng.xml.
     * @throws MalformedURLException If the Selenium Grid Hub URL is malformed.
     * @throws IllegalArgumentException If an unsupported browser is specified.
     */
    @Parameters({"browser"})
    @BeforeMethod
    public void setup(String browser) throws MalformedURLException {
        System.out.println("Starting test on browser: " + browser + " on thread: " + Thread.currentThread().getId());

        // Get the run mode from system properties. Default to "grid" if not specified.
        String runMode = System.getProperty("run.mode", "grid");

        if ("local".equalsIgnoreCase(runMode)) {
            // --- Local Execution using WebDriverManager ---
            System.out.println("Running tests locally using WebDriverManager for " + browser + " browser.");
            switch (browser.toLowerCase()) {
                case "chrome":
                    // Automatically downloads and sets up the ChromeDriver executable.
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                    break;
                case "edge":
                    // Automatically downloads and sets up the EdgeDriver executable.
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver());
                    break;
                default:
                    throw new IllegalArgumentException("Local browser '" + browser + "' is not supported. Please use 'chrome' or 'edge'.");
            }
        } else {
            // --- Grid Execution using RemoteWebDriver and DesiredCapabilities ---
            System.out.println("Connecting to Selenium Grid Hub at " + GridConfig.HUB_URL + " for " + browser + " browser.");

            // DesiredCapabilities are used to specify the browser and platform requirements
            // for the remote WebDriver session that the Grid will fulfill.
            DesiredCapabilities capabilities = new DesiredCapabilities();

            switch (browser.toLowerCase()) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    // You can add custom Chrome-specific options here.
                    // For example: chromeOptions.addArguments("--incognito");
                    // Attach ChromeOptions to the DesiredCapabilities.
                    capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                    capabilities.setBrowserName("chrome"); // Set the browser name capability.
                    capabilities.setPlatform(Platform.ANY); // Request any available platform on the Grid.
                    break;
                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    // You can add custom Edge-specific options here.
                    // For example: edgeOptions.addArguments("--inprivate");
                    // Attach EdgeOptions to the DesiredCapabilities.
                    capabilities.setCapability(EdgeOptions.CAPABILITY, edgeOptions);
                    capabilities.setBrowserName("MicrosoftEdge"); // Standard capability name for Microsoft Edge.
                    capabilities.setPlatform(Platform.ANY); // Request any available platform on the Grid.
                    break;
                default:
                    throw new IllegalArgumentException("Grid browser '" + browser + "' is not supported. Please use 'chrome' or 'edge'.");
            }

            // Initialize RemoteWebDriver, connecting to the Selenium Grid Hub URL
            // and passing the desired capabilities.
            driver.set(new RemoteWebDriver(new URL(GridConfig.HUB_URL), capabilities));
        }

        // Apply common WebDriver settings for both local and remote drivers.
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Implicit wait for elements.
        driver.get().manage().window().maximize(); // Maximize the browser window.
    }

    /**
     * Provides access to the WebDriver instance for the current thread.
     * Test methods in subclasses will use this to interact with the browser.
     * @return The WebDriver instance.
     */
    public WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Quits the WebDriver instance after each test method and removes it from ThreadLocal.
     * This ensures resources are properly released.
     */
    @AfterMethod
    public void tearDown() {
        if (driver.get() != null) {
            System.out.println("Closing browser on thread: " + Thread.currentThread().getId());
            driver.get().quit(); // Quits the browser session.
            driver.remove(); // Removes the WebDriver instance from ThreadLocal.
        }
    }
}
