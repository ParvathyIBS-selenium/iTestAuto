package screens;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import com.gargoylesoftware.htmlunit.javascript.host.file.FileReader;
import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.Xls_Read;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class ListMessages_MSG005 extends CustomFunctions {

	String sheetName = "ListMessages_MSG005";
	String screenName = "List Messages : MSG005";
	String screenId = "MSG005";
	int msgCounter = 0;

	// CustomFunctions comm=new CustomFunctions(driver, excelreadwrite,
	// xls_Read);

	public ListMessages_MSG005(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	/**
	 * Description... Click Clear Button
	 * 
	 * @throws InterruptedException
	 */
	public void clickClearButton() throws InterruptedException {
		/****clickWebElement("Generic_Elements", "btn_clear;name", "Clear Button",
				screenName);
		waitForSync(2);****/
	}
	/* @author A-9844
	 * Desc..  get the number of records present in the table
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public int getNumberOfRecordsPresent() throws InterruptedException, IOException{

		int msgCount=0 ;

		String errorMessage = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMessages;xpath");

		try{
			if(driver.findElement(By.xpath(errorMessage)).isDisplayed()){

				if(driver.findElement(By.xpath(errorMessage)).getText().contains("No results found for the specified criteria."))
				{
					writeExtent("Pass","Retrieved message count as " +msgCount+ " from "+screenName+" Page");
				    return msgCount;
				}
				else
					writeExtent("Fail","Expected error message does not match." +"shown on "+screenName+" Page");

			} 

		}

		catch(Exception e)
		{
			String locator = xls_Read.getCellValue(sheetName, "htmldiv_messageCountText;xpath");
			String Text=driver.findElement(By.xpath(locator)).getText();
			String[] len = Text.split(" ");
			int count=len.length;
			msgCount=Integer.parseInt(len[count-1]);
			writeExtent("Pass","Retrieved message count as " +msgCount+ " from "+screenName+" Page");


		}

		return msgCount;

	}
	/**
	 * Description... Enter Message Type
	 * 
	 * @param MessageType
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterMsgType(String MessageType) throws InterruptedException,
			AWTException {
		enterValueInTextbox(sheetName, "MSGtype_field;xpath", MessageType,
				"Message Type", screenName);
		performKeyActions(sheetName, "MSGtype_field;xpath", "TAB",
				"Message Type", screenName);
		waitForSync(2);
	}
	/**
	 * Description... Verify Message Content
	 * 
	 * @param MessageContent
	 * @throws Exception
	 */
	public void verifyMessageContent(List<String> MessageContent,String messageType,boolean msgContentsExists)
			throws Exception {
		driver.switchTo().frame("popupContainerFrame");
	       
		waitForSync(3);
		ele = findDynamicXpathElement("txtarea_RawMsg;xpath", sheetName,
				"Message Content", screenName);
		String actText = ele.getText();
		System.out.println(actText);

		for (String value : MessageContent) {
			System.out.println("Actual val is---" + data(value));
			if(msgContentsExists)
			{
			verifyScreenText(sheetName, data(value), actText,"Message Content Verification for the message type "+messageType, screenName);
					
			}
			else
			{
			verifyScreenTextNotExists(sheetName, data(value), actText,"Message Content Verification for the message type "+messageType);
						
			}
			
		}

		waitForSync(2);
	}
	/**
	 * @author A-7271
	 * @param messageContent
	 * @return
	 * @throws Exception
	 * Desc : Verify message contents and return true if matches with the expected value
	 */
	public boolean verifyMessageContents(String messageContent)
			throws Exception {
		driver.switchTo().frame("popupContainerFrame");

		waitForSync(3);
		ele = findDynamicXpathElement("txtarea_RawMsg;xpath", sheetName,
				"Message Content", screenName);
		String actText = ele.getText();
		System.out.println(actText);

		if(actText.contains(messageContent))

			return true;

		else

			return false;



	}
	/**
	 * @author A-7271
	 * @param row
	 * @param profileId
	 * @return
	 * Desc : verify logs for profile ID
	 */
	public boolean verifyLogs(int row,String profileId)
	{
		try
		{
			String locatorListMessage=xls_Read.getCellValue(sheetName, "table_listMessage;xpath");
			String locatorVerifyLogs=xls_Read.getCellValue(sheetName, "table_verifyLogs;xpath");
			
			String xpath="("+locatorListMessage+")["+row+"]//td[12]//a";
			driver.findElement(By.xpath(xpath)).click();
			switchToWindow("storeParent");
			switchToWindow("multipleWindows");
			List <WebElement> rows=driver.findElements(By.xpath(locatorVerifyLogs));
			
			for(WebElement rowValue:rows)
			{
				if(rowValue.getText().contains(profileId))
				{
					clickWebElementByWebDriver("ListMessages_MSG005", "btn_closeBtn;xpath",
							"Close Button", "Message View Button Pop up");

					switchToWindow("getParent");
					switchToDefaultAndContentFrame("MSG005");
					return true;
				}
					

			}
			clickWebElementByWebDriver("ListMessages_MSG005", "btn_closeBtn;xpath",
					"Close Button", "Message View Button Pop up");

			switchToWindow("getParent");
			switchToDefaultAndContentFrame("MSG005");
			return false;
		}
		
		catch(Exception e)
		{
			return false;
		}
	}
	/**
	 * Description... Click Check Box
	 * 
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void clickMessageCheckBox(String row) throws InterruptedException {
		
		try
		{
		   String locator=xls_Read.getCellValue(sheetName, "table_listMessage;xpath");
		   String xpath="("+locator+")["+row+"]//td[1]";
		   driver.findElement(By.xpath(xpath)).click();
		   waitForSync(1);
		 
		}
		
		catch(Exception e)
		{
			
		}
		

	}
	
	/**
	 * @author A-7271
	 * @param message
	 * @param contents
	 * @throws Exception
	 * Verify If message triggering by verifying the contents
	 */
	public void verifyIfMessageTriggered(String message,String contents) throws Exception
	{
		String locator=xls_Read.getCellValue(sheetName, "chk_msgs;name");
		List <WebElement> chkBox=driver.findElements(By.name(locator));
		boolean ifMsgTriggered=false;
		int count=0;
		for(WebElement checkBox:chkBox)
		{
			chkBox=driver.findElements(By.name(locator));
			checkBox=chkBox.get(count);
			checkBox.click();
			clickView();
			ifMsgTriggered=verifyMessageContents(contents);
			closeView();
			if(ifMsgTriggered)
			{
				writeExtent("Pass","Message type" +"'"+message+"'"+ "triggered for the content "+contents+" on "+screenId);
				break;
				
			}
			count++;
		}
			
			if(!ifMsgTriggered)
			{
				writeExtent("Fail","Message type" +"'"+message+"'"+ "does not trigger for the content "+contents+" on "+screenId);
			}
			
			
		}
	
	/**
	 * @author A-7271
	 * @param pmKey
	 * @param profileId
	 * @param msgType
	 * @param msgTriggered
	 * Desc : verify if a message triggered
	 */
	public void verifyIfMessageTriggered(String pmKey,String profileId,String msgType,boolean msgTriggered)
	{
		
		
		try
		{
			waitForSync(3);
		String locator=xls_Read.getCellValue(sheetName, "table_listMessage;xpath");
		
		List <WebElement> rows=driver.findElements(By.xpath(locator));
		
		int count=1;
		boolean flag=false;
		
		for(WebElement row:rows)
		{
			rows=driver.findElements(By.xpath("locator"));
			row=driver.findElement(By.xpath("("+locator+")["+count+"]//td[2]"));
			
			if(row.getText().contains(pmKey))
			{
				System.out.println(count);
				boolean profileIdVerified=verifyLogs(count,profileId);
				if(profileIdVerified)
				{
					
					flag=true;
					map.put("MsgRef",Integer.toString(count));
					break;
				}
			}
			count++;
		}
		
		if(flag==true)
			if(msgTriggered)
				
				writeExtent("Pass","Message "+msgType+" triggered for the key "+pmKey+" on "+screenName);
		
			else
				writeExtent("Fail","Message "+msgType+" triggered for the key "+pmKey+" on "+screenName);
		else
			if(msgTriggered)
				writeExtent("Fail","Message "+msgType+" not triggered for the key "+pmKey+" on "+screenName);
			else
				
				writeExtent("Pass","Message "+msgType+" not triggered for the key "+pmKey+" on "+screenName);
		}
		
		catch(Exception e)
		{
			
			writeExtent("Fail","Exception while checking if message "+msgType+"  triggered for the key "+pmKey+" on "+screenName);
		}
	}
	/**
	 * @author A-7271
	 * @param pmKey
	 * @param msgRef
	 * @throws Exception
	 * Desc : verify if message triggered by verifying the contents
	 */
	
	public void verifyIfMessageTriggered(String pmKey,String msgType,String msgRef) throws Exception
	{
		
		String msgChkBoxLocator=xls_Read.getCellValue(sheetName, "chk_msgID;xpath");
		String msgTable=xls_Read.getCellValue(sheetName, "table_listMessage;xpath");
		List<WebElement> ele=driver.findElements(By.xpath(msgChkBoxLocator));
		int count=2;
		int eleCount=0;
		String msgReference="";
		boolean msgTriggered=false;
		for(WebElement element : ele)
		{
			ele=driver.findElements(By.xpath(msgChkBoxLocator));
			element=ele.get(eleCount);
			String locator="("+msgTable+")["+count+"]//td[2]";
			msgReference=driver.findElement(By.xpath(locator)).getText();
			element.click();
			clickView();
			msgTriggered=verifyMessageContents(pmKey);
			closeView();
			count++;
			eleCount++;
			if(msgTriggered)
			{
			 map.put(msgRef, msgReference);
			 break;
				
			}


		}
		
		if(msgTriggered)
			writeExtent("Pass","Message triggered for the message type "+msgType);
		else
			writeExtent("Fail","No message triggered for the message type "+msgType);
        
		
	}

	/**
	 * Description... Load From File
	 * 
	 * @param ParticipantType
	 * @param TransmissionMode
	 * @param InterfaceSystem
	 * @param StationName
	 * @param FileListener
	 * @param FilePath
	 * @throws Exception
	 */
	public void loadFromFileWithStatusCheck(String ParticipantType, String Participant,String TransmissionMode,
			String InterfaceSystem, String StationName, String FileListener,
			String FileName,String expMsg) throws Exception {

		clickButtonSwitchWindow(sheetName, "btn_LoadFromFile;name", screenName,
				"Load From File Button");
		selectValueInDropdown(sheetName, "lst_participantType;name",
				ParticipantType, "Participant Type", "VisibleText");
		
		if(!ParticipantType.equals("All"))
		{
			enterValueInTextbox(sheetName, "inbx_participant;name", data(Participant),
					"Participant", "Load From File Pop up");
		}
		selectValueInDropdown(sheetName, "lst_transmissionMode;name",
				TransmissionMode, "Transmission Mode", "VisibleText");
		
		waitForSync(2);
	
		enterValueInTextbox(sheetName, "inbx_stationCode;name", data(StationName),
				"StationName", "Load From File Pop up");
		/**selectValueInDropdown(sheetName, "lst_interfaceSystem;name",
				InterfaceSystem, "Interface System", "VisibleText");**/
		clickButtonSwitchToSecondWindow(sheetName, "btn_Address;id",
				"Load From File Pop up", "Address Button");
		
		

		/****enterValueInTextbox(sheetName, "inbx_fileListener;name", FileListener,
				"File Listener", "Message Address Details Pop up");***/
		
		

		clickWebElement(sheetName, "btn_ok;name", "OK Button",
				"Message Address Details Pop up");
		
		switchToWindow("getFirstChild");

		try {
			driver.findElement(By.name("theFile")).sendKeys(message_files+FileName+".txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		clickWebElement(sheetName, "btnLoad;name", "Load Button",
				"Load From File Pop up");
		  handleAlert("Accept", "List Messages",FileName,expMsg);

		clickWebElement(sheetName, "btn_viewMsg_Close;xpath", "Close Button",
				"Load From File Pop up");

		switchToWindow("getParent");
		switchToDefaultAndContentFrame(screenId);
	}
	/**
	 * Description... Handles an alert with options getText/Accept/Dismiss/Close
	 * 
	 * @param alertOps
	 * @param ScreenName
	 */
	public boolean handleAlert(String alertOps, String ScreenName,String fileName,String expMessage) {
		switchToFrame("default");
		String AlertText = "";

		try {
			AlertText = driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "htmlDiv_alertMsg;xpath")))
					.getText();
			if (!AlertText.equals("")) {
				
				if(!AlertText.contains(expMessage))
				{
					writeExtent("Fail", "'"+fileName+"'"+" Message status not matches on processing. Expected status is : '"+expMessage+"' and actual status is : '"+AlertText+ScreenName+ "' Screen");
					Assert.assertFalse(true, "Message status not matches on processing"+ScreenName);
				}
				else
				{
				switch (alertOps.valueOf(alertOps)) {
				case "getText":
					setPropertyValue("AlertText", AlertText, proppath);
					break;

				case "Accept":
					driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
					if(AlertText.contains("Received"))
					{
						
						writeExtent("Info", "'"+fileName+"'"+" Message  processed with status "+AlertText + " on " + ScreenName + " Screen");
					}
					else
					{
						writeExtent("Pass", "'"+fileName+"'"+" Message  processed with status "+AlertText + " on " + ScreenName + " Screen");
					}

					break;
				case "Dismiss":
					driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_no;xpath"))).click();
					writeExtent("Pass", "Dismissed Alert with text " + AlertText + " on " + ScreenName + " Screen");
					break;
				case "Close":
					driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_close;xpath"))).click();
					writeExtent("Pass", "Closed Alert with text " + AlertText + " on " + ScreenName + " Screen");
					break;
				case "GetTextAndClose":
					setPropertyValue("AlertText", AlertText, proppath);
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_closePopUp;xpath"))).click();
				writeExtent("Pass", "Closed Alert with text " + AlertText + " on " + ScreenName + " Screen");
				break;
				}
				}

			}
			
			return true;
		} catch (Exception e) {
	
			//writeExtent("Info", "Failed to handle Alert with text " + AlertText + " On " + ScreenName + " Screen");
				return false;

		}
	}

	/**
	 * Description... Select Message SubType
	 * 
	 * @param MessageSubType
	 * @throws InterruptedException
	 */
	public void selectMsgSubType(String MessageSubType)
			throws InterruptedException {

		selectValueInDropdown(sheetName, "lst_MsgSubType;xpath",
				MessageSubType, "Message SubType", "VisibleText");
		waitForSync(2);

	}
	/***
	 * 
	 * 
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
		// int verfCols[]={4,5,6,10,11,12,13};
		// String[]
		// actVerfValues={data("Date"),data("Origin"),data("Destination"),"FC",data("ShipmentPieces"),data
		// ("ShipmentWeight"),data("ShipmentVolume")};

		verify_tbl_records_multiple_cols(sheetName, "table_listMessage;xpath",
				"//td", verfCols, pmKey, actVerfValues,data(msgType),isAssertreq);
	}
	/**
	 * Description... Verify Message Details
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 */
	public void verifyMessageDetailsWithInfo(int verfCols[], String actVerfValues[],
			String pmKey,String msgType,boolean isAssertreq) throws InterruptedException {
		waitForSync(2);
		// int verfCols[]={4,5,6,10,11,12,13};
		// String[]
		// actVerfValues={data("Date"),data("Origin"),data("Destination"),"FC",data("ShipmentPieces"),data
		// ("ShipmentWeight"),data("ShipmentVolume")};

		verify_tbl_records_multiple_cols_info_inreport(sheetName, "table_listMessage;xpath",
				"//td", verfCols, pmKey, actVerfValues,data(msgType),isAssertreq);
	}
	/**
	 * @author A-6260
	 * Description... Verify  message is not triggered
	 * @param screenName
	 * @throws Exception
	 */
	public void verifyNoMsgTriggered(String screenName) throws Exception {

		verifyElementDisplayed("Generic_Elements", "txt_errorMessage;xpath",
				"No results found for the specified criteria panel",

				screenName, "No message triggered for the searched data");
		


	}
	/**
	 * @author A-6260
	 * Description... Verify  message is not triggered
	 * @param screenName
	 * @throws Exception
	 */
	public void verifyNoMsgTriggered(String screenName,String msg,String value) throws Exception {

		verifyElementDisplayed("Generic_Elements", "txt_errorMessage;xpath",
				"No '"+msg+"'"+" message triggered for the data : "+ data(value),

				screenName, "No '"+msg+"'"+" message triggered for the data : "+ data(value));
		


	}
	/**
	 * Description... Verify Message Status checks with the most recent message
	 * that is on top of the list. Matches the actual value
	 * 
	 * with the value given by the user
	 * 
	 * @param expectedMsgStatus
	 * @param msgType
	 * @throws InterruptedException
	 */
	public void verifyMessageStatus(String expectedMsgStatus, String msgType)
			throws InterruptedException {

		String actMsgStatus = getElementText("ListMessages_MSG005",
				"txt_messageSent;xpath", "Message Status", screenName);
		verifyValueOnPage(actMsgStatus, expectedMsgStatus, "Verify " + msgType
				+ " Message status",

		screenName, "Message Status");

	}

	/**
	 * Description... Click Reference
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickReference() throws InterruptedException, IOException {
		clickWebElement(sheetName, "Reference_But;xpath", "View", screenName);
		waitForSync(5);

	}
	
	/**
	 * 
	 * @param pmyKey
	 * @param msgType
	 * @throws InterruptedException
	 */
	public void verifyMessageTriggered(String pmyKey, String msgType)
            throws InterruptedException {
      String xpath = xls_Read.getCellValue(sheetName, "chk_AWBNo_Row;xpath")
                  .replace("AWBNo", data(pmyKey));
      verifyElementDisplayed(xpath, "Verify " + msgType + " Message",
                  screenName, msgType + " Message");
}


	/**
	 * Description... Enter Reference Value
	 * 
	 * @param messageType
	 * @param FlightNo
	 * @param AWBNo
	 * @throws InterruptedException
	 */
	public void enterReferenceValue(String messageType, String FlightNo,
			String AWBNo) throws InterruptedException {

		switch (messageType) {

		case "FFM":
			enterValueInTextbox(sheetName, "FLTnum_text;xpath", data(FlightNo),
					"FLTNUM", screenName);
			break;

		case "SSM":
			enterValueInTextbox(sheetName, "SSMNum_text;xpath", data(FlightNo),
					"NUM", screenName);
			break;

		case "FWB":
			enterValueInTextbox(sheetName, "AWBscr_text;xpath", data(AWBNo),
					"AWBSCR", screenName);
			break;

		case "FHL":
			enterValueInTextbox(sheetName, "HAWBscr_text;xpath", data(AWBNo),
					"HAWBSCR", screenName);
			break;

		case "ASM":

			enterValueInTextbox(sheetName, "ASMNum_text;xpath", data(FlightNo),
					"ASMSCR", screenName);
			break;

		case "FLTOPRAVI":
			enterValueInTextbox(sheetName, "inbx_refFLTOPRAVI;xpath",
					data(FlightNo), "FLTNUM", screenName);
			break;

		case "MYD":
			enterValueInTextbox(sheetName, "inbx_refMYD;xpath", data(FlightNo),
					"FLTNUM", screenName);
			break;
		case "FSU":
			enterValueInTextbox(sheetName, "AWBserNo_text;xpath", data(AWBNo),
					"AWBSERNUM", screenName);
			break;

		case "EVTTRGMSG":
			enterValueInTextbox(sheetName, "inbx_searchAWB;xpath", data(AWBNo),
					"AWB No", screenName);
			break;
		case "EFSU":
			enterValueInTextbox(sheetName, "inbx_refValueEFSU;xpath",
					data(AWBNo), "AWBSERNUM", screenName);
			break;

		case "FBL":
			enterValueInTextbox(sheetName, "inbx_refFLTOPRAVI;xpath",
					data(FlightNo), "FLTNUM", screenName);

			break;

		case "FLTSCH":
			enterValueInTextbox(sheetName, "inbx_FLTSCH_FLTNUM;xpath",
					data(FlightNo), "FLTNUM", screenName);
			break;

		case "FDD":
			enterValueInTextbox(sheetName, "inbx_refValueFDD;xpath",
					data(AWBNo), "AWBSERNUM", screenName);
			break;

		case "CAPBKG":
			enterValueInTextbox(sheetName, "inbx_ubr;xpath", data(AWBNo),
					"UBR", screenName);
			break;

		case "AWBDTL":
			enterValueInTextbox(sheetName, "inbx_searchAWB;xpath", data(AWBNo),
					"AWBSERNUM", screenName);
			break;

		case "BUM":
			enterValueInTextbox(sheetName, "AWBserNo_text;xpath", data(AWBNo),
					"AWB No", screenName);
			break;

		case "IQAMSG":
			enterValueInTextbox(sheetName, "inbx_refValueIQAMsg;xpath",
					data(AWBNo), "MSTDOCNUM", screenName);
			break;

		case "XSDG":
			enterValueInTextbox(sheetName, "inbx_refXSDGawb;xpath",
					data(AWBNo), "AWBSERNUM", screenName);
			break;
		case "CINOUT":
			enterValueInTextbox(sheetName, "inbx_refValueFSU;xpath",
					data(AWBNo), "AWBSERNUM", screenName);
			break;

		case "FLTLDP":
			enterValueInTextbox(sheetName, "FLTnum_text;xpath", data(FlightNo),
					"FLTNUM", screenName);
			break;
		case "OBJID":
			enterValueInTextbox(sheetName, "inbx_OBJLOCMSG;xpath",
					data(FlightNo), "OBJID", screenName);
			break;

		case "LOCID":
			enterValueInTextbox(sheetName, "inbx_LOCID;xpath", data(FlightNo),
				"LOCID", screenName);
			break;
			
		case "WSCL":
			enterValueInTextbox(sheetName, "FLTnum_text;xpath", data(FlightNo),
					"FLTNUM", screenName);
			break;

		}

		waitForSync(2);
	}

	/**
	 * Description... Click View Button
	 * 
	 * @throws Exception
	 */
	public void clickViewButton() throws Exception {
		switchToWindow("storeParent");
		clickWebElement("Generic_Elements", "btn_view;name", "View Button",
				screenName);
	}

	/**
	 * Description... Click Message check Box
	 * 
	 * @return
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public boolean clickMsg() throws InterruptedException, IOException {

		boolean chkBox = verifyElementDisplayed("Generic_Elements",
				"chk_msg;name", "", screenName, "Message check Box");

		if (chkBox) {
			clickWebElement("Generic_Elements", "chk_msg;name",
					"Message check Box", screenName);

			return true;
		}

		else {
			return false;
		}

	}

	/**
	 * Description... Get Message Content
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getMessageContent() throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		ele = findDynamicXpathElement("txtarea_RawMsg;xpath", sheetName,
				"Message Content", screenName);
		String text = ele.getText();

		clickWebElementByWebDriver("Generic_Elements", "butn_close;name",
				"Close Button", "Message View Button Pop up");
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", screenId);
		return text;

	}

	/**
	 * Description... Verify Error MIP Description
	 * 
	 * @param expText
	 * @throws Exception
	 */
	public void verifyErrorMIPDescription(String expText) throws Exception {
		driver.switchTo().frame("popupContainerFrame");
        
		ele = findDynamicXpathElement("table_ErrorDesc;xpath", sheetName,
				"MIP Error Description", screenName);
		String actText = ele.getText();
		verifyScreenText(sheetName, data(expText), actText,
				"Error Description Verification", screenName);
		
		clickWebElement(sheetName, "click_CloseView;xpath", "Close View",
				screenName);
		waitForSync(2);
		
		switchToFrame("default");
        switchToFrame("contentFrame", screenId);
        
        
		/*clickWebElement("Generic_Elements", "butn_close;name", "Close Button",
				"Message View Button Pop up");
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", screenId);*/

	}

	/**
	 * Description... Enters the to and from date in message screen
	 * 
	 * @param fromDate
	 * @param toDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterToFromDate(String fromDate, String toDate)
			throws InterruptedException, AWTException {

		enterValueInTextbox(sheetName, "FromDate_field;xpath", fromDate,
				"From Date", screenName);
		enterValueInTextbox(sheetName, "ToDate_Field;xpath", toDate, "To Date",
				screenName);
		keyPress("TAB");
		keyRelease("TAB");

	}
public void VerifyHandlingCode(String[] HandlingCodes) throws Exception {
		
		
		waitForSync(2);
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(2);
		try {
			int count =HandlingCodes.length;
			for (int i = 0; i < count; i++) {
			
			ele = findDynamicXpathElement("tbl_Viewlogs_HandlingProfile;xpath",
					sheetName, "Handling code", screenName);
			waitForSync(1);
			String actText = ele.getText();
			waitForSync(1);
			String expText = HandlingCodes[i];
			verifyScreenText(sheetName, expText, actText,
					"Message Verification", screenName);

		
			}
		} catch (Exception e) {
			System.out.println("Handling code and Configuration Profile are not verified");
		}
		
	
	}
	/**
	 * Description... Select Message Status
	 * 
	 * @param messageStatus
	 * @throws InterruptedException
	 */
	public void selectStatus(String messageStatus) throws InterruptedException {

		switch (messageStatus) {
		case "ProcessedWithWarnings":
			selectValueInDropdown(sheetName, "lst_MsgStatus;xpath",
					"Processed With Warnings", "Message Status", "VisibleText");
			break;

		case "ProcessedSuccessfully":
			selectValueInDropdown(sheetName, "lst_MsgStatus;xpath",
					"Processed Successfully", "Message Status", "VisibleText");
			break;

		case "ProcessedWithErrors":
			selectValueInDropdown(sheetName, "lst_MsgStatus;xpath",
					"Processed With Errors", "Message Status", "VisibleText");
			break;

		case "DecodedWithErrors":
			selectValueInDropdown(sheetName, "lst_MsgStatus;xpath",
					"Decoded With Errors", "Message Status", "VisibleText");
			break;

		case "Sent":
			selectValueInDropdown(sheetName, "lst_MsgStatus;xpath", "Sent",
					"Message Status", "VisibleText");
			break;

		}
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Description... Enter Reference Value for PublishULDRelocation
	 * @param UldNum
	 * @throws InterruptedException
	 */
	public void enterReferenceValue(String UldNum) throws InterruptedException {
	
		enterValueInTextbox(sheetName, "inbx_refULDNo;xpath", data(UldNum),"ULD Num", screenName);
		waitForSync(2);
			
	}
	/**
	 * Description... Click List
	 * 
	 * @throws Exception
	 */
	public void clickList() throws Exception {
		// From Date
		String prevDate = createDateFormat("dd-MMM-YYYY", -1, "DAY", "");
		String fmDate = createDateFormat("dd-MMM-YYYY", +1, "DAY", "");

		enterValueInTextbox(sheetName, "inbx_calFromDate;id", prevDate,
				"From Date", screenName);
		keyPress("TAB");
		Thread.sleep(3000);

		// To Date
		clearText(sheetName, "inbx_calToDate;id", "To Date", screenName);
		enterValueInTextbox(sheetName, "inbx_calToDate;id", fmDate, "To Date",
				screenName);
		keyPress("TAB");
		

		waitForSync(3);
		clickWebElement(sheetName, "List_Msg;xpath", "List", screenName);
		waitTillScreenloadWithOutAssertion(sheetName, "table_listMessage;xpath", "list of messages",
				screenName, 20);
		waitForSync(1);
	}

	/**
	 * Description... Click Clear Button
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickClear() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Clear;name", "Clear", screenName);
		waitForSync(2);
	}

	/**
	 * Description... Click Check Box
	 * 
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void clickCheckBox(String pmyKey) throws InterruptedException {
		
		   
		
		selectTableRecord(data(pmyKey), "chk_selectAWB;xpath", sheetName, 3);
		waitForSync(1);

	}

	/**
	 * Description... Verify Message Details
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyMessageDetails(int verfCols[], String actVerfValues[],
			String pmKey) throws InterruptedException, IOException {
		waitForSync(2);
		// int verfCols[]={4,5,6,10,11,12,13};
		// String[]
		// actVerfValues={data("Date"),data("Origin"),data("Destination"),"FC",data("ShipmentPieces"),data
		// ("ShipmentWeight"),data("ShipmentVolume")};

		verify_tbl_records_multiple_cols(sheetName, "table_listMessage;xpath",
				"//td", verfCols, pmKey, actVerfValues);
	}

	/**
	 * Description... Click process
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickprocess() throws InterruptedException, IOException {
		
		ArrayList<String>tableValues=new ArrayList<String>();
		int col[]={9};
		boolean msgProcessed=false;
		
		tableValues=retrieve_tbl_records_multiple_cols(sheetName,  "table_listMessage;xpath", "//td", col,
				data("pmkey"));
		
		
		
		for(String values:tableValues)
		{
			System.out.println(values);
			if(values.contains("Processed"))
			{
				msgProcessed=true;
				break;
			}
		}
		
		
		
		if(!msgProcessed)
		{
			clickWebElement(sheetName, "click_Process;xpath", "Process", screenName);
			waitForSync(8);

			switchToFrame("default");

			try {

				String msgStatus = getElementText("Generic_Elements",
						"htmlDiv_msgStatus;xpath", "Message Status", screenName);

				if (msgStatus.contains("processed successfully.")) {
					writeExtent("Pass", "Message processed successfully");
				} else {
					if (msgStatus.contains("warnings")) {
						writeExtent("Pass", "Message processed.Msg status is"
								+ msgStatus);
					} else {
						writeExtent("Fail",
								"Message not processed successfully.Msg status is "
										+ msgStatus);
					}

				}
				clickWebElement("Generic_Elements", "btn_OK;xpath", "OK button",
						screenName);
				switchToFrame("contentFrame", "MSG005");

			}

			catch (Exception e) {

			}
		}

	}

	/**
	 * Description... Click View logs
	 * 
	 * @param pmyKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickViewlogs(String pmyKey) throws InterruptedException, IOException {

		clickWebElement(sheetName, "lnk_Viewlogs;xpath", "Click Viewlogs",
				screenName);
		waitForSync(3);

	}

	/**
	 * Description... Verify Handling Code
	 * 
	 * @param HandlingCode
	 * @throws Exception
	 */
	public void VerifyHandlingCode(String HandlingCode) throws Exception {
		waitForSync(2);
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(2);
		try {
			ele = findDynamicXpathElement("tbl_Viewlogs_HandlingProfile;xpath",
					sheetName, "Handling code", screenName);
			waitForSync(1);
			String actText = ele.getText();
			waitForSync(1);
			String expText = data(HandlingCode);
			verifyScreenText(sheetName, expText, actText,
					"Message Verification", screenName);

		} catch (Exception e) {
			System.out.println("Handling code is not verified");
		}

	}

	/**
	 * Description... Close View logs
	 * 
	 * @throws Exception
	 */
	public void closeViewlogs() throws Exception {

		clickWebElement(sheetName, "btn_CloseViewlogs;xpath", "Close Viewlogs",
				screenName);
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "MSG005");
	}

	/**
	 * Description... Verify Error MIP Description FF
	 * 
	 * @param expText
	 * @throws Exception
	 */
	public void verifyErrorMIPDescriptionFF(String expText) throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		ele = findDynamicXpathElement("table_ErrorDesc;xpath", sheetName,
				"MIP Error Description", screenName);
		String actText = ele.getText();
		verifyScreenText(sheetName, data(expText), actText,
				"Error Description Verification", screenName);
		clickWebElement("Generic_Elements", "butn_close;name", "Close Button",
				"Message View Button Pop up");
		switchToWindow("getParent");
		Thread.sleep(3000);

		switchToFrame("default");
		switchToFrame("contentFrame", screenId);

	}
	/**
	 * @author A-9847
	 * Description... retrieve message sent time
	 * 
	 * @param MessageSubType
	 * @throws InterruptedException
	 */
	public String retrieveMsgSentTime(String awbno)throws Exception

	{

		String time = new String();

		try

		{
			String ele = xls_Read.getCellValue(sheetName, "txt_gettime;xpath").replace("*",data(awbno));

			time=driver.findElement(By.xpath(ele)).getText().split(" ")[1];

			writeExtent("Pass", "Successfully retrived time " + time + " from " + screenName);
		}

		catch(Exception e)

		{
			writeExtent("Fail", "Couldn't retrieve time " + time + " from " + screenName);

		}

		return time;     

	}

	/**
	 * Description... Click View
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickView() throws InterruptedException, IOException {
		clickWebElement(sheetName, "click_View;xpath", "View", screenName);
		waitForSync(8);

	}

	/**
	 * Description... Verify Message Content
	 * 
	 * @param MessageContent
	 * @throws Exception
	 */
	public void verifyMessageContent(String MessageContent) throws Exception {
		
		
		driver.switchTo().frame("popupContainerFrame");
        ele = findDynamicXpathElement("txtarea_RawMsg;xpath", sheetName,
				"Message Content", screenName);
        String actText = ele.getText();
        System.out.println(actText);
        verifyScreenText(sheetName, data(MessageContent), actText, "Message Verification",
				screenName);
      
	}
	/**
	 * Description... Load From File
	 * 
	 * @param ParticipantType
	 * @param TransmissionMode
	 * @param InterfaceSystem
	 * @param StationName
	 * @param FileListener
	 * @param FilePath
	 * @throws Exception
	 */
	public void loadFromFileWithStatusCheck(String ParticipantType, String Participant,String TransmissionMode,
			String InterfaceSystem, String StationName, String FileListener,
			String FileName,String expMsg,boolean isXml) throws Exception {

		clickButtonSwitchWindow(sheetName, "btn_LoadFromFile;name", screenName,
				"Load From File Button");
		selectValueInDropdown(sheetName, "lst_participantType;name",
				ParticipantType, "Participant Type", "VisibleText");

		if(!ParticipantType.equals("All"))
		{
			enterValueInTextbox(sheetName, "inbx_participant;name", data(Participant),
					"Participant", "Load From File Pop up");
		}
		selectValueInDropdown(sheetName, "lst_transmissionMode;name",
				TransmissionMode, "Transmission Mode", "VisibleText");

		waitForSync(2);

		enterValueInTextbox(sheetName, "inbx_stationCode;name", data(StationName),
				"StationName", "Load From File Pop up");
		/**selectValueInDropdown(sheetName, "lst_interfaceSystem;name",
				InterfaceSystem, "Interface System", "VisibleText");**/
		clickButtonSwitchToSecondWindow(sheetName, "btn_Address;id",
				"Load From File Pop up", "Address Button");



		/****enterValueInTextbox(sheetName, "inbx_fileListener;name", FileListener,
				"File Listener", "Message Address Details Pop up");***/



		clickWebElement(sheetName, "btn_ok;name", "OK Button",
				"Message Address Details Pop up");

		switchToWindow("getFirstChild");

		try {
			if(isXml)
			{
				//check  xml checkbox
				clickWebElement(sheetName, "btn_chkXML;id", "Check XML Button",
						"Check XML");

				driver.findElement(By.name("theFile")).sendKeys(message_files+FileName+".xml");
			}

			else{
				driver.findElement(By.name("theFile")).sendKeys(message_files+FileName+".txt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		clickWebElement(sheetName, "btnLoad;name", "Load Button",
				"Load From File Pop up");
		handleAlert("Accept", "List Messages",FileName,expMsg);

		clickWebElement(sheetName, "btn_viewMsg_Close;xpath", "Close Button",
				"Load From File Pop up");

		switchToWindow("getParent");
		switchToDefaultAndContentFrame(screenId);
	}

	/**
	 * Description... Verify Message Content
	 * 
	 * @param MessageContent
	 * @throws Exception
	 */
	public void verifyMessageContent(List<String> MessageContent)
			throws Exception {
		driver.switchTo().frame("popupContainerFrame");
	       
		waitForSync(3);
		ele = findDynamicXpathElement("txtarea_RawMsg;xpath", sheetName,
				"Message Content", screenName);
		String actText = ele.getText();
		System.out.println(actText);

		for (String value : MessageContent) {
			System.out.println("Actual val is---" + value);
			verifyScreenText(sheetName, data(value), actText,
					"Message Content Verification", screenName);
		}

		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Description... Enter Reference Value as AWB  for PublishAWBDetails Request
	 * @param  AWB
	 * @throws InterruptedException
	 */
	public void enterReferenceAWBValue(String AWB) throws InterruptedException {
	
		enterValueInTextbox(sheetName, "inbx_refAWBNo;xpath", data(AWB),"ULD Num", screenName);
		waitForSync(1);
			
	}
	/**
	 * Description... Verify Message Content
	 * 
	 * @param MessageContent
	 * @throws Exception
	 */
	public void verifyMessageContent(List<String> MessageContent,String messageType)
			throws Exception {
		driver.switchTo().frame("popupContainerFrame");
	       
		waitForSync(3);
		ele = findDynamicXpathElement("txtarea_RawMsg;xpath", sheetName,
				"Message Content", screenName);
		String actText = ele.getText().toLowerCase();
		System.out.println(actText);

		for (String value : MessageContent) {
			System.out.println("Actual val is---" + data(value));
			verifyScreenText(sheetName, data(value).toLowerCase(), actText,
					"Message Content Verification for the message type "+messageType, screenName);
		}

		waitForSync(2);
	}

	

	/**
	 * Description... Verify Message Content Line
	 * 
	 * @param actText
	 * @param Line
	 * @param functinalityName
	 * @param arrayLen
	 * @throws Exception
	 */
	public void verifyMessageContentLine(String actText, String Line,
			String functinalityName, int arrayLen) throws Exception {

		String expText = Line;
		if (actText.trim().contains(expText.trim()))
			msgCounter++;
		else {
			test.log(LogStatus.FAIL, "Failed to Verify " + expText);
			Assert.assertFalse(true, "Element is not found");
		}

		if (arrayLen == msgCounter) {
			counter = counter + 1;

			excelreadwrite.insertData(DriverSetup.testName,

			commonUtility.getcurrentDateTime() + "_" + String.valueOf(counter),
					"Verify the functionality " + functinalityName + " On "
							+ screenName + " Screen",

					functinalityName, functinalityName, true, "No",
					functinalityName, functinalityName);
			test.log(LogStatus.PASS, "Verified " + functinalityName
					+ " Message Content");
		}

	}

	/**
	 * Description... Close View
	 * 
	 * @throws Exception
	 */
	public void closeView() throws Exception {
		clickWebElement(sheetName, "click_CloseView;xpath", "Close View",
				screenName);
		waitForSync(2);
		
		switchToFrame("default");
        switchToFrame("contentFrame", screenId);
        
		
	}

	/*
	 * public void clickprocess() throws InterruptedException {
	 * clickWebElement(sheetName, "click_Process;xpath", "View", screenName);
	 * waitForSync(2); }
	 */
	/**
	 * Description... Expand Search Criteria
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void expandSearchCriteria() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_ExpandSearch;xpath", "ExpandSearch",
				screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_ExpandSearch;xpath", "ExpandSearch",
				screenName);
		waitForSync(2);
	}

	/**
	 * Description... Verification of different customs in the MSG005 screen.
	 * 
	 */

	public void verifyCustomMsg(String customDes) throws Exception {
		waitForSync(8);

		switch (customDes) {
		case "CanadianCustomDes":

			ele = findDynamicXpathElement("senRec_CanCustom;xpath", sheetName,
					"Sender/Recipient", screenName);
			String actText = ele.getText();
			String expText = "JFKCILH~";
			verifyScreenText(sheetName, expText, actText,
					"Message Verification", screenName);
			waitForSync(2);
			break;

		case "EuropianCustomDes":
			ele = findDynamicXpathElement("senRec_EupCustom;xpath", sheetName,
					"Sender/Recipient", screenName);
			String actText1 = ele.getText();
			String expText1 = "ICSEULH~";
			verifyScreenText(sheetName, expText1, actText1,
					"Message Verification", screenName);
			waitForSync(2);
			break;

		case "MexicanCustomDes":
			ele = findDynamicXpathElement("senRec_MexCustom;xpath", sheetName,
					"Sender/Recipient", screenName);
			String actText2 = ele.getText();
			String expText2 = "MEXCILH~";
			verifyScreenText(sheetName, expText2, actText2,
					"Message Verification", screenName);
			waitForSync(2);
			break;

		case "USCustomDes":
			ele = findDynamicXpathElement("senRec_Uscustom;xpath", sheetName,
					"Sender/Recipient", screenName);
			String actText3 = ele.getText();
			String expText3 = "QLHCILH~";
			verifyScreenText(sheetName, expText3, actText3,
					"Message Verification", screenName);
			waitForSync(2);
			break;

		}

	}

	/**
	 * Description... returns xml tag Value when xml content and tagname is
	 * passed
	 * 
	 * @param xmlContent
	 * @param tagName
	 * @return tag value
	 */

	public String getValueFromXMLTag(String xmlContent, String tagName) {

		String tagValue = "";
		tagValue = xmlContent.split(tagName + ">")[1].split("<")[0];
		return tagValue;
	}

	/**
	 * Description... Enter Sender
	 * 
	 * @param sender
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterSender(String sender) throws InterruptedException,
			AWTException {
		enterValueInTextbox(sheetName, "inbx_sender;xpath", sender,
				"Sender Recipient", screenName);
	}

	/**
	 * Description... Click Message Check Box
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickMsgChk() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_msgID;xpath", "Msg Check Box",
				screenName);
		waitForSync(2);

	}

	/**
	 * Description... Verify xml Message
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyxmlMessage() throws InterruptedException, AWTException {
		try {
			Thread.sleep(3000);
			switchToWindow("child");
			System.out.println("Entered view message window");

		} catch (Exception e) {

			System.out.println("Could not get message reference" + e);
			test.log(LogStatus.FAIL, "Could not get message reference");
		}

		try {
			String xmlContent = getElementText(sheetName,
					"txtarea_RawMsg;xpath", "xml content", screenName);

			String FlightNo = getValueFromXMLTag(xmlContent,
					"flightscheduleNumber");
			if (FlightNo.equals(data("FlightNo"))) {
				System.out.println("found true for " + FlightNo);
				onPassUpdate(screenName, data("FlightNo"), FlightNo,
						"Flight no ", "Message verification");

			} else {
				onFailUpdate(screenName, data("FlightNo"), FlightNo,
						"Flight no ", "Message verification");

			}

			String Origin = getValueFromXMLTag(xmlContent,
					"flightscheduleOrigin");
			if (Origin.equals(data("Origin"))) {
				System.out.println("found true for " + Origin);
				onPassUpdate(screenName, data("Origin"), Origin, "Origin ",
						"Message verification");

			} else {
				onFailUpdate(screenName, data("Origin"), Origin, "Origin ",
						"Message verification");

			}

			String Destination = getValueFromXMLTag(xmlContent,
					"flightscheduleDestination");
			if (Destination.equals(data("Destination"))) {
				System.out.println("found true for " + Destination);
				onPassUpdate(screenName, data("Destination"), Destination,
						"Destination", "Message verification");

			} else {
				onFailUpdate(screenName, data("Destination"), Destination,
						"Destination", "Message verification");

			}

			String Route = getValueFromXMLTag(xmlContent, "flightscheduleRoute");
			if (Route.equals(data("Route"))) {
				System.out.println("found true for " + Route);
				onPassUpdate(screenName, data("Route"), Route, "Route",
						"Message verification");

			} else {
				onFailUpdate(screenName, data("Route"), Route, "Route",
						"Message verification");

			}

			String FromDate = getValueFromXMLTag(xmlContent,
					"flightscheduleFromDate");
			String ExpFromDate = createDateFormat("dd-MMM-YYYY", 5, "DAY", "");

			if (FromDate.equals(ExpFromDate)) {
				System.out.println("found true for " + FromDate);
				onPassUpdate(screenName, ExpFromDate, FromDate, "StartDate",
						"Message verification");

			} else {
				onFailUpdate(screenName, ExpFromDate, FromDate, "StartDate",
						"Message verification");

			}

			String ToDate = getValueFromXMLTag(xmlContent,
					"flightscheduleToDate");
			String ExpToDate = createDateFormat("dd-MMM-YYYY", 15, "DAY", "");
			if (ToDate.equals(ExpToDate)) {
				System.out.println("found true for " + ToDate);
				onPassUpdate(screenName, ExpToDate, ToDate, "EndDate",
						"Message verification");

			} else {
				onFailUpdate(screenName, ExpToDate, ToDate, "EndDate",
						"Message verification");

			}

			String AircraftType = getValueFromXMLTag(xmlContent,
					"airCraftTypeCode");
			Assert.assertEquals(AircraftType, data("AircraftType"));
			if (AircraftType.equals(data("AircraftType"))) {
				System.out.println("found true for " + AircraftType);
				onPassUpdate(screenName, data("AircraftType"), AircraftType,
						"AircraftType", "Message verification");

			} else {
				onFailUpdate(screenName, data("AircraftType"), AircraftType,
						"AircraftType", "Message verification");

			}

		} catch (Exception e) {

			System.out.println("Could not verify message" + e);
			test.log(LogStatus.FAIL, "Could not verify message");

		}

	}

	/**
	 * Description... Verify Sender Reciver
	 * 
	 * @param sender
	 * @param locator
	 * @throws Exception
	 */
	public void verifySenderReciver(String sender, String locator)
			throws Exception {

		ele = findDynamicXpathElement(locator, sheetName,
				" Sender Recipient Verification ", screenName);
		String actText = ele.getText();
		verifyScreenText(sheetName, data(sender), actText,
				" Sender Recipient Verification", screenName);
	}

	/**
	 * Description... Verify Message
	 * 
	 * @param msg
	 * @throws Exception
	 */
	public void verifyMessage(String msg) throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(3);
		ele = findDynamicXpathElement("div_reco;xpath", sheetName,
				" Message content Verification ", screenName);
		String actText = ele.getText();
		verifyScreenText(sheetName, msg, actText,
				"Message content Verification", screenName);
		waitForSync(2);
	}

	/**
	 * Description... Verify No Message Triggered
	 * 
	 * @throws Exception
	 */
	public void verifyNoMsgTriggered() throws Exception {

		verifyElementDisplayed("Generic_Elements", "htmlDiv_errorText;xpath",
				"No results found for the specified criteria panel",

				screenName, "error Div");

	}

	/**
	 * Description... Verify Email Not Sent
	 * 
	 * @param AWBno
	 * @param AWBPre
	 * @param Origin
	 * @param Destination
	 * @param Value
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyEmailNotSent(String AWBno, String AWBPre, String Origin,
			String Destination, int Value) throws

	InterruptedException, AWTException {
		String awbNo = data(AWBno);
		String awbPre = data(AWBPre);
		String ORG = data(Origin);
		String DES = data(Destination);
		String Xpath = (awbPre + " - " + awbNo + " - " + ORG + " - " + DES);
		System.out.println(Xpath);
		String temp = "//*[text()= '" + Xpath + "']";
		System.out.println(temp);

		int iCount = driver.findElements(By.xpath(temp)).size();

		if (iCount > Value) {
			System.out.println("Email Sent");
			writeExtent("Fail", "Email Sent");

		} else {
			System.out.println("Email not Sent");
			writeExtent("Pass", "Email not Sent");
		}

	}

	/**
	 * Description... Select Interface System
	 * 
	 * @param MessageSubType
	 * @throws InterruptedException
	 */
	public void selectInterfaceSystem(String MessageSubType)
			throws InterruptedException {

		selectValueInDropdown(sheetName, "lst_InterfaceSystem;xpath",
				MessageSubType, "Interface System", "VisibleText");
		waitForSync(2);

	}

	/**
	 * Description... Enter Sender Receiver
	 * 
	 * @param sender
	 * @throws InterruptedException
	 */
	public void enterSenderReceiver(String sender) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_messageSentOrReceipt;name",
				sender, "Sender Recipient", screenName);

	}

	/**
	 * Description... Enters message 2 check box
	 * @throws IOException 
	 */
	public void clickMsg2Chk() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_msgID2;xpath", "Msg Check Box",
				screenName);
		waitForSync(2);

	}

	/**
	 * Description... Verify Handling Profile
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyHandlingProfile() throws InterruptedException, IOException {
		String actHandlingProfile = getElementText(sheetName,
				"txt_handlingProfile;xpath", "Handling Profile", screenName);
		verifyValueOnPageContains(actHandlingProfile, data("HandlingProfile"),
				"Verify Handling Profile", actHandlingProfile,
				"Handling Profile");
	}

	/**
	 * Description... Verify XFNM Content
	 * 
	 * @throws Exception
	 */
	public void verifyXFNMContent() throws Exception {
		String expXFNMContent = getMessageContent();

		String StatusCode = getValueFromXMLTag(expXFNMContent, "StatusCode");
		String ConditionCode = getValueFromXMLTag(expXFNMContent,
				"ConditionCode");
		String PurposeCode = getValueFromXMLTag(expXFNMContent, "PurposeCode");

		verifyValueOnPage(StatusCode, data("StatusCode"), "Verify Status Code",
				screenName, "Status Code");
		verifyValueOnPage(ConditionCode, data("ConditionCode"),
				"Verify Condition Code", screenName, "Condition Code");
		verifyValueOnPage(PurposeCode, data("PurposeCode"),
				"Verify Purpose Code", screenName, "Purpose Code");

	}

	/**
	 * Description... Verify Message Sent To RECO
	 * 
	 * @throws Exception
	 */
	public void verifyMessageSentToRECO() throws Exception {
		switchToWindow("storeParent");
		clickViewlogs("");
		switchToWindow("child");
		verifyElementDisplayed(sheetName, "txt_MsgSentToRECO;xpath",
				"Verify Message sent to RECO", screenName,
				"Message sent to RECO");
		clickWebElementByWebDriver("ListMessages_MSG005", "btn_closeBtn;xpath",
				"Close Button", "Message View Button Pop up");

		switchToWindow("getParent");
		switchToDefaultAndContentFrame("MSG005");
	}

	/**
	 * Description... Verify Handling SSR Instructions In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyHandlingSSRInstrInXFWB() throws Exception {
		clickMsgChk();
		clickButtonSwitchWindow("Generic_Elements", "btn_view;name",
				"View Button", "List Messages");
		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");

		try {
			String expMessageContent[] = { data("HandlingSSRInstructions"),
					data("HandlingOSIInstructions") };

			String tagName[] = { "HandlingSSRInstructions",
					"HandlingOSIInstructions" };

			for (int i = 0; i < expMessageContent.length; i++)
				verifyValueOnPageContains(actMessageContentXML,
						expMessageContent[i],
						"1.Make a Booking\n2.Depart Flight\n3.Verify"
								+ tagName[i] + "tag Value in XML Content",
						"List Messages", tagName[i]);

			clickButtonSwitchtoParentWindow("Generic_Elements",
					"butn_close;name", "Close Button", "List Messages");
			switchToDefaultAndContentFrame("MSG005");
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	/**
	 * Description... Evaluate XPath
	 * 
	 * @param document
	 * @param xpathExpression
	 * @return
	 * @throws Exception
	 */
	private static List<String> evaluateXPath(Document document,
			String xpathExpression) throws Exception {
		// Create XPathFactory object
		XPathFactory xpathFactory = XPathFactory.newInstance();

		// Create XPath object
		XPath xpath = xpathFactory.newXPath();

		List<String> values = new ArrayList<>();
		try {
			// Create XPathExpression object
			XPathExpression expr = xpath.compile(xpathExpression);

			// Evaluate expression result on XML document
			NodeList nodes = (NodeList) expr.evaluate(document,
					XPathConstants.NODESET);

			for (int i = 0; i < nodes.getLength(); i++) {
				values.add(nodes.item(i).getNodeValue());
			}

		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return values;
	}

	/**
	 * Description... Get XML tagvalue for Handling SSR Instructions Description
	 * 
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	private static Document getDocument(String fileName) throws Exception {

		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		Document document = docBuilder.parse(new InputSource(new StringReader(
				fileName)));

		System.out.println("Root element: "
				+ document.getDocumentElement().getNodeName());
		NodeList nodeList = document
				.getElementsByTagName("//HandlingSSRInstructions/Description");
		for (int temp = 0; temp < nodeList.getLength(); temp++) {
			org.w3c.dom.Node node = nodeList.item(temp);
			System.out.println("\nCurrent element: " + node.getNodeName());
			if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
				Element element = (Element) node;
				System.out.println("Name: "
						+ element.getElementsByTagName

						("//HandlingSSRInstructions/Description").item(0)
								.getTextContent());

			}
		}

		return document;

	}

	/**
	 * Description... Verify XFNM Content Error
	 * 
	 * @throws Exception
	 */
	public void verifyXFNMContentError() throws Exception {
		String expXFNMContent = getMessageContent();

		String StatusCode = getValueFromXMLTag(expXFNMContent, "StatusCode");
		String ConditionCode = getValueFromXMLTag(expXFNMContent,
				"ConditionCode");

		verifyValueOnPage(StatusCode, data("StatusCode"), "Verify Status Code",
				screenName, "Status Code");
		verifyValueOnPage(ConditionCode, data("ConditionCode"),
				"Verify Condition Code", screenName, "Condition Code");

	}

	/**
	 * Description... Verify Shipper Street Name Post office box
	 * 
	 * @throws Exception
	 */
	public void verifyShprStrtNmPostOfcBox() throws Exception {
		clickMsgChk();
		clickViewButton();
		String actXFWBContent = getMessageContent();
		String actStreetName = getValueFromXMLTag(actXFWBContent, "StreetName");
		String actPostofficebox = getValueFromXMLTag(actXFWBContent,
				"Postofficebox");
		verifyValueOnPage(actStreetName, data("ShipperStreetAddress"),
				"Verify Shipper Address", screenName, "Shipper Address");
		verifyValueOnPage(actPostofficebox, data("Postofficebox"),
				"Verify Post office box", screenName, "Post office box");
	}

	/**
	 * Description... Verify Address Profile
	 * 
	 * @param AddressProfile
	 * @throws Exception
	 */
	public void verifyAddressProfile(String AddressProfile) throws Exception {
		waitForSync(2);
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(2);
		try {
			ele = findDynamicXpathElement("tbl_Viewlogs_HandlingProfile;xpath",
					sheetName, "Handling code",

					screenName);
			waitForSync(1);
			String actText = ele.getText();
			waitForSync(1);
			String expText = data(AddressProfile);
			verifyScreenText(sheetName, expText, actText,
					"Message Verification", screenName);

		} catch (Exception e) {
			System.out.println("Handling code is not verified");
		}

	}

	/**
	 * Description... Click View Button Switch Window
	 * 
	 * @throws Exception
	 */
	public void clickButtonSwitchWindow() throws Exception {

		clickButtonSwitchWindow("Generic_Elements", "btn_view;name",
				"View Button", "List Messages");

	}

	/**
	 * Description... Verify AWB In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyAWBInXFWB() throws Exception {

		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent = "<TransportContractDocument>" + "\n"
				+ "<ID>" + data("Full AWBNo") + "</ID>";
		System.out.println(expMessageContent);
		verifyScreenText(sheetName, expMessageContent, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify HAWBNo In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyHAWBNoInXFWB() throws Exception {

		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent = "<ns2:BusinessHeaderDocument>" + "\n"
				+ "<ID>" + data("HAWB") + "</ID>";
		System.out.println(expMessageContent);
		verifyScreenText(sheetName, expMessageContent, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify Summary Description In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifySummaryDescriptionInXFWB() throws Exception {

		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent = "<SummaryDescription>"
				+ data("ShipmentDesc") + " " + data("RemarksXFZB");
		System.out.println(expMessageContent);
		verifyScreenText(sheetName, expMessageContent, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify Origin Destination In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyOriginDestinationInXFWB() throws Exception {

		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent3 = "<OccurrenceArrivalLocation>" + "\n"
				+ "<ID>" + data("Destination") + "</ID>";
		System.out.println(expMessageContent3);
		verifyScreenText(sheetName, expMessageContent3, actMessageContentXML,
				"Message Verification", screenName);
		String expMessageContent4 = "<OccurrenceDepartureLocation>" + "\n"
				+ "<ID>" + data("Origin") + "</ID>";
		System.out.println(expMessageContent3);
		verifyScreenText(sheetName, expMessageContent4, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Click Close Button Switch to List Messages Window
	 * 
	 * @throws Exception
	 */
	public void clickButtonSwitchtoParentWindow() throws Exception {

		clickButtonSwitchtoParentWindow("Generic_Elements", "butn_close;name",
				"Close Button", "List Messages");

	}

	/**
	 * Description... verify Pieces Weight In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyPiecesWeightInXFWB() throws Exception {
		char ch = '"';
		String unitCode = "KGM";
		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent1 = "<IncludedTareGrossWeightMeasure unitCode="
				+ ch + unitCode + ch + ">" + data("WeightXFZB")
				+ "</IncludedTareGrossWeightMeasure>" + "\n"
				+ "<TotalPieceQuantity>" + data("Pieces");
		;
		System.out.println(expMessageContent1);
		verifyScreenText(sheetName, expMessageContent1, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify Shipper Consignee In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyShipperConsigneeInXFWB() throws Exception {

		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent = "<ConsignorParty>" + "\n"
				+ "<Name>GUBI A S</Name>" + "\n" + "<PostalStructuredAddress>"
				+ "\n" + "<PostcodeCode>2150</PostcodeCode>" + "\n"
				+ "<StreetName>KLUBIENSVEJ 7-9 PAKHUS 53</StreetName>" + "\n"
				+ "<CityName>COPENHAGEN NORDHA</CityName>" + "\n"
				+ "<CountryID>DK</CountryID>" + "\n"
				+ "<CountryName>DENMARK</CountryName>" + "\n"
				+ "</PostalStructuredAddress>" + "\n" + "<DefinedTradeContact>"
				+ "\n" + "<DirectTelephoneCommunication>" + "\n"
				+ "<CompleteNumber>4533377911</CompleteNumber>" + "\n"
				+ "</DirectTelephoneCommunication>" + "\n"
				+ "</DefinedTradeContact>" + "\n" +

				"</ConsignorParty>";
		System.out.println(expMessageContent);
		verifyScreenText(sheetName, expMessageContent, actMessageContentXML,
				"Message Verification", screenName);
		String expMessageContent1 = "<ConsigneeParty>"
				+ "\n"
				+ "<Name>TONIC DESIGN CC</Name>"
				+ "\n"
				+ "<PostalStructuredAddress>"
				+ "\n"
				+ "<PostcodeCode>2642</PostcodeCode>"
				+ "\n"
				+ "<StreetName>SHOP 9 PARKTOWN QUARTER CRN 3RD   7</StreetName>"
				+ "\n" + "<CityName>PARKTOWN</CityName>" + "\n"
				+ "<CountryID>ZA</CountryID>" + "\n"
				+ "<CountryName>SOUTH AFRICA</CountryName>" + "\n"
				+ "</PostalStructuredAddress>" + "\n" + "<DefinedTradeContact>"
				+ "\n" + "<DirectTelephoneCommunication>" + "\n"
				+ "<CompleteNumber>4533377911</CompleteNumber>" + "\n"
				+ "</DirectTelephoneCommunication>" +

				"\n" + "</DefinedTradeContact>" + "\n" + "</ConsigneeParty>";
		System.out.println(expMessageContent1);
		verifyScreenText(sheetName, expMessageContent1, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify Harmonized Commodity Code In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyHarmonizedCommodityCodeInXFWB() throws Exception {

		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent = "<IncludedHouseConsignmentItem>" + "\n"
				+ "<SequenceNumeric>1</SequenceNumeric>" + "\n"
				+ "<TypeCode>123213</TypeCode>" + "\n"
				+ "<TypeCode>452123</TypeCode>" + "\n"
				+ "<TypeCode>452143</TypeCode>" + "\n"
				+ "<TypeCode>9563277451</TypeCode>" + "\n"
				+ "<TypeCode>785421</TypeCode>" + "\n"
				+ "<TypeCode>875421</TypeCode>" + "\n"
				+ "<TypeCode>8745542</TypeCode>" + "\n"
				+ "<TypeCode>5652322</TypeCode>" + "\n"
				+ "<TypeCode>8754542</TypeCode>";
		System.out.println(expMessageContent);
		verifyScreenText(sheetName, expMessageContent, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify OCI In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifyOCIInXFWB() throws Exception {

		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent = "<IncludedCustomsNote>" + "\n"
				+ "<ContentCode>ST</ContentCode>" + "\n"
				+ "<Content>SHIPMENT DESCRIPTION NOT</Content>" + "\n"
				+ "<SubjectCode>ISS</SubjectCode>" + "\n"
				+ "<CountryID>DE</CountryID>" + "\n" + "</IncludedCustomsNote>"
				+ "\n" + "<IncludedCustomsNote>"

				+ "\n" + "<ContentCode>ED</ContentCode>" + "\n"
				+ "<Content>1299</Content>" + "\n" +

				"</IncludedCustomsNote>" + "\n" + "<IncludedCustomsNote>"
				+ "\n" + "<ContentCode>M</ContentCode>" + "\n"
				+ "<Content>15DE875212911200E9</Content>" + "\n"
				+ "<SubjectCode>EXP</SubjectCode>" + "\n"
				+ "<CountryID>DE</CountryID>" + "\n" + "</IncludedCustomsNote>";
		System.out.println(expMessageContent);
		verifyScreenText(sheetName, expMessageContent, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify Type Code In XFWB
	 * 
	 * @param TypeCode
	 * @throws Exception
	 */
	public void verifyTypeCodeInXFWB(String TypeCode) throws Exception {

		clickButtonSwitchWindow("Generic_Elements", "btn_view;name",
				"View Button", "List Messages");
		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent1 = "<TypeCode>" + data(TypeCode)
				+ "</TypeCode>";
		verifyScreenText(sheetName, expMessageContent1, actMessageContentXML,
				"Message Verification", screenName);
		clickButtonSwitchtoParentWindow("Generic_Elements", "butn_close;name",
				"Close Button", "List Messages");

	}

	/**
	 * Description... Verify Specified Logistics Transport Movement In XFWB
	 * 
	 * @throws Exception
	 */
	public void verifySpecifiedLogisticsTransportMovementInXFWB()
			throws Exception {

		clickButtonSwitchWindow("Generic_Elements", "btn_view;name",
				"View Button", "List Messages");
		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent1 = "<SpecifiedLogisticsTransportMovement>";
		verifyScreenText(sheetName, expMessageContent1, actMessageContentXML,
				"Message Verification", screenName);
		String expMessageContent2 = "</SpecifiedLogisticsTransportMovement>";
		verifyScreenText(sheetName, expMessageContent2, actMessageContentXML,
				"Message Verification", screenName);
		String expMessageContent3 = "<OccurrenceArrivalLocation>" + "\n"
				+ "<ID>" + data("Destination") + "</ID>";
		System.out.println(expMessageContent3);
		verifyScreenText(sheetName, expMessageContent3, actMessageContentXML,
				"Message Verification", screenName);
		String expMessageContent4 = "<OccurrenceDepartureLocation>" + "\n"
				+ "<ID>" + data("Origin") + "</ID>";
		System.out.println(expMessageContent3);
		verifyScreenText(sheetName, expMessageContent4, actMessageContentXML,
				"Message Verification", screenName);
		clickButtonSwitchtoParentWindow("Generic_Elements", "butn_close;name",
				"Close Button", "List Messages");
	}

	/**
	 * Description... Verify message with the primary key is displayed
	 * 
	 * @param pmyKey
	 * @param msgType
	 * @throws InterruptedException
	 */
	public void verifyMessageDisplayedPmyKey(String pmyKey, String msgType)
			throws InterruptedException {
		String xpath = xls_Read.getCellValue(sheetName, "chk_AWBNo_Row;xpath")
				.replace("AWBNo", pmyKey);
		verifyElementDisplayed(xpath, "Verify " + msgType + " Message",
				screenName, msgType + " Message");
	}

	/**
	 * Description... Verify FHL Content
	 * 
	 * @throws Exception
	 */
	public void verifyFHLContent() throws Exception {
		String actFHLMsgTxt1 = getMessageContent();
		String expFHLMsgTxt1[] = {

				data("HAWB") + "/" + data("Origin") + data("Destination") + "/"
						+ data("Pieces") + "/" + "K" + data("Weight") + "//"
						+ data("ShipmentDesc").toUpperCase(),

				data("ShipperFHL"), data("ConsigneeFHL")

		};

		for (String line : expFHLMsgTxt1) {
			System.out.println(line);
			verifyMessageContentLine(actFHLMsgTxt1, line,
					"FHL Message Content", expFHLMsgTxt1.length);
		}

	}

	/**
	 * Description... Count the number of sent messages on List Message screen
	 * 
	 * @return
	 */
	public int countNoOfSentMessage() {
		return returnListSize(returnListOfElements(sheetName,
				"txt_NoOfSentMsg;xpath"));
	}

	/**
	 * Description... verify Message Sent After performing Any Operation by
	 * comparing number of messages before and after the operation
	 * 
	 * @param noOfSentMsgBeforeOp
	 * @param noOfSentMsgAfterOp
	 * @param opName
	 * @param msgType
	 */
	public void verifyMessageSentAfterAnyOp(int noOfSentMsgBeforeOp,
			int noOfSentMsgAfterOp, String opName, String msgType) {

		if (noOfSentMsgAfterOp > noOfSentMsgBeforeOp)
			verifyValueOnPage(true, true, "Verify " + msgType
					+ " Message triggered after " + opName, screenName, opName);
		else
			verifyValueOnPage(true, false, "Verify " + msgType
					+ " Message triggered after " + opName, screenName, opName);

	}

	/**
	 * Description... Verify Pieces Weight In XFZB
	 * 
	 * @throws Exception
	 */
	public void verifyPiecesWeightInXFZB() throws Exception {
		char ch = '"';
		String unitCode = "KGM";
		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String expMessageContent1 = "<GrossWeightMeasure unitCode=" + ch
				+ unitCode + ch + ">" + data("WeightXFZB")
				+ "</GrossWeightMeasure>" + "\n" + "<PieceQuantity>"
				+ data("Pieces");
		;
		System.out.println(expMessageContent1);
		verifyScreenText(sheetName, expMessageContent1, actMessageContentXML,
				"Message Verification", screenName);

	}

	/**
	 * Description... Verify SRN 1 and SRN 2
	 * 
	 * @throws Exception
	 */
	public void verifySRN1SRN2() throws Exception {

		String expSRN1 = getElementText(sheetName, "txt_SRN1;xpath",
				"Shipper Reference No 1", screenName);
		String expSRN2 = getElementText(sheetName, "txt_SRN2;xpath",
				"Shipper Reference No 2", screenName);

		if (expSRN1.contains("SRN1") | expSRN1.contains("SRN2")
				& expSRN2.contains("SRN1") | expSRN2.contains("SRN2"))
			verifyValueOnPage(true, true,
					"Verify Shipper Reference No 1 and Shipper Reference No 2",
					screenName,
					"Shipper Reference No 1 and Shipper Reference No 2");
		else
			verifyValueOnPage(true, false,
					"Verify Shipper Reference No 1 and Shipper Reference No 2",
					screenName,
					"Shipper Reference No 1 and Shipper Reference No 2");
	}

	/**
	 * Description... Verify Purpose Code
	 * 
	 * @throws Exception
	 */
	public void verifyPurposeCode() throws Exception {
		checkIfUnchecked("ListMessages_MSG005", "chk_msgFSU;xpath",
				"Message select check box", "List Messages");
		clickView();
		String expXFNMContent = getMessageContent();
		String PurposeCode = getValueFromXMLTag(expXFNMContent, "PurposeCode");

		verifyValueOnPage(data("PurposeCode").toUpperCase(),
				PurposeCode.toUpperCase(), "Verify Status Code", screenName,
				"Status Code");

	}

	/**
	 * Description... Verify Address Profile
	 * 
	 * @param AddressProfile
	 * @param functionalityName
	 * @throws Exception
	 */
	public void verifyAddressProfile(String AddressProfile,
			String functionalityName) throws Exception {
		checkIfUnchecked("ListMessages_MSG005", "chk_msgFSU;xpath",
				"Message select check box", "List Messages");
		clickViewlogs("");
		waitForSync(2);
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(2);
		try {
			ele = findDynamicXpathElement("tbl_Viewlogs_HandlingProfile;xpath",
					sheetName, functionalityName, screenName);
			waitForSync(1);
			String actText = ele.getText();
			waitForSync(1);
			String expText = data(AddressProfile);
			verifyScreenText(sheetName, expText, actText, "Verify "
					+ functionalityName, screenName);

		} catch (Exception e) {
			System.out.println(functionalityName + " not verified");
		}
		clickWebElement(sheetName, "bttn_close;xpath", "Close View Button",
				screenName);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("MSG005");

	}

	/**
	 * Description... Verify Shipper Declaration Info And Purpose Code
	 * 
	 * @throws Exception
	 */
	public void verifyShipperDeclarationInfoAndPurposeCode() throws Exception {
		checkIfUnchecked("ListMessages_MSG005", "chk_msgFSU;xpath",
				"Message select check box", "List Messages");
		clickView();
		String expXSDGContent = getMessageContent();
		String PurposeCode = getValueFromXMLTag(expXSDGContent, "PurposeCode");

		verifyValueOnPage(data("PurposeCode").toUpperCase(),
				PurposeCode.toUpperCase(), "Verify Status Code", screenName,
				"Status Code");
		String ShipperDeclarationInformation = getValueFromXMLTag(
				expXSDGContent, "ShipperDeclarationInformation");
		verifyValueOnPage(
				ShipperDeclarationInformation,
				data("ShipperDeclarationInformation"),
				"Verify Shipper Declaration Information Tag contains info processed in Statement Tag",

				screenName, "Shipper Declaration Information Tag Data");
	}

	/**
	 * Description... Verify Message Processed Successfully
	 * 
	 * @param msgType
	 * @throws InterruptedException
	 */
	public void verifyMessageProcessedSuccessfully(String msgType)
			throws InterruptedException {
		verifyElementDisplayed(sheetName, "txt_ProcessedSuccessfully;xpath",
				"Verify " + msgType + "Message Processed Successfully",
				screenName, msgType + " Message Processed Successfully");

	}

	/**
	 * Description... Verify Message Not Sent After Any Operation
	 * 
	 * @param noOfSentMsgBeforeOp
	 * @param noOfSentMsgAfterOp
	 * @param opName
	 * @param msgType
	 */
	public void verifyMessageNotSentAfterAnyOp(int noOfSentMsgBeforeOp,
			int noOfSentMsgAfterOp, String opName, String msgType) {

		if (noOfSentMsgAfterOp == noOfSentMsgBeforeOp)
			verifyValueOnPage(true, true, "Verify " + msgType
					+ " Message Not triggered after " + opName,

			screenName, opName);
		else
			verifyValueOnPage(true, false, "Verify " + msgType
					+ " Message triggered after " + opName, screenName, opName);

	}

	/**
	 * Description... Click Message select Check Box
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickMessageCheckBox1() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_msgID;xpath",
				"Message select Check Box", screenName);

	}

	/**
	 * Description... Verify View Message Content
	 * 
	 * @param val
	 * @param valName
	 * @throws Exception
	 */
	public void verifyViewMessageContent(String val[], String valName[])
			throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		ele = findDynamicXpathElement("tab_allText_view;xpath", sheetName,
				"Handling code", screenName);
		waitForSync(2);
		try {
			String actText = ele.getText().replaceAll(" ", "").trim();
			for (int i = 0; i < val.length; i++) {
				String expText = val[i].replaceAll(" ", "").trim();

				verifyScreenText(sheetName, expText, actText, valName[i],
						screenName);

			}

		} catch (Exception e) {
			System.out
					.println("Failed in verifying the Message content in View Tab"
							+ " On " + screenName + " Page");
			writeExtent("Fail",
					"Failed in verifying the Message content in View Tab"
							+ " On " + screenName + " Page");
			Assert.assertFalse(true,
					"Failed in verifying the Message content in View Tab"
							+ " On " + screenName + " Page");
		}
	}

	/**
	 * Description... Verify Error Shipper Consignee
	 * 
	 * @throws InterruptedException
	 */
	public void verifyErrorShipCons() throws InterruptedException {
		verifyElementDisplayed(
				sheetName,
				"inbx_errorMissingShipAdd;xpath",
				"Verify One of Street Name/PO Box is mandatory (MIP code: SHP03)",
				screenName, "Street Name/PO Box is mandatory for Shipper");
		verifyElementDisplayed(
				sheetName,
				"inbx_errorMissingConsAdd;xpath",
				"Verify One of Street Name/PO Box is mandatory  (MIP code: CNE03)",
				screenName, "Street Name/PO Box is mandatory for Consignee");

	}

	/*
	 * public void verifyErrorCodeSameOrigin() throws Exception { String
	 * expXFNMContent=getMessageContent(); String ReasonCode =
	 * getValueFromXMLTag(expXFNMContent, "ReasonCode"); String Reason =
	 * getValueFromXMLTag(expXFNMContent, "Reason");
	 * 
	 * 
	 * verifyValueOnPageContains(ReasonCode, data("ReasonCode"),
	 * "Verify Reason Code", screenName, "Reason Code");
	 * verifyValueOnPageContains(Reason, data("Reason"), "Verify Reason",
	 * screenName, "Reason");
	 * 
	 * }
	 */

	/**
	 * Description... Verify XFNM Error
	 * 
	 * @param statusCode
	 * @param conditionCode
	 * @param reasonCode
	 * @param reason
	 * @throws Exception
	 */
	public void verifyXFNMError(String statusCode, String conditionCode,
			String reasonCode, String reason) throws Exception {
		String expXFNMContent = getMessageContent();

		String StatusCode = getValueFromXMLTag(expXFNMContent, "StatusCode");
		String ConditionCode = getValueFromXMLTag(expXFNMContent,
				"ConditionCode");
		String ReasonCode = getValueFromXMLTag(expXFNMContent, "ReasonCode");
		String Reason = getValueFromXMLTag(expXFNMContent, "Reason");

		verifyValueOnPage(StatusCode, data(statusCode), "Verify Status Code",
				screenName, "Status Code");
		verifyValueOnPage(ConditionCode, data(conditionCode),
				"Verify Condition Code", screenName, "Condition Code");

		verifyValueOnPageContains(ReasonCode, data(reasonCode),
				"Verify Status Code", screenName, "Status Code");
		verifyValueOnPageContains(Reason, data(reason),
				"Verify Condition Code", screenName, "Condition Code");

	}

	/**
	 * Description... Verify Message Remark
	 * 
	 * @param expectedMsgStatus
	 * @param msgType
	 * @throws InterruptedException
	 */
	public void verifyMessageRemark(String expectedMsgStatus, String msgType)
			throws InterruptedException {
		/*
		 * String actMsgStatus = getElementText("ListMessages_MSG005",
		 * "txt_messageRemark;xpath", "Message Status", screenName);
		 * 
		 * verifyValueOnPageContains(actMsgStatus, expectedMsgStatus, "Verify "
		 * + msgType + " Message status", screenName, "Message Status");
		 */

	}

	/**
	 * Description... Select AWB CheckBox
	 * 
	 * @param AWBNo
	 * @throws InterruptedException
	 */
	public void selectAWBCheckBox(String AWBNo) throws InterruptedException {
		String xpath = xls_Read.getCellValue(sheetName, "chk_AWBNo_Row;xpath")
				.replace("AWBNo", AWBNo);
		clickWebElement(xpath, "AWB No Check Box", screenName);

	}

	/**
	 * Description... Click List Without Date
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickListWithoutDate() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "List_Msg;xpath", "List", screenName);
		waitForSync(3);
	}

	/**
	 * Description... Verify XFNM Error
	 * 
	 * @param statusCode
	 * @param conditionCode
	 * @param reason
	 * @throws Exception
	 */
	public void verifyXFNMError(String statusCode, String conditionCode,
			String reason) throws Exception {
		String expXFNMContent = getMessageContent();

		String StatusCode = getValueFromXMLTag(expXFNMContent, "StatusCode");
		String ConditionCode = getValueFromXMLTag(expXFNMContent,
				"ConditionCode");
		String Reason = getValueFromXMLTag(expXFNMContent, "Reason");

		verifyValueOnPage(StatusCode, data(statusCode), "Verify Status Code",
				screenName, "Status Code");
		verifyValueOnPage(ConditionCode, data(conditionCode),
				"Verify Condition Code", screenName, "Condition Code");
		verifyValueOnPageContains(Reason, data(reason),
				"Verify Condition Code", screenName, "Condition Code");

	}

	/**
	 * Description... Click Edit Button
	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickEditButton() throws InterruptedException, IOException {
		clickWebElement("Generic_Elements", "lnk_edit;xpath", "Edit Button",
				screenName);

	}

	/**
	 * Description... Verify No MIP Description
	 * 
	 * @throws Exception
	 */
	public void verifyNoMIPDescription() throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		verifyElementNotDisplayed(
				xls_Read.getCellValue("ListMessages_MSG005",
						"table_ErrorDesc;xpath"),
				"1.Search screen MSG005 2.List AWB 3. Verify MIP code not displayed",
				screenName, "MIP Code");
		clickWebElement("Generic_Elements", "butn_close;name", "Close Button",
				"Message View Button Pop up");
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "MSG005");

	}

	/**
	 * Description... Load From File
	 * 
	 * @param ParticipantType
	 * @param TransmissionMode
	 * @param InterfaceSystem
	 * @param StationName
	 * @param FileListener
	 * @param FilePath
	 * @throws Exception
	 */
	public void loadFromFile(String ParticipantType, String Participant,String TransmissionMode,
			String InterfaceSystem, String StationName, String FileListener,
			String FileName) throws Exception {

		/************************/
		  if(FileName.contains("MVT")){

			ParticipantType="Airline";
			Participant= "carrierCode";
			TransmissionMode="MQ-SERIES";
			

		}
		/************************/
		clickButtonSwitchWindow(sheetName, "btn_LoadFromFile;name", screenName,
				"Load From File Button");
		

	
		selectValueInDropdown(sheetName, "lst_participantType;name",
				ParticipantType, "Participant Type", "VisibleText");
		
		if(!ParticipantType.equals("All"))
		{
			enterValueInTextbox(sheetName, "inbx_participant;name", data(Participant),
					"Participant", "Load From File Pop up");
		}
		selectValueInDropdown(sheetName, "lst_transmissionMode;name",
				TransmissionMode, "Transmission Mode", "VisibleText");
		
		waitForSync(2);
	
		enterValueInTextbox(sheetName, "inbx_stationCode;name", data(StationName),
				"StationName", "Load From File Pop up");
		/**selectValueInDropdown(sheetName, "lst_interfaceSystem;name",
				InterfaceSystem, "Interface System", "VisibleText");**/
		clickButtonSwitchToSecondWindow(sheetName, "btn_Address;id",
				"Load From File Pop up", "Address Button");
		
		
        if(TransmissionMode.equals("FILE")){
            
      enterValueInTextbox(sheetName, "inbx_fileListener;name", data(FileListener),
             "File Listener", "Message Address Details Pop up");
             
      }

		
		

		clickWebElement(sheetName, "btn_ok;name", "OK Button",
				"Message Address Details Pop up");
		
		switchToWindow("getFirstChild");

		try {
			driver.findElement(By.name("theFile")).sendKeys(message_files+FileName+".txt");
		} catch (Exception e) {
			e.printStackTrace();
		}
		clickWebElement(sheetName, "btnLoad;name", "Load Button",
				"Load From File Pop up");
		  handleAlert("Accept", "List Messages",FileName);

		clickWebElement(sheetName, "btn_viewMsg_Close;xpath", "Close Button",
				"Load From File Pop up");

		switchToWindow("getParent");
		switchToDefaultAndContentFrame(screenId);
	}
	/* @author A-10328
	 * Desc..  get the number of records present in the table
	 * @throws InterruptedException
	 * @throws IOException
 */
	
	public void getNumberOfRecordsPresent(String UldNum,int expcount) throws InterruptedException, IOException

	{

		waitForSync(3);
		String locator = xls_Read.getCellValue(sheetName, "table_ULDNo;xpath");
		locator = locator.replace("*", UldNum);
		int size=driver.findElements(By.xpath(locator)).size();
		System.out.println(size);

		if(size==expcount)
		{
			writeExtent("Pass", "Successfully verified count as " + expcount + "on "+screenName);
		}
		else
		{
			writeExtent("Fail", "Failed to verify the count as " + expcount + "where the actual records came as "+size+" on "+screenName);
		}


	}

	/**
	 * Description... Handles an alert with options getText/Accept/Dismiss/Close
	 * 
	 * @param alertOps
	 * @param ScreenName
	 */
	public boolean handleAlert(String alertOps, String ScreenName,String fileName) {
		switchToFrame("default");
		String AlertText = "";

		try {
			AlertText = driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "htmlDiv_alertMsg;xpath")))
					.getText();
			if (!AlertText.equals("")) {
				
				if(AlertText.contains("errors"))
				{
					writeExtent("Fail", "'"+fileName+"'"+" Message not processed on "+ScreenName+ " Screen");
					Assert.assertFalse(true, "Message not processed on "+ScreenName);
				}
				else
				{
				switch (alertOps.valueOf(alertOps)) {
				case "getText":
					setPropertyValue("AlertText", AlertText, proppath);
					break;

				case "Accept":
					driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
					if(AlertText.contains("Received"))
					{
						
						writeExtent("Info", "'"+fileName+"'"+" Message  processed with status "+AlertText + " on " + ScreenName + " Screen");
					}
					else
					{
						writeExtent("Pass", "'"+fileName+"'"+" Message processed on "+ScreenName+ " Screen");
					}

					break;
				case "Dismiss":
					driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_no;xpath"))).click();
					writeExtent("Pass", "Dismissed Alert with text " + AlertText + " on " + ScreenName + " Screen");
					break;
				case "Close":
					driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_close;xpath"))).click();
					writeExtent("Pass", "Closed Alert with text " + AlertText + " on " + ScreenName + " Screen");
					break;
				case "GetTextAndClose":
					setPropertyValue("AlertText", AlertText, proppath);
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_closePopUp;xpath"))).click();
				writeExtent("Pass", "Closed Alert with text " + AlertText + " on " + ScreenName + " Screen");
				break;
				}
				}

			}
			
			return true;
		} catch (Exception e) {
	
			//writeExtent("Info", "Failed to handle Alert with text " + AlertText + " On " + ScreenName + " Screen");
				return false;

		}
	}
	/**
     * Description... Load From File
     * 
      * @param ParticipantType
     * @param TransmissionMode
     * @param InterfaceSystem
     * @param StationName
     * @param FileListener
     * @param FilePath
     * @throws Exception
     */
     public void loadFromFile(String ParticipantType, String Participant,String TransmissionMode,
                 String InterfaceSystem, String StationName, String FileListener,
                 String FileName,boolean isXml) throws Exception {

           clickButtonSwitchWindow(sheetName, "btn_LoadFromFile;name", screenName,
                       "Load From File Button");
           selectValueInDropdown(sheetName, "lst_participantType;name",
                       ParticipantType, "Participant Type", "VisibleText");
           
           if(!ParticipantType.equals("All"))
           {
                 enterValueInTextbox(sheetName, "inbx_participant;name", data(Participant),
                             "Participant", "Load From File Pop up");
           }
           selectValueInDropdown(sheetName, "lst_transmissionMode;name",
                       TransmissionMode, "Transmission Mode", "VisibleText");
           
           waitForSync(2);
     
           enterValueInTextbox(sheetName, "inbx_stationCode;name", data(StationName),
                       "StationName", "Load From File Pop up");
           /**selectValueInDropdown(sheetName, "lst_interfaceSystem;name",
                       InterfaceSystem, "Interface System", "VisibleText");**/
           clickButtonSwitchToSecondWindow(sheetName, "btn_Address;id",
                       "Load From File Pop up", "Address Button");
           
           

           /****enterValueInTextbox(sheetName, "inbx_fileListener;name", FileListener,
                       "File Listener", "Message Address Details Pop up");***/
           
           

           clickWebElement(sheetName, "btn_ok;name", "OK Button",
                       "Message Address Details Pop up");
           
           switchToWindow("getFirstChild");

           try {
                 if(isXml)
                 {
                    //check  xml checkbox
                	 clickWebElement(sheetName, "btn_chkXML;id", "Check XML Button",
                             "Check XML");
                  
           driver.findElement(By.name("theFile")).sendKeys(message_files+FileName+".xml");
                 }
                 else
                 {
                 driver.findElement(By.name("theFile")).sendKeys(message_files+FileName+".txt");
                 }
           } catch (Exception e) {
                 e.printStackTrace();
           }
           clickWebElement(sheetName, "btnLoad;name", "Load Button",
                       "Load From File Pop up");
           handleAlert("Accept", "List Messages",FileName);

           clickWebElement(sheetName, "btn_viewMsg_Close;xpath", "Close Button",
                       "Load From File Pop up");

           switchToWindow("getParent");
           switchToDefaultAndContentFrame(screenId);
     }




	/**
	 * Description... Replace AWB No
	 * 
	 * @param filePath
	 * @param oldString
	 * @param newString
	 * @throws Exception
	 */
	public void replaceAWBNo(String filePath, String oldString, String newString)
			throws Exception {
		/*
		 * File fileToBeModified = new File(filePath);
		 * 
		 * String oldContent = "";
		 * 
		 * BufferedReader reader = null;
		 * 
		 * FileWriter writer = null;
		 * 
		 * try { reader = new BufferedReader(new FileReader(fileToBeModified));
		 * 
		 * //Reading all the lines of input text file into oldContent
		 * 
		 * String line = reader.readLine();
		 * 
		 * while (line != null) { oldContent = oldContent + line +
		 * System.lineSeparator();
		 * 
		 * line = reader.readLine(); }
		 * 
		 * //Replacing oldString with newString in the oldContent
		 * 
		 * String newContent = oldContent.replaceAll(oldString, newString);
		 * 
		 * //Rewriting the input text file with newContent
		 * 
		 * writer = new FileWriter(fileToBeModified);
		 * 
		 * writer.write(newContent); } catch (IOException e) {
		 * e.printStackTrace(); } finally { try { //Closing the resources
		 * 
		 * reader.close();
		 * 
		 * writer.close(); } catch (IOException e) { e.printStackTrace(); } }
		 */
	}

	/**
	 * Description... Set XML Node Value
	 * 
	 * @param filePath
	 * @param fullAWBNo
	 * @throws Exception
	 */
	public void setXMLNodeValue(String filePath, String fullAWBNo)
			throws Exception {
		try {

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(new File(filePath));

			XPath xPath = XPathFactory.newInstance().newXPath();
			Node startDateNode = (Node) xPath.compile(
					"//TransportContractDocument//ID").evaluate(doc,
					XPathConstants.NODE);
			startDateNode.setTextContent(fullAWBNo);

			Transformer tf = TransformerFactory.newInstance().newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty(OutputKeys.METHOD, "xml");
			tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
					"4");

			DOMSource domSource = new DOMSource(doc);
			StreamResult sr = new StreamResult(new File(filePath));
			tf.transform(domSource, sr);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Description... Verify Message Processed Successfully
	 * 
	 * @param noOfSentMsgBeforeOp
	 * @param noOfSentMsgAfterOp
	 * @param opName
	 * @param msgType
	 */
	public void verifyMessageProcessedSuccessfully(int noOfSentMsgBeforeOp,
			int noOfSentMsgAfterOp, String opName, String msgType) {

		if (noOfSentMsgAfterOp > noOfSentMsgBeforeOp)
			verifyValueOnPage(true, true, "Verify " + msgType
					+ " Message triggered after " + opName, screenName, opName);
		else
			verifyValueOnPage(true, false, "Verify " + msgType
					+ " Message triggered after " + opName, screenName, opName);

	}

	/**
	 * Description... Verify FHL Message Content
	 * 
	 * @param MessageContent
	 * @param MessageContent1
	 * @param MessageContent2
	 * @param MessageContent3
	 * @param MessageContent4
	 * @param MessageContent5
	 * @param MessageContent6
	 * @param MessageContent7
	 * @param MessageContent8
	 * @param MessageContent9
	 * @param MessageContent10
	 * @param MessageContent11
	 * @throws Exception
	 */
	public void verifyFHLMessageContent(String MessageContent,
			String MessageContent1, String MessageContent2,
			String MessageContent3, String MessageContent4,
			String MessageContent5, String MessageContent6,
			String MessageContent7, String MessageContent8,
			String MessageContent9, String MessageContent10,
			String MessageContent11) throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(3);
		try {
			ele = findDynamicXpathElement("txtarea_RawMsg;xpath", sheetName,
					"Message Content", screenName);
			String actText = ele.getText().trim();
			String expText = data(MessageContent).trim();
			verifyScreenText(sheetName, expText, actText,
					"Message Verification", screenName);
			String expText1 = data(MessageContent1).trim();
			verifyScreenText(sheetName, expText1, actText,
					"Message Verification", screenName);
			String expText2 = data(MessageContent2).trim();
			verifyScreenText(sheetName, expText2, actText,
					"Message Verification", screenName);
			String expText3 = data(MessageContent3).trim();
			verifyScreenText(sheetName, expText3, actText,
					"Message Verification", screenName);
			String expText4 = data(MessageContent4).trim();
			verifyScreenText(sheetName, expText4, actText,
					"Message Verification", screenName);
			String expText5 = data(MessageContent5).trim();
			verifyScreenText(sheetName, expText5, actText,
					"Message Verification", screenName);
			String expText6 = data(MessageContent6).trim();
			verifyScreenText(sheetName, expText6, actText,
					"Message Verification", screenName);
			String expText7 = data(MessageContent7).trim();
			verifyScreenText(sheetName, expText7, actText,
					"Message Verification", screenName);
			String expText8 = data(MessageContent8).trim();
			verifyScreenText(sheetName, expText8, actText,
					"Message Verification", screenName);
			String expText9 = data(MessageContent9).trim();
			verifyScreenText(sheetName, expText9, actText,
					"Message Verification", screenName);
			String expText10 = data(MessageContent10).trim();
			verifyScreenText(sheetName, expText10, actText,
					"Message Verification", screenName);
			String expText11 = data(MessageContent11).trim();
			verifyScreenText(sheetName, expText11, actText,
					"Message Verification", screenName);
			waitForSync(2);
		} catch (Exception e) {
			System.out.println("Failed in FHL verification");
			writeExtent("Fail", "Failed in FHL verification On " + screenName
					+ " Page");
			Assert.assertFalse(true, "Failed in FHL verification On "
					+ screenName + " Page");
		}
	}

	/**
	 * Description... Count the number of processed successfully messages on
	 * List Message screen
	 * 
	 * @return
	 */
	public int countNoOfProcessedSuccessfullyMessage() {
		return returnListSize(returnListOfElements(sheetName,
				"txt_ProcessedSuccessfully;xpath"));
	}

	/**
	 * Description... Verify FHL Trimming
	 * 
	 * @param expectedText
	 * @param actualText
	 * @param value
	 * @throws Exception
	 */
	public void verifyFHLTrimming(String expectedText, String actualText,
			int value) throws Exception {

		String actText = actualText.trim();
		String expText = data(expectedText).trim();

		System.out.println("str is--" + actText);

		String splitStr[] = actText.split("\n");

		for (String s : splitStr) {

			System.out.println("s is--" + s);
			if (s.contains(expText)) {
				String splitStrShipper[] = s.split("/");
				System.out.println("shipper line is--" + splitStrShipper[1]);
				System.out.println("Length is--" + splitStrShipper[1].length());
				if ((splitStrShipper[1].length()) == value)

					writeExtent("Pass", "Accounting Info ContentCode  matching");

				else
					writeExtent("Fail",
							"Accounting Info ContentCode not matching");

			}

		}

	}

	/**
	 * Description... Verify Message Count
	 * 
	 * @param actualCount
	 * @param expectedCount
	 * @param opName
	 * @param msgType
	 */
	public void verifyMessageCount(int actualCount, int expectedCount,
			String opName, String msgType) {

		if (actualCount == expectedCount)
			verifyValueOnPage(true, true, "Verify " + msgType + " Count "
					+ opName, screenName, opName);
		else
			verifyValueOnPage(true, false, "Verify " + msgType + " Count "
					+ opName, screenName, opName);

	}

	/**
	 * Description... Verify Exclusive Usage Indicator
	 * 
	 * @throws Exception
	 */
	public void verifyExclusiveUsageIndicator() throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(3);
		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String ExclusiveUsageIndicator = "<ExclusiveUsageIndicator>"
				+ data("ExclusiveUsageIndicator")
				+ "</ExclusiveUsageIndicator>";
		verifyScreenText(sheetName, ExclusiveUsageIndicator,
				actMessageContentXML, "Message Verification", screenName);

	}

	/**
	 * Description... Enter Shipper Reference
	 * 
	 * @param ShipperRef
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterShipperRef(String ShipperRef) throws InterruptedException,
			AWTException {
		enterValueInTextbox(sheetName, "inbx_shipperRefXSDG;xpath", ShipperRef,
				"Shipper Reference", screenName);

	}

	/**
	 * Description... Verify trimmimg FHL
	 * 
	 * @param expectedText
	 * @param actualText
	 * @param value
	 * @param number
	 * @throws Exception
	 */
	public void verifytrimmimgFHL(String expectedText, String actualText,
			int value, int number) throws Exception {

		try {
			String actText = actualText.trim();
			String expText = data(expectedText).trim();

			System.out.println("str is--" + actText);

			String splitStr[] = actText.split("\n");

			for (String s : splitStr) {

				System.out.println("s is--" + s);
				if (s.contains(expText)) {
					String splitStrShipper[] = s.split("/");
					System.out.println("shipper line is--"
							+ splitStrShipper[number]);
					System.out.println("Length is--"
							+ splitStrShipper[number].length());
					if ((splitStrShipper[number].length()) == value)

						writeExtent("Pass", "Trimmins Success");

					else
						writeExtent("Fail", "Trimmins Success");

				}

			}

		} catch (Exception e) {
			System.out.println("Trimming Success" + " On " + screenName
					+ " Page");
			writeExtent("Fail", "Failed in verifying Trimming" + " On "
					+ screenName + " Page");
			Assert.assertFalse(true, "Failed in verifying Trimming" + " On "
					+ screenName + " Page");
		}
	}

	/**
	 * Description... Verify Sender Recipient1
	 * 
	 * @param Sender
	 * @param msgType
	 * @throws Exception
	 */
	public void verifySenderRecipient1(String Sender, String msgType)
			throws Exception {
		String actMsgStatus = getElementText("ListMessages_MSG005",
				"txt_messageSenderRecipient1;xpath",
				"Message Sender/Recipient  for House 1", screenName);
		verifyValueOnPage(actMsgStatus, data(Sender), "Verify " + msgType
				+ " Message Sender/Recipient  for House 1",

		screenName, "Message Sender/Recipient  for House 1");

	}

	/**
	 * Description... Verify Sender Recipient2
	 * 
	 * @param Sender
	 * @param msgType
	 * @throws Exception
	 */
	public void verifySenderRecipient2(String Sender, String msgType)
			throws Exception {
		String actMsgStatus = getElementText("ListMessages_MSG005",
				"txt_messageSenderRecipient2;xpath",
				"Message Sender/Recipient for House 2", screenName);
		verifyValueOnPage(actMsgStatus, data(Sender), "Verify " + msgType
				+ " Message Sender/Recipient  for House 2",

		screenName, "Message Sender/Recipient  for House 2");

	}

	/**
	 * Description... Verify Sender Recipient2 FHL
	 * 
	 * @param Sender
	 * @param msgType
	 * @throws Exception
	 */
	public void verifySenderRecipient2FHL(String Sender, String msgType)
			throws Exception {
		String actMsgStatus = getElementText("ListMessages_MSG005",
				"txt_messageSenderRecipient2FHL;xpath",
				"Message Sender/Recipient for House 2", screenName);
		verifyValueOnPage(actMsgStatus, data(Sender), "Verify " + msgType
				+ " Message Sender/Recipient  for House 2",

		screenName, "Message Sender/Recipient  for House 2");

	}

	/**
	 * Description... Verify Message Status 2
	 * 
	 * @param expectedMsgStatus
	 * @param msgType
	 * @throws InterruptedException
	 */
	public void verifyMessageStatus2(String expectedMsgStatus, String msgType)
			throws InterruptedException {

		String actMsgStatus = getElementText("ListMessages_MSG005",
				"txt_messageSent2;xpath", "Message Status", screenName);
		verifyValueOnPage(actMsgStatus, expectedMsgStatus, "Verify " + msgType
				+ " Message status",

		screenName, "Message Status");

	}

	/**
	 * Description... Verify Exclusive Usage Indicator
	 * 
	 * @param Exclusiveusageindicator
	 * @throws Exception
	 */
	public void verifyExclusiveUsageIndicator(String Exclusiveusageindicator)
			throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");
		switchToFrame("default");
		waitForSync(3);
		String actMessageContentXML = getElementText("ListMessages_MSG005",
				"txt_OrigFSUMsg;xpath", "Message Text", "List Messages");
		String ExclusiveUsageIndicator = "<ExclusiveUsageIndicator>"
				+ Exclusiveusageindicator + "</ExclusiveUsageIndicator>";
		verifyScreenText(sheetName, ExclusiveUsageIndicator,
				actMessageContentXML, "Message Verification", screenName);

	}

	/**
	 * Description... Verify View Message Content JavaScript
	 * 
	 * @param val
	 * @param valName
	 * @throws Exception
	 */
	public void verifyViewMessageContentJavaScript(String val[],
			String valName[]) throws Exception {
		switchToWindow("storeParent");
		switchToWindow("child");

		WebElement ele1 = findDynamicXpathElement("tab_allText_view;xpath",
				sheetName, "Handling code", screenName);
		waitForSync(2);

		ele1.click();
		waitForSync(2);
		keyPress("SCROLLDOWNMOUSE");
		keyPress("SCROLLDOWNMOUSE");
		keyPress("SCROLLDOWNMOUSE");
		keyPress("SCROLLDOWNMOUSE");
		waitForSync(2);
		try {
			String actText = ele1.getText().replaceAll(" ", "").trim();
			for (int i = 0; i < val.length; i++) {
				String expText = val[i].replaceAll(" ", "").trim();

				verifyScreenText(sheetName, expText, actText, valName[i],
						screenName);

			}

		} catch (Exception e) {
			System.out
					.println("Failed in verifying the Message content in View Tab"
							+ " On " + screenName + " Page");
			writeExtent("Fail",
					"Failed in verifying the Message content in View Tab"
							+ " On " + screenName + " Page");
			Assert.assertFalse(true,
					"Failed in verifying the Message content in View Tab"
							+ " On " + screenName + " Page");
		}
	}

	public void setXMLNodeValueForMultipleNodes(String filePath,
			String[] nodes, String[] values) {
		try {

			DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
			DocumentBuilder b = f.newDocumentBuilder();
			Document doc = b.parse(new File(filePath));
			for (int i = 0; i < nodes.length; i++) {
				XPath xPath = XPathFactory.newInstance().newXPath();
				Node startDateNode = (Node) xPath.compile("//" + nodes[i] + "")
						.evaluate(doc, XPathConstants.NODE);
				startDateNode.setTextContent(values[i]);
				Transformer tf = TransformerFactory.newInstance()
						.newTransformer();
				tf.setOutputProperty(OutputKeys.INDENT, "yes");
				tf.setOutputProperty(OutputKeys.METHOD, "xml");
				tf.setOutputProperty(
						"{http://xml.apache.org/xslt}indent-amount", "4");
				DOMSource domSource = new DOMSource(doc);
				StreamResult sr = new StreamResult(new File(filePath));
				tf.transform(domSource, sr);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public String createMessageID() {
		Random ran = new Random();
		int m = ran.nextInt(9000000) + 1000000;
		String messageID = String.valueOf(m);
		return messageID;
	}

	public List getValueFromXML(String uldMsg, String[] node) {
		List<String> expectedValue = new ArrayList<String>();
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			InputSource src = new InputSource();
			src.setCharacterStream(new StringReader(uldMsg));
			Document doc = builder.parse(src);
			for (int i = 0; i < node.length; i++) {
				String temp = doc.getElementsByTagName("ns2:" + node[i] + "")
						.item(0).getTextContent();
				expectedValue.add(temp);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return expectedValue;
	}

	public void verifyXMLValues(List<String> expectedxmlValues, String[] actual) {
		List<String> actualValues = Arrays.asList(actual);
		for (int i = 0; i < expectedxmlValues.size(); i++) {
			if (expectedxmlValues.get(i).contains(actual[i])) {
				onPassUpdate(screenName, expectedxmlValues.get(i), actual[i],
						"ULDACTWGT message", "Message value verification");
			}

			else {
				onFailUpdate(screenName, expectedxmlValues.get(i), actual[i],
						"ULDACTWGT message", "Message value verification");
			}

		}

	}

	public void verifyMsgStatus(String status, String msgType)
			throws InterruptedException {
		String actMsgStatus = getElementText("ListMessages_MSG005",
				"inbx_msgStatus;xpath", "Message Status", screenName);
		verifyValueOnPage(actMsgStatus, status, "Verify " + msgType
				+ " Message status", screenName, "Message Status");

	}



/**A-8705
 * Verifies Msg
 * @param msg
 * @throws Exception 
 */
	public void verifyMsg(String msg) throws Exception {
		clickWebElement("Generic_Elements", "btn_view;name", "View Button",
				screenName);
		waitForSync(4);
		driver.switchTo().frame("popupContainerFrame");
		String actMsgStatus = getElementText("ListMessages_MSG005",
				"txtarea_RawMsg;xpath", "Message content", screenName);
		System.out.println(actMsgStatus);
		System.out.println(data(msg));
		if (actMsgStatus.contains(data(msg))) {
			onPassUpdate(screenName, actMsgStatus, data(msg),
					"Msg content", "Msg Content");
		}
		else {
			onFailUpdate(screenName, actMsgStatus, data(msg),
					"Error Description", "Error Description");
		}	
		clickWebElement("Generic_Elements", "butn_close;name", "Close Button",
				"Message View Button Pop up");
		
	}

	public void verifyReasonForFailure(String reason, String msgType)
			throws InterruptedException {
		String actMsgStatus = getElementText("ListMessages_MSG005",
				"inbx_errorDescription;xpath", "Error Description", screenName);
		if (actMsgStatus.contains(reason)) {
			onPassUpdate(screenName, actMsgStatus, actMsgStatus,
					"Error Description", "Error Description");
		}

		else {
			onFailUpdate(screenName, actMsgStatus, actMsgStatus,
					"Error Description", "Error Description");
		}


	}

}
