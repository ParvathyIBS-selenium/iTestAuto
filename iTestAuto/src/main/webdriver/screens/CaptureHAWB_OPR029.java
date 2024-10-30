package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.WebFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CaptureHAWB_OPR029 extends CustomFunctions {

	public CaptureHAWB_OPR029(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "CaptureHAWB_OPR029";
	public String screenName = "CaptureHAWB";
/**
 * Description... List HAWB
 * @param awbNo
 * @param ShipmentPrefix
 * @throws InterruptedException
 * @throws IOException 
 */
	public void listHAWB(String awbNo,String ShipmentPrefix) throws InterruptedException, IOException {
		
		awbNo = getPropertyValue(proppath, "AWBNo");

		System.out.println("AWBnumber is ---" + awbNo);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(ShipmentPrefix), "Shipment Prefix", screenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", awbNo, "AWB No", screenName);
		clickWebElement(sheetName, "btn_list;name", "List Button", screenName);
		waitForSync(4);
		
	}
/**
 * Description... Verify HAWB Tbl details
 * @param verfCols
 * @param actVerfValues
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyHAWBTbldetails(int verfCols[], String actVerfValues[]) throws InterruptedException, IOException {
		
		/*int verfCols[] = { 5, 6, 11, 12, 13, 15 };
		String actVerfValues[] = { data("Pieces"), data("Weight"), data("ShipmentDesc"), data("Origin"),
				data("Destination"), data("Remarks") };*/

		verify_tbl_records_multiple_cols(sheetName, "tbl_HAWBdetails;xpath", "//td", verfCols, data("HAWB"),
				actVerfValues);
	}
	
	
	/**
     * Description...       Add HAWB Details
     * @param HAWB
     * @param Shipper
     * @param Consignee
     * @param Origin
     * @param Destination
     * @param Pieces
     * @param Weight
     * @throws Exception
     */
           public  void addHAWBDetailsFromOPR026(String HAWB, String Shipper, String Consignee, String Origin, String Destination, String Pieces,String Weight) throws Exception {
        	   String hawbNo=generateHAWB();
       		map.put(HAWB,hawbNo);

       		switchToWindow("child");
       		clickWebElement(sheetName, "inbx_houses;id", "Houses", screenName);
       		waitForSync(2);
       		enterValueInTextbox(sheetName, "inbx_houses;id", data(HAWB), "Houses", screenName);
       		keyPress("TAB");
       		enterValueInTextbox(sheetName, "inbx_shipper;name", data(Shipper), "Shipper", screenName);
       		keyPress("TAB");
       		enterValueInTextbox(sheetName, "inbx_consignee;name", data(Consignee), "Consignee", screenName);      
       		keyPress("TAB");
       		enterValueInTextbox(sheetName, "inbx_origin;name", data(Origin), "Origin", screenName);
       		keyPress("TAB");  
       		enterValueInTextbox(sheetName, "inbx_destination;name", data(Destination), "Destination", screenName);
       		keyPress("TAB");
       		enterValueInTextbox(sheetName, "inbx_pieces;name", data(Pieces), "Pieces", screenName);
       		keyPress("TAB");
       		enterValueInTextbox(sheetName, "inbx_weigth;name", data(Weight), "Weight", screenName);
       		keyPress("TAB");
       		waitForSync(2);
       		enterValueInTextbox(sheetName, "inbx_Desc;name", "Consol Shipment", "Remarks", screenName);
       		keyPress("TAB");
       		enterHAWBHSCode();
       		clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
       		waitForSync(3);
       		switchToWindow("getParent");
       		switchToDefaultAndContentFrame("OPR026");
           }


	/**
     * @author A-9175
     * Desc : Clicking check box based on PMKEY value
     * @param pmyKey
     * @throws InterruptedException
     */
    
    public void clickCheckBox(String pmyKey) throws InterruptedException {

		selectTableRecord(data(pmyKey), "chk_selectAWB;xpath", sheetName, 3);
		waitForSync(1);

	}
    /**
	 * Description... Verify HAWB Details
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyHAWBTableDetails(int verfCols[], String actVerfValues[],
			String pmKey) throws InterruptedException, IOException {
	
		verify_tbl_records_multiple_cols(sheetName, "table_hawb;xpath",
				"//td", verfCols, pmKey, actVerfValues);
	}
/**
 * Description... 	Click Add Update HAWB Btn
 * @throws Exception
 */
	public  void clickAddUpdateHAWBBtn() throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_Add;xpath", "Add/Update Button", screenName);
	}
