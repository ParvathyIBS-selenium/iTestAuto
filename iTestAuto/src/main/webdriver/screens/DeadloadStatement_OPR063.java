package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class DeadloadStatement_OPR063 extends CustomFunctions{
	String sheetName = "DeadloadStatement_OPR063";
	String screenName = "Deadload Statement";
	public DeadloadStatement_OPR063(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	
	}
/**
 * Description... Change Operating Reference
 * @param flightDate
 * @throws InterruptedException
 */
public void changeOperatingReference(String flightDate) throws InterruptedException {
	enterValueInTextbox(sheetName, "inbx_opRefCarrierCode;xpath", data("opRefCarrierCode"), "Operating Reference Carrier Code", screenName);
	enterValueInTextbox(sheetName, "inbx_opRefFlightNumber;xpath", data("opRefFlightNumber"), "Operating Reference Flight Number", screenName);
	enterValueInTextbox(sheetName, "inbx_opRefFlightDate;xpath", flightDate, "Operating Reference Flight Date", screenName);
	//save(screenName);
	handleAlert("Accept", screenName);
	waitForSync(4);
	switchToFrame("contentFrame","OPR063");
	waitForSync(10);
}
/***A-10690
 * Verifies enter actual weight for bulk
 * @param weight
 * @throws interrupt exception
 */
public void enterActualWeightForBulk(String Weight) throws InterruptedException {
	
	String dynXpath = xls_Read.getCellValue(sheetName, "tbl_Bulkdetails1;xpath");
	String dynXpath1 = dynXpath + "[8]//input";
	enterValueInTextbox(dynXpath1,data(Weight), "actual Weight", screenName);
	waitForSync(4);
	
	
}
/**A-10690
 * Verifies loadability status
 * @param expected status
 * @throws Exception
 */
public void verifyLoadabilityStatus(String status) throws Exception {
	
	
	switchToWindow("storeParent");
	switchToWindow("child");
	String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","dropdown_loadabilitystatus;xpath");
	while(driver.findElements(By.xpath(locator)).size()!=1)
	{
		waitForSync(1);
	}
	String actual = driver.findElement(By.xpath(locator)).getText();
if(actual.equals(status)){
	        onPassUpdate(screenName,status ,actual ,
	                "Loadability status verification",
	                "1.List Flight 2. Check loadability status");
	    }
	    else{
	        onFailUpdate(screenName,status ,actual ,
	                "Loadability status verification",
	                "1.List Flight 2.  Check loadability status");
	    }
	
}	
/**
 * @author A-10690
 * Desc ..Select the linked ULD
 * @throws InterruptedException
 * @throws IOException
 */
public void selectLinkedULD(String uld) throws InterruptedException, IOException{
	
	
	
	clickWebElement(sheetName, "selectlinkeduld;name", "select Button", screenName);
	String locator = xls_Read.getCellValue(sheetName, "selectuldnolink;xpath");
	locator = locator.replace("*",data(uld));
	driver.findElement(By.xpath(locator)).click();
	waitForSync(2);
}


/**
 * @author A-10690
 * Desc ..Enter Overhang details
 * @throws InterruptedException
 * @throws IOException
 */
public void captureOverhang(String overhang) throws InterruptedException, IOException{
	
	
	waitForSync(1);
	
enterValueInTextbox(sheetName, "inbx_backwardSide;xpath", data(overhang), "Overhang details", screenName);

}

/**
 * @author A-7271
 * @param uldNumber
 * @param expectedWt
 * Desc : verification of actual ULD wt
 */
public void verifyULDActualWeight(String uldNumber,String expectedWt)
{
	String locator = xls_Read.getCellValue(sheetName, "table_ULDDetails;xpath");
	List<WebElement> rows=driver.findElements(By.xpath(locator));
	int row=0;

	for(WebElement uldNum:rows)
	{
		row++;

		if(uldNum.getText().replaceAll(" ", "").contains(data(uldNumber)))

			break;
	}
	String actualWt=driver.findElement(By.xpath("("+locator+")["+row+"]//td[11]//input")).getAttribute("value");
	System.out.println(actualWt);
	verifyScreenTextWithExactMatch(screenName, data(expectedWt), actualWt, "Verification of actual wt","Verification of actual ULD wt");

	
}


/**
 * @author A-6260
 * Desc ..Click Save
 * @throws InterruptedException
 * @throws IOException
 */
public void clickSave() throws InterruptedException, IOException{
	clickWebElement(sheetName, "btn_save;name", "Save button", screenName);
	waitForSync(2);
	switchToFrame("default");
	clickWebElement(sheetName, "btn_Yes;xpath", "Yes Button", screenName);
	Thread.sleep(2000);
	switchToFrame("contentFrame","OPR063");
	waitForSync(3);
}
/**
 * @author A-6260
 * Desc..Capture overhang indent details
 * @param front
 * @param rear
 * @param left
 * @param right
 * @throws Exception
 */
public void captureOverhangIndentDetails(String front,String rear,String left,String right) throws Exception {
	waitForSync(3);
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(2);
	enterValueInTextbox(sheetName, "inbx_forwardSide;xpath", data(front), "Front", screenName);
	enterValueInTextbox(sheetName, "inbx_leftSide;xpath", data(left), "Left", screenName);
	enterValueInTextbox(sheetName, "inbx_rightSide;xpath", data(right), "Right", screenName);
	enterValueInTextbox(sheetName, "inbx_backwardSide;xpath", data(rear), "Rear", screenName);
	keyPress("TAB");
		
}
/**
 * @author A-6260
 * Desc..Enter actual weight of uld
 * @param weight
 * @throws InterruptedException
 */
public void enterActualWeightOfUld(String weight) throws InterruptedException{
	enterValueInTextbox(sheetName, "inbx_uldActWt;name", data(weight), "ULD actual weight", screenName);
	
}
/**
 * @author A-10690
 * Desc ..Enter overhang pieces
 * @param pieces
 * @throws InterruptedException
 * @throws IOException
 */
public void captureOverhangPcs(String pieces) throws InterruptedException, IOException{
		

	enterValueInTextboxByJS(sheetName,"inbx_pcsoverhangbackside;id", data(pieces), "Overhang details", screenName);

}
/**
 * @author A-10690
 * Desc ..Enter Indent pieces
 * @param pieces
 * @throws InterruptedException
 * @throws IOException
 */
public void captureIndentPcs(String pieces) throws InterruptedException, IOException{
	

	enterValueInTextboxByJS(sheetName,"inbx_fwdPieceUp;name", data(pieces), "Overhang details", screenName);

}

/**
 * @author A-10690
 * Desc ..Enter Indent details
 * @param front
 * @param rear
 * @param left
 * @param right
 * @throws InterruptedException
 * @throws IOException
 * @throws AWTException 
 */
public void captureIndent(String front,String rear,String left,String right) throws InterruptedException, IOException, AWTException{
	
	
	waitForSync(2);
	enterValueInTextbox(sheetName, "inbx_leftSide;xpath", data(left), "indent details", screenName);
	enterValueInTextbox(sheetName, "inbx_rightSide;xpath", data(right), "indent details", screenName);
	enterValueInTextbox(sheetName, "inbx_backwardSide;xpath", data(rear), "Rear", screenName);
	enterValueInTextbox(sheetName, "inbx_forwardSide;xpath", data(front), "indent details", screenName);
	keyPress("TAB");

}


/**
 * Desc : Verifying Flight Details
 * @author A-9175
 */
public void verifyFlightDetails() {
	try 
	{
		String locator2=xls_Read.getCellValue(sheetName, "lbl_flightDetails;xpath");
		List<WebElement> elements=new ArrayList<WebElement>();
		elements=driver.findElements(By.xpath(locator2));
		System.out.println(elements.size());
		for(int i=1;i<=elements.size();i++)
		{
			if(i==1)
			{
				String FlightContent=driver.findElement(By.xpath("("+locator2+")["+i+"]")).getAttribute("title");
				writeExtent("Pass", "Flight Details displayed as : "+ FlightContent+ screenName + " Page");
			}
			else
			{
				String FlightContent=driver.findElement(By.xpath("("+locator2+")["+i+"]")).getText();
				writeExtent("Pass", "Flight Details displayed as : "+ FlightContent+ screenName + " Page");
			}
		}
	} catch (Exception e) {
		writeExtent("Fail","Element Not Displayed"+ screenName + " Page");
	}

}

/**
 * Description... List Flight Details
 * @param carrierCode
 * @param fltNo
 * @param fltDate
 * @throws InterruptedException
 * @throws IOException 
 */
	public void  listFlightDetails(String carrierCode,String fltNo,String fltDate) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;name", data(carrierCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_fligtNo;name", data(fltNo), "Flight Number", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;name", data(fltDate), "Flight Date", screenName);
		clickWebElement(sheetName, "btn_list;name","List Button", screenName);	
		waitForSync(4);
				
	}
	/*A-8705
* Description : Select Entry from BULK Table
*/
/**
 * Description... Select Entry from BULK Table
 * @param pmyKey
 * @throws InterruptedException
 */
