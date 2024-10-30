package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class BuildupPlanning_ADD004 extends CustomFunctions{
	
	public BuildupPlanning_ADD004(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="BuildupPlanning_ADD004";
	public String ScreenName="Buildup Planning";
	String screenID = "ADD004";
	//public CustomFunctions comm;
	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	WebFunctions libr = new WebFunctions(driver, excelreadwrite, xls_Read);
	   public static String haproppath = "\\src\\resources\\HA.properties";
	   public static String toproppath = "\\src\\resources\\TO.properties";
	   
	/**
	 * 
	 * @author A-9478
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... List Flight
	 */

	public void listFlight(String carrierCode, String flightNumber, String flightDate) throws InterruptedException, AWTException {
		try {
			waitTillScreenload(sheetName, "inbx_carrierCode;id","Flight Carrier code", ScreenName);
			enterValueInTextbox(sheetName, "inbx_carrierCode;id", data(carrierCode), "Carrier Code", screenID);
			enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(flightNumber), "Flight Number", screenID);
			enterValueInTextbox(sheetName, "inbx_flightDate;id", data(flightDate), "Flight Date", screenID);
			waitForSync(2);
			performKeyActions(sheetName,"inbx_flightDate;id", "TAB","Flight Date", screenID);
			waitForSync(2);
			clickWebElementByWebDriver(sheetName, "btn_List;xpath", "List Button", screenID);
			waitForSync(6);
		} catch (Exception e) {
			System.out.println("Could not perform list flight operations");
			test.log(LogStatus.FAIL, "Could not perform list flight operations in "+ScreenName);

		}
	}
	  /**
     * @author A-9847
     * @Desc To verify the Planned Pieces and Weight of an AWB
     * @param awb
     * @param pcs
     * @param wgt
     * @param vol
     */
    public void verifyPlannedAwbDetails(String awb,String pcs,String wgt,String vol){
   	 
   	try{
   	
   		String actpcs=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_plannedPcs;xpath").replace("*", data(awb)))).getText();
   		String actwgt=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_plannedWgt;xpath").replace("*", data(awb)))).getText();
   		String actvol=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_plannedVol;xpath").replace("*", data(awb)))).getText();

   		
   		verifyScreenTextWithExactMatch(sheetName,data(pcs), actpcs.replaceAll("[^0-9]", ""), "AWB Planned Pieces", ScreenName);
			verifyScreenTextWithExactMatch(sheetName, data(wgt), actwgt.replaceAll("[^0-9]", ""), "AWB Planned Weight", ScreenName);
			verifyScreenTextWithExactMatch(sheetName,data(vol), actvol.replaceAll("[^0-9]", ""), "AWB Planned Volume", ScreenName);
			
   		
   	}
   	catch(Exception e) {
   		 writeExtent("Fail", "Failed to verify the Planned AWB details on "+screenID);
   		
   	}
   	 
    }
    /**
     * Desc : select Task
     * @author A-9844
     * @throws InterruptedException
     * @throws AWTException
     * @throws IOException 
     */
     public void selectTask(String awb) throws InterruptedException, AWTException, IOException 

     {
   	 
   	  String taskLocator=xls_Read.getCellValue(sheetName, "btn_taskchekbox;xpath");
   	  taskLocator=taskLocator.replace("awb", data(awb));
   	  driver.findElement(By.xpath(taskLocator)).click();
   	  waitForSync(2);

     } 
    /**@author A-10328
     * Description- Enter SCC and ULD Number in the filter 
     * @param SCC
     * @param ULDNo
* @throws InterruptedException
 */

public void enterSCC(String SCC,String ULDNo) throws InterruptedException


{
enterValueInTextbox(sheetName, "inbx_scc;xpath", data(SCC), "Enter SCC", screenID);
enterValueInTextbox(sheetName, "inbx_UldNo;xpath", data(ULDNo), "Enter ULD number", screenID);
waitForSync(1);
}

    /**
     * @author A-9847
     * @Desc To verify the Stated Pieces and Weight of an AWB
     * @param awb
     * @param pcs
     * @param wgt
     * @param vol
     */
    
    public void verifyStatedAwbDetails(String awb,String pcs,String wgt,String vol){
   	 
    	try{
    		
    		String actpcs=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_statedPcs;xpath").replace("*", data(awb)))).getText();
   		String actwgt=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_statedWgt;xpath").replace("*", data(awb)))).getText();
   		String actvol=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "div_statedVol;xpath").replace("*", data(awb)))).getText();
   		
   		
   		verifyScreenTextWithExactMatch(sheetName,data(pcs), actpcs.replaceAll("[^0-9]", ""), "AWB Stated Pieces", ScreenName);
			verifyScreenTextWithExactMatch(sheetName, data(wgt), actwgt.replaceAll("[^0-9]", ""), "AWB Stated Weight", ScreenName);
			verifyScreenTextWithExactMatch(sheetName,data(vol), actvol.replaceAll("[^0-9]", ""), "AWB Stated Volume", ScreenName);
    	
    		
    	}
    	catch(Exception e) {
    		
    		 writeExtent("Fail", "Failed to verify the Stated AWB details on "+screenID);
    	}
    	 
     }
    
    
  /**
   * @author A-9847
   * @Desc To verify AWB Origin and Destination
   * @param awb
   * @param origin
   * @param destination
   */
    public void verifyAwbOriginDestination(String awb,String origin, String destination){
   	 
   		try{
     		
        		int Orgsize=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "div_originDestinationScc;xpath").replace("AWB", data(awb)).replace("*", data(origin)))).size();
       		int Destsize=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "div_originDestinationScc;xpath").replace("AWB", data(awb)).replace("*", data(destination)))).size();
       			
       		System.out.println(Orgsize);
       		System.out.println(Destsize);	
       		
       		if(Orgsize==1)
       			 writeExtent("Pass", "Sucessfully verified AWB Origin as "+data(origin)+" on "+screenID);
       		else
       			 writeExtent("Fail", "Failed to verify AWB Origin as "+data(origin)+" on "+screenID);
       		
       		if(Destsize==1)
      			 writeExtent("Pass", "Sucessfully verified AWB Destination as "+data(destination)+" on "+screenID);
      		else
      			 writeExtent("Fail", "Failed to verify AWB Destination as "+data(destination)+" on "+screenID);
   				
        	}
        	catch(Exception e) {
        		
        		 writeExtent("Fail", "Failed to verify AWB Origin and Destination on "+screenID);
        	}
        	 
         }
        	 
   	 
    /**
     * @author A-9847
     * @Desc To verify the AWB Sccs
     * @param awb
     * @param scc
     */
    public void verifySccs(String awb,String scc[]){
   	 
   	 try{

   		 for(int i=0;i<scc.length;i++)
   		 {
   			 int Sccsize=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "div_originDestinationScc;xpath").replace("AWB", data(awb)).replace("*", scc[i]))).size();	
   			 System.out.println(Sccsize);

   			 if(Sccsize==1)

   				 writeExtent("Pass", "Successfully verified the SCC "+scc[i]+" on "+screenID);

   			 else

   			 {
   				 String pendingScc = xls_Read.getCellValue(sheetName, "div_pendingSccs;xpath");
   				 pendingScc = pendingScc.replace("AWB", data(awb));	

   				 libr.hover(pendingScc);		

   				 String hiddenScc = xls_Read.getCellValue(sheetName, "div_hiddenSccs;xpath");
   				 hiddenScc = hiddenScc.replace("*", scc[i]);	

   				 if(driver.findElements(By.xpath(hiddenScc)).size()==1)
   					 writeExtent("Pass", "Successfully verified the SCC "+scc[i]+" on "+screenID);
   				 else
   					 writeExtent("Fail", "Failed to verify the scc"+scc[i]+" on "+screenID); 

   			 }
   		 }

   	 }
   	 catch(Exception e) {

   		 writeExtent("Fail", "Failed to verify SCC details on "+screenID);
   	 }

    }


	 /**
     * @author A-9847
     * @Desc To verify the flight Instruction as per CFP
     * @param remarks
     */
    public void verifyFlightInstruction(String remarks){
    try{
   	 
   	 clickWebElement(sheetName, "btn_FlightInstruction;xpath", "Instruction button", ScreenName);
   	 waitForSync(3);
   	 String actFlightInstruction = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_flightInstruction;xpath"))).getText();
   	 System.out.println(actFlightInstruction);
   	 verifyScreenText(ScreenName, data(remarks), actFlightInstruction, "Flight Instruction", "Flight Instruction");
   	 clickWebElement(sheetName, "btn_FlightInstruction;xpath", "Instruction slide bar", ScreenName);
   	 waitForSync(3);
    }
    catch(Exception e){
   	 writeExtent("Fail", "Failed to verify Flight Instruction on "+ScreenName);	
    }
}

    /**
     * @author A-9847
     * @Desc To verify the Loading Priority of the given AWB
     * @param loadingPrio
     * @param awb
     */
    
    public void verifyLoadingPriorityfromCFP(String loadingPrio,String awb){
   	 
   	
   	 try
		{
			String actLoadingPrio=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_loadingPriority;xpath").replace("*", data(awb)))).getText();
			waitForSync(1);
			System.out.println(actLoadingPrio);
			
			
			verifyScreenTextWithExactMatch(ScreenName, data(loadingPrio)+"*", actLoadingPrio, "Loading Priority", "Loading Priority");
			
			/******verifyLoadingPriority(ScreenName, data(loadingPrio), actLoadingPrio, "Loading Priority", "Loading Priority");******/
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the Loading Priority on "+ScreenName);	
		}
 
		}
    /**
     * @author A-9847
     * @Desc To verify the Loading Priority of the given AWB
     * @param loadingPrio
     * @param awb
     */
    
    public void verifyAutoCalculatedLoadingPriorityfromCFP(String loadingPrio,String awb){
   	 
   	
   	 try
		{
			String actLoadingPrio=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_loadingPriority;xpath").replace("*", data(awb)))).getText();
			waitForSync(1);
			System.out.println(actLoadingPrio);
			
			
			verifyScreenTextWithExactMatch(ScreenName, data(loadingPrio), actLoadingPrio, "Loading Priority", "Loading Priority");
			
			
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the Loading Priority on "+ScreenName);	
		}
 
		}
    
    /**
	 * Description... Verifies the Screen Text with exact match and logs the result in the Extent
	 * Report
	 * 
	 * @param screenName
	 * @param expText
	 * @param actText
	 * @param functinalityName
	 * @param testSteps
	 */
	public void verifyLoadingPriority(String screenName, String expText, String actText, String functinalityName,
			String testSteps) {

		    if (actText.trim().replaceAll("*", "").equals(expText.trim()))
			onPassUpdate(screenName, expText, actText, functinalityName, testSteps);
		else
			onFailUpdate(screenName, expText, actText, functinalityName, testSteps);
	}
	/**
     * @author A-10328
     * Desc : Selecting Handling Area 
     * @param Handling Area
     * @throws InterruptedException
* @throws AWTException
       * @throws IOException
       */
      
