package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class WarehouseShipmentEnquiry_WHS011 extends CustomFunctions {

	String sheetName = "WarehouseShipmentEnquiry_WHS011";
	String screenName = "Warehouse Shipment Enquiry: WHS011 ";
	String screenId="WHS011";


	public WarehouseShipmentEnquiry_WHS011(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	/**
	 * Description...	List Flight
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void listFlight(String carrierCode,String flightNumber, String flightDate) throws InterruptedException, AWTException, IOException {

		String sheetname="Generic_Elements";
		selectValueInDropdown(sheetName,"lst_flightDirection;name","Inbound","Flight Direction","VisibleText");
		enterValueInTextbox(sheetname, "inbx_carrierCode;xpath",
				data(carrierCode), "Carrier Code", screenId);
		enterValueInTextbox(sheetname, "inbx_flightNumber;xpath", data(flightNumber),
				"Flight Number", screenId);
		enterValueInTextbox(sheetname, "inbx_flightDate;xpath",
				data(flightDate), "Flight Date", screenId);
		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement(sheetname, "btn_listChildWindow;name", "List Button",screenId);
		waitForSync(4);

	}
	/**
	 * @Desc : Getting SU number
	 * @author A-9175
	 * @param awbno
	 */
	public void GetSuGeneratedOnRelocation(String awbno)
	{
		String loc=xls_Read.getCellValue(sheetName, "txt_Sunumber;xpath").replace("awbno",data(awbno));;
		String SUText=driver.findElement(By.xpath(loc)).getText().trim();
		map.put("SU", SUText);
	}

	/**
	 * @author A-8783
	 * Description...verify Column name
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyColumn(String[] columnName) throws InterruptedException, Exception{
		int i = 0;
		int flag=0;
		try {
		String locator=xls_Read.getCellValue(sheetName,"table_wareHouseColumn;xpath");
		List<WebElement> column = driver.findElements(By.xpath(locator));
		 for( i=0;i<columnName.length;i++){
			flag=0;
			 for(WebElement col:column) {
			 String actText = col.getText();
			 System.out.println(actText);
			 if(actText.equals(columnName[i])) {
				
				 writeExtent("Pass", "Verified that the column " + columnName[i] + " is present in the table");
				 break;
			 }
			 else {
				 flag+=1;
			 }
			 	
		 }
			 if(flag==column.size()) {
				 writeExtent("Fail", "Failed to verify that the column " + columnName[i] + " is present in the table");

			 }

	 }
				
		}
		catch(Exception e) {
			 writeExtent("Fail", "Failed to verify if columns are present");
		}
	}

	/**
	 * @author A-8783
	 * Description...verify Column name not present
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyColumnNotPresent(String[] columnName) throws InterruptedException, Exception{
		int i = 0;
		int flag=0;
		try {
		String locator=xls_Read.getCellValue(sheetName,"table_wareHouseColumn;xpath");
		List<WebElement> column = driver.findElements(By.xpath(locator));
		 for( i=0;i<columnName.length;i++){
			flag=0;
			 for(WebElement col:column) {
			 String actText = col.getText();
			 System.out.println(actText);
			 if(actText.equals(columnName[i])) {
				
				 writeExtent("Fail", "The column " + columnName[i] + " is  present in the table");
				 break;
			 }
			 else {
				 flag+=1;
			 }
			 	
		 }
			 if(flag==column.size()) {
				 writeExtent("Pass", "Verified that the column  " + columnName[i] + " is not present in the table");

			 }

	 }
				
		}
		catch(Exception e) {
			 writeExtent("Fail", "Failed to verify if columns are not present");
		}
	}

    /**
	 * Description... Click SU Relocation Button
	 * @throws IOException
	 * @throws AWTException
	 */
	public void clickSURelocation() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_SUreloc;name", "SU Relocation Button", screenName);
		waitForSync(2);
	}
    /**
	 * Description... Enter SU relocation details and save
	 * @param location
	 * @throws Exception
	 */
	public void SURelocationDetails(String location) throws Exception
	{
		waitForSync(3);	
		switchToWindow("storeParent");
		switchToWindow("child");
		//Enter Destination Location in new window
		enterValueInTextboxByJS(sheetName, "inbx_location;xpath", data(location), "Destination Location", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "btn_saveRelocation;id", "save button", screenName);
		waitForSync(3);
		switchToFrame("default");
		clickWebElement(sheetName, "btn_dialogpopup;xpath", "ok button", screenName);
	
	    switchToWindow("getParent");
	}

	/**
	 * @author A-9844
	 * Description...verify fileds-Days
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyFieldDays() throws InterruptedException, Exception{
		
		//verify Days
		 String locatorDays= xls_Read.getCellValue(sheetName, "inbx_days;xpath");
		 if((driver.findElements(By.xpath(locatorDays)).size()>0)){
			 writeExtent("Pass","Successfully verified Days field on "+screenName);
		 }
		 
		 else{
			 writeExtent("Fail","Failed to  verify Days field on "+screenName); 
		 }
		 
		 
	}

	/**
	 * @author A-9844
	 * Description...verify fileds-Hrs
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyFieldHrs() throws InterruptedException, Exception{
		
		//verify Hrs
		 String locatorHrs= xls_Read.getCellValue(sheetName, "inbx_hours;xpath");
		 if((driver.findElements(By.xpath(locatorHrs)).size()>0)){
			 writeExtent("Pass","Successfully verified Hrs field on "+screenName);
		 }
		 
		 else{
			 writeExtent("Fail","Failed to  verify Hrs field on "+screenName); 
		 }
		 
		 
	}
	/**
	 * @author A-9844
	 * Description...verify fileds-Mins
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyFieldMins() throws InterruptedException, Exception{
		
		//verify Mins
		 String locatorMins= xls_Read.getCellValue(sheetName, "inbx_minutes;xpath");
		 if((driver.findElements(By.xpath(locatorMins)).size()>0)){
			 writeExtent("Pass","Successfully verified Mins field on "+screenName);
		 }
		 
		 else{
			 writeExtent("Fail","Failed to  verify Mins field on "+screenName); 
		 }
		 
		 
	}
    /**
	   * @author A-9847
	   * @des To verify Warehouse Checkin Time
	   * @param verfCols
	   * @param actVerfValues
	   * @param pmKey
	   * @throws Exception 
	   * @throws IOException
	   */

	  public void verifyWarehouseCheckinTime(String messageSentTime,int count,String awbno) throws Exception

      {

		
		  String loc=xls_Read.getCellValue(sheetName, "table_checkInTime;xpath").replace("*",data(awbno));
          String checkInTime=createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "")+" "+driver.findElement(By.xpath(loc)).getText().split(" ")[1];
          System.out.println(checkInTime);

          boolean flag=false;
          
          for(int i=0;i<=count;i++)

          {
                 if(checkInTime.equals(createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "")+" "+timeCalculation(messageSentTime, "HH:mm:ss","SECOND",-i)))

                 {
                        flag=true;
                        break;
                 }
          }

          if(flag==true)

                 writeExtent("Pass", "Successfully verified Warehouse Check-in time "+checkInTime+" on" + screenName+" for "+data(awbno));

          else

                 writeExtent("Fail", "Failed to verify Warehouse Check-in time  "+checkInTime+" on"+ screenName+" for "+data(awbno));
    }


	/**
	 * @author A-9844
	 * Description... Click Edit Button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickEditBtn() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_edit;xpath", "Edit Button", screenName);
		waitForSync(3);
	}
	/**
	 * @author A-9847
	 * @Desc To enter the SU
	 * @param su
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	
	public void enterSU(String su) throws InterruptedException, AWTException {
		waitTillScreenload(sheetName, "inbx_su;xpath", "SU", screenName);
		enterValueInTextbox(sheetName, "inbx_su;xpath",data(su), "SU", screenId);

		
	}
	
	/**
	 * @author A-9847
	 * @Desc To check the Also Show Empty SU checkbox
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickAlsoShowEmptySUCheckbox() throws InterruptedException, AWTException, IOException {
		
		String locatorValue=xls_Read.getCellValue(sheetName, "chkbox_emptySU;xpath"); 
		if(!driver.findElement(By.xpath(locatorValue)).isSelected())
			clickWebElement(sheetName, "chkbox_emptySU;xpath", "Also Show Empty SU Checkbox", screenName);

	}
	
	/**
	 * Desc : Verifying Table Details
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyTable() throws InterruptedException, AWTException {
		String locator = xls_Read.getCellValue(sheetName, "table_warehouseDeatils;xpath");
		//List<WebElement> tableRows=new ArrayList<WebElement>();
		int rowCount=driver.findElements(By.xpath(locator)).size();
		System.out.println(rowCount);
		try{
			if(rowCount==0)
			{
				writeExtent("Pass", "No shipments Displayed "+screenName);
			}}
		catch(Exception e){
			writeExtent("Fail", "shipments Still Displayed "+screenName);
		}
	}
	/**
	 * @author A-9844
	 * Description... Enter from date
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterFromDate(String fromDate) throws InterruptedException, AWTException {

		enterValueInTextbox(sheetName, "inbx_fromDate;xpath",data(fromDate), "AWB Prefix", screenId);

	}
	/**
	 * @author A-9844
	 * Description... Enter to date
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterToDate(String toDate) throws InterruptedException, AWTException {

		enterValueInTextbox(sheetName, "inbx_toDate;xpath",data(toDate), "AWB Prefix", screenId);

	}
	/**
	 * @author A-9847
	 * To verify the Checkin Type Dropdown
	 */
	public void verifycheckInTypeDropDown(){

		//Verifying Checkin Type drpdown with filtervalues
		String loc= xls_Read.getCellValue(sheetName, "drpdn_checkInType;xpath");
		List<WebElement> vals= driver.findElements(By.xpath(loc));
		for(int i=0;i<vals.size();i++){
			String val=vals.get(i).getText();
			if(val.equals("Dwell Time") || val.equals("Warehouse Checkin"))
				writeExtent("Pass","Successfully verified Checkin Type dropdown with filter value: "+val+ " on "+screenName);
			else
				writeExtent("Fail","Fail to verify the Checkin Type dropdown filter values on "+screenName);
		}

		//Checking the Default value of Checkin Type as Dwell Time
		String loc1=xls_Read.getCellValue(sheetName, "drpdn_checkType;xpath");
		String defval=driver.findElement(By.xpath(loc1)).getText();
		verifyScreenText(screenName, "Dwell Time", defval, "Default Checkin Type", "Default Checkin Type");

	}
	/**
	 * @author A-9847
	 * @desc To select Checkin Type values
	 * @param opt
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectCheckinType(String opt) throws InterruptedException, IOException{
		//values 1)WHSCHK for warehouse checkin  2)LOCCHK for dwell time
		selectValueInDropdown(sheetName,"drpdn_checkType;xpath",data(opt),"Checkin Type","Value"); 
	}
	/**
	 * @author A-8783
	 * Desc- Click on column configurator button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickColumnConfig() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_clmnConfig;id", "Column configurator",screenId);
	}
	/**
	 * @author A-8783
	 * Desc - to check if all columns are checked
	 */
	public void verifyColumnChkboxChecked() {

		int flag=0;
		String locator = xls_Read.getCellValue(sheetName, "chkbox_columnConfig;xpath");
		List<WebElement> columnConfigChkboxes = driver.findElements(By.xpath(locator));
		int count = columnConfigChkboxes.size();
		for(WebElement c:columnConfigChkboxes) {
			if(c.isSelected()) {
				flag+=1;
			}

		}

		if(flag==count) {
			writeExtent("Pass", "All checkboxes are selected in column configurator");
		}
		else {
			writeExtent("Fail", "All checkboxes are not selected");
		}


	}

	/**
	 * @author A-8783
	 * Desc - Unselect the columns
	 * @param columnName
	 */
	public void unselectColumns(String[] column) {
		try {
			String locator=xls_Read.getCellValue(sheetName,"chkbox_columnCongifName;xpath");
			for(int i=0;i<column.length;i++) {
				String	locatorNew=locator.replace("columnName",column[i] );
				waitForSync(1);
				WebElement columnConfigChkbox = driver.findElement(By.xpath(locatorNew));
				if(columnConfigChkbox.isSelected()) {
					columnConfigChkbox.click();
					waitForSync(2);
					writeExtent("Pass" , "Unselected the column" + column[i]);
				}
				else {
					writeExtent("Info" , column[i] + " is already unselected");
				}

			}

		}
		catch (Exception e) {
			writeExtent("Fail","Could not unselect the columns");
		}
	}

	/**
	 * @author A-8783
	 * Desc - Unselect the columns
	 * @param columnName
	 */
	public void selectColumns(String[] column) {
		try {
			String locator=xls_Read.getCellValue(sheetName,"chkbox_columnCongifName;xpath");
			waitForSync(1);
			for(int i=0;i<column.length;i++) {
				String	locatorNew=locator.replace("columnName",column[i] );
				WebElement columnConfigChkbox = driver.findElement(By.xpath(locatorNew));
				if(!columnConfigChkbox.isSelected()) {
					columnConfigChkbox.click();
					waitForSync(2);
					writeExtent("Pass" , "Selected the column" + column[i]);
				}
				else {
					writeExtent("Info" , column[i] + " is already selected");
				}

			}

		}
		catch (Exception e) {
			writeExtent("Fail","Could not select the columns");
		}
	}

	/**
	 * @author A-8783
	 * Desc - Click on column configurator save button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveColmnConfig() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_saveCol;id", "Save Button",screenId);
	}

	/**
	 * @author A-9847
	 * @des To verify Warehouse Checkin Time
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws IOException
	 */

	public void verifyWarehouseCheckinTime(int verfCols[], String actVerfValues[],String pmKey) throws IOException{

		verify_tbl_records_multiple_cols(sheetName, "table_warehouseDeatils;xpath", "//td", verfCols, data(pmKey),
				actVerfValues);  

	}
	/**
	 * Desc : Verifying footer message
	 * @author A-9175
	 * @param status
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyInfoBar(String status) throws InterruptedException, AWTException {
		getTextAndVerify(sheetName, "infoFooter;xpath", " Footer text ", screenName, " Footer text details ",
				data(status), "contains");
		waitForSync(3);
	}


	/**
	 * Description... Click AWB checkBox
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickAWBcheckBox() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "chk_AWBcheck;name", "AWB checkbox", screenName);
		waitForSync(3);
	}
	/**
	 * Description... Enter AWB details
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterAWBdetails() throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_AWBpre;name",data("prop~stationCode"), "Station code", screenId);
		enterValueInTextbox(sheetName, "inbx_AWBno;name",data("prop~AWBNo"), "AWB No", screenId);
		waitForSync(3);
	}
	
	/**
	 * Description... Enter AWB details
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterAWBdetails(String awbPrefix, String awbNo) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_AWBpre;name",data(awbPrefix), "AWB Prefix", screenId);
		enterValueInTextbox(sheetName, "inbx_AWBno;name",data(awbNo), "AWB No", screenId);
		waitForSync(3);
	}

	/**
	 * Description... Click List Button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickList() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_List;name", "List Button", screenName);
		waitForSync(3);
	}
	
	
	/**
	 * Description... Click Shipment Relocation Button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickShipmentRelocation() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_SHPreloc;name", "List Button", screenName);
		waitForSync(3);
	}
	
	
	public void verifySUDetails(int verfColsSU[], String actVerfValuesSU[], String pmyKeySU)
			throws InterruptedException, IOException {
		
		
		verify_tbl_records_multiple_cols(sheetName, "table_SUNumber;xpath", "//td", verfColsSU, pmyKeySU,
				actVerfValuesSU);
		
             }
	
	/**
	 * Description... Verify Warehouse Details
	 * @param verfCols
	 * @param actVerfValues
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyWarehouseDetails(int verfCols[], String actVerfValues[]) throws InterruptedException, IOException {

		verify_tbl_records_multiple_cols(sheetName, "tbl_warehouseEnquiry;xpath", "//td", verfCols, data("AWBNo"),
				actVerfValues);   

	}
	/**
	 * Description... Verify Warehouse Details
	 * @param verfCols
	 * @param actVerfValues
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyWarehouseDetailsWithPmKey(int verfCols[], String actVerfValues[],String pmKey) throws InterruptedException, IOException {

		verify_tbl_records_multiple_cols(sheetName, "tbl_warehouseEnquiry;xpath", "//td", verfCols, data(pmKey),
				actVerfValues);   

	}

	public void verifyTitle()
	{
		String title=getAttributeWebElement(sheetName, "",
				"WHS011 Title", "title", screenName);
		System.out.println(title);

		if(title.equals("Warehouse Shipment Enquiry"))
		{
			customFunction.onPassUpdate(screenName, "Title verification", "Title should be Warehouse Shipment Enquiry", "Title is "+title, "Title verification");
		}
		else
		{
			customFunction.onFailUpdate(screenName, "Title verification", "Title should be Warehouse Shipment Enquiry", "Title is "+title, "Title verification");	
		}

	}

	/**
	 * Description...List Flight
	 * @param option
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void listFlight2(String option,String carrierCode,String flightNumber, String flightDate) throws InterruptedException, AWTException, IOException {

		String sheetname="Generic_Elements";
		selectValueInDropdown(sheetName,"lst_flightDirection;name",option,"Flight Direction","VisibleText");
		enterValueInTextbox(sheetname, "inbx_carrierCode;xpath",
				data(carrierCode), "Carrier Code", screenId);
		enterValueInTextbox(sheetname, "inbx_flightNumber;xpath", data(flightNumber),
				"Flight Number", screenId);
		enterValueInTextbox(sheetname, "inbx_flightDate;xpath",
				data(flightDate), "Flight Date", screenId);
		waitForSync(2);
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement(sheetname, "btn_listChildWindow;name", "List Button",screenId);
		waitForSync(4);

	}


}
