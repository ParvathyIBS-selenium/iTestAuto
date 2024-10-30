package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.GetPageSource;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListChecksheet_CHK003 extends CustomFunctions {

	public ListChecksheet_CHK003(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	String sheetName = "ListChecksheet_CHK003";
	public String screenName = "ListChecksheet_CHK003";


	
	
	public void selectTransaction(String transactionIndex) throws InterruptedException, IOException{
		
    clickWebElementByWebDriver(sheetName, "drpdn_transaction;xpath", "Transaction Drop Down", screenName);
    waitForSync(2);   
    String locator=xls_Read.getCellValue(sheetName, "txt_transaction;xpath");
	locator=locator.replace("*", transactionIndex);
	 driver.findElement(By.xpath(locator)).click();   

	}
	
	public void clickList() throws InterruptedException, IOException{
		
		clickWebElement(sheetName, "btn_list;id", "List Button", screenName);
		waitForSync(3);
	}
	
	public void enterFlightDetails(String fltCode,String fltNum, String fltDate) throws InterruptedException, AWTException{
		
		enterValueInTextbox(sheetName, "txt_fltCode;id",data(fltCode), "Flight Code", screenName);
		enterValueInTextbox(sheetName, "txt_fltNum;id", data(fltNum),"Flight Number", screenName);
		enterValueInTextbox(sheetName, "txt_fltDate;id", data(fltDate),"Flight Date", screenName);
		
	}
	
	public void selectULDType() throws InterruptedException, IOException{
		clickWebElement(sheetName, "radiobtn_ULD;xpath", "ULD Radio Button", screenName);	
		waitForSync(2);
	}
	/**
	 * @author A-9847
	 * @Desc To verify the ULD checksheet templates displayed
	 * @param UldNum
	 * @param templates
	 */
	public void verifyChecksheetTemplate(String UldNum,String templates[]){

		try{
			String locator = xls_Read.getCellValue(sheetName, "txt_ulddetails;xpath");
			locator=locator.replace("*", data(UldNum));

			int tempcount=0;

			List <WebElement> ulds=driver.findElements(By.xpath(locator));

			for(WebElement ele:ulds)
			{	
				for(int i=0;i<templates.length;i++){								
					if(ele.getText().equals(templates[i]))
						tempcount++;					
				}	
			}
			if(tempcount==templates.length)		
				writeExtent("Pass", "Successfully verified ULD templates on " + screenName);
			else
				writeExtent("Fail", "Failed to verify ULD templates on " + screenName);
		}

		catch(Exception e){
			writeExtent("Fail", "Failed to verify ULD templates on " + screenName);	
		}
	}

	public void verifyTemplatesFromCHK002(String templates[]){

		String screenname="Capture_Checksheet_CHK002";
		try{
			waitForSync(3);
			String locator = xls_Read.getCellValue(sheetName, "htmldiv_templates;xpath");
			List <WebElement> temps=driver.findElements(By.xpath(locator));

			int tempcount=0;

			for(WebElement ele:temps)
			{	
				for(int i=0;i<templates.length;i++){					
					if(ele.getText().equals(templates[i]))
						tempcount++;

				}	
			}
			if(tempcount==templates.length)		
				writeExtent("Pass", "Successfully verified ULD templates on " + screenname);
			else
				writeExtent("Fail", "Failed to verify ULD templates on " + screenname);
		}

		catch(Exception e){
			writeExtent("Fail", "Failed to verify ULD templates on " + screenname);	
		}
	}

	public void selectUldCheckBox(String UldNum){
		
		try{
		String locator = xls_Read.getCellValue(sheetName, "chk_uld;xpath");
		locator=locator.replace("*", data(UldNum));
		driver.findElement(By.xpath(locator)).click();
		writeExtent("Pass", "Successfully Selected the ULD on " + screenName);
		}
		catch(Exception e){
			
			writeExtent("Fail", "Couldnt Select the ULD on " + screenName);
		}
		
	}
	
	
public void clickDetails() throws InterruptedException, IOException{
		
		clickWebElement(sheetName, "btn_details;id", "Details Button", screenName);
		waitForSync(3);
	}
	
	public void closeFromCHK002() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_close;id", "Close Button", screenName);
		waitForSync(4);
		
	}
	
	public void clickClose() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_chk003Close;id", "Close Button", screenName);
		waitForSync(2);
		
	}
	
	public void enterUldNumber(String uldNum) throws InterruptedException{
		
		enterValueInTextbox(sheetName, "txt_Uldnumber;id",data(uldNum), "UldNumber", screenName);
	}
	
	
	
	public void verifyChecksheetTemplates(String UldNum,String templates[]){
		
		try{
		String locator = xls_Read.getCellValue(sheetName, "txt_ulddetails;xpath");
		locator=locator.replace("*", data(UldNum));
		
		List <WebElement> ulds=driver.findElements(By.xpath(locator));
		
			for(WebElement ele:ulds)
		{
				
				for(int i=0;i<templates.length;i++){
			if(ele.getText().equals(templates[i]))	
				writeExtent("Pass", "Successfully verified ULD template as "+templates[i] +" on " + screenName);	
		}
		
	}
	}
	catch(Exception e){
		writeExtent("Fail", "Failed to verify ULD templates on " + screenName);	
	}
	}
}
