package pages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	public static List<String> windowHandles;
	public BasePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
	}
	public String getPageTitle() {
		return driver.getTitle();
	}
	public Set<String> windowHandles() {
		return driver.getWindowHandles();
	}
	public void switchPage(String s) {
		driver.switchTo().window(s);
        driver.manage().window().maximize();
	}
	public void closeWin() {
		driver.close();
	}
}
