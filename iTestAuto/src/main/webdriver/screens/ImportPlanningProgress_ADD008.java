package screens;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ImportPlanningProgress_ADD008 extends CustomFunctions {
	public CustomFunctions customFuction;
	String sheetName = "ImportPlanningProgress_ADD008";
	String screenName = "Import Planning Progress";
	String screenId = "ADD008";

	public ImportPlanningProgress_ADD008(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}


	
	/**
	 * @author A-9847
	 * Desription : List the flight details
	 * @param carrierCode
	 * @param flightNumber
	 * @param fromDate
	 * @param toDate
	 * @throws Exception 
	 */
	public void EnterFlightDetails(String carrierCode,String flightNumber,String fromDate,String toDate) throws Exception
	{	
		enterValueInTextbox(sheetName, "inbx_carrierCode;name",data(carrierCode), "carrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name",data(flightNumber), "flightNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_fromdate;name",data(fromDate), "From date", screenName);
		enterValueInTextbox(sheetName, "inbx_fromtime;name","00:00", "From Time", screenName);
		enterValueInTextbox(sheetName, "inbx_todate;name",data(toDate), "To Date", screenName);
		enterValueInTextbox(sheetName, "inbx_totime;name","23:59", "To Time", screenName);
		
	}
	public ArrayList<String> calculateLPSTimeWithOutCustomerSlot(String ata,int bct)
	{
		ArrayList<String> lpsDetails =new ArrayList<String>();
		try
		{	
			String ciq=timeCalculation(ata, "HH:mm","MINUTE",Integer.parseInt(data("CIQ_Configtime")));
			String lps=timeCalculation(ciq,"HH:mm","MINUTE",-(bct));
			lpsDetails.add(lps);
			lpsDetails.add(createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
			return lpsDetails;
			}
			
			catch(Exception e)
			{
				return lpsDetails;
			}
		}
	/**
	 * @author A-7271
	 * @param timeSlots
	 * @param bct
	 * @param ata
	 * @param rampToPitMoveTime
	 * @return
	 * @throws java.text.ParseException
	 * @throws ParseException
	 * Desc : calculate LPS time with customer time slot
	 */
	public ArrayList<String> calculateLPSWithCustomerSlot(String timeSlots,int bct,String ata,int rampToPitMoveTime) throws java.text.ParseException, ParseException
	{
		
		ArrayList<String> lpsDetails =new ArrayList<String>();

		try
		{
			String slotTime=timeCalculation(ata, "HH:mm","MINUTE",(bct+rampToPitMoveTime));
			System.out.println(slotTime);
			boolean sameDayDelivery=true;

			String startTime="";
			String endTime="";


			boolean lpsFound=false;
			String lps="";
			LocalTime target=null;
			LocalTime target2=null;
			String firstSlotTime=timeConverter("HHmm","HH:mm",timeSlots.split(",")[0].split("-")[0]);
			int timeslots=timeSlots.split(",").length;
			String lastTimeSlot=timeConverter("HHmm","HH:mm",timeSlots.split(",")[timeslots-1].split("-")[1]);
			System.out.println(lastTimeSlot);


			for(int i=0;i<timeSlots.split(",").length;i++)
			{
				String startTime2="";
				String endTime2="";
				startTime=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i].split("-")[0]);
				endTime=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i].split("-")[1]);

				try
				{
					startTime2=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i+1].split("-")[0]); 
					endTime2=timeConverter("HHmm","HH:mm",timeSlots.split(",")[i+2].split("-")[1]); 

				}
				catch(Exception e)
				{

				}


				target = LocalTime.parse( slotTime ) ;
				target2 = LocalTime.parse( ata ) ;

				System.out.println(target);
				System.out.println(startTime);



				Boolean targetInZone = ( 
						target.isAfter( LocalTime.parse( startTime ) ) 
						&& 
						target.isBefore( LocalTime.parse( endTime ) ) 
						) ; 



				Boolean targetInZone2 = ( 

						target.isBefore( LocalTime.parse( startTime ) ) || target.equals( LocalTime.parse( startTime )) 


						) ; 

				Boolean targetInZone3 = ( 

						target.equals( LocalTime.parse( endTime )) 
						) ; 

				if(target2.isAfter( LocalTime.parse( lastTimeSlot )))
				{
					targetInZone2=false;
					targetInZone3=false;
					targetInZone=false;		
				}

				System.out.println(targetInZone);
				System.out.println(targetInZone2);

				if(targetInZone)
				{

					lps=timeCalculation(startTime, "HH:mm","MINUTE",-(bct));
					System.out.println(lps);
					lpsFound=true;
					break;


				}
				else if(targetInZone2)
				{
					lps=timeCalculation(startTime, "HH:mm","MINUTE",-(bct));
					System.out.println(lps);
					lpsFound=true;
					break;

				}
				else if(targetInZone3)
				{
					if(!endTime2.equals(""))
					{
						lps=timeCalculation(startTime2, "HH:mm","MINUTE",-(bct));
						System.out.println(lps);
						lpsFound=true;
						break;
					}

				}

			}
			if(!lpsFound)
			{
				lps=timeCalculation(firstSlotTime, "HH:mm","MINUTE",-(bct));
				System.out.println(lps);
				sameDayDelivery=false;
			}


			System.out.println("LPS TIME IS CALCULATED AS "+lps);
			if(sameDayDelivery)
			{
				System.out.println("LPS DATE IS CALCULATED AS "+createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));

				lpsDetails.add(lps);
				lpsDetails.add(createDateFormatWithTimeZone("dd-MMM-YYYY", 0, "DAY", ""));
				
			}
			else
			{

				System.out.println("LPS DATE IS CALCULATED AS "+createDateFormatWithTimeZone("dd-MMM-YYYY", 1, "DAY", ""));
				lpsDetails.add(lps);
				lpsDetails.add(createDateFormatWithTimeZone("dd-MMM-YYYY", 1, "DAY", ""));
			}
			
			
			return lpsDetails;
		}


		catch(Exception e)
		{
			return lpsDetails;
		}
		
	}


	/**
	 * @author A-7271
	 * @param ata
	 * @param eps
	 * @return
	 * Desc : calculate LPS time without time slot
	 */
	public String calculateLPSWithOutCustomerSlot(String ata,int eps)
	{
		try
		{
			String epsTime=timeCalculation(ata, "HH:mm","MINUTE",(eps));
			return epsTime;
		}

		catch(Exception e)
		{
			return "";
		}
	}
	/**
		 * @author A-7271
		 * @param timeSlot
		 * @param bct
		 * @param ata
		 * @param rampToPitMoveTime
		 * @throws java.text.ParseException
		 * @throws ParseException
		 * Desc : verify LPS time terminal shipment
		 */
		public void verifyLPSForTerminalShipment(int count,String timeSlot,int[] bct,String ata,int rampToPitMoveTime,int eps) throws java.text.ParseException, ParseException
		{
			
			
			List <String> lpsDetails=new ArrayList<String>();
			ArrayList <String> lpsCalculated=new ArrayList<String>();
			String [] timeStamp=new String[count];
			int k =0;
			try
			{

				if(timeSlot.equals("noTimeSlot"))
									
				{
					for(int i=0;i<count;i++){
						lpsDetails=calculateLPSTimeWithOutCustomerSlot(ata,bct[i]);
						System.out.println("LPS Time of shipment "+i+" is "+lpsDetails.get(0));
						System.out.println("LPS Date of shipment "+i+" is "+lpsDetails.get(1));

						lpsCalculated.add(lpsDetails.get(0));
						lpsCalculated.add(lpsDetails.get(1));

				}
					
					 System.out.println(lpsCalculated);
						for(int j=0;j<count*2;j=j+2){

							timeStamp[k]=lpsCalculated.get(j);
							System.out.println(timeStamp[k]);
							k++;
							
						}

				}
				
				
				else
				{
					for(int i=0;i<count;i++){
						lpsDetails=calculateLPSWithCustomerSlot(timeSlot,bct[i],ata,rampToPitMoveTime);
						System.out.println("LPS Time of shipment "+i+" is "+lpsDetails.get(0));
						System.out.println("LPS Date of shipment "+i+" is "+lpsDetails.get(1));

						lpsCalculated.add(lpsDetails.get(0));
						lpsCalculated.add(lpsDetails.get(1));

					}	
					   System.out.println(lpsCalculated);	
						for(int j=0;j<count*2;j=j+2){

							timeStamp[k]=lpsCalculated.get(j);
							System.out.println(timeStamp[k]);
							k++;
							
						}
					}
					
					
					waitForSync(5);
					String actLPSTime=sortTimeStamps(timeStamp, "lowset");
					
					//verify lps time
					String ele = xls_Read.getCellValue(sheetName, "txt_lpsTime;xpath");
					String lps=driver.findElement(By.xpath(ele)).getText();
					System.out.println(lps);
					waitForSync(5);
					verifyScreenTextWithExactMatch(sheetName, lps,actLPSTime , "LPS time displayed","LPS time displayed");
			}
			
			
			catch(Exception e)
			{
				writeExtent("Fail", "Could not verify LPS time");
		
			}

			
		}
		
			
	/**
	 * @author A-9844
	 * @Desc To verify the field LPS is present
	 */
	
	public void verifyFieldLPS(String expText){
		
		String locator= xls_Read.getCellValue(sheetName, "lbl_LPS;xpath");
		 By ele =By.xpath(locator);
		 String actText = driver.findElement(ele).getText();
		 System.out.println(actText);
		 
		 if(actText.contains(data(expText))){
				writeExtent("Pass", "Successfully verified the filed LPS on " +screenName);	 
		 }
		 
		 else{
			 writeExtent("Fail", "Failed to verify the filed LPS on " +screenName);
		 }
		
	}
