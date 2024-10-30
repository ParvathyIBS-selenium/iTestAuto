package screens;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ExportManifest_OPR344  extends CustomFunctions {
	String sheetName = "ExportManifest_OPR344";
	public CustomFunctions customFuction;
	String screenID = "OPR344";
	public String screenName = "ExportManifest";
	public static String uldproppath = "\\src\\resources\\ULD.properties";
	 public static String toproppath = "\\src\\resources\\TO.properties";

	public ExportManifest_OPR344(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelreadwrite, xls_Read);

	}

	/**
	 * @author A-7271
	 * @Desc: List flight 
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... List Flight
	 */
	public void listFlight(String carrierCode, String flightNumber, String flightDate) throws InterruptedException, AWTException {

		try {
			waitTillScreenload(sheetName, "inbx_carrierCode;id","Flight Carrier code", screenName);
			waitForSync(2);
			enterValueInTextbox(sheetName, "inbx_carrierCode;id", data(carrierCode), "Carrier Code", screenID);
			enterValueInTextbox(sheetName, "inbx_flightNumber;id", data(flightNumber), "Flight Number", screenID);
			enterValueInTextbox(sheetName, "inbx_flightDate;id", data(flightDate), "Flight Date", screenID);
			clickWebElementByWebDriver(sheetName, "btn_List;id", "List Button", screenID);
			waitForSync(5);
		} catch (Exception e) {
			System.out.println("Could not perform list flight operations");
			test.log(LogStatus.FAIL, "Could not perform list flight operations in "+screenName);

		}
	}

/**@author A-98444
	 * Description - Enter Build up location if its not enetered
	 * @throws InterruptedException
	 */
	public void enterBuildUpLocationIfNotEntered() throws InterruptedException
	{
		String locator=xls_Read.getCellValue(sheetName, "inbx_buildupLocation;xpath");
		String locationText=driver.findElement(By.xpath(locator)).getAttribute("value");
		if(locationText.isEmpty()){

			String station = getLoggedInStation("OPR344");
			if (station.equals("CDG")){

				String buildupLocation= WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG");
				enterValueInTextbox(sheetName, "inbx_buildupLocation;xpath", buildupLocation, "build up location", screenID);
			}
		}

	}
	/**
	 * Desc : verify buiuldup reopen
	 * @author A-10330
	 * @throws InterruptedException
	 * */
	public void verifyBuildupcompleteReopened() throws InterruptedException, IOException
	{

		clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);

		waitForSync(2);
		String uldNum=getAttributeWebElement(sheetName, "inbx_uldnumber;name", "ULD Textbox","value", screenID);

		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(3);
		waitTillScreenloadWithOutAssertion(sheetName, "btn_ATAOk;xpath","popup ok button", screenName,10);


		String acceptAlert=xls_Read.getCellValue(sheetName, "btn_ATAOk;xpath");
		if(driver.findElements(By.xpath(acceptAlert)).size()==1)
		{
			clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
		}

		waitTillScreenloadWithOutAssertion(sheetName, "btn_AddULD;xpath","Add Uld Button", screenName,10);


		map.put("ULDNumber",uldNum );
		verifyBuildUpCompleteNotRetained("ULDNumber");
		waitForSync(2);
		clickEditULDdetails("ULDNumber");
	}

    /**@author A-10328
	 * Description - Enter Offload location 
	 * @throws InterruptedException
	 */
	public void enterOffloadLocation() throws InterruptedException
	
	{

		String station = getLoggedInStation("OPR344");
		String offlaodLocation="";

		if (station.equals("CDG"))

		{

			offlaodLocation= WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG");

		}
		else if (station.equals("AMS"))
		{

			offlaodLocation= WebFunctions.getPropertyValue(toproppath, "BufferLocation_AMS");


		}
		else if ((station.equals("IAD"))|(station.equals("WRO")))
		{
			offlaodLocation= WebFunctions.getPropertyValue(toproppath, "Location_IAD");
		}

		else if (station.equals("BEY"))
		{
			offlaodLocation= WebFunctions.getPropertyValue(toproppath, "Location_BEY");
		}


		enterValueInTextbox(sheetName, "inbx_offloadLoc;xpath", offlaodLocation, "build up location", screenID);
		waitForSync(2);
	}

    /**@author A-10328
	 * Description - Enter Build up location 
	 * @throws InterruptedException
	 */
	public void enterBuildUpLocation() throws InterruptedException
	{
		String station = getLoggedInStation("OPR344");
		String buildupLocation="";
		clickMoreUldDetails();
		if (station.equals("CDG"))

		{

			buildupLocation= WebFunctions.getPropertyValue(toproppath, "PITLocation_CDG");

		}
		else if (station.equals("AMS"))
		{

			buildupLocation= WebFunctions.getPropertyValue(toproppath, "BufferLocation_AMS");


		}
		else if ((station.equals("IAD"))|(station.equals("WRO")))
		{
			buildupLocation= WebFunctions.getPropertyValue(toproppath, "Location_IAD");
		}

		else if (station.equals("BEY"))
		{
			buildupLocation= WebFunctions.getPropertyValue(toproppath, "Location_BEY");
		}
		

		enterValueInTextbox(sheetName, "inbx_buildupLocation;xpath", buildupLocation, "build up location", screenID);
	}

	/**
	 * @author A-10330
	 * Description : verification of scc at uld level
	 * @param uldnum,scc
	 */
	 public void verifySCCAtUldLevel(String uldnum,String[] SCC) throws InterruptedException, IOException

	 {
		 String locator=xls_Read.getCellValue(sheetName, "btn_maximizeuld;xpath");
		 locator=locator.replace("*",data(uldnum));
		 driver.findElement(By.xpath(locator)).click();
		 try{
			 String SCCValue=xls_Read.getCellValue(sheetName, "txt_SCC1;xpath");
			 for(int i=0;i<SCC.length;i++) {
				 SCCValue=SCCValue.replace("*",SCC[i]);
				 String actText=driver.findElement(By.xpath(SCCValue)).getText();

				 verifyScreenText(sheetName,SCC[i], actText,"successfully verified"+SCC[i],"verified SCC");
			 }
		 }


		 catch(Exception e)
		 {
			 writeExtent("Fail", "could not extract text SCC  on" + screenID + " Page"); 
		 }
	 }
	 /**
		 * @author A-9847
		 * @Desc To click on BuildUp Complete without capturing Actual Weight Capture
		 * @throws InterruptedException
		 * @throws IOException
		 */
		
		public void clickBuildUpCompleteWithoutActualWeightCapture() throws InterruptedException, IOException
		{
			waitTillScreenload(sheetName, "btn_BuildUpComplete;xpath","Build up complete button", screenName);
			clickWebElementByWebDriver(sheetName, "btn_BuildUpComplete;xpath", "Build up complete button", screenID);
			waitForSync(5);

		}
	/**
	 * @author A-10690
	 * Description  : Clicks on Build up complete with respect to the ULD
	 * @param ULDNo
	 * @throws InterruptedException
	 * @throws IOException 
	 * 
	 */
	public void clickBuildUpComplete(String ULDNo) throws InterruptedException, IOException
	{
		enterActualweight(ULDNo);

		waitTillScreenload(sheetName, "btn_BuildUpComplete;xpath","Build up complete button", screenName);
		String locator=xls_Read.getCellValue(sheetName, "btn_BuildUpCompleteuld;xpath");
		locator=locator.replace("*", data(ULDNo));
		WebElement element=driver.findElement(By.xpath(locator));
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		waitForSync(5);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

	}
	
	
	/** Description : enter actual weight with respect to each uld if multiple ULDs are present
	 * @param uld
	 * @throws InterruptedException,IOException
	 */
	public void enterActualweight(String uld) throws InterruptedException, IOException
	{
		try{
			waitForSync(4);
			String locator = xls_Read.getCellValue(sheetName, "txt_actualweight;xpath");

			String ULDType=data(uld).substring(0,3);
			String Carriercode=data(uld).substring(8);
			if(driver.findElements(By.xpath(locator)).size()==0)
			{

				clickEditULDdetailsByJS(uld);
				clickMoreUldDetails();
				
			}
			waitForSync(1);

			if((ULDType.equals("PMC"))&&(Carriercode.equals("KL")))
				map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight1"));
			else
			map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight"));

			String Wght=driver.findElement(By.xpath(locator)).getText();
			int newWeight=(Integer.parseInt(data("Extraweight")));
			int actweight=(Integer.parseInt(Wght))+newWeight;
			String actwght=String.valueOf(actweight);
			map.put("actwght", actwght);
			
			captureMoreUldDetails("actwght","val~80");

			}
			catch(Exception e)
			{
				System.out.println("Failed to enter more uld details on " +screenName);
			}
	}


    /**@author A-10328
	 * @throws Exception 
	 * @Desc: check the exclude empty ULDS 
	 */

public void excludeEmptyULDS() throws Exception
	
{
	clickWebElementByWebDriver(sheetName, "chk_excludeemptyULD;xpath", "Exclude empty ULDS", screenName);
	waitForSync(2);	
		
	}



	/**@author A-10328
	 * @throws Exception 
	 * @Desc: Uncheck the consider for manifest print
	 */
	public void considerformanifestprint() throws Exception
	
	
	{

	clickWebElementByWebDriver(sheetName, "chk_considerformanifestprint;xpath", "consider for manifest print", screenName);
	waitForSync(2);	
		
	}
	/**@author A-10328
	 *  * @throws IOException, InvalidPasswordException
	 * @param fileName
	 * @param expectedValues
	 * @Desc: Uncheck the consider for manifest print
	 */

	public void extractPDFContentAndCompare(String fileName,String...expectedValues) throws InvalidPasswordException, IOException


	{


		String filePath=System.getProperty("user.dir")+"\\src\\resources\\Downloads\\";

		try (PDDocument document = PDDocument.load(new File(filePath+fileName)))


		{

			document.getClass();

			if (!document.isEncrypted()) 

			{

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);


				for (String s  : expectedValues)

				{

					if(pdfFileInText.contains(s))
					{
						writeExtent("Pass", "Sucessfully Verified on "+fileName+" report : " + s + " on " + screenID);
					}
					else
					{
						writeExtent("Fail", "Not Verified on "+fileName+" report : " + s + " on " + screenID);


					}



				}

			}


		}
              
                	
            
                	
        

catch(Exception e)
		


{
	

writeExtent("Fail","Could not extract the data from the document "+fileName);


}
	}

    /**@author A-10328
	 * @Desc: Verifying filter in manifest popup 
	 * @param SccFilter
	 * @param ULDFilter
	 * @throws Exception 
 */
	




public void verifyFilter(String SccFilter, String ULDFilter) throws Exception
	

