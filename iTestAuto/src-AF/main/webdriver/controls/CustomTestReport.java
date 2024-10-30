/* Description : This class works for custom report generation. Reports are generated once all the 
 * execution is done. The class file have different functions relating to custom report generation.  
 * There are few functions and overridden functions like OnTestSuccess, OnTestFailure, 
 * onStart, logResult, onFinish, getScreenshotPath, getScreenshot.  This class file is used for
 * report generation with parallel and grid execution options set as true.
 * Author :
 * Comments Author: Raghothama
 * Date created : 03-Jun-2014
 * Modification comments: Added code to work with Parallel and Grid execution.
 * Modified by : Moshe George
 * Modified on : 03-Jun-2014  
 * Modified by : Nagaraju Vutukuri
 * Modified on : 04-Feb-2015  
 */

package controls;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.Augmenter;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeSuite;
import org.testng.internal.Utils;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;

public class CustomTestReport extends TestListenerAdapter {
	Logger logger = Logger.getLogger("controls");
	public static String projectName;
	ITestResult result3;
	//String imagepath = null;
	

	BufferedWriter out = null;
	public static ArrayList<String> failedList;
	public static ArrayList<String> successList;
	public static ArrayList<String> skippedList;

	public static ArrayList<String> testcaseSucessName;

	public static ArrayList<String> getTestcaseSucessName() {
		return testcaseSucessName;
	}

	public static ArrayList<String> testcaseFailName;

	public static ArrayList<String> getTestcaseFailName() {
		return testcaseFailName;
	}

	public static ArrayList<String> testcaseSkipName;

	public static ArrayList<String> getTestcaseSkipName() {
		return testcaseSkipName;
	}

	public static ArrayList<String> screenShotPath;

	public static ArrayList<String> failedBrowserName;
	public static ArrayList<String> passedBrowserName;
	public static ArrayList<String> skippedBrowserName;

	public static ArrayList<String> passedLanguage;
	public static ArrayList<String> failedLanguage;
	public static ArrayList<String> skippedLanguage;

	public static ArrayList<String> passedDescription;
	public static ArrayList<String> failedDescription;
	public static ArrayList<String> skippedDescription;

	public static ArrayList<String> failedBrowserVersion;
	public static ArrayList<String> passedBrowserVersion;
	public static ArrayList<String> skippedBrowserVersion;
	public static ExcelReadWrite excl;

	public static ArrayList<String> passTestName;
	public static ArrayList<String> failedTestName;
	
	public static boolean reportNotGenerated;

	int totTestCase = 0;
	String dateNow = "";
	int passPercentage = 0;
	String newFileNamePathofScreen = "";
	int count = 0;
	String dateStart = "";
	String dateStop = "";
	Date d1 = null;
	Date d2 = null;
	long diff;
	long diffSeconds;
	long diffMinutes;
	long diffHours;

	Date date = new Date();
	int i = 0;
	ClassLoader cl;
	ArrayList<String> screenShotPath1 = new ArrayList<String>();
	public static int testCounter = 0;
	/**
	 * Overridden method of TestNG Run on completion of each test and add the
	 * test-case name in to ArrayList, if test is failed.
	 */
	private int m_verbose = 0;
	private String m_testName = null;
	public CommonUtility comUtils;

	public CustomTestReport() {
		System.out.println("constructor of custom report ...........#########");
		//projectName = "Custom";
		failedList = new ArrayList<String>();
		testcaseSucessName = new ArrayList<String>();

		screenShotPath = new ArrayList<String>();

		successList = new ArrayList<String>();

		testcaseFailName = new ArrayList<String>();

		failedBrowserName = new ArrayList<String>();
		passedBrowserName = new ArrayList<String>();
		passedLanguage = new ArrayList<String>();
		failedLanguage = new ArrayList<String>();
		passedDescription = new ArrayList<String>();
		failedDescription = new ArrayList<String>();
		failedBrowserVersion = new ArrayList<String>();
		passedBrowserVersion = new ArrayList<String>();

		skippedList = new ArrayList<String>();

		testcaseSkipName = new ArrayList<String>();
		skippedBrowserName = new ArrayList<String>();
		skippedLanguage = new ArrayList<String>();
		skippedDescription = new ArrayList<String>();
		skippedBrowserVersion = new ArrayList<String>();

		passTestName = new ArrayList<String>();
		failedTestName = new ArrayList<String>();
		System.out
				.println("%%%% going to clear lists inside constructor  %%%%");
		failedList.clear();
		successList.clear();
		skippedList.clear();

		testcaseSucessName.clear();
		testcaseFailName.clear();

		testcaseSkipName.clear();

		screenShotPath.clear();

		failedBrowserName.clear();
		passedBrowserName.clear();
		skippedBrowserName.clear();

		passedLanguage.clear();
		failedLanguage.clear();
		skippedLanguage.clear();

		passedDescription.clear();
		failedDescription.clear();
		skippedDescription.clear();

		failedBrowserVersion.clear();
		passedBrowserVersion.clear();
		skippedBrowserVersion.clear();
		
		passTestName.clear();
		failedTestName.clear();
		
		System.out.println("%%%% all the list cleared %%%%");

		reportNotGenerated = true;
		excl=new ExcelReadWrite("Interim_Report");;
	}

