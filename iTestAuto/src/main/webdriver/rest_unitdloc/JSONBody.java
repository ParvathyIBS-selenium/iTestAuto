package rest_unitdloc;

import java.util.Base64;
import org.openqa.selenium.WebDriver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JSONBody extends CustomFunctions {


	public JSONBody(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);

	}

	/**
	 * @author A-8783
	 * @param equipId
	 * @param uld
	 * @param targLocation
	 * @param occupancyStatus
	 * @param wareHouse
	 * @throws JsonProcessingException
	 * @throws InterruptedException
	 */

	public void postRequest(String equipId,String uld,String targLocation,String occupancyStatus, String wareHouse) throws JsonProcessingException, InterruptedException
	{  


		//End point
		String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";

		//Message to be encoded
		String msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.af-klm.com/services/cargo/MI-v1/xsd\"> <soapenv:Header> <trackingMessageHeader xmlns=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"> <consumerRef> <userID>CGOIMCOM</userID> <partyID>AF</partyID> <consumerID>W002478</consumerID> <consumerLocation>VLB</consumerLocation> <consumerType>A</consumerType> <consumerTime>2022-12-07T23:01:24Z</consumerTime> </consumerRef> </trackingMessageHeader> <MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</MessageID> <RelatesTo xmlns=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"InitiatedBy\">4b8a127d-a48f-4893-8530-90d665ff666c</RelatesTo> </soapenv:Header> <soapenv:Body> <q1:SendStorageUnitDistrictLocationRequestElement xmlns:q1=\"http://www.af-klm.com/services/cargo/MI-v1/xsd\"> <messageHeader> <messageType>D</messageType> <sourceSystem>IM</sourceSystem> <entityUpdateTime>2022-12-07T23:01:24.506549Z</entityUpdateTime> <messageCreationTime>2022-12-07T23:01:24.506549Z</messageCreationTime> </messageHeader> <requestData> <requestId>69531</requestId> <airportCode>CDG</airportCode> <storageUnitCode>"+uld+"</storageUnitCode> <equipment> <equipmentID>"+equipId+"</equipmentID> <equipmentName>"+equipId+"</equipmentName> </equipment> <currentWarehouseCode>"+wareHouse+"</currentWarehouseCode> <targetLocation>"+targLocation+"</targetLocation> <targetWarehouseCode>"+wareHouse+"</targetWarehouseCode> <lockStatus>N</lockStatus> <occupancyStatus>"+occupancyStatus+"</occupancyStatus> <grossWeight>0</grossWeight> <transactionDate>2022-12-07T23:01:24.506549Z</transactionDate> <plannedRetrievalDate>2022-12-07T23:01:24.506549Z</plannedRetrievalDate> </requestData> </q1:SendStorageUnitDistrictLocationRequestElement> </soapenv:Body> </soapenv:Envelope>";
		
		//Encoded message
		String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());

		Headers headers=new Headers("\"http://www.af-klm.com/services/cargo/SendStorageUnitDistrictLocation-v1/sendStorageUnitDistrictLocation\"");		

		/***** CREATE PAYLOAD****/				
		Payload p=new Payload(encodedMsg,"UNITDLOC-ICARGO-RCT",headers);

		ObjectMapper objMap=new ObjectMapper();

		String mydata=objMap.writerWithDefaultPrettyPrinter().writeValueAsString(p);

		String f=mydata.replaceAll("soapAction", "SoapAction");

		System.out.println(f);

		Object obj=f;

		//Post json request
		Response resp=RestAssured.given()
				.header("Content-Type","application/json").log().all().body(obj).post(url);

		int val=resp.getStatusCode();

		System.out.println(val);

		if(val==200)
		{
			writeExtent("Pass","Response code of RelocateStorageUnit is "+val);
		}
		else
		{
			writeExtent("Fail","Response code of RelocateStorageUnit is "+val);
		}
	}

}
