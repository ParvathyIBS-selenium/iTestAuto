package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.appium.java_client.MobileElement;
import org.testng.Assert;

public class TransportOrderListing extends CustomFunctions {

	String sheetName = "TransportOrderListing";
	String screenName = "TransportOrderListing";
	public String globalVariableProppath = "\\src\\resources\\TO.properties";

	public TransportOrderListing(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	} 
	/**
	 * @author A-10690
	 * @Desc To verify whether assignedbuild up location got updated in TO listing screen
	 * @param fullawbnumber,assignedlocation
	 */


	public void verifyAssigneddBuildupLocationinTOlistingscreen(String fullawbnumber,String assignedlocation )
	{

		waitForSync(3);
		String locator=getPropertyValue(proppathtransportorder, "to_relocationtext;xpath");
		locator=locator.replace("*",data(fullawbnumber));
		locator=locator.replace("location",data(assignedlocation));
		if(androiddriver.findElements(By.xpath(locator)).size()>0)
			writeExtent("Pass", "new location  "+data(assignedlocation)+ " updated on " +screenName);					
		else					
			writeExtent("Fail", "new location  "+data(assignedlocation)+ " not updtade on " +screenName);


	}

	public void enterEmptyLocation(String location) throws IOException {

		try{
			clearValueInHHT("to_inbx_destLocation;xpath",proppathtransportorder,"Destination location",screenName);
			enterValueInHHT("to_inbx_destLocation;xpath",proppathtransportorder,data(location),"Destination location",screenName);
			String locatorValue = getPropertyValue(proppathtransportorder, "to_btn_destOk;xpath");
			locatorValue = locatorValue.replace("dest", data(location));
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);
			chooseOptionYes();
			waitForSync(1);
			writeExtent("Pass", "Successfully clicked on relocation complete for the task on "+ screenName);
		}
		catch (Exception e) {
			writeExtent("Fail", "Failed to complete relocation "+ screenName);
		}
	}

	/**
	 * @author A-10690
	 * Desc- To select all option from handling area filter
	 */
	public void  selectAllOption() throws IOException
	{
		waitTillMobileElementDisplay(proppathtransportorder, "to_filterIcon;xpath", "xpath");
		clickActionInHHT("to_filterIcon;xpath", proppathtransportorder, "filter Icon", screenName);
		waitForSync(1);

		int size = getSizeOfMobileElement("to_selectHandlingare;xpath", proppathtransportorder); 
		if (size == 1)
		{
			clickActionInHHT("to_selectHandlingare;xpath", proppathtransportorder, "select handling area button", screenName);
			waitForSync(1);
			clickActionInHHT("to_selectAlloption;xpath", proppathtransportorder, "All button", screenName);
			clickActionInHHT("to_Done;xpath", proppathtransportorder, "done button", screenName);
		}
		waitTillMobileElementDisplay(proppathtransportorder, "to_clearFilter;xpath", "xpath");
		clickActionInHHT("to_closeFilter;xpath", proppathtransportorder, "close filter", screenName);
		waitForSync(3);
		}
	/**
	 * @author A-10690
	 * Desc- To verify shipment details -awb/uld is removed
	 * @param uldNo/AWB
	 */
	public void verifyTOIsRemoved(String shipment) {


		String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmentDetails;xpath");

		locatorValue=locatorValue.replace("*", data(shipment));     

		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

		if(eleSize==0)
		{
			writeExtent("Pass","Verified that the shipment "+data(shipment)+" is removed from TO APP");

		}
		else
		{
			writeExtent("Fail", "TO is not removed for  " + data(shipment) + " on " + screenName );
			Assert.assertFalse(true, "TO is not removed  for " + data(shipment) + " on " + screenName );
		}
	}
	/**
	 * @author A-9844
	 * Desc - Verify location and its Zone
	 * @param  expZone
	 * @param actZone
	 * @throws InterruptedException 
	 */
	public void  verifyZone(String destnLocationZone,String expZone) throws InterruptedException
	{
		if (destnLocationZone.contains(data(expZone)))
		{
			writeExtent("Pass", "Verified the zone as "+data(expZone)+"  on "+screenName);
     	}
		else{
			writeExtent("Fail", "Failed to verify the zone as "+data(expZone)+"  on "+screenName);
		}
	}
	/**
	 * @author A-8783
	 * @Desc choose options - Yes from the warning message
	 * @throws IOException 
	 */

	public void chooseOptionYes() throws IOException{

		clickActionInHHT("btn_Yes;xpath",proppathtransportorder,"Yes Option",screenName);
		waitForSync(4);	


	}
	/**
	 * @author A-8783
	 * Desc - Verify pieces and weight
	 * @param shipment
	 * @param pieces
	 * @param weight
	 */
	public void  verifyPcsWt(String shipment, String pieces, String weight){
		String locatorValue=getPropertyValue(proppathtransportorder, "to_pcsWt;xpath");
		waitForSync(1);
		locatorValue=locatorValue.replace("shp", data(shipment));   
		System.out.println(locatorValue);
		String actPcsWt=androiddriver.findElement(By.xpath(locatorValue)).getText(); 
		String expPcsWt = data(pieces)+"/"+data(weight)+" Kg";
		verifyScreenText(sheetName, expPcsWt, actPcsWt, actPcsWt, screenName);
	}
	/**
	 * @author A-10330
	 * Desc- selcet the To filters in TO app
	 * @param filter option filter
	 */
	public void selectToFilter(String filteroption,String filter) throws IOException
	{
		waitTillMobileElementDisplay(proppathtransportorder, "to_filterIcon;xpath", "xpath");
		clickActionInHHT("to_filterIcon;xpath", proppathtransportorder, "filter Icon", screenName);
		waitForSync(3);

		try
		{
			if(filter.equals("Handling Area"))
			{
				waitForSync(2);
				String locator=getPropertyValue(proppathtransportorder, "to_listToFilters;xpath");

				locator=locator.replace("*", filter);
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(1);
				scrollMobileDevice(filteroption);
				String locator1=getPropertyValue(proppathtransportorder, "to_selectToFilters;xpath");
				locator1=locator1.replace("*", filteroption);
				androiddriver.findElement(By.xpath(locator1)).click();
				waitForSync(1);
			}
			else if(filter.equals("Destination Location"))
			{
				waitForSync(2);
				String locator=getPropertyValue(proppathtransportorder, "to_listToFilters;xpath");
				locator=locator.replace("*", filter);
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(1);
				scrollMobileDevice(filteroption);
				String locator1=getPropertyValue(proppathtransportorder, "to_selectToFilters;xpath");
				locator1=locator1.replace("*", filteroption);
				androiddriver.findElement(By.xpath(locator1)).click();
			}
			else if(filter.equals("Flight"))
			{
				waitForSync(2);
				String locator=getPropertyValue(proppathtransportorder, "to_listToFilters;xpath");
				locator=locator.replace("*", filter);
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(1);
				scrollMobileDevice(filteroption);
				String locator1=getPropertyValue(proppathtransportorder, "to_selectToFilters;xpath");
				locator1=locator1.replace("*", filteroption);
				androiddriver.findElement(By.xpath(locator1)).click();
			}
			else if(filter.equals("SCC"))
			{
				waitForSync(2);
				String locator=getPropertyValue(proppathtransportorder, "to_listToFilters;xpath");
				locator=locator.replace("*", filter);
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(1);
				scrollMobileDevice(filteroption);
				String locator1=getPropertyValue(proppathtransportorder, "to_selectToFilters;xpath");
				locator1=locator1.replace("*", filteroption);
				androiddriver.findElement(By.xpath(locator1)).click();
			}

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Failed to select the TO"+filter+" filter on "+screenName);
		}
	}
	/*
	/**
	 * @author A-9844
	 * Desc- to un select the HA
	 */
	public void  unSelectHA() throws IOException
	{
		//click filter icon
				waitTillMobileElementDisplay(proppathtransportorder, "to_filterIcon;xpath", "xpath");
				clickActionInHHT("to_filterIcon;xpath", proppathtransportorder, "filter Icon", screenName);
				
				//click clear button
				waitTillMobileElementDisplay(proppathtransportorder, "to_clearFilter;xpath", "xpath");
				clickActionInHHT("to_clearFilter;xpath", proppathtransportorder, "clear filter", screenName);
				
				//click HA icon
				waitTillMobileElementDisplay(proppathtransportorder, "to_selectHandlingare;xpath", "xpath");
				clickActionInHHT("to_selectHandlingare;xpath", proppathtransportorder, "select handling area button", screenName);
				
				//click Select All option
				waitTillMobileElementDisplay(proppathtransportorder, "to_selectAlloption;xpath", "xpath");
				clickActionInHHT("to_selectAlloption;xpath", proppathtransportorder, "All button", screenName);
				waitForSync(2);
				clickActionInHHT("to_selectAlloption;xpath", proppathtransportorder, "All button", screenName);
				waitForSync(2);
				
				//click Done button
				waitTillMobileElementDisplay(proppathtransportorder, "to_Done;xpath", "xpath");
				clickActionInHHT("to_Done;xpath", proppathtransportorder, "done button", screenName);
				waitForSync(3);
				
				//click filter close button
				waitTillMobileElementDisplay(proppathtransportorder, "to_closeFilter;xpath", "xpath");
				clickActionInHHT("to_closeFilter;xpath", proppathtransportorder, "close filter", screenName);
				waitForSync(3);
	}


	/**
	 * @author A-9844
	 * @Desc select HA
	 * @throws IOException 
	 */

	public void selectHA() throws IOException{

		
		String HA_CDG=getPropertyValue(globalVariableProppath,"HandlingArea");
		String HA_AMS=getPropertyValue(globalVariableProppath,"HandlingArea_AMS");
		int size = getSizeOfMobileElement("to_selectHAText;xpath", proppathtransportorder); 
		waitForSync(3);	
		if (size == 1) 
		{

			try{
				String locatorHA=getPropertyValue(proppathtransportorder, "to_handlingAreaOptionsList;xpath");
				locatorHA=locatorHA.replace("*", HA_CDG); 
				scrollMobileDevice(HA_CDG);
				androiddriver.findElement(By.xpath(locatorHA)).click();
				waitForSync(3);

			}
			catch (Exception e) 

			{

				String locatorHA=getPropertyValue(proppathtransportorder, "to_handlingAreaOptionsList;xpath");
				locatorHA=locatorHA.replace("*", HA_AMS); 
				scrollMobileDevice(HA_AMS);
				androiddriver.findElement(By.xpath(locatorHA)).click();
				waitForSync(3);

			}

			clickActionInHHT("to_Done;xpath",proppathtransportorder,"Done Button",screenName); 
			waitForSync(3);	
			writeExtent("Pass", "Selected the Handling Area on "+ screenName);
		}
	}


	/**
	 * @author A-9844
	 * Desc- To verify shipment details -awb/uld
	 * @param uldNo
	 */
	public void verifyShipmentsAreListed(String shipment) {


		String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmentDetails;xpath");

		locatorValue=locatorValue.replace("*", data(shipment));     

		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

		if(eleSize>0)
		{
			writeExtent("Pass","Verified that the shipments "+data(shipment)+" are listed");

		}
		else
		{
			writeExtent("Fail", "TO is not generated for " + data(shipment) + " on " + screenName );
			Assert.assertFalse(true, "TO is not generated for " + data(shipment) + " on " + screenName );
		}
	}

	/**
	 * @author A-9844
	 * @Desc search awb/uld number
	 * @param flightNum
	 * @throws IOException 
	 */

	public void searchForShipments(String shipment) throws IOException{

		String HA_CDG=getPropertyValue(globalVariableProppath,"HandlingArea");
		String HA_AMS=getPropertyValue(globalVariableProppath,"HandlingArea_AMS");
		int size = getSizeOfMobileElement("to_selectHAText;xpath", proppathtransportorder); 
		waitForSync(3);	
		if (size == 1) 
		{

			try{
				String locatorHA=getPropertyValue(proppathtransportorder, "to_handlingAreaOptionsList;xpath");
				locatorHA=locatorHA.replace("*", HA_CDG); 
				scrollMobileDevice(HA_CDG);
				androiddriver.findElement(By.xpath(locatorHA)).click();
				waitForSync(3);

			}
			catch (Exception e) 

			{

				String locatorHA=getPropertyValue(proppathtransportorder, "to_handlingAreaOptionsList;xpath");
				locatorHA=locatorHA.replace("*", HA_AMS); 
				scrollMobileDevice(HA_AMS);
				androiddriver.findElement(By.xpath(locatorHA)).click();
				waitForSync(3);

			}

			clickActionInHHT("to_Done;xpath",proppathtransportorder,"Done Button",screenName); 
			waitForSync(3);	
			writeExtent("Pass", "Selected the Handling Area on "+ screenName);

		}
		selectAllOption();
		waitTillMobileElementDisplay(proppathtransportorder, "to_searchIcon;xpath", "xpath");
		clickActionInHHT("to_;xpath",proppathtransportorder,"Search shipment",screenName); 
		waitForSync(3);	
		enterValueInHHT("to_enterShipmentDetails;xpath",proppathtransportorder,data(shipment),"shipment details",screenName);
		waitForSync(3);	
		//verify whether the TO is generated and displayed in the TO app
		verifyShipmentsAreListed(shipment); 


	}
	/**
	 * @author A-9844
	 * @Desc select HA
	 * @throws IOException 
	 */

	public void selectHA(String HA) throws IOException{


		int size = getSizeOfMobileElement("to_selectHAText;xpath", proppathtransportorder); 
		waitForSync(3);	
		if (size == 1) 
		{


			String locatorHA=getPropertyValue(proppathtransportorder, "to_handlingAreaOptionsList;xpath");
			locatorHA=locatorHA.replace("*", HA); 
			scrollMobileDevice(HA);
			androiddriver.findElement(By.xpath(locatorHA)).click();
			waitForSync(3);

			clickActionInHHT("to_Done;xpath",proppathtransportorder,"Done Button",screenName); 
			waitForSync(3);	
			writeExtent("Pass", "Selected the Handling Area on "+ screenName);
		}
		}
	/**
	 *@author A-9844
	 * @param shipment
	 * @param status
	 * @param originLoc
	 * Desc : verify the TO details in the app
	 */
	public void verifyULDDetails(String shipment, String status, String originLoc) {



		try{
			int flag=0;
			String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmntOrigin;xpath");
			waitForSync(1);
			locatorValue=locatorValue.replace("shp", data(shipment));   
			System.out.println(locatorValue);



			List <MobileElement> elements=androiddriver.findElements(By.xpath(locatorValue));



			for(MobileElement ele:elements)
			{
				String actOrg=ele.getText();
				String statusLoc=getPropertyValue(proppathtransportorder, "to_shipmntStatus;xpath").replace("*", actOrg).replace("shp", data(shipment));
				waitForSync(2);
				String actStatus=androiddriver.findElement(By.xpath(statusLoc)).getText();              

				String expOrg=data(originLoc).trim();
				if((actOrg.trim().equals(expOrg)) && actStatus.equals(data(status)))
					flag=flag+1;          



			}



			if(flag>0)
				writeExtent("Pass","TO got generated from Origin "+data(originLoc)+" and Status as "+data(status)+" for the shipments");


			else
				writeExtent("Fail","No TO got generated from Origin "+data(originLoc)+" and Status as "+data(status));
		}catch(Exception e){



			writeExtent("Fail","Failed to verify the TO generated");
		}


	}
	/**
	 * @author A-9844
	 * @desc enter shipment details
	 * @param location
	 * @throws IOException 
	 */
	public void enterShipmentDetails(String shipment) throws IOException {

		waitTillMobileElementDisplay(proppathtransportorder,"to_searchShipment;xpath","xpath",10);	
		clickActionInHHT("to_searchShipment;xpath",proppathtransportorder,"Search shipment",screenName); 
		waitTillMobileElementDisplay(proppathtransportorder, "to_enterShipmentDetails;xpath", "xpath");
		enterValueInHHT("to_enterShipmentDetails;xpath",proppathtransportorder,data(shipment),"shipment details",screenName);
		waitForSync(5);	
	}


	/**
	 * @author A-10330
	 * Desc- verify the TO  filter is selected
	 * @param filter option,filter
	 */
	public void VerifyFilterSelected(String filteroption,String filter) throws IOException
	{
		waitForSync(1);
		clickActionInHHT("to_Done;xpath", proppathtransportorder, "filter done button ", screenName);
		waitTillMobileElementDisplay(proppathtransportorder, "to_clearFilter;xpath", "xpath");
		String locator=getPropertyValue(proppathtransportorder, "to_verifyFilter;xpath");
		locator=locator.replace("filter", filter).replace("*", filteroption);
		if( androiddriver.findElements(By.xpath(locator)).size()>0)
		{
			writeExtent("Pass", "TO "+filter+"filter"+filteroption+"is selected on "+screenName); 
		}
		else
		{
			writeExtent("Fail", "TO "+filter+" filter"+filteroption+"is not selected on "+screenName);  
		}
		waitForSync(1);

		clickActionInHHT("to_closeFilter;xpath", proppathtransportorder, "close filter", screenName);
		waitForSync(3);
	}
	/**
	 * @author A-10330
	 * Desc- clear the TO  filter is selected
	 */
	public void  clearFilterOption() throws IOException
	{
		waitTillMobileElementDisplay(proppathtransportorder, "to_filterIcon;xpath", "xpath");
		clickActionInHHT("to_filterIcon;xpath", proppathtransportorder, "filter Icon", screenName);
		waitTillMobileElementDisplay(proppathtransportorder, "to_clearFilter;xpath", "xpath");
		clickActionInHHT("to_clearFilter;xpath", proppathtransportorder, "clear button", screenName);
		waitTillMobileElementDisplay(proppathtransportorder, "to_closeFilter;xpath", "xpath");
		clickActionInHHT("to_closeFilter;xpath", proppathtransportorder, "close filter", screenName);
		waitForSync(3);
	}

	/**
	 * @author A-9847
	 * Desc - Verify TO generated for the corresponding Origin and Destination Locations and status
	 * @param shipment
	 * @param status
	 * @param originLoc
	 * @param DestLoc
	 */
	public void verifyShipmentDetails(String shipment, String status, String originLoc, String DestLoc) {

		try{
			int flag=0;
			String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmntrelocation;xpath");
			waitForSync(1);
			locatorValue=locatorValue.replace("*", data(originLoc)).replace("shp", data(shipment));   
			System.out.println(locatorValue);

			List <MobileElement> elements=androiddriver.findElements(By.xpath(locatorValue));

			for(MobileElement ele:elements)
			{
				String actDest=ele.getText();
				System.out.println(actDest);
				String statusLoc=getPropertyValue(proppathtransportorder, "to_shipmntStatus;xpath").replace("*", actDest).replace("shp", data(shipment));
				waitForSync(2);
				String actStatus=androiddriver.findElement(By.xpath(statusLoc)).getText();              
				System.out.println(actStatus);

				if(actDest.equals(data(DestLoc)) && actStatus.equals(data(status)))
					flag=flag+1;          

			}

			if(flag==1)
				writeExtent("Pass","TO got generated with Destination "+data(DestLoc)+" and Status as "+data(status));

			else if(flag>1)
				writeExtent("Fail","Duplicate TOs got generated with Destination "+data(DestLoc)+" and Status as "+data(status));

			else
				writeExtent("Fail","No TO got generated with Destination "+data(DestLoc)+" and Status as "+data(status));
		}catch(Exception e){

			writeExtent("Fail","Failed to verify the TO generated");
		}

	}
	/**
	 * @author A-9844
	 * @param warningMsg1
	 * @param warningMsg1
	 * Desc : verify error messgae with Yes and No options
	 */
	public void verifyWarningMessage(String warningMsg1,String warningMsg2) throws AWTException, InterruptedException, IOException
	{

		waitForSync(6);
		String locatorValue1=getPropertyValue(proppathtransportorder, "to_warningMessage1;xpath");
		String locatorValue2=getPropertyValue(proppathtransportorder, "to_warningMessage2;xpath");

		String actText1=androiddriver.findElement(By.xpath(locatorValue1)).getText();
		String actText2=androiddriver.findElement(By.xpath(locatorValue2)).getText();

		verifyScreenTextWithExactMatch(screenName, warningMsg1,actText1, "successfully the warming message ", screenName);
		verifyScreenTextWithExactMatch(screenName, warningMsg2,actText2, "successfully the warming message ", screenName);


		//verifying YES and NO options are present
		String locatorValueYes=getPropertyValue(proppathtransportorder, "btn_Yes;xpath");
		String locatorValueNo=getPropertyValue(proppathtransportorder, "btn_No;xpath");
		waitForSync(3);
		if((androiddriver.findElements(By.xpath(locatorValueYes)).size()==1) && (androiddriver.findElements(By.xpath(locatorValueNo)).size()==1))
		{
			writeExtent("Pass","Verified Yes and No Buttons are present on "+screenName);

		}

		else{
			writeExtent("Fail","Failed to verify Yes and No Buttons are present on "+screenName);
		}


	}




	/**
	 * @author A-9844
	 * @Desc choose options - No from the warning message
	 * @throws IOException 
	 */

	public void chooseOptionNo() throws IOException{


		clickActionInHHT("btn_No;xpath",proppathtransportorder,"No Option",screenName);
		waitForSync(4);	


	}




	/**
	 * @author A-9844
	 * @param fullawbnumber
	 * @param assignedlocation
	 * @param wronglocation
	 * @Desc To verify relocation not happened to new location
	 * @param fullawbnumber,assignedlocation
	 */


	public void verifyRelocatedLocationNotUpdated(String fullawbnumber,String assignedlocation, String wronglocation )
	{

		waitForSync(3);
		String locator=getPropertyValue(proppathtransportorder, "to_relocationtext;xpath");
		locator=locator.replace("*",data(fullawbnumber));
		locator=locator.replace("location",data(assignedlocation));
		if(androiddriver.findElements(By.xpath(locator)).size()>0)
			writeExtent("Pass", "Verified no relocation happens to the wrong location.Sugested Location is " +data(assignedlocation)+screenName);					
		else					
			writeExtent("Fail", "Relocation happens to the wrong location "+ data(wronglocation)+"on "+screenName);


	}


	/**
	 * @author A-9844
	 * Desc - Click relocation complete OK button
	 * @throws IOException
	 */
	public void clickRelocationComplete() throws IOException {

		clickActionInHHT("to_btn_destOk;xpath",proppathtransportorder,"Check button",screenName); 
		waitForSync(3);	

	}

	/**
	 * @author A-9844
	 * @Desc click refresh button
	 * @throws IOException 
	 */

	public void clickRefresh() throws IOException{


		waitTillMobileElementDisplay(proppathtransportorder, "to_refreshIcon;xpath", "xpath");
		clickActionInHHT("to_refreshIcon;xpath", proppathtransportorder, "refresh icon", screenName);
		waitTillMobileElementDisplay(proppathtransportorder, "to_searchIcon;xpath", "xpath");



	}

	/**
	 * @author A-9844
	 * @Desc search awb/uld number
	 * @param flightNum
	 * @throws IOException 
	 */

	public void searchShipment(String shipment) throws IOException{

		String HA_CDG=getPropertyValue(globalVariableProppath,"HandlingArea");
		String HA_AMS=getPropertyValue(globalVariableProppath,"HandlingArea_AMS");
		int size = getSizeOfMobileElement("to_selectHAText;xpath", proppathtransportorder); 
		waitForSync(3);	
		if (size == 1) 
		{

			try{
				String locatorHA=getPropertyValue(proppathtransportorder, "to_handlingAreaOptionsList;xpath");
				locatorHA=locatorHA.replace("*", HA_CDG); 
				scrollMobileDevice(HA_CDG);
				androiddriver.findElement(By.xpath(locatorHA)).click();
				waitForSync(3);

			}
			catch (Exception e) 

			{

				String locatorHA=getPropertyValue(proppathtransportorder, "to_handlingAreaOptionsList;xpath");
				locatorHA=locatorHA.replace("*", HA_AMS); 
				scrollMobileDevice(HA_AMS);
				androiddriver.findElement(By.xpath(locatorHA)).click();
				waitForSync(3);

			}

			clickActionInHHT("to_Done;xpath",proppathtransportorder,"Done Button",screenName); 
			waitForSync(3);	
			writeExtent("Pass", "Selected the Handling Area on "+ screenName);

		}
		selectAllOption();
		waitTillMobileElementDisplay(proppathtransportorder, "to_searchIcon;xpath", "xpath");
		clickActionInHHT("to_searchShipment;xpath",proppathtransportorder,"Search shipment",screenName); 
		waitForSync(3);	
		waitTillMobileElementDisplay(proppathtransportorder, "to_enterShipmentDetails;xpath", "xpath");
		enterValueInHHT("to_enterShipmentDetails;xpath",proppathtransportorder,data(shipment),"shipment details",screenName);
		waitForSync(3);	
		//verify whether the TO is generated and displayed in the TO app
		verifyShipmentIsListed(shipment); 



	}
	/**
	 * @author A-8783 Desc - Select task
	 * @param location
	 */
	public void selectTask(String location) {

		try{

			waitForSync(4);
			String locatorValue = getPropertyValue(proppathtransportorder, "to_selectTask;xpath");
			locatorValue = locatorValue.replace("loc", data(location));
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitTillMobileElementDisplay(proppathtransportorder, "to_taskSelectTxt;xpath", "xpath");
			writeExtent("Pass", "Successfully Selected the task on "+ screenName);
		}
		catch(Exception e){
			writeExtent("Fail", "Could not select the task on "+ screenName);
		}
	}
	/**
	 * @author A-9844
	 * Description... retrieve the source location
	 * @param awbno
	 * @throws InterruptedException
	 */
	public String retrieveSrcLocation(String awbno)throws Exception
	{
		String location = new String();
		try
		{
			waitTillMobileElementDisplay(proppathtransportorder, "to_searchIcon;xpath", "xpath");
			String srcLocator = getPropertyValue(proppathtransportorder, "to_shipmentsrcLocation;xpath");
			srcLocator = srcLocator.replace("shp", data(awbno));
			location =androiddriver.findElement(By.xpath(srcLocator)).getText().trim();
			writeExtent("Pass", "Successfully retrived the source location as " + location + " from " + screenName);

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't retrieve the source location from " + screenName+".Since TO is not generated.");
		}
		return location;     
	}


	/**
	 *@author A-9844
	 * @param originLocation
	 * @param destinationLocation
	 * Desc : retrieve and verify origin location
	 */
	public void retrieveAndVerifyOriginLocation(String awbNo,String expSrcLoc) {

		String srcLocation = getPropertyValue(proppathtransportorder, "to_shipmentsrcLocation;xpath");
		srcLocation = srcLocation.replace("shp", data(awbNo));
		//retrieving the src location
		String actSrcLocation =androiddriver.findElement(By.xpath(srcLocation)).getText().trim();  
		System.out.println(actSrcLocation);
		verifyScreenText(sheetName, data(expSrcLoc), actSrcLocation, "Source location","Source location verification");

	}

	/**
	 *@author A-9847
	 * @param shipment
	 * @param status
	 * @param originLoc
	 * Desc : verify the TO details in the app
	 */
	public void verifyShipmentDetails(String shipment, String status, String originLoc) {



		try{
			int flag=0;
			String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmntOrigin;xpath");
			waitForSync(1);
			locatorValue=locatorValue.replace("shp", data(shipment));   
			System.out.println(locatorValue);



			List <MobileElement> elements=androiddriver.findElements(By.xpath(locatorValue));



			for(MobileElement ele:elements)
			{
				String actOrg=ele.getText();
				String statusLoc=getPropertyValue(proppathtransportorder, "to_shipmntStatus;xpath").replace("*", actOrg).replace("shp", data(shipment));
				waitForSync(2);
				String actStatus=androiddriver.findElement(By.xpath(statusLoc)).getText();              

				String expOrg=data(originLoc).trim();
				if((actOrg.trim().equals(expOrg)) && actStatus.equals(data(status)))
					flag=flag+1;          



			}



			if(flag==1)
				writeExtent("Pass","TO got generated from Origin "+data(originLoc)+" and Status as "+data(status));



			else if(flag>1)
				writeExtent("Fail","Duplicate TOs got generated from Origin "+data(originLoc)+" and Status as "+data(status));



			else
				writeExtent("Fail","No TO got generated from Origin "+data(originLoc)+" and Status as "+data(status));
		}catch(Exception e){



			writeExtent("Fail","Failed to verify the TO generated");
		}





	}
	/**
	 * @author A-9844
	 * Description... retrieve the destination location
	 * @param awbno
	 * @throws InterruptedException
	 */
	public String retrieveDestnLocation(String awbno)throws Exception
	{
		String location = new String();
		try
		{
			waitTillMobileElementDisplay(proppathtransportorder, "to_searchIcon;xpath", "xpath");
			String srcLocator = getPropertyValue(proppathtransportorder, "to_shipmentdestnLocation;xpath");
			srcLocator = srcLocator.replace("shp", data(awbno));
			location =androiddriver.findElement(By.xpath(srcLocator)).getText().trim();
			writeExtent("Pass", "TO is generated and retrived the destination location as " + location + " from " + screenName);

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't retrieve the location from " + screenName+", since TO is not generated.");
		}
		return location;     
	}


	/**
	 * @author A-9844
	 * Description... retrieve the destination location
	 * @param awbno
	 * @throws InterruptedException
	 */
	public String retrieveOriginLocation(String awbno)throws Exception
	{
		String location = new String();
		try
		{
			waitTillMobileElementDisplay(proppathtransportorder, "to_searchIcon;xpath", "xpath");
			String srcLocator = getPropertyValue(proppathtransportorder, "to_shipmentdestnLocation;xpath");
			srcLocator = srcLocator.replace("shp", data(awbno));
			location =androiddriver.findElement(By.xpath(srcLocator)).getText();
			writeExtent("Pass", "Successfully retrived the destination location as " + location + " from " + screenName);

		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't retrieve the location from " + screenName);
		}
		return location;     
	}
	/**
	 * @author A-8783 
	 * Desc - Click confirm task list
	 * @throws IOException
	 */
	public void confirmTaskList() throws IOException {
		String locatorValueTask = getPropertyValue(proppathtransportorder, "to_confirmTaskList;xpath");
		int size = androiddriver.findElements(By.xpath(locatorValueTask)).size();
		if(size==1)
		{
		clickActionInHHT("to_confirmTaskList;xpath", proppathtransportorder, "Confirm task list", screenName);
		waitForSync(5);
		}
	}
	/**
	 * @author A-8783
	 * Desc - Enter destination location
	 * @param location
	 * @throws IOException
	 */
	public void enterDestLocation(String location) throws IOException {
		try{
			clearValueInHHT("to_inbx_destLocation;xpath",proppathtransportorder,"Destination location",screenName);
			enterValueInHHT("to_inbx_destLocation;xpath",proppathtransportorder,data(location),"Destination location",screenName);
			String locatorValue = getPropertyValue(proppathtransportorder, "to_btn_destOk;xpath");
			locatorValue = locatorValue.replace("dest", data(location));
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);	
			writeExtent("Pass", "Successfully clicked on relocation complete for the task on "+ screenName);
		}
		catch (Exception e) {
			writeExtent("Fail", "Relocation was not successful on "+ screenName);
		}



	}


	/**
	 * 
	 * @param originLocation
	 * @param destinationLocation
	 * Desc : verify TO relocation details 
	 */
	public void verifyShipmentRelocation(String originLocation,String destinationLocation) {

		String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmentrelocation;xpath");
		locatorValue=locatorValue.replace("*", data(originLocation));    

		String actRelocation =androiddriver.findElement(By.xpath(locatorValue)).getText();
		verifyScreenText(sheetName, data(destinationLocation), actRelocation, "Relocation verification","Assign flight location ");

	}

	/**
	 * 
	 * @param originLocation
	 * @param destinationLocation
	 * Desc : verify TO relocation details 
	 */
	public void verifyShipmentRelocation(String location) {

		String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmentrelocation;xpath");
		locatorValue=locatorValue.replace("*", data(location));    

		String actRelocation =androiddriver.findElement(By.xpath(locatorValue)).getText();
		verifyScreenText(sheetName, data(location), actRelocation, "Relocation verification","Assign flight location ");

	}

	/**
	 * @author A-8783
	* Desc - Click relocation complete OK button
	 * @throws IOException
	 */
	public void clickRelocationComplete(String location) throws IOException {

		try{
			waitTillMobileElementDisplay(proppathtransportorder, "to_taskSelectTxt;xpath", "xpath");
			String locatorValueTask = getPropertyValue(proppathtransportorder, "to_confirmTaskList;xpath");
			int size = androiddriver.findElements(By.xpath(locatorValueTask)).size();
			if(size==1){
				androiddriver.findElement(By.xpath(locatorValueTask)).click();
				writeExtent("Pass", "Successfully clicked on Confirm Task List on "+screenName);
			}
			String locatorValue = getPropertyValue(proppathtransportorder, "to_btn_destOk;xpath");
			locatorValue = locatorValue.replace("dest", data(location));
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(6);
			writeExtent("Pass", "Successfully clicked on relocation complete for the task on "+ screenName);
			waitTillMobileElementDisplay(proppathtransportorder, "to_swipeRightTask;xpath", "xpath");

		}

		catch(Exception e){
			writeExtent("Fail", "Could not complete relocation for the task on "+ screenName); 
		}



	}

	/**
	 * @author A-10690
	 * @Desc clear searched shipments
	 * @throws IOException 
	 */

	public void clearShipment() throws IOException{

		waitForSync(2);	
		clearValueInHHT("to_searchtext;xpath",proppathtransportorder,"URL","SST Login");
		waitForSync(2);	


	}


	/**
	 * @author A-9844
	 * Desc- To verify shipment details -awb/uld
	 * @param uldNo
	 */
	public void verifyShipmentIsListed(String shipment) {


		String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmentDetails;xpath");
		locatorValue=locatorValue.replace("*", data(shipment));   
		waitForSync(6);
		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
		if(eleSize==1)
		{
			writeExtent("Pass","Verified that the shipment "+data(shipment)+" is listed");

		}
		else
		{
			writeExtent("Fail", "TO is not generated for " + data(shipment) + " on " + screenName );
			Assert.assertFalse(true, "TO is not generated for " + data(shipment) + " on " + screenName );
		}
	}

	/**
	 * @author A-9844
	 * Desc- To verify status of the shipment
	 * @param uldNo
	 */
	public void verifyShipmentStatus(String status) {


		String locatorValue=getPropertyValue(proppathtransportorder, "to_shipmentStatus;xpath");

		locatorValue=locatorValue.replace("*", data(status));     

		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

		if(eleSize==1)
		{
			writeExtent("Pass","Verified the shipment status as "+data(status));

		}
		else
		{
			writeExtent("Fail", "Failed to verify the shipment status as "+data(status));
		}
	}



}






