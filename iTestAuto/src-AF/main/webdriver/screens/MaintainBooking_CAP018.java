/**
* Author : A-7037
* Date Created/ Modified : 20/02/2019
* Description : To perform operations on Maintain Booking (CAP018) Screen
*/

package screens;

import java.awt.AWTException;
import java.awt.Robot;
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

public class MaintainBooking_CAP018 extends CustomFunctions{

	String sheetName = "MaintainBooking_CAP018";
	String screenName = "Maintain Booking : CAP018";

	public MaintainBooking_CAP018(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	/**
	 * Description... Takes the fresh AWB from the stock and list it in CAP018
	 * screen, throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public boolean listNewAwb(String awbNumber) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(awbNumber), "Awb Number", screenName);
		clickWebElement(sheetName, "btn_list;id", "List", screenName);
		waitForSync(3);
		switchToFrame("default");
		waitForSync(1);

		boolean verifyBooking = verifyElementDisplayed("Generic_Elements", "btn_yes;xpath", "", screenName,
				"Yes Button");

		if (verifyBooking) {
			clickWebElement("Generic_Elements", "btn_yes;xpath", "List", screenName);
			waitForSync(5);
			return true;
		} else {
			clickWebElement(sheetName, "btn_clear;xpath", "Clear", screenName);
			return false;
		}
	}
	/**
	 * @Description: Getting ChargeCode
	 * @author A-9175
	 * @param OTchargeCode
	 * @param OTchargeCodeVal
	 */
	public void getChargeCode(String OTchargeCode, String OTchargeCodeVal)
	{
		String charge="";
		try{
			String locator=xls_Read.getCellValue(sheetName, "inbx_chargeCodeValue;xpath");
			locator=locator.replace("ChargeCode", OTchargeCode);
			charge=driver.findElement(By.xpath(locator)).getAttribute("value");
			map.put(OTchargeCodeVal, charge);
			writeExtent("Pass", "Sucessfully Returned Charge code vale for "+OTchargeCode+ " As "+charge);
		}catch (Exception e) {
			writeExtent("Pass", "Failed to  Return Charge code vale for "+OTchargeCode+ " As "+charge);
		}
	}
	

	/**
	 * Selecting Rating tab
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void clickRatingTab() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_ratingTab;id", "Rating Tab", screenName);
		waitForSync(2);
		
	}
	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : cancel booking
	 */
	public void clickcancelbooking() throws InterruptedException, IOException{
		waitForSync(5);
		clickWebElement(sheetName, "btn_Cancel;xpath", "Cancel", screenName);
		waitForSync(5);
		try{
			switchToFrame("default");
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
				
			}
		}
		catch (Exception e){

		}
		switchToFrame("contentFrame","CAP018");
	}

	/**
	 * 
	 * @param irregularity
	 * @param irregularityremark
	 * @throws Exception
	 * Desc : capture irregularity
	 */
	public void captureirregularitydetails(String irregularity,String irregularityremark) throws Exception {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(4);
		enterValueInTextbox(sheetName, "btn_irregularity;xpath", data(irregularity), "irregularity", screenName);
		enterValueInTextbox(sheetName, "btn_irregularityremark;id", data(irregularityremark), "irregularityremark", screenName);
		clickWebElement(sheetName, "btn_Ok;name", "Ok button", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP018");
	}

	/**
	 * Description... Verify Rate details
	 * @param verfCols
	 * @author A-9175
	 * @param actVerfValues
	 * @throws IOException 
	 */
	public void verifyRateDetails(String rate) throws IOException
	{
		String Rate=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "table_rateDetailsTable;xpath"))).getText();
		if (Rate.trim().equals(data(rate))) {
			writeExtent("Pass", "Rate Details found as : "+Rate);
		}else
		{
			writeExtent("Fail", "Rate Details found as : "+Rate);
		}
	}	
	
	/**
	 * @author A-7271
	 * @param key
	 * @throws InterruptedException
	 * Desc : store chargable wt in the map
	 */
  public void storeChargableWeight(String key) throws InterruptedException
  {
	  String chargableWeight=getElementText(sheetName, "inbx_chargableWt;xpath","Chargable Weight", screenName).replaceAll(",", "");
	  map.put(key, chargableWeight);
			
  }
	

	/**
	 * @author A-8783
	 * @param origin
	 * @param destination
	 * @param shippingDate
	 * @param aircraft
	 * @param flightcode
	 * @param i
	 * @param checkAll
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectFlightfromPopup(String origin, String destination, String shippingDate, String aircraft, String flightcode,String index, boolean checkAll) throws InterruptedException, IOException {
		try {
			clickWebElement(sheetName, "btn_editIcon;xpath", "Edit icon", screenName);
			waitForSync(2);
			enterValueInTextbox(sheetName,"inbx_flightPopupOrigin;id", data(origin), "Enter origin", screenName);
			enterValueInTextbox(sheetName,"inbx_flightPopupDestination;id", data(destination), "Enter destination", screenName);
			enterValueInTextbox(sheetName,"inbx_flightPopupShippingDate;id", data(shippingDate), "Enter shipping date", screenName);
			clickWebElement(sheetName, "btn_selectAircraft;id", "Select dropdown", screenName);
			waitForSync(2);
		
			if(checkAll) {
			clickWebElement(sheetName, "chk_checkAll;xpath", "Select dropdown", screenName);
			writeExtent("Pass", "Checked all as aircraftType on "
					+ screenName + " Page");
		}
		else {
			String locator=xls_Read.getCellValue(sheetName, "chk_aircraftType;xpath");
			locator=locator.replace("aircraftType", data(aircraft));
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", "Entered " + data(aircraft) + " as aircraftType on "
					+ screenName + " Page");
		}
			
			//Enter flight code as keyword
			enterValueInTextbox(sheetName,"inbx_SearchFlightNumber;xpath", flightcode, "Enter keyword", screenName);
			waitForSync(3);
			
			//Select flight based on index
			String locator1=xls_Read.getCellValue(sheetName, "btn_selectFirstFlightPopup;xpath").replace("value", index);
			 driver.findElement(By.xpath(locator1)).click();
			 writeExtent("Pass", "Selected Flight from "+ screenName + " Page");
					
			clickWebElement(sheetName, "btn_selectFlightpopupOK;xpath", "OK", screenName);
			switchToFrame("default");
			switchToFrame("contentFrame","CAP018");
		}
			catch (Exception e) {
			writeExtent("Fail","Could not select flight from the select flight pop up");
		}
		}
	/**
	 * @Description : Get flight details
	 * @author A-9175
	 * @param rows
	 * @param Org
	 * @param Dest
	 * @param flightNum
	 * @param FltDate
	 * @param pcs
	 * @param wgt
	 * @param vol
	 */
