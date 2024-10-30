package screens;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.WebFunctions;
import common.Xls_Read;

public class DeliveryNoteRegister_OPR035 extends CustomFunctions{
	public DeliveryNoteRegister_OPR035(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
	}

	public String sheetName = "DeliveryNoteRegister_OPR035";
	public String ScreenName = "Delivery Note Register";
/**
 * Description... Verify AWB Details
 * @throws InterruptedException
 * @throws IOException 
 */
	public void verifyAWBDetails() throws InterruptedException, IOException {
		String xpath[] = { "txt_statedPieces;xpath", "txt_statedWeight;xpath", "txt_origin;xpath",
				"txt_destination;xpath", "txt_scc;xpath", "txt_shipDescription;xpath" };
		String eleName[] = { "Pieces", "Weight", "Origin", "Destination", "SCC", "Shipment Description" };
		String actawbDetailsTxt[] = new String[xpath.length];

		String expawbDetailsTxt[] = { data("Pieces"), data("Weight"), data("Origin"), data("Destination"),
				data("VerfSCC"), data("NatureOfGoods") };

		for (int i = 0; i < xpath.length; i++) {
			actawbDetailsTxt[i] = getElementText(sheetName, xpath[i], eleName[i], ScreenName);
			verifyValueOnPageContains(actawbDetailsTxt[i], expawbDetailsTxt[i], "Verify " + eleName[i], ScreenName, eleName[i]);

		}
	}
	/**
	 * Description... Verify HandOver Details Button
	 * @throws Exception
	 */
		public void  clickHandOverDetails() throws Exception{
			
			clickButtonSwitchWindow(sheetName, "btn_HandOverDetails;name", "HandOver Details Button" , ScreenName);
		}
	/**
	 * Description... Verify HandOver Details
	 * @throws InterruptedException
	 */
		public void verifyHandOverDetails() throws InterruptedException{
			ScreenName="Document HandOver";
			String acthandOverTo=getAttributeWebElement(sheetName, "inbx_handOverTo;name", "HandOver To","value",ScreenName);
			String actremarks=getAttributeWebElement(sheetName, "inbx_remarksPopup;name", "Remarks","value",ScreenName);
			verifyValueOnPage(acthandOverTo.toUpperCase(), data("CustomerName").toUpperCase(), "Verify HandOver To", ScreenName, "HandOver To");
			verifyValueOnPage(actremarks.toUpperCase(), data("Remarks").toUpperCase(), "Verify Remarks", ScreenName, "Remarks");
		}
	/**
	 * Description... Verify Document HandOver Check Box is checked
	 * @throws InterruptedException
	 */
		public void verifyDocumentHandOverChecked() throws InterruptedException{
			String actchecked=getAttributeWebElement(sheetName, "chk_docHandedOver;name", "Document HandOver Check Box", "checked", ScreenName);
			verifyValueOnPage(actchecked, "true", "Verify Document HandOver Check Box", ScreenName, "Document HandOver Check Box is checked");
		}
		/**
		 * Description... Close Document HandOver
		 * @throws Exception
		 */
		public void closeDocumentHandOver() throws Exception{
			clickButtonSwitchtoParentWindow(sheetName, "btn_close;name", "Close Button", "Document HandOver");
			switchToDefaultAndContentFrame("OPR035");
		}
		
		

}