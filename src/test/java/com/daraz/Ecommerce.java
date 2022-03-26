package com.daraz;



import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Ecommerce {

	public WebDriver driver;
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
//this one will run before test
	@BeforeTest
	public void setExtent() {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/myReport.html");
		htmlReporter.config().setDocumentTitle("Automation Report");
		
		htmlReporter.config().setTheme(Theme.STANDARD);

		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);

		// Passing General information
		
		extent.setSystemInfo("Tester Name", "Anamul Haque");

	}

	@AfterTest
	public void endReport() {
		extent.flush();
	}
//before test execution
	@BeforeTest
	public void setup() {
		WebDriverManager.firefoxdriver().setup();
		 driver = new FirefoxDriver();
		driver.manage().window().maximize();

		driver.get("https://www.daraz.com.bd");
	}

	@Test(priority = 0)
	public void darazTitleTest() {

		test = extent.createTest("darazTitleTest");
	
		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(title, "Online Shopping in Bangladesh: Order Now from Daraz.com.bd");
	}
	@Test(priority = 1)
	public void darazLogoTest() throws InterruptedException {
		test = extent.createTest("darazLogoTest");
		Thread.sleep(15000);
		driver.getWindowHandles();
		driver.switchTo().alert().dismiss();
		int logo = driver.findElements(By.cssSelector("a[data-spm-anchor-id= 'a2a0e.home.header.dhome']")).size(); 
		if(logo > 0) {
			System.out.println("logo found");
		}else {
			System.out.println("logo not found");
		}
		//driver.quit();
		
	}
	

	
	@AfterMethod
	 public void tearDown(ITestResult result) throws IOException {
	  if (result.getStatus() == ITestResult.FAILURE) {
	   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); // to add name in extent report
	   test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); // to add error/exception in extent report
	   String screenshotPath = Ecommerce.getScreenshot(driver, result.getName());
	   test.addScreenCaptureFromPath(screenshotPath);// adding screen shot
	  } else if (result.getStatus() == ITestResult.SKIP) {
	   test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
	  }
	  else if (result.getStatus() == ITestResult.SUCCESS) {
	   test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
	  }
	  
	}
	  //screenshot method
	  public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
		  String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		  TakesScreenshot ts = (TakesScreenshot) driver;
		  File source = ts.getScreenshotAs(OutputType.FILE);
		  
		  // after execution, you could see a folder "FailedTestsScreenshots" under src folder
		  String destination = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		  File finalDestination = new File(destination);
		  FileUtils.copyFile(source, finalDestination);
		  return destination;
		 }
		 
		 
}