package afklregression;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import screens.CaptureAWB_OPR026;
import screens.CaptureHAWB_OPR029;
import screens.ListMessages_MSG005;
import common.BaseSetup;
import common.CommonUtility;
import common.CustomFunctions;
import common.Excel;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import controls.ExcelRead;

/**Data capture of paper AWB for an account customer for local export of loose shipment which consignee is not in China**/

public class AWBDCCDG5_009 extends BaseSetup {
	
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
	public CaptureHAWB_OPR029 OPR029;
	String path1 = System.getProperty("user.dir")+ "\\src\\resources\\TestData.xls";
	public static String proppath = "\\src\\resources\\GlobalVariable.properties";
	public static String custproppath = "\\src\\resources\\Customer.properties";
	String sheetName="afklregression";	
	
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
		MSG005 = new ListMessages_MSG005(driver, excelreadwrite, xls_Read);
		OPR026 = new CaptureAWB_OPR026(driver, excelreadwrite, xls_Read);
		OPR029 = new CaptureHAWB_OPR029(driver, excelreadwrite, xls_Read);
	}
	
	
	
	@DataProvider(name = "TC_014")
	public Object[][] createData2() throws Exception {
		Object[][] retObjArr1 = excelRead.getMapArray(path1, sheetName, testName);
		return retObjArr1;

	}

	@Test(dataProvider = "TC_014")
	public void getTestSuite(Map<Object, Object> map) {
		
		try {
			libr.map=map;		
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			}

			System.out.println("The Class Name is:" + this.getClass().getName());
			libr.setExtentTestInstance(test);
		
			//Login to iCargo
		
			String [] iCargo=libr.getApplicationParams("iCargo");	
			driver.get(iCargo[0]);
			Thread.sleep(9000);
			cust.loginICargo(iCargo[1], iCargo[2]);
			Thread.sleep(2000);
			
			// Switch Role
			cust.switchRole("Origin", "FCTL", "RoleGroup");
			
			/****** Store XFWB CUSTOMER DETAILS IN MAP***/
			
			map.put("AgentCode", cust.getPropertyValue(custproppath, "creditCustomerId_DE"));
			map.put("AgentName", cust.getPropertyValue(custproppath, "creditCustomerName_DE"));
			map.put("AgentAccountNumber", cust.getPropertyValue(custproppath, "creditCustomerAccountNumber_DE"));
			map.put("AgentPostCode", cust.getPropertyValue(custproppath, "creditCustomerpostCode_DE"));
			map.put("AgentStreetName", cust.getPropertyValue(custproppath, "creditCustomerstreetName_DE"));
			map.put("AgentCityName", cust.getPropertyValue(custproppath, "creditCustomercityName_DE"));
			map.put("AgentCountryId", cust.getPropertyValue(custproppath, "creditCustomercountryId_DE"));
			map.put("AgentCountryName", cust.getPropertyValue(custproppath, "creditCustomercountryName_DE"));
			map.put("AgentCountrySubDiv", cust.getPropertyValue(custproppath, "creditCustomercountrySubdivision_DE"));
			map.put("AgentPhoneNo", cust.getPropertyValue(custproppath, "creditCustomertelephoneNo_DE"));
			map.put("AgentEmail", cust.getPropertyValue(custproppath, "creditCustomeremail_DE"));
			
			map.put("ShipperCode", cust.getPropertyValue(custproppath, "creditCustomerId_DE"));
			map.put("ShipperName", cust.getPropertyValue(custproppath, "creditCustomerName_DE"));
			map.put("ShipperPostCode", cust.getPropertyValue(custproppath, "creditCustomerpostCode_DE"));
			map.put("ShipperStreetName", cust.getPropertyValue(custproppath, "creditCustomerstreetName_DE"));
			map.put("ShipperCityName", cust.getPropertyValue(custproppath, "creditCustomercityName_DE"));
			map.put("ShipperCountryId", cust.getPropertyValue(custproppath, "creditCustomercountryId_DE"));
			map.put("ShipperCountryName", cust.getPropertyValue(custproppath, "creditCustomercountryName_DE"));
			map.put("ShipperCountrySubDiv", cust.getPropertyValue(custproppath, "creditCustomercountrySubdivision_DE"));
			map.put("ShipperPhoneNo", cust.getPropertyValue(custproppath, "creditCustomertelephoneNo_DE"));
			map.put("ShipperEmail", cust.getPropertyValue(custproppath, "creditCustomeremail_DE"));

			map.put("ConsigneeCode", cust.getPropertyValue(custproppath, "vcc_cashCustomerId_NL"));
			map.put("ConsigneeName", cust.getPropertyValue(custproppath, "vcc_cashCustomerName_NL"));
			map.put("ConsigneePostCode", cust.getPropertyValue(custproppath, "vcc_cashCustomerpostCode_NL"));
			map.put("ConsigneeStreetName", cust.getPropertyValue(custproppath, "vcc_cashCustomerstreetName_NL"));
			map.put("ConsigneeCityName", cust.getPropertyValue(custproppath, "vcc_cashCustomercityName_NL"));
			map.put("ConsigneeCountryId", cust.getPropertyValue(custproppath, "vcc_cashCustomercountryId_NL"));
			map.put("ConsigneeCountryName", cust.getPropertyValue(custproppath, "vcc_cashCustomercountryName_NL"));
			map.put("ConsigneeCountrySubDiv", cust.getPropertyValue(custproppath, "vcc_cashCustomercountrySubdivision_NL"));
			map.put("ConsigneePhoneNo", cust.getPropertyValue(custproppath, "vcc_cashCustomertelephoneNo_NL"));
			map.put("ConsigneeEmail", cust.getPropertyValue(custproppath, "vcc_cashCustomeremail_NL"));

			map.put("OriginAirport", cust.getPropertyValue(custproppath, "CDG"));
			map.put("DestinationAirport", cust.getPropertyValue(custproppath, "IAD"));
			
            /**** OPR026 - Capture AWB****/
			//Checking AWB is fresh or Not
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.checkAWBExists_OPR026("Capture Awb", "OPR026");
			libr.waitForSync(1);

			//Writing the full AWB No
			cust.setPropertyValue("FullAWBNo", cust.data("prop~stationCode")+"-"+cust.data("prop~AWBNo"), proppath);
			
			cust.searchScreen("OPR026","Capture AWB");
			OPR026.listAWB("prop~AWBNo", "prop~CarrierNumericCode");

			//Enter shipment details			
			OPR026.updateOrigin("Origin");
			OPR026.updateDestination("Destination");
			OPR026.enterRouting("Destination","prop~flight_code");       
			OPR026.selectSCI("SCI");
			OPR026.enterAgentCode("AgentCode");    
			OPR026.provideShipperCode("ShipperCode");
			OPR026.provideConsigneeCode("ConsigneeCode");
			OPR026.enterShipmentDetails("Pieces", "Weight","Volume","CommodityCode", "ShipmentDesc");
			
			OPR026.clickChargesAcc();
			//Provide rating details
			OPR026.provideRatingDetails("rateClass","IATARate","IATAcharge","netCharge");
			//Click calculate charges button
			OPR026.clickCalcCharges();
			//Click As Is Execute button
			OPR026.asIsExecute();
			cust.closeTab("OPR026", "Capture AWB");
			
			/*******MSG005 - List Messages******/
			cust.searchScreen("MSG005", "MSG005 - List Messages");
			MSG005.enterMsgType("XFWB");
			MSG005.selectStatus("Sent");
			MSG005.clickList();
			String pmKeyXFWB=cust.data("prop~CarrierNumericCode")+" - "+cust.data("prop~AWBNo")+" - "+cust.data("Origin")+" - "+cust.data("Destination");
			int verfColsXFWB[]={9};
			String[] actVerfValuesXFWB={"Sent"};
			MSG005.verifyMessageDetails(verfColsXFWB, actVerfValuesXFWB, pmKeyXFWB,"val~XFWB",true);
			libr.waitForSync(1); 
            
            
            /*** VERIFY THE MESSAGE CONTENTS***/
			map.put("pmkey", pmKeyXFWB);
			MSG005.clickCheckBox("pmkey");
			MSG005.clickView();
			List <String> msgContents=new ArrayList<String>();
			/**Origin**/
			msgContents.add("val~<OriginLocation>"+"\n"+"<ID>"+cust.data("Origin")+"</ID>");
			/**Destination**/
			msgContents.add("val~<FinalDestinationLocation>"+"\n"+"<ID>"+cust.data("Destination")+"</ID>");
			/**Agent**/
			msgContents.add("val~<FreightForwarderParty>"+"\n"+"<Name>"+cust.data("AgentName")+"</Name>"+
					"\n"+"<AccountID>"+cust.data("AgentAccountNumber")+"</AccountID>");
			/**Shipper**/
			msgContents.add("val~<ConsignorParty>"+"\n"+"<Name>"+cust.data("ShipperName")+"</Name>");
			/**Consignee**/
			msgContents.add("val~<ConsigneeParty>"+"\n"+"<Name>"+cust.data("ConsigneeName")+"</Name>");

			/*** SCI***/
			msgContents.add("val~<GoodsStatusCode>"+cust.data("SCI")+"</GoodsStatusCode>");
			
			/**Commodity Details**/
			String wtUnit="\"KGM\"";
			String volUnit="\"MTQ\"";
			msgContents.add("val~<IncludedMasterConsignmentItem>"+"\n"+"<SequenceNumeric>1</SequenceNumeric>"+
					"\n"+"<GrossWeightMeasure unitCode="+wtUnit+">"+cust.data("Weight")+"</GrossWeightMeasure>"+
					"\n"+"<GrossVolumeMeasure unitCode="+volUnit+">"+cust.data("Volume")+"</GrossVolumeMeasure>"+
					"\n"+"<PieceQuantity>"+cust.data("Pieces")+"</PieceQuantity>"+
					"\n"+"<NatureIdentificationTransportCargo>"+"\n"+"<Identification>"+cust.data("ShipmentDesc")+"</Identification>");
			//Verify message contents
			MSG005.verifyMessageContent(msgContents,"XFWB");
			MSG005.closeView();

			MSG005.closeTab("MSG005", "MSG005 - List Messages");
		
            


		}	
		catch(Exception e)
		{
			libr.onFailUpdate("Test case has failed steps");
			e.printStackTrace();
		}

	}
}

