package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListCheckSheetConfig_SHR094 extends CustomFunctions{
	public ListCheckSheetConfig_SHR094(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="ListCheckSheetConfig_SHR094";
	public String ScreenName="ListCheckSheetConfig_SHR094";
	
	/**
	 * Description... Check Sheet Type
	 * @param mname
	 * @throws InterruptedException
	 */
	public void selectCheckSheetType(String name) throws InterruptedException{
		waitForSync(2);
		selectValueInDropdown(sheetName, "drpDown_checkSheetType;id",data(name), "Check Sheet Type", "VisibleText");	
		waitForSync(3);
	}
	/**
	 * @author A-9847
	 * @Desc To enter the Airport Group
	 * @param airportGrp
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void enterAirportGrp(String airportGrp) throws InterruptedException, IOException {
        enterValueInTextbox(sheetName, "inbx_airportGrp;id", data(airportGrp), "Airport Group",ScreenName);
        waitForSync(4);

  }
    /**
* @author A-8783
* @param scc
* @throws InterruptedException
* @throws IOException
*/
public void enterScc(String scc) throws InterruptedException, IOException {
enterValueInTextbox(sheetName, "inbx_scc;id", data(scc), "SCC",ScreenName);
waitForSync(4);

}

	/**
	 * @author A-9847
	 * @Desc To enter the SCC Group
	 * @param sccGrp
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterSccGrp(String sccGrp) throws InterruptedException, IOException {
        enterValueInTextbox(sheetName, "inbx_sccGrp;id", data(sccGrp), "SCC Group",ScreenName);
        waitForSync(4);

  }
    /**
     * @author A-10330
	 * Description - verification of currdate lies in from and to date
	 * param currDate 
	 * @throws InterruptedException
	 * @throws AWTException
*/
	public void verifyFromAndToDate(String currDate) throws InterruptedException, AWTException {
		waitForSync(2);
		String fromDateText=getElementText(sheetName, "txt_fromdate;xpath", "From date", ScreenName);
		waitForSync(2);
		String toDateText=getElementText(sheetName, "txt_todate;xpath", "From date", ScreenName);

		String fromDate=fromDateText.trim();
		String toDate=toDateText.trim();

		try {
			String locator = xls_Read.getCellValue(sheetName, "txt_fromdate;xpath");

			driver.findElement(By.xpath(locator)).isDisplayed();
			waitForSync(2);
			String locator1 = xls_Read.getCellValue(sheetName, "txt_todate;xpath");
			driver.findElement(By.xpath(locator1)).isDisplayed();

			SimpleDateFormat sd=new SimpleDateFormat("dd-MMM-yyyy");
			Date date1=sd.parse(fromDate);
			Date date2=sd.parse(currDate);
			Date date3=sd.parse(toDate);
			int result1=date2.compareTo(date1);
			int result2=date2.compareTo(date3);

			if(result1>0)
			{
				if(result2<0)
				
					writeExtent("Pass", "Successfully Verified " + currDate +"lies in"+fromDate+ "and"+toDate+"In" + ScreenName);
				
				else
				
					writeExtent("Fail", "Not Verified " + currDate +"lies in"+fromDate+ "and"+toDate+"In" + ScreenName); 
				
			}

		} catch (Exception e) {
			writeExtent("Fail", "Could not perform parsing of dates from string format to Date");
		}

	}


	
	/**
	 * Description... Select Transaction
	 * @param mname
	 * @throws InterruptedException
	 */
	public void selectTransaction(String transaction) throws InterruptedException{
		waitForSync(2);
		selectValueInDropdown(sheetName, "drpDown_transactionCode;id", transaction, "Transaction Type", "VisibleText");	
		waitForSync(3);
	}
	/**
     * To enter Airport
     * @param airport
     * @throws InterruptedException
     * @throws IOException
     */
     
     public void enterAirport(String airport) throws InterruptedException, IOException {
           enterValueInTextbox(sheetName, "inbx_airport;id", data(airport), "Airport",ScreenName);
           waitForSync(4);

     }
     /**
      * 
      * @param checkSheetType
      * @param transaction
      * @param templateId
      * @param fromDate
      * @param toDate
      * @param scc
      * @throws Exception
      * Desc : create checksheet
      */
     public void createCheckSheet(String checkSheetType, String transaction, String templateId, String fromDate, String toDate, String scc) throws Exception {
 		clickWebElement(sheetName, "btn_create;name", "Create button", ScreenName); 	
 		waitForSync(2);
 		switchToWindow("storeParent");
 		switchToWindow("child");
 		selectValueInDropdown(sheetName, "lst_checkSheetType;name",data(checkSheetType), "Check Sheet Type", "VisibleText");
 		selectValueInDropdown(sheetName, "lst_transaction;name",data(transaction), "Transaction code", "VisibleText");
 		enterValueInTextbox(sheetName, "txt_templateId;name", data(templateId), "Template id",ScreenName);
 		enterValueInTextbox(sheetName, "txt_fromDate;name", data(fromDate), "Valid from date",ScreenName);
 		enterValueInTextbox(sheetName, "txt_toDate;name", data(toDate), "Valid to date",ScreenName);
 		enterValueInTextbox(sheetName, "txt_SCCcode;name", data(scc), "SCC",ScreenName);
 		clickWebElement(sheetName, "btn_save;name", "save button", ScreenName); 
 		clickWebElement(sheetName, "btn_close;name", "close button", ScreenName); 
 		waitForSync(2);
 		switchToWindow("getParent");
 		
 		
 	}

	/**
	 * Description... List Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listDetails() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_List;id", "list details", ScreenName); 	
	}
	/**
	 * Desc : Selecting status is active or inactive
	 * @author A-9175
	 * @param status
	 * @throws InterruptedException
	 */
	public void selectStatus(String status) throws InterruptedException{
		waitForSync(2);
		selectValueInDropdown(sheetName, "drpDown_statusCose;id", status, "Status Type", "VisibleText");	
		waitForSync(3);
	}
	
	/**
	 * Desc:  Entering Commodity code
	 * @author A-9175
	 * @param commCode
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterCommodityCode(String commCode) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName, "txt_SCCcode;id", data(commCode), "Commodity Code",ScreenName);
		waitForSync(4);

	}

	/**
	 * Description... 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public String getTemplateID() throws InterruptedException, AWTException{
		
		
		String elementText=getElementText(sheetName, "lbl_templateValue;xpath", "Template Value", ScreenName);
		waitForSync(2);
		return elementText.trim();
	
	}
	
	

}
