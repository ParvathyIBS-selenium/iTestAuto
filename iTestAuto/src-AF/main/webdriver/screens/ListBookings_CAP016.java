package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ListBookings_CAP016 extends CustomFunctions {
	
	String sheetName = "ListBookings_CAP016";
	String screenName = "List Bookings : CAP016";
	

	public ListBookings_CAP016(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public void enterAwbNumber(String awbNumber) throws InterruptedException, AWTException {

		waitForSync(4);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(awbNumber), "awbNumber", screenName);
		waitForSync(1);
	}
	
	public void selectBKGStatus(String bkgStatus)
	{
		selectValueInDropdown(sheetName,"lst_bookingStatus;id",data(bkgStatus),"Booking Status","VisibleText");
		
	}
	public void listDetails() throws Exception {

		clickWebElement(sheetName, "btn_list;id", "List details", screenName);
		
		waitForSync(2);

	}
	public void expandShipmentDetails() throws Exception {

		clickWebElement(sheetName, "btn_expand;xpath", "Expand shipment details", screenName);
		
		waitForSync(2);

	}
	
	public void checkAWB() throws InterruptedException, AWTException {

	
		waitForSync(2);
		verifyElementDisplayed(sheetName, "chk_bookingData;name",
			"Booking Status", screenName,"Booking Data Check box");

	}

	
	public void verifyAwbDetails(int verfCols[],String actVerfValues[],String pmKey
			) throws InterruptedException, IOException {
		waitForSync(4);
		//int verfCols[]={4,5,6,10,11,12,13};
		//String[] actVerfValues={data("Date"),data("Origin"),data("Destination"),"FC",data("ShipmentPieces"),data("ShipmentWeight"),data("ShipmentVolume")};
		verify_tbl_records_multiple_cols(sheetName, "table_bookingDetails;xpath", "//label", verfCols, pmKey, actVerfValues);
	}

	public void listFlight(String FlightNumber, String flightDate) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightFrom;xpath", data(flightDate), "From Flight Date", screenName);
		enterValueInTextbox(sheetName, "inbx_flightTo;xpath", data(flightDate), "To Flight Date", screenName);
		
		keyPress("TAB");
		clickWebElement("Generic_Elements", "btn_list2;name", "List", screenName);
		Thread.sleep(3000);		
	}
	public void verifyNoContent()
	{
		By element = getElement("Generic_Elements", "txt_errorText;xpath");
		String msg = driver.findElement(element).getText();
		
		
		
		if(msg.contains("No results found for the specified criteria."))
		{
		verifyScreenText(sheetName, "No content Present", "No content Present","ULD Booking Not displayed",screenName);
		
		}
		else
		{
			verifyScreenText(sheetName, "No content Present", "Content Present","ULD Booking displayed",screenName);
		}
	}
	public void verifyULDBkgID(String UldBkgID)
	{
		waitForSync(5);
		
		/*By element=getElement("ListBookings_CAP016", "ListBkgTableTab;xpath");
		WebElement scroll=driver.findElement(element);
		driver.findElement(element).click();
		scroll.click();
		Actions move = new Actions(driver);
        move.moveToElement(scroll).clickAndHold();
        move.moveByOffset(500,0);
        move.release();
        move.perform();
		
		*/
		By element = getElement("ListBookings_CAP016", "lnk_uldBKGId;xpath");
		String msg=driver.findElement(element).getText().trim();
		
		if(msg.equals(data(UldBkgID)))
		{
		verifyScreenText(sheetName, data(UldBkgID), msg,"ULD Booking ID displayed",screenName);
		driver.findElement(element).click();
		}
		else
		{
			verifyScreenText(sheetName, data(UldBkgID), msg,"ULD Booking ID Not displayed",screenName);
		}
		waitForSync(5);
	}
// To verify the shipment details like Number of pcs,wt and volume
	public void vrfyShpmntDetails(String pices, String wight,String volume ){
		waitForSync(5);	
		String[] tmp, tmp1,tmp2;
		String pcs = driver.findElement(By.xpath("(//table[@id='listbookingtable']//tr//em)[1]")).getText();
		tmp = pcs.split(" ");
		String peices = tmp[0];
		if(peices.equals(data(pices)))
			writeExtent("Pass", "pcs are same");
		else
			writeExtent("Fail", "pcs are not matching");
		waitForSync(2);
		String wt = driver.findElement(By.xpath("(//table[@id='listbookingtable']//tr//em)[3]")).getText();
		tmp1 = wt.split(" ");
		String weight = tmp1[0];
		if(weight.equals(data(wight)))
			writeExtent("Pass", "Weight matches");
		else
			writeExtent("Fail", "Weight does not matches");
		waitForSync(2);
		String vl = driver.findElement(By.xpath("(//table[@id='listbookingtable']//tr//em)[4]")).getText();
		tmp2 = vl.split(" ");
				String vol = tmp2[0];
				if(vol.equals(data(volume)))
					writeExtent("Pass", "Volume Matches");
				else
					writeExtent("Fail", "Volume does not matches");
	}
	// To click on the close button
	public void close() throws InterruptedException, IOException{
		waitForSync(5);
		clickWebElement(sheetName, "button_close;xpath", "Close Button", screenName);
		waitForSync(2);
	}
	// To verify the updated date
	public void updatedDate(String dt) throws Exception{
		waitForSync(5);
		String[] tmp,tmp1;
		String date = driver.findElement(By.xpath("(//table[@id='listbookingtable']//tr//em)[11]")).getText();
		tmp = date.split(" ");
		//String flightNumber = tmp[0];
		tmp1 = tmp[2].split(" ");
		String updatedDate = tmp1[0]+"-18";
		System.out.println(updatedDate);
		Thread.sleep(2000);
		updatedDate = changeDateFormat(updatedDate, "dd-MMM-yy", "ddMMMyy");
		System.out.println(updatedDate);
		if(updatedDate.equalsIgnoreCase(data(dt)))
			writeExtent("Pass", "updated date matches");
		else
			writeExtent("Fail", "updated date does not matches");
		
	}
	// To verify the updated flight number
	public void updatedFlight(String flight){
		waitForSync(5);
		String[] tmp,tmp1;
		String flt = driver.findElement(By.xpath("(//table[@id='listbookingtable']//tr//em)[10]")).getText();
		tmp = flt.split(" ");
		tmp1 = tmp[0].split(" ");
		String updatedflt = tmp1[0];
		System.out.println(updatedflt);
		if(updatedflt.equalsIgnoreCase(data(flight)))
			writeExtent("Pass", "updated Flight matches");
		else
			writeExtent("Fail", "updated Flight does not matches");
	}
				
				
	}




