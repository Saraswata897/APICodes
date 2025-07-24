package pages;

import java.util.ArrayList;
import java.util.Set;

import org.openqa.selenium.WebDriver;

public class TermsPage extends BasePage{

	public TermsPage(WebDriver driver) {
		super(driver);
	}
	public boolean validateCWinOpen() {
		Set<String> handles=windowHandles();
		windowHandles=new ArrayList<String>(handles);
		return windowHandles.size()>1;
	}
	public void switchWindow() {
		switchPage(windowHandles.get(1));
	}
	public boolean validateCWinTl() {
		 String childTitle = getPageTitle();
	     System.out.println("Child Window Title: " + childTitle);
	     return childTitle.equalsIgnoreCase("Rediffmail: Terms and Conditions");
	}
	
}
