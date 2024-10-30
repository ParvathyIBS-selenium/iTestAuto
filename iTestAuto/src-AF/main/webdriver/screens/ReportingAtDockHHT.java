package screens;

import io.appium.java_client.MobileElement;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class ReportingAtDockHHT extends CustomFunctions {
	
	String sheetName = "ReportingAtDockHHT";
	String screenName = "ReportingAtDockHHT";
	public static String checksheetpath = "\\src\\resources\\Checksheet.properties";  

	public ReportingAtDockHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc : invoke Reporting at Dock screen
	 */
	public void invokeReportingAtDockScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Reporting at Dock");	
		clickActionInHHT("reportatdockhht_menu;xpath",proppathhht,"Reporting at Dock menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Reporting at Dock hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Reporting at Dock hht screen is not invoked successfully");
		}
	}

/**
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : click report dock 
	 * @throws IOException 
	 */
	public void clickReportDock() throws InterruptedException
	{
		waitForSync(5);
	try {
		int size=getSizeOfMobileElement("reportatdockhht_btn_reportDock;xpath",proppathhht);
		 if(size==1) {
			 String locator=getPropertyValue(proppathhht, "reportatdockhht_btn_reportDock;xpath");
		        androiddriver.findElement(By.xpath(locator)).click();
				waitForSync(5);
		 }
	}
	catch(Exception e) {
		
	}

		
	}
	/**
	 * @author A-10690
	 * @throws IOException
	 * Desc : Enter the seal numbe in reporting at dock hht screen
	 */
	public void enterSealNumbers(String sealNumber1,String sealNumber2) throws IOException
	{
		waitForSync(1);
		scrollInMobileDevice("Seal Number");
		waitForSync(1);
		enterValueInHHT("reportatdockhht_txt_sealnumber;xpath",proppathhht,data(sealNumber1)+","+data(sealNumber2),"Current dock",screenName);
		waitForSync(3);
	}

	/**
	 * @author A-10690
	 * @throws IOException
	 * Desc : Capturing multiple seal numbers
	 */
	public void captureMultipleSealNumbers() throws IOException
	{
		String aplha1="abc";
		String alpha2="efg";
		Random random = new Random(); 
		int rand5Digt = random.nextInt(9999);
		String rkey=aplha1+rand5Digt;
		String Secondkey=alpha2+rand5Digt;
		map.put("sealNumber1",rkey);
		map.put("sealNumber2",Secondkey);
		System.out.println(data("sealNumber1"));
		System.out.println(data("sealNumber2"));
	}

	/**
	 * @author A-10690
	 * @throws IOException
	 * Desc : Clicking yes button
	 */
	public void clickYes() throws IOException
	{
		waitForSync(2);
		clickActionInHHT("btn_Yes2;xpath",proppathhht,"yes button ",screenName);
		waitForSync(5);
	}
	/**
	 * @author A-9844
	 * @Desc To enter the obligatory answer of checksheet as YES/NO based on questions
	 * @param answer
	 * @throws IOException
	 */
	public void captureCheckSheet() throws IOException
	{

		try{
			waitForSync(1); 
			String locator=getPropertyValue(proppathhht, "reportatdockhht_txt_questions;xpath");
			List<MobileElement> ele=androiddriver.findElements(By.xpath(locator));
			System.out.println(ele.size());  
			
			if(ele.size()>0) {
				for(MobileElement ele2:ele)
				{
					String text=ele2.getText();
					if(ele2.getText().equals(""))
					{

					}
					else
					{
						try
						{
							String loc1=getPropertyValue(proppathhht, "reportatdockhht_txt_chksheetyes;xpath");
							loc1=loc1.replace("*", text);
							androiddriver.findElement(By.xpath(loc1)).click(); 
							writeExtent("Pass", "Checksheet details Successfully captured on  "+screenName);
						}
						catch(Exception e)
						{

						}
					}
				}

				clickActionInHHT("reportatdockhht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);      

			}
			
		}
		catch(Exception e){
			writeExtent("Info", "Check sheet details not configured in "+screenName);
		}

	}

	/**
	 * Desc: Verifying Dock Details
	 * @author A-9175
	 * @throws IOException
	 * @throws InterruptedException 
	 */

	public void verifyDockDetails(String tokenId, String servicePoint, String name,String vehicleNo) throws IOException, InterruptedException 
	{
		 String actTokenId=getTextAndroid("reportatdockhht_txt_TokenVal;xpath",proppathhht," TokenId",screenName);
         verifyValueOnPage(actTokenId, data(tokenId),"Verification of Token ID ", screenName, "Verification of Token ID");
         
         String actServicePoint=getTextAndroid("reportatdockhht_txt_ServicePointVal;xpath",proppathhht," ServicePoint",screenName);
         verifyValueOnPage(actServicePoint, data(servicePoint),"Verification of ServicePoint ", screenName, "Verification of ServicePoint");
         
         String actName=getTextAndroid("reportatdockhht_txt_drivername;xpath",proppathhht," Name",screenName);
         verifyValueOnPage(actName.toLowerCase(), data(name).toLowerCase(),"Verification of Name ", screenName, "Verification of Name");
         
         String actVehicleNo=getTextAndroid("reportatdockhht_txt_VehicleNo;xpath",proppathhht," Vehicle Number",screenName);
         verifyValueOnPage(actVehicleNo, data(vehicleNo),"Verification of Vehicle Number ", screenName, "Verification of Vehicle Number");
         
		
	}

		
	
	
	/**
	 * Des : Clicking No to Release dock confirmation
	 * @author A-9175
	 * @throws IOException
	 */
	public void dontReleaseDock() throws IOException
	{
		 waitForSync(3);
		clickActionInHHT("btn_No;xpath",proppathhht,"Release dock",screenName);
        waitForSync(5);
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
		
		   enterValueInHHT("reportatdockhht_inbx_tokenNo;accessibilityId",proppathhht,data(value),"Token No",screenName);
		   waitForSync(8);
		
		 
	}
	/**
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : capture check sheet
	 */
	public void clickSaveCaptureChecksheet() throws InterruptedException, IOException
	{
		try
	     {
	          int size=getSizeOfMobileElement("reportatdockhht_btn_checksheetSave;xpath",proppathhht);                             
	           if(size==1)
	           {
	               waitForSync(4);
	                  clickActionInHHT("reportatdockhht_btn_checksheetSave;xpath",proppathhht," Save Capture Checksheet ",screenName);
	               writeExtent("Pass", "Saved Checksheet Details" +screenName);
	               waitForSync(5);
	           }
	           else
	           {
	               writeExtent("Info", "Not Found Checksheet Details for save" +screenName);
	           }
	     }
	     catch(Exception e)
	        {
	           writeExtent("Info", "Could not find Checksheet Details for save" +screenName);
	        }


		}

	/**
	 * @author A-8783
	 * @Desc To enter the obligatory answer of checksheet as YES/NO based on questions
	 * @param answer
	 * @throws IOException
	 */
	public void captureCheckSheetIfMandatory() throws IOException
	{

		boolean found=true;

		String locator=getPropertyValue(proppathhht, "reportatdockhht_txt_questionsDisplayed;xpath");
		List<MobileElement> ele=androiddriver.findElements(By.xpath(locator));
		System.out.println(ele.size());  

		if(ele.size()==0)
		{
			found=false;

		}

		else{

			try
			{

				
				List<MobileElement>answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_txt_chksheetyes;xpath")));
				List<MobileElement>answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesOption;xpath")));
				List<MobileElement> textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));
				List<MobileElement>Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
				String RadioAnswers[]=getPropertyValue(checksheetpath, "RadioAnswers").split(",");
				captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);
				waitForSync(2);

				clickActionInHHT("reportatdockhht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);      
				waitForSync(2);
			}
			catch (Exception e) {
				writeExtent("Fail", "Failed to capture checksheet details on "+screenName);
			}
		}

		if(found==false){
			writeExtent("Info", "No checksheet details are configured on "+screenName);
		}



	}
	/**
	 * @param answers
	 * @param textfields
	 * @param answersRadioYes
	 * @param Totalquestions
	 * @param RadioAnswers
	 * @Desc -to handle multiple checksheet formats
	 */
		public void captureChecksheetAnswers( List<MobileElement> answers,List<MobileElement> textfields,List<MobileElement>answersRadioYes,List<MobileElement>Totalquestions,String [] RadioAnswers){

			//Yes/No Options
			for(MobileElement answer1:answers)
			{		
				answer1.click();
				waitForSync(2);		
			}

			//TextFields
			for(MobileElement text:textfields)
			{
				text.sendKeys("Yes");
				waitForSync(2);
			}

			//Yes/No/NA radiobuttons
			for(MobileElement answer2:answersRadioYes)
			{
				answer2.click();
				waitForSync(2);

			}

			//Handling the radio button with Answers
			for(int i=0;i<RadioAnswers.length;i++){
				String locator=getPropertyValue(proppathhht, "gahht_checksheet_radiobutton;xpath").replace("*",RadioAnswers[i]);	
				if(androiddriver.findElements(By.xpath(locator)).size()==1)
					androiddriver.findElement(By.xpath(locator)).click();
				locator="";
			}

			//Handling Obligatory Questions - No
			String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestions");		
			for(MobileElement quest:Totalquestions)
			{
				String text=quest.getText().replace("*","");
				if (ObgQuest.contains(text))
				{
					String loc=getPropertyValue(proppathhht, "gahht_obligatoryquestNo;xpath").replace("*", text);	
					if(androiddriver.findElements(By.xpath(loc)).size()!=1)
						scrollMobileDevice(text);
					androiddriver.findElement(By.xpath(loc)).click(); 


				}
			}	

		}
		/**
		 * @author A-10328
		 * @throws IOException
		 * Desc : Click save
		 */
		public void clickSave() throws IOException

		{

			clickActionInHHT("reportatdockhht_btn_save;xpath",proppathhht,"Save",screenName); waitForSync(3);

		}

	/**
	 * @author A-8783
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : click report dock 
	 * @throws IOException 
	 */
	public void clickReportDockMandatory() throws InterruptedException
	{
		waitForSync(5);
		String locator=getPropertyValue(proppathhht, "reportatdockhht_btn_reportDock;xpath");
        androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(5);
		
	}

	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : click next
	 */
	public void clickNext() throws IOException
	{
		clickActionInHHT("reportatdockhht_btn_next;xpath",proppathhht,"Next",screenName);
        waitForSync(8);
	}
	/**
	 * @author A-7271
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : enter current dock
	 * @throws IOException 
	 */
	public void enterCurrentDock(String value) throws AWTException, InterruptedException, IOException
	{
		
		   enterValueInHHT("reportatdockhht_inbx_currentDock;accessibilityId",proppathhht,data(value),"Current dock",screenName);
			waitForSync(3);
		
		 
	}
	
	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : Start the handling process
	 */
	public void start() throws IOException
	{
		 clickActionInHHT("reportatdockhht_btn_start;xpath",proppathhht,"Start",screenName);
         waitForSync(5);
	}
	
	
	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : complete the handling process
	 */
	public void complete() throws IOException
	{
		 waitForSync(7);
		 clickActionInHHT("reportatdockhht_btn_complete;xpath",proppathhht,"Complete",screenName);
         waitForSync(3);
	}
	
	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : Yes button
	 */
	public void releaseDock() throws IOException
	{
		clickActionInHHT("btn_Yes2;xpath",proppathhht,"Release dock",screenName);
        waitForSync(5);
	}
	
	
}

