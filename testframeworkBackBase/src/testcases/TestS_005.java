package testcases;

import generic.Initialization;
import generic.Log;
import org.openqa.selenium.WebDriver;
import modules.SearchCompModule;

public class TestS_005 {	
	private WebDriver driver;
	private Log log;
	
	public TestS_005(WebDriver driver, Log log) {
		this.driver = driver;
		this.log = log;	
	}
	
	public Boolean test5() throws Exception  {
		
		int paperID = (int) Math.round(Math.random() * (999999 - 100000 + 1) + 100000);		
		String AccNo = Integer.toString(paperID);		
		String Testname = new Object(){}.getClass().getEnclosingClass().getName();		
		
		SearchCompModule report = new SearchCompModule(driver, log);
		boolean Retval = report.SearchComp(AccNo, Testname);
		
		if (Retval && (Initialization.AutoMultipleUser.equalsIgnoreCase("yes"))) {
			return true;
		}
		
		if (Retval){
			log.logLine(Testname, false, "Verification of Search for Computer details is successful");
		} else {
			log.logLine(Testname, true, "Verification of Search for Computer details is unsuccessful");
			throw new Exception("Verification of Search for Computer details is unsuccessful");
		}		
		
		return Retval;		
	}
}

