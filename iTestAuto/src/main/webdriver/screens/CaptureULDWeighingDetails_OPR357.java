package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CaptureULDWeighingDetails_OPR357 extends CustomFunctions {
	
	public CaptureULDWeighingDetails_OPR357(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
	super(driver, excelReadWrite, xls_Read2);
	
}

	String sheetName = "CaptureULDWeighingDet_OPR357"; 
	public String screenName="CaptureULDWeighingDetails_OPR357";
	
	/**
	 * A-7271
	 * search Screen
	 */
	public void searchScreen(String ScreenID, String ScreenName) throws InterruptedException {
		
		
		try {
			waitForSync(4);
			
			
			String sheetName = "Login";
			clickWebElement(sheetName, "inbx_searchScreen;xpath", "Screen Search Field", ScreenID);
			enterValueInTextbox(sheetName, "inbx_searchScreen;xpath", ScreenID, ScreenID, ScreenName);
			waitForSync(1);
			String screenXpath = xls_Read.getCellValue("Generic_Elements", "lst_searchScreen;xpath").replace("ScreenID",
					ScreenID);

			driver.findElement(By.xpath(screenXpath)).click();

			
			
		}
		catch (Exception e) {
			System.out.println("Could not enter " + ScreenID + " and invoke " + ScreenName + " Screen");
			test.log(LogStatus.FAIL, "Could not enter " + ScreenID + " and invoke " + ScreenName + " Screen");
			Assert.assertFalse(true, "Could not enter " + ScreenID + " and invoke " + ScreenName + " Screen");

		}
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 */
	public void clickTriggerScaling() throws InterruptedException
	{
		
		driver.findElement(By.name("btnTriggerScaling")).click();
		waitForSync(8);
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 */
	public void clearSplLane() throws InterruptedException
	{
		clearText(sheetName, "inbx_transportLane;name", "transportLane",screenName);
				
		waitForSync(1);
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 */
	public void clickSave() throws InterruptedException
	{
		
		driver.findElement(By.name("btnSave")).click();
		waitForSync(8);
	}
	/**
	 * @author A-7271
	 * @param transportLane
	 * @throws InterruptedException
	 */
	public void enterSplLane(String transportLane) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_transportLane;name", data(transportLane), "transportLane", screenName);
		waitForSync(1);
	}
	/**
	 * @author A-7271
	 * @param weighingMode
	 * @throws InterruptedException
	 */
	public void enterWeighingMode(String weighingMode) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_WeighingMode;name", data(weighingMode), "weighingMode", screenName);
		waitForSync(1);
	}
	/**
	 * 
	 * @param expValue
	 * @param attribute
	 */
	public void verifySplLane(String expValue,String attribute)
	{
		String actualResult=getAttributeWebElement(sheetName, "inbx_transportLane;name","transportLane", attribute, screenName);
				
		System.out.println(actualResult);
		System.out.println(expValue);
		verifyScreenText(sheetName, data(expValue),actualResult,  "verification of spl lane", screenName); 
	}
	/**
	 * A-7271
	 * listUldDetails
	 * @param uldNumber
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void listUldDetails(String uldNumber) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_uldNumber;id", data(uldNumber), "uldNumber", screenName);
		clickWebElement(sheetName, "btn_list;id", "List button", screenName);
		waitForSync(10);
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYes() throws InterruptedException, IOException
	{
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes button", screenName);
		waitForSync(10);
	}
	/**
	 * A-7271
	 * verifyTransportLaneAndWeighingMode
	 * @param transportLane
	 * @param weighingMode
	 */
	public void verifyTransportLaneAndWeighingMode(String transportLane,String weighingMode)
	{
		//Transport Lane
		String transportLan=getAttributeWebElement(sheetName, "inbx_transportLane;name","transport Lane", "value", screenName);

		System.out.println("ss"+transportLan);
		System.out.println("ssss"+data(transportLane));
		verifyScreenTextWithExactMatch(sheetName, data(transportLane), transportLan,  "Verification of Transport Lane in captureULD weighing details screen", screenName); 

		//Weighing mode
		String weighingMod=getAttributeWebElement(sheetName, "inbx_WeighingMode;name","Weighing Mode", "value", screenName);

		verifyScreenTextWithExactMatch(sheetName, data(weighingMode), weighingMod,  "Verification of Weighing mode in captureULD weighing details screen", screenName); 
	}

public void verifyULDDetails(String element, String expected)
		throws InterruptedException {
	System.out.println("hjsyg");
	WebElement ele = driver.findElement(By
			.xpath("//label[contains(text(),'" + element
					+ "')]//following::strong[1]"));
	String actual = ele.getText();
	verifyScreenTextWithExactMatch(sheetName, data(expected), actual,
			"Verification of " + element
					+ " in captureULD weighing details screen", screenName);

}

/**
 * @throws InterruptedException
 * 
 */
public void verifyBuiltupBy(String builtUpBy) throws InterruptedException {
	System.out.println("hgh");
	System.out.println("ghg");
	String actualBuiltUp = getAttributeWebElement(sheetName,
			"inbx_BuiltUp;name", "BuiltUp", "value", screenName);
	verifyScreenTextWithExactMatch(
			sheetName,
			data(builtUpBy),
			actualBuiltUp,
			"Verification of BuiltUpBy in captureULD weighing details screen",
			screenName);

}

/**
 * @throws InterruptedException
 * 
 */
public void verifyWeighingMode(String weighingMode) {
	String weighingMod = getAttributeWebElement(sheetName,
			"inbx_WeighingMode;name", "Weighing Mode", "value", screenName);
	verifyScreenTextWithExactMatch(
			sheetName,
			data(weighingMode),
			weighingMod,
			"Verification of Weighing mode in captureULD weighing details screen",
			screenName);

}

/**
 * @throws InterruptedException
 * 
 */
public String getULDWeightAndDolly() throws InterruptedException {
	String w = getAttributeWebElement(sheetName, "inbx_combinedWt;name",
			"Combined weight", "value", screenName);
	return w;
}

/**
 * @throws Exception
 * 
 */
public String verifyActualULDWeight(String weight, String DollyWeight)
		throws Exception {
	int temp = Integer.parseInt(weight)
			- Integer.parseInt(data(DollyWeight));
	String actualULDweight = String.valueOf(temp);
	String actualULDInScreen = getElementText(sheetName,
			"inbx_ActualULDWeight;xpath", "Actual ULD Weight", screenName);
	verifyScreenTextWithExactMatch(sheetName, actualULDweight,
			actualULDInScreen,
			"Verification of AHA in captureULD weighing details screen",
			screenName);
	return actualULDweight;

}

/**
 * @throws InterruptedException
 * 
 */
public void verifyAutoPrintMsg(String ExpectedMsg)
		throws InterruptedException {
	String actualMsg = getElementText(sheetName, "msg_autoPrint;xpath",
			"Auto Print Msg", screenName);
	verifyScreenTextWithExactMatch(
			sheetName,
			data(ExpectedMsg),
			actualMsg,
			"Verification of auto print msg in captureULD weighing details screen",
			screenName);

}

public void verifyTriggerScalingBtn(String eleName, String testSteps) {
	String at =  driver.findElement(By.name("btnTriggerScaling")).getAttribute("fromscriptdisabling");
	if (at.equals("true")) {
		customFunction.onPassUpdate(screenName, "Trigger Scaling Btn"
				+ " is enabled", "Trigger Scaling Btn" + " is disabled",
				"Trigger Scaling Btn" + " is disabled",
				"1.Login to IE 2.List ULD 3.verify " + "Trigger Scaling Btn"
						+ " ");
	} else {
		customFunction.onFailUpdate(screenName,"Trigger Scaling Btn"
				+ " is disabled", "Trigger Scaling Btn" + " is enabled",
				"Trigger Scaling Btn" + " is enabled",
				"1.Login to IE 2.List ULD 3.verify " + "Trigger Scaling Btn"
						+ " ");
	}
	waitForSync(5);
}



public void enterCombinedWeight(String combinedWeight)
		throws InterruptedException {
	enterValueInTextbox(sheetName, "inbx_combinedWt;name",
			data(combinedWeight), "Combined weight", screenName);
	waitForSync(5);

}

public void verifymainDeckisUnchecked(String eleName, String testSteps) {
	WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue(
			sheetName, "chk_mainDeck;xpath")));
	if (ele.isSelected() == false) {
		customFunction.onPassUpdate(screenName, eleName
				+ " is not selected", eleName + " is enabled", eleName
				+ " is not selected", testSteps);
	} else {
		customFunction.onFailUpdate(screenName, eleName + " is selected",
				eleName + " is enabled", eleName + " is selected",
				testSteps);
	}
}

