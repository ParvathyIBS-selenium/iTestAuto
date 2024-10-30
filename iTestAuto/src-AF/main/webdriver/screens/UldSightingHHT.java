package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import java.util.ArrayList;
import java.util.List;
import io.appium.java_client.MobileElement;
import java.io.File;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;


public class UldSightingHHT extends CustomFunctions {
	
	String sheetName = "UldSightingHHT";
	String screenName = "UldSightingHHT";
	public static String checksheetpath = "\\src\\resources\\Checksheet.properties";

	public UldSightingHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	
	/**
	 * @desc: checksheet multiple format capture
	 * @throws IOException
	 */
	public void captureCheckSheetCDGSIGHT() throws IOException {
		// Getting the number of checksheet templates displayed

		List<MobileElement> answers = androiddriver
            .findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesTextOption;xpath")));
		List<MobileElement> answersRadioYes = androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_btn_yesRadioOption;xpath")));
				
		List<MobileElement> textfields = androiddriver
				.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));
		List<MobileElement> Totalquestions = androiddriver
				.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
		String RadioAnswers[] = getPropertyValue(checksheetpath, "RadioAnswerschecksheet").split(",");
		captureChecksheetAnswers(answers, textfields, answersRadioYes, Totalquestions, RadioAnswers);

		}

	/**
	 * @author A-7271
	 * @param uld
	 * @throws IOException
	 * Desc : enter uld number
	 */
	public void enterUldNumber(String uld) throws IOException
	{
		
		waitForSync(1);
		enterValueInHHT("inbx_uldNumber;accessibilityId",proppathuldsight,data(uld),"Uld Number",screenName);
		waitForSync(1);

		
	}
	/**
	 * @des: clickPrintTag
	 * @author A-9175
	 * @throws IOException
	 */
	public void clickPrintTag() throws IOException
	{
		clickActionInHHT("btn_printTag;xpath",proppathuldsight,"print Tag",screenName);
		waitForSync(5);

		try
		{
			int eleSize=getSizeOfMobileElement("txt_msgForFailedToPrint;xpath",proppathuldsight);

			if(eleSize==1)
			{
				writeExtent("Fail","Failed To Print message displayed");
				clickActionInHHT("btn_closemsgForFailedToPrint;xpath",proppathuldsight,"Failed To Print message displayed",screenName);
				waitForSync(1);
			}
			else
			{
				writeExtent("Pass","Confirmation message for Print Tag generation displayed sucessfully");

			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Confirmation message doesn not come for Print Tag");
		}
	}
	/**
	 * @desc : Captureing checksheet answers
	 * @author A-9844
	 * @throws IOException
	 */
	public void captureCheckSheets() throws IOException
	{

		List<MobileElement>answers=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "buildUphht_txt_chksheetyes;xpath")));
		List<MobileElement> textfields =androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "gahht_txt_textarea;xpath")));	
		List<MobileElement>Totalquestions=androiddriver.findElements(By.xpath(getPropertyValue(proppathhht, "checkhht_txt_questions;xpath")));
		captureChecksheetAnswers(answers,textfields,Totalquestions);

	}	

