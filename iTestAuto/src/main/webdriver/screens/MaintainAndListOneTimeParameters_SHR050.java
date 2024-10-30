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

public class MaintainAndListOneTimeParameters_SHR050 extends CustomFunctions {

	public MaintainAndListOneTimeParameters_SHR050(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "MaintainListOneTimeParameter";
	public String screenName = "MaintainAndListOneTimeParameters_SHR050";

	/**
	 * Description... List Parameter
	 * @param Parameter
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void listParameter(String Parameter) throws InterruptedException, IOException {

		enterValueInTextbox(sheetName, "inbx_fieldType;name", Parameter, "Field type", screenName);
		clickWebElement(sheetName, "btn_List;id", "List Button", screenName);
		waitForSync(2);

	}

	/**
	 * Description... Get All Field Description For Parameter
	 * @return
	 * @throws InterruptedException
	 */
	public List<String> getAllFieldDescriptionForParameter() throws InterruptedException {
		List<String> fieldDescriptions = new ArrayList<String>();
		By b = getElement(sheetName, "tbl_parameterFieldDescription;xpath");
		List<WebElement> descriptions = driver.findElements(b);
		for (WebElement description : descriptions) {
			String fieldDescription = description.getAttribute("value");
			fieldDescriptions.add(fieldDescription);
		}
		Collections.sort(fieldDescriptions);
		return fieldDescriptions;
	}

	/**
	 * Description... Get Field Value For Parameter
	 * @param fieldDescription
	 * @return
	 * @throws InterruptedException
	 */
	public String getFieldValueForParameter(String fieldDescription) throws InterruptedException {
		String fieldValues = null;
		try {
			String dynXpath = xls_Read.getCellValue(sheetName, "tbl_parameterFieldValue;xpath")
					.replace("fieldDescription", fieldDescription);
			fieldValues = driver.findElement(By.xpath(dynXpath)).getAttribute("value");
		} catch (Exception e) {
			onFailUpdate(screenName, "Fetched field value", "Could no fetch field value", "Feield value",
					"Fetching the field value");
		}
		return fieldValues;
	}

	/**
	 * Description... Click Additional Loading Details Checkbox
	 * @param index
	 * @throws Exception
	 */
	public void select_DeselectAdditionalLoadingDetails(String index) throws Exception {
		String locator = xls_Read.getCellValue(sheetName, "tbl_addintionalLoadingDetailsTable;xpath");
		String dynXpath = locator + "//input[" + index + "]";
		clickWebElement(dynXpath, "Additional Loading Details Checkbox", screenName);
	}

}