	/**
	 * This method will be executed before each @BeforeTest.
	 * 
	 * @author Original Framework Code
	 * @param
	 * @param
	 * @return void
	 */
	@Override
	public void onStart(ITestContext testContext) {
		// m_testContexts.add(testContext);
		System.out.println("onStart....#");
		comUtils = new CommonUtility();
		// s_logger = SampleLogger.getSampleLogger();
		String abc = testContext.getCurrentXmlTest().getParameter(
				"googledriver");
		Calendar currentDate = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");

		if (i == 0) {
			dateStart = format.format(currentDate.getTime());
			i = i + 1;
		}

		String strPath = "";
		try {
			strPath = new File(".").getCanonicalPath() + "\\screenshots\\";
			System.out.println("strPath :" + strPath);
		} catch (IOException e) {

			e.printStackTrace();
		}

		System.out.println("DateStart :" + dateStart);
		System.out.println("Welcome for Test Suite Execution of <Customer>");

	}
    
	/*
	 * This method will be executed when an assertion failure/exception/ other
	 * failures occur. various lists associated with test failure is created.
	 *
	 * Modification comments: Added code to work with Update the Testcase excel sheet Execution status 
	 * Modified by : Moshe George
	 * Modified on : 03-Jun-2014  
	 * Modified by : Nagaraju Vutukuri
	 * Modified on : 04-Feb-2015  
	 */
	
	
	