{
	
switchToFrame("frameName","popupContainerFrame");


String locator =xls_Read.getCellValue(sheetName,"chk_filter;xpath");
String ActualText=driver.findElement(By.xpath(locator)).getText();


String locator2 =xls_Read.getCellValue(sheetName,"chk_ULDFilter;xpath");
String ActualText1=driver.findElement(By.xpath(locator2)).getText();
	
verifyScreenTextWithExactMatch(sheetName, data(SccFilter),ActualText, "verifying SCC Filter", "Export Manifest");

verifyScreenTextWithExactMatch(sheetName, data(ULDFilter),ActualText1, "verifying ULD Filter", "Export Manifest");


}

	/**
	 * @author A-10690
	 * Description : Add new ULD with awb
	 * @param uldNo
	 * @param POU
	 * @param prefix
	 * @param awb number
	 * @param pieces,weight,contour
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addNewULDWithAWBAndContour(String uldNo,String pou,String prefix,String awbNumber,String pcs,String wt,String contour) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		/***************************************************************************/
		/**Method for selecting POU when pou is not autopopulated***/
		selectPOU(pou);

		waitForSync(1);
		/***************************************************************************/
		//Adding contour details
		for(int i=0;i<2;i++)
		{
			/**checking whether contour selected by default***/
			String contour1=xls_Read.getCellValue(sheetName, "lbl_countourcheck;xpath");
			contour1=contour1.replace("*", data(contour));
			if(driver.findElements(By.xpath(contour1)).size()==1)
			{
				break;
			}
			String locator1=xls_Read.getCellValue(sheetName, "lbl_countour;xpath");
			driver.findElement(By.xpath(locator1)).click();
			String locator2=xls_Read.getCellValue(sheetName, "dpdwn_countourname;xpath");
			locator2=locator2.replace("*", data(contour));
			if(driver.findElements(By.xpath(locator2)).size()==1)
			{

				driver.findElement(By.xpath(locator2)).click();
				break;
			}
		}
		//Enter BuildUp Location 

         enterBuildUpLocation();

		//Add AWB Number
		addAWBDetails(prefix,awbNumber,pcs,wt);
		try {

			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");

			}
			/***************************************************/
			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
			}

		} catch (Exception e) {

		}
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}



	}

	/**
	 * Desc : Adding breakdown instruction
	 * @author A-10690
	 * @param instruction
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addbreakdowninstruction(String instruction) throws InterruptedException, AWTException, IOException
	{
		clickWebElementByWebDriver(sheetName, "lst_BDN1;xpath", "List BDN", screenID);
		waitForSync(3);
		try {
			String locator=xls_Read.getCellValue(sheetName, "lst_BDNIndex1;xpath");
			locator=locator.replace("*", instruction);
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", " BDN instruction Selected "+ screenID + " Page");
		} catch (Exception e) {
			writeExtent("Fail", " BDN instruction Not Selected "+ screenID + " Page");
		}
		waitForSync(2);    
		waitForSync(2);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}
	}
	
	
	/**
	 * Desc :Clicking Edit Uld button
	 * @author A-10690	 
	 * * @param uldNo
	 */
	public void clickEditULDdetailsByJS(String uldNo) {
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "btn_editwithULD;xpath");
		locator=locator.replace("*", data(uldNo));
		try{
			WebElement element=driver.findElement(By.xpath(locator));

			JavascriptExecutor executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].click();", element);
			
			waitForSync(2);
			writeExtent("Pass", "Edit ULD Button Clicked in " + screenID + " Page");
		}
		catch (Exception e) {
			writeExtent("Fail", "Edit ULD Button Clicked in Not Clicked in " + screenID + " Page");
		}
	}

	/**
	 * @author A-9844
	 * @param ULDNo
	 * @param AWBNo
	 * @param count
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : to verify N number of Ulds and the AWB details present in the planned section 
	 */
	public void verifyULDandAWBDetailsInPlannedSection(int count,String[] ULDNo,String[] AWBNo) throws InterruptedException, AWTException {

		try{
			for(int i=0;i<count;i++){


				try {
					enterValueInTextbox(sheetName, "inbx_placeholderText;xpath", ULDNo[i], "Uld", screenID);
					waitForSync(3);
					String ULDText=xls_Read.getCellValue(sheetName, "lbl_plannedshipmentULD;xpath");
					ULDText=ULDText.replace("ULDno", ULDNo[i]);

					String uldDropdownLocator=xls_Read.getCellValue(sheetName, "btn_uldDropdwn;xpath");
					uldDropdownLocator=uldDropdownLocator.replace("uldNo", ULDNo[i]);

					String AWBText=xls_Read.getCellValue(sheetName, "txt_awbText;xpath");
					AWBText=AWBText.replace("*", AWBNo[i]);

					if(driver.findElement(By.xpath(ULDText)).isDisplayed()){
						waitForSync(3);
						writeExtent("Pass", "ULD "+ULDNo[i]+ " exists in planned section" + screenID + " Page");

						driver.findElement(By.xpath(uldDropdownLocator)).click();
						waitForSync(2);
						if(driver.findElement(By.xpath(AWBText)).isDisplayed()){
							waitForSync(2);
							writeExtent("Pass", "Awb number "+AWBNo[i]+ " exists in planned section" + screenID + " Page");
						}


						clearText(sheetName, "inbx_placeholderText;xpath", "Search Field ", screenName);
						waitForSync(3);
					}

					else
						writeExtent("Fail", "ULD/Awb number "+ULDNo[i]+ " does not exist in planned section" + screenID + " Page");
					waitForSync(2);
				} catch (Exception e) {
					writeExtent("Fail", "Element not found in" + screenID + " Page");
				}

			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't verify the ULD in "+screenName);
		}


	}

	/** 
	* @author A-9844
	* Description : Offload ULD with reason
	* @throws InterruptedException
	* @throws AWTException
	 * @throws IOException 
	*/
	public void offloadULDWithReasonAfterBuildupComplete(String ULD) throws InterruptedException, AWTException, IOException
	{
		String locator=xls_Read.getCellValue(sheetName, "btn_OffloadULD_BuilupComplete;xpath");
		locator=locator.replace("ULDNum", data(ULD));
		driver.findElement(By.xpath(locator)).click();
		waitForSync(2);

		handlePopup();

		clickWebElementByWebDriver(sheetName, "lst_offloadReason;xpath", "Offload Reason", screenID);
		waitForSync(3);
		keyPress("ENTER");
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_offloadSave;xpath", "Offload Save", screenID);
		waitForSync(2);


	}
	/**
	 * @author A-9847
	 * Desc To offload the ULD with offload location and reason
	 * @param ULD
	 * @param offloadLoc
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	public void offloadULDWithReasonAndLocationAfterBuildupComplete(String ULD,String offloadLoc)throws InterruptedException, AWTException
	{

		try{

			String locator=xls_Read.getCellValue(sheetName, "btn_OffloadULD_BuilupComplete;xpath");
			locator=locator.replace("ULDNum", data(ULD));
			driver.findElement(By.xpath(locator)).click();
			waitTillScreenloadWithOutAssertion(sheetName, "inbx_offloadLoc;xpath", "Offloaded Location", screenID, 10);
			handlePopup();	
			//Entering Offload Location
			enterValueInTextbox(sheetName, "inbx_offloadLoc;xpath", data(offloadLoc), "Offloaded Location", screenID);

			//Selecting the Offload Reason
			clickWebElementByWebDriver(sheetName, "lst_offloadReason;xpath", "Offload Reason", screenID);
			waitForSync(3);
			keyPress("ENTER");
			waitForSync(2);

			clickWebElementByWebDriver(sheetName, "btn_offloadSave;xpath", "Offload Save", screenID);
			waitForSync(2);

		}
		catch(Exception e){

			writeExtent("Fail", "Failed to offload the ULD on "+screenName);
		}

	}
	/**
	 * @author A-10690
	 * Description : offload awb
	 * @param SCC
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void offloadAwb(String SCC) throws InterruptedException, AWTException
	{
		clickWebElementByWebDriver(sheetName, "btn_offload;id", "Offload Button", screenID);
		waitTillScreenload(sheetName, "inbx_offloadLoc;xpath", "Offload Location", screenID);
		enterOffloadLocation();


		clickWebElementByWebDriver(sheetName, "btn_offloadSave;xpath", "Offload Save", screenID);
		waitForSync(2);
		
		
		try{
		String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
		String actText=driver.findElement(By.xpath(popUp)).getText();
		if (actText.contains("marked as Buildup Complete. Do you want to proceed with re-opening?"))
		{
			waitForSync(3);  
			clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
			waitForSync(5); 
			writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
		}
		}
		catch (Exception e) {
			
		}
		

		
		String locator=xls_Read.getCellValue(sheetName, "dpdwn_selectScc;xpath");
		if(driver.findElements(By.xpath(locator)).size()==1)
		{

			//Select ULD Type
			clickWebElementByWebDriver(sheetName, "dpdwn_selectScc;xpath", "select scc", screenID);
			waitForSync(2);
			String scclocator=xls_Read.getCellValue(sheetName, "div_sccselect;xpath").replace("*", data(SCC));
			waitForSync(3);
			driver.findElement(By.xpath(scclocator)).click();
			waitForSync(3);
			clickWebElementByWebDriver(sheetName, "btn_offloadsccok;xpath", "ok button", screenID);
			waitForSync(3);
		}



	}
	/**
	 * @Description : Method for verifying contents are present and not present based on the boolean value passed
	 * @author A-9844
	 * @param reportHeading
	 * @param screenId
	 * @param VP
	 * @throws Exception
	 */
	public void printAndVerifyReport(String reportHeading,String screenId,boolean isPresent,String...VP ) throws Exception
	{
		try
		{

			//Verification if report got generated

			switchToWindow("storeParent");

			switchToWindow("multipleWindows");

			int windowSize=getWindowSize();



			if(windowSize==2)
			{
				switchToFrame("frameName","ReportContainerFrame");

				//Verifying heading of the report

				String locatorHeading=xls_Read.getCellValue("Generic_Elements", "htmlDiv_reportHeading;xpath");
				locatorHeading=locatorHeading.replace("ReportHeading", data(reportHeading));
				try {
					if(driver.findElement(By.xpath(locatorHeading)).isDisplayed())
					{
						onPassUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is  getting generated", "Verify whether the report is generated","Verify whether the report is generated");
					}
					else
					{
						onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
					}

				} catch (Exception e) {
					onFailUpdate(screenId, "Report is generated and the Heading "+data(reportHeading)+" is stamped", "Report is Not getting generated", "Verify whether the report is Not generated","Verify whether the report is not generated",false);
				}

				//Verifying Report Contents

				String locatorContent=xls_Read.getCellValue("Generic_Elements", "htmlContents_report;xpath");
				String contents=driver.findElement(By.xpath(locatorContent)).getText();
				

				waitForSync(5);
				for(String s:VP)
				{

					if(isPresent){
						waitForSync(5);

						if(contents.contains(s))
						{
							writeExtent("Pass", "Sucessfully Verified on report : " +s + " on " + screenId);
						}
						else
						{ 
							writeExtent("Fail", "Not Verified on report : " + s + " on " + screenId);
						}
					}


					else{


						waitForSync(2);

						if(!contents.contains(s))
						{
							writeExtent("Pass", "Sucessfully Verified the content is not present on the report : " +s + " on " + screenId);
						}
						else
						{ 
							writeExtent("Fail", "Content is present on the report : " + s + " on " + screenId);
						}
					}
				}
			}
		}




		catch(Exception e)
		{
			writeExtent("Fail", "Report is not getting generated"+" on " + screenId);
		}
		finally
		{
			closeBrowser();
			waitForSync(2);
			switchToWindow("getParent");
			switchToFrame("default");
			switchToFrame("contentFrame",screenId);
		}

	}



/**
	 * @author A-9844
	 * Description : Verify the flight status is not Offloaded
	 * @param status
	 * @throws InterruptedException
	 */
	public void verifyFlightStatusIsNotOffloaded(String status) throws InterruptedException
	{
		String locator=xls_Read.getCellValue(sheetName, "htmlDiv_flightStatus;xpath");
		
		String actText=driver.findElement(By.xpath(locator)).getText();
		
		if(!actText.equals(data(status))){
			writeExtent("Pass", "Sucessfully verified the status is not "+data(status)+" on "+ screenID + " Page");
		}
		
		else{
			writeExtent("Fail", "The status is  "+actText+ " on "+screenID + " Page");
		}

	}

	/**
	 * @author A-8783
	 * Desc - Select Document type
	 * @param docType
	 * @throws InterruptedException
	 */
	public void selectDocType(String docType) throws InterruptedException {
		try {
			switchToFrame("frameName","popupContainerFrame");
			switchToWindow("storeParent");
		String locator=xls_Read.getCellValue(sheetName, "chkBox_docType;xpath");
		locator=locator.replace("docType", docType);
		driver.findElement(By.xpath(locator)).click();
		writeExtent("Pass", "Click on "+docType +" on "+screenName);
		waitForSync(2);
		}
		catch(Exception e){
			writeExtent("Pass", "Could not click on "+docType +" on "+screenName);
		}

	}
	/**
	 * @author A-8783
	 * Desc -  Select additional type from dropdown
	 * @param docType
	 * @throws Exception
	 */
	public void selectAdditionalDocType(String docType) throws Exception {
		clickWebElement(sheetName, "dpdwn_addtnlReport;xpath", "Select Additional Report", screenID);
		selectValueInDropdown(sheetName, "dpdwn_addtnlReport;xpath", docType, "Select Additional Report","Value");


		
	}
	/**
	 * @author A-8783
	 * @throws Exception
	 * Desc : clock ok button of print manifest pop up
	 */
	public void printManifestOkWithoutFrame() throws Exception
	{
		waitForSync(4);
		clickWebElementByWebDriver(sheetName, "btn_ManifestOk;id", "Manifest Pop up Ok", screenID);
		waitForSync(6);	
	}

/**
	 * Desc : Verify HAWB
	 * @author A-10328
	 * @param expText
       * @param actText
	 * @throws InterruptedException
	 * @throws IOException
	 */

public void verifyHAWBpresent(String expText,String awbNumber) throws IOException, InterruptedException {
	try
	{
		String locatorHAWB = xls_Read.getCellValue(sheetName, "htmlDiv_hawb_eawb;xpath").replace("*", awbNumber);
		String locatorHAWB2 = xls_Read.getCellValue(sheetName, "htmlDiv_hawb;xpath").replace("*", awbNumber);
		
		String acttext="";

		try
		{
			acttext=driver.findElement(By.xpath(locatorHAWB)).getText();
		}
		catch(Exception e)
		{
			acttext=driver.findElement(By.xpath(locatorHAWB2)).getText();
		}
		
		verifyScreenTextWithExactMatch(sheetName, expText,acttext, "verifying HAWB", "Export Manifest");
	}
	catch(Exception e)
	{
		writeExtent("Fail","Verification of HAWB could not be done on "+screenName);
	}
}
/* Desc : Verify Pieces,Weight
* @author A-10328
* @param pcs
  * @param weight
* @throws InterruptedException
*/

public void verifyPiecesWeight(String pcs, String weight,String awbNumber) throws InterruptedException
{

	String locatorPcs= xls_Read.getCellValue(sheetName, "htmlDiv_pieces_eawb;xpath").replace("*", awbNumber);
	String locatorPcs2= xls_Read.getCellValue(sheetName, "htmlDiv_pieces;xpath").replace("*", awbNumber);
	String acttext="";

	//Verification of Pieces
	try
	{
		acttext=driver.findElement(By.xpath(locatorPcs)).getText();
	}
	catch(Exception e)
	{
		acttext=driver.findElement(By.xpath(locatorPcs2)).getText();
	}
	
	verifyScreenTextWithExactMatch(sheetName, pcs,acttext, "verifying pieces", "Export Manifest");


	//Verification of Weight
	String locatorWt= xls_Read.getCellValue(sheetName, "htmlDiv_weight_eawb;xpath").replace("*", awbNumber);
	String locatorWt2= xls_Read.getCellValue(sheetName, "htmlDiv_weight;xpath").replace("*", awbNumber);
	try
	{
		acttext=driver.findElement(By.xpath(locatorWt)).getText();
	}
	catch(Exception e)
	{
		acttext=driver.findElement(By.xpath(locatorWt2)).getText();
	}

	verifyScreenTextWithExactMatch(sheetName, weight,acttext, "verifying weight", "Export Manifest");


}
/**
 * @author A-9175
 * Description : Add new barrow with awb
 * @param uldNo
 * @param POU
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void addNewBarrowWithAWB(String uldNo,String pou,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException
{
	waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
	clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
	waitForSync(2);
	clickWebElement(sheetName, "chk_barrow;xpath", "Barrow checkbox", screenID);
	waitForSync(2);
	waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
	enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
	waitForSync(2);
	performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
	waitForSync(2);
	handleNewULDWarning();
	/***************************************************************************/
	/**Method for selecting POU when pou is not autopopulated***/
	selectPOU(pou);
	/***************************************************************************/
	
	enterBuildUpLocation();
	
	//Add AWB Number
	addAWBDetails(prefix,awbNumber,pcs,wt);
	try {

		String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
		String actText=driver.findElement(By.xpath(popUp)).getText();
		if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
		{
			waitForSync(3);  
			clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
			waitForSync(5); 
			writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
		}
		/***************************************************/
		else if (actText.contains("do not have a booking"))
		{
			waitForSync(3);  
			clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
			waitForSync(5); 
			writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");

		}
		/***************************************************/
		else
		{
			writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
		}

	} catch (Exception e) {

	}
	waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

	String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
	if(driver.findElements(By.xpath(closeBtn)).size()==1)
	{
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
	}
}
/**
 * Desc : Enter Location and AWB details 
 * @author A-9844
 * @param buildUpLocation
 * @throws InterruptedException
 * @throws IOException
 */
public void captureLocationAndAWBDetails(String buildUpLocation,String prefix,String awbNumber,String pcs,String wt,String vol) throws InterruptedException, IOException 
{
	//Location
	enterValueInTextbox(sheetName, "inbx_buildupLocation;xpath", data(buildUpLocation), "build up location", screenID);
	//awb details
	enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
	enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);
	waitForSync(1);
	performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
	waitForSync(1);
	waitTillScreenload(sheetName, "inbx_awbPieces;name", "Pieces", screenID);
	enterValueInTextbox(sheetName, "inbx_awbPieces;name", data(pcs), "Pieces", screenID);
	enterValueInTextbox(sheetName, "inbx_awbWeight;name", data(wt), "Weight", screenID);
	enterValueInTextbox(sheetName, "inbx_awbVolume;name", data(vol), "Volume", screenID);
	clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
	waitForSync(2);
	waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
	String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
	if(driver.findElements(By.xpath(closeBtn)).size()==1)
	{
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
	}
}

/**
 * Desc : maximize all Details
 * @author A-10328
   * @throws AWTException
 * @throws InterruptedException
 * @throws IOException

 */


public void maximizeAllDetails(String uldNumber) throws InterruptedException, AWTException, IOException 
{
waitForSync(5);
String locator = xls_Read.getCellValue(sheetName, "btn_maximizeAllDetails;xpath").replace("*", uldNumber);
System.out.println(locator);


try
{
	driver.findElement(By.xpath(locator)).click();
	waitForSync(5);

}
catch(Exception e)
{
	writeExtent("Fail","Uld details can't be maximized on "+screenName);
}


}


	/**
	 * @Desc: Finalize the flight
	 * @param waitReq
	 * @throws InterruptedException
	 */
	public void finalizeFlightforMultipleAwbs(boolean waitReq) throws InterruptedException
	{
		if(waitReq)
		{

			clickWebElementByWebDriver(sheetName, "btn_finalizeFlight;id", "Finalize flight button", screenID);
			waitForSync(3);
			clickWebElementByWebDriver(sheetName, "btn_ATAOk;xpath", "ATD button OK", screenID);
			waitForSync(2);
			String currdate=createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
			enterValueInTextbox(sheetName, "inbx_actualDate;name", currdate, "actualDate", screenID);
			waitForSync(1);
			enterValueInTextbox(sheetName, "inbx_ATA;name", "00:00", "ATD", screenID);
			waitForSync(3);
			clickWebElementByWebDriver(sheetName, "btn_ATApopupsave;xpath", "ATD pop up Save", screenID);
			waitForSync(2);
		}

		else
		{
			clickWebElementByWebDriver(sheetName, "btn_finalizeFlight;id", "Finalize flight button", screenID);
			waitForSync(2);
			clickWebElementByWebDriver(sheetName, "btn_ATAOk;xpath", "ATD button OK", screenID);
			waitForSync(1);
			String currdate=createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
			enterValueInTextbox(sheetName, "inbx_actualDate;name", currdate, "actualDate", screenID);
			waitForSync(1);
			enterValueInTextbox(sheetName, "inbx_ATA;name", "00:00", "ATD", screenID);
			clickWebElementByWebDriver(sheetName, "btn_ATApopupsave;xpath", "ATD pop up Save", screenID);
			waitForSync(1);
		}
	}
	/**
	 * Desc : adding new ULD/Bulk without close button
	 * @author A-9175
	 * @param uldNo
	 * @param pou
	 * @param prefix
	 * @param awbNumber
	 * @param pcs
	 * @param wt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addNewULDWithAWBDFetails(String uldNo,String pou,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException
	{

		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		/***************************************************************************/
		/**Method for selecting POU when pou is not autopopulated***/
		selectPOU(pou);
		//Add AWB Number
		
		addAWBDetails(prefix,awbNumber,pcs,wt);
		try {

			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");

			}
			/***************************************************/
			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
			}

		} catch (Exception e) {

		}
	}

