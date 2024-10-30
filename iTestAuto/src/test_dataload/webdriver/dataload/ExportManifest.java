package dataload;

/**  Verify icargo requests for DG Details to Cafeed during FFM processing to get DG Details for the shipments  **/

import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;
import screens.CaptureAWB_OPR026;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptance_OPR335;



public class ExportManifest extends  BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ExportManifest_OPR344 OPR344;


	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName = "dataload";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
	
	}

	@DataProvider(name = "Web_e2e")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "Web_e2e")
	public void getTestSuite(Map<Object, Object> map) throws Exception {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}


			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// Login to iCargo
			String[] iCargo = libr.getApplicationParams("iCargoSTG");
				driver.get(iCargo[0]);
				Thread.sleep(2000);
				cust.loginICargoSTG(iCargo[1], iCargo[2]);
				Thread.sleep(2000);

			
			/**Switch role to Origin**/
			cust.switchRole("val~CDG", "val~CDG", "RoleGroup"); 
			cust.searchScreen("OPR344", "Export Manifest");
			
			int firstIndex=1;
			int awbIndex=1;
			int totalRows=cust.totalRowCount();
			//int totalRows=4;
			System.out.println(totalRows);
			
			  boolean awbAssigned =false;
			  boolean verifyAWBNo = false;	 
			
			
		  while(firstIndex!=(totalRows))
			  
		  {
			  System.out.println(firstIndex);
			  String uldNumber=cust.getCellValue(firstIndex,0);
			  System.out.println("uldNumber ----"+uldNumber);
			  String carrierCode=cust.getCellValue(firstIndex,10);
			  System.out.println("carrierCode ----"+carrierCode);
			  String flightNumber=cust.getCellValue(firstIndex,11);
			  System.out.println("flightNumber ----"+flightNumber);
			  String flightDate=cust.getCellValue(firstIndex,12);
			  System.out.println("flightDate ----"+flightDate);

			  String pou=cust.getCellValue(firstIndex,9);
			  String destination=cust.getCellValue(firstIndex,8);
			  String contour=cust.getCellValue(firstIndex,1);
			  String actualWeight=cust.getCellValue(firstIndex,3);
			  String location=cust.getCellValue(firstIndex,7);
			  if(!uldNumber.equals(""))
			  {
				  OPR344.dataload_editAndClear();
				  // List the flight details
				  OPR344.dataload_listFlight(carrierCode, flightNumber,flightDate);
				  System.out.println(uldNumber);
				  OPR344.dataload_addULDWithoutAWB(uldNumber, "0",destination,contour,actualWeight,location);
				  map.put("uldNo", uldNumber);

				  uldNumber=cust.getCellValue(firstIndex+awbIndex,0);
				
				  while(uldNumber.equals(""))
				  {
					  String awbNumber=cust.getCellValue((firstIndex+awbIndex),2);
					  String awbNo=awbNumber.substring(3);
					  
					  System.out.println(awbNo);
					  verifyAWBNo= OPR344.dataload_verifyShipmentInPlannedSection(awbNo);
					 
					 
					 if(verifyAWBNo)
					 {
					 
					  OPR344.dataload_clickShipemntFromPlannedSection(awbNo);
					  OPR344.dataload_selectULD(uldNumber);
					   awbAssigned =OPR344.dataload_verifyULDInAssignedShipment(libr.data("uldNo"));
					  if(!awbAssigned)
					  {
						  cust.setCellValue(firstIndex+awbIndex, 17, "FAILED - AWB not assigned to ULD");
					  }
					   
					 }
					 else
					 {
						 cust.setCellValue(firstIndex+awbIndex, 17, "FAILED - AWB not found in planned section");
					 }
					 
					 if(verifyAWBNo&& awbAssigned)
					 {
						 cust.setCellValue(firstIndex+awbIndex, 17, "PASSED"); 
					 }
					 
					  awbIndex=awbIndex+1;
					  uldNumber=cust.getCellValue(firstIndex+awbIndex,0);
					  System.out.println(uldNumber);
				  }

			  
			  
				  OPR344.dataload_clickBuildUpComplete();
				 
				 
				 firstIndex=awbIndex+1;
				 
				 
		  }
		  
		  
		  
		  }
		}
		  
		  
		 
		catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

		  
		  
	}
}
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  


		
	

