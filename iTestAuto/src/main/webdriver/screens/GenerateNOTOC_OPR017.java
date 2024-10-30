package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class GenerateNOTOC_OPR017 extends CustomFunctions {

	public GenerateNOTOC_OPR017(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
	}
	
	public String sheetName = "GenerateNOTOC_OPR017";
	public String screenName = "Generate NOTOC";
/**
 * Description...	Expand All ULDs
 * @param uldno
 * @param uldno1
 * @throws InterruptedException
 */
	public void expandAllULDs(String uldno, String uldno1) throws InterruptedException{
		String xpathExpandBtn1 = xls_Read.getCellValue(
				"DeadloadStatement_OPR063", "btn_expand;xpath").replace(
				"BULK", uldno);
		WebElement eleExpandBtn1 = findDynamicXpathElement(
				xpathExpandBtn1, "Expand Button", "Deadload Statement");
		clickWebElement(eleExpandBtn1, "Expand Button",
				"Deadload Statement");	
		
		//ULD 2
		
		String xpathExpandBtn2 = xls_Read.getCellValue(
				"DeadloadStatement_OPR063", "btn_expand;xpath").replace(
				"BULK", uldno1);
		WebElement eleExpandBtn2 = findDynamicXpathElement(
				xpathExpandBtn2, "Expand Button", "Deadload Statement");
		clickWebElement(eleExpandBtn2, "Expand Button",
				"Deadload Statement");	
		
		//ULD 3
		String xpathExpandBtn3 = xls_Read.getCellValue(
				"DeadloadStatement_OPR063", "btn_expand;xpath");
		WebElement eleExpandBtn3 = findDynamicXpathElement(
				xpathExpandBtn3, "Expand Button", "Deadload Statement");
		clickWebElement(eleExpandBtn3, "Expand Button",
				"Deadload Statement");	
	}
/**
 * Description... Check No Dg	
 * @throws InterruptedException
 * @throws IOException 
 */
	public void checkNoDg() throws InterruptedException, IOException {

		clickWebElement(sheetName, "chk_noDgCheckBox;xpath", "No DG Check box", screenName);	
	}
/**
 * Description... Check No Dg 2
 * @throws InterruptedException
 * @throws IOException 
 */
	public void checkNoDg2() throws InterruptedException, IOException {

		clickWebElement(sheetName, "chk_noDgCheckBox2;xpath", "No DG Check box2", screenName);	
	}
	/**
	 * A-10690 Checks ULD checkbox for SL awbs in OPR017 screen
	 * 
	 * @param uldno
	 * @param count-no of ULDs
	 */
	public void selectAWBsByJSForSL(String[] awbNo,int count) {
		waitForSync(2);
		
		for(int i=0;i<count;i++)
		{
		String locator=xls_Read.getCellValue(sheetName, "btn_selectawbSL;xpath");
		locator=locator.replace("*", awbNo[i]);
		try{
			WebElement element=driver.findElement(By.xpath(locator));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			
			waitForSync(3);
			writeExtent("Pass", "ULD no selected " + (awbNo[i]) +"on "+screenName );
		}
		catch (Exception e) {
			writeExtent("Fail", "failed to select uld no"+ (awbNo[i]+"on "+screenName ));
		}
		}
	}
	public void performULDAsscWithUNID(String uldNum,String unid)
	{
		try
		{
		String xpath= xls_Read.getCellValue(sheetName,  "table_dgrTable;xpath");
	          
		List <WebElement> dgrTableRow=driver.findElements(By.xpath(xpath));
		String tableText="";
		int rowCount=0;
		
		/******************GETTING THE ROW COUNT OF ULD**************/
		for(WebElement val : dgrTableRow)
		{
			tableText=val.getText();
			System.out.println(tableText);
			rowCount=rowCount+1;
			
			if(tableText.replace(" ", "").contains(data(uldNum)))
			{
				break;
			}
			
			
		}
		
		System.out.println(rowCount);
		/************VERIFYING IF UNID ASSOCIATION EXISTS************/
		
		String dynaXpath="("+xpath+")["+(rowCount+1)+"]//td[9]//img";
		driver.findElement(By.xpath(dynaXpath)).click();
		waitForSync(2);
		
		}
		
		catch(Exception e)
		{
			
		}
		
	}

	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * Desc : select UNID
	 */
	public void selectUNID() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "chkBox_unidDetails;name", "Check box UNID", screenName);
		clickWebElementByWebDriver(sheetName, "btn_selectUNID;id", "select UNID", screenName);
		waitForSync(3);
	}

	/**
	 * @author A-10690
	 * @throws Exception
	 * @params: screenid,awbs
	 * Desc : Generate NOTOC and verify whether all the awbs are getting displayed in the report
	 */
	public void generateNOTOCandVerifyReportWithCont(String screenId,String[] VP) throws Exception {
		
		try
		{
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_generateNOTOC;name", "generate NOTOC", screenName);	
		waitForSync(8);
		switchToFrame("default");
	    clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
	    waitForSync(5);
		switchToFrame("contentFrame", screenId);
		
		//Verification if report got generated
		
		switchToWindow("multipleWindows");

		int windowSize=getWindowSize();

		if(windowSize==2)
		{
			switchToFrame("frameName","ReportContainerFrame");
			
			//Verifying the heading
			
			verifyElementDisplayed(sheetName, "htmlDiv_reportHeading;xpath","NOTOC report generation", screenName, "NOTOC report heading");
			
			
			//Verification of data
			for(int i=0;i<VP.length;i++)
			{
			
			String locatorValue=xls_Read.getCellValue(sheetName, "htmlDiv_reportData;xpath");
			locatorValue=locatorValue.replace("AWB", VP[i]);
			if(driver.findElement(By.xpath(locatorValue)).isDisplayed())
			{
				onPassUpdate(screenName, "NOTOC report is generated and the AWB "+VP[i]+" is stamped", "NOTOC report is not getting generated and the AWB "+VP[i]+" is stamped", "Verify whether the report is generated",
						"Verify whether the notoc report is generated");
			}
			else
			{
				onFailUpdate(screenName, "NOTOC report is generated but the AWB "+VP[i]+" is not stamped", "NOTOC report is  getting generated and the AWB "+VP[i]+" is not stamped", "Verify whether the report is generated",
						"Verify whether the notoc report is generated",false);
			}
			
		}
		}
		else
		{
			onFailUpdate(screenName, "NOTOC report should be generated", "NOTOC report is not getting generated", "Verify whether the report is generated",
					"Verify whether the notoc report is generated",false);
		}
		
		closeBrowser();
		waitForSync(2);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame",screenId);
		}
		
		catch(Exception e)
		{
			onFailUpdate(screenName, "NOTOC report should be generated", "NOTOC report is not getting generated", "Verify whether the report is generated",
					"Verify whether the notoc report is generated",false);
		}
		
		
	}
	/**
	 * @author A-9844
	 * @Desc: List flight 
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... List Flight
	 */
	public void listFlight(String carrierCode, String flightNumber, String flightDate) throws InterruptedException, AWTException {

		try {
			
			enterValueInTextbox(sheetName, "inbx_carrierCode;xpath", data(carrierCode), "Carrier Code", screenName);
			enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(flightNumber), "Flight Number", screenName);
			enterValueInTextbox(sheetName, "inbx_flightDate;xpath", data(flightDate), "Flight Date", screenName);
			clickWebElementByWebDriver(sheetName, "btn_List;id", "List Button", screenName);
			waitForSync(5);
		} catch (Exception e) {
			System.out.println("Could not perform list flight operations");
			test.log(LogStatus.FAIL, "Could not perform list flight operations in "+screenName);

		}
	}

	/**
	 * A-10690 Checks ULD checkbox
	 * 
	 * @param uldno
	 * @param count-no of ULDs
	 */
	public void selectsULDsByJS(String[] uldNo,int count) {
		waitForSync(2);
		
		for(int i=0;i<count;i++)
		{
		String locator=xls_Read.getCellValue(sheetName, "btn_selectuld1;xpath");
		locator=locator.replace("*", uldNo[i]);
		try{
			WebElement element=driver.findElement(By.xpath(locator));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			
			waitForSync(3);
			writeExtent("Pass", "ULD no selected " + (uldNo[i]) );
		}
		catch (Exception e) {
			writeExtent("Fail", "failed to select uld no"+ (uldNo[i]));
		}
		}
	}

	/**A-9478
	* Clicks close button
	* @param pmKey
	 * @throws IOException 
	*/
	public void clickClose() throws InterruptedException, IOException 
	{
		clickWebElement(sheetName, "btn_closeOPR017;xpath", "Close button", screenName);	
		waitForSync(5);
		
	}
	/**
	 * @author A-8783
	 * @param pcs
	 * @throws InterruptedException
	 */
	public void enterPcsUNIDDetails(String pcs) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_pieces1;xpath", data(pcs), "UNID pcs",
				screenName); 
	}

	/**
	 * @author A-9844
	 * @param AWBNo
	 * @param awbCount
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void performAWBAsscWithUNID(String[] AWBNo,int awbCount )
	{
		try
		{

			for(int i=0;i<awbCount;i++){
				String locator= xls_Read.getCellValue(sheetName,  "btn_DGCheckIcon;xpath");
				locator=locator.replace("*",AWBNo[i]);
				moveScrollBar(driver.findElement(By.xpath(locator)));
				driver.findElement(By.xpath(locator)).click();
				System.out.println(locator);
				selectUNID();
				writeExtent("Pass", "Selected UNID details for AWB "+AWBNo[i]+" on "+screenName + " Page");

			}

		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not select UNID details for AWB on "+screenName + " Page");
		}

	}

	/**
	 * 
	 * @param ScreenId
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickGenerateNOTOC(String ScreenId) throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_generateNOTOC;name", "generate NOTOC", screenName);		
		switchToFrame("default");
		waitForSync(8);
	    clickWebElement("Generic_Elements", "btn_no;xpath", "no Button", screenName);
		switchToFrame("contentFrame", ScreenId);	
		
	}

/**
 * Description... Verify No DG Flag Unchecked For First ULD
 */
	public void verifyNoDGFlagUncheckedForFirstULD() {

		waitForSync(3);
		try{
			boolean checked = driver.findElement(By.xpath("(//input[@name='nonDgShipment'])[1]")).isSelected();
			if(checked==false) {
				onPassUpdate("GenerateNOTOC_OPR017", "No DG Check box not checked for first ULD", "No DG Check box not checked for first ULD", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box checked\n");
			}
			else {
				onFailUpdate("GenerateNOTOC_OPR017", "No DG Check box not checked for first ULD", "No DG Check box checked for first ULD", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box \n");
			}	

		}
		catch(Exception e) {
			e.printStackTrace();
		}	
	}
/**
 * Description... Verify No DG Flag Unchecked For Second ULD
 */
	public void verifyNoDGFlagUncheckedForSecondULD() {
		try{	
			boolean checked2 = driver.findElement(By.xpath("(//input[@name='nonDgShipment'])[2]")).isSelected();
			if(checked2==false) {
				onPassUpdate("GenerateNOTOC_OPR017", "No DG Check box not checked for Second ULD", "No DG Check box not checked for Second ULD", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box \n");
			}
			else {
				onFailUpdate("GenerateNOTOC_OPR017", "No DG Check box checked for second ULD", "No DG Check box  checked for second ULD", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box checked \n");
			}												
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
/**
 * Description... Verify UNID1 Exist Or Not after Clicking On No DG CheckBox
 */
	public void verifyUNID1ExistOrNotfterClickingOnNoDGCheckBox() {

		try{
			boolean expected = driver.findElement(By.xpath("(//div[@class='ic-row'])[5]//tr[3]//td[9]")).isDisplayed();
			if(expected==false) {
				onPassUpdate("GenerateNOTOC_OPR017", "UNID 1 Not Exist After clicking on NoDgCheck box", "UNID 1 Not Exist After clicking on NoDgCheck box", 
						"UNID 1 ", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify UNID exist or not \n");
			}
			else {
				onFailUpdate("GenerateNOTOC_OPR017", "UNID 1 Not Exist After clicking on NoDgCheck box", "UNID 1 Exist After clicking on NoDgCheck box", 
						"UNID 1", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify UNID exist or not \n");
			}	
		}
		catch (Exception e) {

		}
	}
/**
 * Description... Verify UNID2 Exist Or Not after Clicking On No DG CheckBox
 */
	public void verifyUNID2ExistOrNotfterClickingOnNoDGCheckBox() {

		try{
			boolean expected = driver.findElement(By.xpath("(//div[@class='ic-row'])[5]//tr[4]//td[9]")).isDisplayed();
			if(expected==false) {
				onPassUpdate("GenerateNOTOC_OPR017", "UNID 2 Not Exist After clicking on NoDgCheck box", "UNID 2 Not Exist After clicking on NoDgCheck box", 
						"UNID 2", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify UNID exist or not \n");
			}
			else {
				onFailUpdate("GenerateNOTOC_OPR017", "UNID 2 Not Exist After clicking on NoDgCheck box", "UNID 2 Exist After clicking on NoDgCheck box", 
						"UNID 2", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify UNID exist or not \n");
			}	


		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
/**
 * Description... Verify No DG Flag Checked For First ULD
 */
	public void verifyNoDGFlagCheckedForFirstULD() {

		waitForSync(2);
		try{
			boolean checked = driver.findElement(By.xpath("(//input[@name='nonDgShipment'])[1]")).isSelected();
			if(checked==true) {
				onPassUpdate("GenerateNOTOC_OPR017", "No DG Check box not checked for first ULD", "No DG Check box not checked for first ULD", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box checked\n");
			}
			else {
				onFailUpdate("GenerateNOTOC_OPR017", "No DG Check box not checked for first ULD", "No DG Check box checked for first ULD", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box \n");
			}							
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
/**
 * Description... Verify No DG Flag Checked For Second ULD
 */
	public void verifyNoDGFlagCheckedForSecondULD() {

		waitForSync(2);
		try{
			boolean checked2 = driver.findElement(By.xpath("(//input[@name='nonDgShipment'])[2]")).isSelected();
			if(checked2==true) {
				onPassUpdate("GenerateNOTOC_OPR017", "No DG Check box checked for second UNID", "No DG Check box checked for second UNID", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box \n");
			}
			else {
				onFailUpdate("GenerateNOTOC_OPR017", "No DG Check box checked for second UNID", "No DG Check box not checked for second UNID", 
						"No DG Check box", "//1. Login to iCargo \n , 2.Invoke OPR017 Screen \n , 3.Verify NO DG Check Box checked \n");
			}	
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
public void verifyOperatingReference(String operatingRefCarrier, String operatingRef) {
	String carrierCode=getAttributeWebElement(sheetName, "inbx_opRefCarrierCode;name",
			 "Operating Reference Carrier Code","value",
			screenName);
    String flight=getAttributeWebElement(sheetName, "inbx_opRefFlightNumber;name",
			 "Operating Reference Flight Number","value",
			screenName);
if(carrierCode.equals(data(operatingRefCarrier)) && flight.equals(data(operatingRef))){
	onPassUpdate(screenName, carrierCode+flight, operatingRefCarrier+operatingRef,
			"operating reference verification",
			"1.check operating reference");
}
else{
	onFailUpdate(screenName, carrierCode+flight, operatingRefCarrier+operatingRef,
			"operating reference verification",
			"1.check operating reference");
}
}


/**
 * A-8705 Checks ULD checkbox
 * 
 * @param pmKey
 */
public void selectsULD(String pmKey) {
	selectTableRec(data(pmKey), sheetName, "chk_selectULD;xpath",
            "chk_checkBox;xpath", 3);
    waitForSync(2);



}



/**
 * A-8705 Adds UNID for single AWB
 * 
 * @param unid
 * @throws Exception
 */
public void addUNID(String[] unid) throws Exception {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    clickWebElement(sheetName, "img_addUnidDetails_1;xpath",
            "add unid button", screenName);
    waitForSync(3);
    for (int i = 0; i < unid.length; i++) {
        int k=i+1;
        String g=String.valueOf(k);
        WebElement ele1 = driver.findElement(By.xpath("//table[@id='UNIDDetailsforAWBTable']//tbody//tr["+g+"]//td[1]//input"));
        js.executeScript("arguments[0].scrollIntoView(true);", ele1);
        ele1.click();
        waitForSync(2);
    }
    clickWebElement(sheetName, "btn_OK;name", "ok button", screenName);
}



/**
 * A-8705 Adds UNID for multiple AWB
 * 
 * @param unid1
 * @throws InterruptedException
 * @throws IOException 
 */
public void addUNIDForMultipleAWB(String[] unid1, String j)
        throws InterruptedException, IOException {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    String xpath2 = xls_Read.getCellValue(sheetName,
            "img_addUnidDetails;xpath").replace("code", j);
    WebElement ele2 = findDynamicXpathElement(xpath2,
            "Add Unid Details image", "Generate Notac screen");
    js.executeScript("arguments[0].scrollIntoView(true);", ele2);
    ele2.click();
    waitForSync(3);
    for (int i = 0; i < unid1.length; i++) {
        int k=i+1;
        String g=String.valueOf(k);
        WebElement ele1 = driver.findElement(By.xpath("//table[@id='UNIDDetailsforAWBTable']//tbody//tr["+g+"]//td[1]//input"));
        js.executeScript("arguments[0].scrollIntoView(true);", ele1);
        ele1.click();
        waitForSync(2);
    }
    clickWebElement(sheetName, "btn_OK;name", "ok button", screenName);
}



/**
 * A-8705 Clicks Expand Button
 * 
 * @throws InterruptedException
 * @throws IOException 
 */
public void clickULDExpand() throws InterruptedException, IOException {
    clickWebElement(sheetName, "btn_expand;xpath", "expand button",
            screenName);
    waitForSync(3);



}

public void verifyULDAsscWithUNID(String uldNum,String unid)
{
	try
	{
	String xpath= xls_Read.getCellValue(sheetName,  "table_dgrTable;xpath");
          
	List <WebElement> dgrTableRow=driver.findElements(By.xpath(xpath));
	String tableText="";
	int rowCount=0;
	
	/******************GETTING THE ROW COUNT OF ULD**************/
	for(WebElement val : dgrTableRow)
	{
		tableText=val.getText();
		System.out.println(tableText);
		rowCount=rowCount+1;
		
		if(tableText.replace(" ", "").contains(data(uldNum)))
		{
			break;
		}
		
		
	}
	
	System.out.println(rowCount);
	/************VERIFYING IF UNID ASSOCIATION EXISTS************/
	
	String dynaXpath="("+xpath+")["+(rowCount+2)+"]//td[9]";
	String getUNID=driver.findElement(By.xpath(dynaXpath)).getText();
	System.out.println(getUNID);
	
	if(getUNID.equals("ID "+data(unid)))
	{
		onPassUpdate(screenName, "UNID and ULD association should exist", "UNID and ULD association exists", "Verify whether UNID and ULD association exists",
				"Verify whether UNID and ULD association exists");
	}
	
	else
	{
		onFailUpdate(screenName, "UNID and ULD association should exist", "UNID and ULD association does not exist", "Verify whether UNID and ULD association exists",
				"Verify whether UNID and ULD association exists");
	}
	}
	
	catch(Exception e)
	{
		onFailUpdate(screenName, "UNID and ULD association should exist", "UNID and ULD association does not exist", "Verify whether UNID and ULD association exists",
				"Verify whether UNID and ULD association exists");
	}
	
	
}
/**A-8705
* Clicks expand button of particular ULD
* @param pmKey
*/
public void clickULDExpand(String pmKey) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    WebElement ele = driver.findElement(By.xpath("//td[contains(text(),'"+pmKey+"')]/..//td[2]//a"));
    js.executeScript("arguments[0].scrollIntoView(true);", ele);
    ele.click();
    waitForSync(3);
    
}
public void clickGenerateNOTOC() throws InterruptedException, IOException {
	clickWebElement(sheetName, "btn_generateNOTOC;name", "generate NOTOC", screenName);		
	switchToFrame("default");
	waitForSync(8);
    clickWebElement("Generic_Elements", "btn_no;xpath", "no Button", screenName);
	switchToFrame("contentFrame", "OPR017");	
	
}	
/**
 * 
 * @param screenId
 * @throws Exception
 */
public void generateNOTOCandVerifyReportWithCont(String screenId) throws Exception {
	
	switchToWindow("storeParent");
	clickWebElement(sheetName, "btn_generateNOTOC;name", "generate NOTOC", screenName);	
	waitForSync(8);
	switchToFrame("default");
    clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
    waitForSync(3);
	switchToFrame("contentFrame", screenId);
	
	//Verification if report got generated
	
	switchToWindow("multipleWindows");

	int windowSize=getWindowSize();

	if(windowSize==2)
	{
		onPassUpdate(screenName, "window size should be 2 while generating NOTOC", "window size is "+windowSize+" while generating NOTOC", "Verify whether the notoc report is generated",
				"Verify whether the notoc report is generated");
	}
	else
	{
		onFailUpdate(screenName, "window size should be 2 while generating NOTOC", "window size is "+windowSize+" while generating NOTOC", "Verify whether the report is generated",
				"Verify whether the notoc report is generated");
	}
	closeBrowser();
	waitForSync(2);
	switchToWindow("getParent");
	switchToFrame("default");
	switchToFrame("contentFrame",screenId);
	
	
}
public void generateNOTOCandVerifyReport(String screenId) throws Exception {
	
	switchToWindow("storeParent");
	clickWebElement(sheetName, "btn_generateNOTOC;name", "generate NOTOC", screenName);	
	waitForSync(8);
	switchToFrame("default");
    clickWebElement("Generic_Elements", "btn_yes;xpath", "yes Button", screenName);
    waitForSync(3);
	switchToFrame("contentFrame", screenId);
	
	//Verification if report got generated
	
	switchToWindow("multipleWindows");

	int windowSize=getWindowSize();

	if(windowSize==2)
	{
		onPassUpdate(screenName, "window size should be 2 while generating NOTOC", "window size is "+windowSize+" while generating NOTOC", "Verify whether the notoc report is generated",
				"Verify whether the notoc report is generated");
	}
	else
	{
		onFailUpdate(screenName, "window size should be 2 while generating NOTOC", "window size is "+windowSize+" while generating NOTOC", "Verify whether the report is generated",
				"Verify whether the notoc report is generated");
	}
	closeBrowser();
	waitForSync(2);
	switchToWindow("getParent");
	switchToFrame("default");
	switchToFrame("contentFrame",screenId);
	
	
}	
	
}


