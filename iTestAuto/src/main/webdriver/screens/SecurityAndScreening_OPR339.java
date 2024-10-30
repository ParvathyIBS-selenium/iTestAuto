package screens;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class SecurityAndScreening_OPR339 extends CustomFunctions {

	public SecurityAndScreening_OPR339(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "SecurityAndScreening_OPR339";
	public String screenName = "Security And Screening";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String airportproppath = "\\src\\resources\\AirportGroup.properties";
	/**
	 * Description... List AWB
	 * @throws IOException 
	 */
	public void listAWB(String awbNo, String ShipmentPrefix, String ScreenName)
			throws InterruptedException, IOException {

		awbNo = getPropertyValue(proppath, "AWBNo");

		System.out.println("AWBnumber is ---" + awbNo);
		waitForSync(2);
		enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;name",
				data(ShipmentPrefix), "Shipment Prefix", ScreenName);
		enterValueInTextbox("Generic_Elements", "inbx_AWBnumber;xpath", awbNo,
				"AWB No", ScreenName);
		clickWebElement(sheetName, "btn_list;xpath", "List Button", ScreenName);
		waitForSync(4);

	}
	/**
	 * @author A-9847
	 * @Desc To verify the Screened Pieces and Weight
	 * @param pcs
	 * @param wgt
	 * @throws InterruptedException
	 */
	
	public void verifyScreenedPiecesAndWeight(String pcs,String wgt) throws InterruptedException{
		try{
			
		String actpcs=getElementText(sheetName, "txt_pieces;xpath","Screened Pieces", screenName);
		verifyScreenTextWithExactMatch(sheetName, data(pcs), actpcs, "Screened Pieces", screenName);
		String actwgt=getElementText(sheetName, "txt_weight;xpath","Screened Weight", screenName);
		verifyScreenTextWithExactMatch(sheetName, data(wgt), actwgt, "Screened Weight", screenName);
		}
		catch(Exception e){
			
			writeExtent("Fail","Failed to verify the Screened Pieces and Weight on "+screenName);
		}
	
	}
	
	/**
	 * @Desc : click Edit Screening Info
* @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickEditScreeningInfo() throws InterruptedException, IOException 
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_editScreening;xpath", "Edit Screening Info Button", screenName);
		waitForSync(2);
		
}
	
	/**
	 * @Desc : enter Pcs To Be Screened
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterPcsToBeScreened(String pcs) throws InterruptedException, IOException
	{
		//Pcs
		enterValueInTextbox(sheetName, "inbx_pcsToScreen;xpath", data(pcs), "Pieces", screenName);
		waitForSync(2);	
	}
	
	/**
	 * @Desc : enter Wgt To Be Screened
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterWgtOfScreenedPcs(String wgt) throws InterruptedException, IOException
	{
		//wt
		enterValueInTextbox(sheetName, "inbx_wtToScreen;xpath", data(wgt), "Weight", screenName);
		waitForSync(2);	
	}
	
	/**
	 * @Desc : select Screening Status
* @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectScreeningStatus(String result) throws InterruptedException, IOException
	{
		//Select Result
		selectValueInDropdown(sheetName, "lst_screenResult;xpath",	data(result), "Screening result","VisibleText");
		waitForSync(2);
}
	
	/**
	 * @Desc : click Ok
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOk() throws InterruptedException, IOException
	{
		//Click Ok button
		clickWebElement(sheetName, "btn_ScreeningOk;xpath","Ok button", screenName);
		waitForSync(2);
	}

	/**
	 * @author A-9847
	 * @Desc To verify whether the Security Data Reviewed Checkbox is Checked
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifySecurityDataReviewedIsTicked() throws InterruptedException, IOException
	{

		try{
			
		String locatorValue=xls_Read.getCellValue(sheetName, "chk_SecurityDataRcvd;xpath");              
		if(driver.findElement(By.xpath(locatorValue)).isSelected())
			writeExtent("Pass","The Security Data Reviewed Checkbox is Checked on "+screenName);
		else
			writeExtent("Fail","The Security Data Reviewed Checkbox is Unchecked on "+screenName);
		waitForSync(1);
             
		}catch(Exception e){
			
			writeExtent("Fail","Failed to verify the status of Security Data Reviewed Checkbox on "+screenName);
			
		}

	}
	
	/**
	 * @author A-9847
	 * @Desc To verify whether Given Security Status Accepted Checkbox is Checked
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyGivenSecurityStatusAcceptedIsTicked() throws InterruptedException, IOException
	{

		try{
			
		String locatorValue=xls_Read.getCellValue(sheetName, "chk_givenSecurityStatusAccepted;name");              
		if(driver.findElement(By.name(locatorValue)).isSelected())
			writeExtent("Pass","The Given Security Status Accepted Checkbox is Checked on "+screenName);
		else
			writeExtent("Fail","The Given Security Status Accepted Checkbox is Unchecked on "+screenName);
		waitForSync(1);
		
		}catch(Exception e){
			
			writeExtent("Fail","Failed to verify the status of Given Security Status Accepted Checkbox on "+screenName);
			
		}


	}
	


	/**
     * @author A-9175
     * @Description : Capturing double screening details
     * @param screeningMethod
     * @param pcs
     * @param wt
     * @param result
     * @throws InterruptedException
     * @throws IOException
     */
     public void captureDoubleScreeningDetails(String screeningMethod[],String pcs[],String wt[],String result[]) throws InterruptedException, IOException
     {
            //Screening methods
            
            for(int i=0;i<2;i++)
            {
                   
                //Screening methods
                selectValueInDropdown(sheetName, "lst_screenMethod;xpath", screeningMethod[i], "Screening method","VisibleText");
                
                if(screeningMethod[i].contains("AOM"))
                  //AOM Details
                        enterValueInTextbox(sheetName, "inbx_AOM_Details;xpath", data("val~AOM"), "AOM Details", screenName);  
                
                //Pcs
                enterValueInTextbox(sheetName, "inbx_pcsToScreen;xpath",pcs[i], "Pieces", screenName); 
                //wt
                enterValueInTextbox(sheetName, "inbx_wtToScreen;xpath", wt[i], "Weight", screenName);
         
                //Select Result
                selectValueInDropdown(sheetName, "lst_screenResult;xpath", result[i], "Screening result","VisibleText");
                
                //Click add button
                clickWebElement(sheetName, "btn_addScreeningDetails;xpath","Add button", screenName);
                waitForSync(2);

            }
     }


	/**
	 * @author A-9847
	 * @Desc To Verify the Screening result and SU number
	 * @param screeningResult
	 * @param SU
	 */
	
	public void verifyScreeningResultAndSUnumber(String screeningResult,String SU){
		
		
		String screeningResultloc = xls_Read.getCellValue(sheetName, "txt_screeningResult;xpath");
		screeningResultloc=screeningResultloc.replace("*",screeningResult);
		
		if(driver.findElements(By.xpath(screeningResultloc)).size()==1) {
			onPassUpdate(screenName,"screening result "+ screeningResult ,"screening result "+ screeningResult, "Verification of screening result","Verification of screening result");
		} else {
			onFailUpdate(screenName,"screening result "+ screeningResult ,"screening result "+ screeningResult, "Verification of screening result","Verification of screening result");
		}
		
		
		String suloc = xls_Read.getCellValue(sheetName, "txt_Sunumber;xpath");
		suloc=suloc.replace("*",SU);
		
		if(driver.findElements(By.xpath(suloc)).size()==1) {
			onPassUpdate(screenName," SU "+ SU ," SU "+ SU, "Verification of SU","Verification of SU");
		} else {
			onFailUpdate(screenName," SU "+ SU ," SU "+ SU, "Verification of SU","Verification of SU");
		}
		
		
		
		
	}
	
	/**
	 * @author A-9847
	 *@Desc To verify the Screener name and Screening Date
	 * @param screenerName
	 * @param date
	 */
	public void verifyScreenerDetails(String screenerName, String date){
		
		String actScreenerName=getAttributeWebElement(sheetName, "inbx_screenerName;id","Screener Name","value", screenName);
		verifyScreenTextWithExactMatch(sheetName, data(screenerName), actScreenerName, "Screener Name", screenName);
		
		String actDate=getAttributeWebElement(sheetName, "inbx_screeningDate;id","Screening Date","value", screenName);
		verifyScreenTextWithExactMatch(sheetName, date, actDate, "Screening Date", screenName);
		
		
	}
	/**
	 * Description... Verify agent details autopopulated after loading xfwb with security info
	 * @author A-10690
	 * @param agentType
	 * @param countryCode
	 * @param agentId
	 * @throws InterruptedException
	 */
	public void verifyAgentDetailsAutopopulated(String agentType,String countryCode,String agentId) throws InterruptedException
	{
		//AgentType
				String agentTypeActual=getElementText(sheetName, "htmlDiv_agentType;xpath",
						"Agent Type", screenName);

				verifyScreenText(sheetName, data(agentType), agentTypeActual, "Agent Type", screenName);

				//country code
				String countryCodeActual=getElementText(sheetName, "htmlDiv_isoCountryCode;xpath",
						"Country code", screenName);

				verifyScreenText(sheetName, data(countryCode), countryCodeActual, "Country Code", screenName);

				//Agent id
				String agentIdActual=getElementText(sheetName, "htmlDiv_agentId;xpath",
						"Agent Id", screenName);

				verifyScreenText(sheetName, data(agentId), agentIdActual, "Agent Id", screenName);

	}
	/**
	 * @author A-9844
	 * Description... Enter Screening Details with AOM details
	 * @param screeningMethod
	 * @param pcs
	 * @param AOMDetails
	 * @param wt
	 * @param result
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterScreeningDetailsForValidCCSF(String screeningMethod,String AOMDetails,String pcs,String wt,String result) throws InterruptedException, IOException
	{
		//Screening methods
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_screenMethod;xpath",	data(screeningMethod), "Screening method","VisibleText");
		waitForSync(2);
		
		//AOM Details
		enterValueInTextbox(sheetName, "inbx_AOM_Details;xpath", data(AOMDetails), "AOM Details", screenName);	


		//Pcs
		enterValueInTextbox(sheetName, "inbx_pcsToScreen;xpath", data(pcs), "Pieces", screenName);	

		//wt
		enterValueInTextbox(sheetName, "inbx_wtToScreen;xpath", data(wt), "Weight", screenName);
		waitForSync(2);
		//Select Result
		selectValueInDropdown(sheetName, "lst_screenResult;xpath",	data(result), "Screening result","VisibleText");
		waitForSync(2);
		//Click add button

		clickWebElement(sheetName, "btn_addScreeningDetails;xpath","Add button", screenName);
	}

	/**
	 * Description... Verify screeningmethodautopopulated
	 * @author A-10690
	 * @param screeningmethod
	 * @throws InterruptedException
	 */
	public void verifyScreeningMethodAutopopulated(String screeningmethod) throws InterruptedException
	{
		String locator = xls_Read.getCellValue(sheetName, "txt_securitymethod;xpath");
		locator=locator.replace("*",data(screeningmethod));
		
		if(driver.findElements(By.xpath(locator)).size()==1) {
			onPassUpdate(screenName,"screening method "+ data(screeningmethod) ,"screening method "+ data(screeningmethod), "Verification of screening method","Verification of screening method");
		} else {
			onFailUpdate(screenName,"screening method "+ data(screeningmethod) ,"screening method "+ data(screeningmethod), "Verification of screening method","Verification of screening method");
		}
	}
    /**@author A-10328
	 * Description... check new security status given  check box
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void checkNewSecurityStatusGiven() throws InterruptedException, IOException
	

	{

		 String locatorValue=xls_Read.getCellValue(sheetName, "chk_NewSecurityStatusRcvd;xpath"); 
		
		  moveScrollBar(driver.findElement(By.xpath(locatorValue)));
		
		if(!driver.findElement(By.xpath(locatorValue)).isSelected())	
		
			clickWebElement(sheetName, "chk_NewSecurityStatusRcvd;xpath","New Security Status Given Checkbox", screenName);
		     waitForSync(1);
		


	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : check given security status accepted checkbox
	 */
	public void checkGivenSecurityStatusAccepted() throws InterruptedException, IOException
	{
		
		 String locatorValue=xls_Read.getCellValue(sheetName, "chk_givenSecurityStatusAccepted;name");
		 moveScrollBar(driver.findElement(By.name(locatorValue)));
		 
		if(!driver.findElement(By.name(locatorValue)).isSelected())
		clickWebElement(sheetName, "chk_givenSecurityStatusAccepted;name","Given Security Status Accepted", screenName);	
		waitForSync(1);

	}

	/**
	 * @author A-8783
	 * Desc - Verify block checked icon
	 * @throws InterruptedException
	 */
	public void verifyNoBlock() throws InterruptedException {
	verifyElementDisplayed(sheetName, "img_BlockIcon;xpath", "Verify Block icon", screenName, "Block icon");
	}

	/**
	 * @author A-8783
	 * Description : Click on Print Button and not close the pdf.
	 * @throws Exception
	 */
	public void clickPrintForVerification() throws Exception
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_print;id","Print button", screenName);
		
	}
	/**
	 * @author A-8783
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
	verifyElementDisplayed(sheetName, "img_eCSDicon;xpath", "Verify eCSD icon", screenName, "eCSD icon");
}



	/**
	 * @author A-6260
	 * Desc: save security and screening after releasing the block
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveSecurityDetailsWithBlock() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_OK;xpath","Save button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		boolean blockExists=false;
		
		//Verify the block message
		while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
		{
			String expText= "Do you want to release the block on the AWB?";
			String actText=getElementText(sheetName, "htmlDiv_blockMsg;xpath","block release message", screenName);		
			if(actText.contains(expText))
			{
				blockExists=true;
			}
			
			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(3);
		}

		if(blockExists)
		{
			writeExtent("Pass","Message "+"'Do you want to release the block on the AWB?'"+ " displayed on releasing the block on "+screenName);
		}
		else
		{
			writeExtent("Fail","Message "+"'Do you want to release the block on the AWB?'"+ " not displayed on releasing the block on "+screenName);
		}
		
		//Verify the screening save success message
		String expectedMsg="Screening details saved succesfully";

		String actualMsg=getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath","Confirmation Message", screenName);

		verifyScreenText(sheetName, expectedMsg, actualMsg, "Confirmation Msg", screenName);

		clickWebElement("Generic_Elements", "btn_OK;xpath","Ok button", screenName);


	}
	/**
	 * @author A-9175
	 * @description : Capture n number of screening details
	 **/
	public void captureScreeningDetails(String screeningMethod[],String pcs[],String wt[],String result[]) throws InterruptedException, IOException
	{
		//Screening methods

		for(int i=0;i<screeningMethod.length;i++)
		{

			//Screening methods
			selectValueInDropdown(sheetName, "lst_screenMethod;xpath", screeningMethod[i], "Screening method","VisibleText");

			if(screeningMethod[i].contains("AOM"))
				//AOM Details
				enterValueInTextbox(sheetName, "inbx_AOM_Details;xpath", data("val~AOM"), "AOM Details", screenName);  

			//Pcs
			enterValueInTextbox(sheetName, "inbx_pcsToScreen;xpath",pcs[i], "Pieces", screenName); 
			//wt
			enterValueInTextbox(sheetName, "inbx_wtToScreen;xpath", wt[i], "Weight", screenName);

			//Select Result
			selectValueInDropdown(sheetName, "lst_screenResult;xpath", result[i], "Screening result","VisibleText");

			//Click add button
			clickWebElement(sheetName, "btn_addScreeningDetails;xpath","Add button", screenName);
			waitForSync(2);

		}
	}

	/**
	 * @author A-8783
	 * @param status
	 * @throws InterruptedException
	 */
	public void verifySecurityStatus(String status) throws InterruptedException {
		String actualStatus=getElementText(sheetName, "txt_securityStatus;xpath","Security status", screenName);
		verifyScreenText(sheetName, status, actualStatus, "Confirmation Msg", screenName);
	}

	/**
	 * Description... Save Security Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void saveSecurityDetail() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_OK;xpath","Save button", screenName);

		switchToFrame("default");

		if(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
		{
			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(4);
		}
		waitForSync(4);
		clickWebElement("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
	}

	/**
	 * @author A-10690
	 * @Desc To Verify the Screening result 
	 * @param screeningResult

	 */
	
	public void verifyScreeningResult(String screeningResult){
		String screeningResultloc = xls_Read.getCellValue(sheetName, "txt_screeningResult;xpath");
		screeningResultloc=screeningResultloc.replace("*",screeningResult);
		
		if(driver.findElements(By.xpath(screeningResultloc)).size()==1) {
			onPassUpdate(screenName,"screening result "+ screeningResult ,"screening result "+ screeningResult, "Verification of screening result","Verification of screening result");
		} else {
			onFailUpdate(screenName,"screening result "+ screeningResult ,"screening result "+ screeningResult, "Verification of screening result","Verification of screening result");
		}
	}


	/**
	 * Description... List AWB
	 * @throws IOException 
	 */
	public void listAWBNo(String awbNo, String ShipmentPrefix, String ScreenName)
			throws InterruptedException, IOException {

		awbNo = getPropertyValue(proppath, awbNo);

		System.out.println("AWBnumber is ---" + awbNo);
		waitForSync(2);
		enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;name",
				data(ShipmentPrefix), "Shipment Prefix", ScreenName);
		enterValueInTextbox("Generic_Elements", "inbx_AWBnumber;xpath", awbNo,
				"AWB No", ScreenName);
		clickWebElement(sheetName, "btn_list;xpath", "List Button", ScreenName);
		waitForSync(4);

	}
	
	
	/**
	 * @author A-6260
	 * Description: To verify the scc field
	 * @param SCC
	 */
	public void verifyScc(String SCC){
		try{			
			String expScc=data(SCC);
			waitForSync(4);
			String actScc=getElementText(sheetName,"link_shipmentSCC;xpath", "Shipment SCC", screenName).trim();
			verifyValueOnPage(actScc, expScc, "verify SCC","Security And Screening","verify SCC in Screening");
			waitForSync(3);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @author A-6260
	 * Description: select reason for exemption
	 * @param exemptionReason
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectReasonForExemption(String exemptionReason) throws InterruptedException, IOException
	{
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_reasonForExemption;xpath",	data(exemptionReason), "Reason For Exemption","VisibleText");
		waitForSync(2);
	}
	
	
	/**
	 * @author A-9175
	 * Description : Click on Print Button
	 * @throws Exception
	 */
	public void clickPrint() throws Exception
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_print;id","Print button", screenName);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		closeBrowser();
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR339");
	}
    /**
	 * Description... Verify Agent Details for table verfication
	 * @author A-10330
	 * @param agentType
	 * @param countryCode
	 * @param agentId
	 * @throws InterruptedException
 * @throws IOException 
	 */
	public void verifyAgentDetails(int verfCols[], String actVerfValues[],
			String pmKey,String agentType,boolean isAssertreq) throws InterruptedException, IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "htmlDiv_agentDetails;xpath",
				"//td", verfCols, pmKey, actVerfValues,data(agentType),isAssertreq);
     }

	
	/**
	 * Description... Delete Screening Details
	 * @param number
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void deleteScreeningDetails(int number) throws InterruptedException, IOException
	{
		for(int i=0;i<number;i++)
		{
			clickWebElement(sheetName, "btn_deleteSecurityDetails;xpath","Delete button", screenName);
			waitForSync(1);
		}
	}
	
	
	/**
	 * Description... Enter SCC
	 * @param SecSCC
	 * @throws Exception
	 */
	public void enterSCC(String SecSCC) throws Exception {
		 //COMMENTED AS PART OF NSC AUTOSTAMPING RULE
		/******screenName="Security and Screening";


		clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);

		enterValueInTextbox(sheetName, "inbx_SCC;xpath", data(SecSCC),"SecSCC", screenName);
		clickWebElement(sheetName, "btn_okAddSCC;id", "OK button", screenName);*****/

	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Screening details of Multiple SUs
	 * @param su
	 * @param screeningMethods
	 * @param screeningResult
	 * @param screeningPcs
	 * @param screeningWgt
	 */
	public void verifyScreeningDetailsOfMultipleSUs(String su[],String screeningMethods[],String screeningResult[],String screeningPcs[],String screeningWgt[]){
		try{
			
		    waitForSync(3);
			
		    for(int i=0;i<su.length;i++)
			{
	
			String screeningmethod = xls_Read.getCellValue(sheetName, "txt_multipleSUScreeningMethod;xpath").replace("*",su[i]).replace("screeningmethod",screeningMethods[i]);
			if(driver.findElements(By.xpath(screeningmethod)).size()==1) 
				onPassUpdate(screenName,"Screening method "+ screeningMethods[i] ,"Screening method "+ screeningMethods[i], "Verification of screening method of "+su[i],"Verification of screening method of "+su[i]);
			 else 
				onFailUpdate(screenName,"Screening method "+ screeningMethods[i] ,"Screening method "+ screeningMethods[i], "Verification of screening method of "+su[i],"Verification of screening method of "+su[i]);
			
			String screeningresult = xls_Read.getCellValue(sheetName, "txt_multipleSUScreeningResult;xpath").replace("*",su[i]).replace("result",screeningResult[i]);
			if(driver.findElements(By.xpath(screeningresult)).size()==1) 
				onPassUpdate(screenName,"Screening result "+ screeningResult[i] ,"Screening result "+ screeningResult[i], "Verification of screening result of "+su[i],"Verification of screening result of "+su[i]);
			 else 
					onFailUpdate(screenName,"Screening result "+ screeningResult[i] ,"Screening result "+ screeningResult[i], "Verification of screening result of "+su[i],"Verification of screening result of "+su[i]);
			
			String actScreenedPcs=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_multipleSUScreenedPcs;xpath").replace("*",su[i]))).getText();		
			System.out.println(actScreenedPcs);
			verifyScreenTextWithExactMatch(screenName ,screeningPcs[i], actScreenedPcs, "Screened Pieces of "+su[i],"Screened Pieces "+su[i]);

			String actScreenedWgt = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_multipleSUScreenedWgt;xpath").replace("*",su[i]))).getText();
			System.out.println(actScreenedWgt);
			verifyScreenTextWithExactMatch(screenName ,screeningWgt[i], actScreenedWgt, "Screened Weight of "+su[i],"Screened Weight of "+su[i]);

			}
				
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify the Screening details on "+screenName);
			}
			
		}	

	

	/**
	 * Description... Security And Screening
	 * @param SecSCC
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void securityAndScreening(String SecSCC) throws InterruptedException, IOException {
		switchToFrame("default");
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR339");
		enterValueInTextbox(sheetName, "inbx_SCC;xpath", data(SecSCC),
				"SecSCC", screenName);
		Thread.sleep(1000);
		clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath",
				"Security Checkbox", screenName);
		clickWebElement(sheetName, "btn_OK;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_OK_xpath", "Yes Button", screenName);
	}
	
	
	/**
	 * Description... Delete SCC add New SCC
	 * @param deleteSCC
	 * @param addSCC
	 * @throws Exception
	 */
	public void deleteSCCaddNewSCC(String deleteSCC,String addSCC) throws Exception {
		screenName="Security and Screening";
		clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName);
		String expDeleteSCC=getElementText(sheetName,"link_shipmentSCC;xpath", "Shipment SCC", screenName);
		clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);
		waitForSync(4);
		if(expDeleteSCC.contains(deleteSCC)){
			clickWebElement(sheetName, "btn_deleteSCC;xpath", "Delete SCC Button", screenName);
			waitForSync(3);
			clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_SCC;xpath", addSCC,"SecSCC", screenName);
			waitForSync(4);
			clickWebElement(sheetName, "btn_addBtn;xpath", "OK Button ", screenName);
			waitForSync(2);

		}	
	}
	
	
	/**
	 * Description... Modify Security And Screening Screen
	 * @param ScrnrName
	 * @param ScrnrDate
	 * @throws Exception
	 */
	public void modifySecurityAndScreeingScreen(String ScrnrName, String ScrnrDate) throws Exception {
		screenName="Security and Screening";
		enterValueInTextbox(sheetName, "inbx_sccreenerName;xpath", ScrnrName, "Screener Name",
				screenName);
		enterValueInTextbox(sheetName, "inbx_sccreenerDate;xpath", ScrnrDate, "Screener Date",
				screenName);
		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
		waitForSync(4); 

	}
	

	/**
	 * @author A-9847
	 * @Desc To click OK after saving screening details without checking any checkboxes
	 * @throws Exception
	 */
	public void OkButtonAfterScreeningSaveWithoutCheckingCheckboxes() throws Exception
	{
		
		addAgentDetails(getPropertyValue(proppath, "testEnv"));

		javaScriptToclickElement(sheetName, "btn_OK;xpath","Ok button", screenName);
		waitForSync(3);
		switchToFrame("default");

		while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
		{
			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(3);
		}
    }

	/**
	 * Description... Add Agent Details
	 * @param agentType
	 * @param countryCode
	 * @param agentId
	 * @param expiry
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void addAgentDetails(String agentType,String countryCode,String agentId, String expiry)throws InterruptedException,Exception
	{
		//Expanding the agent tab
				clickWebElement(sheetName, "tab_agentDetails;xpath", "Agent details", screenName);

				//Click on Add button for adding details
				clickWebElement(sheetName, "btn_addAgent;xpath", "Click Add button", screenName);
				switchToFrame("default");	
				switchToFrame("contentFrame","OPR339");

				//AgentType
				selectValueInDropdown(sheetName, "lst_agentType;xpath", data(agentType), "Agent Type", "VisibleText");

				//Country Code 
				enterValueInTextbox(sheetName, "inbx_countryCode;xpath", data(countryCode), "Country Code", screenName);

				//Agent ID
				enterValueInTextbox(sheetName, "inbx_agentId;xpath", data(agentId), "Agent ID", screenName);

				//Expiry
				enterValueInTextbox(sheetName, "inbx_expiry;xpath", data(expiry), "expiry", screenName);

				//Add 
				clickWebElement(sheetName, "btn_okAgentAdd;xpath", "Add agent Details", screenName);
				waitForSync(6);
				switchToFrame("default");	
				switchToFrame("contentFrame","OPR339");

	}
	/**
	 * Description... Add Agent Details
	 * @param agentType
	 * @param countryCode
	 * @param agentId
	 * @param expiry
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void addAgentDetails(String agentType,String countryCode,String agentId, String expiry,String screenId,String frameName)throws InterruptedException,Exception
	{
		//Expanding the agent tab
				clickWebElement(sheetName, "tab_agentDetails;xpath", "Agent details", screenName);

				//Click on Add button for adding details
				clickWebElement(sheetName, "btn_addAgent;xpath", "Click Add button", screenName);
				switchToFrame("default");	
				switchToFrame("contentFrame",screenId);
				switchToFrame("frameName", frameName);
				
				//AgentType
				clickWebElementByWebDriver(sheetName, "lst_agentType;xpath", "Agent Type", screenName);			
				String loc = xls_Read.getCellValue(sheetName, "drpdn_agent;xpath");
				loc=loc.replace("*",data(agentType));			
				driver.findElement(By.xpath(loc)).click();	

			/*********selectValueInDropdown(sheetName, "lst_agentType;xpath", data(agentType), "Agent Type", "VisibleText");********/
				
				//Country Code 
				enterValueInTextbox(sheetName, "inbx_countryCode;xpath", data(countryCode), "Country Code", screenName);

				//Agent ID
				enterValueInTextbox(sheetName, "inbx_agentId;xpath", data(agentId), "Agent ID", screenName);

				//Expiry
				enterValueInTextbox(sheetName, "inbx_expiry;xpath", data(expiry), "expiry", screenName);

				//Add 
				clickWebElement(sheetName, "btn_okAgentAdd;xpath", "Add agent Details", screenName);
				waitForSync(6);
				
				
				

	}
	/**
	 * @author A-6260
	 * Desc: Verify SCC
	 * @param scc
	 */
	public void verifyScc(String[] scc){
		try{	
			String actScc=getElementText(sheetName,"link_shipmentSCC;xpath", "Shipment SCC", screenName).trim();
			int size=scc.length;
			for(int i=0;i<size;i++) {
				verifyScreenText(sheetName,scc[i], actScc,"verify SCC", "verify SCC in Screening");
			}
			waitForSync(3);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @author A-72721
	 * Desc: Verify SCC
	 * @param scc
	 */
	public void verifySccNotPresent(String[] scc){
		try{	
			String actScc=getElementText(sheetName,"link_shipmentSCC;xpath", "Shipment SCC", screenName).trim();
			int size=scc.length;
			for(int i=0;i<size;i++) {
				verifyScreenTextNotExists(sheetName,scc[i], actScc,"verify SCC not present", "verify SCC in Screening");
			}
			waitForSync(3);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * @author A-9844
	 * @param screening completed status
	 * @throws InterruptedException
	 */
	public void verifyScreeningCompletedStatus(String status) throws InterruptedException {
		String actualStatus=getElementText(sheetName, "txt_screeningCompleted;xpath","Screening Completed status", screenName);
		verifyScreenText(sheetName, status, actualStatus, "Screening Completed status", screenName);
	}
	/**
	 * @author A-9847
	 * @Desc To add RA Accepting details
	 * @param RAAcceptingCountryId
	 * @param RAAcceptingCode
	 * @param RAExpiry
	 */
	public void addRAAcceptingDetails(String RAAcceptingCountryId, String RAAcceptingCode,String RAExpiry){

		try{

			//Expanding the agent tab
			clickWebElement(sheetName, "tab_agentDetails;xpath", "Agent details", screenName);

			//Click on Add button for adding details
			clickWebElement(sheetName, "btn_addAgent;xpath", "Click Add button", screenName);

			//AgentType
			selectValueInDropdown(sheetName, "lst_agentType;xpath", "Reg. Agent Accepting", "Agent Type", "VisibleText");

			//Country Code 
			enterValueInTextbox(sheetName, "inbx_countryCode;xpath", data(RAAcceptingCountryId), "Country Code", screenName);

			//Agent ID
			enterValueInTextbox(sheetName, "inbx_agentId;xpath", data(RAAcceptingCode), "Agent ID", screenName);

			//Expiry
			enterValueInTextbox(sheetName, "inbx_expiry;xpath", data(RAExpiry), "Expiry", screenName);


			//Add 
			clickWebElement(sheetName, "btn_okAgentAdd;xpath", "Add agent Details", screenName);
			waitForSync(3);

		}catch(Exception e){

			writeExtent("Fail","Failed to add the RA Accepting details on "+screenName);


		}

	}


	/**
	 * @author A-9844
	 * @param screening completed status
	 * @throws InterruptedException
	 */
	public void verifySecurityDataReviewedStatus(String status) throws InterruptedException {
		String actualStatus=getElementText(sheetName, "txt_securityDataReviewd;xpath","Security Data Reviewed", screenName);
		verifyScreenText(sheetName, status, actualStatus, "Security Data Reviewed status", screenName);
	}
	/**
	 * Description... Update SCC
	 * @param SecSCC
	 * @throws Exception
	 */
	public void updateSCC(String SecSCC) throws Exception {
		screenName="Security and Screening";
		switchToFrame("contentFrame", "OPR339");
		clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName);
		clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);

		enterValueInTextbox(sheetName, "inbx_SCC;xpath", data(SecSCC),"SecSCC", screenName);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);
		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);

	}
	/**
	 * @author A-9847
	 * @Desc To verify the failure reason in case of Failed Screening
	 * @param failureReason
	 */
	public void verifyFailureReason(String failureReason){
	
		try{	
		String failreason = xls_Read.getCellValue(sheetName, "txt_Sunumber;xpath");
		failreason=failreason.replace("*",data(failureReason));
		System.out.println(driver.findElement(By.xpath(failreason)).getText());
		if(driver.findElements(By.xpath(failreason)).size()==1) 
			writeExtent("Pass","Successfully verified the Failure Reason as "+data(failureReason)+" on "+screenName);
		 else 
			 writeExtent("Fail","Failed to verify the Failure Reason as "+data(failureReason)+" on "+screenName);
		
	}catch(Exception e){
		
		writeExtent("Fail","Failed to verify the Failure Reason on "+screenName);
	}		
		
	}

	/**
	 * Description... Add the second agent details 
	 * @param agentType
	 * @param countryCode
	 * @param agentId
	 * @param expiry
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void addSecondAgentDetails(String agentType,String countryCode,String agentId, String expiry)throws InterruptedException,Exception
	{
		//Expanding the agent tab
				//clickWebElement(sheetName, "tab_agentDetails;xpath", "Agent details", screenName);

				//Click on Add button for adding details
				clickWebElement(sheetName, "btn_addAgent;xpath", "Click Add button", screenName);
				switchToFrame("default");	
				switchToFrame("contentFrame","OPR339");

				clickWebElement(sheetName, "btn_selectStatus;id", "Selet Agent Type", screenName);
				String agentdata = xls_Read.getCellValue(sheetName, "btn_selecttype;xpath");
				agentdata=agentdata.replace("*",data(agentType));
				driver.findElement(By.xpath(agentdata)).click();

				//Country Code 
				enterValueInTextbox(sheetName, "inbx_countryCode;xpath", data(countryCode), "Country Code", screenName);

				//Agent ID
				enterValueInTextbox(sheetName, "inbx_agentId;xpath", data(agentId), "Agent ID", screenName);

				//Expiry
				enterValueInTextbox(sheetName, "inbx_expiry;xpath", data(expiry), "expiry", screenName);

				//Add 
				clickWebElement(sheetName, "btn_okAgentAdd;xpath", "Add agent Details", screenName);
				waitForSync(6);
				switchToFrame("default");	
				switchToFrame("contentFrame","OPR339");

	}
	/**
	 * @author A-9847
	 * @Desc To verify the Agent details of the corresponding Agent Type given
	 * @param agentType
	 * @param countryCode
	 * @param agentCode
	 * @param expiry
	 * @throws InterruptedException
	 */
	
	public void verifyAgentDetailsAutopopulated(String agentType,String countryCode,String agentCode, String expiry) throws InterruptedException
	{
		        
		        //Agent Country code
		        String countryCodeActual=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "htmlDiv_agentCountryCode;xpath").replace("*", data(agentType)))).getText();
		        verifyScreenText(sheetName, data(countryCode), countryCodeActual, data(agentType)+" Country Code", screenName);

				//Agent id
				String agentIdActual=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "htmlDiv_agentCode;xpath").replace("*", data(agentType)))).getText();
				verifyScreenText(sheetName, data(agentCode), agentIdActual, data(agentType)+" Id Code", screenName);
				
				
				//Agent Expiry
				String agentExpiryActual=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "htmlDiv_agentExpiry;xpath").replace("*", data(agentType)))).getText();
				verifyScreenText(sheetName, data(expiry), agentExpiryActual, data(agentType)+" Expiry", screenName);

	}
	
	
	/**
	 * @author A-9847
	 * @Desc To retrieve the number of Agent details rows present
	 * @param expcount
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void getNumberOfAgentDetailsPresent(int expcount) throws InterruptedException, IOException

	{

		String locator = xls_Read.getCellValue(sheetName, "text_agentsCount;xpath");
		int size=driver.findElements(By.xpath(locator)).size();
		System.out.println(size);

		if(size==expcount)		
			writeExtent("Pass", "Successfully verified the Records of Agent Details present as " + expcount + "on "+screenName);	
		else		
			writeExtent("Fail", "Failed to verify the Records of Agent Details as " + expcount + "where the actual records came as "+size+" on "+screenName);


	}





/**
	 * @author A-9847
	 * @Desc To verify the new security status given checkbox is autoticked
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyNewSecurityStatusGivenIsTicked() throws InterruptedException, IOException
	{

		try{
			
		String locatorValue=xls_Read.getCellValue(sheetName, "chk_NewSecurityStatusRcvd;xpath");     
		if(driver.findElement(By.xpath(locatorValue)).isSelected())
			writeExtent("Pass","New Security Status Given is Checked on "+screenName);
		else
			writeExtent("Fail","The New Security Status Given Checkbox is Unchecked on "+screenName);
		
		}catch(Exception e){
			
			writeExtent("Fail","Failed to verify the status of New Security Status Given Checkbox on "+screenName);
			
		}


	}
	/**
	 * 
	 * @throws Exception
	 * Desc : save security details without checking any checkboxes
	 */
	public void saveSecurityDetailsWithOutTickingCheckbox() throws Exception
	{
		addAgentDetails(getPropertyValue(proppath, "testEnv"));
      
		clickWebElement(sheetName, "btn_OK;xpath","Save button", screenName);
        waitForSync(3);
        switchToFrame("default");
        
   /******   while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
      {
            clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
            waitForSync(3);
      }*****/
      

     int counter=1;
        
        while(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "btn_saveSecurityDetailsOK;xpath"))).size()==0 && counter<=5)
        {      

               try{
                     waitForSync(1);     
                     driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Yes;xpath"))).click();
                     waitForSync(3);     

               }
               catch(Exception e){

                     counter=counter+1;
                     
               } 
        
        }
      
        String expectedMsg="Screening details saved succesfully";

        String actualMsg=getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath","Confirmation Message", screenName);

        verifyScreenText(sheetName, expectedMsg, actualMsg, "Confirmation Msg", screenName);

        clickWebElement("Generic_Elements", "btn_OK;xpath","Ok button", screenName);

	}
	/**
	 * @author A-9847
	 * @Desc To uncheck checkboxes if checked
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void uncheckingAllCheckboxesIfChecked() throws InterruptedException, IOException{

		try{
			//GivenSecurityStatusAccepted
			String locatorValue=xls_Read.getCellValue(sheetName, "chk_givenSecurityStatusAccepted;name");
			moveScrollBar(driver.findElement(By.name(locatorValue)));

			if(driver.findElement(By.name(locatorValue)).isSelected())
				clickWebElement(sheetName, "chk_givenSecurityStatusAccepted;name","Given Security Status Accepted", screenName);	
			waitForSync(1);

			//SecurityDataReviewed
			String locatorValue1=xls_Read.getCellValue(sheetName, "chk_SecurityDataRcvd;xpath");
			if(driver.findElement(By.xpath(locatorValue1)).isSelected())
				clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath","Security Data Reviewed Checkbox", screenName);
			waitForSync(1);

			//NewSecurityStatusGiven
			String locatorValue2=xls_Read.getCellValue(sheetName, "chk_NewSecurityStatusRcvd;xpath"); 	
			if(driver.findElement(By.xpath(locatorValue2)).isSelected())	
				clickWebElement(sheetName, "chk_NewSecurityStatusRcvd;xpath","New Security Status Given Checkbox", screenName);
			waitForSync(1);
		}

		catch(Exception e){
			writeExtent("Fail","Failed to uncheck the checkboxes on "+screenName);


		}

	}
	/**
	 * Description... Verify Shipment Details Whether Editable
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyShipmentDetailsWhetherEditable() throws InterruptedException, IOException
	{
		String expectedVal="false";
		String actualResult="";

		//Expanding the shipment tab
		clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName+"_for editable property for pcs");

		//verifying pcs field
		actualResult=getAttributeUsingJavascript(sheetName, "htmlDiv_pcs;xpath", "shipmentPcs",screenName, "isContentEditable");
		verifyScreenText(sheetName, expectedVal, actualResult, "Shipment pcs field editable", screenName);

		//verifying wt field
		actualResult=getAttributeUsingJavascript(sheetName, "htmlDiv_wt;xpath", "shipmentWt",screenName, "isContentEditable");
		verifyScreenText(sheetName, expectedVal, actualResult, "Shipment wt field editable", screenName);

		//verifying shipment field
		actualResult=getAttributeUsingJavascript(sheetName, "htmlDiv_shipmentDesc;xpath", "shipmentDesc",screenName, "isContentEditable");
		verifyScreenText(sheetName, expectedVal, actualResult, "Shipment desc field editable", screenName);

	}
	
	
	/**
	 * Description... Verify Shipment Description
	 * @param scc
	 * @throws InterruptedException
	 */
	public void verifyShipmentDescription(String scc) throws InterruptedException
	{
		String sccActual=getElementText(sheetName, "htmlDiv_shipmentDesc;xpath",
				"Shipment description", screenName);

		verifyScreenText(sheetName, data(scc), sccActual, "Shipment Description", screenName);
	}
	

