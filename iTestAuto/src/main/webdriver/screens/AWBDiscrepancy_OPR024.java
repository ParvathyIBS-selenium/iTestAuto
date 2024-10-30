	package screens;

	import java.awt.AWTException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

	import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

	import com.relevantcodes.extentreports.LogStatus;

	import common.CustomFunctions;
import common.WebFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

	public class AWBDiscrepancy_OPR024 extends CustomFunctions {

		public AWBDiscrepancy_OPR024(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
			super(driver, excelReadWrite, xls_Read2);
		}

		public String sheetName = "AWBDiscrepancy_OPR024";
		public String screenName = "AWBDiscrepancy";
		String globalVarPath = "\\src\\resources\\GlobalVariable.properties";
		
		public void verifyDiscDetails(String pmyKey, String tbltag, int verfCols[], String actVerfValues[]) throws InterruptedException, IOException {
			Thread.sleep(2000);
				 
				 verify_tbl_records_multiple_cols(sheetName, "tbl_discrepancyDetails;xpath", tbltag,
				   verfCols, pmyKey, actVerfValues);
		}
		
		public void verifyAction(String expText) throws InterruptedException {
			Thread.sleep(2000);
		
			By ele =  getElement(sheetName,"tbl_tarcinghistory;xpath");
			String actText = driver.findElement(ele).getText();
			
		verifyScreenText(screenName, expText, actText, "Verify Action", "Verify Case Created or MSCAW");
		}
		
	}
	

