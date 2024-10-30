package mvpcrs;

import java.util.Map;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import screens.AWBClearance_OPR023;
import screens.AutoBlockSetUp_OPR031;
import screens.BuildUpHHT;
import screens.CaptureAWB_OPR026;
import screens.CaptureConsumablesHHT;
import screens.DamageCaptureHHT;
import screens.DeadloadStatement_OPR063;
import screens.ExportManifest_OPR344;
import screens.GoodsAcceptanceHHT;
import screens.GoodsAcceptance_OPR335;
import screens.ImportManifest_OPR367;
import screens.ListMessages_MSG005;
import screens.MaintainFlightSchedule_FLT005;
import screens.MaintainOperationalFlight_FLT003;
import screens.MarkFlightMovements_FLT006;
import screens.OffloadHHT;
import screens.SecurityAndScreeningHHT;
import screens.SecurityAndScreening_OPR339;
import screens.ULDTag_OPR013;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

import controls.ExcelRead;

//Capture Split Shipment Function for Multi line Part Shipment(Dims and ULD)
public class IASCB_51706_Acceptance_TC04 extends BaseSetup {
	
	int counter = 0;
	public ExcelRead excelRead;
	public Excel excel;
	public ExcelReadWrite excelreadwrite;
	public CommonUtility commonUtility;
	String currentTestName;
	Xls_Read xls_Read;
	public WebFunctions libr;
	public CustomFunctions cust;
	public ListMessages_MSG005 MSG005;
	public CaptureAWB_OPR026 OPR026;
	public BuildUpHHT buhht;
	public GoodsAcceptanceHHT gahht;
	public CaptureConsumablesHHT cchht;
	public DeadloadStatement_OPR063 OPR063;
	public OffloadHHT offloadhht;
	public ULDTag_OPR013 OPR013;
	public DamageCaptureHHT dchht;
	public SecurityAndScreening_OPR339 OPR339;
	public AWBClearance_OPR023 OPR023;
	public SecurityAndScreeningHHT sechht;
	public ExportManifest_OPR344 OPR344;
	public MarkFlightMovements_FLT006 FLT006;
	public ImportManifest_OPR367 OPR367;
	public GoodsAcceptance_OPR335 OPR335;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	String sheetName="mvpcrs";	
	
	@BeforeClass
	public void setup() {
		
		testName = getTestName();
		//excel=new Excel();
		excelRead = new ExcelRead();
		commonUtility = new CommonUtility();
		excelreadwrite = new ExcelReadWrite(testName, driver, getBrowser(), getScrenshotfilepath());
		xls_Read = new Xls_Read(null, xpathFilePath);
		libr = new WebFunctions(driver, excelreadwrite, xls_Read);
		cust = new CustomFunctions(driver, excelreadwrite, xls_Read);
		MSG005=new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026=new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		buhht=new BuildUpHHT(driver, excelreadwrite, xls_Read);
		gahht = new GoodsAcceptanceHHT(driver, excelreadwrite, xls_Read);
		cchht=new CaptureConsumablesHHT(driver, excelreadwrite, xls_Read);
		OPR063=new DeadloadStatement_OPR063(driver, excelreadwrite, xls_Read);
		OPR013=new ULDTag_OPR013(driver, excelreadwrite, xls_Read);
		offloadhht = new OffloadHHT(driver, excelreadwrite, xls_Read);
		OPR339 = new SecurityAndScreening_OPR339(driver, excelreadwrite, xls_Read);
		dchht = new DamageCaptureHHT(driver, excelreadwrite, xls_Read);
		OPR023=new AWBClearance_OPR023(driver, excelreadwrite, xls_Read);
		sechht=new SecurityAndScreeningHHT(driver, excelreadwrite, xls_Read);
		OPR344 = new ExportManifest_OPR344(driver, excelreadwrite, xls_Read);
		FLT006 = new MarkFlightMovements_FLT006(driver, excelreadwrite, xls_Read);
		OPR367 = new ImportManifest_OPR367(driver, excelreadwrite, xls_Read);
		OPR335 = new GoodsAcceptance_OPR335(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "IASCB_31368_TC03")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}
	

