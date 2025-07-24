package Test_Files;

import java.io.IOException;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.BookNowPage;
import pageObjects.HomePage;
import pageObjects.ResultsPage;
import pageObjects.SearchShipResultPage;

public class TestRunClass {
	WebDriver driver;
	String browserUsed;
	@BeforeClass
	@Parameters({"browser"})
	public void setUp(String browser) throws InterruptedException {
		browserUsed=browser;
		if (browser.equalsIgnoreCase("chrome")) {
			driver = new ChromeDriver();
		} 
		else if (browser.equalsIgnoreCase("edge")) {
			driver = new EdgeDriver();
		} else {
			throw new IllegalArgumentException("Unsupported browser: " + browser);
		}

		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().deleteAllCookies();
		driver.get("https://www.royalcaribbean.com/?country=IND");
		driver.manage().window().maximize();
		Thread.sleep(3000);
	}
	
	@Test(priority = 0)
	public void searchShip() throws InterruptedException {
		HomePage hp = new HomePage(driver);
		hp.handleLoginPopup();
		hp.clickSearchButton();
		hp.setSearch("Rhapsody of the Seas");
		Thread.sleep(500);
	}
	@Test(priority = 1)
	public void clickResult() throws InterruptedException {
		SearchShipResultPage ssrp = new SearchShipResultPage(driver);
		ssrp.clickRhapsody();
		Thread.sleep(500);
	}
	@Test(priority = 2)
	public void bookNow() {
		BookNowPage bnp = new BookNowPage(driver);
		bnp.clickBookNow();
	}
	@Test(priority = 3)
	public void selectDateFilter() throws InterruptedException{
		ResultsPage rp = new ResultsPage(driver);
		rp.selectDate();
	}
	@Test(priority = 4)
	public void selectDepPortFilter() throws InterruptedException{
		ResultsPage rp = new ResultsPage(driver);
		rp.selectDepPort();
	}
	@Test(priority = 5)
	public void selectDestinationFilter() throws InterruptedException {
		ResultsPage rp = new ResultsPage(driver);
		rp.destinationSelect();
	}
	@Test(priority = 6)
	public void selectLength() throws InterruptedException{
		ResultsPage rp = new ResultsPage(driver);
		rp.cruiseLengthSelect();
	}
	@Test(priority = 7)
	public void resultFilter() throws InterruptedException, IOException{
		ResultsPage rp = new ResultsPage(driver);
		rp.clickResult();
		rp.filterResultDropdown();
		int resCount = rp.countElements();
		System.out.println("Number of results : "+resCount);
		rp.resultOut(browserUsed);
	}
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
}
