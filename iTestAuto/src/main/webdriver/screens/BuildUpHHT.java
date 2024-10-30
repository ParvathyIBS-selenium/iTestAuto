package screens;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class BuildUpHHT extends CustomFunctions {
	
	String sheetName = "BuildUpHHT";
	String screenName = "BuildUpHHT";
	public static String uldproppath = "\\src\\resources\\ULD.properties";
	public static String checksheetpath = "\\src\\resources\\Checksheet.properties";  

	public BuildUpHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175f
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Build up screen
	 */
	public void invokeBuildUpScreen() throws InterruptedException, AWTException {

			try
		{	
		scrollInMobileDevice("Build Up");
		clickActionInHHT("buildUphht_menu;xpath",proppathhht,"Build Up menu",screenName);
		waitForSync(2);
		writeExtent("Pass", screenName+" is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", screenName+" is not invoked successfully");
		}
	}
	/**@author A-10328
	* Description - Verify SCC Field
	*/
			
	public void verifySCCField()
	{
		String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_clickSCC;xpath");

		if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
		{
		writeExtent("Pass", "Successfully verified SCC Field in"+screenName);

		}
		else
		{
		writeExtent("Fail", "SCC Field is not displayed in"+screenName);
		}
		}

	/**
	 * @author A-10690
	 * Desc..Accept alert message
	 * @throws IOException
	 */
		public void handleAlertMessage() throws IOException
		{
			try
			{
				
				
				int size=getSizeOfMobileElement("btn_Yes;xpath",proppathhht);
				String locatorValue=getPropertyValue(proppathhht, "text_alertMsg1;xpath");
				String alertMessage=androiddriver.findElement(By.xpath(locatorValue)).getText();

				if(size==1)
				{
					clickActionInHHT("btn_Yes;xpath",proppathhht,"yes button",screenName);	
					writeExtent("Pass", "Clicked on yes button for alert "+ alertMessage +screenName);
				}
				else
				{
					captureScreenShot("Android");
					writeExtent("Fail", "Could not click on Yes button for alert "+ alertMessage +screenName);
				}
			}

			catch(Exception e)
			{
				writeExtent("Fail", "Could not click on yes button  "+screenName);
			}

		}
		/**
		 * @author A-10690
		 * @throws AWTException
		 * @throws InterruptedException
		 * @throws IOException
		 * Desc:click save button only while doing build up
		 */
		public void clicksave() throws AWTException, InterruptedException, IOException
		{

			clickActionInHHT("buildUphht_btn_Save;xpath",proppathhht,"Save",screenName);    
		    waitForSync(8);                        

		}
		/**
		 * Desc : Click save button
		 * @author A-9844
		 * @throws AWTException
		 * @throws InterruptedException
		 * @throws IOException 
		 */
		public void clickSaveButton() throws AWTException, InterruptedException, IOException
		{
				clickActionInHHT("buildUphht_unitizedSave;xpath",proppathhht,"Unitized Save button",screenName);	
				waitForSync(4); 
		}





		/**
		 * @author A-9844
		 * Description : Select offload reasons
		 * @throws InterruptedException 
		 * @throws IOException 
		 */
		public void selectOffloadReason(String offloadReason) throws InterruptedException, IOException
		{

			try
			{
				scrollInMobileDevice("Offload Reason");
				clickActionInHHT("buildUphht_offloadReasonIcon;xpath",proppathhht,"Offload Reason Icon",screenName);
				waitForSync(5);
				String locator = WebFunctions.getPropertyValue(proppathhht,"buildUphht_offloadReasonValue;xpath");
				locator=locator.replace("OffloadReason", offloadReason);
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(2);
				writeExtent("Pass", "Offload Reason "+offloadReason+" is selected "+" in "+screenName);
			}

			catch(Exception e)
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Failed to select Offload Reason "+offloadReason+" in "+screenName);
			}

		}

		/** 
		 * @author A-10690
		 * @param pcs
		 * @param wt
		 * @throws AWTException
		 * @throws InterruptedException
		 * @throws IOException
		 * Desc:Enter pieces ,weight and scc while build up without clicking save button
		 */
		public void enterPiecesAndSCC(String pcs,String wt,String scc) throws AWTException, InterruptedException, IOException
		{
			try
			{
				scrollInMobileDevice("Select SCC");

				clickActionInHHT("buildUphht_btn_selectSCCarrow;xpath",proppathhht,"selectsccarrow",screenName); 

				String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectSCCValue;xpath");
				locatorValue=locatorValue.replace("SCC", data(scc));   
				if(androiddriver.findElements(By.xpath(locatorValue)).size()!=1)
				{
					clickActionInHHT("buildUphht_btn_selectSCCarrow;xpath",proppathhht,"selectsccarrow",screenName); 
					waitForSync(3);
					androiddriver.findElement(By.xpath(locatorValue)).click();
					waitForSync(3);
				}

				String sccSelected=getPropertyValue(proppathhht, "buildUphht_btn_selectedSCCValue;xpath");
				sccSelected=sccSelected.replace("SCC", data(scc)); 
				if(androiddriver.findElements(By.xpath(sccSelected)).size()==0)
					androiddriver.findElement(By.xpath(locatorValue)).click();

				String contiueBtn=getPropertyValue(proppathhht, "buildUphht_btn_selectcontinuebutton;xpath");
				String okBtn=getPropertyValue(proppathhht, "buildUphht_click_ok;xpath");

				if(androiddriver.findElements(By.xpath(contiueBtn)).size()==1)
				{
					androiddriver.findElement(By.xpath(contiueBtn)).click();
					waitForSync(3);
				}
				else if(androiddriver.findElements(By.xpath(okBtn)).size()==1){
					androiddriver.findElement(By.xpath(okBtn)).click();
					waitForSync(3);
				}
				waitForSync(1);
				enterValueInHHT("buildUphht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);

				waitForSync(2);
				androidScrolllTillPageDown();
				enterValueInHHT("buildUphht_inbx_Wt;accessibilityId",proppathhht,data(wt),"Weight",screenName);
				waitForSync(3);
			}
			catch(Exception e){

				writeExtent("Fail", "Couldn't enter details in build up screen"); 
			}

		
	}

	/**
	 * @author A-9844
	 * @param pcs
	 * @param wgt
	 * @param scc
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : enter shipment details
	 */
	public void enterShipmentDetailsWithSCC(String pcs,String wgt,String scc) throws AWTException, InterruptedException, IOException
	{
		waitForSync(5);
		enterValueInHHT("buildUphht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		waitForSync(5);
		androidScrolllTillPageDown();
		enterValueInHHT("buildUphht_inbx_Wt;accessibilityId",proppathhht,data(wgt),"Weight",screenName);
		waitForSync(5);
		clickActionInHHT("buildUphht_btn_Save;xpath",proppathhht,"Save",screenName);	
		waitForSync(2);						
		try {
			String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectSCCValue;xpath");
			locatorValue=locatorValue.replace("SCC",data(scc)); 

			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorValue)).click();
				waitForSync(8);
			}
		

			String contiueBtn=getPropertyValue(proppathhht, "buildUphht_btn_selectcontinuebutton;xpath");
			String okBtn=getPropertyValue(proppathhht, "buildUphht_click_ok;xpath");

			if(androiddriver.findElements(By.xpath(contiueBtn)).size()==1)
			{
				androiddriver.findElement(By.xpath(contiueBtn)).click();
				waitForSync(8);
			}
			else if(androiddriver.findElements(By.xpath(okBtn)).size()==1){
				androiddriver.findElement(By.xpath(okBtn)).click();
				waitForSync(8);
			}
		} catch (Exception e) {
  
			writeExtent("Fail", "Could not enter AWB details on "+screenName); 
		}



	}




/** 
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc:click Next
	 */
	public void clickNext() throws AWTException, InterruptedException, IOException
	{

		clickActionInHHT("buildUphht_btn_Next;xpath",proppathhht,"Next",screenName);	
		waitForSync(10);


	}




