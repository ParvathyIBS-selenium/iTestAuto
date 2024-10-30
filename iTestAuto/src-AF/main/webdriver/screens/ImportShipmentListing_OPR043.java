package screens;

import org.testng.Assert;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ImportShipmentListing_OPR043 extends CustomFunctions {
	public ImportShipmentListing_OPR043(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "ImportShipmentListing_OPR043";
	public String ScreenName = "ImportShipmentListing_OPR043";

	/**
	 * Description : To list AWB on the screen
	 * 
	 * @param stationCode
	 *            : shipment prefix
	 * @param AWBNumber
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public void listAWB(String stationCode, String AWBNumber, String Origin) throws InterruptedException, IOException  {
		enterValueInTextbox(sheetName, "inbx_ShipmentPrefix;id", stationCode, "Station code", ScreenName);
		enterValueInTextbox(sheetName, "inbx_AwbNo;id", AWBNumber, "AWB number", ScreenName);
		enterValueInTextbox(sheetName, "inbx_Airport;id", Origin, "Airport", ScreenName);
		clearText(sheetName, "inbx_Destination;id", "Destination", ScreenName);
		
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(4);
	}
	/**
	 * @author A-9844
	 * Description...verify table records are present
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyTableRecordsPresent() throws InterruptedException, Exception{
		try
		{
			String locator = xls_Read.getCellValue(sheetName, "table_importShipmentListingDetails;xpath");
			if((driver.findElements(By.xpath(locator)).size()>0)){
				writeExtent("Pass","Successfully verified the table records are listed when filtered on "+ScreenName);
			}
			else{
				writeExtent("Fail","No table records were listed when filtered on "+ScreenName);
			}

		}

		catch(Exception e)
		{
			writeExtent("Fail","No table records were present on listing on "+ScreenName);
		}

	}

	/**
	 * @author A-8783
	 * Description...select date type
	 * @param filterModeOptions
	 */
	public void selectDateType(String filterModeOptions) {

		selectValueInDropdown(sheetName, "drp_dateType;id", data(filterModeOptions), "Date type Option", "VisibleText");
	}
	/**
	 * @author A-8783
	 * Desc - Click clear button
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void clickClear() throws InterruptedException, Exception{
		clickWebElement(sheetName, "btn_clear;name", "Clear Button", ScreenName);
		waitForSync(3);
	}
	/**
	 * @author A-8783
	 * Desc - Verify onward flight fields are displayed
	 * @param carrierCode
	 * @param onwardFlightNumber
	 * @param onwardFlightDate
	 * @throws InterruptedException
	 */
	public void verifyOnwardFlightDetails() throws InterruptedException{
		verifyElementDisplayed(sheetName, "inbx_OnwardFltCarrierCode;name", "Verify carrier code", ScreenName, "Verify Onward carrier code");
		verifyElementDisplayed(sheetName, "inbx_OnwardFlightNo;name", "Verify Flight number", ScreenName, "Verify Onward Flight Number");
		verifyElementDisplayed(sheetName, "inbx_OnwardFlightDate;name", "Verify Flight date", ScreenName, "Verify Onward Flight Date");
	}

	/**
	 * @author A-9844
	 * Description...Enter onward flight details
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void enterOnwardFlightDetails(String carrierCode,String onwardFlightNumber,String onwardFlightDate) throws InterruptedException, Exception{
		waitForSync(2);		
		enterValueInTextbox(sheetName, "inbx_OnwardFltCarrierCode;name", data(carrierCode), "Carrier Code",ScreenName);
		enterValueInTextbox(sheetName, "inbx_OnwardFlightNo;name", data(onwardFlightNumber), "Onward Flight Number",ScreenName);
		enterValueInTextbox(sheetName, "inbx_OnwardFlightDate;name", data(onwardFlightDate), "Onward Flight Date",ScreenName);
		waitForSync(2);
	}

	/**
	 * @author A-9844
	 * Description...select filter mode
	 * @param filterModeOptions
	 */
	public void selectFilterMode(String filterModeOptions) {

		selectValueInDropdown(sheetName, "drp_filterMode;xpath", data(filterModeOptions), "Filter mode Option", "VisibleText");
	}





/**
	 * @author A-9844
	 * Description...verify Onward Flt. No column is blank
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyOnwardFlightNoColumnIsBlank() throws InterruptedException, Exception{

		try{
			String locator= xls_Read.getCellValue(sheetName, "txt_onwardFlightDetails;xpath").replace("CoLNo", "20");
			By ele =By.xpath(locator);
			String actText = driver.findElement(ele).getText();
			if(actText.isEmpty()){
				writeExtent("Pass","Verified the Onward flight details are empty since no bookings done on "+ScreenName);
			}
			else{
				writeExtent("Fail","Failed to verify the Onward flight details are empty on "+ScreenName);
			}
		}
		catch (Exception e) {
			writeExtent("Fail","Could not verify the Status of the Onward Flight on "+ScreenName);
		}

	}


/**
	 * @author A-9844
	 * Description...verify Column name
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifyColumn(String[] columnName) throws InterruptedException, Exception{
		int i = 0;
		int flag=0;
		try {
		String locator=xls_Read.getCellValue(sheetName,"table_importShipmentListingColumn;xpath");
		List<WebElement> column = driver.findElements(By.xpath(locator));
		 for( i=0;i<columnName.length;i++){
			flag=0;
			 for(WebElement col:column) {
			 String actText = col.getText();
			 System.out.println(actText);
			 if(actText.equals(columnName[i])) {
				
				 writeExtent("Pass", "Verified that the column " + columnName[i] + " is present in the table");
				 break;
			 }
			 else {
				 flag+=1;
			 }
			 	
		 }
			 if(flag==column.size()) {
				 writeExtent("Fail", "Failed to verify that the column " + columnName[i] + " is present in the table");

			 }

	 }
				
		}
		catch(Exception e) {
			 writeExtent("Fail", "Failed to verify if columns are present");
		}
	}



/**
	 * @author A-9844
	 * Description...click List
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void clickList() throws InterruptedException, Exception{
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(3);
	}

	/**
	 * 
	 * @param status
	 * @param AWBno
	 * @throws InterruptedException
	 */
	public void VerifyShipmentStatus(String status,String AWBno ) throws InterruptedException {

		String locator=xls_Read.getCellValue(sheetName, "txt_status;xpath");
		locator=locator.replace("Status", status);
		String ShipmentStatus = driver.findElement(By.xpath(locator)).getText();
		if (ShipmentStatus.contains(status)) {
			System.out.println("Document HandOver status is " + status);
			writeExtent("Pass", "Document HandOver status is " + status);
		} else {
			System.out.println("Document HandOver status is not " + status);
			writeExtent("Fail", "Document HandOver status is not " + status);
		}

	}

	public void verifyAWBDetails(String AWBNumber, int[] verfCols, String[] actVerfValues)
			throws Exception {
		verify_tbl_records_multiple_cols_contains(sheetName, "tbl_AwbDetails;xpath", "//td", verfCols, AWBNumber,
				actVerfValues);
	}
	
	public void VerifyStatus(String status,String AWBno ) throws InterruptedException {

		String xpath = xls_Read.getCellValue(sheetName, "tbl_AwbDetails;xpath");
		String dynxpath = xpath + "[contains(.,'" + AWBno + "')]";
			
		
		switch (status) {

		case "DocumentHandOver_NotCompleted":
			String dynxpath2 = xpath + "[contains(.,'" + AWBno + "')]//td[22]";
			WebElement img = driver.findElement(By.xpath(dynxpath2));
			String actstatus = img.getAttribute("src");
			if (actstatus.contains("cross")) {
				System.out.println("Document HandOver status is " + status);
				writeExtent("Pass", "Document HandOver status is " + status);
			} else {
				System.out.println("Document HandOver status is not " + status);
				writeExtent("Fail", "Document HandOver status is not " + status);
			}
			break;

		case "DocumentHandOver_Completed":
			String dynxpath3 = xpath + "[contains(.,'" + AWBno + "')]//td[22]";
			WebElement img2 = driver.findElement(By.xpath(dynxpath3));
			String actstatus2 = img2.getAttribute("src");
			if (actstatus2.contains("tick")) {
				System.out.println("Document HandOver status is " + status);
				writeExtent("Pass", "Document HandOver status is " + status);
			} else {
				System.out.println("Document HandOver status is not " + status);
				writeExtent("Fail", "Document HandOver status is not " + status);
			}
			break;

		case "Ready_for_Delivery":
			String dynxpath4 = xpath + "[contains(.,'" + AWBno + "')]//td[23]";
			WebElement img3 = driver.findElement(By.xpath(dynxpath4));
			String actstatus3 = img3.getAttribute("src");
			if (actstatus3.contains("tick")) {
				System.out.println("Ready for Delivery status is " + status);
				writeExtent("Pass", "Ready for Delivery status is " + status);
			} else {
				System.out.println("Ready for Delivery status is not " + status);
				writeExtent("Fail", "Ready for Delivery status is not " + status);
			}
			break;

		case "Not_Ready_for_Delivery":
			String dynxpath5 = xpath + "[contains(.,'" + AWBno + "')]//td[23]";
			WebElement img4 = driver.findElement(By.xpath(dynxpath5));
			String actstatus4 = img4.getAttribute("src");
			if (actstatus4.contains("cross")) {
				System.out.println("Ready for Delivery status is " + status);
				writeExtent("Pass", "Ready for Delivery status is " + status);
			} else {
				System.out.println("Ready for Delivery status is not " + status);
				writeExtent("Fail", "Ready for Delivery status is not " + status);
			}
			break;

		
		}
	}
	/**
	 * @author A-10690
	 * Desc - Verify manifested pieces 
	 * @param expected pieces
	 * @param awb number
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void verifyMftPieces(String pieces,String awb) throws InterruptedException{
		
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "txt_MftPiecscol;xpath");
		String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-column-key");
		String locator1=xls_Read.getCellValue(sheetName, "txt_Plndvalue;xpath");
		locator1=locator1.replace("awb",data(awb));
		locator1=locator1.replace("*",columnnumber);

		String acttext = driver.findElement(By.xpath(locator1)).getText();
		if(acttext.equalsIgnoreCase(data(pieces)))
		{
			writeExtent("Pass","Successfully verified manifested pieces "+ScreenName);
		}
		else{
			writeExtent("Fail","Failed to verify manifested pieces "+ScreenName);
		}
		
	}	
	
	/**
	 * @author A-10690
	 * Desc - Verify manifested weight
	 * @param expected weight
	 * @param awb number
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	 
	public void verifyMftWeight(String weight,String awb) throws InterruptedException{
		
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "txt_MftWeightcol;xpath");
		String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-column-key");
		String locator1=xls_Read.getCellValue(sheetName, "txt_Plndvalue;xpath");
		locator1=locator1.replace("awb",data(awb));
		locator1=locator1.replace("*",columnnumber);

		String acttext = driver.findElement(By.xpath(locator1)).getText();
		if(acttext.equalsIgnoreCase(data(weight)))
		{
			writeExtent("Pass","Successfully verified the manifested weight on "+ScreenName);
		}
		else{
			writeExtent("Fail","failed to verify manifested weight on "+ScreenName);
		}

		
		
	}	
	public void verifyOnwardFlightColumnIsBlank(String awb) throws InterruptedException, Exception{

		try{
			String locator= xls_Read.getCellValue(sheetName, "txt_onwardFltDetails;xpath").replace("*", data(awb));
			
			By ele =By.xpath(locator);
			String actText = driver.findElement(ele).getText();
			if(actText.isEmpty()){
				writeExtent("Pass","Verified the Onward flight details are empty since no bookings done on "+ScreenName);
			}
			else{
				writeExtent("Fail","Failed to verify the Onward flight details are empty on "+ScreenName);
			}
		}
		catch (Exception e) {
			writeExtent("Fail","Could not verify the Onward Flight details are empty on "+ScreenName);
		}

	}
	/**
	 * @author A-10690
	 * Description...select Status filter type
	 * @param filterModeOptions
	 */
	public void selectStatusType(String filterModeOptions) {

		selectValueInDropdown(sheetName, "drp_statusFilter;id", data(filterModeOptions), "Status filter option", "VisibleText");
	}

	/**
	 * @author A-10690
	 * Desc - Verify planned pieces 
	 * @param expected pieces
	 * @param awb number
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void verifyPlannedPieces(String pieces,String awb) throws InterruptedException, AWTException{
		
		
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "txt_PlndPiecscol;xpath");
		String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-column-key");
		String locator1=xls_Read.getCellValue(sheetName, "txt_Plndvalue;xpath");
		locator1=locator1.replace("awb",data(awb));
		locator1=locator1.replace("*",columnnumber);

		String acttext = driver.findElement(By.xpath(locator1)).getText();
		if(acttext.equalsIgnoreCase(data(pieces)))
		{
			writeExtent("Pass","Successfully verified planned pieces on "+ScreenName);
		}
		else{
			writeExtent("Fail","Failed to verify planned pieces "+ScreenName);
		}

		
		
	}	
	
	/**
	 * @author A-10690
	 * Desc - Verify planned weight 
	 * @param expected weight
	 * @param awb number
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void verifyPlannedWeight(String weight,String awb) throws InterruptedException{
		
		String locator=xls_Read.getCellValue(sheetName, "txt_PlndWeightcol;xpath");
		String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-column-key");
		String locator1=xls_Read.getCellValue(sheetName, "txt_Plndvalue;xpath");
		locator1=locator1.replace("awb",data(awb));
		locator1=locator1.replace("*",columnnumber);

		String acttext = driver.findElement(By.xpath(locator1)).getText();
		if(acttext.equalsIgnoreCase(data(weight)))
		{
			writeExtent("Pass","Successfully verified planned weight "+ScreenName);
		}
		else{
			writeExtent("Fail","Failed to verify planned weight "+ScreenName);
		}
		
	}	

	
	/**
	 * @author A-10690
	 * Desc - Verify no data available after listing  
	 * @throws InterruptedException

	 */
	public void verifyRecordsNotavailable() throws InterruptedException{
		
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "txt_recordssize;xpath");
		
		if(driver.findElements(By.xpath(locator)).size()==1)
		{
			writeExtent("Pass","Successfully verified the table records are not available "+ScreenName);
		}
		else{
			writeExtent("Fail","failed to verify table records are not available "+ScreenName);
		}

		
		
	}
	/**
	 * @author A-9844
	 * Description...verify Multiple SCCs are present
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void verifySCCsFromLov() throws InterruptedException, Exception{
		try
		{
			
			
			clickWebElement(sheetName,"btn_sccLOV;xpath","SCC LOV", ScreenName);
			waitForSync(2);

			switchToWindow("storeParent");
			switchToWindow("child");
			
			String locator = xls_Read.getCellValue(sheetName, "chk_scc;xpath");
			List<WebElement> sccs=driver.findElements(By.xpath(locator));
			int size=sccs.size();
			if(size>1){
				writeExtent("Pass","Successfully verified different SCCs are  present on SCC LOV "+ScreenName);
			}
			
			clickWebElement(sheetName,"btn_Cancel;xpath","Cancel", ScreenName);
			waitForSync(3);
			waitForSync(2);
			switchToWindow("getParent");
			switchToDefaultAndContentFrame("OPR043");

		}

		catch(Exception e)
		{
			writeExtent("Fail","Failed to verify different SCCs are  present on SCC LOV "+ScreenName);
		}

	}




	/**
	 * @author A-9844
	 * Description...select single  SCC from LOV 
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void selectSingleSCCFromLOV(String scc1) throws InterruptedException, Exception{

		
		
		waitForSync(2);
		clickWebElement(sheetName,"btn_sccLOV;xpath","SCC LOV", ScreenName);
		waitForSync(2);

		switchToWindow("storeParent");
		switchToWindow("child");

		enterValueInTextbox(sheetName, "inbx_scc;id", scc1, "SCC",ScreenName);
		clickWebElement(sheetName,"btn_sccLOVList;xpath","SCC LOV List", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName,"inbx_firstsccChkBox;xpath","SCC LOV List", ScreenName);
		clickWebElement(sheetName, "btn_OKButtonLOV;xpath", "Clicking OK in LOV", ScreenName);


		waitForSync(2);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR043");
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);
		
		
	}
	/**
	 * @author A-9844
	 * Description... Verify Table Records
	 * @param verfCols
	 * @param actVerfValues
	 * @param FullAWBNumber
	 * @throws IOException 
	 */
	//verifying Table Records

	public void verifyTableRecords(int verfCols[],String actVerfValues[],String shipmentDesc) throws IOException{
		 
		verify_tbl_records_multiple_cols(sheetName, "tbl_AwbDetails;xpath", "//td", verfCols, shipmentDesc, actVerfValues);
	}

	/**
	 * @author A-9844
	 * Description...select multiple SCCs from LOV 
	 * @throws Exception 
	 * @throws InterruptedException 
	 */
	public void selectMultipleSCCFromLOV(String scc1,String scc2) throws InterruptedException, Exception{
		
		
		clickWebElement(sheetName,"btn_sccLOV;xpath","SCC LOV", ScreenName);
		waitForSync(2);

		switchToWindow("storeParent");
		switchToWindow("child");

		enterValueInTextbox(sheetName, "inbx_scc;id", scc1 +","+scc2, "SCC",ScreenName);
		clickWebElement(sheetName,"btn_sccLOVList;xpath","SCC LOV List", ScreenName);
		clickWebElement(sheetName,"inbx_firstsccChkBox;xpath","SCC LOV List", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName,"inbx_secondsccChkBox;xpath","SCC LOV List", ScreenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_OKButtonLOV;xpath", "Clicking OK in LOV", ScreenName);


		waitForSync(2);
		switchToWindow("getParent");
		switchToDefaultAndContentFrame("OPR043");
		clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
		waitForSync(2);
		
		
		
	}
    /**
 * @author A-9844
 * Description...Enter flight number
 * @throws Exception 
 * @throws InterruptedException 
 */
public void enterFlightNumber(String carrierCode,String flightNumber) throws InterruptedException, Exception{
	enterValueInTextbox(sheetName, "inbx_FltCarrierCode;id", data(carrierCode), "Carrier Code",ScreenName);
	enterValueInTextbox(sheetName, "inbx_FlightNo;id", data(flightNumber), "Flight Number",ScreenName);
	waitForSync(2);
}
/**
 * @author A-9844
 * Description...Enter flight date
 * @throws Exception 
 * @throws InterruptedException 
 */
public void enterFlightDate(String flightDate) throws InterruptedException, Exception{
	enterValueInTextbox(sheetName, "inbx_FlightDate;id", data(flightDate), "Flight Date",ScreenName);

}
/**
 * @author A-9844
 * Description...verify SCC field is present
 * @throws Exception 
 * @throws InterruptedException 
 */
public void verifySCCField() throws InterruptedException, Exception{
	try
	{
		String locator = xls_Read.getCellValue(sheetName, "inbx_sccField;xpath");
		if((driver.findElements(By.xpath(locator)).size()>0)){
			writeExtent("Pass","Successfully verified SCC field present on "+ScreenName);
		}

	}

	catch(Exception e)
	{
		writeExtent("Fail","Failed to verify SCC filed present on "+ScreenName);
	}

}
/**
 * @author A-9844
 * Description...select multiple SCC from LOV and verify whether shipments with the selected SCCs are listed
 * @throws Exception 
 * @throws InterruptedException 
 */
public void selectMultipleSCCFromLOVAndVerifyShipments(String scc1,String scc2,HashMap<String, String> hm) throws InterruptedException, Exception{
	ArrayList<String> actArrayList=new ArrayList<String>();
	ArrayList<String> expArrayList=new ArrayList<String>();

	clickWebElement(sheetName,"btn_sccLOV;xpath","SCC LOV", ScreenName);
	waitForSync(2);

	switchToWindow("storeParent");
	switchToWindow("child");

	enterValueInTextbox(sheetName, "inbx_scc;id", scc1 +","+scc2, "SCC",ScreenName);
	clickWebElement(sheetName,"btn_sccLOVList;xpath","SCC LOV List", ScreenName);
	clickWebElement(sheetName,"inbx_firstsccChkBox;xpath","SCC LOV List", ScreenName);
	waitForSync(2);
	clickWebElement(sheetName,"inbx_secondsccChkBox;xpath","SCC LOV List", ScreenName);
	waitForSync(2);
	clickWebElement(sheetName, "btn_OKButtonLOV;xpath", "Clicking OK in LOV", ScreenName);


	waitForSync(2);
	switchToWindow("getParent");
	switchToDefaultAndContentFrame("OPR043");
	clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
	waitForSync(2);
	
	
	for (Entry<String, String> entry: hm.entrySet())

    {

        if (entry.getValue().contains(scc1) ||entry.getValue().contains(scc2) ) {
           
            String awbNum= entry.getKey();
            System.out.println(awbNum);
            
            actArrayList.add(awbNum);
            System.out.println(actArrayList);
        }

        
        
       
    }
	String awbNoTable=xls_Read.getCellValue(sheetName, "tbl_awbRowVaues;xpath");
	
	List<WebElement> eleawb=driver.findElements(By.xpath(awbNoTable));
	String sccRowTable=xls_Read.getCellValue(sheetName, "tbl_sccRowVaues;xpath");
	List<WebElement> elescc=driver.findElements(By.xpath(sccRowTable));
	int length=eleawb.size();

	
	for (int i = 0; i < length; i++){
		
		
		String awbno=eleawb.get(i).getText().trim();
		System.out.println(awbno);
		String awbscc=elescc.get(i).getText().trim();
		System.out.println(awbscc);
		
		
		expArrayList.add(awbno);
        System.out.println(expArrayList);
		
		
		
	}
	
	Collections.sort(actArrayList);
	 System.out.println(actArrayList);
		
	Collections.sort(expArrayList);
	
	 System.out.println(expArrayList);
		
	
	
	if(actArrayList.equals(expArrayList))
	{
		
		writeExtent("Pass","Verified awb numbers "+actArrayList+" for the selected scc"+scc1+" and "+scc2);
		
	}
	else{
		writeExtent("Fail"," failed to verify awb numbers "+actArrayList+" for the selected scc"+scc1+" and "+scc2);
	}
	
}
/**
 * @author A-9844
 * Description...click edit button
 * @throws Exception 
 * @throws InterruptedException 
 */
public void clickEditButton() throws InterruptedException, IOException  {

	clickWebElement(sheetName, "btn_edit;xpath", "Edit Button", ScreenName);
	waitForSync(2);
}
/**
 * @author A-9844
 * Description...select single  SCC from LOV and verify whether the shipments with the selected SCC is listed
 * @throws Exception 
 * @throws InterruptedException 
 */
public void selectSingleSCCFromLOVAndVerifyShipments(String scc1,HashMap<String, String> hm) throws InterruptedException, Exception{

	ArrayList<String> actArrayList=new ArrayList<String>();
	ArrayList<String> expArrayList=new ArrayList<String>();
	clickWebElement(sheetName,"btn_sccLOV;xpath","SCC LOV", ScreenName);
	waitForSync(2);

	switchToWindow("storeParent");
	switchToWindow("child");

	enterValueInTextbox(sheetName, "inbx_scc;id", scc1, "SCC",ScreenName);
	clickWebElement(sheetName,"btn_sccLOVList;xpath","SCC LOV List", ScreenName);
	waitForSync(2);
	clickWebElement(sheetName,"inbx_firstsccChkBox;xpath","SCC LOV List", ScreenName);
	clickWebElement(sheetName, "btn_OKButtonLOV;xpath", "Clicking OK in LOV", ScreenName);


	waitForSync(2);
	switchToWindow("getParent");
	switchToDefaultAndContentFrame("OPR043");
	clickWebElement(sheetName, "btn_List;id", "List Button", ScreenName);
	waitForSync(2);
	
	for (Entry<String, String> entry: hm.entrySet())

    {

        if (entry.getValue().contains(scc1)) {
           
            String awbNum= entry.getKey();
            System.out.println(awbNum);
            
            actArrayList.add(awbNum);
            System.out.println("ArrayList 1:"+actArrayList);
        }

       
    }
	String awbNoTable=xls_Read.getCellValue(sheetName, "tbl_awbRowVaues;xpath");
	
	List<WebElement> eleawb=driver.findElements(By.xpath(awbNoTable));
	String sccRowTable=xls_Read.getCellValue(sheetName, "tbl_sccRowVaues;xpath");
	List<WebElement> elescc=driver.findElements(By.xpath(sccRowTable));
	int length=eleawb.size();

	
	for (int i = 0; i < length; i++){
		
		
		String awbno=eleawb.get(i).getText().trim();
		System.out.println(awbno);
		String awbscc=elescc.get(i).getText().trim();
		System.out.println(awbscc);
		
		
		expArrayList.add(awbno);
        System.out.println("ArrayList 2:"+expArrayList);
		
		
		
	}
	
	Collections.sort(actArrayList);
	Collections.sort(expArrayList);
	
	
		
	
	
	if(actArrayList.equals(expArrayList))
	{
		
		writeExtent("Pass","Verified awb numbers "+actArrayList+" for the selected scc "+scc1);
		
	}
	else{
		writeExtent("Fail"," failed to verify awb numbers "+actArrayList+" for the selected scc "+scc1);
	}
	

}

	public void clickClose() throws InterruptedException, IOException  {
		
		clickWebElement(sheetName, "btn_Close;id", "Close Button", ScreenName);
		waitForSync(2);
	}

	

}