/**
	 * @author A-10690
	 * Description : Adding awb to the same uld without selecting close button after save
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException 
	 * @throws AWTException
	 */
	
	public void addAWBstoExistingULDwithPcsWeight(String uldNo,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException

	{
		String locator=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(locator)).size()!=1)
		{
			waitForSync(2);
			clickEditULDdetailsByJS(uldNo);

		}

              addAWBDetails(prefix,awbNumber,pcs,wt);
		
		try {

			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");

			}
			/***************************************************/
			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
			}
		} catch (Exception e) {

		}

		 
	}


	/**
	 * @author A-8783
	 * Desc -  add another awb to an existing uld with pieces and weight
	 * @param uldNo
	 * @param prefix
	 * @param awbNumber
	 * @param pcs
	 * @param wt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
		public void addAWBtoExistingULDwithPcsWeight(String uldNo,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException

		{
			waitTillScreenload(sheetName, "inbx_shipmentPrefix;name", "awb prefix", screenID);
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
			enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

			waitForSync(1);
			performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
			waitForSync(2);
			waitTillScreenload(sheetName, "inbx_awbPieces;name", "Pieces", screenID);
			enterValueInTextbox(sheetName, "inbx_awbPieces;name", data(pcs), "Pieces", screenID);
			enterValueInTextbox(sheetName, "inbx_awbWeight;name", data(wt), "Weight", screenID);
			waitForSync(2);
			clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
			waitForSync(2);
			waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
			try {
				
				String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
				String actText=driver.findElement(By.xpath(popUp)).getText();
				if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
				}
				/***************************************************/
				else if (actText.contains("do not have a booking"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
					
				}
				/***************************************************/
				else
				{
					writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
				}
			} catch (Exception e) {
				
			}
			waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

			String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
			if(driver.findElements(By.xpath(closeBtn)).size()==1)
			{
				clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
			}
		}

	       /**@author A-10328
		 * Description - Verify SCC for the AWB in the Load plan 
		 * @param AWBNo
		 * @param SCC
*/
	
	


		public void verifySCC(String AWBNo,String[] SCC)

		{

			String locator=xls_Read.getCellValue(sheetName, "btn_maximize;xpath");
			locator=locator.replace("*",data(AWBNo));
			driver.findElement(By.xpath(locator)).click();

			try{
				String SCCValue=xls_Read.getCellValue(sheetName, "txt_SCC;xpath");
				SCCValue=SCCValue.replace("*",data(AWBNo));
				String actText=driver.findElement(By.xpath(SCCValue)).getText();
				String actScc=actText.trim();
				int size=SCC.length;
				for(int i=0;i<size;i++) {
					verifyScreenText(sheetName,SCC[i], actScc,"verified SCC", "verified SCC in Export Manifest");
				}
			}


			catch(Exception e)
			{

			}
		}

	/**
	 * @description : adding new uld with awb
	 * @author A-9175
	 * @param uldNo
	 * @param prefix
	 * @param awbNumber
	 * @param pcs
	 * @param wt
	 * @param pou
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addAWBwithULD(String uldNo,String prefix,String awbNumber,String pcs,String wt,String pou) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(3);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		waitTillScreenload(sheetName, "inbx_shipmentPrefix;name", "awb prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

		waitForSync(1);
		performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
		waitForSync(1);
		waitTillScreenload(sheetName, "inbx_awbPieces;name", "Pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_awbPieces;name", data(pcs), "Pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_awbWeight;name", data(wt), "Weight", screenID);
		waitForSync(2);
		
		/********************************************/
		clickWebElementByWebDriver(sheetName, "lst_POU;xpath", "List POU", screenID);
		waitForSync(2);
		try {
			String locator=xls_Read.getCellValue(sheetName, "lst_POUIndex;xpath");
			locator=locator.replace("POUIndex", pou);
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", " POU Selected "+ screenID + " Page");
		} catch (Exception e) {
			writeExtent("Fail", " POU Not Selected "+ screenID + " Page");
		}
		waitForSync(2); 
		//Enter BuildUp Location
		enterBuildUpLocation();

		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		waitForSync(5);

	}
	/* @author A-9844
	 * Description : Add Barrow
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addBarrow(String uldNo,String pou) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "chk_barrow;xpath", "Barrow checkbox", screenID);
		clickWebElement(sheetName, "chk_barrow;xpath", "Barrow checkbox", screenID);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		
		/********************************************************************/


		/**Method for selecting POU when pou is not autopopulated***/
		selectPOU(pou);
		waitForSync(1);

		//Enter Buildup location
		enterBuildUpLocation();
		/*********************************************************************/

		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		waitForSync(2);


		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}



	}
	
	// To generate ULD no in format - CM105691
			public String create_uld_number_cart(String uldtype) {

				String randStr = "";

				try {

					String randomNum_length = "6";
					int digit = Integer.parseInt(randomNum_length);
					long value1 = 1;
					long value2 = 9;

					for (int i = 1; i < digit; i++) {
						value1 = value1 * 10;
						value2 = value2 * 10;
					}

					Long randomlong = (long) (value1 + Math.random() * value2);

					randStr = randomlong.toString();

				
					
					if(data(uldtype).equals("CM"))
						randStr = data(uldtype) + randStr;

					writeExtent("Pass", "ULD number is generated " + randStr);
					System.out.println("ULD number is generated " + randStr);

				}

				catch (Exception e) {
					System.out.println("ULD number could not be generated");
					test.log(LogStatus.FAIL, "ULD number could not be generated");

				}
				return randStr;
			}

	/**
	 * @author A-10690
	 * Description : offload awb
	 * @param ofldPcs
	 * @param OfldWt
	 * @param offload reason
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void offloadAwb(String ofldPcs,String OfldWt,String scc) throws InterruptedException, AWTException
	{
		
		offloadAwb(ofldPcs,OfldWt);
		//Select ULD Type
		clickWebElementByWebDriver(sheetName, "dpdwn_selectScc;xpath", "select scc", screenID);
		waitForSync(2);
		String scclocator=xls_Read.getCellValue(sheetName, "div_sccselect;xpath").replace("*", data(scc));
		waitForSync(3);
		driver.findElement(By.xpath(scclocator)).click();
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "btn_offloadsccok;xpath", "ok button", screenID);
		waitForSync(6);

	}
	/**
	 * @author A-10690
	 * Description  : Clicks on Build up complete for TCONs
	 * @throws InterruptedException
	 * @throws IOException 
	 * 
	 */
	public void clickBuildUpCompleteWithBarrow() throws InterruptedException, IOException
	{
		waitForSync(3);
		enterActualweightForTcon();
		waitTillScreenload(sheetName, "btn_BuildUpComplete;xpath","Build up complete button", screenName);
		clickWebElementByWebDriver(sheetName, "btn_BuildUpComplete;xpath", "Build up complete button", screenID);
		waitForSync(5);

	}

