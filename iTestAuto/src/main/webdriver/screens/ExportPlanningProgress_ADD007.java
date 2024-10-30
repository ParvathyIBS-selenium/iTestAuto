package screens;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ExportPlanningProgress_ADD007 extends CustomFunctions {
	private static final String TAB = null;
	private static final String Enter = null;
	public CustomFunctions customFuction;
	String sheetName = "ExportPlanningProgress_ADD007";
	String screenName = "Export Planning Progress";
	String screenId = "ADD007";

	public ExportPlanningProgress_ADD007(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}


	
	/**
	 * @author A-9175
	 * Desription : List the flight details
	 * @param carrierCode
	 * @param flightNumber
	 * @param fromDate
	 * @param toDate
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void EnterFlightDetails(String carrierCode,String flightNumber,String fromDate,String toDate) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;name",data(carrierCode), "carrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name",data(flightNumber), "flightNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_fromdate;name",data(fromDate), "From date", screenName);
		enterValueInTextbox(sheetName, "inbx_todate;name",data(toDate), "To Date", screenName);
		
	}
	/**
     * @author A-8783
     * Desc - 
     * @param columnName
     * @param progress
     */
    public void verifyProgressPercentage(String columnName,String progress){
    	 String locator = xls_Read.getCellValue(sheetName, "txt_statusProgress;xpath");
         locator=locator.replace("*", data(columnName));
        String actProgress = driver.findElement(By.xpath(locator)).getText();
    	verifyScreenTextWithExactMatch(sheetName, progress, actProgress, "Verify Progress", screenName); 
    	}

	/**
	 * @author A-8783
	 * Desc - Verify excluded departed flights checkbox is present
	 * @throws InterruptedException
	 */
	public void verifyExcludeDepartedFlightCheckbox() throws InterruptedException {
		verifyElementDisplayed(sheetName,"chk_excludeDptdFlt;name", "Verify checkbox", screenName, "Exclude Departed Flight Checkbox");
	}
	/**
	 * @author A-8783
	 * Desc - verify buildup closure time is displayed as
	 *         DD-MMM-YYYY, HH:MM or is empty based on expected text given
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyBuildupClosureTime(String expText) throws InterruptedException, IOException {
		try {
			waitForSync(2);
			String locator = xls_Read.getCellValue(sheetName, "txt_buidlupClosure;xpath");
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);
			if (expText.equals("isEmpty")) {
				 if((driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_buildupToolTip;xpath"))).size()==0)){
				       writeExtent("Pass","Successfully verified the Buildup closure time is empty on "+screenName);
				         }
				 else{
					 String toolTipText = driver
								.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_buildupToolTip;xpath"))).getText();
					 writeExtent("Fail", "Buildup Closure time is not empty on "+screenName+" and is displayed as "+toolTipText);
				 }
			} else {
				String actText = driver
						.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_buildupToolTip;xpath"))).getText();
				waitForSync(1);
				System.out.println(actText);
				if (actText.equals(expText)) {
					writeExtent("Pass", "Verified the buildup closure time " + expText + " on " + screenName);
				}

				else {
					writeExtent("Fail", "Could not verify the buildup closure time " + expText + " on " + screenName);
				}
			}

		}

		catch (Exception e) {
			writeExtent("Fail", "Failed to verify the buildup closure time checks on " + screenName);

		}
	}
	/**
     * @author A-8783
     * Description...verify Column name
     * @throws Exception
     * @throws InterruptedException
     */
    public void verifyColumn(String[] columnName) throws InterruptedException, Exception{
        int i = 0;
        int flag=0;
        try {
        
         for( i=0;i<columnName.length;i++){
            flag=0;
            String locator=xls_Read.getCellValue(sheetName,"txt_col;xpath");
            locator=locator.replace("*", columnName[i]);
            List<WebElement> column = driver.findElements(By.xpath(locator));
             for(WebElement col:column) {
             String actText = col.getText();
             System.out.println(actText);
             System.out.println(columnName[i]);
             if(actText.equals(columnName[i])) {
                
                 writeExtent("Pass", "Verified that the column " + columnName[i] + " is present in the table");
                 break;
             }
             else {
                 flag+=1;
             }
                 
         }
             if(flag==column.size()) {
                 writeExtent("Fail", "Failed to verify that the column " + columnName[i] + " is present in the table");



            }
    }
                
        }
        catch(Exception e) {
             writeExtent("Fail", "Failed to verify if columns are present");
        }
    }
    /**
	 * @author A-9844
	 * Desc - verify manifest completion time is displayed as DD-MMM-YYYY, HH:MM
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickManifestStatusIconAndVerifyTooltip(String expText) throws InterruptedException, IOException
	{
		try{

			waitForSync(2);		
			String locator = xls_Read.getCellValue(sheetName, "img_manifestTick;xpath");		
			System.out.println(locator);
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);

			String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_manifestToolTip;xpath"))).getText();
			waitForSync(1);
			System.out.println(actText);
			if(actText.equals(expText)){
				writeExtent("Pass","Verified the manifest completion time "+expText +" on "+screenName);
			}

			else{
				writeExtent("Fail","Could not verify the manifest completion time "+expText +" on "+screenName);
			}
		}

		catch(Exception e){

			writeExtent("Fail","Failed to verify the manifest time checks on "+screenName);	

		}
	}

	/**@author A-10328
	 * Description - To verify the visit declaration widget 
     * @throws Exception
	 */
	
	
public void verifyVisitDeclarationWidget() throws Exception


{
	switchToFrame("frameName","popupContainerFrame");
	/**Visit Declaration Widget **/

	String locator = xls_Read.getCellValue(sheetName, "div_visitdeclarationdetailslink;xpath");

	if((driver.findElements(By.xpath(locator)).size()==1)){
		clickWebElement(sheetName, "div_visitdeclarationdetailslink;xpath", "visitDeclarationDetails Link",screenName);
	}
	verifyElementDisplayed(sheetName, "txt_visitDecl;xpath", "Visit Declaration", screenName, "Visit Declaration Widget");
	waitForSync(1);

}
/**@author A-10328
 * Description - To verify plus Indicator in the visit declaration widget
 * @throws InterruptedException
 */

public void verifyPlusIndicator() throws InterruptedException
	

{
		
verifyElementDisplayed(sheetName, "btn_plusindicator;xpath", "Plus Indicator", screenName, "Plus Indicator");
waitForSync(1);

}
/**@author A-10328
 * Description - To click plus Indicator in the visit declaration widget
 * @throws InterruptedException
	 * @throws IOException
	 */
	
	

public void clickPlusIndicator() throws InterruptedException, IOException
	

{
	clickWebElement(sheetName, "btn_plusindicator;xpath", "Plus Indicator", screenName);
	waitForSync(5);
		

	}

/* @author A-10330
* @throws Exception
* @Params verfText, tokenDetails,size,tokenNo,servicePoint
*/

public void verifyVisitDeclarationDetails(String tokenNo,String servicePoint) throws Exception{
switchToFrame("frameName","popupContainerFrame");
	/**Visit Declaration Widget **/
String locator = xls_Read.getCellValue(sheetName, "div_visitdeclarationdetailslink;xpath");

if((driver.findElements(By.xpath(locator)).size()==1)){
	clickWebElement(sheetName, "div_visitdeclarationdetailslink;xpath", "visitDeclarationDetails Link",screenName);
	
}


verifyElementDisplayed(sheetName, "txt_visitDecl;xpath", "Visit Declaration", screenName, "Visit Declaration Widget");
/**Token number **/
verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(tokenNo), "Token Number",1, screenName);

verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(servicePoint), "Service Point", 1, screenName);

