package rest_cfp;


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

	
	
	
	public void postRequestCFP(String awbPrefix,String[] awbNumber,String carrierCode,String fltno, String flightDate,String origin, String destination,String pcs, String wt,String vol,String totalPcs, String totalWt, String totalVol,String noOfShpmnt,String[] commodityCode, String[] scc, String[] loadingPrio) throws JsonProcessingException
	{
		       
		         // End point
		        String url="https://mq-injector-cae-mdw1-00033-pks.qvi-cae.af-klm.com/api/injector/sendMessageToIBMMQ";
		        
		        //Message to be encoded
		        String msg ="<?xml version=\"1.0\" encoding=\"UTF-8\"?> <SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"> <SOAP-ENV:Header> <ns0:Action xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">http://www.accenture.com/afls/fpln/FplnService-v1/sendFplnService</ns0:Action> <ns0:To xmlns:ns0=\"http://www.w3.org/2005/08/addressing\"/> <ns0:MessageID xmlns:ns0=\"http://www.w3.org/2005/08/addressing\">uuid:e274b01d-40fc-40d9-a339-d25628bd45df</ns0:MessageID> <ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/InitiatedBy\">uuid:e274b01d-40fc-40d9-a339-d25628bd45df</ns0:RelatesTo> <ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/PrecededBy\"/> <ns0:RelatesTo xmlns:ns0=\"http://www.w3.org/2005/08/addressing\" RelationshipType=\"http://www.af-klm.com/soa/tracking/ReplyTo\"/> <ns2:trackingMessageHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/soa/xsd/MessageHeader-V1_0\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"> <ns2:consumerRef> <ns2:userID>AFLS</ns2:userID> <ns2:partyID>EXT</ns2:partyID> <ns2:consumerID>w41477230</ns2:consumerID> <ns2:consumerLocation>QVI</ns2:consumerLocation> <ns2:consumerType>A</ns2:consumerType> <ns2:consumerTime>2022-05-13T13:14:04.055Z</ns2:consumerTime> </ns2:consumerRef> </ns2:trackingMessageHeader> <ns2:EventHeader xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:ns2=\"http://www.af-klm.com/services/common/EventHeader-v1/xsd\" xmlns:ns1=\"soa://Framework/Ops/Context/Schemas/LocalContext-v1_0\"> <ns2:Publication> <ns2:Publisher Name=\"w41477230\" Type=\"soa\" Category=\"system\"/> </ns2:Publication> <ns2:Addressing> <ns2:ContentBasedAddressing Scope=\"all\">true</ns2:ContentBasedAddressing> <ns2:DeliverTo> <ns2:TriggeredSubscriber Name=\"CETMON\" Type=\"soa\" Category=\"system\"> <ns2:TriggeredByRule> <ns2:Rule Name=\"Generated Rule name\" Version=\"1\"/> </ns2:TriggeredByRule> </ns2:TriggeredSubscriber> </ns2:DeliverTo> <ns2:AlreadyDeliveredTo> <ns2:Subscriber Name=\"w41477230\" Type=\"soa\" Category=\"system\"/> <ns2:Subscriber Name=\"CARGOCDG\" Type=\"soa\" Category=\"system\"/> <ns2:Subscriber Name=\"CGODLK\" Type=\"soa\" Category=\"system\"/> <ns2:Subscriber Name=\"CHAIN\" Type=\"soa\" Category=\"system\"/> </ns2:AlreadyDeliveredTo> </ns2:Addressing> </ns2:EventHeader> </SOAP-ENV:Header> <SOAP-ENV:Body> <MESSAGEDOCUMENT xmlns:jms1=\"http://www.tibco.com/namespaces/tnt/plugins/jms\" xmlns=\"http://www.accenture.com/cargoops/xbeans/fplnservice\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:ns0=\"soa://Framework/EEB/Common/Schemas/EEBMessage-v1_0\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"> <RESPONSECONTENT> <FLIGHTPLANNINGLIST> <FLIGHT_DETAILS> <CARRIER>"+carrierCode+"</CARRIER> <FLIGHT_NUMBER>"+fltno+"</FLIGHT_NUMBER> <LEG_BOARDPOINT>"+origin+"</LEG_BOARDPOINT> <LEG_DEPARTURE_DT>"+flightDate+" 10:10</LEG_DEPARTURE_DT> <LEG_OFFPOINT>"+destination+"</LEG_OFFPOINT> <LEG_ARRIVAL_DT>"+flightDate+" 13:35</LEG_ARRIVAL_DT> <AIRCRAFT_GROUP>W</AIRCRAFT_GROUP> <AIRCRAFT_TYPE>33X</AIRCRAFT_TYPE> <CARGO_DETAILS> <WEIGHT UNITS=\"K\">"+totalWt+"</WEIGHT> <VOLUME UNITS=\"MC\">"+totalVol+"</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">7</POSITION> <POSITION TYPE=\"LD3\">0</POSITION> <POSITION TYPE=\"LD7\">7</POSITION> </POSITIONS> </CARGO_DETAILS> <FLIGHT_ROUTING> <STATION_CODE>"+origin+"</STATION_CODE> <STATION_CODE>"+destination+"</STATION_CODE> </FLIGHT_ROUTING> <RM_HANDOVER_STATUS LAST_UPDATED=\"10/SEP/2022 11:48\">true</RM_HANDOVER_STATUS> <WH_HANDOVER_STATUS LAST_UPDATED=\"10/SEP/2022 15:14\">true</WH_HANDOVER_STATUS> <OPTI_FLIGHT_STATUS>false</OPTI_FLIGHT_STATUS> <CREATED_BY>t060491</CREATED_BY> <CREATED_DT>10/SEP/2022 15:14</CREATED_DT> <FLIGHT_SUMMARY_REMARKS>RM ISA.LD 06.98.01.63.56 V7/0 3PLD/19CM LOOSE TO BUILD ---------------------- RM Carmen 10/Sep/2022 11:48: BIG 057-02866964; OHG 057-07162643 OA 0CM / 0kg</FLIGHT_SUMMARY_REMARKS> <OPTI_REMARKS/> </FLIGHT_DETAILS> <FLIGHT_CAPACITY> <LEG_CAPACITY> <WEIGHT UNITS=\"K\">"+totalWt+"</WEIGHT> <VOLUME UNITS=\"MC\">"+totalVol+"</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">7</POSITION> <POSITION TYPE=\"LD3\">0</POSITION> <POSITION TYPE=\"LD7\">7</POSITION> </POSITIONS> </LEG_CAPACITY> <LEG_TOTAL> <LEG_BOOKED> <NO_OF_SHIPMENTS>"+noOfShpmnt+"</NO_OF_SHIPMENTS> <PIECES>"+totalPcs+"</PIECES> <WEIGHT UNITS=\"K\">"+totalWt+"</WEIGHT> <VOLUME UNITS=\"MC\">"+totalVol+"</VOLUME> </LEG_BOOKED> <LEG_BUP> <NO_OF_SHIPMENTS>0</NO_OF_SHIPMENTS> <PIECES>0</PIECES> <WEIGHT UNITS=\"K\">0</WEIGHT> <VOLUME UNITS=\"MC\">0</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">0</POSITION> <POSITION TYPE=\"LD3\">0</POSITION> <POSITION TYPE=\"LD7\">0</POSITION> </POSITIONS> </LEG_BUP> <LEG_ULD> <NO_OF_SHIPMENTS>0</NO_OF_SHIPMENTS> <PIECES>0</PIECES> <WEIGHT UNITS=\"K\">0</WEIGHT> <VOLUME UNITS=\"MC\">0</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">0</POSITION> <POSITION TYPE=\"LD3\">0</POSITION> <POSITION TYPE=\"LD7\">0</POSITION> </POSITIONS> </LEG_ULD> <LEG_LOOSE> <NO_OF_SHIPMENTS>"+noOfShpmnt+"</NO_OF_SHIPMENTS> <PIECES>"+totalPcs+"</PIECES> <WEIGHT UNITS=\"K\">"+totalWt+"</WEIGHT> <VOLUME UNITS=\"MC\">"+totalVol+"</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">0</POSITION> <POSITION TYPE=\"LD3\">0</POSITION> <POSITION TYPE=\"LD7\">3</POSITION> </POSITIONS> </LEG_LOOSE> <LEG_PALLETIZED> <NO_OF_SHIPMENTS>0</NO_OF_SHIPMENTS> <PIECES>0</PIECES> <WEIGHT UNITS=\"K\">0</WEIGHT> <VOLUME UNITS=\"MC\">0</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">0</POSITION> <POSITION TYPE=\"LD3\">0</POSITION> <POSITION TYPE=\"LD7\">0</POSITION> </POSITIONS> </LEG_PALLETIZED> <GRAND_TOTAL> <NO_OF_SHIPMENTS>"+noOfShpmnt+"</NO_OF_SHIPMENTS> <PIECES>"+totalPcs+"</PIECES> <WEIGHT UNITS=\"K\">"+totalWt+"</WEIGHT> <VOLUME UNITS=\"MC\">"+totalVol+"</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">0</POSITION> <POSITION TYPE=\"LD3\">0</POSITION> <POSITION TYPE=\"LD7\">4</POSITION> </POSITIONS> </GRAND_TOTAL> <UNRELEASED_ALLOTMENTS> <ALLOTMENT> <ALLOC_ID>REPAIR</ALLOC_ID> <WEIGHT UNITS=\"K\">0</WEIGHT> <VOLUME UNITS=\"MC\">0</VOLUME> <POSITIONS> <POSITION TYPE=\"BLK\">0</POSITION> <POSITION TYPE=\"LD7\">0</POSITION> </POSITIONS> </ALLOTMENT> </UNRELEASED_ALLOTMENTS> </LEG_TOTAL> </FLIGHT_CAPACITY> <AWBS> <AWB> <PREFIX>"+awbPrefix+"</PREFIX> <SERIAL_NUMBER>"+awbNumber[0]+"</SERIAL_NUMBER> <PIECES_BOOKED>"+pcs+"</PIECES_BOOKED> <PIECES_TO_BUILD>"+pcs+"</PIECES_TO_BUILD> <DIMENSIONS> <LENGTH UNITS=\"C\">142</LENGTH> <WIDTH UNITS=\"C\">142</WIDTH> <HEIGHT UNITS=\"C\">92</HEIGHT> </DIMENSIONS> <WEIGHT UNITS=\"K\">"+wt+"</WEIGHT> <VOLUME UNITS=\"MC\">"+vol+"</VOLUME> <COMMODITY>"+commodityCode[0]+"</COMMODITY> <SHCS> <SHC>"+scc[1]+"</SHC> </SHCS> <ULD_ID>N/A</ULD_ID> <SEG_BOARD_POINT>"+origin+"</SEG_BOARD_POINT> <SEG_OFF_POINT>"+destination+"</SEG_OFF_POINT> <LOADING_PRIORITY>"+loadingPrio[0]+"</LOADING_PRIORITY> <LOCATION/> </AWB> <AWB> <PREFIX>"+awbPrefix+"</PREFIX> <SERIAL_NUMBER>"+awbNumber[1]+"</SERIAL_NUMBER> <PIECES_BOOKED>"+pcs+"</PIECES_BOOKED> <PIECES_TO_BUILD>"+pcs+"</PIECES_TO_BUILD> <DIMENSIONS> <LENGTH UNITS=\"C\">142</LENGTH> <WIDTH UNITS=\"C\">142</WIDTH> <HEIGHT UNITS=\"C\">92</HEIGHT> </DIMENSIONS> <WEIGHT UNITS=\"K\">"+wt+"</WEIGHT> <VOLUME UNITS=\"MC\">"+vol+"</VOLUME> <COMMODITY>"+commodityCode[1]+"</COMMODITY> <SHCS> <SHC>"+scc[1]+"</SHC> </SHCS> <ULD_ID>N/A</ULD_ID> <SEG_BOARD_POINT>"+origin+"</SEG_BOARD_POINT> <SEG_OFF_POINT>"+destination+"</SEG_OFF_POINT> <LOADING_PRIORITY>"+loadingPrio[1]+"</LOADING_PRIORITY> <LOCATION/> </AWB> </AWBS> </FLIGHTPLANNINGLIST> </RESPONSECONTENT> </MESSAGEDOCUMENT> </SOAP-ENV:Body> </SOAP-ENV:Envelope>";
		       
		        System.out.println(msg);
				//Encoded message
				String encodedMsg= Base64.getEncoder().encodeToString(msg.getBytes());
				

				Headers headers=new Headers("http://www.accenture.com/afls/fpln/FplnService-v1/sendFplnService");
			
				
				/***** CREATE PAYLOAD****/
				
				Payload p=new Payload(encodedMsg,"CFP-ICARGO-RC4",headers);

				ObjectMapper objMap=new ObjectMapper();

				String mydata=objMap.writerWithDefaultPrettyPrinter().writeValueAsString(p);

				String f=mydata.replaceAll("soapAction", "SoapAction");

				System.out.println(f);

				Object obj=f;

			  
			     // Post json request
			     Response resp=RestAssured.given()
			    .header("Content-Type","application/json").log().all().body(obj).post(url);
			     
			      int val=resp.getStatusCode();
			      
			      System.out.println(val);
			      
			      if(val==200)
			      {
			    		writeExtent("Pass","Response code of CFP is "+val);
			      }
			      else
			      {
			    	  writeExtent("Fail","Response code of CFP is "+val);
			      }
			
			     
			
			     

	     
	     

	}

}
