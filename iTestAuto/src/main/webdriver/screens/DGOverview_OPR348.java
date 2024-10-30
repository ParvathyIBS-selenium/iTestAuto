package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class DGOverview_OPR348 extends CustomFunctions {

	public DGOverview_OPR348(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	public String sheetName = "DGOverview_OPR348";
	public String screenName = "DGOverview_OPR348";
/**
 * Description...  Click AWB link and Switch to the Child Window
 * @param AWBno
 * @throws Exception
 */
	public void clickAWBlink(String AWBno) throws Exception{
		String dyxpath = "//a[contains(.,'"+AWBno+"')]";
		clickWebElement(dyxpath, AWBno+" link", screenName);
		switchToWindow("storeParent");
		switchToWindow("child");
		
	}
/**
 * Description...  List eFrieght AWB
 * @param awbNo
 * @param Prefix
 * @param FilterMode
 * @throws Exception
 */
	public void listeFrieghtAWB(String awbNo, String Prefix,String FilterMode) throws Exception {
		enterValueInTextbox(sheetName, "inbx_awbNumberPrefix;name", data(Prefix), "Prefix", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;name", data(awbNo), "AWB No", screenName);
		
		enterValueInTextbox(sheetName, "inbx_FromDate;id", createDateFormat("ddMMMYY",-1,"DAY",""), "From Date", screenName);
		enterValueInTextbox(sheetName, "inbx_ToDate;id", createDateFormat("ddMMMYY",+1,"DAY",""), "To Date", screenName);
		selectValueInDropdown(sheetName, "lst_FilterMode;id", data(FilterMode), "FilterMode","VisibleText");
		
		clickWebElement(sheetName, "btn_list;id", "List Button", screenName);
		waitForSync(4);
	}
/**
 * Description...  Verify eDGD Status for Capable and Non Capable Lane
 * @param AWBNumber
 * @param Capable_nonCapableLane
 * @throws InterruptedException
 */
	public void verifyeDGDStatus(String AWBNumber,String Capable_nonCapableLane) throws InterruptedException {
		
		String xpath = xls_Read.getCellValue(sheetName, "tbl_dgOverview;xpath");
		String dynxpath = xpath + "[contains(text(),'"+ AWBNumber + "')]/..//div";
		
		switch (Capable_nonCapableLane) {
		
		case "NonCapableLane":
			String imgxpath = dynxpath + "[@class='iCeDgeXButton']";
			WebElement img = driver.findElement(By.xpath(imgxpath));
			verifyElementDisplayed(img, "edgd verification", screenName, "non capable lane status");
			break;
			
		case "CapableLane":
			String imgxpath2 = dynxpath + "[@class='iCeDgeCButton']";
			WebElement img2 = driver.findElement(By.xpath(imgxpath2));
			verifyElementDisplayed(img2, "edgd verification", screenName, "capable lane status");
			break;
		
		}

	}
/**
 * Description...  Select AWB CheckBox	
 * @param AWBNumber
 * @throws InterruptedException
 */
	public void selectAWB(String AWBNumber) throws InterruptedException {
		
		String xpath = xls_Read.getCellValue(sheetName, "tbl_dgOverview;xpath");
		String dynxpath = xpath + "[contains(text(),'"+ AWBNumber + "')]/ancestor::tr//input[1]";
		
		clickWebElement(dynxpath, AWBNumber+" checkbox", screenName);

	}
/**
 * Description...  	click  Start/Stop DG Acceptance Check
 * @throws Exception
 */
	public void clickStart_StopDGAcceptanceCheck() throws Exception {
		
		clickWebElement(sheetName, "btn_Start_StopDGAcceptanceCheck;id", "List Button", screenName);
		waitForSync(4);
		
	}
/**
 * Description...  	Verify Hand Symbol	
 * @param AWBNumber
 * @throws InterruptedException
 */
	public void verifyHandSymbol(String AWBNumber) throws InterruptedException {
		
		String xpath = xls_Read.getCellValue(sheetName, "tbl_dgOverview;xpath");
		String dynxpath = "("+xpath + "[contains(text(),'"+ AWBNumber + "')]/ancestor::tr//td)[1]//div[contains(@class,'thumbpopover')]";
		
		if (driver.findElement(By.xpath(dynxpath)).isDisplayed()) {
			System.out.println("Hand symbol is displayed");
			writeExtent("Pass", "Hand symbol is displayed");
		} else {
			System.out.println("Hand symbol is not displayed");
			writeExtent("Fail", "Hand symbol is not displayed");
		}
		
		

	}
/**
 * Description...  List AWB	
 * @throws IOException 
 */
	public void listAWB(String awbNo, String ShipmentPrefix, String ScreenName) throws InterruptedException, IOException {

              
              enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",
                           ScreenName);
              enterValueInTextbox(sheetName, "inbx_AWBnumber;id", data(awbNo), "AWB No", ScreenName);
              clickWebElement(sheetName, "btn_list;id", "List Button", ScreenName);
              waitForSync(4);

       }

}