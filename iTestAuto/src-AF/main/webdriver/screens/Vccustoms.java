package screens;


import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.relevantcodes.extentreports.LogStatus;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Vccustoms extends CustomFunctions {

	String sheetName = "vccustoms_screen";
	String screenName = "vccustoms";


	public Vccustoms(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}
	
	/**
	 * @author A-9847
	 * @Des To verify the MRN
	 * @param expMRN
	 */
	public void verifyMRN(String expMRN){
		
		try
	    {
		String actMRN=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"table_mrn;xpath"))).getText();
	    System.out.println(actMRN);
		verifyScreenTextWithExactMatch(sheetName, data(expMRN),actMRN ,"MRN Reference number",screenName);
    }
    catch(Exception e){
    	writeExtent("Fail", "Mrn reference number is not updated in vccustoms"+ screenName);
   }
		
	}
	
	/**
	 * @author A-9847
	 * @Des To verify the MRN corresponding to the given source
	 * @param expMRN
	 */
	public void verifyMRN(String expMRN,String source){
		
		try
	    {
			
		String MRN=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"table_mrnValue;xpath").replace("*",data(source)))).getText();
		System.out.println(MRN);
		verifyScreenTextWithExactMatch(sheetName, data(expMRN) ,MRN ,"MRN Reference number",screenName);
    }
    catch(Exception e){
    	writeExtent("Fail", "Mrn reference number is not updated in vccustoms"+ screenName);
   }
		
	}
	

    /**@author A-10328
	 * Description : Verify MRN is not displayed 
	 * @param expMRN
	 */
	public void verifyNoMRNDisplayedOnSave(String expMRN)
	
	
	{
				
	String actMRN=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"table_mrn;xpath"))).getText();

	if(expMRN!=actMRN)
	writeExtent("Pass", "Successfully verified No MRN displayed on "+screenName);
	else
		writeExtent("Fail", "MRN displayed on "+screenName);
		}

	/**
	 * @author A-9847
	 * @Desc To verify MRNs of same source
	 * @param expMRN
	 * @param source
	 */
	public void verifyMRNsOfSameSource(String expMRN[],String source){
		
		
			
			try
			{		
				int mrnCount=0;		
				List<WebElement> mrns=driver.findElements(By.xpath(xls_Read.getCellValue(sheetName,"table_mrnValue;xpath").replace("*",data(source))));

				for(WebElement mrn:mrns){ 	
					for(int i=0;i<expMRN.length;i++){
						if(mrn.getText().equals(expMRN[i])){	  
							writeExtent("Pass", "Successfully verified as "+ expMRN[i]+ " on "+ screenName);   
							mrnCount=mrnCount+1;
							break;

						}	
					}
				}

				if(mrnCount==expMRN.length)  
					writeExtent("Pass", "Successfully verified all the MRNs on "+ screenName);
				else
					writeExtent("Fail", "Failed to verify all the MRNs on "+ screenName);

			}
			catch(Exception e){
				writeExtent("Fail", "Failed to verify the MRNs on "+ screenName);
			}

		}
	
	/*Desc:verify updateexportcustomData  is not triggered
	 * 
	 */

	public void  NoMrnUpdated()
	{
		String actText=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"table_noSourceUpdated;xpath"))).getText();

		verifyScreenTextWithExactMatch(sheetName, data("val~No result found") ,actText,"update export custom data not triggered","mrn number not updated");

	}


/**
 * @author A-9847
 * @Desc To verify the SOAP Response	
 * @param expResp
 */
public void verifySoapResponse(String expResp){
		
		String actResp=getPropertyValue(proppath, "SOAPResponse");
		if(actResp.equals(data(expResp)))
			onPassUpdate("screenName", data(expResp), actResp, "Response Status Code of UpdateExportCustomData came correctly as "+actResp,
                    "UpdateExportCustomData verification");
		else
			onFailUpdate("Response Status Code of UpdateExportCustomData came as "+actResp+" where expected was "+data(expResp));
			
	}
/**
	 * description:method to list the awbno
	 * @author A-10330
	 * @param Fullawbno
	 * @throws InterruptedException
	 * @throws IOException
	 */
	
	public void listAwbno(String Fullawbno) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_id;id", data(Fullawbno), "enter fullawbno",screenName);
		
		clickWebElement(sheetName, "btn_search;xpath", "list search button", screenName);
		
		waitForSync(5);
	}
	/**
	 * description:method to click awbno link
	 * @author A-10330
	 * @throws InterruptedException
	 */
	
	public void clickAwbLink() throws InterruptedException 
	{
		clickWebElementByWebDriver(sheetName,"anchr_awblink;xpath","awb link",
				screenName);
		waitForSync(3);
	}
	/**
	 * description:method to generate mrn number
	 * @author A-10330
	 * @return
	 */
	public String generateMrnNumber()
	{
		String randStr = "";

		try {

			String randomNum_length = "13";
			int digit = Integer.parseInt(randomNum_length);
			long value1 = 1;
			long value2 = 9;

			for (int i = 1; i < digit; i++) {
				value1 = value1 * 10;
				value2 = value2 * 10;
			}

			Long randomlong = (long) (value1 + Math.random() * value2);

			randStr = randomlong.toString();
			randStr=data("Mrnprefix") + randStr ;
			System.out.println(randStr);
			
		}
		catch (Exception e) {
			System.out.println("MRN number could not be generated");
			test.log(LogStatus.FAIL, "MRN number could not be generated");

		}
		return randStr;
	}
	

}