public void  selectHA(String HA) throws InterruptedException, AWTException, IOException 

{
 

 //Select Handling Area
	clickWebElementByWebDriver(sheetName, "lbl_HAautopopulated;xpath", "List Handling area", screenID);
	waitForSync(2);
	String locator2=xls_Read.getCellValue(sheetName, "lst_BDPselectlocation;xpath");
	locator2=locator2.replace("*", HA);
	moveScrollBar(driver.findElement(By.xpath(locator2)));
	driver.findElement(By.xpath(locator2)).click();
	waitForSync(2);
	clickWebElement(sheetName, "btn_closeHA;xpath", "close Handling area", screenID);

}
/** Desc : verify multiple Handling Areas Selected 
* @author A-10328
* @throws InterruptedException
* @throws AWTException
* @throws IOException 
*/
public void verifyMultipleHAAllocated(String HA)throws InterruptedException, AWTException, IOException

{
	try
	{

		String locatorValue1=xls_Read.getCellValue(sheetName, "lbl_HandlingAreaallocated;xpath");
		String locatorValue2=xls_Read.getCellValue(sheetName, "lbl_MultipleHA;xpath");
		String actText1=driver.findElement(By.xpath(locatorValue1)).getText();
		String actText2=driver.findElement(By.xpath(locatorValue2)).getText();
		String actText=actText1+actText2;
		System.out.println(actText);
		waitForSync(2);
		verifyScreenTextWithExactMatch(ScreenName, HA,actText, "successfully verified MultipleHA ", "Buildup Planning");

	}


	catch(Exception e)

	{

		writeExtent("Fail", "Failed to verify Handling Area  "+ScreenName);

	}
}

	/**
	 * @author A-8783 Desc - Verify Loading priority is displayed in Planned
	 *         shipment
	 * @param noOfAwbs
	 */
	public void verifyLoadingPriority(int noOfAwbs) {

		for (int i = 0; i < noOfAwbs; i++) {
			String locator = xls_Read.getCellValue(sheetName, "lbl_loadingPrio;id");
			String row = String.valueOf(i);
			locator = locator.replace("*", row);
			boolean flag;
			flag = driver.findElement(By.id(locator)).isDisplayed();
			if (flag) {
				writeExtent("Pass",
						"Verified that Loading Priority is displayed for AWB " + row + " in " + ScreenName);
				flag=false;
			} else {
				writeExtent("Fail",
						"Could not verify Loading Priority is displayed for AWB " + row + " in " + ScreenName);

			}
		}

	}
	/**
	 * @author A-8783 Desc - Verify Loading priority is displayed in Allocated
	 *         section
	 * @param noOfAwbs
	 */
	public void verifyLoadingPriorityAllocated(int noOfAwbs) {
		for (int i = 0; i < noOfAwbs; i++) {
			String locator = xls_Read.getCellValue(sheetName, "lbl_AllocatedLoadingPrio;id");
			String row = String.valueOf(i);
			locator = locator.replace("*", row);
			boolean flag;
			flag = driver.findElement(By.id(locator)).isDisplayed();
			if (flag) {
				writeExtent("Pass", "Verified that Loading Priority for Allocated shipment is displayed for AWB "
						+ row + " in " + ScreenName);
				flag=false;
			} else {
				writeExtent("Fail", "Could not verify Loading Priority for Allocated shipment is displayed for AWB "
						+ row + " in " + ScreenName);

			}
		}

	}

	/**
	 * @author A-8783 Desc - Verify Loading priority arrow
	 * @param noOfAwbs
	 */
	public void verifyLoadingPrioArrow(String[] awbNo) {

		

		for (int i = 0; i < awbNo.length; i++) {
			String locator = xls_Read.getCellValue(sheetName, "img_loadingPrioArrowLyingList;xpath");
			locator = locator.replace("awbno", awbNo[i]);
			boolean flag;
			flag = driver.findElement(By.xpath(locator)).isDisplayed();
			if (flag) {
				writeExtent("Pass",
						"Verified that Loading Priority Arrow for Allocated shipment is displayed for AWB "
								+ awbNo[i] + " in " + ScreenName);
				flag = false;
			} else {
				writeExtent("Fail",
						"Could not verify Loading Priority Arrow for Allocated shipment is displayed for AWB "
								+ awbNo[i] + " in " + ScreenName);
			}
		}
	}
	/**
	 * @author A-8783
	 * Desc - Verify Loading priority does not exist in Lying list
	 */
	public void verifyLoadingPrioNotExist() {
		String locator = xls_Read.getCellValue(sheetName, "div_loadingPrioLyingList;xpath");
		
		int size = driver.findElements(By.xpath(locator)).size();
		if (size==0) {
			writeExtent("Pass",
					"Verified that Loading Priority does not exist on the Lying List in " + ScreenName);
		} else {
			writeExtent("Fail",
					"Could not verify Loading Priority does not exist on the Lying List in " + ScreenName);
		}
	}
	public void verifyLoadingPrioNotExist(String awb) {

		try{	
			String actLoadingPrio=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_loadingPriority;xpath").replace("*", data(awb)))).getText();	
			if (actLoadingPrio.equals(""))
			{
				writeExtent("Pass","Verified that Loading Priority does not exist for the AWB "+data(awb)+" on the Lying List in " + ScreenName);
			}
			else 
			{
				writeExtent("Fail","Could not verify Loading Priority does not exist for the AWB "+data(awb)+" on the Lying List in " + ScreenName);
			}

		}
		catch(Exception e){

			writeExtent("Fail","Failed to verify Loading Priority does not exist for the AWB on the Lying List in " + ScreenName);
		}
	}
	/**
	 * @author A-8783 Desc - Verify Tool tip
	 * @throws InterruptedException
	 */
	public void verifyLoadingPrioToolTip() throws InterruptedException {

		hover(sheetName, "txt_loadingPrio;xpath");
		waitForSync(1);
		getTextAndVerify(sheetName, "txt_toolTipLoadingPrio;xpath", "Tool Tip ", ScreenName,
				"Verification of Tool tip", "Loading Priority", "equals");
	}
	/**
	 * @author A-8783
	 * Desc - Filter by awb in Lying list
	 * @param awbPrefix
	 * @param awbNumber
	 * @throws InterruptedException 
	 */
	public void filterByShipment(String awbPrefix, String awbNumber) throws InterruptedException {
		//Enter shipment details
		/****enterValueInTextbox(sheetName, "inbx_awbPrefix;name", data(awbPrefix), "Awb Prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_awbNumber;name", data(awbNumber), "Awb Number", screenID);****/
		
		enterValueInTextbox(sheetName, "inbx_lyingAwbPrefix;name", data(awbPrefix), "Awb Prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_lyingAwbNumber;name", data(awbNumber), "Awb Number", screenID);
		
	}
	
	
	/**
	 * @author A-8783
	 * Desc - check if loading priority is in decreasing order
	 * @param noOfAwbs
	 */
	public void verifyDecreasingLoadingPrio(int noOfAwbs) {
		int[] arr = new int[noOfAwbs];
		boolean flag = false;
		for (int i = 0; i < noOfAwbs; i++) {
			String locator = xls_Read.getCellValue(sheetName, "lbl_loadingPrio;id");
			String row = String.valueOf(i);
			locator = locator.replace("*", row);
			String priority = driver.findElement(By.id(locator)).getText();
			arr[i] = Integer.parseInt(priority);
		}
		int length = arr.length - 1;
		for (int k = 0; k < length; k++) {
			if (arr[k] <= arr[k + 1]) {
				flag = true;
				System.out.println(flag);

			} else {
				flag = false;
				System.out.println(flag);
				break;
			}
		}
		if (flag = true) {
			writeExtent("Pass", "Verified that the Loading priority is in decreasing order");
		} else
			writeExtent("Fail", "Loading priority is not in decreasing order");
		System.out.println("out");

	}

