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

public class DeliveryReturn_OPR036 extends CustomFunctions{
	public DeliveryReturn_OPR036(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="DeliveryReturn_OPR036";
	public String ScreenName="DeliveryReturn";
	//public CustomFunctions comm;
	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	
	
	/**
     * @author A-9478
     * @param awbPrefix
     * @param awbNo
     * @throws InterruptedException
     * Description : List with awbprefix and awbNo
	 * @throws IOException 
     */
	public void ListByAWB(String awbPrefix, String awbNo) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_awbPrefix;name", data(awbPrefix), "AWB prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_awbNo;name", data(awbNo), "AWB number", ScreenName);
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
	}
	
	
	/**
     * @author A-9478
     * @param pieces
     * @param weight
     * @throws InterruptedException
     * Description : Enter number of pieces and weight to return
     */
	public void enterNumberOfPiecesAndWeight(String pieces, String weight) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_returnPieces;xpath", data(pieces), "Return Pieces", ScreenName);
		enterValueInTextbox(sheetName, "inbx_returnWeight;xpath", data(weight), "Return Weight", ScreenName);
		
	}
	/**
     * @author A-9478
     * @param pieces
     * @param weight
     * @throws InterruptedException
     * Description : Enter location
     */
      public void enterLocation(String location) throws InterruptedException
      {
            enterValueInTextbox(sheetName, "inbx_location;id", data(location), "Location", ScreenName);
            
      }

	/**
     * @author A-9478
     * @param pieces
     * @param weight
     * @throws InterruptedException
     * Description : Enter number of pieces and weight to return
     */
	public void selectReasonCode(String ReasonCode) throws InterruptedException
	{
		By ele = getElement(sheetName,"list_reasonCode;name");
		selectValueInDropdownWebElement(driver.findElement(ele),ReasonCode, "Reason Code", "VisibleText");
		
		
	}
	/**
	 * Desc : Entering SU
	 * @author A-9175
	 * @param su
	 * @throws InterruptedException
	 */
	public void enterSU(String su) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_SU;name", data(su), " SU ", ScreenName);
		
		
	}
	
	/**
	 * Desc : Checking scc
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void selectSCC() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", ScreenName);
		waitForSync(1);
		
		
		/*clickWebElement(sheetName, "chkBox_SCC;id", "Checkbox SCC", ScreenName);
		waitForSync(1);*/
		
		driver.findElement(By.xpath("(//span[contains(.,'Check all')])[2]")).click();
		waitForSync(1);
		clickWebElement(sheetName, "btn_SCC;id", "Button SCC", ScreenName);
		waitForSync(1);
	}


	
	/**
     * @author A-9478
     * @throws InterruptedException
     * Description : Clicks on Save button
	 * @throws IOException 
     */
	public void saveInOPR036() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", ScreenName);
		waitForSync(3);
		verifyElementDisplayed(sheetName,"htmlDiv_save;xpath", " Save", ScreenName, "Save toast message");
	}
	
	
	
	
}