clickWebElement(sheetName, "ilink_visitdeclarationdetails;xpath", "visitDeclarationDetails Link",screenName);

waitForSync(3);		
}
/**
* Desc: to verify visit declaration details
* @author A-10330
* @Params verfText, tokenDetails,size
*/

public void  verifyTokenDetails(String[] verfText,String[] tokenDetails,int size)
{
	for(int i=0;i<verfText.length;i++)
	{
		try{

			String locator1 = xls_Read.getCellValue(sheetName,"em_visitdeclarationTokendetails;xpath");
			waitForSync(1);
			locator1=locator1.replace("*", verfText[i]);

			int actSize = driver.findElements(By.xpath(locator1)).size();
			if(actSize==size){
				writeExtent("Pass", " successfully Verified "+verfText[i]+" verification point is visit declaration details on "+screenName);
			}
			else{
				writeExtent("Fail", "Failed to verify "+verfText[i]+"verification point is visit declaration details on "+screenName);
			}

		}
		catch(Exception e ){
			writeExtent("Fail", "Failed to verify "+tokenDetails[i]+" on "+screenName);
		} 	
	} 
	switchToFrame("default");
	switchToFrame("contentFrame","ADD007");
}


/**@author A-10328
 * Description - To verify the token number 
 * @param token
 */