/**
	 * 
	 * @author A-9844
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... verify Allocate button is enabled without selecting the shipment
	 */

	public void verifyStatusOfAllocateButtonWithShipmentNotSelected(boolean val,String awbNo) throws InterruptedException, AWTException {


		String locator=xls_Read.getCellValue(sheetName, "btn_AWBNo;xpath");
		locator=locator.replace("AWBNo", data(awbNo));
		waitForSync(2);

		boolean checked = 	driver.findElement(By.xpath(locator)).isSelected();

		if(!checked){

			writeExtent("Pass", "Verified shipment is not selected  on "+ScreenName );
		}

		else{
			writeExtent("Fail", "Shipment checkbox is selected  on "+ScreenName );
		}
		waitForSync(3);
		if(val)
			verifyElementEnabled(sheetName, "btn_Allocate;xpath", "in Buildup Planning Screen", ScreenName, "Allocate Button");
		else
			verifyElementNotEnabled(sheetName, "btn_Allocate;xpath", "in Buildup Planning Screen", ScreenName, "Allocate Button");



	}



/**
	 * 
	 * @author A-9844
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... verify attributes in the Allocate To pop up
	 * @throws IOException 
	 */

	public void verifyingAttributesInAllocateToPopup(String expTextSegment,String expTestAssignType,String expTextBulk,String expTextULDType,String expTextspecificUld,String expTextBuildupLocation,String expTextHandlingArea,String expTextMaterilsRequired,String expTextMaterials,String expTextCount,String expTextInstructions,String expTextInstructionDetails) throws InterruptedException, AWTException, IOException 
	{	

		//segment

		getTextAndVerify(sheetName, "div_segmentLabel;xpath", "Segment Label", screenID, "Verification of label:segment",expTextSegment, "equals");


		//Assign Type

		getTextAndVerify(sheetName, "div_AssighTypeLabel;xpath", "Assign Type Label", screenID, "Verification of label:Assign Type",expTestAssignType, "equals");
		getTextAndVerify(sheetName, "lbl_BULK;xpath", "BULK", screenID, "Verification of label:BULK",expTextBulk, "equals");
		getTextAndVerify(sheetName, "lbl_ULDType;xpath", "ULD Type", screenID, "Verification of label:ULD Type",expTextULDType, "equals");
		getTextAndVerify(sheetName, "lbl_specificULD;xpath", "Specific ULD", screenID, "Verification of label:Specific ULD",expTextspecificUld, "equals");

		//Builup Location

		getTextAndVerify(sheetName, "lbl_buildupLocation;xpath", "Build Up Location", screenID, "Verification of label:Build Up Location",expTextBuildupLocation, "equals");

		//Handling Area
		getTextAndVerify(sheetName, "lbl_handlingArea;xpath", "Handling Area", screenID, "Verification of label:Handling Area",expTextHandlingArea, "equals");

		//Materials Required
		getTextAndVerify(sheetName, "div_MateralsRequiredLabel;xpath", "Materials Required", screenID, "Verification of label:Materials Required",expTextMaterilsRequired, "equals");

		//Material

		getTextAndVerify(sheetName, "lbl_materialText;xpath", "Material", screenID, "Verification of label:Material",expTextMaterials, "equals");

		//Count
		getTextAndVerify(sheetName, "lbl_countText;xpath", "Count", screenID, "Verification of label:Count",expTextCount, "equals");

		//Instructions

		getTextAndVerify(sheetName, "div_Intructions;xpath", "Instructions", screenID, "Verification of label:Instructions",expTextInstructions, "equals");

		//Instruction Details

		getTextAndVerify(sheetName, "lbl_intructionDetails;xpath", "Instruction Details", screenID, "Verification of label:Instruction Details",expTextInstructionDetails, "equals");


	} 





	/**
	 * @author A-9844
	 * @Desc To enter the ULD details
	 * @param ULDTypeIndex
	 * @param count
	 * @param segmentIndex
	 * @throws InterruptedException
	 */
	public void enterUldDetails(String ULDType,String count) throws InterruptedException{

		try{

			//Select ULD Type
			clickWebElementByWebDriver(sheetName, "div_uldType;xpath", "ULD Type", screenID);
			waitForSync(2);
			String Uldlocator=xls_Read.getCellValue(sheetName, "htmldiv_uldType;xpath").replace("*", data(ULDType));
			driver.findElement(By.xpath(Uldlocator)).click();
			waitForSync(3);

			enterValueInTextbox(sheetName, "inbx_countUld;name", count, "count number", screenID);
			waitForSync(5);



		}
		catch(Exception e){
			writeExtent("Fail", "Failed to enter Uldtype on "+screenID);
		}

	}


