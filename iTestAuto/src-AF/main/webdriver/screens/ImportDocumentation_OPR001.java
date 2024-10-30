package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;




import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ImportDocumentation_OPR001 extends CustomFunctions {
	
	String sheetName = "ImportDocumentation_OPR001";
	String sheetName1 = "ImportManifest_OPR367";
	String screenName = "Import Documentation: OPR001";
	String screenId="OPR001";
	

	public ImportDocumentation_OPR001(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	

	public void verifyDocumentDetails(int verfCols[], String actVerfValues[],int verfCols1[], String actVerfValues1[]) throws Exception {

		verify_tbl_records_multiple_cols(sheetName, "tbl_arrival;xpath", "//td", verfCols, data("AWBNo"),
				actVerfValues);
		verify_tbl_records_multiple_cols_RampHandle(sheetName, "tbl_arrival;xpath", verfCols1, data("AWBNo"),
				actVerfValues1);	
		waitForSync(3);

	}
	/**
	 * @Desc : Click Update Prenomination Button
	 * @author A-9175
	 * @throws Exception
 */

	public void clickUpdatePrenominationBtn() throws Exception {
		clickWebElement(sheetName, "btn_updatePreNomination;id", "Update Prenomination Button", screenName);
		waitForSync(2);
	}
	/**
	 * @Desc: clickScribblePad
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickScribblePad() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_scribblepad;xpath", " Scribble Pad ", screenName);
		waitTillScreenload(sheetName, "txt_scribbleTextCaptured;xpath", "Scribble Remarks", screenName);
	}

	/**
	 * @Desc : enterScribbleText
	 * @author A-9175
 * @param scribbleText
	 * @throws Exception
	 */
	public void enterScribbleText(String scribbleText) throws Exception {
		try {
			enterValueInTextbox(sheetName, "txt_flightScribbleTxt;id", scribbleText, "Scribble Text", screenName);
			clickWebElement(sheetName, "btn_List;xpath", "Ok Button", screenName);
			waitTillScreenload(sheetName, "txt_scribbleTextCaptured;xpath", "Scribble Text code", screenName);
			writeExtent("Pass",
					"Sucessfully Captured Scribble Information as " + scribbleText + " On " + screenName + " Page");
		} catch (Exception e) {
			writeExtent("Fail", "Could not Capture Scribble Information " + " On " + screenName + " Page");
		}

	}

	/**
	 * @Desc : Update Clearing Agent Details
	 * @author A-9175
	 * @param ConsigneeCode
	 * @throws Exception
	 */
	public void updateClearingAgentDetails(String ConsigneeCode) throws Exception 
	{
		switchToWindow("storeParent");
		switchToWindow("multipleWindows");
		String clearingAgent = data(ConsigneeCode);
		enterValueInTextboxByJS(sheetName, "txt_clearingAgentval;id", clearingAgent, "Clearing Agent",screenName);
		clickWebElement(sheetName, "btn_clearingAgent;id", "Save Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR001");
		waitForSync(2);
	}

	/**
	 * @author A-9847
	 * @Desc To verify the Check sheet Status Column based on img src as "finished-indicator"/"blocker"/"warning"
	 * @param awb
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyYellowIndicator(String awb,String imgsrc, boolean value) throws InterruptedException, IOException{

		try
		{
			String dynXpath=xls_Read.getCellValue(sheetName, "btn_yellowIndicator;xpath");
			dynXpath=dynXpath.replace("*", data(awb));
			dynXpath=dynXpath.replace("h1", imgsrc);

			waitForSync(3);
			if(value)
			{
				/******* Verifying the yellow indicator status column  ***/


				try
				{

					if(driver.findElements(By.xpath(dynXpath)).size()==1)

						writeExtent("Pass","Yellow indicator status column verified as  houselevel info for the shipment "+data(awb)+" on "+screenName);
					else
						writeExtent("Fail","Yellow indicator status column is not verified as house level info for the shipment "+data(awb)+" on "+screenName);

				}
				catch(Exception e)
				{
					writeExtent("Fail","Yellow indicator status column is not verified for the shipment "+data(awb)+" on "+screenName);
				}
			}
			else
			{
				try
				{

					if(!(driver.findElements(By.xpath(dynXpath)).size()==1))

						writeExtent("Pass","No Yellow indicator status column is verified as house level info for the shipment "+data(awb)+" on "+screenName);
					else
						writeExtent("Fail","Yellow indicator status column  present as  house level info for the shipment "+data(awb)+" on "+screenName);

				}
				catch(Exception e)
				{
					writeExtent("Fail","No yellow indicator status column not verified for "+data(awb)+" on "+screenName);
				}
			}



		}

