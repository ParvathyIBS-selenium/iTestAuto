package common;

//Author name: Shalini P
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;



public class Itest implements ITestListener {

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

		Reporter.log("****Testcase got started*****", true);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTestFailure(ITestResult result) {

		try {
			String st2 = System.getProperty("user.dir");
			String path2 = st2
					+ "\\src\\resources\\WriteTestcaseIpaddress.xlsx";
			File f1 = new File(path2);
			FileInputStream ios1 = new FileInputStream(f1);
			XSSFWorkbook workbook1 = new XSSFWorkbook(ios1);
			XSSFSheet sheet1 = workbook1.getSheet("Sheet1");

			Row row1 = sheet1.getRow(0);
			Cell cell1 = row1.getCell(0);
			cell1.setCellType(Cell.CELL_TYPE_STRING);
			String testCaseID = String.valueOf(cell1.getStringCellValue());
			Cell cell2 = row1.getCell(1);
			cell2.setCellType(Cell.CELL_TYPE_STRING);
			String ipAddressServer = String.valueOf(cell2.getStringCellValue());
			System.out.println(testCaseID + ipAddressServer);

			/*System.out.println(IndividualBookingCruiseOBS_DTS_1.testCaseID
					+ "*******The Failed test case is:");
*/
			String s2 = System.getProperty("user.dir");

			String path1 = s2 + "\\src\\resources\\AutomationReport.xlsx";

			File f = new File(path1);

			FileInputStream ios = new FileInputStream(f);
			XSSFWorkbook workbook = new XSSFWorkbook(ios);

			XSSFSheet sheet = workbook.getSheet(ipAddressServer);
			int rowCount = sheet.getLastRowNum();
			rowCount++;
			Row row = sheet.createRow(rowCount);
			Cell cell = row.createCell(0);
			cell.setCellValue(testCaseID);

			FileOutputStream out = new FileOutputStream(new File(path1));
			workbook.write(out);
			out.close();
			System.out.println("Report written successfully on disk.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		Reporter.log(
				"********testcase case********" + result.getName(),
				true);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

	}

}
