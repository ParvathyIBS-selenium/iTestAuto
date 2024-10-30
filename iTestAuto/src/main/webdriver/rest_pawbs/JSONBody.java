package rest_pawbs;


import java.util.Base64;

import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class JSONBody extends CustomFunctions {


	public JSONBody(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}

	public static String custproppath = "\\src\\resources\\Customer.properties";
	
	/**
	 * 
	 * @param awbPrefix - Awb prefix
	 * @param awbNumber - awb suffix
	 * @param timeStamp - screening time
	 * @param executionResult - screening result whether pass or fail
	 * @param screeningMethod - screening method
	 * @param rapixEntryLoc - rapix entry loc
	 * @param screener - screened by
	 * @param su - storage unit
	 * @throws JsonProcessingException
	 * @Desc : post publishawbscreening event
	 */
	public void postRequest(String awbPrefix,String awbNumber,String timeStamp,String executionResult,String screeningMethod,String rapixEntryLoc,String screener,String su) throws JsonProcessingException
	{
		
		//Agent ID
		String agentId= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB");
		
		//Country code
		String countryCode= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB");
		
		//Expiry
		String expiry= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB");
		
		String fullAWBNumber=awbPrefix+"-"+awbNumber;
		System.out.println(fullAWBNumber);

		//End point
		String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";

		//Message to be encoded
		String msg="";     
		if(executionResult.equals("P"))
			msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header><ns0:Action xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">http://www.af-klm.com/services/cargo/publishAWBScreening-V1/saveAWBScreening</ns0:Action><ns0:To xmlns:ns0=\"http://www.w3.org/2005/08/addressing\"/><ns0:MessageID xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">988d2a2e-25df-4985-93e0-b952a89d8f40</ns0:MessageID><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\">b762bf9e-2487-42a3-bc88-be998364e51d</ns0:RelatesTo><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\">b762bf9e-2487-42a3-bc88-be998364e51d</ns0:RelatesTo><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/ReplyTo\"/><ns2:trackingMessageHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:consumerRef><ns2:userID>unknown</ns2:userID><ns2:partyID>AF</ns2:partyID><ns2:consumerID>w07576423</ns2:consumerID><ns2:consumerLocation>CDG</ns2:consumerLocation><ns2:consumerType>A</ns2:consumerType><ns2:consumerTime>2022-06-01T14:44:45Z</ns2:consumerTime></ns2:consumerRef></ns2:trackingMessageHeader><ns2:EventHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/services/common/EventHeader-v1/xsd\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:Publication><ns2:Publisher Name=\"w07576423\" Type=\"soa\" Category=\"system\"/></ns2:Publication><ns2:Addressing><ns2:ContentBasedAddressing Scope=\"all\">true</ns2:ContentBasedAddressing><ns2:DeliverTo><ns2:TriggeredSubscriber Name=\"ICARGO\" Type=\"soa\" Category=\"system\"><ns2:TriggeredByRule><ns2:Rule Name=\"Generated Rule name\" Version=\"1\"/></ns2:TriggeredByRule></ns2:TriggeredSubscriber></ns2:DeliverTo><ns2:AlreadyDeliveredTo><ns2:Subscriber Name=\"w07576423\" Type=\"soa\" Category=\"system\"/></ns2:AlreadyDeliveredTo></ns2:Addressing></ns2:EventHeader></SOAP-ENV:Header><SOAP-ENV:Body><saveAWBScreening xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns=\"http://www.af-klm.com/services/cargo/publishAWBScreening-V1/publishAWBScreening/xsd\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><awb>"+fullAWBNumber+"</awb><airport>CDG</airport><method>"+screeningMethod+"</method><storageUnitCode>"+su+"</storageUnitCode><locationCode>"+rapixEntryLoc+"</locationCode><statusCode>"+executionResult+"</statusCode><recommendationCode>Espacer les colis</recommendationCode><screenerName>"+screener+"</screenerName><screeningDate>"+timeStamp+"</screeningDate><agent_type>ISS</agent_type><agent_id>"+agentId+"</agent_id><iso_country_code>"+countryCode+"</iso_country_code><expiry>"+expiry+"</expiry></saveAWBScreening></SOAP-ENV:Body></SOAP-ENV:Envelope>";
		else
			msg="<?xml version=\"1.0\" encoding=\"UTF-8\"?><SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header><ns0:Action xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">http://www.af-klm.com/services/cargo/publishAWBScreening-V1/saveAWBScreening</ns0:Action><ns0:To xmlns:ns0=\"http://www.w3.org/2005/08/addressing\"/><ns0:MessageID xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">988d2a2e-25df-4985-93e0-b952a89d8f40</ns0:MessageID><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\">b762bf9e-2487-42a3-bc88-be998364e51d</ns0:RelatesTo><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\">b762bf9e-2487-42a3-bc88-be998364e51d</ns0:RelatesTo><ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/ReplyTo\"/><ns2:trackingMessageHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:consumerRef><ns2:userID>unknown</ns2:userID><ns2:partyID>AF</ns2:partyID><ns2:consumerID>w07576423</ns2:consumerID><ns2:consumerLocation>CDG</ns2:consumerLocation><ns2:consumerType>A</ns2:consumerType><ns2:consumerTime>2022-06-01T14:44:45Z</ns2:consumerTime></ns2:consumerRef></ns2:trackingMessageHeader><ns2:EventHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/services/common/EventHeader-v1/xsd\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"><ns2:Publication><ns2:Publisher Name=\"w07576423\" Type=\"soa\" Category=\"system\"/></ns2:Publication><ns2:Addressing><ns2:ContentBasedAddressing Scope=\"all\">true</ns2:ContentBasedAddressing><ns2:DeliverTo><ns2:TriggeredSubscriber Name=\"ICARGO\" Type=\"soa\" Category=\"system\"><ns2:TriggeredByRule><ns2:Rule Name=\"Generated Rule name\" Version=\"1\"/></ns2:TriggeredByRule></ns2:TriggeredSubscriber></ns2:DeliverTo><ns2:AlreadyDeliveredTo><ns2:Subscriber Name=\"w07576423\" Type=\"soa\" Category=\"system\"/></ns2:AlreadyDeliveredTo></ns2:Addressing></ns2:EventHeader></SOAP-ENV:Header><SOAP-ENV:Body><saveAWBScreening xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns=\"http://www.af-klm.com/services/cargo/publishAWBScreening-V1/publishAWBScreening/xsd\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><awb>"+fullAWBNumber+"</awb><airport>CDG</airport><method>"+screeningMethod+"</method><storageUnitCode>"+su+"</storageUnitCode><locationCode>"+rapixEntryLoc+"</locationCode><statusCode>"+executionResult+"</statusCode><refusalReason>FREIGHT TOO DENSE</refusalReason><recommendationCode>SPACE OUT</recommendationCode><screenerName>"+screener+"</screenerName><screeningDate>"+timeStamp+"</screeningDate><agent_type>ISS</agent_type><agent_id>"+agentId+"</agent_id><iso_country_code>"+countryCode+"</iso_country_code><expiry>"+expiry+"</expiry></saveAWBScreening></SOAP-ENV:Body></SOAP-ENV:Envelope>";

		
		//Encoded message
		String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());

		Headers headers=new Headers("http://www.af-klm.com/services/cargo/publishAWBScreening-V1/saveAWBScreening");

		/***** CREATE PAYLOAD****/
		Payload p=new Payload(encodedMsg,"PAWBS-ICARGO-RCT",headers);

		ObjectMapper objMap=new ObjectMapper();

		String mydata=objMap.writerWithDefaultPrettyPrinter().writeValueAsString(p);

		String f=mydata.replaceAll("soapAction", "SoapAction");

		System.out.println(f);

		Object obj=f;

		//Post json request
		Response resp=RestAssured.given().header("Content-Type","application/json").log().all().body(obj).post(url);

		int val=resp.getStatusCode();

		System.out.println(val);

		if(val==200)
		{
			writeExtent("Pass","Response code of PAWBS is "+val);
		}
		else
		{
			writeExtent("Fail","Response code of PAWBS is "+val);
		} 


	}

}
