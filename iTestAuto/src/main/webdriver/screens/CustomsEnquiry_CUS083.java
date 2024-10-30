package screens;

import org.testng.Assert;
import java.awt.AWTException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CustomsEnquiry_CUS083 extends CustomFunctions {
	public CustomsEnquiry_CUS083(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "CustomsEnquiry_CUS083";
	public String ScreenName = "CustomsEnquiry_CUS083";

	/**
	 * Description : To list AWB on the screen
	 * 
	 * @param stationCode
	 *            : shipment prefix
	 * @param AWBNumber
	 * @throws Exception
	 */
	public void listAWB(String stationCode, String AWBNumber) throws Exception {
		enterValueInTextbox(sheetName, "inbx_ShipmentPrefix;name", stationCode, "Station code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AwbNo;name", AWBNumber, "AWB number", ScreenName);
		enterValueInTextbox(sheetName, "inbx_FromDate;id", createDateFormat("dd-MMM-YYYY", -1, "DAY", ""), "From date",
				ScreenName);
		enterValueInTextbox(sheetName, "inbx_Todate;id", createDateFormat("dd-MMM-YYYY", 1, "DAY", ""), "To date",
				ScreenName);

		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);
	}

	public void verifyShipmentCustomsDetails(String AWBNumber, int[] verfCols, String[] actVerfValues)
			throws Exception {
		verify_tbl_records_multiple_cols(sheetName, "tbl_ShipmentCustomsDetails;xpath", "//td", verfCols, AWBNumber,
				actVerfValues);
	}

	public void verifyCustomsInformation(String FlightNo, int[] verfCols, String[] actVerfValues,
			String expCustomsStatus) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement ele = (WebElement) js.executeScript("return document.getElementsByTagName('circle')[0]");
		ele.click();
		waitForSync(4);
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_CustomsInformation;xpath", "//td", verfCols, FlightNo,
				actVerfValues);
		String actCustomsStatus = getElementText(sheetName, "txt_CustomsStatus;xpath", "Customs status code",
				ScreenName);

		if (actCustomsStatus.contains(expCustomsStatus)) {
			System.out.println("found true for " + actCustomsStatus);

			onPassUpdate(ScreenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		} else {
			onFailUpdate(ScreenName, expCustomsStatus, actCustomsStatus,
					"Customs status code verification against " + FlightNo, "Customs status code verification");

		}

	}


public void verifyAdditionalInfo_WithRowCount(String AWBNumber,int rowCount,  String[] ExpVerfValues)
			throws Exception {
		
		String dynXpath = xls_Read.getCellValue(sheetName, "tbl_ShipmentCustomsDetails;xpath") + "[contains(.,'"
				+ AWBNumber + "')]" + xls_Read.getCellValue(sheetName, "lnk_clickToView;xpath");
		ele = findDynamicXpathElement(dynXpath, "Click to view link ", ScreenName);
		clickWebElement(ele, "Click to view link ", ScreenName);
		List <WebElement> eleList = returnListOfElements(sheetName, "tbl_AdditionalInfo;xpath");
		for(int i = 0; i<rowCount; i++){
			
			if (eleList.get(i).getText().contains(ExpVerfValues[i])) {
				System.out.println("found true for " + eleList.get(i).getText());

				onPassUpdate(ScreenName, ExpVerfValues[i], eleList.get(i).getText(),
						"Customs information verification for "+ExpVerfValues[i], "Customs information verification");

			} else {
				onFailUpdate(ScreenName, ExpVerfValues[i], eleList.get(i).getText(),
						"Customs information verification for "+ExpVerfValues[i], "Customs information verification");

			}
		}
}

	public void verifyAdditionalInfo(String AWBNumber, String[] ExpVerfValues)
			throws Exception {
		
		String dynXpath = xls_Read.getCellValue(sheetName, "tbl_ShipmentCustomsDetails;xpath") + "[contains(.,'"
				+ AWBNumber + "')]" + xls_Read.getCellValue(sheetName, "lnk_clickToView;xpath");
		ele = findDynamicXpathElement(dynXpath, "Click to view link ", ScreenName);
		clickWebElement(ele, "Click to view link ", ScreenName);
		List <WebElement> eleList = returnListOfElements(sheetName, "tbl_AdditionalInfo;xpath");
		for(int i = 0; i<eleList.size(); i++){
			
			if (eleList.get(i).getText().contains(ExpVerfValues[i])) {
				System.out.println("found true for " + eleList.get(i).getText());

				onPassUpdate(ScreenName, ExpVerfValues[i], eleList.get(i).getText(),
						"Customs information verification for "+ExpVerfValues[i], "Customs information verification");

			} else {
				onFailUpdate(ScreenName, ExpVerfValues[i], eleList.get(i).getText(),
						"Customs information verification for "+ExpVerfValues[i], "Customs information verification");

			}
		}

	}

	/**
	 * Description : To select customs authority
	 * @param CustomsAuthority
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void selectCustomsAuthority(String CustomsAuthority) throws InterruptedException, AWTException {

		selectValueInDropdown(sheetName,"lst_CustomsAuthority;id",CustomsAuthority,"Customs Authority dropdown","VisibleText");
		waitForSync(5);

	}

}