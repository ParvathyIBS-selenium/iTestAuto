package controls;

/**
 * ExtentReports is a HTML reporting library for Java which is extremely easy to use
 * and creates beautiful execution reports. It shows test and step summary, test steps
 * and status in a toggle view for quick analysis. 
 */

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import common.DriverSetup;

public class ExtentReporterNG extends DriverSetup implements IReporter {
    private ExtentReports extent;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
	    String outputDirectory) {
	extent = new ExtentReports(outputDirectory + File.separator
		+ "ExtentReportsTestNG.html", true);

	for (ISuite suite : suites) {
	    Map<String, ISuiteResult> result = suite.getResults();

	    for (ISuiteResult r : result.values()) {
		ITestContext context = r.getTestContext();

		buildTestNodes(context.getPassedTests(), LogStatus.PASS);
		buildTestNodes(context.getFailedTests(), LogStatus.FAIL);
		buildTestNodes(context.getSkippedTests(), LogStatus.SKIP);
	    }
	}

	extent.flush();
	extent.close();
    }

    private void buildTestNodes(IResultMap tests, LogStatus status) {
	ExtentTest test;

	if (tests.size() > 0) {
	    for (ITestResult result : tests.getAllResults()) {
		test = extent.startTest(result.getMethod().getMethodName());
		test.getTest().setStartedTime(getTime(result.getStartMillis()));
		test.getTest().setEndedTime(getTime(result.getEndMillis()));

		for (String group : result.getMethod().getGroups())
		    test.assignCategory(group);

		String message = "Test " + status.toString().toLowerCase()
			+ "ed";

		if (result.getThrowable() != null)
		    message = result.getThrowable().getMessage();
		
		test.log(status, message);

		extent.endTest(test);
	    }
	}
    }

    private Date getTime(long millis) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTimeInMillis(millis);
	return calendar.getTime();
    }
}