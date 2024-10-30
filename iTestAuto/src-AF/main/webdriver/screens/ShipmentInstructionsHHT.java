/**
 * 
 */
package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

/**
 * @author A-7271
 *
 */
public class ShipmentInstructionsHHT extends CustomFunctions {
	
	String sheetName = "ShipmentInstructionsHHT";
	String screenName = "ShipmentInstructionsHHT";
	

	public ShipmentInstructionsHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht shipment instructions screen
	 */
	public void invokeShipmentInstructionsScreen() throws InterruptedException, AWTException {

			try
		{
		scrollInMobileDevice("Shipment Instructions");	
		clickActionInHHT("shipinshht_menu;xpath",proppathhht,"Security and screening menu",screenName);
		waitForSync(2);
		writeExtent("Pass", "ShipmentInstructions hht screen is invoked successfully");
		}
		
		catch(Exception e)
		{
		writeExtent("Fail", "ShipmentInstructions hht screen is not invoked successfully");
		}
	}
	
	/**
	 * @author A-7271
	 * @param awbNumber
	 * Desc : enter awb number
	 * @throws IOException 
	 */
	public void enterAwb(String awbNumber) throws IOException
	{
		   enterValueInHHT("shipinshht_inbx_Awb;accessibilityId",proppathhht,data(awbNumber),"Awb Number",screenName);
		   waitForSync(12);
	}
	
	/**
	 * @author A-7271
	 * @param embargoRef
	 * Desc : verify embargo details
	 */
	public void verifyEmbargoDetails(String embargoRef)
	{
		int embargo;
		int embargoRefName;
		String locatorValue;
		try
		{
			embargo=getSizeOfMobileElement("shipinshht_btn_Embargo;xpath",proppathhht);
			
			if(embargo==1)
			{
				writeExtent("Pass", "Embargo details listed in "+screenName);
				clickActionInHHT("shipinshht_btn_Embargo;xpath",proppathhht,"Embargo",screenName);
				waitForSync(1);
				locatorValue=getPropertyValue(proppathhht, "shipinshht_label_Embargo;xpath");
				locatorValue=locatorValue.replace("Embargo", data(embargoRef));
				embargoRefName=androiddriver.findElements(By.xpath(locatorValue)).size();
				if(embargoRefName==1)
				{
					writeExtent("Pass", "Embargo Ref Name is getting listed as expected , Ref Name :"+data(embargoRef)+screenName);
				}
				else
				{
					writeExtent("Fail", "Embargo Ref Name is not getting listed as expected , Ref Name :"+data(embargoRef)+screenName);
				}
				
				
				
			}
			else
			{
				writeExtent("Fail", "Embargo details not getting listed in "+screenName);
			}
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Embargo details not getting listed in "+screenName);
		}
	}
	
	/**
	 * @author A-7271
	 * Desc : verify shipment instruction details
	 */
	public void verifyShipInsDetails()
	{
		int shipIns;
		try
		{
		shipIns=getSizeOfMobileElement("shipinshht_btn_Instructions;xpath",proppathhht);
		
		if(shipIns==1)
		{
			writeExtent("Pass", "Shipment instruction button is getting displayed in "+screenName);
		}
		else
		{
			writeExtent("Fail", "Shipment instruction button is not getting displayed in "+screenName);
		}
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Shipment instruction button is not getting displayed in "+screenName);
		}
	}
	/**
     * @author A-9478
     * @param awbNumber
     * Desc : click awb number
     * @throws IOException 
      */
     public void clickAWB(String awbNumber) throws IOException
     {
           try
           {
                 String locator1=getPropertyValue(proppathhht, "shipinshht_btn_AWBNo;xpath");
                 locator1 = locator1.replace("AWBNO", data(awbNumber));
                 androiddriver.findElement(By.xpath(locator1)).click();   
                 waitForSync(3);
                 writeExtent("Pass", "Clicked on AWB Number "+data(awbNumber)+" in "+screenName);
           }
           catch(Exception e)
           {
                 captureScreenShot("Android");
                 writeExtent("Fail", "Couldn't click on AWB Number "+data(awbNumber)+" in "+screenName);
           }
           
     }
     /**
     * @author A-9478
     * @param flightNo
     * Desc : verify flight number
     * @throws IOException 
      */
     public void verifyFlightNo(String flightNo) throws IOException
     {
           try
           {
                 String locator1=getPropertyValue(proppathhht, "shipinshht_txt_flightno;xpath");
                 String s = androiddriver.findElement(By.xpath(locator1)).getText();   
                 String actualFlightNo=s.replace(" ", "");
                 if(actualFlightNo.equals(data(flightNo)))
                 {
                       writeExtent("Pass", "Successfully verified flight number "+data(flightNo)+" in "+screenName);
                 }
                 
           }
           catch(Exception e)
           {
                 captureScreenShot("Android");
                 writeExtent("Fail", "Couldn't verify flight number "+data(flightNo)+" in "+screenName);
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
                                  
          String locator1=getPropertyValue(proppathhht, "shipinshht_txt_StatedPieces;xpath");
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
          
          String locator2=getPropertyValue(proppathhht, "shipinshht_txt_StatedWeight;xpath");
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
                                    
            String locator1=getPropertyValue(proppathhht, "shipinshht_txt_Origin;xpath");
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
            String locator2=getPropertyValue(proppathhht, "shipinshht_txt_Destination;xpath");
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
       * Description : Verify SCC
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
	 * @author A-7271
	 * Desc : Verify reject button
	 */
	public void verifyRejectButton()
	{
		int rejctBtn;
		
		try
		{
			rejctBtn=getSizeOfMobileElement("shipinshht_btn_Reject;xpath",proppathhht);	
			if(rejctBtn==1)
			{
				writeExtent("Pass", "Reject button is getting displayed in "+screenName);
			}
			else
			{
				writeExtent("Fail", "Reject button is not getting displayed in "+screenName);
			}
			
		}
		
		catch(Exception e)
		{
			writeExtent("Fail", "Reject button is not getting displayed in "+screenName);
		}
	}
	
	/**
	 * @author A-7271
	 * Desc : Click reject button
	 * @throws IOException 
	 */
	public void clickRejectButton() throws IOException
	{
		clickActionInHHT("shipinshht_btn_Reject;xpath",proppathhht,"Reject",screenName);
		waitForSync(10);
	}
}
