package testcases;

import generic.Log;
import org.openqa.selenium.WebDriver;
import modules.ValidateCompDetails;

public class TestS_003 {	
	private WebDriver driver;
	private Log log;
	
	public TestS_003(WebDriver driver, Log log) {
		this.driver = driver;
		this.log = log;	
	}
	
	public Boolean test3() throws Exception  {
		
		int paperID = (int) Math.round(Math.random() * (999999 - 100000 + 1) + 100000);		
		String AccNo = Integer.toString(paperID);		
		String Testname = new Object(){}.getClass().getEnclosingClass().getName();		
		
		boolean Retuser1, Retuser2 = false;
		ValidateCompDetails repts = new ValidateCompDetails(driver, log);
		Retuser1 = repts.validateComp(AccNo, Testname);
		
		if (Retuser1) {	
			log.logLine(Testname, false, "Verification of Computer details validation is successful");
		} else {
			log.logLine(Testname, true, "Verification of Computer details validation is unsuccessful");
			throw new Exception("Verification of Computer details validation is unsuccessful");
		}
		
		return Retuser2;
	}
	
	
}

