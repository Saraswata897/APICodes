package pages;

import java.util.List;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateAccountPage extends BasePage{
	int noOfLinks;
	public CreateAccountPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(xpath="//form//div//h2")
	WebElement pageHeading;
	@FindBy(tagName="a")
	List<WebElement> links;
	@FindBy(xpath="//a[normalize-space()='terms and conditions']")
	WebElement tAndCElement;
	public boolean validateCAP() {
        boolean isDisplayed = pageHeading.isDisplayed();
        return isDisplayed;
    }
	public int countList() {
		noOfLinks=links.size();
		System.out.println("Total number of links: " + noOfLinks);
        for (int i = 0; i < noOfLinks; i++) {
            String href = links.get(i).getAttribute("href");
            System.out.println("Link " + (i + 1) + ": " + href);
        } 
		return noOfLinks;
	}
	public void clickTAndC() {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", tAndCElement);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wait.until(ExpectedConditions.elementToBeClickable(tAndCElement));
		tAndCElement.click();
	}
	public boolean returnToP() {
		switchPage(windowHandles.get(0));
		return getPageTitle().contains("Rediffmail");
	}
	
}
