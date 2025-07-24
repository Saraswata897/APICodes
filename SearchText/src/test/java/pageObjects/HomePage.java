package pageObjects;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{
	Actions actions;
	public HomePage(WebDriver driver) {
		super(driver);
		actions=new Actions(driver);
	}
	@FindBy(xpath="//*[@class='header__buttonIcon header__buttonIcon__search']")
	WebElement searchButton;
	@FindBy(className = "headerSearchBox__input")
	WebElement searchBox;
	public void handleLoginPopup() {
	    try {
	        Thread.sleep(2000);
	 
	        // First try with explicit waiting for the email capture popup
	        try {
	            // Use the exact class names from the image
	            By emailCaptureLocator = By.cssSelector("div.email-capture.email-capture--open");
	            wait.until(ExpectedConditions.presenceOfElementLocated(emailCaptureLocator));
	            // Look for the close button in the wrapper
	            By closeButtonLocator = By.cssSelector("svg.email-capture__close");
	            WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(closeButtonLocator));
	            // Try multiple ways to click the close button
	            try {
	                // First try moving to element with actions
	                Actions actions = new Actions(driver);
	                actions.moveToElement(closeButton).click().perform();
	            } catch (Exception e1) {
	                try {
	                    // Try JavaScript click as backup
	                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeButton);
	                } catch (Exception e2) {
	                    // Last resort - try direct click
	                    closeButton.click();
	                }
	            }
	            // Wait for popup to disappear
	            wait.until(ExpectedConditions.invisibilityOfElementLocated(emailCaptureLocator));
	        } catch (Exception e) {
	            System.out.println("Could not find email capture popup: " + e.getMessage());
	        }
	 
	        // Handle cookie consent if present
//	        try {
//	            By cookieAcceptLocator = By.cssSelector("[aria-label*='Accept'], [class*='cookie-accept']");
//	            WebElement cookieAccept = wait.until(ExpectedConditions.elementToBeClickable(cookieAcceptLocator));
//	            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cookieAccept);
//	        } catch (Exception e) {
//	            System.out.println("No cookie consent found");
//	        }
	 
	    } catch (Exception e) {
	        System.out.println("Error handling popups: " + e.getMessage());
	    }
	}
	public void clickSearchButton() {
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
	}
	public void setSearch(String term) {
		wait.until(ExpectedConditions.visibilityOf(searchBox)).sendKeys(term);
		actions.sendKeys(Keys.ENTER).perform();
		
	}
}
