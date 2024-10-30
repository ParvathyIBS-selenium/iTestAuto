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
import screens.GoodsAcceptance_OPR335;


public class Acceptance extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public GoodsAcceptance_OPR335 OPR335;


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
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
	
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
			//cust.switchRole("val~CDG", "val~CDG", "RoleGroup"); 
			cust.searchScreen("OPR335", "Goods Acceptance");
			
			int firstIndex=1;
			//int totalRows=cust.totalRowCount();
			int totalRows=4;
			System.out.println(totalRows);
			
			
		  while(firstIndex!=(totalRows))
			  
		  {
			  System.out.println(firstIndex);
			  String awbNumber=cust.getCellValue(firstIndex,0);
			  System.out.println("awb Number is ----"+awbNumber);
			  
				  OPR335.dataload_listAWB(awbNumber, "Goods Acceptance");
				  
				  String pcs=cust.getCellValue(firstIndex,5);
				  String weight=cust.getCellValue(firstIndex,4);
				  String volume=cust.getCellValue(firstIndex,6);
				  String location=cust.getCellValue(firstIndex,21);
				  String isTransit=cust.getCellValue(firstIndex,7);
				  //String carrierCode=cust.getCellValue(firstIndex,9);
				  String carrierCode="AA";

				  boolean isAWBNotAccepted=true;
				  String isULD=cust.getCellValue(firstIndex,8);
				  boolean isAWBCaptured =OPR335.isAWBCaptured(firstIndex,22,awbNumber);
				  
				  if(isAWBCaptured)
				  {
					  isAWBNotAccepted=OPR335.isAWBNotAccepted(firstIndex,22,awbNumber);
				  }


				  if(isAWBCaptured && isAWBNotAccepted)
				  {
					  if(isULD.equals("N"))
					  {
						  System.out.println("enter loose acceptance");
						  System.out.println("pcs ----"+pcs);
						  System.out.println("weight ----"+weight);
						  System.out.println("volume ----"+volume);
						  System.out.println("location ----"+location);

						  OPR335.dataload_looseShipmentDetails(pcs, weight,volume,location);
						  OPR335.dataload_addLooseShipment(firstIndex,23);

						  if(isTransit.equals("Y"))
						  {
							  OPR335.dataload_provideCTMdetails(carrierCode);
						  }
					  }
					  else
					  {
						  String uldNumber=cust.getCellValue(firstIndex,3);
						  System.out.println("pcs ----"+pcs);
						  System.out.println("weight ----"+weight);
						  System.out.println("volume ----"+volume);
						  System.out.println("location ----"+location);
						  System.out.println("uldNumber ----"+uldNumber);
						  System.out.println("enter ULD acceptance acceptance");

						  OPR335.dataload_uldShipmentDetails(pcs, weight,volume, location, uldNumber);
						  OPR335.addULDDetails();
						  cust.setCellValue(firstIndex, 23,uldNumber);

						  if(isTransit.equals("Y"))
						  {
							  OPR335.dataload_provideCTMdetails(carrierCode);
						  }
					  }


					  System.out.println(cust.getCellValue(firstIndex,0));
					  System.out.println(cust.getCellValue(firstIndex+1,0));




					  while(!cust.getCellValue(firstIndex,0).equals(cust.getCellValue(firstIndex+1,0)))
					  {
						  // OPR335.allPartsRecieved();
						  OPR335.dataload_saveAcceptance(awbNumber,firstIndex,22);
						  OPR335.dataload_clear();
						  break;

					  }
				  }
					  
				  else
				  {
					  OPR335.dataload_clear();
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
	