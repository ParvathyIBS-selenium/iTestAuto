package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CaptureDGDetails_OPR350 extends CustomFunctions {

	public CaptureDGDetails_OPR350(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	public String sheetName = "CaptureDGDetails_OPR350";
	public String screenName = "Capture DG Details";
/**
 * Description... Capture DGR Details
 * @param UNIDNo
 * @param properShippingName
 * @param netQuantityperPackage
 * @param noOfPackage
 * @param PerPackageUnit
 * @throws InterruptedException
 * @throws IOException 
 */
	public void captureDGRDetails(String UNIDNo, String properShippingName,
			String netQuantityperPackage, String noOfPackage,
			String PerPackageUnit) throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
				"UNID No", screenName);
		enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
				netQuantityperPackage, "Net Quantity Per Package", screenName);
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
				"No Of Package", screenName);
		waitForSync(3);
		selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
				properShippingName, "Proper Shipping Name", "Value");

		selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
				PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
		waitForSync(1);
	}
	/**
	 * Description... verify packaging details
	 * @author A-9844
	 * @param UNID
	 * @param Packagedetails
	 * @throws InterruptedException,IoException
	 */	

	public void verifyOverpackDetails(String packageDetails) throws InterruptedException, IOException {

		//edit unid details
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR350");
		driver.switchTo().frame("popupContainerFrame");
		String id  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "inbx_packagedetails;id");
		WebElement packdetails= driver.findElement(By.id(id));
		String overpackValue=packdetails.getText();
		if(overpackValue.equals(packageDetails)){
			writeExtent("Pass","Verified packaging details displayed as :"+overpackValue+" on "+screenName);
		}
		else{
			writeExtent("Fail","packaging detail is displayed as :"+overpackValue+" on "+screenName);
		}
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR350");
		waitForSync(2); 

	}	
	/**
	 * Description...clicking cancel button in the pop up window
	 * @author A-10690
	 * @throws Exception
	 */  


	public void clickCancelButtonPopUp() throws Exception {

		waitForSync(5);     
	    clickWebElement(sheetName, "btn_cancel;name",
				"cancel button", screenName);
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR350");
		waitForSync(5);  


	}


	/**
	 * Description... Edit UNID details netquantity per package
	 * @author A-10690
	 * @param UNID
	 * @param netquantityperpackage
	 * @throws InterruptedException,IoException
	 */	



	public void modifyNetQuantity(String unid,String netquantity) throws InterruptedException, IOException {



		//edit unid details
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR350");
		driver.switchTo().frame("popupContainerFrame");
		String netquantityperpackage  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "inbx_netQuantityPerPackage;xpath");
		WebElement packdetails= driver.findElement(By.xpath(netquantityperpackage));
		packdetails.clear();
		enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
				netquantity, "Net Quantity Per Package", screenName);
		clickWebElement(sheetName, "btn_update;name", "update button", screenName);
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR350");
		waitForSync(2); 

	}	



	/**
	 * Description... Click close button for the error pop up
	 * @author A-10690
	 * @throws InterruptedException,IoException
	 */	

	public void closeError() throws InterruptedException, IOException {

		waitForSync(3);
		clickWebElement(sheetName, "btn_closeerror;xpath", " error close button", screenName);

	}

	
	/**
	 * Description... clicking save button after updating package details
	 * @author A-10690
	 * @throws InterruptedException,IoException
	 */	

	public void saveupadtedPackagingdetails() throws InterruptedException, IOException {

		
		waitForSync(2);
		switchToFrame("default");
		driver.switchTo().frame("iCargoContentFrameOPR350");
		waitForSync(2);
		clickWebElement(sheetName, "btn_save;id", "Save Button", screenName);
		waitForSync(3);

	}
	/**
	 * @author A-9847
	 * @Desc To verify the DG details of a DG shipment retrieved from Cafeed
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmkey
	 * @throws IOException
	 */
	public void verifyAwbDGDetails(int verfCols[], String actVerfValues[],String pmkey) throws IOException
	{
		waitForSync(2);
		verify_tbl_records_multiple_cols(sheetName, "table_awbDgDetails;xpath", "//td", verfCols, data(pmkey),
				actVerfValues);
		waitForSync(3);
	}
	
	
	/**
	 * Description... Capture DGR Details
	 * @param UNIDNo
	 * @param properShippingName
	 * @param netQuantityperPackage
	 * @param noOfPackage
	 * @param PerPackageUnit
	 * @throws InterruptedException
	 */
		public void captureDGRDetailsWdoutAdd(String UNIDNo, String properShippingName,
				String netQuantityperPackage, String noOfPackage,
				String PerPackageUnit) throws InterruptedException {

			enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
					"UNID No", screenName);
			enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
					netQuantityperPackage, "Net Quantity Per Package", screenName);
			enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
					"No Of Package", screenName);
			waitForSync(3);
			selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
					properShippingName, "Proper Shipping Name", "Value");

			selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
					PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
			
		}
		
		/**
		 * Description... Verify the system generated no on perform APIO/OVP
		 * @author A-10690
		 * @param UNID
		 * @throws InterruptedException,IoException
		 */		
	public void verifySystemGeneratedNo(String unid) throws InterruptedException, IOException {

 
		waitForSync(1);
		String xpath  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "txt_Packaging details;xpath");
		String  locator=xpath.replace("UNID", data(unid));
		String systemGeneratedNo=driver.findElement(By.xpath(locator)).getText();
		if(driver.findElement(By.xpath(locator)).isDisplayed())
		
			writeExtent("Pass","Text "+systemGeneratedNo+"  exists on "+screenName+" .Functionality is chceking the systemgenerated number on OVP/APIO");
			
			else
				writeExtent("Fail","Text "+systemGeneratedNo+" not exists on "+screenName+" .Functionality is chceking the systemgenerated number on OVP/APIO");
	}
	/**
	 * Description... Select edit button
	 * @author A-10690
	 * @param UNID
	 * @throws InterruptedException,IoException
	 */	

	public void selectEditbutton(String unid) throws InterruptedException, IOException {


		String xpath= xls_Read.getCellValue(sheetName, "txt_siNo;xpath");
		String  locator1=xpath.replace("UNID",data(unid));
		String sNo = driver.findElement(By.xpath(locator1)).getText();
		int siNo= Integer.parseInt(sNo);
		String id=String.valueOf(siNo-1);
		waitForSync(1);
		String xpath3= xls_Read.getCellValue(sheetName, "btn_editbutton;xpath");
		String  locator3=xpath3.replace("*",id);
		driver.findElement(By.xpath(locator3)).click();


	}
	/**
	 * Description... Click more options
	 * @author A-10690
	 * @param UNID
	 * @throws InterruptedException,IoException
	 */	

	public void clickMoreOption(String unid) throws InterruptedException, IOException {

		waitForSync(3);
		String xpath  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "btn_moreoption;xpath");
		String  locator=xpath.replace("UNID", data(unid));
		WebElement ele1=driver.findElement(By.xpath(locator));
		moveScrollBar(ele1);
			waitForSync(1);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
    	executor.executeScript("arguments[0].click();", ele1);


	}
	
