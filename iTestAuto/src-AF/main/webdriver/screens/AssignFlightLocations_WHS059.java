package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AssignFlightLocations_WHS059 extends CustomFunctions
{

	public AssignFlightLocations_WHS059(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	

	public String sheetName="AssignFlightLocations_WHS059";
	public String screenName="AssignFlightLocations_WHS059";
	public String toVariableProppath = "\\src\\resources\\TO.properties";

	/**
	 * @author A-9847
	 * @desc To Click on List Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickList() throws InterruptedException, IOException{

		clickWebElementByWebDriver(sheetName, "btn_list;id", "List Button", screenName);
		waitForSync(5);
	}
	/**
	 * @author A-10690
	 * @Desc To get the buffer location from the screen
	 * @param Assigned location key
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void getAssignedLocation(String AssignedLocation) throws InterruptedException, IOException{

		String locator=xls_Read.getCellValue(sheetName, "txt_assignedLocation;xpath");
		
		String assignedLocation=driver.findElement(By.xpath(locator)).getText(); 
		map.put("AssignedLocation", assignedLocation);
		waitForSync(2);   

	}
	/**
	 * @author A-9847
	 * @Des To click on More Optin(three dots) giving the segment as primary key and getting back index position
	 * @param segment
	 * @param index
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickMoreOption(String segment,String index) throws InterruptedException, IOException{

		try{
		String locator=xls_Read.getCellValue(sheetName, "btn_moreOptions;xpath");
		locator=locator.replace("*", segment);
		driver.findElement(By.xpath(locator)).click(); 
		String locator1=xls_Read.getCellValue(sheetName, "div_rowIndex;xpath");
		locator1=locator1.replace("*", segment);
	    String Index= driver.findElement(By.xpath(locator1)).getAttribute("rowindex");
	    map.put(index, Index);	
		waitForSync(2);   
		}
		catch(Exception e){
			
			writeExtent("Fail", "Not able to click and retrieve index from " + screenName);	
		}

	}



/**
	 * @author A-9847
	 * @Desc To enter Open time for a specific segment of the flight
	 * @param openDate
	 * @param openTime
	 * @param segment
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterOpenTimeForSegment(String openDate,String openTime,String segment) throws InterruptedException, IOException{
        waitForSync(3);
	try{
        String locator1=xls_Read.getCellValue(sheetName, "div_rowIndex;xpath").replace("*", segment);
	    String Index= driver.findElement(By.xpath(locator1)).getAttribute("rowindex");
        
	    String locator=xls_Read.getCellValue(sheetName, "btn_opentimeIcon;id").replace("*", Index);
	    driver.findElement(By.id(locator)).click();
	    waitForSync(3);     
	    
	   String locator2=xls_Read.getCellValue(sheetName, "inbx_opendate;xpath").replace("*", Integer.toString(Integer.parseInt(Index)+1));
	   driver.findElement(By.xpath(locator2)).sendKeys(data(openDate));
	    
	   String locator3=xls_Read.getCellValue(sheetName, "inbx_opentime;xpath").replace("*", Integer.toString(Integer.parseInt(Index)+1));
	   driver.findElement(By.xpath(locator3)).sendKeys(data(openTime));
	   
	   String locator4=xls_Read.getCellValue(sheetName, "btn_OK;xpath").replace("*", Integer.toString(Integer.parseInt(Index)+1));
	   driver.findElement(By.xpath(locator4)).click();
	}
	catch(Exception e){
		writeExtent("Fail", "Not able to enter opendate/opentime on " + screenName); 
	}
	}
	/**
	 * @des : verify Flight In Assign Location
	 * @author A-9175
	 */
	public void verifyFlightInAssignLocation(String flightNo) {
		try {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightDetails;xpath");
			locator = locator.replace("*", data(flightNo));
			writeExtent("Pass", data(flightNo) + " Displayed in Assign Location Tab" + screenName);
			waitForSync(3);
		} catch (Exception e) {
			writeExtent("Fail", data(flightNo) + " Not Displayed in Assign Location Tab" + screenName);
		}

	}
	/**
	 * @desc : click Flight In Assign Location
	 * @author A-9175
	 * @param flightNo
	 */
	public void clickFlightInAssignLocation(String flightNo) {
		try {
			String locator = xls_Read.getCellValue(sheetName, "chk_flightDetails;xpath");
			locator = locator.replace("*", data(flightNo));
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", "Clicked on checkbox" + data(flightNo) + " in Assign Location Tab" + screenName);
			waitForSync(3);
		} catch (Exception e) {
			writeExtent("Pass",
					"Could not Click on checkbox" + data(flightNo) + " in Assign Location Tab" + screenName);
		}
	}

	/**
	 * @desc : add Count Of Assigned Locations
	 * @author A-9175
	 * @param count
	 */
	public void addCountOfAssignedLocations(String count) {
		try {
			String locator = xls_Read.getCellValue(sheetName, "btn_addOn;xpath");
			locator = locator.replace("*", count);
			int loopcount = Integer.parseInt(count);
			for (int i = 1; i < loopcount; i++) {
				driver.findElement(By.xpath(locator)).click();
			}
			writeExtent("Pass", "Clicked on Plus Button in Assign Location Window" + screenName);
			waitForSync(3);
		} catch (Exception e) {
			writeExtent("Fail", "Could not click on Plus Button in Assign Location Window" + screenName);
		}
	}
/**
 * @Desc :capture Assign Location details
 * @author A-9175
 * @param Count
 * @param zone
 * @param assignLoc
 * @param opendate
 * @param openTime
 * @param closedate
 * @param Closetime
 * @throws InterruptedException
 * @throws IOException
 * @throws AWTException 
 */
	public void captureAssignLocationdetails(String Count, String zone[], String assignLoc[], String opendate,
			String openTime, String closedate,String Closetime) throws InterruptedException, IOException, AWTException {

		int loopcount = Integer.parseInt(Count);
		int k = 0;
		for (int i = 0; i < loopcount; i++) {
			
try {
				
				// Capture Location

				String locatorLocation = xls_Read.getCellValue(sheetName, "txt_assignedLocations;name");
				String changed=Integer.toString(k);
				locatorLocation = locatorLocation.replace("*",changed );
				driver.findElement(By.name(locatorLocation)).sendKeys(assignLoc[k]);
				waitForSync(4);
				writeExtent("Pass", "Sucessfully Entered the Assigned Location "+assignLoc[k]+" on " + screenName);

			} catch (Exception e) {
				writeExtent("Fail", "Failed to Select the Assigned Location "+assignLoc[k]+" on " + screenName);
			}

			try {
				// Capture Open Date and Time
				String locatorOpenDate = xls_Read.getCellValue(sheetName, "txt_openDate;name");
				locatorOpenDate = locatorOpenDate.replace("*", Integer.toString(k));
				driver.findElement(By.name(locatorOpenDate)).sendKeys(data(opendate));
				waitForSync(4);
				String locatorOpenTime = xls_Read.getCellValue(sheetName, "txt_openTime;name");
				locatorOpenTime = locatorOpenTime.replace("*", Integer.toString(k));
				driver.findElement(By.name(locatorOpenTime)).click();
				System.out.println(data(opendate));
				driver.findElement(By.name(locatorOpenTime)).sendKeys(data(openTime));
				waitForSync(4);
				writeExtent("Pass", "Sucessfully Entered  Open Date as "+data(opendate)+" and Open Time as " +data(openTime)+"  on " + screenName);

			} catch (Exception e) {
				writeExtent("Fail", "Failed to Enter Open Date or Open Time on " + screenName);
			}

			try {
				// Capture Close Date and Time
				String locatorCloseDate = xls_Read.getCellValue(sheetName, "txt_closeDate;name");
				locatorCloseDate = locatorCloseDate.replace("*", Integer.toString(k));
				driver.findElement(By.name(locatorCloseDate)).sendKeys(data(closedate));
				waitForSync(4);
				String locatorCloseTime = xls_Read.getCellValue(sheetName, "txt_closeTime;name");
				locatorCloseTime = locatorCloseTime.replace("*", Integer.toString(k));
				driver.findElement(By.name(locatorCloseTime)).click();
				driver.findElement(By.name(locatorCloseTime)).sendKeys(data(Closetime));
				waitForSync(4);
		writeExtent("Pass", "Sucessfully Entered  Close Date as "+data(closedate)+" and  Close Time as "+data(Closetime)+" on " + screenName);

				
			} catch (Exception e) {
				writeExtent("Fail", "Failed to Enter Close Date or Close Time on " + screenName);
			}
			
			try {
				// Capture Open Date and Time
				String locatorOpenDate = xls_Read.getCellValue(sheetName, "txt_openDate;name");
				locatorOpenDate = locatorOpenDate.replace("*", Integer.toString(k));
				driver.findElement(By.name(locatorOpenDate)).clear();
				driver.findElement(By.name(locatorOpenDate)).sendKeys(data(opendate));
				waitForSync(4);
				//keyPress("TAB");
				writeExtent("Pass", "Sucessfully Entered  Open Date "+data(opendate)+" On " + screenName);

			} catch (Exception e) {
				writeExtent("Fail", "Failed to Enter Open Date on " + screenName);
			}
			
			k++;

		}

		clickWebElement(sheetName, "txt_assignLocOk;id", "Ok Button", screenName);
		waitForSync(8);
		keyPress("ENTER");
		waitTillScreenloadWithOutAssertion(sheetName, "btn_assignedLoc;xpath", "Assigend TAB",screenName, 20);

	}
	
/**
 * @Desc : verify Zones
 * @author A-9175
 * @param FlightNo
 * @param Zones
 */
	public void verifyZones(String FlightNo, String[] Zones) {

		try {
			ArrayList<String> expZonesList = new ArrayList<String>();
			List<WebElement> Zonesval = driver.findElements(By.xpath(
					xls_Read.getCellValue(sheetName, "txt_AssignZones;xpath").replace("FlightNo", data(FlightNo))));
			ArrayList<String> actualZonesList = new ArrayList<String>();
			for (WebElement zone : Zonesval) {
				actualZonesList.add(zone.getText());
			}
			for (String zone : Zones) {
				expZonesList.add(zone);
			}
			Collections.sort(actualZonesList);
			Collections.sort(expZonesList);
			if (actualZonesList.equals(expZonesList)) {
				writeExtent("Pass", "Sucessfully Found Zones" + actualZonesList + " on " + screenName);
			} else {
				writeExtent("Fail", "Fail to verify Zones" + actualZonesList + " on " + screenName);
			}
		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify Zone Values on " + screenName);
		}
	}

	
	/**
	 * @author A-9847
	 * @Desc To verify the Open status of a particular segment of the flight
	 * @param expText
	 * @param segment
	 */
	public void verifyOpenStatusOfSegment(String expText,String segment){

		try{
		String actualText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_locStatus;xpath").replace("*", segment))).getText();
		verifyScreenTextWithExactMatch(sheetName,expText, actualText, "Open Status","Open Status");
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify OPEN status on " + screenName);
		}

	}

	/**
	 * @author A-9847
	 * @Desc To enter the Flights Details
	 * @param fltCode
	 * @param fltNum
	 * @param fltDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void enterFlightDetails(String fltCode,String fltNum, String fltDate) throws InterruptedException, AWTException, IOException{

		enterValueInTextbox(sheetName, "txt_fltCode;id",data(fltCode), "Flight Code", screenName);
		enterValueInTextbox(sheetName, "txt_fltNum;id", data(fltNum),"Flight Number", screenName);
		enterValueInTextbox(sheetName, "txt_fltDate;id", data(fltDate),"Flight Date", screenName);

		String actualZoneCategoryText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "text_zoneCategory;xpath"))).getText();
		System.out.println(actualZoneCategoryText); 

		if(actualZoneCategoryText!="Build-up")
			selectZoneTypeCategory();


	}
	/**
	 * @author A-9844
	 * @Desc To verify the status-OPEN
	 * @param openDate
	 * @param openTime
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void verifyOpenStatus(String expText) throws InterruptedException, IOException{

		//added to click the Assigned Location tab before doing the verification
				clickAssignedLocationTab();
				String actualText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_statusOpen;xpath"))).getText();
				System.out.println(actualText);  
				verifyScreenTextWithExactMatch(sheetName,expText, actualText, "Open Status","Open Status");


	}
	/**
	 * @author A-9844
	 * @Desc To select a Particular zone by passing the zone name on Dropdown
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectZoneTypeCategory() throws InterruptedException, IOException{


		String zoneType=getPropertyValue(toVariableProppath,"Zone");
		clickWebElementByWebDriver(sheetName, "drpdn_zoneCategory;xpath", "Zone Category Drop Down", screenName);
		waitForSync(2);  
		String locator=xls_Read.getCellValue(sheetName, "div_zone;xpath");
		locator=locator.replace("*", zoneType);     
		moveScrollBar(driver.findElement(By.xpath(locator)));
		waitForSync(2); 
		driver.findElement(By.xpath(locator)).click();   
		waitForSync(2); 
		writeExtent("Pass", "Selected the zone type category as "+zoneType+" on "+ screenName);

	}



	/**
	 * @author A-9847
	 * @Desc To select a Particular zone by passing its indexPosition on Dropdown
	 * @param zoneIndex
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectZone(String zoneIndex) throws InterruptedException, IOException{

		clickWebElementByWebDriver(sheetName, "drpdn_zoneCategory;xpath", "Zone Category Drop Down", screenName);
		waitForSync(2);  
		String locator=xls_Read.getCellValue(sheetName, "txt_zoneCategory;xpath");
		locator=locator.replace("*", zoneIndex);
		driver.findElement(By.xpath(locator)).click();   

	}


	/**
	 * @author A-9847
	 * @Desc To enter the Opentime
	 * @param openDate
	 * @param openTime
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterOpenTime(String openDate,String openTime) throws InterruptedException, IOException{
        waitForSync(3);
		clickWebElement(sheetName, "btn_opentime;id", "Open Time edit Icon", screenName);	
		enterValueInTextbox(sheetName, "txt_opendate;xpath",data(openDate), "Open date", screenName);
		enterValueInTextbox(sheetName, "txt_opentime;xpath", data(openTime),"Open time", screenName);
		clickWebElement(sheetName, "btn_openTimeOk;id", "Ok Button", screenName);	

	}

	/**
	 * @author A-9847
	 * @Desc To enter the Close time
	 * @param closeDate
	 * @param closeTime
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterCloseTime(String closeDate,String closeTime) throws InterruptedException, IOException{

		clickWebElement(sheetName, "btn_closetime;id", "Close Time edit Icon", screenName);	
		enterValueInTextbox(sheetName, "txt_closuredate;xpath",data(closeDate), "Close date", screenName);
		enterValueInTextbox(sheetName, "txt_closuretime;xpath", data(closeTime),"Close time", screenName);
		clickWebElement(sheetName, "btn_closureTimeOk;id", "Ok Button", screenName);	

	}
	/**
	 * @author A-9844
	 * @Desc To enter the Assigned Zone and location details 
	 * @param zoneIndex
	 * @param assignLoc
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterAssignZoneandLocationDetails(String zone,String assignLoc) throws InterruptedException, IOException{
		
        clickWebElementByWebDriver(sheetName, "drpdn_assignloc;xpath", "Assigned Zone", screenName);
        waitForSync(2); 
        String locator=xls_Read.getCellValue(sheetName, "div_zone;xpath");
        locator=locator.replace("*",data(zone));     
        moveScrollBar(driver.findElement(By.xpath(locator)));
        waitForSync(4); 
        driver.findElement(By.xpath(locator)).click();       
        enterValueInTextbox(sheetName, "txt_assignedLocation;name", data(assignLoc),"Assign Location", screenName);
        clickWebElement(sheetName, "txt_assignLocOk;id", "Ok Button", screenName);     
        waitForSync(3);  

	}
	/**
	 * @author A-9847
	 * @Desc To click the edit Icon
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickEditIcon() throws InterruptedException, IOException{

		clickWebElement(sheetName, "btn_editIcon;xpath", "Edit Icon", screenName);	
		waitForSync(2);   

	}

	/**
	 * @author A-9847
	 * @Desc To verify the opentime
	 * @param openDate
	 * @param openTime
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void verifyOpenTimeSaved(String openDate,String openTime) throws InterruptedException, IOException{

		clickAssignedLocationTab();
		String actualText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_savedOpenTime;xpath"))).getText();
		String expText= data(openDate)+" "+data(openTime);   
		verifyScreenTextWithExactMatch(sheetName,expText, actualText, "Open Time verification","Open Time verification");


	}

	/**
	 * @author A-9847
	 * @Desc To click the Assigned Location tab
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickAssignedLocationTab() throws InterruptedException, IOException{

		clickWebElement(sheetName, "btn_assignedLoc;xpath", "Assigned Location Tab", screenName);	
		waitForSync(4);   

	}

	/**
	 * @author A-9847
	 * @Desc To verify whether the closure time is empty or not
	 */
	public void verifyEmptyCloseTime(){

		try{

			String closetime=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_emptyCloseTime;xpath"))).getText(); 
			if(closetime.equals(""))
				writeExtent("Pass", "Close time field is empty on "+ screenName);
			else
				writeExtent("Fail", "Close time already exists on as "+closetime+" "+ screenName);
		}
		catch(Exception e){
			writeExtent("Fail", "Not able to retrieve close time on " + screenName);  
		}
	}

	/**
	 * @author A-9847
	 * @Desc To verify whether the open time is empty or not
	 */
	public void verifyEmptyOpenTime(){

		try{
			String opentime=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_emptyOpenTime;xpath"))).getText(); 
			if(opentime.equals(""))
				writeExtent("Pass", "Open time field is empty on "+ screenName);
			else
				writeExtent("Fail", "Open time already exists on as "+opentime+" "+ screenName);
		}
		catch(Exception e){
			writeExtent("Fail", "Not able to retrieve Open time on " + screenName);  

		}
	}

	/**
	 * @author A-9847
	 * @Desc To click on MoreOptions corresponding to a particular flight
	 * @param flightNum
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickMoreOptions(String flightNum) throws InterruptedException, IOException{

		String fltNum = data(flightNum).substring(0,2)+" "+data(flightNum).substring(2);
		String locator=xls_Read.getCellValue(sheetName, "btn_moreOptions;xpath");
		locator=locator.replace("*", fltNum);
		driver.findElement(By.xpath(locator)).click();  	
		waitForSync(2);   

	}

	/**
	 * @author A-9847
	 * @Desc To click the Assign Location button
	 * @param index
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickAssignLocation(String index) throws InterruptedException, IOException{

		String locator=xls_Read.getCellValue(sheetName, "btn_assignLocation;xpath");
		locator=locator.replace("*", index);
		driver.findElement(By.xpath(locator)).click();  	
		waitForSync(2);   

	}

	/**
	 * @author A-9847
	 * @Desc To enter the Assigned Zone and location details
	 * @param zoneIndex
	 * @param assignLoc
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterAssignZoneandLocation(String zone,String assignLoc) throws InterruptedException, IOException{

		String zoneAssigned=getPropertyValue(toVariableProppath,"PITLocationZone_CDG");
		String locationAssigned=getPropertyValue(toVariableProppath,"PITLocation_CDG");
		
        clickWebElementByWebDriver(sheetName, "drpdn_assignloc;xpath", "Assigned Zone", screenName);
        waitForSync(2); 
        String locator=xls_Read.getCellValue(sheetName, "div_zone;xpath");
        locator=locator.replace("*", zoneAssigned);     
        moveScrollBar(driver.findElement(By.xpath(locator)));
        waitForSync(4); 
        driver.findElement(By.xpath(locator)).click();       
        enterValueInTextbox(sheetName, "txt_assignedLocation;name", locationAssigned,"Assign Location", screenName);
        clickWebElement(sheetName, "txt_assignLocOk;id", "Ok Button", screenName);     
        waitForSync(3);  



	}



}
