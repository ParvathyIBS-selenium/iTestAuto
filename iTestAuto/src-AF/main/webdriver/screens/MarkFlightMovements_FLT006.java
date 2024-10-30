package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class MarkFlightMovements_FLT006 extends CustomFunctions {

	String sheetName = "MarkFlightMovements_FLT006";
	String screenName = "Mark Flight Movements : FLT006";

	public MarkFlightMovements_FLT006(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description... List Flight
 * @param FlightNumber
 * @param flightDate
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void listFlight(String FlightNumber, String flightDate) throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data("prop~flight_code"),"Carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", data(flightDate), "Flight Date", screenName);
		performKeyActions(sheetName, "inbx_flightDate;name", "TAB", "Flight Date", screenName);
		clickWebElement(sheetName, "btn_list;name", "List", screenName);
		Thread.sleep(3000);
	}
	/**
	 * @Desc To enter the Arrival details of multi-leg flight at transit
	 * @param time
	 * @param date
	 * @throws InterruptedException
	 */
	public void enterArrivalDetailsTransit(String time,String date)throws InterruptedException{
		
		
		enterValueInTextbox(sheetName, "inbx_ATADate;xpath", data(date), "ATA date", screenName);
		enterValueInTextbox(sheetName, "inbx_ATATime;xpath", data(time), "ATA time", screenName);		
	}
	
	/**
	 * Description... Get STD time
	 * @author A-9844
	 * @throws InterruptedException
	 */
	public void getSTDTime(String stdTime) throws InterruptedException
	{
		String stdtime=getElementText(sheetName, "txt_STDTime;xpath","std time", screenName).split(" ")[1];
		System.out.println(stdtime);

		map.put("stdTime", stdtime);


	}
	/**
	* Description... Enter ETA Details
	* @author A-8783
	* @param etd
	* @throws InterruptedException
	*/

	public void enterETADetails(String etaDate, String etaTime) throws InterruptedException
	{

	/***enterValueInTextbox(sheetName, "inbx_ETADate;name", data(etaDate), "ETA date", screenName);
	waitForSync(1);
	enterValueInTextbox(sheetName, "inbx_ETATime;name", data(etaTime), "ETA time", screenName);****/
		
		enterValueInTextbox(sheetName, "inbx_ETADate;xpath", data(etaDate), "ETA date", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_ETATime;xpath", data(etaTime), "ETA time", screenName);

	}

	/**
	 * @Desc To enter the Depature details of multi-leg flight at transit
	 * @param time
	 * @param date
	 * @throws InterruptedException
	 */
	
     public void enterDepartureDetailsTransit(String time,String date) throws InterruptedException{
    	 
    		enterValueInTextbox(sheetName, "inbx_ATDDate;xpath", data(date), "ATD date", screenName);
    		enterValueInTextbox(sheetName, "inbx_ATDTime;xpath", data(time), "ATD time", screenName);		
    	}


	/**
	 * @author A-7271
	 * @param carrierCode
	 * @param FlightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlight(String carrierCode,String FlightNumber, String flightDate) throws InterruptedException, AWTException, IOException {
		 enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode),"Carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", data(flightDate), "Flight Date", screenName);
		performKeyActions(sheetName, "inbx_flightDate;name", "TAB", "Flight Date", screenName);
		clickWebElement(sheetName, "btn_list;name", "List", screenName);
		Thread.sleep(3000);
		}
	/**
	 * To verify ATA is updated based on MVT_ATA message
		 * @throws InterruptedException
		 */
		
		public void verifyATA(int verfCols[], String actVerfValues[], String pmKey) throws InterruptedException, IOException{
			
	    verify_tbl_records_multiple_cols(sheetName, "table_mvmtDetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	 
	    
			
		}
		/**
		* Description... Enter ETD Details
		* @author A-9847
		* @param etd
		* @throws InterruptedException
		*/

		public void enterETDDetails(String etdDate, String etdTime) throws InterruptedException
		{

		enterValueInTextbox(sheetName, "inbx_ETDDate;name", data(etdDate), "ETD date", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_ETDTime;name", data(etdTime), "ETD time", screenName);
		}


	/**
	 * @author A-6260
	 * Desc..verify flight departure details
	 * @param flightTime
	 * @param departureDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
public void verifyFlightMovementDepartureDetails(String flightTime, String departureDate) throws InterruptedException, AWTException, IOException {
		
		String actDate = getAttributeWebElement(sheetName, "inbx_ATDDate;name", "Flight departure date", "value", screenName);
		String actTime = getAttributeWebElement(sheetName, "inbx_ATDTime;name", "Flight departure time", "value", screenName);
		 
		verifyScreenTextWithExactMatch(screenName, departureDate, actDate, "Flight departure date", "Verification of Flight departure date");
		verifyScreenTextWithExactMatch(screenName, flightTime, actTime, "Flight departure time", "Verification of Flight departure time");
		 
	}
	/**
	 * @author A-6260
	 * Desc..verify flight arrival details
	 * @param flightTime
	 * @param arrivalDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void verifyFlightMovementArrivalDetails(String flightTime, String arrivalDate) throws InterruptedException, AWTException, IOException {
		
		String actDate = getAttributeWebElement(sheetName, "inbx_ATADate;xpath", "Flight Arrival date", "value", screenName);
		String actTime = getAttributeWebElement(sheetName, "inbx_ATATime;xpath", "Flight Arrival time", "value", screenName);
		 
		verifyScreenTextWithExactMatch(screenName, arrivalDate, actDate, "Flight Arrival date", "Verification of Flight Arrival date");
		verifyScreenTextWithExactMatch(screenName, flightTime, actTime, "Flight Arrival time", "Verification of Flight Arrival time");
		 
	}

/**
 * Description... Get ATA Date
 * @return
 * @throws InterruptedException
 */
public String getATADate() throws InterruptedException
{
	String actDate = getAttributeWebElement(sheetName, "inbx_ATADateText;xpath", "ATD Date", "value", screenName);
	return actDate;
}
/**
 * Description... Enter Flight Movement Departure Details
 * @param flightTime
 * @param prevDate
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void enterFlightMovementDepartureDetails(String flightTime, String prevDate) throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_ATDDate;name", prevDate, "Flight Date", "Mark Flight Movements : FLT006");
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement(sheetName, "inbx_ATDTime;name", "Flight Time", screenName);
		enterValueInTextbox(sheetName, "inbx_ATDTime;name", flightTime, "Flight Time",
				"Mark Flight Movements : FLT006");
	
		Thread.sleep(2000);
	}
	/**
	 * @author A-9175
	 * Description:List flight with Carrier code
	 * @param carrCode
	 * @param FlightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlightDetails(String carrCode,String FlightNumber, String flightDate) throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrCode),"Carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", data(flightDate), "Flight Date", screenName);
		performKeyActions(sheetName, "inbx_flightDate;name", "TAB", "Flight Date", screenName);
		clickWebElement(sheetName, "btn_list;name", "List", screenName);
		Thread.sleep(3000);
		}


/**
 * Description... Enter Flight Movement Departure Details
 * @throws InterruptedException
 */
	public void enterFlightMovementDepartureDetails() throws InterruptedException {

		// enterValueInTextbox(sheetName, "inbx_ETDDate;xpath", data("etdDate"),
		// "ETD date", screenName);
		// enterValueInTextbox(sheetName, "inbx_ETDTime;xpath", data("etdTime"),
		// "ETD time", screenName);
		enterValueInTextbox(sheetName, "inbx_ATDDate;name", data("atdDate"), "ATD date", screenName);
		enterValueInTextbox(sheetName, "inbx_ATDTime;name", data("atdTime"), "ATD time", screenName);
		Thread.sleep(2000);

	}
/**
 * Description... Get STD Details
 * @author A-7271
 * @throws InterruptedException
 */
	public void getSTDDetails() throws InterruptedException
	{
		String std=getElementText(sheetName, "inbx_STD;xpath","std", screenName);
		System.out.println(std);
		
		map.put("std", std);
		
				
	}
	/**
	 * Description... Enter ETD Details
	 * @author A-7271
	 * @param etd
	 * @throws InterruptedException
	 */
	public void enterETDDetails(String etd) throws InterruptedException
	{
		String etdDate=data(etd).split(" ")[0].toString();
		String etdTime=data(etd).split(" ")[1].toString();
		
		enterValueInTextbox(sheetName, "inbx_ETDDate;name", etdDate, "ETD date", screenName);
		enterValueInTextbox(sheetName, "inbx_ETDTime;name", etdTime, "ETD time", screenName);
	}
/**
 * Description... Enter Flight Movement Arrival Details
 * @throws InterruptedException
 */
	public void enterFlightMovementArrivalDetails() throws InterruptedException {

		// enterValueInTextbox(sheetName, "inbx_ETAData;xpath", data("etaDate"),
		// "ETA date", screenName);
		// enterValueInTextbox(sheetName, "inbx_ETATime;xpath", data("etaTime"),
		// "ETA time", screenName);
		enterValueInTextbox(sheetName, "inbx_ATADate;xpath", data("ataDate"), "ATA date", screenName);
		enterValueInTextbox(sheetName, "inbx_ATATime;xpath", data("ataTime"), "ATA time", screenName);
		Thread.sleep(2000);
	}
/**
 * Description... Enter Flight Movement Departure Details
 * @param flightTime
 * @throws InterruptedException
 * @throws AWTException
 */
	public void enterFlightMovementDepartureDetails(String flightTime) throws InterruptedException, AWTException {

		// enterValueInTextbox(sheetName, "inbx_ETDDate;xpath", data(etdDate),
		// "ETD date", screenName);
		// enterValueInTextbox(sheetName, "inbx_ETDTime;xpath", data(etdTime),
		// "ETD time", screenName);
		enterValueInTextbox(sheetName, "inbx_ATDDate;name", "-1", "Flight Date", "Mark Flight Movements : FLT006");
		Thread.sleep(3000);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_ATDTime;name", flightTime, "Flight Time",
				"Mark Flight Movements : FLT006");
		/*
		 * enterValueInTextbox(sheetName, "inbx_ATDDate;name", data(atdDate),
		 * "ATD date", screenName); enterValueInTextbox(sheetName,
		 * "inbx_ATDTime;name", data(atdTime), "ATD time", screenName);
		 */
		Thread.sleep(2000);
	}
	/**
	 * To verify ATA is updated based on MVT_ATA message
	 * @throws InterruptedException
	 */

	public void verifyATA(String expValue,String index) throws InterruptedException, IOException{

     String locator= xls_Read.getCellValue(sheetName, "inbx_ATALocalTime;xpath").replace("*", index);
     String ata= driver.findElement(By.xpath(locator)).getAttribute("defaultValue");
     System.out.println(ata);
     verifyScreenTextWithExactMatch(sheetName,expValue, ata, "ATA verification","ATA verification");
 			


	}
/**
 * Description... Enter Flight Movement Departure Detail
 * @param ATD
 * @param Date
 * @throws InterruptedException
 */
public void enterFlightMovementDepartureDetail(String ATD,String Date) throws InterruptedException { 
                
                enterValueInTextbox(sheetName, "inbx_ATDTime;name", data(ATD), "ATD TIME", screenName); 
                waitForSync(2); 
                enterValueInTextbox(sheetName, "inbx_ATDDate;name", data(Date), "ATD DATE", screenName); 
                waitForSync(1);
        } 

/**
 * Description... Enter Flight Movement Arrival Details
 * @param flightTime
 * @throws InterruptedException
 * @throws AWTException
 */
	public void enterFlightMovementArrivalDetails(String flightTime) throws InterruptedException, AWTException {

		// enterValueInTextbox(sheetName, "inbx_ETAData;xpath", data(etaDate),
		// "ETA date", screenName);
		// enterValueInTextbox(sheetName, "inbx_ETATime;xpath", data(etaTime),
		// "ETA time", screenName);
		enterValueInTextbox(sheetName, "inbx_ATADate;xpath", "-1", "Flight Date", screenName);
		Thread.sleep(3000);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_ATATime;xpath", flightTime, "Flight Time", screenName);
		/*
		 * enterValueInTextbox(sheetName, "inbx_ATADate;xpath", data(ataDate),
		 * "ATA date", screenName); enterValueInTextbox(sheetName,
		 * "inbx_ATATime;xpath", data(ataTime), "ATA time", screenName);
		 */
		Thread.sleep(2000);
	}
/**
 * Description... Click Save
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickSave() throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "btn_save;name", "Save", screenName);
		waitTillScreenloadWithOutAssertion(sheetName, "inbx_carrierCode;name","carrier Code", screenName,20);

	}
/**
 * Description... Enter Flight Movement Arrival Details
 * @param flightTime
 * @param prevDate
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void enterFlightMovementArrivalDetails(String flightTime, String prevDate) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_ATADate;xpath", prevDate, "Flight Date", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement(sheetName, "inbx_ATATime;xpath", "Flight Time", screenName);
		enterValueInTextbox(sheetName, "inbx_ATATime;xpath", flightTime, "Flight Time", screenName);

		waitForSync(1);
	}
/**
 * Description... Enter Flight Movement Arrival Detail
 * @param ATA
 * @param Date
 * @throws InterruptedException
 */
public void enterFlightMovementArrivalDetail(String ATA,String Date) throws InterruptedException { 
                
                enterValueInTextbox(sheetName, "inbx_ATATime;xpath", data(ATA), "ATA date", screenName); 
                Thread.sleep(2000); 
                enterValueInTextbox(sheetName, "inbx_ATADate;xpath", data(Date), "ATA time", screenName); 
                Thread.sleep(2000); 
        }

/**
 * Description... 	Verify ED marked Or Not For Operational Flight
 * @param expeFirstEDDate
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyEDmarkedOrNotForOperationalFlight(String expeFirstEDDate) throws InterruptedException, IOException {

		WebElement webElement = driver.findElement(By.xpath("(//input[@name='expectedDateDeparture'])[1]"));
		String actEDDate = webElement.getAttribute("Value");
		verifyValueOnPageContains(
				actEDDate,
				expeFirstEDDate,
				"1.Process the SSM and MVT message via MESEX ,\n 2.Verift ED Marked or Not in FLT006 screen",
				"screenName", "Ed first date");

	}
/**
 * Description... Verify ED marked Or Not For NonOperational Flight
 * @param expeSecondEDDate
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyEDmarkedOrNotForNonOperationalFlight(String expeSecondEDDate) throws InterruptedException, IOException {
		WebElement webElement = driver.findElement(By.xpath("(//input[@name='expectedDateDeparture'])[2]"));
		String actEDDate = webElement.getAttribute("Value");			
		verifyValueOnPageContains(
				actEDDate,
				expeSecondEDDate,
				"1.Process the SSM and MVT message via MESEX ,\n 2.Verift ED Marked or Not in FLT006 screen",
				"screenName", "Ed second date");

	}
	/**
	 * @author A-9175
	 * Description:Used to enter Flight Arrival Details on clicking link
	 * @param flightTime
	 * @param prevDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	
	public void clickFlightMovementArrivalDetailsLink() throws InterruptedException, AWTException {

		doubleclickWebElement(sheetName, "txt_dateTimeATD;xpath", "Departure Date And Time Link", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
	}
	
	/**
	 * @author A-9175
	 * Description:Used to enter Flight Departure Details on clicking link
	 * @param flightTime
	 * @param prevDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void clickFlightMovementDepartureDetailsLink() throws InterruptedException, AWTException {
		doubleclickWebElement(sheetName, "txt_dateTimeATA;xpath", "Arrival Date And Time Link", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
	}
	
}
