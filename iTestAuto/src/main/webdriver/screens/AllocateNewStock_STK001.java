package screens;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AllocateNewStock_STK001 extends CustomFunctions {
	String sheetName = "AllocateNewStock_STK001";
	public CustomFunctions customFuction;
	String screenID = "STK001";
	public String screenName = "AllocateNewStock";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";

	public AllocateNewStock_STK001(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
	}
	
	/**
	 * @Description : Enter Document Details
	 * @author A-9844
	 * @param docType
	 * @param docSubType
	 * @throws InterruptedException
	 */
		
	public void enterDocumentDetails(String docType,String docSubType) throws InterruptedException{
	
		selectValueInDropdown(sheetName, "inbx_docType;xpath", data(docType), "Document Type dropdown", "VisibleText");
		waitForSync(2);
		selectValueInDropdown(sheetName, "inbx_subType;id", data(docSubType), "Document Subtype dropdown", "VisibleText");
	}
	
	/**
	 * @Description : Enter Stock Holder Details
	 * @author A-9844
	 * @param aprrover
	 * @param agent
	 * @param stockHolderCode
	 * @throws InterruptedException
	 */
	
	public void enterStockHolderDetails(String aprrover,String agent,String stockHolderCode) throws InterruptedException{
		
		enterValueInTextbox(sheetName, "inbx_Approver;id", data(aprrover), "Approver", screenName);
		waitForSync(2);
		selectValueInDropdown(sheetName, "inbx_stockHolderType;name", data(agent), "Agent", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_stockHolderCode;name", data(stockHolderCode), "Stock Holder Code", screenName);
	}
	
	/**
	 * @Description : Enter Available Stocks
	 * @author A-9844
	 * @param rangeFrom
	 * @param rangeTo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void enterAvailableStock(String rangeFrom,String rangeTo) throws InterruptedException, IOException{
		
		enterValueInTextbox(sheetName, "inbx_stockFrom;xpath", data(rangeFrom), "Range From", screenName);
		enterValueInTextbox(sheetName, "inbx_stockTo;xpath", data(rangeTo), "Range To", screenName);
		clickWebElement(sheetName, "btn_List;xpath", "List button", screenName);
		waitForSync(3);
	}
	

	/**
	 * @Description : To save details
	 * @author A-9844
	 * @throws InterruptedException
	 * @throws AWTException
	 * @throws IOException
	 */
	public void saveDetails() throws InterruptedException, AWTException, IOException
	{
		
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button",screenName);
		waitForSync(3);
	}
	
	/**
	 * @Description : Allocate Stocks
	 * @author A-9844
	 * @param rangeFrom
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws AWTException
	 */
	public void allocateStock(String rangeFrom) throws InterruptedException, IOException, AWTException{
		
		String locator = xls_Read.getCellValue(sheetName, "tbl_stockRow;xpath");
        locator = locator.replace("*", data(rangeFrom));
        if(driver.findElements(By.xpath(locator)).size()>0){
		
        	
		clickWebElement(sheetName, "chk_stockRow;xpath", "click row", screenName);   
		clickWebElement(sheetName, "btn_moveRange;xpath", "Move range", screenName);
		waitForSync(2);
		saveDetails();
		switchToFrame("default");
		clickWebElement("Generic_Elements", "btn_yes;xpath", "Yes Button", screenName);
		
		switchToFrame("contentFrame", screenID);
		waitForSync(1);
		verifyElementDisplayed(sheetName,"htmlDiv_saveSuccessful;xpath", " Save Successful", screenName, "save successful");	
	   }
        else{
        	writeExtent("Fail","No available stocks present on "+screenName);
        }
        
	
	
}
}



