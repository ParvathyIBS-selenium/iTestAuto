package screens;
import com.relevantcodes.extentreports.LogStatus;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ULDTag_OPR013 extends CustomFunctions {
	String sheetName = "ULDTag_OPR013";
	String screenName = "ULD Tag";

	public ULDTag_OPR013(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
/**
 * Description... List Uld Tag Details
 * @param carrierCode
 * @param fltNo
 * @param fltDate
 * @param uldNo
 * @throws InterruptedException
 * @throws IOException 
 */
public void  listUldTagDetails(String carrierCode,String fltNo,String fltDate,String uldNo) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_carrierCode;xpath", data(carrierCode), "Carrier Code", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNo;xpath", data(fltNo), "Flight Number", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;xpath", data(fltDate), "Flight Date", screenName);
		enterValueInTextbox(sheetName, "inbx_uldNo;xpath", data(uldNo), "Uld number", screenName);
		clickWebElement(sheetName, "btn_list;xpath","List Button", screenName);	
		waitForSync(10);
				
	}




/**
 * Description... Enter Details in ULD loading instruction window
 * @param carrierCode
 * @param fltNo
 * @param fltDate
 * @param uldNo
 * @throws InterruptedException
 */
public void  enterDetails(String locator,String value,String fieldName) throws InterruptedException
	{
		enterValueInTextbox(sheetName, locator, value, fieldName, screenName);
		waitForSync(2);
				
	}



public void verifyContentID(String contentID) {
       
 String actualID = getAttributeWebElement(sheetName,
                "inbx_contentID;xpath", "Content ID", "value", screenName);
        verifyScreenTextWithExactMatch(sheetName, contentID, actualID,
                "Verification of contentID Wt in ULD Tag screen ", screenName);
    }

/**
 * Description... Enter Details in ULD loading instruction window
 * @param carrierCode
 * @param fltNo
 * @param fltDate
 * @param uldNo
 * @throws InterruptedException
 */
public void verifyInputboxAttributes(String locator,String fieldName, int expSize) throws InterruptedException
	{
		
		String txtULDPriority = getAttributeWebElement(sheetName, locator, fieldName, "value", screenName);
		int size = txtULDPriority.length();

		if(size == expSize){
			onPassUpdate(screenName, "inputbox length should be " + expSize, "txt length is " + size, "Verification of allowed input ", "Verification of allowed text in given input field");
		}
		else
			onFailUpdate(screenName, "inputbox length should be " + expSize, "txt length is " + size, "Verification of allowed input ", "Verification of allowed text in given input field");
	
				
	}


	/**
	 * Description...  ULD Tag Print With Weighing Mode
	 * @author A-7271
	 * @param builtUpBy
	 * @param occupancy
	 * @param weighingMode
	 * @throws Exception
	 */
	public void ULDTagPrintWithWeighingMode(String builtUpBy, String occupancy,String weighingMode) throws Exception {
        switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_ULDLoadingInstr;xpath",
				"ULD Loading Instrction Button", screenName);		
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_builtUpBy;xpath", builtUpBy, "Built Up By", screenName);
		enterValueInTextbox(sheetName, "inbx_occupancy;xpath", occupancy, "Occupancy", screenName);	
	
				
		enterValueInTextbox(sheetName, "inbx_WeighingMode;name", data(weighingMode), "weighingMode", screenName);	
		
	}
	/**
	 * Description... Verify Weighing Mode
	 * @author A-7271
	 * @param expValue
	 * @param attribute
	 */
	public void verifyWeighingMode(String expValue,String attribute)
	{
		String actualResult=getAttributeWebElement(sheetName, "inbx_WeighingMode;name",
				"WeighingMode", attribute, screenName);
		System.out.println(actualResult);
		System.out.println(expValue);
		verifyScreenText(sheetName, data(expValue),actualResult,  "verification of WeighingMode", screenName); 
	}
	/**
	 * Description... Click Close Button ULD Tag
	 * @throws Exception
	 */
	public void clickCloseButtonULDTag() throws Exception
	{
		clickButtonSwitchtoParentWindow(sheetName, "btn_close;name", "Close Button", screenName);
	}
	
