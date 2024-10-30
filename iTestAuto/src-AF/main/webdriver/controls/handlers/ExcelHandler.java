/* Description : This class works with Excel file for various operations.  The class file have 
 * different functions relating to excel operation.  There are different functions like
 * readDataFromExcel, getCellValue, numberOfRowsinSheet(with name), numberOfRowsinSheet(with Index), 
 * numberOfColumnsinSheet(with name), numberOfColumnsinSheet(with Index), closeWorkbook
 * Author :
 * Comments Author: Raghothama
 * Date created : 05-Jun-2014
 * Modification comments:
 * Modified by :
 * Modified on :   
 */

package controls.handlers;

import java.io.File;
import java.io.IOException;

import jxl.*;
import jxl.read.biff.BiffException;

import org.testng.Reporter;

import controls.exceptions.TestDataNotFoundException;

public class ExcelHandler {
	private Workbook workbook;
	private String testDataPath;

	public ExcelHandler(String excelWorkbookName) {
		try {
			String path = System.getProperty("user.dir");
			testDataPath = path + "/src/resources/"+excelWorkbookName;
			this.workbook = Workbook.getWorkbook(new File(testDataPath));
			System.out.println("Opened workbook: " + testDataPath);
		} catch (BiffException e) {
			Reporter.log("Exception in opening Excel file: "
					+ testDataPath);
		} catch (IOException e) {
			Reporter.log("Exception in opening Excel file: "
					+ testDataPath);
		}
	}
	
	/**
	 * This method reads the test data from an Excel workbook
	 * @param sTestDataIdentifier - the string that identifies the location on the test data. Example: TESTDATA.LOGIN.UserName 
	 * @param intRowIndex - the index of the row of the Excel sheet from which the data is to be read
	 * @return value - the contents of the Excel cell
	 */
	public String readDataFromExcel(String sTestDataIdentifier, int intRowIndex){
		String value = null;
		if(intRowIndex<1){
			Reporter.log("Invalid row index");
			throw new TestDataNotFoundException("Invalid row index");
		}
		String[] splitTestDataIdentifierIntoArray = sTestDataIdentifier.split("\\.");
		if (splitTestDataIdentifierIntoArray.length == 3){
			//The second part of the sTestDataIdentifier parameter is the sheet name (xxxx.LOGIN.yyyy)
			String sheetname = splitTestDataIdentifierIntoArray[1];
			Sheet sheet = workbook.getSheet(sheetname);
			if(sheet!=null){
				//The thrid part of the sTestDataIdentifier parameter is the column name (xxxx.yyyy.UserName)
				Cell columnNameCell = sheet.findCell(splitTestDataIdentifierIntoArray[2]);
				int columnIndex = 0;
				if(columnNameCell!=null){
					columnIndex= columnNameCell.getColumn();
					value = getCellValue(sheetname,intRowIndex,columnIndex);	
				}else{   //Handle incorrect cell name over here
					Reporter.log("No such cell in Excel workbook");
					throw new TestDataNotFoundException("No such cell in Excel workbook");
				}
			}else{   //Handle incorrect sheet name over here
				Reporter.log("No such sheet name in Excel workbook");
				throw new TestDataNotFoundException("No such sheet name in Excel workbook");
			}
		}else {   //Handle incorrect format of the parameter 'sTestDataIdentifier' over here
			Reporter.log("The format of parameter 'sTestDataIdentifier' provided to this method is incorrect");
			throw new TestDataNotFoundException("The format of parameter 'testDataName' provided to this method is incorrect");		
		}
		return value;
	}
	
	public String getCellValue(String sheetName, int rowIndex, int columnIndex) {
		// Open the specific sheet
		Sheet sheet = workbook.getSheet(sheetName);
	
		if(numberOfRowsinSheet(sheetName)<(rowIndex-1)){
			Reporter.log("No data in this row");
			throw new TestDataNotFoundException("No data in this row");
		}
		// Read data from specific cell
		Cell cell = sheet.getCell(columnIndex, rowIndex);
		String value = cell.getContents();

		return value;
	}

	public int numberOfRowsinSheet(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getRows();
	}

	public int numberOfRowsinSheet(int sheetIndex) {
		Sheet sheet = workbook.getSheet(sheetIndex);
		return sheet.getRows();
	}

	public int numberOfColumnsinSheet(String sheetName) {
		Sheet sheet = workbook.getSheet(sheetName);
		return sheet.getColumns();
	}

	public int numberOfColumnsinSheet(int sheetIndex) {
		Sheet sheet = workbook.getSheet(sheetIndex);
		return sheet.getColumns();
	}

	public void closeWorkbook() {
		workbook.close();
	}

}
