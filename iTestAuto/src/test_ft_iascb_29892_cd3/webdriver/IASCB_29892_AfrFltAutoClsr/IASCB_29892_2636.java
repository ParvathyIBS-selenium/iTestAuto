package IASCB_29892_AfrFltAutoClsr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
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
import screens.BuildUpHHT;
import screens.BuildupPlanning_ADD004;
import screens.CaptureAWB_OPR026;
import screens.CaptureIrregularity_OPR342;
import screens.Cgocxml;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.Mercury;
import screens.SecurityAndScreening_OPR339;

/**Test ID : 2636 - Triggering FSU-DIS for Shipments pending for Built-up.**/

public class IASCB_29892_2636 extends BaseSetup {

	int counter = 0;
	public ExcelRead excelRead;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;

	String path1 = System.getProperty("user.dir") + "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	public static String telexproppath = "\\src\\resources\\TelexAddress.properties";

	String sheetName = "BeforeFlightAutoClosure";

	@BeforeClass
	public void setup() {

		testName = getTestName();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);




	}

	@DataProvider(name = "TC_2636")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_2636")
	public void getTestSuite(Map<Object, Object> map) {

		try {
			WebFunctions.map = map;
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			//Login to iCargo

			String [] iCargo=libr.getApplicationParams("iCargoSTG");	
			driver.get(iCargo[0]);
			Thread.sleep(2000);
			cust.loginICargoSTG(iCargo[1], iCargo[2]);
			Thread.sleep(2300);	

			/**Switch role to Origin**/
			cust.switchRole("Origin", "Origin", "RoleGroup");	

			/******* Verify xFSU-DIS message in MSG005 ******/

			//Verifying Discrepancy stamped against Not Built up shipments which is having Irregularity.

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo1");
			int verfColsFSU[] = { 9 };
			String[] actVerfValuesFSU = { "Sent" };
			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU, "val~XFSU-DIS", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");


			/******* Verify xFSU-DIS message in MSG005 ******/

			//Verifying Discrepancy stamped against Not Built up shipments which is not having Irregularity.

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyFSU1 = cust.data("CarrierNumericCode") + " - " + cust.data("AWBNo3");

			MSG005.verifyMessageDetails(verfColsFSU, actVerfValuesFSU, pmKeyFSU1, "val~XFSU-DIS", false);
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");




			/******* Verify xFSU-DIS message in MSG005 ******/

			//Verifying Discrepancy not stamped against  Built up shipments which is Not having Irregularity.

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo","AWBNo2");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyErrorMessage("MSG005", "val~No results found for the specified criteria.");
			libr.waitForSync(1);
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**Verifying Message Contents **/ 

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo","AWBNo1");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyIfMessageTriggered("<ID>"+cust.data("FullAWBNo1")+"</ID>","XFSU","MsgRef");
			MSG005.clickCheckBox("MsgRef");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			/**Verifying OFLD Discrepancy code**/
			msgContents.add("val~<DiscrepancyDescriptionCode>OFLD</DiscrepancyDescriptionCode>");
			/** Verifying OFLD Discrepancy Desc**/
			msgContents.add("val~<Description>Warehouse Offload. Will not make the planned flight.</Description>");
			MSG005.verifyMessageContent(msgContents,"XFSU",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

			/**Verifying Message Contents **/ 

			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFSU");
			MSG005.selectMsgSubType("Discrepancy");
			MSG005.clickReference();
			MSG005.enterReferenceValue("FSU", "FlightNo","AWBNo3");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			MSG005.verifyIfMessageTriggered("<ID>"+cust.data("FullAWBNo3")+"</ID>","XFSU","MsgRef");
			MSG005.clickCheckBox("MsgRef");
			MSG005.clickView();
			MSG005.verifyMessageContent(msgContents,"XFSU",true);
			MSG005.closeView();
			MSG005.closeTab("MSG005", "MSG005 - List Messages");

		} catch (Exception e) {
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
}
