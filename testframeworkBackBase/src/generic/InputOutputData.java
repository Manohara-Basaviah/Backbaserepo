package generic;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class InputOutputData {

	private String inputFile;	
	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}
	
	// This method reads the driver file 
	public String readControlFile(String fieldname, String inputsheet, int mycnt) throws IOException  {
		
		File inputWorkbook = new File(inputFile);
		Workbook w;
		String cellData = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(inputsheet);
			Cell cell1 = sheet.getCell(3,0);
			//System.out.println(cell1.getContents());
			if (cell1.getContents().equals(fieldname)) {
				Cell nxtcell = sheet.getCell(3, mycnt);			
				cellData = nxtcell.getContents();							
			}				
		} catch (BiffException e) {
			e.printStackTrace();
		}
		return cellData;
		
	}	
	
	// This method reads the data from the first cell of input data sheet
	public String readColumnData(String fieldname, String inputsheet) throws IOException  {
		File inputWorkbook = new File(inputFile);
		Workbook w;
		String cellData = null;
		try {
			w = Workbook.getWorkbook(inputWorkbook);
			// Get the first sheet
			Sheet sheet = w.getSheet(inputsheet);
			// Loop over first 10 column and lines
			
			for (int j = 0; j < sheet.getColumns(); j++) {				
				Cell cell = sheet.getCell(j, 0);				
				if (cell.getContents().equals(fieldname)) {					
					cellData = sheet.getCell(j, 1).getContents();
					break;
				}				
			}		
		} catch (BiffException e) {
			e.printStackTrace();
		}		
		return cellData;
	}
	
	
	public String readConfigXML() {
		
		String Constants = "";
		try {						 
			File fXmlFile = new File("C:/BackBase_TA/Config/Config.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();		 
			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
					 
			NodeList nList = doc.getElementsByTagName("Client");		 
			//System.out.println("----------------------------");
		 
			for (int temp = 0; temp < nList.getLength(); temp++) {		 
				Node nNode = nList.item(temp);		 
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					if (!(((eElement.getElementsByTagName("Status").item(0).getTextContent()).equals("Completed"))
							|| ((eElement.getElementsByTagName("Status").item(0).getTextContent()).equals("completed")))) {
			 
							Constants = temp + " " + eElement.getAttribute("env") + " "  
									+ eElement.getElementsByTagName("UserID").item(0).getTextContent() + " "
									+ eElement.getElementsByTagName("Passwd").item(0).getTextContent() + " "
									+ eElement.getElementsByTagName("Browser").item(0).getTextContent() + " "
									+ eElement.getElementsByTagName("ExeMultipleUser").item(0).getTextContent();									
							break;											 
					}
				}
			}
		} catch (Exception e) {
		   	e.printStackTrace();
		}
		return Constants;
	}
	
	
	public void UpdateConfigXML(int iterXML) {		
		
		try {
			String filepath = "C:/BackBase_TA/Config/Config.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(filepath);
	 
			// Get the root element
			Node company = doc.getFirstChild();
	 
			// Get the staff element by tag name directly
			Node client = doc.getElementsByTagName("Client").item(iterXML);

			// loop the staff child node
			NodeList list = client.getChildNodes();	 
			for (int i = 0; i < list.getLength(); i++) {
	 
	           Node node = list.item(i);	 
			   // get the Status element, and update the value
			   if ("Status".equals(node.getNodeName())) {
				   node.setTextContent("Completed");
				   break;
			   }	 
			}
	 
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(filepath));
			transformer.transform(source, result);
	 
			//System.out.println("Updated XML file");	 
		} catch (ParserConfigurationException pce) {
			   pce.printStackTrace();
		} catch (TransformerException tfe) {
			   tfe.printStackTrace();
		} catch (IOException ioe) {
			   ioe.printStackTrace();
		} catch (SAXException sae) {
			   sae.printStackTrace();
		}		
	}
	
	
	public void CopyReportFile(String tstamp) throws IOException {
		
		InputStream inStream = null;
        OutputStream outStream = null;
        //Copy the report draft file from Config folder to BB_Reports folder
        try{ 
            File file1 =new File("C:/BackBase_TA/Config/SampleReport.xls");
            File file2 =new File("C:/BackBase_TA/Report & Logs/BackBase_Report_"+Initialization.mydate+"/BBReport_"+tstamp+".xls");
 
            inStream = new FileInputStream(file1);
            outStream = new FileOutputStream(file2); // for override file content
 
            byte[] buffer = new byte[1024]; 
            int length;
            while ((length = inStream.read(buffer)) > 0){
                outStream.write(buffer, 0, length);
            }
 
            if (inStream != null)inStream.close();
            if (outStream != null)outStream.close();        
        }catch(IOException e){
            e.printStackTrace();
        }       
      
	}	
		
	//Method to delete temp directories created by test automation
	public static boolean removeDirectory(File directory) {

		  // System.out.println("removeDirectory " + directory);
	
		  if (directory == null)
		    return false;
		  if (!directory.exists())
		    return true;
		  if (!directory.isDirectory())
		    return false;
	
		  String[] list = directory.list();
	
		  // Some JVMs return null for File.list() when the
		  // directory is empty.
		  if (list != null) {
		    for (int i = 0; i < list.length; i++) {
		      File entry = new File(directory, list[i]);
		      if (entry.isDirectory())
		      {
		        if (!removeDirectory(entry))
		          return false;
		      }
		      else
		      {
		        if (!entry.delete())
		          return false;
		      }
		    }
		  }
	
		  return directory.delete();
	}
	
	
	// Method to kill processe started by test automation - browser drivers
	public void KillautoProcess(String Browser) throws Exception {
		
		String OSbits = System.getProperty("sun.arch.data.model");			
		if (OSbits.equals("64")) { 
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer32.exe");
		} else  {
			Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer64.exe");
		}
	
		 Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
		
		//Kill all the process which are opened at this run
		Runtime.getRuntime().exec("taskkill /F /IM AcroRd32.exe");		
		
	}	
	
}


