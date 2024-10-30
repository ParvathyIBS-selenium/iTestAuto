package screens;

import java.io.IOException;
import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class MaintainAndListSystemParameters_SHR048 extends CustomFunctions {

	public MaintainAndListSystemParameters_SHR048(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "MaintainAndListSystemParameter";
	public String screenName = "MaintainAndListSystemParameters_SHR048";

	/**
	 * Description... List Parameter
	 * @param Parameter
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listParameter(String Parameter) throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_parameterCode;id", Parameter, "Parameter Code", screenName);
		clickWebElement(sheetName, "btn_List;id", "List Button", screenName);
		waitForSync(2);

	}

	public void enterParametrValue(String ParameterValue) throws InterruptedException
	{
		By element = getElement(sheetName, "inbx_parameterValue;id");
		driver.findElement(element).clear();
		enterValueInTextbox(sheetName, "inbx_parameterValue;id", ParameterValue, "Parameter Code", screenName);
	}
	
	public void saveDetails() throws InterruptedException, IOException
	{		
		clickWebElement(sheetName, "btn_save;id", "Save Button", screenName);
		waitForSync(2);
	}
	/**
     * Description... get Parameter value
     * @param Parameter
     * @throws InterruptedException
     */
     public String getParameterValue() throws InterruptedException
     {
           By element = getElement(sheetName, "inbx_parameterValue;id");
           String paramValue = driver.findElement(element).getAttribute("value");
           return paramValue;
     }


}
