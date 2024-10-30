package screens;

import java.awt.AWTException;
import java.io.IOException;
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

public class HandlingAreaTypeSetUp_WHS007 extends CustomFunctions {

	String sheetName = "HandlingAreaTypeSetUp_WHS007";
	String screenName = "Handling Area Type Set Up : WHS007";
	String screenId="WHS007";
	int msgCounter = 0;

	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	 WebFunctions libr = new WebFunctions(driver, excelreadwrite, xls_Read);


	public HandlingAreaTypeSetUp_WHS007(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

/**
 * Description... Click List And Verify Add And Delete Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickListAndVerifyAddAndDeleteButton() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(2);
		ele = findDynamicXpathElement("btn_Add;xpath", sheetName,
				"Add Button", screenName);
		if(ele.isEnabled()) {
			System.out.println("Add Button Enabled");
			writeExtent("Pass", "Add Button Enabled " + "" + " On " + screenName + " Page");
			onPassUpdate(screenName, "Add Button Enabled", "Add Button Enabled", "Add Button", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n" );
		}
		else {
			System.out.println("Add Button disabled");
			writeExtent("FAIL", "Add Button disabled " + "" + " On " + screenName + " Page");
			onPassUpdate(screenName, "Add Button Enabled", "Add Button disabled", "Add Button", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n" );
		}
		waitForSync(2);
		WebElement ele2 = findDynamicXpathElement("btn_Delete;xpath", sheetName,
				"Delete Button", screenName);
		if(ele2.isEnabled()) {
			System.out.println("Delete Button Enabled");
			writeExtent("Pass", "Delete Button Enabled " + "" + " On " + screenName + " Page");
			onPassUpdate(screenName, "Delete Button Enabled", "Delete Button Enabled", "Delete Button", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n" );
		}
		else {
			System.out.println("Delete Button disabled");
			writeExtent("FAIL", "Delete Button disabled " + "" + " On " + screenName + " Page");
			onPassUpdate(screenName, "Delete Button Enabled", "Delete Button disabled", "Delete Button", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n" );
		}
		waitForSync(2);
		boolean value = driver.findElement(By.xpath("//tr[@class='ic-table-row-even']")).isEnabled();
		if(value==true) {
			System.out.println("Existing Handling areas listed");
			writeExtent("Pass", "Existing Handling areas listed " + "" + " On " + screenName + " Page");
			onPassUpdate(screenName, "Existing Handling areas listed", "Existing Handling areas listed", "Handling areas", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n" );
		}
		else {
			System.out.println("Existing Handling areas not listed");
			writeExtent("FAIL", "Existing Handling areas not listed " + "" + " On " + screenName + " Page");
			onPassUpdate(screenName, "Existing Handling areas listed", "Existing Handling areas not listed", "Handling areas", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n" );
		}
	}
	
	/**
	 * Description... Verify Default Airport And Wearhouse
	 * @param airport
	 * @param wearHouse
	 * @throws InterruptedException
	 */
	public void verifyDefaultAirportAndWearhouse(String airport, String wearHouse) throws InterruptedException {
		waitForSync(2);
		ele = findDynamicXpathElement("txt_Airport;xpath", sheetName,
				"Airport", screenName);
		String actualText = ele.getAttribute("value");
		String expectedText = data(airport);
		verifyScreenText(screenName, expectedText, actualText,"FRA","//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n");

		waitForSync(2);
		WebElement ele2 = findDynamicXpathElement("txt_wearHouse;xpath", sheetName,
				"Wear House", screenName);
		String actualText2 = ele2.getAttribute("value");
		String expectedText2 = data(wearHouse);
		verifyScreenText(screenName, expectedText2, actualText2,"FRAWHS","//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n ");
		
	}
/**
 * Description... Click Add And Verify NewLine Added
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickAddAndVerifyNewLineAdded() throws InterruptedException, IOException{

		clickWebElement(sheetName, "btn_Add;xpath", "Add Button", screenName);
		waitForSync(2);
		ele = driver.findElement(By.xpath("//tr[@class='ic-table-row-even'][last()]"));

		if(ele.isDisplayed()){
			System.out.println("New Line Added");
			writeExtent("Pass", "Empty Line Added " + "" + " On " + screenName + " Page");
			onPassUpdate(screenName, "Empty Line Added", "Empty Line Added", "Empty Line", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n");
		}
		else {
			System.out.println("New Line Not Added");
			writeExtent("Fail", "Empty Line Not Added " + "" + " On " + screenName + " Page");
			onFailUpdate(screenName, "Empty Line Added", "Empty Line not Added", "Empty Line", "//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n" );
		}
	}
	
	/**
	 * Description... Get Handling Area
	 * @throws InterruptedException
	 */
	public void getHandlingArea() throws InterruptedException{

		waitForSync(2);
		ele = driver.findElement(By.xpath("(//input[@id='CMP_warehouse_defaults_HandlingAreaTypeSetup_hdlgAreaType'])[1]"));
		String handlingArea = ele.getAttribute("value");
		setPropertyValue("HandlingArea", handlingArea, proppath);
		
	}
	
	/**
	 * Description... Create Handling Area
	 * @param hdlArea
	 * @param roleGroup
	 * @param maxNumber
	 * @param description
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void createHandlingArea(String hdlArea,String roleGroup,String maxNumber,String description) throws InterruptedException, AWTException, IOException {

	
		waitForSync(2);
		enterValueInTextbox(sheetName, "txt_hdlAreainput;xpath", data(hdlArea), "Hdl Area type", screenName);
		enterValueInTextbox(sheetName, "txt_rolGrp;xpath", data(roleGroup), "Role group", screenName);
		clickWebElement(sheetName, "chkBx_shared;xpath", "Shared", screenName);
		clickWebElement(sheetName, "chkBx_continuous;xpath", "Continuous", screenName);
		enterValueInTextbox(sheetName, "inbx_maxNumber;xpath", data(maxNumber), "maxNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_description;xpath", data(description), "description", screenName);
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		libr.keyPress("ENTER");

	}
	/**
	 * Description... Verify Error Message
	 * @param errorMsg
	 */
	public void verifyErrorMessage(String errorMsg){
		
		waitForSync(2);
		ele = findDynamicXpathElement("text_errorMsg;xpath", sheetName,
				"Error Message", screenName);
		String actualText = ele.getText();
		String expectedText = data(errorMsg);
		verifyScreenText(screenName, expectedText, actualText,"Duplicate Handling Area Type Entered","//1. Login to iCargo \n , 2.Invoke WHSOO7 Screen \n , 3.Click on list button \n , 4.Select multiple handling areas \n");
	}

}

