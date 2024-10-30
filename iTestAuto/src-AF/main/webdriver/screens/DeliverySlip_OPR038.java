package screens;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class DeliverySlip_OPR038 extends CustomFunctions{
	
	public DeliverySlip_OPR038(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="DeliverySlip_OPR038";
	public String ScreenName="Delivery Slip";
	//public CustomFunctions comm;
	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	
	/**
	 * @author A-9478
	 * Desc: Select checkbox
	 * @throws Exception
	 */
	public void selectCheckbox() throws Exception
	{
		clickWebElement(sheetName, "chbx_selectRow;xpath", "Select checkbox",
				sheetName);
	}
	/**
	 * @author A-9844
	 * Desc: Click Clear
	 * @throws Exception
	 */
	public void clickClear() throws Exception
	{
		clickWebElement(sheetName, "btn_clear;xpath", "click clear button",sheetName);
		waitForSync(3);
	}

	/**
	 * @author A-9844
	 * Desc: Click Reprint
	 * @throws Exception
	 */
	public void clickReprint() throws Exception
	{
		clickWebElement(sheetName, "btn_Reprint;xpath", "click reprint button",sheetName);
		waitForSync(6);
	}
	/**
	 * @author A-9844
	 * Desc: verify table records
	 * @throws Exception
	 */
	public void verifyTableRecords(int verfCols[],String actVerfValues[],String pmKey) throws Exception
	{
		
		verify_tbl_records_multiple_cols(sheetName, "tbl_gatePassEnquiry;xpath", "//td", verfCols, pmKey, actVerfValues);
	}
	/**
	 * @author A-9478
	 * Desc: List By AWB
	 * @throws Exception
	 */
	public void listByAWB(String AWBPrefix,String AWBNo) throws Exception
	{
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id", data(AWBPrefix), "AWB prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_awbNo;id", data(AWBNo), "AWB number", ScreenName);
		clickWebElement(sheetName, "btn_List;xpath", " List button ",
				sheetName);
		waitForSync(5);
	}
	
	/**
	 * @author A-9478
	 * Desc: Click Reprint button and verify
	 * @throws Exception
	 */
	public void verifyReprint() throws Exception
	{
		switchToWindow("storeParent");      
		clickWebElement("DeliverySlip_OPR038", "btn_Reprint;xpath", "Reprint button",
				"DeliverySlip_OPR038");
		waitForSync(5);
		int windowSize=driver.getWindowHandles().size();
		
		try
		{
			if(windowSize>1)
			{
			switchToWindow("child");
			driver.close();
			switchToWindow("getParent");
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR038");
			onPassUpdate(ScreenName, "Reprint functionality in Delivery slip screen", "Reprint functionality is  working in Delivery slip screen", "Reprint window", "Verify Reprint functionality");
			}
			
			
			else
			{
				onFailUpdate(ScreenName, "Reprint functionality in Delivery slip screen", "Reprint functionality is not working in Delivery slip screen", "Reprint window", "Verify Reprint functionality");
			}
		}
		catch(Exception e)
		{
			onFailUpdate(ScreenName, "Reprint functionality in Delivery slip screen", "Reprint functionality is not working in Delivery slip screen", "Reprint window", "Verify Reprint functionality");
			Assert.assertFalse(true, "Delivery Slip Reprint window is not opened");
		}
	}
	
	
	
	
}
