package capacitymanagement;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;


public class allotment extends CustomFunctions {
	
	

	String sheetName="Monitor_Flights_CAP147";
	String screenName="Monitor_Flights : CAP147";
	
	public allotment(WebDriver driver, ExcelReadWrite excelReadWrite,
			Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);}
	
	/**
	 * @Description : Total capacity Sales with OVB Details
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param OVBwgt
	 * @param AircraftVol
	 * @param OVBvol
	 * @throws InterruptedException
	 */
	public void totalCapacitySales(String Aircraftwgt, String OVBwgt, String AircraftVol, String OVBvol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int totalCapacitySalesWgt= Integer.parseInt(Aircraftwgt)+Integer.parseInt(OVBwgt);
		int totalCapacitySalesVol= Integer.parseInt(AircraftVol)+Integer.parseInt(OVBvol);

		
		getTextAndVerify(sheetName, "lbl_totalCapacitySalesWgt;xpath", "Total Capacity Sales Weight", screenName, "Total Capacity Sales Weight",Integer.toString(totalCapacitySalesWgt)+" "+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalCapacitySalesVol;xpath", "Total Capacity Sales Volume", screenName, "Total Capacity Sales Volume",Integer.toString(totalCapacitySalesVol)+" "+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	
	/**
	 * @Description : Total Capacity Handling with OVB Details
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param AircraftVol
	 * @throws InterruptedException
	 */
	public void totalCapacityHandling(String Aircraftwgt, String AircraftVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		
		getTextAndVerify(sheetName, "lbl_totalCapacityHandlingWgt;xpath", "Total Capacity Handling Weight", screenName, "Total Capacity Handling Weight",Aircraftwgt+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalCapacityHandlingVol;xpath", "Total Capacity Handling Volume", screenName, "Total Capacity Handling Volume",AircraftVol+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
}
	/**
	 * @Description : Total Allotment Details
	 * @author A-9175
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @throws InterruptedException
	 */
	
	public void totalAllotment(String AllotmentWgt, String AllotmentVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		
		getTextAndVerify(sheetName, "lbl_totalAllotmentWgt;xpath", "Total Allotment Weight", screenName, "Total Allotment Weight",AllotmentWgt+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalAllotmentVol;xpath", "Total Allotment Volume", screenName, "Total Allotment Volume",AllotmentVol+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
}
	
	/**
	 * @Description :Remaining Capacity FS Sales details with ALLOTMENT
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param OVBwgt
	 * @param AircraftVol
	 * @param OVBvol
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @throws InterruptedException
	 */
	public void remainingCapacityFSSalesAllotment(String Aircraftwgt, String OVBwgt, String AircraftVol, String OVBvol,String AllotmentWgt,String AllotmentVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingCapacitySalesFSWgt= Integer.parseInt(Aircraftwgt)+Integer.parseInt(OVBwgt)-Integer.parseInt(AllotmentWgt);
		int remainingCapacitySalesFSVol= Integer.parseInt(AircraftVol)+Integer.parseInt(OVBvol)-Integer.parseInt(AllotmentVol);

		
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity - Sales Weight", screenName, "Remaining FS Capacity - Sales Weight",Integer.toString(remainingCapacitySalesFSWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity - Sales Volume", screenName, "Remaining FS Capacity - Sales Volume",Integer.toString(remainingCapacitySalesFSVol)+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	
	/**
	 * @Description : Remaining Capacity FS Handling details with ALLOTMENT
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param AircraftVol
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @throws InterruptedException
	 */
	public void remainingcapacityFSHandlingAllotment(String Aircraftwgt, String AircraftVol, String AllotmentWgt,String AllotmentVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingCapacityHandlingFSWgt= Integer.parseInt(Aircraftwgt)-Integer.parseInt(AllotmentWgt);
		int remainingCapacityHandlingFSVol= Integer.parseInt(AircraftVol)-Integer.parseInt(AllotmentVol);

		
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity - Handling Weight", screenName, "Remaining FS Capacity - Handling Weight",Integer.toString(remainingCapacityHandlingFSWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity - Handling Volume", screenName, "Remaining FS Capacity - Handling Volume",Integer.toString(remainingCapacityHandlingFSVol)+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	
	/**
	 * @Description: Remaining ALLOTMENT capacity deatils
	 * @author A-9175
	 * @param AllotmentWgt
	 * @param AllotmentVol
	 * @param bookedAllotmentWgt
	 * @param bookedAllotmentVol
	 * @throws InterruptedException
	 */
	public void remainingAllotmentCapacity(String AllotmentWgt, String AllotmentVol, String bookedAllotmentWgt,String bookedAllotmentVol) throws InterruptedException {
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingAllotmentCapacityWgt= Integer.parseInt(AllotmentWgt)-Integer.parseInt(bookedAllotmentWgt);
		int remainingAllotmentCapacityVol= Integer.parseInt(AllotmentVol)-Integer.parseInt(bookedAllotmentVol);

		
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityWgt;xpath", "Remaining Allotment Capacity Weight", screenName, "Remaining FS Capacity - Handling Weight",Integer.toString(remainingAllotmentCapacityWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityVol;xpath", "Remaining Allotment Capacity Volume", screenName, "Remaining FS Capacity - Handling Volume",Integer.toString(remainingAllotmentCapacityVol)+"CBM", "equals");

		
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	
	/**
	 * @Description : Remaining Capacity FS Handling details without ALLOTMENT and with OVB deatils
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param OVBwgt
	 * @param AircraftVol
	 * @param OVBvol
	 * @param BookedWgt
	 * @param BookedVol
	 * @throws InterruptedException
	 */
	public void remainingCapacityFSSaleswithoutAllotment(String Aircraftwgt, String OVBwgt, String AircraftVol, String OVBvol,String BookedWgt,String BookedVol) throws InterruptedException {
	
	switchToFrame("frameName","popupContainerFrame");
	waitForSync(2);
	
	int remainingCapacitySalesFSWgt= Integer.parseInt(Aircraftwgt)+Integer.parseInt(OVBwgt)-Integer.parseInt(BookedWgt);
	int remainingCapacitySalesFSVol= Integer.parseInt(AircraftVol)+Integer.parseInt(OVBvol)-Integer.parseInt(BookedVol);

	
	getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity - Sales Weight", screenName, "Remaining FS Capacity - Sales Weight",Integer.toString(remainingCapacitySalesFSWgt)+"kg", "equals");
	getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity - Sales Volume", screenName, "Remaining FS Capacity - Sales Volume",Integer.toString(remainingCapacitySalesFSVol)+"CBM", "equals");

	
	waitForSync(2);
	switchToFrame("default");
	switchToFrame("contentFrame","CAP147");
	
}

	/**
	 * @Description : Remaining Capacity FS Handling details without ALLOTMENT 
	 * @author A-9175
	 * @param Aircraftwgt
	 * @param AircraftVol
	 * @param BookedWgt
	 * @param BookedVol
	 * @throws InterruptedException
	 */
	public void remainingcapacityFSHandlingwithoutAllotment(String Aircraftwgt, String AircraftVol, String BookedWgt,String BookedVol) throws InterruptedException {
	
	switchToFrame("frameName","popupContainerFrame");
	waitForSync(2);
	
	int remainingCapacityHandlingFSWgt= Integer.parseInt(Aircraftwgt)-Integer.parseInt(BookedWgt);
	int remainingCapacityHandlingFSVol= Integer.parseInt(AircraftVol)-Integer.parseInt(BookedVol);

	
	getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity - Handling Weight", screenName, "Remaining FS Capacity - Handling Weight",Integer.toString(remainingCapacityHandlingFSWgt)+"kg", "equals");
	getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity - Handling Volume", screenName, "Remaining FS Capacity - Handling Volume",Integer.toString(remainingCapacityHandlingFSVol)+"CBM", "equals");

	
	waitForSync(2);
	switchToFrame("default");
	switchToFrame("contentFrame","CAP147");
	
}
	
	/**
	 * @author A-9175
	 * Description... verify Depletion Details for  flight after booking with allotment
	 * @param AircraftWgt
	 * @param AircraftVol
	 * @param shipmentWgt
	 * @param shipmentVol
	 * @throws InterruptedException
	 */
	public void verifyDepletionDetailsAfterAllotmentBooking(String AircraftWgt, String AircraftVol,
			String FlightAltWgt,String shipmentWgt,String FlightAltVol,String shipmentVol) throws InterruptedException 
	{
		
		
		
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		
		int remainingWgt= Integer.parseInt(AircraftWgt)-Integer.parseInt(FlightAltWgt);
		int remainingVol= Integer.parseInt(AircraftVol)-Integer.parseInt(FlightAltVol);
		int remainingAltWgt=Integer.parseInt(FlightAltWgt)-Integer.parseInt(shipmentWgt);
		int remainingAltVol=Integer.parseInt(FlightAltVol)-Integer.parseInt(shipmentVol);
		
		getTextAndVerify(sheetName, "lbl_totalAllotmentWgt;xpath", "Total Allotment Weight", screenName, "Total Allotment Weight",FlightAltWgt+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_totalAllotmentVol;xpath", "Total Allotment Volume", screenName, "Total Allotment Volume",FlightAltVol+"CBM", "equals");

		
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity Sales Weight", screenName, "Verification of Remaining FS Capacity Sales Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity Sales Volume", screenName, "Verification of Remaining FS Capacity Sales Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity Handling Weight", screenName, "Verification of Remaining FS Capacity Handling Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity Handling Volume", screenName, "Verification of Remaining FS Capacity Handling Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityWgt;xpath", "Remaining Allotment Capacity  Weight", screenName, "Verification of Remaining Allotment Capacity Weight",
				Integer.toString(remainingAltWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingAllotmentCapacityVol;xpath", "Remaining Allotment Capacity Volume", screenName, "Verification of Remaining Allotment Capacity  Volume",
				Integer.toString(remainingAltVol)+"CBM", "equals");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	
	/**
	 * @author A-9175
	 * Description... generic method to verify Depletion Details after booking 
	 * @param AircraftWgt
	 * @param AircraftVol
	 * @param shipmentWgt
	 * @param shipmentVol
	 * @throws InterruptedException
	 */
	public void verifyDepletionDetailsAfterFSBooking(String AircraftWgt, String AircraftVol,String shipmentWgt,String shipmentVol) throws InterruptedException 
	{
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		int remainingWgt= Integer.parseInt(AircraftWgt)-Integer.parseInt(shipmentWgt);
		int remainingVol= Integer.parseInt(AircraftVol)-Integer.parseInt(shipmentVol);
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity Sales Weight", screenName, "Verification of Remaining FS Capacity Sales Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity Sales Volume", screenName, "Verification of Remaining FS Capacity Sales Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity Handling Weight", screenName, "Verification of Remaining FS Capacity Handling Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity Handling Volume", screenName, "Verification of Remaining FS Capacity Handling Volume",
				Integer.toString(remainingVol)+"CBM", "equals");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}
	
	/**
	 * @Description : overloaded generic method to Verifying depletion details - when volume is double
	 * @author A-9175
	 * @param AircraftWgt
	 * @param AircraftVol
	 * @param shipmentWgt
	 * @param shipmentVol
	 * @throws InterruptedException
	 */

	public void verifyDepletionAfterFSBooking(String AircraftWgt, String AircraftVol,String shipmentWgt,String shipmentVol) throws InterruptedException 
	{
		switchToFrame("frameName","popupContainerFrame");
		waitForSync(2);
		int remainingWgt= Integer.parseInt(AircraftWgt)-Integer.parseInt(shipmentWgt);
		double remainingVol= Integer.parseInt(AircraftVol)-Double.parseDouble(shipmentVol);
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesWgt;xpath", "Remaining FS Capacity Sales Weight", screenName, "Verification of Remaining FS Capacity Sales Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacitySalesVol;xpath", "Remaining FS Capacity Sales Volume", screenName, "Verification of Remaining FS Capacity Sales Volume",
				Double.toString(remainingVol)+"CBM", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingWgt;xpath", "Remaining FS Capacity Handling Weight", screenName, "Verification of Remaining FS Capacity Handling Weight",
				Integer.toString(remainingWgt)+"kg", "equals");
		getTextAndVerify(sheetName, "lbl_remainingFSCapacityHandlingVol;xpath", "Remaining FS Capacity Handling Volume", screenName, "Verification of Remaining FS Capacity Handling Volume",
				Double.toString(remainingVol)+"CBM", "equals");
		waitForSync(2);
		switchToFrame("default");
		switchToFrame("contentFrame","CAP147");
		
	}

	

}