/*A-8705
* Verifies Special Lane
*/
	/**
	 * Description... Verify Speacial Lane
	 * @param SpecialLane
	 * @throws Exception
	 */
public void verifySpeacialLane(String SpecialLane) throws Exception {
String actualText=getAttributeWebElement(sheetName, "inbx_transportLane;name","Special Lane", "value", screenName);
verifyScreenTextWithExactMatch(sheetName, data(SpecialLane), actualText,  "Verification of Transport Lane in dead load statement screen", screenName); 
clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);      
}


/*A-8705
* Enters Special Lane
*/
/**
 * Description... Enter Special Lane
 * @param splLane
 * @throws Exception
 */
public void enterSpecialLane(String splLane) throws Exception {
      enterValueInTextbox(sheetName, "inbx_transportLane;name", data(splLane), "transportLane", screenName); 
      clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
}

	/**
	 * Description... Click Ok Button ULD Tag
	 * @throws Exception
	 */
	public void clickOkButtonULDTag() throws Exception
	{
		clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
	}
	/**
	 * Description... Verify Spl Lane
	 * @author A-7271
	 * @param expValue
	 * @param attribute
	 */
	public void verifySplLane(String expValue,String attribute)
	{
		String actualResult=getAttributeWebElement(sheetName, "inbx_transportLane;name",
				"transportLane", attribute, screenName);
		System.out.println(actualResult);
		System.out.println(expValue);
		verifyScreenText(sheetName, data(expValue),actualResult,  "verification of spl lane", screenName); 
	}
	/*Description... Clicks on Save button in OPR013 screen if OPR013 Screen is opened from opr063 screen
* @author A-8705
* 
*/
/**
 * Description... Save Opened From Deadload
 * @throws InterruptedException
 * @throws IOException 
 */
      public void saveOpenedFromDeadload() throws InterruptedException, IOException {
            switchToFrame("default");
            switchToFrame("contentFrame", "OPR063");
            waitForSync(3);
            clickWebElement(sheetName, "btn_save;xpath", "Save Button", screenName);
            waitForSync(3);

      }
	  /*A-8705
* Verifies error message if dolly weight is greater than cart weight
*/
      /**
       * Description... Verification Of Err Grt Than Cart
       * @param dollyWt
       * @param expectedErrorMsg
       * @param weighingMode
       * @param builtUpBy
       * @param occupancy
       * @throws Exception
       */
      public void verificationOfErrGrtThanCart(String dollyWt,String expectedErrorMsg,String weighingMode,String builtUpBy,String occupancy ) throws Exception {
            enterValueInTextbox(sheetName, "inbx_dollyWt;name", data(dollyWt),
                        "dollyWt", screenName);
      verificationOfWeighingMode(weighingMode, builtUpBy, occupancy);
      saveOpenedFromDeadload();
      verifyErrorMessage(screenName,expectedErrorMsg);
      
      }

/**
 * Description... Verify Transport Lane And Special Lane
	 * @author A-7271
	 * @param transportLane
	 * @param weighingMode
	 * @param builtUpBy
	 * @throws Exception
	 */
	public void verifyTransportLaneAndSpecialLane(String transportLane,String weighingMode,String builtUpBy) throws Exception
	{
		//Transport Lane
		String transportLan=getAttributeWebElement(sheetName, "inbx_transportLane;name","transport Lane", "value", screenName);
		
		verifyScreenTextWithExactMatch(sheetName, data(transportLane), transportLan,  "Verification of Transport Lane in dead load statement screen", screenName); 
		
       //Weighing mode
		String weighingMod=getAttributeWebElement(sheetName, "inbx_WeighingMode;name","Weighing Mode", "value", screenName);
		
		verifyScreenTextWithExactMatch(sheetName, data(weighingMode), weighingMod,  "Verification of Weighing mode in dead load statement screen", screenName); 
		
		enterValueInTextbox(sheetName, "inbx_builtUpBy;xpath", builtUpBy, "Built Up By", screenName);	
		
		clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
		
	}