/**
	 * Desc :verifying empty allocation
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void verifyEmptyAllocation(String expTextUldType,String expTextSegment) throws InterruptedException, AWTException, IOException 

	{
		try {

			String locator=xls_Read.getCellValue(sheetName, "drp_allocationDetails;xpath");

			if((driver.findElements(By.id(locator)).size() == 0))

			{
				writeExtent("Pass", "Verified empty allocation is created on "+ScreenName );
				
				getTextAndVerify(sheetName, "div_uldTypeText;xpath", "Uld Type", screenID, "Verification of Uld Type",data(expTextUldType), "equals");
				getTextAndVerify(sheetName, "div_segmentText;xpath", "Segment", screenID, "Verification of Segment",data(expTextSegment), "equals");
			}
			
			else{
				writeExtent("Fail", "Empty allocation is not created on "+ScreenName );
			}



		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify empty allocation on " + " Page");
		}


	}

	
	/**
     * Desc : Entering flight level instructions
     * @author A-10690
     * @throws InterruptedException
     * @throws AWTException
     * @throws IOException 
     * @param instructions
     */
     public void enterFlightlevelInstruction(String flightinstructions) throws InterruptedException, AWTException, IOException 

     {
           clickWebElement(sheetName, "btn_FlightInstruction;xpath", "Instruction button", ScreenName);
           waitForSync(3);
           clickWebElement(sheetName, "btn_Addflightinstruction;id", "add Button", ScreenName);
           waitForSync(3);
           enterValueInTextbox(sheetName, "inbx_flightinstruction;xpath", flightinstructions, "instructions", screenID);
           clickWebElement(sheetName, "btn_instructionsave;xpath", "save button", ScreenName);
           waitForSync(2);
           clickWebElement(sheetName, "btn_instructionclose;xpath", "close button", ScreenName);
           waitForSync(2);
           clickWebElement(sheetName, "btn_FlightInstruction;xpath", "instruction slide bar", ScreenName);

     } 
     /**
      * Desc : Selecting BP Location
      * @author A-10690
      * @param location
      * @throws InterruptedException
      * @throws AWTException
      */
      public void  selectBPLocation(String location) throws InterruptedException, AWTException 

      {
            //Select Location
          
          clickWebElementByWebDriver(sheetName, "lst_BPLocation;xpath", "List BDP Location", screenID);
          
          String locator2=xls_Read.getCellValue(sheetName, "lst_BDPselectlocation;xpath");
          locator2=locator2.replace("*", data(location));
          moveScrollBar(driver.findElement(By.xpath(locator2)));
          driver.findElement(By.xpath(locator2)).click();

      }
      
      /**
       * Desc : Verifying Handling Area
       * @author A-10690
       * @param HandlingArea
       * @throws InterruptedException
       * @throws AWTException
       */
       public void  verifyHA(String HandlingArea) throws InterruptedException, AWTException 

       {
             //verify handling area
           
    	   getTextAndVerify(sheetName, "lbl_HAautopopulated;xpath", "HA Details", screenID, "Verification of autopopulated HA",HandlingArea, "equals");

       }
       
       /**
        * Desc : verifying Buildup Location in allocated section
        * @author A-10690
        * @param location
        * @throws InterruptedException
        * @throws AWTException
        */
        public void  verifyBuildUpLocationAllocated(String location) throws InterruptedException, AWTException 

        {
              //verify buildup location in allocated section
            
     	   getTextAndVerify(sheetName, "lbl_BPlocationallocated;xpath", "Build up location", screenID, "Verification of buildup location",data(location), "equals");

        }
        /**
         * @author A-10690
         * @param expected awb number
         * Desc : Verify shipment is not available in load plan section
         */
        public void verifyShipmentNotInLoadPlan(String expValue)
        {
        	try
        	{
        		waitForSync(2);
        		boolean verifyData=false;
        		String locator=xls_Read.getCellValue(sheetName, "lnk_loadPlanShipments;xpath");


        		List <WebElement> actEle=driver.findElements(By.xpath(locator));

        		for(WebElement ele:actEle )
        		{
        			if(ele.getText().contains(expValue))
        			{
        				writeExtent("Fail", " fail to verify " + expValue +" is not  present  On "
        						+screenID);
        				verifyData=true;
        				break;
        			}
        		}

        		if(!verifyData)
        			writeExtent("Pass", "Verified " + expValue +" is not present On "
        					+screenID);
        	}

        	catch(Exception e)
        	{
        		writeExtent("Fail", " fail to verify " + expValue +" is not  present  On "
        				+screenID);
        	}


        }
        /**
         * Desc : verifying handling area in allocated section
         * @author A-10690
         * @param HandlingArea
         * @throws InterruptedException
         * @throws AWTException
         */
         public void  verifyHandlingAreaAllocated(String HandlingArea) throws InterruptedException, AWTException 

         {
               //verify handling area in allocated section
             
      	   getTextAndVerify(sheetName, "lbl_HandlingAreaallocated;xpath", "HA Details", screenID, "Verification of allocated HA",HandlingArea, "equals");

         }
         
         /**
          * Desc : Selecting edit button
          * @author A-10690
          * @throws InterruptedException
          * @throws AWTException
          */
          public void  selectEditbutton() throws InterruptedException, AWTException 
          {
         clickWebElementByWebDriver(sheetName, "btn_editpopup;xpath", "edit Button", screenID);
         waitForSync(2);
          }
         
         
         
          /**
           * Desc : Verify handling area and BUP in the modification pop up
           * @author A-10690
           * @param HandlingArea
           * @param location
           * @throws InterruptedException
           * @throws AWTException
           */
           public void  verifyHAAndBUPAllocatedInUpdatePOPUp(String HandlingArea,String location) throws InterruptedException, AWTException 

           {
                 //verify Handling area and build up location
        	   getTextAndVerify(sheetName, "lst_BPLocation;xpath", "BUP Location", screenID, "Verification of Build up location",data(location), "equals");

        	   getTextAndVerify(sheetName, "lbl_HAautopopulated;xpath", "HA Details", screenID, "Verification of HA",data(HandlingArea), "equals");


           } 
           
           /**
       	 * @author A-9844
       	 * Description: Select the AWB check box
       	 * @param awbNo,splitPieces
       	 */
       	public void selectAWB(String awbNo, String splitPieces)
       			throws InterruptedException, AWTException, IOException {
       		try{
       			String locatorAWB=xls_Read.getCellValue(sheetName, "chkbox_awb;xpath");
       			locatorAWB=locatorAWB.replace("splitPcs", data(splitPieces));
       			locatorAWB=locatorAWB.replace("AWB", data(awbNo));
       			driver.findElement(By.xpath(locatorAWB)).click();
       			waitForSync(5);
       			writeExtent("Pass","Selected the AWBNo" + data(awbNo) + " with pieces "+ data(splitPieces)+" on " + ScreenName);
       		}
       		catch (Exception e) {
       			writeExtent("Fail","Could not selected the AWBNo" + data(awbNo) + " with pieces "+ data(splitPieces)+" on " + ScreenName);
       		}
       	}


       /**
       	 * @author A-9844
       	 * Description: Split awb from planned list 
       	 * @param awbNo,pieces
       	 */
       	public void splitShipmentFromPlannedList(String awbNo, String pieces)
       			throws InterruptedException, AWTException, IOException {

       		clickWebElement(sheetName, "btn_splitInPlannedSection;xpath","Split icon in the planned shipment", screenID);
       		clickWebElement(sheetName, "btn_splitIcon;xpath","Split  icon ", screenID);
       		enterValueInTextbox(sheetName, "inbx_splitPieces;xpath", data(pieces), "Pieces", screenID);
       		performKeyActions(sheetName,"inbx_splitPieces;xpath", "TAB","Split Pieces", screenID);
       		clickWebElement(sheetName, "btnOk;xpath", "Ok button", screenID);
       		waitForSync(5);
       	}


     /**
      * Desc : Entering group level instructions
      * @author A-10690
      * @throws InterruptedException
      * @throws AWTException
      * @throws IOException 
      * @param instructions
      */
      public void entergrouplevelinstructions(String instructions) throws InterruptedException, AWTException, IOException 

      {
           
            waitForSync(3);
            enterValueInTextbox(sheetName, "inbx_grpinstruction;xpath", instructions, "instruction", screenID);
            clickWebElement(sheetName, "btn_grpinstruction;xpath", "group level instruction", ScreenName);
            waitForSync(2);
           
      }
      
      /**
       * 
        * @author A-10690
       * @param awb,instrcutions
       * @throws InterruptedException
       * Description... Entering shipment level instructions
       * @throws IOException 
       * @throws AWTException 
       */
       
       public void enterShipmentLevelInstructions(String awb,String instructions) throws InterruptedException, AWTException, IOException 
       {     
    	   //Click more options for an awb
    	   waitForSync(6);
    	   String locator=xls_Read.getCellValue(sheetName, "btn_drpdownshipmentlevel;xpath");
    	   locator=locator.replace("*", data(awb));
    	   moveScrollBar(driver.findElement(By.xpath(locator)));
    	   waitForSync(1);
    	   driver.findElement(By.xpath(locator)).click();
    	   String locator1=xls_Read.getCellValue(sheetName, "btn_shipmentleveladdinstructions;xpath");
    	   if( driver.findElements(By.xpath(locator1)).size()!=1)
    		   driver.findElement(By.xpath(locator)).click();
    	   waitForSync(2);
    	   driver.findElement(By.xpath(locator1)).click();

    	   waitForSync(2);
    	   enterValueInTextbox(sheetName, "inbx_shipmentlevelinstructions;xpath", instructions, "instructions", screenID);
    	   //Ok button
    	   clickWebElement(sheetName, "btn_instructionsave;xpath", "instruction save", ScreenName);
    	   waitForSync(2);
    	   clickWebElement(sheetName, "btn_instructionclose;xpath", "close button", ScreenName);
    	   waitForSync(3);


         
       }
  	

       /**
        * @author A-9847
        * @Desc To select the segment and enter the ULD details
        * @param ULDTypeIndex
        * @param count
        * @param segmentName
        * @throws InterruptedException
        */
          public void enterSegmentAndUldDetails(String ULDType,String count,String segmentName) throws InterruptedException{
       	   	try{
           	    clickWebElementByWebDriver(sheetName, "lst_segment;xpath", "List Segment", screenID);
           	    waitForSync(2);
           	    String segmentLoc=xls_Read.getCellValue(sheetName, "div_segment;xpath").replace("segment", segmentName);
           	    driver.findElement(By.xpath(segmentLoc)).click();
           	    //Select ULD Type
           	    clickWebElementByWebDriver(sheetName, "div_uldType;xpath", "ULD Type", screenID);
           	    waitForSync(2);
           	    String Uldlocator=xls_Read.getCellValue(sheetName, "htmldiv_uldType;xpath").replace("*", data(ULDType));
           	    driver.findElement(By.xpath(Uldlocator)).click();
           	    waitForSync(3);
           	    
           	    enterValueInTextbox(sheetName, "inbx_countUld;name", count, "count number", screenID);
           	    waitForSync(5);
           	    
           	   	}
           	   	catch(Exception e){
           	   		 writeExtent("Fail", "Failed to enter segment/Uldtype on "+screenID);
           	   	}

          }



	/**
     * 
      * @author A-9478
     * @param ScreenID
     * @throws InterruptedException
     * Description... Verify Build up status
     */
     
     public void verifyBuildUpStatus(String status) throws InterruptedException, AWTException 
     {     
     
       String locator=xls_Read.getCellValue(sheetName, "inbx_BuildUpStatus;xpath");
       String actual=driver.findElement(By.xpath(locator)).getText();
       if(actual.equalsIgnoreCase(status))
       {
           writeExtent("Pass", "Status is "+status+" is displaying"+ScreenName + " Page");
       }
       else
       {
           writeExtent("Fail", "Status is "+status+" is not displaying"+ ScreenName + " Page");
       }
       
      
		
     }
     /**
      * Desc : Clicking BUP Shipment Confirmation Ok Button
      * @author A-9175
      * @throws InterruptedException
      * @throws AWTException
      * @throws IOException 
      */
      public void acceptAlertPopUp(String expText) throws InterruptedException, AWTException, IOException 

      {
         try {
                    waitForSync(2);
                          
                          getTextAndVerify(sheetName, "lbl_popUp;xpath", "PopUp Status", screenID, "Sucessfully found", data(expText), "equals");
                          waitForSync(2);  
                            clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", ScreenName);
                          waitForSync(3);
                          writeExtent("Pass", "Sucessfully Accepted Popup " + screenID + " Page");
            } catch (Exception e) {
                    writeExtent("Fail", "Not Accepted Popup " + screenID + " Page");
            }
            
  
      }
      /**
       * @author A-9847
       * @Desc To verify the presence of Text Fields with given label
       * @param textFieldLabel
       */
      public void verifyTextFields(String textFieldLabel[]){

     	 try
     	 { 
     		 for(int i=0;i<textFieldLabel.length;i++)
     		 {
     		 String locator=xls_Read.getCellValue(sheetName, "lbl_sccAndSccGroup;xpath").replace("*", textFieldLabel[i]);   
     		 if(driver.findElements(By.xpath(locator)).size()==1)
     			 writeExtent("Pass", "Text Field : '"+textFieldLabel[i] +"' is present on "+screenID);
     		 else
     			 writeExtent("Fail", "Text Field : '"+textFieldLabel[i] +"' is not present on "+screenID);
     		 }
     	 }
     	 catch(Exception e)
     	 {
     		 writeExtent("Fail", "Failed to verify the given text fields on "+screenID);
     	 }

      }


      /**
       * @author A-9847
       * @Desc To verify the presence of checkboxes with given label
       * @param textFieldLabel
       */
      public void verifyCheckboxes(String label[]){

     	 try
     	 {
     		 for(int i=0;i<label.length;i++)
     		 {
     		 String locator=xls_Read.getCellValue(sheetName, "lbl_checkboxes;xpath").replace("*",label[i]); 
     		 if(driver.findElements(By.xpath(locator)).size()==1)
     			 writeExtent("Pass", "Checkbox : '"+label[i]+"' is present on "+screenID);
     		 else
     			 writeExtent("Fail", "Checkbox : '"+label[i] +"' is not present on "+screenID);
     		 }

     	 }
     	 catch(Exception e)
     	 {
     		 writeExtent("Fail", "Failed to verify the given checkboxes on "+screenID);
     	 }

      }
      
      /**
       * @author A-9847
       * @Desc To verify the presence of ULD no Text Field
       * @param textFieldLabel
       */
    
      public void verifyUldNumberTextField(){

     	 try
     	 {
     		 String locator=xls_Read.getCellValue(sheetName, "div_uldNo;xpath");   
     		 if(driver.findElements(By.xpath(locator)).size()==1)
     			 writeExtent("Pass", "Text Field : 'Uld No'  is present on "+screenID);
     		 else
     			 writeExtent("Fail", "Text Field : 'Uld No' is not present on "+screenID);
     	 }
     	 catch(Exception e)
     	 {
     		 writeExtent("Fail", "Failed to verify the text field on "+screenID);
     	 }

      }
      
      /**
       * @author A-9847
       * @Desc To verify the dropdown fields with default value as Select and dropdown values as Yes/No
       * @param drpdnlabel
       */
      public void verifyDropDownFields(String drpdnlabel[]){

     	 try
     	 {
     		 for(int i=0;i<drpdnlabel.length;i++)
     		 {
     	     //Verifying the dropdown default Value
     		 String locator=xls_Read.getCellValue(sheetName, "div_lstValues;xpath").replace("*",drpdnlabel[i]);    
     		 if(driver.findElements(By.xpath(locator)).size()==1)
     			 writeExtent("Pass", "Drop Down Field :'"+drpdnlabel[i]+"' is present with default Value as 'Select' on "+screenID);
     		 else
     			 writeExtent("Fail", "Drop Down Field :'"+ drpdnlabel[i]+"' is not present on "+screenID);
   
     		 //Verifying the dropdown Values
     		 driver.findElement(By.xpath(locator)).click();	 
     		 
     		 String locator2=xls_Read.getCellValue(sheetName, "lbl_drpdnValues;xpath").replace("*",drpdnlabel[i]);
     		 int drpdnvalues=driver.findElements(By.xpath(locator2)).size();	 
     		 for(int j=1;j<=drpdnvalues;j++)
     		 {	 
     			String value= driver.findElement(By.xpath("("+locator2+")["+j+"]/div")).getText();
     			
     			if(value.equals("Yes") || value.equals("No"))
     				writeExtent("Pass", "Drop Down Field :'"+drpdnlabel[i]+"' has value as "+value+" on "+screenID);
     			else
     				writeExtent("Fail", "Drop Down Field :'"+drpdnlabel[i]+"' has Value other than Yes/No on "+screenID);
  	 
     		 } 
     		 }	 
     	 }
     	 catch(Exception e)
     	 {
     		 writeExtent("Fail", "Failed to verify the drop down field on "+screenID);
     	 }

      }
      
      
      /**
       * @author A-9847
       * @Desc To click the Cancel button on Fliter PopUp
       * @throws InterruptedException
       * @throws IOException
       */
      public void clickCancelInFilterPopUp() throws InterruptedException, IOException{
     	 
     	 clickWebElement(sheetName, "btn_cancel;id", "Cancel button", ScreenName);
     	 
     	 
      }

     /**
      * @author A-9175
      * Desc : Clicking error message to Open
      * @throws InterruptedException
      * @throws AWTException
      * @throws IOException
      */
     public void clickError() throws InterruptedException, AWTException, IOException 
 	{	
        waitForSync(2);
        clickWebElement(sheetName, "btn_PlusErrorPopUp;xpath", "Error button", ScreenName);
 		waitForSync(2);
         
         
 	}
     
     /**
      * Desc: Verifying error message
      * @author A-9175
      * @param expErrorMessage
      * @throws InterruptedException
      * @throws IOException
      */
     public void verifyErrorMessage(String expErrorMessage) throws InterruptedException, IOException{
  		String actErrorMessage=getElementText(sheetName, "lbl_ErrorMsg;xpath", "Error Message", ScreenName);
  	    verifyValueOnPageContains(actErrorMessage, data(expErrorMessage), "Verify Error Message", ScreenName, "Error Message");
  	    
  	}

     /**
      * 
       * @author A-9478
      * @param ULDNum
      * @throws InterruptedException
      * Description... Enter Instruction
      */
      
      public void enterInstruction(String instruction) throws InterruptedException, AWTException 
      {     
            enterValueInTextbox(sheetName, "inbx_instruction;xpath", data(instruction), "Instruction", screenID);
            waitForSync(2);
        }
      /**
       * Desc : Verifying pending section details
       * @author A-9175
       * @param CountAWB
       * @param pcs
       * @param wgt
       * @throws InterruptedException
       */
      
      public void verifyPendingSection(String CountAWB,String pcs,String wgt) throws InterruptedException
      {
      	 getTextAndVerify(sheetName, "span_pendingAWBSize;xpath", "Pending No Of AWBs", screenID, "Verification of status",
      				data(CountAWB),"equals");
      	getTextAndVerify(sheetName, "lbl_pendingPcs;xpath", "Pieces in Pending Section", screenID, "Verification of status",
  				data(pcs),"equals");
      	getTextAndVerify(sheetName, "lbl_pendingWgt;xpath", "Weight in Pending Section", screenID, "Verification of status",
  				data(wgt),"equals");
      	
      }
      
      /**
       * Desc : close screen
       * @author A-9175
       * @throws InterruptedException
       * @throws AWTException
       * @throws IOException
       */
      
      public void closeScreen() throws InterruptedException, AWTException, IOException 

      {
  
            clickWebElement(sheetName, "btn_close;xpath", "Close Button", ScreenName);
            waitForSync(4);

      } 
      

 	


