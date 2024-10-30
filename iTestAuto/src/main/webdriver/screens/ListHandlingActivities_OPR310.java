package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListHandlingActivities_OPR310 extends CustomFunctions {
	public ListHandlingActivities_OPR310(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	public String performActivityProppath = "\\src\\resources\\PerformActivityTime.properties";
	public String sheetName = "ListHandlingActivities_OPR310";
	public String screenName = "ListHandlingActivities";
	CustomFunctions comm = new CustomFunctions(driver, excelreadwrite, xls_Read);
	

	
	
	/**
	 * @author A-7271
	 * @param awbNumber
	 * @throws InterruptedException
	 * Description : list AWB
	 * @throws IOException 
	 */
	public void listAWB(String awbPrefix,String awbNumber) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_AWBPre;name", data(awbPrefix), "AWB Prefix",
				screenName);
		enterValueInTextbox(sheetName, "inbx_AWBNumber;name", data(awbNumber), "AWB Number",
				screenName);
		clickWebElement(sheetName, "btn_list;name", "List Button", screenName);
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Desc: Select checkbox
	 * @throws Exception
	 */
	public void selectCheckbox(String awbno) throws Exception
	{

		 String time=getPropertyValue(performActivityProppath,"waitFor");
         int t=Integer.parseInt(time);
         System.out.println(t);
		for(int i=0;i<=t;i++)
		{
			clickWebElement(sheetName, "btn_list;name", "List Button", screenName);
			waitForSync(3);
			try{

				String locator = xls_Read.getCellValue(sheetName, "btn_status;xpath");
				locator=locator.replace("awb", data(awbno));
				String Colour = driver.findElement(By.xpath(locator)).getAttribute("fill");

				if (Colour.equals("#ff0000")){

					String awblocator = xls_Read.getCellValue(sheetName, "chk_awbRow;xpath");
					awblocator=awblocator.replace("*", data(awbno));

					driver.findElement(By.xpath(awblocator)).click();		
					waitForSync(2);
					writeExtent("Pass", "Sucessfully selected the AWB in created status");
					break;
				}
				
				else{
					
					waitForSync(3);
					i++;
				}
			}catch (Exception e) {
				writeExtent("Fail", "Failed to select the AWB in created status");
			}

			}


		}

	/**
	 * @author A-9844
	 * @Description : Clicking Perform Activity Button
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickPerformActivityButton() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_performActivity;xpath", "Perform Activity Button", screenName);
		waitForSync(5);

	}
	/**
	 * @author A-7271
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * Description : Verify work details
	 * @throws IOException 
	 */
	public void verifyWorkDetails(int verfCols[], String actVerfValues[],
			String pmKey) throws IOException
	{

		verify_tbl_records_multiple_cols(sheetName, "tbl_workList;xpath",
				"//td", verfCols, pmKey, actVerfValues);
	}
}
