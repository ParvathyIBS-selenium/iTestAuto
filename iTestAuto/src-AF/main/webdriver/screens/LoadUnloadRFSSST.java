package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class LoadUnloadRFSSST extends CustomFunctions {

	String sheetName = "LoadUnloadRFSSST";
	String screenName = "LoadUnloadRFSSST";


	public LoadUnloadRFSSST(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}

	/**
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the LoadUnloadRFS SST Screen
	 */
	public void invokeLoadUnloadRFSSSTScreen() throws InterruptedException, AWTException {

		try
		{

			clickActionInHHT("lurfs_btn_menuLoadUnloadRFS;xpath",proppathsst,"SST menu",screenName);
			waitForSync(2);
			writeExtent("Pass", "LoadUnloadRFS sst screen is invoked successfully");
		}

		catch(Exception e)
		{
			writeExtent("Fail", "LoadUnloadRFS sst screen is not invoked successfully");
		}
	}
	/** @author A-9847
	 * To  verify token generated is directed to dock
	 * @param expText
	 * @throws IOException
	 */
	public void verifyDirectedToDock(String expText) throws IOException
	{
		try{
			String actText=getTextAndroid("lurfs_DockservicePointName;xpath",proppathsst,"Service Point Name",screenName).trim();
			System.out.println(actText);
			if(actText.contains(data(expText)))
				writeExtent("Pass", "Successfully verified token is directed to dock "+screenName);
			else
				writeExtent("Fail", "Failed to verify token is directed to dock "+screenName);

		}catch(Exception e){
			writeExtent("Fail", "Failed to verify the token assignment to dock "+screenName);
		}

	}
	/**
	 * @author A-9844
	 * Description... To generate mobile no in format - +31 or with +33 *
	 * @param country
	 * @return mobile number
	 */

	public String createMobileNumber(String country) {

		String randStr = "";

		try {

			String randomNum_length = "8";
			int digit = Integer.parseInt(randomNum_length);
			long value1 = 1;
			long value2 = 9;

			for (int i = 1; i < digit; i++) {
				value1 = value1 * 10;
				value2 = value2 * 10;
			}

			Long randomlong = (long) (value1 + Math.random() * value2);

			randStr = randomlong.toString();

			if(data(country).equals("CDG")){

				randStr = 33 + randStr;
			}
			else if(data(country).equals("AMS")){
				randStr = 31 + randStr;
			}


			writeExtent("Pass", "Mobile number is generated " + randStr);
			System.out.println("Mobile number is generated " + randStr);

		}

		catch (Exception e) {
			System.out.println("Mobile number could not be generated");
			test.log(LogStatus.FAIL, "Mobile number could not be generated");

		}
		return randStr;
	}





	/**
	 * @author A-9844
	 * @param mobileNo
	 * @throws IOException
	 * Desc : enter mobile number
	 */
	public void enterMobileNumber(String mobileNo) throws IOException
	{

		enterValueInHHT("lurfs_inbx_MobileNumber;xpath",proppathsst,data(mobileNo),"Mobile Number ",screenName);
		waitForSync(2);

	}




	/**
	 * @author A-9844
	 * Description... To generate pager no in format 123#*
	 * @return mobile number
	 */

	public String createPagerNumber() {

		String randStr = "";

		try {

			String randomNum_length = "3";
			int digit = Integer.parseInt(randomNum_length);
			long value1 = 1;
			long value2 = 9;

			for (int i = 1; i < digit; i++) {
				value1 = value1 * 10;
				value2 = value2 * 10;
			}

			Long randomlong = (long) (value1 + Math.random() * value2);

			randStr = randomlong.toString();


			writeExtent("Pass", "Pager number is generated " + randStr);
			System.out.println("Pager number is generated " + randStr);

		}

		catch (Exception e) {
			System.out.println("Pager number could not be generated");
			test.log(LogStatus.FAIL, "Pager number could not be generated");

		}
		return randStr;
	}





	/**
	 * @author A-9844
	 * @param pagerNo
	 * @throws IOException
	 * Desc : enter pager number
	 */
	public void enterPagerNumber(String pagerNo) throws IOException
	{
		enterValueInHHT("lurfs_inbx_PagerNumber;xpath",proppathsst,data(pagerNo),"Pager Number ",screenName);
		waitForSync(2);

	}





	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws IOException
	 * Desc : get Service Point Name-Dock
	 */
	public void getServicePointName(String servicePoint) throws IOException
	{
		waitForSync(8);
		String servicepoint=getTextAndroid("lurfs_DockservicePointName;xpath",proppathsst,"Service Point Name",screenName).trim();
		String servicepointName=servicepoint.substring(servicepoint.lastIndexOf(" ")+1);
		System.out.println(servicepointName);
		map.put(servicePoint, servicepointName);
	}
	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws IOException
	 * Desc : get and verify Service Point Name-Dock
	 */
	public void getAndVerifyServicePointName(String servicePoint) throws IOException
	{
		waitForSync(8);
		try
		{
			String locatorValue=getPropertyValue(proppathsst, "lurfs_DockservicePointName;xpath");
			int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
			if(eleSize==1)
			{
				String servicepoint=getTextAndroid("lurfs_DockservicePointName;xpath",proppathsst,"Service Point Name",screenName).trim();
				String servicepointName=servicepoint.substring(servicepoint.lastIndexOf(" ")+1);
				System.out.println(servicepointName);

				verifyScreenTextWithExactMatch(screenName, data(servicePoint),servicepointName, "Verification of service point in RFS-SST",
						"Verification of service point in RFS-SST") ;
			}
			else
			{
				writeExtent("Fail", "Token is not assigned to the  service point name "+data(servicePoint)+  " on "+ screenName);
				Assert.assertFalse(true, "Failed to verify token assigned to Dock");
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","could not retrieve and verify the service point name on "+screenName);
		}
	}

	/**
	 * @author A-9844
	 * Desc - Enter seal number
	 * @throws IOException
	 */
	public void enterSealNumber(String SealNumber) throws IOException {
		enterValueInHHT("lurfs_inbx_sealNumber;xpath",proppathsst,data(SealNumber),"Seal Number",screenName);
		waitForSync(3);

	}
	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : Enter the seal numbers
	 */
	public void enterMultipleSealNumbers(String sealNumber1,String sealNumber2) throws IOException
	{
		
		enterValueInHHT("lurfs_inbx_sealNumber;xpath",proppathsst,data(sealNumber1)+","+data(sealNumber2),"Seal numbers",screenName);
		waitForSync(3);
	}
	/**
	 * @author A-9844
	 * Desc : select Seal State-Broken 
	 * @throws IOException 
	 */

	public void selectsealBrokenState() throws IOException
	{
		String locatorValue=getPropertyValue(proppathsst, "lurfs_inbx_sealBrokenState;xpath");

		androiddriver.findElement(By.xpath(locatorValue)).click();

		waitForSync(5);
	}
	/**
	 * @author A-9844
	 * @throws verify token generated is directed to counter
	 */
	public void verifyDirectedToCounter(String expText) throws IOException
	{
		String actText=getTextAndroid("lurfs_inbx_documentationCounter;xpath",proppathsst,"text",screenName);
		waitForSync(3);

		if(actText.contains(data(expText)))
		{
			writeExtent("Pass", "Successfully verified token is directed to counter"+screenName);

		}
		else
		{
			writeExtent("Fail", "Failed to verify token is directed to counter "+screenName);	

		}

	}

	/**
	 * @author A-9847
	 * @Desc To verify whether error message is displayed and to close
	 */
	public void verifyMessageDisplayed(String errorMsg ) {

		try{
			waitForSync(3);	
			String locatorValue=getPropertyValue(proppathsst, "lurfs_messageclose;xpath");			
			locatorValue=locatorValue.replace("*", errorMsg); 
			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
				writeExtent("Pass", "Successfully verified message displayed as "+errorMsg+" on"+screenName);
				androiddriver.findElement(By.xpath(locatorValue)).click();

			}  
			else
				writeExtent("Fail", "Failed to verify message displayed as "+errorMsg+" on"+screenName);	
		}
		catch(Exception e){
			writeExtent("Fail", "Could not fetch the message displayed on "+screenName);
		}

	}


	/**
	 * @author A-9847
	 * @Desc To verify whether the valid flight details given got added
	 * @param fltNum
	 */
	public void verifyFlightDetailsAdded(String fltNum){

		try{
			waitForSync(3);
			String locatorValue=getPropertyValue(proppathsst, "lurfs_txt_fieldName;xpath");			
			locatorValue=locatorValue.replace("*", data(fltNum)); 

			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
				writeExtent("Pass", "Successfully verified flight details got added as "+data(fltNum)+" on "+screenName); 
			else
				writeExtent("Fail", "Failed to verify the flight details added on"+screenName);	

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Failed to add the flight details on "+screenName);
		}


	}



	/**
	 * @author A-9847
	 * @Desc To clear the values of Flight code and Flight Number 
	 * @throws IOException
	 */
	public void clearFlightDetails() throws IOException
	{

		waitForSync(3);
		clearValueInHHT("lurfs_inbx_flightCode;xpath",proppathsst,"CarrierCode ",screenName);
		clearValueInHHT("lurfs_inbx_flightNumber;xpath",proppathsst,"Flight Number",screenName);		

	}

	/**
	 * @author A-8783
	 * @throws IOException
	 * Desc : Verify Optional field
	 */
	public void verifyOptionalFileds(String field) throws IOException
	{
		String optional;
		try{
			String mandatoryFields=getPropertyValue(proppathsst, "lurfs_txt_mandatoryIcon;xpath");
			mandatoryFields=mandatoryFields.replace("Field", data(field));
			optional =androiddriver.findElement(By.xpath(mandatoryFields)).getText();
			System.out.println(optional);
			if(optional.equals("*")) {
				writeExtent("Fail", "Could not verify that the field " + data(field) +"is optional in " +screenName);

			}
			else {
				writeExtent("Pass", "Verified that the field " + data(field) + " is optional in " +screenName);

			}

		}
		catch (Exception e) {
			writeExtent("Fail", "Verification of optional field failed"+screenName);
		}

	}
	/** 
	 * Desc - Enter ID expiry date from calendar
	 * @param date
	 * @param selectedDate
	 * @throws IOException
	 */
	public void enterIDExpiryDate(String date, String selectedDate) throws IOException
	{
		waitForSync(2);
		clickActionInHHT("lurfs_btn_idExpiryCalendar;xpath", proppathsst, "Id expiry date calendar", screenName);

		String currentDay = createDateFormatWithTimeZone("dd", 0, "DAY", "");
		System.out.println(currentDay);
		String currentMonth = createDateFormatWithTimeZone("MMM", 0, "DAY", "").toUpperCase();
		System.out.println(currentMonth);

		if (date.equals("future")) {
			String futureLocator = getPropertyValue(proppathsst, "lurfs_btn_idExpiryFuture;xpath");
			futureLocator = futureLocator.replace("*", currentMonth);
			waitForSync(2);
			androiddriver.findElement(By.xpath(futureLocator)).click();


			String dateLocator = getPropertyValue(proppathsst, "lurfs_btn_calendarDate;xpath");
			dateLocator = dateLocator.replace("*", selectedDate);
			androiddriver.findElement(By.xpath(dateLocator)).click();

			clickActionInHHT("lurfs_btn_ok;xpath", proppathsst, "Click ok", screenName);

		}

		else if(date.equals("past"))
		{

			String pastLocator = getPropertyValue(proppathsst, "lurfs_btn_idExpiryPast;xpath");
			pastLocator = pastLocator.replace("*", currentMonth);
			waitForSync(2);
			androiddriver.findElement(By.xpath(pastLocator)).click();

			String dateLocator = getPropertyValue(proppathsst, "lurfs_btn_calendarDate;xpath");
			dateLocator = dateLocator.replace("*", selectedDate);
			androiddriver.findElement(By.xpath(dateLocator)).click();

			clickActionInHHT("lurfs_btn_ok;xpath", proppathsst, "Click ok", screenName);


		}

		else {
			if (currentDay.equals("25") || currentDay.equals("26") || currentDay.equals("27")
					|| currentDay.equals("28") || currentDay.equals("29") || currentDay.equals("30")
					|| currentDay.equals("31")) {
				try {
					String day1Locator = getPropertyValue(proppathsst, "lurfs_btn_currentDay1;xpath");
					day1Locator = day1Locator.replace("*", currentDay);
					androiddriver.findElement(By.xpath(day1Locator)).click();

				} catch (Exception e) {
					String day2Locator = getPropertyValue(proppathsst, "lurfs_btn_currentDay2;xpath");
					day2Locator = day2Locator.replace("*", currentDay);
					androiddriver.findElement(By.xpath(day2Locator)).click();

				} finally {
					clickActionInHHT("lurfs_btn_ok;xpath", proppathsst, "Click ok", screenName);					}

			} else {
				String dayLocator = getPropertyValue(proppathsst, "lurfs_btn_calendarDate;xpath");
				dayLocator = dayLocator.replace("*", currentDay);
				androiddriver.findElement(By.xpath(dayLocator)).click();

				clickActionInHHT("lurfs_btn_ok;xpath", proppathsst, "Click ok", screenName);					}

		}

	}
	/**
	 * @author A-9844
	 * Desc : select Seal State-Not Broken 
	 * @throws IOException 
	 */

	public void selectsealNotBrokenState() throws IOException
	{
		clickActionInHHT("lurfs_inbx_sealNotBrokenState;xpath", proppathsst, "clik seal state not broken", screenName);

	}
	/**
	 * @author A-8783
	 * Desc - Click back in sst
	 */
	public void clickBack(String ScreenName) throws AWTException, InterruptedException, IOException
	{
		try
		{
			String locatorValue=getPropertyValue(proppathsst, "lurfs_btn_clickBack;xpath");
			locatorValue=locatorValue.replace("SCREEN", ScreenName);
			androiddriver.findElement(By.xpath(locatorValue)).click(); 
			waitForSync(3);
			writeExtent("Pass", "Clicked back in "+ScreenName+" screen");
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't click back in "+ScreenName+" screen");
		}
	}
	/**
	 * @author A-8783
	 *
	 * @throws IOException
	 * Desc : enter driver details
	 */
	public void enterDriverDetails() throws IOException
	{
		waitForSync(2);
		enterValueInHHT("lurfs_inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		enterValueInHHT("lurfs_inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		clickActionInHHT("lurfs_lst_IdType;xpath",proppathsst,"Select ID",screenName);
		waitForSync(3);
		String idType=getPropertyValue(proppathsst, "lurfs_btn_IdType;xpath");
		idType=idType.replace("idType", "Passport");

		for(int i=0;i<2;i++)
		{
			androiddriver.findElement(By.xpath(idType)).click();
		}
		waitForSync(2);
		enterValueInHHT("lurfs_inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		enterValueInHHT("lurfs_inbx_city;xpath",proppathsst,data("Origin"),"City",screenName);
		enterValueInHHT("lurfs_inbx_vehicleNo;xpath",proppathsst,data("VehicleNo"),"Vehicle No",screenName);
		scrollMobileDevice("Terms and Data Protection Policy");
		clickActionInHHT("lurfs_chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
	}
	/**
	 * @author A-8783
	 * Desc- verify ID expiry error message 
	 * @throws IOException 
	 */
	public void verifyIDExpiryErrorMessage() throws IOException {

		String locatorValue=getPropertyValue(proppathsst, "btn_errorMsg;xpath");

		locatorValue=locatorValue.replace("*", "The ID is expired. Please capture details of a valid ID");
		waitForSync(1);
		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

		if(eleSize==1)
		{
			writeExtent("Pass","Verified the ID expiry validation error message: The ID is expired. Please capture details of a valid ID");
			waitForSync(1);
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(4);					
		}
		else
		{
			writeExtent("Fail","Could not verify the error message: The ID is expired. Please capture details of a valid ID");
		}

	}


	/**
	 * @author A-8783
	 * Desc - Enter ID expiry date
	 * @param endDate
	 * @throws IOException
	 */
	public void enterIDExpiryDate(String endDate) throws IOException {
		enterValueInHHT("lurfs_inbx_date;xpath",proppathsst,data(endDate),"ID Expiry Date",screenName);
	}
	/**
	 * @author A-8783
	 * Desc - Enter Trailer number
	 * @throws IOException
	 */
	public void enterTrailerNo() throws IOException {
		enterValueInHHT("lurfs_inbx_trailerNo1;xpath",proppathsst,data("TrailerNo1"),"Trailer number 1",screenName);
		waitForSync(3);
		enterValueInHHT("lurfs_inbx_trailerNo2;xpath",proppathsst,data("TrailerNo2"),"Trailer number 2",screenName);
	}

	/**
	 * @author A-8783
	 * Dec - Verify trailer number fields are present
	 * @throws IOException
	 */
	public void verifyTrailerNoField() throws IOException {
		int trailerNo1;
		int trailerNo2;
		waitForSync(2);
		try {
			trailerNo1=getSizeOfMobileElement("lurfs_inbx_trailerNo1;xpath",proppathsst);
			trailerNo2=getSizeOfMobileElement("lurfs_inbx_trailerNo2;xpath",proppathsst);
			if(trailerNo1==1 && trailerNo2==1) {
				writeExtent("Pass", "Verified that trailer no 1 and Trailer no 2 fields are present");
			}
			else {
				writeExtent("Fail", "Could not verify  trailer no 1 and Trailer no 2 fields are present");

			}
		}

		catch (Exception e) {
			writeExtent("Fail", "Could not verify  trailer no 1 and Trailer no 2 fields are present");

		}
	}



	/**
	 * @author A-9844
	 * @param carrierCode
	 * @param FlightNo
	 * @param date
	 * @throws IOException
	 * Desc : Add shipment
	 */
	public void addFlightDetails(String carrierCode,String FlightNo,String flightDate ) throws IOException
	{
		enterValueInHHT("lurfs_inbx_flightCode;xpath",proppathsst,data(carrierCode),"CarrierCode ",screenName);
		enterValueInHHT("lurfs_inbx_flightNumber;xpath",proppathsst,data(FlightNo),"Flight Number",screenName);
		waitForSync(2);
		if(flightDate.equals("CurrentDate"))
		{
			clickActionInHHT("lurfs_btn_Today;xpath",proppathsst,"Today's Date",screenName);
			waitForSync(2);
		}

		else if(flightDate.equals("PreviousDate"))
		{
			clickActionInHHT("lurfs_btn_yesterday;xpath",proppathsst,"Yesterday's Date",screenName);
			waitForSync(2);
		}
		waitForSync(5);
		clickActionInHHT("lurfs_btn_addFlight;xpath",proppathsst,"Add Flight",screenName);
		waitForSync(2);
	}

	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : Click proceed
	 */
	public void clickProceed() throws IOException
	{
		clickActionInHHT("lurfs_btn_proceed;xpath",proppathsst,"Proceed",screenName);
		waitForSync(3);
	}

	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : Click proceed
	 */
	public void verifyMandatoryFileds(String field, String expText) throws IOException
	{
		try{
			String mandatoryFields=getPropertyValue(proppathsst, "lurfs_txt_mandatoryIcon;xpath");
			mandatoryFields=mandatoryFields.replace("Field", data(field));

			String actText=androiddriver.findElement(By.xpath(mandatoryFields)).getText();
			System.out.println(actText);
			if (actText.equals(data(expText)))

			{
				writeExtent("Pass", "Verified mandatory icon for "+data(field));
			}
			else

			{
				writeExtent("Fail", "Failed to Verify mandatory icon for "+data(field));
			}
		}
		catch (Exception e) 

		{
			writeExtent("Fail", "Field is not marked as mandatory "+screenName);
		}




	}




	/**
	 * @author A-9844
	 * @param field
	 * @param expText
	 * @throws IOException
	 * Desc : Click proceed
	 */
	public void verifyFiledIsPresent(String field) throws IOException
	{
		try{
			String fieldName=getPropertyValue(proppathsst, "lurfs_txt_fieldName;xpath");
			fieldName=fieldName.replace("*", data(field));
			waitForSync(3);

			String actText=androiddriver.findElement(By.xpath(fieldName)).getText();
			System.out.println(actText);
			if (actText.equals(data(field))){
				writeExtent("Pass", "Verified field "+data(field)+" is present on "+screenName); 
			}
			else{
				writeExtent("Fail", "Failed to Verify  field "+data(field)+" is present on "+screenName);
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Field is not present on "+screenName);
		}



	}


	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : Click proceed
	 */
	public void verifyIDTypeValueNotDefaulted(String expText) throws IOException
	{
		try{
			String locator=getPropertyValue(proppathsst, "lurfs_lst_IdType;xpath");


			String actText=androiddriver.findElement(By.xpath(locator)).getText();
			System.out.println(actText);
			if (actText.equals(data(expText))){
				writeExtent("Pass", "Verified ID Type is defaulted with value "+(data(expText))); 
			}
			else{
				writeExtent("Fail", "Failed to Verify ID Type is defaulted with value "+(data(expText))); 
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Field is not defaulted "+screenName);
		}



	}


	/**
	 * @author A-8783
	 * @param date
	 * @throws IOException
	 * Desc : enter driver details
	 */
	public void enterDriverDetailsWithScroll(String date) throws IOException
	{

		enterValueInHHT("lurfs_inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		enterValueInHHT("lurfs_inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		clickActionInHHT("lurfs_lst_IdType;xpath",proppathsst,"Select ID",screenName);
		waitForSync(3);
		String idType=getPropertyValue(proppathsst, "lurfs_btn_IdType;xpath");
		idType=idType.replace("idType", "Passport");

		for(int i=0;i<2;i++)
		{
			androiddriver.findElement(By.xpath(idType)).click();
		}
		waitForSync(2);
		enterValueInHHT("lurfs_inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		enterValueInHHT("lurfs_inbx_city;xpath",proppathsst,data("Origin"),"City",screenName);
		enterValueInHHT("lurfs_inbx_date;xpath",proppathsst,data(date),"DropOff/PickUp Date",screenName);
		enterValueInHHT("lurfs_inbx_vehicleNo;xpath",proppathsst,data("VehicleNo"),"Vehicle No",screenName);
		scrollMobileDevice("Terms and Data Protection Policy");
		clickActionInHHT("lurfs_chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
	}

	/**
	 * @author A-9844
	 * @param date
	 * @throws IOException
	 * Desc : enter driver details
	 */
	public void enterDriverDetailsWithScroll(String date,String city) throws IOException
	{

		scrollMobileDevice("First Name");
		enterValueInHHT("lurfs_inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		enterValueInHHT("lurfs_inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		clickActionInHHT("lurfs_lst_IdType;xpath",proppathsst,"Select ID",screenName);
		waitForSync(3);
		String idType=getPropertyValue(proppathsst, "lurfs_btn_IdType;xpath");
		idType=idType.replace("idType", "Passport");

		for(int i=0;i<2;i++)
		{
			androiddriver.findElement(By.xpath(idType)).click();
		}
		waitForSync(2);
		enterValueInHHT("lurfs_inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		enterValueInHHT("lurfs_inbx_city;xpath",proppathsst,data(city),"City",screenName);
		enterValueInHHT("lurfs_inbx_date;xpath",proppathsst,data(date),"DropOff/PickUp Date",screenName);
		enterValueInHHT("lurfs_inbx_vehicleNo;xpath",proppathsst,data("VehicleNo"),"Vehicle No",screenName);
		scrollMobileDevice("Terms and Data Protection Policy");
		clickActionInHHT("lurfs_chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
	}




	/**
	 * @author A-8783
	 * @param vehicleType
	 * Desc : Select vehicle Type
	 */
	public void selectVehicletype(String vehicleType)
	{
vehicleType="All";
		
		try
		{
			String vehicle=getPropertyValue(proppathsst, "lurfs_btn_vehicleType;xpath");
			vehicle=vehicle.replace("vehicleType",vehicleType);

			androiddriver.findElement(By.xpath(vehicle)).click();
			waitForSync(2);
			writeExtent("Pass", "Vehicle Type selected as "+vehicleType+" on "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not select vehicle Type on "+screenName);
		}
	}
	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws IOException
	 * Desc : get and verify Service Point Name-Counter
	 */
	public void getAndVerifyCounterServicePointName(String servicePoint) throws IOException
	{
		waitForSync(8);
		try
		{
			String locatorValue=getPropertyValue(proppathsst, "txt_CounterServicePoint;xpath");
			int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
			if(eleSize==1)
			{
				String servicepoint=getTextAndroid("txt_CounterServicePoint;xpath",proppathsst,"counter Service Point Name",screenName).trim();
				String servicepointName=servicepoint.substring(servicepoint.lastIndexOf(" ")+1);
				System.out.println(servicepointName);
				verifyScreenTextWithExactMatch(screenName, data(servicePoint),servicepointName, "Verification of  service point in SST", "Verification of counter service point in SST") ;
			}
			else
			{
				writeExtent("Fail", "Token is not assigned to the  service point name "+data(servicePoint)+  " on "+ screenName);
				Assert.assertFalse(true, "Failed to verify token assigned to Counter");
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","could not retrieve and verify the service point name on "+screenName);
		}
	}

	/**
	 * @author A-8783
	 * @throws IOException
	 * Desc : verify and store token
	 */
	public void verifyTokenGeneration(String tokenId) throws IOException
	{
		waitForSync(8);
		String tokenValue=getTextAndroid("lurfs_inbx_tokenNum;xpath",proppathsst,"Token Id",screenName);

		if(tokenValue.equals(""))
		{
			writeExtent("Fail", "Token is not generated  on "+screenName);
			Assert.assertFalse(true, "Token is not generated  on "+screenName);
		}
		else
		{
			writeExtent("Pass", "Token is generated with value "+tokenValue+" on "+screenName);	

		}

		waitForSync(6);
		map.put(tokenId, tokenValue);
	}



	/**
	 * @author A-9844
	 * Desc - Enter trucking company name and Verify
	 * @param truckCompany
	 * @throws IOException
	 */
	public void enterTruckingCompanyName(String truckCompanyCode,String expText) throws IOException {
		try{
			enterValueInHHT("lurfs_inbx_truckingCompanyName;xpath",proppathsst,data(truckCompanyCode),"Trucking Company Name",screenName);
			waitForSync(2);
			String locator=getPropertyValue(proppathsst, "lurfs_drp_truckingCompanyName;xpath");
			locator=locator.replace("*", data(expText));

			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(2);
			writeExtent("Pass", "Trucking company selected as "+data(expText)+" on "+screenName);

			String actText=getTextAndroid("lurfs_inbx_truckingCompanyName;xpath",proppathsst,"Trucking company",screenName);
			if(actText.equals(data(expText)))
			{
				writeExtent("Pass", "Successfully verified Trucking company selected on"+screenName);

			}
			else
			{
				writeExtent("Fail", "Failed to verify Trucking company selected on "+screenName);	

			}

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Could not select Trucking company on "+screenName);
		}

	}


	/**
	 * @author A-9844
	 * Desc- verify message
	 * @throws IOException 
	 */
	public void verifyMessage(String expText) throws IOException {

		try{

			scrollMobileDevice("Terms and Data Protection Policy");
			waitForSync(3);
			String actText=getTextAndroid("lurfs_txt_message;xpath",proppathsst,"Text Message",screenName);
			waitForSync(3);



			if(actText.equals(data(expText)))
			{
				writeExtent("Pass", "Successfully verified text message displayed on"+screenName);

			}
			else
			{
				writeExtent("Fail", "Failed to verify text message displayed on "+screenName);	

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Could not fetch the text displayed on "+screenName);
		}
	}



	/**
	 * @author A-8783
	 * Desc - Enter trucking company name
	 * @param truckCompany
	 * @throws IOException
	 */
	public void enterTruckingCompanyName(String truckCompany) throws IOException {
		enterValueInHHT("lurfs_inbx_truckingCompanyName;xpath",proppathsst,data(truckCompany),"Trucking Company Name",screenName);
	}



}