package screens;


import java.awt.AWTException;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;


public class CGOICSS extends CustomFunctions

{
	String sheetName ="CGOICSS";
	String screenName ="CGOICSS_screen";


	public CGOICSS(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}

	/**@author A-10328
	 * Description : Create Flight
	 * @param fltNumber
	 * @param startDate
	 * @param endDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void createNewFlight(String fltNumber, String startDate , String endDate) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_AirlineDesig;xpath", data("prop~flight_code"), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNo;xpath", data(fltNumber), "Flight No", screenName);
		doubleclickWebElement(sheetName, "btn_clickOPE;xpath", "click OPE", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_startDate;xpath", data(startDate), "Flight Date", screenName);
		enterValueInTextbox(sheetName, "inbx_endDate;xpath", data(endDate), "Flight Date", screenName);
		waitForSync(2);
	}


	/**@author A-10328
	 * Description - select day from the dropdown
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */

	public void selectDayofOperations() throws InterruptedException, IOException, AWTException
	{
		doubleclickWebElement(sheetName, "drpdn_clickOperation;xpath", "click dropdown", screenName);
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_clickselectall;xpath", "click select all", screenName);
		waitForSync(1);
		doubleclickWebElement(sheetName, "btn_clickback;xpath", "click back", screenName);
		waitForSync(1);

	}

	/**
	 * @author A-9847
	 * @Desc Overridden method to create new flight by including carrierCode also as an argument
	 * @param carrierCode
	 * @param fltNumber
	 * @param startDate
	 * @param endDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void createNewFlight(String carrierCode,String fltNumber, String startDate , String endDate) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_AirlineDesig;xpath", data(carrierCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNo;xpath", data(fltNumber), "Flight No", screenName);
		doubleclickWebElement(sheetName, "btn_clickOPE;xpath", "click OPE", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_startDate;xpath", data(startDate), "Flight Date", screenName);
		enterValueInTextbox(sheetName, "inbx_endDate;xpath", data(endDate), "Flight Date", screenName);
		waitForSync(2);
	}



	/**@author A-10328
	 * Description : Add Leg 
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void addLeg() throws InterruptedException, IOException

	{
		String deptime=xls_Read.getCellValue(sheetName, "inbx_deptime;xpath");
		int size=driver.findElements(By.xpath(deptime)).size();
		if(size==0)
		{
			clickWebElementByWebDriver(sheetName, "btn_clickleg;xpath", "click add leg", screenName);
			waitForSync(2);
		}

	}



	/**@author A-10328
	  Description : enter leg details
	 * @param departureTime
	 * @param arrivalTime
	 * @param Origin
	 * @param dest
	 * @param serviceType
	 * @param aircraft
	 * @param carriercode
	 * @throws InterruptedException
	 */

	public void enterLegDetails(String departureTime, String arrivalTime, String Origin , String dest,String serviceType,String aircraftType, String carriercode) throws InterruptedException
	{

		enterTextWithoutClear(sheetName, "inbx_deptime;xpath", data(departureTime), "Departure Time", screenName);
		waitForSync(1);
		enterTextWithoutClear(sheetName, "inbx_from;xpath", data(Origin), "From ", screenName);
		enterTextWithoutClear(sheetName, "inbx_To;xpath", data(dest), "To", screenName);
		enterTextWithoutClear(sheetName, "inbx_arrivaltime;xpath", data(arrivalTime), "Arrival Time", screenName);
		waitForSync(1);
		enterTextWithoutClear(sheetName, "inbx_servicetype;xpath", data(serviceType), "Service type", screenName);
		enterTextWithoutClear(sheetName, "inbx_aircraftType;xpath", data(aircraftType), "aircraft type", screenName);
		enterTextWithoutClear(sheetName, "inbx_aircraftowner;xpath", data(carriercode), "aircraft owner", screenName);
		waitForSync(1);
	}

	/**@author A-10328
	 * Description : click save
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void clickSave() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "btn_save;xpath", "click save", screenName);
		waitForSync(2);
		switchToAlert("Accept", screenName);
		waitForSync(1);
	}
	/**@author A-10690
	  Description : enter leg details
	 * @param departureTime
	 * @param arrivalTime
	 * @param Origin
	 * @param dest
	 * @param serviceType
	 * @param aircraft
	 * @param carriercode
	 * @param legcount details
	 * @throws InterruptedException
	 */