public void captureChecksheetAnswers(List<MobileElement> answers, List<MobileElement> textfields,
			List<MobileElement> answersRadioYes, List<MobileElement> Totalquestions, String[] RadioAnswers) {

		// Yes/No Options
		for (MobileElement answer1 : answers) {
			answer1.click();
			waitForSync(2);

			/*** Handling non-obligatory Questions ****/
			String noOption = getPropertyValue(proppathhht, "gahht_btn_NoOpt;xpath");
			String warning = getPropertyValue(proppathhht, "gahht_btn_Warning;xpath");

			if (androiddriver.findElements(By.xpath(warning)).size() != 0)
				androiddriver.findElement(By.xpath(noOption)).click();

		}

		// TextFields
		for (MobileElement text : textfields) {
			text.sendKeys("Yes");
			waitForSync(2);

		}

		// Yes/No/NA radiobuttons
		for (MobileElement answer2 : answersRadioYes) {
			answer2.click();
			waitForSync(2);
			/*** Handling non-obligatory Questions ****/
			String noOption = getPropertyValue(proppathhht, "gahht_btn_NoOpt;xpath");
			String warning = getPropertyValue(proppathhht, "gahht_btn_Warning;xpath");
			if (androiddriver.findElements(By.xpath(warning)).size() != 0)
				androiddriver.findElement(By.xpath(noOption)).click();

		}

		// Handling the radio button with Answers
		for (int i = 0; i < RadioAnswers.length; i++) {
			String locator = getPropertyValue(proppathhht, "gahht_checksheet_radiobutton;xpath").replace("*",
					RadioAnswers[i]);
			if (androiddriver.findElements(By.xpath(locator)).size() == 1)
				androiddriver.findElement(By.xpath(locator)).click();
			locator = "";

		}

		//Text filed value to be entered as numeric value
				String Quest= WebFunctions.getPropertyValue(checksheetpath, "NumericValueQuestion");		
				for(MobileElement quest:Totalquestions)
				{
					String text=quest.getText().replace("*","");
					if (Quest.equals(text))
					{

						String loc=getPropertyValue(proppathuldsight, "txt_textareaQuestion;xpath").replace("*", text);
						androiddriver.findElement(By.xpath(loc)).clear(); 
						androiddriver.findElement(By.xpath(loc)).sendKeys("200"); 
						waitForSync(2);
					}
				}
				
				//Handling Obligatory Questions - No
				String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestionschecksheet_CDGSIGHT");		
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
	 * @desc : captureChecksheetAnswers
	 * @author A-9844
	 * @param answers
	 * @param textfields
	 * @param answersRadioYes
	 * @param Totalquestions
	 * @param RadioAnswers
	 */
	public void captureChecksheetAnswers( List<MobileElement> answers,List<MobileElement> textfields,List<MobileElement>Totalquestions){

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


		//Text filed value to be entered as numeric value
		String Quest= WebFunctions.getPropertyValue(checksheetpath, "NumericValueQuestion");		
		for(MobileElement quest:Totalquestions)
		{
			String text=quest.getText().replace("*","");
			if (Quest.equals(text))
			{

				String loc=getPropertyValue(proppathuldsight, "txt_textareaQuestion;xpath").replace("*", text);
				androiddriver.findElement(By.xpath(loc)).clear(); 
				androiddriver.findElement(By.xpath(loc)).sendKeys("200"); 
				waitForSync(2);
			}
		}

		//Handling Obligatory Questions - No
		String ObgQuest= WebFunctions.getPropertyValue(checksheetpath, "ObligatoryQuestionschecksheet_CDGSIGHT");		
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
	 * Desc : Verifying uld doesn't exists in the list
	 * @author A-9844
	 * @param expText
	 * @param uldNum
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void verifyUldNotExists(String expText,String uldNum) throws InterruptedException, IOException
	{

		try{
		
			String actAlertText=getPropertyValue(proppathuldsight, "txt_alertText;xpath");
			actAlertText=actAlertText.replace("*", expText);

			int eleSize=androiddriver.findElements(By.xpath(actAlertText)).size();

			if(eleSize==1)
			{
				writeExtent("Pass","Verified the uld "+data(uldNum)+ " is not present in the list on "+screenName);
				String locatorCloseButton=getPropertyValue(proppathuldsight, "btn_close;xpath");
				locatorCloseButton=locatorCloseButton.replace("*", expText);
				androiddriver.findElement(By.xpath(locatorCloseButton)).click();
				waitForSync(1);
				clickActionInHHT("btn_closeAlert;xpath",proppathuldsight,"button close alert",screenName);
				waitForSync(1);
			}

			else
			{
				writeExtent("Fail","ULD  "+data(uldNum)+ " is present in the list,not removed from the list on sighting complete on "+screenName);
			}
		}catch (Exception e) {
			writeExtent("Fail","Failed to verify the  uld  is present in the list on "+screenName);
		}

	}


	/**
	 * @author A-10690
	 * @Des To close the weight capture pop up
	 * @throws IOException
	 */

	public void clickCloseBtnForCaptureWeight() throws IOException{

		waitForSync(2);
		clickActionInHHT("btn_closecaptureWt;xpath",proppathuldsight,"close Capture weight pop up",screenName);
		waitForSync(2);

	}
	/**
	 * @author A-10690
	 * @param expColourCode
	 * @param expected overhang category
	 * @throws IOException
	 * Test : verify expected overhang category is selected in uldsighting app
	 */
	public void verifyOverhangCategorySelected(String expColourCode,String overhangcategory) throws IOException
	{

		String locator=getPropertyValue(proppathuldsight, "btn_OverhangCategory;xpath");
		locator=locator.replace("*",data(overhangcategory));	
		
		WebElement el1= androiddriver.findElement(By.xpath(locator));
		MobileElement elem = (MobileElement)(el1);
		org.openqa.selenium.Point point = elem.getCenter();
		int centerX = point.getX();
		int centerY = point.getY();

		File scrFile = ((TakesScreenshot)androiddriver).getScreenshotAs(OutputType.FILE);

		BufferedImage image = ImageIO.read(scrFile);
		// Getting pixel color by position x and y 
		int clr = image.getRGB(centerX,centerY); 
		Color col = new Color(clr, true);
		int r = col.getRed();
		int g = col.getGreen();
		int b=col.getBlue();
		/***verifying background colour is not white or text colour  is not black.When category is selected background colour will be white/text colour will be black****/

		if(!(((r==255)&&(g==255)&&(b==255))|((r==0)&&(g==0)&&(b==0))))
		{
			writeExtent("Pass","verified the Category"+data(overhangcategory)+"selected on ULDSIGHTING APP" );
			System.out.println("verified the Category "+data(overhangcategory)+" selected on ULDSIGHTING APP" );
		}
		else
		{

			writeExtent("Fail"," Category"+data(overhangcategory)+" not selected on ULDSIGHTING APP");
		}

	}
	/**
	 * @author A-9175
	 * @Desc :verify Countours Listed
	 * @param contourExpected
	 */
	public void verifyCountoursListed(ArrayList<String> contourExpected) 
	{	
		ArrayList<String> contourActual=new ArrayList<String>();	
		List<MobileElement> contoursActual = androiddriver.findElements(By.xpath(getPropertyValue(proppathuldsight, "txt_listOfCountoursDisplayed;xpath")));
		for(MobileElement contour:contoursActual)
		{
			System.out.println(contour.getText());
			contourActual.add(contour.getText());
		}
		if (contourActual.containsAll(contourExpected))
		{
			writeExtent("Pass", "Sucessfully Verified contours Expected as "+contourExpected+" is present on "+screenName); 
		}
		else
		{
			writeExtent("Fail", "Failed to verify Verified contours Expected as "+contourExpected+" is present on "+screenName);
		}
		
	}

/**
 * @author A-9175
* @desc : clickContourButton
 * @throws IOException
 */
	public void clickContourButton() throws IOException
	{
		try {
			androidScrolllTillPageDown();
			clickActionInHHT("btn_clickContour;xpath",proppathuldsight," Click Contour ",screenName); 
			waitForSync(2);
			writeExtent("Pass", "Clicked Contour on "+screenName); 
		} catch (Exception e) {
			writeExtent("Fail", "Could not Click Contour on "+screenName); 
		}
	}

	/**
	 * @author A-9175
	 * @Desc : clickCloseButton
	 * @throws IOException
	 */
	public void clickCloseButton() throws IOException
	{

	try {
		clickActionInHHT("btn_contourVals_close;xpath",proppathuldsight," Click Close Contour ",screenName); 
		waitForSync(2);
		writeExtent("Pass", "Clicked on Close Contour on "+screenName); 
	} catch (Exception e) {
		writeExtent("Fail", "Could not Click Close Contour on "+screenName); 
	}
	
}
	/**
	 * @author A-10690
	 * @Desc enter overhang values
	 * @param forwardlinklen
	 * @param afterlinklen
	 * @param leftlinklen
	 * @param rightlinklen
	 * @throws IOException
	 */


	public void enterOverhangDetailsPopulated(String forwardlinklen,String afterlinklen,String leftlinklen,String rightlinklen) throws IOException{

		waitForSync(2);
		enterValueInHHT("txt_forwardlen;xpath",proppathuldsight,data(forwardlinklen),"Front overhang value",screenName);
		enterValueInHHT("txt_afterlen;xpath",proppathuldsight,data(afterlinklen),"After overhang",screenName);
		enterValueInHHT("txt_leftlen;xpath",proppathuldsight,data(leftlinklen),"Left overhang",screenName);
		enterValueInHHT("txt_rightlen;xpath",proppathuldsight,data(rightlinklen),"right overhang",screenName);



	}
	/** 
	* @author A-9844
	* Desc- Click on done button in filter
	* @throws IOException 
	*/
	public void clickDoneInFilter() throws IOException {
		waitForSync(3);	
		int eleSize=getSizeOfMobileElement("btn_Done;xpath",proppathuldsight);
		try{

			if(eleSize==1)
			{

				clickActionInHHT("btn_Done;xpath",proppathuldsight,"Done button",screenName);
		        writeExtent("Pass","Clicked on Done button");
		        waitForSync(3);
			}

		}
		catch(Exception e)
		{
			writeExtent("Fail","Could not click on Done button");
		}
	}
	/**
	 * @author A-10690
	 * @Des To click on capture weight button
	 * @throws IOException
	 */

	public void clickCaptureWeightBtn() throws IOException{

		waitForSync(2);
		clickActionInHHT("btn_captureWt;xpath",proppathuldsight,"Capture weight button",screenName);


	}

/**
	 * @author A-10690
	 * @Des Enter weight and verifying the re-entering weight field is there  
	 * @param weight
	 * @throws IOException
	 */

	public void captureWeight(String weight) throws IOException{

		waitForSync(4);
		enterValueInHHT("inbx_enterWt;xpath",proppathuldsight,data(weight),"entered weight",screenName);
		int newweight= Integer.parseInt(data(weight))-1;
		String weightnew =String.valueOf(newweight);
		try{
			int eleSize=getSizeOfMobileElement("inbx_ReEnterWt;xpath",proppathuldsight);

			if(eleSize==1)
			{
				writeExtent("Pass","successfully verified renter weight field");
				enterValueInHHT("inbx_ReEnterWt;xpath",proppathuldsight,weightnew,"Re-enter weight field",screenName);
				waitForSync(1);
				clickActionInHHT("btn_done;xpath",proppathuldsight,"done button",screenName);
				waitForSync(2);
			}
			else
			{
				writeExtent("Fail","Failed to verify re-enter weight field"+screenName);
			}
		}catch(Exception e)
		{
			writeExtent("Fail","Failed to verify re-enter weight field"+screenName);
		}

	}

	/**
	 * @author A-8783 
	 * Desc - Verify error message and click close
	 * @param error
	 */
	public void verifyError(String error) {
		waitForSync(4);
		String locatorValue = getPropertyValue(proppathuldsight, "txt_error;xpath");
		locatorValue = locatorValue.replace("*", error);
		String errorBtn = getPropertyValue(proppathuldsight, "btn_errorMsg;xpath");
		errorBtn = errorBtn.replace("*", error);
		int eleSize = androiddriver.findElements(By.xpath(locatorValue)).size();

		if (eleSize == 1) {
			writeExtent("Pass", "Verified the error message "+errorBtn+" on "+screenName);
			androiddriver.findElement(By.xpath(errorBtn)).click();
			waitForSync(2);
		} else {
			writeExtent("Fail", "Could not verify the error message "+errorBtn+" on "+screenName);
		}
	}


/**
	 * @author A-9847
	 * @Des To verify EPS time is not displayed in Uld sighting App
	 */
	public void verifyEPSNotAvailable(){
		
		try{
		
			int size=androiddriver.findElements(By.xpath(getPropertyValue(proppathuldsight, "txt_epsTime;xpath"))).size();
			System.out.println(size);
			if(size>0)
			{
				String EPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathuldsight, "txt_epsTime;xpath"))).getText().split(" ")[2];
				writeExtent("Fail", "EPS time got displayed as "+EPS+" which is not expected in CDG on "+screenName);
			}
			else
				writeExtent("Pass", "Successfully verified EPS time is not displayed on "+screenName);

		}
		catch(Exception e){
			writeExtent("Fail", "Failed to conclude on EPS time Verification "+screenName);
		}
		
	}
	
	/**
	 * @author A-9844
	 * @param field
	 * @throws IOException
	 * Desc : verify Contour field is present
	 */
	public void verifyContourFiledIsPresent(String field) throws IOException
	{
		
		String fieldName=getPropertyValue(proppathuldsight, "txt_Contour;xpath");
		fieldName=fieldName.replace("*", data(field));

		String actText=androiddriver.findElement(By.xpath(fieldName)).getText();
		System.out.println(actText);
		waitForSync(2);
		if (actText.equals(data(field))){
			writeExtent("Pass", "Verified field "+data(field)+" is present on "+screenName); 
		}
		else{
			writeExtent("Fail", "Failed to verify field "+data(field)+" is present on "+screenName);
		}
	}
	/**
	 * @author A-9844
	 * @param field
	 * @throws IOException
	 * Desc : verify no default contour values are displayed
	 */
	public void verifyNoDefaultContourIsDisplayed(String field) throws IOException
	{	
		waitForSync(4);
		try{
			String fieldName=getPropertyValue(proppathuldsight, "txt_selectContour;xpath");
			fieldName=fieldName.replace("*", data(field));

			String actText=androiddriver.findElement(By.xpath(fieldName)).getText();
			System.out.println(actText);
			waitForSync(2);
			if (actText.equals(data(field))){
				writeExtent("Pass", "Verified field "+data(field)+" is present on .No default contour is  displayed "+screenName); 
			}
			else{
				writeExtent("Fail", "Default contour is displayed"+screenName);
			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Default contour value is present "+screenName);
		}


	}
	/**
	 * Desc : Select Contour
	 * @author A-9844
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void selectContour(String contour) throws AWTException, InterruptedException, IOException
	{

		androidScrolllTillPageDown();
		clickActionInHHT("btn_clickContour;xpath",proppathuldsight," Click Contour ",screenName); 
		waitForSync(2);
		//Select the contour value
		String locatorValue=getPropertyValue(proppathuldsight, "txt_ContourValue;xpath");
		locatorValue=locatorValue.replace("ContourValue", data(contour));
		androiddriver.findElement(By.xpath(locatorValue)).click();
		waitForSync(3);
		writeExtent("Pass", "Selected  "+data(contour)+" as Contour on "+screenName); 


	}


	/**
	 * Desc : To Click pallet button
	 * @author A-10690
	 * @throws IOException 
	 */
	public void clickPallet() throws IOException
	{
		clickActionInHHT("btn_PalletOption;xpath",proppathuldsight,"pallet option",screenName);
		waitForSync(5);
		
	}

	/**
	 * Desc : Verifying container type  Uld got displayed
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void verifyContainerTypeUld(String BULK) throws InterruptedException, IOException
	{
		String actText="";

		String locator=getPropertyValue(proppathuldsight, "txt_UldText;xpath");
		List <MobileElement> elements=androiddriver.findElements(By.xpath(locator));

		boolean uldExists=false;

		for(MobileElement elemnt:elements)
		{
			actText=elemnt.getText();
			System.out.println(actText);

			if(!actText.contains(data(BULK))){
				uldExists=true;
				break;

			}
		}

		if(uldExists)
			writeExtent("Fail", "Failed to verify ULD Type as "+data(BULK)+". Actual value displayed is "+actText+" in " + screenName);
		else

			writeExtent("Pass", "Verified ULD Type as "+data(BULK)+".Actual value displayed is "+actText+" in " +screenName);



	}
	/**
	 * @author A-9844
	 * Desc- To select the sighting location
	 * @param location
	 * @throws IOException 
	 */
	public void selectSightingLocation(String location) throws IOException {
	
		clickActionInHHT("btn_SightingLoc;xpath",proppathuldsight,"Sight",screenName);
		waitForSync(3);
		
		enterValueInHHT("inbx_forwardloc;xpath",proppathuldsight,data(location),"Sighting location",screenName);
		String locatorValue=getPropertyValue(proppathuldsight, "lst_forwardingLoc;xpath");
		locatorValue=locatorValue.replace("location", data(location));
		scrollInMobileDevice(data(location));
		androiddriver.findElement(By.xpath(locatorValue)).click();
		waitForSync(3);
	}
	/**
	 * @author A-9844
	 * @Desc To select the Forward Location before sighting
	 * @param location
	 * @throws IOException
	 */
	public void selectFwLocationAfterSighting(String location) throws IOException{

		try{
			
			clickActionInHHT("btn_forwardZON;xpath",proppathuldsight,"Forward ZON Edit Button",screenName);
			waitTillMobileElementDisplay(proppathuldsight,"inbx_forwardloc;xpath","xpath",20);
			enterValueInHHT("inbx_forwardloc;xpath",proppathuldsight,data(location),"Forward ZON",screenName);
			String locatorValue=getPropertyValue(proppathuldsight, "lst_forwardingLoc;xpath");
			locatorValue=locatorValue.replace("location", data(location));
			scrollInMobileDevice(data(location));
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);
		}
		catch(Exception e){

			writeExtent("Fail", "Failed to select the forwarding Zone on "+screenName);
		}
	}


	/**
	 * Desc : To Click container button
	 * @author A-10690
	 * @throws IOException 
	 */
	public void clickContainer() throws IOException
	{
		clickActionInHHT("btn_ContainerOption;xpath",proppathuldsight,"Container option",screenName);
		waitForSync(5);
		
	}
	/**
	 *@author A-9847
	 *@Desc To verify EPS time is displayed on Uld sighting app
	 * @param ata
	 */
	
	public void verifyEPSTime(String ata){
	
			try{
			
				String ExpEPS=timeCalculation(data(ata), "HH:mm","MINUTE",Integer.parseInt(data("EPS_Configtime")));
				String EPS=androiddriver.findElement(By.xpath(getPropertyValue(proppathuldsight, "txt_epsTime;xpath"))).getText().split(" ")[2];
				if(ExpEPS.equals(EPS))
					writeExtent("Pass", "Successfully verified EPS time as "+EPS+" on "+screenName);
				else
					writeExtent("Fail", "Failed to verify EPS time as " +EPS+ " on "+screenName+" where expected value is "+ExpEPS);

			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify EPS time on "+screenName);
			}
			
		}
	/**
	 * @desc To click the checksheet button
	 * @throws IOException
	 */
	public void clickChecksheet() throws IOException
	{
		clickActionInHHT("btn_Checksheet;xpath",proppathuldsight,"Checksheet",screenName);
		waitForSync(5);
		
	}
	/**
	 * @descrption : clickULDinstructions
	 * @author A-9175
	 * @param UldNum
* @throws IOException
	 */
	public void clickULDinstructions(String UldNum) throws IOException {
		
		
        try
        {
        	String locatorValue=getPropertyValue(proppathuldsight,"btn_uldinstruction;xpath");
            locatorValue=locatorValue.replace("*", data(UldNum));
            androiddriver.findElement(By.xpath(locatorValue)).click();
        	waitForSync(3);
        	writeExtent("Pass","Clicked on  ULD Instruction Icon");
        }
        catch(Exception e)
		{
			writeExtent("Fail","Could not Clicked on ULD Instruction Icon");
		}
}
	
	/**
	 * @Desc : verify ULD instructions Notification Count
	 * @author A-9175 
	 * @param UldNum
	 * @param NotificationCount
	 * @throws IOException
	 */
