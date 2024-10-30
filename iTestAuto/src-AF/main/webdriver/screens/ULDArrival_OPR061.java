package screens;
import java.awt.AWTException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ULDArrival_OPR061 extends CustomFunctions  {
	String sheetName = "ULDArrival_OPR061";
	String screenName = "ULD Arrival";
	
	public ULDArrival_OPR061(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		

}
	CustomFunctions comm = new CustomFunctions(driver, excelreadwrite, xls_Read);
	/**
	 * Description... List Flight
	 * @param fltNo
	 * @param fltDate
	 * @throws Exception
	 */
	public void listFlight(String fltNo, String fltDate) throws Exception{
		enterValueInTextbox(sheetName,"inbx_fltNo;xpath",fltNo,"Flight Number", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName,"inbx_fltDt;xpath",fltDate,"Flight Date", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		waitForSync(2);
		clickWebElement(sheetName,"btn_List;xpath","List flight", screenName);

	}
	/**
	 * Description... Verify ATD Error and click on Close Button
	 * @param error
	 * @throws Exception
	 */
	public void verifyATDError(String error)throws Exception{
		
		String actualAlerttext=driver.findElement(By.xpath("//*[@class='ic-error-container']")).getText();
		String expectedAlertText=data(error);
		comm.verifyScreenText(
				screenName,
				expectedAlertText,
				actualAlerttext,
				"Alert text",
				"ATD time not captured");

		clickWebElement(sheetName, "btn_Close;xpath",
				"Close Button", screenName);
	}
}
