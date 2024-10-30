package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class GeneratePaymentAdvice_CSH007 extends CustomFunctions {

	public GeneratePaymentAdvice_CSH007(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "GeneratePaymentAdvice_CSH007";
	public String ScreenName = "Generate Payment Advice: CSH007";
	String screenId = "CSH007";
	
	
/**
 * @author A-7271
 * @param paymentAdviceNo
 * @throws InterruptedException
 * @throws IOException
 * Desc : List with payment advice number
 */
	public void listWithPaymentAdviceNo(String paymentAdviceNo) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_paymentAdviceNo;name", data(paymentAdviceNo), "Payment Advice No", ScreenName);
		 clickWebElement(sheetName, "btn_list;id", "List Button", ScreenName);
         waitForSync(5);
	}
	/**
	 * @author A-9847
	 * @Desc To click Save as Draft Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void clickSaveAsDraft() throws InterruptedException, IOException 
	{
         clickWebElement(sheetName, "btn_saveAsDraft;id", "Save As Draft button", ScreenName);
         waitForSync(3);
	}
	/**
	 * @author A-7271
	 * Verify is credit check box is checked
	 * 
	 */
	public void verifyCreditCheckBox()
	{
		try
		{
		String locator=xls_Read.getCellValue(sheetName, "chkBox_credit;xpath");
        
		List <WebElement> creditChk=driver.findElements(By.xpath(locator));
		int count=1;
		
		
		for(int i=1;i<creditChk.size();i=i+2)
		{
			String isChecked=creditChk.get(i).getAttribute("checked");
			if(isChecked!=null)
			{
				if(isChecked.equals("true"))
				{
					writeExtent("Pass","Credit check box is checked in "+ScreenName+ " for the checkbox No "+count);
				}
				else
				{
					writeExtent("Fail","Credit check box is not checked in "+ScreenName+ " for the checkbox No"+count);
				}
				
			}
			else if(isChecked==null)
			{
			
				writeExtent("Fail","Credit check box is not checked in "+ScreenName+ " for the checkbox No"+count);
			}
			count=count+1;
		}
		
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Credit check box is not checked in "+ScreenName);
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
			if(driver.findElement(By.id(locatorValue)).getAttribute("value").equals(btpid))
			{
				writeExtent("Pass","sucessfully verified BTP id as : "+btpid);
			}
			else
			{
				writeExtent("Fail","Couldnt verified BTP id as : "+btpid);
			}
		}catch (Exception e) {
			writeExtent("Fail","Couldn't verified BTP id as : "+btpid);
		}
	}

    /**
	 * Desc:finalize payment action
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickFinalizePaymentDetails() throws InterruptedException, IOException 
	{
		clickWebElement(sheetName, "btn_FinalizePayment;id", "Finalize Payment button", ScreenName);
		waitForSync(5);
		
		try
		{

			switchToFrame("default");

			while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
			{
				clickWebElement(sheetName, "btn_yes;xpath", "Yes Button", ScreenName);
				waitForSync(3);
			}

		}catch(Exception e){
			
		}
		finally
		{
			switchToFrame("contentFrame", "CSH009");
		}
  }

	/**
	 * @dexription : list with awb
	 * @author A-9175
	 * @param prefix
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listWithAWB(String prefix,String awbNo) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "Awb Prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_awbNo;name", data(awbNo), "Awb Number", ScreenName); 
		clickWebElement(sheetName, "btn_list;id", "List Button", ScreenName);
         waitForSync(5);
	}
	

	/**
	 * Description... Select Payment mode
	 * @author A-9478
	 * @param PaymentMode
	 * @throws InterruptedException
	 */
	public void selectPaymentMode(String PaymentMode) throws InterruptedException 
	{
		 String station=getLoggedInStation("OPR026");
			
	        if(PaymentMode.equalsIgnoreCase("Cash")&&station.equals("IAD"))
	        {
	        	PaymentMode="PAYCARGO";
	        }
	        
	        waitTillScreenload(sheetName, "btn_Add;xpath","Add Button", ScreenName);
			PaymentMode =PaymentMode.replace(" ", "");
			String locator=xls_Read.getCellValue(sheetName, "lst_paymentMode;xpath");
	        locator=locator.replace("PMode", PaymentMode.toUpperCase());
	        driver.findElement(By.xpath(locator)).click();
	        waitForSync(2);
	}
	
	/**
	 * Description... Clicks on Add button
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickAdd() throws InterruptedException, IOException 
	{
         clickWebElement(sheetName, "btn_Add;xpath", "Add Button", ScreenName);
         waitForSync(3);
	}
	/**
	 * 
	 * @param amount
	 * @throws InterruptedException 
	 */
	public void enterPayment(String amount) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_paymentAdviceActAmt;id", data(amount), "Payment Amount", ScreenName);
		 waitForSync(2);
	}
	
	/**
	 * Description... Clicks on Finalize Payment button
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickFinalizePayment() throws InterruptedException, IOException 
	{
		clickWebElement(sheetName, "btn_FinalizePayment;id", "Finalize Payment button", ScreenName);
		waitForSync(5);
		
		switchToFrame("default");

		while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
		{
			clickWebElement(sheetName, "btn_yes;xpath", "Yes Button", ScreenName);
			waitForSync(3);
		}
		
		driver.switchTo().frame("iCargoContentFrameOPR026");
	}
	
	
	/**
	 *@author A-7271
	 * @param guranteeDetails
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verifyGuarenteeAmount
	 */
	
	public void verifyGuarenteeAmount(List<String> guranteeDetails) throws InterruptedException, IOException 
	{
		clickWebElement(sheetName, "img_guaranteeDetailsImg;name", "Guarantee Details", ScreenName);
		String actText= getElementText(sheetName, "htmlDiv_guarenteeDetails;name","Guarantee Amount Details", ScreenName);

		for (String value : guranteeDetails) {


			verifyScreenText(sheetName, value, actText, "Guarentee amount verification ", ScreenName);

		}

	}
	/**
	 * Description... Clicks on Close button
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickClose() throws InterruptedException, IOException 
	{
         clickWebElement(sheetName, "btn_Close;name", "Close button", ScreenName);
         waitForSync(5);
	}
	
	/**
	 * Description... Enter remarks
	 * @author A-9478
	 * @throws InterruptedException
	 */
	public void enterRemarks(String Remarks) throws InterruptedException 
	{
        Remarks=Remarks.replace("Cash", "Paycargo");
        
        if(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "inbx_Remarks;xpath"))).size()==1)
        enterValueInTextbox(sheetName, "inbx_Remarks;xpath", data(Remarks), "Remarks", ScreenName);
 
        waitForSync(5);

	}
	/**
	 * @author A-7271
	 * @param serviceCode
	 * @throws InterruptedException
	 * Desc : verifyServiceCode
	 */
	public void verifyServiceCode(String serviceCode) throws InterruptedException 
	{
		
		getTextAndVerify(sheetName, "htmlDiv_serviceHeader;xpath",	"Service Code", ScreenName, "Service Code Verification", data(serviceCode), "contains");
		waitForSync(1);

				
        
	}
	/**
	 * @author A-7271
	 * @param paymentAdviceNoKey
	 * Desc : Get Payment Advice No and store in the map
	 */
	public void getPaymentAdviceNo(String paymentAdviceNoKey)
	{
		String paymentAdviceNo=getAttributeWebElement(sheetName, "inbx_paymentAdviceNo;name","Payment Advice No","value", ScreenName);
		System.out.println(paymentAdviceNo);
		map.put(paymentAdviceNoKey, paymentAdviceNo);
				
	}
	/**
	 * Description... Verify Payment status
	 * @author A-9478
	 * @throws InterruptedException
	 */
	public void verifyPaymentStatus(String paymentStatus) throws InterruptedException 
	{
		waitForSync(5);
		String actStatus = getElementText(sheetName, "txt_paymentStatus;xpath", "Payment status",
				ScreenName);
		if(paymentStatus.equalsIgnoreCase(actStatus))
		{
			onPassUpdate(ScreenName, paymentStatus, actStatus,
					"Payment status code verification ", "Payment status verification");
		}
		else
		{
			onFailUpdate(ScreenName, paymentStatus, actStatus,
					"Payment status code verification ", "Payment status verification");

		}
        
	}
	


}