catch(Exception e)
{
	writeExtent("Fail","Could not retreive yellow indicator status column details on "+screenName+" for "+data(awb));
}

}


	/**
	 * @author A-10690
	 * @Description : Click Yes on ALert
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void ClickYesAlert() throws InterruptedException, AWTException {
		waitForSync(5);
		switchToFrame("default");
		try {
			while (driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath")))
					.isDisplayed()) {
				handleAlert("Accept", screenName);
				waitForSync(5);
			}
		} catch (Exception e) {
		}

		finally {
			switchToFrame("contentFrame", "OPR001");
		}
	}
	/**
	 * 
	 * @param handoverTo
	 * @param pigeonhole
	 * @throws Exception
	 * Desc : CaptureHandoverDetailsAndverifyPopup
	 */
	public void CaptureHandoverDetailsAndverifyPopup(String handoverTo,String pigeonhole) throws Exception {
		waitForSync(5);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
		String locator = xls_Read.getCellValue(sheetName, "htmlDiv_displaypopup;xpath");
		WebElement elem=driver.findElement(By.xpath(locator));
		if(elem.isDisplayed())
		{
			writeExtent("Pass","successfully verifed capture handover popup");
		}
		else
		{
			writeExtent("Fail","capture handover popup is not displayed");
		}
	String locator2=xls_Read.getCellValue(sheetName, "inbx_handoverTo;id");
		
		WebElement elem2=driver.findElement(By.id(locator2));
		if(elem2.isEnabled())
		{
		enterValueInTextbox(sheetName,"inbx_handoverTo;id",data(handoverTo), handoverTo , screenName);
		waitForSync(2);
		}
		//pigeonhole number
		String exppigeonhole= driver.findElement(By.id(xls_Read.getCellValue(sheetName, "txt_pigeonhole;id"))).getAttribute("value");
		if(exppigeonhole.equals(""))
		enterValueInTextbox(sheetName, "txt_pigeonhole;id", data(pigeonhole), "Pigeon Hole", screenName);
		clickWebElement(sheetName, "btn_handoverSave;id", "Capture Handover Save", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR001");
	}
	/**
	 * @author A-8783
	 * Description... Verify AWB Documents Received Check Box is unchecked
	 * @param AWBNo
	 * @throws InterruptedException
	 */
	public void checkAWBDocumentRcvdNotChecked(String AWBNo) throws InterruptedException{

		String xpath=xls_Read.getCellValue(sheetName, "chk_awbDocumentsRcvd;xpath").replace("AWBNo", data(AWBNo));	
		try{
		if(!driver.findElement(By.xpath(xpath)).isSelected())
			onPassUpdate(screenName, "AWB Document Received Unchecked", "AWB Document Received Unchecked", "Checkbox Unchecked", "checkbox");
		
		else
			onFailUpdate("AWB Document Received checkbox is checked on "+screenName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
	        writeExtent("Fail", "Could not verify document received checkbox is unchecked on " + screenName + " Page");
		}
		
	}


	/**
	 * @author A-9844
	 * @param prefix
	 * @param awb
	 * @throws Exception
	 * Desc : List AWB details
	 */
	 public void listAWBDetails(String awbPrefix,String awbNo)throws Exception{
		 enterValueInTextbox(sheetName, "inbx_awbPrefix;xpath", data(awbPrefix),"awb prefix", screenName);
	        enterValueInTextbox(sheetName,"inbx_awbNo;xpath",data(awbNo), "AWB Number", screenName);
	        waitForSync(3);
	        clickWebElement(sheetName,"btn_ListAWB;xpath","List AWB Details", screenName);
	        waitForSync(3);
	       
	 }
	 /**
		 * Desc : click on fLAG FLIGHT button
		 * @author A-10690
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void clickFlagButton() throws InterruptedException, IOException{
			waitForSync(2);
			clickWebElement(sheetName, "btn_flagflight;name", " flag flight", screenName);
			waitForSync(2);}  

		/**
		 * @Description : Verifying the warning messages for the awbs having discrepencies while doing flag flight operation
		 * @author A-10690
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void verifyWarningMessageWith2AWBsWithDiscrepenciesAfterFlag(String awb1,String awb2) throws InterruptedException, IOException {
			switchToFrame("default");
			String s1 = "2 AWBs," + data("CarrierNumericCode") + "-" + data(awb1) + ", "
					+ data("CarrierNumericCode") + "-" + data(awb2) + ".";
			String s2 = "2 AWBs," + data("CarrierNumericCode") + "-" + data(awb2) + ", "
					+ data("CarrierNumericCode") + "-" + data(awb1) + ".";
			String locator = xls_Read.getCellValue(sheetName1, "txt_warningmessageDis;xpath");
			String actualtext=driver.findElement(By.xpath(locator)).getText();
			System.out.println(actualtext);
			System.out.println(s1);
			System.out.println(s2);
			if (actualtext.contains(s1) || actualtext.contains(s2)) {
				writeExtent("Pass",
						"Successfully verified warning message as '" +actualtext+ "' on "+ screenName);
			} else {
				writeExtent("Fail",
						"Failed to verify warning message with 2 awbs after clicking breakdown complete" + screenName);
			}
 
			
			clickWebElement(sheetName1, "btn_yes;xpath", "yes Button", screenName);
			waitForSync(3);
		


		}

	/**
	 * @author A-9847
	 * @Desc To Accept the Arrival Checksheet Warning 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void checkSheetWarning() throws InterruptedException, IOException{

			waitForSync(5);
			switchToFrame("default");
			try {
				while (driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath")))
						.isDisplayed()) {
					handleAlert("Accept", screenName);
					waitForSync(5);
				}
			} catch (Exception e) {
			}

			finally {
				switchToFrame("contentFrame", "OPR001");
			}
		}
	/**
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickImportManifest() throws InterruptedException, AWTException, IOException {
		waitForSync(3);
		clickWebElement(sheetName, "btn_importManifest;xpath", " Import Manifest button", screenName);
		waitForSync(3); }


/**
	 * Description... Clicks AWB Number Check Box
	 * @param AWBNo
	 * @throws InterruptedException
	 */
	public void clickAWBNumberCheckBox(String AWBNo) throws InterruptedException{
		waitForSync(5);
		String xpath=xls_Read.getCellValue(sheetName, "chk_awbNo;xpath").replace("AWBNo", AWBNo);
		clickWebElementByActionClass(xpath, "AWB Number Check Box", screenName);
	}
	/**
	 * @author A-9847
	 * @des To capture Handoverdeatils and verify the pigeonhole details
	 * @param handoverTo
	 * @param pigeonHole
	 * @throws Exception
	 */

	/**
	 * @author A-9847
	 * @des To capture Handoverdeatils and pigeonhole details
	 * @param handoverTo
	 * @param pigeonHole
	 * @throws Exception
	 */

	public void captureHandoverDetailsandVerifyPigeonHoleDetails(String handoverTo,String pigeonHole) throws Exception{
		
		waitForSync(5);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_handoverTo;id",data(handoverTo), handoverTo , screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_remarks;id", data("Remarks"), "Remarks", screenName);    
	    
		//pigeonhole number
		String exppigeonhole= driver.findElement(By.id(xls_Read.getCellValue(sheetName, "txt_pigeonhole;id"))).getAttribute("value");
		if(exppigeonhole.equals(""))
		enterValueInTextbox(sheetName, "txt_pigeonhole;id", data(pigeonHole), "Pigeon Hole", screenName);    	
		
		clickWebElement(sheetName, "btn_handoverSave;id", "Capture Handover Save", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR001");
	}

	/**
	 * @author A-7271
	 * @param fwbs
	 * @throws InterruptedException
	 * Verify fwbs
	 */
	 public void verifyFWB(String fwbs) throws InterruptedException
	 {
		 String fwbSent=getElementText(sheetName,"htmlDiv_Fwb;xpath","FWB sent", screenName);
			
	
		 
		 fwbSent=fwbSent.split("FWB Rcvd :")[1].split("FHL Rcvd :")[0].replaceAll(" ", "").replace("\n", "");
		
		if(fwbs.equals(fwbSent))
			
		{
			writeExtent("Pass","XFWB received is : "+fwbSent);
		}
		else
		{
			writeExtent("Fail","XFWB received is : "+fwbSent+" XFWB expected to be received : "+fwbs);
		}
	 }
	/**
	 * @author A-9175
	 * @param carrCode
	 * @param fltNo
	 * @param flightDate
	 * @throws Exception
	 * Desc : List flight details
	 */
	 public void listFlightDetails(String carrCode,String fltNo,String flightDate)throws Exception{
	        enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrCode),"Carrier code", screenName);
	        enterValueInTextbox(sheetName,"inbx_flightNo;xpath",data(fltNo), "Flight Number", screenName);
	        Thread.sleep(1000);
	        enterValueInTextbox(sheetName,"inbx_flightDate;xpath", data(flightDate), "Flight Date", screenName);
	        waitForSync(5);
	        clickWebElement(sheetName,"btn_List;xpath","List Details", screenName);
	        waitForSync(10);
	       
	 }

	/**
	 * Desc : click on Pouch Recieved check box
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickPouchRcvd() throws InterruptedException, IOException{
		waitForSync(2);
		clickWebElement(sheetName, "chkbox_flightPouchRcvd;name", " Flight Pouch Recieved ", screenName);
		waitForSync(2);}  

	/**
	 * Desc : Sending FSU-NFD 
	 * @author A-9175
	 * @throws Exception
	 */
	public void clickFSUNFD() throws Exception{
		
		waitForSync(5);
		clickWebElement(sheetName, "btn_FSUNFD;xpath", "Capture  Save", screenName);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
		clickWebElement(sheetName, "btn_sendMessageOK;id", " ok ", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR001");
		
		waitForSync(3);
	}
	/**
	 * @author A-9844
	 * Description... Verify Clearing Agent Displayed
	 * @param awb,expectedClearingAgent,Org
	 * @throws Exception
	 */
	public void verifyClearingAgentDisplayed(String AWBNo,String expectedClearingAgent,String Org)throws Exception{

		try{
			
			
			clickWebElement(sheetName, "txt_origin;xpath", "Origin", screenName);
			String locator = xls_Read.getCellValue(sheetName, "txt_clearingAgent;xpath");
			locator=locator.replace("awb", data(AWBNo));
			locator=locator.replace("clearingAgent", data(expectedClearingAgent));

			if(driver.findElements(By.xpath(locator)).size()==1){
				writeExtent("Pass", "Verified the clearing agent name displayed as "+data(expectedClearingAgent)+" on "+screenName);
			}
			else
			{
				writeExtent("Fail", "Couldn't verify the clearing agent name displayed on "+screenName);
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Clearing agent name is not displayed on "+screenName);
		}
	}
	/**
	 * Description... Verify the sccs displayed for the shipment
	 * @param awb
	 * @param scc
	 * @throws Exception
	 */
	public void verifySCCs(String awb,String[] scc)throws Exception{
	waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "txt_sccdisplayed;xpath").replace("fullawb", data(awb));	
	    for(int i=0;i<scc.length;i++)
	    {
	    	locator=locator.replace("SCC",scc[i]);
	    
	   
	    if(driver.findElements(By.xpath(locator)).size()==1){
	    	writeExtent("Pass","verified the scc"+scc[i]+"on" +screenName);
	    }
			else
			writeExtent("Fail"," not verified the scc"+scc[i]+"on" +screenName);
	    }
	}
	/**
	 * @author A-7271
	 * @param awbNumber
	 * Desc : verify customer notofication details
	 */
 public void verifyCustomerNotification(String awbNumber)
 {
	 try
	 {
		 // Get the specified row
		 int rowCount=1;
		

		 String xpath=xls_Read.getCellValue(sheetName, "table_awbDetailsNew;xpath");	
		 String xpath1=xls_Read.getCellValue(sheetName, "txt_awbNo;xpath");	
		 List <WebElement> row=driver.findElements(By.xpath(xpath1));

         
		 for(WebElement ele:row)
		 {
			 moveScrollBar(ele);
			 System.out.println(ele);
			 System.out.println(ele.getText());
			 
			 if(ele.getText().contains(data(awbNumber)))
			 {
					
				 break;
			 }
			 System.out.println(ele.getText());
			 rowCount=rowCount+1;
		 }
	
		 /******* Verifying if the customer notification status is stamped***/
			

		 String dynXpath="("+xpath+")["+rowCount+"]//td[26]//img"+"|"+"("+xpath+")["+rowCount+"]//td[27]//img";
		
		
		 try
		 {
			 if(driver.findElements(By.xpath(dynXpath)).size()==1)
			 {
				 writeExtent("Pass","Customer notification is stamped for the shipment "+data(awbNumber)+" on "+screenName);
			 }
			 else
			 {
				 writeExtent("Fail","Customer notification is not stamped for the shipment "+data(awbNumber)+" on "+screenName);
			 }
		 }

		 catch(Exception e)
		 {
			 writeExtent("Fail","Customer notification is not stamped for the shipment "+data(awbNumber)+" on "+screenName);
		 }
	 }

	 catch(Exception e)
	 {
		 writeExtent("Fail","Could not retreive customer notofication details on "+screenName+" for "+data(awbNumber));
	 }


 }
 
 /**
	 * @author A-9847
	 * @Desc To capture the checksheet on Import Documentation Screen
	 * @param chkSheetRequired
	 * @throws Exception
	 */
	
	public void captureCheckSheet(boolean chkSheetRequired) throws Exception{
		
		boolean checkSheetExists=true;
		try
		{
		clickWebElement(sheetName, "btn_checksheet;id", "Check Sheet Button", screenName);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
		List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
		if(questions.size()==0)
			checkSheetExists=false;
	
		for(WebElement ele : questions)
		{
			Select select = new Select(ele);
			select.selectByVisibleText("Yes");
		}
		if(chkSheetRequired)
		{
			if(checkSheetExists)
				writeExtent("Pass","Check sheet details selected on "+screenName);
			else
			writeExtent("Fail","No check sheet details configured on "+screenName);
			
		}

		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
		driver.findElement(By.xpath("//button[@name='btnClose']")).click();
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR001");
		
		if(chkSheetRequired)
		{
			if(checkSheetExists)
			{
				writeExtent("Pass","Check sheet details saved on "+screenName);
			}
		}
	}

	catch(Exception e)
	{
		writeExtent("Fail","Could not save check sheet details on "+screenName);
	}	
		
		}
	/**
	 * To verify the Checksheet Status column is present before the Payment type column
	 * @param columnName
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void verifyChecksheetColumnBeforePaymentType(String[] columnName) throws InterruptedException, Exception{
		int i = 0;
		int index=0;
		try {
			String locator=xls_Read.getCellValue(sheetName,"table_columns,xpath");
			List<WebElement> column = driver.findElements(By.xpath(locator));
			for( i=0;i<columnName.length;i++){
				for(WebElement col:column) {
					String actText = col.getText();
					if(actText.equals(columnName[i])) {			
						writeExtent("Pass", "Verified that the column " + columnName[i] + " is present in the table");
						index = column.indexOf(col);					
						break;
					}
				}
			}
			waitForSync(2);
			String nextColtext=column.get(index+1).getText();
			System.out.println(nextColtext);
			verifyScreenText(sheetName, "Payment Type", nextColtext, "Checksheet Status Column before Payment Type Column",screenName);	
		}	 

		catch(Exception e) {
			writeExtent("Fail", "Failed to verify if columns are present");
		}
	}
	
	/**
	 * @author A-9847
	 * To verify column present in Column Chooser
	 * @param column
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
        public void verifyColumnInColumnChooser(String column) throws InterruptedException, IOException {
		
		clickWebElement(sheetName,"btn_columnchoser;xpath","column choser", screenName);
		String col=xls_Read.getCellValue(sheetName, "btn_selectcolumn;xpath").replace("*",data(column));
		waitForSync(2);
		if(driver.findElements(By.xpath(col)).size()==1)
			writeExtent("Pass", "Verified that the column " + data(column) + " is present in the Column Chooser");
		else
			writeExtent("Fail", "Failed to verify that the column " + data(column) + " is present in the Column Chooser");
		clickWebElement(sheetName,"btn_closecolumnchoser;id"," Close Column Chooser", screenName);
		

	}
	
        /**
    	 * @author A-10690
    	 * @Desc To enter the Obligatory answer of checksheet as YES/NO based on questions
    	 * @param chkSheetRequired
    	 * @param answer
    	 * @throws Exception 
    	 */
    	public void captureCheckSheetForDG(boolean chkSheetRequired,String answer) throws Exception
    	{  

    		boolean checkSheetExists1=true;
    		
    		String station=getLoggedInStation("OPR001");
    		
    		if(station.equals("CDG"))
    		{


    			try
    			{
    				clickWebElement(sheetName, "btn_checkSheetcapture;id", "Checksheet", screenName);
    				switchToWindow("storeParent");
    				waitForSync(2);
    				switchToWindow("child");
    				waitForSync(3);

    				List <WebElement> questions1=driver.findElements(By.xpath("//p[@style='display:inline']"));
    				if(questions1.size()==0)
    				{
    					checkSheetExists1=false;
    				}
    				int i=0;
    				for(WebElement ele : questions1)
    				{
    					System.out.println(ele.getText());
    					if(ele.getText().contains(answer))
    					{	
    						selectValueInDropdownWthXpath("//select[@name='questionwithAnswer["+i+"].templateAnswer']","No", ele.getText(), "VisibleText");
    						i++;
    					}
    					else
    					{
    						selectValueInDropdownWthXpath("//select[@name='questionwithAnswer["+i+"].templateAnswer']","Yes", ele.getText(), "VisibleText");
    						i++;
    					}

    				}
    				if(chkSheetRequired)
    				{
    					if(checkSheetExists1)
    					{
    						writeExtent("Pass","Check sheet details selected on "+screenName);
    					}

    					else
    					{
    						writeExtent("Fail","No check sheet details configured on "+screenName);
    					}
    				}

    				clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
    				switchToFrame("default");
    				clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
    				waitForSync(2);
    				driver.findElement(By.xpath("//button[@name='btnClose']")).click();
    				switchToWindow("getParent");
    				switchToFrame("contentFrame", "OPR001");



    				if(chkSheetRequired)
    				{
    					if(checkSheetExists1)
    					{
    						writeExtent("Pass","Check sheet details saved on "+screenName);
    					}
    				}
    			}
    			catch(Exception e)
    			{
    				writeExtent("Fail","Could not save check sheet details on "+screenName);
    			}
    		}

    	}
     
	
	/**
	 * To capture checksheet with non-obligatory answer
	 * @param chkSheetRequired
	 * @throws Exception
	 */
        public void captureCheckSheetWithNonObligatoryAnswer(boolean chkSheetRequired) throws Exception{
    		
    		boolean checkSheetExists=true;
    		try
    		{
    		clickWebElement(sheetName, "btn_checksheet;id", "Check Sheet Button", screenName);
    		switchToWindow("storeParent");
    		waitForSync(2);
    		switchToWindow("child");
    		waitForSync(2);
    		List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
    		if(questions.size()==0)
    			checkSheetExists=false;
    	
    		for(WebElement ele : questions)
    		{
    			Select select = new Select(ele);
    			select.selectByVisibleText("No");
    		}
    		if(chkSheetRequired)
    		{
    			if(checkSheetExists)
    				writeExtent("Pass","Check sheet details selected on "+screenName);
    			else
    			writeExtent("Fail","No check sheet details configured on "+screenName);
    			
    		}

    		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
    		switchToFrame("default");
    		clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
    		driver.findElement(By.xpath("//button[@name='btnClose']")).click();
    		switchToWindow("getParent");
    		switchToFrame("contentFrame", "OPR001");
    		
    		if(chkSheetRequired)
    		{
    			if(checkSheetExists)
    			{
    				writeExtent("Pass","Check sheet details saved on "+screenName);
    			}
    		}
    	}

    	catch(Exception e)
    	{
    		writeExtent("Fail","Could not save check sheet details on "+screenName);
    	}	
    		
    		}
	

	/**
	 * @author A-9847
	 * @Desc To verify the Check sheet Status Column based on img src as "finished-indicator"/"blocker"/"warning"
	 * @param awb
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyCheckSheetColumnStatus(String awb, String imgsrc) throws InterruptedException, IOException{

		try
		{
			// Get the specified row
			int rowCount=1;
			String xpath=xls_Read.getCellValue(sheetName, "table_awbDetails;xpath");	

			List <WebElement> row=driver.findElements(By.xpath(xpath));       
			for(WebElement ele:row)
			{		
				if(ele.getText().contains(data(awb)))
				{

					break;
				}
				rowCount=rowCount+1;
			}

			/******* Verifying the check sheet Status column  ***/
			String dynXpath="("+xpath+")["+rowCount+"]//td[7]//img[contains(@src,'*')]";
			dynXpath=dynXpath.replace("*", imgsrc);
			waitForSync(3);
			try
			{
				if(driver.findElements(By.xpath(dynXpath)).size()==1)

					writeExtent("Pass","Checksheet Status Column is verified as "+imgsrc+" for the shipment "+data(awb)+" on "+screenName);
				else
					writeExtent("Fail","Checksheet Status Column is not verified as "+imgsrc+" for the shipment "+data(awb)+" on "+screenName);

			}

			catch(Exception e)
			{
				writeExtent("Fail","Checksheet Status Column is not verified for the shipment "+data(awb)+" on "+screenName);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not retreive Check sheet Status column details on "+screenName+" for "+data(awb));
		}

	}

	/**
	 * @author A-9847
	 * @Desc To verify the green tick in Check sheet Status Column
	 * @param awb
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void verifyCheckSheetColumnStatus(String awb) throws InterruptedException, IOException{

		try
		{
			// Get the specified row
			int rowCount=1;
			String xpath=xls_Read.getCellValue(sheetName, "table_awbDetails;xpath");	

			List <WebElement> row=driver.findElements(By.xpath(xpath));       
			for(WebElement ele:row)
			{		
				if(ele.getText().contains(data(awb)))
				{

					break;
				}
				rowCount=rowCount+1;
			}

			/******* Verifying if the check sheet Status column is greenticked***/
			String dynXpath="("+xpath+")["+rowCount+"]//td[7]//img[contains(@src,'finished-indicator')]";			
			try
			{
				if(driver.findElements(By.xpath(dynXpath)).size()==1)
					writeExtent("Pass","Checksheet Status Column is green-ticked for the shipment "+data(awb)+" on "+screenName);
				else
					writeExtent("Fail","Checksheet Status Column is not green-ticked for the shipment "+data(awb)+" on "+screenName);

			}

			catch(Exception e)
			{
				writeExtent("Fail","Checksheet Status Column is not green-ticked for the shipment "+data(awb)+" on "+screenName);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not retreive Check sheet Status column details on "+screenName+" for "+data(awb));
		}

	}

	
	
	/**
	 * @author A-9847
	 * @Desc To click the Second List Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickListTwo() throws InterruptedException, IOException{
		 
		clickWebElement(sheetName,"btn_ListTwo;id","List Button", screenName);
		  Thread.sleep(5000);
		
	}
/**
 * Description... Clicks AWB Document Received CheckBox
 * @param AWBNo
 * @throws InterruptedException
 */
	public void clickAWBDocumentReceived(String AWBNo) throws InterruptedException{
		String xpath1=xls_Read.getCellValue(sheetName, "chk_awbDocumentsRcvdChecked;xpath").replace("AWBNo", AWBNo);
		if(!(driver.findElements(By.xpath(xpath1)).size()==1))
		{
		
		String xpath=xls_Read.getCellValue(sheetName, "chk_awbDocumentsRcvd;xpath").replace("AWBNo", AWBNo);	
		clickWebElementByActionClass(xpath, "AWB Document Received CheckBox", screenName);
		}

	}
	/**
	 * Description... Clicks Arrival Notice Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickArrivalNotice() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_ArrivalNotice;name", "Arrival Notice Button", screenName);
	}
	/**
	 * Description... Clicks IQA Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickIQAButton() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_IQA;name", "IQA Button", screenName);
	}
public void listDetails(String fltNo,String flightDate)throws Exception{
              
              enterValueInTextbox(sheetName,"inbx_flightNo;xpath",fltNo, "Flight Number", screenName);
              waitForSync(1);
              enterValueInTextbox(sheetName,"inbx_flightDate;xpath", flightDate, "Flight Date", screenName);
              keyPress("TAB");
              keyRelease("TAB");
              waitForSync(1);
              clickWebElement(sheetName,"btn_List;xpath","List Details", screenName);
              waitForSync(6);
       }

/**
*
* Desc : Clicking Captue Handover details button
* @author A-9175
* @throws InterruptedException
 * @throws IOException 
*/
public void clickCaptureHandover() throws InterruptedException, IOException{
	waitForSync(3);
	clickWebElement(sheetName, "btn_captureHandover;id", "Capture Handover", screenName);
}