public void getFlightDetails(int rows,String Org,String Dest, String flightNum,String FltDate,String pcs,String wgt,String vol) {
	for (int i = 1; i <= rows; i++) {

		String org = getAttributeWebElement(sheetName, "inbx_fltOrigin" + i + ";xpath", "Origin", "value", screenName);
		String dest= getAttributeWebElement(sheetName, "inbx_fltDestination" + i + ";xpath", "Destination", "value", screenName);
		String fltNum = getAttributeWebElement(sheetName, "inbx_fltNumber" + i + ";xpath", "Flight Number", "value", screenName);
		String fltDate = getAttributeWebElement(sheetName, "inbx_fltDate" + i + ";xpath", "Flight Date", "value", screenName);
		String fltPcs = getAttributeWebElement(sheetName, "inbx_fltPcs" + i + ";xpath", "Total Pieces", "value", screenName);
		String fltWt = getAttributeWebElement(sheetName, "inbx_fltWt" + i + ";xpath", "Total Weight", "value", screenName);
		String fltVol= getAttributeWebElement(sheetName, "inbx_fltVolume" + i + ";xpath", "Total Weight", "value", screenName);
		map.put(Org, org);
		map.put(Dest, dest);
		map.put(flightNum, fltNum);
		map.put(FltDate, fltDate);
		map.put(pcs, fltPcs);
		map.put(wgt, fltWt);
		map.put(vol, fltVol);

	}
	
}

/**
 * @Description : Capture Dimension Details
 * @author A-9175
 * @param dimpcs
 * @param dimwgt
 * @param dimlen
 * @param dimwidth
 * @param dimheight
 * @throws Exception
 */
public void captureDimensionDetails(String dimpcs,String dimwgt,String dimlen,String dimwidth,String dimheight ) throws Exception {
	
	switchToWindow("storeParent");
	switchToWindow("child");
	enterValueInTextbox(sheetName, "inbx_dimPcs;xpath", data(dimpcs), "Pieces", screenName);
	enterValueInTextbox(sheetName, "inbx_dimWgt;xpath", data(dimwgt), "Weight", screenName);
	enterValueInTextbox(sheetName, "inbx_dimLen;xpath", dimlen, "Length", screenName);
	enterValueInTextbox(sheetName, "inbx_dimWid;xpath", dimwidth, "Width", screenName);
	enterValueInTextbox(sheetName, "inbx_dimHgt;xpath", dimheight, "Height", screenName);
	performKeyActions(sheetName, "inbx_dimHgt;xpath", "TAB", "Height", screenName);
	clickWebElement(sheetName, "btn_ok;xpath", "OK", screenName);
	
	waitForSync(2);
	switchToFrame("default");
	clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes", screenName);
	switchToWindow("getParent");
}

/**
 * @Description : Click dimension button
 * @author A-9175
 * @param row
 */
public void clickDimension(String row) {
	String dimloc=xls_Read.getCellValue(sheetName, "btn_dim;xpath");
	dimloc=dimloc.replace("Loc", row);
	driver.findElement(By.id(dimloc)).click();
}

/**
 * @Description : Enter Flight level Details without shipment details
 * @author A-9175
 * @param rows
 * @param origin
 * @param destination
 * @param flightNo
 * @param flightDate
 * @param force
 * @param forceOption
 * @throws InterruptedException
 */
public void enterFlightDetails(int rows, String origin[], String destination[], String flightNo[],
		String flightDate[],boolean force,String forceOption) throws InterruptedException {

	for (int i = 1; i <= rows; i++) {

		enterValueInTextbox(sheetName, "inbx_fltOrigin" + i + ";xpath", data(origin[i - 1]), "Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_fltDestination" + i + ";xpath", data(destination[i - 1]),
				"Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_fltNumber" + i + ";xpath", data(flightNo[i - 1]), "FlightNo",
				screenName);
		enterValueInTextbox(sheetName, "inbx_fltDate" + i + ";xpath", data(flightDate[i - 1]), "Flight Date",
				screenName);
		 if(force)
         {
                waitForSync(3);
                selectValueInDropdown(sheetName, "lst_bkgStatus"+ i + ";xpath", data(forceOption), "Force Option", "VisibleText");
                waitForSync(3);
         }
  

	}
}
	


	
	/**
	 * @author A-8783
	 *Desc - Click on select flight button
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void selectFlight() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_selectFlight;xpath", "Select Flight", screenName);
		waitForSync(4);
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(4);
	}

/**
       * Description... Enter pieces, Weight, Volume  in
       * flight details section of CAP018 screen,throws exception otherwise
       * 
        * @return
       * @throws InterruptedException
       */
       
       public void enterFlightShipmentDetails(int i, String pcs[], String wt[], String vol[],boolean force,String forceOption) throws InterruptedException {

                    enterValueInTextbox(sheetName, "inbx_fltPcs" + i + ";xpath", data(pcs[i - 1]), "Pieces", screenName);
                    enterValueInTextbox(sheetName, "inbx_fltWt" + i + ";xpath", data(wt[i - 1]), "Weight", screenName);
                    enterValueInTextbox(sheetName, "inbx_fltVolume" + i + ";xpath", data(vol[i - 1]), "Volume", screenName);
                    
                    if(force)
                    {
                           waitForSync(3);
                           selectValueInDropdown(sheetName, "lst_bkgStatus"+ i + ";xpath", data(forceOption), "Force Option", "VisibleText");
                           waitForSync(3);
                    }
             
       }

	
	/**
	 * Description... Enter the Shipment details origin, destination,
	 * Agentcode and shipping date by passing the values from datasheet or
	 * property file in CAP018 screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void enterShipmentDetails(String origin, String destination,String agentCode,
			String shippingDate) throws InterruptedException, AWTException {
		//switchToFrame("contentFrame", "CAP018");
		enterValueInTextbox(sheetName, "inbx_origin;xpath", data(origin), "Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_destination;xpath", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_agentCode;xpath", data(agentCode), "Agent Code", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_shipmentDate;xpath", data(shippingDate), "Shipping Date", screenName);
		waitForSync(2);
	}

	/**
	 * @author A-9847
	 * @desc To verify flight level details on CAP018 screen
	 * @param rows
	 * @param origin
	 * @param destination
	 * @param flightNo
	 * @param flightDate
	 * @param pcs
	 * @param wt
	 * @param vol
	 * @throws InterruptedException
	 */
	
	public void verifyFlightLevelDetails(int rows, String origin[], String destination[], String flightNo[],
			String flightDate[], String pcs[], String wt[], String vol[]) throws InterruptedException {

		for (int i = 1; i <= rows; i++) {

			String org = getAttributeWebElement(sheetName, "inbx_fltOrigin" + i + ";xpath", "Origin", "value", screenName);
			String dest= getAttributeWebElement(sheetName, "inbx_fltDestination" + i + ";xpath", "Destination", "value", screenName);
			String fltNum = getAttributeWebElement(sheetName, "inbx_fltNumber" + i + ";xpath", "Flight Number", "value", screenName);
			String fltDate = getAttributeWebElement(sheetName, "inbx_fltDate" + i + ";xpath", "Flight Date", "value", screenName);
			String fltPcs = getAttributeWebElement(sheetName, "inbx_fltPcs" + i + ";xpath", "Total Pieces", "value", screenName);
			String fltWt = getAttributeWebElement(sheetName, "inbx_fltWt" + i + ";xpath", "Total Weight", "value", screenName);
			String fltVol= getAttributeWebElement(sheetName, "inbx_fltVolume" + i + ";xpath", "Total Weight", "value", screenName);

			verifyScreenText(screenName ,data(origin[i-1]), org, "Flight Origin","Flight Origin");
			verifyScreenText(screenName ,data(destination[i-1]), dest, "Flight Destination","Flight Destination");
			verifyScreenText(screenName ,data(flightNo[i-1]), fltNum, "Flight Number","Flight Number");
			verifyScreenText(screenName ,data(flightDate[i-1]), fltDate, "Flight Date","Flight Date");
			verifyScreenText(screenName ,data(pcs[i-1]), fltPcs, "Total Pieces","Total Pieces");
			verifyScreenText(screenName ,data(wt[i-1]), fltWt, "Total Weight","Total Weight");
			verifyScreenText(screenName ,data(vol[i-1]), fltVol, "Total Volume","Total Volume");

		}
	}


