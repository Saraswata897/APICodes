package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchShipResultPage extends BasePage{

	public SearchShipResultPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(partialLinkText = "Rhapsody of the Seas")
	WebElement resElement;
	public void clickRhapsody() {
		wait.until(ExpectedConditions.visibilityOf(resElement)).click();
	}
}
