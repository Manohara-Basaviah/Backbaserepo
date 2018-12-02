package generic;

import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.Test;

import testcases.TestS_001;
import testcases.TestS_002;
import testcases.TestS_003;
import testcases.TestS_004;
import testcases.TestS_005;

public class TestcaseSuite extends Initialization {
	
	
	@Test(enabled=true)
	public void Test01_Verify_Creation_Of_NewComputer_In_Application() throws Exception {
		if (!testval){
			throw new SkipException("Skipping Test01 - User opted to skip this test ");
		} else {		
			TestS_001 clasInst = new TestS_001(driver, log);
			Boolean retVal = clasInst.test1();		
			if (retVal) {
				Reporter.log("Report Viewer -  new computer is addition to Database is successful");		
			} else {
				Reporter.log("Report Viewer -  new computer is addition to Database is unsuccessful");	
			}
		}
	}
	
	@Test(enabled=true)
	public void Test02_Verify_Modification_Of_Existing_ComputerDetails() throws Exception {
		if (!testval){
			throw new SkipException("Skipping Test02 - User opted to skip this test ");
		} else {		
			TestS_002 clasInst = new TestS_002(driver, log);
			Boolean retVal = clasInst.test2();		
			if (retVal) {
				Reporter.log("Report Viewer - Modificaiton of existing computer details is successful");			
			} else {
				Reporter.log("Report Viewer - Modificaiton of existing computer details is unsuccessful");			
			}
		}
	}
	
	@Test(enabled=true)	 
	public void Test03_Verify_Validation_Of_Existing_ComputerDetails() throws Exception {
		if (!testval){
			throw new SkipException("Skipping Test03 - User opted to skip this test ");
		} else {		
			TestS_003 clasInst = new TestS_003(driver, log);
			Boolean retVal = clasInst.test3();		
			if (retVal) {
				Reporter.log("Report Viewer - Validation of existing computer details is successful");			
			} else {
				Reporter.log("Report Viewer - Validation of existing computer details is unsuccessful");			
			}
		}
	}
	
	@Test(enabled=true)
	public void Test04_Verify_Deletion_Of_Existing_ComputerDetails() throws Exception {
		if (!testval){
			throw new SkipException("Skipping Test04 - User opted to skip this test ");
		} else {		
			TestS_004 clasInst = new TestS_004(driver, log);
			Boolean retVal = clasInst.test4();		
			if (retVal) {
				Reporter.log("Report Viewer - Deletion of existing computer details is successful");		
			} else {
				Reporter.log("Report Viewer - Deletion of existing computer details is unsuccessful");	
			}
		}
	}	
	
	@Test(enabled=true)
	public void Test05_Verify_Search_Of_Computer_With_UniqueName() throws Exception {
		if (!testval){
			throw new SkipException("Skipping Test05 - User opted to skip this test ");
		} else {		
			TestS_005 clasInst = new TestS_005(driver, log);
			Boolean retVal = clasInst.test5();		
			if (retVal) {
				Reporter.log("Report Viewer - Searching the computer unique name is successful");			
			} else {
				Reporter.log("Report Viewer - Searching the computer unique name is unsuccessful");			
			}
		}
	}
	
	
}