public void selectBulk(String pmyKey) throws InterruptedException{
      selectTableRec(pmyKey, sheetName, "chk_selectBULK;xpath", "chk_BULKdetails1;xpath", 3);
      waitForSync(2);

}
/**
 * Description... Select ULD
 * @param pmyKey
 * @throws InterruptedException
 */
public void selectULD(String pmyKey) throws InterruptedException{
	try{
		String locator = xls_Read.getCellValue(sheetName, "txt_ULDNo;xpath");
		WebElement entry = driver.findElement(By.xpath(locator));
		moveScrollBar(entry);
		selectTableRec(pmyKey, sheetName, "chk_select1ULD;xpath", "chk_ULDdetails1;xpath", 3);
		waitForSync(2);
		writeExtent("Pass", "ULD Found and Selected in: "+ screenName + " Page");
	}
	catch (Exception e) {
		writeExtent("Fail", "ULD not found: "+ screenName + " Page");
	}


}

/**
 * Desc : Verifying CONTOUR Loading Instructor Pop UP
 * @author A-8783
 * @param flightNo
 * @param uldNum
 * @param contour
 
 * @throws Exception
 */
public void verifyContourInstuctorPopUp(String flightNo, String uldNum,String contour) throws Exception{
	
	
	waitForSync(3);
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(2);
	
	String actContour=getAttributeWebElement(sheetName, "inbx_contour;name", " Contour ",
			"value", screenName);
    verifyScreenText(sheetName, data(contour), actContour,  " Contour ", screenName);
	
	
	String FlightNo=getAttributeWebElement(sheetName, "inbx_FlightNumber;name", "Flight Number",
			"value", screenName);
    verifyScreenText(sheetName, data(flightNo), FlightNo,  " Flight Number ", screenName);
   
    
	String ULDNum=getAttributeWebElement(sheetName, "inbx_ULDNo;name", "ULD Number",
			"value", screenName);
    verifyScreenText(sheetName, data(uldNum), ULDNum,  " ULD Number ", screenName);
   
	
}


