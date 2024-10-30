package screens;


import java.io.IOException;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class RateAuditDetailed_CRA212 extends CustomFunctions{

	String sheetName="RateAuditDetailed_CRA212";
	String screenName="Rate Audit Detailed : CRA212";
	
	public RateAuditDetailed_CRA212(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
	}
	
	
	/**
	 * @Desc :captureAWB
	 * @author A-9175
	 * @param stationCode
	 * @param AWBNumber
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureAWB(String stationCode, String AWBNumber) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(stationCode), "Station code", screenName);
		enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(AWBNumber), "AWB number", screenName);
	}
	
	
	/**
	 * @Description : List
	 * @author A-9175
	 * @throws InterruptedException,IOException
	 */
	public void listDetails() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_btnList;name", "List",screenName);
		waitForSync(4);
	}
	
	/**
	 * @Description : Clear
	 * @author A-9175
	 * @throws InterruptedException,IOException
	 */
	
	public void clearDetails() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_btnClear;name", "Clear",screenName);
	}
	/**
	 * @Description : Get Other Charges Discrepancy Count
	 * @author A-9175
	 */
	public void getOtherChargesDiscrepancyCount() {
		try{
			String locator=xls_Read.getCellValue(sheetName, "lbl_otherChargediscrepancy;xpath");
			String label=driver.findElement(By.xpath(locator)).getText();
			writeExtent("Pass", " Found "+label+" Number of Discrepancy "+" in Other Charges Discrepancy TAB"+ screenName);
		}catch (Exception e) {
			writeExtent("Fail", " Unable to Find Discrepancy in Other Charges Discrepancy TAB"+ screenName);
		}
			
	}
	/**
	 * @author A-6260
	 * Desc..enter flight number
	 * @param carrierCode
	 * @param flightNumber
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterFlightNumber(String carrierCode, String flightNumber) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_CarrierCode;id", carrierCode, "carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;id", flightNumber, "flight Number", screenName);
	
	}
	/**
	 * @Description : handle OtherCharge Discrepancies
	 * @author A-9175
	 * @param chargeCode
	 */
	public void handleOtherChargeDiscrepancy(String chargeCode) {
		try{
			String statedAWBCharge=xls_Read.getCellValue(sheetName, "inbx_statedAWBCharge;xpath");
			statedAWBCharge=statedAWBCharge.replace("chargehead", chargeCode);
			String statedCharge=driver.findElement(By.xpath(statedAWBCharge)).getAttribute("value");
			System.out.println(statedCharge);
			writeExtent("Pass", " Found "+statedCharge+" As stated AWB Charge "+" in Other Charges Discrepancy TAB"+ screenName);
			
			String Auditedchargeedit=xls_Read.getCellValue(sheetName, "inbx_AuditedchargeEdit;xpath");
			Auditedchargeedit=Auditedchargeedit.replace("chargehead", chargeCode);
			driver.findElement(By.xpath(Auditedchargeedit)).click();
			
			String Auditedcharge=xls_Read.getCellValue(sheetName, "inbx_Auditedcharge;xpath");
			Auditedcharge=Auditedcharge.replace("chargehead", chargeCode);
			driver.findElement(By.xpath(Auditedcharge)).sendKeys(statedCharge);
			writeExtent("Pass", " Entered "+statedCharge+" As Audited charge "+" in Other Charges Discrepancy TAB"+ screenName);
			
			
		}catch (Exception e) {
			writeExtent("Fail", " Not Fixed  Discrepancy in with charges in Other Charges Discrepancy TAB"+ screenName);
		}
	}

