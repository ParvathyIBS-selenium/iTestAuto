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

import common.DriverSetup;


public class FullTestReportSoftAssert extends TestListenerAdapter {
	 /** Primitive type name -> class map. */
	  private static final Map PRIMITIVE_NAME_TYPE_MAP = new HashMap();

	ITestResult result3 ;
	// FileWriter fstream=null;
	BufferedWriter out = null;
	ArrayList<String> failedList = new ArrayList<String>(); // stored the tests
	ArrayList<String> testcaseSucessName = new ArrayList<String>();
	public static ArrayList<String> screenShotPath = new ArrayList<String>();
	
	ArrayList<String> successList = new ArrayList<String>(); // stored the tests
	ArrayList<String> testcasefailName = new ArrayList<String>();
	ArrayList<String> testcaseFailName = new ArrayList<String>();
		int totTestCase = 0;
	String dateNow = "";
	int passPercentage = 0;
	String newFileNamePathofScreen = "";
	int count = 0;
	String dateStart = "";   
	String dateStop = "";
	Date d1 = null;        
	Date d2 = null;
	long diff ;
	long diffSeconds;                 
	long diffMinutes;         
	long diffHours;
	ArrayList array = new ArrayList();
	Date date = new Date();
	int i=0;
	 ClassLoader cl;
	/**
	 * Overridden method of TestNG Run on completion of each test and add the
	 * test-case name in to ArrayList, if test is failed.
	 */
	private int m_verbose = 0;
	private String m_testName = null;
	
	/*@Override
    public void onTestStart(ITestResult result) {
        System.out.println("before starting the test");
        Object[] params= result.getParameters();
        System.out.println("params.length :"+params.length);
        ITestContext testContext = null;
        for(int i=0;i<params.length;i++){
        	System.out.println(">>>>>>>>>>>>>>> :"+(ITestResult)params[i]);
        }
    } */
	
