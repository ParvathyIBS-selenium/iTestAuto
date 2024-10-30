package screens;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import rest_sfmi.JSONBody;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.core.JsonProcessingException;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class GoodsAcceptanceHHT extends CustomFunctions {
	
	String sheetName = "GoodsAcceptanceHHT";
	String screenName = "GoodsAcceptanceHHT";
	public static String checksheetpath = "\\src\\resources\\Checksheet.properties";  
	public static String toproppath="\\src\\resources\\TO.properties";

	public GoodsAcceptanceHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	JSONBody jsonbody=new JSONBody(driver, excelreadwrite, xls_Read);
	/**
	 * @author A-9844
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht and accept the pop up
	 * @throws IOException 
	 */
	public void enterAWBNumber(String value) throws AWTException, InterruptedException, IOException
	{
			enterValueInHHT("gahht_inbx_enterValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
			waitForSync(7);
			map.put("VPPAwb", data(value));
			
			/**AWB does not exist pop up and Clicking Yes**/
			String locatorYes=getPropertyValue(proppathhht, "btn_Yes;xpath");
			if(androiddriver.findElements(By.xpath(locatorYes)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorYes)).click();
				waitForSync(5);
			}
	}
	
	/**
	 * @author A-7271
	 * @param pieces
	 * @param weight
	 * @param location
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered loose acceptance details
	 */
	public void LooseAcceptanceDetailsWithoutStoragePosition(String pieces,String weight,String location) throws AWTException, InterruptedException
	{
		try
		{
			System.out.println(data(weight));
			enterValueInHHT("gahht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);
			waitForSync(3);
			clickActionInHHT("gahht_inbx_Pcs;accessibilityId",proppathhht,"pieces",screenName);
			enterValueInHHT("gahht_inbx_Pcs;xpath",proppathhht,data(pieces),"Pieces",screenName);
			String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
			locatorValue=locatorValue.replace("*", "Invalid AWB scanned");
			waitForSync(3);
			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorValue)).click();
				waitForSync(3);
			}
			waitForSync(3);
			//	enterPieces(data(pieces));
			scrollInMobileDevice("Select ZON");
			enterValueInHHT("gahht_inbx_Wt;accessibilityId",proppathhht,data(weight),"Weight",screenName);
			writeExtent("Pass", "Loose acceptance details entered as pieces : "+data(pieces)+" weight : "+data(weight)+" location : "+
					data(location));
			map.put("VPPWeight", data(weight));
			map.put("VPPVolume", data("Volume"));
			map.put("VPPType", "loose");

		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter the loose acceptance details in "+screenName);
		}
	}
	/**@author A-10328
	 * Description - Enter Storage Position
	 * @param storagePOS
	 * @throws IOException
	 */
	public void CaptureStoragePosition(String storagePOS) throws IOException {
		// Scroll down
		scrollInMobileDevice("Storage Position");
		clickActionInHHT("gahht_btn_storagePosDetails;xpath", proppathhht, "Select Storage Position", screenName);
		waitTillMobileElementDisplay(proppathhht, "gahht_btn_storagePosDetails;xpath", "xpath", 20);
		String locatorStoragePOSValue=getPropertyValue(proppathhht, "gahht_btn_SelectStoragePOSValue;xpath");
		locatorStoragePOSValue=locatorStoragePOSValue.replace("POS", data(storagePOS));
		scrollMobileDevice(data(storagePOS));
		androiddriver.findElement(By.xpath(locatorStoragePOSValue)).click();
		writeExtent("Pass", "Selected Storage Position as "+data("storagePOS")+" in Goods Acceptance hht screen");
	}

	/**
	 * @author A-9844
	 * @param location
	 * @param awb
	 * @param pcs
	 * @param wt
	 * Description : Enter ULD acceptance details
	 */
	public void enterUldAcceptanceDetailWithPieces(String awb, String pcs)
	{
		try
		{

			clickActionInHHT("gahht_btn_addNewAwb;xpath",proppathhht,"Add New AWB",screenName);
			waitForSync(2);
			//Enter AWB
			enterValueInHHT("gahht_inbx_awbNumber;accessibilityId",proppathhht,data(awb),"Awb No",screenName);
			waitForSync(3);
			String locatorPcs=getPropertyValue(proppathhht, "gahht_inbx_piecesdisplayed;xpath");
			
			if((androiddriver.findElement(By.xpath(locatorPcs)).getText())!=data(pcs)){
			
			for(int i=0;i<Integer.parseInt(data(pcs))-1;i++){
				
				clickActionInHHT("gahht_btn_piecesAdd;xpath",proppathhht,"Add pieces button",screenName);
				waitForSync(1);
			}
			}
			
			
			clickActionInHHT("gahht_btn_Add;xpath",proppathhht,"Add New AWB",screenName);
		
			
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter ULD acceptance details on "+screenName);
		}

		
		
	}

	/**
	 * @Desc : reEnterScaleWeight
	 * @author A-9175
	 * @param scalewgt
	 * @throws IOException
	 */
	public void reEnterScaleWeight(String scalewgt) throws IOException {
		enterValueInHHT("gahhthht_inbx_reenterweighscale;xpath", proppathhht, data(scalewgt),
				"Re entered Scale Weight Value", screenName);
		waitForSync(3);

	}

	/**
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: select handling area
	 */
	public void selectHandlingAreaAndClickDone() throws InterruptedException, AWTException {

		try
		{
			
			waitForSync(4);
			clickActionInHHT("gahht_txt_homeIcon;xpath",proppathhht,"Home icon",screenName);
			waitForSync(2);
			clickActionInHHT("gahht_txt_handlingAreaOption;xpath",proppathhht,"HandlingArea Option",screenName);
			waitForSync(2);
			clickActionInHHT("gahht_txt_handlingAreaDropdownText;xpath",proppathhht,"HandlingArea Dropdown Text",screenName);
			waitForSync(2);

			//Deselecting the Generally selected HA during login
			String locatorHA=getPropertyValue(proppathhht, "gahht_btn_handlingAreaOptionsList;xpath");
			locatorHA=locatorHA.replace("*", data("HA_Buildup")); 
			scrollMobileDevice(data("HA_Buildup"));
			androiddriver.findElement(By.xpath(locatorHA)).click();

			//Selecting the needed HA
			String locatorHA1=getPropertyValue(proppathhht, "gahht_btn_handlingAreaOptionsList;xpath");
			locatorHA1=locatorHA1.replace("*", data("HandlingArea")); 
			scrollMobileDevice(data("HandlingArea"));
			androiddriver.findElement(By.xpath(locatorHA1)).click();

			waitForSync(2);
			clickActionInHHT("gahht_btn_handlingAreaOk;xpath",proppathhht,"OK",screenName);
			waitForSync(2);
			clickActionInHHT("gahht_txt_Done;xpath",proppathhht,"Done",screenName);
			waitForSync(2);
			

		}


			

		
		catch(Exception e)
		{
			writeExtent("Fail", "Could not select the Handling Area on "+screenName);
		}
	}




