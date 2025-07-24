package selenium;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.*;

public class FirstCase extends BaseClass {
    

    @Test(priority = 1)
    public void enterURL() throws InterruptedException {
        // Step 1: Launch the browser and navigate to https://www.rediff.com
        Assert.assertTrue(driver.getTitle().contains("Rediff"), "Rediff homepage not loaded");
        Thread.sleep(1000);
    }

    @Test(priority = 2)
    public void clickCreateAccountLink() {
        // Step 2: Click on "Create Rediffmail Account" link
        driver.findElement(By.xpath("//a[@title='Create Rediffmail Account']")).click();
    }

    @Test(priority = 3)
    public void validateCreateAccountPage() {
        // Step 3: Validate “Create Rediffmail account” webpage is opened
        boolean isDisplayed = driver.findElement(By.xpath("//form//div//h2")).isDisplayed();
        Assert.assertTrue(isDisplayed, "Create Rediffmail account page not displayed");
    }

    @Test(priority = 4)
    public void countAndPrintAllLinks() {
        // Step 4: Find and print the total number of links on the “Create Rediffmail account” page
        List<WebElement> links = driver.findElements(By.tagName("a"));
        System.out.println("Total number of links: " + links.size());
        for (int i = 0; i < links.size(); i++) {
            String href = links.get(i).getAttribute("href");
            System.out.println("Link " + (i + 1) + ": " + href);
        }    
        Assert.assertTrue(links.size() > 0, "No links found on the page");
    }

    @Test(priority = 5)
    public void clickTermsAndConditionsLink() throws InterruptedException {
        // Step 5: Click on "terms and conditions" link
        WebElement termsLink = driver.findElement(By.xpath("//a[normalize-space()='terms and conditions']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", termsLink);
        Thread.sleep(1000);
        termsLink.click();
        Thread.sleep(2000);
    }

    @Test(priority = 6)
    public void validateChildWindowOpened() {
        // Step 6: Validate child window “Terms and Conditions” is opened
        Set<String> handles = driver.getWindowHandles();
        windowHandles = new ArrayList<String>(handles);
        Assert.assertTrue(windowHandles.size() > 1, "Child window not opened");
    }

    @Test(priority = 7)
    public void switchToChildWindow() throws InterruptedException {
        // Step 7: Switch to the child window
        driver.switchTo().window(windowHandles.get(1));
        driver.manage().window().maximize();
        Thread.sleep(2000);
    }

    @Test(priority = 8)
    public void getChildWindowTitle() {
        // Step 8: Get and validate the title of the child window
        String childTitle = driver.getTitle();
        System.out.println("Child Window Title: " + childTitle);
        Assert.assertEquals(childTitle, "Rediffmail: Terms and Conditions", "Child window title mismatch");
    }

    @Test(priority = 9)
    public void closeChildWindow() {
        // Step 9: Close the child window
        driver.close();
    }

    @Test(priority = 10)
    public void switchToParentWindow() throws InterruptedException {
        // Step 10: Switch back to the parent window (“Create Rediffmail account” webpage)
        driver.switchTo().window(windowHandles.get(0));
        Assert.assertTrue(driver.getTitle().contains("Rediffmail"), "Failed to return to parent window");
        Thread.sleep(2000);
    }

    
    
}