/**
      * 
       * @author A-9478
      * @param ULDNum
      * @throws InterruptedException
      * Description... Click Add  after entering instruction
 * @throws IOException 
      */
      
      public void clickAdd() throws InterruptedException, AWTException, IOException 
      {     
            clickWebElement(sheetName, "btn_Add;xpath", "Add button", ScreenName);
            waitForSync(2);
            
        }

      /**
       * 
        * @author A-9478
       * @param ULDNum
       * @throws InterruptedException
       * Description... Click Unassign button
     * @throws IOException 
       */
      public void clickUnassign() throws InterruptedException, IOException
      {
    	  clickWebElement(sheetName, "btn_unassignShipmnt;xpath", "Unassign button", ScreenName);
          waitForSync(2);
      }
     /**
     * 
      * @author A-9478
     * @param ScreenID
     * @throws InterruptedException
     * Description... unassign awb
     * @throws IOException 
     */
     
     public void unassignAWB(String awb) throws InterruptedException, AWTException, IOException 
     {     
           //Click more options for an awb
       String locator=xls_Read.getCellValue(sheetName, "btn_moreOptions;xpath");
       locator=locator.replace("AWBNo", data(awb));
       driver.findElement(By.xpath(locator)).click();
       waitForSync(2);
       //click unassign button
       clickWebElement(sheetName, "btn_Unassign;xpath", "Unassign button", ScreenName);
           waitForSync(2);
           //Ok button
           clickWebElementByWebDriver(sheetName, "btn_UnassignOk;xpath", "Ok", ScreenName);
           waitForSync(1);
       
     }
     /**
      * @author A-7271
      * @param awb
      * @throws InterruptedException
      * @throws AWTException
      * Desc : click more options
      */
     public void clickMoreoptions(String awb) throws InterruptedException, AWTException 
     {     
           //Click more options for an awb
       String locator=xls_Read.getCellValue(sheetName, "btn_moreOptions;xpath");
       locator=locator.replace("AWBNo", data(awb));
       driver.findElement(By.xpath(locator)).click();
       waitForSync(2);
     }
     /**
      * @author A-7271
      * @throws InterruptedException
      * Desc : Add instructions
      */
     public void clickAddIns() throws InterruptedException
     {
    	 clickWebElementByWebDriver(sheetName, "btn_addIns;xpath", "Add instructions", ScreenName);
         waitForSync(1); 
     }
     /**
      * @author A-7271
      * @throws InterruptedException
      * Desc : Save instructions
      */
     public void saveInsDetails() throws InterruptedException
     {
    	 clickWebElementByWebDriver(sheetName, "btn_saveIns;xpath", "Save instructions", ScreenName);
         waitForSync(1);  
         
         clickWebElementByWebDriver(sheetName, "btn_closeIns;xpath", "Close instructions", ScreenName);
         waitForSync(2);  
         
        
     }

	/**
	 * 
	 * @author A-9478
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... Select ULD
	 */

	public void selectULD(String awbNo) throws InterruptedException, AWTException 
	{	
		    
		  String locator=xls_Read.getCellValue(sheetName, "btn_AWBNo;xpath");
	        locator=locator.replace("AWBNo", data(awbNo));
	        waitForSync(3);
	        moveScrollBar(driver.findElement(By.xpath(locator)));
	        waitForSync(2);
	        driver.findElement(By.xpath(locator)).click();
	        waitForSync(5);

		} 
	
	public void enterNotifyDetails(String toAddress,String subject,String content,String msg) throws InterruptedException
	{
		  clickWebElementByWebDriver(sheetName, "btn_notify;xpath", "Notify", ScreenName);
          waitForSync(1);
          enterValueInTextbox(sheetName, "inbx_notifyToAddress;name",toAddress, "Notify To Address", screenID);
          enterValueInTextbox(sheetName, "inbx_notifySubject;name",subject, "Notify Subject", screenID);
          enterValueInTextbox(sheetName, "inbx_notifyContent;name",subject, "content", screenID);
          clickWebElementByWebDriver(sheetName, "btn_notifySend;xpath", "Notify Send", ScreenName);
          waitForSync(2);
          
          //Verify notify message if send
          
          getTextAndVerify(sheetName, "htmlDiv_notifyMsg;xpath", "Notify Status", screenID, "Verification of notify status",
                  data(msg),"contains");
          
          //Close notify pop up
          clickWebElementByWebDriver(sheetName, "btn_notifyClose;xpath", "Notify Close", ScreenName);
          waitForSync(1);
	}
	/**
     * 
      * @author A-9478
     * @param ScreenID
     * @throws InterruptedException
     * Description... reassign awb to existing allocation
	 * @throws IOException 
     */
     
     public void reassignToExistingAllocation(String awb,String ULD,String pieces) throws InterruptedException, AWTException, IOException 
     {     
           //Click more options for an awb
       String locator=xls_Read.getCellValue(sheetName, "btn_moreOptions;xpath");
       locator=locator.replace("AWBNo", data(awb));
       waitForSync(6);
       driver.findElement(By.xpath(locator)).click();
       waitForSync(6);
       //click Reassign to existing allocation
       clickWebElement(sheetName, "btn_reassignToExistingAllocation;xpath", "Reassign to existing ULD/Allocation button", ScreenName);
       waitForSync(2);
       //Existing allocation tab
       clickWebElementByWebDriver(sheetName, "btn_ExistingAllocationTab;xpath", "Existing Allocation tab", ScreenName);
       waitForSync(2);
       //Select the checkbox
       String locator3=xls_Read.getCellValue(sheetName, "chbx_existingAllocationULD;xpath");
       locator3=locator3.replace("ULD", data(ULD));
       driver.findElement(By.xpath(locator3)).click();
       waitForSync(1);
       //Enter the pieces for the respective ULD
       String locator2=xls_Read.getCellValue(sheetName, "inbx_ExistingAllocationPieces;xpath");
           locator2=locator2.replace("ULD", data(ULD));
           driver.findElement(By.xpath(locator2)).sendKeys(data(pieces));  
           //Click Save
           clickWebElementByWebDriver(sheetName, "btn_Save;xpath", "Save button", ScreenName);
           waitForSync(2);
           }

     /**
      * 
       * @author A-9478
      * @param ScreenID
      * @throws InterruptedException
      * Description... Verify awb within the ULD in Allocated section
      */
      
      public void verifyAWBWithinULDInAllocated(String AWBNo,String ULDNum) throws InterruptedException, AWTException 
      {     
      
        String locator=xls_Read.getCellValue(sheetName, "lst_verifyAWBwithinULD;xpath");
        locator=locator.replace("AWB", data(AWBNo));
        locator=locator.replace("ULD", data(ULDNum));
        System.out.println(locator);
        
        if(driver.findElement(By.xpath(locator)).isDisplayed())
        {
            writeExtent("Pass", " AWB "+ data(AWBNo)+" is within ULD "+data(ULDNum)+ScreenName + " Page");
        }
        else
        {
            writeExtent("Fail", " AWB "+ data(AWBNo)+" is not within ULD "+data(ULDNum)+ScreenName + " Page");
        }
        
        
      }


	/**
	 * @author A-7271
	 * @param awbPrefix
	 * @param awbNumber
	 * @param plndPcs
	 * @param PlndWt
	 * @param Segment
	 * Desc : add shipment in to pending list
	 * @throws InterruptedException 
	 * @throws IOException 
	 * 
	 */
