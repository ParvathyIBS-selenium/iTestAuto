/**
 * @author A-8468/A-8470
 */
package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class PreCheckList_OPR346 extends CustomFunctions {

	public PreCheckList_OPR346(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "PreCheckList_OPR346";
	public String ScreenName = "PreCheck List / Screen : OPR346";

	/**
	 * Description : To list AWb by providing AWB no and precheck status
	 * @param AWBno : AWB no for the shipment
	 * @param stationCode : shipment prefix e.g., 020
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void ListAWB(String AWBno, String stationCode) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_awbNumberPrefix;name", data(stationCode), "Satation Code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;name", data(AWBno), "AWB Number", ScreenName);
		waitForSync(2);
		selectValueInDropdown(sheetName,"lst_precheckStatus;name","Select","Precheck status dropdown","VisibleText");
		clickWebElement(sheetName, "btn_List;name", "List Button", ScreenName);
		waitForSync(5);

	}

	/**
	 * Description : To verify precheck status of the AWB
	 * @param AWBno : AWB no for the shipment
	 * @param PrecheckStatus : Expected precheck status e.g, On-hold, Success, Failed, Pending
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyPrecheckStatus(String AWBno, String PrecheckStatus) throws InterruptedException, AWTException {
		String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
		String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[11]//label";
		WebElement img = driver.findElement(By.xpath(dynxpath));
		String status = img.getAttribute("data-status");

		switch (PrecheckStatus) {
		case "On-hold":

			if (status.equals("H")) {
				System.out.println("Precheck status is " + PrecheckStatus);
				writeExtent("Pass", "Precheck status is " + PrecheckStatus);
			} else {
				System.out.println("Precheck status is not " + PrecheckStatus);
				writeExtent("Fail", "Precheck status is not " + PrecheckStatus);
			}
			break;

		case "Success":

			if (status.equals("S")) {
				System.out.println("Precheck status is " + PrecheckStatus);
				writeExtent("Pass", "Precheck status is " + PrecheckStatus);
			} else {
				System.out.println("Precheck status is not " + PrecheckStatus);
				writeExtent("Fail", "Precheck status is not " + PrecheckStatus);
			}
			break;
		case "Failed":

			if (status.equals("F")) {
				System.out.println("Precheck status is " + PrecheckStatus);
				writeExtent("Pass", "Precheck status is " + PrecheckStatus);
			} else {
				System.out.println("Precheck status is not " + PrecheckStatus);
				writeExtent("Fail", "Precheck status is not " + PrecheckStatus);
			}
			break;

		case "Pending":

			if (status.equals("P")) {
				System.out.println("Precheck status is " + PrecheckStatus);
				writeExtent("Pass", "Precheck status is " + PrecheckStatus);
			} else {
				System.out.println("Precheck status is not " + PrecheckStatus);
				writeExtent("Fail", "Precheck status is not " + PrecheckStatus);
			}
			break;
		}

	}

	/**
	 * Description : To select precheck status in the filter
	 * @param status : status of precheck to be selected
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void selectPrecheckStatus(String status) throws InterruptedException, AWTException {

		selectValueInDropdown(sheetName, "lst_precheckStatus;name", status, "Precheck Status", "VisibleText");
		waitForSync(2);
	}

	/**
	 * Description : To click on precheck validate button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickPrecheckRevalidate() throws InterruptedException, AWTException, IOException {

  clickWebElement(sheetName, "btn_precheckRevalidate;name", "Precheck Revalidate Button", ScreenName);
  waitForSync(5);
 }

	/**
	 * Description : To verify eData status
	 * @param AWBno : AWB no for the shipment
	 * @param eDataStatus : Expected eData status e.g., Success
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyeDataStatus(String AWBno, String eDataStatus) throws InterruptedException, AWTException {
		String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
		String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[10]//label";
		WebElement img = driver.findElement(By.xpath(dynxpath));
		String status = img.getAttribute("class");

		switch (eDataStatus) {
		case "Success":

			if (status.contains("greens status")) {
				System.out.println("eData status is " + eDataStatus);
				writeExtent("Pass", "eData status is " + eDataStatus);
			} else {
				System.out.println("eData status is not " + eDataStatus);
				writeExtent("Fail", "eData status is not " + eDataStatus);
			}

		}

	}

	/**
	 * Description : To list AWB based on filters without giving AWB no
	 * @param PrecheckStatus : Precheck status of the AWB
	 * @param StartDate : Start date for the period for which AWB are to be listed
	 * @param EndDate : End date for the period for which AWB are to be listed
	 * @param BookingStatus : Booking status of AWB e.g., Confirmed, Queued
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void List(String PrecheckStatus,String StartDate,String EndDate,String BookingStatus) throws InterruptedException, AWTException, IOException 
	{		
		
		selectValueInDropdown(sheetName,"lst_precheckStatus;name",data(PrecheckStatus),"Precheck status dropdown","VisibleText");
		enterValueInTextbox(sheetName, "inbx_FromDate;name", "+1","Start date", ScreenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_ToDate;name", "+1","End date", ScreenName);
		keyPress("TAB");
		//selectValueInDropdown(sheetName,"lst_BookingStatus;name",data(BookingStatus),"Booking status dropdown","VisibleText");
		selectOptionInList(sheetName, "btn_BookingStatus;id","lst_BookingOptions;xpath", data(BookingStatus), "Booking status dropdown");
		
		
		waitForSync(2);
		clickWebElement(sheetName, "btn_List;name", "List Button", ScreenName);
		waitForSync(5);
		      
	}
	
	/**
	 * Description : To click on check of the row where AWB is listed
	 * @param AWBno : AWb no which has to be selected
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void clickAWBcheckbox(String AWBno) throws InterruptedException, AWTException 
	{  
  String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
  String dynxpath = xpath + "//a[contains(.,'" + AWBno + "')]/../../..//input";
  Actions a1 = new Actions(driver);
  
  try{
   
    ele = driver.findElement(By.xpath(dynxpath));
    a1.moveToElement(ele).click().build().perform();
   
   }catch(Exception e){
    
   System.out.println("Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
   writeExtent("Fail", "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
   Assert.assertFalse(true, "Could not click on" + AWBno + "checkox on " + ScreenName + " Page");
    
   }
  
        
 }

	/**
	 * Description : To click on details button which will navigate to precheck details page
	 * @throws Exception
	 */
	public void clickDetails() throws Exception 
	{		
		
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR346");
		
		clickWebElement(sheetName, "btn_Details;name", "Details Button", ScreenName);
		waitForSync(5);
		
		      
	}
	
	/**
	 * Description : To click on assign priority button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickAssignPriority() throws InterruptedException, AWTException, IOException 
	{		
		clickWebElement(sheetName, "btn_AssignPriority;name", "Assign Priority Button", ScreenName);
		waitForSync(5);		
		      
	}
	
	
	/**
	 * Description : To verify whether hand symbol is displayed or not
	 * @param AWBno : AWB no for the shipment
	 * @param displayed_notdisplayed : Whether hand symbol should be displayed or not e.g., Displayed, Not Displayed
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyHandSymbol(String AWBno, String displayed_notdisplayed) throws InterruptedException, AWTException 
       {             
       //"//table[@id='awbDetailtable']//tr[contains(.,'43324411')]//td[1]//img[contains(@src,'thumb')]"
              
              
              String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
              String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[1]//img[contains(@src,'thumb')]";

              switch(displayed_notdisplayed){
              
              case "Displayed":
              if (driver.findElement(By.xpath(dynxpath)).isDisplayed()) {
                 System.out.println("Hand symbol is displayed");
                     writeExtent("Pass", "Hand symbol is displayed");
              } else {
                     System.out.println("Hand symbol is not displayed");
                     writeExtent("Fail", "Hand symbol is not displayed");
              }
              break;
          
              case "Not Displayed":
                     verifyElementNotDisplayed(dynxpath,"Verification of hand symbol not displayed", ScreenName, "Hand Symbol");
                 break;
                     }
       }
	       

	/**
	 * Description : To select row for which AWB details to be displayed
	 * @param pmyKey : Primary key for the row (AWB no) e.g.,020-12312311
	 * @param sheetName : Sheet name in Locators.xls
	 * @param locatorTableRow : Locator for table rows
	 * @param locatorEle : Locator for element to be selected which will be concatenated to dynamic xpath
	 * @param loopCount : no of times loop has to run in order to find the row
	 * @throws InterruptedException
	 */
	public void selectAWBDetails(String pmyKey, String sheetName, String locatorTableRow, String locatorEle, int loopCount)
			throws InterruptedException {
		selectTableRecord(pmyKey, sheetName, locatorTableRow, locatorEle, loopCount);
		waitForSync(2);

	}

	/**
	 * Description : To verify eData information for the AWB
	 * @param AWBno : AWB no for the shipment
	 * @param eDataStatus : Expected eData status
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyeDataInfo(String AWBno, String eDataStatus) throws InterruptedException, AWTException {
		String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
		String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[10]";
		WebElement img = driver.findElement(By.xpath(dynxpath));
		img.click();
		
		String xpath2 = xls_Read.getCellValue(sheetName, "div_eDataInfo;xpath");
		String dynxpath2 = xpath2 + "//td[3]//i";
		WebElement img2 = driver.findElement(By.xpath(dynxpath2));
		
		String status = img2.getAttribute("class");
		System.out.println(status);

		switch (eDataStatus) {
		case "Success":

			if (status.contains("tick-full-green block")) {
				System.out.println("eData info is " + eDataStatus);
				writeExtent("Pass", "eData info is " + eDataStatus);
			} else {
				System.out.println("eData info is not " + eDataStatus);
				writeExtent("Fail", "eData status is not " + eDataStatus);
			}
			
			break;

		}
		
	}
	
	/**
	 * Description : To verify AWB no is not listed for precheck
	 * @param AWBno : AWB no for shipment
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyAWBnotListed(String AWBno) throws InterruptedException, AWTException 
 {  
  String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
  String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[1]";
  
  try{
   
  if(driver.findElement(By.xpath(dynxpath)).isDisplayed())
  {
   System.out.println(AWBno + " is listed on " + ScreenName + " Page");
   writeExtent("Fail",  AWBno + " is listed on " + ScreenName + " Page");
   Assert.assertFalse(true, AWBno + " is listed on " + ScreenName + " Page");
  }
   
   
  }catch(Exception e){
   System.out.println(AWBno + " is not listed on " + ScreenName + " Page");
   writeExtent("Pass",  AWBno + " is not listed on " + ScreenName + " Page");
   
  }
  }
	
	/**
	 * Description : To click on export to excel button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void exportToExcel() throws InterruptedException, AWTException, IOException 
	{		
		clickWebElement(sheetName, "btn_exportToExcel;id", "Export to Excel Button", ScreenName);
		waitForSync(5);		      
	}
	
	/**
	 * Description : To verify Drop-off/Pickup Code code listed against AWb no
	 * @param AWBno : AWB no for the shipment
	 * @param Dropoff_PickupCode : Drop off code created for the shipment
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyDropoff_PickupCode(String AWBno, String Dropoff_PickupCode) throws InterruptedException, AWTException {
		String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
		String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[3]//label";
		
		String dropOffCode = driver.findElement(By.xpath(dynxpath)).getText();
		
		if (dropOffCode.equals(Dropoff_PickupCode)) {
			System.out.println("Drop off_Pickup Code is " + Dropoff_PickupCode);
			writeExtent("Pass", "Drop off_Pickup Code is " + Dropoff_PickupCode);
		} else {
			System.out.println("Drop off_Pickup Code is not " + Dropoff_PickupCode);
			writeExtent("Fail", "Drop off_Pickup Code is not " + Dropoff_PickupCode);
		}

		
	}
	
	/**
	 * Description : To provide origin and destination in the filter
	 * @param Origin : Origin for flight/shipment
	 * @param Destination : Destination for flight/shipment
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterOrigin_Destination(String Origin,String Destination) throws InterruptedException, AWTException 
	{		
			
		enterValueInTextbox(sheetName, "inbx_Origin;name", data(Origin),"Origin", ScreenName);
		enterValueInTextbox(sheetName, "inbx_Destination;name", data(Destination),"Destination", ScreenName);
		      
	}

	
	/**
	 * Description : To verify AWB no is not listed for precheck
	 * @param AWBno : AWB no for shipment
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyAWBIsNotListed(String AWBno) throws InterruptedException, AWTException 
	{		
		String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetail;xpath");
		String dynxpath = xpath + "[contains(.,'" + AWBno + "')]//td[1]";
		
		try{
			
			driver.findElement(By.xpath(dynxpath)).isDisplayed();
			Status = false;
			System.out.println(AWBno + " is listed on " + ScreenName + " Page");
			writeExtent("Fail",  AWBno + " is listed on " + ScreenName + " Page");
			Assert.assertFalse(true, AWBno + " is listed on " + ScreenName + " Page");
		} catch (Exception e) {
			
			System.out.println(AWBno + " is not listed on " + ScreenName + " Page");
			writeExtent("Pass",  AWBno + " is not listed on " + ScreenName + " Page");
		}
				      
	}
	
	/**
	 * Description : To click "Yes" button in the pop up
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYesButton() throws InterruptedException, IOException
       {      
              switchToFrame("default");
              clickWebElement("Generic_Elements", "btn_yes;xpath","yes Button", ScreenName);
       
       }
       

	/**
	 * Description : To click "No" button in the pop up
	 * @throws InterruptedException
	 * @throws IOException 
	 */
       public void clickNoButton() throws InterruptedException, IOException
       {      
              switchToFrame("default");
              clickWebElement("Generic_Elements", "btn_no;xpath","no Button", ScreenName);
       
       }
             
       
}

