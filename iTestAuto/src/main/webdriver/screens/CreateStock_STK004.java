package screens;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class CreateStock_STK004 extends CustomFunctions {

	String sheetName = "CreateStock_STK004";
	public CustomFunctions customFuction;
	String screenID = "STK004";
	public String screenName = "CreateStock";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";

	public CreateStock_STK004(WebDriver driver,ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	/**
	 * 
	 * @param docType
	 * @param subType
	 * Desc : enter doc type details
	 */

	public void enterDocTypeDetails(String docType,String subType){

		//To Enter Doc Type and Sub type
		selectValueInDropdown(sheetName, "drpdn_docType;id", data(docType), "Doc Type", "Value");
		selectValueInDropdown(sheetName, "drpdn_subType;id", data(subType), "Sub Type", "Value");

	}

	/**
	 * 
	 * @param stockFrom
	 * @param stockTo
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : create stock
	 */
	public void createStock(String stockFrom,String stockTo) throws InterruptedException, IOException{

		//To create stock till "stock created successfully" toast msg appears else incrementing stock range to and from by 10
		boolean status=false;
		int i=0;

		do{
			String newStockFrom =Integer.toString(Integer.parseInt(data(stockFrom))+i);
			String newStockTo = Integer.toString(Integer.parseInt(data(stockTo))+i);

			enterValueInTextbox(sheetName, "inbx_rangeFrom;id",newStockFrom, "Stock Range From", screenName);
			enterValueInTextbox(sheetName, "inbx_rangeTo;id",newStockTo, "Stock Range To", screenName);
			clickWebElement(sheetName, "btn_save;id", "Save Button", screenName);

			handleAlert("Accept", screenName);
			switchToFrame("contentFrame","STK007");
			waitForSync(1);

			try{
				status=driver.findElement(By.xpath(xls_Read.getCellValue("CreateStock_STK004", "sveInfo;xpath"))).isDisplayed();
			}catch(Exception e){}

			if(status){	
				setPropertyValue("stock_range_from", newStockFrom, proppath);
				setPropertyValue("stock_range_to", newStockTo, proppath);
				writeExtent("Pass","Verified Stock created successfully message in  "+screenName);
			}
			else{

				i=i+10; 
			}

		}while(status==false);
		

	}





}