package controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


public class DriverExtrator {
	
	private WebDriver driver;
	
	public DriverExtrator(boolean headless, boolean disableImages) throws IOException {
		WebDriverManager.chromedriver().setup();

		ChromeOptions options = new ChromeOptions();
		if(headless) {
		    options.addArguments("--headless");
		}else {
			options.addArguments("--disable-notifications");
		}
		if(disableImages) {
			options.addArguments("--disable-gpu");
			options.addArguments("--blink-settings=imagesEnabled=false");
		}
		driver = new ChromeDriver(options);
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.MINUTES);
		driver.manage().window().maximize();
	}
	
	public void waitForLoad() {
	    new WebDriverWait(driver, 60).until((ExpectedCondition<Boolean>) wd ->
            ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
	}

	private void waitForAjax(long segundos){
		new WebDriverWait(driver, segundos).until((ExpectedCondition<Boolean>) wd ->
			((JavascriptExecutor) wd).executeScript("return jQuery.active").equals("0"));
	}
	
	public void waitForLazyLoad(String path, long segundos) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int dataSize = driver.findElements(By.xpath(path)).size();
		
		for(int i = 0; i < 100; i++) {
			long targetHeight = (long) js.executeScript("return document.body.scrollHeight - 10000" );
			js.executeScript("window.scrollTo(0, " + targetHeight + ")" );
			waitForLoad();
		}
		
//		while (true){
//
//			waitForLoad();
//			long targetHeight = (long) js.executeScript("return document.body.scrollHeight-100" );
//			js.executeScript("window.scrollTo(0, " + targetHeight + ")" );
//			
//		    if (driver.findElements(By.xpath(path)).size() == dataSize)
//		        return;
//		    
//		    if (driver.findElements(By.xpath(path)).size() > 10)
//		        return;
//		    
//		    dataSize = driver.findElements(By.xpath(path)).size();
//		}
	}
	
	public void hoverMouseJavaScript(WebElement webElement) {
		String javaScript = "var evObj = document.createEvent('MouseEvents');" +
                "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);" +
                "arguments[0].dispatchEvent(evObj);";


		((JavascriptExecutor)driver).executeScript(javaScript, webElement);
	}
	
	public WebDriver getDriver() {
		return driver;
	}

}
