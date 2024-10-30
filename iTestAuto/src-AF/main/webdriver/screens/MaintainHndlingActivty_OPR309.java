package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.testng.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

import com.relevantcodes.extentreports.LogStatus;









import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class MaintainHndlingActivty_OPR309 extends CustomFunctions {

	String sheetName = "MaintainHandlingActivity_OPR309";
	String screenName = "MaintainHandlingActivity: OPR309 ";
	String screenId="OPR309";


	public MaintainHndlingActivty_OPR309(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	/**
	 * @Description... Select worktemplate from dropdown
	 * @author A-10690
	 * 
	 * @throws InterruptedException,IOException
	 */
	public void selectWorkTemplate() throws InterruptedException, IOException {

		selectValueInDropdown(sheetName, "lst_worktemplate;name", "TEMPCHECK", "WORKTEMPLATE", "VisibleText");
	}


	/**
	 * @Description... Enter full awb no in the details popup windo
	 * @author A-10690
	 * @param awb no
	 * @param Shipment prefix
	 * @throws Exception
	 */

	public void enterDetails(String awbNo, String ShipmentPrefix) throws Exception {


		clickWebElement(sheetName, "btn_details;xpath", "Detailsbutton", screenName);
		waitForSync(3);
		switchToWindow("storeParent");
		switchToWindow("child");
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_awbprefix;id", data(ShipmentPrefix), "prefix", screenName);
		enterValueInTextbox(sheetName, "inbx_awbnumebr;id", data(awbNo), "AwbNo", screenName);
		clickWebElement(sheetName, "btn_okbutton;name", "Okbutton", screenName);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR309");

	}

	/**
	 * @Description... Enter Activation time
	 * @author A-10690
	 * @param Activation time

	 * @throws Exception
	 */

	public void enterActivationTime(String currentTime) throws Exception {

		enterValueInTextbox(sheetName, "inbx_activationtime;name", currentTime, "ActivationTime", screenName);

	}

	/**
	 * @Description... Enter Save button
	 * @author A-10690
	 * 
	 * @throws Exception
	 */

	public void enterSave() throws Exception {

		clickWebElement(sheetName, "btn_save;name", "Savebutton", screenName);
		waitForSync(1);

	}



}




