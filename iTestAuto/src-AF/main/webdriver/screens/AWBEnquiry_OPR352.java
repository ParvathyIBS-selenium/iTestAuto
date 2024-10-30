package screens;

import org.testng.Assert;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AWBEnquiry_OPR352 extends CustomFunctions {
	public AWBEnquiry_OPR352(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "AWBEnquiry_OPR352";
	public String ScreenName = "AWBEnquiry_OPR352";

	/**
	 * Description...  Verify the SCC Code
	 * @param scc
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifySCC(String scc) throws InterruptedException, AWTException {
		
		By ele = getElement(sheetName, "txt_SCC;xpath");
		List<WebElement> Scc=driver.findElements(ele);
		String actScc="";
		String locatorName = xls_Read.getCellValue(sheetName, "txt_SCC;xpath");
		for(int i=1;i<=Scc.size()-2;i++){
			String xpath= locatorName+"["+i+"]";
			actScc =actScc+ driver.findElement(By.xpath(xpath)).getText().trim();
		}
		verifyScreenText(sheetName, scc, actScc, " SCC verification ", ScreenName);
	}
/**
 * Description...  Verify the Shipper AND Consignee
 * @param shipper
 * @param Consignee
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyShipperANDConsignee(String shipper, String Consignee) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "txt_Shipper;xpath");
		String actshipper = driver.findElement(ele).getText();
		verifyScreenText(sheetName, shipper, actshipper, " Shipper Name verification ", ScreenName);

		ele = null;
		ele = getElement(sheetName, "txt_Consignee;xpath");
		String actConsignee = driver.findElement(ele).getText();
		verifyScreenText(sheetName, Consignee, actConsignee, "Consignee Name verification ", ScreenName);
	}
/**
 * Description...  Verify the Message Contents
 * @param msg
 * @param msgContents
 * @throws Exception
 */
	public void verifyMsgContents(String msg, String msgContents[]) throws Exception {
			//get the message type
			String partialxpath = xls_Read.getCellValue(sheetName, "btn_messageContent;xpath");
			String xpath="//*[text()='"+msg+"'"+partialxpath;
			
			//move to message type and get the message content
			WebElement ele = driver.findElement(By.xpath(xpath));
			moveScrollBar(ele);
			String actText =  getElementText(ele,"Message Content","Message");
			waitForSync(2);
			
			//compare with expected data
			for (int i = 0; i < msgContents.length; i++) {
				verifyScreenText(sheetName, msgContents[i], actText, "Msg verification", ScreenName);
			}

		waitForSync(1);
	}
/**
 * Description...  Verify the Shipment AND Route
 * @param shipmnt
 * @param route 
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyShipmntANDRoute(String shipmnt, String route) throws InterruptedException, AWTException {
	
		Actions actionDriver = new Actions(driver);
		By element = getElement(sheetName, "txt_shipmentDescription;xpath");
		WebElement elementtobeClicked = driver.findElement(element);
		moveScrollBar(elementtobeClicked);
		actionDriver.moveToElement(elementtobeClicked).perform();
		waitForSync(2);
		
		
		By ele = getElement(sheetName, "txt_shipmentDesTooltip;xpath");
		String actshipmnt = driver.findElement(ele).getText();
		verifyScreenText(sheetName, shipmnt, actshipmnt, " Shipment Description verification ", ScreenName);

		ele = null;
		ele = getElement(sheetName, "txt_Route;xpath");
		String actroute = driver.findElement(ele).getText();
		verifyScreenText(sheetName, route, actroute, "Route verification ", ScreenName);
	}
/**
 * Description...  Verify the Pieces Weight
 * @param pcs
 * @param wgt
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyPcsWgt(String pcs, String wgt) throws InterruptedException, AWTException {
		By ele = getElement(sheetName, "txt_StatedPcs;xpath");
		String actpcs = driver.findElement(ele).getText();
		verifyScreenText(sheetName, pcs, actpcs, " Stated Pcs verification ", ScreenName);

		ele = null;
		ele = getElement(sheetName, "txt_StatedWt;xpath");
		String actwgt = driver.findElement(ele).getText(); //it will return 10/100kg
		String atwgt[]=actwgt.split("/"); //now it ll be 100kg
		
		verifyScreenText(sheetName, wgt, atwgt[1].replace("kg","").trim(), "Stated weight verification ", ScreenName);
	}
/**
 * Description... Verify the Flight Number
 * @param fltNo
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyFlightNo(String fltNo) throws InterruptedException, AWTException {
		
		enterValueInTextbox(sheetName, "list_SearchForWidget;xpath", "booking info", "Flight Number verification ", ScreenName);
		waitForSync(1);
		By bkginfo = getElement(sheetName, "list_SearchForWidget;xpath");
		driver.findElement(bkginfo).sendKeys(Keys.ENTER);
		waitForSync(5);
		
		By ele = getElement(sheetName, "txt_BookingInfoFlightNo;xpath"); //1st flight number
		String actfltNo = driver.findElement(ele).getText();
		verifyScreenText(sheetName, fltNo, actfltNo, "Flight Number  verification ", ScreenName);
	}
/**
 * @author A-7271
 * @param prefix
 * @param awbNo
 * Desc : List AWB
 * @throws InterruptedException 
 * @throws IOException 
 */
	public void listAWB(String prefix,String awbNo) throws InterruptedException, IOException
	{
		waitTillScreenload(sheetName, "inbx_shipmentPrefix;name", "Shipment Prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "Shipment Prefix",
				ScreenName);
		enterValueInTextbox(sheetName, "inbx_docNum;name", data(awbNo), "AWB No", ScreenName);
		clickWebElement(sheetName, "btn_list;id", "List Button", ScreenName);
		waitForSync(4);

	}
	/**
	 * @Desc : selectWidgetToAdd
* @author A-9175
	 * @param widgentname
	 */
	public void selectWidgetToAdd(String widgentname) 
	{
		try 
		{
			String locator = xls_Read.getCellValue(sheetName, "btn_addWidget;xpath");
			locator = locator.replace("widgetname", data(widgentname));
			driver.findElement(By.xpath(locator)).click();
			
	        // Wait for the widget to be visible
			String widgetselected = xls_Read.getCellValue(sheetName, "icon_selectedWidget;xpath");
			widgetselected = widgetselected.replace("widgetname", data(widgentname));

			WebDriverWait wait = new WebDriverWait(driver, 20);
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(widgetselected)));
	        
			writeExtent("Pass", "Selected "+data(widgentname)+"  Widget on" + ScreenName + " Page");
		}
		catch (Exception e) 
		{
			writeExtent("Fail", "could not select "+data(widgentname)+"  Widget  on" + ScreenName + " Page");
		}

	}

	/**
	 * @Desc :verifySCCs in widget 
	 * @author A-9175
	 * @param exptscc
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifySCCs(String[] exptscc) throws InterruptedException, AWTException {
		List<String> expectedScc = Arrays.asList(exptscc);
		try 
		{
			By ele = getElement(sheetName, "lbl_SCCsdisplayed;xpath");
			List<WebElement> actSccsList = driver.findElements(ele);
			ArrayList<String> actualSccs = new ArrayList<>(); 
			expectedScc = Arrays.asList(exptscc);
			System.out.println(expectedScc);
			 
			 for (WebElement sccs : actSccsList) 
			 {
				 actualSccs.add(sccs.getText());
		     }
			 
			 System.out.println(expectedScc);
			 System.out.println(actualSccs);

			 if(actualSccs.containsAll(expectedScc))
			{
				writeExtent("Pass", "Found SCC's "+actualSccs+" on" + ScreenName + " Page");
			}
		} 
		catch (Exception e) 
		{
			writeExtent("Fail", "Failed to verify SCC's "+expectedScc+" on" + ScreenName + " Page");
		}    
	}
	/**
	 * @desc : close widget
	 * @author A-9175
	 * @param widgetname
*/
	public void closeWidget(String widgetname)
	{
		try 
		{
			String locator = xls_Read.getCellValue(sheetName, "btn_closeSelectedWidgent;xpath");
			locator = locator.replace("widgetname", data(widgetname));
			driver.findElement(By.xpath(locator)).click();

			writeExtent("Pass", "Closed "+data(widgetname)+"  Widget  on" + ScreenName + " Page");

		} 
		catch (Exception e) 
		{
			writeExtent("Fail", "could not close "+data(widgetname)+"  Widget on" + ScreenName + " Page");
		}

	}




	/**
	 * Description... Verification of Not Ready for carriage
	 * @throws Exception
	 */
	public void verificationOfNotRFCStatusOnOPR352() throws Exception {

		waitForSync(3);
		verifyElementDisplayed(sheetName, "lbl_NotRFC;xpath", "5", ScreenName, "Ready For Carriage");

	}

	
	/**
	 * Description... Verification of Ready for carriage
	 * @throws Exception
	 */
	public void verificationOfRFCStatusOnOPR352() throws Exception {

		waitForSync(3);
		verifyElementDisplayed(sheetName, "lbl_RFC;xpath", "5", ScreenName, "Ready For Carriage");

	}
