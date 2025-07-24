package com.newproject.runners; // New package for test runners

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(
    features = "src/test/resources/features", // Path to your feature files
    glue = "com.newproject.stepdefs",        // Path to your step definition classes
    plugin = {"pretty", "html:target/cucumber-reports.html", "json:target/cucumber.json"}, // Reporting plugins
    monochrome = true,                       // Makes console output more readable
    tags = "" // Tags will be set dynamically via system property
)
public class TestRunner extends AbstractTestNGCucumberTests {

    // This method will be executed before any tests in the suite.
    // It reads the 'cucumber.filter.tags' parameter from testng.xml
    // and sets it as a system property, which Cucumber will then pick up
    // to filter scenarios.
    @BeforeSuite(alwaysRun = true)
    @Parameters("cucumber.filter.tags")
    public void setCucumberTags(@Optional("") String tags) {
        if (tags != null && !tags.isEmpty()) {
            System.setProperty("cucumber.filter.tags", tags);
            System.out.println("Setting Cucumber tags system property for execution: " + tags);
        } else {
            // If no tags are provided, clear the property so all features run by default
            System.clearProperty("cucumber.filter.tags");
            System.out.println("No specific Cucumber tags provided. All features will run by default.");
        }
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        // This method provides scenarios to TestNG.
        // The filtering based on tags is handled by the system property set in @BeforeSuite.
        return super.scenarios();
    }
}
