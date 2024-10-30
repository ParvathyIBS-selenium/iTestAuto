package screens;

import io.appium.java_client.MobileElement;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ServicePointAllocationHHT extends CustomFunctions {
	
	String sheetName = "ServicePointAllocationHHT";
	String screenName = "ServicePointAllocationHHT";
	

	public ServicePointAllocationHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc : invoke service point allocation screen
	 */
	public void invokeServicePointAllocationScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Service Point Allocation");	
		clickActionInHHT("servicepointhht_menu;xpath",proppathhht,"Service point allocation menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Service point allocation hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Service point allocation hht screen is not invoked successfully");
		}
	}
	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered service point name
	 * @throws IOException 
	 */
	public void enterServicePointName(String servicepoint) throws IOException 
	{

		enterValueInHHT("servicepointhht_servicepointnameValue;xpath",proppathhht,data(servicepoint),"Service Point",screenName);
		waitForSync(1);


	}
	/**
	 * @author A-9844
	 * Desc - Click dock
	 * @throws IOException
	 */
	public void clickDock() throws IOException {
		waitTillMobileElementDisplay(proppathhht, "servicepointhht_btn_dock;accessibilityId", "id");
		clickActionInHHT("servicepointhht_btn_dock;accessibilityId",proppathhht,"Dock",screenName);
		waitForSync(2);
	}


/**
	 * @author A-10328
	 * @param  token
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : selecting token from list
	 * @throws IOException 
	 */

public void clickTokenDropdown(String TokenId) throws AWTException, InterruptedException, IOException

	{
try
		
{
	
clickActionInHHT("servicepointhht_lst_Token;xpath",proppathhht,"Select Service Point",screenName);
waitForSync(2);
		

String locatorValue=getPropertyValue(proppathhht, "servicepointhht_txt_tokenitem;xpath");
locatorValue=locatorValue.replace("*", data(TokenId));  
waitForSync(2);


androiddriver.findElement(By.xpath(locatorValue)).click();
waitForSync(2);
writeExtent("Pass", "Successfully selected token as "+data(TokenId)+" in "+screenName);
		
}
catch(Exception e)
		
{
writeExtent("Fail", "Couldn't select token  as "+data(TokenId)+" in "+screenName);
		
}
	
}
/**
 * @author A-10328
 * @param vehicleType
 * Desc : Select vehicle Type
 */


public void verifyVehicleType(String VehicleType)
{
	try
	
{


String locatorValue=getPropertyValue(proppathhht, "sevicepointhht_txt_vehicletype;xpath");
locatorValue=locatorValue.replace("vehicleType", data(VehicleType));
	
String actText=androiddriver.findElement(By.xpath(locatorValue)).getText();
waitForSync(2);
verifyScreenTextWithExactMatch(screenName, data("VehicleType"),actText, "successfully verified volume field", "ServicePointAllocationHHT");
	
}
	
catch(Exception e){
		
writeExtent("Fail", "Failed to verify vehicle type "+screenName);
	
}


}

/**
 * @author A-10328
 * @param volume
 * Desc : verify volume
 */

public void verifyVolume(String volume)
{
	
	try
	{

String locatorValue1=getPropertyValue(proppathhht, "servicepointhht_txt_volume;xpath");
String locatorValue2=getPropertyValue(proppathhht, "servicepointhht_txt_vol;xpath");

String actText1=androiddriver.findElement(By.xpath(locatorValue1)).getText();
String actText2=androiddriver.findElement(By.xpath(locatorValue2)).getText();
String actText=actText1+" "+actText2;
System.out.println(actText);
waitForSync(2);
verifyScreenTextWithExactMatch(screenName, volume,actText, "successfully verified volume ", "ServicePointAllocationHHT");
	
}
catch(Exception e)
{
		
writeExtent("Fail", "Failed to verify volume  "+screenName);

}

}

/**
 * @author A-9844
 * @throws AWTException
 * @throws InterruptedException
 * Description : selecting service point from list
 * @throws IOException 
 */
public void clickselectServicePointDropdown() throws AWTException, InterruptedException, IOException
{
	try
	{
		clickActionInHHT("servicepointhht_selectServicePoint;xpath",proppathhht,"Select Service Point",screenName);
		waitForSync(2);
		clickActionInHHT("servicepointhht_servicePointName;xpath",proppathhht,"Select Service Point",screenName);
		waitForSync(2);
		writeExtent("Pass", "Successfully selected service point on "+screenName);
	}
	catch(Exception e)
	{
		writeExtent("Fail", "Couldn't select service point on "+screenName);
	}
}




