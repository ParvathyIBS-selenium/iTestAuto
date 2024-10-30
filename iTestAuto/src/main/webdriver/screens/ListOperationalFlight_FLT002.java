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

public class ListOperationalFlight_FLT002 extends CustomFunctions {


	String sheetName="ListOperationalFlight_FLT002";
	String screenName="ListOperationalFlight : FLT002";

	public ListOperationalFlight_FLT002(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description... List Flight	
 * @param flightNumber
 * @param startDate
 * @param endDate
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void listFlight(String flightNumber,String startDate,String endDate) throws InterruptedException, AWTException, IOException
	{

		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(flightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_startDate;id", startDate, "Flight Start Date", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_toDate;id", endDate, "Flight End Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_list;id", "List", screenName);
		Thread.sleep(3000);		
	}
/**
 * Description... Verify Leg Details	
 * @param verfCols
 * @param actVerfValues
 * @param pmKey
 * @throws IOException 
 */
	public void verifyLegDetails(int verfCols[],String actVerfValues[],String pmKey) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_flightDetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}
	
	/**
                * Description... Verify the flight status is Active
                * @param status
                * @param mode
                * @throws InterruptedException
                */
                public void verifyFlightStatus_Mode(String status, String mode) throws InterruptedException{
                                String fltStatus=getElementText(sheetName, "txt_flightStatus;xpath", "Flight Status", screenName);
                                String fltMode=getElementText(sheetName, "txt_flightMode;xpath", "Flight Mode", screenName);                
                                                                
                                verifyValueOnPage(fltStatus, status, "Verify Flight Status", screenName, "Flight Status");
                                verifyValueOnPage(fltMode, mode, "Verify Flight Mode", screenName, "Flight Mode");                              

                }

/**
 * Description... List All Flight
 * @param flightStation
 * @param startDate
 * @param endDate
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void listAllFlight(String flightStation,String startDate,String endDate) throws InterruptedException, AWTException, IOException
	{

		enterValueInTextbox(sheetName, "inbx_originAirport;xpath", flightStation, "Flight Station", screenName);
		enterValueInTextbox(sheetName, "inbx_startDate;id", startDate, "Flight Start Date", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_toDate;id", endDate, "Flight End Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_list;id", "List", screenName);
		Thread.sleep(3000);		
	}
/**
 * Description... Find Active Flight	
 * @return
 */
	public String []findActiveFlight(){
		String fltDetails[]= new String[2];
		try{
		String activeModeXpath=xls_Read.getCellValue("ListOperationalFlight_FLT002", "inbx_fltMode;xpath");
		List<WebElement>fltStatus=returnListOfElements(sheetName, "inbx_fltStatus;xpath");
		List<String>fltStatustxt=returnTextListOfElements(fltStatus);
		int j=0;
		for(int i=1;i<fltStatustxt.size();i++)
		{
			if(fltStatustxt.get(i).equals("Active"))
			{
			String dynXpath="("+activeModeXpath+")["+(i+1)+"]";
			   String activeMode=driver.findElement(By.xpath(dynXpath)).getText();
			   if(activeMode.equals("Active"))
			   { j=i+1;
				   break;}
			}
		}
		String dynFltNumXpath=xls_Read.getCellValue("ListOperationalFlight_FLT002", "inbx_fltNum;xpath");
		String dynXpath1="("+dynFltNumXpath+")["+j+"]";	
		
		String dynFltDepDateTime=xls_Read.getCellValue("ListOperationalFlight_FLT002", "inbx_fltDepDateTime;xpath");
		String dynXpath2="("+dynFltDepDateTime+")["+j+"]";	
		
		
		/*	String dynFltArvDateTime=xls_Read.getCellValue("ListOperationalFlight_FLT002", "inbx_fltArvDateTime;xpath");
		String dynXpath3="("+dynFltArvDateTime+")["+j+"]";	*/
		
		 fltDetails[0]=driver.findElement(By.xpath(dynXpath1)).getText();
		 fltDetails[1]=driver.findElement(By.xpath(dynXpath2)).getText();
		 /* fltDetails[2]=driver.findElement(By.xpath(dynXpath3)).getText();*/
		}
		catch(Exception e){
			System.out.println("Could not find Active Flight on "+ screenName);
			writeExtent("Fail", "Could not find Active Flight on "+ screenName);
			Assert.assertFalse(true, "Could not find Active Flight on "+ screenName);

	}
		return fltDetails;
	}
/**
 * Description... Verify Operational Flt Day Details	
 * @throws InterruptedException
 */
	public void verifyOperationalFltDayDetails() throws InterruptedException {

		String days = data("Days");
		try {
			List<WebElement> listOfDays=returnListOfElements(sheetName, "lst_daysofweek;xpath");
			List<String> value =returnTextListOfElements(listOfDays);
			for(int i=0;i<listOfDays.size();i++) {
				if(days.contains(value.get(i))) {
					onPassUpdate(
							screenName,
							"Flt Day of operation details are matching",
							"Flt Day of operation details are matching",
							"Day Of Operation",
							"1.Process SSM message from MESEX \n ,2.Open iCargo and invoke FLT002 screen , enter the flight details \n ,3.Verify the flight day of operation ");
				}
				else {
					onFailUpdate(
							screenName,
							"Flt Day of operation details are matching",
							"Flt Day of operation details are not matching",
							"Day Of Operation",
							"1.Process SSM message from MESEX \n ,2.Open iCargo and invoke FLT002 screen , enter the flight details \n ,3.Verify the flight day of operation ");
				}
			}
		}
		catch(Exception e) {

		}
	}
/**
 * Description... Verify Splitted Aircract Types
 */
	public void verifySplittedAircractTypes() {

		String airCraftTypes = data("AircraftTypes");
		try {
			List<WebElement> listOfAirCraftes= returnListOfElements(sheetName, "lst_AircraftTypes;xpath");
			List<String> listOfAirCraftesValues = returnTextListOfElements(listOfAirCraftes);
			for(int i=0;i<listOfAirCraftes.size();i++) {

				if(airCraftTypes.contains(listOfAirCraftesValues.get(i))) {

					onPassUpdate(
							screenName,
							"AircraftTypes are matching",
							"AircraftTypes are matching",
							"AircraftTypes",
							"1.Process ASM message from MESEX \n ,2.Open iCargo and invoke FLT002 screen , enter the flight details \n ,3.Verify the AircraftTypes");
				}
				else {
					onFailUpdate(
							screenName,
							"AircraftTypes are matching",
							"AircraftTypes are not matching",
							"AircraftTypes",
							"1.Process ASM message from MESEX \n ,2.Open iCargo and invoke FLT002 screen , enter the flight details \n ,3.Verify the AircraftTypes");
				}

			}

		}
		catch(Exception e) {

		}
	}
/**
 * Description... Select Flight Mode
 * @param fltMode
 */
	public void selectFlightMode(String fltMode) {
		selectValueInDropdown(sheetName, "lst_ModeOfFlight;xpath", data(fltMode), "Flight Mode", "VisibleText");
		
	}
	
}