package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ShipperReturnNote_OPR040 extends CustomFunctions {
	
	String sheetName = "ShipperReturnNote_OPR040";
	String screenName = "Shipper Return Note: OPR040";
	String screenId="OPR040";
	

	public ShipperReturnNote_OPR040(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	
/**
 * Description... Enter AWB details
 * @throws InterruptedException
 * @throws AWTException
 */
public void enterAWBdetails(String AWBPre,String AWBNo) throws InterruptedException, AWTException {
       enterValueInTextbox(sheetName, "inbx_AWBPre;name",data(AWBPre), "AWB Prefix", screenId);
       enterValueInTextbox(sheetName, "inbx_AWBNo;name",data(AWBNo), "AWB No", screenId);
       waitForSync(3);
    }

/**
 * Description... Click List Button
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void clickList() throws InterruptedException, AWTException, IOException {
       clickWebElement(sheetName, "btn_List;id", "List Button", screenName);
       waitForSync(3);
   }
/**
* Desc : Mousehover Print button
* @author A-9175
* @throws InterruptedException
*/
public void clickPrint() throws InterruptedException{
	waitForSync(3);
	hover(sheetName, "btn_Print;xpath");
	waitForSync(2);
}
/**
* Desc : Clicking Shipper return print button and click ok
* @author A-9175
* @throws Exception
*/
public void clickReturnPrint() throws Exception{
	
	waitForSync(5);
	clickWebElement(sheetName, "btn_printShipperreturnNote;xpath", "Shipper return Print", screenName);
	waitForSync(3);
}
/**
 * @description : Clicking cancel button
 * @author A-9175
 * @throws Exception
 */

public void clickCancel() throws Exception{
	
	waitForSync(5);
	clickWebElement(sheetName, "btn_cancel;id", "Shipper return Cancel", screenName);
	waitForSync(3);
}

/**
 * @Description : Verifying messages
 * @author A-9175
 * @param msg
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
			handleAlert("Accept","Shipper Return Note");
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
	switchToFrame("contentFrame","OPR040");
}

/**
* Description... Enter Return pieces and weight
* @throws InterruptedException
* @throws AWTException
*/
public void enterReturnPiecesAndWeight(String Pieces,String Weight) throws InterruptedException, AWTException {
	enterValueInTextbox(sheetName, "inbx_ReturnPieces;name",data(Pieces), "Return pieces", screenId);
	enterValueInTextbox(sheetName, "inbx_ReturnWeight;name",data(Weight), "Return weight", screenName);
      waitForSync(3);
  }

/**
* Description... Enter Staff Id and Valid Until date
* @throws InterruptedException
* @throws AWTException
*/
public void enterStaffIdAndValidUntil(String StaffId,String ValidUntilDate) throws InterruptedException, AWTException {
	enterValueInTextbox(sheetName, "inbx_StaffId;id",data(StaffId), "Staff id", screenId);
	enterValueInTextbox(sheetName, "inbx_ValidUntil;name",data(ValidUntilDate), "Valid Until Date", screenName);
      waitForSync(3);
  }

/**
 * Description... Save Button and clicks on Ok in Alert
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void clickSave() throws InterruptedException, AWTException, IOException {
       clickWebElement(sheetName, "btn_Save;id", "Save Button", screenName);
       waitForSync(3);
       switchToFrame("default");
       clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "Ok button", screenName);
       waitForSync(2);
       switchToFrame("contentFrame", "OPR040");
       waitForSync(2);
}



/**
 * @author A-7943
 * @throws InterruptedException
 *             Description : select reason code
 */
public void selectReasonCode(String reasonCode) throws InterruptedException {
	selectValueInDropdown(sheetName, "clk_reasonCode;xpath", reasonCode, "ReasonCode", "VisibleText");
	waitForSync(3);

}

/**
 * @author A-7943
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException
 *             Description : save using the SRN No
 */
public void clickSaveForFetchingSRNNO() throws InterruptedException, AWTException, IOException {
	clickWebElement(sheetName, "btn_Save;id", "Save Button", screenName);
	waitForSync(3);
}

/**
 * @author A-7943
 * @throws IOException
 * @throws Exception
 *             Description : Get the description from the alert
 */
public String handleAlertAndReturnDescription() {
	switchToFrame("default");
	String AlertText = "";
	AlertText = driver.findElement(By.xpath("//*[@id='ui-id-21']/div[1]/div/span")).getText();
	return AlertText;

}

/**
 * @author A-7943 Description : Get the SRN Number
 */
public String getSRNNo(String alertMsg) {
	String value = null;
	for (String exactShipperReturnNote : alertMsg.split("Shipment is returned with Shipper Return Note No - ")) {
		value = exactShipperReturnNote;
	}
	return value;
}

/**
 * @author A-7943
 * 
 *Description : Get the random staff id number
 */
public int getRandomNumber() {
	Random ran = new Random();
	return ran.nextInt(1000);
}

/**
 * @author A-7943
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException
 *             Description : click Ok after Save
 */
public void clickOkAfterSave() throws InterruptedException, AWTException, IOException {

	clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "Ok button", screenName);
	waitForSync(2);
}

/**
 * @author A-7943
 * @throws IOException
 * @throws InterruptedException
 *             Description : List With SRN No
 */
public void listWithSrnNo(String SRNNo) throws InterruptedException, IOException {
	enterValueInTextbox(sheetName, "inbx_srnno;xpath", data(SRNNo), " srn no", screenName);
	clickWebElement(sheetName, "btn_List;id", "List Button", screenName);
	waitForSync(3);

}

/**
 * @author A-7943
 * @throws IOException
 * @throws InterruptedException
 *             Description : click pay button
 */
public void clickPay() throws InterruptedException, IOException {
	clickWebElement(sheetName, "clk_pay;xpath", "click pay", screenName);
	waitForSync(3);

}

/**
 * @author A-7943
 * @throws IOException
 * @throws InterruptedException
 *             Description : click Acquit shipment button
 */
public void clickAcquitShipment() throws InterruptedException, IOException {
	clickWebElement(sheetName, "clk_acquitShipment;xpath", "Acquit shipment", screenName);
	switchToFrame("default");
	clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "Ok button", screenName);
	waitForSync(2);
	switchToFrame("contentFrame", "OPR040");
	waitForSync(2);
}