	@Override
	public void onTestFailure(ITestResult result2) {
		System.out.println("onTestFailure....#");
		String currentBrowser, currBrowserVersion;

		System.out.println("In onTestFailure....");
		XmlClass iclass = result2.getTestClass().getXmlClass();
		System.out.println("Failed Class iClass.getName ------:"
				+ iclass.getName());
		System.out.println("Test name :" + iclass.getClass());
		Object currentInstance = result2.getInstance();
		WebDriver webDriver = ((DriverSetup) currentInstance).getDriver();
		currentBrowser = ((DriverSetup) currentInstance).getBrowser();
		currBrowserVersion = ((DriverSetup) currentInstance)
				.getBrowserversion();
		
		failedBrowserName.add(currentBrowser);
		failedBrowserVersion.add(currBrowserVersion);

		failedLanguage.add(((DriverSetup) currentInstance).getLanguage());
		failedDescription.add(((DriverSetup) currentInstance).getDescription());
		
		failedTestName.add(((DriverSetup) currentInstance).getTestName());
		String tcSheetName = ((DriverSetup) currentInstance).getCurrentSuiteSheetName();
		System.out.println("failedList class name :"
				+ ((result2.getTestClass().getName().substring((result2
						.getTestClass().getName().lastIndexOf(".")) + 1))));

		failedList.add(result2.getMethod().getMethodName());
		testcaseFailName
				.add((result2.getTestClass().getName().substring((result2
						.getTestClass().getName().lastIndexOf(".")) + 1)));
		result2.getTestClass().getName().toString();
		String failedClass = result2.getTestClass().getName();
		//String imagepath = getScreenShot(webDriver, currentBrowser, failedClass);
		//screenShotPath.add(imagepath);
		//System.out.println("Image path is " + imagepath);
		String failedTestName = iclass.getName();
		System.out.println("failed Test Name  is " + failedTestName);
		//String s2 = System.getProperty("user.dir");
		//String testcasepath = s2+"\\src\\resources\\TestCase\\TestCase.xlsx (file://src/resources/TestCase/TestCase.xlsx)";
		try {
			
			String imagepath = getScreenShot(webDriver, currentBrowser, failedClass);
			screenShotPath.add(imagepath);			
			System.out.println("Image path is " + imagepath);
			System.out.println("Test case Sheet name is  " + tcSheetName);
			System.out.println("failedClass name is  " + failedClass);
			//If Failure Write back the status to testcase.xlsx
			comUtils.updateTestCaseXLS(failedClass,tcSheetName,"Fail");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		/*
		 * List<ITestResult> itr = getConfigurationFailures(); Throwable ex =
		 * result2.getThrowable(); System.out.println("test>>>>>>>>" +
		 * getConfigurationFailures());
		 * System.out.println("ex.getStackTrace() of onTestSkipped() " +
		 * ex.getStackTrace());
		 * 
		 * for (Object o : getConfigurationFailures()) {
		 * 
		 * result2 = (ITestResult) o; ex = result2.getThrowable(); String abc =
		 * Utils.stackTrace(ex, false)[1]; System.out.println("In Failure abc :"
		 * + abc); String stackTrace = ""; if (ex != null) { if (m_verbose >= 2)
		 * { System.out .println("stackTraceUtils.stackTrace(ex, false)[0] :" +
		 * Utils.stackTrace(ex, false)[0]); stackTrace = Utils.stackTrace(ex,
		 * false)[0]; System.out.println("stackTrace :" + stackTrace); } } }
		 */
		excl.insertDataCustomeReport(failedClass,"FAIL");
		
		System.out
				.println("***********************************onTestFailure*********************************************");
	}

	/*
	 * This method will be executed when a test is skipped. various lists
	 * associated with test skip is created.
	 * Modification comments: Added code to work with Update the Testcase excel sheet Execution status 
	 * Modified by : Moshe George
	 * Modified on : 03-Jun-2014  
	 * Modified by : Nagaraju Vutukuri
	 * Modified on : 04-Feb-2015  
	 */
	
	
	/*public String getImagepath() {
	    return this.imagepath;
	}

	public void setImagepath(String imagepath) {
	    this.imagepath = imagepath;
	}*/

	@Override
	public void onTestSkipped(ITestResult result2) {
		System.out.println("onTestSkipped.Started...#");
		String currentBrowser, currBrowserVersion;

		XmlClass iclass = result2.getTestClass().getXmlClass();
		System.out.println("Skipped test getClass ------:" + iclass.getClass());
		Object currentInstance = result2.getInstance();
		WebDriver webDriver = ((DriverSetup) currentInstance).getDriver();
		currentBrowser = ((DriverSetup) currentInstance).getBrowser();
		currBrowserVersion = ((DriverSetup) currentInstance)
				.getBrowserversion();
		String tcSheetName = ((DriverSetup) currentInstance).getCurrentSuiteSheetName();
		System.out.println("Test case Sheet name is  " + tcSheetName);
		skippedBrowserName.add(currentBrowser);
		skippedBrowserVersion.add(currBrowserVersion);

		skippedLanguage.add(((DriverSetup) currentInstance).getLanguage());
		skippedDescription
				.add(((DriverSetup) currentInstance).getDescription());

		System.out.println("Skipped class name :"
				+ ((result2.getTestClass().getName().substring((result2
						.getTestClass().getName().lastIndexOf(".")) + 1))));

		skippedList.add(result2.getMethod().getMethodName());
		testcaseSkipName
				.add((result2.getTestClass().getName().substring((result2
						.getTestClass().getName().lastIndexOf(".")) + 1)));
		String SkippedClass = result2.getTestClass().getName();
		
		//
		/*
		 * Image capture commenter since the test has been skipped. String
		 * imagepath = getScreenShot(webDriver, currentBrowser, failedClass);
		 * screenShotPath.add(imagepath); System.out.println("Image path is " +
		 * imagepath);
		 */
		List<ITestResult> itr = getConfigurationSkips();
		Throwable ex = result2.getThrowable();
		System.out.println("test>>>>>>>>" + getConfigurationSkips());
		System.out.println("ex.getStackTrace() of onTestSkipped() "
				+ ex.getStackTrace());

		for (Object o : getConfigurationSkips()) {

			result2 = (ITestResult) o;
			ex = result2.getThrowable();
			String abc = Utils.stackTrace(ex, false)[1];
			System.out.println("In Skip, configuration failures are :" + abc);
			String stackTrace = "";
			if (ex != null) {
				if (m_verbose >= 2) {
					System.out
							.println("stackTraceUtils.stackTrace(ex, false)[0] :"
									+ Utils.stackTrace(ex, false)[0]);
					stackTrace = Utils.stackTrace(ex, false)[0];
					System.out.println("stackTrace :" + stackTrace);
				}
			}
		}
		excl.insertDataCustomeReport(SkippedClass,"SKIP");
		String skippedTestName = iclass.getName();
		try {
			System.out.println("Test case Sheet name is  " + tcSheetName);
			System.out.println("Skipped test name is  " + skippedTestName);
			comUtils.updateTestCaseXLS(skippedTestName,tcSheetName,"Skip");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out
				.println("***********************************onTestSkip**END*******************************************");
	}

	private void logResult(Throwable Ex, String status, String name,
			String description, String stackTrace, Object[] params,
			Class[] paramTypes) {
		StringBuffer msg = new StringBuffer(name);

		if (null != params && params.length > 0) {
			msg.append("(");

			// The error might be a data provider parameter mismatch, so make
			// a special case here
			if (params.length != paramTypes.length) {
				msg.append(name + ": Wrong number of arguments were passed by "
						+ "the Data Provider: found " + params.length + " but "
						+ "expected " + paramTypes.length + ")");
			} else {
				for (int i = 0; i < params.length; i++) {
					if (i > 0)
						msg.append(", ");
					msg.append(Utils.toString(params[i], paramTypes[i]));
				}

				msg.append(")");
			}
		}
		if (!Utils.isStringEmpty(description)) {
			msg.append("\n");
			for (int i = 0; i < status.length() + 2; i++) {
				msg.append(" ");
			}
			msg.append(description);
		}
		if (!Utils.isStringEmpty(stackTrace)) {
			msg.append("\n").append(stackTrace);
		}

		logResult(status, msg.toString());
	}

	private void logResult(String status, String message) {
		System.out.println("logResult....#");
		StringBuffer buf = new StringBuffer();
		if (!"".equals(status)) {
			buf.append(status).append(": ");
		}
		buf.append(message);

		// System.out.println("buf>>>>" + buf);
	}

	/**
	 * Overridden method of TestNG Run on completion of each test and add the
	 * test-case name in to ArrayList, if test is passed successfully.
	 * 
	 * @author Original Framework Code
	 * @param
	 * @param
	 * @return void
	 */
	
	/*
	 * Modification comments: Added code to work with Update the Testcase excel sheet Execution status 
	 * Modified by : Moshe George
	 * Modified on : 03-Jun-2014  
	 * Modified by : Nagaraju Vutukuri
	 * Modified on : 04-Feb-2015  
	 */
	
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("onTestSuccess....#");
		System.out.println("Passed method name is :"
				+ result.getMethod().getMethodName());
		System.out.println("successList class name :"
				+ (result.getTestClass().getName().substring((result
						.getTestClass().getName().lastIndexOf(".")) + 1)));
		testcaseSucessName
				.add((result.getTestClass().getName().substring((result
						.getTestClass().getName().lastIndexOf(".")) + 1)));
		successList.add(result.getMethod().getMethodName());
		Object currentClass = result.getInstance();
		String tcSheetName = ((DriverSetup) currentClass).getCurrentSuiteSheetName();
		passedBrowserName.add(((DriverSetup) currentClass).getBrowser());
		passedLanguage.add(((DriverSetup) currentClass).getLanguage());
		passedDescription.add(((DriverSetup) currentClass).getDescription());
		passedBrowserVersion.add(((DriverSetup) currentClass)
				.getBrowserversion());
		passTestName.add(((DriverSetup) currentClass)
				.getTestName());
		String sucessClass = result.getTestClass().getName();
		excl.insertDataCustomeReport(sucessClass,"PASS");
		XmlClass iclass = result.getTestClass().getXmlClass();
		String passedTestName = iclass.getName();
		try {
			System.out.println("Test case Sheet name is  " + tcSheetName);
			System.out.println("passedTestName name is  " + passedTestName);
			comUtils.updateTestCaseXLS(passedTestName,tcSheetName,"Pass");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Overridden method of TestNG Run on the completion of complete suite and
	 * take the failed tests name and write them in to the xml file format.
	 * Generates the testng-failed.xml file.
	 */
	@Override
	public void onFinish(ITestContext context) {
		System.out.println("Starting onFinish ()...#");
		
		String xlsPathFailedTC = null;

		try {
			xlsPathFailedTC = new File(".").getCanonicalPath()
					+ "\\reports\\xls\\";
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String suiteHeaderText = "<customer> Automation Test Suite Execution Report";
		System.out.println("tests completed are as follows");
		try {
			for (int i = 0; i < testcaseFailName.size(); i++) {
				System.out.println("Test case failed are $$$  "
						+ testcaseFailName.get(i));
			}
		} catch (Exception e) {
			System.out.println("No fail test cases completed yet ###");
		}
		try {
			for (int i = 0; i < testcaseSucessName.size(); i++) {
				System.out.println("Test case Passed are $$$  "
						+ testcaseSucessName.get(i));
			}
		} catch (Exception e) {
			System.out.println("No pass test cases completed yet ###");
		}

		try {
			for (int i = 0; i < testcaseSkipName.size(); i++) {
				System.out.println("Test case Skipped are $$$  "
						+ testcaseSkipName.get(i));
			}
		} catch (Exception e) {
			System.out.println("No skipped test cases yet ###");
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {

			e1.printStackTrace();
		}

		int totalTestCount = Integer.parseInt(comUtils
				.getPropertiesValue("TotalTestCount"));
		int failCount, passCount, skipCount;
		if (testcaseFailName == null)
			failCount = 0;
		else
			failCount = testcaseFailName.size();

		if (testcaseSucessName == null)
			passCount = 0;
		else
			passCount = testcaseSucessName.size();

		if (testcaseSkipName == null)
			skipCount = 0;
		else
			skipCount = testcaseSkipName.size();
		int totalTestExecuted = failCount + passCount + skipCount;
		System.out.println("Total test count is " + totalTestExecuted
				+ " fail count " + failCount + " pass count " + passCount
				+ "  Skip count " + skipCount);
		if ((totalTestExecuted >= totalTestCount) && reportNotGenerated
				&& ((failCount + passCount) > 0)) {
			System.out
					.println("All the test executed...............Preparing to write report\n total teat count id "
							+ totalTestCount);
			/*
			 * int totalTestCount =
			 * context.getSuite().getXmlSuite().getTests().size();
			 * context.getSuite().getXmlSuite().getTests(); ++testCounter; if
			 * (testCounter==totalTestCount) {
			 */
			try {

				String logoPath = "";
				try {
					logoPath = new File(".").getCanonicalPath() + "\\src\\img";
					System.out.println("logoPath :" + logoPath);
				} catch (IOException e) {

					e.printStackTrace();
				}

				Calendar currentDate1 = Calendar.getInstance();
				DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
				dateStop = format.format(currentDate1.getTime());
				System.out.println("dateStop :" + dateStop);
				try {
					d1 = format.parse(dateStart);
					d2 = format.parse(dateStop);
				} catch (Exception e) {
					e.printStackTrace();
				}
				// Get msec from each, and subtract.
				diff = d2.getTime() - d1.getTime();
				System.out.println("diff :" + diff);
				long diffSeconds = diff / 1000 % 60;
				long diffMinutes = diff / (60 * 1000) % 60;
				long diffHours = diff / (60 * 60 * 1000);
				String diffSecond = "";
				String diffMinute = "";
				if (diffSeconds >= 0 && diffSeconds <= 9) {
					System.out.println("==========");
					diffSecond = "0" + diffSeconds;

					System.out.println("diffSecond========== " + diffSecond);
				} else {
					System.out.println("----------->");
					diffSecond = "" + diffSeconds;
					System.out.println("diffSecond-------->:" + diffSecond);
				}
				if (diffMinutes >= 0 && diffMinutes <= 9) {
					diffMinute = "0" + diffMinutes;
				} else {
					diffMinute = "" + diffMinutes;
				}

				totTestCase = testcaseSucessName.size()
						+ testcaseFailName.size();
				System.out.println("totTestCase :" + totTestCase);

				Calendar currentDate = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat(
						"dd-MM-yyyy HH:mm:ss");
				dateNow = formatter.format(currentDate.getTime());
				String OUT_FOLDER = "";
				try {
					System.out
							.println(">>> :"
									+ (float) testcaseFailName.size()
									+ '\n'
									+ (float) testcaseSkipName.size()
									+ '\n'
									+ "......"
									+ (float) totTestCase
									+ '\n'
									+ "{{{{{{{{ :"
									+ (int) (((float) testcaseSucessName.size() / (float) totTestCase) * 100)
									+ '%');
					passPercentage = (int) ((((float) testcaseSucessName.size() / (float) totTestCase) * 100));
				} catch (Exception _ex) {
					_ex.printStackTrace();
				}
				try {

					System.out.println("testcaseSucessName :"
							+ testcaseSucessName + '\n' + "successList"
							+ successList + '\n' + "failedList :" + failedList
							+ '\n' + "testcaseFailName" + testcaseFailName+"testcaseskipName :"
							+ testcaseSkipName + '\n' + "skippedList"
							+ skippedList);
					/**        
					 * Reading the build number from the file           
					 */

					screenShotPath1.add("");

					DateFormat dateFormat = new SimpleDateFormat(
							"dd_MMM_yyyy__HH_mm_ss");
					Date date = new Date();

					OUT_FOLDER = new File(".").getCanonicalPath()
							+ "\\src\\customreport";
					PrintWriter fstream = new PrintWriter(new BufferedWriter(

					new FileWriter(new File(OUT_FOLDER, "Executionreport"
							+ dateFormat.format(date) + ".html"))));
					out = new BufferedWriter(fstream);

					out.write("<html>");
					out.write("<head>");
					out.write("<style type=text/css media=screen>");
					out.write(".popup_window {" + '\n');
					out.write("display: none;" + '\n');
					out.write("position: relative;" + '\n');
					out.write("left: 0px;" + '\n');
					out.write("top: 0px;" + '\n');
					out.write("padding: 10px;" + '\n');
					out.write(" background-color: #E6E6D6;" + '\n');
					out.write("font-family: Lucida Console, Courier New, Courier, monospace;" + '\n');
					out.write("text-align: left;" + '\n');
					out.write("font-size: 8pt;" + '\n');
					out.write("width: 500px;" + '\n');
					out.write("}" + '\n');

					out.write("body        {" + '\n');
					out.write("font-family: verdana, arial, helvetica, sans-serif; font-size: 80%;" + '\n');
					out.write(" }" + '\n');
					out.write("table {" + '\n');
					out.write(" font-size: 100%;" + '\n');
					out.write("}" + '\n');
					out.write("pre {" + '\n');
					out.write("}" + '\n');
					/*
					 * -- heading
					 * ----------------------------------------------------
					 * ------------------
					 */
					out.write("	h1 {" + '\n');
					out.write("font-size: 16pt;" + '\n');
					out.write("color: gray;" + '\n');
					out.write("}" + '\n');
					out.write(".heading {" + '\n');
					out.write(" margin-top: 0ex;" + '\n');
					out.write("margin-bottom: 1ex;" + '\n');
					out.write("}" + '\n');
					out.write(".heading .attribute {" + '\n');
					out.write(" margin-top: 1ex;" + '\n');
					out.write("margin-bottom: 0;" + '\n');
					out.write("}" + '\n');

					out.write(".heading .description {" + '\n');
					out.write(" margin-top: 4ex;" + '\n');
					out.write(" margin-bottom: 6ex;" + '\n');
					out.write(" }" + '\n');

					/*
					 * -- css div popup
					 * ----------------------------------------------
					 * --------------------------
					 */
					out.write("a.popup_link {" + '\n');
					out.write("}" + '\n');

					out.write("a.popup_link:hover {" + '\n');
					out.write("    color: red;" + '\n');
					out.write("}" + '\n');

					out.write(".popup_window {" + '\n');
					out.write(" display: none;" + '\n');
					out.write(" position: relative;" + '\n');
					out.write(" left: 0px;" + '\n');
					out.write(" top: 0px;" + '\n');
					/* border: solid #627173 1px; */
					out.write("  padding: 10px;" + '\n');
					out.write(" background-color: #E6E6D6;" + '\n');
					out.write(" font-family: Lucida Console, Courier New, Courier, monospace;" + '\n');
					out.write(" text-align: left;" + '\n');
					out.write("  font-size: 8pt;" + '\n');
					out.write(" width: 500px;" + '\n');
					out.write("}" + '\n');
					out.write("<title>");
					out.write("(Customer)" + " Detailed Reports");
					out.write("</title>");
					out.write("</style>");
					out.write("</head>");
					out.write("<body bgcolor='#C0BEBE' style='text-align:center;margin:0;font-family: tahoma, courier, serif;'>");
					out.write("<script language=javascript type=text/javascript>" + '\n');
					out.write("function showTestDetail(div_id){" + '\n');
					out.write("var details_div = document.getElementById(div_id)" + '\n');
					out.write("alert(details_div);" + '\n');
					out.write("var displayState = details_div.style.display" + '\n');
					out.write("alert(displayState);" + '\n');
					// alert(displayState)
					out.write("if (displayState != 'block' ) {" + '\n');
					out.write("alert" + "(" + "'INIF'" + ")" + ";" + '\n');
					out.write("displayState = 'block'" + '\n');
					out.write("details_div.style.display = 'block'" + '\n');
					out.write("}" + '\n');
					out.write("else {" + '\n');
					out.write("details_div.style.display = 'none'" + '\n');
					out.write("}" + '\n');
					out.write("}" + '\n');
					out.write("</script>");
					out.write("<center>");
					out.write("<div style='background-color:#FFFFFF;border:3px solid #867B7B; width:80%;padding-top:15px'>");
					// out.write("<table>");
					out.write("<table width=85%>");
					out.write("<tr>");
					out.write("<td style='padding-top:5px;text-align: left'>");
					out.write("<img src='..//img//logo.JPG' width=150px height=50px/>");
					out.write("</td>");
					out.write("<td align='center' style='padding-top:10px'>");
					out.write("<span style='font-size: 2em;font-family: tahoma, courier, serif'>"
							+ suiteHeaderText + "</span>");
					out.write("</td>");
					out.write("</tr>");
					out.write("</table>");
					out.write("<br>");
					out.write("<table width=85%><tr><td text-align: left>");
					out.write("<div class='heading'>");
					out.write("<h1>Test Summary</h1>");
					out.write("<p class='attribute'><strong>Date of Execution:</strong>		"
							+ dateNow + "</p>");
					out.write("<p class='attribute'><strong>Duration:</strong>  "
							+ diffHours
							+ ":"
							+ diffMinute
							+ ":"
							+ diffSecond
							+ "</p>");
					out.write("<p class='attribute'><strong></strong><b>Total Number of Test Cases:</b><font color=#254117>  "
							+ totTestCase + "</font>");
					out.write("<p class='attribute'><strong></strong><b>	"
							+ "Total Number of Test Cases Passed:		" + "</b>"
							+ testcaseSucessName.size() + "");
					// out.write("<p class='attribute'><strong></strong><font color=#4AA02C>	"+"Total Number of Test Cases Passed:		"+"<b>"+testcaseSucessName.size()+""+"</b></font>");
					out.write("<p class='attribute'><strong></strong><b> "
							+ "Total Number of Test Cases Failed:		" + "</b>"
							+ testcaseFailName.size() + "");
					out.write("<p class='attribute'><strong></strong><b>	"
							+ "Total Number of Test Cases Skipped:		" + "</b>"
							+ testcaseSkipName.size() + "");		
					out.write("<p class='attribute'><strong>Pass Percentage:</strong><font color=#2F4B21>	"
							+ passPercentage + '%' + "</font></p>");
					out.write("<p class='description'></p>");
					out.write("</div>");
					out.write("</td>");
					out.write("</tr>");
					out.write("</table>");

					if (testcaseSucessName != null
							&& testcaseSucessName.size() > 0) {
						out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Passed</h4>");
						out.write("<table  border=1 cellspacing=1    cellpadding=1 width=85%>");
						out.write("<tr> ");
						out.write("<td align=center width=5%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Sl.No:</b></td>");
						out.write("<td align=center width=20% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Test Case Name</b></td>");
						out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Browser</b></td>");
						out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Language</b></td>");
						out.write("<td align=center width=30% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Description</b></td>");
						out.write("<td align=center WIDTH=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Results</b></td>");
						out.write("</tr>");

						for (int j = 0; j < testcaseSucessName.size(); j++) {
							count = countValue();
							out.write("<tr> ");
							out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ count + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ testcaseSucessName.get(j) + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ passedBrowserName.get(j)
									+ " -v "
									+ passedBrowserVersion.get(j) + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ passedLanguage.get(j) + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ passedDescription.get(j) + "</td>");
							// out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
							// + testcaseSucessName.get(j) + "</td>");
							if (successList.get(j) != null
									|| !successList.get(j).equals("")) {

//								out.write("<td align= center width=5% bgcolor=#58FA82><FONT COLOR=#153E7E FACE=Arial SIZE=2>PASS</td>\n");
								out.write("<td align=left width=10% bgcolor=#58FA82><FONT COLOR=#153E7E FACE=Arial SIZE=2><b> <a href=\""
										+ xlsPathFailedTC
										+ ""
										+ passTestName.get(j)
										+ ".xls"
										+ "\">"
										+ "Detailed Report"
										+ "</a>" + "</b></td>");

							}
						}
					}
					out.write("</tr>");
					out.write("</table>");

					if (testcaseSkipName != null
							&& testcaseSkipName.size() > 0) {
						out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Skipped</h4>");
						out.write("<table  border=1 cellspacing=1    cellpadding=1 width=85%>");
						out.write("<tr> ");
						out.write("<td align=center width=5%  align=center bgcolor=#153E7E><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>Sl.No:</b></td>");
						out.write("<td align=center width=20% align=center bgcolor=#153E7E><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>Test Case Name</b></td>");
						out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>Browser</b></td>");
						out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>Language</b></td>");
						out.write("<td align=center width=30% align=center bgcolor=#153E7E><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>Description</b></td>");
						out.write("<td align=center WIDTH=10% align=center bgcolor=#153E7E><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>Results</b></td>");
						out.write("</tr>");

						for (int j = 0; j < testcaseSkipName.size(); j++) {
							count = countValue();
							out.write("<tr> ");
							out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ count + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ testcaseSkipName.get(j) + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ skippedBrowserName.get(j)
									+ " -v "
									+ skippedBrowserVersion.get(j) + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ skippedLanguage.get(j) + "</td>");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2>"
									+ skippedDescription.get(j) + "</td>");
							// out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
							// + testcaseSucessName.get(j) + "</td>");
							if (skippedList.get(j) != null
									|| !skippedList.get(j).equals("")) {

								out.write("<td align= center width=5% bgcolor=#FFA500><FONT COLOR=#153E7E FACE=Arial SIZE=2>SKIPPED</td>\n");

							}
						}
					}
					out.write("</tr>");
					out.write("</table>");
					
				
					

					if (testcaseFailName.size() > 0) {

						out.write("<br>");
						out.write("<br>");
						out.write("<br>");
						out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Failed</h4>");
						out.write("<table  border=1 cellspacing=1    cellpadding=1 width=85%>");
						out.write("<tr> ");
						out.write("<td align=center width=5%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Sl.No:</b></td>");
						out.write("<td align=center width=20% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Test Case Name</b></td>");
						out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Browser</b></td>");
						out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Language</b></td>");
						out.write("<td align=center width=30% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Description</b></td>");
						// out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>TestCase Summary</b></td>");
						out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Results</b></td>");
						//out.write("<td align=center width=15% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Screen Shot</b></td>");
						out.write("</tr>");
						if (testcaseFailName != null) {

							for (int j = 0; j < testcaseFailName.size(); j++) {
								count = countValue();
								out.write("<tr> ");

								out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
										+ count + "</b></td>");
								out.write("<td align=left width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
										+ testcaseFailName.get(j) + "</b></td>");

								out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
										+ failedBrowserName.get(j)
										+ " -v "
										+ failedBrowserVersion.get(j)
										+ "</b></td>");
								out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
										+ failedLanguage.get(j) + "</b></td>");
								out.write("<td align=left width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
										+ failedDescription.get(j)
										+ "</b></td>");
								// out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								// + testcaseFailName.get(j) + "</b></td>");

								if (failedList.get(j) != null
										|| !failedList.get(j).equals("")) {
									out.write("<td align=left width=10% bgcolor=#FA5858><FONT COLOR=#153E7E FACE=Arial SIZE=2><b> <a href=\""
											+ xlsPathFailedTC
											+ ""
											+ failedTestName.get(j)
											+ ".xls"
											+ "\">"
											+ "Detailed Report"
											+ "</a>" + "</b></td>");
									
								/*	out.write("<td align= center  bgcolor=#FA5858><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>FAIL"
											+ "</b>"
											+ "<div id="
											+ "'"
											+ count
											+ "'"
											+ " class=popup_window>"
											+ "<div style='text-align: right; color:red;cursor:pointer'>"
											+ "<a onfocus='this.blur();' onclick=\"document.getElementById("
											+ "'"
											+ count
											+ "'"
											+ ").style.display='none'\">"
											+ "[x]"
											+ "</a>"
											+ "</div>"
											+ "<pre></pre>"
											+ "</div>"
											+ "</td>\n");*/

									// }
								}
								/*if (screenShotPath.get(j) != null) {
									out.write("<td align=center width=10%><FONT color=red FACE=Arial SIZE=2><b><a href=\""
											+ screenShotPath.get(j)
											+ "\">"
											+ "<font color=#FF0000>Screen Shot</font></a>"
											+ "</b></td>");
								} else {
									out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>&nbsp;</b></td>");
									out.write("</tr>");
								}*/
							}
						}
					}
					out.write("</tr>");
					out.write("</table>");
					out.write("<br><br><br<center><a href='#' onClick='window.print()'><span style='font-size:10pt'>Print Report</span><center></a><br>");
					out.write("</div>");
					out.write("</center>");
					out.write("<span style='font-size:8pt;text-align:center'>&copy; IBS Software Pvt Ltd</span>");
					// out.write("</table>");

					out.write("</body>");
					out.write("</html>");

					// }
					System.out.println("FINISHED HERE");
					count = 0;
					out.close();

					reportNotGenerated = false;

				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

	private String getScreenShotPath(String strScreenName) {
		try {

			// Code to get screen resolution
			// Get the default toolkit
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			// Get the current screen size
			Dimension scrnsize = toolkit.getScreenSize();
			// Print the screen size
			System.out.println("Screen size : " + scrnsize);

			// Get the dir path
			File directory = new File(".");
			// System.out.println(directory.getCanonicalPath());

			// get current date time with Date() to create unique file name
			DateFormat dateFormat = new SimpleDateFormat(
					"dd_MMM_yyyy__HH_mm_ss");
			// get current date time with Date()
			// Date date = null;
			// System.out.println(dateFormat.format(date));

			// To identify the system
			InetAddress ownIP = InetAddress.getLocalHost();
			// System.out.println("IP of my system is := "+ownIP.getHostAddress());

			newFileNamePathofScreen = directory.getCanonicalPath()
					+ "\\screenshots\\" + strScreenName + "_"
					+ dateFormat.format(date) + ".jpg";
			System.out.println("newFileNamePathofScreen : "
					+ newFileNamePathofScreen);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return newFileNamePathofScreen;

	}

	public int countValue() {
		count = count + 1;
		return count;

	}

	private void ScreenShot(String strScreen) {
		try {

			String newFileNamePath;

			// Code to get screen resolution
			// Get the default toolkit
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			// Get the current screen size
			Dimension scrnsize = toolkit.getScreenSize();
			// Print the screen size
			System.out.println("Screen size : " + scrnsize);

			// Get the dir path
			File directory = new File(".");
			// System.out.println(directory.getCanonicalPath());

			// get current date time with Date() to create unique file name
			DateFormat dateFormat = new SimpleDateFormat(
					"dd_MMM_yyyy__HH_mm_ss");
			// get current date time with Date()

			// System.out.println(dateFormat.format(date));

			// To identify the system
			InetAddress ownIP = InetAddress.getLocalHost();
			// System.out.println("IP of my system is := "+ownIP.getHostAddress());

			newFileNamePath = directory.getCanonicalPath() + "\\screenshots\\"
					+ strScreen + "_" + dateFormat.format(date) + ".png";
			System.out.println("NewFileNamePath :" + newFileNamePath);

			// Capture the screen shot of the area of the screen defined by the
			// rectangle
			Robot robot = new Robot();
			BufferedImage bi = robot.createScreenCapture(new Rectangle(1280,
					1024));
			ImageIO.write(bi, "png", new File(newFileNamePath));
			Reporter.log(newFileNamePath);

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.getMessage();
		}
	}

	public ITestResult returnITestRes() {
		System.out.println("returnITestRes....#");
		return result3;
	}

	public String getScreenShot(WebDriver driver, String currentBrowser,
			String failedClass) {
		System.out.println("getScreenShot....#");
		String newFileNamePath = null;
		Calendar calendar = null;
		DateFormat dateFormat = null;
		File screenshot = null;
		try {
			// s_logger.write("entered simplesearchAddRooms_normal sequence");
			WebDriver augmentedDriver = new Augmenter().augment(driver);
			File directory = new File(".");

			calendar = Calendar.getInstance();
			// sFormatedDate = formatedDate(calendar, sFormat);

			date = calendar.getTime();
			dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");

			screenshot = ((TakesScreenshot) augmentedDriver)
					.getScreenshotAs(OutputType.BYTES.FILE);
			/* try { */
			newFileNamePath = System.getProperty("user.dir")
					+ "\\screenshots\\" + "_" + currentBrowser + "_"
					+ failedClass + "_" + dateFormat.format(date) + ".png";
			System.out.println("NewFileNamePath :" + newFileNamePath);

			FileUtils.copyFile(screenshot, new File(newFileNamePath));

			/*
			 * } catch (IOException e) {
			 * 
			 * 
			 * }
			 */

			/*
			 * Capture the screen shot of the area of the screen defined by the
			 * rectangle Robot robot = new Robot(); BufferedImage bi =
			 * robot.createScreenCapture(new Rectangle(1280, 1024));
			 */
			Reporter.log(newFileNamePath);
			// s_logger.write("exiting simplesearchAddRooms_normal sequence");
		} catch (org.openqa.selenium.WebDriverException e) {
			// s_logger.write("entered simplesearchAddRooms_WebDriverException_catch sequence");
			calendar = Calendar.getInstance();
			// sFormatedDate = formatedDate(calendar, sFormat);

			date = calendar.getTime();
			dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");
			screenshot = new File(System.getProperty("user.dir")
					+ "\\src\\resources\\noScreenShot.png");
			try {
				newFileNamePath = System.getProperty("user.dir")
						+ "\\screenshots\\" + "_" + currentBrowser + "_"
						+ failedClass + "_" + dateFormat.format(date) + ".png";
				System.out.println("NewFileNamePath :" + newFileNamePath);
				FileUtils.copyFile(screenshot, new File(newFileNamePath));
			} catch (IOException e1) {

				e1.printStackTrace();
			}
			e.printStackTrace();
			// s_logger.write("exiting simplesearchAddRooms_WebDriverException_catch sequence");
		} catch (IOException e) {
		}
		return newFileNamePath;
	}

}
