package screens;

import java.awt.AWTException;
import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
public class RequestPalletMovement_ADD015 extends CustomFunctions {

	String sheetName = "RequestPalletMovement_ADD015";
	String screenName = "RequestPalletMovement_ADD015";
	String screenId="ADD015";	

	public RequestPalletMovement_ADD015(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}


	/**
	 * Description... Enter Date Details
	 * @param fromDate
	 * @param toDate
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void enterDateDetails(String fromDate,String toDate) throws AWTException, InterruptedException
	{
		//from date
		enterValueInTextbox(sheetName, "inbx_fromDate;id", data(fromDate), "From Date", screenName);
		//from date
		enterValueInTextbox(sheetName, "inbx_toDate;id", data(toDate), "To Date", screenName);
		keyPress("TAB");
waitForSync(1);

	}

	/**
	 * Description... Click List Awb Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void list() throws InterruptedException, IOException
	{
		//List button
		
		javaScriptToclickElement(sheetName, "btn_list;xpath", "list Button", screenName);
		waitForSync(3);	
	}

	/*** @author A-10690
	 * Desc - Verify TO details
	 * @param uldnumber
	 * @param expected TO details
	 * @throws AWTException 
	 */
	public void verifyTODetails(String uldNumber,String[] toDetails) throws InterruptedException, AWTException{

		int i=0;

		String locator=xls_Read.getCellValue(sheetName, "txt_orglocation;xpath");
		locator=locator.replace("uld",data(uldNumber));
		String actorigin=driver.findElement(By.xpath(locator)).getText();
		String locator1=xls_Read.getCellValue(sheetName, "txt_vehicletype;xpath");
		locator1=locator1.replace("uld",data(uldNumber));
		String actvehicleType=driver.findElement(By.xpath(locator1)).getText();
		String locator2=xls_Read.getCellValue(sheetName, "txt_source;xpath");
		locator2=locator2.replace("uld",data(uldNumber));
		String actsource=driver.findElement(By.xpath(locator2)).getText();
		String locator3=xls_Read.getCellValue(sheetName, "txt_Tostatus;xpath");
		locator3=locator3.replace("uld",data(uldNumber));
		String acttoStatus=driver.findElement(By.xpath(locator3)).getText();

        String exp=data(toDetails[i]);

		if((actorigin.equalsIgnoreCase(exp))&&(actvehicleType.equalsIgnoreCase(data(toDetails[i+1])))&&(actsource.equalsIgnoreCase(data(toDetails[i+2])))&&(acttoStatus.equalsIgnoreCase(data(toDetails[i+3]))))
		{
			writeExtent("Pass","Successfully verified the details as "+toDetails[i]+","+toDetails[i+1]+", "+toDetails[i+2]+", "+toDetails[i+3]+ " on "+screenName);
		}
		else{
			writeExtent("Fail","Failed to verify the details  on "+screenName);
		}

	

}
	
	/*** @author A-10690
	 * Desc - Verify  AGV TO details
	 * @param uldnumber
	 * @param expected TO details
	 * @throws AWTException 
	 */
	public void verifyAGVTODetails(String uldNumber,String[] toDetails) throws InterruptedException, AWTException{

		int i=0;

		String locator=xls_Read.getCellValue(sheetName, "txt_fwdlocation;xpath");
		locator=locator.replace("uld",data(uldNumber));
		String actorigin=driver.findElement(By.xpath(locator)).getText();
		String locator1=xls_Read.getCellValue(sheetName, "txt_vehicletype;xpath");
		locator1=locator1.replace("uld",data(uldNumber));
		String actvehicleType=driver.findElement(By.xpath(locator1)).getText();
		String locator2=xls_Read.getCellValue(sheetName, "txt_feedback;xpath");
		locator2=locator2.replace("uld",data(uldNumber));
		String actFeedback=driver.findElement(By.xpath(locator2)).getText();
		String locator3=xls_Read.getCellValue(sheetName, "txt_Tostatus1;xpath");
		locator3=locator3.replace("uld",data(uldNumber));
		String acttoStatus=driver.findElement(By.xpath(locator3)).getText();

        String exp=data(toDetails[i]);
    	if((actorigin.equalsIgnoreCase(exp))&&(actvehicleType.equalsIgnoreCase(data(toDetails[i+1])))&&(actFeedback.contains(data(toDetails[i+2])))&&(acttoStatus.equalsIgnoreCase(data(toDetails[i+3]))))
		{
			writeExtent("Pass","Successfully verified the details as "+toDetails[i]+","+toDetails[i+1]+", "+toDetails[i+2]+", "+toDetails[i+3]+ " on "+screenName);
		}
		else{
			writeExtent("Fail","Failed to verify the details  on "+toDetails[i]+","+toDetails[i+1]+", "+toDetails[i+2]+", "+toDetails[i+3]+"on"+ screenName);
		}

	

}
	/**
	 * @Description : verify AGVTO removed from request pallet movement screen
 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void verifyAGVTORemoved() throws InterruptedException, AWTException, IOException {


		String locator3=xls_Read.getCellValue(sheetName, "txt_ToRemoved;xpath");
		if(driver.findElements(By.xpath(locator3)).size()==1)
		{
			writeExtent("Pass","Successfully verified the TO details removed from"+screenName);
		}
		else{
			writeExtent("Fail","Failed to verified the TO details removed from"+screenName);
		}
		}

	/*** @author A-10690* Desc - enter ULD number
	 * @param uldnumber
	 * @throws InterruptedException
	 */
	
	public void enterULDNumber(String uldNumber) throws InterruptedException
	{
		//Enter uld number
		enterValueInTextbox(sheetName, "inbx_uldNumber;xpath", data(uldNumber), "ULD number", screenName);
	}
	/**
	 * @Description : verify ULD list details
	 * @author A-10690
	 * @param  ULDNUMBER
	* @param  Equipment 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */


	public void verifyULDdetails(String uldNumber,String[] equipment)throws InterruptedException, AWTException, IOException {

		try{

			for(int i=0;i<equipment.length;i++)
			{
				String locator = xls_Read.getCellValue(sheetName, "inbx_ULDdetails;xpath");
				locator=locator.replace("ULD",data(uldNumber));
				locator=locator.replace("*",equipment[i]);

				int size=driver.findElements(By.xpath(locator)).size();

				if(size==1){
					writeExtent("Pass", "Verified the ULD details " + data(uldNumber) + "with value "+ equipment[i] +"on " + screenName);
				}
				else{
					writeExtent("Fail", "Failed to verify ULD details  " + data(uldNumber) + "with value  "+ equipment[i] +"on " + screenName);
				}
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not verify ULD  details on " + screenName);
		}

	}

	/**
	 * @Description : select the flight and  click destocking
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void clickCancelTO() throws InterruptedException, AWTException, IOException {


		clickWebElement(sheetName, "btn_cancelTO;xpath", "cancel TO button", screenName);
		waitForSync(3);
	}

	/**
	 * @Description : select the flight and  click destocking
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void selectULD(String uldNumber)throws InterruptedException, AWTException, IOException {


		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		String uldRow = xls_Read.getCellValue(sheetName, "chkbox_ULDNumberCheckBox;xpath");
		uldRow=uldRow.replace("*", data(uldNumber));
		driver.findElement(By.xpath(uldRow)).click();

	}
}