package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class BreakdownListHHT  extends CustomFunctions {
	
	String sheetName = "BreakdownListHHT";
	String screenName = "BreakdownListHHT";
	

	public BreakdownListHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		} 
	
	
	/**
	 * To invoke the Breakdown List Screen
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void invokeBreakdownListHHTScreen() throws InterruptedException, AWTException, IOException {
		
		scrollInMobileDevice("Breakdown List");
		clickActionInHHT("breakdownlisthht_menu;xpath",proppathhht,"Breakdown List menu",screenName);
		waitForSync(5);
	}
	/**
	 * @author A-9844
	 * @Desc To Verify Breakdown progress is displayed against each ULD
	 * @param uldNo
	 * @param NoofAWBsBreakdown
	 * @param TotalAWBsPresent
	 * @param expText
	 */
	public void verifyBreakdownProgressStatus(String uldNo,String NoofAWBsBreakdown,String TotalAWBsPresent,String expText){

		try{

			String locator=getPropertyValue(proppathhht, "breakdownlisthht_inProgressStatus;xpath");
			locator=locator.replace("uld",data(uldNo));
			waitForSync(2);

			String Text=androiddriver.findElement(By.xpath(locator)).getText();
			waitForSync(2);

			String actText=Text.split("-")[0]+"-"+data(NoofAWBsBreakdown)+"/"+data(TotalAWBsPresent);      
			System.out.println(actText);


			if(actText.equals(data(expText)))
				writeExtent("Pass", "Successfully verified breakdown progress status "+ actText+" for "+data(uldNo)+" on"+screenName);
			else
				writeExtent("Fail", "Failed to verify breakdown progress status "+ actText+" for "+data(uldNo)+" on"+screenName);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to verify the breakdown status");
		}


	}

