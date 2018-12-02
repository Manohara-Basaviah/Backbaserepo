package generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//import modules.BackBaseSignInOut;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class Log {

	//protected String dirPath;
	public String localIp;
	//protected String host;
	
	public Log(String ip) {		
		this.localIp = ip;
		//this.host = host;		
	}
	
	
	void createFold(String path, String mydate) throws InvalidFormatException, IOException {	
		File dir = new File(path+"/BackBase_Report_"+mydate);
        if(dir.exists()){
        	//logLine("", false, "A folder with name "+mydate +" is already exist in the path "+path);
        	
        	File Subdir1 = new File(path+"/BackBase_Report_"+mydate+"/Logs");
        	if (!(Subdir1.exists())){
        		Subdir1.mkdir();        		
        	}
        	File Subdir2 = new File(path+"/BackBase_Report_"+mydate+"/Failure_Screenshots");
        	if (!(Subdir2.exists())){
        		Subdir2.mkdir();        		
        	}

        }else{
        	dir.mkdir();
        	//logLine("", false, "A folder with name "+mydate +" has been created in the path "+path);
        	
        	File Subdir1 = new File(path+"/BackBase_Report_"+mydate+"/Logs");
        	if (!(Subdir1.exists())){
        		Subdir1.mkdir();        		
        	}
        	File Subdir2 = new File(path+"/BackBase_Report_"+mydate+"/Failure_Screenshots");
        	if (!(Subdir2.exists())){
        		Subdir2.mkdir();        		
        	}
        }	
	}
	
	public void logLine(String Testname, boolean err, String message) throws IOException, InvalidFormatException {
		
		String vars = new String(Testname);
		String[] arrsh = vars.split("\\.");
		String Status = null;
		String localip = null;
		SimpleDateFormat gmtDateFormat = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat gmtTimeFormat = new SimpleDateFormat("HH:mm:ss");
		gmtDateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
		//Current Date Time in IST
		String CurDate = gmtDateFormat.format(new Date());
		String CurTime = gmtTimeFormat.format(new Date());
		Status =  (err ? "ERROR" : "INFO");
		localip =  localIp ;	
	
	 	InputStream inp = new FileInputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/BBReport_"+Initialization.myTimestamp+".xls");  
	    Workbook wb = WorkbookFactory.create(inp);
	    Sheet sheet = null;
	    if (Testname.equals("")) {    	
	    	sheet = wb.getSheetAt(1);
	    } else if (!(Testname.equals(""))) {    	
	    	sheet = wb.getSheet(arrsh[1]+"_Log");
	    }
	    
	    int RCnt = sheet.getPhysicalNumberOfRows();	    
	    Row row = sheet.createRow((RCnt-1)+1);	   
	    
	    Cell cell1 = row.getCell(0);  
	    if (cell1 == null)  {
	        cell1 = row.createCell(0);
	        //cell1.setCellType(Cell.CELL_TYPE_STRING);  
		    cell1.setCellValue(CurDate);
	    }
	    
	    Cell cell2 = row.getCell(1);  
	    if (cell2 == null)  {
	        cell2 = row.createCell(1);
	        //cell2.setCellType(Cell.CELL_TYPE_STRING);  
		    cell2.setCellValue(CurTime);
	    }
	    
	    Cell cell3 = row.getCell(2);  
	    if (cell3 == null)  {
	        cell3 = row.createCell(2);
	        //cell3.setCellType(Cell.CELL_TYPE_STRING);  
		    cell3.setCellValue(Status);
		    if (Status.equals("ERROR")) {
		    	//Change the cell color to Red since its failed
		        CellStyle style = wb.createCellStyle();
		        style.setFillForegroundColor(IndexedColors.RED.getIndex());
		        //style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		        cell3.setCellStyle(style);		    	
		    }	        
	    }	    
	    
	    Cell cell4 = row.getCell(3);  
	    if (cell4 == null)  {
	        cell4 = row.createCell(3);
	        //cell4.setCellType(Cell.CELL_TYPE_STRING);  
		    cell4.setCellValue(localip);
	    }
	    
	    Cell cell5 = row.getCell(4);  
	    if (cell5 == null)  {
	        cell5 = row.createCell(4);
	        //cell2.setCellType(Cell.CELL_TYPE_STRING);  
		    cell5.setCellValue(message);
	    }	      
	  
	    // Write the output to a file  
	    FileOutputStream fileOut = new FileOutputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/BBReport_"+Initialization.myTimestamp+".xls");  
	    wb.write(fileOut);  
	    fileOut.close();
	}	
	
	
	/*public void updateTestSummary() throws IOException, InvalidFormatException {
				
	
	 	InputStream inp = new FileInputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/BBReport_"+Initialization.myTimestamp+".xls");  
	    Workbook wb = WorkbookFactory.create(inp);
	    // Zero shows that the first sheet - Test Summary
	    Sheet sheet = wb.getSheetAt(0);	    
	    int RCnt = sheet.getPhysicalNumberOfRows();   
	    
	    for (int k = 0; k < (RCnt-1); k++) {
	    	Row row = sheet.getRow(k);	    	    	 
	    	//Cell cell = row.getCell(1, Row.RETURN_BLANK_AS_NULL);
	    	
	    	if (cell == null) {
	    	}else {
		    	String par = cell.getStringCellValue();
		    	
			    if (par.equals("Date"))  {
			        row.getCell(2).setCellValue(Initialization.CurDateTime);		        
			    } else if (par.equals("Test Environement"))  {
			        row.getCell(2).setCellValue(Initialization.EnvirSite);		        
			    } else if (par.equals("UserID"))  {
			    	if (Initialization.AutoMultipleUser.equalsIgnoreCase("yes")) {
			    		row.getCell(2).setCellValue(BackBaseSignInOut.Uname);
			    	}else {
			    		row.getCell(2).setCellValue(Initialization.UserID);
			    	}
			    } else if (par.equals("Browser"))  {
			        row.getCell(2).setCellValue(Initialization.Browser);
			    } else if (par.equals("Test Server"))  {
			        row.getCell(2).setCellValue(Initialization.ServerName);		    
			    } else if (par.equals("Total Execution Time"))  {
			        row.getCell(2).setCellValue(Initialization.totalTime+" mins");		        
			    }    
	    	}
	    }    	    	 	  
	    // Write the output to a file  
	    FileOutputStream fileOut = new FileOutputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/BBReport_"+Initialization.myTimestamp+".xls");  
	    wb.write(fileOut);  
	    fileOut.close();
	}
	
	
	public void updateTestResults(String Test, String status) throws IOException, InvalidFormatException {	
	
	 	InputStream inp = new FileInputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/BBReport_"+Initialization.myTimestamp+".xls");  
	    Workbook wb = WorkbookFactory.create(inp);
	    
	    // Zero shows that the first sheet - Test Summary
	    Sheet sheet = wb.getSheetAt(0);	    
	    int RCnt = sheet.getPhysicalNumberOfRows();   	    
	    
	    //Change the cell color to Green when it is Passed
        CellStyle Passstyle = wb.createCellStyle();
        Passstyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        //Passstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //Passstyle.setBorderRight(CellStyle.BORDER_THIN);
        //Passstyle.setBorderBottom(CellStyle.BORDER_THIN);
        
        //Change the cell color to Red when it is Failed
        CellStyle Failstyle = wb.createCellStyle();
        Failstyle.setFillForegroundColor(IndexedColors.RED.getIndex());
        //Failstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        //Failstyle.setBorderRight(CellStyle.BORDER_THIN);
        //Failstyle.setBorderBottom(CellStyle.BORDER_THIN);
        
        //Change the cell color to Green when it is Skipped
        CellStyle Skipstyle = wb.createCellStyle();
        Skipstyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        //Skipstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);        
        //Skipstyle.setBorderRight(CellStyle.BORDER_THIN);
        //Skipstyle.setBorderBottom(CellStyle.BORDER_THIN);	    
	    
	    for (int k = 0; k < (RCnt-1); k++) {
	    	Row row = sheet.getRow(k);	 
	    	//System.out.println("Row data "+RCnt +" k vari "+k  );
	    	Cell cell = " "; row.getCell(1, Row.RETURN_BLANK_AS_NULL);
	    	
	    	if (cell == null) {
	    	}else {
		    	String par = cell.getStringCellValue();
		    	
			    if (par.equals("Test Scenarios Executed"))  {
			    	Cell cell2 = row.getCell(2);
			    	double totcnt = cell2.getNumericCellValue();		    	
			        row.getCell(2).setCellValue(totcnt+1);		   
			        
			    } else if ((par.equals("Test Scenario Passed")) && (status.equals("Pass")))  {
			    	Cell cell2 = row.getCell(2);
			    	double pascnt = cell2.getNumericCellValue();		    	
			        row.getCell(2).setCellValue(pascnt+1);		
			        row.getCell(2).setCellStyle(Passstyle);
			        
			    } else if ((par.equals("Test Scenario Failed")) && (status.equals("Fail")))  {		    	
			    	Cell cell2 = row.getCell(2);
			    	double failcnt = cell2.getNumericCellValue();		    	
			        row.getCell(2).setCellValue(failcnt+1);	        
			        row.getCell(2).setCellStyle(Failstyle);		
			        
			    } else if ((par.equals("Test Scenario Skipped")) && (status.equals("Skip")))  {
			    	Cell cell2 = row.getCell(2);
			    	double skpcnt = cell2.getNumericCellValue();	    	
			        row.getCell(2).setCellValue(skpcnt+1);
			        row.getCell(2).setCellStyle(Skipstyle);
			    }	    
			    
			    if (par.equals(Test))  {
			    	if (status.equals("Pass")) {
			    		row.getCell(2).setCellValue(status);		    		
				        row.getCell(2).setCellStyle(Passstyle);
			    	} else if (status.equals("Fail")) {
			    		row.getCell(2).setCellValue(status);		    		
				        row.getCell(2).setCellStyle(Failstyle);
			    	} else if (status.equals("Skip")) {
			    		row.getCell(2).setCellValue(status);		    		
				        row.getCell(2).setCellStyle(Skipstyle);
			    	}
			    }
	    	}	
	    }    	    	 	  
	    // Write the output to a file  
	    FileOutputStream fileOut = new FileOutputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/BBReport_"+Initialization.myTimestamp+".xls");  
	    wb.write(fileOut);  
	    fileOut.close();
	}*/
	
	
	public void updateCleanupData(String type, String nametype) throws IOException, InvalidFormatException {
	
	 	InputStream inp = new FileInputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/Logs/TDCleanUp_"+Initialization.myTimestamp+".xls");  
	    Workbook wb = WorkbookFactory.create(inp);  
	    Sheet sheet = wb.getSheetAt(0);	    
	    int RCnt = sheet.getPhysicalNumberOfRows();
	    
	    Row row = sheet.getRow(0);
	    int k;
	    for (k = 0; k < 7; k++) {
	    	String typeStr = row.getCell(k).getStringCellValue();
	    	if ((!typeStr.isEmpty()) && (typeStr.equals(type))) {
	    		break;	    
	    	}
	    }	    
	    row = sheet.createRow((RCnt-1)+1);
		Cell cell = row.getCell(k);  
	    if (cell == null)  {
	        cell = row.createCell(k);
	        //cell.setCellType(Cell.CELL_TYPE_STRING);  
		    cell.setCellValue(nametype);		 
	    }                
	  
	    // Write the output to a file  
	    FileOutputStream fileOut = new FileOutputStream("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/Logs/TDCleanUp_"+Initialization.myTimestamp+".xls");  
	    wb.write(fileOut);  
	    fileOut.close();
	}
	
}