/**
 * @author A-9847
 * Description... Click List Button	
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickList() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "btn_list;id", "List Button",screenName);
		waitForSync(5);
	}
	 
	/**
	 * @author A-9847
	 * @Desc To verify the flight
	 * @param flight
	 */
	public void verifyFlight(String flight) {
		By ele = getElement(sheetName, "txt_flight;xpath");
		String expFlight = driver.findElement(ele).getText();
		System.out.println(expFlight);
		verifyScreenText(sheetName, expFlight, data(flight), "Verify Flight", screenName);
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Plan Status
	 * @param status
	 */
	public void verifyPlanStatus(String status){
		try{
			String expstatus=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_planStatus;xpath"))).getText();
			System.out.println(expstatus);
			if(expstatus.equals(data(status)))
				writeExtent("Pass", "Successfully verified Plan Status as" +expstatus+" on " +screenName);	
			else
				writeExtent("Fail", "Failed to verify Plan Status as "+expstatus+" on " +screenName);
			}catch(Exception e){
				writeExtent("Fail", "Failed to verify the Plan Status on " +screenName);
			}
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the flight Pouch Icon is in received Status
	 */
	
	public void verifyFlightPouchIcon(){
		
		try{
		if(driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_flightPouchIcon;xpath"))).isDisplayed())
			writeExtent("Pass", "Successfully verified Flight Pouch Icon in Received Status on " +screenName);	
		else	
			writeExtent("Fail", "Failed to verify Flight Pouch Icon on " +screenName);
		}catch(Exception e){
			writeExtent("Fail", "Failed to verify Flight Pouch Icon in Received Status on " +screenName);
		}	
	}
	/**@author A-10330
	 * @Desc To click arrow
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	
	public void clickArrow() throws InterruptedException, IOException
	{
		waitForSync(3);
		clickWebElement(sheetName,"lbl_Arrow;xpath","click Arrow",screenName);
        waitForSync(2);
	}
    /**@author A-10330
	 * @Desc To select plan status
	 * @param:-status
	 * @throws InterruptedException
	 */
	public void selectPlanStatus(String status) throws InterruptedException
	{
		waitForSync(3);
		String locatorplanStatus=xls_Read.getCellValue(sheetName, "txt_span;xpath");
		String       planstatusoption=driver.findElement(By.xpath(locatorplanStatus)).getText();
		System.out.println(planstatusoption);
		if(planstatusoption.contains("--Select--"))
		{
			waitForSync(3);
			clickWebElementByWebDriver(sheetName, "span_arrow;xpath", "List planStatus",screenId);
			String locator=xls_Read.getCellValue(sheetName, "html_div;xpath");
			locator=locator.replace("*", status);
driver.findElement(By.xpath(locator)).click();
			
            waitForSync(2);
		}
	}

	/**@author A-9847
	 * @Desc To verify the FFM Indicator
	 */
	/**@author A-9847
	 * @Desc To verify the FFM Indicator
	 */
	public void verifyFFMIndicator(){
		
		
		try{
			if(driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_ffm;xpath"))).isDisplayed())
				writeExtent("Pass", "Successfully verified Green Tick Indicator under FFM on " +screenName);	
			else	
				writeExtent("Fail", "Failed to verify FFM Green Tick Indicator on " +screenName);
			}catch(Exception e){
				writeExtent("Fail", "Failed to verify FFM Green Tick on " +screenName);
			}	
		
		
		}
	
	
	
	
	

}
