package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class AWBClearance_OPR023 extends CustomFunctions
{

	public AWBClearance_OPR023(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="AWBClearance_OPR023";
	public String screenName="AWBClearance_OPR023";
	
	/**
	 * Description...  List AWB Number
	 * @param stationCode
	 * @param AWBNumber
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listAWB(String stationCode, String AWBNumber) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_shipmentPrefix;xpath", data(stationCode), "Station code", "AWB Clearance");
		enterValueInTextbox(sheetName, "inbx_awbNumber;xpath", data(AWBNumber), "AWB number", "AWB Clearance");
		clickWebElement(sheetName, "btn_listAwb;xpath", "List Button", "AWB Clearance");
		waitTillScreenloadWithOutAssertion("AWBClearance_OPR023", "btn_blockRelease;xpath","Release Button", "AWB Clearance");
		waitForSync(2);
		
	}
	 /**
 	 * @author A-9847
 	 * Desc..verify block released w.r.t given station
 	 * @param BlockType
 	 * @param AWB
 	 * @throws Exception
 	 */
 	public void verifyBlockReleasedForShipment(String BlockType, String AWB,String airport) throws Exception
 	{
 		int n = driver.findElements(By.xpath("//input[@name='blockId']")).size();
 		try
 		{
 			for(int i=1;i<=n;i++)
 			{
 				String blockTypelocator = xls_Read.getCellValue(sheetName, "txt_blockedType;xpath");
 				blockTypelocator=blockTypelocator.replace("*",Integer.toString(i));
 				String blockStatuslocator = xls_Read.getCellValue(sheetName, "txt_blockedStatus;xpath");
 				blockStatuslocator=blockStatuslocator.replace("*",Integer.toString(i)); 
 				
 				String airportlocator = xls_Read.getCellValue(sheetName, "txt_airport;xpath");
 				airportlocator=airportlocator.replace("*",Integer.toString(i)); 
 				
 				if(driver.findElement(By.xpath(blockTypelocator)).getText().contains(BlockType) && driver.findElement(By.xpath(airportlocator)).getText().contains(airport))
 				{
 					if((driver.findElement(By.xpath(blockStatuslocator)).getText().contains("Released")))
 					{
 						onPassUpdate(screenName,"Block released","Block released", "Verification of Block status","Verification of Block status");
 					} else {
 						String status=driver.findElement(By.xpath(blockStatuslocator)).getText();
 						onFailUpdate(screenName, "Block released. Status is "+status,"Block not released", "Verification of Block status","Verification of Block status");
 					}
 				}
 			}

 		}
 		catch(Exception e)
 		{
 			writeExtent("Fail", "Could not verify block status in "+screenName);
 		}
 	}
	/**
	 * @author A-9847
	 * @Desc To click a particular row-checkbox by giving a unique value and release block
	 * @param AWBNo
	 * @throws Exception 
	 */
	public void selectCheckboxandReleaseBlock(String pmKey,String remarks) throws Exception {
		
		String station="";
		try
		{
			station=getLoggedInStation("OPR023");
		}
		catch(Exception e)
		{
			writeExtent("Fail", "Failed to retrieve logged in station "+screenName);
		}
		/***if(station.contains("AMS"))
		{
			pmKey="val~Customs";
		}***/

		String xpath = xls_Read.getCellValue(sheetName, "chbx_details;xpath").replace("*", data(pmKey));
		int weElements = driver.findElements(By.xpath(xpath)).size();


		if(weElements>0)
		{
			for(int i=0;i<weElements;i++)
			{
				String dynamicXpath="("+xpath+")["+(i+1)+"]";
				String dynamicXpathStatus=dynamicXpath+"/../..//input[@name='blockStatus']/..";

				if(driver.findElement(By.xpath(dynamicXpathStatus)).getText().equals("Blocked"))
				{
					clickWebElement(dynamicXpath, "Check Box", screenName);
					switchToWindow("storeParent");
					clickWebElement(sheetName, "btn_blockRelease;xpath", "Release Block", screenName);
					Thread.sleep(2000);
					switchToWindow("child");
					Thread.sleep(2000);
					switchToFrame("default");
					enterValueInTextbox(sheetName, "inbx_releaseRemarks;xpath", data(remarks), "Release block Remarks", screenName);
					waitForSync(2);
					clickWebElement(sheetName, "btn_blockReleasePopup;xpath", "Release Button in Popup", screenName);
					switchToWindow("getParent");
					switchToDefaultAndContentFrame("OPR023");
					waitForSync(4);

				}
			}
		}

	}
	/**
	 * @author A-9847
	 * @Desc To release the various given blocked types
	 * @param pmKey
	 * @param remarks
	 * @throws Exception
	 */
	public void selectCheckboxandReleaseBlocks(String pmKey[],String remarks) throws Exception {

		for(int j=0;j<pmKey.length;j++)
		{
			String xpath = xls_Read.getCellValue(sheetName, "chbx_details;xpath").replace("*", pmKey[j]);	
			int weElements = driver.findElements(By.xpath(xpath)).size();

			if(weElements>0)
			{
				for(int i=0;i<weElements;i++)
				{
					String dynamicXpath="("+xpath+")["+(i+1)+"]";
					String dynamicXpathStatus=dynamicXpath+"/../..//input[@name='blockStatus']/..";

					if(driver.findElement(By.xpath(dynamicXpathStatus)).getText().equals("Blocked"))
						clickWebElement(dynamicXpath, "Check Box", screenName);

				}

			}
		}

		if(driver.findElement(By.xpath("//input[@type='checkbox' and @name='blockId']")).isSelected())
		{
			switchToWindow("storeParent");
			clickWebElement(sheetName, "btn_blockRelease;xpath", "Release Block", screenName);
			Thread.sleep(2000);
			switchToWindow("child");
			Thread.sleep(2000);
			switchToFrame("default");
			enterValueInTextbox(sheetName, "inbx_releaseRemarks;xpath", data(remarks), "Release block Remarks", screenName);
			waitForSync(2);
			clickWebElement(sheetName, "btn_blockReleasePopup;xpath", "Release Button in Popup", screenName);
			switchToWindow("getParent");
			switchToDefaultAndContentFrame("OPR023");
			waitForSync(4);	
		}
	}
	/**
     * @author A-9478
     * Description...  Release block
     * @param releaseRemarks
     * @throws Exception
     */
     public void releaseBlock(String releaseRemarks) throws Exception
     {
           int n = driver.findElements(By.xpath("//td//input[@value='B']/../../td/input[@type='checkbox']")).size();
           try
           {
                 if(n>=1)
                 {
                	 for(int i=1;i<=n;i++)
                	 {
                		 String locator1 = xls_Read.getCellValue(sheetName, "chbx_eachBlockedStatus;xpath");
                		 locator1=locator1.replace("index",Integer.toString(i));
                		 String locator2 = xls_Read.getCellValue(sheetName, "chbx_eachBlockedType;xpath");
                		 locator2=locator2.replace("index",Integer.toString(i));                       
                		 if(!driver.findElement(By.xpath(locator2)).getText().contains("US Customs"))
                		 {
                			 driver.findElement(By.xpath(locator1)).click();
                			 waitForSync(1);
                		 }                       
                	 }                 
             
                       switchToWindow("storeParent");
                       clickWebElement(sheetName, "btn_blockRelease;xpath", "Release Block", screenName);
                       Thread.sleep(2000);
                       switchToWindow("child");
                       Thread.sleep(2000);
                       switchToFrame("default");
                       enterValueInTextbox(sheetName, "inbx_releaseRemarks;xpath", data(releaseRemarks), "Release block Remarks", screenName);
                       waitForSync(2);
                       clickWebElement(sheetName, "btn_blockReleasePopup;xpath", "Release Button in Popup", screenName);
                       switchToWindow("getParent");
                       waitForSync(4);
                       writeExtent("Pass", "Successfully Released Block in "+screenName);
                 
                 }
                 
                 else
                 {
                       writeExtent("Pass", "No block exists in "+screenName);
                 }
                 
           }
           catch(Exception e)
           {
                 writeExtent("Fail", "Could not release block in "+screenName);
           }
     }
     /**
 	 * @author A-6260
 	 * Desc..verify block released
 	 * @param BlockType
 	 * @param AWB
 	 * @throws Exception
 	 */
 	public void verifyBlockReleased(String BlockType, String AWB) throws Exception
 	{
 		int n = driver.findElements(By.xpath("//input[@name='blockId']")).size();
 		try
 		{
 			for(int i=1;i<=n;i++)
 			{
 				String blockTypelocator = xls_Read.getCellValue(sheetName, "txt_blockedType;xpath");
 				blockTypelocator=blockTypelocator.replace("*",Integer.toString(i));
 				String blockStatuslocator = xls_Read.getCellValue(sheetName, "txt_blockedStatus;xpath");
 				blockStatuslocator=blockStatuslocator.replace("*",Integer.toString(i));  
 				if(driver.findElement(By.xpath(blockTypelocator)).getText().contains(BlockType))
 				{
 					if((driver.findElement(By.xpath(blockStatuslocator)).getText().contains("Released")))
 					{
 						onPassUpdate(screenName,"Block released","Block released", "Verification of Block status","Verification of Block status");
 					} else {
 						String status=driver.findElement(By.xpath(blockStatuslocator)).getText();
 						onFailUpdate(screenName, "Block released. Status is "+status,"Block not released", "Verification of Block status","Verification of Block status");
 					}
 				}
 			}

 		}
 		catch(Exception e)
 		{
 			writeExtent("Fail", "Could not verify block status in "+screenName);
 		}
 	}
 	

     /**
      * @author A-6260
      * Desc..verify block
      * @param BlockType
      * @param AWB
      * @throws Exception
      */
     	public void verifyBlock(String BlockType, String AWB) throws Exception
     	{
     		int n = driver.findElements(By.xpath("//input[@name='blockId']")).size();
     		boolean blockExists=false;
     		try
     		{
     			for(int i=1;i<=n;i++)
     			{
     				String blockTypelocator = xls_Read.getCellValue(sheetName, "txt_blockedType;xpath");
     				blockTypelocator=blockTypelocator.replace("*",Integer.toString(i));
     				String blockStatuslocator = xls_Read.getCellValue(sheetName, "txt_blockedStatus;xpath");
     				blockStatuslocator=blockStatuslocator.replace("*",Integer.toString(i));  
     				if(driver.findElement(By.xpath(blockTypelocator)).getText().contains(BlockType))
     				{
     					if(driver.findElement(By.xpath(blockStatuslocator)).getText().contains("Blocked"))
     					{
     						blockExists=true;
     					break;
     				}   
     			}
     		}
                  
     				if(blockExists) {
     					writeExtent("Pass", BlockType+ " Block is present for "+AWB+" in " +screenName);
     				}
     				else
     				{
     					writeExtent("Fail", BlockType+ " is not present for "+AWB+" in "+screenName);
     				}

     		}
     		catch(Exception e)
     		{
     			writeExtent("Fail", "Could not verify block status in "+screenName);
     		}
     	}
     /**
      * Desc..Verify scc
      * @author A-6260
      * @param SCC
      * @throws IOException
      * @throws InterruptedException
      */
     public void verifySCCs(String SCC) throws IOException, InterruptedException
     {                       
           String locator = xls_Read.getCellValue(sheetName, "txt_SCC;xpath");
           if(driver.findElement(By.xpath(locator)).getAttribute("value").contains(data(SCC)))
           {
                 writeExtent("Pass","Successfully verified SCC "+data(SCC)+" in "+screenName);
           }
           else
           {
                 writeExtent("Fail","Couldn't verify SCC "+data(SCC)+" in "+screenName);
          }
    }
	/**
	 * Description...  Enter the Block Details
	 * @param blockedType
	 * @param BlockRemarks
	 * @throws Exception
	 */
	public void enterBlockDetailsByJavaScript(String blockedType, String BlockRemarks) throws Exception
	{
		selectValueInDropdown(sheetName, "lst_blockedType;xpath", data(blockedType), "Select Blocked Type", "VisibleText");
		waitForSync(4);
		
		javaScriptToEnterValueInTextBox(sheetName, "inbx_blockRemarks;xpath", data(BlockRemarks), "Block Remarks", "AWB Clearance");
		
		clickButtonSwitchtoParentWindow(sheetName,"btn_blockDetails;xpath", "Block Details Button", screenName);
		waitForSync(2);
		
	}
	/**
     * @author A-9478
     * Description... Verify that there are no block details
     * @param verfCols
     * @param actVerfValues
     * @param pmKey
     * @throws IOException 
      */
     public void verifyThereAreNoBlockDetails() throws IOException
     {
           String locator = xls_Read.getCellValue(sheetName, "table_blockdetails;xpath");
           if(driver.findElements(By.xpath(locator)).size()==1)
           {
                 writeExtent("Pass","Block details are not present in "+screenName);
           }
           else
           {
                 writeExtent("Fail","Block details not present in "+screenName);
           }
     }
     /**
      * @author A-9478
      * Description... Verify the SCC    
      * @throws InterruptedException 
       */
      public void verifySCC(String SCC) throws IOException, InterruptedException
      {                       
            String locator = xls_Read.getCellValue(sheetName, "txt_SCC;xpath");
            if(driver.findElement(By.xpath(locator)).getAttribute("value").equals(data(SCC)))
            {
                  writeExtent("Pass","Successfully verified SCC "+data(SCC)+" in "+screenName);
            }
            else
            {
                  writeExtent("Fail","Couldn't verify SCC "+data(SCC)+" in "+screenName);
           }
     }

      /**
  	 * @author A-9847
  	 * @Desc To verify no block exists for the given BlockType
  	 * @param BlockType
  	 * @param AWB
  	 * @throws Exception
  	 */
  	public void verifyNoBlockExists(String BlockType, String AWB) throws Exception
   	{
   		int n = driver.findElements(By.xpath("//input[@name='blockId']")).size();
   		boolean blockExists=false;
   		try
   		{
   			for(int i=1;i<=n;i++)
   			{
   				String blockTypelocator = xls_Read.getCellValue(sheetName, "txt_blockedType;xpath");
   				blockTypelocator=blockTypelocator.replace("*",Integer.toString(i));
   				String blockStatuslocator = xls_Read.getCellValue(sheetName, "txt_blockedStatus;xpath");
   				blockStatuslocator=blockStatuslocator.replace("*",Integer.toString(i));  
   				
   				if(driver.findElement(By.xpath(blockTypelocator)).getText().contains(BlockType))
   				{
   					if(driver.findElement(By.xpath(blockStatuslocator)).getText().contains("Blocked"))
   					{
   					blockExists=true;
   					break;
   				}   
   			}
   		}
                
   				if(blockExists)
   					writeExtent("Fail", BlockType+ " block present for "+AWB+" on " +screenName);
   				else
   					writeExtent("Pass", "No " +BlockType +" block present for "+AWB+" on "+screenName);
   				
   		}
   		catch(Exception e)
   		{
   			writeExtent("Fail", "Could not verify block status in "+screenName);
   		}
   	}
	/**
	 * Description...  Release the Block Details
	 * @param releaseRemarks
	 * @throws Exception
	 */
	public void releasBlockDetails(String releaseRemarks) throws Exception
	{
		clickWebElement(sheetName, "checkbox_allBlockId;xpath", "Select Block", screenName);
		switchToWindow("storeParent");
		clickWebElement(sheetName, "btn_blockRelease;xpath", "Release Block", screenName);
		Thread.sleep(2000);
		switchToWindow("child");
		Thread.sleep(2000);
		switchToFrame("default");
		enterValueInTextbox(sheetName, "inbx_releaseRemarks;xpath", data(releaseRemarks), "Release block Remarks", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_blockReleasePopup;xpath", "Release Button in Popup", screenName);
		switchToWindow("getParent");
		waitForSync(4);	
	}
	/**
 	 * @author A-6260
 	 * Desc.. Verify block does not exist
 	 * @param BlockType
 	 * @param AWB
 	 * @param ScreenName
 	 * @throws Exception
 	 */
 	public void verifyBlockReleased(String BlockType, String AWB, String ScreenName) throws Exception
	{
		int n = driver.findElements(By.xpath("//input[@name='blockId']")).size();
		boolean blockExists=false;
		boolean noBlockPresent=false;
		if(n>0) {
			noBlockPresent=false;
			try
			{
				for(int i=1;i<=n;i++)
				{
					String blockTypelocator = xls_Read.getCellValue(sheetName, "txt_blockedType;xpath");
					blockTypelocator=blockTypelocator.replace("*",Integer.toString(i));
					String blockStatuslocator = xls_Read.getCellValue(sheetName, "txt_blockedStatus;xpath");
					blockStatuslocator=blockStatuslocator.replace("*",Integer.toString(i));  
					if(driver.findElement(By.xpath(blockTypelocator)).getText().contains(BlockType))
					{
						if(driver.findElement(By.xpath(blockStatuslocator)).getText().contains("Blocked"))
						{
							blockExists=true;
							break;
						}   
					}
				}
			}
			catch(Exception e)
			{
				writeExtent("Fail", "Could not verify block status in "+ScreenName);
			}
		}else {
			noBlockPresent=true;

		}

		if(blockExists) {
			writeExtent("Fail", "Block present for "+AWB+" in " +ScreenName);
		}
		else if((blockExists == false) || (noBlockPresent))
		{
			writeExtent("Pass", "No block present for "+AWB+" in "+ScreenName);
		}


	}

	/**
	 * Description... Click Block Button
	 * @throws Exception
	 */
	public void clickBlock() throws Exception
	{
		switchToWindow("storeParent");
		
		clickWebElement(sheetName, "btn_block;xpath", "Block Button", "AWB Clearance");
		waitForSync(2);	
		switchToWindow("child");
		waitForSync(2);
	}
	
	/**
	 * Description... Enter the Block Details
	 * @param blockedType
	 * @param BlockRemarks
	 * @throws Exception
	 */
	public void enterBlockDetails(String blockedType, String BlockRemarks) throws Exception
	{
		selectValueInDropdown(sheetName, "lst_blockedType;xpath", data(blockedType), "Select Blocked Type", "VisibleText");
		enterValueInTextbox(sheetName, "inbx_blockRemarks;xpath", data(BlockRemarks), "Block Remarks", "AWB Clearance");
		
		clickButtonSwitchtoParentWindow(sheetName,"btn_blockDetails;xpath", "Block Details Button", screenName);
		waitForSync(2);
		
	}
	/**
	 * Description... Verify the Block Details
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws IOException 
	 */
	public void verifyBlockDetails(int verfCols[],String actVerfValues[],String pmKey) throws IOException
	{
		waitForSync(4);
		verify_tbl_records_multiple_cols(sheetName, "table_blockdetails;xpath", "//td", verfCols, pmKey, actVerfValues);
	}
	
	
	
	
	
	
}