/**
	 * @author A-10690
	 * Description : enter actual weight for TCONS
	 * @param uld
	 * @throws InterruptedException,IOException
	 */
	public void enterActualweightForTcon() throws InterruptedException, IOException
	{
		waitForSync(4);
		try{

			String locator = xls_Read.getCellValue(sheetName, "txt_actualweight;xpath");
			if(driver.findElements(By.xpath(locator)).size()!=1)
			{
				String locator1 = xls_Read.getCellValue(sheetName, "txt_uldnumber;xpath");
				String uld=driver.findElement(By.xpath(locator1)).getText();
				map.put("ULDNO", uld);
				clickEditULDdetails("ULDNO");
				clickMoreUldDetails();

			}
			waitForSync(1);
			String Wght=driver.findElement(By.xpath(locator)).getText();


			map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight"));


			int newWeight=(Integer.parseInt(data("Extraweight")));
			int actweight=(Integer.parseInt(Wght))+newWeight;
			String actwght=String.valueOf(actweight);
			map.put("actwght", actwght);


			captureMoreUldDetails("actwght","val~80");
		}

		catch(Exception e)
		{
			System.out.println("Failed to enter more uld details in " +screenName);
		}


	}

	/**
	 * @author A-10690
	 * Description : Add AWB details
	 * @param prefix
	 * @param awbNumber
	 * @param pcs
	 * @param wt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addAWBDetails(String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException
	{
		//Add AWB Number
				waitTillScreenload(sheetName, "inbx_shipmentPrefix;name", "awb prefix", screenID);
				enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
				enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

				waitForSync(2);
				performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
				waitTillScreenload(sheetName, "inbx_awbPieces;name", "Pieces", screenID);
				enterValueInTextbox(sheetName, "inbx_awbPieces;name", data(pcs), "Pieces", screenID);
				enterValueInTextbox(sheetName, "inbx_awbWeight;name", data(wt), "Weight", screenID);
				keyPress("TAB");
				waitForSync(2);
		       clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
				waitForSync(1);
				waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
	}
	/**
	 * Desc : Adding AWB details with  HAWB
	 * @author A-10690
	 * @param prefix
	 * @param awbNumber
	 * @param hawb
	 * @param pcs
	 * @param wt
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addAWBDetailswithHAWB(String prefix, String awbNumber,boolean hawb,String pcs,String wt) throws InterruptedException, IOException 
	{
		if(hawb)
		{
			waitTillScreenload(sheetName, "inbx_shipmentPrefix;name", "awb prefix", screenID);
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
			enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

			waitForSync(1);
			performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
			waitForSync(2);
			waitTillScreenload(sheetName, "inbx_awbPieces;name", "Pieces", screenID);
			enterValueInTextbox(sheetName, "inbx_awbPieces;name", data(pcs), "Pieces", screenID);
			enterValueInTextbox(sheetName, "inbx_awbWeight;name", data(wt), "Weight", screenID);
			waitForSync(2);

			waitForSync(2);
			clickMoreAWBDetails();
			clickWebElement(sheetName, "btn_HAWB;xpath", "Button HAWB", screenID);
			String locator=xls_Read.getCellValue(sheetName, "btn_HAWB;xpath");
			try {
				locator=locator.replace("house",data("HAWB"));
				if(driver.findElement(By.xpath(locator)).isDisplayed())
				{
					waitForSync(2);
					writeExtent("Pass", "House is displayed as : "+ data("HAWB")+" on "+ screenID + " Page");
					clickWebElement(sheetName, "btn_selectHAWB;xpath", "select hawb ", screenID);
					clickWebElement(sheetName, "btn_HAWBok;xpath", "Ok Button", screenID);
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
					waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
				}
			} catch (Exception e) {
				writeExtent("Fail", "House is Not displayed as : "+ data("HAWB")+" on "+ screenID + " Page");
			}

		}
		else
		{
			writeExtent("Fail", "House is Not displayed as : "+ data("HAWB")+" on "+ screenID + " Page");
		
			
		}
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}
 



	}
/**
	 * @description : Applying fliter with awb number
	 * @author A-9175
	 * @param prefix
	 * @param awbNumber
	 * @throws InterruptedException
	 * @throws IOException
 * @throws AWTException 
	 */
	public void applyFilter(String prefix,String awbNumber) throws InterruptedException, IOException, AWTException
	{
		clickWebElement(sheetName, "btn_shipfilter;xpath", "Add Filter", screenID);
		waitForSync(3);
		clickWebElement(sheetName, "btn_clearFilter;xpath", "Clear Filter", screenID);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_prefixForFilter;name", data(prefix), "awb prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_documentNumberFilter;name", data(awbNumber), "awb number", screenID);
		waitForSync(1);
		clearText(sheetName, "inbx_carrierCodefilter;name", "Carrier Code", screenName);
        keyPress("TAB");
		clickWebElement(sheetName, "btn_applyFilter;xpath", "Apply Filter", screenID);
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Description : handle the popup - 'ULD is marked as Buildup Complete. Do you want to proceed with re-opening?'
	 * @param status
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void handlePopup() throws InterruptedException, IOException
	{

		String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
		int size=driver.findElements(By.xpath(popUp)).size();

		if(size==1){
			String actText=driver.findElement(By.xpath(popUp)).getText();

			if (actText.contains("is marked as Buildup Complete. Do you want to proceed with re-opening?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Pass", "Accepted Popup with text as: " + actText+ " on "+ screenID + " Page");
			}

			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" on " + screenID + " Page");
			}
		}
	}

	/**
	  * @author A-8783
	  * Desc - Add AWB details with HAWB
	  * @param prefix
	  * @param awbNumber
	  * @param pcs
	  * @param wt
	  * @throws InterruptedException
	  * @throws IOException
	  */
	public void addAWBDetailsWithHAWB(String prefix, String awbNumber,String pcs, String wt) throws InterruptedException, IOException 
	{
		waitTillScreenload(sheetName, "inbx_shipmentPrefix;name", "awb prefix", screenID);
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

		waitForSync(1);
		performKeyActions(sheetName, "inbx_documentNumber;name", "TAB", "awb number", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "inbx_awbPieces;name", "Pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_awbPieces;name", data(pcs), "Pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_awbWeight;name", data(wt), "Weight", screenID);
		waitForSync(2);
		clickMoreAWBDetails();
		clickWebElement(sheetName, "btn_HAWB;xpath", "Button HAWB", screenID);

		clickWebElement(sheetName, "chk_selectAllHawb;xpath", "Select All", screenID);
		clickWebElement(sheetName, "btn_HAWBOk;xpath", "Ok Button", screenID);
		clickWebElement(sheetName, "btn_OKHawb;xpath", "Ok Button", screenID);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(3);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 

			
	}

	/**
	 * @description : Clicking on Lying list tab
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickLyingList() throws InterruptedException, IOException
	{
		//Enter the awbNumber
		clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
		waitForSync(2);
	}

	/**
	 * @Desc: Verify shipments in the assigned list using uld
	 * @param uldNo
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc:verifyShipmentFromAssignedListUsingULD
	 */
	public void verifyShipmentFromAssignedListUsingULD(String uldNo) throws InterruptedException, IOException {

		try {
			enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(uldNo), "ULD Number", screenID);

			String locator = xls_Read.getCellValue(sheetName,"lbl_uldInAssignedShipment;xpath");
			locator = locator.replace("uldNo", data(uldNo));
			if (driver.findElement(By.xpath(locator)).isDisplayed())
				writeExtent("Pass", "ULD number " + data(uldNo) + "exists in " + screenID + " Page");
			else
				writeExtent("Fail", "ULD number " + data(uldNo) + "Doesnt exists in " + screenID + " Page");
			waitForSync(2);
		} catch (Exception e) {
			writeExtent("Fail", "Element not found in" + screenID + " Page");
		}
	}
	
	/**
	 * @author A-6260
	 * Desc.. Enter pieces to be checked out
	 * @param value
	 * @param Pieces
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterPiecesToBeCheckedOut(int value, String Pieces) throws InterruptedException, IOException {
		waitForSync(2);
		switchToFrame("frameName","popupContainerFrame");
		String locator = xls_Read.getCellValue(sheetName, "inbx_transactionPieces;id");
		locator=locator.replace("*", Integer.toString(value));
		driver.findElement(By.id(locator)).sendKeys(data(Pieces));
		clickWebElement(sheetName, "btn_OK;id", "ok button", screenID);
		waitForSync(5);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR344");
		
		
	}
	/**
	 * @author A-6260
	 * Desc..verify error message
	 * @param expMsg
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyErrorMessage(String expMsg) throws InterruptedException, IOException
	{
		waitForSync(3);
		getTextAndVerify("Generic_Elements", "htmlDiv_errorMsg;xpath","Error Msg",screenID, "Verify Error Msg", data(expMsg), "contains");

	}
	/**
	 * @author A-6260
	 * Desc.. verify build up complete
	 * @param uld
	 * @throws InterruptedException
	 */
	public void verifyBuildUpComplete(String uldNum) throws InterruptedException
	{
		try{
			waitForSync(2);
			String locator = xls_Read.getCellValue(sheetName, "txt_uld;xpath");
			locator=locator.replace("uld",data(uldNum));
			if(driver.findElement(By.xpath(locator)).getAttribute("data-buildupcompleteflag").equalsIgnoreCase("true")){
				writeExtent("Pass", "Build up completed for uld- "+data(uldNum)+" in" + screenID + " Page");
			}
			else {
				writeExtent("Fail", "Build up not completed for uld- "+data(uldNum)+" in" + screenID + " Page");
			}}
		catch (Exception e) {

			writeExtent("Fail", "Could not verify Build up status in "+ screenID + " Page");
		}
	}
	/**
	 * @Desc: Check the shipment in the Lying list after assigning using uld
	 * @param uldNo
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc:verifyShipmentFromLyingListAfterAssigningUsingULD
	 */
	public void verifyShipmentFromLyingListAfterAssigningUsingULD(String uldNo)
			throws InterruptedException, IOException {
		try {
			clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
			waitForSync(3);
			clickWebElement(sheetName, "btn_UldsList;xpath", "ULDs to be assigned", screenID);
			waitForSync(2);
			enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(uldNo), "ULD Number", screenID);
			waitForSync(2);
			String xpath = xls_Read.getCellValue(sheetName, "btn_ULD_Record;xpath");
			verifyElementDisplayed(xpath, "Verification of ULD number in lying list", screenName, "build up");
			if (driver.findElement(By.xpath(xpath)).isDisplayed())
				writeExtent("Pass", "The ULD number " + data(uldNo) + " is not found " + screenID + " Page");
			else
				writeExtent("Fail", "ULD number " + data(uldNo) + "are existing " + screenID + " Page");

		} catch (Exception e) {
			writeExtent("Fail", "Element not found in " + screenID + " Page");
		}
	}
	
	
	/**
	 * @Desc: Check the shipment in the Lying list after assigning using awb
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 * @Desc : verifyShipmentFromLyingListAfterAssigningUsingAWB
	 */
	public void verifyShipmentFromLyingListAfterAssigningUsingAWB(String awbNo)
			throws InterruptedException, IOException {
		try {

			clickWebElement(sheetName, "btn_AWB;xpath", "AWB(s)", screenID);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(awbNo), "Awb No", screenID);
			waitForSync(2);
			waitForSync(2);
			String xpath = xls_Read.getCellValue(sheetName, "btn_ULD_Record;xpath");
			verifyElementDisplayed(xpath, "Verification of AWB number in lying list", screenName, "build up");
			if (driver.findElement(By.xpath(xpath)).isDisplayed())
				writeExtent("Pass", "The AWB number " + data(awbNo) + " is not found " + screenID + " Page");
			else
				writeExtent("Fail", "AWB number " + data(awbNo) + "are existing " + screenID + " Page");


		} catch (Exception e) {
			writeExtent("Fail", "Element not found in " + screenID + " Page");
		}
	}
	/**@author A-9844
	 * @throws IOException, InvalidPasswordException
	 * @param fileName
	 * @param expectedValues
	 * @Desc: verifying values are not displayed in the report downloaded
	 */

	public void extractPDFContentAndCompareContentsNotPresent(String fileName,String...expectedValues) throws InvalidPasswordException, IOException
	{

		String filePath=System.getProperty("user.dir")+"\\src\\resources\\Downloads\\";
		try (PDDocument document = PDDocument.load(new File(filePath+fileName)))

		{

			document.getClass();

			if (!document.isEncrypted()) 

			{

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(document);


				for (String s  : expectedValues)

				{

					if(!(pdfFileInText.contains(s)))
					{
						writeExtent("Pass", "Verified the content "+ s + " is not present on " +fileName+" on "+ screenID);
					}
					else
					{
						writeExtent("Fail", "Content "+s +" are  present on "+fileName+" report : " + s + " on " + screenID);

					}
				}
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not extract the data from the document "+fileName);

		}
	}
	/**
	 * @author A-9847
	 * @Desc - To verify the actual weight displayed
	 * @param actualWeight
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyActualULDWeight(String actualWeight) throws InterruptedException, IOException {
		try{
		waitForSync(2);
		String ActULDWgt = getAttributeWebElement(sheetName,"inbx_actualWgt;name", "Actual ULD weight", "value", screenName);
		System.out.println(ActULDWgt);
		verifyScreenTextWithExactMatch(sheetName, data(actualWeight), ActULDWgt,"Verification of Actual ULD Weight", screenName);
		waitForSync(2);     
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		
		}
	catch(Exception e){
		
		writeExtent("Fail", "Failed to verify the Actual Weight on " + screenID);
	}
	}
	/**
	 * @author A-7943
	 * Description: Split and assign awb from planned list to the bulk
	 * @param awbNo,pieces, weight
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void splitShipmentFromPlannedList(String awbNo, String pieces, String weight)
			throws InterruptedException, AWTException, IOException {
		enterValueInTextbox(sheetName, "inbx_KeywordPlanningList;xpath", data(awbNo), "Awb No", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_splitAndAssign_PlannedSection;xpath",
				"Split and assign icon in the planned shipment", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_splitAndAssign_PlannedSectionAfterClick;xpath",
				"Split and assign button in the planned shipment after clicking three dots", screenID);
		waitTillScreenload(sheetName, "inbx_splitPieces;xpath","split pieces text box", screenName);
		enterValueInTextbox(sheetName, "inbx_splitPieces;xpath", data(pieces), "Pieces", screenID);
		performKeyActions(sheetName,"inbx_splitPieces;xpath", "TAB","Split Pieces", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok button", screenID);
		waitForSync(2);

	}
	
	
	/**
	 * @author A-9175
	 * Desc : Verifying number of shipment count in planned section
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */

	public void verifySplitShipmentCount(String awbNo)
			throws InterruptedException, AWTException, IOException {
		try {
			List<WebElement> count=new ArrayList<WebElement>();
			enterValueInTextbox(sheetName, "inbx_placeholderText;xpath", data(awbNo), "Awb No", screenID);
			String locator=xls_Read.getCellValue(sheetName, "lbl_plannedShipments;xpath");
			locator=locator.replace("awbNo", data(awbNo));
			count=driver.findElements(By.xpath(locator));
			writeExtent("Pass", "Shipment count displayed in Planned Section Tab is " + count.size());
		} catch (Exception e) {
			writeExtent("Fail", data(awbNo)+" Not Found in Planned Section"+ screenID + " Page");
		}
	}
	

	/**
	 * @author A-9175
	 *  Desc : Select and assign split shipment from planned section
	 * @param awbNo
	 * @param index
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void selectSplitShipmentAndAssign(String awbNo,String index)
			throws InterruptedException, AWTException, IOException {
		waitTillScreenload(sheetName, "inbx_KeywordPlanningList;xpath","search shipment textbox", screenName);
		enterValueInTextbox(sheetName, "inbx_KeywordPlanningList;xpath", data(awbNo), "Awb No", screenID);
		waitForSync(2);
		try {
			String locator=xls_Read.getCellValue(sheetName, "chk_splitShipment;xpath");
			locator=locator.replace("pos", index);
			WebElement e=driver.findElement(By.xpath(locator));
			if(e.isDisplayed())
			{
				e.click();
				writeExtent("Pass", data(awbNo) + index + "Selected"+ screenID + " Page");
			}
			else
			{
				writeExtent("Fail", data(awbNo) + index + "Not Selected"+ screenID + " Page");
			}

		} catch (Exception e) {
			writeExtent("Fail", data(awbNo) + index + "Not Found"+ screenID + " Page");
		}

	}
	

	/**
	 * @author A-7943
	 * Description : Verify the shipment in the assigned shipment using AWB
	 * @param awbNo
	 * @throws InterruptedException      
	 * @throws IOException
	 */
	public void verifyShipmentFromAssignedListUsingAWB(String awbNo) throws InterruptedException, IOException {
		
		try {
			clickWebElement(sheetName, "btn_downArrowInAssigned;xpath","down arrow button in bulk of assigned shipment", screenID);
			String locator = xls_Read.getCellValue(sheetName, "lbl_awbInAssignUnderBulk;xpath");
			locator = locator.replace("awbNo", data(awbNo));
			if (driver.findElement(By.xpath(locator)).isDisplayed())
				writeExtent("Pass", "AWB number " + data(awbNo) + "exists in " + screenID + " Page");
			else
				writeExtent("Fail", "AWB number " + data(awbNo) + "Doesnt exists in " + screenID + " Page");
			waitForSync(2);
		} catch (Exception e) {
			writeExtent("Fail", "Element not found in" + screenID + " Page");
		}
	}


	/**
	 * @author A-7943
	 * Description : Selecting the shipment from the Lying list
	 * @param awbNo
	 * @throws InterruptedException          
	 * @throws IOException
	 */
	public void selectShipmentFromLyingList(String awbNo) throws InterruptedException, IOException {

		clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
		waitForSync(3);
		clickWebElement(sheetName, "btn_AWB;xpath", "AWB(s)", screenID);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(awbNo), "Awb No", screenID);
		waitForSync(3);
		getTextAndVerify(sheetName, "htmlDiv_lnkAwbNumber;xpath", "awb number", screenID,
				"Verification of awb number in lying list", data(awbNo), "contains");
		clickWebElement(sheetName, "chkBox_awbNumberLyingList;name", "Lying List Check Box", screenID);

	}
	

	/**
	 * @author A-9175
	 * Desc : Verifying shipment under planned section
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyShipmentInPlannedSection(String awbNo) throws InterruptedException, IOException
	{
		try {
			enterValueInTextbox(sheetName, "inbx_placeholderText;xpath", data(awbNo), "Awb No", screenID);
			String locator=xls_Read.getCellValue(sheetName, "lbl_plannedShipments;xpath");
			locator=locator.replace("awbNo", data(awbNo));
			waitForSync(3);
			if(driver.findElement(By.xpath(locator)).isDisplayed())
				writeExtent("Pass", "AWB number "+data(awbNo)+ "exists in " + screenID + " Page");
			else
				writeExtent("Fail", "AWB number "+data(awbNo)+ "Doesnt exists in " + screenID + " Page");
			waitForSync(2);
		} catch (Exception e) {
			writeExtent("Fail", "Element not found in" + screenID + " Page");
		}


	}
	

	/**
	 * @author A-9175
	 * Desc: Verifying BDP Button status
	 * @param expText
	 * @throws InterruptedException
	 */
	public void verifyBDPbuttonStatus(String status) throws InterruptedException
	{
		if(status.equals("Open"))
		{
			String xpath = xls_Read.getCellValue(sheetName, "btn_openFlight;xpath");
			verifyElementDisplayed(xpath, "Verify if the flight is opened for build up ",screenName,"Open flight for build up");
		}
		else
		{
			String xpath = xls_Read.getCellValue(sheetName, "btn_closeFlight;xpath");
			verifyElementDisplayed(xpath, " Verify if the flight is reopened ",screenName,"Close flight for build up");
		}

	}
	/**
	  @author A-8783
	 * Description : Add new ULD with awb and handle DG/SL popup
      * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addNewULDWithAWBSplitShipment(String uldNo,String pou,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(2);
		handleNewULDWarning();
		/***************************************************************************/
		/**Method for selecting POU when pou is not autopopulated***/
		selectPOU(pou);
		/***************************************************************************/

		//Enter Buildup location
		enterBuildUpLocation();

		//Add AWB Number
		addAWBDetails(prefix,awbNumber,pcs,wt);
		try {
			assignDGSL();
			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
			}

		} catch (Exception e) {

		}
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}	
	}
	

	/**
		  @author A-8783
		 * Description : Add new ULD with awb and handle split locations popup
	    * @param uldNo
		 * @param POU
		 * @throws InterruptedException
		 * @throws AWTException
		 * @throws IOException 
		 */
		public void assignSplitShipmenttoULD(String uldNo,String pou,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException
		{
			waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
			clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
			waitForSync(2);
			waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
			enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
			waitForSync(2);
			performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
			waitForSync(2);
			handleNewULDWarning();
			/***************************************************************************/
			/**Method for selecting POU when pou is not autopopulated***/
			selectPOU(pou);
			/***************************************************************************/
			//Add AWB Number
			addAWBDetails(prefix,awbNumber,pcs,wt);
			try {
				enterPcsForSplitAWB(pcs);
				assignDGSL();
				String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
				String actText=driver.findElement(By.xpath(popUp)).getText();
				if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
				}
				/***************************************************/
				else if (actText.contains("do not have a booking"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");

				}
				/***************************************************/
				else
				{
					writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
				}

			} catch (Exception e) {

			}
			waitForSync(2);
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 



		}

	/**
	 * @author A-8783
	 * Desc - Handle assign DG/SL popup
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void assignDGSL() throws InterruptedException, IOException{
		
       String locator=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
		
		waitTillScreenloadWithOutAssertion(sheetName, "lbl_popUp;xpath", "DG/SL popup",	screenName, 6);
		
		int size=driver.findElements(By.xpath(locator)).size();
		
		if(size>=1)
		{
		


		String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
		String actText=driver.findElement(By.xpath(popUp)).getText();
		if (actText.contains("Do you want to assign DG/SL details?"))
		{
			waitForSync(2);  
			clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
			waitForSync(2);
			switchToFrame("frameName","popupContainerFrame");
			waitTillScreenload(sheetName, "btn_dgSlClear;xpath", "Clear button",screenID );
			try{
				String pcsInUld = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "inbx_pcsUld;xpath"))).getText();
			if(pcsInUld.isEmpty()){
				enterValueInTextbox(sheetName, "inbx_pcsUld;xpath", data("val~1"), "Pieces in ULD", screenID);
			}
			}
			catch (Exception e){
				
			}
			clickWebElement(sheetName, "btn_dgSlSave;xpath", "Save Button", screenID);
			switchToFrame("default");
			switchToFrame("contentFrame","OPR344");
			writeExtent("Info", "Sucessfully Accepted UNID/SL Popup " + screenID + " Page");
		}
		else
		{
			writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
		}

		}

	}



	
	/**
	 * @author A-9175
	 * Desc : Capture Breakdown instruction
	 * @param bdnIndex
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void captureBreakdownInstruction(String bdnIndex) throws InterruptedException, IOException 
	{
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "lst_BDN;xpath", "List BDN", screenID);
		waitForSync(2);
		try
		{
			String locator=xls_Read.getCellValue(sheetName, "lst_BDNIndex;xpath");
			locator=locator.replace("BDNIndex", bdnIndex);
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", "Breakdown instructions succesfully selected"+ screenID + " Page");
		}catch (Exception e) {
			writeExtent("Fail", "Breakdown instructions couldnt selected"+ screenID + " Page");
		}
		waitForSync(3);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}
	}
	

	/**
	 * Desc : Selecting shipment from Planned section
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 */
	public void clickShipemntFromPlannedSection(String awbNo) throws InterruptedException
	{
		try {
			String locator=xls_Read.getCellValue(sheetName, "chkBox_plannedshipment;xpath");
			locator=locator.replace("awbNo", data(awbNo));
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", "AWB number "+data(awbNo)+ "Selected in " + screenID + " Page");
			waitForSync(2);  
		} catch (Exception e) {
			writeExtent("Fail", "Shipment number "+data(awbNo)+ "Not Selected in " + screenID + " Page");
		}

	}
	/** 
	* @author A-9478
	* Description : Offload ULD
	* @throws InterruptedException
	* @throws AWTException
	 * @throws IOException 
	*/
	public void offloadULDAfterBuildupComplete(String ULD) throws InterruptedException, AWTException, IOException
	{
	String locator=xls_Read.getCellValue(sheetName, "btn_OffloadULD_BuilupComplete;xpath");
	locator=locator.replace("ULDNum", data(ULD));
	driver.findElement(By.xpath(locator)).click();
	waitForSync(2);
	handlePopup();
	clickWebElementByWebDriver(sheetName, "btn_offloadSave;xpath", "Offload Save", screenID);
	waitForSync(2);

	}

	
	/**
	 * Desc: Clicking Bulk
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBULK() throws InterruptedException, IOException
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_BULK;xpath", "BULK Button", screenID);
		waitForSync(2);
		try {
			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
				
			}
			/***************************************************/
			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
			}
			

		} catch (Exception e) {
			
		}		
	}
	/**
	 * Desc : adding a new AWB in existing ULD
	 * @author A-9175
	 * @param uldNo
	 * @param prefix
	 * @param awbNumber
	 * @param pcs
	 * @param wt
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addAWBtoExistingULD(String uldNo,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException
	{
		

		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();

		addAWBDetails(prefix,awbNumber,pcs,wt);
		try {
			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");

			}
			/***************************************************/


		} catch (Exception e) {

		}		
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		} 
		waitForSync(5);





	}