/*Description... Verifies Weighing Mode in OPR013 Screen
* @author A-8705
* 
*/
/**
 * Description... Verification Of Weighing Mode
 * @param weighingMode
 * @param builtUpBy
 * @param occupancy
 * @throws Exception
 */
      public void verificationOfWeighingMode(String weighingMode,
                  String builtUpBy, String occupancy) throws Exception {
            switchToWindow("storeParent");
            clickWebElement(sheetName, "btn_ULDLoadingInstr;xpath",
                        "ULD Loading Instrction Button", screenName);
            switchToWindow("child");
            waitForSync(2);
            enterValueInTextbox(sheetName, "inbx_builtUpBy;xpath", builtUpBy,
                        "Built Up By", screenName);
            enterValueInTextbox(sheetName, "inbx_occupancy;xpath", occupancy,
                        "Occupancy", screenName);
            String dollyWtVal = getAttributeWebElement(sheetName,
                        "inbx_WeighingMode;name", "dollyWt", "value", screenName);
            verifyScreenTextWithExactMatch(sheetName, data(weighingMode),
                        dollyWtVal, "Verification of weighing mode", screenName);
            clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath",
                        "OK Button", screenName);

      }
/*Description... Verifies Dolly Weight in OPR013 Screen
* @author A-8705
* 
*/

/**
 * Description... Verify Dolly Weight
 * @param dollyWt
 */

      public void verifyDollyWeight(String dollyWt) {
            String dollyWtVal = getAttributeWebElement(sheetName,
                        "inbx_dollyWt;name", "dollyWt", "value", screenName);
            verifyScreenTextWithExactMatch(sheetName, data(dollyWt), dollyWtVal,
                        "Verification of dolly Wt in ULD Tag screen ", screenName);
      }
	  /**
	   * Description... Save without switching
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException 
	 */
      
      
public void savewithoutswitching() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_save;xpath","Save Button", screenName);
		waitForSync(3);
	}
	/*** VERIFICATION OF ULD DETAILS IN ULD PRINT TAG SCREEN***/
/**
 * Description... Verify Uld Details
 * @param tareWt
 * @param netWt
 * @param grossWt
 * @param dollyWt
 * @param actualWt
 * @param occupancy
 * @param pou
 * @param contents
 * @throws InterruptedException
 */
