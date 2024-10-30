package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CaptureCheckSheet_CHK002 extends CustomFunctions {

	public CaptureCheckSheet_CHK002(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	String sheetName = "CaptureCheckSheet_CHK002";
	public String screenName = "CaptureCheckSheet_CHK002";

	/**
	 * A-8705 Description...Lists AWB
	 * 
	 * @param AWB
	 *            Number
	 * @param shipment
	 *            prefix
	 * @param transaction
	 *            type
	 * @throws IOException 
	 */
	public void listAWBWithTransaction(String awbNo, String ShipmentPrefix, String option)
			throws InterruptedException, IOException {
		String sheetName = "Generic_Elements";
		awbNo = getPropertyValue(proppath, awbNo);

		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath",
				data(ShipmentPrefix), "Shipment Prefix", screenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", awbNo,
				"AWB No", screenName);
		selectValueInDropdown("CaptureCheckSheet_CHK002",
				"inbx_TransactionType;name", option, "Transaction Type",
				"VisibleText");
		clickWebElement(sheetName, "btn_List;xpath", "List Button", screenName);
		waitForSync(4);
	}

	/**
	 * @author A-7271
	 * @param checksheetType
	 */
	public void listCheckSheetType(String checksheetType)
	{
		selectValueInDropdown(sheetName,"lst_checksheetType;name", checksheetType, "Check Sheet Type","VisibleText");
		waitForSync(1);	

	}
	/**
	 * A-8705 Description..Selects Yes in checksheet
	 * 
	 * @param option
	 *            either Y/N
	 */
	public void clickYes(String option) throws InterruptedException {
		selectValueInDropdown(sheetName, "inbx_FillUpChecksheet;xpath", option,
				"checksheet", "Value");
		waitForSync(4);
	}
	/**
	 * @author A-8783
	 * Desc -  capture AVI cheksheet
	 * @param date
	 * @param time
	 */
	public void enterCheckSheetAnsLiveAnimal(String date, String time)
	{

		boolean checkSheetExists=true;
		try
		{

			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));

			if(questions.size()==0)
			{
				checkSheetExists=false;
			}

			for(WebElement ele : questions)
			{
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
			}

			enterValueInTextbox(sheetName, "inbx_answerDate;name", date, "Date when animal was fed", screenName);
			enterValueInTextbox(sheetName, "inbx_answerDateTime;name", time, "Time when animal was fed", screenName);

			if(checkSheetExists)
			{
				writeExtent("Pass","Check sheet details selected on "+screenName);
			}

			else
			{
				writeExtent("Fail","No check sheet details configured on "+screenName);
			}

		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not select check sheet details on "+screenName);
		}
	}


	/**
	 * A-8705 Description..Clicks Save button
	 * @throws IOException 
	 * 
	 * 
	 */
	public void save() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Save;name", "save Button", screenName);
		waitForSync(4);
		try {
			waitForSync(2);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn;xpath",
					"OK Button", screenName);
			waitForSync(2);
			writeExtent("Pass","Check sheet details are saved on "+screenName);

		} catch (Exception e) {

			writeExtent("Fail","Could not save check sheet details on "+screenName);
		}
		switchToFrame("contentFrame", "CHK002");
	}
	/**@author A-10328
	 * @Desc To capture checksheet of Multiple formats
	 * @param chkSheetRequired
	 * @throws Exception
	 */
	public void captureChecksheetWithMultiFormats(boolean chkSheetRequired) throws Exception 

	{

		boolean checkSheetExists = true;
		String startDate = createDateFormat("dd-MMM-YYYY", 0, "DAY", "");

		try {

			waitForSync(3);
			List<WebElement> questions = driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));
			List<WebElement> questions2 = driver.findElements(By.xpath("//input[@title='Date']"));
			List<WebElement> questions3 = driver.findElements(By.xpath("//input[@title='Time']"));
			List<WebElement> questions4 = driver.findElements(By.xpath("//textarea[@class='iCargoTextAreaMedium']"));
			List<WebElement> questions5 = driver.findElements(By.xpath("//button[contains(@id,'CMP_Checksheet') and @class='ui-multiselect ui-widget ui-state-default ui-corner-all']//span[2]"));

			if (questions.size() == 0 && questions2.size() == 0 && questions3.size() == 0 && questions4.size() == 0 && questions5.size() == 0)
				checkSheetExists = false;

			/** Date Fields **/
			for (WebElement ele : questions2)
			{
				ele.sendKeys(startDate);
			}

			/** Time Fields **/
			for (WebElement ele : questions3)
			{
				ele.sendKeys("00:00");
			}

			/** TextAreas **/
			for (WebElement ele : questions4)
			{
				ele.sendKeys("Yes");
				keyPress("TAB");
			}

			/** Select first option from DropDowns other than Yes/No/NA **/
			for (WebElement ele : questions5) {	
				moveScrollBar(ele);
				String selectedOrNot=ele.getText();

				if(selectedOrNot.equals("Select"))
				{

					//Opening the options dialog box
					ele.click();	
					int i = questions5.indexOf(ele);

					//Selecting the first option from dialog box
					String dynamicXpath="(//input[contains(@id,'ui-multiselect-"+(i+1)+"-CMP_Checksheet_Defaults_CaptureCheckSheet')])[1]";
					driver.findElement(By.xpath(dynamicXpath)).click();
					//Closing the options dialog box
					driver.findElement(By.xpath(dynamicXpath+"/../../../..//a//span[@class='ui-icon ui-icon-circle-close']")).click();		
				}
			}

			/** Yes/No DropDowns **/
			for (WebElement ele : questions)
			{		
				WebElement selectedOption = new Select(ele).getFirstSelectedOption();
				if(selectedOption.getText().equals(""))
				{
					new Select(ele).selectByVisibleText("Yes");
					keyPress("TAB");
					waitForSync(2);
					/****  Handling Any Obligatory questions - No  ***/
					if(driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "warning_symbol;xpath"))).size()==1)
					{
						new Select(ele).selectByVisibleText("No");
						waitForSync(2);
					}
				}
			}

			if (chkSheetRequired) {
				if (checkSheetExists)
					writeExtent("Pass", "Check sheet details are Saved on " + screenName);
				else
					writeExtent("Fail", "No check sheet details configured on " + screenName);
			}

		}
		catch (Exception e) {
			writeExtent("Fail", "Could not save check sheet details on " + screenName);
		}
	}

	

	/**@author A-10328
	 * Description - Verify Checksheet Type Present in dropdown
	 * @param checksheetType
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyChecksheetType(String checksheetType) throws InterruptedException, IOException

	{
		try
		{
			clickWebElement(sheetName, "drpdn_checksheet;xpath", " Check sheet drop down Button", screenName);
			String locatorValue = xls_Read.getCellValue(sheetName, "drpdn_value;xpath");
			locatorValue=locatorValue.replace("*",checksheetType);
			String ActualText=driver.findElement(By.xpath(locatorValue)).getText();
			if(ActualText.contains(checksheetType))
			{
				writeExtent("Pass", " Verified " + checksheetType +" is Present in the drop down "
						+screenName);
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", " Failed to verify " + checksheetType +" is not Present in the drop down "
					+screenName);
		}

	}

	/**@author A-10328
	 * Description - Enter ULD Number
	 * @param ULDNo
	 * @throws InterruptedException
	 */

	public void enterULDNumber(String ULDNo) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_ULDNo;xpath", data(ULDNo), "ULD Number",screenName);
		waitForSync(1);
	}
	/**@author A-10328
	 * Description- Enter Flight Date
	 * @param startDate
	 * @throws InterruptedException
	 */

	public void captureDate(String startDate) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_capturedate;xpath", startDate, "Flight Date",screenName);
		waitForSync(1);
	}

	/**@author A-10328
	 * * Description - Enter Flight Details
	 * @param carrierCode
	 * @param FlightNo
	 * @throws InterruptedException
	 */

	public void  enterFlightDetails(String carrierCode , String FlightNo) throws InterruptedException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;xpath", data(carrierCode), "Carrier code",screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(FlightNo), "Flight Number",screenName);
		waitForSync(1);
	}
	/**@author A-10328
	 * Description - Select Transaction type
	 * @param option
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void transactionType(String option) throws InterruptedException, IOException

	{

		selectValueInDropdown("CaptureCheckSheet_CHK002",
				"drpdn_Transactiontype;xpath", option, "Transaction Type",
				"VisibleText");
		waitForSync(1);

	}


	/**@author A-10328
	 * Description - Click list
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void list() throws InterruptedException, IOException
	{
		clickWebElement("Generic_Elements", "btn_List;xpath", "List Button", screenName);
		waitForSync(1);
	}

	/**
	 * @author A-9844
	 * @Desc To enter the Obligatory answer of checksheet as YES/NO based on questions
	 * @param chkSheetRequired
	 * @param answer
	 */
	public void captureCheckSheet(boolean chkSheetRequired,String answer)
	{  

		boolean checkSheetExists=true;
		try
		{
			waitForSync(3);


			String locatorDGRChecksheet = xls_Read.getCellValue(sheetName, "txt_dgrchecksheet;xpath");
			String locatorExport = xls_Read.getCellValue(sheetName, "txt_exportChecsheet;xpath");
			List<WebElement> questionsDGR = driver.findElements(By.xpath(locatorDGRChecksheet));
			List<WebElement> questionsExport = driver.findElements(By.xpath(locatorExport));

			if(questionsDGR.size()==0 && questionsExport.size()==0 )
			{
				checkSheetExists=false;
			}
			int i=0;
			for(WebElement ele : questionsDGR)
			{
				System.out.println(ele.getText());

				moveScrollBar(ele);
				if(ele.getText().contains(answer))
				{	

					selectValueInDropdownWthXpath("//h2[text()='checksheet for DGR']/..//following-sibling::div//select[@name='questionwithAnswer["+i+"].templateAnswer']","No", ele.getText(), "VisibleText");
					i++;
				}
				else
				{
					selectValueInDropdownWthXpath("//h2[text()='checksheet for DGR']/..//following-sibling::div//select[@name='questionwithAnswer["+i+"].templateAnswer']","Yes", ele.getText(), "VisibleText");
					i++;
				}

			}

			int j=0;
			for(WebElement ele : questionsExport)
			{
				System.out.println(ele.getText());

				moveScrollBar(ele);
				if(ele.getText().contains(answer))
				{	

					selectValueInDropdownWthXpath("//h2[text()='EXP 144']/..//following-sibling::div//select[@name='questionwithAnswer["+j+"].templateAnswer']","No", ele.getText(), "VisibleText");
					j++;
				}
				else
				{
					selectValueInDropdownWthXpath("//h2[text()='EXP 144']/..//following-sibling::div//select[@name='questionwithAnswer["+j+"].templateAnswer']","Yes", ele.getText(), "VisibleText");
					j++;
				}

			}


			if(chkSheetRequired)
			{
				if(checkSheetExists)
				{
					writeExtent("Pass","Check sheet details selected on "+screenName);
				}

				else
				{
					writeExtent("Fail","No check sheet details configured on "+screenName);
				}
			}

			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);


			if(chkSheetRequired)
			{
				if(checkSheetExists)
				{
					writeExtent("Pass","Check sheet details saved on "+screenName);
				}
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Could not save check sheet details on "+screenName);
		}
	}
	/**
	 * @author A-9844
	 * @Desc To enter the Obligatory answer of checksheet as YES/NO based on questions
	 * @param chkSheetRequired
	 * @param answer
	 */
	public void captureCheckSheetAnswers(boolean chkSheetRequired,String answer)
	{  

		boolean checkSheetExists=true;
		try
		{

			List <WebElement> questions=driver.findElements(By.xpath("//p[@style='display:inline']"));
			if(questions.size()==0)
			{
				checkSheetExists=false;
			}
			int i=0;
			for(WebElement ele : questions)
			{
				System.out.println(ele.getText());
				if(ele.getText().contains(answer))
				{	
					selectValueInDropdownWthXpath("//select[@name='questionwithAnswer["+i+"].templateAnswer']","No", ele.getText(), "VisibleText");
					i++;
				}
				else
				{
					selectValueInDropdownWthXpath("//select[@name='questionwithAnswer["+i+"].templateAnswer']","Yes", ele.getText(), "VisibleText");
					i++;
				}

			}
			if(chkSheetRequired)
			{
				if(checkSheetExists)
				{
					writeExtent("Pass","Check sheet details selected on "+screenName);
				}

				else
				{
					writeExtent("Fail","No check sheet details configured on "+screenName);
				}
			}

			clickWebElement("Generic_Elements", "btn_save;xpath", "Save Button", screenName);
			switchToFrame("default");
			clickWebElement("Generic_Elements", "btn_dialogOkBtn2;xpath", "OK Button", screenName);
			waitForSync(3);


			if(chkSheetRequired)
			{
				if(checkSheetExists)
				{
					writeExtent("Pass","Check sheet details saved on "+screenName);
				}
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail","Could not save check sheet details on "+screenName);
		}
	}

	/**
	 * Description..Click print button and verify the report
	 * @throws Exception
	 */
	public void clickPrint() throws Exception {
		waitForSync(2);
		clickWebElement(sheetName,"btn_Print;xpath","Save Button", screenName);
		waitForSync(5);
		switchToWindow("storeParent");
		switchToWindow("child");
		waitForSync(5);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("ReportContainerFrame");
		verifyElementDisplayed(sheetName,"txt_ReportTitle;xpath","Verify the Report Title", screenName,"CheckSheet title");

		switchToWindow("closeChild");
		waitForSync(2);
		switchToWindow("getParent");
		waitForSync(2);
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameCHK002");
		waitForSync(2);
	}

	/**
	 * Description..Click print button and verify the report
	 * @throws Exception
	 */
	public void verifyPrint() throws Exception {
		waitForSync(2);
		clickWebElement(sheetName,"btn_Print;xpath","Print Button", screenName);
		waitForSync(5);

		switchToWindow("multipleWindows");

		int windowSize=getWindowSize();

		if(windowSize==2)
		{
			onPassUpdate(screenName, "check sheet report is generated", "check sheet report is generated", "Verify whether the report is generated",
					"Verify whether the check sheet report is generated");
		}
		else
		{
			onFailUpdate(screenName, "check sheet report is generated", "check sheet report is not generated", "Verify whether the report is generated",
					"Verify whether the check sheet report is generated");
		}
		closeBrowser();
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","CHK002");

	}

	/**
	 * Description.. Select the answer from dropdown list
	 * @param answer
	 * @throws InterruptedException
	 */
	public void select3Question(String answer) throws InterruptedException {
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_1stQuestion;xpath", answer, "1st Question", "VisibleText");
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_1stQuestion;xpath", answer, "1st Question", "VisibleText");
		waitForSync(2);
		selectValueInDropdown(sheetName, "lst_1stQuestion;xpath", answer, "1st Question", "VisibleText");
		waitForSync(2);
	}

	public void enterCheckSheetAns()
	{

		boolean checkSheetExists=true;
		try
		{

			List <WebElement> questions=driver.findElements(By.xpath("//select[@class='iCargoSmallComboBox']"));

			if(questions.size()==0)
			{
				checkSheetExists=false;
			}

			for(WebElement ele : questions)
			{
				Select select = new Select(ele);
				select.selectByVisibleText("Yes");
			}

			if(checkSheetExists)
			{
				writeExtent("Pass","Check sheet details selected on "+screenName);
			}

			else
			{
				writeExtent("Fail","No check sheet details configured on "+screenName);
			}

		}

		catch(Exception e)
		{
			writeExtent("Fail","Could not select check sheet details on "+screenName);
		}
	}
}