/**
	 * @author A-9844
	 * @throws fetch the default location value displayed
	 */
	public void fetchDefaultLocationDisplayed() throws IOException
	{
		String actDefaultLocation=getTextAndroid("gahht_txt_defaultLocation;xpath",proppathhht,"Default acceptance location",screenName);
		waitForSync(2);
		map.put("DefaultLocation",actDefaultLocation);
		

	}

	/**
	 * @author A-9844
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : capture awb details
	 * @throws IOException 
	 */
	public void captureAWBDetails(String origin,String destination,String pieces,String weight,String scc) throws AWTException, InterruptedException, IOException
	{
		enterValueInHHT("gahht_txt_origin;xpath",proppathhht,data(origin),"Origin",screenName);
		enterValueInHHT("gahht_txt_destination;xpath",proppathhht,data(destination),"Destination",screenName);
		enterValueInHHT("gahht_txt_pieces;xpath",proppathhht,data(pieces),"Pieces",screenName);
		enterValueInHHT("gahht_txt_weight;xpath",proppathhht,data(weight),"Weight",screenName);
		enterValueInHHT("gahht_txt_scc;xpath",proppathhht,data(scc),"SCC",screenName);
		clickActionInHHT("gahht_txt_captureAWBSave;xpath",proppathhht,"Save",screenName);
		waitForSync(8);
		
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht acceptance screen
	 */
	public void invokeAcceptanceScreen() throws InterruptedException, AWTException {

			try
		{
				
				waitTillMobileElementDisplay(proppathhht,"gahht_menu;xpath","xpath");
				clickActionInHHT("gahht_menu;xpath",proppathhht,"Acceptance menu",screenName);
				waitForSync(2);
				writeExtent("Pass", "Acceptance hht screen is invoked successfully");

		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Acceptance hht screen is not invoked successfully");
		}
	}
	/**
	 * Desc : Verifying transhipment status is autochecked or not
	 * @author A-10690
	 * @param expStatus
	 * @throws InterruptedException
	 */
	public void verifytranshipmentStatus(String expStatus) throws InterruptedException
	{
		
		
		scrollInMobileDevice("All Parts Received");
		//check transhipment toggle bar enabled
		String locatorValue=getPropertyValue(proppathhht, "gahht_lbl_transhipmentStatus;xpath");
		String status=androiddriver.findElement(By.xpath(locatorValue)).getText();
		try
		{
			
			if(status.equals(expStatus))
				writeExtent("Pass", "successfully Verified status as "+expStatus+"in "+screenName);	
			waitForSync(1);
		}
		catch (Exception e)
		{
			writeExtent("Fail", "Could not  Verified status as "+expStatus+"in "+screenName);	
		}
		
		
	}
	
/**
	 * Desc : Capture CheckSheet
	 * @author A-10690
	 * @param answer
	 * @throws IOException
	 */


	public void captureCheckSheet(String answer) throws IOException
	{
		clickActionInHHT("goodsacceptencephht_txt_checksheet;xpath",proppathhht,"Capture Checksheet",screenName);
		waitForSync(3);
		List<MobileElement>questions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_question;xpath")));
		List<MobileElement>answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_txt_chksheetyes;xpath")));
		System.out.println(questions.size());
		for(MobileElement answer1:answers)
		{
			answer1.click();
		}
		for(MobileElement quest:questions)
		{
			
	
		String text=quest.getText();
		System.out.println(text);
		if(text.contains(answer))		
		clickActionInHHT("txt_no;xpath",proppathhht,"No",screenName);
						
		}	
		clickActionInHHT("buildUphht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);
	}	

	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : capture check sheet
	 */
	public void CaptureChecksheet() throws InterruptedException, IOException
	{

		{
            try
         {
              int size=getSizeOfMobileElement("gahht_btn_checksheetSave;xpath",proppathhht);                             
               if(size==1)
               {
            	   waitForSync(2);
            	   /***********************************************/
            	   
            	   captureCheckSheet("leakage");
            	   
            	   /*************************************************/
            	   waitForSync(3);
            	   clickActionInHHT("gahht_btn_checksheetSave;xpath",proppathhht," Save Capture Checksheet ",screenName);
            	   waitForSync(7);
            	   verifyHHTSaveDetails(screenName);
                      
                   writeExtent("Pass", "Saved Checksheet Details" +screenName);
                   waitForSync(2);
               }
               else
               {
                   writeExtent("Info", "Not Found Checksheet Details for save" +screenName);
                   verifyHHTSaveDetails(screenName);
               }
         }
         catch(Exception e)
            {
               writeExtent("Fail", "Could not Found Checksheet Details for save" +screenName);
            }


		}


	}
	
	/**
	 * @author A-10690
	 * @throws InterruptedException
	 * Description : click on save button
	 * @throws IOException 
	 */
	public void save() throws InterruptedException, IOException
	{
		
		
		
			clickActionInHHT("gahht_btn_Save;xpath",proppathhht,"Save",screenName);	
			waitForSync(10);

			 /***** WEIGHT RECEPTION FROM VPP ****/
	        getVPPFeed();
    			/**********************************************/

	}

	 /**
     * @author A-9844
     * @param zone
     * Description : Select ZON
     * @throws IOException 
      */
     public void selectZON(String zone) throws IOException
     {
    	//Scroll down     
         scrollInMobileDevice("Select ZON");
         clickActionInHHT("gahht_btn_clickZON;xpath",proppathhht,"Select ZON",screenName);
         waitForSync(5);
        try
         {
                String locatorValue=getPropertyValue(proppathhht, "gahht_btn_selectZONValue;xpath");
              locatorValue=locatorValue.replace("ZON", data(zone));   
              androiddriver.findElement(By.xpath(locatorValue)).click();
              waitForSync(5);
              writeExtent("Pass", "Successfully selected ZON value "+data(zone)+" in "+screenName);
              
         }
        
        
         catch(Exception e)
         {
               writeExtent("Pass", "Couldn't select ZON value "+data(zone)+" in "+screenName);
         }
   }

/**
	 * @Description : verify Instruction Icon is displayed
	 * @author A-9844
	 * @param awbNo
	 * @throws IOException
	 */
	public void verifyInstructionIcon(String awbNo) throws IOException
	{  
		
		
		try
		{      
			waitForSync(2);
			String locatorValue=getPropertyValue(proppathhht, "gahht_txt_instructionIcon;xpath");
			locatorValue=locatorValue.replace("awb", data(awbNo));
			
			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
				writeExtent("Pass", "Successfully verified the instruction icon is present in "+screenName);	
			} 
			else{
				writeExtent("Failed", "Failed to  verify the instruction icon is present in "+screenName);
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't fetch the intruction icon on "+screenName);
		}

	}
	/**
	 * @author A-8783 Desc - Verify origin and destination
	 * @param Origin
	 * @param Destination
	 */
	public void verifyOriginAndDestination(String Origin, String Destination) {
		try {
			String locatorOrg = getPropertyValue(proppathhht, "gahht_txt_route;xpath");
			locatorOrg = locatorOrg.replace("route", data(Origin));
			waitForSync(1);
			int sizeOrg = androiddriver.findElements(By.xpath(locatorOrg)).size();

			String locatorDest = getPropertyValue(proppathhht, "gahht_txt_route;xpath");
			locatorDest = locatorDest.replace("route", data(Destination));
			waitForSync(1);
			int sizeDest = androiddriver.findElements(By.xpath(locatorDest)).size();

			if (sizeOrg == 1) {
				writeExtent("Pass", "Verified origin " + data(Origin) + " in " + screenName);
			} else
				writeExtent("Fail", "Failed to verify origin for " + data(Origin) + " in " + screenName);

			if (sizeDest == 1) {
				writeExtent("Pass", "Verified Destination " + data(Destination) + " in " + screenName);
			} else
				writeExtent("Fail", "Failed to verify destination for " + data(Destination) + " in " + screenName);

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the origin and destination on " + screenName);

		}
	}
	/**
     * @author A-10690
     * @param pcs
     * @param wt
     * @param dimensions
     * Description : Verify dimension pieces a,volume,dimensions in acceptance hht screen
     * @throws IOException 
      */
     public void verifyDimensionpiecesandvolume(String pcs,String vol, String len,String wid,String height) throws IOException
     {
           
           
    	 //Scroll down     
    	 scrollInMobileDevice("Dimension Capture");
    	 clickActionInHHT("gahht_btn_dimensionCapture;xpath",proppathhht,"Dimension capture",screenName);
    	 waitForSync(5);

    	 //Verify the dimension details
    	 String actDimPieces=getTextAndroid("gahht_txt_dimensionpieces;xpath",proppathhht,"pieces",screenName);
    	 String actDimvolume=getTextAndroid("gahht_txt_dimensionvolume;xpath",proppathhht,"volume",screenName);
    	 scrollTillEnd();
    	 String actDimlength=getTextAndroid("gahht_txt_dimensionlength;xpath",proppathhht,"length",screenName);
    	 String actDimWidth=getTextAndroid("gahht_txt_dimensionwidth;xpath",proppathhht,"width",screenName);
    	 String actDimHeight=getTextAndroid("gahht_txt_dimensionheight;xpath",proppathhht,"height",screenName);




       
           
           verifyScreenTextWithExactMatch(screenName, data(vol),  actDimvolume, "Dimension-Volume",
       			"Verification of dimension-volume");
           
           
           verifyScreenTextWithExactMatch(screenName, data(len),  actDimlength, "Dimension-Length",
          			"Verification of dimension-length");
              
           verifyScreenTextWithExactMatch(screenName, data(wid),  actDimWidth, "Dimension-Width",
         			"Verification of dimension-width");
         
           verifyScreenTextWithExactMatch(screenName, data(height),  actDimHeight, "Dimension-Height",
        			"Verification of dimension-height");
           
           verifyScreenTextWithExactMatch(screenName, data(pcs),  actDimPieces, "Dimension-Pieces",
       			"Verification of dimension-pieces");
          
          
           
           
           clickActionInHHT("gahht_btn_dimOk;xpath",proppathhht,"Dimension capture OK",screenName);
}
     /**
 	 * To select multiple SCCs
 	 * @param sccs
 	 * @throws IOException
 	 */
 	public void selectMultipleSCC(String sccs[]) throws IOException{


 		waitTillMobileElementDisplay(proppathhht,"gahht_btn_clickSCC;xpath","xpath",10);
 		clickActionInHHT("gahht_btn_clickSCC;xpath",proppathhht,"SCC icon",screenName);
 		waitForSync(2);

 		try{
 			for(int i=0;i<sccs.length;i++){
 				String screenXpath = getPropertyValue(proppathhht,"gahht_selectMultiplesccs;xpath").replace("*",sccs[i]);
 				androiddriver.findElement(By.xpath(screenXpath)).click();
 				waitForSync(2);
 				writeExtent("Pass", "Successfully selected "+ sccs[i]+ " on "+screenName);
 			}
 		}catch(Exception e){
 			writeExtent("Fail", "Failed to select SCC on"+screenName);
 		}


 		int size = getSizeOfMobileElement("gahht_btn_Ok;xpath", proppathhht); 
 		if (size == 1) 

 		{ 

 			clickActionInHHT("gahht_btn_Ok;xpath", proppathhht, " SCC Ok ", screenName); 
 			waitForSync(3); 

 		} 

 	}

	/**
	 * @author A-9844
	 * Desc- verify error message 
	 * @throws IOException 
	 */
	public void verifyErrorMessage(String errorMessage) throws IOException {

		//click acceptane save button
				clickActionInHHT("gahht_btn_Save;xpath",proppathhht,"Save",screenName);
				waitForSync(3);
				String locatorcloseBtn=getPropertyValue(proppathhht, "gahht_btn_closeButtonxpath");
				locatorcloseBtn=locatorcloseBtn.replace("*", data(errorMessage)); 
				String locatorValue=getPropertyValue(proppathhht, "gahht_txt_errorMessge;xpath");

				locatorValue=locatorValue.replace("*", data(errorMessage));
				waitForSync(1);
				int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

				if(eleSize==1)
				{
					writeExtent("Pass","Verified the error message: "+data(errorMessage)+" in "+screenName);
					androiddriver.findElement(By.xpath(locatorcloseBtn)).click();
					waitForSync(8);
				}
				
				else
				{
					writeExtent("Fail","Could not verify the error message: "+data(errorMessage)+" in "+screenName);
				}

	}

	/**
	 * @author A-9844
	 * @throws InterruptedException
	 * Description : verify multiple SCCs displayed
	 * @throws IOException 
	 */
	public void verifyMultipleSCCs(String awbNo,String SCC) throws InterruptedException, IOException
	{
		try
		{
		String locatorValue=getPropertyValue(proppathhht, "gahht_txt_AWBNumber;xpath");
		locatorValue=locatorValue.replace("*", data(awbNo));
		androiddriver.findElement(By.xpath(locatorValue)).click();
		waitForSync(3);


		String sccLocator=getPropertyValue(proppathhht, "gahht_txt_sccValues;xpath");
		boolean sccExists=true;
		String[]arrSCCExp=new String[20];
		List <MobileElement> listSCC=androiddriver.findElements(By.xpath(sccLocator));
		List<String>actListScc=new ArrayList<String>();


		//Storing the SCC retreived in arraylist

		for(int i=0;i<listSCC.size();i++)
		{
			actListScc.add(listSCC.get(i).getText());
		}


		//Storing expected values in array


		for(int i=0;i<SCC.split(",").length;i++)
		{
			arrSCCExp[i]=SCC.split(",")[i].trim();
		}



		//Verifying if expected SCC contains in the actual SCC list

		for(int i=0;i<SCC.split(",").length;i++)
		{
			if(!actListScc.contains(arrSCCExp[i]))
			{
				writeExtent("Fail","SCC "+arrSCCExp[i]+" is missing in the SCC field on "+screenName);
				sccExists=false;
				break;
			}
		}
		if(sccExists)
			writeExtent("Pass","SCC field matches on "+screenName);
		else
			writeExtent("Fail","SCC field does not match on "+screenName);


		clickActionInHHT("gahht_btn_closePopup;xpath",proppathhht,"Popup close",screenName);
		waitForSync(3);
		
		}

		catch(Exception e)
		{
			writeExtent("Fail","SCC field does not match on "+screenName);
		}
	}




	/**
	 * @Description : verify Instruction is displayed
	 * @author A-9844
	 * @param awbNo
	 * @throws IOException
	 */
	public void verifyInstructionDisplayed(String awbNo,String expInstruction) throws IOException
	{  
		
		
		try
		{      
			waitForSync(2);
			String locatorValue=getPropertyValue(proppathhht, "gahht_txt_instructionIcon;xpath");
			locatorValue=locatorValue.replace("awb", data(awbNo));
			
			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
				
				androiddriver.findElement(By.xpath(locatorValue)).click();
				waitForSync(3);
				
				String locatorInstruction=getPropertyValue(proppathhht, "gahht_txt_accepInstruction;xpath");
				locatorInstruction=locatorInstruction.replace("instruction", data(expInstruction));
		 		waitForSync(3);
				String actstatus=androiddriver.findElement(By.xpath(locatorInstruction)).getText();
		            waitForSync(3);
				if(actstatus.equals(data(expInstruction)))
				writeExtent("Pass","Sucessfully verified the instruction :"+data(expInstruction)+" in "+screenName);
				else
				writeExtent("Fail","Failed to verify the instruction "+data(expInstruction)+" in "+screenName);
				
				//close the instruction
				clickActionInHHT("gahht_blockPopUpClose;xpath",proppathhht,"Block Popup Close",screenName);
	  			waitForSync(2);
				
			} 
			else{
				writeExtent("Failed", "No instruction icon is displyed "+screenName);
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't fetch the intruction on "+screenName);
		}


	}




	/**
	  * @description :capture checksheet with save button 
	  * @author A-9175
	  * @throws InterruptedException
	  * @throws IOException
	  */
	 public void clickSaveCaptureChecksheet() throws InterruptedException, IOException
		{
		 	waitForSync(2);
			clickActionInHHT("gahht_btn_checksheetSave;xpath",proppathhht," Save Capture Checksheet ",screenName);	
			waitForSync(2);
			
		}
	  /**
	 	 * @Description : Capture Big Reference number
	 	 * @author A-9175
	 	 * @param value
	 	 * @throws AWTException
	 	 * @throws InterruptedException
	 	 * @throws IOException
	 	 */
	 	public void enterBigReferenceNumber(String value) throws AWTException, InterruptedException, IOException
	 	{
	 		    scrollInMobileDevice("Big Reference Number");
	 			enterValueInHHT("gahht_inbx_big_reference_number;accessibilityId",proppathhht,data(value),"Big reference number",screenName);
	 			waitForSync(2);
	 	}
	 	/**
		 * @author A-9844
		 * @throws verify default acceptance location
		 */
		public void verifyDefaultAcceptanceLocation(String expText) throws IOException
		{
			String actText = getTextAndroid("gahht_txt_defaultLocation;xpath", proppathhht, "Default acceptance location",
					screenName);

			if (actText.equals(data(expText))) {
				writeExtent("Pass", "Successfully verified default acceptance location as on " + actText + screenName);
			} else {
				writeExtent("Fail",
						"Failed to verify default acceptance location as " + data(expText) + " on " + screenName);

			}


		}
		
		
		/**
		 * @author A-9844
		 * @param pieces
		 * @param weight
		 * @throws AWTException
		 * @throws InterruptedException
		 * Description : entered loose acceptance details
		 */
		public void enterLooseAcceptanceDetails(String pieces,String weight) throws AWTException, InterruptedException
		{
			try
			{
				System.out.println(data(weight));
				clickActionInHHT("gahht_inbx_dimPcs;accessibilityId",proppathhht,"pieces",screenName);	
				enterPieces(data(pieces));
				scrollInMobileDevice("Select ZON");
				enterValueInHHT("gahht_inbx_Wt;accessibilityId",proppathhht,data(weight),"Weight",screenName);
				writeExtent("Pass", "Loose acceptance details entered as pieces : "+data(pieces)+" weight : "+data(weight));


				map.put("VPPWeight", data(weight));
				map.put("VPPVolume", data("Volume"));
				map.put("VPPType", "loose");


			
			}
			
			catch(Exception e)
			{
				writeExtent("Fail", "Cound not enter the loose acceptance details in "+screenName);
			}
		}

		public void clickSaveOnly() throws IOException
		{
			
			clickActionInHHT("gahht_btn_Save;xpath",proppathhht,"Save",screenName);	
			waitForSync(8);	
			
			
		}
		/**
		 * @author A-10330
		 * @throws IOException
		 * Description : verified statedwt statedpcs and statedsccs
		 * @param pcs wt fullawbno scc
		 * @throws IOException 
		 */
		public void verifyShipmentDetails(String pcs,String wt,String Fullawbno,String scc) throws IOException
		{
			waitForSync(2);
			String locator=getPropertyValue(proppathhht, "gahht_txt_shipmentDetails;xpath");
			String locator1=locator.replace("*",data(Fullawbno));
			androiddriver.findElement(By.xpath(locator1)).click();
			waitForSync(3);
			String locator2=locator.replace("*",data(pcs));
			String StringPcs=androiddriver.findElement(By.xpath(locator2)).getText();

			if(StringPcs.equals(data(pcs)))
			{
				writeExtent("Pass", "Verified Stated pieces "+data(pcs)+" in "+screenName);
			}
			else
			{
				writeExtent("Fail", "Failed to verify stated pieces for "+data(pcs)+" in "+screenName);
			}



			String locator3=locator.replace("*",data(wt));
			String StringWgt=androiddriver.findElement(By.xpath(locator3)).getText();

			if(StringWgt.equals(data(wt)))
			{
				writeExtent("Pass", "Verified Stated weight "+data(wt)+" in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Failed to verify stated weight for "+data(wt)+" in "+screenName);
			}

			String locator4=locator.replace("*",scc);
			String StringSCC=androiddriver.findElement(By.xpath(locator4)).getText();

			if(StringSCC.equals(scc))
			{
				writeExtent("Pass", "Verified Stated scc "+scc+" in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Failed to verify stated scc for "+scc+" in "+screenName);
			}
			clickActionInHHT("gahht_close_shipmentDetails;xpath",proppathhht,"close shipmentdeatilspopup",screenName);

			waitForSync(2);

		}
		/**
		 * @author A-10330
		 * @throws IOException
		 * Description : Entered uld height in acceptancehht
		 * @param height
		 * @throws IOException 
		 */
		public void captureUldHeight(String height) throws IOException
		{
			waitForSync(2);
			enterValueInHHT("gahht_inbx_uldheight;accessibilityId",proppathhht,data(height),"captureuldHeight",screenName);
		}

	 /**
      * @author A-9478
      * Description : Select SCC
      * @throws IOException 
       */
      public void selectSCCValue() throws IOException
      {
            //Scroll down     
            scrollInMobileDevice("Select SCC");
            clickActionInHHT("gahht_btn_clickSCC;xpath",proppathhht,"Select SCC",screenName);
            waitForSync(5);
         
      }     
      /**
  	 * @author A-9847
  	 * @Desc To verify the block reason in GoodsAcceptance HHT
  	 * @param fullAwb
  	 * @param blockReason
  	 */
  	public void verifyBlock(String fullAwb,String blockReason){

  		try{

  			if(data(blockReason).contains("Screening"))
  				blockReason="val~Screening-ACC3";

  			String blockICON=getPropertyValue(proppathhht, "gahht_blockIcon;xpath");
  			blockICON=blockICON.replace("*", data(fullAwb));
  			androiddriver.findElement(By.xpath(blockICON)).click();
  			waitForSync(3);

  			String locatorText=getPropertyValue(proppathhht, "gahht_blockReasons;xpath");
  			locatorText=locatorText.replace("*", data(blockReason));

  			waitForSync(3);
  			System.out.println(androiddriver.findElements(By.xpath(locatorText)).size());

  			if(androiddriver.findElements(By.xpath(locatorText)).size()==1)
  				writeExtent("Pass", "Successfully verified the Reason for Block as - " +data(blockReason)+" on "+ screenName);

  			else
  				writeExtent("Fail", "Failed to verify the Reason for Block on " + screenName);

  			clickActionInHHT("gahht_blockPopUpClose;xpath",proppathhht,"Block Popup Close",screenName);
  			waitForSync(2);


  		}

  		catch(Exception e){
  			writeExtent("Fail", "Failed to verify the Block reason on " + screenName);
  		}
  	}
	/**
     * @author A-9478
     * Description : Select SCC
     * @throws IOException 
      */
     public void selectSCCValue(String SCC) throws IOException
     {
    	 //Scroll down     
    	 scrollInMobileDevice("Select SCC");
    	 clickActionInHHT("gahht_btn_clickSCC;xpath",proppathhht,"Select SCC",screenName);
    	 waitForSync(5);
    	 try
    	 {
    		 String locatorValue=getPropertyValue(proppathhht, "gahht_btn_selectSCCValue;xpath");
    		 locatorValue=locatorValue.replace("SCC", data(SCC));   
    		//Code for handling Select SCC with Double click
    		 if((androiddriver.findElements(By.xpath(locatorValue)).size())!=1)
        		 clickActionInHHT("gahht_btn_clickSCC;xpath",proppathhht,"Select SCC",screenName);
        		 waitForSync(3);
        		 androiddriver.findElement(By.xpath(locatorValue)).click();
        		 waitForSync(3);

    		 //Code for handling Select scc with double click if first click is not selected 

        		 String sccSelected=getPropertyValue(proppathhht, "gahht_btn_selectedSCCValue;xpath");
        		 sccSelected=sccSelected.replace("SCC", data(SCC)); 
        		 if(androiddriver.findElements(By.xpath(sccSelected)).size()==0)
        			 androiddriver.findElement(By.xpath(locatorValue)).click();
        		 waitForSync(2);
        		 writeExtent("Pass", "Successfully selected SCC value "+data(SCC)+" in "+screenName);

        		 clickActionInHHT("gahht_btn_Ok;xpath", proppathhht, " SCC Ok ", screenName); 
        		 waitForSync(5); 

        		
        	 }


        	 catch(Exception e)
        	 {
        		 writeExtent("Pass", "Couldn't select SCC value "+data(SCC)+" in "+screenName);
        	 }



   }
     /**
      * @author A-9478
      * Description : Select SCC
      * @throws IOException 
       */
      public void selectSCC(String SCC) throws IOException
      {
           
    	  try 

          { 

                String locatorValue = getPropertyValue(proppathhht, "gahht_btn_selectSCCValue;xpath"); 
                locatorValue = locatorValue.replace("SCC", SCC); 
                androiddriver.findElement(By.xpath(locatorValue)).click(); 
                waitForSync(1); 
                writeExtent("Pass", "Successfully selected SCC value "+SCC+" in "+screenName); 

          } 

          catch (Exception e) 

          { 

                writeExtent("Fail", "Couldn't select SCC value " + SCC + " in " + screenName); 

          } 

      }
      
      /**
       * @author A-10330        
       * Description : Verify flight number
       * @throws IOException 
       */
      public void verifyFlightDetails(String carrierCode,String flightNumber,String Fullawbnum) throws IOException
      {
    	  String locator1=getPropertyValue(proppathhht, "gahht_txt_AWBNumber;xpath");
    	  locator1=locator1.replace("*",data(Fullawbnum));
    	  androiddriver.findElement(By.xpath(locator1)).click();
    	  waitForSync(3);
    	  String locator=getPropertyValue(proppathhht, "gahht_fltDetails;xpath");
    	  locator = locator.replace("*", data(carrierCode)+" "+data(flightNumber));
    	  String actualText=androiddriver.findElement(By.xpath(locator)).getText(); 
    	  System.out.println(actualText);
    	  if(actualText.equals(data(carrierCode)+" "+data(flightNumber)))
    	  {
    		  writeExtent("Pass", "Verified flight details "+data(carrierCode)+" "+data(flightNumber)+" in "+screenName);
    	  }
    	  else
    	  {
    		  captureScreenShot("Android");
    		  writeExtent("Fail", "Failed to verify flight details for "+data(carrierCode)+" "+data(flightNumber)+" in "+screenName);
    	  }  
    	  clickActionInHHT("gahht_close_shipmentDetails;xpath",proppathhht,"close shipment details popup",screenName);
    	  waitForSync(2);
      }
      public void enterPieces(String pieces) throws AWTException, InterruptedException
      {

    	  Robot r=new Robot();      
    	  String a=pieces;
    	  char c;
    	  int d=a.length(),e=0,f=0;

    	  while(e<d)
    	  {
    		  c=a.charAt(e);
    		  f=(int) c; //converts character to Unicode. 
    		  r.keyPress(KeyEvent.getExtendedKeyCodeForChar(f));
    		  e++;

    		  Thread.sleep(1000);
    	  }

      }


  
  	
      /**
   	 * @author A-9847
   	 * @Desc To select the given sccs if Select SCC filed is present in Android HHT
   	 * @param sccs
   	 * @throws IOException
   	 */
   	
   	public void selectSccs(String sccs[]) throws IOException{


   		scrollInMobileDevice("Location");
   		if(getSizeOfMobileElement("gahht_btn_clickSCC;xpath", proppathhht)!=0)
   		{
   	
   	    clickActionInHHT("gahht_btn_clickSCC;xpath",proppathhht,"SCC icon",screenName);
   		waitForSync(7);

   		try{
   			for(int i=0;i<sccs.length;i++){
   				String screenXpath = getPropertyValue(proppathhht,"gahht_selectMultiplesccs;xpath").replace("*",sccs[i]);
   				androiddriver.findElement(By.xpath(screenXpath)).click();
   				waitForSync(7);
   				writeExtent("Pass", "Sucessfully selected "+ sccs[i]+ " on "+screenName);
   			}
   		}catch(Exception e){
   			writeExtent("Fail", "Failed to select SCC on"+screenName);
   		}
   	
   		int size = getSizeOfMobileElement("gahht_btn_Ok;xpath", proppathhht); 
   		if (size == 1) 
   		{ 
   			clickActionInHHT("gahht_btn_Ok;xpath", proppathhht, " SCC Ok ", screenName); 
   			waitForSync(5); 

   		} 
   		
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

   			/**  //Handling Obligatory Questions - No
String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestions");		
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
		} **/

	}

		
   	/**
	 * Desc : Capture CheckSheet-CDGPHYCHCK
	 * @author A-9844
	 * @param questionText
	 * @throws IOException
	 */
	public void captureCheckSheetCDGPHYCHCK() throws IOException
	{
		//Getting the number of checksheet templates displayed
		List<MobileElement> templates=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_checksheetButton;xpath")));	

		for(MobileElement temp:templates)
		{	
			//Getting templates Questions Count	
			String questionsCount= androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "gahht_btn_checksheetButton;xpath")+"//preceding-sibling::android.widget.TextView[contains(@text,'/')]")).getText();
			String Count=questionsCount.split("/")[1];
            System.out.println(Count);
			//Getting on to each template
			temp.click();	
			waitForSync(2);
			
			List<MobileElement>answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));  			
   			List<MobileElement>answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));	
			List<MobileElement> textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));	
			List<MobileElement>Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
			String RadioAnswers[]=getPropertyValue(checksheetpath, "RadioAnswers").split(",");
			captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);

			/**** Inorder to Scroll till last Question of that template  ***/
			
			
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

			
			
			/********androidScrolllTillPageDown();	
			
			answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));  			
   			answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
   			textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));		
			Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));

			captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);****/
			
			
			/*** *********************   *******************  ***/
			//Click OK after capturing each Checksheet template
			clickActionInHHT("buildUphht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);
			waitForSync(2);	

		}

		//Click Save after Capturing all Checksheet templates
		clickActionInHHT("gahht_btn_checksheetSave;xpath",proppathhht," Save Capture Checksheet ",screenName);	
		waitForSync(2);

	}


 

			

	


			

  	
      /**
  	 * @author A-10330
  	 * @param location
  	 * @param remarks
     * @param pcs
	 * @param wt
	 * Description : Enter ULD acceptance details without adding awb and list the uld details
	 */
     
      public void captureUldAcceptanceDetails(String location,String awb,String pcs,String wgt)
  	{
  		try
  		{
  			//Location
  			waitForSync(2);
  			
  			enterValueInHHT("gahht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);

			//Remarks
			enterValueInHHT("gahht_inbx_remarks;accessibilityId",proppathhht,"ULDACCEPTANCE","Remarks",screenName);
			//click on bttn arrow to navigate to next page
			String locator1=getPropertyValue(proppathhht, "gahht_btnArrow_ShipmentDetails;xpath");
			String locator2=locator1.replace("*",data(wgt));
			androiddriver.findElement(By.xpath(locator2)).click();
			waitForSync(2);
			//click on shipment details to capture uld shipment details
			String locator3=getPropertyValue(proppathhht, "gahht_txt_shipmentDetails;xpath");

			String locator4=locator3.replace("*",data(wgt));
			androiddriver.findElement(By.xpath(locator4)).click();
			waitForSync(5);
			//Enter shipment pcs
			enterValueInHHT("gahht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
			
			waitForSync(3);
			writeExtent("Pass", "ULD acceptance details entered as pieces : "+data(pcs)+" location : "+
					data(location));
			map.put("VPPType", "uld");
			map.put("VPPAwb", data(awb));
			map.put("VPPWeight",getTextAndroid("gahht_inbx_uldWeight;xpath",proppathhht,"Weight",screenName));	
			map.put("VPPVolume",data("Volume"));

  		}

			catch(Exception e)
			{
				writeExtent("Fail", "Could not enter ULD acceptance details entered as  : "+data(pcs)+" location : "+
						data(location));
			}

  	}
      /**
       * @author A-7271
       * Desc : click SCC OK
       */
      public void clickSCCOK()
      {
    	  
    	  try 

          { 

                int size = getSizeOfMobileElement("gahht_btn_Ok;xpath", proppathhht); 
                if (size == 1) 

                { 

                       clickActionInHHT("gahht_btn_Ok;xpath", proppathhht, " SCC Ok ", screenName); 
                       waitForSync(5); 

                } 

          } 

          catch (Exception e) 

          { 
           writeExtent("Fail", "Could not click on SCC OK in "+screenName); 
          } 

      }

     /**
 	 * @Description : Update Shipment
 	 * @author A-9175
 	 * @param SCC
 	 * @throws IOException
 	 */
 	public void updateShipment(String SCC) throws IOException
     {  
        waitForSync(5);
        try
         {      
       	 scrollMobileDevice(SCC);
      	   		String locatorValue=getPropertyValue(proppathhht, "gahht_btn_updateSCCPcs;xpath");
	           locatorValue=locatorValue.replace("SCC", SCC); 
	           System.out.println(locatorValue);
	           String Text= androiddriver.findElement(By.xpath(locatorValue)).getText();
	           System.out.println(Text);
	           int size;
		          
	           do{
	        	   androiddriver.findElement(By.xpath(locatorValue)).click();
	        	   waitForSync(3);
	        	   size=getSizeOfMobileElement("gahht_inbx_splitPcs;accessibilityId",proppathhht);
		
	           }
	           while(size!=1);
	          
	           waitForSync(3);
              writeExtent("Pass", "Successfully selected split Shipment Button for SCC "+SCC+" in "+screenName);
         }
         catch(Exception e)
         {
               writeExtent("Fail", "Couldn't select split Shipment Button for SCC "+SCC+" in "+screenName);
         }

     }
 	
 	/**
 	 * @Description : Update shipment pcs
 	 * @author A-9175
 	 * @param pcs
 	 * @throws AWTException
 	 * @throws InterruptedException
 	 * @throws IOException
 	 */
 	public void updateSplitPcsandWgt(String pcs) throws AWTException, InterruptedException, IOException
  	{
  			clearValueInHHT("gahht_inbx_splitPcs;accessibilityId",proppathhht,"Split Pieces",screenName);
  			enterValueInHHT("gahht_inbx_splitPcs;accessibilityId",proppathhht,pcs,"Split Pieces",screenName);
  			waitForSync(2);
  			clickActionInHHT("gahht_btn_updatePcs;xpath",proppathhht,"Update",screenName);
  			
  	}

     /**
 	 * @Description : click split shipment button
 	 * @author A-9175
 	 * @param SCC
 	 * @throws IOException
 	 */
 	public void splitShipment(String SCC) throws IOException
     {  
        waitForSync(5);
       try
        {      
     	  
     	   String locatorValue=getPropertyValue(proppathhht, "gahht_btn_split_shipmnet;xpath");
	           locatorValue=locatorValue.replace("SCC", SCC);   
	           
	           int size;
	          
	           do{
	        	   androiddriver.findElement(By.xpath(locatorValue)).click();
	        	   size=getSizeOfMobileElement("gahht_inbx_splitPcs;accessibilityId",proppathhht);
		           System.out.println(size);
	           }
	           while(size!=1);
	          
	   

             writeExtent("Pass", "Successfully selected split Shipment Button for SCC "+SCC+" in "+screenName);
        }
        catch(Exception e)
        {
              writeExtent("Fail", "Couldn't select split Shipment Button for SCC "+SCC+" in "+screenName);
        }

     }
 	
 	/**
 	 * @Description : Capture Split pieces information
 	 * @author A-9175
 	 * @param pcs
 	 * @throws IOException
 	 */
 	public void enterSplitPcsandWgt(String pcs) throws AWTException, InterruptedException, IOException
 	{
 			clearValueInHHT("gahht_inbx_splitPcs;accessibilityId",proppathhht,"Split Pieces",screenName);
 			enterValueInHHT("gahht_inbx_splitPcs;accessibilityId",proppathhht,pcs,"Split Pieces",screenName);
 			waitForSync(2);
 			clickActionInHHT("gahht_btn_split_shipmnet_add;xpath",proppathhht,"Add",screenName);
 			
 	}

     /**
 	 * @Description : Selecting split SCCS information
 	 * @author A-9175
 	 * @param SCC
 	 * @throws IOException
 	 */
 	
 	 public void selectSplitSCCValue(String SCC) throws IOException
      {           
 		 waitForSync(5);
      try
      {
    	  if(!SCC.contains("+"))
          	scrollMobileDevice(SCC);
    	  
    	  String SCCval=SCC.substring(0,3); 
           String locatorValue=getPropertyValue(proppathhht, "gahht_btn_split_scc;xpath");
           locatorValue=locatorValue.replace("SCC", SCCval);   
           if(SCC.contains("+"))
           {
           androiddriver.findElement(By.xpath(locatorValue)).click();
           androiddriver.findElement(By.xpath(locatorValue)).click();
           }
           else
           {
        	   androiddriver.findElement(By.xpath(locatorValue)).click();
           }
           

           waitForSync(3);
           writeExtent("Pass", "Successfully selected split SCC value "+SCC+" in "+screenName);
      }
      catch(Exception e)
      {
          writeExtent("Fail", "Couldn't select split SCC value "+SCC+" in "+screenName);
    }

}



     

	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht acceptance screen
	 */
	public void invokeAcceptanceScreen(boolean scroll) throws InterruptedException, AWTException {

		try
		{

			if(scroll)
			{
				scrollInMobileDevice("Acceptance");
			}
			clickActionInHHT("gahht_menu;xpath",proppathhht,"Acceptance menu",screenName);
			waitForSync(2);
			writeExtent("Pass", "Acceptance hht screen is invoked successfully");
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Acceptance hht screen is not invoked successfully");
		}
	}
	/**
     * @author A-9478
     * @param pcs
     * @param wt
     * @param dimension
     * Description : Verify dimension details
     * @throws IOException 
      */
     public void verifyDimensionDetails(String pcs1,String wt1,String pcs2,String wt2, String dimension) throws IOException
     {
           
           
           //Scroll down     
           scrollInMobileDevice("Dimension Capture");
           clickActionInHHT("gahht_btn_dimensionCapture;xpath",proppathhht,"Dimension capture",screenName);
           waitForSync(5);
           scrollTillEnd();
           //Verify the dimension details
           String locatorValue1=getPropertyValue(proppathhht, "gahht_txt_Dimension1;xpath");
           locatorValue1=locatorValue1.replace("Pieces", data(pcs1));        
           locatorValue1=locatorValue1.replace("Weight", data(wt1));   
           locatorValue1=locatorValue1.replace("Length", data(dimension.split(",")[0]));           
           locatorValue1=locatorValue1.replace("Width", data(dimension.split(",")[1]));
           locatorValue1=locatorValue1.replace("Height", data(dimension.split(",")[2]));
           String locatorValue2=getPropertyValue(proppathhht, "gahht_txt_Dimension2;xpath");
           locatorValue2=locatorValue2.replace("Pieces", data(pcs2));        
           locatorValue2=locatorValue2.replace("Weight", data(wt2));   
           locatorValue2=locatorValue2.replace("Length", data(dimension.split(",")[0]));           
           locatorValue2=locatorValue2.replace("Width", data(dimension.split(",")[1]));
           locatorValue2=locatorValue2.replace("Height", data(dimension.split(",")[2]));           
           
           if(androiddriver.findElements(By.xpath(locatorValue1)).size()>0)
           {
                 writeExtent("Pass", "Verified dimension details for "+data(pcs1)+" in "+screenName);
           }
           else
           {
                 writeExtent("Fail", "Failed to verify dimension details for "+data(pcs1)+" in "+screenName);
           }
           
           if(androiddriver.findElements(By.xpath(locatorValue2)).size()>0)
           {
                 writeExtent("Pass", "Verified dimension details for "+data(pcs2)+" in "+screenName);
           }
           else
           {
                 writeExtent("Fail", "Failed to verify dimension details for "+data(pcs2)+" in "+screenName);
           }
           //Click OK
           
           clickActionInHHT("gahht_btn_dimOk;xpath",proppathhht,"Dimension capture OK",screenName);
}
     /**
      * Desc : Clicking Weight Capture Link
      * @author A-9478
      * @throws InterruptedException
      * @throws IOException 
       */
      public void clickWeightCaptureIcon() throws InterruptedException, IOException
      {
            clickActionInHHT("gahht_btn_captureWeightIcon;xpath",proppathhht," Weight Capture Icon",screenName);    
            waitForSync(5);
            
      }
      /**
       * @author A-9478        
       * Description : Verify flight number
       * @throws IOException 
        */
       public void verifyFlightDetails(String carrierCode,String flightNumber) throws IOException
       {
                                     
             String locator=getPropertyValue(proppathhht, "gahht_fltDetails;xpath");
             locator = locator.replace("*", data(carrierCode)+" "+data(flightNumber));
             String actualText=androiddriver.findElement(By.xpath(locator)).getText(); 
             System.out.println(actualText);
             if(actualText.equals(data(carrierCode)+" "+data(flightNumber)))
             {
                   writeExtent("Pass", "Verified flight details "+data(carrierCode)+" "+data(flightNumber)+" in "+screenName);
             }
             else
             {
             	captureScreenShot("Android");
                   writeExtent("Fail", "Failed to verify flight details for "+data(carrierCode)+" "+data(flightNumber)+" in "+screenName);
             }                             
  }

      /**
       * @author A-9478
       * @param pcs
       * @param wt
       * @param dimension
       * Description : Verify Stated Pieces and weight
       * @throws IOException 
        */
       public void verifyStatedPiecesWeight(String pcs,String wt) throws IOException
       {
                                     
             String locator1=getPropertyValue(proppathhht, "gahht_txt_StatedPieces;xpath");
             String StringPcs=androiddriver.findElement(By.xpath(locator1)).getText();
             String actualPcs = (StringPcs.split(" "))[0];
             if(actualPcs.equals(data(pcs)))
             {
                 writeExtent("Pass", "Verified Stated pieces as  "+data(pcs)+" in "+screenName);
             }
             else
             {
                 writeExtent("Fail", "Failed to verify stated pieces as "+data(pcs)+" in "+screenName);
             }
             
             String locator2=getPropertyValue(proppathhht, "gahht_txt_StatedWeight;xpath");
             String StringWgt=androiddriver.findElement(By.xpath(locator2)).getText();
             String actualWgt = (StringWgt.split(" "))[0];
             if(actualWgt.equals(data(wt)))
             {
                 writeExtent("Pass", "Verified Stated weight as "+data(wt)+" in "+screenName);
             }
             else
             {
            	 captureScreenShot("Android");
                 writeExtent("Fail", "Failed to verify stated weight as "+data(wt)+" in "+screenName);
             }
 }
       /**
        * Desc : Entering Transshipment Flight Details
        * @author A-9844
        * @param carrier
        * @param FlightNo
        * @param flightDate
        * @throws AWTException
        * @throws InterruptedException
        * @throws IOException 
        */

       public void entertransShipmentDetails(String carrier,String flightDate) throws AWTException, InterruptedException, IOException
       {
       	try
       	{
       		scrollInMobileDevice("All Parts Received");
       		waitForSync(10);
       		enterValueInHHT("gahht_inbx_fromFlightCarrier;accessibilityId",proppathhht,data(carrier),"Carrier",screenName);
       		
       		if(flightDate.equals("currentDay"))
       		{
       			clickActionInHHT("gahht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
       		}

       		else if(flightDate.equals("nextDay"))
       		{
       			clickActionInHHT("gahht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
       		}
       	    writeExtent("Pass", "Trans-Shipment From Flight details entered as Carrier : "+data(carrier));
       	}
       	
       	catch(Exception e)
       	{
       		 captureScreenShot("Android");
       		writeExtent("Fail", "Cound not enter the  Trans-Shipment From Flight details in "+screenName);
       	}
       }
       
       /**
        * @author A-9478
        * @param pcs
        * @param wt
        * @param dimension
        * Description : Verify Accepted Pieces and weight
        * @throws IOException 
         */
        public void verifyAcceptedPiecesWeight(String pcs,String wt) throws IOException
        {
                                      
              String locator1=getPropertyValue(proppathhht, "gahht_txt_AcceptedPieces;xpath");
              String StringPcs=androiddriver.findElement(By.xpath(locator1)).getText();
              String actualPcs = (StringPcs.split(" "))[0];
              if(actualPcs.equals(data(pcs)))
              {
                    writeExtent("Pass", "Verified Accepted pieces "+data(pcs)+" in "+screenName);
              }
              else
              {
            	  captureScreenShot("Android");
                    writeExtent("Fail", "Failed to verify accepted pieces for "+data(pcs)+" in "+screenName);
              }
              
              String locator2=getPropertyValue(proppathhht, "gahht_txt_AcceptedWeight;xpath");
              String StringWgt=androiddriver.findElement(By.xpath(locator2)).getText();
              String actualWgt = (StringWgt.split(" "))[0];
              if(actualWgt.equals(data(wt)))
              {
                    writeExtent("Pass", "Verified Accepted weight "+data(wt)+" in "+screenName);
              }
              else
              {
            	  captureScreenShot("Android");
                    writeExtent("Fail", "Failed to verify accepted weight for "+data(wt)+" in "+screenName);
              }
   }
        
        /**
         * @author A-9478        
         * Description : Verify origin and destination
         * @throws IOException 
          */
         public void verifyOriginAndDestination(String AWBNO,String Origin, String Destination) throws IOException
         {
                                       
               String locator1=getPropertyValue(proppathhht, "gahht_txt_Origin;xpath");
               locator1 = locator1.replace("AWBNo", data(AWBNO));
              
               String actualOrigin=androiddriver.findElement(By.xpath(locator1)).getText();        
               if(actualOrigin.equals(data(Origin)))
               {
                     writeExtent("Pass", "Verified origin "+data(Origin)+" in "+screenName);
               }
               else
               {
            	   captureScreenShot("Android");
                     writeExtent("Fail", "Failed to verify origin for "+data(Destination)+" in "+screenName);
               }
               String locator2=getPropertyValue(proppathhht, "gahht_txt_Destination;xpath");
               locator2 = locator2.replace("AWBNo", data(AWBNO));
               String actualDest=androiddriver.findElement(By.xpath(locator2)).getText();        
               if(actualDest.equals(data(Destination)))
               {
                     writeExtent("Pass", "Verified Destination "+data(Destination)+" in "+screenName);
               }
               else
               {
            	   captureScreenShot("Android");
                     writeExtent("Fail", "Failed to verify destination for "+data(Destination)+" in "+screenName);
               }
               
    }
         
         /**
          * @author A-9478        
          * Description : Verify origin and destination
          * @throws IOException 
           */
          public void verifySCC(String AWBNo,String SCC) throws IOException
          {
                                        
        	  String sccValue=data(SCC).split(" ")[0];
  			String expSCC1=sccValue+" + 2";  
  			String expSCC2=sccValue+" + 3";     
  			String expSCC3=sccValue+" + 1"; 


  			String locator1=getPropertyValue(proppathhht, "gahht_txt_SCC;xpath");
  			locator1 = locator1.replace("AWBNo", data(AWBNo));
  			String actualSCC=androiddriver.findElement(By.xpath(locator1)).getText();  
  			
  			if(actualSCC.equals(expSCC1) || actualSCC.equals(expSCC2)|| actualSCC.equals(expSCC3))
  			{
  				writeExtent("Pass", "Verified SCC "+data(SCC)+" in "+screenName);
  			}
  			else
  			{
  				captureScreenShot("Android");
  				writeExtent("Fail", "Failed to verify SCC for "+data(SCC)+" in "+screenName);
  			}                             
  		}

/**
      * Desc : Click cancel button in Unable to fetch data pop up
      * @author A-9478
      * @throws InterruptedException
      * @throws IOException 
       */
      public void clickCancelButton() throws InterruptedException, IOException
      {
            String locatorValue=getPropertyValue(proppathhht, "gahht_btn_cancelButton;xpath");
            String locatorValue1=getPropertyValue(proppathhht, "gahht_btn_cancelButtonScanTheBarcode;xpath");
            if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
            {
                  clickActionInHHT("gahht_btn_cancelButton;xpath",proppathhht," Cancel button ",screenName);   
                  waitForSync(5);
            }
            else if(androiddriver.findElements(By.xpath(locatorValue1)).size()>0)
            {
                  clickActionInHHT("gahht_btn_cancelButtonScanTheBarcode;xpath",proppathhht," Cancel button ",screenName);  
                  waitForSync(5);
            }
            
      }
 
/**
      * Desc : Entering Scale Weight value
      * @author A-9478
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException 
       */
      public void enterScaleWeightValue(String value) throws AWTException, InterruptedException, IOException
      {           
    	  enterValueInHHT("gahht_inbx_scaleWight;xpath", proppathhht, data(value), "Scale Weight Value", screenName);
    	  String locatorValue = getPropertyValue(proppathhht, "btn_errorMsg;xpath");
    	  locatorValue = locatorValue.replace("*", "Please scan the Barcode from specified Scale ID Interface");
    	  waitForSync(3);
    	  if (androiddriver.findElements(By.xpath(locatorValue)).size() == 1) {
    		  androiddriver.findElement(By.xpath(locatorValue)).click();
    		  waitForSync(3);
    	  }
    	  waitForSync(3);

      }


	/**
	 * @author A-7271
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterValue(String value) throws AWTException, InterruptedException, IOException
	{
		enterValueInHHT("gahht_inbx_enterValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
		waitForSync(12);
		map.put("VPPAwb", data(value));
		map.put("VPPULDNumber", data(value));
		handleNewULDWarning();

		String locator = getPropertyValue(proppathhht, "gahht_txt_checksheet;xpath");

		//capture  checksheet
		if(androiddriver.findElements(By.xpath(locator)).size()==1)
		{

			captureCheckSheetCDGPHYCHCK();
		}
	}
	/**
	 * @author A-7271
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterValueAndVerifyChecksheet(String value) throws AWTException, InterruptedException, IOException
	{
		boolean checkSheetVerification=false;
		enterValueInHHT("gahht_inbx_enterValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
		waitForSync(12);
		map.put("VPPAwb", data(value));
		map.put("VPPULDNumber", data(value));
		handleNewULDWarning();

		String locator = getPropertyValue(proppathhht, "gahht_txt_checksheet;xpath");

		//capture CDGPHYCHCK checksheet at CDG
		if(androiddriver.findElements(By.xpath(locator)).size()==1)
		{

			captureCheckSheetCDGPHYCHCK();
			checkSheetVerification=true;
		}
		
		if(!checkSheetVerification)
		{
			writeExtent("Fail", "No checksheet details found for "+(value)+" on "+screenName);
		}
	}
	
	/**
	 * @author A-7271
	 * @param pcs
	 * @param wt
	 * @param dimension
	 * Description : Enter the dimension details
	 */
	public void enterDimensionDetails(String pcs,String wt,String dimension)
	{
		
		try
		{
	
		//Scroll down
	
		scrollInMobileDevice("Dimension Capture");
		clickActionInHHT("gahht_btn_dimensionCapture;xpath",proppathhht,"Dimension capture",screenName);
		waitForSync(5);
		enterValueInHHT("gahht_inbx_dimPcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		enterValueInHHT("gahht_inbx_dimWt;accessibilityId",proppathhht,data(wt),"Weight",screenName);
		//Dimensions
		
		enterValueInHHT("gahht_inbx_dimLen;accessibilityId",proppathhht,data(dimension.split(",")[0]),"Length",screenName);
		enterValueInHHT("gahht_inbx_dimWidth;accessibilityId",proppathhht,data(dimension.split(",")[1]),"Width",screenName);
		enterValueInHHT("gahht_inbx_dimHeight;accessibilityId",proppathhht,data(dimension.split(",")[2]),"Height",screenName);
		
		//Click OK
		
		clickActionInHHT("gahht_btn_dimOk;xpath",proppathhht,"Dimension capture OK",screenName);
		
	
		 waitForSync(6);
		 writeExtent("Pass", "Entered dimension details in "+screenName);
		
		}
		
		catch(Exception e)
		{
			 writeExtent("Fail", "Failed to entered dimension details in "+screenName);
		}
	}
	
	
	/**
	 * @author A-7271
	 * @param location
	 * @param awb
	 * @param pcs
	 * @param wt
	 * Description : Enter ULD acceptance details
	 */
	public void enterUldAcceptanceDetails(String location,String awb,String pcs)
	{
		try
		{
			//Location
			
			enterValueInHHT("gahht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);

			//Remarks
			enterValueInHHT("gahht_inbx_remarks;accessibilityId",proppathhht,"ULDACCEPTANCE","Remarks",screenName);
			waitForSync(3);

			clickActionInHHT("gahht_btn_addNewAwb;xpath",proppathhht,"Add New AWB",screenName);
			
			

			waitForSync(6);
			//Enter AWB
			enterValueInHHT("gahht_inbx_awbNumber;accessibilityId",proppathhht,data(awb),"Awb No",screenName);
			waitTillMobileElementDisplay(proppathhht,"gahht_txt_checksheet;xpath","xpath",20);
			String locator = getPropertyValue(proppathhht, "gahht_txt_checksheet;xpath");

			//capture CDGPHYCHCK checksheet at CDG
			if(androiddriver.findElements(By.xpath(locator)).size()==1)
			{

				captureCheckSheetCDGPHYCHCK();
			}
			waitForSync(5);

			//Enter shipment pcs
			enterValueInHHT("gahht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
			
			waitForSync(3);
			writeExtent("Pass", "ULD acceptance details entered as pieces : "+data(pcs)+" location : "+
					data(location));
			map.put("VPPType", "uld");
			map.put("VPPAwb", data(awb));
			map.put("VPPWeight",getTextAndroid("gahht_inbx_uldWeight;xpath",proppathhht,"Weight",screenName));	
			map.put("VPPVolume",data("Volume"));

			
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter ULD acceptance details entered as  : "+data(pcs)+" location : "+
					data(location));
		}

		
		
	}
	/**
	 * @author A-9844
	 * @param location
	 * @param awb
	 * @param pcs
	 * @param wt
	 * Description : Enter ULD acceptance details
	 */
	public void enterUldAcceptanceDetail(String location,String awb,String pcs)
	{
		try
		{
			//Location
			
			enterValueInHHT("gahht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);

			//Remarks
			enterValueInHHT("gahht_inbx_remarks;accessibilityId",proppathhht,"ULDACCEPTANCE","Remarks",screenName);
			waitForSync(3);

			clickActionInHHT("gahht_btn_addNewAwb;xpath",proppathhht,"Add New AWB",screenName);
			
			//Enter AWB
			enterValueInHHT("gahht_inbx_awbNumber;accessibilityId",proppathhht,data(awb),"Awb No",screenName);
			waitForSync(3);
			
            String locatorPcs=getPropertyValue(proppathhht, "gahht_inbx_piecesdisplayed;xpath");
			
			if((androiddriver.findElement(By.xpath(locatorPcs)).getText())!=data(pcs)){
			
			for(int i=0;i<Integer.parseInt(data(pcs))-1;i++){
				
				clickActionInHHT("gahht_btn_piecesAdd;xpath",proppathhht,"Add pieces button",screenName);
				waitForSync(1);
			}
			}
					
			
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter ULD acceptance details on "+screenName);
		}
	
	}

	/**
	 * @author A-7271
	 * @param location
	 * @param awb
	 * @param pcs
	 * @param wt
	 * Description : Enter ULD acceptance details
	 */
	public void enterUldAcceptanceDetails(String location,String awb)
	{
		try
		{
			//Location
			
			enterValueInHHT("gahht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);

		
			clickActionInHHT("gahht_btn_addNewAwb;xpath",proppathhht,"Add New AWB",screenName);
			
			

			waitForSync(6);
			//Enter AWB
			enterValueInHHT("gahht_inbx_awbNumber;accessibilityId",proppathhht,data(awb),"Awb No",screenName);
			waitForSync(5);
			map.put("VPPType", "uld");
			map.put("VPPAwb", data(awb));
			map.put("VPPWeight",getTextAndroid("gahht_inbx_uldWeight;xpath",proppathhht,"Weight",screenName));	
			map.put("VPPVolume",data("Volume"));
			

			
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter ULD acceptance details on "+screenName);
					
		}

		
		
	}
	/**
	 * Desc : Clicking Weight Capture Link
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickWeightCapture() throws InterruptedException, IOException
	{
		
		
		scrollInMobileDevice("Weight Capture");
		clickActionInHHT("gahht_btn_wgtCapture;xpath",proppathhht," Weight Capture ",screenName);	
		waitForSync(5);
		
	}
	
	/**
	 * Desc : Entering Scale Weight
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterScaleWeight(String value) throws AWTException, InterruptedException, IOException
	{		
			scrollInMobileDevice("Scale Weight ( Kg)");
			enterValueInHHT("gahht_inbx_scaleWight;accessibilityId",proppathhht,data(value),"Scale Weight Value",screenName);
			waitForSync(3);
			clickActionInHHT("gahht_lbl_ScaleidPopUp;xpath",proppathhht," Pop Up ",screenName);
			waitForSync(3);
	}
	

	/**
	 * Desc : Entering Scale Weight ID
	 * @author A-9175
	 * @param value
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterScaleWeightID(String value) throws AWTException, InterruptedException, IOException
	{
		enterValueInHHT("gahht_inbx_scaleWightID;xpath", proppathhht, data(value), "Scale Weight ID", screenName);
		String locatorValue = getPropertyValue(proppathhht, "btn_errorMsg;xpath");
		locatorValue = locatorValue.replace("*", "Unable to fetch data");
		waitForSync(3);
		if (androiddriver.findElements(By.xpath(locatorValue)).size() == 1) {
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);
		}
	}
	
	
	
	
	/**
	 * Desc : Clicking Ok button in Weight Capture Screen
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickOkWeightCapture() throws InterruptedException, IOException
	{
		clickActionInHHT("gahht_btn_ClickOkwgtCapture;xpath",proppathhht," Ok ",screenName);	
		waitForSync(5);
		
	}
	
	/**
	 * Desc : Entering Contour
	 * @author A-9175
	 * @param Contour
	 * @throws IOException 
	 */
	public void enterContour(String Contour) throws IOException
	{

			enterValueInHHT("gahht_inbx_Contour;accessibilityId",proppathhht,data(Contour),"Contour",screenName);
	}

	/**
	 * @author A-7271
	 * Desc: Add ULD acceptance details
	 * @throws IOException 
	 */
	public void addULDDetails() throws IOException
	{
		try
		{
	
		clickActionInHHT("gahht_btn_Add;xpath",proppathhht,"Add",screenName);	
		
		 waitForSync(6);
		 writeExtent("Pass", "Shipment details added to the ULD in "+screenName);
		}
		
		catch(Exception e)
		{
			captureScreenShot("Android");
			 writeExtent("Fail", "Could not add shipment details added to the ULD in "+screenName);
		}
	}
	/**
	 * @author A-9175
	 * @Desc : To capture storage Position
	 * @throws IOException
	 */
	public void enterStoragePosition() throws IOException {
		// Scroll down
				scrollInMobileDevice("Storage Position");
				clickActionInHHT("gahht_btn_storagePosDetails;xpath", proppathhht, "Select Storage Position", screenName);
				waitTillMobileElementDisplay(proppathhht, "gahht_btn_storagePosDetails;xpath", "xpath", 20);

				map.put("storagePOS", WebFunctions.getPropertyValue(toproppath, "Storage_Position_CDG"));
				String locatorStoragePOSValue=getPropertyValue(proppathhht, "gahht_btn_SelectStoragePOSValue;xpath");
				locatorStoragePOSValue=locatorStoragePOSValue.replace("POS", data("storagePOS"));
				scrollMobileDevice(data("storagePOS"));
				androiddriver.findElement(By.xpath(locatorStoragePOSValue)).click();
				writeExtent("Pass", "Selected Storage Position as "+data("storagePOS")+" in Goods Acceptance hht screen");

	}

	/**
	 * @author A-7271
	 * @param pieces
	 * @param weight
	 * @param location
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered loose acceptance details
	 */
	public void enterLooseAcceptanceDetails(String pieces,String weight,String location) throws AWTException, InterruptedException
	{
		try
		{
			System.out.println(data(weight));
			
			enterValueInHHT("gahht_inbx_location;accessibilityId",proppathhht,data(location),"Location",screenName);
			waitForSync(3);
			enterStoragePosition();
			clickActionInHHT("gahht_inbx_Pcs;accessibilityId",proppathhht,"pieces",screenName);
			enterValueInHHT("gahht_inbx_Pcs;xpath",proppathhht,data(pieces),"Pieces",screenName);
			String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
			locatorValue=locatorValue.replace("*", "Invalid AWB scanned");
			waitForSync(3);
			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);
			}
			waitForSync(3);
//				enterPieces(data(pieces));
			scrollInMobileDevice("Select ZON");
			enterValueInHHT("gahht_inbx_Wt;accessibilityId",proppathhht,data(weight),"Weight",screenName);
			writeExtent("Pass", "Loose acceptance details entered as pieces : "+data(pieces)+" weight : "+data(weight)+" location : "+
			data(location));
			map.put("VPPWeight", data(weight));
			map.put("VPPVolume", data("Volume"));
			map.put("VPPType", "loose");




		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter the loose acceptance details in "+screenName);
		}
	}
	/***
	 * Desc : Enable skid
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	
	public void checkSkid() throws InterruptedException, IOException
	{
		
		
		scrollInMobileDevice("With Skid");
		//click With Skid
		clickActionInHHT("gahht_btn_Skid;xpath",proppathhht," Skid ",screenName);	
		waitForSync(1);
		
	}
	/**
	 * Desc : Verifying All Parts Recieved status is checked or not
	 * @author A-9175
	 * @param expStatus
	 * @throws InterruptedException
	 */
	public void verifyAllPartsEnabledStatus(String expStatus) throws InterruptedException
	{
		
		
		scrollInMobileDevice("All Parts Received");
		//check With All Parts Received toggle bar enabled
		String locatorValue=getPropertyValue(proppathhht, "gahht_lbl_AllPartsEnabledStatus;xpath");
		locatorValue=locatorValue.replace("expStatus", expStatus);
		String status=androiddriver.findElement(By.xpath(locatorValue)).getText();
		try
		{
			
			if(status.equals(expStatus))
				writeExtent("Pass", "successfully Verified status as "+expStatus+"in "+screenName);	
			waitForSync(1);
		}
		catch (Exception e)
		{
			writeExtent("Fail", "Could not  Verified status as "+expStatus+"in "+screenName);	
		}
		
		
	}
	/**
	 * Desc : Verifying All Parts Recieved status is checked or not
	  *@author A-7271
	 * @param expStatus
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAllPartsEnabledStatusInULDAcc(String expStatus) throws InterruptedException, IOException
	{
		
		
		
		//check With All Parts Received toggle bar enabled
		String locatorValue=getPropertyValue(proppathhht, "gahht_lbl_AllPartsEnabledStatus;xpath");
		locatorValue=locatorValue.replace("expStatus", expStatus);
		String status=androiddriver.findElement(By.xpath(locatorValue)).getText();
		try
		{
			
			if(status.equals(expStatus))
				writeExtent("Pass", "successfully Verified status as "+expStatus+" for All Parts Received on "+screenName);	
			waitForSync(1);
		}
		catch (Exception e)
		{
			 captureScreenShot("Android");
			writeExtent("Fail", "Could not  Verified status as "+expStatus+" for All Parts Received on "+screenName);	
		}
		
		
	}
	/**
	 * @author A-7271
	 * Description : Check All parts received
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void checkAllPartsReceived() throws InterruptedException, IOException
	{
		
		
		scrollInMobileDevice("All Parts Received");
		//Click all part received
		clickActionInHHT("gahht_btn_AllPartsReceived;xpath",proppathhht,"All Parts Received",screenName);	
		//For Handling - if "All parts received" is not toggled for first time
		String locator=getPropertyValue(proppathhht, "gahht_txt_allpartsreceivedYesStatus;xpath");	
		while(androiddriver.findElements(By.xpath(locator)).size()==0)
		{
			clickActionInHHT("gahht_btn_AllPartsReceived;xpath",proppathhht,"All Parts Received",screenName);	
		}
		waitForSync(1);

		
	}
	/**
	 * @author A-7271
	 * Description : Check All parts received
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void checkAllPartsReceivedForUldAcceptance() throws InterruptedException, IOException
	{
		
		//Click all part received
		clickActionInHHT("gahht_btn_AllPartsReceived;xpath",proppathhht,"All Parts Received",screenName);	
		 waitForSync(1);
		
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * Description : Entered loose acceptance details
	 * @throws IOException 
	 */
	public void saveULDAcceptanceDetails() throws InterruptedException, IOException
	{
		
		
		
			clickActionInHHT("gahht_btn_Save;xpath",proppathhht,"Save",screenName);	
			waitForSync(10);
			verifyHHTSaveDetails(screenName);
			
			
			
			
		
}
	/**
	 * Desc : Entering Transshipment Flight Details
	 * @author A-9175
	 * @param carrier
	 * @param FlightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */

public void entertransShipmentDetails(String carrier,String FlightNo,String flightDate) throws AWTException, InterruptedException, IOException
	{
		try
		{
			scrollInMobileDevice("All Parts Received");
			waitForSync(10);
			enterValueInHHT("gahht_inbx_fromFlightCarrier;accessibilityId",proppathhht,data(carrier),"Carrier",screenName);
			enterValueInHHT("gahht_inbx_fromFlightNumber;accessibilityId",proppathhht,data(FlightNo),"FlightNo",screenName);
			if(flightDate.equals("currentDay"))
			{
				clickActionInHHT("gahht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
			}

			else if(flightDate.equals("nextDay"))
			{
				clickActionInHHT("gahht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
			}
		    writeExtent("Pass", "Trans-Shipmen From Flight details entered as Carrier : "+data(carrier)+" FlightNo : "+data(FlightNo));
		}
		
		catch(Exception e)
		{
			 captureScreenShot("Android");
			writeExtent("Fail", "Cound not enter the  Trans-Shipment From Flight details in "+screenName);
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
 * @author A-7271
 * @throws IOException 
 */
public void getVPPFeed() throws IOException
{
	//Check for transit data
	String transitStation=data("Transit");
	boolean transitCdg=false;
	
	if(transitStation!=null)
		if(transitStation.equals("CDG"))
			transitCdg=true;

	if(data("Origin").equals("CDG") || transitCdg)
	{

		System.out.println(data("VPPAwb"));
		System.out.println(data("VPPWeight"));
		System.out.println(data("VPPVolume"));

		/******* POST REQUEST****/
		if(data("VPPType").equals("loose"))
			jsonbody.postRequest(data("VPPAwb"),data("VPPWeight"),data("VPPVolume"),"100","100","100");
		else
			jsonbody.postRequest(data("VPPAwb"),data("VPPWeight"),data("VPPVolume"),"100","100","100",data("VPPULDNumber"));

	}	
}


	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * Description : Entered loose acceptance details
	 * @throws IOException 
	 */
	public void saveAcceptanceDetails() throws InterruptedException, IOException
	{
		
		try
		{
			clickActionInHHT("gahht_btn_Save;xpath",proppathhht,"Save",screenName);
			waitForSync(10);
			writeExtent("Pass", "Acceptance details saved successfully in "+screenName);

			//click YES in incompatible SCC present pop up if it is present
			String locatorYes=getPropertyValue(proppathhht, "btn_Yes;xpath");
			if(androiddriver.findElements(By.xpath(locatorYes)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorYes)).click();
				waitForSync(5);
			}
			/***** WEIGHT RECEPTION FROM VPP ****/
				 
			
			String locator = getPropertyValue(proppathhht, "gahht_txt_checksheet;xpath");

			//capture  checksheet
			if(androiddriver.findElements(By.xpath(locator)).size()==1)
			{

				captureCheckSheetCDGPHYCHCK();
			}
			
			getVPPFeed();
			/**********************************************/

		}
		catch (Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Acceptance details not saved successfully in "+screenName);
		}
}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * Description : Entered loose acceptance details
	 * @throws IOException 
	 */
	public void saveAcceptanceDetailsAndVerifyCheckSheets() throws InterruptedException, IOException
	{
		boolean checkSheet=false;
		
		try
		{
			clickActionInHHT("gahht_btn_Save;xpath",proppathhht,"Save",screenName);
			waitForSync(10);
			writeExtent("Pass", "Acceptance details saved successfully in "+screenName);

			//click YES in incompatible SCC present pop up if it is present
			String locatorYes=getPropertyValue(proppathhht, "btn_Yes;xpath");
			if(androiddriver.findElements(By.xpath(locatorYes)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorYes)).click();
				waitForSync(5);
			}
			/***** WEIGHT RECEPTION FROM VPP ****/
				    
			
			getVPPFeed();
			/**********************************************/
			
			String locator = getPropertyValue(proppathhht, "gahht_txt_checksheet;xpath");
			//capture  checksheet
			if(androiddriver.findElements(By.xpath(locator)).size()==1)
			{

				captureCheckSheetCDGPHYCHCK();
				checkSheet=true;
			}
			
			if(!checkSheet)
			{
				writeExtent("Fail", "No checksheet details found after acceptance save on "+screenName);
			}

		}
		catch (Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Acceptance details not saved successfully in "+screenName);
		}
}

}
