package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ULDServiceabilityDetailsHHT extends CustomFunctions {
	
	String sheetName = "ULDServiceabilityDetailsHHT";
	String screenName = "ULDServiceabilityDetailsHHT";
	

	public ULDServiceabilityDetailsHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht ULD Serviceability Details
	 */
	public void invokeULDServiceabilityScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("ULD Serviceability Details");	
		clickActionInHHT("uldser_menu;xpath",proppathhht,"Uld serviceability menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "ULD Serviceability Details hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "ULD Serviceability Details hht screen is not invoked successfully");
		}
	}
	/**
     * @author A-9478
     * Description : Select Party type
     * @throws InterruptedException 
      * @throws IOException 
      */
     public void selectPartyType(String partyType) throws InterruptedException, IOException
     {
           
           try
           {
           scrollInMobileDevice("Party Type");
           waitForSync(2);
           clickActionInHHT("uldser_btn_partyType;xpath",proppathhht,"Party Type",screenName);
           waitForSync(2);
     androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+partyType+"']")).click();
           waitForSync(2);
           writeExtent("Pass", "Selected party type as "+partyType+" in "+screenName);
           }
           
           catch(Exception e)
           {
                 captureScreenShot("Android");
                 writeExtent("Fail", "Could not select party type as "+partyType+" in "+screenName);
           }
           
     }
     /**
      * @author A-9478
      * Description : verify field names
      * @throws IOException 
       */
      public void verifyFieldNames(String fieldValue) throws IOException
      {
            try
            {
                  String locatorValue=getPropertyValue(proppathhht, "uldser_txt_fieldNames;xpath");
              locatorValue=locatorValue.replace("Field", fieldValue);
              if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
              {
                  writeExtent("Pass", "Verified field name "+fieldValue+" in "+screenName);
              }
              else
              {
                  scrollInMobileDevice(fieldValue);
                  if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
                    {
                        writeExtent("Pass", "Verified field name "+fieldValue+" in "+screenName);
                    }
                  else
                  {
                        captureScreenShot("Android");
                        writeExtent("Fail", "Failed to verify field name "+fieldValue+" in "+screenName);
                  }
                  
              }
            }
            catch(Exception e)
            {
                  captureScreenShot("Android");
                  writeExtent("Fail", "Failed to verify field name "+fieldValue+" in "+screenName);
            }
  

      }

	/**
	 * @author A-9478
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterULDNumber(String uldNumber) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("uldser_inbx_Uld;accessibilityId",proppathhht,data(uldNumber),"Uld Number",screenName);
			waitForSync(5);
		
		
		 
	}
	
	/**
	 * @author A-9478
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 */
	public void selectOverallStatusAndSeverity(String OverallStatus,String Severity) throws AWTException, InterruptedException
	{
		try
		{
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+OverallStatus+"']//preceding-sibling::android.view.ViewGroup//android.view.ViewGroup")).click();
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+OverallStatus+"']//preceding-sibling::android.view.ViewGroup//android.view.ViewGroup")).click();
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+Severity+"']//preceding-sibling::android.view.ViewGroup//android.view.ViewGroup")).click();
			waitForSync(12);
			writeExtent("Pass", "Overall status "+ data(OverallStatus)+" and Severity "+data(Severity)+" selected in "+screenName);
			
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Overall status "+ data(OverallStatus)+" and Severity "+data(Severity)+" not selected in "+screenName);
		}
		 
	}
	
	/**
	 * @author A-9478
	 * @param location
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered location
	 * @throws IOException 
	 */
	public void enterLocation(String Location) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("uldser_inbx_Location;accessibilityId",proppathhht,data(Location),"Location",screenName);
		 
	}
	

	/**
	 * @author A-9478
	 * Description : Select Damage Details and damage sub details
	 * @throws InterruptedException 
	 */
	public void selectDamageDetails(String DamageReason) throws InterruptedException
	{
		
		try
		{
		clickActionInHHT("uldser_btn_damageDetails;xpath",proppathhht,"Damage details",screenName);
		waitForSync(2);
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+DamageReason+"']//following-sibling::android.widget.TextView")).click();
		waitForSync(2);
		clickActionInHHT("uldser_btn_handles;xpath",proppathhht,"Handle",screenName);
		writeExtent("Pass", "Damage Reason "+DamageReason+" is selected "+" in "+screenName);
		}
		
		catch(Exception e)
		{
			 writeExtent("Fail", "Failed to select Damage reason "+DamageReason+" in "+screenName);
		}
		
	}
	
	
	/**
	 * @author A-9478
	 * Description : Select Point of notice
	 * @throws InterruptedException 
	 */
	public void selectPointOfNotice(String PON) throws InterruptedException
    {
          
          try
          {
                for(int i=0;i<2;i++)
                {
                clickActionInHHT("uldser_btn_PON;xpath",proppathhht,"Point of notice",screenName);
                waitForSync(4);   
                if(androiddriver.findElements(By.xpath("//android.widget.TextView[@text='"+PON+"']")).size()>=1)
                      {
                            break;
                      }
                }
          waitForSync(2);
    androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+PON+"']")).click();
          waitForSync(2);
          writeExtent("Pass", "Point Of Notice "+PON+" is selected "+" in "+screenName);
          }
          
          catch(Exception e)
          {
                writeExtent("Fail", "Failed to select Point of Notice "+PON+" in "+screenName);
          }
          
    }

	
	/**
	 * @author A-9478
	 * Description : Select Facility type
	 * @throws InterruptedException 
	 */
	public void selectFacilityType(String FacilityType) throws InterruptedException
	{
		
		try
		{
			clickActionInHHT("uldser_btn_facilityType;xpath",proppathhht,"Facility Type",screenName);
		waitForSync(2);
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+FacilityType+"']")).click();
		waitForSync(2);
		writeExtent("Pass", "Facility type "+FacilityType+" is selected "+" in "+screenName);
		}
		
		catch(Exception e)
		{
			 writeExtent("Fail", "Failed to select Facility type "+FacilityType+" in "+screenName);
		}
		
	}
	
	/**
	 * @author A-9478
	 * Description : Select Party type, Party
	 * @throws InterruptedException 
	 */
	public void selectPartyTypeAndParty(String partyType,String party) throws InterruptedException
	{
		
		try
		{
		scrollInMobileDevice("Party Type");
		waitForSync(2);
		clickActionInHHT("uldser_btn_partyType;xpath",proppathhht,"Party Type",screenName);
		waitForSync(2);
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+partyType+"']")).click();
		waitForSync(2);
		clickActionInHHT("uldser_btn_party;xpath",proppathhht,"Party",screenName);
		waitForSync(2);
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+party+"']")).click();
		waitForSync(2);
		writeExtent("Pass", "Selected party type as "+partyType+" and party as "+party+" in "+screenName);
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Could not select party type as "+partyType+" and party as "+party+" in "+screenName);
		}
		
	}
		
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	/**
     * @author A-9478
     * @throws AWTException
     * @throws InterruptedException
	 * @throws IOException 
     */
     public void clickSave() throws AWTException, InterruptedException, IOException
     {
           
                 clickActionInHHT("uldser_btn_save;xpath",proppathhht,"Save",screenName);     
                 waitForSync(4);
                 verifyHHTSaveDetails(screenName);
           
     }


	
}
