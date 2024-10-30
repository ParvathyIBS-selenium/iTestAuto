package screens;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.AWTException;
import java.io.IOException;
import java.util.*;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class DeliverNoteEnquiry_OPR034 extends CustomFunctions{
	public DeliverNoteEnquiry_OPR034(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="DeliveryNoteEnquiry_OPR034";
	public String ScreenName="DeliveryNoteEnquiry";
	//public CustomFunctions comm;
	CustomFunctions comm=new CustomFunctions(driver, excelreadwrite, xls_Read);
	
	/**
     * @author A-9478
     * @param awb prefix, awb number
     * @throws InterruptedException
     * Description : List by AWB number
	 * @throws IOException 
     */
	public void listByAWB(String awbPrefix, String awbNo) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_awbPrefix;xpath", data(awbPrefix), "AWB Prefix", ScreenName);
		enterValueInTextbox(sheetName, "inbx_awbNo;xpath", data(awbNo), "AWB Number", ScreenName);
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
	}
	
	/**
     * @author A-9478
     * @param DN number
     * @throws InterruptedException
     * Description : List by DN number
	 * @throws IOException 
     */
	public void listByDNNumber(String DNNo) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_Dnno;name", data(DNNo), "DN Number", ScreenName);
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
	}
	
	/**
     * @author A-9478
     * @param status
     * @throws InterruptedException
     * Description : verify DN status
     */
	public void verifyDNStatus(String status) throws InterruptedException
    {
        String DNStatus=getElementText(sheetName, "DNStatus;xpath", "DN Status", ScreenName);
        boolean deliveryStatus = DNStatus.contains(status);
        verifyValueOnPage(true, deliveryStatus, "Verify DN Status", ScreenName, "DN status");
        
}
	
}
