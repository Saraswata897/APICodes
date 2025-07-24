package pageObjects;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.ExcelOutput;

public class ResultsPage extends BasePage {
	static int resultSize;
	ExcelOutput eo;
	public ResultsPage(WebDriver driver) {
		super(driver);
		eo=new ExcelOutput();
	}
	@FindBy(xpath = "//button[@data-testid='date-filter-button']")
	WebElement dateFilterBtn;
	@FindBy(xpath ="//*[text() = 'Sep']" )
	WebElement sepBtn;
	@FindBy(xpath="//*[text() = 'Oct']")
	WebElement octBtn;
	@FindBy(xpath = "//*[text() = 'Nov']")
	WebElement novBtn;
	@FindBy(xpath = "//*[text() = 'Dec']")
	WebElement decBtn;
	@FindBy(xpath = "//section[@id = 'filter-modal']//button[text() = 'Departure Port']")
	WebElement deptPortElement;
	@FindBy(xpath = "//button[text()='San Juan, Puerto Rico']")
	WebElement sanJuanElement;
//	@FindBy(xpath = "//div[@class='components__ButtonWrap-sc-6abd413c-1 gBFaFs']")
//	WebElement elementT;
	@FindBy(xpath = "//section[@id = 'filter-modal']//button[text() = 'Destinations']")
	WebElement destPortElement;
	@FindBy(xpath = "//div[@class='components__ButtonWrap-sc-6abd413c-1 gBFaFs']")
	WebElement destChoicElement;
	@FindBy(xpath = "//section[@id = 'filter-modal']//button[text() = 'Number of nights']")
	WebElement cruiseLengthElement;
	@FindBy(xpath = "//div[@class='NightsSelector__OverPill-sc-c59cb1b1-2 hiorfi']")
	WebElement nightsElement;
	@FindBy(xpath = "//button[text() = 'See results']")
	WebElement resultElement;
	@FindBy(xpath = "//div[@data-testid='sort-by-results-dropdown']")
	WebElement resDropDown;
	@FindBy(xpath = "//p[text() = 'Price lowest to highest']")
	WebElement priceElement;
	@FindBy(xpath = "//div[@id='cruise-results-wrapper']/div")
	List<WebElement> resultList;
//	@FindBy(xpath="//div[@id='cruise-results-wrapper']/div")
//	List<WebElement> resultCardElements;
	@FindBy(xpath = "//div[@id='cruise-results-wrapper']/div//h4")
	List<WebElement> resultNameElements;
	@FindBy(xpath = "//div[@id='cruise-results-wrapper']//span[starts-with(@class,'CruiseCardPrice-styles')][2]")
	List<WebElement> resultPriceElements;
	@FindBy(xpath = "//div[@itemprop='ratingValue']")
	List<WebElement> resultRatingElements;
	
	
	public void selectDate() {
		wait.until(ExpectedConditions.elementToBeClickable(dateFilterBtn)).click();;
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//*[text() = 'Sep']")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[text() = 'Jul']")));
		wait.until(ExpectedConditions.elementToBeClickable(sepBtn)).click();
		wait.until(ExpectedConditions.elementToBeClickable(octBtn)).click();
		wait.until(ExpectedConditions.elementToBeClickable(novBtn)).click();
		wait.until(ExpectedConditions.elementToBeClickable(decBtn)).click();
	}
	public void selectDepPort() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(deptPortElement));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", deptPortElement);
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(deptPortElement)).click();
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", deptPortElement);
		wait.until(ExpectedConditions.elementToBeClickable(sanJuanElement));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", sanJuanElement);
		Thread.sleep(500);
		sanJuanElement.click();
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", sanJuanElement);
	}
	public void destinationSelect() throws InterruptedException {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//section[@id = 'filter-modal']//button[text() = 'Destinations']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", destPortElement);
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(destPortElement)).click();
//		((JavascriptExecutor) driver).executeScript("arguments[0].click();", destPortElement);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", destChoicElement);
		Thread.sleep(500);
		destChoicElement.click();
		
	}
	public void cruiseLengthSelect() throws InterruptedException {
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//section[@id = 'filter-modal']//button[text() = 'Number of nights']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cruiseLengthElement);
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(cruiseLengthElement)).click();
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nightsElement);
		nightsElement.click();
	}
	public void clickResult() {
		wait.until(ExpectedConditions.elementToBeClickable(resultElement)).click();
	}
	public void filterResultDropdown() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(resDropDown)).click();
		Thread.sleep(500);
		wait.until(ExpectedConditions.elementToBeClickable(priceElement)).click();
	}
	public int countElements() {
		resultSize=resultList.size();
		return resultSize;
	}
	public void resultOut(String browser) throws IOException {
		for(int i=0;i<resultSize;i++) {
			String name = resultNameElements.get(i).getText();
			String price = resultPriceElements.get(i).getText();
			String rating = resultRatingElements.get(i).getText();
			eo.setData(name, price, rating, browser);
			System.out.println(name+"\t"+price+"\t"+rating);
		}
//		for(WebElement e:resultNameElements) {
//			System.out.println(e.getText());
//		}
//		System.out.println(resultNameElements);
//		System.out.println(resultPriceElements);
//		System.out.println(resultRatingElements);
	}
	
}
