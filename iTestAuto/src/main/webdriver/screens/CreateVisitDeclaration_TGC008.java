/**
 * @author A-8468
 */
package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CreateVisitDeclaration_TGC008 extends CustomFunctions
{

	public CreateVisitDeclaration_TGC008(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="CreateVisitDeclaration_TGC008";
	public String screenName="CreateVisitDeclaration_TGC008";
	
	/**
	 * Description : To enter Drop-off/Pickup code
	 * @param dropOffPickUpCode : Drop-off/Pickup code created from portal
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterdropOffPickUpCode(String dropOffPickUpCode) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_dropOffPickUpCode;id", dropOffPickUpCode, " Drop Off PickUp Code ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To enter Token
	 * @param Token : Token for SST
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterToken(String Token) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_token;id", Token, " Token ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To click list button on TGC008 screen
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickList() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_list;id", " List Button ",screenName);
		waitForSync(2);
	}
	
	/**
	 * Description : To click clear button on TGC008 screen
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickClear() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_clear;id", " Clear Button ",screenName);
		waitForSync(2);
	}
	
	/**
	 * Description : To click truck and driver registration link on TGC008 screen
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickTruckandDriverRegistration() throws InterruptedException, IOException {
		clickWebElement(sheetName, "lnk_TruckandDriverRegistration;id", " Truck and Driver Registration link ",screenName);
		waitForSync(2);
	}
	
	/**
	 * Description : To select purpose of visit from dropdown
	 * @param purposeOfVisit : values from purpose of visit dropdown e.g., Pick up
	 * @throws InterruptedException
	 */
	public void selectPurposeOfVisit(String purposeOfVisit) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_purposeOfVisit;id", purposeOfVisit, " Purpose Of Visit ", "VisibleText");
		waitForSync(2);
	}
	
	/**
	 * Description : To select vehicle type from the dropdown
	 * @param VehicleType : values from vehicle type drop down e.g., truck, all
	 * @throws InterruptedException
	 */
	public void selectVehicleType(String VehicleType) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_vehicleType;id", VehicleType, " Vehicle Type ", "VisibleText");
		waitForSync(2);
	}
	
	/**
	 * Description : To enter trucking company name
	 * @param TruckingCompany : Name of the trucking company e.g., Norton Trucking Company
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterTruckingCompany(String TruckingCompany) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_truckingComapny;id", TruckingCompany, " Trucking Company ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To enter vehicle number
	 * @param VehicleNumber : vehicle number to which shipment is assigned e.g., MH04HD4561
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterVehicleNumber(String VehicleNumber) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_vehicleNumber;id", VehicleNumber, " Vehicle Number ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To enter first name for the driver
	 * @param DriverFirstName : first name for driver e.g, Joe
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterDriverFirstName(String DriverFirstName) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_driverName;id", DriverFirstName, " Driver First Name ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To enter last name of the driver
	 * @param DriverLastName : last name for the driver e.g, Wilson
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterDriverLastName(String DriverLastName) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_driverLastName;id", DriverLastName, " Driver Last Name ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To enter mobile number
	 * @param MobileNumber : Mobile no of the driver e.g., +49151256348
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterMobileNumber(String MobileNumber) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_mobileNumber;id", MobileNumber, " Mobile Number ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To enter email id
	 * @param EmailID : Email id of the driver e.g., abc.def@ibsplc.com
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterEmailID(String EmailID) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_emailID;id", EmailID, " Email ID ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : to select id type from the dropdown
	 * @param IdType : values from ID type dropdown e.g., Driving License
	 * @throws InterruptedException
	 */
	public void selectIdType(String IdType) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_idType;id", IdType, " Id Type", "VisibleText");
	}
	
	/**
	 * Description : To enter issuing state
	 * @param idIssuingState : place where given id is issued e.g., Germany, India
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterIdIssuingState(String idIssuingState) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_idIssuingState;id", idIssuingState, "Id Issuing State", screenName);
		waitForSync(2);
			
	}
	/**
	 * Description : To enter id details
	 * @param idDetails : Details of id given e.g., Driving License number (G67171)
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterIdDetails(String idDetails) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_idDetails;id", idDetails, "Id Details", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To enter date of birth
	 * @param driverDOB : Date of birth of the driver as per id e.g.,24-Dec-2019
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterDriverDOB(String driverDOB) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_driverDOB;id", driverDOB, "Driver DOB", screenName);
		waitForSync(2);
		
	}
	
	/**
	 * Description : To enter registration number
	 * Not a mandatory field
	 * @param RegistrationNumber : Registration for the driver
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterRegistrationNumber(String RegistrationNumber) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_RegistrationNumber;id", RegistrationNumber, " RegistrationNumber ", screenName);
		waitForSync(2);
			
	}
	
	/**
	 * Description : To select product priority from the drop down
	 * @param ProductPriority : values from product priority dropdown e.g., td.flash
	 * @throws InterruptedException
	 */
	public void selectProductPriority(String ProductPriority) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_ProductPriority;id", ProductPriority, " Product Priority", "VisibleText");
		waitForSync(2);
	}
	
	/**
	 * Description : To select nature of shipment from the dropdown
	 * @param NatureOfShipment : vallues from nature of shipment dropdown e.g.,COMBIDGR
	 * @throws InterruptedException
	 */
	public void selectNatureOfShipment(String NatureOfShipment) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_NatureOfShipment;id", NatureOfShipment, " Nature Of Shipment", "VisibleText");
		waitForSync(2);
	}
	
	/**
	 * Description : To select department from the multi-select dropdown, also covered uncheck all and check all in the cases
	 * @param operation : what has to be selected from the dropdown e.g.,check_all, uncheck_all, index(index of the value in dropdown), visible_text(to select by visible text)
	 * @param indexORvisibletext : Array of indexes or visible text to be selected, combination doesn't work in this method 
	 * 			for check all and uncheck all give empty array
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void selectDepartment(String operation, String [] indexORvisibletext) throws InterruptedException, IOException 
	{	
		clickWebElement(sheetName, "btn_department;id", " Department button", screenName); 
		String xpath = xls_Read.getCellValue(sheetName, "lst_departmentOptions;xpath");
		  waitForSync(2);
		
		  switch (operation) {	
			
			case "check_all":
				clickWebElement(sheetName, "lnk_CheckAll;xpath", "Check All ", screenName); 
				break; 
				
			case "uncheck_all":
				clickWebElement(sheetName, "lnk_UnCheckAll;xpath", "UnCheck All ", screenName); 
				break;
				
			case "index" :
				for(int i = 0 ; i<indexORvisibletext.length ;i++)
				{
				String dynxapth = xpath + "["+ indexORvisibletext[i] +"]";
				driver.findElement(By.xpath(dynxapth)).click();
				}
				
			case "visible_text" :
				for(int i = 0 ; i<indexORvisibletext.length ;i++)
				{
				String dynxapth = xpath + "[contains(.,'"+ indexORvisibletext[i] +"')]";
				driver.findElement(By.xpath(dynxapth)).click();
				}
				
				waitForSync(2);
		  }
	}
	
	/**
	 * Description : To enter sub token number
	 * @param subToken : when token contains multiple sub token, identification number for sub token
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void entersubToken(String subToken) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_subToken;id", subToken, " Sub Token ", screenName);
			
	}
	
	/**
	 * Description : To click on add token button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickAddToken() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_add;id", " Add token button ",screenName);
	}
	
	/**
	 * Description : To enter and add sub token
	 * @param subToken : sub token number
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void AddSubToken(String subToken) throws InterruptedException, AWTException, IOException {
		entersubToken(subToken);
		clickAddToken();
		waitForSync(2);
	}
	
	/**
	 * Description : To enter AWB no and click on add to list button
	 * @param ShipmentPrefix : Shipment prefix of the AWB e.g., 020
	 * @param AWBno : AWB no for the shipment (master document number)
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void AddAWB(String ShipmentPrefix , String AWBno) throws InterruptedException, IOException {
		
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;id", ShipmentPrefix, " Shipment Prefix ", screenName);
		enterValueInTextbox(sheetName, "inbx_AWBno;id", AWBno, " AWB No ", screenName);
		clickWebElement(sheetName, "btn_addToList;id", " Add to list button ",screenName);
		waitForSync(2);
	}
	
	/**
	 * Description : To add multiple AWBs to the token
	 * @param ShipmentPrefix : Shipment prefix of the AWBs e.g., 020
	 * @param AWBnos : Array of multiple AWb numbers to be added to the token e.g., [12312311,12312322,12312333]
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void AddmultipleAWB(String ShipmentPrefix , String [] AWBnos) throws InterruptedException, IOException {

   clickaddMultipleandSwitchFrame();
   enterMultipleAWBs(ShipmentPrefix, AWBnos);
   clickaddAllandSwitchToParentFrame();
  }
	
	/**
	 * Description : To enter and list Dropoff code 
	 * @param dropOffPickUpCode : Already Drop-of code created either from portal or this screen
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void listDropOffCode(String dropOffPickUpCode) throws InterruptedException, AWTException, IOException {
		enterdropOffPickUpCode(dropOffPickUpCode);
		clickList();
		waitForSync(4);
	}
	
	/**
	 * Description : To delete an AWB attached to Drop-off code
	 * @param AWBno : AWB no to be deleted
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void deleteAWB(String AWBno) throws InterruptedException, AWTException {
		String dynXpath ="//tr[contains(.,'" + AWBno + "')]//i[contains(@class,'icon delete')]";
		try{
			driver.findElement(By.xpath(dynXpath)).click();
			waitForSync(4);
			
		}catch(Exception e){
			
			System.out.println("Could not delete AWB on " + screenName + " Page");
			writeExtent("Fail", "Could not delete AWB on " + screenName + " Page");
			Assert.assertFalse(true, "Could not delete AWB on " + screenName + " Page");
		}
		
	}
	
	/**
	 * Description : To verify AWB is not listed after deletion
	 * @param AWBno : AWB no deleted from the drop-off code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyAWBdeleted(String AWBno) throws InterruptedException, AWTException {
		String dynXpath ="//tr[contains(.,'" + AWBno + "')]";
		
		try{
			
			driver.findElement(By.xpath(dynXpath)).isDisplayed();
			Status = false;
			System.out.println("Failed to verify AWB is deleted on " + screenName + " Page");
			writeExtent("Fail", "Failed to verify AWB is deleted on " + screenName + " Page");
				
			
			
		}catch(Exception e){
			
			System.out.println("verified AWB is deleted on " + screenName + " Page");
			writeExtent("Pass", "verified AWB is deleted on " + screenName + " Page");
		}
		waitForSync(2);
	}
	
	/**
	 * Description : To save the token by clicking on close button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void saveTokenByClose() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_close;id", " Close button ",screenName);
		handleAlert("Accept",screenName);
		switchToFrame("contentFrame","TGC008");
	}
	
	/**
	 * Description : To save token or drop-off code also handles confirmation pop up
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void saveToken() throws InterruptedException, IOException {
  clickWebElement(sheetName, "btn_save;id", " Save button ",screenName);
  handleAlert("Accept", "Create Visit Declaration");
  switchToFrame("parentFrame");
  waitForSync(4);
  
 }
	
	
/**
 * Description : To enter multiple AWB Nos in AWB no text area field
 * @param shipmentPrefix : Shipment prefix of the AWBs e.g., 020
 * @param AWBnumbers : Array of multiple AWb numbers to be added to the token e.g., [12312311,12312322,12312333]
 * @throws InterruptedException
 */
