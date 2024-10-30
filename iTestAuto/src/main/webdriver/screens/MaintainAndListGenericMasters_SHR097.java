package screens;

import java.io.File;
import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainAndListGenericMasters_SHR097 extends CustomFunctions {
	String sheetName = "ListGenericMasters_SHR097";
	String screenName = "MaintainAndListGenericMasters_SHR097";

	public MaintainAndListGenericMasters_SHR097(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
/**
 * Description... Select Master Type
 * @param masterType
 * @throws InterruptedException
 * @throws IOException 
 * @throws AWTException 
 */
public void  selectMasterType(String masterType) throws InterruptedException, IOException, AWTException
	{
		
	selectValueInDropdown(sheetName, "lst_masterType;name", data(masterType), "master Type", "VisibleText");
	waitForSync(1);	
	deleteFile(data(masterType));
	waitForSync(3);	
	clickWebElement(sheetName, "btn_export;xpath", "Export Button", screenName);
	waitForSync(8);
	saveDocument(data(masterType)+".xls");

	}
/**
 * @author A-7271
 * @param tcName
 * @throws AWTException
 * @throws InterruptedException
 * Desc : save document
 */
public void saveDocument(String fileName) throws AWTException, InterruptedException
{
	try
	{
		String filePath=System.getProperty("user.dir")+"\\src\\resources\\Downloads\\";
		deleteFileIfPresent(filePath,fileName);
		waitForSync(2);
		saveActionInRobot();
		waitForSync(5);
		copyContentsWithClipboard(filePath+fileName);
		pasteActionInRobot();
		waitForSync(2);
		keyPress("ENTER");
		waitForSync(1);
		writeExtent("Pass","File '"+fileName+"' generated and stored in "+filePath);

	}

	catch(Exception e)
	{
		writeExtent("Fail","Error in saving the file "+fileName);
	}

}

/**
 * @author A-10690
 * Description...retreive warehouse code with respect to the agent
 * @param agent
 * @throws Exception 
 * @throws InterruptedException 
 */
public String retrieveTSDvalue(String agent) throws InterruptedException, Exception{

	String locator=xls_Read.getCellValue(sheetName,"txt_tsdvalue;xpath");
	locator=locator.replace("*", data(agent));
	String actText=driver.findElement(By.xpath(locator)).getText().trim();
	 return actText;
}

/**
 * @author A-9844
 * @param fileName
 * @param rule
 * @param airport
 * @return
 * Desc : get Next Forwarding Zone  from Rule Atr Master excel
 */
public String getNextForwardingZone(String fileName,String rule,String airport)
{
	String nextForwardingZone="";
	String HA="";
	boolean nextForwardingZoneFound=false;
	try
	{

		FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\resources\\Downloads\\"+fileName+".xls"));

		HSSFWorkbook workbook =new HSSFWorkbook(file);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Row row=null;

		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			row = sheet.getRow(rowIndex);
			if (row != null) {

				//Airport
				Cell cell = row.getCell(0);

				//Rule
				Cell cell2 = row.getCell(1);



				if( cell.getStringCellValue().equals(airport)&& cell2.getStringCellValue().equals(rule))
				{
					//fetch Next forwarding zone
					Cell cell3=row.getCell(26);
					nextForwardingZone=cell3.getStringCellValue();
					//fetch HA
					Cell cell4=row.getCell(24);
					HA=cell4.getStringCellValue();
					map.put("HandlingArea", HA);



					writeExtent("Pass", "Next Forwarding zone is retreived from the Rule Atr Master excel as "+nextForwardingZone+ " on "+screenName);
					nextForwardingZoneFound=true;
					return nextForwardingZone;

				}

			}



		}

		if(nextForwardingZoneFound==false)
		{
			writeExtent("Fail", "No next forwarding zone based on the passed paramemeter on "+ screenName);
		}
	}	
	catch(Exception e)
	{
		writeExtent("Fail", "Next forwarding zone cannot be retreived from the Rule Atr Master excel on "+ screenName);
		return nextForwardingZone ;

	}
	return nextForwardingZone;
}




/**
 * Description... Select Master Type without exporting
 * @param masterType
 * @throws InterruptedException
 * @throws IOException 
 */
public void  selectMasterTypeWithoutExport(String masterType) throws InterruptedException, IOException
	{
		
	selectValueInDropdown(sheetName, "lst_masterType;name", data(masterType), "master Type", "VisibleText");
	waitForSync(2);
	
	}


/**
 * @author A-7271
 * @param sccCodes
 * @param hm
 * @return
 * Desc : sort the SCCs based on rank / priority
 */
public String[] sortSCCs(String sccCodes[],HashMap<Integer,String> hm)

{
	
	String[] sccs=new String[sccCodes.length];

	String sortedSccs[]=new String[sccCodes.length];
	try
	{


		TreeMap<Integer,String> tm=new  TreeMap<Integer,String> (hm);  
		Iterator itr=tm.keySet().iterator();    
		int count=0; 

		while(itr.hasNext())    
		{    
			int key=(int)itr.next();  
			sccs[count]=hm.get(key);
			count++;
		}    

		for(int i=0;i<count;i++)
		{


			sortedSccs[i]=sccs[i];

		}
		return sortedSccs;
	}

	catch(Exception e)
	{
		onFailUpdate(screenName, "", "","Sorting of SCCs","Sorting of SCCs");
				
		return sortedSccs;
	}


}
/**
 * @author A-7271
 * @param fileName
 * @throws FileNotFoundException
 * Desc : delete files
 */
public void deleteFile(String fileName) throws FileNotFoundException
{
	try
	{
	  File file  = new File(System.getProperty("user.dir") + "\\src\\resources\\Downloads\\"+fileName+".xls");
	  if(file.exists())
	  {
		  file.delete();
	  }
	}
	
	catch(Exception e)
	{
		
	}
	  
    
	 
	
}
/**
 * @author A-7271
 * @param fileName
 * @param sccCode
 * @param airport
 * @return
 * Desc : get Shipment rank from shipment rank master excel
 */
public int getShipmentRank(String fileName,String sccCode,String airport)
{
	 int rank=0;
	 boolean rankEntryFound=false;
	try
	{
	
     FileInputStream file = new FileInputStream(new File(System.getProperty("user.dir") + "\\src\\resources\\Downloads\\"+fileName+".xls"));
	 
	 HSSFWorkbook workbook =new HSSFWorkbook(file);
	 HSSFSheet sheet = workbook.getSheetAt(0);
	 
	 
	
	 Row row=null;
	
	 for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
		  row = sheet.getRow(rowIndex);
		  if (row != null) {
			  
			  //SCC Code
		    Cell cell = row.getCell(2);
		   
		    
		    //Airport
		    Cell cell2 = row.getCell(16);
		    
		  
		    
		    
		    
		   
		    	if( cell.getStringCellValue().equals(sccCode)&& cell2.getStringCellValue().equals(airport))
		    	{
		    		Cell cell3=row.getCell(0);
		    		rank=Integer.parseInt(cell3.getStringCellValue());
		    		writeExtent("Pass", "Rank is retreived from the Shipment rank master excel as "+rank+ "on "+screenName);
		    		rankEntryFound=true;
		    		return rank ;
		    		
		    	}
		   
		}
	 
	
	
	}
	 
	 if(rankEntryFound==false)
	 {
		 writeExtent("Fail", "No rank entry based on the passed paramemeter "+ screenName);
	 }
	}	
	catch(Exception e)
	{
		writeExtent("Fail", "Rank cannot be retreived from the shipment rank master excel on "+ screenName);
		return rank ;
		
	}
	return rank;
}

	

/**
 * @author A-8783
 * Description...verify Column name
 * @throws Exception 
 * @throws InterruptedException 
 */
public void verifyColumnPresent(String columnName) throws InterruptedException, Exception{

	String locator=xls_Read.getCellValue(sheetName,"txt_columnName;xpath");
	locator=locator.replace("*", columnName);
	String actText=driver.findElement(By.xpath(locator)).getText();
	 verifyScreenText(screenName, columnName, actText, "ColumnName", "ColumnName");
}

}