/**
 * Description... 	Select Value In Dropdown Without Fail
 */
	//select the option in a dropdown if it is not selected
		public void selectValueInDropdownWithoutFail(String sheetName, String locator, String option, String eleName,
				String selectBy) {
			By ele = getElement(sheetName, locator);
			WebElement ele1 = driver.findElement(ele);
			Select select = new Select(ele1);
			try {
				

				switch (selectBy) {
				case "Value": {
					String actopt = select.getFirstSelectedOption().getText();
					if (!actopt.equalsIgnoreCase(option))
						select.selectByValue(option);

				}
					break;
				case "VisibleText": {
					String actopt = select.getFirstSelectedOption().getText();
					if (!actopt.equalsIgnoreCase(option))
						select.selectByVisibleText(option);

				}
					break;
				case "Index": {
					int index = Integer.parseInt(option);
					String actopt = select.getFirstSelectedOption().getText();
					if (!actopt.equalsIgnoreCase(option))
						select.selectByIndex(index);

				}
					break;

				}
				writeExtent("Pass", "Entered " + option + " as " + eleName + " on " + sheetName.split("_")[0] + " Screen");
				System.out.println("Entered " + option + " as " + eleName + " on " + sheetName.split("_")[0] + " Screen");

			} catch (Exception e) {
				
				
				
					select.selectByIndex(1);

			}
		}
	
		/**
		 * Description... Click Edit Button
		 * @throws InterruptedException
		 * @throws IOException
		 */
		public void clickEditButton() throws InterruptedException, IOException {


			clickWebElement(sheetName, "btn_edittab;name", "Edit Button", screenName);
			waitForSync(2);
			clickWebElement(sheetName, "btn_clear;xpath", "clear Button", screenName);
			waitForSync(4);
			switchToFrame("default");
			if(driver.findElements(By.xpath("//div[@class='ui-dialog-buttonset']//button[1]")).size()==1) {
				clickWebElement("Generic_Elements", "btn_yes;xpath",
						"yes Button", screenName);
				waitForSync(3);
				driver.switchTo().frame("iCargoContentFrameOPR350");
			}
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
/**
 * Description... Click Type Of Packing
 * @throws Exception
 */
	public void clickTypeOfPacking() throws Exception {
		/*clickButtonSwitchWindow(sheetName, "btn_typeOfPacking;xpath",
				screenName, "Type Of Packing Button");*/

	}
/**
 * Description... Over Packing Details
 * @param packType
 * @param numberOfPackages
 * @param Length
 * @param Width
 * @param Height
 * @throws Exception
 */
	public void overPackingDetails(String packType, String numberOfPackages,
			String Length, String Width, String Height) throws Exception {

		if (packType.equals("Overpack"))

			clickWebElement(sheetName, "btn_overPacked;xpath",
					"Overpack Button", screenName);
		else if (packType.equals("AllPackedInOne"))
			clickWebElement(sheetName, "btn_allPackedInOne;xpath",
					"All Packed in one Check Box", screenName);

		switchToFrame("default");
		switchToFrame("contentFrame","OPR350");
		driver.switchTo().frame("popupContainerFrame");
		
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionLength;xpath",
				Length, "Dimension Length", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionWidth;xpath",
				Width, "Dimension Width", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionHeight;xpath",
				Height, "Dimension Height", screenName);
		enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
				numberOfPackages, "Number Of Packages", screenName);
		clickWebElement("Generic_Elements",
				"btn_childWinOk;xpath", "OK Button", screenName);
		waitForSync(3);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR350");
	}
/**
 * Description... Capture DG Details Radioactive
 * @param UNIDNo
 * @param properShippingName
 * @param netQuantityperPackage
 * @param noOfPackage
 * @param RMC
 * @param TI
 * @throws InterruptedException
 * @throws IOException 
 */
	public void captureDGDetailsRadioactive(String UNIDNo,
			String properShippingName, String netQuantityperPackage,
			String noOfPackage, String RMC, String TI)
			throws InterruptedException, IOException {
		enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
				"UNID No", screenName);
		
		try{
			driver.findElement(By.xpath("//input[@name='netQuantityPerPackage']")).sendKeys(netQuantityperPackage);
		}
		catch(Exception InvalidElementStateException)
		{
			
		}
		
		
		enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
				"Net Quantity Per Package", screenName);
		selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
				properShippingName, "Proper Shipping Name", "Value");
		waitForSync(1);
		selectValueInDropdown(sheetName, "lst_RMC;xpath", RMC, "RMC",
				"VisibleText");
		enterValueInTextbox(sheetName, "inbx_transportIndex;xpath", TI,
				"Transport Index", screenName);
		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
	}
	
	
	
	/**
     * A-8705 Selects UNID
     * 
     * @param unid
     */
    public void selectUNID(String unid) {
        String xpath1 = xls_Read.getCellValue("CaptureDGDetails_OPR350",
                "chk_DGRTable;xpath").replace("UNID", unid);
        WebElement ele1 = findDynamicXpathElement(xpath1,
                "UNID Table Check Box", "Capture DG Details");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", ele1);
        ele1.click();
        waitForSync(4);

 

    }
    
    
    
    /**
     * A-8705 Captures radio active unids with cao
     * 
     * @param UNIDNo
     * @param properShippingName
     * @param netQuantityperPackage
     * @param noOfPackage
     * @param RMC
     * @param TI
     * @throws InterruptedException
     * @throws IOException 
     */
    public void captureDGDetailsRadioactiveWithCao(String UNIDNo,
            String properShippingName, String netQuantityperPackage,
            String noOfPackage, String RMC, String TI)
            throws InterruptedException, IOException {
        enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
                "UNID No", screenName);

 

        try {
            driver.findElement(
                    By.xpath("//input[@name='netQuantityPerPackage']"))
                    .sendKeys(netQuantityperPackage);
        } catch (Exception InvalidElementStateException) {

 

        }
        enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
                "Net Quantity Per Package", screenName);
        selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
                properShippingName, "Proper Shipping Name", "Value");
        waitForSync(1);
        selectValueInDropdown(sheetName, "lst_RMC;xpath", RMC, "RMC",
                "VisibleText");
        enterValueInTextbox(sheetName, "inbx_transportIndex;xpath", TI,
                "Transport Index", screenName);
        waitForSync(3);
        clickWebElement(sheetName, "chk_cao;id", "CAO Checkbox", screenName);
        waitForSync(3);
        clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
        waitForSync(5);
    }
 
    /**
     * A-8705 Capture non radio active unid details with cao
     * 
     * @param UNIDNo
     * @param properShippingName
     * @param netQuantityperPackage
     * @param noOfPackage
     * @param PerPackageUnit
     * @throws InterruptedException
     * @throws IOException 
     */
    public void captureDGRDetailsWithCao(String UNIDNo,
            String properShippingName, String netQuantityperPackage,
            String noOfPackage, String PerPackageUnit)
            throws InterruptedException, IOException {

 

        enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
                "UNID No", screenName);
        enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
                netQuantityperPackage, "Net Quantity Per Package", screenName);
        enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
                "No Of Package", screenName);
        waitForSync(3);
        selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
                properShippingName, "Proper Shipping Name", "Value");

 

        selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
                PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
        waitForSync(3);
        clickWebElement(sheetName, "chk_cao;id", "CAO Checkbox", screenName);
        waitForSync(3);
        clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
        waitForSync(5);

 

    }

/**
 * Description... OverPacking Details Radioactive
 * @param packType
 * @param numberOfPackages
 * @param Length
 * @param Width
 * @param Height
 * @param RMC
 * @param TransportIndex
 * @throws Exception
 */
	public void overPackingDetailsRadioactive(String packType,
			String numberOfPackages, String Length, String Width,
			String Height, String RMC, String TransportIndex) throws Exception {

		if (packType.equals("Overpack"))
		
			clickWebElement(sheetName, "btn_overPacked;xpath",
					"Overpack used Button", screenName);
		else if (packType.equals("AllPackedInOne"))
			clickWebElement(sheetName, "btn_allPackedInOne;xpath",
					"All Packed in one Button", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR350");
		driver.switchTo().frame("popupContainerFrame");
		
		enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
				numberOfPackages, "Number Of Packages", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionLength;xpath",
				Length, "Dimension Length", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionWidth;xpath",
				Width, "Dimension Width", screenName);
		enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionHeight;xpath",
				Height, "Dimension Height", screenName);
		selectValueInDropdown(sheetName, "lst_RMC_packagingDetails;xpath", RMC,
				"RMC", "VisibleText");
		enterValueInTextbox(sheetName,
				"inbx_transportIndex_packagingDetails;xpath", TransportIndex,
				"Transport Index", screenName);
		clickWebElement("Generic_Elements",
				"btn_childWinOk;xpath", "OK Button", screenName);
		waitForSync(5);
		switchToFrame("default");
		switchToFrame("contentFrame","OPR350");

	}
	
	
	
	/**A-8705
	 * Selects Identical UNID
	 * @param unid
	 */
	    public void selectIdenticalUNID(String unid) {
	    	JavascriptExecutor js = (JavascriptExecutor) driver;
			String xpath1 = xls_Read.getCellValue("CaptureDGDetails_OPR350",
					"chk_DGRTable_same;xpath").replace("UNID", data(unid));
			List<WebElement> ele = driver.findElements(By.xpath(xpath1));
			for(WebElement s:ele){
				js.executeScript("arguments[0].scrollIntoView(true);", s);
				s.click();
				waitForSync(4);   
			}
		}

	   