/**
	 * @author A-9847
	 * @Desc To enter the screening details along with SU 
	 * @param su
	 * @param screeningMethod
	 * @param pcs
	 * @param wt
	 * @param result
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterScreeningDetailsWithSU(String su,String screeningMethod,String pcs,String wt,String result) throws InterruptedException, IOException
	{
		//Screening methods
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_screenMethod;xpath",	data(screeningMethod), "Screening method","VisibleText");
		waitForSync(2);

		//Pcs
		enterValueInTextbox(sheetName, "inbx_pcsToScreen;xpath", data(pcs), "Pieces", screenName);	

		//wt
		enterValueInTextbox(sheetName, "inbx_wtToScreen;xpath", data(wt), "Weight", screenName);
		waitForSync(2);
		
		//SU
		enterValueInTextbox(sheetName, "inbx_su;id", data(su), "SU", screenName);
		waitForSync(2);
		
		//Select Result
		selectValueInDropdown(sheetName, "lst_screenResult;xpath",	data(result), "Screening result","VisibleText");
		waitForSync(2);
		//Click add button

		clickWebElement(sheetName, "btn_addScreeningDetails;xpath","Add button", screenName);
	}
	

	/**
	 * Description... Verify Agent Details
	 * @param agentType
	 * @param countryCode
	 * @param agentId
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAgentDetails(String agentType,String countryCode,String agentId) throws InterruptedException, IOException
	{
		String locator = xls_Read.getCellValue(sheetName, "htmlDiv_agentType;xpath");
		if(driver.findElements(By.xpath(locator)).size()==0) {
			//Expanding the agent tab
			clickWebElement(sheetName, "tab_agentDetails;xpath", "Agent details", screenName);
		}

		//AgentType
		String agentTypeActual=getElementText(sheetName, "htmlDiv_agentType;xpath",
				"Agent Type", screenName);

		verifyScreenText(sheetName, data(agentType), agentTypeActual, "Agent Type", screenName);

		//country code
		String countryCodeActual=getElementText(sheetName, "htmlDiv_isoCountryCode;xpath",
				"Country code", screenName);

		verifyScreenText(sheetName, data(countryCode), countryCodeActual, "Country Code", screenName);

		//Agent id
		String agentIdActual=getElementText(sheetName, "htmlDiv_agentId;xpath",
				"Agent Id", screenName);

		verifyScreenText(sheetName, data(agentId), agentIdActual, "Agent Id", screenName);


	}
	/**
	 * Description... Verify screening method not auto populated
	 * @author A-9844
	 * @param screeningmethod
	 * @throws InterruptedException
	 */
	public void verifyScreeningMethodNotAutopopulated() throws InterruptedException
	{
		String locator = xls_Read.getCellValue(sheetName, "txt_screeningDetailsNotpopulated");

		if(driver.findElements(By.xpath(locator)).size()==0) {
			writeExtent("Pass","Screening details are not auto populated on "+screenName);
		} 
		else 
		{
			writeExtent("Fail","Screening details are auto populated on "+screenName);
		}
	}






