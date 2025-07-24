package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage{
	public HomePage(WebDriver driver) {
		super(driver);
	}
	@FindBy(xpath="//a[@title='Create Rediffmail Account']")
	WebElement createAccount;
	
	public void createAccountClick() {
		wait.until(ExpectedConditions.elementToBeClickable(createAccount)).click();
	}
	

}