public void verifyULDinstructionsNotificationCount(String UldNum,String NotificationCount) throws IOException {
		
		try 
		{
			String locatorValue=getPropertyValue(proppathuldsight,"txt_uldinstructionNotification;xpath");
	        locatorValue=locatorValue.replace("*", data(UldNum));
	        String actNotificationCount=androiddriver.findElement(By.xpath(locatorValue)).getText();
			System.out.println(actNotificationCount);
			waitForSync(2);
			if (actNotificationCount.equals(data(NotificationCount)))
			{
				writeExtent("Pass", "Verified Notification count as "+data(NotificationCount)+" is present on "+screenName); 
			}
			else
			{
				writeExtent("Fail", "Failed to verify Notification "+data(NotificationCount)+" is not present on "+screenName);
			}

		} catch (Exception e) 
		{
			writeExtent("Fail", "Failed to verify Notification On"+screenName);
		}
}


/**
* @description : verify ULD instruction Content
* @author A-9175
* @param Instruction
*/
public void verifyULDinstructionContent(String Instruction) 
{
	String locatorValue=getPropertyValue(proppathuldsight,"txt_instructioncontent;xpath");
    locatorValue=locatorValue.replace("*", data(Instruction));
    System.out.println(locatorValue);
    try 
    {
    	 String actCountent=androiddriver.findElement(By.xpath(locatorValue)).getText();
 		System.out.println(actCountent);
 		String expContent=data(Instruction);
 		waitForSync(2);
 		if (actCountent.contains(expContent))
 		{
 			writeExtent("Pass", "Verified ULD Instruction as "+data(Instruction)+" is present on "+screenName); 
 		}
 		else
 		{
 			writeExtent("Fail", "Failed to verify ULD Instruction "+data(Instruction)+" is not present on "+screenName);
 		}
 		
	} catch (Exception e) {
		writeExtent("Fail", "Failed to verify ULD Instruction "+ "on "+screenName);
	}
   
}