/**
* Desc : Capturing Handover details
* @author A-9175
* @param handoverTo
* @throws Exception
*/
public void captureHandoverDetails(String handoverTo) throws Exception {
	waitForSync(5);
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(2);
	enterValueInTextbox(sheetName,"inbx_handoverTo;id",data(handoverTo), handoverTo , screenName);
	waitForSync(2);
	clickWebElement(sheetName, "btn_handoverSave;id", "Capture Handover Save", screenName);
	switchToWindow("getParent");
	switchToFrame("contentFrame", "OPR001");
}

/**
* Desc : Mousehover Notify button
* @author A-9175
* @throws InterruptedException
*/
public void clickNotify() throws InterruptedException{
	waitForSync(3);
	hover(sheetName, "btn_Notify;xpath");
}

/**
* Desc : Clicking FSU-AWD button and click ok
* @author A-9175
* @throws Exception
*/
public void clickFSUAWD() throws Exception{
	
	waitForSync(5);
    clickWebElement(sheetName, "btn_FSUAWD;xpath", "Sent FSU-AWD", screenName);
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(2);
	clickWebElement(sheetName, "btn_sendMessageOK;id", " ok ", screenName);
	switchToWindow("getParent");
	switchToFrame("contentFrame", "OPR001");
	
	waitForSync(3);
}

