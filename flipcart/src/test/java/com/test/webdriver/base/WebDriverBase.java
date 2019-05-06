package com.test.webdriver.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.test.webdriver.util.TestUtil;
import com.test.webdriver.util.WebEventListener;



public class WebDriverBase {
	
	public static WebDriver driver;
	public static Properties prop;
	private static File respourcepath = new File("src/test/resources");
	public  static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	
	public WebDriverBase(){
		try {
			prop = new Properties();
			
			File configpath = new File(respourcepath,"config.properties");
			
			FileInputStream ip = new FileInputStream(configpath.getAbsoluteFile());
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void initialization(){
		String browserName = prop.getProperty("browser");
		File driverpath ;
		
		
		if(browserName.equals("chrome")){
			driverpath = new File(respourcepath,"chromedriver.exe");
			System.setProperty("webdriver.chrome.driver", driverpath.toString());	
			driver = new ChromeDriver(); 
		}
		else if(browserName.equals("firefox")){
			driverpath = new File(respourcepath,"geckodriver.exe");
			System.setProperty("webdriver.gecko.driver",driverpath.toString());	
			driver = new FirefoxDriver(); 
		}
		
		else if(browserName.equals("ie")){
			driverpath = new File(respourcepath,"MicrosoftWebDriver.exe");
			System.setProperty("webdriver.ie.driver",driverpath.toString());	
			driver = new InternetExplorerDriver(); 
		}
		
		
		
		e_driver = new EventFiringWebDriver(driver);
		// Now create object of EventListerHandler to register it with EventFiringWebDriver
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		driver = e_driver;
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		
		driver.get(prop.getProperty("url"));
		
	}
	
	
	
	
	
	
	
	

}
