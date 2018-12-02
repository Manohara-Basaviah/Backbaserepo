package modules;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import generic.InputOutputData;
import generic.Log;
import generic.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class ValidateCompDetails extends Page{
	
	public ValidateCompDetails(WebDriver driver, Log log) throws InvalidFormatException, IOException {
		super(driver, log);
	}	
	@Override
	protected void load() {}
	@Override
	protected void isLoaded() throws Error {}
	
	public boolean IEcheck=false;	

	public boolean validateComp(String AccNo, String Testname) throws Exception  {
		
		//To Read the date from properties file
		InputOutputData test = new InputOutputData();  
		test.setInputFile(properties.getProperty("InputDatafile")); 		
		String sheetname = new Object(){}.getClass().getEnclosingMethod().getName();	
		
		//Create 4 digit random number to keep computerName unique
		int paperID = (int) Math.round(Math.random() * (9999 - 1000 + 1) + 1000);		
		String RandNo = Integer.toString(paperID);		
		
		//Click on Home page to set the start point
		driver.findElement(By.xpath(properties.getProperty("HomePage")));
		
		if (elementExistCssSelect(properties.getProperty("AddcompBtn"), 5)) {    
			WebElement arclnk = driver.findElement(By.cssSelector(properties.getProperty("AddcompBtn")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", arclnk);
			
			PleasewaitDisappear();
			log.logLine(Testname, false, "Clicking on Add new computer button is successfull");
 		} else {
 			log.logLine(Testname, true, "Clicking on Add new computer button is failed");
 			throw new Exception("Clicking on Add new computer button is failed");
	 	}    
		
		//Read the computerName from the test data
		String NameComputer = test.readColumnData("ComputerName", sheetname);
		if (elementExistCssSelect(properties.getProperty("CompName"), 5)) {	  
			WebElement Txt = driver.findElement(By.cssSelector(properties.getProperty("CompName")));
			Txt.sendKeys(NameComputer+RandNo);
			log.logLine(Testname, false, "Entered Value in comupeter Name field :" +NameComputer+RandNo);			
		}else {
			log.logLine(Testname, true,"Unable to enter the text in comupeter Name field");
			throw new Exception("Unable to  enter the text in comupeter Name field");
		}
		
		//Get todays date & Discontinued date
		Date date = new Date();
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String todaysDate =  formatDate.format(date);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, 25); 
		String DiscontDate = formatDate.format(c.getTime());
		
		if (elementExistCssSelect(properties.getProperty("IntroDate"), 5)) {	  
			WebElement Txt = driver.findElement(By.cssSelector(properties.getProperty("IntroDate")));
			Txt.sendKeys(todaysDate);
			log.logLine(Testname, false, "Entered Date Value in field :" +todaysDate);			
		}else {
			log.logLine(Testname, true,"Unable to enter the date value");
			throw new Exception("Unable to  enter the date value");
		}
		
		if (elementExistCssSelect(properties.getProperty("DiscontDate"), 5)) {	  
			WebElement Txt = driver.findElement(By.cssSelector(properties.getProperty("DiscontDate")));
			Txt.sendKeys(DiscontDate);
			log.logLine(Testname, false, "Entered Discontinued Date Value in field :" +DiscontDate);			
		}else {
			log.logLine(Testname, true,"Unable to enter the Discontinued date value");
			throw new Exception("Unable to  enter the Discontinued date value");
		}
   

		Thread.sleep(1000);	    
	    String AssocComp = test.readColumnData("CompanyName", sheetname);			    
	    
		if (elementExistXpath("//*[contains(text(),'"+AssocComp+"')]", 5)) {	  
			WebElement dropdownlink = driver.findElement(By.xpath("//*[contains(text(),'"+AssocComp+"')]"));
			dropdownlink.click();
			log.logLine(Testname, false, "Entered Discontinued Date Value in field :" +DiscontDate);			
		}else {
			log.logLine(Testname, true,"Unable to enter the Discontinued date value");
			throw new Exception("Unable to  enter the Discontinued date value");
		}
		    
	    if (elementExistCssSelect(properties.getProperty("AddComBtn"), 5)) {    
			WebElement btnClick = driver.findElement(By.cssSelector(properties.getProperty("AddComBtn")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", btnClick);
			
			Thread.sleep(2000);
		    PleasewaitDisappear();

			log.logLine(Testname, false, "Clicking on Create this computer is successfull");
 		} else {
 			log.logLine(Testname, true, "Clicking on Create this computer is unsuccessfull");
 			throw new Exception("Clicking on Create this computer is unsuccessfull");
	 	}
	    
	    Thread.sleep(400);
	    if (elementExistCssSelect(properties.getProperty("ConfMessage"), 5)) {	    	
	    	log.logLine(Testname, false, "Done! new computer has been created successful");			    	
	    } else {
	    	log.logLine(Testname, true, "new computer has been creation is unsuccessful");		
	    	throw new Exception("new computer has been creation is unsuccessful");
	    }
	    
	    
	    ////Search for an Computer
	    String ActComputer = test.readColumnData("ComputerName", sheetname);
	    if (elementExistCssSelect(properties.getProperty("SearchFld"), 5)) {	  
			WebElement Txt = driver.findElement(By.cssSelector(properties.getProperty("SearchFld")));
			Txt.sendKeys(ActComputer+RandNo);
			log.logLine(Testname, false, "Entered computer name in search field :" +DiscontDate);			
		}else {
			log.logLine(Testname, true,"Unable to enter the computer name in search field");
			throw new Exception("Unable to  enter the computer name in search field");
		}			    
	    
	    //Click on Filter button and select the computer
	    if (elementExistCssSelect(properties.getProperty("FilterBtn"), 5)) {    
			WebElement btnClick = driver.findElement(By.cssSelector(properties.getProperty("FilterBtn")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click()", btnClick);

			Thread.sleep(1000);
		    log.logLine(Testname, false, "Clicking on Filter button is successfull");
 		} else {
 			log.logLine(Testname, true, "Clicking on Filter button is unsuccessfull");
 			throw new Exception("Clicking on Filter button is unsuccessfull");
	 	}
		
	    //Validate computer details entered vs displayed
	    if (elementExistXpath(properties.getProperty("IntrDateweb"), 5)) {    
			String IntrDateWeb = driver.findElement(By.xpath(properties.getProperty("IntrDateweb"))).getText();
			if (IntrDateWeb.matches(todaysDate)) {
				log.logLine(Testname, false, "Introduced Date validation is successfull");
			}
 		} else {
 			log.logLine(Testname, true, "Introduced Date validation is unsuccessfull");
 			throw new Exception("Introduced Date validation is unsuccessfull");
	 	}
	    
	    if (elementExistXpath(properties.getProperty("DiscDateweb"), 5)) {    
			String DiscDateWeb = driver.findElement(By.xpath(properties.getProperty("DiscDateweb"))).getText();
			if (DiscDateWeb.matches(DiscontDate)) {
				log.logLine(Testname, false, "Discontinued Date validation is successfull");
			}
 		} else {
 			log.logLine(Testname, true, "Discontinued Date validation is unsuccessfull");
 			throw new Exception("Discontinued Date validation is unsuccessfull");
	 	}
	    
	    if (elementExistXpath(properties.getProperty("Companyweb"), 5)) {    
			String ComNameWeb = driver.findElement(By.xpath(properties.getProperty("Companyweb"))).getText();
			if (ComNameWeb.matches(AssocComp)) {
				log.logLine(Testname, false, "Validation of Company Name of Computer is successfull");
				return true;
			}
 		} else {
 			log.logLine(Testname, true, "Validation of Company Name of Computer is unsuccessfull");
 			throw new Exception("Validation of Company Name of Computer is unsuccessfull");
	 	}    
	    return false;
 	}		 	
}
