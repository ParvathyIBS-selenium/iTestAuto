package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class DimensionCaptureHHT  extends CustomFunctions {
	
	String sheetName = "DimensionCaptureHHT";
	String screenName = "DimensionCaptureHHT";
	

	public DimensionCaptureHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht dimension capture screen
	 * @throws IOException 
	 */
	public void invokeDimensionCaptureScreen() throws InterruptedException, AWTException, IOException {

			try
		{
			
		scrollInMobileDevice("Dimension Capture");	
		clickActionInHHT("dimhht_menu;xpath",proppathhht,"Dimension Capture menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Dimension Capture hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
			captureScreenShot("Android");
		writeExtent("Fail", "Dimension Capture hht screen is not invoked successfully");
		}
	}
	
	/**
	 * @author A-7271
	 * @param value
	 * Desc : Enter the list value
	 * @throws IOException 
	 */
	public void enterValue(String value) throws IOException
	{
		enterValueInHHT("dimhht_inbx_Awb;accessibilityId",proppathhht,data(value),"List Value",screenName);
	    waitForSync(12);
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
      * @param pcs
      * @param wt
      * @param dimension
      * Description : Verify Accepted Pieces and weight
      * @throws IOException 
       */
      public void verifyAcceptedPiecesWeight(String pcs,String wt) throws IOException
      {
                                    
            String locator1=getPropertyValue(proppathhht, "gahht_txt_AcceptedPieces;xpath");
            String StringPcs=androiddriver.findElement(By.xpath(locator1)).getText();
            String actualPcs = (StringPcs.split(" "))[0];
            if(actualPcs.equals(data(pcs)))
            {
                  writeExtent("Pass", "Verified Accepted pieces "+data(pcs)+" in "+screenName);
            }
            else
            {
            	captureScreenShot("Android");
                  writeExtent("Fail", "Failed to verify accepted pieces for "+data(pcs)+" in "+screenName);
            }
            
            String locator2=getPropertyValue(proppathhht, "gahht_txt_AcceptedWeight;xpath");
            String StringWgt=androiddriver.findElement(By.xpath(locator2)).getText();
            String actualWgt = (StringWgt.split(" "))[0];
            if(actualWgt.equals(data(wt)))
            {
                  writeExtent("Pass", "Verified Accepted weight "+data(wt)+" in "+screenName);
            }
            else
            {
            	captureScreenShot("Android");
                  writeExtent("Fail", "Failed to verify accepted weight for "+data(wt)+" in "+screenName);
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
	 * @author A-7271
	 * @param pcs
	 * @param wt
	 * @param dimension
	 * Description : Enter the dimension details
	 * @throws IOException 
	 */
	public void enterDimensionDetails(String pcs,String wt,String dimension) throws IOException
	{
		
		try
		{
	
		
		clickActionInHHT("gahht_btn_dimensionCapture;xpath",proppathhht,"Dimension capture",screenName);
		waitForSync(5);
		enterValueInHHT("gahht_inbx_dimPcs;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
		enterValueInHHT("gahht_inbx_dimWt;accessibilityId",proppathhht,data(wt),"Weight",screenName);
		//Dimensions
		System.out.println(data(dimension).split(",")[0]);
		enterValueInHHT("gahht_inbx_dimLen;accessibilityId",proppathhht,data(dimension).split(",")[0],"Length",screenName);
		enterValueInHHT("gahht_inbx_dimWidth;accessibilityId",proppathhht,data(dimension).split(",")[0],"Width",screenName);
		enterValueInHHT("gahht_inbx_dimHeight;accessibilityId",proppathhht,data(dimension).split(",")[0],"Height",screenName);
	
		 waitForSync(1);
		 writeExtent("Pass", "Entered dimension details in "+screenName);
		
		}
		
		catch(Exception e)
		{
			captureScreenShot("Android");
			 writeExtent("Fail", "Failed to entered dimension details in "+screenName);
		}
	}
	/**
     * @author A-7271
     * Desc : verify AWB not accepted pop up in HHT
     */
     public void verifyAWBNotAcceptedPopUp() throws IOException
     {
           
           try
           {
               int size=getSizeOfMobileElement("dimhht_btn_AWBNotAcceptedPopup;xpath",proppathhht);
                 waitForSync(2);
                 
                 if(size==1)
                 {
                 writeExtent("Pass", "'AWB is not accepted' pop up is displaying in "+screenName);
                 }
                 else
                 {
                	 captureScreenShot("Android");
                       writeExtent("Fail", "Failed to display 'AWB is not accepted' pop up in "+screenName);
                 }
           }
           
           catch(Exception e)
           {     
                 captureScreenShot("Android");
                 writeExtent("Fail", "Failed to display 'AWB is not accepted' pop up in "+screenName);
           }
     }

	/**
	 * @author A-7271
	 * Desc : Click save button
	 * @throws IOException 
	 */
	public void saveDetails() throws IOException
	{
		
		try
		{
			clickActionInHHT("dimhht_btn_Save;xpath",proppathhht,"Save",screenName);
			waitForSync(10);
			
		    int size=getSizeOfMobileElement("dimhht_txt_msgConfimation;xpath",proppathhht);
			
			/*** CLOSE CONFIRMATION MESSAGE**/
  	     clickActionInHHT("dimhht_btn_msgConfirmation;xpath",proppathhht,"Close confirmation message",screenName);	
  	   
			
			waitForSync(2);
			
			if(size==1)
			{
			 writeExtent("Pass", "Details saved successfully in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				 writeExtent("Fail", "Details not saved successfully in "+screenName);
			}
		}
		
		catch(Exception e)
		{
			captureScreenShot("Android");
			 writeExtent("Fail", "Details not saved successfully in "+screenName);
		}
	}
	
	
}
