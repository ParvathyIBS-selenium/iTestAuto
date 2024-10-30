package screens;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class DeliveryHHT extends CustomFunctions {
	
	String sheetName = "DeliveryHHT";
	String screenName = "Delivery HHT";
	

	public DeliveryHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the Delivery HHT screen
	 * @throws IOException 
	 */
	public void invokeDeliveryHHTScreen() throws InterruptedException, AWTException, IOException {
	
		scrollInMobileDevice("Delivery");
		clickActionInHHT("deliveryhht_menu;xpath",proppathhht,"Delivery menu",screenName);
		waitForSync(5);
	}
	/**
	 * @author A-10690
	 * Desc: Enter HAWB
	 * @throws IOException 
	 */
	public void enterHAWB(String HAWB) throws IOException
	{
                    waitForSync(2);
		enterValueInHHT("deliveryhht_txt_HAWB;xpath",proppathhht,data(HAWB),"Vehicle Info",screenName);
		waitForSync(4);
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
            clickActionInHHT("deliveryhht_checksheetSave;xpath",proppathhht," Save Checksheet ",screenName);    
           waitForSync(5);
       }
	/**
	 * @author A-10690
	 * Description : select HAWB from the list
	 * @param AWBnumber
	 * @param Full awbnumber
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException 
	 */
	public void selectHAWBForDelivery(String AWBNo,String FullAWBNo) throws InterruptedException, AWTException, IOException
	{

		/***************************************************************************/

		waitForSync(4);
		String locatorPOU=getPropertyValue(proppathhht, "deliveryhht_txt_selectmaster;xpath");
        int elesize=androiddriver.findElements(By.xpath(locatorPOU)).size();
		if(elesize==1)
		{
			scrollInMobileDevice(data(AWBNo));
			String locator=getPropertyValue(proppathhht, "deliveryhht_btn_selectHAWB;xpath");
	 		locator=locator.replace("*", data(FullAWBNo));
	 		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(4);
		}
	}
	/**
 	 * @author A-9844
 	 * Desc: verify delivered status against awbs
 	 * @throws IOException 
 	 */
 	public void verifyDeliveredStatusAgainstAWB(String awbNo,String expStatus) throws IOException
 	{
 		String locator=getPropertyValue(proppathhht, "deliveryhht_deliveredStatusAgainstAWB;xpath");
 		locator=locator.replace("*", data(awbNo));
 		waitForSync(3);
		String Actstatus=androiddriver.findElement(By.xpath(locator)).getText();
                waitForSync(3);
		if(Actstatus.equals(data(expStatus)))
		writeExtent("Pass","Sucessfully found :"+data(expStatus)+" in "+screenName);
		else
		writeExtent("Fail","Couldnt find "+data(expStatus)+" in "+screenName);

 	
 	}
 	/**
 	 * @author A-9844
 	 * Desc: verify awb number is present
 	 * @throws IOException 
 	 */
 	public void verifyAWBNumber(String awbNo) throws IOException
 	{
 		String locator=getPropertyValue(proppathhht, "deliveryhht_txt_AWBNumber;xpath");
 		locator=locator.replace("*", data(awbNo));

 		int eleSize=androiddriver.findElements(By.xpath(locator)).size();

 		if(eleSize>0)
 		{
 			writeExtent("Pass","Verified the awb "+data(awbNo)+" is present on "+screenName);

 		}
 		else
 		{
 			writeExtent("Fail"," Failed to verify the awb "+data(awbNo)+" is present on "+screenName);
 		}


 	}


 	/**
	 * @author A-8783 
	 * Desc - Verify origin and destination
	 * @param Origin
	 * @param Destination
	 */
	public void verifyOriginAndDestination(String Origin, String Destination) {
		try {
			String locatorOrg = getPropertyValue(proppathhht, "deliveryhht_txt_route;xpath");
			locatorOrg = locatorOrg.replace("route", data(Origin));
			waitForSync(1);
			int sizeOrg = androiddriver.findElements(By.xpath(locatorOrg)).size();

			String locatorDest = getPropertyValue(proppathhht, "deliveryhht_txt_route;xpath");
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
	 * @author A-8783
	 * Desc - Verify awb number
	 * @param carrierNumericCode
	 * @param awbNo
	 */
	public void verifyShipment(String carrierNumericCode, String awbNo) {
		String fullAwb = data(carrierNumericCode)+" - "+data(awbNo)+" ";
		String locator = getPropertyValue(proppathhht, "deliveryhht_txt_awbNumber;xpath");
		locator=locator.replace("awbNo", fullAwb);
		waitForSync(1);
		int size = androiddriver.findElements(By.xpath(locator)).size();
		
		if(size==1) {
			writeExtent("Pass", "Verified Awb number " + data(awbNo) + " in " + screenName);
		}
		else
			writeExtent("Fail", "Failed to verify Awb number " + data(awbNo) + " in " + screenName);
	}
 	/**
	 * @author A-9844
	 * Desc -  enter customs reference number if not auto populated
	 * @param customRefNo
	 */
	public void enterCustomsReferenceNumberIfNotAutopopulated(String customRefNo ) {
		try
		{
			waitForSync(8);
			String locatorValue=getPropertyValue(proppathhht, "deliveryhht_inbx_custRefNo;accessibilityId");
			String Actstatus=androiddriver.findElementByAccessibilityId(locatorValue).getText();
			System.out.println(Actstatus);
			map.put(customRefNo, Actstatus);
			waitForSync(3);
			if(Actstatus.contains("Enter Customs Ref. No")){
				enterValueInHHT("deliveryhht_inbx_custRefNo;accessibilityId",proppathhht,data(customRefNo),"Customs Reference Number",screenName);
				waitForSync(5);
				writeExtent("Pass","Customs ReferenceNumber is not auto populated . Entered  :"+data(customRefNo)+" as Customs Reference Number in "+screenName);
			}
			

		}
		catch(Exception e)
		{
			
		}	
	}
	/**
	 * @author A-8783
	 * Desc - Verify delivered pieces and weight
	 * @param expPcs
	 * @param expWt
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void verifyPiecesWeight(String expPcs, String expWt) throws IOException, InterruptedException {
		String actPcs = getTextAndroid("deliveryhht_txt_pcs;xpath", proppathhht, "Delivered Pieces", screenName);
		 verifyValueOnPage(actPcs, data(expPcs),"Verification of pieces", screenName, "Verification of Delivered pieces"); 
		 
		 String actWt = getTextAndroid("deliveryhht_txt_weight;xpath", proppathhht, "Delivered Weight", screenName);
		 verifyValueOnPage(actWt, data(expWt),"Verification of weight", screenName, "Verification of Delivered weight"); 
	}

	/**
	 * @author A-9478
	 * Desc : Enter Delivery Note Number
	 * @throws IOException 
	 */
	
	public void enterDeliveryNoteNumber(String DN) throws IOException
	{
		enterValueInHHT("deliveryhht_inbx_DeliveryNote;accessibilityId",proppathhht,data(DN),"Delivery Note Number",screenName);
		waitForSync(3); 
	}
	/**
	 * @author A-9844
	 * @param value
	 * Desc : Enter delivery location
	 * @throws IOException 
	 */
	
	public void enterDeliveryLocation(String value) throws IOException
	{
		
		clearValueInHHT("deliveryhht_inbx_LocationText;xpath",proppathhht,"Location",screenName);
		waitForSync(3);
		enterValueInHHT("deliveryhht_inbx_Location;xpath",proppathhht,data(value),"Location",screenName);
		waitForSync(5);
	}
	/**
	 * Desc: verify Select All button 
	 * @author A-9844
	 * @param expStatus
	 * @throws IOException
	 */
	public void verifySelectAllButton(String expText) throws IOException
	{
		
		try
		{
			waitForSync(4);
			String locatorValue=getPropertyValue(proppathhht, "deliveryhht_btn_selectAll;xpath");
			String Acttext=androiddriver.findElement(By.xpath(locatorValue)).getText();
			if(Acttext.equals(data(expText)))
			writeExtent("Pass","Sucessfully found :"+data(expText)+" in "+screenName);
			else
			writeExtent("Fail","Couldnt find "+data(expText)+" in "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail","Element not found in "+screenName);
		}			
		
	}


	/**
	 * @author A-9844
	 * Desc : Click Deliver button
	 * @throws IOException 
	 */
	
	public void clickDeliverButton() throws IOException
	{
		for(int i=1;i<=2;i++)
		{
			clickActionInHHT("deliveryhht_btn_Deliver;xpath",proppathhht,"Deliver button",screenName);	
			waitForSync(5);

			int size=getSizeOfMobileElement("deliveryhht_lbl_AWBs;xpath",proppathhht);   
			int size2=getSizeOfMobileElement("deliveryhht_lbl_location;xpath",proppathhht);
			waitForSync(3); 
			System.out.println(size);
			System.out.println(size2);
			if((size>0) || (size2>0))

			{
				writeExtent("Pass","Sucessfully navigated to next page");
				break;
			}
		}	
	
	}

	/**
     * @author A-9478
     * Desc : Enter Delivery ID
     * @throws IOException 
      */
     
     public void enterDeliveryID(String DeliveryID) throws IOException
     {
    	 enterValueInHHT("deliveryhht_inbx_DeliveryID;accessibilityId",proppathhht,data(DeliveryID),"Delivery ID Number",screenName);
    	 waitForSync(3);
    	 clickActionInHHT("delivery_locationType;xpath",proppathhht,"Location",screenName);
    	 waitForSync(3);
    	 enterValueInHHT("delivery_locationType;xpath",proppathhht,"DEFDLV","Location",screenName);

           
           
     }
     /**
 	 * @author A-9844
 	 * Desc: verify checkbox against awbs
 	 * @throws IOException 
 	 */
 	public void verifyCheckBoxAgainstAWB(String awbNo) throws IOException
 	{
 		String locator=getPropertyValue(proppathhht, "deliveryhht_chkBoxAgainstAWB;xpath");
 		locator=locator.replace("*", data(awbNo));

 		int eleSize=androiddriver.findElements(By.xpath(locator)).size();

 		if(eleSize>0)
 		{
 			writeExtent("Pass","Verified check box against awb"+data(awbNo));

 		}
 		else
 		{
 			writeExtent("Fail"," Failed to verify check box against awb"+data(awbNo));
 		}


 	}
 	/**
     * @author A-8783
     * Desc -  Verify customs reference number
     * @param customRefNo
     */
    public void verifyCustomsReferenceNumber(String customRefNo ) {
    	 try
 		{
 			waitForSync(8);
 			String locatorValue=getPropertyValue(proppathhht, "deliveryhht_inbx_custRefNo;accessibilityId");
 			String Actstatus=androiddriver.findElementByAccessibilityId(locatorValue).getText();
 			if(Actstatus.equals(data(customRefNo)))
 			writeExtent("Pass","Sucessfully verified :"+data(customRefNo)+" in "+screenName);
 			else
 			writeExtent("Fail","Could not verify "+data(customRefNo)+" in "+screenName);
 		}
 		catch(Exception e)
 		{
 			writeExtent("Fail","Element not found in "+screenName);
 		}	
     }



/**
	 * @author A-9844
	 * Desc : click  checkbox against a particular AWBNO
	 * @throws IOException 
	 */

	public void clickSelectOptionIcon(String awbNo) throws IOException{

		try{
			String locator=getPropertyValue(proppathhht, "deliveryhht_chkBoxAgainstAWB;xpath");
			locator=locator.replace("*", data(awbNo));

			for(int i=1;i<=2;i++)
			{
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(4); 

				int size=getSizeOfMobileElement("deliveryhht_btn_Deliver;xpath",proppathhht);    
				System.out.println(size);
				waitForSync(2); 
				if(size>0)

				{
					writeExtent("Pass","Sucessfully navigated to next page");
					break;
				}
			}
			waitForSync(5); 
			writeExtent("Pass", "Clicked on checkbox against "+data(awbNo)+screenName);

		}catch (Exception e) {
			writeExtent("Fail", "Failed to clicked on checkbox against "+data(awbNo)+screenName);
		}
	}

	/**
	 * @author A-9478
	 * Desc : Click Pending button
	 * @throws IOException 
	 */

	public void clickPendingButton(String awbNo) throws IOException
	{

		try{
			String locator=getPropertyValue(proppathhht, "deliveryhht_pendingBtnAgainstAWB;xpath");
			locator=locator.replace("*", data(awbNo));

			for(int i=1;i<=2;i++)
			{
				androiddriver.findElement(By.xpath(locator)).click();
			}
			waitForSync(3); 
			writeExtent("Pass", "Clicked on Pending button against "+data(awbNo)+screenName);

		}catch (Exception e) {
			writeExtent("Fail", "Failed to click on Pending against "+data(awbNo)+screenName);
		}
	}


     /**
 	 * @author A-9844
 	 * @param value
 	 * Desc : Enter Token Number
 	 * @throws IOException 
 	 */
 	
 	public void enterTokenNumber(String value) throws IOException
 	{
 		enterValueInHHT("deliveryhht_inbx_Token;xpath",proppathhht,data(value),"Enter Token",screenName);
 		waitForSync(3); 
 	}
 	
     /**
 	 * @author A-9844
 	 * Desc : click select option
 	 * @throws IOException 
 	 */

 	public void clickSelectOptionIcon() throws IOException
 	{
 		for(int i=1;i<=2;i++)
 		{
 		clickActionInHHT("deliveryhht_btn_clickSelectOptionIcon;xpath",proppathhht,"select option",screenName);
 		}
 		waitForSync(7);
 		  
 	}
 	/**
	 * @author A-9844
	 * Desc : Click Add On Menu Option
	 * @throws IOException 
	 */

	public void clickAddOnMenu() throws IOException
	{


		clickActionInHHT("deliveryhht_btn_addOnMenu;xpath",proppathhht,"Add On Menu Option",screenName);			
		waitForSync(5);
	}
	/**
	 * @author A-9844
	 * Desc : verify and select Damage Capture option
	 * @throws IOException 
	 */

	public void verifyAndSelectDamageCapture() throws IOException
	{
		  try
          {
        	int size=getSizeOfMobileElement("deliveryhht_btn_damageCaptureOption;xpath",proppathhht);                             
                if(size==1)
                {
                clickActionInHHT("deliveryhht_btn_damageCaptureOption;xpath",proppathhht,"Damage Capture Option",screenName);
                waitForSync(5);
                writeExtent("Pass", "Verified Damage Capture Option present in  "+screenName);
                }
                else
                {
                      captureScreenShot("Android");
                      writeExtent("Fail", "Damage Capture option is not present"+screenName);
                }
          }
          catch(Exception e)
          {
                captureScreenShot("Android");
                writeExtent("Fail", "Could not click on Damage Capture Option "+screenName);
          }


	}


	

     /**
 	 * @author A-6260
 	 * Desc..verify save details
 	 * @throws IOException
 	 */
 	public void verifySaveDetails() throws IOException
 	{
 		try
 		{
 			waitForSync(1);
 			int size=getSizeOfMobileElement("txt_msgConfimation;xpath",proppathhht);

 			if(size==1)
 			{
 				writeExtent("Pass", "Details saved successfully in "+screenName);
 			}
 			else
 			{
 				captureScreenShot("Android");
 				writeExtent("Fail", "Details not saved successfully in "+screenName);
 			}
 		}

 		catch(Exception e)
 		{
 			writeExtent("Fail", "Delivery details not saved in  "+screenName);
 		}

 	}
/**
     * Desc: Enter customs reference number
     * @author A-9478
     * @param remarks
     * @throws IOException
     */
     public void enterCustomsReferenceNumber(String customRefNo) throws IOException
     {
     enterValueInHHT("deliveryhht_inbx_custRefNo;accessibilityId",proppathhht,data(customRefNo),"Customs Reference Number",screenName);
           waitForSync(4);
     }

/**
     * @author A-9478
     * Desc : Enter AWB/ULD number
     * @throws IOException 
      */
     
     public void enterAWBULDNum(String value) throws IOException
     {
     enterValueInHHT("deliveryhht_inbx_AWB/ULD;xpath",proppathhht,data(value),"value",screenName);
           waitForSync(3); 
     }

	/**
	 * @author A-9478
	 * Desc : Click Pending button
	 * @throws IOException 
	 */
	
	public void clickPendingButton() throws IOException
	{
       waitTillMobileElementDisplay(proppathhht,"deliveryhht_btn_Pending;xpath","xpath");
		
		for(int i=1;i<=2;i++)
		{
			clickActionInHHT("deliveryhht_btn_Pending;xpath",proppathhht,"Pending button",screenName);			
		}
		waitTillMobileElementDisplay(proppathhht,"deliveryhht_btn_Next;xpath","xpath",20);
	}
	
	/**
	 * @author A-9478
	 * Desc : Click radio button and enter pieces
	 * @throws IOException 
	 */
	
	public void enterPieces(String pieces) throws IOException
	{
		for(int i=1;i<=2;i++)
		{
			clickActionInHHT("deliveryhht_btn_clickRadioButton;xpath",proppathhht,"Radio button",screenName);			
		}
		waitTillMobileElementDisplay(proppathhht,"deliveryhht_inbx_Pieces;accessibilityId","accessibilityId",10);
		enterValueInHHT("deliveryhht_inbx_Pieces;accessibilityId",proppathhht,data(pieces),"Pieces",screenName);
		waitForSync(3); 
	}
	
	/**
	 * @author A-9478
	 * Desc: Click Next
	 * @throws IOException 
	 */
	public void clickNext() throws IOException
	{
		try{


			int size = getSizeOfMobileElement("deliveryhht_btn_Next;xpath", proppathhht); 
			waitForSync(2);
			if (size>0) 
			{
				clickActionInHHT("deliveryhht_btn_Next;xpath",proppathhht,"Next button",screenName);
				waitForSync(6);
			}
		}
		catch (Exception e) {

		}
	}
	/**
	 * Desc: To verify the given delivery status
	 * @param expStatus
	 * @throws IOException
	 */
	
	public void verifyDeliveryStatus(String expStatus) throws IOException
	{
		
		try
		{
			waitTillMobileElementDisplay(proppathhht,"deliveryhht_btn_DeliverComplete;xpath","xpath",20);	
			String actStatusloc=getPropertyValue(proppathhht, "deliveryhht_btn_deliveryStatus;xpath").replace("*", data(expStatus));	
			String Actstatus=androiddriver.findElement(By.xpath(actStatusloc)).getText();
			waitForSync(4);
			if(Actstatus.equals(data(expStatus)))
			writeExtent("Pass","Sucessfully found :"+data(expStatus)+" in "+screenName);
			else
			writeExtent("Fail","Couldnt find "+data(expStatus)+" in "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail","Element not found in "+screenName);
		}		

	}
	
	/**
	 * @author A-9478
	 * Desc: Click print POD
	 * @throws IOException 
	 */
	public void clickPrintPOD() throws IOException
	{
		clickActionInHHT("deliveryhht_btn_PrintPOD;xpath",proppathhht,"Print POD button",screenName);
		waitForSync(10);
	}
	
	/**
	 * @author A-9478
	 * Desc: Click Delivery Complete
	 * @throws IOException 
	 */
	public void clickDeliveryComplete() throws IOException
	{
		clickActionInHHT("deliveryhht_btn_DeliverComplete;xpath",proppathhht,"Delivery Complete",screenName);
		waitForSync(4);
	}
	
	/**
	 * @author A-9478
	 * Desc: Enter delivered to
	 * @throws IOException 
	 */
	public void enterDeliveredTo(String DeliveredTo) throws IOException
	{
		enterValueInHHT("deliveryhht_inbx_DeliveredTo;accessibilityId",proppathhht,data(DeliveredTo),"Delivered To",screenName);
		waitForSync(4);
	}
	/**
	 * @author A-9478
	 * Desc: Enter vehicle info
	 * @throws IOException 
	 */
	public void enterVehicleInfo(String vehicleInfo) throws IOException
	{
		enterValueInHHT("deliveryhht_inbx_VehicleNo;accessibilityId",proppathhht,data(vehicleInfo),"Vehicle Info",screenName);
		waitForSync(4);
	}
	/**
	 * @author A-9478
	 * Desc: Enter contact number
	 * @throws IOException 
	 */
	public void enterContactNumber(String contactNumber) throws IOException
	{
		enterValueInHHT("deliveryhht_inbx_ContactNumber;accessibilityId",proppathhht,data(contactNumber),"Contact number",screenName);
		waitForSync(4);
	}
	/**
	 * @author A-9478
	 * Desc: Enter remarks
	 * @throws IOException 
	 */
	public void enterRemarks(String remarks) throws IOException
	{
		enterValueInHHT("deliveryhht_inbx_Remarks;accessibilityId",proppathhht,data(remarks),"Remarks",screenName);
        waitForSync(3);
	}
	
	/**
	 * @author A-7271
	 * Desc : capture signature
	 */
	public void captureSignature()
	{
		int height=androiddriver.manage().window().getSize().getHeight();
        int width=androiddriver.manage().window().getSize().getWidth();
        
        int x=(int) (width*0.5);
        int y=(int) (height*0.5);
        /********** MOUSE STROKE*****/
        new TouchAction(androiddriver).longPress(x, y).moveTo((x+150), (y+150)).release().perform();
        waitForSync(1);
        
        
       
        
	}
	
	/**
	 * @author A-9478
	 * @param value
	 * Desc : Enter AWB/ULD/Tracking ID
	 * @throws IOException 
	 */
	
	public void enterValue(String value) throws IOException
	{
		enterValueInHHT("deliveryhht_inbx_value;accessibilityId",proppathhht,data(value),"Enter value",screenName);
		waitForSync(3); 
	}
	
	/**
	 * Desc: Selecting all shipments from breakdown location
	 * @author A-9175
	 * @throws IOException
	 */
	public void clickSelectAll() throws IOException
	{
		clickActionInHHT("deliveryhht_btn_selectAll;xpath",proppathhht,"Select All button",screenName);		
		waitForSync(4); 
	}
	public void enterPackageCodeDamageReasonCode(String PackageCode,String DamageReasonCode) throws InterruptedException
	{
		
		try
		{
			clickActionInHHT("damCaphht_btn_packageCode;xpath",proppathhht,"clicked on Package Code",screenName);
			waitForSync(3);
			scrollInMobileDevice(PackageCode);
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+PackageCode+"']")).click();
			waitForSync(3);
			scrollInMobileDevice("Enter or Select");
			clickActionInHHT("damCaphht_btn_damageReasonCode;xpath",proppathhht,"clicked on Damage Reason Code",screenName);
			waitForSync(2);
			scrollInMobileDevice(DamageReasonCode);
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+DamageReasonCode+"']")).click();
			waitForSync(3);
			writeExtent("Pass", "Package code and Damage reason code is entered "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Failed to enter package code and damage reason code"+screenName);
		}

	}

	/**
	 * Desc: Enter Delivery Remarks
	 * @author A-9175
	 * @param remarks
	 * @throws IOException
	 */
	public void enterDeliverRemarks(String remarks) throws IOException
	{
		waitForSync(2);
		String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
		locatorValue=locatorValue.replace("*", "Please enter remarks");
		
		if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
		{
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);
		} 
		enterValueInHHT("deliveryhht_inbx_remarks;accessibilityId",proppathhht,data(remarks),"Delivery Remarks",screenName);
	}
	

	/**
	 * @author A-9175
	 * Desc : Click Delivered button
	 * @throws IOException 
	 */
	
	public void clickDeliveredButton() throws IOException
	{
		for(int i=1;i<=2;i++)
		{
			clickActionInHHT("deliveryhht_btn_Delivered;xpath",proppathhht,"Delivered button",screenName);			
		}
		waitForSync(2); 
	}
	/**
	 * @author A-6260
	 * Desc..select shipment
	 * @throws IOException
	 */
	public void selectShipment() throws IOException {
		 
		waitForSync(5);
		String locatorValue=getPropertyValue(proppathhht, "deliveryhht_btn_selectShipment;xpath");
		if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
		{

		
		for(int i=1;i<=2;i++)
		{
			clickActionInHHT("deliveryhht_btn_selectShipment;xpath",proppathhht,"select button",screenName);
		}
		waitForSync(4);
		}
	}
	/**
	 * Desc: Delivery status verification
	 * @author A-9175
	 * @param expStatus
	 * @throws IOException
	 */
	public void deliveryStatusVerify(String expStatus) throws IOException
	{
		
		try
		{
			waitTillMobileElementDisplay(proppathhht,"deliveryhht_btn_DeliverComplete;xpath","xpath",20);
			String locatorValue=getPropertyValue(proppathhht, "deliveryhht_btn_Delivered;xpath");
			String Actstatus=androiddriver.findElement(By.xpath(locatorValue)).getText();
			waitForSync(4);
			if(Actstatus.equals(data(expStatus)))
			writeExtent("Pass","Sucessfully found :"+data(expStatus)+" in "+screenName);
			else
			writeExtent("Fail","Couldnt find "+data(expStatus)+" in "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail","Element not found in "+screenName);
		}		

	}

}
