package screens;


import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ULDAndLocationHistory_ADD016 extends CustomFunctions

{
	
	String sheetName = "ULDAndLocationHistory_ADD016";
	String screenName = "ULDAndLocationHistory";

	public ULDAndLocationHistory_ADD016(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	

	
/**@author A-10328
 * Description - enter ULD Number 	
 * @param ULDNo
 * @throws InterruptedException
 */
public void enterULD(String ULDNo) throws InterruptedException
{
	enterValueInTextbox(sheetName, "inbx_ULDNumber;xpath", data(ULDNo),"ULD Number", screenName);
	waitForSync(1);
}

/**@author A-10328
 * Description - Enter from date and To date
 * @param startDate
 * @param EndDate
 * @throws InterruptedException
 */
public void enterFromAndToDate(String startDate, String EndDate) throws InterruptedException
{
	enterValueInTextbox(sheetName, "inbx_fromDate;xpath", data("startDate"),"From Date", screenName);
	enterValueInTextbox(sheetName, "inbx_toDate;xpath", data("EndDate"),"To Date", screenName);
}

/**@author A-10328
 * Description - click List
 * @throws InterruptedException
 * @throws IOException
 */

public void list() throws InterruptedException, IOException
{
	clickWebElement(sheetName, "btn_list;id", "List Button", screenName);
	waitForSync(2);
}
/**@author A-9844
 * Description - enter location 	
 * @param ULDNo
 * @throws InterruptedException
 */
public void enterLocation(String location) throws InterruptedException
{
	enterValueInTextbox(sheetName, "inbx_location;id", data(location),"Location", screenName);
	waitForSync(1);
}


/**@author A-10328
 * Description - Verify shipment Details in the table
 * @param actverfValues
 */

public void verifyShipmentDetails(String []actverfValues)

{
	String locator = xls_Read.getCellValue(sheetName, "tbl_ULDNo;xpath");
	WebElement elem=driver.findElement(By.xpath(locator));

	if(elem.isDisplayed())
	{   
		for(int i=0;i<actverfValues.length;i++)
		{

			String locatorValue = xls_Read.getCellValue(sheetName, "tbl_ULDDetails;xpath");
			locatorValue=locatorValue.replace("*", actverfValues[i]);
			
			String actText = driver.findElement(By.xpath(locatorValue)).getText();
			if(actText.contains(actverfValues[i]))
			{
				writeExtent("Pass","Successfully verifed "+ actverfValues[i] +" on" +screenName);
			}
			else
			{
				writeExtent("Fail","Could not verify "+ actverfValues[i]+ " on "+screenName);
			}

		}
	}
}

}
