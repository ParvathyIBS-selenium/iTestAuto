package screens;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import io.appium.java_client.MobileElement;

public class BreakdownHHT extends CustomFunctions {
	
	String sheetName = "BreakdownHHT";
	String screenName = "BreakdownHHT";
	
	public static String checksheetpath = "\\src\\resources\\Checksheet.properties";
	public static String locationpath = "\\src\\resources\\Location.properties";
	public static String toproppath="\\src\\resources\\TO.properties";
	public BreakdownHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht BreakdownHHT screen
	 * @throws IOException 
	 */
	public void invokeBreakdownHHTScreen() throws InterruptedException, AWTException, IOException {
	
		scrollInMobileDevice("Breakdown");
		clickActionInHHT("breakdownhht_menu;xpath",proppathhht,"Breakdown menu",screenName);
		waitForSync(5);
	}
	
	/**
	 * @author A-10690
	 * @param HAWB number
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering HAWB number
	 * @throws IOException 
	 */
	public void enterHAWB(String value) throws AWTException, InterruptedException, IOException
	{
		waitTillMobileElementDisplay(proppathhht,"breakdown_txt_enterHAWB;xpath","xpath");
		enterValueInHHT("breakdown_txt_enterHAWB;xpath",proppathhht,data(value),"List Value",screenName);
		waitForSync(6);
	}
	
	/**
	 * @Desc : Clicking Breakdown complete button follwing by the confirmation message 
	 * @author A-10690
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBreakdownCompleteWithHAWB() throws AWTException, InterruptedException, IOException
	{
			waitForSync(5);
			clickActionInHHT("breakdownhht_btn_breakdownComplete;xpath",proppathhht,"Breakdown Complete",screenName);
			waitForSync(3);
			int size = getSizeOfMobileElement("breakdownhht_btn_YES;xpath", proppathhht);
			if (size == 1)

			{
			clickActionInHHT("breakdownhht_btn_YES;xpath",proppathhht,"Yes",screenName);
			}
			waitForSync(4);
			int checksheetsize = getSizeOfMobileElement("breakdownhht_txt_checksheet;xpath", proppathhht);
			if (checksheetsize == 1) {
			captureCheckSheetBDNCPLT();
			}

	}
	/**
	 * @author A-8783
	 * @param SCC
	 * DESC Verify the Splitted SCC  value
	 * @throws InterruptedException
	 */
	public void verifySplitSCC(String SCC) throws IOException
	{

		scrollInMobileDevice(SCC);

		String locator=getPropertyValue(proppathhht, "breakdownhht_txtThruOptions;xpath");
		locator=locator.replace("*",SCC);

		if(androiddriver.findElements(By.xpath(locator)).size()==1)
			writeExtent("Pass", "Sucessfully verified the option "+SCC+" on "+screenName); 

		else
			writeExtent("Fail", "Failed to verify UldNo "+SCC+" in"+screenName);
	}
	/**
	 * Desc : Verifying unitized button status
	 * @author A-10690
	 * @param expected status
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyUnitizedButton(String expvalue) throws AWTException, InterruptedException, IOException
	{
		String unitized=getPropertyValue(proppathhht, "breakdownhht_unitizedstatus;xpath").replace("*",data(expvalue));;
		if(androiddriver.findElements(By.xpath(unitized)).size()==1)
		{
			writeExtent("Pass", "Verified UNITIZED button status as" + data(expvalue) + " in " + screenName);
		} else
			writeExtent("Fail", "Failed to verify  UNITIZED button  status as " + data(expvalue)  + " in " + screenName);

	}
	/**
	 * @author A-9844
	 * To verify Pieces and weight against each awb
	 * @param sccRank
	 */
	public void verifyPiecesWeight(String awb[],String pieces,String weight,String Status){

		try{
			
			System.out.println(awb.length);

			for(int i=0;i<awb.length;i++)
			{
				String locatorPieces=getPropertyValue(proppathhht, "breakdownhht_awbPcsWgt;xpath").replace("awb",awb[i]);
				locatorPieces=locatorPieces.replace("status", data(Status));
				locatorPieces=locatorPieces.replace("number", data(pieces));
                                waitForSync(2);
				
				int sizePieces = androiddriver.findElements(By.xpath(locatorPieces)).size();
				System.out.println(sizePieces);
				
				String locatorWeight=getPropertyValue(proppathhht, "breakdownhht_awbPcsWgt;xpath").replace("awb",awb[i]);
				locatorWeight=locatorWeight.replace("status", data(Status));
				locatorWeight=locatorWeight.replace("number", data(weight));

				int sizeWeight = androiddriver.findElements(By.xpath(locatorWeight)).size();
				System.out.println(sizeWeight);

				if (sizePieces > 0) {
					writeExtent("Pass", "Verified "+data(Status)+" Pieces " + data(pieces) + " in " + screenName);
				} else
					writeExtent("Fail", "Failed to verify "+data(Status)+" Pieces " + data(pieces) + " in " + screenName);


				if (sizeWeight > 0) {
					writeExtent("Pass", "Verified "+data(Status)+" Weight " + data(weight) + " in " + screenName);
				} else
					writeExtent("Fail", "Failed to verify "+data(Status)+" Weight " + data(weight) + " in " + screenName);
			}
		}catch(Exception e){

			writeExtent("Fail"," Pieces and weight is not displayed on "+screenName);

		}
	}
	/**
	 * Desc : verify Partial THRU and Direct THRU options in the prompt 
	 * @author A-9844
	 * @param option
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyThruOptions(int count,String[] options) throws InterruptedException, AWTException {

		try{
			for(int i=0;i<count;i++){


				String locator=getPropertyValue(proppathhht, "breakdownhht_txtThruOptions;xpath");
				locator=locator.replace("*", options[i]);

				if(androiddriver.findElements(By.xpath(locator)).size()==1)
					writeExtent("Pass", "Sucessfully verified the option "+options[i]+" on "+screenName); 

				else
					writeExtent("Fail", "Failed to verify UldNo "+options[i]+" in"+screenName); 


			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't verify the thru options on "+screenName);
		}


	}

	/**
	 * @author A-9844
	 * @Desc choose thru options-Partial THRU or Direct THRU
	 * @throws IOException
	 */
	public void selectThruOption(String option) throws IOException{	  

		try{
		String locator=getPropertyValue(proppathhht, "breakdownhht_txtThruOptions;xpath");
		locator=locator.replace("*", option);
		androiddriver.findElement(By.xpath(locator)).click();
		writeExtent("Pass", "Sucessfully selected the option : "+option+" on "+screenName);
		}
		catch (Exception e) {
			writeExtent("Fail", "Could not select the option : "+option+" on "+screenName);
		}
		verifyHHTSaveDetails(screenName);

	}

