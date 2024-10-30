package stockmanagement;

import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.CaptureAWB_OPR026;
import screens.CreateStock_STK004;
import screens.ListMessages_MSG005;
import screens.MaintainBooking_CAP018;
import screens.MonitorStock_STK007;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

/** Processing FWB message with AWB number not from agent stock.  **/

//Verify the MIP code and reason in the description. is pending in FMA message veriifcation
public class TC016 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public CaptureAWB_OPR026 OPR026;
	public ListMessages_MSG005 MSG005;
	public MonitorStock_STK007 STK007;
	public CreateStock_STK004 STK004;
	public MaintainBooking_CAP018 CAP018;


	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="stockmanagement";	

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		STK007= new MonitorStock_STK007(driver, excelreadwrite, xls_Read);
		STK004=new CreateStock_STK004(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		CAP018 = new MaintainBooking_CAP018(driver, excelreadwrite, xls_Read);
	}



	@DataProvider(name = "TC016")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC016")
	public void getTestSuite(Map<Object, Object> map) throws InterruptedException {

		try {
			WebFunctions.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			/****** UPDATING FWB CUSTOMER DETAILS IN MAP***/


			map.put("ShipperName", WebFunctions.getPropertyValue(custproppath, "agent_1_name"));
			map.put("ShipperAddress", WebFunctions.getPropertyValue(custproppath, "agent_1_address"));
			map.put("ShipperCityName", WebFunctions.getPropertyValue(custproppath, "agent_1_city_name"));
			map.put("ShipperCountryName", WebFunctions.getPropertyValue(custproppath, "agent_1_country_code"));

			map.put("ConsigneeName", WebFunctions.getPropertyValue(custproppath, "agent_2_name"));
			map.put("ConsigneeAddress", WebFunctions.getPropertyValue(custproppath, "agent_2_address"));
			map.put("ConsigneeCityName", WebFunctions.getPropertyValue(custproppath, "agent_2_city"));
			map.put("ConsigneeCountryName", WebFunctions.getPropertyValue(custproppath, "agent_2_country_name"));
			map.put("ConsigneePhoneNo", WebFunctions.getPropertyValue(custproppath, "agent_2_telephone"));

			map.put("AgentName", WebFunctions.getPropertyValue(custproppath, "agent_1_name"));
			map.put("AgentCity", WebFunctions.getPropertyValue(custproppath, "agent_1_city_name"));
			map.put("IATACode", WebFunctions.getPropertyValue(custproppath, "agent_1_iata_code"));
			map.put("CassCode", WebFunctions.getPropertyValue(custproppath, "agent_1_cass_code"));

			map.put("Currency", WebFunctions.getPropertyValue(custproppath, "agent_1_currency"));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			
		

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargo(iCargo[1], iCargo[2], iCargo[3]);
			Thread.sleep(2000);

		

			//Creating Fresh AWB 

			cust.searchScreen("CAP018", "Maintain Booking");
			CAP018.fetchAWBNotInStock_CAP018("Maintain Booking", "CAP018","AWBNo");

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("CarrierNumericCode") + "-" + cust.data("prop~AWBNo"),proppath);
			map.put("FullAWBNo", cust.data("prop~FullAWBNo"));
			map.put("AWBNo", cust.data("prop~AWBNo"));
			excelRead.writeDataInExcel(map, path1, sheetName, testName);


			//Load FWB from MSG005
			cust.createTextMessage("MessageExcelAndSheetFWB", "MessageParamFWB");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFileWithStatusCheck("All", "ALL", "JMS", "", "Origin", "", "FWB_AWB1","processed with errors.");
			cust.closeTab("MSG005", "MSG005 - List Messages");

			/*** MSG005-verify error remark and log verification ***/
			
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("FWB");
			MSG005.clickList();
			String pmKeyFWB = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo");
			int verfColsFWB[] = { 9,11 };
			String[] actVerfValuesFWB = { "Processed  With Errors","AWB not present in the stock of agent" };
			MSG005.verifyMessageDetails(verfColsFWB, actVerfValuesFWB, pmKeyFWB, "val~FWB", false);
			
			MSG005.clickViewlogs(cust.data("AWBNo"));
			String[] MessageProfiles= { cust.data("MessageProfile") };
            MSG005.VerifyHandlingCode(MessageProfiles);
			MSG005.closeViewlogs();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			/*** MSG005-verify FNA message triggered ***/

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("FNA");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFNA = cust.data("prop~CarrierNumericCode") + " - " + cust.data("AWBNo");
			int verfColsFNA[] = { 9 };
			String[] actVerfValuesFNA = { "Sent"};
			MSG005.verifyMessageDetails(verfColsFNA, actVerfValuesFNA, pmKeyFNA, "val~FNA", false);

			/*** Verify the MIP code and reason in the description***/
			map.put("pmkey", pmKeyFNA);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			MSG005.verifyErrorMIPDescription("val~AWB not present in the stock of agent");
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			//verify FWB details not displayed in OPR026
			cust.searchScreen("OPR026", "Capture AWB");
			OPR026.verifyAWBdetailsNotDisplayed("AWBNo","CarrierNumericCode");
			cust.closeTab("OPR026", "Capture AWB");
		}	
		catch(Exception e)
		{

			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}



	}
}