public void addShipment(String awbPrefix,String awbNumber,String plndPcs,String PlndWt,String Segment) throws InterruptedException, IOException
{
	//Add shipment
	clickWebElement(sheetName, "btn_addShipment;id", "Add shipment", ScreenName);
	waitForSync(2);
	//Enter shipment details
	enterValueInTextbox(sheetName, "inbx_awbPrefix;name", data(awbPrefix), "Awb Prefix", screenID);
	enterValueInTextbox(sheetName, "inbx_awbNumber;name", data(awbNumber), "Awb Number", screenID);
	
	clickWebElementByWebDriver(sheetName, "btn_addShipmentList;xpath", "List", ScreenName);
	
	waitForSync(2);
	//Enter planned shipment info
	enterValueInTextbox(sheetName, "inbx_plannedPcs;name", data(plndPcs), "Planned Pieces", screenID);
	enterValueInTextbox(sheetName, "inbx_plannedWt;name", data(PlndWt), "Planned Weight", screenID);
	clickWebElementByWebDriver(sheetName, "htmlDiv_addShipmentSeg;xpath", "Select segment", ScreenName);
	waitForSync(2);
	//Select Segment
	String locator=xls_Read.getCellValue(sheetName, "lnk_segment;xpath");
	locator=locator.replace("ContourIndex",Segment);
	driver.findElement(By.xpath(locator)).click();
	waitForSync(1);
	//OK buttoon
	clickWebElementByWebDriver(sheetName, "btn_addShipmentOK;xpath", "OK", ScreenName);
	waitForSync(1);

}
/**
 * Desc : Capturing Allocate details for radio button ULD type
 * @author A-9175
 * @param segmentIndex
 * @param BDPIndex
 * @param assignType
 * @param ULDTypeIndex
 * @param contourIndex
 * @param count
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */

public void enterAllocateToDetailsForULDType(String segmentIndex,String BDPIndex,String assignType,String ULDTypeIndex,String contourIndex,String count) throws InterruptedException, AWTException, IOException 
{	
    //Select Segment
	clickWebElement(sheetName, "lst_segment;xpath", "List Segment", screenID);
    waitForSync(2);
    String locator=xls_Read.getCellValue(sheetName, "lst_segment;xpath");
    locator=locator.replace("segmentIndex", segmentIndex);
    driver.findElement(By.xpath(locator)).click();
    //Select Radio button        
    String locator3=xls_Read.getCellValue(sheetName, "radiobtn_AssignType;xpath");
    locator3=locator3.replace("AssignType", assignType);
    driver.findElement(By.xpath(locator3)).click();

    //Select BDP Location
    driver.findElement(By.xpath(locator3)).sendKeys(Keys.TAB);
    clickWebElementByWebDriver(sheetName, "lst_BDPLocation;xpath", "List BDP Location", screenID);
    String locator2=xls_Read.getCellValue(sheetName, "lst_BDPLocationIndex;xpath");
    locator2=locator2.replace("BDPIndex", BDPIndex);
    driver.findElement(By.xpath(locator2)).click();
    
 
    if(assignType.equals("ULD Type"))
    {
    	//Select ULD Type
    	clickWebElementByWebDriver(sheetName, "lst_uldType;xpath", "ULD Number", screenID);
        waitForSync(2);
        String Uldlocator=xls_Read.getCellValue(sheetName, "lst_uldTypeindex;xpath");
        Uldlocator=Uldlocator.replace("uldtypeindex", ULDTypeIndex);
        driver.findElement(By.xpath(locator)).click();
        waitForSync(5);
        enterValueInTextbox(sheetName, "inbx_countUld;name", data(count), "count number", screenID);
        waitForSync(5);
        //Select Contour
    	clickWebElementByWebDriver(sheetName, "lst_Contour;xpath", "List Contour", screenID);
        String locator4=xls_Read.getCellValue(sheetName, "lst_ContourIndex;xpath");
        locator4=locator4.replace("ContourIndex", contourIndex);
        driver.findElement(By.xpath(locator4)).click();
        waitForSync(5);
    	
    }
    //Select HA
    String station = getLoggedInStation("ADD004");
    if (station.equals("CDG"))
    {
    	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_CDG"));
    	
    }
    else if (station.equals("AMS"))
    {
    	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_AMS"));
    	
    }
    selectHABeforeAllocation();
    //Click Save
    clickWebElement(sheetName, "btn_Save;xpath", "Save button", ScreenName);
    
	waitForSync(8);
    
    
}
/**
 * Desc : clickWeightExceededPopUp
 */
public void clickWeightExceededPopUp()
{

    if(driver.findElement(By.xpath("//button[contains(.,'Ok')]")).isDisplayed())
    {
    	driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
    }
    waitForSync(5);
}
/**
 * Desc : Capturing Allocate details for radio button ULD type
 * @author A-9175
 * @param segmentIndex
 * @param BDPIndex
 * @param assignType
 * @param ULDTypeIndex
 * @param contourIndex
 * @param count
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */

public void enterDetailsForULDTypeAndVerifyMaterialsInfo(String segmentIndex,String BDPIndex,String assignType,String ULDTypeIndex,String contourIndex,String count) throws InterruptedException, AWTException, IOException 
{	
    //Select Segment
	clickWebElement(sheetName, "lst_segment;xpath", "List Segment", screenID);
    waitForSync(2);
    String locator=xls_Read.getCellValue(sheetName, "lst_segment;xpath");
    locator=locator.replace("segmentIndex", segmentIndex);
    driver.findElement(By.xpath(locator)).click();
    //Select Radio button        
    String locator3=xls_Read.getCellValue(sheetName, "radiobtn_AssignType;xpath");
    locator3=locator3.replace("AssignType", assignType);
    driver.findElement(By.xpath(locator3)).click();

    //Select BDP Location
    driver.findElement(By.xpath(locator3)).sendKeys(Keys.TAB);
    clickWebElementByWebDriver(sheetName, "lst_BDPLocation;xpath", "List BDP Location", screenID);
    String locator2=xls_Read.getCellValue(sheetName, "lst_BDPLocationIndex;xpath");
    locator2=locator2.replace("BDPIndex", BDPIndex);
    driver.findElement(By.xpath(locator2)).click();
    
 
    if(assignType.equals("ULD Type"))
    {
    	//Select ULD Type
    	clickWebElementByWebDriver(sheetName, "lst_uldType;xpath", "ULD Number", screenID);
        waitForSync(2);
        String Uldlocator=xls_Read.getCellValue(sheetName, "lst_uldTypeindex;xpath");
        Uldlocator=Uldlocator.replace("uldtypeindex", ULDTypeIndex);
        driver.findElement(By.xpath(locator)).click();
        waitForSync(5);
        enterValueInTextbox(sheetName, "inbx_countUld;name", data(count), "count number", screenID);
        waitForSync(5);
        //Select Contour
    	clickWebElementByWebDriver(sheetName, "lst_Contour;xpath", "List Contour", screenID);
        String locator4=xls_Read.getCellValue(sheetName, "lst_ContourIndex;xpath");
        locator4=locator4.replace("ContourIndex", contourIndex);
        driver.findElement(By.xpath(locator4)).click();
        waitForSync(5);
    	
    }
    monitorMaterialsInfoTab();
    verifyAvailableMaterials();
    //Select HA
    String station = getLoggedInStation("ADD004");
    if (station.equals("CDG"))
    {
    	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_CDG"));
    
    }
    else if (station.equals("AMS"))
    {
    	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_AMS"));
    	
    }
    selectHABeforeAllocation();
    //Click Save
    clickWebElement(sheetName, "btn_Save;xpath", "Save button", ScreenName);
	waitForSync(8);
    
    
}
/**
 * @author A-9175
 * Desc : Verifying Status of shipment
 * @param ExpectedStatus
 * @throws InterruptedException
 * @throws AWTException
 */