	public void enterLegDetails(String departureTime, String arrivalTime, String Origin , String dest,String serviceType,String aircraftType, String carriercode,String legcount) throws InterruptedException
	{

		
		String deptime = xls_Read.getCellValue(sheetName, "inbx_secdeptime;xpath").replace("index",legcount);
		driver.findElement(By.xpath(deptime)).sendKeys(data(departureTime));
		waitForSync(1);
		String origin = xls_Read.getCellValue(sheetName, "inbx_secfrom;xpath").replace("index",legcount);
		driver.findElement(By.xpath(origin)).sendKeys(data(Origin));
		String destination = xls_Read.getCellValue(sheetName, "inbx_secTo;xpath").replace("index",legcount);
		driver.findElement(By.xpath(destination)).sendKeys( data(dest));
		String arrtime = xls_Read.getCellValue(sheetName, "inbx_secarrivaltime;xpath").replace("index",legcount);
		driver.findElement(By.xpath(arrtime)).sendKeys(data(arrivalTime));
		waitForSync(1);
		String service = xls_Read.getCellValue(sheetName, "inbx_secservicetype;xpath").replace("index",legcount);
		driver.findElement(By.xpath(service)).sendKeys(data(serviceType));
		String aircraft = xls_Read.getCellValue(sheetName, "inbx_secaircraftType;xpath").replace("index",legcount);
		driver.findElement(By.xpath(aircraft)).sendKeys(data(aircraftType));
		String carrier = xls_Read.getCellValue(sheetName, "inbx_secaircraftowner;xpath").replace("index",legcount);
		driver.findElement(By.xpath(carrier)).clear();
		driver.findElement(By.xpath(carrier)).sendKeys(data(carriercode));
		waitForSync(1);
	}


/**@author A-10690
	 * Description : Add LEG button 
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void addSecondLeg() throws InterruptedException, IOException

	{
		
			clickWebElementByWebDriver(sheetName, "btn_clickleg;xpath", "click add leg", screenName);
			waitForSync(2);
		

	}

	/**@author A-10328
	 * Description : Enter departure and arrival time
	 * @param departureTime
	 * @param arrivalTime
	 * @throws InterruptedException
	 */

	public void enterDepartureAndArrivalTime(String departureTime,String arrivalTime) throws InterruptedException
	{
		enterTextWithoutClear(sheetName, "inbx_deptime;xpath", data(departureTime), "Departure Time", screenName);
		waitForSync(2);
		enterTextWithoutClear(sheetName, "inbx_arrivaltime;xpath", data(arrivalTime), "Arrival Time", screenName);
		waitForSync(2);
	}

	/**@author A-10328
	 * Description -  Enter From and To Details
	 * @param Origin
	 * @param dest
	 * @throws InterruptedException
	 */

	public void enterFromAndTo (String Origin , String dest) throws InterruptedException
	{
		enterTextWithoutClear(sheetName, "inbx_from;xpath", data(Origin), "From ", screenName);
		enterTextWithoutClear(sheetName, "inbx_To;xpath", data(dest), "To", screenName);
		waitForSync(1);
	}

	/**@author A-10328
	 * Description - Enter air craft type
	 * @param aircraft
	 * @throws InterruptedException
	 */

	public void enterAircraftType (String aircraft) throws InterruptedException
	{

		enterTextWithoutClear(sheetName, "inbx_aircraftType;xpath", data(aircraft), "aircraft type", screenName);
		waitForSync(1);
	}

	/**@author A-10328
	 * Description - Enter service  type
	 * @param serviceType
	 * @throws InterruptedException
	 */

	public void enterserviceType (String serviceType) throws InterruptedException
	{

		enterTextWithoutClear(sheetName, "inbx_servicetype;xpath", data(serviceType), "Service type", screenName);
		waitForSync(1);
	}

	/**@author A-10328
	 * Description - search for flight
	 * @throws Exception
	 */

	public void searchFlightBtn() throws Exception
	{
		clickWebElementByWebDriver(sheetName, "btn_search;xpath", "List flight ", screenName);
		waitForSync(3);


	}

	/**@author A-10328
	 * Description - List flight details
	 * @param flightNumber
	 * @param carriercode
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void listFlightDetails(String flightNumber,String carrierCode) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_carriercode;xpath", data(carrierCode),"carrier code ", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(flightNumber),"Flight Number ", screenName);
		doubleclickWebElement(sheetName, "btn_clickLt;xpath", "click LT ", screenName);
		clickWebElementByWebDriver(sheetName, "btn_search;xpath", "List flight ", screenName);
		waitForSync(3);

	}

	/**@author A-10328
	 * Description - Click on create flight
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOnCreateFlight() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "btn_create;xpath", "click on create ", screenName);
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_selectLT;xpath", "Select LT ", screenName);
		waitForSync(3);

	}


	/**@author A-10328
	 * Description - list new flight details
	 * @param flightNumber
	 * @param carriercode
	 * @param flightType
	 * @throws Exception
	 */

	public void createNewFlightDetails(String flightNumber,String carrierCode,String flightType ) throws Exception
	{


		listFlightDetails(flightNumber, carrierCode);
		waitForSync(1);
		try
		{

			String fltsize=xls_Read.getCellValue(sheetName, "txt_createdflight;xpath");
			int size=driver.findElements(By.xpath(fltsize)).size();
			while(size==1)
			{
				createFlight(flightType);
				searchFlightBtn();
				waitForSync(1);
				clickWebElementByWebDriver(sheetName, "btn_reset;xpath", "List flight link", screenName);
				listFlightDetails(flightNumber, carrierCode);
				size=driver.findElements(By.xpath(fltsize)).size();
				waitForSync(1);
			}

			clickOnCreateFlight();
			waitForSync(1);


		}

		catch(Exception e)
		{

		}
	}


	/**@author A-10328
	 * Description - To cancel flight creation
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void CancelFLTCreation() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "btn_clickcancel;xpath", "click cancel", screenName);
		waitForSync(2);

	}


}
