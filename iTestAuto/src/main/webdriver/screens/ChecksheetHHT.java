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
import common.WebFunctions;
import common.Xls_Read;
import io.appium.java_client.MobileElement;

public class ChecksheetHHT extends CustomFunctions {

	String sheetName = "ChecksheetHHT";
	String screenName = "ChecksheetHHT";
	public static String checksheetpath = "\\src\\resources\\Checksheet.properties";

	public ChecksheetHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}

	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Checksheet screen
	 * @throws IOException 
	 */
	public void invokeChecksheetHHTScreen() throws InterruptedException, AWTException, IOException {

		scrollInMobileDevice("Checksheet");
		clickActionInHHT("checksheethht_menu;xpath",proppathhht,"Checksheet menu",screenName);
		waitForSync(5);
	}
	/**
	 * @author A-8783
	 * Desc - Click on checksheet template and capture checksheet if mandatory
	 * @param templateName
	 * @param answer
	 * @throws InterruptedException
	 */
	public void captureChecksheetIfMandatory(String templateName, String answer) throws InterruptedException
	{
		waitForSync(5);
		{
			try
			{
				String locator=getPropertyValue(proppathhht, "checkhht_btn_testtemplate;xpath");
				locator=locator.replace("TemplateName", templateName);      
				int size = androiddriver.findElements(By.xpath(locator)).size();
				if(size==1)
				{
					waitForSync(2);
					androiddriver.findElement(By.xpath(locator)).click();
					writeExtent("Pass", "Clciked on checksheet template on " +screenName);
					waitForSync(2);
					captureCheckSheet(answer);
					clickSave();
				}
				else
				{
					writeExtent("Info", "Not Found Checksheet Template" +screenName);
				}


			}
			catch(Exception e)
			{
				writeExtent("Fail", "Could not Found Checksheet Template" +screenName);
			}


		}

	}
	public void captureCheckSheetWithMultiFormats() throws IOException

	{
		//Getting the number of checksheet templates displayed
		List<MobileElement> templates=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_btn_checksheetButton;xpath")));

		for(MobileElement temp:templates)
		{
			//Getting templates Questions Count
			String questionsCount= androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "buildUphht_btn_checksheetButton;xpath")+"//preceding-sibling::android.widget.TextView[contains(@text,'/')]")).getText();
			String Count=questionsCount.split("/")[1];

			//Getting on to each template
			temp.click();
			waitForSync(2);

			List<MobileElement>answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));
			List<MobileElement>answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
			List<MobileElement> textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));
			List<MobileElement>Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
			String RadioAnswers[]=getPropertyValue(checksheetpath, "RadioAnswers").split(",");
			captureChecksheetAnswer(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);

			/*** Inorder to Scroll till last Question of that template  **/

			String locatorValue=getPropertyValue(proppathhht, "gahht_txt_lastQuestion;xpath").replace("lastQues",Count);
			while(androiddriver.findElements(By.xpath(locatorValue)).size()!=1)
			{
				swipeAndroidScreen();

				answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));
				answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
				textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));
				Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));

				captureChecksheetAnswer(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);

			}

			androidScrolllTillPageDown();
			answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));
			answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
			textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));
			Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
			captureChecksheetAnswer(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);

			/*** *********************   *******************  ***/

			//Click OK after capturing each Checksheet template
			clickActionInHHT("buildUphht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);
			waitForSync(2);

		}
	}


	public void captureChecksheetAnswer( List<MobileElement> answers,List<MobileElement> textfields,List<MobileElement>answersRadioYes,List<MobileElement>Totalquestions,String [] RadioAnswers){


		//Yes/No Options
		for(MobileElement answer1:answers)
		{
			answer1.click();
			waitForSync(2);

			/*** Handling non-obligatory Questions ****/
			String noOption=getPropertyValue(proppathhht, "gahht_btn_NoOpt;xpath");
			String warning=getPropertyValue(proppathhht, "gahht_btn_Warning;xpath");
			if(androiddriver.findElements(By.xpath(warning)).size()!=0)
				androiddriver.findElement(By.xpath(noOption)).click();

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

			/*** Handling non-obligatory Questions ****/
			String noOption=getPropertyValue(proppathhht, "gahht_btn_NoOpt;xpath");
			String warning=getPropertyValue(proppathhht, "gahht_btn_Warning;xpath");
			if(androiddriver.findElements(By.xpath(warning)).size()!=0)
				androiddriver.findElement(By.xpath(noOption)).click();
		}


		//Handling the radio button with Answers
		for(int i=0;i<RadioAnswers.length;i++){
			String locator=getPropertyValue(proppathhht, "gahht_checksheet_radiobutton;xpath").replace("*",RadioAnswers[i]);
			if(androiddriver.findElements(By.xpath(locator)).size()==1)
				androiddriver.findElement(By.xpath(locator)).click();
			locator="";
		}

		/**	//Handling Obligatory Questions - No
String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestionschecksheet_BuildUp");
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
}	**/



	}

	/**
	 * @desc : Captureing checksheet answers
	 * @author A-9175
	 * @throws IOException
	 */
	public void captureCheckSheets() throws IOException
	{

		//Getting the number of checksheet templates displayed
		List<MobileElement> templates=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_checksheetButton;xpath")));	

		for(MobileElement temp:templates)
		{	

			//Getting templates Questions Count	
			String questionsCount= androiddriver.findElement(By.xpath(getPropertyValue(proppathhht, "gahht_btn_checksheetButton;xpath")+"//preceding-sibling::android.widget.TextView[contains(@text,'/')]")).getText();
			String Count=questionsCount.split("/")[1];


			temp.click();	
			waitForSync(3);
			List<MobileElement>answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_txt_chksheetyes;xpath")));
			List<MobileElement>answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesOption;xpath")));
			List<MobileElement> textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));	
			List<MobileElement>Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
			String RadioAnswers[]=getPropertyValue(checksheetpath, "RadioAnswerschecksheet").split(",");
			captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);


			//Inorder to Scroll till last Question of that template
			String locatorValue=getPropertyValue(proppathhht, "gahht_txt_lastQuestion;xpath").replace("lastQues",Count);
			if(androiddriver.findElements(By.xpath(locatorValue)).size()!=1)
			{
				scrollMobileDevice(locatorValue);

				answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_txt_chksheetyes;xpath")));
				answersRadioYes=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesOption;xpath")));
				textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));	
				Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));

				captureChecksheetAnswers(answers,textfields,answersRadioYes,Totalquestions,RadioAnswers);

			}


			//Click OK after capturing each Checksheet template
			clickActionInHHT("checkhht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);

			waitForSync(2);	
		}

	}



	/**
	 * @desc : captureChecksheetAnswers
	 * @author A-9175
	 * @param answers
	 * @param textfields
	 * @param answersRadioYes
	 * @param Totalquestions
	 * @param RadioAnswers
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
		String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestionschecksheet");		
		for(MobileElement quest:Totalquestions)
		{
			String text=quest.getText().replace("*","");
			if (ObgQuest.contains(text))
			{
				String loc=getPropertyValue(proppathhht, "gahht_obligatoryquestNo;xpath").replace("*", text);	
				//scrollMobileDevice(text);
				androiddriver.findElement(By.xpath(loc)).click(); 

			}
		}	

	}

	/**
	 * @Desc To enter the obligatory answer of checksheet as YES/NO based on questions
	 * @param answer
	 * @throws IOException
	 */
	public void captureCheckSheet(String answer) throws IOException
	{

		try{
			waitForSync(1); 
			String locator=getPropertyValue(proppathhht, "checkhht_txt_questions;xpath");
			List<MobileElement> ele=androiddriver.findElements(By.xpath(locator));
			System.out.println(ele.size());     
			for(MobileElement ele2:ele)
			{
				String text=ele2.getText();
				if(ele2.getText().equals(""))
				{

				}
				else if(ele2.getText().contains(answer))
				{ 
					String loc=getPropertyValue(proppathhht, "checkhht_txt_chksheetno;xpath");
					loc=loc.replace("*", text);
					androiddriver.findElement(By.xpath(loc)).click(); 
					writeExtent("Pass", "Checksheet details Successfully captured on  "+screenName);
				}
				else
				{
					try
					{
						String loc1=getPropertyValue(proppathhht, "checkhht_txt_chksheetyes;xpath");
						loc1=loc1.replace("*", text);
						androiddriver.findElement(By.xpath(loc1)).click(); 
						writeExtent("Pass", "Checksheet details Successfully captured on  "+screenName);
					}
					catch(Exception e)
					{

					}
				}
			}

			clickActionInHHT("checkhht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);      

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to capture checksheet details on "+screenName);
		}

	}


	/**
	 * @author A-9175
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering awb number in Check sheet hht Screen
	 * @throws IOException 
	 */
	public void enterValue(String value) throws AWTException, InterruptedException, IOException
	{

		enterValueInHHT("checkhht_inbx_Awb;accessibilityId",proppathhht,data(value),"List Value",screenName);
		waitForSync(5);


	}
	/**
	 * @Desc To enter the obligatory answer of checksheet as YES/NO based on questions
	 * @param answer
	 * @throws IOException
	 */
	public void captureCheckSheet(String... answer) throws IOException
	{

		try{
			waitForSync(1); 
			String locator=getPropertyValue(proppathhht, "checkhht_txt_questions;xpath");
			List<MobileElement> ele=androiddriver.findElements(By.xpath(locator));
			System.out.println(ele.size());     
			for(MobileElement ele2:ele)
			{
				for(int i=0;i<answer.length;i++)
				{
					String text=ele2.getText();
					if(ele2.getText().equals(""))
					{

					}
					else if(ele2.getText().contains(answer[i].toString()))
					{ 
						String loc=getPropertyValue(proppathhht, "checkhht_txt_chksheetno;xpath");
						loc=loc.replace("*", text);
						androiddriver.findElement(By.xpath(loc)).click(); 
						writeExtent("Pass", "Checksheet details Successfully captured on  "+screenName);
					}
					else
					{
						try
						{
							String loc1=getPropertyValue(proppathhht, "checkhht_txt_chksheetyes;xpath");
							loc1=loc1.replace("*", text);
							androiddriver.findElement(By.xpath(loc1)).click(); 
							writeExtent("Pass", "Checksheet details Successfully captured on  "+screenName);
						}
						catch(Exception e)
						{

						}
					}
				}
			}

			clickActionInHHT("checkhht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);      

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to capture checksheet details on "+screenName);
		}

	}
	/**
	 * To select the ULD Transaction type
	 * @param transaction
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void selectULDTransaction(String transaction) throws InterruptedException, IOException
	{
		waitForSync(5);
		clickActionInHHT("checkhht_btn_transactionTypeuld;xpath",proppathhht,"Transaction type",screenName);
		waitForSync(5);
		scrollInMobileDeviceToExactTextMatch(transaction);
		String locator=getPropertyValue(proppathhht, "checkhht_dropDown_transaction;xpath");
		locator=locator.replace("transaction", transaction);
		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(2);
		waitForSync(5);

	}
	/**
	 * To click on ULD tab
	 * @throws IOException
	 */

	public void clickULDTab() throws IOException{

		waitForSync(3);
		clickActionInHHT("checkhht_uldTab;xpath",proppathhht,"ULD Tab",screenName);
		waitForSync(3);
	}
	/**
	 * To enter the ULD number and click on Next Button
	 * @param uldnum
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void enterUldNum(String uldnum) throws AWTException, InterruptedException, IOException
	{

		enterValueInHHT("checkhht_inbx_Uld;accessibilityId",proppathhht,data(uldnum)," ULD number",screenName);
		waitForSync(5);

		clickActionInHHT("checkhht_btn_next;xpath",proppathhht,"Next button",screenName);
		waitForSync(6);


	}

	/**
	 * Desc : Selecting a transaction
	 * @author A-9175
	 * @param transaction
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void selectTransaction(String transaction) throws InterruptedException, IOException
	{
		waitForSync(5);
		clickActionInHHT("checkhht_btn_transactionType;xpath",proppathhht,"Transaction type",screenName);
		waitForSync(5);
		scrollInMobileDeviceToExactTextMatch(transaction);
		String locator=getPropertyValue(proppathhht, "checkhht_dropDown_transaction;xpath");
		locator=locator.replace("transaction", transaction);
		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(2);
		waitForSync(5);

	}

	/**
	 * @author A-9175
	 * Desc : Clicking on Check sheet Button
	 * @throws InterruptedException
	 */


	public void clickChecksheetTemplate(String templateName) throws InterruptedException
	{
		waitForSync(5);
		String locator=getPropertyValue(proppathhht, "checkhht_btn_testtemplate;xpath");
		locator=locator.replace("TemplateName", templateName);
		androiddriver.findElement(By.xpath(locator)).click();
		waitForSync(5);

	}
	/**
	 * Desc : Capturing check sheet details
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void captureChecksheet() throws InterruptedException, IOException
	{
		waitForSync(5);
		String locator=getPropertyValue(proppathhht, "checkhht_btn_manifestYes;xpath");
		List<MobileElement> elements=androiddriver.findElements(By.xpath(locator));

		for(MobileElement elemnt:elements)
		{


			elemnt.click();
			waitForSync(3);
		}
		clickActionInHHT("checkhht_btn_captureChecksheetOk;xpath",proppathhht,"Capture Checksheet Ok",screenName);

	}


	/**
	 * Desc : Clicking Save
	 * @author A-9175
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void clickSave() throws AWTException, InterruptedException
	{

		try
		{
			waitForSync(5);
			clickActionInHHT("checkhht_btn_captureCheckSheetSave;xpath",proppathhht,"Save",screenName);	
			waitForSync(12); 
			writeExtent("Pass", "Details Saved Successfully in "+screenName);
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Failed to save details in "+screenName);
		}

	}



}
