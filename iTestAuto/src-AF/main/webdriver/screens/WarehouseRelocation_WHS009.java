package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class WarehouseRelocation_WHS009  extends CustomFunctions {
	
	String sheetName = "WarehouseRelocation_WHS009";
	String screenName = "WarehouseRelocation_WHS009";
	String screenId="WHS009";	

	public WarehouseRelocation_WHS009(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}
	
/**
 * Description... Enter AWB
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
 * Description... List Awb Details
 * @throws InterruptedException
 * @throws IOException 
 */
	public void listAwbDetails() throws InterruptedException, IOException
	{
		//List button
		clickWebElement(sheetName, "btn_list;xpath", "List", screenName);
		waitForSync(1);	
	}
	
/**
 * Description...	Click on the Mark Checkbox
 * @throws InterruptedException
 * @throws IOException 
 */
	public void markCheckbox() throws InterruptedException, IOException
	{
		//Mark the check box in results
		waitForSync(3);	
		clickWebElement(sheetName, "inbx_check;xpath", "Checkbox", screenName);	
	}
	/**@author A-10328
	 * Description - Get the SU generated after relocation is completed 
	 * @param SU
	 */
	
	public void GetSuGeneratedAfterRelocation(String SU)


	{
		String loc=xls_Read.getCellValue(sheetName, "tbl_verfSu;xpath");
		String SUText=driver.findElement(By.xpath(loc)).getText();

		map.put(SU, SUText);
	}
/**
 * Description...	Click Full Relocation Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickFullRelocationButton() throws InterruptedException, IOException
	{
		//Full Relocation button
		waitForSync(2);	
		clickWebElement(sheetName, "btn_fullRelocation;xpath", "Full Relocation", screenName);	
	}
/**
 * Description...	Enter Destination Location, click on OK Button
 * @param location
 * @throws Exception
 */
	public void enterLocation(String location) throws Exception
	{
		waitForSync(2);	
		switchToWindow("storeParent");
		switchToWindow("child");
		//Enter Destination Location in new window
		enterValueInTextbox(sheetName, "inbx_location;xpath", data(location), "Destination Location", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("iCargoContentFrameWHS009");
		
	}
	/**
	 * @author A-9847
	 * @Desc To enter SU
	 * @param su
	 * @throws InterruptedException
	 */
	public void enterSU(String su) throws InterruptedException{
		
		waitTillScreenload(sheetName, "inbx_su;id", "SU Number", screenName);
		enterValueInTextbox(sheetName, "inbx_su;id", data(su), "SU", screenName);
		waitForSync(1);
	}


	/**
	 * Description...	Click Split Relocation Button
	 * @author A-10330
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSplitRelocationButton() throws InterruptedException, IOException
	{
		//split Relocation button
		waitForSync(2);	
		clickWebElement(sheetName, "btn_splitRelocation;xpath", "split Relocation", screenName);	
	}
	/**
	 * Description...	Enter DestLocation and Pieces for splitrelocation new window
	 * @author A-10330
	 * @param location
	 * @param Pieces
	 * @throws Exception
	 */
	public void enterSplitRelocationDetails(String location,String pieces,String weight) throws Exception
	{
		waitForSync(2);	
		switchToWindow("storeParent");
		switchToWindow("child");
		//Enter Destination Location in new window
		enterValueInTextbox(sheetName, "inbx_location;xpath", data(location), "Destination Location", screenName);
		waitForSync(2);
		//Enter shipment pieces in new window
		enterValueInTextbox(sheetName, "inbx_pcs;id", data(pieces), "ShipmentPieces", screenName);
		//Enter shipment weight in new window
		enterValueInTextbox(sheetName, "inbx_wgt;name", data(weight), "ShipmentWeight", screenName);
		waitForSync(2);
		clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(3);
	}

	/**
	 * Description... Verify SU auto generated in New Tab
	 * @param SU,awbno
	 * @author A-10330
	 * @param AWBNo
	 */
	public void verifySuGenerated(String su,String awbno)
	{
		 try{
			   
				String loc=xls_Read.getCellValue(sheetName, "tbl_verfSu;xpath");
				String actText=driver.findElement(By.xpath(loc)).getText();
				
				verifyScreenText(screenName,data(su) , actText, "SU auto generated after Relocation of shipment", "SU auto generated after Relocation of shipment" +data(awbno));
		   }
		   catch(Exception e){
			   writeExtent("Fail", "Could not verify the SU auto generated after Relocation of shipment"+ data(awbno) +" on "+screenName);
			   
		   }
	}
	/***
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @param msgType
	 * @param isAssertreq
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAWBDetails(int verfCols[], String actVerfValues[],
			String pmKey,boolean isAssertreq) throws InterruptedException, IOException {

		verify_tbl_records_multiple_cols(sheetName,"table_awbDetails;xpath","//td", verfCols, pmKey, actVerfValues,isAssertreq);
	}


	/**
	 * Description... Verify Relocation details
	 * @param Tab
	 * @param  verfcols,actverfvalues,pmkey,verf,isassertreq
	 * @author A-10330
	 */
	public void verifyRelocationDetails(int verfCols[], String actVerfValues[],
			String pmKey,String verfpoint,boolean isAssertreq)
	{
		verify_tbl_records_multiple_cols_info_inreport(sheetName, "tbl_verfrelocationdetails;xpath",
				"//td", verfCols, pmKey, actVerfValues,verfpoint,isAssertreq);
	}

	/**
		 * @author A-9847
		 * @Desc To enter the destination Location and Destination SU during full relocation
		 * @param location
		 * @param su
		 * @throws Exception
		 */
		
		public void enterDestinationLocAndSU(String location,String su) throws Exception
		{
			waitForSync(2);	
			switchToWindow("storeParent");
			switchToWindow("child");
			//Enter Destination Location in new window
			enterValueInTextbox(sheetName, "inbx_location;xpath", data(location), "Destination Location", screenName);
			//Enter Destination SU
			enterValueInTextbox(sheetName, "inbx_destSU;id", data(su), "Destination SU", screenName);
			waitForSync(3);
			clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenName);
			waitForSync(3);
			switchToWindow("getParent");
			waitForSync(1);
		}
		/**
		 * Description... Verify shipment details in new section
		 * @author A-10330
		 * @param actverfvalues
		 * @throws IOException
		 */
		public void verifyRelocationDetailsAfterRelocation(String []actverfValues)throws IOException
		{
			String locator = xls_Read.getCellValue(sheetName, "div_tableverf;xpath");
			WebElement elem=driver.findElement(By.xpath(locator));

			if(elem.isDisplayed())
			{   
				for(int i=0;i<actverfValues.length;i++)
				{

					String actText = driver.findElement(By.xpath(locator)).getText();
					if(actText.contains(data("AWBNo"))&&actText.contains(actverfValues[i]))
					{
						writeExtent("Pass","Successfully verifed shipment details with complete pieces and target Location after relocation for "+data("AWBNo")+"on "+screenName);
					}
					else
					{
						writeExtent("Fail","Could not verify shipment details with complete pieces and target Location after relocation for "+data("AWBNo")+"on "+screenName);
					}

				}
			}

		}


		/**
		 * @author A-9847
		 * @Desc To verify the current location of the given ULD
		 * @param uldNum
		 * @param expLoc
		 */

		public void VerifyCurrentLocOfULD(String uldNum,String expLoc){
			   
			   try{
				   
				String loc=xls_Read.getCellValue(sheetName, "txt_currentLoc;xpath").replace("*", data(uldNum));
				String actLoc=driver.findElement(By.xpath(loc)).getText();
				
				System.out.println(actLoc);
				verifyScreenText(screenName, data(expLoc), actLoc, "Current Location Verification", "Current Location Verification");
			   }
			   catch(Exception e){
				   writeExtent("Fail", "Could not verify the Current location of "+ data(uldNum) +" on "+screenName);
				   
			   }
			   
		}
/**
 * Description...	Click Save Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickSaveButton() throws InterruptedException, IOException
	{
		waitForSync(1);	
		
		//Save button
		clickWebElement(sheetName, "btn_save;xpath", "Save", screenName);	
		waitForSync(1);	
	}
	
/**
 * Description...	Click Close Button
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickCloseButton() throws InterruptedException, IOException
	{
		waitForSync(1);	
		
		//Close button
		clickWebElement(sheetName, "btn_close;xpath", "Close", screenName);	
		waitForSync(1);	
	}
	
	// new fn
/**
 * Description...	Enter Location no Frame Switch
 * @param location
 * @throws Exception
 */
	public void enterLocationnoFrameSwitch(String location) throws Exception
	{
		waitForSync(2);	
		switchToWindow("storeParent");
		switchToWindow("child");
		//Enter Destination Location in new window
		enterValueInTextbox(sheetName, "inbx_location;xpath", data(location), "Destination Location", screenName);
		waitForSync(3);
		clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenName);
		waitForSync(3);
		switchToWindow("getParent");
		waitForSync(3);
	}
/**
 * Description... Verify details in New Tab
 * @param Tab
 * @param AWBNo
 * @throws Exception
 */
public void verifydetailsinNewTab(String Tab, String AWBNo)throws Exception{
              
              String actualTab=driver.findElement(By.xpath("//span[@class='ic-label'][1]")).getText();
              String expectedTab=data(Tab);
              String actualAWB= driver.findElement(By.xpath("//td[@class='iCargoTableDataTd'][1]")).getText();
              String expectedAWB=AWBNo;
              
              if(actualTab.equals(expectedTab) && actualAWB.equals(expectedAWB)){
                     verifyScreenText(sheetName, actualTab,expectedTab, "Shipment Relocated", screenName);

              }
              else{
                     verifyScreenText(sheetName, actualTab,expectedTab, "Shipment Relocated not done", screenName);
              }
                           
       }


}
