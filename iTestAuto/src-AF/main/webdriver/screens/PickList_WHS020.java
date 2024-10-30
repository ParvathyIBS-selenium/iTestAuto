package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.testng.Assert;
import org.openqa.selenium.WebElement;
import java.util.List;
import com.relevantcodes.extentreports.LogStatus;


import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class PickList_WHS020 extends CustomFunctions {
	
	String sheetName = "PickList_WHS020";
	String screenName = "Pick List: WHS020 ";
	String screenId="WHS020";
	

	public PickList_WHS020(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	/**
	 * Description... select List Type
	 * @param lstType
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void selectLstType(String lstType) throws AWTException, InterruptedException
{
	waitForSync(1);
	selectValueInDropdown(sheetName,"lst_lstType;name",lstType,"List Type","VisibleText");	
	keyRelease("TAB");
	waitForSync(1);
}
	/**
	 * Description... List Flight
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
public void listFlight(String carrierCode,String flightNumber, String flightDate) throws InterruptedException, AWTException, IOException {
		
		String sheetname="Generic_Elements";		
		enterValueInTextbox(sheetname, "inbx_carrierCode;xpath",
				data(carrierCode), "Carrier Code", screenId);
		enterValueInTextbox(sheetname, "inbx_flightNumber;xpath", data(flightNumber),
				"Flight Number", screenId);
		enterValueInTextbox(sheetname, "inbx_flightDate;xpath",
				data(flightDate), "Flight Date", screenId);
		keyPress("TAB");
		keyRelease("TAB");
		waitForSync(2);
		//clickWebElement(sheetName, "lst_segment;name", "Segment",screenId);
		selectValueInDropdown(sheetName,"lst_segment;name","All","Flight Segment","VisibleText");
		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement(sheetName, "chkbox_Location;name", "Show All Locations",screenId);
		waitForSync(1);
		clickWebElement(sheetName, "btn_List;name", "List Button",screenId);
		waitForSync(4);

	}
	/*A-8705
      * Selects shipment in Picklist screen for TO generation
      */
      /**
       * Description... Select Shipment For PickList
       * @throws Exception
       */
      public void SelectShipmentForPickList() throws Exception{
            waitForSync(3);
            switchToWindow("storeParent");
            waitForSync(2);
            clickWebElement(sheetName, "btn_AssignBuildUpLoc;name",
                        "BuildUp Location", screenId);
            waitForSync(4);
            switchToWindow("child");
            waitForSync(7);
          pickListOk();
        switchToFrame("default");
        switchToFrame("contentFrame", "WHS020");
        waitForSync(3);
    List<WebElement> c = driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "chkbx_AllPicklist;xpath")));
     System.out.println(c.size());
     for(WebElement e:c){
       e.click();
     }

}
	/**
	 * Description... Enter Flight Details
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterFlightDetails(String carrierCode,String flightNumber, String flightDate) throws InterruptedException, AWTException {
              
             
              String sheetname = "Generic_Elements";
              enterValueInTextbox(sheetname, "inbx_carrierCode;xpath", data(carrierCode), "Carrier Code", screenId);
              enterValueInTextbox(sheetname, "inbx_flightNumber;xpath", data(flightNumber), "Flight Number", screenId);
              enterValueInTextbox(sheetname, "inbx_flightDate;xpath", data(flightDate), "Flight Date", screenId);
              performKeyActions(sheetname, "inbx_flightDate;xpath", "TAB", "Flight Date", screenId);
              
              waitForSync(2);
              selectValueInDropdown(sheetName, "lst_FlightSegment;name", "All", "Flight Segment", "VisibleText");
              waitForSync(2);
              performKeyActions(sheetname, "lst_FlightSegment;name", "TAB", "Flight Segment", screenId);

              
       }
	
	/**
	 * Description... Advanced Search
	 * @param searchCriteria
	 * @throws InterruptedException
	 * @throws AWTException
	 */