	@Test(dataProvider = "IASCB_31368_TC03")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);

			// Login to "ICARGO"
			String[] iCargo = libr.getApplicationParams("iCargo");
			driver.get(iCargo[0]); // Enters URL
			cust.loginICargo(iCargo[1], iCargo[2]);

			// creating flight number
			cust.createFlight("FullFlightNumber");
			cust.createFlight("FullFlightNumber2");
			String startDate = cust.createDateFormat("dd-MMM-YYYY", 0, "DAY", "");
			String endDate = cust.createDateFormat("dd-MMM-YYYY", 7, "DAY", "");
			String FlightNum = cust.getPropertyValue(proppath, "flightNumber");
			String FlightNum2= cust.getPropertyValue(proppath, "flightNumber2");
			FlightNum=FlightNum.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			FlightNum2=FlightNum2.replace(cust.data("prop~flight_code"),cust.data("carrierCode"));
			map.put("FullFlightNo", FlightNum);	
			map.put("FullFlightNo2", FlightNum2);	
			map.put("FlightNo", FlightNum.substring(2));
			map.put("FlightNo2", FlightNum2.substring(2));
			map.put("StartDate", startDate);
			map.put("EndDate", endDate);
			map.put("FBLDate", cust.createDateFormat("ddMMM", 0, "DAY", ""));
			map.put("Day", cust.createDateFormat("dd", 0, "DAY", ""));
			map.put("Month", cust.createDateFormat("MMM", 0, "DAY", ""));
			map.put("FWBDate", cust.createDateFormat("ddMMMyy", 0, "DAY", "").toUpperCase());
			String flightdate1 = cust.createDateFormat("yyyy-MM-dd", 0, "DAY", "");
			map.put("XFWBDate", flightdate1);
			System.out.println(FlightNum);
			
			//Switch role
			cust.switchRole("Origin", "Destination", "RoleGroup");
			
			/***MESSAGE - loading ASM - Flight 1***/
			
			cust.createTextMessage("MessageExcelAndSheetASM", "MessageParamASM");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "ASM_NEW");
			
			//Process ASM message
			
			MSG005.enterMsgType("ASM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", "NEW"+" - "+cust.data("carrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("FBLDate").toUpperCase());
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			/***MESSAGE - loading ASM - Flight 2***/
			
			cust.createTextMessage("MessageExcelAndSheetASM2", "MessageParamASM2");
			//Load ASM message
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "ASM_NEW");
			
			//Process ASM message
			
			MSG005.enterMsgType("ASM");
			MSG005.clickList();
			libr.waitForSync(6);
			map.put("pmkey", "NEW"+" - "+cust.data("carrierCode")+" - "+cust.data("prop~flightNo2")+" - "+cust.data("FBLDate").toUpperCase());
			MSG005.clickCheckBox("pmkey");
			MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
						
			
			/******MSG005-loading FBL - for flight 1***/
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);
			cust.setPropertyValue("FullAWBNo", cust.data("prop~CarrierNumericCode")+"-"+cust.data("prop~AWBNo"), proppath);
			
			map.put("FullFlightNo", FlightNum);
			map.put("Pcs", cust.data("Pieces1"));
			map.put("Wgt", cust.data("Weight1"));
			map.put("Vol", cust.data("Volume1"));
			//Create the message FBL
			
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "FBL_Dimentions");

			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);


			map.put("pmkey", cust.data("carrierCode")+" - "+cust.data("FlightNo")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
				MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			/******MSG005-loading FBL - for flight 2***/
			
			
			map.put("FullFlightNo", FlightNum2);
			map.put("Pcs", cust.data("Pieces2"));
			map.put("Wgt", cust.data("Weight2"));
			map.put("Vol", cust.data("Volume2"));
			map.put("ShipmentDesc", "MACGESTCAPSULES");
			map.put("CommodityCode", "MACGEST");
			//Create the message FBL
			
			cust.createTextMessage("MessageExcelAndSheet", "MessageParam");
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.loadFromFile("All","ALL", "JMS", "", "Origin", "", "FBL_Dimentions");

			//Process the message

			MSG005.enterMsgType("FBL");
			MSG005.clickList();
			libr.waitForSync(6);


			map.put("pmkey", cust.data("carrierCode")+" - "+cust.data("FlightNo2")+" - "+cust.data("Day")+" - "+cust.data("Month").toUpperCase()
					+" - "+cust.data("Origin"));
			MSG005.clickCheckBox("pmkey");
				MSG005.clickprocess();
			cust.closeTab("MSG005", "List Message");
			
			/******MSG005-loading XFWB***/
			
			//PER,BUP
            
            map.put("Pcs", cust.data("Pieces"));
            map.put("Wgt", cust.data("Weight"));
            map.put("Vol", cust.data("Volume"));
            map.put("CommCode", "PERISHABLES");
            map.put("FullFlightNo", FlightNum);
            cust.createXMLMessage("MessageExcelAndSheetFWB", "MessageParamFWB");

            //Create XFWB with multi line shipments
            String sccs[]={cust.data("SCC").split(",")[0],cust.data("SCC").split(",")[1]};
            String flightDetails1 = cust.data("FullFlightNo")+";"+cust.data("Origin")+";"+cust.data("Destination");
            String flightDetails2 = cust.data("FullFlightNo2")+";"+cust.data("Origin")+";"+cust.data("Destination");
            String fltDetails[]={flightDetails1,flightDetails2};
            String shipmentDetails1=cust.data("Pieces2")+";"+cust.data("Weight2")+";"+cust.data("Volume2")+";"+cust.data("val~MACGEST CAPSULES");
            String shipmentInfo[]={shipmentDetails1};
            cust.createXFWBMutliLineShipment("XFWB_MultiLineShipments", sccs,fltDetails,shipmentInfo);

            
            //Load FWB message
            cust.searchScreen("MSG005", "MSG005 - List Messages");
            MSG005.loadFromFile("All","ALL", "MQ-SERIES", "", "Origin", "", "XFWB_MultiLineShipments",true);
            cust.closeTab("MSG005", "List Message");

            /********OPR026 - Capture AWB********/
            //Split SCC
            cust.searchScreen("OPR026","Capture AWB");
            OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");
           
            String pcs2[]={libr.data("SplitPcs").split(",")[0],libr.data("SplitPcs").split(",")[1]};
            OPR026.splitShipmentWithSCC(libr.data("SCC"),pcs2);	
            OPR026.asIsExecute();
            cust.closeTab("OPR026", "Capture AWB");
			
            
			/****OPR355 - Goods Acceptance****/
			
			//Goods acceptance
			cust.searchScreen("OPR335", "Goods Acceptance");
			cust.listAWB("AWBNo", "prop~CarrierNumericCode", "Goods Acceptance");
			OPR335.clickLooseAccptTab();
			String locs[]={libr.data("Location2").split(",")[0],libr.data("Location2").split(",")[1]};
			OPR335.editShipmentLocation(pcs2[0],sccs[0],locs[0]);
			OPR335.editShipmentLocation(pcs2[1],sccs[1],locs[1]);
			OPR335.verifySUNotNull(cust.data("SCC").split(",")[1]);
			OPR335.verifySUNotNull(cust.data("SCC").split(",")[2]);
			OPR335.allPartsRecieved();
			OPR335.saveAcceptance();
			cust.closeTab("OPR335", "Goods Acceptance");
            
            /** CHECKING FSU-RCS TRIGGERED FOR AWB **/
            
		   /* cust.searchScreen("MSG005", "MSG005 - List Messages");
		 	MSG005.enterMsgType("FSU");
		 	MSG005.selectMsgSubType("Acceptance");
		 	MSG005.clickList();
		 	String pmKeyRCS1=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo");
			int verfColsRCS[]={9};
			String[] actVerfValuesRCS={"Sent"};
			MSG005.verifyMessageDetails(verfColsRCS, actVerfValuesRCS, pmKeyRCS1,"val~FSU-RCS",false);
            libr.waitForSync(2); 
            MSG005.closeTab("MSG005", "MSG005 - List Messages");*/
            
            //Relocation Task Manager :: needs to be modified once configuration is done
			
           /* WHS052.searchScreen("WHS052", "Relocation Task Monitor");
			WHS052.enterAWB("prop~CarrierNumericCode", "prop~AWBNo");
			WHS052.listAwbDetails();
			WHS052.expandAWB();
            //Verification of activation time and updated pieces
            int verfCols2[]={1,7,8};
            String[] actVerfValues3={cust.data("StartDate"),"",cust.data("Pieces")};
            WHS052.verifyMessageDetails(verfCols2, actVerfValues3);
            cust.closeTab("WHS052", "Relocation Task Monitor");*/
			
		}	
		catch(Exception e)
		{
			libr.writeExtent("Fail", "Test case has failed steps");
			e.printStackTrace();
			Assert.assertFalse(true, "The test case has failed steps");
		}
	}
	
}