/**
	 * @author A-9844
	 * Desc: to verify the loadability status
	 * @param category
	 * @throws IOException
	 */
	public void verifyLoadabilityStatus(String loadabilityStatus) throws IOException {

		String locatorPcs=getPropertyValue(proppathhht, "buildUphht_txtPcs;xpath");
		 

		while(!(androiddriver.findElements(By.xpath(locatorPcs)).size()==1))
		{
			waitForSync(2);
		}
		String LoadabilityStatusText=getTextFromHHT("buildUphht_txt_loadabilityStatus;xpath", proppathhht, "loadability status", screenName);
		
		if(LoadabilityStatusText.equals(data(loadabilityStatus)))
		{
			writeExtent("Pass", "Loadability status "+data(loadabilityStatus)+" is disaplyed on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Loadability status "+data(loadabilityStatus)+" is not disaplyed on "+screenName);
		}
		waitForSync(5);
	}
	/**
	 * @author A-8783
	 * Desc - Enter buildup location
	 * @param location
	 * @throws IOException
	 */
	public void enterBuildupLocation(String location) throws IOException {
		waitTillMobileElementDisplay(proppathhht,"buildUphht_inbx_location;xpath","xpath");
		clearValueInHHT("buildUphht_inbx_location;xpath",proppathhht,"Buildup location",screenName);

		enterValueInHHT("buildUphht_inbx_location;xpath",proppathhht,data(location),"Buildup location",screenName);
		waitForSync(3);

	}
	
	/**
	 * Desc : Verify buildup location
	 * @author A-9175
	 * @param BuildupLoc
	 * @throws IOException
	 */
	public void verifyBuildupLocation(String BuildupLoc) throws IOException
	{
		try
		{
			
			String locatorValue=getPropertyValue(proppathhht, "buildUphht_inbx_location;xpath");
			String buildupLoc=androiddriver.findElement(By.xpath(locatorValue)).getText();

			if(data(BuildupLoc).equals(buildupLoc))
			{
				writeExtent("Pass", "Sucessfully Verified Buildup location as :" + data(BuildupLoc) +screenName);
			}
			else
			{
				writeExtent("Fail", "Failed to verify Verified Buildup location as :" + data(BuildupLoc) +screenName);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Location Verification Failed  "+screenName);
		}

	}

	/**
	 * @author A-7271
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : updateFlightDetailsWithOutPopUpCurrentDay
	 */
	public void updateFlightDetailsWithOutPopUpCurrentDay(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
	{
		
		waitForSync(5);
		enterValueInHHT("buildUphht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
		waitForSync(2);
		enterValueInHHT("buildUphht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
		waitForSync(2);
		
		/*********************************************************************/
		// ADDED THE CODE FOR HANDLING THE INVALID FLIGHT POP UP
		waitForSync(2);
		String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
		locatorValue=locatorValue.replace("*", "Invalid Flight"); 
		waitForSync(5);
		if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
		{
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(8);
		}
       	/*********************************************************************/
           
		if(flightDate.equals("currentDay"))
		{
			clickActionInHHT("buildUphht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
		}

		else if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("buildUphht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
		}
		clickActionInHHT("buildUphht_btn_next2;xpath",proppathhht,"Next",screenName);
		waitForSync(10);
		
		/**Flight Details Updation Confirmation Pop Up and Clicking Yes**/
		
		String locatorYes=getPropertyValue(proppathhht, "btn_Yes;xpath");
		
		if(androiddriver.findElements(By.xpath(locatorYes)).size()==1)
		{
			androiddriver.findElement(By.xpath(locatorYes)).click();
			waitForSync(2);
		}
		
		waitForSync(10);
	
	

	}


	/**
	 * 
	 * @param Pieces
	 * @throws IOException
	 * Desc : verify pieces
	 */
	public void verifyPieces(String Pieces) throws IOException
    {
          try
          {
          String locator=getPropertyValue(proppathhht, "buildUphht_txt_pcs;xpath");
          locator = locator.replace("*", data("Pieces"));
          String actualText=androiddriver.findElement(By.xpath(locator)).getText(); 
          waitForSync(3);
          verifyScreenTextWithExactMatch("Build Up", Pieces, actualText, "Pieces verified successfully",
					"Pieces verified successfully"); 
          }
          catch(Exception e)
          {
       	   writeExtent("Fail", "Failed to verify Pieces "+" in "+screenName);  
          }
                        }
	
	/**
	 * 
	 * @param Dimension
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : vreify dimensions 
	 */
	public void verifyDimensions(String Dimension) throws AWTException, InterruptedException, IOException
    {
try
{
String locatorValue=getPropertyValue(proppathhht, "buildUphht_txt_dimension;xpath");
locatorValue=locatorValue.replace("*",Dimension);  
String actText=androiddriver.findElement(By.xpath(locatorValue)).getText();
verifyScreenTextWithExactMatch("Build Up", Dimension,actText, "Dimensions verified successfully",
		"Dimensions verified successfully");	               
}
catch(Exception e)
{
writeExtent("Fail", "Failed to verify Dimensions "+" in "+screenName); 
}


}

	/**
	 * @author A-9844
	 * @param value
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Reentering existing uld number (not new) in hht Screen
	 */
	public void reenterValue(String value) throws AWTException, InterruptedException
	{
		try
		{
			enterValueInHHT("buildUphht_inbx_enterValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
			clickActionInHHT("buildUphht_btn_next;xpath",proppathhht,"Next",screenName);
			waitForSync(4);
			writeExtent("Pass", "Value "+ data(value)+" entered in "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Value "+ data(value)+" not entered in "+screenName);
		}
	}

	/**
	 * @author A-9844
	 * Desc..Accept alert message and click Continue button
	 * @throws IOException
	 */
	public void acceptAlertMessageAndContinue(String expText) throws IOException
	{
		try
		{
			int size=getSizeOfMobileElement("btn_Continue;xpath",proppathhht);
			String locatorValue=getPropertyValue(proppathhht, "text_alertPopUpText;xpath");
			String alertMessage=androiddriver.findElement(By.xpath(locatorValue)).getText();
			if(size==1)
			{
				if(alertMessage.equals(data(expText)))
				{
					clickActionInHHT("btn_Continue;xpath",proppathhht,"Continue button",screenName);	
					writeExtent("Pass", "Warning message came as "+ alertMessage +screenName);
				}
				else
				{
					writeExtent("Fail", "Warning message came as "+ alertMessage +screenName);
				}
			}
			else
			{
				writeExtent("Fail", "No alert message got displayed on "+screenName);
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Could not click on continue button  "+screenName);
		}

	}
	/** 
	 * @author A-9844
	 * @param option
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc:To click on the menu button and choose the option displayed
	 */
	public void clickMenuAndChooseOption(String option) throws AWTException, InterruptedException, IOException
	{
		try
		{
			clickActionInHHT("buildUphht_menuButton;xpath",proppathhht,"Menu Button",screenName);	
			waitForSync(2);
			String locatorValue=getPropertyValue(proppathhht, "buildUphht_optionFromMenu;xpath");
			locatorValue=locatorValue.replace("*",data(option)); 
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);
		}
		catch(Exception e){
			writeExtent("Fail", "Could not select the option "+data(option)+" from the menu"); 
		}
	}
	/**
	 * @author A-9175
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : updateFlightDetailsWithOutPopUp with POU
	 */
	public void updateFlightDetailsWithOutPopUpWithPOU(String carrCode, String flightNo, String flightDate, String POU)
			throws AWTException, InterruptedException, IOException {

		if (flightDate.equals("currentDay")) {
			flightDate = "nextDay";
		} else if (flightDate.equals("selectCurrentDay")) {
			flightDate = "currentDay";
		}
		waitForSync(5);
		clickActionInHHT("buildUphht_inbx_carrierCode;accessibilityId", proppathhht, "Current Date", screenName);
		enterValueInHHT("buildUphht_inbx_carrierCode;accessibilityId", proppathhht, data(carrCode), "Carrier Code",
				screenName);
		waitForSync(2);
		clickActionInHHT("buildUphht_inbx_flightNumber;accessibilityId", proppathhht, "Current Date", screenName);
		waitForSync(2);
		/****
		 * enterValueInHHT("buildUphht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight
		 * No",screenName);
		 ***/

		enterFlightNumber(data(flightNo));

		waitForSync(2);
		/*********************************************************************/
		// ADDED THE CODE FOR HANDLING THE INVALID FLIGHT POP UP
		/***
		 * waitForSync(2); String locatorValue=getPropertyValue(proppathhht,
		 * "btn_errorMsg;xpath"); locatorValue=locatorValue.replace("*",
		 * "Invalid Flight"); waitForSync(5);
 * if(androiddriver.findElements(By.xpath(locatorValue)).size()==1) {
		 * androiddriver.findElement(By.xpath(locatorValue)).click();
		 * waitForSync(8); }
		 ****/
		/*********************************************************************/

		/*** waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_next2;xpath","xpath"); ***/
		if (flightDate.equals("currentDay")) {
			clickActionInHHT("buildUphht_btn_currentDate;xpath", proppathhht, "Current Date", screenName);
		}

		else if (flightDate.equals("nextDay")) {
			clickActionInHHT("buildUphht_btn_nextDate;xpath", proppathhht, "Next Date", screenName);

		}

		/******* Select POU ***/
		waitTillMobileElementDisplay(proppathhht, "buildUphht_btn_pou;xpath", "xpath", 20);
		String locatorValue = getPropertyValue(proppathhht, "buildUphht_btn_pou;xpath");
		locatorValue = locatorValue.replace("pou", data(POU));
		androiddriver.findElement(By.xpath(locatorValue)).click();
		waitForSync(2);
		waitTillMobileElementDisplay(proppathhht, "buildUphht_btn_next2;xpath", "xpath");

		// click Next Button
		for (int i = 0; i < 2; i++) {

			String locatorNext = getPropertyValue(proppathhht, "buildUphht_btn_next2;xpath");

			androiddriver.findElement(By.xpath(locatorNext)).click();
			waitForSync(10);
			String locatorValue1 = getPropertyValue(proppathhht, "btn_errorMsg;xpath");
			locatorValue1 = locatorValue1.replace("*",
					"Do you want to assign this Carrier/Shipper Built ULD to the flight specified above?");
			waitForSync(5);

			if (androiddriver.findElements(By.xpath(locatorValue1)).size() == 1) {
				androiddriver.findElement(By.xpath(locatorValue1)).click();
				writeExtent("Pass",
						"Clicked yes on Do you want to assign this Carrier/Shipper Built ULD to the flight specified above ");
				waitForSync(8);
				int size = getSizeOfMobileElement("btn_Continue;xpath", proppathhht);
				if (size == 1)
					acceptAlertMessageAndContinue(
							"val~The shipment is not booked to the flight. Do you want to proceed?");
			}

			int size = getSizeOfMobileElement("buildUphht_inbx_Awb;accessibilityId", proppathhht);
			if ((size > 0)) {

				break;
			}

		}

		/** Flight Details Updation Confirmation Pop Up and Clicking Yes **/

		String locatorYes = getPropertyValue(proppathhht, "btn_Yes;xpath");

		if (androiddriver.findElements(By.xpath(locatorYes)).size() == 1) {
			androiddriver.findElement(By.xpath(locatorYes)).click();
			waitForSync(2);
		}

		waitForSync(10);

	}
	public void captureCheckSheetWithMultiFormats() throws IOException
	{
		//Getting the number of checksheet templates displayed
		List<MobileElement> templates=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_btn_checksheetButton;xpath")));	
	   	
   		for(MobileElement temp:templates)
   		{	
   			//Getting templates Questions Count	
   			String questionsCount= androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "buildUphht_btn_checksheetButton;xpath")+"//preceding-sibling::android.widget.TextView[contains(@text,'/')]")).getText();
   			String Count=questionsCount.split("/")[1];
   			
   			//Getting on to each template
   			temp.click();	
   			waitForSync(2);
   		
   			List<MobileElement>answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));  			
   			List<MobileElement>answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
   			List<MobileElement> textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));	
   			List<MobileElement>Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
   			String RadioAnswers[]=getPropertyValue(checksheetpath, "RadioAnswers").split(",");

   			captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);
   	   	
   			/*** Inorder to Scroll till last Question of that template  **/
   			
   			String locatorValue=getPropertyValue(proppathhht, "gahht_txt_lastQuestion;xpath").replace("lastQues",Count);		
   			while(androiddriver.findElements(By.xpath(locatorValue)).size()!=1)
   			{
   				swipeAndroidScreen();
   				
     		answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));
            answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
   			textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));		
   			Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));

   			captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);
   		
   			}
   			
   			androidScrolllTillPageDown();		
   			answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));
            answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
   			textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));		
   			Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
   			captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);			

   			/*** *********************   *******************  ***/
   					
   			//Click OK after capturing each Checksheet template
   			clickActionInHHT("buildUphht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);
   			waitForSync(2);	

   		}

   	}
	 /**
     * @author A-9847
     * @Desc Click on Save if present
     * @throws AWTException
     * @throws InterruptedException
     * @throws IOException
     */
    public void clickOnSave() throws AWTException, InterruptedException, IOException
    {
   
        int sizeEle=getSizeOfMobileElement("gahht_btn_checksheetSave;xpath",proppathhht); 
        if(sizeEle!=0)
        {
             clickActionInHHT("gahht_btn_checksheetSave;xpath",proppathhht," Save ",screenName);
             waitForSync(3);
        }
     
    }
    
	public void captureChecksheetAnswers( List<MobileElement> answers,List<MobileElement> textfields,List<MobileElement>answersRadioYes,List<MobileElement>Totalquestions,String [] RadioAnswers){


    	//Yes/No Options
    	for(MobileElement answer1:answers)
    	{		
    		answer1.click();
    		waitForSync(2);	

    		/*** Handling non-obligatory Questions ****/
    		String noOption=getPropertyValue(proppathhht, "gahht_btn_NoOpt;xpath");	
    		String warning=getPropertyValue(proppathhht, "gahht_btn_Warning;xpath");	
    		if(androiddriver.findElements(By.xpath(warning)).size()!=0)
    			androiddriver.findElement(By.xpath(noOption)).click();	

    	}

    	//TextFields
    	for(MobileElement text:textfields)
    	{
    		text.sendKeys("Yes");
    		waitForSync(2);
    	}

    	//Yes/No/NA radiobuttons
    	for(MobileElement answer2:answersRadioYes)
    	{
    		answer2.click();
    		waitForSync(2);

    		/*** Handling non-obligatory Questions ****/
    		String noOption=getPropertyValue(proppathhht, "gahht_btn_NoOpt;xpath");	
    		String warning=getPropertyValue(proppathhht, "gahht_btn_Warning;xpath");	
    		if(androiddriver.findElements(By.xpath(warning)).size()!=0)
    			androiddriver.findElement(By.xpath(noOption)).click();

    	}


    	//Handling the radio button with Answers
    	for(int i=0;i<RadioAnswers.length;i++){
    		String locator=getPropertyValue(proppathhht, "gahht_checksheet_radiobutton;xpath").replace("*",RadioAnswers[i]);	
    		if(androiddriver.findElements(By.xpath(locator)).size()==1)
    			androiddriver.findElement(By.xpath(locator)).click();
    		locator="";
    	}

    	/**	//Handling Obligatory Questions - No
		String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestionschecksheet_BuildUp");		
		for(MobileElement quest:Totalquestions)
		{
			String text=quest.getText().replace("*","");
			if (ObgQuest.contains(text))
			{
				String loc=getPropertyValue(proppathhht, "gahht_obligatoryquestNo;xpath").replace("*", text);	
	if(androiddriver.findElements(By.xpath(loc)).size()!=1)
				scrollMobileDevice(text);		
				androiddriver.findElement(By.xpath(loc)).click(); 

			}
		}	**/



	}

	public void updateFlightDetailsWithOutPopUp(String carrCode,String flightNo,String flightDate,String pou) throws AWTException, InterruptedException, IOException
	{
		if(flightDate.equals("currentDay"))
		{
			flightDate="nextDay";
		}
		waitForSync(5);
		enterValueInHHT("buildUphht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
		waitForSync(2);
		enterValueInHHT("buildUphht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
		waitForSync(2);
		if(flightDate.equals("currentDay"))
		{
			clickActionInHHT("buildUphht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
		}

		else if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("buildUphht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
		}
		waitForSync(2);

		/*******Select POU***/
		String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_pou;xpath");
		locatorValue=locatorValue.replace("pou", data(pou));  
		androiddriver.findElement(By.xpath(locatorValue)).click();
		clickActionInHHT("buildUphht_btn_next2;xpath",proppathhht,"Next",screenName);
		waitForSync(10);
		
	}
		
	
	/**
	 * @author A-9844
	 * Desc..Accept alert message
	 * @throws IOException
	 */
	public void acceptAlertMessage(String expText) throws IOException
	{
		try
		{
			int size=getSizeOfMobileElement("btn_Yes;xpath",proppathhht);
			String locatorValue=getPropertyValue(proppathhht, "text_alertMsg2;xpath");
			String alertMessage=androiddriver.findElement(By.xpath(locatorValue)).getText();
			if(size==1)
			{
				if(alertMessage.equals(data(expText)))
				{
					clickActionInHHT("btn_Yes;xpath",proppathhht,"yes button",screenName);	
					writeExtent("Pass", "Warning message came as "+ alertMessage +screenName);
				}
				else
				{
					writeExtent("Fail", "Warning message came as "+ alertMessage +screenName);
				}
			}
			else
			{
				writeExtent("Fail", "No alert message got displayed on "+screenName);
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Could not click on yes button  "+screenName);
		}

	}
	/** 
	 * @author A-9844
	 * @param pcs
	 * @param  actualwt
	 * @param location
	 * @param weightscaleid
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc:Enter ULD actual weight details like weigh scale id,actual weight ,location
	 */
	public void enterULDActualweight(String location,String actwt,String scaleid) throws AWTException, InterruptedException, IOException
	{
		try
		{

			waitTillMobileElementDisplay(proppathhht,"buildUphht_inbx_location1;xpath","xpath",10);
			enterValueInHHT("buildUphht_inbx_location1;xpath",proppathhht,data(location),"location",screenName);
			waitForSync(2);
			androidScrolllTillPageDown();

			enterValueInHHT("buildUphht_inbx_weighscaleid;xpath",proppathhht,data(scaleid),"Weigh scale id",screenName);
			String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
			locatorValue=locatorValue.replace("*", "Unable to fetch data"); 
			waitForSync(3);
			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorValue)).click();
				waitForSync(3);
			}
			androidScrolllTillPageDown();
			clickActionInHHT("buildUphht_inbx_weighscale;xpath",proppathhht,"Save",screenName);
			waitForSync(1);
			enterFlightNumber(data(actwt));
			String locatorValue1=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
			locatorValue1=locatorValue1.replace("*", "Please scan the Barcode from specified Scale ID Interface"); 
			waitForSync(3);
			if(androiddriver.findElements(By.xpath(locatorValue1)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorValue1)).click();
				waitForSync(3);
			}
			enterValueInHHT("buildUphht_inbx_reenterweighscale;xpath",proppathhht,data(actwt),"Actual weight",screenName);
			waitForSync(2);
			clickActionInHHT("buildUphht_btn_uldweightsave;xpath",proppathhht,"Save",screenName);


		}
		catch(Exception e){

			writeExtent("Fail", "Couldn't enter details in build up screen"); 
		}

	}


	/**
	 * Desc : Select HAWB
	 * @author A-10328
	 * @param HAWB
	 * @throws IOException
	 */
