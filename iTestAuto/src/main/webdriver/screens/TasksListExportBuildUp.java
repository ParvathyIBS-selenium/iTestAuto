package screens;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;

public class TasksListExportBuildUp extends CustomFunctions {
	
	String sheetName = "TasksListExportBuildUp";
	String screenName = "TasksList ExportBuildUp";
	

	public TasksListExportBuildUp(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	} 

/**
	 * @author A-9847
	 * @Desc To verify whether task is present
	 * @param flightNum
	 */
	
	   public void verifytaskCreated(String flightNum){
		
		   scrollInMobileDevice(data(flightNum));    //SCROLL ADDED
		    String locator=getPropertyValue(proppathexportbuildup, "tasklist_flight;xpath");
			locator=locator.replace("*",data(flightNum));		        	        
			if(androiddriver.findElements(By.xpath(locator)).size()>0)
				writeExtent("Pass", "Task is created for Flight Number "+data(flightNum)+ " on " +screenName);					
			else					
				writeExtent("Fail", "Task is not created for Flight Number "+data(flightNum)+ " on " +screenName);
				 
			
	}
	   /**
		 * @author A-10690
		 * @Desc To verify the dimension indicator in the export build up apk
		 * @param AWB Number
		 * @param location
		 */

		public void verifyDimensionIndicator(String FullAwbNo){

			String locator=getPropertyValue(proppathexportbuildup, "txt_DimensionIndicator;xpath");
			locator=locator.replace("*",data(FullAwbNo));		        	        
			if(androiddriver.findElements(By.xpath(locator)).size()>0)
				writeExtent("Pass", "Sucessfully verified dimension indicator for "+data(FullAwbNo)+ " updated on " +screenName);					
			else					
				writeExtent("Fail", "Failed to verify dimension indicator for   "+data(FullAwbNo)+ "  on " +screenName);


		}	
		/**
		 * @author A-10330
		 * @Desc To verify the uld warning msg and handle the warning popup
		 */

