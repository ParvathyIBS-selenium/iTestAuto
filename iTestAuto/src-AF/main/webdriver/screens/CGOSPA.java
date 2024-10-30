package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Arrays;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;


public class CGOSPA extends CustomFunctions

{

	String sheetName ="CGOSPA";
	String screenName ="CGOSPA_screen";


	public CGOSPA(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2){
		super(driver, excelReadWrite, xls_Read2);


	}


	/**@author A-10328
	 * Description- select language as English
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void selectLanguage() throws InterruptedException, IOException

	{
		for(int i=0;i<5;i++)
		{
			clickWebElement(sheetName, "btn_selectLanguage;xpath", "Select Language", screenName);
			waitForSync(2);
			if(driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "btn_selectFR;xpath"))).size()!=1){
				driver.navigate().refresh();
				waitForSync(2);

			}
			else{

				clickWebElement(sheetName, "btn_selectEN;xpath", "Select EN", screenName);
				waitForSync(2);
				break;
			}

		}
	}

	/**@author A-10328
	 * Description - Click search from Menu 
	 * @throws InterruptedException
	 * @throws IOException 
	 */

	public void clickSearchOrArchives() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "img_search;xpath", "click search/Archives ", screenName);
		waitForSync(2);
	}

	/**@author A-10328
	 * Description - enter start date
	 * @param startDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	public void enterStartDate(String startDate) throws InterruptedException, AWTException
	{
		String daterange=xls_Read.getCellValue(sheetName, "txt_startDate;xpath");
		int size=driver.findElements(By.xpath(daterange)).size();
		if(size==1)
		{
			enterValueInTextbox(sheetName, "txt_startDate;xpath", startDate, "StartDate", screenName);
			keyPress("TAB");
			waitForSync(2);
		}
	}

	

	/**@author A-10328
	 * Description- enter end date
	 * @param endDate
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	public void enterEndDate(String endDate) throws InterruptedException, AWTException
	{
		String daterange=xls_Read.getCellValue(sheetName, "txt_endDate;xpath");
		int size=driver.findElements(By.xpath(daterange)).size();
		if(size==1)
		{
		enterValueInTextbox(sheetName, "txt_endDate;xpath", endDate, "End Date", screenName);
		keyPress("TAB");
		waitForSync(2);
		}

	}

	/**@author A-10328
	 * Description- Enter AWBNO and search
	 * @param awbNo
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void listAWBNo(String awbNo) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "txt_awbNo;xpath", data(awbNo), "AWB No", screenName);
		clickWebElement(sheetName, "btn_search;xpath", "Search Button", screenName);
		waitForSync(2);

	}


	/**@author A-10328
	 * Description- verify awb details from the table
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmkey
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void verifyAWBDetails(int verfCols[], String actVerfValues[],String pmkey) throws InterruptedException, IOException {

		verify_tbl_records_multiple_cols(sheetName, "tbl_awbdetails;xpath", "//td", verfCols, pmkey,
				actVerfValues);   

	}


	/**@author A-10328
	 * Description- Verify flight details from the table
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmkey
	 * @throws InterruptedException
	 * @throws IOException
	 */


	public void verifyFlightDetails(int verfCols[], String actVerfValues[],String pmkey) throws InterruptedException, IOException {

		verify_tbl_records_multiple_cols(sheetName, "tbl_flightdetails;xpath", "//td", verfCols, pmkey,
				actVerfValues);   

	}
	
	/***
	 * @author A-6260
	 * Desc..verify SU generated after selecting AWB  in cgospa
	 * @param SU Number
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void verifySUGenerated(String SUNo) throws InterruptedException, IOException {
		
		
		String SUNum=xls_Read.getCellValue(sheetName, "txt_SUgenerated;xpath").replace("SU",data(SUNo)); 
	

		int size=driver.findElements(By.xpath(SUNum)).size();
		if(size==0) {
			writeExtent("Fail", "SU not generated as FNR entry on " + screenName);
		}
		else {
			writeExtent("Pass", "SU generated as FNR entry on " + screenName);
		}

	}

	/**
	 * 
	 * @param pmkey
	 * @param expectedSCCs
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verify SCC details 
	 */
	public void verifySCCDetails(String pmkey,String SCCs,String scc) throws InterruptedException, IOException {

		String locator="";
		//Get actual SCCs with primaryKey

		if(pmkey.contains("AWBNo")||pmkey.contains("FlightNo") ||pmkey.contains("DF"))

		{
			locator=xls_Read.getCellValue(sheetName, "txt_sccList;xpath").replace("awb", data(pmkey)); 
			locator=locator.replace("scc", scc);
		}
		else
		{
			locator=xls_Read.getCellValue(sheetName, "txt_UldType;xpath").replace("uld", data(pmkey));
			locator=locator.replace("scc", scc);
		}

		// Get actual SCCs

		String getActualSCCs=driver.findElement(By.xpath(locator)).getText();
		String actualSCCs[]=getActualSCCs.split(" ");
		String expectedSCCs[]=SCCs.split(" ");


		if(Arrays.asList(actualSCCs).containsAll(Arrays.asList(expectedSCCs)))



			writeExtent("Pass","Succesfully verified the SCCs disdplayed for "+data(pmkey)+" on "+screenName);

		else

			writeExtent("Fail","Mismatch in SCCs disdplayed on "+screenName+" " + "with the expected values. Expected values are : "+Arrays.asList(expectedSCCs)+" Actual values are : "+Arrays.asList(actualSCCs));




	}







/**
	 * @author A-9844
	 * @param pmkey
	 * @param expectedSCCs
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : verify SCC details in the search/archives section of CGOSPA 
	 */
	public void verifySCCDetailsInSearchSection(String pmkey,String SCCs,String scc) throws InterruptedException, IOException {


		String locator=xls_Read.getCellValue(sheetName, "txt_sccInSearchSection;xpath").replace("awb", data(pmkey)); 
		locator=locator.replace("scc", scc);

		// Get actual SCCs

		String getActualSCCs=driver.findElement(By.xpath(locator)).getText();
		String actualSCCs[]=getActualSCCs.split(" ");
		String expectedSCCs[]=SCCs.split(" ");

		if(expectedSCCs.length==actualSCCs.length)

		{

			if(Arrays.asList(expectedSCCs).containsAll(Arrays.asList(actualSCCs)))



				writeExtent("Pass","Succesfully verified the SCCs disdplayed for "+data(pmkey)+" on "+screenName);

			else

				writeExtent("Fail","Mismatch in SCCs disdplayed on "+screenName+" " + "with the expected values. Expected values are : "+Arrays.asList(expectedSCCs)+" Actual values are : "+Arrays.asList(actualSCCs));

		}

		else

		{

			writeExtent("Fail","Mismatch in SCCs disdplayed on "+screenName+" "+ "with the expected values. Expected values are : "+Arrays.asList(expectedSCCs)+" Actual values are : "+Arrays.asList(actualSCCs));

		}

	}
	/**@author A-10328
	 * Description - click AWBNo
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void  clickAWBNo() throws InterruptedException, IOException

	{

		clickWebElement(sheetName, "btn_awbNo;xpath", "click AWBNo ", screenName);
		waitForSync(2);


	}



}
