/**
 * File Name: ResetPasswordTest.java<br>
 * Asfour, Rania<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Nov 7, 2015
 */
package com.sqa.ao.testNGTests;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class LoginWithDataProviderTest {

	private final static long LONG_TIME_TO_WAIT = 30;
	private final static long SHORT__TIME_TO_WAIT = 5;
	static String xlsFilePath = "D://Workspace//assertTests//data2.xls";
	static String sheetName = "Login";
	static String startPoint = "StartPoint";
	static String endPoint = "EndPoint";

	private WebDriver driver;
	
	@DataProvider(name = "credentails")
	public Object[][] credSet() throws BiffException, IOException {
		return getDataFromExcel(xlsFilePath, sheetName, startPoint, endPoint);
	}
	
	private String[][] getDataFromExcel(String xlsFilePath, String sheetName,
			   String startPoint, String endPoint) throws BiffException, IOException {
		String[][] tabArray = null;
		  // open an workbook
		  Workbook workbook = Workbook.getWorkbook(new File(xlsFilePath));
		  // open a sheet "Login"
		  Sheet sheet = workbook.getSheet(sheetName);
		  int startRow, startCol, endRow, endCol, ci, cj;
		  // find a cell labeled with "StartPoint"
		  Cell tableStart = sheet.findCell(startPoint);
		  // get a row of that cell
		  startRow = tableStart.getRow();
		  // get a column of that cell
		  startCol = tableStart.getColumn();
		  // find a cell#2 labeled with "EndPoint"
		  Cell tableEnd = sheet.findCell(endPoint);

		  // get a row of that cell
		  endRow = tableEnd.getRow();
		  // get a row of that cell
		  endCol = tableEnd.getColumn();
		  
		  
		  
		  return tabArray;
	}
	
	
	@Test(dataProvider="credentails")
	public void resetPass(String email, String password) {
		
		// launch google.com
		driver.get("http://www.salesforce.com/sales-cloud/");
		WebElement element = null;

		element = waitForVisibilityOfElement(LONG_TIME_TO_WAIT, By.id("button-login"));
		element.click();
		
		element = waitForVisibilityOfElement(LONG_TIME_TO_WAIT, By.id("username"));
		element.clear();
		element.sendKeys(email);
		
		element = waitForVisibilityOfElement(LONG_TIME_TO_WAIT, By.id("password"));
		element.clear();
		element.sendKeys(password);
		
		element = waitForVisibilityOfElement(LONG_TIME_TO_WAIT, By.id("Login"));
		element.click();
		
		
		final String msg = "Please check your username and password. If you still can't log in, contact your Salesforce administrator.";
		final String errorMessage = "Expected error doesn't natch with actual";
		
		/////////////////////////1 way////////////////////
		Assert.assertEquals(driver.findElement(By.id("error")).getText(), msg, errorMessage);
	}

	@BeforeMethod
	private void Setup() {
		// create a firefox driver to
		// drive the Browser
		driver = new FirefoxDriver();
	}

	@AfterMethod
	// quit quits the browser instead close
	private void tearDown() {
		driver.quit();
	}

	// Create a helper method for wait for expected conditions
	private WebElement waitForVisibilityOfElement(long time, By locator) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
}