package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class SecurityAndScreening_OPR287 extends CustomFunctions {

	public SecurityAndScreening_OPR287(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "SecurityAndScreening_OPR287";
	public String screenName = "Security And Screening";

	public void listAWB(String awbNo, String ShipmentPrefix, String ScreenName)
			throws InterruptedException, IOException {

		awbNo = getPropertyValue(proppath, "AWBNo");

		System.out.println("AWBnumber is ---" + awbNo);
		waitForSync(2);
		enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;name",
				data(ShipmentPrefix), "Shipment Prefix", ScreenName);
		enterValueInTextbox("Generic_Elements", "inbx_AWBnumber;xpath", awbNo,
				"AWB No", ScreenName);
		clickWebElement(sheetName, "btn_list;xpath", "List Button", ScreenName);
		waitForSync(4);

	}

	public void securityAndScreening(String SecSCC) throws InterruptedException, IOException {
		switchToFrame("default");
		clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR287");
		enterValueInTextbox(sheetName, "inbx_SCC;xpath", data(SecSCC),
				"SecSCC", screenName);
		Thread.sleep(1000);
		clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath",
				"Security Checkbox", screenName);
		clickWebElement(sheetName, "btn_OK;xpath", "Save Button", screenName);
		Thread.sleep(2000);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_OK_xpath", "Yes Button", screenName);
	}

public void updateSCCFirst(String SecSCC) throws Exception {
              switchToFrame("default");
              clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
              switchToFrame("contentFrame", "OPR339");
              waitForSync(1);
              clickWebElement(sheetName, "chk_SecurityDataRcvd;xpath",
                           "Security Checkbox", screenName);
              clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName);
              waitForSync(2);
              clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);
              waitForSync(2);
              enterValueInTextbox(sheetName, "inbx_SCC;xpath", data(SecSCC),"SecSCC", screenName);
              waitForSync(2);
              clickWebElement(sheetName, "btn_updateSCCok;name", "OK Button ", screenName);
              waitForSync(2);
              clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
              waitForSync(2);
              
       }

public void deleteSCC(String SCC) throws Exception
       {
              waitForSync(2);
              clickWebElement(sheetName, "tab_ShipmentDetails;id", "Shipment details", screenName);
              waitForSync(2);
              clickWebElement(sheetName, "icn_Editshipment;xpath", "Edit Shipment details", screenName);
              waitForSync(2);
              driver.findElement(By.xpath("//span[contains(text(),'"+SCC+"')]//i")).click();
              clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
              Thread.sleep(3000);
              switchToFrame("default");
              clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
              Thread.sleep(2000);
              switchToFrame("contentFrame", "OPR339");
              
       }

}