/**
 * Description... Change DG Details
 * @param ShipmentClass
 * @param erg
 * @throws InterruptedException
 * @throws IOException 
 */
	public void changeDGDetails(String ShipmentClass, String erg)
			throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_shipmentClass;xpath",
				ShipmentClass, "Shipment Class", screenName);
		enterValueInTextbox(sheetName, "inbx_erg;xpath", erg, "erg", screenName);
		clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);

	}
/**
 * Description... Enter DG Details
 * @param UNIDNo
 * @param properShippingName
 * @param netQuantityperPackage
 * @param noOfPackage
 * @param PerPackageUnit
 * @throws InterruptedException
 */
	public void enterDGDetails(String UNIDNo, String properShippingName,
                  String netQuantityperPackage, String noOfPackage,
                  String PerPackageUnit) throws InterruptedException {

            enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
                        "UNID No", screenName);
            enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
                        "No Of Package", screenName);
            selectValueInDropdown(sheetName, "lst_properShipName;xpath",
                        properShippingName, "Proper Shipping Name", "Value");
            selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
                        PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
            enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
                        netQuantityperPackage, "Net Quantity Per Package", screenName);
            waitForSync(1);
      }
/**
 * Description... verify CAO checkbox selection
 * @author A-10690
 * @param Boolean condition true/false
 * @throws Exception
 */	
public void verifyCAOIndicator(boolean condition) throws Exception {
	
	switchToFrame("default");
	driver.switchTo().frame("iCargoContentFrameOPR350");
	driver.switchTo().frame("popupContainerFrame");
	waitForSync(2);
	String caocheckbox  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "chk_caoChecked;id");
	if(condition)
	{
	if(driver.findElement(By.id(caocheckbox)).isSelected())
		writeExtent("Pass","CAO indicator checkbox is selecetd "+screenName);
	
	else
		writeExtent("Fail","CAO indicator checkbox is not selecetd "+screenName);
	}else
	{
	if(driver.findElement(By.id(caocheckbox)).isSelected())
		writeExtent("Fail","CAO indicator checkbox is selecetd "+screenName);
	
	else
		writeExtent("pass","CAO indicator checkbox is not selecetd "+screenName);
	}
	clickCancelButtonPopUp();
}


/**
 * Description... Verify All Pack In One Symbol
 * @param UNIDS
 * @param AllPackName
 * @throws InterruptedException
 */
public void verifyAllPackInOneSymbol(String [] UNIDS, String AllPackName) throws InterruptedException{
 
 String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetailtable;xpath");
 
 for(int i =0 ; i<UNIDS.length ; i++){
 
 String dynxpath = xpath +"[contains(.,'"+ UNIDS[i] +"')]//em[contains(.,'" + AllPackName + "')]";
 
 if(driver.findElement(By.xpath(dynxpath)).isDisplayed()){
  System.out.println("All Pack In One Symbol is displayed for " + UNIDS[i]);
  writeExtent("Pass", "All Pack In One Symbol is displayed for " + UNIDS[i]);
 }else{
  
  System.out.println("All Pack In One Symbol is not displayed for " + UNIDS[i]);
  writeExtent("Fail", "All Pack In One Symbol is not displayed for " + UNIDS[i]);
  Assert.assertFalse(true, "All Pack In One Symbol is not displayed for " + UNIDS[i] + " on " + screenName + " Page");
  
 }
 
 }
 
}
/**
 * Description... Verify Over Pack Symbol
 * @param UNIDS
 * @param OverPack
 * @throws InterruptedException
 */
public void verifyOverPackSymbol(String [] UNIDS, String OverPack) throws InterruptedException{
 
 String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetailtable;xpath");
 
 for(int i =0 ; i<UNIDS.length ; i++){
 
 String dynxpath = xpath +"[contains(.,'"+ UNIDS[i] +"')]//em[contains(.,'" + OverPack + "')]";
 
 if(driver.findElement(By.xpath(dynxpath)).isDisplayed()){
  System.out.println("Over Pack Symbol is displayed for " + UNIDS[i]);
  writeExtent("Pass", "Over Pack Symbol is displayed for " + UNIDS[i]);
 }else{
  
  System.out.println("Over Pack Symbol is not displayed for " + UNIDS[i]);
  writeExtent("Fail", "Over Pack Symbol is not displayed for " + UNIDS[i]);
  Assert.assertFalse(true, "Over Pack Symbol is not displayed for " + UNIDS[i] + " on " + screenName + " Page");
  
 }
 
 }
 
}

/**
 * Description... Verify Declaration Details
 * @param OriginFullName
 * @param DestinationFullName
 * @throws InterruptedException
 */
public void verifyDeclarationDetails(String OriginFullName, String DestinationFullName) throws InterruptedException{
 
 try{
 String xpath = xls_Read.getCellValue(sheetName, "div_declarationDetails;xpath");
 String dynxpath1 = xpath +"//span[@class='valign-middle'][1]";
 
 String actOrigin = driver.findElement(By.xpath(dynxpath1)).getText();
 if(actOrigin.contains(OriginFullName)){
  System.out.println("Verified origin and value is " + OriginFullName);
  writeExtent("Pass", "Verified origin and value is " + OriginFullName);
 }else{
  
  System.out.println("Failed to verify origin " + " on " + screenName + " Page");
  writeExtent("Fail", "Failed to verify origin " + " on " + screenName + " Page");
  Assert.assertFalse(true, "Failed to verify origin" + " on " + screenName + " Page");
  
 }
 
 
 String dynxpath2 = xpath +"//span[@class='valign-middle'][2]";
 
 String actDestination = driver.findElement(By.xpath(dynxpath2)).getText();
 if(actDestination.contains(DestinationFullName)){
  System.out.println("Verified Destination and value is " + DestinationFullName);
  writeExtent("Pass", "Verified Destination and value is " + DestinationFullName);
 }else{
  
  System.out.println("Failed to verify Destination " + " on " + screenName + " Page");
  writeExtent("Fail", "Failed to verify Destination " + " on " + screenName + " Page");
  Assert.assertFalse(true, "Failed to verify Destination" + " on " + screenName + " Page");
  
 }
 
 }catch(Exception e){
  
  System.out.println("Failed to verify Declaration Details " + " on " + screenName + " Page");
  writeExtent("Fail", "Failed to verify Declaration Details " + " on " + screenName + " Page");
  Assert.assertFalse(true, "Failed to verify Declaration Details " + " on " + screenName + " Page");
 }
 
}
/**
 * Description... Capture DGR Details with PI
 * @param UNIDNo
 * @param properShippingName
 * @param netQuantityperPackage
 * @param noOfPackage
 * @param PerPackageUnit
 * @param PACKINGINSTRUCTIONTYPECODE
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void captureDGRDetailswithPI(String UNIDNo, String properShippingName,
  String netQuantityperPackage, String noOfPackage,
  String PerPackageUnit, String PACKINGINSTRUCTIONTYPECODE) throws InterruptedException, AWTException, IOException {

 enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
   "UNID No", screenName);
 waitForSync(5);
 enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
   netQuantityperPackage, "Net Quantity Per Package", screenName);
 enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
   "No Of Package", screenName);
 waitForSync(4);
 selectValueInDropdown(sheetName, "lst_properShipName;xpath",
   properShippingName, "Proper Shipping Name", "Value");

 selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
   PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
 enterPackingInstructions(PACKINGINSTRUCTIONTYPECODE);
 clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
 waitForSync(1);
}
/**
 * Description... Click Collapse Shp Ref
 * @throws InterruptedException
 * @throws IOException 
 */
