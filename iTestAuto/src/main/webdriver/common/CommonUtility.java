package common;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import org.apache.commons.lang3.StringUtils;
import controls.exceptions.UnknownConditionException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsDriver;

import org.openqa.selenium.interactions.internal.Coordinates;
import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import org.openqa.selenium.Cookie;



public class CommonUtility extends DriverSetup {

	private Calendar calendar;
	private Format formatter;
	private String sFormatedDate;
	private Date date;
	private Map<String, List<String>> airCityCodes;


	public CommonUtility() {
		
	}

	/**
	 * This method return the email address for domain input.
	 * 
	 * @param domain
	 *            giving into the email address
	 * @return emailAddress with valid format for the domain input
	 * @author A-5874
	 */
	public String generateRandomEmailAddress(String domain) {
		String emailAddress = "";
		// Generate random email address
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		while (emailAddress.length() < 5) {
			int character = (int) (Math.random() * 26);
			emailAddress += alphabet.substring(character, character + 1);
		}
		emailAddress += Integer.valueOf((int) (Math.random() * 99)).toString();
		emailAddress += "@" + domain;
		return emailAddress;
	}

	/**
	 * Matches two strings by removing the locale
	 * 
	 * @param two
	 *            strings
	 * @return boolean
	 * @author A-5874
	 */
	public boolean plainStringMatches(String arg1, String arg2) {

		Locale locale = new Locale("en");

		String formatterVal1 = String.format(locale, "");
		String formatterVal2 = String.format(locale, "");

		return formatterVal1.matches(formatterVal2);
	}

	/**
	 * Formats to string and returns the same
	 * 
	 * @param a
	 *            string
	 * @return String
	 * @author A-5874
	 */
	public String plainString(String args1) {
		// Locale locale = new Locale("en");
		// String.format("%s",data);

		String s = String.format("%s", args1);

		return s;

	}

	/**
	 * Matches two strings and returns the boolean
	 * 
	 * @param two
	 *            strings
	 * @return boolean
	 * @author A-5874
	 */
	public boolean match(String one, String two) {
		// splitString.replaceAll("\\p{Z}","");

		one = removeSpace(one);
		two = removeSpace(two);
		boolean foundMatch = true;

		int len1 = one.length();
		int len2 = two.length();
		char[] c_one = one.toCharArray();
		char[] c_two = two.toCharArray();
		if (len1 == len2) {
			for (int i = 0; i < len1; i++) {
				if (foundMatch) {
					if (c_one[i] != c_two[i]) {

						foundMatch = false;
						break;
					}
				} else
					break;
			}
		} else {
			foundMatch = false;
		}
		System.out.println("");
		return foundMatch;
	}

	/**
	 * Removes or trims string and returns the string
	 * 
	 * @param a
	 *            string
	 * @return String
	 * @author A-5874
	 */
	public String removeSpace(String data) {
		Pattern pattern = Pattern.compile("\\w+");
		Matcher matcher = pattern.matcher(data);
		String noSpaceStr = "";
		while (matcher.find()) {
			noSpaceStr = noSpaceStr.concat(matcher.group());
		}
		return noSpaceStr;
	}

	public String getClassNameRemovingExtension(String data) {
		Pattern pattern = Pattern.compile("^\\w+");
		Matcher matcher = pattern.matcher(data);
		String noSpaceStr = "";
		while (matcher.find()) {
			noSpaceStr = noSpaceStr.concat(matcher.group());
		}
		return noSpaceStr;
	}

	/**
	 * Return the calendar in specific format for domain input.
	 * 
	 * @param sFormat
	 *            giving into the calendar format
	 * @return sFormatedDate with valid numeric date format
	 */
	public String getCurrentDate(String sFormat) {

		calendar = Calendar.getInstance();
		sFormatedDate = formatedDate(calendar, sFormat);
		return sFormatedDate;
	}