/**
	 * @author A-10690
	 * Desc..verify the block message displayed while doing build up
	 * @param expMsg
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyErrorcheck(String expMsg) throws InterruptedException, IOException
	{
		
		String locator=xls_Read.getCellValue(sheetName, "txt_errormsg;xpath");
		if(driver.findElements(By.xpath(locator)).size()==1)
		{
			clickWebElementByWebDriver(sheetName, "txt_errormsg;xpath", "error notification", screenID);
		}
		
		getTextAndVerify("Generic_Elements", "htmlDiv_errorMsg;xpath","Error Msg",screenID, "Verify Error Msg", data(expMsg), "contains");
		
		

	}

	/**
	 * Desc : Capture occupancy
	 * @author A-10690
	 * @param occupancy
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureoccupancy( String occupancy) throws InterruptedException, IOException 
	{
waitForSync(2);
		
		enterValueInTextbox(sheetName, "inbx_occupancy;name", data(occupancy), "Occupancy", screenID);
		enterValueInTextbox(sheetName, "inbx_remarks;name","Test Remarks", "Remarks", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}  
	}
	/**
	 * Desc : Verifying Pop up with expected text is displayed --> Generic
	 * @author A-9175
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyPopUpDisplayed(String expText) throws InterruptedException, IOException
	{
		try {
			waitForSync(2);
			getTextAndVerify(sheetName, "lbl_popUp;xpath", "PopUp Status", screenID, "Sucessfully found", data(expText), "equals");
			waitForSync(2);  
			clickWebElement(sheetName, "btn_cancel;xpath", "Cancel Button", screenID);
			waitForSync(3);
			writeExtent("Pass", "Sucessfully Cancelled Popup " + screenID + " Page");
		} catch (Exception e) {
			writeExtent("Fail", "Not Cancelled Popup " + screenID + " Page");
		}

	}
	
	/**
	 * @author A-10690
	 * Description : Selecting the shipment from the Lying list
	 * @throws InterruptedException          
	 * @throws IOException
	 */
	public void selectAWBcheckboxFromLyingList() throws InterruptedException, IOException 
	{
         waitForSync(3);
		clickWebElement(sheetName, "chkBox_awbNumberLyingList;name", "Lying List Check Box", screenID);

	}

	/**
	 * Desc : Verifying Pop up with expected text is not displayed --> Generic
	 * @author A-9175
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyPopUpNotDisplayed(String expText) throws InterruptedException, IOException
	{
		try {
			waitForSync(2);
			String locator=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			if(driver.findElement(By.xpath(locator)).isDisplayed())
			{
				clickWebElement(sheetName, "btn_cancel;xpath", "Cancel Button", screenID);
				writeExtent("Fail", "Popup Not expected in " + screenID + " Page");
			}
			else
				writeExtent("Pass", "Alert with Text "+ expText + screenID + "Not found Page");

		} catch (Exception e) {
			writeExtent("Pass", "Alert with Text "+ expText + screenID + "Not found Page");
		}

	}
	
	
	/**
	 * Desc : Clicking Reopen flight button
	 * @author A-9175
	 * @throws InterruptedException
	 */
	
		public void reOpenFlight() throws InterruptedException
		{
			try 
			{
				String locator=xls_Read.getCellValue(sheetName, "btn_finalizeFlight;id");
				waitForSync(3);
				driver.findElement(By.id(locator)).click();
				writeExtent("Pass", "Clicked on Finalize Flight " + screenID + " Page");
			}
			catch (Exception e)
			{
				String locator=xls_Read.getCellValue(sheetName, "btn_reopen_flight;id");
				waitForSync(3);
				driver.findElement(By.id(locator)).click();
				writeExtent("Pass", "Clicked on Reopen Flight " + screenID + " Page");
			}
			
		}

	
		

	      /**@author A-10328
		 * Description - Select Contour
		 * @param contour
		 */
		public void selectContour(String contour)
		
		{
				
		for(int i=0;i<2;i++)
		{
		/**checking whether contour selected by default***/
		String contour1=xls_Read.getCellValue(sheetName, "lbl_countourcheck;xpath");
		contour1=contour1.replace("*", data(contour));
		if(driver.findElements(By.xpath(contour1)).size()==1)
		{
			break;
		}
		String locator1=xls_Read.getCellValue(sheetName, "lbl_countour;xpath");
		driver.findElement(By.xpath(locator1)).click();
		String locator2=xls_Read.getCellValue(sheetName, "dpdwn_countourname;xpath");
		locator2=locator2.replace("*", data(contour));
		if(driver.findElements(By.xpath(locator2)).size()==1)
		{

		driver.findElement(By.xpath(locator2)).click();
		break;
		}
		}
		}

	
	
	/**
	 * @author A-9175
	 * Description : Add  ULD With existing awb
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addULDWithoutAWB(String uldNo,String pou) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		/********************************************************************/
		selectPOU(pou);
		//Select Contour 
		if(data("UldType").equals("PMC"))
		{
		selectContour("Contour");

		}
		//Enter BuildUp location 
		enterBuildUpLocation();


		/*********************************************************************/

		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		String locator=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(locator)).size()>=1)
		{

		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID);
		}

	}
	
	public void selectPOU(String pou) throws InterruptedException
	{
		String locatorPOU=xls_Read.getCellValue(sheetName, "htmlDiv_SelectPOU;xpath");
		String pouDisplayed=driver.findElement(By.xpath(locatorPOU)).getText();
		if(pouDisplayed.contains("Select..."))
		{
			clickWebElementByWebDriver(sheetName, "inbx_uldNum;id", "List POU", screenID);
			clickWebElementByWebDriver(sheetName, "lst_POU;xpath", "List POU", screenID);
			String locator=xls_Read.getCellValue(sheetName, "lst_POUIndex;xpath");
			locator=locator.replace("POUIndex", pou);
			driver.findElement(By.xpath(locator)).click();
			waitForSync(1);
		}
	}
	
	/**
	 * Desc : Verifying ULD in planned section
	 * @author A-9175
	 * @param uldno
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyULDInPlannedSection(String uldno) throws InterruptedException, IOException
	{
		try {
			enterValueInTextbox(sheetName, "inbx_placeholderText;xpath", data(uldno), "Uld/Awb No", screenID);
			String locator=xls_Read.getCellValue(sheetName, "lbl_plannedshipmentULD;xpath");
			locator=locator.replace("ULDno", data(uldno));
			if(driver.findElement(By.xpath(locator)).isDisplayed())
				writeExtent("Pass", "ULD/Awb number "+data(uldno)+ "exists in planned section" + screenID + " Page");
			else
				writeExtent("Fail", "ULD/Awb number "+data(uldno)+ "does not exist in planned section" + screenID + " Page");
			waitForSync(2);
		} catch (Exception e) {
			writeExtent("Fail", "Element not found in" + screenID + " Page");
		}
	}

	/**
	 * @author A-8783
	 * Desc - Verify special instaructions
	 * @param instruction
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifySpclInstr(String instruction) throws InterruptedException, IOException{
		clickWebElement(sheetName, "htmlDiv_instruction;xpath", "Special Instruction icon", screenID);
		getTextAndVerify(sheetName, "txt_spclInstr;xpath", "Special Instructions", screenID, "Verify Special Instruction", data(instruction), "equals");
	}

	/**
	 * Desc: assigning ULD shipments from planned section
	 * @author A-9175
	 * @param uldNo
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void assignUldPlanningSection(String uldNo) throws InterruptedException, AWTException, IOException
	{
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "chkBox_plannedshipmentULD;xpath");
		locator=locator.replace("ULDno", data(uldNo));
		System.out.println(locator);
		try{
			driver.findElement(By.xpath(locator)).click();
			waitForSync(4);
			writeExtent("Pass", "ULD number "+data(uldNo)+ "Clicked in " + screenID + " Page");
		}
		catch (Exception e) {
			writeExtent("Fail", "uld number "+data(uldNo)+ "Not Clicked in " + screenID + " Page");
		}
		waitForSync(2);
		clickWebElement(sheetName, "btn_AssignHere;xpath", "Assign Here", screenID);
		waitForSync(8);
		/***waitTillScreenload(sheetName, "btn_BuildUpComplete;xpath","Buil up Complete", screenName);****/
		try{
		String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath"); 		
		String actText=driver.findElement(By.xpath(popUp)).getText();
		if(driver.findElements(By.xpath(popUp)).size()==1 && actText.contains("Do you want to assign DG/SL details?"))

			assignDGSL();
		}
		catch(Exception e)
		{
			
		}

	}

	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click assign button
	 */
	public void clickAssignBtn() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_AssignHere;xpath", "Assign Here", screenID);
		waitForSync(5);
	}
	
	/**
	 * Desc :Clicking Edit Uld button
	 * @author A-9175
	 * @param uldNo
	 */
	public void clickEditULDdetails(String uldNo) {
		
		String locator=xls_Read.getCellValue(sheetName, "btn_editULD;xpath");
		locator=locator.replace("ULDno", data(uldNo));
		if(driver.findElements(By.xpath(locator)).size()==1)
		{
			try{
				driver.findElement(By.xpath(locator)).click();
				waitForSync(2);
				writeExtent("Pass", "Edit ULD Button Clicked in " + screenID + " Page");
			}
			catch (Exception e) {
				writeExtent("Fail", "Couldn't click on  Edit uld button  on " + screenID + " Page");
			}
		}
	}

	
	/**
	 * Desc : Verify and Accept Popup
	 * @author A-9175
	 * @param expText
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void acceptAlertPopUp(String expText) throws InterruptedException, AWTException, IOException 

	{
		try {
			waitForSync(2);
			getTextAndVerify(sheetName, "lbl_popUp;xpath", "PopUp Status", screenID, "Sucessfully found", data(expText), "equals");

			String actText = getElementText(sheetName, "lbl_popUp;xpath", "Popup Status", screenID);
			if(actText.equals("The ULD is build-up completed. Do you want to proceed?"))
			{

				verifyBuildupcompleteReopened();


			}


			else
			{

				waitForSync(2);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(3);
			}
			writeExtent("Pass", "Sucessfully Accepted Popup " + screenID + " Page");

		} catch (Exception e) {
			writeExtent("Fail", "Not Accepted Popup " + screenID + " Page");
		}

	}


	

	
	/**
	 * @author A-9175
	 * Desc : Verifying ULD displayed values under assigned section
	 */
	public void verifyULDValues() {
		
			try 
			{
				String uldText=getAttributeWebElement(sheetName, "inbx_uldnumber;name", "ULD Textbox","value", screenID);
				writeExtent("Pass", "ULD Number is displayed as : "+ uldText+ screenID + " Page");

				String locator1=xls_Read.getCellValue(sheetName, "inbx_POU;xpath");
				String POUText=driver.findElement(By.xpath(locator1)).getText();
				writeExtent("Pass", "POU is displayed as : "+ POUText+ screenID + " Page");

				String locator2=xls_Read.getCellValue(sheetName, "lbl_ULDdetails;xpath");
				String otherDetails=driver.findElement(By.xpath(locator2)).getText();
				writeExtent("Pass", "Other Details is displayed as : "+ otherDetails+ screenID + " Page");

				String locator3=xls_Read.getCellValue(sheetName, "lbl_countour;xpath");
				String contour=driver.findElement(By.xpath(locator3)).getAttribute("value");
				System.out.println(contour);
				
				if(contour==null)
				{
				 writeExtent("Pass", "No contours selected : "+screenID + " Page");
				}
				else
				{
					writeExtent("Pass", "contour is displayed as : "+ contour+ screenID + " Page");
				}
				
				
			} catch (Exception e) {
				writeExtent("Fail","Element Not Displayed"+ screenID + " Page");
			}

	} 
	
	
	/**
	 * Desc : Click More ULD Details
	 * @author A-9175
	 * @throws InterruptedException
	 */
	public void clickMoreUldDetails() throws InterruptedException
	{
		waitTillScreenload(sheetName, "btn_moreULDdetails;xpath","More ULD details button", screenName);
		clickWebElementByWebDriver(sheetName, "btn_moreULDdetails;xpath", "More ULD Details button", screenID);
		waitForSync(2);
	}

	
	/**
	 * Desc : Capture More ULD Details 
	 * @author A-9175
	 * @param actWgt
	 * @param occupancy
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureMoreUldDetails(String actWgt, String occupancy) throws InterruptedException, IOException 
	{
		
		//actWgt = gross +10

				waitForSync(2);
				enterValueInTextbox(sheetName, "inbx_actualWgt;name", data(actWgt), "Actual Weight", screenID);
				enterValueInTextbox(sheetName, "inbx_occupancy;name", "80", "Occupancy", screenID);
				enterValueInTextbox(sheetName, "inbx_remarks;name","Test Remarks", "Remarks", screenID);
				waitForSync(2);
				
				//Enter the buildup location if its not entered
				enterBuildUpLocationIfNotEntered();
				
				clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
				waitForSync(4);
				waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

				String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
				if(driver.findElements(By.xpath(closeBtn)).size()==1)
				{
					clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
				}
	}

	
	/**
	 * Desc : Adding AWB details with or without HAWB
	 * @author A-9175
	 * @param prefix
	 * @param awbNumber
	 * @param hawb
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addAWBDetails(String prefix, String awbNumber,boolean hawb) throws InterruptedException, IOException 
	{
		if(hawb)
		{
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
			enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

			waitForSync(1);
			performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
			waitForSync(2);
			clickMoreAWBDetails();
			clickWebElement(sheetName, "btn_HAWB;xpath", "Button HAWB", screenID);
			String locator=xls_Read.getCellValue(sheetName, "btn_HAWB;xpath");
			try {
				locator=locator.replace("house",data("HAWB"));
				if(driver.findElement(By.xpath(locator)).isDisplayed())
				{
					writeExtent("Pass", "House is displayed as : "+ data("HAWB")+" on "+ screenID + " Page");
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
				}
			} catch (Exception e) {
				writeExtent("Fail", "House is Not displayed as : "+ data("HAWB")+" on "+ screenID + " Page");
			}

		}
		else
		{
			enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
			enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

			waitForSync(1);
			performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
			waitForSync(2);

			clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		}
		
		try {
			waitForSync(3);
			String locator=xls_Read.getCellValue(sheetName, "lbl_selectSCC;xpath");
			String alertText=driver.findElement(By.xpath(locator)).getText();
			if(alertText.contains("Select SCC"))
			{
				
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok button", screenID);
				writeExtent("Pass", "Clicked ok for Alert Displayed : "+ alertText+" on "+ screenID + " Page");
				
			}
		} catch (Exception e) {
			
		}

	}
	
	
	/**
	 * Desc : Capture more breakdown instructions from assigned section
	 * @author A-9175
	 * @param bdnIndex
	 * @param carrCode
	 * @param flightNo
	 * @param fltDate
	 * @param des
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureMoreULDBreakdownDetails(String bdnIndex,String carrCode, String flightNo,String fltDate, String des) throws InterruptedException, IOException 
	{
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "lst_BDN;xpath", "List BDN", screenID);
		waitForSync(2);
		try
		{
		
			
			//Selecting THRU
			if(bdnIndex.contains("Index"))
			{
				int index=Integer.parseInt(bdnIndex.split("_Index")[0]);
				for(int i=0;i<index;i++)
				{
				keyPress("DOWN");
				}
				keyPress("ENTER");
				
			}
			
			else
			{
				String locator=xls_Read.getCellValue(sheetName, "lst_BDNIndex;xpath");
				locator=locator.replace("BDNIndex", bdnIndex);
				driver.findElement(By.xpath(locator)).click();
			}
			writeExtent("Pass", "Breakdown instructions succesfully selected"+ screenID + " Page");
		}catch (Exception e) {
			writeExtent("Fail", "Breakdown instructions couldnt selected"+ screenID + " Page");
		}

		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_ThruFlightCarrCode;name", data(carrCode), "Flight Carrier code", screenID);
		enterValueInTextbox(sheetName, "inbx_ThruFlightNumber;name", data(flightNo), "Flight Number", screenID);
		enterValueInTextbox(sheetName, "inbx_ThruFlightDate;name", data(fltDate), "Flight date", screenID);
		enterValueInTextbox(sheetName, "inbx_ThruFlightDestination;name", data(des), "Destination", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}
	}

	/**
	 * Desc : Capture more breakdown instructions from assigned section
	 * @author A-10690
	 * @param instruction
	 * @param carrCode
	 * @param flightNo
	 * @param fltDate
	 * @param des
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void captureULDBreakdownDetails(String instruction,String carrCode, String flightNo,String fltDate, String des) throws InterruptedException, IOException 
	{
		waitForSync(5);
		clickWebElementByWebDriver(sheetName, "lst_BDN1;xpath", "List BDN", screenID);
		waitForSync(2);
		try {
			String locator=xls_Read.getCellValue(sheetName, "lst_BDNIndex1;xpath");
			locator=locator.replace("*", instruction);
			driver.findElement(By.xpath(locator)).click();
		}catch (Exception e) {
			writeExtent("Fail", "Breakdown instructions couldnt selected"+ screenID + " Page");
		}

		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_ThruFlightCarrCode;name", data(carrCode), "Flight Carrier code", screenID);
		enterValueInTextbox(sheetName, "inbx_ThruFlightNumber;name", data(flightNo), "Flight Number", screenID);
		enterValueInTextbox(sheetName, "inbx_ThruFlightDate;name", data(fltDate), "Flight date", screenID);
		enterValueInTextbox(sheetName, "inbx_ThruFlightDestination;name", data(des), "Destination", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}

	}
	/**
	 * @author A-10690
	 * Description :Handle the pop up coming  in export manifest screen on  adding a new uld
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void handleNewULDWarning() throws InterruptedException, IOException
	{

		String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
		if(driver.findElements(By.xpath(popUp)).size()==1)
		{

			String actText=driver.findElement(By.xpath(popUp)).getText();

			if (actText.contains("does not exist in the system. Do you want to continue ?"))
			{

				writeExtent("Info", "Warning message comes as "+actText+ "on adding a new ULD on "+screenID);
			}
			else
			{
				writeExtent("Fail", "Warning message comes as "+actText+ "on adding a new ULD on "+screenID);
			}
			clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
			waitForSync(2); 

		}
	}
	
	/**
	 * @author A-9175
	 * Desc :Clicking More awb details button
	 * @throws InterruptedException
	 */
	public void clickMoreAWBDetails() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "btn_moreAWBdetails;xpath", "More ULD Details button", screenID);
		waitForSync(2);
	}

	
	/**
	 * @author A-9478
	 * Description  : Clicks on Build up complete
	 * @throws InterruptedException
	 * @throws IOException 
	 * 
	 */
	public void clickBuildUpComplete() throws InterruptedException, IOException
	{
		waitForSync(3);
		enterActualweight();
		waitTillScreenload(sheetName, "btn_BuildUpComplete;xpath","Build up complete button", screenName);
		clickWebElementByWebDriver(sheetName, "btn_BuildUpComplete;xpath", "Build up complete button", screenID);
		waitForSync(5);


		try{
			String locator = xls_Read.getCellValue(sheetName, "txt_selectSCC;xpath");
			String sccText=driver.findElement(By.xpath(locator)).getText();

			if (sccText.contains("Select SCC")){

				clickWebElement(sheetName, "btn_ATAOk;xpath", "Select SCC Ok button", screenID);
				waitTillScreenload(sheetName, "btn_BuildUpComplete;xpath","Build up complete button", screenName);
				clickWebElementByWebDriver(sheetName, "btn_BuildUpComplete;xpath", "Build up complete button", screenID);
				waitForSync(5);

			}
		}
		catch (Exception e) {


		}

	}
	/**
	 * @author A-10690
	 * Description : enter actual weight
	 * @param uld
	 * @throws InterruptedException,IOException
	 */
		public void enterActualweight() throws InterruptedException, IOException
		{
			waitForSync(4);
			try{

				String locator2=xls_Read.getCellValue(sheetName, "txt_uldnumber;xpath");
				String ULDNumber=driver.findElement(By.xpath(locator2)).getText();
				String ULDType=ULDNumber.substring(0,3);
				String Carriercode=ULDNumber.substring(8);
				String locator = xls_Read.getCellValue(sheetName, "txt_actualweight;xpath");
				if(driver.findElements(By.xpath(locator)).size()!=1)
				{
					String locator1 = xls_Read.getCellValue(sheetName, "txt_uldnumber;xpath");
					String uld=driver.findElement(By.xpath(locator1)).getText();
					map.put("ULDNO", uld);
					clickEditULDdetails("ULDNO");
					clickMoreUldDetails();

				}
				waitForSync(1);
				String Wght=driver.findElement(By.xpath(locator)).getText();
				/***Code added for handling the extra weight updation for KL-PMC data combination***/
				if((ULDType.equals("PMC"))&&(Carriercode.equals("KL")))
					map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight1"));
				else
					map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight"));



				int newWeight=(Integer.parseInt(data("Extraweight")));
				int actweight=(Integer.parseInt(Wght))+newWeight;
				String actwght=String.valueOf(actweight);
				map.put("actwght", actwght);


				captureMoreUldDetails("actwght","val~80");
			}

			catch(Exception e)
			{
				System.out.println("Failed to enter more uld details in " +screenName);
			}


			}

	
	/**
	 * @author A-9478
	 * Desc : Verifying ULD in Assigned Shipment section
	 * @param awb
	 * @throws InterruptedException
	 */
	public void verifyULDInAssignedShipment(String Uld, boolean ULDPresent) throws InterruptedException {

		String locator = xls_Read.getCellValue(sheetName, "uldNumAssignedShipment;xpath");
		locator=locator.replace("ULDNo",data(Uld));
		int n = driver.findElements(By.xpath(locator)).size();
		if(ULDPresent)
		{
			if(n>0)
			{
				writeExtent("Pass", "ULD number "+data(Uld)
				+ "exists in " + screenID + " Page");
			}
			else
			{
				writeExtent("Fail", "ULD number "+data(Uld)
				+ "does not exists in " + screenID + " Page");
			}
		}
		else
		{
			if(n==0)
			{
				writeExtent("Pass", "ULD number "+data(Uld)
				+ "does not exists in " + screenID + " Page");
			}
			else
			{
				writeExtent("Fail", "ULD number "+data(Uld)
				+ "exists in " + screenID + " Page");
			}
		}

	}


	

	/**
	 * @author A-9478
	 * Description: Split and assign awb from lying list to ULD
	 * @param awbNo, pieces, weight,uld number
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void splitAndAssign(String awbNo,String pieces,String weight,String uldNo) throws InterruptedException, AWTException, IOException
	{
		clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
		enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(awbNo), "Awb No", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_splitAndAssignIcon;xpath", "Split and assign icon", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "btn_splitAndAssign;xpath", "Split and assign button", screenID);
		enterValueInTextbox(sheetName, "inbx_splitPieces;xpath", data(pieces), "Pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_splitWeight;name", data(weight), "Weight", screenID);
		clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok button", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "chkBox_SplitAwbNo;xpath", "Split awb check box", screenID);
		//Assigning to ULD
		String locator=xls_Read.getCellValue(sheetName, "htmlDiv_uldNum;xpath");
		locator=locator.replace("uldNumber", data(uldNo));

		driver.findElement(By.xpath(locator)).click();
		waitForSync(2);

	}
	
	/**
	 * @author A-9847
	 * @Desc To verify multiple shipments inside ULD on the Assigned section
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
public void verifyShipmentsInAssignedList(String awbNo[]) throws InterruptedException, IOException {
		
		try {
					
			clickWebElement(sheetName, "btn_downArrowInAssigned;xpath","down arrow button of assigned shipment", screenID);
			waitForSync(1);
			for(int i=0;i<awbNo.length;i++)
			{
			String locator = xls_Read.getCellValue(sheetName, "lbl_awbInAssignUnderBulk;xpath");
			locator = locator.replace("awbNo", data(awbNo[i]));
			
			if (driver.findElement(By.xpath(locator)).isDisplayed())
				writeExtent("Pass", "AWB number " + data(awbNo[i]) + " exists on " + screenID + " Page");
			else
				writeExtent("Fail", "AWB number " + data(awbNo[i]) + " doesnt exists on " + screenID + " Page");
			
			}
			waitForSync(2);
				
		} catch (Exception e) {
			writeExtent("Fail", "AWB not found on" + screenID + " Page");
		}
	}
	

	/**
	 * Description : allows to click a particular shipment in buildup section
	 * @author A-9175
	 * @param pmyKey
	 * @throws InterruptedException
	 */
	public void clickExpand(String pmyKey) throws InterruptedException {

		selectTableRecord(data(pmyKey), "btn_awbVerify;xpath", sheetName, 3);
		waitForSync(1);

	}
	
	
	/**
	 * @Desc: Finalize the flight
	 * @param waitReq
	 * @throws InterruptedException
	 */
	public void finalizeFlight(boolean waitReq) throws InterruptedException
	{
		if(waitReq)
		{

			clickWebElementByWebDriver(sheetName, "btn_finalizeFlight;id", "Finalize flight button", screenID);
			waitForSync(3);
			clickWebElementByWebDriver(sheetName, "btn_ATAOk;xpath", "ATD button OK", screenID);
			waitForSync(2);
			String currdate=createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
			enterValueInTextbox(sheetName, "inbx_actualDate;name", currdate, "actualDate", screenID);
			waitForSync(1);
			enterValueInTextbox(sheetName, "inbx_ATA;name", "00:00", "ATD", screenID);
			waitForSync(3);
			clickWebElementByWebDriver(sheetName, "btn_ATASave;xpath", "ATD pop up Save", screenID);
			waitForSync(2);
		}

		else
		{
			clickWebElementByWebDriver(sheetName, "btn_finalizeFlight;id", "Finalize flight button", screenID);
			waitForSync(2);
			clickWebElementByWebDriver(sheetName, "btn_ATAOk;xpath", "ATD button OK", screenID);
			waitForSync(1);
			String currdate=createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
			enterValueInTextbox(sheetName, "inbx_actualDate;name", currdate, "actualDate", screenID);
			waitForSync(1);
			enterValueInTextbox(sheetName, "inbx_ATA;name", "00:00", "ATD", screenID);
			clickWebElementByWebDriver(sheetName, "btn_ATASave;xpath", "ATD pop up Save", screenID);
			waitForSync(1);
		}
	}
	
	
	/**
	 * @author A-9175
	 * @Desc: Closing flight
	 * @throws InterruptedException
	 */
	public void closeFLTforBDP() throws InterruptedException
	{
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_closeFlight;id", "Close flight button", screenID);
		waitForSync(2);

	}
	
	
	/**
	 * @author A-9175
	 * Desc : enters the uldnumber or bulk in search textbox
	 * @param shipment
	 * @throws InterruptedException
	 */
	public void searchShipmentInBuildupSection(String shipment) throws InterruptedException {

		enterValueInTextbox(sheetName, "searchShipment;xpath", shipment, "Awb No / ULD", screenID);
		waitForSync(3);

	}
	
	
	/**
	 * @author A-9478
	 * Description : Offload ULD
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void offloadULD(String ULD) throws InterruptedException, AWTException
	{
		String locator=xls_Read.getCellValue(sheetName, "btn_OffloadULD;xpath");
		locator=locator.replace("ULDNum", data(ULD));
		driver.findElement(By.xpath(locator)).click();
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_offloadSave;xpath", "Offload Save", screenID);
		waitForSync(2);

	}
	
	
	/**
	 * @author A-9175
	 * Desc : Clicking Capture checksheet button
	 * @throws InterruptedException
	 */
	public void clickCaptureChecksheet() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "btn_captureChecksheet;id", "Capture Check sheet button", screenID);
		waitForSync(2);

	}
	
	
	/**
	 * @author A-9175
	 * Desc : Verifying Checksheet details
	 * @throws InterruptedException
	 */
	public void verifyChecksheetCaptured() throws InterruptedException
	{
		try{
			switchToFrame("frameName","popupContainerFrame");
			String locator=xls_Read.getCellValue(sheetName, "txt_verifychecksheetValues;xpath");
			List<WebElement> elements=driver.findElements(By.xpath(locator));
			for(WebElement elemnt:elements)

			{
				elemnt.getText().equals("Yes");
				waitForSync(2);
			}
			writeExtent("Pass", "Check sheet details  captured"
					+ " on " + screenID + " Page");
		} 
		catch (Exception e) {

			writeExtent("Fail", "Check sheet details not captured"
					+ " on " + screenID + " Page");
		}

		switchToFrame("default");
		switchToFrame("contentFrame","OPR344");

	}


	/**
	 * @author A-9175
	 * Desc : Verifying POU
	 * @param shipmentVal
	 * @param pou
	 * @throws InterruptedException
	 */
	public void verifyPOU(String shipmentVal,String pou) throws InterruptedException {

		String locator = xls_Read.getCellValue(sheetName, "pouShipment;xpath");
		locator=locator.replace("ULD",shipmentVal);
		locator=locator.replace("POU",data(pou));
		String actualPOU=driver.findElement(By.xpath(locator)).getText();
		verifyScreenText(screenName, data(pou), actualPOU, "POU value", "Successfully identified");
		waitForSync(1);

	}

	
	/**
	 * @author A-9175
	 * Desc : Verifying pcs and weight in buildup section for a ULD/Bulk
	 * @param shipmentVal
	 * @param pcs
	 * @param wgt
	 * @throws InterruptedException
	 */
	public void verifyShipmentDetails(String shipmentVal,String pcs,String wgt) throws InterruptedException {

		/**Pieces**/

		String pcslocator = xls_Read.getCellValue(sheetName, "pcsShipment;xpath");
		pcslocator=pcslocator.replace("ULD",shipmentVal);
		pcslocator=pcslocator.replace("pcs",data(pcs));
		String actualPcs=driver.findElement(By.xpath(pcslocator)).getText();
		verifyScreenText(screenName, data(pcs), actualPcs, pcs, " Successfully identified");

		/**Weight**/

		String wgtlocator = xls_Read.getCellValue(sheetName, "wgtShipment;xpath");
		wgtlocator=wgtlocator.replace("ULD",shipmentVal);
		wgtlocator=wgtlocator.replace("wgt",data(wgt));
		String actualwgt=driver.findElement(By.xpath(wgtlocator)).getText();
		verifyScreenText(screenName, data(wgt), actualwgt, wgt, " Successfully identified");
		waitForSync(1);

	}

	
	/**
	 * @author A-9175
	 * Desc : Verifying awb number inside a ULD/BULK in BuldUp section
	 * @param awb
	 * @throws InterruptedException
	 */
	public void verifyAWBinBuildupSection(String awb) throws InterruptedException {

		String locator = xls_Read.getCellValue(sheetName, "awbNumberBuildUp;xpath");
		locator=locator.replace("awbNo",data(awb));
		String actualAWB=driver.findElement(By.xpath(locator)).getText();
		verifyScreenText(screenName, data(awb), actualAWB, awb, "Successfully identified");
		waitForSync(1);

	}

	/**
	 * @author A-9844
	 * Description : Add new ULD with awb ,pcs,wgt,vol
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addNewULDWithAWB(String uldNo,String pou,String prefix,String awbNumber,String pcs,String wt,String vol) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(1);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(2);
		handleNewULDWarning();
		clickWebElementByWebDriver(sheetName, "inbx_uldNum;id", "List POU", screenID);
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "lst_POU;xpath", "List POU", screenID);
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "lst_POUIndex;xpath");
		locator=locator.replace("POUIndex", pou);
		driver.findElement(By.xpath(locator)).click();
		waitForSync(2);

		//Add AWB Number
		waitTillScreenload(sheetName, "inbx_shipmentPrefix;name", "awb prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(prefix), "awb prefix", screenID);
		enterValueInTextbox(sheetName, "inbx_documentNumber;name", data(awbNumber), "awb number", screenID);

		waitForSync(1);
		performKeyActions(sheetName, "inbx_documentNumber;name","TAB","awb number", screenID);
		waitForSync(1);

		waitTillScreenload(sheetName, "inbx_awbPieces;name", "Pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_awbPieces;name", data(pcs), "Pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_awbWeight;name", data(wt), "Weight", screenID);
		enterValueInTextbox(sheetName, "inbx_awbVolume;name", data(vol), "Volume", screenID);
		waitForSync(2);

		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 


	}
	/**
	 * @author A-7271
	 * Description : Clicks on NOTOC button
	 * @param uld
	 */
	public void clickNOTOC() throws InterruptedException
	{
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		clickWebElementByWebDriver(sheetName, "btn_NOTOC;id", "NOTOC button", screenID);
		waitForSync(2);

	}
	/**A-9478
	* Clicks close button
	* @param pmKey
	 * @throws IOException 
	*/
	public void clickClose() throws InterruptedException, IOException 
	{
		clickWebElement("GenerateNOTOC_OPR017", "btn_closeOPR017;xpath", "Close button", screenName);	
		waitForSync(5);
		
	}
	/**
	 * 
	 * @param ScreenId
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickGenerateNOTOC(String ScreenId) throws InterruptedException, IOException {
		clickWebElement("GenerateNOTOC_OPR017", "btn_generateNOTOC;name", "generate NOTOC", screenName);		
		switchToFrame("default");
		waitForSync(8);
	    clickWebElement("Generic_Elements", "btn_no;xpath", "no Button", screenName);
		switchToFrame("contentFrame", ScreenId);	
		
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Description : Offload ULD
	 */
	public void offloadULD() throws InterruptedException, AWTException
	{
		clickWebElementByWebDriver(sheetName, "btn_offload;id", "Offload Button", screenID);
		waitForSync(6);
		clickWebElementByWebDriver(sheetName, "lst_offloadReason;xpath", "Offload Reason", screenID);
		waitForSync(3);
		keyPress("ENTER");
		enterOffloadLocationAMS();
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_offloadSave;xpath", "Offload Save", screenID);
		waitForSync(2);


	}
	
	/**@author A-10328
	 * Description - Enter Build up location 
	 * @throws InterruptedException
	 */