/**
 * Description... Verify Shipment Displayed
 * @param awb
 * @throws Exception
 */
public void verifyShipmentDisplayed(String awb)throws Exception{
	  waitForSync(5);
    String expected=awb;
    String actual=driver.findElement(By.xpath("(//*[@class='iCargoLink'])[2]")).getText();
    if(actual.equals(expected)){
           verifyScreenText(sheetName, actual,expected, "Shipment is displayed", screenName);
    }
    else{
           verifyScreenText(sheetName, actual,expected, "Shipment is not displayed", screenName);
    }
}


/**
 * 
 * @throws Exception
 */
       
       public void saveDetails()throws Exception{
    	   clickWebElement(sheetName,"btn_save;id","Save button", screenName);
           ClickYesAlert();

       }

	/**
	 * Description... Checks AWB Documents Received Check Box
	 * @param AWBNo
	 * @throws InterruptedException
	 */
	public void checkAWBDocumentRcvdIsChecked(String AWBNo) throws InterruptedException{
		String ScreenName="ImportDocumentation_OPR001";
		String xpath=xls_Read.getCellValue("ImportDocumentation_OPR001", "chk_awbDocumentsRcvd;xpath").replace("AWBNo", AWBNo);	
		WebElement ele=null;
		String eleName="AWB Documents Received Check Box";
		try{
			ele=driver.findElement(By.xpath(xpath));
		}
		catch(Exception e)
		{
			e.printStackTrace();
	        System.out.println("Could not click on " + eleName + " On " + ScreenName + " Page");
	        writeExtent("Fail", "Could not click on " + eleName + " On " + ScreenName + " Page");
	        Assert.assertFalse(true, "Could not click on " + eleName + " On " + ScreenName + " Page");
		}
		
		
		String actChecked=getAttributeUsingJavascript(ele, "AWB Documents Received Check Box", "Import Documentation", "checked");
		verifyValueOnPage(actChecked, "true", "Verify AWB Documents Received Check Box is checked", "ImportDocumentation_OPR001", "AWB Documents Received Check Box is checked");
	}



