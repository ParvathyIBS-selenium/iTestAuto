package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class TracingReports_TRC006 extends CustomFunctions {
	public TracingReports_TRC006(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "TracingReports_TRC006";
	public String ScreenName = "TracingReports";
	
	/**
	 * @author A-7271
	 * @param dmgCode
	 * @param dmgPcs
	 * Description : add damage details
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void addDamageDetails(String dmgCode,String dmgPcs,String dmgDetails) throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "lnk_add;xpath", "Add button", ScreenName);
		waitForSync(3);
		
		enterValueInTextbox(sheetName, "inbx_dmgCode;xpath",data(dmgCode), "DamageCode", ScreenName);
		enterValueInTextbox(sheetName, "inbx_dmgPcs;xpath",data(dmgPcs), "DamagePcs", ScreenName);
		enterValueInTextbox(sheetName, "inbx_dmgDetails;xpath",data(dmgDetails), "DamageDetails", ScreenName);
	}
	/**
	 * 
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : listReportWithAWB
	 */
	public void listReportWithAWB(String awbNo,String ShipmentPrefix) throws InterruptedException, IOException{
		awbNo = getPropertyValue(proppath, awbNo);
		System.out.println("AWBnumber is ---" + awbNo);
		waitForSync(2);
		
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;name", data(ShipmentPrefix), "Shipment Prefix",ScreenName);
		
		enterValueInTextbox(sheetName, "inbx_AWBnumber;name", awbNo, "AWB No", ScreenName);
		clickWebElement(sheetName, "btn_List;name", "List Button", ScreenName);
		waitForSync(4);
	}
	
	/**
	 * @author A-7271
	 * @throws Exception 
	 */
	public void printDamage() throws Exception
	{
		waitForSync(2);
		clickWebElement(sheetName, "btn_print;name", "Print Button", ScreenName);
		waitForSync(6);

		int windowSize=getWindowSize();


		if(windowSize==2)
		{
			switchToWindow("storeParent");
			switchToWindow("multipleWindows");
			closeBrowser();
			waitForSync(2);
			switchToWindow("getParent");
			switchToFrame("default");
			switchToFrame("contentFrame","TRC006");
		}

	}
	/**
	 * @author A-6260
	 * Desc.. Verify stated piece and weight
	 * @param Pieces
	 * @param Weight
	 * @param Shipmentdescription
	 * @throws InterruptedException
	 */
	public void verifyStatedPiecesAndWeight(String Pieces,String Weight,String Shipmentdescription) throws InterruptedException
	{
		String StatedPiecesLocator=xls_Read.getCellValue(sheetName, "txt_statedPieces;xpath");
		String actText_StatedPieces = driver.findElement(By.xpath(StatedPiecesLocator)).getAttribute("Value");
		
		String StatedWeightLocator=xls_Read.getCellValue(sheetName, "txt_statedWeight;xpath");
		String actText_StatedWeight = driver.findElement(By.xpath(StatedWeightLocator)).getAttribute("Value");
		
		String descriptionLocator=xls_Read.getCellValue(sheetName, "txt_description;xpath");
		String actText_description = driver.findElement(By.xpath(descriptionLocator)).getText();

		if (actText_StatedPieces.contains(data(Pieces)) && actText_StatedWeight.contains(data(Weight)) && actText_description.contains(data(Shipmentdescription))) {
			verifyScreenText(sheetName, data(Pieces), actText_StatedPieces, actText_StatedPieces, ScreenName);
			verifyScreenText(sheetName, data(Weight), actText_StatedWeight, actText_StatedWeight, ScreenName);
			verifyScreenText(sheetName, data(Shipmentdescription), actText_description, actText_description, ScreenName);
		} else {
			verifyScreenText(sheetName,data(Pieces), actText_StatedPieces, actText_StatedPieces, ScreenName);
			verifyScreenText(sheetName, data(Weight), actText_StatedWeight, actText_StatedWeight, ScreenName);
			verifyScreenText(sheetName, data(Shipmentdescription), actText_description, actText_description, ScreenName);
		}

	}
	/**
	 * @author A-8783
	 * Desc - Add damage details in a specific row
	 * @param dmgCode
	 * @param dmgPcs
	 * @param dmgDetails
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void addDamageDetailsRowWise(String dmgCode,String dmgPcs,String dmgDetails,String row) throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "lnk_add;xpath", "Add button", ScreenName);
		waitForSync(3);
		String locatorCode = xls_Read.getCellValue(sheetName, "inbx_damageCode;xpath");
		locatorCode=locatorCode.replace("*", row);
		enterValueInTextbox(locatorCode, data(dmgCode), "DamageCode", ScreenName);
		
		String locatorPcs = xls_Read.getCellValue(sheetName, "inbx_damagePieces;xpath");
		locatorPcs=locatorPcs.replace("*", row);
		enterValueInTextbox(locatorPcs, data(dmgPcs), "DamagePieces", ScreenName);
		
		String locatorDmgDetails = xls_Read.getCellValue(sheetName, "inbx_damageDetails;xpath");
		locatorDmgDetails=locatorDmgDetails.replace("*", row);
		enterValueInTextbox(locatorDmgDetails, data(dmgDetails), "DamageDetails", ScreenName);
	}

	/**
	 * @author A-6260
	 * Desc.. add damage details
	 * @param dmgCode
	 * @param dmgPcs
	 * @param dmgDetails
	 * @throws Exception
	 */
	public void addDamageDetails(String[] dmgCode,String[] dmgPcs,String[] dmgDetails) throws Exception
	{
		int size=dmgCode.length;
		try
		{
			for(int i=0;i<size;i++) {
				clickWebElement(sheetName, "lnk_add;xpath", "Add button", ScreenName);
				waitForSync(2);
				String dmgCodeLocator=xls_Read.getCellValue(sheetName,"inbx_damageCode;id").replace("*", Integer.toString(i));
				String dmgPcsLocator=xls_Read.getCellValue(sheetName,"inbx_damagePcs;id").replace("*", Integer.toString(i));
				String dmgDetailsLocator=xls_Read.getCellValue(sheetName,"inbx_damageDetails;id").replace("*", Integer.toString(i));
				
				driver.findElement(By.id(dmgCodeLocator)).sendKeys(dmgCode[i]);
				driver.findElement(By.id(dmgPcsLocator)).sendKeys(dmgPcs[i]);
				driver.findElement(By.id(dmgDetailsLocator)).sendKeys(dmgDetails[i]);
				
			}
			writeExtent("Pass", "Successfully entered damage details  in "+ScreenName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writeExtent("Fail", "Couldn't enter damage details in "+ScreenName);
		}
	}
	/**
	 * @author A-7943
	 * @throws Exception
	 * Description : Add Report type
	 */
	public void addReportType(String reportType) throws InterruptedException{
	    selectValueInDropdown(sheetName,"drpdwn_reportType;name",reportType,"Report","VisibleText");
		waitForSync(3);

	}

	/**
	 * @author A-7271
	 * @throws Exception
	 * Description : print damage details
	 */
	public void printFromAcceptanceScreen() throws Exception
	{
		switchToWindow("storeParent");
		clickWebElementByWebDriver(sheetName, "btn_print;name", "Print Button", ScreenName);
		waitForSync(3);
		switchToWindow("multipleWindows");
		
		int windowSize=getWindowSize();
	
		if(windowSize==2)
		{
			onPassUpdate(ScreenName, "window size should be 2 ", "window size is "+windowSize, "Verify whether the report is generated for capture damage details ",
					"Verify whether the report is generated for capture damage details");
		}
		else
		{
			onFailUpdate(ScreenName, "window size should be 2 ", "window size is "+windowSize, "Verify whether the report is generated for capture damage details",
					"Verify whether the report is generated for capture damage details");
		}
		closeBrowser();
		switchToWindow("getParent");
		switchToFrame("default");
		switchToFrame("contentFrame","OPR335");
		waitForSync(2);
	}
	
	public void close() throws InterruptedException
	{
		
	clickWebElementByWebDriver(sheetName, "btn_close;xpath", "Close Button", ScreenName);

		waitForSync(3);
	}

}