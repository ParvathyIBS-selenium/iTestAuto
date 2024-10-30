package screens;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class LocationSynchronisation_ADD020 extends CustomFunctions {
	String sheetName = "LocationSynchronisation_ADD020";
	String screenName = "Location Synchronisation screen";
	String screenID = "ADD020";


	public LocationSynchronisation_ADD020(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2)
	{
		super(driver, excelReadWrite, xls_Read2);
	}

	/**
	 * @author A-10330
	 * Description... Click Save
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void clickSync() throws InterruptedException, IOException {

		clickWebElement(sheetName, "btn_syncbutton;id", "Sync Button", screenName);
		waitTillScreenload(sheetName, "div_uld;xpath", "Uld Number", screenID);

	}

	/**
	 * @author A-10330
	 * @param uldType
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String  extractUld(String uldType) throws InterruptedException, IOException
	{
		String xpath=xls_Read.getCellValue(sheetName, "div_listofulds;xpath");
        String uldText=null;
		String AgvLocation=null;
		String Location=null;
		
		if(uldType.equals("PAG"))
			 {
				xpath= xpath.replace("*",uldType);
			 }
			 else if(uldType.equals("PMC"))
			 {
				 xpath= xpath.replace("*",uldType); 
			 }
			 else if(uldType.equals("AKE"))
			 {
				 xpath= xpath.replace("*",uldType); 
			 }
	      
	      List<WebElement> Ulds=listUlds(xpath);
		
    try
      {
	    while(Ulds.size()>=1)
		{
    	   waitTillScreenload(sheetName, "div_uld;xpath", "Uld Number", screenID);
			int i=1;
           while(i<=Ulds.size())
			{
                WebElement e1=driver.findElement(By.xpath("("+xpath+")["+i+"]"));
				WebElement  e2=driver.findElement(By.xpath("("+xpath+")["+i+"]//following::div[@aria-describedby='agvLocation']"));
				WebElement  e3=driver.findElement(By.xpath("("+xpath+")["+i+"]//following::div[@aria-describedby='location']"));
				AgvLocation=e2.getText();
				Location=e3.getText();
				int location= Location.length();
				int Agvlocation=AgvLocation.length();
				if(Agvlocation!=0 && location!=0 ){
					uldText= e1.getText();
					writeExtent("Pass","Successfully verified Ulds listed  based on   getMHS inventory details service  having  mismatch in Locations On"+screenName);  
					break;
				}
				i++;
			}
            if(uldText==null)
			{
				clickWebElement(sheetName, "anch_rightarrowbtn;xpath", "right arrow button", screenName);	
				waitForSync(4);
				Ulds=listUlds(xpath);	
			}
			else
			{
				break;
			}
	 }
      }catch(Exception e)
      {
    	  writeExtent("Fail","could not retrieve the uldNo having mismatch in Locations On "+screenName);  
      }
		return uldText;

	}
/**
 * @author A-10330
 * @param UldNo
 * @throws InterruptedException
 * @throws IOException
 */
	
 public void clickRelocation(String UldNo) throws InterruptedException, IOException
 {
	
	String xpath=xls_Read.getCellValue(sheetName, "div_location_button;xpath");
	String xpath2=xls_Read.getCellValue(sheetName, "div_reloc_button;xpath");
	xpath= xpath.replace("*",UldNo);
	xpath2=xpath2.replace("*",UldNo);
	try
	{
		listUld(xpath);
        driver.findElement(By.xpath(xpath)).click();
	       waitForSync(2); 
	   driver.findElement(By.xpath(xpath2)).click();
	}catch(Exception e)
	{
		 writeExtent("Fail","could not click on the relocation button "+screenName); 
	}
	       
 }
 /**
  *@author A-10330
  * @param location
  * @throws Exception
  */
 
 public void completeRelocation(String location) throws Exception
 {
    	
	 waitForSync(3);	
	 switchToFrame("frameName","popupContainerFrame");
		//Enter Destination Location in new window
       enterValueInTextbox(sheetName, "btn_suLocationinbox;xpath", location, "Location details", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "btn_okbutton;name", "save button", screenName);
		waitForSync(3);
		switchToFrame("default");
		clickWebElement(sheetName, "btn_dialogpopup;xpath", "ok button", screenName);
		switchToFrame("default");
		switchToFrame("contentFrame","ADD020");
		
	
	 }
 /**
  * @author A-10330
  * @param location
  * @param uldNo
  * @throws InterruptedException
  * @throws IOException
  */
 
 public void  verifyLocationUpdated(String location,String uldNo) throws InterruptedException, IOException
 {
	 String xpath= xls_Read.getCellValue(sheetName, "verf_icargoLocation;xpath");
	 String xpath2=xls_Read.getCellValue(sheetName, "verf_AGVLocation;xpath");
	 
	        xpath=xpath.replace("*", uldNo);
	        xpath2=xpath2.replace("*", uldNo);
	        try
	        {
	        	listUld( xpath);
	        
	        String Location=driver.findElement(By.xpath(xpath)).getText().trim();
	        
	        System.out.println(Location);
	        System.out.println(location);
	        
	        String AgvLocation=driver.findElement(By.xpath(xpath2)).getText().trim();
	        
	        if(Location.equalsIgnoreCase(location.trim()))
	        writeExtent("Pass","successfully verified icargo Location is updated after Relocation done  for" +uldNo+"On"+screenName);
	        
	        else
	        writeExtent("Fail","Failed to verify icargo Location is updated after Relocation done  for" +uldNo+"On"+screenName);
	        
	         if(!Location.equalsIgnoreCase(AgvLocation))
	        
	         writeExtent("Pass","successfully verified "+uldNo+" having mismatch in  AGV Location  corresponding to Icargo Location On"+screenName);  	
	         else
	        writeExtent("Fail","Failed to verify "+uldNo+" having mismatch in  AGV Location  corresponding to Icargo Location On"+screenName);
	        
	        }catch(Exception e)
	        {
	        	writeExtent("Fail","could not retrieve the Locations On"+screenName);
	        }
	        
  }
/**
 * @author A-10330
 * @param xpath
 * @return
 * @throws InterruptedException
 * @throws IOException
 */
 
 public List<WebElement> listUlds(String xpath) throws InterruptedException, IOException
 {
	 
	 List<WebElement> ulds=driver.findElements(By.xpath(xpath));
	 try
	 {
	 while(ulds.size()==0)
		{
			
			clickWebElement(sheetName, "anch_rightarrowbtn;xpath", "right arrow button", screenName);	
			waitForSync(4);
			ulds=driver.findElements(By.xpath(xpath));
       }
	 }catch(Exception e)
	 {
		 writeExtent("Fail","could not list the Ulds ON"+screenName); 
	 }
	 return  ulds;
	 
 }
 /**@author A-10330
  * 
  * @param xpath
  * @throws InterruptedException
  * @throws IOException
  */
 
 public void listUld(String xpath) throws InterruptedException, IOException
 {
	 try
	 {
	  while(driver.findElements(By.xpath(xpath)).size()==0)
 	    {
 		 clickWebElement(sheetName, "anch_rightarrowbtn;xpath", "right arrow button", screenName);	
 		  waitForSync(4); 
 	    }
	 }catch(Exception e)
	 {
		 writeExtent("Fail","could not list the UldNo ON"+screenName);  
	 }
 }
 
 }
 