/**
	 * Description... Close Flight closed dialog box if found
	 * 
	 * @param AWBNo
	 * @throws InterruptedException
	 */
	public void clickFlightClosedDialogBoxY(String screenName) throws InterruptedException, AWTException {
		// handleAlert("Accept",screenName);

		try {
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_yes;xpath", "Flight Closed Dialog Box", screenName);

		}

		catch (Exception e) {

			System.out.println("No dialog box found");
		}

	}

public void verifyCustomsInformation(String FlightNo, int[] verfCols, String[] actVerfValues,
			String expCustomsStatus) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = (WebElement) js.executeScript("return document.getElementsByTagName('circle')[0]");
		ele.click();
		waitForSync(4);
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_CustomsInformation;xpath", "//td", verfCols, FlightNo,
				actVerfValues);
		String actCustomsStatus = getElementText(sheetName, "txt_CustomsStatus;xpath", "Customs status code",
				screenName);

		if (actCustomsStatus.contains(expCustomsStatus)) {
			System.out.println("found true for " + actCustomsStatus);

			onPassUpdate(screenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		} else {
			onFailUpdate(screenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		}
	}


public void verifyCustomsInformation2(String FlightNo, int[] verfCols, String[] actVerfValues,
		String expCustomsStatus, int[] circleNo) throws Exception {
	JavascriptExecutor js = (JavascriptExecutor) driver;
	
	for(int i : circleNo){
		WebElement ele = (WebElement) js.executeScript("return document.getElementsByTagName('circle')[" + i + "]");
		
		
		
	//WebElement ele = (WebElement) js.executeScript("return document.getElementsByTagName('circle')[1]");
	ele.click();
	waitForSync(4);
	verify_tbl_records_multiple_cols_contains(sheetName, "tbl_CustomsInformation;xpath", "//td", verfCols, FlightNo,
			actVerfValues);
	String actCustomsStatus = getElementText(sheetName, "txt_CustomsStatus;xpath", "Customs status code",
			screenName);

	if (actCustomsStatus.contains(expCustomsStatus)) {
		System.out.println("found true for " + actCustomsStatus);

		onPassUpdate(screenName, expCustomsStatus, actCustomsStatus,
				"Customs status code verification against " + FlightNo, "Customs status code verification");

	} else {
		onFailUpdate(screenName, expCustomsStatus, actCustomsStatus,
				"Customs status code verification against " + FlightNo, "Customs status code verification");

	}
}
}


	
	public void verifyAWBODpair(String AWBNo, int[] verfCols, String[] actVerfValues) throws Exception {
		
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_arrival;xpath", "//input", verfCols, AWBNo,
				actVerfValues);
		
	}
	
	public void verifyShipmentDetails(String AWBNo, int[] verfCols, String[] actVerfValues) throws Exception {
		
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_arrival;xpath", "//td", verfCols, AWBNo,
				actVerfValues);
		
	}

}