		public void handleULDWarning() throws IOException{
			waitTillMobileElementDisplay(proppathexportbuildup,"text_alertMsg2;xpath","xpath");

			String locator = getPropertyValue(proppathexportbuildup, "text_alertMsg2;xpath");
			waitForSync(3);
			if(androiddriver.findElements(By.xpath(locator)).size()==1)
			{
				String warning = getTextAndroid("text_alertMsg2;xpath",proppathexportbuildup,"New ULD Warning",screenName);
				System.out.println(warning);
				
				if(warning.contains("ULD not available in the system. Do you want to create ?"))
					writeExtent("Info","Warning message came as " +warning+" on "+screenName);
				else
					writeExtent("Fail","Warning message came as " +warning+" on "+screenName);

				clickActionInHHT("btn_Yes;xpath",proppathexportbuildup,"Yes Button",screenName); 
				waitForSync(3);

			}	

		}
		/**
		 * @author A-9844
		 * @Desc assigning a build up location
		 * @param awb
		 * @param location
		 */
		public void openBuildupLocation(String location,String zone){
			try{

				waitForSync(2);
				clickActionInHHT("btn_assignbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
				waitForSync(2);
				clickActionInHHT("btn_enterbuildupzone;xpath",proppathexportbuildup,"assign build up location",screenName);
				waitForSync(3);
				String zoneselected=getPropertyValue(proppathexportbuildup, "txt_selectzone;xpath");
				zoneselected=zoneselected.replace("*",data(zone));	
				androiddriver.findElement(By.xpath(zoneselected)).click();
				waitForSync(2);
				clickActionInHHT("txt_enterbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
				waitForSync(2);
				enterValueInHHT("txt_searchlocation;xpath",proppathexportbuildup,data(location),"ULD Number",screenName);
				waitForSync(2);
				String locator1=getPropertyValue(proppathexportbuildup, "txt_selectlocation;xpath");
				locator1=locator1.replace("*",data(location));	
				androiddriver.findElement(By.xpath(locator1)).click();
				waitForSync(2);
				clickActionInHHT("btn_done;xpath",proppathexportbuildup,"done button",screenName);
				waitForSync(3);
				waitTillMobileElementDisplay(proppathexportbuildup,"btn_close;xpath","xpath");
				clickActionInHHT("btn_close;xpath",proppathexportbuildup,"close button",screenName);
				waitForSync(1);	
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to select the shipment on " +screenName);
			}
		}
		/**
		 * @author A-9844
		 * @Desc verify invalid location is not displayed in the location tile
		 * @param awb
		 * @param location
		 * @param zone
		 */
		public void verifyErrorOnSelectingInvalidLocation(String awb,String location,String zone){
			try{

				waitForSync(2);
				String locator=getPropertyValue(proppathexportbuildup, "btn_selectawb;xpath");
				locator=locator.replace("*",data(awb));	
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(2);
				clickActionInHHT("btn_assignbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
				waitForSync(2);
				clickActionInHHT("btn_enterbuildupzone;xpath",proppathexportbuildup,"assign build up location",screenName);
				waitForSync(3);
				String zoneselected=getPropertyValue(proppathexportbuildup, "txt_selectzone;xpath");
				zoneselected=zoneselected.replace("*",data(zone));	
				androiddriver.findElement(By.xpath(zoneselected)).click();
				waitForSync(2);
				clickActionInHHT("txt_enterbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
				waitForSync(2);
				enterValueInHHT("txt_searchlocation;xpath",proppathexportbuildup,data(location),"ULD Number",screenName);
				waitForSync(2);
				String locator1=getPropertyValue(proppathexportbuildup, "txt_selectlocation;xpath");
				locator1=locator1.replace("*",data(location));	

				if(androiddriver.findElements(By.xpath(locator1)).size()==0){
					writeExtent("Pass", "Successfully verified system does not display the invalid location:" +data(location)+ " on " +screenName);
				}
				else{
					writeExtent("Fail", "Location " +data(location)+ " is displayed on " +screenName);
				}

				clickActionInHHT("btn_locationBack;xpath",proppathexportbuildup,"back button",screenName);
				waitForSync(3);

			}
			catch(Exception e){
				writeExtent("Fail", "Verified invalid location is not displayed in the location tile on " +screenName);
			}
		}


		/**
		 * @author A-10690
		 * @Desc To click view Details button
		 * @throws IOException
		 */
		public void selectViewDetailsBtn() throws IOException{
			clickActionInHHT("btn_ViewDetailsBtn;xpath",proppathexportbuildup,"view details button",screenName);
			waitForSync(3);
		}

		/**
		 * @author A-10690
		 * @Desc To verify the dimension details in export build up apk
		 * @param expected dimension
		 */

		public void verifyDimensionDetails(String Dimensions){

			waitForSync(2);
			String locator=getPropertyValue(proppathexportbuildup, "txt_DimDetails;xpath");
			locator=locator.replace("*",Dimensions);		        	        
			if(androiddriver.findElements(By.xpath(locator)).size()>0)
				writeExtent("Pass", "Sucessfully verified dimension details "+Dimensions+ " updated on " +screenName);					
			else					
				writeExtent("Fail", "Failed to verify dimension details   "+Dimensions+ "  on " +screenName);


		}

		/**
		 * @author A-10690
		 * @Desc To search an awb  in export build up apk
		 * @param awbnumber
		 */

		public void searchAWB(String awb) throws IOException{

			clickActionInHHT("btn_search;xpath",proppathexportbuildup,"search Button",screenName);
			waitForSync(3);
			enterValueInHHT("txt_awb;accessibilityId",proppathexportbuildup,data(awb),"AWB Number",screenName);
			waitForSync(3);
			Tapoutside();
		}

		/**
		 * @author A-10690
		 * @Desc To click close button in details page
		 * @param Fullawb number
		 * @throws IOException
		 */
		public void selectDimCloseBtn(String FullAWBNo) throws IOException{
			String locator=getPropertyValue(proppathexportbuildup, "btn_dimClose;xpath");
			locator=locator.replace("*",data(FullAWBNo));	
			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(3);
		}

	   /**
		 * @author A-9844
		 * @param awb
		 * @throws IOException
		 * Desc : Verify split shipment indicator ~ when awb is parted in multiple flights
		 */
		public void verifySplitIndicatorAcrossFlight(String awb) throws IOException
		{
			try{

				String locator=getPropertyValue(proppathexportbuildup, "txt_splitAcrossFlightIndicator;xpath");
				locator=locator.replace("AWB",data(awb));	
				waitForSync(2);
				if(androiddriver.findElements(By.xpath(locator)).size()==1){
					writeExtent("Pass", "Verified split indicator icon is displayed for the AWB "+data(awb)+" on "+screenName);
				}
				else{
					writeExtent("Fail", "Could not verify split indicator icon is displayed for the AWB "+data(awb)+" on "+screenName); 
				}
			}
			catch (Exception e) {
				writeExtent("Fail", "Could not verify the split indicator on "+screenName);
			}
		}
		/**
		 * @author A-9847
		 * @Desc To verify the Loading Priority
		 * @param loadingPrio
		 * @param SCC
		 */
		
		public void verifyLoadingPriority(String loadingPrio,String SCC ){
			
			try{
			 String locator=getPropertyValue(proppathexportbuildup, "txt_loadingPriority;xpath");
			 String actLoadingPrio=androiddriver.findElement(By.xpath(locator.replace("scc",data(SCC)))).getText();
		      System.out.println(actLoadingPrio);
		      
		      verifyScreenTextWithExactMatch(screenName,data(loadingPrio),actLoadingPrio, "Loading Priority", "Export Build up apk");
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify the Loading Priority on " +screenName);
			}
		
		
		}
		/**
		 * @author A-9844
		 * @param flightNum,splitPieces
		 * @Desc To select a particular task with pieces
		 * @param flightNum
		 */

		public void selectTaskCreated(String flightNum,String splitPieces){
			try{
				String locator=getPropertyValue(proppathexportbuildup, "txt_tasklistPieces;xpath");
				locator=locator.replace("*",data(flightNum));	
				locator=locator.replace("pieces",data(splitPieces));	
				androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(3);
				writeExtent("Pass", "Successfully selected the task with pieces as " +data(splitPieces)+" on "+ screenName);	
				System.out.println("Successfully selected the task with pieces as " +data(splitPieces)+" on "+ screenName);
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to select the task on " +screenName);
			}


		}
	   /**
		 * @author A-10690
		 * @Desc To click Loaded tab
		 * @throws IOException
		 */
		public void clickLoadedTab() throws IOException{
			clickActionInHHT("btn_loadedtab;xpath",proppathexportbuildup,"Loaded tab Button",screenName);
			waitForSync(5);
		}

		/**
		 * @author A-10690
		 * @Desc To click moveULD button
		 * @throws IOException
		 */
		public void selectMoveULDBtn() throws IOException{
			clickActionInHHT("btn_selectmoveuld;xpath",proppathexportbuildup,"move ULD button",screenName);
			waitForSync(5);
		}
		/**
		 * @author A-9844
		 * @param awb
		 * @throws IOException
		 * Desc : Click proceed
		 */
		public void verifySplitIndicator(String awb) throws IOException
		{
			try{

				String locator=getPropertyValue(proppathexportbuildup, "txt_splitIndicator;xpath");
				locator=locator.replace("AWB",data(awb));	
				waitForSync(2);
				if(androiddriver.findElements(By.xpath(locator)).size()==1){
					writeExtent("Pass", "Verified split indicator icon is displayed for the AWB "+data(awb)+" on "+screenName);
				}
				else{
					writeExtent("Fail", "Could not verify split indicator icon is displayed for the AWB "+data(awb)+" on "+screenName); 
				}
			}
			catch (Exception e) {
				writeExtent("Fail", "Could not verify the split indicator on "+screenName);
			}
		}




	/**
		 * @author A-9844
		 * @desc To select the task status-COMPLETED,PENDING,IN PROGRESS from the drop down
		 * @param expStatus
		 * @throws IOException
		 */
		public void selectTaskStatuses(String expStatus) throws IOException{


			clickActionInHHT("btn_allTaskStatuses;xpath",proppathexportbuildup,"All task statuses dropdown","Export build up apk");
			waitForSync(3);
			String locator=getPropertyValue(proppathexportbuildup, "drp_taskStatus;xpath");
			locator=locator.replace("*",data(expStatus));	
			androiddriver.findElement(By.xpath(locator)).click();

			int height=androiddriver.manage().window().getSize().getHeight();
			int width=androiddriver.manage().window().getSize().getWidth();

			int x=(int) (width*0.5);
			int y=(int) (height*0.5);
			new TouchAction(androiddriver).longPress(x, y).release().perform();
			waitForSync(3);

		}





	/**
		 * @author A-9844
		 * @Desc allocate the shipment and handling the popup on assigning the shipments into ULD
		 * @param num
		 * @param uldNum
		 * @throws IOException
		 */
		public void allocateShipmentToUld(String num,String uldNum) throws IOException{

			try{
				String locator=getPropertyValue(proppathexportbuildup, "txt_moveShipment;xpath");
				locator=locator.replace("*",data(uldNum)).replace("number", num);	
				String locator2=getPropertyValue(proppathexportbuildup, "btn_save;xpath");


				if(androiddriver.findElements(By.xpath(locator)).size()>0)
				{
					clickActionInHHT("btn_continue;xpath",proppathexportbuildup,"Continue Button",screenName);
					waitForSync(4);		
					if(androiddriver.findElements(By.xpath(locator2)).size()>0)
					{

						clickActionInHHT("btn_save;xpath",proppathexportbuildup,"Shipment saved successfuly",screenName);
					}

					String locator1=getPropertyValue(proppathexportbuildup, "tasklist_shipmentAdded;xpath");
					while(androiddriver.findElements(By.xpath(locator1)).size()!=1)
					{
						waitForSync(1);
					}
					clickActionInHHT("tasklist_shipmentAdded;xpath",proppathexportbuildup,"Shipment Added to ULD Sucessfully",screenName);
					waitForSync(5);
				}  
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to move the shipment into uld on " +screenName);
			}
		}


		 /**
		 * @author A-9844
		 * @desc To enter AWB/Uld details in the find section
		 * @param awbNo
		 * @throws IOException
		 */
		public void enterAWBorULDDetails(String awbNo) throws IOException{


			clickActionInHHT("btn_findAWBorULD;xpath",proppathexportbuildup,"Find AWB/ULD search box","Export build up apk");
			waitForSync(3);
			enterValueInHHT("inbx_AWBNo;xpath",proppathexportbuildup,data(awbNo),"AWB Number",screenName);
			waitForSync(3);

			int height=androiddriver.manage().window().getSize().getHeight();
			int width=androiddriver.manage().window().getSize().getWidth();

			int x=(int) (width*0.5);
			int y=(int) (height*0.5);
			new TouchAction(androiddriver).longPress(x, y).release().perform();
			waitForSync(3);


		}


/**
		 * @author A-9844
		 * @Desc verify the allocation is present
		 * @param awbNo
		 * @throws IOException
		 */
		public void verifyAllocations(String awbNo) throws IOException
		{
			try{
				waitForSync(5);
				String locator=getPropertyValue(proppathexportbuildup, "txt_awbNo;xpath");
				locator=locator.replace("awb",data(awbNo));	
				String text=androiddriver.findElement(By.xpath(locator)).getText();
				System.out.println(text);
				waitForSync(3);

				if(text.equals(data(awbNo)))
				{
					writeExtent("Pass", "Verified the AWB Number " +data(awbNo)+" on"+screenName);
				}
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify the AWB present on  " +screenName);
			}

		}

		/**
		 * @author A-10690
		 * @Desc unassigning a ULD
		 * @param ULD
		 * @param Reason for unassigning
		 */
		public void UnassignULD(String ULD,String reason){
			try{

				selectShipment(ULD);
				selectMoveULDBtn();
				clickActionInHHT("btn_selectunassign;xpath",proppathexportbuildup,"Unassign from flight",screenName);
				waitForSync(2);
				clickActionInHHT("btn_selectunassignreason;xpath",proppathexportbuildup,"reason button",screenName);
				waitForSync(2);
				String locator1=getPropertyValue(proppathexportbuildup, "btn_selectreasonname;xpath");
				locator1=locator1.replace("*",data(reason));	
				androiddriver.findElement(By.xpath(locator1)).click();
				waitForSync(2);
				clickActionInHHT("btn_selectoffloaddone;xpath",proppathexportbuildup,"done button",screenName);
				waitForSync(7);
				clickActionInHHT("btn_closeunassignuld;xpath",proppathexportbuildup,"close button",screenName);
				waitForSync(1);
				writeExtent("Pass", "Successfully unassigned the ULD  " +data(ULD)+ " on " +screenName);		
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to unassign the ULD " +screenName);
			}
		}
	   /**
		 * @author A-10690
		 * @Desc To verify whether build up location got updated
		 * @param location
		 */

	
		   public void verifyAssigneddBuildupLocationinTasklistingscreen(String flightno,String assignedlocation ){
		   {
			   
			   
			waitForSync(3);
			scrollInMobileDevice(data(flightno));
			    String locator=getPropertyValue(proppathexportbuildup, "tasklist_assignedlocation;xpath");
				locator=locator.replace("*",data(flightno));
				locator=locator.replace("location",data(assignedlocation));
				if(androiddriver.findElements(By.xpath(locator)).size()>0)
					writeExtent("Pass", "new location  "+data(assignedlocation)+ " updated on " +screenName);					
				else					
					writeExtent("Fail", "new location  "+data(assignedlocation)+ " not updtade on " +screenName);
					 
				
		}
		   }
	
/**
* @author A-10690
* @Desc assigning a build up location
* @param awb
* @param location
*/
public void assignBuildupLocation(String awb,String location,String zone){
	  try{
		  
		  waitForSync(2);
			String locator=getPropertyValue(proppathexportbuildup, "btn_selectawb;xpath");
			locator=locator.replace("*",data(awb));	
			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(2);
			clickActionInHHT("btn_assignbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
			waitForSync(2);
			clickActionInHHT("btn_enterbuildupzone;xpath",proppathexportbuildup,"assign build up location",screenName);
			waitForSync(3);
			String zoneselected=getPropertyValue(proppathexportbuildup, "txt_selectzone;xpath");
			zoneselected=zoneselected.replace("*",data(zone));	
			androiddriver.findElement(By.xpath(zoneselected)).click();
			waitForSync(2);
			clickActionInHHT("txt_enterbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
			waitForSync(2);
			enterValueInHHT("txt_searchlocation;xpath",proppathexportbuildup,data(location),"ULD Number",screenName);
			waitForSync(2);
			String locator1=getPropertyValue(proppathexportbuildup, "txt_selectlocation;xpath");
			locator1=locator1.replace("*",data(location));	
			androiddriver.findElement(By.xpath(locator1)).click();
			waitForSync(2);
			clickActionInHHT("btn_done;xpath",proppathexportbuildup,"done button",screenName);
			waitForSync(3);
			waitTillMobileElementDisplay(proppathexportbuildup,"btn_close;xpath","xpath");
			clickActionInHHT("btn_close;xpath",proppathexportbuildup,"close button",screenName);
			waitForSync(1);
			writeExtent("Pass", "Successfully selected the shipment " +data(awb)+ " on " +screenName);		
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to select the shipment on " +screenName);
		}
}

	   /**
		 * @author A-9847
		 * @Desc To select the ULD type tab(AKE/BLK)
		 * @param uldType
		 */
		public void clickUldType(String uldType){
			
			 try{
				 
				 String locator=getPropertyValue(proppathexportbuildup, "tasklist_uldType;xpath");
				 androiddriver.findElement(By.xpath(locator.replace("*",data(uldType)))).click();
				 waitForSync(3);
				 writeExtent("Pass", "Clicked on the UldType tab on " +screenName);					
			
		}
			 catch(Exception e){
				 writeExtent("Fail", "Failed to click on UldType tab on " +screenName);
			 }
		
		}
		
		/**
		 * @author A-9847
		 * @Desc To select/unselect the Barrow toggle
		 * @throws IOException
		 */
		public void clickBarrow() throws IOException{
			  
			  clickActionInHHT("tasklist_barrow;xpath",proppathexportbuildup,"Barrow",screenName);
			  waitForSync(3);
		}
		
		/**
		 * @author A-9847
		 * @Desc To click on Done button and verify shipment added to the Uld successfully
		 * @throws IOException
		 */
		public void clickDoneAndVerifyShipmentAdded() throws IOException{	  
			clickActionInHHT("tasklistDone;xpath",proppathexportbuildup,"Done",screenName);
			
			 String locator1=getPropertyValue(proppathexportbuildup, "tasklist_shipmentAdded;xpath");
			 while(androiddriver.findElements(By.xpath(locator1)).size()!=1)
			 {
				 waitForSync(1);
			 }
			clickActionInHHT("tasklist_shipmentAdded;xpath",proppathexportbuildup,"Shipment Added to ULD Sucessfully",screenName);
			waitForSync(5);
				  
			  }
		
	   /**
		 * @author A-10690
		 * @Desc select anywhere outside the selected location
		  * @throws IOException 
		 */

		public void Tapoutside() throws IOException{

			
		
			int height=androiddriver.manage().window().getSize().getHeight();
			int width=androiddriver.manage().window().getSize().getWidth();

			int x=(int) (width*0.9);
			int y=(int) (height*0.9);
			new TouchAction(androiddriver).longPress(x, y).release().perform();
			waitForSync(3);
			
		
		
		
		}
		
		/**
		 * @author A-10690
		 * @Desc To verify group level instructions
		 * @param awb,expected instructions
		 * @throws IOException 
		 */

		public void verifygrouplevelinstrsuction(String awb,String instructions) throws IOException{

			clickActionInHHT("btn_search;xpath",proppathexportbuildup,"search Button",screenName);
			waitForSync(5);
			enterValueInHHT("txt_awb;accessibilityId",proppathexportbuildup,data(awb),"AWB Number",screenName);
			waitForSync(5);
			Tapoutside();
			clickActionInHHT("btn_groupinstructionicon;xpath",proppathexportbuildup,"instruction Button",screenName);
			waitForSync(7);
			String actText=getTextAndroid("txt_groupinstruction;xpath",proppathexportbuildup,"instructions",screenName);
			
	                if(actText.contains(instructions))
		{
				writeExtent("Pass", "Successfully verified group level instructions"+screenName);

			}
			else
			{
				writeExtent("Fail", "Failed to verify group level instructions "+screenName);	
			}
			
		Tapoutside();
			
		
		
		
		}
		
		/**
		 * @author A-10690
		 * @Desc To verify flight level instructions
		 * @param flightNum,instructions
		 * @throws IOException 
		 */

		public void verifyflightlevelinstrsuction(String flightNum,String instructions) throws IOException{
			
			
			waitForSync(10);
			  String locator=getPropertyValue(proppathexportbuildup, "btn_flightinstruction;xpath");
				locator=locator.replace("*",data(flightNum));
			WebElement el=androiddriver.findElement(By.xpath(locator));
		    org.openqa.selenium.Dimension dim = androiddriver.manage().window().getSize();
		    int height = dim.getHeight();
		    int width = dim.getWidth();
		    int x = width/2;
		    int top_y = (int)(height*0.80);
		    int bottom_y = (int)(height*0.20);
		    System.out.println("coordinates :" + x + "  "+ top_y + " "+ bottom_y);
		    TouchAction ts = new TouchAction(androiddriver);
		    ts.longPress(el).moveTo(x, bottom_y).release().perform();
		    writeExtent("Pass","Scrolled till page down");
		    waitForSync(7);
		    String actText=getTextAndroid("txt_flightinstruction;xpath",proppathexportbuildup,"instructions",screenName);

			if(actText.contains(instructions))
			{
				writeExtent("Pass", "Successfully verified flight level instructions"+screenName);

			}
			else
			{
				writeExtent("Fail", "Failed to verify flight level instructions "+screenName);	

			}
			
			waitForSync(3);
			Tapoutside();
			


			
		
		
		
		}
		
		/**
		 * @author A-10690
		 * @Desc To verify shipment level instructions
		 * @param awb,instructions
		 * @throws IOException 
		 */
	public void verifyShipmentlevelinstrsuction(String awb,String instructions) throws IOException{

		waitForSync(4);
		 String locator=getPropertyValue(proppathexportbuildup, "btn_shipmentinstruction;xpath");
			locator=locator.replace("*",data(awb));
	       androiddriver.findElement(By.xpath(locator)).click();
	       waitForSync(4);
			String actText=getTextAndroid("txt_groupinstruction;xpath",proppathexportbuildup,"instructions",screenName);

			if(actText.contains(instructions))
			{
				writeExtent("Pass", "Successfully verified shipment level instructions"+screenName);

			}
			else
			{
				writeExtent("Fail", "Failed to verify shipment level instructions "+screenName);	

			}
			
			waitForSync(3);
			Tapoutside();
			
		
		}

	/**
	 * @author A-10690
	 * @Desc To select a particular task after searching with an awb
	 * @param flightNum
	 */

	  public void selectTaskCreatedaftersearching(String flightNum){
			try{
		    String locator=getPropertyValue(proppathexportbuildup, "tasklist_flightsearch;xpath");
			locator=locator.replace("*",data(flightNum));		        	        
			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(3);
			writeExtent("Pass", "Successfully selected the task on " +screenName);		
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to select the task on " +screenName);
			}
			
			
	}


	/**
		 * @author A-10690
		 * @Desc To verify remaining time for the flight to get closed
		 * @param flightNum,current time in CDG,STD
		 * @throws IOException
		 * @throws ParseException
		 */
		
		public void verifytime(String currenttime,String std,String flightNum) throws IOException, ParseException{
			
			 SimpleDateFormat sdf
	         = new SimpleDateFormat(
	             "HH:mm");
			 String s1=currenttime;
			

	         // parse method is used to parse
	         // the text from a string to
	         // produce the date
	    	 java.util.Date d1 =  sdf.parse(s1);
	    	 java.util.Date d2 =  sdf.parse(std);

	         // Calucalte time difference
	         // in milliseconds
	         long difference_In_Time
	             = d2.getTime() - d1.getTime();



	         long difference_In_Minutes
	             = (difference_In_Time
	                / (1000 * 60))
	               % 60;

	         long difference_In_Hours
	             = (difference_In_Time
	                / (1000 * 60 * 60))
	               % 24;

	         long totaltimeinmin=difference_In_Minutes+(difference_In_Hours*60);
	       
	        long expmaxlimit= totaltimeinmin+5;
	        long expmminlimit= totaltimeinmin-5;
	        
	         
	         
	         String locator=getPropertyValue(proppathexportbuildup, "tasklist_flightclosureRemainingTime");
				locator=locator.replace("*",data(flightNum));
				String acttext=androiddriver.findElement(By.xpath(locator)).getText();
				String hrs=acttext.split(" ")[0];
				String min=acttext.split(" ")[2];
	         long actualremainingtime=(Long.parseLong(hrs)*60)+Long.parseLong(min);
	         if(actualremainingtime<expmaxlimit && actualremainingtime>expmminlimit)
	         {
	        	 
	          
				writeExtent("Pass", "Successfully verified flight closure time"+screenName);

			}
			else
			{
				writeExtent("Fail", "Failed to verify flight closure time "+screenName);	
			}
	 }

	/**
	   * @author A-7271
	   * @param expColourCode
	   * @throws IOException
	   * Test : verify colour code of time elapse for flight closure
	   */
	   public void verifyColour(String expColourCode,String flightnum) throws IOException
	   {
	  	   String locator=getPropertyValue(proppathexportbuildup, "tasklist_flightclosureRemainingTime");
	  	 locator=locator.replace("*",data(flightnum));	
	   String actColourCode="";
	   MobileElement elem = (MobileElement) androiddriver.findElement(By.xpath(locator));

	   org.openqa.selenium.Point point = elem.getCenter();
	   int centerX = point.getX();
	   int centerY = point.getY();

	   File scrFile = ((TakesScreenshot)androiddriver).getScreenshotAs(OutputType.FILE);

	   BufferedImage image = ImageIO.read(scrFile);
	   // Getting pixel color by position x and y 
	   int clr = image.getRGB(centerX,centerY); 
	   int red   = (clr & 0x00ff0000) >> 16;
	   int green = (clr & 0x0000ff00) >> 8;
	   
	   
	   if(red>0)
	   
	          actColourCode="Red";
	   
	   else if(green>0)
	   
	          actColourCode="Green";
	   
	   else
	   
	          actColourCode="Yellow";
	   
	   verifyScreenTextWithExactMatch(screenName, expColourCode, actColourCode, "Verify colour code of time elapse for flight closure", "Export Build up apk");
	   
	  }
	   /**
		 * @author A-10690
		 * @Desc To verify whether task is present for the shipments inside a flight
		 * @param flightNum
		 * @throws IOException 
		 */

		public void verifytaskCreatedwrtAWB(String awb,String flight) throws IOException{

			clickActionInHHT("btn_search;xpath",proppathexportbuildup,"Continue Button",screenName);
			waitForSync(5);
			enterValueInHHT("txt_awb;accessibilityId",proppathexportbuildup,data(awb),"ULD Number",screenName);
			waitForSync(5);
			int height=androiddriver.manage().window().getSize().getHeight();
			int width=androiddriver.manage().window().getSize().getWidth();

			int x=(int) (width*0.5);
			int y=(int) (height*0.5);
			new TouchAction(androiddriver).longPress(x, y).release().perform();
			waitForSync(3);
			String locator=getPropertyValue(proppathexportbuildup, "tasklist_flight;xpath");
			locator=locator.replace("*",data(flight));		        	        
			if(androiddriver.findElements(By.xpath(locator)).size()>0)
				writeExtent("Pass", "Task is created for Flight Number "+data(flight)+ " on " +screenName);					
			else					
				writeExtent("Fail", "Task is not created for Flight Number "+data(flight)+ " on " +screenName);


		}
		
		
		/**
		 * @author A-9847
		 * @desc To enter tomorrows date
		 * @throws IOException
		 */
		public void enterTomorrowsDate(String month) throws IOException{


			String locator=getPropertyValue(proppathexportbuildup, "btn_datearrow;xpath");
			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(2);    

			clickActionInHHT("btn_tomorrowdate;xpath",proppathexportbuildup,"Add ULD Button",screenName); 
			clickActionInHHT("btn_current;xpath",proppathexportbuildup,"Add ULD Button",screenName); 
			waitForSync(3);

			waitForSync(2);             
			int height=androiddriver.manage().window().getSize().getHeight();
			int width=androiddriver.manage().window().getSize().getWidth();

			int x=(int) (width*0.5);
			int y=(int) (height*0.5);
			new TouchAction(androiddriver).longPress(x, y).release().perform();
			waitForSync(3);


		}
/**
	    * @author A-9847
	    * @Des To verify the pieces and weight
	    * @param flightNum
	    * @param pieces
	    * @param weight
	    */
	
	public void verifyPcsWgt(String flightNum,String pieces,String weight){
	   
	   try{
	   String locator=getPropertyValue(proppathexportbuildup, "tasklistpcswgt;xpath");
	   String act=androiddriver.findElement(By.xpath(locator.replace("*",data(flightNum)) )).getText();
       System.out.println(act);
       String pcs =act.split(" ")[0];
       System.out.println(pcs);
       String wgt =act.split(" ")[3].split("\\.")[0];
       System.out.println(wgt);
       
       if(pcs.equals(data(pieces)) && wgt.equals(data(weight)))
    	   writeExtent("Pass", "Successfully verified  pieces as "+data(pieces)+ " and weight as "+data(weight)+ " on " +screenName);					
		else					
			writeExtent("Fail", "Failed to verify pieces as "+data(pieces)+ "and weight as "+data(weight)+ " on "+screenName);
	   }
	   catch(Exception e){
		   writeExtent("Fail", "Failed to verify pieces and weight on " +screenName);
	   }
	   
	   
	}
	/**
	 * @author A-9847
	 * @Desc To select a particular task
	 * @param flightNum
	 */
	
	  public void selectTaskCreated(String flightNum){
			try{
		    String locator=getPropertyValue(proppathexportbuildup, "tasklist_flight;xpath");
			locator=locator.replace("*",data(flightNum));		        	        
			androiddriver.findElement(By.xpath(locator)).click();
			waitForSync(3);
			writeExtent("Pass", "Successfully selected the task on " +screenName);		
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to select the task on " +screenName);
			}
			
			
	}
	
	  /**
	   * @author A-9847
	   * @Desc Marking a selected Task as In progress
	   * @throws IOException
	   */
	  public void markTaskInProgress() throws IOException
	  {
          try{
          String locator=getPropertyValue(proppathexportbuildup, "tasklistText;xpath");
		    String text=androiddriver.findElement(By.xpath(locator)).getText();
		    System.out.println(text);

			if(text.contains("Do you wish to mark this task as 'In Progress'?"))
			{
		    writeExtent("Pass", "Popup with text '" +text+"' appeared on  " +screenName);
		    clickActionInHHT("tasklistOk;xpath",proppathexportbuildup,"OK Button",screenName);	  
			waitForSync(6);
			}
          }
          catch(Exception e){
          	writeExtent("Fail", "Failed to mark the task as 'In Progress' on  " +screenName);
          }
		  
		  }
	  
	  /**
	   * @author A-9847
	   * @desc To click on Add ULD
	   * @throws IOException
	   */
	  public void clickAddULD() throws IOException{
		  
		  clickActionInHHT("tasklistAddUld;xpath",proppathexportbuildup,"Add ULD Button",screenName); 
		  waitForSync(3);
	  }
	
	  /**
	   * @author A-9847
	   * @Desc To enter the UldNumber and Location on clicking on Add Uld
	   * @param loc
	   * @param uldNum
	   * @throws IOException
	   */
	public void enterUldNumAndLoc(String loc,String uldNum) throws IOException
	{
		
		enterValueInHHT("inbx_uldNum;accessibilityId",proppathexportbuildup,data(uldNum),"ULD Number",screenName);
		enterValueInHHT("inbx_location;accessibilityId",proppathexportbuildup,data(loc),"Location",screenName);
		
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To click on Done Button and Adding a Uld Sucessfully
	 * @throws IOException
	 */
public void clickDone() throws IOException{	  
	clickActionInHHT("tasklistDone;xpath",proppathexportbuildup,"Done",screenName);
	waitForSync(5);
	waitTillMobileElementDisplay(proppathexportbuildup,"txt_uldAddedSucessfully;xpath","xpath");
	clickActionInHHT("txt_uldAddedSucessfully;xpath",proppathexportbuildup,"Uld Details Added Sucessfully",screenName);
	handleULDWarning();
	waitForSync(5);
		  
	  }



/**
 * @author A-9847
 * @Desc To click Buildup Complete
 * @throws IOException
 */
public void clickBuildUpComplete() throws IOException{
	  
	  clickActionInHHT("btn_buildupComplete;xpath",proppathexportbuildup,"Build Up Complete",screenName);
	  waitForSync(3);
}
/**
 * @author A-10690
 * @Desc assigning a build up location
 * @param awb
 * @param location
 */
public void assignBuildupLocation(String awb,String location){
	  try{
		  
	  String locator=getPropertyValue(proppathexportbuildup, "btn_selectawb;xpath");
		locator=locator.replace("*",data(awb));	
		androiddriver.findElement(By.xpath(locator)).click();
		clickActionInHHT("btn_assignbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
		waitForSync(2);
		clickActionInHHT("txt_enterbuilduplocation;xpath",proppathexportbuildup,"assign build up location",screenName);
		waitForSync(2);
		enterValueInHHT("txt_searchlocation;xpath",proppathexportbuildup,data(location),"ULD Number",screenName);
		waitForSync(2);
		 String locator1=getPropertyValue(proppathexportbuildup, "txt_selectlocation;xpath");
			locator1=locator1.replace("*",data(location));	
			androiddriver.findElement(By.xpath(locator1)).click();
			waitForSync(2);
		clickActionInHHT("btn_done;xpath",proppathexportbuildup,"done button",screenName);
		waitForSync(3);
		clickActionInHHT("btn_close;xpath",proppathexportbuildup,"close button",screenName);
		waitForSync(1);
		writeExtent("Pass", "Successfully selected the shipment " +data(awb)+ " on " +screenName);		
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to select the shipment on " +screenName);
		}
}

/**
 * @author A-10690
 * @Desc To verify whether build up location got updated
 * @param location
 */

   public void verifyupdatedBuildupLocation(String location){
	
	    String locator=getPropertyValue(proppathexportbuildup, "txt_verifynewlocation;xpath");
		locator=locator.replace("*",data(location));		        	        
		if(androiddriver.findElements(By.xpath(locator)).size()>0)
			writeExtent("Pass", "new location  "+data(location)+ " updated on " +screenName);					
		else					
			writeExtent("Fail", "new location  "+data(location)+ " not updtade on " +screenName);
			 
		
}
/**
 * @author A-9847
 * @Desc To select a particular shipment
 * @param awb
 */
public void selectShipment(String awb){
	  try{
		  
	  String locator=getPropertyValue(proppathexportbuildup, "tasklist_selectshipment;xpath");
		locator=locator.replace("*",data(awb));	
		androiddriver.findElement(By.xpath(locator)).click();
		writeExtent("Pass", "Successfully selected the shipment " +data(awb)+ " on " +screenName);		
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to select the shipment on " +screenName);
		}
}
	  /**
	   * @author A-9847
	   * @Des To assign a shipment into an ULD
	   * @param uld
	   */
	  public void assignULD(String uld){
		  try{
			  
		   String locator=getPropertyValue(proppathexportbuildup, "tasklist_assignUld;xpath");
			locator=locator.replace("*",data(uld));	
			androiddriver.findElement(By.xpath(locator)).click();
			 waitForSync(5);
			writeExtent("Pass", "Successfully assigned the shipment into uld " +data(uld)+ " on " +screenName);
		  }
		  catch(Exception e){
			  writeExtent("Fail", "Failed to assign the shipment into uld "+data(uld)+" on " +screenName);
		  }
		  
	  }
	  
	  /**
	   * @author A-9847
	   * @Desc Handling the popup on assigning the shipments into ULD
	   * @param num
	   * @param uldNum
	   * @throws IOException
	   */
	  public void moveShipmentToUld(String num,String uldNum) throws IOException{
		  
		  try{
		 String locator=getPropertyValue(proppathexportbuildup, "txt_moveShipment;xpath");
		locator=locator.replace("*",data(uldNum)).replace("number", num);	
		if(androiddriver.findElements(By.xpath(locator)).size()>0)
		{
		clickActionInHHT("btn_continue;xpath",proppathexportbuildup,"Continue Button",screenName);
		 waitForSync(5);
		clickActionInHHT("txt_shipmentAddedSucessfully;xpath",proppathexportbuildup,"Shipment Added to ULD sucessfully",screenName);
		 waitForSync(3);
		}  
		  }
		  catch(Exception e){
			  writeExtent("Fail", "Failed to move the shipment into uld on " +screenName);
		  }
	  }
	  
	  /**
	   * @author A-9847
	   * @Desc Handling the 'To mark the task as Completed' popup
	   * @throws IOException
	   */
	  
	 public void markCompleted() throws IOException{
		 try{
			 
		 String locator=getPropertyValue(proppathexportbuildup, "txt_markCompleted;xpath");
		 String text=androiddriver.findElement(By.xpath(locator)).getText();
		 if(text.contains("mark as 'Completed'"))
		 {
		 clickActionInHHT("btn_yes;xpath",proppathexportbuildup,"Yes Button",screenName);
		 writeExtent("Pass", "Clicked on Yes for the popup '" + text+ "' on " +screenName); 
		 waitForSync(5);
		 }
		 else
			 writeExtent("Fail", "Could not click on Yes for the popup '" + text+ "' on " +screenName); 
		 }
		 catch(Exception e){
			 writeExtent("Fail", "Failed to mark as Completed on " +screenName); 
		 }
					
		 
		 
	 }
	 
	 /**
	  * @author A-9847
	  * @Desc To click Back arrow
	  * @throws IOException
	  */
public void clickBack() throws IOException{
	  clickActionInHHT("btn_back;xpath",proppathexportbuildup,"Back Button",screenName);
	  waitForSync(6);
}


/**
	 * @author A-9847
	 * @Desc To verify the Status as Pending/In progress/Completed
	 * @param flightNum
	 * @param expStatus
	 */
	public void verifyStatus(String flightNum,String expStatus ){
	
		try{
	
		 String locator=getPropertyValue(proppathexportbuildup, "taskliststatus;xpath");
		 String status=androiddriver.findElement(By.xpath(locator.replace("*",data(flightNum)) )).getText();
	      System.out.println(status);
	      if(status.equals(data(expStatus)))
	    	  writeExtent("Pass", "Successfully verified Status as "+data(expStatus)+ " for "+data(flightNum)+ " on " +screenName);					
			else					
				writeExtent("Fail", "Failed to verify Status as "+data(expStatus)+ " for "+data(flightNum)+ " on " +screenName);
				   
		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the Status on " +screenName);
		}
	
	
	}
	
	
	
	

}
