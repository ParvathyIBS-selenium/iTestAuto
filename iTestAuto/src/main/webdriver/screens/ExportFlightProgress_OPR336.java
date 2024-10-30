package screens;

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

public class ExportFlightProgress_OPR336 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "ExportFlightProgress_OPR336";
	String screenName = "Export Flight Progress";
	String screenId = "OPR336";

	public ExportFlightProgress_OPR336(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description... Select Flight Check Box
 * @throws InterruptedException
 * @throws IOException 
 */
	// selects flight check box
	public void selectFlightCheckBox() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_selectFlight;xpath",
				"Flight Select Check Box", screenName);

	}
	/**
	 * @author A-10690
	 * Desc : Verifying flght is  displayed in export flight progress
	 * @throws InterruptedException
	 */
	public void verifyFlightdisplayed(String flightNumber) throws InterruptedException
	 
	{		
		 getTextAndVerify(sheetName, "table_flightDetails1;xpath", "Flight Status", screenName, "Verification of flight status",
					data(flightNumber), "contains");	
		}
	/**
	 * @author A-10690
	 * Desc - Verify export print status column colour 
	 * @param expected colour
	 * @param flight number
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void verifyExportPrintStatusColour(String colour,String flightNumber) throws InterruptedException, AWTException{
		
		
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "txt_printmanifestcolumnnum;xpath");
		String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-csid");
		String locator1=xls_Read.getCellValue(sheetName, "txt_printmanifestcolour;xpath");
		locator1=locator1.replace("flgt",data(flightNumber));
		locator1=locator1.replace("*",columnnumber);
		locator1=locator1.replace("clr",colour);

		if(driver.findElements(By.xpath(locator1)).size()==1)
		{
			writeExtent("Pass","Successfully verified  export print status colour on "+screenName);
		}
		else{
			writeExtent("Fail","Failed to verify export print status colour "+screenName);
		}

		
		
	}	
	/**
	 * @author A-8783
	 * Desc - Click on drop down more options 
	 * @throws InterruptedException
	 * @throws IOException
	 */
public void clickDropdownOptions() throws InterruptedException, IOException {
	clickWebElement(sheetName, "dpDwn_moreOptions;xpath", "Drop down button",screenName);
}
/**
 * @author A-8783
 * Desc - Capture or remove satchel handover
 * @throws InterruptedException
 * @throws IOException
 */
public void clickCaptureRemoveSatchelHandover() throws InterruptedException, IOException {
	clickWebElement(sheetName, "btn_satchelHandover;xpath", "Capture / Remove Satchel Handover",screenName);
waitForSync(2);
}
/**
 * @author A-9847
 * @desc To verify the warning message and perform the required action(yes/no)
 * @param expText
 * @param action
 */
		public void verifyWarningMsg(String expText,String action){

			try{
				switchToFrame("default");
				String actText = getElementText("Generic_Elements", "txt_AlertText;xpath", "Confirmation Message",screenName);
				verifyScreenTextWithExactMatch(sheetName, expText, actText, "Warning message","Warning message");
				if(action.equals("yes"))
					clickWebElement("Generic_Elements", "btn_yes;xpath","Yes Button", screenName);
				else
					clickWebElement("Generic_Elements", "btn_no;xpath","No Button", screenName);
				switchToFrame("contentFrame", "OPR336");
			}
			catch(Exception e) {
				writeExtent("Fail", " Failed to verify the Warning message "+expText+ " on " + screenName);
			}

		}


/**
 * @author A-8783
 * Desc - Verify Satchel handover status
 * @param status
 * @throws InterruptedException
 */
public void verifySatchelHandoverStatus(String status) throws InterruptedException {


	String locator = xls_Read.getCellValue(sheetName, "txt_SatchelHandoverStatus;xpath");
	String actStatus = driver.findElement(By.xpath(locator)).getAttribute("data-tooltip-text");
	String actColour = driver.findElement(By.xpath(locator)).getAttribute("class");
	System.out.println("Tooltip " + actStatus);
	System.out.println("Colour "+ actColour);
	waitForSync(1);
	verifyScreenText(screenName, data(status), actStatus, "Verify Satchel Handover Status", "Satchel Handover Status");
	
	if(data(status).equals("Completed")) {
		verifyScreenText(screenName, "ok", actColour, "Verify Satchel Handover Status icon as tick mark ", "Satchel Handover Status");
	}
	else if(data(status).equals("Pending")) {
		verifyScreenText(screenName, "minus", actColour, "Verify Satchel Handover Status icon as cross mark ", "Satchel Handover Status");

	}
	
}
/**
 * @author A-8783 
 * Desc - Verify column is present in table
 * @param colName
 */