public void clickSelectHAWB() throws IOException
	{
		waitForSync(3);
		scrollInMobileDevice("SelectHAWB");
		waitForSync(2);
		for(int i=0;i<2;i++)
{
clickActionInHHT("buildUphht_txt_SelectHAWB;xpath",proppathhht,"SelectHAWB",screenName);
waitForSync(4);
}
}
/**
 * Desc : Select SCC
 * @author A-10328
 * @param SCC
 * @throws IOException
 */

public void selectSCCValue(String SCC) throws IOException
 {
	//Scroll down     
			scrollInMobileDevice("SCC");
			clickActionInHHT("buildUphht_btn_clickSCC;xpath",proppathhht,"SCC",screenName);
			waitForSync(5);
			try
			{
				String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectSCCValue;xpath");
				locatorValue=locatorValue.replace("SCC", data(SCC));   
				androiddriver.findElement(By.xpath(locatorValue)).click();
				waitForSync(5);
				writeExtent("Pass", "Successfully selected SCC value "+data(SCC)+" in "+screenName);
				int size = getSizeOfMobileElement("gahht_btn_Ok;xpath", proppathhht); 
				if (size == 1)  { 

					clickActionInHHT("gahht_btn_Ok;xpath", proppathhht, " SCC Ok ", screenName); 
					waitForSync(5); 
				} 
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Couldn't select SCC value "+data(SCC)+" in "+screenName);
			}

}       

/**
	 * Desc : Click Select all
	 * @author A-10328
	 * @throws IOException
	 */	

public void clickSelectAll() throws IOException
	{
		
clickActionInHHT("buildUphht_btn_selectAll;xpath",proppathhht,"Select All button",screenName);
waitForSync(4); 
	}
/**
 * Desc : Save
 * @author A-10328
 * @param awbNo
 * @throws AWTException
 * @throws InterruptedException
 * @throws IOException
 */
public void clickSave() throws AWTException, InterruptedException, IOException
{

try
{
clickActionInHHT("buildUphht_btn_overhangIndendSave;xpath",proppathhht,"Save",screenName);
waitForSync(10);
writeExtent("Pass", "details saved successfully in "+screenName);
}
catch (Exception e)
{
 captureScreenShot("Android");
writeExtent("Fail", "details not saved successfully in "+screenName);
}
}
/**
 * Desc : Click Ok
 * @author A-10328
 * @throws IOException
 */
public void clickOk() throws IOException
{
clickActionInHHT("buildUphht_click_ok;xpath",proppathhht,"Click on ok",screenName);		
waitForSync(4); 
}

/**
 * Desc : Capture CheckSheet
 * @author A-10328
 * @param answer
 * @throws IOException
 */