public void enterMultipleAWBs(String shipmentPrefix, String [] AWBnumbers) throws InterruptedException{
  
  //enterValueInTextbox(sheetName, "inbx_multipleShipmentPrefix;id", shipmentPrefix, "Shipment Prefix", screenName);
  
  String multipleAWBnumber = "";
  for (int i=0; i < AWBnumbers.length ; i++ )
  {
   if(i != AWBnumbers.length-1){
    
    multipleAWBnumber = multipleAWBnumber + AWBnumbers[i] +",";
   }else{
    
    multipleAWBnumber = multipleAWBnumber + AWBnumbers[i];
   }
  }
  
  waitForSync(4);
  enterValueInTextbox(sheetName, "inbx_multipleAWBnumber;id", multipleAWBnumber, "multiple AWB numbers", screenName);
 }
 
/**
 * Description : To add multiple button and switch to window that pops up
 * @throws InterruptedException
 * @throws IOException 
 */
 public void clickaddMultipleandSwitchFrame() throws InterruptedException, IOException{
  
  clickWebElement(sheetName, "lnk_addMultipleAWB;xpath", " Add Multiple button ",screenName);
  waitForSync(2);
  switchToFrame("default");
  waitForSync(2);
  
 }
 
 /**
  * Description : To add all button and switch to parent frame
  * @throws InterruptedException
 * @throws IOException 
  */
 public void clickaddAllandSwitchToParentFrame() throws InterruptedException, IOException{
  
  clickWebElement(sheetName, "btn_addAll;id", "add All Button",screenName);
  switchToFrame("parentFrame");
  waitForSync(2);
  
 }
 
 /**
  * Description : To click on add all button
  * @throws InterruptedException
 * @throws IOException 
  */
 public void clickaddAll() throws InterruptedException, IOException{
  
  clickWebElement(sheetName, "btn_addAll;id", "add All Button",screenName);
  waitForSync(2);
  
 }
 
 /**
  * Description : To verify Error message displayed
  * @param locator : Locator for error message division
  * @param verificationstep : Verification being performed
  * @param ExpErrorMsg : Expected error message
  * @throws InterruptedException
  */
 public void verifyErrorMsg(String locator, String verificationstep , String ExpErrorMsg) throws InterruptedException{
  
  String ActErrorMsg= getElementText(sheetName, locator, " Error message ", screenName);
  
  if (ActErrorMsg.contains(ExpErrorMsg)) {
   System.out.println(ExpErrorMsg + " is verified on " + screenName + " Page");
   writeExtent("Pass", ExpErrorMsg + " is verified on " + screenName + " Page");
  } else {
   System.out.println(" Could not verify " + ExpErrorMsg + screenName + " Page");
   writeExtent("Fail", " Could not verify " + ExpErrorMsg + screenName + " Page");
  }
  
 }
 
 /**
  * Description : To fetch the generated drop-off code
  * @return Drop-Off Code
  * @throws InterruptedException
  */
 public String getDropOffCode() throws InterruptedException {
  String DropOffCode= getElementText(sheetName, "span_dropOffCode;xpath"," Drop off code ", screenName);
  
  return DropOffCode;
 }
 
 /**
  * Description : To check "Verify Quick Drop Off Execute button" is enabled or disabled 
  * @param status : expected status of the button e.g., Disabled, Enabled
  * @throws InterruptedException
  */
 public void VerifyQuickDropOffExecuteButton_Enable_Disabled(String status) throws InterruptedException{
  
  switch(status){
  
  case "Disabled" :
  verifyElementNotEnabled(sheetName, "btn_VerifyQuickDropOffExecute;id",
    "verify that Verify QuickDropOff Execute Button is disabled", screenName, "Verify QuickDropOff Execute Button");
  
  
  case "Enabled" :
   verifyElementEnabled(sheetName, "btn_VerifyQuickDropOffExecute;id",
     "verify that Verify QuickDropOff Execute Button is disabled", screenName, "Verify QuickDropOff Execute Button");
  
  }
 }
	
}
