
package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AdhocTaskCreationHHT extends CustomFunctions {
	
	String sheetName = "AdhocTaskCreationHHT";
	String screenName = "AdhocTaskCreationHHT";
	

	public AdhocTaskCreationHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-8783
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void invokeAdhocTaskCreationScreen() throws InterruptedException, AWTException {

		try {

			clickActionInHHT("adhocTaskCreation_menu;xpath", proppathhht, "Adhoc Task Creation menu", screenName);
			waitForSync(2);
			writeExtent("Pass", "Adhoc Task Creation hht screen is invoked successfully");
		}

		catch (Exception e) {
			writeExtent("Fail", "Adhoc Task Creation hht screen is not invoked ");
		}

	}
	
	/**
	 * @author A-8783
	 * Desc - Click Relocation Task
	 * @throws IOException
	 */
	public void clickRelocationTask() throws IOException {
		clickActionInHHT("adhocTaskCreation_txt_relocationTask;xpath", proppathhht, "Relocation task", screenName);

	}
	
	/**
	 * @author A-8783
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void updateFlightDetails(String carrCode, String flightNo, String flightDate)
			throws AWTException, InterruptedException, IOException {

		waitForSync(5);
		enterValueInHHT("adhocTaskCreation_inbx_carrier;xpath", proppathhht, data(carrCode), "Carrier Code",
				screenName);
		waitForSync(2);
		enterValueInHHT("adhocTaskCreation_inbx_flightNo;xpath", proppathhht, data(flightNo), "Flight No", screenName);
		waitForSync(2);
		handleErrorHHT("Invalid Flight");
		if (flightDate.equals("currentDay")) {
			clickActionInHHT("adhocTaskCreation_btn_today;xpath", proppathhht, "Current Date", screenName);
		}

		else if (flightDate.equals("nextDay")) {
			clickActionInHHT("adhocTaskCreation_btn_tomorrow;xpath", proppathhht, "Next Date", screenName);
		}
		waitForSync(10);

	}
	
	/**
	 * @author A-8783
	 * @param uldNo
	 * @throws IOException
	 */
	public void selectOrUnselectULD(String uldNo) throws IOException {

		try {
			String locator = getPropertyValue(proppathhht, "adhocTaskCreation_btn_checkBox;xpath");
			locator = locator.replace("uld", data(uldNo));

			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(4);

			writeExtent("Pass", "Clicked on checkbox against " + data(uldNo) + screenName);

		} catch (Exception e) {
			writeExtent("Fail", "Failed to clicked on checkbox against " + data(uldNo) + screenName);
		}
	}
	/**
	 * @author A-9844
	 * Desc - Enter location in the search filed
	 * @param location
	 * @throws IOException
	 */
	public void enterLocationInSearchField(String location) throws IOException {

		waitTillMobileElementDisplay(proppathhht,"adhocTaskCreation_txt_location;xpath","xpath");
		enterValueInHHT("adhocTaskCreation_txt_location;xpath", proppathhht, data(location), "Location", screenName);
		String locator = getPropertyValue(proppathhht, "adhocTaskCreation_btn_location;xpath");
		locator = locator.replace("loc", data(location));
		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(2);

	}
	/**
	 * @author A-8783
	 * Desc - Verify if the ULDs are preselected or not
	 */
	public void verifyULDPreselected() {
		try {
			waitForSync(2);
			int size = getSizeOfMobileElement("adhocTaskCreation_txt_unselectAll;xpath", proppathhht);

			if (size == 1) {
				onPassUpdate(screenName, "UnselectAll", "UnselectAll","ULDs are preselected", "unselect all");
			} else {
				onFailUpdate("The ULDs are not preselected in " + screenName);
			}
		} catch (Exception e) {
			onFailUpdate("Could not verify the ULDs are preselected in " + screenName);		}
	}
	
	
	/**
	 * @author A-8783
	 * Desc - Verify flight number field
	 */
	public void verifyFlightField() {
		try {
			waitForSync(2);
		int size = getSizeOfMobileElement("adhocTaskCreation_inbx_flightNo;xpath",proppathhht);
		if(size==1) {
			writeExtent("Pass", "Verified that Flight number field is present in "+screenName);
		}
		else {
			writeExtent("Fail", "Flight number field is not present in "+screenName);
		}
		}
		catch(Exception e) {
			writeExtent("Fail", "Failed to verify Flight number field is present in "+screenName);

		}
	}
	
	/**
	 * @author A-8783
	 * Desc - Click next button
	 * @throws IOException 
	 */
	public void clickNext() throws IOException {
		clickActionInHHT("adhocTaskCreation_btn_next;xpath", proppathhht, "Next", screenName);
	}
	
	/**
	 * @author A-8783
	 * Desc - Enter location
	 * @param location
	 * @throws IOException
	 */
	public void enterLocation(String location) throws IOException {
		
		scrollInMobileDevice(data(location));
		String locator = getPropertyValue(proppathhht, "adhocTaskCreation_btn_location;xpath");
		locator = locator.replace("loc", data(location));
		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(2);

	}
	
	/**
	 * @author A-8783
	 * Desc - verify task created successfully message
	 */
	public void verifyTaskCreated() {
		try {
			waitForSync(2);
			int size = getSizeOfMobileElement("adhocTaskCreation_txt_taskMessage;xpath", proppathhht);

			if (size == 1) {
				writeExtent("Pass", "Task created successfully in " + screenName);
			} else {

				writeExtent("Fail", "Task not created in " + screenName);
			}
		} catch (Exception e) {
			writeExtent("Fail", "Could not verify if task is created in " + screenName);
		}
	}
}