/**
 * @author A-10328
 * @param volume
 * Desc : verify volume field
   *@throws IOException
 */

public void verifyvolumefield(String field) throws IOException

{
	
try
	
{
	

String fieldName=getPropertyValue(proppathhht, "servicepointhht_txt_volumefield;xpath");
	
String actText=androiddriver.findElement(By.xpath(fieldName)).getText();
System.out.println(actText);
waitForSync(2);
verifyScreenTextWithExactMatch(screenName, field,actText, "successfully verified volume field", "ServicePointAllocationHHT");
	
	

}
	
	catch(Exception e)

{
		


writeExtent("Fail", "Failed to verify volume field is present on "+screenName);
}


}


/**
 * @author A-10328
 * @param truck type
 * Desc : verify truck type field
   * @throws IOException
 */





public void verifytrucktypefield(String field) throws IOException
	

{
		

try
		

{
		
		

String fieldName=getPropertyValue(proppathhht, "servicepointhht_txt_trucktypefield;xpath");
		
String actText=androiddriver.findElement(By.xpath(fieldName)).getText();
System.out.println(actText);
waitForSync(2);
verifyScreenTextWithExactMatch(screenName, field,actText, "successfully verified truck type  field", "ServicePointAllocationHHT");



}
			
			
catch(Exception e)

{
				
				
writeExtent("Fail", "Failed to verify truck type  field is present on "+screenName);
			}
			
		
			
		
		
	}




	/**
	 * @author A-10690
	 * @throws IOException
	 * @throws InterruptedException
	 * Description : verify token numbers are displayed under counter section
	 * @throws IOException 
	 */
	public void verifyTokenNoIsDisplayedundercounter() throws IOException, InterruptedException{

		try
		{

			clickActionInHHT("servicepointhht_drp_countertokenNo;xpath",proppathhht,"Token Number Dropdown",screenName);
			waitForSync(2);

			int size=getSizeOfMobileElement("servicepointhht_text_tokenNo;xpath",proppathhht);                             
			if(size>=1)
			{
				writeExtent("Pass", "Verified token numbers are displayed in token dropdown  "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "No token gets displayed"+screenName);
			}


		}
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "No token gets created"+screenName);
		}


	}
	
	/**
	 * @author A-7271
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered token number in hht
	 * @throws IOException 
	 */
	public void enterToken(String value) throws AWTException, InterruptedException, IOException
	{
		
		   enterValueInHHT("servicepointhht_inbx_tokenNo;accessibilityId",proppathhht,data(value),"Token No",screenName);
			waitForSync(1);
		
		 
	}

/**@author A-10328
	* To verify and close the error message
	* @param errorMessage
	* @throws IOException
*/
	
	
	public void verifyErrorMessage(String errorMessage) throws IOException 


	{


		String locatorcloseBtn=getPropertyValue(proppathhht, "servicepointhht_closeerrormsg;xpath");
		locatorcloseBtn=locatorcloseBtn.replace("*",data(errorMessage));
		String locatorValue=getPropertyValue(proppathhht, "servicepointhht_txt_errorMessage;xpath");
		locatorValue=locatorValue.replace("*",  data(errorMessage));
		waitForSync(1);

		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

		if(eleSize==1)
		{
			writeExtent("Pass","Verified the error message: "+ data(errorMessage)+" in "+screenName);


			androiddriver.findElement(By.xpath(locatorcloseBtn)).click();
			waitForSync(8);
		}

		else
		{
			writeExtent("Fail","Could not verify the error message: "+ data(errorMessage)+" in "+screenName);
		}

	}


	/**
	 * @author A-8783
	 * Desc - Click counter
	 * @throws IOException
	 */
	public void clickCounter() throws IOException {
		 waitForSync(3);
			clickActionInHHT("servicepointhht_btn_counter;accessibilityId",proppathhht,"OK Button",screenName);
			waitForSync(5);
	}