/**
 * @author A-6260
 * Desc..Enter actual weight of uld 
 * @param row
 * @param weight
 * @throws InterruptedException
 */
public void enterActualWeightOfUld(int row,String weight) throws InterruptedException{
	waitForSync(2);

	String locator = xls_Read.getCellValue(sheetName, "inbx_ULDActualWeight;xpath");
	locator = locator.replace("*", Integer.toString(row));
	driver.findElement(By.xpath(locator)).sendKeys(data(weight));

}
/**
 * @author A-6260
 * @param uldnum
 * @param weight
 * @throws InterruptedException
 * Desc : enter actualweight of ULD
 */
public void enterActualWeightOfUld(String uldnum,String weight) throws InterruptedException{
	waitForSync(2);

	List<WebElement> rows = driver.findElements(By.xpath(xls_Read
			.getCellValue(sheetName, "chk_select1ULD;xpath")));
	try
	{
	for(int i=0;i<rows.size();i++) {
		String locator = xls_Read.getCellValue(sheetName, "txt_uldnum;xpath");
		locator=locator.replace("row", Integer.toString(i+1));
		if (driver.findElement(By.xpath(locator)).getText().equals(uldnum)){
			String uldWeightlocator = xls_Read.getCellValue(sheetName, "inbx_ULDActualWeight;xpath");
			uldWeightlocator = uldWeightlocator.replace("*", Integer.toString(i+1));
			driver.findElement(By.xpath(uldWeightlocator)).sendKeys(data(weight));
			writeExtent("Pass", "Entered actual weight of uld in "+screenName);
			break;
		}
		
	}}
	catch(Exception e)
	{
		writeExtent("Fail", "Couldn't enter actual weight of uld in "+screenName);
	}
}
	/*Description... Selects ULD depending on the composite keys.
* @author A-8705
* 
*/

/**
 * Description... Select ULD in DeadLoad
 * @param pmKey
 */

public void selectULDinDeadLoad(String pmKey) {
      selectCheckBoxinTableRecord(pmKey, sheetName, "tbl_ULDdetails;xpath", "chk_ULDdetails_new;xpath", 3);
      waitForSync(2);
      
}
/**
 * @author A-8783
 * Desc - Capture Linkage Details
 * @param ULD
 * @param reason
 * @throws Exception
 */
public void captureLinkageDetails(String uldNo, String reason) throws Exception {
	
	
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(4);
	selectValueInDropdown(sheetName, "dpdwn_captureLinkage;name", data(uldNo), "Floating Pallet", "VisibleText");
	enterValueInTextbox(sheetName, "txt_linkagereason;name", data(reason), "Linkage Reason", screenName);

}

/**
 * @author A-8783
 * Desc - Select Floating Pallet Yes or No
 * @param option
 * @throws InterruptedException 
 */
public void selectFloatingPallet(String option, String noOfPstns) throws InterruptedException{
selectValueInDropdown(sheetName, "dpdwn_floatingPallet;name", data(option), "Floating Pallet", "VisibleText");
enterValueInTextbox(sheetName, "txt_noofpositions;xpath", data(noOfPstns), "No of positions", screenName);
}
/**
 * @author A-8783
 * Desc - Switch window based on the value passed
 * @param window
 * @throws Exception
 */
public void switchWindow() throws Exception{
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(4);

}

/**
 * Description... Click Print Tag
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickPrintTag() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_PrintTag;xpath", "Print Tag Button", screenName);
		handleAlert("Accept", screenName);
	}
/**
 * Description...	Click Send Provisional
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickSendProvisional() throws InterruptedException, IOException{
		clickWebElement(sheetName, "btn_SendProvisional;xpath", "Send Provisional Button", screenName);
        waitForSync(3);
        
        verifyElementDisplayed(sheetName,"htmlDiv_sendProvisional;xpath", "Send Provisional", screenName, "Send Provisional Message");
    			
		
	}
	
/**
 * Description...	Get DCS Reporting Status
 * @return
 * @throws InterruptedException
 */
	public String getDCSReportingStatus() throws InterruptedException{
		 return getElementText(sheetName, "txt_DCSReportingStatus;xpath", "DCS Reporting Status", screenName);
	}
/**
 * Description... Change Operating Reference
 * @param carrierCode
 * @param flightNumber
 * @throws InterruptedException
 */
public void changeOperatingRef(String carrierCode,String flightNumber) throws InterruptedException{
		enterValueInTextbox(sheetName, "inbx_opRefCarrierCode;xpath", carrierCode, "Operating reference carrier code", screenName);
		
		enterValueInTextbox(sheetName, "inbx_opRefFlightNumber;xpath", flightNumber, "Operating reference Flight Number", screenName);

	}
	

/*
 * Click ULD Tag
 */
/**
 * Description... Click ULD Tag
 * @throws InterruptedException
 * @throws IOException 
 */
public void clickULDTag() throws InterruptedException, IOException {
	clickWebElement(sheetName, "btn_ULDTag;name", "ULD Tag Button", screenName);		
} 
/**
 * Description... Click ULD Loading Instuctor
 * @throws InterruptedException
 * @throws IOException 
 */
public void clickULDLoadingInstuctor() throws InterruptedException, IOException{
	clickWebElement(sheetName, "btn_ULDLoadingInstructor;name", "ULDLoading Instructor", screenName);
	waitForSync(2);
}
/**A-10690
 * Verifies linked ULD in deadload statement screen
 * @param ULD
 * @throws Exception
 */
