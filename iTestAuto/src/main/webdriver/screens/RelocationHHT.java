package screens;



import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class RelocationHHT extends CustomFunctions {
	
	String sheetName = "RelocationHHT";
	String screenName = "RelocationHHT";
	

	public RelocationHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9175
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Relocation screen
	 */
	public void invokeRelocationScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Relocation");		
		clickActionInHHT("relhht_menu;xpath",proppathhht,"Relocation menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "Relocation hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "Relocation hht screen is not invoked successfully");
		}
	}

	  /**
	    	 * @author A-8783
	    	 * Desc- verify warning message and click yes button
	    	 * @throws IOException 
	    	 */
	    		public void verifyWarningAndClickYes(String warning) throws IOException {
	    			
	    			String locatorValue=getPropertyValue(proppathhht, "relhht_txt_warning;xpath");

	                locatorValue=locatorValue.replace("*", warning);
	    			
	    				int eleSize=androiddriver.findElements(By.xpath(locatorValue)).size();

	    				if(eleSize==1)
	    				{
	    					writeExtent("Pass","Verified the warning message");
	    		waitForSync(2);			clickActionInHHT("relhht_btn_Yes;xpath",proppathhht,"Yes button",screenName);
	    					waitForSync(1);
	    				}
	    				else
	    				{
	    					writeExtent("Fail","Could not verify the warning message");
	    				}
	    		}
	    		/**
	    		 * @author A-8783
	    		 * @param location
	    		 * @throws IOException
	    		 */
	    		public void enterDestLocation(String location) throws IOException {
	    			enterValueInHHT("relhht_inbx_destLoc;xpath",proppathhht,data(location),"Location",screenName);

	    		}


	/**
	 * @author A-9175
	 * @param awbNumber
	 * @throws AWTException
	 * @throws InterruptedException
	 * Description : Entering awb number in hht Screen
	 */
	public void enterValue(String value) throws AWTException, InterruptedException
	{
		try
		{
			enterValueInHHT("relhht_inbx_listValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
			waitForSync(5);
			clickActionInHHT("relhht_btn_Next;xpath",proppathhht,"Next",screenName);
			waitForSync(5);
			writeExtent("Pass", "Value "+ data(value)+" entered in Relocation hht screen");
			waitForSync(12);
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Value "+ data(value)+" not entered in "+screenName);
		}
		 
	}
	
	/**
     * @author A-9478
     * @throws IOException 
      * @Description: Select Destination location
     */
     public void selectDestinationLocation(String location) throws IOException
     {
       clickActionInHHT("relhht_btn_SelectDestinationLocation;xpath",proppathhht,"Destination location",screenName);
           waitForSync(2);
           try
           {
                 String locatorValue=getPropertyValue(proppathhht, "relhht_btn_SelectDestinationLocationValue;xpath");
                 locatorValue=locatorValue.replace("LOC", data(location));
                 androiddriver.findElement(By.xpath(locatorValue)).click();
                 writeExtent("Pass", "Selected destination location as "+data(location)+" in Relocation hht screen");
           }
           catch(Exception e)
           {
                 captureScreenShot("Android");
                 writeExtent("Fail", "Couldn't select destination location "+data(location)+" in Relocation hht screen");
           }
           
     }
     
     /**
     * @author A-9478
     * @throws IOException 
      * @Description: Click complete relocation button
     */
     public void clickCompleteRelocation() throws IOException
     {
           clickActionInHHT("relhht_btn_completeRelocation;xpath",proppathhht,"Complete Relocation",screenName);
           waitForSync(2);   
     }

/**
      * @author A-9478
      * Description: Select Location radio button
      * @param location
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException
      */
      public void selectLocationRadioButton(String location) throws AWTException, InterruptedException, IOException
      {
            
            try
        {
              String locatorValue=getPropertyValue(proppathhht, "relhht_btn_SelectLocation;xpath");
              locatorValue=locatorValue.replace("LOC", data(location));
              androiddriver.findElement(By.xpath(locatorValue)).click();
              clickActionInHHT("relhht_btn_Next;xpath",proppathhht,"Next",screenName);
                    waitForSync(5);
              writeExtent("Pass", "Selected location "+data(location)+" in Relocation hht screen");
        }
        catch(Exception e)
        {
              captureScreenShot("Android");
              writeExtent("Fail", "Couldn't select location "+data(location)+" in Relocation hht screen");
        }
            
            
      }
      
      /**
      * @author A-9478
      * Description : Entering awb number in hht Screen
      */
      public void enterValueWithoutNext(String value) throws AWTException, InterruptedException
      {
            try
            {
            	waitTillMobileElementDisplay(proppathhht,"relhht_inbx_listValue;accessibilityId","id",20);
               enterValueInHHT("relhht_inbx_listValue;accessibilityId",proppathhht,data(value),"List Value",screenName);
                  waitForSync(5);               
                  writeExtent("Pass", "Value "+ data(value)+" entered in Relocation hht screen");               
            }
            
            catch(Exception e)
            {
                  writeExtent("Fail", "Value "+ data(value)+" not entered in "+screenName);
            }
            
      }
      
      /**
      * @author A-9478
      * @param awbNumber
      * @throws AWTException
      * @throws InterruptedException
      * Description : Entering pieces in hht Screen
      * @throws IOException 
       */
      public void enterPieces(String pcs) throws AWTException, InterruptedException, IOException
      {           
            enterValueInHHT("relhht_inbx_Pieces;accessibilityId",proppathhht,data(pcs),"Pieces",screenName);
                  waitForSync(5);               
      }
      
      /**
      * @author A-9478 
       * Description : Entering destination SU in hht Screen
      * @throws IOException 
       */
      public void enterDestinationSU(String SU) throws AWTException, InterruptedException, IOException
      {           
            enterValueInHHT("relhht_inbx_destinationSU;accessibilityId",proppathhht,data(SU),"Destination SU",screenName);
                  waitForSync(5);               
      }

/**
      * @author A-9478
      * @throws IOException 
       * @Description: Select reason
      */
      public void selectReason(String reason) throws IOException
      {
        clickActionInHHT("relhht_btn_selectReason;xpath",proppathhht,"Reason",screenName);
            waitForSync(2);
            try
            {
                  String locatorValue=getPropertyValue(proppathhht, "relhht_btn_selectReasonValue;xpath");
                  locatorValue=locatorValue.replace("Reason", data(reason));
                  androiddriver.findElement(By.xpath(locatorValue)).click();
                  writeExtent("Pass", "Selected Reason as "+data(reason)+" in Relocation hht screen");
            }
            catch(Exception e)
            {
                  captureScreenShot("Android");
                  writeExtent("Fail", "Couldn't select reason "+data(reason)+" in Relocation hht screen");
            }
            
      }
      
      /**
       * @author A-9478
       * @throws IOException 
        * @Description: Select SCC
       */
       public void selectSCC(String scc) throws IOException
       {
         clickActionInHHT("relhht_btn_selectSCC;xpath",proppathhht,"SCC",screenName);
             waitForSync(2);
             try
             {
                   String locatorValue=getPropertyValue(proppathhht, "relhht_btn_selectSCCValue;xpath");
                   locatorValue=locatorValue.replace("SCC", data(scc));
                   androiddriver.findElement(By.xpath(locatorValue)).click();
                   waitForSync(3);
                   writeExtent("Pass", "Selected SCC as "+data(scc)+" in Relocation hht screen");
             }
             catch(Exception e)
             {
                   captureScreenShot("Android");
                   writeExtent("Fail", "Couldn't select SCC "+data(scc)+" in Relocation hht screen");
             }
             
       }

	public void clickStartRelocation() throws AWTException, InterruptedException, IOException
	{
		
			waitForSync(5);
			clickActionInHHT("relhht_btn_StartRelocation;xpath",proppathhht,"Start Relocation",screenName);
			waitForSync(12);
		
		
	}
	/**
	 * @author A-9478
	 * Desc: Click SU number
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOnValue(String value) throws AWTException, InterruptedException, IOException
	{
			waitForSync(3);
			clickActionInHHT("relhht_btn_ClickSU;xpath",proppathhht,data(value),screenName);
			waitForSync(3);		
	}
	
	/**
	 * @author A-9478
	 * Desc: Click on AWB number
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickOnAWB(String AWBNo) throws AWTException, InterruptedException, IOException
	{
		try
		{
			scrollInMobileDevice(data(AWBNo));
			String locator1=getPropertyValue(proppathhht, "relhht_btn_ClickAWB;xpath");	
			locator1=locator1.replace("AWBNO", data(AWBNo));
			androiddriver.findElement(By.xpath(locator1)).click();
			waitForSync(5);
			writeExtent("Pass", "Clicked on AWB "+data(AWBNo)+" in "+screenName);			
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't click on AWB "+data(AWBNo)+" in "+screenName);
		}
		
	}
	
	/**
	 * @author A-9478
	 * Desc: Verify AWB number
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifyAWB(String AWBNo) throws AWTException, InterruptedException, IOException
	{
		try
		{
			String locator1=getPropertyValue(proppathhht, "relhht_btn_ClickAWB;xpath");	
			locator1=locator1.replace("AWBNO", data(AWBNo));
			if(androiddriver.findElements(By.xpath(locator1)).size()>0)
			{
				writeExtent("Pass", "Verified AWB "+data(AWBNo)+" in "+screenName);
			}
			else
			{
				writeExtent("Fail", "Couldn't verify AWB "+data(AWBNo)+" in "+screenName);
			}
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Couldn't verify AWB "+data(AWBNo)+" in "+screenName);
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
                                   
           String locator1=getPropertyValue(proppathhht, "relhht_txt_StatedPieces;xpath");
           String StringPcs=androiddriver.findElement(By.xpath(locator1)).getText();
           String actualPcs = (StringPcs.split(" "))[0];
           if(actualPcs.equals(data(pcs)))
           {
                 writeExtent("Pass", "Verified Stated pieces "+data(pcs)+" in "+screenName);
           }
           else
           {
        	   captureScreenShot("Android");
                 writeExtent("Fail", "Failed to verify stated pieces for "+data(pcs)+" in "+screenName);
           }
           
           String locator2=getPropertyValue(proppathhht, "relhht_txt_StatedWeight;xpath");
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
      * Description : Verify Available Pieces and weight
      * @throws IOException 
       */
      public void verifyAvailablePiecesWeight(String pcs,String wt) throws IOException
      {
                                    
            String locator1=getPropertyValue(proppathhht, "relhht_txt_AvailablePieces;xpath");
            String StringPcs=androiddriver.findElement(By.xpath(locator1)).getText();
            String actualPcs = (StringPcs.split(" "))[0];
            if(actualPcs.equals(data(pcs)))
            {
                  writeExtent("Pass", "Verified availabe pieces "+data(pcs)+" in "+screenName);
            }
            else
            {
         	   captureScreenShot("Android");
                  writeExtent("Fail", "Failed to verify available pieces for "+data(pcs)+" in "+screenName);
            }
            
            String locator2=getPropertyValue(proppathhht, "relhht_txt_AvailableWeight;xpath");
            String StringWgt=androiddriver.findElement(By.xpath(locator2)).getText();
            String actualWgt = (StringWgt.split(" "))[0];
            if(actualWgt.equals(data(wt)))
            {
                  writeExtent("Pass", "Verified available weight "+data(wt)+" in "+screenName);
            }
            else
            {
           	 captureScreenShot("Android");
                  writeExtent("Fail", "Failed to verify available weight for "+data(wt)+" in "+screenName);
            }
 }
      
      
	
	
}

