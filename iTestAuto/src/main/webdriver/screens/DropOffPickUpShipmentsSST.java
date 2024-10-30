package screens;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.appium.java_client.MobileElement;

public class DropOffPickUpShipmentsSST extends CustomFunctions {

	String sheetName = "DropOffPickUpShipmentsSST";
	String screenName = "DropOffPickUpShipmentsSST";


	public DropOffPickUpShipmentsSST(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}
	/**
	 * @author A-8783
	 * Desc - Verify Add button
	 * 
	 */
	public void verifyAddButton() {
		int size = getSizeOfMobileElement("btn_addShipment;xpath", proppathsst);
		if(size==1) {
			writeExtent("Pass", "Verified that the button Add is present in SST");
		}
		else {
			writeExtent("Fail", "The button Add is not present in SST");
		}

	}
	/**
	  * @desc: The addAWB method is invoked when the pickup/drop-off options are displayed during the capture of card details.
	  * @author A-9175
	  * @param carrCode
	  * @param awb
	  * @param fullAWBNo
	  * @throws IOException
      * @throws InterruptedException
	  * @throws AWTException
	  */
	 public void addAwb(String carrCode,String awb,String fullAWBNo) throws IOException, InterruptedException, AWTException

	 {
		 String locatorValue=getPropertyValue(proppathsst, "btn_menuDropOffPickUp;xpath");
		 int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
		 if(eleSize==1)
		 {
			 invokeDropOffPickUpShipmentsSSTScreen();
			 addShipment(carrCode,awb);
		 }
		 else
		 {
			 enterAWBNo(fullAWBNo);
			 clickAddButton();
			 waitForSync(2);
		 }

	 }

