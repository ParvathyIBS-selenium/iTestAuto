package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class PrintLabel_OPR343 extends CustomFunctions {

	public PrintLabel_OPR343(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	public String sheetName = "PrintLabel_OPR343";
	public String screenName = "Print label";


/**
 * @author A-10690
 * Description... select label
 * @param labeltype
 * @throws InterruptedException
 * @throws IOException 
 */
public void selectLabel(String labeltype) throws InterruptedException, IOException {
	selectValueInDropdown(sheetName, "list_SelectLabel;name",
			data(labeltype), "select label type",
			"VisibleText");
	waitForSync(2);
}

/**
	 *  @author A-10328
	 * Description... List Flight Details with satchel
	 * @param carrierCode
	 * @param fltNo
	 * @param fltDate
	 * @param satchel
	 * @throws InterruptedException
	 * @throws IOException 
	 */

public void  listFlightDetailsWithSatchel(String carrierCode,String fltNo,String fltDate,String Satchel1) throws InterruptedException, IOException

	{

enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode), "Carrier Code", screenName);
enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNo), "Flight Number", screenName);
enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
enterValueInTextbox(sheetName, "inbx_satchelNumber;id", data(Satchel1), "Satchel Number", screenName);
clickWebElement(sheetName, "btn_List;id","List Button", screenName);	
waitForSync(4);
	}


/**
 * @author A-10328
 * @throws IOException
 * @param Satchel
 * @param Flightnum
 * Desc : verify the satchel
 */


public void verifySatchel(String satchelNo,String Flightnum) throws IOException
{
	try
	{
String locator = xls_Read.getCellValue(sheetName, "txt_satchel;xpath");
locator=locator.replace("*",data(Flightnum));
		
String actText = driver.findElement(By.xpath(locator)).getText();
verifyScreenTextWithExactMatch(sheetName, satchelNo,actText, "Satchel is verified equal", "PrintLabel");
		
}
catch(Exception e)
	{
		writeExtent("Fail", "Satchel is not equal "+screenName);
		
	}
	}


/**
 *  @author A-10690
 * Description... List Flight Details
 * @param carrierCode
 * @param fltNo
 * @param fltDate
 * @throws InterruptedException
 * @throws IOException 
 */
	public void  listFlightDetails(String carrierCode,String fltNo,String fltDate) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(fltNo), "Flight Number", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;id", data(fltDate), "Flight Date", screenName);
		clickWebElement(sheetName, "btn_List;id","List Button", screenName);	
		waitForSync(4);
				
	}
	
	/**
	 * @author A-10690
	 * Description... Click print button
	 * @param flightnum
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickprint(String flightnum) throws InterruptedException, IOException {
		String locator = xls_Read.getCellValue(sheetName, "table_SelectFlight;xpath");
		locator=locator.replace("*",data(flightnum));
		driver.findElement(By.xpath(locator)).click();
		clickWebElement(sheetName,"btn_print;name", "print button", screenName);
		
				waitForSync(10);
				switchToFrame("default");
				waitForSync(10);     
				try {

				while (driver.findElement(
				By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
				.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_OK;xpath",
				"OK Button", screenName);
				Thread.sleep(10000);
				}
				} catch (Exception e) {
				}

				Thread.sleep(12000);
				switchToFrame("contentFrame", "OPR343");
				Thread.sleep(12000);
		
	}
	/**
	 * @author A-10690
	 * @throws IOException
	 * @param Satchel
	 * @param Flightnum
	 * Desc :store the generated satchel 
	 */
	public void verifySatchelGeneration(String Satchel,String Flightnum) throws IOException
	{
		try
		{
			
		
		waitForSync(8);
		
		String locator = xls_Read.getCellValue(sheetName, "txt_satchel;xpath");
		locator=locator.replace("*",data(Flightnum));
		
		String satchel = driver.findElement(By.xpath(locator)).getText();

		if(satchel.equals(""))
		{
			writeExtent("Fail", "satchel is not generated  on "+screenName);

		}
		else
		{
			writeExtent("Pass", "satchel is generated with value "+satchel+" on "+screenName);	

		}

		waitForSync(6);
		map.put(Satchel, satchel);
		System.out.println(data(Satchel));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writeExtent("Fail", "Couldn't  generate satchel "+screenName);
		}
	}




}