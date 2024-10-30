package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import bsh.ParseException;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ImportManifest_OPR367 extends CustomFunctions {

	String sheetName = "ImportManifest_OPR367";
	String sheetName2 = "BreakDown_OPR004";
	String GenericSheet = "Generic_Elements";
	String screenName = "Import Manifest : OPR367";
	String screenId = "OPR367";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String toproppath = "\\src\\resources\\TO.properties";

	public ImportManifest_OPR367(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}
	/**
	 * @Description : click deconsolcheckbox
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void clickDeconsolCheckbox() throws InterruptedException, AWTException, IOException {
		

		waitForSync(5);
		clickWebElement(sheetName, "chk_Deconsol;xpath", "Maximize Shipment Details", screenName);
		performKeyActions(sheetName, "chk_Deconsol;xpath", "TAB", "Decondol checkbox", screenName);
		waitForSync(5);
		

	}

/**
	 * @Desc : Verifying Clearing Agent details against AWB
* @author A-9175
	 * @param AWBNo
	 * @param clearingAgentName
* @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyClearingAgent(String AWBNo,String clearingAgentName) throws InterruptedException, AWTException {

		String locator = xls_Read.getCellValue(sheetName, "lbl_clearingAgentName;xpath");
		locator = locator.replace("AWBNo", data(AWBNo));       
		String actText=driver.findElement(By.xpath(locator)).getText().replaceAll("\\s","");
		System.out.println(actText);
		verifyScreenText(screenName, data(clearingAgentName).replaceAll("\\s",""), actText, " clearing Agent Name ", "Verified Sucessfully");
		waitForSync(2);
	}

	/**
	 * Desc : verify deconsol checkbox is checked
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void verifyDeconsolChecked() throws InterruptedException, AWTException, IOException 

	{

		try{
			String locator=xls_Read.getCellValue(sheetName, "chk_Deconsol;xpath");
			if(driver.findElement(By.xpath(locator)).isSelected())
			{
				writeExtent("Pass", "Verified De-consol checkbox is checked "+ "on "+screenName); 
			}

			else{
				writeExtent("Fail", "De-consol checkbox is not checked "+ "on "+screenName);  
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "De-consol checkbox is not checked "+ "on "+screenName);
		}

	}
	/**
	 * @Description : Verifying Breakdown Instruction Label against specified ULDNumber
	 * @author A-9175
	 * @param breakDownInstructionLabel
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyBreakdownInstructionsTagforULD(String ULD,String breakDownInstructionLabel)
			throws InterruptedException, AWTException {
		String locator = xls_Read.getCellValue(sheetName, "lbl_BreakdownInstructionsTag;xpath");
		locator = locator.replace("ULD", data(ULD));
		String ActualText=driver.findElement(By.xpath(locator)).getText();
		System.out.println(ActualText);
		verifyScreenText(screenName, data(breakDownInstructionLabel), ActualText, " BDN InstructionTag ",
				"BDN InstructionTag Verified Sucessfully");

		waitForSync(2);
	}


	/**
	 * @Description : Capture AWB instructions
	 * @author A-10330
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void captureAWBInstruction(String AWB,String instruction) throws InterruptedException, AWTException {
		waitForSync(2);
		try {
			
			
			By b = getElement(sheetName,"btn_awbinstruction;xpath");
			moveScrollBar(driver.findElement(b));
			clickWebElement(sheetName, "btn_awbinstruction;xpath", "AWB Insruction", screenName);

			waitForSync(2);

			enterValueInTextbox(sheetName, "inbx_awbinstruction;xpath",instruction, "Awb instruction capture ", screenName);
			waitForSync(2);

			clickWebElement(sheetName, "btn_addawbinstr;xpath", "Add Button", screenName);
			writeExtent("Pass", "Entered AWB Instruction for" +AWB+""+ screenName + " Page");

		} catch (Exception e) {
			writeExtent("Fail", "Not Entered AWB  Instruction for "+AWB+" " + screenName + " Page");
		}
		waitForSync(2);

	}


	/**
	 * @Description : click UldInstruction
	 * @author A-9175
	 * @param uld
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickUldInstruction(String uld) throws InterruptedException, AWTException, IOException {
		waitForSync(3);
		try {
			String locator = xls_Read.getCellValue(sheetName, "btn_uldInstructions;xpath");
			locator = locator.replace("UldNumber", data(uld));
			driver.findElement(By.xpath(locator)).click();
			waitForSync(2);
			writeExtent("Pass", "Clicked on ULD Instruction " + screenName + " Page");
		} catch (Exception e) {
			writeExtent("Fail", "Not Clicked on ULD Instruction" + screenName + " Page");
		}
	}
	
	/**
	* Desc : click relist button
	* @author A-10328
	* @throws InterruptedException
	* @throws AWTException
     */
	public void relist() throws InterruptedException, AWTException {

	clickWebElementByWebDriver(sheetName, "btn_relist;xpath", "Relist Button", screenName);
	waitForSync(3);
	clickWebElementByWebDriver(sheetName, "btn_list;xpath", "List Button", screenName);
	waitForSync(5);

	}

	/**
	 * @Description : capture UldInstruction
	 * @author A-9175
	 * @param uld
	 * @throws InterruptedException
 * @throws AWTException
	 * @throws IOException
	 */
	public void captureUldInstruction(String uld,String instruction) throws InterruptedException, AWTException {
		waitForSync(2);
		try {
			String locator = xls_Read.getCellValue(sheetName, "txt_uldinstruction;xpath");
			locator = locator.replace("UldNumber", data(uld));
			driver.findElement(By.xpath(locator)).sendKeys(data(instruction));
			waitForSync(2);
			writeExtent("Pass", "Entered ULD Instruction " + screenName + " Page");
			clickWebElement(sheetName, "btn_adduldInstruction;xpath", "Add Button", screenName);
		} catch (Exception e) {
			writeExtent("Fail", "Not Entered ULD Instruction" + screenName + " Page");
		}
		waitForSync(2);

	}

	/**
	 * @Description : check AWB Document Received checkbox if it is not selected
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void checkAWBDocReceived(String awb) throws InterruptedException, AWTException, IOException {
		try{
			
		
		String AWBreceivedlocator = xls_Read.getCellValue(sheetName, "chk_AWBreceived;xpath");
		AWBreceivedlocator = AWBreceivedlocator.replace("*", data(awb));
		moveScrollBar(driver.findElement(By.xpath(AWBreceivedlocator)));
		waitForSync(3);

		if(!driver.findElement(By.xpath(AWBreceivedlocator)).isSelected())
		{
			driver.findElement(By.xpath(AWBreceivedlocator)).click();
			waitForSync(3);
		}
		}
		catch (Exception e) {
			writeExtent("Fail", "Failed to check AWB Document Received check box on"+screenName);
		}
	}

	/**
	 * @Description : Verify intact chcekbox is selected or not
	 * @author A-10690
	 * @param boolean value
	 * @throws InterruptedException
	 * @throws AWTException
	
	 */
	public void verifyIntactCheck(Boolean intactselection) throws InterruptedException, AWTException {
		
		String locator = xls_Read.getCellValue(sheetName, "chk_Intactcheck;xpath");
		if(intactselection)
		{
			if (driver.findElements(By.xpath(locator)).size()==1)
			{
				writeExtent("Pass", "Verified that intact is selected for the ULD on "+screenName); 
			}
			else
			{
				writeExtent("Fail", "Intact is not selected for the  uld on  "+screenName); 
			}
				
		}
		else
		{
			String locator1 = xls_Read.getCellValue(sheetName, "chk_Intact;xpath");
			if ((driver.findElement(By.xpath(locator1)).getAttribute("checked") == null)) {
				
				writeExtent("Pass", "Verified that intact is not selected for the ULD on "+screenName); 
			}
			else
			{
				writeExtent("Fail", "Intact is  selected for the  uld on  "+screenName); 
			}
					
		}
	}

	/**
	 * @Description : verify breakdown image
	 * @author A-10330
	 * @throws InterruptedException
	 *@param: expcolour,status,pmkey
	 */
public void verifyBreakdownImageForMultipleUlds(String expcolour,String status,String pmkey) throws InterruptedException {
		String actColor="";
		String locator = xls_Read.getCellValue(sheetName, "span_imgBDN;xpath");
		locator=locator.replace("*", pmkey);
		actColor=driver.findElement(By.xpath(locator)).getAttribute("class");
		   System.out.println(actColor);
		
		switch (status){

		case "Completed" :
			if (actColor.contains("green"))
			{
				writeExtent("Pass", "Verified that the breakdown successfully completed for  "+pmkey+"uld on "+screenName); 
			}
			else
			{
	writeExtent("Fail", "breakdown is not completed for "+pmkey+" uld on  "+screenName); 
				
			}

			break;

		case "notCompleted" :
			if (actColor.contains("red"))
			{
				writeExtent("Pass", "Verified that the breakdown is not completed for"+pmkey+"uld on"+screenName);
			}
			else 
			{
				writeExtent("Fail", "verified that breakdown is completed for"+pmkey+" shipemnts on"+screenName);
			}
		}
	}

	/**
	 * @Description : Used to List Flight
	 * @author A-9175
	 * @param carrCode
	 * @param FlightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void listFlight(String carrCode, String FlightNumber, String flightDate)
			throws InterruptedException, AWTException, IOException {
		
		waitTillScreenload(sheetName, "inbx_carrierCode;id","Flight Carrier code", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_carrierCode;xpath", data(carrCode), "Flight Carrier code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;xpath", data(flightDate), "Flight Date", screenName);
		performKeyActions(sheetName, "inbx_flightDate;xpath", "TAB", "Flight Date", screenName);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(5);

	}
	public ArrayList<String> calculateLPSTimeWithOutCustomerSlot(String ata,int bct)
	{
		ArrayList<String> lpsDetails =new ArrayList<String>();
		try
		{	
			String ciq=timeCalculation(ata, "HH:mm","MINUTE",Integer.parseInt(data("CIQ_Configtime")));
			String lps=timeCalculation(ciq,"HH:mm","MINUTE",-(bct));
			lpsDetails.add(lps);
			lpsDetails.add(createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			return lpsDetails;
			}
			
			catch(Exception e)
			{
				return lpsDetails;
			}
		}


	/**
	 * @author A-7271
	 * @param timeSlot
	 * @param bct
	 * @param ata
	 * @param rampToPitMoveTime
	 * @throws java.text.ParseException
	 * @throws ParseException
	 * Desc : verify LPS time terminal shipment before planning complete
	 */
	public void verifyLPSForTerminalShipmentBeforePlanningComplete(int count,String timeSlot,int[] bct,String ata,int rampToPitMoveTime,int eps,String uldNo,String lpsDate) throws java.text.ParseException, ParseException
	{



		List <String> lpsDetails=new ArrayList<String>();
		ArrayList <String> lpsCalculated=new ArrayList<String>();
		String [] timeStamp=new String[count];
		int k =0;

		try
		{
			if(timeSlot.equals("noTimeSlot"))
				
			{
				for(int i=0;i<count;i++){
					lpsDetails=calculateLPSTimeWithOutCustomerSlot(ata,bct[i]);
					System.out.println("LPS Time of shipment "+i+" is "+lpsDetails.get(0));
					System.out.println("LPS Date of shipment "+i+" is "+lpsDetails.get(1));

					lpsCalculated.add(lpsDetails.get(0));
					lpsCalculated.add(lpsDetails.get(1));

				}
					
					 System.out.println(lpsCalculated);
						for(int j=0;j<count*2;j=j+2){

							timeStamp[k]=lpsCalculated.get(j);
							System.out.println(timeStamp[k]);
							k++;
							
						}
}
			
			
			else
			{
				for(int i=0;i<count;i++){
					lpsDetails=calculateLPSWithCustomerSlot(timeSlot,bct[i],ata,rampToPitMoveTime);
					System.out.println("LPS Time of shipment "+i+" is "+lpsDetails.get(0));
					System.out.println("LPS Date of shipment "+i+" is "+lpsDetails.get(1));

					lpsCalculated.add(lpsDetails.get(0));
					lpsCalculated.add(lpsDetails.get(1));

				}	
			
			   System.out.println(lpsCalculated);	
				for(int j=0;j<count*2;j=j+2){

					timeStamp[k]=lpsCalculated.get(j);
					System.out.println(timeStamp[k]);
					k++;
					
				}
			}
				
				waitForSync(5);
				//verify lps time
				String actLPSTime=sortTimeStamps(timeStamp, "lowset");
				System.out.println(actLPSTime);
				driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_lpsedit;xpath").replace("*", data(uldNo)))).click();
				waitForSync(2);	
				String locator= xls_Read.getCellValue(sheetName, "txt_lpsTime;xpath").replace("*", data(uldNo));
				String lpsTime= driver.findElement(By.xpath(locator)).getAttribute("value");
				verifyScreenTextWithExactMatch(sheetName,lpsTime, actLPSTime, "LPS Time verification","LPS Time verification");
				//verify lps date
				String locator1= xls_Read.getCellValue(sheetName, "txt_lpsDate;xpath");
				String ExplpsDate= driver.findElement(By.xpath(locator1)).getAttribute("value");
				verifyScreenTextWithExactMatch(sheetName,ExplpsDate, data(lpsDate), "LPS Date verification","LPS Date verification");
				writeExtent("Pass", "LPS time got Displayed on " +screenName);
			}
		catch(Exception e)
		{
			writeExtent("Fail", "Could not verify LPS time");
		}

	}

	


	/**
	 * @author A-7271
	 * @param timeSlot
	 * @param bct
	 * @param ata
	 * @param rampToPitMoveTime
	 * @throws java.text.ParseException
	 * @throws ParseException
	 * Desc : verify LPS time terminal shipment
	 */
	public void verifyLPSForTerminalShipment(int count,String timeSlot,int[] bct,String ata,int rampToPitMoveTime,int eps,String uldNo) throws java.text.ParseException, ParseException
	{


		List <String> lpsDetails=new ArrayList<String>();
		ArrayList <String> lpsCalculated=new ArrayList<String>();

		try
		{

			if(timeSlot.equals("noTimeSlot"))
			{
				String epsTime=calculateLPSWithOutCustomerSlot(ata,eps);
				String epsDate=createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", "");

			}

			else
			{
				for(int i=0;i<count;i++){
					lpsDetails=calculateLPSWithCustomerSlot(timeSlot,bct[i],ata,rampToPitMoveTime);
					System.out.println("LPS Time of shipment "+i+" is "+lpsDetails.get(0));
					System.out.println("LPS Date of shipment "+i+" is "+lpsDetails.get(1));

					lpsCalculated.add(lpsDetails.get(0));
					lpsCalculated.add(lpsDetails.get(1));

				}

				System.out.println(lpsCalculated);
				String [] timeStamp=new String[count];

				int k =0;
				for(int j=0;j<count*2;j=j+2){

					timeStamp[k]=lpsCalculated.get(j);
					System.out.println(timeStamp[k]);
					k++;
				}
				waitForSync(5);
				//verify lps time
				String actLPSTime=sortTimeStamps(timeStamp, "lowset");
				System.out.println(actLPSTime);
				String ele = xls_Read.getCellValue(sheetName, "txt_lpsTimeDisplayed;xpath").replace("*",data(uldNo));
				System.out.println(ele);
				String lps=driver.findElement(By.xpath(ele)).getText();
				System.out.println(lps);
				waitForSync(5);
				verifyScreenTextWithExactMatch(sheetName, lps,actLPSTime , "LPS time displayed","LPS time displayed");
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not verify LPS time");

		}

	}
	/** @Desc : clickScribblePad
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickScribblePad() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_flightnumber_scribbleimg;id", " Scribble Pad ", screenName);
        waitTillScreenload(sheetName, "txt_remarksScribblePad;xpath", "Reamrks Section code", screenName);
		
	}

	/**
	 * @Desc : verifyScribbleText
	 * @author A-9175
	 * @param scribbleText
	 * @throws Exception
	 */
	public void verifyScribbleText(String scribbleText) throws Exception {
		try {
			waitTillScreenload(sheetName, "txt_scribbleCaptured;xpath", "Scribble Text code", screenName);
			String locator = xls_Read.getCellValue(sheetName, "txt_capturedScribbleText;xpath");
			String actText = driver.findElement(By.xpath(locator)).getText().replaceAll("\\s", "");
			System.out.println(actText);
			verifyScreenText(screenName, scribbleText.replaceAll("\\s", ""), actText, " Scribble Text ",
					"Verified Sucessfully");
			waitForSync(2);
			writeExtent("Pass",
					"Sucessfully Verified Scribble Information as " + scribbleText + " On " + screenName + " Page");
		} catch (Exception e) {
			writeExtent("Fail", "Could not Capture Scribble Information " + " On " + screenName + " Page");
		}

	}

	/**
	 * @author A-9847
	 * @Desc To verify the LPS time for Transit Shipments as BufferOpenTime-Configured BCT on ImportManifest Screen(Single Onward Flight)
	 * @param lpsDate
	 * @param bufferTime
	 * @param bct
	 * @param uldNum
	 * @throws NumberFormatException
	 * @throws ParseException
	 */
	
	public void verifyLPSTransitTime(String lpsDate,String bufferTime, String bct,String uldNum) throws NumberFormatException, ParseException{
	
	try{
	
		driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_lpsedit;xpath").replace("*", data(uldNum)))).click();
	    waitForSync(2);
		String ExplpsTime=timeCalculation(data(bufferTime), "HH:mm","MINUTE",-Integer.parseInt(data(bct)));	
		String locator= xls_Read.getCellValue(sheetName, "txt_lpsTime;xpath").replace("*", data(uldNum));
	    String lpsTime= driver.findElement(By.xpath(locator)).getAttribute("value");
	    verifyScreenTextWithExactMatch(sheetName,ExplpsTime, lpsTime, "LPS Time verification","LPS Time verification");
	
		 String locator1= xls_Read.getCellValue(sheetName, "txt_lpsDate;xpath");
	     String ExplpsDate= driver.findElement(By.xpath(locator1)).getAttribute("value");
	     verifyScreenTextWithExactMatch(sheetName,ExplpsDate, data(lpsDate), "LPS Date verification","LPS Date verification");
	     writeExtent("Pass", "LPS time got Displayed on " +screenName);
	}
	catch(Exception e){
		writeExtent("Fail", "LPS time is not getting displayed correctly on " +screenName);
	}
	
	
	}
	/**
	 * @author A-9844
	 * @param count
	 * @param expText
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify split shipment indicator displayed against the AWB
	 */
	public void verifySplitShipmentIndicator(int count,String expText) throws InterruptedException, AWTException {

		try{
			for(int i=0;i<count;i++){

				String locator= xls_Read.getCellValue(sheetName, "txt_spiltShipmentIndicatorIcon;xpath");
				By ele =By.xpath(locator);
				String actText = driver.findElement(ele).getText();
				System.out.println(actText);
				if (actText.equals(data(expText)))
					writeExtent("Pass", "Verified split shipment indicator "+actText+"  displayed for the awb in "+screenName); 
				else
					writeExtent("Fail", "Failed to verify split shipment indicator "+actText+"  displayed for the awb in "+screenName); 

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "No indicator is displayed for the shipment "+screenName);
		}


	}
	/**
	 * @author A-9844
	 * @param count
	 * @param expText
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify the color of the  split shipment indicator displayed against the awb number
	 */
	public void verifySplitShipmentIndicatorColor(int count,String expText) throws InterruptedException, AWTException {

		String actColor = "";
		try{
			for(int i=0;i<count;i++){

				String ele1 = xls_Read.getCellValue(sheetName, "txt_spiltShipmentIndicatorIcon;xpath");
				actColor=driver.findElement(By.xpath(ele1)).getAttribute("class");
				System.out.println(actColor);
				if (actColor.contains(data(expText)))
					writeExtent("Pass", "Verified color of split shipment indicator as "+actColor+"  displayed for the awb in "+screenName); 
				else
					writeExtent("Fail", "Failed to verify color of split shipment indicator as "+actColor+"  displayed for the awb in "+screenName); 

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "No indicator color is displayed for the shipment "+screenName);
		}


	}
	/**
	 * @author A-9844
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify split shipment indicator icon is  displayed to the left of  the awb number
	 */
	public void verifySplitShipmentIndicatorIsPresentLeft(String awbNo) throws InterruptedException, AWTException {

		try
		{
			String locator = xls_Read.getCellValue(sheetName, "txt_splitshipmentAWB;xpath");
			locator=locator.replace("*", data(awbNo));

			if((driver.findElements(By.xpath(locator)).size()>0)){

				writeExtent("Pass","Successfully verified the split shipment indicator is present to the left of the awb on "+screenName);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Failed to verify the split shipment indicator is present to the left of the awb on "+screenName);
		}

	}


	/**
	 * @Desc To enter the remarks
	 * @throws InterruptedException
	 */
public void enterRemarks() throws InterruptedException{
		
	enterTextWithoutClear(sheetName, "txt_remarks;xpath", "Test", "Remarks", screenName);
	/**enterValueInTextbox(sheetName, "txt_remarks;xpath", "Test", "Remarks", screenName);**/
	waitForSync(1);

		}
	/**
	 * @Description : verifying the presence of awbs under ULD/Bulk
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyShipmentPresentOrNot(boolean val, String awbNo) throws InterruptedException, AWTException {
		waitForSync(2);
		if (val) {
			try {
				String locator = xls_Read.getCellValue(sheetName, "txt_AWBnoVerify;xpath");

				locator = locator.replace("awbNo", data(awbNo));

				driver.findElement(By.xpath(locator)).isDisplayed();
				waitForSync(2);
				writeExtent("Pass", "Successfully Verified " + data(awbNo) + " In " + screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Could not Verify " + data(awbNo) + " In " + screenName);
			}
			waitForSync(2);
		} else {
			try {
				String locator = xls_Read.getCellValue(sheetName, "txt_AWBnoVerify;xpath");

				locator = locator.replace("awbNo", data(awbNo));

				driver.findElement(By.xpath(locator)).isDisplayed();
				waitForSync(2);
				writeExtent("Fail", data(awbNo) + " is present in " + screenName);
			} catch (Exception e) {
				writeExtent("Pass", data(awbNo) + " Not Present in " + screenName);
			}
			waitForSync(2);
		}

	}
	/**
	 * @Description :CAPTURING NEW AWB AFTER SELECTING ADD BUTTON 
	 * @author A-10690
	 * @param awbPre
	 * @param AwbNo
	 * @param manPcs
	 * @param manWgt
	 * @param Origin
	 * @param Destination
	 * @param statedPcs
	 * @param statedWgt
	 * @throws Exception 
	 */
	public void addNewAWB(String awbPre, String AwbNo, String manPcs, String manWgt, String Origin,
			String Destination, String statedPcs, String statedWgt)
					throws Exception {


		clickButtonSwitchWindow(sheetName, "btn_add;id", "Add  Button", screenName);
		waitForSync(4);
		enterValueInTextbox(sheetName, "btn_shipprefix;name", data(awbPre), " AWB Prefixr ", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName,"btn_awbno;xpath", data(AwbNo), " AWB Number ", screenName);
		clickWebElement(sheetName, "btn_list;name", "Ok Button", screenName);
		waitForSync(5);
		try {
			clickWebElement(sheetName, "btn_yes;xpath", "yes Button", screenName);
			waitForSync(2);
		} catch (Exception e) {
		}
		enterValueInTextbox(sheetName, "btn_origin;name", data(Origin), " Origin ", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "btn_dest;name", data(Destination), " Destination ", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "btn_statedp;name", data(manPcs), " Manifested Pieces ", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "btn_statedw;name", data(manWgt), " Manifested weight ", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "btn_rcvdpcs;name", data(manPcs), " Manifested Pieces ", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "btn_rcvdwg;name", data(manWgt), " Manifested weight ", screenName);

		waitForSync(2);
		enterValueInTextbox(sheetName, "btn_loc;name", data("BDNlocation"), " Manifested weight ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_ok;name", "OK Button", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR367");
	}


	
	/**
	 * @Description : Verifying the warning messages for the awbs having discrepencies
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyWarningMessageWith2AWBsWithDiscrepencies(String awb1,String awb2) throws InterruptedException, IOException {
		switchToFrame("default");
		String s1 = "2 AWBs," + data("CarrierNumericCode") + "-" + data(awb1) + ", "
				+ data("CarrierNumericCode") + "-" + data(awb2) + ".";
		String s2 = "2 AWBs," + data("CarrierNumericCode") + "-" + data(awb2) + ", "
				+ data("CarrierNumericCode") + "-" + data(awb1) + ".";
		String locator = xls_Read.getCellValue(sheetName, "txt_warningmessageDis;xpath");
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

		clickWebElement(sheetName, "btn_yes;xpath", "yes Button", screenName);
		waitForSync(1);


	}

	/**
	 * @Description : Clicking Flag flight button
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void flagFlightWarningMessage() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_flagFlight;id", "Flag Flight", screenName);
		waitForSync(5);
}

/**
	 * @Description : Enter thru breakdown location
	 * @author A-9844
	 * @throws Exception
	 */
	public void enterThruBreakdownLocation() throws Exception {
		
		String thruLocation="";
		if(getLoggedInStation("OPR367").equals("AMS"))
		{
		 thruLocation= getPropertyValue(toproppath, "Breakdown_Location_AMS");
		}
		else if(getLoggedInStation("OPR367").equals("IAD")){
		 thruLocation= getPropertyValue(toproppath, "Location_IAD");	
		}
		
		switchToWindow("storeParent");
		switchToWindow("multipleWindows");
	
		enterValueInTextboxByJS(sheetName,"inbx_thruLocation;name",thruLocation , "breakdown location -thru unit", screenName);
		clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
            waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR367");
		waitForSync(2);
	}


/**
	 * @Description : Verifying the warning messages for the awbs having discrepencies
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyWarningMessageAfterFlagFlight(String awb1,String awb2) throws InterruptedException, IOException {
		
		String s1 = "2 AWBs," + data("CarrierNumericCode") + "-" + data(awb1) + ", "
				+ data("CarrierNumericCode") + "-" + data(awb2) + ".";
		String s2 = "2 AWBs," + data("CarrierNumericCode") + "-" + data(awb2) + ", "
				+ data("CarrierNumericCode") + "-" + data(awb1) + ".";
		String locator = xls_Read.getCellValue(sheetName, "txt_WarningMessageAfterCloseFlight;xpath");
		String actualtext=driver.findElement(By.xpath(locator)).getText();
		System.out.println(actualtext);
		System.out.println(s1);
	if (actualtext.contains(s1) || actualtext.contains(s2)) {
			writeExtent("Pass",
					"Successfully verified warning message as '" +actualtext+ "' on "+ screenName);
		} else {
			writeExtent("Fail",
					"Failed to verify warning message with 2 awbs after clicking flag flight" + screenName);
		}

	clickWebElement(sheetName, "btn_Ok;xpath", "ok button", screenName);
		waitForSync(2);


	}	

	/**
	 * @Description : Verifying the warning messages for the awbs having discrepencies
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyWarningMessageAfterFlagFlight(String awb1) throws InterruptedException, IOException {
		switchToFrame("default");
		String s1 = "1 AWBs," + data("CarrierNumericCode") + "-" + data(awb1) + ".";
		String locator = xls_Read.getCellValue(sheetName, "txt_WarningMessageAfterCloseFlight;xpath");
		String actualtext=driver.findElement(By.xpath(locator)).getText();
		System.out.println(actualtext);
		System.out.println(s1);
	if (actualtext.contains(s1)) {
			writeExtent("Pass",
					"Successfully verified warning message as '" +actualtext+ "' on "+ screenName);
		} else {
			writeExtent("Fail",
					"Failed to verify warning message with 2 awbs after clicking flag flight" + screenName);
		}

	clickWebElement(sheetName, "btn_Ok;xpath", "ok button", screenName);
		waitForSync(2);


	}
	/**
	 * @Description : Verify and add custom info
	 * @author A-7943
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */



/**
 * @author A-7943
 * Desc :verifySelectAsCustomInfoSCI
 */
	public void verifySelectAsCustomInfoSCI(){
		By source = getElement(sheetName, "lbl_SelectCustomInfo;xpath");
		String actText = driver.findElement(source).getText();
		String expText = "Select";
		if (actText.equals(expText)) {
			verifyScreenText(sheetName, expText, actText, "verify SCI ", "Capture AWB");
			writeExtent("Pass", "SCI is  displayed as " + expText + " on " + screenName);

		} else {
			writeExtent("Fail", "SCI is not displayed as " + expText + " on " + screenName);

		}
	}
	/**
	 * @Description : Verify and add custom info
	 * @author A-7943
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

		public void addNewULDInfo(String ULDno, String awbPre, String AwbNo, String manPcs, String manWgt, String Origin,
				String Destination, String statedPcs, String statedWgt,int sciIndex)
				throws InterruptedException, AWTException, IOException {
			
			//click add ULD button

			clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
			waitForSync(5);
			
			// Capture  New ULD number
			enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
			clickWebElement(sheetName, "btn_newULDAdd;id", "Add New ULD Button", screenName);
			
			//Capture New AWB Number
		
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
			enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
			
			waitForSync(5);
			try {
				//Checking for ok for fresh AWB number
				clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
				waitForSync(2);
			} catch (Exception e) {
			}
			//Verify the custom info as select
			verifySelectAsCustomInfoSCI();
			
			//Capturing Manifest Pcs and Wgt Information
			enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
			performKeyActions(sheetName, "inbx_manifestedPcs;id", "TAB", "AWB Num", screenName);
			enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
			
			//Capturing Origin and Destination
			
			enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(Origin), " Origin ", screenName);
			enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(Destination), " Destination ", screenName);
			waitForSync(5);
			
			//Capturing Stated Pieces and Weight Information
			
			enterValueInTextbox(sheetName, "inbx_statedPcs;id", data(statedPcs), " Stated Pieces ", screenName);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_statedWgt;id", data(statedWgt), " Stated Weight ", screenName);

			//Selecting SCI Info
			waitForSync(3);
			clickWebElementByWebDriver(sheetName, "lst_SCI;xpath", "List BDN", screenName);
			waitForSync(2);
			try
			{
				for(int i=0;i<sciIndex;i++)
				{
				keyPress("DOWN");
				}
				keyPress("ENTER");
				writeExtent("Pass", "SCI : Index : "+sciIndex+" successfully selected"+ screenName + " Page");
			}catch (Exception e) {
				writeExtent("Fail", "SCI : Index : "+sciIndex+" could not be selected"+ screenName + " Page");
			}
			
			waitForSync(2);
			clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
			waitForSync(2);
			clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
		}

	
		/**
		 * @author A-9847
		 * @Desc To enter the breakdown details if not autopopulated
		 * @param numberOfShipments
		 * @param location
		 * @param pieces
		 * @param weight
		 * @throws InterruptedException
		 * @throws AWTException
		 * @throws IOException
		 */
		public void enterBdnDetailsIfnotPopulated(int numberOfShipments, String[] location, String[] pieces,
				String[] weight) throws InterruptedException, AWTException, IOException {

			String loc = xls_Read.getCellValue(sheetName, "inbx_breakdownLocation;xpath");
			loc = loc.replace("*", "1");

			if(driver.findElement(By.xpath(loc)).getAttribute("value").equals(""))
			{
				for (int i = 0; i < numberOfShipments; i++) 
				{

					//Enter location
					try {
						String locator = xls_Read.getCellValue(sheetName, "inbx_breakdownLocation;xpath");
						locator = locator.replace("*", Integer.toString(i + 1));
						driver.findElement(By.xpath(locator)).clear();
						driver.findElement(By.xpath(locator)).sendKeys(location[i]);
						keyPress("TAB");
						waitForSync(1);
						writeExtent("Pass", "Entered breakdown location " + location[i] + " in " + screenName);
					} catch (Exception e) {
						writeExtent("Fail", "Couldn't enter breakdown location " + location[i] + " in " + screenName);
					}

					//Enter pieces
					try {
						String locator = xls_Read.getCellValue(sheetName, "inbx_recievedPieces;xpath");
						locator = locator.replace("*", Integer.toString(i + 1));
						driver.findElement(By.xpath(locator)).clear();
						driver.findElement(By.xpath(locator)).sendKeys(pieces[i]);
						keyPress("TAB");
						waitForSync(1);
						writeExtent("Pass", "Entered received pieces " + pieces[i] + " in " + screenName);
					} catch (Exception e) {
						writeExtent("Fail", "Couldn't enter received pieces " + pieces[i] + " in " + screenName);
					}

					//Enter weight
					try {
						String locator = xls_Read.getCellValue(sheetName, "inbx_recievedWeight;xpath");
						locator = locator.replace("*", Integer.toString(i + 1));
						driver.findElement(By.xpath(locator)).clear();
						driver.findElement(By.xpath(locator)).sendKeys(weight[i]);
						waitForSync(1);
						writeExtent("Pass", "Entered received weight " + weight[i] + " in " + screenName);
					} catch (Exception e) {
						writeExtent("Fail", "Couldn't enter received weight " + weight[i] + " in " + screenName);
					}

				}
			}

			}


	/**
	 * @author A-6260
	 * @Description: Enter breakdown details fro multiple shipments
	 * @param numberOfShipments
	 * @param location
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void enterBdnDetails_multipleShipments(int numberOfShipments, String[] location, String[] pieces,
			String[] weight) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		for (int i = 0; i < numberOfShipments; i++) {
			// Enter location
			try {
				String locator = xls_Read.getCellValue(sheetName, "inbx_breakdownLocation;xpath");
				locator = locator.replace("*", Integer.toString(i + 1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(location[i]);
				keyPress("TAB");
				waitForSync(1);
				writeExtent("Pass", "Entered breakdown location " + location[i] + " in " + screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter breakdown location " + location[i] + " in " + screenName);
			}

			// Enter pieces
			try {
				String locator = xls_Read.getCellValue(sheetName, "inbx_recievedPieces;xpath");
				locator = locator.replace("*", Integer.toString(i + 1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(pieces[i]);
				keyPress("TAB");
				waitForSync(1);
				writeExtent("Pass", "Entered received pieces " + pieces[i] + " in " + screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter received pieces " + pieces[i] + " in " + screenName);
			}

			// Enter weight
			try {
				String locator = xls_Read.getCellValue(sheetName, "inbx_recievedWeight;xpath");
				locator = locator.replace("*", Integer.toString(i + 1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(weight[i]);
				waitForSync(1);
				writeExtent("Pass", "Entered received weight " + weight[i] + " in " + screenName);
			
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter received weight " + weight[i] + " in " + screenName);
			}
		}

	}

	/**
	 * @Description : Verifying SCC against ULD
	 * @author A-9175
	 * @param ULDNo
	 * @param SccValue
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifySCCsForShipment(String ULDNo, String SccValue) throws InterruptedException, AWTException {
		String locator = xls_Read.getCellValue(sheetName, "lbl_sccs;xpath");
		locator = locator.replace("ULDNo", data(ULDNo));

		String actText = driver.findElement(By.xpath(locator)).getText();
		verifyScreenText(screenName, data(SccValue), actText, " SCC ", "Verified Sucessfully");

		waitForSync(2);
	}

	/**
	 * @author A-9478
	 * @Description: Verify warning message after clicking on Flag flight button
	 * @throws InterruptedException
	 */
	public void verifyWarningMessageWith2AWBsAfterFlagFlight() throws InterruptedException {
		String s1 = "2 AWBs," + data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo2") + ", "
				+ data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo") + ".";
		String s2 = "2 AWBs," + data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo") + ", "
				+ data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo2") + ".";
		String actualtext = getElementTextnoFrameSwitch(sheetName, "txt_WarningMessageAfterCloseFlight;xpath",
				"Warning message", screenName);
		if (actualtext.contains(s1) || actualtext.contains(s2)) {
			writeExtent("Pass",
					"Successfully verified warning message with 2 awbs after clicking on Flag flight in" + screenName);
		} else {
			writeExtent("Fail",
					"Failed to verify warning message with 2 awbs after clicking on Flag flight in" + screenName);
		}
	}

	/**
	 * @author A-9478
	 * @Description: Verify warning message after clicking on Close Flight
	 *               button
	 * @throws InterruptedException
	 */
	public void verifyWarningMessageWith2AWBsAfterCloseFlight() throws InterruptedException {
		String s1 = "2 AWB(s) , " + data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo") + ", "
				+ data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo2") + ".";
		String s2 = "2 AWB(s) , " + data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo2") + ", "
				+ data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo") + ".";
		String actualtext = getElementTextnoFrameSwitch(sheetName, "txt_WarningMessageAfterCloseFlight;xpath",
				"Warning message", screenName);
		if (actualtext.contains(s1) || actualtext.contains(s2)) {
			writeExtent("Pass",
					"Successfully verified warning message with 2 awbs after clicking on Close Flight in" + screenName);
		} else {
			writeExtent("Fail",
					"Failed to verify warning message with 2 awbs after clicking on Close Flight in" + screenName);
		}
	}

	/**
	 * @Description : capture breakdown details general
	 * @author A-9175
	 * @param breakDownLoc
	 * @param numberOfShipments
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void enterBreakdownInstructions(String breakDownLoc, int numberOfShipments)
			throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		for (int i = 0; i < numberOfShipments; i++) {
			String locator = xls_Read.getCellValue(sheetName, "inbx_locationBreakdown;id");
			String shipment = String.valueOf(i);
			locator = locator.replace("val", shipment);
			driver.findElement(By.id(locator)).sendKeys(data(breakDownLoc));
			waitForSync(3);

			keyPress("TAB");
			keyRelease("TAB");
			waitForSync(2);
		}
		waitForSync(5);

	}

	/**
	 * @author A-9478
	 * @Description: Verify warning message after clicking on Breakdown complete
	 *               button
	 * @throws InterruptedException
	 */

	public void verifyWarningMessageWith2AWBsAfterBreakdownComplete() throws InterruptedException {
		switchToFrame("default");
		String s1 = "2 AWBs," + data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo2") + ", "
				+ data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo") + ".";
		String s2 = "2 AWBs," + data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo") + ", "
				+ data("prop~CarrierNumericCode") + "-" + data("prop~AWBNo2") + ".";
		String actualtext = getElementTextnoFrameSwitch(sheetName, "txt_warningMessage;xpath", "Warning message",
				screenName);
		if (actualtext.contains(s1) || actualtext.contains(s2)) {
			writeExtent("Pass", "Successfully verified warning message with 2 awbs after clicking breakdown complete in"
					+ screenName);
		} else {
			writeExtent("Fail",
					"Failed to verify warning message with 2 awbs after clicking breakdown complete in" + screenName);
		}
		switchToFrame("contentFrame", "OPR367");
	}

	/**
	 * @Description : Verifying Breakdown Instruction Label
	 * @author A-9175
	 * @param breakDownInstructionLabel
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyBreakdownInstructionsTag(String breakDownInstructionLabel)
			throws InterruptedException, AWTException {
		String actText = "";
		actText = getElementText(sheetName, "lbl_BreakdownInstructions;xpath", " BDN InstructionTag ", screenName);
		System.out.println(actText);
		verifyScreenText(screenName, data(breakDownInstructionLabel), actText, " BDN InstructionTag ",
				"BDN InstructionTag Verified Sucessfully");
		waitForSync(2);
	}

	/**
	 * @author A-6260
	 * @Description: Select the awb document received checkbox
	 * @param awbs
	 */
	public void selectAWBdocumentReceived(String[] awbs) {
		int count = awbs.length;
		for (int i = 0; i < count; i++) {
			try {
				String AWBreceivedlocator = xls_Read.getCellValue(sheetName, "chk_AWBreceived;xpath");
				AWBreceivedlocator = AWBreceivedlocator.replace("*", awbs[i]);
				moveScrollBar(driver.findElement(By.xpath(AWBreceivedlocator)));
				waitForSync(3);

				//Mark AWB Received
				WebElement element=driver.findElement(By.xpath(AWBreceivedlocator));
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
				keyPress("TAB");

				writeExtent("Pass", "Selected " + awbs[i] + " document received Checkbox in " + screenName);
				waitForSync(4);
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't select " + awbs[i] + " document received checkbox in " + screenName);
			}
		}

	}

	/**
	 * @author A-6260
	 * @Description: add breakdown instructions
	 * @param breakdownInstruction
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void addBreakdownInstructions(String breakdownInstruction) throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_AddBreakdownInstructions;xpath", "Add breakdown instructions", screenName);
		waitForSync(3);
		switch (breakdownInstruction) {
		case "THRU":
			clickWebElement(sheetName, "btn_ThruUnit;xpath", "Thru unit", screenName);
			waitForSync(2);
			break;
		case "Breakdown":
			clickWebElement(sheetName, "btn_bdnInstruction;xpath", "Breakdown", screenName);
			waitForSync(2);
			break;
		}
		clickWebElement(sheetName, "btn_breakdownInstructionsArrow;xpath", "breakdown instructions Arrow", screenName);
		waitForSync(2);
	}

	/**
	 * @Description : Verifying Forward Suggestion details after edit
	 * @author A-9175
	 * @param zoneVal
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	public void verifyForwardSuggestionValAfterAdd(String zoneVal) throws InterruptedException, AWTException {
		String actText = "";
		actText = getElementText(sheetName, "lbl_afterForwardSuggestion;xpath", " Zone Details ", screenName);
		verifyScreenText(screenName, data(zoneVal), actText, " Zone Details ", "Verified Sucessfully");
		waitForSync(2);
	}

	/**
	 * @Description : Verifying Warning Message Appears on Screen
	 * @author A-9175
	 * @throws InterruptedException
	 */
	public void verifyWarningMessage(String message) throws InterruptedException {
		switchToFrame("default");
		getTextAndVerify(sheetName, "txt_warningMessage;xpath", "Warning message", screenName, "Warning message",
				message, "contains");
		waitForSync(1);
		switchToFrame("contentFrame", "OPR367");
	}

	/**
	 * @author A-9478
	 * @param status
	 * @throws InterruptedException
	 * @Description : Verify Operational Status
	 */
	public void verifyOperationalStatus(String status) throws InterruptedException {
		waitForSync(5);
		getTextAndVerify(sheetName, "txt_OpearationalStatus;xpath", "Operational Status", screenName,
				"Operational status", data(status), "equals");
	}

	/**
	 * @Description : Verifying value for special note after selecting value
	 * @author A-9175
	 * @param specialNoteValue
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifySpecialNoteAfterAdd(String specialNoteValue) throws InterruptedException, AWTException {
		String actText = "";
		actText = getElementText(sheetName, "lbl_specialNoteValueAfterSelect;xpath", " Specia Note ", screenName);
		verifyScreenText(screenName, data(specialNoteValue), actText, " Special Note Details ", "Verified Sucessfully");
		waitForSync(2);
	}
	/**@author A-10328
	 * Description - Click Reopen button
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void clickReopen() throws InterruptedException, IOException 

	{


		clickWebElement(sheetName, "btn_reopen;id", "Reopen Button", screenName);
		waitForSync(1);

	}

	/***@author A-10328
	 * Description - Planning complete button is disabled
	 * @param val
	 * @throws InterruptedException
	 */

	public void verifyPlanningCompleteButtonDisabled() throws InterruptedException {

		By btnStatus = getElement(sheetName, "btn_planningComplete;id");
		boolean val = driver.findElement(btnStatus).isEnabled();

		if(!val)
		{

			writeExtent("Pass", "Successfully Verified Planning Complete Button status is Disabled On " + screenName + " Page");
		}
		else
		{

			writeExtent("Fail", "Successfully Verified Planning Complete Button status is not Disabled On " + screenName + " Page");
		}
	}
	/**
	 * @Description : click add link for special note
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void clickAddSpecialNoteLink() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "link_SpecialNote;xpath", " Special Note ", screenName);
	}

	/**
	 * @Description : Selecting value for special note
	 * @author A-9175
	 * @param specialNoteValue
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void clickSpecialNoteRadioButtonFromList(String specialNoteValue) throws InterruptedException, AWTException {

		try {
			String locator = xls_Read.getCellValue(sheetName, "btn_specialNoteRadioButton;xpath");
			locator = locator.replace("SpecifySpecialNote", data(specialNoteValue));
			driver.findElement(By.xpath(locator)).click();
			waitForSync(2);
			writeExtent("Pass", "Successfully Selected " + specialNoteValue + " In " + screenName);
		} catch (Exception e) {
			writeExtent("Fail", "Could not Select " + specialNoteValue + " In " + screenName);
		}
	}

	/**
	 * @Description : Verifying value for special note after selecting value
	 * @author A-9175
	 * @param specialNoteValue
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifySpecialNoteAfterEdit(String specialNoteValue) throws InterruptedException, AWTException {
		String actText = "";
		actText = getElementText(sheetName, "lbl_specialNoteValueAfterSelect;xpath", " Specia Note ", screenName);
		verifyScreenText(screenName, data(specialNoteValue), actText, " Special Note Details ", "Verified Sucessfully");
		waitForSync(2);
	}

	/**
	 * @Description : Capture Location Pieces and weight
	 * @author A-9175
	 * @param AWBNo
	 * @param Location
	 * @param rcvdPcs
	 * @param rcvdWgt
	 * @throws Exception
	 */
	public void enterLocationPcsAndWgt(String AWBNo, String Location, String rcvdPcs, String rcvdWgt) throws Exception {
		try {
			String locator1 = xls_Read.getCellValue(sheetName, "inbx_locCodeForAWB;xpath");
			locator1 = locator1.replace("AWBNO", data(AWBNo));
			driver.findElement(By.xpath(locator1)).clear();
			driver.findElement(By.xpath(locator1)).sendKeys(data(Location));
			String locator2 = xls_Read.getCellValue(sheetName, "inbx_rcvdPiecesForAWB;xpath");
			locator2 = locator2.replace("AWBNO", data(AWBNo));
			String locator3 = xls_Read.getCellValue(sheetName, "inbx_rcvdWeightForAWB;xpath");
			locator3 = locator3.replace("AWBNO", data(AWBNo));
			driver.findElement(By.xpath(locator2)).clear();
			driver.findElement(By.xpath(locator2)).sendKeys(data(rcvdPcs));
			driver.findElement(By.xpath(locator3)).clear();
			driver.findElement(By.xpath(locator3)).sendKeys(data(rcvdWgt));
			writeExtent("Pass", "Successfully entered location " + data(Location) + " pieces " + data(rcvdPcs)
					+ " and weight " + data(rcvdWgt) + " in " + screenName);
		} catch (Exception e) {
			writeExtent("Fail", "Could not enter location " + data(Location) + " and pieces " + data(rcvdPcs)
					+ " and weight " + data(rcvdWgt) + " in " + screenName);
		}

	}

	/**
	 * @Description : Click close flight
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickCloseFlight() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_closeFlight;id", "Close Flight", screenName);
		waitForSync(5);
	}
	/**
	 * @Description Verifying SCCs added in the  ULD
	 * @author A-10690
	 * @param ULDNo
	 * @param SccValue
	 * @throws AWTException
	 */
	public void verifySCCsAddedInULD(String uldno,String[] sccValue) throws InterruptedException, AWTException {
		
	

		for(int i=0;i<sccValue.length;i++)
		{
			String locator = xls_Read.getCellValue(sheetName, "txt_outersccs;xpath");
			locator=locator.replace("ULD",data(uldno));
			String actscc=locator.replace("SCC", sccValue[i]);
			if(driver.findElements(By.xpath(actscc)).size()==1)
			{
				writeExtent("Pass", "verified the scc"+sccValue[i]+screenName);

			}
			else 
			{
				String scclist = xls_Read.getCellValue(sheetName, "btn_sccslist;xpath");
				scclist = scclist.replace("ULD", data(uldno));
				driver.findElement(By.xpath(scclist)).click();
				waitForSync(1);
				String actscc2 = xls_Read.getCellValue(sheetName, "txt_innerscc;xpath");
				actscc2=actscc2.replace("SCC", sccValue[i]);
				if(driver.findElements(By.xpath(actscc2)).size()==1)
				{
					writeExtent("Pass", "verified the scc"+sccValue[i]+screenName);
					driver.findElement(By.xpath(scclist)).click();

				}
				else
				{
					writeExtent("Fail", "verify the scc"+sccValue[i]+screenName);
					driver.findElement(By.xpath(scclist)).click();
				}

			}

		}
		waitForSync(2);
	}

	/**
	 * @author A-9844
	 * @param uld
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify ULD details 
	 */
	public void verifyUldDetails(int count,String[] ULDNo) throws InterruptedException, AWTException {

		try{
			for(int i=0;i<count;i++){
			

				String locator= xls_Read.getCellValue(sheetName, "txt_ULDdetails;xpath");
				locator=locator.replace("*", ULDNo[i]);
				By ele =By.xpath(locator);
				String actText = driver.findElement(ele).getText();
				System.out.println(actText);
				if (actText.equals(ULDNo[i]))
					writeExtent("Pass", "Sucessfully verified the ULDNo"+ULDNo[i]+" in "+screenName); 
				else
					writeExtent("Fail", "Failed to verify UldNo "+ULDNo[i]+" in"+screenName); 

			
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't verify the ULD in "+screenName);
		}


	}

	/**
	 * @author A-9844
	 * @param uld
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify ULD details 
	 */
	public void verifyUldDetails(int count,String[] ULDNo,String[] splitPieces,String[] splitWeight) throws InterruptedException, AWTException {

		try{
			for(int i=0;i<count;i++){
			

				String locator= xls_Read.getCellValue(sheetName, "txt_ULDdetails;xpath");
				locator=locator.replace("*", ULDNo[i]);
				By ele =By.xpath(locator);
				String actText = driver.findElement(ele).getText();
				System.out.println(actText);
				if (actText.equals(ULDNo[i]))
					writeExtent("Pass", "Sucessfully verified the ULDNo"+ULDNo[i]+" in "+screenName); 
				else
					writeExtent("Fail", "Failed to verify UldNo "+ULDNo[i]+" in"+screenName); 

			
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't verify the ULD in "+screenName);
		}


	}
	
	/**
	 * @author A-7271
	 * @param timeSlots
	 * @param bct
	 * @param ata
	 * @param rampToPitMoveTime
	 * @return
	 * @throws java.text.ParseException
	 * @throws ParseException
	 * Desc : calculate LPS time with customer time slot
	 */
	public ArrayList<String> calculateLPSWithCustomerSlot(String timeSlots,int bct,String ata,int rampToPitMoveTime) throws java.text.ParseException, ParseException
	{
		
		ArrayList<String> lpsDetails =new ArrayList<String>();

		try
		{
			String slotTime=timeCalculation(ata, "HH:mm","MINUTE",(bct+rampToPitMoveTime));
			System.out.println(slotTime);
			boolean sameDayDelivery=true;

			String startTime="";
			String endTime="";


			boolean lpsFound=false;
			String lps="";
			LocalTime target=null;
			LocalTime target2=null;
			String firstSlotTime=timeConverter("HHmm","HH:mm",timeSlots.split(",")[0].split("-")[0]);
			int timeslots=timeSlots.split(",").length;
			String lastTimeSlot=timeConverter("HHmm","HH:mm",timeSlots.split(",")[timeslots-1].split("-")[1]);
			System.out.println(lastTimeSlot);


			for(int i=0;i<timeSlots.split(",").length;i++)
			{
				String startTime2="";
				String endTime2="";
				startTime=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i].split("-")[0]);
				endTime=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i].split("-")[1]);

				try
				{
					startTime2=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i+1].split("-")[0]); 
					endTime2=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i+2].split("-")[1]); 

				}
				catch(Exception e)
				{

				}


				target = LocalTime.parse( slotTime ) ;
				target2 = LocalTime.parse( ata ) ;

				System.out.println(target);
				System.out.println(startTime);



				Boolean targetInZone = ( 
						target.isAfter( LocalTime.parse( startTime ) ) 
						&& 
						target.isBefore( LocalTime.parse( endTime ) ) 
						) ; 



				Boolean targetInZone2 = ( 

						target.isBefore( LocalTime.parse( startTime ) ) || target.equals( LocalTime.parse( startTime )) 


						) ; 

				Boolean targetInZone3 = ( 

						target.equals( LocalTime.parse( endTime )) 
						) ; 

				if(target2.isAfter( LocalTime.parse( lastTimeSlot )))
				{
					targetInZone2=false;
					targetInZone3=false;
					targetInZone=false;		
				}

				System.out.println(targetInZone);
				System.out.println(targetInZone2);

				if(targetInZone)
				{

					lps=timeCalculation(startTime, "HH:mm","MINUTE",-(bct));
					System.out.println(lps);
					lpsFound=true;
					break;


				}
				else if(targetInZone2)
				{
					lps=timeCalculation(startTime, "HH:mm","MINUTE",-(bct));
					System.out.println(lps);
					lpsFound=true;
					break;

				}
				else if(targetInZone3)
				{
					if(!endTime2.equals(""))
					{
						lps=timeCalculation(startTime2, "HH:mm","MINUTE",-(bct));
						System.out.println(lps);
						lpsFound=true;
						break;
					}

				}

			}
			if(!lpsFound)
			{
				lps=timeCalculation(firstSlotTime, "HH:mm","MINUTE",-(bct));
				System.out.println(lps);
				sameDayDelivery=false;
			}


			System.out.println("LPS TIME IS CALCULATED AS "+lps);
			if(sameDayDelivery)
			{
				System.out.println("LPS DATE IS CALCULATED AS "+createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));

				lpsDetails.add(lps);
				lpsDetails.add(createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
				
			}
			else
			{

				System.out.println("LPS DATE IS CALCULATED AS "+createDateFormatWithTimeZone("dd-MMM-YYYY", 1, "DAY", ""));
				lpsDetails.add(lps);
				lpsDetails.add(createDateFormatWithTimeZone("dd-MMM-YYYY", 1, "DAY", ""));
			}
			
			
			return lpsDetails;
		}


		catch(Exception e)
		{
			return lpsDetails;
		}
		
	}

	
	/**
	 * @author A-10690
	 * Description :Handle the pop up coming  in import manifest screen on  adding a new uld
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void handleNewULDWarning() throws InterruptedException, IOException
	{

		String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
		waitForSync(2);
		if(driver.findElements(By.xpath(popUp)).size()==1)
		{

			String actText=driver.findElement(By.xpath(popUp)).getText();

			if (actText.contains("does not exist in the system. Do you want to continue ?"))
			{

				writeExtent("Info", "Warning message comes as "+actText+ "on adding a new ULD on "+screenName);
			}
			else
			{
				writeExtent("Fail", "Warning message comes as "+actText+ "on adding a new ULD on "+screenName);
			}
			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(2); 

		}
	}

	/**
	 * @author A-7271
	 * @param ata
	 * @param eps
	 * @return
	 * Desc : calculate LPS time without time slot
	 */
	public String calculateLPSWithOutCustomerSlot(String ata,int eps)
	{
		try
		{
		String epsTime=timeCalculation(ata, "HH:mm","MINUTE",(eps));
		return epsTime;
		}
		
		catch(Exception e)
		{
			return "";
		}
	}
	
	/**
	 * @author A-9844
	 * @param count
	 * @param pieces
	 * @param weight
	 * @param expText
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify split indicator displayed against manifested pieces 
	 */
	public void verifySplitIndicator(int count,String[] pieces,String[] weight,String expText) throws InterruptedException, AWTException {

		try{
			for(int i=0;i<count;i++){

				String locator= xls_Read.getCellValue(sheetName, "txt_spiltIndicatorManifested;xpath");
				By ele =By.xpath(locator);
				String actText = driver.findElement(ele).getText();
				System.out.println(actText);
				if (actText.equals(data(expText)))
					writeExtent("Pass", "Verified split indicator "+actText+"  displayed for manifested "+pieces[i]+" pieces and weight "+weight[i]+" kg"); 
				else
					writeExtent("Fail", "Failed to verify split indicator "+actText+" displayed for Manifested pieces and weight"); 

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Shipment is not a split shipment "+screenName);
		}


	}


	/**
	 * @Description : Verifying Warning Message After Closing Flight
	 * @author A-9478
	 * @throws InterruptedException
	 */
	public void verifyWarningMessageAfterCloseFlight(String message) throws InterruptedException {

		getTextAndVerify(sheetName, "txt_WarningMessageAfterCloseFlight;xpath", "Warning message", screenName,
				"Warning message", message, "contains");
		waitForSync(1);
	}

	/**
	 * @Description : Click Ok button
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickOkbutton() throws InterruptedException, AWTException, IOException {

		while (driver.findElements(By.xpath("//button[text()='Ok']")).size() >= 1) {
			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(4);
		}
	}

	/**
	 * @Description : Search for an awb number in search area of shipments
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterSearchAWB(String awbNo) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_SearchAwb;xpath", data(awbNo), "Flight Carrier code", screenName);
		waitForSync(5);
	}

/**
 * @author A-9847
 * @Desc To check whether error message is displayed after closing the flight
 */
	
	
	public void errorMsgDisplayed(){
		try{
	String xpath = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMsg;xpath");
	String errMsg=driver.findElement(By.xpath(xpath)).getText();
		if(driver.findElement(By.xpath(xpath)).isDisplayed()) 
			writeExtent("Fail","Error message '"+errMsg+"' displayed on "+screenName);
		}
		catch(Exception e){
		}
  }

	
	/**
	 * @author A-9847
	 * @Desc  To enter the Breakdown details for the given AWB
	 * @param location
	 * @param pieces
	 * @param weight
	 * @param awb
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void enterBdnDetailsforAWB( String location, String pieces,
			String weight,String awb) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
	try{
	
		String locator = xls_Read.getCellValue(sheetName, "table_awbno;xpath");
		locator = locator.replace("*", data(awb));
		String k=driver.findElement(By.xpath(locator)).getAttribute("id");
		int i=Integer.parseInt(k);
		
			// Enter location
			try {
				String locator1 = xls_Read.getCellValue(sheetName, "inbx_breakdownLocation;xpath");
				locator1 = locator1.replace("*", Integer.toString(i+1));
				driver.findElement(By.xpath(locator1)).clear();
				driver.findElement(By.xpath(locator1)).sendKeys(location);
				keyPress("TAB");
				waitForSync(1);
				writeExtent("Pass", "Entered breakdown location " + location + " in " + screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter breakdown location " + location+ " in " + screenName);
			}

			// Enter pieces
			try {
				String locator2 = xls_Read.getCellValue(sheetName, "inbx_recievedPieces;xpath");
				locator2 = locator2.replace("*", Integer.toString(i+1));
				driver.findElement(By.xpath(locator2)).clear();
				driver.findElement(By.xpath(locator2)).sendKeys(pieces);
				keyPress("TAB");
				waitForSync(1);
				writeExtent("Pass", "Entered received pieces " + pieces+ " in " + screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter received pieces " + pieces + " in " + screenName);
			}

			// Enter weight
			try {
				String locator3 = xls_Read.getCellValue(sheetName, "inbx_recievedWeight;xpath");
				locator3 = locator3.replace("*", Integer.toString(i+1));
				driver.findElement(By.xpath(locator3)).clear();
				driver.findElement(By.xpath(locator3)).sendKeys(weight);
				waitForSync(1);
				writeExtent("Pass", "Entered received weight " + weight + " in " + screenName);
				} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter received weight " + weight + " in " + screenName);
			}
	}
	    catch(Exception e){
	     writeExtent("Fail", "Couldn't find the specified awb "+data(awb)+ " on" + screenName);	
	}

	}
	
	/**
	 * 
	 * @param numberOfShipments
	 * @param location
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 * Desc : enterBdnLocPiecesandVerifyWeightAutopopulated
	 */
	public void enterBdnLocPiecesandVerifyWeightAutopopulated(int numberOfShipments, String[] location, String[] pieces,String[] weight) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		for (int i = 0; i < numberOfShipments; i++) {
			// Enter location
			try {
				String locator = xls_Read.getCellValue(sheetName, "inbx_breakdownLocation;xpath");
				locator = locator.replace("*", Integer.toString(i + 1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(location[i]);
				keyPress("TAB");
				writeExtent("Pass", "Entered breakdown location " + location[i] + " in " + screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter breakdown location " + location[i] + " in " + screenName);
			}

			// Enter pieces
			try {
				String locator = xls_Read.getCellValue(sheetName, "inbx_recievedPieces;xpath");
				locator = locator.replace("*", Integer.toString(i + 1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(pieces[i]);
				keyPress("TAB");
				writeExtent("Pass", "Entered received pieces " + pieces[i] + " in " + screenName);
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't enter received pieces " + pieces[i] + " in " + screenName);
			}

			// Verifying Received weight Auto-populated
				String locator = xls_Read.getCellValue(sheetName, "inbx_recievedWeight;xpath");
				locator = locator.replace("*", Integer.toString(i + 1));
				String expwgt=driver.findElement(By.xpath(locator)).getAttribute("value");
				verifyScreenTextWithExactMatch(sheetName, weight[i], expwgt, "Received Weight Autopopulated","Received Weight");

		
		
		}

	}

	

	/**
	 * @Description : Select Breakdown Instruction
	 * @author A-9175
	 * @param instruction
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void selectBreakDownInstruction(String instruction) throws InterruptedException, AWTException, IOException {
		waitForSync(3);
		String locator1=xls_Read.getCellValue(sheetName, "span_bdninstructionupdated;xpath");
		locator1 = locator1.replace("*", data(instruction));


		int size=driver.findElements(By.xpath(locator1)).size();

		if(size==0)
		{
			clickWebElement(sheetName, "btn_editBreakdownInstruction;xpath", "Breakdown Instruction", screenName);

			waitForSync(2);
			try {
				String locator = xls_Read.getCellValue(sheetName, "btn_breakdownMode;xpath");
				locator = locator.replace("Mode", data(instruction));
				driver.findElement(By.xpath(locator)).click();
				waitForSync(2);
				writeExtent("Pass", "Selected Breakdown Instruction as " + data(instruction) + screenName + " Page");
			} catch (Exception e) {
				writeExtent("Fail", "Not Selected Breakdown Instruction as " + data(instruction) + screenName + " Page");
			}
		}

	}

	/**
	 * @Description : Verifying Info Listed
	 * @author A-9175
	 * @param infoNeeded
	 * @throws InterruptedException
	 */
	public void verifyIfInfoListed(boolean infoNeeded) throws InterruptedException {
		if (infoNeeded == false) {
			if (verifyElementDisplayed(sheetName, "txt_noResultsFound;xpath", "Info is not visible", screenName,
					"No Results found")) {
				test.log(LogStatus.PASS, "No shipments have been listed in " + screenName + " Page");
			} else {
				test.log(LogStatus.FAIL, "Shipment(s) is/are listed in " + screenName + " Page");
			}
		} else {
			if (verifyElementDisplayed(sheetName, "txt_noResultsFound;xpath", "Info is not visible", screenName,
					"No Results found")) {
				test.log(LogStatus.FAIL, "No shipments have been listed in " + screenName + " Page");
			} else {
				test.log(LogStatus.PASS, "Shipments are successfully listed in " + screenName + " Page");
			}
		}

	}

	/**
	 * @Description : Used to see all shipment Details
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void maximizeAllDetails() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		String locator = xls_Read.getCellValue(sheetName, "btn_maximizeAllDetails;xpath");
		if (driver.findElements(By.xpath(locator)).size() > 0) {
			clickWebElement(sheetName, "btn_maximizeAllDetails;xpath", "Maximize Shipment Details", screenName);
			waitForSync(5);
		}

	}

	/**
	 * 
	 * @Description : Used to click and breakdown and enter breakdown details for multiple shipment
	 * @author A-7037
	 * @param breakDownLoc
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickBreakDownandBreakdownComplete2AWBs(String breakDownLoc, String rcvdPcs, String rcvdWt)
			throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_breakDown;xpath", "BreakDown Button", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_breakdownLocationCode;name", data(breakDownLoc), "BreakDown Location",
				screenName);
		enterValueInTextbox(sheetName, "inbx_recievedPcs;name", data(rcvdPcs), "Recieved pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_recievedWgt;name", data(rcvdWt), "Recieved weight", screenName);
		enterValueInTextbox(sheetName, "inbx_breakdownLocationCode1;xpath", data(breakDownLoc), "BreakDown Location",
				screenName);
		enterValueInTextbox(sheetName, "inbx_recievedPcs1;xpath", data(rcvdPcs), "Recieved pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_recievedWgt1;xpath", data(rcvdWt), "Recieved weight", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_breakDownComplete;name", "BreakDown Button", screenName);
		waitForSync(5);

	}

	/**
	 * @Description Verifying SCCs in ULD
	 * @author A-9478
	 * @param ULDNo
	 * @param SccValue
	 * @throws AWTException
	 */
	public void verifySCCsInULD(String ULDNo, String SccValue) throws InterruptedException, AWTException {
		String locator = xls_Read.getCellValue(sheetName, "lst_BreakdownSccs;xpath");
		locator = locator.replace("ULDNo", data(ULDNo));
		int count = driver.findElements(By.xpath(locator)).size();
		if (count == 1) {
			String actText = driver.findElement(By.xpath(locator)).getText();
			verifyScreenText(screenName, data(SccValue), actText, " SCC ", "Verified Sucessfully");
		}
		waitForSync(2);
	}

	/**
	 * Desc : Verifying Clearing agent name Label
	 * 
	 * @author A-9175
	 * @param clearingAgentName
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyClearingAgentName(String clearingAgentName) throws InterruptedException, AWTException {

		String actText = "";
		actText = getElementText(sheetName, "lbl_clearingAgentName;xpath", " clearing Agent Name ", screenName);
		verifyScreenText(screenName, data(clearingAgentName), actText, " clearing Agent Name ", "Verified Sucessfully");
		waitForSync(2);
	}

	/**
	 * @Description : Used to close flight
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void closeFlight_withOkbutton() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_closeFlight;id", "Close Flight", screenName);
		waitForSync(5);
		clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
		waitForSync(2);
		try {
			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(2);
		}

		catch (Exception e) {

		}
	}

	public void addNewULDInfoWithSCC(String ULDno, String awbPre, String AwbNo, String manPcs, String manWgt, String Origin,
				String Destination, String statedPcs, String statedWgt,int sciIndex, String scc)
				throws InterruptedException, AWTException, IOException {
			
		//click add ULD button
		clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
		waitForSync(5);
		
		// Capture  New ULD number
		enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
		clickWebElement(sheetName, "btn_newULDAdd;id", "Add New ULD Button", screenName);
		handleNewULDWarning();
		
		//Capture New AWB Number	
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
		
		waitForSync(5);
		try {
			//Checking for ok for fresh AWB number
			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(2);
		} catch (Exception e) {
		}
		//Verify the custom info as select
		verifySelectAsCustomInfoSCI();
		
		//Capturing Manifest Pcs and Wgt Information
		enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
		performKeyActions(sheetName, "inbx_manifestedPcs;id", "TAB", "AWB Num", screenName);
		enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
		
		//Capturing Origin and Destination
		
		enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(Origin), " Origin ", screenName);
		enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(Destination), " Destination ", screenName);
		waitForSync(5);
		
		//Capturing SCC
		enterValueInTextbox(sheetName, "inbx_scc;xpath", data(scc), " SCC ", screenName);
		
		//Capturing Stated Pieces and Weight Information	
		enterValueInTextbox(sheetName, "inbx_statedPcs;id", data(statedPcs), " Stated Pieces ", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_statedWgt;id", data(statedWgt), " Stated Weight ", screenName);

		//Selecting SCI Info
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "lst_SCI;xpath", "List BDN", screenName);
		waitForSync(2);
		try
		{
			for(int i=0;i<sciIndex;i++)
			{
			keyPress("DOWN");
			}
			keyPress("ENTER");
			writeExtent("Pass", "SCI : Index : "+sciIndex+" successfully selected"+ screenName + " Page");
		}catch (Exception e) {
			writeExtent("Fail", "SCI : Index : "+sciIndex+" could not be selected"+ screenName + " Page");
		}
		
		waitForSync(2);
		clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
		}






/**
	 * @author A-9847
	 * @Desc To uncheck the manually updated checkbox if already checked.
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void uncheckManuallyUpdatedCheckbox() throws InterruptedException, IOException{
		
		try{	
		String xpath = xls_Read.getCellValue(sheetName, "inbx_manualUpdate;xpath");
		if(driver.findElements(By.xpath(xpath)).size()==1)
		{ 
			driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "chk_manuallyUpdated;xpath"))).click();
			writeExtent("Pass", "Unchecked the Manually Updated Checkbox on " + screenName);
		}
		}
		catch(Exception e){
			
			writeExtent("Fail", "Failed to Uncheck the Manually Updated Checkbox on " + screenName);
		}
		
	}
	



/**
	 * @author A-9847
	 * @Desc To verify given SCCs are not present in ULD 
	 * @param uldno
	 * @param sccValue
	 * @throws InterruptedException
	 * @throws AWTException
	 */
public void verifySCCsNotPresentInULD(String uldno,String[] sccValue) throws InterruptedException, AWTException {
		
		
		try{
		
		for(int i=0;i<sccValue.length;i++)
		{
			String locator = xls_Read.getCellValue(sheetName, "txt_outersccs;xpath");
			locator=locator.replace("ULD",data(uldno));
			String actscc=locator.replace("SCC", sccValue[i]);
			
			String scclist = xls_Read.getCellValue(sheetName, "btn_sccslist;xpath");
			scclist = scclist.replace("ULD", data(uldno));
			
			if(driver.findElements(By.xpath(actscc)).size()==1)
			{
				writeExtent("Fail", "Verified the scc "+sccValue[i]+" is present on "+screenName);

			}
			else if(driver.findElements(By.xpath(scclist)).size()!=0)	
			{
				driver.findElement(By.xpath(scclist)).click();
				waitForSync(1);
				String actscc2 = xls_Read.getCellValue(sheetName, "txt_innerscc;xpath");
				actscc2=actscc2.replace("SCC", sccValue[i]);
				if(driver.findElements(By.xpath(actscc2)).size()==1)
				{
					writeExtent("Fail", "Verified the scc "+sccValue[i]+" is present on "+screenName);
					driver.findElement(By.xpath(scclist)).click();
				}			
				else
				{
					writeExtent("Pass", "Verified the scc "+sccValue[i]+" is not present on "+screenName);
				        driver.findElement(By.xpath(scclist)).click();
				}			
				
			}
				else
				{
					writeExtent("Pass",  "Verified the scc "+sccValue[i]+" is not present on "+screenName);
				}
			
		waitForSync(2);
			}

		}
		
		catch(Exception e){
			
			writeExtent("Fail", "Failed to verify the sccs are not present on "+screenName);
		}	
		
	}




/**
	 * @author A-9847
	 * @Desc To verify the AWB Origin Destination Pair
	 * @param uld
	 * @param awb
	 * @param org
	 * @param dest
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyAWBOriginAndDestinationInsideULD(String uld, String awb, String org, String dest)throws InterruptedException, AWTException {
		
		try{
		String actOrg = xls_Read.getCellValue(sheetName, "txt_shipmentOrigin;xpath");
		actOrg = actOrg.replace("ULD", data(uld)).replace("AWB",data(awb));
		
		String actDest = xls_Read.getCellValue(sheetName, "txt_shipmentDestination;xpath");
		actDest = actDest.replace("ULD", data(uld)).replace("AWB",data(awb));
			
		String Origin=driver.findElement(By.xpath(actOrg)).getText();
    	String Destination=driver.findElement(By.xpath(actDest)).getText();
    	
    	
    	verifyScreenTextWithExactMatch(sheetName, data(org), Origin, "Shipment Origin", screenName);
    	verifyScreenTextWithExactMatch(sheetName, data(dest), Destination, "Shipment Destination", screenName);
		
		}
		
		catch(Exception e){
			
			writeExtent("Fail", "Failed to verify the AWB Origin Destination on" + screenName);
		}
	}
	/**
	 * @Description : Verifying Forward Suggestion details
	 * @author A-9175
	 * @param zoneVal
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyForwardSuggestionVal(String zoneVal) throws InterruptedException, AWTException {
		String actText = "";
		actText = getElementText(sheetName, "lbl_BreakdownInstructions;xpath", " Zone Details ", screenName);
		verifyScreenText(screenName, data(zoneVal), actText, " Zone Details ", "Verified Sucessfully");
		waitForSync(2);
	}

	/**
	 * @Description: Forward Suggestion add link
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickAddForwardSuggestionLink() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_addForwardSuggestion;xpath", "Forward Suggestion", screenName);
	}

	/**
	 * @Description: Forward Suggestion add link
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addForwardSuggestionLink() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_addForwardSuggestion;xpath", " ADD Forward Suggestion Link ", screenName);
	}

	/**
	 * @Description: Forward Suggestion add link
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addZoneBDN() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_lookUpIconBdnForwardSuggestion;xpath", " ADD ZONE ", screenName);
	}
	/**
	 * @author A-9847
	 * @Desc To click on Planning Complete Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickPlanningComplete() throws InterruptedException, IOException{
		
		clickWebElement(sheetName, "btn_planningComplete;id", "Planning Complete Button", screenName);
		waitForSync(6);
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify Reopen Button got Displayed on Clicking Planning Complete Button
	 */
	public void verifyPlanningisCompleted(){
		
		try{
			waitForSync(3);			
		if(driver.findElement(By.id(xls_Read.getCellValue(sheetName, "btn_reopen;id"))).isDisplayed())       
			writeExtent("Pass", "Planning Complete is Successfull and REOPEN button is displayed");	
			else
			writeExtent("Fail", "Planning complete is not successfull");	
		}
		catch(Exception e){
			writeExtent("Fail", "Planning complete is not successfull");
		}
		
	}

	/**
	 * @Description : Selecting zone
	 * @author A-9175
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void selectZONE(String pmyKey) throws InterruptedException {

		switchToFrame("frameName", "lovContainerFrame");
		System.out.println(data(pmyKey));
		selectTableRecord(data(pmyKey), "chk_selectZone;xpath", sheetName, 1);
		waitForSync(5);
		performKeyActions(sheetName, "btn_okForwardZone;id", "TAB", "Ok Button", screenName);
		clickWebElementByWebDriver(sheetName, "btn_okForwardZone;id", "Ok Button", screenName);
		waitForSync(5);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR367");

	}

	/**
	 * @Description : Verifying Forward Suggestion details after edit
	 * @author A-9175
	 * @param zoneVal
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyForwardSuggestionValAfterEdit(String zoneVal) throws InterruptedException, AWTException {
		String actText = "";
		actText = getElementText(sheetName, "lbl_afterForwardSuggestion;xpath", " Zone Details ", screenName);
		verifyScreenText(screenName, data(zoneVal), actText, " Zone Details ", "Verified Sucessfully");
		waitForSync(2);
	}

	/**
	 * @Description : Select override reasons and enter remarks in Violations
	 *              window and click on Ok button
	 * @author A-9478
	 * @param overrideReason
	 * @param remarks
	 * @throws Exception
	 */
	public void enterDetailsInViolations(String overrideReason, String remarks) throws Exception {
		waitForSync(2);
		switchToWindow("storeParent");
		switchToWindow("multipleWindows");
		selectValueInDropdown(sheetName, "list_OverrideReasons;id", data(overrideReason), "Overide reasons dropdown",
				"VisibleText");

		enterValueInTextbox(sheetName, "inbx_ViolationRemarks;id", data(remarks), "Flight Carrier code", screenName);
		clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR367");
		waitForSync(2);
	}
	/**
	 * @author A-9847
	 * @Desc To verify whether "Not in CPM" uld label is stamped or not
	 * @param ulds
	 */
	public void verifyNotInCpmLabel(String ulds[]){
		
		for(int i=0;i<ulds.length;i++){
			try{
			String locator=xls_Read.getCellValue(sheetName, "lbl_cpm;xpath").replace("*", data(ulds[i]));
			moveScrollBar(driver.findElement(By.xpath(locator)));
			waitForSync(2);
			hoverInWeb(locator);  
			String actText=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_tooltip;xpath").replace("*", data(ulds[i])))).getText();	
			verifyScreenTextWithExactMatch(screenName, "Not in CPM",actText, "ULD label Verification", "ULD Label");
			writeExtent("Pass", "Not in CPM label stamped for "+data(ulds[i]) +" on "+ screenName);
			}
			catch(Exception e){
			writeExtent("Fail", "Failed to stamp Not in CPM label for "+data(ulds[i]) +" on " + screenName);
			}
			
		}
	   
}
		
	public void hoverInWeb(String locator) {

		try {
			By element = By.xpath(locator);
			waitTillOverlayDisappear(element);
			WebElement ele = driver.findElement(By.xpath(locator));
			(new Actions(driver)).moveToElement(ele).perform();
		}

		catch (Exception e) {
			System.out.println("Not hovered on the object with locator : "
					+ locator);
		}
	}

	/**
	 * @author A-9175
	 * @Description: Check intact checkbox in OPR004
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void selectIntactCheckbox() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "chk_Intact;xpath", "Intact checkbox", screenName);
		waitForSync(2);
	}

	/**
	 * @author A-9478
	 * @param ULDno
	 * @param awbPre
	 * @param AwbNo
	 * @param manPcs
	 * @param manWgt
	 * @param Origin
	 * @param Destination
	 * @param statedPcs
	 * @param statedWgt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Adding new ULD from Import manifest screen
	 * @throws IOException
	 */

	public void addNewULDWithExistingAWB(String ULDno, String awbPre, String AwbNo, String manPcs, String manWgt)
			throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
		clickWebElement(sheetName, "btn_newULDAdd;id", "Add New ULD Button", screenName);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
		Actions act = new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
		waitForSync(5);
		clickWebElement(sheetName, "btn_ListAWB;xpath", "List AWB", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
		enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
		act.sendKeys(Keys.TAB).build().perform();
		String Oklocator = xls_Read.getCellValue(sheetName, "btn_Ok;xpath");
		waitForSync(2);
		clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
		waitForSync(2);
		/** Handling Validation error if any **/
		try {
			while (driver.findElements(By.xpath(Oklocator)).size() >= 1) {
				clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
				waitForSync(2);
				clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
				waitForSync(2);
			}
		} catch (Exception e) {
		}
		clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
		waitForSync(2);
	}

	/**
	 * @Description : Capture check sheet in a generic way
	 * @author A-9175
	 * @throws Exception
	 */

	public void checksheetCapture() throws Exception {

		switchToWindow("storeParent");

		waitForSync(3);
		try {
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR367");
			driver.switchTo().frame("popupContainerFrame");
			String locator = xls_Read.getCellValue(sheetName, "btn_Yesbutton;xpath");
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			for (WebElement elemnt : elements) {
				elemnt.click();
				waitForSync(3);
			}

			clickWebElement("BreakDown_OPR004", "btn_Save;id", "Ok Button", screenName);
			waitForSync(5);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "OK Button", screenName);
			switchToFrame("contentFrame", "OPR367");
			driver.switchTo().frame("popupContainerFrame");
			clickWebElementByWebDriver("BreakDown_OPR004", "btnCloseChecksheet;id", "Close button", screenName);
			waitForSync(5);
		}

		finally {
			waitForSync(2);

			switchToWindow("getParent");
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR367");
			waitForSync(5);
		}
	}

	/**
	 * @author A-9175
	 * @Description: Click on Breakdown and enter breakdown details in OPR004
	 * @param breakDownLoc
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void enterBreakdownDetails(String breakDownLoc, String rcvdPcs, String rcvdWt)
			throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_breakDown;xpath", "BreakDown Button", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_breakdownLocationCode;name", data(breakDownLoc), "BreakDown Location",
				screenName);
		enterValueInTextbox(sheetName, "inbx_recievedPcs;name", data(rcvdPcs), "Recieved pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_recievedWgt;name", data(rcvdWt), "Recieved weight", screenName);
		waitForSync(2);
	}

/**
	 * @author A-9847
	 * To select a particular breakdown instruction for the given uld
	 * @param uld
	 * @param instruction
	 */
	public void selectBDInstructionforULD(String uld,String instruction)
	{
	
		try{         

			String minimize = xls_Read.getCellValue(sheetName, "btn_minimizeAllDetails;xpath");
			if (driver.findElements(By.xpath(minimize)).size() > 0) {
				clickWebElement(sheetName, "btn_minimizeAllDetails;xpath", "Minimize ULD Details", screenName);
				waitForSync(2);
			}

			String xpath = xls_Read.getCellValue(sheetName, "btn_uldInstructionAdd;xpath");
			xpath=xpath.replace("*", data(uld));
			int size=driver.findElements(By.xpath(xpath)).size();
		
		
			//if size is 0,means some breakdown instruction got auto displayed
			if(size==0){

				String locatorBDKInstructionText = xls_Read.getCellValue(sheetName, "txt_bdkInstructionText;xpath");
				String actText = driver.findElement((By.xpath(locatorBDKInstructionText))).getText();
				System.out.println("ActText :"+actText);
				
				//verifying breakdown instruction stamped is same as expected
				if(actText.equals(data(instruction))){	
					writeExtent("Pass", "Expected breakdown instruction got auto-displayed as " + actText+ " for " +data(uld) +" on "+screenName + " Page");

				}

				else
				{
					String xpathIcon = xls_Read.getCellValue(sheetName, "btn_bdkInstructionIcon;xpath");
				
					writeExtent("Info", "Incorrect breakdown instruction got auto-displayed.User needs to select the BDK instruction as: " + actText+ " for " +data(uld) +" on "+screenName + " Page");
					moveScrollBar(driver.findElement(By.xpath(xpathIcon)));
					waitForSync(2);
					driver.findElement(By.xpath(xpathIcon)).click();
					waitForSync(2);

					String locator = xls_Read.getCellValue(sheetName, "btn_breakdownInstruction;xpath");
					locator = locator.replace("Mode", data(instruction));       
					driver.findElement(By.xpath(locator)).click();      
					waitForSync(2);     
					driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_uldRemarks;xpath").replace("*", data(uld)))).click(); 
					writeExtent("Pass", "Selected Breakdown Instruction as " + data(instruction)+ " for " +data(uld) +" "+screenName + " Page");

				}	

			}

			else{

				writeExtent("Info", "User needs to select the BDK instruction as it is not auto-displayed for " +data(uld) +" on "+screenName + " Page");
				moveScrollBar(driver.findElement(By.xpath(xpath)));
				waitForSync(2);
				driver.findElement(By.xpath(xpath)).click();
				waitForSync(2);

				String locator = xls_Read.getCellValue(sheetName, "btn_breakdownInstruction;xpath");
				locator = locator.replace("Mode", data(instruction));       
				driver.findElement(By.xpath(locator)).click();      
				waitForSync(2);     
				driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_uldRemarks;xpath").replace("*", data(uld)))).click(); 
				writeExtent("Pass", "Selected Breakdown Instruction as " + data(instruction)+ " for " +data(uld) +" "+screenName + " Page");
			}

		} catch (Exception e) {
			writeExtent("Fail", "Not Selected Breakdown Instruction as " + data(instruction)+" for " +data(uld) +" "+screenName + " Page");
		}


		
			}

	/**
	 * @Description: Save in OPR004
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void SaveDetailsInOPR004() throws InterruptedException, AWTException {

		clickWebElementByWebDriver(sheetName, "btn_saveBreakdown;xpath", "Save Button", screenName);
		waitForSync(3);
	}

	/**
	 * @author A-7271
	 * @Description : click breakdown complete button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBreakdownComplete() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_breakDownComplete;name", "BreakDown Button", screenName);
		waitForSync(5);
	}

	/**
	 * @Description : Used to Select specified shipment
	 * @author A-9175
	 * @param pmyKey
	 * @throws InterruptedException
	 */

	public void clickCheckBox(String pmyKey) throws InterruptedException {

		selectTableRecord(data(pmyKey), "chk_selectShipment;xpath", sheetName, 1);
		waitForSync(1);

	}

	/**
	 * @Description : Used to click and breakdown and and enter breakdown
	 *              details
	 * @author A-9175
	 * @param breakDownLoc
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickBreakDownandBreakdownComplete(String breakDownLoc, String rcvdPcs, String rcvdWt)
			throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_breakDown;xpath", "BreakDown Button", screenName);
		waitForSync(5);
		String locator = xls_Read.getCellValue(sheetName, "chk_Intact;xpath");
		if ((driver.findElement(By.xpath(locator)).getAttribute("checked") == null)) {
			enterValueInTextbox(sheetName, "inbx_breakdownLocationCode;name", data(breakDownLoc), "BreakDown Location",
					screenName);
			enterValueInTextbox(sheetName, "inbx_recievedPcs;name", data(rcvdPcs), "Recieved pcs", screenName);
			enterValueInTextbox(sheetName, "inbx_recievedWgt;name", data(rcvdWt), "Recieved weight", screenName);
			waitForSync(2);
			clickWebElement(sheetName, "btn_breakDownComplete;name", "BreakDown Button", screenName);
			waitForSync(5);
		}

	}

	/**
	 * @Description : Used to close flight
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void closeFlight() throws InterruptedException, AWTException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_closeFlight;id", "Close Flight", screenName);
		waitForSync(3);
		try {
			while (driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).isDisplayed()) {
				
				driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).click();
				waitForSync(2);

			}
		} catch (Exception e) {
		}

	}
	/**
	 * @Description : Close flight and message verifications
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void closeFlight(String... msg) throws InterruptedException, AWTException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_closeFlight;id", "Close Flight", screenName);
		waitForSync(3);
		int noOfMsgs=0;
		
	

		try {
			while (driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).isDisplayed()) {
			

				for(String message:msg)
				{
					String alertTxt=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_WarningMessageAfterCloseFlight;xpath"))).getText();
					if(alertTxt.contains(message))
					{

						writeExtent("Pass","Message '"+alertTxt+"'  triggered while closing the flight on "+screenName);
						noOfMsgs=noOfMsgs+1;
						driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).click();
						waitForSync(5);
						

					}
					else
					{
						driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).click();
						waitForSync(5);
						break;
					}

				}
			
					
			
						
			
				
				
				

			
			if(noOfMsgs!=msg.length)
			{
				writeExtent("Fail","Expected Message '"+Arrays.asList(msg)+"' not triggered while closing the flight on "+screenName);
			}
			
		
			}
		}

		 catch (Exception e) {
			if(noOfMsgs!=msg.length)
			{

				writeExtent("Fail","Expected Message '"+Arrays.asList(msg)+"' not triggered while closing the flight on "+screenName);
			}
		}

	}
	/**
	 * @author A-9175
	 * @Description : Clicking Checkbox
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void clickCheckBox_ULD(String pmyKey) throws InterruptedException {

		System.out.println(pmyKey);
		selectTableRecordJS(pmyKey, "chk_selectShipment;xpath", sheetName, 1);
		waitForSync(1);

	}

	/**
	 * @Description : Listing ULD awith awbo by clicking Add new ULD
	 * @author A-9175
	 * @param ULDno
	 * @param awbPre
	 * @param AwbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickandListAddNewULD(String ULDno, String awbPre, String AwbNo)
			throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
		clickWebElement(sheetName, "btn_newULDAdd;id", "Add New ULD Button", screenName);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
		Actions act = new Actions(driver);
		act.sendKeys(Keys.TAB).build().perform();
		waitForSync(5);
		clickWebElement(sheetName, "btn_ListAWB;xpath", "List AWB", screenName);
		waitForSync(3);

	}

	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 * @Description: Click flag flight
	 */
	public void clickFlagFlight() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_flagFlight;id", "Flag Flight", screenName);
		waitForSync(5);
	}

	/**
	 * @author A-9175
	 * @throws Exception 
	 * @Description : Clicking Breakdown Button
	 */
	public void clickBreakdownButton() throws Exception {
		String actText = getElementText(sheetName, "lbl_BreakdownInstructions;xpath", " BDN InstructionTag ", screenName);
		System.out.println(actText);
		waitForSync(5);
		clickWebElement(sheetName, "btn_breakDown;xpath", "BreakDown Button", screenName);
		waitForSync(5);
		if((actText.trim().equals("Thru unit"))||(actText.trim().equals("Intact Unit"))){
			enterThruBreakdownLocation();
		}
	}

	/**
	 * @author A-9175
	 * @Description: Check Thru checkbox in OPR004
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void selectThruCheckbox() throws InterruptedException, AWTException {
		waitForSync(5);
		clickWebElementByWebDriver(sheetName, "chk_thru;xpath", "Thru checkbox", screenName);
		waitForSync(2);
	}

	/**
	 * @Description : Clicking Yes Button
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickYesButton() throws InterruptedException, IOException {
		switchToFrame("default");
		clickWebElement(GenericSheet, "btn_Yes;xpath", "Yes Button", screenName);
		waitForSync(1);
		switchToFrame("contentFrame", "OPR367");
	}

	/**
	 * @Description : Close from OPR004 screen
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void closeFromOPR004() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName2, "btn_close;name", "Beeakdown Close Button", screenName);
		waitForSync(4);
		waitTillScreenload(sheetName, "txt_remarks;xpath", "Remarks", screenName);	
	}

	/**
	 * @Description : clicking more options for specific ULD
	 * @author A-9175
	 * @param ULDno
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickOptionForULD(String ULDno) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		try {
			String locator = xls_Read.getCellValue(sheetName, "btn_options;xpath");
			locator = locator.replace("ULDnum", data(ULDno));
			driver.findElement(By.xpath(locator)).click();
			System.out.println(locator);
			waitForSync(3);
			writeExtent("Pass", "Clicked on More options button" + screenName);
		} catch (Exception e) {
			writeExtent("Fail", "Could not Click on More options button" + screenName);
		}

	}

	/**
	 * @Description : entering different breakdown locations for different AWB
	 * @author A-9175
	 * @param numberOfShipments
	 * @param breakDownLoc
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void enterBreakdownInDifferentLoc(int numberOfShipments, String breakDownLoc)
			throws InterruptedException, AWTException, IOException {
		waitForSync(5);

		for (int i = 0; i < numberOfShipments; i++) {
			String locator = xls_Read.getCellValue(sheetName, "inbx_locationBreakdown;id");
			String shipment = String.valueOf(i);
			locator = locator.replace("val", shipment);
			driver.findElement(By.id(locator)).sendKeys(breakDownLoc.split(",")[i]);
			waitForSync(3);

			keyPress("TAB");
			keyRelease("TAB");
			waitForSync(2);
		}
		waitForSync(5);

	}

	/**
	 * @Description: Verifying block image
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyBlockImage() throws InterruptedException, AWTException {
		verifyElementDisplayed(sheetName, "btn_blockImg;xpath", " Block Image ", screenName, "Block image ");
		waitForSync(2);
	}

	/**
	 * @Description adding new AWB Details
	 * @author A-9175
	 * @param ULDno
	 * @param awbPre
	 * @param AwbNo
	 * @param manPcs
	 * @param manWgt
	 * @param Origin
	 * @param Destination
	 * @param statedPcs
	 * @param statedWgt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addNewULDDetails(String ULDno, String awbPre, String AwbNo, String manPcs, String manWgt, String Origin,
			String Destination, String statedPcs, String statedWgt)
			throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
		clickWebElement(sheetName, "btn_newULDAdd;id", "Add New ULD Button", screenName);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
		performKeyActions(sheetName, "inbx_awbNumber;id", "TAB", "AWB Num", screenName);
		waitForSync(5);
		try {
			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(2);
		} catch (Exception e) {
		}
		enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
		enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
		enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(Origin), " Origin ", screenName);
		enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(Destination), " Destination ", screenName);
		waitForSync(5);

		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div[@id='react-select-3--value']")));
		act.click().build().perform();
		act.sendKeys(Keys.ARROW_DOWN).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_statedPcs;id", data(statedPcs), " Stated Pieces ", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_statedWgt;id", data(statedWgt), " Stated Weight ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
		clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
	}

	/**
	 * @Description : Handling alerts with expected text
	 * @author A-9175
	 * @param expAlert
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void handleGeneralAlert(String expAlert) throws InterruptedException, AWTException, IOException {
		String alertText = "";
		String alertTextLocator = xls_Read.getCellValue(sheetName, "txt_alertText;xpath");
		String Oklocator = xls_Read.getCellValue(sheetName, "btn_Ok;xpath");
		try {
			while (driver.findElements(By.xpath(Oklocator)).size() >= 1) {
				waitForSync(5);
				alertText = driver.findElement(By.xpath(alertTextLocator)).getText();

				if (alertText.contains(expAlert)) {
					waitForSync(5);
					clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
					waitForSync(5);
					System.out.println("Entered");
					writeExtent("Pass", "Accepted Alert with text " + alertText + " on " + screenName + " Screen");
					waitForSync(2);
					break;

				} else {
					clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
					waitForSync(3);
					clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
				}
			}
			waitForSync(5);
			clickWebElement(sheetName, "btn_closeWindow;xpath", "Close Button", screenName);
			waitForSync(3);
		} catch (Exception e) {
		}
		waitForSync(2);

	}

	/**
	 * @Description : Click on Modify
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickModify() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_modifyButton;id", " Modify ", screenName);
		waitForSync(3);
	}

	/**
	 * @Description : Click Ok for adding New ULD
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickOkAddULD() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
		waitForSync(3);
	}

	/**
	 * @Description : Modify shipment details
	 * @author A-9175
	 * @param manPcs
	 * @param manWgt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void modifyDetails(String manPcs, String manWgt) throws InterruptedException, AWTException, IOException {

		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
		enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
		clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
	}
public void verifyWarningMessageWith2AWBsAfterCloseFlight(String awb1,String awb2) throws InterruptedException {
		
		String s1 = "2 AWB(s) , " + data("CarrierNumericCode") + "-" + data("awb1") + ", "
				+ data("CarrierNumericCode") + "-" + data("awb2") + ".";
		String s2 = "2 AWB(s) , " + data("CarrierNumericCode") + "-" + data("awb2") + ", "
				+ data("CarrierNumericCode") + "-" + data("awb1") + ".";
		String actualtext = getElementTextnoFrameSwitch(sheetName, "txt_WarningMessageAfterCloseFlight;xpath",
				"Warning message", screenName);
		if (actualtext.contains(s1) || actualtext.contains(s2)) {
			writeExtent("Pass",
					"Successfully verified warning message as '" +actualtext+ "' on "+ screenName);
		} else {
			writeExtent("Fail",
					"Failed to verify warning message with 2 awbs after clicking on Close Flight in" + screenName);
		}
	
	
	}



public void clickOK() throws InterruptedException, AWTException, IOException {

			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(4);
		}
	/**
	 * @Description : Adding new awb to existing shipment
	 * @author A-9175
	 * @param awbPre
	 * @param AwbNo
	 * @param manPcs
	 * @param manWgt
	 * @param Origin
	 * @param Destination
	 * @param statedPcs
	 * @param statedWgt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addNewAWBtoExistingULD(String awbPre, String AwbNo, String manPcs, String manWgt, String Origin,
			String Destination, String statedPcs, String statedWgt)
			throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
		waitForSync(5);
		try {
			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(2);
		} catch (Exception e) {
		}
		enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
		enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
		enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(Origin), " Origin ", screenName);
		enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(Destination), " Destination ", screenName);
		waitForSync(5);

		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div[@id='react-select-3--value']")));
		act.click().build().perform();
		act.sendKeys(Keys.ARROW_DOWN).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_statedPcs;id", data(statedPcs), " Stated Pieces ", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_statedWgt;id", data(statedWgt), " Stated Weight ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
		String Oklocator = xls_Read.getCellValue(sheetName, "btn_Ok;xpath");

		/** Handling Validation error if any **/
		try {
			while (driver.findElements(By.xpath(Oklocator)).size() >= 1) {
				clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
				waitForSync(3);
				clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
			}
		} catch (Exception e) {
		}
		waitForSync(2);
		clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
	}

	/**
	 * @Description : verifying awbs under ULD/Bulk
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyShipment(String awbNo) throws InterruptedException, AWTException {
		waitForSync(2);
		try {
			String locator = xls_Read.getCellValue(sheetName, "txt_AWBnoVerify;xpath");

			locator = locator.replace("awbNo", data(awbNo));

			driver.findElement(By.xpath(locator)).isDisplayed();
			waitForSync(2);
			writeExtent("Pass", "Successfully Verified the AWB" + data(awbNo) + " In " + screenName);
		} catch (Exception e) {
			writeExtent("Fail", "Could not Verify the AWB " + data(awbNo) + " In " + screenName);
		}
		waitForSync(2);

	}

	/**
	 * @Description: Expand ULD
	 * @author A-9175
	 * @param uldNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void expandUld(String uldNo) throws InterruptedException, AWTException {
		waitForSync(2);
		try {
			String expandLoc = xls_Read.getCellValue(sheetName, "btn_expandShipment;xpath");
			expandLoc = expandLoc.replace("uldNo", uldNo);
			driver.findElement(By.xpath(expandLoc)).click();
			waitForSync(2);
			writeExtent("Pass", "Successfully Clicked on Expand in  " + screenName);
		} catch (Exception e) {
			writeExtent("Fail", "Could not Click on Expand in  " + screenName);
		}
		waitForSync(2);

	}

	/**
	 * @Description : Verifying Block image is not displayed
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyBlockImageNotDisplayed() throws InterruptedException, AWTException {

		try {
			String locator = xls_Read.getCellValue(sheetName, "btn_blockImg;xpath");
			driver.findElement(By.xpath(locator)).isDisplayed();
			verifyElementDisplayed(sheetName, "btn_blockImg;xpath", " Block Image ", screenName, "Block image ");
			waitForSync(2);
			writeExtent("Fail", "Block Image found in  " + screenName + " Screen ");
		} catch (Exception e) {
			writeExtent("Pass", "Block Image not found in  " + screenName + " Screen ");
		}
	}

	/**
	 * @Description : Updating ULD number
	 * @author A-9175
	 * @param uldNum
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void CorrectUldNum(String uldNum) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_corrctUldNum;xpath", " Correct ULD Number ", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_newUldNumber;id", data(uldNum), "ULD Number", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_UpdateuldNum;id", "Update ULD number", screenName);
		waitForSync(2);
	}

	/**
	 * @Description : Updating ULD number
	 * @author A-9175
	 * @param uldNum
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void CorrctUldNum(String uldNum) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_corrctUldNum;xpath", "Corrct ULD Number ", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_newUldNumber;id", data(uldNum), "ULD Number", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_UpdateuldNum;id", "Update ULD number", screenName);
		waitForSync(2);
	}

	/**
	 * @author A-9175
	 * @param ULDno
	 * @param awbPre
	 * @param AwbNo
	 * @param manPcs
	 * @param manWgt
	 * @param Origin
	 * @param Destination
	 * @param statedPcs
	 * @param statedWgt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Adding new ULD from Import manifest screen
	 * @throws IOException
	 */

	public void addNewULD(String ULDno, String awbPre, String AwbNo, String manPcs, String manWgt, String Origin,
			String Destination, String statedPcs, String statedWgt)
			throws InterruptedException, AWTException, IOException {

		clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
		clickWebElement(sheetName, "btn_newULDAdd;id", "Add New ULD Button", screenName);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefixr ", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
		waitForSync(5);
		try {
			clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
			waitForSync(2);
		} catch (Exception e) {
		}
		enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
		enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
		enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(Origin), " Origin ", screenName);
		enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(Destination), " Destination ", screenName);
		waitForSync(5);

		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//div[@id='react-select-3--value']")));
		act.click().build().perform();
		act.sendKeys(Keys.ARROW_DOWN).build().perform();
		act.sendKeys(Keys.ENTER).build().perform();
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_statedPcs;id", data(statedPcs), " Stated Pieces ", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_statedWgt;id", data(statedWgt), " Stated Weight ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
		String Oklocator = xls_Read.getCellValue(sheetName, "btn_Ok;xpath");

		/** Handling Validation error if any **/
		try {
			while (driver.findElements(By.xpath(Oklocator)).size() >= 1) {
				clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
				waitForSync(3);
				clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
			}
		} catch (Exception e) {
		}
		waitForSync(2);
		clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
	}

	/**
	 * @Description : Click edit For Special Note
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void clickEditSpecialNote() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_editSpecialNote;xpath", " Edit Button Special Note ", screenName);
	}

	/**
	 * @Description : Click edit For FWD Suggestion
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickEditFWDSuggestion() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_editFWDSuggestion;xpath", " Edit Button Special Note ", screenName);
	}

	/**
	 * @Description : Updating a zone
	 * @author A-9175
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void updateZONE(String pmyKey) throws InterruptedException {

		switchToFrame("frameName", "lovContainerFrame");
		clickWebElementByWebDriver(sheetName, "btn_ClearInFWDSuggestionEdit;id", "Ok Button", screenName);
		waitForSync(5);
		clickWebElementByWebDriver(sheetName, "btn_listFWDSuggestion;id", "Ok Button", screenName);
		waitForSync(3);
		System.out.println(data(pmyKey));
		selectTableRecord(data(pmyKey), "chk_selectZone;xpath", sheetName, 1);
		waitForSync(5);
		clickWebElementByWebDriver(sheetName, "btn_OkforEditLocationInFWDSuggestion;id", "Ok Button", screenName);
		waitForSync(5);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR367");

	}

	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : To save details
	 * @throws IOException
	 */

	public void SaveDetails() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_Save;id", "Save Button", screenName);
		waitForSync(6);
	}
	
	
	/**
	 * @Description : add uld info
	 * @author A-6260
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

		public void addNewULDInfo(String ULDno, String awbPre, String AwbNo,String ownerCode, String manPcs, String manWgt, String Origin,
				String Destination, String statedPcs, String statedWgt,int sciIndex)
				throws InterruptedException, AWTException, IOException {
			
			//click add ULD button

			clickWebElement(sheetName, "btn_addUld;id", "Add New ULD Button", screenName);
			waitForSync(5);
			
			// Capture  New ULD number
			enterValueInTextbox(sheetName, "inbx_newULD;xpath", data(ULDno), " ULD Number ", screenName);
			clickWebElement(sheetName, "btn_newULDAdd;id", "Add New ULD Button", screenName);
			
			//Capture New AWB Number
			
			waitForSync(2);
			enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(awbPre), " AWB Prefix ", screenName);
			enterValueInTextbox(sheetName, "inbx_awbNumber;id", data(AwbNo), " AWB Number ", screenName);
			waitForSync(2);
			
			try {
				//Checking for ok for fresh AWB number
				clickWebElementByWebDriver(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
				
				waitForSync(2);
			} catch (Exception e) {
			}
			
			
			
			//Verify the custom info as select
			verifySelectAsCustomInfoSCI();
			
			//Capturing Manifest Pcs and Wgt Information
			enterValueInTextbox(sheetName, "inbx_manifestedPcs;id", data(manPcs), " Manifested Pieces ", screenName);
			performKeyActions(sheetName, "inbx_manifestedPcs;id", "TAB", "AWB Num", screenName);
			enterValueInTextbox(sheetName, "inbx_manifestedWgt;id", data(manWgt), " Manifested weight ", screenName);
			
			//Capturing Origin and Destination
			
			enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(Origin), " Origin ", screenName);
			enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(Destination), " Destination ", screenName);
			waitForSync(1);
			
			//Capturing Stated Pieces and Weight Information
			
			enterValueInTextbox(sheetName, "inbx_statedPcs;id", data(statedPcs), " Stated Pieces ", screenName);
			waitForSync(1);
			enterValueInTextbox(sheetName, "inbx_statedWgt;id", data(statedWgt), " Stated Weight ", screenName);

			//Selecting SCI Info
			waitForSync(2);
			clickWebElementByWebDriver(sheetName, "lst_SCI;xpath", "List BDN", screenName);
			waitForSync(3);
			try
			{
				for(int i=0;i<sciIndex;i++)
				{
					keyPress("DOWN");
				}
				keyPress("ENTER");
				writeExtent("Pass", "SCI : Index : "+sciIndex+" successfully selected"+ screenName + " Page");
			}catch (Exception e) {
				writeExtent("Fail", "SCI : Index : "+sciIndex+" could not be selected"+ screenName + " Page");
			}

			waitForSync(2);
			clickWebElement(sheetName, "btn_addAWB;id", "Add New ULD Button", screenName);
			waitForSync(2);
			clickWebElement(sheetName, "btn_popUpOK;id", "OK Button", screenName);
		}
	/**
	 * @author A-9175
	 * @param status
	 * @throws InterruptedException
	 * @Description : Verifying Nill Manifest details
	 */
	public void verifyNilDetails(String status) throws InterruptedException {
		getTextAndVerify(sheetName, "txt_nilManifest;xpath", "Nil Manifest", screenName, "Nil Manifest Details",
				data(status), "equals");
	}

	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Checking Nil Check box
	 */
	public void checkNil() throws InterruptedException, AWTException {
		waitForSync(5);
		selectMultipleCheckboxes(sheetName, "chk_nilManifest;xpath");
		waitForSync(2);
	}

	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Deleting Selected ULD
	 * @throws IOException
	 */
	public void deleteULD() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_deleteUld;id", "Delete ULD ", screenName);
		waitForSync(5);
		clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", screenName);
		waitForSync(2);
	}

	/**
	 * @author A-9175
	 * @param uld
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Verifying Break down green tick mark
	 */
	public void verifyBreakdownSuccessfullImage() throws InterruptedException, AWTException {
		verifyElementDisplayed(sheetName, "img_BDN;xpath", " BDN Success ", screenName,
				" BreakDown Successfull image ");
		waitForSync(2);
	}

	/**
	 * @author A-9175
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
			switchToFrame("contentFrame", "OPR367");
		}
	}

	/**
	 * @author A-9175
	 * @param mpcs
	 * @param mWgt
	 * @param rpcs
	 * @param rwgt
	 * @param spcs
	 * @param swgt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Verifying Manifested Details in Import Screen
	 */

	public void verifyManifestedDetails(String mpcs, String mWgt, String spcs, String swgt)
			throws InterruptedException, AWTException {
		waitForSync(2);
		getTextAndVerify(sheetName, "txt_ManifestedPcsCount;xpath", "Manifested Pieces", screenName,
				"Manifested Pieces", data(mpcs), "equals");
		waitForSync(2);
		getTextAndVerify(sheetName, "txt_ManifestedWgtCount;xpath", "Manifested Weight", screenName,
				"Manifested Weight", data(mWgt), "equals");
		waitForSync(2);
		getTextAndVerify(sheetName, "txt_statedPcsCount;xpath", "Stated Pieces", screenName, "Stated Pieces",
				data(spcs), "equals");
		waitForSync(2);
		getTextAndVerify(sheetName, "txt_statedWgtCount;xpath", "Stated Weight", screenName, "Stated Weight",
				data(swgt), "equals");
	}

	/**
	 * @Description : Verifying ULD is highlighted or not as part of found cargo
	 * @author A-9175
	 * @param ULDNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyULDFontClass(String ULDNo) throws InterruptedException, AWTException {
		String locator = xls_Read.getCellValue(sheetName, "notifyBar;xpath");
		locator = locator.replace("ULDNo", data(ULDNo));
		try {
			WebElement notifyBar = driver.findElement(By.xpath(locator));
			String c = notifyBar.getCssValue("color");
			// convert the color from string type to hexa form
			String ColorasHex = Color.fromString(c).asHex();
			System.out.println("hexadecimal format : " + ColorasHex);
			if (ColorasHex.equals("#06c400")) {
				writeExtent("Fail", "verified as " + data(ULDNo) + " is Highlighted in " + screenName);
			} else {
				writeExtent("Pass", "verified as " + data(ULDNo) + " is Not Highlighted in " + screenName);

			}
		} catch (Exception e) {
			writeExtent("Fail", data(ULDNo) + " found in " + screenName);
		}

	}

	/**
	 * @Description Verify Popup
	 * @author A-9175
	 * @param shouldAppear
	 */
	public void verifyPopUpAppears(boolean shouldAppear) {
		if (shouldAppear) {
			try {
				String xpath = xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath");
				driver.findElement(By.xpath(xpath)).click();
				writeExtent("Pass", "PopUp was displayed and handled successfully");
			} catch (Exception e) {
				System.out.println("PopUp was not displayed on " + screenName + " Page");
				writeExtent("Fail", "PopUp was not displayed on " + screenName + " Page");

			}
		} else {
			try {
				String xpath = xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath");
				driver.findElement(By.xpath(xpath)).click();
				writeExtent("Pass", "PopUp was displayed and handled successfully");
			} catch (Exception e) {
				System.out.println("PopUp was not displayed on " + screenName + " Page");
				writeExtent("Fail", "PopUp was not displayed on " + screenName + " Page");

			}
		}
	}

	/**
	 * @author A-9175
	 * @param uld
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : enter shipment number ie,Bulk or ULD num ber in search
	 *              text box
	 */
	public void enterShipmentDetailsInSearchBox(String uld) throws InterruptedException, AWTException {
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_SearchAwb;xpath", data(uld), "Search Element ", screenName);
		waitForSync(5);

	}

	/**
	 * @Description : Clicking Flag flight button
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void flagFlight() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_flagFlight;id", "Flag Flight", screenName);
		waitForSync(5);
		try {
			while (driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).isDisplayed()) {
				driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Ok;xpath"))).click();
			}
		} catch (Exception e) {
		}

	}
}
