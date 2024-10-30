package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class FlightLoadPlan_OPR015 extends CustomFunctions {

	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "FlightLoadPlan_OPR015";
	String ScreenName = "Flight Load Plan";
	String screenId = "OPR015";

	public FlightLoadPlan_OPR015(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
/**
 * Description... Click ULD Tab
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickULDTab() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_uld;xpath", "ULD Tab", ScreenName);
	}
/**
 * Description... Check ULD
 * @param uldNo
 * @param locator
 * @param locatorEle
 * @throws InterruptedException
 */
	public void checkULD(String uldNo, String locator, String locatorEle) throws InterruptedException {
		customFuction.selectTableRecord(uldNo, sheetName, locator, locatorEle, 5);

	}
/**
 * Description... Select ULD Type
 * @param option
 */
	public void selectULDType(String option) {
		selectValueInDropdown(sheetName, "lst_uld;name", option, "ULD Type", "Value");
	}
/**
 * Description... Click Assign AWB
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickAssignAWB() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_assignAwb;name", "Assign AWB Button", ScreenName);
		waitForSync(5);
	}
	
	/**
	 * Description... Click Yes Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickYesButton() throws InterruptedException, IOException
	{
		switchToFrame("default");
		
		try
		{
		
		if(driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).isDisplayed())
		{
		clickWebElement("Generic_Elements", "btn_Yes;xpath", "Yes Button", ScreenName);
		waitForSync(2);
		}
		
		
		}
		
		catch(Exception e)
		{
			
		}
		finally
		{
		switchToFrame("contentFrame", "OPR015");
		}
	}
	
	
/**
 * Description... Click More Panel
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickMorePanel() throws InterruptedException, IOException {
		clickWebElement(sheetName, "lnk_panelLink;xpath", "More Panel Link", ScreenName);

	}
/**
 * Description... Click Auto Assign
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickAutoAssign() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_autoAssign;name", "Auto Assign Button", ScreenName);

	}
/**
 * Description... Click Save Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickSaveButton() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_save;name", "Save Button", ScreenName);
		waitForSync(10);
	}
/**
 * Description... Verify BuildUp Summary Plan
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verfBuildUpSummaryPlan() throws InterruptedException, IOException {
		System.out.println("i am here");

		String totalCap = getElementText("FlightLoadPlan_OPR015", "tab_totalCap;xpath", "Total Capacity",
				"Flight Load Plan");
		String preBuiltUnits = getElementText("FlightLoadPlan_OPR015", "tab_preBuiltUnits;xpath", "Pre Built Units",
				"Flight Load Plan");
		String remCapForLocal = getElementText("FlightLoadPlan_OPR015", "tab_remCapForLocal;xpath",
				"Capacity Remaining for Local", "Flight Load Plan");
		String plannedCap = getElementText("FlightLoadPlan_OPR015", "tab_plannedCap;xpath", "Planned Capacity",
				"Flight Load Plan");
		String unplannedCap = getElementText("FlightLoadPlan_OPR015", "tab_unplannedCap;xpath", "Unplanned Capacity",
				"Flight Load Plan");
		String remCap = getElementText("FlightLoadPlan_OPR015", "tab_remCap;xpath", "Remaining Capacity",
				"Flight Load Plan");

		String[] expVerfVals = { totalCap, preBuiltUnits, remCapForLocal, plannedCap, unplannedCap, remCap };
		String[] actVerfVals = { "MDP: 26 Q7: 0 LDC: 2 LDP: 10", "MDP: 1 Q7: 0 LDC: 1 LDP: 1",
				"MDP: 25 Q7: 0 LDC: 1 LDP: 9", "Wt/Vol: 0K/0B", "Wt/Vol: 0K/0B", "MDP: 25 Q7: 0 LDC: 1 LDP: 9" };
		String[] funcName = { "Total Capacity", "Pre Built Units", "Capacity Remaining for Local", "Planned Capacity",
				"Unplanned Capacity", "Remaining Capacity" };
		String screenName = "Flight Load Plan";

		for (int i = 0; i < expVerfVals.length; i++)
			verifyValueOnPageContains(expVerfVals[i].replace(" ", ""), actVerfVals[i].replace(" ", ""),
					"Verfify " + funcName[i], screenName, funcName[i]);

	}
/**
 * Description... Verify Flight Header Info
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verfFlightHeaderInfo() throws InterruptedException, IOException {
		int verfColsLDC[] = { 4, 5, 6, 7 };
		String actVerfValuesLDC[] = { "1", "10", "90", "0.15" };
		verify_tbl_records_multiple_cols("FlightLoadPlan_OPR015", "table_positionTable;xpath", "//td", verfColsLDC,
				data("ULDTypeNoLDC"), actVerfValuesLDC);

		int verfColsLDP[] = { 4, 5, 6, 7 };
		String actVerfValuesLDP[] = { "1", "10", "125", "0.15" };
		verify_tbl_records_multiple_cols("FlightLoadPlan_OPR015", "table_positionTable;xpath", "//td", verfColsLDP,
				data("ULDTypeNoLDP"), actVerfValuesLDP);

		int verfColsMDP[] = { 4, 5, 6, 7 };
		String actVerfValuesMDP[] = { "1", "10", "125", "0.15" };
		verify_tbl_records_multiple_cols("FlightLoadPlan_OPR015", "table_positionTable;xpath", "//td", verfColsMDP,
				data("ULDTypeNoMDP"), actVerfValuesMDP);

	}
/**
 * Description... Check AWB
 * @param AWB
 * @throws InterruptedException
 */
	public void checkAWB(String AWB) throws InterruptedException {
		waitForSync(10);
		selectTableRecord(AWB, "chk_AWB;xpath", sheetName, 3);
		waitForSync(2);
	}
/**
 * Description... Click Release To Ops Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickReleaseToOpsButton() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_releaseToOps;id", "Release to Ops Button", ScreenName);
		waitForSync(5);
		switchToFrame("default");
		ele = driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"));
		ele.click();
		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR015");
	}
/**
 * Description... Verify Warning Msg
 * @param expText
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyWarningMsg(String expText) throws InterruptedException, AWTException {

		switchToFrame("default");
		By element = getElement(sheetName, "div_info;xpath");
		String errorText = driver.findElement(element).getText();

		verifyValueOnPage(errorText, data(expText), "Flight already departed warning verification", sheetName,
				"Flight already departed warning verification");

		// switchToFrame("default");
		ele = driver.findElement(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]"));
		ele.click();
		Thread.sleep(2000);
		switchToFrame("contentFrame", "OPR015");
	}
/**
 * Description... Click UnAssign AWB
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickUnAssignAWB() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_unassignAwb;name", "Unassign AWB Button", ScreenName);
		waitForSync(6);
	}
/**
 * Description... Verify Save Final Version
 * @param expVersionSaved
 * @param expVersionSavedFinal
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifySaveFinalVersion(String expVersionSaved, String expVersionSavedFinal)
			throws InterruptedException, AWTException {

		String savedVersion = getElementText("FlightLoadPlan_OPR015", "label_SavedVersion;xpath", "Saved Version",
				"Flight Load Plan");

		String finalVersion = getElementText("FlightLoadPlan_OPR015", "label_FinalVersion;xpath", "Final Version",
				"Flight Load Plan");

		verifyValueOnPage(savedVersion, expVersionSaved, "Load Plan saved version verification ", sheetName,
				"Load Plan saved version verification ");
		verifyValueOnPage(finalVersion, expVersionSavedFinal, "Load Plan final version verification ", sheetName,
				"Load Plan final version verification ");

	}
/**
 * Description... Verify Final Version
 * @param expVersion
 * @throws InterruptedException
 * @throws AWTException
 */
	public void verifyFinalVersion(String expVersion) throws InterruptedException, AWTException {

		String finalVersion = getElementText("FlightLoadPlan_OPR015", "label_FinalVersion;xpath", "Final Version",
				"Flight Load Plan");

		verifyValueOnPage(finalVersion, expVersion, "Load Plan final version verification ", sheetName,
				"Load Plan final version verification ");

	}
/**
 * Description... Check All AWB
 * @throws InterruptedException
 * @throws IOException 
 */
	public void checkAllAWB() throws InterruptedException, IOException {
		Thread.sleep(3000);
		clickWebElement(sheetName, "chk_masterAWB;name", "Check All AWB checkbox", ScreenName);

	}

/**
 * Description... Verify Button State
 * @throws InterruptedException
 */
 	public void verifyButtonState() throws InterruptedException
       {
              verifyElementIsEnabled("FlightLoadPlan_OPR015","bttn_configure;xpath",
                           "verify configure button enable or not",ScreenName,"Configure Button","enable");
              waitForSync(5);
              verifyElementIsEnabled("FlightLoadPlan_OPR015","bttn_uldconfigure;xpath", 
                            "verify ULD configure button enable or not",ScreenName,"uldconfigure","disable");
       }
       
  /**
   * Description...  Click Configure Segment Positions    
   * @throws Exception
   */
              public void clickConfigureSegmentPositions() throws Exception {
              clickButtonSwitchWindow(sheetName, "bttn_configure;xpath", "Configure Segment", ScreenName);
   
       }
/**
 * Description... Get Value After Clicking Configure Segment Positions
 * @return
 * @throws Exception
 */
public String getValueAfterClickingConfigureSegmentPositions() throws Exception
{
// getting the values of weight ,volume and each sections of ULD positions for each leg.

String pos=getElementText("FlightLoadPlan_OPR015","values_intable;xpath",
"values in box", ScreenName);

String weight=getElementText("FlightLoadPlan_OPR015","value_weight;xpath",
"values in box",ScreenName);


String volume=getElementText("FlightLoadPlan_OPR015","value_volume;xpath",
"values in box", ScreenName);
waitForSync(5);
System.out.print(pos+" "+weight+" "+volume);
String wt[]= weight.split("\\.");
String vol[] = volume.split("\\.");
if(wt[1].equals("0")){
        weight =wt[0];
}
if(vol[1].equals("0")){
volume = vol[0];
}
return pos+"/"+weight+"/"+volume;

}


/**
 * Description... Get Second Value After Clicking Configure Segment Positions
 * @return
 * @throws InterruptedException
 */



	public String getSecondValueAfterClickingConfigureSegmentPositions() throws InterruptedException
{
String pos=getElementText("FlightLoadPlan_OPR015","values_2leg;xpath","values of second leg",ScreenName);
System.out.print("///**********************"+pos);

String weight=getElementText("FlightLoadPlan_OPR015","values_weight1;xpath",
"values in box", ScreenName);
String volume=getElementText("FlightLoadPlan_OPR015","values_volume1;xpath",
"values in box", ScreenName);
waitForSync(5);
System.out.print(pos+"/"+weight+"/"+volume);
String wt[]= weight.split("\\.");
String vol[] = volume.split("\\.");
if(wt[1].equals("0")){
weight =wt[0];
}
if(vol[1].equals("0")){
volume = vol[0];
}
return pos+"/"+weight+"/"+volume;

}

/**
 * Description... Enter Remarks AWB
 * @param AWBRemarks
 * @throws InterruptedException
 * @throws IOException 
 */
public void enterRemarksAWB(String AWBRemarks) throws InterruptedException, IOException {
		waitForSync(3);
		clickWebElement(sheetName, "inbx_AWBremarks;xpath", "Remark for AWB", "OPR015");
		enterValueInTextbox(sheetName, "inbx_AWBremarks;xpath", AWBRemarks, "Remark for AWB", ScreenName);
		waitForSync(5);
	}
	
/**
 * Description... Click Print Pict Load Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickPrintPictLoad() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_PrintPictLoad;name", "Print Pict Load Button", ScreenName);
		waitForSync(8);
	}
 
	/**
	 * Description... Get Element Text Print Pict Popup
	 * @param locator
	 * @return
	 * @throws Exception
	 */

	public String getElementTextPrintPictPopup(String locator) throws Exception {


              waitForSync(3);
              switchToWindow("storeParent");
              switchToWindow("child");
              waitForSync(3);
              switchToFrame("default");
              waitForSync(3);
              driver.switchTo().frame("ReportContainerFrame");
              waitForSync(3);
              String remarks=getElementText("FlightLoadPlan_OPR015",locator,"Remarks Text Print Pict",ScreenName).trim();
              waitForSync(8);
              switchToWindow("getParent");
              return remarks;
                           
       }

/**
 * Description... Enter Position Details
 * @param ULDno
 * @param RemarksPosDet
 * @throws InterruptedException
 */
	public void enterPositionDetails(String ULDno, String RemarksPosDet) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_UDLnoPositiondet;xpath", ULDno, "ULDno", ScreenName);
		selectValueInDropdown(sheetName, "lst_contourPositiondet;xpath", data("contour"), "Contour", "VisibleText");
		selectValueInDropdown(sheetName, "lst_POLPositiondet;xpath",data("Origin"), "Origin", "VisibleText");
		selectValueInDropdown(sheetName, "lst_POUPositiondet;xpath",data("Destination"), "Destination", "VisibleText");
		selectValueInDropdown(sheetName, "lst_PosPriorPositiondet;xpath", data("PosPriority"), "Position Priority", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_remarksPositiondet;xpath", RemarksPosDet, "Remark for AWB", ScreenName);
	}
/**
 * Description...	Verify Load Advice Message 
 * @param msgverf
 * @param msgData
 * @throws InterruptedException
 */
// msgverf shows the message to be verified against the one fetched from screen
// msgData refers to the data the message contains. Example like load advice message version 	
	public void verifLoadAdviceMsg(String msgverf, String msgData) throws InterruptedException {
		
		
		String msgactual=getElementTextnoFrameSwitch(sheetName, "txt_toastcontainerMsg;xpath", "Toast Container Msg", "Flight Load Plan").trim();
		System.out.println(msgactual);
		System.out.println(msgverf);
		if(msgactual.equalsIgnoreCase(msgverf))
			writeExtent("Pass","Toast Container Message verified Successfully for "+ msgData);
		else
			writeExtent("Fail", "Toast Container Message not verified for" +msgData);
		waitForSync(8);
	}
	/**
	 * Description... Scroll TO View
	 * @param locator
	 * @throws InterruptedException
	 */
	public void scrollTOView(String locator) throws InterruptedException {
		
		By ele = getElement(sheetName, locator);
		WebElement ele1 = driver.findElement(ele);
		moveScrollBar(ele1);
	}
/**
 * Description... Click Lying List Button
 * @throws Exception
 */
public void clickLyingListButton() throws Exception {
		clickButtonSwitchWindow(sheetName, "btn_lyinglist;xpath", "Lying List", ScreenName);
		waitForSync(10);
	}
/**
 * Description...	Click AWB ChkBox Lying List PopUp 
 * @param locator
 * @throws Exception
 */
	public void clickAWBChkBoxLyingListPopUp( String locator ) throws Exception {
		
		By element = getElement(sheetName, locator);
		WebDriverWait wait = new WebDriverWait(driver, 60);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
		
		checkIfUnchecked(sheetName, locator, "AWB CheckBox", ScreenName);
		
	}
/**
 * Description... Click OK Lying List PopUp
 * @throws Exception
 */
	public void clickOKLyingListPopUp() throws Exception {
		clickButtonSwitchtoParentWindow(sheetName, "btn_okLyingListPopup;xpath", "Lying List Ok Button", ScreenName);
		
		waitForSync(5);
	}
/**
 * Description... Verify Build Up Summary Plan Pre Built Unit
 * @param LDC
 * @param LDP
 * @param MDP
 * @param Q
 * @param wt
 * @param vol
 * @throws InterruptedException
 * @throws IOException 
 */
public void verfBuildUpSummaryPlanPreBuiltUnit(String LDC, String LDP, String MDP, String Q, String wt, String vol) throws InterruptedException, IOException {
		
		String preBuiltUnits = getElementTextnoFrameSwitch("FlightLoadPlan_OPR015", "tab_preBuiltUnits;xpath", "Pre Built Units ULD",
				"Flight Load Plan").trim();
		String preBuiltUnitsWtandVol = getElementTextnoFrameSwitch("FlightLoadPlan_OPR015", "tab_preBuiltUnitsWtVol;xpath", "Pre Built Units wt and vol",
				"Flight Load Plan").trim();
	

		String[] expVerfVals1 = {preBuiltUnits};
		String[] actVerfVals1 = { "UPOS:  MDP: "+MDP+" Q7: "+Q+" LDC: "+LDC+" LDP: "+LDP};
		System.out.println(actVerfVals1);
		String[] expVerfVals2 = {preBuiltUnitsWtandVol};
		String[] actVerfVals2 = { "Wt/Vol: "+wt+"K/"+vol+"B" };
		String[] funcName =  {"Pre Built Units"};
		String screenName = "Flight Load Plan";

		for (int i = 0; i < expVerfVals1.length; i++)
			verifyValueOnPageContains(expVerfVals1[i].replace(" ", ""), actVerfVals1[i].replace(" ", ""),
					"Verfify " + funcName[i], screenName, funcName[i]);
		
		for (int i = 0; i < expVerfVals2.length; i++)
			verifyValueOnPageContains(expVerfVals2[i].replace(" ", ""), actVerfVals2[i].replace(" ", ""),
					"Verfify " + funcName[i], screenName, funcName[i]);

	}
/**
 * Description... Select One AWB
 * @param no
 * @throws InterruptedException
 */
public void selectOneAWB(String no) throws InterruptedException
	{
		clickWebElement("(//*[@name='selectAwbShipment'])"+"["+no+"]","AWB"+" "+no,ScreenName);
	}
/**
 * Description... Verify Error Message
 * @param expMsg
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyErrorMessge(String expMsg) throws InterruptedException, IOException
	{
		 String actMsg = driver.findElement(By.xpath("//td[@class='ic-error-msg']")).getText();
		if(actMsg.contains(expMsg)){
			
			System.out.println(expMsg+ " is displayed on " + ScreenName + " Page");
			writeExtent("Pass", expMsg+ " is displayed on " + ScreenName + " Page" );
			
		}else{
			
			System.out.println(expMsg+ " is not displayed on " + ScreenName + " Page");
			writeExtent("Fail", expMsg+ " is not displayed on " + ScreenName + " Page" );
			Assert.assertFalse(true, expMsg + " is not displayed on " + ScreenName + " Page");
		        			
		}
		
		closeError();
				
		}
/**
 * Description... Get Text From Unplanned Capacity And Compare
 * @param sheetName
 * @param locatorweight
 * @param locatorvolume
 * @throws InterruptedException
 */
	public void getTextFromUnplannedCapacityAndCompare(String sheetName,String locatorweight,String locatorvolume) throws InterruptedException
	{
		String value=getElementText(sheetName,"table_unplanned;xpath","Unplanned Capacity info","Flight Load Plan");
		String weight=getAttributeWebElement(sheetName,locatorweight,"Weight Value from table","value",
				 "Flight Load Plan");
		String volume=getAttributeWebElement(sheetName,locatorvolume,"Volume Value from table","value",
				 "Flight Load Plan");
		System.out.print(value+" -----------------"+weight+"//////////// "+volume);
		waitForSync(8);
		 String act[]=value.split(":");
		 String exp=" "+weight+"K/"+volume+"B";
		 verifyValueOnPage(act[1], exp, "Verify"+exp,"Flight Load Plan",
					"Compare the unplanned capacity");
	}
/**
 * Description... Verify ULD Position
 * @param exp
 * @param elename
 * @param awbNo
 * @throws InterruptedException
 */
public void verifyULDPosition(String exp,String elename,String awbNo) throws InterruptedException
	{
		
		String pos=getElementText("FlightLoadPlan_OPR015","table_uldpos;xpath","uld position of first awb","FlightLoadPlan_OPR015");
		verifyValueOnPage(pos,exp,"Verify"+exp,"Flight Load Plan",
					elename);
		
	}
/**
 * Description... Verify Position Detail
 * @param pmyKey
 * @param verfCols
 * @param actVerfValues
 * @throws InterruptedException
 * @throws IOException 
 */
public void verifyPosDetail(String pmyKey,int verfCols[],String actVerfValues[]) throws InterruptedException, IOException
	{
	

		
		verify_tbl_records_multiple_cols(sheetName, "table_positionTable;xpath", "//td", verfCols,
				pmyKey, actVerfValues);
	
	}
	
/**
 * Description... Verify POL And POU
 * @param pos
 * @param verfCols
 * @param actVerfValues
 * @param actValuespou
 * @throws InterruptedException
 */
public void verifyPOLAndPOU(String pos[],int verfCols[],String actVerfValues[],String actValuespou[]) throws InterruptedException
	{
	

		
		String actPOL[] =new String[pos.length],actPOU[] = new String[pos.length];
		for(int i=0;i<(pos.length);i++)
		{		
		String  xpathPOL=xls_Read.getCellValue("FlightLoadPlan_OPR015", "table_positionPOL;xpath").replace("pos",pos[i]);
		String  xpathPOU=xls_Read.getCellValue("FlightLoadPlan_OPR015", "table_positionPOU;xpath").replace("pos",pos[i]);
		
		String actPOL0=getFirstSelectedOptionDropdown(xpathPOL, "");
		String actPOU0=getFirstSelectedOptionDropdown(xpathPOU, "");
		
		actPOL[i]=actPOL0;
		actPOU[i]=actPOU0;
		}
		
		for(int i=0;i<(pos.length);i++)
		{
		
		verifyValueOnPage(actPOL[i], actVerfValues[i], "", "POL", "");
		verifyValueOnPage(actPOU[i], actValuespou[i], "", "POU", ""); 
		 
		}
		 
		
	}
/**
 * Description... Fill Segment Position Values
 * @param value
 * @throws InterruptedException
 */
public void fillSegmentPositionValues(String[] value) throws InterruptedException
	{    
		
		String locator="(//*[@class='iCargoTextFieldSmall'])";
		for(int i=1;i<=12;i++)
		{
			
		String xpath=locator+"["+i+"]";
		//enterValueInTextbox(sheetName,xpath,value[i],"segment postion values: LDP,LDC,MDP,Q7","Flight Load Plan");
		enterValueInTextbox(xpath,value[i-1],"segment postion values: LDP,LDC,MDP,Q7","Flight Load Plan");
		}
		
		
	}
/**
 * Description...	Click OK Button 	
 * @throws Exception
 */
	public void clickOKButton() throws Exception
	{
		
		
		clickButtonSwitchtoParentWindow(sheetName,"btn_okpopup;xpath","OK button",ScreenName);
	}
/**
 * Description... Compare Values In DropDown
 * @param pos
 * @throws InterruptedException
 */
	public void compareValuesInDropDown(String pos[]) throws InterruptedException
	{
		
		By ele = getElement("FlightLoadPlan_OPR015","lst_uld;name");
		WebElement ele1 = driver.findElement(ele);
		String options[]=getAllOptions(ele1);
		for(int i=0;i<(pos.length);i++)
		{
		
			verifyValueOnPage(options[i], pos[i], "Verifying the positions in dropdown", "Flight loadPlan", "Verify"); 
		 
		}
		 
		
		
	}
/**
 * Description...	Update Segment Position Values 
 * @param pos
 * @param value
 * @throws InterruptedException
 */
public void updateSegmentPositionValues(String pos,String value) throws InterruptedException
	{    
		
		String locator="(//*[@class='iCargoTextFieldSmall'])";
		
			
		String xpath=locator+"["+pos+"]";
		
		enterValueInTextbox(xpath,value,"segment postion values: LDP,LDC,MDP,Q7","Flight Load Plan");
		
		
		
	}
/**
 * Description... Get Value And Verify
 * @param exp
 * @param pos
 * @param loc
 * @throws InterruptedException
 * @throws IOException 
 */
public void getValueAndVerify(String exp,String pos,String loc) throws InterruptedException, IOException
	{
		String locator=xls_Read.getCellValue("FlightLoadPlan_OPR015",loc);
		String dynxpath=locator+"["+pos+"]";
		ele = driver.findElement(By.xpath(dynxpath));
	
		
		if (exp==null)
			
		{
			verifyNullValues(ele," ", "FlightLoadPlan_OPR015");
			
			
		}
			
		else{
		String text=getElementText(ele,"uld shipment pos","FlightLoadPlan_OPR015");
		verifyValueOnPageContains(text, exp,"","FlightLoadPlan_OPR015","verify pos ");
		}
		
	}
/**
 * Description... Click Close Button
 * @throws Exception
 */
public void clickCloseButton() throws Exception
	{
		clickButtonSwitchtoParentWindow(sheetName,"btn_closepopup;xpath","close button",ScreenName);
	}
/**
 * Description...	Close Error 
 * @throws InterruptedException
 * @throws IOException 
 */
public void closeError() throws InterruptedException, IOException{
		
		clickWebElement(sheetName, "btn_closeErrorMsg;xpath", "ULD Tab", ScreenName);
	}
/**
 * Description... Get Auto Assigned Position Value
 * @param MDP
 * @param NOofAWB
 * @return
 * @throws InterruptedException
 */
	public String getAutoAssignedPositionValue(String MDP, String NOofAWB) throws InterruptedException
{
       String actVerfValuesMDP = "";
       try{
       String locator=xls_Read.getCellValue("FlightLoadPlan_OPR015","table_ULDdetails;xpath");
       String dynxpath1=locator+"[contains(.,'"+MDP+"')]//td[8]";
       ele = driver.findElement(By.xpath(dynxpath1));
       String GrossWt=getElementText(ele,MDP+" pos Gross Wt","FlightLoadPlan_OPR015");
       
       
       String dynxpath3=locator+"[contains(.,'"+MDP+"')]//td[10]";
       ele = driver.findElement(By.xpath(dynxpath3));
       String TotPcs=getElementText(ele,MDP+" pos Net Wt","FlightLoadPlan_OPR015");
       
       actVerfValuesMDP = NOofAWB +"," + TotPcs+","+ GrossWt ;
       
       
       }catch(Exception e){
              
              System.out.println("Could not return text from element " + MDP +"position"
                           + " on " + ScreenName);
              writeExtent("Fail", "Could not return text from element " + MDP +"position"
                           + " on " + ScreenName);
       }
       return actVerfValuesMDP;
       
}
public void enterPosition(String position) {
	System.out.println(data(position));
	selectValueInDropdown(sheetName, "lst_uld;name", data(position), "ULD Type", "VisibleText");
	
}
/**
 * @author A-7271
 * @param segment
 * Desc : Select segment
 * @throws IOException 
 * @throws InterruptedException 
 */
public void selectSegment(String segment) throws InterruptedException, IOException {
	
	selectValueInDropdown(sheetName, "leg_drpdown;xpath", data(segment), "Segment", "VisibleText");
	waitForSync(2);
	clickWebElement(sheetName, "btn_planForSegment;id", "Plan for segment", ScreenName);
	waitForSync(5);
}
/**
 * Description... Enter Position Details
 * @param ULDno
 * @param RemarksPosDet
 * @throws InterruptedException
 */
	public void enterPositionDetails(String ULDno, String Contour,String RemarksPosDet) throws InterruptedException {
		enterValueInTextbox(sheetName, "inbx_UDLnoPositiondet;xpath", data(ULDno), "ULDno", ScreenName);
		selectValueInDropdown(sheetName, "lst_contourPositiondet;xpath", data(Contour), "Contour", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_remarksPositiondet;xpath", RemarksPosDet, "Remark for AWB", ScreenName);
	}



}