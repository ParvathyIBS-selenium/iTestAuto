package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class MaintainAirportScreen_SHR006 extends CustomFunctions {

	public MaintainAirportScreen_SHR006(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "MaintainAirportScreen_SHR006";
	public String ScreenName = "Maintain Airport : SHR006";
	String screenId = "SHR006";
	
	
	/*A-8705
	 * Lists the paramaters based on airport code
	 */
	/**
	 * Description... Enter Origin, Click List Button
	 * @param Origin
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listAirport(String Origin) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName,"inbx_airportCode;name", data(Origin), "Airport Code", ScreenName);
		waitForSync(1);
		clickWebElement(sheetName, "btn_list;name", "List Button", ScreenName);
		waitForSync(6);
		
	}
/**
 * Description... Filter Parameter Based On value
 * @param parameterValue
 * @throws InterruptedException
 * @throws AWTException
 * @throws IOException 
 */
	/*A-8705
	 * filters the parameters based on value
	 */
	public void filterParameterBasedOnvalue(String parameterValue) throws InterruptedException, AWTException, IOException {
        waitForSync(5);
		clickWebElement(sheetName, "icon_fltr_parameter;xpath", "filter Button", ScreenName);
		waitForSync(5);
		enterValueInTextbox(sheetName,"inbx_fltr_parameter;xpath", data(parameterValue), "Airport Code", ScreenName);
		
	}
	/**
     * Description... get parameter value
     * @throws InterruptedException
     */
           public String getParameterValue(String Parameter) throws InterruptedException
           {
               String locator = xls_Read.getCellValue(sheetName, "lst_getParameterValue1;xpath");
               locator=locator.replace("PARAM",data(Parameter));               
               String paramValue = driver.findElement(By.xpath(locator)).getAttribute("value");
               return paramValue;

           }
           
           /**
            * Description... Change Parameter value
            * @throws InterruptedException
            */
           public void changeParameterValue(String Parameter,String ParamValue) throws InterruptedException
           {
                  String locator = xls_Read.getCellValue(sheetName, "lst_setParameterValue;xpath");
                locator=locator.replace("PARAM",data(Parameter));
                try
                {
                	
                    Select s = new Select(driver.findElement(By.xpath(locator)));
                    s.selectByVisibleText(ParamValue);
                        waitForSync(5);
                        save();
                        waitForSync(5);
                        writeExtent("Pass", "Changed Parameter value of "+data(Parameter)+" to "+ParamValue
                                          + " on " + ScreenName + " Page");
                }
                catch (Exception e) 
                {
                  writeExtent("Fail", "Couldn't change Parameter value of "+data(Parameter)+" to "+ParamValue
                                          + " on " + ScreenName + " Page");
                }
                                    
            }

	/**
     * Description... Change Enable SCC Parameter
     * @throws InterruptedException
	 * @throws IOException 
     */
           public void changeEnableSCCParameterValue(String value) throws InterruptedException, IOException
           {
                 selectValueInDropdown(sheetName, "lst_ParametrValueEnableSpecialSCC;xpath",value, "Special SCC paramter value",
                             "VisibleText");
                 waitForSync(5);
                 save();
                 waitForSync(5);
                 
           }
           /**
            * Description... get Station Cashiering Enabled parameter value
            * @throws InterruptedException
            */
                  public String getCashieringEnabledParameterValue() throws InterruptedException
                  {
                	  waitForSync(4);
                      By element = getElement(sheetName, "lst_getstationCashieringParamValue;xpath");
                      String paramValue = driver.findElement(element).getAttribute("value");
                      System.out.println(paramValue);
                      return paramValue;

                  }
           /**
            * Description... Change Station cashieiring Parameter
            * @throws InterruptedException
         * @throws IOException 
            */
                  public void changeStationCashieringParameterValue(String value) throws InterruptedException, IOException
                  {
                        selectValueInDropdown(sheetName, "lst_parameterValue;xpath",value, "Station Cashiering enabled parameter value",
                                    "VisibleText");
                        waitForSync(5);
                        save();
                        waitForSync(5);
                       
                  }


/**
* Description... get Enable SCC parameter value
* @throws InterruptedException
*/
     public String getEnableSCCParameterValue() throws InterruptedException
     {
       waitForSync(4);
       By element = getElement(sheetName, "lst_ParametrValueEnableSpecialSCC;xpath");
         String paramValue = driver.findElement(element).getAttribute("value");  
         return paramValue;
     }

	/**
	 * Description... Select Weighing mode parameter
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	/*A-8705
	 * changes Parameter value to N
	 */
	public void changeParameterValuetoN() throws InterruptedException, IOException {
		selectValueInDropdown(sheetName, "lst_parameterValue;xpath","N", "Weighing mode paramter",
				"VisibleText");
		waitForSync(5);
		save();
		waitForSync(5);
	}
	/**
     * Description... Change Weighing mode Parameter Value to Y
     * @throws InterruptedException
	 * @throws IOException 
     */
           public void changeEnableSCCParameterValuetoY() throws InterruptedException, IOException
           {
                 selectValueInDropdown(sheetName, "lst_ParametrValueEnableSpecialSCC;xpath","Y", "Enable Special SCC paramter",
                             "VisibleText");
                 waitForSync(5);
                 save();
                 waitForSync(5);
                 
           }

/**
           * Description... Change Weighing mode Parameter Value to Y
           * @throws InterruptedException
 * @throws IOException 
           */
                 public void changeEnableSCCParameterValuetoN() throws InterruptedException, IOException
                 {
                       selectValueInDropdown(sheetName, "lst_ParametrValueEnableSpecialSCC;xpath","N", "Enable Special SCC paramter",
                                   "VisibleText");
                       waitForSync(5);
                       save();
                       waitForSync(5);
                       
                 }


	/**
	 * Description... Click Save Button
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	/*A-8705
	 * Clicks on Save button
	 */
	public void save() throws InterruptedException, IOException {
		clickWebElement(sheetName, "btn_Save;name", "Save Button", ScreenName);		
	}
/**
 * Description... Change Weighing mode Parameter Value to Y
 * @throws InterruptedException
 * @throws IOException 
 */
	public void changeParameterValuetoY() throws InterruptedException, IOException {
		selectValueInDropdown(sheetName, "lst_parameterValue;xpath","Y", "Weighing mode paramter",
				"VisibleText");
		waitForSync(5);
		save();
		waitForSync(5);
		
	}


}