public void verifyColumn(String colName) {
	try {
		String column = "";
		boolean found = false;
		String locator = xls_Read.getCellValue(sheetName, "txt_columnName;xpath");
		List<WebElement> element = driver.findElements(By.xpath(locator));

		for (WebElement ele : element) {
			column = ele.getText();

			if (column.contains(data(colName))) {
				found = true;
				break;
			}
		}

		if (found)
			writeExtent("Pass", "Verified that the column" + data(colName) + " is present in " + screenName);

		else
			writeExtent("Fail", "Could not verify that the column " + data(colName) + " is present in " + screenName);

	} catch (Exception e) {
		writeExtent("Fail", "Could not verify that the column " + data(colName) + " is present in " + screenName);
	}
}

/**
 * @author A-8783
 * Desc - Verify and handle alert
 * @param expectedMsg
 * @throws InterruptedException
 * @throws IOException
 */
public void verifyAndHandleAlert(String expectedMsg) throws InterruptedException, IOException {
	switchToFrame("default");
	String actualMsg = getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath", "Confirmation Message",
			screenName);

	verifyScreenText(sheetName, data(expectedMsg), actualMsg, "Confirmation Msg", screenName);

	clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes Button", screenName);
	switchToFrame("contentFrame", "OPR336");
}


	/**
	 * 
	 * @param verificationValue
	 */
	public void verifyManifestStatus(String verificationValue)
	{
		By element = getElement(sheetName, "txt_manifestStatus;xpath");
		String displayedText = driver.findElement(element).getText().trim();
		if(verificationValue.equalsIgnoreCase(displayedText))
		{
			test.log(LogStatus.PASS, "The value in the Manifest Status column  is successfully verified");
		}
		else
		{
			test.log(LogStatus.FAIL, "The value in the Manifest Status column  doesn't match. Displayed status is: "+displayedText);
		}

	}
	/**
	 * @author A-9175
	 * BUP status
	 * @param verificationValue
	 */
	public void verifyBUPStatus(String verificationValue)
	{
		By element = getElement(sheetName, "txt_BUPpercentage;xpath");
		String displayedText = driver.findElement(element).getText().trim();
		if(data(verificationValue).equalsIgnoreCase(displayedText))
		{
			test.log(LogStatus.PASS, "The value in the BUP Status column  is successfully verified");
		}
		else
		{
			test.log(LogStatus.FAIL, "The value in the BUP Status column  doesn't match. Displayed status is: "+displayedText);
		}
	}
	/**
	 * @Description :captureDates details
	 * @author A-9175
	 * @param fromdate
	 * @param toDate
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureDates(String fromdate,String toDate) throws InterruptedException, IOException
	{
		
		enterValueInTextbox(sheetName, "inbx_fromdate;id",data(fromdate), "From Date", screenName);
		enterValueInTextbox(sheetName, "inbx_fromTime;id","00:00", "From Time", screenName);
		enterValueInTextbox(sheetName, "inbx_toDate;id",data(toDate), "To Date", screenName);
		waitForSync(2);
	}
	
	/**
	 * @Desc : Click More Options
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickMoreOptions() throws InterruptedException, IOException
	{
		
		clickWebElement(sheetName, "htmlDiv_Moreoptions;id", "more options",screenName);
		waitForSync(2);
	}
	
	/**
	 * @desc: enter Flight Details
	 * @author A-9175
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterFlightDetails(String carrierCode,String flightNumber,String flightDate) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_flightCarrierCode;id",data(carrierCode), "carrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id",data(flightNumber), "flightNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id",data(flightDate), "flightDate", screenName);
		waitForSync(2);
	}
	
	/**
	 * @Desc : Verifying Manifest Print status color
	 * @author A-9175
	 * @param flightNumber
	 */
	public void verifyManifestPrintStatusGrey(String flightNumber) {
		

		String locator=xls_Read.getCellValue(sheetName, "img_manifestPrintStatus;xpath");
		locator=locator.replace("loc", data(flightNumber));
		String clr=driver.findElement(By.xpath(locator)).getAttribute("class");   
		if(clr.contains("grey"))
			writeExtent("Pass", "Successfully Verified Grey Color for Manifest Print Status on "+ screenName + " Page");
		else
			writeExtent("Fail","Not Verified Grey Color for Manifest Print Status on "+ screenName + " Page");

	}

	/**
	 * @author A-6260
	 * Desc:List flight with destination
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @param destination
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listFlightWithDestination(String carrierCode,String flightNumber,String flightDate,String destination) throws InterruptedException, IOException
	{
		
		enterValueInTextbox(sheetName, "inbx_totimeFilter;name","23:00", "To time", screenName);
		clickWebElement(sheetName, "htmlDiv_Moreoptions;id", "more options",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightCarrierCode;id",data(carrierCode), "carrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id",data(flightNumber), "flightNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id",data(flightDate), "flightDate", screenName);
		enterValueInTextbox(sheetName, "inbx_destination;xpath",data(destination), "Destination", screenName);
		clickWebElement(sheetName, "btn_list;id", "List button",
				screenName);
		waitForSync(2);
	}

	/**
	 * @author A-9175
	 * Desc : Verifying manifest sucessfull image
	 */
	
	public void verifyManifestStatusImage()
	{
		try
		{
			By element = getElement(sheetName, "txt_manifestStatusimg;xpath");
			boolean displayedImg = driver.findElement(element).isDisplayed();
			if(displayedImg)
			{
				
				writeExtent("Pass", "The value in the Manifest Status column  is successfully verified in " + screenName);
			}
			else
			{
			
				writeExtent("Fail", "The value in the Manifest Status column  doesn't match in " + screenName);
			}
			
		}
	catch (Exception e) {
		writeExtent("Fail", "Manifest sucessfull Image not found " + screenName);
	}

	}

