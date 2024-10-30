/* Description : This class works with excel file which is used to give test data.  The class file 
 * have one function relating to data read from excel.  The function is getTableArray which is used
 * get data from excel table.
 * Author :
 * Comments Author: Raghothama
 * Date created : 04-Jun-2014
 * Modification comments:endCols and endRows is given to the application for parsing the data.
 * The data which are beyond endCol and endRow will not be fetched and testcase either gets failed or skipped.		
 * Modified by : Arjun B M
 * Modified on :  
 */
package controls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

 
public class ExcelRead {
	
	static DataFormatter formatter = new DataFormatter();

	public String[][] getTableArray3(String xlFilePath, String sheetName,
			String tableName) throws Exception {

		String[][] tabArray = null;

		Workbook workbook = Workbook.getWorkbook(new File(xlFilePath));
		Sheet sheet = workbook.getSheet(sheetName);
		int startRow, startCol, endRow, endCol, ci, cj;
		Cell tableStart = sheet.findCell(tableName);
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();
		int endCols = sheet.getColumns();
		int endRows = sheet.getRows();
		System.out.println("startRow :" + startRow + "startCol :" + startCol);
		/*
		 * endCols and endRows is given to the application for parsing the data.
		 * The data which are beyond endCol and endRow will not be fetched and
		 * testcase either gets failed or skipped.
		 */
		Cell tableEnd1 = sheet.findCell(tableName, startCol, startRow, endCols,
				endRows, true);

		endRow = tableEnd1.getRow();
		endCol = tableEnd1.getColumn();
		System.out.println("startRow=" + startRow + ", endRow=" + endRow + ", "
				+ "startCol=" + startCol + ", endCol=" + endCol);

		tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		// System.out.println("tableEnd :"+tableEnd1);
		ci = 0;

		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;
			for (int j = startCol + 1; j < endCol; j++, cj++) {
				tabArray[ci][cj] = sheet.getCell(j, i).getContents();
			}
		}
		for (int i = 0; i < tabArray.length; i++) {
			for (int j = 0; j < tabArray[i].length; j++) {

			}
			System.out.println("");
		}

		return (tabArray);

	}
public Object[][] getMapArray(String xlFilePath, String sheetName,
                                                                String tableName) throws IOException {
                                   HashMap<String, String> hmap = new HashMap<>();
                                   Object[][] obj = new Object[1][1];
                                   FileInputStream fileInputStream= new FileInputStream(xlFilePath);
                                   try{
                                   //Excel sheet file location get mentioned here
                        HSSFWorkbook workbook = new HSSFWorkbook (fileInputStream); //get  workbook 
                        HSSFSheet worksheet=workbook.getSheet(sheetName);// get sheet from workbook
                          
                        
                          int rowNum = findRow(worksheet,tableName);
                          HSSFRow row=worksheet.getRow(rowNum);     //get Row of TC
                          HSSFRow rowHeader=worksheet.getRow(0);   //get header Row 
                          System.out.println("row num is---" + rowNum);


                         
                          
                          int col_Num = -1;
                         
                          for(int i = 1; i < rowHeader.getLastCellNum(); i++)
                          {
                               HSSFCell cell = rowHeader.getCell(i);
                               String key = formatter.formatCellValue(cell);
                               System.out.println("cell val is" + key);
                                 col_Num = i;
                              System.out.println("col no is" + col_Num);
                              HSSFCell cell2 = row.getCell(col_Num);
                              String value = formatter.formatCellValue(cell2);
                              System.out.println("cell val is--" + value);
                              hmap.put(key,value);
                             
                              
                          }
                          hmap.remove("");
                           obj[0][0] = hmap;
                              System.out.println("Obj val is---" + obj[0][0]);
                                                                  
                      }
                                   
                      catch(Exception e)
                      {
                                  System.out.println("Failed in reading data from TestData file");
                      }
                                   finally
                                   {
                                                   fileInputStream.close();
                                   }
                         return (obj);     
                    }

	
    public static int findRow(HSSFSheet sheet, String TCName){
    	int rowIndex=-1;
        for (Row row : sheet) {
	        for (org.apache.poi.ss.usermodel.Cell cell : row) {
	            if (cell.getCellType() == org.apache.poi.ss.usermodel.Cell.CELL_TYPE_STRING) {
	                if (cell.getRichStringCellValue().getString().trim().equals(TCName)) {
	                    rowIndex = row.getRowNum();  
	                    return rowIndex;
	                }
	            }
	        }
	    }
		return 1; 


    	
    }
    
    
   public void writeDataInExcel(Map map, String xlFilePath, String sheetName,
                                                String tableName) throws Exception {    
                FileInputStream fileInputStream= new FileInputStream(xlFilePath);
                try{
                                List<String> keys = new ArrayList(map.keySet());
                                List<String> values = new ArrayList(map.values());
                                   //Excel sheet file location get mentioned here
                        HSSFWorkbook workbook = new HSSFWorkbook (fileInputStream); //get my workbook 
                        HSSFSheet worksheet=workbook.getSheet(sheetName);
                                  
                     int rowNum = findRow(worksheet,tableName);
                      HSSFRow row = worksheet.getRow(rowNum);     
             HSSFRow rowHeader=worksheet.getRow(0); 
           
            for(int k=0;k<keys.size();k++){
             for(int i = 1; i < rowHeader.getLastCellNum(); i++)
             {
                  HSSFCell cell = rowHeader.getCell(i);
                  String colName = formatter.formatCellValue(cell);
                  if(keys.get(k).equalsIgnoreCase(colName)) {
                                  setCellValue(xlFilePath, sheetName, rowNum,
                                                                                i, values.get(k));
                                  
                 // System.out.println("cell val is" + colName);
                  
                  }
                 
             }
                
                }
    }
                catch(Exception e){
                                e.printStackTrace();
                }
                finally
                {
                                fileInputStream.close();
                }
                }
  
	
	
  

	public void setCellValue(String xlFilePath, String sheetName, int rowNum,
			int columnNum, String columnValue) {
		try {

			
			FileInputStream jis = new FileInputStream(xlFilePath);
			org.apache.poi.ss.usermodel.Workbook wb = WorkbookFactory
					.create(jis);
			org.apache.poi.ss.usermodel.Sheet s = wb.getSheet(sheetName);
			s.getRow(rowNum).createCell(columnNum).setCellValue(columnValue);
			FileOutputStream out = new FileOutputStream(new File(xlFilePath));
			wb.write(out);
			jis.close();
		} catch (Exception ex) {
			System.out.println(ex);

		}
	}
}
