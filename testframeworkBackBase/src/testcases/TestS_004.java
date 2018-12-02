package testcases;

import generic.Log;
import org.openqa.selenium.WebDriver;
import modules.DelComputerModule;

public class TestS_004 {	
	private WebDriver driver;
	private Log log;
	
	public TestS_004(WebDriver driver, Log log) {
		this.driver = driver;
		this.log = log;	
	}
	
	public Boolean test4() throws Exception  {
		
		int paperID = (int) Math.round(Math.random() * (999999 - 100000 + 1) + 100000);		
		String AccNo = Integer.toString(paperID);		
		String Testname = new Object(){}.getClass().getEnclosingClass().getName();		
		
		boolean Retuser1, Retuser2 = false;
		
		DelComputerModule repts = new DelComputerModule(driver, log);
		Retuser1 = repts.deleteComp(AccNo, Testname);
		
		if (Retuser1) {
				log.logLine(Testname, false, "Verifying the Deletion of existing computer details is successful");
		} else {
			log.logLine(Testname, true, "Verifying the Deletion of existing computer details is unsuccessful");
			throw new Exception("Verifying the Deletion of existing computer details is unsuccessful");
		}	
		return Retuser2;		
	}
	
	
}