public void verifyLinkedULD(String ULD) throws Exception {
	
	
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(4);

	String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","dropdpwn_linkeduld;xpath");
	String actualULD = driver.findElement(By.xpath(locator)).getText();
	if(actualULD.equals(data(ULD))){
		onPassUpdate(screenName,actualULD ,ULD ,
				"Content ID verification",
				"1.List Flight 2. Check content ID");
	}
	else{
		onFailUpdate(screenName,actualULD ,ULD ,
				"Content ID verification",
				"1.List Flight 2. Check content ID");
	}


}	


/**
 * Description... Verify ULD PopUp
 * @param flightNum
 * @param ULDNo
 * @throws Exception
 */
public void verifyULDPopUp(String flightNum, String ULDNo) throws Exception{
	
	
	waitForSync(3);
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(4);
	
	String FlightNo=getAttributeWebElement(sheetName, "inbx_FlightNumber;name", "Flight Number",
			"value", screenName);
    verifyScreenText(sheetName, data(flightNum), FlightNo,  " Flight Number ", screenName);

	String ULDNum=getAttributeWebElement(sheetName, "inbx_ULDNo;name", "ULD Number",
			"value", screenName);
    verifyScreenText(sheetName, data(ULDNo), ULDNum,  " ULD Number ", screenName);
	
	waitForSync(2);
}
/**A-10328
 * Verify floating pallet as yes in deadload statement screen
* @param expectedFloatingValue
* @throws Exception
*/
public void verifyFloatingPallet(String expectedFloatingValue) throws Exception {


switchToWindow("storeParent");
waitForSync(2);
switchToWindow("child");

waitForSync(4);

String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","dropdown_floatingpallet;xpath");
String actualFloatingValue = driver.findElement(By.xpath(locator)).getText();
verifyScreenTextWithExactMatch(screenName, expectedFloatingValue,actualFloatingValue, "Verification of floating pallet as yes ","Verification of floating pallet as yes ");
}

/**@author A-10328
 * Description - verifies no .of positions
 * @param expectednoofpositions
 */

public void verifynoofpositions(int expectednoofpositions)


{
	

try
	
{

String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","txt_noofpositions;xpath");
String actualvalue = driver.findElement(By.xpath(locator)).getAttribute("value");
int actualnoofpositions = Integer.parseInt(actualvalue);
if(actualnoofpositions==expectednoofpositions)
{
writeExtent("Pass", "verified no.of positions as "+actualnoofpositions+screenName);
}
else
{
writeExtent("Fail", "verified no.of positions as "+actualnoofpositions+screenName);
}
}
catch(Exception e)
{

}
}
/**
 * Description... Verify Add n Uld Details
 * @param actWt
 * @param occupancy
 * @param builtBy
 * @throws Exception
 */
public void verifyAddnUldDetails(String actWt, String occupancy,String builtBy ) throws Exception{
	
	
	
	//String act wt
	String actWtValue=getAttributeWebElement(sheetName, "inbx_uldActWt;name", "actWt",
			"value", screenName);
	
    verifyScreenText(sheetName, data(actWt), actWtValue,  " actWt ", screenName);

  //String occupancy
    
   
	String occupancyValue=getAttributeWebElement(sheetName, "inbx_uldOccupancy;name", "occupancy",
			"value", screenName);
    verifyScreenText(sheetName, data(occupancy), occupancyValue,  " occupancy ", screenName);
   
    
   
  //String builtBy
  	String builtByValue=getAttributeWebElement(sheetName, "inbx_ULDbuiltUpBy;name", "ULD Number",
  			"value", screenName);
      verifyScreenText(sheetName, data(builtBy), builtByValue,  " builtBy ", screenName);
	
	waitForSync(2);
}
/**
 * Description... Close ULD Loading Instuctor
 * @throws Exception
 */
public void closeULDLoadingInstuctor() throws Exception{
	clickWebElement(sheetName, "btn_ULDClose;name", "ULDLoading Instructor", screenName);
	waitForSync(2);
	switchToWindow("getParent");
	waitForSync(2);
}
/**A-10690
 * Verifies linkage reason
 * @param expected linkage reason
 * @throws Exception
 */
public void verifyLinkageReason(String reason) throws Exception {
	
	
	

	waitForSync(2);
	String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","txt_linkagereason;name");

	String actualreason = driver.findElement(By.name(locator)).getAttribute("value");
	if(actualreason.equals(data(reason))){
		onPassUpdate(screenName,actualreason ,reason ,
				"Content ID verification",
				"1.List Flight 2. Check content ID");
	}
	else{
		onFailUpdate(screenName,actualreason ,reason ,
				"Content ID verification",
				"1.List Flight 2. Check content ID");
	}


}	
/**
 * Description... Verify Add n Bulk Details
 * @param actWt
 * @param builtBy
 * @throws Exception
 */
public void verifyAddnBulkDetails(String actWt,String builtBy ) throws Exception{
	
	
	
	//String act wt
	String actWtValue=getAttributeWebElement(sheetName, "inbx_bulkActWt;name", "actWt",
			"value", screenName);
	
    verifyScreenText(sheetName, data(actWt), actWtValue,  " actWt ", screenName);

  
   
  //String builtBy
  	String builtByValue=getAttributeWebElement(sheetName, "inbx_BulkbuiltUpBy;name", "Built by",
  			"value", screenName);
      verifyScreenText(sheetName, data(builtBy), builtByValue,  " builtBy ", screenName);
	
	waitForSync(2);
}
/**
 * Description... Select Accessories Row
 * @param Accessory
 */