public void verifyStatus(String ExpectedStatus) throws InterruptedException, AWTException 

{
	getTextAndVerify(sheetName, "inbx_BuildUpStatus;xpath", "Build up Status", screenID, "Verification of status",
            data(ExpectedStatus),"equals");
waitForSync(2);

} 


/**
 * 
 * @author A-9478
 * @param ScreenID
 * @throws InterruptedException
 * Description... Click Lying list tab
 * @throws IOException 
 */

public void clickLyinglist() throws InterruptedException, AWTException, IOException 
{	
	clickWebElement(sheetName, "btn_lyinglist;xpath", "Lying List Tab", ScreenName);
	waitForSync(8);
}

public void verifyPendingNoOfAWBs(String expectedResult) throws InterruptedException
{
	 getTextAndVerify(sheetName, "span_pendingAWBSize;xpath", "Pending No Of AWBs", screenID, "Verification of status",
				data(expectedResult),"equals");
	
}

/**
 * @author A-7271
 * @throws InterruptedException
 * Desc : click lying list filter
 * @throws IOException 
 */
public void clickFilterLyingList() throws InterruptedException, IOException
{
	clickWebElement(sheetName, "btn_lyingListFilter;xpath", "Apply Filter For Lying List", ScreenName);
	waitForSync(2);
}
/**
 * @author A-7271
 * @throws InterruptedException
 * Desc : check planned shipment
 * @throws IOException 
 */
public void chkPlandShipmentInLyingList() throws InterruptedException, IOException
{
	clickWebElement(sheetName, "chkBox_plannedShipment;name", "Planned shipment In Lying List Filter", ScreenName);
	waitForSync(2);
}
/**
 * @author A-7271
 * @throws InterruptedException
 * Desc : click apply in lying list filter
 * @throws IOException 
 */
public void clickApplyInLyingListFilter() throws InterruptedException, IOException
{

	clickWebElement(sheetName, "btn_applyLyingListFilter;xpath", "Apply In Lying List Filter", ScreenName);
	waitForSync(2);
}
/**
 * @author A-7271
 * @param value
 * @throws InterruptedException
 * Desc : Search details in lying list
 */
public void searchDtlsInLyingList(String value) throws InterruptedException
{
	enterValueInTextbox(sheetName, "inbx_keywordLyingList;xpath", data(value), "Search value in Lying List", screenID);
	waitForSync(1);
}
/**
 * @author A-7271
 * @param awbNumber
 * Desc : Verify details in lying list
 */
public void verifyDetailsInLyingList(String awbNumber)
{
	verifyDataFromListOfWebElements(data(awbNumber), "lnk_loadPlanShipments;xpath","AWB Number in Lying List",sheetName, ScreenName);
}

/**
 * @author A-7271
 * @throws InterruptedException 
 * Desc : click move to load plan button
 * @throws IOException 
 * 
 */
public void clickMoveToLoadPlan() throws InterruptedException, IOException
{
	clickWebElement(sheetName, "btn_moveToLoadPlan;xpath", "Move to Load plan", ScreenName);
	waitForSync(3);
}
/**
 * @author A-7271
 * @param awbNumber
 * Desc : Verify shipment in loag plan
 */
public void verifyShipmentInLoadPlan(String awbNumber)
{
	verifyDataFromListOfWebElements(data(awbNumber), "lnk_loadPlanShipments;xpath","AWB Number in Load plan",sheetName, ScreenName);
}
	/**
	 * 
	 * @author A-9478
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... Click allocate
	 * @throws IOException 
	 */
	
	public void clickAllocate() throws InterruptedException, AWTException, IOException 
	{	
		clickWebElement(sheetName, "btn_Allocate;xpath", "Allocate button", ScreenName);
		waitForSync(3);
	} 
	
	/**
     * Desc : Save allocation
     * @author A-9175
     * @throws InterruptedException
     * @throws AWTException
	 * @throws IOException 
     */
     public void clickSaveAllocation() throws InterruptedException, AWTException, IOException 

     {
    	//Select HA
    	 String station = getLoggedInStation("ADD004");
    	 if (station.equals("CDG"))

    	 {
    		 map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_CDG"));
    		 
    		 
    		 String buildupLocation= WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG");
    		 
    		 selectLocBeforeAllocation(buildupLocation);
           
    	 }
    	 else if (station.equals("AMS"))

    	 {
    		 map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_AMS"));

    		 String buildupLocationAMS= WebFunctions.getPropertyValue(toproppath, "BufferLocation_AMS");

    		 selectLocBeforeAllocation(buildupLocationAMS);


    	 }
    	 selectHABeforeAllocation();
           clickWebElement(sheetName, "btn_Save;xpath", "Save button", ScreenName);
           waitForSync(8);

     }
     
     /**
      * @DESC : Selects Location Before allocation
      * @author A-9175
      * @param buildupLocation
      */
     public void selectLocBeforeAllocation(String buildupLocation) 
     {
    	 try{  		

 			String defaultText=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_BPLocation;xpath"))).getText();
 			System.out.println(defaultText);
 			if(defaultText.equals("Select"))
 			{   
 				clickWebElementByWebDriver(sheetName, "lst_BPLocation;xpath", "List Handling area", screenID);
 				String locator=xls_Read.getCellValue(sheetName, "lst_BDPselectlocation;xpath").replace("*", buildupLocation);
 				moveScrollBar(driver.findElement(By.xpath(locator)));
 				driver.findElement(By.xpath(locator)).click();
 				writeExtent("Pass", "Successfully Selected BDP location as "+buildupLocation+ " on " +screenID);
 				clickWebElement(sheetName, "btn_closeBDPLoc;xpath", "Buildup Location", screenID);
 			}

 		}
 		catch(Exception e){
 			writeExtent("Fail", "Failed to Selected BDP on "+screenID);

 		}
	 }

     /**@author A-10328
      * Description - Select HA Before allocation 
      * @throws InterruptedException
      * @throws IOException
      */


     public void selectHABeforeAllocation() throws InterruptedException, IOException


     {

    	 try{
    		
    		 String defaultText=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_HAautopopulated;xpath"))).getText();

    		 if(defaultText.equals("Select"))
    		 {
    			 clickWebElementByWebDriver(sheetName, "lbl_HAautopopulated;xpath", "List Handling area", screenID);
    			 String locator=xls_Read.getCellValue(sheetName, "lst_BDPselectlocation;xpath").replace("*", data("HA_Buildup"));
    			 moveScrollBar(driver.findElement(By.xpath(locator)));
    			 driver.findElement(By.xpath(locator)).click();
    			 writeExtent("Pass", "Successfully Selected HA as "+ data("HA_Buildup")+ " on " +screenID);
    			 clickWebElement(sheetName, "btn_closeHA;xpath", "Close Handling area", screenID);
    		 }

    	 }
    	 catch(Exception e){
    		 writeExtent("Fail", "Failed to Selected HA on "+screenID);

    	 }


     }

     /**
      * 
       * @author A-9478
      * @param ULDNum
      * @throws InterruptedException
      * Description... Enter ULD num in Allocate To details
      */
      
      public void enterULDNum(String ULDNum) throws InterruptedException, AWTException 
      {     
            enterValueInTextbox(sheetName, "inbx_uldNumber;id", data(ULDNum), "ULD number", screenID);
            performKeyActions(sheetName,"inbx_uldNumber;id", "TAB","ULD no", screenID);
        }
      /**
       * Desc : Click radio button BULK/ULD/Specific ULD
       * @author A-9175
       * @param assignType
       * @throws InterruptedException
       * @throws AWTException
       */

       public void selectAllocationType(String assignType) throws InterruptedException, AWTException 
       {      
          
           //Select Radio button        
           String locator3=xls_Read.getCellValue(sheetName, "radiobtn_AssignType;xpath");
           locator3=locator3.replace("AssignType", assignType);
           driver.findElement(By.xpath(locator3)).click();
           
       }


/**
       * Desc : Selecting BDP Location
       * @author A-9175
       * @param BDPIndex
       * @throws InterruptedException
       * @throws AWTException
       */
       public void  selectBDPLocation(String BDPIndex) throws InterruptedException, AWTException 

       {
             //Select BDP Location
           
           clickWebElementByWebDriver(sheetName, "lst_BDPLocation;xpath", "List BDP Location", screenID);
           String locator2=xls_Read.getCellValue(sheetName, "lst_BDPLocationIndex;xpath");
           locator2=locator2.replace("BDPIndex", BDPIndex);
           driver.findElement(By.xpath(locator2)).click();

       }
