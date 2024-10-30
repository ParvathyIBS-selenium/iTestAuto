package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class MaintainListCommodity_SHR015 extends CustomFunctions {
	

	public MaintainListCommodity_SHR015(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName="MaintainListCommodity_SHR015";
	public String screenName="MaintainListCommodity";
	
	/**
	 * Description... List Commodity
	 * @param commodity
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listCommodity(String commodity) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName,"inbx_commodity;name", data(commodity),
				"Commodity Code", screenName);
		clickWebElement("Generic_Elements", "btn_listChildWindow;name", "List Button", screenName);
		waitForSync(3);
		
	}
	/**
	 * Description... Get Density Factor
	 * @return
	 * @throws InterruptedException
	 */
	public String getDensityFactor() throws InterruptedException {
	
		//ele=findDynamicXpathElement("inbx_densityFactor;xpath", sheetName, "Density Factor", screenName);
		//String denFactor = ele.getAttribute("value");
		
		By element = getElement(sheetName, "inbx_densityFactor;xpath");
		String denFactor = driver.findElement(element).getAttribute("value");
		
		System.out.println("Density factor is---" + denFactor);
		return denFactor;
		
	}
}
