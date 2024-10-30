package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class BreakDownScreen_OPR004 extends CustomFunctions {

	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "BreakDown_OPR004";
	String screenName = "Breakdown : OPR004";

	public BreakDownScreen_OPR004(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}

	/**
	 * @Description : Click Checkbox All 
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void clickCheckBoxAll() throws InterruptedException, IOException {
		clickWebElement(sheetName, "chk_chkAll;name", "Check All", screenName);
		Thread.sleep(2000);
	}
    /**
	 * @Description : verify checksheet is not complete
	 * @author A-10330
	 * param expText
	 * param FullAWBno
	 */
	public void verifyCheckSheetErrorMessage( String expText,String FullAWBno) 
	{
		try
		{
			expText=expText.replace("awbno", data(FullAWBno));

			waitForSync(2);
			String acttext=getElementText(sheetName, "txt_checksheet_verf;xpath", "checksheet error", screenName);
			verifyScreenTextWithExactMatch(sheetName, expText,acttext , "checksheet verification","breakdown operation");
			writeExtent("Pass", "check sheet is not completed at "+ screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail", "check sheet error message is not verified "+ screenName);	
		}
	}
	/**
	 * @Description : get the SU Number
	 * @author A-9844
	 * @return
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public String getSUNumber(String awbNo) throws InterruptedException, AWTException, IOException {
		String suNo="";
		String suLocator = xls_Read.getCellValue(sheetName, "txt_SUNumber;xpath");
		suLocator=suLocator.replace("AWB", data(awbNo));
		suNo=driver.findElement(By.xpath(suLocator)).getAttribute("value");
		System.out.println(suNo);
		return suNo;
	}

    /**
* @Description : captureChecksheet of AVI shipment
* @author A-10330
* param chkSheetRequired
* param date
* param time
     * @throws IOException 
     * @throws InterruptedException 
*/
public void captureChecksheetAVI(boolean chkSheetRequired,String date, String time) throws InterruptedException, IOException
{
	boolean checkSheetExists=true;
	try
	{

		waitForSync(3); 

		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);


		List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
		if(questions.size()==0)
		{
			checkSheetExists=false;
		}

		for(WebElement ele : questions)
		{
			Select select = new Select(ele);
			select.selectByVisibleText("Yes");
		}
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_answerDate;name", date, "Date when animal was fed", screenName);
		enterValueInTextbox(sheetName, "inbx_answerDateTime;name", time, "Time when animal was fed", screenName);
		if(chkSheetRequired)
		{
			if(checkSheetExists)
			{
				writeExtent("Pass","Check sheet details selected on "+screenName);
			}

			else
			{
				writeExtent("Fail","No check sheet details configured on "+screenName);
			}
		}

		clickWebElement("Generic_Elements", "btn_ok3;id", "Save Button", screenName);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
		switchToFrame("default");

		switchToFrame("contentFrame", "OPR004");
		driver.switchTo().frame("popupContainerFrame");
		clickWebElementByWebDriver("BreakDown_OPR004", "btnCloseChecksheet;id", "Close button", screenName);
		switchToFrame("default");
		waitForSync(3);
		switchToFrame("contentFrame", "OPR004");

		if(chkSheetRequired)
		{
			if(checkSheetExists)
			{
				writeExtent("Pass","Check sheet details saved on "+screenName);
			}
		}
	}

	catch(Exception e)
	{
		writeExtent("Fail","Could not save check sheet details on "+screenName);
	}
	
	clickWebElement("Generic_Elements", "btn_ok3;id", "Save Button", screenName);
	switchToFrame("default");

}
/**
 * @author A-9175
 * @Description : get Split SU Number
 * @param awbNo
 * @return
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException
 */
public String[] getSplitSUNumber(String awbNo) throws InterruptedException, AWTException, IOException {
	String suLocator = xls_Read.getCellValue(sheetName, "txt_splitSUNumber;xpath");
	suLocator=suLocator.replace("AWB", data(awbNo));
	List<WebElement> splitSu=driver.findElements(By.xpath(suLocator));
	ArrayList<String> splitSuNumList = new ArrayList<String>();
	System.out.println(splitSu.size());
    for (WebElement webElement : splitSu) {
        String su = webElement.getText();
        splitSuNumList.add(su);
    }
    String[] splitSuNum = new String[splitSuNumList.size()];
    splitSuNum = splitSuNumList.toArray(splitSuNum);
	System.out.println(splitSuNumList);
    return splitSuNum;
}

/** @author A-9175
* @Description : Updating split breakdown details
* @param numberOfSplits
* @param pieces
* @param weight
* @param location
* @param sccs
* @throws Exception
*/
public void updateSplitBreakdown(int numberOfSplits, String[] pieces, String[] weight, String[] location, String[] sccs)
		throws Exception 
{
	clickWebElement(sheetName, "chk_chkAllSplit;name", "Select All Button", screenName);
	clickWebElement(sheetName, "btn_delete;id", "Delete Button", screenName);
	enterValueInTextbox(sheetName, "inbx_noofSplit;id", Integer.toString(numberOfSplits) , "Split into", screenName);
	keyPress("TAB");
	keyPress("TAB");
	int k=0;
	for (int i = numberOfSplits; i <=numberOfSplits+2; i++) {
		String y = Integer.toString(i);
		try{
			// Enter pieces
			String splitPieces = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
			splitPieces = splitPieces.replace("rowno", y);
			driver.findElement(By.xpath(splitPieces)).sendKeys(pieces[k]);
		}catch(Exception e){
			writeExtent("Fail","Failed to enter the split pieces on "+screenName);  
		}

		try{
			// Enter weight
			String splitWeight = xls_Read.getCellValue(sheetName, "inbx_splitWeight;xpath");
			splitWeight = splitWeight.replace("rowno", y);
			driver.findElement(By.xpath(splitWeight)).clear();
			driver.findElement(By.xpath(splitWeight)).sendKeys(weight[k]);
		}catch(Exception e){
			writeExtent("Fail","Failed to enter the split weight on "+screenName);}

		try{
			// Enter SCC
			int s=i-1;
			String SccDrpdn=xls_Read.getCellValue(sheetName, "drpdn_splitScc;id");
			SccDrpdn = SccDrpdn.replace("*",Integer.toString(s));
			driver.findElement(By.id(SccDrpdn)).click();
			waitForSync(2);
			String splitSCC = xls_Read.getCellValue(sheetName, "sel_splitScc;xpath");
			splitSCC = splitSCC.replace("&", Integer.toString(s)).replace("*", sccs[s-1]);
			moveScrollBar(driver.findElement(By.xpath(splitSCC)));
			driver.findElement(By.xpath(splitSCC)).click();
			keyPress("TAB");
			keyPress("TAB");
		}catch(Exception e){
			writeExtent("Fail","Failed to enter the SCC on "+screenName);}

		try{
			// Enter location
			String splitLocation = xls_Read.getCellValue(sheetName, "inbx_splitLocation;xpath");
			splitLocation = splitLocation.replace("rowno", y);
			driver.findElement(By.xpath(splitLocation)).sendKeys(location[k]);
		}catch(Exception e){
			writeExtent("Fail","Failed to enter the split location on "+screenName);}

		k++;
	}
}


/**
 * @Description : captureChecksheet brkdwn opr004
 * @author A-10330
* param chkSheetRequired
 * @throws IOException 
 * @throws InterruptedException 
 **/

public void captureChecksheet(boolean chkSheetRequired) throws InterruptedException, IOException
{
	boolean checkSheetExists=true;
	try
	{
		waitForSync(3); 

		driver.switchTo().frame("popupContainerFrame");
		waitForSync(3);


		List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
		if(questions.size()==0)
		{
			checkSheetExists=false;
		}

		for(WebElement ele : questions)
		{
			Select select = new Select(ele);
			select.selectByVisibleText("Yes");
		}
		waitForSync(3);

		if(chkSheetRequired)
		{
			if(checkSheetExists)
			{
				writeExtent("Pass","Check sheet details selected on "+screenName);
			}

			else
			{
				writeExtent("Fail","No check sheet details configured on "+screenName);
			}
		}
		clickWebElement("Generic_Elements", "btn_ok3;id", "Save Button", screenName);
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
		switchToFrame("default");

		switchToFrame("contentFrame", "OPR004");
		if(chkSheetRequired)
		{
			if(checkSheetExists)
			{
				writeExtent("Pass","Check sheet details saved on "+screenName);
			}
		}
	}

	catch(Exception e)
	{
		writeExtent("Fail","Could not save check sheet details on "+screenName);
	}
	
	

}


/**
 * @Description : verification of blocked error msg
 * @author A-10330
 * param expText
*/
		public void verifyErrorMsg( String expText) 
		{
			try
			{
				

				waitForSync(2);
				String acttext=getElementText(sheetName, "htmlDiv_errorverf;xpath", "Error msg", screenName);
				verifyScreenTextWithExactMatch(sheetName, expText,acttext , "Error msg verification","breakdown operation");
				
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Error message "+expText+"is not verified on"+ screenName);	
			}
		}

/**
* @Description : verify breakdown status
* @author A-10330
* param expText
*/
public void breakDownStatus(String expText)
{
try
{
	waitForSync(2); 
	
	String acttext=getElementText(sheetName, "txt_bdnstatus;xpath", "breakdown status", screenName);
	verifyScreenTextWithExactMatch(sheetName, expText,acttext , "breakdown status","breakdown operation");
	writeExtent("Pass", "break down is completed "+ screenName);
}
catch(Exception e)
{
	writeExtent("Fail", "break down is not completed "+ screenName); 
}

}


	/**
	 * @author A-6260
	 * @Desc: select the required awbs
	 * @param awbs
	 */
	public void selectAWBs(String[] awbs) {
		try {
			String tableBody = xls_Read.getCellValue(sheetName, "tbl_breakdowndetails;xpath");
			List<WebElement> rows = driver.findElements(By.xpath(tableBody));
			for(int j=0; j<awbs.length;j++) {
				for (int i = 0; i <= rows.size(); i++) {
					if (rows.get(i).getText().toLowerCase().replace(" ", "")
							.contains(awbs[j].toLowerCase().replace(" ", ""))) {
						String locator = xls_Read.getCellValue(sheetName, "tbl_breakdowndetails;xpath");
						locator=locator+"["+Integer.toString(i)+"]//td[1]";
						driver.findElement(By.xpath(locator)).click();
						waitForSync(2);
						break;

					}
				}
			}

		}catch(Exception e)
		{
			writeExtent("Fail", "Couldn't select AWB in  "+screenName);
		}
	}
	/**
	 * @author A-9175
 * @Description : Switching in to split scc window
	 * @throws Exception
	 */
	
	public void switchInToSplitWindow() throws Exception 
	{
		Thread.sleep(2000);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitBreakdownIcon;xpath", "Split Breakdown Icon", screenName);
		waitForSync(2);
		switchToWindow("child");
	}
	/**
	 * @author A-9175
	 * @Description : Verification of Split SCC Summary details table
	 * @throws Exception
	 */
	public void verifySplitSccSummaryDetails(int verfCols[], String actVerfValues[],String pmkey) throws Exception {
		verify_tbl_records_multiple_cols(sheetName, "table_SplitSCC_summary;xpath","//td", verfCols, pmkey,
				actVerfValues);
	}
	/**
	 * @author A-9175
	 * @Description : Switching out from split scc window
	 * @throws Exception
	 */
	public void switchOutToSplitWindow() throws Exception 
	{
		clickWebElement(sheetName, "btn_Ok;name", "Ok Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR004");
	}



/**
	 * @author A-10328
	 * @Desc To capture Split Breakdown Info along with Sccs
	 * @param numberOfSplits
	 * @param pieces
	 * @param weight
	 * @param location
	 * @param sccs
	 * @throws Exception
	 */

public void captureSplitBreakdown(int numberOfSplits, String[] pieces, String[] weight, String[] location, String[] sccs)
			throws Exception 
{

switchToWindow("storeParent");
clickWebElement(sheetName,"btn_splitBreakdownIcon;xpath", "Split Breakdown Icon", screenName);
waitForSync(3);
switchToWindow("child");
enterValueInTextbox(sheetName, "inbx_noofSplit;id", Integer.toString(numberOfSplits) , "Split into", screenName);
keyPress("TAB");


int k=0;
for (int i = 1; i <=numberOfSplits; i++) 

{
String y = Integer.toString(i);
try
{
// Enter pieces

String splitPieces = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
splitPieces = splitPieces.replace("rowno", y);
driver.findElement(By.xpath(splitPieces)).sendKeys(pieces[k]);

            }
catch(Exception e){

writeExtent("Fail","Failed to enter the split pieces on "+screenName);  
				}

            
 try
{
// Enter weight
String splitWeight = xls_Read.getCellValue(sheetName, "inbx_splitWeight;xpath");
splitWeight = splitWeight.replace("rowno", y);
driver.findElement(By.xpath(splitWeight)).clear();
driver.findElement(By.xpath(splitWeight)).sendKeys(weight[k]);
            }
catch(Exception e){

writeExtent("Fail","Failed to enter the split weight on "+screenName);

}

 try{
			// Enter SCC
String SccDrpdn=xls_Read.getCellValue(sheetName, "drpdn_splitScc;id");
			
SccDrpdn = SccDrpdn.replace("*",Integer.toString(k));
waitForSync(2);
driver.findElement(By.id(SccDrpdn)).click();
waitForSync(2);
String splitSCC = xls_Read.getCellValue(sheetName, "sel_splitScc;xpath");
splitSCC = splitSCC.replace("&", Integer.toString(k)).replace("*", sccs[k]);
moveScrollBar(driver.findElement(By.xpath(splitSCC)));
driver.findElement(By.xpath(splitSCC)).click();
keyPress("TAB");
            }
catch(Exception e){
writeExtent("Fail","Failed to enter the SCC on "+screenName);

}
            
 try
{
			// Enter location
String splitLocation = xls_Read.getCellValue(sheetName, "inbx_splitLocation;xpath");
splitLocation = splitLocation.replace("rowno", y);
driver.findElement(By.xpath(splitLocation)).sendKeys(location[k]);
            }
catch(Exception e){
writeExtent("Fail","Failed to enter the split location on "+screenName);

}

			k++;
		}

clickWebElement(sheetName, "btn_Ok;name", "Ok Button", screenName);
switchToWindow("getParent");
switchToFrame("default");
waitForSync(3);
switchToFrame("contentFrame", "OPR367");
}
/**
	 * @author A-10328
	 * @param verfCols
	 * @param actVerfValues
 * @throws InterruptedException
      	 * @throws IOException
      	 * Desc : verify SU details
      	 */

public void verifySUDetails(int verfCols[], String actVerfValues[],String awb)throws InterruptedException, IOException 

{
	verify_tbl_records_multiple_cols(sheetName, "tbl_breakdowndetails;xpath", "//td", verfCols, awb ,actVerfValues);


	}
/**
 * /**
 * @Description... verify SUNumber Displayed
 * @author A-9175
 * @throws Exception
 */
public void verifySUNumberDisplayed(String awbNo) throws InterruptedException, AWTException, IOException {
	
	String suNo="";
	try{
		
		String suLocator = xls_Read.getCellValue(sheetName, "txt_SUNumber;xpath");
		suLocator=suLocator.replace("AWB", data(awbNo));
		suNo=driver.findElement(By.xpath(suLocator)).getAttribute("value");
		System.out.println(suNo);
		System.out.println("SU Number returned for "+data(awbNo)+" : is "+ suNo);
		writeExtent("Pass", "SU Number returned for "+data(awbNo)+" : is "+ suNo +"in Screen "+ screenName);
	}catch (Exception e) {
		writeExtent("Fail", "Failed to return SU Number for "+data(awbNo)+" in Screen "+ screenName);
	}
	
	
}

	/**
	 * @author A-9847
	 * @Desc To capture Split Breakdown Info along with Sccs
	 * @param numberOfSplits
	 * @param pieces
	 * @param weight
	 * @param location
	 * @param sccs
	 * @throws Exception
	 */

	public void captureSplitBreakdownandVerifySccColumn(int numberOfSplits, String[] pieces, String[] weight, String[] location, String[] sccs)
			throws Exception {

		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitBreakdownIcon;xpath", "Split Breakdown Icon", screenName);
		waitForSync(3);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_noofSplit;id", Integer.toString(numberOfSplits) , "Split into", screenName);
		keyPress("TAB");
		//verifying SCC Column present
		try{
			String acttext=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "scc_column;xpath"))).getText();
			verifyScreenTextWithExactMatch(screenName, "SCC",acttext, "SCC Column Verification", "SCC Column");
			writeExtent("Pass", "SCC Column present on "+ screenName);
		}
		catch(Exception e){
			writeExtent("Fail", "SCC Column is not present on " + screenName);
		}

		int k=0;
		for (int i = 1; i <=numberOfSplits; i++) {
			String y = Integer.toString(i);
            try{
			// Enter pieces
			String splitPieces = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
			splitPieces = splitPieces.replace("rowno", y);
			driver.findElement(By.xpath(splitPieces)).sendKeys(pieces[k]);
            }catch(Exception e){
				writeExtent("Fail","Failed to enter the split pieces on "+screenName);  
				}
            
            try{
			// Enter weight
			String splitWeight = xls_Read.getCellValue(sheetName, "inbx_splitWeight;xpath");
			splitWeight = splitWeight.replace("rowno", y);
			driver.findElement(By.xpath(splitWeight)).clear();
			driver.findElement(By.xpath(splitWeight)).sendKeys(weight[k]);
            }catch(Exception e){
				writeExtent("Fail","Failed to enter the split weight on "+screenName);}

            try{
			// Enter SCC
			String SccDrpdn=xls_Read.getCellValue(sheetName, "drpdn_splitScc;id");
			SccDrpdn = SccDrpdn.replace("*",Integer.toString(k));
			driver.findElement(By.id(SccDrpdn)).click();
			waitForSync(2);
			String splitSCC = xls_Read.getCellValue(sheetName, "sel_splitScc;xpath");
			splitSCC = splitSCC.replace("&", Integer.toString(k)).replace("*", sccs[k]);
			moveScrollBar(driver.findElement(By.xpath(splitSCC)));
			driver.findElement(By.xpath(splitSCC)).click();
			keyPress("TAB");
            }catch(Exception e){
				writeExtent("Fail","Failed to enter the SCC on "+screenName);}

            try{
			// Enter location
			String splitLocation = xls_Read.getCellValue(sheetName, "inbx_splitLocation;xpath");
			splitLocation = splitLocation.replace("rowno", y);
			driver.findElement(By.xpath(splitLocation)).sendKeys(location[k]);
            }catch(Exception e){
				writeExtent("Fail","Failed to enter the split location on "+screenName);}

			k++;
		}
		clickWebElement(sheetName, "btn_Ok;name", "Ok Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR004");

	}
	/**
	 * @author A-9847
	 * To verify details inside the Breakdown Location Column
	 * @param rowcount
	 * @param details
	 */
	
	public void verifyBreakdownLocDetails(int rowcount,String[] details){
		
		for(int i=0;i<rowcount;i++)
		{
			try{
			String locator = xls_Read.getCellValue(sheetName, "txt_bdnloc;xpath");
			locator = locator.replace("*", Integer.toString(i+1));
			String actdetails=driver.findElement(By.xpath(locator)).getText().trim();
			verifyScreenTextWithExactMatch(sheetName, details[i], actdetails, "Breakdown Loc Details verification","Breakdown Loc Details verification");
			}catch(Exception e){
				writeExtent("Fail","Failed to verify Breakdown location details on "+screenName);}
		}
		
}
	/**
	 * @Description : Verify the BDN location selected
	 * @author A-10690
	 * @param AWBNo
	 * @param expected Location
	 * @throws InterruptedException
	 * @throws AWTException
	
	 */
	public void verifyBDNLoc(String[] awb,String[] loc) throws InterruptedException, AWTException {
		
		
			
			int count = awb.length;
			for(int i=0;i<count;i++){
			
		
			String locator1 = xls_Read.getCellValue(sheetName, "txt_BDNLocation;xpath");
			locator1=locator1.replace("*", data(awb[i]));
			String actLoc=driver.findElement(By.xpath(locator1)).getAttribute("value");
			if(actLoc.equals(data(loc[i])))
			{
				
				writeExtent("Pass", "Verified BDNlocation selected as expected on "+screenName); 
			}
			else
			{
				writeExtent("Fail", "Failed to verify the BDNLocations as expected on "+screenName); 
			}
					
		}
	}
	/**
	 * @author A-9847
	 * To verify breakdown details having multiple Sccs
	 * @param verfCols
	 * @param actVerfValues
	 * @param sccs
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyBreakdownDetailsWithSccs(int rowcount,String pcss[], String wgt[],String sccs[])
			throws InterruptedException, IOException {

		for(int i=0;i<rowcount;i++){

			//Verifying pieces
			try{
				String locator = xls_Read.getCellValue(sheetName, "txt_rcvdpieces;xpath");
				locator = locator.replace("*", Integer.toString(i+1));
				String pcsVal=driver.findElement(By.xpath(locator)).getAttribute("value");
				verifyScreenTextWithExactMatch(sheetName, data(pcss[i]), pcsVal, "Rcvd Pieces verification","Rcvd Pieces verification");
			}catch(Exception e){
				writeExtent("Fail","Not able to verify the received pieces on "+screenName);
			}

			//Verifying wgt
			try{
				String locator1 = xls_Read.getCellValue(sheetName, "txt_rcvdwgt;xpath");
				locator1 = locator1.replace("*", Integer.toString(i+1));
				String wgtVal=driver.findElement(By.xpath(locator1)).getAttribute("value");
				verifyScreenTextWithExactMatch(sheetName, data(wgt[i]), wgtVal, "Rcvd Weight verification","Rcvd Weight verification");
			}catch(Exception e){
				writeExtent("Fail","Not able to verify the received weight on "+screenName);
			}
			//Verifying SCCs
			try{
				String locator2 = xls_Read.getCellValue(sheetName, "btn_SccDropdwn;id");
				locator2 = locator2.replace("*", Integer.toString(i));
				driver.findElement(By.id(locator2)).click();
				for(int j=0;j<sccs.length;j++){
					String locator3 = xls_Read.getCellValue(sheetName, "scc_drpdn;xpath").replace("&",sccs[j]).replace("*",Integer.toString(i));
					String val=driver.findElement(By.xpath(locator3)).getAttribute("aria-selected");
					if(val.equals("true"))
						writeExtent("Pass","Successfully verified " +sccs[j] +" is selected on "+screenName);	
					else
						writeExtent("Fail","Not able to verify " +sccs[j] +" is selected on "+screenName);	
				}

			}catch(Exception e){
				writeExtent("Fail","Not able to verify the SCCs selected on "+screenName);

			}
		}
	}
	/**
	  * To verify breakdown details having multiple Sccs
	  * @param verfCols
	  * @param actVerfValues
	  * @param sccs
	  * @throws InterruptedException
	  * @throws IOException
	  */
		
		public void verifyBreakdownDetailsWithSccs(int verfCols[], String actVerfValues[],String sccs[])
				throws InterruptedException, IOException {
			verify_tbl_records_multiple_cols(sheetName, "tbl_breakdowndetails;xpath", "//td", verfCols, data("AWBNo"),
					actVerfValues);
			try{
			clickWebElement(sheetName, "btn_SccDropdwn;id", "SCC dropdown", screenName);
			for(int i=0;i<sccs.length;i++){
			String locator = xls_Read.getCellValue(sheetName, "drpdn_scc;xpath").replace("*",sccs[i]);
	        String val=driver.findElement(By.xpath(locator)).getAttribute("aria-selected");
			if(val.equals("true"))
				writeExtent("Pass","Successfully verified " +sccs[i] +" is selected on "+screenName);	
			else
				writeExtent("Fail","Not able to verify " +sccs[i] +" is selected on "+screenName);	
			}
			
			}catch(Exception e){
				writeExtent("Fail","Not able to verify the SCCs selected on "+screenName);
				
			}
			
		}

	/**
	 * @Description : Listing with ULD and Flight Details
	 * @author A-9175
	 * @param uldno
	 * @param FlightNoStationCode
	 * @param FlightNumber
	 * @param FlightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlightAndULD(String uldno,String FlightNoStationCode, String FlightNumber, String FlightDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(FlightNoStationCode), "Flight carrierCode",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", data(FlightDate), "Flight Date", screenName);
		keyPress("TAB");
		enterValueInTextbox(sheetName, "inbx_uldNumber;name", data(uldno), "ULD Number",
				screenName);
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		Thread.sleep(3000);
	}
	/**
	 * @Description Enter the Breakdown Location, Received Pieces, Received Weight 
	 * @author A-10330
	 * @param BDNLocation
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterBreakdownDetails(String BDNLocation, String rcvdPcs, String rcvdWt)throws InterruptedException, IOException {
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_locCode;name", data(BDNLocation), "BDN location", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdPcs;name", data(rcvdPcs), "Received Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdWgt;name", data(rcvdWt), "Received Weight", screenName);
		waitForSync(2);
		
	}
	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : save details on OPR004
	 */
