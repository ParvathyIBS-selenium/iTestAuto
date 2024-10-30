package screens;


import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ImportFlightProgress_OPR263 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "ImportFlightProgress_OPR263";
	String screenName = "Import Flight Progress";
	String screenId = "OPR263";

	public ImportFlightProgress_OPR263(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}

	/**
	 * Description... Click Flight Enquiry
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickFlightEnquiry() throws InterruptedException, IOException{
		clickWebElement(sheetName, "chk_Flight;name", "Flight Check Box", screenName);
		clickWebElement(sheetName, "btn_flightEnquiry;name", "Flight Enquiry Button", screenName);
		waitForSync(5);
	}
	
	/**
	 * Description... Enter To Date Filter
	 * @param toDate
	 * @throws InterruptedException
	 */
	public void enterToDateFilter(String toDate) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_toDateFilter;name", toDate, "To Date Filter", screenName);
	
	}
	
	/**
	 * @throws IOException 
	 * Description... List Flight
	 * 
	 * @param ScreenID
	 * @throws InterruptedException
	 * 
	 */
	public void listFlight(String ScreenID, String carrierCode,
			String flightNumber, String flightDate, String sheetName)
			throws InterruptedException, AWTException, IOException {
		enterValueInTextbox("Generic_Elements", "inbx_carrierCode;xpath",
				carrierCode, "Carrier Code", ScreenID);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", flightNumber,
				"Flight Number", ScreenID);
		enterValueInTextbox("Generic_Elements", "inbx_flightDate;xpath",
				flightDate, "Flight Date", ScreenID);
		
		keyPress("TAB");
		keyRelease("TAB");

		enterToDateFilter(flightDate);

		keyPress("TAB");
		keyRelease("TAB");
		clickWebElement("Generic_Elements", "btn_list;name", "List Button",
				ScreenID);
		waitForSync(3);

	}
	
	/**
	 * Description... Check CLS red
	 * @throws Exception
	 */
	public void checkCLSred() throws Exception {
		waitForSync(3);
		verifyElementDisplayed("(//img[@src='/icargo/images/error.gif'])", "check if red mark is displayed", screenName, "CLS");
	}

	/**
	 * Description... Check CLS green
	 * @throws Exception
	 */
	public void checkCLSgreen() throws Exception {
		waitForSync(3);
		verifyElementDisplayed("(//img[@src='/icargo/images/finished-indicator.png'])[2]", "check if red mark is displayed", screenName, "CLS");
	}
	
	public void provideDateRange(String flightDate) throws InterruptedException, AWTException {

		checkIfUnchecked(sheetName, "chk_continuous;xpath", "continuous check box", screenName);
		enterValueInTextbox(sheetName, "inbx_fromDateFilter;name", flightDate, "From Date", screenId);
		enterValueInTextbox(sheetName, "inbx_toDateFilter;name", flightDate, "To Date", screenId);
		enterValueInTextbox(sheetName, "inbx_fromTimeFilter;name", "00:00", "From Time", screenId);
		enterValueInTextbox(sheetName, "inbx_toTimeFilter;name", "23:59", "From Time", screenId);

	}

	public void listFlight(String carrierCode, String flightNumber, String flightDate)
			throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", carrierCode, "Carrier Code", screenId);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", flightNumber, "Flight Number", screenId);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", flightDate, "Flight Date", screenId);

		keyPress("TAB");
		keyRelease("TAB");
		provideDateRange(flightDate);

		clickWebElement(sheetName, "btn_list;name", "List Button", screenId);
		waitForSync(3);

	}

	public void verifyCustomsInformation(String expCustomsName) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = (WebElement) js.executeScript("return document.getElementsByTagName('circle')[0]");
		ele.click();
		waitForSync(4);
		String actCustomName = getElementText(sheetName, "tbl_customsInformation;xpath", "Customs information",
				screenName);
		if (actCustomName.equals(expCustomsName)) {
			System.out.println("found true for " + actCustomName);

			onPassUpdate(screenName, expCustomsName, actCustomName, "Customs name verification ",
					"Customs name verification");

		} else {
			onFailUpdate(screenName, expCustomsName, actCustomName, "Customs name verification ",
					"Customs name verification");

		}

	}

	public void clickClose() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_Close;id", "Close Button", screenName);
		waitForSync(2);
	}

}
