package screens;


import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Cgomon_old extends CustomFunctions {
	
	public CustomFunctions customFuction;
	String sheetName = "cgomon_screen";
	String screenName = "cgomon_screen";
	

	public Cgomon_old(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelreadwrite, xls_Read);
	}
	
	
	
	
	/**
	 * Desc: Click Inbound message
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void clickInboundMessage() throws InterruptedException, IOException {
		    waitForSync(3);
			clickWebElementByWebDriver(sheetName, "btn_viewInboundMessage;xpath", " View Inbound Message ", screenName);
			waitForSync(3);
	}
	/**
	 * @author A-9847
	 * @Desc To verify the number of Records present based on given AWB/Flight
	 * @param expNoOfRec
	 * @param awbNumber
	 */
public void verifynumberOfRecords(int expNoOfRec,String awbNumber){
		
		String locatorAwb = xls_Read.getCellValue(sheetName, "cell_awbNumber;xpath").replace("*", data(awbNumber));
		List<WebElement> messages=driver.findElements(By.xpath(locatorAwb));	
		int actnNoOfRec=messages.size();	
		System.out.println(actnNoOfRec);	
	if(actnNoOfRec==expNoOfRec)	
		writeExtent("Pass", "Successfully verified the number of message records as "+expNoOfRec);
	else
		writeExtent("Fail", "Failed to verify the number of message records as "+expNoOfRec+" where the actual records came as "+actnNoOfRec);
		
	}
	/**
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : select advanced Search Option to click and select the option
	 */
	public void selectAdvancedSearchOption(String option) throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "inbx_advancedSearch;xpath", " Advanced Search Field  ", screenName);
		waitForSync(5);
		String locator = xls_Read.getCellValue(sheetName, "drp_advSearchOption;xpath").replace("*", data(option));
		driver.findElement(By.xpath(locator)).click();
		waitForSync(5);
		
		
	}
	/**
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : to enter the recipient address
	 */
	public void enterRecipientAddress(String address) throws InterruptedException, IOException {
		
		waitForSync(3);
		enterValueInTextbox(sheetName, "txt_recipientAddress;xpath", data(address)," Recipient Address ", screenName);
		waitForSync(3);
		
	}

	/**
	 * Desc: Click Communications message
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void clickCommunicationsMessage() throws InterruptedException, IOException {
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "btn_viewCommunicationMessage;xpath", " View Communication Message ", screenName);
		waitForSync(4);
	}
	/**
	 * @author A-9844
	 * @param eventType
	 * Desc : Enter EventType
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void enterEventType(String eventType) throws InterruptedException, IOException
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		String locatorChannel = xls_Read.getCellValue(sheetName, "btn_eventType;xpath");
		/*** CLICK CHANNEL***/
		WebElement element=driver.findElement(By.xpath(locatorChannel));
		executor.executeScript("arguments[0].click();", element);

		waitForSync(2);
		String locator = xls_Read.getCellValue(sheetName, "lst_channel;xpath").replace("*", eventType);
		System.out.println(locator);
		/**** ENTER CHANNEL***/

		WebElement element2=driver.findElement(By.xpath(locator));
		executor.executeScript("arguments[0].click();", element2);
		waitForSync(2);

	}
	/**
	 * @author A-9844
	 * @param mobileNumber
	 * @param msgType
	 * @param expTextTokenNo
	 * @param expsmsContents
	 * Desc : verify SMS contents in CGOMON
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws AWTException 
	 */
	public void verifySMSisTriggeredWithTokenNumber(String mobileNumber,String msgType,String expTextTokenNo,String expsmsContents) throws InterruptedException, IOException, AWTException
	{
		try{
			String locatorMobNo = xls_Read.getCellValue(sheetName, "cell_mobileNumber;xpath").replace("*", data(mobileNumber));
			List<WebElement> messages=driver.findElements(By.xpath(locatorMobNo));
			boolean smsTriggered=true;

			//Checking if message details getting displayed
			System.out.println(messages.size());
			if(messages.size()==0)
			{
				writeExtent("Fail","No "+msgType+" details displayed for the shipment with recipients mobile no as  "+data(mobileNumber)+" on "+screenName);
				smsTriggered=false;

			}

			else{
				writeExtent("Pass",msgType+" details displayed for the shipment with recipients mobile no as  "+data(mobileNumber)+" on "+screenName);
				smsTriggered=true;
			}


			if(smsTriggered)
			{
				driver.findElement(By.xpath(locatorMobNo)).click();
				waitForSync(4);
				WebElement entry=driver.findElement(By.xpath(locatorMobNo));
				moveScrollBar(entry);
				waitForSync(4);
				verifySMSDetailsWithTokenNumber(data(mobileNumber), data(expTextTokenNo), expsmsContents);

			}
		}catch (Exception e) {

			
			writeExtent("Fail","Failed to verify the contents of  "+msgType+" displayed for the shipment with recipients mobile no as  "+data(mobileNumber)+" on "+screenName);
		}


	}




