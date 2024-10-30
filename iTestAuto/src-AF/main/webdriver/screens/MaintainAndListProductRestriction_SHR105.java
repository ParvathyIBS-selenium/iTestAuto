package screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainAndListProductRestriction_SHR105 extends CustomFunctions{

	
	String sheetName = "MaintainProdRestriction_SHR105";
	public CustomFunctions cust;
	String screenID = "SHR105";
	public String screenName = "Maintain And List Product Restriction";
	int count=0,count2=0;
	public MaintainAndListProductRestriction_SHR105(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
	}
/**
 * Description... List With Flight No From And To	
 * @param from_Flight
 * @param To_Flight
 * @throws Exception
 */
	public void listWithFlightNoFromAndTo(String from_Flight,String To_Flight) throws Exception
	{
		String first_Flight=data(from_Flight).substring(2);
		String second_Flight=data(To_Flight).substring(2);
		System.out.println(first_Flight+second_Flight);
		enterValueInTextbox(sheetName, "inbx_FromFlNo;name", first_Flight,
				"From Flight number", screenName);
		enterValueInTextbox(sheetName, "inbx_ToFlNo;name", second_Flight,
				"From Flight number", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_List;name", "List", screenName);
	}
/**
 * Description... 	Click Add Button
 * @throws Exception
 */
	public void clickAddButton() throws Exception
	{
		waitForSync(2);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_Add;name", "ADD", screenName);
		switchToWindow("child");
	}
/**
 * Description... Click Add in PopUp	
 * @throws Exception
 */
	public void clickAddinPopUp() throws Exception
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_addINpopUp;id", "ADD in popup", screenName);
	}
/**
 * Description... Enter Details IN Popup
 * @param carierCode
 * @param flightNo
 * @param origin
 * @param destination
 * @throws Exception
 */
	public void enterDetailsINPopup(String carierCode,String flightNo,String origin,String destination) throws Exception
	{
		waitForSync(3);
		String first_Flight=data(flightNo).substring(2);
		enterValueInTextbox(sheetName, "inbx_CarrierCode;name", carierCode,
				"Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_Flightnumber;name",first_Flight,
				"Flight Number", screenName);
		enterValueInTextbox(sheetName, "inbx_Destination;name", data(origin),
				"Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_Origin;name",data(destination),
				"Destination", screenName);
	}
/**
 * Description... Select Product Priority
 * @param option
 * @throws Exception
 */
	public void selectProductPriority(String option) throws Exception
       {
              waitForSync(2);
              if(count==0)
              {
                     clickWebElement(sheetName, "btn_ProductPriority;xpath", "product priority", screenName);
                     driver.findElement(By.xpath("(//span[contains(text(),'"+option+"')])[1]")).click();
                     count++;
              }
              else
              {
                     
                     driver.findElement(By.xpath("(//input[@type='checkbox'])[11]")).click();
                     driver.findElement(By.xpath("(//input[@type='checkbox'])[11]")).click();
                     clickWebElement(sheetName, "btn_ProductPriority2;xpath", "product priority", screenName);
                     driver.findElement(By.xpath("(//span[contains(text(),'"+option+"')])[2]")).click();
              }
              keyPress("TAB");
              keyRelease("TAB");
              
       }
/**
 * Description... Select Flight Setting
 * @param option
 * @throws Exception
 */
	public void selctFlightSetting(String option) throws Exception
	{
		waitForSync(1);
		if(count2==0)
		{
			clickWebElement(sheetName, "btn_FlightSetting;xpath", "Flight setting", screenName);
			driver.findElement(By.xpath("(//option[contains(text(),'"+option+"')])[1]")).click();
			count2++;
		}
		else
		{
			clickWebElement(sheetName, "btn_FlightSetting2;xpath", "Flight setting", screenName);
			driver.findElement(By.xpath("(//option[contains(text(),'"+option+"')])[2]")).click();
		}
		
	}
/**
 * Description... Click Save In Popup
 * @throws Exception
 */
	public void clickSaveInPopup() throws Exception
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_Save;name", "Save in popup", screenName);
		switchToWindow("getParent");
		String frameName = "iCargoContentFrame" + screenID;
		driver.switchTo().frame(frameName);
	}
/**
 * Description... Click Excel Generation
 * @throws Exception
 */
	public void clickExcelGeneration() throws Exception
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_ExcelLink;xpath", "Save in popup", screenName);
	}
}