public void selectAccessoriesRow(String Accessory){
 //(//tbody[@id='uldLoadingDetailsTableBody']//input[@value='ACE']/ancestor::tr//input)[1]
 
 try{
  String row = xls_Read.getCellValue(sheetName, "tbl_AccessoriesDetail;xpath");
  String label = "(" + row + "//input[@value='" + Accessory + "']/ancestor::tr//input)[1]";
  driver.findElement(By.xpath(label)).click();
  
  }catch(Exception e){
   
   test.log(LogStatus.FAIL, "Failed to select " + Accessory);
   System.out.println("Failed to select " + Accessory);
   Assert.assertFalse(true, "Element is not found");
  }
 
}



/**Select additinal loading details
 * A-8705
 * @param additionalULDIns
 */
    public void select_AdditionalLoadingDetails(String additionalULDIns) {
        WebElement ele = driver.findElement(By.xpath("//*[contains(text(),'"
                + additionalULDIns + "')]/..//input"));
        ele.click();
        waitForSync(5);
    }
    
    
/**
 * Description... Enter Accessories Remarks
 * @param Accessory
 * @param Remarks
 * @throws InterruptedException
 */
public void enterAccessoriesRemarks(String Accessory, String Remarks) throws InterruptedException{
 //tbody[@id='uldLoadingDetailsTableBody']//input[@value='ACE']/ancestor::tr//input[@name='accdtlremarks']
  
  String row = xls_Read.getCellValue(sheetName, "tbl_AccessoriesDetail;xpath");
  String accesory = row + "//input[@value='" + Accessory + "']/ancestor::tr//input[@name='accdtlremarks']";
  enterValueInTextbox(accesory, Remarks, "Accessory Details Remarks", screenName);
  
  }
/**
 * Description... Enter builtUp By and Occupancy
 * @param builtUpBy
 * @param occupancy
 * @throws Exception
 */
public void enterBuilupandOccupancy(String builtUpBy, String occupancy) throws Exception {
    
 enterValueInTextbox(sheetName, "inbx_builtUpBy;name", builtUpBy, "Built Up By", screenName);
 enterValueInTextbox(sheetName, "inbx_occupancy;name", occupancy, "Occupancy", screenName);  
 
}
/**
 * Description... ULD Loading Instruction OK
 * @throws Exception
 */
public void ULDLoadingInstructionOK() throws Exception { 
 clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
waitForSync(5);
switchToFrame("default");
switchToFrame("contentFrame","OPR063");

}
/**
 * Description... Verify ULD Details
 * @param pmyKey
 * @param tbltag
 * @param verfCols
 * @param actVerfValues
 * @throws InterruptedException
 */
public void verifyULDDetails(String pmyKey, String tbltag, int verfCols[], String actVerfValues[]) throws InterruptedException{
 
 verify_tbl_records_multiple_cols_contains(sheetName, "tbl_ULDdetails;xpath", tbltag,
   verfCols, pmyKey, actVerfValues);

}



public void verifyOperatingReference(String operatingRefCarrier,String operatingRef) throws InterruptedException {
        System.out.println();
    String carrierCode=getAttributeWebElement(sheetName, "inbx_opRefCarrierCode;xpath",
                 "Operating Reference Carrier Code","value",
                screenName);
    String flight=getAttributeWebElement(sheetName, "inbx_opRefFlightNumber;xpath",
                "Operating Reference Flight Number","value",
                screenName);
    if(carrierCode.equals(data(operatingRefCarrier)) && flight.equals(data(operatingRef))){
        onPassUpdate(screenName, carrierCode+flight, operatingRefCarrier+operatingRef,
                "operating reference verification",
                "1.check operating reference");
    }
    else{
        onFailUpdate(screenName, carrierCode+flight, operatingRefCarrier+operatingRef,
                "operating reference verification",
                "1.check operating reference");
    }
}




/**
 * Description... Verify ULD Details
 * @param verfCols
 * @param actVerfValues
 * @param pmKey
 * @throws IOException 
 */
public void verifyULDDetails(int verfCols[],String actVerfValues[],String pmKey) throws IOException
{
	verify_tbl_records_multiple_cols(sheetName, "tbl_ULDdetails;xpath", "//td", verfCols, pmKey, actVerfValues);
}
/**
 * Description... Verify BULK Details
 * @param verfCols
 * @param actVerfValues
 * @param pmKey
 * @throws IOException 
 */
public void verifyBULKDetails(int verfCols[],String actVerfValues[],String pmKey) throws IOException
{
	verify_tbl_records_multiple_cols(sheetName, "tbl_Bulkdetails;xpath", "//td", verfCols, pmKey, actVerfValues);
}
/**
 * Description... Enter Remarks
 * @param Remarks
 * @throws Exception
 */
public void enterRemarks(String Remarks) throws Exception {
    
 /*String length = getAttributeUsingJavascript(sheetName, "inbx_Remarks;name",
   "Remarks", screenName, "maxlength");*/
 
 String length = getAttributeWebElement(sheetName, "inbx_Remarks;name",
"Remarks", screenName, "maxlength");
System.out.println("Maximum length for remarks field on " + screenName + " Page is " +length);
writeExtent("Info", "Maximum length for remarks field on " + screenName + " Page is " +length);
enterValueInTextbox(sheetName, "inbx_Remarks;name", Remarks, "Remarks", screenName);

 
 
}
/**
 * Desc : Verifying ULD Loading Instructor ContourDetails
 * @author A-9175
 * @param flightNo
 * @param uldNum
 * @param contour
 * @throws Exception
 */