public void clickCollapseShpRef() throws InterruptedException, IOException{
 
 clickWebElement(sheetName, "btn_collapseShpRef;name", "Collapse Shipper Reference ", screenName);
}
/**
 * Description... Capture Non Radioactive DG
 * @param UNIDNo
 * @param properShippingName
 * @param netQuantityperPackage
 * @param noOfPackage
 * @param PerPackageUnit
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
public void captureNonRadioactiveDG(String UNIDNo, String properShippingName,
  String netQuantityperPackage, String noOfPackage,
  String PerPackageUnit) throws InterruptedException, AWTException, IOException {

 enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
   "UNID No", screenName);

 waitForSync(4);
 selectValueInDropdown(sheetName, "lst_properShipName;xpath",
   properShippingName, "Proper Shipping Name", "Value");
 enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
   netQuantityperPackage, "Net Quantity Per Package", screenName);
 enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
   "No Of Package", screenName);
 

 selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
   PerPackageUnit, "Net Quantity Per Package Unit", "VisibleText");
 clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
 waitForSync(1);
}
/**
 * Description... Modify UNID details
 * @param UNID
 * @param noOfUNIDS
 * @param newUNID
 * @param properShippingName
 * @param netQuantityperPackage
 * @param noOfPackage
 * @param PerPackageUnit
 */
public void modifyUNIDdetails(String UNID, int noOfUNIDS , String newUNID , 
		   String properShippingName, String netQuantityperPackage, String noOfPackage, String PerPackageUnit)
		 {
		  try{
		   String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetailtable;xpath");
		   String dynxpath = xpath +"[contains(.,'"+ UNID +"')]//a[@id='moreOption']";
		   driver.findElement(By.xpath(dynxpath)).click();
		   waitForSync(4);
		   
		   for (int i = 0 ; i < noOfUNIDS ;i++){
		    
		    String xpath2 = "(//button[@name='btEdit'])["+(i+1)+"]";
		    
		    if(driver.findElement(By.xpath(xpath2)).isDisplayed()){
		     
		     driver.findElement(By.xpath(xpath2)).click();
		    }
		   }
		   
		   switchToFrame("default");
		   driver.switchTo().frame("iCargoContentFrameOPR350");
		            driver.switchTo().frame("popupContainerFrame");
		   waitForSync(10);
		   
		   enterDGDetails(newUNID, properShippingName, netQuantityperPackage, noOfPackage, PerPackageUnit);
		   
		   clickWebElement(sheetName, "btn_updateUNID;id", "Update Button", screenName);
		   
		   switchToFrame("parentFrame");
		   
		   
		  }catch(Exception e){
		   
		   
		  }
		  
		 }
/**
 * Description... Capture Radioactive DG		 
 * @param UNIDNo
 * @param properShippingName
 * @param noOfPackage
 * @param RMC
 * @param TI
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
		 public void captureRadioactiveDG(String UNIDNo,
		   String properShippingName,String noOfPackage, String RMC, String TI)
		   throws InterruptedException, AWTException, IOException {
		  enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", UNIDNo,
		    "UNID No", screenName);
		  waitForSync(5);
		  
		  enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", noOfPackage,
		    "Net Quantity Per Package", screenName);
		  waitForSync(4);
		  selectValueInDropdown(sheetName, "lst_properShipName;xpath",
		    properShippingName, "Proper Shipping Name", "Value");
		  waitForSync(1);
		  selectValueInDropdown(sheetName, "lst_RMC;xpath", RMC, "RMC",
		    "VisibleText");
		  enterValueInTextbox(sheetName, "inbx_transportIndex;xpath", TI,
		    "Transport Index", screenName);
		  clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
		 }
/**
 * Description... Enter Packing Instructions		 
 * @param PackingInstruction
 * @throws InterruptedException
 */
		 public void enterPackingInstructions( String PackingInstruction) throws InterruptedException{
		  enterValueInTextbox(sheetName, "inbx_packingInstruction;xpath", PackingInstruction ," Packing Instruction ", screenName);
		  
		 }
/**
 * Description... Verify RadioActive Symbol	 
 * @param UNID
 * @throws InterruptedException
 */
		 public void verifyRadioActiveSymbol(String UNID) throws InterruptedException{
		  
		  String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetailtable;xpath");
		  String dynxpath = xpath +"[contains(.,'"+ UNID +"')]//td[14]//i[@class='icon radioactive']";
		  
		  if(driver.findElement(By.xpath(dynxpath)).isDisplayed()){
		   System.out.println("Radio active symbol is displayed");
		   writeExtent("Pass", "Radio active symbol is displayed");
		  }else{
		   
		   System.out.println("Radio active symbol is not displayed");
		   writeExtent("Fail", "Radio active symbol is not displayed");
		  }
		  
		 }
/**
 * Description... Verify UNID details Displayed
 * @param UNIDS
 * @throws InterruptedException
 */
		public void verifyUNIDdetailsDisplayed(String [] UNIDS) throws InterruptedException{
		  
		  String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetailtable;xpath");
		  
		  for(int i =0 ; i<UNIDS.length ; i++){
		  
		  String dynxpath = xpath +"[contains(.,'"+ UNIDS[i] +"')]";
		  
		  Actions a1 = new Actions(driver);
		  a1.moveToElement(driver.findElement(By.xpath(dynxpath)));
		  
		  if(driver.findElement(By.xpath(dynxpath)).isDisplayed()){
		   System.out.println("UNID details displayed for " + UNIDS[i]);
		   writeExtent("Pass", "UNID details is displayed for " + UNIDS[i]);
		  }else{
		   
		   System.out.println("UNID details is not displayed for " + UNIDS[i]);
		   writeExtent("Fail", "UNID details is not displayed for " + UNIDS[i]);
		   Assert.assertFalse(true, "UNID details is not displayed for " + UNIDS[i] + " on " + screenName + " Page");
		   
		  }
		  
		  }
		  
		 }
/**
 * Description... OverPack UNID Radioactive
 * @param numberOfPackages
 * @param Length
 * @param Width
 * @param Height
 * @param RMC
 * @param TransportIndex
 * @throws Exception
 */
		public void overPackUNIDRadioactive(String numberOfPackages, String Length, String Width,
		  String Height, String RMC, String TransportIndex) throws Exception {

		 //clickButtonSwitchWindow(sheetName, "btn_overPacked;xpath", screenName,"Over Packed button");
		 
		 
		 clickWebElement(sheetName, "btn_overPacked;xpath", "Over Packed button", screenName);
		 switchToFrame("default");
		 driver.switchTo().frame("iCargoContentFrameOPR350");
		    driver.switchTo().frame("popupContainerFrame");
		 waitForSync(5);
		 
		 enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
		   numberOfPackages, "Number Of Packages", screenName);
		 enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionLength;xpath",
		   Length, "Dimension Length", screenName);
		 enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionWidth;xpath",
		   Width, "Dimension Width", screenName);
		 enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionHeight;xpath",
		   Height, "Dimension Height", screenName);
		 selectValueInDropdown(sheetName, "lst_RMC_packagingDetails;xpath", RMC,
		   "RMC", "VisibleText");
		 enterValueInTextbox(sheetName,
		   "inbx_transportIndex_packagingDetails;xpath", TransportIndex,
		   "Transport Index", screenName);
		 //clickButtonSwitchtoParentWindow("Generic_Elements",
		   //"btn_childWinOk;xpath", "OK Button", screenName);
		 clickWebElement("Generic_Elements", "btn_childWinOk;xpath", "OK Button", screenName);
		 switchToFrame("parentFrame");

		}
