package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class Handling_Area_Assignment_WHS049 extends CustomFunctions {

	String sheetName = "Handling_Area_Assignment_WHS049";
	String screenName = "Handling Area Assignment : WHS049";
	String screenId="WHS049";
	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	WebFunctions libr = new WebFunctions(driver, excelreadwrite, xls_Read);


	public Handling_Area_Assignment_WHS049(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
/**
 * Description... Click List
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickList() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_List;xpath", "List", screenName);
	}
/**
 * Description... Collect Shift Details
 */
	public void collectShiftDetais() {
		String userID = driver.findElement(By.xpath("(//td[@class='text-center'])[2]")).getText();
		String handlingArea = driver.findElement(By.xpath("(//td[@class='text-center'])[3]")).getText();
		map.put("User ID", userID);
		map.put("HandlingArea", handlingArea);
	}
	/**
	 * Description... Verify Applicable Route Details
	 * @author A-7271
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 */
	 public void verifyApplicableRouteDetails(int verfCols[],String actVerfValues[],String pmKey)
			 throws InterruptedException {
	
		
			waitForSync(1);
			verify_tbl_records_multiple_cols_contains(sheetName, "table_applRoute;xpath", "//td", verfCols, pmKey, actVerfValues);	
	}
	 /**
	  * Description... Verify Available Source HA
	  * @author A-7271
	  * @param count
	  * @param handlingArea
	  */
	 public void verifyAvailableSourceHA(int count,String handlingArea)
	 {
		 for(int i=3;i<(count+3);i++)
		 {
			 String actualResult=driver.findElement(By.xpath("(//td[contains(.,'"+handlingArea+"')]/ancestor::tr)["+i+"]")).getAttribute("style");
			
			 verifyScreenText(sheetName, "background-color: white",actualResult,  "verification of applicable route value for "+handlingArea, screenName); 
		 }
	 }
	 /**
	  * Description... Verify Non Application Routes
	  * @author A-7271
	  * @param originRoute
	  * @param count
	  * @param handlingArea
	  */
	 public void verifyNonApplicationRoutes(int originRoute,int count,String handlingArea)
	 {
		 for(int i=(originRoute+3);i<(count+originRoute+3);i++)
		 {
			 String actualResult=driver.findElement(By.xpath("(//td[contains(.,'"+handlingArea+"')]/ancestor::tr)["+i+"]")).getAttribute("style");
			
			 verifyScreenText(sheetName, "background-color: darkgrey",actualResult,  "verification of non applicable route value for "+handlingArea, screenName); 
		 }
	 }
	  /**
	  * Description... Verify Certificate Count
	  * @author A-7271
	  * @param count
	  * @param certificate
	  */
	 public void verifyCertificateCount(int count,String certificate)
	 {
		 int certCount=driver.findElements(By.xpath("//td[contains(.,'"+data(certificate)+"')]")).size();
		 
		 System.out.println(certCount);
		 verifyScreenTextWithExactMatch(sheetName, String.valueOf(count),String.valueOf(certCount),  "verification of certificate counts for "+data(certificate), screenName);
	 }
	/**
	 * Description... Click Applicable Route
	 * @author A-7271
	 * @throws InterruptedException
	 */
	public void clickApplicableRoute() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "htmlDiv_applRoute;xpath", "Applicable Route",screenName);	
		
		waitForSync(1);
	}
 /**
  * Description... Verify Applicable Route Value
	  * @author A-7271
	  * @param applRoute
	  */
	 public void verifyApplicableRouteValue(String applRoute)
	 {
		 String actualResult=getAttributeWebElement(sheetName, "htmlDiv_applRoute;xpath","Applicable Route", "innerText", screenName);
		
		 
		 verifyScreenTextWithExactMatch(sheetName, data(applRoute),actualResult,  "verification of applicable route value", screenName); 
					
	 }
	/**
	 * Description... Click Clear After Creating Or Editing Shift
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickClearAfterCreatingOrEditingShift() throws InterruptedException, IOException {
		waitForSync(2);
		comm.switchToFrame("default");
		switchToFrame("contentFrame","WHS049");
		clickWebElement(sheetName, "btn_Clear;xpath", "Clear", screenName);
	}
	/*A-8705
      * Assigns multiple handling area to single user
      */
	/**
	 * Description... Enter Shift Details With Multiple HA
	 * @param UserID
	 * @param handlingArea
	 * @param fromDate
	 * @param toDate
	 * @param vehicleType
	 * @throws InterruptedException
	 * @throws AWTException
	 */
      public void enterShiftDetailsWithMultipleHA(String UserID,String handlingArea ,String fromDate,String toDate,String vehicleType) throws InterruptedException, AWTException {
            waitForSync(3);
            enterValueInTextbox(sheetName, "inbx_Userid;xpath", data(UserID), "User Id", screenName);
            waitForSync(3);         
            libr.keyPress("TAB");         
            libr.keyRelease("TAB");
            enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
            waitForSync(15);  
            enterValueInTextbox(sheetName, "inbx_enterShiftFrmDate;name", fromDate, "fromDate", screenName);
            enterValueInTextbox(sheetName, "inbx_enterShiftToDate;name", toDate, "toDate", screenName);
            selectValueInDropdown(sheetName, "lst_enterShiftType;name", "1", "Shift Type", "Index");
            selectValueInDropdown(sheetName, "lst_enterVehicleType;name", data(vehicleType), "vehicleType", "VisibleText");
      }