public void verifyULDLoadingInstuctorWithContourDetails(String flightNo, String uldNum,String contour) throws Exception{
	
	
	waitForSync(3);
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(4);
	
	String actContour=getAttributeWebElement(sheetName, "inbx_contour;name", " Contour ",
			"value", screenName);
    verifyScreenText(sheetName, data(contour), actContour,  " Contour ", screenName);

	
	String FlightNo=getAttributeWebElement(sheetName, "inbx_FlightNumber;name", "Flight Number",
			"value", screenName);
    verifyScreenText(sheetName, data(flightNo), FlightNo,  " Flight Number ", screenName);

    
	String ULDNum=getAttributeWebElement(sheetName, "inbx_ULDNo;name", "ULD Number",
			"value", screenName);
    verifyScreenText(sheetName, data(uldNum), ULDNum,  " ULD Number ", screenName);
 
    
   
	
}

/**
 * Description... Clear Remarks
 * @throws Exception
 */
public void clearRemarks() throws Exception {
    
 clearText(sheetName, "inbx_Remarks;name",  "Remarks", screenName);
  
}
public void verifyScaleDateAndNumber(String scaleDate, String scaleNumber) throws InterruptedException, IOException {
      String actualScaleDate = getElementText(sheetName, "inbx_scaleDate;xpath", "Scale date","Deadload statement");
      verifyScreenText(sheetName, data(scaleDate), actualScaleDate,  "Scale Date", screenName);
      clickWebElement(sheetName, "btn_Scaleinfo;id", "scale info btn", screenName);
      waitForSync(4);
      String temp =getElementText(sheetName, "inbx_scaleNumber;xpath", "Scale Number","Deadload statement");
    String[] a =temp.split(":");
    String actualScaleNum = a[1].trim();
    System.out.println(actualScaleNum);
    verifyScreenText(sheetName, data(scaleNumber), actualScaleNum,  "Scale Number", screenName);

}
/**
 * @Desc : verify Flight Icon Is Square
 * @author A-9175
 * @throws Exception
 */
public void verifyFlightIconIsSquare() throws Exception{
	
	
	switchToWindow("storeParent");
	waitForSync(2);
	switchToWindow("child");
	waitForSync(2);
	
	String width=getAttributeWebElement(sheetName, "img_flightRepImg;xpath", "Width","width", screenName);
	String height=getAttributeWebElement(sheetName, "img_flightRepImg;xpath", "Height","height", screenName);
   
	if(width.equals(height))
		writeExtent("Pass", "Successfully Verified Flight representation image is in Squre size with Width as :"+width+" And Height as "+height+ screenName + " Page");
	else
		writeExtent("Fail", "Could not Verify Flight representation image is in Squre size Since Width is :"+width+" And Height is "+height+ screenName + " Page");

}

