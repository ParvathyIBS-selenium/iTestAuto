package screens;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.text.ParseException;
import java.util.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;



public class CaptureAWB_OPR026 extends CustomFunctions {

	public CaptureAWB_OPR026(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "CaptureAWB_OPR026";
	public String screenName = "CaptureAWB";
	String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
	String cxmlPropPath = "\\src\\resources\\CXML.properties";
	String grouping = "\\src\\resources\\Grouping.properties";
	MaintainListCommodity_SHR015 shr015 = new MaintainListCommodity_SHR015(driver, excelreadwrite, xls_Read);
	AWBClearance_OPR023 OPR023 = new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
	//private static Logger logger=LogManager.getLogger(CaptureAWB_OPR026.class);
	
	/**
	 * Description... To select SCI value from the dropdown
	 * @param SCI: test data column name for SCI
	 * @throws InterruptedException
	 */
	public void selectSCI(String SCI) throws InterruptedException {
		Thread.sleep(2000);
		selectValueInDropdown(sheetName, "lst_SCI;xpath", data(SCI), "SCICode", "VisibleText");
		Thread.sleep(2000);
		//logger.info("");
		
	}
	/**
	* Description... Click securityscreen Button
	* @author A-10330
	* @throws InterruptedException
	* @throws IOException
	*/
	public void clickSecurityScreening() throws InterruptedException, IOException
	{
	clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Security & Screening Button", screenName);
	waitForSync(3);

	}
	/**
	 * @author A-8783
	 * Desc - Verify LAT is stamped
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verfyLATStamped() throws InterruptedException, IOException{
		try
		{
			
		
		clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
		waitForSync(3);
		driver.switchTo().frame("popupContainerFrame");
		int size=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).size();
		if(size==1){
			writeExtent("Pass", "LAT Details are present on "+screenName);
		}
		else{
			writeExtent("Fail", "LAT Details are not present on "+screenName);
		}
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR026");
		handleShipmentStatusPopUp();
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not verify LAT on "+screenName);
		}

	}	
	/**
	 * @author A-8783
	 * Desc - Verify flight is stamped
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verfyFlightDetails(String flightNo) throws InterruptedException, IOException{
		try
		{


			clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
			waitForSync(3);
			driver.switchTo().frame("popupContainerFrame");
			String flighInfo =driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_flight;xpath"))).getText();
			if(flighInfo.contains(flightNo)){
				writeExtent("Pass", "Flight Details are present on "+screenName);
			}
			else{
				writeExtent("Fail", "Flight Details are not present on "+screenName);
			}
			switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameOPR026");
			handleShipmentStatusPopUp();
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not verify flight details  on "+screenName);
		}

	}	


	/**
	 * Description : click clear button
	 * @author A-10330
	 * @throws InterruptedException
	 */

	public void clickClearButton() throws InterruptedException, IOException {
		try{

			waitTillScreenload("CaptureAWB_OPR026", "btn_clear;name", "Clear Button", screenName);
			clickWebElement("CaptureAWB_OPR026", "btn_clear;name", "Clear Button", screenName);


		}
		catch (Exception e) {
			test.log(LogStatus.FAIL, "Could not click on the clear button "+screenName);

		}

	}

	/**
	 * @author A-10690
	 * Desc - Verify LAT updated in shipment status
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verfyLATUpdatedInShipmentStatus(String date) throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
		waitForSync(3);
		driver.switchTo().frame("popupContainerFrame");
		int size=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).size();
				
		if(size==1){
			String text=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).getText();
			
			if(text.contains(data(date)))
			{
				
				writeExtent("Pass", "LAT  got updated as expected on "+screenName);
			}
			else
				writeExtent("Fail", "LAT  not getting updated on "+screenName);
		}
		else{
			writeExtent("Fail", "LAT Details are not present on "+screenName);
		}
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR026");
		handleShipmentStatusPopUp();
		
	}
	/**
	 * @author A-9844
	 * @param expStatus
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verify security status
	 */
	
	public void verifySecurityStatus(String expStatus,String expText) throws InterruptedException, IOException  {	
		
		clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
		waitForSync(3);
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);
		String actStatus = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_inerror;xpath"))).getText();
		verifyScreenTextWithExactMatch(sheetName, expStatus, actStatus, "Verify security status error", "Capture AWB");
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_securityStatusText;xpath"))).getText();
		verifyScreenTextWithExactMatch(sheetName, expText, actText, "Verify security status text", "Capture AWB");
		
	}
	/**
	 * Description... To verify the value in customs information is autopoulated after giving parameter
	 * @param RowNumber
	 * @param customsAuthority
	 * @param parameter
	 * @param value
	 * @throws IOException 
	 * @throws AWTException 
	 */
	public void verifyTSDvalue(String RowNumber, String customsAuthority,
			String parameter, String value) throws IOException, AWTException {
		int n = Integer.parseInt(data(RowNumber));

		for (int i = 0; i <= n; i++) {

			try {
				clickWebElement(sheetName, "clk_Add;xpath", "Add Button",
						screenName);
				Thread.sleep(2000);
				selectValueInDropdownWthXpath(
						"(//select[@name='customsAuthority'])[" + (i + 1) + "]",
						data("customsAuthority"), "customs Authority", "VisibleText");
				waitForSync(3);
				selectValueInDropdownWthXpath(
						"(//select[@name='customsParameter'])[" + (i + 1) + "]",
						data("parameter"), "parameter", "VisibleText");
				
				keyPress("TAB");
				String locator=xls_Read.getCellValue(sheetName, "txt_customvalue;xpath");
				locator=locator.replace("*", data(parameter));
				String actText = driver.findElement(By.xpath(locator)).getAttribute("value");
				
				String expText = value;
				
				if(actText.equals(expText)){
					writeExtent("Pass", "Successfully verified custom value "+screenName);
					
				}
				
			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

	}	
	
	/**
	 * @author A-9847
	 * @Desc To clear the IATA charge and Chargeable weight and reenter the Chargeable weight
	 * @param Chargeablewgt
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void clearIATAChargeAndUpdateChargeableWgt(String Chargeablewgt) throws InterruptedException, AWTException{
		
	clearText(sheetName, "inbx_ratelineIataCharges;xpath", "IATACharge", screenName);
	clearText(sheetName, "inbx_chrgWght;name", "Chargeable Weight", screenName);
    waitForSync(1);
	enterValueInTextbox(sheetName, "inbx_chrgWght;name", data(Chargeablewgt), "Chargeable Weight", screenName);
	waitForSync(1);
	keyPress("TAB");
	keyPress("TAB");
		
	}
	/**
	 * @author A-9844
	 * Description... 	enter NSC as SCC if not present
	 * @param scc
	 * @throws InterruptedException
	 */
	public void enterNSCasSCC(String scc) throws InterruptedException{

		try{
		By element = getElement(sheetName, "txt_sccText;xpath");
		String actText = driver.findElement(element).getAttribute("value");
		String expText = data(scc);
		
		if(!actText.contains(expText)){
			
			String actSCC=actText+","+expText;
			
			enterValueInTextbox(sheetName, "txt_sccText;xpath", actSCC, "scc",screenName);
			waitForSync(3);
			writeExtent("Pass", "Successfully entered the SCC on "+screenName);
		}
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not enter the SCC on "+screenName);
		}

	}

	/**
	 * @author A-9847
	 * @Desc To verify the Status in green/red on Check Status Pop-up
	 * @param status
	 * @param colour
	 * @throws InterruptedException
	 * @throws IOException
	 */
public void checkStatus(String status,String colour) throws InterruptedException, IOException {
		
		try {
			String col;
			if(colour.equals("close"))
				col="red";
			else
				col="green";
			
	    waitForSync(2);
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(1);
		String locator=xls_Read.getCellValue(sheetName, "img_securityStatus;xpath");
		locator=locator.replace("*", data(status));
		String actColour=driver.findElement(By.xpath(locator)).getAttribute("class");	
		waitForSync(2);
		if(driver.findElement(By.xpath(locator)).isDisplayed() && actColour.contains(colour))  		
		
			writeExtent("Pass", "Successfully verified the status as " + data(status) + " in " +col +" on "+screenName);	
		else	
			writeExtent("Fail", "Failed to verify the status as " + data(status) + " in " +col +" on "+screenName);	
		}
		
		catch(Exception e) {
			writeExtent("Fail", "Could not verify the status as " + data(status) + " on " + screenName);
		}
		finally {
		
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		clickWebElement(sheetName, "btn_chkShipmentStatus;xpath", "Check Shipment Status", screenName);
		waitForSync(2);
		}
		

	
}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Status of Section as Validated/IN error on Check Status Pop-up
	 * @param section
	 * @param status
	 * @throws InterruptedException
	 * @throws IOException
	 */
      public void checkSectionStatus(String section,String status) throws InterruptedException, IOException {
		
		try {
	    waitForSync(2);
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(1);
		String locator=xls_Read.getCellValue(sheetName, "txt_sectionStatus;xpath");
		locator=locator.replace("*", data(section));
		
		String actStatus=driver.findElement(By.xpath(locator)).getText();	
		System.out.println(actStatus);
		verifyScreenText(screenName, status, actStatus, data(section)+" Status Verification", data(section)+" Status Verification");
		}
		catch(Exception e) {
			writeExtent("Fail", "Could not verify the status of " + data(section)+" as " +status+ " on " + screenName);
		}
		finally {
		
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		clickWebElement(sheetName, "btn_chkShipmentStatus;xpath", "Check Shipment Status", screenName);
		waitForSync(2);
		}
		

	
}
	/**
	 * @author A-9847
	 * @Desc To verify the Multiple OCI details by passing MRN(supplCustomsInfo)values as the primary keys
	 * @param supplCustomsInfo
	 * @param source
	 * @param infoId
	 * @param customsInfoId
	 */
	public void verifyOCIDetails(String supplCustomsInfo[],String source[], String infoId[], String customsInfoId[]){
		
		try{
			
			for(int i=0;i<supplCustomsInfo.length;i++)
			{

			String locator = xls_Read.getCellValue(sheetName, "inbx_serialNo;xpath");
			locator = locator.replace("*", data(supplCustomsInfo[i]));
			String slNo=driver.findElement(By.xpath(locator)).getAttribute("value");
			System.out.println(slNo);
			
			String actSource = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_source;xpath").replace("*",slNo))).getAttribute("value");
			verifyScreenText(screenName ,data(source[i]), actSource, "Source","Source");

			String actInfoId=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_informatnId;xpath").replace("*",slNo))).getAttribute("value");			
			verifyScreenText(screenName ,data(infoId[i]), actInfoId, "Information ID","Information ID");

			String actCustomsInfoId = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_customInformatnId;xpath").replace("*",slNo))).getAttribute("value");
			verifyScreenText(screenName ,data(customsInfoId[i]), actCustomsInfoId, "Customs Information ID","Customs Information ID");

		}
			
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the OCI details on "+screenName);
		}
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To click Retrieve MRN Button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickRetrieveMRN() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_retrieveMRN;id", "Retrieve MRN Button", screenName);
		waitForSync(3);
	}
	
	
	
	/**
	 * @author A-9847
	 * @Desc To verify the OCI Details giving the Source as primary key
	 * @param source
	 * @param infoId
	 * @param customsInfoId
	 * @param supplCustomsInfo
	 */
	public void verifyOCIDetails(String source, String infoId, String customsInfoId, String supplCustomsInfo){
			
		try{

			String locator = xls_Read.getCellValue(sheetName, "inbx_serialNo;xpath");
			locator = locator.replace("*", data(source));
			String slNo=driver.findElement(By.xpath(locator)).getAttribute("value");
			System.out.println(slNo);

			String actInfoId=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_informatnId;xpath").replace("*",slNo))).getAttribute("value");			
			verifyScreenText(screenName ,data(infoId), actInfoId, "Information ID","Information ID");

			String actCustomsInfoId = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_customInformatnId;xpath").replace("*",slNo))).getAttribute("value");
			verifyScreenText(screenName ,data(customsInfoId), actCustomsInfoId, "Customs Information ID","Customs Information ID");

			String actsupplCustomsInfo = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_supplCustomsInformatn;xpath").replace("*",slNo))).getAttribute("value");
			verifyScreenText(screenName ,data(supplCustomsInfo), actsupplCustomsInfo, "Supplementary Customs Info","Supplementary Customs Info");

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the OCI details on "+screenName);
		}
		
	}

	/**
	 * @author A-6260
	 * Desc..verify the status and execute
	 * @throws Exception
	 */
	public void verifyAndExecute() throws Exception {
		String expText = "Executed";
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_executed;xpath"))).getText();
		if(actText.equalsIgnoreCase(expText)){
			writeExtent("Info", "AWB is in executed status in "+screenName);
		}else {
			asIsExecute();
		}
	}
	/* @author A-10690
	 * Desc..  Verify the error message during AWB execution when irregularity is captured for the awb 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyIrregularityErrorMessage(String errormessage) throws InterruptedException, IOException{

		

		String errorMessage = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMessages;xpath");

		try
		{
			if(driver.findElement(By.xpath(errorMessage)).isDisplayed()){

				if(driver.findElement(By.xpath(errorMessage)).getText().contains(errormessage))
				{
					writeExtent("Pass","verified exepected error message"+errormessage+"  from "+screenName+" Page");
				   
				}
				else
					writeExtent("Fail","Expected error message does not match." +"shown on "+screenName+" Page");

			} else
			{
				writeExtent("Fail","Expected error message not getting" +"shown on "+screenName+" Page");
			}
		}catch(Exception e)
		{
			writeExtent("Fail","Expected error message not getting" +"shown on "+screenName+" Page");
		}

		}
	/**
	 * @author A-8783
	 * Desc - Click view/ upload files button
	 * @throws Exception 
	 */
	public void clickViewUploadFile() throws Exception {
		clickWebElement(sheetName, "btn_viewUploadFiles;xpath", "Upload button", screenName);
		waitForSync(5);
		switchToWindow("storeParent");
		switchToWindow("child");
	}
	
	/**
	 * @author A-8783
	 * Desc - Upload file from opr026
	 * @param typeOfDocument
	 * @param Remarks
	 * @param fileName
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void uploadFile(String typeOfDocument, String Remarks, String fileName, String noOfFile) throws InterruptedException, AWTException, IOException {
		String filePath=System.getProperty("user.dir")+"\\src\\resources\\OPR026_uploadFile\\";
		
			String locatorDoc = xls_Read.getCellValue(sheetName, "lst_TypeOfDocumentMultiFile;xpath");
			locatorDoc = locatorDoc.replaceFirst("fileNo", noOfFile);
			selectValueInDropdownWthXpath(locatorDoc, data(typeOfDocument), "Type Of Document", "VisibleText");
			
			String locatorRemarks = xls_Read.getCellValue(sheetName, "inbx_viewUploadRemarksMultiFile;xpath");
			locatorRemarks = locatorRemarks.replaceFirst("fileNo", noOfFile);
			enterValueInTextbox(locatorRemarks, data(Remarks), "Remarks", screenName);
			
			waitForSync(3);
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[@id='theFile']")));
			waitForSync(3);
			fileUpload(fileName, filePath);
			waitForSync(2);
		
	}


	
	/**
	 * @author A-8783
	 * Desc - Save the file upload and verify it is saved successfully
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void saveAndVerifyFileSaved() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_viewUploadSave;id", "Save Button", screenName);
		
		By alert = getElement("Generic_Elements", "txt_AlertText;xpath");
		String actText = driver.findElement(alert).getText();
		
		verifyScreenText(sheetName, "All files saved successfully", actText, "File upload save",
				screenName);
	}

	
	/**
	 * @author A-8783
	 * Desc - Click close button
	 * @throws Exception 
	 */
	public void clickViewUploadClose() throws Exception {
		clickWebElement(sheetName, "btn_viewUploadClose;id", "Close Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
	}

	
	/**
	 * @author A-8783
	 * Desc - Click add button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickViewUploadAdd() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_viewUploadAdd;id", "Close Button", screenName);
	}

	
	/**
	 * @author A-8783
	 * Description..File upload
	 * @param fileName
	 * @return
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public CaptureAWB_OPR026 fileUpload(String fileName, String filePath) throws AWTException, InterruptedException
	{
		StringSelection ss = new StringSelection(filePath+fileName);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		//imitate mouse events like ENTER, CTRL+C, CTRL+V
		Robot robot = new Robot();
		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(90);
		robot.keyRelease(KeyEvent.VK_ENTER);
		return this;
	}

	/**
	 * @author A-10328
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verify security status
	 */
	
	public void verifySecurityStatus(String expText) throws InterruptedException, IOException  {	
		clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
		waitForSync(3);
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_inerror;xpath"))).getText();
		verifyScreenTextWithExactMatch(sheetName, expText, actText, "Verify security status error", "Capture AWB");
	}
	/**
	 * @author A-8783
	 * Desc - Select Charge Code from dropdown
	 * @throws InterruptedException
	 */
	public void selectChargeCode(String chargeCode) throws InterruptedException{
		selectValueInDropdown(sheetName, "lst_chargeCode;name",
				data(chargeCode), "ChargeCode", "Value");
		
	}

	/**
	 * @author A-10690
	 * Desc - Verify chargecode
	 * @param paymentType
	 */

	public void verifyChargeCode(String paymentType) throws IOException {

		waitForSync(1);	
		String chargecode=	getFirstSelectedOptionDropdown(sheetName,"lst_chargeCode;name","chargecode");
		System.out.println(chargecode);
		verifyScreenTextWithExactMatch(sheetName, data(paymentType), chargecode, "Verify charge code", "Capture AWB");


	}

/**
	 * @author A-8783
	 * Desc - Verify customs info from OCI line
	 * @param isoCode
	 * @param infoId
	 * @param customsInfoId
	 * @param supplCustomsInfo
	 */
	public void verifyOCILine(String isoCode, String infoId, String customsInfoId, String supplCustomsInfo) {
		String actISOCode = getAttributeWebElement(sheetName, "inbx_isoCode;xpath", "ISO Country Code", "value", screenName);
		verifyScreenText(screenName ,data(isoCode), actISOCode, "ISO Country Code","ISO Country Code");
		
		String actInfoId = getAttributeWebElement(sheetName, "inbx_infoId;xpath", "Information ID", "value", screenName);
		verifyScreenText(screenName ,data(infoId), actInfoId, "Information ID","Information ID");
		
		String actCustomsInfoId = getAttributeWebElement(sheetName, "inbx_customsInfoId;xpath", "Customs Information ID", "value", screenName);
		verifyScreenText(screenName ,data(customsInfoId), actCustomsInfoId, "Customs Information ID","Customs Information ID");
		
		String actsupplCustomsInfo = getAttributeWebElement(sheetName, "inbx_supplCustomsInfo;xpath", "Supplementary Customs Info", "value", screenName);
		verifyScreenText(screenName ,data(supplCustomsInfo), actsupplCustomsInfo, "Supplementary Customs Info","Supplementary Customs Info");

		
	}


/**
	 * @author A-9847
	 * @Desc To enter the Obligatory answer of checksheet as YES/NO based on questions
	 * @param chkSheetRequired
	 * @param answer
	 */
	public void captureCheckSheet(boolean chkSheetRequired,String answer)
	{  
     
		if(getLoggedInStation("OPR026").equals("AMS"))
		{


			boolean checkSheetExists=true;
			try
			{
				clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",screenName);
				driver.switchTo().frame("popupContainerFrame");
				waitForSync(3);
				waitTillScreenload("Generic_Elements", "btn_save;xpath", "OK Button", screenName);
				List <WebElement> questions=driver.findElements(By.xpath("//p[@style='display:inline']"));
				if(questions.size()==0)
				{
					checkSheetExists=false;
				}
				int i=0;
				for(WebElement ele : questions)
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
					if(checkSheetExists)

						writeExtent("Pass","Check sheet details selected on "+screenName);
					else			
						writeExtent("Info","No check sheet details configured on "+screenName); // To be changed to fail later
				}

				clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
				switchToFrame("default");
				clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);

				waitForSync(2);
				switchToFrame("contentFrame", "OPR026");
				driver.switchTo().frame("popupContainerFrame");
				driver.findElement(By.xpath("//button[@name='btnClose']")).click();
				waitForSync(1);
				switchToFrame("default");
				switchToFrame("contentFrame", "OPR026");

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
	}

	
	
	
	/***
	 * @author A-6260
	 * Desc..verify awb details not displayed in opr026
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyAWBdetailsNotDisplayed(String awbNo, String ShipmentPrefix) throws InterruptedException, IOException {

		String sheetName = "Generic_Elements";

		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",screenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", data(awbNo), "AWB No", screenName);
		clickWebElement(sheetName, "btn_List;xpath", "List Button", screenName);
		waitForSync(4);
		int size=driver.findElements(By.xpath("//div[@class='toast-item-close-success']")).size();
		if(size==0) {
			writeExtent("Fail", "AWB details displayed in " + screenName);
		}
		else {
			writeExtent("Pass", "AWB details not displayed in " + screenName);
		}

	}
	/**
	 * @Description : Verifying Print Contents 
	 * @author A-6260
	 * @param reportHeading
	 * @param screenId
	 * @param VP
	 * @throws Exception
	 */
	public void printAndVerifyReport(String PrintType,String reportHeading,String...VP) {
		try {
			switchToWindow("storeParent");
			Actions actionDriver = new Actions(driver);
			String printLocator = xls_Read.getCellValue(sheetName, "btn_Print;xpath");
			WebElement printButton = driver.findElement(By.xpath(printLocator));
			actionDriver.moveToElement(printButton).perform();
			waitForSync(2);

			switch(PrintType) {
			case "LaserPrint":
				String Lprintlocator = xls_Read.getCellValue(sheetName, "btn_LaserPrint;xpath");
				WebElement LprintButton = driver.findElement(By.xpath(Lprintlocator));
				actionDriver.moveToElement(LprintButton).click().build().perform();
				break;
			}
			waitForSync(3);
			switchToWindow("child");
			switchToWindow("storeFirstChild");
			clickWebElement(sheetName, "btn_ok;xpath", "OK Button", screenName);
			switchToWindow("childWindow2");
			
			switchToFrame("frameName","ReportContainerFrame");
            
            //Verifying heading of the report
            String locatorHeading=xls_Read.getCellValue("Generic_Elements", "htmlDiv_reportHeading;xpath");
            locatorHeading=locatorHeading.replace("ReportHeading", data(reportHeading));
            try {
           	 if(driver.findElement(By.xpath(locatorHeading)).isDisplayed())
                {
                      onPassUpdate(screenName, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is  getting generated", "Verify whether the report is generated","Verify whether the report is generated");
                }
                else
                {
                      onFailUpdate(screenName, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
                }
                 
			} catch (Exception e) {
				 onFailUpdate(screenName, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
			}
            
            //Verifying Report Contents
            
            String locatorContent=xls_Read.getCellValue("Generic_Elements", "htmlContents_report;xpath");
            String contents=driver.findElement(By.xpath(locatorContent)).getText();
            System.out.println(contents);
            for(String s:VP)
            {
           	 waitForSync(2);
           	
           	 if(contents.contains(s))
           	 {
           		 writeExtent("Pass", "Sucessfully Verified " +s + " In " + screenName);
           	 }
           	 else
           	 { 
           		 writeExtent("Fail", "Not Verified " + s + " In " + screenName);
           	 }
            }
		
			closeBrowser();
			switchToWindow("getFirstChild");
			clickWebElement(sheetName, "btn_close;xpath", "close Button", screenName);
			switchToWindow("getParent");
			switchToDefaultAndContentFrame("OPR026");

		} catch (Exception e) {
			writeExtent("Fail", "Report is not getting generated"+" In " + screenName);
		}

	}
	/**
	 * @author A-6260
	 * Desc.. as is execute with invalid certificate details
	 * @param expText
	 * @throws Exception
	 */
	public void asIsExecuteWithInvalidCertificate(String expText) throws Exception {
		boolean invalidCertificateMessageDisplayed=false;
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		clickGeneralTab();
		enterExecutionDate();
		waitForSync(3);
		
		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
		  	
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/

		/**********************************/
		enterHSCode();
		/**********************************/

		clickWebElement(sheetName, "btn_AsIsExecute;xpath",
				"AsIsExecute Button", screenName);
		waitForSync(6);
		switchToFrame("default");
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		

	try {
		while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
		{
			String actText=getElementText("Generic_Elements", "htmlDiv_msgStatus;xpath","invalid certificate msg", screenName);
			System.out.println(actText);
			System.out.println(expText);
			if(actText.contains(expText))
			{
				invalidCertificateMessageDisplayed=true;
			}
			driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
			waitForSync(6);
		}}
	
	catch(Exception e)
	{

	}
		
		if(invalidCertificateMessageDisplayed)
		{
			writeExtent("Pass","Message "+expText+ " displayed on "+screenName);
		}
		else
		{
			writeExtent("Fail","Message "+expText+ " not displayed on"+screenName);
		}
		 switchToFrame("contentFrame", "OPR026");
	}


	/**
	 * @author A-7271
	 * @param chkSheetRequired
	 * Desc : capture check sheet
	 */
	public void captureChecksheetWithMultiFormat(boolean chkSheetRequired)
	{
		if(getLoggedInStation("OPR026").equals("AMS"))
		{

		boolean checkSheetExists=true;
		try
		{

			clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",
					screenName);
			waitForSync(3); 

			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);


			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			List <WebElement> questions2=driver.findElements(By.xpath("//input[@title='Date']")); 
			List <WebElement> questions3=driver.findElements(By.xpath("//input[@title='Time']"));
			List <WebElement> questions4=driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));
			
			
			if(questions.size()==0&&questions2.size()==0&&questions3.size()==0&&questions4.size()==0)
			{
				checkSheetExists=false;
			}

			
			for(WebElement ele : questions2)
			{
				ele.sendKeys(createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			}
			for(WebElement ele : questions3)
			{
				ele.sendKeys("00:00");
			}
			for(WebElement ele : questions4)
			{
				ele.sendKeys("Yes");
                keyPress("TAB");
			}
			for(WebElement ele : questions)
			{
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
			}
			
			
			
			if(chkSheetRequired)
			{
				if(checkSheetExists)
				{
					writeExtent("Pass","Check sheet details selected on "+screenName);
				}

				else
				{
					writeExtent("Info","No check sheet details configured on "+screenName); //To be changed to fail later
				}
			}
			waitForSync(2);
			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);

			waitForSync(2);
			switchToFrame("contentFrame", "OPR026");
			driver.switchTo().frame("popupContainerFrame");
			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
			waitForSync(1);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
			waitForSync(3);


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
	}
	/**
	 * @author A-6260
	 * Desc..verify charge codes
	 * @param verifyChargeCodePresent
	 * @param expValue
	 * @param screenId
	 */
	public void verifyChargeCodes(boolean verifyChargeCodePresent, String expValue,String screenId) {
		try
		{
			String locator1 = xls_Read.getCellValue(sheetName, "txt_chargeHeadCode;xpath");
			List<WebElement> chargeCode = driver.findElements(By.xpath(locator1));
			boolean chargeCodeExists=chargeCode.stream().map(s->s.getAttribute("value")).anyMatch(s->s.equalsIgnoreCase(expValue));

				if(verifyChargeCodePresent) {
					
				if(chargeCodeExists)
				{
					writeExtent("Pass","ChargeCode "+expValue+" present on the screen "+screenId);
				}
				else
				{
					writeExtent("Fail","ChargeCode "+expValue+" not displayed on the screen "+screenId);
				}
			}else {
				if(chargeCodeExists)
				{
					writeExtent("Fail"," ChargeCode "+expValue+" present on the screen "+screenId);
				}
				else
				{
					writeExtent("Pass","ChargeCode "+expValue+" not displayed on the screen "+screenId);
				}
				}
			

		} catch(Exception e)
		{
			writeExtent("Fail","Could not verify charge Codes in  "+screenId);
		}

	}
		/**
	 * @author A-6260
	 * Desc..provide IAC certificate details
	 * @param IACNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void provideIACCertificateDetails(String IACNumber) throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_shprCertificate;id", IACNumber, "IAC CertificateNumber", screenName);
		waitForSync(3);
		performKeyActions(sheetName, "inbx_shprCertificate;id", "TAB", "IAC CertificateNumber", screenName);
	}
	/**
	 * Description...  Enter Shipper PhoneNo 
	 * @throws InterruptedException
	 */
	public void enterShipperPhoneNo(String ShipperPhoneNo) throws InterruptedException {
		By element = getElement(sheetName, "inbx_shipperTelephoneNumber;name");
		String actText = driver.findElement(element).getAttribute("value");
		
		if(actText.equals("")){
			enterValueInTextbox(sheetName, "inbx_shipperTelephoneNumber;name", data("ShipperPhoneNo"),
					"Consignee PhoneNo", screenName);
		}
		
		
	}
	/**
	 * Description...  Enter Consignee PhoneNo 
	 * @throws InterruptedException
	 */
	public void enterConsigneePhoneNo(String ConsigneePhoneNo) throws InterruptedException {
		
		By element = getElement(sheetName, "inbx_consigneeTelephoneNumber;name");
		String actText = driver.findElement(element).getAttribute("value");
		
		if(actText.equals("")){
			enterValueInTextbox(sheetName, "inbx_consigneeTelephoneNumber;name", data("ConsigneePhoneNo"),
					"Consignee PhoneNo", screenName);
		}
		
	}


	/**
	 * Author :A-7943
	 * Description... To provide rating details (IATA rate charges is not auto
	 * calculated and is blank)
	 * @param rateClass
	 *            : test data column name for rate class
	 * @param IATARate
	 *            : test data column name for IATA rate 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void provideRatingDetailsWithoutNetCharge(String rateClass, String IATARate,String IATACharge) throws InterruptedException, AWTException {
		selectValueInDropdown(sheetName, "lst_RateClass;xpath", data(rateClass), "RateClass", "VisibleText");
		clearText(sheetName, "inbx_IATARate;xpath", "IATARate", screenName);
		enterValueInTextbox(sheetName, "inbx_IATARate;xpath", data(IATARate), "IATARate", screenName);
		performKeyActions(sheetName, "inbx_IATARate;xpath", "TAB","IATARate", screenName);                              
		String rating=getAttributeWebElement("CaptureAWB_OPR026", "inbx_ratelineIataCharges;xpath", "Rate line Iata Charges", "value", screenName);
		if(rating.equals("0"))
			clearText(sheetName, "inbx_ratelineIataCharges;xpath", "IATACharge", screenName);
		enterValueInTextbox("CaptureAWB_OPR026", "inbx_ratelineIataCharges;xpath", data(IATACharge), "Rate line Iata Charges", screenName);
		Thread.sleep(2000);
	}



	/**
	 * @Description : 
	 * @author A-9175
	 * @param status [Pass true if you want to test button status as Enabled, pass false to check button status is Disabled]
	 * @throws InterruptedException
	 */
	public void verifySplitButtonStatus(boolean status) throws InterruptedException{
		
		By btnStatus = getElement(sheetName, "btn_splitShipment;id");
		boolean val = driver.findElement(btnStatus).isEnabled();
		if(status)
		{
			if(val)
			{
				System.out.println("Sucessfully Verified Split Button status is Enabled On " + screenName + " Page");
				writeExtent("Pass", "Sucessfully Verified Split Button status is Enabled On " + screenName + " Page");
			}
			else
			{
				System.out.println("Not Verified Split Button status is Enabled On " + screenName + " Page");
				writeExtent("Fail", "Not Verified Split Button status is Enabled On " + screenName + " Page");
			}
		}
		else
		{
			if(!val)
			{
				System.out.println("Sucessfully Verified Split Button status is Disabled On " + screenName + " Page");
				writeExtent("Pass", "Sucessfully Verified Split Button status is Disabled On " + screenName + " Page");
			}
			else
			{
				System.out.println("Not Verified Split Button status is Disabled On " + screenName + " Page");
				writeExtent("Fail", "Not Verified Split Button status is Disabled On " + screenName + " Page");
			}
		}

	}
	/**
	 * @Description :Verification of SCI code as Select
	 * @author A-7943
	 * @throws InterruptedException
	 */
	public void verifySCIAsSelect() throws InterruptedException {
		By source = getElement(sheetName, "lbl_sccSelectValue;xpath");
		String actText = driver.findElement(source).getText();
		String expText = "--Select--";
		if (actText.equals(expText)) {
			verifyScreenText(sheetName, expText, actText, "verify SCI ", "Capture AWB");
			writeExtent("Pass", "SCI is  displayed as " + expText + " on " + screenName);

		} else {
			writeExtent("Fail", "SCI is not displayed as " + expText + " on " + screenName);

		}

	}

	/**
	 * @Description : Verifying Split Shipments elements status
	 * @author A-9175
	 * @throws Exception
	 */
	public void clickSplitShipment() throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id","Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		By awbPrefix = getElement(sheetName, "inbx_shipmentPrefix;name");
		By awbNumber = getElement(sheetName, "inbx_splitAwbNumber;name");
		By statedPcs = getElement(sheetName, "inbx_splitStatedPcs;name");
		By scc = getElement(sheetName, "inbx_splitSCC;name");
		By splitInto=getElement(sheetName,"inbx_splitCount;id");
		
		
		try
		{
			if(driver.findElement(awbPrefix).getAttribute("readonly") != null)
			{
				
				System.out.println("Sucessfully Verified AWB Prefix Text box is Disabled On " + screenName + " Page");
				writeExtent("Pass", "Sucessfully Verified AWB Prefix Text box is Disabled On " + screenName + " Page");
			}
			else
			{
				System.out.println("Not Verified AWB Prefix Text box is Disabled On " + screenName + " Page");
				writeExtent("Fail", "Not Verified AWB Prefix Text box is Disabled On " + screenName + " Page");
			}
			
			if(driver.findElement(awbNumber).getAttribute("readonly") != null)
			{
				System.out.println("Sucessfully Verified AWB Number Text box is Disabled On " + screenName + " Page");
				writeExtent("Pass", "Sucessfully Verified AWB Number Text box is Disabled On " + screenName + " Page");
			}
			else
			{
				System.out.println("Not Verified AWB Number Text box is Disabled On " + screenName + " Page");
				writeExtent("Fail", "Not Verified AWB Number Text box is Disabled On " + screenName + " Page");
			}
			if(driver.findElement(statedPcs).getAttribute("readonly") != null)
			{
				System.out.println("Sucessfully Verified Stated Pieces Text box is Disabled On " + screenName + " Page");
				writeExtent("Pass", "Sucessfully Verified Stated Pieces Text box is Disabled On " + screenName + " Page");
			}
			else
			{
				System.out.println("Not Verified Stated Pieces Text box is Disabled On " + screenName + " Page");
				writeExtent("Fail", "Not Verified Stated Pieces Text box is Disabled On " + screenName + " Page");
			}
			
			if(driver.findElement(scc).getAttribute("readonly") != null)
			{
				System.out.println("Sucessfully Verified SCC Text box is Disabled with SCCs Displayed as "+driver.findElement(scc).getAttribute("value")+ screenName + " Page");
				writeExtent("Pass","Sucessfully Verified SCC Text box is Disabled with SCCs Displayed as "+driver.findElement(scc).getAttribute("value")+ screenName + " Page");
			}
			else
			{
				System.out.println("Not Verified SCC Text box is Disabled On " + screenName + " Page");
				writeExtent("Fail", "Not Verified SCC Text box is Disabled On " + screenName + " Page");
			}
			if(driver.findElement(splitInto).isEnabled())
			{
				System.out.println("Sucessfully Verified SCC Text box is Enabled On"+ screenName + " Page");
				writeExtent("Pass","Sucessfully Verified SCC Text box is Enabled On"+ screenName + " Page");
			}
			else
			{
				System.out.println("Not Verified SCC Text box is Enabled On"+ screenName + " Page");
				writeExtent("Fail", "Not Verified SCC Text box is Enabled On"+ screenName + " Page");
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Failed to verify Split shipment Disabled elements on " + screenName + " Page");
		}
		
		
		
		waitForSync(1);
		clickWebElement(sheetName, "splitSCCclose;id","Close", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}

	/**
	 * @author A-8783
	 * Description...  Remove Consignee PhoneNo 
	 * @throws Exception 
	 */
	public void removeConsigneePhoneNo() throws Exception {

		String consigneeTelephoneNo=getAttributeWebElement(sheetName, "inbx_consigneeTelephoneNumber;name", "consignee PhoneNo", "value", screenName);

		if(!(consigneeTelephoneNo.equals(null))){
			clearText(sheetName, "inbx_consigneeTelephoneNumber;name", "Consignee PhoneNo", screenName);

		}

	}

	
	/**
	 * @author A-8783
	 * Description...  Remove Shipper PhoneNo 
	 * @throws Exception 
	 */
	public void removeShipperPhoneNo() throws Exception {

		String shipperTelephoneNo=getAttributeWebElement(sheetName, "inbx_shipperTelephoneNumber;name", "shipper PhoneNo", "value", screenName);
		if(!(shipperTelephoneNo.equals(null))){
			clearText(sheetName, "inbx_shipperTelephoneNumber;name", "Consignee PhoneNo", screenName);

		}
	}


	/**
	 * @author A-6260
	 * Desc: to enter shipment details
	 * @param numberOfRecords
	 * @param Pieces
	 * @param Weight
	 * @param Volume
	 * @param commodityCode
	 * @param shipmentDesc
	 * @throws Exception
	 */
	public void enterShipmentDetails(int numberOfRecords,String[] Pieces,String[] Weight, String[] Volume, String[] commodityCode) throws Exception {

		for(int i=0;i<numberOfRecords;i++)
		{
			//Enter Pieces
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_Pieces;xpath");
				locator=locator.replace("*", Integer.toString(i+1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(Pieces[i]);
				writeExtent("Pass", "Entered Pieces "+Pieces[i]+" in "+screenName);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter pieces "+Pieces[i]+" in "+screenName);
			}

			//Enter weight
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_Weight;xpath");
				locator=locator.replace("*", Integer.toString(i+1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(Weight[i]);
				writeExtent("Pass", "Entered gross Weight "+Weight[i]+" in "+screenName);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter gross Weight "+Weight[i]+" in "+screenName);
			}

			//Enter volume
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_Vol;xpath");
				locator=locator.replace("*", Integer.toString(i+1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(Volume[i]);
				writeExtent("Pass", "Entered volume "+Volume[i]+" in "+screenName);
				waitForSync(2);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter volume "+Volume[i]+" in "+screenName);
			}
			//Enter Commodity code
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_cmdtyCode;xpath");
				locator=locator.replace("*", Integer.toString(i+1));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(commodityCode[i]);
				//press Tab
				driver.findElement(By.xpath(locator)).sendKeys(Keys.TAB);
				waitForSync(3);
				writeExtent("Pass", "Entered commodity code "+commodityCode[i]+" in "+screenName);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter commodity code "+commodityCode[i]+" in "+screenName);
			}

			if(i< numberOfRecords-1) {
				clickWebElement(sheetName, "btn_AddRating;id", "Add rating Button",screenName);
				waitForSync(5);
			}
		}
	}


	/**
	 * @author A-6260
	 * Desc: to click print
	 * @param PrintType
	 */
	public void clickPrint(String PrintType) {
		try {
			switchToWindow("storeParent");
			Actions actionDriver = new Actions(driver);
			String printLocator = xls_Read.getCellValue(sheetName, "btn_Print;xpath");
			WebElement printButton = driver.findElement(By.xpath(printLocator));
			actionDriver.moveToElement(printButton).perform();
			waitForSync(2);

			switch(PrintType) {
			case "LaserPrint":
				String Lprintlocator = xls_Read.getCellValue(sheetName, "btn_LaserPrint;xpath");
				WebElement LprintButton = driver.findElement(By.xpath(Lprintlocator));
				actionDriver.moveToElement(LprintButton).click().build().perform();
				break;
			}
			waitForSync(3);
			switchToWindow("child");
			switchToWindow("storeFirstChild");
			clickWebElement(sheetName, "btn_ok;xpath", "OK Button", screenName);
			switchToWindow("childWindow2");
			closeBrowser();
			switchToWindow("getFirstChild");
			clickWebElement(sheetName, "btn_close;xpath", "close Button", screenName);
			switchToWindow("getParent");
			switchToDefaultAndContentFrame("OPR026");

		} catch (Exception e) {
			e.printStackTrace();
			writeExtent("Fail", "Could not click on " + PrintType + " button On "
					+ screenName + " Page");
		}

	}
	
	
	/**
	 * @author A-6260
	 * Desc: provide rating details
	 * @param numberOfRecords
	 * @param rateClass
	 * @param IATArate
	 * @param IATAcharge
	 */
	public void provideRatingDetails(int numberOfRecords, String[] rateClass, String[] IATArate, String[] IATAcharge) {
		for(int i=0;i<numberOfRecords;i++)
		{
			//Select rate class
			if(rateClass[i] !=null && !rateClass[i].equals("")) {
				try
				{
					String locator = xls_Read.getCellValue(sheetName, "lst_RateClass;id");
					locator=locator.replace("*", Integer.toString(i));
					WebElement rateclass = driver.findElement(By.id(locator));
					Select select = new Select(rateclass);
					select.selectByVisibleText(rateClass[i]);
					writeExtent("Pass", "selected rate class "+rateClass[i]+" in "+screenName);
				}
				catch(Exception e)
				{
					writeExtent("Fail", "Couldn't select rate class "+rateClass[i]+" in "+screenName);
				}
			}
			//Enter IATArate
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_IATARate;id");
				locator=locator.replace("*", Integer.toString(i));
				driver.findElement(By.id(locator)).clear();
				driver.findElement(By.id(locator)).sendKeys(IATArate[i]);
				writeExtent("Pass", "Entered IATA rate "+IATArate[i]+" in "+screenName);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter IATA rate "+IATArate[i]+" in "+screenName);
			}

			//Enter IATA charge
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_IATACharge;id");
				locator=locator.replace("*", Integer.toString(i));
				driver.findElement(By.id(locator)).clear();
				driver.findElement(By.id(locator)).sendKeys(IATAcharge[i]);
				writeExtent("Pass", "Entered IATA charge "+IATAcharge[i]+" in "+screenName);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter IATA charge "+IATAcharge[i]+" in "+screenName);
			}

		}
	}
	

	/**
	 * Description... Click AsIsExecute Button
	 * @throws Exception
	 */
	public void asIsExecuteButtonOnly() throws Exception {
		try
		{
			screenName="Capture AWB";
			String testEnv=getPropertyValue(globalVarPath, "testEnv");
			/********** REMOVE CUSTOMS BLOCK*****/
			if(testEnv.equals("RC4"))
			{
			removeCustomsBlock();
			}
			/*********************************/
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
			waitForSync(3);
			clickGeneralTab();
			// Click override certificate
			/**String locator = xls_Read.getCellValue(sheetName, "chk_overrideCertifications;id");
			if(data("Origin").equals("IAD"))
			{
				if(!driver.findElement(By.id(locator)).isSelected())
				{
					clickWebElement(sheetName, "chk_overrideCertifications;id", "Override checkbox",screenName);
				}
			}**/
			enterExecutionDate();
			
			/************ FRENCH CUSTOMS****/
			String station=getLoggedInStation("OPR026");  
			
			
			if(station.equals("CDG")) 
			{
			  	
				enterFrenchCustomsDetails();
				captureCDGCompChecksheet();
			}
			/**********************************/

			/**********************************/
			enterHSCode();
			/**********************************/

			clickWebElement(sheetName, "btn_AsIsExecute;xpath",
					"AsIsExecute Button", screenName);
			waitForSync(12);

		}
		catch(Exception e)
		{

		}
	}


	/**
	 * Description...  Enter Shipper PhoneNo 
	 * @throws InterruptedException
	 */
	public void enterShipperPhoneNo() throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_shipperTelephoneNumber;name", data("ConsigneePhoneNo"),
				"Consignee PhoneNo", screenName);
	}


	/**
	 * Description : clickOverrideCertifications 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOverrideCertifications() throws InterruptedException, IOException {
		/**waitForSync(5);
		clickWebElement(sheetName, "chk_overrideCertifications;id", "Override checkbox",
				screenName);**/

	}
	
	
	/**
	 * @author A-9478
	 * Description... Enter Rating ULD Details by clicking on ULD Type LOV
	 * @throws Exception
	 */
	public void enterRatingULDDetails(int row,int numberOfRecords,String[] ULDNum,String[] ULDWeight) throws Exception {

		switchToWindow("storeParent");
		waitForSync(2);
		String uldTypeLOVlocator = xls_Read.getCellValue(sheetName, "btn_UldTypeLOV;xpath");
		uldTypeLOVlocator=uldTypeLOVlocator.replace("*", Integer.toString(row));
		driver.findElement(By.xpath(uldTypeLOVlocator)).click();
		waitForSync(3);
		switchToWindow("child");
		for(int i=0;i<numberOfRecords;i++)
		{
			clickWebElement(sheetName, "btn_AddULDInRatingULD;id", "Add", screenName);
			//Enter ULD Number
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_ULDNumberInRatingULD;xpath");
				locator=locator.replace("*", Integer.toString(i));
				driver.findElement(By.xpath(locator)).clear();
				driver.findElement(By.xpath(locator)).sendKeys(ULDNum[i]);
				waitForSync(2);
				writeExtent("Pass", "Entered ULD Number "+ULDNum[i]+" in "+screenName);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter ULD Number "+ULDNum[i]+" in "+screenName);
			}

			//Enter weight
			try
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_ULDWeight;id");
				locator=locator.replace("*", Integer.toString(i));
				driver.findElement(By.id(locator)).clear();
				driver.findElement(By.id(locator)).sendKeys(ULDWeight[i]);
				writeExtent("Pass", "Entered ULD Weight "+ULDWeight[i]+" in "+screenName);
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't enter ULD Weight "+ULDWeight[i]+" in "+screenName);
			}

		}
		clickWebElement(sheetName, "btn_RatingULDOk;id", "Ok", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
	}
	/**
	 * Description... To verify the status and  click on reopen button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAndclickReopen() throws InterruptedException, IOException {

	  String status=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_executionStatusText;xpath"))).getText();
        System.out.println(status);
        if(status.equals("Executed")){
        	clickWebElement(sheetName, "button_Reopen;xpath", "Reopen Button", screenName);
        	waitForSync(2);
        }
		
	}
	
	/**
	 * Description..click HAWB Without Clicking On Console
	 * @param ScreenID
	 * @throws Exception
	 */
	public void clickHAWBWithoutClickingOnConsole(String ScreenID) throws Exception {
		screenName = "CaptureHAWB";
		switchToFrame("default");
		switchToFrame("contentFrame", ScreenID);
		clickWebElement(sheetName, "btn_HAWB;name", "HAWB Button", screenName);
		waitForSync(4);
		switchToFrame("default");
		try {
			ele = driver.findElement(By
					.xpath("//div[@class='ui-dialog-buttonset']//button[1]"));
			ele.click();
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		switchToFrame("contentFrame", ScreenID);
		Thread.sleep(2000);
	}
	
	
	/**
	 * @author A-8783
	 * Description... 	Verify IATA Charge Details
	 * @param IATACharge, IATARate
	 * @throws InterruptedException
	 */
	public void verifyIATAChargeDetails(String IATACharge, String IATARate) throws InterruptedException{

		By element1 = getElement(sheetName, "inbx_iataCharge;name");
		String actText1 = driver.findElement(element1).getAttribute("value").replaceAll(",", "");
		verifyValueOnPage(actText1, IATACharge, "", screenName, "IATA Charge Verification");
		By element2 = getElement(sheetName, "inbx_IATARate;xpath");
		String actText2 = driver.findElement(element2).getAttribute("value").replaceAll(",", "");
		verifyValueOnPage(actText2, IATARate, "", screenName, "Net Charge Verification");

	}

	
	/**
	 * @author A-6260
	 * Description :Provide certificate number
	 * @param certificateNumber
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void provideCertificateDetails(String certificateNumber) throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_CCSF;xpath", certificateNumber, "CertificateNumber", screenName);
		waitForSync(3);
		performKeyActions(sheetName, "inbx_CCSF;xpath", "TAB", "CertificateNumber", screenName);
		Thread.sleep(2000);
	}

	
	/**
	 * @author A-6260
	 * Description..enter and validate agentCode
	 * @param AgentCode
	 * @throws Exception
	 */
	public void enterAndValidateAgentCode(String AgentCode) throws Exception  {
		switchToWindow("storeParent");
		enterValueInTextbox(sheetName, "inbx_AgentCode;xpath", data(AgentCode), "Agent Code", screenName);
		performKeyActions(sheetName, "inbx_AgentCode;xpath", "ENTER", "AgentCode", screenName);
		waitForSync(1);
		switchToWindow("child");
		try {
			clickWebElement(sheetName, "chk_AgentCode;xpath", "AgentCode checkbox", screenName);
		}catch(Exception e)
		{}
		clickWebElement(sheetName, "btn_okAgentPopUp;id", "AgentCode OK", screenName);
		waitForSync(1);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");
	}

	
	/**
	 * @author A-8783
	 * Description... update Notification Details 
	 * @param NotificationName
	 * @throws Exception
	 */
	public void updateNfyDetails(String NotifyCode) throws Exception {

		try{

			String locator1 = xls_Read.getCellValue(sheetName, "btn_clickMore;xpath");
			String locator2 = xls_Read.getCellValue(sheetName, "btn_clickNotify;xpath");
			WebElement ele1 = driver.findElement(By.xpath(locator1));
			Actions a = new Actions(driver);
			a.moveToElement(ele1).perform();
			waitForSync(2);
			driver.findElement(By.xpath(locator2)).click();
			waitForSync(5);
			switchToWindow("storeParent");
			waitForSync(2);
			switchToWindow("child");

			/*****enterValueInTextbox(sheetName, "inbx_NotifyCode;id", data(NotifyCode), "Notify Code", screenName);****/
			switchToWindow("storeParent;parent1");
			clickWebElement(sheetName, "img_notifyLOV;xpath", "Notify LOV", screenName);
			switchToWindow("multipleWindows");
			enterValueInTextbox(sheetName, "inbx_customerLOV;id", data(NotifyCode), "Customer Code", screenName);
			clickWebElement(sheetName, "btn_customerLOVList;id", "List Customer", screenName);
			clickWebElement(sheetName, "chkBox_customerLOV;xpath", "Customer check box", screenName);
			clickWebElement(sheetName, "btn_customerLOVOK;xpath", "Customer LOV Ok", screenName);
			waitForSync(3);
			switchToSpecifiedWindow(data("parent1"));
			clickWebElement(sheetName, "btn_notifyList;name", "List button", screenName);
			clickWebElement(sheetName, "btn_NotificationOK;id", "OK button", screenName);
			waitForSync(3);
			switchToWindow("getParent");
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");

			writeExtent("Pass","Updated Notification details in "+screenName);


		}
		catch(Exception e)
		{
			e.printStackTrace();
			writeExtent("Fail", "Couldn't update  Notification details in "+screenName);
		}
	}
	
	
	/**
	 * @author A-8783
	 * Description... Enter charge code details automatically
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void enterChargeCode(int pos,String chargecode) throws InterruptedException, AWTException, IOException 
	{

		try
		{

			//Charge Code
			String chargeCodeLoc=xls_Read.getCellValue(sheetName, "inbx_chargeHead;id");
			driver.findElement(By.id(chargeCodeLoc+pos)).sendKeys(data(chargecode));

			driver.findElement(By.id(chargeCodeLoc+pos)).sendKeys(Keys.TAB);
			waitForSync(2);

			writeExtent("Pass","Charge code entered . Other Charge : "+data(chargecode)+" on "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail","Other charge details not eneterd on "+screenName);
		}

	}
	

	/**
	 * @author A-8783
	 * Description... Store other charges
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void storeOtherChargesValue(String chargecode,String otherChargeVal) throws InterruptedException, AWTException, IOException 
	{
		int otherChargeCount=data(chargecode).split(",").length;

		try
		{
			for(int i=0;i<otherChargeCount;i++)
			{

				//Charge Code
				String chargeCodeLoc=xls_Read.getCellValue(sheetName, "inbx_chargeHead;id");
				driver.findElement(By.id(chargeCodeLoc+i)).sendKeys(data(chargecode).split(",")[i]);

				driver.findElement(By.id(chargeCodeLoc+i)).sendKeys(Keys.TAB);
				waitForSync(2);

				//Other Charge
				String otherCharge=xls_Read.getCellValue(sheetName, "inbx_otherCharge;id");
				driver.findElement(By.id(otherCharge+i)).sendKeys(data(otherChargeVal));

				//Due carrier
				String dc=xls_Read.getCellValue(sheetName, "chkBox_OCBillingParty;xpath");
				driver.findElement(By.xpath(dc+"["+(i+1)+"]")).click();

			}

			writeExtent("Pass","Other Charge details entered . Other Charge : "+data(chargecode)+" and charge value "+data(otherChargeVal)+" on "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail","Other charge details not eneterd on "+screenName);
		}

	}

	/**
	 * 
	 * @param shipperCode
	 * @throws Exception
	 * Description : enter and validate ShipperCode
	 */
	public void enterAndValidateShipperCode(String shipperCode) throws Exception  {
		switchToWindow("storeParent");
		enterValueInTextbox(sheetName, "inbx_shipperCode;xpath", data(shipperCode), "ShipperCode", screenName);
		performKeyActions(sheetName, "inbx_shipperCode;xpath", "ENTER", "ShipperCode", screenName);
		waitForSync(1);
		switchToWindow("child");
		try {
			clickWebElement(sheetName, "chk_shipperConsigneeCode;xpath", "ShipperCode checkbox", screenName);
		}catch(Exception e)
		{}
		clickWebElement(sheetName, "btn_okShipperConsigneePopUp;xpath", "ShipperCode OK", screenName);
		waitForSync(1);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");
	}
	
	
	/**
	 * 
	 * @param consigneeCode
	 * @throws Exception
	 * Description : enter and validate ConsigneeCode
	 */
	public void enterAndValidateConsigneeCode(String consigneeCode) throws Exception {
		switchToWindow("storeParent");
		enterValueInTextbox(sheetName, "inbx_consigneeCode;xpath", data(consigneeCode), "ConsigneeCode", screenName);
		performKeyActions(sheetName, "inbx_consigneeCode;xpath", "ENTER", "ConsigneeCode", screenName);
		waitForSync(1);
		switchToWindow("child");
		try {
			clickWebElement(sheetName, "chk_shipperConsigneeCode;xpath", "ConsigneeCode checkbox", screenName);
		}catch(Exception e)
		{}
		clickWebElement(sheetName, "btn_okShipperConsigneePopUp;xpath", "ShipperCode OK", screenName);
		waitForSync(1);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");
	}
	
	/**
	 * @author A-7271
	 * @param consigneeCode
	 * @throws InterruptedException
	 * Desc : Enter consignee code
	 */
	public void enterConsigneeCode(String consigneeCode) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_consigneeCode;xpath", data(consigneeCode), "ConsigneeCode", screenName);
		performKeyActions(sheetName, "inbx_consigneeCode;xpath", "TAB", "ConsigneeCode", screenName);
		waitForSync(1);
	}
	/**
	 * Description..add HAWBDetails and validate Shipper and Consignee
	 * @param HAWB
	 * @param Shipper
	 * @param Consignee
	 * @param Origin
	 * @param Destination
	 * @param Pieces
	 * @param Weight
	 * @throws Exception
	 */
	public void addHAWBDetailsAndValidateShipperAndConsignee(String HAWB, String Shipper, String Consignee, String Origin, String Destination, String Pieces,String Weight) throws Exception {
		String hawbNo=generateHAWB();
		map.put(HAWB,hawbNo);
		
		switchToWindow("child");
		clickWebElement("CaptureHAWB_OPR029", "inbx_houses;id", "Houses", screenName);
		waitForSync(2);
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_houses;id", data(HAWB), "Houses", screenName);
		enterAndValidateShipperCodeHAWB(Shipper);
		enterAndValidateConsigneeCodeHAWB(Consignee); 
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_origin;name", data(Origin), "Origin", screenName);
		keyPress("TAB");  
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_destination;name", data(Destination), "Destination", screenName);
		keyPress("TAB");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_pieces;name", data(Pieces), "Pieces", screenName);
		keyPress("TAB");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_weigth;name", data(Weight), "Weight", screenName);
		keyPress("TAB");
		waitForSync(2);
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_Desc;name", "HAWB Remarks", "Remarks", screenName);
		keyPress("TAB");
		enterHAWBHSCode();
		clickWebElement("CaptureHAWB_OPR029", "btn_hawbOK;id", "OK", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");
	}
	
	
	/**
	 * Description..enter and validate ShipperCode in HAWB
	 * @param shipperCode
	 * @throws Exception
	 */
	public void enterAndValidateShipperCodeHAWB(String shipperCode) throws Exception  {
		switchToWindow("storeFirstChild");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_shipper;name", data(shipperCode), "Shipper", screenName);
		performKeyActions("CaptureHAWB_OPR029", "inbx_shipper;name", "ENTER", "Shipper", screenName);
		waitForSync(1);
		switchToWindow("childWindow2");
		try {
			clickWebElement(sheetName, "chk_shipperConsigneeCode;xpath", "ShipperCode checkbox", screenName);
			waitForSync(1);
		}catch(Exception e)
		{}
		clickWebElement(sheetName, "btn_okShipperConsigneePopUp;xpath", "ShipperCode OK", screenName);
		waitForSync(1);
		switchToWindow("getFirstChild");
	}

	/**
	 * @author A-9844
	 * Description... Enter Product Code
	 * @param productCode
	 * @throws Exception
	 */
	public void enterProductCode(String productCode) throws Exception  {
		
		enterValueInTextbox(sheetName, "inbx_product;name", data(productCode), "Product Code", screenName);
		waitForSync(2);

	}
	/**
	 * Description..enter and validate ConsigneeCode in HAWB
	 * @param consigneeCode
	 * @throws Exception
	 */
	public void enterAndValidateConsigneeCodeHAWB(String consigneeCode) throws Exception {
		switchToWindow("storeFirstChild");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_consignee;name", data(consigneeCode), "Consignee", screenName);  
		performKeyActions("CaptureHAWB_OPR029", "inbx_consignee;name", "ENTER", "Consignee", screenName);
		waitForSync(3);
		switchToWindow("childWindow2");
		try {
			clickWebElement(sheetName, "chk_shipperConsigneeCode;xpath", "ConsigneeCode checkbox", screenName);
			waitForSync(3);
		}catch(Exception e)
		{}
		clickWebElement(sheetName, "btn_okShipperConsigneePopUp;xpath", "ConsigneeCode OK", screenName);
		waitForSync(3);
		switchToWindow("getFirstChild");
	}

	
	/**
	 * Description..enter ShipmentDetails and validate commodityCode
	 * @param Pieces
	 * @param Weight
	 * @param Volume
	 * @param CommodityCode
	 * @param ShipmentDesc
	 * @throws Exception
	 */
	public void enterShipmentDetailsAndValidateCommodityCode(String Pieces, String Weight, String Volume, String CommodityCode, String ShipmentDesc) throws Exception  {

		enterValueInTextbox(sheetName, "inbx_Pieces;name", data(Pieces), "Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_grossWeight;xpath", data(Weight), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_grossVol;xpath", data(Volume), "Volume", screenName);
		waitForSync(1);
		enterAndValidateCommodityCode(CommodityCode);
		performKeyActions(sheetName, "inbx_cmdtyCode;name", "TAB", "Commodity Code", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_ShipmentDesc;name", data(ShipmentDesc), "Shipment description", screenName);
		waitForSync(1);

	}
	/**
	 * Description..enter and validate CommodityCode
	 * @param CommodityCode
	 * @throws Exception
	 */
	public void enterAndValidateCommodityCode(String CommodityCode) throws Exception  {
		switchToWindow("storeParent");
		enterValueInTextbox(sheetName, "inbx_cmdtyCode;name", data(CommodityCode), "Commodity Code", screenName);
		performKeyActions(sheetName, "inbx_cmdtyCode;name", "ENTER", "CommodityCode", screenName);
		waitForSync(1);
		switchToWindow("child");
		try {
			clickWebElement(sheetName, "chk_shipperConsigneeCode;xpath", "CommodityCode checkbox", screenName);
		}catch(Exception e)
		{}
		clickWebElement(sheetName, "btn_okCommodityPopUp;xpath", "CommodityCode OK", screenName);
		waitForSync(1);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");
	}
	
	
	/**
	 * Description... Compare SCC with SCC in test data file
	 * @param SCC : SCC code
	 * @throws InterruptedException
	 */
	public void compareSCCs(String SCC1,String SCC2) throws InterruptedException 
	{
		boolean flag=false;
		String[] S1= SCC1.split(",");
		String[] S2= data(SCC2).split(",");
		int n1 = S1.length;
		int n2 = S2.length;
		for(int i=0;i<=n1-1;i++)
		{
			for(int j=0;j<=n2-1;j++)
			{
				if(S1[i].equals(S2[j]))
				{
					flag=true;
					return;
				}
			}           
		}
		if(flag==true)
		{
			writeExtent("Fail", "SCCs are equal");
		}
		else
		{
			writeExtent("Pass", "SCCs are not equal");
		}

	}

	/**
	 * @author A-9847
	 * @To capture the SPX checksheet 
	 */
	public void captureSPXChecksheet(){

		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		String airportGrpPath = "\\src\\resources\\AirportGroup.properties";
		String screeningAirport=getPropertyValue(airportGrpPath, "Screening");		
		String station=getLoggedInStation("OPR026");  

		if(testEnv.equals("RCT"))			
		{
			if(screeningAirport.contains(station))
			{
				if(verifySCCPresent("SPX")||verifySCCPresent("SHR"))
				{
					captureCheckSheet(true);
					waitForSync(1);
				}
			}
		}	

	}
	/**
	 * Returns true if the given SCC is present else returns false
	 * @param scc
	 * @return
	 */
	
	public boolean verifySCCPresent(String scc) {
		String actSCC = getAttributeWebElement(sheetName, "inbx_SCC;xpath", "SCC", "value", screenName);
		if (actSCC.contains(scc))
			return true;
		else
			return false;
			

	}
	/**
	 * Description... To click on Accept button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void acceptButton()throws InterruptedException, AWTException, IOException{
		clickWebElement(sheetName, "btn_accept;xpath","Accept Button", screenName); 
	}
	
	
	/**
	 * Description... Enter booking details
	 * @throws InterruptedException
	 */
	public void enterBookingDetailsSingleLeg(String Origin,String destination,String flightno,String flightdate, String pieces,String weight,String volume) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_bookingOrigin;xpath", data(Origin), "Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingDestination;xpath", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingFlightno;id", data(flightno), "Flight number", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingFlightDate;id", data(flightdate), "Flight date", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingPieces;id", data(pieces), "Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingweight;xpath", data(weight), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingVolume;xpath", data(volume), "Volume", screenName);
		selectValueInDropdown(sheetName, "lst_bookingForce;xpath", "Confirm", "Force", "VisibleText");		
	}
	
	
	/**
	 * Description : Entering Rate and Net Charges
	 * @author A-9175
	 * @param rateCharge
	 * @param netCharge
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void marketDetails(String rateCharge,String netCharge) throws InterruptedException, AWTException {

		String ratePivot=getAttributeWebElement(sheetName, "inbx_ratelineAppliedRate;name", "Rate line Pivot charges", "value", screenName);
		if(ratePivot.equals("0"))
			enterValueInTextbox(sheetName, "txt_netCharge;xpath", data(rateCharge), "Rate line Rate Charges", screenName);
		Thread.sleep(2000);
		if(netCharge.equals("0"))
			enterValueInTextbox(sheetName, "txt_netCharge;xpath", data(netCharge), "Rate line Net Charges", screenName);
		Thread.sleep(2000);
	}
	
	
	/**
	 * @throws InterruptedException
	 * @throws IOException
	 * Description : check verify and execute
	 */
	public void checkVerifyAndExecute() throws InterruptedException,IOException{
		checkIfUnchecked(sheetName, "chk_verifyAndExecute;name", "Verify and Execute checkbox", screenName);
	}
	
	
	/**
	 * Description... Check if SCC2(SCCs in SHR048) contains SCC1(SCCs in test data)
	 * @param SCC : SCC code
	 * @throws InterruptedException
	 */
	public void verifySCCs(String SCC1,String SCC2) throws InterruptedException 
	{
		boolean flag=false;
		String[] S2= data(SCC2).split(",");
		List<String> l = Arrays.asList(S2);
		int n = S2.length;
		for(int i=0;i<=n-1;i++)
		{
			if(l.contains(SCC1))
			{
				flag=true;
				break;
			}
		}
		if(flag==true)
		{
			writeExtent("Pass", l+" contains "+ SCC1);
		}
		else
		{
			writeExtent("Fail", l+" does not contains "+ SCC1);
		}

	}
	
	
	/**
	 * @author A-9478
	 * Description... Verify Agent name, IATA code and CASS code value is populating
	 * @throws Exception
	 */
	public void verifyValueIsDisplayedInAgentTab() throws Exception  
	{
		String agentName = getAttributeWebElement(sheetName, "inbx_AgentName;id",
				"Agent name", "value", screenName);
		String iataCode = getAttributeWebElement(sheetName, "inbx_AgentIATACode;id",
				"IATA code", "value", screenName);
		String cassCode = getAttributeWebElement(sheetName, "inbx_CASSCode;id",
				"CASS code", "value", screenName);
		if(!agentName.equals("") && !iataCode.equals("") && !cassCode.equals(""))
		{
			writeExtent("Pass", "Agent name, IATA Code,CASS code are populating in "+screenName+" Page . Values are " +
					"Agent name : "+agentName+"IATA Code : "+iataCode+" CASS Code : "+cassCode);
		}
		else
		{
			writeExtent("Fail", "Agent name, IATA Code,CSS code are not populating in "+screenName+" Page");
		}
	}

	/**
	 * 
	 * @param SCCValues
	 * @param Pieces
	 * @param Weights
	 * @throws Exception
	 * Desc : split shipment by entering the pcs / wt
	 */
	public void splitShipmentWithSCC(String SCCValues,String Pieces[],String Weights[]) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id",
				"Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		String[] sccVal = SCCValues.split(",");
		int sccLength = sccVal.length;
		enterValueInTextbox(sheetName, "inbx_splitCount;id",Integer.toString(sccLength), "Split into",
				screenName);
		clickWebElement(sheetName, "btn_Split;id",
				"Split", screenName);
		waitForSync(2);

		try
		{
			for(int i=1;i<=sccLength;i++)
			{
				String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
				pcsLoc = "("+pcsLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(pcsLoc)).click();
				driver.findElement(By.xpath(pcsLoc)).clear();
				driver.findElement(By.xpath(pcsLoc)).sendKeys(Pieces[i-1]);
				waitForSync(1);
				String sccLoc = xls_Read.getCellValue(sheetName, "btn_selectSCC;id");
				sccLoc = sccLoc.replaceAll("Index", Integer.toString(i-1));
				driver.findElement(By.id(sccLoc)).click();
				waitForSync(2);
				System.out.println(sccVal[i-1]);

				for(int j=0;j<sccVal[i-1].split(";").length;j++)
				{
					driver.findElement(By.xpath("(//span[contains(.,'"+sccVal[i-1].split(";")[j]+"')])["+i+"]")).click(); 
				}
				waitForSync(1);
				driver.findElement(By.id(sccLoc)).click();
				waitForSync(2);

				writeExtent("Pass","Successfully entered Pieces as" +Pieces[i-1]+" and SCC "+sccVal[i-1]+ "on" +screenName);

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't enter Pieces and SCC in "+screenName);
		}


		waitForSync(1);


		try
		{
			for(int i=1;i<=sccLength;i++)
			{
				String wgtLoc = xls_Read.getCellValue(sheetName, "inbx_splitWeights;xpath");
				wgtLoc = "("+wgtLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(wgtLoc)).click();
				driver.findElement(By.xpath(wgtLoc)).clear();
				driver.findElement(By.xpath(wgtLoc)).sendKeys(Weights[i-1]);

				waitForSync(2);

				writeExtent("Pass","Successfully entered Weight as" +Weights[i-1]+" on " +screenName);

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't enter Pieces and SCC in "+screenName);
		}
		waitForSync(1);



		clickWebElement(sheetName, "btn_splitShipmentOk;id",
				"Ok", screenName);


		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}
	/**
	 * @author A-9478
	 * Description... perform split scc
	 * @throws Exception
	 */
	public void splitShipmentWithSCC(String SCCValues,String Pieces[]) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id",
				"Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		String[] sccVal = SCCValues.split(",");
		int sccLength = sccVal.length;
		enterValueInTextbox(sheetName, "inbx_splitCount;id",Integer.toString(sccLength), "Split into",
				screenName);
		clickWebElement(sheetName, "btn_Split;id",
				"Split", screenName);
		waitForSync(2);
		try
		{
			for(int i=1;i<=sccLength;i++)
			{
				String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
				pcsLoc = "("+pcsLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(pcsLoc)).click();
				driver.findElement(By.xpath(pcsLoc)).clear();
				driver.findElement(By.xpath(pcsLoc)).sendKeys(Pieces[i-1]);
				waitForSync(1);
				String sccLoc = xls_Read.getCellValue(sheetName, "btn_selectSCC;id");
				sccLoc = sccLoc.replaceAll("Index", Integer.toString(i-1));
				driver.findElement(By.id(sccLoc)).click();
				waitForSync(2);
				System.out.println(sccVal[i-1]);

				for(int j=0;j<sccVal[i-1].split(";").length;j++)
				{
					driver.findElement(By.xpath("(//span[contains(.,'"+sccVal[i-1].split(";")[j]+"')])["+i+"]")).click(); 
				}


				waitForSync(1);
				driver.findElement(By.id(sccLoc)).click();
				waitForSync(2);

				writeExtent("Pass","Successfully entered Pieces as" +Pieces[i-1]+" and SCC "+sccVal[i-1]+ "on" +screenName);

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't enter Pieces and SCC in "+screenName);
		}


		waitForSync(1);
		clickWebElement(sheetName, "btn_splitShipmentOk;id",
				"Ok", screenName);


		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}

	/**
	 * @author A-8783
	 * Desc - Verify payment type
	 * @param paymentType
	 */
	public void verifyPaymentType(String paymentType) {
		String actPaymentType=getFirstSelectedOptionDropdown(sheetName,"lst_paymentType;name","Paymenttype");		
		verifyScreenTextWithExactMatch(sheetName, data(paymentType), actPaymentType, "Verify payment type", "Capture AWB");
		
	}

	/**
	 * @author A-9478
	 * Description... perform split scc with invalid split pcs
	 * @throws Exception
	 */
	public void splitShipmentWithSCCWithInvalidSplitPcs(String SCCValues,String Pieces[],String errorMsg) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id",
				"Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		String[] sccVal = SCCValues.split(",");
		int sccLength = sccVal.length;
		enterValueInTextbox(sheetName, "inbx_splitCount;id",Integer.toString(sccLength), "Split into",
				screenName);
		clickWebElement(sheetName, "btn_Split;id",
				"Split", screenName);
		waitForSync(2);
		try
		{
			for(int i=1;i<=sccLength;i++)
			{
				String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
				pcsLoc = "("+pcsLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(pcsLoc)).click();
				driver.findElement(By.xpath(pcsLoc)).clear();
				driver.findElement(By.xpath(pcsLoc)).sendKeys(Pieces[i-1]);
				waitForSync(1);
				String sccLoc = xls_Read.getCellValue(sheetName, "btn_selectSCC;id");
				sccLoc = sccLoc.replaceAll("Index", Integer.toString(i-1));
				driver.findElement(By.id(sccLoc)).click();
				waitForSync(2);
				System.out.println(sccVal[i-1]);

				for(int j=0;j<sccVal[i-1].split(";").length;j++)
				{
					driver.findElement(By.xpath("(//span[contains(.,'"+sccVal[i-1].split(";")[j]+"')])["+i+"]")).click(); 
				}


				waitForSync(1);
				driver.findElement(By.id(sccLoc)).click();
				waitForSync(2);

				writeExtent("Pass","Successfully entered Pieces as" +Pieces[i-1]+" and SCC "+sccVal[i-1]+ "on" +screenName);

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't enter Pieces and SCC in "+screenName);
		}


		waitForSync(1);
		clickWebElement(sheetName, "btn_splitShipmentOk;id",
				"Ok", screenName);
		String actualtext = getElementTextnoFrameSwitch(sheetName, "txt_errorMessage;xpath", "Error message", screenName);
		if(actualtext.contains(errorMsg))
		{
			writeExtent("Pass", "Successfully verified error message "+errorMsg+" in"+screenName);
		}
		else
		{
			writeExtent("Fail", "Couldn't verify error message "+errorMsg+" in"+screenName);
		}
		waitForSync(3);
		clickWebElement(sheetName, "btn_splitShipmentClose;id",
				"Close button", screenName);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}

	
	/**
	 * @author A-9478
	 * Description... Verify split pieces and Sccs
	 * @throws Exception
	 */
	public void verifySplitShipmentWithSCC(String SCCValues,String Pieces[]) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id",
				"Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		String[] sccVal = SCCValues.split(",");
		int sccLength = sccVal.length;
		waitForSync(2);
		try
		{
			for(int i=1;i<=sccLength;i++)
			{
				String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
				pcsLoc = "("+pcsLoc+")"+"["+i+"]";
				String actualText = driver.findElement(By.xpath(pcsLoc)).getAttribute("value");

				verifyScreenTextWithExactMatch(screenName,Pieces[i-1], actualText, " Split pieces ", " Split pieces Verified Sucessfully");
				String sccLoc = xls_Read.getCellValue(sheetName, "btn_selectSCC;id");
				sccLoc = sccLoc.replaceAll("Index", Integer.toString(i-1));
				String actualText2 = driver.findElement(By.id(sccLoc)).getAttribute("innerText");
				verifyScreenTextWithExactMatch(screenName,sccVal[i-1].replace(";", ","), actualText2, " Split SCC ", " Split SCC Verified Sucessfully");

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't verify split pieces and split SCCs values in "+screenName);
		}
		clickWebElement(sheetName, "btn_splitShipmentClose;id",
				"Close button", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}

	

	/**
	 * Description..modify shipment pieces
	 * @param pcs
	 * @throws InterruptedException
	 */
	public void modifyShipmentPcs(String[] pcs) throws InterruptedException
	{

		//ENTER TAB
		for(int i=0;i<pcs.length;i++)
		{
			String locator = xls_Read.getCellValue(sheetName, "inbx_rateLinePcs;xpath");
			locator=locator.replaceAll("index", Integer.toString(i+1));
			WebElement ele=driver.findElement(By.xpath(locator));
			ele.clear();
			ele.sendKeys(pcs[i]);
			performKeyActions(ele, "TAB", "Shipment Pieces", screenName);
		}
	}

	
	/**
	 * @author A-9478
	 * Description... modify split pieces
	 * @throws Exception
	 */
	public void modifySplitPieces(String rowCount,String Pieces[]) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id",
				"Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		int sccLength = Integer.parseInt(rowCount);
		waitForSync(2);
		try
		{
			for(int i=1;i<=sccLength;i++)
			{

				String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
				pcsLoc = "("+pcsLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(pcsLoc)).click();
				driver.findElement(By.xpath(pcsLoc)).clear();
				driver.findElement(By.xpath(pcsLoc)).sendKeys(Pieces[i-1]);
				waitForSync(1);
				writeExtent("Pass","Successfully modified Pieces as " +Pieces[i-1]+ " in " +screenName);

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't modify Pieces in "+screenName);
		}


		waitForSync(1);
		clickWebElement(sheetName, "btn_splitShipmentOk;id",
				"Ok", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}


	/**
	 * Description... To verify whether acceptance is finalised
	 * @param Acceptance_finalised_notfinalised
	 *            : Option to be verified e.g., finalised/ not finalised
	 * @throws InterruptedException
	 */
	public void verifyAcceptanceFinalized(String Acceptance_finalised_notfinalised) throws InterruptedException{

		switchToFrame("iCargoContentFrameOPR026");


		switch (Acceptance_finalised_notfinalised){

		case "finalised" :
			String actText = driver.findElement(By.xpath(".//*[@id='messagePane']/label[2]")).getText();

			System.out.println("Actual text is--" + actText);
			String expText = "Acceptance finalised";
			if (actText.equals(expText)) {
				System.out.println("Acceptance finalised");
				writeExtent("Pass", "Acceptance finalised");

			} else {
				System.out.println("Acceptance not finalised");
				writeExtent("Fail", "Acceptance not finalised");

			}
			break;
		case "not finalised" :
			String actText1 = driver.findElement(By.xpath(".//*[@id='messagePane']/label")).getText();

			System.out.println("Actual text is--" + actText1);
			String expText1 = " Acceptance not finalised";
			if (actText1.equals(expText1)) {
				System.out.println("Acceptance not finalised");
				writeExtent("Pass", "Acceptance not finalised");

			} else {

				System.out.println("Acceptance finalised");
				writeExtent("Fail", "Acceptance finalised");

			}
			break;
		}

		Thread.sleep(2000);

	}

	
	/**
	 * Description... Enter booking details
	 * @throws InterruptedException
	 */
	public void enterBookingDetailsSecondRow(String Origin,String destination,String flightno,String flightdate, String pieces,String weight,String volume) throws InterruptedException 
	{
		enterValueInTextbox(sheetName, "inbx_bookingOrigin2;xpath", data(Origin), "Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingDestination2;xpath", data(destination), "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingFlightno2;id", data(flightno), "Flight number", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingFlightDate2;id", data(flightdate), "Flight date", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingPieces2;id", data(pieces), "Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingweight2;xpath", data(weight), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_bookingVolume2;xpath", data(volume), "Volume", screenName);
		selectValueInDropdown(sheetName, "lst_bookingForce2;xpath", "Confirm", "Force", "VisibleText");		
	}

	
	/**
	 * Description... To select Service Cargo class value from the dropdown 
	 * @param: Service Cargo class
	 * @throws InterruptedException
	 */
	public void selectServiceCargoClass(String ServiceCargoClass) throws InterruptedException {
		Thread.sleep(2000);
		selectValueInDropdown(sheetName, "lst_ServiceCargoClass;xpath", data(ServiceCargoClass), "Service Cargo Class", "VisibleText");
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... 	Verify Charge Details
	 * @param ChargeCodeHead
	 * @throws InterruptedException
	 */
	public void verifyChargeDetails(String NetCharge, String IATACharge) throws InterruptedException{

		By element1 = getElement(sheetName, "inbx_iataCharge;name");
		String actText1 = driver.findElement(element1).getAttribute("value");
		verifyValueOnPage(actText1, NetCharge, "", screenName, "IATA Charge Verification");
		By element2 = getElement(sheetName, "txt_netCharge;xpath");
		String actText2 = driver.findElement(element2).getAttribute("value");
		verifyValueOnPage(actText2, IATACharge, "", screenName, "Net Charge Verification");

	}
	
	
	/**
	 * Description... Click OK Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickOkButton() throws InterruptedException, IOException
	{
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "Ok Button", screenName);
		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR026");
	}
	
	/**
	 * Description... To click on save button and handle the pop up by clicking
	 * on YES button
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void saveAWBDetails() throws InterruptedException, AWTException, IOException {

		clickGeneralTab();
		enterValueInTextbox(sheetName, "inbx_executionDate;id", currentDateUS().toUpperCase(), "ExecutionDate", screenName);
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		if(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_DGPopup2;xpath"))).size()==1)
		{
			clickWebElement("Generic_Elements", "btn_yes;xpath",
					"yes Button", screenName);
		}

	}
	/**
	 * @author A-10690
* Desc - Handles the dg pop up comes up while doing the awb execution
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void handleDG() throws InterruptedException, IOException{

	
	if(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName,"txt_DGPopup;xpath"))).size()==1)
	{
		clickWebElement("Generic_Elements", "btn_yes;xpath",
				"yes Button", screenName);
	}
	waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
	if(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_DGPopup2;xpath"))).size()==1)
		{
		clickWebElement("Generic_Elements", "btn_yes;xpath",
				"yes Button", screenName);
		
	}
	waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
}

	/**
	 * Description..Capture DGR details
	 * @param UNIDNo
	 * @param properShippingName
	 * @param netQuantityperPackage
	 * @param noOfPackage
	 * @param PerPackageUnit
	 * @param PI
	 * @param radioActive
	 * @throws Exception
	 */
	public void captureDGRDetails(String UNIDNo, String properShippingName,
			String netQuantityperPackage, String noOfPackage,
			String PerPackageUnit,String PI,boolean radioActive) throws Exception {
		waitForSync(3);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR026");
		driver.switchTo().frame("popupContainerFrame");    


		keyPress("SCROLLDOWNMOUSE");
		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", data(UNIDNo),
				"UNID No", screenName);

		performKeyActions(sheetName, "inbx_UNIDNumber;xpath", "TAB", "DGR Shipment", screenName);

		keyPress("TAB");
		keyRelease("TAB");  

		selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
				data(properShippingName), "Proper Shipping Name", "Value");

		waitForSync(1);
		if(radioActive)
		{
			selectValueInDropdown(sheetName, "lst_RMC;id",
					"1", "RMC dropdown", "Index");
			enterValueInTextbox(sheetName, "inbx_TI;id", "1",
					"Transport Index", screenName);
		}
		if(!radioActive)
		{
			enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
					data(netQuantityperPackage), "Net Quantity Per Package", screenName);
			selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
					data(PerPackageUnit), "Net Quantity Per Package Unit", "VisibleText");
		}     
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", data(noOfPackage),
				"No Of Package", screenName);       


		enterValueInTextbox(sheetName, "inbx_PI;xpath", data(PI),
				"PI", screenName); 

		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);

		waitForSync(6);

		clickWebElement(sheetName, "btn_Dgrok;xpath", "Ok Button", screenName);

	}
    /**
 * @author A-10330
 * Description... modify split pieces and weight
 * @throws Exception
 */
	public void modifySplitPiecesAndWeight(String rowCount,String Pieces[],String Weights[]) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id",
				"Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		int sccLength = Integer.parseInt(rowCount);
		waitForSync(2);
		try
		{
			for(int i=1;i<=sccLength;i++)
			{

				String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
				pcsLoc = "("+pcsLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(pcsLoc)).click();
				driver.findElement(By.xpath(pcsLoc)).clear();
				driver.findElement(By.xpath(pcsLoc)).sendKeys(Pieces[i-1]);
				waitForSync(1);
				writeExtent("Pass","Successfully modified Pieces as " +Pieces[i-1]+ " in " +screenName);

				String wgtLoc = xls_Read.getCellValue(sheetName, "inbx_splitWeights;xpath");
				wgtLoc = "("+wgtLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(wgtLoc)).click();
				driver.findElement(By.xpath(wgtLoc)).clear();
				driver.findElement(By.xpath(wgtLoc)).sendKeys(Weights[i-1]);

				waitForSync(2);
				writeExtent("Pass","Successfully entered Weight as" +Weights[i-1]+" on " +screenName);

			}


		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't modify Pieces in "+screenName);
		}


		waitForSync(1);
		clickWebElement(sheetName, "btn_splitShipmentOk;id",
				"Ok", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}

	/**
	 * @author A-10328
	 * Desc - Verify eCSD icon
	 * @throws InterruptedException
	 */
	public void verifyeCSDiconNotDisplayed() throws InterruptedException {
		try
		{
			String locator = xls_Read.getCellValue(sheetName, "img_eCSDicon;xpath");

			if((driver.findElements(By.id(locator)).size()==0)){

				writeExtent("Pass","Successfully verified eCSD icon is not displayed on "+screenName);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","eCSD icon is getting displayed on "+screenName);
		}

	}
	/**
	 * Description... Capture DGR Details
	 * @param UNIDNo
	 * @param properShippingName
	 * @param netQuantityperPackage
	 * @param noOfPackage
	 * @param PerPackageUnit
	 * @throws Exception
	 */

	public void captureDGRDetails(String UNIDNo, String properShippingName,
			String netQuantityperPackage, String noOfPackage,
			String PerPackageUnit,String PI) throws Exception {
		waitForSync(3);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR026");
		driver.switchTo().frame("popupContainerFrame");    


		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", data(UNIDNo),
				"UNID No", screenName);

		keyPress("TAB");
		keyRelease("TAB");    

		selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
				data(properShippingName), "Proper Shipping Name", "Value");

		waitForSync(1);

		enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
				data(netQuantityperPackage), "Net Quantity Per Package", screenName);
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", data(noOfPackage),
				"No Of Package", screenName);       
		selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
				data(PerPackageUnit), "Net Quantity Per Package Unit", "VisibleText");

		enterValueInTextbox(sheetName, "inbx_PI;xpath", data(PI),
				"PI", screenName); 

		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);

		waitForSync(6);

		clickWebElement(sheetName, "btn_Dgrok;xpath", "Ok Button", screenName);


	}
	
	/**
	 * @author A-9175
	 * Description : Save DGR Details
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void saveAWBDGR() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");

	}


	/**
	 * Description... To verify if the SCC codes are associated with the AWB
	 * @param SCCCondition
	 *            : Whether SCC should exist or not e.g., VerifySCCExists /
	 *            VerifySCCNotExists
	 * @param SCC
	 *            : SCC code to be verified e.g., EAP,EAW,GEN,MDC
	 */
	public void verifySCCCodes(String SCCCondition, String SCC) {
		waitForSync(6);
		switch (SCCCondition) {

		case "VerifySCCExists":
			ele = findDynamicXpathElement("inbx_SCC;xpath", sheetName, "SCC Codes", screenName);
			String actText = ele.getAttribute("value");
			String expText = SCC;
			verifyScreenText(sheetName, expText, actText, "Verify SCC codes", "Capture AWB");

			break;

		case "VerifySCCNotExists":
			ele = findDynamicXpathElement("inbx_SCC;xpath", sheetName, "SCC Codes", screenName);
			String actText1 = ele.getAttribute("value");
			String expText1 = SCC;
			verifyScreenTextNotExists(sheetName, expText1, actText1, "Verify SCC codes does not exists", "Capture AWB");
			break;
		}
	}

	
	/**
	 * Description... To provide shipper code and auto populate shipper details
	 * 
	 * @param shipperCode
	 *            : test data column name for shipper code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void provideShipperCode(String shipperCode) throws InterruptedException, AWTException {
		Thread.sleep(8000);
		enterValueInTextbox(sheetName, "inbx_shipperCode;xpath", data(shipperCode), "ShipperCode", screenName);
		performKeyActions(sheetName, "inbx_shipperCode;xpath", "TAB", "ShipperCode", screenName);
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... To add multiple custom information to the AWB
	 * 
	 * @param customsAuthority
	 *            : Array of Applicable customs authority e.g., German Customs,
	 *            Chinese Customs
	 * @param parameter
	 *            : Array of Customs parameter e.g., VUB
	 * @param CustomValue
	 *            : Array of Customs value e.g., I,C,H
	 * @param numberOfCustomAdd
	 *            : Total no of custom informations to be added e.g., 2
	 */
	public void addCustomInformation(String customsAuthority[],String parameter[],String CustomValue[],int numberOfCustomAdd){
		try{


			for(int i=0;i<numberOfCustomAdd;i++){

				clickWebElement(sheetName,"lnk_addCustomsRow;xpath","Add Customs RowAdd", screenName);
				waitForSync(2);
				String xpathCustomsAuthority=xls_Read.getCellValue(sheetName, "lst_customAuthority;xpath").replace("NUMBER", i+"");
				waitForSync(4);
				selectValueInDropdownWthXpath(xpathCustomsAuthority,customsAuthority[i], "Customs Authority", "VisibleText");
				waitForSync(2);
				String xpathCustomParameter=xls_Read.getCellValue(sheetName,"lst_customParameter;xpath").replace("NUMBER", (i+1)+"");
				waitForSync(4);
				selectValueInDropdownWthXpath(xpathCustomParameter, parameter[i], "Custome Parameter", "VisibleText");
				waitForSync(2);
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String javascript = "document.getElementsByName('customsValue')["+i+"].value='"+CustomValue[i]+"'";
				js.executeScript(javascript);
				waitForSync(2);
			}
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Description... To click on additional info button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickaddtionalInfo1() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_additionalInfo;xpath", "Additional Information Tab", screenName);
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... To click "NO" on MRN dialog box
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickMRNDialogBox()throws InterruptedException, AWTException, IOException{

		//Verify the text
		waitForSync(6);
		handleAlert("getText","CaptureAWB");

		clickWebElement("Generic_Elements", "btn_no;xpath","Additional Information Tab", screenName);
		switchToFrame("contentFrame","OPR026");

	}
	
	
	/**
	 * Description... To click "YES" on MRN dialog box
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickMRNDialogBoxY()throws InterruptedException, AWTException, IOException{
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath","Additional Information Tab", screenName);
		switchToFrame("contentFrame","OPR026");
		System.out.println("Clicked on Yes button");
	}
	
	
	/**
	 * Description... To click on As Is Execute button
	 * @throws Exception 
	 */
	public void asisExecuteButton()throws Exception{
		
		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		/********** REMOVE CUSTOMS BLOCK*****/
		if(testEnv.equals("RC4"))
		{
		removeCustomsBlock();
		}
		/*********************************/
		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/
		

		/**********************************/
		enterHSCode();
		/**********************************/

		clickWebElement(sheetName, "btn_AsIsExecute;xpath","Additional Information Tab", screenName); 
	}
	
	
	/**
	 * Description... To replace secure SCC value
	 * @param SCC
	 *            : test data column name for new SCC code
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void replaceSecureSCC(String SCC) throws InterruptedException, IOException
	{
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_deleteSecureSCC;xpath", "delete Secure SCC", screenName);
		Thread.sleep(5000);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);
		Thread.sleep(2000);

		ele = driver.findElement(By.xpath("//input[@name='newScc']"));

		ele.click();
		enterValueInTextbox(sheetName, "inbx_newSCC;xpath", data(SCC), "SCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(3000);

		//Getting the length of the object
		int elementCount = driver.findElements(By.xpath("//input[@id='deletebtn']")).size();

		for(int i=1;i<=elementCount;i++)
		{
			driver.findElement(By.xpath("(//input[@id='deletebtn'])["+i+"]")).click();
		}


	}
	
	
	/**
	 * Description... To delete customs informations without selecting a row and
	 * verifying error message
	 */	
	public void deleteCustomsInformation(){
		try{

			clickWebElement(sheetName, "btn_delete;xpath", "Click Delete Button", screenName);
			waitForSync(4);
			handleAlert("getText", screenName);
			String alertText= getPropertyValue(globalVarPath, "AlertText");
			if(alertText.contains(data("errorMsg"))){
				customFunction.onPassUpdate(screenName, data("errorMsg"), alertText, "capturing AWB ", "Deleting without selecting row");
			}else{
				customFunction.onFailUpdate(screenName, data("errorMsg"), alertText, "capturing AWB ", "Deleting without selecting row");
			}
			handleAlert("Accept", screenName);
			switchToFrame("contentFrame", "OPR026");
			waitForSync(2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Description... To provide consignee code
	 * @param consigneeCode
	 *            : test data column name for consignee code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void provideConsigneeCode(String consigneeCode) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_consigneeCode;xpath", data(consigneeCode), "ConsigneeCode", screenName);
		performKeyActions(sheetName, "inbx_consigneeCode;xpath", "TAB", "ConsigneeCode", screenName);
		Thread.sleep(2000);
	}
	public  void addHAWBDetails(String HAWB[], String Shipper[], String Consignee[], String Origin[], String Destination[], String Pieces[],String Weight[]) throws Exception {

		for(int i=0;i<HAWB.length;i++)
		{
			switchToWindow("child");
			clickWebElement("CaptureHAWB_OPR029", "inbx_houses;id", "Houses", screenName);
			waitForSync(2);
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_houses;id", data(HAWB[i]), "Houses", screenName);
			keyPress("TAB");
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_shipper;name", data(Shipper[i]), "Shipper", screenName);
			keyPress("TAB");
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_consignee;name", data(Consignee[i]), "Consignee", screenName);      
			keyPress("TAB");
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_origin;name", data(Origin[i]), "Origin", screenName);
			keyPress("TAB");  
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_destination;name", data(Destination[i]), "Destination", screenName);
			keyPress("TAB");
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_pieces;name", data(Pieces[i]), "Pieces", screenName);
			keyPress("TAB");
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_weigth;name", data(Weight[i]), "Weight", screenName);
			keyPress("TAB");
			waitForSync(2);
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_Desc;name", "HAWB Remarks", "Remarks", screenName);
			keyPress("TAB");
			enterHAWBHSCode();

            if(i<(HAWB.length-1))
				clickWebElement("CaptureHAWB_OPR029", "btn_addNew;id", "New House", screenName);
			else
				clickWebElement("CaptureHAWB_OPR029", "btn_hawbOK;id", "OK", screenName);

		}
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");

	}
	
	/**
	 * Description... To update customs authority name in multiple rows
	 * @param customsAuthorityUpdate
	 *            : New name for customs authority e.g., German Customs
	 * @param numberOfCustomSelection
	 *            : No of rows to be updated e.g., 1
	 */
	public void updateCustomsInfo(String customsAuthorityUpdate[],int numberOfCustomSelection){
		try{
			for(int i=0;i<numberOfCustomSelection;i++){

				String xpathCustomsAuthority=xls_Read.getCellValue(sheetName, "lst_customAuthority;xpath").replace("NUMBER", i+"");
				driver.findElement(By.xpath(xpathCustomsAuthority));
				waitForSync(2);
				selectValueInDropdownWthXpath(xpathCustomsAuthority,customsAuthorityUpdate[i], "Customs Authority Update", "VisibleText");
				waitForSync(4);
			}  


		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Description... Click the Booking Details Link
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickBookingDetails() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_bookingDetails;xpath", "Booking Details Button", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
	}
	
	
	/**
	 * Description... To click on as is execute button and error message should
	 * be displayed
	 * @throws Exception
	 */
	public void asIsExecuteButtonToVerfyErrorMsg() throws Exception {
		screenName = "Capture AWB";
		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		/**** REMOVE CUSTOMS BLOCK***/
		if(testEnv.equals("RC4"))
		{
		removeCustomsBlock();
		}
		/****************/
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		switchToWindow("storeParent");
		
		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
		  	
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/
		

		/**********************************/
		enterHSCode();
		/**********************************/

		clickWebElement(sheetName, "btn_AsIsExecute;xpath",
				"AsIsExecute Button", screenName);
		waitForSync(30);

	}
	
	
	/**
	 * Description... To verify customs information after changing customer
	 * authority to select, the customs parameter should be select and customs
	 * value should be null
	 */
	public void verifyCustomsInfoAfterReselect(){
		WebElement ele=driver.findElement(By.xpath("(//select[@name='customsParameter'])[1]"));
		Select select= new Select(ele);

		String actParamUpdate=select.getFirstSelectedOption().getText();
		String expParamUpdate="--Select--";

		if(actParamUpdate.contains(expParamUpdate)){
			customFunction.onPassUpdate(sheetName, expParamUpdate, actParamUpdate, "Parameter Value Check", "Value check after clear ");
		}else{
			customFunction.onFailUpdate(sheetName, expParamUpdate, actParamUpdate, "Parameter Value Check", "Value check after clear");
		}

		String expValueUpdate="";

		String actValueUpdate = driver.findElement(By.xpath("(//input[@name='customsValue'])[1]")).getText();
		if(expValueUpdate.contains(actValueUpdate)){
			customFunction.onPassUpdate(sheetName, expValueUpdate, actValueUpdate, "LOV Value Check", "LOV Value check after clear ");
		}else{
			customFunction.onFailUpdate(sheetName, expValueUpdate, actValueUpdate, "LOV Value Check", "LOV Value check after clear ");
		}
	}
	
	/**
	 * Description... ClickAsIsExecute Button
	 * @author A-10330
	 * @throws Exception
	 */
	public void clickAsIsExecute() throws Exception {
		try
		{
			screenName="Capture AWB";
String testEnv=getPropertyValue(globalVarPath, "testEnv");
			
			/*********************************/
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
			waitForSync(3);
			clickGeneralTab();
	   
		   enterExecutionDate();
		   /************ FRENCH CUSTOMS****/
			String station=getLoggedInStation("OPR026");  
			
			
			if(station.equals("CDG")) 
			{
				enterFrenchCustomsDetails();
				captureCDGCompChecksheet();
			}
			/**********************************/
			

			/**********************************/
			enterHSCode();
			/**********************************/

			clickWebElement(sheetName, "btn_AsIsExecute;xpath",
					"AsIsExecute Button", screenName);
			waitForSync(12);

		}
		catch(Exception e)
		{

		}
	}

	/**
	 * Description... To verify error message when zip code is missing for HAWB
	 * @param HAWB
	 *            : test data column name for House Airwaybill no 
	 * @throws InterruptedException
	 */	
	public void verifyErrorMessageIfZipCodeIsNotEntered(String HAWB) throws InterruptedException {

		try {
			String pmKey = data(HAWB);
			String expErrorMsg = ("Consignee Zip/Postal code is not specified for "
					.concat(data(HAWB)));
			String div = xls_Read.getCellValue(sheetName, "div_errorMsg;xpath");
			String dynxpath = div + "//td[contains(.,' "+ pmKey + "')]";

			String actErrMsg = driver.findElement(By.xpath(dynxpath)).getText();

			if (actErrMsg.contains(expErrorMsg)) {
				System.out.println("found true for ");

				onPassUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			} else {
				onFailUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	
	/**
	 * Description... To click on HAWB button without checking console check box
	 * and click yes on error displayed
	 * @throws Exception
	 */
	public void clickHAWBWithoutClickingOnConsole() throws Exception {
		screenName = "CaptureHAWB";
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		clickWebElement(sheetName, "btn_HAWB;name", "HAWB Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		try {
			ele = driver.findElement(By
					.xpath("//div[@class='ui-dialog-buttonset']//button[1]"));
			ele.click();
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description Execute AVI Shipment
	 * @throws Exception
	 */
	public void asIsExecuteAVI() throws Exception {
		switchToFrame("iCargoContentFrameOPR026");
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AsIsExecute;xpath",
				"AsIsExecute Button", screenName);
		Thread.sleep(30000);

		try {
			if (driver.getWindowHandles().size() == 2)

			{
				switchToWindow("child");
				clickWebElement(sheetName, "btn_continue_AVI;name",
						"Continue Button", screenName);
				switchToWindow("getParent");

			}
		} catch (Exception e) {

		}
		switchToFrame("default");
		for (int i = 0; i < 3; i++)
		{
			clickIfPopsUp();
			waitForSync(10);
		}
		switchToFrame("contentFrame", "OPR026");
		waitForSync(6);
		String actText = driver
				.findElement(
						By.xpath(xls_Read.getCellValue(sheetName,
								"txt_executed;xpath"))).getText();
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute",
				"Capture AWB");
		Thread.sleep(2000);
	}
	

	/**
	 * Description... To click on charges and accounting tab
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickChargesAcc() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_chargAndAcountg;xpath", "Charges and Accounting Tab", screenName);
		Thread.sleep(2000);
	}
	/**
	 * Description... To click on general tab
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickGeneralTab() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_generalTab;id", "General Tab", screenName);
		Thread.sleep(2000);
	}
	
	/**
	 * @author A-7271
	 * @throws Exception
	 * Desc : enter execution Date
	 */
	public void enterExecutionDate() throws Exception
	{
		String execDate=getAttributeWebElement(sheetName, "inbx_executionDate;id",
				"Execution Date", "defaultValue",screenName).toUpperCase();
		System.out.println(execDate);
		
		if(!execDate.equals(""))
		{

			if(data("Origin").equals("IAD"))
			{
				System.out.println(currentDateUS().toUpperCase());
				if(!execDate.equals(currentDateUS().toUpperCase()))
				{
					enterValueInTextbox(sheetName, "inbx_executionDate;id", currentDateUS().toUpperCase(), "ExecutionDate", screenName);
					waitForSync(1);
				}
			}
			else
			{
				if(!execDate.equalsIgnoreCase(createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam")))
				{
					enterValueInTextbox(sheetName, "inbx_executionDate;id", createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam"), "ExecutionDate", screenName);
					waitForSync(1);
				}
			}
		}
	}
	/**
	 * @author A-8783
	 * Desc- Enter instructions in scribble pad
	 * @param remark
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureInstruction(String remark) throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "btn_scribbleButton;id", "Scribble pad", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "txt_remarks;id", data(remark),"Remarks in scribble pad", screenName);
		clickWebElement(sheetName, "btn_scribbleOk;id", "Scribble pad OK", screenName);

	}

	/**
	 * Description... To provide rating details (IATA rate charges is not auto
	 * calculated and is blank)
	 * @param rateClass
	 *            : test data column name for rate class
	 * @param IATARate
	 *            : test data column name for IATA rate 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void provideRatingDetails(String rateClass, String IATARate,String IATACharge,String netCharge) throws InterruptedException, AWTException {
		selectValueInDropdown(sheetName, "lst_RateClass;xpath", data(rateClass), "RateClass", "VisibleText");
		clearText(sheetName, "inbx_IATARate;xpath", "IATARate", screenName);
		enterValueInTextbox(sheetName, "inbx_IATARate;xpath", data(IATARate), "IATARate", screenName);
		performKeyActions(sheetName, "inbx_IATARate;xpath", "TAB","IATARate", screenName);                              
		String rating=getAttributeWebElement("CaptureAWB_OPR026", "inbx_ratelineIataCharges;xpath", "Rate line Iata Charges", "value", screenName);
		if(rating.equals("0"))
			clearText(sheetName, "inbx_ratelineIataCharges;xpath", "IATACharge", screenName);
		enterValueInTextbox("CaptureAWB_OPR026", "inbx_ratelineIataCharges;xpath", data(IATACharge), "Rate line Iata Charges", screenName);
		Thread.sleep(2000);
		
		String netChargeLoc=xls_Read.getCellValue("CaptureAWB_OPR026","txt_netCharge;xpath");
		/********************************/
		//Verify if net charge field is disabled
		boolean netChargeReq=true;
		try
		{
			
			
			if(driver.findElement(By.xpath(netChargeLoc)).getAttribute("class").equals("is-readonly"))
			
				netChargeReq=false;
			
		}
		
		catch(Exception e){}
		/********************************/
		
		if(netChargeReq)
		{
		String netCharges=getAttributeWebElement("CaptureAWB_OPR026", "txt_netCharge;xpath", "Rate line Net Charges", "value", screenName);
		if(netCharges.equals("0"))
			enterValueInTextbox("CaptureAWB_OPR026", "txt_netCharge;xpath", data(netCharge), "Rate line Iata Charges", screenName);
		}
		Thread.sleep(2000);
	}

	/**
	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 * Desc : enter HS code
	 */
	public void enterHSCode() throws InterruptedException, AWTException, IOException{

		 //Click Charges and Accounting Tab
	     clickChargesAcc();
	     String hsCode= getAttributeWebElement(sheetName, "inbx_hsCode;id", "HS Code", "value", screenName);		
	     if(hsCode.equals("")) 
	    	 enterValueInTextbox(sheetName, "inbx_hsCode;id", "HS12345" , "HS Code", screenName);
	    	 
		}

	/**
	 * Description... To provide rating details (IATA rate charges is not auto
	 * calculated and is 0)
	 * @param rateClass
	 *            : test data column name for rate class
	 * @param IATARate
	 *            : test data column name for IATA rate
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void provideRatingDetails1(String rateClass, String IATARate) throws InterruptedException, AWTException {
		selectValueInDropdown(sheetName, "lst_RateClass;xpath", data(rateClass), "RateClass", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_IATARate;xpath", data(IATARate), "IATARate", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		keyPress("TAB");
		keyRelease("TAB");

		String rating=getAttributeWebElement("CaptureAWB_OPR026", "inbx_ratelineIataCharges;xpath", "Rate line Iata Charges", "value", screenName);
		if(rating.equals("0"))
			enterValueInTextbox("CaptureAWB_OPR026", "inbx_ratelineIataCharges;xpath", "10", "Rate line Iata Charges", screenName);
		Thread.sleep(2000);
	}
	
	/**
	 * @author A-6260
	 * Desc..Enter OCI details
	 * @param serialnumber
	 * @param countryCode
	 * @param informationID
	 * @param customsInfoID
	 * @param suplCustomsInfo
	 * @throws Exception
	 */
	public void enterOCIDetails(String[] serialnumber,String[] countryCode,String[] informationID, String[] customsInfoID, String[] suplCustomsInfo) throws Exception {
		int size=serialnumber.length;
		clickWebElement(sheetName, "btn_adlInfo;id", "Adl Info Tab", screenName);
		waitForSync(1);
		try
		{
			for(int i=0;i<size;i++) {
				clickWebElement(sheetName, "btn_addOCI;xpath", "Add OCI Button",screenName);
				String serialNumberLocator=xls_Read.getCellValue(sheetName,"inbx_serialNumber;xpath").replace("NUMBER", (i+1)+"");
				String countryCodeLocator=xls_Read.getCellValue(sheetName,"inbx_countryCode;xpath").replace("NUMBER", (i+1)+"");
				String informationIDLocator=xls_Read.getCellValue(sheetName,"inbx_informationID;xpath").replace("NUMBER", (i+1)+"");
				String customsInfoLocator=xls_Read.getCellValue(sheetName,"inbx_customsInfo;xpath").replace("NUMBER", (i+1)+"");
				String suplcustomsInfoLocator=xls_Read.getCellValue(sheetName,"inbx_suplCustomsInfo;xpath").replace("NUMBER", (i+1)+"");

				driver.findElement(By.xpath(serialNumberLocator)).sendKeys(serialnumber[i]);
				driver.findElement(By.xpath(countryCodeLocator)).sendKeys(countryCode[i]);
				driver.findElement(By.xpath(informationIDLocator)).sendKeys(informationID[i]);
				driver.findElement(By.xpath(customsInfoLocator)).sendKeys(customsInfoID[i]);
				driver.findElement(By.xpath(suplcustomsInfoLocator)).sendKeys(suplCustomsInfo[i]);


				
				
			}
			writeExtent("Pass", "Successfully entered OCI details  in "+screenName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writeExtent("Fail", "Couldn't enter OCI details in "+screenName);
		}
	}

	/**
	 * Description... To click on save button and handle the pop up by clicking
	 * on YES button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void saveAWB() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		String locator = xls_Read.getCellValue("Generic_Elements", "btn_Yes;xpath");
		
		try
		{
			while(driver.findElement(By.xpath(locator)).isDisplayed())
			{
				clickWebElement("Generic_Elements", "btn_Yes;xpath", "Yes Button", screenName);
				waitForSync(6);
			}
		}

		catch(Exception e)
		{

		}
		
		switchToFrame("contentFrame", "OPR026");


	}
	/**
	 * @author A-9847
	 * @Desc To handle checksheets with multiple formats
	 * @param chkSheetRequired
	 */
	public void captureChecksheetWithMultiFormats(boolean chkSheetRequired)
	{
		boolean checkSheetExists=true;	
		try
		{
		   clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",screenName);
		   waitForSync(3); 

			driver.switchTo().frame("popupContainerFrame");
			waitTillScreenload("Generic_Elements", "btn_save;xpath", "OK Button", screenName);
	
			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			List <WebElement> questions2=driver.findElements(By.xpath("//input[@title='Date']")); 
			List <WebElement> questions3=driver.findElements(By.xpath("//input[@title='Time']"));
			List <WebElement> questions4=driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));
			List<WebElement> questions5 = driver.findElements(By.xpath("//button[contains(@id,'CMP_Checksheet') and @class='ui-multiselect ui-widget ui-state-default ui-corner-all']"));
			
			if(questions.size()==0&&questions2.size()==0&&questions3.size()==0&&questions4.size()==0 && questions5.size() == 0)
				checkSheetExists=false;

			/***    Date Fields      ****/
			for(WebElement ele : questions2)
			{
				
				ele.sendKeys(createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			}
		
			/***    Time Fields      ****/
			for(WebElement ele : questions3)
			{
				ele.sendKeys("00:00");
			}
		
			/***    Text Areas      ****/
			for(WebElement ele : questions4)
			{
				ele.sendKeys("Yes");
				keyPress("TAB");
			}
			
			/** Select first option from DropDowns other than Yes/No/NA **/
			for (WebElement ele : questions5) {	
				moveScrollBar(ele);
				//Opening the options dialog box
				ele.click();	
				int i = questions5.indexOf(ele);
				//Selecting the first option from dialog box
				String dynamicXpath="(//input[contains(@id,'ui-multiselect-"+(i+1)+"-CMP_Checksheet_Defaults_CaptureCheckSheet')])[1]";
				driver.findElement(By.xpath(dynamicXpath)).click();
				//Closing the options dialog box
				driver.findElement(By.xpath(dynamicXpath+"/../../../..//a//span[@class='ui-icon ui-icon-circle-close']")).click();		
			}

			
			/** Yes/No DropDowns **/
			for (WebElement ele : questions)
			{		
				moveScrollBar(ele);
				new Select(ele).selectByVisibleText("Yes");
				keyPress("TAB");
				waitForSync(2);
				
				/****  Handling Any Obligatory questions - 'No' by checking warning symbol ***/			
				if(driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "warning_symbol;xpath"))).size()==1)
				{
					new Select(ele).selectByVisibleText("No");
					waitForSync(2);
				   
				}
			}

			/*******************************************************/	
			if(chkSheetRequired)
			{
				if(checkSheetExists)
					writeExtent("Pass","Check sheet details selected on "+screenName);
				else
					writeExtent("Info","No check sheet details configured on "+screenName); //To be changed to fail later	
			}
			
			waitForSync(2);
			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);

			waitForSync(2);
			switchToFrame("contentFrame", "OPR026");
			driver.switchTo().frame("popupContainerFrame");
			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
			waitForSync(1);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
			waitForSync(3);
		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not save check sheet details on "+screenName);
		}
	}
	/**
	 * @author A-9175
	 * Description : Deleting all existing Split shipment details and enters new Details
	 * @param SCCValues
	 * @param Pieces
	 * @throws Exception
	 */
	
	public void deleteSplitShipmentWithSCC(String SCCValues,String Pieces[]) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitShipment;id","Split Shipment", screenName);
		waitForSync(3);
		switchToWindow("child");
		waitForSync(3);
		clickWebElement(sheetName, "btn_checkAllSplitDetails;xpath","Check All", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_deleteSplitDetails;id","Delete", screenName);
		waitForSync(1);
		String[] sccVal = SCCValues.split(",");
		int sccLength = sccVal.length;
		for(int i=1;i<=sccLength;i++ )
		{
			clickWebElement(sheetName, "btn_addSplit;id","Add Split", screenName);
			waitForSync(2);
		}
		waitForSync(2);
		try
		{
			for(int i=1;i<=sccLength;i++)
			{
				String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
				pcsLoc = "("+pcsLoc+")"+"["+i+"]";
				driver.findElement(By.xpath(pcsLoc)).click();
				driver.findElement(By.xpath(pcsLoc)).clear();
				driver.findElement(By.xpath(pcsLoc)).sendKeys(Pieces[i-1]);
				waitForSync(1);
				String sccLoc = xls_Read.getCellValue(sheetName, "btn_selectSccIndex;xpath");
				sccLoc = sccLoc.replaceAll("index", Integer.toString(i));
				driver.findElement(By.xpath(sccLoc)).click();
				waitForSync(2);
				System.out.println(sccVal[i-1]);

				for(int j=0;j<sccVal[i-1].split(";").length;j++)
				{
					driver.findElement(By.xpath("(//span[contains(.,'"+sccVal[i-1].split(";")[j]+"')])["+i+"]")).click(); 
				}


				waitForSync(1);
				driver.findElement(By.xpath	(sccLoc)).click();
				waitForSync(2);

				writeExtent("Pass","Successfully entered Pieces as" +Pieces[i-1]+" and SCC "+sccVal[i-1]+ "on" +screenName);

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Couldn't enter Pieces and SCC in "+screenName);
		}


		waitForSync(1);
		clickWebElement(sheetName, "btn_splitShipmentOk;id",
				"Ok", screenName);


		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");
	}
	

/**
	 * @author A-8783
	 * Description... To perform as is execute without customs block release from OPR023
	 * @throws Exception
	 */
	public void asIsExecuteWithoutCustomBlockRelease() throws Exception {
		screenName="Capture AWB";
		screenName="Capture AWB";
		String testEnv=getPropertyValue(globalVarPath, "testEnv");


		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		switchToWindow("storeParent");
		clickGeneralTab();
	
		enterExecutionDate();
		/****** ENTER THE FRENCH CUSTOMS DETAILS****/


		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
		  	
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/
		

		/**********************************/
		enterHSCode();
		/**********************************/

		clickWebElement(sheetName, "btn_AsIsExecute;xpath",
				"AsIsExecute Button", screenName);
		waitForSync(10);
		switchToFrame("default");
		/*****waitForSync(10); ***/  
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		try {
			handleDG();
			while (driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
					.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
				/***Thread.sleep(20800);***/
				Thread.sleep(12000);
			}
		} catch (Exception e) {
		}

		/***Thread.sleep(12000);****/
		switchToFrame("contentFrame", "OPR026");
		/***Thread.sleep(12000);***/
		Thread.sleep(2000);
		
		waitTillScreenload(sheetName, "txt_executed;xpath","Executed text", screenName);
		String actText = driver
				.findElement(
						By.xpath(xls_Read.getCellValue(sheetName,
								"txt_executed;xpath"))).getText();
		/***waitForSync(5);***/
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute",
				"Capture AWB");
		/****Thread.sleep(2000);****/

	}


	 /**
	  * @author A-9847
	  * @Desc To verify the Stated pieces
	  * @param statedPcs
	  */
	public void verifyStatedPieces(String statedPcs)
	{
		
		String actpcs = getAttributeWebElement(sheetName, "inbx_statedPcs;xpath", "Stated Pieces", "value", screenName);
		verifyScreenText(sheetName, data(statedPcs), actpcs, "Stated Pieces", screenName);
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Stated Weight
	 * @param statedWgt
	 */
	public void verifyStatedWeight(String statedWgt)
	{
		
		String actwgt = getAttributeWebElement(sheetName, "inbx_statedWgt;xpath", "Stated Weight", "value", screenName);
		verifyScreenText(sheetName, data(statedWgt), actwgt, "Stated Weight", screenName);
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Stated Volume
	 * @param statedVol
	 */
	public void verifyStatedVolume(String statedVol)
	{
		
		String actvol = getAttributeWebElement(sheetName, "inbx_statedVolume;xpath", "Stated Volume", "value", screenName);
		verifyScreenText(sheetName, data(statedVol), actvol, "Stated Volume", screenName);
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Origin
	 * @param origin
	 */
	public void verifyOrigin(String origin)
	{
		
		String actOrigin = getAttributeWebElement(sheetName, "inbx_Origin;xpath", "Origin", "value", screenName);
		verifyScreenText(sheetName, data(origin), actOrigin, "Origin", screenName);
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Destination
	 * @param destination
	 */
	public void verifyDestination(String destination)
	{
		
		String actDestination = getAttributeWebElement(sheetName, "inbx_Destination;xpath", "Destination", "value", screenName);
		verifyScreenText(sheetName, data(destination), actDestination, "Destination", screenName);
		
	}
	


	/***
	 * @author A-7271
	 * Verify if houses are saved successfully
	 * @throws InterruptedException 
	 */
	public void verifyHouses(String houseAwb) throws InterruptedException
	{

		try
		{
			By b = getElement(sheetName, "htmlDiv_houses;xpath");
			String actText = driver.findElement(b).getAttribute("innerText");

			if(actText.contains(data(houseAwb)))
			{
				writeExtent("Pass","HAWB "+data(houseAwb)+" captured on "+screenName);

			}

			else
			{
				writeExtent("Fail","HAWB "+data(houseAwb)+" not captured on "+screenName);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","HAWB "+data(houseAwb)+" not captured on "+screenName);
		}
	}
	
	
	/**
	 * Description... To click on save button and handle the pop up by clicking
	 * on YES button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void saveAWBWithDGRDetails() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		try{		
			while (driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]|//button[contains(.,'OK')]"))
					.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
				Thread.sleep(3000);
			}

		}
		catch(Exception e)
		{

		}

		driver.switchTo().frame("iCargoContentFrameOPR026");

	}
	/**
	 * @author A-9844
	 * @Desc To verify the given warning message during As is Execute
	 * @param warningmsg
	 * @throws Exception
	 */
	
	public void clickAsIsExecuteAndVerifyWarningNotDisplayed(String warningmsg) throws Exception{

		boolean warningMsgDisplayed=false;
	

		clickWebElement(sheetName, "btn_AsIsExecute;xpath","AsIsExecute Button", screenName);
		waitForSync(6);
		switchToFrame("default");
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		

		try {
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				String actText=getElementText("Generic_Elements", "htmlDiv_msgStatus;xpath","warning msg", screenName);
				System.out.println(actText);
				if(actText.contains(warningmsg))
				{
					warningMsgDisplayed=true;
					
				}
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
				waitForSync(8);
			}
			
		}

		catch(Exception e)
		{

		}
		if(!warningMsgDisplayed)
			writeExtent("Pass","Message "+warningmsg+ " is not displayed on "+screenName);
		else
			writeExtent("Fail","Message "+warningmsg+ " is displayed on"+screenName);
		switchToFrame("contentFrame", "OPR026");
	}

	 /**
	   * @author A-9847
	   * @Desc To verify whether the Shipments contains DGR Sccs
	   * @param awb
	   * @return
	   * @throws InterruptedException
	   */
	  public boolean verifyDGR(String awb) throws InterruptedException{

			try{
			By element = getElement(sheetName, "txt_sccText;xpath");
			String actSCCs= driver.findElement(element).getAttribute("value");
			
			System.out.println(actSCCs);
			
			String dgrSCCs=getPropertyValue(grouping, "DGR");
			
			for(int i=0;i<actSCCs.split(",").length;i++)
			{
				if(dgrSCCs.contains(actSCCs.split(",")[i]))
					return true;
			}
			
			return false;
			
			}
			catch (Exception e) {
				writeExtent("Fail", "Check the  SCC for the AWB "+awb);
				return false;
			}

		}
	  
	/**
	 * Description... To click on save button and handle the pop up by clicking
	 * on YES button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void saveAWBWithVerification(String message) throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");

		clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");

	}
	
	
	/**
	 * Description... To click on save button and handle the pop up by clicking
	 * on OK button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void saveAWBClickOK() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_OK;xpath", "Ok Button", screenName);
		Thread.sleep(2000);

	}
	
	
	/**
	 * Description... To save AWB with discrepancies and handling the alert
	 * message
	 * @param alertMsg
	 *            : Expected alert message when there is some discrepancy
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void saveAWBWithDiscrepancy(String alertMsg) throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		Thread.sleep(4000);
		switchToFrame("default");
		String actText = driver.findElement(By.xpath("//*[@id='ic-sd-msgc']")).getText();

		System.out.println("Actual text is--" + actText);
		String expText=alertMsg;
		if (actText.contains(expText)) {
			verifyScreenText(sheetName, expText, actText, "Discrepancy in awb execution", screenName);

		} else {
			verifyScreenText(sheetName, expText, actText, "No discrepancy in awb execution",
					screenName);
		}
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes Button", screenName);
		Thread.sleep(4000);

		//Verify Save
		String actText2 = driver.findElement(By.xpath("//*[@id='ic-sd-msgco']")).getText();

		System.out.println("Actual text is--" + actText);
		String expText2 = "saved successfully";
		if (actText2.contains(expText2)) {
			verifyScreenText(sheetName, expText2, actText2, "Awb saved successfully", screenName);

		} else {
			verifyScreenText(sheetName, expText2, actText2, "Awb not saved successfully",
					screenName);
		}
		clickWebElement("Generic_Elements", "btn_OK;xpath", "Ok Button", screenName);
		Thread.sleep(4000);

	}
	
	
	/**
	 * Description... To click on add button in OCI
	 * @param noOfRows
	 *            : No of OCI entries to be added e.g., 1,2
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void addOCIInfo(int noOfRows) throws InterruptedException, IOException
	{
		for(int i=0;i<noOfRows;i++)
		{
			clickWebElement(sheetName, "lnk_addRow;xpath", "Add additional info", screenName);
			Thread.sleep(2000);
		}
	}
	
	
	/**
	 * Descrition : To verify if OCI fields are editable
	 * @param slNum
	 *            : serial no e.g., 1
	 * @param isoCode
	 *            : ISO country code e.g., DE
	 * @param infoId
	 *            : Information id e.g., A125B
	 * @param customsInfoId
	 *            : Customs information id e.g., 456DE
	 * @param supplCustomsInfoId
	 *            : Supplementary customs information id e.g., 020LH
	 * @throws InterruptedException
	 */
	public void verifyOCIFieldsWhetherEditable(String slNum,String isoCode,String infoId,String customsInfoId,String supplCustomsInfoId) throws InterruptedException
	{

		//Serial number

		enterValueInTextbox(sheetName, "inbx_serialNum;xpath", slNum, "Serial number", screenName);

		//ISO code	
		enterValueInTextbox(sheetName, "inbx_isoCode;xpath", isoCode, "ISO Code", screenName);

		//Info id
		enterValueInTextbox(sheetName, "inbx_infoId;xpath", infoId, "Info Id", screenName);

		//Customs info id

		enterValueInTextbox(sheetName, "inbx_customsInfoId;xpath", customsInfoId, "Customs Info Id", screenName);

		//Suppl Customs Info Id
		enterValueInTextbox(sheetName, "inbx_supplCustomsInfo;xpath", supplCustomsInfoId, "Supplimentary Info Id", screenName);

	}
	/**
	 * @author A-8783
	 * Description : enterFrenchCustomsDetails
	 * @throws IOException 
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
  public void enterFrenchCustomsDetails(String infoId,String mrn) throws InterruptedException, AWTException, IOException
  {
		  clickWebElement(sheetName, "btn_customsInfoAdd;xpath","Customs Info Add Button", screenName);
		  selectValueInDropdown(sheetName, "lst_customsAuthority;xpath","French Customs", "Customs Authority", "VisibleText");
		  waitForSync(1);
		  keyPress("TAB");
		  waitForSync(2);
		  selectValueInDropdown(sheetName, "lst_customsParameter;xpath",data(infoId), "Customs Parameter", "VisibleText");
		  waitForSync(1);
		  keyPress("TAB");
		  waitForSync(1);
		  enterValueInTextbox(sheetName, "inbx_customsInfoValue;xpath", data(mrn), "Customs Info Value", screenName);		
		  waitForSync(1);
	  
  }
  /**
	 * @author A-8783
	 * Desc - Check source is not editbale 
	 */
  public void verifySourceNonEditable(){
	  String notEditable = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_source;xpath").replace("*","1"))).getAttribute("readonly");
	  if(notEditable.equals("true")){
		  writeExtent("Pass", "The source field is not editable");
	  }
	  else{
		  writeExtent("Fail", "The source field is editable");
	  }
  }

	
	/**
	 * Description... To select rating details row and click on auto rate button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void autoRate() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "chk_RatingDetails;xpath", "Rating checkbox", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_AutoRate;xpath", "Auto Rate Button", screenName);
		waitForSync(4);
	}
	
	
	/**
	 * Description... to click on calculate charges button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickCalcCharges() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_calCharges;name", "Calculate Charges Button", screenName);
		waitForSync(8);
	}
	
	
	/**
	 * Description... To click on compute total button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickComputeTotal() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_computeTotal;name", "Compute Total Button", screenName);
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... Verify prepaid amount
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */

	public void verifyPrepaidAmount() throws InterruptedException, AWTException, IOException
	{
		String locator = xls_Read.getCellValue(sheetName, "txt_PrepaidAmount;id");
		String actualValue = driver.findElement(By.id(locator)).getAttribute("value");
		if(actualValue!="0")
		{
			writeExtent("Pass", "Prepaid amount "+actualValue+" is displayed in "+screenName);
		}
		else
		{
			writeExtent("Fail", "Prepaid amount "+actualValue+" is displaying in "+screenName);
		}
		waitForSync(2);
	}

	
	/**
	 * Description... To capture security and screening details 
	 * @param SecSCC : test data column name for Secured SCC 
	 * @throws Exception
	 */
	// below method is for security and screening screen
	public void securityAndScreeingScreen(String SecSCC) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		switchToWindow("child");
		Thread.sleep(2000);
		switchToFrame("default");
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_SCC;xpath", data(SecSCC), "SecSCC", screenName);
		Thread.sleep(1000);
		clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath", "Security Checkbox", screenName);
		clickWebElement(sheetName, "btn_OK;id", "OK Button", screenName);
		Thread.sleep(2000);

	}
	
	
	/**
	 * Description... To provide security and screening, when shipment details tab is not opened by default
	 * @param SecSCC : test data column name for Secured SCC 
	 * @throws Exception
	 */
	// below method is for security and Screening popup
	public void securityAndScreeing(String SecSCC) throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(4000);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");

		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);
		Thread.sleep(2000);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		Thread.sleep(1000);
		enterValueInTextbox(sheetName, "inbx_newSCC;xpath", data(SecSCC), "SecSCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);
		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

	}
	/**
	 * Description... To do security and screening without giving Secured SCC
	 * @throws Exception
	 */
	public void securityAndScreeingScreenWithoutSCC() throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(4000);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);

		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");
		Thread.sleep(2000);
		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... To sav security and screening details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void saveSecurityAndScreeningDetails() throws InterruptedException, IOException
	{
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

	}
	

/**
	 * @author A-9847
	 * @Desc To enter the HS Code at HAWB level
	 * @throws InterruptedException
	 */
	public void enterHAWBHSCode() throws InterruptedException{

		String hsCode= getAttributeWebElement("CaptureHAWB_OPR029", "inbx_hawbHS;xpath", "HS Code", "value", screenName);		
		if(hsCode.equals("")) 		
			enterValueInTextbox("CaptureHAWB_OPR029", "inbx_hawbHS;xpath", "HS12345" , "HS Code", screenName);

	}


	/**
	 * Description... To click on reopen button
	 * @throws InterruptedException
	 */
	public void clickReopen() throws InterruptedException {

		waitForSync(5);
		javaScriptToclickElement(sheetName, "button_Reopen;xpath", "Reopen Button", screenName);

	}

	
	/**
	 * Description... To open security and screening window
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void openSecurityAndScreening() throws InterruptedException, IOException
	{
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");
	}
	
	
	/**
	 * Description... To modify SCC in security and screening
	 * @param SecSCC : test data column name for new SCC value
	 * @throws Exception
	 */
	public void securityAndScreeingModifySCC(String SecSCC) throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");

		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);
		Thread.sleep(2000);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		Thread.sleep(1000);
		enterValueInTextbox(sheetName, "inbx_newSCC;xpath", data(SecSCC), "SecSCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);

		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

		String actText = getElementText(sheetName, "htmlDiv_errorMsgSec;xpath",
				"errorMsg", screenName);
		String expText = "AWB is executed. Only Special SCCs can be modified.";

		verifyScreenText(sheetName, expText, actText, "SCC modify in security & screening", screenName);
		Thread.sleep(2000);


	}
	
	
	/**
	 * Description... To update stated pieces, weight and volume
	 * @param statedPieces : test data column name for stated pieces
	 * @param statedWeight : test data column name for stated weight
	 * @param StatedVolume : test data column name for stated volume
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void updateStatedValues(String statedPieces, String statedWeight, String StatedVolume)
			throws InterruptedException, AWTException {
		waitForSync(3);
		keyPress("SCROLLDOWNMOUSE");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_stated_Pieces;xpath", data(statedPieces), "Stated pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_stated_weight;name", data(statedWeight), "Stated weight", screenName);
		enterValueInTextbox(sheetName, "inbx_stated_Volume;xpath", data(StatedVolume), "Stated Volume", screenName);
		waitForSync(2);
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : acceptMsgOnExecution
	 */
	public void acceptMsgOnExecution() throws InterruptedException, IOException
	{
		try
		{
			switchToFrame("default");
		while (driver.findElement(
				By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
				.isDisplayed()) {
			clickWebElement("Generic_Elements", "btn_yes;xpath",
					"yes Button", screenName);
			Thread.sleep(10000);
		}
		}
		
		catch(Exception e)
		{
			
		}
		
		finally
		{
			switchToFrame("contentFrame", "OPR026");
		}
		
	}
	/**
	 * Description... To perform as is execute, icargo dimensions module
	 * @throws Exception
	 */
	public void asIsExecuteDim() throws Exception {
		waitForSync(10);
		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		
		/**** REMOVE CUSTOMS BLOCK***/
		if(testEnv.equals("RC4"))
		{
		removeCustomsBlock();
		}
		/****************/
		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/
		

		/**********************************/
		enterHSCode();
		/**********************************/

		clickWebElement(sheetName, "btn_AsIsExecute;xpath", "AsIsExecute Button", screenName);
		waitForSync(8);
		switchToFrame("default");
		waitForSync(8);
		try{		
			while (driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
					.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
				Thread.sleep(10000);
			}

		}
		catch(Exception e)
		{

		}
		waitForSync(8);
		switchToFrame("contentFrame", "OPR026");
		waitForSync(8);
		String actText = driver
				.findElement(
						By.xpath(xls_Read.getCellValue(sheetName,
								"txt_executed;xpath"))).getText();
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute",
				"Capture AWB");
		Thread.sleep(2000);

	}

	/*
	 * public void asIsExecute() throws Exception { switchToWindow("getParent");
	 * switchToFrame("contentFrame", "OPR026"); clickWebElement(sheetName,
	 * "btn_AsIsExecute;xpath", "AsIsExecute Button", screenName);
	 * waitForSync(6); switchToFrame("default"); try { Thread.sleep(6000);
	 * switchToFrame("default");
	 * 
	 * while (driver.findElement(By.xpath(
	 * "//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
	 * clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button",
	 * screenName); Thread.sleep(6000); } } catch (Exception e) { }
	 * 
	 * Thread.sleep(2000); switchToFrame("contentFrame", "OPR026");
	 * Thread.sleep(2000); String actText =
	 * driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,
	 * "txt_executed;xpath"))).getText(); String expText = "Executed";
	 * verifyScreenText(sheetName, expText, actText, "As is Execute",
	 * "Capture AWB"); Thread.sleep(2000);
	 * 
	 * }
	 */
	
	
	/**
	 * Description... To perform as is execute
	 * @throws Exception
	 */
	public void asIsExecuteScreen() throws Exception {
		switchToWindow("storeParent");
		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		
		/**** REMOVE CUSTOMS BLOCK***/
		if(testEnv.equals("RC4"))
		{
		removeCustomsBlock();
		}
		/****************/
		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/
		

		/**********************************/
		enterHSCode();
		/**********************************/

		clickWebElement(sheetName, "btn_AsIsExecute;xpath", "AsIsExecute Button", screenName);
		waitForSync(6);
		switchToFrame("default");
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);


		try {
			Thread.sleep(6000);
			switchToFrame("default");

			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				Thread.sleep(6000);
			}
		} catch (Exception e) {
		}

		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR026");
		Thread.sleep(2000);
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_executed;xpath"))).getText();
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute", "Capture AWB");
		Thread.sleep(2000);

	}
	
	/**
	 * Description : Verifying status of AWB
	 * @author A-8783
	 * @param ExecutionStatus
	 * @throws InterruptedException
	 */
	public void verifyAWBStatus(String awbStatus) throws InterruptedException
	{
		
		try
		{
			By status = getElement(sheetName, "txt_executed;xpath");
			String actText = driver.findElement(status).getText();

			if(actText.contains(data(awbStatus)))
			{
				writeExtent("Pass","AWB status is "+data(awbStatus)+" on "+screenName);

			}

			else
			{
				writeExtent("Fail","AWB status is not"+data(awbStatus)+" on "+screenName);
				
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","AWB status "+data(awbStatus)+" is not displayed on "+screenName);
			
		}
	}
	/**
	 * @author A-10690
	 * Desc- verify the expected scc not present in the scc field
	 * @param SCC
	 */
	public void verifySCCNotPresent(String scc) {
		String actSCC = getAttributeWebElement(sheetName, "inbx_SCC;xpath", "SCC", "value", screenName);
		if (!actSCC.contains(scc))
			writeExtent("Pass", "Successfully verified second scc"+scc+" not available  "+screenName);
			
		else
			writeExtent("Fail", "Failed to verify that scc not present"+scc+"in"+screenName);

	}
	/**
	 * Desc: Verifying all mandatory XFWB Components
	 * @author A-9175
	 * @param components
	 * @throws Exception
	 */
	public void verifyXFWBMandatoryComponents(List<String> components)throws Exception {

		List<String>ActValues=new ArrayList<String>();
		List<String>VerifyValues=new ArrayList<String>();
		/***ORIGIN**/
		By elementOrigin = getElement(sheetName, "inbx_Origin;xpath");
		String actTextOrigin = driver.findElement(elementOrigin).getAttribute("value");ActValues.add(actTextOrigin);
		VerifyValues.add("Origin Verification");

		/***DESTINATION***/
		By elementDestination = getElement(sheetName, "inbx_Destination;xpath");
		String actTextDestination = driver.findElement(elementDestination).getAttribute("value");ActValues.add(actTextDestination);
		VerifyValues.add("Destination Verification");

		/***ROUTING**/
		By elementCarrierCode = getElement(sheetName, "inbx_routingCarrier;id");
		String actTextCarrierCode = driver.findElement(elementCarrierCode).getAttribute("value");ActValues.add(actTextCarrierCode);
		VerifyValues.add("Carrier Code Verification");

		By routingTo = getElement(sheetName, "inbx_routingAirport;id");
		String actRoutingTo = driver.findElement(routingTo).getAttribute("value");ActValues.add(actRoutingTo);
		VerifyValues.add("Routing To Verification");

		/****AGENT**/
		By elementAgent = getElement(sheetName, "inbx_AgentCode;xpath");
		String actTextAgentCode = driver.findElement(elementAgent).getAttribute("value");ActValues.add(actTextAgentCode);
		VerifyValues.add("Agent Code Verification");

		/****SHIPPER**/
		By elementShipper = getElement(sheetName, "inbx_shipperCode;xpath");
		String actShipper = driver.findElement(elementShipper).getAttribute("value");ActValues.add(actShipper);
		System.out.println(actShipper);
		VerifyValues.add("Shipper Verification");

		/***CONSIGNEE**/
		By elementConsignee = getElement(sheetName, "inbx_consigneeCode;xpath");
		String actConsignee = driver.findElement(elementConsignee).getAttribute("value");ActValues.add(actConsignee);
		VerifyValues.add("Consignee Verification");

		By elementPieces = getElement(sheetName, "inbx_Pieces;name");
		String actPcs = driver.findElement(elementPieces).getAttribute("value");ActValues.add(actPcs);
		VerifyValues.add("Pieces Verification");

		/****SHIPEMT INFO**/
		By elementWgt = getElement(sheetName, "inbx_grossWeight;xpath");
		String actWgt = driver.findElement(elementWgt).getAttribute("value");ActValues.add(actWgt);
		VerifyValues.add("Weight Verification");

		By elementCommodity = getElement(sheetName, "inbx_cmdtyCode;name");
		String actCommodity = driver.findElement(elementCommodity).getAttribute("value");ActValues.add(actCommodity);
		System.out.println(actCommodity);
		VerifyValues.add("Commodity Code Verification");

		for(int i=0;i<components.size();i++)
			
			if(!VerifyValues.get(i).equals("Commodity Code Verification")&&!VerifyValues.get(i).equals("Shipper Verification")&&
					!VerifyValues.get(i).equals("Consignee Verification"))
			{
			verifyScreenText(sheetName, components.get(i), ActValues.get(i),VerifyValues.get(i), screenName);
			}
	}
	/*desc:-to verify user added mrn value
	 *@Param:customs value 
	 */
	
	public void  verifyMrnadded(String customsvalue)
	{
		String actText=getAttributeWebElement(sheetName, "tbl_inputMrnValue;xpath",
				"MRN value","value","capture awb page");
		
		verifyScreenText(sheetName, customsvalue, actText, "added Mrn number", "Mrn umber added");
		
	}
	
	/**
	 * @author A-9478
	 * Description... Enter notification details 
	 * @throws Exception 
	 */
	public void enterNotificationDetails(String NotifyCode,String NotifyName,String TelNumber,String countryCode) throws Exception 
	{
		switchToWindow("storeParent");  
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_NotifyCode;id", data(NotifyCode), "Notify Code", screenName);
		enterValueInTextbox(sheetName, "inbx_NotifyName;id", data(NotifyName), "Notify Name", screenName);
		enterValueInTextbox(sheetName, "inbx_TelNumber;id", data(TelNumber), "Tel Number", screenName);
		enterValueInTextbox(sheetName, "inbx_CountryCode;id", data(countryCode), "Country Code", screenName);
		clickWebElement(sheetName, "btn_NotificationOK;id", "OK button", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
	}
	/**
	 * @author A-9847
	 * @Desc To capture the CDG COMPLIANCE checksheet
	 */
	public void captureCDGCompChecksheet(){
		
		captureChecksheetWithMultiFormats(true);
		
	}
	/**
	 * @author A-9844
	 * Desc - Verify LAT is not stamped
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verfyLATIsNotStamped() throws InterruptedException, IOException{
		try
		{


			clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
			waitForSync(3);
			driver.switchTo().frame("popupContainerFrame");
			int size=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).size();
			if(size==0){
				writeExtent("Pass", "LAT Details are not present on "+screenName);
			}
			else{
				writeExtent("Fail", "LAT Details are present on "+screenName);
			}
			switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameOPR026");
			handleShipmentStatusPopUp();
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not verify LAT on "+screenName);
		}

	}


	/**
	 * @author A-9844
	 * @Desc To verify the given warning message during As is Execute
	 * @param warningmsg
	 * @throws Exception
	 */

	public void clickAsIsExecuteAndVerifyBookingWarning(String warningmsg) throws Exception{

		boolean warningMsgDisplayed=false;
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		clickGeneralTab();
		enterExecutionDate();

		waitForSync(3);
		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  


		if(station.equals("CDG")) 
		{

			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/


		/**********************************/
		enterHSCode();
		/**********************************/


		clickWebElement(sheetName, "btn_AsIsExecute;xpath","AsIsExecute Button", screenName);
		waitForSync(6);
		switchToFrame("default");
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);


		try {
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				String actText=getElementText("Generic_Elements", "htmlDiv_msgStatus;xpath","warning msg", screenName);
				System.out.println(actText);
				if(actText.contains(warningmsg))
				{
					warningMsgDisplayed=true;
                    
				}
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
				waitForSync(8);
			}

			
		}

		catch(Exception e)
		{

		}
		if(warningMsgDisplayed)
			writeExtent("Pass","Message "+warningmsg+ " displayed on "+screenName);
		else
			writeExtent("Fail","Message "+warningmsg+ " not displayed on"+screenName);
		switchToFrame("contentFrame", "OPR026");
	}


	   /**
		 * @author A-9847
		 * @Desc To click on AsIsExecute button without entering french customs
		 * @throws Exception
		 */
		public void asIsExecuteonly() throws Exception {
			try
			{
				screenName="Capture AWB";
				String testEnv=getPropertyValue(globalVarPath, "testEnv");
				/**** REMOVE CUSTOMS BLOCK***/
				if(testEnv.equals("RC4"))
				{
				removeCustomsBlock();
				}
				/****************/
				switchToFrame("default");
				switchToFrame("contentFrame", "OPR026");
				waitForSync(3);
				String station=getLoggedInStation("OPR026");  
				if(station.equals("CDG")) 
					captureCDGCompChecksheet();
				

			


				clickGeneralTab();
				enterExecutionDate();
				/**********************************/
				enterHSCode();
				/**********************************/
				clickWebElement(sheetName, "btn_AsIsExecute;xpath","AsIsExecute Button", screenName);
				waitForSync(12);
				switchToFrame("default");
				waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
				while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
				{
					driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
					waitForSync(6);
				}

			}

			catch(Exception e)
			{

			}

			finally
			{
				switchToFrame("contentFrame", "OPR026");
			}
		}
		/**
		 * @author A-9844
		 * @param SCCValues
		 * @param Pieces
		 * @param Weights
		 * @throws Exception
		 * Desc : split shipment by entering the pcs / wt
		 */
		public void splitShipmentDetails(String[] SCCValues,String Pieces[],String Weights[],int count,String[] uldNo) throws Exception
		{
			switchToWindow("storeParent");
			clickWebElement(sheetName, "btn_splitShipment;id","Split Shipment", screenName);
			waitForSync(3);
			switchToWindow("child");
			
			enterValueInTextbox(sheetName, "inbx_splitCount;id",Integer.toString(count), "Split into",screenName);
			clickWebElement(sheetName, "btn_Split;id","Split", screenName);
			waitForSync(2);

			try
			{
				for(int i=1;i<=count;i++)
				{
					
					//enter pieces
					String pcsLoc = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
					pcsLoc = "("+pcsLoc+")"+"["+i+"]";
					driver.findElement(By.xpath(pcsLoc)).click();
					driver.findElement(By.xpath(pcsLoc)).clear();
					driver.findElement(By.xpath(pcsLoc)).sendKeys(Pieces[i-1]);
					writeExtent("Pass","Successfully entered Weight as" +Pieces[i-1]+" on " +screenName);
					
					
					//enter weight
					String wgtLoc = xls_Read.getCellValue(sheetName, "inbx_splitWeights;xpath");
					wgtLoc = "("+wgtLoc+")"+"["+i+"]";
					driver.findElement(By.xpath(wgtLoc)).click();
					driver.findElement(By.xpath(wgtLoc)).clear();
					driver.findElement(By.xpath(wgtLoc)).sendKeys(Weights[i-1]);
					writeExtent("Pass","Successfully entered Weight as" +Weights[i-1]+" on " +screenName);
					
					
					//enter ULD Number
					String uldNum = xls_Read.getCellValue(sheetName, "inbx_splitULD;xpath");
					uldNum = "("+uldNum+")"+"["+i+"]";
					driver.findElement(By.xpath(uldNum)).click();
					driver.findElement(By.xpath(uldNum)).clear();
					driver.findElement(By.xpath(uldNum)).sendKeys(uldNo[i-1]);
					writeExtent("Pass","Successfully entered ULD Number as" +uldNo[i-1]+" on " +screenName);
					
					
					//selct SCC
					String sccLoc = xls_Read.getCellValue(sheetName, "btn_selectSCC;id");
					sccLoc = sccLoc.replaceAll("Index", Integer.toString(i-1));
					driver.findElement(By.id(sccLoc)).click();
					String sccCheckAll = xls_Read.getCellValue(sheetName, "btn_checkAllSCC;xpath");
					
					int index=i+Integer.parseInt("1");
					sccCheckAll="("+sccCheckAll+")"+"["+index+"]";
					driver.findElement(By.xpath(sccCheckAll)).click();
					waitForSync(3);
					keyPress("TAB");
					
					
					
				}
			}
			catch(Exception e)
			{
				writeExtent("Fail","Couldn't enter details in "+screenName);
			}


		

			clickWebElement(sheetName, "btn_splitShipmentOk;id","Ok", screenName);
			waitForSync(3);
			switchToWindow("getParent");
			switchToFrame("contentFrame", "OPR026");
		}
	/**
	 * @author A-7271
	 * Description : enterFrenchCustomsDetails
	 * @throws IOException 
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
  public void enterFrenchCustomsDetails() throws InterruptedException, AWTException, IOException
  {
	
      
      
	  clickaddtionalInfo();
	  String locatorOCIChkBox = xls_Read.getCellValue(sheetName, "inbx_ociLine;xpath");
	  String locatorCIChkBox = xls_Read.getCellValue(sheetName, "lst_ciLine;xpath");
	  List<WebElement> ocichkBox=driver.findElements(By.xpath(locatorOCIChkBox));
	  List<WebElement> cichkBox=driver.findElements(By.xpath(locatorCIChkBox));

	  
	 
	  
	  if(ocichkBox.size()!=1 && cichkBox.size()!=1)
	  {
		  clickWebElement(sheetName, "btn_customsInfoAdd;xpath","Customs Info Add Button", screenName);
		  selectValueInDropdown(sheetName, "lst_customsAuthority;xpath","French Customs", "Customs Authority", "VisibleText");
		  waitForSync(1);
		  keyPress("TAB");
		  waitForSync(2);
		  selectValueInDropdown(sheetName, "lst_customsParameter;xpath","ECS", "Customs Parameter", "VisibleText");
		  waitForSync(1);
		  keyPress("TAB");
		  waitForSync(1);
		  enterValueInTextbox(sheetName, "inbx_customsInfoValue;xpath", "21FRD3030006866654", "Customs Info Value", screenName);		
		  waitForSync(1);
	  }
  }
  
  /**
   * @author A-7271
   * @throws Exception
   * Desc : remove customs block
   */
  public void removeCustomsBlock() throws Exception
  {
	  String awb=data("AWBWithBlock");
	  map.put("awbPrefixWithCustomsBlock", awb.split("-")[0]);
	  map.put("awbNumberWithCustomsBlock", awb.split("-")[1]);
	  
	  
	/****  String station=getLoggedInStation("OPR026");
	  boolean isDGR=verifyDGR(awb);****/

	  switchToFrame("default");
	  searchScreen("OPR023","AWB Clearance");
      OPR023.listAWB("awbPrefixWithCustomsBlock","awbNumberWithCustomsBlock");
      waitTillScreenload("AWBClearance_OPR023", "btn_blockRelease;xpath","Release Button", "AWB Clearance");
      waitForSync(2);
      

     /***** String [] blocks ={"Special Shipment","Customs"};
      if(station.equals("AMS") && isDGR)
    	  OPR023.selectCheckboxandReleaseBlocks(blocks,"val~Block released");       
      else
          OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Customs Block removed");      ****/ 
      

      OPR023.selectCheckboxandReleaseBlock("val~Compliance","val~Customs Block removed");       
      OPR023.closeTab("OPR023", "AWB Clearance");   

	  
	  
	  
  }
	/**
	 * Description... To perform as is execute
	 * @throws Exception
	 */
	public void asIsExecute() throws Exception {
		screenName="Capture AWB";
		screenName="Capture AWB";
		String testEnv=getPropertyValue(globalVarPath, "testEnv");

		/********** REMOVE CUSTOMS BLOCK*****/
		/****if(testEnv.equals("RC4"))
		{
			removeCustomsBlock();
		}****/
		
		
		
		
		/*********************************/
		/********** CAPTURE SPX CHECKSHEET *****/
		captureSPXChecksheet();
		/*********************************/

		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		switchToWindow("storeParent");
		clickGeneralTab();
		// Click override certificate
		/**String locator = xls_Read.getCellValue(sheetName, "chk_overrideCertifications;id");
		if(data("Origin").equals("IAD"))
		{
		if(!driver.findElement(By.id(locator)).isSelected())
		{
		clickWebElement(sheetName, "chk_overrideCertifications;id", "Override checkbox",screenName);
		}
		}**/
		enterExecutionDate();
		/****** ENTER THE FRENCH CUSTOMS DETAILS****/

		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
		  	
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		else if(station.equals("AMS")) 
		{
 
				captureCDGCompChecksheet();
 
		}
		/**********************************/
		
		/**********************************/
		enterHSCode();
		/**********************************/

		clickWebElement(sheetName, "btn_AsIsExecute;xpath",
				"AsIsExecute Button", screenName);
		waitForSync(10);
		switchToFrame("default");
		/*****waitForSync(10); ***/  
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		try {
			handleDG();
			while (driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
					.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
				/***Thread.sleep(20800);***/
				Thread.sleep(12000);
			}
		} catch (Exception e) {
		}

		/***Thread.sleep(12000);****/
		switchToFrame("contentFrame", "OPR026");
		/***Thread.sleep(12000);***/
		Thread.sleep(2000);
		
		waitTillScreenload(sheetName, "txt_executed;xpath","Executed text", screenName);
		String actText = driver
				.findElement(
						By.xpath(xls_Read.getCellValue(sheetName,
								"txt_executed;xpath"))).getText();
		/***waitForSync(5);***/
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute",
				"Capture AWB");
		/****Thread.sleep(2000);****/
		

		 /****REMOVE CUSTOMS BLOCK*****/
		if(testEnv.equals("RC4"))
		{
			removeCustomsBlock();
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
		}

	}
	
	
	/**
	 * @author A-9478
	 * Description... Check other charges and store in map 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public HashMap<String,String> checkAndStoreOtherChargesValue() throws InterruptedException, AWTException, IOException 
	{
		String locator1 = xls_Read.getCellValue(sheetName, "txt_chargeHeadCode;xpath");
		String locator2 = xls_Read.getCellValue(sheetName, "txt_charges;xpath");
		List<WebElement> chargeCode = driver.findElements(By.xpath(locator1));
		List<WebElement> charge = driver.findElements(By.xpath(locator2));
		int rowCount = chargeCode.size();
		Map<String,String> hm = new HashMap<String,String>();
		for(int i=0;i<rowCount;i++)
		{
			if(chargeCode.get(i).getAttribute("value").equals(""))
			{
				if(i==0)
				{
					writeExtent("Fail", "Other charge code is not getting displayed on "+screenName);
				}
				break;
			}
			else
			{
                hm.put(chargeCode.get(i).getAttribute("value"), charge.get(i).getAttribute("value").replace(",", ""));
				writeExtent("Pass", "Other charge code is "+chargeCode.get(i).getAttribute("value")+"and charge value is"+charge.get(i).getAttribute("value")+" on "+screenName);
			}

		}
		return (HashMap<String, String>) hm;
	}

	
	/**
	 * Description... To click on compute total button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickNotify() throws InterruptedException, AWTException, IOException 
	{
		try
		{
			String locator1 = xls_Read.getCellValue(sheetName, "btn_clickMore;xpath");
			String locator2 = xls_Read.getCellValue(sheetName, "btn_clickNotify;xpath");
			WebElement ele1 = driver.findElement(By.xpath(locator1));
			Actions a = new Actions(driver);
			a.moveToElement(ele1).perform();
			waitForSync(2);
			driver.findElement(By.xpath(locator2)).click();
			waitForSync(5);
			writeExtent("Pass", "Successfully clicked on Notify under more options in "+screenName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writeExtent("Fail", "Couldn't click on Notify button in "+screenName);
		}
	}


	/**
	 * Description... To click on YES button in pop up
	 */
	public void clickIfPopsUp() {

		try {
			Thread.sleep(2000);
			WebElement ele = driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"));
			ele.click();

		} catch (Exception e) {
		}
	}
	
	
	/**
	 * Description... To verify Gross volume and Stated volume
	 * @throws Exception
	 */
	public void verifyShipmentVolume() throws Exception {
		waitForSync(5);
		// Gross Volume
		By element = getElement(sheetName, "inbx_grossVol;xpath");
		String grossVolume = driver.findElement(element).getAttribute("value");
		System.out.println("Gross vol is--" + grossVolume);

		// stated volume
		By element2 = getElement(sheetName, "inbx_statedVol;xpath");
		String statedVolume = driver.findElement(element2).getAttribute("value");
		System.out.println("stated vol is--" + statedVolume);

		// gross weight
		By element3 = getElement(sheetName, "inbx_grossWeight;xpath");
		String grossWeight = driver.findElement(element3).getAttribute("value");
		System.out.println("gross Weight is--" + grossWeight);
		closeTab("OPR026", "CaptureAWB");

		waitForSync(4);
		searchScreen("SHR015", "Maintain and List Commodity");
		// list MISCELLANOUS commodity
		shr015.listCommodity("commodity");

		// Get its density factor
		String densityFactor = shr015.getDensityFactor();

		// Gross and Stated volume should be = Gross weight / density factor of
		// the commodity
		double result = Double.parseDouble(grossWeight) / Double.parseDouble(densityFactor);
		double roundOff = Math.round(result * 100.0) / 100.0;

		String expVol = String.valueOf(roundOff);
		System.out.println("expVol" + expVol);

		verifyValueOnPage(grossVolume, expVol, "Gross volume verification", sheetName, "Gross volume verification");
		verifyValueOnPage(statedVolume, expVol, "Stated volume verification", sheetName, "Stated volume verification");
	}
	
	
	/**
	 * Description... To verify default unit change, verification of stated weight and gross weight
	 * @throws Exception
	 */
	public void verifyDefaultUnitChange() throws Exception {
		waitForSync(5);

		String cbfWeight = data("Weight");
		String converionValueWt = "0.453592";
		double cubicMetreWt = Double.parseDouble(cbfWeight) * Double.parseDouble(converionValueWt);
		cubicMetreWt = Math.round(cubicMetreWt * 100.0) / 100.0;
		String cubicMetreWtExp = String.valueOf(cubicMetreWt);

		// gross weight
		By element3 = getElement(sheetName, "inbx_grossWeight;xpath");
		String grossWeight = driver.findElement(element3).getAttribute("value");
		System.out.println("gross Weight is--" + grossWeight);

		// Stated weight
		By element4 = getElement(sheetName, "inbx_stated_weight;name");
		String statedWeight = driver.findElement(element4).getAttribute("value");
		System.out.println("Stated Weight is--" + statedWeight);

		verifyValueOnPage(grossWeight, cubicMetreWtExp, "Gross Weight default unit verification", sheetName,
				"Gross Weight default unit verification");
		verifyValueOnPage(statedWeight, cubicMetreWtExp, "Stated Weight default unit verification", sheetName,
				"Stated Weight default unit verification");

	}
	
	/**
	 * @author A-8783
	 * Desc-Clcik on check shipment status button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCheckStatus() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
	}

	/**
	 * Description... To verify shipment volume with dimensions, verification of gross volume stated volume and rating volume
	 * @throws Exception
	 */
	public void verifyShipmentVolumewithDimension() throws Exception {
		waitForSync(5);
		switchToWindow("storeParent");
		waitForSync(2);

		clickWebElement(sheetName, "img_dimensionLOV;xpath", "Dimension LOV", screenName);
		waitForSync(12);
		switchToWindow("child");
		waitForSync(2);
		// Dimension Gross vol
		By element = getElement(sheetName, "inbx_volumeLOV;xpath");
		String dimVolume = driver.findElement(element).getAttribute("value");
		clickWebElement(sheetName, "btn_CloseLOV;name", "Dimension LOV", screenName);
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");

		// Gross Vol
		By element2 = getElement(sheetName, "inbx_grossVol;xpath");
		String grossVolume = driver.findElement(element2).getAttribute("value");
		System.out.println("Gross vol is--" + grossVolume);

		// Stated Vol
		By element3 = getElement(sheetName, "inbx_statedVol;xpath");
		String statedVolume = driver.findElement(element3).getAttribute("value");

		// Verify gross, stated vol = gross volume in dimension LOV
		clickChargesAcc();
		By element4 = getElement(sheetName, "inbx_ratingVolume;xpath");
		String ratingVolume = driver.findElement(element4).getAttribute("value");

		verifyValueOnPage(ratingVolume, dimVolume, "Rating details volume verification", sheetName,
				"Rating details volume verification");
		verifyValueOnPage(grossVolume, dimVolume, "Gross volume verification", sheetName, "Gross volume verification");
		verifyValueOnPage(statedVolume, dimVolume, "Stated volume verification", sheetName,
				"Stated volume verification");

	}
	
	
	/**
	 * Description... To verify values in ULD LOV
	 * @throws Exception
	 */
	public void verifyULDLov() throws Exception {
		waitForSync(5);
		switchToWindow("storeParent");
		waitForSync(2);

		clickWebElement(sheetName, "img_uldLOV;xpath", "ULD LOV", screenName);
		waitForSync(8);
		switchToWindow("child");
		waitForSync(2);

		// Dimension Gross vol
		By element = getElement(sheetName, "inbx_uldNum1;xpath");
		String uldNum1 = driver.findElement(element).getAttribute("value");

		By element2 = getElement(sheetName, "inbx_uldNum2;xpath");
		String uldNum2 = driver.findElement(element2).getAttribute("value");

		By element3 = getElement(sheetName, "inbx_uldSlacPieces1;xpath");
		String uldSlacPieces1 = driver.findElement(element3).getAttribute("value");

		By element4 = getElement(sheetName, "inbx_uldSlacPieces2;xpath");
		String uldSlacPieces2 = driver.findElement(element4).getAttribute("value");

		clickWebElement(sheetName, "btn_CloseLOV;name", "Dimension LOV", screenName);
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR026");

		verifyValueOnPage(uldNum1, data("ULD1"), "1st ULD name verification", sheetName, "1st ULD name verification");
		verifyValueOnPage(uldNum2, data("ULD2"), "2nd ULD name verification", sheetName, "2nd ULD name verification");

		verifyValueOnPage(uldSlacPieces1, data("ULDPieces1"), "ULD1 pieces verification", sheetName,
				"ULD1 pieces verification");
		verifyValueOnPage(uldSlacPieces2, data("ULDPieces2"), "ULD2 pieces verification", sheetName,
				"ULD2 pieces verification");

	}
	
	
	/**
	 * Description... To verify shipper code
	 * @param shipperCode : test data column name for shipper code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyShipperCode(String shipperCode) throws InterruptedException, AWTException {
		By element = getElement(sheetName, "inbx_shipperCode;xpath");

		String actShipperNo = driver.findElement(element).getAttribute("value");
		verifyValueOnPage(actShipperNo, data("shipperCode"), "Shipper Code verification", sheetName,
				"Shipper Code verification");
	}
	
	
	/**
	 * Description... To verify Consignee code
	 * @param consigneeCode : test data column name for consignee code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyConsigneeCode(String consigneeCode) throws InterruptedException, AWTException {

		By element = getElement(sheetName, "inbx_consigneeCode;xpath");
		String actConsigneeNo = driver.findElement(element).getAttribute("value");

		verifyValueOnPage(actConsigneeNo, data("consigneeCode"), "Consignee Code verification", sheetName,
				"Consignee Code verification");
	}
	
	
	/**
	 * Description... To verify Rate and Commodity details
	 * @param rateClass : test data column name for rate class selected
	 * @param CmdtyItemNo : test data column name for commodity item number
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyRateCommodityDetails(String rateClass, String CmdtyItemNo)
			throws InterruptedException, AWTException {

		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_RateClass;xpath")));
		String actText = ele.getAttribute("value");

		String expText = data(rateClass);
		verifyScreenText(sheetName, expText, actText, "Rate Class verification", "Capture AWB");
		Thread.sleep(2000);

		WebElement ele2 = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_comdtyItemNo;xpath")));
		String actText2 = ele2.getAttribute("value");

		String expText2 = data(CmdtyItemNo);
		verifyScreenText(sheetName, expText2, actText2, "Commodity Item No verification", "Capture AWB");
		Thread.sleep(2000);

	}
	 /**
		 * @author A-9847
		 * @Desc To verify no MRN details are captured on OPR026
		 * @throws InterruptedException
		 * @throws AWTException
		 * @throws IOException
		 */
		public void verifyNoMRNCaptured() throws InterruptedException, AWTException, IOException
		 {
			  clickaddtionalInfo();
			  String locatorOCIChkBox = xls_Read.getCellValue(sheetName, "inbx_ociLine;xpath");
			  String locatorCIChkBox = xls_Read.getCellValue(sheetName, "lst_ciLine;xpath");
			  List<WebElement> ocichkBox=driver.findElements(By.xpath(locatorOCIChkBox));
			  List<WebElement> cichkBox=driver.findElements(By.xpath(locatorCIChkBox));
			  
			  if(ocichkBox.size()!=1 && cichkBox.size()!=1)
				  writeExtent("Pass", "Successfully verified No MRN details are captured on "+screenName); 
			  else
				  writeExtent("Fail", "MRN details are already captured on "+screenName); 
		  }
			
	
	/**
	 * Description... Verification of Rate class and Rate Class Type in ratings table
	 * @param rateClass : test data column name for expected value of rate class
	 * @param RateClassType : test data column name for expected value of ULD rate class type
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyRateRateClassTypeDetails(String rateClass, String RateClassType)
			throws InterruptedException, AWTException {

		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_RateClass;xpath")));
		String actText = ele.getAttribute("value");

		String expText = data(rateClass);
		verifyScreenText(sheetName, expText, actText, "Rate Class verification", "Capture AWB");
		Thread.sleep(2000);

		WebElement ele2 = driver.findElement(By.name(xls_Read.getCellValue(sheetName, "inbx_ULDRateClassType;name")));
		String actText2 = ele2.getAttribute("value");

		String expText2 = data(RateClassType);
		verifyScreenText(sheetName, expText2, actText2, "Rate Class Type verification", "Capture AWB");
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... To verify Rate class, rate class code basis and rate class percentage
	 * @param rateClass : test data column name for expected value of rate class
	 * @param RateClassCodeBasis : test data column name for expected value of class code basis
	 * @param RateClassPercent : test data column name for expected value of rate class percent
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyRateRateClassCodeBasis(String rateClass, String RateClassCodeBasis, String RateClassPercent)
			throws InterruptedException, AWTException {

		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_RateClass;xpath")));
		String actText = ele.getAttribute("value");

		String expText = data(rateClass);
		verifyScreenText(sheetName, expText, actText, "Rate Class verification", "Capture AWB");
		Thread.sleep(2000);

		WebElement ele2 = driver.findElement(By.name(xls_Read.getCellValue(sheetName, "lst_ULDRateCodeBasis;name")));
		String actText2 = ele2.getAttribute("value");

		String expText2 = data(RateClassCodeBasis);
		verifyScreenText(sheetName, expText2, actText2, "Rate Class Code Basis verification", "Capture AWB");
		Thread.sleep(2000);

		WebElement ele3 = driver
				.findElement(By.name(xls_Read.getCellValue(sheetName, "inbx_ClassratePercentage;name")));
		String actText3 = ele3.getAttribute("value");

		String expText3 = data(RateClassPercent);
		verifyScreenText(sheetName, expText3, actText3, "Rate Class Percent verification", "Capture AWB");
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... To verify IATA Rate Charges with 2 rates B and K added
	 * @param rateClass : 
	 * @param IATARate
	 * @param IATACharge
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyIATARateChargesAdded(String rateClass, String IATARate, String IATACharge)
			throws InterruptedException, AWTException {

		// Rate Class verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_RateClass;xpath")));
		String actText = ele.getAttribute("value");

		String expText = data(rateClass);
		verifyScreenText(sheetName, expText, actText, "Rate Class verification", "Capture AWB");
		Thread.sleep(2000);

		// IATA RATE verification
		WebElement ele2 = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_IATARate;xpath")));
		String actText2 = ele2.getAttribute("value");

		double iataRate = Double.parseDouble(data(IATARate)) + Double.parseDouble(data(IATACharge));
		String expText2 = String.valueOf(iataRate);
		System.out.println("IATA rate is---" + expText2);

		verifyScreenText(sheetName, expText2, actText2, "Total IATA Rate verification", "Capture AWB");

		// IATA Charge verification
		WebElement ele3 = driver.findElement(By.name(xls_Read.getCellValue(sheetName, "inbx_iataCharge;name")));
		String actText3 = ele3.getAttribute("value");
		actText3 = actText3.replace(",", "");

		double iataChrg = Double.parseDouble(data(IATACharge)) + Double.parseDouble(data(IATACharge));
		int iataChrg2 = (int) iataChrg;
		String expText3 = String.valueOf(iataChrg2);
		System.out.println("IATA charge is---" + expText3);

		verifyScreenText(sheetName, expText3, actText3, "Total IATA Charge verification", "Capture AWB");
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description: To update commodity code
	 * @param cmdtyCode : test data column name for commodity code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void updateCmdtyCode(String cmdtyCode) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_cmdtyCode;name", data(cmdtyCode), "Commodity Code in shipment details",
				screenName);
		performKeyActions(sheetName, "inbx_cmdtyCode;name", "TAB", "Commodity Code in shipment details", screenName);
		waitForSync(2);
	}
	
	
	/**
	 * Description... To update shipment destination
	 * @param destination : test data column name for destination
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void updateDestination(String destination) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_Destination;xpath", data(destination), "Destination in shipment details",
				screenName);
		performKeyActions(sheetName, "inbx_Destination;xpath", "TAB", "Destination in shipment details", screenName);
		waitForSync(2);
	}
	
	
	/**
	 * Description... To update shipment origin
	 * @param origin : test data column name for origin
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	public void updateOrigin(String origin) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_Origin;xpath", data(origin), "Origin in shipment details", screenName);
		performKeyActions(sheetName, "inbx_Origin;xpath", "TAB", "Origin in shipment details", screenName);
		waitForSync(2);
	}
	
	/**
	 * @author A-8783
	 * @desc To verify booking details
	 * @param rows
	 * @param origin
	 * @param destination
	 * @param flightNo
	 * @param flightDate
	 * @param pcs
	 * @param wt
	 * @param vol
	 * @throws InterruptedException
	 */
	
	public void verifyBookingDetails(int rows, String origin[], String destination[], String flightNo[],
			String flightDate[], String pcs[], String wt[], String vol[]) throws InterruptedException {

		for (int i = 1; i <= rows; i++) {

			String org = getAttributeWebElement(sheetName, "inbx_fltOrigin" + i + ";xpath", "Origin", "value", screenName);
			String dest= getAttributeWebElement(sheetName, "inbx_fltDestination" + i + ";xpath", "Destination", "value", screenName);
			String fltNum = getAttributeWebElement(sheetName, "inbx_fltNumber" + i + ";xpath", "Flight Number", "value", screenName);
			String fltDate = getAttributeWebElement(sheetName, "inbx_fltDate" + i + ";xpath", "Flight Date", "value", screenName);
			String fltPcs = getAttributeWebElement(sheetName, "inbx_fltPcs" + i + ";xpath", "Total Pieces", "value", screenName);
			String fltWt = getAttributeWebElement(sheetName, "inbx_fltWt" + i + ";xpath", "Total Weight", "value", screenName);
			String fltVol= getAttributeWebElement(sheetName, "inbx_fltVolume" + i + ";xpath", "Total Weight", "value", screenName);

			verifyScreenText(screenName ,data(origin[i-1]), org, "Flight Origin","Flight Origin");
			verifyScreenText(screenName ,data(destination[i-1]), dest, "Flight Destination","Flight Destination");
			verifyScreenText(screenName ,data(flightNo[i-1]), fltNum, "Flight Number","Flight Number");
			verifyScreenText(screenName ,data(flightDate[i-1]), fltDate, "Flight Date","Flight Date");
			verifyScreenText(screenName ,data(pcs[i-1]), fltPcs, "Total Pieces","Total Pieces");
			verifyScreenText(screenName ,data(wt[i-1]), fltWt, "Total Weight","Total Weight");
			verifyScreenText(screenName ,data(vol[i-1]), fltVol, "Total Volume","Total Volume");

		}
	}

	/**
	 * Description... To verify error message (exact match)
	 * @param expText : Expected error message
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyerrorMsg(String expText) throws InterruptedException, AWTException {

		By element = getElement(sheetName, "div_error;xpath");
		String errorText = driver.findElement(element).getText();

		verifyValueOnPage(errorText, data(expText), "Auto Rate error verification", sheetName,
				"Auto Rate error verification");
	}
	
	
	/**
	 * Description... To verify error message (contains)
	 * @param expText : Expected error message
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyerrorMsgContains(String expText) throws InterruptedException, AWTException {

		By element = getElement(sheetName, "div_error;xpath");
		String errorText = driver.findElement(element).getText();
		verifyScreenText(sheetName, data(expText), errorText, "Auto Rate error verification", "Capture AWB");

	}
	
	
	/**
	 * Description... Verify shipper name
	 * author-A-8222
	 */
	public void verifyShipperName(String shiperName)
	{
		By element = getElement(sheetName, "inbx_shiperName;id");
		String actText = driver.findElement(element).getAttribute("value").toLowerCase();
		String expText = shiperName.toLowerCase();
		verifyScreenText(sheetName, expText, actText, "Verify Shipper Name ", "Capture AWB");
	}
	
	
	/**
	 * Description... To verify consignee name
	 * @param cnsgName : Expected consignee name
	 */	
	public void verifyConsigneeName(String cnsgName)
	{
		By element = getElement(sheetName, "inbx_consigneeName;id");
		String actText = driver.findElement(element).getAttribute("value").toLowerCase();
		String expText = cnsgName.toLowerCase();
		verifyScreenText(sheetName, expText, actText, "Verify Consignee Name ", "Capture AWB");
	}
	
	/**
	 * Description: To enter the SCC in the SCC field
	 * @author A-9844
	 * @param scc
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifySCCFieldAndEnterSCC(String SCC) throws InterruptedException, AWTException {
		try{
			
		
		String sccValues = getAttributeWebElement(sheetName , "txt_sccText;xpath" , "SCC" , "value", screenName );
		System.out.println(sccValues);
		
		if(!sccValues.equals("")){
			String scc=sccValues+","+data("SCC");
			enterSCC(scc);
		}
		}
		catch (Exception e) {
			
			writeExtent("Fail","Could not fetch the SCC values from the SCC Field on "+screenName);
		}
		
	}
	/**
	 * @author A-10690
	 * Desc - Verify LAT updated in shipment status after booking is updated from afls
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verfyLATValueAfterBookingUpdate(String date) throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
		waitForSync(3);
		driver.switchTo().frame("popupContainerFrame");
		int size=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).size();

		if(size==1){
			String text=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).getText();

			if(!(text.equals(data(date))))
			{

				writeExtent("Pass", "LAT date got updated as expected  "+screenName);
			}
			else
				writeExtent("Fail", "LAT date not getting updated "+screenName);
		}
		else{
			writeExtent("Fail", "LAT Details are not present in "+screenName);
		}
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR026");
		handleShipmentStatusPopUp();

	}



/**
	 * @author A-10690
	 * Desc - checking the LAT date and time and storing the values in a map
	 * @param LATData
	 * @param LATDate
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void LATUpdatedInShipmentStatus(String LATData,String LATDate ) throws InterruptedException, IOException{


		String locator=xls_Read.getCellValue(sheetName, "btn_chkShipmentStatus;xpath");
		boolean checksPopupinside=false;
		try 
		{
			if(driver.findElement(By.xpath(locator)).isDisplayed())
			{
				checksPopupinside=true;
			}
		} 
		catch (Exception e) 
		{
			if(!checksPopupinside)
				clickWebElement(sheetName, "btn_checkStatus;name", "check button", screenName);
			waitForSync(3);
		}
		driver.switchTo().frame("popupContainerFrame");
		int size=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).size();
		if(size==1){
			String text=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetails;xpath"))).getText();
			String latdate=text.split(" ")[1];
			map.put(LATDate, latdate);
			map.put(LATData, text);
			System.out.println(data(LATData));
		}
		else{
			writeExtent("Fail", "LAT Details are not present in "+screenName);
		}
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR026");
		handleShipmentStatusPopUp();



	}

/**
	 * @author A-10690
	 * Desc - Verify expected LAT  date is stamped in OPR026 screen
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verfyLATvalue(String date) throws InterruptedException, IOException{

		waitForSync(3);

		String text=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_LatDetailsupdated;xpath"))).getText();
		System.out.println(data(date));
		if(text.contains(data(date)))
		{

			writeExtent("Pass", "LAT date got updated as expected  "+screenName);
		}
		else
			writeExtent("Fail", "LAT date not getting updated "+screenName);


	}

	/**
	 * @author A-9847
	 * @Desc To select the given message type and click Ok
	 * @param messageType
	 * @throws Exception
	 */
	public void selectSendFwbDetails(String messageType) throws Exception
	{
		switchToWindow("child");
		selectValueInDropdown(sheetName, "drpdn_messageType;xpath",messageType, "Message Type", "VisibleText");
		clickWebElement(sheetName, "btn_OkSendFWB;id","Ok Button", screenName);
		switchToWindow("getParent");

	}

	/**
	 * @author A-7271
	 * @param chkSheetRequired
	 * Description : Capture check sheet
	 */
	public void captureCheckSheet(boolean chkSheetRequired)
	{
		boolean checkSheetExists=true;
		try
		{

			clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",
					screenName);

			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);

			waitTillScreenload("Generic_Elements", "btn_save;xpath", "OK Button", screenName);
			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			if(questions.size()==0)
			{
				checkSheetExists=false;
			}

			for(WebElement ele : questions)
			{
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
				
				
				
			}
			if(chkSheetRequired)
			{
				if(checkSheetExists)
				{
					writeExtent("Pass","Check sheet details selected on "+screenName);
				}

				else
				{
					writeExtent("Info","No check sheet details configured on "+screenName); // To be changed to fail later
				}
			}

			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);

			waitForSync(2);
			switchToFrame("contentFrame", "OPR026");
			driver.switchTo().frame("popupContainerFrame");
			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
			waitForSync(1);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");

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
	 * @author A-10328
	 * Desc - Verify eCSD icon
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyeCSDicon() throws InterruptedException, IOException {

		String locator = xls_Read.getCellValue(sheetName, "htmlDiv_toggleicon;xpath");
		WebElement elem=driver.findElement(By.xpath(locator));
		if(elem.isDisplayed())
		{
			clickWebElement(sheetName, "htmlDiv_toggleicon;xpath","toggleLink icon", screenName);
		}
		verifyElementDisplayed(sheetName, "btn_eCSDicon;xpath", "Verify eCSD icon", screenName, "eCSD icon");
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ESCAPE).build().perform();
		

	}

	/**
	 * Description... To verify pieces and weight
	 * @param pcs : test data column name for expected pieces
	 * @param weight : test data column name for expected weight
	 * @throws InterruptedException
	 */	
	public void verifyPiecesWeight(String pcs, String weight) throws InterruptedException
	{
		By element = getElement(sheetName, "inbx_stated_Pieces;xpath");
		String actText = driver.findElement(element).getAttribute("value");
		String expText = data(pcs);
		verifyScreenText(sheetName, expText, actText, "Verify Pieces ", "Capture AWB");
		Thread.sleep(1000);
		By element1 = getElement(sheetName, "inbx_stated_weight;name");
		String actText1 = driver.findElement(element1).getAttribute("value");
		String expText1 = data(weight);
		verifyScreenText(sheetName, expText1, actText1, "Verify Weight ", "Capture AWB");

	}
	
	
	/**
	 * Description... To verify volume and commodity code
	 * @param volume : test data column name for expected volume
	 * @param cmdtCode : test data column name for expected commodity code
	 * @throws InterruptedException
	 */	
	public void verifyVolumeCmdtycode(String volume, String cmdtCode) throws InterruptedException
	{
		By element = getElement(sheetName, "inbx_stated_Volume;xpath");
		String actText = driver.findElement(element).getAttribute("value");
		String expText = data(volume);
		verifyScreenText(sheetName, expText, actText, "Verify Volume ", "Capture AWB");
		Thread.sleep(1000);
		By element1 = getElement(sheetName, "inbx_cmdtyCode;name");
		String actText1 = driver.findElement(element1).getAttribute("value").toLowerCase();
		String expText1 = data(cmdtCode).toLowerCase();
		verifyScreenText(sheetName, expText1, actText1, "Verify Commodity Code ", "Capture AWB");

	}	
	
	
	/**
	 * Description... To verify IATA rate and class
	 * @param rateClass : Expected rate class
	 * @param IATARate : Expected IATA rate
	 * @throws InterruptedException
	 * @throws AWTException
	 */	

	public void verifyIATARateAndClass(String rateClass, String IATARate)
			throws InterruptedException, AWTException 
	{
		// Rate Class verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_RateClass;xpath")));
		String actText = ele.getAttribute("value");
		String expText = rateClass;
		verifyScreenText(sheetName, expText, actText, "Rate Class verification ", "Capture AWB");
		Thread.sleep(1000);

		// IATA RATE verification
		WebElement ele2 = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_IATARate;xpath")));
		String actText2 = ele2.getAttribute("value");
		String expText2 = IATARate; 
		verifyScreenText(sheetName, expText2, actText2, "IATA Rate verification ", "Capture AWB");
	}
	
	
	/**
	 * Description... To verify security and screening 
	 * @param scrnMethod : Expected screening method
	 * @param result : Expected result
	 * @param ScrnrName : Expected screener name
	 * @throws Exception
	 */	
	public void verifySecurityAndScreeingScreen(String scrnMethod,String result,String ScrnrName) throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");
		Thread.sleep(1000);

		WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "hd_scrnMethod;xpath")));
		String actscrnMethod = ele.getText();
		verifyScreenText(sheetName, scrnMethod, actscrnMethod,  "Screening Method verification ", "Security And Screeing  Screen");

		ele=null;
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_result;xpath")));
		String actResult = ele.getText();
		verifyScreenText(sheetName,result, actResult,  "Result verification ", "Security And Screeing  Screen");

		ele=null;
		By ele1 =  getElement(sheetName,"inbx_screenerName;id");
		String actScrnrName = driver.findElement(ele1).getAttribute("value");
		verifyScreenText(sheetName,ScrnrName, actScrnrName,  "Screener Name verification ", "Security And Screeing  Screen");

		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_close;xpath")));
		clickWebElement(ele,"Close Button", screenName);
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... To click on additional information tab
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */	
	public void clickaddtionalInfo() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_additionalInfo;xpath", "Additional Information Tab", screenName);
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... TO verify country code and information ID
	 * @param cntryCode : Expected country code
	 * @param InfoID : Expected information ID
	 * @throws Exception
	 */
	public void verifyCountryCodeANDInfoID(String cntryCode,String InfoID) throws Exception 
	{
		By element = getElement(sheetName, "inbx_ISOcode;id");
		String actcntryCode = driver.findElement(element).getAttribute("value");
		verifyScreenText(sheetName, cntryCode,actcntryCode,  "ISO Country Code verification ", "Capture AWB");

		element = getElement(sheetName, "inbx_infoID;id");
		String actInfoID =driver.findElement(element).getAttribute("value");
		verifyScreenText(sheetName,InfoID, actInfoID,  "Information ID verification ", "Capture AWB");
	}
	
	
	/**
	 * Description... To verify custom Information and supplementary custom information
	 * @param custmInfo : Expected custom information
	 * @param SuppCustmInfo : Expected supplementary custom information
	 * @throws Exception
	 */
	public void verifyCustmInfoANDSuppCustmInfo(String custmInfo,String SuppCustmInfo) throws Exception 
	{
		By element = getElement(sheetName, "inbx_custInfoID;id");
		String actcustmInfo =  driver.findElement(element).getAttribute("value");
		verifyScreenText(sheetName, custmInfo,actcustmInfo,  "Custom Information ID verification ", "Capture AWB");

		element = getElement(sheetName, "inbx_suplCustIndo;id");
		String actSuppCustmInfo = driver.findElement(element).getAttribute("value");
		verifyScreenText(sheetName, SuppCustmInfo,actSuppCustmInfo, "Supplementary Custom Information verification ", "Capture AWB");
	}
	
	
	/**
	 * Description... To verify SSR (special service request) and certificate
	 * @param SSR : Expected SSR
	 * @param certificate : Expected certificate
	 * @throws Exception
	 */	
	public void verifySSRANDCertificate(String SSR,String certificate) throws Exception 
	{
		By element = getElement(sheetName, "txt_ssr;id");
		String actSSR = driver.findElement(element).getText();
		verifyScreenText(sheetName,SSR, actSSR, "Special Service verification ", "Capture AWB");

		element = getElement(sheetName, "inbx_shprCertificate;id");
		String actcertificate = driver.findElement(element).getAttribute("value");
		verifyScreenText(sheetName, certificate,actcertificate, "shipper Certification verification ", "Capture AWB");
	}
	
	
	/**
	 * Description... To verify IATA rate and Chargeable weight
	 * @param chrgwght : Expected chargeable weight
	 * @param IATARate : Expected IATA rate
	 * @throws InterruptedException
	 * @throws AWTException
	 */	
	public void verifyIATARateAndChgrWght(String chrgwght, String IATARate)
			throws InterruptedException, AWTException 
	{
		//Chargeable Weight  verification
		By element = getElement(sheetName, "inbx_chrgWght;name");
		String actchrgwght = driver.findElement(element).getAttribute("value");
		verifyScreenText(sheetName,chrgwght, actchrgwght, "Chargeable Weight verification ", "Capture AWB");
		Thread.sleep(1000);
		// IATA RATE verification
		WebElement ele2 = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_IATARate;xpath")));
		String actText2 = ele2.getAttribute("value");
		String expText2 = IATARate; 
		verifyScreenText(sheetName, expText2, actText2, "IATA Rate verification ", "Capture AWB");
	}
	
	
	/**
	 * Description... To enter SCC value
	 * @param SCC : SCC code
	 * @throws InterruptedException
	 */
	public void enterSCC(String SCC) throws InterruptedException {
		waitForSync(5);
		clearText(sheetName, "inbx_capawb_scc;name", "SCC", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_capawb_scc;name", SCC, "SCC", screenName);

	}
	
	
	/**
	 * Description : Updating commodity codes for n number of shipments
	 * @author A-9175
	 * @param cmdtyCode
	 * @param row values starting from 0.
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void updateCmdtyCodes(String cmdtyCode,int row) throws InterruptedException, AWTException {
		String locatorValue = xls_Read.getCellValue(sheetName, "inbx_commdodyCode;xpath");
		locatorValue=locatorValue.replace("INDEX",Integer.toString(row)); 
		driver.findElement(By.xpath(locatorValue)).clear();
		waitForSync(3);
		driver.findElement(By.xpath(locatorValue)).sendKeys(cmdtyCode);
		waitForSync(3);
	}


	/**
	 * Description... To verify notification details
	 * @param notifyCode : test data column name for notify code
	 * @param notifyName : test data column name for notify name
	 * @param ntyAddress : test data column name for notify address
	 * @param ntyCity : test data column name for notify city
	 * @param ntyCountry : test data column name for notify country
	 * @throws Exception
	 */	
	public void verificationNfyDetails(String notifyCode,String notifyName,String ntyAddress,String ntyCity, String ntyCountry) throws Exception {

		waitForSync(4);

		hover(sheetName, "btn_more;xpath");
		clickWebElement(sheetName, "btn_notify;xpath", "Notify Button", screenName);
		waitForSync(2);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");

		// Notify Code verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_ntyCode;xpath")));
		String actText = ele.getAttribute("value");
		String expText = data(notifyCode);
		verifyScreenText(sheetName, expText, actText, "Notify Code", "Notify Details");
		waitForSync(1);

		// Notify Name verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_ntyName;xpath")));
		String actText1 = ele.getAttribute("value");
		String expText1 = data(notifyName);
		verifyScreenText(sheetName, expText1, actText1, "Notify Name", "Notify Details");
		waitForSync(1);

		// Notify Address verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_ntyAdd;xpath")));
		String actText2 = ele.getAttribute("value");
		String expText2 = data(ntyAddress);
		verifyScreenText(sheetName, expText2, actText2, "Address1", "Notify Details");
		waitForSync(1);

		// Notify City verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_ntyCty;xpath")));
		String actText3 = ele.getAttribute("value");
		String expText3 = data(ntyCity);
		verifyScreenText(sheetName, expText3, actText3, "City", "Notify Details");
		waitForSync(1);

		// Notify Country verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_ntyCountry;xpath")));
		String actText4 = ele.getAttribute("value");
		String expText4 = data(ntyCountry);
		verifyScreenText(sheetName, expText4, actText4, "Country", "Notify Details");
		waitForSync(1);
		clickWebElement(sheetName, "btn_notifyClose;xpath", "Notify Close", screenName);
		waitForSync(2);
		switchToWindow("getParent");

	}
	
	/**
	 * @author A-8783
	 * Desc - capture checksheet for live animals
	 * @param chkSheetRequired
	 * @param date
	 * @param time
	 */
	public void captureCheckSheetLiveAnimals(boolean chkSheetRequired, String date, String time )
	{
		if(getLoggedInStation("OPR026").equals("AMS"))
		{

		boolean checkSheetExists=true;
		try
		{

			clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",
					screenName);

			driver.switchTo().frame("popupContainerFrame");
			waitForSync(3);


			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			if(questions.size()==0)
			{
				checkSheetExists=false;
			}

			for(WebElement ele : questions)
			{
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
			}
			
			enterValueInTextbox(sheetName, "inbx_answerDate;name", date, "Date when animal was fed", screenName);
			enterValueInTextbox(sheetName, "inbx_answerDateTime;name", time, "Time when animal was fed", screenName);
			
			if(chkSheetRequired)
			{
				if(checkSheetExists)
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
			switchToFrame("contentFrame", "OPR026");
			driver.switchTo().frame("popupContainerFrame");
			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
			waitForSync(1);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");

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
	}
	/**
	 * @author A-9847
	 * @Desc To verify the given warning message during As is Execute
	 * @param warningmsg
	 * @throws Exception
	 */
	
	public void clickAsIsExecuteAndVerifyWarning(String warningmsg) throws Exception{

		boolean warningMsgDisplayed=false;
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		clickGeneralTab();
		enterExecutionDate();

		waitForSync(3);
		/************ FRENCH CUSTOMS****/
		String station=getLoggedInStation("OPR026");  
		
		
		if(station.equals("CDG")) 
		{
		  	
			enterFrenchCustomsDetails();
			captureCDGCompChecksheet();
		}
		/**********************************/
		

		/**********************************/
		enterHSCode();
		/**********************************/


		clickWebElement(sheetName, "btn_AsIsExecute;xpath","AsIsExecute Button", screenName);
		waitForSync(6);
		switchToFrame("default");
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		

		try {
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				String actText=getElementText("Generic_Elements", "htmlDiv_msgStatus;xpath","warning msg", screenName);
				System.out.println(actText);
				if(actText.contains(warningmsg))
				{
					warningMsgDisplayed=true;
					break;
				}

				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
				waitForSync(8);
			}
		}

		catch(Exception e)
		{

		}
		if(warningMsgDisplayed)
			writeExtent("Pass","Message "+warningmsg+ " displayed on "+screenName);
		else
			writeExtent("Fail","Message "+warningmsg+ " not displayed on"+screenName);
		switchToFrame("contentFrame", "OPR026");
	}


	/**
	 * Description... To verify issue details
	 * @param executedAt : expected executed at station e.g., FRA
	 * @param executedOn : expected execution date
	 * @throws Exception
	 */
	public void verifyISUDetails(String executedAt,String executedOn) throws Exception {
		waitForSync(3);

		// Notify Code verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_executedAt;xpath")));
		String actText = ele.getAttribute("value");
		String expText = data(executedAt);
		verifyScreenText(sheetName, expText, actText, "Executed At", "Executed At");
		waitForSync(1);

		// Notify Name verification
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_executedDate;xpath")));
		String actText1 = ele.getAttribute("value");
		String expText1 = data(executedOn);
		verifyScreenText(sheetName, expText1, actText1, "Executed On", "Executed On");
		waitForSync(1);

	}
	
	
	/**
	 * Description... To enter invalid execution date
	 * @param date : Invalid execution date
	 * @throws InterruptedException
	 * @throws AWTException
	 */	
	public void enterInvalidExecutedDate(String date) throws InterruptedException, AWTException {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_executionDate;id", date, "ExecutedDate", screenName);
		performKeyActions(sheetName, "inbx_executionDate;id", "TAB", "ExecutedDate", screenName);
		waitForSync(3);

	}
	
	
	/**
	 * Description... To enter invalid chargeable weight
	 * @param weight : Invalid chargeable weight
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterInvalidChargeableWeight(String weight) throws InterruptedException, AWTException {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_chrgWght;name", weight, "Chargeable Weight", screenName);
		performKeyActions(sheetName, "inbx_chrgWght;name", "TAB", "Chargeable Weight", screenName);

	}
	
	
	/**
	 * Description... To verify shipment is not ready for carriage
	 * @throws Exception
	 */	
	public void verificationOfNotRFCStatus() throws Exception {

		waitForSync(3);
		verifyElementDisplayed(sheetName, "txt_NotRFC;xpath", "5", screenName, "Ready For Carriage");

	}
	
	
	/**
	 * Description... To verify shipment is ready for carriage
	 * @throws Exception
	 */
	public void verificationOfRFCStatus() throws Exception {

		waitForSync(3);
		verifyElementDisplayed(sheetName, "txt_RFC;xpath", "5", screenName, "Ready For Carriage");

	}
	
	
	/**
	 * Description... To click custom info checkbox
	 * @param value 
	 */
	public void clkChkBoxInCustInfo(String value) {
		String pmKey = data(value);
		String dynxpath = "//input[@type='checkbox']" + "[contains(.,' "
				+ pmKey + "')]";
		ele = findDynamicXpathElement(dynxpath, "Check Box", "ScreenName");
		try {
			clickWebElement(ele, "Check Box", screenName);

		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		waitForSync(3);

	}
	
	
	/**
	 * Description...  Delete the records in Custom Info tab
	 * @throws IOException 
	 */
	public void clikCustomInfoDeleteButton() throws IOException {
		waitForSync(1);
		try {
			waitForSync(2);
			clickWebElement(sheetName, "clk_dltButton;xpath", "Delete",
					screenName);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Description... To add multiple entries for custom info table
	 * @param RowNumber
	 * @param customsAuthority
	 * @param parameter
	 * @param value
	 * @throws IOException 
	 */
	public void custonInfoTable(String RowNumber, String customsAuthority,
			String parameter, String value) throws IOException {
		int n = Integer.parseInt(data(RowNumber));

		for (int i = 0; i <= n; i++) {

			try {
				clickWebElement(sheetName, "clk_Add;xpath", "Add Button",
						screenName);
				Thread.sleep(2000);
				selectValueInDropdownWthXpath(
						"(//select[@name='customsAuthority'])[" + (i + 1) + "]",
						data("customsAuthority"), "customs Authority", "VisibleText");
				waitForSync(3);
				selectValueInDropdownWthXpath(
						"(//select[@name='customsParameter'])[" + (i + 1) + "]",
						data("parameter"), "parameter", "VisibleText");
				String column = value;
				if (i != 0) {
					column = value + i;
				}
				JavascriptExecutor js = (JavascriptExecutor) driver;
				String javaSript = "document.getElementById('CMP_Operations_Shipment_CaptureAWB_AditionalInfo_CustomInfoValue"
						+ (i) + "').value='" + data(column) + "'";

				js.executeScript(javaSript);
				WebDriver driver = (WebDriver) js;

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

		}

	}
	/**
	 * Description... List AWB without handling status popup
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listAWBWithStatusPopup(String awbNo, String ShipmentPrefix) throws InterruptedException, IOException {

		String sheetName = "Generic_Elements";

		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",screenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", data(awbNo), "AWB No", screenName);
		clickWebElement(sheetName, "btn_List;xpath", "List Button", screenName);
		waitForSync(6);
	
	}
	/**
	 * @author A-8783
	 * Desc- To verify the AWB status in the Check shipment status popup
	 * @param status
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void checkStatus(String status) throws InterruptedException, IOException {
		
			try {
			driver.switchTo().frame("popupContainerFrame");
			waitForSync(1);
			String locator=xls_Read.getCellValue(sheetName, "img_securityStatus;xpath");
			locator=locator.replace("*", data(status));
			waitForSync(1);
			if(driver.findElement(By.xpath(locator)).isDisplayed()) {
				writeExtent("Pass", "Verified the staus as " + data(status) + " in " + screenName);
				
			}
			}
			
			catch(Exception e) {
				writeExtent("Fail", "Could not verify the staus as " + data(status) + " in " + screenName);
			}
			finally {
			
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
			clickWebElement(sheetName, "btn_chkShipmentStatus;xpath", "Check Shipment Status", screenName);
			waitForSync(2);
			}
			

		
	}

	
	/**
	 * Description... To verify error message in case when check boxes are not selected
	 */
	public void verifyErrorMsgInCustInfo() {

		try {
			switchToFrame("default");
			String expErrorMsg = "Please select a row.";
			String xpath = "//div[@id='ic-sd-msgc']";
			String actErrorMsg = driver.findElement(By.xpath(xpath)).getText();
			verifyValueOnPage(actErrorMsg, expErrorMsg, "", screenName, "Error Message Verification");
			handleAlert("Accept", "CaptureAWB");
			switchToWindow("getParent");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Description... To click on consol check box and HAWB button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */	
	public void clickHAWB() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "chk_Consol;name", "Consol checkbox", screenName);
		clickWebElement(sheetName, "btn_HAWB;name", "HAWB Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		try {
			ele = driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"));
			ele.click();
			Thread.sleep(4000);
		} catch (Exception e) {
		}
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... To check HAWB document finalised checkbox
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickHAWBDocFinalized() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "chk_HawbDocFinalized;name", "HAWB Doc Finalized checkbox", screenName);
	}

	
	/**
	 * Description... To check security data received check box in security and screening screen
	 * @throws Exception
	 */
	public void checkDataRcvd() throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(4000);
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");
		String date = createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_secDate;xpath", date, "Origin in shipment details", screenName);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

	}


	/**
	 * Description... To fill check sheet on OPR026
	 * @throws Exception
	 */	
	public void checkSheet() throws Exception {		

		clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",
				screenName);

		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);
		try{

			if(driver.findElement(By.xpath("//td[contains(text(),'No Check Sheets configured')]")).isDisplayed())
			{

				System.out.println("No check sheet configured");
				switchToFrame("default");		
				switchToFrame("contentFrame","OPR026");
				driver.findElement(By.xpath("//button[@title='Close']")).click();


			}}
		catch(Exception e){
			selectValueInDropdown(sheetName, "lst_DGDetailsCaptureComplete;xpath",
					data("DGDetailsCaptureComplete"), "DG Details Capture Complete",
					"VisibleText");
			selectValueInDropdown(sheetName, "lst_DGRCheckOK;xpath",
					data("DGRCheckOK"), "DGR Check OK",
					"VisibleText");
			selectValueInDropdown(sheetName, "lst_DGRSpecial;xpath",
					data("DGRCheckOK"), "Special",
					"VisibleText");
			selectValueInDropdown(sheetName, "lst_DGRExceedWt;xpath",
					data("DGRCheckOK"), "Exceed Weight Check",
					"VisibleText");
			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			
			waitForSync(2);
			switchToFrame("contentFrame", "OPR026");
			driver.switchTo().frame("popupContainerFrame");

			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
		}


		waitForSync(2);


	}


	/**
	 * Description... To verify Precheck status of the shipment
	 * @param PrecheckStatus : Expected Precheck status e.g.,On-hold, Failed, Pending, NA, Success
	 * @throws InterruptedException
	 */
	public void verifyPrecheckStatus(String PrecheckStatus) throws InterruptedException {
		String dynxpath = xls_Read.getCellValue(sheetName, "img_precheckStatus;xpath");
		String imgxpath = "";
		switch (PrecheckStatus) {
		case "On-hold":
			imgxpath = dynxpath + "[contains(@src,'pause')]";
			WebElement img = driver.findElement(By.xpath(imgxpath));
			verifyElementDisplayed(img, "precheck verification", "Capture AWB", "on hold status");
			break;
		case "Failed":
			imgxpath = dynxpath + "[contains(@src,'error')]";
			WebElement img2 = driver.findElement(By.xpath(imgxpath));
			verifyElementDisplayed(img2, "precheck verification", "Capture AWB", "Failed status");
			break;
		case "Pending":
			imgxpath = dynxpath + "[contains(@src,'loading')]";
			WebElement img3 = driver.findElement(By.xpath(imgxpath));
			verifyElementDisplayed(img3, "precheck verification", "Capture AWB", "Pending status");
			break;
		case "NA":
			String actText = getElementText(sheetName, "txt_precheckStatus;xpath", "Precheck Status", screenName);
			verifyScreenText(screenName, "NA", actText, "Precheck Status", "Precheck NA verification");
			break;

		case "Success":
			imgxpath = dynxpath + "[contains(@src,'tick')]";
			WebElement img4 = driver.findElement(By.xpath(imgxpath));
			verifyElementDisplayed(img4, "precheck verification", "Capture AWB", "Success status");
			break;
		}

	}
	
	
	/**
	 * Description... To update shipment description
	 * @param ReplacementValue : test data column name for new shipment description
	 * @throws InterruptedException
	 */
	public void updateShpmntDescription(String ReplacementValue) throws InterruptedException{
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_shipmntDescription;xpath", data(ReplacementValue), "Origin in shipment details", screenName);
		waitForSync(5);
	}
	/**
	 * Description... To capture UBR number
	 */
	public void ubrNumber(){
		waitForSync(5);
		String ubr = driver.findElement(By.xpath("//input[@name='ubrNumber']")).getAttribute("value");
		System.out.println(ubr);
		map.put("UBRNo",ubr);
	}


	/**
	 * Description... To accept the alert
	 */
	public void handleAlert(){
		handleAlert("Accept", screenName);
	}

	
	/**
	 * Description..Verify agent scc code
	 */
	public void verifyAgentCodeSCCCode(){    
		String AgentCode = getAttributeWebElement(sheetName , "inbx_AgentCode;xpath" , "Code" , "value", screenName );
		String SCCCode=getAttributeWebElement(sheetName, "inbx_SCC;xpath", "SCC", "value", screenName);
		String replaceSCCCode=SCCCode.replace('/',','); //FWB SCC format -> "SLY/MDC/GEN" 
		System.out.println("replaced"+replaceSCCCode);  //OPR026 SCC format-> "SLY.MDC.GEN"

		map.put("CapAgentCode",AgentCode);
		map.put("CapSCCCode", SCCCode);
		map.put("SCCCode1", replaceSCCCode);
	}
	
	
	/**
	 * Description... To capture Agent code and SCC codes from screen
	 */   
	public void verifySCI(String SCI){
		By element = getElement(sheetName, "lst_SCI;xpath");
		WebElement ele = driver.findElement(element);
		Select sel=new Select(ele);
		WebElement option = sel.getFirstSelectedOption();
		String actText =option.getText();
		String expText = SCI;
		verifyScreenText(sheetName, expText, actText, "verify SCI ", "Capture AWB");

	}
	
	
	/**
	 * Description... Add HAWB Details
	 * @param HAWB
	 * @param Shipper
	 * @param Consignee
	 * @param Origin
	 * @param Destination
	 * @param Pieces
	 * @param Weight
	 * @throws Exception
	 */
	public  void addHAWBDetails(String HAWB, String Shipper, String Consignee, String Origin, String Destination, String Pieces,String Weight) throws Exception {
		String hawbNo=generateHAWB();
		map.put(HAWB,hawbNo);
		
		
		switchToWindow("child");
		clickWebElement("CaptureHAWB_OPR029", "inbx_houses;id", "Houses", screenName);
		waitForSync(2);
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_houses;id", data(HAWB), "Houses", screenName);
		keyPress("TAB");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_shipper;name", data(Shipper), "Shipper", screenName);
		keyPress("TAB");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_consignee;name", data(Consignee), "Consignee", screenName);      
		keyPress("TAB");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_origin;name", data(Origin), "Origin", screenName);
		keyPress("TAB");  
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_destination;name", data(Destination), "Destination", screenName);
		keyPress("TAB");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_pieces;name", data(Pieces), "Pieces", screenName);
		keyPress("TAB");
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_weigth;name", data(Weight), "Weight", screenName);
		keyPress("TAB");
		waitForSync(2);
		enterValueInTextbox("CaptureHAWB_OPR029", "inbx_Desc;name", "HAWB Remarks", "Remarks", screenName);
		keyPress("TAB");
		enterHAWBHSCode();
		clickWebElement("CaptureHAWB_OPR029", "btn_hawbOK;id", "OK", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");
	}


	/**
	 * Description... To verify shipment description
	 * @param Shipmentdesc
	 */	    
	public void verifyShipmentDescription(String Shipmentdesc){
		By element = getElement(sheetName, "inbx_ShipmentDesc;xpath");
		String actText = driver.findElement(element).getAttribute("value");
		String expText = Shipmentdesc;
		verifyScreenText(sheetName, expText, actText, "Verify Pieces ", "Capture AWB");

	}
	
	
	/**
	 * Description... Capture DGR Details
	 * @param UNIDNo
	 * @param properShippingName
	 * @param netQuantityperPackage
	 * @param noOfPackage
	 * @param PerPackageUnit
	 * @throws Exception
	 */
	public void captureDGRDetails(String UNIDNo, String properShippingName,
			String netQuantityperPackage, String noOfPackage,
			String PerPackageUnit) throws Exception {
		waitForSync(3);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR026");
		driver.switchTo().frame("popupContainerFrame");    


		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", data(UNIDNo),
				"UNID No", screenName);

		keyPress("TAB");
		keyRelease("TAB");    


		selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
				data(properShippingName), "Proper Shipping Name", "Value");


		waitForSync(1);


		enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
				data(netQuantityperPackage), "Net Quantity Per Package", screenName);
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", data(noOfPackage),
				"No Of Package", screenName);    




		selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
				data(PerPackageUnit), "Net Quantity Per Package Unit", "VisibleText");


		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);

		waitForSync(6);

		clickWebElement(sheetName, "btn_Dgrok;xpath", "Ok Button", screenName);


	}
	
	
	/**
	 * Description... 	Capture DGR Details
	 * @param UNIDNo
	 * @param properShippingName
	 * @param netQuantityperPackage
	 * @param noOfPackage
	 * @param PerPackageUnit
	 * @param NoOfUNIUD
	 * @throws Exception
	 */
	public void captureDGRDetails(String UNIDNo[], String properShippingName[],
			String netQuantityperPackage[], String noOfPackage[],
			String PerPackageUnit[], int NoOfUNIUD) throws Exception {
		waitForSync(3);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR026");
		driver.switchTo().frame("popupContainerFrame");      

		for(int i=0;i<NoOfUNIUD;i++)
		{
			enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo[i],
					"UNID No", screenName);

			keyPress("TAB");
			keyRelease("TAB");    


			selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
					properShippingName[i], "Proper Shipping Name", "Value");


			waitForSync(1);


			enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
					netQuantityperPackage[i], "Net Quantity Per Package", screenName);
			enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage[i],
					"No Of Package", screenName);    




			selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
					PerPackageUnit[i], "Net Quantity Per Package Unit", "VisibleText");


			clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
			waitForSync(4);
		}

		clickWebElement(sheetName, "btn_Dgrok;xpath", "Ok Button", screenName);


	}

	
	/**
	 * Description... Click DGR Goods
	 * @throws Exception
	 */
	public void clickDGRGoods() throws Exception {
		waitForSync(3);
		clickWebElement(sheetName, "btn_DgrGoods;xpath", "DGR Goods Button",
				screenName);

	}
	
	
	/**
	 * Description... Verify Shipment Description Text area
	 * @param Shipmentdesc
	 */
	public void verifyShipmentDescriptionTextarea(String Shipmentdesc){
		By element = getElement(sheetName, "textarea_ShipmentDesc;xpath");
		String actText = driver.findElement(element).getAttribute("value");
		String expText = Shipmentdesc;
		verifyScreenText(sheetName, expText, actText, "Verify Pieces ", "Capture AWB"); //A-8290
	}	
	
	
	/**
	 * Description... Update Shipment Desciption Textarea
	 * @param Shipmentdesc1
	 * @throws InterruptedException
	 */
	public void updateShipmentDesciptionTextarea(String Shipmentdesc1) throws InterruptedException {
		waitForSync(5);
		enterValueInTextbox(sheetName, "textarea_ShipmentDesc;xpath", Shipmentdesc1 , "shipment details", screenName);
		waitForSync(5); 
	}

	
	/**
	 * Description... 	Update Weight		
	 * @param updatedWeight
	 * @throws InterruptedException
	 */
	public void updateWeight(String updatedWeight) throws InterruptedException{
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_grossWeight;xpath", updatedWeight , "shipment details", screenName);
		waitForSync(5);  

	}
	
	
	/**
	 * Description... Provide Executed Date
	 * @throws Exception
	 */
	public void provideExecutedDate() throws Exception  {

		JavascriptExecutor js = (JavascriptExecutor)driver;
		String script = "document.getElementById('executedDate').value ='"+ createDateFormat("dd-MMM-YYYY",0,"DAY","") +"'";
		js.executeScript(script);
		WebDriver driver = (WebDriver)js;

	}
	
	
	/**
	 * Description... Enter Slac Pieces
	 * @param slacPieces
	 * @throws Exception
	 */
	public void enterSlacPieces(String slacPieces) throws Exception  {

		enterValueInTextbox(sheetName, "inbx_slacPieces;name", slacPieces, "Slac Pieces", screenName);

	}
	

