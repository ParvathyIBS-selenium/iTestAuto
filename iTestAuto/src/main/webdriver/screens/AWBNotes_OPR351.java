package screens;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.*;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AWBNotes_OPR351 extends CustomFunctions{
	public AWBNotes_OPR351(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="AWBNotes_OPR351";
	public String screenName="AWB Notes";



	/**
	 * @author A-9844
	   Description... List AWB
	 * @param awbNo
	 * @param ShipmentPrefix
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void listAWBNo(String ShipmentPrefix,String awbNo) throws InterruptedException, IOException{

		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(ShipmentPrefix), "Shipment Prefix",screenName);
		enterValueInTextbox(sheetName, "inbx_AWBnumber;xpath", data(awbNo), "AWB No", screenName);
		clickWebElement(sheetName, "btn_List;xpath", "List Button", screenName);
		waitForSync(7);
	}


	/**
	 * @author A-9844
	 * Desc..Enter remarks
	 * @param remarks
	 * @throws InterruptedException
	 */
	public void enterInstuctionRemarks(String remarks) throws InterruptedException{

		enterValueInTextbox(sheetName, "txt_remarks;xpath", data(remarks), "Remarks", screenName);
		waitForSync(2);
	}


	/**
	 * @author A-9844
	 * Desc..click Save
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_Save;xpath", "Save Button", screenName);
		waitForSync(2);
	}

	
	/**
	 * @author A-9844
	 * Desc..click AddNew
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickaddNew() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_AddNew;name", "Add Button", screenName);
		waitForSync(3);
	}
	

}