/**
	 * Description...ULD Remarks Verification
	 * @throws Exception
	 */
	public void verifyFlightDetailsNonEditable() throws Exception {

		//Flight number verification
		String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_flightNumber;xpath");
		WebElement ele = driver.findElement(By.xpath(locator));
		verifyElementDisabled(ele, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify Flight details are Non-Editable/n" ,screenName, "Flight Number");

		//Flight Date verification
		String locator1 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_flightDate;xpath");
		WebElement ele1 = driver.findElement(By.xpath(locator1));
		verifyElementDisabled(ele1, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify Flight details are Non-Editable/n" ,screenName, "Flight Date");	


		//Flight ULD number verification
		String locator2 = xls_Read.getCellValue("DeadloadStatement_OPR063","txt_ULDNumber;xpath");
		WebElement ele2 = driver.findElement(By.xpath(locator2));
		verifyElementDisabled(ele2, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "ULD number");   	


		//Flight POU verification
		String locator3 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_POU;xpath");
		WebElement ele3 = driver.findElement(By.xpath(locator3));
		verifyElementDisabled(ele3, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "POU ");

		//Flight DST verification
		String locator4 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_DST;xpath");
		WebElement ele4 = driver.findElement(By.xpath(locator4));
		verifyElementDisabled(ele4, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "DST ");
		
		
	}
	
	public void verifyULDDetailsEditable() throws Exception {

		//Actual Weight verification
		String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_actualWeight;xpath");
		WebElement ele = driver.findElement(By.xpath(locator));
		verifyElementDisabled(ele, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify Flight details are Non-Editable/n" ,screenName, "Actual Weight");

		//Actual Height verification
		String locator1 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_height;xpath");
		WebElement ele1 = driver.findElement(By.xpath(locator1));
		verifyElementDisabled(ele1, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify Flight details are Non-Editable/n" ,screenName, "Actual Height");	


		//Volume verification
		String locator2 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_Volume;xpath");
		WebElement ele2 = driver.findElement(By.xpath(locator2));
		verifyElementDisabled(ele2, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "Volume");   	


		//BuildUp by  verification
		String locator3 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_buildUpBy;xpath");
		WebElement ele3 = driver.findElement(By.xpath(locator3));
		verifyElementDisabled(ele3, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "BuildUp by ");

		//Occupancy verification
		String locator4 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_Occupancy;xpath");
		WebElement ele4 = driver.findElement(By.xpath(locator4));
		verifyElementDisabled(ele4, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "Occupancy ");

		//Drop down Content ID verification
		String locator5 = xls_Read.getCellValue("DeadloadStatement_OPR063","dropDown_ContentID;xpath");
		WebElement ele5 = driver.findElement(By.xpath(locator5));
		verifyElementDisabled(ele5, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "Drop down Content ID ");

		//ULD Priority verification
		String locator6 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_ULDPriority;xpath");
		WebElement ele6 = driver.findElement(By.xpath(locator6));
		verifyElementDisabled(ele6, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "ULD Priority");

		//Rigidity verification
		String locator7 = xls_Read.getCellValue("DeadloadStatement_OPR063","dropDown_rigidity;xpath");
		WebElement ele7 = driver.findElement(By.xpath(locator7));
		verifyElementDisabled(ele7, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "Rigidity");  

	}
	
	public void verifyULDDetailsNonEditable() throws Exception {

		//Tare Weight verification
		String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_tareWeight;xpath");
		WebElement ele = driver.findElement(By.xpath(locator));
		verifyElementDisabled(ele, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify Flight details are Non-Editable/n" ,screenName, "Tare Weight");

		//Net Weight verification
		String locator1 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_netWeight;xpath");
		WebElement ele1 = driver.findElement(By.xpath(locator1));
		verifyElementDisabled(ele1, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify Flight details are Non-Editable/n" ,screenName, "Net Weight");	


		//Gross Weight verification
		String locator2 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_grossWeight;xpath");
		WebElement ele2 = driver.findElement(By.xpath(locator2));
		verifyElementDisabled(ele2, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "Gross Weight");   	


		//Contour  verification
		String locator3 = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_contour;xpath");
		WebElement ele3 = driver.findElement(By.xpath(locator3));
		verifyElementDisabled(ele3, sheetName, "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify  Flight details are Non-Editable/n" ,screenName, "Contour");


	}
	
	/**
	 * Description... Verification of the ULD Priority values 
	 * @throws Exception
	 */
	public void verifyULDPriorityAcceptNumericValues() throws InterruptedException {

		try {
		
			//ULD Priority field verification
			String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","inbx_ULDPriority;xpath");
			WebElement ele6 = driver.findElement(By.xpath(locator));
			driver.findElement(By.xpath(locator)).sendKeys("1234");
			String textValue = ele6.getAttribute("value");			
			
			System.out.println("The Value is "+textValue);

			if(textValue.length()==3) {
				customFunction.onPassUpdate(screenName, "ULD Priority field should accept only 3 numeric values" , "ULD Priority field is accepting only 3 numeric values" 
						,"ULD Priority field", "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify ULD Priority field is accepting only 3 numeric values/n");
					

			}
			else {
				customFunction.onFailUpdate(screenName, "ULD Priority field should accept only 3 numeric values" , "ULD Priority field is not accepting only 3 numeric values" 
						,"ULD Priority field", "1.Login iCargo Invoke OPR063 and list the flight/n," +
				" 2.select the ULD and click on ULDinstruction/n , 3.Verify ULD Priority field is accepting only 3 numeric values/n");

			}

		}
								
		catch(Exception e) {
			e.printStackTrace();

		}
	}
	
	/**
	 * Description... Enter Accessories Weight
	 * @param Accessory
	 * @param Remarks
	 * @throws InterruptedException
	 */
	public void enterAccessoriesWeight(String Accessory, String Weight) throws InterruptedException{
		//tbody[@id='uldLoadingDetailsTableBody']//input[@value='ACE']/ancestor::tr//input[@name='accdtlremarks']

		String row = xls_Read.getCellValue(sheetName, "tbl_AccessoriesDetail;xpath");
		String accesory = row + "//input[@value='" + Accessory + "']/ancestor::tr//input[@name='accdtlwgt']";
		enterValueInTextbox(accesory, Weight, "Accessory Details Weight", screenName);

	}
	
	/**
	 * Description...ULD Remarks Verification
	 * @throws Exception
	 */
	public void verifyULDRemarks(String expRemarks) throws Exception {

		String locator = xls_Read.getCellValue("DeadloadStatement_OPR063","txt_ULDRemarks;xpath");
		ele = driver.findElement(By.xpath(locator));

		String actText = getElementText(ele, "ULD Remarks", "OPR063");

		System.out.println("Actual text is--" + actText);
		String expText = data(expRemarks);
		verifyScreenText(sheetName, actText, expText,  actText, screenName);


	}
	/**
	 * Desc : Verifying ULD Loading Instructor Pop UP
	 * @author A-9175
	 * @param flightNo
	 * @param uldNum
	 * @param contour
	 * @param front
	 * @param rear
	 * @param left
	 * @param right
	 * @throws Exception
	 */
	public void verifyULDLoadingInstuctorPopUp(String flightNo, String uldNum,String contour,String front,String rear,String left,String right) throws Exception{
		
		waitForSync(3);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
		
		String actContour=getAttributeWebElement(sheetName, "inbx_contour;name", " Contour ",
				"value", screenName);
	    verifyScreenText(sheetName, data(contour), actContour,  " Contour ", screenName);
		
		
		String FlightNo=getAttributeWebElement(sheetName, "inbx_FlightNumber;name", "Flight Number",
				"value", screenName);
	    verifyScreenText(sheetName, data(flightNo), FlightNo,  " Flight Number ", screenName);
	   
	    
		String ULDNum=getAttributeWebElement(sheetName, "inbx_ULDNo;name", "ULD Number",
				"value", screenName);
	    verifyScreenText(sheetName, data(uldNum), ULDNum,  " ULD Number ", screenName);
		
	    String actFront=getAttributeWebElement(sheetName, "inbx_forwardSide;xpath", "Front",
				"value", screenName);
	    verifyScreenText(sheetName, data(front), actFront,  " Front ", screenName);
	   
	    
	    String actRear=getAttributeWebElement(sheetName, "inbx_backwardSide;xpath", "Rear",
				"value", screenName);
	    verifyScreenText(sheetName, data(rear), actRear,  " Rear ", screenName);
	
		
		String actLeft=getAttributeWebElement(sheetName, "inbx_leftSide;xpath", "Left",
				"value", screenName);
	    verifyScreenText(sheetName, data(left), actLeft,  " Left ", screenName);
		
		
		String actRight=getAttributeWebElement(sheetName, "inbx_rightSide;xpath", "Right",
				"value", screenName);
	    verifyScreenText(sheetName, data(right), actRight,  " Right ", screenName);
		
		
	}
	
	/**
	 * @author A-6260
	 * Desc..Capture Pieces up details
	 * @param front
	 * @param rear
	 * @param left
	 * @param right
	 * @throws Exception
	 */
	public void capturePiecesUpDetails(String front,String rear,String left,String right) throws Exception {
		waitForSync(3);
		switchToWindow("storeParent");
		waitForSync(2);
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_fwdPieceUp;name", data(front), "Pieces up Front", screenName);
		enterValueInTextbox(sheetName, "inbx_leftPieceUp;name", data(left), "Pieces up Left", screenName);
		enterValueInTextbox(sheetName, "inbx_rightPieceUp;name", data(right), "Pieces up Right", screenName);
		enterValueInTextbox(sheetName, "inbx_aftPieceUp;name", data(rear), "Pieces up Rear", screenName);
		waitForSync(2);
			
	}


	/**
	 * @author A-6260
	 * Desc: To verify the pieces up details
	 * @param front
	 * @param rear
	 * @param left
	 * @param right
	 * @throws Exception
	 */
	public void verifyPiecesUpDetails(String front,String rear,String left,String right) throws Exception{
			
			
			waitForSync(3);
			switchToWindow("storeParent");
			waitForSync(2);
			switchToWindow("child");
			waitForSync(2);
		    
		    String actFront=getAttributeWebElement(sheetName, "inbx_fwdPieceUp;name", "Pieces Up Front",
					"value", screenName);
		    verifyScreenText(sheetName, data(front), actFront,  "Pieces Up Front ", screenName);
		   
		    
		    String actRear=getAttributeWebElement(sheetName, "inbx_aftPieceUp;name", "Pieces up Rear",
					"value", screenName);
		    verifyScreenText(sheetName, data(rear), actRear,  "Pieces up Rear ", screenName);
		
			
			String actLeft=getAttributeWebElement(sheetName, "inbx_leftPieceUp;name", "Pieces up Left",
					"value", screenName);
		    verifyScreenText(sheetName, data(left), actLeft,  "Pieces up Left ", screenName);
			
			
			String actRight=getAttributeWebElement(sheetName, "inbx_rightPieceUp;name", "Pieces up Right",
					"value", screenName);
		    verifyScreenText(sheetName, data(right), actRight,  "Pieces up Right ", screenName);
			
			
		}

	/**
	 * Description... Check the Damaged Check Box
	 * @throws IOException 
	 * @throws Exception
	 */
	public void selectDamagedCheckBox() throws InterruptedException, IOException {
		
		clickWebElement(sheetName, "chkBx_Damaged;xpath","Damaged CheckBox", screenName);	
	}
	
	
	
	/**A-8705
     * Verifies content ID
     * @param expectedValues
     * @param pmKey
     */
   
    public void verifyContendID(String expectedValues, String pmKey) {
    WebElement ele = driver.findElement(By.xpath("//*[contains(text(),'"+pmKey+"')]/..//select[@name='uldContentId']"));
    Select select = new Select(ele);
    String actual=select.getFirstSelectedOption().getText();
    if(actual.contains(expectedValues)){
        onPassUpdate(screenName,actual ,expectedValues ,
                "Content ID verification",
                "1.List Flight 2. Check content ID");
    }
    else{
        onFailUpdate(screenName,actual ,expectedValues ,
                "Content ID verification",
                "1.List Flight 2. Check content ID");
    }
   
}
    
  
    
    /***A-8705
     * Verifies ULD remarks
     * @param expRemarks
     * @param pmKey
     */
public void verifyULDRemarks(String expRemarks,String pmKey) {
        WebElement ele = driver.findElement(By.xpath("//*[contains(text(),'"+pmKey+"')]/..//div[@name='uldRMK']"));
        String actual=ele.getAttribute("title");
        String[] act = actual.split("[/.]");
        String[] exp = expRemarks.split("[/.]");
        Arrays.sort(act);
        Arrays.sort(exp);
        if(Arrays.equals(act,exp)==true){
            onPassUpdate(screenName,actual ,expRemarks ,
                    "uld Remarks verification",
                    "1.List Flight 2. Check ULD Remarks");   
        }
        else{
            onFailUpdate(screenName,actual ,expRemarks ,
                    "uld Remarks verification",
                    "1.List Flight 2. Check ULD Remarks");   
        }
    }


	
	/**
	 * Description... Enter tare weight in actual weight
	 * @throws Exception
	 */
	public void enterActualWeight() throws InterruptedException {
		
		String dynXpath = xls_Read.getCellValue(sheetName, "tbl_ULDdetails1;xpath");
		
		String dynXpath1 = dynXpath + "[7]";
		ele = driver.findElement(By.xpath(dynXpath1));
		String tareWeight = getAttributeWebElement(ele, "Tare weight", "textContent", screenName);
		
		String dynXpath2 = dynXpath + "[10]//input";
		//ele = driver.findElement(By.xpath(dynXpath1));
		enterValueInTextbox(dynXpath2, tareWeight, "actual Weight", screenName);
		
		
}
	
	

}