/**
	 * Description... Enter Handling Area
	 * @author A-7271
	 * @param handlingArea
	 * @throws InterruptedException
	 */
public void enterHandlingArea(String handlingArea) throws InterruptedException
{
	enterValueInTextbox(sheetName, "inbx_handlingArea;name", data(handlingArea), "Handling Area", screenName);
	waitForSync(1);
}
/**
 * Description... Click Clear
 * @throws InterruptedException
 * @throws IOException 
 */

	public void clickClear() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_Clear;xpath", "Clear", screenName);
	}
/**
 * Description... Click Save Button
 * @throws InterruptedException
 * @throws IOException 
 */
public void clickSaveButton() throws InterruptedException, IOException {
waitForSync(2);
clickWebElement(sheetName, "btn_Save;xpath", "Save", screenName);
}
/**
 * Description... Check Show Unassigned Users
 * @throws InterruptedException
 * @throws IOException 
 */
	public void checkShowUnassignedUsers() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "chk_UnassgUsers;xpath", "Show Unassigned Users", screenName);
	}
/**
 * Description... Enter Shift Details Without From Date
 * @param UserID
 * @param handlingArea
 * @throws InterruptedException
 * @throws AWTException
 */
	public void enterShiftDetailsWithoutFromDate(String UserID,String handlingArea ) throws InterruptedException, AWTException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_Userid;xpath", data(UserID), "User Id", screenName);
		waitForSync(3);		
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
		driver.findElement(By.xpath("//input[@id='calendar_shiftfromdate']")).clear();

	}
/**
 * Description... Enter Shift Details
 * @param UserID
 * @param handlingArea
 * @param fromDate
 * @param toDate
 * @param vehicleType
 * @throws InterruptedException
 * @throws AWTException
 */
	public void enterShiftDetails(String UserID,String handlingArea ,String fromDate,String toDate,String vehicleType) throws InterruptedException, AWTException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_Userid;xpath", data(UserID), "User Id", screenName);
		waitForSync(3);		
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
		enterValueInTextbox(sheetName, "inbx_enterShiftFrmDate;name", fromDate, "fromDate", screenName);
		enterValueInTextbox(sheetName, "inbx_enterShiftToDate;name", toDate, "toDate", screenName);
		selectValueInDropdown(sheetName, "lst_enterShiftType;name", "1", "Shift Type", "Index");
		selectValueInDropdown(sheetName, "lst_enterVehicleType;name", data(vehicleType), "vehicleType", "VisibleText");
	}