/**
	 * @author A-9844
	 * @Desc To verify the  OCI details with screening information
	 * @param supplCustomsInfo
	 * @param source
	 * @param infoId
	 * @param customsInfoId
	 */
	public void verifyOCIDetailsWithScreeningDetails(String supplCustomsInfo[],String source[], String infoId[], String customsInfoId[]){
		
		try{
			
			for(int i=0;i<supplCustomsInfo.length;i++)
			{
			
			String actSource = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_source;xpath").replace("*",Integer.toString(i+1)))).getAttribute("value");
			verifyScreenText(screenName ,data(source[i]), actSource, "Source","Source");

			String actInfoId=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_informatnId;xpath").replace("*",Integer.toString(i+1)))).getAttribute("value");			
			verifyScreenText(screenName ,data(infoId[i]), actInfoId, "Information ID","Information ID");

			String actCustomsInfoId = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_customInformatnId;xpath").replace("*",Integer.toString(i+1)))).getAttribute("value");
			verifyScreenText(screenName ,data(customsInfoId[i]), actCustomsInfoId, "Customs Information ID","Customs Information ID");

			String actsupplCustomsInfo = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_supplCustomsInformatn;xpath").replace("*",Integer.toString(i+1)))).getAttribute("value");
			verifyScreenText(screenName ,data(supplCustomsInfo[i]), actsupplCustomsInfo, "Supplementary Customs Info","Supplementary Customs Info");
			
		}
			
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the OCI details on "+screenName);
		}
		
	}
	/**
	 * Description... Enter Shipment Details
	 * @param Pieces
	 * @param Weight
	 * @param Volume
	 * @param CommodityCode
	 * @param ShipmentDesc
	 * @throws Exception
	 */
	public void enterShipmentDetails(String Pieces, String Weight, String Volume, String CommodityCode, String ShipmentDesc) throws Exception  {

		
		/***********************************/
		//Enter SCC if not entered
		String locatorValue = xls_Read.getCellValue(sheetName, "inbx_capawb_scc;name");
		String scc=driver.findElement(By.name(locatorValue)).getAttribute("value");
		
		if(scc.equals(""))
		{
			enterValueInTextbox(sheetName, "inbx_capawb_scc;name", data("SCC"), "SCC", screenName);
		}
		/***********************************/
		enterValueInTextbox(sheetName, "inbx_Pieces;name", data(Pieces), "Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_grossWeight;xpath", data(Weight), "Weight", screenName);
		enterValueInTextbox(sheetName, "inbx_grossVol;xpath", data(Volume), "Volume", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_cmdtyCode;name", data(CommodityCode), "Commodity Code", screenName);
		performKeyActions(sheetName, "inbx_cmdtyCode;name", "TAB", "Commodity Code", screenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_ShipmentDesc;name", data(ShipmentDesc), "Shipment description", screenName);
		waitForSync(2);

	}
	
	
	/**
	 * Description... Upload File
	 * @throws Exception
	 */
	public void uploadFileInOPR026(String typeOfDocument,String Remarks,String filePath) throws Exception
	{
		clickWebElement(sheetName, "btn_viewUploadFiles;xpath", "Upload button", screenName);
		waitForSync(5);
		switchToWindow("storeParent");
		switchToWindow("child");
		selectValueInDropdown(sheetName, "lst_TypeOfDocument;id", data(typeOfDocument), "Type Of Document", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_viewUploadRemarks;name", data(Remarks), "Remarks", screenName);
		waitForSync(3);
		WebElement uploadEle = driver.findElement(By.xpath("//*[@id='theFile']"));
		uploadEle.click();
		waitForSync(3);
		fileUpload(filePath);
		waitForSync(2);
		clickWebElement(sheetName, "btn_viewUploadSave;id", "Save Button", screenName);
		clickWebElement(sheetName, "btn_viewUploadClose;id", "Close Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
	}
	
	
	/**
	 * Description..File upload
	 * @param fileName
	 * @return
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public CaptureAWB_OPR026 fileUpload(String fileName) throws AWTException, InterruptedException
	{

		StringSelection ss = new StringSelection(message_files+fileName+".txt");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		//imitate mouse events like ENTER, CTRL+C, CTRL+V
		Robot robot = new Robot();
		robot.delay(250);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(90);
		robot.keyRelease(KeyEvent.VK_ENTER);
		return this;
	}
	
	
	/**
	 * Description... Enter Agent Code
	 * @param AgentCode
	 * @throws Exception
	 */
	public void enterAgentCode(String AgentCode) throws Exception  {

		System.out.println(AgentCode);
		enterValueInTextbox(sheetName, "inbx_AgentCode;xpath", data(AgentCode), "Agent Code", screenName);
		keyPress("TAB");
		keyRelease("TAB");

	}
	
	
	/**
	 * Description : Verifying Source of AWB
	 * @author A-9175
	 * @param sourceType
	 * @throws InterruptedException
	 */
	public void verifySource(String sourceType) throws InterruptedException
	{

		try
		{
			By source = getElement(sheetName, "label_sourceValue;xpath");
			String actText = driver.findElement(source).getText();

			if(actText.contains(data(sourceType)))
			{
				writeExtent("Pass","Source is displayed as "+data(sourceType)+" on "+screenName);

			}

			else
			{
				writeExtent("Fail","Source is not displayed as "+data(sourceType)+" on "+screenName);
				
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Source is not displayed as "+data(sourceType)+" on "+screenName);
			
		}
	}

	/**
	 * Description : Verifying Source of AWB
	 * @author A-9175
	 * @param sourceType
	 * @throws InterruptedException
	 */
	public void verifySource(String sourceType,boolean isAssertionReq) throws InterruptedException
	{

		try
		{
			By source = getElement(sheetName, "label_sourceValue;xpath");
			String actText = driver.findElement(source).getText();

			if(data(sourceType).contains("GBL"))
			{
				if(actText.equals("GBL")||actText.equals("FBL"))
				{
					writeExtent("Pass","Source is displayed as "+data(sourceType)+" on "+screenName);

				}

				else
				{
					writeExtent("Fail","Source is not displayed as "+data(sourceType)+" on "+screenName);
					if(isAssertionReq)
					{
						Assert.assertFalse(true, "Source is not displayed as "+data(sourceType)+" on "+screenName);
					}
				}
			}

			else
			{
				if(actText.contains(data(sourceType)))
				{
					writeExtent("Pass","Source is displayed as "+data(sourceType)+" on "+screenName);

				}

				else
				{
					writeExtent("Fail","Source is not displayed as "+data(sourceType)+" on "+screenName);
					if(isAssertionReq)
					{
						Assert.assertFalse(true, "Source is not displayed as "+data(sourceType)+" on "+screenName);
					}
				}
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Source is not displayed as "+data(sourceType)+" on "+screenName);
			if(isAssertionReq)
			{
				Assert.assertFalse(true, "Source is not displayed as "+data(sourceType)+" on "+screenName);
			}
		}
	}
	/**
	 * Description..verify status of save button
	 * @param val
	 * @throws InterruptedException
	 */
	public void verifyStatusOfSaveButton(boolean val) throws InterruptedException {
		waitForSync(5);
		if(val)
			verifyElementEnabled(sheetName, "btn_Save;xpath", "in Capture Screen", screenName, "Save Button");
		else
			verifyElementNotEnabled(sheetName, "btn_Save;xpath", "in Capture Screen", screenName, "Save Button");


	}
	
	
	/**
	 * Description..verify status of execute button
	 * @param val
	 * @throws InterruptedException
	 */
	public void verifyStatusExecuteButton(boolean val) throws InterruptedException {
		waitForSync(5);
		if(val)
			verifyElementDisplayed(sheetName, "btn_Execute;id", "in Capture Screen", screenName, "Execute Button");
		else
			verifyElementNotDisplayed(sheetName, "btn_Execute;id", "in Capture Screen", screenName, "Execute Button");

	}

	
	/**
	 * Description... Enter Routing
	 * @param Destination
	 * @param FlightCode
	 * @throws Exception
	 */
	public void enterSecondRouting(String Destination, String FlightCode) throws Exception  {

		clickWebElement(sheetName, "inbx_routingAirport1;id","Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_routingAirport1;id", data(Destination), "Destination", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		enterValueInTextbox(sheetName, "inbx_routingCarrier1;id", data(FlightCode), "Carrier code", screenName);

	}
	
	
	/** @author A-9175
	 * Description : Click on Send FWB button
	 * @throws Exception
	 */
	public void sendFWB() throws Exception {
		switchToWindow("storeParent");

		clickWebElementByActionClass(sheetName, "link_sendButtonOptions;xpath", "Send Button Options", screenName);
		waitForSync(2);
		clickWebElementByActionClass(sheetName, "link_sendButtonOptions;xpath", "Send Button Options", screenName);
		javaScriptToclickElement(sheetName, "btn_sendFWB;xpath", "Send FWB Button", screenName);
		waitForSync(3);
	}
	
	
	/**
	 * @author A-9175
	 * Description : Enter Send Fwb Details
	 * @param interfaceVal
	 * @throws Exception
	 */
	public void enterSendFwbDetails(String interfaceVal) throws Exception
	{
		switchToWindow("child");
		clickWebElement(sheetName, "btn_addSendFwbDetails;xpath","Add Button", screenName);
		waitForSync(2);
		selectValueInDropdown(sheetName, "drpdn_interfaceSystem;xpath", interfaceVal, "Interface Code", "VisibleText");
		clickWebElement(sheetName, "btn_OkSendFWB;id","Ok Button", screenName);
		switchToWindow("getParent");

	}
	
	
	/**
	 * @author A-9175
	 * Description : Click Delete Awb Button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickDeleteAWB() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_DeleteAWB;id", "Delete AWB Button", screenName);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath","Additional Information Tab", screenName);
		switchToFrame("contentFrame","OPR026");
		waitForSync(2);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Ok Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");

	}


	/**
	 * Description... Enter Routing
	 * @param Destination
	 * @param FlightCode
	 * @throws Exception
	 */
	public void enterRouting(String Destination, String FlightCode) throws Exception  {

		handleShipmentStatusPopUp();
		clickWebElement(sheetName, "inbx_routingAirport;id", "Destination", screenName);
		enterValueInTextbox(sheetName, "inbx_routingAirport;id", data(Destination), "Destination", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		enterValueInTextbox(sheetName, "inbx_routingCarrier;id", data(FlightCode), "Carrier code", screenName);

	}
	
	
	/**
	 * Description... Verify Hawb PopUp After Click Save
	 */
	public void verifyHawbPopUpAfterClickSave(){
		try{
			handleAlert("getText",screenName);
			waitForSync(2);
			String actAlert=getPropertyValue(globalVarPath, "AlertText");
			waitForSync(2);
			String expAlert=data("AlertMessage");
			waitForSync(4);
			if(actAlert.contains(expAlert)){
				customFunction.onPassUpdate(screenName, expAlert, actAlert, "Capturing HAWB alert", "Capturing HAWB alert after clicking Save");
			}else{
				customFunction.onFailUpdate(screenName, expAlert, actAlert, "Capturing HAWB alert", "Capturing HAWB alert after clicking Save");
			}
			waitForSync(3);
			handleAlert("Accept", screenName);
			switchToFrame("contentFrame", "OPR026");
			waitForSync(2);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	/**
	 * Description... Click Add OCI	 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickAddOCI() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "chk_HawbDocFinalized;name", "Other Custom Information", screenName);
	}
	
	
	/**
	 * 	Description... Click on Save button in OPR026 with out handling Pop-up message 
	 */
	public void clickSave(){
		try{
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
			clickWebElement(sheetName, "btn_Save;xpath", "Click Save", screenName);
			waitForSync(4);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	
	/**
	 * Description... Add Other Custom Information
	 * @param SerialNo
	 * @param CountryCode
	 * @param InformationId
	 * @param CustomInformationId
	 * @param SupplementryCustomsInfo
	 * @throws Exception
	 */
	public void addOtherCustomInformation(String SerialNo, String CountryCode, String InformationId, 
			String CustomInformationId, String SupplementryCustomsInfo) throws Exception  {

		enterValueInTextbox(sheetName, "inbx_OCISerialNo;name", SerialNo, "Serial No", screenName);
		enterValueInTextbox(sheetName, "inbx_OCICountryCode;name", CountryCode, "Country code", screenName);
		enterValueInTextbox(sheetName, "inbx_informationId;name", InformationId, "Information Id", screenName);
		enterValueInTextbox(sheetName, "inbx_customInformationId;name", CustomInformationId, "Custom Information Id", screenName);
		enterValueInTextbox(sheetName, "inbx_supplementryCustomsInfo;name", SupplementryCustomsInfo, "Supplementry Customs Info", screenName);

		waitForSync(2);
	}
	
	
	/**
	 * Description... Click Save Ammended Details
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickSaveAmmendeDetails() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_saveAmmendedDetails;id", "Save Ammended Details Button", screenName);
	}
	
	
	/**
	 * Description... Update Shipper ZipCode
	 * @param zipCode
	 * @throws Exception
	 */
	public void updateShipperZipCode(String zipCode) throws Exception{
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_shipperZipCode;xpath", data(zipCode),
				"Shipper Zip Code", screenName); 

	}
	
	
	/**
	 * Description..update consignee zip code
	 * @param zipCode
	 * @throws Exception
	 */
	public void updateConsigneeZipCode(String zipCode) throws Exception{
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_consigneeZipCode;name", data(zipCode),
				"Consignee Zip Code", screenName); 

	}
	
	
	/**
	 * Description... Verify Error Message If ZipCode Of Shipper Is Not Entered
	 * @throws InterruptedException
	 */
	public void verifyErrorMessageIfZipCodeOfShipperIsNotEntered() throws InterruptedException {

		try {
			Thread.sleep(3000);
			String pmKey = data("AWBNo");
			String expErrorMsg = ("Shipper Zip/Postal code is not specified for 020-"
					.concat(pmKey));

			String dynxpath = "(//table[@class='ic-errors-table']//td[contains(.,'"+pmKey+"')])[2]";

			String actErrMsg = driver.findElement(By.xpath(dynxpath)).getText();

			if (actErrMsg.contains(expErrorMsg)) {
				System.out.println("found true for ");

				onPassUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			} else {
				onFailUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}
	
	
	/**
	 * Description... Update Consignee State
	 * @param consigneeState
	 * @throws InterruptedException
	 */
	public void updateConsigneeState(String consigneeState) throws InterruptedException{
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_consneeState;xpath", data(consigneeState),
				"Shipper Zip Code", screenName); 
	}
	
	
	/**
	 * Description... verify the error message when consignee state is not provided by the user
	 */
	public void verifyErrorMsgIfConsgnStateIsAbsent(){
		try {
			Thread.sleep(3000);
			String pmKey = data("AWBNo");
			String expErrorMsg = ("Consignee State is not specified for 020-"
					.concat(pmKey));

			String dynxpath = "(//table[@class='ic-errors-table']//td[contains(.,'"+pmKey+"')])[2]";

			String actErrMsg = driver.findElement(By.xpath(dynxpath)).getText();

			if (actErrMsg.contains(expErrorMsg)) {
				System.out.println("found true for ");

				onPassUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			} else {
				onFailUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			}
		} catch (Exception e) {

		}

	}

	
	/**
	 * Description... Click Console Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickConsoleButton() throws InterruptedException, IOException {
		waitForSync(3);
		clickWebElement(sheetName, "chk_Consol;name", "Consol checkbox",
				screenName);
		waitForSync(8);

	}
	public void demo()
	{
		//String s=driver.findElement(By.xpath("//input[@name='shipperZipCode']")).getText();
		
		String s=driver.findElement(By.xpath("//input[@name='shipperZipCode']")).getAttribute("value");
		System.out.println(s);
	}
	
	/**
	 * Description... Click Close button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickClose() throws InterruptedException, IOException{
		waitForSync(5);
		clickWebElement(sheetName, "btn_clickbutton;xpath","Close button", screenName);
	}


	/**
	 * Description... verify error message when user provides wrong input in consignee
	 * @throws Exception
	 */
	public void vrifyConsigneePostalCodeErrorMsg() throws Exception {
		try {
			Thread.sleep(3000);
			String pmKey = data("HAWB");
			String expErrorMsg = ("Consignee Zip/Postal for ".concat(pmKey)
					.concat(" does not have 5 digits."));
			String dynxpath = "(//table[@class='ic-errors-table']//td[contains(.,'"
					+ pmKey + "')])[2]";

			String actErrMsg = driver.findElement(By.xpath(dynxpath)).getText();

			if (actErrMsg.contains(expErrorMsg)) {
				System.out.println("found true for ");

				onPassUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			} else {
				onFailUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			}
		} catch (Exception e) {
		}
	}

	// To verify error message when user provides wrong input in consignee popstal code for a hawb
	// In this case the error message is coming different from the other error messages
	/**
	 * Description... Verify Consignee Postal Code Error message
	 * @throws Exception
	 */
	public void vrifyConsigneePostalCodeErroMsg() throws Exception {
		try {
			Thread.sleep(3000);
			String pmKey = data("HAWB");
			String expErrorMsg = "Incorrect Zip/Postal code specified for Consignee for ".concat(pmKey);
			String dynxpath = "(//table[@class='ic-errors-table']//td[contains(.,'"
					+ pmKey + "')])[2]";

			String actErrMsg = driver.findElement(By.xpath(dynxpath)).getText();

			if (actErrMsg.contains(expErrorMsg)) {
				System.out.println("found true for ");

				onPassUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			} else {
				onFailUpdate(screenName, expErrorMsg, actErrMsg,
						"Custom verification against " + pmKey + " On ",
						"Error message verification");

			}
		} catch (Exception e) {
		}
	}
	
	
	/**
	 * Description... 	Verify Charge Details
	 * @param ChargeCodeHead
	 * @throws InterruptedException
	 */
	public void verifyChargeDetails(String ChargeCodeHead) throws InterruptedException{

		By element = getElement(sheetName, "inbx_chrgDtlsChrgHead;name");
		String actText = driver.findElement(element).getAttribute("value");
		String expText = data(ChargeCodeHead);
		verifyValueOnPage(actText, expText, "", screenName, "Charge Head Code Verification");

		element = getElement(sheetName, "chk_dueAgent;xpath");
		boolean checked = driver.findElement(element).isSelected();
		if (checked){
			onPassUpdate(screenName, "", "",
					"Due Agent checked verification",
					"Due Agent checked verification");

		} else {
			onFailUpdate(screenName, "", "",
					"Due Agent checked verification",
					"Due Agent checked verification");

		}

	}
	

	/**
	 * Description... Security AndScreening Screen Without SCC1
	 * @throws Exception
	 */
	public void securityAndScreeingScreenWithoutSCC1() throws Exception {
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");

		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("contentFrame","OPR026");


	}
	
	
	/**
	 * Description... 	Change Payment Type
	 * @throws InterruptedException
	 */
	public void changePaymentType() throws InterruptedException{
		selectValueInDropdown(sheetName, "lst_chargeCode;name",
				"CC", "ChargeCode", "Value");
		selectValueInDropdown(sheetName, "lst_paymentType;name",
				"CC", "Payment Type", "Value");

	}
	
	/**
	 * @author A-8783
	 * Description... 	overloaded method to change Payment Type
	 * @throws InterruptedException
	 */
	public void changePaymentType(String paymentType) throws InterruptedException{
		
		selectValueInDropdown(sheetName, "lst_paymentType;name",
				data(paymentType), "Payment Type", "Value");

	}

	/**
	 * Description... Security And Screening1
	 * @param secSCC
	 * @throws Exception
	 */
	public void securityAndScreeing1(String secSCC) throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(4000);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");

		clickWebElement(sheetName, "btn_shipDetails;xpath", "Shipment Details Panel", screenName);
		clickWebElement(sheetName, "btn_editSCC;xpath", "Edit SCC Button", screenName);

		Thread.sleep(2000);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		Thread.sleep(1000);
		enterValueInTextbox(sheetName, "inbx_newSCC;xpath", data(secSCC), "SecSCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);
		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

	}

	
	/**
	 * @author A-7271
	 * Desc : handle shipment status pop up
	 */
	public void handleShipmentStatusPopUp()
	{
		try
		{
			String locator=xls_Read.getCellValue(sheetName, "btn_chkShipmentStatus;xpath");
			String testEnv=getPropertyValue(globalVarPath, "testEnv");
			if(testEnv.equals("RC4"))
			{
				waitTillScreenloadWithOutAssertion(sheetName, "btn_chkShipmentStatus;xpath","Check Shipment Status",screenName,3) ;
			}

			if(driver.findElement(By.xpath(locator)).isDisplayed())
			{
				clickWebElement(sheetName, "btn_chkShipmentStatus;xpath", "Check Shipment Status", screenName);
				waitForSync(2);
			}
		}
          catch(Exception e)

          {

          }


	}
	/**
	 * @author A-7271
	 * Desc : handle shipment status pop up
	 */
	public void handleShipmentStatusPopUpIfDisplayed()
	{
		try
		{
			String locator=xls_Read.getCellValue(sheetName, "btn_chkShipmentStatus;xpath");
			

			if(driver.findElement(By.xpath(locator)).isDisplayed())
			{
				clickWebElement(sheetName, "btn_chkShipmentStatus;xpath", "Check Shipment Status", screenName);
				waitForSync(2);
			}
		}
          catch(Exception e)

          {

          }


	}
	
	/**
	 * Description... List AWB
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listAWB(String awbNo, String ShipmentPrefix) throws InterruptedException, IOException {

		try{
			String sheetName = "Generic_Elements";
			waitTillScreenload(sheetName, "inbx_shipmentPrefix;xpath","Shipment Prefix", screenName);
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",screenName);
			enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", data(awbNo), "AWB No", screenName);
			clickWebElement(sheetName, "btn_List;xpath", "List Button", screenName);
			waitForSync(6);
			handleShipmentStatusPopUp();

			// Store the AWB in map
			map.put("AWBWithBlock",data(ShipmentPrefix)+"-"+data(awbNo));
		}
		catch (Exception e) {
			System.out.println("Could not enter the AWB prefix");
			test.log(LogStatus.FAIL, "Could not enter the AWB prefix in "+screenName);

		}



	}
	/**
	 * @author A-9844
	 * Description... verify second routing and carrier details
	 * @param Destination
	 * @param FlightCode
	 * @throws Exception
	 */
	public void verifySecondRoutingDetails(String Destination, String FlightCode) throws Exception  {

		String actRoutingTo = getAttributeWebElement(sheetName, "inbx_routingAirport1;id", "Routing To", "value", screenName);
		verifyScreenText(screenName ,data(Destination), actRoutingTo, "Routing To","Routing To");
		
		String actCarrierCode = getAttributeWebElement(sheetName, "inbx_routingCarrier1;id", "Carrier Code", "value", screenName);
		verifyScreenText(screenName ,data(FlightCode), actCarrierCode, "Carrier Code","Carrier Code");
		

	}
	/**
	 * @author A-6260
	 * Desc..Verify checksheet templates
	 * @param templates
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyCheckSheetTemplate(String[] templates) throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "btn_CheckSheet;xpath", "CheckSheet Button",
				screenName);
		waitForSync(3); 
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);
		try {
			for(String t:templates) {
				String locator = xls_Read.getCellValue(sheetName, "txt_template;xpath");
				locator=locator.replace("*", t);
				if(driver.findElement(By.xpath(locator)).isDisplayed()) {
					onPassUpdate(screenName,"template "+ t ,"template "+ t, "Verification of checksheet template","Verification of checksheet template");
				} else {
					onFailUpdate(screenName, "template "+ t ,"template "+ t, "Verification of checksheet template","Verification of checksheet template");
				}
			}
			
			//Close button
			try
			{
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", driver.findElement(By.name("btnClose")));
			}
			
			catch(Exception e)
			{
				
			}
			waitForSync(2);
			switchToFrame("contentFrame", "OPR026");
			
		}catch (Exception e) {
			writeExtent("Fail", "Couldnt verify template name in " + screenName);
		}
		}

	/**
	 * Description..Verify gurantee amount
	 * @param guranteeDetails
	 * @throws InterruptedException
	 */
	public void verifyGuarenteeAmount(List<String> guranteeDetails) throws InterruptedException
	{
		driver.findElement(By.xpath("//img[@id='guaranteeDetailsImg']")).click();
		waitForSync(2);

		String actText=  driver.findElement(By.name("guaranteedetails")).getText();


	}

	
	/**
	 * Description... Click Yes Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYesButton() throws InterruptedException, IOException
	{
		switchToFrame("default");
		String locator = xls_Read.getCellValue("Generic_Elements", "btn_Yes;xpath");
		
		try
		{
			while(driver.findElement(By.xpath(locator)).isDisplayed())
			{
				clickWebElement("Generic_Elements", "btn_Yes;xpath", "Yes Button", screenName);
				waitForSync(6);
			}
		}

		catch(Exception e)
		{

		}
		
		switchToFrame("contentFrame", "OPR026");
	}
	
	
	/**
	 * Description...      Change Payment Type
	 * @throws InterruptedException
	 */
	public void enterPaymentType(String chargeCode,String paymentType) throws InterruptedException{
		selectValueInDropdown(sheetName, "lst_chargeCode;name",
				data(chargeCode), "ChargeCode", "Value");
		selectValueInDropdown(sheetName, "lst_paymentType;name",
				data(paymentType), "Payment Type", "Value");

	}


	/**
	 * Description... Verify eHM Status
	 * @param OPRSHPMST
	 * @throws InterruptedException
	 */
	public void verifyeHMStatus(String OPRSHPMST) throws InterruptedException {

		String xpath = xls_Read.getCellValue(sheetName, "div_eHMflag;xpath");
		switch (OPRSHPMST) {

		case "Y":
			WebElement img = driver.findElement(By.xpath(xpath));
			verifyElementDisplayed(img, "eHM flag verification", "Capture AWB", "eHM flag");
			break;
		case "N":

			verifyElementNotDisplayed(sheetName, "div_eHMflag;xpath","eHM flag verification", screenName, "eHM flag");
			break;

		}

	}	
	
	
	/**
	 * Description...  Enter Consignee Address
	 * @throws InterruptedException
	 */
	public void enterConsigneeAddress() throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_consigneeAddress1;name", data("ConsigneeAddress"), "Consignee Address",
				screenName);

	}
	
	
	/**
	 * Description...  Enter Consignee PhoneNo 
	 * @throws InterruptedException
	 */
	public void enterConsigneePhoneNo() throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_consigneeTelephoneNumber;name", data("ConsigneePhoneNo"),
				"Consignee PhoneNo", screenName);
	}
	
	
	/**
	 * Description... Verify Consignee Address and PhoneNo
	 * @throws InterruptedException
	 */
	public void verifyConsigneeAddPhoneNo() throws InterruptedException {
		String actConsigneeAddress = getAttributeWebElement(sheetName, "inbx_consigneeAddress1;name",
				"Consignee Address", "value", screenName);
		String actConsigneePhoneNo = getAttributeWebElement(sheetName, "inbx_consigneeTelephoneNumber;name",
				"Consignee PhoneNo", "value", screenName);

		verifyValueOnPage(actConsigneeAddress.toUpperCase(), data("ConsigneeAddress").toUpperCase(), "Verify Consignee Address", screenName,
				"Consignee Address");
		verifyValueOnPage(actConsigneePhoneNo.toUpperCase(), data("ConsigneePhoneNo").toUpperCase(), "Verify Consignee PhoneNo", screenName,
				"Consignee PhoneNo");
	}
	
	
	/**
	 * Description... Click Reopen AWB
	 * @throws InterruptedException
	 */
	public void clickReopenAWB()throws InterruptedException{
		driver.findElement(By.name("btnReopen")).click();
	}
	
	
	/**
	 * Description... Click AsIsExecute Button
	 * @throws Exception
	 */
	public void asIsExecuteOnly() throws Exception {
		try
		{
			screenName="Capture AWB";
			String testEnv=getPropertyValue(globalVarPath, "testEnv");
			/**** REMOVE COMPLIANCE BLOCK***/
			if(testEnv.equals("RC4"))
			{
			removeCustomsBlock();
			}
			/****************/
			/********** CAPTURE SPX CHECKSHEET *****/
			captureSPXChecksheet();
			/*********************************/

			switchToFrame("default");
			switchToFrame("contentFrame", "OPR026");
			waitForSync(3);
			clickGeneralTab();
			// Click override certificate
			/***String locator = xls_Read.getCellValue(sheetName, "chk_overrideCertifications;id");
			if(data("Origin").equals("IAD"))
			{
				if(!driver.findElement(By.id(locator)).isSelected())
				{
					clickWebElement(sheetName, "chk_overrideCertifications;id", "Override checkbox",screenName);
				}
			}***/
			enterExecutionDate();
			/****** ENTER THE FRENCH CUSTOMS DETAILS****/
			String station=getLoggedInStation("OPR026");  
			
			
				if(station.equals("CDG")) 
				{
					enterFrenchCustomsDetails();
					captureCDGCompChecksheet();
				}
					
			
			/*************************************/
				

				/**********************************/
				enterHSCode();
				/**********************************/

				
			clickWebElement(sheetName, "btn_AsIsExecute;xpath",
					"AsIsExecute Button", screenName);
			waitForSync(12);
			boolean elementpresent=verifyElementEnabled("GeneratePaymentAdvice_CSH007", "btn_FinalizePayment;id");
	        System.out.println(elementpresent);
	        switchToFrame("default");

	        if(!elementpresent)
				waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 20);

			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
				waitForSync(6);
			}

		}


		catch(Exception e)
		{

		}

		finally
		{
		
			switchToFrame("contentFrame", "OPR026");
		}
	}

	
	/**
	 * 
	 * @throws InterruptedException
	 * Desc : Verify if awb is executed
	 */
	public void asIsExecuteVP() throws InterruptedException
	{
		switchToFrame("default");
		try {

			while (driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
					.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
				Thread.sleep(10000);
			}
		} catch (Exception e) {
		}

		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR026");
		Thread.sleep(2000);
		waitTillScreenload(sheetName, "txt_executed;xpath","Executed text", screenName);
		String actText = driver
				.findElement(
						By.xpath(xls_Read.getCellValue(sheetName,
								"txt_executed;xpath"))).getText();
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute",
				"Capture AWB");
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... Provide Shipper Code1
	 * @param shipperCode
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void provideShipperCode1(String shipperCode) throws InterruptedException, AWTException {
		Thread.sleep(8000);
		enterValueInTextbox(sheetName, "inbx_shipperCode;xpath", data(shipperCode), "ShipperCode", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
	}
	
	
	/**
	 * Description... Perform AsIsExecute Remove MDC
	 * @throws Exception
	 */
	public void asIsExecuteRemoveMDC() throws Exception {
		screenName = "Capture AWB";
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		switchToWindow("storeParent");

		String sccTxt = customFunction.getAttributeWebElement(sheetName, "inbx_SCC;xpath", "SCC", "value", screenName);

		if (sccTxt.contains("MDC,"))
			sccTxt = sccTxt.replace("MDC,", "");
		else if (sccTxt.contains(",MDC"))
			sccTxt = sccTxt.replace(",MDC", "");
		if (sccTxt.contains(",ECC"))
			sccTxt = sccTxt.replace("ECC,", "");
		else if (sccTxt.contains(",ECC"))
			sccTxt = sccTxt.replace(",ECC", "");
		customFunction.enterValueInTextbox(sheetName, "inbx_SCC;xpath", sccTxt, "SCC", screenName);

		clickWebElement(sheetName, "btn_AsIsExecute;xpath", "AsIsExecute Button", screenName);
		waitForSync(30);
		switchToFrame("default");

		try {

			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				waitForSync(10);
			}
		} catch (Exception e) {
		}

		waitForSync(3);
		switchToFrame("contentFrame", "OPR026");
		waitForSync(1);
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_executed;xpath"))).getText();
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute", "Capture AWB");
		waitForSync(2);

	}
	
	
	/**
	 * Description... VerifySCC code
	 * @param SCCUpdate
	 * @throws Exception
	 */
	public void verifySCCAdd(String SCCUpdate)throws Exception{
		ele = findDynamicXpathElement("inbx_SCC;xpath", sheetName, "SCC Codes", screenName);
		String actText = ele.getAttribute("value");
		String expText=data(SCCUpdate);
		if(actText.contains(expText)){
			verifyScreenText(sheetName, expText, actText, "Verify SCC codes", "Capture AWB");


		}
		else{
			verifyScreenText(sheetName, expText, actText, "Verify SCC codes failed", "Capture AWB");

		}

	}
	/**
	 * Description... VerifySCC code
	 * @param SCCUpdate
	 * @throws Exception
	 */
	public void verifySCCAdd(String... SCCUpdate)throws Exception{
		ele = findDynamicXpathElement("inbx_SCC;xpath", sheetName, "SCC Codes", screenName);
		String actText = ele.getAttribute("value");

		for(String expText:SCCUpdate)
		{

			if(actText.contains(expText)){
				verifyScreenText(sheetName, expText, actText, "Verify SCC codes", "Capture AWB");


			}
			else{
				verifyScreenText(sheetName, expText, actText, "Verify SCC codes failed", "Capture AWB");

			}
		}

	}
	
	/**
	 * Description... Verify Consignee details
	 * 
	 * @param txtList
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	/*public void verifyConsigneeDetails(String txtList) throws FileNotFoundException, IOException {
	try {
		// read data for verification from the excel
		String expListTxt[] = txtList.split(",");
		String subjectList[] = new String[expListTxt.length];
		for (int i = 0; i < expListTxt.length; i++)
			subjectList[i] = expListTxt[i].split("=")[0];

		// collect values from the UI
		List<WebElement> listShipperDetails = returnListOfElements(sheetName, "txt_consignee;xpath");
		listShipperDetails.remove(1);
		Map txtListShipperDetails = returnAttributeListOfElements(listShipperDetails, subjectList, "value");

		Map<String, String> shipperMap = new HashMap();
		Object[][] obj = new Object[1][1];
		for (int i = 0; i < expListTxt.length; i++) {
			String key = expListTxt[i].split("=")[0];
			try {

				String value = expListTxt[i].split("=")[1];
				shipperMap.put(key, value);

			} catch (Exception e) {
				shipperMap.remove(key);
				txtListShipperDetails.remove(key);

			}
		}
		// compare the keys
		boolean flag = shipperMap.keySet().equals(txtListShipperDetails.keySet());
		if (flag == true)
			System.out.println("Keys are same in UI and data sheet");
		else
			writeExtent("Fail", "Keys from UI and from Test Data not matching");

		// compare the values
		Map<String, Boolean> result = verifyEqualKeyValues(shipperMap, txtListShipperDetails);
		for (Entry<String, Boolean> entry : result.entrySet()) {
			if (entry.getValue() == false) {
				verifyValueOnPage(true, false, "1. Process XFWB \n2. Verify Consignee Details", screenName,
						entry.getKey());
				System.out.println("Key = " + entry.getKey());

			} else
				verifyValueOnPage(true, true, "1. Process XFWB \n2. Verify Consignee Details", screenName,
						entry.getKey() + " for Consignee");

		}

	} catch (Exception e) {
		writeExtent("Fail", "Failed in Consignee Values verification");
	}

}
	 */
	
	/**
	 * Description... Verify Handling Info
	 * @throws InterruptedException
	 */
	public void verifyHandlingInfo() throws InterruptedException {
		String actHandlingInfo = getAttributeWebElement(sheetName, "txt_handlingInfo;name", "Handling Info", "value",
				screenName);
		verifyValueOnPage(actHandlingInfo, data("HandlingInfo"), "1. Verify Handling Information", screenName,
				"Handling Info");
	}

	
	/**
	 * Description... Verify Shipment Description 
	 * @throws InterruptedException
	 */
	public void verifyShipDescription() throws InterruptedException {
		String actShipmentDescription = getAttributeWebElement(sheetName, "txt_shipmentDescription;name",
				"Shipment Description", "value", screenName);
		verifyValueOnPage(actShipmentDescription, data("ShipmentDescription"), "1. Verify Shipment Description",
				screenName, "Shipment Description");
	}
	
	
	/**
	 * Description... Verify Accounting Info
	 * @throws InterruptedException
	 */
	public void verifyAccountingInfo() throws InterruptedException {
	}
	
	
	/**
	 * Description... Verify Auto Compute Tax
	 * @param txtList
	 * @throws InterruptedException
	 */
	/*public void verifyAutoComputeTax(String txtList) throws InterruptedException {
	try {

		txtList = "WeightChargePrepaid=62,843~ValuationChargePrepaid=21,542~TaxPrepaid=785,211~OtherChargesDueAgentPrepaid=0~OtherChargesDueCarrierPrepaid=51,417.2~TotalPrepaid=921,013.2~WeightChargeCollect=0~ValuationChargeCollect=0~TaxCollect=0~OtherChargesDueCarrierCollect=0~OtherChargesDueCarrierCollect=12,854.3~TotalCollect=12,854.3";
		String expListTxt[] = txtList.split("~");

		String subjectList[] = new String[expListTxt.length];
		for (int i = 0; i < expListTxt.length; i++)
			subjectList[i] = expListTxt[i].split("=")[0];

		// collect values from the UI
		List<WebElement> listShipperDetails = returnListOfElements(sheetName, "txt_autoComputeTax;xpath");
		listShipperDetails.remove(0);
		listShipperDetails.remove(1);
		listShipperDetails.remove(2);
		listShipperDetails.remove(3);
		listShipperDetails.remove(4);
		listShipperDetails.remove(5);
		listShipperDetails.remove(6);
		listShipperDetails.remove(7);
		listShipperDetails.remove(8);
		listShipperDetails.remove(9);
		listShipperDetails.remove(10);
		listShipperDetails.remove(11);
		Map txtListShipperDetails = returnAttributeListOfElements(listShipperDetails, subjectList, "value");

		Map<String, String> shipperMap = new HashMap();
		Object[][] obj = new Object[1][1];
		for (int i = 0; i < expListTxt.length; i++) {
			String key = expListTxt[i].split("=")[0];
			try {

				String value = expListTxt[i].split("=")[1];
				shipperMap.put(key, value);

			} catch (Exception e) {
				shipperMap.remove(key);
				txtListShipperDetails.remove(key);

			}
		}
		// compare the keys
		boolean flag = shipperMap.keySet().equals(txtListShipperDetails.keySet());
		if (flag == true)
			System.out.println("Keys are same in UI and data sheet");
		else
			writeExtent("Fail", "Keys from UI and from Test Data not matching");

		// compare the values
		Map<String, Boolean> result = verifyEqualKeyValues(shipperMap, txtListShipperDetails);
		for (Entry<String, Boolean> entry : result.entrySet()) {
			if (entry.getValue() == false) {
				verifyValueOnPage(true, false, "1. Process XFWB \n2. Verify Shipper Details", screenName,
						entry.getKey());
				System.out.println("Key = " + entry.getKey());

			} else
				verifyValueOnPage(true, true, "1. Process XFWB \n2. Verify Shipper Details", screenName,
						entry.getKey() + " for Shipper");

		}

	} catch (Exception e) {
		writeExtent("Fail", "Failed in Auto Compute Tax verification");
	}

}*/

	/**
	 * Description... Verify Shipper details
	 * 
	 * @param txtList
	 * @throws FileNotFoundException
	 * @throws IOException
	 *//*
public void verifyXFWBDetailsSoap(String txtList) throws FileNotFoundException, IOException {
	try {
		// read data for verification from the excel
		String expListTxt[] = txtList.split(",");

		Properties prop = new Properties();
		Map<String, String> txtListDetails = new HashMap<String, String>();
		try {
			String s2 = System.getProperty("user.dir");
			String path = s2 + cxmlPropPath;
			FileInputStream inputStream = new FileInputStream(path);
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Some issue finding or loading file....!!! " + e.getMessage());

		}
		for (final Entry<Object, Object> entry : prop.entrySet()) {
			txtListDetails.put((String) entry.getKey(), (String) entry.getValue());
		}

		txtListDetails.remove("getShipmentResponse");
		txtListDetails.remove("SoapTaskStatus");

		Map<String, String> txtListDetailsReqdMap = new HashMap();

		Map<String, String> shipperMap = new HashMap();
		Object[][] obj = new Object[1][1];
		for (int i = 0; i < expListTxt.length; i++) {
			String key = expListTxt[i].split("=")[0];
			try {

				String value = expListTxt[i].split("=")[1];
				shipperMap.put(key, value.replace("\\", ""));
				txtListDetailsReqdMap.put(key, txtListDetails.get(key).toString().replace("\\", ""));

			} catch (Exception e) {
				shipperMap.remove(key);
				txtListDetails.remove(key);

			}
		}
		// compare the keys
		boolean flag = shipperMap.keySet().equals(txtListDetailsReqdMap.keySet());
		if (!flag)
			writeExtent("Fail", "Keys from UI and from Test Data not matching");

		// compare the values
		Map<String, Boolean> result = verifyEqualKeyValues(shipperMap, txtListDetailsReqdMap);
		for (Entry<String, Boolean> entry : result.entrySet()) {
			if (entry.getValue() == false) {
				verifyValueOnPage(true, false, "1. Process XFWB \n2. Verify Details", screenName,
						entry.getKey() + " for XFWB Verification");
				System.out.println("Failed for Key = " + entry.getKey());

			} else
				verifyValueOnPage(true, true, "1. Process XFWB \n2. Verify Details", screenName,
						entry.getKey() + " for XFWB Verification");
			System.out.println("Passed for Key = " + entry.getKey());

		}

	} catch (Exception e) {
		writeExtent("Fail", "Failed in Capture AWB Screen XFWB Verification");
		Assert.assertFalse(true, "Failed in Capture AWB Screen XFWB Verification");
	}

}*/
	/**
	 * Description... Security And Screening
	 * @throws Exception
	 */
	public void securityAndScreening() throws Exception {
		screenName = "Security and Screening Pop up";
		Thread.sleep(2000);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_SecurityAndScreening;id", "Sec&Screening Button", screenName);
		Thread.sleep(2000);
		waitTillSpinnerDisappear();
		switchToFrame("default");
		Thread.sleep(4000);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		driver.switchTo().frame("iCargoContentFrameOPR026");
		driver.switchTo().frame("popupContainerFrame");

		checkIfUnchecked(sheetName, "chk_dataRcvd;name", "Data Received Check Box", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

	}
	
	
	/**
	 * Description... Verify Special Service Request
	 * @throws InterruptedException
	 */
	public void verifySpecialServiceRequest() throws InterruptedException {
		String actSpecialServiceRequest = getAttributeWebElement(sheetName, "txt_selectCustomsinfo;name",
				"Special Service Request Text", "value", screenName);
		verifyValueOnPage(actSpecialServiceRequest, data("SpecialServiceRequest"), "1. Verify Special Service Request",
				screenName, "Special Service Request");

	}
	
	
	/**
	 * Description... Verify Shipper StreetName
	 * @throws InterruptedException
	 */
	public void verifyShipperStreetName() throws InterruptedException {

		String actAddress = getAttributeWebElement(sheetName, "inbx_ShipperAddress;xpath", "Shipper Address", "value",
				screenName);
		verifyValueOnPage(actAddress, data("ShipperStreetAddress"), "Verify Shipper Address", screenName,
				"Shipper Address");
	}
	
	
	/**
	 * Description... Verify Shipper Details Table Soap
	 * @throws Exception
	 */
	/*public void verifyShipperDetailsTableSoap() throws Exception {
	verifyXFWBDetailsSoap(data("ShipperDetailsTable"));
}
	 *//**
	 * Description... Verify Consignee Details Table Soap
	 * @throws Exception
	 *//*
public void verifyConsigneeDetailsTableSoap() throws Exception {
	verifyXFWBDetailsSoap(data("ConsigneeDetailsTable"));
}
	  *//**
	  * Description... Verify Handling Information Soap
	  * @throws Exception
	  *//*
public void verifyHandlingInformationSoap() throws Exception {
	verifyXFWBDetailsSoap(data("HandlingInformation"));
}
	   *//**
	   * Description... Verify Shipment Description Soap
	   * @throws Exception
	   *//*
public void verifyShipmentDescriptionSoap() throws Exception {
	verifyXFWBDetailsSoap(data("ShipmentDescription"));
}
	    *//**
	    * Description... Verify Rating Details Soap
	    * @throws Exception
	    *//*
public void verifyRatingDetailsSoap() throws Exception {
	verifyXFWBDetailsSoap(data("RatingDetails"));
}
	     *//**
	     * Description... Verify Shipment Details Pieces Weight Volume Soap
	     * @throws Exception
	     *//*
public void verifyShipmentDetailsPiecesWeightVolumeSoap() throws Exception {
	verifyXFWBDetailsSoap(data("ShipmentDetailsPiecesWeightVolume"));
}
	      *//**
	      * Description... Verify Dimnesion Details Soap
	      * @throws Exception
	      *//*
public void verifyDimnesionDetailsSoap() throws Exception {
	verifyXFWBDetailsSoap(data("DimnesionDetails"));
}
	       *//**
	       * Description... Verify Rating ULD Details Soap
	       * @throws Exception
	       *//*
public void verifyRatingULDDetailsSoap() throws Exception {
	verifyXFWBDetailsSoap(data("RatingULDDetails"));
}
	        *//**
	        * Description... Verify Additional Information Soap
	        * @throws Exception
	        *//*
public void verifyAdditionalInformationSoap() throws Exception {
	verifyXFWBDetailsSoap(data("AdditionalInformation"));
}
	         *//**
	         * Description... Verify Auto Compute Tax Charges And Accounting Soap
	         * @throws Exception
	         *//*
public void verifyAutoComputeTaxChargesAndAccountingSoap() throws Exception {
	verifyXFWBDetailsSoap(data("AutoComputeTaxChargesAndAccounting"));
}
	          *//**
	          * Description... Verify Accounting Info Soap
	          * @throws Exception
	          *//*
public void verifyAccountingInfoSoap() throws Exception {
	verifyXFWBDetailsSoap(data("AccountingInfo"));
}
	           *//**
	           * Description... Verify Charge Details Soap
	           * @throws Exception
	           *//*
public void verifyChargeDetailsSoap() throws Exception {
	verifyXFWBDetailsSoap(data("ChargeDetails"));
}
	            *//**
	            * Description... Verify Booking Details Soap
	            * @throws Exception
	            *//*
public void verifyBookingDetailsSoap() throws Exception {
	String BookingDetailsSegment1 = "BookingDetailsSegment1=" + data("Origin") + data("Transit")
			+ data("fullFlightNo1");
	String BookingDetailsSegment2 = "BookingDetailsSegment2=" + data("Transit") + data("Destination")
			+ data("fullFlightNo2");
	verifyXFWBDetailsSoap(BookingDetailsSegment1 + "," + BookingDetailsSegment2);
}
	             *//**
	             * Description... Verify Agent Details Soap
	             * @throws Exception
	             *//*
public void verifyAgentDetailsSoap() throws Exception {
	verifyXFWBDetailsSoap(data("AgentDetails"));

}
	              *//**
	              * Description... Verify Routing Details Soap
	              * @throws Exception
	              *//*
public void verifyRoutingDetailsSoap() throws Exception {
	verifyXFWBDetailsSoap("RoutingDetails=" + data("Origin") + "_" + data("Destination") + "_" + data("Transit")
			+ "_" + data("Destination"));

}
	               *//**
	               * Description... Verify SCC Soap
	               * @throws Exception
	               *//*
public void verifySCCSoap() throws Exception {
	verifyXFWBDetailsSoap("SCC=SPX");
}*/
	/**
	 * Description... Verify XFWB Details
	 * @throws Exception
	 */
	/*public void verifyXFWBDetails() throws Exception {

	verifyShipperDetailsTableSoap();
	verifyConsigneeDetailsTableSoap();
	verifyHandlingInformationSoap();
	verifyShipmentDescriptionSoap();
	verifyRatingDetailsSoap();
	verifyShipmentDetailsPiecesWeightVolumeSoap();
	verifyDimnesionDetailsSoap();
	verifyRatingULDDetailsSoap();

	verifyAutoComputeTaxChargesAndAccountingSoap();


	verifyBookingDetailsSoap();
	verifyAgentDetailsSoap();
	verifyRoutingDetailsSoap();

}*/
	/**
	 * Description... AsIsExecute CXML
	 * @throws Exception
	 */
	public void asIsExecuteCXML() throws Exception {
		screenName = "Capture AWB";
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_AsIsExecute;xpath", "AsIsExecute Button", screenName);
		waitForSync(30);
		switchToFrame("default");

		try {

			while (driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
				Thread.sleep(15000);
			}
		} catch (Exception e) {
		}

		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR026");
		Thread.sleep(1000);
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_executed;xpath"))).getText();
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute", "Capture AWB");
		Thread.sleep(2000);

	}
	
	
	/**
	 * Description... Click Send FZB Button
	 * @throws Exception
	 */
	public void sendFZB() throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_sendFZB;xpath", "Send FZB Button", screenName);
		waitForSync(3);
	}
	
	
	/**
	 * Description... Click HAWB Dropdown
	 * @param HAWB
	 * @throws Exception
	 */
	public void clickHAWBDropdown(String HAWB) throws Exception {
		screenName = "CaptureHAWB";
		clickWebElement(sheetName, "lst_HAWB;xpath", "HAWB Dropdown", screenName);
		waitForSync(3);
		try {
			driver.findElement(By.name("houses")).sendKeys(HAWB);
			waitForSync(2);
			driver.findElement(By.name("houses")).sendKeys(Keys.ARROW_DOWN);
			waitForSync(2);
			driver.findElement(By.name("houses")).sendKeys(Keys.ARROW_DOWN);
			waitForSync(2);
			driver.findElement(By.name("houses")).sendKeys(Keys.TAB);
		} catch (Exception e) {
			writeExtent("Fail", "Could not select Option from HAWB Dropdown");
			Assert.assertFalse(true, "Could not select Option from HAWB Dropdown");
		}
		waitForSync(3);
	}
	
	
	/**
	 * Description... Verify Console Check Box is checked
	 */
	public void verifyConsoleCheckBoxChkd() {
		String checked = getAttributeUsingJavascript(sheetName, "chk_Consol;name", "Consol checkbox", screenName,
				"checked");
		if (checked.equals("true"))
			verifyValueOnPage(true, true, "Verify Consol check box is checked ", screenName,
					"Consol checkbox is checked ");
		else
			verifyValueOnPage(true, false, "Verify Consol check box is checked ", screenName,
					"Consol checkbox is checked ");
	}
	
	
	/**
	 * Description... Click HAWB Button
	 * @throws InterruptedException
	 */
	public void clickHAWBButton() throws InterruptedException {
		  try {
				clickWebElement(sheetName, "btn_HAWB;name", "HAWB Button",
				  screenName);
			} catch (IOException e) {
				
				e.printStackTrace();
			} Thread.sleep(3000);
			 
			
			  handleAlert("Accept",screenName);
			  waitForSync(5);
			  switchToFrame("contentFrame","OPR026");

	}
	
	
	/**
	 * Description... Enter Execution Date
	 * @param ExecutionDate
	 * @throws InterruptedException
	 */
	public void enterExecutionDate(String ExecutionDate) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_executedDate;xpath", ExecutionDate, "Execution Date", screenName);

	}
	
	
	/**
	 * Description... Verify HAWB Not Saved
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyHAWBNotSaved() throws InterruptedException, IOException {
		String houseValue = getElementText(sheetName, "txt_houses;xpath", "House No", screenName);
		verifyValueOnPageContains(houseValue, data("HAWB"), "Verify no HAWB No is saved with the AWB", screenName,
				"No HAWB No is saved with the AWB");

	}
	
	
	/**
	 * Description... Verify HAWB Saved
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyHAWBSaved() throws InterruptedException, IOException {
		String houseValue = getElementText(sheetName, "txt_houses;xpath", "House No", screenName);
		verifyValueOnPageContains(houseValue, data("HAWB"), "Verify HAWB No is saved with the AWB", screenName,
				"HAWB No is saved with the AWB");

	}
	
	
	/**
	 * Description... Verify Shipper Address
	 * @param ShipperDetailsTable
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyShipperAddress(String ShipperDetailsTable) throws InterruptedException, IOException {
		String actShipperAddress = getAttributeWebElement(sheetName, "txt_shipperAddress;name", "Shipper Address",
				"value", screenName);
		verifyValueOnPageContains(actShipperAddress, data(ShipperDetailsTable), "1. Verify Shipper Address",
				"Shipper Address", screenName);
	}
	
	
	/**
	 * Description... Verify Consignee Address
	 * @param ConsigneeDetailsTable
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyConsigneeAddress(String ConsigneeDetailsTable) throws InterruptedException, IOException {
		String actConsigneeAddress = getAttributeWebElement(sheetName, "txt_consigneeAddress;name", "Consignee Address",
				"value", screenName);
		verifyValueOnPageContains(actConsigneeAddress, data(ConsigneeDetailsTable), "1. Verify Consignee Address",
				"Consignee Address", screenName);

	}
	
	
	/**
	 * Description... Verify SCC Removed
	 */
	public void verifySCCRemoved() {
		String actSCC = getAttributeWebElement(sheetName, "inbx_SCC;xpath", "SCC", "value", screenName);
		if (actSCC.equals(""))
			verifyValueOnPage(true, true, "Verify SCC is removed", screenName, "SCC is removed");
		else
			verifyValueOnPage(true, false, "Verify SCC is removed", screenName, "SCC is removed");

	}
	
	/**
	 * Description... Edit Shipper Name
	 * @param shipperName
	 * @throws InterruptedException
	 */
	public void editShipperName(String shipperName) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_shiperName;id", data(shipperName), "Shipper Name", screenName);

	}
	
	/**
	 * Description... Enter Consignee State
	 * @param consigneeState
	 * @throws InterruptedException
	 */
	public void enterConsigneeState(String consigneeState) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_consneeState;xpath", data(consigneeState), "Consignee State", screenName);

	}
	
	/**
	 * Description... Verify Signatory Consignor Authentication
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void verifySignatoryConsignorAuthentication()
			throws FileNotFoundException, IOException, InterruptedException {
		String expSignatoryConsignorAuthentication = getAttributeWebElement(sheetName,
				"inbx_shipmentCertification;name", "Signatory Consignor Authentication", "value", screenName);

		verifyValueOnPageContains(data("SignatoryConsignorAuthentication"), expSignatoryConsignorAuthentication,
				"Verify Signatory Consignor Authentication", screenName, "Signatory Consignor Authentication");

	}
	
	
	/**
	 * Description... Verify ADC Origin
	 * @param Origin
	 * @throws InterruptedException
	 */
	public void verifyADCOrigin(String Origin) throws InterruptedException {
		By element = getElement(sheetName, "inbx_Origin;xpath");
		String actText = driver.findElement(element).getAttribute("value").toLowerCase();
		String expText = Origin.toLowerCase();
		verifyScreenText(sheetName, expText, actText, "Origin", "Capture AWB");
	}
	
	
	/**
	 * Description... Verify Data Saved When Listed With House
	 * @param XFZBOrigin
	 * @param Shipper
	 * @param Consignee
	 * @param ADCPieces
	 * @param ADCWeight
	 * @throws Exception
	 */
	public void verifyDataSavedWhenListedWithHouse(String XFZBOrigin, String Shipper, String Consignee,
			String ADCPieces, String ADCWeight) throws Exception {

		verifyADCOrigin(XFZBOrigin);
		verifyShipperName(Shipper);
		verifyConsigneeName(Consignee);
		verifyPiecesWeight(ADCPieces, ADCWeight);

	}
	
	
	/**
	 * Description... Verify DV For Carriage
	 * @throws InterruptedException
	 */
	public void verifyDVForCarriage() throws InterruptedException {
		String actDVForCarriage = getAttributeWebElement(sheetName, "txt_dvForCarriage;name",
				"DV For Carriage Description", "value", screenName);
		verifyValueOnPage(actDVForCarriage, data("DVForCarriage"), "1. Verify  DV For Carriage", screenName,
				"DV For Carriage");
	}
	
	
	/**
	 * Description... Edit Flight Details
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void editFlightDetails() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "img_editReqFlight;xpath", "Edit Flight Details", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "txt_fltNum0;xpath", data("UpdatedFlight1"), "Flight Number 1", screenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "txt_fltNum1;xpath", data("UpdatedFlight2"), "Flight Number 2", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_ReqFltOK;name", "Ok button", screenName);

	}
	
	
	/**
	 * Description... Verify Charges And Acoounting ULD Details
	 * @throws Exception
	 */
	public void verifyChargesAndAcoountingULDDetails() throws Exception {
		waitForSync(5);
		clickChargesAcc();
		switchToWindow("storeParent");
		waitForSync(2);
		clickWebElement(sheetName, "img_uldLOVCharges;xpath", "ULD Icon", screenName);
		waitForSync(8);
		switchToWindow("child");
		waitForSync(2);
		By element = getElement(sheetName, "inbx_uldNum1;xpath");
		String uldNum1 = driver.findElement(element).getAttribute("value");
		By element3 = getElement(sheetName, "inbx_uldSlacPieces1;xpath");
		String uldSlacPieces1 = driver.findElement(element3).getAttribute("value");
		clickWebElement(sheetName, "btn_CloseLOV;name", "Dimension LOV", screenName);
		waitForSync(2);
		switchToWindow("getParent");
		verifyValueOnPage(uldNum1, data("ULDNumber"), "1st ULD name verification", sheetName,
				"1st ULD name verification");
		verifyValueOnPage(uldSlacPieces1, data("ULDPiece"), "ULD1 pieces verification", sheetName,
				"ULD1 pieces verification");

	}
	
	
	/**
	 * Description... Verify Data Saved When Listed With House
	 * @throws Exception
	 */
	public void verifyDataSavedWhenListedWithHouse() throws Exception {

		verifyADCOrigin(data("XFZBOrigin"));
		verifyShipperName(data("Shipper"));
		verifyConsigneeName(("Consignee"));
		verifyPiecesWeight("ADCPieces", "ADCWeight");

	}
	
	
	/**
	 * Description... Verify Shipper Account Number
	 * @param ShipperAccNo
	 * @throws InterruptedException
	 */
	public void verifyShipperAccNo(String ShipperAccNo) throws InterruptedException {
		String actShipperNo = getAttributeWebElement(sheetName, "inbx_shipperAccountNumber;name",
				"Shipper Account Number", "value", screenName);

		verifyValueOnPage(actShipperNo, data(ShipperAccNo), "Shipper Account Number", sheetName,
				"Shipper Account Number");

	}
	
	/**
	 * Description... Verify Consignee Account Number
	 * @param ConsigneeAccNo
	 * @throws InterruptedException
	 */
	public void verifyConsigneeAccNo(String ConsigneeAccNo) throws InterruptedException {
		String actShipperNo = getAttributeWebElement(sheetName, "inbx_consigneeAccountNumber;name",
				"Consignee Account Number", "value", screenName);

		verifyValueOnPage(actShipperNo, data(ConsigneeAccNo), "Consignee Account Number", sheetName,
				"Consignee Account Number");
	}
	
	/**
	 * Description... Verify Agent Code
	 * @param AgentCode
	 * @throws InterruptedException
	 */
	public void verifyAgentCode(String AgentCode) throws InterruptedException {
		String actShipperNo = getAttributeWebElement(sheetName, "inbx_AgentCode;xpath", "Agent Code", "value",
				screenName);

		verifyValueOnPage(actShipperNo, data(AgentCode), "Agent Code", sheetName, "Agent Code");

	}
	
	/**
	 * Description... Verify Shipper Code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyShipperCode() throws InterruptedException, AWTException {
		String actShipperNo = getAttributeWebElement(sheetName, "inbx_shipperCode;xpath", "Shipper Code", "value",
				screenName);

		verifyValueOnPage(actShipperNo, data("ShipperCode"), "Shipper Code", sheetName, "Shipper Code");
	}
	
	/**
	 * Description... Verify Consignee Code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyConsigneeCode() throws InterruptedException, AWTException {
		String actConsigneeNo = getAttributeWebElement(sheetName, "inbx_consigneeCode;xpath", "Consignee Code", "value",
				screenName);
		verifyValueOnPage(actConsigneeNo, data("ConsigneeCode"), "Consignee Code", sheetName, "Consignee Code");
	}
	
	/**
	 * Description... Verify Associated Party Block
	 */
	public void verifyAssociatedPartyBlock() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Description... Update notify Details Address
	 * @param NotificationAdd
	 * @throws Exception
	 */
	public void updateNfyDetailsAddress(String NotificationAdd) throws Exception {
		waitForSync(3);

		javaScriptToclickElement(sheetName, "btn_more;xpath", "More Button", screenName);

		waitForSync(4);
		javaScriptToclickElement(sheetName, "btn_notify;xpath", "Notify Button", screenName);

		waitForSync(2);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");

		enterValueIfDisplayed(sheetName, "inbx_ntyAdd;xpath", data(NotificationAdd), "Notification Address 1",
				screenName);
		clickWebElement(sheetName, "btn_ntySave;xpath", "Notify Save Button", screenName);
		waitForSync(2);

		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");

	}
	
	
	/**
	 * Description... update Notification Details Name
	 * @param NotificationName
	 * @throws Exception
	 */
	public void updateNfyDetailsName(String NotificationName) throws Exception {
		waitForSync(3);

		javaScriptToclickElement(sheetName, "btn_more;xpath", "More Button", screenName);

		waitForSync(4);
		javaScriptToclickElement(sheetName, "btn_notify;xpath", "Notify Button", screenName);

		waitForSync(2);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");

		enterValueIfDisplayed(sheetName, "inbx_ntyName;xpath", data(NotificationName), "Notification Name", screenName);
		clickWebElement(sheetName, "btn_ntySave;xpath", "Notify Save Button", screenName);
		waitForSync(2);

		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR026");

	}
	
	
	/**
	 * Description... Verify Notification Details
	 * @param notifyCode
	 * @param notifyName
	 * @param ntyAddress
	 * @param ntyCity
	 * @param ntyCountry
	 * @param ntyPostalCode
	 * @param ntyPhoneNo
	 * @param ntyEmail
	 * @throws Exception
	 */
	public void verifyNotificationDetails(String notifyCode, String notifyName, String ntyAddress, String ntyCity,
			String ntyCountry,String ntyPostalCode,String ntyPhoneNo,String ntyEmail) throws Exception {
		try {
			waitForSync(3);
			String actText = "", expText = "";
			javaScriptToclickElement(sheetName, "btn_more;xpath", "More Button", screenName);

			waitForSync(4);
			javaScriptToclickElement(sheetName, "btn_notify;xpath", "Notify Button", screenName);

			waitForSync(2);
			switchToWindow("storeParent");
			waitForSync(2);
			switchToWindow("child");

			// Notify Code verification
			actText = getAttributeWebElement(sheetName, "inbx_ntyCode;xpath", "Notify Code", "value", screenName);
			expText = data(notifyCode);
			verifyScreenText(sheetName, expText, actText, "Notify Code", "Notify Details");

			// Notify Name verification
			actText = getAttributeWebElement(sheetName, "inbx_ntyName;xpath", "Notify Name", "value", screenName);
			expText = data(notifyName);
			verifyScreenText(sheetName, expText, actText, "Notify Name", "Notify Details");

			// Notify Address verification
			actText = getAttributeWebElement(sheetName, "inbx_ntyAdd;xpath", "Notify Address", "value", screenName);
			expText = data(ntyAddress);
			verifyScreenText(sheetName, expText, actText, "Address1", "Notify Details");

			// Notify City verification
			actText = getAttributeWebElement(sheetName, "inbx_ntyCty;xpath", "Notify City", "value", screenName);
			expText = data(ntyCity);
			verifyScreenText(sheetName, expText, actText, "City", "Notify Details");

			// Notify Country verification
			actText = getAttributeWebElement(sheetName, "inbx_ntyCountry;xpath", "Notify Country", "value", screenName);
			expText = data(ntyCountry);
			verifyScreenText(sheetName, expText, actText, "Country", "Notify Details");

			// Notify Zip Postal verification
			actText = getAttributeWebElement(sheetName, "inbx_notifyPostalCode;name", "Notify Postal Code", "value",
					screenName);
			expText = data(ntyPostalCode);
			verifyScreenText(sheetName, expText, actText, "Postal Code", "Notify Details");

			// Notify Telephone verification
			actText = getAttributeWebElement(sheetName, "inbx_notifyTelephoneNumber;name", "Notify Telephone Number",
					"value", screenName);
			expText = data(ntyPhoneNo);
			verifyScreenText(sheetName, expText, actText, "Telephone Number", "Notify Details");

			// Notify Email verification
			actText = getAttributeWebElement(sheetName, "inbx_notifyEmailId;name", "Notify Email Id", "value",
					screenName);
			expText = data(ntyEmail);
			verifyScreenText(sheetName, expText, actText, "Email Id", "Notify Details");

			clickWebElement(sheetName, "btn_notifyClose;xpath", "Notify Close", "Notify Details");
			waitForSync(2);

			switchToWindow("getParent");
			switchToDefaultAndContentFrame("OPR026");
		} catch (Exception e) {
			System.out.println("Failed to verify Notification Details On " + screenName + " Page");
			writeExtent("Fail", "Failed to verify Notification Details On " + screenName + " Page");
			Assert.assertFalse(true, "Failed to verify Notification Details On " + screenName + " Page");
		}

	}
	
	
	/**
	 * Description... Verify Invalid SCC Removed
	 * @param invalidSCC
	 */
	public void verifyInvalidSCCRemoved(String invalidSCC) {
		String actSCC = getAttributeWebElement(sheetName, "inbx_SCC;xpath", "SCC", "value", screenName);
		if (!actSCC.contains(invalidSCC))
			verifyValueOnPage(true, true, "Verify Invalid SCC is removed", screenName, "SCC is removed");
		else
			verifyValueOnPage(true, false, "Verify Invalid SCC is removed", screenName, "SCC is removed");

	}
	/**
	 * Description... Verify valid SCC Present
	 * @param validSCC
	 */
	public void verifyvalidSCCPresent(String validSCC) {
		String actSCC = getAttributeWebElement(sheetName, "inbx_SCC;xpath", "SCC", "value", screenName);
		if (!actSCC.contains(validSCC))
			verifyValueOnPage(true, true, "Verify Valid SCC is not removed", screenName, "Valid SCC is not removed");
		else
			verifyValueOnPage(true, false, "Verify Valid SCC is not removed", screenName, "Valid SCC is not removed");

	}
	/**
	 * Description... Verify Consignee State
	 * @param ConsigneeState
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyConsigneeState(String ConsigneeState) throws InterruptedException, IOException {
		String actConsigneeState = getAttributeWebElement(sheetName, "inbx_consneeState;xpath", "Consignee State",
				"value", screenName);
		verifyValueOnPageContains(actConsigneeState, data(ConsigneeState), "1. Verify Consignee State",
				"Consignee State", screenName);

	}
	/**
	 * Description... Verify Shipper City
	 * @param ShipperCity
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyShipperCity(String ShipperCity) throws InterruptedException, AWTException {
		String actShipperCiy = getAttributeWebElement(sheetName, "txt_shipperCity;name", "Shipper City", "value", screenName);	

		verifyValueOnPage(actShipperCiy, data(ShipperCity), "Shipper City", sheetName,
				"Shipper City");
	}
	/**
	 * Description... Verify Consignee City
	 * @param ConsigneeCity
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyConsigneeCity(String ConsigneeCity) throws InterruptedException, AWTException {
		String actConsigneeCiy = getAttributeWebElement(sheetName, "txt_consigneeCity;name", "Consignee City", "value", screenName);	

		verifyValueOnPage(actConsigneeCiy, data(ConsigneeCity), "Consignee City", sheetName,
				"Consignee City");
	}
	/**
	 * Description... Enter Shipper State
	 * @param shipperState
	 * @throws InterruptedException
	 */
	public void enterShipperState(String shipperState) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_shipperState;name", data(shipperState), "Shipper State", screenName);

	}
	/**
	 * Description... Verify Booking Details1 Soap
	 * @throws Exception
	 */
	/*public void verifyBookingDetails1Soap() throws Exception {
    String BookingDetailsSegment1 = "BookingDetailsSegment1=" + data("Origin") + data("Destination")
                 + data("fullFlightNo1");

    verifyXFWBDetailsSoap(BookingDetailsSegment1);
}*/
	/**
	 * Description... Comapare the maps
	 * @param first
	 * @param second
	 * @return
	 */
	/*private Map<String, Boolean> verifyEqualKeyValues(Map<String, String> first, Map<String, String> second) {
	return first.entrySet().stream()
			.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().equals(second.get(e.getKey()))));
}*/
	/**
	 * Description... Verify Booking Details Soap Single Segment
	 * @throws Exception
	 */
	/*public void verifyBookingDetailsSoapSingleSegment() throws Exception {

	String BookingDetailsSegment1 = "BookingDetailsSegment1=" + data("Origin") + data("Destination")
			+ data("fullFlightNo1");

	verifyXFWBDetailsSoap(BookingDetailsSegment1);
}*/
	/**
	 * Description... Verify Multiple Custom Information
	 * @param custmInfo
	 * @param Count
	 * @throws Exception
	 */
	public void verifyMultipleCustmInfo(String custmInfo,int Count) throws Exception {

		String split[] = data("CustomIInformationID").split(",");
		int i=1;
		for(String s : split){

			String xpath1 = xls_Read.getCellValue(sheetName,
					"inbx_customsinfoid;id");
			String xpath2=xpath1+i+"]";
			WebElement ele1 = findDynamicXpathElement(xpath2,"OPR026 Additional info tab", "Capture AWB");
			String actcustmInfo = getAttributeWebElement(ele1, "OPR026 Additional info tab", "value", "Capture AWB");

			verifyValueOnPage(
					actcustmInfo.trim().replace(" ", ""),
					s.trim().replace(" ", ""),
					"1. List awb.\n 3. Verify Customs Information ID",
					"Capture AWB", "Customs Information ID");
			i++;

		}
	}

	/**
	 * Description... Verify Multiple Supplementary Customs Information
	 * @param SupplementaryCustomsInfo
	 * @param Count
	 * @throws Exception
	 */
	public void verifyMultipleSupplementaryCustmInfo(String SupplementaryCustomsInfo,int Count) throws Exception {

		String split[] = data("SupplementaryCustomsInfo").split(",");
		int i=1;
		for(String s : split){

			String xpath1 = xls_Read.getCellValue(sheetName,
					"inbx_supplementaryCustomsInfoId;id");
			String xpath2=xpath1+i+"]";
			WebElement ele1 = findDynamicXpathElement(xpath2,"OPR026 Additional info tab", "Capture AWB");
			String actcustmInfo = getAttributeWebElement(ele1, "OPR026 Additional info tab", "value", "Capture AWB");

			verifyValueOnPage(
					actcustmInfo.trim().replace(" ", ""),
					s.trim().replace(" ", ""),
					"1. List awb.\n 3. Verify Supplementary Customs Info",
					"Capture AWB", "Supplementary Customs Info");
			i++;
		}
	}      

	/**
	 * Description... Verify Multiple Information ID
	 * @param InformationID
	 * @param Count
	 * @throws Exception
	 */
	public void verifyMultipleInformationID(String InformationID,int Count) throws Exception {

		String split[] = data("InformationID").split(",");
		int i=1;
		for(String s : split){

			String xpath1 = xls_Read.getCellValue(sheetName,
					"inbx_informationID;id");
			String xpath2=xpath1+i+"]";
			WebElement ele1 = findDynamicXpathElement(xpath2,"OPR026 Additional info tab", "Capture AWB");
			String actcustmInfo = getAttributeWebElement(ele1, "OPR026 Additional info tab", "value", "Capture AWB");

			verifyValueOnPage(
					actcustmInfo.trim().replace(" ", ""),
					s.trim().replace(" ", ""),
					"1. List awb.\n 3. Verify Information ID",
					"Capture AWB", "Information ID");
			i++;
		}

	}

	/**
	 * Description... Verify iso Country Code
	 * @param isoCountryCode
	 * @param Count
	 * @throws Exception
	 */
	public void verifyisoCountryCode(String isoCountryCode,int Count) throws Exception {

		String split[] = data("ISOCountryCode").split(",");
		int i=1;
		for(String s : split){

			String xpath1 = xls_Read.getCellValue(sheetName,
					"inbx_isoCountryCode;id");
			String xpath2=xpath1+i+"]";
			WebElement ele1 = findDynamicXpathElement(xpath2,"OPR026 Additional info tab", "Capture AWB");
			String actcustmInfo = getAttributeWebElement(ele1, "OPR026 Additional info tab", "value", "Capture AWB");

			verifyValueOnPage(
					actcustmInfo.trim().replace(" ", ""),
					s.trim().replace(" ", ""),
					"1. List awb.\n 3. Verify ISO Country Code",
					"Capture AWB", "ISO Country Code");
			i++;
		}
	}
	
	/**
	 * Description... Add Other Custom Information
	 * @param SupplementryCustomsInfo
	 * @param count
	 * @throws Exception
	 */
	public void addOtherCustomInformation(String SupplementryCustomsInfo,int count) throws Exception {

		String xpath1 = xls_Read.getCellValue(sheetName,
				"inbx_supplementaryCustomsInfoId;id");
		String xpath2=xpath1+count+"]";
		enterValueInTextbox(xpath2, SupplementryCustomsInfo, "Supplementry Customs Info", screenName);

		waitForSync(2);

	}
	/**
	 * Description... Verify Booking Details Soap Single Segment2
	 * @throws Exception
	 */
	/*public void verifyBookingDetailsSoapSingleSegment2() throws Exception {

	String BookingDetailsSegment2 = "BookingDetailsSegment2=" + data("Transit") + data("Destination")
			+ data("fullFlightNo2");

	verifyXFWBDetailsSoap(BookingDetailsSegment2);
}*/
	/**
	 * Description... click General Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickGeneral() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_General;xpath", "General Button", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");

	}

	/**
	 * Description.. verify supplementary customs info
	 * @param CustomsInfoId
	 * @param SupplementaryCustomsInfo
	 * @throws InterruptedException
	 */
	public void verifySupplementaryCustomsInfo(String CustomsInfoId, String [] SupplementaryCustomsInfo) throws InterruptedException {
		String xpath = (xls_Read.getCellValue(sheetName, "inbx_supplementaryCustomsInfo;xpath")).replace("dynVar",
				CustomsInfoId);
		List <WebElement> l1 = driver.findElements(By.xpath(xpath));
		for(int i =0; i<SupplementaryCustomsInfo.length; i++){
			if ((l1.get(i)).getAttribute("value").contains(SupplementaryCustomsInfo[i])){

				System.out.println("found true for " + SupplementaryCustomsInfo[i]);
				onPassUpdate(screenName, SupplementaryCustomsInfo[i], (l1.get(i)).getAttribute("value"), "Supplementary Customs Info verification ",
						"Supplementary Customs Info verification");

			}else{

				onFailUpdate(screenName, SupplementaryCustomsInfo[i], (l1.get(i)).getAttribute("value"), "Supplementary Customs Info verification ",
						"Supplementary Customs Info verification");
			}
		}
	}

 /**
  * Description..verify other customs information
  * @param verfCols
  * @param actVerfValues
  * @param pmKey
  * @throws IOException
  */
	public void verifyOtherCustomsInformations(int verfCols[], String actVerfValues[], String pmKey) throws IOException {
		waitForSync(4);
		verify_tbl_records_multiple_cols(sheetName, "tbl_OCI;xpath", "//input", verfCols, pmKey,
				actVerfValues);

	}
	
	
	/**
	 * Description... List AWB
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void dataload_listAWB(String awbNo) throws InterruptedException, IOException {

		try{
			String sheetName = "Generic_Elements";
			waitTillScreenload("CaptureAWB_OPR026", "btn_clear;name", "Clear Button", screenName);
			clickWebElement("CaptureAWB_OPR026", "btn_clear;name", "Clear Button", screenName);
			waitTillScreenload(sheetName, "inbx_shipmentPrefix;xpath","Shipment Prefix", screenName);
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath",  awbNo.split("-")[0], "Shipment Prefix",screenName);
			enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath",  awbNo.split("-")[1], "AWB No", screenName);
			clickWebElement(sheetName, "btn_List;xpath", "List Button", screenName);
			waitForSync(6);
			handleShipmentStatusPopUp();
			captureScreenShot("Web");
			test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
			
		}
		catch (Exception e) {
			System.out.println("Could not enter the AWB prefix");
			captureScreenShot("Web");
			test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
			test.log(LogStatus.FAIL, "Could not enter the AWB prefix in "+screenName);

		}



	}
	public boolean paymentAdviceScreen(String awbNumber , int rowVal,int colVal)
	{
		//Select payment mode
		
	   
		try
		{
		String locator=xls_Read.getCellValue("GeneratePaymentAdvice_CSH007", "lst_paymentMode;xpath");
        locator=locator.replace("PMode","CASH");
        driver.findElement(By.xpath(locator)).click();
        waitForSync(2);
        
        //Remarks and Add
        enterValueInTextbox("GeneratePaymentAdvice_CSH007", "inbx_Remarks;xpath", "CASH Payment", "Remarks", "");
        clickWebElement("GeneratePaymentAdvice_CSH007", "btn_Add;xpath", "Add Button", "");
        
         waitForSync(1);
         
         //Finalize payment
         
         clickWebElement("GeneratePaymentAdvice_CSH007", "btn_FinalizePayment;id", "Finalize Payment button", "");
         waitForSync(5);
         
     	boolean isErrorMsg=dataload_verifyErrorMessage(awbNumber,rowVal,colVal);
     	if(isErrorMsg)
     		return false;
     	else
     		{
     		/*******************************************/
     		switchToFrame("default");
    		
    		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
    		try {

    			while (driver.findElement(
    					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
    					.isDisplayed()) {
    				clickWebElement("Generic_Elements", "btn_yes;xpath",
    						"yes Button", screenName);
    			
    				Thread.sleep(12000);
    			}
    		} catch (Exception e) {
    		}

    		finally
    		{
    		switchToFrame("contentFrame", "OPR026");
    		}
    		
    		/*******************************************/
     		 clickWebElement("GeneratePaymentAdvice_CSH007", "btn_Close;name", "Close button", "");
             waitForSync(5);
             return true;
     		}
     	
		}
		
		catch(Exception e)
		{
			return false;
		}
	}
	/**
	 * Description... To perform as is execute
	 * @throws Exception
	 */
	public boolean dataload_asIsExecute(String awbNumber,int rowVal,int colVal) throws Exception {
		screenName="Capture AWB";
		

		

		switchToFrame("default");
		switchToFrame("contentFrame", "OPR026");
		switchToWindow("storeParent");
		

		clickWebElement(sheetName, "btn_AsIsExecute;xpath",
				"AsIsExecute Button", screenName);
		waitForSync(10);
		
		
		
		
		
		switchToFrame("default");
		
		waitTillScreenloadWithOutAssertion("Generic_Elements","htmlDiv_msgStatus;xpath", "warning popup", screenName, 60);
		try {

			while (driver.findElement(
					By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
					.isDisplayed()) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
			
				Thread.sleep(12000);
			}
		} catch (Exception e) {
		}

		finally
		{
		switchToFrame("contentFrame", "OPR026");
		}
		
		boolean isErrorMsg=dataload_verifyErrorMessage( awbNumber,rowVal,colVal);
		if(!isErrorMsg)
		{
		boolean elementpresent=verifyElementEnabled("GeneratePaymentAdvice_CSH007", "btn_FinalizePayment;id");
		
		if(elementpresent)
		{
			
			boolean isPaymentFinalized=paymentAdviceScreen(awbNumber,rowVal,colVal);
			if(!isPaymentFinalized)
				return false;
			
		     
		}
		     	
		     	
		        try {
		        	switchToFrame("default");
					while (driver.findElement(
							By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"))
							.isDisplayed()) {
						clickWebElement("Generic_Elements", "btn_yes;xpath",
								"yes Button", screenName);
					
						Thread.sleep(12000);
					}
				} catch (Exception e) {
				}
		        finally
		        {
		        	switchToFrame("contentFrame", "OPR026");
		        }
		
		
		Thread.sleep(2000);
		handleShipmentStatusPopUpIfDisplayed();
		waitTillScreenload(sheetName, "txt_executed;xpath","Executed text", screenName);
		String actText = driver
				.findElement(
						By.xpath(xls_Read.getCellValue(sheetName,
								"txt_executed;xpath"))).getText();
		
		String expText = "Executed";
		verifyScreenText(sheetName, expText, actText, "As is Execute",
				"Capture AWB");
		
		captureScreenShot("Web");
		test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
		return true;
		}
		
		else
		{
			//setCellValue(rowVal, colVal, "AWB not executed due to error message");
			return false;
		}
		
		
		
	}

	/**
	 * @author A-9844
	 * Description... 	enter NSC as SCC if not present
	 * @param scc
	 * @throws InterruptedException
	 */
	public boolean dataload_verifySCC(String awb) throws InterruptedException{

		
		try{
		By element = getElement(sheetName, "txt_sccText;xpath");
		String actSCCs= driver.findElement(element).getAttribute("value");
		String dgrSCCs=getPropertyValue(grouping, "DGR");
		
		for(int i=0;i<actSCCs.split(",").length;i++)
		{
			if(dgrSCCs.contains(actSCCs.split(",")[i]))
			{
				writeExtent("Fail", "AWB "+awb+" is a DGR shipment");
				return false;
			}
		}
		
		return true;
		
		}
		catch (Exception e) {
			writeExtent("Fail", "Check the  SCC for the AWB "+awb);
			return false;
		}

	}
	/**
	 * Description : Verifying Source of AWB
	 * @author A-9175
	 * @param sourceType
	 * @throws InterruptedException
	 */
	public boolean dataload_verifySource(String awbNumber,int rowVal,int colVal) throws InterruptedException
	{

		String actText ="";
		try
		{
			By source = getElement(sheetName, "label_sourceValue;xpath");
			 actText = driver.findElement(source).getText();

			if(!actText.contains("FWB"))
			{
				setCellValue( rowVal , colVal,"Source of the AWB "+awbNumber+" is "+actText);
				writeExtent("Fail","Source of the AWB "+awbNumber+" is "+actText);
				
				return false;
 
			}
			else
			{
				return true;
			}

			
		}

		catch(Exception e)
		{
			writeExtent("Fail","Source of the AWB "+awbNumber+" is "+actText);
			return false;
			
		}
	}
	   public boolean dataload_verifyErrorMessage(String awbNumber , int rowVal, int colVal) throws InterruptedException, IOException{


			String errorMessage = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMessages;xpath");

			try
			{
				if(driver.findElement(By.xpath(errorMessage)).isDisplayed()){
					
					String errorMessageText=driver.findElement(By.xpath(errorMessage)).getText();
					setCellValue(rowVal, colVal, "Error message is coming on executing the AWB "+awbNumber+" Error message is "+errorMessageText);
					writeExtent("Fail","Error message is coming on executing the AWB "+awbNumber+" Error message is "+errorMessageText);
					captureScreenShot("Web");
					test.log(LogStatus.INFO, test.addScreenCapture(getPropertyValue(globalVarPath,"screenShotPath")));
					return true;
					
				}
				else
				{
					return false;
				}
				
			

				
			}
			
			catch(Exception e)
			{
				
				return false;
			}
	   }

}
		
	

