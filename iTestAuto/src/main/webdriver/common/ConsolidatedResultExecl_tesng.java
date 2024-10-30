package common;
/** 
 * @author A-5907

 * @Name: Nagaraju vutukuri
 * Description : Consolidated Report for one cycle execution.  Calculate pass and fail percentage 
 * 				in overall detail messages written in excel sheet and getting all fail 
 * 				entries and stored in one consolidated result file 
 * 
 * Consolidation of failed reports from all excel reports created during execution.
 * How to run: Execute using menu option, Run->RunAs->Java application, no need to execute from suite file.
 * Date created: 06-Nov-2014
 * Modified by: Arjun B M
 * Date Modified: 2/10/2015
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.testng.annotations.Test;

import common.CommonUtility;
import common.DriverSetup;
import common.ExcelReadWrite;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ConsolidatedResultExecl_tesng extends DriverSetup {

	private static int totalFileCount = 0; // variable to store total excle
											// report file count
	private static String consolidatedFilename = null; // Variable for
														// consolidated excel
														// file name
	private static int consolidatedFailCount = 0; // variable to store required
													// files count
	private static int consolidatedPassCount = 0; // variable to store required
													// files count
	private static int eachfilefailCount = 0; // variable to store required each
												// files fail step count
	private static int eachfilePasscount = 0; // variable to store required each
												// files pass step count
	public static double failPercent = 0; // variable to store required Fail
											// count percentage
	public static double passPercent = 0; // variable to store required Pass
											// count percentage
	private static int totalNoOfSteps = 0; // variable to store required files
											// count
	private static String testStartTime; // variable to store current time stamp
	private static String returnFileName = null; // variable to store the output
													// file path

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\reports\\xls";
		System.out.println(path);
		File folder = new File(path);
		File[] listOfFilesInDirectory = folder.listFiles();
		System.out.println(" total no.of files in this folder is : "
				+ listOfFilesInDirectory.length);
		consolidatedFilename = createConsolidatedResultExecl();
		// //
		// Added By Arjun - In header Consolidated Excel Report and note has
		// been provided
		FileInputStream fileInputStream = new FileInputStream(
				consolidatedFilename);
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		HSSFSheet worksheet = workbook.getSheetAt(0);
		int rowcount = ((worksheet.getLastRowNum() - worksheet.getFirstRowNum()));
		CreationHelper createHelper = workbook.getCreationHelper();
		// CellStyle hlink_style = workbook.createCellStyle();
		System.out.println(returnFileName);
		CellStyle cellStyle = workbook.createCellStyle();
		HSSFFont headerFont = workbook.createFont();
		// int headerRowNum = worksheet.getLastRowNum();
		HSSFRow headerRow = worksheet.createRow(rowcount + 1);
		HSSFCell headerCell = headerRow.createCell(0);
		cellStyle.setBorderTop((short) 1);
		cellStyle.setBorderBottom((short) 1);
		headerFont.setFontHeightInPoints((short) 20);
		headerFont.setFontName(HSSFFont.FONT_ARIAL);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(HSSFColor.BLACK.index);
		cellStyle.setFont(headerFont);
		headerCell = headerRow.createCell(4);
		headerCell.setCellValue("Consolidated Excel Report");
		headerCell.setCellStyle(cellStyle);
		HSSFRow noteRow = worksheet.createRow(rowcount + 3);
		HSSFCell noteCell = noteRow.createCell(0);
		CellStyle noteStyle = workbook.createCellStyle();
		HSSFFont noteFont = workbook.createFont();
		noteFont.setFontName(HSSFFont.FONT_ARIAL);
		noteFont.setColor(HSSFColor.BLACK.index);
		noteStyle.setFont(noteFont);
		noteCell = noteRow.createCell(1);
		noteCell.setCellValue("Note: Consolidation of failed reports from all excel reports created during execution.");
		noteCell.setCellStyle(noteStyle);
		try {
			FileOutputStream outFile = new FileOutputStream(new File(
					returnFileName));
			workbook.write(outFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// getting and rotating each and very file Specified folder
			for (int numberOfFilesInFolder = 0; numberOfFilesInFolder < listOfFilesInDirectory.length; numberOfFilesInFolder++) {
				if (listOfFilesInDirectory[numberOfFilesInFolder].isFile()) {
					totalFileCount++;
					String files = listOfFilesInDirectory[numberOfFilesInFolder]
							.getName();
					System.out
							.println("File names is -- " + files
									+ " -- file number is --  "
									+ numberOfFilesInFolder);
					String excelfilepath = path + "\\" + files;
					readExcelFileAndGetContent(excelfilepath, files);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		totalNoOfSteps = consolidatedPassCount + consolidatedFailCount; 
		// variable to stores total no.of steps count
		passPercent = (double) (consolidatedPassCount) / (totalNoOfSteps) * 100;
		// variable to stores only pass count percentage in all files
		failPercent = (double) (consolidatedFailCount) / (totalNoOfSteps) * 100;
		/* variable to stores only fail count percentage in all files */
		System.out.println("Total file count = " + totalFileCount);
		// System.out.println("Required file count = " + fileCount);
		System.out.println("Total no.of count = " + totalNoOfSteps);
		System.out.println("Total  fail count = " + consolidatedFailCount);
		System.out.println("Total pass count = " + consolidatedPassCount);
		System.out.println("Total pass Percentage  = " + passPercent + "%");
		System.out.println("Total fail Percentage = " + failPercent + "%");
	}

	// function is specified for reading each file and getting fail rows in each
	// and every file
	public static void readExcelFileAndGetContent(String excelfilepath,
			String files) {
		try {
			eachfilePasscount = 0;
			eachfilefailCount = 0;
			FileInputStream fileInputStream = new FileInputStream(excelfilepath);
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheetAt(0);
			HSSFRow row1;
			int rowcount = ((worksheet.getLastRowNum() - worksheet
					.getFirstRowNum()));
			System.out.println("sheet row count is  " + rowcount);
			System.out.println(worksheet.getLastRowNum());
			System.out
					.println("total no.of rows in the sheet is : " + rowcount);
			// storing the each row data in variable as long as getting fails
			// row data pushing to variables
			for (int rowno = 0; rowno < rowcount; rowno++) {
				row1 = worksheet.getRow(rowno + 5);
				String result = row1.getCell(5).getStringCellValue().toString();
				System.out.println(row1.getCell(5).getStringCellValue()
						.toString());
				// getting the Step No column data and store into Stepno
				// variable
				HSSFCell cellA1 = row1.getCell(1);
				String StepNo = cellA1.getStringCellValue();
				// getting the Test Script Name column data and store into
				// TestScriptName variable
				HSSFCell cellB1 = row1.getCell(2);
				String TestScriptName = cellB1.getStringCellValue();
				// getting the Step Description column data and store into
				// StepDescription variable
				HSSFCell cellC1 = row1.getCell(3);
				String StepDescription = cellC1.getStringCellValue();
				// getting the Message column data and store into Message
				// variable
				HSSFCell cellD1 = row1.getCell(4);
				String Message = cellD1.getStringCellValue();
				// getting the Result(Pass/Fail) column data and store into
				// Result_Pass_Fail variable
				HSSFCell cellE1 = row1.getCell(5);
				String Result_Pass_Fail = cellE1.getStringCellValue();
				// getting the Screen shot column data and store into Screenshot
				// variable
				HSSFCell cellF1 = row1.getCell(6);
				HSSFHyperlink Screenshot = cellF1.getHyperlink();
				// getting the Actual Result column data and store into
				// ActualResult variable
				HSSFCell cellG1 = row1.getCell(7);
				String ActualResult = cellG1.getStringCellValue();
				// getting the Expected Result column data and store into
				// ExpectedResult variable
				HSSFCell cellH1 = row1.getCell(8);
				String ExpectedResult = cellH1.getStringCellValue();
				// comparing each file each row result column with "Fail" Sting
				// if it matches with Fail string it pushes row data into output
				// file
				if (result.equalsIgnoreCase("Fail")) {
					consolidatedFailCount++;
					eachfilefailCount++;
					if (eachfilefailCount <= 1) {
						createSheetWithHeaders(consolidatedFilename, files,
								excelfilepath);
					}
					consolidatedInsertData(files, StepNo, TestScriptName,
							StepDescription, Message, Result_Pass_Fail,
							Screenshot, ActualResult, ExpectedResult);
				}
				if (result.equalsIgnoreCase("Pass")) {
					consolidatedPassCount++;
					eachfilePasscount++;
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// function calculating percentage and returns the percentage value

	public static double percentage(int value) {
		return ((value / totalNoOfSteps) * 100);
	}

	// this function creating the headers in output file
	public static String createSheetWithHeaders(String consolidatedFilename,
			String files, String excelfilepath) {

		FileInputStream file;
		try {
			file = new FileInputStream(new File(returnFileName));
			HSSFWorkbook workBook = new HSSFWorkbook(file);
			HSSFSheet sheet = workBook.getSheetAt(0);
			CreationHelper createHelper = workBook.getCreationHelper();
			CellStyle hlink_style = workBook.createCellStyle();
			CellStyle cellStyle = workBook.createCellStyle();
			cellStyle.setWrapText(true);
			System.out.println(returnFileName);
			int rownum = sheet.getLastRowNum();
			HSSFRow row = sheet.createRow(rownum + 1);

			// HSSFRow row = sheet.createRow(rownum + 5);
			HSSFCell cell = row.createCell(0);
			cellStyle.setBorderTop((short) 1);
			cellStyle.setBorderBottom((short) 1);
			cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

			// Formating Cell Font
			HSSFFont font = workBook.createFont();
			font.setFontHeightInPoints((short) 10);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setColor(HSSFColor.BLACK.index);
			cellStyle.setFont(font);
			org.apache.poi.ss.usermodel.Hyperlink link = createHelper
					.createHyperlink(org.apache.poi.ss.usermodel.Hyperlink.LINK_FILE);
			link.setAddress(excelfilepath);
			HSSFRow TitleRow = sheet.createRow(rownum + 2);
			// HSSFRow TitleRow = sheet.createRow(rownum + 6);
			cell = TitleRow.createCell(1);
			cell.setHyperlink(link);
			Font hlink_font = workBook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			cell.setCellValue("Testcase Name : " + files + link);
			hlink_style.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			hlink_style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellValue("Testcase Name : " + files);
			cell.setCellStyle(hlink_style);
			cell = TitleRow.createCell(5);
			cell.setCellValue("Test start time : " + testStartTime);
			HSSFCellStyle resultStyle1 = workBook.createCellStyle();
			resultStyle1.setFillForegroundColor(HSSFColor.LIGHT_BLUE.index);
			resultStyle1.setFillPattern(CellStyle.SOLID_FOREGROUND);
			row = sheet.createRow(rownum + 4);
			// row = sheet.createRow(rownum + 8);
			cell.setCellStyle(resultStyle1);
			cell.setCellValue("Test start time : " + testStartTime);
			cell = row.createCell(1);
			cell.setCellValue("Step No:");
			cell.setCellStyle(cellStyle);
			cell = row.createCell(2);
			cell.setCellValue("Test Script Name");
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(2, 6000);
			cell = row.createCell(3);
			cell.setCellValue("Step Description");
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(3, 6000);
			cell = row.createCell(4);
			cell.setCellValue("Message");
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(4, 6000);
			cell = row.createCell(5);
			cell.setCellValue("Result (Pass/Fail)");
			cell.setCellStyle(cellStyle);
			cell = row.createCell(6);
			cell.setCellValue("Screenshot");
			cell.setCellStyle(cellStyle);
			cell = row.createCell(7);
			cell.setCellValue("Actual Result");
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(7, 6000);
			cell = row.createCell(8);
			cell.setCellValue("Expected Result");
			cell.setCellStyle(cellStyle);
			sheet.setColumnWidth(8, 6000);
			try {
				file.close();
				FileOutputStream outFile = new FileOutputStream(new File(
						returnFileName));
				workBook.write(outFile);
				outFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnFileName;
	}

	// this function is calculating current time stamp and convert into string
	// returns this time stamp
	public static String getcurrentDateTime() {
		// get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		Date date = new Date();
		DateFormat dateandTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(dateandTime.format(cal.getTime()));
		return dateandTime.format(date);
	}

	// this function insert the fail test data in the output file in required
	// columns
	public static void consolidatedInsertData(String excelSheetName,
			String stepNo, String moduleName, String stepDescription,
			String message, String status, HSSFHyperlink f1Val,
			String ActualValue, String ExpectedValue) {
		try {
			String result = "result not defined";
			FileInputStream file = new FileInputStream(new File(returnFileName));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0);
			CreationHelper createHelper = workbook.getCreationHelper();
			CellStyle hlink_style = workbook.createCellStyle();
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setWrapText(true);
			org.apache.poi.ss.usermodel.Hyperlink link = createHelper
					.createHyperlink(org.apache.poi.common.usermodel.Hyperlink.LINK_FILE);
			HSSFCell cell = null;
			HSSFRow row = null;

			// Update the value of cell
			int lastRow = sheet.getLastRowNum();
			row = sheet.createRow(++lastRow);
			cell = row.createCell(1);
			cell.setCellValue(stepNo);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(2);
			cell.setCellValue(moduleName);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(3);
			cell.setCellValue(stepDescription);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(4);
			cell.setCellValue(message);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(5);
			cell.setCellValue(status);

			// Formating Cell Style
			HSSFCellStyle resultStyle = workbook.createCellStyle();
			resultStyle.setFillForegroundColor(HSSFColor.ORANGE.index);
			resultStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
			cell.setCellValue(status);
			cell.setCellStyle(resultStyle);
			Font hlink_font = workbook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);

			// cell 6
			cell = row.createCell(6);
			cell.setCellValue("Screen Shot");
			cell.setCellStyle(hlink_style);
			cell.setHyperlink(f1Val);
			cell = row.createCell(7);
			cell.setCellValue(ActualValue);
			cell.setCellStyle(cellStyle);
			cell = row.createCell(8);
			cell.setCellValue(ExpectedValue);
			cell.setCellStyle(cellStyle);
			file.close();
			FileOutputStream outFile = new FileOutputStream(new File(
					returnFileName));
			workbook.write(outFile);
			outFile.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// this function create output file in specified location in path variable
	public static String createConsolidatedResultExecl() {
		String s2 = System.getProperty("user.dir");
		String path = s2 + "\\reports";
		testStartTime = getcurrentDateTime();
		returnFileName = path + "\\Consolidatedoutputfile.xls";
		System.out.println(returnFileName);
		HSSFWorkbook workBook = new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("Execution Report");
		try {
			// Only if a file is not existing , new file will be created
			File fileToWrite = new File(returnFileName);
			Path path1 = fileToWrite.toPath();
			if (Files.notExists(path1, LinkOption.NOFOLLOW_LINKS)) {
				FileOutputStream out = new FileOutputStream(new File(
						returnFileName));
				workBook.write(out);
				out.close();
				System.out.println("Excel written successfully..");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnFileName;
	}
}