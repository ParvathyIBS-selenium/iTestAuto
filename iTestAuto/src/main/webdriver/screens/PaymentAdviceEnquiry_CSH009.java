package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class PaymentAdviceEnquiry_CSH009 extends CustomFunctions {

	public PaymentAdviceEnquiry_CSH009(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "PaymentAdviceEnquiry_CSH009";
	public String ScreenName = "PaymentAdviceEnquiry: CSH009";
	String screenId = "CSH009";
	
	/**
	 * @author A-7271
	 * @param awbPrefix
	 * @param awbNumber
	 * Desc : List awb
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
public void listAWB(String awbPrefix,String awbNumber) throws InterruptedException, IOException
{
	enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(awbPrefix), "awb Prefix", ScreenName);
	enterValueInTextbox(sheetName, "inbx_masterDocNum;name", data(awbNumber), "awb Number", ScreenName);
	 clickWebElement(sheetName, "btn_list;name", "List button", ScreenName);
     waitForSync(5);
}
/**
 * Description... Click Check Box
 * 
 * @param pmyKey
 * @throws InterruptedException
 */
public void clickPaymentAdvice(String pmyKey) throws InterruptedException {

	String locator=xls_Read.getCellValue(sheetName, "chkBox_paymentAdvice;xpath");
	locator=locator.replace("PaymentAdviceNo", data(pmyKey));
	driver.findElement(By.xpath(locator)).click();
}
/**
 * Description... Click AsIsExecute Button
 * @throws Exception
 */
public void regeneratepaymentAdvice() throws Exception {
	try
	{
		
		waitForSync(12);
		switchToFrame("default");
		while(driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).isDisplayed())
		{
			driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).click();
			waitForSync(4);
		}

	}


	catch(Exception e)
	{
            
	}

	finally
	{
		switchToFrame("contentFrame", "CSH009");
	}
      }


/**
 * @author A-10330
 * @param cashierid
 * @throws InterruptedException
 * @throws IOException
 */

public void enterCashierId(String cashierid) throws InterruptedException, IOException
{
	enterValueInTextbox(sheetName, "inbx_cashierid;id", data(cashierid), "Cashier id", ScreenName);
	 clickWebElement(sheetName, "btn_list;name", "List button", ScreenName);
	 waitTillScreenload(sheetName, "btn_cancelPaymentAdvice;id","Cancel Payment advice button", ScreenName);
	}


/**
 * @author A-7271
 * @throws Exception
 */
public void clickCancelPaymentAdvice() throws Exception {

	switchToWindow("storeParent");
	clickWebElement(sheetName, "btn_cancelPaymentAdvice;id", "Cancel button", ScreenName);
	waitForSync(5);
	switchToWindow("multipleWindows");
	enterValueInTextbox(sheetName, "inbx_paymentAdviceRemarks;id", "Cancelled payment advice", "Cancelled Payment Advice Remarks", ScreenName);
	clickWebElement(sheetName, "btn_reasonCodeOk;id", "OK button of cancel payment advice details", ScreenName);
	waitForSync(5);
	switchToWindow("getParent");
	switchToFrame("default");
	switchToFrame("contentFrame","CSH009");
	
}
/**
 * @author A-7271
 * @param verfCols
 * @param actVerfValues
 * @param pmKey
 * @param msgType
 * @param isAssertreq
 * @throws InterruptedException
 * @throws IOException
 * Desc : Verify payment advice details
 */
public void verifyPaymentAdviceDetails(int verfCols[], String actVerfValues[],
		String pmKey,boolean isAssertreq) throws InterruptedException, IOException {
	

	verify_tbl_records_multiple_cols(sheetName, "table_paymentAdviceEnquiry;xpath",
			"//td", verfCols, pmKey, actVerfValues,isAssertreq);
}

}
