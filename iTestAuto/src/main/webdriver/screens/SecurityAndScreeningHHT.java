package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class SecurityAndScreeningHHT extends CustomFunctions {
	
	String sheetName = "SecurityAndScreeningHHT";
	String screenName = "SecurityAndScreeningHHT";
	

	public SecurityAndScreeningHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht acceptance screen
	 */
	public void invokeSecurityAndScreeningScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Security Screening");	
		clickActionInHHT("sechht_menu;xpath",proppathhht,"Security and screening menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "SecurityAndScreening hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "SecurityAndScreening hht screen is not invoked successfully");
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
	public void enterValue(String value) throws AWTException, InterruptedException, IOException
	{
		
		   enterValueInHHT("sechht_inbx_enterValue;accessibilityId",proppathhht,data(value),"List value",screenName);
			waitForSync(12);
		
		 
	}
	
	/**
	 * @author A-7271
	 * @param pcs
	 * Description : Enter screening details
	 */
	public void enterScreeningDetails(String pcs)
	{
		try
		{
	    enterValueInHHT("sechht_inbx_Pcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		scrollInMobileDevice("Enter Remarks");	
		enterValueInHHT("sechht_inbx_remarks;xpath",proppathhht,"Entered the screened pcs","Remarks",screenName);
		writeExtent("Pass", "Entered the screened pcs as  "+data(pcs)+ " in "+screenName);
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter the screened pcs in "+screenName);
		}
	}
	/**
	 * @author A-7271
	 * @param status
	 * Description : enter the screening status
	 */
	public void enterScreeningStatus(String status)
	{
		try
		{
			if(status.equals("Pass"))
			{
				clickActionInHHT("sechht_inbx_screeningStatusPass;xpath",proppathhht,"Screening Status",screenName);

			}
			else
			{
				clickActionInHHT("sechht_inbx_screeningStatusFail;xpath",proppathhht,"Screening Status",screenName);
			}
			writeExtent("Pass", "Screening status is marked as   "+status+ " in "+screenName);
		}

		catch(Exception e)
		{
			writeExtent("Fail", "Could not enter screening status in "+screenName);
		}

			
	}
	
	/**
	 * @author A-7271
	 * Description : Saving the screening details
	 * @throws IOException 
	 */
	public void saveScreeningDetails() throws IOException
	{
		
		clickActionInHHT("sechht_btn_Save;xpath",proppathhht,"Save",screenName);	
		waitForSync(10);
		verifyHHTSaveDetails(screenName);
	}
	
	/**
     * @author A-9478
     * @param status
     * Description : Select screening method
	 * @throws IOException 
     */
     public void selectScreeningMethod(String ScreeningMethod) throws IOException
     {
           try
           {
                 scrollInMobileDevice("Screening Method");
                 clickActionInHHT("sehht_btn_screeningMethod;xpath",proppathhht,"Screening Method",screenName);
                 waitForSync(3);
                 //Select Screening method
                 scrollInMobileDevice(ScreeningMethod);
                 String locatorValue=getPropertyValue(proppathhht, "sehht_btn_selectScreeningMethod;xpath");
                 locatorValue=locatorValue.replace("SCREENING", ScreeningMethod);
                 androiddriver.findElement(By.xpath(locatorValue)).click();
                 waitForSync(3);
                 writeExtent("Pass", "Screening method is selected as   "+ScreeningMethod+ " in "+screenName);                
           }

           catch(Exception e)
           {
        	   captureScreenShot("Android");
                 writeExtent("Fail", "Could not select screening method in "+screenName);
           }

                 
     }

/**
* @author A-9478
* @param status
* Description : Verifying the screening history
* @throws IOException 
*/
public void verifyScreeningHistoryDetails(String ScreeningMethod,String ScreeningStatus) throws IOException
{
     
     try
     {
           clickActionInHHT("sehht_btn_selectScreeningHistory;xpath",proppathhht,"Screening History",screenName);
           waitForSync(2);
           String locatorValue=getPropertyValue(proppathhht, "sehht_txt_screeningHistory;xpath");
           locatorValue=locatorValue.replace("SCREENING", ScreeningMethod);
           locatorValue=locatorValue.replace("STATUS", ScreeningStatus);
           int result = androiddriver.findElements(By.xpath(locatorValue)).size();
           if(result==1)
           {
                 writeExtent("Pass", "Screening History stamped with screening method "+ScreeningMethod+" in "+screenName);
           }
           else
           {
        	   captureScreenShot("Android");
                 writeExtent("Fail", "Screening History not stamped with screening method "+ScreeningMethod+" in "+screenName);
           }
     }     
     catch(Exception e)
     {
    	 captureScreenShot("Android");
           
           writeExtent("Fail", "Failed to verify screening history"+screenName);
     }
}

	
	/**
	 * @author A-7271
	 * @param status
	 * Description : Verifying the screening status
	 * @throws IOException 
	 */
	public void verifyScreeningStatus(String status) throws IOException
	{
		int screenStatus;
		String statusflag="";
		
		try
		{
		if(status.equals("Completed"))
		{
			screenStatus=getSizeOfMobileElement("sechht_inbx_screeningStatusCompleted;xpath",proppathhht);
		
			if(screenStatus==1)
			{
				statusflag="Pass";
			}
			else
			{
				statusflag="Fail";
			}
			writeExtent(statusflag, "Screening status stamped in"+screenName);
		}
		else
		{
			screenStatus=getSizeOfMobileElement("sechht_inbx_screeningStatusPending;xpath",proppathhht);
			if(screenStatus==1)
			{
				statusflag="Pass";
			}
			else
			{
				statusflag="Fail";
			}
			writeExtent(statusflag, "Screening status stamped in"+screenName);
		}
		}
		
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Screening status stamped in"+screenName);
		}
	}
	
	
	
	
}
