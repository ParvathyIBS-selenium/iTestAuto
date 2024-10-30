package demo;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CheckExtentReport {
	
	
	
	public static void main(String[]args)
	{
		//Initializing part
		ExtentReports extentreports=new ExtentReports("",true);// Path where the report to be saved
		ExtentTest test=extentreports.startTest("");//Test case name
		
		
		//Logging part
		test.log(LogStatus.PASS, "Testcase is passed");
		test.log(LogStatus.FAIL, "Testcase is failed");
		test.log(LogStatus.INFO, "Testcase is failed");
		
		//Tear down part
		extentreports.endTest(test);
		extentreports.flush();
		extentreports.close();
		
	}

}