/**
 * Description... Create Shift for User ID
 * @param userId
 * @param handlingArea
 * @param shiftFromDate
 * @param shiftToDate
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void createShiftforUserID(String userId,String handlingArea,String shiftFromDate,String shiftToDate) throws InterruptedException, AWTException, IOException{
        waitForSync(3);
        enterValueInTextbox(sheetName, "inbx_Userid;xpath", data(userId), "User Id", screenName);
        waitForSync(10);
        libr.keyPress("TAB");         
        libr.keyRelease("TAB");
        waitForSync(10);
        enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
        libr.keyPress("TAB");         
        libr.keyRelease("TAB");
        waitForSync(10);
        enterValueInTextbox(sheetName, "inbx_ShiftFromdate;xpath", data(shiftFromDate), "Shift From Date", screenName);
        libr.keyPress("TAB");         
        libr.keyRelease("TAB");
        waitForSync(10);
        enterValueInTextbox(sheetName, "inbx_ShiftTodate;xpath", data(shiftToDate), "Shift To Date", screenName);
        libr.keyPress("TAB");         
        libr.keyRelease("TAB");
        waitForSync(10);
        selectRandomValueFromDropdown(sheetName, "dropdown_Shifttypeassign;xpath");
        waitForSync(10);
        clickSave();
        waitForSync(10);
        handleAlert("Accept",screenName);
        
  }
/**
 * Description... Click Save
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickSave() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_Save;xpath", "Save", screenName);
		waitForSync(6);

		switchToFrame("default");
		
		try
		{

		boolean assignmentExists=driver.findElement(By.xpath("//button[@class='btn primary ui-button ui-corner-all ui-widget']")).isDisplayed();

		if(assignmentExists)
		{

			driver.findElement(By.xpath("//button[@class='btn primary ui-button ui-corner-all ui-widget']")).click();
			waitForSync(6);
		}
		}
		
		catch(Exception e)
		{
			
		}
		String frameName = "iCargoContentFrame" + "WHS049";
		driver.switchTo().frame(frameName);
		
		
	}
/**
 * Description... Delete HA Details
 * @throws InterruptedException
 */
	public void deleteHADetails() throws InterruptedException
	{
		try
		{
		clickWebElement(sheetName, "htmlDiv_deleteHADetails;xpath", "Delete HA Details", screenName);
		waitForSync(1);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
		waitForSync(3);
		String frameName = "iCargoContentFrame" + "WHS049";
		driver.switchTo().frame(frameName);
		}
		
		catch(Exception e)
		{
			
		}
	}
/**
 * Description... Verify Error Message
 * @param errorMsg
 */

	public void verifyErrorMessage(String errorMsg){

		waitForSync(2);
		ele = findDynamicXpathElement("txterror_message", sheetName,
				"Error Message", screenName);
		String actualText = ele.getText();
		String expectedText = data(errorMsg);
		verifyScreenText(screenName, expectedText, actualText,"From date is mandatory","//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter the shift details \n , 4.Check for the error message \n");
	}
/**
 * Description... Select Single Table Record
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectSingleTableRecord() throws InterruptedException, IOException {

		waitForSync(4);
		clickWebElement(sheetName, "chk_Shiftrow;xpath", "Check box",screenName);

	}
/**
 * Description... Delete Shift Record
 * @throws InterruptedException
 * @throws IOException 
 */
	public void deleteShiftRecord() throws InterruptedException, IOException {
		waitForSync(4);
		clickWebElement(sheetName, "btn_deleteFirstRecord;xpath", "Delete",screenName);

	}
/**
 * Description... Edit Shift Record
 * @param handlingArea
 * @throws Exception
 */
	public void editShiftRecord(String handlingArea) throws Exception {
		waitForSync(4);
		clickWebElement(sheetName, "btn_editFirstRecord;xpath", "Edit",screenName);
		waitForSync(4);
		driver.findElement(By.xpath("//input[@id='handlingAreaAssign']")).clear();
		enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		waitForSync(2);
		String shiftFromDate = comm.createDateFormat("dd-MMM-YYYY", 1, "DAY", "");
		String shiftToDate = comm.createDateFormat("dd-MMM-YYYY", 2, "DAY", "");
		enterValueInTextbox(sheetName, "inbx_ShiftFromdate;xpath", shiftFromDate, "Shift From Date", screenName);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		enterValueInTextbox(sheetName, "inbx_ShiftTodate;xpath", shiftToDate, "Shift To Date", screenName);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		selectRandomValueFromDropdown(sheetName, "dropdown_Shifttypeassign;xpath");
		//selectValueInDropdown(sheetName, "dropdown_Shifttypeassign;xpath", "1", "Shift Type", "Index");


		WebElement shiftType = driver.findElement(By.xpath("//select[@id='CMP_Warehouse_Defaults_HandlingAreaAssignment_ShiftTypeAssign']"));
		Select sec = new Select(shiftType);
		sec.selectByIndex(1);
		WebElement shiftTypeValue = sec.getFirstSelectedOption();
		String value = shiftTypeValue.getText();
		System.out.println(value);
		map.put("shiftFromDate", shiftFromDate);
		map.put("shiftToDate", shiftToDate);
		map.put("shiftType", value);

	}