public void checkTurnOffTransportReq() throws InterruptedException,
		AWTException, IOException {
	clickWebElement(sheetName, "chk_transportReq;xpath",
			"Screen Search Field", screenName);
	waitForSync(3);
	driver.findElement(
			By.xpath(xls_Read.getCellValue(sheetName,
					"chk_transportReq;xpath"))).sendKeys(Keys.TAB);
	waitForSync(3);
}

public void verifyTransportLaneisDisabled(String eleName, String testSteps) {
	WebElement ele = driver.findElement(By.name(xls_Read.getCellValue(
			sheetName, "inbx_transportLane;name")));
	if (ele.isEnabled() == false) {
		customFunction.onPassUpdate(screenName, eleName + " is disabled",
				eleName + " is enabled", eleName + " is disabled",
				testSteps);
	} else {
		customFunction
				.onFailUpdate(screenName, eleName + " is enabled", eleName
						+ " is enabled", eleName + " is enabled", testSteps);
	}

}

public void verifyFlight(String Expectedflight) throws InterruptedException {
	String ActualflightNo = getAttributeWebElement(sheetName,
					"inbx_flight;name", "Flight Number", "value",
					screenName);
	verifyScreenTextWithExactMatch(
			sheetName,
			Expectedflight,
			ActualflightNo,
			"Verification of flight No in captureULD weighing details screen",
			screenName);
}

