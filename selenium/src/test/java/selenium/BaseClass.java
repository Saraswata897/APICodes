package selenium;
    import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

	public class BaseClass {
	    public WebDriver driver;
	    public String baseUrl = "https://www.rediff.com/";
	    public List<String> windowHandles;
	    public List<String> outputList=new ArrayList<String>();
//	    public BaseClass() {
//	    	outputList=new ArrayList<String>();
//	    }

	    @Parameters("browser")
	    @BeforeClass
	    public void setup(String browser) {
	        outputList.add(browser);
	    	if (browser.equalsIgnoreCase("chrome")) {
	            driver = BrowserFactory.getChromeDriver();
	        } else if (browser.equalsIgnoreCase("edge")) {
	            driver = BrowserFactory.getEdgeDriver();
	        }
	        driver.manage().window().maximize();
	        driver.get(baseUrl);
	    }
	    @AfterClass
	    public void closeBrowser() {
	        // Step 11: Close the browser
	        driver.quit();
	    }

	}