/**
 * Description... Cancel Packing
 * @throws InterruptedException
 * @throws IOException 
 */
		public void cancelPacking() throws InterruptedException, IOException{
		 
		 clickWebElement(sheetName, "btn_cancelPacking;name", "Cancel Packing Button", screenName);
		}
/**
 * Description... OverPack UNID
 * @param numberOfPackages
 * @throws Exception
 */
		public void overPackUNID(String numberOfPackages) throws Exception {

		 //clickButtonSwitchWindow(sheetName, "btn_overPacked;xpath", screenName,"Over Packed button");
		 clickWebElement(sheetName, "btn_overPacked;xpath", "Over Packed button", screenName);
		 switchToFrame("default");
		 driver.switchTo().frame("iCargoContentFrameOPR350");
		    driver.switchTo().frame("popupContainerFrame");
		 waitForSync(5);
		 
		 enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
		   numberOfPackages, "Number Of Packages", screenName);
		 
		// clickButtonSwitchtoParentWindow("Generic_Elements",
		//   "btn_childWinOk;xpath", "OK Button", screenName);
		 clickWebElement("Generic_Elements", "btn_childWinOk;xpath", "OK Button", screenName);
		 switchToFrame("parentFrame");

		}
/**
 * Description... Capture Nuclide Details
 * @param UNID
 * @param noOfUNIDS
 * @param nuclideNames
 * @param nuclideActivity
 * @param nuclideUnit
 */
		public void captureNuclideDetails(String UNID, int noOfUNIDS , String [] nuclideNames , 
		  String [] nuclideActivity, String [] nuclideUnit)
		{
		 try{
		  String xpath = xls_Read.getCellValue(sheetName, "tbl_awbDetailtable;xpath");
		  String dynxpath = xpath +"[contains(.,'"+ UNID +"')]//a[@id='moreOption']";
		  driver.findElement(By.xpath(dynxpath)).click();
		  waitForSync(4);
		  
		  for (int i = 0 ; i < noOfUNIDS ;i++){
		   
		   String xpath2 = "(//button[@name='btEdit'])["+(i+1)+"]";
		   
		   if(driver.findElement(By.xpath(xpath2)).isDisplayed()){
		    
		    driver.findElement(By.xpath(xpath2)).click();
		   }
		  }
		  
		  switchToFrame("default");
		  driver.switchTo().frame("iCargoContentFrameOPR350");
		        driver.switchTo().frame("popupContainerFrame");
		  waitForSync(10);
		  clickWebElement(sheetName, "lnk_addNuclideBtn;name", "Add nuclide Button", screenName); 
		  waitForSync(5);
		  
		  String xpath2 = xls_Read.getCellValue(sheetName, "tbl_nuclideTable;xpath");
		  for (int i =1 ; i <= nuclideNames.length ; i++){
		   
		  clickWebElement(sheetName, "btn_addNewNuclide;id", "Add new Button", screenName);  
		  
		  String dynxpath2 = xpath2 + "["+ i + "]//input[@name='nuclideName']";
		  enterValueInTextbox(dynxpath2, nuclideNames[i-1], "Nuclide name "+i, screenName);
		  
		  String dynxpath3 = xpath2 + "["+ i + "]//input[@name='nuclideActivity']";
		  enterValueInTextbox(dynxpath3, nuclideActivity[i-1], "Nuclide Activity "+i, screenName);
		  
		  String dynxpath4 =xpath2 + "["+ i + "]//select[@name='nuclideUnit']";
		  selectValueInDropdownWthXpath(dynxpath4 ,nuclideUnit[i-1], "Nuclide Unit "+i, "VisibleText");
		  
		  }
		  
		  clickWebElement(sheetName, "btn_nuclideCaptureOK;id", "OK Button", screenName);
		  
		  clickWebElement(sheetName, "btn_updateUNID;id", "Update Button", screenName);
		  switchToFrame("parentFrame");
		  
		  
		 }catch(Exception e){
		  
		  
		 }
		 
		}
/**
 * Description... All Pack In One UNID Radioactive
 * @param numberOfPackages
 * @param Length
 * @param Width
 * @param Height
 * @param RMC
 * @param TransportIndex
 * @throws Exception
 */
		public void allPackInOneUNIDRadioactive(String numberOfPackages, String Length, String Width,
		  String Height, String RMC, String TransportIndex) throws Exception {

		 //clickButtonSwitchWindow(sheetName, "btn_allPackedInOne;xpath", screenName,"Over Packed button");
		 clickWebElement(sheetName, "btn_allPackedInOne;xpath", "All Packed in One button", screenName);
		 switchToFrame("default");
		 driver.switchTo().frame("iCargoContentFrameOPR350");
		    driver.switchTo().frame("popupContainerFrame");
		 waitForSync(5);
		 
		 enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
		   numberOfPackages, "Number Of Packages", screenName);
		 enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionLength;xpath",
		   Length, "Dimension Length", screenName);
		 enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionWidth;xpath",
		   Width, "Dimension Width", screenName);
		 enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionHeight;xpath",
		   Height, "Dimension Height", screenName);
		 selectValueInDropdown(sheetName, "lst_RMC_packagingDetails;xpath", RMC,
		   "RMC", "VisibleText");
		 enterValueInTextbox(sheetName,
		   "inbx_transportIndex_packagingDetails;xpath", TransportIndex,
		   "Transport Index", screenName);
		 //clickButtonSwitchtoParentWindow("Generic_Elements",
		   //"btn_childWinOk;xpath", "OK Button", screenName);
		 clickWebElement("Generic_Elements", "btn_childWinOk;xpath", "OK Button", screenName);
		 switchToFrame("parentFrame");

		}

/**
		 * Description... clicks Copy Edgd Details Button, enter AWB Details and clicks on OK Button
		 * @throws InterruptedException
 * @throws IOException 
		 */
		public void  copyeDGDDetails() throws InterruptedException, IOException{
			clickWebElement(sheetName, "btn_copyEdgdDetails;name", "Copy Edgd Details Button", screenName);
	switchToFrame("default");
	switchToFrame("contentFrame","OPR350");
	driver.switchTo().frame("popupContainerFrame");
	enterValueInTextbox(sheetName, "inbx_awbPrefix;name", data("ShipmentPrefix"), "AWB Prefix", screenName);
	enterValueInTextbox(sheetName, "inbx_awbDocumentNumber;name", data("AWBNo"), "AWB Number", screenName);
	clickWebElement(sheetName, "btn_Ok;name", "OK Button", screenName);
	switchToFrame("default");
	switchToFrame("contentFrame","OPR350");

}
/**
 * Description... Click SPL UNIDs	
 * @throws InterruptedException
 * @throws IOException 
 */
		public void clickSPLUNIDs() throws InterruptedException, IOException {
			clickWebElement(sheetName, "btn_SPLUNIDSs;xpath", "SPL UNIDs Button", screenName);
			
		}
/**
 * Description... Edit UNID Details		
 * @throws InterruptedException
 * @throws IOException 
 */
		public void editUNIDDetails()throws InterruptedException, IOException{
			clickWebElement(sheetName, "btn_editDetails;xpath", "SPL UNIDs Button", screenName);
			clickWebElement(sheetName, "btn_Edit;name", "Edit Button", screenName);
			
		}
