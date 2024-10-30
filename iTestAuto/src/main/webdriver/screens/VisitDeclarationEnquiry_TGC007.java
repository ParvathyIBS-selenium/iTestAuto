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

public class VisitDeclarationEnquiry_TGC007 extends CustomFunctions
{

	public VisitDeclarationEnquiry_TGC007(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="VisitDeclarationEnquiry_TGC007";
	public String screenName="VisitDeclarationEnquiry_TGC007";
	
	/**
	 * Description : Enters value of token no for listing
	 * @param Token : Token no / Quick drop-off/pickup code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterToken(String Token) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_Token;id", Token, " Token ", screenName);
			
	}
	
	/**
	 * Description : Enters value of Vehicle Number 
	 * @param VehicleNumber : vehicle number for which drop off code has to be created
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterVehicleNumber(String VehicleNumber) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_vehicleNumber;id", VehicleNumber, " Vehicle Number ", screenName);
			
	}
	
	/**
	 * Description : Enters from date filter
	 * @param fromDate : start date for which drop-off code/ tokens to be listed
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterfromDate(String fromDate) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_fromDate;id", fromDate, " From Date ", screenName);
			
	}
	
	/**
	 * Description : Enters to date filter
	 * @param toDate : end date for which drop-off code/ tokens to be listed
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void entertoDate(String toDate) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_toDate;id", toDate, " To Date ", screenName);
			
	}
	
	/**
	 * Description : To select token status from dropdown
	 * @param tokenStatus : status of token e.g., draft
	 * @throws InterruptedException
	 */
	public void selectTokenStatus(String tokenStatus) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_tokenStatus;id", tokenStatus, " Token Status ", "VisibleText");
	}
	
	/**
	 * Description : To provide id type for the driver
	 * @param IdType : e.g., Driving license
	 * @throws InterruptedException
	 */
	public void selectIdType(String IdType) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_idType;id", IdType, " Id Type", "VisibleText");
	}
	
	/**
	 * Description : To provide id details/number
	 * @param idDetails : details or number for id provide e.g., driving license no G67171
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterIdDetails(String idDetails) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_idDetails;id", idDetails, "Id Details", screenName);
			
	}
	
	/**
	 * Description : To select the truck type for the shipment
	 * @param TruckType : Truck type in which shipment can be taken e.g, All
	 * @throws InterruptedException
	 */
	public void selectTruckType(String TruckType) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_truckType;id", TruckType, " Truck Type ", "VisibleText");
	}
	
	/**
	 * Description : To provide dock type of the truck
	 * Non mandatory field
	 * @param TruckDockType
	 * @throws InterruptedException
	 */
	public void selectTruckDockType(String TruckDockType) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_truckDockType;id", TruckDockType, " Truck Dock Type ", "VisibleText");
	}
	
	/**
	 * Description : To provide dock for the truck
	 * Non mandatory field
	 * @param Dock
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterDock(String Dock) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_Dock;id", Dock, " Dock ", screenName);
	}
			
	/**
	 * Description : To select purpose of visit 
	 * @param purposeOfVisit : e.g., Pickup
	 * @throws InterruptedException
	 */
	public void selectPurposeOfVisit(String purposeOfVisit) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_purposeOfVisit;id", purposeOfVisit, " Purpose Of Visit ", "VisibleText");
	}
	
	/**
	 * Description : To click on list button
	 * @throws IOException 
	 */
	public void clickList() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_List;id", " List Button ",screenName);
	}
	
	/**
	 * Description : To click on clear button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickClear() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Clear;id", " Clear Button ",screenName);
	}
	
	/**
	 * Description : To verify Token/Drop-off pickup code status
	 * @param dropOffCode : Drop-off code generated for the shipment
	 * @param expTokenStatus : Expected Drop-off code status
	 * @param expTokenStatus2 : Alternate status for Drop-off code, if only one status required the give this parameter as ""
	 * @throws InterruptedException
	 */
	public void verifyTokenStatus(String dropOffCode, String expTokenStatus , String expTokenStatus2) throws InterruptedException {
		
		String dynXpath = xls_Read.getCellValue(sheetName, "tbl_VisitDetails;xpath") + "//input[@value='"+ dropOffCode + "']/../..//td[17]";
		String actTokenStatus = driver.findElement(By.xpath(dynXpath)).getText();
		
		if (actTokenStatus.contains(expTokenStatus) || actTokenStatus.contains(expTokenStatus2))
		{
			System.out.println(" Verified actual token status is "+ actTokenStatus + " on " + screenName + " Page");
			writeExtent("Pass",  " Verified actual token status is "+ actTokenStatus + " on " + screenName + " Page");
		}else{
			
			System.out.println(" Actual token status is "+ actTokenStatus + " on " + screenName + " Page where as it should be" + expTokenStatus +"or"+ expTokenStatus2);
			writeExtent("Fail",  " Actual token status is "+ actTokenStatus + " on " + screenName + " Page where as it should be" + expTokenStatus +"or"+ expTokenStatus2);
			Assert.assertFalse(true, " Actual token status is "+ actTokenStatus + " on " + screenName + " Page where as it should be" + expTokenStatus +"or"+ expTokenStatus2);
		}
		

		
	}
	
	/**
	 * Description : To list the drop-off pick up code on the screen
	 * @param Token : drop-off pick up code number
	 * @param fromDate : start date for the period to be filtered
	 * @param toDate : end date for the period to be filtered
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void listDropOffCode(String Token,String fromDate , String toDate) throws InterruptedException, AWTException, IOException{
		
		enterToken(Token);
		enterfromDate(fromDate);
		entertoDate(toDate);
		clickList();
		
	}
	
}
