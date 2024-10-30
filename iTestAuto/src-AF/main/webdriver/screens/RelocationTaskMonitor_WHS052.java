package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.*;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import com.relevantcodes.extentreports.LogStatus;



public class RelocationTaskMonitor_WHS052 extends CustomFunctions {
	
	String sheetName = "RelocationTaskMonitor_WHS052";
	String screenName = "RelocationTaskMonitor_WHS052";
	String screenId="WHS052";	
	public String TOProppath = "\\src\\resources\\GlobalVariable.properties";
	public RelocationTaskMonitor_WHS052(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
/**
 * Description...	
 * @param carrierCode
 * @param awbNumber
 * @throws InterruptedException
 */
	public void enterAWB(String carrierCode,String awbNumber) throws InterruptedException
	{
		//Enter carrier code
		enterValueInTextbox(sheetName, "inbx_awbPrefix;xpath", data(carrierCode), "Carrier Code", screenName);
		
		//Enter awb number
		enterValueInTextbox(sheetName, "inbx_awbNumber;xpath", data(awbNumber), "Awb Number", screenName);
		
	}
	/**
	 * @author A-9844
	 * Desc: verify table records -TO details
	 * @throws Exception
	 */
	public void verifyTableRecords(int verfCols[],String actVerfValues[],String pmKey) throws Exception
	{
		
		verify_tbl_records_multiple_cols(sheetName, "tbl_messageDetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}

/**
	 * @author A-9847
	 * @desc To Click on List Button
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void VerifyBuildupTOTriggered() throws InterruptedException, IOException{

		String locator = xls_Read.getCellValue(sheetName, "txt_norecords;xpath");
		

		String time=getPropertyValue(TOProppath,"waitFor");
		int t=Integer.parseInt(time);
		System.out.println(t);
		for(int i=0;i<=t;i++)
		{
			listAwbDetails();
			int errorSize=driver.findElements(By.xpath(locator)).size();
			try{

				if (errorSize==0){

					writeExtent("Pass", "Buildup TO details are listed on "+ screenName);
					break;
				}

				else{

					waitForSync(30);
					i++;
				}
			}
			catch (Exception e) {
				writeExtent("Fail", "Buildup TO details are not displayed on "+screenName);
			}

		}

	}

	
	/**
     * @author A-10690
     * Description... verify ULD/awb removed from relocation task monitor screen
     * @param ULD/AWB
   * @throws IOException 
     **/

    public void verifyULDRemoved(String uldNumber)throws InterruptedException, AWTException, IOException {

    	try{

			
			String locator = xls_Read.getCellValue(sheetName, "txt_norecords;xpath");
		

			int size=driver.findElements(By.xpath(locator)).size();

			if(size==1){
				writeExtent("Pass", "Verified the ULD details " + data(uldNumber) + "removed from " + screenName);
			}
			else{
				writeExtent("Fail", "ULD details  " + data(uldNumber) + "are not removed from  " + screenName);
				Assert.assertFalse(true, "ULD details " + data(uldNumber) + "are not removed from " + screenName );
			}
	}
		
		catch (Exception e) {
			writeExtent("Fail", "Could not verify ULD  details removed from " + screenName);
		}

	}


	/**
	 * @author A-10328
	 * Description... maximize ULD details
	 * @param ULD
	 * @throws InterruptedException
	 * @throws IOException 
	 */



public void maximizeULDDetails(String UldNum) throws InterruptedException, IOException
{
	String locator=xls_Read.getCellValue(sheetName, "drp_maximizeAWBDetails;xpath");
	try
	{
		locator=locator.replace("*", data(UldNum));
		driver.findElement(By.xpath(locator)).click();  	
		waitForSync(2); 
	}

	catch(Exception e)
	{
		writeExtent("Fail","ULD details could not be maximized on "+screenName);
	}
}
/**
 * @author A-10328
 * Description... verify current location
 * @param ULD
 * @param colnum
 * param expLocation
 * @throws InterruptedException
 * @throws IOException 
 */


public void verifyCurrentLoc(String UldNum,String colnum,String expLocation) throws InterruptedException, IOException
{
	String locator=xls_Read.getCellValue(sheetName, "txt_Location;xpath");
	locator=locator.replace("*", data(UldNum));
	locator=locator.replace("colNo",colnum);
	moveScrollBar(driver.findElement(By.xpath(locator)));
	waitForSync(2);
	By ele =By.xpath(locator);
	waitForSync(3);
	String actText = driver.findElement(ele).getText();
	System.out.println(actText);
	verifyScreenTextWithExactMatch(sheetName, expLocation,actText, "verification of current location", "Export Manifest");

}
/**
 * @author A-9844
	 * Description... verify destination location
	 * @param ULD
	 * @param colnum
	 * param expLocation
	 * @throws InterruptedException
	 * @throws IOException 
	 */


public void verifyDestLocation(String UldNum,String colnum,String expLocation) throws InterruptedException, IOException
	{
		String locator=xls_Read.getCellValue(sheetName, "txt_Location;xpath");
		locator=locator.replace("*", data(UldNum));
		locator=locator.replace("colNo",colnum);
		moveScrollBar(driver.findElement(By.xpath(locator)));
		By ele =By.xpath(locator);
		waitForSync(3);
		String actText = driver.findElement(ele).getText();
		System.out.println(actText);
		verifyScreenTextWithExactMatch(sheetName, expLocation,actText, "verification of destination location", "Export Manifest");
		}
	

/**
 * Description...
 * @param ULD
 * @param DestinationLoc
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	 public void verifyActionedLoc(String ULD,String DestinationLoc) throws InterruptedException, AWTException, IOException {
    expandAWB();
    waitForSync(10);
    String Text = getElementText(sheetName,
                                    "htmlDiv_ULD_ActionedLoc;xpath", "Actioned Location", screenName);
    String Text1=Text.replace("Actioned Location","");
    String expectedText = Text1.replaceAll("\\s+", "");
    System.out.println("actioned LOc"+expectedText);
    String actualText = data(DestinationLoc);
    verifyScreenText(
                                    sheetName,
                                    expectedText,
                                    actualText,
                                    "ULD Number",
                                    "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check ULD TO actioned location \n ");

}


	 /**
		 * @author A-9844
		 * @param verfCols
		 * @param verfCols
		 * @param pmKey
		 * @throws Exception
		 * Desc : verify TO details of AWB
		 */

		public void verifyTODetailsOfAWB(int verfCols[],String actVerfValues[],String pmKey) throws Exception {

			String actText="";
			String locator = xls_Read.getCellValue(sheetName, "txt_awbNumbers;xpath");
			List <WebElement> elements= driver.findElements(By.xpath(locator));

			boolean found=false;

			for(WebElement  elemt:elements){
				

				actText=elemt.getText();
				System.out.println("ActTect   "+actText);

				if(actText.equals(pmKey)){
					found=true;
					break;
				}

			}

			if(found){
				
				String locator1 = xls_Read.getCellValue(sheetName, "txt_awbNumberSU;xpath");
				locator1=locator1.replace("*",pmKey);
				moveScrollBar(driver.findElement(By.xpath(locator1)));

				writeExtent("Pass", "Successfully verified TO details for the awb number "+pmKey+" is displayed  on " +screenName);
				verifyTableRecords(verfCols, actVerfValues, pmKey);
				
				

			}

			else
			{

				writeExtent("Fail", "Failed verify TO details for the awb number "+pmKey+" is displayed  on " +screenName);

			}
		}
		/**
		 * @author A-10690
		 * Desc - Verify TO status
		 * @param status column name,awbnumber,expected status
		 * @throws AWTException 
		 */
		public void verifyTOStatus(String status,String awb,String expStatus) throws InterruptedException, AWTException{


			String locator=xls_Read.getCellValue(sheetName, "txt_columnName;xpath");
			locator=locator.replace("colName",status);
			String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-csid");

			String locator1=xls_Read.getCellValue(sheetName, "txt_toDetailsText;xpath");
			locator1=locator1.replace("awb",data(awb));
			locator1=locator1.replace("colNo",columnnumber);
			String acttext = driver.findElement(By.xpath(locator1)).getText();
			System.out.println(acttext);
			String[] expectedStatus=expStatus.split("/");

			if((acttext.equalsIgnoreCase(expectedStatus[0]))||(acttext.equalsIgnoreCase(expectedStatus[1])))
			{
				writeExtent("Pass","Successfully verified the status as "+expectedStatus[0]+"or"+expectedStatus[1]+ " on "+screenName);
			}
			else{
				writeExtent("Fail","Failed to verify the status as "+expectedStatus[0]+"or"+expectedStatus[1]+ " on "+screenName);
			}
	}


		/**
		 * @author A-9844
		 * Description... maximize awb details
		 * @param awb
		 * @throws InterruptedException
		 * @throws IOException 
		 */
		public void maximizeAwbDetails(String awb) throws InterruptedException, IOException
		{
			String locator=xls_Read.getCellValue(sheetName, "drp_maximizeAWBDetails;xpath");
			locator=locator.replace("*", data(awb));
			driver.findElement(By.xpath(locator)).click();  	
			waitForSync(2);   
		}
		/**
		 * @author A-9844
		 * Description... verify current location
		 * @param awb
		 * @param colnum
		 * param expLocation
		 * @throws InterruptedException
		 * @throws IOException 
		 */
		public void verifyCurrentLocation(String awb,String colnum,String expLocation) throws InterruptedException, IOException
		{
			String locator=xls_Read.getCellValue(sheetName, "txt_Location;xpath");
			locator=locator.replace("*", data(awb));
			locator=locator.replace("colNo",colnum);
			moveScrollBar(driver.findElement(By.xpath(locator)));
			waitForSync(2);
			By ele =By.xpath(locator);
			waitForSync(3);
			String actText = driver.findElement(ele).getText();
			System.out.println(actText);
			if(actText.equals(expLocation))
			{
				writeExtent("Pass","Successfully verified the current location as "+ expLocation+ "on " +screenName);
			}

			else
			{
				writeExtent("Fail","Failed to verify the current location as "+ expLocation+ "on " +screenName);

			}
		}
		/**
		 * @author A-9844
		 * Description... verify destination location
		 * @param awb
		 * @param colnum
		 * param expLocation
		 * @throws InterruptedException
		 * @throws IOException 
		 */
		public void verifyDestinationLocation(String awb,String colnum,String expLocation) throws InterruptedException, IOException
		{
			String locator=xls_Read.getCellValue(sheetName, "txt_Location;xpath");
			locator=locator.replace("*", data(awb));
			locator=locator.replace("colNo",colnum);
			moveScrollBar(driver.findElement(By.xpath(locator)));
			By ele =By.xpath(locator);
			waitForSync(3);
			String actText = driver.findElement(ele).getText();
			System.out.println(actText);
			if(actText.equals(expLocation))
			{
				writeExtent("Pass","Successfully verified the destination location as "+ expLocation+ "on " +screenName);
			}

			else
			{
				writeExtent("Fail","Failed to verify the destination location as "+ expLocation+ "on " +screenName);

			}
		}

	/**
	 * Description... Verification Of Explosive Icon For NonSCC
      * Author : A-8705 Date Created/ Modified : 04/06/2019 Description
      * :Verification explosive icon is not displayed for Non SCC goods
      */
      public void VerificationOfExplosiveIconForNonSCC()
                  throws InterruptedException {
            verifyElementNotDisplayed(
                        sheetName,
                        "img_ExplosiveIcon;xpath",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check Explosive icon against TO \n",
                        screenName, "Explosive Icon");

      }
      /**
  	 * @9844
  	 * Description... Enter source location
  	 * @param uldNumber
  	 * @throws InterruptedException
  	 */
  	public void enterSourceLocation(String srcLocation) throws InterruptedException
  	{

  		enterValueInTextbox(sheetName, "inbx_srcLocation;id", data(srcLocation), "Source Location", screenName);


  	}
      /**
       * Description... Verify Close Button On History
      * Author : A-8705 Date Created/ Modified : 26/06/2019 Description
      * :Verification of "close" button in Audit pop up
      */
      public void verifyCloseButtonOnHistory() throws Exception {
            clickWebElement(sheetName, "CheckBox_TO;xpath", "checkbox button",
                        screenName);
            waitForSync(3);
            clickWebElement(sheetName, "Btn_History_TO;xpath", "Status button",
                        screenName);
            waitForSync(2);
            switchToWindow("child");
            String expectedText = getElementText(sheetName, "btn_Close;xpath",
                        "Close Button", screenName);
            switchToWindow("getParent");
            String actualText = "Closed";
            verifyScreenText(
                        sheetName,
                        expectedText,
                        actualText,
                        "Closed Button",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check Close button in Audit pop up \n ");

      }
      /**
		 * @author A-9844
		 * Description... verify vehicle type
		 * @param awb/uldNo
		 * @param colnum
		 * param expLocation
		 * @throws InterruptedException
		 * @throws IOException 
		 */
		public void verifyVehicleType(String uldNo,String colnum,String expLocation) throws InterruptedException, IOException
		{
			String locator=xls_Read.getCellValue(sheetName, "txt_Location;xpath");
			locator=locator.replace("*", data(uldNo));
			locator=locator.replace("colNo",colnum);
			moveScrollBar(driver.findElement(By.xpath(locator)));
			By ele =By.xpath(locator);
			waitForSync(3);
			String actText = driver.findElement(ele).getText();
			System.out.println(actText);
			if(actText.equals(expLocation))
			{
				writeExtent("Pass","Successfully verified the vehicle type as "+ expLocation+ "on " +screenName);
			}

			else
			{
				writeExtent("Fail","Failed to verify the vehicle type as "+ expLocation+ "on " +screenName);

			}
		}
      /**
  	 * @author A-9844
  	 * Description... Select status Closed-C, Closed with Discrepancy-CD, Force Closed-CF, In Progress-A, Open -O
  	 * @throws InterruptedException
  	 * @throws IOException 
  	 */
  		public void selectStatus(String Status) throws InterruptedException, IOException {
  			

  			clickWebElement(sheetName, "btn_Status;xpath", "Status button", screenName);
  			waitForSync(2);
  			String locator=xls_Read.getCellValue(sheetName, "txt_selectStatus;xpath");
  			locator=locator.replace("*", Status);
  			driver.findElement(By.xpath(locator)).click();  	
  			waitForSync(2); 
  			writeExtent("Pass", "Successfully selected the status "+ Status + "on "+screenName);
  			clickWebElement(sheetName, "btn_Status;xpath", "Status button", screenName);

  		
  		}	
/*
	 * A-8705 Verfies ULD TO created
	 */
 /**
  * Description... Verify ULD TO
  * @param uldNumber
  * @throws InterruptedException
  */
	public void verifyULDTO(String uldNumber) throws InterruptedException {
		String expectedText = getElementText(sheetName,
				"htmlDiv_ULDNumber;xpath", "ULD Number", screenName);
		String actualText = data(uldNumber);
		verifyScreenText(
				sheetName,
				expectedText,
				actualText,
				"ULD Number",
				"//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check ULD TO \n ");

	} 
	/**
	 * Description... Verify History
      * Author : A-8705 Date Created/ Modified : 26/06/2019 Description
      * :Verification of "TO Creation" in Audit pop up screen
      */
      public void verifyHistory() throws Exception {
                       clickWebElement(sheetName, "CheckBox_TO;xpath", "checkbo button",
                        screenName);
            Thread.sleep(10000);
            switchToWindow("storeParent");
            clickWebElement(sheetName, "Btn_History_TO;xpath", "Status button",
                        screenName);
            Thread.sleep(10000);
            switchToWindow("child");
            String expectedText = getElementText(sheetName,
                        "btn_TO_Creation;xpath", "TO Creation", screenName);
            switchToWindow("getParent");
            String actualText = "TO Creation";
            verifyScreenText(
                        sheetName,
                        expectedText,
                        actualText,
                        "Closed Button",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check TO Creation in Audit pop up \n ");


      }
                          public void verifyULDStatus(String ULDStatus) throws InterruptedException {
                                String expectedText = getElementText(sheetName,
                                                                "htmlDiv_ULD_Status;xpath", "ULD Status", screenName);
                                String actualText = data(ULDStatus);
                                System.out.println("status"+expectedText);
                                verifyScreenText(
                                                                sheetName,
                                                                expectedText,
                                                                actualText,
                                                                "ULD Number",
                                                                "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check ULD TO status \n ");
                                
                }

/**
 * Description... Verify Actioned Time
 * @param flightDate
 * @throws InterruptedException
 */
                public void verifyActionedTime(String flightDate)
                  throws InterruptedException {
            String Text = getElementText(sheetName,
                        "htmlDiv_ULD_ActionedTime;xpath", "Actioned Time and Date",
                        screenName);
            String Text1 = Text.replace("ActionedDate/Time", "");
            String Text2 = Text1.substring(0, 11);
            String expectedText1 = Text1.replaceAll("\\s+", "");
            String expectedText=expectedText1.substring(0,11);
            String actualText = data(flightDate);
            System.out.println("actioned time" + expectedText);
            verifyScreenText(
                        sheetName,
                        expectedText,
                        actualText,
                        "ULD Number",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check ULD TO Time \n ");

      }


/**
 * Description... Verify Status
 * @param ULD
 * @throws InterruptedException
 */
public void verifyStatus(String ULD) throws InterruptedException {
            String expectedText = getElementText(sheetName,
                        "htmlDiv_ULD_Status;xpath", "ULD Number", screenName);
            String actualText = "Closed";
            verifyScreenText(
                        sheetName,
                        expectedText,
                        actualText,
                        "ULD Number",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check ULD TO status \n ");
            
      }


      /**
       * Description... Verification Of Vehile Type
      * Author : A-8705 Date Created/ Modified : 26/06/2019 Description
      * :Verification of Vehicle Type in TO
      */
      public void verificationOfVehileType(String vehicleType) throws Exception {
            expandAWB();
            waitForSync(3);
            String expectedText = getElementText(sheetName,
                        "htmlDiv_VehicleType;xpath", "Vehicle Type", screenName);
            String actualText = data(vehicleType);
            verifyScreenText(
                        sheetName,
                        expectedText,
                        actualText,
                        "Vehicle Type",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check Vehicle Type \n ");

      }
/**
 * Description... Enter Date Details
 * @param fromDate
 * @param toDate
 * @throws AWTException
 * @throws InterruptedException
 */
	public void enterDateDetails(String fromDate,String toDate) throws AWTException, InterruptedException
	{
		//from date
		enterValueInTextbox(sheetName, "inbx_fromDate;xpath", data(fromDate), "From Date", screenName);
		//from date
		enterValueInTextbox(sheetName, "inbx_toDate;xpath", data(toDate), "To Date", screenName);
		
		
	}
	/**
	 * Description... Verification Of Handling Area
      * Author : A-8705 Date Created/ Modified : 26/06/2019 Description
      * :Verification of Handling Area in TO
      */
      public void verificationOfHandlingArea(String DestinationHandlingArea)
                  throws Exception {
            String expectedText = getElementText(sheetName,
                        "txt_Destination;xpath", "Handling Area", screenName);
            String actualText = data(DestinationHandlingArea);
            verifyScreenText(
                        sheetName,
                        expectedText,
                        actualText,
                        "Vehicle Type",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check Vehicle Type \n ");

      }

/**
 * Description... Click Edit Search Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickEditSearch() throws InterruptedException, IOException
                {
                                //Edit search
                                clickWebElement(sheetName, "htmli_editsearch;xpath", "Edit Search", screenName);
                                waitForSync(2); 
                }
/**
 * Description... Change Uld Status
 * @param destination
 * @throws Exception
 */
                public void changeUldStatus(String destination) throws Exception 
                {
                                // Change ULD Status
                                clickWebElement(sheetName, "htmli_relocation;xpath", "Relocation Navigation", screenName);
                                waitForSync(2); 
                                switchToWindow("storeParent");
                                switchToWindow("child");
                                enterValueInTextbox(sheetName, "inbx_newdestination;xpath", data(destination), "Destination Location", screenName);
                                waitForSync(3);
                                clickWebElement(sheetName, "btn_save;xpath", "Save Button", screenName);
                                waitForSync(2);
                                switchToWindow("getParent");
                                switchToFrame("contentFrame","WHS052");
                
                }
/**
 * Description...  Click Clear Button              
 * @throws InterruptedException
 * @throws IOException 
 */
                public void clickClearButton() throws InterruptedException, IOException
                {
                                //Clear Button
                                clickWebElement(sheetName, "btn_clear;xpath", "Clear", screenName);
                                waitForSync(2); 
                }
  /**
   * Description...  Clear Date Filters            
   */
                public void clearDateFilters()
                {
                                //From date
                                clearText(sheetName, "inbx_fromDate;xpath","From Date", screenName);
                                //To date
                                clearText(sheetName, "inbx_toDate;xpath","To Date", screenName);
                                
                }
               /**
                * Description... Verify No Of Transport Orders
                * @throws InterruptedException
                */
				public void verifyNoOfTransportOrders() throws InterruptedException {
                  List checkboxList=returnListOfElements(sheetName, "lst_NumberOfCheckBox;xpath");
                  String expectedText=getElementText(sheetName, "txt_NumberOfTO's;xpath", "Number of TO's", screenName);    
                  int ac=returnListSize(checkboxList);
                  String actualText=String.valueOf(ac);
                  verifyScreenText(sheetName, expectedText, actualText,"Number of TO's",
                              "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check number of TO's created in Message Heading \n ");
                  
                        
            }
/**
 * Description... Verify No Select All Checkbox
 * @throws Exception
 */
                 public void verifyNoSelectAllCheckbox() throws Exception {
                  clickWebElement(sheetName, "CheckBox_TO;xpath", "checkbox button", screenName);
                  waitForSync(3);
                  clickWebElement(sheetName, "CheckBox_TO_2;xpath", "checkbox button 2", screenName);
                  waitForSync(3);
                  clickWebElement(sheetName, "Btn_History_TO;xpath", "Status button", screenName);
                  waitForSync(2);
                  String expectedText=handleAlertAndReturnText();
                  String actualText="Please select a single row";
                  verifyScreenText(sheetName, expectedText, actualText,"Error Message_noSelectAll",
                              "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Select  2 TO's and Click on History button \n ");
            }
/**
 * Description... Enter Flight Details
 * @param carrierCode
 * @param flightNumber
 * @throws InterruptedException
 */
	 public void enterFlightDetails(String carrierCode,String flightNumber) throws InterruptedException
      {
                                //Enter carrier code
                                enterValueInTextbox(sheetName, "inbx_carriercode;xpath", data(carrierCode), "Carrier Code", screenName);
                                
                                //Enter flight number
                                enterValueInTextbox(sheetName, "inbx_flightno;xpath", data(flightNumber), "Flight Number", screenName);
                                
      }
/**
 * Description... Verify Filter Date
 * @param xpath
 * @param filterStartDate
 * @throws InterruptedException
 */
                        public void verifyFilterDate(String xpath, String filterStartDate) throws InterruptedException
                {
                                //Fetching date and verifying with the given filtered date 
                                String date = getElementTextnoFrameSwitch(sheetName, xpath, "Date", screenName);
                                if(date.equals(filterStartDate))
                                {
                                                System.out.println("Date given match with filtered date");
                                                test.log(LogStatus.PASS, "Date given match with filtered date");
                                }              
                }
 /**
  * Description... Get No Of Results       
  * @param count
  * @throws InterruptedException
  */
public void getNoOfResults(String count) throws InterruptedException
                {
                                //Number of results
                                String noOfResults = getElementTextnoFrameSwitch(sheetName, "htmlDiv_recordsize;xpath","Results", screenName);
                                waitForSync(2);
                                if(noOfResults.equals(count))
                                {
                                                System.out.println("Appropriate filters are applied and results are filtered out");
                                                test.log(LogStatus.PASS, "Appropriate filters are applied and expected results are filtered out");
                                }
                                
                                
                }
                
/**
 * Description... Uncheck TO Status
 * @throws AWTException
 * @throws InterruptedException
 * @throws IOException 
 */
	public void uncheckTOStatus() throws AWTException, InterruptedException, IOException
	{
		
		clickWebElement(sheetName, "lst_toStatus;id", "TO Status", screenName);
		waitForSync(2);	
		clickWebElement(sheetName, "htmlDiv_statusTypes;xpath", "TO Status Types", screenName);
		waitForSync(2);	
		
	}
/**
 * Description... Expand AWB
 * @throws AWTException
 * @throws InterruptedException
 * @throws IOException 
 */
	public void expandAWB() throws AWTException, InterruptedException, IOException
	{
		
		clickWebElement(sheetName, "img_expandAwb;xpath", "Expand AWB", screenName);
		waitForSync(1);	
	}
/**
 * Description... Click List Awb Details
 * @throws InterruptedException
 * @throws IOException 
 */
	public void listAwbDetails() throws InterruptedException, IOException
	{
		//List button
				clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
				waitForSync(5);	
	}
/**
 * Description... Delete TO
 * @param remarks
 * @throws AWTException
 * @throws InterruptedException
 * @throws IOException 
 */
	public void deleteTO(String remarks) throws AWTException, InterruptedException, IOException
	{
	
		clickWebElement(sheetName, "lnk_deleteTO;xpath", "Delete TO", screenName);
		waitForSync(3);	
		
		selectValueInDropdown(sheetName,"lst_remarks;xpath",remarks, "Select Remarks", "VisibleText");
		
		waitForSync(1);	
				
		clickWebElement(sheetName, "btn_saveRemarks;xpath", "Save Remarks", screenName);
		
		waitForSync(8);	
	}
/**
 * Description... Uncheck Relocation Status
 * @throws InterruptedException
 * @throws IOException 
 */
	public void unchkRelocationStatus() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
		waitForSync(1);	
		clickWebElement(sheetName, "spn_checkAll;xpath", "check Status", screenName);
		waitForSync(1);	
	}
	/**
	 * @author A-9844
	 * Desc - Verify TO details
	 * @param columnName
	 * @throws AWTException 
	 */
	public void verifyTODetails(int count,String[] columnName,String awb,String[] toDetails) throws InterruptedException, AWTException{

		for(int i=0;i<count;i++){

			String locator=xls_Read.getCellValue(sheetName, "txt_columnName;xpath");
			locator=locator.replace("colName",columnName[i]);
			String columnnumber = driver.findElement(By.xpath(locator)).getAttribute("data-ic-csid");

			String locator1=xls_Read.getCellValue(sheetName, "txt_toDetailsText;xpath");
			locator1=locator1.replace("awb",data(awb));
			locator1=locator1.replace("colNo",columnnumber);
			String acttext = driver.findElement(By.xpath(locator1)).getText();
			System.out.println(acttext);

			if(acttext.equalsIgnoreCase(toDetails[i]))
			{
				writeExtent("Pass","Successfully verified the status as "+toDetails[i]+ " on "+screenName);

			}
			else{
				writeExtent("Fail","Failed to verify the status as "+toDetails[i]+ " on "+screenName);
			}

		}

	}
/**
 * Description... Verify Flight Details
 * @param fltDetails
 * @throws InterruptedException
 */
	public void verifyFlightDetails(String fltDetails) throws InterruptedException {
			
		waitForSync(4);

		
	
		String flightDetails=getElementText( sheetName,  "htmlDiv_flight;xpath",
				"flight Details",  "Relocation taskMonitor");
		
		
	

		if(flightDetails.contains(data(fltDetails)))
		{
			writeExtent("Pass", "Flight Details matched ; Expected : "+data(fltDetails)+ "Actual : "+flightDetails);
		}

		else
		{
			writeExtent("Fail", "Flight Details not matched ; Expected : "+data(fltDetails)+ "Actual : "+flightDetails);
		}

	}
/**
 * Description... Verify Awb Details
 * @param verfCols
 * @param actVerfValues
 * @param pmKey
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyAwbDetails(int verfCols[],String actVerfValues[],String pmKey
			) throws InterruptedException, IOException {
		waitForSync(4);
		verify_tbl_records_multiple_cols(sheetName, "table_messageDetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}
/**
 * Description...	Verify Message Details
 * @param verfCols
 * @param actVerfValues
 * @throws InterruptedException
 */
	public void verifyMessageDetails(int verfCols[],String actVerfValues[]
			) throws InterruptedException {
		waitForSync(4);
		verify_col_records(sheetName, "innerText","htmlDiv_messageDetails;xpath", verfCols, actVerfValues);
	}
/**
 * Description... Enter ULD Number
 * @param uldNumber
 * @throws InterruptedException
 */
	public void enterULDNumber(String uldNumber) throws InterruptedException
	{
		//Enter uld number
		enterValueInTextbox(sheetName, "inbx_uldNumber;xpath", data(uldNumber), "ULD number", screenName);
		
		
	}
/**
 * Description... Get Id Label
 * @return
 * @throws InterruptedException
 */
	public String getIdLabel() throws InterruptedException {
		
		String idLabel=getElementText(sheetName, "htmlDiv_idLabel;xpath",
				"Id label", screenName);
		
		     return idLabel;
		
	}
/**
 * Description... Select Unchec kAll
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectUncheckAll() throws InterruptedException, IOException {
		
		
		waitForSync(2);
		clickWebElement(sheetName, "btn_Status;xpath", "Status button", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "click_uncheckAll;xpath", "Uncheck All", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_Status;xpath", "Status button", screenName);
	
	}	
	
	
	
/**
 * Description...	Verify AWB Status After Creation Of New Id Label
 * @param status
 */
	public void verifyAWBStatusAfterCreationOfNewIdLabel(String status) {
		ele = findDynamicXpathElement("txt_status2;xpath", sheetName,
				"Status", screenName);
		String actualText = ele.getText();	
		String expectedText = data(status);			
		verifyScreenText(sheetName, expectedText, actualText,"AWB Status","AWB Status" +
				"//1. Login to iCargo \n , 2.Invoke Relocation task monitor screen \n" +
				"3.Lsit the AWB and check for the AWB status " + expectedText);
	}
	
/**
 * Description...	Verify New To Created Or Not
 * @param status
 */
	public void verifyNewToCreatedOrNot(String status) {
		ele = findDynamicXpathElement("txt_status;xpath", sheetName,
				"Status", screenName);
		String actualText = ele.getText();	
		String expectedText = data(status);			
		verifyScreenText(sheetName, expectedText, actualText,"New Open To created","New Open To created" +
				"//1. Login to iCargo \n , 2.Invoke Relocation task monitor screen \n" +
				"3.Lsit the AWB and check for the AWB status " + expectedText);
	}
	
	
/**
 * Description...	Verify New Active Id Label
 * @param oldIdLabel
 */
	public void verifyNewActiveIdLabel(String oldIdLabel){
	
		ele = findDynamicXpathElement("htmlDiv_idLabel;xpath", sheetName,
				"Id label", screenName);
		String idLabelText = ele.getText();	
		String actualText = idLabelText.split("Id Label")[1].toString();
		String expectedText = data(oldIdLabel);	
		verifyScreenTextNotExists(sheetName, expectedText, actualText,"New Id label created","New Id label created" +
				"//1. Login to iCargo \n , 2.Invoke Relocation task monitor screen \n ,3.Verify the activeId label  \n ");
	
	}
	
/**
 * Description...	Select Closed Status
 * @throws InterruptedException
 * @throws IOException 
 */
	public void selectClosedStatus() throws InterruptedException, IOException {
		waitForSync(2);
		clickWebElement(sheetName, "btn_Status;xpath", "Status button", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "click_uncheckAll;xpath", "Uncheck All", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "chkBx_Closed;xpath", "Closed", screenName);	
		waitForSync(2);
		clickWebElement(sheetName, "btn_Status;xpath", "Status button", screenName);
	
	}	
	
/**
 * Description...	Verify AWB Status
 * @param status
 */
	
	public void verifyAWBStatus(String status) {
		ele = findDynamicXpathElement("txt_status;xpath", sheetName,
				"Status", screenName);
		String actualText = ele.getText();	
		String expectedText = data(status);			
		verifyScreenText(sheetName, expectedText, actualText,"AWB Status","AWB Status" +
				"//1. Login to iCargo \n , 2.Invoke Relocation task monitor screen \n" +
				"3.Lsit the AWB and check for the AWB status " + expectedText);
	}
/**A-8705
      * Description... verifies audit of Transport order for individual status is present or not
      * 
       * @param String status and String array ,contents of Transport audit
      * @return  boolean 
       */
      

      public void verifyAudit(String status, String[] expectedResult) throws Exception {
            {                 
                  String actualResult = null;
                  System.out.println(data(status));         
                  for (int i = 1; i <=6; i++) {
                        try {
                              String dynXpath="//table[@id='GeneralOrder']//*[contains(text(),'"
                                          + data(status) + "')]/..//td[" + i + "]";
                              System.out.println(dynXpath);
                              WebElement ele=driver.findElement(By.xpath(dynXpath));
                              String Result = ele.getText();
                              actualResult = Result.replaceAll("\\s+", "");
                              String expRes=expectedResult[i-1].replaceAll("\\s+", "");
                              if (actualResult.contains(expRes)) {
                                    System.out.println("found true for " + actualResult);
                                    onPassUpdate(screenName, expectedResult[i-1],
                                                actualResult, "Table verification against "
                                                            + status + " On ", "Table verification");
                              }
                        } catch (Exception e) {
                              onFailUpdate(screenName, expectedResult[i], actualResult,
                                          "Table verification against " + status + " On ",
                                          "Table verification");
                              System.out.println(e);
                        }
                  }

            }
      }

      /**A-8705
      * Description...verifies Transport order is created or not
      * 
       * @param String awb number 
       * @return  boolean 
       */
      public void verifyAwbTO(String AWBNo) throws InterruptedException {
            String expectedText = getElementText(sheetName,
                        "htmlDiv_ULDNumber;xpath", "AWB Number", screenName);
            String actualText = "020" + "-" + data(AWBNo);
            verifyScreenText(
                        sheetName,
                        expectedText,
                        actualText,
                        "ULD Number",
                        "//1. Login to iCargo \n , 2.Complete TO Creation\n ,3.Invoke WHS052 screen \n 4.Check ULD TO \n ");

      }
      

      /**A-8705
      * Description...Clicks History button and switch to child window
      * @param 
       * @return  
       */
      public void clickHistory() throws Exception {         
            clickWebElement(sheetName, "CheckBox_TO;xpath", "checkbox button",
                        screenName);
            waitForSync(3);
            clickButtonSwitchWindow(sheetName, "Btn_History_TO;xpath", "Status button",
                        screenName);
            

      }
	
/**
       * @author A-8468
       * Description... The method will verify each and every row listed for particular filter
       * @param Column_name : column header for column to be verified
       * @param Expected value : value to be verified
       **/
       public void verifyEachRow(String Column_name, String ExpectedValue){
              int column = 0;
              String table_header = (xls_Read.getCellValue(sheetName, "table_messageDetailsHeader;xpath"))+"//th";
              List<WebElement> headers = driver.findElements(By.xpath(table_header));
              for(int j=1; j<=headers.size(); j++){
                     if(headers.get(j).getText().contains(Column_name)){
                           column=j;
                     }
              }
              
              String table_row = xls_Read.getCellValue(sheetName, "table_messageDetails;xpath");
              List<WebElement> rows = driver.findElements(By.xpath(table_row));
              
               for(int i = 1; i<=rows.size() ; i++)
                     {
                     System.out.println("i= " + i);
                       String dynXpath = table_row + "[" + i +"]";
                                                       
                                                
                                                String td = dynXpath +  "//td" +"[" + column + "]";
                                                ele = driver.findElement(By.xpath(td))  ;
                                                
                                                String actual =  ele.getText().toLowerCase().replace(" ", "");
                                                String expected = (ExpectedValue.replace(" ", "").toLowerCase()); 
                                                
                                                if (actual.contains(expected)) {
                                                       System.out.println("found true for " + ExpectedValue);
                                                       
                                                       onPassUpdate(screenName, expected, actual, "Table verification for " + Column_name + "  for row "+i,
                                                                     "Table verification");
                     
                                                } else {
                                                       onFailUpdate(screenName, expected, actual, "Table verification for " + Column_name + "  for row "+i,
                                                                     "Table verification");

                                                }
                     }
       }
       
       /**
       * @author A-8468
       * Description... method verifies whether after applying filter for AWB only rows matching to data is displayed
       * @param shipmentPrefix : shipment prefix for AWB example 020/083
       * @param awbNumber : awb number for shipment example : 25625622
     * @throws IOException 
       **/
       public void verifyFilter_AWBNo(String shipmentPrefix, String awbNumber ) throws InterruptedException, IOException{
              enterValueInTextbox(sheetName, "inbx_awbPrefix;xpath", data(shipmentPrefix), "Carrier Code", screenName);
              enterValueInTextbox(sheetName, "inbx_awbNumber;xpath", data(awbNumber), "Awb Number", screenName);
              clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "spn_checkAll;xpath", "check Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
              waitForSync(5);     
              
              verifyEachRow("AWB/HAWB/ULD No.", data(awbNumber));
              
       }
       
       
       /**
       * @author A-8468
       * Description... method verifies whether after applying filter for FlightNo only rows matching to data is displayed
       * @param carrierCode : carrier code for flight example : LH
       * @param flightNumber : flight Number example : 6362L
     * @throws IOException 
       **/
    public void verifyFilter_FlightNo(String carrierCode, String flightNumber ) throws InterruptedException, IOException{

        enterValueInTextbox(sheetName, "inbx_carriercode;xpath", data(carrierCode), "Carrier Code", screenName);
        enterValueInTextbox(sheetName, "inbx_flightno;xpath", data(flightNumber), "Flight Number", screenName);
        clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "spn_checkAll;xpath", "check Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
              waitForSync(5);     
              
              verifyEachRow("Flight", data(flightNumber));
              
       }
    
    /**
       * @author A-8468
       * Description... method verifies whether after applying filter for SourceHA only rows matching to data is displayed
       * @param SourceHA : source handling area example : BD451
     * @throws IOException 
       **/
    public void verifyFilter_SourceHA(String SourceHA ) throws InterruptedException, IOException{

        enterValueInTextbox(sheetName, "inbx_SourceHA;id", data(SourceHA), "Source HA", screenName);
              clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
              waitForSync(5);     
              clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "spn_checkAll;xpath", "check Status", screenName);
              waitForSync(1);     
              
              verifyEachRow("Source HA", data(SourceHA));
              
       }
    
    /**
       * @author A-8468
       * Description... method verifies whether after applying filter for DestinationHA only rows matching to data is displayed
       * @param DestinationHA : destination handling area example : EIN
     * @throws IOException 
       **/
    public void verifyFilter_DestinationHA(String DestinationHA ) throws InterruptedException, IOException{

        enterValueInTextbox(sheetName, "inbx_DestinationHA;id", data(DestinationHA), "Destination HA", screenName);
              clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
              waitForSync(5);     
              clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "spn_checkAll;xpath", "check Status", screenName);
              waitForSync(1);     
              
              verifyEachRow("Dest. HA", data(DestinationHA));
              
       }
    
    /**
       * @author A-8468
       * Description... method verifies whether after applying filter for Status only rows matching to data is displayed
       * @param Status : Transport order status example : Open/Closed/In Progress
     * @throws IOException 
       **/
    public void verifyFilter_Status(String Status ) throws InterruptedException, IOException{
       clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "spn_uncheckAll;xpath", "uncheck Status", screenName);
              waitForSync(1);
              clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              selectOptionInList(sheetName, "btn_selectStatus;xpath", "chk_status;xpath", data(Status), "TO status");
              clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
              waitForSync(5);     
              
              verifyEachRow("Status", data(Status));
              
       }
    
    /**
       * @author A-8468
       * Description... method verifies whether after applying filter for AWBNo and Flight No only rows matching to data is displayed
       * @param shipmentPrefix : shipment prefix for AWB example 020/083
       * @param awbNumber : awb number for shipment example : 25625622
       * @param carrierCode : carrier code for flight example : LH
       * @param flightNumber : flight Number example : 6362L
     * @throws IOException 
       **/
    public void verifyFilter_AWBNo_FlightNo(String shipmentPrefix, String awbNumber,String carrierCode ,String flightNumber ) throws InterruptedException, IOException{
              enterValueInTextbox(sheetName, "inbx_awbPrefix;xpath", data(shipmentPrefix), "Shipment Prefix", screenName);
              enterValueInTextbox(sheetName, "inbx_awbNumber;xpath", data(awbNumber), "Awb Number", screenName);
              clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "spn_checkAll;xpath", "check Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
              waitForSync(5);     
              enterValueInTextbox(sheetName, "inbx_carriercode;xpath", data(carrierCode), "Carrier Code", screenName);
           enterValueInTextbox(sheetName, "inbx_flightno;xpath", data(flightNumber), "Flight Number", screenName);
              
              verifyEachRow("AWB/HAWB/ULD No.", data(awbNumber));
              verifyEachRow("Flight", data(flightNumber));
              
              
       }
    
    /**
       * @author A-8468
       * Description... method verifies whether after applying filter for SourceHA and DestinationHA only rows matching to data is displayed
       * @param SourceHA : source handling area example : BD451
        * @param DestinationHA : destination handling area example : EIN
     * @throws IOException 
        **/
    public void verifyFilter_SourceAndDestinationHA(String SourceHA,String DestinationHA ) throws InterruptedException, IOException{

       enterValueInTextbox(sheetName, "inbx_SourceHA;id", data(SourceHA), "Source HA", screenName);
        enterValueInTextbox(sheetName, "inbx_DestinationHA;id", data(DestinationHA), "Destination HA", screenName);
              clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
              waitForSync(5);     
              clickWebElement(sheetName, "btn_selectStatus;xpath", "Select Status", screenName);
              waitForSync(1);     
              clickWebElement(sheetName, "spn_checkAll;xpath", "check Status", screenName);
              waitForSync(1);     
              
              verifyEachRow("Source HA", data(SourceHA));
              verifyEachRow("Dest. HA", data(DestinationHA));
              
       }

	
	
}