/**
 * Description... Change UNID Weight		
 * @throws InterruptedException
 */
		public void changeUNIDWeight()throws InterruptedException{
			try{
				driver.switchTo().frame("popupContainerFrame");
			enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",data("WeightPortal"), "Net Quantity Per Package", screenName);
			clickWebElement(sheetName, "btn_Update;name", "Update Button", screenName);
			switchToDefaultAndContentFrame("OPR350");
			}
			catch(Exception e )
			{
				System.out.println("Failed in Changing UNID Weight");
				writeExtent("Fail", "Could not Change UNID Weight " +" On " + screenName + " Page");
				Assert.assertFalse(true, "Could not Change UNID Weight " +" On " + screenName + " Page");
			}
			
		}
/**
 * Description... Verify Weight Changed
 * @throws InterruptedException
 * @throws IOException 
 */
		public void verifyWeightChanged() throws InterruptedException, IOException {
		String newWeight=getElementText(sheetName, "txt_weight;xpath", "DG Table Text", screenName);
		verifyValueOnPageContains(newWeight, data("WeightPortal"), "Verify Weight changed in DG Table", newWeight, "Weight changed in DG Table");
			
		}
/**
 * Description... Click Add SPL UNIDs		
 * @throws InterruptedException
 * @throws IOException 
 */
		public void clickAddSPLUNIDs() throws InterruptedException, IOException{
			clickWebElement(sheetName, "btn_AddSplUNID;name", "Add Spl UNID Button", screenName);
			waitForSync(5);
			
		}
/**
 * Description... Edit Shipper Details
 * @param transit
 * @throws Exception 
 */
		public void editShipperDetails(String transit) throws Exception {
			clickWebElement(sheetName, "btn_toeditshipperdetails;name", "Shipper Details Edit Button", screenName);
			enterValueInTextbox(sheetName, "inbx_departure;name", data(transit), "Departure", screenName);
			clickWebElement(sheetName, "btn_ShipperReferenceOk;name", "Shipper Reference Ok Button", screenName);			
			save(screenName);
		}
		
		/**
		 * Desc : Capture DGR Details with PI value
		 * @author A-9175
		 * @param UNIDNo
		 * @param properShippingName
		 * @param netQuantityperPackage
		 * @param noOfPackage
		 * @param PerPackageUnit
		 * @param PI
		 * @throws InterruptedException
		 * @throws IOException 
		 */

		public void captureDGRDetails(String UNIDNo, String properShippingName,
				String netQuantityperPackage, String noOfPackage,
				String PerPackageUnit,String PI) throws InterruptedException, IOException {

			enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", data(UNIDNo),
					"UNID No", screenName);
			enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
					data(netQuantityperPackage), "Net Quantity Per Package", screenName);
			enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", data(noOfPackage),
					"No Of Package", screenName);
			waitForSync(3);
			selectValueInDropdownWithoutFail(sheetName, "lst_properShipName;xpath",
					data(properShippingName), "Proper Shipping Name", "Value");

			selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",
					data(PerPackageUnit), "Net Quantity Per Package Unit", "VisibleText");
			waitForSync(5);
			 enterValueInTextbox(sheetName, "inbx_packingInstruction;id",
		             data(PI), "Package Instruction", screenName);
			 waitForSync(5);
			clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
			waitForSync(1);
		}

		


/**
 * Description... Verify Exclusive Usage Indicator		
 * @throws Exception
 */
		public void verifyExclusiveUsageIndicator() throws Exception {
		       switchToWindow("storeParent");
		       switchToWindow("child");
		       switchToFrame("default");
		       waitForSync(3);
		       String actMessageContentXML      = getElementText(
		                     "ListMessages_MSG005", "txt_OrigFSUMsg;xpath",
		                     "Message Text", "List Messages");
		       String ExclusiveUsageIndicator = "<ExclusiveUsageIndicator>"+data("ExclusiveUsageIndicator")+"</ExclusiveUsageIndicator>";
		       verifyScreenText(sheetName, ExclusiveUsageIndicator, actMessageContentXML,"Message Verification",screenName);
		       
		}
/**
 * Description... Verify Radionuclide
 * @param noOfRadionuclide
 * @throws InterruptedException
 */
		public void verufyRadionuclide(int noOfRadionuclide) throws InterruptedException{
            
            try{
                  for(int i=1; i<=noOfRadionuclide; i++ ){
                         
                         String xpath = xls_Read.getCellValue(sheetName, "lnk_expandUNID;xpath");
                         String dynxpath = xpath +"[" + i +"]" + "//td)[3]";
                         clickWebElement(dynxpath,"Expand UNID", screenName);
                         System.out.println(dynxpath);
                         String xpath2 = xls_Read.getCellValue(sheetName, "lnk_viewAll;xpath");
                         String dynxpath2 = xpath2 +"[" + i +"]";
                         System.out.println(dynxpath2);
                         clickWebElement(dynxpath2,"View All", screenName); 
                         String activity=data("Radionuclide1");
                         String xpath3 = xls_Read.getCellValue(sheetName, "activity;xpath");
                         String dynxpath3 = xpath3+"//tr[1][contains(.,'"+ activity +"')]//td[4]";
                         
                          Actions a1 = new Actions(driver);
                         a1.moveToElement(driver.findElement(By.xpath(dynxpath3)));
                           
                          if(driver.findElement(By.xpath(dynxpath3)).isDisplayed()){
                            System.out.println("Activity details displayed as " + activity);
                            writeExtent("Pass", "Activity details displayed as " + activity);
                         }else{
                            
                            System.out.println("Activity details not displayed as " + activity);
                            writeExtent("Fail", "Activity details not displayed as " + activity);
                            Assert.assertFalse(true, "Activity details not displayed as " + activity + " on " + screenName + " Page");
                         }
                         
                          String activity2=data("Radionuclide2");
                         String xpath4 = xls_Read.getCellValue(sheetName, "activity;xpath");
                         String dynxpath4 = xpath3+"//tr[2][contains(.,'"+ activity2 +"')]//td[4]";
                                
                          Actions a2 = new Actions(driver);
                         a1.moveToElement(driver.findElement(By.xpath(dynxpath4)));
                                  
                          if(driver.findElement(By.xpath(dynxpath4)).isDisplayed()){
                                System.out.println("Activity details displayed as " + activity2);
                                writeExtent("Pass", "Activity details displayed as " + activity2);
                         }else{
                                   
                                System.out.println("Activity details not displayed as " + activity2);
                                writeExtent("Fail", "Activity details not displayed as " + activity2);
                                Assert.assertFalse(true, "Activity details not displayed as " + activity2 + " on " + screenName + " Page");
                                 }
                                String Unit1=data("Unit1");
                                String xpath5 = xls_Read.getCellValue(sheetName, "activity;xpath");
                                String dynxpath5 = xpath3+"//tr[1][contains(.,'"+ Unit1 +"')]//td[5]";
                                       
                                Actions a3 = new Actions(driver);
                                a1.moveToElement(driver.findElement(By.xpath(dynxpath5)));
                                         
                                if(driver.findElement(By.xpath(dynxpath5)).isDisplayed()){
                                       System.out.println("Activity details displayed as " + Unit1);
                                       writeExtent("Pass", "Activity details displayed as " + Unit1);
                                }else{
                                          
                                       System.out.println("Activity details not displayed as " + Unit1);
                                       writeExtent("Fail", "Activity details not displayed as " + Unit1);
                                       Assert.assertFalse(true, "Activity details not displayed as " + Unit1 + " on " + screenName + " Page");                                 
                                 }
                                String Unit2=data("Unit1");
                                String xpath6 = xls_Read.getCellValue(sheetName, "activity;xpath");
                                String dynxpath6 = xpath3+"//tr[2][contains(.,'"+ Unit2 +"')]//td[5]";
                                       
                                Actions a4 = new Actions(driver);
                                a1.moveToElement(driver.findElement(By.xpath(dynxpath6)));
                                         
                                if(driver.findElement(By.xpath(dynxpath6)).isDisplayed()){
                                       System.out.println("Activity details displayed as " + Unit2);
                                       writeExtent("Pass", "Activity details displayed as " + Unit2);
                                }else{
                                          
                                       System.out.println("Activity details not displayed as " + Unit2);
                                       writeExtent("Fail", "Activity details not displayed as " + Unit2);
                                       Assert.assertFalse(true, "Activity details not displayed as " + Unit2 + " on " + screenName + " Page");
                                        }
            
                         
                  }
            }catch(Exception e)
      {
          e.printStackTrace();
      }
         
     } 
