package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class HandlingAreaSetUpScreen_WHS008 extends CustomFunctions {

	String sheetName = "HandlingAreaSetUpScreen_WHS008";
	String screenName = "Handling Area Set Up / Screen : WHS008";
	String screenId="WHS008";


	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	WebFunctions libr = new WebFunctions(driver, excelreadwrite, xls_Read);


	public HandlingAreaSetUpScreen_WHS008(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
/**
 * Description... Click Add Modify Handling Area And Verify PopUp
 * @throws Exception
 */
	public void clickAddModifyHandlingAreaAndVerifyPopUp() throws Exception {

		clickWebElement(sheetName, "btn_AddMdyHdlArea;xpath",
				"Add/Modify Handling Area", screenName);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		WebElement text = driver.findElement(By
				.xpath("//*[contains(text(),'Based On')]"));
		if (text.isDisplayed()) {
			System.out.println("Pop up opened");
			writeExtent("Pass", "Pop Up Opened " + "" + " On " + screenName
					+ " Page");
			onPassUpdate(
					screenName,
					"Pop Up Opened",
					"Pop Up Opened",
					"Pop Up",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		} else {
			writeExtent("Fail", "Pop Up not Opened" + "" + " On " + screenName
					+ " Page");
			onFailUpdate(
					screenName,
					"Pop Up Opened ",
					"Pop Up not Opened",
					"Pop Up",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		}

	}
	/**
	 * @author A-9844
	 * @Description... verify the location and its corresponding zone displayed
	 * @throws Exception 
	 */
	public void verifyLocationAndCorrespondingZone(String location,int verfCols[],String actVerfValues[]) throws Exception {
		
		Boolean found=false;
		enterValueInTextbox(sheetName, "inbx_locationCode;xpath", data(location), "location code", screenName);
		clickWebElement(sheetName, "btn_ListLocation;xpath", "List", screenName);

		clickWebElement(sheetName,"inbx_HArow;xpath","HA row", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_HA;xpath", "HA button", screenName);

		switchToWindow("storeParent");
		switchToWindow("child");


		try {
			List<WebElement> listOfZones=returnListOfElements(sheetName, "txt_zoneNames;xpath");
			List<String> value =returnTextListOfElements(listOfZones);



			for(int i=0;i<value.size();i++) {


				String actText=actVerfValues[0].replace(" ", "").trim();
				System.out.println(actText);

				String expText=value.get(i).trim();
				System.out.println(expText);


				if(actText.equals(expText)) {
					found=true;
					break;

				}


			}
		}
		catch(Exception e) {
			writeExtent("Fail", "Couldn't verify the zone for the location on " + screenName);
		}


		if(found=true){
			writeExtent("Pass", "Verified the zone for the location on " + screenName);
		}
		else{
			writeExtent("Fail", "Failed to verify the zone for the location on " + screenName);
		}

		clickWebElement(sheetName, "btn_Save;xpath", "Save", screenName);
		waitForSync(2);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("WHS008");
	}

/**
 * Description... Click Add Modify Handling Area
 * @throws Exception
 */
	public void clickAddModifyHandlingArea() throws Exception {

		clickWebElement(sheetName, "btn_AddMdyHdlArea;xpath",
				"Add/Modify Handling Area", screenName);

	}
/**
 * Description... Verify Created Handling Area
 * @param hdlArea
 * @throws Exception
 */
	public void verifyCreatedHandlingArea(String hdlArea) throws Exception {

		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_HdlArea;xpath", data(hdlArea),
				"Hdl Area type", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		ele = driver.findElement(By.xpath("//select[@id='CMP_Warehouse_Defaults_HandlingAreaSetUp_handlingAreaType']"));

		Select sec = new Select(ele);
		List<WebElement> list = sec.getOptions();
		System.out.println(list);
		boolean value = false;
		for (int i = 0; i < list.size(); i++) {
			String text = list.get(i).getText();
			if (text.contains(data(hdlArea))) {
				value = true;
				break;
			}
		}
		if (value) {
			System.out.println("Handling Area created successfully");
			writeExtent("Pass",
					"Created Handling Area present in the drop down " + ""
							+ " On " + screenName + " Page");
			onPassUpdate(
					screenName,
					"Created Handling Area present in the drop down",
					"Created Handling Area present in the drop down",
					"Drop Down",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");

		} else {
			writeExtent("Fail",
					"Created Handling Area not present in the drop down " + ""
							+ " On " + screenName + " Page");
			onFailUpdate(
					screenName,
					"Created Handling Area present in the drop down ",
					"Created Handling Area not present in the drop down",
					"Drop Down",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		}
	}
/**
 * Description... Verify Created Handling Area In DropDown
 * @param hdlArea
 * @throws Exception
 */
	public void verifyCreatedHandlingAreaInDropDown(String hdlArea) throws Exception {

		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_HdlArea;xpath", data(hdlArea),
				"Hdl Area type", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		ele = driver
				.findElement(By
						.xpath("//select[@id='CMP_Warehouse_Defaults_HandlingAreaSetUp_handlingAreaType']"));
		Select sec = new Select(ele);
		List<WebElement> list = sec.getOptions();
		System.out.println(list);
		boolean value = false;
		for (int i = 0; i < list.size(); i++) {
			String text = list.get(i).getText();
			if (text.contains(data(hdlArea))) {
				value = true;
				break;
			}
		}
		if (value) {
			System.out.println("Handling Area created successfully");
			writeExtent("Pass",
					"Created Handling Area present in the drop down " + ""
							+ " On " + screenName + " Page");
			onPassUpdate(
					screenName,
					"Created Handling Area present in the drop down",
					"Created Handling Area present in the drop down",
					"Drop Down",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");

		} else {
			writeExtent("Fail",
					"Created Handling Area not present in the drop down " + ""
							+ " On " + screenName + " Page");
			onFailUpdate(
					screenName,
					"Created Handling Area present in the drop down ",
					"Created Handling Area not present in the drop down",
					"Drop Down",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		}
	}
/**
 * Description... Click List Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickList() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_ListHdlarea;xpath", "List Button",
				screenName);

	}
/**
 * Description... Select Multiple Table Records
 * @throws InterruptedException
 */
	public void selectMultipleTableRecords() throws InterruptedException {

		List<WebElement> checkBoxes = driver.findElements(By
				.xpath("//input[contains(@id,'fld') and @name='checkBox']"));
		for (int i = 5; i <= 10; i++) {
			checkBoxes.get(i).click();
		}

	}
/**
 * Description... Select Single Table Record
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectSingleTableRecord() throws InterruptedException, IOException {

		waitForSync(4);
		clickWebElement(sheetName, "chkbox_Single;xpath", "Check box",screenName);

	}
/**
 * Description... Error Message Verification
 * @param errorMessage
 * @throws Exception
 */
	public void errorMessageVerification(String errorMessage) throws Exception {

		comm.handleAlert("getText", "Handling Area Set Up / Screen");
		String actualAlertText = WebFunctions.getPropertyValue(comm.proppath,
				"AlertText");
		String expectedAlertText = data(errorMessage);
		verifyScreenText(
				screenName,
				expectedAlertText,
				actualAlertText,
				"Please select a single record",
				"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on list button \n , 4.Select multiple check boxes \n");

	}
/**
 * Description... Create Return Handling Area Config With Only Destination And Pickable handling area
 * @param destinationHa
 * @param pickableHa
 * @throws Exception
 */
	public void createReturnHandlingAreaConfigWithOnlyDestinationAndPickableHa(String destinationHa,
			String pickableHa) throws Exception {
		waitForSync(2);
		clickWebElement(sheetName, "btn_addReturnHdlArea;xpath","Add return handling area", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "lst_destination;xpath", "DestinationHa LOV",
				screenName);

		waitForSync(3);
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		List listOfDestinations = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
		List<String> destinationValues = returnTextListOfElements(listOfDestinations);
		System.out.println(destinationValues);
		clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
		switchToWindow("getFirstChild");
		String randomDestinationValues = destinationValues.get(comm.randomNumberInList(1, destinationValues.size()-1));	
		enterValueInTextbox(sheetName, "inbx_destination;xpath",randomDestinationValues, "Destination HA", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "lst_pickable;xpath", "DestinationHa LOV",
				screenName);
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		List listOfPickables = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
		List<String> pickableValues = returnTextListOfElements(listOfPickables);
		System.out.println(pickableValues);
		clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
		switchToWindow("getFirstChild");
		String randomPickableValues = pickableValues.get(comm.randomNumberInList(1, pickableValues.size()-1));
		javaScriptToEnterValueInTextBox(sheetName, "inbx_pickable;xpath",randomPickableValues, "Pickable HA	", screenName);
		String destinationHa1 = driver.findElement(By.xpath("//tr[@rowcount][last()]//input[@name='destinationHandlingAreaCode']")).getAttribute("value");
		String pickableHa1 = driver.findElement(By.xpath("//tr[@rowcount][last()]//input[@name='pickableHandlingAreaCode']")).getAttribute("value");
		map.put("DestinationHa1", destinationHa1);	
		map.put("PickableHa1", pickableHa1);
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", screenId);

	}
/**
 * Description... Verify Created Return Handling Area
 * @param destinationHa
 * @param pickableHa
 */
	public void verifyCreatedReturnHandlingArea(String destinationHa,String pickableHa) {
		waitForSync(2);	
		boolean flag= false;
		List destinationHaList = returnListOfElements(sheetName,"list_DestinationValues;xpath");
		List<String> destinationValues = returnAttributeValueListOfElements(destinationHaList,"value");
		String expectedText = data(destinationHa);
		if(destinationValues.contains(expectedText)){
			flag=true;
			onPassUpdate(
					screenName,
					"Created DestinationHa"+expectedText+" available ",
					"Created DestinationHa"+expectedText+" available ",
					"Table list",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		}
		if(!flag)

			onFailUpdate(
					screenName,
					"Created DestinationHa"+expectedText+" available ",
					"Created DestinationHa"+expectedText+"Not available",
					"Drop Down",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");

		waitForSync(4);	
		boolean flag1= false;
		List pickableHaList = returnListOfElements(sheetName,"txt_PickableLocation;xpath");
		List<String> pickableVaues = returnAttributeValueListOfElements(pickableHaList,"value");
		String expectedText1 = data(pickableHa);
		if(pickableVaues.contains(expectedText1)){
			flag1=true;
			onPassUpdate(
					screenName,
					"Created PickableHa"+expectedText1+" available ",
					"Created PickableHa"+expectedText1+" available",
					"Table list",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		}

		if(!flag1) {
			onFailUpdate(
					screenName,
					"Created PickableHa"+expectedText1+" available ",
					"Created PickableHa"+expectedText1+"Not available",
					"Drop Down",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		}


	}


/**
 * Description... Click Clear button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickClear() throws InterruptedException, IOException {
		waitForSync(4);
		clickWebElement(sheetName, "btn_clear;xpath", "Clear button",
				screenName);
		waitForSync(3);
	}
/**
 * Description... Relist Created Handling Area
 * @param handlingArea
 * @throws InterruptedException
 */
	public void relistCreatedHandlingArea(String handlingArea)
			throws InterruptedException {

		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_HandlingArea;xpath",
				data(handlingArea), "Hdl Area ", screenName);

	}
/**
 * Description... Click Delete Button
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException
 */
	public void clickDelete() throws InterruptedException, AWTException,
	IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_delete;xpath", "Delete Button",
				screenName);
		libr.keyPress("ENTER");
	}
	/**
	 * @author A-9844
	 * Description... enter Zone code
	 * @throws InterruptedException
	 */
	public void enterZone(String zone)throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_zoneCode;xpath",zone, "zone", screenName);
		waitForSync(2);
	}
	/**
	 * Description... Get Handling Area
	 * @return
	 * @throws InterruptedException
	 */
	public String getHandlingArea() throws InterruptedException
	{
		String HA=getElementText(sheetName,"txt_HAValue;xpath","HA Value", screenId);
		return HA;
	}
/**
 * Description... Create Handling Area For Location
 * @param handlingArea
 * @throws Exception
 */
	public void createHandlingAreaForLocation(String handlingArea) throws Exception {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_HdlArea;xpath",data(handlingArea), "Hdl Area ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		selectRandomValueFromDropdown(sheetName, "lst_hdlAreaType;xpath");
		waitForSync(2);
		selectRandomValueFromDropdown(sheetName, "lst_vehicleType;xpath");
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "lst_LocationCode;xpath", "Location LOV",
				screenName);
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		List listOfLocations = returnListOfElements(sheetName,"list_LocationValues;xpath");
		List<String> locationValues = returnTextListOfElements(listOfLocations);
		System.out.println(locationValues);
		clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenName);
		switchToWindow("getFirstChild");
		for (int j = 0; j < locationValues.size(); j++) {
			driver.findElement(
					By.xpath("//input[@id='CMP_WAREHOUSE_DEFAULTS_HANDLINGAREASETUP_LOCATIONCODEADDTOLIST']"))
					.clear();
			driver.findElement(
					By.xpath("//input[@id='CMP_WAREHOUSE_DEFAULTS_HANDLINGAREASETUP_LOCATIONCODEADDTOLIST']"))
					.sendKeys(locationValues.get(j));
			waitForSync(2);
			clickWebElement(sheetName, "btn_AddtoList;xpath", "Add to List",
					screenName);
			waitForSync(2);
			WebElement errorMessage = null;
			try {
				errorMessage = driver.findElement(By
						.xpath("//td[@class='ic-error-msg']"));
			} catch (Exception e) {
			}

			if (errorMessage != null && errorMessage.isDisplayed()) {
				continue;
			} else {

				break;
			}
		}
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", screenId);

	}
	/**
	 * @author A-9847
	 * @Desc To retrieve the zone of the given location
	 * @param location
	 * @return
	 * @throws Exception
	 */
	public String retrieveZoneOfLocation(String location) throws Exception {

		String Zone="";
		try
		{
			clickWebElement(sheetName,"btn_locationLOV;xpath","Location LOV", screenName);
			waitForSync(5);
			switchToWindow("storeParent");
			switchToWindow("child");
			enterValueInTextbox(sheetName, "inbx_locationCode;xpath", data(location), "location code", screenName);
			clickWebElement(sheetName, "btn_listLOV;xpath", "List button", screenName);
			waitForSync(3);
			Zone = getElementText(sheetName,"div_zone;xpath", "Zone", screenName);		
			System.out.println(Zone);
			clickWebElement(sheetName, "btn_okLOV;xpath", "Clicking OK in LOV", screenName);
			waitForSync(2);
			switchToWindow("getParent");
			switchToDefaultAndContentFrame("WHS008");       

		}
		catch(Exception e){
			
			writeExtent("Fail", "Failed to retrieve the Zone on " + screenName);
			
		}

		return Zone;
	}

/**
 * Description... Create Handling Area For Zone
 * @param handlingArea
 * @throws Exception
 */
	public void createHandlingAreaForZone(String handlingArea) throws Exception {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_HdlArea;xpath",data(handlingArea), "Hdl Area ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		selectRandomValueFromDropdown(sheetName, "lst_hdlAreaType;xpath");
		waitForSync(2);
		selectRandomValueFromDropdown(sheetName, "lst_vehicleType;xpath");
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "lst_Zone;xpath", "Zone LOV",	screenName);
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		List listOfZones = returnListOfElements(sheetName,"lst_ZoneLov;xpath");
		List<String> zoneValues = returnTextListOfElements(listOfZones);
		System.out.println(zoneValues);

		clickWebElement(sheetName, "btn_Close;xpath", "Close Button", screenName);
		switchToWindow("getFirstChild");
		for (int j = 0; j < zoneValues.size(); j++) {
			driver.findElement(
					By.xpath("//input[@id='CMP_Warehouse_Defaults_HandlingAreaSetUp_zoneName']"))
					.clear();
			driver.findElement(
					By.xpath("//input[@id='CMP_Warehouse_Defaults_HandlingAreaSetUp_zoneName']"))
					.sendKeys(zoneValues.get(j));
			waitForSync(2);
			clickWebElement(sheetName, "btn_AddtoList;xpath", "Add to List",
					screenName);
			waitForSync(2);
			WebElement errorMessage = null;
			try {
				errorMessage = driver.findElement(By
						.xpath("//td[@class='ic-error-msg']"));
			} catch (Exception e) {
			}

			if (errorMessage != null && errorMessage.isDisplayed()) {
				continue;
			} else {

				break;
			}
		}
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", screenId);

	}
/**
 * Description... Create Handling Area With Multiple Locations
 * @param handlingArea
 * @throws Exception
 */
	public void createHandlingAreaWithMultipleLocations(String handlingArea)
			throws Exception {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_HdlArea;xpath",
				data(handlingArea), "Hdl Area ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		selectRandomValueFromDropdown(sheetName, "lst_hdlAreaType;xpath");
		waitForSync(2);
		selectRandomValueFromDropdown(sheetName, "lst_vehicleType;xpath");
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "lst_LocationCode;xpath", "Location LOV",
				screenName);
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		List listOfLocations = returnListOfElements(sheetName,"list_LocationValues;xpath");
		List<String> locationValues = returnTextListOfElements(listOfLocations);
		System.out.println(locationValues);
		clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenName);
		switchToWindow("getFirstChild");
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < locationValues.size(); j++) {
				driver.findElement(
						By.xpath("//input[@id='CMP_WAREHOUSE_DEFAULTS_HANDLINGAREASETUP_LOCATIONCODEADDTOLIST']"))
						.clear();
				driver.findElement(
						By.xpath("//input[@id='CMP_WAREHOUSE_DEFAULTS_HANDLINGAREASETUP_LOCATIONCODEADDTOLIST']"))
						.sendKeys(locationValues.get(j));

				waitForSync(2);
				clickWebElement(sheetName, "btn_AddtoList;xpath",
						"Add to List", screenName);
				waitForSync(2);
				WebElement errorMessage = null;

				try {
					errorMessage = driver.findElement(By
							.xpath("//td[@class='ic-error-msg']"));
					if (errorMessage != null && errorMessage.isDisplayed()) 
						continue;

					else

						break;
				} catch (Exception e) {

					break;
				}


			}
		}

		String locationOne = driver.findElement(By.xpath("//tbody[@id='locationBody']/tr[1]/td[2]")).getText();
		String locationTwo = driver.findElement(By.xpath("//tbody[@id='locationBody']/tr[2]/td[2]")).getText();
		map.put("Location1", locationOne);
		map.put("Location2", locationTwo);
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", screenId);

	}
/**
 * Description... Verification Of Locations
 * @param location1
 * @param location2
 */
	public void verificationOfLocations(String location1, String location2) {

		boolean flag= false;
		List locationList = returnListOfElements(sheetName,"lst_LocationValues;xpath");
		List<String> locationValues = returnTextListOfElements(locationList);
		String expectedLoc1 = data(location1);
		String expectedLoc2 = data(location2);
		if(locationValues.contains(expectedLoc1) && locationValues.contains(expectedLoc2) ){
			flag=true;
			onPassUpdate(
					screenName,
					"Created locations "+expectedLoc1+" and "+ expectedLoc2+" are available ",
					"Created locations "+expectedLoc1+" and "+ expectedLoc2+" are available ",
					"Table list",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");
		}
		if(!flag)

			onFailUpdate(
					screenName,
					"Created locations "+expectedLoc1+" and "+ expectedLoc2+"are available ",
					"Created locations "+expectedLoc1+" and "+ expectedLoc2+"are not available ",
					"Table list",
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area");

	}
/**
 * Description... Verify Created Handling Area In Table
 * @param handlingArea
 * @throws InterruptedException
 */
	public void verifyCreatedHandlingAreaInTable(String handlingArea) throws InterruptedException{
		waitForSync(3);
		verifyElementDisplayed(sheetName, "txt_HdlArea;xpath", "Created Handling area verification", "Handling area details","Handling area details");
	}

/**
 * Description... Collect Selected Handling Area
 */

	public void collectSelectedHandlingArea(){

		WebElement hdlArea = driver.findElement(By.xpath("(//td[@class='iCargoTableTd'])[1]"));
		String hdlAreaValue = hdlArea.getText();
		map.put("HandlingArea", hdlAreaValue);

	}
/**
 * Description... Enter Handling Area
 * @param hdlArea
 * @throws InterruptedException
 */
	public void enterHandlingArea(String hdlArea) throws InterruptedException {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_handlingAreaH;xpath",data(hdlArea), "Handling Area ", screenName);
	}
/**
 * Description... Create Handling Area And Return Handling Area Config
 * @param handlingArea
 * @throws Exception
 */
	public void createHandlingAreaAndReturnHandlingAreaConfig(String handlingArea) throws Exception{
		waitForSync(2);

		enterValueInTextbox(sheetName, "inbx_HdlArea;xpath",data(handlingArea), "Hdl Area ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		selectRandomValueFromDropdown(sheetName, "lst_hdlAreaType;xpath");
		waitForSync(2);
		selectRandomValueFromDropdown(sheetName, "lst_vehicleType;xpath");
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "lst_LocationCode;xpath", "Location LOV",
				screenName);
		switchToWindow("storeFirstChild");
		switchToWindow("secondChild");
		List listOfLocations = returnListOfElements(sheetName,"list_LocationValues;xpath");
		List<String> locationValues = returnTextListOfElements(listOfLocations);
		System.out.println(locationValues);
		clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenName);
		switchToWindow("getFirstChild");
		for (int j = 0; j < locationValues.size(); j++) {
			driver.findElement(
					By.xpath("//input[@id='CMP_WAREHOUSE_DEFAULTS_HANDLINGAREASETUP_LOCATIONCODEADDTOLIST']"))
					.clear();
			driver.findElement(
					By.xpath("//input[@id='CMP_WAREHOUSE_DEFAULTS_HANDLINGAREASETUP_LOCATIONCODEADDTOLIST']"))
					.sendKeys(locationValues.get(j));
			waitForSync(2);
			clickWebElement(sheetName, "btn_AddtoList;xpath", "Add to List",
					screenName);
			waitForSync(2);
			WebElement errorMessage = null;
			try {
				errorMessage = driver.findElement(By
						.xpath("//td[@class='ic-error-msg']"));
			} catch (Exception e) {
			}

			if (errorMessage != null && errorMessage.isDisplayed()) {
				continue;
			} else {

				break;
			}
		}
		//Creation of return handling area configuration.
		waitForSync(5);
		for (int i = 0; i < 2; i++) {
			clickWebElement(sheetName, "btn_addReturnHdlArea;xpath","Add return handling area", screenName);
			waitForSync(3);
			clickWebElement(sheetName, "lst_destination;xpath", "DestinationHa LOV",
					screenName);
			waitForSync(3);
			switchToWindow("storeFirstChild");
			switchToWindow("secondChild");
			List listOfDestinations = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
			List<String> destinationValues = returnTextListOfElements(listOfDestinations);
			System.out.println(destinationValues);
			clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
			switchToWindow("getFirstChild");
			String randomDestinationValues = destinationValues.get(comm.randomNumberInList(1, destinationValues.size()-1));	
			enterValueInTextbox(sheetName, "inbx_destination;xpath",randomDestinationValues, "Destination HA", screenName);
			waitForSync(3);

			clickWebElement(sheetName, "lst_DesPickable;xpath", "Possible Dest. From Pickable HA",	screenName);
			switchToWindow("storeFirstChild");
			switchToWindow("secondChild");
			List listOfDestinationPickables = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
			List<String> destinationPickableValues = returnTextListOfElements(listOfDestinationPickables);
			System.out.println(destinationPickableValues);
			clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
			switchToWindow("getFirstChild");
			String randomDestinationPickableValues = destinationPickableValues.get(comm.randomNumberInList(1, destinationPickableValues.size()-1));
			enterValueInTextbox(sheetName, "inbx_DestinationPickable;xpath",randomDestinationPickableValues, "Possible Dest. From Pickable HA ", screenName);
			waitForSync(3);

			clickWebElement(sheetName, "lst_pickable;xpath", "DestinationHa LOV",
					screenName);
			switchToWindow("storeFirstChild");
			switchToWindow("secondChild");
			List listOfPickables = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
			List<String> pickableValues = returnTextListOfElements(listOfPickables);
			System.out.println(pickableValues);
			clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
			switchToWindow("getFirstChild");
			String randomPickableValues = pickableValues.get(comm.randomNumberInList(1, pickableValues.size()-1));
			javaScriptToEnterValueInTextBox(sheetName, "inbx_pickable;xpath",randomPickableValues, "Pickable HA	", screenName);
			waitForSync(3);


		}
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", screenId);
	}
/**
 * Description... Create Return Handling Area Configuration
 * @param handlingArea
 * @throws Exception
 */
	public void createReturnHandlingAreaConfig(String handlingArea) throws Exception{
		waitForSync(2);

		//Creation of return handling area configuration.
		waitForSync(5);
		for (int i = 0; i < 2; i++) {
			clickWebElement(sheetName, "btn_addReturnHdlArea;xpath","Add return handling area", screenName);
			waitForSync(3);
			clickWebElement(sheetName, "lst_destination;xpath", "DestinationHa LOV",
					screenName);
			waitForSync(3);
			switchToWindow("storeFirstChild");
			switchToWindow("secondChild");
			List listOfDestinations = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
			List<String> destinationValues = returnTextListOfElements(listOfDestinations);
			System.out.println(destinationValues);
			clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
			switchToWindow("getFirstChild");
			String randomDestinationValues = destinationValues.get(comm.randomNumberInList(1, destinationValues.size()-1));	
			enterValueInTextbox(sheetName, "inbx_destination;xpath",randomDestinationValues, "Destination HA", screenName);
			waitForSync(3);

			clickWebElement(sheetName, "lst_DesPickable;xpath", "Possible Dest. From Pickable HA",	screenName);
			switchToWindow("storeFirstChild");
			switchToWindow("secondChild");
			List listOfDestinationPickables = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
			List<String> destinationPickableValues = returnTextListOfElements(listOfDestinationPickables);
			System.out.println(destinationPickableValues);
			clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
			switchToWindow("getFirstChild");
			String randomDestinationPickableValues = destinationPickableValues.get(comm.randomNumberInList(1, destinationPickableValues.size()-1));
			enterValueInTextbox(sheetName, "inbx_DestinationPickable;xpath",randomDestinationPickableValues, "Possible Dest. From Pickable HA ", screenName);
			waitForSync(3);

			clickWebElement(sheetName, "lst_pickable;xpath", "DestinationHa LOV",
					screenName);
			switchToWindow("storeFirstChild");
			switchToWindow("secondChild");
			List listOfPickables = returnListOfElements(sheetName,"list_DestinationLOV;xpath");
			List<String> pickableValues = returnTextListOfElements(listOfPickables);
			System.out.println(pickableValues);
			clickWebElement(sheetName, "btn_OK1;xpath", "Ok Button", screenName);
			switchToWindow("getFirstChild");
			String randomPickableValues = pickableValues.get(comm.randomNumberInList(1, pickableValues.size()-1));
			javaScriptToEnterValueInTextBox(sheetName, "inbx_pickable;xpath",randomPickableValues, "Pickable HA	", screenName);
			waitForSync(3);


		}
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", screenId);
	}
/**
 * Description... Select Based On
 * @param basedOn
 */
	public void selectBasedOn(String basedOn) {
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_basedOn;xpath", data(basedOn), "Based On", "VisibleText");
	}
/**
 * Description... Delete Return Handling Area Record
 * @throws InterruptedException
 */
	public void deleteReturnHandlingAreaRecord() throws InterruptedException {
		try{
			waitForSync(2);
			clickWebElement(sheetName, "chkbox_returnHdn;xpath", "Return handling record", screenName);
			waitForSync(2);
			clickWebElement(sheetName, "btn_deletereturnHdl;xpath", "Delete button", screenName);
			onPassUpdate(
					screenName,
					"Return handling area deleted ","Return handling area deleted" ,"Return handling area" ,
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area \n , 4.Click return handling area check box \n");
			clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
			switchToWindow("getParent");
			switchToFrame("contentFrame", screenId);

		}
		catch(Exception e) {
			onFailUpdate(
					screenName,
					"Return handling area deleted ","Return handling area not deleted" ,"Return handling area" ,
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area \n , 4.Click return handling area check box \n");
		}

	}
/**
 * Description... List Created Handling Area In Add Modify Handling Area PopUp
 * @param handlingArea
 * @throws InterruptedException
 * @throws IOException 
 */
	public void listCreatedHandlingAreaInAddModifyHandlingAreaPopUp(String handlingArea) throws InterruptedException, IOException {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_HdlArea;xpath",data(handlingArea), "Hdl Area ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);

	}
/**
 * Description... Verify Handling Area Label In Add Modify Handling Area PopUp
 * @throws InterruptedException
 */
	public void verifyHandlingAreaLabelInAddModifyHandlingAreaPopUp() throws InterruptedException{
		String handlingAreaTextField=comm.getElementText(sheetName, "txt_HdlArealabel;xpath" , "Hdlg Area", "AddModifyHandlingArea Pop Up");
		verifyScreenText("AddModifyHandlingArea Pop Up", "Hdlg Area", handlingAreaTextField, "Hdlg Area", "1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area \n , 4.Check for the text \n");

	}
/**
 * Description... Verify Vehicle Types In DropDown
 */
	public void verifyVehicleTypesInDropDown() {
		WebElement vehicleType = driver.findElement(By.xpath("//select[@id='CMP_WAREHOUSE_DEFAULTS_HANDLINGAREASETUP_VEHICLETYPE']"));
		String[] exp = {"Forklift","Truck"};
		Select sec = new Select(vehicleType);
		List<WebElement> list = sec.getOptions();
		ArrayList<String> list1=new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String text = list.get(i).getText();
			list1.add(text);	

		}
		if (list1.contains(exp[0])&&list1.contains(exp[1]))
			onPassUpdate(
					"Add/Modify Handling Area On Handling Area Pop Up ",
					exp[0]+" and "+exp[1]+"available in Vehicle type drop down","Forklift and Truck available in Vehicle type drop down" ,"Vehicle Type drop down" ,
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area \n ,  4.Check for the vehicle type \n");

		else{
			onFailUpdate(
					"Add/Modify Handling Area On Handling Area Pop Up ",
					exp[0]+" and "+exp[1]+"available in Vehicle type drop down" ,"Forklift and Truck not available in Vehicle type drop down" ,"Vehicle Type drop down" ,
					"1.Login to iCapsit \n ,2.Invoke WHS008 screen \n ,3.Click on AddUpdateHandling area \n , 4.Click return handling area check box \n");
		}


	}

}
