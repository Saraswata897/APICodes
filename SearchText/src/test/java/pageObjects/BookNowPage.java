package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BookNowPage extends BasePage {

	public BookNowPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(linkText = "BOOK NOW")
	WebElement bookNowBtnElement;
	public void clickBookNow() {
		wait.until(ExpectedConditions.elementToBeClickable(bookNowBtnElement)).click();
	}
}
