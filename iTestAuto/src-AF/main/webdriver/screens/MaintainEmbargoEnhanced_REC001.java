
package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainEmbargoEnhanced_REC001 extends CustomFunctions {

	public MaintainEmbargoEnhanced_REC001(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	
	}

	

	public String sheetName = "MaintainEmbargoEnhanced_REC001";
	public String screenName = "MaintainEmbargoEnhanced";
	String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
	/**
	 * Description... UnCheck applicable Transactions
	 * @param visibletext
	 * @param index
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void applicableTransactions(String visibletext, String [] index) throws InterruptedException, IOException 
	{		
		
		
		clickWebElement(sheetName, "btn_Select;xpath", "Select dropdown ", screenName);
		clickWebElement(sheetName, "lnk_UnCheckAll;xpath", "UnCheck All ", screenName);
		
		  switch (visibletext) {	
			
			case "check_all":
				clickWebElement(sheetName, "lnk_CheckAll;xpath", "Check All ", screenName); 
				break; 
				
			case "uncheck_all":
				clickWebElement(sheetName, "lnk_UnCheckAll;xpath", "UnCheck All ", screenName); 
				break;
				
			case "index" :
				for(int i = 0 ; i<index.length ;i++)
				{
				String dynxapth = "//*[@id='ui-multiselect-0-CMP_Reco_Defaults_MaintainEmbargo_ApplicableTransactions-option-"+index[i]+"']";
				driver.findElement(By.xpath(dynxapth)).click();
				}
				
		  }
		
	}
/**
 * Description... 	Enter To From Date
 * @param fromDate
 * @param toDate
 * @throws InterruptedException
 * @throws AWTException
 */
	public void enterToFromDate (String fromDate, String toDate)throws InterruptedException, AWTException {

		enterValueInTextbox(sheetName, "FromDate_field;xpath", fromDate,
				"From Date", screenName);
		enterValueInTextbox(sheetName, "ToDate_Field;xpath", toDate, "To Date",
				screenName);
		keyPress("TAB");
		keyRelease("TAB");

	}
	
/**
 * Description... 	Select Category
 * @param Category
 * @throws InterruptedException
 */
	public void selectCategory(String Category)throws InterruptedException 
	{
		selectValueInDropdown(sheetName, "lst_Category;xpath", Category, "Category", "VisibleText");
		waitForSync(2);
	}
/**
 * Description... 	Select Level
 * @param Level
 * @throws InterruptedException
 */
	public void selectLevel(String Level)throws InterruptedException
	{
		selectValueInDropdown(sheetName, "lst_Level;xpath", Level, "Level", "VisibleText");
		waitForSync(2);			
	}
/**
 * Description... 	Select compliance Type
 * @param complianceType
 * @throws InterruptedException
 */
	public void selectcomplianceType(String complianceType)throws InterruptedException
	{
		selectValueInDropdown(sheetName, "lst_ComplianceType;xpath", complianceType, "ComplianceType", "VisibleText");
		waitForSync(2);	
	}
/**
 * Description... 	Add Geographic Level
 * @param geographicLevel
 * @param geographicLevelType
 * @param applicable
 * @param values
 * @throws InterruptedException
 * @throws IOException 
 */
	public void AddGeographicLevel(String geographicLevel,String geographicLevelType,String applicable,String values) throws InterruptedException, IOException{
		clickWebElement("MaintainEmbargoEnhanced_REC001", "lnk_addGeographic;xpath", "Add Button", screenName);
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_geographicLevel;xpath", geographicLevel, "geographicLevel", "VisibleText");
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_geographicLevelType;xpath", geographicLevelType, "geographicLevelType", "VisibleText");
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_applicable;xpath", applicable, "applicable", "VisibleText");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_values;xpath", values,"values", screenName);
		
	}
/**
 * Description... 	Select Days Of Week
 * @param days
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectDaysOfWeek(String days)throws InterruptedException, IOException
	{	
		switch (days) {
		case "All":
		clickWebElement(sheetName, "checkbox_all;xpath", "All", screenName); 
		waitForSync(2);
		break;
		case "Mon":
		clickWebElement(sheetName, "checkbox_mon;xpath", "Mon", screenName); 
		waitForSync(2);
		break;
		case "Tue":
		clickWebElement(sheetName, "checkbox_tue;xpath", "Tue", screenName); 
		waitForSync(2);
		break;
		case "Wed":
		clickWebElement(sheetName, "checkbox_wed;xpath", "Wed", screenName); 
		waitForSync(2);
		break;
		case "Thu":
		clickWebElement(sheetName, "checkbox_thu;xpath", "Thu", screenName); 
		waitForSync(2);
		break;
		case "Fri":
		clickWebElement(sheetName, "checkbox_fri;xpath", "Fri", screenName); 
		waitForSync(2);
		break;
		case "Sat":
		clickWebElement(sheetName, "checkbox_sat;xpath", "Sat", screenName); 
		waitForSync(2);
		break;
		case "Sun":
		clickWebElement(sheetName, "checkbox_sun;xpath", "Sun", screenName); 
		waitForSync(2);
		break;
		}
		
		
	}
/**
 * Description... 	Select Applicable On	
 * @param ApplicableOn
 * @throws InterruptedException
 */
		public void selectApplicableOn(String ApplicableOn)throws InterruptedException
		{
			selectValueInDropdown(sheetName, "lst_ApplicableOn;xpath", ApplicableOn, "ApplicableOn", "VisibleText");
			waitForSync(2);			
		}		
		
	/**
	 * Description... Enter Remarks	
	 * @param Remarks
	 * @throws InterruptedException
	 */
		public void enterRemarks(String Remarks)throws InterruptedException
		{
			enterValueInTextbox(sheetName, "textarea_Remarks;xpath", Remarks,"Remarks", screenName);
			waitForSync(2);			
		}
/**
 * Description... Enter Description		
 * @param Description
 * @throws InterruptedException
 */
		public void enterDescription(String Description)throws InterruptedException
		{
			enterValueInTextbox(sheetName, "textarea_Description;xpath", Description,"Remarks", screenName);
			waitForSync(2);			
		}	
		
/**
 * Description... Add Embargo Details		
 * @param Parameter
 * @param EmbargoApplicable
 * @param Commodity
 * @throws InterruptedException
 * @throws IOException 
 */
		public void AddEmbargoDetails(String Parameter,String EmbargoApplicable,String Commodity) throws InterruptedException, IOException{
			clickWebElement("MaintainEmbargoEnhanced_REC001", "lnk_addEmbargoDetails;xpath", "Add Button", screenName);
			waitForSync(2);
			selectValueInDropdown(sheetName, "lst_Parameter;xpath", Parameter, "Parameter", "VisibleText");
			waitForSync(2);
			selectValueInDropdown(sheetName, "lst_EmbargoApplicable;xpath", EmbargoApplicable, "EmbargoApplicable", "VisibleText");
			waitForSync(2);		
			enterValueInTextbox(sheetName, "inbx_Commodity;xpath",Commodity,"Commodity", screenName);
			waitForSync(2);	
			
		}
/**
 * Description... Click Save Button
 * @throws InterruptedException
 * @throws IOException 
 */
		public void clickSaveButton() throws InterruptedException, IOException{
			clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		
		}
		

	
}