public void enterDollyTareWeight(String dollyWeight)
		throws InterruptedException {
	enterValueInTextbox(sheetName, "input_dollyWeightr;name",
			data(dollyWeight), "Dolly tare weight", screenName);

}

public void acceptWarningMsg() throws InterruptedException, AWTException {
 keyPress("ENTER");
}

public void verifyWeighingModeDropdownValues(String[] expectedValues,
		List<String> actualValues) {
	List<String> expectedList = Arrays.asList(expectedValues);
	if (expectedList.size() == actualValues.size()) {
		for (int i = 0; i < expectedList.size(); i++) {
			if (expectedList.get(i).contains(actualValues.get(i))) {
				customFunction
						.onPassUpdate(
								screenName,
								expectedList.get(i) + " ",
								actualValues.get(i) + " ",
								"Checking Planned areas and handling areas popup are same",
								"1.Login to micap \n 2.Verify Planned areas \n ");
			} else {
				customFunction
						.onFailUpdate(
								screenName,
								expectedList.get(i) + " ",
								actualValues.get(i) + " ",
								"Checking Planned areas and handling areas popup are not same",
								"1.Login to micap \n 2.Verify Planned areas \n ");
			}

		}

	}

}

public List<String> getWeighingModeValues() {
	List<String> a = new ArrayList<>();
	WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue(
			sheetName, "lst_WeighingMode")));
	ele.click();
	waitForSync(4);
	WebElement e = driver.findElement(By
			.xpath("//select[@id='div_weighingModeId_']"));
	Select select = new Select(e);
	List<WebElement> dropdown = select.getOptions();
	for (WebElement f : dropdown) {
		String actValue = f.getAttribute("value");
		System.out.println(actValue);
		a.add(actValue);

	}
	WebElement ele1 = driver.findElement(By.xpath(xls_Read.getCellValue(
			sheetName, "chk_mainDeck;xpath")));
	ele1.click();
	return a;

}

public void verifyWarningMsg(String expectedMsg) {
	String actMsg = handleAlertAndReturnText();
	System.out.println(actMsg);
	if (actMsg.contains(data(expectedMsg))) {
		customFunction
				.onPassUpdate(
						screenName,
						expectedMsg + " ",
						actMsg + " ",
						"Verifying warning msg when weighing mode is changed",
						"1.Login to micap \n 2.Change weighing mode 3.Verify warning msg \n ");
	} else {
		customFunction
				.onFailUpdate(
						screenName,
						expectedMsg + " ",
						actMsg + " ",
						"Verifying warning msg when weighing mode is changed",
						"1.Login to micap \n 2.Change weighing mode 3.Verify warning msg \n");
	}
}

public void clickNo() throws InterruptedException, IOException {
	switchToFrame("default");
	clickWebElement("Generic_Elements", "btn_no;xpath", "No button",
			screenName);
	waitForSync(5);
}

public void verifyTransportLane(String transportLane) {
	WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue(
			sheetName, "inbx_transportLane;xpath")));
	String actual = ele.getText();
	verifyScreenTextWithExactMatch(
			sheetName,
			data(transportLane),
			actual,
			"Verification of Transport Lane in captureULD weighing details screen",
			screenName);

}

public void verifyDollyWeight(String dollyWeight)
		throws InterruptedException {
	getElementText(sheetName, "input_dollyWeightr;name",
			"Dolly tare weight", screenName);

}

public void verifyULDWeightThruMsg(String actualULDweight)
		throws InterruptedException {
	String actualULDInScreen = getElementText(sheetName,
			"inbx_ActualULDWeight;xpath", "Actual ULD Weight", screenName);
	verifyScreenTextWithExactMatch(
			sheetName,
			data(actualULDweight),
			actualULDInScreen,
			"Verification of ULD weight in captureULD weighing details screen",
			screenName);

}

public void verifyElementIsDisabled(String[] elements) {
	for (int i = 0; i < elements.length; i++) {
		String at = getAttributeWebElement(sheetName, "inbx_"+elements[i]+";name", elements[i], "fromscriptdisabling", "OPR357");
		if (at.equals("true")) {
			customFunction.onPassUpdate(screenName, elements[i]
					+ " is enabled", elements[i] + " is disabled",
					elements[i] + " is disabled",
					"1.Login to IE 2.List ULD 3.verify " + elements[i]
							+ " ");
		} else {
			customFunction.onFailUpdate(screenName, elements[i]
					+ " is disabled", elements[i] + " is enabled",
					elements[i] + " is enabled",
					"1.Login to IE 2.List ULD 3.verify " + elements[i]
							+ " ");
		}
		waitForSync(5);
	}

}

public void verifyWeighingModeidDisabled(String eleName, String testSteps) {
	WebElement ele = driver.findElement(By.name(xls_Read.getCellValue(
			sheetName, "inbx_WeighingMode;name")));
	if (ele.isEnabled() == false) {
		customFunction.onPassUpdate(screenName, eleName + " is disabled",
				eleName + " is enabled", eleName + " is disabled",
				testSteps);
	} else {
		customFunction
				.onFailUpdate(screenName, eleName + " is enabled", eleName
						+ " is enabled", eleName + " is enabled", testSteps);
	}

}
}