public void advancedSearch(String searchCriteria) throws InterruptedException, AWTException {
       
       String sheetname="PickList_WHS020";           
       enterValueInTextbox(sheetname, "txt_advancedSearch;xpath",
                     data(searchCriteria), "Advanced Search", screenId);

}
/**
 * Description... Click List
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void clickList() throws InterruptedException, AWTException, IOException {
       
clickWebElement(sheetName, "btn_List;name", "List Button",screenId);
waitForSync(4);

}
/**
 * Description... Enter Destination Location
 * @param locCode
 * @throws InterruptedException
 */
     public void enterDestinationLocation(String locCode) throws InterruptedException
    {
	enterValueInTextbox(sheetName, "inbx_destinationCode;xpath",data(locCode), "Location Code", screenId);
	waitForSync(2);		
    }
     /**
      * Description... Select Shipment
      * @throws Exception
      */
	public void SelectShipment() throws Exception {

		// selectTableRecord(data(pmkey), "tbl_pickList;xpath", sheetName, 3);
		clickWebElement(sheetName, "chkbox_selectShipment;name", "Select Checkbox", screenId);
		waitForSync(3);
		switchToWindow("storeParent");
		waitForSync(2);
		clickWebElement(sheetName, "btn_AssignBuildUpLoc;name", "BuildUp Location", screenId);
		waitForSync(4);
		switchToWindow("child");
		waitForSync(7);
	}
/**
 * Description... Verify PickList Details
 * @param verfCols
 * @param actVerfValues
 * @param verfCols1
 * @param actVerfValues1
 * @throws Exception
 */
	public void verifyPickListDetails(int verfCols[], String actVerfValues[],int verfCols1[], String actVerfValues1[]) throws Exception {

		verify_tbl_records_multiple_cols_Picklist(sheetName, "tbl_pickList;xpath", "//input", verfCols, data("FlightNo"),
				actVerfValues);
		verify_tbl_records_multiple_cols_Picklist(sheetName, "tbl_pickList;xpath", "//select", verfCols1, data("FlightNo"),
				actVerfValues1);
	}

/**
 * Description...	Select All Shipment
 * @throws InterruptedException
 */
     public void selectAllShipment() throws InterruptedException
    {
	
	checkIfUnchecked(sheetName, "chkbox_selectAllShipment;xpath","Select All Shipment", screenId);
	waitForSync(1);
     }
 /**
  * Description... Verify Multiple BuildUp Zone
  * @throws Exception
  */
	public void verifyMultipleBuildUpZone() throws Exception {
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_ZoneCode1;xpath")))
				.getText();
		String actText1 = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_ZoneCode2;xpath")))
				.getText();
		String expText = data("BuildUpZone");
		String expText1 = data("BuildUpZone2");
		try
		{		
		if(actText.equals(expText))
		{
			verifyScreenText(sheetName, expText, actText, "Verify BuildUpZone", "Pick List");
		}
		else
		{
			verifyScreenText(sheetName, expText1, actText, "Verify BuildUpZone", "Pick List");
		}
		waitForSync(2);
		
		if(actText1.equals(expText1))
		{
			verifyScreenText(sheetName, expText1, actText1, "Verify BuildUpZone", "Pick List");
		}
		else
		{
			verifyScreenText(sheetName, expText, actText1, "Verify BuildUpZone", "Pick List");
		}
		waitForSync(2);
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}
			
	}
	
