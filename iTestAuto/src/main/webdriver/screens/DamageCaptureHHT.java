package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class DamageCaptureHHT extends CustomFunctions {
	
	String sheetName = "DamageCaptureHHT";
	String screenName = "DamageCaptureHHT";
	

	public DamageCaptureHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Damage Capture screen
	 */
	public void invokeDamageCaptureScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Damage Capture");	
		clickActionInHHT("damCaphht_menu;xpath",proppathhht,"Damage Capture menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Damage Capture hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Damage Capture hht screen is not invoked successfully");
		}
	}
	/**
	 * @author A-6260
	 * Desc- to enter awb and hawb number
	 * @param awbNumber
	 * @param hawbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterAwbAndHawbNumber(String awbNumber,String hawbNumber) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("damCaphht_inbx_Awb;accessibilityId",proppathhht,data(awbNumber),"Awb Number",screenName);
			waitForSync(3);
			enterValueInHHT("damCaphht_inbx_Hawb;accessibilityId",proppathhht,data(hawbNumber),"Awb Number",screenName);
			waitForSync(3);
			clickActionInHHT("damCaphht_btn_next;xpath",proppathhht,"Next button",screenName);
			waitForSync(5);
		
		 
	}
	/**
	 * @author A-9478
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterAwbNumber(String awbNumber) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("damCaphht_inbx_Awb;accessibilityId",proppathhht,data(awbNumber),"Awb Number",screenName);
			waitForSync(5);
			clickActionInHHT("damCaphht_btn_next;xpath",proppathhht,"Next button",screenName);
			waitForSync(12);
		
		 
	}
	
	/**
	 * @author A-9478
	 * @param pieces
	 * @param weight
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered pieces and weight
	 */
	public void enterPiecesAndWeight(String pieces,String weight) throws AWTException, InterruptedException
	{
		try
		{
			waitTillMobileElementDisplay(proppathhht, "damCaphht_inbx_Pcs;accessibilityId", "accessibilityId");
			enterValueInHHT("damCaphht_inbx_Pcs;accessibilityId",proppathhht,data(pieces),"Pieces",screenName);
			enterValueInHHT("damCaphht_inbx_Wt;accessibilityId",proppathhht,data(weight),"Weight",screenName);
		    writeExtent("Pass", "Entered pieces : "+data(pieces)+" weight : "+data(weight));
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Cound not enter the pieces and weight in "+screenName);
		}
	}
	
	/**
	 * @author A-9478
	 * Description : Select Damage Code
	 * @throws InterruptedException 
	 */
	public void selectDamageCode(String DamageCode) throws InterruptedException
	{
		
		try
		{
		
		for(int i=0;i<2;i++){
			clickActionInHHT("damCaphht_btn_damageCode;xpath",proppathhht,"Damage code",screenName);
		}
		
		waitForSync(5);
		scrollInMobileDevice(DamageCode);
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+DamageCode+"']")).click();
		waitForSync(1);
		clickActionInHHT("damCaphht_btn_OK;xpath",proppathhht,"OK",screenName);
		writeExtent("Pass", "Damage code "+DamageCode+" is selected "+screenName);
		}
		
		catch(Exception e)
		{
			 writeExtent("Fail", "Failed to select damage code "+DamageCode+screenName);
		}
		
		
	}
	/**
	 * @author A-6260
	 * Desc: to click on save and verify email notification po up is not dispalyed
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSaveWithoutEmailNotificationPopUp() throws AWTException, InterruptedException, IOException
	{
		
			clickActionInHHT("btn_Save;xpath",proppathhht,"Save",screenName);
			waitForSync(2);
			String locatorValue=getPropertyValue(proppathhht, "damCaphht_txt_emailNotification;xpath");
			try {
				androiddriver.findElement(By.xpath(locatorValue)).isDisplayed();
				 writeExtent("Fail", "Email notification pop up is disaplyed on "+screenName);

			} catch (Exception e) {

				writeExtent("Pass", "Email notification pop up is not disaplyed on "+screenName);
			}        
			waitForSync(5);
			verifyHHTSaveDetails(screenName);
		 
	}
	

/**
	 * @author A-9844
	 * Desc-Click yes on email notification pop up and save
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void acceptEmailNotificationPopUp() throws AWTException, InterruptedException, IOException
	{

		clickActionInHHT("btn_Save;xpath",proppathhht,"Save",screenName);
		waitForSync(3);

		String locatorValue=getPropertyValue(proppathhht, "damCaphht_btn_emailNotificationYes;xpath");
		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

		if(eleSize==1)
			clickActionInHHT("damCaphht_btn_emailNotificationYes;xpath",proppathhht,"pop up",screenName);
		waitForSync(2);

	}


/**
	 * @author A-6260
	 * Desc-Click yes on email notification pop up and save
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickYesOnEmailNotificationPopUp() throws AWTException, InterruptedException, IOException
	{
		
			clickActionInHHT("btn_Save;xpath",proppathhht,"Save",screenName);
			waitForSync(5);
			clickActionInHHT("damCaphht_btn_emailNotificationYes;xpath",proppathhht,"Save",screenName);
			waitForSync(5);
			verifyHHTSaveDetails(screenName);
		 
	}

	/**
	 * @author A-9478
	 * Description : Enter Package code,Damage Reason code
	 * @throws InterruptedException 
	 */
	public void enterPackageCodeDamageReasonCode(String PackageCode,String DamageReasonCode) throws InterruptedException
	{
		
		try
		{
			waitTillMobileElementDisplay(proppathhht, "damCaphht_btn_packageCode;xpath", "xpath");
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
	 * @author A-9478
	 * Description : Enter Point of notice
	 * @throws InterruptedException 
	 */
	public void enterPointOfNotice(String PON) throws InterruptedException
	{
		
		try
		{
		scrollInMobileDevice("Enter or Select");
		waitForSync(2);
		clickActionInHHT("damCaphht_btn_PON;xpath",proppathhht,"Point of notice",screenName);
		
		waitForSync(2);
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+PON+"']")).click();
		writeExtent("Pass", "Point of notice "+PON+" is entered "+screenName);
		}
		
		catch(Exception e)
		{
			 writeExtent("Fail", "Failed to enter point of notice "+PON+screenName);
		}
		
	}
	
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Click on HAWB
	 */
	public void clickOnHAWB(String HAWB) throws AWTException, InterruptedException
	{
		try
		{
			
			waitForSync(2);
			androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+data(HAWB)+"']")).click();
			waitForSync(10);
			writeExtent("Pass", "Clicked on HAWB "+data(HAWB)+" in "+screenName);
		
			
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Could not click on HAWB "+data(HAWB)+" in "+screenName);
		}
		 
	}

	
	/**
	 * @author A-9478
	 * Description : Enter remarks
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void enterRemarks(String remarks) throws InterruptedException, IOException
	{
		
		waitForSync(3);
		enterValueInHHT("damCaphht_inbx_Remarks;xpath",proppathhht,data(remarks),"Remarks",screenName);
		waitForSync(1);
		
		
	}
		
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws AWTException, InterruptedException, IOException
	{
		
			clickActionInHHT("btn_Save;xpath",proppathhht,"Save",screenName);
			waitForSync(5);
			verifyHHTSaveDetails(screenName);
		 
	}
	
}
