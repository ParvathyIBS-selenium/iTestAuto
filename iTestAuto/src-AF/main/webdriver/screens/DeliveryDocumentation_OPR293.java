package screens;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.AWTException;
import java.io.IOException;
import java.util.*;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class DeliveryDocumentation_OPR293 extends CustomFunctions{
	public DeliveryDocumentation_OPR293(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="DeliveryDocumentation_OPR293";
	public String ScreenName="DeliveryDocumentation";
	//public CustomFunctions comm;
	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);

	public void enterCustName() throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_custCode;name", data("CustomerCode"), "Customer Code", ScreenName);
	}
	/**
	 * Description... Verify Deliver ID
	 * @throws InterruptedException
	 */
	public void verifyDeliverID() throws InterruptedException{
		String deliveryID=getElementText(sheetName, "tab_deliveryID;xpath", "Delivery ID", ScreenName);
		verifyValueNotNull(deliveryID, "Delivery ID");
	}
	/**
	 * Description... Verify AWBNo
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAWBNo() throws InterruptedException, IOException{
		String actAWBNo=getElementText(sheetName, "lnk_awbNo;xpath", "AWB Number", ScreenName);
		verifyValueOnPageContains(actAWBNo, data("AWBNo"), "Verify AWB No is displayed", ScreenName, "AWB Number");
	}
	/**
	 * @author A-9847
	 * @Desc To verify the Customer Code as Blank in case of shipments having different consignees
	 * @throws InterruptedException
	 */
	public void verifyCustomerCode() throws InterruptedException{
		try {
		
			String locator=xls_Read.getCellValue(sheetName, "inbx_custCode;name");
			WebElement ele=driver.findElement(By.name(locator));
			String actText=getAttributeWebElement(ele,"Customer Code", "value", ScreenName);
			System.out.println(actText);
			
			if(actText.isEmpty())
				writeExtent("Pass", "Successfully verified the Customer code as Blank on "+ScreenName );
			else
				writeExtent("Fail", "Failed to verify the Customer code as Blank since it is populated as "+actText+" on "+ScreenName);
			waitForSync(2);	
					

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the Customer Code on "+ScreenName);
		}
	}
	/**
	 * @author A-9844
	 * Description... To verify and enter consignee code
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyAndEnterConsigneeCode() throws InterruptedException, AWTException {
		By element = getElement(sheetName, "inbx_CustomerName;id");

		String actConsigneeCode = driver.findElement(element).getAttribute("value");
		if(actConsigneeCode.equals(data("ConsigneeCode"))){

			writeExtent("Pass", "Consignee code is displayed correctly on "+ScreenName);
		}

		else{
			writeExtent("Info", "Consignee code is displayed as "+ actConsigneeCode+" on "+ScreenName);
			enterValueInTextbox(sheetName,"inbx_CustomerName;id",data("ConsigneeCode"),"Consignee Code", ScreenName);
                  keyPress("TAB");
 			waitForSync(3);
		}



	}

	/**
	 * @author A-9847
	 * @Desc To verify the Customer Code as Blank in case of shipments having different consignees
	 * @throws InterruptedException
	 */
	public void verifyCustomerCode(String customerCode) throws InterruptedException{
		try {
		
			String locator=xls_Read.getCellValue(sheetName, "inbx_custCode;name");
			WebElement ele=driver.findElement(By.name(locator));
			String actText=getAttributeWebElement(ele,"Customer Code", "value", ScreenName);
			System.out.println(actText);
			
			if(actText.equals(data(customerCode)))
				writeExtent("Pass", "Successfully verified the Customer code as "+data(customerCode)+ "on ScreenName" );
			else
				writeExtent("Fail", "Failed to verify the Customer code as "+data(customerCode)+ "on ScreenName . Its displaying as "+actText );
			waitForSync(2);	
					

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the Customer Code on "+ScreenName);
		}
	}

/**
	 * @author A-9847
	 * Description... Enters the given Customer Code and Name
	 * @param customerName
	 * @throws InterruptedException
	 */
	
	public void enterCustomerCodeandName(String customerName,String customerCode) throws InterruptedException{
		
		clearText(sheetName, "inbx_custCode;name", "Customer Code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_custCode;name", customerCode, "Customer Code", ScreenName);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_customerName;name", customerName, "Customer Name", ScreenName);
		

	}


/**
	 * @author A-9847
	 * @Desc To capture the handoverdetails of specific awbs given and to verify the handpver tickmark
	 * @param awbs
	 * @param custNames
	 */
	
	public void selectAwbandCaptureHandOverDetails(String awbs[],String custNames[]){
		
		try{
			for(int i=0;i<awbs.length;i++){
				
			String locator = xls_Read.getCellValue(sheetName, "chkbox_awbNo;xpath");
			locator=locator.replace("*",data(awbs[i]));
			driver.findElement(By.xpath(locator)).click();
			
			//capture handover details
			clickButtonSwitchWindow(sheetName, "btn_HandoverDetials;name", ScreenName, "Capture HandOver Details Buttom");
			enterValueInTextbox(sheetName, "inbx_handOverTo;name", data(custNames[i]), "HandOver To", ScreenName);
			enterValueInTextbox(sheetName, "inbx_remarksPopup;name", data("Remarks"), "Remarks", ScreenName);                   
			clickButtonSwitchtoParentWindow("Generic_Elements", "btn_save;xpath", "Save Button", ScreenName);
			switchToDefaultAndContentFrame("OPR293");
			
			//Verify HandOver Tick
			verifyHandoverTickMark(awbs[i]);
	
			waitForSync(2);
			writeExtent("Pass", "Successfully captured the Handover Details for "+data(awbs[i])+" on "+ScreenName);
			}
		}
		catch(Exception e){
			writeExtent("Fail", " Failed to Capture the Handover Details  on "+ScreenName);
		}
	}
	


