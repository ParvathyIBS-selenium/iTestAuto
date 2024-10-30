package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class RampHandling_WHS019 extends CustomFunctions {
	
	String sheetName = "RampHandling_WHS019";
	String screenName = "Ramp Handling : WHS019";
	String screenId="WHS019";	

	public RampHandling_WHS019(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
/**
 * Description... Verify Ramp Handling Details
 * @param verfCols
 * @param actVerfValues
 * @param ULDNumber
 * @throws InterruptedException
 * @throws IOException 
 */
public void verifyRampHandlingDetails(int verfCols[], String actVerfValues[],String ULDNumber) throws InterruptedException, IOException {
		
	
		verify_tbl_records_multiple_cols(sheetName, "tbl_rampHandling;xpath", "//td", verfCols, ULDNumber,
				actVerfValues);	
		waitForSync(3);
		
	}
/**
 * Description... Verify Ramp Handling Details
 * @param verfCols
 * @param actVerfValues
 * @param ULDNumber
 * @throws InterruptedException
 */
public void verifyRampHandlingDetails1(int verfCols[], String actVerfValues[],String ULDNumber) throws InterruptedException {
	
	verify_tbl_records_multiple_cols_RampHandle(sheetName, "tbl_rampHandling;xpath", verfCols, ULDNumber,
			actVerfValues);	
	waitForSync(3);
	
}

}
