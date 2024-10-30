package screens;

import org.testng.Assert;
import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AutoBlockSetUp_OPR031 extends CustomFunctions {
	public AutoBlockSetUp_OPR031(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "AutoBlockSetUp_OPR031";
	public String ScreenName = "Auto Block SetUp : OPR031";

	 /**
	  * @author A-9478
	  * Description... List by Block Type
	  * @throws Exception 
      */
	public void listByBlockType(String BlockType) throws Exception
    {          
         selectValueInDropdown(sheetName, "lst_BlockType;id", 
        data(BlockType), "Select Block Type", 
        "VisibleText");
         clickWebElement(sheetName, "btn_list;name", "List button", ScreenName);
         waitForSync(3); 
         
    }
	
	/**
	  * @author A-9478
	  * Description... Click Add to add auto block details
	  * @throws Exception 
     */
	public void clickAdd() throws Exception
   {          
       
        clickWebElement(sheetName, "id_Add;xpath", "Add button", ScreenName);
        waitForSync(3);          
   }
	
	/**
	  * @author A-9478
	  * Description... select block type
	  * @throws Exception 
    */
	public void selectBlockType(String BlockType) throws Exception
  {                
		selectValueInDropdown(sheetName, "lst_selectBlockType;xpath", 
		        data(BlockType), "Select Block Type", 
		        "VisibleText");
       waitForSync(3); 
       
  }
	/**
     * Description... Verify Auto block details
     * @author A-9478
     * @throws IOException 
      */
     public void verifyAutoBlockdetails(String pmKeyCol,String pmKey,int[] colVal,int[] colVal2,String[] actVal,String[]actVal2) throws IOException
     {
           verify_tbl_records_multiple_cols(sheetName, "table_AutoBlockDetails;xpath", pmKeyCol,pmKey,colVal,colVal2,actVal,actVal2,ScreenName);
           waitForSync(3);
     }
     /**
      * @author A-7271
      * @param verfCols
      * @param actVerfValues
      * @param pmKey
      * @throws InterruptedException
      * Desc : verifyAutoBlockdetails
      */
     public void verifyAutoBlockdetails(int verfCols[],String actVerfValues[],String pmKey)
			 throws InterruptedException {
	
		
			waitForSync(1);
			verify_tbl_records_multiple_cols_contains(sheetName, "table_AutoBlockDetails;xpath", "//td", verfCols, pmKey, actVerfValues);	
	}

	/**
	  * @author A-9478
	  * Description... select transaction
	  * @throws Exception 
   */
	public void selectTransaction(String trans) throws Exception
 {                
		selectValueInDropdown(sheetName, "lst_selectTransaction;xpath", 
		        data(trans), "Select transaction ", 
		        "VisibleText");
      waitForSync(3); 
      
 }
	
	/**
	  * @author A-9478
	  * Description: Click Add Paramters link 
	  * @throws Exception 
  */
	public void addParamters() throws Exception
{                
		 clickWebElement(sheetName, "btn_AddParamter;xpath", "Add paramters", ScreenName);
	     waitForSync(3);          
}
	

	/**
	  * @author A-9478
	  * Description: Select blocking parameter 
	  * @throws Exception 
  */
	public void selectBlockingParamter(String irregularityCode, String transactionCode) throws Exception
{               
		 switchToWindow("storeParent");
		 switchToWindow("child");
		 clickWebElement(sheetName, "btn_IrregularityCodeLOV;xpath", "Irregularity code LOV", ScreenName);
	     waitForSync(2);
	     switchToFrame("default");	    
		 switchToWindow("multipleWindows");
	     enterValueInTextbox(sheetName, "inbx_IrregularityCode;id", data(irregularityCode), "Irregularity Code", ScreenName);
	     clickWebElement(sheetName, "btn_ListIrregularityCode;xpath", "List", ScreenName);
	     waitForSync(2);
	     String locator = xls_Read.getCellValue(sheetName, "chbx_selectByTransactionCode;xpath");
	     locator = locator.replace("TRANS", data(transactionCode));
	     try
	     {
	    	 driver.findElement(By.xpath(locator)).click();
	    	 writeExtent("Pass", "Selected "+data(transactionCode)+" in "+ ScreenName + " Page");
	     }
	     catch(Exception e)
	     {
	    	 writeExtent("Fail", "Couldn't select "+data(transactionCode)+" in "+ ScreenName + " Page");
	     }
	     clickWebElement(sheetName, "btn_OK;xpath", "OK", ScreenName);
	     waitForSync(2);
	     switchToWindow("multipleWindows");
	     clickWebElement(sheetName, "btn_clickOk;xpath", "Ok", ScreenName);
	     switchToWindow("getParent");
         switchToFrame("default");
         switchToFrame("contentFrame", "OPR031");
}
	
	/**
	  * @author A-9478
	  * Description... select release transaction
	  * @throws Exception 
  */
	public void selectReleaseTransaction(String trans) throws Exception
{                
		selectValueInDropdown(sheetName, "lst_selectReleaseTrasaction;xpath", 
		        data(trans), "Select release transaction ", 
		        "VisibleText");
     waitForSync(2);      
}
	
	/**
	  * @author A-9478
	  * Description: Click Save
	  * @throws Exception 
 */
	public void clickSave() throws Exception
{                
		 clickWebElement(sheetName, "btn_Save;id","Save", ScreenName);
	     waitForSync(3);          
}
	
	/**
	 * Description... Verify Auto block details
	 * @author A-9478
	 * @throws IOException 
	 */
	public void verifyAutoBlockdetails(int verfCols[], String actVerfValues[]) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_AutoBlockDetails;xpath", "//td", verfCols, data("BlockType"),
				actVerfValues);
		waitForSync(3);
	}

           
           
	
}