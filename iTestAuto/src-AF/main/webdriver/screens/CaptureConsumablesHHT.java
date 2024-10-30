package screens;



import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.appium.java_client.MobileElement;

public class CaptureConsumablesHHT extends CustomFunctions {
	
	String sheetName = "CaptureConsumablesHHT";
	String screenName = "CaptureConsumablesHHT";
	

	public CaptureConsumablesHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Capture Consumables screen
	 * @throws IOException 
	 */
	public void CaptureConsumablesHHTScreen() throws InterruptedException, AWTException, IOException {
	
		scrollInMobileDevice("Capture Consumables");
		clickActionInHHT("cchht_menu;xpath",proppathhht,"Capture Consumables menu",screenName);
		waitForSync(5);
	}
	
	/**
	 * @author A-9175
	 * @param value
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering value in Capture Consumables screen
	 * @throws IOException 
	 */
	public void enterValue(String value) throws AWTException, InterruptedException, IOException
	{
			enterValueInHHT("cchht_inbx_Awb;xpath",proppathhht,value,"List Value",screenName);
			waitForSync(5); 
	}
	
	/**
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering value in Capture Consumables screen
	 * @throws IOException 
	 */
	public void clickNext() throws AWTException, InterruptedException, IOException
	{
		clickActionInHHT("cchht_btn_next;xpath",proppathhht,"Next Button",screenName);
		waitForSync(5); 
	}
	/**
     * @author A-9478
     * @throws AWTException
     * @throws InterruptedException
     * Description : Select Airline or GHA
     * @throws IOException 
      */
     public void selectAirlineOrGHA(String value) throws AWTException, InterruptedException, IOException
     {
           if(value.equalsIgnoreCase("Airline"))
           {
           if(androiddriver.findElements(By.xpath("//android.widget.TextView[@text='Airline']")).size()==0)
                 {
                 clickActionInHHT("cchht_btn_Airline;xpath",proppathhht,"Airline",screenName);
                 waitForSync(3); 
                 }
           }
           else if(value.equalsIgnoreCase("GHA"))
           {
           if(androiddriver.findElements(By.xpath("//android.widget.TextView[@text='GHA']")).size()==0)
                 {
                       clickActionInHHT("cchht_btn_Airline;xpath",proppathhht,"Airline",screenName);
                 }
                 waitForSync(3); 
           }
     }

	/**
	 * Desc : Selecting a Material
	 * @author A-9175
	 * @param materialType
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	
	public void selectMaterial(String materialType) throws InterruptedException, IOException
	{
		waitForSync(5);
		clickActionInHHT("cchht_btn_selectMaterial;xpath",proppathhht,"Material type Button",screenName);
		waitForSync(5);
		scrollInMobileDevice(data(materialType));
		String locator=getPropertyValue(proppathhht, "cchht_btn_materialType;xpath");
        locator=locator.replace("materialType", data(materialType));
        androiddriver.findElement(By.xpath(locator)).click();
        waitForSync(2);
        clickActionInHHT("cchht_btnOk_material;xpath",proppathhht,"Ok",screenName);
		waitForSync(5);
		
	}
	
	
	
	/**
	 * @author A-9175
	 * @param count
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering value in Capture Consumables screen
	 * @throws IOException 
	 */
	public void enterCount(String count) throws AWTException, InterruptedException, IOException
	{
			clearValueInHHT("cchht_inbx_totalPcs;accessibilityId",proppathhht,"List Value",screenName);
			waitForSync(5);
			enterValueInHHT("cchht_inbx_totalPcs;accessibilityId",proppathhht,data(count),"List Value",screenName);
			waitForSync(5);
	}
	
	

	/**
	 * Desc : Clicking Save
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws AWTException, InterruptedException, IOException
	{
		waitForSync(5);
		clickActionInHHT("cchht_btn_save;xpath",proppathhht,"Save",screenName);	
		waitForSync(12); 
		verifyHHTSaveDetails(screenName);
	}
	
		
}
