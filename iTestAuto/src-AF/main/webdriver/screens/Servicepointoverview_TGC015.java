package screens;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Servicepointoverview_TGC015 extends CustomFunctions {
	public CustomFunctions customFuction;
	String sheetName = "Servicepointoverview_TGC015";
	String screenName = "Servicepointoverview";
	String screenId = "TGC015";

	public Servicepointoverview_TGC015(WebDriver driver,
			ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		customFuction = new CustomFunctions(driver, excelReadWrite, xls_Read2);

	}
	
	
	/**
	 * @author A-10690
	 * @param servicePoint
	 * @throws IOException
	 * Desc : select warehouse as counter
	 */
	
	public void selectWarehouse(String servicePoint) throws InterruptedException, IOException {
		
		waitTillScreenload(sheetName, "btn_leftarrow;xpath","Left Arrow", screenName);
		String s1=xls_Read.getCellValue(sheetName,"btn_leftarrow;xpath");
		driver.findElement(By.xpath(s1)).click();
		waitForSync(2);
		String type=xls_Read.getCellValue(sheetName,"btn_servicetype;xpath");
		String servicemode=type.replace("*",data(servicePoint));
		driver.findElement(By.xpath(servicemode)).click();
		waitForSync(2);
		String rightarrow=xls_Read.getCellValue(sheetName,"btn_rightarrow;xpath");
		driver.findElement(By.xpath(rightarrow)).click();
		
		
	}
	/**
	 * @author A-8783
	 * Desc - Verify status from tool tip
	 * @param awbNo
	 * @param checktype
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCHIconAndVerifyTooltip(String token,String checktype,String expText) throws InterruptedException, IOException
	{
		try{
			
		waitForSync(2);		
		String locator = xls_Read.getCellValue(sheetName, "div_chStatus;xpath");
		locator=locator.replace("*", data(token));
		moveScrollBar(driver.findElement(By.xpath(locator)));		
		waitForSync(3);	
		System.out.println(locator);
		driver.findElement(By.xpath(locator)).click();
		waitForSync(3);
		
		driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_statuscheck;xpath").replace("*", checktype))).click();
		waitForSync(1);
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_toolTip;xpath"))).getText();
		waitForSync(1);
		verifyScreenTextWithExactMatch(sheetName, expText, actText, "Verify "+checktype+" on ",screenName);
		driver.findElement(By.xpath(locator)).click();
	}
		
		catch(Exception e){
			
			writeExtent("Fail","Failed to verify the Status checks on "+screenName);	
			
	}

		
		
	}

	/**
	 * @author A-9847
	 * @Desc To click on CH Icon and verify the Status checks under Compliance section
	 * @param token
	 * @param checktype
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCHIconAndVerifyStatus(String token,String checktype,String expText) throws InterruptedException, IOException
	{
		try{
			
		waitForSync(2);		
		String locator = xls_Read.getCellValue(sheetName, "div_chStatus;xpath");
		locator=locator.replace("*", data(token));
		moveScrollBar(driver.findElement(By.xpath(locator)));		
		waitForSync(3);	
		System.out.println(locator);
		driver.findElement(By.xpath(locator)).click();
		waitForSync(3);
		
		String actText = driver.findElement(By.xpath(xls_Read.getCellValue(sheetName,"txt_statuscheck;xpath").replace("*", checktype))).getText();
		System.out.println(actText);
		verifyScreenTextWithExactMatch(sheetName, expText, actText, "Verify "+checktype+" on ",screenName);
		driver.findElement(By.xpath(locator)).click();
	}
		
		catch(Exception e){
			
			writeExtent("Fail","Failed to verify the Status checks on "+screenName);	
			
	}
	
		
		
	}
		
	/**
	 * @author A-10690
	 * Desc - Verify token priority
	 * @param TokenInbound
	 * @param TokenOutbound
	 */
	public void verifyTokenPriority(String TokenInbound, String TokenOutbound) {
	
		int inboundIndex=0;
		int outboundIndex=0;
	waitForSync(3);
		List<WebElement> tokens= driver.findElements(By.xpath(xls_Read.getCellValue(sheetName, "txt_token;xpath")));
		waitForSync(4);
		System.out.println(tokens.size());

		
		for(WebElement token:tokens) {
			
			
			
			if(token.getText().equals(data(TokenInbound))) {
				
				waitForSync(4);
				 inboundIndex = tokens.indexOf(token);
				 waitForSync(3);
			}
			
			else if(token.getText().equals(data(TokenOutbound))) {
				waitForSync(2);
				 outboundIndex = tokens.indexOf(token);
			}
		}
		
		if(inboundIndex<outboundIndex) {
			writeExtent("Pass", "Tokens are in correct order");
		}
		else {
			writeExtent("Fail", "Tokens are not in correct order");
		}
}


/**
	 * @author A-9844
	 * @param token
	 * @throws Exception
	 * Desc : freeing the dock if tokens are not displayed
	 */

	public void freeDockToken(String exptoken) throws Exception {
	
		
		try{

			String actText="";
			boolean found=false;
			enterValueInTextbox(sheetName, "inbx_searchBox;xpath",exptoken, "token", screenName);
			waitForSync(2);
			String locator = xls_Read.getCellValue(sheetName, "text_tokens;xpath");
			if((driver.findElements(By.xpath(locator)).size()==0)){
				found=false;
			}

			else
			{
				List <WebElement> elements= driver.findElements(By.xpath(locator));

				for(WebElement  elemt:elements){

					actText=elemt.getText();
					System.out.println(actText);

					if(actText.equals(exptoken)){
						found=true;
						break;
					}

				}
				
				
				writeExtent("Pass", "Expected token is not already callforwarded on " +screenName);
			}
			if(!found)
			{
				String locatorRight = xls_Read.getCellValue(sheetName, "text_servicePointtokens;xpath");
				locatorRight=locatorRight.replace("servicepoint",data("ServicePoint") );
				locatorRight=locatorRight.replace("Token",exptoken );
				waitForSync(3);
				if((driver.findElements(By.xpath(locatorRight)).size()>0)){

					expandServicePoint();
					clickServicePointOption(data("ServicePoint"), "val~Free");
					shrinkServicePoint();

				}

			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Expected token is not found on" +screenName);
			Assert.assertFalse(true, "Expected token is not found on" +screenName);
		}
			
	}
/**
	* @author A-10330
	* @param awbno[],count
	* @throws Exception
	* Desc : verify awbnos is displayed
	*/
 public void verifyMultiple_Awbno(String awbno[],int loopcount,String pmkey[]) throws Exception {
	String actText="";
	String expText="";
	
	for(int i=0;i<loopcount;i++){
	try
	{
	 String locator = xls_Read.getCellValue(sheetName, "htmldiv_awbnos;xpath");
	locator=locator.replace("*", pmkey[i]);

	actText=driver.findElement(By.xpath(locator)).getText();
	expText=awbno[i];
	
	verifyScreenTextWithExactMatch(sheetName, expText,actText , "awbno verification  of"+awbno[i]+"shipment of delivery token","Servicepointoverview");
	}
	catch(Exception e)
	{
		writeExtent("Fail",   ""+expText+" shipment is not verified for delivery token"+ screenName);	
	}
	
	}
	}
 /**
	* @author A-10330
	* @param statedpieces[],count,AWBNO[]
	* @throws Exception
* Desc : verify stated pieces is displayed
	*/
	public void verifyStated_Pieces(String statedPieces[],int loopcount,String AWBNO[],String pmkey[]) throws Exception {
	String actText="";
	String expText="";
	
	
	for(int i=0;i<loopcount;i++){
	try
	{   String locator = xls_Read.getCellValue(sheetName, "htmldiv_statedpcswgt;xpath");
		locator=locator.replace("*", pmkey[i]);
		actText=driver.findElement(By.xpath(locator)).getText();
		 expText=statedPieces[i];
		verifyScreenTextWithExactMatch(sheetName, expText,actText , "stated piecees verification of"+statedPieces[i]+" of " +AWBNO[i]+"","Servicepointoverview");
		
	}
	catch(Exception e)
	{
		writeExtent("Fail", "stated pieces  "+expText+"is not verified for"+AWBNO[i]+" shipment"+ screenName);		
		}
		
	    }
	 }
	   /**
		 * @author A-10330
		 * @param status,expcolor,AWBNO[],count
		 * @param status
		 * @throws InterruptedException
		 * @throws AWTException
		 * @Description : verify thedocumentReceivedStatus 
		 */
		public void verifyDocumentReceivedStatus(String status,String expColor,String AWBNO[],int loopcount,String pmkey[]) throws InterruptedException {
			String actColor = "";
			
			 
			for(int i=0;i<loopcount;i++)
			{
				String locator = xls_Read.getCellValue(sheetName, "htmldiv_documentstatus;xpath");
				locator=locator.replace("*",pmkey[i]);
				
			actColor=driver.findElement(By.xpath(locator)).getAttribute("class");
			System.out.println(actColor);

			switch (status){

			case "received" :
				if (actColor.contains("green"))
				{
					writeExtent("Pass", "Verified that the documents are received for the"+AWBNO[i]+"shipment on "+screenName); 
				}
				else
				{
					writeExtent("Fail", "Documents are not received for the "+AWBNO[i]+" shipemnts on  "+screenName); 	
				}
				break;

			case "notReceived" :
				if (actColor.contains("red"))
				{
					writeExtent("Pass", "Verified that the documents are not received for the"+AWBNO[i]+"shipment on"+screenName);
				}
				else 
				{
					writeExtent("Fail", "Documents are received for the"+AWBNO[i]+" shipemnts on"+screenName);
				}
				break;
			}
			  
			}
	    }
		/**
		 * @author A-10330
		 * @param status,expcolor,AWBNO[],count
		 * @param status
 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify thedeliverystatus
	 */
	public void verifyReadyForDeliveryStatus(String status,String expColor,String AWBNO[],int loopcount,String pmkey[]) throws InterruptedException {
		String actColor = "";
		
		 
		for(int i=0;i<loopcount;i++)
		{
		 String locator = xls_Read.getCellValue(sheetName, "htmldiv_documentstatus;xpath");	
		 locator=locator.replace("*", pmkey[i]);
		actColor=driver.findElement(By.xpath(locator)).getAttribute("class");
		System.out.println(actColor);

		switch (status){

		case "received" :
			if (actColor.contains("green"))
			{
				writeExtent("Pass", "Verified that the delivery status are received for the"+AWBNO[i]+"shipment on "+screenName); 
			}
			else
			{
				writeExtent("Fail", "delivery status are not received for the "+AWBNO[i]+" shipemnts on  "+screenName); 	
			}

			break;

		case "notReceived" :
			if (actColor.contains("red"))
			{
				writeExtent("Pass", "Verified that the delivery status are not received for the"+AWBNO[i]+"shipment on"+screenName);
			}
			else 
			{
				writeExtent("Fail", "delivery status are received for the"+AWBNO[i]+" shipemnts on"+screenName);
			}
			break;
		}
		  
		}
    }
	/**
	 * @author A-10330
	 * @param status,expcolor,AWBNO[],count
	 * @param status
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify flightArrivedStatus
	 */
	public void verifyFlightArrivedStatus(String status,String expColor,String AWBNO[],int loopcount,String pmkey[]) throws InterruptedException {
		String actColor = "";
		
		
		for(int i=0;i<loopcount;i++)
		{
			String locator = xls_Read.getCellValue(sheetName, "htmldiv_documentstatus;xpath");
			locator=locator.replace("*", pmkey[i]);
		    actColor=driver.findElement(By.xpath(locator)).getAttribute("class");
		   System.out.println(actColor);

		switch (status){

		case "received" :
			if (actColor.contains("green"))
			{
				writeExtent("Pass", "Verified that the flight arrived status are received for the"+AWBNO[i]+"shipment on "+screenName); 
			}
			else
			{
				writeExtent("Fail", "flight arrived status are not received for the "+AWBNO[i]+" shipemnts on  "+screenName); 	
			}

			break;

		case "notReceived" :
			if (actColor.contains("red"))
			{
				writeExtent("Pass", "Verified that the flight arrived status are not received for the"+AWBNO[i]+"shipment on"+screenName);
			}
			else 
			{
				writeExtent("Fail", "flight arrived status are received for the"+AWBNO[i]+" shipemnts on"+screenName);
			}
			break;
		}
		  
		}
    }
	/**
	 * @author A-8783 Desc - Click CH icon and verify the colour of the status
	 *         icon
	 * @param awbNo
	 * @param checktype
	 * @param expText
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void clickCHIconAndVerifyColour(String token, String checktype, String expText)
			throws InterruptedException, IOException {
		String expClr = "";
		
		try {
			if (expText.equals("Green")) {
				expClr = "rgba(88, 175, 46, 1)";
			} else if (expText.equals("Red")) {
				expClr = "rgba(219, 68, 61, 1)";
			}
			waitForSync(2);
			String locator = xls_Read.getCellValue(sheetName, "div_chStatus;xpath");
			locator = locator.replace("*", data(token));
			System.out.println(locator);
			moveScrollBar(driver.findElement(By.xpath(locator)));
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);

			String actClr = driver
					.findElement(
							By.xpath(xls_Read.getCellValue(sheetName, "txt_statuscheck;xpath").replace("*", checktype)))
					.getCssValue("color");
			System.out.println(actClr);
			verifyScreenTextWithExactMatch(sheetName, expClr, actClr, "Verify colour as " + expText + " on ",
					screenName);
			driver.findElement(By.xpath(locator)).click();
		}

		catch (Exception e) {

			writeExtent("Fail", "Failed to verify the Status colour as " + expText + " on " + screenName);

		}
	

		
	}

	/**
	 * @author A-10330
	 * @param status,expcolor,AWBNO[],count
	 * @param status
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify BreakDownStatus
	 */
	public void verifyBreakDownStatus(String status,String expColor,String AWBNO[],int loopcount,String pmkey[]) throws InterruptedException {
		String actColor = "";
		
		
		for(int i=0;i<loopcount;i++)
		{   
			String locator = xls_Read.getCellValue(sheetName, "htmldiv_documentstatus;xpath");
			locator=locator.replace("*", pmkey[i]);
		    actColor=driver.findElement(By.xpath(locator)).getAttribute("class");
		    System.out.println(actColor);

		switch (status){

		case "received" :
			if (actColor.contains("green"))
			{
				writeExtent("Pass", "Verified that the BreakDown completed status are received for the"+AWBNO[i]+"shipment on "+screenName); 
			}
			else
			{
				writeExtent("Fail", "BreakDown completed status are not received for the "+AWBNO[i]+" shipemnts on  "+screenName); 	
			}

			break;

		case "notReceived" :
			if (actColor.contains("red"))
			{
				writeExtent("Pass", "Verified that the BreakDown completed status are not received for the"+AWBNO[i]+"shipment on"+screenName);
			}
			else 
			{
				writeExtent("Fail", "BreakDown completed status are received for the"+AWBNO[i]+" shipemnts on"+screenName);
			}
			break;
		}
		  
		}
    }
	/**
	 * @author A-10690
	 * Desc - Verify LAT value displayed
	 * @param token number
	 * @param LAT details
	 * @throws InterruptedException
	 * @throws AWTException 
	 */
	public void verifyLATVlaue(String token,String LATData) throws InterruptedException{
		
		String locator=xls_Read.getCellValue(sheetName, "txt_LATdate;xpath");
		
	
		locator=locator.replace("*",data(token));
		String locator1=xls_Read.getCellValue(sheetName, "txt_LATtime;xpath");
		
		
		locator1=locator1.replace("*",data(token));

		String Date = driver.findElement(By.xpath(locator)).getText();
		String[] actLATDate=Date.split(":");
		String actDate=actLATDate[1].trim();
		String actTime = driver.findElement(By.xpath(locator1)).getText();
		String[] LATValues=data(LATData).split(" ");
		if((actDate.equals(LATValues[1]))&&(actTime.equals(LATValues[0])))
		{
			writeExtent("Pass","Successfully verified LAT details on  "+screenName);
		}
		else{
			writeExtent("Fail","Successfully verified LAT details on "+screenName);
		}
		
	}	
	/**
	 * @author A-9844
	 * @param token
	 * @throws Exception
	 * Desc : verify token is displayed
	 */

	public void verifyTokenIsDisplayed(String exptoken) throws Exception {
	
		String actText="";
		boolean found=false;
		enterValueInTextbox(sheetName, "inbx_searchBox;xpath",data(exptoken), "token", screenName);
		waitForSync(2);
		String locator = xls_Read.getCellValue(sheetName, "text_tokens;xpath");
		if((driver.findElements(By.xpath(locator)).size()==0)){

			found=false;
			map.put("tokenInWaitingArea", "NA");
		}

		else
		{
			List <WebElement> elements= driver.findElements(By.xpath(locator));
			map.put("tokenInWaitingArea", "NA");

			for(WebElement  elemt:elements){

				actText=elemt.getText();
				System.out.println(actText);

				if(actText.equals(data(exptoken))){
					found=true;
					map.put("tokenInWaitingArea", "true");
					break;
				}

			}
		}
		if(!found)
		{
			String locatorRight = xls_Read.getCellValue(sheetName, "text_servicePointtokens;xpath");
			locatorRight=locatorRight.replace("servicepoint",data("ServicePoint") );
			locatorRight=locatorRight.replace("Token",data(exptoken) );
			waitForSync(3);
			if((driver.findElements(By.xpath(locatorRight)).size()>0)){

				map.put("tokenInWaitingArea", "false");
				found=true;	
			}

		}
		if(found)

			writeExtent("Pass", "Successfully verified the token "+data(exptoken)+" on" +screenName);

		else{
			writeExtent("Fail", "Failed verify the token "+data(exptoken)+" on" +screenName);
			Assert.assertFalse(true, "Failed verify the token "+data(exptoken)+" on" +screenName);
		}
	
}



/**
	 * @author A-9844
	 * Desc - Click expand arrow
	 * @throws InterruptedException
	 */
	public void expandServicePoint() throws InterruptedException {
		clickWebElementByWebDriver(sheetName, "btn_expand;xpath", "Expand Button",screenName);
		waitForSync(2);
	}
	/**
	 * @author A-9844
	 * Desc - Click on more option and select the option
	 * @param servicePoint
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public void clickServicePointOption(String servicePoint, String option) throws InterruptedException, IOException {
		try {
		String locatorOption = xls_Read.getCellValue(sheetName, "btn_moreOption;xpath");
		locatorOption = locatorOption.replace("servicePoint", servicePoint);
		waitForSync(1);
		driver.findElement(By.xpath(locatorOption)).click();
		writeExtent("Pass", "Clicked on more options button on "+screenName);
		}
		catch(Exception e) {
			writeExtent("Fail", "Could not click on more options button on "+screenName);
		}
		try {
		String locator = xls_Read.getCellValue(sheetName, "btn_servicePointOption;xpath");
		locator = locator.replace("*", data(option));
		locator = locator.replace("servicePoint", servicePoint);
		waitForSync(1);
		driver.findElement(By.xpath(locator)).click();
		waitForSync(3);
		writeExtent("Pass", "Clicked on "+data(option)+" option on "+screenName);
		}
		catch(Exception e) {
			writeExtent("Fail", "Could not click on option "+data(option)+" on "+screenName);
		}
		waitForSync(2);

		while(driver.findElements(By.xpath("//button[text()='Ok']")).size() >0)
		{

		clickWebElement(sheetName, "btn_ok;xpath", "Ok Button", screenId);
		waitForSync(7);
		}	
		

		
	}
	/**
	 * @author A-9844
	 * Desc - Click shrink button
	 * @throws InterruptedException
	 */
	public void shrinkServicePoint() throws InterruptedException {
		clickWebElementByWebDriver(sheetName, "btn_shrink;xpath", "Shrink Button",screenName);
		waitForSync(4);
	}

	/**
	 * @author A-9844
	 * @param token
	 * @throws Exception
	 * Desc : freeing the counter if tokens are not displayed
	 */

	public void freeCounterToken(String exptoken) throws Exception {
	
		try{

			String actText="";
			boolean found=false;
			enterValueInTextbox(sheetName, "inbx_searchBox;xpath",exptoken, "token", screenName);
			waitForSync(2);
			String locator = xls_Read.getCellValue(sheetName, "text_tokens;xpath");
			if((driver.findElements(By.xpath(locator)).size()==0)){

				found=false;
			}

			else
			{
				List <WebElement> elements= driver.findElements(By.xpath(locator));

				for(WebElement  elemt:elements){

					actText=elemt.getText();
					System.out.println(actText);

					if(actText.equals(exptoken)){
						found=true;
						break;
					}

				}
				
				writeExtent("Pass", "Expected token is not already callforwarded on " +screenName);
			}
			if(!found)
			{
				String locatorRight = xls_Read.getCellValue(sheetName, "text_servicePointtokens;xpath");
				locatorRight=locatorRight.replace("servicepoint",data("ServicePoint") );
				locatorRight=locatorRight.replace("Token",exptoken );
				waitForSync(3);
				if((driver.findElements(By.xpath(locatorRight)).size()>0)){

					expandServicePoint();
					clickServicePointOption(data("ServicePoint"), "val~Free");
					shrinkServicePoint();

				}

			}
		}
		catch (Exception e) {
			writeExtent("Fail", "Expected token is not found on" +screenName);
			Assert.assertFalse(true, "Expected token is not found on" +screenName);
		}


			
	}


/**
	 * @author A-9844
	 * @param exptoken
	 * @param expColor
	 * @throws Exception
	 * Desc : verify default token color
	 */

	public void verifyDefaultTokenColor(String exptoken,String expColor) throws Exception {

		freeCounterToken(data(exptoken));
		waitForSync(5);
		String actText="";
		String actColor="";
		boolean found=false;
		enterValueInTextbox(sheetName, "inbx_searchBox;xpath",data(exptoken), "token", screenName);
		waitForSync(2);
		String locator = xls_Read.getCellValue(sheetName, "text_tokens;xpath");
		if((driver.findElements(By.xpath(locator)).size()==0)){
			found=false;
		}

		else
		{
			List <WebElement> elements= driver.findElements(By.xpath(locator));

			for(WebElement  elemt:elements){

				actText=elemt.getText();
				System.out.println(actText);

				if(actText.equals(data(exptoken))){
					found=true;
					break;
				}

			}
		}

		if(found){

			writeExtent("Pass", "Successfully verified the token "+data(exptoken)+" on " +screenName);
			String ele1 = xls_Read.getCellValue(sheetName, "txt_defaultokenColour;xpath").replace("*",data(exptoken));
			System.out.println(ele1);
			waitForSync(2);
			actColor=driver.findElement(By.xpath(ele1)).getAttribute("class");
			System.out.println(actColor);
			if (actColor.contains(data(expColor)) && !actColor.contains("green"))
				writeExtent("Pass", "Verified the default color of the token as "+data(expColor)+"  on "+screenName); 

		}

		else
		{

			writeExtent("Fail", "Failed verify the default token colour on " +screenName+" Actual color displayed is "+actColor);

		}
	}


	/**
	 * @author A-9844
	 * @param exptoken
	 * @throws IOException,InterruptedException
	 * Desc : click delivery purpose
	 */


	public void clickDeliveryPupose(String exptoken) throws InterruptedException, IOException {

		String locator = xls_Read.getCellValue(sheetName, "txt_deliveryPurpose;xpath").replace("*",data(exptoken));
		driver.findElement(By.xpath(locator)).click();
		waitForSync(3);
		clickWebElement(sheetName,"txt_deliveryIcon;xpath","delivery expand icon", screenName);
		waitForSync(3);
	}

/**
	 * @author A-9844
	 * @Desc To verify the awb number
	 * @param awbNo
	 */
	public void verifyAWBNumber(String awbNo){
		try{
			String actstatus=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_awbNumber;xpath"))).getText();
			System.out.println(actstatus);
			if(actstatus.contains(data(awbNo)))
				writeExtent("Pass", "Successfully verified the awb number as " +(data(awbNo))+" on"+screenName);	
			else
				writeExtent("Fail", "Failed to verify the awb number " +screenName);
		}catch(Exception e){
			writeExtent("Fail", "Failed to verify the awb number " +screenName);
		}

	}
	/**
	 * @author A-9844
	 * @param expText
	 * @param status
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify the color of the  document status
	 */
	public void verifyDocumentReceivedStatus(String status,String expColor) throws InterruptedException {
		try{
			
			String actColor = "";
			String ele1 = xls_Read.getCellValue(sheetName, "btn_documentStatus;xpath");
			actColor=driver.findElement(By.xpath(ele1)).getAttribute("class");
			System.out.println(actColor);

			switch (status){

			case "received" :
				if (actColor.contains("green"))
				{
					writeExtent("Pass", "Verified that the documents are received for the shipment on "+screenName); 
				}
				else
				{
					writeExtent("Fail", "Documents are not received for the shipemnts on  "+screenName); 	
				}

				break;

			case "notReceived" :
				if (actColor.contains("red"))
				{
					writeExtent("Pass", "Verified that the documents are not received for the shipment on "+screenName);
				}
				else 
				{
					writeExtent("Fail", "Documents are received for the shipemnts on "+screenName);
				}
				break;
			}
			
			}
			catch (Exception e) {
				writeExtent("Fail", "Could not verify document received status on "+screenName);
			}


		}

	/**
	 * @author A-9844
	 * @param expColor
	 * @param status
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : verify the delivery status
	 */
	public void verifyReadyForDeliveryStatus(String status,String expColor) throws InterruptedException {

		try{
			String actColor = "";
			String ele1 = xls_Read.getCellValue(sheetName, "btn_deliveryStatus;xpath");
			actColor=driver.findElement(By.xpath(ele1)).getAttribute("class");
			System.out.println(actColor);

			switch (status){

			case "readyForDelivery" :
				if (actColor.contains("green"))
				{
					writeExtent("Pass", "Verified that the shipments are in Ready For Delivery status on  "+screenName); 
				}
				else
				{
					writeExtent("Fail", "Shipments are in Not Ready for Delivery status on  "+screenName); 	
				}

				break;

			case "notReadyForDelivery" :
				if (actColor.contains("red"))
				{
					writeExtent("Pass", "Verified that the shipments are in Not Ready For Delivery status on "+screenName); 
				}
				else 
				{
					writeExtent("Fail", "Shipments are in Ready for Delivery status on  "+screenName);
				}
				break;
			}
			}
			catch (Exception e) {
				writeExtent("Fail", "Could not verify Ready for Delivery status on  "+screenName);
			}

		}
	/**
	 * @author A-9844
	 * @Desc To verify the SCC
	 * @param exptoken
	 * @param SCC
	 */
	public void verifySCC(String exptoken, String SCC){
		try{
			String locator = xls_Read.getCellValue(sheetName, "txt_scc;xpath").replace("*",data(exptoken));
			String actSCC = driver.findElement(By.xpath(locator)).getText();
			if(actSCC.contains(data(SCC)))
				writeExtent("Pass", "Successfully verified the SCC on " +screenName);	
			else
				writeExtent("Fail", "Failed to verify the SCC on " +screenName);
		}catch(Exception e){
			writeExtent("Fail", "Failed to verify the SCC on " +screenName);
		}

	}
	/**
	 * @author A-9844
	 * @throws IOException
	 * Desc : verify popup closure
	 */

	public void verifyPopupClosure() throws InterruptedException, IOException {


		String s1=xls_Read.getCellValue(sheetName,"btn_rightarrow;xpath");
		driver.findElement(By.xpath(s1)).click();
		waitForSync(2);

		String locator = xls_Read.getCellValue(sheetName, "btn_leftarrow;xpath");

		if((driver.findElements(By.id(locator)).size()==0)){

			writeExtent("Pass","Successfully verified the popup is closed on "+screenName);
		}
		else
		{
			writeExtent("Fail","The popup is not closed on "+screenName);	
		}


	}
	/**
	 * @author A-9844
	 * @throws IOException
	 * @param exptoken
	 * Desc : verify delivery popup closure on clicking out
	 */

	public void verifyDeliveryPopupClosure(String exptoken) throws InterruptedException, IOException {

		waitForSync(5);
		String locator = xls_Read.getCellValue(sheetName, "txt_deliveryPurpose;xpath").replace("*",data(exptoken));
		driver.findElement(By.xpath(locator)).click();
		waitForSync(3);
		String locator1 = xls_Read.getCellValue(sheetName, "txt_deliveryIcon;xpath");
		Boolean isPresent=driver.findElement(By.xpath(locator1)).isDisplayed();

		if(isPresent)
		{
			writeExtent("Pass", "Verified that the delivery popup is opened on  "+screenName); 
			driver.findElement(By.xpath(locator)).click();
			waitForSync(3);
			if((driver.findElements(By.id(locator1)).size()==0))
			{
				writeExtent("Pass", "Verified that the delivery popup is closed on  "+screenName);  
			}
			else{
				writeExtent("Fail", "Delivery popup is open on  "+screenName);  
			}

		}
		else
		{
			writeExtent("Fail", "Delivery popup is closed on "+screenName); 	
		}
	}


	/**
	 * @author A-10690
	 * @param startdate(currentdate)
	 * @throws IOException,InterruptedException
	 * Desc : listing token based on date
	 */
	
	
public void listToken(String startDate) throws InterruptedException, IOException {
	
	
	
	clickWebElement(sheetName,"btn_datefilter;xpath","date filter", screenName);
	waitForSync(2);
	enterValueInTextbox(sheetName, "inbx_fromdate;name",data(startDate), "fromdate", screenName);
	waitForSync(2);
	enterValueInTextbox(sheetName, "inbx_todate;name",data(startDate), "todate", screenName);
	clickWebElement(sheetName,"btn_applybtn;id", "apply Button", screenName);
		
	}
	
	
	
	
/**
 * @author A-10690
 * @param token
 * @throws InterruptedException
 * Desc : search key
 */

	
	public void searchkey(String token) throws InterruptedException {
		By ele = getElement(sheetName, "btn_search;xpath");
		enterValueInTextbox(sheetName, "btn_search;xpath",data(token), "token", screenName);
	}
	
	
	/**
	 * @author A-10690
	 * @param column
	 * @throws InterruptedException
	 * Desc : select column
	 */
	
	public void selectColumn(String column) throws InterruptedException, IOException {
		
		clickWebElement(sheetName,"btn_settingsicon;id","column choser", screenName);
		String col=xls_Read.getCellValue(sheetName, "btn_selectcolumn;xpath").replace("*",data(column));
		if(!driver.findElement(By.xpath(col)).isSelected())
		driver.findElement(By.xpath(col)).click();
		clickWebElement(sheetName,"btn_save;id", "save Button", screenName);

	}
	
	
	/**
	 * @author A-10690
	 * @param column
	 * @throws InterruptedException,IOException
	 * Desc : removing the column selection
	 */
	
	public void removeColumnSelection(String column) throws InterruptedException, IOException {
		
		clickWebElement(sheetName,"btn_settingsicon;id", "column choser", screenName);
		String col=xls_Read.getCellValue(sheetName, "btn_selectcolumn;xpath").replace("*",data(column));
		if(driver.findElement(By.xpath(col)).isSelected())
		{
			driver.findElement(By.xpath(col)).click();
		}
		
		clickWebElement(sheetName,"btn_save;id", "arrow button Button", screenName);
		}
		
	
	
	/**
	 * @author A-10690
	 * @param token
	 * @param displaymode
	 * @throws Exception
	 * Desc : verifying  whether the column is displayed or not
	 */
	
	public void verifyColumnDisplayed(String token,boolean displayMode) throws Exception {
		
	       
		waitForSync(2);
		String column=	xls_Read.getCellValue(sheetName, "txt_scccolumn;xpath").replace("*",data(token));
		System.out.println(column);

			
				if(displayMode)
				{
					verifyElementDisplayed( column,"verify column is dispayed" , screenName,"column");
						
				}
				else
				{
					verifyElementNotDisplayed(sheetName, column, "verify column not dispayed", screenName, "column");
							
				}
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	
	/**
	 * @author A-9847
	 * Desription : List the flight details
	 * @param carrierCode
	 * @param flightNumber
	 * @param fromDate
	 * @param toDate
	 * @throws Exception 
	 */
	public void EnterFlightDetails(String carrierCode,String flightNumber,String fromDate,String toDate) throws Exception
	{	
		enterValueInTextbox(sheetName, "inbx_carrierCode;name",data(carrierCode), "carrierCode", screenName);
		enterValueInTextbox(sheetName, "inbx_flightNumber;name",data(flightNumber), "flightNumber", screenName);
		enterValueInTextbox(sheetName, "inbx_fromdate;name",data(fromDate), "From date", screenName);
		enterValueInTextbox(sheetName, "inbx_fromtime;name","00:00", "From Time", screenName);
		enterValueInTextbox(sheetName, "inbx_todate;name",data(toDate), "To Date", screenName);
		enterValueInTextbox(sheetName, "inbx_totime;name","23:59", "To Time", screenName);
		
	}
	
	
/**
 * @author A-9847
 * Description... Click List Button	
 * @throws InterruptedException
 * @throws IOException 
 */
	public void clickList() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "btn_list;id", "List Button",screenName);
		waitForSync(5);
	}
	 
	/**
	 * @author A-9847
	 * @Desc To verify the flight
	 * @param flight
	 */
	public void verifyFlight(String flight) {
		By ele = getElement(sheetName, "txt_flight;xpath");
		String expFlight = driver.findElement(ele).getText();
		System.out.println(expFlight);
		verifyScreenText(sheetName, expFlight, data(flight), "Verify Flight", screenName);
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the Plan Status
	 * @param status
	 */
	public void verifyPlanStatus(String status){
		try{
		String expstatus=driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "txt_planStatus;xpath"))).getText();
		System.out.println(expstatus);
		if(expstatus.equals(data(status)))
			writeExtent("Pass", "Successfully verified Plan Status as 'Completed' on " +screenName);	
		else
			writeExtent("Fail", "Failed to verify Plan Status as 'Completed' on " +screenName);
		}catch(Exception e){
			writeExtent("Fail", "Failed to verify the Plan Status on " +screenName);
		}
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To verify the flight Pouch Icon is in received Status
	 */
	
	public void verifyFlightPouchIcon(){
		
		try{
		if(driver.findElement(By.xpath(xls_Read.getCellValue(sheetName, "lbl_flightPouchIcon;xpath"))).isDisplayed())
			writeExtent("Pass", "Successfully verified Flight Pouch Icon in Received Status on " +screenName);	
		else	
			writeExtent("Fail", "Failed to verify Flight Pouch Icon on " +screenName);
		}catch(Exception e){
			writeExtent("Fail", "Failed to verify Flight Pouch Icon in Received Status on " +screenName);
		}	
	}
	
	/**@author A-9847
	 * @Desc To verify the FFM Indicator
	 */
	public void verifyFFMIndicator(){
		
		
	}
	
	
	
	
	

}
