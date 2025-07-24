package test;

import java.io.IOException;

import org.testng.Assert;
import org.testng.annotations.Test;

import pages.CreateAccountPage;
import pages.HomePage;
import pages.TermsPage;
import selenium.BaseClass;
import utilities.ExcelOutput;

public class TestCase extends BaseClass {
	ExcelOutput eOutput = new ExcelOutput();
	@Test(priority=1)
	public void goToCreateAccount() {
		HomePage hPage = new HomePage(driver);
		hPage.createAccountClick();
		CreateAccountPage cap =new  CreateAccountPage(driver);
		Assert.assertTrue(cap.validateCAP());
	}
	@Test(priority = 2)
	public void goToTermsAndConditions(){
		CreateAccountPage cap =new  CreateAccountPage(driver);
		int c = cap.countList();
		outputList.add(String.valueOf(c));
		System.out.println("srhsr");
		System.out.println(outputList);
		cap.clickTAndC();
		
		TermsPage tPage = new TermsPage(driver);
		Assert.assertTrue(tPage.validateCWinOpen());	
	}
	@Test(priority = 3)
	public void childWindowOp() throws InterruptedException {
		TermsPage tPage = new TermsPage(driver);
		tPage.switchWindow();
		Thread.sleep(2000);
		Assert.assertTrue(tPage.validateCWinTl());
		
	}
	@Test(priority = 4)
	public void switchBack() throws IOException {
		TermsPage tPage = new TermsPage(driver);
		tPage.closeWin();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CreateAccountPage cap =new  CreateAccountPage(driver);
		cap.returnToP();
		eOutput.setData(outputList);
		Assert.assertTrue(cap.validateCAP());
	}
}