/**
 * Description... Click Next Button
 * @throws InterruptedException
 * @throws IOException 
 */
		public void clickNext() throws InterruptedException, IOException {
			clickWebElement(sheetName, "btn_Next;xpath", "Next Button", screenName);
			
		}
/**
 * Description... Click Shipper Reference
 * @param ShipperRef
 * @throws InterruptedException
 */
		public void clickShipperRef(String ShipperRef) throws InterruptedException {
			String dynXpath = xls_Read.getCellValue(sheetName, "btn_ShipperrefNo;xpath").replace("dynVar", ShipperRef);
			clickWebElement(dynXpath, ShipperRef, screenName);
			
		}
/**
 * Description... Click Edit Shipper Reference	
 * @throws InterruptedException
 * @throws IOException 
 */
		public void clickEditShpRef() throws InterruptedException, IOException {
			clickWebElement(sheetName, "lnk_editShipperRef;name", "Edit Shipper Reference Link", screenName);
			
		}
/**
 * Description... Modify Origin Destination		
 * @param Origin
 * @param Destination
 * @throws InterruptedException
 */
		public void modifyOriginDestination(String Origin, String Destination) throws InterruptedException {
			enterValueInTextbox(sheetName, "inbx_Origin;id",Origin, "Origin", screenName);
			enterValueInTextbox(sheetName, "inbx_Destination;id",Destination, "Destination", screenName);
			
		}