	/**
	 * @author A-9844
	 * Desc - Verify origin and destination
	 * @param Origin
	 * @param Destination
	 */
	public void verifyOriginAndDestination(String awb[],String Origin, String Destination) {
		try {
			
			
			System.out.println(awb.length);

			for(int i=0;i<awb.length;i++)
			{
			String locatorOrg = getPropertyValue(proppathhht, "breakdownhht_awbOrgDes;xpath");
			locatorOrg = locatorOrg.replace("awb",awb[i] );
			locatorOrg = locatorOrg.replace("route", data(Origin));
			waitForSync(1);
			int sizeOrg = androiddriver.findElements(By.xpath(locatorOrg)).size();

			String locatorDest = getPropertyValue(proppathhht, "breakdownhht_awbOrgDes;xpath");
			locatorDest = locatorDest.replace("awb",awb[i] );
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
			}

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the origin and destination on " + screenName);

		}
	}

	/**
	 * @author A-10690
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering awbnumber in breakdown hht Screen
	 * @throws IOException 
	 */
	public void enterAWBValue(String value) throws AWTException, InterruptedException, IOException
	{
			enterValueInHHT("breakdownhht_inbx_Awbdetails;accessibilityId",proppathhht,data(value),"List Value",screenName);
			waitForSync(6);
	}
	/**
	 * Desc : Adding Location
	 * @author A-9844
	 * @param loc
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void enterLocation(String loc) throws AWTException, InterruptedException, IOException
	{
		waitTillMobileElementDisplay(proppathhht,"breakdownhht_locationType;xpath","xpath",15);
			clickActionInHHT("breakdownhht_locationType;xpath",proppathhht,"Location",screenName);
			waitTillMobileElementDisplay(proppathhht,"breakdownhht_searchHere;xpath","xpath",5);
			
			String search = getPropertyValue(proppathhht, "breakdownhht_searchHere;xpath");
			int searchSize = androiddriver.findElements(By.xpath(search)).size();
			if(searchSize!=1)
			{
				clickActionInHHT("breakdownhht_locationType;xpath",proppathhht,"Location",screenName);
			}
			waitTillMobileElementDisplay(proppathhht,"breakdownhht_searchHere;xpath","xpath",10);
			enterValueInHHT("breakdownhht_searchHere;xpath",proppathhht,data(loc),"Location",screenName);
			String locationDisplayed=getPropertyValue(proppathhht, "breakdownhht_displayedLocation;xpath");
			
			locationDisplayed=locationDisplayed.replace("*", data(loc));
			androiddriver.findElement(By.xpath(locationDisplayed)).click();
			waitForSync(5);

	}
	/**
	 * @author A-8783 
	 * Desc - Verify origin and destination
	 * @param Origin
	 * @param Destination
	 */
	public void verifyOriginAndDestination(String Origin, String Destination) {
		try {
			String locatorOrg = getPropertyValue(proppathhht, "breakdownhht_txt_route;xpath");
			locatorOrg = locatorOrg.replace("route", data(Origin));
			waitForSync(1);
			int sizeOrg = androiddriver.findElements(By.xpath(locatorOrg)).size();

			String locatorDest = getPropertyValue(proppathhht, "breakdownhht_txt_route;xpath");
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
	 * @author A-9844
	 * Description : Handling ULD does not exist in the system warning message
	 * @throws IOException 
	 */
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
	 * @author A-8783
	 *  Desc - Verify pieces and weight
	 * @param pieces
	 * @param weight
	 * @param status
	 */
	public void verifyPiecesWeight(String pieces, String weight, String status) {
		try {
			String locatorStatus = getPropertyValue(proppathhht, "breakdownhht_txt_pcsWt;xpath");
			locatorStatus = locatorStatus.replace("status", data(status));
			waitForSync(1);
			
			String locatorPieces = locatorStatus.replace("number", pieces);
			waitForSync(1);
			
			int sizePieces = androiddriver.findElements(By.xpath(locatorPieces)).size();

			String locatorWeight = locatorStatus.replace("number", weight);
			waitForSync(1);
			int sizeWeight = androiddriver.findElements(By.xpath(locatorWeight)).size();

			if (sizePieces > 0) {
				writeExtent("Pass", "Verified "+data(status)+" Pieces " + pieces + " in " + screenName);
			} else
				writeExtent("Fail", "Failed to verify "+data(status)+" Pieces " + pieces + " in " + screenName);

			if (sizeWeight > 0) {
				writeExtent("Pass", "Verified "+data(status)+" Weight " + weight + " in " + screenName);
			} else
				writeExtent("Fail", "Failed to verify "+data(status)+" Weight " + weight + " in " + screenName);

		} catch (Exception e) {
			writeExtent("Fail", "Failed to verify the "+data(status)+" Pieces and Weight on " + screenName);

		}
	}
	/**
	 * @author A-8783
	 * @param SCC
	 * @throws IOException
	 */
	 public void verifySCC(String SCC) throws IOException
     {
                                   
		 String expSCC1=data(SCC)+" + 2";  
			String expSCC2=data(SCC)+" + 3";     
			String expSCC3="NSC"+" + 2";  
			String expSCC4="NSC"+" + 3";

			String actualSCC=getTextAndroid("breakdownhht_txt_scc;xpath", proppathhht, "SCC", screenName);
			
			
			if(actualSCC.equals(expSCC1) || actualSCC.equals(expSCC2)||actualSCC.equals(expSCC3) || actualSCC.equals(expSCC4))
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
	 * @author A-8783
	 * Desc - Verify split indicator is displayed based on the split type
	 * @param split
	 * @param fullAwbNo
	 * @throws IOException
	 */
	public void verifySplitIndicator(String split, String fullAwbNo) throws IOException {
		String indicator = "";
		String prefix= data(fullAwbNo).split("-")[0];
		String awb= data(fullAwbNo).split("-")[1];
		if(split.equals("Uld")) {
			indicator = "*";
		}
		else if(split.equals("Flight")) {
			indicator="~";
		}

		String expAWB=prefix+ " - " +awb+" ";
		String expIndicator= expAWB+indicator;
		System.out.println(expIndicator);

		//Modified as double click is required to select the awb using arrow

		clickListAwbArrow();

		String screenXpath = getPropertyValue(proppathhht,"breakdownhht_lblSplitIndicator;xpath").replace("awbno",expAWB);

		String actIndicator = androiddriver.findElement(By.xpath(screenXpath)).getText();

		System.out.println(actIndicator);

		if(actIndicator.equals(expIndicator))
			writeExtent("Pass","Sucessfully verified split indicator as "+indicator+" in "+screenName);
		else
			writeExtent("Fail","Could not verify split indicator as "+indicator+" in "+screenName);

	}

	public void selectSplitAWB(String fullAwbNo) throws IOException{
	      
		try{

			String screenXpath = getPropertyValue(proppathhht,"breakdownhht_selectAwb;xpath").replace("*",data(fullAwbNo));
			System.out.println(screenXpath);
			androiddriver.findElement(By.xpath(screenXpath)).click();
			waitForSync(8);
			writeExtent("Pass", "Sucessfully selected"+ data(fullAwbNo) + " on "+screenName);



		}catch(Exception e){
			writeExtent("Fail", "Failed to select AWB on "+screenName);
		}



	}
	/**
	 * Desc : Saving breakdown details 
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSaveAfterBreakdown() throws AWTException, InterruptedException, IOException
	{
		waitForSync(5);
		enterLocationIFNotDisplayed();
		clickActionInHHT("breakdownhht_btn_Save;xpath",proppathhht,"Save",screenName);	
		String locator1=getPropertyValue(proppathhht, "btn_airside;xpath");
		if(androiddriver.findElements(By.xpath(locator1)).size()==1)
			clickAirSide();
		waitForSync(12); 
		verifyHHTBreakdownSaveDetails(screenName);
	}
	/**
	 * @author A-9175
       * Desc : Save Details After Breakdown
	 */
	public void saveDetailsAfterBreakdown() throws AWTException, InterruptedException, IOException
	{
		waitForSync(5);
		enterLocationIFNotDisplayed();
		clickActionInHHT("breakdownhht_btn_Save;xpath",proppathhht,"Save",screenName);	
		String locator1=getPropertyValue(proppathhht, "btn_airside;xpath");
		if(androiddriver.findElements(By.xpath(locator1)).size()==1)
			clickAirSide();
		waitForSync(12); 
		verifyHHTSaveDetails(screenName);
	}


	/**
	 * @author A-10690
	 * @Desc To click on Done button and verify shipment added to the Uld successfully
	 * @throws IOException
	 */
	public void clickAirSide() throws IOException{	  

		clickActionInHHT("btn_airside;xpath",proppathhht,"selected AirSide","Airside button");
		waitForSync(5);

	}

	/**
	 * @author A-10690
	 * @Desc To click on Done button and verify shipment added to the Uld successfully
	 * @throws IOException
	 */
	public void clickPublicSide() throws IOException{	  
		
		clickActionInHHT("btn_publicside;xpath",proppathhht,"selected PublicSide","Publicside button");
		waitForSync(5);

	}
	public void saveWithoutLocation() throws AWTException, InterruptedException, IOException
	{
		waitForSync(5);
		clickActionInHHT("breakdownhht_btn_Save;xpath",proppathhht,"Save",screenName);	
		waitForSync(3); 
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
     	  
     	   String locatorValue=getPropertyValue(proppathhht, "breakdownhht_btn_updateSCCPcs;xpath");
	           locatorValue=locatorValue.replace("SCC", SCC);  
	           
	           int size;
		          
	           do{
	        	   
	        	   androiddriver.findElement(By.xpath(locatorValue)).click();
	        	   waitForSync(3);
	        	   size=getSizeOfMobileElement("breakdownhht_inbx_splitPcs;accessibilityId",proppathhht);
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
    /**@author A-10328
	 * Description- To verify SCC field is enabled or disabled
 * @throws IOException
	 */


public void verifySCCField() throws IOException

	

{

waitForSync(2);
clickActionInHHT("breakdownhht_selectScc;xpath",proppathhht,"SCC icon",screenName);

try


{
	int size = getSizeOfMobileElement("breakdownhht_btn_sccsOk;xpath", proppathhht);
	if (size == 1)

	{
	writeExtent("Pass", "Verified SCC Field is enabled "+screenName);
	clickBack("Select Item");
	}
	else
	{
	captureScreenShot("Android");
	writeExtent("Fail", "Failed to verify SCC Field is disabled "+screenName);
	}

	}
	catch(Exception e)
	{
	}
	}

/**
 * To select  SCCs only if the scc field is available in breakdown hht screen
 * @param sccs
 * @throws IOException
 */
public void selectSCC(String sccs[]) throws IOException{
	
	waitForSync(3);
	//String selectSCC = getPropertyValue(proppathhht,"breakdownhht_selectScc;xpath");
  // if( androiddriver.findElements(By.xpath(selectSCC)).size()==1)
   //{
	clickActionInHHT("breakdownhht_selectScc;xpath",proppathhht,"SCC icon",screenName);
	waitForSync(7);
	
	try{
	for(int i=0;i<sccs.length;i++){
	String screenXpath = getPropertyValue(proppathhht,"breakdownhht_sccs;xpath").replace("*",sccs[i]);
    androiddriver.findElement(By.xpath(screenXpath)).click();
	waitForSync(7);
	writeExtent("Pass", "Sucessfully selected "+ sccs[i]+ " on "+screenName);
	}
	}catch(Exception e){
		writeExtent("Fail", "Failed to select SCC on"+screenName);
	//}
	
	
	clickActionInHHT("breakdownhht_btn_sccsOk;xpath",proppathhht,"OK",screenName);
	waitForSync(3);

}
}


/**@author A-10328
* To verify and close the error message
* @param errorMessage
* @throws IOException
*/

public void verifyErrorMessage(String errorMessage) throws IOException 


{
	String locatorcloseBtn=getPropertyValue(proppathhht, "breakdown_closeerrormsg;xpath");

	locatorcloseBtn=locatorcloseBtn.replace("*", data(errorMessage));
	String locatorValue=getPropertyValue(proppathhht, "breakdown_txt_errorMessage;xpath");
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
	 * Desc To click on Arrow icon to list the AWB
	 * @throws IOException
	 */
	
public void clickListAwbArrow() throws IOException{

	waitForSync(3);
	scrollMobileDevice("Received");
	waitTillMobileElementDisplay(proppathhht,"breakdownhht_arrowIcon;xpath","xpath",10);
	try
	{
		clickActionInHHT("breakdownhht_arrowIcon;xpath",proppathhht,"Arrow Icon",screenName);    
		waitForSync(2);
		String arrowicon = getPropertyValue(proppathhht,"breakdownhht_arrowIcon;xpath");

		while( androiddriver.findElements(By.xpath(arrowicon)).size()==1)
		{
			clickActionInHHT("breakdownhht_arrowIcon;xpath",proppathhht,"Arrow Icon",screenName); 	
			waitForSync(3);
		}
	}catch(Exception e)
	{
		writeExtent("Fail","could not click the awb icon  in "+screenName);
	}
		

		
	
	}
	/**
	 * To select a particular AWB
	 * @param awb
	 * @throws IOException
	 */
	
	public void selectAwb(String fullAwbNo) throws IOException{
	
		
		String prefix= data(fullAwbNo).split("-")[0];
		String awb= data(fullAwbNo).split("-")[1];
		
		String expAWB=prefix+ " - " +awb+" ";
	      
			try{
				
				waitForSync(3);
				scrollMobileDevice("Received");
				waitTillMobileElementDisplay(proppathhht,"breakdownhht_arrowIcon;xpath","xpath",10);

				clickActionInHHT("breakdownhht_arrowIcon;xpath",proppathhht,"Arrow Icon",screenName);    
				waitForSync(2);
				String arrowicon = getPropertyValue(proppathhht,"breakdownhht_arrowIcon;xpath");
				
				while( androiddriver.findElements(By.xpath(arrowicon)).size()==1)
				{
					clickActionInHHT("breakdownhht_arrowIcon;xpath",proppathhht,"Arrow Icon",screenName); 	
					waitForSync(3);
				}

				String screenXpath = getPropertyValue(proppathhht,"breakdownhht_selectAwb;xpath").replace("*",expAWB);
				System.out.println(screenXpath);
				waitForSync(2);
				androiddriver.findElement(By.xpath(screenXpath)).click();
				waitForSync(7);
				writeExtent("Pass", "Sucessfully selected"+ awb + " on "+screenName);

				

			}catch(Exception e){
				writeExtent("Fail", "Failed to select AWB on "+screenName);
			}
		}

	/**
     * @author A-8783       
     * Description : Verify scc
     * @throws IOException 
      */
     public void verifyMultipleSCC(String SCC) throws IOException
     {

    	    scrollInMobileDevice(SCC);
			String locator=getPropertyValue(proppathhht, "breakdownhht_txt_sccs;xpath");
			locator = locator.replace("SCC", SCC);
			String actualSCC=androiddriver.findElement(By.xpath(locator)).getText();  
			
			if(actualSCC.equals(SCC))
			{
				writeExtent("Pass", "Verified SCC "+SCC+" in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Failed to verify SCC for "+SCC+" in "+screenName);
			}                             
		}


	/**
	 * To select multiple SCCs
	 * @param sccs
	 * @throws IOException
	 */
	public void selectMultipleSCC(String sccs[]) throws IOException{
		

		  // Commented as part of auto selection of SCCs
				/***waitForSync(3);
				swipeAndroidScreen();
				String selectSCC = getPropertyValue(proppathhht,"breakdownhht_selectScc;xpath");
				if( androiddriver.findElements(By.xpath(selectSCC)).size()==1)
				{
					clickActionInHHT("breakdownhht_selectScc;xpath",proppathhht,"SCC icon",screenName);
					waitForSync(7);


					clickActionInHHT("breakdownhht_selectScc;xpath",proppathhht,"SCC icon",screenName);
					waitForSync(7);
					try{
						for(int i=0;i<sccs.length;i++){
							String screenXpath = getPropertyValue(proppathhht,"breakdownhht_sccs;xpath").replace("*",sccs[i]);
							androiddriver.findElement(By.xpath(screenXpath)).click();
							waitForSync(7);
							writeExtent("Pass", "Sucessfully selected "+ sccs[i]+ " on "+screenName);


						}
						clickActionInHHT("breakdownhht_btn_sccsOk;xpath",proppathhht,"OK",screenName);

					}catch(Exception e){
						writeExtent("Fail", "Failed to select SCC on"+screenName);
					}

					clickActionInHHT("breakdownhht_btn_sccsOk;xpath",proppathhht,"OK",screenName);
					waitForSync(3);

				}***/

	
	}
	/**
	 * Desc : Capture CheckSheet at Breakdown for pallets
	 * @author A-9844
	 * @throws IOException
	 * @throws InterruptedException 
	 */


	public void captureCheckSheetBDNCPLT() throws IOException, InterruptedException
	{

		clickActionInHHT("breakdownhht_txt_checksheet;xpath",proppathhht,"Breakdown Complete checksheet button",screenName);	
		waitForSync(4); 

		List<MobileElement>questions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_quest;xpath")));
		System.out.println(questions.size());

		//Handling Obligatory Questions - No
		String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestionsBreakdown");		
		for(MobileElement quest:questions)
		{
			String actText=quest.getText().replace("*","");
			if (ObgQuest.contains(actText))
			{
				String locNo=getPropertyValue(proppathhht, "gahht_obligatoryquestNo;xpath").replace("*", actText);	
				scrollMobileDevice(actText);
				androiddriver.findElement(By.xpath(locNo)).click(); 

			}

			else{

				String locYes=getPropertyValue(proppathhht, "breakdownhht_obligatoryquestYes;xpath").replace("*", actText);
				scrollMobileDevice(actText);
				androiddriver.findElement(By.xpath(locYes)).click(); 

			}
		}

		//Click OK after capturing each Checksheet template
		clickActionInHHT("buildUphht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);
		waitForSync(2);	
		clickSaveCaptureChecksheet();

	}


	/**
	 * @Desc : Clicking Breakdown button 
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickBreakdownCompleteBtn() throws AWTException, InterruptedException, IOException
	{
		waitForSync(5);
		clickActionInHHT("breakdownhht_btn_breakdownComplete;xpath",proppathhht,"Breakdown Complete",screenName);	
		waitTillMobileElementDisplay(proppathhht,"breakdownhht_txt_checksheet;xpath","xpath");

		int size = getSizeOfMobileElement("breakdownhht_txt_checksheet;xpath", proppathhht); 
		if (size == 1) {
			captureCheckSheetBDNCPLT();
		}
	}
	/**
	 * Desc : Saving Operation and select bdn instruction
	 * @author A-8783
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws AWTException 
	 
	 */
	public void clickSave(String instruction) throws IOException, AWTException, InterruptedException 
	{
		waitForSync(5);
		enterLocationIFNotDisplayed();
		clickActionInHHT("breakdownhht_btn_Save;xpath",proppathhht,"Save",screenName);
		waitForSync(5);
		String locator1=getPropertyValue(proppathhht, "btn_dirPartialThru;xpath");
		locator1=locator1.replace("*", data(instruction));
		if(androiddriver.findElements(By.xpath(locator1)).size()==1)
			clickBdnInstruction(instruction);
		waitForSync(12); 
		verifyHHTSaveDetails(screenName);
	}
	
	 /**
	  * @author A-8783
	  * Desc - Select Bdn instruction
	  * @throws IOException
	  */
	public void clickBdnInstruction(String instruction) throws IOException{	  

		String locator1=getPropertyValue(proppathhht, "btn_dirPartialThru;xpath");
		locator1=locator1.replace("*", data(instruction));
		androiddriver.findElement(By.xpath(locator1)).click();
		waitForSync(5);

	}

	/**
	 * To verify split shipment indicator 
	 * @param actawb
	 */
	
	public void verifySplitShipment(String actawb){
		
		try
		{
		String locatorValue=getPropertyValue(proppathhht, "breakdownhht_splitshipment;xpath").replace("*", data("Origin"));
		System.out.println(locatorValue);
		waitForSync(5);
		String Element=androiddriver.findElement(By.xpath(locatorValue)).getText();
	
		System.out.println(Element);
		
		if(Element.equals(data(actawb)))
		{
			
			writeExtent("Pass", "Sucessfully verified " +data(actawb)+" as split shipment"+screenName);
		}
		else
		{
			writeExtent("Fail", "Failed to verify " +data(actawb)+" as split shipment"+screenName);
		}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Shipment is not verified as split shipment"+screenName);
		}
	}
	/**
	 * To verify AWB list according to SCC ranking
	 * @param sccRank
	 */
	public void verifySccRanking(String sccRank[]){
		
		try{
			  String locator=getPropertyValue(proppathhht, "breakdownhht_sccrank;xpath").replace("*", data("Origin"));
			  
				 
			  List <MobileElement> elements=androiddriver.findElements(By.xpath(locator));
			  System.out.println(elements.size());
				 
              if(elements.size()==0)
			  {
				  writeExtent("Fail","Failed to retrieve scc ranking on "+screenName);
			  }

			  for(int i=0;i<elements.size();i++){
				  System.out.println(elements.get(i).getText().split(" ")[0]);
				  
				  if(elements.get(i).getText().split(" ")[0].equals(sccRank[i]))
					  writeExtent("Pass", "Sucessfully verified " +sccRank[i]+" in the AWB list in priority order " +(i+1) + screenName);
					 
		        
				 
		          else
		       	   writeExtent("Fail", "Failed to verify " +sccRank[i]+" in the AWB list in priority order " +(i+1) + screenName);   

				  
			  }
		}
			  catch(Exception e){
					
				  writeExtent("Fail"," SCC ranking is not maintained "+screenName);
					
				}
				
				
			}

	

	/**
	 * @author A-8783
	 * Desc - to check if instruction icon is present and click on it
	 * @param FullAwb
	 */
	public void clickInstructionIcon(String FullAwb) {
		String locatorValue = getPropertyValue(proppathhht,"breakdownhht_instructionIcon;xpath").replace("awb",data(FullAwb));
		
		
		try {
			
			int size=androiddriver.findElements(By.xpath(locatorValue)).size();
		
			if(size==1)
			{
				androiddriver.findElement(By.xpath(locatorValue)).click();
				writeExtent("Pass", "The instruction icon is displayed and clicked in" + screenName + "screen");
					
			}

		}

		catch (Exception e) {
			writeExtent("Fail", "Could not click the instruction icon");
		}
	}
/**
 * 
 * @param remark
 * Desc : verift remarks
 */
public void verifyRemarks(String remark) {
           String locatorValue = getPropertyValue(proppathhht,"breakdownhht_remarks;xpath").replace("remark",data(remark));
		
		waitForSync(2);
		try {
			
			int size=androiddriver.findElements(By.xpath(locatorValue)).size();
		
			if(size==1)
			{
				writeExtent("Pass", "The remark is verified successfully");
					
			}

		}

		catch (Exception e) {
			writeExtent("Fail", "Could not verify the remarks");
		}
	}
/**
 * @author A-8783
 *
 * @throws IOException
 */
public void clickClose() throws IOException {
	clickActionInHHT("breakdownhht_close;xpath",proppathhht,"Close confirmation message",screenName);	
}

	/**
	 * @Description : click save 
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void save() throws AWTException, InterruptedException, IOException
	{
		waitForSync(5);
		enterLocationIFNotDisplayed();
		clickActionInHHT("breakdownhht_btn_Save;xpath",proppathhht,"Save",screenName);	
		waitForSync(3); 
	}

	/* @Description : Update shipment pcs
	 * @author A-9175
	 * @param pcs
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void updateSplitPcsandWgt(String pcs) throws AWTException, InterruptedException, IOException
	{
			clearValueInHHT("breakdownhht_inbx_splitPcs;accessibilityId",proppathhht,"Split Pieces",screenName);
			enterValueInHHT("breakdownhht_inbx_splitPcs;accessibilityId",proppathhht,pcs,"Split Pieces",screenName);
			waitForSync(2);
			clickActionInHHT("breakdownhht_btn_updatePcs;xpath",proppathhht,"Update",screenName);
			
	}

	/**
	 * Desc : Verifying alert and accept the alert
	 * @author A-9175
	 * @param expText
	 */
	public void verifyAlertAndAccept(String expText)
	{
		String statusflag="Fail";
		try
		{
			
			String locatorValue=getPropertyValue(proppathhht, "breakdownhht_txtAlert;xpath");
			locatorValue=locatorValue.replace("alertText", expText);
			String actualAlertText=androiddriver.findElement(By.xpath(locatorValue)).getText();
			waitForSync(5);
			if(actualAlertText.equals(expText))
			{
				statusflag="Pass";
			}
			
			
			waitForSync(5);
			clickActionInHHT("breakdownhht_btn_YES;xpath",proppathhht,"Yes",screenName);
			writeExtent(statusflag, "Expected alert text is : "+expText+ " Actual alert text is : "+actualAlertText+screenName);
			
		}
		catch(Exception e)
		{
			writeExtent(statusflag, "Expected alert : "+expText+" Not Found in "+screenName);
		}
	}
	/**
 	 * @Description : Selecting split SCCS information
 	 * @author A-9175
 	 * @param SCC
 	 * @throws IOException
 	 */
 	
 	 public void selectSplitSCCValue(String SCC) throws IOException
      {            waitForSync(5);
      try
      {
    	  
    	  
    	  if(!SCC.contains("+"))
          	scrollMobileDevice(SCC);
          
         String SCCval=SCC.substring(0,3);  
        	  
           String locatorValue=getPropertyValue(proppathhht, "breakdownhht_btn_split_scc;xpath");
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
           waitForSync(5);
           writeExtent("Pass", "Successfully selected split SCC value "+SCC+" in "+screenName);
      }
      catch(Exception e)
      {
          writeExtent("Fail", "Couldn't select split SCC value "+SCC+" in "+screenName);
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
			clickActionInHHT("breakdownhht_checksheetSave;xpath",proppathhht," Save Checksheet ",screenName);	
			waitForSync(5);
			
		}
	 
	

	/**
 * @author A-6260
 * Desc.. click breakdown complete
 * @throws AWTException
 * @throws InterruptedException
 * @throws IOException
 */
	public void clickBreakdownCompleteAndVerifyMessage() throws AWTException, InterruptedException, IOException
	{
		try {
			waitForSync(3);
			clickActionInHHT("breakdownhht_btn_breakdownComplete;xpath",proppathhht,"Breakdown Complete",screenName);	
			String screenStatus="Breakdown completed successfully";
			String locatorValue=getPropertyValue(proppathhht, "breakdownhht_btn_breakdownCompletedSuccessfully;xpath");
			waitForSync(2);
			String actualAlertTextElemenet=androiddriver.findElement(By.xpath(locatorValue)).getText();
			if(actualAlertTextElemenet.equals(screenStatus))
			{

				writeExtent("Pass", "Breakdown completed successfully is displaying in "+screenName);
			}
			else
			{
				writeExtent("Fail", "Breakdown completed successfully is not displaying in "+screenName); 
			}
		} 
		catch(Exception e)
		{
			writeExtent("Fail", "Couldnt verify breakdown complete message in "+screenName);
		}
	}
	/**
     * Desc : Verifying alert
     * @author A-9478
     * @param expText
     */
     public void verifyAlert(String expText) throws IOException
     {
           String statusflag="Fail";
           try
           {
        	    waitForSync(5); 
                 String locatorValue=getPropertyValue(proppathhht, "breakdownhht_txtAlert;xpath");
                 locatorValue=locatorValue.replace("alertText", expText);
                 String actualAlertText=androiddriver.findElement(By.xpath(locatorValue)).getText();
                 if(actualAlertText.equals(expText))
                 {
                       statusflag="Pass";
                 }
                
                 writeExtent(statusflag, "Expected alert text is : "+expText+ " Actual alert text is : "+actualAlertText+screenName);
                 
           }
           catch(Exception e)
           {
                 captureScreenShot("Android");
                 writeExtent(statusflag, "Expected alert : "+expText+" Not Found in "+screenName);
           }
     }
     /**
      * 
      * @param flightNumber
      * @throws AWTException
      * @throws InterruptedException
      */
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
	 * Desc : Updating Flight details for \\Found ULD Scenario\\
	 * @author A-9175
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void updateFlightDetails(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
	{
		
		waitForSync(5);
		enterValueInHHT("breakdownhht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
		waitForSync(2);
		clickActionInHHT("breakdownhht_inbx_flightNumber;accessibilityId",proppathhht,"Flight Number",screenName);
		waitForSync(2);
		enterFlightNumber(data(flightNo));
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

		waitTillMobileElementDisplay(proppathhht,"breakdownhht_btn_next;xpath","xpath");
		if(flightDate.equals("currentDay"))
		{
			clickActionInHHT("breakdownhht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
		}

		else if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("breakdownhht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
			waitForSync(2);
		}
		
		
		waitTillMobileElementDisplay(proppathhht,"breakdownhht_btn_next;xpath","xpath");
		clickActionInHHT("breakdownhht_btn_next;xpath",proppathhht,"Next",screenName);
		waitForSync(10);
	}

/**
	 * Desc : Adding Pieces 
	 * @author A-9175
	 * @param pcs
	 * @throws AWTException
	 * @throws InterruptedException
 * @throws IOException 
	 */
	public void addPcs(String pcs) throws AWTException, InterruptedException, IOException
	{
		swipeAndroidScreen();
		scrollInMobileDevice("Pieces");
		enterValueInHHT("breakdownhht_inbx_pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		waitForSync(5);
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
	 * @author A-9175
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering ULD/BULK number in Check sheet hht Screen
	 * @throws IOException 
	 */
	public void enterValue(String value) throws AWTException, InterruptedException, IOException
	{
		enterValueInHHT("breakdownhht_inbx_Awb;accessibilityId",proppathhht,data(value),"List Value",screenName);
		waitForSync(6);
		handleNewULDWarning();
	}
	/**
	 * Desc : Adding AWB number to ULD
	 * @author A-9175
	 * @param awbNo
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void addAWB(String awbNo) throws AWTException, InterruptedException, IOException
	{
		String locatorValue=getPropertyValue(proppathhht, "breakdownhht_awbNo;accessibilityId");
		while(androiddriver.findElementsByAccessibilityId(locatorValue).size()!=1)
			waitForSync(2);
			enterValueInHHT("breakdownhht_awbNo;accessibilityId",proppathhht,data(awbNo),"AWB Number",screenName);
			waitForSync(6);
	}
	
	/**
	 * Desc : Adding Location
	 * @author A-9175
	 * @param loc
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void addLocation(String loc) throws AWTException, InterruptedException, IOException
	{
			waitForSync(5);
			clickActionInHHT("breakdownhht_locationType;xpath",proppathhht,"Location",screenName);
			waitForSync(5);
			
			//Fetching the locator value from property file
			String locatorValue=getPropertyValue(proppathhht, "breakdownhht_locationValue;xpath");
			locatorValue=locatorValue.replace("Location", loc);
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(5);
	}
	/**
	 * Desc : Adding Location
	 * @author A-9175
	 * @param loc
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void addLocationDetails(String loc) throws AWTException, InterruptedException, IOException
	{
			waitForSync(5);
			clickActionInHHT("breakdownhht_locationType;xpath",proppathhht,"Location",screenName);
			waitForSync(5);
			
			//Fetching the locator value from property file
			String locatorValue=getPropertyValue(proppathhht, "breakdownhht_locationValue;xpath");
			locatorValue=locatorValue.replace("Location", data(loc));
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(5);
	}
	
	/**
	 * Desc : Adding Pieces and Weight
	 * @author A-9175
	 * @param pcs
	 * @param Wgt
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void addPcsWgt(String pcs,String Wgt) throws AWTException, InterruptedException, IOException
	{
		waitForSync(1);
		swipeAndroidScreen();
		scrollInMobileDevice("Pieces");
		enterValueInHHT("breakdownhht_inbx_pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		waitForSync(5);
		enterValueInHHT("breakdownhht_inbx_wgt;accessibilityId",proppathhht,data(Wgt),"Weight",screenName);
		waitForSync(5);
	}
	/**
	 * @author A-10690
	 * @param BULK
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering BULK  in breakdownhhtscreen
	 * @throws IOException 
	 */
	public void enterBulkValue(String value) throws AWTException, InterruptedException, IOException
	{
		enterValueInHHT("breakdownhht_inbx_Awb;accessibilityId",proppathhht,data(value),"List Value",screenName);
		waitForSync(4);
		clickActionInHHT("breakdownhht_btn_next2;xpath",proppathhht,"Next",screenName);
		
	}
	/**
	 * @author A-9175
	 * @Desc : To capture storage Position
	 * @throws IOException
	 */
	public void enterStoragePosition() throws IOException {
		
		// Scroll down
		scrollInMobileDevice("Storage Position");
		clickActionInHHT("breakdownhht_btn_storagePosDetails;xpath", proppathhht, "Select Storage Position", screenName);
		waitTillMobileElementDisplay(proppathhht, "breakdownhht_btn_storagePosDetails;xpath", "xpath", 20);

		map.put("storagePOS", WebFunctions.getPropertyValue(toproppath, "Storage_Position_CDG"));
		String locatorStoragePOSValue=getPropertyValue(proppathhht, "breakdownhht_btn_SelectStoragePOSValue;xpath");
		locatorStoragePOSValue=locatorStoragePOSValue.replace("POS", data("storagePOS"));
        androiddriver.findElement(By.xpath(locatorStoragePOSValue)).click();
        writeExtent("Pass", "Selected Storage Position as "+data("storagePOS")+" in Goods Acceptance hht screen");

	}

	/**
	 * Desc : Saving Operation
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws AWTException, InterruptedException, IOException
	{
		enterStoragePosition();
		enterLocationIFNotDisplayed();
		waitForSync(5);
		clickActionInHHT("breakdownhht_btn_Save;xpath",proppathhht,"Save",screenName);
		waitForSync(5);
		String locator1=getPropertyValue(proppathhht, "btn_airside;xpath");
		if(androiddriver.findElements(By.xpath(locator1)).size()==1)
			clickAirSide();
		waitForSync(12); 
		closePopup();
		/****verifyHHTSaveDetails(screenName);****/
	}
	
	/**@author A-10328
	* Description - Close the popup on breakdown save
* @throws IOException
*/
public void closePopup() throws IOException
{

String locator1=getPropertyValue(proppathhht, "breakdownhht_closepopup;xpath");
if(androiddriver.findElements(By.xpath(locator1)).size()==1)
clickActionInHHT("breakdownhht_close;xpath",proppathhht,"close Button",screenName);
waitForSync(3);
}

/**
	 * @author A-9844
	 * Description : for fetching the location value from the poperties file
	 * @throws IOException 
	 */
	public void enterLocationIFNotDisplayed() throws AWTException, InterruptedException, IOException
	{

		scrollInMobileDevice("Location");
		//String locationDisplayed=getPropertyValue(proppathhht, "breakdownhht_locationText;xpath");
		//Commenting this as DEFBDN is not a valid Breakdown location and it is necessary to select a valid breakdown location	//if(androiddriver.findElements(By.xpath(locationDisplayed)).size()==1)	
		getAndEnterLocation();




	}
	/**
	 * @author A-9844
	 * Description : for fetching the location value from the poperties file
	 * @throws IOException 
	 */
	public void getAndEnterLocation() throws AWTException, InterruptedException, IOException
	{
		if(data("LoggedStation").equals("CDG")){
			map.put("Location", WebFunctions.getPropertyValue(locationpath, "CDG_BreakdownLocation"));
			
		}

		else if(data("LoggedStation").equals("AMS")){
			map.put("Location", WebFunctions.getPropertyValue(locationpath, "AMS_BreakdownLocation"));
			
		}

		else if(data("LoggedStation").equals("IAD")){

			map.put("Location", WebFunctions.getPropertyValue(locationpath, "IAD_BreakdownLocation"));
			
		}

		enterLocation("Location");
	}


	/**
	 * Desc : Removing entered AWB number
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickCloseAWBButton() throws AWTException, InterruptedException, IOException
	{
			waitForSync(5);
			clickActionInHHT("breakdownhht_btnAwbClose;xpath",proppathhht,"AWB Close",screenName);	
			waitForSync(12); 
	}
	
	/**
	 * Desc : Clicking More Options Button
	 *@author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickMoreOptions() throws AWTException, InterruptedException, IOException
	{
			waitForSync(5);
			clickActionInHHT("breakdownhht_btn_moreOptions;xpath",proppathhht,"Breakdown Options",screenName);	
			waitForSync(12); 
	}
	
	/**
	 * Desc : Clicking Breakdown Complete button
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickBreakdownComplete() throws AWTException, InterruptedException, IOException
	{
			waitForSync(5);
			clickActionInHHT("breakdownhht_btn_breakdownComplete;xpath",proppathhht,"Breakdown Complete",screenName);	
			waitForSync(12); 
            verifyBreakdownCompletedSuccessfullyPopUp();
			clickActionInHHT("breakdownhht_btn_msgConfirmation;xpath",proppathhht,"Close confirmation message",screenName);	
	}
	/**
     * @author A-9478
     * Desc : Verifying Breakdown completed successfully message in pop up
     */
     public void verifyBreakdownCompletedSuccessfullyPopUp()
     {
           try
           {
         
           String screenStatus="Breakdown completed successfully";
           String locatorValue=getPropertyValue(proppathhht, "breakdownhht_btn_breakdownCompletedSuccessfully;xpath");
           waitForSync(2);
           String actualAlertTextElemenet=androiddriver.findElement(By.xpath(locatorValue)).getText();
           if(actualAlertTextElemenet.equals(screenStatus))
           {
               
                 writeExtent("Pass", "Breakdown completed successfully is displaying in "+screenName);
           }
           else
           {
        	   writeExtent("Fail", "Breakdown completed successfully is not displaying in "+screenName); 
           }
           }
           catch(Exception e)
           {
                 writeExtent("Fail", "Breakdown completed successfully is not displaying in "+screenName);
           }
     }

     /**
      * Desc : Saving Operation
      * @author A-9478
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException 
       */
      public void clickSaveButton() throws AWTException, InterruptedException, IOException
      {
    	  enterLocationIFNotDisplayed();
  		clickActionInHHT("breakdownhht_btn_Save;xpath",proppathhht,"Save",screenName);    
  		waitForSync(5);
      }

	/**
	 * @author A-9175
	 * Desc : Verifying Message for Breakdown Complete
	 */
	public void verifyBreakdownCompletedStatusPopUp()
	{
		try
		{
			
	String screenStatus="Breakdown is complete for the  ULD. Do you want to continue";
	String screenStatus1="Breakdown is complete for the  ULD";
	String locatorValue=getPropertyValue(proppathhht, "breakdownhht_txtalertforBreakdownComplete;xpath");
	waitForSync(5);
	String actualAlertTextElement=androiddriver.findElement(By.xpath(locatorValue)).getText();
	if(actualAlertTextElement.equals(screenStatus) || actualAlertTextElement.equals(screenStatus1) )
	{
				
		writeExtent("Pass", "Breakdown status is Completed in"+screenName);
	}
	else
	{
	writeExtent("Fail", "Breakdown status is not identified as Completed in"+screenName);
	}
	}
	catch(Exception e)
	{
	writeExtent("Fail", "Breakdown status is not identified as Completed in"+screenName);
	}

}
}