/**
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : verify token numbers are displayed
	 * @throws IOException 
	 */
	public void verifyTokenNoIsDisplayed() throws IOException, InterruptedException{

		try
		{

			clickActionInHHT("servicepointhht_drp_tokenNo;xpath",proppathhht,"Token Number Dropdown",screenName);
			waitForSync(2);

			int size=getSizeOfMobileElement("servicepointhht_text_tokenNo;xpath",proppathhht);                             
			if(size>=1)
			{
				writeExtent("Pass", "Verified token numbers are displayed in token dropdown  "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "No token gets displayed"+screenName);
			}


		}
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "No token gets created"+screenName);
		}


	}
	/**
	 * Desc : Verifying generated token number got displayed
	 * @author A-9844
	 * @param tokenNo
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void verifyGeneratedTokenNo(String tokenNo) throws InterruptedException, IOException
	{
		String actText="";

		String locator=getPropertyValue(proppathhht, "servicepointhht_text_tokenNo;xpath");
		List <MobileElement> elements=androiddriver.findElements(By.xpath(locator));

		for(MobileElement elemnt:elements)
		{
			actText=elemnt.getText();
			System.out.println(actText);

			if(actText.equals(data(tokenNo))){
				writeExtent("Pass", "Verified token number as "+data(tokenNo)+".Actual value displayed is "+actText+" in " +screenName);
				break;

			}

			else{
				writeExtent("Fail", "Failed to verify token number as "+data(tokenNo)+". Actual value displayed is "+actText+" in " + screenName);
			}
		}


		clickActionInHHT("servicepointhht_btn_OK;xpath",proppathhht,"OK Button",screenName);
		waitForSync(2);

	}

	/**
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : verify Select Service Point dropdown is present
	 * @throws IOException 
	 */
	public void verifyselectServicePointDropdown() throws AWTException, InterruptedException, IOException
	{

		int size=getSizeOfMobileElement("servicepointhht_selectServicePoint;xpath",proppathhht);
		if(size==1)
		{
			writeExtent("Pass", "Sucessfully verified select service point dropdown is present"+screenName);
		}
		else
		{

			writeExtent("Fail", " Failed to verify select service point dropdown is present"+screenName);
		}

	}
	/**
	 * @author A-9844
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : verify Service Point Type is Dock
	 * @throws IOException 
	 */
	public void verifyServicePoints() throws IOException, InterruptedException{
		
		  try
          {
        	int size=getSizeOfMobileElement("servicepointhht_txt_servicePointType;xpath",proppathhht);                             
                if(size==1)
                {
                writeExtent("Pass", "Verified Dock is auto focussed in  "+screenName);
                }
                else
                {
                      captureScreenShot("Android");
                      writeExtent("Fail", "Dock is auto focussed on"+screenName);
                }
          }
          catch(Exception e)
          {
                captureScreenShot("Android");
                writeExtent("Fail", "Dock is not auto focussed on "+screenName);
          }

	}
	/**
	 * @author A-9844
	 * @param servicePoint
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : selecting service point from list
	 * @throws IOException 
	 */
	public void clickselectServicePointDropdown(String servicePoint) throws AWTException, InterruptedException, IOException
	{
		try
		{
			clickActionInHHT("servicepointhht_selectServicePoint;xpath",proppathhht,"Select Service Point",screenName);
			waitForSync(2);
			scrollInMobileDevice(data(servicePoint));	
			String locationDisplayed=getPropertyValue(proppathhht, "breakdownhht_displayedLocation;xpath");
			
			locationDisplayed=locationDisplayed.replace("*", data(servicePoint));
			androiddriver.findElement(By.xpath(locationDisplayed)).click();
			waitForSync(2);
			writeExtent("Pass", "Successfully selected service point on "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't select service point on "+screenName);
		}
	}



	/**
	 * @author A-7271
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered token number in hht
	 * @throws IOException 
	 */
	public void enterServicePoint(String value) throws AWTException, InterruptedException, IOException
	{
		
		   enterValueInHHT("servicepointhht_inbx_servicePoint;accessibilityId",proppathhht,data(value),"Service point",screenName);
			waitForSync(1);
		
		 
	}
	
	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : call forward
	 */
	public void callForward() throws IOException
	{
		 clickActionInHHT("servicepointhht_btn_callforward;xpath",proppathhht,"Call Forward",screenName);
         waitForSync(5);
         /*********************************************************************/
			// ADDED THE CODE FOR HANDLING DATA SAVED SUCCESSFULLY
			waitForSync(2);
			String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");
			locatorValue=locatorValue.replace("*", "Data Saved Successfully "); 

			if(androiddriver.findElements(By.xpath(locatorValue)).size()==1)
			{
				androiddriver.findElement(By.xpath(locatorValue)).click();
				waitForSync(2);
			}  
	}
	/**
	 * @author A-7271
	 * Description : Confirm if call forward is done
	 * @throws IOException 
	 */
	public void confirmIfCallForwarded() throws IOException
	{
		verifyHHTSaveDetails(screenName);
	}
	
	
	
	
	
}

