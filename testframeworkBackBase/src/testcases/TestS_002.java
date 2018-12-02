package testcases;

import generic.Initialization;
import generic.Log;
import org.openqa.selenium.WebDriver;
import modules.ModifyComputerModule;

public class TestS_002 {	
	private WebDriver driver;
	private Log log;
	
	public TestS_002(WebDriver driver, Log log) {
		this.driver = driver;
		this.log = log;	
	}
	
	public Boolean test2() throws Exception  {
		
		int paperID = (int) Math.round(Math.random() * (999999 - 100000 + 1) + 100000);		
		String AccNo = Integer.toString(paperID);		
		String Testname = new Object(){}.getClass().getEnclosingClass().getName();		
		
		ModifyComputerModule report = new ModifyComputerModule(driver, log);
		boolean Retval = report.modifyComputer(AccNo, Testname, "Multiple");
		
		if (Retval && (Initialization.AutoMultipleUser.equalsIgnoreCase("yes"))) {
			return true;
		}
		
		if (Retval){
			log.logLine(Testname, false, "Modifying computer to BackBase DB is successful");
		} else {
			log.logLine(Testname, true, "Modifying computer to BackBase DB is unsuccessful");
			throw new Exception("Modifying computer to BackBase DB is unsuccessful");
		}		
		
		return Retval;		
	}
}