	/**
	 * Returns a date after adding given number of days and return date in
	 * specific calendar format
	 * 
	 * @param intDaysToAdd
	 *            value to pass the no of days
	 * @param sFormat
	 *            format of calendar type
	 * @return calendar date before/after the no of days of current date.
	 */
	public String addDaysToCurrentDate(int intDaysToAdd, String sFormat) {
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, intDaysToAdd);
		sFormatedDate = formatedDate(calendar, sFormat);
		return sFormatedDate;
	}

	/**
	 * Returns date in new calendar format
	 * 
	 * @param cal
	 *            giving the calendar instance
	 * @param sFormat
	 *            the calendar format to be required
	 * @return the new calendar format as required.
	 */
	private String formatedDate(Calendar cal, String sFormat) {
		String sNewFormatedDate = null;
		try {
			date = cal.getTime();
			formatter = new SimpleDateFormat(sFormat);
			sNewFormatedDate = formatter.format(date);
		} catch (NullPointerException excpNull) {
			Reporter.log("Format pattern given as argument is null");
			excpNull.printStackTrace();
		} catch (IllegalArgumentException excpIllegalArgument) {
			Reporter.log("Format pattern given as argument is invalid");
			excpIllegalArgument.printStackTrace();
		}
		return sNewFormatedDate;
	}

	/**
	 * Returns a date after adding given number of days and return date in
	 * specific date format A-5735
	 * 
	 * @param intDaysToAdd
	 *            value to pass the no of days
	 * @param sFormat
	 *            format of calendar type
	 * @return calendar date before/after the no of days of current date.
	 */
	public String addDaysToCurrentDate_mod(int intDaysToAdd, String sFormat) {
		DateFormat sdf = new SimpleDateFormat(sFormat);
		calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.add(Calendar.DATE, intDaysToAdd);
		String output = sdf.format(calendar.getTime());
		return output;
	}

	/**
	 * Returns date after adding additional days to current date
	 * 
	 * @param intDaysToAdd
	 *            no: if days to be added to current date
	 * @return day value after adding additional days.
	 */
	public String getDayAfter(int intDaysToAdd) {
		Calendar calendar;
		calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.add(Calendar.DATE, intDaysToAdd);
		// String output = sdf.format(calendar.getTime());
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day + "";
	}

	/**
	 * Returns Month after adding additional days to current date
	 * 
	 * @param intDaysToAdd
	 *            no: if days to be added to current date
	 * @return month value after adding additional days.
	 */
	public String getMonth_StrAfter(int intDaysToAdd) {
		Calendar calendar;
		calendar = Calendar.getInstance();
		// calendar.setTime(new Date());
		calendar.add(Calendar.DATE, intDaysToAdd);
		// String output = sdf.format(calendar.getTime());
		int monthVal = calendar.get(Calendar.MONTH);

		return getCurrentMonth_Map(monthVal);
	}

	/**
	 * This method returns lowercase ISO 639 code of some languages
	 * 
	 * @param String
	 *            languageName
	 * @return String language code
	 */
	/**
	 * private String getLanguageCode(String languageName){ String sLangCode =
	 * null; if(languageName.equalsIgnoreCase("English")){ sLangCode = "en";
	 * }else if(languageName.equalsIgnoreCase("Turkish")){ sLangCode = "tr";
	 * }else if (languageName.equalsIgnoreCase("Spanish")){ sLangCode = "es";
	 * }else{ Reporter.log("Unsupported or unknown language: " + languageName);
	 * throw new UnknownConditionException("Unsupported or unknown language: " +
	 * languageName); } return sLangCode; }
	 */

	public String getCurrentDay(int intDaysToAdd) {
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, intDaysToAdd);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		String day = Integer.toString((calendar.get(Calendar.DATE)));
		/*
		 * if(day.length()==1){ day = "0"+day; }
		 */
		String date = day;
		return date;
	}

	public String getCurrentMonthYear(int intDaysToAdd) {
		calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, intDaysToAdd);

		int month = calendar.get(Calendar.MONTH) + 1;
		String monthString;
		switch (month) {
		case 1:
			monthString = "January";
			break;
		case 2:
			monthString = "February";
			break;
		case 3:
			monthString = "March";
			break;
		case 4:
			monthString = "April";
			break;
		case 5:
			monthString = "May";
			break;
		case 6:
			monthString = "June";
			break;
		case 7:
			monthString = "July";
			break;
		case 8:
			monthString = "August";
			break;
		case 9:
			monthString = "September";
			break;
		case 10:
			monthString = "October";
			break;
		case 11:
			monthString = "November";
			break;
		case 12:
			monthString = "December";
			break;
		default:
			monthString = "Invalid month";
			break;
		}
		String day = Integer.toString((calendar.get(Calendar.DATE)));
		if (day.length() == 1) {
			day = "0" + day;
		}
		int year = calendar.get(Calendar.YEAR);

		String date = monthString + " " + Integer.toString(year);

		return date;
	}

	public String getcurrentDateTime() {
		String dateandTime = null;

		Calendar cal = new GregorianCalendar();

		int day = cal.get(Calendar.DAY_OF_MONTH);
		//Added to match georgian calendar to current month
		cal.add(Calendar.MONTH, 1);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);

		int second = cal.get(Calendar.SECOND);
		int minute = cal.get(Calendar.MINUTE);
		int hour = cal.get(Calendar.HOUR);
		dateandTime = year + "/" + day + "/" + month + "   " + hour + ":"
				+ minute + ":" + second;
		return dateandTime;
	}

	public void WaitForPageObjectDisplayed(boolean PageObjectDetails,
			int iteration) throws InterruptedException {
		boolean flag = false;

		for (int i = 0; i < iteration; i++) {
			try {
				Thread.sleep(5000);
				if (PageObjectDetails == true) {
					flag = true;
					break;
				}
			} catch (Exception e) {
			}
		}
		if (!flag) {
			Assert.assertFalse(true);
		}
	}

	public String getCurrentMonth_Str() {
		return getCurrentMonth_Map(getcurrentMonth());
	}

	public int getcurrentMonth() {
		String month_Str = null;

		Calendar cal = new GregorianCalendar();

		int month = cal.get(Calendar.MONTH);

		// month_Str = ""+month;
		return month;
	}

	public String getCurrentMonth_Map(int monthKay) {
		Map<Integer, String> monthMap = new HashMap();

		monthMap.put(0, "january");
		monthMap.put(1, "february");
		monthMap.put(2, "march");
		monthMap.put(3, "april");
		monthMap.put(4, "may");
		monthMap.put(5, "june");
		monthMap.put(6, "july");
		monthMap.put(7, "august");
		monthMap.put(8, "september");
		monthMap.put(9, "october");
		monthMap.put(10, "november");
		monthMap.put(11, "december");

		return monthMap.get(monthKay);
	}

	public void threadSleep(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPropertiesValue(String property) {
		/* code to fetch values from properties file */
		Properties props = new Properties();
		InputStream is = null;
		String userDir = System.getProperty("user.dir");
		String propertyPath = userDir
				+ "\\src\\resources\\Properties\\SuiteConfig.properties";
		// First try loading from the current directory
		try {
			File f = new File(propertyPath);
			is = new FileInputStream(f);
		} catch (Exception e) {
			is = null;
		}
		try {
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return props.getProperty("TotalTestCount");
	}

	public String getPropertiesConfigValue(String property) {
		/* code to fetch values from properties file */
		Properties props = new Properties();
		InputStream is = null;
		String userDir = System.getProperty("user.dir");
		String propertyPath = userDir
				+ "\\src\\resources\\Properties\\ProjectConfig.properties";
		// First try loading from the current directory
		try {
			File f = new File(propertyPath);
			is = new FileInputStream(f);
			props.load(is);
		} catch (IOException e) {
			is = null;
		}

		return props.getProperty(property);
	}

	
	public String getPropertiesTestDataFile(String property) {
		/* code to fetch values from properties file */
		Properties props = new Properties();
		InputStream is = null;
		String userDir = System.getProperty("user.dir");
		String propertyPath = userDir
				+ "\\src\\resources\\Properties\\TestDataConfig.properties";
		// First try loading from the current directory
		try {
			File f = new File(propertyPath);
			is = new FileInputStream(f);
			props.load(is);
		} catch (IOException e) {
			is = null;
		}

		return props.getProperty(property);
	}
	// Added by A-2798
	/**
	 * Method to append Date and time (in the format -> _03_Jul_2014_12_22_20 )
	 * to the given String
	 */
	public String appendDatandTime(String fileName) {

		return (new StringBuilder(fileName).append(new SimpleDateFormat(
				"_dd_MMM_yyyy_hh_mm_ss").format(new Date()))).toString();
	}

	public String formatToYYYYMMDD(String date) throws ParseException {
		/*
		 * This method is used to convert date format from DD-MM-YYYY to
		 * 2014/07/15
		 */
		if (StringUtils.isNotEmpty(date)) {
			DateFormat formatter = new SimpleDateFormat("DD-mm-yyyy");
			Date inputDate = (Date) formatter.parse(date);
			SimpleDateFormat newFormat = new SimpleDateFormat("yyyy/mm/DD");
			String finalString = newFormat.format(inputDate);
			return finalString;
		}
		return null;
	}

	/**
	 * Using this method, user will be Update the Testcase excel sheet Execution
	 * Status , parameters will be passed from CustomTestReport
	 * 
	 * @author A-5907
	 * @name Nagaraju
	 * @param currentTestName
	 * @param ExeStatus
	 * @param
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */

	public void updateTestCaseXLS(String currentTestName,
			String currentSheetName, String ExeStatus)
			throws InvalidFormatException, IOException, NullPointerException {
		// TODO Auto-generated method stub

		String userDir = System.getProperty("user.dir");
		String propertyPath = userDir
				+ "\\src\\resources\\TestCase\\TestCase.xlsx";

		File file = new File(propertyPath);
		FileInputStream fis = new FileInputStream(file);

		Workbook wb = WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheet(currentSheetName);
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setFontName("Arial");

		int rowLimit = sheet.getLastRowNum();

		// Row row = null;
		for (int i = 2; i <= rowLimit; i++) {

			Row row = sheet.getRow(i);

			// Get TestName details
			Cell cellRunStatID = row.getCell(1);
			try {
				String TestName = cellRunStatID.getStringCellValue().toString();
				// System.out.println(TestName);

				if (TestName.equalsIgnoreCase(currentTestName)) {

					if (ExeStatus.equalsIgnoreCase("pass")) {

						// style.setFillBackgroundColor(IndexedColors.LIGHT_GREEN.getIndex());
						// style.setFillPattern(CellStyle.ALIGN_FILL);
						font.setColor(IndexedColors.GREEN.getIndex());
						style.setFont(font);
						Cell cellStatus = row.createCell(8);
						cellStatus.setCellStyle(style);
						cellStatus.setCellValue("Pass");

					}
					if (ExeStatus.equalsIgnoreCase("Fail")) {

						// style.setFillBackgroundColor(IndexedColors.RED.getIndex());
						// style.setFillPattern(CellStyle.ALIGN_FILL);
						font.setColor(IndexedColors.RED.getIndex());
						style.setFont(font);
						Cell cellStatus = row.createCell(8);
						cellStatus.setCellStyle(style);
						cellStatus.setCellValue("Fail");
					}
					if (ExeStatus.equalsIgnoreCase("Skip")) {

						// style.setFillBackgroundColor(IndexedColors.TAN.getIndex());
						// style.setFillPattern(CellStyle.ALIGN_FILL);
						font.setColor(IndexedColors.BLUE.getIndex());
						style.setFont(font);
						Cell cellStatus = row.createCell(8);
						cellStatus.setCellStyle(style);
						cellStatus.setCellValue("Skip");
					}
					// Cell cellStatus = row.createCell(8);
					// cellStatus.setCellValue(ExeStatus);
				}
			} catch (Exception ex) {
			}
		}
		FileOutputStream fileOut = new FileOutputStream(file);
		wb.write(fileOut);
		fileOut.close();
		// Write the output to a file

	}

	/**
	 * Moves a jQuery slider to percental position, don't care about directions
	 * 
	 * @param slider
	 *            to move
	 * @param percent
	 *            to set the slider
	 * @author A-5874
	 * @param Slider
	 *            , percent
	 */
	public void moveSliderToPercent(WebDriver driver, WebElement slider,
			int percent) {

		Actions builder = new Actions(driver);

		Action dragAndDrop;

		int height = slider.getSize().getHeight();
		int width = slider.getSize().getWidth();

		if (width > height) {
			// highly likely a horizontal slider
			dragAndDrop = builder.clickAndHold(slider)
					.moveByOffset(-(width / 2), 0)
					.moveByOffset((int) ((width / 100) * percent), 0).release()
					.build();
		} else {
			// highly likely a vertical slider
			dragAndDrop = builder.clickAndHold(slider)
					.moveByOffset(0, -(height / 2))
					.moveByOffset(0, (int) ((height / 100) * percent))
					.release().build();
		}

		dragAndDrop.perform();

	}

	/**
	 * Used for tabbing and entering - used in security certificate exception
	 * handling
	 * 
	 * @author A-5874
	 * @param n
	 */
	public static void tabbingUsingRobot(int n) throws AWTException,
			InterruptedException {
		Robot robot1 = new Robot();
		for (int i = 0; i <= n; i++) {
			robot1.keyPress(KeyEvent.VK_TAB);
			Thread.sleep(1000);
		}
		Thread.sleep(2000);
		robot1.keyPress(KeyEvent.VK_ENTER);
	}

	/**
	 * Used to avoid stale element reference exception
	 * 
	 * @author A-5874
	 * @param driver
	 *            , by
	 */
	public boolean avoidStaleElementException(WebDriver driver, By by) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				driver.findElement(by).click();
				result = true;
				break;
			} catch (StaleElementReferenceException e) {
			}
			attempts++;
		}
		return result;
	}

	/**
	 * Some button clicks fails in IE, since those buttons where not in view
	 * port.This code is from
	 * :http://stackoverflow.com/questions/15252837/selenium
	 * -javascriptexecutor-on-ie9-results-in-element-was-not-scrolled-into-the
	 * 
	 * 
	 * @author Moshe George
	 * @version 1.0
	 * @since 2014-07-22
	 */
	public void actionClick(WebDriver driver, WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String urlBeforeClick = driver.getCurrentUrl();
			Actions builder = new Actions(driver);
			builder.moveToElement(element).doubleClick(element).perform();
			String urlAfterClick = driver.getCurrentUrl();
			(new WebDriverWait(driver, 5)).until(ExpectedConditions
					.not(ExpectedConditions.visibilityOf(element)));
			if (urlBeforeClick.equals(urlAfterClick))
				// element.click();dp_adultCount_header =
				// By.xpath(".//span[contains(text(),'Adults')]/../.."); } catch
				// (org.openqa.selenium.StaleElementReferenceException e) {
				System.out.println("StaleElementReferenceException happened");
			// e.printStackTrace();

		} catch (Exception e) {
			System.out.println("Some other exception happened");
			// e.printStackTrace();
		}
	}

	/**
	 * Get current window handle.
	 * 
	 * @author Moshe George
	 * @version 1.0
	 * @since 2014-07-22
	 */
	public String getCurrentWindowhandle(WebDriver driver) {
		return driver.getWindowHandle();
	}

	public String takeScreenshotElementCustomizedForAlertUsingWrapsDriver(
			WebElement element, WebDriver driver, String currentBrowser,
			String failedClass, String screenShotPath) throws IOException,
			HeadlessException, AWTException {
		String newFileNamePath = null;
		String imageName = null;
		DateFormat dateFormat = null;
		File directory = new File(".");
		calendar = Calendar.getInstance();
		date = calendar.getTime();
		dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");
		WrapsDriver wrapsDriver = (WrapsDriver) element;
		File screenshot = ((TakesScreenshot) wrapsDriver.getWrappedDriver())
				.getScreenshotAs(OutputType.BYTES.FILE);
		Rectangle rectangle = new Rectangle(element.getSize().width,
				element.getSize().height);
		Point location = element.getLocation();
		BufferedImage bufferedImage = ImageIO.read(screenshot);
		BufferedImage destImage = bufferedImage.getSubimage(location.x,
				location.y, rectangle.width, rectangle.height);
		ImageIO.write(destImage, "png", screenshot);

		imageName = "_" + currentBrowser + "_" + failedClass + "_"
				+ dateFormat.format(date) + ".png";
		if (screenShotPath.isEmpty())
			newFileNamePath = System.getProperty("user.dir")
					+ "\\screenshots\\" + imageName;
		else
			newFileNamePath = screenShotPath + "\\" + imageName;
		System.out.println("NewFileNamePath :" + newFileNamePath);

		FileUtils.copyFile(screenshot, new File(newFileNamePath));
		return newFileNamePath;

	}

	/*
	 * public void takeScreenshotElementCustomizedForAlertUsingRobot( WebElement
	 * element, WebDriver driver, String currentBrowser, String failedClass,
	 * String screenShotPath) throws HeadlessException, AWTException,
	 * IOException { String newFileNamePath = null; String imageName = null;
	 * DateFormat dateFormat = null; File directory = new File("."); calendar =
	 * Calendar.getInstance(); date = calendar.getTime(); dateFormat = new
	 * SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss"); imageName = "_" +
	 * currentBrowser + "_" + failedClass + "_" + dateFormat.format(date) +
	 * ".png";
	 * 
	 * if (screenShotPath.isEmpty()) newFileNamePath =
	 * System.getProperty("user.dir") + "\\screenshots\\" + imageName; else
	 * newFileNamePath = screenShotPath + "\\" + imageName;
	 * 
	 * System.out.println("NewFileNamePath :" + newFileNamePath); BufferedImage
	 * destImage = new Robot() .createScreenCapture(new
	 * Rectangle(Toolkit.getDefaultToolkit() .getScreenSize()));
	 * ImageIO.write(destImage, "png", new File(newFileNamePath)); }
	 */
	public String getScreenShot(WebDriver driver, String currentBrowser,
			String failedClass, String screenShotPath) {

		System.out.println("getScreenShot....#");
		String newFileNamePath = null;
		String imageName = null;
		Calendar calendar = null;
		DateFormat dateFormat = null;
		File screenshot = null;
		if (!driver.getClass().getCanonicalName().contains("htmlunit")) {
			try {
				// s_logger.write("entered simplesearchAddRooms_normal sequence");
				WebDriver augmentedDriver = new Augmenter().augment(driver);
				File directory = new File(".");

				calendar = Calendar.getInstance();
				date = calendar.getTime();
				dateFormat = new SimpleDateFormat("dd_MMM_yyyy__HH_mm_ss");

				screenshot = ((TakesScreenshot) augmentedDriver)
						.getScreenshotAs(OutputType.BYTES.FILE);
				imageName = "_" + currentBrowser + "_" + failedClass + "_"
						+ dateFormat.format(date) + ".png";
				if (screenShotPath.isEmpty())
					newFileNamePath = System.getProperty("user.dir")
							+ "\\screenshots\\" + imageName;
				else
					newFileNamePath = System.getProperty("user.dir")
							+ "\\screenshots\\" + "\\" + imageName;
					
				System.out.println("NewFileNamePath :" + newFileNamePath);
				
				FileUtils.copyFile(screenshot, new File(newFileNamePath));
				newFileNamePath = "..\\..\\" + "screenshots\\" + imageName;
				if(screenShotPath.equalsIgnoreCase("NA")){
					newFileNamePath = "..\\..\\" + "NA\\" + imageName;
				}
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
					imageName = "_" + currentBrowser + "_" + failedClass + "_"
							+ dateFormat.format(date) + ".png";
					newFileNamePath = System.getProperty("user.dir")
							+ "\\screenshots\\" + imageName;
					System.out.println("NewFileNamePath :" + newFileNamePath);
					FileUtils.copyFile(screenshot, new File(newFileNamePath));
				} catch (IOException e1) {

					e1.printStackTrace();
				}
				e.printStackTrace();
				// s_logger.write("exiting simplesearchAddRooms_WebDriverException_catch sequence");
			} catch (IOException e) {
			}
		} else
			newFileNamePath = "No Screenshot, running HTML UNIT";
		return newFileNamePath;
	}

	/**
	 * To check whether the title of the current title contains, the parameter
	 * value passed.
	 * 
	 * @author A-5735
	 * @since 09-09-2014
	 * @param titlePart
	 *            the string we are planning to match with window title
	 * @return In match success true else false.
	 */
	public boolean patternMatchTitle(WebDriver driver, String titlePart) {
		String fullTitle = driver.getTitle();
		boolean matchFound = false;

		if (fullTitle.toLowerCase().contains(titlePart.toLowerCase())) {
			matchFound = true;
		}
		return matchFound;
	}

	/**
	 * Purpose : this will close all child/parent window, except the window with
	 * the title passed. It will also switch control to that window and return
	 * driver
	 * 
	 * @author Moshe George
	 * @version 1.0
	 * @since 2014-07-22
	 */
	public WebDriver retainOnlyThisWin_WithTitle(String title, WebDriver driver) {

		WebDriver popup = null;
		Set<String> windowIterator = driver.getWindowHandles();
		boolean windowAvailable = false;
		System.out.println("@retainOnlyThisWin_WithTitle No of windows :  "
				+ windowIterator.size());
		if (title != null && windowIterator.size() > 1) {
			try {
				for (String s : windowIterator) {
					String windowHandle = s;
					popup = driver.switchTo().window(windowHandle);
					if (popup.getTitle().equals(title)) {
						windowAvailable = true;
					}

				}
				if (windowAvailable) {
					for (String s : windowIterator) {
						String windowHandle = s;
						popup = driver.switchTo().window(windowHandle);
						System.out
								.println("Window Title : " + popup.getTitle());
						System.out.println("Window Url : "
								+ popup.getCurrentUrl());
						if (!popup.getTitle().equals(title)) {
							popup.close();
						}

					}
					windowIterator = driver.getWindowHandles();
					for (String s : windowIterator) {
						String windowHandle = s;
						popup = driver.switchTo().window(windowHandle);
					}
					System.out.println("Window Title :" + popup.getTitle());
					System.out.println();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return popup;
	}

	public WebDriver switchToThisWin_WithTitle(String title, WebDriver driver) {
		// Purpose : To switch to a window with a given title.
		WebDriver popup = null;
		Set<String> windowIterator = driver.getWindowHandles();
		System.out.println("@switchToThisWin_WithTitle No of windows :  "
				+ windowIterator.size());
		if (title != null && windowIterator.size() > 1) {
			try {
				for (String s : windowIterator) {
					String windowHandle = s;
					popup = driver.switchTo().window(windowHandle);

					if (popup.getTitle().contains(title)) {
						Thread.sleep(5000);
						driver.switchTo().window(s);
						driver.manage().window().maximize();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return popup;
	}

	public boolean isWindowAvailableWithTitle(String title, WebDriver driver) {
		// Purpose : To check whether a window with title is available.
		WebDriver popup = null;
		Set<String> windowIterator = driver.getWindowHandles();
		boolean windowAvailable = false;
		System.out.println("@isWindowAvailableWithTitle No of windows :  "
				+ windowIterator.size());
		if (title != null && windowIterator.size() > 1) {
			try {
				for (String windowHandle : windowIterator) {
					Thread.sleep(1000);
					popup = driver.switchTo().window(windowHandle);
					if (popup.getTitle().contains(title)) {
						windowAvailable = true;
						Thread.sleep(2000);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return windowAvailable;
	}

	/**
	 * This fn will make the test wait till the window mentioned in title is
	 * displayed. To avoid infinite loop a counter is also added
	 * 
	 * @author Moshe George
	 * @version 1.0
	 * @since 2014-07-22
	 */
	public boolean waitTillWindowWithTitleIsAvailable(String title,
			WebDriver driver, int counter) {
		System.out.println("waitTillWindowWithTitleIsAvailable");
		boolean windowNotAvailable = true;
		int localCounter = 0;
		while (windowNotAvailable && localCounter < counter) {
			localCounter++;
			WebDriver popup = null;
			Set<String> windowIterator = driver.getWindowHandles();
			System.out
					.println("@waitTillWindowWithTitleIsAvailable No of windows :  "
							+ windowIterator.size());
			if (title != null && windowIterator.size() > 1) {
				try {
					for (String windowHandle : windowIterator) {
						Thread.sleep(1000);
						popup = driver.switchTo().window(windowHandle);
						if (popup.getTitle().contains(title)) {
							windowNotAvailable = false;
							Thread.sleep(500);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return windowNotAvailable;
	}

	/**
	 * To switch to child window.
	 * 
	 * @author Moshe George
	 * @version 1.0
	 * @since 2014-07-22
	 */
	public boolean switchToChildWindow(WebDriver driver, String parentHandle) {
		boolean switchHappened = false;
		Set<String> allWindowHandles = driver.getWindowHandles();
		if (allWindowHandles.size() > 1) {
			while (!switchHappened) {
				for (String strWindowHandle : allWindowHandles) {
					if (!strWindowHandle.equals(parentHandle)) {
						driver.switchTo().window(strWindowHandle);
						System.out.println(driver.getTitle());
						switchHappened = true;
						break;
					}
				}
			}
		}
		return switchHappened;
	}

	/**
	 * Date format in selenium using java
	 * 
	 * @author A-5874
	 * @param period
	 *            ,format
	 */
	// Date format in selenium using java
	public String getDate(int period, String format) {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		currentDate.add(Calendar.DAY_OF_MONTH, period);
		String date = formatter.format(currentDate.getTime());
		return date;
	}

	/**
	 * Code to Handle SSL errors in IE Use the Webdriver instance to navigate to
	 * the result of the JS
	 * 
	 * @author A-5874
	 * @param driver
	 *            ,element
	 */
	public static void CertErrorHandlerForIE(WebDriver driver) {
		driver.navigate().to(
				"javascript:document.getElementById('overriderlink').click()");
	}

	/**
	 * Code to highlight a web element in Selenium.Makes use of Javascript to
	 * highlight. Uses black colour for selection.We can use any of the colours
	 * that Javascript recognizes. A good practice is to call this method before
	 * actually selecting the element using selenium webdriver. So that we can
	 * see what selenium is actually doing before working with it. Calling it
	 * later may lead to exceptions,since the element might not be visible after
	 * working with it.
	 * 
	 * @author A-5874
	 * @param driver
	 *            ,element
	 */

	public void highlightElement(WebDriver driver, WebElement element) {
		for (int i = 0; i < 1; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);",
					element, "color: black; border: 3px solid black;");

		}
	}

	/**
	 * capture screenshot of Image element. We need to use many different
	 * classes and methods of java and selenium webdriver to capture web element
	 * screenshot. Test execution sequence to capture screenshot of element Is
	 * as bellow. Locate Image element and call captureElementScreenshot
	 * function to capture Its screenshot. Capture full screenshot as buffered
	 * Image Get element's height and width using getSize() method as described
	 * In THIS POST. Get element's X Y coordinates using Point class as
	 * described In THIS POST. Read buffered Image. Crop buffered Image using
	 * element's x y coordinate position and height width parameters. Save
	 * cropped Image at destination location physically.
	 * 
	 * @author A-5874
	 * @param WebElement
	 *            element
	 */
	public void captureElementScreenshot(WebElement element) throws IOException {
		// Capture entire page screenshot as buffer.
		// Used TakesScreenshot, OutputType Interface of selenium and File class
		// of java to capture screenshot of entire page.
		File screen = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);

		// Used selenium getSize() method to get height and width of element.
		// Retrieve width of element.
		int ImageWidth = element.getSize().getWidth();
		// Retrieve height of element.
		int ImageHeight = element.getSize().getHeight();

		// Used selenium Point class to get x y coordinates of Image element.
		// get location(x y coordinates) of the element.
		Point point = element.getLocation();
		int xcord = point.getX();
		int ycord = point.getY();

		// Reading full image screenshot.
		BufferedImage img = ImageIO.read(screen);

		// cut Image using height, width and x y coordinates parameters.
		BufferedImage dest = img.getSubimage(xcord, ycord, ImageWidth,
				ImageHeight);
		ImageIO.write(dest, "png", screen);

		// Used FileUtils class of apache.commons.io.
		// save Image screenshot In D: drive.
		FileUtils.copyFile(screen, new File("D:\\screenshot.png"));
	}

	/**
	 * Scroll to an particular element, used in combination with the below
	 * highlightelement function
	 * 
	 * @author A-5874
	 * @param WebElement
	 *            ScrolltoThisElement
	 */
	public static void scrolltoElement(WebElement ScrolltoThisElement) {
		Coordinates coordinate = ((Locatable) ScrolltoThisElement)
				.getCoordinates();
		coordinate.onPage();
		coordinate.inViewPort();
	}

	/**
	 * Highlight an particular element, used in combination with the above
	 * scrolltoElement function Code to highlight a web element in
	 * Selenium.Makes use of Javascript to highlight. Uses black colour for
	 * selection.We can use any of the colours that Javascript recognizes. A
	 * good practice is to call this method before actually selecting the
	 * element using selenium webdriver. So that we can see what selenium is
	 * actually doing before working with it. Calling it later may lead to
	 * exceptions,since the element might not be visible after working with it.
	 * 
	 * @author A-5874
	 * @param driver
	 *            ,element
	 */
	public static void highlightelement(WebDriver driver, WebElement element) {
		for (int i = 0; i < 4; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute(‘style’, arguments[1]);",
					element, "color: yellow; border: 4px solid blue;");
			js.executeScript(
					"arguments[0].setAttribute(‘style’, arguments[1]);",
					element, "");
		}
	}

	/**
	 * Get and extract all cookies of provided URL and print cookie parameters.
	 * 
	 * @author A-5874
	 * @param driver
	 */
	public void extractCookie(WebDriver driver) {

		Set<Cookie> totalCookies = driver.manage().getCookies();
		System.out.println("Total Number Of cookies :" + totalCookies.size());

		for (Cookie currentCookie : totalCookies) {
			System.out.println(String.format("%s -> %s -> %s -> %s",
					"Domain Name : " + currentCookie.getDomain(),
					"Cookie Name : " + currentCookie.getName(),
					"Cookie Value : " + currentCookie.getValue(),
					"Cookie Expiry : " + currentCookie.getExpiry()));
		}
	}

	/**
	 * Used To find the broken links
	 * 
	 * @author A-5874
	 * @param urlString
	 */
	public static int getResponseCode(String urlString) throws IOException {
		URL u = new URL(urlString);
		HttpURLConnection h = (HttpURLConnection) u.openConnection();
		h.setRequestMethod("GET");
		h.connect();
		return h.getResponseCode();
	}

	/**
	 * Used To generate a random file name
	 * 
	 * @author A-5874
	 * @param urlString
	 */
	private String generateRandomFilename(Throwable arg0) {
		Calendar c = Calendar.getInstance();
		String filename = arg0.getMessage();
		int i = filename.indexOf('\n');
		filename = filename.substring(0, i).replaceAll("\\s", "_")
				.replaceAll(":", "")
				+ ".jpg";
		filename = "" + c.get(Calendar.YEAR) + "-" + c.get(Calendar.MONTH)
				+ "-" + c.get(Calendar.DAY_OF_MONTH) + "-"
				+ c.get(Calendar.HOUR_OF_DAY) + "-" + c.get(Calendar.MINUTE)
				+ "-" + c.get(Calendar.SECOND) + "-" + filename;
		return filename;
	}

	/**
	 * Used To capture screenshot using Bufferd image
	 * 
	 * @author A-5874
	 * @param urlString
	 */
	private void createScreenCaptureJPEG(String filename) {
		try {
			BufferedImage img = getScreenAsBufferedImage();
			File output = new File(filename);
			ImageIO.write(img, "jpg", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Used To capture screenshot using Robot
	 * 
	 * @author A-5874
	 * @param urlString
	 */
	private BufferedImage getScreenAsBufferedImage() {
		BufferedImage img = null;
		try {
			Robot r;
			r = new Robot();
			Toolkit t = Toolkit.getDefaultToolkit();
			Rectangle rect = new Rectangle(t.getScreenSize());
			img = r.createScreenCapture(rect);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * This method helps from URL for webdriver
	 * 
	 * @param remoteURL
	 * @param port
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL getWebDriverURL(String remoteURL, int port)
			throws MalformedURLException {
		if (port > 0 && remoteURL != null && !remoteURL.equals("")) {
			if (!remoteURL.startsWith("http://")) {
				return new URL("http://" + remoteURL + ":" + port + "/wd/hub");
			} else
				return new URL(remoteURL + ":" + port + "/wd/hub");
		}
		return null;
	}

	/**
	 * This function is mainly used by TestNG data provider
	 * 
	 * @author A-5874
	 * @param mapList
	 * @param log
	 * @return
	 */
	public static Object[][] listHashMapToObject(
			List<HashMap<String, String>> mapList) {
		Object[][] data = new Object[mapList.size()][1];
		{
			for (int i = 0; i < mapList.size(); i++)
				data[i][0] = mapList.get(i);
		}
		return data;
	}

	/**
	 * This function is mainly used by get US Phone Numbers
	 * 
	 * @author A-5874
	 * @param string
	 * @return phoneNumbers
	 */
	public static List<String> USPhoneNumber(String string) {
		List<String> phoneNumbers = new ArrayList<String>();
		String regexPhone = "\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})";
		Pattern paternPhone = Pattern.compile(regexPhone);
		Matcher matcherPhone = paternPhone.matcher(string);
		while (matcherPhone.find()) {
			phoneNumbers.add(matcherPhone.group());
		}
		return phoneNumbers;
	}

	/**
	 * This function is used to get the current time stamp
	 * 
	 * @author A-5874
	 * @return Timestamp
	 */
	public static String getTimeStamp() {
		java.util.Date date = new java.util.Date();
		return new Timestamp(date.getTime()).toString();
	}

	/**
	 * This function is used to get the file name from the url provided
	 * 
	 * @author A-5874
	 * @return fileName
	 */
	public static String getFileNameFromURL(String URL) {
		String fileName = "";
		String[] path = URL.split("/");
		if (URL.endsWith("/"))
			fileName = path[path.length - 1];
		else {
			String[] fileNameSplit = path[path.length - 1].split(".");
			if (fileNameSplit.length > 0)
				fileName = path[path.length - 1];
		}
		return fileName;
	}

	/**
	 * This function is used to check the email is valid or not
	 * 
	 * @author A-5874
	 * @return email
	 */
	public boolean checkValidEmail(String email) {
		String REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		boolean isValid = email.matches(REGEX);
		return isValid;

	}

	public String getDomainName() {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		String CurrentURLUsingJS = (String) javascript
				.executeScript("return document.domain");
		return CurrentURLUsingJS;
	}

	public void generateAlertAfterStartingExecution() {
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript
				.executeScript("alert('Test Case Execution Is started Now..');");
	}

	public String closeAlertAndGetItsText() {
		boolean acceptNextAlert = true;
		String alertText = null;
		try {
			Alert alert = driver.switchTo().alert();
			if (acceptNextAlert) {
				alertText = alert.getText();
				alert.accept();
			} else {
				alert.dismiss();
			}
			// return alert.getText();
		} finally {
			acceptNextAlert = true;
		}
		return alertText;
	}
	
	
	/**
	 * Overloaded method return the  date after the no of days from current date.
	 * @param int days, the number of days to be added to the current date 
	 * @param String languageName, the language in which the date elements must be returned
	 * @return ArrayList<String> containing the WEEK_OF_YEAR, DAY_OF_WEEK, DAY_OF_MONTH, DATE & TIME, MONTH
	 *			in respective language
	 */
	public ArrayList<String> generateDate(int days, String languageName) {

		ArrayList dateList = new ArrayList();
		DateFormatSymbols symbols = new DateFormatSymbols(new Locale(getLanguageCode(languageName)));
		String[] months = symbols.getMonths();

		Calendar calendar = Calendar.getInstance();
		Date date = new Date();
		calendar.add(Calendar.DATE, days);

		calendar.setTime(calendar.getTime());
		dateList.add(calendar.get(Calendar.WEEK_OF_YEAR));
		dateList.add(calendar.get(Calendar.DAY_OF_WEEK));
		dateList.add(calendar.get(Calendar.DAY_OF_MONTH));
		dateList.add(calendar.getTime());
		dateList.add(months[calendar.get(Calendar.MONTH)]);	

		return dateList;	

	}
	
	
	/**
	 * Overloaded method return the  date after the no of days from current date.
	 * @param int days, the number of days to be added to the current date 
	 * @return ArrayList<String> containing the WEEK_OF_YEAR, DAY_OF_WEEK, DAY_OF_MONTH, DATE & TIME, MONTH
	 *			in English	 
	 */		   
	public ArrayList<String> generateDate(int days){
		return generateDate(days, getLanguageCode("English"));
	}

	
	

	/**
	 * This method returns lowercase ISO 639 code of some languages 
	 * @param String languageName
	 * @return String language code
	 */
	private String getLanguageCode(String languageName){
		String sLangCode = null;
		if(languageName.equalsIgnoreCase("English")){
			sLangCode = "en";
		}else if(languageName.equalsIgnoreCase("Turkish")){
			sLangCode = "tr";
		}else if (languageName.equalsIgnoreCase("Spanish")){
			sLangCode = "es";
		}else{
			Reporter.log("Unsupported or unknown language: " + languageName);
			throw new UnknownConditionException("Unsupported or unknown language: " + languageName);
		}
		return sLangCode;
	}

}
