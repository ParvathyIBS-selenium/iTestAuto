package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CreateVisitDeclaration_TGC013 extends CustomFunctions
{

	public CreateVisitDeclaration_TGC013(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="CreateVisitDeclaration_TGC013";
	public String screenName="CreateVisitDeclaration_TGC013";
	public String sheetName2 = "CaptureAWB_OPR026";
    
	
	/**
	 * @author A-7271
	 * @param tokenNo
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc : enter token No
	 */
	public void enterTokenNo(String tokenNo) throws InterruptedException, AWTException {
		waitTillScreenload(sheetName, "inbx_tokenNumber;name","Token No", screenName);
		enterValueInTextbox(sheetName, "inbx_tokenNumber;name", data(tokenNo), "Token No", screenName);
		
			
	}
	
	/**
	 * @author A-8783
	 * Desc - Verify status from tool tip
	 * @param awbNo
	 * @param checktype
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCHIconAndVerifyTooltip(String awbNo,String checktype,String expText) throws InterruptedException, IOException
	{
		try{
			
			waitForSync(2);		
			String locator = xls_Read.getCellValue(sheetName, "div_awbChIcon;xpath");
			locator=locator.replace("*", data(awbNo));
			moveScrollBar(driver.findElement(By.xpath(locator)));			
			System.out.println(locator);
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);
			
			driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_statuscheck;xpath").replace("*", checktype))).click();
			waitForSync(1);
			String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_toolTip;xpath"))).getText();
			waitForSync(1);
			verifyScreenTextWithExactMatch(sheetName, expText, actText, "Verify "+checktype+" on ",screenName);
			 driver.findElement(By.xpath(locator)).click();
				
				waitForSync(1);
		}
			
			catch(Exception e){
				
				writeExtent("Fail","Failed to verify the Status checks on "+screenName);	
				
		}
		
			

		
		
	}
	/**
	 * @author A-9844
	 * @Desc verify uld number is present
	 * @param awbNo
	 * @param uldNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyULDNumber(String awbNo,String uldNo) throws InterruptedException, IOException
	{
		try{

			String locator = xls_Read.getCellValue(sheetName, "txt_uldNumber;xpath");
			locator=locator.replace("AWBNo", data(awbNo));
			locator=locator.replace("uldno", data(uldNo));
			moveScrollBar(driver.findElement(By.xpath(locator)));		

			int size=driver.findElements(By.xpath(locator)).size();

			if(size==1){
				writeExtent("Pass","Verified Uldnumber got displayed on "+screenName);	
			}

			else{
				writeExtent("Pass","Uldnumber is not displayed on "+screenName);	
			}

		}

		catch(Exception e){

			writeExtent("Fail","Failed to verify the uld number checks on "+screenName);	

		}
	}
	/**
	 * Desc : Clicking edit
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickEdit() throws InterruptedException, IOException
	{
		
	    //click Edit button
	    clickWebElement(sheetName, "btn_edit;xpath", "Edit button", screenName);
	    waitForSync(2);
		
	}

	/**@Desc : captureULDinfo
	 * @author A-9844
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureULDinfo(String uldnumber) throws InterruptedException, IOException 
	{

		waitTillScreenload(sheetName, "txt_uldNumberField;xpath", "ULD Number", screenName);
		enterValueInTextbox(sheetName, "txt_uldNumberField;xpath", data(uldnumber), "ULD Number", screenName);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add Button", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_saveULD;xpath", "Save Button", screenName);
		waitForSync(2);
		
	}
	
	/**
	 * @Desc :Selecting departments
	 * @author A-9175
	 * @param department
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectDepartments(String department) throws InterruptedException, IOException {

		String dep;
		if(data(department).contains("Acceptance") | data(department).contains("Return to Shipper")) {
			dep="E_ALO>Top";
		}
		else {
			dep="IMP>Top";
		}
		clickWebElementByWebDriver(sheetName, "dpdwn_department;xpath", "Click on department dropdown", screenName);
		try {
			String locatorValue1=xls_Read.getCellValue(sheetName, "btn_selectDepartment;xpath");
			locatorValue1=locatorValue1.replace("dep", dep);
			driver.findElement(By.xpath(locatorValue1)).click();
			writeExtent("Pass","Selected the department from"+screenName);
		}
		catch(Exception e) {
			writeExtent("Info", " Selected department" + dep +"is not found in the drop down" + screenName);
		}

		waitForSync(2);
		try {
			String locatorValue=xls_Read.getCellValue(sheetName, "chk_bupAcceptanceDept;xpath");
			locatorValue=locatorValue.replace("*", data(department));
			driver.findElement(By.xpath(locatorValue)).click();
			waitForSync(2);
			writeExtent("Pass","Selected the department "+data(department)+" from"+screenName);
		}catch(Exception e) {
			writeExtent("Fail", "Could not select the department "+data(department)+" from"+ screenName);
		}

		finally {
			clickWebElementByWebDriver(sheetName, "dpdwn_department;xpath", "Click on department dropdown", screenName);
		}
	}
	
	/**
	 * @Description Verifying  the flight details added in TGC013  
	 * @author A-10690
	 * @param Flight no
	 * @param Flight date
	 * @throws AWTException
	 */
	public void verifyFlightDetails(String flghtNo,String FlightDate) throws InterruptedException, AWTException {

		
		String locator = xls_Read.getCellValue(sheetName, "txt_FlightDetails;xpath");
		locator=locator.replace("*",FlightDate);
		String text=driver.findElement(By.xpath(locator)).getText();
		if(text.contains(data(flghtNo)))
		{
			writeExtent("Pass", "verified the flight"+data(flghtNo)+screenName);
		}
		else 
		{
			writeExtent("Fail","Failed to verify the flight"+data(flghtNo)+screenName) ;

		}

	
	waitForSync(2);
}

	/**
	 * @Desc :capture Nature Of Shipment Details
	 * @author A-9175
	 * @param shipmentType
	 * @throws InterruptedException
 */
	public void captureNatureOfShipmentDetails(String shipmentType) throws InterruptedException {
		clickWebElementByWebDriver(sheetName, "lst_ShipmentType;xpath", "Shipment Type", screenName);
		waitForSync(2);
		try {
			String locator=xls_Read.getCellValue(sheetName, "lst_ShipmentTypeval;xpath");
			if(shipmentType=="0")
			{
				locator=locator.replace("*", "DGR");
			}
			else if(shipmentType=="1")
			{
				locator=locator.replace("*", "Standard");

			}
			else if(shipmentType=="2")
			{
				locator=locator.replace("*", "BUP");
			}
			
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", " shipmentType Type Selected on "+ screenName + " Page");

		} catch (Exception e) {
			writeExtent("Fail", "shipmentType Type Not Selected on "+ screenName + " Page");
		}

	}
	/**@Desc :capture Product Priority Details
	 * @author A-9175
	 * @param shipmentType
	 * @throws InterruptedException
	 */
	public void captureProductPriorityDetails(String prio) throws InterruptedException {
		clickWebElementByWebDriver(sheetName, "lst_ProductPrio;xpath", "Product Priority", screenName);
		waitForSync(2);
		try {

			String locator=xls_Read.getCellValue(sheetName, "lst_ProductPrioval;xpath");
			if(prio=="0")
			{
				locator=locator.replace("*", "High");
			}
			else if(prio=="1")
			{
				locator=locator.replace("*", "Low");

			}
			else if(prio=="2")
			{
				locator=locator.replace("*", "Medium");
			}

			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", " Product Priority Selected on "+ screenName + " Page");

		} catch (Exception e) {
			writeExtent("Fail", "Product Priority Not Selected on "+ screenName + " Page");
		}

	}
	
	/**
	 * @Desc : enterIdInfo
	 * @author A-9175
	 * @param id
	 * @throws InterruptedException
	 */
	public void enterIdInfo(String id) throws InterruptedException {
		enterValueInTextbox(sheetName, "txt_IdNumber;name", data(id), "ID Number", screenName);
	}

	/**@Desc : captureULDinfo
	 * @author A-9175
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String captureULDinfo() throws InterruptedException, IOException 
	{
		String uldNo = create_uld_number("UldType", "carrierCode");
		waitTillScreenload(sheetName, "txt_ULDnumber;xpath", "ULD Number", screenName);
		enterValueInTextbox(sheetName, "txt_ULDnumber;xpath", uldNo, "ULD Number", screenName);
		clickWebElement(sheetName, "btn_AddToList;xpath", "List Button", screenName);
		waitForSync(2);
		String alertTxt=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_alertText;xpath"))).getText();
		System.out.println("Alert displayed as : "+alertTxt);
		try {
			if (driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).isDisplayed()) {
				driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).click();
				waitForSync(2);
				writeExtent("Pass", " Sucessfully found Alert as on "+alertTxt+ screenName + " Page");
			}
		} catch (Exception e)
		{
			writeExtent("Fail", " Couldnt found any Alert on add to list Button as on "+ screenName + " Page");
		}
		return uldNo;
	}
	/**
	 * @Desc : addAWBToULD
	 * @author A-9175
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addAWBToULD(String awbNo, String ShipmentPrefix) throws InterruptedException, IOException 
	{
		enterValueInTextbox(sheetName, "inbx_AWBpre;xpath", data(ShipmentPrefix), "Shipment Prefix",screenName);
		enterValueInTextbox(sheetName, "inbx_AWBno;xpath", data(awbNo), "AWB No", screenName);
		performKeyActions(sheetName, "inbx_AWBnumber;name", "TAB", "AWB Number", screenName);
		clickWebElement(sheetName, "btn_addAWB;xpath", "Add Button", screenName);
		waitTillScreenload(sheetName, "txt_awbDataadded;xpath","AWB Added Data", screenName);
		clickWebElement(sheetName, "btn_saveData;xpath", "Sve Button", screenName);
		waitForSync(3);
	}

	/**
	 * @Desc getTokenNumber
	 * @author A-9175
	 * @return
	 */
	public String getTokenNumber() 
	{
		String token = "";
		try 
		{
			token=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_tokenNum;xpath"))).getText();
			writeExtent("Pass", " Sucessfully found Token Number as "+token+ screenName + " Page");
			System.out.println("Token Number generated is :"+token);
		} catch (Exception e) {
			writeExtent("Fail", " Couldnt found  Token Number generated on "+ screenName + " Page");
		}
		return token;
	}






/**
	 * @author A-9847
	 * @Desc To click on CH Icon and verify the given Status Check(checktype as RTI Status and expText as OK)
	 * @param awbNo
	 * @param checktype
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCHIconAndVerifyStatus(String awbNo,String checktype,String expText) throws InterruptedException, IOException
	{
		try{
			
			waitForSync(2);		
			String locator = xls_Read.getCellValue(sheetName, "div_awbChIcon;xpath");
			locator=locator.replace("*", data(awbNo));
			moveScrollBar(driver.findElement(By.xpath(locator)));		
			System.out.println(locator);
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);
			
			String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_statuscheck;xpath").replace("*", checktype))).getText();
			System.out.println(actText);
			verifyScreenTextWithExactMatch(sheetName, expText, actText, "Verify "+checktype+" on ",screenName);
			clickWebElement(sheetName, "txt_tokenNo;xpath", "Token number", screenName);
}
		
		catch(Exception e){
			
			writeExtent("Fail","Failed to verify the Status checks on "+screenName);	
			
	}
	

		
	}
	/**
	 * @author A-9844
	 * Desc-Save truck details and generate token
	 * @throws InterruptedException
	 */
	public void saveAsDraft() throws InterruptedException {
		clickWebElementByWebDriver(sheetName, "btn_saveasdraft;xpath", " Save As Draft button ",screenName);
		waitForSync(2);
	}
	
	/**
	 * @author A-9847
	 * @Desc To check the Status checks on OPR026 Status pop-up
	 * @param status
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void checkStatusOPR026(String status) throws InterruptedException, IOException{
		String  ScreenName="Capture_AWB Screen";
	try {	
		
		waitForSync(8);
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(1);
		String locator=xls_Read.getCellValue(sheetName2, "img_securityStatus;xpath");
		locator=locator.replace("*", data(status));
		waitForSync(1);
		if(driver.findElement(By.xpath(locator)).isDisplayed()) {
			writeExtent("Pass", "Verified the status as " + data(status) + " on " + ScreenName);
			
		}
		switchToFrame("default");
		switchToFrame("contentFrame", "TGC013");
		}
		
		catch(Exception e) {
			writeExtent("Fail", "Could not verify the status as " + data(status) + " on " + ScreenName);
		}
	
	}

	
	/**
	 * @author A-9847
	 * @Desc To click on More options three dots against the given AWB
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickMoreOption(String awbNo) throws InterruptedException, IOException
	{
		try{
		String locatorMore = xls_Read.getCellValue(sheetName, "btn_mreOptions;xpath");
		locatorMore=locatorMore.replace("*", data(awbNo));
		driver.findElement(By.xpath(locatorMore)).click();
		waitForSync(3);
		}
		catch(Exception e) {
			writeExtent("Fail", "Could not click on More options on " + screenName);
		}

		
	}
	
	
	/**
	 * @author A-9847
	 * @Desc To verify the token Details
	 * @throws InterruptedException
	 */
	public void verifyTokenDetails() throws InterruptedException{
		
		try{

			String driver = getElementText(sheetName, "div_driverDetails;xpath", "Driver Details", screenName);
			verifyScreenText(screenName,data("Name").toUpperCase(), driver,  "Driver Details", "Driver details verification");

			String idType = getElementText(sheetName, "div_idType;xpath", "ID Type", screenName);
			verifyScreenText(screenName,data("IDType"), idType,  "ID Type", "ID Type verification");

			String idDetails = getElementText(sheetName, "div_idDetails;xpath", "ID Details", screenName);
			verifyScreenText(screenName, data("ID No"), idDetails,  "ID Details", "ID Details verification");	

			String issueState = getElementText(sheetName, "div_state;xpath", "Issuing State", screenName);
			verifyScreenText(screenName, data("Origin"), issueState,  "Issuing State", "Issuing State verification");

			String idDate = getElementText(sheetName, "div_idDate;xpath", "ID Date", screenName);
			verifyScreenText(screenName, data("StartDate"), idDate,  "ID Date", "ID Date verification");

			String truckType = getElementText(sheetName, "div_type;xpath", "Truck Type", screenName);
			verifyScreenText(screenName, data("VehicleType"), truckType,  "Truck Type", "Truck Type verification");

			String vehNo = getElementText(sheetName, "div_vehicleNo;xpath", "Vehicle Number", screenName);
			verifyScreenText(screenName, data("VehicleNo"), vehNo,  "Vehicle Number", "Vehicle Number verification");


		}
		catch(Exception e) {
			writeExtent("Fail", "Could not verify the token details on " + screenName);
		}

		
	}
	/**
	 * @author A-8783 Desc - Click CH icon and verify the colour of the status
	 *         icon
	 * @param awbNo
	 * @param checktype
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCHIconAndVerifyColour(String awbNo, String checktype, String expText)
			throws InterruptedException, IOException {
		String expClr = "";
		try {
			if (expText.equals("Green")) {
				expClr = "rgba(88, 175, 46, 1)";
			} else if (expText.equals("Red")) {
				expClr = "rgba(219, 68, 61, 1)";
			}
			waitForSync(2);
			String locator = xls_Read.getCellValue(sheetName, "div_awbChIcon;xpath");
			locator = locator.replace("*", data(awbNo));
			moveScrollBar(driver.findElement(By.xpath(locator)));
			System.out.println(locator);
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);

			String actClr = driver
					.findElement(
							By.xpath(xls_Read.getCellValue(sheetName, "txt_statuscheck;xpath").replace("*", checktype)))
					.getCssValue("color");
			System.out.println(actClr);
			verifyScreenTextWithExactMatch(sheetName, expClr, actClr, "Verify colour as " + expText + " on ",
					screenName);
			clickWebElement(sheetName, "txt_tokenNo;xpath", "Token number", screenName);
		}

		catch (Exception e) {

			writeExtent("Fail", "Failed to verify the Status colour as " + expText + " on " + screenName);

		}
	

		
	}

	/**
	 * @author A-8783
	 * Desc - Verify trailer numbers
	 * @param expTrailerNo1
	 * @param expTrailerNo2
	 */
	public void verifyTrailerNos(String expTrailerNo1, String expTrailerNo2) {
		 String actTrailerNo1=driver.findElement(By.name(xls_Read.getCellValue(sheetName, "txt_trailer1;name"))).getAttribute("value");
		 verifyScreenText(sheetName, data(expTrailerNo1), actTrailerNo1, "Trailer number 1",
					screenName);
		 String actTrailerNo2=driver.findElement(By.name(xls_Read.getCellValue(sheetName, "txt_trailer2;name"))).getAttribute("value");
		 verifyScreenText(sheetName, data(expTrailerNo2), actTrailerNo2, "Trailer number 2",
					screenName);
	}
	/**
	 * @author A-8783
	 * Desc - Verify the field is optional
	 * @param field
	 */
	public void verifyOptionalField(String field) {
		int size=0;
		try {
		String locator= xls_Read.getCellValue(sheetName, "lbl_mandatoryIcon;xpath");
		locator=locator.replace("*", data(field));
		size=driver.findElements(By.xpath(locator)).size();
		if(size==0) {
			writeExtent("Pass", "The field " + data(field)+ "is an optional field.");
		}
		else
			writeExtent("Fail", "The field " + data(field)+ "is a mandatory field.");
	}
	catch(Exception e) {
		writeExtent("Fail","Failed to verify if the field is optional");
	}
		
	}
	/**
	 * @Description Verifying  the SCCs added in the shipment  
	 * @author A-10690
	 * @param Shipment desc
	 * @param SccValue
	 * @throws AWTException
	 */
	public void verifySCCsAddedInAWB(String shipDesc,String[] sccValue) throws InterruptedException, AWTException {


		for(int i=0;i<sccValue.length;i++)
		{
			String locator = xls_Read.getCellValue(sheetName, "txt_outersccs;xpath");
			locator=locator.replace("*",data(shipDesc));
			String actscc=locator.replace("SCC", sccValue[i]);
			if(driver.findElements(By.xpath(actscc)).size()==1)
			{
				writeExtent("Pass", "verified the scc"+sccValue[i]+screenName);

			}
			else 
			{
				String scclist = xls_Read.getCellValue(sheetName, "btn_sccslist;xpath");
				scclist = scclist.replace("*", data(shipDesc));
				moveScrollBar(driver.findElement(By.xpath(scclist)));
				driver.findElement(By.xpath(scclist)).click();

				waitForSync(1);
				String actscc2 = xls_Read.getCellValue(sheetName, "txt_innerscc;xpath");
				actscc2=actscc2.replace("SCC", sccValue[i]);
				if(driver.findElements(By.xpath(actscc2)).size()==1)
				{
					writeExtent("Pass", "verified the scc"+sccValue[i]+screenName);
					driver.findElement(By.xpath(scclist)).click();

				}
				else
				{
					writeExtent("Fail", "verify the scc"+sccValue[i]+screenName);
					driver.findElement(By.xpath(scclist)).click();
				}

			}

		}
		waitForSync(2);

	}


	/**
	 * @author A-8783
	 * Desc- click truck and driver registration button and verify the popup
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickTruckDriverRegAndVerifyPopup() throws InterruptedException, IOException {

		String expText="Do you want to create new Truck And Driver details?";
		
		clickWebElement(sheetName, "btn_truckAndDriverReg;xpath", "Truck and Driver Registration", screenName);
		waitForSync(1);
		getTextAndVerify(sheetName, "txt_truckAndDriverPopup;xpath", "Truck and Driver Registration Popup", screenName, "Truck and Driver Registration Popup", expText, "equals");
		clickWebElement(sheetName, "btn_ok;xpath", " OK Button ",screenName);
	}

	/**
	 * @author A-8783
	 * Desc - Verify ID expiry date field
	 * @param expiryDate
	 */
	public void verifyExpiryDate(String expiryDate) {
		String actexpiryDate=driver.findElement(By.name(xls_Read.getCellValue(sheetName, "inbx_IdexpiryDate;name"))).getAttribute("value");
		 verifyScreenText(sheetName, data(expiryDate), actexpiryDate, "ID Expiry date",
					screenName);
	}

	/**
	 * @author A-9844
	 * @throws Exception
	 * Desc : enter execution Date
	 */
	public void enterExecutionDate() throws Exception
	{
		String execDate=getAttributeWebElement(sheetName2, "inbx_executionDate;id",
				"Execution Date", "defaultValue",screenName).toUpperCase();
		System.out.println(execDate);
		
		if(!execDate.equals(""))
		{

			if(data("Origin").equals("IAD"))
			{
				System.out.println(currentDateUS().toUpperCase());
				if(!execDate.equals(currentDateUS().toUpperCase()))
				{
					enterValueInTextbox(sheetName2, "inbx_executionDate;id", currentDateUS().toUpperCase(), "ExecutionDate", screenName);
					waitForSync(1);
				}
			}
			else
			{
				if(!execDate.equalsIgnoreCase(createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam")))
				{
					enterValueInTextbox(sheetName2, "inbx_executionDate;id", createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam"), "ExecutionDate", screenName);
					waitForSync(1);
				}
			}
		}
	}

	

	/**
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verifyDepartment field in TGC013 screen
	 */
	
	public void verifyDepartment(String department,String expText) throws InterruptedException, IOException
	{
		try {
			String locator=xls_Read.getCellValue(sheetName,"txt_department;xpath");
			locator=locator.replace("*", department);
				String actText=driver.findElement(By.xpath(locator)).getText();
			 System.out.println(actText);
			if(actText.equals(expText))
			{
				writeExtent("Pass","Verified "+department+" as "+expText+" on "+screenName);
			}
				else			
					writeExtent("Fail","Could not verify "+department+" details on "+screenName);
			
	}catch(Exception e)
		{
		writeExtent("Fail","Failed to verify department "+screenName);
		}

	
}

	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click List
	 */
	public void clickList() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_list;xpath", " List Button ",screenName);
		waitForSync(3);
	}
	
	/**
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click drop down button
	 */
	public void clickDropdown() throws InterruptedException, IOException
	{
		
		clickWebElement(sheetName, "btn_dropdown;xpath", "dropdown button",screenName);
		waitForSync(3);
	}
		/**
	 * @author A-8783
	 * Desc- click truck and driver registration button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickTruckDriverReg() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_truckAndDriverReg;xpath", "Truck and Driver Registration", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "btn_ok;xpath", " OK Button ",screenName);
	}
		/**
	 * @author A-8783
	 * Desc-Verify the fields Trailer 1 and Trailer 2 
	 */
	public void verifyTrailerFields() {
		try {
		 String locatorValue1=xls_Read.getCellValue(sheetName, "txt_trailer1;name");
		driver.findElement(By.name(locatorValue1)).isDisplayed();
		 writeExtent("Pass","The field Trailer 1 is displayed in"+screenName);
		
		}catch (Exception e) {
			writeExtent("Fail","The field Trailer 1 is not displayed in"+screenName);
		}
		
		try {
			String locatorValue2=xls_Read.getCellValue(sheetName, "txt_trailer2;name");
			driver.findElement(By.name(locatorValue2)).isDisplayed();
			 writeExtent("Pass","The field Trailer 2 is displayed in"+screenName);
		}catch (Exception e) {
			writeExtent("Fail","The field Trailer 2 is not displayed in"+screenName);
		}
	}	
		/**
	 * @author A-8783
	 * Desc- Verify the field id expiry date
	 */
	public void verifyIdExpiryDateField() {
		try {
			 String locatorValue=xls_Read.getCellValue(sheetName, "inbx_IdexpiryDate;name");
			driver.findElement(By.name(locatorValue)).isDisplayed();
			 writeExtent("Pass","The field id expiry date is displayed in"+screenName);
			
			}catch (Exception e) {
				writeExtent("Fail","The field id expiry date is not displayed in"+screenName);
			}
	}
	/**
	 * @author A-9847
	 * @Desc To enter the Registration Number
	 * @param regno
	 * @throws InterruptedException
	 */
	public void enterRegistrationNumber(String regno) throws InterruptedException{
		
		enterValueInTextbox(sheetName, "inbx_registrationNumber;xpath", data(regno), "Registration Number", screenName);
	}
		/**
	 * @author A-8783
	 * Desc -  Select purpose of visit
	 * @param purposeOfVisit
	 * @throws InterruptedException
	 * @throws IOException
	 */
		public void selectPurposeOfVisit(String purposeOfVisit) throws InterruptedException, IOException {
			waitForSync(2);
			clickWebElementByWebDriver(sheetName, "lst_purposeOfVisit;xpath", "List Purpose Of Visit", screenName);
			waitForSync(1);
			try {
				String locator=xls_Read.getCellValue(sheetName, "lst_purposeOfVisitIndex;xpath");
				locator=locator.replace("POVIndex", purposeOfVisit);
				driver.findElement(By.xpath(locator)).click();
				writeExtent("Pass", " Purpose Of Visit Selected "+ screenName + " Page");
			} catch (Exception e) {
				writeExtent("Fail", " Purpose Of Visit Not Selected "+ screenName + " Page");
			}
		}

		
			/**
		 * @author A-8783
		 * Desc- select department
		 * @param department
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void selectDepartment(String department) throws InterruptedException, IOException {
		
			String dep;
			if(data(department).contains("Acceptance") | data(department).contains("Return to Shipper")) {
				dep="EXP>Top";
			}
			else {
				dep="IMP>Top";
			}
			clickWebElementByWebDriver(sheetName, "dpdwn_department;xpath", "Click on department dropdown", screenName);
			try {
				String locatorValue1=xls_Read.getCellValue(sheetName, "btn_selectDepartment;xpath");
				locatorValue1=locatorValue1.replace("dep", dep);
				driver.findElement(By.xpath(locatorValue1)).click();
				writeExtent("Pass","Selected the department from"+screenName);
			}
			catch(Exception e) {
				writeExtent("Info", " Selected department" + dep +"is not found in the drop down" + screenName);
			}

			waitForSync(2);
			try {
				String locatorValue=xls_Read.getCellValue(sheetName, "chkBox_department;xpath");
				locatorValue=locatorValue.replace("*", data(department));
				driver.findElement(By.xpath(locatorValue)).click();
				waitForSync(2);
				writeExtent("Pass","Selected the department "+data(department)+" from"+screenName);
			}catch(Exception e) {
				writeExtent("Fail", "Could not select the department "+data(department)+" from"+ screenName);
			}


		}
		

		/**
		 * @author A-8783
		 * Desc- Enter driver details
		 * @param firstName
		 * @param lastName
		 * @throws InterruptedException
		 */
		public void captureDriverDetails(String firstName, String lastName) throws InterruptedException {
			enterValueInTextbox(sheetName, "inbx_driverName;name", data(firstName), "First Name", screenName);
			enterValueInTextbox(sheetName, "inbx_driverLastName;name", data(lastName), "Last Name", screenName);
		}
		/**
		 * @author A-8783
		 * Desc- capture id details
		 * @throws InterruptedException 
		 */
		public void captureIdType(String idType) throws InterruptedException {
			clickWebElementByWebDriver(sheetName, "lst_IdType;xpath", "List ID Type", screenName);
			waitForSync(2);
			try {
				String locator=xls_Read.getCellValue(sheetName, "lst_IdTypeIndex;xpath");
				locator=locator.replace("IdIndex", idType);
				driver.findElement(By.xpath(locator)).click();
				writeExtent("Pass", " ID Type Selected "+ screenName + " Page");
			} catch (Exception e) {
				writeExtent("Fail", "ID Typet Not Selected "+ screenName + " Page");
			}
		}
			/**
		 * @author A-8783
		 * Desc- Capture id expiry date
		 * @param expiryDate
		 * @throws InterruptedException
		 * @throws AWTException 
		 */
		public void captureIdExpiryDate(String expiryDate) throws InterruptedException, AWTException {
			clearText(sheetName, "inbx_IdexpiryDate;name", "Expiry date",screenName);
			enterValueInTextbox(sheetName, "inbx_IdexpiryDate;name", data(expiryDate), "Expiry date", screenName);
			performKeyActions(sheetName, "inbx_IdexpiryDate;name", "TAB", "Expiry Date", screenName);
			waitForSync(2);
		}

		/**
		 * @author A-8783
		 * Desc - Verify error generated when expired id date is given
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void verifyExpiryError() throws InterruptedException, IOException {
			
	
			String errorText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_errorMsg;xpath"))).getText();
			verifyScreenText(sheetName, "The ID is expired. Please capture details of a valid ID", errorText, "Error message",
					screenName);
			clickWebElement(sheetName, "btn_ok;xpath", " List Button ",screenName);
		}
			/**
		 * @author A-8783
		 * Desc - Enter truck details
		 * @param vehicleNo
		 * @throws InterruptedException
		 */
		public void captureTruckDetails(String vehicleType,String vehicleNo) throws InterruptedException {
			clickWebElementByWebDriver(sheetName, "lst_truckType;xpath", "List Truck Type", screenName);
			waitForSync(2);
			try {
				String locator=xls_Read.getCellValue(sheetName, "lst_truckTypeIndex;xpath");
				if(vehicleType=="0")
				{
					locator=locator.replace("*", "All");
				}
				else if(vehicleType=="1")
				{
					locator=locator.replace("*", "Forklift/Internal");

				}
				else if(vehicleType=="2")
				{
					locator=locator.replace("*", "Long");
				}
				else if(vehicleType=="3")
				{
					locator=locator.replace("*", "Short");

				}

				else if(vehicleType=="4")
				{
					locator=locator.replace("*", "Truck");

				}

				driver.findElement(By.xpath(locator)).click();
				writeExtent("Pass", " Truck Type Selected on "+ screenName + " Page");

			} catch (Exception e) {
				writeExtent("Fail", "Truck Type Not Selected on "+ screenName + " Page");
			}

			enterValueInTextbox(sheetName, "inbx_vehicleNo;name", data(vehicleNo), "Vehicle number", screenName);


		}


			/** @author A-8783
		  * Desc-Update trailer no 1 and 2
		 * @param trailerNo1
		 * @param trailerNo2
		 * @throws InterruptedException
		 */
		public void updateTrailerNos(String trailerNo1, String trailerNo2) throws InterruptedException {
			clearText(sheetName, "txt_trailer1;name", "Trailer Number 1",screenName);
			waitForSync(1);
			enterValueInTextbox(sheetName, "txt_trailer1;name", data(trailerNo1), "Trailer Number 1", screenName);
			clearText(sheetName, "txt_trailer2;name", "Trailer Number 2",screenName);
			waitForSync(1);
			enterValueInTextbox(sheetName, "txt_trailer2;name", data(trailerNo2), "Trailer Number 2", screenName);
		}
		/** 
		* @author A-8783
		* @throws InterruptedException
		* @throws IOException
		* Desc : click add button 
		*/
		public void clickAdd() throws InterruptedException, IOException
		{
		clickWebElement(sheetName, "btn_add;xpath", " Add button ",screenName);
		waitForSync(1);

		}

			/**
		 * @author A-8783
		 * Desc- click edit truck details icon
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void editTruckDetails() throws InterruptedException, IOException
		{
			clickWebElementByWebDriver(sheetName, "btn_editTruckDetails;xpath", " Edit truck details Button ",screenName);
			waitForSync(1);
		}

			/**
		 * @author A-8783
		 * Desc- Add AWB to list
		 * @param awbNo
		 * @param ShipmentPrefix
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void listAWB(String awbNo, String ShipmentPrefix) throws InterruptedException, IOException {


			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(ShipmentPrefix), "Shipment Prefix",screenName);
			enterValueInTextbox(sheetName, "inbx_AWBnumber;name", data(awbNo), "AWB No", screenName);
			clickWebElement(sheetName, "btn_AddToList;xpath", "List Button", screenName);
			waitForSync(2);

		}
		
			/**
		 * @author A-8783
		 * Desc-Save truck details and generate token
		 * @throws InterruptedException
		 */
		public void saveDetails() throws InterruptedException {
			clickWebElementByWebDriver(sheetName, "btn_saveDetails;xpath", " Save button ",screenName);
			waitForSync(2);
			
			String errorMessageLocator=xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMsg;xpath");
			int errorMsgSize=driver.findElements(By.xpath(errorMessageLocator)).size();
	        System.out.println(errorMsgSize);
			
			// Verify error message
			if(errorMsgSize==1)
			{
				
			    String errorText=driver.findElement(By.xpath(errorMessageLocator)).getText();
				writeExtent("Fail","Error while saving token details on TGC013 . Error message is "+errorText);
			}
		}

		/**
		 * @author A-8783
		 * Desc - Verify token generated by checking the green tick
		 * @throws InterruptedException
		 */
		public void verifyTokenGenerated() throws InterruptedException {
			verifyElementDisplayed(sheetName, "img_token;xpath", " Token generated  ", screenName, " Token generated image ");
}


	/**
	 * @Description : Close from OPR026 screen
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void closeFromOPR026() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName2, "btn_CloseLOV;name", "Capture AWB Close Button", screenName);
		waitForSync(2);
	}
	/**
	    * @author A-9847
	    * @Desc To handle checksheets with multiple formats
	    * @param chkSheetRequired
	    */
	public void captureChecksheetWithMultiFormats(boolean chkSheetRequired)
    

	{
		boolean checkSheetExists=true;
		try
		{
			clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",screenName);//1
			waitForSync(3);

			driver.switchTo().frame("popupContainerFrame");
			waitTillScreenload("Generic_Elements", "btn_save;xpath", "OK Button", screenName);

			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			List <WebElement> questions2=driver.findElements(By.xpath("//input[@title='Date']"));
			List <WebElement> questions3=driver.findElements(By.xpath("//input[@title='Time']"));
			List <WebElement> questions4=driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));
			List<WebElement> questions5 = driver.findElements(By.xpath("//button[contains(@id,'CMP_Checksheet') and @class='ui-multiselect ui-widget ui-state-default ui-corner-all']"));

			if(questions.size()==0&&questions2.size()==0&&questions3.size()==0&&questions4.size()==0 && questions5.size() == 0)
				checkSheetExists=false;

			/***    Date Fields      ****/
			for(WebElement ele : questions2)
			{
				ele.sendKeys(createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			}

			/***    Time Fields      ****/
			for(WebElement ele : questions3)
			{
				ele.sendKeys("00:00");
			}

			/***    Text Areas      ****/
			for(WebElement ele : questions4)
			{
				ele.sendKeys("Yes");
				keyPress("TAB");
			}

			/** Select first option from DropDowns other than Yes/No/NA **/
			for (WebElement ele : questions5) {
				moveScrollBar(ele);
				//Opening the options dialog box
				ele.click();
				int i = questions5.indexOf(ele);
				//Selecting the first option from dialog box
				String dynamicXpath="(//input[contains(@id,'ui-multiselect-"+(i+1)+"-CMP_Checksheet_Defaults_CaptureCheckSheet')])[1]";
				driver.findElement(By.xpath(dynamicXpath)).click();
				//Closing the options dialog box
				driver.findElement(By.xpath(dynamicXpath+"/../../../..//a//span[@class='ui-icon ui-icon-circle-close']")).click();
			}


			/** Yes/No DropDowns **/
			for (WebElement ele : questions)
			{
				new Select(ele).selectByVisibleText("Yes");
				keyPress("TAB");
				waitForSync(2);

				/****  Handling Any Obligatory questions - 'No' by checking warning symbol ***/
				if(driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "warning_symbol;xpath"))).size()==1)
				{
					new Select(ele).selectByVisibleText("No");
					waitForSync(2);

				}
			}

			/*******************************************************/
			if(chkSheetRequired)
			{
				if(checkSheetExists)
					writeExtent("Pass","Check sheet details selected on "+screenName);
				else
					writeExtent("Info","No check sheet details configured on "+screenName); //To be changed to fail later
			}

			waitForSync(2);
			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);


			waitForSync(2);
			switchToFrame("contentFrame", "TGC013");
			driver.switchTo().frame("popupContainerFrame");
			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
			waitForSync(2);
			switchToFrame("default");
			switchToFrame("contentFrame", "TGC013");
			waitForSync(3);

		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not save check sheet details on "+screenName);
		}
	}

	

    /**
	 * @author A-9847
	 * @Desc To capture the CDG COMPLIANCE checksheet
	 */
	public void captureCDGCompChecksheet()

     {
		
		captureChecksheetWithMultiFormats(true);
		
	}

	/**
	 * @author A-9844
	 * Description... To perform as is execute
	 * @throws Exception
	 */
	public void asIsExecute() throws Exception {
		screenName="Capture AWB";
		switchToFrame("default");
		switchToFrame("contentFrame", "TGC013");
		switchToWindow("storeParent");
		waitForSync(3);
		enterExecutionDate();
		String station = getLoggedInStation("TGC013");
		if (station.equals("CDG")) 
		{

			captureCDGCompChecksheet();
		}
		waitForSync(3);
		clickWebElement(sheetName2, "btn_AsIsExecute;xpath","AsIsExecute Button", screenName);
		waitForSync(10);
		switchToFrame("default");
		/*****waitForSync(10); ***/  
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		try {

			while (driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
					.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
				/***Thread.sleep(20800);***/
				Thread.sleep(12000);
			}
		} catch (Exception e) {
		}

		Thread.sleep(2000);
		switchToFrame("contentFrame", "TGC013");
		Thread.sleep(2000);
		String actText = driver
				.findElement(
						By.xpath(xls_Read.getCellValue(sheetName2,
								"txt_executed;xpath"))).getText();
		Thread.sleep(10000);
		String expText = "Executed";
		verifyScreenText(sheetName2, expText, actText, "As is Execute",
				"Capture AWB");
		Thread.sleep(2000);
	}

	
	/**
	 * @author A-7271
	 * @param value
	 * @param col
	 * Desc : Verify attributes
	 */
	public void verifyAttributes(String value,String col)
	{
		try
		{
		boolean attributeExists=false;
		 String locatorValue=xls_Read.getCellValue(sheetName, "htmlDiv_PickDropDetails;xpath");
		 System.out.println(locatorValue);
        locatorValue=locatorValue.replace("colIndex", col);
        System.out.println(locatorValue);
        
         List<WebElement> attributes=driver.findElements(By.xpath(locatorValue));
         
         
         for(WebElement actual:attributes)
         {
        	 if(actual.getText().equals(data(value)))
        	 {
        		 attributeExists=true;
        		 writeExtent("Pass","The attribute "+data(value)+" present on "+screenName);
        		
        		 break;
        	 }
         }
         
         if(attributeExists==false)
        	 writeExtent("Fail","The attribute "+data(value)+" does not present on "+screenName); 
       
		}
		
		
		catch(Exception e)
		{
			 writeExtent("Fail","The attribute "+data(value)+" does not present on "+screenName); 	
		}
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * DEsc : edit verification details
	 */
	public void editVerificationDetails() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "btn_editDetails;xpath", " Edit verification details Button ",screenName);
		waitForSync(1);
	}
	
	/**
	 * Desc : Clicking More options
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void clickMoreOptions(String awbNo) throws InterruptedException, IOException
	{
		String locatorMore = xls_Read.getCellValue(sheetName, "btn_moreOptions;xpath");
		locatorMore=locatorMore.replace("AWBNo", data(awbNo));
		moveScrollBar(driver.findElement(By.xpath(locatorMore)));
		driver.findElement(By.xpath(locatorMore)).click();
		waitForSync(5);
		
	}
	
	/**
	 * Desc : Clicking Capture AWB
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCaptureAwb() throws InterruptedException, IOException
	{
		
		//click CaptureAWB button
		waitForSync(5);
	    clickWebElement(sheetName, "btn_captureAWB;xpath", "CaptureAWB button", screenName);
	    waitForSync(5);
		
	}

	/**
	 * @author A-8783
	 * Desc : Perform photo verification
	 */
	public void performPhotoVerification()
	{
		try{
			moveScrollBar(driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_selectPhotoID;xpath"))));
			String locator =xls_Read.getCellValue(sheetName, "btn_selectPhotoID;xpath");
			driver.findElement(By.xpath(locator)).click();
			clickWebElementByWebDriver(sheetName, "lst_photoOptions;xpath", "select options", screenName);
			writeExtent("Pass","Performed photo verification on "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail","Could not perform photo verification on "+screenName);
		}


       
	}



       

	
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : add verification details
	 */
	public void addVerificationDetails() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_add;xpath", " Add verification details button ",screenName);
		waitForSync(1);

	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click documentation completed
	 */
	public void clickDocCompleted() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "chkBox_documentationCmpltd;name", "documentation complete checkbox",screenName);
		
	}
	
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : Save details
	 */
	public void save() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_save;xpath", " Save verification details button ",screenName);
		waitForSync(5);
		
		//Verifying if any message is getting displayed
		String locatorValue=xls_Read.getCellValue(sheetName, "htmlDiv_msg;xpath");
		
		
		try
		{
			if(driver.findElement(By.xpath(locatorValue)).isDisplayed())
			{
				getElementText(sheetName, "htmlDiv_msg;xpath","Confirmation message", screenName);
				clickWebElement(sheetName, "btn_ok;xpath", "Ok button ",screenName);
				waitForSync(1);	
			}
		}

		catch(Exception e)
		{

		}
	}
}

