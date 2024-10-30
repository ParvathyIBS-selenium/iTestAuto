package screens;
import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class BreakDownEnquiry_OPR005 extends CustomFunctions {


	public CustomFunctions customFuction;
	String sheetName = "BreakDownEnquiry_OPR005";
	String screenName = "Breakdown Enquiry / Screen : OPR005";

	public BreakDownEnquiry_OPR005(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);
	}
	
	public void listFlight(String awbNo,String sdate,String edate) throws InterruptedException, AWTException, IOException {
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_awbNo;id", data(awbNo), "Awb Number ", screenName);
		waitForSync(5);
		enterValueInTextbox(sheetName, "inbx_fromDate;id", data(sdate), "Start Date ", screenName);
		waitForSync(5);
		performKeyActions(sheetName, "inbx_fromDate;id", "TAB", "Flight Date", screenName);
		enterValueInTextbox(sheetName, "inbx_endDate;id", data(edate), "End Date ", screenName);
		waitForSync(3);
		performKeyActions(sheetName, "inbx_endDate;id", "TAB", "Flight Date", screenName);
		clickWebElement(sheetName, "btn_list;id", "List", screenName);
		waitForSync(2);
		}

	public void verifyRecordExist(String startdatepmkey,String enddatepmkey,String userpmkey) throws InterruptedException, AWTException {
		
		waitForSync(2);
		getTextAndVerify(sheetName, "tbl_startDate;xpath", "Start Date", screenName, "Start Date",data(startdatepmkey), "contains");
		waitForSync(2);
		getTextAndVerify(sheetName, "tbl_endDate;xpath", "End Date", screenName, "End Date",data(enddatepmkey), "contains");
		waitForSync(2);
		getTextAndVerify(sheetName, "tbl_User;xpath", "End Date", screenName, "End Date",data(userpmkey), "equals");
		waitForSync(2);
		}
	
}
