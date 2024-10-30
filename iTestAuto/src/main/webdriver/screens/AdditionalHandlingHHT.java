package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AdditionalHandlingHHT extends CustomFunctions {
	
	String sheetName = "AdditionalHandlingHHT";
	String screenName = "AdditionalHandlingHHT";
	

	public AdditionalHandlingHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht additional handling screen
	 */
	public void invokeAdditionalHandlingScreen() throws InterruptedException, AWTException {

			try
		{
				
		clickActionInHHT("addHandhht_menu;xpath",proppathhht,"Additional Handling menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Additional Handling hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Additional Handling hht screen is not invoked ");
		}
			
			
	}
	
	/**
	 * @author A-7271
	 * @param awb
	 * Desc : enter awb details
	 * @throws IOException 
	 */
	public void enterAwbDetails(String awb) throws IOException
	{
		
		enterValueInHHT("addHandhht_inbx_Awb;accessibilityId",proppathhht,data(awb),"Awb",screenName);
		waitForSync(2);
		
	}
	/**
	 * @author A-7271
	 * @param carrierCode
	 * @param flightNumber
	 * @param flightDate
	 * Desc:enter flight details
	 */
	public void enterFlightDetails(String carrierCode,String flightNumber,String flightDate)
	{
		try
		{
			enterValueInHHT("addHandhht_inbx_carrierCode;accessibilityId",proppathhht,data(carrierCode),"Carrier Code",screenName);
			enterValueInHHT("addHandhht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNumber),"Flight Number",screenName);
		if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("addHandhht_btn_flightDateNextDay;xpath",proppathhht,"Flight Date",screenName);	
		}
		
		clickActionInHHT("addHandhht_btn_Next;xpath",proppathhht,"Next",screenName);
		waitForSync(8);
		writeExtent("Pass", "Flight Details entered as flight Number : "+data(carrierCode)+data(flightNumber)+ " in "+screenName);
		}
		catch(Exception e)
		{
		writeExtent("Fail", "Could not enter flight details in "+screenName);
		}
	}
	/**
     * @author A-9478
     * @param awb
     * Desc : enter HAWB details
     * @throws IOException 
      */
     public void enterHAWB(String HAWB) throws IOException
     {
           
     enterValueInHHT("addHandhht_inbx_HAWB;accessibilityId",proppathhht,data(HAWB),"HAWB",screenName);
           waitForSync(2);
           
     }

	
	/**
	 * @author A-7271
	 * @param handlingType
	 * Desc: Select handling type
	 */
	public void selectHandlingType(String handlingType)
	{
		try
		{
			
			for(int i=0;i<2;i++)
			{
				clickActionInHHT("addHandhht_btn_selectHandlingType;xpath",proppathhht,"Select Handling Type",screenName);
			}
			waitForSync(3);
		//select handling type
		
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+data(handlingType)+"']")).click();	
		
		
		waitForSync(3);
		writeExtent("Pass", "Handling Type selected as : "+data(handlingType)+" in "+screenName);
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Could not select handling Type selected as : "+data(handlingType)+" in "+screenName);	
		}
	}
	
	/**
	 * @author A-7271
	 * @param endDate
	 * Desc: enter date
	 */
	public void selectDate(String startDate,String endDate)
	{
		
		try
		{
			clickActionInHHT("addHandhht_btn_calendarStartDate;xpath",proppathhht,"Calendar",screenName);
			waitForSync(2);
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+data(startDate)+"']")).click();
			clickActionInHHT("addHandhht_btn_OK;xpath",proppathhht,"OK",screenName);
			waitForSync(3);
			clickActionInHHT("addHandhht_btn_calendarEndDate;xpath",proppathhht,"Calendar",screenName);
			waitForSync(2);
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+data(endDate)+"']")).click();
			clickActionInHHT("addHandhht_btn_OK;xpath",proppathhht,"OK",screenName);
			waitForSync(3);
			writeExtent("Pass", "Start Date is selected as "+data(startDate)+" End Date is selected as : "+data(endDate)+" in "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Date could not be selected in "+screenName);
		}
	}
	
	/**
	 * @author A-7271
	 * @param pcs
	 * Desc: enter no of units
	 * @throws IOException 
	 */
	public void enterNoOfUnits(String pcs) throws IOException
	{
		
			enterValueInHHT("addHandhht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		
	}
	/**
	 * @author A-7271
	 * @param cusCode
	 * Desc: enter customer code
	 */
	public void enterCustomerCode(String cusCode)
	{
		
		try
		{
		scrollInMobileDevice("Customer Code");	
	
		
		clickActionInHHT("addHandhht_btn_CustomerCode;xpath",proppathhht,"Customer Code",screenName);
		
		
		waitForSync(10);
		for(int j=0;j<2;j++)
		{
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+data(cusCode)+"']")).click();
		
		}
		waitForSync(4);
		writeExtent("Pass", "Customer code selected as : "+data(cusCode)+" in "+screenName);
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter customer code in "+screenName);
		}
	}
	/**
	 * @author A-7271
	 * Desc : enter remarks
	 * @throws IOException 
	 */
	public void enterRemarks() throws IOException
	{
		
		scrollInMobileDevice("Remarks");	

		enterValueInHHT("addHandhht_inbx_Remarks;accessibilityId",proppathhht,"Add Handling Details","Remarks",screenName);
		
	}
	

	/**
	 * @author A-7271
	 * Desc : save details
	 * @throws IOException 
	 */
	public void saveDetails() throws IOException
	{
		
		    clickActionInHHT("btn_Save;xpath",proppathhht,"Save",screenName);
			waitForSync(8);
			verifyHHTSaveDetails(screenName);
	
	}
}

