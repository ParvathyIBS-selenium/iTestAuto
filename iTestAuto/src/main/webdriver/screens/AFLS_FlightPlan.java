package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;


public class AFLS_FlightPlan extends CustomFunctions

{
	String SheetName ="AFLS_FlightPlan";
	String ScreenName ="AFLS_FlightPlan screen";


	public AFLS_FlightPlan(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	/**
	 * @author A-9847
	 * @Desc To enter the flight details
	 * @param carrierCode
	 * @param fltNo
	 * @param fltDepDateFrom
	 * @param fltDepDateTo
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void enterFlightDetails(String carrierCode,String fltNo,String fltDepDateFrom,String fltDepDateTo) throws InterruptedException, IOException, AWTException{

		waitForSync(2);
		String fltDepDateRange=data(fltDepDateFrom)+" - "+data(fltDepDateTo);
		waitTillScreenload(SheetName, "inbx_carrierCode;xpath","Flight Carrier code", ScreenName);
		enterValueInTextboxWithoutScroll(SheetName,"inbx_carrierCode;xpath", data(carrierCode), "Carrier Code", ScreenName);
		enterValueInTextboxWithoutScroll(SheetName, "inbx_flightNum;xpath", data(fltNo), "Flight Number", ScreenName);		
		enterValueInTextboxWithoutScroll(SheetName, "inbx_departureDateRange;xpath", fltDepDateRange, "Flight Depature Date Range", ScreenName);	
		waitForSync(2);
		//click to close the calender opened while entering depature date
		doubleclickWebElement(SheetName, "inbx_flightNum;xpath", "Flight Number", ScreenName);

	}
	/**
	 * @author A-9847
	 * @Desc Click on Save Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSave() throws InterruptedException, IOException{
		clickWebElement(SheetName, "btn_save;xpath", "Save Button", ScreenName);
		waitForSync(3);
	}

	
	/**
	 * @author A-9847
	 * @Desc To enter the Loading priority of the given AWB
	 * @param loadingPrio
	 * @param fullAwbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterLoadingPriority(String loadingPrio,String fullAwbNo) throws InterruptedException, IOException{
		try
		{
			driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "inbx_loadingPrio;xpath").replace("*", data(fullAwbNo)))).clear();
			waitForSync(1);
			driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "inbx_loadingPrio;xpath").replace("*", data(fullAwbNo)))).sendKeys(data(loadingPrio));
		writeExtent("Pass", "Successfully entered the Loading Priority as "+data(loadingPrio)+" on "+ScreenName);
		waitForSync(3);
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to enter the Loading Priority on "+ScreenName);	
		}

	}
    /**
	   * @author A-10328
	   * @Desc Get the system calculated value of Loading Prio 
	   * @param fullAwbNo
       * @throws InterruptedException
	   * @throws IOException
	 */
	

	public String getLoadingPriority(String fullAwbNo) throws InterruptedException, IOException

	{

		String loadingPrio="";
		try
		{


			loadingPrio=driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "inbx_loadingPrio;xpath").replace("*", data(fullAwbNo)))).getAttribute("value");
			waitForSync(2);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to retrieve the Loading Priority on "+ScreenName);
		}
		return loadingPrio;

	}

	/**
	 * @author A-9847
	 * @Desc To enter the Flight Instruction Remarks
	 * @param remarks
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void enterFlightInstructionRemarks(String remarks) throws InterruptedException, IOException, AWTException{
		
		enterValueInTextboxWithoutScroll(SheetName, "inbx_flightRemarks;xpath",data(remarks), "Flight Remarks", ScreenName);	
		waitForSync(2);
		

	}

	/**
	 * @author A-9847
	 * @Desc To verify the WH HandOverStatus as YES
	 */
	public void verifyWHHandOverStatus(){
		try{
			String actStatus = driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "txt_WHHandoverStatus;xpath"))).getText();
			System.out.println(actStatus);
			verifyScreenTextWithExactMatch(ScreenName, "Yes", actStatus, "WH HandOver Status", "WH HandOver Status");
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the WH Handover Status on "+ScreenName);	
		}
	}
	/**
	 * @author A-9844
	 * @Desc To enter the flight board point
	 * @param origin
	 * @throws InterruptedException
	 */
	public void enterFlightBoardPoint(String origin) throws InterruptedException{

		
		enterValueInTextboxWithoutScroll(SheetName,"inbx_legBrdPoint;xpath", data(origin), "Origin", ScreenName);
		waitForSync(2);
		

	}

	/**
	 * @author A-9844
	 * @Desc To click on UnWareHouse HandOver 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickWHUnHandOver() throws InterruptedException, IOException{
		try{

			clickWebElement(SheetName, "btn_whUnHandOver;id", "WH UnHandOver", ScreenName);
			waitForSync(4);
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to click on WH UnHandOver on "+ScreenName);	
		}

	}

	/**
	 * @author A-9847
	 * @Desc To click on Search Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSearch() throws InterruptedException, IOException{

		clickWebElement(SheetName, "btn_search;xpath", "Search Button", ScreenName);
		waitForSync(4);
	}


	/**
	 * @author A-9847
	 * @Desc To select the the given Shipment checkbox
	 * @param fullAwbNo - Eg: 074-10080803
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectShipment(String fullAwbNo) throws InterruptedException, IOException{

		try{
			driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "chk_shipment;xpath").replace("*", data(fullAwbNo)))).click();
			waitForSync(2);

			writeExtent("Pass", "Selected the Shipment "+data(fullAwbNo)+ " on "+ScreenName);
		}
		catch(Exception e){

			writeExtent("Fail", "Failed to select the Shipment on "+ScreenName);	
		}

	}


	/**
	 * @author A-9847
	 * @Desc To click on Send Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSend() throws InterruptedException, IOException{

		clickWebElementByWebDriver(SheetName, "btn_send;xpath", "Send Button", ScreenName);
		waitForSync(2);
	}


	/**
	 * @author A-9847
	 * @Desc To select the given message type from the list after clicking Send Button	
	 * @param msg
	 */
	public void selectSendMessages(String msg){

		try{
			driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "div_sendMsges;xpath").replace("*", data(msg)))).click();
			waitForSync(2);

			writeExtent("Pass", "Selected the Message "+data(msg)+ " on "+ScreenName);
		}
		catch(Exception e){

			writeExtent("Fail", "Failed to select the Message on "+ScreenName);	
		}
	}

	/**
	 * @author A-9847
	 * @Desc To click on Menu Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickMenu() throws InterruptedException, IOException{

		clickWebElement(SheetName, "btn_menu;xpath", "Menu Button", ScreenName);
		waitForSync(3);
	}

	/**
	 * @author A-9847
	 * @Desc To select the given menuOption
	 * @param menuOption
	 */
	public void selectMenuOption(String menuOption){

		try{
			driver.findElement(By.xpath(xls_Read.getCellValue(SheetName, "txt_menuOption;xpath").replace("*", data(menuOption)).replace("option", data(menuOption)))).click();
		
			writeExtent("Pass", "Selected the Menu Option "+data(menuOption)+ " on "+ScreenName);
		}
		catch(Exception e){

			writeExtent("Fail", "Failed to select the Menu Option on "+ScreenName);	
		}

	}

	/**
	 * @author A-9847
	 * @Desc To click on WareHouse HandOver 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickWHHandOver() throws InterruptedException, IOException{
		try{

			clickWebElement(SheetName, "btn_whHandOver;id", "WH HandOver", ScreenName);
			waitForSync(4);
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to click on WH HandOver on "+ScreenName);	
		}

	}


}































