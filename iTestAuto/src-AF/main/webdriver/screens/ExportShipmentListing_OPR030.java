package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ExportShipmentListing_OPR030 extends CustomFunctions{
	public ExportShipmentListing_OPR030(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="ExportShipmentListing_OPR030";
	public String ScreenName="ExportShipmentListing_OPR030"; 
	
	//Enter AWB number in the AWB field
	/**
	 * Description... List AWB
	 * @param awbNo
	 * @param Prefix
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listAWB(String awbNo, String Prefix) throws InterruptedException, IOException {
		enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_awbNumberPrefix;xpath", data(Prefix), "Prefix", "ExportShipmentListing_OPR030");
		enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_awbNumber;xpath", data(awbNo), "AWB No", "ExportShipmentListing_OPR030");
		clickWebElement("ExportShipmentListing_OPR030", "btn_search;xpath", "List Button", "ExportShipmentListing_OPR030");
		waitForSync(4);
	}
	/**
	 * @author A-9844
	 * Description...Enter flight number
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void enterFlightNumber(String carrierCode,String flightNumber) throws InterruptedException, Exception{
		enterValueInTextbox(sheetName, "inbx_carrierCode;xpath", data(carrierCode), "Carrier Code",ScreenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(flightNumber), "Flight Number",ScreenName);
		waitForSync(2);
	}
	/**
	 * @author A-10330
	 * Description... select ShipmentEnquiryfilters
	 * @param --Filtervalue,filter
	 * @throws Exception
	 */

	public void selectShipmentEnquiryFilters(String Filtervalue,String filter ) throws Exception{

		if(filter.equals("AgentCode"))
		{

			enterValueInTextbox("ExportShipmentListing_OPR030","input_AgentCode;xpath",data(Filtervalue), "Agent Code", "ExportShipmentListing_OPR030");
			waitForSync(1);

		}

		else if(filter.equals("SCI"))
		{
			selectValueInDropdown("ExportShipmentListing_OPR030","select_SCI;xpath",data(Filtervalue),"select SCI","VisibleText");
			waitForSync(1);
		}
		else if(filter.equals("RFC"))
		{
			selectValueInDropdown("ExportShipmentListing_OPR030","select_RFC;xpath",data(Filtervalue),"select Ready for carriage","VisibleText");
			waitForSync(1);
		}
		clickList();
	}
	/**
	 * @author A-10330
	 * Description...verifyShipmentEnquiryfilters
	 * @param --Filtervalue,filter
	 * @throws Exception
	 */
	public void verifyShipmentEnquiryFilters(String filterValue,String filter) throws Exception
	{
		String locator="";
		Boolean filters=false;
		
		try
		{
			if(filter.equals("AgentCode"))
			{
				locator=xls_Read.getCellValue(sheetName, "input_AgentCode;xpath");
				filters=driver.findElement(By.xpath(locator)).isDisplayed();
				if(filters)
				{
					writeExtent("Pass", "successfully verified "+filter+" filter on"+ScreenName);
					selectShipmentEnquiryFilters(filterValue,filter);
				}
				waitForSync(1);
			}

			else if(filter.equals("SCI"))
			{
				locator=xls_Read.getCellValue(sheetName, "select_SCI;xpath");
				filters=driver.findElement(By.xpath(locator)).isDisplayed();
				if(filters)
				{
					writeExtent("Pass", "successfully verified "+filter+" filter on"+ScreenName);
					selectShipmentEnquiryFilters(filterValue,filter);
				}
				waitForSync(1);
			}
			else if(filter.equals("RFC"))
			{
				locator=xls_Read.getCellValue(sheetName, "select_RFC;xpath");
				filters=driver.findElement(By.xpath(locator)).isDisplayed();
				if(filters)
				{
					writeExtent("Pass", "successfully verified "+filter+" filter on"+ScreenName);
					selectShipmentEnquiryFilters(filterValue,filter);
				}
				waitForSync(1);
			}
			}
		catch(Exception e)
		{
			writeExtent("Fail", "Could not verified "+filter+" filter on"+ScreenName);
		}
	}

	/**
	 * @author A-9844
	 * Description...Enter flight date
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void enterFlightDate(String flightDate) throws InterruptedException, Exception{
		enterValueInTextbox(sheetName, "inbx_flightDate;xpath", data(flightDate), "Flight Date",ScreenName);
		
	}
	/**
	 * Description...created Overloaded method to Enter toDate
	 * @throws Exception 
	 * @throws InterruptedException 
	 */

public void clickFromToDate(String toDate) throws InterruptedException, Exception

{
	 enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_FromDate;id", createDateFormat("dd-MMM-YYYY",-1,"DAY","").toUpperCase(), "From Date", "ExportShipmentListing_OPR030");
     enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_ToDate;id",toDate,"To Date", "ExportShipmentListing_OPR030");
}

	/**
	 * @author A-9844
	 * Description...verify Column name -Accepted/Breakdown/Pcs/Wgt/vol
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyAccepted_BreakdownColumn(String expText) throws InterruptedException, Exception{
		 String locator= xls_Read.getCellValue(sheetName, "label_acceptedBreakdown;xpath");
		 By ele =By.xpath(locator);
		 String actText = driver.findElement(ele).getText();
		 System.out.println(actText);
		 verifyScreenText(ScreenName, expText, actText, "Accepted/Breakdown/Pcs/Wgt/Vol", "Accepted/Breakdown/Pcs/Wgt/Vol");
		 
	}
	/**
	 * @author A-9844
	 * Description...click LIST
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void clickList() throws InterruptedException, Exception{
		 
		clickWebElement(sheetName,"btn_search;xpath","Click List", ScreenName);
		waitForSync(2);
	}
	/**
	 * Description... Click Acceptance
	 * @throws InterruptedException
	 * @throws IOException 
	 */
             public void clickAcceptance() throws InterruptedException, IOException
             {
                    clickWebElement("ExportShipmentListing_OPR030", "btn_Acceptance;id", "Acceptance button", "ExportShipmentListing_OPR030");
                    waitForSync(10);
             }
       /**
        * Description... Click Shipment CheckBox
        * @throws InterruptedException
     * @throws IOException 
        */
             public void clickShipmentCheckBox() throws InterruptedException, IOException
             {
                    clickWebElement("ExportShipmentListing_OPR030", "chk_Shipment;name", "Shipment CheckBox", "ExportShipmentListing_OPR030");
             }


/**
 * Description... List eFrieght AWB
 * @param awbNo
 * @param Prefix
 * @param FilterMode
 * @throws Exception
 */
	public void listeFrieghtAWB(String awbNo, String Prefix,String FilterMode) throws Exception {
              enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_awbNumberPrefix;xpath", data(Prefix), "Prefix", "ExportShipmentListing_OPR030");
              enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_awbNumber;xpath", data(awbNo), "AWB No", "ExportShipmentListing_OPR030");
              
              enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_FromDate;id", createDateFormat("dd-MMM-YYYY",-1,"DAY","").toUpperCase(), "From Date", "ExportShipmentListing_OPR030");
              enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_ToDate;id", createDateFormat("dd-MMM-YYYY",+1,"DAY","").toUpperCase(), "To Date", "ExportShipmentListing_OPR030");
              selectValueInDropdown("ExportShipmentListing_OPR030", "lst_FilterMode;id", data(FilterMode), "FilterMode","VisibleText");
              
              clickWebElement("ExportShipmentListing_OPR030", "btn_search;xpath", "List Button", "ExportShipmentListing_OPR030");
              waitForSync(4);
       }
	/**
	 * 
	 * @param origin
	 * @param destination
	 * @param filterMode
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void listWithODandFilterMode(String origin, String destination, String filterMode) throws InterruptedException, Exception
	{
		 enterValueInTextbox(sheetName, "inbx_originValue;xpath", data(origin), "Origin", ScreenName);
		 enterValueInTextbox(sheetName, "inbx_destValue;xpath", data(destination), "Origin", ScreenName);
		  selectValueInDropdown("ExportShipmentListing_OPR030", "lst_FilterMode;id", data(filterMode), "FilterMode","VisibleText");
	      
	      clickWebElement("ExportShipmentListing_OPR030", "btn_search;xpath", "List Button", "ExportShipmentListing_OPR030");
	      waitForSync(4);
	}
	/**
	 * 
	 * @param atRow
	 * @param atColumn
	 * @return
	 */
	public String getColumnValueRowWise(String atRow, String atColumn) {
		
		String locator = xls_Read.getCellValue(sheetName, "dynamicColumnValue;xpath");
	    locator=locator.replace("AtRow",atRow);
	    locator=locator.replace("AtColumn",atColumn);
	    String value=driver.findElement(By.xpath(locator)).getText().trim();
	    
	    return value;
		
	}

	/**
	 * 
	 * @param atRow
	 * @param atColumn
	 * @param columnName
	 * @param requiredData
	 */
	public void verifyAnyColumnData(String atRow, String atColumn,String columnName,String requiredData)
	{
		String actualText=getColumnValueRowWise(atRow, atColumn);
		String trimmedTxt="";
		String requiredDataTrimmed="";
		for(int i=0;i<actualText.length();i++)
		{
			char ch=actualText.charAt(i);
			if(ch!=' ')
				trimmedTxt=trimmedTxt+ch;
		}
		for(int i=0;i<requiredData.length();i++)
		{
			char ch=requiredData.charAt(i);
			if(ch!=' ')
				requiredDataTrimmed=requiredDataTrimmed+ch;
		}
		if(trimmedTxt.contains(requiredDataTrimmed))
		{
			test.log(LogStatus.PASS, "The value in row "+atRow+" column: "+columnName+" is successfully verified");
		}
		else
		{

			test.log(LogStatus.FAIL, "The value in row "+atRow+" column: "+columnName+" doesn't match. Value displayed is: "+trimmedTxt);
			Assert.fail();
		}
	}


	/**
	 * Description... Verify Table Records
	 * @param verfCols
	 * @param actVerfValues
	 * @param FullAWBNumber
	 * @throws IOException 
	 */
	//verifying Table Records
	
	public void verifyTableRecords(int verfCols[],String actVerfValues[],String FullAWBNumber) throws IOException{
		 waitForSync(4);
		//int verfCols[]={5,6,9,16};
		//String[] actVerfValues={data(Origin),data(Destination),data(Product),data(AWBStatus)};
		verify_tbl_records_multiple_cols(sheetName, "tbl_verification;xpath", "//td", verfCols, data(FullAWBNumber), actVerfValues);
	}
	

/**
 * Description... Verify E DATA Status
 * @param FullAWBNumber
 * @param EDTAStatusColor
 * @param EdataStatus
 */
	public void verifyEDATAStatus(String FullAWBNumber, String EDTAStatusColor, String EdataStatus) {

        waitForSync(4);
        String dynxpath = ("//div[@class='tableContainer']//tr[contains(.,'"+ FullAWBNumber +"')]//div[contains(@id,'ImageEdata')]/label");
        
        switch (EdataStatus) 
		{
		case "NA":
			List<WebElement> imgs = driver.findElements(By.xpath(dynxpath));
			 
			   int size = imgs.size();
			 if(size == 0){
				 System.out.println("EdataStatus status is "+EdataStatus);
				 writeExtent("Pass", "EdataStatus status is "+EdataStatus);
			 	}else{
			 		System.out.println("EdataStatus status is not "+EdataStatus);
			 		writeExtent("Fail", "EdataStatus status is not "+EdataStatus);
			 	}
		break;
			
		
      
        case "eDataAvailable":
        String colorStatus = null;
        String actVerfValues = data(EDTAStatusColor);
        WebElement valueStore = driver.findElement(By.xpath(dynxpath));
        colorStatus = valueStore.getAttribute("class");

        if (actVerfValues.equals(colorStatus))
               System.out.println("EDATA status is verified");
        else
               System.out.println("EDATA status is not verified");
        break;	
		}
 }
	/**
	 * @author A-8783
	 * Desc - Verify planned flight details
	 * @param flightNumber
	 * @param flightDate
	 */
	public void verifyPlannedFlight(String flightNumber, String flightDate){
		try{
			waitForSync(3);
			String locator = xls_Read.getCellValue(sheetName, "txt_flightNo;xpath").replace("*", flightNumber);
			String FlightDet = driver.findElement(By.xpath(locator)).getText();
			System.out.println(FlightDet);
			String actFlightNo = FlightDet.split("\n")[0];
			verifyScreenText(sheetName, flightNumber, actFlightNo, "Planned Flight", "Flight number");
			String actFlightDate = FlightDet.split("\n")[1];
			verifyScreenText(sheetName, flightDate, actFlightDate, "Planned Flight Date", "Flight date");

			}
			catch(Exception e)
			{
				writeExtent("Fail", "Failed to verify the planned flight details on "+ScreenName);
			}
	}

/**
	 * @author A-9844
	 * Description...verify Column name
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyColumn(String[] columnName) throws InterruptedException, Exception{
		int i = 0;
		int flag=0;
		try {
			String locator=xls_Read.getCellValue(sheetName,"tbl_exportShipmentListingColumn;xpath");
			List<WebElement> column = driver.findElements(By.xpath(locator));
			for( i=0;i<columnName.length;i++){
				flag=0;
				for(WebElement col:column) {
					String actText = col.getText();
					System.out.println(actText);
					if(actText.equals(columnName[i])) {

						writeExtent("Pass", "Verified that the column " + columnName[i] + " is present in the table");
						break;
					}
					else {
						flag+=1;
					}

				}
				if(flag==column.size()) {
					writeExtent("Fail", "Failed to verify that the column " + columnName[i] + " is present in the table");

				}

			}

		}
		catch(Exception e) {
			writeExtent("Fail", "Failed to verify if columns are present");
		}
	}
	/**
	 * @author A-9844
	 * Description... Enter AWB
	 * @param awbNo
	 * @param Prefix
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterAWB(String awbNo, String Prefix) throws InterruptedException, IOException {
		enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_awbNumberPrefix;xpath", data(Prefix), "Prefix", "ExportShipmentListing_OPR030");
		enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_awbNumber;xpath", data(awbNo), "AWB No", "ExportShipmentListing_OPR030");
		
	}
	/**
	 * @author A-9844
	 * Description... select filter Mode
	 * @param FilterMode
	 * @throws Exception
	 */
	public void selectFilterMode(String FilterMode) throws Exception{
		selectValueInDropdown("ExportShipmentListing_OPR030","lst_filterMode;xpath",data(FilterMode),"List filter mode","VisibleText");
	}
/**
 * Description... Verify Precheck Status
 * @param AWBNumber
 * @param PrecheckStatus
 * @throws InterruptedException
 */
	public void verifyPrecheckStatus(String AWBNumber, String PrecheckStatus) throws InterruptedException 
	{
		String xpath = xls_Read.getCellValue(sheetName, "tbl_ShipmentEnquiry;xpath");
		String dynxpath = xpath + "[contains(text(),'"+ AWBNumber + "')]/../../td[8]//img";
		
		switch (PrecheckStatus) 
		{
		
		case "NA":
			List<WebElement> imgs = driver.findElements(By.xpath(dynxpath));
			 
			 int size = (imgs.size());  
			 if(size == 0){
				 System.out.println("Precheck status is "+PrecheckStatus);
				 writeExtent("Pass", "Precheck status is "+PrecheckStatus);
			 		}else{
			 		System.out.println("Precheck status is not "+PrecheckStatus);
			 		writeExtent("Fail", "Precheck status is not "+PrecheckStatus);
			 		}
			 	break;
		
		case "On-hold":
			String imgxpath = dynxpath + "[contains(@src,'pause')]";
			WebElement img = driver.findElement(By.xpath(imgxpath));
			verifyElementDisplayed(img, "precheck verification", ScreenName, "on hold status");
			break;
			
		case "Failed":
			String imgxpath2 = dynxpath + "[contains(@src,'error')]";
			WebElement img2 = driver.findElement(By.xpath(imgxpath2));
			verifyElementDisplayed(img2, "precheck verification", ScreenName, "failed status");
			break;
		
		case "Pending":
			String imgxpath3 = dynxpath + "[contains(@src,'loading')]";
			WebElement img3 = driver.findElement(By.xpath(imgxpath3));
			verifyElementDisplayed(img3, "precheck verification", ScreenName, "pending status");
			break;
			
		}
		 

    }
		 

        
/**
 * Description... Clear Origin
 */
    	public void clearOrigin()
	 {
		 try {
	           By element=getElement(sheetName,"inbx_originValue;xpath");  
	           driver.findElement(element).click();                  
	           driver.findElement(element).clear();
	           waitForSync(1);
	           
			} catch (Exception e) {
				System.out.println("Could not clear Origin data " + ScreenName + " Page");
				writeExtent("Fail", "Could not clear Origin data " + ScreenName + " Page" );
				Assert.assertFalse(true, "Could not clear Origin data " + ScreenName + " Page");
			}

	 }
/**
 * Description... Select Reason For Not RFC
 * @param action
 * @param reason
 * @throws InterruptedException
 * @throws IOException 
 */
public void selectReasonForNotRFC(String action, String reason) throws InterruptedException, IOException
    	{
    		
    		clickWebElement("ExportShipmentListing_OPR030", "btn_reasonForNotRFC;id", "Reason for Not RFC Button", "ExportShipmentListing_OPR030");
    		
    		switch (action) 
    		{
    		
    		case "UncheckAll":
    			clickWebElement("ExportShipmentListing_OPR030", "lnk_checkAll;xpath", "Uncheck All link", "ExportShipmentListing_OPR030");	 	
    			break;
    		
    		case "CheckAll":
    			clickWebElement("ExportShipmentListing_OPR030", "lnk_checkAll;xpath", "Uncheck All link", "ExportShipmentListing_OPR030");
    			break;
    			
    		case "Select":
    			
    			clickWebElement("ExportShipmentListing_OPR030", "lnk_checkAll;xpath", "Uncheck All link", "ExportShipmentListing_OPR030");
    			driver.findElement(By.xpath("//span[contains(.,'"+reason+"')]/../input")).click();
    			break;
    			
    		}
    	}
    		
   /**
    * Description...  Verify Error Messge		
    * @param expMsg
    * @throws InterruptedException
    */
    		
    		public void verifyErrorMessge(String expMsg) throws InterruptedException
        	{
        		
        		if(driver.findElement(By.xpath("//td[contains(.,'"+expMsg+"')]")).isDisplayed()){
    			
        			System.out.println(expMsg+ " is displayed on " + ScreenName + " Page");
    				writeExtent("Pass", expMsg+ " is displayed on " + ScreenName + " Page" );
        			
        		}else{
        			
        			System.out.println(expMsg+ " is not displayed on " + ScreenName + " Page");
    				writeExtent("Fail", expMsg+ " is not displayed on " + ScreenName + " Page" );
    				Assert.assertFalse(true, expMsg + " is not displayed on " + ScreenName + " Page");
        		        			
        		}
        				
        				
        		}
   /**
    * Description...  Click Clear		
    * @throws InterruptedException
 * @throws IOException 
    */
    		public void clickClear() throws InterruptedException, IOException
        	{
        		
    			clickWebElement("ExportShipmentListing_OPR030", "btn_Clear;id", "Reason for Not RFC Button", "ExportShipmentListing_OPR030");
				waitForSync(2);
        				
        	}
    		
    		
    /**
     * Description... 	Verify Carriage Status
     * @param AWBNumber
     * @param CarriageStatus
     * @throws InterruptedException
     */
    		
    		public void verifyCarriageStatus(String AWBNumber, String CarriageStatus) throws InterruptedException
        	{
        		
    			String xpath = xls_Read.getCellValue(sheetName, "tbl_ShipmentEnquiry;xpath");
    			String dynxpath = xpath + "[contains(text(),'"+ AWBNumber + "')]/../../..//img";
    			
    			
    			switch (CarriageStatus) 
    			{
    			
    			
    			case "ReadyForCarriage":
    				String imgxpath = dynxpath + "[contains(@src,'tick')]";
    				
    				
    			
    				
    				try
    				{
    				
    				
    					WebElement img = driver.findElement(By.xpath(imgxpath));
    					onPassUpdate("ScreenName", "Ready for carriage","Ready for carriage","RCS","Verifying the whether the shipment is ready for carriage");
    						
    				}
    				
    				catch(Exception e)
    				{
    					 onFailUpdate("ScreenName", "Ready for carriage","Not ready for carriage","RCS","Verifying the whether the shipment is ready for carriage");	
    				}
    				
    				
    				//verifyElementDisplayed(img, "carriage status verification", ScreenName, "Ready for carriage status");
    				break;
    				
    			case "NotReadyForCarriage":
    				String imgxpath2 = dynxpath + "[contains(@src,'cross')]";
    			
    				try
    				{
    				
    				
    					WebElement img2 = driver.findElement(By.xpath(imgxpath2));
    					onPassUpdate("ScreenName", "Not Ready for carriage","Not Ready for carriage","RCS","Verifying the whether the shipment is ready for carriage");
    						
    				}
    				
    				catch(Exception e)
    				{
    					 onFailUpdate("ScreenName", "Not Ready for carriage","Ready for carriage","RCS","Verifying the whether the shipment is ready for carriage");	
    				}
    				
    				
    				
    				//verifyElementDisplayed(img2, "carriage status verification", ScreenName, "Not ready for carriage status");
    				break;

    				
    			}	
        				
        	}
/**
 * Description...  Verify eData Info
 * @param FullAWBNumber
 * @param pmKey
 * @param verfCols
 * @param actVerfValues
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void verifyeDataInfo(String FullAWBNumber, String pmKey, int [] verfCols, String [] actVerfValues ) throws InterruptedException, AWTException, IOException {
    			
    			String dynxpath = ("//tr[contains(.,'"+ FullAWBNumber +"')]//div[contains(@id,'ImageEdata')]/label");
    			driver.findElement(By.xpath(dynxpath)).click();
    			waitForSync(3);
    			    			
    			verify_tbl_records_multiple_cols(sheetName, "tbl_eDataInfo;xpath", "//td", verfCols, pmKey, actVerfValues);

    			}
    		
  /**
   * Description...  Export To Excel 		
   * @throws InterruptedException
   * @throws AWTException
 * @throws IOException 
   */
    		
    		public void exportToExcel() throws InterruptedException, AWTException, IOException 
    		{		
    			clickWebElement(sheetName, "btn_exportToExcel;id", "Export to Excel Button", ScreenName);
    			waitForSync(5);		      
    		}
    	/**
    	 * Description... Click Capture AWB button
    	 * @throws InterruptedException
    	 * @throws IOException 
    	 */
public void capture_AWB()throws InterruptedException, IOException{
    		   clickWebElement("ExportShipmentListing_OPR030", "cap_AWB;xpath", "Capture AWB button","ExportShipmentListing_OPR030");
    		}
/**
 * Description... Verify Carriage Status2
 * @param AWBNumber
 * @param CarriageStatus
 * @throws InterruptedException
 */
public void verifyCarriageStatus2(String AWBNumber, String CarriageStatus) throws InterruptedException
             {
                    System.out.println(AWBNumber);
                    String xpath = xls_Read.getCellValue(sheetName, "tbl_ShipmentEnquiry;xpath");
                    String dynxpath = "(("+xpath + "[contains(text(),'"+ AWBNumber + "')])//..)[1]//..//img";

                    switch (CarriageStatus) 
                    {
                    
                    
                    case "ReadyForCarriage":
                           String imgxpath = dynxpath + "[contains(@src,'tick')]";
                           System.out.println(imgxpath);
                           WebElement img = driver.findElement(By.xpath(imgxpath));
                           moveScrollBar(img);
                           verifyElementDisplayed(imgxpath, "carriage status verification", ScreenName, "Ready for carriage status");
                           
                           break;
                           
                    case "NotReadyForCarriage":
                           String imgxpath2 = dynxpath + "[contains(@src,'cross')]";
                           WebElement img2 = driver.findElement(By.xpath(imgxpath2));
                           moveScrollBar(img2);
                           verifyElementDisplayed(imgxpath2, "carriage status verification", ScreenName, "Not ready for carriage status");
                           break;                   
  
                    }
             }
    		
/**
 * Description... Verify eDGD Status
 * @param AWBNumber
 * @param Capable_nonCapableLane
 * @throws InterruptedException
 */
public void verifyeDGDStatus(String AWBNumber,String Capable_nonCapableLane) throws InterruptedException {
    
    String xpath = xls_Read.getCellValue(sheetName, "tbl_ShipmentEnquiry;xpath");
    String dynxpath = xpath + "[contains(text(),'"+ AWBNumber + "')]/..//div";
    
    switch (Capable_nonCapableLane) {
    
    case "NonCapableLane":
           String imgxpath = dynxpath + "[@class='iCeDgeXButton']";
           WebElement img = driver.findElement(By.xpath(imgxpath));
           verifyElementDisplayed(img, "edgd verification", ScreenName, "non capable lane status");
           break;
           
    case "CapableLane":
           String imgxpath2 = dynxpath + "[@class='iCeDgeCButton']";
           WebElement img2 = driver.findElement(By.xpath(imgxpath2));
           verifyElementDisplayed(img2, "edgd verification", ScreenName, "capable lane status");
           break;
    
    }
   			
}
/**
 * Description... select AWB Status
 * @param FilterMode
 * @throws Exception
 */
public void AWBStatus(String FilterMode) throws Exception{
selectValueInDropdown("ExportShipmentListing_OPR030","lst_AWBStatus;xpath",data(FilterMode),"List AWB Status","VisibleText");
       }

/**
 * Description... VerifyAWBListed
 * @param AWBNo
 * @throws Exception
 */
public void verifyAWBListed(String AWBNo) throws Exception{
       String actual=driver.findElement(By.xpath("//td[@class='iCargoTableDataTd'][2]")).getText();
       String expected=AWBNo;
       if(actual.contains(expected)){
              verifyScreenText(sheetName, expected, actual, "AWB Listed", "AWB Listed");
       }
       else{
              verifyScreenText(sheetName, expected, actual, "AWB not Listed", "AWB Listed");
       }
}
/**
 * Description... Click Edit
 * @throws Exception
 */
public void clickEdit()throws Exception{
       clickWebElement(sheetName,"btn_Edit;xpath","Click Edit", ScreenName);
}
/**
 * Description...Enter From Date and To Date
 * @throws Exception 
 * @throws InterruptedException 
 */
public void clickFromToDate() throws InterruptedException, Exception{
	 enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_FromDate;id", createDateFormat("dd-MMM-YYYY",-1,"DAY","").toUpperCase(), "From Date", "ExportShipmentListing_OPR030");
     enterValueInTextbox("ExportShipmentListing_OPR030", "inbx_ToDate;id", createDateFormat("dd-MMM-YYYY",+1,"DAY","").toUpperCase(), "To Date", "ExportShipmentListing_OPR030");
}
}