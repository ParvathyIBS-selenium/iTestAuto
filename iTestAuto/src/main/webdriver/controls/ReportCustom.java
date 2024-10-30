package controls;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlTest;

/**
 * Based on the Test Scripts executed this class generates Custom HTML report
 * 
 */
public class ReportCustom extends TestListenerAdapter implements
		ISuiteListener, ITestListener {

	DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
	Calendar currentDate = Calendar.getInstance();
	Date d1 = null;
	Date d2 = null;
	long diff;
	long diffSeconds;
	long diffMinutes;
	long diffHours;
	int suiteClassCount = 0;
	Date date = new Date();
	String suitedateStart = "";
	String suitedateEnd = "";
	String onTestStartDt = "";
	String onTestSuccessDt3 = "";
	String onTestFailureDt4 = "";
	String onTestSkippedDt5 = "";
	HashMap hasMap4 = null;
	HashMap arr49 = null;
	ArrayList arr3 = new ArrayList();
	ArrayList arr4 = new ArrayList();
	ArrayList arr5 = null;
	boolean flag = false;
	ArrayList arrSucessDuration = new ArrayList();
	ArrayList arrFailureDuration = new ArrayList();
	ArrayList arrSkipDuration = new ArrayList();
	ArrayList<String> failedList = new ArrayList<String>(); // stored the tests
	ArrayList<HashMap> testcaseSucessName = new ArrayList<HashMap>();
	public static ArrayList<String> screenShotPath = new ArrayList<String>();

	ArrayList<String> successList = new ArrayList<String>(); // stored the tests
	ArrayList<String> testcasefailName = new ArrayList<String>();
	ArrayList<String> tescaseIdSuccess = new ArrayList<String>();
	ArrayList<String> tescaseIdFailure = new ArrayList<String>();
	WritableWorkbook workbook = null;
	ArrayList<HashMap> testcaseFailName = new ArrayList<HashMap>();
	ArrayList failMsg = new ArrayList();
	BufferedWriter out = null;
	int totTestCase = 0;
	String dateNow = "";
	int passPercentage = 0;
	String newFileNamePathofScreen = "";
	int count = 0;
	
	String url = "http://www.w3schools.com";
	
	DBDetailsVO dbDetailsVO = null;
	String sqlServerURLDetails = "", databaseName = "", username = "",
			pwd = "";
	public WritableCellFormat times;
	public WritableCellFormat timesBoldUnderline;
	String inputFile = "";
	File file = null;
	String ibeTestPlan = "";
	String ibeBuild = "";
	String quickresTestPlan = "";
	String quickresBuild = "";
	String suitename = "";
	String _blank = "_blank";
	String asa = "toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=400, height=400";
	/**
	 * 
	 * @return int
	 */
	public int countValue() {
		count = count + 1;
		return count;

	}

	/**
	 * 
	 * @return DBDetailsVO
	 */
	public DBDetailsVO dbDetails() {
		DBDetailsVO dbDetailsVO = new DBDetailsVO();
		try {
			Properties p = new Properties();
			String filePath = "";
			String sysProp = System.getProperty("user.dir");
			// System.out.println(sysProp+"\\src\\resources");
			String path = sysProp
					+ "\\src\\resources\\turkish\\DBDetails.properties";
			System.out.println("path in report :" + path);
			p.load(new FileInputStream(path));
			// String file = "selenium.properties";

			String dburl = "";
			String dBUser = "";
			String dBPassword = "";
			String dBDatabaseName = "";
			for (Entry e : p.entrySet()) {
				if (p.containsKey("DATASOURCE1.dBUrl")) {
					dburl = (String) p.get("DATASOURCE1.dBUrl");
					System.out.println("dburl ::::::: " + dburl);
					if (dburl != null && dburl != "") {
						dbDetailsVO.setServerName(dburl);
					}
				}

				if (p.containsKey("DATASOURCE1.dBUser")) {
					dBUser = (String) p.get("DATASOURCE1.dBUser");
					if (dBUser != null && dBUser != "") {
						dbDetailsVO.setUsername(dBUser);
					}
				}
				if (p.containsKey("DATASOURCE1.dBPassword")) {
					dBPassword = (String) p.get("DATASOURCE1.dBPassword");
					if (dBPassword != null && dBPassword != "") {
						dbDetailsVO.setPwd(dBPassword);
					}
				}
				if (p.containsKey("DATASOURCE1.dBDatabaseName")) {
					dBDatabaseName = (String) p
							.get("DATASOURCE1.dBDatabaseName");
					if (dBDatabaseName != null && dBDatabaseName != "") {
						dbDetailsVO.setDatabaseName(dBDatabaseName);
					}
				}
			}
			System.out.println("dburl : " + dburl + '\n' + "dBUser : " + dBUser
					+ '\n' + "dBPassword : " + dBPassword + '\n'
					+ "dBDatabaseName : " + dBDatabaseName);

		} catch (Throwable t) {
			t.printStackTrace();
		}

		return dbDetailsVO;
	}

	/*
	 * This method gets Executed and generates Report when Suite is completed
	 */
	@Override
	public void onFinish(ISuite arg0) {

		System.out.println("on finish Suite Start");
		
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
		suitedateEnd = format.format(currentDate1.getTime());

		try {
			d1 = format.parse(suitedateStart);
			System.out.println("d1 :" + d1);
			d2 = format.parse(suitedateEnd);
			System.out.println("d2 :" + d2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("arr3 SIZE : " + arr3.size());
		System.out.println("testcaseSucessName.size() :"
				+ testcaseSucessName.size());
		System.out.println("testcaseFailName.size() :"
				+ testcaseFailName.size());

		diff = d2.getTime() - d1.getTime();
		System.out.println("diff :" + diff);
		System.out
				.println("TIME >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("DIFF>>>>>>>>>>> IN MILLI" + diff);
		long diff1 = diff / 1000;
		System.out.println("DIFF1 in SECONDS>>>>>>>>>>>>>>>>" + diff1);

		long days = TimeUnit.SECONDS.toDays(diff1);
		diff1 -= TimeUnit.DAYS.toSeconds(days);
		long hours = TimeUnit.SECONDS.toHours(diff1);
		System.out.println("hours---------------------> : " + hours);
		diff1 -= TimeUnit.HOURS.toSeconds(hours);
		long minutes = TimeUnit.SECONDS.toMinutes(diff1);
		System.out.println("minutes---------------------> : " + minutes);
		diff1 -= TimeUnit.MINUTES.toSeconds(minutes);
		long seconds = TimeUnit.SECONDS.toSeconds(diff1);
		System.out.println("seconds---------------------> : " + seconds);

		System.out.println("diff :" + diff);

		String diffSecond = "";
		String diffMinute = "";
		if (seconds >= 0 && seconds <= 9) {
			System.out.println("==========");
			diffSecond = "0" + seconds;

			System.out.println("diffSecond========== " + diffSecond);
		} else {
			System.out.println("----------->");
			diffSecond = "" + seconds;
			System.out.println("diffSecond-------->:" + diffSecond);
		}
		if (minutes >= 0 && minutes <= 9) {
			diffMinute = "0" + minutes;
		} else {
			diffMinute = "" + minutes;
		}
		totTestCase = testcaseSucessName.size() + testcaseFailName.size();
		System.out.println(+diffHours + ":" + diffMinute + ":" + diffSecond);
		for (int j = 0; j < arrSucessDuration.size(); j++) {
			System.out.println("::::::::::::::::::::::::::::::::::::"
					+ arrSucessDuration.get(j));
		}
		for (int k = 0; k < arrFailureDuration.size(); k++) {
			System.out.println(":::::::::::::::::::::::::::::::::"
					+ arrFailureDuration.get(k));
		}
		try {
			write();
		} catch (WriteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		dateNow = formatter.format(currentDate.getTime());
		String OUT_FOLDER = "";
		try {

			System.out.println("testcaseSucessName :" + testcaseSucessName
					+ '\n' + "successList" + successList + '\n'
					+ "failedList :" + failedList + '\n' + "testcaseFailName"
					+ testcaseFailName);

			ArrayList screenShotPath1 = new ArrayList();
			screenShotPath1.add("");

			DateFormat dateFormat = new SimpleDateFormat(
					"dd_MMM_yyyy__HH_mm_ss");
			Date date = new Date();

			OUT_FOLDER = new File(".").getCanonicalPath()
					+ "\\src\\customreport";
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

			} catch (Exception _ex) {
				_ex.printStackTrace();
			}

			/*
			 * 
			 * Connecting to DataBase & inserting the test results in to
			 * DataBase Table
			 */

			try {

				dbDetailsVO = dbDetails();
				if (dbDetailsVO != null) {
					sqlServerURLDetails = dbDetailsVO.getServerName();
					// databaseName=dbDetailsVO.getDatabaseName();
					username = dbDetailsVO.getUsername();
					pwd = dbDetailsVO.getPwd();
					databaseName = dbDetailsVO.getDatabaseName();

				}
				// System.out.println(">>>>>>: "+arrDBDetails.toString());
				/*
				 * for(int i=0;i<arrDBDetails.size();i++){
				 * //System.out.println(">>>>>>: "+(arrDBDetails.get(i)));
				 * 
				 * oracledriverName=(String)arrDBDetails.get(0);
				 * databaseName=(String)arrDBDetails.get(1);
				 * username=(String)arrDBDetails.get(2);
				 * pwd=(String)arrDBDetails.get(3);
				 * 
				 * }
				 */
				String url = sqlServerURLDetails + "databaseName="
						+ databaseName;
				System.out.println("DataBase URL :" + url);
				// Class.forName("oracle.jdbc.driver.OracleDriver");
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				System.out.println("Connected2 :");

				// String url =
				// "jdbc:oracle:thin:@"+oracledriverName+""+":1521:"+databaseName+"";

				// Connection conn =
				// DriverManager.getConnection("jdbc:oracle:thin:@192.168.84.110:1521:CBA03","MCS_OWNER","MCS_OWNER");
				System.out.println("DataBase Connected" + url);

				Connection conn = DriverManager.getConnection(url, username,
						pwd);
				System.out.println("Connected to Specific database");
				PreparedStatement pstmt = conn.prepareStatement("sql");
				ResultSet rset = pstmt
						.executeQuery("select loc_sys_id from locations  where loc_cd = 'BOM'");
				while (rset.next()) {
					System.out.println("Result from DataBase : "
							+ rset.getString(1));
				}
				pstmt.close();
				// System.out.println("Ok.");
			} catch (SQLException sql) {
				sql.printStackTrace();
			} catch (Exception _ex) {
				_ex.printStackTrace();
			}

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
			/* border: solid #627173 1px; */
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
			 * --------------------------------------------------------
			 * --------------
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
			 * --------------------------------------------------
			 * ----------------------
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
			out.write("LVS IBE" + " Detailed Reports");
			out.write("</title>");
			out.write("</style>");
			out.write("</head>");
			out.write("<body bgcolor='#C0BEBE' style='text-align:center;margin:0;font-family: tahoma, courier, serif;'>");
			out.write("<script language=javascript type=text/javascript>" + '\n');
			out.write("function showTestDetail(div_id){" + '\n');
			out.write("var details_div = document.getElementById(div_id)" + '\n');
			
			out.write("var displayState = details_div.style.display" + '\n');
			

			out.write("if (displayState != 'block' ) {" + '\n');
			
			out.write("displayState = 'block'" + '\n');
			out.write("details_div.style.display = 'block'" + '\n');
			out.write("}" + '\n');
			out.write("else {" + '\n');
			out.write("details_div.style.display = 'none'" + '\n');
			out.write("}" + '\n');
			out.write("}" + '\n');
			
			out.write("function openScreenShot(name){" + '\n');
			//out.write("var name = ""\"Hello\"" + '\n');
			//out.write("var name = 'none'" + '\n');
			out.write("alert" + "(" + "'INIF'" + ")" + ";" + '\n');
			out.write("var _blank = '_blank';" + '\n');
			out.write("var asa = 'toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=400, height=400';" + '\n');
			out.write("alert(name);"+'\n');
			out.write("alert(_blank);"+'\n');
			out.write("alert(asa);"+'\n');
			//out.write("var _blank = \""_blank"" + '\n');
			out.write("window.open(name,_blank,asa);" + '\n');
	//		window.open(name,"_blank","toolbar=yes, location=yes, directories=no, status=no, menubar=yes, scrollbars=yes, resizable=no, copyhistory=yes, width=400, 
		//			height=400");
			
			out.write("}" + '\n');
			
			out.write("</script>");
			out.write("<center>");
			out.write("<div style='background-color:#FFFFFF;border:3px solid #867B7B; width:80%;padding-top:15px'>");

			out.write("<table width=85%>");
			out.write("<tr>");
			
			
			
			
			 out.write("<td style='padding-top:5px;text-align: left'>");
			 out.write
			 ("<img src='..//img//logo.JPG' width=90px height=90px/>");
			 out.write("</td>");
			 
			
			
			
			out.write("<td align='center' style='padding-top:10px'>");
			out.write("<span style='font-size: 2em;font-family: tahoma, courier, serif'>LVS IBE Test Suite Execution Report</span>");
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
					+ diffHours + ":" + diffMinute + ":" + diffSecond + "</p>");
			out.write("<p class='attribute'><strong></strong><b>Total Number of Test Cases:</b><font color=#254117>  "
					+ totTestCase + "</font>");
			out.write("<p class='attribute'><strong></strong><b>	"
					+ "Total Number of Test Cases Passed:		" + "</b>"
					+ testcaseSucessName.size() + "");
			// out.write("<p class='attribute'><strong></strong><font color=#4AA02C>	"+"Total Number of Test Cases Passed:		"+"<b>"+testcaseSucessName.size()+""+"</b></font>");
			out.write("<p class='attribute'><strong></strong><b> "
					+ "Total Number of Test Cases Failed:		" + "</b>"
					+ testcaseFailName.size() + "");
			out.write("<p class='attribute'><strong>Pass Percentage:</strong><font color=#2F4B21>	"
					+ passPercentage + '%' + "</font></p>");
			out.write("<p class='description'></p>");
			out.write("</div>");
			out.write("</td>");
			out.write("</tr>");
			out.write("</table>");

			if (testcaseSucessName != null && testcaseSucessName.size() > 0) {
				out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Passed</h4>");
				out.write("<table  border=1 cellspacing=1    cellpadding=1 width=85%>");
				out.write("<tr> ");
				out.write("<td align=center width=2%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Sl.No</b></td>");
				out.write("<td align=center width=20% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Test Case Name</b></td>");
				out.write("<td align=center WIDTH=8% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Results</b></td>");
				out.write("<td align=center WIDTH=7% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>TestCase Duration</b></td>");
				out.write("<td align=center WIDTH=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Operating System</b></td>");
				out.write("</tr>");

				System.out.println(" IN testcaseSucessName :"
						+ testcaseSucessName.size());
				for (int j = 0; j < testcaseSucessName.size(); j++) {
					System.out.println(" IN testcaseSucessName :"
							+ testcaseSucessName.get(j));
					HashMap arr6 = (HashMap) testcaseSucessName.get(j);

					Set set = arr6.entrySet();
					// Get an iterator
					Iterator jj = set.iterator();
					// Display elements
					while (jj.hasNext()) {
						Map.Entry me = (Map.Entry) jj.next();
						// System.out.print(me.getKey() + ": ");
						String Key = (String) me.getKey();
						// System.out.println(me.getValue());
						String arr51 = (String) me.getValue();
						System.out.println("ARR 5 :" + arr51);

						count = countValue();
						out.write("<tr> ");
						out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								+ count + "</b></td>");
						out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								+ arr51 + "</b></td>");

						if (arr51 != null || !arr51.equals("")) {

							out.write("<td align= center width=5% bgcolor=#58FA82><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>PASS</b></td>\n");

							out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
									+ arrSucessDuration.get(j) + "</b></td>");
							out.write("<td align=center width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
									+ Key + "</b></td>");
						}
					}
				}
			}
			out.write("</tr>");
			out.write("</table>");

			if (testcaseFailName != null && testcaseFailName.size() > 0) {

				out.write("<br>");
				out.write("<br>");
				out.write("<br>");
				out.write("<h4> <FONT COLOR=660000 FACE=Arial> Test Cases Failed</h4>");
				out.write("<table  border=1 cellspacing=1    cellpadding=1 width=85%>");
				out.write("<tr> ");
				out.write("<td align=center width=3%  align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Sl.No</b></td>");
				out.write("<td align=center width=17% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Test Case Name</b></td>");
				out.write("<td align=center width=10% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Results</b></td>");
				out.write("<td align=center width=8% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>TestCase Duration</b></td>");
				out.write("<td align=center WIDTH=8% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Operating System</b></td>");
				out.write("<td align=center width=22% align=center bgcolor=#153E7E><FONT COLOR=#E0E0E0 FACE=Arial SIZE=2><b>Screen Shot</b></td>");
				out.write("</tr>");

				for (int j = 0; j < testcaseFailName.size(); j++) {

					System.out.println(" IN testcaseFailName :"
							+ testcaseFailName.get(j));
					HashMap arr6 = (HashMap) testcaseFailName.get(j);

					Set set = arr6.entrySet();
					// Get an iterator
					Iterator jk = set.iterator();
					// Display elements
					while (jk.hasNext()) {
						Map.Entry me = (Map.Entry) jk.next();
						// System.out.print(me.getKey() + ": ");
						String failureKey = (String) me.getKey();
						// System.out.println(me.getValue());
						String strFailValue = (String) me.getValue();
						System.out.println("strValue :" + strFailValue);

						count = countValue();
						out.write("<tr> ");

						out.write("<td align=center width=10%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								+ count + "</b></td>");
						out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								+ strFailValue + "</b></td>");

						if (strFailValue != null || !strFailValue.equals("")) {
							out.write("<td align= center  bgcolor=#FA5858><FONT COLOR=#153E7E FACE=Arial SIZE=2><b><a href=\"javascript:showTestDetail("
									+ "'"
									+ count
									+ "'"
									+ ")\">FAIL</a>"
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
									//+ "<pre>"
									+ failMsg.get(j)
									//+ "</pre>"
									+ "<pre></pre>"
									+

									"</div>" + "</td>\n");
							out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
									+ arrFailureDuration.get(j) + "</b></td>");

						}

						out.write("<td align=left width=5%><FONT COLOR=#153E7E FACE=Arial SIZE=2><b>"
								+ failureKey + "</b></td>");
						
						if (screenShotPath.get(j) != null) {
							out.write("<td align=center width=10%><FONT color=red FACE=Arial SIZE=2><b><a href=\"javascript:openScreenShot("
									+ "'"
									+ url
									+ "'"
									+ ")\">" +
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
			out.write("<br><br><center><a href='#' onClick='window.print()'><span style='font-size:10pt'>Print Report</span><center></a><br>");
			out.write("</div>");
			out.write("</center>");
			out.write("<span style='font-size:8pt;text-align:center'>&copy; IBS Software Pvt Ltd</span>");
			// out.write("</table>");

			out.write("</body>");
			out.write("</html>");
			System.out.println("FINISHED HERE");
			count = 0;
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onStart(ISuite suite) {
		System.out.println("on Start Suite");
		ISuite isuite = null;
		String suit = suite.getHost();
		suitename = suite.getName();
		System.out.println("suite name" + suit);
		System.out.println("suite name" + suitename);
		int i = 0;
		java.util.Map<java.lang.String, java.lang.String> mp = suite
				.getXmlSuite().getParameters();
		String browser = suite.getXmlSuite().getParameter("browser");
		System.out.println("brow>>>" + browser);
		Iterator<Entry<String, String>> it = mp.entrySet().iterator();
		while (it.hasNext()) {
			System.out.println("Suite Parameters :::: " + it.next());
			System.out.println("---------------------- :" + i++);
		}

		suitedateStart = format.format(currentDate.getTime());
		System.out.println(" suitedateStart  : " + suitedateStart);
		System.out.println("on end Suite");
	}

	@Override
	public void onTestStart(ITestResult result) {

		System.out.println("test start");
		System.out.println("Class Name : " + result.getTestClass().getName());
		Calendar currentDate = Calendar.getInstance();
		Object[] ab = result.getParameters();
		System.out.println("Param Set : " + ab.length);
		for (int i = 0; i < ab.length; i++) {
			System.out.print(ab[i] + " ");
		}

		System.out.println("Method Name : "
				+ result.getMethod().getMethodName());
		onTestStartDt = format.format(currentDate.getTime());
		System.out.println("onTestStartDt :" + onTestStartDt);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		System.out.println("test end");
	}

	/*
	 * This method gets executed when Test Case gets executed Successfully
	 */
	@Override
	public void onTestSuccess(ITestResult result) {

		System.out.println("test onTestSuccess Start");
		Calendar currentDate1 = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		String onTestSuccessDt = format.format(currentDate1.getTime());
		Date d3 = null;
		Date d4 = null;
		HashMap hmap = new HashMap();
		String Key = "";
		try {
			d3 = format.parse(onTestStartDt);
			System.out.println("d3 :" + d3);
			// Thread.sleep(5000);
			d4 = format.parse(onTestSuccessDt);
			System.out.println("d4 :" + d4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		diff = d4.getTime() - d3.getTime();
		System.out.println("diff :" + diff);
		System.out
				.println("TIME >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("DIFF>>>>>>>>>>> IN MILLI" + diff);
		long diff1 = diff / 1000;
		System.out.println("DIFF1 in SECONDS>>>>>>>>>>>>>>>>" + diff1);
		// 3600000000

		long days = TimeUnit.SECONDS.toDays(diff1);
		diff1 -= TimeUnit.DAYS.toSeconds(days);
		long hours = TimeUnit.SECONDS.toHours(diff1);
		System.out.println("hours---------------------> : " + hours);
		diff1 -= TimeUnit.HOURS.toSeconds(hours);
		long minutes = TimeUnit.SECONDS.toMinutes(diff1);
		System.out.println("minutes---------------------> : " + minutes);
		diff1 -= TimeUnit.MINUTES.toSeconds(minutes);
		long seconds = TimeUnit.SECONDS.toSeconds(diff1);
		System.out.println("seconds---------------------> : " + seconds);

		String diffSecond = "";
		String diffMinute = "";
		if (seconds >= 0 && seconds <= 9) {
			System.out.println("==========");
			diffSecond = "0" + seconds;

			System.out.println("diffSecond========== " + diffSecond);
		} else {
			System.out.println("----------->");
			diffSecond = "" + seconds;
			System.out.println("diffSecond-------->:" + diffSecond);
		}
		if (minutes >= 0 && minutes <= 9) {
			diffMinute = "0" + minutes;
		} else {
			diffMinute = "" + minutes;
		}
		String strSuccessDur = +diffHours + ":" + diffMinute + ":" + diffSecond;
		System.out.println("strSuccessDur :" + strSuccessDur);
		arrSucessDuration.add(strSuccessDur);
		System.out.println("arr3arr3arr3arr3arr3arr3arr3arr3 :" + arr3.size());

		for (int i = 0; i < arr3.size(); i++) {
			System.out.println("------> :" + arr3.get(i));
			hasMap4 = (HashMap) arr3.get(i);
		}

		Set set = hasMap4.entrySet();
		// Get an iterator
		Iterator j = set.iterator();

		while (j.hasNext()) {
			Map.Entry me = (Map.Entry) j.next();
			Key = (String) me.getKey();
			arr5 = (ArrayList) me.getValue();

			for (int e = 0; e <= arr5.size() - 1; e++) {

				if (((String) arr5.get(e)).equals((result.getTestClass()
						.getName().substring((result.getTestClass().getName()
						.lastIndexOf(".")) + 1)))) {

					hmap.put(Key, (result.getTestClass().getName()
							.substring((result.getTestClass().getName()
									.lastIndexOf(".")) + 1)));
					testcaseSucessName.add(hmap);

				}
			}

		}
		System.out
				.println(" testcaseSucessName :::::::::::::::::::::::::::::::::::::: "
						+ testcaseSucessName);
		successList.add(result.getMethod().getMethodName());
		Field field;
		try {
			field = Class.forName(result.getTestClass().getName()).getDeclaredField("testcaseID");
			field.setAccessible(true);
			String fieldValue = ""+field.get(Class.forName(result.getTestClass().getName()));
			tescaseIdSuccess.add(fieldValue);
			System.out.println("fieldValue---:"+fieldValue);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Allow modification on the field
		
		
		System.out.println("test onTestSuccess End");

	}

	/*
	 * This method gets executed when Test Case gets Failed
	 */
	@Override
	public void onTestFailure(ITestResult result) {
		System.out.println("test onTestFailure START");
		Calendar currentDate1 = Calendar.getInstance();
		// DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		DateFormat df = new SimpleDateFormat(
				"HH 'hours', mm 'mins,' ss 'seconds'");

		String onTestFailureDt = format.format(currentDate1.getTime());

		Date d5 = null;
		Date d6 = null;
		HashMap failHmap = new HashMap();
		String failKey = "";
		try {
			d5 = format.parse(onTestStartDt);
			System.out.println("d5 :" + d5);
			d6 = format.parse(onTestFailureDt);
			System.out.println("d6 :" + d6);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("d6>>>>>>>>>>>>>>>>>>>>>>" + d6.getTime());
		diff = d6.getTime() - d5.getTime();

		long diff1 = diff / 1000;

		long days = TimeUnit.SECONDS.toDays(diff1);
		diff1 -= TimeUnit.DAYS.toSeconds(days);
		long hours = TimeUnit.SECONDS.toHours(diff1);
		System.out.println("hours---------------------> : " + hours);
		diff1 -= TimeUnit.HOURS.toSeconds(hours);
		long minutes = TimeUnit.SECONDS.toMinutes(diff1);
		System.out.println("minutes---------------------> : " + minutes);
		diff1 -= TimeUnit.MINUTES.toSeconds(minutes);
		long seconds = TimeUnit.SECONDS.toSeconds(diff1);
		System.out.println("seconds---------------------> : " + seconds);
		System.out.println("diff :" + diff);

		String diffSecond = "";
		String diffMinute = "";
		if (seconds >= 0 && seconds <= 9) {

			diffSecond = "0" + seconds;

		} else {

			diffSecond = "" + seconds;

		}
		if (minutes >= 0 && minutes <= 9) {
			diffMinute = "0" + minutes;
		} else {
			diffMinute = "" + minutes;
		}

		String strFailureDur = +diffHours + ":" + diffMinute + ":" + diffSecond;
		System.out.println("strFailureDur :" + strFailureDur);
		String st = result.getTestClass().getName();

		
		
		
		
		
		
		
		
		
		
		String screenShotname = listFiles(result.getTestClass().getName()
				.substring((result.getTestClass().getName()
						.lastIndexOf(".")) + 1));
		
		screenShotPath.add(screenShotname);
		List<ITestResult> itr = getConfigurationFailures();
		arrFailureDuration.add(strFailureDur);

		for (int i = 0; i < arr3.size(); i++) {
			System.out.println("------> :" + arr3.get(i));
			arr49 = (HashMap) arr3.get(i);

		}
		Set set = arr49.entrySet();
		// Get an iterator
		Iterator j = set.iterator();
		// Display elements
		while (j.hasNext()) {
			Map.Entry me = (Map.Entry) j.next();
			// System.out.print(me.getKey() + ": ");
			failKey = (String) me.getKey();
			// System.out.println(me.getValue());
			arr5 = (ArrayList) me.getValue();

			for (int t = 0; t <= arr5.size() - 1; t++) {
				// System.out.println("------"+(String)arr5.get(e)+" ------ :"+(String)testcaseSucessName.get(p));

				if (((String) arr5.get(t)).equals((result.getTestClass()
						.getName().substring((result.getTestClass().getName()
						.lastIndexOf(".")) + 1)))) {
					System.out.println("Failure ----------------------> : "
							+ (String) arr5.get(t));

					System.out.println("failKey BRowser Value :" + failKey);
					failHmap.put(failKey, (result.getTestClass().getName()
							.substring((result.getTestClass().getName()
									.lastIndexOf(".")) + 1)));
					testcaseFailName.add(failHmap);
				}
			}

		}

		failedList.add(result.getMethod().getMethodName());
		// testcaseFailName.add((result.getTestClass().getName().substring((result.getTestClass().getName().lastIndexOf("."))+1)));

		String strSub = result.getThrowable().toString();

		if (strSub.contains("<")) {
			strSub = strSub.replace("<", "&#60");
			if (strSub.contains(">")) {
				strSub = strSub.replace(">", "&#62");
			}
		}
		failMsg.add(strSub);
		Field field;
		try {
			field = Class.forName(result.getTestClass().getName()).getDeclaredField("testcaseID");
			field.setAccessible(true);
			String fieldValue = ""+field.get(Class.forName(result.getTestClass().getName()));
			tescaseIdFailure.add(fieldValue);
			System.out.println("fieldValue---:"+fieldValue);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 * This method gets executed when Test Case gets Skipped
	 */
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("test onTestSkipped");
		Calendar currentDate1 = Calendar.getInstance();
		DateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		String onTestFailureDt = format.format(currentDate1.getTime());
		Date d7 = null;
		Date d8 = null;
		try {
			d7 = format.parse(onTestStartDt);
			System.out.println("d7 :" + d7);
			// Thread.sleep(5000);
			d8 = format.parse(onTestFailureDt);
			System.out.println("d8 :" + d8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("d8>>>>>>>>>>>>>>>" + d8);
		System.out.println("d7>>>>>>>>>>>>>>>" + d7);
		diff = d8.getTime() - d7.getTime();

		long diff1 = diff / 1000;

		long days = TimeUnit.SECONDS.toDays(diff1);
		diff1 -= TimeUnit.DAYS.toSeconds(days);
		long hours = TimeUnit.SECONDS.toHours(diff1);
		System.out.println("hours---------------------> : " + hours);
		diff1 -= TimeUnit.HOURS.toSeconds(hours);
		long minutes = TimeUnit.SECONDS.toMinutes(diff1);
		System.out.println("minutes---------------------> : " + minutes);
		diff1 -= TimeUnit.MINUTES.toSeconds(minutes);
		long seconds = TimeUnit.SECONDS.toSeconds(diff1);
		System.out.println("seconds---------------------> : " + seconds);

		System.out.println("diff :" + diff);

		String diffSecond = "";
		String diffMinute = "";
		if (seconds >= 0 && seconds <= 9) {
			System.out.println("==========");
			diffSecond = "0" + seconds;

			System.out.println("diffSecond========== " + diffSecond);
		} else {
			System.out.println("----------->");
			diffSecond = "" + seconds;
			System.out.println("diffSecond-------->:" + diffSecond);
		}
		if (minutes >= 0 && minutes <= 9) {
			diffMinute = "0" + minutes;
		} else {
			diffMinute = "" + minutes;
		}

		String strSkipedDur = +diffHours + ":" + diffMinute + ":" + diffSecond;
		arrSkipDuration.add(strSkipedDur);
		System.out.println("strSkipedDur :" + strSkipedDur);

		System.out.println("Skipped test: " + result.getName() + ".Reason"
				+ result.getThrowable());

	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	/*
	 * This method gets executed when suite has Test Tag
	 */
	@Override
	public void onStart(ITestContext context) {
		try {
			System.out.println("on Start tests");
			System.out.println("test name " + context.getName());
			String browserValue = "";
			arr3 = new ArrayList();
			HashMap hm1 = new HashMap();
			ArrayList arr1 = new ArrayList();
			String browsValue = "";

			ISuite suite = context.getSuite();
			Map<String, String> xmlTest = context.getCurrentXmlTest()
					.getTestParameters();
			Iterator<Entry<String, String>> it = xmlTest.entrySet().iterator();

			Set set = xmlTest.entrySet();
			// Get an iterator
			Iterator f = set.iterator();
			// Display elements
			while (f.hasNext()) {
				Map.Entry me = (Map.Entry) f.next();
				// System.out.print(me.getKey() + ": ");
				String Key = (String) me.getKey();
				browserValue = (String) me.getValue();
				System.out.println("Key :" + Key);
				System.out.println("browserValue :" + browserValue);
				if (Key.equals("browser")) {
					System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&& :"
							+ browserValue);
					browsValue = browserValue + '\\'
							+ System.getProperty("os.name");
				}

			}
			XmlTest xmlvalue = context.getCurrentXmlTest();

			List ls = xmlvalue.getXmlClasses();
			System.out.println("Size -----> :" + ls.size());
			System.out.println("ls -----> :" + ls);
			suiteClassCount = ls.size();
			for (int y = 0; y < ls.size(); y++) {
				System.out.println("-----> :" + ls.get(y));
			}
			for (int j = 0; j < ls.size(); j++) {

				System.out.println("-----> :" + ls.get(j).toString());
				String classNm = (String) ls.get(j).toString();
				String classNm2 = classNm
						.substring(classNm.lastIndexOf((".")) + 1);
				String classNM3 = classNm2.substring(0,
						classNm2.lastIndexOf(("]")));
				System.out.println("classNM3  : " + classNM3);
				arr1.add(classNM3);

			}

			hm1.put(browsValue, arr1);
			arr3.add(hm1);
			arr4.add(hm1);

			ITestNGMethod[] value = context.getAllTestMethods();
			for (int i = 0; i < value.length; i++) {
				System.out.println("Method Name :" + value[i].getMethodName());
			}
			System.out.println("Method Number : " + value.length);

			//count = value.length;
			System.out.println("on Start tests END");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		System.out.println("on finish ");

	}

	public String listFiles(String fileName) {
		String fileNamePath = "";

		try {
			String s2 = System.getProperty("user.dir");
			// System.out.println(s2+"\\src\\resources");
			String path = s2 + "\\screenshots";
			System.out.println("path :" + path);
			// Directory path here
			// String path =
			// "D:\\projects\\thyEnvironment\\turkish\\screenshots";

			String files;
			File folder = new File(path);
			File[] listOfFiles = folder.listFiles();

			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {
					files = listOfFiles[i].getName();
					if (files.endsWith(".png")) {

						files = files.substring(0, files.lastIndexOf("."));
						System.out.println(files);
						if (files.equals(fileName)) {
							// String filePath =
							// getFilePath("DomesticRoutewith_One_Adult");
							System.out
									.println("folder.getAbsolutePath() ::::::::"
											+ folder.getAbsolutePath()
											+ "\\"
											+ fileName + "." + "png");
							fileNamePath = folder.getAbsolutePath() + "\\"
									+ fileName + "." + "png";
						}

					} else {
						System.out.println("No Files with .png");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileNamePath;

	}
	 public void write() throws IOException, WriteException {
		 Properties p = new Properties();
		 String buildProp = System.getProperty("user.dir");
			// System.out.println(s2+"\\src\\resources");
			String buildPropPath = buildProp + "\\src\\resources\\turkish\\build.properties";
			System.out.println("buildPropPath :" + buildPropPath);
			p.load(new FileInputStream(buildPropPath));
			//String file = "selenium.properties";

			
			for (Entry e : p.entrySet()) {
				if (p.containsKey("ibeTestPlan")) {
					ibeTestPlan = (String) p.get("ibeTestPlan");
					
				}
				if (p.containsKey("ibeBuild")) {
					ibeBuild = (String) p.get("ibeBuild");
					
				}
				if (p.containsKey("quickresTestPlan")) {
					quickresTestPlan = (String) p.get("quickresTestPlan");
					
				}
				if (p.containsKey("quickresBuild")) {
					quickresBuild = (String) p.get("quickresBuild");
					
				}
			}
			System.out.println("quickresTestPlan --- >:"+quickresTestPlan+" quickresBuild --- >:"+quickresBuild+" ibeTestPlan --- >:"+ibeTestPlan+" ibeBuild -- >:"+ibeBuild);
			inputFile = "C:\\temp\\TC_Result.xls";
			/*if(suitename.equals("IBESuite")){
			inputFile = "C:\\temp\\IBE_TC_Result.xls";
			}else{
				inputFile = "C:\\temp\\QCK_TC_Result.xls";
			}*/
		  System.out.println(" inputFile  -------*********************************:"+inputFile);
		    file = new File(inputFile);
		    WorkbookSettings wbSettings = new WorkbookSettings();

		    wbSettings.setLocale(new Locale("en", "EN"));

		    workbook = Workbook.createWorkbook(file, wbSettings);
		    workbook.createSheet("Sheet1", 0);
		    WritableSheet excelSheet = workbook.getSheet(0);
		    createLabel5(excelSheet);
//		    createContent(excelSheet);

		    workbook.write();
		    workbook.close();
		  }

		  public void createLabel5(WritableSheet sheet)
		      throws WriteException {
			  ArrayList qckSucssBuild = new ArrayList();
			  ArrayList ibeSucssBuild = new ArrayList();
			  ArrayList qckFailBuild = new ArrayList();
			  ArrayList ibeFailBuild = new ArrayList();
			  
			  String bldName = "";
			  String tstName = "";
			 // public WritableCellFormat times;
		    // Lets create a times font
		    WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		    // Define the cell format
		    times = new WritableCellFormat(times10pt);
		    // Lets automatically wrap the cells
		    times.setWrap(true);

		    // Create create a bold font with unterlines
		    WritableFont times10ptBoldUnderline = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false,
		        UnderlineStyle.SINGLE);
		    timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		    // Lets automatically wrap the cells
		    timesBoldUnderline.setWrap(true);

		    CellView cv = new CellView();
		    cv.setFormat(times);
		    cv.setFormat(timesBoldUnderline);
		    cv.setAutosize(true);

		    // Write a few headers
		    addCaption5(sheet, 0, 0, "TESTCASEID");
		    addCaption5(sheet, 1, 0, "STATUS");
		    addCaption5(sheet, 2, 0, "BUILDNAME");
		    addCaption5(sheet, 3, 0, "TESTPLAN");
		    addCaption5(sheet, 4, 0, "PLATFORM");
		  //  addCaption(sheet, 1, 0, "This is another header");
		    /*for(int k=0;k<testcaseSucessName.size();k++){
		    	if(testcaseSucessName.get(k).contains("TC_QCK")){
		    		System.out.println("");
		    		qckSucssBuild.add(testcaseSucessName.get(k));
		    }else{
		    	ibeSucssBuild.add(testcaseSucessName.get(k));
		    }
		    }
		    for(int k=0;k<testcaseFailName.size();k++){
		    	if("TC_QCK".contains(""+testcaseFailName.get(k))){
		    		System.out.println("");
		    		qckFailBuild.add(testcaseFailName.get(k));
		    }else{
		    	ibeFailBuild.add(testcaseFailName.get(k));
		    }
		    }*/
		if (testcaseSucessName.size() > 0) {
			try{
			for (int i = 0; i < testcaseSucessName.size(); i++) {
				System.out.println(" IN arrtestcaseSucessName :"
						+ testcaseSucessName.get(i));

				
				HashMap arr6 = (HashMap) testcaseSucessName.get(i);
				// System.out.println("............ : "+arr4);

				Set set = arr6.entrySet();
				// Get an iterator
				Iterator jj = set.iterator();
				// Display elements
				while (jj.hasNext()) {
					Map.Entry me = (Map.Entry) jj.next();
					// System.out.print(me.getKey() + ": ");
					String Key = (String) me.getKey();
					// System.out.println(me.getValue());
					String arr51 = (String) me.getValue();
					if(arr51.contains("TC_QCK")){
			    		System.out.println("");
			    		bldName = quickresBuild;
						tstName = quickresTestPlan;
			    		//qckSucssBuild.add(arr51);
			    }else{
			    	bldName = ibeBuild;
					tstName = ibeTestPlan;
			    	//ibeSucssBuild.add(arr51);
			    }
					/*if(arr51.equals(qckSucssBuild.get(i))){
						bldName = quickresBuild;
						tstName = quickresTestPlan;
					}else if(arr51.equals(ibeSucssBuild.get(i))){
						bldName = ibeBuild;
						tstName = ibeTestPlan;
					}*/
					System.out.println("createContent Value :" + arr51);
					System.out.println("createContent Key :" + Key);
					System.out.println("arrSucessDuration.get(i)  :" + ""
							+ arrSucessDuration.get(i));
					for (int j = 0; j <= 3; j++) {
						System.out.println("j>>" + j);
						System.out.println("i>>" + i);
						if (j == 0) {
							addCaption5(sheet, j, i+1, tescaseIdSuccess.get(i));
						} else if (j == 1) {
							addCaption5(sheet, j, i+1, "Passed");
						} else if (j == 2) {
							addCaption5(sheet, j, i+1, bldName);
						}else if (j == 3){
							addCaption5(sheet, j, i+1,tstName);
						}
					}

				}

			}
		}catch(Exception e){
			e.printStackTrace();
		}
		}
		
		if (testcaseFailName.size() > 0) {
			int numOfRows = numberOfRowsinSheet("Sheet1");
			System.out.println("numOfRows :"+numOfRows);
			try{
			for (int i = 0; i < testcaseFailName.size(); i++) {
				
				System.out.println(" IN testcaseFailName :"+testcaseFailName.get(i));
				
				
				HashMap arr6 = (HashMap)testcaseFailName.get(i);
				//System.out.println("............ : "+arr4);
			
				Set set = arr6.entrySet();
				// Get an iterator
				Iterator jk = set.iterator();
				// Display elements
				while(jk.hasNext()) {
				Map.Entry me = (Map.Entry)jk.next();
				//System.out.print(me.getKey() + ": ");
				String failureKey = (String)me.getKey();
				//System.out.println(me.getValue());
				String strFailValue = (String) me.getValue();
				if(strFailValue.contains("TC_QCK")){
		    		System.out.println("");
		    		bldName = quickresBuild;
					tstName = quickresTestPlan;
		    		//qckFailBuild.add(strFailValue);
		    }else{
		    	bldName = ibeBuild;
				tstName = ibeTestPlan;
		    	//ibeFailBuild.add(strFailValue);
		    }
				/*if(strFailValue.equals(qckFailBuild.get(i))){
					bldName = quickresBuild;
					tstName = quickresTestPlan;
				}else if(strFailValue.equals(ibeFailBuild.get(i))){
					bldName = ibeBuild;
					tstName = ibeTestPlan;
				}*/
				System.out.println("strValue :"+strFailValue);
					for (int j = 0; j <= 3; j++) {
						System.out.println("j>>" + j);
						System.out.println("i>>" + i);
						if (j == 0) {
							addCaption5(sheet, j, i+numOfRows, tescaseIdFailure.get(i));
						} else if (j == 1) {
							addCaption5(sheet, j, i+numOfRows, "Failed");
						} else if (j == 2) {
							addCaption5(sheet, j, i+numOfRows,bldName);
						}else if (j == 3){
							addCaption5(sheet, j, i+numOfRows,tstName);
						}
					}

				}

			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
		  private void addCaption5(WritableSheet sheet, int column, int row, String s)
	      throws RowsExceededException, WriteException {
	    Label label;
	    label = new Label(column, row, s, timesBoldUnderline);
	    sheet.addCell(label);
	  }

	  private void addNumber5(WritableSheet sheet, int column, int row,
	      Integer integer) throws WriteException, RowsExceededException {
	    Number number;
	    String string;
	    number = new Number(column, row, integer, times);
	    sheet.addCell(number);
	  }
	  public int numberOfRowsinSheet(String sheetName) {
			Sheet sheet = workbook.getSheet(sheetName);
			return sheet.getRows();
		}
}