/**
	 * @author A-9844
	 * desc: To select the location from the list
	 */

	public void enterLocation(String loc) throws InterruptedException, AWTException, IOException {
		
		enterValueInHHT("breakdownlisthht_location;xpath",proppathhht,data(loc),"Location",screenName);
		String locationDisplayed=getPropertyValue(proppathhht, "breakdownlisthht_displayedLocation;xpath");
		
		locationDisplayed=locationDisplayed.replace("*", data(loc));
		androiddriver.findElement(By.xpath(locationDisplayed)).click();
		waitForSync(5);
   }

	/**
	 * To close the uld search
	 * @param uldNum
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void clickClose(String uldNum) throws AWTException, InterruptedException, IOException
	{
		String locator=getPropertyValue(proppathhht, "breakdownlisthht_close;xpath");
		locator=locator.replace("*",data(uldNum));
		waitForSync(3);
	}
	/**
	 * @author A-9844
	 * @Desc To click the ULD Radio Button and select the uld
	 * @throws IOException
	 */
	public void selectUld(String uldNo) throws IOException{
	
		String locator=getPropertyValue(proppathhht, "breakdownlisthht_selectULD;xpath");
        locator=locator.replace("*",data(uldNo));
        androiddriver.findElement(By.xpath(locator)).click();	            
		waitForSync(5);
	}
	/**
	 * @author A-9844
	 * @Desc To click Breakdown Option
	 * @throws IOException
	 */
	public void clickBreakDown() throws IOException{
	
	  clickActionInHHT("breakdownlisthht_breakdown;xpath",proppathhht,"Breakdown Button",screenName);   
	  waitForSync(5);
	}
	

	 /**
		 * @author A-9844
		 * @Desc To verify EPS Time
		 * @param ata
		 *
		 */
		
		public void verifyEPSTime(String ata){
			
			
			
			try{
				
				String actEPS="";
			String ExpEPS=timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("EPS_Configtime")));

			
			if(androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblCIQ;xpath"))).size()>0){
				 actEPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_EPSstartdatetimeMiddle;xpath").replace("Month",data("Month")+" "))).getText().split(" ")[1];
			}
			
			else{
				 actEPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_EPSstartdatetime;xpath").replace("Month",data("Month")+" "))).getText().split(" ")[1];
			}
			
			System.out.println(actEPS);
			if(ExpEPS.equals(actEPS))
				writeExtent("Pass", "Successfully verified EPS time as "+actEPS+" on "+screenName);
			else
				writeExtent("Fail", "Failed to verify EPS time as " +actEPS+ " on "+screenName+" where expected value is "+ExpEPS);

		}
			catch (Exception e) {
				writeExtent("Fail", "Failed to verify EPS time on "+screenName);
		}
		}
		/**
		 * @author A-9847
		 * @Desc To verify LPS time without Delivery slot configured for consignee
		 * @param ciq
		 * @param bct
		 * @param currdate
		 */
		public void verifyLPSTimeWithoutDeliverySlot(String ata,String bct,String currdate){
		
			try{
				String ActLPS="";
				String ciq=timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("CIQ_Configtime")));
				String LPS= timeCalculation(ciq, "HH:mm","MINUTE",-Integer.parseInt(data(bct)));
				String ExpLPS=data(currdate).split("-")[0]+"-"+data(currdate).split("-")[1]+" "+LPS;
				
				if(androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblCIQ;xpath"))).size()>0 && androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblEPS;xpath"))).size()>0)
					ActLPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_LPStime1;xpath").replace("Month",data("Month")+" "))).getText();			
				else if(androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblEPS;xpath"))).size()>0 && androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblCIQ;xpath"))).size()==0 )
					ActLPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_LPStime;xpath").replace("Month",data("Month")+" "))).getText();
				else if(androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblCIQ;xpath"))).size()>0 && androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblEPS;xpath"))).size()==0 )
					ActLPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_LPStime;xpath").replace("Month",data("Month")+" "))).getText();
				else
					ActLPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_LPSstartdatetime;xpath").replace("Month",data("Month")+" "))).getText();				 
				
				if(ExpLPS.equals(ActLPS))
					writeExtent("Pass", "Successfully verified LPS time as "+ActLPS+" on "+screenName);
				else
					writeExtent("Fail", "Failed to verify LPS time as " +ActLPS+ " on "+screenName+" where expected value is "+ExpLPS);


			}	catch(Exception e){
				writeExtent("Fail", "Failed to verify LPS time on "+screenName);
			}
		}


		/**
		 * @author A-9847
		 * @Desc To verify EPS time at CDG/AMS Stations
		 * @param ata
		 * @param currDate
		 * @param station
		 */
		
		public void verifyEPSTime(String ata,String currDate,String station){	

			if(data(station).equals("CDG")){		
				try{		   
					String actEPS="";
					if(androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblCIQ;xpath"))).size()>0)
						actEPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_EPSstartdatetimeMiddle;xpath").replace("Month",data("Month")+" "))).getText();
					else
						actEPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_EPSstartdatetime;xpath").replace("Month",data("Month")+" "))).getText();

					if(!actEPS.equals(""))
						writeExtent("Fail", "EPS time got displayed at CDG as " +actEPS+ " on "+screenName+" which is not expected");          	
					else	
						writeExtent("Pass", "Successfully verified EPS time is not getting displayed on "+screenName+" in CDG");
				}	

				catch (Exception e) {
					writeExtent("Pass", "Successfully verified EPS time is not getting displayed on "+screenName+" in CDG");
				}
			}

			else if(data(station).equals("AMS")){				
				try{				
					String actEPS="";
					String EPS=timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("EPS_Configtime")));
					String ExpEPS=data(currDate).split("-")[0]+"-"+data(currDate).split("-")[1]+EPS;

					if(androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "breakdownhht_lblCIQ;xpath"))).size()>0)
						actEPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_EPSstartdatetimeMiddle;xpath").replace("Month",data("Month")+" "))).getText();			
					else
						actEPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_EPSstartdatetime;xpath").replace("Month",data("Month")+" "))).getText();
					if(ExpEPS.equals(actEPS))
						writeExtent("Pass", "Successfully verified EPS time as "+actEPS+" on "+screenName);
					else
						writeExtent("Fail", "Failed to verify EPS time as " +actEPS+ " on "+screenName+" where expected value is "+ExpEPS);

				}	catch (Exception e) {
					writeExtent("Fail", "EPS time is not getting displayed on "+screenName);
				}

			}

		}	



	/**
	 * To verify All processed ULDs on a flight are present or not
	 * @param ulds
	 */
	public void verifyUldsPresent(String ulds[]){
	
		try{
		
			for(int i=0;i<ulds.length;i++){
		
		String locator=getPropertyValue(proppathhht, "breakdownlisthht_uld;xpath");
        locator=locator.replace("*",data(ulds[i]));
        scrollInMobileDeviceToExactTextMatch(data(ulds[i]));
        int n = androiddriver.findElements(By.xpath(locator)).size();
	
		 if(n>0)
			
				writeExtent("Pass", "ULD number "+data(ulds[i])+ " exists on " +screenName);
			
			else
			
				writeExtent("Fail", "ULD number "+data(ulds[i])+ " does not exists on " +screenName);
		}
        
		}
		catch(Exception e){
			
			writeExtent("Fail", "All processed ULDs doesnot exist on " +screenName);
		}
		}
	/**
	 * @author A-9847
	 * @Desc To unselect the given Handling Area
	 * @param handlingArea
	 */
	
	public void unselectHandlingArea(){
		
		 try{
				waitForSync(3);
				clickActionInHHT("breakdownlisthht_handlingareaicon;xpath",proppathhht,"Handling Area Icon",screenName);   
				waitForSync(4);
			    clickActionInHHT("breakdownlisthht_OK;xpath",proppathhht,"OK Button",screenName);  
				waitForSync(5);
				
				writeExtent("Pass", "Succesfully unselected the Handling Area on "+screenName);
				}catch(Exception e){
					writeExtent("Fail", "Failed to unselect the Handling Area on "+screenName);
				}	
		
	}	
	
	/**
	 * @author A-9847
	 * @Desc To select the given Handling Area
	 * @param handlingArea
	 */
	  public void selectHandlingArea(String handlingArea){
			
		  try{
				waitForSync(5);
				clickActionInHHT("breakdownlisthht_handlingareaicon;xpath",proppathhht,"Handling Area Icon",screenName);   
				waitForSync(5);
				String locator=getPropertyValue(proppathhht, "breakdownlisthht_handling_area;xpath");
		        locator=locator.replace("Handling",data(handlingArea));
		        androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(5);
				
				writeExtent("Pass", "Succesfully selected the Handling Area as "+data(handlingArea)+ " on "+screenName);
				}catch(Exception e){
					writeExtent("Fail", "Failed to select the Handling Area on "+screenName);
				}
		
		
	}
		
	  /**
		 * @author A-9847
		 * @Desc To verify the Handling Area selected on Breakdown List HHT screen
		 * @param handlingArea
		 */
		public void verifyHandlingAreaChanged(String handlingArea){
			
			try{
				
			String loc=getPropertyValue(proppathhht, "breakdownlisthht_handling_areaselected;xpath");
			String actualHandlingArea=androiddriver.findElement(By.xpath(loc)).getText();
			System.out.println(actualHandlingArea);
			
			 if(data(handlingArea).equals(actualHandlingArea))
			    	writeExtent("Pass", "Successfully verified Handling Area is selected on "+screenName);
			    else
			    	writeExtent("Fail", "Failed to verify the Handling Area Selection  on "+screenName);
				
			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify the Handling Area on "+screenName);
			}
			
			
			
			
		}
		

	/**
	 * Desc : Selecting a Breakdown Instruction
	 * @param bdInstruction
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	
	public void selectBDInstruction(String bdInstruction) throws InterruptedException, IOException
	{
		try{
			waitForSync(5);
			clickActionInHHT("breakdownlisthht_breakdowninstrctnicon;xpath",proppathhht,"Breakdown Instruction Icon",screenName);   
			waitForSync(5);
			scrollInMobileDeviceToExactTextMatch(data(bdInstruction));
			String locator=getPropertyValue(proppathhht, "breakdownlisthht_breakDown_instruction;xpath");
	        locator=locator.replace("Instruction",data(bdInstruction));
	        waitForSync(2);
	        androiddriver.findElement(By.xpath(locator)).click();
	        waitForSync(2);
			waitForSync(5);
			
			writeExtent("Pass", "Succesfully selected the Breakdown Instruction as "+data(bdInstruction)+ " on "+screenName);
			}catch(Exception e){
				writeExtent("Fail", "Failed to select the Breakdown Instruction on "+screenName);
			}
			
		}

	
	
	/**
	 * To click on Next Button On Breakdown List Screen
	 */

	public void clickNext() throws InterruptedException, AWTException, IOException {
	
	  clickActionInHHT("breakdownlisthht_next;xpath",proppathhht,"Next Button",screenName);
	   waitForSync(5);
   }
	/**
	 * @author A-9844
	 * desc: To click the more options
	 */

	public void clickMoreOptions() throws InterruptedException, AWTException, IOException {
	
	  clickActionInHHT("breakdownlisthht_moreOptions;xpath",proppathhht,"More Options Button",screenName);
	  waitForSync(2);
   }