/**
 * Description... Click Auto Load Plan
 * @throws InterruptedException
 * @throws IOException 
 */
	// clicks auto load plan button
	public void clickAutoLoadPlan() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_AutoLoadPlan;xpath",
				"Auto Load Plan Button", screenName);
waitForSync(5);
	}

	/**
	 * Description... Change To Date
	 * @param toDate
	 * @throws InterruptedException
	 */
	public void changeToDate(String toDate) throws InterruptedException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_todate;xpath", toDate, "To Date", screenName);	
				
	}

/**
 * Description... Change From Date
 * @param fromDate
 * @throws InterruptedException
 */
public void changeFromDate(String fromDate) throws InterruptedException {
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_fromdate;xpath",fromDate, "From Date", screenName);	
				
	}
	
/**
 * Description... Click Booked Flight CheckBox
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickBookedFlightCheckBox() throws InterruptedException, IOException
	{
		waitForSync(3);
		clickWebElement(sheetName, "chk_bookedFlight;xpath", "check box of booked flights",
				screenName);
	}
	
/**
 * Description... Enter Destination
 * @param destination
 * @throws InterruptedException
 */
	public void enterDestination(String destination) throws InterruptedException
	{
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_destination;xpath",data(destination), "Destination", screenName);	
		
		
	}
	
	/**
	 * Desription : List the flight details
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void listFlight(String carrierCode,String flightNumber,String flightDate,String oprStatus) throws InterruptedException, IOException
	{
		
		enterValueInTextbox(sheetName, "inbx_totimeFilter;name","23:00", "To time", screenName);
		clickWebElement(sheetName, "htmlDiv_Moreoptions;id", "more options",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightCarrierCode;id",data(carrierCode), "carrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id",data(flightNumber), "flightNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id",data(flightDate), "flightDate", screenName);
		
		if(oprStatus.equals("Manifest"))
		{
			selectValueInDropdown(sheetName, "lst_manifestStatus;name", "Completed", "Manifest Status",
					"VisibleText");
		}
		clickWebElement(sheetName, "btn_list;id", "List button",
				screenName);
		waitForSync(2);
	}
	
	
	
	public void verifyFlightDetails(String flightNumber) throws InterruptedException
			 
	{		
		 getTextAndVerify(sheetName, "table_flightDetails;xpath", "Flight Status", screenName, "Verification of flight status",
					data(flightNumber), "contains");	
		}
	
	
/**
 * Description... Click List Button	
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickList() throws InterruptedException, IOException
	{
		clickWebElement("Generic_Elements", "btn_List;xpath", "List Button",screenName);
		waitForSync(5);
	}
	
/**
 * Description... Select flight	
 * @param flight
 * @throws InterruptedException
 */
	public void selectflight(String flight) throws InterruptedException
	{
		String  xpath=xls_Read.getCellValue("ExportFlightProgress_OPR262","chk_selectFlght;xpath").replace("flight",flight);
		waitForSync(5);
		clickWebElement(xpath,"checkbox","Export Flight Progress");
		
		
	}
	

	
}
