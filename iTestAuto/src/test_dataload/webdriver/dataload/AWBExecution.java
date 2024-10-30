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



public class AWBExecution extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public Cgocxml Cgocxml;
	public CaptureAWB_OPR026 OPR026;


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
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		Cgocxml = new Cgocxml(driver, excelreadwrite, xls_Read);
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





//
//			/*** Login to cgomon **********/
//			String[] cgocxml = libr.getApplicationParams("cgocxml");
//			driver.get(cgocxml[0]); // Enters URL
//			cust.loginToCgocxml(cgocxml[1], cgocxml[2]);
//
//			/**Message loading in EML**/
//
//			int totalFiles=cust.totalFilesInDirectory(System.getProperty("user.dir")+"\\src\\resources\\TestData\\DataLoad\\xfwb\\");
//			System.out.println(totalFiles);
//			Cgocxml.sendMessage("ICARGO");
//
//			for(int i=1;i<=totalFiles;i++)
//			{
//				Cgocxml.loadMessage(System.getProperty("user.dir")+"\\src\\resources\\TestData\\DataLoad\\xfwb\\File_"+i+".txt");
//			}
//			libr.quitBrowser();


			// Relaunch browser
			//driver = libr.relaunchBrowser("chrome");

			/*** Login to icargo **********/
			String[] iCargo = libr.getApplicationParams("iCargoSTG");

			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2000);


			/**Switch role to Origin**/
			//cust.switchRole("val~AMS", "val~AMS", "RoleGroup"); 
			cust.searchScreen("OPR026", "Goods Acceptance");

			int firstIndex=6;
			//int totalRows=cust.totalRowCount();
			int totalRows=20;

			System.out.println(totalRows);


			while(firstIndex!=(totalRows))

			{
				System.out.println(firstIndex);
				String awbNumber=cust.getCellValue(firstIndex,0);
				System.out.println("awb Number is ----"+awbNumber);

				OPR026.dataload_listAWB(awbNumber);
				boolean isSourceFWB=OPR026.dataload_verifySource(awbNumber,firstIndex, 2);
				/*if(!isSourceFWB)
					OPR026.setCellValue(firstIndex, 2, "Source of the AWB is not FWB");*/

				boolean isnonDGRSCC=OPR026.dataload_verifySCC(awbNumber);
				if(!isnonDGRSCC)
					OPR026.setCellValue(firstIndex, 2, "DGR Shipment");


				if(isSourceFWB && isnonDGRSCC)
				{
					OPR026.dataload_asIsExecute(awbNumber,firstIndex, 2);
				}

				firstIndex=firstIndex+1;
			}


		} 
		catch (Exception e) {
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}