/**
 * Description... Add Update HAWB Btn
 * @throws Exception
 */
	public void addUpdateHAWBBtn() throws Exception {
		screenName = "CaptureHAWB";
		switchToFrame("contentFrame", "OPR026");
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_Add;xpath", "Add/Update Button",
				screenName);
		switchToWindow("child");
		screenName = "CaptureHAWBForm";
	}
/**
 * Description... 	Add HAWB Details
 * @param HAWB
 * @param Shipper
 * @param Consignee
 * @param Origin
 * @param Destination
 * @param Pieces
 * @param Weight
 * @throws Exception
 */
	public  void addHAWBDetails(String HAWB, String Shipper, String Consignee, String Origin, String Destination, String Pieces,String Weight) throws Exception {
		switchToWindow("child");
		
		
		String hawbNo=generateHAWB();
		map.put(HAWB,hawbNo);

		
		switchToWindow("child");
		clickWebElement(sheetName, "inbx_houses;id", "Houses", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_houses;id", data(HAWB), "Houses", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_shipper;name", data(Shipper), "Shipper", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_consignee;name", data(Consignee), "Consignee", screenName);	
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_origin;name", data(Origin), "Origin", screenName);
		keyPress("TAB");	
		enterValueInTextbox(sheetName, "inbx_destination;name", data(Destination), "Destination", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_pieces;name", data(Pieces), "Pieces", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_weigth;name", data(Weight), "Weight", screenName);
		keyPress("TAB");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_Desc;name", "Aut HAWB Remarks", "Remarks", screenName);
		keyPress("TAB");
		enterHAWBHSCode();
		clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR029");
	}
/**
 * Description... 	Click HAWB Save Btn
 * @throws Exception
 */
	public  void clickHAWBSaveBtn() throws Exception {
		
		clickWebElement(sheetName, "btn_HAWBSave;id", "Save Button", screenName);
		waitForSync(2);
	}
/**
 * Description... Click HAWB Close Btn
 * @throws Exception
 */
	public  void clickHAWBCloseBtn() throws Exception {
		
		clickWebElement(sheetName, "btn_HAWBClose;id", "Close Button", screenName);
		waitForSync(2);
	}
/**
 * Description... Postal Code For Consignee
 * @param ConsigneeZipCode
 * @throws Exception
 */
public void postalCodeForConsignee(String ConsigneeZipCode)
                     throws Exception {
              screenName = "CaptureHAWB";
              switchToWindow("storeParent");
              switchToFrame("default");
              switchToFrame("contentFrame", "OPR026");

              clickWebElement(sheetName, "inbx_clkonHAWBDetails;xpath",
                           "HAWB Details", screenName);
              clickAddUpdateHAWBBtn();
              waitForSync(2);
              switchToWindow("child");
              enterValueInTextbox(sheetName, "inbx_postlCode;xpath",
                           data(ConsigneeZipCode), "postalCode", screenName);
              keyPress("TAB");
              clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
              waitForSync(3);
              screenName = "CaptureHAWB";
              switchToWindow("getParent");
              switchToFrame("default");
              switchToFrame("contentFrame", "OPR026");
       }
/**
 * Description... Select HAWB
 * @param HAWBNo
 */
       

public void selectHAWB(String HAWBNo){
	
	String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
	String dynxpath = xpath + "[contains(.,'" + HAWBNo + "')]//td[1]//input";
	
	try{
		
		driver.findElement(By.xpath(dynxpath)).click();			
		
	 }catch(Exception e){
		 
		System.out.println("Could not click on" + HAWBNo + "checkox on " + screenName + " Page");
		writeExtent("Fail", "Could not click on" + HAWBNo + "checkox on " + screenName + " Page");
		Assert.assertFalse(true, "Could not click on" + HAWBNo + "checkox on " + screenName + " Page");
		 
	 }

}

/**
 * Description... Update HAWB Remarks
 * @param Remarks
 * @throws Exception
 */
public void updateHAWBRemarks(String Remarks) throws Exception{
	
	switchToWindow("child");
    enterValueInTextbox(sheetName, "inbx_HAWBremarks;id", data(Remarks), "postalCode", screenName);
    keyPress("TAB");
    clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
    waitForSync(3);
    screenName = "CaptureHAWB";
    switchToWindow("getParent");
		 
	 }

/**
	 * @author A-9847
	 * @Desc To enter the HS Code at HAWB level
	 * @throws InterruptedException
	 */
	public void enterHAWBHSCode() throws InterruptedException{

		String hsCode= getAttributeWebElement("CaptureHAWB_OPR029", "inbx_hawbHS;xpath", "HS Code", "value", screenName);		
		if(hsCode.equals("")) 		
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_hawbHS;xpath", "HS12345" , "HS Code", screenName);

	}



/**
 * Description... Verify Manually Updated
 * @param Updated_Notupdated
 * @throws Exception
 */

public void verifyManuallyUpdated(String Updated_Notupdated) throws Exception{
	
	By element = getElement(sheetName, "chk_manuallyUpdated;name");
	switch (Updated_Notupdated)
	{
		
		case "Updated":
			if(driver.findElement(element).isSelected()){
				customFunction.onPassUpdate(screenName, "manually updated checkbox is checked", "manually updated checkbox is checked",
                    "Capturing house AWB", "Check the Manually updated flag is updated or not");

					} else {
					Status = false;
					customFunction.onFailUpdate(screenName, "manually updated checkbox is checked", "manually updated checkbox is not checked",
		                    "Capturing house AWB", "Check the Manually updated flag is updated or not");	
				
			}
			
		case "Not Updated":
			if(!driver.findElement(element).isSelected()){
				customFunction.onPassUpdate(screenName, "manually updated checkbox is not checked", "manually updated checkbox is not checked",
                    "Capturing house AWB", "Check the Manually updated flag is updated or not");

					} else {
					Status = false;
					customFunction.onFailUpdate(screenName, "manually updated checkbox is not checked", "manually updated checkbox is checked",
		                    "Capturing house AWB", "Check the Manually updated flag is updated or not");	
				
			}	
			
			
			
	}
		 
	 }

/**
 * Description... Click HAWB Details Btn
 * @throws Exception
 */
public void clickHAWBDetailsBtn() throws Exception {

		clickWebElement(sheetName, "btn_hawbDetails;id", "HAWB Details Button", screenName);
		waitForSync(2);
	}
/**
 * Description... Verify AWB Details
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyAWBDetails() throws InterruptedException, IOException {
		String actText = getElementText(sheetName, "tbl_AWBdetails;xpath", "AWB Details Table text", screenName);
		verifyValueOnPageContains(actText, data("Pieces"), "1. Process FHL\n 2. Verify Pieces", screenName,
				"No of Pieces");
		verifyValueOnPageContains(actText, data("Weight"), "1. Process FHL\n 2. Verify Weight", screenName, "Weight");
		verifyValueOnPageContains(actText, data("Origin"), "1. Process FHL\n 2. Verify Origin", screenName, "Origin");
		verifyValueOnPageContains(actText, data("Destination"), "1. Process FHL\n 2. Verify Destination", screenName,
				"Destination");
		verifyValueOnPageContains(actText.toUpperCase(), data("ShipperFHL").toUpperCase(),
				"1. Process FHL\n 2. Verify Shipper", screenName, "Shipper");
		verifyValueOnPageContains(actText.toUpperCase(), data("ConsigneeFHL").toUpperCase(),
				"1. Process FHL\n 2. Verify Consignee", screenName, "Consignee");
		verifyValueOnPageContains(actText.toUpperCase(), data("ShipmentDesc").toUpperCase(),
				"1. Process FHL\n 2. Verify Shipment Description", screenName, "Shipment Description");
		verifyValueOnPageContains(actText.toUpperCase(), data("RemarksFHL").toUpperCase(),
				"1. Process FHL\n 2. Verify HAWB Remarks", screenName, "HAWB Remarks");

	}
/**
 * Description... Verify Manually Updated CheckBox Chkd
 */
	public void verifyManuallyUpdatedCheckBoxChkd() {
		String checked = getAttributeUsingJavascript(sheetName, "chk_manuallyUpdated;name", "Manually Updated checkbox",
				screenName, "checked");
		if (checked.equals("true"))
			verifyValueOnPage(true, true, "Verify Manually Updated check box is checked ", screenName,
					"Manually Updated checkbox is checked ");
		else
			verifyValueOnPage(true, false, "Verify Manually Updated check box is checked ", screenName,
					"Manually Updated checkbox is checked ");
	}
/**
 * Description... Add Multiple HAWB Details
 * @param HAWB
 * @param Shipper
 * @param Consignee
 * @param Origin
 * @param Destination
 * @param Pieces
 * @param Weight
 * @param Slac
 * @throws Exception
 */
	public void addMultipleHAWBDetails(String HAWB, String Shipper, String Consignee, String Origin, String Destination,
			String Pieces, String Weight, String Slac) throws Exception {
		String hawbNo=generateHAWB();
		map.put(HAWB,hawbNo);

		switchToWindow("child");
		clickWebElement(sheetName, "inbx_houses;id", "Houses", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_houses;id", data(HAWB), "Houses", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_shipper;name", data(Shipper), "Shipper", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_consignee;name", data(Consignee), "Consignee", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_origin;name", data(Origin), "Origin", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_destination;name", data(Destination), "Destination", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_pieces;name", data(Pieces), "Pieces", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_weigth;name", data(Weight), "Weight", screenName);
		keyPress("TAB");
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_Desc;name", "Aut HAWB Remarks", "Remarks", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_slacPieces;id", data(Slac), "Destination", screenName);
		keyPress("TAB");

	}
/**
 * Description... Click HAWB OK Btn
 * @throws Exception
 */
	public void clickHAWBOKBtn() throws Exception {
		waitForSync(3);
		clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
		waitForSync(3);
		switchToWindow("getParent");
	}
/**
 * Description... Click New House Add New Btn
 * @throws Exception
 */
	public void clickNewHouseKBtn() throws Exception {
		waitForSync(3);
		clickWebElement(sheetName, "btn_addNew;id", "Add New", screenName);
		waitForSync(3);
	}
/**
 * Description... Update HAWB Pieces Weight Slac
 * @param Pieces
 * @param Weight
 * @param Slac
 * @throws Exception
 */
	public void updateHAWBPiecesWeightSlac(String Pieces, String Weight, String Slac) throws Exception {

		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_pieces;name", data(Pieces), "Pieces", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_weigth;name", data(Weight), "Weight", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_slacPieces;id", data(Slac), "Destination", screenName);
		keyPress("TAB");
		waitForSync(5);
		clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
		waitForSync(3);
		screenName = "CaptureHAWB";
		switchToWindow("getParent");

	}
/**
 * Description... Click HAWB CheckBox 1
 * @throws Exception
 */
	public void clickHAWBCheckBox1() throws Exception {
		clickWebElement(sheetName, "chk_HAWB1;xpath", "HAWB Check Box 1", screenName);
	}
/**
 * Description... Click HAWB CheckBox 2
 * @throws Exception
 */
	public void clickHAWBCheckBox2() throws Exception {
		clickWebElement(sheetName, "chk_HAWB2;xpath", "HAWB Check Box 2", screenName);
	}
/**
 * Description... Edit Shipment Description
 * @param shipDesc
 * @throws Exception
 */
	public void editShipmentDesc(String shipDesc) throws Exception {
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_Desc;name", shipDesc, "Shipment Description", screenName);
		clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR029");
	}
/**
 * Description... Edit Harmonised Commodity Code
 * @param harmonisedCommodityCode
 * @throws Exception
 */
	public void editHarmonisedCommodityCode(String harmonisedCommodityCode) throws Exception {
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_harmonisedCommodityCode;name", harmonisedCommodityCode,
				"Harmonised Commodity Code", screenName);
		clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR029");
	}