/**
 * Description... Remove Shift Date And Provide Shift Type
 */
	public void removeShiftDateAndProvideShiftType() {
		waitForSync(2);
		driver.findElement(By.xpath("//input[@id='calendar_shiftdate']")).clear();
		String value = selectRandomValueFromDropdown(sheetName, "dropDown_ShiftTypevalues;xpath");
     	System.out.println(value);
		map.put("shifttype", value);

	}
/**
 * Description... Enter Shift Date
 * @param shiftDate
 * @throws Exception
 */
	public void enterShiftDate(String shiftDate) throws Exception {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_ShiftDate;xpath", data(shiftDate), "Shift date", screenName);

	}
/**
 * Description... Verify Unassigned User Table Columns
 */
	public void verifyUnassignedUserTableColumns() {

		//verification of Handling Area Column values
		List listOfHandlingAreas = returnListOfElements(sheetName,"lst_hdlAreatabledata;xpath");
		List<String> handlingAreasValues = returnTextListOfElements(listOfHandlingAreas);
		boolean value = false;
		for(int i=2;i<handlingAreasValues.size();i++) {
			if(handlingAreasValues.get(i).equals("")) {
				value = true;

			}
			else{
				value=false;
				break;
			}

		}
		if(value) 
			onPassUpdate("Handling_Area_Assignment_WHS049", "Handling Area Column values are null", "Handling Area Column values are null", "Handling Area column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");


		else
			onFailUpdate("Handling_Area_Assignment_WHS049", "Handling Area Column values are null", "Handling Area Column values are not null", "Handling Area column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");

		//verification of Shift From date column values
		List listOfShiftFromDates = returnListOfElements(sheetName,"lst_ShiftFromDatetabledata;xpath");
		List<String> shiftFromDateValues = returnTextListOfElements(listOfShiftFromDates);
		boolean value1 = false;
		for(int i=2;i<shiftFromDateValues.size();i++) {
			if(shiftFromDateValues.get(i).equals("")) {
				value1 = true;


			}else
			{
				value1=false;
				break;
			}

		}
		if(value1)
			onPassUpdate("Handling_Area_Assignment_WHS049", "Shift From date Column values are null", "Shift From date Column values are null", "Shift From date column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");
		else
			onFailUpdate("Handling_Area_Assignment_WHS049", "Shift From date Column values are null", "Shift From date Column values are not null", "Shift From date column" ,
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");

		//verification of Shift To date column values
		List listOfShiftToDates = returnListOfElements(sheetName,"lst_ShiftToDateTabledata;xpath");
		List<String> shiftToDateValues = returnTextListOfElements(listOfShiftToDates);
		boolean value2 = false;
		for(int i=2;i<shiftToDateValues.size();i++) {
			if(shiftToDateValues.get(i).equals("")) {
				value2 = true;
			}else
			{
				value2=false;
				break;
			}

		}
		if(value2)
			onPassUpdate("Handling_Area_Assignment_WHS049", "Shift To date Column values are null", "Shift To date Column values are null", "Shift To date column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");
		else
			onFailUpdate("Handling_Area_Assignment_WHS049", "Shift To date Column values are null", "Shift To date Column values are not null", "Shift To date column" ,
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");

		//verification of Shift Type column values
		List listOfShiftTypes = returnListOfElements(sheetName,"lst_ShiftTypeTabledata;xpath");
		List<String> shiftTypeValues = returnTextListOfElements(listOfShiftTypes);
		boolean value3 = false;
		for(int i=2;i<shiftTypeValues.size();i++) {
			if(shiftTypeValues.get(i).equals("")) {
				value3 = true;
			}else
			{
				value3=false;
				break;
			}

		}
		if(value3)
			onPassUpdate("Handling_Area_Assignment_WHS049", "Shift Type Column values are null", "Shift Type Column values are null", "Shift Type column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");
		else
			onFailUpdate("Handling_Area_Assignment_WHS049", "Shift Type Column values are null", "Shift Type Column values are not null", "Shift Type column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");

		//verification of Vehicle Type column values
		List listOfVehicleTypes = returnListOfElements(sheetName,"lst_VehicleTypeTabledata;xpath");
		List<String> vihicleTypeValues = returnTextListOfElements(listOfVehicleTypes);
		boolean value4 = false;
		for(int i=2;i<vihicleTypeValues.size();i++) {
			if(vihicleTypeValues.get(i).equals("")) {
				value4 = true;
			}else
			{
				value4=false;
				break;

			}

		}
		if(value4)
			onPassUpdate("Handling_Area_Assignment_WHS049", "Vehicle Type Column values are null", "Vehicle Type Column values are null", "Vehicle Type column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");

		else
			onFailUpdate("Handling_Area_Assignment_WHS049", "Vehicle Type Column values are null", "Vehicle Type Column values are not null", "Vehicle Type column",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");

	}
/**
 * Description... Collect User ID And Handling Areas
 */
	public void collectUserIDAndHandlingAreas() {
		waitForSync(2);

		List listOfUserIds = returnListOfElements(sheetName,"lst_assignedUsers;xpath");
		List<String> userIDValues = returnTextListOfElements(listOfUserIds);
		String userID = userIDValues.get(comm.randomNumberInList(1, userIDValues.size()-1));
		map.put("UserID", userID);

		List listOfHandlingAreas = returnListOfElements(sheetName,"lst_hdlAreatabledata;xpath");
		List<String> handlingAreaValues = returnTextListOfElements(listOfHandlingAreas);
		String handlinArea = handlingAreaValues.get(comm.randomNumberInList(1, handlingAreaValues.size()-1));
		map.put("HandlingArea", handlinArea);

	}
/**
 * Description... Collect Handling Areas
 */
	public void collectHandlingAreas() {
		waitForSync(2);

		List listOfHandlingAreas = returnListOfElements(sheetName,"lst_hdlAreatabledata;xpath");
		List<String> handlingAreaValues = returnTextListOfElements(listOfHandlingAreas);
		String handlinArea = handlingAreaValues.get(comm.randomNumberInList(1, handlingAreaValues.size()-1));
		map.put("HandlingArea", handlinArea);

	}

	/*public void createShift(String userId,String handlingArea) throws InterruptedException, AWTException{
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_Userid;xpath", data(userId), "User Id", screenName);
		waitForSync(2);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		waitForSync(2);
		selectValueInDropdown(sheetName, "dropdown_Shifttypeassign;xpath", "1", "Shift Type", "Index");

	}*/
/**
 * Description... Create Shift
 * @param userId
 * @param handlingArea
 * @param shiftFromDate
 * @param shiftToDate
 * @throws InterruptedException
 * @throws AWTException
 */
	public void createShift(String userId,String handlingArea,String shiftFromDate,String shiftToDate) throws InterruptedException, AWTException{
waitForSync(3);
enterValueInTextbox(sheetName, "inbx_Userid;xpath", data(userId), "User Id", screenName);
performKeyActions(sheetName, "inbx_Userid;xpath", "TAB", "User Id", screenName);
waitForSync(8);
enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
performKeyActions(sheetName, "inbx_HandlingArea;xpath", "TAB", "Handling Area", screenName);
waitForSync(5);
 
enterValueInTextbox(sheetName, "inbx_ShiftFromdate;xpath", data(shiftFromDate), "Shift From Date", screenName);
performKeyActions(sheetName, "inbx_ShiftFromdate;xpath", "TAB", "Shift From Date", screenName);
waitForSync(5);
 
enterValueInTextbox(sheetName, "inbx_ShiftTodate;xpath", data(shiftToDate), "Shift To Date", screenName);
performKeyActions(sheetName, "inbx_ShiftTodate;xpath", "TAB", "Shift To Date", screenName);
waitForSync(5);
selectRandomValueFromDropdown(sheetName, "dropdown_Shifttypeassign;xpath");
 
}
/**
 * Description... Create Shift WithOut Shift From Date
 * @param userId
 * @param handlingArea
 * @param shiftToDate
 * @throws InterruptedException
 * @throws AWTException
 */

	public void createShiftWithOutShiftFromDate(String userId,String handlingArea,String shiftToDate) throws InterruptedException, AWTException{
waitForSync(3);
enterValueInTextbox(sheetName, "inbx_Userid;xpath", data(userId), "User Id", screenName);
waitForSync(2);
performKeyActions(sheetName, "inbx_Userid;xpath", "TAB", "User Id", screenName);
waitForSync(3);
enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
performKeyActions(sheetName, "inbx_HandlingArea;xpath", "TAB","Handling Area", screenName);
waitForSync(2);
enterValueInTextbox(sheetName, "inbx_ShiftTodate;xpath", data(shiftToDate), "Shift To Date", screenName);
performKeyActions(sheetName, "inbx_ShiftTodate;xpath", "TAB", "Shift To Date", screenName);
selectRandomValueFromDropdown(sheetName, "dropdown_Shifttypeassign;xpath");
 
}
/**
 * Description... Enter User ID
 * @param userId
 * @throws InterruptedException
 */
	public void enterUserID(String userId) throws InterruptedException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_searchUserID;xpath", data(userId), "User Id", screenName);

	}
/**
 * Description... Verify Edited Shift Record
 * @param handlingArea
 * @param shiftFromdate
 * @param shiftToDate
 * @param shiftType
 */
	public void verifyEditedShfitRecord(String handlingArea,String shiftFromdate,String shiftToDate,String shiftType){

		//Verification of handlingArea
		ele = findDynamicXpathElement("txt_hdlArea;xpath", sheetName,
				"Handling Area", screenName);
		String actualText = ele.getText();
		String expectedText = data(handlingArea);
		verifyScreenText(screenName, expectedText, actualText, "Handling Area", "//1. Login to iCargo \n , " +
				"2.Invoke WHSO49 Screen \n , 3.Enter UserID date \n , 4.Click on list \n");

		//Verification of Shift From date
		WebElement ele1 = findDynamicXpathElement("txt_ShiftFromdate;xpath", sheetName,
				"Shift From Date", screenName);
		String actualText1 = ele1.getText();
		String expectedText1 = data(shiftFromdate);
		verifyScreenText(screenName, expectedText1, actualText1, "Shift From Date"+data(shiftFromdate), "//1. Login to iCargo \n , " +
				"2.Invoke WHSO49 Screen \n , 3.Enter UserID  \n , 4.Click on list \n");

		//Verification of Shift To date
		WebElement ele3 = findDynamicXpathElement("txt_ShiftTodate;xpath", sheetName,
				"Shift To Date", screenName);
		String actualText3 = ele3.getText();
		String expectedText3 = data(shiftToDate);
		verifyScreenText(screenName, expectedText3, actualText3, "Shift To Date"+data(shiftToDate), "//1. Login to iCargo \n , " +
				"2.Invoke WHSO49 Screen \n , 3.Enter UserID  \n , 4.Click on list \n");

		//Verification of Shift Type
		WebElement ele4 = findDynamicXpathElement("txt_Shift Type;xpath", sheetName,
				"Shift Type", screenName);
		String actualText4 = ele4.getText();
		String expectedText4 = data(shiftType);
		verifyScreenText(screenName, expectedText4, actualText4, "Shift Type", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter UserID date \n , 4.Click on list \n");

	}
/**
 * Description... Verify Shift Date In Table
 * @param shiftDate
 * @throws Exception
 */
	public void verifyShiftDateInTable(String shiftDate) throws Exception {


		String shiftDate1 = comm.createDateFormat("dd-MMM-YYYY", 2, "DAY", "");

		//verification of Shift From date column values
		List listOfShiftFromDates = returnListOfElements(sheetName,"lst_ShiftFromDatetabledata;xpath");
		List<String> shiftFromDateValues = returnTextListOfElements(listOfShiftFromDates);

		if(!shiftFromDateValues.contains(shiftDate1)) {
			onPassUpdate("Handling_Area_Assignment_WHS049", "Shift from date values not greater than "+data(shiftDate), "Shift from date values not greater than "+data(shiftDate), "Shift From date",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");

		}
		else {
			onFailUpdate("Handling_Area_Assignment_WHS049", "Shift from date values not greater than "+data(shiftDate), "Shift from date values are greater than "+data(shiftDate), "Shift From date",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");
		}

	}
/**
 * Description... Verify Created Shift
 * @param userId
 * @param handlingArea
 */
	public void verifyCreatedShift(String userId,String handlingArea) {
		try {

			//Verification of UserID
			String actvalue1 = getTextUsingJavascript(sheetName, "txt_userID;xpath", "User ID", screenName);
			String expectedValue1 = data(userId);
			verifyScreenText(screenName, expectedValue1, actvalue1, "Shift From Date", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter UserID  \n , 4.Click on list \n");				

			//Verification of handlingArea
			String actvalue = getTextUsingJavascript(sheetName, "txt_hdlArea;xpath", "Handling Area", screenName);
			String expectedValue = data(handlingArea);
			verifyScreenText(screenName, expectedValue, actvalue, "Handling Area", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter UserID  \n , 4.Click on list \n");
			onPassUpdate("Handling_Area_Assignment_WHS049", "New Shift created successfully", "New Shift created successfully", "New Shift",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter userId \n , 5.Click on list \n");

		}
		catch(Exception e) {
			onFailUpdate("Handling_Area_Assignment_WHS049", "New Shift created successfully", "New Shift not created ", "New Shift",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter from date \n , 5.Click on list \n");
		}


	}
/**
 * Description... Create New Shift For Existing User Id
 * @param handlingArea
 * @throws Exception
 */
	public void createNewShiftForExistingUserId(String handlingArea) throws Exception {
		waitForSync(4);
		clickWebElement(sheetName, "btn_editFirstRecord;xpath", "Edit",screenName);
		waitForSync(4);
		driver.findElement(By.xpath("//input[@id='handlingAreaAssign']")).clear();
		enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath", data(handlingArea), "Handling Area", screenName);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		waitForSync(2);
		String shiftFromDate = comm.createDateFormat("dd-MMM-YYYY", 2, "DAY", "");
		String shiftToDate = comm.createDateFormat("dd-MMM-YYYY", 3, "DAY", "");
		enterValueInTextbox(sheetName, "inbx_ShiftFromdate;xpath", shiftFromDate, "Shift From Date", screenName);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		enterValueInTextbox(sheetName, "inbx_ShiftTodate;xpath", shiftToDate, "Shift To Date", screenName);
		libr.keyPress("TAB");		
		libr.keyRelease("TAB");
		selectValueInDropdown(sheetName, "dropdown_Shifttypeassign;xpath", "1", "Shift Type", "Index");
		selectValueInDropdown(sheetName, "dropDown_vehicleTypeassign;xpath", "3", "Vehicle Type", "Index");
		map.put("shiftFromDate", shiftFromDate);
		map.put("shiftToDate", shiftToDate);


	}
/**
 * Description... Verify New Shift Details For Existing User
 * @param handlingArea
 * @param shiftFromDate
 * @param shiftToDate
 */
	public void verifyNewShiftDetailsForExistingUser(String handlingArea,String shiftFromDate,String shiftToDate) {
		try {
			//Verification of handlingArea
			String actvalue = getTextUsingJavascript(sheetName, "txt_hdlArea;xpath", "Handling Area", screenName);
			String expectedValue = data(handlingArea);
			verifyScreenText(screenName, expectedValue, actvalue, "Handling Area", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter UserID date \n , 4.Click on list \n");

			//Verification of Shift From date
			String actvalue1 = getTextUsingJavascript(sheetName, "txt_ShiftFromdate;xpath", "Shift From Date", screenName);
			String expectedValue1 = data(shiftFromDate);
			verifyScreenText(screenName, expectedValue1, actvalue1, "Shift From Date", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter UserID  \n , 4.Click on list \n");

			//Verification of Shift To date
			String actvalue2 = getTextUsingJavascript(sheetName, "txt_ShiftTodate;xpath", "Shift To Date	", screenName);
			String expectedValue2 = data(shiftToDate);
			verifyScreenText(screenName, expectedValue2, actvalue2, "Shift To Date", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Enter UserID  \n , 4.Click on list \n");

		}
		catch(Exception e) {


		}
	}
/**
 * Description... Verify Error Message For Different Vehicle Type
 * @param errorMsg
 */
	public void verifyErrorMessageForDifferentVehicleType(String errorMsg) {

		waitForSync(2);
		comm.switchToFrame("default");
		switchToFrame("contentFrame","WHS049");
		ele = findDynamicXpathElement("txt_vehicleTypeErrorMessage;xpath", sheetName,
				"Error Message", screenName);
		String actualText = ele.getText();
		String expectedText = data(errorMsg);
		verifyScreenText(screenName, expectedText, actualText, "Different vehicle type", "//1. Login to iCargo \n ," +
				" 2.Invoke WHSO49 Screen \n , 3.Allocate different vehicle type for user  \n , 4.Click on save \n");

	}
/**
 * Description... Change Vehicle Type
 */
	public void changeVehicleType() {
		waitForSync(2);
		selectValueInDropdown(sheetName, "dropDown_vehicleTypeassign;xpath", "1", "Vehicle Type Type", "Index");
	}
/**
 * Description... Verify Shift Type In Table
 * @param shiftType
 */
	public void verifyShiftTypeInTable(String shiftType) {
		//verification of Shift Type column values
		List listOfShiftTypes = returnListOfElements(sheetName,"lst_ShiftTypeTabledata;xpath");
		List<String> shiftTypeValues = returnTextListOfElements(listOfShiftTypes);
		boolean value = false;
		for(int i=1;i<shiftTypeValues.size();i++) {
			if(shiftTypeValues.get(i).equals(data(shiftType))) {
				value = true;
			}else
			{
				value=false;
				break;
			}

		}
		if(value)
			onPassUpdate("Handling_Area_Assignment_WHS049", "Shifts are available accroding to Shift type", 
					"Shifts are available accroding to Shift type", "Shift type", 
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , " +
					"3.Remove from date \n, 4.Select Shift typ\n , 5.Click on list \n");
		else
			onFailUpdate("Handling_Area_Assignment_WHS049", "Shifts are available accroding to Shift type", 
					"Shifts are not available accroding to Shift type", "Shift type", 
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Remove from date \n, " +
					"4.Select Shift typ\n , 5.Click on list \n" );		

	}
/**
 * Description... Verify Default Entries For Next Session For Shift Dates
 * @param shiftFromDate
 * @param shiftToDate
 */
	public void verifyDefaultEntriesForNextSessionForShiftDates(String shiftFromDate,String shiftToDate){
		waitForSync(2);
		try{
			comm.switchToFrame("default");
			switchToFrame("contentFrame","WHS049");
			//Verification of Shift From date
			String actvalue = getTextUsingJavascript(sheetName, "inbx_ShiftFromdate;xpath", "Shift From Date", screenName);
			String expectedValue = data(shiftFromDate);
			verifyScreenText(screenName, expectedValue, actvalue, "Shift From Date", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Verify the Shift From date \n");

			//Verification of Shift To date
			String actvalue1 = getTextUsingJavascript(sheetName, "inbx_ShiftTodate;xpath", "Shift To Date	", screenName);
			String expectedValue1 = data(shiftToDate);
			verifyScreenText(screenName, expectedValue1, actvalue1, "Shift To Date", "//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , 3.Verify the Shift To date \n");
			onPassUpdate("Handling_Area_Assignment_WHS049", "Shift dates are defaulted for the entire session in shift detail section", 
					"Shift dates are defaulted for the entire session in shift detail section", "Shift dates",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , " +
					"3.Enter mandatory details \n 4.Click on List \n 5.Verify shift dates \n");
		}
		catch(Exception e){
			onFailUpdate("Handling_Area_Assignment_WHS049", "Shift dates are defaulted for the entire session in shift detail section", 
					"Shift dates are not defaulted for the entire session in shift detail section", "Shift dates",
					"//1. Login to iCargo \n , 2.Invoke WHSO49 Screen \n , " +
					"3.Enter mandatory details \n 4.Click on List \n 5.Verify shift dates \n" );	
		}
	}


}




