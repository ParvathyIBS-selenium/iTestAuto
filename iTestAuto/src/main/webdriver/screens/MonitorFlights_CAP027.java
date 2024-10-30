package screens;



import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class MonitorFlights_CAP027 extends CustomFunctions {

	String sheetName="MonitorFlights_CAP027";
	String screenName="MonitorFlights : CAP027";

	public MonitorFlights_CAP027(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
	/**
	 * Description... List Flight
	 * @param FlightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void listFlight(String FlightNumber, String flightDate)
			throws InterruptedException, AWTException, IOException {
		Thread.sleep(3000);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name",
				data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name",
				data(flightDate), "Flight Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_list;name", "List", screenName);
		Thread.sleep(3000);
	}
	
	

	/**
	 * Description... Verify Capacity Summary Remaining Sales
	 * @param REM_FS_Capacity_Sales_Wt
	 * @param REM_FS_Capacity_Sales_Vol
	 * @param REM_FS_Capacity_Sales_LDC
	 * @param REM_FS_Capacity_Sales_LDP
	 * @param REM_FS_Capacity_Sales_MDP
	 * @throws IOException 
	 */
	public void verifyCapacitySummaryRemainingSales(String REM_FS_Capacity_Sales_Wt,String REM_FS_Capacity_Sales_Vol,String REM_FS_Capacity_Sales_LDC,String REM_FS_Capacity_Sales_LDP,String REM_FS_Capacity_Sales_MDP) throws IOException
	{
		waitForSync(4);
		int verfCols[]={2,3,4,5,6};
		String[] actVerfValues={data(REM_FS_Capacity_Sales_Wt),data(REM_FS_Capacity_Sales_Vol),data(REM_FS_Capacity_Sales_LDC),data(REM_FS_Capacity_Sales_LDP),data(REM_FS_Capacity_Sales_MDP)};
		verify_tbl_records_multiple_cols(sheetName, "tbl_capacitySummay;xpath", "//td", verfCols, "RemainingFS Capacity - Sales", actVerfValues);
	}
	/**
	 * Description... Verify Capacity Summary
	 * @param actVerfValues
	 * @param index
	 * @param row
	 * @param verfCols
	 * @param pmKey
	 */
	public void verifyCapacitySummary(String[] actVerfValues,int index, int row, int verfCols[], String pmKey){
				
		  
			   for (int j = 0; j<verfCols.length; j++)
			   {
			   
				   String xpath = "(//table[@id = 'capTable'])["+ index +"]//tbody//tr"+"["+ row +"]" + "//td" + "["+ verfCols[j] +"]";
				   WebElement col = driver.findElement(By.xpath(xpath));
			   
			   
			   
			   if (col.getText().equals(actVerfValues[j])) {
					System.out.println("found true for " + actVerfValues[j]);
					
					onPassUpdate(screenName, actVerfValues[j], col.getText(), "Table verification against " + pmKey + " On ",
							"Table verification");

				} else {
					onFailUpdate(screenName, actVerfValues[j], col.getText(), "Table verification against " + pmKey + " On ",
							"Table verification");

				}
			   
			   }
			   		
		   
		}
/**
 * Description... Verify Capacity Summary Consumed Capacity
 * @param ConsumedCapacity_Wt
 * @param ConsumedCapacity_Vol
 * @param ConsumedCapacity_LDC
 * @param ConsumedCapacity_LDP
 * @param ConsumedCapacity_MDP
 * @throws IOException 
 */
	public void verifyCapacitySummaryConsumedCapacity(String ConsumedCapacity_Wt,String ConsumedCapacity_Vol,String ConsumedCapacity_LDC,String ConsumedCapacity_LDP,String ConsumedCapacity_MDP) throws IOException
	{
		waitForSync(4);
		int verfCols[]={2,3,4,5,6};
		String[] actVerfValues={data(ConsumedCapacity_Wt),data(ConsumedCapacity_Vol),data(ConsumedCapacity_LDC),data(ConsumedCapacity_LDP),data(ConsumedCapacity_MDP)};
		verify_tbl_records_multiple_cols(sheetName, "tbl_capacitySummay;xpath", "//td", verfCols, "Total Consumed Capacity", actVerfValues);
	}
	/**
	 * Description... Close View Cap Summary
	 * @throws Exception
	 */
	public void closeViewCapSummary() throws Exception
	{
		clickWebElement(sheetName, "btn_viewCapSummaryClose;id", "CloseButton", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "CAP027");
	}
/**
 * Description... Verify Capacity Summary To Verify Wt Vol	
 * @param Weight
 * @param Volume
 * @param Contour
 * @param row
 * @param verfCols
 * @param pmKey
 */
	public void verifyCapacitySummaryToVerifyWtVol(String Weight , String Volume, String Contour,int row, int verfCols[],String pmKey){
               
               String actwt=data(Weight);
              String actVol=data(Volume);
              String contour=data(Contour);
               
                   String[] actVerfValues={actwt,actVol,contour,contour,contour};
                 
              
                      for (int j = 0; j<verfCols.length; j++)
                      {
                            String xpath = "//table[@id = 'capTable']//tbody//tr"+"["+ row +"]" + "//td" + "["+ verfCols[j] +"]";
                            WebElement col = driver.findElement(By.xpath(xpath));        
                      
                      if (col.getText().equals(actVerfValues[j])) {
                                System.out.println("found true for " + actVerfValues[j]);
                                
                                onPassUpdate(screenName, actVerfValues[j], col.getText(), "Table verification against " + pmKey + " On ",
                                              "Table verification");

                         } else {
                                onFailUpdate(screenName, actVerfValues[j], col.getText(), "Table verification against " + pmKey + " On ",
                                              "Table verification");
                         }
                      }

                      }
/**
 * Description... View Cap Summary
 * @throws Exception
 */
	public void viewCapSummary() throws Exception {
		waitForSync(4);
		clickButtonSwitchWindow(sheetName, "btn_viewCapSummary;id", "View Capacity Summary", screenName);
}
	/**
	 * Description... Switch To Booking Status	
	 * @throws InterruptedException
	 * @throws IOException 
	 */
// Switch to Confirmed Booking or Queued booking based on the Booking status
	public void switchToBookingStatus() throws InterruptedException, IOException {
		waitForSync(5);
		String bookingStatus = data("BookingStatus");
		if (bookingStatus.contains("CONFIRMED")) {
			waitForSync(5);
			clickWebElement(sheetName, "btn_confirmed;xpath", "CONFIRMED",
					screenName);
		} else if (bookingStatus.contains("QUEUED")) {
			waitForSync(5);
			clickWebElement(sheetName, "btn_queued;xpath", "QUEUED", screenName);
		} else
			clickWebElement(sheetName, "btn_waitlisted;xpath", "waitlisted",
					screenName);
	}
/**
 * Description... Capture SCC Tool Tip
 * @param AWBNo
 */
	// Capture SCC from tool tip
	public void captureSCCToolTip(String AWBNo) {
		waitForSync(5);
		String bookingStatus = data("BookingStatus");
		if (bookingStatus.contains("CONFIRMED")) {
			waitForSync(2);
			String dynxpath = "//tbody/tr" + "[contains(.,'" + data(AWBNo)
					+ "')]//td[30]//div";
			System.out.println(dynxpath);
			String element = driver.findElement(By.xpath(dynxpath))
					.getAttribute("title");
			/* String title = element.getAttribute("title"); */

			System.out.println("SCCs are:" + element);
			if (element.contains(data("toolTip"))) {
				System.out.println("scc matches");
			} else
				System.out.println("scc does not match");
		} else if (bookingStatus.contains("QUEUED")) {
			waitForSync(5);
			String dynxpath = "//tbody/tr" + "[contains(.,'" + data(AWBNo)
					+ "')]//td[31]//div";
			System.out.println(dynxpath);
			String element = driver.findElement(By.xpath(dynxpath))
					.getAttribute("title");
			/* String title = element.getAttribute("title"); */

			System.out.println("SCCs are:" + element);
			if (element.contains(data("toolTip"))) {
				System.out.println("scc matches");
			} else
				System.out.println("scc does not match");
		}
	}
/**
 * Description... Verify Table Details
 * @throws InterruptedException
 */
	// Verification for SCC astrisk
	public void verifyTableDetails() throws InterruptedException {
waitForSync(8);
String bookingStatus = data("BookingStatus");
if (bookingStatus.contains("CONFIRMED")) {
 
waitForSync(5);
int verfCols[] = { 30 };
String actVerfValues[] = { data("SCCserials") };
String pmKey = data("AWBNo");
System.out.println("PMKEY:" + pmKey);
waitForSync(25);
verify_tbl_records_multiple_cols_contains(sheetName,
 
"tble_sccVerification;xpath", "//td", verfCols, pmKey,
actVerfValues);
} else if (bookingStatus.contains("QUEUED")) {
waitForSync(5);
int verfCols[] = { 31 };
String actVerfValues[] = { data("SCCserials") };
String pmKey = data("AWBNo");
System.out.println("PMKEY:" + pmKey);
verify_tbl_records_multiple_cols_contains(sheetName,
"tble_sccVerification;xpath", "//td", verfCols, pmKey,
actVerfValues);
}
}

/**
 * Description... Get Text From View Cap Summary
 * @param loc
 * @return
 * @throws InterruptedException
 */

public List getTextFromViewCapSummary(String loc) throws InterruptedException
	{
	
		List<WebElement>ele=returnListOfElements(sheetName,loc);
		List<String> eleText=returnTextListOfElements(ele);
		
	
		return eleText;
		
	}

/**
 * Description... Verify Capacity Summary Sales and Handling
 * @throws InterruptedException
 */
	public void verifyCapacitySummarySalesHandling() throws InterruptedException {
		String xpathAct[] = { "tab_prvCap_SalesLDC;xpath", "tab_prvCap_SalesLDP;xpath", "tab_prvCap_SalesMDP;xpath",
				"tab_prvCap_handlingLDC;xpath", "tab_prvCap_handlingLDP;xpath", "tab_prvCap_handlingMDP;xpath", };

		String xpathExp[] = { "tab_remCap_SalesLDC;xpath", "tab_remCap_SalesLDP;xpath", "tab_remCap_SalesMDP;xpath",
				"tab_remCap_handlingLDC;xpath", "tab_remCap_handlingLDP;xpath", "tab_remCap_handlingMDP;xpath" };

		String[] xpathActTxt = new String[xpathAct.length];
		for (int i = 0; i < xpathAct.length; i++)
			xpathActTxt[i] = getElementText(sheetName, xpathAct[i], "Sales and Handling Capacity", screenName);
		String[] xpathExpTxt = new String[xpathExp.length];
		for (int i = 0; i < xpathExp.length; i++)
			xpathExpTxt[i] = getElementText(sheetName, xpathExp[i], "Sales and Handling Capacity", screenName);

		for (int i = 0; i < xpathActTxt.length; i++)
			verifyValueOnPage(xpathActTxt[i], xpathExpTxt[i], "Verify Sales and Handling Capacity", screenName,
					"Sales and Handling Capacity");

	}

}