    /**@author A-10328
* Description - Click Add AWB 
* @throws IOException
*/

public void clickAddAWB() throws IOException

{
clickActionInHHT("btn_addAWB;xpath",proppathsst,"click Add AWB",screenName);
waitForSync(2);
	
 }

/**@author A-10328
* Description - Enter AWB Number
* @param value
* @throws IOException
*/
public void enterAWBNo(String value) throws IOException

{
enterValueInHHT("inbx_AWBNo;xpath",proppathsst,data(value),"add shipment",screenName);
waitForSync(2);
}

/**@author A-10328
* Description - Click ADD Button
* @throws IOException
*/
public void clickAddButton() throws IOException

{
clickActionInHHT("btn_clickAdd;xpath",proppathsst,"click Add AWB",screenName);
waitForSync(5);
}


	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws IOException
	 * Desc : get and verify Service Point Name-Dock
	 */
	public void fetchAndVerifyServicePointName(String servicePoint) throws IOException
	{

		waitForSync(8);
		try{
			String locatorValue=getPropertyValue(proppathsst, "txt_dockAssigned;xpath");
			int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
			if(eleSize==1)
			{
				String servicepoint=getTextAndroid("txt_dockAssigned;xpath",proppathsst,"Dock Service Point Name",screenName).trim();

				String servicepointName=servicepoint.substring(servicepoint.lastIndexOf(" ")+1);
				System.out.println(servicepointName);
				verifyScreenTextWithExactMatch(screenName, data(servicePoint),servicepointName, "Verification of dock service point in SST", "Verification of dock service point in SST") ;
			}
			else
			{
				writeExtent("Fail", "Token is not assigned to the  service point name "+data(servicePoint)+  " on "+ screenName);
				Assert.assertFalse(true, "Failed to verify token assigned to DOCK");

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","could not retrieve and verify the service point name on "+screenName);
		}
	}

	 /**
		  * @author A-9844
		  * Desc - check disclaimer box
		  */
		 public void checkDisclaimerBox() throws AWTException, InterruptedException, IOException
		 {
			 try
			 {
				 scrollMobileDevice("Terms and Data Protection Policy");
				 clickActionInHHT("chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
			 }
			 catch(Exception e)
			 {
				 writeExtent("Fail", "Couldn't check disclaimer box on " +" screen");
			 }
		 }



	/**
	 * @author A-10690
	 * @param date
	 * @param city
	 * @param Agentcode
	 * @param expagent
	 * @throws IOException
	 * Desc : enter driver details in bonded  side and alos checking whether agent name  is autosuggested
	 */
	public void enterDetailsBondedSideWithAgentAutopopulated(String date,String city,String Agentcode,String expagent) throws IOException
	{

		enterValueInHHT("inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		enterValueInHHT("inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		clickActionInHHT("lst_idType;xpath",proppathsst,"Select ID",screenName);
		waitForSync(3);
		String idType=getPropertyValue(proppathsst, "btn_idType;xpath");
		idType=idType.replace("idType", "Passport");

		for(int i=0;i<2;i++)
		{
			androiddriver.findElement(By.xpath(idType)).click();
		}
		waitForSync(2);
		enterValueInHHT("inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		enterValueInHHT("inbx_city;xpath",proppathsst,data(city),"City",screenName);
		enterValueInHHT("inbx_date;xpath",proppathsst,data(date),"DropOff/PickUp Date",screenName);
		enterValueInHHT("inbx_AgentName;xpath",proppathsst,data(Agentcode),"agentnamecode",screenName);
		waitForSync(2);
		String locator=getPropertyValue(proppathsst, "drp_AgentName;xpath");
		locator=locator.replace("*", data(expagent));

		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(2);
		writeExtent("Pass", "Agent name selected as"+data(expagent)+" on "+screenName);
		clickActionInHHT("chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
	}



	/**
	 * @author A-8783
	 * @param expColourCode
	 * @throws IOException
	 * Test : verify colour code of time elapse for flight closure
	 */
	public void verifyAwbIndication(String expColourCode,String awbNo) throws IOException
	{
		String locator=getPropertyValue(proppathsst, "img_error;xpath");
		locator=locator.replace("awbno",data(awbNo));	
		String actColourCode="";
		MobileElement elem = (MobileElement) androiddriver.findElement(By.xpath(locator));

		org.openqa.selenium.Point point = elem.getCenter();
		int centerX = point.getX();
		int centerY = point.getY();

		File scrFile = ((TakesScreenshot)androiddriver).getScreenshotAs(OutputType.FILE);

		BufferedImage image = ImageIO.read(scrFile);
		// Getting pixel color by position x and y 
		int clr = image.getRGB(centerX,centerY); 
		int red   = (clr & 0x00ff0000) >> 16;
		int green = (clr & 0x0000ff00) >> 8;


		if(red>0)

			actColourCode="Red";

		else if(green>0)

			actColourCode="Green";


		verifyScreenTextWithExactMatch(screenName, expColourCode, actColourCode, "Verify AWB status indicator", screenName);

	}

	/**
	 * @author A-8783
	 * Desc- verify ID expiry error message 
	 * @throws IOException 
	 */
	public void verifyIDExpiryErrorMessage() throws IOException {

		try {
			waitForSync(4);
			String locatorValue = getPropertyValue(proppathhht, "btn_errorMsg;xpath");

			locatorValue = locatorValue.replace("*", "The ID is expired. Please capture details of a valid ID");
			waitForSync(4);
			int eleSize = androiddriver.findElements(By.xpath(locatorValue)).size();

			if (eleSize == 1) {
				writeExtent("Pass",
						"Verified the ID expiry validation error message: The ID is expired. Please capture details of a valid ID");
				androiddriver.findElement(By.xpath(locatorValue)).click();
				waitForSync(4);
			} else {
				writeExtent("Fail",
						"Could not verify the error message: The ID is expired. Please capture details of a valid ID");
			}
		} catch (Exception e) {
			writeExtent("Fail","Verification of error message failed");
		}
	}
	/**
	 * @author A-9847
	 * @Desc To enter the no:of AWBs
	 * @param num
	 * @throws IOException
	 */
	public void enterNoOfAWBs(String num) throws IOException
	{
		waitForSync(2);
		enterValueInHHT("inbx_noOfAWBs;xpath",proppathsst,num,"No: of AWBs",screenName);
		waitForSync(2);

	}

	/**
	 * @author A-9847
	 *@Desc To select the Nature of Shipments as Standard/BUP/DGR/Live animals
	 * @param nature
	 * @throws IOException
	 */

	public void selectNatureOfShipments(String nature) throws IOException{

		try
		{
			String locator=getPropertyValue(proppathsst, "btn_natureofShipments;xpath");
			locator=locator.replace("*", nature);	 
			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(2);
			writeExtent("Pass", "Successfully selected Nature of Shipments as "+nature+" on "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not select nature of Shipments on "+screenName);
		}


	}

	/**
	 * @author A-8783
	 * Desc -  To verify Add ULD button
	 * @throws IOException
	 */
	public void verifyAddULD() throws IOException
	{
		int size = getSizeOfMobileElement("btn_addULD;xpath", proppathsst);
		if(size==1) {
			writeExtent("Pass", "Verified that the button Add a ULD is present in SST");
		}
		else {
			writeExtent("Fail", "The button Add a ULD is not present in SST");
		}

	}

	/**
	 * 
	 * @throws IOException
	 * Desc : Verify mandatory fields
	 */
	public void verifyMandatoryFields(String field, String expText) throws IOException
	{
		try{
			String mandatoryFields=getPropertyValue(proppathsst, "txt_mandatoryIcon;xpath");
			mandatoryFields=mandatoryFields.replace("Field", data(field));

			String actText=androiddriver.findElement(By.xpath(mandatoryFields)).getText();
			System.out.println(actText);
			if (actText.equals(data(expText))){
				writeExtent("Pass", "Verified mandatory icon for "+data(field)); 
			}
			else{
				writeExtent("Fail", "Failed to Verify mandatory icon for "+data(field)); 
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Field is not marked as mandatory "+screenName);
		}

	}
	/**
	 * @author A-8783
	 * @throws IOException
	 * Desc : Verify Optional field
	 */
	public void verifyOptionalFields(String field) throws IOException
	{
		String optional;
		try{
			String mandatoryFields=getPropertyValue(proppathsst, "txt_mandatoryIcon;xpath");
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
	 *
	 * @param field
	 * @throws IOException
	 * Desc : Verify if field present
	 */
	public void verifyFieldIsPresent(String field) throws IOException
	{
		try{
			String fieldName=getPropertyValue(proppathsst, "lurfs_txt_fieldName;xpath");
			fieldName=fieldName.replace("*", data(field));

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
	 * @author A-8783
	 * Desc - Verify info in airside
	 */
	public void verifyInfoBonded() {

		int size=getSizeOfMobileElement("txt_infoBonded;xpath",proppathsst);
		if(size==1) {
			writeExtent("Pass", "Verified the message 'Information will be stored only for 30 days and will be deleted after 30 days.' on "+screenName);
		}
		else
			writeExtent("Fail", "Could not verify the message 'Information will be stored only for 30 days and will be deleted after 30 days.' on "+screenName);

	}
	/**
	 * @author A-9844
	 * @throws verify token generated is directed to dock
	 */
	public void verifyDirectedToDock(String expText) throws IOException
	{
		String actText=getTextAndroid("txt_dockAssigned;xpath",proppathsst,"text",screenName);
		waitForSync(3);

		if(actText.contains(data(expText)))
		{
			writeExtent("Pass", "Successfully verified token is directed to the dock "+actText +screenName);

		}
		else
		{
			writeExtent("Fail", "Failed to verify token is directed to dock "+screenName);	

		}

	}




	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws IOException
	 * Desc : get Service Point Name-Dock
	 */
	public void getDockServicePointName(String servicePoint) throws IOException
	{
		waitForSync(8);
		String servicepoint=getTextAndroid("txt_dockAssigned;xpath",proppathsst,"Service Point Name",screenName);
		String servicepointName=servicepoint.substring(servicepoint.lastIndexOf(" ")+1);
		System.out.println(servicepointName);
		map.put(servicePoint, servicepointName);
	}
	/**
	 * 
	 * @param expText
	 * @throws IOException
	 */
	public void verifyIDTypeValueNotDefaulted(String expText) throws IOException
	{
		try{
			String locator=getPropertyValue(proppathsst, "lst_idType;xpath");


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
	 * 
	 * Desc - Enter trucking company name and Verify
	 * @param truckCompany
	 * @throws IOException
	 */
	public void enterTruckingCompanyName(String truckCompanyCode,String expText) throws IOException {
		try{
			enterValueInHHT("inbx_truckingCompanyName;xpath",proppathsst,data(truckCompanyCode),"Trucking Company Name",screenName);
			waitForSync(2);
			String locator=getPropertyValue(proppathsst, "drp_truckingCompanyName;xpath");
			locator=locator.replace("*", data(expText));

			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(2);
			writeExtent("Pass", "Trucking company selected as "+data(expText)+" on "+screenName);

			String actText=getTextAndroid("inbx_truckingCompanyName;xpath",proppathsst,"Trucking company",screenName);
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
	 * @param servicePoint
	 * @throws IOException
	 * Desc : get Service Point Name-Counter
	 */
	public void getServicePointName(String servicePoint) throws IOException
	{
		waitForSync(8);
		String servicepoint=getTextAndroid("txt_CounterServicePoint;xpath",proppathsst,"Service Point Name",screenName).trim();
		String servicepointName=servicepoint.substring(servicepoint.lastIndexOf(" ")+1);
		System.out.println(servicepointName);
		map.put(servicePoint, servicepointName);
	}



	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws IOException
	 * Desc : get and verify Service Point Name-Counter
	 */
	public void getAndVerifyServicePointName(String servicePoint) throws IOException
	{
		waitForSync(8);
		try
		{
			String locatorValue=getPropertyValue(proppathsst, "txt_CounterServicePoint;xpath");
			int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
			if(eleSize==1)
			{
				//			String servicepoint=getTextAndroid("txt_CounterServicePoint;xpath",proppathsst,"counter Service Point Name",screenName).trim();
				//			String servicepointName=servicepoint.substring(servicepoint.lastIndexOf(" ")+1);
				//verifying remarks since token assigned to servicepoint remarks
				String servicepoint=getTextAndroid("txt_CounterServicePoint;xpath",proppathsst,"counter Service Point Name",screenName);
				System.out.println(servicepoint);
				System.out.println(servicepoint);
				verifyScreenTextWithExactMatch(screenName, data(servicePoint),servicepoint, "Verification of  service point in SST", "Verification of counter service point in SST") ;
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
	 * @author A-9844
	 * @param mobileNo
	 * @throws IOException
	 * Desc : enter mobile number
	 */
	public void enterMobileNumber(String mobileNo) throws IOException
	{

		enterValueInHHT("inbx_MobileNumber;xpath",proppathsst,data(mobileNo),"Mobile Number ",screenName);
		waitForSync(2);

	}
	/** 
	 * Desc - Enter ID expiry date from calendar
	 * @param date
	 * @param selectedDate
	 * @throws IOException
	 */	public void enterIDExpiryDate(String date, String selectedDate) throws IOException
	 {
		 waitForSync(2);
		 clickActionInHHT("btn_idExpiryCalendar;xpath", proppathsst, "Id expiry date calendar", screenName);

		 String currentDay = createDateFormatWithTimeZone("dd", 0, "DAY", "");
		 System.out.println(currentDay);
		 String currentMonth = createDateFormatWithTimeZone("MMM", 0, "DAY", "").toUpperCase();
		 System.out.println(currentMonth);

		 if (date.equals("future")) {
			 String futureLocator = getPropertyValue(proppathsst, "btn_idExpiryFuture;xpath");
			 futureLocator = futureLocator.replace("*", currentMonth);
			 waitForSync(2);
			 androiddriver.findElement(By.xpath(futureLocator)).click();


			 String dateLocator = getPropertyValue(proppathsst, "btn_calendarDate;xpath");
			 dateLocator = dateLocator.replace("*", selectedDate);
			 androiddriver.findElement(By.xpath(dateLocator)).click();

			 clickActionInHHT("btn_ok;xpath", proppathsst, "Click ok", screenName);

		 }

		 else if(date.equals("past"))
		 {

			 String pastLocator = getPropertyValue(proppathsst, "btn_idExpiryPast;xpath");
			 pastLocator = pastLocator.replace("*", currentMonth);
			 waitForSync(2);
			 androiddriver.findElement(By.xpath(pastLocator)).click();

			 String dateLocator = getPropertyValue(proppathsst, "btn_calendarDate;xpath");
			 dateLocator = dateLocator.replace("*", selectedDate);
			 androiddriver.findElement(By.xpath(dateLocator)).click();

			 clickActionInHHT("btn_ok;xpath", proppathsst, "Click ok", screenName);


		 }

		 else {
			 if (currentDay.equals("25") || currentDay.equals("26") || currentDay.equals("27")
					 || currentDay.equals("28") || currentDay.equals("29") || currentDay.equals("30")
					 || currentDay.equals("31")) {
				 try {
					 String day1Locator = getPropertyValue(proppathsst, "btn_currentDay1;xpath");
					 day1Locator = day1Locator.replace("*", currentDay);
					 androiddriver.findElement(By.xpath(day1Locator)).click();

				 } catch (Exception e) {
					 String day2Locator = getPropertyValue(proppathsst, "btn_currentDay2;xpath");
					 day2Locator = day2Locator.replace("*", currentDay);
					 androiddriver.findElement(By.xpath(day2Locator)).click();

				 } finally {
					 clickActionInHHT("btn_ok;xpath", proppathsst, "Click ok", screenName);					}

			 } else {
				 String dayLocator = getPropertyValue(proppathsst, "btn_calendarDate;xpath");
				 dayLocator = dayLocator.replace("*", currentDay);
				 androiddriver.findElement(By.xpath(dayLocator)).click();

				 clickActionInHHT("btn_ok;xpath", proppathsst, "Click ok", screenName);					}

		 }

	 }

	 /**
	  * @author A-8783
	  * @param date
	  * @throws IOException
	  * Desc : enter driver details
	  */
	 public void enterDriverDetails() throws IOException
	 {
		 waitForSync(2);
		 enterValueInHHT("inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		 enterValueInHHT("inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		 clickActionInHHT("lst_idType;xpath",proppathsst,"Select ID",screenName);
		 waitForSync(3);
		 String idType=getPropertyValue(proppathsst, "btn_idType;xpath");
		 idType=idType.replace("idType", "Passport");

		 for(int i=0;i<2;i++)
		 {
			 androiddriver.findElement(By.xpath(idType)).click();
		 }
		 waitForSync(2);
		 enterValueInHHT("inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		 enterValueInHHT("inbx_city;xpath",proppathsst,data("Origin"),"City",screenName);
		 enterValueInHHT("inbx_vehicleNo;xpath",proppathsst,data("VehicleNo"),"Vehicle No",screenName);
		 scrollMobileDevice("Terms and Data Protection Policy");
		 clickActionInHHT("chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
	 }
	 /**
	  * @author A-8783
	  * Desc - Click back
	  */
	 public void clickBack(String ScreenName) throws AWTException, InterruptedException, IOException
	 {
		 try
		 {
			 String locatorValue=getPropertyValue(proppathsst, "btn_clickBack;xpath");
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
	  * DESC:Verifying the error message on adding a uld wich is already assigned to another flight
	  * @author A-10690
	  * @throws IOException
	  */
	 public void verifyerrormessageWhileAddingUld(String flightno,String date) throws IOException
	 {


		 String exptext="The ULD is assigned to Flight "+data(flightno)+" on "+data(date)+". Cannot Proceed.";
		 String actText=getTextAndroid("txt_erroronaddinguld;xpath",proppathsst,"text",screenName);
		 waitForSync(3);
		 System.out.println(exptext);
		 System.out.println(actText);
		 if(actText.equalsIgnoreCase(exptext))
		 {
			 writeExtent("Pass", "Successfully verified token is directed to counter"+screenName);

		 }
		 else
		 {
			 writeExtent("Fail", "Failed to verify token is directed to counter "+screenName);	

		 }

	 }


	 /**
	  * desc:verify uld is getting added or not
	  * @author A-10690
	  * @throws IOException
	  */
	 public void verifyULD(String uldnum) throws IOException
	 {

		 waitForSync(6);
		 String actText=getTextAndroid("txt_ULD;xpath",proppathsst,"text",screenName);
		 String exptext=data(uldnum);
		 waitForSync(3);

		 if(actText.equals (exptext))
		 {
			 writeExtent("Pass", "Successfully verified uld is displayed"+screenName);



		 }
		 else
		 {
			 writeExtent("Fail", "Failed to verify uld is displayed "+screenName);



		 }



	 }
	 /**
	  * @author A-7271
	  * @throws InterruptedException
	  * @throws AWTException
	  * Desc: Invoking the DropOffPickUpShipments SST Screen
	  */
	 public void invokeDropOffPickUpShipmentsSSTScreen() throws InterruptedException, AWTException {

		 try
		 {

			 clickActionInHHT("btn_menuDropOffPickUp;xpath",proppathsst,"SST menu",screenName);
			 waitForSync(2);
			 writeExtent("Pass", "DropOffPickUpShipments sst screen is invoked successfully");
		 }

		 catch(Exception e)
		 {
			 writeExtent("Fail", "DropOffPickUpShipments sst screen is not invoked successfully");
		 }
	 }
	 /**
	  * @author A-9844
	  * @param date
	  * @param city
	  * @throws IOException
	  * Desc : enter driver details 
	  */
	 public void enterDriverDetailsForBondedSide(String date,String city) throws IOException
	 {

		 enterValueInHHT("inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		 enterValueInHHT("inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		 clickActionInHHT("lst_idType;xpath",proppathsst,"Select ID",screenName);
		 waitForSync(3);
		 String idType=getPropertyValue(proppathsst, "btn_idType;xpath");
		 idType=idType.replace("idType", "ACN");

		 for(int i=0;i<2;i++)
		 {
			 androiddriver.findElement(By.xpath(idType)).click();
		 }
		 waitForSync(2);
		 enterValueInHHT("inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		 enterValueInHHT("inbx_city;xpath",proppathsst,data(city),"City",screenName);
		 enterValueInHHT("inbx_date;xpath",proppathsst,data(date),"DropOff/PickUp Date",screenName);
		 clickActionInHHT("chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);

	 }

	 /**
	  * @author A-7271
	  * @param prefix
	  * @param docNumber
	  * @throws IOException
	  * Desc : Add shipment
	  */
	 public void addShipment(String prefix,String docNumber) throws IOException
	 {
		 waitForSync(2);
		 enterValueInHHT("inbx_shipmentPrefix;xpath",proppathsst,data(prefix),"AWB Prefix",screenName);
		 enterValueInHHT("inbx_documentNumber;xpath",proppathsst,data(docNumber),"AWB Number",screenName);
		 clickActionInHHT("btn_addShipment;xpath",proppathsst,"Add Shipment",screenName);
		 waitForSync(4);

	 }

	 /**
	  * @author A-8783
	  * Desc - Enter Trailer number
	  * @throws IOException
	  */
	 public void enterTrailerNo() throws IOException {
		 enterValueInHHT("inbx_trailerNo1;xpath",proppathsst,data("TrailerNo1"),"Trailer number 1",screenName);
		 enterValueInHHT("inbx_trailerNo2;xpath",proppathsst,data("TrailerNo2"),"Trailer number 2",screenName);
	 }
	 /**
	  * @author A-8783
	  * Desc - Enter ID expiry date
	  * @param endDate
	  * @throws IOException
	  */
	 public void enterIDExpiryDate(String endDate) throws IOException {
		 enterValueInHHT("inbx_idExpiryDate;xpath",proppathsst,data(endDate),"ID Expiry Date",screenName);
	 }
	 /**
	  * @author A-8783
	  * Dec - Verify trailer number fields are present
	  * @throws IOException
	  */
	 public void verifyTrailerNoField() throws IOException {
		 int trailerNo1;
		 int trailerNo2;

		 try {
			 trailerNo1=getSizeOfMobileElement("inbx_trailerNo1;xpath",proppathsst);
			 trailerNo2=getSizeOfMobileElement("inbx_trailerNo2;xpath",proppathsst);
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
	  * @author A-8783
	  * Dec - Verify ID Expiry Date fields are present
	  * @throws IOException
	  */
	 public void verifyIDExpiryDateField() throws IOException {

		 int idExpiryDate;

		 try {
			 idExpiryDate=getSizeOfMobileElement("inbx_idExpiryDate;xpath",proppathsst);

			 if(idExpiryDate==1) {
				 writeExtent("Pass", "Verified that ID expiry field is present");
			 }
			 else {
				 writeExtent("Fail", "ID expiry field is not present in the screen");
			 }
		 }

		 catch (Exception e) {
			 writeExtent("Fail", "Could not verify ID expiry field is  present");

		 }
	 }
	 /**
	  * @author A-9844
	  * @param date
	  * @throws IOException
	  * Desc : enter driver details
	  */
	 public void enterDriverDetailsWithScroll(String date) throws IOException
	 {

		 enterValueInHHT("inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		 enterValueInHHT("inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		 clickActionInHHT("lst_idType;xpath",proppathsst,"Select ID",screenName);
		 waitForSync(3);
		 String idType=getPropertyValue(proppathsst, "btn_idType;xpath");
		 idType=idType.replace("idType", "ACN");

		 for(int i=0;i<2;i++)
		 {
			 androiddriver.findElement(By.xpath(idType)).click();
		 }
		 waitForSync(2);
		 enterValueInHHT("inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		 enterValueInHHT("inbx_city;xpath",proppathsst,data("Origin"),"City",screenName);
		 enterValueInHHT("inbx_date;xpath",proppathsst,data(date),"DropOff/PickUp Date",screenName);
		 enterValueInHHT("inbx_vehicleNo;xpath",proppathsst,data("VehicleNo"),"Vehicle No",screenName);
		 scrollMobileDevice("Terms and Data Protection Policy");
		 clickActionInHHT("chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);

	 }

	 /**
	  * @author A-7271
	  * @throws IOException
	  * Desc : Click proceed
	  */
	 public void clickProceed() throws IOException
	 {
		 clickActionInHHT("btn_proceed;xpath",proppathsst,"Proceed",screenName);
		 waitForSync(3);
	 }

	 /**
	  * @author A-7271
	  * @throws IOException
	  * Desc : Add Uld
	  */
	 public void clickAddULD() throws IOException
	 {
		 clickActionInHHT("btn_addULD;xpath",proppathsst,"Add Uld",screenName);
		 waitForSync(3);
	 }

	 /**
	  * @author A-7271
	  * @param uld
	  * @throws IOException
	  * Desc : Enter uld details
	  */
	 public void enterULDDetails(String uld) throws IOException
	 {
		 enterValueInHHT("inbx_uldNumber;xpath",proppathsst,data(uld),"Uld No",screenName);
		 clickActionInHHT("btn_next;xpath",proppathsst,"Next",screenName);
		 waitForSync(2);
	 }

	 /**
	  * @author A-9844
	  * @throws verify text associated with the checkbox
	  */
	 public void verifyCheckBoxText(String expText) throws IOException
	 {
		 scrollMobileDevice("Information");
		 waitForSync(3);
		 String locatorValue = getPropertyValue(proppathsst, "lbl_firstName;xpath");

		 String actText=getTextAndroid("chkBox_multipleIDs;xpath",proppathsst,"CheckBox text",screenName);

		 if(actText.equals(data(expText)))
		 {
			 writeExtent("Pass", "Successfully verified the checkbox text"+screenName);

		 }
		 else
		 {
			 writeExtent("Fail", "Failed to verify the checkbox text on "+screenName);	

		 }

		 int eleSize = androiddriver.findElements(By.xpath(locatorValue)).size();

		 if(eleSize==0){
			 scrollMobileDevice("First Name");
			 waitForSync(2);
		 }



	 }

	 /**
	  * @author A-9844
	  * @param date
	  * @param city
	  * @throws IOException
	  * Desc : enter driver details 
	  */
	 public void enterDriverDetailsWithScroll(String date,String city) throws IOException
	 {

		 enterValueInHHT("inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		 enterValueInHHT("inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		 clickActionInHHT("lst_idType;xpath",proppathsst,"Select ID",screenName);
		 waitForSync(3);
		 String idType=getPropertyValue(proppathsst, "btn_idType;xpath");
		 idType=idType.replace("idType", "Passport");

		 for(int i=0;i<2;i++)
		 {
			 androiddriver.findElement(By.xpath(idType)).click();
		 }
		 waitForSync(2);
		 enterValueInHHT("inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		 enterValueInHHT("inbx_city;xpath",proppathsst,data(city),"City",screenName);
		 enterValueInHHT("inbx_date;xpath",proppathsst,data(date),"DropOff/PickUp Date",screenName);
		 enterValueInHHT("inbx_vehicleNo;xpath",proppathsst,data("VehicleNo"),"Vehicle No",screenName);
		 scrollMobileDevice("Terms and Data Protection Policy");
		 clickActionInHHT("chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
	 }
	 /**
	  * @author A-9844
	  * @throws verify token generated is directed to counter
	  */
	 public void verifyDirectedToCounter(String expText) throws IOException
	 {
		 String actText=getTextAndroid("txt_documentationCounter;xpath",proppathsst,"text",screenName);
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
	  * @author A-7271
	  * @param prefix
	  * @param documentNum
	  * @throws IOException
	  * Desc : Add shipment details in Uld
	  */
	 public void addShipmentInUld(String prefix,String documentNum) throws IOException
	 {
		 waitForSync(2);
		 enterValueInHHT("inbx_addPrefix;xpath",proppathsst,data(prefix),"AWB Prefix",screenName);
		 enterValueInHHT("inbx_addDocumentNo;xpath",proppathsst,data(documentNum),"AWB Number",screenName);
		 clickActionInHHT("btn_addShipment;xpath",proppathsst,"Add Shipment",screenName);
		 waitForSync(2);

	 }

	 /**
	  * @author A-7271
	  * @throws IOException
	  * Desc : click done
	  */
	 public void clickDone() throws IOException
	 {
		 clickActionInHHT("btn_done;xpath",proppathsst,"Done",screenName);
		 waitForSync(2);
	 }

/* @author A-9844
	  * @param vehicleType
	  * Desc : Select vehicle Type
	  */
	 public void selectVehicletypeOption(String vehicleType)
	 {
		 try
		 {
			 String vehicleTyp=getPropertyValue(proppathsst, "btn_vehicleType;xpath");
			 vehicleTyp=vehicleTyp.replace("vehicleType", data(vehicleType));

			 androiddriver.findElement(By.xpath(vehicleTyp)).click();
			 waitForSync(2);
			 writeExtent("Pass", "Vehicle Type selected as "+data(vehicleType)+" on "+screenName);
		 }

		 catch(Exception e)
		 {
			 writeExtent("Fail", "Could not select vehicle Type on "+screenName);
		 }
	 
	 }


	 /**
	  * @author A-7271
	  * @param date
	  * @throws IOException
	  * Desc : enter driver details
	  */
	 public void enterDriverDetails(String date) throws IOException
	 {


		 enterValueInHHT("inbx_firstName;xpath",proppathsst,data("Name").split(" ")[0],"First Name",screenName);
		 enterValueInHHT("inbx_lastName;xpath",proppathsst,data("Name").split(" ")[1],"Last Name",screenName);
		 clickActionInHHT("lst_idType;xpath",proppathsst,"Select ID",screenName);
		 waitForSync(2);
		 String idType=getPropertyValue(proppathsst, "btn_idType;xpath");
		 idType=idType.replace("idType", "ACN");

		 for(int i=0;i<2;i++)
		 {
			 androiddriver.findElement(By.xpath(idType)).click();
		 }
		 waitForSync(2);
		 enterValueInHHT("inbx_idNumber;xpath",proppathsst,data("ID No"),"ID Number",screenName);
		 enterValueInHHT("inbx_city;xpath",proppathsst,data("Origin"),"City",screenName);
		 enterValueInHHT("inbx_date;xpath",proppathsst,data(date),"DropOff/PickUp Date",screenName);
		 enterValueInHHT("inbx_vehicleNo;xpath",proppathsst,data("VehicleNo"),"Vehicle No",screenName);
		 scrollMobileDevice("Terms and Data Protection Policy");
		 clickActionInHHT("chkBox_disclaimer;xpath",proppathsst,"Select Disclaimer",screenName);
	 }

	 /**
	  * @author A-7271
	  * @param vehicleType
	  * Desc : Select vehicle Type
	  */
	 public void selectVehicletype(String vehicleType)
	 {
		 vehicleType="Side Loader (AMS)";
		 try
		 {
			 String vehicleTyp=getPropertyValue(proppathsst, "btn_vehicleType;xpath");
			 vehicleTyp=vehicleTyp.replace("vehicleType", vehicleType);

			 androiddriver.findElement(By.xpath(vehicleTyp)).click();
			 waitForSync(2);
			 writeExtent("Pass", "Vehicle Type selected as "+vehicleType+" on "+screenName);
		 }

		 catch(Exception e)
		 {
			 writeExtent("Fail", "Could not select vehicle Type on "+screenName);
		 }
	 
	 }

	 /**
	  * @author A-7271
	  * @throws IOException
	  * Desc : verify and store token
	  */
	 public void verifyTokenGeneration(String tokenId) throws IOException
	 {
		 waitForSync(10);

		 String tokenValue=getTextAndroid("inbx_tokenNum;xpath",proppathsst,"Token Id",screenName);

		 if(tokenValue.equals(""))
		 {
			 writeExtent("Fail", "Token is not generated  on "+screenName);
			 Assert.assertFalse(true, "Token is not generated  on "+screenName);

		 }
		 else
		 {
			 writeExtent("Pass", "Token is generated with value "+tokenValue+" on "+screenName);	

		 }

		 map.put(tokenId, tokenValue);

	 }

}