public void saveOPR004Alert() throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "btn_Save;name", "Save Button", screenName);
		waitForSync(2);
		switchToFrame("default");
		try {
			clickWebElement("Generic_Elements", "btn_dialogYesBtn;xpath", "Yes Button", screenName);
		}
		finally {
			switchToFrame("contentFrame", "OPR004");
		}
		
		
	}
	/**
	 * @Description :  Verifying Split SCC POP Up details
	 * @author A-9175
	 * @param numberOfShipments
	 * @param pieces
	 * @param weight
	 * @param scc
	 * @throws Exception
	 */
	public void verifySplitShipmentInfo(int numberOfShipments, String[] pieces,String[] weight,String scc[]) throws Exception {
		
		clickWebElement(sheetName, "btn_splitBreakdownIcon;xpath", "Split Breakdown Icon", screenName);
		waitForSync(5);
		switchToWindow("storeParent");
		waitForSync(3);
		switchToWindow("child");
		for (int i = 0; i < numberOfShipments; i++) {
			// Verifying Pieces
			try {
				String locator = xls_Read.getCellValue(sheetName, "inbx_indexPcs;xpath");
				locator = locator.replace("val", Integer.toString(i));
				String pcsVal=driver.findElement(By.xpath(locator)).getAttribute("value");
				if(pieces[i].equals(pcsVal))
				writeExtent("Pass", "Sucessfully Verified Split Pieces  " + pieces[i] + " in " + screenName);
				else
				writeExtent("Fail", "Not Verified Split Pieces  " + pieces[i] + " in " + screenName);	
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't Verify Split Pieces " + pieces[i] + " in " + screenName);
			}

			// Verify Weight
			try {
				String locator = xls_Read.getCellValue(sheetName, "inbx_indexWgt;xpath");
				locator = locator.replace("val", Integer.toString(i));
				String wgtVal=driver.findElement(By.xpath(locator)).getAttribute("value");
				if(weight[i].equals(wgtVal))
				writeExtent("Pass", "Sucessfully Verified Split Weight  " + weight[i] + " in " + screenName);
				else
				writeExtent("Fail", "Not Verified Split Weight  " + weight[i] + " in " + screenName);	
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't Verify Split Weight " + weight[i] + " in " + screenName);
			}

			// Verify SCC
			try {
				String locator = xls_Read.getCellValue(sheetName, "lbl_scc;xpath");
				locator = locator.replace("val", Integer.toString(i));
				String sccVal=driver.findElement(By.xpath(locator)).getAttribute("value");
				if(scc[i].equals(sccVal))
				writeExtent("Pass", "Sucessfully Verified Split SCC  " + scc[i] + " in " + screenName);
				else
				writeExtent("Fail", "Not Verified Split SCC  " + scc[i] + " in " + screenName);	
			} catch (Exception e) {
				writeExtent("Fail", "Couldn't Verify Split SCC " + scc[i] + " in " + screenName);
			}
		}
		clickWebElement(sheetName, "btn_splitClose;id", "Close", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		switchToFrame("contentFrame", "OPR004");

	}


	/**
	 * @author A-7271
	 * @param numberOfSplits
	 * @param pieces
	 * @param weight
	 * @param location
	 * @throws Exception
	 * Desc : capture split breakdown info
	 */
	public void captureSplitBreakdownInfo(String numberOfSplits, String[] pieces, String[] weight, String[] location)
			throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitBreakdownIcon;xpath", "Split Breakdown Icon", screenName);
		waitForSync(5);
		switchToWindow("child");
		
		int n = Integer.parseInt(numberOfSplits);
		keyPress("TAB");
		waitForSync(3);
		
		int k = 0;
		for (int i = 1; i <= n; i++) {
			String y = Integer.toString(i);

			// Enter pieces
			String splitPieces = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
			splitPieces = splitPieces.replace("rowno", y);
			driver.findElement(By.xpath(splitPieces)).clear();
			driver.findElement(By.xpath(splitPieces)).sendKeys(pieces[k]);

			// Enter weight
			String splitWeight = xls_Read.getCellValue(sheetName, "inbx_splitWeight;xpath");
			splitWeight = splitWeight.replace("rowno", y);
			driver.findElement(By.xpath(splitWeight)).clear();
			driver.findElement(By.xpath(splitWeight)).sendKeys(weight[k]);

			// Enter location
			String splitLocation = xls_Read.getCellValue(sheetName, "inbx_splitLocation;xpath");
			splitLocation = splitLocation.replace("rowno", y);
			driver.findElement(By.xpath(splitLocation)).sendKeys(location[k]);
			k++;
			
		}
		clickWebElement(sheetName, "btn_Ok;name", "Ok Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		

	}
	/**
	 * @author A-9175
	 * @Description : Click Yes on ALert
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void clickYesAlert() throws InterruptedException, AWTException {
		waitForSync(5);
		switchToFrame("default");
		try {
			while (driver.findElement(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath")))
					.isDisplayed()) {
				handleAlert("Accept", screenName);
				waitForSync(5);
			}
		} catch (Exception e) {
		}

		finally {
			switchToFrame("contentFrame", "OPR004");
		}
	}
	

	

	/**
	 * @author A-8783
	 * Description... Capture breakdown details from Warehouse location code	 * 
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void addWarehouseLocation(String breakDownLoc) throws InterruptedException, IOException {
		try{

			String locator = xls_Read.getCellValue(sheetName, "inbx_whLocation;name");

			driver.findElement(By.id(locator)).sendKeys(data(breakDownLoc));
			clickWebElement(sheetName, "btn_AddToLocation;name", "Breakdown details added", screenName);
			waitForSync(8);
			writeExtent("Pass", "Entered breakdown location in "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't enter breakdown location in "+screenName);
		}


	}
	/**
	 * @author A-9175
	 * @Description : Verifying No Bulk/ULD  in Manifested Popup
	 */
	
	public void verifyNoBulkULDManifestedPopUp() {
		handleAlert("getText", screenName);
		String actualAlertText = WebFunctions.getPropertyValue(proppath, "AlertText");

		String expectederror = "ULD/BULK is not manifested, Do you want to continue?";

		if ((actualAlertText).contains(expectederror)) {
			verifyScreenText(sheetName, expectederror, actualAlertText, "No BULK/ ULD is manifested", screenName);
			handleAlert("Accept", screenName);

		} else {
			verifyScreenText(sheetName, actualAlertText, expectederror, "BULK/ ULD is manifested", screenName);
		}
	}

	/**
	 * @author A-9175
	 * @Description : Verifying Damage Captured
	 * @param dmgCaptured
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyDamageCaptured(String dmgCaptured) throws InterruptedException, IOException {

		String actText = driver.findElement(By.xpath("//table[@id='breakDownMaintable']//tr//td[13]/b")).getText();
		System.out.println("Actual text is--" + actText);
		customFuction.verifyScreenText(sheetName, dmgCaptured, actText, "Damage Captured", screenName);
	}

	/**
	 * @author A-6260 
	 * @Description: capture damage
	 * @param DamageDiscrepancyCode
	 * @param DamagePcs
	 * @throws Exception
	 */
	public void captureDamage(String DamageDiscrepancyCode, String DamagePcs) throws Exception {
		waitForSync(2);
		screenName = "Breakdown";
		clickWebElement(sheetName, "chk_uldNo;xpath", "awbno Check Box", screenName);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_captureDamage;name", "Capture Damage Button", screenName);
		switchToWindow("child");
		screenName = "Add/Update Damage Discrepency Pop up";
		enterValueInTextbox(sheetName, "inbx_damageDiscrepancyCode;name", data(DamageDiscrepancyCode),
				"Damage Discrepancy Code", screenName);
		enterValueInTextbox(sheetName, "inbx_damagePcs;name", data(DamagePcs), "Damage Pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_discDate;id", createDateFormat("dd-MMM-YYYY", -1, "DAY", ""), "Damage Pcs",
				screenName);
		keyPress("TAB");
		waitForSync(3);
		keyRelease("TAB");
		waitForSync(3);
		clickWebElement(sheetName, "btn_childWinOk;name", "Ok Button", screenName);
		screenName = "Breakdown";
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR367");
	}

/**
	 * @Desc To verify whether the Received Pieces and Weight Autopopulated
	 */
	
	public void piecesWeightIfAutoPopulated(int rowcount,String pcs[], String wgt[])
	{
	
		for(int i=0;i<rowcount;i++){

			//Verifying pieces autopopulated
			try{
				String locator = xls_Read.getCellValue(sheetName, "txt_rcvdpieces;xpath");
				locator = locator.replace("*", Integer.toString(i+1));
				String pcsVal=driver.findElement(By.xpath(locator)).getAttribute("value");
				verifyScreenTextWithExactMatch(sheetName, data(pcs[i]), pcsVal, "Rcvd Pieces Autopopulated","Rcvd Pieces Autopopulated");
			}catch(Exception e){
				writeExtent("Fail","Not able to verify the received pieces autopopulated on "+screenName);
			}

			//Verifying weight autopopulated
			try{
				String locator1 = xls_Read.getCellValue(sheetName, "txt_rcvdwgt;xpath");
				locator1 = locator1.replace("*", Integer.toString(i+1));
				String wgtVal=driver.findElement(By.xpath(locator1)).getAttribute("value");
				verifyScreenTextWithExactMatch(sheetName, data(wgt[i]), wgtVal, "Rcvd Weight Autopopulated","Rcvd Weight Autopopulated");
			}catch(Exception e){
				writeExtent("Fail","Not able to verify the received weight autopopulated on "+screenName);
			}
		
		
	}

}

	/**
	 * @Description Verifying Flight with Shipment
	 * @author A-9175
	 * @param shipment
	 * @param FlightNoStationCode
	 * @param FlightNumber
	 * @param FlightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlightwithShipment(String shipment, String FlightNoStationCode, String FlightNumber,
			String FlightDate) throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_ULDnum;id", shipment, "ULD Number", screenName);
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(FlightNoStationCode), "Flight carrierCode",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", data(FlightDate), "Flight Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		Thread.sleep(3000);
	}

	/**
	 * @Description... Verify thru checkbox is present
	 * @author A-9175
	 * @throws InterruptedException
	 */
	public void verifyThruCheckbox() throws InterruptedException {

		String locator = xls_Read.getCellValue(sheetName, "chbx_verifyThrucheckbox;xpath");
		try
		{
			if (driver.findElement(By.xpath(locator)).getAttribute("checked").equals("true")) {
				writeExtent("Pass", "Thru checkbox is checked in " + screenName + " Page");
			} else {
				writeExtent("Fail", "Thru checkbox is not checked in " + screenName + " Page");
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Thru checkbox is not checked in " + screenName + " Page");
		}
	}

	/**
	 * @author A-9175
	 * @Description : Click Capture AWB
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void clickCaptureAWB() throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		clickWebElement(sheetName, "btn_captureAWB;id", "Capture AWB", screenName);
		waitForSync(5);
	}

	/**
	 * @Description Enter the Breakdown Location, Received Pieces, Received Weight and click on Save Button
	 * @author A-9175
	 * @param BDNLocation
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterBdnDetails(String BDNLocation, String rcvdPcs, String rcvdWt)throws InterruptedException, IOException {
		waitForSync(2);
		screenName = "Breakdown";
		enterValueInTextbox(sheetName, "inbx_locCode;name", data(BDNLocation), "BDN location", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdPcs;name", data(rcvdPcs), "Received Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdWgt;name", data(rcvdWt), "Received Weight", screenName);
		clickWebElement(sheetName, "btn_Save;name", "Save Button", screenName);
		handleAlert("Accept", screenName);
		switchToFrame("contentFrame", "OPR004");
	}

	/**
	 * @author A-9175 
	 * @Description : Clicking Capture Check sheet
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCaptureCheckSheet() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_captureChecksheet;id", "Capture Check sheet", screenName);
		waitForSync(5);
	}

	/**
	 * @author A-9175 
	 * @Description : Verify Check sheet Captured
	 * @throws InterruptedException
	 */
	public void verifyChecksheetCaptured() throws InterruptedException {
		try {
			switchToFrame("frameName", "popupContainerFrame");
			String locator = xls_Read.getCellValue(sheetName, "txt_verifychecksheetValues;xpath");
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			for (WebElement elemnt : elements)

			{
				elemnt.getText().equals("Yes");
				waitForSync(2);
			}
			writeExtent("Pass", "Check sheet details  captured" + " on " + screenName + " Page");
		} catch (Exception e) {

			writeExtent("Fail", "Check sheet details not captured" + " on " + screenName + " Page");
		}

		switchToFrame("default");
		switchToFrame("contentFrame", "OPR367");

	}

	/**
	 * @Description : Capture check sheet in a Generic Way
	 * @author A-9175
	 * @throws Exception
	 */

	public void checksheetCapture() throws Exception {

		switchToWindow("storeParent");

		waitForSync(3);
		try {
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR367");
			driver.switchTo().frame("popupContainerFrame");
			String locator = xls_Read.getCellValue(sheetName, "btn_Yesbutton;xpath");
			List<WebElement> elements = driver.findElements(By.xpath(locator));
			for (WebElement elemnt : elements) {
				elemnt.click();
				waitForSync(3);
			}
			clickWebElement("BreakDown_OPR004", "btn_Save;id", "Ok Button", screenName);
			waitForSync(5);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath", "OK Button", screenName);
			switchToFrame("contentFrame", "OPR367");
			driver.switchTo().frame("popupContainerFrame");
			clickWebElementByWebDriver("BreakDown_OPR004", "btnCloseChecksheet;id", "Close button", screenName);
			waitForSync(5);
		}
		finally {
			waitForSync(2);

			switchToWindow("getParent");
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR367");
			waitForSync(5);
		}
	}

	/**
	 * @Description... Enter Received Pieces, Received Weight and click on Save Button
	 * @author A-9175
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterBdnPcsandWt(String rcvdPcs, String rcvdWt) throws InterruptedException, IOException {
		waitForSync(2);
		screenName = "Breakdown";
		switchToFrame("frameLocator", "ImportManifest_OPR014");
		enterValueInTextbox(sheetName, "inbx_rcvdPcs;name", rcvdPcs, "Received Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdWgt;name", rcvdWt, "Received Weight", screenName);
		clickWebElement(sheetName, "btn_Save;name", "Save Button", screenName);
		handleAlert("Accept", screenName);
		switchToFrame("contentFrame", "OPR014");

	}

	/**
	 * @Description... Enter the Breakdown details and capture Discrepancy
	 * @author A-9478
	 * @param BDNLocation
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @param DamageDiscrepancyCode
	 * @param DamagePcs
	 * @throws Exception
	 */
	public void enterBdnDetailsCaptureDamageFromOPR367(String BDNLocation, String rcvdPcs, String rcvdWt,
			String DamageDiscrepancyCode, String DamagePcs) throws Exception {
		waitForSync(2);
		screenName = "Breakdown";
		enterValueInTextbox(sheetName, "bdn_LocationDetails;xpath", data(BDNLocation), "BDN location", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdPcs;name", data(rcvdPcs), "Received Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdWgt;name", data(rcvdWt), "Received Weight", screenName);
		clickWebElement(sheetName, "chk_uldNo;xpath", "awbno Check Box", screenName);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_captureDamage;name", "Capture Damage Button", screenName);
		switchToWindow("child");
		screenName = "Add/Update Damage Discrepency Pop up";
		enterValueInTextbox(sheetName, "inbx_damageDiscrepancyCode;name", DamageDiscrepancyCode,
				"Damage Discrepancy Code", screenName);
		enterValueInTextbox(sheetName, "inbx_damagePcs;name", data(DamagePcs), "Damage Pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_discDate;id", "-1", "Damage Pcs", screenName);
		keyPress("TAB");
		waitForSync(3);
		keyRelease("TAB");
		waitForSync(3);
		clickWebElement(sheetName, "btn_childWinOk;name", "Ok Button", screenName);
		screenName = "Breakdown";
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR367");
		clickWebElement(sheetName, "btn_Save;name", "Save Button", screenName);
		waitForSync(5);
		handleAlert("Accept", screenName);
		switchToFrame("contentFrame", "OPR367");
	}

	/**
	 * @Description... Enter the Breakdown details and capture Discrepancy *
	 * @author A-9175
	 * @param BDNLocation
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @param DamageDiscrepancyCode
	 * @param DamagePcs
	 * @throws Exception
	 */
	public void enterBdnDetailsCaptureDamage(String BDNLocation, String rcvdPcs, String rcvdWt,
			String DamageDiscrepancyCode, String DamagePcs) throws Exception {
		waitForSync(2);
		screenName = "Breakdown";
		switchToFrame("contentFrame", "OPR014");
		enterValueInTextbox(sheetName, "bdn_LocationDetails;xpath", BDNLocation, "BDN location", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdPcs;name", rcvdPcs, "Received Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdWgt;name", rcvdWt, "Received Weight", screenName);
		clickWebElement(sheetName, "chk_uldNo;xpath", "awbno Check Box", screenName);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_captureDamage;name", "Capture Damage Button", screenName);
		switchToWindow("child");
		screenName = "Add/Update Damage Discrepency Pop up";
		enterValueInTextbox(sheetName, "inbx_damageDiscrepancyCode;name", DamageDiscrepancyCode,
				"Damage Discrepancy Code", screenName);
		enterValueInTextbox(sheetName, "inbx_damagePcs;name", DamagePcs, "Damage Pcs", screenName);
		enterValueInTextbox(sheetName, "inbx_discDate;id", "-1", "Damage Pcs", screenName);
		keyPress("TAB");
		waitForSync(3);
		keyRelease("TAB");
		waitForSync(3);
		clickWebElement(sheetName, "btn_childWinOk;name", "Ok Button", screenName);
		screenName = "Breakdown";
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR014");
		clickWebElement(sheetName, "btn_Save;name", "Save Button", screenName);
		waitForSync(5);
		handleAlert("Accept", screenName);
		switchToFrame("contentFrame", "OPR014");
		clickCloseButton();
		switchToFrame("contentFrame", "OPR014");

	}

	/**
	 * @Description... Click on Close Button 
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCloseButton() throws InterruptedException, IOException {
		clickWebElement(sheetName, "butn_close;name", "Close Button", screenName);
		handleAlert("Accept", screenName);

	}

	/**
	 * @Description... Click split breakdown icon and enter details
	 * @author A-9175
	 * @throws@ Exception
	 */

	public void splitBreakdown(String numberOfSplits, String[] pieces, String[] weight, String[] location)
			throws Exception {
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_splitBreakdownIcon;xpath", "Split Breakdown Icon", screenName);
		waitForSync(2);
		switchToWindow("child");
		enterValueInTextbox(sheetName, "inbx_noofSplit;id", numberOfSplits, "Number of split", screenName);
		keyPress("TAB");
		waitForSync(2);
		int n = Integer.parseInt(numberOfSplits);
		int k = 0;
		for (int i = 1; i <= n; i++) {
			String y = Integer.toString(i);

			// Enter pieces
			String splitPieces = xls_Read.getCellValue(sheetName, "inbx_splitPieces;xpath");
			splitPieces = splitPieces.replace("rowno", y);
			driver.findElement(By.xpath(splitPieces)).sendKeys(pieces[k]);

			// Enter weight
			String splitWeight = xls_Read.getCellValue(sheetName, "inbx_splitWeight;xpath");
			splitWeight = splitWeight.replace("rowno", y);
			driver.findElement(By.xpath(splitWeight)).clear();
			driver.findElement(By.xpath(splitWeight)).sendKeys(weight[k]);

			// Enter location
			String splitLocation = xls_Read.getCellValue(sheetName, "inbx_splitLocation;xpath");
			splitLocation = splitLocation.replace("rowno", y);
			driver.findElement(By.xpath(splitLocation)).sendKeys(location[k]);
			k++;
			
		}
		clickWebElement(sheetName, "btn_Ok;name", "Ok Button", screenName);
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame", "OPR004");

	}

	/**
	 * @Description... click on Save Button
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveOPR004() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_Save;name", "Save Button", screenName);
	}

	/**
	 * @Description... Enter the ULD Number 
	 * @author A-9175
	 * @param ULDBumber
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterULDnumber(String ULDBumber) throws InterruptedException, AWTException {

		enterValueInTextbox(sheetName, "uld_Number;xpath", ULDBumber, "ULD number", screenName);
		Thread.sleep(2000);

	}

	/**
	 * @Description... Verify Intact check box is checked
	 * @author A-9175
	 * @throws InterruptedException
	 */
	public void verifyIntactCheckbox() throws InterruptedException {

		String locator = xls_Read.getCellValue(sheetName, "chbx_verifyThrucheckbox;xpath");
		locator = locator.replace("thru", "inTact");
		try
		{
			if (driver.findElement(By.xpath(locator)).getAttribute("checked").equals("true")) {
				writeExtent("Pass", "Intact checkbox is checked in " + screenName + " Page");
			} else {
				writeExtent("Fail", "Intact checkbox is not checked in " + screenName + " Page");
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Intact checkbox is not checked in " + screenName + " Page");
		}
				
				
	}
	/**
	 * @author A-7271
	 * Desc : verify if breakdown location autopulated
	 */
	public void breakdownLocIfAutoPopulated()
	{
		
		String locator=xls_Read.getCellValue(sheetName, "inbx_locCode;name");
		
		
		boolean locAutoPopulated=true;
		List<WebElement> breakDownLoc=driver.findElements(By.name(locator));
		
		for(WebElement loc:breakDownLoc)
		{
			
			if(loc.getAttribute("value").equals(""))
			{
				locAutoPopulated=false;
				break;
			}
		}
		
		if(locAutoPopulated)
		{
			writeExtent("Pass","Breakdown location autopulated as on "+screenName);
		}
		else
		{
			writeExtent("Fail","Breakdown location not autopulated as on "+screenName);
		}
	}
	/**
	 * @author A-7271
	 * Verify if the add button is disabled
	 */
	public void verifyAddButtonIfDisabled()
	{
		try
		{
			String locator = xls_Read.getCellValue(sheetName, "btn_Add;xpath");
			
		if(driver.findElement(By.xpath(locator)).getAttribute("disabled").equals("true"))
		{
			writeExtent("Pass","Add button is disabled on "+screenName);
		}
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Add button is enabled on "+screenName);
		}
	}
	/**
	 * @Description... List Flight 
	 * @author A-9175
	 * @param FlightNoStationCode
	 * @param FlightNumber
	 * @param FlightDate
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void listFlight(String FlightNoStationCode, String FlightNumber, String FlightDate)
			throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(FlightNoStationCode), "Flight carrierCode",
				screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name", data(FlightNumber), "Flight No", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", data(FlightDate), "Flight Date", screenName);
		keyPress("TAB");
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		Thread.sleep(3000);
	}

	
	/**
	 * @Description... Enter Breakdown Location 
	 * @author A-9175
	 * @param BDNLocation
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterBdnLocationDetails1(String BDNLocation) throws InterruptedException, AWTException {

		waitForSync(2);

		enterValueInTextbox(sheetName, "bdn_LocationDetails;xpath", data(BDNLocation), "BDN location", screenName);
		performKeyActions(sheetName, "bdn_LocationDetails;xpath", "TAB", "BDN location", screenName);
		Thread.sleep(2000);
	}
	
	/**
	 * @Description... switch Frame and Enter Breakdown Location 
	 * @author A-9175
	 * @param BDNLocation
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterBdnLocationDetails2(String BDNLocation) throws InterruptedException, AWTException {

		waitForSync(2);
		switchToFrame("frameLocator", "ImportManifest_OPR014");
		enterValueInTextbox(sheetName, "bdn_LocationDetails;xpath", data(BDNLocation), "BDN location", screenName);
		performKeyActions(sheetName, "bdn_LocationDetails;xpath", "TAB", "BDN location", screenName);
		Thread.sleep(2000);
	}

	/**
	 * @Description... switch Frame and Enter Breakdown Location *
	 * @author A-9175
	 * @param BDNLocation
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterBdnLocationDetails_multipleShipments(String BDNLocation)
			throws InterruptedException, AWTException {
		waitForSync(2);
		switchToFrame("frameLocator", "ImportManifest_OPR014");
		enterValueInTextbox(sheetName, "inbx_locCode2;id", data(BDNLocation), "BDN location 2nd AWB", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
		enterValueInTextbox(sheetName, "inbx_locCode3;id", data(BDNLocation), "BDN location 3rd AWB", screenName);
		keyPress("TAB");
		keyRelease("TAB");
		Thread.sleep(2000);
	}

	/**
	 * @Description... Click ULD CheckBox
	 * @author A-9175
	 * @param value
	 * @throws InterruptedException
	 */
	
	public void clickCheckBox(String value) throws InterruptedException {
		selectTableRecord(value, "uld_CheckBox;xpath", sheetName, 3);
		Thread.sleep(2000);
	}

	/**
	 * @Description... Click Breakdown Complete Button 
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void clickBreakdownComplete() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_BreakDownComplete;name", "BreakDown Complete", screenName);
		waitForSync(8);
	}
	
	/**
	 * @author A-6260
	 * @Desc: verify breakdown details for multiple awbs
	 * @param verfCols
	 * @param actVerfValues
	 * @param awb
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyBreakdownDetailsForMultipleAwbs(int verfCols[], String actVerfValues[],String awb[]) throws InterruptedException, IOException {
		for (int i=0; i<awb.length;i++) {
			verify_tbl_records_multiple_cols(sheetName, "tbl_breakdowndetails;xpath", "//td", verfCols, awb[i],
					actVerfValues);
		}

	}

	/**
	 * @Description... Verify Breakdown Details 
	 * @author A-9175
	 * @param verfCols
	 * @param actVerfValues
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void verifyBreakdownDetails(int verfCols[], String actVerfValues[])
			throws InterruptedException, IOException {
		verify_tbl_records_multiple_cols(sheetName, "tbl_breakdowndetails;xpath", "//td", verfCols, data("AWBNo"),
				actVerfValues);

	}

	/**
	 * @Description... Verify Breakdown Details 
	 * @author A-9175
	 * @param verfCols
	 * @param actVerfValues
	 * @throws InterruptedException
	 */
	
	public void verifyBreakdownDetails1(int verfCols[], String actVerfValues[]) throws InterruptedException {

		verify_tbl_records_multiple_cols_RampHandle(sheetName, "tbl_breakdowndetails;xpath", verfCols, data("AWBNo"),
				actVerfValues);

	}

	/**
	 * @Description... Click Breakdown Close Button 
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void closeBreakdownScreen() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_close;name", "BreakDown Close", screenName);
		waitForSync(4);

	}

	/**
	 * @Description... Verify ULD displayed is a Thru Unit ULD 
	 * @author A-9175
	 * @throws InterruptedException
	 */
	public void verifyThruUnit() throws InterruptedException {
		try {
			switchToFrame("contentFrame", "OPR014");
			ele = findDynamicXpathElement("chkbox_thru;xpath", sheetName, "Thru Checkbox", screenName);
			waitForSync(1);
			String actText = ele.getAttribute("value");
			waitForSync(1);
			String expText = "on";
			actText.equals(expText);
			System.out.println("ULD displayed is a Thru Unit ULD");

		} catch (Exception e) {
			System.out.println("ULD displayed is not a Thru Unit ULD");
		}
	}

	/**
	 * @Description... Enter Manifested Pieces Weight 
	 * @author A-9175
	 * @param rcvdPcs
	 * @param rcvdWt
	 * @throws Exception
	 */
	public void enterManifestedPcsWeight(String rcvdPcs, String rcvdWt) throws Exception {
		enterValueInTextbox(sheetName, "inbx_rcvdPcs;name", rcvdPcs, "Received Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_rcvdWgt;name", rcvdWt, "Received Weight", screenName);
		clickWebElement(sheetName, "btn_Save;name", "Save Button", screenName);
	}

	/**
	 * @Description... Verify Error Popup 
	 * @author A-9175
	 * @throws Exception
	 */
	public void verifyErrorPopup() throws Exception {
		handleAlert("getText", "Handling Area Set Up / Screen");
		String actualAlertText = WebFunctions.getPropertyValue(proppath, "AlertText");

		String expectederror = "Total Received Pcs/Wt is greater than the stated Pcs/Wt";

		if ((actualAlertText).contains(expectederror)) {
			verifyScreenText(sheetName, expectederror, actualAlertText, "Rcvd Pcs>Stated Pcs", screenName);

		} else {
			verifyScreenText(sheetName, actualAlertText, expectederror, "Rcvd Pcs>Stated Pcs", screenName);

		}
	}

}
