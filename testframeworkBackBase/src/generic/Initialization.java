package generic;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;
import modules.BackBaseSignInOut;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Initialization {	
	
	public static String Browser; 				public static Log log;
	public static WebDriver driver;		 		public static boolean testval;	
	public static int mycnt=0;					public static String timestamp, ServerName;
	public static String myTimestamp; 			public static String host, AutoMultipleUser;	
	public static String UserID; 				public static String Passwd;
	public static String IterXML; 				public static String EnvirSite;
	public static String OSbits; 				public static String mydate, todaysDate;
	boolean Admin= false;						public static boolean remoteWebDriver;
	public static long startTime;				public static long endTime; 
	public static double totalTime;				public static String CurDateTime;	
	public static int PassCnt=0, FailCnt=0, SkipCnt=0;

	
	@BeforeSuite // Captures when the execution started and checks, extracts the parameter from config.xml
	public void Initialize() throws Exception {
		
		// Capture the Start time of test execution
		startTime = System.currentTimeMillis();		
		
		SimpleDateFormat gmtDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");		
		gmtDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		//Current Date Time in IST
		CurDateTime = gmtDateFormat.format(new Date());
		
		// Create time stamp to keep the session track
		int paperID = (int) Math.round(Math.random() * (999999 - 100000 + 1) + 100000);		
		myTimestamp = Integer.toString(paperID);	
		
		InputOutputData test = new InputOutputData();	
		
		//read the Config.xml elements
		String Consts = test.readConfigXML();

		String Vars = new String(Consts);
		String[] arr = Vars.split(" ");
		if (arr.length > 5) {
			IterXML = arr[0];
			EnvirSite = arr[1];
			UserID = arr[2];
			Passwd = arr[3];
			Browser = arr[4];
			AutoMultipleUser = arr[5];		
		} else {
			throw new Exception("Attribute in Config XML status is 'completed', change the status inorder to run");
		}		
		
		//kill the already running process
		test.KillautoProcess(Browser);
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy_HHmmss");		
		Date date = new Date();
		mydate = dateFormat.format(date);			
		try {			
			InetAddress thisIp = InetAddress.getLocalHost();
			log = new Log(thisIp.getHostAddress());			
			ServerName = thisIp.getHostName();
			
			//Create report folder with current time-stamp
			log.createFold("C:/BackBase_TA/Report & Logs", mydate);
			
			//Copy the sample logs and report files
			test.CopyReportFile(myTimestamp);
			
			log.logLine("", false, "Log instance has been created for this round of execution with TimeStamp: "+myTimestamp);			
											
		} catch(IOException e) {
			log.logLine("", true, "Failed to create Run time folders for the execution - exiting");
			System.exit(1);
		}				
	}
	
	
	@BeforeTest // Decides what browser to use for execution and login
	public static void LaunchBrowser() throws Exception {	
		
		OSbits = System.getProperty("sun.arch.data.model");
		remoteWebDriver = false;
		File file = null;
		if ((Browser.equalsIgnoreCase("ie")) || (Browser.equalsIgnoreCase("InternetExplorer"))) {
			if (OSbits.equals("64")) { 
				file = new File("C:/BackBase_TA/Selenium jars/IEDriverServer32.exe");
			} else if (OSbits.equals("32")) {
				file = new File("C:/BackBase_TA/Selenium jars/IEDriverServer64.exe");
			}
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			DesiredCapabilities iecapabilities = DesiredCapabilities.internetExplorer();
			iecapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			iecapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			iecapabilities.setJavascriptEnabled(true);		
			iecapabilities.setCapability("requireWindowFocus", true);
			iecapabilities.setCapability("enablePersistentHover", false);
			iecapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
			iecapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "accept");
			iecapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			
			driver = new InternetExplorerDriver();			
			driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
			
		} else if ((Browser.equalsIgnoreCase("safari"))) {
			
			Platform current = Platform.getCurrent();
		    if (Platform.MAC.is(current) || Platform.WINDOWS.is(current)) {
				DesiredCapabilities safariCapabilities = DesiredCapabilities.safari();
				safariCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				safariCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				safariCapabilities.setJavascriptEnabled(true);
				safariCapabilities.setCapability("requireWindowFocus", true);
				safariCapabilities.setCapability("enablePersistentHover", false);
				safariCapabilities.setCapability(CapabilityType.HAS_NATIVE_EVENTS, true);
				safariCapabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, "accept");
				safariCapabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
				safariCapabilities.setCapability("browser.helperApps.neverAsk.saveToDisk", "application/pdf,application/x-pdf");
							
				driver = new SafariDriver();
		    }
			
		} else if ((Browser.equalsIgnoreCase("ff")) || (Browser.equalsIgnoreCase("firefox"))) {
			String downloadPath = "C:\\BackBase_TA\\Test Output";
            FirefoxProfile firefoxProfile = new FirefoxProfile();
            System.setProperty("webdriver.gecko.driver", "path/to/geckodriver.exe");
            firefoxProfile.setPreference("browser.download.folderList",2);
            firefoxProfile.setPreference("browser.download.manager.showWhenStarting",false);
            firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false); 
            firefoxProfile.setPreference("browser.download.dir", downloadPath);
            firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                                          "text/csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.wordprocessingml.document, application/msword, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/pdf, image/png,image/jpeg,text/html,text/plain");
            
            driver = new FirefoxDriver();		
			
		} else if (Browser.equalsIgnoreCase("chrome")) {
			
			driver = new ChromeDriver();			
			
		
		}		
		
		//Call sign in methods for BackBase DB application
		BackBaseSignInOut loginPge = new BackBaseSignInOut(driver, log);
		loginPge.load(Browser, EnvirSite);
		Thread.sleep(6000);
		//loginPge.loginAs(UserID, Passwd);		
	}	
	
		
	@BeforeMethod // Decides whether the test to be run by receiving "Control" from config file
	public static void ControlTest() throws IOException, Exception {
		
		InputOutputData test = new InputOutputData();
	    test.setInputFile("C:/BackBase_TA/Config/Driver.xls");	    
	    String mystr = test.readControlFile("Control", "ControlFile", mycnt+1);
	    
	    if ((mystr.equalsIgnoreCase("y")) || (mystr.equalsIgnoreCase("yes"))) {
	    	testval = true;
		} else {
	    	testval = false;
		}	
	    mycnt = mycnt + 1;
	}
	
	
	@AfterMethod // Capture screenshot on Fail/Pass events
	public void Screenshot(ITestResult result) throws IOException, InvalidFormatException {
		
	    if (!result.isSuccess()) { 
	    	if (testval) {
	    		FailCnt = FailCnt + 1;
	    		//log.updateTestResults(result.getMethod().getMethodName(), "Fail");	
		        File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		        String failImg = "/BackBase_TA/Report & Logs/BackBase_Report_"+mydate+"/Failure_Screenshots/"+result.getMethod().getMethodName()+ "_" + myTimestamp + ".png";
		        File failureImageFile = new File(failImg);
		        FileUtils.moveFile(imageFile, failureImageFile);
		        Reporter.log("<a href='"+ failImg +"'> <img src='"+ failImg + "' height='100' width='100'/> </a>");
	    	} else {
	    		SkipCnt = SkipCnt + 1;
	    		//log.updateTestResults(result.getMethod().getMethodName(), "Skip");
	    	}
	    } else {
	    	PassCnt = PassCnt + 1;
	    	//log.updateTestResults(result.getMethod().getMethodName(), "Pass");
	    	File imageFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);	    	
	    	String passImg = "/BackBase_TA/Report & Logs/BackBase_Report_"+mydate+"/Logs/"+result.getMethod().getMethodName()+ "_" + myTimestamp + ".png";
	        File passimgfile = new File(passImg);
	        FileUtils.moveFile(imageFile, passimgfile);
	        Reporter.log("<a href='"+ passImg +"'> <img src='"+ passImg + "' height='100' width='100'/> </a>");      
	     }
	}
	
	
	@AfterTest // Close all the browsers and update the summary in report
	public static void closeBrowser() throws Exception {	
		
		BackBaseSignInOut logoutpge = new BackBaseSignInOut(driver, log);		
		logoutpge.clickLogout();	
		logoutpge.closeBrowser();		
		
		// Capture the end time of test execution
		endTime = System.currentTimeMillis();
		long testime = endTime - startTime;
		totalTime =(double) ((testime/(1000*60)));  
				
		//log.updateTestSummary();
	}
	
	
	@AfterSuite // Status to completed in Config file and check if the same config has to be run with different role user
	public void UpdateConfig() throws Exception {	
		
		InputOutputData test = new InputOutputData();
		
		if (!(AutoMultipleUser.equalsIgnoreCase("yes"))) {			
			test.UpdateConfigXML(Integer.parseInt(IterXML));
		}
			
		DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");		
		Date date = new Date();
		todaysDate = dateFormat.format(date);
		
		//Send email to recipients attaching report to it
		if (AutoMultipleUser.equalsIgnoreCase("yes")) {
			// Can send a mail to members once the execution is completed  <<Not implemented yet>>
			//test.SendReportEmail(EnvirSite+" Automation -"+PivotSignInOut.Uname +" on "+todaysDate+" Success-"+PassCnt+": Fail-"+FailCnt+": Skip-"+SkipCnt);			
		} else {
			
			//test.SendReportEmail(EnvirSite+" Automation as on "+todaysDate+" Success-"+PassCnt+": Fail-"+FailCnt+": Skip-"+SkipCnt);
		}
	}
	
		
	
}

