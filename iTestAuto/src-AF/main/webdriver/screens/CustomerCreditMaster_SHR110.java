package screens;

import org.testng.Assert;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CustomerCreditMaster_SHR110 extends CustomFunctions {
	public CustomerCreditMaster_SHR110(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "CustomerCreditMaster_SHR110";
	public String ScreenName = "CustomerCreditMaster_SHR110";

	/**
	 *@author A-9478
	 *Description: List by customer code
	 */
	public void listCustomerCode(String customerCode) throws Exception {
		enterValueInTextbox(sheetName, "inbx_CustomerCode;id", data(customerCode), "Customer code", ScreenName);
		clickWebElement(sheetName, "btn_list;name", "List Button", ScreenName);
		waitForSync(2);
	}
	/**
     *@author A-9478
     *Description: Enter credit limit threshold percentage
     */
     public void enterCreditLimitThreshold(String creditLimitThreshold) throws Exception {
           enterValueInTextbox(sheetName, "inbx_creditLimitThreshold;id", creditLimitThreshold, "Credit Limit Threshold", ScreenName);
           waitForSync(2);
     }

/**
     *@author A-9478
     *Description: Click Save button
     */
     public void clickSave() throws Exception {
           clickWebElement(sheetName, "btn_Save;id", "Save Button", ScreenName);
           waitForSync(2);
     }
     
     
     /**
     *@author A-9478
     *Description: Enter credit limit threshold percentage
     */
     public String getCreditLimitThreshold() throws Exception {
           String creditLimit = getAttributeWebElement(sheetName,"inbx_creditLimitThreshold;id",
                       "Credit Limit Threshold", "value",ScreenName);
           return creditLimit;
     }
     
     /**
     *@author A-9478
     *Description: Check Override checkbox
     */
     public void selectOverrideCheckbox(boolean selectOverride) throws Exception
     {
           String locator = xls_Read.getCellValue(sheetName, "chbx_Override;xpath");
           if(selectOverride==true && !driver.findElement(By.xpath(locator)).isSelected())
           {
                 driver.findElement(By.xpath(locator)).click();
                 writeExtent("Pass", " Selected Override checkbox in "+ScreenName);
           }
           else
           {
                 if(driver.findElement(By.xpath(locator)).isSelected())
                 {
                       driver.findElement(By.xpath(locator)).click();
                       writeExtent("Pass", " Unchecked Override checkbox in "+ScreenName);
                 }
           }
           
                 waitForSync(2);
           
     }
     /**
     *@author A-9478
     *Description: verify Override checkbox is checked or not
     */
     public boolean verifyOverrideCheckbox() throws Exception
     {
           String locator = xls_Read.getCellValue(sheetName, "chbx_Override;xpath");
           if(driver.findElement(By.xpath(locator)).isSelected())
           {
                 return true;
           }
           else
           {
                 return false;
           }
           
     }
     /**
 	 *@author A-9175
 	 *Description: Get by balance available value of import
 	 */
 	public String getBalanceAvailableImport() throws Exception
 	{
 		String balance = getAttributeWebElement(sheetName,"txt_BalanceAvailableImport;id",
 				"Balance Available Import Credit Details", "value",ScreenName);
 		return balance;
 	} 
 	
 	/**
 	 *@author A-9175
 	 *Description: Verify balance available value
 	 */
 	public void verifyBalanceAvailableImport(String BalanceAmount) throws Exception
 	{
 		String balance = getAttributeWebElement(sheetName,"txt_BalanceAvailableImport;id",
 				"Balance Available Import Credit Details", "value",ScreenName);
 		verifyScreenTextWithExactMatch(ScreenName, data(BalanceAmount), balance, " Balance Amount ",
 			"Verified Sucessfully");
 		waitForSync(2);
 	}


	/**
	 *@author A-9478
	 *Description: Get by balance available value of export
	 */
	public String getBalanceAvailable() throws Exception
	{
		String balance = getAttributeWebElement(sheetName,"txt_BalanceAvailable;id",
				"Balance Available", "value",ScreenName);
		return balance;
	} 
	
	/**
	 *@author A-9478
	 *Description: Get export guarantee amount value
	 */
	public String getExportGuaranteeAmount() throws Exception
	{
		String guranteeAmount = getAttributeWebElement(sheetName,"inbx_exportGuarenteeAmount;name",
				"Export Guarantee Amount", "value",ScreenName);
		return guranteeAmount;
	} 
	/**
     *@author A-9478
     *Description: Verify import Guarantee amount
     */
     public void verifyImportGuaranteeAmount(String GuaranteeAmount) throws Exception
     {
           String guranteeAmount = getAttributeWebElement(sheetName,"inbx_importGuarenteeAmount;name",
                       "Import Guarantee Amount", "value",ScreenName);
           verifyScreenTextWithExactMatch(ScreenName, data(GuaranteeAmount), guranteeAmount, " Import Guarantee Amount ",
                 "Verified Sucessfully");
           waitForSync(2);
     }

/**
     *@author A-9478
     *Description: Get import guarantee amount value
     */
     public String getImportGuaranteeAmount() throws Exception
     {
           String guranteeAmount = getAttributeWebElement(sheetName,"inbx_importGuarenteeAmount;name",
                       "Import Guarantee Amount", "value",ScreenName);
           return guranteeAmount;
     }

	/**
	 *@author A-9478
	 *Description: Verify balance available value
	 */
	public void verifyBalanceAvailable(String BalanceAmount) throws Exception
	{
		String balance = getAttributeWebElement(sheetName,"txt_BalanceAvailable;id",
				"Balance Available", "value",ScreenName);
		verifyScreenTextWithExactMatch(ScreenName, data(BalanceAmount), balance, " Balance Amount ",
			"Verified Sucessfully");
		waitForSync(2);
	}
	
	/**
	 *@author A-9478
	 *Description: Verify Guarantee amount
	 */
	public void verifyGuaranteeAmount(String GuaranteeAmount) throws Exception
	{
		String guranteeAmount = getAttributeWebElement(sheetName,"inbx_exportGuarenteeAmount;name",
				"Export Guarantee Amount", "value",ScreenName);
		verifyScreenTextWithExactMatch(ScreenName, data(GuaranteeAmount), guranteeAmount, " Export Guarantee Amount ",
			"Verified Sucessfully");
		waitForSync(2);
	}
	

}