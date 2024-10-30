package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainAircraftType_SHR003 extends CustomFunctions
{
	
	String sheetName = "MaintainAircraftType_SHR003";
	public CustomFunctions cust;
	String screenID = "SHR003";
	public String screenName = "Maintain Aircraft Type";

	public MaintainAircraftType_SHR003(WebDriver driver,ExcelReadWrite excelReadWrite, Xls_Read xls_Read2)
	{
		super(driver, excelReadWrite, xls_Read2);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
	}
/**
 * Description... List Aircraft		
 * @param AircraftTypeCode
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	public void listAircraft(String AircraftTypeCode) throws InterruptedException, AWTException, IOException {

		enterValueInTextbox(sheetName, "inbx_AircraftTypeCode;xpath", AircraftTypeCode, "Aircraft Type Code", screenName);
		keyPress("TAB");
		Thread.sleep(2000);	
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		Thread.sleep(8000);	
		waitForSync(2);
		
	}
/**
 * Description...	Basic Aircraft Type Data
 * @throws InterruptedException
 * @throws AWTException
 */
	
	public void basicAircraftTypeData() throws InterruptedException, AWTException 
	{
		switchToFrame("contentFrame", "SHR003");
		String version = "1";
		enterValueInTextbox(sheetName, "inbx_Version;xpath", version, "Version", screenName);
		keyPress("TAB");
		
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lst_Type;xpath")));
		Select sel = new Select(ele);
		sel.selectByVisibleText("Cargo-Only");
		
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "chkbx_bulkposition;xpath")));
		clickWebElement(ele,"Bulk position checkbox", screenName);
		
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lnk_bulkcmpdelete;xpath")));
		clickWebElement(ele,"Bulk compartment delete button ", screenName);
		
	}
/**
 * Description... Add Standard Capacity	
 * @param Configuration_name
 * @throws InterruptedException
 * @throws AWTException
 */
	public void addStandardCapacity(String Configuration_name) throws InterruptedException, AWTException
	{
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lnk_addcapacity;xpath")));
		clickWebElement(ele,"Add capacity link ", screenName);
		
		Thread.sleep(2000);
		
		
		enterValueInTextbox(sheetName, "inbx_ConfigurationName;xpath", Configuration_name, "Configuration Name", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_Weight;xpath", data("Weight"), "Wieght ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_Volume;xpath", data("Volume"), "Volume ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_Q7;xpath", data("Q7"), "Q7  ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_MDP;xpath", data("MDP"), "MDP  ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_LDP;xpath", data("LDP"), "LDP  ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_LDC;xpath", data("LDC"), "LDC  ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_MVP_Q7;xpath", data("Max_Q7"), "Maximum volume position Q7  ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_MVP_MDP;xpath", data("Max_MDP"), "Maximum volume position MDP  ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_MVP_LDP;xpath", data("Max_LDP"), "Maximum volume position LDP  ", screenName);
		keyPress("TAB");
		
		enterValueInTextbox(sheetName, "inbx_MVP_LDC;xpath", data("Max_LDC"), "Maximum volume position LDC  ", screenName);
		keyPress("TAB");
		
		Thread.sleep(5000);
		
	}
/**
 * Description...	Save Aircraft Type
 * @throws InterruptedException
 * @throws AWTException
 */
	public void saveAircraftType() throws InterruptedException, AWTException
	{ 
		
		ele = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "btn_Save;xpath")));
		clickWebElement(ele,"Save button ", screenName);
		Thread.sleep(5000);
	}
	
/**
 * Description...	Verify Success Message
 * @throws InterruptedException
 */
	public void verifySucessMsg() throws InterruptedException
	{
		
		
		String aircraftStatus=getElementText(sheetName, "htmlDiv_Successmsg;xpath", "Message Status", screenName);
	        
	        if(aircraftStatus.contains("The data has been successfully saved."))
	        {
	        	writeExtent("Pass", "Aircraft created successfully");
	        }
	        else
	        {
	        	writeExtent("Fail", "Aircraft created successfully.Aircraft status is "+aircraftStatus);	
	        }
	}
	/**
	 * @author A-9175
	 * @Desc : Get aircraft capacity details
	 * @return
	 */
	public List<String> getAircraftCapacityDetails() {
		
		List<String> capacityDetails=new ArrayList<String>();
		String wgt=getAttributeWebElement(sheetName, "inbx_wtcapacity;xpath", "Aircraft weight", "value", screenName);
		String vol=getAttributeWebElement(sheetName, "inbx_volcapacity;xpath", "Aircraft Vol", "value", screenName);
		capacityDetails.add(wgt);capacityDetails.add(vol);
		return capacityDetails;
	}
	
}