/**
 * @author A-7943
 * @throws IOException
 * @throws InterruptedException
 *             Description : click AWB Details button
 */
public void clickAwbDetails(String awbNo) throws InterruptedException, IOException {
	try {
		clickWebElement(sheetName, "clk_awbDetails;xpath", "AWB Details Button", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR040");
		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);
		getTextAndVerify(sheetName, "lbl_awbNo;xpath", "awb number", screenId,
				"Verification of awb number in AWB Details", data(awbNo), "contains");
		writeExtent("Pass",
				"Verified  the details of the awb no - " + data(awbNo) + "in this" + screenId + " Page");
		waitForSync(2);
	} catch (Exception e) {
		writeExtent("Fail", "Element not found in" + screenId + " Page");
	}
	clickWebElement(sheetName, "clk_closeAwbDetails;xpath", "close button", screenName);
	waitForSync(2);
	switchToFrame("default");
	switchToFrame("contentFrame", "OPR040");
}

/**
 * @author A-7943
 * @throws IOException
 * @throws Exception
 *             Description : Verify the status
 */
public void verifySRNStatus(String sRNStatus) throws InterruptedException {

	switch (sRNStatus) {

	case "Issued":
		String actTextForIssued = driver.findElement(By.xpath("//span[contains(text(),'Issued')]")).getText();
		System.out.println("Actual text is--" + actTextForIssued);
		String expTextForIssued = "Issued";
		if (actTextForIssued.equals(expTextForIssued)) {
			System.out.println("Shipper return note status is verified as issued");
			writeExtent("Pass", "Shipper return note status is verified as issued");

		} else {
			System.out.println("Shipper return note status is not verified as issued");
			writeExtent("Fail", "Shipper return note status is no verified as issued");
		}
		break;

	case "Paid":
		String actTextForPaid = driver.findElement(By.xpath("//span[contains(text(),'Paid')]")).getText();
		System.out.println("Actual text is--" + actTextForPaid);
		String expTextForPaid = "Paid";
		if (actTextForPaid.equals(expTextForPaid)) {
			System.out.println("Shipper return note status is verified as Paid");
			writeExtent("Pass", "Shipper return note status is verified as Paid");

		} else {

			System.out.println("Shipper return note status is not verified as Paid");
			writeExtent("Fail", "Shipper return note status is  not verified as Paid");
		}
		break;
	case "Delivered":
		String actTextForDelivered = driver.findElement(By.xpath("//span[contains(text(),'Delivered')]")).getText();
		System.out.println("Actual text is--" + actTextForDelivered);
		String expTextForDelivered = "Delivered";
		if (actTextForDelivered.equals(expTextForDelivered)) {
			System.out.println("Shipper return note status is verified as Delivered");
			writeExtent("Pass", "Shipper return note status is verified as Delivered");

		} else {

			System.out.println("Shipper return note status is not verified as Delivered");
			writeExtent("Fail", "Shipper return note status is  not verified as Delivered");
		}

	}
	Thread.sleep(2000);
}








}
