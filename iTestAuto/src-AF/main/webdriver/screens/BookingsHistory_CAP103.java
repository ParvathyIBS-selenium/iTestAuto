package screens;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

/**
* Author : A-8459
* Date Created/ Modified : 22/02/2019
* Description : To perform operations on Bookings History Screen
*/

public class BookingsHistory_CAP103 extends CustomFunctions {
	public CustomFunctions customFuction;

	public BookingsHistory_CAP103(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelreadwrite, xls_Read);
	}
	public String sheetName = "BH";
	public String screenName = "BookingsHistory";
	
	/**
     * Description....To verify the page title
     * @throws Exception
     */
	public void checkPageTitle() throws Exception
	{
		
		waitForSync(3);
		String expTitle="Booking History";
		String actTitle=driver.getTitle();
		if(expTitle.contains(actTitle))
			test.log(LogStatus.PASS, "Screen Title is verified");
		else
			test.log(LogStatus.FAIL, "Found AWB");
		
		
	}
	/**
     * Description....To click on list button
     * @throws Exception
     */
	public void clickList() throws Exception
	{
		waitForSync(3);
		clickWebElement(sheetName, "click_listBttn;xpath", "List ", screenName);
		
	}
	/**
     * Description....To verify details(psc,weight,vol)
     * @throws Exception
     */
	public void checkDetails(String psc,String weight,String vol) throws Exception
	{
		String actPsc = getElementText(sheetName, "noOf_pcs;xpath", "Psc", screenName);
		String actWeight = getElementText(sheetName, "total_weight;xpath", "Weight", screenName);
		String actVol = getElementText(sheetName, "total_volume;xpath", "volume", screenName);
		System.out.println(actPsc+actWeight+actVol);
		verifyScreenText(sheetName, data(psc), actPsc, "Verify Station ", screenName);
		verifyScreenText(sheetName, data(weight), actWeight, "Verify Station ", screenName);
		verifyScreenText(sheetName, data(vol), actVol, "Verify Station ", screenName);
	}
	/**
     * Description....To verify AWB which is specified on the screen
     * @throws Exception
     */
	public void verifyAwb(String awb) throws Exception
	{
		waitForSync(4);
		String actAwb=getAttributeWebElement(sheetName, "input_awbNo;xpath", "AWB verify", "value", screenName);
		System.out.println(actAwb+awb);
		verifyScreenText(sheetName, awb, actAwb, "Verify Awb ", screenName);
	}
/**
 * Description.... List the AWB Number
 * @param AWBNo
 * @throws Exception
 */
public void listAWB(String AWBNo)throws Exception{
		enterValueInTextbox(sheetName,"list_AWB;xpath",data(AWBNo),"AWB No", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "click_listBttn;xpath", "List ", screenName);
	}
/**
 * Description.... Verify the SCC Code
 * @param SCCUpdate
 * @throws Exception
 */
	public void verifySCC(String SCCUpdate)throws Exception{
		String actual=driver.findElement(By.xpath("(//td[@class='iCargoTableDataTd'])[8]")).getText();
		String expected=data(SCCUpdate);
		if(actual.contains(expected)){
			verifyScreenText(sheetName, expected, actual, "Verify SCC codes", "Capture AWB");
		}
		else{
			verifyScreenText(sheetName, expected, actual, "Verify SCC codes", "Capture AWB");
		}
	}

}
