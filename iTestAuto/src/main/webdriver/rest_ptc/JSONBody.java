package rest_ptc;


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
	 * @param su - timestamp
	 * @throws JsonProcessingException
 * @Desc : post publishawbscreening event
	 */
	public void postRequest(String carriercode,String flightNo,String flightDate,String executionResult,String screeningMethod,String Loc,String screener,String token,String agentType,String timestamp) throws JsonProcessingException
	{
		
		//Agent ID
String agentId= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB");
		
		//Country code
		String countryCode= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB");
		
		//Expiry
		String expiry= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB");

		//End point
		String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";

		//Message to be encoded
		String msg="";     
		if(executionResult.equals("P"))

			msg="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><trackingMessageHeader xmlns=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"><consumerRef><partyID>AF</partyID><consumerID>w07576423</consumerID><consumerLocation>CDG</consumerLocation><consumerType>A</consumerType><consumerTime>2022-11-15T09:57:45Z</consumerTime></consumerRef></trackingMessageHeader><MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">f0952391-0ff5-475f-9431-6161fd1a3256</MessageID><RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo> <RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo></soapenv:Header><soapenv:Body><saveTruckScreening xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.af-klm.com/services/cargo/PublishTruckScreening-V1/PublishTruckScreening/xsd\"><token>"+token+"</token><flightCarrierCode>"+carriercode+"</flightCarrierCode><flightNumber>"+flightNo+"</flightNumber><flighDate>"+flightDate+"</flighDate><airport>CDG</airport><method>"+screeningMethod+"</method><locationCode>"+Loc+"</locationCode><statusCode>P</statusCode><screener_name>"+screener+"</screener_name><screeningDate>"+timestamp+"</screeningDate><agent_type>"+agentType+"</agent_type><agent_id>"+agentId+"</agent_id><iso_country_code>"+countryCode+"</iso_country_code><expiry>"+expiry+"</expiry></saveTruckScreening></soapenv:Body> </soapenv:Envelope>";
			else
				msg="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><trackingMessageHeader xmlns=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"><consumerRef><partyID>AF</partyID><consumerID>w07576423</consumerID><consumerLocation>CDG</consumerLocationconsumerLocation><consumerType>A</consumerType><consumerTime>2022-11-15T09:57:45Z</consumerTime></consumerRef></trackingMessageHeader><MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">f0952391-0ff5-475f-9431-6161fd1a3256</MessageID><RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo> <RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo></soapenv:Header><soapenv:Body><saveTruckScreening xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.af-klm.com/services/cargo/PublishTruckScreening-V1/PublishTruckScreening/xsd\"><token>"+token+"</token><flightCarrierCode>"+carriercode+"</flightCarrierCode><flightNumber>"+flightNo+"</flightNumber><flighDate>"+flightDate+"</flighDate><airport>CDG</airport><method>"+screeningMethod+"</method><locationCode>"+Loc+"</locationCode><statusCode>F</statusCode><screener_name>"+screener+"</screener_name><screeningDate>"+timestamp+"</screeningDate><agent_type>"+agentType+"</agent_type><agent_id>"+agentId+"</agent_id><iso_country_code>"+countryCode+"</iso_country_code><expiry>"+expiry+"</expiry></saveTruckScreening></soapenv:Body> </soapenv:Envelope>";
				
				//Encoded message
		String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());

		Headers headers=new Headers("http://www.af-klm.com/services/cargo/PublishTruckScreening-V1/saveTruckScreening");

		/***** CREATE PAYLOAD****/
		Payload p=new Payload(encodedMsg,"PTC-ICARGO-RCT",headers);

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
			writeExtent("Pass","Response code of PATS is "+val);
		}
		else
		{
			writeExtent("Fail","Response code of PATS is "+val);
		} 


	}

	
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
	public void postRequest(String carriercode,String flightNo,String timeStamp,String executionResult,String screeningMethod,String rapixEntryLoc,String screener,String token,String agentType) throws JsonProcessingException
	{
		
		//Agent ID
		String agentId= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_Carrier_CodeHUB");
		
		//Country code
		String countryCode= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_CountryIdHUB");
		
		//Expiry
		String expiry= WebFunctions.getPropertyValue(custproppath, "regulated_Agent_ExpiryHUB");
		

		//End point
		String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";

		//Message to be encoded
		String msg="";     
		if(executionResult.equals("P"))

		msg="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><trackingMessageHeader xmlns=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"><consumerRef><partyID>AF</partyID><consumerID>w07576423</consumerID><consumerLocation>CDG</consumerLocation><consumerType>A</consumerType><consumerTime>2022-11-15T09:57:45Z</consumerTime></consumerRef></trackingMessageHeader><MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">f0952391-0ff5-475f-9431-6161fd1a3256</MessageID><RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo> <RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo></soapenv:Header><soapenv:Body><saveTruckScreening xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.af-klm.com/services/cargo/PublishTruckScreening-V1/PublishTruckScreening/xsd\"><token>"+token+"</token><flightCarrierCode>"+carriercode+"</flightCarrierCode><flightNumber>"+flightNo+"</flightNumber><flighDate>"+timeStamp+"</flighDate><airport>CDG</airport><method>"+screeningMethod+"</method><locationCode>CDG</locationCode><statusCode>P</statusCode><screener_name>"+screener+"</screener_name><screeningDate>31-Oct-2023 15:22:00</screeningDate><agent_type>"+agentType+"</agent_type><agent_id>"+agentId+"</agent_id><iso_country_code>"+countryCode+"</iso_country_code><expiry>"+expiry+"</expiry></saveTruckScreening></soapenv:Body> </soapenv:Envelope>";
		else
			msg="<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header><trackingMessageHeader xmlns=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\"><consumerRef><partyID>AF</partyID><consumerID>w07576423</consumerID><consumerLocation>CDG</consumerLocation><consumerType>A</consumerType><consumerTime>2022-11-15T09:57:45Z</consumerTime></consumerRef></trackingMessageHeader><MessageID xmlns=\"http://www.w3.org/2005/08/addressing\">f0952391-0ff5-475f-9431-6161fd1a3256</MessageID><RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo> <RelatesTo RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\" xmlns=\"http://www.w3.org/2005/08/addressing\">b762bf9e-2487-42a3-bc88-be998364e51d</RelatesTo></soapenv:Header><soapenv:Body><saveTruckScreening xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"http://www.af-klm.com/services/cargo/PublishTruckScreening-V1/PublishTruckScreening/xsd\"><token>"+token+"</token><flightCarrierCode>"+carriercode+"</flightCarrierCode><flightNumber>"+flightNo+"</flightNumber><flighDate>"+timeStamp+"</flighDate><airport>CDG</airport><method>"+screeningMethod+"</method><locationCode>CDG</locationCode><statusCode>F</statusCode><screener_name>"+screener+"</screener_name><screeningDate>31-Oct-2023 15:22:00</screeningDate><agent_type>"+agentType+"</agent_type><agent_id>"+agentId+"</agent_id><iso_country_code>"+countryCode+"</iso_country_code><expiry>"+expiry+"</expiry></saveTruckScreening></soapenv:Body> </soapenv:Envelope>";
		
		//Encoded message
		String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());

		Headers headers=new Headers("http://www.af-klm.com/services/cargo/PublishTruckScreening-V1/saveTruckScreening");

		/***** CREATE PAYLOAD****/
		Payload p=new Payload(encodedMsg,"PTC-ICARGO-RCT",headers);

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
			writeExtent("Pass","Response code of PATS is "+val);
		}
		else
		{
			writeExtent("Fail","Response code of PATS is "+val);
		} 


	}

}