	 @Override
	  public void onStart(ITestContext testContext) {
		  //m_testContexts.add(testContext);
	
		 String abc = testContext.getCurrentXmlTest().getParameter("googledriver");
		 Calendar currentDate = Calendar.getInstance();
		 DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		 
		 if(i==0)
		 {
			 dateStart = format.format(currentDate.getTime());
			 i=i+1;
		 }
		 
		 String strPath = "";
		 try {
			 strPath = new File(".").getCanonicalPath()+ "\\screenshots\\";
			System.out.println("strPath :"+strPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		 
		    // Get all files in directory 
		    /*	File  directory = new File(strPath);
		    	File[] files = directory.listFiles(); 
		    	System.out.println("files :"+files);
		    	for (File file : files){
		    		System.out.println("files  :"+file.toString());
		    		//boolean success = file.delete();

		    		if (!file.delete()) 
		    			{ 
		    		    // Failed to delete file 
		    			System.out.println("Failed to delete "+file); 
		    			} 
		    }*/
		 
		 System.out.println("DateStart :"+dateStart);
		 System.out.println("Welcome for Test Suite Execution..........."+abc);
		 
	  }
	 
	/*@BeforeSuite (groups = "init")
	public void loadContext(ITestContext context){
	System.out.println("IN loadContext");
	String abc = context.getCurrentXmlTest().getParameter("googledriver");
	        XmlSuite xmlSuite = context.getSuite().getXmlSuite();
	        Map<String, String> allParameters = xmlSuite.getAllParameters();

	        for (Map.Entry<String, String> e : allParameters.entrySet()){
	                context.setAttribute(e.getKey(), e.getValue());
	        }

	}*/
	@Override
	public void onTestFailure(ITestResult result2) {
		System.out.println("In onTestFailure....");
		// System.out.println("getMethodName() :"
		// + result.getMethod().getMethodName());
		XmlClass iclass =  result2.getTestClass().getXmlClass();
		System.out.println("werwerwerwrwrw :"+iclass.getName());
		System.out.println("Test name :"+iclass.getClass());
		
		/*
		Class c = (Class)PRIMITIVE_NAME_TYPE_MAP.get(iclass.getName());
		
	    if (c == null) {
	      // No primitive, try to load it from the given ClassLoader
	      try {
	    	 
	        c = cl.loadClass(iclass.getName());
		System.out.println("ccccccccccccc :"+c.toString());
	      }catch(Exception e){
			e.printStackTrace();
		}
		ClassLoader classLoader = WriteFailedTestsReport.class.getClassLoader();
		Object o5 = null;
		try {
//			Class c   = Class.forName(iclass.getName());
//			Class c1 = (Class)iclass.getName();
//			
			 o5 = c.newInstance();
			 System.out.println("fsfsfsdfsdsdd"+o5.getClass());
	        Class aClass = classLoader.loadClass(iclass.getName());
	        Object o1 = aClass.newInstance();
	       
	       // System.out.println("aClass.getName() = " + aClass.);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		System.out.println("failedList class name :"
				+ ((result2.getTestClass().getName().substring((result2.getTestClass().getName().lastIndexOf("."))+1))));
		
		failedList.add(result2.getMethod().getMethodName());
		testcaseFailName.add((result2.getTestClass().getName().substring((result2.getTestClass().getName().lastIndexOf("."))+1)));
		// System.out.println(result.getTestClass().getName());
	
		String st = result2.getTestClass().getName();
		ScreenShot((result2.getTestClass().getName().substring((result2.getTestClass().getName().lastIndexOf("."))+1)));
		String pathName = getScreenShotPath((result2.getTestClass().getName().substring((result2.getTestClass().getName().lastIndexOf("."))+1)));
		System.out.println("pathName :" + pathName);
		screenShotPath.add(pathName);
		
				List<ITestResult> itr = getConfigurationFailures();
		//System.out.println(itr.add(result));
		//System.out.println("int  >" + itr.size());
		Throwable ex = result2.getThrowable();
		System.out.println("test>>>>>>>>" + getConfigurationFailures());
		System.out.println("ex.getStackTrace() of onTestSkipped() "+ex.getStackTrace());
		
		for (Object o : getConfigurationFailures()) {
		
			 result2 = (ITestResult) o;
				
			 ex = result2.getThrowable();
		
		
			String abc = Utils.stackTrace(ex, false)[1];
			
			System.out.println("In Failure abc :"+abc);
			
			/*String abc2[] = Utils.stackTrace(ex, false);
			System.out.println("abc2.length :"+abc2.length);
			for(int k=0;k<abc2.length;k++){
				System.out.println("abc2[k] :"+abc2[k]);
				
			}*/
			//System.out.println("stackTraceUtils.stackTrace(ex, false)[1] :"+Utils.stackTrace(ex, false)[1]);
			String stackTrace = "";
			if (ex != null) {
				if (m_verbose >= 2) {
					System.out.println("stackTraceUtils.stackTrace(ex, false)[0] :"+Utils.stackTrace(ex, false)[0]);
					stackTrace = Utils.stackTrace(ex, false)[0];
					System.out.println("stackTrace :"+stackTrace);
				}
			}
			//failMsg.add(ex.toString());
			//stackMsg.add(abc);
			
			/*logResult(ex,"FAILED CONFIGURATION", Utils.detailedMethodName(tr
					.getMethod(), false), tr.getMethod().getDescription(),
					stackTrace, tr.getParameters(), tr.getMethod().getMethod()
							.getParameterTypes());*/
		}
	    
		System.out.println("***********************************onTestFailure*********************************************");
	}

	private void logResult(Throwable Ex,String status, String name, String description,
			String stackTrace, Object[] params, Class[] paramTypes) {
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
		StringBuffer buf = new StringBuffer();
		if (!"".equals(status)) {
			buf.append(status).append(": ");
		}
		buf.append(message);

		//System.out.println("buf>>>>" + buf);
	}
	
	/*@Override
	  public void onTestSkipped(ITestResult result) {
		System.out.println("U R IN SKIPPED");
		
		//result.getTestClass().getName().
		classnameskippedTests.add(result.getTestClass().getName());
		System.out.println("classnameskippedTests :"+classnameskippedTests);
	    m_skippedTests.add(result.getMethod().getMethodName());
	    System.out.println("m_skippedTests :"+m_skippedTests);
	    List<ITestResult> itr = getConfigurationFailures();
		
		//System.out.println("int  >" + itr.
		// .itr.System.out.println(result = getConfigurationFailures());
		
		String strStackTrace = "";
		
		Throwable ex = result.getThrowable();
		System.out.println("ex.getStackTrace() of onTestSkipped() "+ex.getStackTrace());

		for (Object o : getConfigurationFailures()) {
			System.out.println("onTestSkipped");

			ITestResult tr = (ITestResult) o;
				//tr.getParameters();
			
		 ex = tr.getThrowable();
			//ex.printStackTrace();
			
			strStackTrace = Utils.stackTrace(ex, false)[1];
			System.out.println("In Skiped strStackTrace :"+strStackTrace);
			//System.out.println("stackTraceUtils.stackTrace(ex, false)[1] :"+Utils.stackTrace(ex, false)[1]);
			String stackTrace = "";
			if (ex != null) {
				if (m_verbose >= 2) {
					System.out.println("stackTraceUtils.stackTrace(ex, false)[0] :"+Utils.stackTrace(ex, false)[0]);
					stackTrace = Utils.stackTrace(ex, false)[0];
					System.out.println("stackTrace :"+stackTrace);
				}
			}
			
			failMsg.add(ex.toString());
			stackMsg.add(strStackTrace);
			//System.out.println("stackMsg :"+stackMsg);
			//System.out.println("failMsg :"+failMsg);
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>onTestSkipped>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	  }
	
	  */
	
	//******************************************************************************************************8
	
	/*@Override
	public void onTestFailure1(ITestResult result) {
		System.out.println("getMethodName() :"
				+ result.getMethod().getMethodName());
		System.out.println("failedList class name :"
				+ result.getTestClass().getName());
		failedList.add(result.getMethod().getMethodName());
		testcaseFailName.add(result.getTestClass().getName());
		String pathName = ScreenShot();
		System.out.println("pathName :" + pathName);
		screenShotPath.add(pathName);
	}*/

	/**
	 * Overridden method of TestNG Run on completion of each test and add the
	 * test-case name in to ArrayList, if test is passed successfully.
	 */
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println("getMethodName22456() :"
				+ result.getMethod().getMethodName());
		System.out.println("successList class name :"
				+ (result.getTestClass().getName().substring((result.getTestClass().getName().lastIndexOf("."))+1)));
		testcaseSucessName.add((result.getTestClass().getName().substring((result.getTestClass().getName().lastIndexOf("."))+1)));
		successList.add(result.getMethod().getMethodName());
	}
 