public void captureCheckSheet(String answer) throws IOException
{
	clickActionInHHT("buildUphht_txt_checksheet;xpath",proppathhht,"Capture Checksheet",screenName);
	waitForSync(3);
	List<MobileElement>questions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_txt_question;xpath")));
	System.out.println(questions.size());
	for(MobileElement quest:questions)
	{
	clickActionInHHT("buildUphht_txt_chksheetyes;xpath",proppathhht,"Yes",screenName);
	String text=quest.getText();
	System.out.println(text);
	if(text.contains(answer))		
	clickActionInHHT("txt_no;xpath",proppathhht,"No",screenName);
					
	}	
	clickActionInHHT("buildUphht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);
}	

public void enterTCONvalue(String value) throws AWTException, InterruptedException
{


	Robot r=new Robot();      
	String a=value;
	char c;
	int d=a.length(),e=0,f=0;

	 while(e<d)
	{

		c=a.charAt(e);
		f=(int) c; //converts character to Unicode. 
		r.keyPress(KeyEvent.getExtendedKeyCodeForChar(f));
		e++;

		Thread.sleep(150);
	}

}


	/**
	 * @author A-10690
	 * @param Tcon
	 * @param flight
	 * @param date
	 * Desc : verifying the warning message displayed on selecting TCON
	 * @throws IOException 
	 */
	
    public void verifyWarningmessage(String tcon,String flight,String date) throws IOException
    {
        
 	   try
 	   {
 		   
 		  String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_warningmessageonTCONselection;xpath");
 		  String expmessage="Shipment assignment restricted since " +data(tcon)+ " is already weighed for "+data(flight)+" "+data(date)+". Do you want to release the barrow?";
	        locatorValue=locatorValue.replace("warning",expmessage);
	        int size=androiddriver.findElements(By.xpath(locatorValue)).size();
	        waitForSync(3);
 
 		   if(size==1)
 		   {
 			   writeExtent("Pass", "Warning message is getting displayed on entering TCON on "+screenName);
 			  clickActionInHHT("builduphht_btn_warningConfirmationYes;xpath",proppathhht,"YES button",screenName);
 			  waitForSync(3);
 			   
 		   }
 		   else
 		   {
 			   captureScreenShot("Android");
 			   writeExtent("Fail", "Warning message is not getting displayed on entering TCON "+screenName);
 		   }
 	   }

 	   catch(Exception e)
 	   {
 		   captureScreenShot("Android");
			   writeExtent("Fail", "Warning message is not getting displayed on entering TCON on "+screenName);
 	   }
    }

	/**
     * @Description : Click NO to TopUP POP up
     * @author A-9175
     * @throws AWTException
     * @throws InterruptedException
     * @throws IOException
     */
     public void clickTopUpNoOption() throws AWTException, InterruptedException, IOException
  {
        waitForSync(3); 
        clickActionInHHT("buildUphht_btn_Nobtn;xpath",proppathhht,"No button",screenName);
        waitForSync(3); 
  }
     /**
 	 * @Descriptions : Verifying Big Reference number is auto populated
 	 * @author A-9175
 	 * @param bigref
 	 * @throws IOException
 	 */
 	public void verifyBigReferenceNumber(String bigref) throws IOException
     {
 		scrollInMobileDevice(data(bigref));
           try
           {
         	  int size=getSizeOfMobileElement("buildUphht_inbx_big_reference_number;accessibilityId",proppathhht);                             
                 if(size==1)
                 {
                 writeExtent("Pass", "Found Big Reference Number as:"+data(bigref)+screenName);
                 }
                 else
                 {
                       captureScreenShot("Android");
                       writeExtent("Fail", "Not found Big Reference Number as:"+data(bigref)+screenName);
                 }
           }
           catch(Exception e)
           {
                 captureScreenShot("Android");
                 writeExtent("Fail", "Not found Big Reference Number "+screenName);
           }

     }

 	/**
     * Desc : Click capture ULD weight button  
     * @author A-10690
     * @throws AWTException
     * @throws InterruptedException
 * @throws IOException 
     */
     public void clickCaptureULDWeigh() throws AWTException, InterruptedException, IOException
     {
    	 clickActionInHHT("buildUphht_btn_captureULDweight;xpath",proppathhht,"Capture ULD weight ",screenName);
    	 waitForSync(2);
    	 int size=getSizeOfMobileElement("buildUphht_div_MoreActions;xpath",proppathhht);

    	 if(size==1)
    	 {
    		 clickActionInHHT("buildUphht_btn_captureULDweight;xpath",proppathhht,"Capture ULD weight ",screenName);
    	 }
    	 waitTillMobileElementDisplay(proppathhht,"buildUphht_inbx_weighscale;xpath","xpath");


          
     }
     
     /** 
		 * @author A-10690
		 * @param pcs
		 * @param  actualwt
		 * @param location
		 * @param weightscaleid
		 * @param ULDheight
		 * @throws AWTException
		 * @throws InterruptedException
		 * @throws IOException
		 * Desc:Enter ULD actual weight details like weigh scale id,actual weight ,location
		 */
		public void enterULDActualweight(String location,String actwt,String height,String scaleid) throws AWTException, InterruptedException, IOException
		{
			try
			{
				
				/***enterWeightCaptureLocationFromList();****/
				updateUldHeight(height);
				waitForSync(1);
				androidScrolllTillPageDown();

				enterValueInHHT("buildUphht_inbx_weighscaleid;xpath",proppathhht,data(scaleid),"Weigh scale id",screenName);
				String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
				locatorValue=locatorValue.replace("*", "Unable to fetch data"); 
				waitForSync(3);
				if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
				{
					androiddriver.findElement(By.xpath(locatorValue)).click();
					waitForSync(3);
				}
				androidScrolllTillPageDown();
				clickActionInHHT("buildUphht_inbx_weighscale;xpath",proppathhht,"Save",screenName);
				waitForSync(1);
				enterFlightNumber(data(actwt));
				String locatorValue1=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
				locatorValue1=locatorValue1.replace("*", "Please scan the Barcode from specified Scale ID Interface"); 
				waitForSync(3);
				if(androiddriver.findElements(By.xpath(locatorValue1)).size()==1)
				{
					androiddriver.findElement(By.xpath(locatorValue1)).click();
					waitForSync(3);
				}
				enterValueInHHT("buildUphht_inbx_reenterweighscale;xpath",proppathhht,data(actwt),"Actual weight",screenName);
				waitForSync(2);
				clickActionInHHT("buildUphht_btn_uldweightsave;xpath",proppathhht,"Save",screenName);

			}
			catch(Exception e){

				writeExtent("Fail", "Couldn't enter details in build up screen"); 
			}


	}

	/**
	 * @author A-6260
	 * Desc-To select the uld category
	 * @param category
	 * @throws IOException
	 */
	public void selectUldCategory(String category) throws IOException {
		try
		{		
			waitForSync(5);
			String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_uldCategory;xpath");
	        locatorValue=locatorValue.replace("category",category);  
	        androiddriver.findElement(By.xpath(locatorValue)).click();
	        waitForSync(3);
	        clickActionInHHT("buildUphht_btn_overhangIndendSave;xpath",proppathhht,"Save",screenName);
	        writeExtent("Pass"," uld category selected in "+ screenName);
		}catch(Exception e)
		{
			 captureScreenShot("Android");
			 writeExtent("Fail"," Couldnt select uld category in  "+ screenName);
		}
	}
	/**
	 * @author A-6260
	 * Desc..Accept alert message
	 * @throws IOException
	 */
		public void acceptAlertMessage() throws IOException
		{
			try
			{
				int size=getSizeOfMobileElement("btn_Yes2;xpath",proppathhht);
				String locatorValue=getPropertyValue(proppathhht, "text_alertMsg;xpath");
				String alertMessage=androiddriver.findElement(By.xpath(locatorValue)).getText();

				if(size==1)
				{
					clickActionInHHT("btn_Yes2;xpath",proppathhht,"yes button",screenName);	
					writeExtent("Pass", "Clicked on yes button for alert "+ alertMessage +screenName);
				}
				else
				{
					captureScreenShot("Android");
					writeExtent("Fail", "Could not click on Yes button for alert "+ alertMessage +screenName);
				}
			}

			catch(Exception e)
			{
				writeExtent("Fail", "Could not click on yes button  "+screenName);
			}

		}
		
		/**
		 * 
		 * @throws InterruptedException
		 * @throws IOException
		 * Desc : capture check sheet
		 */
		public void clickSaveCaptureChecksheet() throws InterruptedException, IOException
		{

			
			try
			{
				waitTillMobileElementDisplay(proppathhht,"gahht_btn_checksheetSave;xpath","xpath",10);
				int size=getSizeOfMobileElement("gahht_btn_checksheetSave;xpath",proppathhht);                             
				if(size==1)
				{
					waitTillMobileElementDisplay(proppathhht,"buildUphht_txt_checksheet;xpath","xpath");
					waitForSync(3);
					/***********************************************/

					//captureCheckSheet("NA");		
					captureCheckSheetWithMultiFormats();


					/*************************************************/
					waitForSync(1);
					clickActionInHHT("gahht_btn_checksheetSave;xpath",proppathhht," Save Capture Checksheet ",screenName);
					writeExtent("Pass", "Saved Checksheet Details" +screenName);
					waitForSync(4);
				}
				else
				{
					writeExtent("Info", "Not Found Checksheet Details for save" +screenName);
				}
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Could not Found Checksheet Details for save" +screenName);
			}


		}

			

		


		/**
		 * 
		 * @throws InterruptedException
		 * @throws IOException
		 * Desc : capture check sheet
		 */
		public void clickSaveCaptureChecksheet(boolean chkSheet) throws InterruptedException, IOException
		{

			
	            try
	         {
	              int size=getSizeOfMobileElement("gahht_btn_checksheetSave;xpath",proppathhht);                             
	               if(size==1)
	               {
	                   waitForSync(2);
	                      clickActionInHHT("gahht_btn_checksheetSave;xpath",proppathhht," Save Capture Checksheet ",screenName);
	                   writeExtent("Pass", "Saved Checksheet Details" +screenName);
	                   waitForSync(2);
	               }
	               else
	               {
	            	   if(chkSheet)
	            	   
	                   writeExtent("Fail", "Not Found Checksheet Details for save" +screenName);
	            	   
	            	   else
	            	   
	            		   writeExtent("Info", "Not Found Checksheet Details for save" +screenName);
	            	   
	               }
	         }
	         catch(Exception e)
	            {
	               writeExtent("Fail", "Could not Found Checksheet Details for save" +screenName);
	            }


			}


		

		

		/**
		 * 
		 * @param pcs
		 * @param wgt
		 * @throws AWTException
		 * @throws InterruptedException
		 * @throws IOException
		 * Desc : enter shipment details
		 */
		public void enterShipmentDetails(String pcs,String wgt) throws AWTException, InterruptedException, IOException
		{
			String locatorPcs=getPropertyValue(proppathhht, "buildUphht_inbx_Pcs;accessibilityId");
			 

			if(!(androiddriver.findElements(By.xpath(locatorPcs)).size()==1))
			{
				waitForSync(2);
			}
			enterValueInHHT("buildUphht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
			waitForSync(3);
			enterValueInHHT("buildUphht_inbx_Wt;accessibilityId",proppathhht,data(wgt),"Weight",screenName);
			waitForSync(5);
			clickActionInHHT("buildUphht_btn_Save;xpath",proppathhht,"Save",screenName);	
			waitForSync(2);						
			try {
				String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectSCCValue;xpath");
				locatorValue=locatorValue.replace("SCC",data("SCC")); 
				
				
				// SCC will be auto selected

				/****if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
				{
					androiddriver.findElement(By.xpath(locatorValue)).click();
					waitForSync(8);
				}*****/
				
				String contiueBtn=getPropertyValue(proppathhht, "buildUphht_btn_selectcontinuebutton;xpath");
				String okBtn=getPropertyValue(proppathhht, "buildUphht_click_ok;xpath");
				
				if(androiddriver.findElements(By.xpath(contiueBtn)).size()==1 )
				{
					androiddriver.findElement(By.xpath(contiueBtn)).click();
					waitForSync(8);
				}
				else if(androiddriver.findElements(By.xpath(okBtn)).size()==1){
					androiddriver.findElement(By.xpath(okBtn)).click();
					waitForSync(8);
				}
			} catch (Exception e) {
				
			}
		}

		/** 
		 * @author A-10690
		 * @param pcs
		 * @param wt
		 * @throws AWTException
		 * @throws InterruptedException
		 * @throws IOException
		 * Desc:Enter pieces and verify whether the weight gets autopopulated
		 */
		public void enterPiecesandVerifyWeight(String pcs,String wt) throws AWTException, InterruptedException, IOException
		{
			try
			{
				enterValueInHHT("buildUphht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
				waitForSync(2);
				String actualweight=getTextFromHHT("buildUphht_inbx_Wt;accessibilityId",proppathhht,wt,"Build Up");
				verifyScreenTextWithExactMatch("Build Up", data(wt),actualweight, "verifyweightautopopulated",
						"verifyweightautopopulated");
				clickActionInHHT("buildUphht_btn_Save;xpath",proppathhht,"Save",screenName);	
				waitForSync(4);


			}
			catch(Exception e){

				writeExtent("Fail", "Couldn't verify weights in build up screen"); 
			}

			

		}

		 /**
		       * @author A-9847
		       * @Desc To click BuildUpComplete without capturing Actual Weight of ULD
		       * @throws AWTException
		       * @throws InterruptedException
		       * @throws IOException
		       */
		      public void clickBuildUpCompleteWithoutWeightCapture() throws AWTException, InterruptedException, IOException
		      {
		    	       clickMoreOptions();
		               clickActionInHHT("buildUphht_btn_buildUpComplete;xpath",proppathhht,"Buildup Complete",screenName); 
		               waitForSync(8); 
		                  
		      }
	/**
	 * @author A-6260
	 * Desc: to verify the overhang category
	 * @param category
	 * @throws IOException
	 */
	public void verifyOverhangCategory(String category) throws IOException {
		waitForSync(2);
		scrollInMobileDevice("Overhang Category");
	  	String overhangeCategory=getTextFromHHT("buildUphht_txt_overhangCategory;xpath", proppathhht, "overhang category", screenName);
	  	if(overhangeCategory.equals(category))
		{
		 writeExtent("Pass", "Overhang category "+overhangeCategory+" is disaplyed on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			 writeExtent("Fail", "Overhang category "+overhangeCategory+" is not disaplyed on "+screenName);
		}
	  	clickActionInHHT("buildUphht_btn_overhangIndendSave;xpath",proppathhht,"Save",screenName);
	  	waitForSync(5);
	}
	/**
	 * @author A-6260
	 * @throws IOException
	 * Desc : verify overhang category
	 */
	public void verifyOverhangCategory() throws IOException {
		waitForSync(2);
		scrollInMobileDevice("Overhang Category");
	  	String overhangeCategory=getTextFromHHT("buildUphht_txt_overhangCategory;xpath", proppathhht, "overhang category", screenName);
	  	if(overhangeCategory.equals("A")||overhangeCategory.equals("B")||overhangeCategory.equals("C"))
		{
		 writeExtent("Pass", "Overhang category "+overhangeCategory+" is disaplyed on "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			 writeExtent("Fail", "Overhang category "+overhangeCategory+" is not disaplyed on "+screenName);
		}
	  	clickActionInHHT("buildUphht_btn_overhangIndendSave;xpath",proppathhht,"Save",screenName);
	  	waitForSync(5);
	}
	/**
	 * @author A-9175
	 * @param contour
	 * Desc : capture contour details without save
	 * @throws IOException 
	 */
	public void captureContourWithoutSave(String contour) throws IOException
	{
		
		waitForSync(3);
		clickActionInHHT("buildUphht_btn_selectContour;xpath",proppathhht,"Contour",screenName);
		scrollInMobileDevice(contour);
		waitForSync(5);
		try
		{		
			waitForSync(5);
			String locatorValue=getPropertyValue(proppathhht, "buildUphht_contour;xpath");
	        locatorValue=locatorValue.replace("contour",contour);  
	        androiddriver.findElement(By.xpath(locatorValue)).click();
	        waitForSync(3);
	        clickActionInHHT("buildUphht_btn_ContourSave;xpath",proppathhht,"Save",screenName);
	        waitForSync(5);

	        writeExtent("Pass"," Contour details saved Sucessfully in "+ screenName);
	        
	          
		}

		catch(Exception e)
		{
			 captureScreenShot("Android");
			 writeExtent("Fail"," Contour details not saved Sucessfully in "+ screenName);
		}
	}

	/**
	 * Desc : Click Unitized yes
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickUnitizedYes() throws AWTException, InterruptedException, IOException
	{
			clickActionInHHT("breakdownhht_btn_Unitized;xpath",proppathhht,"Unitized Yes button",screenName);	
			waitForSync(4); 
	}	
	/**
	 * Desc: enter UNID Details Without save 
	 * @author A-9175
	 * @param pcs
	 * @throws IOException
	 */
	public void enterUNIDDetailsWithoutsave(String pcs) throws IOException
	{
		
		waitForSync(1);
		enterValueInHHT("buildUphht_inbx_SPLPcs;accessibilityId",proppathhht,data(pcs),"Weight",screenName);
		clickActionInHHT("buildUphht_btn_NextSpl;xpath",proppathhht,"Next",screenName);
		waitForSync(8);
	}
	/**
	 * Desc : Capturing Overhang Details
	 * @author A-9175
	 * @param front
	 * @param rear
	 * @param left
	 * @param right
	 * @throws IOException
	 */
	public void captureOverhangIndentDetails(String front,String rear,String left,String right) throws IOException
	{
		

		waitForSync(2);
		scrollInMobileDevice("Overhang Category");
		waitForSync(2);
		clearValueInHHT("buildUphht_txt_Front;xpath",proppathhht,"Front",screenName);
		enterValueInHHT("buildUphht_txt_Front;xpath",proppathhht,data(front),"Front",screenName);
		waitForSync(2);
		clearValueInHHT("buildUphht_txt_Left;xpath",proppathhht,"left",screenName);
		enterValueInHHT("buildUphht_txt_Left;xpath",proppathhht,data(left),"Left",screenName);
		waitForSync(2);
		clearValueInHHT("buildUphht_txt_Right;xpath",proppathhht,"Right",screenName);
		enterValueInHHT("buildUphht_txt_Right;xpath",proppathhht,data(right),"Right",screenName);
		waitForSync(2);
		clearValueInHHT("buildUphht_txt_Rear;xpath",proppathhht,"Rear",screenName);
		enterValueInHHT("buildUphht_txt_Rear;xpath",proppathhht,data(rear),"Rear",screenName);
		waitForSync(5);
  	  
		clickActionInHHT("buildUphht_btn_overhangIndendSave;xpath",proppathhht,"Save",screenName);
		waitForSync(5);
	}
	
	/**
	 * @author A-10690
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @param alert pop up
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : updateFlightDetailsWithOutPopUp
	 */
	public void verifyAlert(String carrCode,String flightNo,String flightDate,String alert) throws AWTException, InterruptedException, IOException
	{

		if(flightDate.equals("currentDay"))
		{
			flightDate="nextDay";
		}
		else if(flightDate.equals("selectCurrentDay"))
		{
			flightDate="currentDay";
		}
		waitForSync(5);
		enterValueInHHT("buildUphht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
		waitForSync(2);
		enterValueInHHT("buildUphht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
		waitForSync(2);

		/*********************************************************************/
		// ADDED THE CODE FOR HANDLING THE INVALID FLIGHT POP UP
		waitForSync(2);
		String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
		locatorValue=locatorValue.replace("*", "Invalid Flight"); 
		waitForSync(5);
		if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
		{
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(8);
		}
		/*********************************************************************/

		waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_next2;xpath","xpath");

		if(flightDate.equals("currentDay"))
		{
			clickActionInHHT("buildUphht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
		}

		else if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("buildUphht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
		}
                waitForSync(2);
		waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_next2;xpath","xpath");


		//click Next Button

			String locatorNext=getPropertyValue(proppathhht, "buildUphht_btn_next2;xpath");

			androiddriver.findElement(By.xpath(locatorNext)).click();
			waitForSync(10);
			
			String BuildupclosePopup=getPropertyValue(proppathhht, "buildUphht_closeErrorPopup;xpath");
			BuildupclosePopup=BuildupclosePopup.replace("*",data(alert)); 
			waitForSync(5);
			
			if(androiddriver.findElements(By.xpath(BuildupclosePopup)).size()==1)
				{
				
				androiddriver.findElement(By.xpath(BuildupclosePopup)).click();
				writeExtent("Pass","Verified the alert "+data(alert)+"in "+screenName);
				waitForSync(8);
			}
			else
			{
				writeExtent("Fail","Failed to verify the alert message "+data(alert)+"in "+screenName);
			}

			}
	/**
	 * @author A-6260
	 * Desc: to capture the piece up information
	 * @param piecesFront
	 * @param piecesRear
	 * @param piecesLeft
	 * @param piecesRight
	 * @throws IOException
	 */
	public void capturePiecesUpDetails(String piecesFront,String piecesRear,String piecesLeft,String piecesRight) throws IOException
	{
		
		waitForSync(3);
		enterValueInHHT("buildUphht_txt_PiecesupFront;xpath",proppathhht,data(piecesFront),"PiecesUp Front",screenName);
		waitForSync(2);
		enterValueInHHT("buildUphht_txt_PiecesupRear;xpath",proppathhht,data(piecesRear),"PiecesUp Rear",screenName);
		waitForSync(2);
		enterValueInHHT("buildUphht_txt_PiecesupLeft;xpath",proppathhht,data(piecesLeft),"PiecesUp Left",screenName);
		waitForSync(2);
		enterValueInHHT("buildUphht_txt_PiecesupRight;xpath",proppathhht,data(piecesRight),"PiecesUp Right",screenName);
		waitForSync(5);
		clickActionInHHT("buildUphht_btn_overhangIndendSave;xpath",proppathhht,"Save",screenName);
		waitForSync(5);
	}
	
	/**
     * @author A-9847
     * @Desc To select the Barrow as YES
     * @throws IOException
     */
    public void selectBarrowYes() throws IOException
    {
   	 
   	clickActionInHHT("buildUphht_btn_BarrowYes;xpath",proppathhht,"Barrow",screenName); 
   	waitForSync(2);
    }

/**
     @author A-9844
 	 * verify split Indicator
 	 * @param awb
 	 * @param awb
 	 * @param indicator
 	 * @throws IOException
 	 */
 	
 	public void verifySplitIndicator(String uld,String awb,String indicator) throws IOException{
 	
 	 	      
 			try{
 				
 				String awbArrowLocator=getPropertyValue(proppathhht, "buildUphht_btnAWBArrow;xpath");
 				awbArrowLocator=awbArrowLocator.replace("*",data(uld));
 				androiddriver.findElement(By.xpath(awbArrowLocator)).click(); 
 				waitForSync(5);
 				String awbLocator=getPropertyValue(proppathhht, "buildUphht_AWBText;xpath");
				awbLocator=awbLocator.replace("awb",data(awb));
				waitForSync(2);
 				System.out.println(awbLocator);
 				String actText=androiddriver.findElement(By.xpath(awbLocator)).getText();
 				waitForSync(2);

 				if (actText.contains(indicator)){
 					
					writeExtent("Pass", "Verified split indicator icon is displayed as"+actText+" on "+screenName); 
				}
				else{
					writeExtent("Fail", "Could not verify split indicator icon is displayed as"+actText+" on "+screenName); 
				}



 			}catch(Exception e){
 				writeExtent("Fail", "Could not verify the split indicator on "+screenName);
 			}
 		}




/**
	 * @author A-9844
	 * @Desc Handling popup
	 * @param expText
	 * @throws IOException
	 */

	public void handlePopup(String expText) throws IOException{
		try{

			String popupText=getPropertyValue(proppathhht, "buildUphht_txt_popUpText;xpath");
			popupText=popupText.replace("*",expText);	
			String text=androiddriver.findElement(By.xpath(popupText)).getText();
			if(text.equals(expText))
			{
				clickActionInHHT("btn_Yes;xpath",proppathhht,"Yes Button",screenName);
				waitForSync(5);
				writeExtent("Pass", "Clicked on Yes for the popup '" + text+ "' on " +screenName); 
				
			}
			else
				writeExtent("Fail", "Could not click on Yes for the popup '" + text+ "' on " +screenName); 
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to handle popup on " +screenName); 
		}
	}

	/**
	 * Desc : Capturing Contour Details
	 * @author A-9175
	 * @param contour
	 * @throws IOException
	 */
	public void captureContour(String contour) throws IOException
	{
		
		waitForSync(3);
		clickActionInHHT("buildUphht_btn_selectContour;xpath",proppathhht,"Contour",screenName);
		try
		{
		scrollInMobileDevice(contour);
		}
		
		catch(Exception e)
		{
			
		}
		waitForSync(5);
		try
		{		
			waitForSync(5);
			String locatorValue=getPropertyValue(proppathhht, "buildUphht_contour;xpath");
	        locatorValue=locatorValue.replace("contour",contour);  
	        androiddriver.findElement(By.xpath(locatorValue)).click();
	        waitForSync(3);
	        clickActionInHHT("buildUphht_btn_ContourSave;xpath",proppathhht,"Save",screenName);
	        waitForSync(5);
	        //Verify text "ULD contour saved" in pop up
	    	  verifyULDContourSavedPopUp();
	    	  verifyHHTContourSaveDetails(screenName);
	        waitForSync(5);
	        writeExtent("Pass"," Contour details saved Sucessfully in "+ screenName);
	        
	          
		}

		catch(Exception e)
		{
			 captureScreenShot("Android");
			 writeExtent("Fail"," Contour details not saved Sucessfully in "+ screenName);
		}
	}
	public void handleNewULDWarning() throws IOException{

		String locator = getPropertyValue(proppathhht, "text_alertMsg2;xpath");	
		if(androiddriver.findElements(By.xpath(locator)).size()==1)
		{
			String warning = getTextAndroid("text_alertMsg2;xpath",proppathhht,"New ULD Warning",screenName);
            System.out.println(warning);
			
			if(warning.contains("does not exist in the system. Do you want to continue?"))
				writeExtent("Info","Warning message came as " +warning+" on "+screenName);
			else
				writeExtent("Fail","Warning message came as " +warning+" on "+screenName);

			clickActionInHHT("btn_Yes;xpath",proppathhht,"Yes Button",screenName); 
			waitForSync(3);

		}	

	}


	/**
	 * clicking Capture Overhang Indent
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * @author A-9175
	 */
	public void clickCaptureOverhangIndent() throws AWTException, InterruptedException, IOException
    {
			waitForSync(8); 
            clickActionInHHT("buildUphht_btn_overhangIndend;xpath",proppathhht,"Capture Overhang Indent",screenName);  
            waitForSync(5); 
            int size=getSizeOfMobileElement("buildUphht_div_MoreActions;xpath",proppathhht);

            if(size==1)
            {
            	 clickActionInHHT("buildUphht_btn_overhangIndend;xpath",proppathhht,"Capture Overhang Indent",screenName);  
            	 waitForSync(5); 
            }

    }
	
	/**
	 * Desc : Verifying Flight image Dimentions
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyImageDimentions() throws AWTException, InterruptedException, IOException
    {
			waitForSync(8); 
			String locatorValue=getPropertyValue(proppathhht, "builduphht_flightImage;xpath");
	        int TotdimValue=androiddriver.findElements(By.xpath(locatorValue)).size();
	        if(TotdimValue==5){
	        	writeExtent("Pass","Overhang or Indent Informations for flight found sucessfull in "+ screenName);
	        }else{
	        	writeExtent("Fail","Overhang or Indent Informations for flight Not found in "+ screenName);
	        }          
    }
	/**
	 * @author A-9847
	 * @Desc To enter the Location on WeightCapture
	 * @param location
	 * @throws IOException
	 */
	public void enterWeightCaptureLocation(String location) throws IOException {
		
		waitTillMobileElementDisplay(proppathhht,"buildUphht_inbx_weightCaptureLoc;accessibilityId","accessibilityId");
		enterValueInHHT("buildUphht_inbx_weightCaptureLoc;accessibilityId",proppathhht,data(location),"Weight Capture Location",screenName);

	}
	
	/**
	 * @author A-9847
	 * @Desc To enter the ULD height on Weight Capture page
	 * @param height
	 * @throws IOException
	 */
	public void enterUldHeight(String height) throws IOException {
		enterValueInHHT("buildUphht_inbx_uldHeight;accessibilityId",proppathhht,data(height),"ULD Height",screenName);

	}
	
	/**
	 * @author A-9847
	 * @Desc To click on Get Scale Weight
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	 public void clickGetScaleWeight() throws AWTException, InterruptedException, IOException
     {
          
		  scrollInMobileDevice("Weigh Scale ID");		  
		  String locator=getPropertyValue(proppathhht, "buildUphht_btn_getScaleWeight;xpath");	  
          clickActionInHHT("buildUphht_btn_getScaleWeight;xpath",proppathhht," Get Scale Weight ",screenName);   
          //Double click required
          androiddriver.findElement(By.xpath(locator)).click();
          waitForSync(3);
     }

	
	 /**
	  * @author A-9847
	  * @Desc To verify the Scale Weight Populated on Weight Capture page
	  * @param scaleWeight
	  * @throws AWTException
	  * @throws InterruptedException
	  * @throws IOException
	  */
	 public void verifyScaleWeightAutopopulated(String scaleWeight) throws AWTException, InterruptedException, IOException
     {
          
		 try{
			
		 String actScaleWeight=getTextAndroid("buildUphht_inbx_scaleWeight;accessibilityId",proppathhht,"Actual Scale Weight",screenName);   
	     verifyScreenTextWithExactMatch(screenName, data(scaleWeight), actScaleWeight, "Actual Scale Weight","Verification of Actual Scale Weight");  
		 }
		 catch(Exception e){
			 writeExtent("Fail", "Failed to verify the autopopulation of Scale Weight on "+screenName);
		 }
     }
	 
	 
	 /**
	  * @author A-9847
	  * @Desc To verify the Weigh Scale ID populated on Weight Capture page
	  * @param scaleWeight
	  * @throws AWTException
	  * @throws InterruptedException
	  * @throws IOException
	  */
	 public void verifyWeightScaleIDAutopopulated(String weighScaleId) throws AWTException, InterruptedException, IOException
     {
          
try{
		 String actScaleWeighId=getTextAndroid("buildUphht_inbx_weightScaleId;accessibilityId",proppathhht,"Weigh Scale ID",screenName);
	       verifyScreenTextWithExactMatch(screenName, data(weighScaleId), actScaleWeighId, "Weigh Scale ID","Verification of Weigh Scale ID");  
     }
	 catch(Exception e){
		 writeExtent("Fail", "Failed to verify the autopopulation of Weigh Scale ID on"+screenName);
	 } 
     }
	 
	 /**
	  * @author A-9847
	  * @Desc To click on save button on Weight Capture Page
	  * @throws AWTException
	  * @throws InterruptedException
	  * @throws IOException
	  */
	 public void clickWeightCaptureSave() throws AWTException, InterruptedException, IOException
     {
          
          clickActionInHHT("buildUphht_btn_weightCaptureSave;xpath",proppathhht,"Weight Capture Save",screenName); 
          
          
     }
	
	/**
     * Desc : Capturing Contour Details
     * @author A-9478
     * @param contour
     * @throws IOException
     */
     public void captureInvalidContour(String contour) throws IOException
     {                                   
           try
           {           
           clickActionInHHT("buildUphht_btn_selectContour;xpath",proppathhht,"Contour",screenName);            
                 waitForSync(5);
                 String locatorValue=getPropertyValue(proppathhht, "buildUphht_contour;xpath");
             locatorValue=locatorValue.replace("contour",contour); 
             try
             {
                  androiddriver.findElement(By.xpath(locatorValue)).click();
                  waitForSync(3);
                  writeExtent("Fail"," Selected invalid Contour "+contour+" in "+ screenName);
             }
             catch(Exception e)
             {
                 writeExtent("Pass"," Unable to select invalid Contour details "+contour+" in "+ screenName);
             }
           }
           catch(Exception e)
       {
           writeExtent("Fail"," Couldn't select Contour details "+contour+" in "+ screenName);
       }
     }

	
	/**
	 * Desc : Clicking Yes on Booking Confirmation of an AWB to New flight
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickYesOnBookingConfirmationInAnotherFlight(String alertText) throws AWTException, InterruptedException, IOException
    {
		 String actualAlertText="";
			try
			{		
				waitForSync(5);
				String locatorValue=getPropertyValue(proppathhht, "builduphht_alertText;xpath");
		        locatorValue=locatorValue.replace("alertText", data(alertText));  
		        actualAlertText=androiddriver.findElement(By.xpath(locatorValue)).getText();
		        if(actualAlertText.contains(data(alertText))){
		        waitForSync(5);
	            clickActionInHHT("builduphht_btn_bookingConfirmationYes;xpath",proppathhht,"Booking Confirmation",screenName); 
		        writeExtent("Pass"," Pop up text found successfully as "+actualAlertText+" in "+ screenName);
		        }
		        else{
		        	 captureScreenShot("Android");
		        writeExtent("Fail"," Pop up text not found as expected and Actual Text is "+actualAlertText+" in "+ screenName);
		        }
			}

			catch(Exception e)
			{
				 captureScreenShot("Android");
				 writeExtent("Fail"," Pop up text not found as expected and Actual Text is "+actualAlertText+" in "+ screenName);
			}
       
    }
	
	/**
	 * Desc : Click capture linkages and floating pallet option 
	 * @author A-10690
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickCaptureLinkageAndFloatingPallets() throws AWTException, InterruptedException, IOException
	{

		clickActionInHHT("buildUphht_btn_capturelinkage;xpath",proppathhht,"Capture linkage ",screenName); 
		waitForSync(5);




	}
	/**
	 * @author A-10690
	 * @param ULD
	 * @param linkage reason
	 * Desc : capture linkage details
	 * @throws IOException 
	 */
	public void captureLInkageDetails(String uld,String linkagereason) throws IOException
	{

		waitForSync(1);
		String capturelinkagebtn=getPropertyValue(proppathhht, "buildUphht_btn_linkuld;xpath");
		String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectuldlink;xpath");
		locatorValue=locatorValue.replace("*",data(uld));
		for(int i=0;i<2;i++)
		{
			waitForSync(1);
			androiddriver.findElement(By.xpath(capturelinkagebtn)).click();

			waitForSync(1);
			int size=androiddriver.findElements(By.xpath(locatorValue)).size();
			if(size>0)
				break;


		}

		waitForSync(6);
		for(int i=0;i<2;i++)
		{
			waitForSync(1);
			androiddriver.findElement(By.xpath(locatorValue)).click();

			waitForSync(1);
			int size=androiddriver.findElements(By.xpath(locatorValue)).size();
			waitForSync(2);
			if(size==0)

				break;
		}



		enterValueInHHT("buildUphht_txt_linkagereason;xpath",proppathhht,data(linkagereason),"linkage reason",screenName);
		waitForSync(3);
	}

	/**
	 * Desc : Clicking Yes on Booking Confirmation of an AWB to New flight
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	/**
	 * Desc : Clicking Yes on Booking Confirmation of an AWB to New flight
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickYesOnBookingConfirmationInAnotherFlight() throws AWTException, InterruptedException, IOException
    {
			waitForSync(3); 
            clickActionInHHT("builduphht_btn_bookingConfirmationYes;xpath",proppathhht,"Booking Confirmation",screenName); 
            waitForSync(8);        
    }
	


	/**
	 * @author A-9175
	 * @param screenName
	 * @throws IOException
	 * Desc:Verifying error message shipment not found in Warehouse
	 */
	public void verifyShipmentNotFoundErrorMessageDetails(String screenName) throws IOException
    {
          try
          {
          int size=getSizeOfMobileElement("buildUphht_btn_ShipmentNotfounderrorMsgConfirmation;xpath",proppathhht);
                
            /*** CLOSE CONFIRMATION MESSAGE**/
          	clickActionInHHT("buildUphht_btn_ShipmentNotfounderrorMsgConfirmation;xpath",proppathhht,"Close confirmation message",screenName);  
             String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_ShipmentNotfounderrorMsgConfirmation;xpath");
	           String errorText=androiddriver.findElement(By.xpath(locatorValue)).getText();
                waitForSync(2);
                if(size==1){
                writeExtent("Pass", "Data not saved successfully in "+screenName +" alert with text found "+errorText);}
                else{
               	captureScreenShot("Android");
               	writeExtent("Fail", "Data saved successfully in "+screenName);}
          }catch(Exception e){
       	   captureScreenShot("Android");
       	 writeExtent("Fail", "Data saving Not sucessful"+screenName);}
    }
	
	/**
	 * @author A-9175
	 * @param screenName
	 * @throws IOException
	 * Desc:Verifying error message Block exists
	 */
	public void verifyBlockExistErrorMessageDetails(String screenName) throws IOException
    {
          try
          {
          int size=getSizeOfMobileElement("buildUphht_btn_BlockerrorMsgConfirmation;xpath",proppathhht);
                
            /*** CLOSE CONFIRMATION MESSAGE**/
          	clickActionInHHT("buildUphht_btn_BlockerrorMsgConfirmation;xpath",proppathhht,"Close confirmation message",screenName);  
             String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_BlockerrorMsgConfirmation;xpath");
	           String errorText=androiddriver.findElement(By.xpath(locatorValue)).getText();
	           System.out.println(errorText);
                waitForSync(2);
                if(size==1){
                writeExtent("Pass", "Data not saved successfully in "+screenName +" alert with text found "+errorText);}
                else{
               	 captureScreenShot("Android");
               	 writeExtent("Fail", "Data saved successfully in "+screenName);}
          }catch(Exception e){
       	   captureScreenShot("Android");
       	   writeExtent("Fail", "Data saving Not sucessful"+screenName);}
    }
	
	/**
	 * @author A-9175
	 * @param screenName
	 * @throws IOException
	 * Desc:Verifying error message Not ready for Carriage
	 */
	public void verifyNotRFCErrorMessageDetails(String screenName) throws IOException
    {
          try
          {
          int size=getSizeOfMobileElement("buildUphht_btn_RFCerrorMsgConfirmation;xpath",proppathhht);
                
            /*** CLOSE CONFIRMATION MESSAGE**/
          	clickActionInHHT("buildUphht_btn_RFCerrorMsgConfirmation;xpath",proppathhht,"Close confirmation message",screenName);  
             String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_RFCerrorMsgConfirmation;xpath");
	           String errorText=androiddriver.findElement(By.xpath(locatorValue)).getText();
                waitForSync(2);
                if(size==1){
                writeExtent("Pass", "Data not saved successfully in "+screenName +" alert with text found "+errorText);}
                else{
               	 captureScreenShot("Android");
               	 writeExtent("Fail", "Data saved successfully in "+screenName);}
          }catch(Exception e){
       	   captureScreenShot("Android");
       	   writeExtent("Fail", "Data saving Not sucessful"+screenName);}
    }

	/**
	 * Desc : Capture AWB
	 * @author A-9175
	 * @param awbNo
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterAWBDetailsWithoutPcsWgt(String awbNo) throws AWTException, InterruptedException, IOException
	{
		waitTillMobileElementDisplay(proppathhht,"buildUphht_inbx_Awb;accessibilityId","accessibilityId");
		enterValueInHHT("buildUphht_inbx_Awb;accessibilityId",proppathhht,data(awbNo),"Awb No",screenName);	
		waitForSync(10); 
		
	}
	
	public void verificationOfSCCField()
	{
		verifySCCField();
	}

	/**
	 * @author A-9175
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering awb number in hht Screen
	 */
	public void enterValue(String value) throws AWTException, InterruptedException
	{
		try
		{
		   enterValueInHHT("buildUphht_inbx_enterValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
			waitForSync(5);
			
			
			/***clickActionInHHT("buildUphht_btn_next;xpath",proppathhht,"Next",screenName);
			waitForSync(5);
			writeExtent("Pass", "Value "+ data(value)+" entered in "+screenName);
			
			handleNewULDWarning();***/
			
		
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Value "+ data(value)+" not entered in "+screenName);
		}
		 
	}
	/**
     * Desc : Clicking More Options Button
     *@author A-9478
     * @throws AWTException
     * @throws InterruptedException
	 * @throws IOException 
     */
     public void clickMoreOptions() throws AWTException, InterruptedException, IOException
     {
    	 waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_moreOptions;xpath","xpath");
              
          clickActionInHHT("buildUphht_btn_moreOptions;xpath",proppathhht,"Buildup More Options",screenName);  
              waitForSync(6); 
     }
     
     /**
     * Desc : Clicking build up Complete button
     * @author A-9478
     * @throws AWTException
     * @throws InterruptedException
     * @throws IOException 
     */
     public void clickBuildUpComplete() throws AWTException, InterruptedException, IOException
     {
    	 enterULDDetails();
    	 clickMoreOptions();
              clickActionInHHT("buildUphht_btn_buildUpComplete;xpath",proppathhht,"Buildup Complete",screenName); 
              waitForSync(8); 
              int size=getSizeOfMobileElement("buildUphht_div_MoreActions;xpath",proppathhht);

              if(size==1)
              {
            	  clickActionInHHT("buildUphht_btn_buildUpComplete;xpath",proppathhht,"Buildup Complete",screenName); 
            	  waitForSync(8); 
              }

               clickActionInHHT("buildUphht_btn_Yesbtn;xpath",proppathhht,"Yes button",screenName);    
     }
     /**
      * Desc : Clicking More Options Button
      *@author A-9478
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException 
      */
      public void clickMore() throws AWTException, InterruptedException, IOException
      {
          clickActionInHHT("buildUphht_btn_more;xpath",proppathhht,"Buildup More Options",screenName);  
          waitForSync(6); 
      }

     /**
      * Desc : Clicking build up Complete button
      * @author A-9478
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException 
      */
      public void clickBuildUpCompleteBtn() throws AWTException, InterruptedException, IOException
      {
    	     enterULDDetails();
    	     clickMoreOptions();
               clickActionInHHT("buildUphht_btn_buildUpComplete;xpath",proppathhht,"Buildup Complete",screenName); 
               waitForSync(8); 
                  
      }
      /**
  	 * @author A-10690
  	 * Desc : enterULD details
  	 * @throws IOException 
*/
	public void enterULDDetails() throws AWTException, InterruptedException, IOException
	{
		waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_moreOptionsClose;xpath","xpath");
		clickActionInHHT("buildUphht_btn_moreOptionsClose;xpath",proppathhht,"Buildup More Options",screenName); 
		waitForSync(2);
		String actualweight=getTextFromHHT("buildUphht_btn_retrieveWeight;xpath",proppathhht,"weight","Build Up");
		clickMoreOptions();
		clickCaptureULDWeigh();
		waitTillMobileElementDisplay(proppathhht,"buildUphht_txt_uldNo;xpath","xpath",10);
		/***Code added for handling the extra weight updation for KL-PMC data combination***/
		String locator2=getPropertyValue(proppathhht, "buildUphht_txt_uldNo;xpath");
		String ULDNumber=androiddriver.findElement(By.xpath(locator2)).getText();
		String ULDType=ULDNumber.substring(0,3);
		String Carriercode=ULDNumber.substring(8);
		if((ULDType.equals("PMC"))&&(Carriercode.equals("KL")))
			map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight1"));
		else
		map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight"));
		
		String grossWeight=calculateActualWeight(actualweight);
		map.put("grossWeight", grossWeight);
		clickBack("Weight Capture");
		clickMoreOptions();
		clickCaptureULDWeigh();
		map.put("weighscaleid", WebFunctions.getPropertyValue(uldproppath, "buhht_weighscale"));
		map.put("height", WebFunctions.getPropertyValue(uldproppath, "buhht_height"));
		enterULDActualweight("location", "grossWeight","height","weighscaleid");
		waitForSync(5); 

	}
	/** @author A-10690
	 * Desc : Method for calculating actual weight
	 * @throws IOException 
	 */

	public String calculateActualWeight(String wght) throws AWTException, InterruptedException, IOException
	{

		String w1=getTareWeight("tareweight");
		map.put("Extraweight", WebFunctions.getPropertyValue(uldproppath, "buhht_uldaddedweight"));
		int newWeight=(Integer.parseInt(data("Extraweight")));
		int weight= (Integer.parseInt(w1))+ (Integer.parseInt(wght))+newWeight;
		String actualweight=String.valueOf(weight);
		return actualweight;

	}
	/**
	 * @author A-10690
 * @Desc To get the tare weight from weight capture screen
	 * @param tare weight
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String getTareWeight(String tareWeight) throws AWTException, InterruptedException, IOException
	{

		try{
			waitTillMobileElementDisplay(proppathhht,"buildUphht_inbx_weightCaptureLoc;accessibilityId","accessibilityId");
			scrollInMobileDevice("Tare Weight (kg)");
			tareWeight=getTextAndroid("buildUphht_txt_tareWeight;xpath",proppathhht,"Tare weight",screenName);


		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the autopopulation of Weigh Scale ID on"+screenName);
		}
		return tareWeight; 
	}

	/**
	 * @author A-10690
	 * @Desc To enter the Location from location list
	 * @throws IOException
 */
	public void enterWeightCaptureLocationFromList() throws IOException {


		waitTillMobileElementDisplay(proppathhht,"buildUphht_txt_uldNo;xpath","xpath",10);
		String location1=getPropertyValue(proppathhht, "buildUphht_btn_locationArrow;xpath");

		androiddriver.findElement(By.xpath(location1)).click();
		String location2=getPropertyValue(proppathhht, "buildUphht_txt_location;xpath");

		if(androiddriver.findElements(By.xpath(location2)).size()!=1)
		{
			androiddriver.findElement(By.xpath(location1)).click();

			waitForSync(2);
		}
		androiddriver.findElement(By.xpath(location2)).click();
		waitForSync(1);
	}






/**
	 * @author A-10690
	 * @Desc To enter the ULD height on Weight Capture page
	 * @param height
	 * @throws IOException
*/
	public void updateUldHeight(String height) throws IOException {
		
		
		String height1=getPropertyValue(proppathhht, "buildUphht_inbx_ULDheight;xpath");
		if(androiddriver.findElements(By.xpath(height1)).size()==1)
		{

			enterValueInHHT("buildUphht_inbx_ULDheight;xpath",proppathhht,data(height),"ULD height",screenName);
		}

		waitForSync(2);

	}




      /**
       * @author A-9478
       * Desc : verify of build up details are not saved
       * @throws IOException 
        */
       public void verifyBuildUpDetailsNotSaved() throws IOException
       {
             try
             {
             int size=getSizeOfMobileElement("txt_msgConfimation;xpath",proppathhht);
                                     
                   if(size==0)
                   {
                   writeExtent("Pass", "Couldn't save build up details in "+screenName);
                   }
                   else
                   {
                         captureScreenShot("Android");
                         writeExtent("Fail", "Build up details saved in "+screenName);
                   }
             }
             
             catch(Exception e)
             {
                   captureScreenShot("Android");
                   writeExtent("Fail", "Build up details saved in "+screenName);
             }

       }
       /**
   	 * @author A-10690
   	 * @param ULD
   	 * @param linkage reason
   	 * Desc : capture linkage details
   	 * @throws IOException 
   	 */
   	public void captureLInkageDetailsWithFloatingInfo(String uld,String linkagereason,String position) throws IOException
   	{

   		waitForSync(1);
   		String capturelinkagebtn=getPropertyValue(proppathhht, "buildUphht_btn_linkuld;xpath");
   		String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectuldlink;xpath");
   		locatorValue=locatorValue.replace("*",data(uld));
   		for(int i=0;i<2;i++)
   		{
   			waitForSync(1);
   			androiddriver.findElement(By.xpath(capturelinkagebtn)).click();

   			waitForSync(1);
   			int size=androiddriver.findElements(By.xpath(locatorValue)).size();
   			if(size>0)
   				break;


   		}

   		waitForSync(6);
   		for(int i=0;i<2;i++)
   		{
   			waitForSync(1);
   			androiddriver.findElement(By.xpath(locatorValue)).click();

   			waitForSync(1);
   			int size=androiddriver.findElements(By.xpath(locatorValue)).size();
   			waitForSync(2);
   			if(size==0)

   				break;
   		}



   		enterValueInHHT("buildUphht_txt_linkagereason;xpath",proppathhht,data(linkagereason),"linkage reason",screenName);
   		clickActionInHHT("buildUphht_btn_FloatingPallet;xpath",proppathhht,"Check Floating pallet",screenName);	
   		waitForSync(1);
   		enterValueInHHT("buildUphht_txt_NoofPosition;xpath",proppathhht,data(position),"enter no of positions",screenName);
   		waitForSync(1);
   		clickActionInHHT("buildUphht_btn_linkagesave;xpath",proppathhht,"save",screenName);
   		waitForSync(5);

   	}
   	
   	/**@author A-10328
	 * Description- check Floating pallet
	 * @throws InterruptedException
	 * @throws IOException
 */
	
	

public void checkFloatingPallet() throws InterruptedException, IOException
	

{
		
	clickActionInHHT("buildUphht_btn_FloatingPallet;xpath",proppathhht,"Check Floating pallet",screenName);
	waitForSync(2);
			
		
	}


/**@author A-10328
* Description - enter no of positions
* @param position
* @throws IOException
*/

public void enterNoofPosition(String position) throws IOException
{
	enterValueInHHT("buildUphht_txt_NoofPosition;xpath",proppathhht,data(position),"enter no of positions",screenName);
	waitForSync(1);
	}

      /**
       * @author A-7271
       * @throws IOException
       * Desc : click and verify mark top up pop up
       */
      public void clickAndVerifyMarkTopUpPopUp() throws IOException
      {
    	  int size=getSizeOfMobileElement("buildUphht_div_marktopup;xpath",proppathhht);
    	  
    	  if(size==1)
			{
			 writeExtent("Pass", "MarkTopUp Pop up is getting displayed on marking build up complete on "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				 writeExtent("Fail", "MarkTopUp Pop up is not getting displayed on marking build up complete on "+screenName);
			}
    	  clickActionInHHT("buildUphht_btn_Yesbtn;xpath",proppathhht,"Yes button",screenName); 
    	  
      }
	/**
	 * 
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void updateFlightDetails(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
	{
		if(flightDate.equals("currentDay"))
		{
			flightDate="nextDay";
		}
		
		
			waitForSync(5);
			enterValueInHHT("buildUphht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
			waitForSync(2);
			enterValueInHHT("buildUphht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
			waitForSync(2);
			if(flightDate.equals("currentDay"))
			{
				clickActionInHHT("buildUphht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
			}

			else if(flightDate.equals("nextDay"))
			{
				clickActionInHHT("buildUphht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
			}
			waitForSync(2);
			clickActionInHHT("buildUphht_btn_next2;xpath",proppathhht,"Next",screenName);
			waitForSync(10);
			
			/**Flight Details Updation Confirmation Pop Up and Clicking Yes**/
			
			clickActionInHHT("btn_Yes;xpath",proppathhht,"Yes",screenName);
			waitForSync(12);
			verifyHHTSaveDetails(screenName);


	}
	/**
	 * Desc : Buildup Complete with No Option
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBuildUpCompleteWithTopUpNoOption() throws AWTException, InterruptedException, IOException
    {
		enterULDDetails();
		  
		clickMoreOptions();

             clickActionInHHT("buildUphht_btn_buildUpComplete;xpath",proppathhht,"Buildup Complete",screenName); 
             waitForSync(8); 
              clickActionInHHT("buildUphht_btn_Nobtn;xpath",proppathhht,"No button",screenName);    
    }
	
	/**
	 * Desc : Verifying Contour Capture screen
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
    public void verifyContourCaptureScreen() throws AWTException, InterruptedException, IOException
    {
            
    	try
    	{
    		waitForSync(5);
    		clickActionInHHT("buildUphht_btn_selectContour;xpath",proppathhht,"Contour",screenName);
    		waitForSync(3);
    		writeExtent("Pass", "Contour Screen Sucessfully Redirected");
    	}catch (Exception e) {
    		writeExtent("Fail", "Failed to redirect to Contour");
		}
    }
    
    /**
     * Desc Saving Contour Informations
     * @author A-9175
     * @throws AWTException
     * @throws InterruptedException
     * @throws IOException
     */
    public void clickSaveForContour() throws AWTException, InterruptedException, IOException
    {
    	waitForSync(5);
        //Click Save
        int sizeContourEle=getSizeOfMobileElement("buildUphht_btn_ContourSave;xpath",proppathhht); 
        if(sizeContourEle!=0)
        {
           clickActionInHHT("buildUphht_btn_ContourSave;xpath",proppathhht,"Save",screenName);
             waitForSync(3);
        }
     
    }
	
   public void enterFlightNumber(String flightNumber) throws AWTException, InterruptedException
   {
	  

	   Robot r=new Robot();      
	   String a=flightNumber;
	   char c;
	   int d=a.length(),e=0,f=0;

	   while(e<d)
	   {

		   c=a.charAt(e);
		   f=(int) c; //converts character to Unicode. 
		   r.keyPress(KeyEvent.getExtendedKeyCodeForChar(f));
		   e++;

		   Thread.sleep(150);
	    }
		
   }
	/**
	 * @author A-7271
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : updateFlightDetailsWithOutPopUp
	 */
	public void updateFlightDetailsWithOutPopUp(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
	{


		if(flightDate.equals("currentDay"))
		{
			flightDate="nextDay";
		}
		else if(flightDate.equals("selectCurrentDay"))
		{
			flightDate="currentDay";
		}
		waitForSync(5);
		clickActionInHHT("buildUphht_inbx_carrierCode;accessibilityId",proppathhht,"Current Date",screenName);
		enterValueInHHT("buildUphht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
		waitForSync(2);
		clickActionInHHT("buildUphht_inbx_flightNumber;accessibilityId",proppathhht,"Current Date",screenName);
		waitForSync(2);
		/****enterValueInHHT("buildUphht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);***/
		enterFlightNumber(data(flightNo));
		waitForSync(2);
		/*********************************************************************/
		// ADDED THE CODE FOR HANDLING THE INVALID FLIGHT POP UP
		/***waitForSync(2);
String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
		locatorValue=locatorValue.replace("*", "Invalid Flight"); 
		waitForSync(5);
		if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
		{
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(8);
		}****/
		/*********************************************************************/
		/***waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_next2;xpath","xpath");***/
		if(flightDate.equals("currentDay"))
		{
			clickActionInHHT("buildUphht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
		}

		else if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("buildUphht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);

		}    

		/*******Select POU***/
		String locatorValue = getPropertyValue(proppathhht, "buildUphht_btn_pou;xpath");
		waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_pou;xpath","xpath",20);
		locatorValue = locatorValue.replace("pou", data("Destination"));
		androiddriver.findElement(By.xpath(locatorValue)).click();
		waitForSync(2);
		waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_next2;xpath","xpath");

		//click Next Button
		for (int i = 0; i < 2; i++) 
		{
			String locatorNext = getPropertyValue(proppathhht, "buildUphht_btn_next2;xpath");
			androiddriver.findElement(By.xpath(locatorNext)).click();
			waitForSync(10);
			String locatorValue1 = getPropertyValue(proppathhht, "btn_errorMsg;xpath");
			locatorValue1 = locatorValue1.replace("*",
					"Do you want to assign this Carrier/Shipper Built ULD to the flight specified above?");
			waitForSync(5);
			if (androiddriver.findElements(By.xpath(locatorValue1)).size() == 1) {
				androiddriver.findElement(By.xpath(locatorValue1)).click();
				writeExtent("Pass",
						"Clicked yes on Do you want to assign this Carrier/Shipper Built ULD to the flight specified above ");
				waitForSync(8);
				int size = getSizeOfMobileElement("btn_Continue;xpath", proppathhht);
				if (size == 1)
					acceptAlertMessageAndContinue(
							"val~The shipment is not booked to the flight. Do you want to proceed?");
			}
			int size=getSizeOfMobileElement("buildUphht_inbx_Awb;accessibilityId",proppathhht); 
			if((size>0))
			{

				break;
			}

		}

		/**Flight Details Updation Confirmation Pop Up and Clicking Yes**/
		String locatorYes=getPropertyValue(proppathhht, "btn_Yes;xpath");
		if(androiddriver.findElements(By.xpath(locatorYes)).size()==1)
		{
			androiddriver.findElement(By.xpath(locatorYes)).click();
			waitForSync(2);
		}
		waitForSync(10);
}







	

	
	/**
     * @author A-9478
     * @throws AWTException
     * @throws InterruptedException
     * Description : Entering awb/uld number, select Barrow as Yes and click Next in hht Screen
     */
     public void enterValueWithBarrow(String value) throws AWTException, InterruptedException
     {
           try
           {
        	   clickActionInHHT("buildUphht_inbx_enterValue;accessibilityId",proppathhht,"Barrow",screenName);
   			enterTCONvalue(data(value));
   			waitForSync(5);
   			clickActionInHHT("buildUphht_btn_BarrowYes;xpath",proppathhht,"Barrow",screenName);
   			waitForSync(2);
   			clickActionInHHT("buildUphht_btn_next;xpath",proppathhht,"Next",screenName);
   			waitForSync(5);
   			writeExtent("Pass", "Value "+ data(value)+" entered in "+screenName);
   			handleNewULDWarning();
                 
           }
           
           catch(Exception e)
           {
                 writeExtent("Fail", "Value "+ data(value)+" not entered in "+screenName);
           }
           
     }

/**
     * Desc : Click Update ULD Height/Contour and select Value 
     * @author A-9478
     * @throws AWTException
     * @throws InterruptedException
 * @throws IOException 
     */
     public void clickUpdateULDHeightContour() throws AWTException, InterruptedException, IOException
     {
          waitForSync(5);
          clickActionInHHT("buildUphht_btn_UpdateULDHeightContour;xpath",proppathhht,"Update ULD Height/Contour ",screenName); 
          waitForSync(5);
          int size=getSizeOfMobileElement("buildUphht_div_MoreActions;xpath",proppathhht);

          if(size==1)
          {
          clickActionInHHT("buildUphht_btn_UpdateULDHeightContour;xpath",proppathhht,"Update ULD Height/Contour ",screenName); 
          waitForSync(5);
          }

      


          
     }

/**
      * Desc : Select Contour
      * @author A-9478
      * @throws AWTException
      * @throws InterruptedException
 * @throws IOException 
      */
      public void selectContour(String contour) throws AWTException, InterruptedException, IOException
      {
    	  waitForSync(5);
    	  clickActionInHHT("buildUphht_btn_clickContour;xpath",proppathhht,"Update ULD Height/Contour ",screenName); 
    	  waitForSync(2);
    	  //Click on Contour value as per the argument
    	  String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_ContourValue;xpath");
    	  locatorValue=locatorValue.replace("ContourValue", data(contour));
    	  androiddriver.findElement(By.xpath(locatorValue)).click();
    	  waitForSync(2);
    	  //Click Save
    	  clickActionInHHT("buildUphht_btn_ContourSave;xpath",proppathhht,"Save",screenName);
    	  waitForSync(3);
    	  //Verify text "ULD contour saved" in pop up
    	  verifyULDContourSavedPopUp();
    	  verifyHHTContourSaveDetails(screenName);
           
      }
      /**
       * Desc : Select Contour
       * @author A-9478
       * @throws AWTException
       * @throws InterruptedException
  * @throws IOException 
       */
       public void selectContourAndSave(String contour) throws AWTException, InterruptedException, IOException
       {
    	   
    	   waitTillMobileElementDisplay(proppathhht,"buildUphht_btn_clickContour;xpath","xpath");
    	   int sizeContourEle=getSizeOfMobileElement("buildUphht_btn_clickContour;xpath",proppathhht); 

    	   if(sizeContourEle!=0)
    	   {

    		   waitForSync(5);
    		   
    		   clickActionInHHT("buildUphht_btn_clickContour;xpath",proppathhht,"Update ULD Height/Contour ",screenName); 
    		   waitForSync(2);
    		   //Click on Contour value as per the argument
    		   String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_ContourValue;xpath");
    		   locatorValue=locatorValue.replace("ContourValue", data(contour));
    		   for(int i=1;i<=2;i++)
    		   {
    			   androiddriver.findElement(By.xpath(locatorValue)).click();
    			   waitForSync(2);
    			   int size=getSizeOfMobileElement("buildUphht_btn_selectConsumablesOK;xpath",proppathhht); 
    			   if(size==0)

    			   {
    				   writeExtent("Pass","Sucessfully navigated to next page");
    				   break;
    			   }
    		   }

    		   //Click Save
    		   clickActionInHHT("buildUphht_btn_ContourSave;xpath",proppathhht,"Save",screenName);
    		   waitForSync(6);
    	   }
    	   
    	   
    	   /*****String locator=getPropertyValue(proppathhht, "buildUphht_txt_Barrow;xpath");
    	   while(androiddriver.findElements(By.xpath(locator)).size()!=1)
    	   {
    		   clickBack("Build Up");
    	   }******/



       }
       
      /**
       * Desc : captureConsumables
       * @author A-7271
       * @throws AWTException
       * @throws InterruptedException
  * @throws IOException 
       */
       public void captureConsumablesWithOutSave(String consumable) throws AWTException, InterruptedException, IOException
       {
     	 
     	  clickActionInHHT("buildUphht_btn_clickConsumables;xpath",proppathhht,"Select Meterial",screenName); 
     	  waitForSync(2);
     	  //Click on Contour value as per the argument
     	  String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectConsumables;xpath");
     	  locatorValue=locatorValue.replace("Consumables", data(consumable));
     	  androiddriver.findElement(By.xpath(locatorValue)).click();
     	  waitForSync(3);
     	  //Ok button
     	  clickActionInHHT("buildUphht_btn_selectConsumablesOK;xpath",proppathhht,"OK",screenName);
     	  waitForSync(3);
     	 
            
       }
       /**
        * Desc : captureConsumables
        * @author A-7271
        * @throws AWTException
        * @throws InterruptedException
   * @throws IOException 
        */
        public void captureConsumables(String consumable) throws AWTException, InterruptedException, IOException
        {
      	 
      	  clickActionInHHT("buildUphht_btn_clickConsumables;xpath",proppathhht,"Select Meterial",screenName); 
      	  waitForSync(2);
      	  //Click on Contour value as per the argument
      	  String locatorValue=getPropertyValue(proppathhht, "buildUphht_btn_selectConsumables;xpath");
      	  locatorValue=locatorValue.replace("Consumables", data(consumable));
      	  androiddriver.findElement(By.xpath(locatorValue)).click();
      	  waitForSync(3);
      	  //Ok button
      	  clickActionInHHT("buildUphht_btn_selectConsumablesOK;xpath",proppathhht,"OK",screenName);
      	  waitForSync(3);
      	  //Click Save
      	
      	 clickActionInHHT("buildUphht_btn_Save;xpath",proppathhht,"Save",screenName);	
      	  waitForSync(3);
      	 verifyHHTSaveDetails(screenName);
      	  
             
        }

/**
       * @author A-9478
       * Desc : Verifying ULD Contour Saved pop up
 * @throws IOException 
       */
       public void verifyULDContourSavedPopUp() throws IOException
       {
           

    	   try
    	   {
    		   int size=getSizeOfMobileElement("buildUphht_btn_ULDContourSavedPopUp;xpath",proppathhht);
    		 
    		   if(size==1)
    		   {
    			   writeExtent("Pass", "Confirmation message is getting displayed on saving contour details on "+screenName);
    		   }
    		   else
    		   {
    			   captureScreenShot("Android");
    			   writeExtent("Fail", "Confirmation message is not getting displayed on saving contour details on "+screenName);
    		   }
    	   }

    	   catch(Exception e)
    	   {
    		   captureScreenShot("Android");
			   writeExtent("Fail", "Confirmation message is not getting displayed on saving contour details on "+screenName);
    	   }
       }
/**
     * @author A-7271
     * @param screenName
     * Desc : Verify save details in hht screen
 * @throws IOException 
     */
     public void verifyHHTContourSaveDetails(String screenName) throws IOException
     {
           try
           {
           int size=getSizeOfMobileElement("buildUphht_btn_ULDContourSavedPopUp;xpath",proppathhht);
                 
                 /*** CLOSE CONFIRMATION MESSAGE**/
         clickActionInHHT("buildUphht_btn_msgConfirmationCloseULDContourSaved;xpath",proppathhht,"Close confirmation message",screenName);  
                 
                 waitForSync(2);
                 
                 if(size==1)
                 {
                 writeExtent("Pass", "Contour Details saved successfully in "+screenName);
                 }
                 else
                 {
                	 captureScreenShot("Android");
                       writeExtent("Fail", "Contour Details not saved successfully in "+screenName);
                 }
           }
           
           catch(Exception e)
           {
        	   captureScreenShot("Android");
                 writeExtent("Fail", "Contour Details not saved successfully in "+screenName);
           }
     }
     /**
      * Desc : Click trigger storage
      * @author A-9478
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException 
      */
      public void clickTriggerStorage() throws AWTException, InterruptedException, IOException
      {
           waitForSync(5);
           clickActionInHHT("buildUphht_btn_TriggerStorage;xpath",proppathhht," Trigger Storage ",screenName); 
           waitForSync(5);
           
      }

	/**
	 * 
	 * @param awbNo
	 * @param pcs
	 * @param wgt
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : enter the build up details
	 * @throws IOException 
	 */
	public void enterBuildUpdetails(String awbNo,String pcs,String wgt) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("buildUphht_inbx_Awb;accessibilityId",proppathhht,data(awbNo),"Awb No",screenName);
			waitForSync(5);
			enterValueInHHT("buildUphht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
			waitForSync(2);
			enterValueInHHT("buildUphht_inbx_Wt;accessibilityId",proppathhht,data(wgt),"Weight",screenName);
			waitForSync(5);
			clickActionInHHT("buildUphht_btn_Save;xpath",proppathhht,"Save",screenName);	
			waitForSync(8);
			verifyHHTSaveDetails(screenName);
		
		 
	}
	/**
	 * @author A-7271
	 * @param awbNo
	 * @param pcs
	 * @param wgt
	 * @throws AWTException
	 * @throws InterruptedException
	 * Desc : enter build up details for the shipment
	 * @throws IOException 
	 */
	public void enterShipmentDetails(String awbNo,String pcs,String wgt) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("buildUphht_inbx_Awb;accessibilityId",proppathhht,data(awbNo),"Awb No",screenName);
			waitForSync(6);
			enterValueInHHT("buildUphht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
			waitForSync(2);
			enterValueInHHT("buildUphht_inbx_Wt;accessibilityId",proppathhht,data(wgt),"Weight",screenName);
			waitForSync(5);
			clickActionInHHT("buildUphht_btn_Save;xpath",proppathhht,"Save",screenName);	
			waitForSync(8);
			
		
		 
	}
	/**
	 * @author A-7271
	 * Desc : verify of build up details are saved
	 * @throws IOException 
	 */
	public void verifyBuildUpDetailsIfSaved() throws IOException
	{
		verifyHHTSaveDetails(screenName);
	}
	
	/**
	 * @author A-7271
	 * @param pcs
	 * Desc : enterUNIdetails
	 * @throws IOException 
	 */
	public void enterUNIDDetails(String pcs) throws IOException
	{
		
		waitForSync(1);
		enterValueInHHT("buildUphht_inbx_SPLPcs;accessibilityId",proppathhht,data(pcs),"Weight",screenName);
		clickActionInHHT("buildUphht_btn_NextSpl;xpath",proppathhht,"Next",screenName);
		waitForSync(8);
		verifyHHTSaveDetails(screenName);
	}
	
	
}
