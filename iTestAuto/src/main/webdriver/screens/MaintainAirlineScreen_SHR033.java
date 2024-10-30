package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainAirlineScreen_SHR033 extends CustomFunctions {

	public MaintainAirlineScreen_SHR033(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "MaintainAirlineScreen_SHR033";
	public String ScreenName = "Maintain Airline : SHR033";
	String screenId = "SHR033";
	
	/**
	 * Desc : Listing using Carrier code
	 * @author A-9175
	 * @param airlinecode
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listAirport(String airlinecode) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName,"inbx_airlineCode;id", data(airlinecode), "Airport Code", ScreenName);
		waitForSync(5);
		clickWebElement(sheetName, "btn_list;id", "List Button", ScreenName);
	}
	
	/**
	 * Desc : 
	 * @author A-9175
	 * @param parameterValue
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void filterParameterBasedOnvalue(String parameterValue) throws InterruptedException, AWTException {
		try{
				clickWebElement(sheetName, "icon_fltr_parameter;xpath", "filter Button", ScreenName);
				waitForSync(5);
				System.out.println(data("parameterValue"));
				enterValueInTextbox(sheetName,"inbx_fltr_parameter;xpath", data(parameterValue), "Parameter value Code", ScreenName);
				performKeyActions(sheetName, "inbx_fltr_parameter;xpath", "ENTER", "Ok Button", ScreenName);
				waitForSync(2);	
				writeExtent("Pass", " Sucessfully Filterd "+data(parameterValue) + ScreenName + " Page");
			}catch(Exception e){
			 
				writeExtent("Fail", " Not Filterd "+data(parameterValue) + ScreenName + " Page");
		 }
		
	}
	/**
	 * Desc : Changing value of text box with respct to key value
	 * @author A-9175
	 * @param parameterKey
	 * @param optionVal
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void changeParameterValueTextBox(String parameterKey,String optionVal) throws InterruptedException, IOException {
		try
		{
			String locator = xls_Read.getCellValue(sheetName, "Key_parameterValueTextBox;xpath");
	        locator=locator.replace("Key",data(parameterKey));
	        driver.findElement(By.xpath(locator)).clear();
	        System.out.println(optionVal);
	        driver.findElement(By.xpath(locator)).sendKeys(data(optionVal));
			waitForSync(5);
			save();
			handleAlert("Accept", " Maintain Airline ");
			waitForSync(5);
			writeExtent("Pass", "Successfully changed Parameter Value In "+ScreenName);
		}catch(Exception e){
			writeExtent("Fail", "could not change Parameter Value In "+ScreenName);
		}
		
		
	}

	/**
	 * Desc : Retriving parameter value
	 * @author A-9175
	 * @return
	 * @throws InterruptedException
	 */
	public String getParameterValue_Filter() throws InterruptedException {
		String locator = xls_Read.getCellValue(sheetName, "paramValue;name");
		List<WebElement> parameterValueFields =new ArrayList<WebElement>();
		parameterValueFields=driver.findElements(By.name(locator));
		    	WebElement parameterField = null;
		    	String paramValue = null;
		    	for (int i = 0; i < parameterValueFields.size(); i++) 
		    	{
		    	    WebElement element = parameterValueFields.get(i);
		    	    if (element.isDisplayed())
		    	    {
		    		parameterField = element;
		    		paramValue=element.getAttribute("value");
		    		break;
		    	    }   
		    	}
		    	return paramValue;
	}
	
	/**
	 * Desc : Changing a parameter value to desired value
	 * @author A-9175
	 * @param parameterKey
	 * @param optionVal
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void changeParameterValue(String parameterKey,String optionVal) throws InterruptedException, IOException {
		String locator = xls_Read.getCellValue(sheetName, "Key_parameterValue;xpath");
        locator=locator.replace("Key",data(parameterKey));
        locator=locator.replace("Value",data(optionVal));
        driver.findElement(By.xpath(locator)).click();
		waitForSync(5);
		save();
		handleAlert("Accept", " Maintain Airline ");
		waitForSync(5);
		
	}
	

	/**
	 * Desc : Saving details
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void save() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Save;name", "Save Button", ScreenName);		
	}



}