/**
	 * @author A-9844
	 * desc: To click retrieve option
	 */

	public void clickRetrieveOption() throws InterruptedException, AWTException, IOException {
	
	  clickActionInHHT("breakdownlisthht_retrieveBtn;xpath",proppathhht,"Retrieve option",screenName);
	  waitForSync(2);
   }
	
	/**
	 * To enter the Uld number to search
	 * @param uldNum
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
   public void enterUld(String uldNum) throws AWTException, InterruptedException, IOException
		{
				enterValueInHHT("breakdownlisthht_inbx_uld;accessibilityId",proppathhht,data(uldNum),"Uld Number",screenName);
				waitForSync(6);
		}
	
	/**
	 * to verify start date and times is displayed against ULD
	 */
	public void verifystartDateTimesDisplayed(String ata){
		
		try{
			String ExpCIQ=createDateFormat("dd-MMM", 0, "DAY", "")+" "+timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("CIQ_Configtime")));
			String ExpEPS=createDateFormat("dd-MMM", 0, "DAY", "")+" "+timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("EPS_Configtime")));
			
			String ExpLPS=createDateFormat("dd-MMM", 0, "DAY", "")+" "+timeCalculation(data(ata),"HH:mm","MINUTE",270);
			
        String CIQ= androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_CIQstartdatetime;xpath"))).getText();
		String EPS= androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_EPSstartdatetime;xpath"))).getText();
		String LPS= androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_LPSstartdatetime;xpath"))).getText();
		
		if(ExpLPS.equals(LPS) && ExpEPS.equals(EPS) && ExpCIQ.equals(CIQ))
		writeExtent("Pass", "Successfully verified CIQ start date and time as "+CIQ+ " ,  EPS start date and time as "  +EPS+ " ,  LPS start date and time as "  +LPS+ " on "+screenName);
		else if(!ExpCIQ.equals(CIQ))
			writeExtent("Fail", "Failed to verify CIQ start date and time as "  +CIQ+ " on "+screenName+" where Expected value was "+ExpCIQ);
		else if(!ExpEPS.equals(EPS))
			writeExtent("Fail", "Failed to verify EPS start date and time as "  +EPS+" on "+screenName+" where Expected value was "+ExpEPS);
		else
			writeExtent("Fail","Failed to verify LPS start date and time as "  +LPS+ " on "+screenName+" where Expected value was "+ExpLPS );
		}
		
		catch(Exception e){
			
			writeExtent("Fail", "Failed to verify Start date and time on "+screenName);
		}
		
	}	

	/**
		 * Desc : Select/Unselect a Breakdown Status
		 * @param bdInstruction
		 * @throws InterruptedException
		 * @throws IOException 
		 */
		
		public void selectBDStatus(String bdStatus,boolean opt) throws InterruptedException, IOException
		{
			try{
			waitForSync(5);
			if(opt){			
			String locator=getPropertyValue(proppathhht, "breakdownlisthht_breakDownStatus;xpath");
	        locator=locator.replace("*",data(bdStatus));
	        androiddriver.findElement(By.xpath(locator)).click();	            
			waitForSync(5);
			
			writeExtent("Pass", "Succesfully selected the Breakdown Status as "+data(bdStatus)+ " on "+screenName);
			} 
			else
			{
				String locator=getPropertyValue(proppathhht, "breakdownlisthht_breakDownStatus;xpath");
		        locator=locator.replace("*",data(bdStatus));
		        androiddriver.findElement(By.xpath(locator)).click();	            
				waitForSync(5);
				
				writeExtent("Pass", "Succesfully unselected the Breakdown Status "+data(bdStatus)+ " on "+screenName);
			}
				
			}catch(Exception e){
				writeExtent("Fail", "Failed to select the Breakdown Status on "+screenName);
			}
			
		}
		
		/**
		 * @author A-9847
		 * @Desc To click the Breakdown Status Filter Icon
		 * @throws IOException
		 */
		public void clickBreakdownListFilter() throws IOException
		{
			clickActionInHHT("breakdownlisthht_filter;xpath",proppathhht,"Breakdown List Filter",screenName);   
			waitForSync(5);
			
		}
		
		/**
		 * @author A-9847
		 * @Desc To click the Ok Button
		 * @throws IOException
		 */
		public void clickOK() throws IOException{
		
		  clickActionInHHT("breakdownlisthht_OK;xpath",proppathhht,"OK Button",screenName);   
		  waitForSync(5);
		}
		
		/**
		 * @author A-9847
		 * @Desc To select the given flight
		 * @param flightNum
		 */
		public void selectFlight(String flightNum){
			try{
				
			String locator=getPropertyValue(proppathhht, "breakdownlisthht_flightNum;xpath");
	        locator=locator.replace("*",data(flightNum));
	        androiddriver.findElement(By.xpath(locator)).click();	            
			waitForSync(5);
			writeExtent("Pass", "Succesfully selected the flight Number as "+data(flightNum)+ " on "+screenName);
			}catch(Exception e){
				writeExtent("Fail", "Failed to select the flight on "+screenName);
			}
		}
		
		/**
		 * @author A-9847
		 * @Desc To verify the Breakdown Status Filter and its Values
		 */
		public void verifyBreakDownStatusFilter(){
		
			try{
				String locator=getPropertyValue(proppathhht, "breakdownlisthht_status;xpath");
				String locator1=getPropertyValue(proppathhht, "breakdownlisthht_filtervalueCompleted;xpath");
				String locator2=getPropertyValue(proppathhht, "breakdownlisthht_filtervalueInProgress;xpath");
				String locator3=getPropertyValue(proppathhht, "breakdownlisthht_filtervalueNotStarted;xpath");
				if(androiddriver.findElements(By.xpath(locator)).size()>0 && androiddriver.findElements(By.xpath(locator1)).size()>0  && androiddriver.findElements(By.xpath(locator2)).size()>0 && androiddriver.findElements(By.xpath(locator3)).size()>0 )    	
					writeExtent("Pass", "Successfully verified BreakDown Status Filter exists with values - Completed, In Progress, and Not Started  on " +screenName);	
				else
					writeExtent("Fail", "Failed to verify BreakDown Status Filter exists on " +screenName);	
			}catch(Exception e)	{

				writeExtent("Fail", "BreakDown Status Filter does not exists on " +screenName);	
			}


		}
          
		/**
		 * @author A-9847
		 * @Desc To verify the Status filters results
		 * @param opt
		 * @param uld
		 */
		
		public void verifyStatusFilters(boolean opt,String uld){

			
			if(opt){
				try{
					
				String locator=getPropertyValue(proppathhht, "breakdownlisthht_uld;xpath");
				locator=locator.replace("*",data(uld));		        	        
				int uldcount = androiddriver.findElements(By.xpath(locator)).size();	    	
				if(uldcount>0)				
					writeExtent("Pass", "ULD number "+data(uld)+ " sucessfully filtered on " +screenName);					
				else					
					writeExtent("Fail", "ULD number "+data(uld)+ " not filtered on " +screenName);
				
			
			}catch(Exception e){
				writeExtent("Fail", "Cannot find the ULD filtered on" +screenName);
			}
			
			}
			
			else{	
				
				try{
				String locator=getPropertyValue(proppathhht, "breakdownlisthht_uld;xpath");
				locator=locator.replace("*",data(uld));		        	        
				int uldcount = androiddriver.findElements(By.xpath(locator)).size();			    	
				if(uldcount>0)
					writeExtent("Fail", "ULD number "+data(uld)+ " got filtered " +screenName);					
				else
					writeExtent("Pass", "ULD number "+data(uld)+ " is not filtered on " +screenName);	
				
				
				}catch(Exception e){
					writeExtent("Fail", "Cannot find the ULD filtered on" +screenName);
				}
			}

			

		}
		/**
		 * 
		 * @param ata
		 * @param currdate
		 * Desc : verify SLA time
		 */
		public void verifySLATime(String ata,String currdate){

			try{
				String CIQ=timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("CIQ_Configtime")));
				String ExpCIQ = data(currdate).split("-")[0]+"-"+data(currdate).split("-")[1]+" "+CIQ;			

				String ActCIQ=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_CIQstartdatetime;xpath").replace("Month",data("Month")+" "))).getText();				 
				if(ExpCIQ.equals(ActCIQ))
					writeExtent("Pass", "Successfully verified SLA time as "+ActCIQ+" on "+screenName);
				else
					writeExtent("Fail", "Failed to verify SLA time as " +ActCIQ+ " on "+screenName+" where expected value is "+ExpCIQ);

			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify SLA time on "+screenName);
			}
		}

	/**
	 * To verify SLA(CIQ) time 
	 */
		
		public void verifySLATime(String ata){
			
			try{
				String ExpCIQ=timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("CIQ_Configtime")));
				
				

				String CIQ=androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "breakdownlisthht_CIQstartdatetime;xpath").replace("Month",data("Month")+" "))).getText().split(" ")[1];
				if(ExpCIQ.equals(CIQ))
					writeExtent("Pass", "Successfully verified SLA time as "+CIQ+" on "+screenName);
				else
					writeExtent("Fail", "Failed to verify SLA time as " +CIQ+ " on "+screenName+" where expected value is "+ExpCIQ);

			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify SLA time on "+screenName);
			}
		}

}