/**
	 * @author A-9844
	 * @param expTextMobNo
	 * @param expTextTokenNo
	 * @param expsmsContents
	 * Desc : verify SMS Details
	 */
	public void verifySMSDetailsWithTokenNumber(String expTextMobNo,String expTextTokenNo,String expsmsContents) throws AWTException, InterruptedException, IOException
	{

		waitForSync(6);
		String locatorMobileNo= xls_Read.getCellValue(sheetName, "txt_mobileNo;xpath");
		String locatorSMSconetents= xls_Read.getCellValue(sheetName, "txt_smsContents;xpath");


		String actTextMobNo=driver.findElement(By.xpath(locatorMobileNo)).getText().split(";")[0];
		String actTextTokenNo=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[9];
		String actsmsContents1=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[10];
		String actsmsContents2=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[11];
		String actsmsContents3=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[12];
		String actsmsContents4=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[13];
		String actsmsContents=actsmsContents1+" "+actsmsContents2+" "+actsmsContents3+" "+actsmsContents4;
		
		System.out.println(actTextMobNo);
		System.out.println(actsmsContents);
		System.out.println(actTextTokenNo);

		verifyScreenText(" CGOMON ", expTextMobNo, actTextMobNo, " Mobile Number", " Mobile Number");
		verifyScreenText(" CGOMON ", expsmsContents, actsmsContents, " SMS Contents", " SMS Contents");
		verifyScreenText(" CGOMON ", expTextTokenNo, actTextTokenNo, " Token Number", " Token Number");


	}

	/**
	 * Entering Telephone Number
	 * @author A-9844
	 * @param mobileNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterTelephoneNumber(String mobileNumber) throws InterruptedException,AWTException {
		enterValueInTextbox(sheetName, "txt_TelephoneNumber;xpath", data(mobileNumber)," Mobile Number ", screenName);
		performKeyActions(sheetName, "txt_TelephoneNumber;xpath", "TAB"," Mobile Number ", screenName);
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * @param mobileNumber
	 * @param msgType
	 * @param expTextPagerNo
	 * @param expsmsContents
	 * Desc : verify SMS contents in CGOMON
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws AWTException 
	 */
	public void verifySMSisTriggered(String mobileNumber,String msgType,String expTextPagerNo,String expsmsContents) throws InterruptedException, IOException, AWTException
	{
		try{
			String locatorMobNo = xls_Read.getCellValue(sheetName, "cell_mobileNumber;xpath").replace("*", data(mobileNumber));
			List<WebElement> messages=driver.findElements(By.xpath(locatorMobNo));
			boolean smsTriggered=true;

			//Checking if message details getting displayed
			System.out.println(messages.size());
			if(messages.size()==0)
			{
				writeExtent("Fail","No "+msgType+" details displayed for the shipment with recipients mobile no as  "+data(mobileNumber)+" on "+screenName);
				smsTriggered=false;

			}

			else{
				writeExtent("Pass",msgType+" details displayed for the shipment with recipients mobile no as  "+data(mobileNumber)+" on "+screenName);
				smsTriggered=true;
			}


			if(smsTriggered)
			{
				driver.findElement(By.xpath(locatorMobNo)).click();
				waitForSync(4);
				WebElement entry=driver.findElement(By.xpath(locatorMobNo));
				moveScrollBar(entry);
				waitForSync(4);
				verifySMSDetails(data(mobileNumber), data(expTextPagerNo), expsmsContents);

			}
		}catch (Exception e) {

			
			writeExtent("Fail","Failed to verify the contents of  "+msgType+" displayed for the shipment with recipients mobile no as  "+data(mobileNumber)+" on "+screenName);
		}


	}

