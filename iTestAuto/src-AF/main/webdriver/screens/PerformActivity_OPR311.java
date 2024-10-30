package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.testng.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;

import com.relevantcodes.extentreports.LogStatus;







import common.CustomFunctions;
import common.DriverSetup;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class PerformActivity_OPR311 extends CustomFunctions {
	
	public String performActivityProppath = "\\src\\resources\\PerformActivityTime.properties";
	String sheetName = "PerformActivity_OPR311";
	String screenName = "Perform Activity: OPR311 ";
	String screenId="OPR311";
	

	public PerformActivity_OPR311(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	/**
	 * @author A-8783
	 * Desc - Select obligatory answer
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void selectObligatoryAnswer(String answer) throws InterruptedException, AWTException {

		selectValueInDropdown(sheetName, "lst_actualValue;name",answer, "Obligatory Answer", screenName);
		

	}


	/**
	 * @Description... List Awb untill the activation time crosses
	 * @author A-10690
	 * @param AWBno
	 * @param Shipmentprefix
	 * @throws InterruptedException
	 */
	
	public void listAWB(String awbNo, String ShipmentPrefix) throws InterruptedException, IOException {

		String sheetName = "Generic_Elements";

		String locator=xls_Read.getCellValue("PerformActivity_OPR311", "txt_errormsg;xpath");

		String time=getPropertyValue(performActivityProppath,"waitFor");
		int t=Integer.parseInt(time);
		System.out.println(t);
		for(int i=0;i<=t;i++)
		{

			listAWB(awbNo,ShipmentPrefix,screenName);
			waitForSync(3);
			try
			{
				WebElement errormsg=driver.findElement(By.xpath(locator));
				if(driver.findElement(By.xpath(locator)).isDisplayed())

					waitForSync(6);

				else

					break;

			}
			catch(Exception e)
			{
				break;
			}
		}
		}
		
	/**
	 * @author A-9844
	 * To verify the temperature sign in the dropdown-+,- and blank
	 */
	public void verifyTemperatureDropDownValues(){

		
		String loc= xls_Read.getCellValue(sheetName, "drpdn_temperatureSign;xpath");
		List<WebElement> vals= driver.findElements(By.xpath(loc));
		for(int i=0;i<vals.size();i++){
			String val=vals.get(i).getText();
			if(val.equals("+") || val.equals("-") || val.equals(""))
				writeExtent("Pass","Successfully verified temperature sign: "+val+ " on "+screenName);
			else
				writeExtent("Fail","Fail to verify the temperature sign on "+screenName);
		}

	}



	/**@author A-9844
	 * @des - To select the temperature sign
	 * @param billingType
	 * @throws InterruptedException 
	 */
	public void selectTemperatureSign(String tempSign) throws InterruptedException{

		//Billing Type Values -> '+' and  '-' and ""
		
 		selectValueInDropdown(sheetName, "inbx_tempSign;xpath", data(tempSign), "temperature sign", "VisibleText");
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Description... Enter temperature value
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void enterTemperatureValue(String tempValue) throws InterruptedException, AWTException {

		enterValueInTextbox(sheetName, "txt_actualValue;name",data(tempValue), "temperature value", screenName);
		

	}
	/**
	 * @Description... Verify unit value displayed
	 * @author A-9844
	 * @param expUnit	 
	 */

	public void verifyUnitValue(String expUnit)
	{
		String locator = xls_Read.getCellValue(sheetName, "txt_unitValue;xpath");
		String actUnit=driver.findElement(By.xpath(locator)).getText();
		System.out.println(expUnit);
		verifyScreenText(screenName, expUnit, actUnit, "Verify unit value displayed", "Verify unit value displayed");

	}
/**
	 * @author A-9844
	 * Desc- Click save
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickSave() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_save;xpath", "Save",screenName);
	}

	/**
	 * @author A-9844
	 * Desc- Click close
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCloseFromOPR311() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_close;xpath", "Close",screenName);
		waitForSync(3);
	}



	/**
	 * @Description... Verify Column scc in perform activity screen
	 * @author A-10690
	 * @param SCC	 
	 * @throws InterruptedException
	 */
	
	public void verifyColumnScc(String scc)throws InterruptedException, IOException
	{
		String locator = xls_Read.getCellValue(sheetName, "table_sccColumn;xpath");
		locator=locator.replace("*", scc);
		if(driver.findElement(By.xpath(locator)).isDisplayed()) {
			onPassUpdate(screenName,"Column "+ scc ,"template "+ scc, "Verification of scc column","Verification of scc column");
		} else {
			onFailUpdate(screenName, "Column "+ scc ,"template "+ scc, "Verification of scc column","Verification of scc column");
		}
	}
	
	/**
	 * @Description... Verify Column Shipment description in perform activity screen
	 * @author A-10690
	 * @param shipmentdescription	 
	 * @throws InterruptedException
	 */
	
	public void verifyColumnShipdescription(String shipmentdescription)throws InterruptedException, IOException
	{
		String locator = xls_Read.getCellValue(sheetName, "table_shipdesc;xpath");
		locator=locator.replace("*", shipmentdescription);
		if(driver.findElement(By.xpath(locator)).isDisplayed()) {
			onPassUpdate(screenName,"Column "+ shipmentdescription ,"template "+ shipmentdescription, "Verification of shipmentdescription column","Verification of shipmentdescription column");
		} else {
			onFailUpdate(screenName, "Column "+ shipmentdescription ,"template "+ shipmentdescription, "Verification of shipmentdescription column","Verification of shipmentdescription column");
		}
	}
	
	/**
	 * @Description... Verify Postfix* following the awb no in shipment prefix screen
	 * @author A-10690
	 * @param awb	 
	 * @throws InterruptedException
	 */
	
	public void verifyPostfixSplitindicator(String awb)
	{
		String locator = xls_Read.getCellValue(sheetName, "table_awbdata;xpath");
		locator=locator.replace("*",data(awb));
		String expText=driver.findElement(By.xpath(locator)).getText();
		String actText=data(awb)+"*";
		verifyScreenText(screenName, expText, actText, "Verfying the awbpostfix * icon", "Verfying the awbpostfix * icon");
	
	}
	}
	
	
	

