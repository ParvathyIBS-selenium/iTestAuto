package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ListDiscrepancies_OPR050 extends CustomFunctions  {

	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName="ListDiscrepancies_OPR050";
	String sheetName1="ListFlightDiscrepancy_OPR047";
	String ScreenName="List Discrepancies";
	String screenId="OPR050";


	public ListDiscrepancies_OPR050(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction=new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
	/**
	 * Description... Click Damage Tab
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickDamageTab() throws InterruptedException, IOException{
		clickWebElement(sheetName, "tab_damage;xpath", "Damage Tab", ScreenName);
		waitForSync(3);
	}
	/**
	 * Description... Enter From Date
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterFromDate() throws InterruptedException, AWTException{
              enterValueInTextbox(sheetName, "inbx_fromDate;xpath", "-1", "From Date", ScreenName);
              keyPress("TAB");
              waitForSync(3);
              keyRelease("TAB");
              waitForSync(3);
       }
/**
 * Description... Click List Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickListButton() throws InterruptedException, IOException{
		clickWebElement("Generic_Elements", "btn_list;name", "List Button", ScreenName);
		waitForSync(5);
	}
	/**
	 * Description : Enter Carrier code, flight no,flight date and click on list
	 * @author A-10690
	 * @throws nterruptedException, AWTException, IOException
	 */
	public void listByFlight(String carrierCode,String flightNo,String flightDate) throws InterruptedException, AWTException, IOException
	{
		enterValueInTextbox(sheetName1, "inbx_carrierCode;id", data(carrierCode), "Carrier code", ScreenName);
		waitForSync(2);
		enterValueInTextbox(sheetName1, "inbx_flightNo;id", data(flightNo), "Flight No", ScreenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(flightDate), "Flight Date", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;id", "List button", ScreenName);
		waitForSync(5);
		
	}

	/**
	 * @author A-9847
	 * @Des To verify Discrepancy details based on primary key given
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmkey
	 * @throws IOException
	 */
	public void verifyDiscrepancydetails(int verfCols[], String actVerfValues[],String pmkey[]) throws IOException
	{	
		try{
			for(int i=0;i<pmkey.length;i++){
				String Pkey=data(pmkey[i]);
				String actVerfValue[]={actVerfValues[i]};
				verify_tbl_records_multiple_cols(sheetName, "table_AWB_FSWR;xpath", "//td", verfCols, Pkey,actVerfValue);
				waitForSync(3);
			}
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the discrepancy details on " +sheetName);
		}


	}	


	/**
	 * @author A-9847
	 * @Des To click a particular checkbox based on primary key given
	 * @param pmKey
	 */
	public void clickCheckbox(String pmKey){	

		selectTableRecord(pmKey, "chk_selectShipment;xpath", sheetName, 1);
		waitForSync(1);

	}
	
	/**
	 * @author A-9847
	 * @Des To select the given Category(High/Low/Medium)
	 * @param categoryValue
	 */

	public void selectCategory(String categoryValue){

		selectValueInDropdown(sheetName, "drpdn_selectCategory;name", categoryValue, "Select Category dropdown", "VisibleText"); 
		waitForSync(3);
	}



	/**
	 * @author A-9847
	 * @Desc To verify the Category Filter
	 * @param opt
	 * @param category
	 * @param count
	 */
	public void verifyCategoryFilter(boolean opt,String category,int count){

		String locator=xls_Read.getCellValue(sheetName, "table_disCode;xpath");
		locator=locator.replace("*",data(category));	
		int categorycount = driver.findElements(By.xpath(locator)).size();	 

		if(opt){
			try{

				if(categorycount==count)				
					writeExtent("Pass", "Shipments with Category as "+data(category)+ " sucessfully filtered on " +sheetName);					
				else					
					writeExtent("Fail", "Shipments with Category as "+data(category)+ " are not filtered on " +sheetName);


			}catch(Exception e){
				writeExtent("Fail", "Cannot find the shipments filtered on" +sheetName);
			}

		}

		else{			
			try{			        	       
				if(categorycount==count)
					writeExtent("Pass", "Shipments with Category as "+data(category)+ " are not filtered on " +sheetName);

				else
					writeExtent("Fail", "Shipments with Category as "+data(category)+ " got filtered " +sheetName);


			}catch(Exception e){
				writeExtent("Fail", "Cannot find the shipments filtered on" +sheetName);
			}
		}

	}
	
	/**
	 * @author A-9847
	 * @Desc To click on print button
	 * @throws Exception
	 */

	public void clickPrint() throws Exception
	{                              
		clickWebElement(sheetName, "btn_Print;id", "Print", ScreenName);
		waitForSync(3);
	}


	/**
	 * @author A-9847
	 * @Des To sort the Category Column and verify sorting order
	 * @param SortOrder
	 */
	public void sortCategoryColumnandVerify(String SortOrder[]){

		try{  	  
			clickWebElement(sheetName, "table_categoryColumn;xpath", "Category Column", ScreenName);
			waitForSync(3);
			String locator=xls_Read.getCellValue(sheetName, "table_categoryCode;xpath");		 
			List <WebElement> elements=driver.findElements(By.xpath(locator));

			for(int i=0;i<elements.size();i++){
				if(elements.get(i).getText().trim().equals(SortOrder[i]))

					writeExtent("Pass", "Sucessfully verified "+SortOrder[i]+" in the Sorting Order based on Category on " + ScreenName);  
				else

					writeExtent("Fail", "Failed to verify "+SortOrder[i]+" in the Sorting Order based on Category on " + ScreenName);   
			}

		}catch(Exception e){

			writeExtent("Fail"," Sorting Order based on Category not maintained on "+ScreenName);

		}		

	} 

	
	/**
     * Description... Click Categorize Button
     * @throws InterruptedException
	 * @throws IOException 
     */
           public void clickCategorize() throws InterruptedException, IOException{
                 clickWebElement(sheetName, "btn_Categorize;id", "Categorize", ScreenName);
                 waitForSync(5);
           }
           /**
           * Description... Select Category and save
           * @throws Exception 
            */
                 public void selectCategoryAndSave(String categoryValue) throws Exception
                 {
                       switchToWindow("storeParent"); 
                       switchToWindow("child");
                       selectValueInDropdown(sheetName, "lst_selectCategory;id", 
                     data(categoryValue), "Select Category dropdown", 
                     "Value");           
                       clickWebElement(sheetName, "btn_saveCategory;id", "Save", ScreenName);
                       waitForSync(5);
                       switchToWindow("getParent");
                       switchToFrame("default");
                       switchToFrame("contentFrame", "OPR050");
                 }
                 
                 /**
                 * @author A-9478
                 * Description... verify print
                 * @throws Exception 
                  */
                       public void verifyPrint() throws Exception
                       {
                             switchToWindow("storeParent");                              
                             clickWebElement(sheetName, "btn_Print;id", "Print", ScreenName);
                             waitForSync(3);
                             int windowSize=driver.getWindowHandles().size();
                             
                             try
                             {
                                   if(windowSize>1)
                                   {
                                   switchToWindow("child");
                                   driver.close();
                                   switchToWindow("getParent");
                                   switchToFrame("default");
                                   switchToFrame("contentFrame", "OPR050");
                                   onPassUpdate(sheetName, "Print functionality in List Discrepancies screen", "Print functionality is  working in List Discrepancies screen", "Print window", "Verify print functionality");
                                   }
                                   
                                   
                                   else
                                   {
                                         onFailUpdate(sheetName, "Print functionality in List Discrepancies screen", "Print functionality is not working in List Discrepancies screen", "Print window", "Verify print functionality");
                                   }
                             }
                             catch(Exception e)
                             {
                                   onFailUpdate(sheetName, "Print functionality in List Discrepancies screen", "Print functionality is not working in List Discrepancies screen", "Print window", "Verify print functionality");
                                   
                             }
                             
                       }


                       /**
                        * @author A-9478
                        * Description... Select AWBNo
                        * @throws InterruptedException
                        */
                              public void selectAWB(String AWBNo) throws InterruptedException
                              {
                                    String locator = xls_Read.getCellValue(sheetName, "chbx_selectAWB;xpath");
                                    locator=locator.replace("AWBNO",data(AWBNo));
                                    driver.findElement(By.xpath(locator)).click();
                                    waitForSync(2);
                              }

	/**
	 * Description... Verify Discrepancy details
	 * @param verfCols
	 * @param actVerfValues
	 * @throws IOException 
	 */
	public void verifyDiscrepancydetails(int verfCols[], String actVerfValues[]) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_AWB_FSWR;xpath", "//td", verfCols, data("AWBNo"),
				actVerfValues);
		waitForSync(3);
	}	
	
}