public void verifyTokenNumber(String token)

{
try
{
String locator = xls_Read.getCellValue(sheetName, "txt_tokenNumber;xpath");
locator=locator.replace("*", token);
String acttokenNumber = driver.findElement(By.xpath(locator)).getText();
verifyScreenTextWithExactMatch(sheetName, token , acttokenNumber , "Verify Token Number", screenName);
}


catch(Exception e)
{
writeExtent("Fail", "Could not verify token number "+screenName);
}
}
	/**
	 * @author A-8783
	 * Desc - Click on more Details for availbility column
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickAvailableDetails() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_Availability;xpath", "Availability Button",screenName);
	}
	/**
	 * @author A-8783
	 * Desc - Click on Pending Acceptance Tab
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickPendingAcceptance() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_pendingAcceptance;xpath", "Pending Acceptance Tab",screenName);
	}
	/**
	 * @author A-8783
	 * Desc - Click AWB Link
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void clickAwbLink(String awbNo) throws InterruptedException, IOException{
		try{
		String locator = xls_Read.getCellValue(sheetName, "btn_awbNoLink;xpath");
		locator=locator.replace("awb", data(awbNo));
		driver.findElement(By.xpath(locator)).click();
		switchToFrame("frameName","popupContainerFrame");
		waitTillScreenload(sheetName, "btn_searchWidget;xpath", "Search for widget", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame","ADD007");
		writeExtent("Pass", "Successfully clicked on AWB Link on "+screenName);
		}
	catch(Exception e){
		writeExtent("Fail", "Could not click on AWB Link on "+screenName);
	}
		
	}
	/**
	 * @author A-8783
	 * Desc - Verify AWB is displayed in Pending acceptance tab
	 */
	public void verifyAwbPendingAcceptance(String awbNo){
		waitForSync(2);
		String locator = xls_Read.getCellValue(sheetName, "btn_awbNoLink;xpath");
		locator=locator.replace("awb", data(awbNo));
		if(driver.findElements(By.xpath(locator)).size()==1){
			writeExtent("Pass", "Verified that AWB is displayed in the Pending Acceptance tab on "+screenName);
		}
		else{
			writeExtent("Fail", "Could not verify that AWB is displayed in the Pending Acceptance tab on "+screenName);
		}
	}
	/**
	 * @author A-8783
	 * @param tokenNo
	 * @param vehicleNo
	 * @param servicePointMode
	 * @param tokenStatus
	 * @param shpmntNature
	 * @param servicePoint
	 * @throws Exception 
	 */
	public void verifyVisitDeclarationDetails(String tokenNo, String vehicleNo, String servicePointMode, String tokenStatus, String shpmntNature, String servicePoint) throws Exception{
		switchToFrame("frameName","popupContainerFrame");
		/**Visit Declaration Widget **/
		verifyElementDisplayed(sheetName, "txt_visitDecl;xpath", "Visit Declaration", screenName, "Visit Declaration Widget");
		
		/**Token number **/
		verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(tokenNo), "Token Number",1, screenName);
		/**Vehicle number **/
		verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(vehicleNo),"Vehicle No:", 1, screenName);
	
		/**Service Point Mode **/
		verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(servicePointMode),"Service Point Mode", 1, screenName);
		
		/**Token status **/
		verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(tokenStatus),"Token Status", 1, screenName);
		
		/**Shipment Nature**/
		verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(shpmntNature),"Nature of Shipment:", 1, screenName);
		
		/**Service Point**/
		verifyElementSize(sheetName, "txt_tokenDetails;xpath", data(servicePoint), "Service Point", 1, screenName);
		switchToFrame("default");
		switchToFrame("contentFrame","ADD007");

	}

	/**
     * @author A-8783
     * Desc - Verify Element size is the required value after replacing values in the locator
     * @param sheetName
     * @param xpath
     * @param expText
     * @param label
     * @param size
     * @param screenName
     */
     public void verifyElementSize(String sheetName,String xpath, String expText, String label,int size, String screenName) {
            try{
            
            String locator = xls_Read.getCellValue(sheetName, xpath);
            waitForSync(1);
            locator=locator.replace("*", expText);
            locator=locator.replace("labelName", label);
            int actSize = driver.findElements(By.xpath(locator)).size();
            if(actSize==size){
                   writeExtent("Pass", "Verified "+expText+" on "+screenName);
            }
            else{
                   writeExtent("Fail", "Failed to verify "+expText+" on "+screenName);
            }
            
            }
            catch(Exception e ){
                   writeExtent("Fail", "Failed to verify "+expText+" on "+screenName);
            }
     }