/**
 * Description... Verify Multiple BuildUp Location
 * @throws Exception
 */
	public void verifyMultipleBuildUpLocation() throws Exception {

		clickWebElement(sheetName, "lst_ZoneLocation;xpath", "BuildUpLocation", screenId);
		waitForSync(3);
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_ZoneLocation1;xpath")))
				.getText().replaceAll(" ", "");
		String actText1 = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_ZoneLocation2;xpath")))
				.getText().replaceAll(" ", "");
		String expText = data("BuildUpLocation2");
		String expText1 = data("BuildUpLocation");
		
		try
		{		
		if(actText.equals(expText))
		{
			verifyScreenText(sheetName, expText, actText, "Verify BuildUpLocation", "Pick List");
		}
		else
		{
			verifyScreenText(sheetName, expText1, actText, "Verify BuildUpLocation", "Pick List");
		}
		waitForSync(2);	
		if(actText1.equals(expText1))
		{
			verifyScreenText(sheetName, expText1, actText1, "Verify BuildUpLocation", "Pick List");
		}
		else
		{
			verifyScreenText(sheetName, expText, actText1, "Verify BuildUpLocation", "Pick List");
		}
		waitForSync(2);
		}		
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
/**
 * Description...
 * @throws InterruptedException
 * @throws IOException 
 */
	 public void saveDetails() throws InterruptedException, IOException
    {
	clickWebElement(sheetName, "btn_save;xpath", "Save Button",screenId);
	waitForSync(4);
    }
/**
 * Description... Click PickList Ok Button
 * @throws Exception
 */
	public void pickListOk() throws Exception
	{
		clickWebElement(sheetName, "btn_BuildUpLocOk;name", "BuildUpLocation ok", screenId);
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(2);
	}

/**
	 * Description... Checks the Select Flag Check Box
	 */
	public void selectForPickList(){
		checkIfUnchecked(sheetName, "chk_selectFlag;xpath", "Select Flag Check Box", screenName);
	}

/**
 * Description... Get element Verify AWB Numner in the AWB Details Table
 * @throws Exception
 */
	public void verifyPickListAWBNo() throws Exception {
		
		String actValue = getElementText(sheetName, "tbl_awbNo;xpath", "AWB No", screenName);
		verifyValueOnPageContains(actValue, data("AWBNo"),
				"1. List the flight in PickList Screen \n2. Add to List \n3. Verify AWB Number in AWB Details Table ",
				screenName, "AWB Number");

	}
/**
 * Description... List AWB
 * @param awbNo
 * @param ShipmentPrefix
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void listAWB(String awbNo, String ShipmentPrefix) throws InterruptedException, AWTException, IOException
{
	
	

	String sheetName = "Generic_Elements";
	awbNo = getPropertyValue(proppath, awbNo);

	System.out.println("AWBnumber is ---" + awbNo);
	waitForSync(2);
	enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",
			screenName);
	enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", awbNo, "AWB No", screenName);
	keyRelease("TAB");
	clickWebElement("PickList_WHS020", "btn_List;name", "List Button", screenName);
	waitForSync(2);
	
}
	/**
	 * Description... Clicks on select shipment check box
	 * @throws Exception
	 */
	public void selectShipment() throws Exception {		
		clickWebElement(sheetName, "chkbox_selectShipment;name", "Select Checkbox", screenId);
	}
/**
 * Description... Check All Location
 * @throws Exception
 */

public void checkAllLocation()throws Exception{
              checkIfUnchecked(sheetName, "chkbx_AllLocation;xpath", "Select Location Box", screenName);
       }
/**
 * Description...  Verify Shipment Listed      
 * @param AWBNo
 * @throws Exception
 */
       public void verifyShipmentListed(String AWBNo)throws Exception{
              String actual= driver.findElement(By.xpath("//td[@class='iCargoTableDataTd'][1]")).getText();
              String expected=AWBNo;
              if(actual.contains(expected)){
                     verifyScreenText(sheetName, expected, actual, "Verify List", "Pick List");
              }
              else{
                     verifyScreenText(sheetName, expected, actual, "Verify List", "Pick List");
              }
              
       }
  /**
   * Description...  Verify Error Messge    
   * @param expMsg
   * @throws InterruptedException
   */
              public void verifyErrorMessge(String expMsg) throws InterruptedException
              {
                     String actMsg = driver.findElement(By.xpath("//td[@class='ic-error-msg']")).getText();
                     if(actMsg.contains(expMsg)){
                           
                           System.out.println(expMsg+ " is displayed on " + screenName + " Page");
                           writeExtent("Pass", expMsg+ " is displayed on " + screenName + " Page" );
                           
                     }else{
                           
                           System.out.println(expMsg+ " is not displayed on " + screenName + " Page");
                           writeExtent("Fail", expMsg+ " is not displayed on " + screenName + " Page" );
                           Assert.assertFalse(true, expMsg + " is not displayed on " + screenName + " Page");
                                                
                     }
              


}
}
