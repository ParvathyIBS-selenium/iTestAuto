package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AWB_Deconsolidation_OPR286 extends CustomFunctions {

	public AWB_Deconsolidation_OPR286(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	String sheetName = "AWB_Deconsolidation_OPR286";
	public String screenName = "AWB_Deconsolidation_OPR286";

	/**
	 * Description... Provide Breakdown location and Pieces
	 * 
	 * @param location
	 * @param pieces
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void provideDetails(String location, String pieces) throws InterruptedException, AWTException {
		enterValueInTextbox(sheetName, "inbx_location;xpath", data(location), "Breakdown location", screenName);
		enterValueInTextbox(sheetName, "inbx_pieces;xpath", data(pieces), "Pieces", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
	}

	/**
	 * Description... Click Save button and Handle alert	 * 
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void clickSave() throws InterruptedException, AWTException, IOException {
		clickWebElement(sheetName, "btn_Save;xpath", "Save button", screenName);
		Thread.sleep(3000);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);

	}
/**
 * Description... Verify Deconsolidation Button is Enabled
 * @throws Exception
 */
	public void checkDeconsolidationEnabled() throws Exception {
		WebElement e = driver
				.findElement(By.xpath("//*[@id='CMP_OPERATIONS_FLTHANDLING_CTO_DECONSOLIDATION_COMPLETE']"));
		boolean check = e.isEnabled();

		if (check) {
			System.out.println("Button is disabled");
		} else {
			System.out.println("Button not disabled");

		}
	}


/**
		 * Description... Provide Breakdown location and Pieces
		 * 
		 * @param location
		 * @param pieces
		 * @throws InterruptedException
		 * @throws AWTException
		 */
		public void provideDeconsolidationDetails(ArrayList<String> locationPiecesDetails, int[] column) throws InterruptedException, AWTException
		
		{
			
			 String tableBody = xls_Read.getCellValue(sheetName, "tbl_deconsolidation;xpath");
			 String xpath1 = xls_Read.getCellValue(sheetName, "tbl_deconsolidation;xpath");
			 List<WebElement> rows = driver.findElements(By.xpath(xpath1.substring(1)));
			 String dynXpath = "";
			 
			 // Enter val in 1st row - 1st row xpath differs slightly from other rows, so handled separately
			 
			 dynXpath = tableBody + "[1]//td[" +  column[0] +  "])[2]//div//input";
			 System.out.println("dynXpath is---" + dynXpath);
			 clickWebElement(dynXpath, "deconsolidation table breakdown Location", screenName);
			
			 enterValueInTextbox(dynXpath, data(locationPiecesDetails.get(0)), "Deconsolidation tbl details", screenName);
			 
			 dynXpath = tableBody + "[1]//td[" +  column[1] +  "])[2]//div//input";
			 ele = driver.findElement(By.xpath(dynXpath));

			 System.out.println("dynXpath is---" + dynXpath);
			 enterValueInTextbox(dynXpath, data(locationPiecesDetails.get(1)), "Deconsolidation tbl details", screenName);
			 ele.sendKeys(Keys.TAB);
			 
			 // Enter val in other rows
			 
			 for(int i=2; i< rows.size(); i++){
				 
				 for(int j=0; j< column.length; j++){
				 dynXpath = tableBody + "[" + i +  "]//td[" +  column[j] +  "])[1]//div//input";
				 System.out.println("dynXpath is---" + dynXpath);
				 enterValueInTextbox(dynXpath, data(locationPiecesDetails.get(j)), "Deconsolidation tbl details", screenName);
					
			 }
		}
			 
			 keyPress("TAB");
			keyRelease("TAB");
			Thread.sleep(2000);
		}

}