	/**
	 * Overridden method of TestNG Run on the completion of complete suite and
	 * take the failed tests name and write them in to the xml file format.
	 * Generates the testng-failed.xml file.
	 */
	@Override
	public void onFinish(ITestContext context) {
System.out.println("Count No of Finish............................................."+count);
		/*
		 * int i = 60, j = 62, k = 47; String color = "green", result =
		 * "passed"; char lessThen = (char) i; // variable printing for '<'
		 * character char greaterThen = (char) j; // variable printing for '>'
		 * character char forwardSlash = (char) k; // variable printing for '/'
		 * character
		 */
		try{

						  
		String logoPath = "";
		try {
			logoPath = new File(".").getCanonicalPath()+ "\\src\\img";
			System.out.println("logoPath :"+logoPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		 Calendar currentDate1 = Calendar.getInstance();
		 DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		 dateStop = format.format(currentDate1.getTime());
		 System.out.println("dateStop :"+dateStop);
		 try 
			{           
				d1 = format.parse(dateStart); 
				d2 = format.parse(dateStop);  
				} catch (Exception e) 
				{             e.printStackTrace();        
			}      
				// Get msec from each, and subtract.
				 diff = d2.getTime() - d1.getTime();
				 System.out.println("diff :"+diff);
				 long diffSeconds = diff / 1000 % 60;    
					long diffMinutes = diff / (60 * 1000) % 60;   
					long diffHours = diff / (60 * 60 * 1000);      
					String diffSecond = "";
					String diffMinute = "";
					if(diffSeconds>=0 && diffSeconds<=9){
						System.out.println("==========");
						 diffSecond="0"+diffSeconds;
						
						System.out.println("diffSecond========== "+diffSecond);
					}else{
						System.out.println("----------->");
						diffSecond=""+diffSeconds;
						 System.out.println("diffSecond-------->:"+diffSecond);
					}
					if(diffMinutes>=0 && diffMinutes<=9){
						diffMinute = "0"+diffMinutes;
					}else{
						diffMinute=""+diffMinutes;
					}
	
		//System.out.println("onFinish stackMsg :"+stackMsg.size());
		//System.out.println("onFinish stackMsg Value :"+stackMsg);
		totTestCase = testcaseSucessName.size() + testcaseFailName.size();
		//+classnameskippedTests.size();
		System.out.println("totTestCase :" + totTestCase);

		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		dateNow = formatter.format(currentDate.getTime());
		String OUT_FOLDER = "";
			try {
				System.out
						.println(">>> :"
								+ (float) testcaseFailName.size()
								+ '\n'
								+ "......"
								+ (float) totTestCase
								+ '\n'
								+ "{{{{{{{{ :"
								+ (int) (((float) testcaseSucessName.size() / (float) totTestCase) * 100)
								+ '%');
				passPercentage = (int) ((((float) testcaseSucessName.size() / (float) totTestCase) * 100));

				// passPercentage= passPercentage+'%';
				// System.out.println("passPercentage :"+passPercentage+'%');
			} catch (Exception _ex) {
				_ex.printStackTrace();
			}
		try {

			System.out.println("testcaseSucessName :" + testcaseSucessName
					+ '\n' + "successList" + successList + '\n'
					+ "failedList :" + failedList + '\n' + "testcaseFailName"
					+ testcaseFailName);
			/**
			 * Reading the build number from the file
			 */
			ArrayList screenShotPath1 = new ArrayList();
			screenShotPath1.add("");
			
			DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");
			Date date = new Date();

			OUT_FOLDER = new File(".").getCanonicalPath()+"\\src\\customreport";
			PrintWriter fstream = new PrintWriter(new BufferedWriter(
					//new FileWriter(new File(OUT_FOLDER, "Executionreport.html" + dateStop))));
					new FileWriter(new File(OUT_FOLDER, "Executionreport"+dateFormat.format(date)+".html"))));
			out = new BufferedWriter(fstream);
			
			//if (testcaseSucessName.size() > 0) {

			out.write("<html>");
			out.write("<head>");
			out.write("<style type=text/css media=screen>");
			out.write(".popup_window {"+'\n');
			out.write("display: none;"+'\n');
			out.write("position: relative;"+'\n');
			out.write("left: 0px;"+'\n');
			out.write("top: 0px;"+'\n');
			    /*border: solid #627173 1px; */
			out.write("padding: 10px;"+'\n');
			out.write(" background-color: #E6E6D6;"+'\n');
					out.write("font-family: Lucida Console, Courier New, Courier, monospace;"+'\n');
					out.write("text-align: left;"+'\n');
							out.write("font-size: 8pt;"+'\n');
								out.write("width: 500px;"+'\n');
									out.write("}"+'\n');
												
													
					out.write("body        {"+'\n'); 
					out.write("font-family: verdana, arial, helvetica, sans-serif; font-size: 80%;"+'\n');
					out.write(" }"+'\n');
					out.write("table {"+'\n');
					out.write(" font-size: 100%;"+'\n'); 
					out.write("}"+'\n');
					out.write("pre {"+'\n'); 
					out.write("}"+'\n');
						/* -- heading ---------------------------------------------------------------------- */
       				out.write("	h1 {"+'\n');
					out.write("font-size: 16pt;"+'\n');
					out.write("color: gray;"+'\n');
					out.write("}"+'\n');
					out.write(".heading {"+'\n');
					out.write(" margin-top: 0ex;"+'\n');
					out.write("margin-bottom: 1ex;"+'\n');
					out.write("}"+'\n');
					out.write(".heading .attribute {"+'\n');
					out.write(" margin-top: 1ex;"+'\n');
					out.write("margin-bottom: 0;"+'\n');
					out.write("}"+'\n');

			        out.write(".heading .description {"+'\n');
					out.write(" margin-top: 4ex;"+'\n');
					out.write(" margin-bottom: 6ex;"+'\n');
					out.write(" }"+'\n');

														/* -- css div popup ------------------------------------------------------------------------ */
					out.write("a.popup_link {"+'\n');
					out.write("}"+'\n');

					out.write("a.popup_link:hover {"+'\n');
					out.write("    color: red;"+'\n');
					out.write("}"+'\n');

					out.write(".popup_window {"+'\n');
					out.write(" display: none;"+'\n');
					out.write(" position: relative;"+'\n');
					out.write(" left: 0px;"+'\n');
					out.write(" top: 0px;"+'\n');
					    /*border: solid #627173 1px; */
					out.write("  padding: 10px;"+'\n');
					out.write(" background-color: #E6E6D6;"+'\n');
					out.write(" font-family: Lucida Console, Courier New, Courier, monospace;"+'\n');
					out.write(" text-align: left;"+'\n');
					out.write("  font-size: 8pt;"+'\n');
					out.write(" width: 500px;"+'\n');
					out.write("}"+'\n');					
			out.write("<title>");
			out.write("LVS IBE" + " Detailed Reports");
			out.write("</title>");
			out.write("</style>");
			out.write("</head>");
			out.write("<body bgcolor='#C0BEBE' style='text-align:center;margin:0;font-family: tahoma, courier, serif;'>");
			out.write("<script language=javascript type=text/javascript>"+'\n');
			out.write("function showTestDetail(div_id){"+'\n');
			out.write("var details_div = document.getElementById(div_id)"+'\n');
			out.write("alert(details_div);"+'\n');
			out.write("var displayState = details_div.style.display"+'\n');
			out.write("alert(displayState);"+'\n');
			    // alert(displayState)
			out.write("if (displayState != 'block' ) {"+'\n');
			out.write("alert"+"("+"'INIF'"+")"+";"+'\n');
			out.write("displayState = 'block'"+'\n');
			out.write("details_div.style.display = 'block'"+'\n');
			out.write("}"+'\n');
			out.write("else {"+'\n');
			out.write("details_div.style.display = 'none'"+'\n');
			out.write("}"+'\n');
			out.write("}"+'\n');
			out.write("</script>");
			out.write("<center>");
					out.write("<div style='background-color:#FFFFFF;border:3px solid #867B7B; width:80%;padding-top:15px'>");
			//out.write("<table>");
			out.write("<table width=85%>");
			out.write("<tr>");
			out.write("<td style='padding-top:5px;text-align: left'>");
			out.write("<img src='..//img//logo.JPG' width=150px height=50px/>");
			out.write("</td>");
			out.write("<td align='center' style='padding-top:10px'>");
			out.write("<span style='font-size: 2em;font-family: tahoma, courier, serif'>LVS IBE Automation Test Suite Execution Report</span>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");
			out.write("<br>");
					out.write("<table width=85%><tr><td text-align: left>");
					out.write("<div class='heading'>");
					out.write("<h1>Test Summary</h1>");
					out.write("<p class='attribute'><strong>Date of Execution:</strong>		"+ dateNow +"</p>");
					out.write("<p class='attribute'><strong>Duration:</strong>  "+diffHours+":"+diffMinute+":"+diffSecond+"</p>");
					out.write("<p class='attribute'><strong></strong><b>Total Number of Test Cases:</b><font color=#254117>  "+ totTestCase + "</font>"); 
					out.write("<p class='attribute'><strong></strong><b>	"+"Total Number of Test Cases Passed:		"+"</b>"+testcaseSucessName.size()+"");
					//out.write("<p class='attribute'><strong></strong><font color=#4AA02C>	"+"Total Number of Test Cases Passed:		"+"<b>"+testcaseSucessName.size()+""+"</b></font>");
					out.write("<p class='attribute'><strong></strong><b> "+"Total Number of Test Cases Failed:		"+"</b>"+ testcaseFailName.size()+"");
					out.write("<p class='attribute'><strong>Pass Percentage:</strong><font color=#2F4B21>	" + passPercentage + '%' +"</font></p>");
					out.write("<p class='description'></p>");
					out.write("</div>");
					out.write("</td>");
					out.write("</tr>");
					out.write("</table>");

			/*out.write("<h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Summary of Test Details </u></h4>\n");
			out.write("<table  border=1 cellspacing=1    cellpadding=1>");
			out.write("<tr> ");
			out.write("<td align=center width=19%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Total Test Cases</b></td>");
			out.write("<td align=center width=19% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Tot pass cases</b></td>");
			
			out.write("<td align=center width=19% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Tot failed cases</b></td>");
			out.write("<td align=center width=22% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Date of Execution</b></td>");
			out.write("<td align=center width=22% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Pass Percentage</b></td>");
			out.write("<td align=center width=22% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Tested On</b></td>");
			out.write("</tr>");
			out.write("<tr> ");
			out.write("<td width=150 align=center><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ totTestCase + "</b></td>\n");
			out.write("<td width=150 align=center><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ testcaseSucessName.size() + "</b></td>\n");
			out.write("<td width=150 align=center><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ testcaseFailName.size() + "</b></td>\n");
			out.write("<td width=150 align=center><FONT COLOR=#071019 FACE=Arial SIZE=2.75><b>"
					+ dateNow + "</b></td>\n");
			
			out.write("<td width=150 align=center bgcolor=#00FF00><FONT COLOR=#071019 FACE=Arial SIZE=2.75><b>"
					+ passPercentage + '%' +"</b></td>\n");
			
			out.write("<td width=150 align=center><FONT COLOR=#071019 FACE=Arial SIZE=2.75><b><a href="
					
					+ "http://www.turkishairlines.com/><Font color=#FF0000>http://www.turkishairlines.com/</font></a></b></td>\n");
										
			
			out.write("</tr>");
			out.write("</table>");
*/			
					if (testcaseSucessName != null && testcaseSucessName.size()>0) {
			out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Passed</h4>");
			out.write("<table  border=1 cellspacing=1    cellpadding=1 width=85%>");
			out.write("<tr> ");
			out.write("<td align=center width=20%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Sl.No:</b></td>");
			out.write("<td align=center width=20% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Test Case Name</b></td>");
			//out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>TestCase Summary</b></td>");
			out.write("<td align=center WIDTH=20% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Results</b></td>");
			// out.write("<td align=center width=15% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Screen Shot</b></td>");
			out.write("</tr>");
			

				for (int j = 0; j < testcaseSucessName.size(); j++) {
					count=countValue();
					out.write("<tr> ");
					out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
							+ count + "</b></td>");
					out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
							+ testcaseSucessName.get(j) + "</b></td>");
					//out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
						//	+ testcaseSucessName.get(j) + "</b></td>");

					if (successList.get(j) != null
							|| !successList.get(j).equals("")) {
						
						out.write("<td align= center width=5% bgcolor=#58FA82><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>PASS</b></td>\n");

					
					}
				}
			}
			out.write("</tr>");
			out.write("</table>");

		
			
/*			out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Skiped</h4>");
			out.write("<table  border=1 cellspacing=1    cellpadding=1>");
			out.write("<tr> ");
			out.write("<td align=center width=20%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Step/Row#</b></td>");
			out.write("<td align=center width=20% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>TestCase Name</b></td>");
			//out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>TestCase Summary</b></td>");
			out.write("<td align=center WIDTH=20% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Result(P/F)</b></td>");
			// out.write("<td align=center width=15% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Screen Shot</b></td>");
			out.write("</tr>");
			if (classnameskippedTests != null) {

				for (int j = 0; j < classnameskippedTests.size(); j++) {
					count=countValue();
					out.write("<tr> ");
					out.write("<td align=center bgcolor=#D7D0D0 width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
							+ count + "</b></td>");
					out.write("<td align=center bgcolor=#D7D0D0 width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
							+ classnameskippedTests.get(j) + "</b></td>");
					//out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
						//	+ testcaseSucessName.get(j) + "</b></td>");
					
					if (m_skippedTests.get(j) != null
							|| !m_skippedTests.get(j).equals("")) {
						
						out.write("<td align= center width=5% bgcolor=#D7D0D0><FONT COLOR=#153E7E FACE=Arial SIZE=2><b><a href=\"javascript:showTestDetail("+"'"+count+"'"+")\">Skipped</a>" +

								"</b>" +
								"<div id="+"'"+count+"'"+" class=popup_window>" +
						        "<div style='text-align: right; color:red;cursor:pointer'>" +
						        "<a onfocus='this.blur();' onclick=\"document.getElementById("+"'"+count+"'"+").style.display='none'\">"+
						           "[x]" +
						           "</a>"+
						         "</div>"+
								"<pre>"+stackMsg.get(j)+"</pre>"+
								
								//System.out.println('"'+abc+'"'); 
						    "</div>"+
								"</td>\n");

					
					}
				}
			}
			out.write("</tr>");
			out.write("</table>");

*/		
			
			if (testcaseFailName.size() > 0) {
				
				out.write("<br>");
				out.write("<br>");
				out.write("<br>");
				out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Failed</h4>");
				out.write("<table  border=1 cellspacing=1    cellpadding=1 width=85%>");
				out.write("<tr> ");
				out.write("<td align=center width=19%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Sl.No:</b></td>");
				out.write("<td align=center width=19% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Test Case Name</b></td>");
				//out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>TestCase Summary</b></td>");
				out.write("<td align=center width=19% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Results</b></td>");
				out.write("<td align=center width=22% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Screen Shot</b></td>");
				out.write("</tr>");
				if (testcaseFailName != null) {

					for (int j = 0; j < testcaseFailName.size(); j++) {
						count=countValue();
						out.write("<tr> ");

						out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								+ count + "</b></td>");
						out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								+ testcaseFailName.get(j) + "</b></td>");
						//out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
							//	+ testcaseFailName.get(j) + "</b></td>");

						if (failedList.get(j) != null
								|| !failedList.get(j).equals("")) {
							// for(int i=0;i<successList.size();i++){
							//out.write("<td align= center  bgcolor=#FF0000><FONT COLOR=#153E7E FACE=Arial SIZE=2><b><a href="+""+"javascript:showTestDetail("+"'"+failMsg.get(j)+"'"+")"+""+">FAIL</a>"+
									//out.write("<td align= center  bgcolor=#FA5858><FONT COLOR=#153E7E FACE=Arial SIZE=2><b><a href=\"javascript:showTestDetail("+"'"+count+"'"+")\">FAIL</a>"+
											out.write("<td align= center  bgcolor=#FA5858><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>FAIL"+
											//System.out.println("\"Hello\""); 
									//System.out.println("\""+abc+"\""); 
									//<a href="javascript:showTestDetail('java.lang.AssertionError: expected:<true> but was:<false>')">FAIL
									"</b>" +
									"<div id="+"'"+count+"'"+" class=popup_window>" +
					        "<div style='text-align: right; color:red;cursor:pointer'>" +
					        "<a onfocus='this.blur();' onclick=\"document.getElementById("+"'"+count+"'"+").style.display='none'\">"+
					           "[x]" +
					           "</a>"+
					         "</div>"+
							//"<pre>"+stackMsg.get(j)+"</pre>"+
					         "<pre></pre>"+
							
							//System.out.println('"'+abc+'"'); 
					    "</div>"+
									"</td>\n");

							// }
						}
						if (screenShotPath.get(j) != null) {
							out.write("<td align=center width=10%><FONT color=red FACE=Arial SIZE=2><b><a href=\""+screenShotPath.get(j)+"\">" +
											"<font color=#FF0000>Screen Shot</font></a>" +
									"</b></td>");
						} else {
							out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=1><b>&nbsp;</b></td>");
							out.write("</tr>");
						}
					}
				}
			}
				out.write("</tr>");
				out.write("</table>");
				out.write("<br><br><br<center><a href='#' onClick='window.print()'><span style='font-size:10pt'>Print Report</span><center></a><br>");
				out.write("</div>");
				out.write("</center>");
				out.write("<span style='font-size:8pt;text-align:center'>&copy; IBS Software Pvt Ltd</span>");
				//out.write("</table>");
				
				out.write("</body>");
				out.write("</html>");

			
		//}
			System.out.println("FINISHED HERE");
			count=0;
/*			out.write("<br>");
			out.write("<br>");
			out.write("<table  border=1 cellspacing=1 cellpadding=1 >\n");
			out.write("<tr>\n");

			out.write("<h4> <FONT COLOR=660000 FACE=Arial SIZE=4.5> <u>Summary of Test Details :</u></h4>\n");
			out.write("<td width=150 align=left bgcolor=#00CCCC><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Total Test Cases</b></td>\n");
			out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ totTestCase + "</b></td>\n");
			out.write("</tr>\n");
			out.write("<tr>\n");

			out.write("<td width=150 align=left bgcolor=#00CCCC><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Tot pass cases</b></td>\n");

			out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ testcaseSucessName.size() + "</b></td>\n");
			out.write("</tr>\n");
			out.write("<tr>\n");
			out.write("<td width=150 align=left bgcolor=#00CCCC><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2.75><b>Tot failed cases</b></td>\n");

			out.write("<td width=150 align=left><FONT COLOR=#153E7E FACE=Arial SIZE=2.75><b>"
					+ testcaseFailName.size() + "</b></td>\n");
			out.write("</tr>\n");
			out.write("<tr>\n");
			out.write("</table>\n");
			out.write("<br><br>");
			// pw.write("Date Of Execution:			"+myResults.getDataExecuted()+"");
			out.write("<FONT COLOR=#071019 FACE=Arial SIZE=2.75><b>Date Of Execution:</b>&nbsp&nbsp"
					+ dateNow + "");
			out.write("<br><br>");
			out.write("<FONT COLOR=#071019 FACE=Arial SIZE=2.75><b>Percentage(%) of Test Cases Passed:</b>&nbsp&nbsp"
					+ passPercentage + '%' + "");
			out.write("<br><br>");
			out.write("<FONT COLOR=#071019 FACE=Arial SIZE=2.75><b>Test On:</b>&nbsp&nbsp"
					+ "http://www.turkishairlines.com/");*/

			out.close();
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		}catch (Exception ex) {
			ex.printStackTrace();
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
			//Date date = null;
			// System.out.println(dateFormat.format(date));

			// To identify the system
			InetAddress ownIP = InetAddress.getLocalHost();
			// System.out.println("IP of my system is := "+ownIP.getHostAddress());

			newFileNamePathofScreen = directory.getCanonicalPath()+ "\\screenshots\\" +strScreenName +"_"+ dateFormat.format(date)+".png";
			System.out.println("newFileNamePathofScreen : "+newFileNamePathofScreen);
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


		//Code to get screen resolution
		//Get the default toolkit
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		//Get the current screen size
		Dimension scrnsize = toolkit.getScreenSize();
		//Print the screen size
		System.out.println ("Screen size : " + scrnsize);


		//Get the dir path
		File directory = new File (".");
		//System.out.println(directory.getCanonicalPath()); 

		//get current date time with Date() to create unique file name
		DateFormat dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");
		//get current date time with Date()
		
		//System.out.println(dateFormat.format(date));

		//To identify the system
		InetAddress ownIP=InetAddress.getLocalHost();
		//System.out.println("IP of my system is := "+ownIP.getHostAddress()); 

		newFileNamePath = directory.getCanonicalPath()+ "\\screenshots\\" +strScreen +"_"+ dateFormat.format(date)+".png";
		System.out.println("NewFileNamePath :"+newFileNamePath);

		//Capture the screen shot of the area of the screen defined by the rectangle
		Robot robot = new Robot();
		BufferedImage bi=robot.createScreenCapture(new Rectangle(1280,1024));
		ImageIO.write(bi, "png", new File(newFileNamePath));
		//Count++;//Assign each screen shot a number
		//NewFileNamePath = "<a href="http://draft.blogger.com/+NewFileNamePath+">ScreenShot"+ Count + "</a>";
			//http://draft.blogger.com/+NewFileNamePath+">ScreenShot"+ Count + "</a>";
		//Place the reference in TestNG web report 
		//NewFileNamePath = "ScreenShot"+ Count;
		Reporter.log(newFileNamePath);


		} 
		catch (AWTException e) {
		e.printStackTrace();
		} 
		catch (IOException e) {
			
		e.getMessage();
		}
		}
	public ITestResult returnITestRes(){
		
		return result3;
	}
}