/**
* @Desc : special Instruction Close
* @author A-9175
 */
	public void specialInstructionClose() 
	{
        try
        {
        	clickActionInHHT("btn_specialInstructionClose;xpath",proppathuldsight,"Special Instruction Close button",screenName);
        	waitForSync(3);
        	writeExtent("Pass","Clicked on  Special Instruction Close Icon");
        }
        catch(Exception e)
		{
			writeExtent("Fail","Could not Clicked on Special Instruction Close Icon");
		}
		
	}
	

	
	/**
	 * To Capture the Sighting1 checksheet
	 * @param answer
	 * @throws IOException
	 */
	public void captureSighting1(String answer) throws IOException
	{
		List <MobileElement> questions=androiddriver.findElements(By.xpath(getPropertyValue(proppathuldsight, "txt_question;xpath")));
		
		System.out.println(questions.size());
		for(MobileElement quest:questions)
		{
			clickActionInHHT("txt_yes;xpath",proppathuldsight,"Yes",screenName);
			String text=quest.getText();
			System.out.println(text);
			if(text.contains(answer))		
			clickActionInHHT("txt_no;xpath",proppathuldsight,"No",screenName);
				
		}		
		
		clickActionInHHT("txt_sighting1;xpath",proppathuldsight,"Sighting 1",screenName);
	}
	
	
	
	/**
	 * To Capture the Sighting2 checksheet
	 * @param answer
	 * @throws IOException
	 */
	public void captureSighting2(String answer) throws IOException
	{
		clickActionInHHT("txt_sighting2;xpath",proppathuldsight,"Sighting 2",screenName);
		waitForSync(3);
		List <MobileElement> questions=androiddriver.findElements(By.xpath(getPropertyValue(proppathuldsight, "txt_question;xpath")));
		System.out.println(questions.size());
		for(MobileElement quest:questions)
		{
			clickActionInHHT("txt_yes;xpath",proppathuldsight,"Yes",screenName);
			String text=quest.getText();
			System.out.println(text);
			if(text.contains(answer))		
				clickActionInHHT("txt_no;xpath",proppathuldsight,"No",screenName);
				
		}		
		
		clickActionInHHT("txt_sighting2;xpath",proppathuldsight,"Sighting 2",screenName);
	}
	
	/**
	 * To click on the Checksheet save
	 * @throws IOException
	 */
	public void clickChecksheetSave() throws IOException
	{
		clickActionInHHT("txt_Save;xpath",proppathuldsight,"Save",screenName);
		waitForSync(5);
		
	}
	/**
	 * @author A-9847
	 * @Desc To click the refresh button after clicking the get scale weight in Sighting App
	 * @throws IOException
	 */
	public void clickScaleWeightRefresh() throws IOException{
		
		clickActionInHHT("btn_scaleWeightRefresh;xpath",proppathuldsight,"Refresh Scale Weight",screenName);
		waitTillMobileElementDisplay(proppathuldsight,"txt_weightUnitKg;xpath","xpath",20);
	
		
	}
	/**
	 * @author A-7271
	 * @param uld
	 * Desc : search uld
	 */
	public void searchUld(String uld)
	{
		 String locatorValue=getPropertyValue(proppathuldsight, "btn_searchUld;xpath");
		 locatorValue=locatorValue.replace("UldNumber", data(uld));
		 
		androiddriver.findElement(By.xpath(locatorValue)).click();
         waitForSync(5);
	}
	
	/**
	 * @author A-7271
	 * @param uld
	 * Desc : Verify if ULd is sighted in the app
	 */
	public void verifyIfUldIsSighted(String uld)
	{

		try
		{
		 String locatorValue=getPropertyValue(proppathuldsight, "txt_uldNumber;xpath");
		 locatorValue=locatorValue.replace("UldNumber", data(uld));
		 
		int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();
		
		if(eleSize==1)
		{
			writeExtent("Pass","ULD is sighted in the ULD sighting app");
			
		}
		else
		{
			writeExtent("Fail","ULD is not sighted in the ULD sighting app");
		}
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","ULD is not sighted in the ULD sighting app");
		}
		
	}
	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : click Filter
	 */
	public void clickFilter() throws IOException
	{
		
	        waitForSync(2);
		clickActionInHHT("btn_filter;xpath",proppathuldsight,"Filter",screenName);
		waitForSync(6);

	}