public void enterOffloadLocationAMS() throws InterruptedException
	
	{
		
	String OffloadLocation= WebFunctions.getPropertyValue(toproppath, "BuildupLocation_AMS");
	enterValueInTextbox(sheetName, "inbx_offloadLoc;xpath", OffloadLocation, "build up location", screenID);
	waitForSync(2);
	}

	/**
	 * @author A-7271
	 * Description : offload awb
	 * @param ofldPcs
	 * @param OfldWt
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void offloadAwb(String ofldPcs,String OfldWt) throws InterruptedException, AWTException
	{
		clickWebElementByWebDriver(sheetName, "btn_offload;id", "Offload Button", screenID);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_offloadPcs;name", data(ofldPcs), "Offload pieces", screenID);
		enterValueInTextbox(sheetName, "inbx_offloadWt;name", data(OfldWt), "Offload weight", screenID);
		clickWebElementByWebDriver(sheetName, "lst_offloadReason;xpath", "Offload Reason", screenID);
		waitForSync(3);
		keyPress("ENTER");
		waitForSync(2);
		clickWebElementByWebDriver(sheetName, "btn_offloadSave;xpath", "Offload Save", screenID);
		waitForSync(2);

	}

	
	/**
	 * @author A-7271
	 * Description : Verify the flight status
	 * @param status
	 * @throws InterruptedException
	 */
	public void verifyFlightStatus(String status) throws InterruptedException
	{
		waitForSync(5);
		getTextAndVerify(sheetName, "htmlDiv_flightStatus;xpath", "Flight Status", screenID, "Verification of flight status",
				data(status), "equals");

	}
	
	
	/**
	 * Desc : Adding POU and ULD number
	 * @author A-9175
	 * @param uldNo
	 * @param pou
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addNewULDandPOU(String uldNo,String pou) throws InterruptedException, AWTException, IOException
	{


		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		/*******************************************/
		clickWebElement(sheetName, "inbx_uldNum;id", "Add ULD button", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		/********************************************/

		/**Method for selecting POU when pou is not autopopulated***/
		selectPOU(pou);
		//Enter Buildup location
				enterBuildUpLocation();

		waitForSync(2);
		}

	
	/**
	 * Desc: Adding Destination
	 * @author A-9175
	 * @param destination
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addULDDestination(String destination) throws InterruptedException, AWTException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_assignedShipmentDestination;name", data(destination), "Destination", screenID);  
	}

	
	/**
	 * Desc : Adding Contour
	 * @author A-9175
	 * @param cont
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void addCountour(String cont) throws InterruptedException, AWTException, IOException
	{
		clickWebElementByWebDriver(sheetName, "lst_contour;xpath", "List Contour", screenID);
		try {
			String locator=xls_Read.getCellValue(sheetName, "lst_ContourIndex;xpath");
			locator=locator.replace("ContourIndex", cont);
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", " Contour Selected "+ screenID + " Page");
		} catch (Exception e) {
			writeExtent("Fail", " Contour Not Selected "+ screenID + " Page");
		}
		waitForSync(2);     
	}


	/**
	 * Desc: Saving and closing details entered for ULD in assigned Tab
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void saveAndClose() throws InterruptedException, IOException 
	{
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(locator)).size()>=1)
		{
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}
	}

	


	/**
	 * Desc : Verifying ULD Shipment Under Lying list
	 * @author A-9175
	 * @param ULD
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyULDShipmentInLyingList(String ULD) throws InterruptedException, IOException
	{

		//Enter the awbNumber
		clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
		clickWebElement(sheetName, "btn_clickULDLyingList;xpath", "ULD Button", screenID);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(ULD), "Awb No", screenID);
		waitForSync(2);
		getTextAndVerify(sheetName, "htmlDiv_lnkULDNumber;xpath", data(ULD)+" ULD getting listed in lying list", screenID, "Verification of ULD number in lying list",
				data(ULD), "contains");


	}


	/**
	 * Desc : click relist button
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void relist() throws InterruptedException, AWTException {

		clickWebElementByWebDriver(sheetName, "btn_relist;xpath", "Relist Button", screenID);
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "btn_List;id", "List Button", screenID);
		waitForSync(5);

	}
	
	
	

	/**
	 * Desc : Enter transaction details for split AWB
	 * @author A-9175
	 * @param pcs
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void EnterTransactionPcsForSplitAWB(String pcs) throws InterruptedException, AWTException {

		switchToFrame("frameName","popupContainerFrame");
		enterValueInTextbox(sheetName, "inbx_transactionPcs;xpath", data(pcs), "Pcs", screenID);
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "btn_OK;id", "OK Button", screenID);
		waitForSync(5);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR344");

	}


	/**
	 * Desc: Closing addNewUldDiv
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void ClickCloseaddNewULDWithAWB() throws InterruptedException, AWTException, IOException
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		waitForSync(5);

	}


	/**
	 * @author A-7271
	 * Description : Verify the flight status
	 * @param status
	 * @throws InterruptedException
	 */
	public void verifyProgressBarStatus(String status,String progress) throws InterruptedException
	{

		if(status.equals("Manifest"))
		{
			getTextAndVerify(sheetName, "span_manifestProgressBar;xpath", "Status Progress Bar", screenID, "Verification of progress bar status",
					data(progress), "equals");
		}
		else if(status.equals("BuildUp"))
		{
			getTextAndVerify(sheetName, "span_buildUpProgressBar;xpath", "Status Progress Bar", screenID, "Verification of progress bar status",
					data(progress), "equals");
		}
		else if(status.equals("Executed"))
		{
			getTextAndVerify(sheetName, "span_executedProgressBar;xpath", "Status Progress Bar", screenID, "Verification of progress bar status",
					data(progress), "equals");
		}
		else if(status.equals("Joining"))
		{
			getTextAndVerify(sheetName, "span_joiningProgressBar;xpath", "Status Progress Bar", screenID, "Verification of progress bar status",
					data(progress), "equals");
		}
		else if(status.equals("Transit"))
		{
			getTextAndVerify(sheetName, "span_transitProgressBar;xpath", "Status Progress Bar", screenID, "Verification of progress bar status",
					data(progress), "equals");
		}

	}
	
	
	/**
	 * @Desc: Assign ULD
	 * @param uldNo
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void assignULD(String uldNo) throws InterruptedException, AWTException, IOException
	{
		clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
		waitForSync(3);
		clickWebElement(sheetName, "btn_UldsList;xpath", "ULDs to be assigned", screenID);
		waitForSync(3);
		enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(uldNo), "ULD Number", screenID);
		waitForSync(4);
		clickWebElement(sheetName, "chkBox_awbNumberLyingList;name", "Lying List Check Box", screenID);
		waitForSync(5);
		clickWebElement(sheetName, "btn_AssignHere;xpath", "Assign Here", screenID);
		waitForSync(5);
		try {
			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
				
			}
			/***************************************************/
			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
			}

		} catch (Exception e) {
			
		}		
	}

	
	/**
	 * @author A-7271
	 * Description : Shipment Manifest
	 * @throws Exception
	 */
	public void manifestDetails() throws Exception
	{
		waitForSync(3);
		clickWebElementByWebDriver(sheetName, "btn_Manifest;id", "Manifest button", screenID);
		waitTillScreenload(sheetName, "txt_printManifest;xpath","Print Manifest text", screenName);
		switchToFrame("frameName","popupContainerFrame");
		waitTillScreenload(sheetName, "btn_ManifestClose;id", "Manifest Pop up Close", screenID);
		waitForSync(2);
		doubleclickWebElement(sheetName, "btn_ManifestClose;id", "Manifest Pop up Close", screenID);

		/***clickWebElementByWebDriver(sheetName, "btn_ManifestClose;id", "Manifest Pop up Close", screenID);***/
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR344");


	}

	
	/**
	 * @author A-7271
	 *  Description : click Manifest
	 * @throws Exception
	 */
	public void clickManifest() throws Exception
	{

		clickWebElementByWebDriver(sheetName, "btn_Manifest;id", "Manifest button", screenID);
		waitForSync(8);
	}
	/**
	 * 
	 * @throws Exception
	 * Desc : clock ok button of print manifest pop up
	 */
	public void printManifestOk() throws Exception
	{
		waitForSync(4);
		switchToFrame("frameName","popupContainerFrame");
		switchToWindow("storeParent");
		clickWebElementByWebDriver(sheetName, "btn_ManifestOk;id", "Manifest Pop up Ok", screenID);
		waitForSync(6);	
	}
	/**
	 * 
	 * @throws Exception
	 *  Desc : clock close button of print manifest pop up
	 * 
	 */
	public void printManifestClose() throws Exception
	{
	switchToFrame("frameName","popupContainerFrame");
	clickWebElementByWebDriver(sheetName, "btn_ManifestClose;id", "Manifest Pop up Close", screenID);
	waitForSync(2);
	switchToFrame("default");
	switchToFrame("contentFrame","OPR344");
	}
	/**
	 * Desc: Adding UldDestination in manifest POPUP filter
	 * @author A-10330
	 * @param destination
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void enterUldDestinationInManifestPopup(String destination) throws InterruptedException, AWTException, IOException
	{
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_uldDest;name", data(destination), "Destination", screenID);  
	}

	/**
	 * @Desc: Print manifest
	 * @author A-7271
	 * @throws Exception 
	 */
	public void printManifest() throws Exception
	{

		clickWebElementByWebDriver(sheetName, "btn_Manifest;id", "Manifest button", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "txt_printManifest;xpath","Print Manifest text", screenName);
		switchToFrame("frameName","popupContainerFrame");
		switchToWindow("storeParent");
		clickWebElementByWebDriver(sheetName, "btn_ManifestOk;id", "Manifest Pop up Ok", screenID);
		waitForSync(2);

		switchToWindow("multipleWindows");

		int windowSize=getWindowSize();

		if(windowSize==2)
		{
			onPassUpdate(screenID, "window size should be 2 ", "window size is "+windowSize, "Verify whether the report is generated",
					"Verify whether the report is generated while print manifest");
		}
		else
		{
			onFailUpdate(screenID, "window size should be 2 ", "window size is "+windowSize, "Verify whether the report is generated",
					"Verify whether the report is generated while print manifest");
		}
		closeBrowser();
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","OPR344");
		switchToFrame("frameName","popupContainerFrame");
		clickWebElementByWebDriver(sheetName, "btn_ManifestClose;id", "Manifest Pop up Close", screenID);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR344");
	}
	
	
	/**
	 * @author A-7271
	 * Description : Expand ULD link
	 * @throws InterruptedException 
	 */
	public void expandULDs() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "btn_uldlinkExpand;xpath", "Expand ULD link", screenID);
	}


	/**
	 * @author A-7271
	 * Desc : capture check sheet
	 * @param pmKey
	 */
	public void clickCheckSheet(String pmKey)
	{
		String locator=xls_Read.getCellValue(sheetName, "btn_checkSheet;xpath");
		locator=locator.replace("pmKey", data(pmKey));

		System.out.println(locator);

		WebElement element=driver.findElement(By.xpath(locator));

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

		waitForSync(3);
	}

	
	/**
	 * @author A-7271
	 * Description  : Close flight
	 * @throws InterruptedException
	 */
	public void closeFlight() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "btn_closeFlight;id", "Close flight button", screenID);
		waitForSync(2);

		//Verify if the open flight for build up button is displayed
		String xpath = xls_Read.getCellValue(sheetName, "btn_openFlight;xpath");
		verifyElementDisplayed(xpath, "Verify is the flight is closed",
				screenName,"Open flight for build up");

	}
	
	
	/**
	 * @author A-7271
	 * Description  : Open flight
	 * @throws InterruptedException
	 */
	public void reopenFlight() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "btn_openFlight;xpath", "Open flight button", screenID);
		waitForSync(2);

		//Verify if the  close flight for build up button is displayed
		String xpath = xls_Read.getCellValue(sheetName, "btn_closeFlight;xpath");
		verifyElementDisplayed(xpath, "Verify is the flight is reopened",
				screenName,"Close flight for build up");
	}

	
	/**
	 * @author A-7271
	 * Description : Add new ULD
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addNewULD(String uldNo,String pou) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(5);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();


		/***************************************************************************/


		/**Method for selecting POU when pou is not autopopulated***/
		selectPOU(pou);
		/***************************************************************************/


		//Replaced OLD CODE for POU selection
		/**clickWebElementByWebDriver(sheetName, "lst_POU;xpath", "List POU", screenID);
        waitForSync(6);
        String locator=xls_Read.getCellValue(sheetName, "lst_POUIndex;xpath");
        locator=locator.replace("POUIndex", pou);
        try {
               driver.findElement(By.xpath(locator)).click();
               writeExtent("Pass", "selected POU" + screenID + " Page");

        } catch (Exception e) {
               writeExtent("Fail", "Could not select POU" + screenID + " Page");
        }**/



		waitForSync(5);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}
		


	}
	
	
	/**
	 * @author A-7271
	 * Desc : Capture check sheet
	 * @param checkSheetExist
	 */
	public void captureCheckSheet(boolean checkSheetExist)
	{
		boolean checkSheetExists=true;
		try
		{
			driver.switchTo().frame("popupContainerFrame");
			waitForSync(1);
			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));

			System.out.println(questions);

			if(questions.size()==0)
			{
				checkSheetExists=false;
			}

			for(WebElement ele : questions)
			{
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
			}

			if(checkSheetExist)
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

			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);


			waitForSync(2);
			switchToFrame("contentFrame", "OPR344");
			driver.switchTo().frame("popupContainerFrame");
			driver.findElement(By.xpath("//button[@name='btnClose']")).click();
			waitForSync(1);
			switchToFrame("default");
			switchToFrame("contentFrame", "OPR344");

		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not select check sheet details on "+screenName);
		}
	}

	
	/**
	 * Desc : Enter transaction details for split AWB
	 * @author A-9478
	 * @param pcs
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterPcsForSplitAWB(String pcs) throws InterruptedException, AWTException 
	{
		try
		{
			switchToFrame("frameName","popupContainerFrame");
			String locator=xls_Read.getCellValue(sheetName, "inbx_transactionPcsForSplit;xpath");
			List<WebElement> ele = driver.findElements(By.xpath(locator));
			int count=0;boolean flag=false;
			for(WebElement loc:ele)
			{
				count=count+1;
				if(loc.isEnabled())
				{                 
					flag=true;
					String finalLocator = "("+locator+")"+"["+count+"]";
					driver.findElement(By.xpath(finalLocator)).sendKeys(data(pcs));
					waitForSync(3);
					writeExtent("Pass", "Transaction pcs "+data(pcs)
					+ "entered successfully in " + screenID + " Page");
					break;
				}

			}
			if(flag=false)
			{
				writeExtent("Fail", "Couldn't enter transaction pcs "+data(pcs)
				+ " in " + screenID + " Page");
			}

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't enter transaction pcs "+data(pcs)
			+ " in " + screenID + " Page");
		}
		clickWebElementByWebDriver(sheetName, "btn_OK;id", "OK Button", screenID);
		waitForSync(5);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR344");

	}

	
	/**
	 * @author A-7271
	 * Description : Add new ULD without AWB booking
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addNewULDWithoutAWBBooking(String uldNo,String pou) throws InterruptedException, AWTException, IOException
	{

		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		clickWebElementByWebDriver(sheetName, "lst_POU;xpath", "List POU", screenID);
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "lst_POUIndex;xpath");
		locator=locator.replace("POUIndex", pou);
		driver.findElement(By.xpath(locator)).click();
		waitForSync(2);
		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		driver.findElement(By.xpath("//button[contains(.,'Ok')]")).click();
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}


	}
	
	
	/**
	 * @author A-7271
	 * Description : Add new ULD with awb
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void addNewULDWithAWB(String uldNo,String pou,String prefix,String awbNumber,String pcs,String wt) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", data(uldNo), "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(2);
		handleNewULDWarning();
		/***************************************************************************/

		/**Method for selecting POU when pou is not autopopulated***/
		selectPOU(pou);
		/***************************************************************************/

		
		
		if(!(data(uldNo).equals("BULK"))){
		//Enter Buildup location
		enterBuildUpLocation();
		}

		//Add AWB Number
		addAWBDetails(prefix,awbNumber,pcs,wt);
		try {
			assignDGSL();
			String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
			String actText=driver.findElement(By.xpath(popUp)).getText();
			if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
			}
			/***************************************************/
			else if (actText.contains("do not have a booking"))
			{
				waitForSync(3);  
				clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
				waitForSync(5); 
				writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");

			}
			/***************************************************/
			else
			{
				writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
			}

		} catch (Exception e) {

		}
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");

		String closeBtn=xls_Read.getCellValue(sheetName, "btn_close;xpath");
		if(driver.findElements(By.xpath(closeBtn)).size()==1)
		{
			clickWebElement(sheetName, "btn_close;xpath", "Close button", screenID); 
		}
	}



