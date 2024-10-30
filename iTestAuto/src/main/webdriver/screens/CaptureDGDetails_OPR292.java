package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CaptureDGDetails_OPR292 extends CustomFunctions {

	public CaptureDGDetails_OPR292(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	public String sheetName = "CaptureDGDetails_OPR292";
	public String screenName = "Capture DG Details";

	public void captureDGRDetails(String UNIDNo, String properShippingName,
			String netQuantityperPackage, String noOfPackage,
			String PerPackageUnit) throws InterruptedException, IOException {
		
		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
				"UNID No", screenName);
		enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
				netQuantityperPackage, "Net Quantity Per Package", screenName);
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
				"No Of Package", screenName);
		selectValueInDropdown(sheetName, "lst_properShipName;xpath",
				properShippingName, "Proper Shipping Name", "Value");

		selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
				PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);

	}


	/**
	 * Description...Returns isContentEditable property using JavaScript
	 * 
	 * @param sheetName
	 * @param locator
	 * @param eleName
	 * @param ScreenName
	 * @param ID
	 * @return isContentEditable property
	 */

	/*
	 * Author : A-7688 Date Modified : 29/08/2017 Purpose : Generic method to
	 * verify an element is disabled using JavaScript
	 */
	public boolean checkDisabledUsingJavascript(String sheetName,
			String locator, String eleName, String ScreenName, String ID) {
		boolean actValue = false;

		try {
			By b = getElement(sheetName, locator);
			ele = driver.findElement(b);

			actValue = (Boolean) ((JavascriptExecutor) driver)
					.executeScript("return document.getElementById('" + ID
							+ "').isContentEditable");

		}

		catch (Exception e) {

			e.printStackTrace();
			System.out.println("Could not get attribute of element " + eleName
					+ " on " + ScreenName + "Screen");
			writeExtent("Fail", "Could not get attribute of element " + eleName
					+ " on " + ScreenName + "Screen");

		}
		return actValue;

	}

	public void clickTypeOfPacking() throws Exception {
		clickButtonSwitchWindow(sheetName, "btn_typeOfPacking;xpath",
				 screenName,"Type Of Packing Button");

	}

	public void overPackingDetails(String packType,String numberOfPackages, String Length,
			String Width, String Height) throws Exception {
		
		if(packType.equals("Overpack"))
		
		clickWebElement(sheetName, "chk_overPackFlag;xpath",
				"Overpack used Check Box", screenName);
		else if(packType.equals("AllPackedInOne"))
			clickWebElement(sheetName, "chk_allPackedFlag;xpath",
					"All Packed in one Check Box", screenName);
		
		enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
				numberOfPackages, "Number Of Packages", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionLength;xpath",
				Length, "Dimension Length", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionWidth;xpath",
				Width, "Dimension Width", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionHeight;xpath",
				Height, "Dimension Height", screenName);
		clickButtonSwitchtoParentWindow("Generic_Elements", "btn_childWinOk;xpath", "OK Button", screenName);

	}

public void captureDGDetailsRadioactive(String UNIDNo, String properShippingName,
			String netQuantityperPackage, String noOfPackage,
			String RMC, String TI) throws InterruptedException, IOException{
		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
				"UNID No", screenName);
		enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
				netQuantityperPackage, "Net Quantity Per Package", screenName);
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
				"Net Quantity Per Package", screenName);
		selectValueInDropdown(sheetName, "lst_properShipName;xpath",
				properShippingName, "Proper Shipping Name", "Value");
	
		selectValueInDropdown(sheetName, "lst_RMC;xpath",
				RMC, "RMC", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_transportIndex;xpath", TI,
				"Transport Index", screenName);
		
		
		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
	}
	
	public void overPackingDetailsRadioactive(String packType,String numberOfPackages, String Length,
			String Width, String Height,String RMC, String TransportIndex) throws Exception {
		
		if(packType.equals("Overpack"))
		
		clickWebElement(sheetName, "chk_overPackFlag;xpath",
				"Overpack used Check Box", screenName);
		else if(packType.equals("AllPackedInOne"))
			clickWebElement(sheetName, "chk_allPackedFlag;xpath",
					"All Packed in one Check Box", screenName);
		
		enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
				numberOfPackages, "Number Of Packages", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionLength;xpath",
				Length, "Dimension Length", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionWidth;xpath",
				Width, "Dimension Width", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionHeight;xpath",
				Height, "Dimension Height", screenName);
		selectValueInDropdown(sheetName, "lst_RMC_packagingDetails;xpath",
				RMC, "RMC", "VisibleText");
		
		enterValueInTextbox(sheetName, "inbx_transportIndex_packagingDetails;xpath",
				TransportIndex, "Transport Index", screenName);
		clickButtonSwitchtoParentWindow("Generic_Elements", "btn_childWinOk;xpath", "OK Button", screenName);

	}


public void enterDGDetails(String UNIDNo, String properShippingName,
			String netQuantityperPackage, String noOfPackage,
			String PerPackageUnit) throws InterruptedException {

		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
				"UNID No", screenName);
		enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
				netQuantityperPackage, "Net Quantity Per Package", screenName);
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
				"No Of Package", screenName);
		selectValueInDropdown(sheetName, "lst_properShipName;xpath",
				properShippingName, "Proper Shipping Name", "Value");
		selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
				PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
		waitForSync(1);
	}

public void changeDGDetails(String ShipmentClass, String erg)
			throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_shipmentClass;xpath",
				ShipmentClass, "Shipment Class", screenName);
		enterValueInTextbox(sheetName, "inbx_erg;xpath", erg, "erg", screenName);
		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);

	}

public void modifyUNIDdetails(String UNID,  String newUNID , 
        String properShippingName, String netQuantityperPackage, String noOfPackage, String PerPackageUnit)
{
 try{
        String xpath = "//a[contains(.,'"+ UNID +"')]";
        driver.findElement(By.xpath(xpath)).click();
        waitForSync(4);
        
        enterDGDetails(newUNID, properShippingName, netQuantityperPackage, noOfPackage, PerPackageUnit);
        
        clickWebElement(sheetName, "btn_modify;id", "Update Button", screenName);
        
        
        
 }catch(Exception e){
        
        
 }
 
}


}