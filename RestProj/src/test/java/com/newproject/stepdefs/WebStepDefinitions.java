package com.newproject.stepdefs; // New package for step definitions

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import io.github.bonigarcia.wdm.WebDriverManager; // Import WebDriverManager
import io.cucumber.java.After; // Import After hook for teardown

public class WebStepDefinitions {

    private WebDriver driver;

    @Given("I open the browser and navigate to {string}")
    public void iOpenTheBrowserAndNavigateTo(String url) {
        WebDriverManager.chromedriver().setup(); // Automatically setup ChromeDriver
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // Maximize browser window
        driver.get(url);
        System.out.println("Navigated to: " + url);
    }

    @Then("the page title should be {string}")
    public void thePageTitleShouldBe(String expectedTitle) {
        String actualTitle = driver.getTitle();
        System.out.println("Actual Page Title: " + actualTitle);
        Assert.assertEquals(actualTitle, expectedTitle,
                "Expected page title '" + expectedTitle + "' but got '" + actualTitle + "'");
    }

    @After("@web") // This hook will run after scenarios tagged with @web
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("Browser closed.");
        }
    }
}