/**
 * Description... Click the History Button
 * @throws Exception
 */
	public void clickHistory() throws Exception {
		waitForSync(2);
		clickButtonSwitchWindow(sheetName, "btn_AuditHistory;xpath", ScreenName, "Audit History button");
		waitForSync(3);

	}
/**
 * Description... Verify History 
 * @param pmy Key
 */
	public void verifyHistory(String pmKey) {

		try {
			String locatorName = xls_Read.getCellValue(sheetName, "lbl_HistoryTransaction;xpath");
			String xpathExpression =locatorName+pmKey+"')]";
			WebElement row = driver.findElement(By.xpath(xpathExpression));
			String status = row.getText();
			System.out.println(status);
			writeExtent("Pass", pmKey + " status is" + status);

		} catch (Exception e) {

			System.out.println(pmKey + " status is not Sent");
			test.log(LogStatus.FAIL, pmKey + " status is not Sent");

		}

	}
/**
 * Description... Close History button
 * @throws Exception
 */
	public void closeHistory() throws Exception {

		clickButtonSwitchtoParentWindow(sheetName, "btn_closeAuditHistory;xpath", "Close button", ScreenName);
		waitForSync(3);

	}


/**
 * Description... Click the Message Detail Button
 * @throws Exception
 */
	public void clickMessageDetails() throws Exception {

		clickWebElement(sheetName, "btn_selectMessage;xpath", "Message button", ScreenName);
		waitForSync(3);

	}

/**
 * Description... Select Messages
 * @param msgType
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectMessages(String msgType) throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_selectMessage;xpath", "Message buton", ScreenName);
		waitForSync(5);
		//get the message name
		String partialxpath = xls_Read.getCellValue(sheetName, "btn_messageFSU;xpath");
		String xpath=partialxpath+msgType+"']";

		//move to message type and get the message content
		WebElement ele = driver.findElement(By.xpath(xpath));
		moveScrollBar(ele);
		ele.click();
		waitForSync(2);
	}

}