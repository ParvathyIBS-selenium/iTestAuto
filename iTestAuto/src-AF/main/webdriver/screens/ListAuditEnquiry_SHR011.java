package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListAuditEnquiry_SHR011 extends CustomFunctions{
	public ListAuditEnquiry_SHR011(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="ListAuditEnquiry_SHR011";
	public String ScreenName="ListAuditEnquiry";
	/**
	 * Description... selectModuleName
	 * @param mname
	 * @throws InterruptedException
	 */
	public void selectModuleName(String mname) throws InterruptedException{
		waitForSync(2);
		//selectOptionInList(sheetName,"lst_SCI;xpath","lst_sciOption;xpath",data(sci),"SCI",ScreenName);
		selectValueInDropdown(sheetName, "lst_moduleName;id", mname, "Module Name", "VisibleText");		
	}
	/**
	 * Description... 
	 * @param mname
	 * @throws InterruptedException
	 */
	public void selectSubModuleName(String mname) throws InterruptedException{
		waitForSync(2);
		//selectOptionInList(sheetName,"lst_SCI;xpath","lst_sciOption;xpath",data(sci),"SCI",ScreenName);
		selectValueInDropdown(sheetName, "lst_submoduleName;id", mname, "Sub Module Name", "VisibleText");		
	}
	/**
	 * Description... 
	 * @param fromdate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterFromDate(String fromdate) throws InterruptedException, AWTException{		
		enterValueInTextbox(sheetName, "inbx_fromDate;id", fromdate, "Txn. From Date", ScreenName);
		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");	
	}
	/**
	 * @author A-8783
	 * Desc - Enter airport code
	 * @param airportCode
	 * @throws InterruptedException
	 */
	public void enterAirportCode(String airportCode) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_airportCode;name", data(airportCode), "Airport code", ScreenName);

	}
	/**
	 * @author A-8783
	 * Desc - Enter token number
	 * @param token
	 * @throws InterruptedException
	 */
	public void enterToken(String token) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_token;name", data(token), "Token number", ScreenName);
	}
	/**
	 * To enter the flight details
	 * @param carrierCode
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 */

	   public void enterFlightDetails(String carrierCode,String fltNumber,String fltDate) throws InterruptedException{
	         
		   enterValueInTextbox(sheetName, "inbx_fltCarrierCode;id", data(carrierCode), "carrier code", ScreenName);
			enterValueInTextbox(sheetName, "inbx_fltNumber;id", data(fltNumber), "flight number", ScreenName);
			enterValueInTextbox(sheetName, "inbx_fltDate;id", data(fltDate), "fltDate", ScreenName);
			
		}

	/**
	 * Description... 
	 * @param todate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterToDate(String todate) throws InterruptedException, AWTException{		
		enterValueInTextbox(sheetName, "inbx_toDate;id", todate, "Txn. To Date", ScreenName);
		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");	
	}
	/**
	 * Description... 
	 * @param visibletext
	 * @param index
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void selectTransactionGroup(String visibletext, String [] index) throws InterruptedException, IOException 
 { 
  clickWebElement(sheetName, "btn_transactiongroup;id", "Transaction Group button", ScreenName); 
    waitForSync(2);
  
    switch (visibletext) { 
   
   case "check_all":
    clickWebElement("PrecheckDetails_OPR349", "lnk_CheckAll;xpath", "Check All ", ScreenName); 
    break; 
    
   case "uncheck_all":
	   clickWebElement("ListAuditEnquiry_SHR011", "btn_UnCheckAll;xpath", "UnCheck All ", ScreenName);
    break;
    
   case "index" :
    for(int i = 0 ; i<index.length ;i++)
    {
    String dynxapth = "//ul[@class='ui-multiselect-checkboxes ui-helper-reset']//li["+ index[i] +"]";
    driver.findElement(By.xpath(dynxapth)).click();
    }
    break;
    
    }
    clickWebElement(sheetName, "btn_transactiongroup;id", "Transaction Group button", ScreenName);
 }
	
	/**
	 * Description... Verify Row Count
	 */
	public void verifyRowCount(int expRowSize, String filter, String sheetName, String locator) throws InterruptedException, AWTException{		
		String dynXpath = xls_Read.getCellValue(sheetName, locator) + filter + "')]";
		System.out.println("dynXpath is---" + dynXpath);
		
		List<WebElement> rows = driver.findElements(By.xpath(dynXpath));
		System.out.println("row size is---" + rows.size());
		int actRowSize = rows.size();
		
		if(expRowSize == actRowSize)
			onPassUpdate((sheetName.split("_"))[0], String.valueOf(expRowSize), String.valueOf(actRowSize) , "Precheck Success should not be stamped after revalidate from Precheck List screen", "Precheck Success should not be stamped after revalidate from Precheck List screen verification");
		else
			onFailUpdate((sheetName.split("_"))[0], String.valueOf(expRowSize), String.valueOf(actRowSize) , "Precheck Success should not be stamped after revalidate from Precheck List screen", "Precheck Success should not be stamped after revalidate from Precheck List screen verification");
		
	}
	/**
	 * @author A-9844
	 * @Desc click the filter option and enter the filter name
	 * @param type
	 * @param filterText)
	 */

	public void clickTransactionFilter(String type,String filterText){
		try{

			String locator=xls_Read.getCellValue(sheetName, "btn_filter;xpath");
			locator=locator.replace("*", type);
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);

			enterValueInTextbox(sheetName, "inbx_filterText;xpath", data(filterText), "Filter Text", ScreenName);
			writeExtent("Pass", "Selected the filter as "+type+" on "+ScreenName);


		}

		catch(Exception e){
			writeExtent("Fail", "Failed to select the filter as "+type+" on "+ScreenName);
		}

	}
	/**
	 * @author A-9844
	 * @Desc verify breakdown Instruction
	 * @param expText
	 */

	public void verifyBreakdownInstruction(String expText){
		try{

			String locator =xls_Read.getCellValue(sheetName,"text_BdnInstruction;xpath");
			String ActualText=driver.findElement(By.xpath(locator)).getText();
			System.out.println(ActualText);

			if(ActualText.contains(expText))
				writeExtent("Pass", "Verified the breakdown instruction as "+expText+" on "+ScreenName);

			else
				writeExtent("Fail", "Failed to verify the breakdown instruction as "+expText+" on "+ScreenName);

		}

		catch(Exception e){
			writeExtent("Fail", "Could not verify the breakdown instruction on "+ScreenName);
		}

	}

	/**
	 * Description... Enter Ops ULD Details
	 * @param uldNumber
	 * @param carrierCode
	 * @param fltNumber
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterOpsULDDetails(String uldNumber,String carrierCode,String fltNumber,String fltDate) throws InterruptedException, AWTException{		
		
		enterValueInTextbox(sheetName, "inbx_uldNumber;name", data(uldNumber), "uldNumber", ScreenName);
		enterValueInTextbox(sheetName, "inbx_fltCarrierCode;name", data(carrierCode), "carrier code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_fltNumber;name", data(fltNumber), "flight number", ScreenName);
		enterValueInTextbox(sheetName, "inbx_fltDate;name", data(fltDate), "fltDate", ScreenName);
		
	}
	/**
	 * Description... Enter Awb Number
	 * @param awbPrefix
	 * @param awbNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterAwbNumber(String awbPrefix,String awbNumber) throws InterruptedException, AWTException{		
	
			
			
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(awbPrefix), "awb prefix", ScreenName);
			enterValueInTextbox(sheetName, "inbx_awbNumber;xpath", data(awbNumber), "awb number", ScreenName);
			
			selectRadioButton("shp");
			
		
		
	}
	/**
	 * @author A-9847
	 * @Desc To select the radio button types(shp/awb/cra/all) when listed with Operations(Module) and AWB(Submodule)
	 * @param type
	 */
	             
	            public void selectRadioButton(String type){
		try{
	            	  String locator=xls_Read.getCellValue(sheetName, "btn_type;xpath");
	                  locator=locator.replace("*", type);
	                  driver.findElement(By.xpath(locator)).click();
	                  writeExtent("Pass", "Selected the "+type+" radio button on "+ScreenName);
		}
		
		catch(Exception e){
			  writeExtent("Fail", "Failed to select the "+type+" radio button on "+ScreenName);
		}
	            	
	}

	/**
	 * Description... List Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listDetails() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_listDetails;xpath", "list details", ScreenName); 
		waitForSync(2);
	}
	/**
	 * Description... Verify Transaction Details
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyTransactionDetails(int verfCols[],String actVerfValues[],String pmKey)
			 throws InterruptedException, IOException {
	
		
			waitForSync(1);
			verify_tbl_records_multiple_cols(sheetName, "table_transactionDetails;xpath", "//td", verfCols, pmKey, actVerfValues);	
	}
	/**
	 * Description... Verify Transaction Details Value
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 */
  public void verifyTransactionDetailsValue(int verfCols[],String actVerfValues[],String pmKey)
			 throws InterruptedException {
	
		
			waitForSync(1);
			verify_tbl_records_multiple_cols_contains(sheetName, "table_transactionDetails;xpath", "//td", verfCols, pmKey, actVerfValues);	
	}
 
  /**
   *  @author A-10690
   * @param submodules[expected dropdown values]
   * @throws InterruptedException
   * @throws IOException
   * Desc : verify submodules in the dropdown in list audit enquiry screen
   */
  public void verifySubmodules(String Submodules) throws InterruptedException, IOException {

  	
  	
  	verifyDropdownValues(Submodules,sheetName,ScreenName,"text_submodule;xpath");	
  }



  /**
   * @author A-10690
   * @param submodulename
   * @throws InterruptedExceptions
   * Desc:verify whether expected submodule got selected in list audit enquiry screen
   */
  public void verifySubModuleSelected(String mname) throws InterruptedException{
  	waitForSync(2);
  	
  	selectValueInDropdown(sheetName, "lst_submoduleName;id", mname, "Sub Module Name", "VisibleText");
  	waitForSync(2);
  	String explocator="text_typeverify;xpath";
  	explocator=explocator.replace("type", mname);
  	waitForSync(2);
  	String locator=xls_Read.getCellValue(sheetName,explocator);
    
  	if(driver.findElements(By.xpath(locator)).size()==1)
  	
  		 writeExtent("Pass", "verified "+mname+"selected"+ScreenName);
     	  
     	  else
     		  writeExtent("Fail", "Failed to verify "+mname+"selected "+ScreenName); 
  	}


  /**
   * Description... Verify Details With Same Transaction
   * @param verfCols
   * @param actVerfValues
   * @param pmKey
   * @param rowIndex
   * @throws InterruptedException
   */
