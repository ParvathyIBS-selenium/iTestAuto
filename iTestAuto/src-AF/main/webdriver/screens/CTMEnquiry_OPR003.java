package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.server.handler.SwitchToFrame;
import org.openqa.selenium.remote.server.handler.SwitchToWindow;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CTMEnquiry_OPR003 extends CustomFunctions {

	public CTMEnquiry_OPR003(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	
	}

	

	public String sheetName = "CTMEnquiry_OPR003";
	public String ScreenName = "CTM Enquiry";
	String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
	
	
	
	/**
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void verifyTableDetails(int verfCols[], String actVerfValues[],
			String pmKey) throws InterruptedException, IOException {
		waitForSync(2);
		verify_tbl_records_multiple_cols(sheetName, "table_listCTM;xpath","//td", verfCols, pmKey, actVerfValues);
	}
	
	/** Select the shipment and click on details button
     * @author A-7037
     * @param AWBNo
     * @throws Exception 
      */
	/**
     * Desc clicking Details Button
     * @author A-9175
     * @throws InterruptedException
     * @throws IOException
     */
    public void clickDetails() throws InterruptedException, IOException {
          waitForSync(2);
          clickWebElement(sheetName, "btn_Details;id", "Details Button", ScreenName);
          waitForSync(4);
          
    }
    
    /**
     * Desc Verifying Delete button is Disabled
     * @author A-9175
     * @throws InterruptedException
     * @throws IOException
     */
    public void verifyDeleteButtonStatus() throws InterruptedException, IOException {
          waitForSync(2);
          String locator = xls_Read.getCellValue(sheetName, "btn_Delete;xpath");
          boolean expStatus=true;
          try{
          boolean actualStatus=driver.findElement(By.xpath(locator)).isDisplayed(); 
          System.out.println(actualStatus);
          if(actualStatus==expStatus)
          {
                 System.out.println("Delete Button is Disabled "+ ScreenName + " Page");
                 writeExtent("Pass", "Delete Button is Disabled "+ ScreenName + " Page"+ ScreenName + " Page");
                 Assert.assertFalse(false, "Delete Button is Disabled "+ ScreenName + " Page" + ScreenName + " Page");
          }
          }catch(Exception e){
           
          System.out.println("Delete Button is Enabled "+ ScreenName + " Page");
          writeExtent("Fail", "Delete Button is Enabled "+ ScreenName + " Page"+ ScreenName + " Page");
          Assert.assertFalse(true, "Delete Button is Enabled "+ ScreenName + " Page" + ScreenName + " Page");
          
     }
          waitForSync(4);
          
    }
    
   /* Desc : Select the shipment from CMT003 screen
    * @author A-9175
    * @param AWBno
    * @throws InterruptedException
    */
   public void selectShipment(String AWBno) throws InterruptedException {

         String locator = xls_Read.getCellValue(sheetName, "chk_selectAWB;xpath");
       locator=locator.replace("AWBno",data(AWBno));
       System.out.println(locator);
         try{
         driver.findElement(By.xpath(locator)).click();                    
         
         }catch(Exception e){
          
         System.out.println("Could not click on" + data(AWBno) + "checkox on " + ScreenName + " Page");
         writeExtent("Fail", "Could not click on" + data(AWBno) + "checkox on " + ScreenName + " Page");
         Assert.assertFalse(true, "Could not click on" + data(AWBno) + "checkox on " + ScreenName + " Page");
          
    }
   }

       public void selectShipmentclickDetails(String awbno) throws Exception {
              
           checkIfUnchecked(sheetName, "chk_CTMref;xpath", "CTM checkbox", ScreenName);
           clickWebElement(sheetName, "btn_details;xpath", "Details button", ScreenName);
           waitForSync(4);

       }

}
