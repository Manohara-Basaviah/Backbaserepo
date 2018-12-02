package modules;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import generic.Initialization;
import generic.Log;
import generic.Page;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.testng.Assert.assertEquals;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class BackBaseSignInOut extends Page{
	
	public BackBaseSignInOut(WebDriver driver, Log log) throws InvalidFormatException, IOException {
		super(driver, log);						
	}	
	
	@Override
	protected void load() { } 
	
	public static String Uname="";
	
	public void load(String Browser, String EnvirSite) throws InvalidFormatException, IOException, InterruptedException {
    	log.logLine("", false, "Navigate to BackBase Computer DB login page");
    	
    	if (EnvirSite.equalsIgnoreCase("Dev")) {
    		driver.get(properties.getProperty("DEVURL"));
    		log.logLine("", false, "Navigated to BackBase - QA site");
    		
    		if ((Browser.equalsIgnoreCase("ie")) || (Browser.equalsIgnoreCase("internetExplorer"))) { 
    			driver.get("javascript:document.getElementById('overridelink').click();");
    		} else if ((Browser.equals("Safari")) || (Browser.equals("SAFARI")) || (Browser.equals("safari"))) {
    			 try {
    			        Alert alert = driver.switchTo().alert();

    			        // check if alert exists
    			        // TODO find better way
    			        alert.getText();

    			        // alert handling    			        
    			        alert.accept();
    			    } catch (Exception e) {
    			    }
    			//driver.switchTo().alert().accept();
    		}
    		
    	} else if (EnvirSite.equalsIgnoreCase("Test")) {
    		driver.get(properties.getProperty("TESTURL"));
    		log.logLine("", false, "Navigated to BackBase TEST site");
    		
    	} else if (EnvirSite.equalsIgnoreCase("prod")) {
    		driver.get(properties.getProperty("PRODURL"));
    		log.logLine("", false, "Navigated to BackBase - PRODUCTION site");
    	}
    	
		driver.manage().window().maximize();		
    }	
	
	protected void isLoaded() throws Error {    	
    	String url = driver.getCurrentUrl();
    	//assert.assertEquals(url, url.compareTo(properties.getProperty("URL")));
    }
 
    public void loginAs(String username, String password) throws Exception {
    	
    	if (Initialization.AutoMultipleUser.equalsIgnoreCase("yes")) {
    		
    		String Pword = null;
    		   		
			File inputWorkbook = new File("C:/BackBase_TA/Config/MultipleUsers.xls");
			Workbook w;
			String cellData = null;
			try {
				w = Workbook.getWorkbook(inputWorkbook);
				Sheet sheet = w.getSheet(0);				
				Cell cell = sheet.getCell(0, 0);
				
				if (cell.getContents().equalsIgnoreCase("Status")) {
					for (int j = 1; j < sheet.getRows(); j++) {
						cellData = sheet.getCell(0, j).getContents();
						if (cellData.equalsIgnoreCase("yes")) {
							Uname = sheet.getCell(1, j).getContents().trim();
							Pword = sheet.getCell(2, j).getContents().trim();
							break;							
						}
					}
				}
						
			} catch (BiffException e) {
				e.printStackTrace();
			}		
				
    		enterUsername(Uname);
    		enterPassword(Pword);
    		
    	} else {
    		enterUsername(username);     
    		enterPassword(password);
    	}
    	
        submitLogin();      
        
        //Wait till the admin menu loads in Home page
        waitForElement(properties.getProperty("AdminMenu"));
    }
    
    public void enterUsername(String userName) throws Exception {
    	log.logLine("", false, "fill up user name = " + userName);
    	WebElement retelm = waitForElement(properties.getProperty("UserName"));
    	if (retelm.isDisplayed()) {
	    	WebElement usrnamefld = driver.findElement(By.cssSelector(properties.getProperty("UserName")));    	
	        clearAndType(usrnamefld, userName);    	
    	} else {
    		log.logLine("", true, "BackBase login page: UserName field does not exist to operate");
			throw new Exception("BackBase login page: UserName field does not exist to operate");
    	}
    }
    
    public void enterPassword(String passWord) throws InvalidFormatException, IOException {
    	log.logLine("", false, "fill up password = " + passWord);
    	WebElement passwdfld = driver.findElement(By.cssSelector(properties.getProperty("Password")));
    	clearAndType(passwdfld, passWord);    	
    }
    
    public void submitLogin() throws InterruptedException, InvalidFormatException, IOException {
    	log.logLine("", false, "click login");
    	driver.findElement(By.cssSelector(properties.getProperty("loginBtn"))).click();
    	Thread.sleep(3000);
    }       
    
    private void clearAndType(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }
    
    public void clickLogout() throws Exception {
    	log.logLine("", false, "There is no logout functionality...");   	
		       	 
    }	
	
	public void closeBrowser() throws InvalidFormatException, IOException {
		log.logLine("", false, "Closing all the browsers launched by WebDriver.");
		driver.quit();
	}   
		
}
