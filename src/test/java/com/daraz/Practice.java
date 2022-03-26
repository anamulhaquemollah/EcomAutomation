package com.daraz;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Practice {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		//System.setProperty("webdriver.chrome.driver", "C:\\work\\chromedriver.exe");
		WebDriverManager.firefoxdriver().setup();
		WebDriver driver = new FirefoxDriver(); 
		
		driver.get("https://www.youtube.com"); 
		
		System.out.println("hello from youtube"); 
		Thread.sleep(10000); 
		int size = driver.findElements(By.tagName("a")).size();
		System.out.println("hello from youtube" + size); 
		
		driver.quit(); 

	}

}