public void verifyUldDetails (String tareWt,String netWt,String grossWt,String dollyWt,String actualWt,String occupancy,String pou,String contents) throws InterruptedException
{
	
	
	
	
	//Verifying tareWT
	String tareWtVal=getAttributeWebElement(sheetName, "inbx_tareWt;name",
			"tareWt", "value", screenName);
	 verifyScreenTextWithExactMatch(sheetName, data(tareWt), tareWtVal,  "Verification of uld tare wt in ULD Tag screen ", screenName); 
	
	//Verifying netWt
		String netWtVal=getAttributeWebElement(sheetName, "inbx_netWt;name",
				"netWt", "value", screenName);
		
		verifyScreenTextWithExactMatch(sheetName, data(netWt), netWtVal,  "Verification of uld net wt in ULD Tag screen ", screenName);
		
		
		//Verifying grossWt
		String grossWtVal=getAttributeWebElement(sheetName, "inbx_grossWt;name",
				"grossWt", "value", screenName);

		
		verifyScreenTextWithExactMatch(sheetName, data(grossWt), grossWtVal,  "Verification of gross Wt in ULD Tag screen ", screenName);
		
		//Verifying dollyWt
		String dollyWtVal=getAttributeWebElement(sheetName, "inbx_dollyWt;name",
				"dollyWt", "value", screenName);

		
		verifyScreenTextWithExactMatch(sheetName, data(dollyWt), dollyWtVal,  "Verification of dolly Wt in ULD Tag screen ", screenName);
		
		//Verifying actWt
		String actualWtVal=getAttributeWebElement(sheetName, "inbx_actWt;name",
				"actualWt", "value", screenName);

		
		verifyScreenTextWithExactMatch(sheetName, data(actualWt), actualWtVal,  "Verification of act ULD Wt in ULD Tag screen ", screenName);
		
		//Verifying occupancy
		String occupancyVal=getAttributeWebElement(sheetName, "inbx_uldOccupancy;name",
				"occupancy", "value", screenName);

		
		verifyScreenTextWithExactMatch(sheetName, data(occupancy), occupancyVal,  "Verification of occupancy in ULD Tag screen ", screenName);
		
		//Verifying pou
		String pouVal=getAttributeWebElement(sheetName, "lst_pou;name",
				"pou", "value", screenName);

		
		verifyScreenTextWithExactMatch(sheetName, data(pou), pouVal,  "Verification of pou in ULD Tag screen ", screenName);
		
		//Verifying contents
		String contentsVal=getAttributeWebElement(sheetName, "inbx_contents;name",
				"contents", "value", screenName);

		
		verifyScreenTextWithExactMatch(sheetName, data(contents), contentsVal,  "Verification of contents in ULD Tag screen ", screenName);

}
/**
 * Description... ULD Tag Print
 * @param builtUpBy
 * @param occupancy
 * @throws Exception
 */
	public void ULDTagPrint(String builtUpBy, String occupancy) throws Exception {
        switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_ULDLoadingInstr;xpath",
				"ULD Loading Instrction Button", screenName);		
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_builtUpBy;xpath", builtUpBy, "Built Up By", screenName);
		enterValueInTextbox(sheetName, "inbx_occupancy;xpath", occupancy, "Occupancy", screenName);		
		
		clickWebElement(sheetName, "lst_weighingMode;xpath", "Weighing Mode Dropdown", screenName);
		enterValueInTextbox(sheetName, "inbx_weighingModeOption;xpath", "20-FT", "Weighing Mode Option", screenName);		
		
		performKeyActions(sheetName, "inbx_weighingModeOption;xpath", "DOWN", "Weighing Mode Option", screenName);
		performKeyActions(sheetName, "inbx_weighingModeOption;xpath", "ENTER", "Weighing Mode Option", screenName);
		try{
			driver.findElement(By.xpath("//button[contains(text(),'OK')]|//*[@name='btnOk']")).click();
			waitForSync(5);
		}
		catch(Exception e){
			
		}

		switchToWindow("getParent");
		
	}
	/**
	 * Description... ULD Tag Print With Special Lane
	 * @param builtUpBy
	 * @param occupancy
	 * @param splLane
	 * @throws Exception
	 */
	public void ULDTagPrintWithSpecialLane(String builtUpBy, String occupancy,String splLane) throws Exception {
        switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_ULDLoadingInstr;xpath",
				"ULD Loading Instrction Button", screenName);		
		switchToWindow("child");
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_builtUpBy;xpath", builtUpBy, "Built Up By", screenName);
		enterValueInTextbox(sheetName, "inbx_occupancy;xpath", occupancy, "Occupancy", screenName);	
	
				
		enterValueInTextbox(sheetName, "inbx_transportLane;name", data(splLane), "transportLane", screenName);	
		clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
	}
	/**
	 * Description... Save
	 * @throws InterruptedException
	 * @throws IOException 
	 */
public void save() throws InterruptedException, IOException
	{
		switchToFrame("default");
		switchToFrame("contentFrame","OPR013");
		waitForSync(3);
		clickWebElement(sheetName, "btn_save;xpath","Save Button", screenName);
		waitForSync(3);
		
	} 
/**
 * Description... Enter Plan Details
 * @param dollyWt
 * @throws InterruptedException
 */
	public void enterPlanDetails(String dollyWt) throws InterruptedException
   {
	enterValueInTextbox(sheetName, "inbx_dollyWt;name", data(dollyWt), "dollyWt", screenName);		
    }
	/**
	 * Description... print
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void print() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_print;xpath","Print Button", screenName);
		waitForSync(3);
	}
/**
 * Description... Log Remark
 * @param actValue
 * @param expValue
 * @param testSteps
 * @param screenName
 * @param functinalityName
 */
