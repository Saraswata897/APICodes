package Test_Files;
 
import java.time.Duration;
import java.util.List;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
 
public class tt1 {
	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		driver.manage().window().maximize();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions action = new Actions(driver);
		driver.get("https://www.royalcaribbean.com/?country=IND");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement searchIcon = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"header__buttonIcon header__buttonIcon__search\"]")));
		searchIcon.click();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("headerSearchBox__input")));
		driver.findElement(By.className("headerSearchBox__input")).sendKeys("Rhapsody of the Seas");
		action.sendKeys(Keys.ENTER).perform();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText("Rhapsody of the Seas")));
		driver.findElement(By.partialLinkText("Rhapsody of the Seas")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("BOOK NOW")));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.linkText("BOOK NOW")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@data-testid=\"date-filter-button\"]")));
		driver.findElement(By.xpath("//button[@data-testid=\"date-filter-button\"]")).click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[text() = 'Sep']")));
		
		 try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Jul']")));
		WebElement element1 = driver.findElement(By.xpath("//*[text() = 'Sep']"));
		
		element1.click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Aug']")));
		WebElement element2 = driver.findElement(By.xpath("//*[text() = 'Oct']"));
		element2.click();
 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Sep']")));
		WebElement element3 = driver.findElement(By.xpath("//*[text() = 'Nov']"));
		element3.click();
 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Oct']")));
		WebElement element4 = driver.findElement(By.xpath("//*[text() = 'Dec']"));
		element4.click();
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement deptPort = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//section[@id = 'filter-modal']//button[text() = 'Departure Port']")));
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", deptPort);

		js.executeScript("arguments[0].click();", deptPort);
		WebElement san = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='San Juan, Puerto Rico']")));
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", san);
		js.executeScript("arguments[0].click();", san);
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement dept=wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"components__ButtonWrap-sc-6abd413c-1 gBFaFs\"]")));
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", dept);
		WebElement dept1=wait.until(ExpectedConditions.elementToBeClickable(dept));
		js.executeScript("arguments[0].click();", dept1);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement destPort = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//section[@id = 'filter-modal']//button[text() = 'Destinations']")));
		js.executeScript("arguments[0].scrollIntoView({block: 'center'});", destPort);
		WebElement destPort1=wait.until(ExpectedConditions.elementToBeClickable(destPort));
		js.executeScript("arguments[0].click();", destPort1);

		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement dest = driver.findElement(By.xpath("//div[@class=\"components__ButtonWrap-sc-6abd413c-1 gBFaFs\"]"));
		js.executeScript("arguments[0].scrollIntoView(true);", dest);
		dest.click();
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement CuriseLength = driver.findElement(By.xpath("//section[@id = 'filter-modal']//button[text() = 'Number of nights']"));
		CuriseLength.click();
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	
		WebElement numOfNights = driver.findElement(By.xpath("//div[@class=\"NightsSelector__OverPill-sc-c59cb1b1-2 hiorfi\"]"));
		js.executeScript("arguments[0].scrollIntoView(true);", numOfNights);
		numOfNights.click();
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		driver.findElement(By.xpath("//button[text() = 'See results']")).click();
	
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		driver.findElement(By.xpath("//div[@data-testid=\"sort-by-results-dropdown\"]")).click();
	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		driver.findElement(By.xpath("//p[text() = 'Price lowest to highest']")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		List<WebElement> list = driver.findElements(By.xpath("//div[@id=\"cruise-results-wrapper\"]/div"));
		System.out.println("Number of Search Results: "+list.size());
		driver.quit();
		
	}
}