/**
	 * @author A-9847
	 * @Desc To verify the DN status of the specific Awbs given
	 * @param status
	 * @param awb
	 * @throws InterruptedException
	 */
	public void verifyDNStatusOfAWB(String status[],String awb[]) throws InterruptedException
	{
		
		try{
		for(int i=0;i<awb.length;i++){
		String locator = xls_Read.getCellValue(sheetName, "txt_awbDNStatus;xpath").replace("*",data(awb[i]));
		String DeliveryId= driver.findElement(By.xpath(locator)).getText();
		boolean deliveryStatus = DeliveryId.contains(status[i]);
		verifyValueOnPage(true, deliveryStatus, "Verify DN Status", ScreenName, "DN status");
		
		if(deliveryStatus)
			writeExtent("Pass","Returned the DN Details as "+ DeliveryId +" on "+ScreenName);
		}
		}
		catch(Exception e){
			writeExtent("Fail","Failed to verify the DN Status on "+ScreenName);	
			
		}

	}


	/**
	 * 
	 * @param codeCharge
	 * @param charge
	 * @throws Exception
	 */
	private void addOthercharges(String codeCharge,Double charge) throws Exception {
		/**Adding charge code details**/
		clickWebElement(sheetName,"chk_checkAllGeneratePayment;xpath","Check all", ScreenName); 
		clickWebElement(sheetName,"btn_addChargeCode;id","Add Charge Code button", ScreenName);
		waitForSync(2);
		switchToWindow("storeFirstChild");
		switchToWindow("childWindow2");

		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_chargecode;name", data(codeCharge), "Charge Code", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName,"btn_chargeCodeList;id","List Charge Code", ScreenName);
		waitForSync(3);
		
		if(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
		{
			clickWebElement("Generic_Elements","btn_yes;xpath","Yes Button", ScreenName);
			waitForSync(4);
		}
		
		clickWebElement(sheetName,"chk_actualCharge;id","Actual Charge ", ScreenName);
		enterValueInTextbox(sheetName,"inbx_actualCharge;id",charge.toString(), "Actual Charge", ScreenName);	
		
		clickWebElement(sheetName,"btn_OkGPScreen;id","OK Button", ScreenName);
		if(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
		{
			clickWebElement("Generic_Elements","btn_yes;xpath","Yes Button", ScreenName);
			waitForSync(3);
		}
		switchToWindow("getFirstChild");
		waitForSync(3);

		/*****/

	}

	/**
	 * 
	 * @param Remarks
	 * @param paymentType
	 * @param otherCharges
	 * @param codeCharge
	 * @param charge
	 * @throws Exception
	 * Desc : generate delivery id with the manual addition of charges
	 */
	public void generateDeliveryIDforPartialPieces(String Remarks,String paymentType,boolean otherCharges,String codeCharge,Double charge)throws Exception{

verifyAndEnterConsigneeCode();
		
		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
		String station=getLoggedInStation("OPR293");
		if(paymentType.equals("CASH")&&station.equals("IAD"))
		{
			paymentType="PAYCARGO";
		}

		switchToWindow("storeParent");
		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(6);
		boolean msgExists=false;
		try
		{
			switchToFrame("default");
			waitForSync(5);
			String locator = xls_Read.getCellValue(sheetName, "txt_warningMessage;xpath");
			String actText=driver.findElement(By.xpath(locator)).getText();

			if(actText.contains("issue DN for all Pending Pieces?"))
			{
				handleAlert("Dismiss","DeliveryDocumentation");
				msgExists=true;
			}
			else
			{
				handleAlert("Accept","DeliveryDocumentation");
			}
			waitForSync(3);

			switchToFrame("contentFrame", "OPR293");
		}
		catch(Exception e){}

		/************************* VERIFICATION OF PARTIAL PCS DELIVERY MESSAGE*****************/
		if(msgExists)
		{
			writeExtent("Pass","Message '"+"issue DN for all Pending Pieces?'" +" is triggered");
		}
		else
		{
			writeExtent("Fail","Message '"+"issue DN for all Pending Pieces?'" +" is not triggered");
		}
		waitForSync(3);
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(2);
		waitForSync(2);
		if(otherCharges)
			addOthercharges(codeCharge,charge);

		if(paymentType.equals("CASH"))
		{
			clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

		}
		else if(paymentType.equals("CREDIT"))
		{
			clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);

		}

		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
		else if(paymentType.equals("PAYCARGO"))         

		{           
			try{
				List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));

				for(WebElement chkBox:ele)
					chkBox.click();

				clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);

			}
			catch(Exception e)
			{
				writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
			}

		}
		/** END  **/


		waitForSync(2); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 
		clickWebElement(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");

		switchToDefaultAndContentFrame("OPR293");

	}
	
	/**
	 * 
	 * @param codeCharge
	 * @param Remarks
	 * @param paymentType
	 * @param otherCharges
	 * @param charge
	 * @throws Exception
	 * Desc : generate delivery id with manual additional of charges
	 */
	public void generateDeliveryIDWithOthercharges(String codeCharge,String Remarks,String paymentType,boolean otherCharges,Double charge)throws Exception
	{
		

verifyAndEnterConsigneeCode();
		
		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
		String station=getLoggedInStation("OPR293");
		if(paymentType.equals("CASH")&& station.equals("IAD"))
		{
			paymentType="PAYCARGO";
		}


		try{

			clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
			waitForSync(5);

			switchToFrame("default");
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				handleAlert("Accept","DeliveryDocumentation");
				waitForSync(4);
			}
		}
		catch(Exception e){}
		finally{switchToFrame("contentFrame", "OPR293");}
		waitForSync(1);
		switchToWindow("storeParent");
		waitForSync(1);
		switchToWindow("child");
		waitForSync(1);
		if(otherCharges)
			addOthercharges(codeCharge,charge);


		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(1);
		if(paymentType.equals("CASH"))
		{
			clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

		}
		else if(paymentType.equals("CREDIT"))
		{
			clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);

		}


		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
		else if(paymentType.equals("PAYCARGO"))         

		{           
			try{
				List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));

				for(WebElement chkBox:ele)
					chkBox.click();

				clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);

			}
			catch(Exception e)
			{
				writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
			}

		}		/**  END   **/

		waitForSync(2); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 

		clickWebElement(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR293");
	}
	/**
	 * Desc : Verifying ready for delivery tick mark
	 * @author A-9844
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyReadyForDelivery(String awbNo) throws InterruptedException, AWTException {

		try{
			String locator = xls_Read.getCellValue(sheetName, "lbl_readyForDeliveryTickMark;xpath");
			locator=locator.replace("AWB",data(awbNo));
			driver.findElement(By.xpath(locator)).isDisplayed();
			waitForSync(2);
			writeExtent("Pass", " Ready For Delivery Tick Displayed for "+data(awbNo)+" in "+ScreenName);
			}
		catch(Exception e){
			writeExtent("Fail", "  Ready For Delivery Tick Not Displayed In "+ScreenName);
		}
	}

	/**
	 * Description... Clicks on the Shipment Check Box
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickShipment() throws InterruptedException, IOException{

		clickWebElement(sheetName, "chk_subRowId;name", "Shipment select check box", ScreenName);
	
	}
	/**
	 * Description... Selects all the AWBs Listed
	 * @throws InterruptedException
	 */

	public void selectAllAWBs() throws InterruptedException{
		List list = returnListOfElements(sheetName, "chk_rowId;xpath");
		clickListOfElements(list, "All AWBs Check Box", ScreenName);


	}
    /**
	 *@author A-10330
	 *Desc:verification of generate payment details popup
	 */
	public void verifyGeneratePaymentDetailsPopup()
	{
		try
		{
		String locator = xls_Read.getCellValue(sheetName, "div_generatepaymentDetails_popup");
		WebElement elem=driver.findElement(By.xpath(locator));
		if(elem.isDisplayed())
		{
			writeExtent("Pass","successfully verifed Generate Payment Details Popup On "+ScreenName);
		}
		else{
			writeExtent("Fail"," Generate Payment Details Popup is not verified on "+ScreenName);
		}
		}catch(Exception e)
		{
			writeExtent("Fail","Failed to navigate Generate Payment Details popup on "+ScreenName);
		}
	}


	
	/**
	 * Desc : Entering Customer code
	 * @author A-9175
	 * @param customercode
	 * @throws InterruptedException
	 */
		public void enterCustName(String customercode) throws InterruptedException{
			enterValueInTextbox(sheetName, "inbx_custCode;name", data(customercode), "Customer Code", ScreenName);
			waitForSync(2);
			performKeyActions(sheetName, "inbx_custCode;name", "TAB", "Customer code", ScreenName);
			
		}

	/**
	 * @author A-9478
	 * Desc: Verify error message
	 */
	public void verifyErrorMessageText(String ExpErrorMsg) throws InterruptedException, IOException{
		String ActErrorMsg =getElementText(sheetName, "txt_ErrorMessage;xpath", "Error Msg", ScreenName);
		verifyValueOnPageContains(ActErrorMsg, ExpErrorMsg, "1. Verify Error Msg", ScreenName, "Error Msg Verification");

	}
	/**
	* @author A-9844
	* Description... retrieve delivery documentation details from table
	* @throws InterruptedException
	*/
	public String retrieveDeliveryDocumentationDetails(String awbno, String column)throws Exception

	{
	String tableEntry = "";
	try
	{

	String locator = xls_Read.getCellValue(sheetName, "txt_deliveryDocDetails;xpath");
	locator=locator.replace("awbNo",data(awbno));
	locator=locator.replace("colNo",column);
	WebElement entry=driver.findElement(By.xpath(locator));
    moveScrollBar(entry);
    tableEntry=entry.getText();

    tableEntry=driver.findElement(By.xpath(locator)).getText();
	System.out.println(tableEntry);
	writeExtent("Pass", "Successfully retrived the text " + tableEntry + " from " + ScreenName);
	}

	catch(Exception e)

	{
	writeExtent("Fail", "Couldn't retrieve the text " + tableEntry + " from " + ScreenName);

	}

	return tableEntry;

	}

	/**
	 * Desc : Generating Delivery Id for Partial Number of Pieces with or without Other charges
	 * @author A-9175
	 * @param Remarks
	 * @param paymentType
	 * @param otherCharges
	 * @param codeCharge
	 * @throws Exception
	 */
	public void generateDeliveryIDforPartialPieces(String Remarks,String paymentType,boolean otherCharges,String codeCharge)throws Exception{

		  /**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
		String station=getLoggedInStation("OPR293");
        if(paymentType.equals("CASH")&&station.equals("IAD"))
        {
              paymentType="PAYCARGO";
        }

		switchToWindow("storeParent");
		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(6);
		boolean msgExists=false;
		try
		{
			switchToFrame("default");
			waitForSync(5);
			String locator = xls_Read.getCellValue(sheetName, "txt_warningMessage;xpath");
			String actText=driver.findElement(By.xpath(locator)).getText();
			
			if(actText.contains("issue DN for all Pending Pieces?"))
			{
				handleAlert("Dismiss","DeliveryDocumentation");
				msgExists=true;
			}
			else
			{
				handleAlert("Accept","DeliveryDocumentation");
			}
			waitForSync(3);
		
			switchToFrame("contentFrame", "OPR293");
		}
		catch(Exception e){}
		
		/************************* VERIFICATION OF PARTIAL PCS DELIVERY MESSAGE*****************/
		if(msgExists)
		{
			writeExtent("Pass","Message '"+"issue DN for all Pending Pieces?'" +" is triggered");
		}
		else
		{
			writeExtent("Fail","Message '"+"issue DN for all Pending Pieces?'" +" is not triggered");
		}
		waitForSync(3);
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(2);
		waitForSync(2);
		if(otherCharges)
			addOthercharges(codeCharge);

		if(paymentType.equals("CASH"))
		{
			clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

		}
		else if(paymentType.equals("CREDIT"))
		{
			clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);

		}
		
		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
		else if(paymentType.equals("PAYCARGO"))         
    
		{           
			try{
				List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));
              
              for(WebElement chkBox:ele)
                    chkBox.click();
              
             clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);
             
			}
			catch(Exception e)
			{
				writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
			}

        }
        	/** END  **/
			
		
		waitForSync(2); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 
		clickWebElement(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");

		switchToDefaultAndContentFrame("OPR293");

	}

	/**
	 * @author A-9478
	 * Description... Get DN ID
	 * @param Remarks
	 * @throws Exception
	 */

	public String getDNID()throws Exception
	{
		String DNID = new String();
		try
		{
			String ele = xls_Read.getCellValue(sheetName, "txt_DNId;xpath");
			DNID = driver.findElement(By.xpath(ele)).getText();
			DNID = (DNID.split("\\["))[0];
			writeExtent("Pass", "Successfully fetched DN Id " + DNID + " from " + ScreenName + " Page");

		}
		catch(Exception e)
		{
			writeExtent("Pass", "Couldn't fetched DN Id " + DNID + " from " + ScreenName + " Page");
		}
		return DNID;      
	}

	/**
	 * List with ULD Number
	 * @author A-9175
	 * @param uldno
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listWithUld(String uldno) throws InterruptedException, IOException{

		enterValueInTextbox(sheetName, "inbx_uldnum;id", data(uldno), "ULD No", ScreenName);
		waitForSync(3);
		
			String locator = xls_Read.getCellValue("DeliveryDocumentation_OPR293", "chkbox_pendingDeliveryId");
			if(driver.findElement(By.name(locator)).isSelected())
			{
				driver.findElement(By.name(locator)).click();
				waitForSync(1);
			}
		
		clickWebElement(sheetName, "btn_List;xpath", "List Button", ScreenName);
	}

	/**
	 * Desc : Verifying customer code
	 * @author A-9175
	 * @param custcode
	 * @throws InterruptedException
	 */
	public void verifyCustCode(String custcode) throws InterruptedException{
		try {
			String locator=xls_Read.getCellValue(sheetName, "inbx_custCode;name");
			WebElement ele=driver.findElement(By.name(locator));
			String actText=getAttributeWebElement(ele,"Customer Code", "value", ScreenName);

			if(actText.equals(data(custcode)))
				writeExtent("Pass", "Customer code Autopopulated as "+data(custcode));
			else
				writeExtent("Fail", "Customer code not Autopopulated as "+data(custcode));
			waitForSync(2);		

		} catch (Exception e) {
			writeExtent("Fail", "Customer code not Autopopulated as "+data(custcode));
		}
	}
	/**
	 * Desc : Generating Delivery Id for Partial Number of Pieces with or without Other charges with FOP
	 * @author A-9175
	 * @param Remarks
	 * @param paymentType
	 * @param otherCharges
* @param codeCharge
	 * @throws Exception
	 */
	public void generateDeliveryIDforPartialPiecesWithFop(String Remarks,String paymentType,boolean otherCharges,String codeCharge,boolean fop)throws Exception{

		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
		String station=getLoggedInStation("OPR293");
		if(paymentType.equals("CASH")&&station.equals("IAD"))
		{
			paymentType="PAYCARGO";
		}

		switchToWindow("storeParent");
		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(6);
		boolean msgExists=false;
		try
		{
			switchToFrame("default");
			waitForSync(5);
			String locator = xls_Read.getCellValue(sheetName, "txt_warningMessage;xpath");
			String actText=driver.findElement(By.xpath(locator)).getText();

			if(actText.contains("issue DN for all Pending Pieces?"))
			{
				handleAlert("Dismiss","DeliveryDocumentation");
				msgExists=true;
			}
			else
			{
				handleAlert("Accept","DeliveryDocumentation");
			}
			waitForSync(3);

			switchToFrame("contentFrame", "OPR293");
		}
		catch(Exception e){}

		/************************* VERIFICATION OF PARTIAL PCS DELIVERY MESSAGE*****************/
		if(msgExists)
		{
			writeExtent("Pass","Message '"+"issue DN for all Pending Pieces?'" +" is triggered");
		}
		else
		{
			writeExtent("Fail","Message '"+"issue DN for all Pending Pieces?'" +" is not triggered");
		}
		waitForSync(3);
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(4);
		if(otherCharges)
			addOthercharges(codeCharge);
		checkOrUncheckFOP(fop);
		if(paymentType.equals("CASH"))
		{
			clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

		}
		else if(paymentType.equals("CREDIT"))
		{
			clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);
			waitForSync(3);	

		}

		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
		else if(paymentType.equals("PAYCARGO"))         

		{           
			try{
				List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));

				for(WebElement chkBox:ele)
					chkBox.click();

				clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);

			}
			catch(Exception e)
			{
				writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
			}

		}
		/** END  **/


		if(driver.findElements(By.id(xls_Read.getCellValue(sheetName, "inbx_paymentAttribute;id"))).size()==1)
		{
			waitForSync(2); 
			enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
			waitForSync(2); 
		}
		waitForSync(2);	
		clickWebElement(sheetName, "btn_ok;xpath", "Add button", ScreenName);
		waitForSync(3);	

		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");

		switchToDefaultAndContentFrame("OPR293");

	}



		
	


	/**
	 * Desc : Verifying Handover tick mark
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void verifyHandoverTickMark(String awbNo) throws InterruptedException, AWTException {

		try{
			String locator = xls_Read.getCellValue(sheetName, "lbl_dochandoverTickMark;xpath");
			locator=locator.replace("AWB",data(awbNo));
			driver.findElement(By.xpath(locator)).isDisplayed();
			waitForSync(2);
			writeExtent("Pass", " Hand Over Tick Displayed In "+ScreenName);}
		catch(Exception e){
			writeExtent("Fail", " Hand Over Tick Not Displayed In "+ScreenName);
		}
	}

	/**
	 * Desc : verifying before payment
	 * @author A-9175
	 * @throws InterruptedException
	 */
	public void checkVerifyBeforePayment() throws InterruptedException{
		
		//As part of new requirement , this VP is not needed

		/*****String locator=xls_Read.getCellValue(sheetName, "chk_verifyBeforePayment;name");
		boolean val=driver.findElement(By.name(locator)).isSelected();
		if(val)
		{
			writeExtent("Pass", "Verify Before Payment  Selected");
		}
		else
		{
			driver.findElement(By.name(locator)).click();
			writeExtent("Fail", "Verify Before Payment Not Selected");
			waitForSync(2);	
		}*****/
	}
	/**
	 * Desc : verifying before payment
	 * @author A-9175
	 * @throws InterruptedException
	 */
	public void unChkVerifyBeforePayment() throws InterruptedException{
		
	

		String locator=xls_Read.getCellValue(sheetName, "chk_verifyBeforePayment;name");
		
		try
		{
		boolean val=driver.findElement(By.name(locator)).isSelected();
		if(val)
		
			driver.findElement(By.name(locator)).click();
		
		}
		
		catch(Exception e)
		{
			
		}
		
		
	}
	/**
	 * @author A-7271
	 * Desc : clickVerifyBeforePayment
	 */
	public void chkVerifyBeforePayment()
	{
		String locator=xls_Read.getCellValue(sheetName, "chk_verifyBeforePayment;name");
		boolean val=driver.findElement(By.name(locator)).isSelected();
		
		if(!val)
		{
			driver.findElement(By.name(locator)).click();
		}
	
	}

	/**
	 * Desc : checkOrUncheckFOP
	 * @author A-9175
	 * @param check	 */

