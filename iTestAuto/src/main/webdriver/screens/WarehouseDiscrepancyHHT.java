package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class WarehouseDiscrepancyHHT extends CustomFunctions {
	
	String sheetName = "WarehouseDiscrepancyHHT";
	String screenName = "WarehouseDiscrepancyHHT";
	

	public WarehouseDiscrepancyHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoke warehouse discrepancy HHT screen
	 */
	public void invokeWarehouseDiscrepancyScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Warehouse Discrepancy");	
		clickActionInHHT("wareDischht_menu;xpath",proppathhht,"Warehouse Discrepancy menu",screenName);
	
		waitForSync(2);
		writeExtent("Pass", "Warehouse Discrepancy hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Warehouse discrepancy hht screen is not invoked successfully");
		}
	}
	/**
     * @author A-9478
     * @param pcs
     * @param wt
     * @param dimension
     * Description : Verify Stated Pieces and weight
     * @throws IOException 
      */
     public void verifyStatedPiecesWeight(String pcs,String wt) throws IOException
     {
                                   
           String locator1=getPropertyValue(proppathhht, "gahht_txt_StatedPieces;xpath");
           String StringPcs=androiddriver.findElement(By.xpath(locator1)).getText();
           String actualPcs = (StringPcs.split(" "))[0];
           if(actualPcs.equals(data(pcs)))
           {
                 writeExtent("Pass", "Verified Stated pieces "+data(pcs)+" in "+screenName);
           }
           else
           {
                 writeExtent("Fail", "Failed to verify stated pieces for "+data(pcs)+" in "+screenName);
           }
           
           String locator2=getPropertyValue(proppathhht, "gahht_txt_StatedWeight;xpath");
           String StringWgt=androiddriver.findElement(By.xpath(locator2)).getText();
           String actualWgt = (StringWgt.split(" "))[0];
           if(actualWgt.equals(data(wt)))
           {
                 writeExtent("Pass", "Verified Stated weight "+data(wt)+" in "+screenName);
           }
           else
           {
             captureScreenShot("Android");
                 writeExtent("Fail", "Failed to verify stated weight for "+data(wt)+" in "+screenName);
           }
}
     
    
      
      /**
       * @author A-9478        
       * Description : Verify origin and destination
       * @throws IOException 
        */
       public void verifyOriginAndDestination(String AWBNO,String Origin, String Destination) throws IOException
       {
                                     
             String locator1=getPropertyValue(proppathhht, "gahht_txt_Origin;xpath");
             locator1 = locator1.replace("AWBNo", data(AWBNO));
            
             String actualOrigin=androiddriver.findElement(By.xpath(locator1)).getText();        
             if(actualOrigin.equals(data(Origin)))
             {
                   writeExtent("Pass", "Verified origin "+data(Origin)+" in "+screenName);
             }
             else
             {
               captureScreenShot("Android");
                   writeExtent("Fail", "Failed to verify origin for "+data(Destination)+" in "+screenName);
             }
             String locator2=getPropertyValue(proppathhht, "gahht_txt_Destination;xpath");
             locator2 = locator2.replace("AWBNo", data(AWBNO));
             String actualDest=androiddriver.findElement(By.xpath(locator2)).getText();        
             if(actualDest.equals(data(Destination)))
             {
                   writeExtent("Pass", "Verified Destination "+data(Destination)+" in "+screenName);
             }
             else
             {
               captureScreenShot("Android");
                   writeExtent("Fail", "Failed to verify destination for "+data(Destination)+" in "+screenName);
             }
             
  }
       
       /**
        * @author A-9478        
        * Description : Verify origin and destination
        * @throws IOException 
         */
        public void verifySCC(String AWBNo,String SCC) throws IOException
        {
                                      
              String locator1=getPropertyValue(proppathhht, "gahht_txt_SCC;xpath");
              locator1 = locator1.replace("AWBNo", data(AWBNo));
              String actualSCC=androiddriver.findElement(By.xpath(locator1)).getText();        
              if(actualSCC.equals(data(SCC)))
              {
                    writeExtent("Pass", "Verified SCC "+data(SCC)+" in "+screenName);
              }
              else
              {
                  captureScreenShot("Android");
                    writeExtent("Fail", "Failed to verify SCC for "+data(SCC)+" in "+screenName);
              }                             
   }


	/**
	 * @author A-9478
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered awb number in hht
	 * @throws IOException 
	 */
	public void enterAwbNumber(String awbNumber) throws AWTException, InterruptedException, IOException
	{
		
			enterValueInHHT("wareDischht_inbx_Awb;accessibilityId",proppathhht,data(awbNumber),"Awb Number",screenName);
			waitForSync(1);
			//androiddriver.findElement(By.xpath("//android.view.ViewGroup[@content-desc='lbl_next']")).click();
			waitForSync(10);
		
		 
	}
	
	/**
	 * @author A-9478
	 * @param pieces
	 * @param weight
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : entered pieces and weight
	 */
	public void enterPiecesAndWeight(String pieces,String weight) throws AWTException, InterruptedException
	{
		try
		{
			
			waitForSync(5);
			enterValueInHHT("wareDischht_inbx_Pcs;accessibilityId",proppathhht,data(pieces),"Pieces",screenName);
			enterValueInHHT("wareDischht_inbx_Wt;accessibilityId",proppathhht,data(weight),"Weight",screenName);
		    writeExtent("Pass", "Entered pieces : "+data(pieces)+" weight : "+data(weight));
		    waitForSync(2);
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Cound not enter the pieces and weight in "+screenName);
		}
	}
	
	
	
	/**
	 * @author A-9478
	 * Description : Enter remarks
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public void enterRemarks(String remarks) throws InterruptedException, IOException
	{
		
		
	  enterValueInHHT("wareDischht_inbx_Remarks;accessibilityId",proppathhht,data(remarks),"Remarks",screenName);
		waitForSync(1);
		
		
	}
		
	/**
	 * @author A-9478
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSave() throws AWTException, InterruptedException, IOException
	{
		
			
			clickActionInHHT("btn_Save2;xpath",proppathhht,"Save",screenName);	
			waitForSync(5);
			verifyHHTSaveDetails(screenName);
		 
	}
	
	/**
	 * @author A-9478
	 * Description : Select Discrepancy code
	 * @throws InterruptedException 
	 */
	public void selectDiscrepancyCode(String DiscrepancyCode) throws InterruptedException
	{
		
		try
		{
			 clickActionInHHT("wareDischht_btn_discrepancyCode;xpath",proppathhht,"Discrepancy Code",screenName);	
		waitForSync(2);
		androiddriver.findElement(By.xpath("//android.widget.TextView[@text='"+DiscrepancyCode+"']")).click();
		writeExtent("Pass", "Discrepancy code "+DiscrepancyCode+" is entered "+screenName);
		}
		
		catch(Exception e)
		{
			 writeExtent("Fail", "Failed to enter Discrepancy code "+DiscrepancyCode+screenName);
		}
		
	}
	
	
}