public void logRemark(String actValue, String expValue,
            String testSteps, String screenName, String functinalityName){
		 waitForSync(1);
         System.out.println("expected is : " + expValue + "\nactual is : "
                                         + actValue);
         test.log(LogStatus.INFO, "expected remark is : " + expValue + "\nactual remark is : " + actValue);
        
	}
/**
 * Description... Verify ULD Details
 * @param builtUpBy
 * @param occupancy
 * @param flightNum
 * @param ULDNo
 * @throws Exception
 */
public void verifyULDDetails(String builtUpBy, String occupancy,String flightNum, String ULDNo) throws Exception {
    switchToWindow("storeParent");
    waitForSync(2);
	clickWebElement(sheetName, "btn_ULDLoadingInstr;xpath",
			"ULD Loading Instrction Button", screenName);		
	switchToWindow("child");
	waitForSync(3);
	String FlightNo=getAttributeWebElement(sheetName, "inbx_FlightNumber;name", "Flight Number",
			"value", screenName);
    verifyScreenText(sheetName, data(flightNum), FlightNo,  " Flight Number ", screenName);

	String ULDNum=getAttributeWebElement(sheetName, "inbx_ULDNo;name", "ULD Number",
			"value", screenName);
    verifyScreenText(sheetName, data(ULDNo), ULDNum,  " ULD Number ", screenName);
    
    String BuildUp=getAttributeWebElement(sheetName, "inbx_builtUpBy;xpath", "BuildUp By",
			"value", screenName);
    verifyScreenText(sheetName, data(builtUpBy), BuildUp,  " BuildUp By ", screenName);

	String Occupancy=getAttributeWebElement(sheetName, "inbx_occupancy;xpath", "Occupancy",
			"value", screenName);
    verifyScreenText(sheetName, data(occupancy), Occupancy,  " Occupancy ", screenName);
    
	clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
}
/**
 * Description... Select Deselect Additional Loading Details
 * @param index
 * @throws Exception
 */
public void select_DeselectAdditionalLoadingDetails(String index) throws Exception {
String locator = xls_Read.getCellValue(sheetName, "tbl_addintionalLoadingDetailsTable;xpath");
String dynXpath = locator + "//input["+ index +"]";
clickWebElement(dynXpath, "Additional Loading Details Checkbox", screenName);
}

/**
 * Description... Click ULD Loading Instruction
 * @throws Exception
 */
public void clickULDLoadingInstruction() throws Exception {
    switchToWindow("storeParent");
 clickWebElement(sheetName, "btn_ULDLoadingInstr;xpath","ULD Loading Instrction Button", screenName);  
 switchToWindow("child");
 
}
/**
 * Description... Enter Overhang Details
 * @param F
 * @param L
 * @param R
 * @param A
 * @throws Exception
 */
public void enterOverhangDetails(String F, String L, String R, String A) throws Exception {
   
 enterValueInTextbox(sheetName, "inbx_overhang_F;name", F, "Forward overhang", screenName);
 enterValueInTextbox(sheetName, "inbx_overhang_L;name", L, "Left side Overhang", screenName);
 enterValueInTextbox(sheetName, "inbx_overhang_R;name", R, "Right side Overhang", screenName);
 enterValueInTextbox(sheetName, "inbx_overhang_A;name", A, "Backward Overhang", screenName);
 
}
/**
 * Description... Enter Builup and Occupancy
 * @param builtUpBy
 * @param occupancy
 * @throws Exception
 */
public void enterBuilupandOccupancy(String builtUpBy, String occupancy) throws Exception {
    
 enterValueInTextbox(sheetName, "inbx_builtUpBy;xpath", builtUpBy, "Built Up By", screenName);
 enterValueInTextbox(sheetName, "inbx_occupancy;xpath", occupancy, "Occupancy", screenName);  
 
}