/**
 * @author A-9175
 * @Description: Shipment level details
 * @param row
 * @param commodity
 * @param pcs
 * @param wt
 * @param vol
 * @throws InterruptedException
 * @throws IOException
 */
    public void enterShipmentLevelDetails(String row,String commodity, String pcs, String wt, String vol)
            throws InterruptedException, IOException {
    		try {
    			String commoloc=xls_Read.getCellValue(sheetName, "inbx_commodityCode;id");
        		commoloc=commoloc.replace("Loc", row);
    			driver.findElement(By.id(commoloc)).clear();
    			driver.findElement(By.id(commoloc)).sendKeys(commodity);

        		String pcsloc=xls_Read.getCellValue(sheetName, "inbx_pieces;id");
        		pcsloc=pcsloc.replace("Loc", row);
    			driver.findElement(By.id(pcsloc)).clear();
    			driver.findElement(By.id(pcsloc)).sendKeys(pcs);
        		
        		String wgtloc=xls_Read.getCellValue(sheetName, "inbx_wgt;xpath");
        		wgtloc=wgtloc.replace("Loc", row);
    			driver.findElement(By.xpath(wgtloc)).clear();
    			driver.findElement(By.xpath(wgtloc)).sendKeys(wt);
    			
    			String volloc=xls_Read.getCellValue(sheetName, "inbx_volumeval;xpath");
    			volloc=volloc.replace("Loc", row);
    			driver.findElement(By.xpath(volloc)).clear();
    			driver.findElement(By.xpath(volloc)).sendKeys(vol);
    			
    			writeExtent("Pass", "Shipment details Captured");
    		} catch (Exception e) {
    			writeExtent("Fail", "Failed to capture Shipment details");
    		}
		
}
    /**
     * @author A-9175
     * @Description : click add button
     * @throws InterruptedException
     * @throws IOException
     */
    public void clickAddShipment() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_addshipment;xpath", "Add", screenName);
		waitForSync(2);
	}

	/**
	 * Description... List an AWB number in CAP018 screen. screen, throws
	 * exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException,
	 *             AWTException
	 * @throws IOException 
	 */

	public void listAwb(String awbNumber) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(awbNumber), "Awb Number", screenName);
		clickWebElement(sheetName, "btn_list;id", "List", screenName);
		Thread.sleep(3000);
	}

	/**
	 * Description... Verify Commodity details like shipment description and SCC
	 * code in CAP018 screen screen, throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyCommodityDetails(String shipmentDesc, String scc) throws InterruptedException {
		String shipmentDes = getAttributeWebElement(sheetName, "inbx_shipmentDes;xpath", "Shipment Description",
				"value", screenName);

		if (shipmentDes.contains(data(shipmentDesc))) {
			writeExtent("Pass", "Shipment description matches");
		} else {
			writeExtent("Fail",
					"Shipment description not matches ; Expected : " + shipmentDesc + " Actual : " + shipmentDes);

			Assert.assertFalse(true,
					"Shipment description not matches ; Expected : " + shipmentDesc + " Actual : " + shipmentDes);
		}
		String shipmentScc = getAttributeWebElement(sheetName, "inbx_shipmentSCC;name", "Shipment SCC", "value",
				screenName);
		if (shipmentScc.contains(data(scc))) {
			writeExtent("Pass", "Shipment scc matches");
		} else {
			writeExtent("Fail", "Shipment scc not matches ; Expected : " + data(scc) + " Actual : " + shipmentScc);
			Assert.assertFalse(true, "Shipment scc not matches ; Expected : " + data(scc) + " Actual : " + shipmentScc);
		}
	}

	/**
	 * Description... Verify the Booking Status of a shipment in CAP018 screen
	 * screen, throws exception otherwise	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyBkgStatus(String bookingStatus) throws InterruptedException {

		String sts = getAttributeWebElement(sheetName, "inbx_bookingStatus;xpath", "BKG STATUS", "value", screenName);
		if (sts.equals(data(bookingStatus))) {
			writeExtent("Pass", "Booking status Matched with Expected Status "+data(bookingStatus));
		} else {
			writeExtent("Fail", "Booking status not matches ; Expected : " + data(bookingStatus + " Actual : " + sts));
		}

	}

	/**
	 * Description... Verify Origin,Destination, Flight Number and Date on
	 * segment level in CAP018 screen,throws exception otherwise	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void verifySegmentLevelDetails() throws InterruptedException, IOException {
		waitForSync(5);
		List<WebElement> rows = driver.findElements(By.xpath("//tbody[@id='flightRows']//tr"));
		int count = rows.size();
		System.out.println("ROW COUNT : " + count);
		for (int i = 1; i < count; i++) {
			String pmKey = data("FlightNo" + (i - 1));
			System.out.println("PMMKEY" + pmKey);
			String[] actVerfValues = { data("ActOrgin" + (i - 1)), data("ActDestination" + (i - 1)),
					data("BOOKINGDATE" + (i - 1)) };
			int[] verfCols = { 2, 3, 6 };
			customFunction.verify_tbl_records_multiple_cols(sheetName, "tble_flightDetails;xpath", "input", verfCols,
					pmKey, actVerfValues);
		}
	}

	/**
	 * Description... Verify Pieces, Weight and Volume details on shipment level
	 * in CAP018 screen,throws exception otherwise	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyShipmentLevelDetails(String pcs, String wt, String vol) throws InterruptedException {
		String pieces = getAttributeWebElement(sheetName, "inbx_fltPcs;xpath", "Pcs", "value", screenName);

		if (pieces.equals(data(pcs))) {
			writeExtent("Pass", "Shipment pieces matches");
		} else {
			writeExtent("Fail", "Shipment pieces not matches ; Expected : " + data(pcs) + " Actual : " + pieces);
			Assert.assertFalse(true, "Shipment pieces not matches ; Expected : " + data(pcs) + " Actual : " + pieces);
		}
		String weight = getAttributeWebElement(sheetName, "inbx_fltWt;xpath", "Wt", "value", screenName);

		if (weight.equals(data(wt))) {
			writeExtent("Pass", "Shipment weight matches");
		} else {
			writeExtent("Fail", "Shipment weight not matches ; Expected : " + data(wt) + " Actual : " + weight);
			Assert.assertFalse(true, "Shipment weight not matches ; Expected : " + data(wt) + " Actual : " + weight);
		}
		String volume = getAttributeWebElement(sheetName, "inbx_fltVolume;xpath", "Volume", "value", screenName);

		if (volume.equals(data(vol))) {
			writeExtent("Pass", "Shipment volume matches");
		} else {
			writeExtent("Fail", "Shipment volume not matches ; Expected : " + data(vol) + " Actual : " + volume);
			Assert.assertFalse(true, "Shipment volume not matches ; Expected : " + data(vol) + " Actual : " + volume);
		}
	}

	/**
	 * Description... Verify LAT and TOA time and date details in CAP018
	 * screen,throws exception otherwise	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyDateDetails(String latDate, String latTime, String toaDate, String toaTime)
			throws InterruptedException {
		String latDat = getAttributeWebElement(sheetName, "inbx_latDate;xpath", "lat date", "value", screenName);

		if (latDat.equals(data(latDate))) {
			writeExtent("Pass", "LAT date matches");
		} else {
			writeExtent("Fail", "LAT date not matches ; Expected : " + data(latDate) + " Actual : " + latDat);
			Assert.assertFalse(true, "LAT date not matches ; Expected : " + data(latDate) + " Actual : " + latDat);
		}
		String latTim = getAttributeWebElement(sheetName, "inbx_latTime;xpath", "LAT time", "value", screenName);

		if (latTim.equals(data(latTime))) {
			writeExtent("Pass", "LAT Time matches");
		} else {
			writeExtent("Fail", "LAT time not matches ; Expected : " + data(latTime) + " Actual : " + latTim);
			Assert.assertFalse(true, "LAT time not matches ; Expected : " + data(latTime) + " Actual : " + latTim);
		}
		String toaDat = getAttributeWebElement(sheetName, "inbx_toaDate;xpath", "toa date", "value", screenName);

		if (toaDat.equals(data(toaDate))) {
			writeExtent("Pass", "TOA date matches");
		} else {
			writeExtent("Fail", "TOA date not matches ; Expected : " + data(toaDate) + " Actual : " + toaDat);
			Assert.assertFalse(true, "TOA date not matches ; Expected : " + data(toaDate) + " Actual : " + toaDat);
		}
		String toaTim = getAttributeWebElement(sheetName, "inbx_toaTime;xpath", "TOA time", "value", screenName);

		if (toaTim.equals(data(toaTime))) {
			writeExtent("Pass", "TOA Time matches");
		} else {
			writeExtent("Fail", "TOA time not matches ; Expected : " + data(toaTime) + " Actual : " + toaTim);
			Assert.assertFalse(true, "TOA time not matches ; Expected : " + data(toaTime) + " Actual : " + toaTim);
		}
	}

	/**
	 * Description... Verify Agent code in CAP018 screen,throws exception
	 * otherwise	 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyAgentCode(String AgentCode) {
		String agentcod = getAttributeWebElement(sheetName, "inbx_agentCode;xpath", "Agent Code", "value", screenName);

		if (agentcod.equals(data(AgentCode))) {
			writeExtent("Pass", "AgentCode matches");
		} else {
			writeExtent("Fail", "AgentCode not matches ; Expected : " + data(AgentCode) + " Actual : " + agentcod);
		}
	}

	/**
	 * Description... Verify Replacement value in commodity code in CAP018
	 * screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyReplacementValue(String scc) {
		String shipmentDes = getAttributeWebElement(sheetName, "inbx_shipmentDes;xpath", "Shipment Description",
				"value", screenName);
		String str1 = shipmentDes;

		int counter = str1.length();

		System.out.println("Char count is = " + counter);
		if (counter == 250) {
			writeExtent("Pass", "wordCount matches");
		} else {
			writeExtent("Fail", "wordCount not matches");
			Assert.assertFalse(true, "wordCount not matches");
		}
		String shipmentScc = getAttributeWebElement(sheetName, "inbx_shipmentSCC;xpath", "Shipment SCC", "value",
				screenName);

		if (shipmentScc.equals(data(scc))) {
			writeExtent("Pass", "Shipment scc matches");
		} else {
			writeExtent("Fail", "Shipment scc not matches ; Expected : " + data(scc) + " Actual : " + shipmentScc);
			Assert.assertFalse(true, "Shipment scc not matches ; Expected : " + data(scc) + " Actual : " + shipmentScc);
		}
	}

	/**
	 * 
	 * Description... Enter Shipment details by passing it in an array in CAP018
	 * screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void enterShipmentDetails() throws InterruptedException {

		String xpath[] = { "inbx_origin;xpath", "inbx_destination;xpath", "inbx_agentCode;xpath",
				"inbx_shipmentDate;xpath", "inbx_sccCode;xpath", "inbx_commodityCode;xpath", "inbx_pieces;xpath",
				"inbx_weight;xpath","inbx_volume;xpath" };

		String eleName[] = { "Origin", "Destination", "AgentCode", "ShipmentDate", "SCC", "CommodityCode", "Pieces",
				"Weight", "Volume"};
		for (int i = 0; i < xpath.length; i++)
			enterValueInTextbox(sheetName, xpath[i], data(eleName[i]), eleName[i], screenName);
	}

	/**
	 * Description... Enter the Shipment details origin, destination, SCC,
	 * Agentcode and shipping date by passing the values from datasheet or
	 * property file in CAP018 screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void enterShipmentDetails(String origin, String destination, String SCC, String agentCode,
			String shippingDate) throws InterruptedException, AWTException {
		//switchToFrame("contentFrame", "CAP018");
		enterValueInTextbox(sheetName, "inbx_origin;xpath", data(origin), "Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_destination;xpath", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_agentCode;xpath", data(agentCode), "Agent Code", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_sccCode;xpath", data(SCC), "SCC Code", screenName);
		enterValueInTextbox(sheetName, "inbx_shipmentDate;xpath", data(shippingDate), "Shipping Date", screenName);
		waitForSync(2);
	}

	/**
	 * Description... Enter origin, destination, flight no and flight date in
	 * flight details section of CAP018 screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void enterFlightDetails(String origin, String destination, String flightNo, String flightDate)
			throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_fltOrigin;xpath", origin, "Flight Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_fltDestination;xpath", destination, "Flight Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_fltDate;xpath", flightDate, "Flight Date", screenName);
		enterValueInTextbox(sheetName, "inbx_fltNumber;xpath", flightNo, "Flight Number", screenName);

	}
	/**
     * @author A-9175
     * @Description : select Flight row
     * @param row
     */
	public void selectFlightRow(String row) {
		try {
			String flightloc=xls_Read.getCellValue(sheetName, "chk_flightRow;id");
			flightloc=flightloc.replace("Loc", row);
			driver.findElement(By.id(flightloc)).click();
			writeExtent("Pass", "Flight details Selected");
		} catch (Exception e) {
			writeExtent("Fail", "Flight details could'nt be selected");
		}
	}

	/**
	 * @author A-9175
	 * @Description : Select station allotment id
	 * @param allotmentId
	 * @throws Exception
	 */
	public void selectStationAllotment(String allotmentId) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_chooseallotment;id", "Choose Allotment", screenName);
		waitForSync(5);
		try{
			switchToWindow("child");
			String allotmentLoc=xls_Read.getCellValue(sheetName,"chk_flightAllotment;xpath");
			allotmentLoc=allotmentLoc.replace("AllotmentId", allotmentId);
			driver.findElement(By.xpath(allotmentLoc)).click();
			writeExtent("Pass", "Selected Allotment ID : "+allotmentId);
		}
		catch (Exception e){
			switchToWindow("storeParent");
		}
		clickWebElement(sheetName, "btn_ok;xpath", "OK", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","CAP018");
	}

	/**
	 * @author A-9847
	 * @desc To verify flight level details Without Volume on CAP018 screen 
	 * @param rows
	 * @param origin
	 * @param destination
	 * @param flightNo
	 * @param flightDate
	 * @param pcs
	 * @param wt
	 * @param vol
	 * @throws InterruptedException
	 */
	
	public void verifyFlightLevelDetails(int rows, String origin[], String destination[], String flightNo[],
			String flightDate[], String pcs[], String wt[]) throws InterruptedException {

		for (int i = 1; i <= rows; i++) {

			String org = getAttributeWebElement(sheetName, "inbx_fltOrigin" + i + ";xpath", "Origin", "value", screenName);
			String dest= getAttributeWebElement(sheetName, "inbx_fltDestination" + i + ";xpath", "Destination", "value", screenName);
			String fltNum = getAttributeWebElement(sheetName, "inbx_fltNumber" + i + ";xpath", "Flight Number", "value", screenName);
			String fltDate = getAttributeWebElement(sheetName, "inbx_fltDate" + i + ";xpath", "Flight Date", "value", screenName);
			String fltPcs = getAttributeWebElement(sheetName, "inbx_fltPcs" + i + ";xpath", "Total Pieces", "value", screenName);
			String fltWt = getAttributeWebElement(sheetName, "inbx_fltWt" + i + ";xpath", "Total Weight", "value", screenName);
			

			verifyScreenText(screenName ,data(origin[i-1]), org, "Flight Origin","Flight Origin");
			verifyScreenText(screenName ,data(destination[i-1]), dest, "Flight Destination","Flight Destination");
			verifyScreenText(screenName ,data(flightNo[i-1]), fltNum, "Flight Number","Flight Number");
			verifyScreenText(screenName ,data(flightDate[i-1]), fltDate, "Flight Date","Flight Date");
			verifyScreenText(screenName ,data(pcs[i-1]), fltPcs, "Total Pieces","Total Pieces");
			verifyScreenText(screenName ,data(wt[i-1]), fltWt, "Total Weight","Total Weight");
			

		}
	}

