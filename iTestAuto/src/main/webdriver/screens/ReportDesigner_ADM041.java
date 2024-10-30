package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
	
	public class ReportDesigner_ADM041 extends CustomFunctions {

		public ReportDesigner_ADM041(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
			super(driver, excelReadWrite, xls_Read2);
			
		}
		
			String sheetName = "ReportDesigner_ADM041";
			String screenName = "ReportDesigner_ADM041";
			CustomFunctions comm = new CustomFunctions(driver, excelreadwrite, xls_Read);
			/**
			 * Description... Report Id
			 * @param rprtid
			 * @param companycode
			 * @param FullAWBNo
			 * @throws InterruptedException
			 * @throws AWTException
			 * @throws IOException 
			 */
			public void reportId(String rprtid,String companycode,String FullAWBNo) throws InterruptedException, AWTException, IOException{
				
				enterValueInTextbox(sheetName,"inbx_reportId;xpath",data(rprtid),"Report ID",screenName);
				clickWebElement(sheetName,"btn_ReportIdList;xpath","List buttom",screenName);
				waitForSync(5);
				enterValueInTextbox(sheetName,"inbx_CompanyCode;xpath",data(companycode),"Company code",screenName);
				Thread.sleep(4000);
				enterValueInTextbox(sheetName,"inbx_AWBnumber;xpath",data(FullAWBNo),"AWB number",screenName);
				Thread.sleep(2000);
			}
			
			/**
			 * Description... Report Id List
			 * @param rprtid
			 * @throws InterruptedException
			 * @throws AWTException
			 * @throws IOException 
			 */
			public void reportIdList(String rprtid)
                     throws InterruptedException, AWTException, IOException {

              enterValueInTextbox(sheetName, "inbx_reportId;xpath", data(rprtid), "Report ID", screenName);
              clickWebElement(sheetName, "btn_ReportIdList;xpath", "List buttom", screenName);
              waitForSync(5);

       }
/**
 * Description... Filters Flight No
 * @param fltNo
 * @throws InterruptedException
 * @throws AWTException
 */
       public void filtersFlightNo(String fltNo)
                     throws InterruptedException, AWTException {

              Thread.sleep(4000);
              enterValueInTextbox(sheetName, "inbx_fltNo;xpath", fltNo, "FLT No", screenName);
              Thread.sleep(2000);
       }

 /**
  * Description...   Filters Airport Code    
  * @param airportCode
  * @throws InterruptedException
  * @throws AWTException
  */
       public void filtersAirportCode(String airportCode)
                     throws InterruptedException, AWTException {

              Thread.sleep(4000);
              enterValueInTextbox(sheetName, "inbx_CompanyCode;xpath", data(airportCode), "Airport Code", screenName);
              Thread.sleep(2000);
       }

/**
 * Description... Click Print
 * @throws Exception
 */
			public void clickPrint()throws Exception{
				clickWebElement(sheetName,"btn_list;xpath","List buttom",screenName );
			}
			
			
		}

		