/**
 * Description... Verify Shipper Reference Not Listed
 * @param ShipperRef
 * @throws InterruptedException
 */
		public void verifyShipperRefNotListed(String ShipperRef) throws InterruptedException {
            
            try{
                  String dynXpath = xls_Read.getCellValue(sheetName, "btn_ShipperrefNo;xpath").replace("dynVar", ShipperRef);
                  clickWebElement(dynXpath, ShipperRef, screenName);
                  
                  System.out.println("Shipper Reference  " + ShipperRef + " is listed on " + screenName + " Page");
                  writeExtent("Fail", "Shipper Reference  " + ShipperRef + " is listed on " + screenName + " Page");
                  Assert.assertFalse(true, "Shipper Reference  " + ShipperRef + " is listed on " + screenName + " Page");
                  
            }catch(Exception e){
                  
                  System.out.println("Shipper Reference  " + ShipperRef + " is not listed on " + screenName + " Page");
                  writeExtent("Pass", "Shipper Reference  " + ShipperRef + " is not listed on " + screenName + " Page");
                  
            }
            
     }

		/**
		 * Description... List AWb
		 * @author A-10690
		 * @param awbNo
		 * @param Shiomet prefix
		 * @throws InterruptedException,IoException
		 */

		public void listAWB(String awbNo,String ShipmentPrefix ) throws InterruptedException, IOException {

			// listAWB(awbNo,ShipmentPrefix,screenName);
			waitForSync(2);
			enterValueInTextbox("Generic_Elements", "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",
					screenName);

			enterValueInTextbox("Generic_Elements", "inbx_AWBnumber;xpath", data(awbNo), "AWB No", screenName);
			clickWebElement("Generic_Elements", "btn_List;xpath", "List Button", screenName);
			waitForSync(4);

		}


		/**
		 * Description... List AWb
		 * @author A-10690
		 * @param UNIDNo
		 * @param properShippingname
		 * @throws InterruptedException,IoException
		 */

		public void captureUnidandProperShippingname(String UNIDNo, String properShippingName) throws InterruptedException, IOException, AWTException {

			enterValueInTextbox(sheetName, "inbx_UNIDNumber;xpath", data(UNIDNo),
					"UNID No", screenName);
			waitForSync(4);
			keyPress("TAB");
			waitForSync(2);
			selectValueInDropdown(sheetName, "lst_properShipName;xpath",properShippingName,"Proper Shipping Name","VisibleText");


		}

		/**
		 * Description... Select SCCS
		 * @author A-10690
		 * @param scc1
		 * @param scc2
		 * @param scc3
		 * @throws InterruptedException,IoException
		 */

		public void selectSccs(String SCC1,String SCC2,String SCC3) throws InterruptedException {

			enterValueInTextbox(sheetName, "inbx_scc;xpath", SCC1,
					"UNID No", screenName);
			waitForSync(3);
			enterValueInTextbox(sheetName, "inbx_scc1;name", SCC2,
					"UNID No", screenName);
			waitForSync(2);
			enterValueInTextbox(sheetName, "inbx_scc2;name", SCC3,
					"UNID No", screenName);

		}


		/**
		 * Description... Capture PI
		 * @author A-10690
		 * @param P1
		 * @throws InterruptedException
		 */

		public void capturePI(String PI) throws InterruptedException {

			enterValueInTextbox(sheetName, "inbx_packingInstruction;xpath", data(PI),
					"PI", screenName);

		}

		/**
		 * Description... CapturePackages
		 * @author A-10690
		 * @param noOfPackage
		 * @param PerPackageUnit
		 * @param netQuantityperPackage
		 * @throws InterruptedException,IoException
		 */
		
		public void capturePackages(String netQuantityperPackage, String noOfPackage,
				String PerPackageUnit) throws InterruptedException {

			enterValueInTextbox(sheetName, "inbx_noOfPackages;xpath", data(noOfPackage),
					"No Of Package", screenName);
			enterValueInTextbox(sheetName, "inbx_netQuantityPerPackage;xpath",
					netQuantityperPackage, "Net Quantity Per Package", screenName);
			waitForSync(2);
			selectValueInDropdown(sheetName, "lst_netQuantityPerPackageUnit;xpath",data(PerPackageUnit), "Net Quantity Per Package Unit", "VisibleText");

		}

		/**
		 * Description... Capture Reportable Qunatity
		 * @author A-10690
		 * @param noOfPackage
		 * @param PerPackageUnit
		 * @throws InterruptedException,IoException
		 */	

		public void selectReportableQuantity(String Reportablequantity) throws InterruptedException {


			selectValueInDropdown(sheetName, "lst_Reportablequantity;id",
					data(Reportablequantity), "Net Quantity Per Package Unit", "VisibleText");

		}

		/**
		 * Description... Click Add button
		 * @author A-10690

		 * @throws InterruptedException,IoException
		 */	

		public void clickAddButton() throws InterruptedException, IOException {


			clickWebElement(sheetName, "btn_add;xpath", "Add Button", screenName);
			waitForSync(2);
		}

		/**
		 * Description... Click Clear button
		 * @author A-10690
		 * @throws InterruptedException,IoException
		 */	

		public void clickClearButton() throws InterruptedException, IOException {


			clickWebElement(sheetName, "btn_clearbutton;id", "clear Button", screenName);

		}	

		/**
		 * Description... Select Required UNID no from the table
		 * @author A-10690
		 * @param UNID
		 * @throws InterruptedException,IoException
		 */	

		public void selectRequiredUnid(String unid) throws InterruptedException, IOException {



			String xpath= xls_Read.getCellValue(sheetName, "chk_DGRTable;xpath");
			String  locator=xpath.replace("UNID",data(unid));
			WebElement ele1 = driver.findElement(By.xpath(locator));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].scrollIntoView(true);", ele1);
			ele1.click();
			waitForSync(4);

		}	

		/**
		 * Description... Edit UNID details
		 * @author A-10690
		 * @param UNID
		 * @param Packagedetails
		 * @throws InterruptedException,IoException
		 */	



		public void modifyUNIDDetails(String unid,String packageDetails) throws InterruptedException, IOException {



			//edit unid details
			switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameOPR350");
			driver.switchTo().frame("popupContainerFrame");
			String id  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "inbx_packagedetails;id");
			WebElement packdetails= driver.findElement(By.id(id));
			packdetails.clear();
			enterValueInTextbox(sheetName, "inbx_packagedetails;id",
					data(packageDetails), "Packaging details", screenName);
			clickWebElement(sheetName, "btn_update;name", "update button", screenName);
			switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameOPR350");
			waitForSync(2); 

		}	

		/**
		 * Description... Verify the updated packaging details
		 * @author A-10690
		 * @param UNID
		 * @param Packaging details
		 * @throws InterruptedException,IoException
		 */	
		public void verifyPackageDetails(String unid,String Packagingdetails) throws InterruptedException, IOException {

 
			
			String xpath  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "txt_Packaging details;xpath");
			String  locator=xpath.replace("UNID", data(unid));
			String expText=data(Packagingdetails);
		String actText=driver.findElement(By.xpath(locator)).getText();
		verifyScreenText(screenName,expText,actText,"Verify the updated packaging details",
				"verify the updated packaging details");
			waitForSync(1);
		}
		/**
		 * Description... ModifyUNIDwith no packaging details
		 * @author A-10690
		 * @param UNID
		 * @throws InterruptedException,IoException
		 */	

		public void modifyUNIDWithNoPackingdetails(String unid) throws InterruptedException, IOException {


			switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameOPR350");
			driver.switchTo().frame("popupContainerFrame");
			waitForSync(1);
			String id  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "inbx_packagedetails;id");
			WebElement packdetails= driver.findElement(By.id(id));
			packdetails.clear();	
			clickWebElement(sheetName, "btn_update;name", "More option", screenName);
			try
			{
				String xpatherror=xls_Read.getCellValue("Generic_Elements", "txt_errorMessage;xpath");
				if(driver.findElement(By.xpath(xpatherror)).isDisplayed())
					writeExtent("Pass"," Please enter packaging details error exist on "+screenName+" .Functionality is verifying the error displayed on updating with empty packaging details");

				else
					writeExtent("Fail"," Please enter packaging details  does not exist on "+screenName+" .Functionality is verifying the error displayed on updating with empty packaging details");

			}
			catch(Exception e)
			{
				writeExtent("Fail"," Please enter packaging details error  does not exist on "+screenName+" .Functionality is verifying the error displayed on updating with empty packaging details");
			}
		}
		/**
		 * Description... Delete required UNID from the table
		 * @author A-10690
		 * @param UNID
		 * @throws InterruptedException,IoException
		 */	

		public void deleteUNID(String unid) throws InterruptedException, IOException {


			String xpath= xls_Read.getCellValue(sheetName, "txt_siNo;xpath");
			String  locator1=xpath.replace("UNID",data(unid));
			String sNo = driver.findElement(By.xpath(locator1)).getText();
			int siNo= Integer.parseInt(sNo);
			String id=String.valueOf(siNo-1);
			waitForSync(1);
			String xpath1= xls_Read.getCellValue(sheetName, "btn_deletebutton;xpath");
			String  locator2=xpath1.replace("*",id);
			driver.findElement(By.xpath(locator2)).click();
			switchToFrame("default");
			while(driver.findElements(By.xpath("//button[contains(.,'Yes')]")).size()>0)
			{
				clickWebElement("Generic_Elements", "btn_Yes;xpath", "Yes Button", screenName);
				waitForSync(3);
			}
			driver.switchTo().frame("iCargoContentFrameOPR350");
		}



		/**
		 * Description... Performing All packin/overpack action
		 * @author A-10690
		 * @param packType
		 * @param numberOfPackages
		 * @param Length
		 * @param Width
		 * @param Height
		 * @param netQuantity1
		 * @param netQuantity1
		 * @param Maximumquantityperpackage
		 * @throws InterruptedException,IoException
		 */  


		public void allPackInOneoverPack(String packType,String numberOfPackages, String Length, String Width,
				String Height,String netQuantity1,String netQuantity2,String Maximumquantityperpackage) throws Exception {


           Double qValue=verifyQValues(netQuantity1,netQuantity2,Maximumquantityperpackage);
			
			
			if (packType.equals("Overpack"))

				clickWebElement(sheetName, "btn_overPacked;xpath",
						"Overpack Button", screenName);
			else if (packType.equals("AllPackedInOne"))
				clickWebElement(sheetName, "btn_allPackedInOne;xpath",
						"All Packed in one Check Box", screenName);       		 
			switchToFrame("default");
			driver.switchTo().frame("iCargoContentFrameOPR350");
			driver.switchTo().frame("popupContainerFrame");
			waitForSync(5);      		
			enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionLength;xpath",
					data(Length), "Dimension Length", screenName);
			keyPress("TAB");
			enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionWidth;xpath",
					data(Width), "Dimension Width", screenName);
			keyPress("TAB");
			enterValueInTextbox(sheetName, "inbx_pkgGrpDimensionHeight;xpath",
					data(Height), "Dimension Height", screenName);       		 
			enterValueInTextbox(sheetName, "inbx_numberOfPackages;xpath",
					data(numberOfPackages), "Number Of Packages", screenName);

			clickWebElement("Generic_Elements", "btn_childWinOk;xpath", "OK Button", screenName);
			waitForSync(2);
			if(qValue>1)
			{
				
				String xpath1  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "txt_errormsg;xpath");
				
				String Acttext = driver.findElement(By.xpath(xpath1)).getText();
				String Exptext="Q Value is greater than 1, please update the package quantity";
				verifyScreenTextWithExactMatch(screenName,Exptext,Acttext,"verify error message when q value is greater than 1","verify error message when q value greater than 1");
				
			}
			else
			{  
				try
				{
				 String xpath1  =xls_Read.getCellValue("CaptureDGDetails_OPR350", "txt_errormsg;xpath");
			     String Acttext = driver.findElement(By.xpath(xpath1)).getText();
				 String Exptext="Q Value is greater than 1, please update the package quantity";
				 verifyScreenTextNotExists(sheetName, Exptext, Acttext," Verify eror message is not getting displayed  when q value less than 1 ");
			   } catch(Exception e)
				{
				writeExtent("Pass","error message not triggered when q value less than 1 ");
				}
		}
	}


		/**
		 * Description... verify Q value
		 * @author A-10690
		 * @param Netquantity per package1
		 * @param Netquantity per package2
		 * @param Maximumquantity per package
		 * @throws InterruptedException,IoException
		 */	
		public double verifyQValues(String N1,String N2,String Maximumquantity) throws InterruptedException, IOException {

			int netQuantity1= Integer.parseInt(N1);
			int netQuantity2= Integer.parseInt(N2);
		     Double l= Double.parseDouble(data(Maximumquantity));
			double q=Double.valueOf(netQuantity1/l+netQuantity2/l);
			System.out.println(q);
			return q;
		
			
			
		}
	
	


		/**
		 * Description... Click Save button
		 * @author A-10690
		 * @throws InterruptedException,IoException
		 */	

		public void clickSaveButton() throws InterruptedException, IOException {


			clickWebElement(sheetName, "btn_save;id", "Save Button", screenName);
			waitForSync(7);
		}
/**
 * Description... Click Delete Shipper Reference
 * @throws InterruptedException
 * @throws IOException 
 */

public void clickDeleteShpRef() throws InterruptedException, IOException {
            clickWebElement(sheetName, "lnk_deleteShipperRef;name", "Delete Shipper Reference Link", screenName);
            
     }


/**
 * Description... Click More Details
 * @throws InterruptedException
 * @throws IOException 
 */
public void clickMoreDetails() throws InterruptedException, IOException {
	clickWebElement(sheetName,"lnk_moreDetails;xpath", "More Details Link", screenName);
	
}


}