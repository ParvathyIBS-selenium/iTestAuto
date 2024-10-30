package screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.*;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListRateAuditExceptions_CRA193 extends CustomFunctions{
	public ListRateAuditExceptions_CRA193(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="ListRateAuditExceptions_CRA193";
	public String ScreenName="List rate audit exceptions Screen";
	String GenericSheet = "Generic_Elements";

	
	/**
	 * @author A-6260
	 * Desc..Enter discrepancy
	 * @param discrepancy
	 * @throws InterruptedException
	 */
	public void enterDiscrepancy(String discrepancy) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_discrepancy;id", data(discrepancy), "discrepancy", ScreenName);
	}
	
	
	/**
	 * @author A-6260
	 * Desc..Enter agent Code
	 * @param agentCode
	 * @throws InterruptedException
	 */
	public void enterAgentCode(String agentCode) throws InterruptedException{

		enterValueInTextbox(sheetName, "inbx_agentCode;id", data(agentCode), "agent code", ScreenName);
	}

	
	/**
	 * @author A-6260
	 * Desc..enter IATA agent code
	 * @param iataAgentCode
	 * @throws InterruptedException
	 */
	public void enterIATAagentCode(String iataAgentCode) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_IATAagentCode;id", data(iataAgentCode), "IATA agent code", ScreenName);

	}
	
	/**
	 * @author A-6260
	 * Desc.. verify exception remark
	 * @param pmyKey
	 * @param verfCols
	 * @param actVerfValues
	 * @throws IOException
	 */
	public void verifyExceptionRemarks(String pmyKey, int verfCols[], String actVerfValues[]) throws IOException{

		verify_tbl_records_multiple_cols(sheetName, "tble_exceptionDetails;xpath", "//textarea",verfCols, data(pmyKey), actVerfValues);

	}
	
	public void verifyExceptionRemarksNotPresent(boolean passCondition, int verfCols, String actVerfValues) throws IOException
	{
		//verify_tbl_records(sheetName, "tble_exceptionDetails;xpath", "//td",verfCols, passCondition, actVerfValues);
	}
	/**
	 * @author A-6260
	 * Desc.. enter outbound customer details
	 * @param customerCode
	 * @param accNum
	 * @throws InterruptedException
	 */
	public void enterOutboundCustomerDetails(String customerCode, String accNum) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_outboundCustomerCode;id", data(customerCode), "Outbound customer code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_outboundAccNo;id", data(accNum), "Outbound acc number", ScreenName);

	}
	
	
		
	/**
	 * @author A-6260
	 * Desc.. enter inbound customer details
	 * @param customerCode
	 * @param accNum
	 * @throws InterruptedException
	 */
	public void enterInboundCustomerDetails(String customerCode, String accNum) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_inboundCustomerCode;id", data(customerCode), "Inbound customer code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_inboundAccNo;id", data(accNum), "Inbound acc number", ScreenName);

	}

	/**
	 * @author A-6260
	 * Desc.. enter origin and destination
	 * @param OriginType
	 * @param Origin
	 * @param DestinationType
	 * @param Destination
	 * @throws InterruptedException
	 */
	public void enterOriginAndDestination(String OriginType,String Origin, String DestinationType, String Destination) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_origin;id", data(OriginType), "Origin type", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_originCode;id", data(Origin), "origin", ScreenName);
		selectValueInDropdown(sheetName, "lst_destination;id", data(DestinationType), "destination type", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_destination;id", data(Destination), "destination", ScreenName);

	}

	/**
	 * @author A-6260
	 * Desc.. enter awb number
	 * @param carrierNumericCode
	 * @param awb
	 * @throws InterruptedException
	 */
	public void enterAWB(String carrierNumericCode,String awb) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_awbprefix;id", data(carrierNumericCode), "awb prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_awbNo;id", data(awb), "awb number", ScreenName);

	}
	
	/**
	 * @author A-6260
	 * Desc.. enter product code
	 * @param productCode
	 * @throws InterruptedException
	 */
	public void enterProduct(String productCode) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_productCode;id", productCode, "productCode", ScreenName);

	}
	
	/**
	 * @author A-6260
	 * Desc.. enter sales rep
	 * @param salesRep
	 * @throws InterruptedException
	 */
	public void enterSalesRep(String salesRep) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_salesRep;name", data(salesRep), "salesRep", ScreenName);
		

	}
	
/**
 * @author A-6260
 * Desc.. enter date
 * @param filterMode
 * @param fromDate
 * @param toDate
 * @throws InterruptedException
 */
	public void enterDate(String filterMode,String fromDate, String toDate) throws InterruptedException {
		selectValueInDropdown(sheetName, "lst_filterMode;id", data(filterMode), "filter mode", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_fromDate;id", fromDate, "from date", ScreenName);
		enterValueInTextbox(sheetName, "inbx_toDate;id", toDate, "to date", ScreenName);

	}

	
	/**
	 * @author
	 * Desc..click list button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void List() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_list;id", "List button", ScreenName);
		waitForSync(2);
	}


	/**
	 * @author A-6260
	 * Desc..Click clear button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void Clear() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_clear;id", "clear button", ScreenName);
	}

	
	/**
	 * @author A-6260
	 * Desc..click close
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void Close() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_close;id", "close button", ScreenName);
	}
	
	
	/**
	 * @author A-6260
	 * Desc..click save
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void Save() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_save;id", "save button", ScreenName);
	}
	
	
	/**
	 * @author A-6260
	 * Desc..click rate audit details
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void ClickRateAuditDetails() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_rateAuditdetails;id", "rate audit details button", ScreenName);
	}
	
	
	/**
	 * @author A-6260
	 * Desc..click awb enquiry
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void ClickAWBEnquiry() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_awbEnquiry;id", "awb enquiry button", ScreenName);
	}
	
	public void checkIfExceptionResolved(String screen,boolean passCondition,int verfCols, String actVerfValues,String... errMessage) throws IOException
	{
		String xpath = xls_Read.getCellValue("Generic_Elements", "htmlDiv_errorMessages;xpath");
		
		try
		{
		if(driver.findElement(By.xpath(xpath)).isDisplayed())
		{
			verifyErrorMessages(screen,errMessage);
		}
		}
		
		catch(Exception e)
		{
			verifyExceptionRemarksNotPresent( passCondition,  verfCols, actVerfValues);
		}
		
		
	}
	/**
	 * @author A-6260
	 * Desc..verify exception details
	 * @param pmyKey
	 * @param verfCols
	 * @param actVerfValues
	 * @throws IOException
	 */
	public void verifyExceptionDetails(String pmyKey, int verfCols[], String actVerfValues[]) throws IOException{

		verify_tbl_records_multiple_cols(sheetName, "tble_exceptionDetails;xpath", "//td",verfCols, data(pmyKey), actVerfValues);

	}
	
	/**
	 * @author A-6260
	 * Desc.. select rate audit exceptions
	 * @param exceptions
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectRateAuditExceptions(String [] exceptions) throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_RateAuditExceptions;id", "Rate audit exception button", ScreenName);
		waitForSync(1);

		for(int i=0;i<exceptions.length;i++)

		{
			String rateAuditExceptionsLocator = xls_Read.getCellValue(sheetName, "lst_RateAuditExceptions;id");
			rateAuditExceptionsLocator=rateAuditExceptionsLocator.replace("*", exceptions[i]);
			driver.findElement(By.id(rateAuditExceptionsLocator)).click();
			waitForSync(1);

		}
	}

}