/**
	 * @author A-9847
	 * @Des To click on Get Scale Weight
	 * @throws IOException
	 */
	
	public void clickGetScaleWeight() throws IOException{
	
		clickActionInHHT("txt_getScaleWgt;xpath",proppathuldsight,"Get Scale Weight",screenName);
		waitTillMobileElementDisplay(proppathuldsight,"txt_weightUnitKg;xpath","xpath",20);
		
		
		
		
	}
	

	/**
	 * @author A-9847
	 * @Desc To enter and select the forward location
	 * @param location
	 * @throws IOException
	 */
	
	public void enterAndSelectFwdLocation(String location) throws IOException
	{
		
		try{
			String SightButtonAvailable=getPropertyValue(proppathuldsight, "btn_sight;xpath");	 	 
			if(androiddriver.findElements(By.xpath(SightButtonAvailable)).size()==0)	
				/** As per the new CR, its the Forward Zone instead of loc selected here after Sighting and Sighting loc before Sighting **/
				clickActionInHHT("btn_forwardingLoc;xpath",proppathuldsight,"Forward Zone",screenName);
			else
				clickActionInHHT("btn_forwarloc;xpath",proppathuldsight,"Sighting Loc Edit Button",screenName);

			waitTillMobileElementDisplay(proppathuldsight,"inbx_forwardloc;xpath","xpath",20);

			enterValueInHHT("inbx_forwardloc;xpath",proppathuldsight,data(location),"Sighting Location/ Forward Zone",screenName);
			String locatorValue=getPropertyValue(proppathuldsight, "lst_forwardingLoc;xpath");
			locatorValue=locatorValue.replace("location", data(location));
			scrollInMobileDevice(data(location));
			androiddriver.findElement(By.xpath(locatorValue)).click();
			waitForSync(3);

			
	}
	catch(Exception e){
		
		writeExtent("Fail", "Failed to select the forwarding location on "+screenName);
	}

		
	}
	/**
	 * @author A-9847
	 * @Desc To verify the Scale weight and Height populated
	 * @param scaleWeight
	 * @param height
	 * @throws IOException
	 */
	
	public void verifyScaleWeightAndHeightPopulated(String scaleWeight, String height) throws IOException{
		try{
			
		
		
		String actScaleWeight=getTextAndroid("txt_scaleWgt;xpath",proppathuldsight,"Actual Scale Weight",screenName);	
        verifyScreenTextWithExactMatch(screenName, data(scaleWeight), actScaleWeight, "Scale weight","Scale weight"); 
		String actHeight=getTextAndroid("txt_height;xpath",proppathuldsight,"Actual Height",screenName);
		verifyScreenTextWithExactMatch(screenName, data(height), actHeight, "Height","Height");
		}
		catch(Exception e){
			
			writeExtent("Fail", "Failed to verify the Scale weight/Height details on "+screenName);
		}
           


           
	}
	/**
	 * @author A-8783
	 * @Des Capture and reenter weight 
	 * @param weight
	 * @throws IOException
	 */

	public void captureActualWeight(String weight) throws IOException{

		waitForSync(2);
		enterValueInHHT("inbx_enterWt;xpath",proppathuldsight,data(weight),"entered weight",screenName);
		enterValueInHHT("inbx_ReEnterWt;xpath",proppathuldsight,data(weight),"Re-enter weight field",screenName);
		clickActionInHHT("btn_done;xpath",proppathuldsight,"done button",screenName);
		waitForSync(1);

	}

	
	/**
	 * @author A-9847
	 * @Desc To verify the overhang details
	 * @param forwardlinklen
	 * @param afterlinklen
	 * @param leftlinklen
	 * @param rightlinklen
	 * @throws IOException
	 */
	
	
	public void verifyOverhangDetailsPopulated(String forwardlinklen,String afterlinklen,String leftlinklen,String rightlinklen) throws IOException{
		
		try{
			String actforwardlinkedlength=getTextAndroid("txt_forwardlen;xpath",proppathuldsight,"Forward Linked Length",screenName);
			String actafterlinkedlength=getTextAndroid("txt_afterlen;xpath",proppathuldsight,"After Linked Length",screenName);	
			String actleftlinkedlength=getTextAndroid("txt_leftlen;xpath",proppathuldsight,"Left Linked Length",screenName);	
			String actrightlinkedlength=getTextAndroid("txt_rightlen;xpath",proppathuldsight,"Right Linked Length",screenName);	



			verifyScreenTextWithExactMatch(screenName, data(forwardlinklen), actforwardlinkedlength, "Forward linked length","Forward linked length");
			verifyScreenTextWithExactMatch(screenName, data(afterlinklen), actafterlinkedlength, "After linked length","After linked length");
			verifyScreenTextWithExactMatch(screenName, data(leftlinklen), actleftlinkedlength, "Left linked length","Left linked length");
			verifyScreenTextWithExactMatch(screenName, data(rightlinklen), actrightlinkedlength, "Right linked length","Right linked length");


		}
		catch(Exception e){

			writeExtent("Fail", "Failed to verify the Overhang details on "+screenName);
		}
			

	}
	
	
	/**
	 * @author A-9847
	 * @Desc To select the Forward Location before sighting
	 * @param location
	 * @throws IOException
	 */
	
	
	public void selectFwLocationBeforeSighting(String location) throws IOException{
		
try{
	/** As per the New CR , its the Sighting Loc we are selecting here as Current Location Before Sighting**/
	
	clickActionInHHT("btn_forwarloc;xpath",proppathuldsight,"Sighting Loc Edit Button",screenName);
	waitTillMobileElementDisplay(proppathuldsight,"inbx_forwardloc;xpath","xpath",20);
	enterValueInHHT("inbx_forwardloc;xpath",proppathuldsight,data(location),"Sighting Location",screenName);
	String locatorValue=getPropertyValue(proppathuldsight, "lst_forwardingLoc;xpath");
	locatorValue=locatorValue.replace("location", data(location));
	scrollInMobileDevice(data(location));
	androiddriver.findElement(By.xpath(locatorValue)).click();
	waitForSync(3);


		
}
catch(Exception e){
	
	writeExtent("Fail", "Failed to select the forwarding location on "+screenName);
}
		
		
	}
	
	/**
	 * @author A-9844
	 * @Desc To enter the overhang details
	 * @param forwardlinklen
	 * @param afterlinklen
	 * @param leftlinklen
	 * @param rightlinklen
	 * @throws IOException
	 */
	public void enterOverhangDetails(String forwardlinklen,String afterlinklen,String leftlinklen,String rightlinklen) throws IOException{
		
		try{
			
			enterValueInHHT("txt_forwardlen;xpath",proppathuldsight,data(forwardlinklen),"Forward linked length",screenName);
			enterValueInHHT("txt_afterlen;xpath",proppathuldsight,data(afterlinklen),"After linked length",screenName);
			enterValueInHHT("txt_leftlen;xpath",proppathuldsight,data(leftlinklen),"Left linked length",screenName);
			enterValueInHHT("txt_rightlen;xpath",proppathuldsight,data(rightlinklen),"Right linked length",screenName);

		}
		catch(Exception e){

			writeExtent("Fail", "Failed to enter the Overhang details on "+screenName);
		}
			
	}

	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : click Uld Type
	 */
	public void clickUldType() throws IOException
	{
		clickActionInHHT("btn_uldType;xpath",proppathuldsight,"ULD Type",screenName);
		waitForSync(8);

	}
	/**
	 * @author A-9844        
	 * Description : Verify filter options- Bulk,Container,Pallet,Clear
	 * @throws IOException 
	 */
	public void verifyULDTypeFilterOptions(String expBulk, String expContainer,String expPallet,String expClear) throws IOException
	{

		//verify Bulk                      
		String locator1=getPropertyValue(proppathuldsight, "txt_Bulk;xpath");

		String actualText1=androiddriver.findElement(By.xpath(locator1)).getText();        
		if(actualText1.equals(data(expBulk)))
		{
			writeExtent("Pass", "Verified filed "+data(expBulk)+" in "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Failed to verify field for "+data(expBulk)+" in "+screenName);
		}

		//verify Container
		String locator2=getPropertyValue(proppathuldsight, "txt_Container;xpath");
		String actualText2=androiddriver.findElement(By.xpath(locator2)).getText();        
		if(actualText2.equals(data(expContainer)))
		{
			writeExtent("Pass", "Verified field "+data(expContainer)+" in "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Failed to verify field for "+data(expContainer)+" in "+screenName);
		}

		//verify Pallet
		String locator3=getPropertyValue(proppathuldsight, "txt_Pallet;xpath");
		String actualText3=androiddriver.findElement(By.xpath(locator3)).getText();        
		if(actualText3.equals(data(expPallet)))
		{
			writeExtent("Pass", "Verified field "+data(expPallet)+" in "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Failed to verify field for "+data(expPallet)+" in "+screenName);
		}

		//verify Clear
		String locator4=getPropertyValue(proppathuldsight, "txt_Clear;xpath");
		String actualText4=androiddriver.findElement(By.xpath(locator4)).getText();        
		if(actualText4.equals(data(expClear)))
		{
			writeExtent("Pass", "Verified field "+data(expClear)+" in "+screenName);
		}
		else
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Failed to verify field for "+data(expClear)+" in "+screenName);
		}
         waitForSync(2);

	}

	
	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : click Bulk Option
	 */
	public void clickBulkOption() throws IOException
	{
		
		clickActionInHHT("btn_BulkOption;xpath",proppathuldsight,"Bulk Option",screenName);
		waitForSync(6);

	}
	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : click Apply
	 */
	public void clickApply() throws IOException
	{
		clickActionInHHT("btn_Apply;xpath",proppathuldsight,"Apply",screenName);
		waitForSync(6);

	}
	/**
	 * Desc : Verifying Bulk Type Uld got displayed
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void verifyBulkTypeUld(String BULK) throws InterruptedException, IOException
	{
		String actText="";

		String locator=getPropertyValue(proppathuldsight, "txt_UldText;xpath");
		List <MobileElement> elements=androiddriver.findElements(By.xpath(locator));

		boolean uldExists=false;

		for(MobileElement elemnt:elements)
		{
			actText=elemnt.getText().substring(0, 4);
			System.out.println(actText);

			if(!actText.equals(data(BULK))){
				uldExists=true;
				break;

			}
		}

		if(uldExists)
			writeExtent("Fail", "Failed to verify ULD Type as "+data(BULK)+". Actual value displayed is "+actText+" in " + screenName);
		else

			writeExtent("Pass", "Verified ULD Type as "+data(BULK)+".Actual value displayed is "+actText+" in " +screenName);



	}
	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : click Sight
	 */
	public void clickSight() throws IOException
	{
		clickActionInHHT("btn_sight;xpath",proppathuldsight,"Sight",screenName);
		waitForSync(6);
		
	}
	/**
	 * @author A-8783
	 * Desc- To verify if the uld is sighted
	 * @param uldNo
	 */
	public void verifySighted(String uldNo) {
		
        hover(uldNo);
        String locatorValue=getPropertyValue(proppathuldsight, "txt_sighted;xpath");

   locatorValue=locatorValue.replace("UldNumber", data(uldNo));     
   
               int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

               if(eleSize==1)
               {
                      writeExtent("Pass","Verified that the ULD is sighted");
                      
               }
               else
               {
                      writeExtent("Fail", "The ULD is not marked as sighted");
               }
  }



	
	/**
	 * @author A-8783
	 * Desc- To verify if the uld is offloaded
	 * @param uldNo
	 */
	public void verifyOffloaded(String uldNo) {
		scrollInMobileDevice(data(uldNo));	
		String locatorValue=getPropertyValue(proppathuldsight, "txt_offload;xpath");

        locatorValue=locatorValue.replace("UldNumber", data(uldNo));
		
			int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

			if(eleSize==1)
			{
				writeExtent("Pass","Verified that the ULD is offloaded");
				
			}
			else
			{
				writeExtent("Fail", "The ULD is not marked as offloaded");
			}
	}
	/**
	 * @author A-8783
	 * Desc- verify warning message and click yes button
	 * @throws IOException 
	 */
		public void verifyWarningAndClickYes(String warning) throws IOException {
			
			String locatorValue=getPropertyValue(proppathuldsight, "txt_warning;xpath");

            locatorValue=locatorValue.replace("*", warning);
			
				int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

				if(eleSize==1)
				{
					writeExtent("Pass","Verified the warning message");
					clickActionInHHT("btn_Yes;xpath",proppathuldsight,"Yes button",screenName);
					waitForSync(1);
				}
				else
				{
					writeExtent("Fail","Could not verify the warning message");
				}
			
		}

	/** 
	* @author A-8783
	* Desc- Click on done button if visible
	* @throws IOException 
	*/
	public void clickDone() throws IOException {
waitForSync(3);	
		
		
		int handlingAreaEleSize=getSizeOfMobileElement("btn_HandlingAreaList;xpath",proppathuldsight);
		
		if(handlingAreaEleSize==1)
		{

			
			clickActionInHHT("btn_Done;xpath",proppathuldsight,"Done button",screenName);
			writeExtent("Pass","Clicked on Done button");
			waitForSync(1);
		}
		waitForSync(3);	
		int eleSize=getSizeOfMobileElement("btn_Done;xpath",proppathuldsight);
		try{
			

			if(eleSize==1)
			{

				enterValueInHHT("inbx_VehicleNo;xpath",proppathuldsight,data("VehicleNo"),"Vehicle ID",screenName);
				waitForSync(1);	
				clickActionInHHT("btn_Done;xpath",proppathuldsight,"Done button",screenName);
				writeExtent("Pass","Clicked on Done button");
				waitForSync(1);
			}

			waitForSync(5);
			clickActionInHHT("btn_filter;xpath",proppathuldsight,"Filter",screenName);
			waitForSync(3);
			clickActionInHHT("txt_Clear;xpath",proppathuldsight,"Clear",screenName);
			waitForSync(3);
			clickActionInHHT("btn_Apply;xpath",proppathuldsight,"Apply",screenName);
			waitForSync(5);			

		}
		catch(Exception e)
		{
			writeExtent("Fail","Could not click on Done/Fliter button");
		}
	}

	/**
	 * @author A-7271
	 * @param location
	 * @throws IOException
	 * Desc : select forward location
	 */
	public void selectFwLocation(String location) throws IOException
	{
		/** As per the new CR, its the Forward Zone selected here after Sighting and Sighting Loc before Sighting **/
		
		String SightButtonAvailable=getPropertyValue(proppathuldsight, "btn_sight;xpath");
		if(androiddriver.findElements(By.xpath(SightButtonAvailable)).size()==0)	

			clickActionInHHT("btn_forwardingLoc;xpath",proppathuldsight,"Forward Zone",screenName);
		else
			clickActionInHHT("btn_forwarloc;xpath",proppathuldsight,"Sighting Loc Edit Button",screenName);

		waitTillMobileElementDisplay(proppathuldsight,"inbx_forwardingLoc;xpath","xpath",20);
		enterValueInHHT("inbx_forwardingLoc;xpath",proppathuldsight,data(location),"Sighting Loc/Forward Zone",screenName);
		String locatorValue=getPropertyValue(proppathuldsight, "lst_forwardingLoc;xpath");
		locatorValue=locatorValue.replace("location", data(location));
		androiddriver.findElement(By.xpath(locatorValue)).click();
		waitForSync(3);



		
	}
	/**
	 * @author A-8783
	 * Desc- verify error message 
	 * @throws IOException 
	 */
		public void verifyBUPErrorMessage() throws IOException {
			
			String locatorValue=getPropertyValue(proppathhht, "btn_errorMsg;xpath");

            locatorValue=locatorValue.replace("*", "The scanned ULD is a BUP Unit. Please perform ULD Acceptance.");
			
				int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

				if(eleSize==1)
				{
					writeExtent("Pass","Verified the error message: The scanned ULD is a BUP Unit. Please perform ULD Acceptance.");
					clickActionInHHT("btn_closeMsgForBUPShipment;xpath",proppathuldsight,"BUP Error message",screenName);
					waitForSync(1);
				}
				else
				{
					writeExtent("Fail","Could not verify the error message: The scanned ULD is a BUP Unit. Please perform ULD Acceptance.");
				}
			
		}

	/**
	 * @author A-7271
	 * @throws IOException
	 * Desc : click complete
	 */
	public void clickComplete() throws IOException
	{
		clickActionInHHT("btn_complete;xpath",proppathuldsight,"Complete",screenName);
		waitForSync(5);
	}
	
	/**
	 * @author A-7271
	 * Desc : verify the message for BUP shipment
	 */
	public void verifyIfBUPShipment()
	{
		try
		{
			int eleSize=getSizeOfMobileElement("txt_msgForBUPShipment;xpath",proppathuldsight);

			if(eleSize==1)
			{
				writeExtent("Pass","Confirmation message comes as the shipment is BUP");
				clickActionInHHT("btn_closeMsgForBUPShipment;xpath",proppathuldsight,"BUP Confirmation message",screenName);
				waitForSync(1);
			}
			else
			{
				writeExtent("Fail","Confirmation message doesn not come as the shipment is BUP");
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Confirmation message doesn not come as the shipment is BUP");
		}
	}

	
}
