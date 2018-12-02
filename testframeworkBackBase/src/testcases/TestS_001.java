package testcases;
import generic.Initialization;
import generic.Log;
import org.openqa.selenium.WebDriver;
import modules.AddComputerModule;

public class TestS_001 {	
	private WebDriver driver;
	private Log log;
	
	public TestS_001(WebDriver driver, Log log) {
		this.driver = driver;
		this.log = log;	
	}
	
	public Boolean test1() throws Exception  {
		
		int paperID = (int) Math.round(Math.random() * (999999 - 100000 + 1) + 100000);		
		String AccNo = Integer.toString(paperID);		
		String Testname = new Object(){}.getClass().getEnclosingClass().getName();		
		
		AddComputerModule report = new AddComputerModule(driver, log);
		boolean Retval = report.addComputer(AccNo, Testname);
		
		if (Retval && (Initialization.AutoMultipleUser.equalsIgnoreCase("yes"))) {
			return true;
		}
		
		if (Retval){
			log.logLine(Testname, false, "Adding computer to BackBase DB is successful");
		} else {
			log.logLine(Testname, true, "Adding computer to BackBase DB is unsuccessful");
			throw new Exception("Adding computer to BackBase DB is unsuccessful");
		}		
		
		return Retval;		
	}
}

