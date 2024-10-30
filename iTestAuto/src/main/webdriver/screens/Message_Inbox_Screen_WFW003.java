package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Message_Inbox_Screen_WFW003 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "Message_Inbox_Screen_WFW003";
	String screenName = "Message_Inbox_Screen";
	String screenId = "WFW003";

	public Message_Inbox_Screen_WFW003(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description... Click On List Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickOnList() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_list;xpath","List Button", screenName);	

	}
/**
 * Description...	Change From Date
 * @param fromDate
 * @throws InterruptedException
 */
	public void changeFromDate(String fromDate) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_fromDate;xpath", data(fromDate), "From Date", screenName);

	}
/**
 * Description...	Select Check Box
 * @throws InterruptedException
 * @throws IOException 
 */
	// selects check box
	public void selectCheckBox() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chkBox_select;xpath","Select Check Box", screenName);
				
/**
 * Description... Click On View
 */
	}	
	public void clickOnView() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_view;xpath","View Button", screenName);	

	}
	/**
	 * Description... Verify Message
	 * @param FlightNo1
	 * @param FlightNo1Date
	 * @throws Exception
	 */
	public void verifyMessage(String FlightNo1,String FlightNo1Date) throws Exception {
		waitForSync(2);
		switchToWindow("storeParent");
		switchToWindow("child");
		ele = findDynamicXpathElement("txtArea_rawMsg;xpath", sheetName,
				"Raw Message", screenName);
		String actualText = ele.getText();	
		String expectedText = "The following exceptions are identified as part of AutoLoadplan for flight LH-"+data(FlightNo1)+" on "+data(FlightNo1Date)+
			" 18:00 at FRA Reason Code: Incompitable Scc's Planned,Planned Weight > 5000";
		verifyScreenText(sheetName, expectedText, actualText,"Error Message","Error Message" +
				"//1. Login to iCargo \n , 2.Complete Manual Auto load Plan Process\n ,3.Invoke WFW003 screen \n 4.Click on list button \n ");
		
	}

} 