public void checkOrUncheckFOP(boolean check)
{
	{
	    try{
		String locator = xls_Read.getCellValue(sheetName, "chkbox_fop;xpath");
		List<WebElement> fopCheckele=driver.findElements(By.xpath(locator));
		System.out.println(fopCheckele.size());
		if(check)
		{
			for(WebElement chkBox:fopCheckele)
			{
				if ( !chkBox.isSelected()) 
				{
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", chkBox);
					/****chkBox.click();****/
				}
			}
		}
		else
		{
			for(WebElement chkBox:fopCheckele)
			{
				if (chkBox.isSelected()) 
				{
					JavascriptExecutor executor = (JavascriptExecutor) driver;
					executor.executeScript("arguments[0].click();", chkBox);
					/*****chkBox.click();*******/
				}
			}
		}
	    }catch (Exception e) {
            writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
		}
		
	}

	}

public void verifyAndUpdateConsignee()
{
	
	//verify consignee.. if C1001 , change it to consignee (data("ConsigneeCode")
	
}
	/**
	 * Desc : Generate Delivery id with other charges
	 * @author A-9175
	 * @param codeCharge
	 * @param Remarks
	 * @param paymentType
	 * @param otherCharges
	 * @throws Exception
	 */
public void generateDeliveryIDWithOthercharges(String codeCharge,String Remarks,String paymentType,boolean otherCharges)throws Exception
{

	verifyAndUpdateConsignee();
	/**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
	String station=getLoggedInStation("OPR293");
	if(paymentType.equals("CASH")&& station.equals("IAD"))
	{
		paymentType="PAYCARGO";
	}


	try{

		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(5);

		switchToFrame("default");
		while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
		{
			handleAlert("Accept","DeliveryDocumentation");
			waitForSync(4);
		}
	}
	catch(Exception e){}
	finally{switchToFrame("contentFrame", "OPR293");}
	waitForSync(1);
	switchToWindow("storeParent");
	waitForSync(1);
	switchToWindow("child");
	waitForSync(1);
	verifyGeneratePaymentDetailsPopup();
	if(otherCharges)
		addOthercharges(codeCharge);


	enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
	waitForSync(1);
	if(paymentType.equals("CASH"))
	{
		clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

	}
	else if(paymentType.equals("CREDIT"))
	{
		clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);

	}


	/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
	else if(paymentType.equals("PAYCARGO"))         

	{           
		try{
			List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));

			for(WebElement chkBox:ele)
				chkBox.click();

			clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);

		}
		catch(Exception e)
		{
			writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
		}

	}
	else if(paymentType.equals("TRANSFERT"))     
	{

		clickWebElementByWebDriver(sheetName, "btn_transfert;xpath", "Transfert Button", ScreenName);
		waitForSync(3);
	}

	/**  END   **/

	if(driver.findElements(By.id(xls_Read.getCellValue(sheetName, "inbx_paymentAttribute;id"))).size()==1)
	{
		waitForSync(2); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 
	}
	waitForSync(2);
	clickWebElement(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
	waitForSync(3);	

	clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
	waitForSync(3);
	switchToWindow("getParent");
	switchToFrame("contentFrame", "OPR293");
}

/**
 * Desc : Generate Delivery id with other charges
 * @author A-9175
 * @param codeCharge
 * @param Remarks
 * @param paymentType
 * @param otherCharges
 * @throws Exception
 */
public void generateDeliveryIDWithOthercharges(String codeCharge,String Remarks,String paymentType,boolean otherCharges,boolean fop)throws Exception
{

	verifyAndEnterConsigneeCode();
	/**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
	String station=getLoggedInStation("OPR293");
	if(paymentType.equals("CASH")&& station.equals("IAD"))
	{
		paymentType="PAYCARGO";
	}


	try{
		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		if(testEnv.equals("RCT"))	
			chkVerifyBeforePayment();

		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(5);

		switchToFrame("default");
		while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
		{
			handleAlert("Accept","DeliveryDocumentation");
			waitForSync(4);
		}
	}
	catch(Exception e){}
	finally{switchToFrame("contentFrame", "OPR293");}
	waitForSync(1);
	switchToWindow("storeParent");
	waitForSync(1);
	switchToWindow("child");
	waitForSync(1);
	if(otherCharges)
		addOthercharges(codeCharge);
	checkOrUncheckFOP(fop);

	enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
	waitForSync(1);
	if(paymentType.equals("CASH"))
	{
		clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

	}
	else if(paymentType.equals("CREDIT"))
	{
		clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);

	}


	/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
	else if(paymentType.equals("PAYCARGO"))         

	{           
		try{
			List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));

			for(WebElement chkBox:ele)
				chkBox.click();

			clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);

		}
		catch(Exception e)
		{
			writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
		}

	}
	else if(paymentType.equals("TRANSFERT"))     
	{

		clickWebElementByWebDriver(sheetName, "btn_transfert;xpath", "Transfert Button", ScreenName);
		waitForSync(3);
	}

	/**  END   **/

	if(driver.findElements(By.id(xls_Read.getCellValue(sheetName, "inbx_paymentAttribute;id"))).size()==1)
	{
		waitForSync(2); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 
	}
	waitForSync(2);
	clickWebElement(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
	waitForSync(3);	

	clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
	waitForSync(3);
	switchToWindow("getParent");
	switchToFrame("contentFrame", "OPR293");
}

	
	/**
	 * Desc : adding other charges in generate id payment screen
	 * @author A-9175
	 * @param codeCharge
	 * @throws Exception
	 */
	private void addOthercharges(String codeCharge) throws Exception {
		/**Adding charge code details**/
		clickWebElement(sheetName,"chk_checkAllGeneratePayment;xpath","Check all", ScreenName); 
		clickWebElement(sheetName,"btn_addChargeCode;id","Add Charge Code button", ScreenName);
		waitForSync(2);
		switchToWindow("storeFirstChild");
		switchToWindow("childWindow2");

		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_chargecode;name", data(codeCharge), "Charge Code", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName,"btn_chargeCodeList;id","List Charge Code", ScreenName);
		waitForSync(3);
/********************Configuration Check POP UP Text verification START*****************/
		boolean alertFlag = false;

		try {
			waitForSync(5);
			switchToFrame("default");
			waitForSync(5);
			String locator = xls_Read.getCellValue(sheetName, "txt_warningMessage;xpath");
			alertFlag=driver.findElement(By.xpath(locator)).isDisplayed();
			//Verifying Alert presence
			if(alertFlag)
			{

				String actText = driver.findElement(By.xpath(locator)).getText();

				if (actText.contains("No Valid configuration found for the selected charge head")) {
					writeExtent("Fail", "Alert displayed as : " + actText + "on Screen name " + ScreenName);
					String locatorYes = xls_Read.getCellValue(sheetName, "btn_yesbutton;xpath");
					driver.findElement(By.xpath(locatorYes)).click();
					writeExtent("Pass", "Sucessfully handled Alert with Yes Button on "+ScreenName);
					waitForSync(3);
				}


			} 
		}
		catch (Exception e) {
			
			if(alertFlag)
				writeExtent("Fail", "Not handled Alert with Yes Option on "+ScreenName);
			else
				writeExtent("Pass", " No Alert found with \"Valid configuration found for the selected charge head\" "+ScreenName);

		}
		finally
		{
			switchToWindow("childWindow2");
			waitForSync(2);
		}

				
				
		/********************Configuration Check POP UP Text verification END*****************/

		
		clickWebElement(sheetName,"btn_calculateChargesGP;id","Calculate Charge Code", ScreenName);
		waitForSync(3);
		
		//code for verifying the value displayed in the netAmount filed is 0
		String locator1=xls_Read.getCellValue(sheetName, "inbx_netAmount;name");
		WebElement ele1=driver.findElement(By.name(locator1));
		System.out.println(getAttributeWebElement(ele1,"Net Amount ", "value", ScreenName));
	
		if((getAttributeWebElement(ele1,"Net Amount ", "value", ScreenName).equals("0.0"))){
			
			
			writeExtent("Info", "Rate configured for the charge head  "+data(codeCharge)+" is 0.0.User can enter the charge.");
			
			clickWebElement(sheetName, "chkbox_actualCharge;xpath", "Actual charge checkbox", ScreenName);
			waitForSync(3);
			enterValueInTextbox(sheetName,"inbx_actualCharge;id",data("val~100"),"Actual charge", ScreenName);
						
		}
	
	

		else{
			
		
		String netAmount=getAttributeWebElement(ele1,"Net Amount ", "value", ScreenName);
		writeExtent("Pass", "NetAmount Displayed as "+netAmount);

		String locator2=xls_Read.getCellValue(sheetName, "inbx_grossAmount;name");
		WebElement ele2=driver.findElement(By.name(locator2));
		String grossAmount=getAttributeWebElement(ele2,"Gross Amount ", "value", ScreenName);
		writeExtent("Pass", "GrossAmount Displayed as "+grossAmount);

		String locator3=xls_Read.getCellValue(sheetName, "inbx_gstAmount;name");
		WebElement ele3=driver.findElement(By.name(locator3));
		String gstAmount=getAttributeWebElement(ele3,"Gst Amount ", "value", ScreenName);
		writeExtent("Pass", "Tax Amount Displayed as "+gstAmount);
		
		}

		clickWebElement(sheetName,"btn_OkGPScreen;id","List Charge Code", ScreenName);

		clickWebElement("Generic_Elements","btn_yes;xpath","List Charge Code", ScreenName);
		switchToWindow("getFirstChild");
		waitForSync(3);

		

	}

	public void generateDeliveryIDforPartialPieces(String Remarks,String paymentType,boolean otherCharges,String codeCharge,boolean fop)throws Exception{

verifyAndEnterConsigneeCode();
		
		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
		String station=getLoggedInStation("OPR293");
		if(paymentType.equals("CASH")&&station.equals("IAD"))
		{
			paymentType="PAYCARGO";
		}
		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		if(testEnv.equals("RCT"))	
			chkVerifyBeforePayment();

		switchToWindow("storeParent");
		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(6);
		boolean msgExists=false;
		try
		{
			switchToFrame("default");
			waitForSync(5);
			String locator = xls_Read.getCellValue(sheetName, "txt_warningMessage;xpath");
			String actText=driver.findElement(By.xpath(locator)).getText();

			if(actText.contains("issue DN for all Pending Pieces?"))
			{
				handleAlert("Dismiss","DeliveryDocumentation");
				msgExists=true;
			}
			else
			{
				handleAlert("Accept","DeliveryDocumentation");
			}
			waitForSync(3);

			switchToFrame("contentFrame", "OPR293");
		}
		catch(Exception e){}

		/************************* VERIFICATION OF PARTIAL PCS DELIVERY MESSAGE*****************/
		if(msgExists)
		{
			writeExtent("Pass","Message '"+"issue DN for all Pending Pieces?'" +" is triggered");
		}
		else
		{
			writeExtent("Fail","Message '"+"issue DN for all Pending Pieces?'" +" is not triggered");
		}
		waitForSync(3);
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(2);
		waitForSync(2);
		if(otherCharges)
			addOthercharges(codeCharge);
		checkOrUncheckFOP(fop);


		if(paymentType.equals("CASH"))
		{
			clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

		}
		else if(paymentType.equals("CREDIT"))
		{
			clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);

		}

		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
		else if(paymentType.equals("PAYCARGO"))         

		{           
			try{
				List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));

				for(WebElement chkBox:ele)
					chkBox.click();

				clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);

			}
			catch(Exception e)
			{
				writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
			}

		}
		/** END  **/


		waitForSync(2); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 
		clickWebElement(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");

		switchToDefaultAndContentFrame("OPR293");

	}



	/**
	 * 
	 * @param status
	 * @throws InterruptedException
	 */
	public void verifyDNStatus(String status) throws InterruptedException
	{
		waitTillScreenloadWithOutAssertion(sheetName, "txt_DNStatus;xpath","DN Status", ScreenName,10);
		String DeliveryId=getElementText(sheetName, "txt_DNStatus;xpath", "DN Status", ScreenName);
		boolean deliveryStatus = DeliveryId.contains(status);
		verifyValueOnPage(true, deliveryStatus, "Verify DN Status", ScreenName, "DN status");

	}
	/**
	 * @author A-9478
	 * Description... Get Delivery ID
	 * @param Remarks
	 * @throws Exception
	 */

	public String getDeliveryID()throws Exception
	{
		String DeliveryId = new String();
		try
		{
			waitTillScreenload(sheetName, "txt_DeliveryId;xpath","DeliveryID Text", ScreenName);
			String ele = xls_Read.getCellValue(sheetName, "txt_DeliveryId;xpath");
			DeliveryId = driver.findElement(By.xpath(ele)).getText();
			DeliveryId = (DeliveryId.split("\\["))[0];
			writeExtent("Pass", "Successfully fetched Delivery Id " + DeliveryId + " from " + ScreenName + " Page");

		}
		catch(Exception e)
		{
			writeExtent("Pass", "Couldn't fetch Delivery Id " + DeliveryId + " from " + ScreenName + " Page");
		}
		return DeliveryId;      
	}

	/**
	 * Description...enters pieces, weight and customer name for delivery id generation
	 * @param Pieces
	 * @param Weight
	 * @param CustomerName
	 * 
	 */
	public void enterDeliveryIdPcs(String Pieces,String Weight,String CustomerName)throws Exception{
		enterValueInTextbox(sheetName,"inbx_dlvIdPcs;xpath",data(Pieces),"Delivery id Pieces", ScreenName);

		enterValueInTextbox(sheetName,"btn_dlvIdWts;xpath",data(Weight),"Delivery id Weight", ScreenName);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_handOverTo;xpath", data("CustomerName"), "HandOver To", ScreenName);
	}
	/**
	 * @author A-9175
	 * Description... Click Checksheet
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void clickChecksheet() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_checkSheet;id", "Checksheet Button", ScreenName);

	}

	/**
	 * @author A-9175
	 * Desc : Verifying Checksheet captured or not
	 * @throws InterruptedException
	 */

	public void verifyChecksheetCaptured() throws InterruptedException
	{
		try{
			switchToFrame("frameName","popupContainerFrame");
			String locator=xls_Read.getCellValue(sheetName, "txt_verifychecksheetValues;xpath");
			List<WebElement> elements=driver.findElements(By.xpath(locator));
			for(WebElement elemnt:elements)

			{
				elemnt.getText().equals("Yes");
				waitForSync(2);
			}
			writeExtent("Pass", "Check sheet details  captured"
					+ " on " + ScreenName + " Page");
		} 
		catch (Exception e) {

			writeExtent("Fail", "Check sheet details not captured"
					+ " on " + ScreenName + " Page");
		}

		switchToFrame("default");
		switchToFrame("contentFrame","OPR293");

	}


	/**
	 * Description... Verify Cross Mark for Handover
	 * @throws InterruptedException
	 */
	public void verifyCrossMarkHandover() throws InterruptedException{
		verifyElementDisplayed(sheetName, "img_crossMark1;xpath", "Verify Cross Mark for AWB 1 Handover", ScreenName, "Cross Mark for AWB 1 Handover");
		verifyElementDisplayed(sheetName, "img_crossMark2;xpath", "Verify Cross Mark for AWB 2 Handover", ScreenName, "Cross Mark for AWB 2 Handover");


	}
	/**
	 * clicks on delivery id button
	 * @throws Exception
	 */
	public void generateDeliveryID()throws Exception{
verifyAndEnterConsigneeCode();
		
		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(5);
		switchToFrame("default");
		waitForSync(5);
		clickWebElement("Generic_Elements","btn_yes;xpath", "yes Button", ScreenName);
		waitForSync(6);
		switchToFrame("contentFrame", "OPR293");
	}
	/**
	 * Description... Generate Delivery ID 3
	 * @throws Exception
	 */
	public void generateDeliveryID3()throws Exception{
		
		
		verifyAndEnterConsigneeCode();
		/***********Capture Delivery Checksheet*************/
		String station = getLoggedInStation("OPR293");
		if ((station.equals("CDG")))
		{
			boolean checksheet=true;

			try
			{
				clickWebElement(sheetName, "btn_checkSheet;id", "CheckSheet Button",ScreenName);
				waitForSync(4);
				switchToWindow("storeParent");  
				switchToFrame("default");
				switchToFrame("contentFrame","OPR293"); 
				driver.switchTo().frame("popupContainerFrame");
				String locator=xls_Read.getCellValue(sheetName, "btn_Yesbutton;xpath");
				List<WebElement> elements=driver.findElements(By.xpath(locator));

				if (elements.size()==0)
				{
					checksheet=false;
					clickWebElementByWebDriver("DeliveryDocumentation_OPR293", "btnCloseChecksheet;id", "Close button", ScreenName);
					waitForSync(5); 
				}
				else
					checksheetCapture();

				if (checksheet)
					writeExtent("Pass", "Check sheet details are Saved on " + ScreenName);
				else 
					writeExtent("Info", "No check sheet details configured on " + ScreenName);	

			}catch (Exception e) {
				writeExtent("Fail", "Could not save check sheet details on " + ScreenName);
			}
			finally
			{
				waitForSync(2);

				switchToWindow("getParent"); 
				switchToFrame("default");
				switchToFrame("contentFrame","OPR293"); 
				waitForSync(5);
			}
		}

		/********************** To uncheck verify before payment check box**********/
		String testEnv=getPropertyValue(globalVarPath, "testEnv");
		if(testEnv.equals("RC4"))
			uncheckVerifyPayment();
		/*****************************************************************/

		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(5);
		acceptAlerts();


	}
	/**
	 * @Description : AcceptAlerts
	 * @author A-9175
	 * @throws Exception
	 */
	public void acceptAlerts()throws Exception{

		try{
			switchToFrame("default");
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				handleAlert("Accept","DeliveryDocumentation");
				waitForSync(4);
			}
		}
		catch(Exception e){}
		finally{switchToFrame("contentFrame", "OPR293");
		}
	}
	/**
	 * 
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc : Verify if ready for delivery tick mark exists
	 */
	public void verifyReadyForDeliveryTick(String awbNo) throws InterruptedException, AWTException {

		try{
			String locator = xls_Read.getCellValue(sheetName, "lbl_RFDTick;xpath");
			locator=locator.replace("*",data(awbNo));
			if(driver.findElement(By.xpath(locator)).isDisplayed())
			writeExtent("Pass","Ready For Delivery Tick Displayed for "+data(awbNo)+" on "+ScreenName);
			else
			writeExtent("Fail","Ready For Delivery Tick is not displayed for "+data(awbNo)+" on "+ScreenName);	

		}
		catch(Exception e)
		{
			writeExtent("Fail","Ready For Delivery Tick is Not Displayed on "+ScreenName);
		}
	}
			
		

	/**
	 * @Description : capture charge details
	 * @author A-9175
	 * @param Remarks
	 * @param fop
	 * @throws Exception
	 */
	public void enterChargeDetails(String Remarks,boolean fop)throws Exception{

		waitForSync(3);
		switchToWindow("storeParent");
		waitForSync(3);
		switchToWindow("child");
		waitForSync(3);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(2);
		try
		{
			String locatorValue=xls_Read.getCellValue(sheetName, "chk_creditCheckBox;xpath");      
			if(fop)
			{
				       
				if(!driver.findElement(By.xpath(locatorValue)).isSelected())
				{
					clickWebElement(sheetName,"chk_creditCheckBox;xpath"," FOP Credit check box ", ScreenName);
					writeExtent("Pass","FOP Credit check box Checked sucessfully");
				}
				else
				{
					writeExtent("Fail","Failed to check FOP Credit checkbox");
				}
			}
			else
			{
				if(driver.findElement(By.xpath(locatorValue)).isSelected())
				{
					clickWebElement(sheetName,"chk_creditCheckBox;xpath"," FOP Credit check box ", ScreenName);
					writeExtent("Pass","FOP Credit check box UnChecked sucessfully");
				}
				else
				{
					writeExtent("Fail","Failed to Uncheck FOP Credit checkbox");
				}
			}
		}
		
		catch (Exception e) {
			writeExtent("Fail","Failed to do operation in Verify Payment checkbox");
		}
	}
	
	/**
	 * List with flight Number
	 * @author A-9844
	 * @param uldno
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listWithFlightNumber(String carrierCode,String flightNo,String flightDate) throws InterruptedException, IOException{

		enterValueInTextbox(sheetName, "inbx_carrierCode;xpath", data(carrierCode), "carrier code", ScreenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_flightNo;xpath", data(flightNo), "flight number", ScreenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;xpath", data(flightDate), "Flight Date", ScreenName);
		clickWebElement(sheetName, "btn_List;xpath", "List Button", ScreenName);
		waitForSync(5);

		String locator = xls_Read.getCellValue("DeliveryDocumentation_OPR293", "chkbox_pendingDeliveryId");
		if(driver.findElement(By.name(locator)).isSelected())
		{
			driver.findElement(By.name(locator)).click();
			waitForSync(1);
		}

	}

	/**
	 * @Description : capture Payment details
	 * @author A-9175
	 * @param paymentType
	 * @param Remarks
	 * @throws Exception
	 */
	public void enterPaymentDetails(String paymentType,String Remarks)throws Exception
	{
		waitForSync(2);
		
		if(paymentType.equals("CASH"))
		{
			clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);
		}
		else if(paymentType.equals("CREDIT"))
		{
			clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit CARD Button", ScreenName);
		}
		waitForSync(3); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 
		
		clickWebElementByWebDriver(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
	}
	
	/**
	 * @description : accept payment
	 * @author A-9175
	 * @throws Exception
	 */
	public void clickAcceptPayment()throws Exception
	{
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR293");
	}



	/**
	 * Description : Unchecking verify payment
	 * @author A-9175
	 * @throws Exception
	 */
	public void uncheckVerifyPayment()throws Exception{

		try{
			String locatorValue=xls_Read.getCellValue(sheetName, "btn_verifyBeforePayment;xpath");              
			if(driver.findElement(By.xpath(locatorValue)).isSelected())
			{
				clickWebElement(sheetName,"btn_verifyBeforePayment;xpath","Verify Before Payment checkbox", ScreenName);
				//writeExtent("Pass","Verify Payment checkbox un-checked sucessfully");
			}
			else
			{
				//writeExtent("Fail","Failed to Uncheck Verify Payment checkbox");
			}
		}catch (Exception e) {
			//writeExtent("Fail","Failed to Uncheck Verify Payment checkbox");
		}
	}
	
	/**
	 * Description : Verifying BTP deatils
	 * @author A-9175
	 * @param btpid
	 * @throws Exception
	 */
	public void verifyBTPid(String btpid)throws Exception{

		try{
			String locatorValue=xls_Read.getCellValue(sheetName, "BTPid;id");              
			if(driver.findElement(By.xpath(locatorValue)).getText().equals(data(btpid)))
			{
				writeExtent("Pass","sucessfully verified BTP id as : "+data(btpid));
			}
			else
			{
				writeExtent("Fail","Couldnt verified BTP id as : "+data(btpid));
			}
		}catch (Exception e) {
			writeExtent("Fail","Couldn't verified BTP id as : "+data(btpid));
		}
	}

	/**
	 * Description... Generate Delivery ID
	 * @param Remarks
	 * @throws Exception
	 */

	public void generateDeliveryIDWithPopUps(String Remarks)throws Exception{

		switchToWindow("storeParent");
		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(6);
		try
		{
			switchToFrame("default");
			waitForSync(5);
			String locator = xls_Read.getCellValue(sheetName, "txt_warningMessage;xpath");
			String actText=driver.findElement(By.xpath(locator)).getText();
			if(actText.contains("issue DN for all Pending Pieces?"))
			{
				handleAlert("Dismiss","DeliveryDocumentation");
			}
			else
			{
				handleAlert("Accept","DeliveryDocumentation");
			}
			waitForSync(3);
			/*** if(!actText.contains("issue DN for all Pending Pieces?"))
                 {
                     handleAlert("Accept","DeliveryDocumentation");
                 }***/
			switchToFrame("contentFrame", "OPR293");
		}
		catch(Exception e){}
		waitForSync(3);
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");

		switchToDefaultAndContentFrame("OPR293");

	}

	/**
	 * Description... Generate Delivery ID 2
	 * @param Remarks
	 * @throws Exception
	 */

	public void generateDeliveryID2(String Remarks)throws Exception{

		switchToWindow("storeParent");
		clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
		waitForSync(6);            
		handleAlert("Accept","DeliveryDocumentation");
		waitForSync(3);
		switchToWindow("child");
		waitForSync(3);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");

		switchToDefaultAndContentFrame("OPR293");

	}
	/**
	 * Description... Enters Capture HandOver Details
	 * @throws Exception
	 */
	public void enterCaptureHandOverDetails() throws Exception{
		clickButtonSwitchWindow(sheetName, "btn_HandoverDetials;name", ScreenName, "Capture HandOver Details Buttom");
		enterValueInTextbox(sheetName, "inbx_handOverTo;name", data("CustomerName"), "HandOver To", ScreenName);
		enterValueInTextbox(sheetName, "inbx_remarksPopup;name", data("Remarks"), "Remarks", ScreenName);                   
		clickButtonSwitchtoParentWindow("Generic_Elements", "btn_save;xpath", "Save Button", ScreenName);
		switchToDefaultAndContentFrame("OPR293");

	}
	/**
	 * Description... Verify Handover Tick Mark
	 * @throws InterruptedException
	 */
	public void verifyHandoverTickMark() throws InterruptedException{
		verifyElementDisplayed(sheetName, "img_docHandOver_tick;xpath", "Verify Tick Mark for Document Handover for AWB1", ScreenName, "Tick Mark for Document Handover for AWB1");
		verifyElementDisplayed(sheetName, "img_docHandOver_tick2;xpath", "Verify Tick Mark for Document Handover for AWB2", ScreenName, "Tick Mark for Document Handover for AWB2");

	}



	/**
	 * Description... Click Delivery ID
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void clickDeliveryID() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_genDelivery;name", "Delivery ID Button", ScreenName);

	}
	/**
	 * Desc : Capture check sheet in a generic way
	 * @author A-9175
	 * @throws Exception
	 */

	public void checksheetCapture() throws Exception
	{

		switchToWindow("storeParent");          

		waitForSync(3); 
		try{ 
			switchToFrame("default");               
			switchToFrame("contentFrame","OPR293"); 
			driver.switchTo().frame("popupContainerFrame"); 
			String locator=xls_Read.getCellValue(sheetName, "btn_Yesbutton;xpath");
			List<WebElement> elements=driver.findElements(By.xpath(locator));
			for(WebElement elemnt:elements)
			{
				elemnt.click();
				waitForSync(3);}

			clickWebElement("DeliveryDocumentation_OPR293", "btn_Save;id", "Ok Button", ScreenName);  
			waitForSync(5);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "OK Button", ScreenName);
			switchToFrame("contentFrame","OPR293"); 
			driver.switchTo().frame("popupContainerFrame"); 
			clickWebElementByWebDriver("DeliveryDocumentation_OPR293", "btnCloseChecksheet;id", "Close button", ScreenName);
			waitForSync(5); 
		} 

		finally
		{
			waitForSync(2);

			switchToWindow("getParent"); 
			switchToFrame("default");
			switchToFrame("contentFrame","OPR293"); 
			waitForSync(5);
		}

	}


	/**
	 * Description... Click Capture Delivery Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickCaptureDelivery() throws InterruptedException, IOException{

		clickWebElement(sheetName, "btn_captureDelivery;xpath", "Capture Delivery Button", ScreenName);
		waitForSync(5);
	}
	/**
	 * Description... Enter Delivered To
	 * @param deliveredTo
	 * @throws InterruptedException
	 */
	public void enterDeliveredTo(String deliveredTo) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_deliveredTo;xpath", deliveredTo, "Delivered To", ScreenName);
	}
	/**
	 * Description...	Click Save Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSaveButton() throws InterruptedException, IOException{
		clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", ScreenName);
		waitForSync(5);
	}
	/**
	 * Description... Verifies Pending DN Pieces
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 */
	public void verifyPendingDNPieces(String pieces, String weight) throws InterruptedException{
		String expDNpcs=getElementText(sheetName, "txt_pendingDNpcs;xpath", "DN Pieces", ScreenName);
		String expDNwt=getElementText(sheetName, "txt_pendingDNwt;xpath", "DN Weight", ScreenName);

		verifyValueOnPage(expDNpcs, pieces, "1. Verify DN Pieces", ScreenName, "DN Pieces");
		verifyValueOnPage(expDNwt, weight, "1. Verify DN Weight", ScreenName, "DN Weight");

	}
	/**
	 * Description... Change the DN Pieces and weight
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 */
	public void changeDNpcs_wt(String pieces, String weight) throws InterruptedException{



		enterValueInTextbox(sheetName, "inbx_deliverID_pcs;name", pieces, "Pieces", ScreenName);
		enterValueInTextbox(sheetName, "inbx_deliverID_wt;name", weight, "Weight", ScreenName);
	}
	/**
	 * Description... Enters the Customer Name
	 * @param customerName
	 * @throws InterruptedException
	 */
	public void enterCustomerName(String customerName) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_customerName;name", customerName, "Customer Name", ScreenName);

	}
	/**
	 * Description... Verify the DN Pieces and weight is changed
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 */
	public void verifyDNpiecesChanged(String pieces, String weight) throws InterruptedException{

		String expDNpcs=getAttributeWebElement(sheetName, "inbx_deliverID_pcs;name", "DN Pieces","value", ScreenName);
		String expDNwt=getAttributeWebElement(sheetName, "inbx_deliverID_wt;name", "DN Weight","value", ScreenName);

		verifyValueOnPage(expDNpcs, pieces, "1. Verify DN Pieces", ScreenName, "DN Pieces");
		verifyValueOnPage(expDNwt, weight, "1. Verify DN Weight", ScreenName, "DN Weight");
	}
	/**
	 * Description... Verify the Remaining Pieces and weight
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 */
	public void verifyRemainingPieces(String pieces, String weight) throws InterruptedException{
		String expDNpcs=getElementText(sheetName, "inbx_deliverID_pcs;name", "DN Pieces", ScreenName);
		String expDNwt=getElementText(sheetName, "inbx_deliverID_wt;name", "DN Weight", ScreenName);

		verifyValueOnPage(expDNpcs, pieces, "1. Verify DN Pieces", ScreenName, "DN Pieces");
		verifyValueOnPage(expDNwt, weight, "1. Verify DN Weight", ScreenName, "DN Weight");
	}
	/**
	 * Description... Clicks on DN Print Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickDNPrint() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_btnDNPrint;name", "DN Print Button", ScreenName);

	}

	public void enterCustomer(String AgentCode) throws InterruptedException, AWTException{
		enterValueInTextbox(sheetName, "inbx_CustomerName;id", data(AgentCode), "Customer No", ScreenName);
		keyPress("TAB");
		keyPress("ENTER");
		keyPress("TAB");
		keyRelease("TAB");
	}

	/**
	 * @author A-7271
	 * @throws Exception
	 * Desc : click Accept Payment Button
	 */
	public void clickAcceptPaymentButton() throws Exception
	{
		try{
			
			clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
			waitForSync(5);

			switchToFrame("default");
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				handleAlert("Accept","DeliveryDocumentation");
				waitForSync(4);
			}
		}
		catch(Exception e){}
		finally{switchToFrame("contentFrame", "OPR293");}
		waitForSync(3);
		switchToWindow("storeParent");
		waitForSync(3);
		switchToWindow("child");
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR293");


	}
	/**
	 * 
	 * @param Remarks
	 * @param paymentType
	 * @throws Exception
	 */
	public void generateDeliveryID3(String Remarks,String paymentType)throws Exception{
		
	
      /**  Temporarily added till we get a confirmation on IAD paycargo behaviour  **/
		 /*******************************************************************/
		String station=getLoggedInStation("OPR293");
		
        if(paymentType.equals("CASH")&&station.equals("IAD"))
        {
              paymentType="PAYCARGO";
        }
        /*******************************************************************/

		try{
			String locatorValue=xls_Read.getCellValue(sheetName, "btn_verifyBeforePayment;xpath");              
			
			//Commented as part of new requirement . verify before payment checkbox will be unchecked.
			/***if(!driver.findElement(By.xpath(locatorValue)).isSelected())
			{
				clickWebElement(sheetName,"btn_verifyBeforePayment;xpath","Verify Before Payment checkbox", ScreenName);
			}****/

			clickWebElement(sheetName,"btn_generateDlvId;xpath","Delivey Id button", ScreenName);
			waitForSync(5);

			switchToFrame("default");
			while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
			{
				handleAlert("Accept","DeliveryDocumentation");
				waitForSync(4);
			}
		}
		catch(Exception e){}
		finally{switchToFrame("contentFrame", "OPR293");}
		waitForSync(3);
		switchToWindow("storeParent");
		waitForSync(3);
		switchToWindow("child");
		waitForSync(3);
		enterValueInTextbox(sheetName,"inbx_remarks;xpath",data(Remarks),"Delivery remarks", ScreenName);
		waitForSync(2);
		if(paymentType.equals("CASH"))
		{
			clickWebElementByWebDriver(sheetName, "btn_cash;xpath", " CASH ", ScreenName);

		}
		else if(paymentType.equals("CREDIT"))
		{
			clickWebElementByWebDriver(sheetName, "btn_credit;xpath", "Credit Button", ScreenName);

		}
   
		
		/**  Temporarily added till we get a confirmation on IAD paycargo behaviour   **/
		else if(paymentType.equals("PAYCARGO"))         
    
		{           
			try{
				List<WebElement> ele=driver.findElements(By.xpath("//input[@type='checkbox' and @name='creditCheckBox' and @checked ]"));
              
              for(WebElement chkBox:ele)
                    chkBox.click();
              
             clickWebElementByWebDriver(sheetName, "btn_paycargo;xpath", "PAYCARGO Button", ScreenName);
             
			}
			catch(Exception e)
			{
				writeExtent("Fail","Failed to Find/Uncheck Credit checkboxes");
			}

        }
        	/** END  **/
		
		waitForSync(2); 
		enterValueInTextbox(sheetName,"inbx_paymentAttribute;id",data(Remarks),"Payment Attribute", ScreenName);
		waitForSync(2); 

		clickWebElementByWebDriver(sheetName, "btn_ok;xpath", "ok Button", ScreenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_Accept;id", "Accept Button", ScreenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR293");




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
				ScreenName);	


		if (actCustomsStatus.contains(expCustomsStatus)) {
			System.out.println("found true for " + actCustomsStatus);

			onPassUpdate(ScreenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		} else {
			onFailUpdate(ScreenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		}
	}


	public void verifyCustomsInformation2(String FlightNo, int[] verfCols, String[] actVerfValues,
			String expCustomsStatus) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = (WebElement) js.executeScript("return document.getElementsByTagName('circle')[1]");

		moveScrollBar(ele);
		boolean checked = ele.isSelected();
		if (!checked)
			ele.click();


		ele.click();
		waitForSync(4);
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_CustomsInformation;xpath", "//td", verfCols, FlightNo,
				actVerfValues);
		String actCustomsStatus = getElementText(sheetName, "txt_CustomsStatus2;xpath", "Customs status code",
				ScreenName);

		if (actCustomsStatus.contains(expCustomsStatus)) {
			System.out.println("found true for " + actCustomsStatus);

			onPassUpdate(ScreenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		} else {
			onFailUpdate(ScreenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		}
	}

	public void verifyErrorMessage(String ExpErrorMsg) throws InterruptedException, IOException{
		String ActErrorMsg =getElementText(sheetName, "txt_ErrorMsg;xpath", "Error Msg", ScreenName);
		verifyValueOnPageContains(ActErrorMsg, ExpErrorMsg, "1. Verify Error Msg", ScreenName, "Error Msg Verification");

	}


}
