package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class GlobalCustomerAllotment_CAP006 extends CustomFunctions {
	String sheetName="GlobalCustomerAllotment_CAP006";
	String screenName="Maintain Global Customer Allotment";
	public GlobalCustomerAllotment_CAP006(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
	}
	/**
	 * Description... Enter Customer Details
	 * @param AgentCode
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void customerdetails(String AgentCode) throws InterruptedException, AWTException{
		enterValueInTextbox(sheetName, "Global_Customer_Code;xpath", data(AgentCode), "AgentCode", screenName);
		keyPress("TAB");
		Thread.sleep(3000);	
	}
	/**
	 * Description... Enter Flight Details
	 * @param FlightNo
	 * @param Origin
	 * @param Destination
	 * @param StartDate
	 * @param EndDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void flightDetails(String FlightNo,String Origin,String Destination,String StartDate,String EndDate) throws InterruptedException, AWTException{
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNo), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_origin;xpath", data(Origin), "Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_dest;xpath", data(Destination), "Destination", screenName);
		Thread.sleep(3000);
		enterValueInTextbox(sheetName, "inbx_startDate;xpath", data(StartDate), "Flight Start Date", screenName);
		enterValueInTextbox(sheetName, "inbx_toDate;xpath", data(EndDate), "Flight End Date", screenName);
		Thread.sleep(2000);	
		checkIfUnchecked(sheetName, "chk_frequencyAll;xpath", "All Frequency", screenName);
        Thread.sleep(2000);
	}
	
	/**
	 * Description... Enter Commodity Details
	 * @param Category
	 * @param AllotmentSubType
	 * @param ShipType
	 * @param Origin
	 * @param Destination
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void commoditydetails(String Category,String AllotmentSubType,String ShipType,String Origin,String Destination ) throws InterruptedException, AWTException{
		enterValueInTextbox(sheetName, "inbx_category;xpath", data(Category), "CATEGORY", screenName);
		selectValueInDropdown(sheetName, "dropDownAllotmentSubType;xpath", data(AllotmentSubType), "AllotmentSubType", "VisibleText");
		waitForSync(2);
		selectValueInDropdown(sheetName, "dropDownShipType;xpath", data(ShipType), "AllotmentSubType", "VisibleText");
		waitForSync(2);
		enterValueInTextbox(sheetName, "transport_origin;xpath", data(Origin), "Origin", screenName);
		enterValueInTextbox(sheetName, "transport_destination;xpath", data(Destination), "Destination", screenName);
	}
	
	/**
	 * Description... enter capacity details
	 * @param wgt
	 * @param Vol
	 * @throws InterruptedException
	 */
	public void capacitydetails(String wgt, String Vol) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_weightCapacity;xpath", data(wgt), "weight", screenName);
		enterValueInTextbox(sheetName, "inbx_volumeCapacity;xpath", data(Vol), "Volume", screenName);
	}
	/**
	 * Description... enter contour details
	 * @param LDC
	 * @param LDP
	 * @param MDP
	 * @throws InterruptedException
	 */
	public void contourdetails(String LDC, String LDP, String MDP) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_LDC;xpath", data(LDC), "LDC", screenName);
		enterValueInTextbox(sheetName, "inbx_LDP;xpath", data(LDP), "LDP", screenName);
		enterValueInTextbox(sheetName, "inbx_MDP;xpath", data(MDP), "MDP", screenName);
	}
	/**
	 * Description...  Click contour LOV Button
	 * @throws Exception
	 */
	public void contourLov() throws Exception{
		waitForSync(3);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "img_contourLOV;xpath", "contour LOV Button", screenName);
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
	}
	/**
	 * Description...  Click contour Ok Button
	 * @throws Exception
	 */
	public void contourLovOk() throws Exception{
		waitForSync(2);
		clickWebElement(sheetName, "btn_contourOk;name", "contour Ok Button", screenName);
		waitForSync(1);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "CAP006");
	}
	/**
	 * Description...  Select contour value
	 * @param contour
	 * @param index
	 * @throws Exception
	 */
	public void selectcontour(String contour,String index) throws Exception{
	
		selectValueInDropdownWthXpath("(//select[@name='contour'])"+index, data(contour), "Contour value", "Value");
		
	}
	/**
	 * Description...  Enter LAT Time details
	 * @param time
	 * @throws InterruptedException
	 */
	public void latTimedetails(String time) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_latTime;xpath", data(time), "Time", screenName);
	}
	/**
	 * Description... Click Save Allotment, handle alert and get Allotment Value
	 * @return
	 * @throws Exception
	 */
	
	public String saveAllotment() throws Exception {
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		waitForSync(4);
		switchToFrame("default");
		handleAlert("Accept", screenName);
		waitForSync(3);
		handleAlert("getText", screenName);
		handleAlert("Accept", screenName);
		waitForSync(2);
		switchToDefaultAndContentFrame("CAP006");
		Thread.sleep(3000);
		WebElement Allotment_id = driver.findElement(By.xpath("//input[@name='globalCustomerAllotmentId']"));
		String s= Allotment_id.getAttribute("value");
        System.out.println(s);
        return s;

		}
	/**
	 * Description... click clear Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clear() throws InterruptedException, IOException {
		clickWebElement("Generic_Elements", "btn_clear;xpath", "Clear Button", screenName);

	}
}