/**
	 * @Description : update Market Charge
	 * @author A-9175
	 * @param marketcharge
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void updateMarketCharge(String marketcharge) throws InterruptedException, IOException 
	{
		try {
			String marketchargeeditloc=xls_Read.getCellValue(sheetName, "btn_editMarketcharge;xpath");
			driver.findElement(By.xpath(marketchargeeditloc)).click();
		
			waitForSync(3);
			
			String marketchargeloc=xls_Read.getCellValue(sheetName, "inbx_Marketcharge;xpath");
			waitForSync(3);
			driver.findElement(By.xpath(marketchargeloc)).click();
			waitForSync(3);
			driver.findElement(By.xpath(marketchargeloc)).sendKeys(marketcharge);
			waitForSync(3);
			writeExtent("Pass", " Entered "+marketcharge+" As Market charge "+ screenName);
			
		} catch (Exception e) {
			writeExtent("Fail", " Failed to Enter "+marketcharge+" As Market charge "+ screenName);
		}
			
		
	}
	/**
	 * @desc: Update market rate
	 * @author A-9175
	 * @param marketrate
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void updateMarketRate(String marketrate) throws InterruptedException, IOException 
	{
		try {
			String marketchargeeditloc=xls_Read.getCellValue(sheetName, "btn_editMarketRate;xpath");
			driver.findElement(By.xpath(marketchargeeditloc)).click();
		
			waitForSync(3);
			
			String marketrateloc=xls_Read.getCellValue(sheetName, "inbx_MarketRate;xpath");
			waitForSync(3);
			driver.findElement(By.xpath(marketrateloc)).click();
			waitForSync(5);
			driver.findElement(By.xpath(marketrateloc)).sendKeys(marketrate);
			waitForSync(4);
			writeExtent("Pass", " Entered "+marketrate+" As Market Rate "+ screenName);
			
		} catch (Exception e) {
			writeExtent("Fail", " Failed to Enter "+marketrate+" As Market Rate "+ screenName);
		}
			
		
	}


/**
	 * @Desc : Click Proceed Anyway
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void proceedAnyway() throws InterruptedException, IOException
	{
		switchToFrame("default");
		waitForSync(3);
		clickWebElement(sheetName, "btn_ProceedAnyway;id", " Proceed Anyway ",screenName);
		switchToMainScreen("CRA212");
		
		waitForSync(2);
	}

	/**
	 * @Desc : Verifying Rate audit mile stone statuses
	 * @author A-9175
	 * @param ratestatus
	 */
	
	public void verifyRateAuditStatus(String ratestatus) {
		waitForSync(4);
		String locator=xls_Read.getCellValue(sheetName, "lbl_rateAuditStatus;xpath");
		locator=locator.replace("status", ratestatus);
		String label=driver.findElement(By.xpath(locator)).getAttribute("class");
		try {
			if(label.equals("complete"))
			writeExtent("Pass", " Sucessfully verified "+ratestatus+" is "+label+" in "+ screenName + " Page");
			else
			writeExtent("Fail", " found status of "+ratestatus+" as "+label+" in "+ screenName + " Page");
		} catch (Exception e) {
			writeExtent("Fail", " Status verification failed in "+ screenName + " Page");
		}
		
	}
	/**
	 * 
	 * @param executionDate
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : enter awb execution date
	 */
	public void enterAWBexecutionDate(String executionDate) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_awbExecutionDate;id", executionDate, "AWB execution date", screenName);
		
	}
	
	/**
	 * 
	 * @param remarks
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : enter remarks
	 */
	
	public void enterRemarks(String remarks) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_remarks;id", remarks, "remarks", screenName);
		
	}
	
	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click save
	 */
	public void clickSave() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_save;id", "Save ",screenName);
		waitForSync(2);
	}

	/**
	 * @Description :Click Rate Audit
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickRateAudit() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_rateaudit;name", " Rate Audit ",screenName);
		waitForSync(2);
	}
	
	/**
	 * @Description :click Btn Finalize 
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBtnFinalize() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_btnFinalize;name", " Rate Audit ",screenName);
		waitForSync(2);
	}
	/**
	 * @author A-6260
	 * Desc..verify error messages
	 * @param errMessage
	 */
	public void verifyErrorMessages(String... errMessage)

	{

		String xpath = xls_Read.getCellValue(sheetName, "div_errorMsgs;xpath");

		List<WebElement> ele=driver.findElements(By.xpath(xpath));
		System.out.println(errMessage.length);

		try
		{

			for(int i=0;i<errMessage.length;i++)
			{
				boolean msgFound=false;
				for(WebElement errMsg:ele)
				{
					if(errMsg.getText().equals(errMessage[i]))
					{
						msgFound=true; 
						
					}

				}

				if(msgFound)
				{
					writeExtent("Pass","Error message '"+ errMessage[i]+"' shown on "+screenName);
					System.out.println("Error message "+ errMessage[i]+" shown on "+screenName);
				}
				else
				{
					writeExtent("Fail","Error message '"+ errMessage[i]+"' not shown on "+screenName);
					System.out.println("Error message "+ errMessage[i]+" not shown on "+screenName);
				}
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Expected Error message  not shown on "+screenName);
			System.out.println("Expected Error message  not shown on "+screenName);
		}
	}	
	/**
	 * @author A-9175
	 * @Description : Handling 'n' number Discrepancies
	 */
	public void handleDiscrepancy() {
		switchToFrame("default");
		waitForSync(3);

		try{
			String locator=xls_Read.getCellValue(sheetName, "btn_instructionforrateaudit;xpath");
			List <WebElement> questions=driver.findElements(By.xpath(locator));
			System.out.println(questions.size());
			for(WebElement ele : questions)
			{
				ele.click();
			}
			writeExtent("Pass", "Sucessfully cleared all Discrepancy details in  "+ screenName);
			clickWebElement(sheetName, "btn_Proceed;xpath", " Proceed ",screenName);
			
		}
		catch (Exception e) {
			writeExtent("Fail", " Not Cleared Discrepancy Details in "+ screenName);
		}
		switchToMainScreen("CRA212");
	}
	
	/**
	 * @Description : Click Participant Details TAB
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickParticipantDetails() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "htmlDiv_ParticipantDetails;xpath", " Participant Details ",screenName);
		waitForSync(2);
	}

	/**
	 * @description : verify IATA Agent Details
	 * @author A-9175
	 * @param IATAagentCode
	 */
	public void verifyIATAAgentDetails(String IATAagentCode) 
	{
		String agentCode= getAttributeWebElement(sheetName, "inbx_iataAgentCode;name", "IATAagentCode", "value", screenName);
		verifyScreenText(screenName ,data(IATAagentCode), agentCode, "IATA agent Code","IATA agent Code");
	}

}