/**
	 * @author A-9844
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
	 * Description... Check Security Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void chkSecurityDetails() throws InterruptedException, IOException
	{

		//check security data reviewed check box
		clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath","Security Checkbox", screenName);

		//check new securityStatusGiven check box
		clickWebElement(sheetName, "chk_newSecurityStatus;xpath","New security status given", screenName);

	}

	
	/**
	 * Description... check security data reviewed check box
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void chkSecurityDataReviewed() throws InterruptedException, IOException
	{

		 //COMMENTED AS PART OF NSC AUTOSTAMPING RULE
		/****clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath","Security Checkbox", screenName);***/

	}
	/**
	 * Description... check security data reviewed check box
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void checkSecurityDataReviewed() throws InterruptedException, IOException
	{

		String locatorValue=xls_Read.getCellValue(sheetName, "chk_SecurityDataRcvd;xpath");
		 moveScrollBar(driver.findElement(By.xpath(locatorValue)));
		 
		if(!driver.findElement(By.xpath(locatorValue)).isSelected())
			clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath","Security Data Reviewed Checkbox", screenName);
		waitForSync(1);


	}
	
	/**
	 * Description... Security And Screening ModifySCC
	 * @param SecSCC
	 * @throws Exception
	 */
	public void editSCC(String SCC) throws Exception
	{                             
		String locator = xls_Read.getCellValue("CaptureAWB_OPR026", "btn_editSCC;xpath");
		//Expanding the shipment tab
		if(!driver.findElement(By.xpath(locator)).isDisplayed())
		{
			clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName+"_for editable property for pcs");
		}                       
		clickWebElement("CaptureAWB_OPR026", "btn_editSCC;xpath", "Edit SCC Button", screenName);
		waitForSync(2);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		waitForSync(5);
		ele.sendKeys(data(SCC));            
		waitForSync(2);
		clickWebElement("CaptureAWB_OPR026", "btn_updateSCCok;name", "OK Button ", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_OK;xpath","Save button", screenName);        
		switchToFrame("default");
		if(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
		{
			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(2);
		}


		String expectedMsg="Screening details saved succesfully";

		String actualMsg=getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath","Confirmation Message", screenName);

		verifyScreenText(sheetName, expectedMsg, actualMsg, "Confirmation Msg", screenName);
		waitForSync(5);
		clickWebElement("Generic_Elements", "btn_OK;xpath","Ok button", screenName);

	}


	/**
	 * Description... Verify Airport
	 * @param airport
	 * @throws InterruptedException
	 */
	public void verifyAirport(String airport) throws InterruptedException
	{
		String airportActual=getAttributeWebElement(sheetName, "inbx_airport;xpath","Airport","value", screenName);
		verifyScreenText(sheetName, data(airport), airportActual, "Airport", screenName);

	}
	
	
	/**
	 * Description... Enter Screening Details
	 * @param screeningMethod
	 * @param pcs
	 * @param wt
	 * @param result
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterScreeningDetails(String screeningMethod,String pcs,String wt,String result) throws InterruptedException, IOException
	{
		//Screening methods
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_screenMethod;xpath",	data(screeningMethod), "Screening method","VisibleText");
		waitForSync(2);

		//Pcs
		enterValueInTextbox(sheetName, "inbx_pcsToScreen;xpath", data(pcs), "Pieces", screenName);	

		//wt
		enterValueInTextbox(sheetName, "inbx_wtToScreen;xpath", data(wt), "Weight", screenName);
		waitForSync(2);
		//Select Result
		selectValueInDropdown(sheetName, "lst_screenResult;xpath",	data(result), "Screening result","VisibleText");
		waitForSync(2);
		//Click add button

		clickWebElement(sheetName, "btn_addScreeningDetails;xpath","Add button", screenName);
	}
	
	
	/**
	 * Description... Security And Screening ModifySCC
	 * @param SecSCC
	 * @throws Exception
	 */
	public void securityAndScreeingModifySCC(String SecSCC) throws Exception {
		screenName = "Security and Screening Pop up";

		Thread.sleep(2000);

		//Expanding the shipment tab
		clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName+"_for editable property for pcs");


		clickWebElement("CaptureAWB_OPR026", "btn_editSCC;xpath", "Edit SCC Button", screenName);
		Thread.sleep(2000);
		ele = driver.findElement(By.xpath("//input[@name='newScc']"));
		ele.click();
		Thread.sleep(1000);
		enterValueInTextbox("CaptureAWB_OPR026", "inbx_newSCC;xpath", data(SecSCC), "SecSCC", screenName);
		clickWebElement("CaptureAWB_OPR026", "btn_updateSCCok;name", "OK Button ", screenName);
		Thread.sleep(2000);

		clickWebElement("Generic_Elements", "btn_save;name", "Save Button", screenName);
		Thread.sleep(2000);

		String actText = getElementText("CaptureAWB_OPR026", "htmlDiv_errorMsgSec;xpath",
				"errorMsg", screenName);
		String expText = "AWB is executed. Only Special SCCs can be modified.";

		verifyScreenText("CaptureAWB_OPR026", expText, actText, "SCC modify in security & screening", screenName);
		Thread.sleep(2000);


	}
	
	
	/**
	 * Description... Click Yes Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYesButton() throws InterruptedException, IOException
	{
		switchToFrame("default");
		waitForSync(5);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		waitForSync(2);
		switchToFrame("contentFrame", "OPR339");
	}
	/**
	 * Description... Click Yes Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYesButton(String screenId) throws InterruptedException, IOException
	{
		switchToFrame("default");
		waitForSync(5);
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		waitForSync(2);
		switchToFrame("contentFrame", screenId);
	}
	/**
	 * Description... Click Yes Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYesButton(String screenid,String frameName) throws InterruptedException, IOException
    {
           switchToFrame("default");
           waitForSync(5);
           clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
           waitForSync(2);
           switchToFrame("contentFrame", screenid);
           switchToFrame("frameName", frameName);
    }

	/**
	 * Description... Save Security Details
	 * @throws Exception 
	 */
	public void saveSecurityDetails() throws Exception
	{
		addAgentDetails(getPropertyValue(proppath, "testEnv"));
		
		/***********************/
	    checkSecurityChkBoxes();
	    /***********************/
	    
	    
        clickWebElement(sheetName, "btn_OK;xpath","Save button", screenName);
        waitForSync(3);
        switchToFrame("default");
        
   /******   while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
      {
            clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
            waitForSync(3);
      }*****/
      

     int counter=1;
        
        while(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "btn_saveSecurityDetailsOK;xpath"))).size()==0 && counter<=5)
        {      

               try{
                     waitForSync(1);     
                     driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Yes;xpath"))).click();
                     waitForSync(3);     

               }
               catch(Exception e){

                     counter=counter+1;
                     
               } 
        
        }
      
        String expectedMsg="Screening details saved succesfully";

        String actualMsg=getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath","Confirmation Message", screenName);

        verifyScreenText(sheetName, expectedMsg, actualMsg, "Confirmation Msg", screenName);

        clickWebElement("Generic_Elements", "btn_OK;xpath","Ok button", screenName);




	}
	public void addAgentDetails(String testEnv) throws InterruptedException, Exception{
		
		if(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "htmlDiv_agentType;xpath"))).size()==0)
		{
			
			/*****map.put("RegulatedAgentCode", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_Code"));
			map.put("AgentCountryId", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryId"));
			map.put("AgentType", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Type"));
			map.put("Expiry", WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Expiry"));
		    addAgentDetails("AgentType","AgentCountryId","RegulatedAgentCode","Expiry","OPR335","if11");****/
		    
			//Expanding the agent tab
			clickWebElement(sheetName, "tab_agentDetails;xpath", "Agent details", screenName);

			//Click on Add button for adding details
			clickWebElement(sheetName, "btn_addAgent;xpath", "Click Add button", screenName);
			

			if(testEnv.equals("RCT"))
			{
				//AgentType
				selectValueInDropdown(sheetName, "lst_agentType;xpath", data("AgentType"), "Agent Type", "VisibleText");

				//Country Code 
				enterValueInTextbox(sheetName, "inbx_countryCode;xpath", data("AgentCountryId"), "Country Code", screenName);

				//Agent ID
				enterValueInTextbox(sheetName, "inbx_agentId;xpath", data("RegulatedAgentCode"), "Agent ID", screenName);

				//Expiry
				enterValueInTextbox(sheetName, "inbx_expiry;xpath", data("Expiry"), "expiry", screenName);
			}

			else
			{

				String station=getLoggedInStation("OPR339"); 
				String agentType="";
				String agentCountryId="";
				String agentId="";
				String expiry="";

				if(station.contains("CDG"))
				{
					agentId= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB");
					agentCountryId= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB");
					agentType= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_TypeHUB");
					expiry= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB");
				}
				else
				{
					agentId= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB_NL");
					agentCountryId= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB_NL");
					agentType= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_TypeHUB_NL");
					expiry= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB_NL");
				}
				//AgentType
				selectValueInDropdown(sheetName, "lst_agentType;xpath", agentType, "Agent Type", "VisibleText");

				//Country Code 
				enterValueInTextbox(sheetName, "inbx_countryCode;xpath", agentCountryId, "Country Code", screenName);

				//Agent ID
				enterValueInTextbox(sheetName, "inbx_agentId;xpath", agentId, "Agent ID", screenName);

				//Expiry
				enterValueInTextbox(sheetName, "inbx_expiry;xpath", expiry, "expiry", screenName);
			}

			//Add 
			clickWebElement(sheetName, "btn_okAgentAdd;xpath", "Add agent Details", screenName);
			waitForSync(6);
		 
		}

	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : check security and screening related checkboxes
	 */
	public void checkSecurityChkBoxes() throws InterruptedException, IOException
	{
		
		String loggedInStation="";
		String airport= WebFunctions.getPropertyValue(airportproppath, "RA-ID_Enabled");
		String pageSource=driver.getTitle();
		

		if(pageSource.contains("Security and Screening"))
		{
			loggedInStation=getLoggedInStation("OPR339");
		}
		else if(pageSource.contains("Goods Acceptance"))
		{
			loggedInStation=getLoggedInStation("OPR335");
			 switchToFrame("frameName", "if11");
		}
		else if(pageSource.contains("Capture AWB"))
		{
			loggedInStation=getLoggedInStation("OPR026");
			 switchToFrame("frameName", "popupContainerFrame");
		}
		
		
       
		

		if(airport.contains(loggedInStation))
		{
			checkGivenSecurityStatusAccepted();
		}
		else
		{
			checkNewSecurityStatusGiven();
			checkSecurityDataReviewed();
		
			
		}

	}
		
		
		
		
	/**
	 * Description... save security details 
 * @author A-10330
	 * @throws Exception 
	 */
	
	public void OkButtonAfterScreeningSave() throws Exception
	{
		
	   addAgentDetails(getPropertyValue(proppath, "testEnv"));
	 
	   
	  
         /***********************************************/
	         checkSecurityChkBoxes();
	         
	       /***********************************************/
	   
	  
	   
	   
		javaScriptToclickElement(sheetName, "btn_OK;xpath","Ok button", screenName);
		waitForSync(3);
		switchToFrame("default");
		
		while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
		{
			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(3);
		}
    }

	/**
	 * Description... Save Security Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void saveSecurityDetailsAfterDataReview() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_OK;xpath","Save button", screenName);

		switchToFrame("default");


		if(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
		{
			clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
			waitForSync(2);
		}

		String expectedMsg="Screening details saved succesfully";

		String actualMsg=getElementText("Generic_Elements", "htmlDiv_confirmMsg;xpath","Confirmation Message", screenName);

		verifyScreenText(sheetName, expectedMsg, actualMsg, "Confirmation Msg", screenName);

		//Click OK
		clickWebElement("Generic_Elements", "btn_OK;xpath","Ok button", screenName);


	}
	
	
	/**
	 * Description... Check Scc Existence
	 * @param SPLCode
	 */
	public void checkSccExistence(String SPLCode){
		try{

			clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName);

			String expScc=data(SPLCode);
			waitForSync(5);
			String actScc=getElementText(sheetName,"link_shipmentSCC;xpath", "Shipment SCC", screenName);
			verifyValueOnPageContains(actScc, expScc, "Check SEC","Security And Screening","Check SEC in Screening");
			waitForSync(5);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Description..check new securityStatusGiven check box
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void newSecurityStatusGiven() throws InterruptedException, IOException
	{

		clickWebElement(sheetName, "chk_newSecurityStatus;xpath","New security status given", screenName);

	}
	
	
	/**
	 * Description... Update SCC First
	 * @param SecSCC
	 * @throws Exception
	 */
	public void updateSCCFirst(String SecSCC) throws Exception {
		switchToFrame("default");
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		switchToFrame("contentFrame", "OPR339");
		waitForSync(1);
		clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath",
				"Security Checkbox", screenName);
		clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_SCC;xpath", data(SecSCC),"SecSCC", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
		waitForSync(2);
		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
		waitForSync(2);

	}
	
	
	/**
	 * Description... Delete SCC
	 * @param SCC
	 * @throws Exception
	 */
	public void deleteSCC(String SCC) throws Exception
	{
		waitForSync(2);
		clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);
		waitForSync(2);
		driver.findElement(By.xpath("//span[contains(text(),'"+SCC+"')]//i")).click();
		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
		Thread.sleep(3000);
		switchToFrame("default");
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR339");

	}


}