package screens;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import com.relevantcodes.extentreports.LogStatus;

import common.BaseSetup;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListScaledULD_OPR355 extends CustomFunctions {
	public ListScaledULD_OPR355(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		// TODO Auto-generated constructor stub
	}
	String sheetName = "ListScaledULD_OPR335";
	String screenName = "ListScaledULD_OPR335";
	String screenId="OPR355";
	/**
	 * Description... List ULD
	 * @param ULDno
	 * @param startdate
	 * @param enddate
	 * @throws Exception
	 */
	public void listULD(String ULDno,String startdate ,String enddate) throws Exception
	{
		System.out.println(data(ULDno)+data(startdate)+data(enddate));
		
		enterValueInTextbox(sheetName, "inbx_uldno;xpath", data(ULDno), "ULD no", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_startdate;xpath", data(startdate), "Start Date", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_enddate;xpath", data(enddate), "End Date", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_save;xpath", "List Button", screenName);
		waitForSync(3);
	}
	/**
	 * Description... Verify Error Message When Contour Checked
	 * @param ErrorMsgForContourMismatach
	 * @throws Exception
	 */
	      /*
      * A-8705 Verifies Error message when contour mismatch checked
      */
      public void verifyErrorMsgWhenContourChecked(String ErrorMsgForContourMismatach) throws Exception {
            listULDWithContourMismatchCheckd("uldNumber","ShippingDate" ,"FlightDate");      
            waitForSync(10);
            verifyErrorMessage(screenName, ErrorMsgForContourMismatach);

      }

	/**
	 * Author : 7271
	 * Description : Check contour mismatch check box
	 * @throws InterruptedException 
	 */
	public void listULDWithContourMismatchCheckd(String ULDno,String startdate ,String enddate) throws Exception
	{
		System.out.println(data(ULDno)+data(startdate)+data(enddate));
		
		enterValueInTextbox(sheetName, "inbx_uldno;xpath", data(ULDno), "ULD no", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_startdate;xpath", data(startdate), "Start Date", screenName);
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_enddate;xpath", data(enddate), "End Date", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "chk_contourMismatch;name", "Contour mismatch checkbox", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "btn_save;xpath", "List Button", screenName);
		waitForSync(3);
	}
	/**
	 * Description... verify tbl records
	 * @param sheetName
	 * @param locator
	 * @param tableTag
	 * @param verfCols
	 * @param pmyKey
	 * @param actVerfValues
	 * @throws IOException 
	 */
	/*A-8705 
 * Verifies Multiple records in OPR355 screen
*/
      public void verify_tbl_records_multiple_colsForOPR355(String sheetName,
                  String locator, String tableTag, int verfCols[], String pmyKey,
                  String actVerfValues[]) throws IOException {
            try {
                  boolean flag = false;
                  int row = 0;
                  String ScreenName = sheetName.split("_")[0];
                  // get the required row
                  String tableBody = xls_Read.getCellValue(sheetName, locator);
                  List<WebElement> rows = driver.findElements(By.xpath(xls_Read
                              .getCellValue(sheetName, locator)));
                  String dynXpath = xls_Read.getCellValue(sheetName, locator)
                              + tableTag;

                  System.out.println("row size  " + rows.size());
                  switch (tableTag) {
                  case "//td":
                  {
                        rows = driver.findElements(By.xpath(tableBody));
                        dynXpath = tableBody + tableTag;
                        {
                              for (int i = 0; i <= rows.size(); i++) {
                                    System.out.println("i= " + i);

                                    if (rows.get(i)
                                                .getText()
                                                .toLowerCase()
                                                .replace(" ", "")
                                                .contains(pmyKey.toLowerCase().replace(" ", "")) == false) {

                                          flag = true;

                                    }

                                    if (flag) {
                                          row = i + 1;
                                          break;
                                    }
                              }
                              System.out.println("row = " + row);
                              for (int i = 0; i < verfCols.length; i++) {

                                    dynXpath = "(" + tableBody + ")[" + row + "]"
                                                + tableTag + "[" + verfCols[i] + "]";
                                    WebElement ele = null;

                                    ele = driver.findElement(By.xpath(dynXpath));

                                    String actual = ele.getText().toLowerCase()
                                                .replace(" ", "");
                                    String expected = (actVerfValues[i].replace(" ", "")
                                                .toLowerCase());
                                    if (expected.contains(actual)) {
                                          System.out.println("found true for "
                                                      + actVerfValues[i]);

                                          onPassUpdate(ScreenName, expected, actual,
                                                      "Table verification against " + pmyKey
                                                                  + " On ", "Table verification");

                                    } else {
                                          onFailUpdate(ScreenName, expected, actual,
                                                      "Table verification against " + pmyKey
                                                                  + " On ", "Table verification");

                                    }

                              }

                        }
                  }
                        break;
                  }
            } catch (Exception e) {
                  retryCount = retryCount + 1;

                  if (retryCount <= 3) {
                        verify_tbl_records_multiple_cols(sheetName, locator, tableTag,
                                    verfCols, pmyKey, actVerfValues);
                  }

                  else {

                        test.log(LogStatus.FAIL,
                                    "Could not perform table record verification");
                        System.out
                                    .println("Table contents are not verified or verification failed");
                        Assert.assertFalse(true,
                                    "Could not perform table record verification");
                  }

            }
      }

	/*A-8705
      *Verifies warning message if all the checkboxes are unchecked
      */
   /**
    * Description...   Verify Warning Msg Is Displayed For Deselection
    * @param expectedMsg
    * @throws InterruptedException
 * @throws IOException 
    */
      public void verifyWarningMsgIsDisplayedForDeselection(String expectedMsg) throws InterruptedException, IOException {
            clickWebElement(sheetName, "btn_settings;xpath", "settings Button", screenName);
            String actualText= deselectMultipleCheckboxesandHandleAlert(sheetName,"checkbox_columns;xpath");
            waitForSync(3);
            verifyScreenText(
                        sheetName,
                        data(expectedMsg),
                        actualText,
                        "Warning Message_deselection of all columns",
                        "//1. Login to iCargo \n , 2.Complete ULD TO Creation\n ,3.Invoke OPR355 screen \n 4.Deselect all columns in settings \n ");
      }
/**
 * Description... Get Weighing Date
 * @return
 * @throws InterruptedException
 */
      /*A-8705
      * Gives weighing date and time
      */
      public String getWeighingDate() throws InterruptedException {
            String weighingDate=getElementText(sheetName, "inbx_weighingDate;xpath", "weighing date and Time", screenName);
            return weighingDate;
      }

/**
 * Description... Verify ULD TO
 * @param uldNumber
 * @throws InterruptedException
 */

/*
      * A-8705 Verfies ULD TO created
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
 * Description... Select All Columns
 * @throws InterruptedException
 * @throws IOException 
 */
/*A-8705
      * Selects all columns in settings icon
      */
      public void selectAllColumns() throws InterruptedException, IOException {
            clickWebElement(sheetName, "btn_settings;xpath", "settings Button", screenName);
            selectMultipleCheckboxes(sheetName,"checkbox_columns;xpath");
            waitForSync(10);
            clickWebElement(sheetName, "btn_saveSettings;xpath", "Save button", screenName);
            
      }

/**
 * Description... Deselect Columns
 * @param k
 * @throws InterruptedException
 * @throws IOException 
 */

      /*A-8705
      * Deselects ULDNumber and Flight Number columns
      */
      public void deselectColumns(int k) throws InterruptedException, IOException {
            clickWebElement(sheetName, "btn_settings;xpath", "settings Button", screenName);
        deselectCheckboxes(sheetName,"checkbox_columns;xpath",k);
            waitForSync(10);
            clickWebElement(sheetName, "btn_saveSettings;xpath", "Save button", screenName);      
      }
/**
 * Description... Verify ULD Details
 * @param verfCols
 * @param actVerfValues
 * @param pmKey
 * @throws Exception
 */
	public void verifyULDDetails(int verfCols[],String actVerfValues[],String pmKey) throws Exception
	{
		waitForSync(4);
		verify_tbl_records_multiple_cols(sheetName, "table_ulddetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}
/**
 * Description... click Table Expand
 * @throws Exception
 */
	public void tableExpand()throws Exception
	{
		waitForSync(3);
		clickWebElement(sheetName, "btn_expand;xpath", "Expand Button", screenName);
	}
/**
 * Description... Export To Excel
 * @throws Exception
 */
	public void exportToExcel()throws Exception
	{
		
		clickWebElement(sheetName, "icon_excelExport;xpath", "Export To Excel Button", screenName);
		waitForSync(10);
	}
/**
 * Description... Verify Message Details
 * @param verfCols
 * @param actVerfValues
 * @throws InterruptedException
 */
	public void verifyMessageDetails(int verfCols[],String actVerfValues[]
            ) throws InterruptedException {
     waitForSync(4);
     verify_col_records(sheetName, "innerText","htmlDiv_messageDetails;xpath", verfCols, actVerfValues);
}

}