/**
	 * @author A-9175
	 * @Description : Select customer allotment id
	 * @param allotmentId
	 * @throws Exception
	 */
	public void selectGlobalAllotment(String allotmentId) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_chooseallotment;id", "Choose Allotment", screenName);
		waitForSync(5);
		try{
			switchToWindow("child");
			String allotmentLoc=xls_Read.getCellValue(sheetName,"chk_flightAllotmentGlobal;xpath");
			allotmentLoc=allotmentLoc.replace("AllotmentId", allotmentId);
			driver.findElement(By.xpath(allotmentLoc)).click();
			writeExtent("Pass", "Selected Allotment ID : "+allotmentId);
		}
		catch (Exception e){
			switchToWindow("storeParent");
		}
		clickWebElement(sheetName, "btn_ok;xpath", "OK", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","CAP018");
	}



	/**
	 * Description... Enter flight details by passing it in an array in flight
	 * details section of CAP018 screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void enterFlightLevelDetails(int rows, String origin[], String destination[], String flightNo[],
			String flightDate[], String pcs[], String wt[], String vol[],boolean force,String forceOption) throws InterruptedException {

		for (int i = 1; i <= rows; i++) {

			enterValueInTextbox(sheetName, "inbx_fltOrigin" + i + ";xpath", data(origin[i - 1]), "Origin", screenName);
			enterValueInTextbox(sheetName, "inbx_fltDestination" + i + ";xpath", data(destination[i - 1]),
					"Destination", screenName);
			enterValueInTextbox(sheetName, "inbx_fltNumber" + i + ";xpath", data(flightNo[i - 1]), "FlightNo",
					screenName);
			enterValueInTextbox(sheetName, "inbx_fltDate" + i + ";xpath", data(flightDate[i - 1]), "Flight Date",
					screenName);

			enterValueInTextbox(sheetName, "inbx_fltPcs" + i + ";xpath", data(pcs[i - 1]), "Pieces", screenName);
			enterValueInTextbox(sheetName, "inbx_fltWt" + i + ";xpath", data(wt[i - 1]), "Weight", screenName);
			enterValueInTextbox(sheetName, "inbx_fltVolume" + i + ";xpath", data(vol[i - 1]), "Volume", screenName);
			if(force)
			{
				waitForSync(3);
				selectValueInDropdown(sheetName, "lst_bkgStatus" + i + ";xpath", data(forceOption), "Force Option", "VisibleText");
				waitForSync(3);
			}

		}
	}

	/**
	 * Description... Enter pieces, Weight, Volume and commodity details in
	 * shipment details section of CAP018 screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void enterShipmentLevelDetailsWithSCC(String commodity, String pcs, String wt, String vol,String scc)
			throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_commodityCode;xpath", data(commodity), "Commodity Code", screenName);
		enterValueInTextbox(sheetName, "inbx_pieces;xpath", data(pcs), "Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_weight;xpath", data(wt), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_volume;xpath", data(vol), "Volume", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_scc;name", data(scc), "scc", screenName);
	}

	/**
	 * Description... Click on Save booking details button in CAP018 screen to
	 * save the details,throws exception otherwise
	 * 
	 * @return
	 * @throws Exception
	 */

	public void saveBookingDetails(String bookingStatus) throws Exception {
		
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_saveDetails;xpath", "Save", screenName);
		waitForSync(5);
		try{
			switchToFrame("default");
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
			}
		}
		catch (Exception e){
			switchToWindow("storeParent");
		}
		
		
		switchToWindow("child");
		verifyBookingStatusAndUBR(bookingStatus);
		clickWebElement(sheetName, "btn_ok;xpath", "OK", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","CAP018");
	}
	/**
	 * Description... Click on Save booking details button in CAP018 screen to
	 * save the details,throws exception otherwise
	 * 
	 * @return
	 * @throws Exception
	 */

	public void saveBookingDetails(String bookingStatus,String flightNo,String allotmentId) throws Exception {
		
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_saveDetails;xpath", "Save", screenName);
		waitForSync(5);
		try{
			switchToFrame("default");
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
			}
		}
		catch (Exception e){
			switchToWindow("storeParent");
		}
		
		
		switchToWindow("child");
		verifyBookingStatusAndUBR(bookingStatus);
		
		/***** Verify the allotment details****/
		 String primaryKey=data(flightNo);
         int verfCols[]={7};
         String[] actVerfValues={data(allotmentId)};
         verifyBookingSummary(verfCols,actVerfValues,primaryKey,true);
         
         /****************************************/
		clickWebElement(sheetName, "btn_ok;xpath", "OK", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","CAP018");
	}
	/**
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @param isAssertreq
	 * @throws InterruptedException
	 * @throws IOException
	 */
	  public void verifyBookingSummary(int verfCols[], String actVerfValues[],
              String pmKey,boolean isAssertreq) throws InterruptedException, IOException {
        verify_tbl_records_multiple_cols(sheetName, "table_viewSummary;xpath",
                    "//td", verfCols, pmKey, actVerfValues,isAssertreq);
  }

	/**
	 * Description... Check if AWB already exists in CAP018 screen,throws
	 * exception otherwise
	 * 
	 * @param screenName
	 *            Maintain Booking
	 * @param screenId
	 *            CAP018
	 */

	public void checkAWBExists(String screenId, String screenName) throws InterruptedException {

		int count = 0;
		boolean bkgStatus = false;
		boolean executedAWB = false;
		String frameName = "iCargoContentFrame" + screenId;
		try {
			do {
				bkgStatus = false;
				executedAWB = false;
				customFunction.createAWB("AWBNo");

				if (!(count == 0)) {
					try {

						String alertText = driver
								.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).getText();
						if (alertText != "")
							clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);

					} catch (Exception e) {

					}
					driver.switchTo().frame(frameName);
				}

				clickWebElement("Generic_Elements", "btn_clear;xpath", "Clear Button", screenName);
				customFunction.listAWB("AWBNo", "CarrierNumericCode", screenName);

				count++;

				try {
					String bookingStatusString = driver.findElement(By.xpath("//input[@name='bookingStatus']"))
							.getAttribute("value");
					if (bookingStatusString.equals("Confirmed"))

						bkgStatus = true;
				} catch (Exception e) {

				}
				switchToFrame("default");

				try {
					driver.findElement(By
							.xpath("//p[contains(text(),'The specified AWB  number is already executed. Do you want to continue?.')]"))
							.isDisplayed();
					executedAWB = true;
				} catch (Exception e) {

				}

			} while (bkgStatus & count < 10 | executedAWB);

		} catch (Exception e) {
			System.out.println("In catch block of checkAWBExists methOd");
		}

		try {
			driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).click();
			switchToFrame("contentFrame", screenId);
		} catch (Exception e) {
			System.out.println("Could not find fresh AWB No on Maintain Booking Screen");
			writeExtent("Fail", "Could not find fresh AWB No on Maintain Booking Screen");
			Assert.assertFalse(true, "Could not find fresh AWB No on Maintain Booking Screen");
		}
	}

	/**
	 * Description... Verify the dimension details of the shipment in CAP018
	 * screen,throws exception otherwise
	 * 
	 * @return
	 * @throws Exception
	 */

	public void verifyDimension() throws Exception {
		waitForSync(2);
		String status = "YES";
		String dime = getAttributeWebElement(sheetName, "inbx_dimensionStatus;xpath", "Dimension", "value", screenName);

		if (dime.equals(status)) {
			onPassUpdate(screenName, "Status is same", "", "Dimension Status", "");
			waitForSync(2);
			try {
				switchToWindow("storeParent");
				clickWebElement(sheetName, "clk_DimensionChkBox;xpath", "Dimension", screenName);
				waitForSync(2);
				waitForSync(2);
				switchToWindow("child");
				switchToFrame("default");
				// Clicking on the icon and moving to pop up part
				String pmKey = data("Pieces");
				String[] actVerfValues = { data("Length"), data("Width"), data("Height") };
				int[] verfCols = { 4, 5, 6 };
				customFunction.verify_tbl_records_multiple_cols(sheetName, "tble_DimensionDetails;xpath", "//input",
						verfCols, pmKey, actVerfValues);
				clickWebElement(sheetName, "button_closeDimPopUp;xpath", "Close Button", screenName);
				switchToWindow("getParent");

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			writeExtent("Fail", "Dimension status not matches ; Expected : " + status + " Actual : " + dime);
		}
	}

	/**
	 * Description... Verify ULD details in shipment level in CAP018
	 * screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyShipmentLevelULDDetails(String uldno, String uldID, String uldWT) throws InterruptedException {
		waitForSync(5);
		String uldNumber = getAttributeWebElement(sheetName, "inbx_uldNo;xpath", "uldNumber", "value", screenName);

		if (uldNumber.equals(data(uldno))) {
			writeExtent("Pass", "uld Number matches");
		} else {
			writeExtent("Fail", "uld Number not matches ; Expected : " + data(uldno) + " Actual : " + uldNumber);
			Assert.assertFalse(true, "uld Number not matches ; Expected : " + data(uldno) + " Actual : " + uldNumber);
		}
		String ULDid = getAttributeWebElement(sheetName, "inbx_ULDID;xpath", "ID", "value", screenName);

		if (ULDid.equals(data(uldID))) {
			writeExtent("Pass", "ULD id matches");
		} else {
			writeExtent("Fail", " ULD id not matches ; Expected : " + data(uldID) + " Actual : " + ULDid);
			Assert.assertFalse(true, " ULD id not matches ; Expected : " + data(uldID) + " Actual : " + ULDid);
		}
		String ULDWeight = getAttributeWebElement(sheetName, "inbx_uldWT;xpath", "ULDWeight", "value", screenName);

		if (ULDWeight.equals(data(uldWT))) {
			writeExtent("Pass", "ULD Weight matches");
		} else {
			writeExtent("Fail", "ULD Weight not matches ; Expected : " + data(uldWT) + " Actual : " + ULDWeight);
			Assert.assertFalse(true, "ULD Weight not matches ; Expected : " + data(uldWT) + " Actual : " + ULDWeight);
		}
	}

	/**
	 * Description... Verify Agent name in CAP018 screen,throws exception
	 * otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyAgentName(String customerName) {
		String agentName = getAttributeWebElement(sheetName, "txt_AgentName;xpath", "Agent Name", "value", screenName);

		if (agentName.equals(data(customerName))) {
			onPassUpdate(screenName, "Agent Name is " + agentName, "Agent Name is " + agentName, "Agent name",
					"1.Login to iCapsit \n ,2.Invoke CAP018 screen \n ,3 Check for the agent name \n");
		} else {
			onFailUpdate(screenName, "Agent Name is " + agentName, "Agent Name is notcorrect ", "Agent name",
					"1.Login to iCapsit \n ,2.Invoke CAP018 screen \n ,3 Check for the agent name \n");
		}
	}

	/**
	 * Description... To fetch the UBR number displayed in CAP018 screen,throws
	 * exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void getUbrNumber() {

		try {
			waitForSync(3);
			WebElement webEle = findDynamicXpathElement("txt_UBRNumber;xpath", sheetName, "UBR Number", screenName);
			String UbrNumber = webEle.getAttribute("Value");
			map.put("UBRNo", UbrNumber);
		} catch (Exception e) {

		}
	}

	/**
	 * Description... Verify length of characters displayed in nature of goods
	 * field in CAP018 screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyNatureOfGoodsCharLength() {

		try {
			waitForSync(8);
			driver.switchTo().frame("main");
			driver.switchTo().frame("shipmentmain");
			WebElement webEle = findDynamicXpathElement("txt_natureOfGoods;Xpath", sheetName, "Nature Of Goods",
					screenName);
			String natureOfGoodsValue = webEle.getText();
			int counter = natureOfGoodsValue.length();
			System.out.println("Char count is = " + counter);
			if (counter == 50) {
				onPassUpdate(screenName, "Nature of Goods length is " + counter, "Nature of Goods length is " + counter,
						"Nature of Goods",
						"//1. Login to tdServices \n , 2.Click on orderSteering \n , 3.Enter Full AWB num \n , 5.Click on Request \n");
			} else {
				onFailUpdate(screenName, "Nature of Goods length is " + counter,
						"Nature of Goods length not equal to " + counter, "Nature of Goods",
						"//1. Login to tdServices \n , 2.Click on orderSteering \n , 3.Enter Full AWB num \n , 5.Click on Request \n");
			}

		} catch (Exception e) {

		}
		driver.switchTo().defaultContent();
	}

	/**
	 * Description... Verify the SCC values of a shipment in CAP018
	 * screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void VerifyUpdatedSCCValues(String sccValues) {
		String shipmentScc = getAttributeWebElement(sheetName, "inbx_shipmentSCC;xpath", "Shipment SCC", "value",
				screenName);

		if (shipmentScc.equals(data(sccValues))) {
			onPassUpdate(screenName, "SCC values are matching " + shipmentScc, "SCC vales are matching " + shipmentScc,
					"SCC values", "1.Login to iCapsit \n ,2.Invoke CAP018 screen \n ,3 Check for the SCC values \n");
		} else {
			onFailUpdate(screenName, "SCC values are matching " + shipmentScc, "SCC vales are not matching ",
					"SCC values", "1.Login to iCapsit \n ,2.Invoke CAP018 screen \n ,3 Check for the SCC values \n");
		}
	}

	/**
	 * Description... Enter or Update values for LAT date and time in CAP018
	 * screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void changeLATdate(String LATdate, String LATtime) throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_latDate;xpath", LATdate, "Flight LAT date", screenName);
		enterValueInTextbox(sheetName, "inbx_latTime;xpath", LATtime, "Flight LAT time", screenName);

	}

	/**
	 * Description... To clear text from the product code field in CAP018
	 * screen,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void clearProductCode() throws InterruptedException {
		clearText(sheetName, "inbx_Product;id", "Product", screenName);
	}

	/**
	 * Description... Verify values in Origin and Destination field by passing
	 * the data,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyOriginDest(String Origin, String Destination) {
		String origin = getAttributeWebElement(sheetName, "inbx_origin;xpath", "Origin", "value", screenName);

		if (origin.equals(data(Origin))) {
			writeExtent("Pass", "Origin matches");
		} else {
			writeExtent("Fail", "Origin does not matches ; Expected : " + data(Origin) + " Actual : " + origin);
		}
		String destination = getAttributeWebElement(sheetName, "inbx_destination;xpath", "Destination", "value",
				screenName);
		if (destination.equals(data(Destination))) {
			writeExtent("Pass", "Destination matches");
		} else {
			writeExtent("Fail",
					"Destination does not matches ; Expected : " + data(Destination) + " Actual : " + destination);
		}
	}

	/**
	 * Description... Verify values in BK Lines field of a shipment by passing
	 * the data,throws exception otherwise
	 * 
	 * @return
	 * @throws InterruptedException
	 */

	public void verifyBKLines(String bkLines) throws InterruptedException {

		waitForSync(2);
		String verifybkLines = getAttributeWebElement(sheetName, "inbx_BKLines;name", "BK Lines", "value", screenName);

		if (verifybkLines.contains(data(bkLines))) {
			writeExtent("Pass", "BK Lines matches");
		}

		else {
			writeExtent("Fail", "BK Lines not matches ; Expected : " + data(bkLines) + " Actual : " + verifybkLines);
		}
	}
/**
 * Description...  Verify Segment Level Details
 * @param origin
 * @param destination
 * @param FlightNum
 * @throws InterruptedException
 */
	public void verifySegmentLevelDetails1(String origin, String destination, String FlightNum)
			throws InterruptedException {
		waitForSync(5);
		String Origin = getAttributeWebElement(sheetName, "inbx_fltOrigin1;xpath", "Origin", "value", screenName);
		if (Origin.contains(data(origin))) {
			writeExtent("Pass", "origin  matches");
		} else {
			writeExtent("Fail", "origin not matches ; Expected : " + data(origin) + " Actual : " + origin);
			Assert.assertFalse(true, "origin not matches ; Expected : " + data(origin) + " Actual : " + origin);
		}

		String Destination = getAttributeWebElement(sheetName, "inbx_fltDestination1;xpath", "destination", "value",
				screenName);
		if (Destination.contains(data(destination))) {
			writeExtent("Pass", "destination  matches");
		} else {
			writeExtent("Fail",
					"destination not matches ; Expected : " + data(destination) + " Actual : " + destination);
			Assert.assertFalse(true,
					"destination not matches ; Expected : " + data(destination) + " Actual : " + destination);
		}

		String flightNo = getAttributeWebElement(sheetName, "inbx_fltNumber1;xpath", "flightNo", "value", screenName);
		if (flightNo.contains(data(FlightNum))) {
			writeExtent("Pass", "flightNo  matches");
		} else {
			writeExtent("Fail", "flightNo not matches ; Expected : " + data(FlightNum) + " Actual : " + flightNo);
			Assert.assertFalse(true, "flightNo not matches ; Expected : " + data(FlightNum) + " Actual : " + flightNo);
		}
	}
	
	
	/**
	 * Description... Click on Save booking details with LAT button in CAP018 screen to
	 * save the details,throws exception otherwise
	 * 
	 * @return
	 * @throws Exception
	 */

	public void saveBookingDetailswithLAT() throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_saveDetails;xpath", "Save", screenName);
		waitForSync(10);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		switchToWindow("child");
		clickWebElement(sheetName, "btn_ok;xpath", "OK", screenName);
		switchToWindow("getParent");
	}

	public void saveBookingDetailsForDGR() throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_saveDetails;xpath", "Save", screenName);
		waitForSync(10);
		try {
			Thread.sleep(4000);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		switchToWindow("child");
		clickWebElement(sheetName, "btn_ok;xpath", "OK", screenName);
		switchToWindow("getParent");
		
	}

	public void enterProductCode(String productCode) throws InterruptedException {
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_productCode;name", data(productCode), "Awb Number", screenName);
		
	}
	
	/*Enters shipment level with product code
	 * A-8705
	 */
	
	public void enterShipmentDetailsWithProductCode(String origin, String destination, String SCC, String agentCode,
			String shippingDate,String Product ) throws InterruptedException, AWTException {
		switchToFrame("contentFrame", "CAP018");
		enterValueInTextbox(sheetName, "inbx_origin;xpath", data(origin), "Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_destination;xpath", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_agentCode;xpath", data(agentCode), "Agent Code", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_productCode;name", data(Product), "Product Code", screenName);
		enterValueInTextbox(sheetName, "inbx_sccCode;xpath", data(SCC), "SCC Code", screenName);
		enterValueInTextbox(sheetName, "inbx_shipmentDate;xpath", data(shippingDate), "Shipping Date", screenName);
		waitForSync(2);
		
	}
	
	/**
	 * @author A-9175
	 * Desc : select Flight
	 * @param flightNo
	 * @throws Exception
	 */
	public void selectFlight(String flightNo) throws Exception {
		
		clickWebElement(sheetName, "btn_selectFlight;xpath", "Select Flight", screenName);
		waitForSync(4);
		switchToFrame("frameName","popupContainerFrame");
		String locator=xls_Read.getCellValue(sheetName, "btn_selectFlightpopup;xpath");
		locator=locator.replace("fltNo", flightNo);
		driver.findElement(By.xpath(locator)).click();
		clickWebElement(sheetName, "btn_selectFlightpopup;xpath", "Select", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_selectFlightpopupOK;xpath", "OK", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP018");
		
	}
	
	/**
	 * @author A-9175
	 * @Desc verify Booking Status And UBR
	 * @throws InterruptedException
	 */
	public void verifyBookingStatusAndUBR(String bookingStatus) throws InterruptedException {
		try{
			String BKGStatus=xls_Read.getCellValue(sheetName, "label_bookingStatusStatus;xpath");
			verifyScreenTextWithExactMatch(screenName, bookingStatus, driver.findElement(By.xpath(BKGStatus)).getText(), "Verification of booking status","Verification of booking status");
			String UBR=xls_Read.getCellValue(sheetName, "label_UBRnumber;xpath");
			writeExtent("Pass", "UBR Number Found As: "+driver.findElement(By.xpath(UBR)).getText());
		}catch (Exception e) {
			writeExtent("Fail","Booking status or UBR Number not Found");
		}
	}
	/**
	 * @author A-9175
	 * @Desc : enter shipment level details
	 * @param commodity
	 * @param pcs
	 * @param wt
	 * @param vol
	 * @throws InterruptedException
	 */
	public void enterShipmentLevelDetails(String commodity, String pcs, String wt, String vol)
			throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_commodityCode;xpath", data(commodity), "Commodity Code", screenName);
		enterValueInTextbox(sheetName, "inbx_pieces;xpath", data(pcs), "Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_weight;xpath", data(wt), "Weight", screenName);
		clearText(sheetName, "inbx_volume;xpath", "Volume", screenName);
		enterValueInTextbox(sheetName, "inbx_volume;xpath", data(vol), "Volume", screenName);

	}
	
	
/**
 * @author A-9175
 * @Desc ULD Details
 * @param uldgroup
 * @param contour
 * @param contourVal
 * @param numberofuld
 * @param uldwt
 * @param commodityCode
 * @throws InterruptedException
 */
	public void enterShipmentLevelULDDetails(String uldgroup,boolean contour,String contourVal,String numberofuld,String uldwt,String commodityCode) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_ULDID;xpath", data(uldgroup), "ULD Group", screenName);
		if(contour)
		{
			waitForSync(3);
			selectValueInDropdown(sheetName, "lst_SCI;xpath", data(contourVal), "Contour Value", "VisibleText");
			waitForSync(3);
		}
		enterValueInTextbox(sheetName, "inbox_noOfUlds;name", data(numberofuld), "Pieces", screenName);
		enterValueInTextbox(sheetName, "inbox_uldWeight;name", data(uldwt), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbox_uldCommodityCode;name", data(commodityCode), "Volume", screenName);
	}

	
	
	
}