/**
	 * @author A-9844
	 * @param expTextMobNo
	 * @param expTextPagerNo
	 * @param expsmsContents
	 * Desc : verify SMS Details
	 */
	public void verifySMSDetails(String expTextMobNo,String expTextPagerNo,String expsmsContents) throws AWTException, InterruptedException, IOException
	{

		waitForSync(6);
		String locatorMobileNo= xls_Read.getCellValue(sheetName, "txt_mobileNo;xpath");
		String locatorPagerNo= xls_Read.getCellValue(sheetName, "txt_PagerNo;xpath");
		String locatorSMSconetents= xls_Read.getCellValue(sheetName, "txt_smsContents;xpath");


		String actTextMobNo=driver.findElement(By.xpath(locatorMobileNo)).getText().split(";")[0];
		String actTextPagerNo=driver.findElement(By.xpath(locatorPagerNo)).getText().split(";")[0];
		String actsmsContents1=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[10];
		String actsmsContents2=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[11];
		String actsmsContents3=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[12];
		String actsmsContents4=driver.findElement(By.xpath(locatorSMSconetents)).getText().split("\\s+")[13];
		String actsmsContents=actsmsContents1+" "+actsmsContents2+" "+actsmsContents3+" "+actsmsContents4;

		System.out.println(actTextMobNo);
		System.out.println(actTextPagerNo);
		System.out.println(actsmsContents);


		verifyScreenText(" CGOMON ", expTextMobNo, actTextMobNo, " Mobile Number", " Mobile Number");
		verifyScreenText(" CGOMON ", expTextPagerNo, actTextPagerNo, " Pager Number", " Pager Number");
		verifyScreenText(" CGOMON ", expsmsContents, actsmsContents, " SMS Contents", " SMS Contents");




	}
	/**
	 * Desc: Click OutboundMessage
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOutboundMessage() throws InterruptedException, IOException {
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "btn_viewoutboundMessage;xpath", " View Outbound Message ", screenName);
		waitForSync(3);
	}
	
	/**
	 * Desc: Entering from and to dates
	 * @author A-9175
	 * @param fromDate
	 * @param toDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	
	public void enterFromandToDates(String fromDate,String toDate) throws InterruptedException,AWTException {
		enterValueInTextbox(sheetName, "inbx_fromDate;id", fromDate," From Date ", screenName);
		performKeyActions(sheetName, "inbx_fromDate;id", "TAB"," From Date ", screenName);
		enterValueInTextbox(sheetName, "inbx_toDate;id", toDate," To Date ", screenName);
		performKeyActions(sheetName, "inbx_toDate;id", "TAB","To Date ", screenName);
		waitForSync(2);
	}
	
	/**
	 * Entering AWB number
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterAWB(String awbNo) throws InterruptedException,AWTException {
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(awbNo)," AWB No ", screenName);
		performKeyActions(sheetName, "inbx_awbNumber;id", "TAB","AWB No ", screenName);
		waitForSync(2);
	}
	/**
	 * Entering AWB number
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterFlightNo(String flightNo) throws InterruptedException,AWTException {
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(flightNo)," AWB No ", screenName);
		performKeyActions(sheetName, "inbx_flightNumber;xpath", "TAB","Flight No ", screenName);
		waitForSync(2);
	}
	/**
	 * Entering Message Type
	 * @author A-9175
	 * @param msgType
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterMessageType(String msgType) throws InterruptedException,AWTException {
		enterValueInTextbox(sheetName, "inbx_msgType;id",msgType," Message Type ", screenName);
		performKeyActions(sheetName, "inbx_msgType;id", "TAB"," Message Type ", screenName);
		waitForSync(2);
	}
	
	
	/**
	 * @author A-7271
	 * @param channel
	 * Desc : Enter chanel
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void enterChannel(String channel,String msgType) throws InterruptedException, IOException
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		
		String locatorChannel="";
		if(msgType.equalsIgnoreCase("Incoming"))
		{
		 locatorChannel = xls_Read.getCellValue(sheetName, "btn_channelIncoming;xpath");
		 System.out.println(locatorChannel);
		}
		else
		{
			 locatorChannel = xls_Read.getCellValue(sheetName, "btn_channelOutgng;xpath");
		}
		/*** CLICK CHANNEL***/
		 WebElement element=driver.findElement(By.xpath(locatorChannel));
		executor.executeScript("arguments[0].click();", element);
		
		waitForSync(2);
		String locator = xls_Read.getCellValue(sheetName, "lst_channel;xpath").replace("*", channel);
		System.out.println(locator);
		/**** ENTER CHANNEL***/
		
        WebElement element2=driver.findElement(By.xpath(locator));
		executor.executeScript("arguments[0].click();", element2);
		waitForSync(2);
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click search
	 */
	public void clickSearch() throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "btn_search;xpath", " Search  ", screenName);
		waitForSync(5);
		
		
	}	
	
	/**
	 * @author A-7271
	 * @param awbNumber
	 * @param msgType
	 * @param channel
	 * Desc : verify message status in CGOMON
	 */
	public void verifyMessageStatus(String awbNumber,String msgType,String channel)
	{
		String locatorAwb = xls_Read.getCellValue(sheetName, "cell_awbNumber;xpath").replace("*", data(awbNumber));
		List<WebElement> messages=driver.findElements(By.xpath(locatorAwb));
		boolean msgTriggered=true;
		boolean msgRejected=false;
		
		//Checking if message details getting displayed
		System.out.println(messages.size());
		if(messages.size()==0)
		{
			writeExtent("Fail","No "+msgType+" message details displayed for the shipment "+data(awbNumber)+" on "+screenName+" Channel is : "+channel);
			msgTriggered=false;
			
		}
		
		//To check if message got Rejected or accepted
		
		if(msgTriggered)
		{
			for(int i=1;i<=messages.size();i++)
			{
				//String dynXpath="(//mat-cell[contains(.,' "+data(awbNumber)+" ')])["+i+"]//preceding-sibling::mat-cell[4]//span//mat-icon";
				String dynXpath="(//mat-cell[contains(.,' "+data(awbNumber)+" ')])["+i+"]//preceding-sibling::mat-cell[4]//mat-icon";
				System.out.println(dynXpath);
				String style=driver.findElement(By.xpath(dynXpath)).getAttribute("style");
				System.out.println(style);
				
				if(!style.contains("green"))
				{
					writeExtent("Fail",msgType+" Message rejected for "+data(awbNumber)+" on "+screenName+" Channel is : "+channel);
					msgRejected=true;
					break;
				}
				
			}
		}
		
		if(!msgRejected)
		{
			if(msgTriggered)
			{
				writeExtent("Pass",msgType+" Message displayed for "+data(awbNumber)+" on "+screenName+" Channel is : "+channel);
			}
		}
		
	}
	
	/**
	 * @author A-7271
	 * @param flightNo
	 * @param msgType
	 * @param channel
	 * Desc : verify message status in CGOMON
	 */
	public void verifyMessageStatusForFlight(String flightNo,String msgType,String channel)
	{
		String locatorAwb = xls_Read.getCellValue(sheetName, "cell_awbNumber;xpath").replace("*", data(flightNo));
		List<WebElement> messages=driver.findElements(By.xpath(locatorAwb));
		boolean msgTriggered=true;
		boolean msgRejected=false;
		//Checking if message details getting displayed
		System.out.println(messages.size());
		if(messages.size()==0)
		{
			writeExtent("Fail","No "+msgType+" message details displayed for the flight "+data(flightNo)+" on "+screenName+" Channel is : "+channel);
			msgTriggered=false;
			
		}
		
		//To check if message got Rejected or accepted
		
		if(msgTriggered)
		{
			for(int i=1;i<=messages.size();i++)
			{
				//String dynXpath="(//mat-cell[contains(.,' "+data(flightNo)+" ')])["+i+"]//preceding-sibling::mat-cell[7]//span//mat-icon";
				String dynXpath="(//mat-cell[contains(.,' "+data(flightNo)+" ')])["+i+"]//preceding-sibling::mat-cell[7]//mat-icon";
				System.out.println(dynXpath);
				String style=driver.findElement(By.xpath(dynXpath)).getAttribute("style");
				System.out.println(style);
				if(!style.contains("green"))
				{
					writeExtent("Fail",msgType+" Message rejected for "+data(flightNo)+" on "+screenName+" Channel is : "+channel);
					msgRejected=true;
					break;
				}
				
			}
		}
		
		if(!msgRejected)
		{
			if(msgTriggered)
			{
				writeExtent("Pass",msgType+" Message displayed for "+data(flightNo)+" on "+screenName+" Channel is : "+channel);
			}
		}
		
	}
	/**
	 * Desc: Verifying message details inbound/outbound
	 * @author A-9175
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @param msgType
	 * @param isAssertreq
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void verifyMessageDetails(int verfCols[], String actVerfValues[],
			String pmKey,String msgType,boolean isAssertreq) throws InterruptedException, IOException {
		waitForSync(2);
		verify_tbl_records_multiple_cols(sheetName, "table_listMessage;xpath",
				"//td", verfCols, pmKey, actVerfValues,data(msgType),isAssertreq);
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : clean details
	 */
	public void cleanDetails() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_clean;xpath", " Clean  ", screenName);
		waitForSync(5);
	}
	
	

}