/**Enters height
 * A-8705
 * @param height
 * @throws InterruptedException
 */
    public void enterOverhangIndent(String height) throws InterruptedException {
        enterValueInTextbox(sheetName, "inbx_overhang_F;name", data(height),
                "Overhang Indent", screenName);

 

    }
    
    
    /*
     * Description... Enter overhang Indent_A A-8705
     * 
     * @param Accessory
     */
    public void enterOverhangIndent_A(String A) throws InterruptedException {
        enterValueInTextbox(sheetName, "inbx_overhang_A;name", data(A),
                "Backward Overhang", screenName);

 

    }

 

    /*
     * Description...selects ULD Id A-8705
     * 
     * 
     */
    public void selectUldId(String uld) {
    selectValueInDropdown(sheetName,"txt_uldId;name", uld, "ULD ID", "Value");
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
 * Description... ULD Loading Instruction OK
 * @throws Exception
 */
public void ULDLoadingInstructionOK() throws Exception { 
waitForSync(2);
clickButtonSwitchtoParentWindow("Generic_Elements", "btn_OK;xpath", "OK Button", screenName);
waitForSync(2);
switchToFrame("default");
waitForSync(2);
switchToFrame("contentFrame","OPR013");
}


/**A-8705
 * Enters accessory weight
 * @param Accessory
 * @param Weight
 * @throws InterruptedException
 */
public void enterAccessoriesWeight(String Accessory, String Weight)
        throws InterruptedException {
    String row = xls_Read.getCellValue(sheetName,"tbl_AccessoriesDetail;xpath");
    String accesory = row + "//input[@value='" + Accessory
            + "']/ancestor::tr//input[@name='accdtlwgt']";
    enterValueInTextbox(accesory, data(Weight), "Accessory Details weight",
            screenName);



}




/*
 * Description... Enter Accessories Remarks A-8705
 * 
 * @param Accessory
 */
public void selectAccessoriesRow(String Accessory) {
    try {
        String row = xls_Read.getCellValue(sheetName,
                "tbl_AccessoriesDetail;xpath");
        String label = "(" + row + "//input[@value='" + Accessory
                + "']/ancestor::tr//input)[1]";
        driver.findElement(By.xpath(label)).click();



    } catch (Exception e) {



        test.log(LogStatus.FAIL, "Failed to select " + Accessory);
        System.out.println("Failed to select " + Accessory);
        Assert.assertFalse(true, "Element is not found");
    }



}

/**
 * Description... Verify Additional Loading Details
 * @param additionalloadingdetails
 * @throws Exception
 */
public void verifyAdditionalLoadingDetails(List <String> additionalloadingdetails) throws Exception {
    
 List <String> fieldDescriptions = null;
 By b = getElement(sheetName, "tbl_addintionalLoadingDetailsNames;xpath");  
 List <WebElement> AdditionalLoadingDetails=driver.findElements(b);
 
 for( WebElement description : AdditionalLoadingDetails){
  
   String fieldDescription= description.getText();
   
   if(additionalloadingdetails.contains(fieldDescription)){
     additionalloadingdetails.remove(fieldDescription);
    System.out.println("found true for " + fieldDescription);
    onPassUpdate(screenName, fieldDescription, fieldDescription, "additional loading detail verification ","additional loading details verification");
    
   }else{
    
    onFailUpdate(fieldDescription, fieldDescription, fieldDescription, "additional loading detail verification ","additional loading details verification");
   }
 }
  
}
/**
 * Description... Verify Remarks
 * @param expRemarks
 * @throws Exception
 */
public void verifyRemarks(String expRemarks) throws Exception {
String actRemarks = getAttributeWebElement(sheetName, "inbx_remarks;name","Remarks", "value" , screenName);
verifyScreenText(screenName, expRemarks, actRemarks, "Remarks", "Remarks verification");
}



/***A-8705
 * Verifies ULD remarks, takes care of jumbled up sequence
 * @param expRemarks
 * @param pmKey
 */
public void verifyULDRemarks(String expRemarks) {
    
    String actRemarks = getAttributeWebElement(sheetName, "inbx_remarks;name","Remarks", "value" , screenName);

    String[] act = actRemarks.trim().split("[/.]");
    String[] exp = expRemarks.trim().split("[/.]");
    Arrays.sort(act);
    Arrays.sort(exp);
    if(Arrays.equals(act,exp)==true){
        onPassUpdate(screenName,actRemarks ,expRemarks ,
                "uld Remarks verification",
                "1.List Flight 2. Check ULD Remarks");   
    }
    else{
        onFailUpdate(screenName,actRemarks ,expRemarks ,
                "uld Remarks verification",
                "1.List Flight 2. Check ULD Remarks");   
    }
}

public void verifyAcutalULDWeight(String actualWeight) {
String ULDWtVal = getAttributeWebElement(sheetName,
            "inbx_actWt;name", "actual ULD weight", "value", screenName);
verifyScreenTextWithExactMatch(sheetName, data(actualWeight), ULDWtVal,
            "Verification of actual ULD Wt in ULD Tag screen ", screenName);
      
}




/**
 * Description... Accessory details remarks verification
 * @param 
 * @param 
 * @throws Exception
 */
public void verifyAcessoryDtlsRemarks() throws Exception {
 
	
	checkIfUnchecked(sheetName, "chk_AccessoryDetailsBLP;xpath", "BLP accessory details chkbox", screenName);
	String lenBLPRemarks = getAttributeWebElement(sheetName, "inbx_AccessoryDetailsBLPRemarks;xpath", "BLP accessory details remarks", "maxlength", screenName);
	verifyValueOnPage(lenBLPRemarks, "250", "max length allowed in input box verification for Remarks field",screenName, "max length allowed in Accessory details remarks field");
	//enterUldDetails("inbx_AccessoryDetailsBLPRemarks;xpath", "<>?", "BLP Remarks");
	//verifyInputboxAttributes("inbx_AccessoryDetailsBLPRemarks;xpath", "Remarks", 0);
	enterDetails("inbx_AccessoryDetailsBLPRemarks;xpath", data("Remarks2"), "BLP Remarks");
	verifyInputboxAttributes("inbx_AccessoryDetailsBLPRemarks;xpath", "Remarks", 250);
	
	enterDetails("inbx_uldRemarks;id", data("Remarks2"), "ULD Remarks");
	
	
}


/**
 * Description...Enter Accessory details remarks 
 * @param 
 * @param 
 * @throws Exception
 */
public void enterAcessoryDtlsRemarks(String accDetailsLocator, String accDetailsWeightLocator, String data) throws Exception {
 
	
	checkIfUnchecked(sheetName, accDetailsLocator, "EIC accessory details chkbox", screenName);
	enterDetails(accDetailsWeightLocator, data, "ULD Remarks");
	
	
}






public void verifyULDDetailsAreDisabled(String[] elements, String[] EleName) {
        for (int i = 0; i < elements.length; i++) {
            WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue(
                    sheetName, elements[i])));
            String val = ele.getAttribute("readOnly");
            if (val.equals("true")) {
                onPassUpdate(screenName, EleName[i] + " is disabled",
                        EleName[i] + " should be disabled",
                        "Verification of ULD details",
                        "Verification of ULD details");
            } else {
                onFailUpdate(screenName, EleName[i] + " is disabled",
                        EleName[i] + " should be disabled",
                        "Verification of ULD details",
                        "Verification of ULD details");
            }


        }


    }


    public void verifyULDDetailsAreEnabled(String[] elements,
            String[] uLDDetails2) {
        for (int i = 0; i < elements.length; i++) {
            WebElement ele = driver.findElement(By.xpath(xls_Read.getCellValue(
                    sheetName, elements[i])));
            if (ele.isEnabled() == true) {
                onPassUpdate(screenName, uLDDetails2[i] + " is enabled",
                        uLDDetails2[i] + " should be enabled",
                        "Verification of ULD details",
                        "Verification of ULD details");
            } else {
                onFailUpdate(screenName, uLDDetails2[i] + " is enabled",
                        uLDDetails2[i] + " should be enabled",
                        "Verification of ULD details",
                        "Verification of ULD details");
            }


        }


    }








}