/**
 * Description... Verify HAWB Details Consignee
 * @throws Exception
 */
	public void verifyHAWBDetailsConsignee() throws Exception {
		switchToWindow("child");
		String expConsigneeCode = getAttributeWebElement(sheetName, "inbx_consignee;name", "Consignee Code", "value",
				screenName);
		String expConsigneeName = getAttributeWebElement(sheetName, "inbx_consigneeName;name", "Consignee Name",
				"value", screenName);
		String expConsigneeAdd = getElementText(sheetName, "inbx_consigneeAddress;name", "Consignee Address",
				screenName);
		String expConsigneeCity = getAttributeWebElement(sheetName, "inbx_consigneeCity;name", "Consignee City",
				"value", screenName);
		String expConsigneePostalCode = getAttributeWebElement(sheetName, "inbx_consigneePostalCode;name",
				"Consignee Postal Code", "value", screenName);
		String expConsigneeState = getAttributeWebElement(sheetName, "inbx_consigneeState;name", "Consignee State",
				"value", screenName);
		String expConsigneeCountry = getAttributeWebElement(sheetName, "inbx_consigneeCountry;name",
				"Consignee Country", "value", screenName);

		verifyValueOnPageContains(data("ConsigneeCodeOPR029"), expConsigneeCode, "Verify Consignee Code", screenName,
				"Consignee Code");
		verifyValueOnPageContains(data("ConsigneeName"), expConsigneeName, "Verify Consignee Name", screenName,
				"Consignee Name");
		verifyValueOnPageContains(data("ConsigneeAdd"), expConsigneeAdd, "Verify Consignee Address", screenName,
				"Consignee Address");
		verifyValueOnPageContains(data("ConsigneeCity"), expConsigneeCity, "Verify Consignee City", screenName,
				"Consignee City");
		verifyValueOnPageContains(data("ConsigneePostalCode"), expConsigneePostalCode, "Verify Consignee Postal Code",
				screenName, "Consignee Postal Code");
		verifyValueOnPageContains(data("ConsigneeState"), expConsigneeState, "Verify Consignee State", screenName,
				"Consignee Code State");
		verifyValueOnPageContains(data("ConsigneeCountry"), expConsigneeCountry, "Verify Consignee Country", screenName,
				"Consignee Code Country");

	}
/**
 * Description... Update Remarks
 * @param Remarks
 * @throws Exception
 */
    public void updateRemarks(String Remarks) throws Exception {

           switchToWindow("child");
           waitForSync(3);
           keyPress("SCROLLDOWNMOUSE");
           waitForSync(3);
           enterValueInTextbox(sheetName, "inbx_remarks;name",Remarks, "Remarks", screenName);
           keyPress("TAB");
           clickWebElement(sheetName, "btn_hawbOK;id", "OK", screenName);
           waitForSync(3);
           screenName = "CaptureHAWB";
           switchToWindow("getParent");

    }
/**
 * Description... Verify Remarks
 * @param Remarks
 * @throws Exception
 */
    public void verifyRemarks(String Remarks) throws Exception {

           String actText = getElementText(sheetName, "tbl_Remarks;xpath", "Remarks", screenName);
           verifyValueOnPageContains(actText, data(Remarks),
                        "1. Process FHL\n 2. Verify HAWB Remarks", screenName, "HAWB Remarks");
    }


}