/**
	 * @author A-9844
	 * @Description... verify manifest status is blank before doing manifest
	 * @throws InterruptedException
	 */
	public void verifyManifestSatusIsEmpty() throws InterruptedException {

		try
		{
			String locator = xls_Read.getCellValue(sheetName, "htmldiv_manifestStatus;xpath");

			if((driver.findElements(By.id(locator)).size()==0)){

				writeExtent("Pass","Successfully verified the manifest status is empty on "+screenName);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not verify the  manifest status on "+screenName);
		}
	}



/**
	 * @author A-9844
	 * Desc- verify the flight is displayed
	 * @param flightType
	 */
	public void verifyFlightIsDisplayed(String flightNo) {
		try{
			String flightNoText=xls_Read.getCellValue(sheetName, "htmldiv_flightNo;xpath");
			flightNoText=flightNoText.replace("FlightNo",data(flightNo));

			if(driver.findElement(By.xpath(flightNoText)).isDisplayed()){
				waitForSync(3);
				writeExtent("Pass", "Verified the flight number "+data("carrierCode")+data(flightNo)+ " is displayes on " + screenName);
			}
		}catch (Exception e) {
			writeExtent("Fail", "Could not Verify the flight number "+data("carrierCode")+data(flightNo)+ " is displayes on " + screenName);
		}

	}
	/**
	 * @author A-9847
	 * @Desc To verify the given flight Instruction as per CFP/CTP
	 * @param fltInstructions
	 */
	public void verifyFlightInstructions(String fltInstructions[],String source){
		try{

			for(int i=0;i<fltInstructions.length;i++)
			{
				String locatorText=xls_Read.getCellValue(sheetName, "div_fltInstrns;xpath");
				locatorText=locatorText.replace("source",source).replace("*",fltInstructions[i]);	

				if(driver.findElements(By.xpath(locatorText)).size()==1)
					writeExtent("Pass", "Successfully verified flight instruction: " +fltInstructions[i]+" on "+ screenName);	
				else
					writeExtent("Fail", "Failed to verify the flight instruction: " +fltInstructions[i]+" on "+ screenName);

			}
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify flight instructions on " + screenName);
		}
	}


	/**
	 * @author A-8783
	 * Desc - click excluded departed flights checkbox
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickExcludeDepartedFlightCheckbox() throws InterruptedException, IOException
	{
		waitForSync(2);
	clickWebElementByWebDriver(sheetName, "chk_excludeDptdFlt;name", "Exclude Departed Flights Checkbox",screenName);
	waitForSync(3);
	}
	/**
	 * @author A-8783
	 * Desc - Verify Flight with the mentioned date is not present
	 * @param fltDate
	 */
	public void verifyFlightNotPresent(String fltDate){

		try{
		By ele = getElement(sheetName, "txt_flightDate;xpath");
		String actFlightDate = driver.findElement(ele).getText();
		verifyScreenTextNotExists(sheetName, data(fltDate), actFlightDate, "Verify Flight Date not exists", screenName);
		}
		catch(Exception e){

		writeExtent("Fail", "Failed to verify Flight not present on m"+data(fltDate)+" on "+screenName);

		}
		}
		/** @author A-8783
	 * Desc - click edit icon
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickEditIcon() throws InterruptedException, IOException
	{
	
	clickWebElementByWebDriver(sheetName, "btn_editIcon;xpath", "Edit icon",screenName);
	waitForSync(1);
	}
	/**
	 * @author A-9847
	 * @Desc To click on FlightInstructionInfoIcon
	 * @param fltNo
	 */
	public void clickFlightInstructionIcon(String fltNo)
	{
		try{
		String locator = xls_Read.getCellValue(sheetName,"div_flightInstructionIcon;xpath");
		locator=locator.replace("*", data(fltNo));
		driver.findElement(By.xpath(locator)).click();
		writeExtent("Pass", "Clicked on the flight Instruction Info Icon on " + screenName);
		}
		catch(Exception e){
  			writeExtent("Fail", "Failed to click the flight Instruction Info Icon on " + screenName);
  		}
		
	}




/**@author A-9847
	 * @Desc To verify the given flight Instructions
	 * @param fltInstructions
	 */
	public void verifyFlightInstructions(String fltInstructions[]){
		try{

			for(int i=0;i<fltInstructions.length;i++)
			{
				String locatorText=xls_Read.getCellValue(sheetName, "div_flightInstructions;xpath");
				locatorText=locatorText.replace("*", fltInstructions[i]);	
				if(driver.findElements(By.xpath(locatorText)).size()==1)
					writeExtent("Pass", "Successfully verified flight instruction: " +fltInstructions[i]+" on "+ screenName);
				else
					writeExtent("Fail", "Failed to verify the flight instructions:" +fltInstructions[i] +" on " + screenName);

			}
		}

		catch(Exception e){
			writeExtent("Fail", "Failed to verify flight instructions on " + screenName);
		}
	}
	

	

	/** @author A-8783
	 * Desc - click clear button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickClear() throws InterruptedException, IOException
	{
	
	clickWebElementByWebDriver(sheetName, "btn_clear;xpath", "Clear Button",screenName);
	waitForSync(1);
	}

	
	/**
	 * @author A-8783
	 * Desc - Verify continous box is selected
	 */
			
	public void verifyContinousChkBoxChkd() {
		String locator = xls_Read.getCellValue(sheetName, "chk_continuous;xpath");
		if(driver.findElement(By.xpath(locator)).isSelected())
			onPassUpdate(screenName, "Continous checkbox is checked", "Continous checkbox", "Checked", "Checkbox is selected");
		else
			onFailUpdate("Continous checkbox is not selected");
	}
	
	/**
	* @author A-8783
	* Description... To verify Flight Dates are present
	* @param flight
	* @throws InterruptedException
	* @throws IOException
	*/

	public void verifyFlightDatesArePresent(String day, int schedule) {
		int i = 1;
		int noOfDays = schedule;
		List<WebElement> element = new ArrayList<WebElement>();
		List<String> actDates = new ArrayList<String>();
		List<String> expDates = new ArrayList<String>();

		try {

			
			while (noOfDays > 0) {
				String locator = xls_Read.getCellValue(sheetName, "txt_ftDates;xpath");
				locator = locator.replace("k", String.valueOf(i));
				System.out.println(locator);
				element.add(driver.findElement(By.xpath(locator)));
				i += 1;
				noOfDays--;
			}

			for (WebElement ele : element) {
				System.out.println(ele.getText().split("-")[0] + " actDate");
				actDates.add(ele.getText().split("-")[0]);

			}
			
			for(int j=0;j<schedule;j++) {
				
				int incDate = Integer.parseInt(data(day));
				int expDate = incDate+=j;
				System.out.println(String.format("%02d",expDate));
				expDates.add(String.format("%02d",expDate));
			}
			
			if(expDates.equals(actDates)) {
				System.out.println("Pass");
				writeExtent("Pass", "All the scheduled flights are listed in" + screenName);
			}
			else
			{
				System.out.println("Fail");
				writeExtent("Fail", "All the scheduled flights are not listed in" + screenName);
			}
			

		} catch (Exception e) {

			writeExtent("Fail", "Failed to verify the scheduled flights are listed on " + screenName);

		}

	}

	/**
	 * @author A-9844
	 * Desription : List the flight details
	 * @param carrierCode
	 * @param flightNumber
	 * @param fromDate
	 * @param toDate
	 * @param fromTime
	 * @param toTime
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void EnterFlightDetailsWithTime(String carrierCode,String flightNumber,String fromDate,String toDate) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;name",data(carrierCode), "carrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name",data(flightNumber), "flightNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_fromdate;name",data(fromDate), "From date", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_fromtime;xpath","00:00", "From Time", screenName);
		enterValueInTextbox(sheetName, "inbx_todate;name",data(toDate), "To Date", screenName);
		enterValueInTextbox(sheetName, "inbx_totime;xpath","23:59", "To Time", screenName);
		
	}
	/**
	 * @author A-8783
	 * Desc- verify the flight time
	 * @param flightType
	 */
	public void verifyFlightTime(String flightTime, String timeType) {



		By ele = getElement(sheetName, "txt_flightTime;xpath");
		String actFlightTimewithSeconds = driver.findElement(ele).getText();
		String actFlightTime = actFlightTimewithSeconds.split(":")[0]+":"+actFlightTimewithSeconds.split(":")[1];
		System.out.println("expected " + data(flightTime));
		System.out.println("actual " + actFlightTime);
	
		verifyScreenTextWithExactMatch(sheetName,data(flightTime),actFlightTime, "Verify Flight Time", screenName);

		String actTimeType = actFlightTimewithSeconds.split("\\(")[1];
		System.out.println(actTimeType);
		verifyScreenText(sheetName, data(timeType), actTimeType,"Verify Flight Time type", screenName);
		}




/**
	 * @author A-8783
	 * Desc- verify flight time colour
	 * @param Color
	 */
	public void verifyFlightTimeColour(String Color) {
		
		switch (Color) {

		case "Red":
			By ele = getElement(sheetName, "txt_flightColour;xpath");
			String expColour = driver.findElement(ele).getAttribute("style");
			if (expColour.equals("color: rgb(211, 33, 45);"))
				writeExtent("Pass", "Verified flight time colour as " + Color + " on "
						+ screenName + " Page");

			else
				writeExtent("Fail", "Could not verify flight time colour as " + Color + " on "
						+ screenName + " Page");		
			break;
		case "Grey":
			 ele = getElement(sheetName, "txt_flightTime;xpath");
			 expColour = driver.findElement(ele).getAttribute("style");
			if (expColour.equals(""))
				writeExtent("Pass", "Verified flight time colour as " + Color + " on "
						+ screenName + " Page");
			else
				writeExtent("Fail", "Could not verify flight time colour as " + Color + " on "
						+ screenName + " Page");	
			break;
		
		
	}
	}
	/**
	* @author A-9847
	* Description... To verify Flight Date
	* @param flight
	* @throws InterruptedException
	* @throws IOException
	*/

	public void verifyFlightDate(String fltDate){

	try{
	By ele = getElement(sheetName, "txt_flightDate;xpath");
	String expFlightDate = driver.findElement(ele).getText();
	verifyScreenTextWithExactMatch(sheetName, expFlightDate, data(fltDate), "Verify Flight Date", screenName);
	}
	catch(Exception e){

	writeExtent("Fail", "Failed to verify Flight Date as "+data(fltDate)+" on "+screenName);

	}
	}

/**
	 * @author A-9847
	 * Desc - click continous checkbox
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickContinuousCheckbox() throws InterruptedException, IOException
	{
		try{

			waitTillScreenloadWithOutAssertion(sheetName, "chk_continuous;xpath", "Continuous Checkbox", screenName,40);

			String locator = xls_Read.getCellValue(sheetName, "chk_continuous;xpath");
			waitForSync(2);

			if(!driver.findElement(By.xpath(locator)).isSelected())
			{
				clickWebElementByWebDriver(sheetName, "chk_continuous;xpath", "Continuous Checkbox",screenName);
				waitForSync(3);
			}

		}

		catch(Exception e){
			writeExtent("Fail", "Failed to check the continuous checkbox on "+screenName);
		}

	}

/**
 * @author A-9175
 * Description... Click List Button	
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickList() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "btn_List;xpath", "List Button",screenName);
		waitForSync(5);
	}
	
	
	
/**
 * @author A-9175
 * Description... Select flight	
 * @param flight
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectflight() throws InterruptedException, IOException
	{
		waitForSync(5);
		clickWebElement(sheetName, "chk_flight;xpath", "Buildup Button",screenName);
		
		
	}
	/**
	 * @author A-8783
	 * Desc- verify the flight type text colour is red or normal
	 * @param flightType
	 */
	public void verifyFlightType(String flightType) {
		By ele = getElement(sheetName, "txt_flightType;xpath");
		String expFlightType = driver.findElement(ele).getText();
		verifyScreenText(sheetName, expFlightType, data(flightType), "Verify Flight Type", screenName);
	}
	/**
	 * @author A-8783
	 * Desc- Verify previous flight type on mouse hover
	 * @param flightType
	 */
	public void verifyPreviousFlightType(String flightType) {
		
		hover(sheetName, "txt_flightType;xpath");
		String locator = xls_Read.getCellValue(sheetName,"txt_prevFlightType;xpath");
		locator=locator.replace("*", data(flightType));
		List<WebElement> ele=driver.findElements(By.xpath(locator));
		
		if(ele.size()==1)
		      {
			writeExtent("Pass", "Verified flight type  " + data(flightType) + "from tool tip on "
					+ screenName + " Page");

		      }
		      else
		      {
		    	  writeExtent("Fail", "Could not verify flight type colour as " + data(flightType) + "from tool tip on "
							+ screenName + " Page");	
		      }
	}
	/**
	 * @author A-8783
	 * Desc- verify aircraft type colour
	 * @param Color
	 */
	public void verifyColour(String Color) {
		By ele = getElement(sheetName, "txt_flightType;xpath");
		String expColour = driver.findElement(ele).getAttribute("style");
		switch (Color) {

		case "Red":
			
			if (expColour.equals("color: rgb(211, 33, 45);"))
				writeExtent("Pass", "Verified flight type colour as " + Color + " on "
						+ screenName + " Page");

			else
				writeExtent("Fail", "Could not verify flight type colour as " + Color + " on "
						+ screenName + " Page");		
			break;
		case "Grey":
			
			if (expColour.equals(""))
				writeExtent("Pass", "Verified flight type colour as " + Color + " on "
						+ screenName + " Page");
			else
				writeExtent("Fail", "Could not verify flight type colour as " + Color + " on "
						+ screenName + " Page");	
			break;
		
		
	}
	}

	/**
	 * Desc: Clicking on Build up button
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBuildupButton() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_buildupPlanning;xpath", "Buildup Button",screenName);
		waitForSync(5);
	}
	

	
}