public void verifyDetailsWithSameTransaction(int verfCols[],String actVerfValues[],String pmKey, int rowIndex)
    throws InterruptedException {
  
  String table_row = "("+ xls_Read.getCellValue(sheetName, "table_transactionDetails;xpath")+"[contains(.,'"+ pmKey +"')])["+ rowIndex +"]";
      

   for (int k = 0; k < verfCols.length; k++) {
    int x = verfCols[k];

    String td = table_row + "//td" + "[" + x + "]";
    ele = driver.findElement(By.xpath(td));

    String actual = ele.getText().toLowerCase().replace(" ", "");
    String expected = (actVerfValues[k].replace(" ", "").toLowerCase());

    if (actual.contains(expected)) {
     System.out.println("found true for " + actVerfValues[k]);

     onPassUpdate(ScreenName, expected, actual, "Table verification against " + pmKey + " On ",
       "Table verification");

    } else {
     onFailUpdate(ScreenName, expected, actual, "Table verification against " + pmKey + " On ",
       "Table verification");

    }
    
   
  }
 }



public void verifyAudit(String status, String[] expectedResult) throws Exception {
    {                 
          String actualResult = null;
          int k=0;
          System.out.println();
          System.out.println(data(status)); 
          List<WebElement> ls = driver.findElements(By.xpath("//tbody[@id='auditDetails']//tr"));        
          for (int i = 1; i <=ls.size(); i++) {
              for(int j=i;j<=5;j++){
                try {
                      String dynXpath="//tbody[@id='auditDetails']//tr[" + i + "]//td[" + j + "]";
                      System.out.println(dynXpath);
                      WebElement ele=driver.findElement(By.xpath(dynXpath));
                      String Result = ele.getText();
                      actualResult = Result.replaceAll("\\s+", "");
                      String expRes=expectedResult[k].replaceAll("\\s+", "");
                      if (actualResult.contains(expRes)) {
                            System.out.println("found true for " + actualResult);
                            onPassUpdate(ScreenName, expectedResult[i-1],
                                        actualResult, "Table verification against "
                                                    + status + " On ", "Table verification");
                            ++k;
                       continue;    
                      }
                      else{
                          break;
                      }
                }
              
                 catch (Exception e) {
                      onFailUpdate(ScreenName, expectedResult[i], actualResult,
                                  "Table verification against " + status + " On ",
                                  "Table verification");
                      System.out.println(e);
                }
              }
          }
          }

}


}