/**
	 * @author A-9844
	 * Desc.. verify build up complete icon is removed
	 * @param uld
	 * @throws InterruptedException
	 */
	public void verifyBuildUpCompleteNotRetained(String uldNum) throws InterruptedException
	{
		try{
			waitForSync(2);
			String locator = xls_Read.getCellValue(sheetName, "txt_uld;xpath");
			locator=locator.replace("uld",data(uldNum));
			if(driver.findElement(By.xpath(locator)).getAttribute("data-buildupcompleteflag").equalsIgnoreCase("false")){
				writeExtent("Pass", "Build up completed icon is not retained for uld- "+data(uldNum)+" in" + screenID + " Page");
			}
			else {
				writeExtent("Fail", "Build up complete icon is retained for uld- "+data(uldNum)+" in" + screenID + " Page");
			}}
		catch (Exception e) {

			writeExtent("Fail", "Could not verify Build up status in "+ screenID + " Page");
		}
	}


	/**
	 * @author A-7271
	 * @Desc: verify error message
	 * @param expMsg
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyErrorMsg(String expMsg) throws InterruptedException, IOException
	{
		clickWebElement("Generic_Elements", "btn_expandError;xpath", "Expand Error", screenID); 
		waitForSync(2);
		getTextAndVerify("Generic_Elements", "htmlDiv_errorMsg;xpath","Error Msg",screenID, "Verify Error Msg", data(expMsg), "contains");

	}

	/**
	 * @author A-7271
	 * Description : Preadvice
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void preAdvice(String scc,String pou) throws InterruptedException, AWTException, IOException
	{

		clickWebElement(sheetName, "btn_preadvice;id", "Preadvice button", screenID);
		waitForSync(3); 
		clickWebElementByWebDriver(sheetName, "lst_preAdviceSCC;xpath", "List SCC", screenID);

		//Select SCC
		waitForSync(2); 
		System.out.println(driver.getPageSource());
		String sccLoc=xls_Read.getCellValue(sheetName, "lst_preAdviceSCCIndex;xpath");
		sccLoc=sccLoc.replace("SCCIndex", scc);
		driver.findElement(By.xpath(sccLoc)).click();

		//Select POU
		clickWebElementByWebDriver(sheetName, "lst_preAdvicePOU;xpath", "List POU", screenID);
		waitForSync(1); 
		String pouLoc=xls_Read.getCellValue(sheetName, "lst_preAdvicePOUIndex;xpath");
		pouLoc=pouLoc.replace("POUIndex", pou);
		driver.findElement(By.xpath(pouLoc)).click();
		
		//List
		clickWebElementByWebDriver(sheetName, "btn_preAdviceList;xpath", "List details", screenID);
		clickWebElementByWebDriver(sheetName, "chkBox_ffmandfwb;xpath", "chkbox ffm and fwb", screenID);
		enterValueInTextbox(sheetName, "txtArea_preAdviceRem;xpath", "FFM and FWB", "FFM and FWB Message", screenID);
		clickWebElementByWebDriver(sheetName, "btn_preAdviceSend;xpath", "Send details", screenID);
		waitForSync(6); 

	}
	
	
	/**
	 * @author A-7271
	 * Description : Select awb number
	 * @param awb
	 */
	public void selectAwbNumber(String pmKey)
	{
		System.out.println(data(pmKey));
		String locator=xls_Read.getCellValue(sheetName, "chkBox_awbNumber;xpath");
		locator=locator.replace("pmKey", data(pmKey));
		driver.findElement(By.xpath(locator)).click();
		waitForSync(2);
	}
	
	
	/**
	 * @author A-7271
	 * @param awb
	 * Description : Select awb number
	 */
	public void selectAwbNumberByJS(String pmKey)
	{
		System.out.println(data(pmKey));
		String locator=xls_Read.getCellValue(sheetName, "chkBox_awbNumber;xpath");
		locator=locator.replace("pmKey", data(pmKey));

		/*** SELECT AWB****/
		WebElement element=driver.findElement(By.xpath(locator));

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
		waitForSync(2);
	}

	
	/**
	 * @author A-7271
	 * Description : finalize flight
	 * @throws InterruptedException
	 */
	public void finalizeFlight() throws InterruptedException
	{
		clickWebElementByWebDriver(sheetName, "btn_finalizeFlight;id", "Finalize flight button", screenID);
		waitForSync(1);
		clickWebElementByWebDriver(sheetName, "btn_ATAOk;xpath", "ATD button OK", screenID);
		String currdate=createDateFormatWithTimeZone("dd-MMM-yyyy", 0, "DAY", "Europe/Amsterdam");
		enterValueInTextbox(sheetName, "inbx_actualDate;name", currdate, "actualDate", screenID);
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_ATA;name", "00:00", "ATD", screenID);
		clickWebElementByWebDriver(sheetName, "btn_ATASave;xpath", "ATD pop up Save", screenID);
		waitForSync(1);
	}
	
	/**
	 * @Desc: Assign lying list
	 * @param awbNo
	 * @param uldNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void assignLyingList(String awbNo,String uldNo) throws InterruptedException, IOException
	{
		//Enter the awbNumber
		clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
		enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(awbNo), "Awb No", screenID);
		waitForSync(2);
		clickWebElement(sheetName, "chkBox_awbNumberLyingList;name", "Lying List Check Box", screenID);

		//Assigning to ULD
		String locator=xls_Read.getCellValue(sheetName, "htmlDiv_uldNum;xpath");
		locator=locator.replace("uldNumber", data(uldNo));

		driver.findElement(By.xpath(locator)).click();
		waitForSync(2);
	}
	
	
	/**
	 * @author A-7271
	 * Description : verify whether the shipment is getting listed in lying list
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyShipmentInLyingList(String awbNo) throws InterruptedException, IOException
	{

		//Enter the awbNumber
		clickWebElement(sheetName, "htmlDiv_LyingList;xpath", "Lying list Button", screenID);
		enterValueInTextbox(sheetName, "inbx_KeywordLyingList;xpath", data(awbNo), "Awb No", screenID);
		waitForSync(2);
		getTextAndVerify(sheetName, "htmlDiv_lnkAwbNumber;xpath", "awb number", screenID, "Verification of awb number in lying list",
				data(awbNo), "contains");

	}
	
	
	/**
	 * @author A-7271
	 * Description : Select ULD
	 * @param uld
	 */
	public void selectULD(String uld)
	{
		
			try{
				String locator=xls_Read.getCellValue(sheetName, "htmlDiv_uldNum;xpath");
				locator=locator.replace("uldNumber", data(uld));

				driver.findElement(By.xpath(locator)).click();
				waitForSync(2);
				writeExtent("Pass", "Sucessfully selected the ULD " +data(uld) + " on " + screenID);
				String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
				String actText=driver.findElement(By.xpath(popUp)).getText();
				if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
				}
				/***************************************************/
				else if (actText.contains("do not have a booking"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
					
				}
				/***************************************************/
				else
				{
					writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
				}

				
			}
			catch(Exception e){
				
			}
			
			
		}

	/**
	 * @author A-7271
	 * @Desc: List flight 
	 * @param ScreenID
	 * @throws InterruptedException
	 * Description... List Flight
	 */
	public void dataload_listFlight(String carrierCode, String flightNumber, String flightDate) throws InterruptedException, AWTException {

		try {
			waitTillScreenload(sheetName, "inbx_carrierCode;id","Flight Carrier code", screenName);
			waitForSync(2);
			enterValueInTextbox(sheetName, "inbx_carrierCode;id", carrierCode, "Carrier Code", screenID);
			enterValueInTextbox(sheetName, "inbx_flightNumber;id", flightNumber, "Flight Number", screenID);
			enterValueInTextbox(sheetName, "inbx_flightDate;id", flightDate, "Flight Date", screenID);
			clickWebElementByWebDriver(sheetName, "btn_List;id", "List Button", screenID);
			waitForSync(5);
		} catch (Exception e) {
			System.out.println("Could not perform list flight operations");
			test.log(LogStatus.FAIL, "Could not perform list flight operations in "+screenName);

		}
	}
	/**
	 * @author A-9175
	 * Desc : Verifying shipment under planned section
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public boolean dataload_verifyShipmentInPlannedSection(String awbNo) throws InterruptedException, IOException
	{
		try {
			enterValueInTextbox(sheetName, "inbx_placeholderText;xpath", awbNo, "Awb No", screenID);
			String locator=xls_Read.getCellValue(sheetName, "lbl_plannedShipments;xpath");
			locator=locator.replace("awbNo", awbNo);
			if(driver.findElement(By.xpath(locator)).isDisplayed())
			{
				writeExtent("Pass", "AWB number "+awbNo+ "exists in " + screenID + " Page");
				return true;
				
			}
			else
			{
				writeExtent("Fail", "AWB number "+awbNo+ "Doesnt exists in " + screenID + " Page");
				return false;
			}
			
		} catch (Exception e) {
			writeExtent("Fail", "Element not found in" + screenID + " Page");
			return false;
		}


	}
	/**
	 * @author A-9175
	 * Description : Add  ULD With existing awb
	 * @param uldNo
	 * @param POU
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void dataload_addULDWithoutAWB(String uldNo,String pou,String destination,String contour,String actWt,String location) throws InterruptedException, AWTException, IOException
	{
		waitTillScreenload(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		clickWebElement(sheetName, "btn_AddULD;xpath", "Add ULD button", screenID);
		waitForSync(2);
		waitTillScreenload(sheetName, "inbx_uldNum;id", "Uld Number", screenID);
		enterValueInTextbox(sheetName, "inbx_uldNum;id", uldNo, "Uld Number", screenID);
		waitForSync(2);
		performKeyActions(sheetName,"inbx_uldNum;id", "TAB","ULD no", screenID);
		waitForSync(3);
		handleNewULDWarning();
		/********************************************************************/
		selectPOU(pou);

		/*********************************************************************/
		
		enterValueInTextbox(sheetName, "inbx_assignedShipmentDestination;name",destination, "Destination", screenID); 
		
		//Adding contour details
				
					/**checking whether contour selected by default***/
					String contour1=xls_Read.getCellValue(sheetName, "lbl_countourcheck;xpath");
					contour1=contour1.replace("*", contour);
					
					String locator1=xls_Read.getCellValue(sheetName, "lbl_countour;xpath");
					driver.findElement(By.xpath(locator1)).click();
					String locator2=xls_Read.getCellValue(sheetName, "dpdwn_countourname;xpath");
					locator2=locator2.replace("*", contour);
					driver.findElement(By.xpath(locator2)).click();

						
						
					
				
				
				clickMoreUldDetails();
				enterValueInTextbox(sheetName, "inbx_actualWgt;name", actWt, "Actual Weight", screenID);
				//Location
				enterValueInTextbox(sheetName, "inbx_buildupLocation;xpath", location, "build up location", screenID);
				

		clickWebElement(sheetName, "btn_SaveULD;xpath", "Save ULD", screenID);
		waitForSync(2);
		waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
		
	}
	
	/**
	 * Desc : Selecting shipment from Planned section
	 * @author A-9175
	 * @param awbNo
	 * @throws InterruptedException
	 */
	public void dataload_clickShipemntFromPlannedSection(String awbNo) throws InterruptedException
	{
		try {
			String locator=xls_Read.getCellValue(sheetName, "chkBox_plannedshipment;xpath");
			locator=locator.replace("awbNo", awbNo);
			driver.findElement(By.xpath(locator)).click();
			writeExtent("Pass", "AWB number "+awbNo+ "Selected in " + screenID + " Page");
			waitForSync(2);  
		} catch (Exception e) {
			writeExtent("Fail", "Shipment number "+awbNo+ "Not Selected in " + screenID + " Page");
		}

	}
	/**
	 * @author A-7271
	 * Description : Select ULD
	 * @param uld
	 */
	public void dataload_selectULD(String uld)
	{
		
			try{
				String locator=xls_Read.getCellValue(sheetName, "htmlDiv_uldNum;xpath");
				locator=locator.replace("uldNumber", uld);

				driver.findElement(By.xpath(locator)).click();
				waitForSync(2);
				waitTillSpinnerDisappear(sheetName,"htmlDiv_loader;xpath");
				writeExtent("Pass", "Sucessfully selected the ULD " +uld + " on " + screenID);
				String popUp=xls_Read.getCellValue(sheetName, "lbl_popUp;xpath");
				String actText=driver.findElement(By.xpath(popUp)).getText();
				if (actText.contains("The shipment is not booked to the flight. Do you want to proceed?"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
				}
				/***************************************************/
				else if (actText.contains("do not have a booking"))
				{
					waitForSync(3);  
					clickWebElement(sheetName, "btn_ATAOk;xpath", "Ok Button", screenID);
					waitForSync(5); 
					writeExtent("Info", "Sucessfully Accepted Popup " + screenID + " Page");
					
				}
				/***************************************************/
				else
				{
					writeExtent("Fail", "Pop up is coming as  "+actText+" while assigning ULD on " + screenID + " Page");
				}

				
			}
			catch(Exception e){
				
			}
			
			
		}
	
	/**
	 * @author A-9478
	 * Desc : Verifying ULD in Assigned Shipment section
	 * @param awb
	 * @throws InterruptedException
	 */
	public boolean dataload_verifyULDInAssignedShipment(String Uld) throws InterruptedException {

		System.out.println(Uld);
		String locator = xls_Read.getCellValue(sheetName, "uldNumAssignedShipment;xpath");
		System.out.println(locator);
		locator=locator.replaceAll("ULDNo",Uld);
		System.out.println(locator);
		int n = driver.findElements(By.xpath(locator)).size();
		System.out.println(n);
		
			if(n>0)
			{
				writeExtent("Pass", "ULD number "+Uld
				+ "exists in " + screenID + " Page");
				return true;
			}
			else
			{
				writeExtent("Fail", "ULD number "+Uld
				+ "does not exists in " + screenID + " Page");
				return false;
			}
		

	}

	/**
	 * @author A-9478
	 * Description  : Clicks on Build up complete
	 * @throws InterruptedException
	 * @throws IOException 
	 * 
	 */
	public void dataload_clickBuildUpComplete() throws InterruptedException, IOException
	{
		
		waitTillScreenload(sheetName, "btn_BuildUpComplete;xpath","Build up complete button", screenName);
		clickWebElementByWebDriver(sheetName, "btn_BuildUpComplete;xpath", "Build up complete button", screenID);
		waitForSync(3);

	}
	
	public void dataload_editAndClear() throws InterruptedException
	{
		/**
		 * Desc : click relist button
		 * @author A-9175
		 * @throws InterruptedException
		 * @throws AWTException
		 */
	
		String locator = xls_Read.getCellValue(sheetName, "btn_relist;xpath");

		while(driver.findElements(By.xpath(locator)).size()==1)
		{
			clickWebElementByWebDriver(sheetName, "btn_relist;xpath", "Relist Button", screenID);
			waitTillScreenload(sheetName, "btn_clear;xpath","clear button", screenName);
			clickWebElementByWebDriver(sheetName, "btn_clear;xpath", "Relist Button", screenID);
			break;
		}

		}
		
	}