/**
 * 
 * @param segmentIndex
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void  selectSegment(String segmentIndex) throws InterruptedException, AWTException, IOException 

       {
             //Select Segment
             clickWebElement(sheetName, "lst_segment;xpath", "List Segment", screenID);
           waitForSync(2);
           String locator=xls_Read.getCellValue(sheetName, "lst_segment;xpath");
           locator=locator.replace("segmentIndex", segmentIndex);
           driver.findElement(By.xpath(locator)).click();
           waitForSync(3);

       } 
       

      /**
      * 
       * @author A-9478
      * @param ULDNum
      * @throws InterruptedException
      * Description... Select Contour in Allocate To details
      */
      
      public void selectContour(String contourIndex) throws InterruptedException, AWTException 
      {     
            clickWebElementByWebDriver(sheetName, "lst_Contour;xpath", "List Contour", screenID);
            String locator4=xls_Read.getCellValue(sheetName, "lst_ContourIndex;xpath");
            locator4=locator4.replace("ContourIndex", contourIndex);
            driver.findElement(By.xpath(locator4)).click();
        }

	/**
	 * 
	 * @author A-9478
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... Enter Allocate To details
	 * @throws IOException 
	 */
	
	public void enterAllocateToDetails(String segmentIndex,String BDPIndex,String assignType,String ULDNum,String contourIndex) throws InterruptedException, AWTException, IOException 
	{	
        //Select Segment
		clickWebElement(sheetName, "lst_segment;xpath", "List Segment", screenID);
        waitForSync(2);
        String locator=xls_Read.getCellValue(sheetName, "lst_segment;xpath");
        locator=locator.replace("segmentIndex", segmentIndex);
        driver.findElement(By.xpath(locator)).click();
        //Select Radio button        
        String locator3=xls_Read.getCellValue(sheetName, "radiobtn_AssignType;xpath");
        locator3=locator3.replace("AssignType", assignType);
        driver.findElement(By.xpath(locator3)).click();

        //Select BDP Location
        driver.findElement(By.xpath(locator3)).sendKeys(Keys.TAB);
        clickWebElementByWebDriver(sheetName, "lst_BDPLocation;xpath", "List BDP Location", screenID);
        String locator2=xls_Read.getCellValue(sheetName, "lst_BDPLocationIndex;xpath");
        locator2=locator2.replace("BDPIndex", BDPIndex);
        driver.findElement(By.xpath(locator2)).click();
        
        //Enter ULDNumber and select contour
        if(assignType.equals("Specific ULD"))
        {
        	enterValueInTextbox(sheetName, "inbx_uldNumber;id", data(ULDNum), "ULD number", screenID);
        	performKeyActions(sheetName,"inbx_uldNumber;id", "TAB","ULD no", screenID);
        	//Select Contour
        	clickWebElementByWebDriver(sheetName, "lst_Contour;xpath", "List Contour", screenID);
            String locator4=xls_Read.getCellValue(sheetName, "lst_ContourIndex;xpath");
            locator4=locator4.replace("ContourIndex", contourIndex);
            driver.findElement(By.xpath(locator4)).click();
        }
      //Select HA
        String station = getLoggedInStation("ADD004");
        if (station.equals("CDG"))
        {
        	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_CDG"));
        	
        }
        else if (station.equals("AMS"))
        {
        	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_AMS"));
        
        }
    	selectHABeforeAllocation();
        //Click Save
        clickWebElement(sheetName, "btn_Save;xpath", "Save button", ScreenName);
		waitForSync(2);
        
        
	}
	
	public void selectSegment(int seg) throws InterruptedException, IOException, AWTException
	{
		try
		{
		 //Select Segment
		clickWebElementByWebDriver(sheetName, "lst_segment;xpath", "List Segment", screenID);
        waitForSync(2);
        
        for(int i=0;i<seg;i++)
        {
        	keyPress("DOWN");
        	waitForSync(1);
        }
        keyPress("ENTER");
        waitForSync(2);
		}
		
		catch(Exception e)
		{
			
		}
	}
	/**
	 * 
	 * @author A-9478
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... Enter Allocate To details
	 * @throws IOException 
	 */
	
	public void enterAllocateToDetails(String BDPIndex,String assignType,String ULDNum,String contourIndex) throws InterruptedException, AWTException, IOException 
	{	
       
        //Select Radio button        
        String locator3=xls_Read.getCellValue(sheetName, "radiobtn_AssignType;xpath");
        locator3=locator3.replace("AssignType", assignType);
        driver.findElement(By.xpath(locator3)).click();

        //Select BDP Location
        driver.findElement(By.xpath(locator3)).sendKeys(Keys.TAB);
        clickWebElementByWebDriver(sheetName, "lst_BDPLocation;xpath", "List BDP Location", screenID);
        String locator2=xls_Read.getCellValue(sheetName, "lst_BDPLocationIndex;xpath");
        locator2=locator2.replace("BDPIndex", BDPIndex);
        driver.findElement(By.xpath(locator2)).click();
        
        //Enter ULDNumber and select contour
        if(assignType.equals("Specific ULD"))
        {
        	enterValueInTextbox(sheetName, "inbx_uldNumber;id", data(ULDNum), "ULD number", screenID);
        	performKeyActions(sheetName,"inbx_uldNumber;id", "TAB","ULD no", screenID);
        	//Select Contour
        	clickWebElementByWebDriver(sheetName, "lst_Contour;xpath", "List Contour", screenID);
            String locator4=xls_Read.getCellValue(sheetName, "lst_ContourIndex;xpath");
            locator4=locator4.replace("ContourIndex", contourIndex);
            driver.findElement(By.xpath(locator4)).click();
        }
        //Select HA
        String station = getLoggedInStation("ADD004");
        if (station.equals("CDG"))
        {
        	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_CDG"));
        	
        }
        else if (station.equals("AMS"))
        {
        	map.put("HA_Buildup", WebFunctions.getPropertyValue(haproppath, "HA_Buildup_AMS"));
        	
        }
        selectHABeforeAllocation();
        //Click Save
        clickWebElement(sheetName, "btn_Save;xpath", "Save button", ScreenName);
		waitForSync(2);
        
        
	}
	/**
     * Desc : Selecting Material and count
     * @author A-9175
     * @param materialIndex
     * @param Count
     * @throws InterruptedException
     * @throws AWTException
     */
     public void  selectMaterialAndCount(String materialIndex,String Count) throws InterruptedException, AWTException 

     {
           
         //Select Material
         clickWebElementByWebDriver(sheetName, "lst_materialsRequired;xpath", " Material ", screenID);
         String materialLoc=xls_Read.getCellValue(sheetName, "lst_materialsRequiredIndex;xpath");
         materialLoc=materialLoc.replace("MRIndex", materialIndex);
         driver.findElement(By.xpath(materialLoc)).click();
         
         //SelectCount
         enterValueInTextbox(sheetName, "lbl_materialCount;name", data(Count), " Material Count ", screenID);

     } 
     
      
     
      
     /**
     * @author A-9175
     * Desc : Clicking monitor Materials Info Tab 
     *@param ExpectedVersion
     * @throws InterruptedException
     * @throws AWTException
     */
     
     public void verifyVersion(String ExpectedVersion) throws InterruptedException, AWTException 

     {
           getTextAndVerify(sheetName, "lbl_buildupVersion;xpath", "Build up Version", screenID, "Verification of build up version",
                 data(ExpectedVersion),"equals");
     waitForSync(2);

     } 
     /**
     * @author A-9175
     * Desc : Clicking monitor Materials Info Tab 
      * @throws InterruptedException
     * @throws AWTException
     */
     public void  monitorMaterialsInfoTab() throws InterruptedException, AWTException 

     {
           clickWebElementByWebDriver(sheetName, "btn_materialInfoTab;xpath", " Material Info Tab ", screenID);
           waitForSync(3);

     } 
     
      
     
     /**
     * @author A-9175
     * Desc : Verifying materials available in Master
     * @throws InterruptedException
     * @throws AWTException
     */
     public void  verifyAvailableMaterials() throws InterruptedException, AWTException 

     {
           int count=0;
           try
           {
                  List<WebElement> availableMaterials=new ArrayList<WebElement>();
                  List<String> availableMaterialsValues=new ArrayList<String>();
                  String availableMaterialsLoc=xls_Read.getCellValue(sheetName, "tbl_availableMaterials;xpath");
                  availableMaterials=driver.findElements(By.xpath(availableMaterialsLoc));
                  for(WebElement material:availableMaterials)
                  {
                         availableMaterialsValues.add(material.getText());
                         System.out.println(material.getText());
                         count++;
                  }
                  if(count!=0)
                  writeExtent("Pass", " Material Details Found as "+ availableMaterialsValues+ "on "+screenID);
                  if(count==0)
                	  writeExtent("Fail", "Material Details Not Found on "+screenID);
           }
           catch(Exception e)
           {
           writeExtent("Fail", " Material Details Not Found on "+screenID);
           }

     } 
     /**@author A-10328
 	 * Description - Select the task before release
 	 */
     public void selectTaskBeforeRelease()
     {
     String locator=xls_Read.getCellValue(sheetName, "btn_checkbox;xpath");
     moveScrollBar(driver.findElement(By.xpath(locator)));
     driver.findElement(By.xpath(locator)).click();
     waitForSync(2);
     }

     /**
     * Desc : Clicking Release Button
     * @author A-9175
     * @throws InterruptedException
     * @throws AWTException
     * @throws IOException 
     */
     public void clickRelease() throws InterruptedException, AWTException, IOException 

     {
    	 String locator=xls_Read.getCellValue(sheetName, "btn_checkbox;xpath");
    	 if(!driver.findElement(By.xpath(locator)).isSelected())
    		 selectTaskBeforeRelease();
    	 clickWebElement(sheetName, "btn_Release;xpath", "Release button", ScreenName);
    	 waitForSync(3);
    	 clickWebElement(sheetName, "btn_Ok;xpath", "Ok Button", ScreenName);
    	 waitForSync(3);



     } 
     

	
	
}
