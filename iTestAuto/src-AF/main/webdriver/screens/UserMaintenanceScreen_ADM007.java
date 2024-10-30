package screens;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class UserMaintenanceScreen_ADM007 extends CustomFunctions {

	public UserMaintenanceScreen_ADM007(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "UserMaintenanceScreen_ADM007";
	public String ScreenName = "User Maintenance : ADM007";
	String screenId = "ADM007";
	
	
	/*A-8705
	 * Lists the user
	 */
	public void listUser(String UserID) throws InterruptedException, IOException {
		enterValueInTextbox(sheetName,"inbx_user;name", data(UserID), "User ID", ScreenName);
		waitForSync(5);
		clickWebElement(sheetName, "btn_list_user;name", "List Button", ScreenName);
		
	}

	/*A-8705
	 * Gets the value depending on key in user parameter table and splits comma seperated and adds into List
	 */
	public List<String> getValueFromUserParameters(int UserParameters) {
		List<String> lt=new ArrayList<String>();
		String[] s=null;
		try{	
			
			WebElement ele = driver.findElement(By.xpath("(//input[@id='CMP_ADM_USR_USERMAINTENANCE_PARAMVALUE'])["+UserParameters+"]"));
			String value2=ele.getAttribute("value");
            s=value2.split(",");		
		}
		catch(Exception e){
			System.out.println(e);
		}
		lt=Arrays.asList(s);
		Collections.sort(lt);
		return lt;
		
	}





}
