package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ListTemplates_SHR093 extends CustomFunctions{
	public ListTemplates_SHR093(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}	
	
	public String sheetName="ListTemplates_SHR093";
	public String ScreenName="ListTemplates_SHR093";
	
	/**
	 * Description... Entering Template Id
	 * @param mname
	 * @throws InterruptedException
	 */
	public void enterTemplateId(String templateid) throws InterruptedException{
		waitForSync(2);
		enterValueInTextbox(sheetName, "inbx_TemplateId;id",templateid, "Template Code", ScreenName);
		waitForSync(3);
	}
	
	
	/**
	 * Description... List Details
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listDetails() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "btn_List;id", "list details", ScreenName); 	
	}
	
	/**
	 * Description... 
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public String getTemplateName() throws InterruptedException, AWTException{
		String elementText=getElementText(sheetName, "lbl_templateValue;xpath", "Template Value", ScreenName);
		waitForSync(2);
		return elementText;
	}
	
	

}
