package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class DeliverCargo_OPR064 extends CustomFunctions{
	
	public DeliverCargo_OPR064(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="DeliverCargo_OPR064";
	public String ScreenName="Deliver Cargo";
	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	
	/**
	 * @Description... Verify DN pieces changed
	 * @author A-9175
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 */
	public void verifyDNpiecesChanged(String pieces, String weight) throws InterruptedException{
		
		String expDNpcs=getAttributeWebElement(sheetName, "inbx_deliverID_pcs;name", "DN Pieces", "value", ScreenName);
		String expDNwt=getAttributeWebElement(sheetName, "inbx_deliverID_wt;name", "DN Weight","value", ScreenName);
		
		verifyValueOnPage(expDNpcs, pieces, "1. Verify DN Pieces", ScreenName, "DN Pieces");
		verifyValueOnPage(expDNwt, weight, "1. Verify DN Weight", ScreenName, "DN Weight");
	}
	/**
	 * @author A-9844
	 * Description...verify Column name -Clearing Agent
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyClearingAgentColumn(String expText) throws InterruptedException, Exception{
		 String locator= xls_Read.getCellValue(sheetName, "label_clearingAgent;xpath");
		 By ele =By.xpath(locator);
		 String actText = driver.findElement(ele).getText();
		 System.out.println(actText);
		 verifyScreenText(ScreenName, expText, actText, "Clearing Agent", "Clearing Agent");
		 
	}
	/**
	 * @Description... Verify DN Pieces and weight
	 * @author A-9175
	 * @param pieces
	 * @param weight
	 * @throws InterruptedException
	 */
	public void verifyDNPiecesAndWeight(String pieces, String weight) throws InterruptedException{
		
		String expDNpcs=getAttributeWebElement(sheetName, "inbx_deliverID_pcs;name", "DN Pieces", "value", ScreenName);
		String expDNwt=getAttributeWebElement(sheetName, "inbx_deliverID_wt;name", "DN Weight","value", ScreenName);
		
		System.out.println(expDNpcs);
		System.out.println(expDNwt);
		verifyValueOnPage(expDNpcs, pieces, "1. Verify DN Pieces", ScreenName, "DN Pieces");
		verifyValueOnPage(expDNwt, weight, "1. Verify DN Weight", ScreenName, "DN Weight");
}
	
	/**
	 * @Description : Entering Deliver to
	 * @author A-9175
	 * @param deliveredTo
	 * @throws InterruptedException
	 */
	public void enterDeliveredTo(String deliveredTo) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_deliveredTo_wt;name", deliveredTo, "Delivered To", ScreenName);
	}
	
   /**
    * @Description... Enters time 1 hour less than what is displayed
    * @author A-9175
    * @throws InterruptedException
    */
	public void enterTimePreviousTime()  throws InterruptedException {
		Float deliveryTime=null;
		String deliveryTimeStr=getAttributeWebElement(sheetName, "inbx_deliveryTime;name", "Delivery Time","value", ScreenName);
		try{
		 deliveryTime=Float.parseFloat(deliveryTimeStr.replace(":", "."))-1;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		enterValueInTextbox(sheetName, "inbx_deliveryTime;name", deliveryTime.toString().replace(".",":"), "Delivery Time", ScreenName);
		
	}
	/**
	 * 
	 * @param errormsg
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click save and verify the error message
	 */
	public void clickSave(String errormsg) throws InterruptedException, IOException
    {
          clickWebElement(sheetName,"btn_Save;id","Save button", ScreenName);
          waitForSync(3);
          verifyErrorMessage(ScreenName, errormsg);

    }

	/**
	 * @author A-9844
	 * @Description... enter AWB Number
	 * @throws InterruptedException
	 */
	public void enterAWBNumber(String AWBPrefix ,String AWBNumber) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_awbPrefix;name", data(AWBPrefix), "AWB Prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;xpath", data(AWBNumber), "AWB Number", ScreenName);
	}
	/**
	 * @author A-9844
	 * @Description... verify clearing agent LOV
	 * @throws InterruptedException
	 */
	public void verifyClearingAgentLOV() throws InterruptedException {
		
		try
		{
		String locator = xls_Read.getCellValue(sheetName, "img_clearingAgentLOV;id");
		
		 if((driver.findElements(By.id(locator)).size()>0)){

		writeExtent("Pass","Successfully verified Clearing Agent LOV on "+ScreenName);
		 }
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Clearing Agent LOV not present on "+ScreenName);
		}
	}
	/**
	 * @author A-9844
	 * @Description... select clearing agent from LOV
	 * @throws Exception 
	 */
	public void selectClearingAgentFromLOV(String clearingAgent) throws Exception {
		
		 clickWebElement(sheetName,"img_clearingAgentLOV;id","Clearing Agent LOV", ScreenName);
         waitForSync(5);
         switchToWindow("storeParent");
         switchToWindow("child");
         enterValueInTextbox(sheetName, "inbx_clearingAgent;id", data(clearingAgent), "clearing agent", ScreenName);
         clickWebElement(sheetName, "btn_ListButtonLOV;xpath", "List button", ScreenName);
         waitForSync(3);
         clickWebElement(sheetName, "chkbx_agentCodeLOV;name", "Clicking Checkbox", ScreenName);
         clickWebElement(sheetName, "btn_OKButtonLOV;name", "Clicking OK in LOV", ScreenName);
         waitForSync(2);
         switchToWindow("getParent");
         switchToDefaultAndContentFrame("OPR064");
	}
	/**
	 * @author A-9844
	 * @Description... click Add To List
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickAddToList() throws InterruptedException, IOException {
		clickWebElement(sheetName,"btn_AddToList;id","Add To List button", ScreenName);
		  waitForSync(2);
	}
	/**
	 * Description... Verify delivery details
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws IOException 
	 */
	public void verifyDeliveryDetails(int verfCols[],String actVerfValues[],String pmKey) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "verifyDeliveryDetails;xpath", "//td", verfCols, pmKey, actVerfValues);

	}

	/**
     * @author A-9478
     * @Description... Enters Pieces and Weight
     * @param Remarks
     * @throws InterruptedException
     */
		public void enterPiecesAndWeight(String Pieces,String Weight)  throws InterruptedException {
         enterValueInTextbox(sheetName, "inbx_pieces;name", data(Pieces), "Pieces", ScreenName);
         enterValueInTextbox(sheetName, "inbx_weight;name", data(Weight), "Weight", ScreenName);
         }

	/**
	 * @Description... Enters Vehicle Number
	 * @author A-9175
	 * @param VehicleNumber
	 * @throws InterruptedException
	 */
		public void enterVehicleNo(String VehicleNumber) throws InterruptedException {
			enterValueInTextbox(sheetName, "inbx_vehicleNumber;name", VehicleNumber, "Vehicle Number", ScreenName);
			
		}
		
	/**
	 * @Description : Verifying Messages
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyMessage(String msg) throws InterruptedException, IOException
	{
		waitForSync(3);
		switchToFrame("default");
		boolean msgExists=false;
		try
		{
			switchToFrame("default");
			waitForSync(5);
			String locator = xls_Read.getCellValue(sheetName, "txt_warningMessage;xpath");
			String actText=driver.findElement(By.xpath(locator)).getText();
			
			if(actText.contains(data(msg)))
			{
				handleAlert("Accept","DeliveryDocumentation");
				msgExists=true;
			}
			waitForSync(3);
		}
		catch(Exception e){}
		
		/************************* VERIFICATION OF MESSAGE*****************/
		if(msgExists)
		{
			writeExtent("Pass","Message '"+data(msg)+" is triggered");
		}
		else
		{
			writeExtent("Fail","Message '"+data(msg)+" is not triggered");
		}
		waitForSync(3);
    	switchToFrame("contentFrame","OPR293");
	}


	/**
	 * @Description... Enters Contact Address
	 * @author A-9175
	 * @param ContactAddress
	 * @throws InterruptedException
	 */
		public void enterContactAddress(String ContactAddress)  throws InterruptedException {
			enterValueInTextbox(sheetName, "inbx_contactAddress;name", ContactAddress, "Contact Address", ScreenName);
			
		}
	/**
	 * @Description... Enters Remarks
	 * @author A-9175
	 * @param Remarks
	 * @throws InterruptedException
	 */
		public void enterRemarks(String Remarks )  throws InterruptedException {
			enterValueInTextbox(sheetName, "inbx_remarks;name", Remarks, "Remarks", ScreenName);
			}
	/**
	 * @Description : Click save
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSave() throws InterruptedException, IOException
	{
		clickWebElement(sheetName,"btn_Save;id","Save button", ScreenName);
		waitForSync(3);
	    verifyElementDisplayed(sheetName,"htmlDiv_save;xpath", " Save", ScreenName, "Save message");

	}
	
	/**
	 * @author A-9175
	 * @Description... Verify Delivery ID field is auto populated
	 * @throws InterruptedException 
	 * 
	 */
	public void verifyDeliveryID() throws InterruptedException{
		String deliveryID=getElementText(sheetName, "txt_deliveryID;xpath", "Delivery ID", ScreenName);
		verifyValueNotNull(deliveryID, "Delivery ID");
	}
	
	/**
	 * @author A-9175
	 * @Description... Verify Delivery ID is auto populated
	 * @throws InterruptedException
	 */
	  public void verifyDeliveredTo() throws InterruptedException {
		String CustomerName=getElementText(sheetName, "inbx_deliveredTo_wt;name","Delivered To", ScreenName);
		verifyValueOnPage(CustomerName.toUpperCase(), data("CustomerName").toUpperCase(), "Verify Delivery ID is auto populated", ScreenName, "Delivered To");
		}
		
	  /**
	   * @Description Verifying Customs informations
	   * @author A-9175
	   * @param FlightNo
	   * @param verfCols
	   * @param actVerfValues
	   * @param expCustomsStatus
	   * @throws Exception
	   */
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
	
	
	
}
