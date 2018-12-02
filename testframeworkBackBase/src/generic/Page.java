package generic;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;


public abstract class Page extends LoadableComponent<Page> {
	//private static final String SUCCESS_MSG = "success";
	//private static final String FAILURE_MSG = "failure";
	protected static WebDriver driver;
	protected final Log log;	
	
	protected Properties properties = new Properties();
	
	@Override	
	protected abstract void load();
	@Override
	protected abstract void isLoaded() throws Error;	
	
	protected Page(WebDriver driver, Log log) throws InvalidFormatException, IOException {
		this.driver = driver;
		this.log = log;
		try {
			log.logLine("", false, "loading .."+ getClass().getName()+".properties !!");			
			InputStream reader = this.getClass().getResourceAsStream(getClass().getSimpleName()+".properties");
			properties.load(reader);
		}
		catch(IOException e) {	
			System.out.println(e);
			log.logLine("", true, "IOException upon loading " + getClass().getName() + ".properties");
			System.exit(1);				
		}
	}
	
	public static boolean elementExistXpath(String elementXPath, int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		boolean exists = driver.findElements(By.xpath(elementXPath)).size() != 0;
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		return exists;
	}
	
	public static boolean elementExistCssSelect(String Cssselector, int timeout) {
		driver.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
		boolean exists = driver.findElements(By.cssSelector(Cssselector)).size() != 0;
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		return exists;
	}
	
	public void waitUntilRetrive() throws Exception {
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		for (int i = 0; i < 200; i++) {
			if (elementExistXpath("//div/span[contains(text(), \"...\")]", 3)) 
					/*|| (doesElementExist("//div/span[text()='Updating eDelivery data...']", 3))
					|| (doesElementExist("//div/span[text()='Closing form...']", 3)) 
					|| (doesElementExist("//div/span[text()='Updating Tool...']", 3))
					|| (doesElementExist("//div/span[text()='Editing eDelivery data...']", 3))
					|| (doesElementExist("//div/span[text()='Editing eDelivery data...']", 3)) 
					*/
				Thread.sleep(100);
			else
				break;
		}
		log.logLine("", false,"Retriving... element did disappear after sometime!");
	}
	
	public boolean isTextPresent(String text){
        try{
            boolean b = driver.getPageSource().contains(text);
            return b;
        }
        catch(Exception e){
            return false;
        }
    }
	

	public void PleasewaitDisappear() throws Exception {
	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    for (int i = 0; i < 120; i++) {
	    	if (elementExistCssSelect("div[class='k-label'] img", 1))
	    		Thread.sleep(100);
	    	else 
	    		break;
	    }	        
	    log.logLine("", false,"Please Wait... element did disappear after sometime!");
	}
	
	
	public void waitUntilLoadElementDisappear4() throws Exception {
	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    for (int i = 0; i < 1200; i++) {
	    	if ((elementExistXpath("//div[text()='Loading...']", 1)) 
	    			|| (elementExistXpath("//div[text()='Upload to the library....']", 1))||(elementExistXpath("//div[text()='Edit operation was successful!']", 1)	    			
	    			|| (elementExistXpath("//div[text()='Upload your Pdf to the library....']", 1)))) {
	    		Thread.sleep(500);
	    	} else {
	    		break;
	    	}	        
	    }
	    log.logLine("", false,"The Loading... element did disappear after sometime!");
	}
	
	
	// Wait for 1 minute to disappear Upload images/pdf... element, if not
	public void waitUntilUploadingElementDisappear() throws Exception {
	    driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	    for (int i = 0; i < 1200; i++) {
	    	if (elementExistCssSelect("div[role='alertdialog'] div div div div[role='heading'] span", 1)) 
	    		Thread.sleep(500);
	    	else 
	    		break;
	    }
	    log.logLine("", false,"The Uploading... element did disappear after sometime!");
	}
	
	
	public boolean isAlertPresent()	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }
	}	
	
	
	public static WebElement waitForElement(final String eleselector) throws Exception {
		WebElement element = null;
		try {
			element = (new WebDriverWait(driver, 120)).until(new ExpectedCondition<WebElement>(){
	    		public WebElement apply(WebDriver d) {
					return d.findElement(By.cssSelector(eleselector));
	    		}
	    	});	    	
		} catch (Exception e) {
			Reporter.log("Application not loaded the object within the buffer time: 2 mins..."+e.getMessage());
		}
		return element;
	}
	
	public static WebElement waitForElement2(final String xpath) throws Exception {
		WebElement element = null;
		try {
			element = (new WebDriverWait(driver, 120)).until(new ExpectedCondition<WebElement>(){
	    		public WebElement apply(WebDriver d) {
					return d.findElement(By.xpath(xpath));
	    		}
	    	});	    	
		} catch (Exception e) {
			Reporter.log("Application not loaded the object within the buffer time: 2 mins..."+e.getMessage());
		}
		return element;
	}
	
	
	public boolean isAttribtuePresent(WebElement element, String attribute) {
	    Boolean result = false;
	    try {
	        String value = element.getAttribute(attribute);
	        if (value != null){
	            result = true;
	        }
	    } catch (Exception e) {}

	    return result;
	}
	
}


