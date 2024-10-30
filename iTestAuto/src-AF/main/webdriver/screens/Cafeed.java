package screens;


import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class Cafeed extends CustomFunctions {

	String sheetName = "cafeed_screen";
	String screenName = "Cafeed";


	public Cafeed(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);


	}
	MaintainOperationalFlight_FLT003 FLT003 = new MaintainOperationalFlight_FLT003(driver, excelreadwrite, xls_Read);
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click search flight link
	 */
	public void clickSearchFlightLink() throws InterruptedException, IOException
	{
		clickWebElement(sheetName, "lnk_flightList;xpath", "List flight link", screenName);
		waitForSync(3);
	}
	/**
	 * @author A-10690
	 * @param expected UNID number,PI number,shipping name
	 * @throws Exception
	 * Desc : verify PI,UNID,shippingname on clicking an awb inside a ULD
	 */
	public void verifyDGDetails(String UNID,String PI,String Shippingname) throws Exception
	{
		try
		{

				switchToWindow("storeParent");
				waitForSync(5);
				switchToWindow("child");
				driver.switchTo().frame("popUpFrame");
				String actunid=getAttributeWebElement(sheetName, "txt_unidno;xpath", "UNID", "value", screenName);
				String actPI=getAttributeWebElement(sheetName, "txt_PI;xpath", "PI", "value", screenName);
				String actshippingname=getAttributeWebElement(sheetName, "txt_shippingname;xpath", "Shippingname", "value", screenName);
				if((actunid.equals(data(UNID)))&&(actPI.equals(data(PI)))&&(actshippingname.equals(data(Shippingname))))
				{
					writeExtent("Pass","Successfully verified the DG details"+screenName);
					
				}
				else
				{
				writeExtent("Fail"," not  verified the DG details"+screenName);
				}
				
				
				switchToWindow("closeChild");
				waitForSync(4);
				switchToWindow("getParent");
							

	}catch(Exception e)
		{
		writeExtent("Fail","Failed to verify DG details "+screenName);	
		}
	}
	/** * @author A-10330
	 * @param uldNo
	 * @throws Exception
	 * Desc : verify ULD Offloaded
	 */
	public void verifyUldOffloaded(String uldNo) throws Exception
	{

		try
		{

			String uldCount=xls_Read.getCellValue(sheetName, "btn_clickULD;xpath");
			uldCount=uldCount.replace("*", data(uldNo));

			if(driver.findElements(By.xpath(uldCount)).size()==0)
			{
				writeExtent("Pass","Successfully verified "+data(uldNo) +"removed from a Flight on "+screenName);
			}



		}
		catch (Exception e)
		{
			writeExtent("Fail","couldnt verified the  Uld Offloaded from Flight "+screenName);
		}

	}

    /**
    * @Desc:Accepting the alert in the cafeed pop up window and switch to child popup
    * @throws Exception 
     */          

    public void  acceptCafeedAlertSwitchtoChildPopup() throws Exception
    {

    	for(String win:driver.getWindowHandles())
    	{

    		String url=           driver.switchTo().window(win).getCurrentUrl();
    		if((url.contains("cafeed"))&&url.contains("pop"))
    		{
    			driver.switchTo().window(win);
    			waitForSync(1);
    			clickWebElement(sheetName, "btn_flightok;xpath", "clicking OK button", screenName);
    			waitForSync(2);
    		}
    	}

    	switchToWindow("getFirstChild");
    	switchToFrame("frameName","popUpFrame");
    }
    

	/**
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Verify the APIO numbers in cafeed
	 */
	public void verifyAPIONumber(String APIONo) throws InterruptedException, AWTException {

		try{

			switchToWindow("storeParent");
			waitForSync(5);
			switchToWindow("child");
			driver.switchTo().frame("popUpFrame");
			String AllPackedNo=xls_Read.getCellValue(sheetName, "txt_APIONo;xpath");
			AllPackedNo=AllPackedNo.replace("*",APIONo);

			if((driver.findElements(By.xpath(AllPackedNo)).size())==1){

				writeExtent("Pass","Successfully verified the APIO/OVP Number"+APIONo);

			}else
			{
				writeExtent("Fail","failed to verify the APIO/OVP Number"+APIONo);
			}

			switchToWindow("closeChild");
			waitForSync(4);
			switchToWindow("getParent");
		}

		catch(Exception e)
		{
			writeExtent("Fail","Failed to verify the APIO/OVP Number");
		}
	}



	/**
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Verify SCC got updated in cafeed for the respective ULD
	 */
	public void verifyScc(String scc,String ULD) throws InterruptedException, AWTException {

		try
		{
			String locator = xls_Read.getCellValue(sheetName, "txt_scc;xpath");
			locator=locator.replace("ULD", data(ULD));
			locator=locator.replace("*", data(scc));

			if((driver.findElements(By.xpath(locator)).size()==1)){

				writeExtent("Pass","Successfully verified the SCC "+data(scc)+" in cafeed");
				
				
			}else
			{
				writeExtent("Fail","failed to verify the SCC "+data(scc)+" in cafeed");
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","failed to verify the SCC "+data(scc)+" in cafeed");
		}
	}

	/**A-10690
	 * For creating new flight in cafeed,Checking fresh flight in cafeed and icargo
	 * @param Flighttype
	 * @param FlightNumber
	 * @param flightStartDate
	 * @param date  for listing in FLT003
	 * @throws Exception 
	 */

	public void createnewFlightInCafeedwindow(String flightNumber,String flightDate,String flightType,String date ) throws Exception {


		int flag=1;

		/** Login to iCargo STG**/
		String [] iCargo=getApplicationParams("iCargoSTG");
		WebDriver driver=relaunchBrowser("chrome");
		driver.get(iCargo[0]);
		Thread.sleep(2000);
		loginICargoSTG(iCargo[1], iCargo[2]);
		Thread.sleep(2000);	
		
		// Switch role
					switchRole("Origin", "FCTL", "RoleGroup");
		/***Login to cafeed**/
		String[] cafeed = getApplicationParams("cafeed");
		String url="\'"+cafeed[0]+"\'"+",'_blank'";	
		launchUrlInTab(url);
		Thread.sleep(2000);
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));  
		loginToCafeed(cafeed[1], cafeed[2]);
		
		Thread.sleep(2000);

		while(flag==1)
			
		{
			setPropertyValue("flightNumber",data("carrierCode")+data("prop~flightNo"),proppath);
			flightNumber="prop~flightNumber";

			/***Taking fresh flight from cafeed**/
			listnewFlightDetails(flightNumber,flightDate,flightType);
			switchToTab("icargo");

			/**list flight in flt003**/
			searchScreen("FLT003", "Maintain Operational Flight");
			FLT003.listnewFlight("carrierCode","prop~flightNo", date);
			waitForSync(2);
			switchToFrame("default");
			/**Checking the flight is new in icargo***/
			int size=driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
			if(size==1)
			{
				flag=0;
				driver.close();
				switchToTab("cafeed");
				/***Create flight in cafeed if the flight is new in cafeed and icargo**/
				createFlight(flightNumber,flightDate);
			}
			else
			{
				closeTab("FLT003", "Maintain Operational Flight");
				switchToTab("cafeed");
				createFlight(flightType);

			}
		}

	}



	/**
	 * @author A-10690
	 * @param flightNumber
	 * @param flightDate
	 * @param flighttype
	 * @throws Exception 
	 */
	public void listnewFlightDetails(String flightNumber,String flightDate,String flightType ) throws Exception
	{
		searchFlghtBtnClick();
		flightNumber="prop~flightNumber";
		listFlightDetails(flightNumber, flightDate);
		waitForSync(1);
		try
		{

			String uldsize=xls_Read.getCellValue(sheetName, "txt_uldAvailable;xpath");
			int size=driver.findElements(By.xpath(uldsize)).size();
			while(size==1)
			{
				createFlight(flightType);
				searchFlghtBtnClick();
				waitForSync(1);
				clickWebElement(sheetName, "btn_reset;xpath", "List flight link", screenName);
				listFlightDetails("prop~flightNumber", flightDate);
				size=driver.findElements(By.xpath(uldsize)).size();
				waitForSync(1);
			}
			waitForSync(1);
			dismissCafeedAlertPopup();


		}

		catch(Exception e)
		{

		}
	}
	/**
	 * @author A-10330
	 * @Desc Creating new flight in the cafeed
	 * @Desc created overloaded method changed method param
	 * @param flightnumber
	 * @param flightdate
	 * @param Origin Destination ATD ATA
	 * @throws Exception
	 */
	public void createFlight(String flightnumber,String flightDate,String Origin,String Destination,String ATD,String ATA ) throws Exception
	{

		listFlightDetails(flightnumber, flightDate);
		acceptCafeedAlertPopup();
		waitForSync(1);
		enterValueInTextbox(sheetName, "txt_origin;id", data(Origin),"origin", screenName);
		enterValueInTextbox(sheetName, "txt_destination;id", data(Destination),"destination", screenName);
		enterValueInTextbox(sheetName, "txt_departuretime;id", data(ATD),"Departure time", screenName);
		enterValueInTextbox(sheetName, "txt_arrivaltime;id", data(ATA),"Arrival time", screenName);
		enterValueInTextbox(sheetName, "txt_aircrafttype;id", data("AircraftType"),"aircrafttype", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "btn_save;xpath", "clicking save button", screenName);
		waitForSync(1);
		driver.quit();
		
	}
	/**A-10330
	 * For creating new flight in cafeed,Checking fresh flight in cafeed and icargo
	 * created overloaded method passed new params into the method
	 * @param Flighttype
	 * @param FlightNumber
	 * @param flightStartDate
	 * param Origin,Dest,ATD,ATA
	 * @param date  for listing in FLT003
	 * @throws Exception 
	 */

public void createnewFlightInCafeedwindow(String flightNumber,String flightDate,String flightType,String date,String Origin,String Dest,String ATD,String ATA ) throws Exception {


	int flag=1;

      /** Login to iCargo STG**/
	String [] iCargo=getApplicationParams("iCargoSTG");
	WebDriver driver=relaunchBrowser("chrome");
	driver.get(iCargo[0]);
	Thread.sleep(2000);
	loginICargoSTG(iCargo[1], iCargo[2]);
	Thread.sleep(2000);	
	
      // Switch role
	switchRole("Origin", "FCTL", "RoleGroup");
	/***Login to cafeed**/
	String[] cafeed = getApplicationParams("cafeed");
	String url="\'"+cafeed[0]+"\'"+",'_blank'";	
	launchUrlInTab(url);
	Thread.sleep(2000);
	ArrayList<String> tabs = new ArrayList<String>               (driver.getWindowHandles());
	driver.switchTo().window(tabs.get(1));  
	loginToCafeed(cafeed[1], cafeed[2]);
	
	Thread.sleep(2000);

	while(flag==1)
		
	{
		setPropertyValue("flightNumber",data("carrierCode")+data("prop~flightNo"),proppath);
		flightNumber="prop~flightNumber";

		/***Taking fresh flight from cafeed**/
		listnewFlightDetails(flightNumber,flightDate,flightType);
		switchToTab("icargo");

		/**list flight in flt003**/
		searchScreen("FLT003", "Maintain Operational Flight");
		FLT003.listnewFlight("carrierCode","prop~flightNo", date);
		waitForSync(2);
		switchToFrame("default");
		/**Checking the flight is new in icargo***/
		int size=driver.findElements(By.xpath(xls_Read.getCellValue("Generic_Elements", "btn_yes;xpath"))).size();
		if(size==1)
		{
			flag=0;
			driver.close();
			switchToTab("cafeed");
			/***Create flight in cafeed if the flight is new in cafeed and icargo**/
			createFlight(flightNumber,flightDate,Origin,Dest,ATD,ATA);
		}
		else
		{
			closeTab("FLT003", "Maintain Operational Flight");
			switchToTab("cafeed");
			createFlight(flightType);

		}
	}
  }


	/**
	 * @author A-10690
	 * @Desc Creating new flight in the cafeed
	 * @param flightnumber
	 * @param flightdate
	 * @throws Exception
	 */

	public void createFlight(String flightnumber,String flightDate) throws Exception
	{

		listFlightDetails(flightnumber, flightDate);
		acceptCafeedAlertPopup();
		waitForSync(1);
		enterValueInTextbox(sheetName, "txt_origin;id", data("Origin"),"origin", screenName);
		enterValueInTextbox(sheetName, "txt_destination;id", data("Destination"),"destination", screenName);
		enterValueInTextbox(sheetName, "txt_departuretime;id", data("ATD_Local"),"Departure time", screenName);
		enterValueInTextbox(sheetName, "txt_arrivaltime;id", data("ATA_Local"),"Arrival time", screenName);
		enterValueInTextbox(sheetName, "txt_aircrafttype;id", data("AircraftType"),"aircrafttype", screenName);
		waitForSync(1);
		clickWebElement(sheetName, "btn_save;xpath", "clicking save button", screenName);
		waitForSync(1);
		driver.quit();


	}
	

	/**
	 * @author A-10690
	 * @desc:Clicking search flight button and dismiss if any alert pop up is coming
	 * @throws Exception 
	 */
	public void searchFlghtBtnClick() throws Exception
	{
		clickWebElement(sheetName, "lnk_flightList;xpath", "List flight link", screenName);
		waitForSync(3);
		dismissCafeedAlertPopup();

	}


	/**
	 * @author A-10690
	 * @Desc:Accepting the alert in the cafeed pop up window
	 * @throws Exception 
	 */	

	public void  acceptCafeedAlertPopup() throws Exception
	{

		switchToWindow("storeParent");

		//Set<String> handles=driver.getWindowHandles();
		//List<String>hl=new ArrayList(handles);
		for(String win:driver.getWindowHandles())
		{

			String url=	driver.switchTo().window(win).getCurrentUrl();
			if((url.contains("cafeed"))&&url.contains("pop"))
			{
				driver.switchTo().window(win);
				waitForSync(1);
				clickWebElement(sheetName, "btn_flightok;xpath", "clicking OK button", screenName);
				waitForSync(2);
			}
		}
		
		switchToWindow("getParent");

	}

	/**
	 * @author A-10690
	 * @Desc:Dismissing the alert in the cafeed pop up window
	 * @throws Exception 
	 */	

	public void  dismissCafeedAlertPopup() throws Exception
	{

		switchToWindow("storeParent");

		//Set<String> handles=driver.getWindowHandles();
		//List<String>hl=new ArrayList(handles);
		for(String win:driver.getWindowHandles())
		{


			String url=	driver.switchTo().window(win).getCurrentUrl();
			if((url.contains("cafeed"))&&url.contains("pop"))
			{
				driver.switchTo().window(win).close();
				waitForSync(1);
				switchToWindow("getParent");
			}
		}

	}
	/**
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Verify the commercial links in cafeed
	 */
	public void verifyCommercialLink(String commericiallink,String uld) throws InterruptedException, AWTException {

		try
		{
			String commerciallink=xls_Read.getCellValue(sheetName, "txt_link;xpath");
			commerciallink=commerciallink.replace("*", data(uld));
		String actcommerciallink=driver.findElement(By.xpath(commerciallink)).getAttribute("title");
			if(actcommerciallink.equals(commericiallink)){

				writeExtent("Pass","Successfully verified the commercial link"+commericiallink);
				
				
			}else
			{
				writeExtent("Fail","failed to verify the commercial link"+commericiallink);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Failed to verify the commercial link");
		}
	}


	/**
	 * @author A-10690
	 * @param ULD number
	 * @param AWB number
	 * @throws Exception
	 * Desc : Verifying no of awbs and awb text inside a uld
	 */
	public void verifyAwbDetailsInsideULD(String ULD,String[] AWB) throws Exception
	{
		
		try
		{

			//checking whether expand arrow icon is present for the ULD
			String expandbtn=xls_Read.getCellValue(sheetName, "btn_expand;xpath");
			expandbtn=expandbtn.replace("*", data(ULD));
			if(driver.findElements(By.xpath(expandbtn)).size()==1)
			{
				driver.findElement(By.xpath(expandbtn)).click();
				waitForSync(2);
				String actawbcount=xls_Read.getCellValue(sheetName, "lnk_awbcount;xpath");
				actawbcount=actawbcount.replace("*", data(ULD));

				//Verifying the no of awbs inside a ULD
				if(driver.findElements(By.xpath(actawbcount)).size()==AWB.length)
				{
					for(int i=0;i<AWB.length;i++)
					{
						String actawb=xls_Read.getCellValue(sheetName, "lnk_awb;xpath");
						actawb=actawb.replace("uldno", data(ULD)).replace("awbno",AWB[i]);
						if(driver.findElements(By.xpath(actawb)).size()==1)

							writeExtent("Pass","Successfully verified "+ AWB[i] +"inside a ULD on "+screenName );
						else
							writeExtent("Fail","Failed to verify"+ AWB[i] +"inside a ULD on "+screenName);
					}
				}
				else

					writeExtent("Fail","fail to  verify AWB count inside a ULD on "+screenName);
				//closing expand button
				driver.findElement(By.xpath(expandbtn)).click();
			}else
				writeExtent("Fail","Failed to verify expand button against ULD on"+screenName);	
		}catch(Exception e)
		{
			writeExtent("Fail","Failed to verify AWB details inside a ULD on "+screenName);	
		}


	}
	/**
	 * @author A-10690
	 * @param expected bigreferencenumber
	 * @throws Exception
	 * Desc : verify bigreferenceno on clicking an awb inside a ULD
	 */
	public void verifyBigrefNo(String bigref) throws Exception
	{
		try
		{

				switchToWindow("storeParent");
				waitForSync(5);
				switchToWindow("child");
				driver.switchTo().frame("popUpFrame");
				String expBigref=getAttributeWebElement(sheetName, "txt_bigrefno;xpath", "Big reference number", "value", screenName);
				if(expBigref.equals(data(bigref)))
				{
					writeExtent("Pass","Successfully verified the BIGREFNo on"+screenName);
					
				}
				else
				{
				writeExtent("Fail","Failed to verify big Reference number on"+screenName);
				}
				
				
				switchToWindow("closeChild");
				waitForSync(4);
				switchToWindow("getParent");
							

	}catch(Exception e)
		{
		writeExtent("Fail","Failed to verify Bigreference number on "+screenName);	
		}
	}
	/**
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Verify the ULD is not present under flight in cafeed
	 */
	public void verifyULDNotPresent(String uld) throws InterruptedException, AWTException {

		try
		{
			String locator = xls_Read.getCellValue(sheetName, "txt_uld;xpath");
			locator=locator.replace("*", data(uld));
			if((driver.findElements(By.xpath(locator)).size()==0)){

				writeExtent("Pass","Successfully verified the ULD  is not available in "+ screenName);
				
				
			}else
			{
				writeExtent("Fail","failed to verify the uld is not present in "+screenName );
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Failed to verify ULD count");
		}
	}
	/**
	 * @author A-10690
	 * @param expected pieces,weight,scc,shipment description
	 * @throws Exception
	 * Desc : verify pieces, weigght,scc,shipment description of an awb
	 */
	public void verifyShipmentdetails(String pcs,String wght,String scc,String desc) throws Exception
	{
		try
		{

				switchToWindow("storeParent");
				waitForSync(5);
				switchToWindow("child");
				driver.switchTo().frame("popUpFrame");
				String actpcs=getAttributeWebElement(sheetName, "txt_pcs;id", "pieces", "value", screenName);
				String actwght=getAttributeWebElement(sheetName, "txt_wght;id", "weight", "value", screenName);
				Double weight=Double. valueOf(data(wght));
				String expwght=String.valueOf(weight);
				String actscc=getAttributeWebElement(sheetName, "txt_scc;id", "Scc", "value", screenName);
				String actshipdesc=getAttributeWebElement(sheetName, "txt_desc;id", "Shipmentdescrition", "value", screenName);
				
				if((actpcs.equals(data(pcs)))&&(actwght.equals(expwght))&&(actscc.equals(data(scc)))&&(actshipdesc.equals(data(desc))))
				{
					writeExtent("Pass","Successfully verified the AWB details"+screenName);
					
				}
				else
				{
				writeExtent("Fail"," not  verified the AWB details"+screenName);
				}
				
				
				switchToWindow("closeChild");
				waitForSync(4);
				switchToWindow("getParent");
							

	}catch(Exception e)
		{
		writeExtent("Fail","Failed to verify AWB details "+screenName);	
		}
	}
	/**
	 * @author A-10690
	 * @param expected ULD number,Overhang details
	 * @throws Exception
	 * Desc : verify Physically linked uld and overhang details
	 */
	public void verifyPhysicalLinkageDetails(String uld,String overhang) throws Exception
	{
		try
		{

				switchToWindow("storeParent");
				waitForSync(5);
				switchToWindow("child");
				driver.switchTo().frame("popUpFrame");
				String actphysicalinkedULD=getAttributeWebElement(sheetName, "txt_physicallinkeduld;id", "linkeduld", "value", screenName);
				String actoverhang=getAttributeWebElement(sheetName, "txt_indent;xpath", "overhang", "value", screenName);
				System.out.println(data(uld));
				System.out.println(data(overhang));
				if(actphysicalinkedULD.equals(data(uld))&&actoverhang.equals(data(overhang)))
				{
					writeExtent("Pass","Successfully verified the physical linkage details"+screenName);
					
				}
				else
				{
				writeExtent("Fail"," not verified physical linkage details"+screenName);
				}
				switchToWindow("closeChild");
				waitForSync(4);
				switchToWindow("getParent");
							

	}catch(Exception e)
		{
		writeExtent("Fail","Failed to verify physical linkage details "+screenName);	
		}
	}
	
	/**
	 * @author A-10690
	 * @Desc To click the uld link in cafeed
	 * @param ULD
	 * @throws Exception
	 */
	public void clickULDLink(String uld) throws Exception
	{
		try{
	
	
		waitForSync(2);
		String locator=xls_Read.getCellValue(sheetName, "lnk_uld;xpath");
		locator=locator.replace("*", data(uld));
		driver.findElement(By.xpath(locator)).click();
		writeExtent("Pass","Selected "+data(uld)+ " as uld on "+screenName);
		}
		
		catch(Exception e){
			writeExtent("Fail","Failed to click the ULD "+screenName);	
		}
		
	}
/**
	 * @author A-10690
	 * @param ULD number
	 * @param awb number
	 * @throws Exception
	 * Desc : Clicking awb inside the uld
	 */
	
	public void clickAWBInsideULD(String ULD,String awb) throws Exception
	{
		waitForSync(2);
		String expandbtn=xls_Read.getCellValue(sheetName, "btn_expand;xpath");
		expandbtn=expandbtn.replace("*", data(ULD));
		driver.findElement(By.xpath(expandbtn)).click();
		waitForSync(2);
		String actawb=xls_Read.getCellValue(sheetName, "lnk_awb;xpath");
		actawb=actawb.replace("uldno", data(ULD)).replace("awbno",data(awb));
		driver.findElement(By.xpath(actawb)).click();
	}
	

	/**
	 * @author A-10690
	 * @param ULD number
	 * @param awbNumbers
	 * @param expecetd big reference numbers
	 * @throws Exception
	 * Desc : verify bigreference on clicking an awb inside a ULD
	 */
	public void verifyBigRegNo(String ULD,String[] AWB,String[] bigref) throws Exception
	{
		try
		{
			for(int i=0;i<AWB.length;i++)
			{
			//checking whether expand arrow icon is present for the ULD
		String locator=xls_Read.getCellValue(sheetName, "btn_expand;xpath");
		locator=locator.replace("*", data(ULD));
		if(driver.findElements(By.xpath(locator)).size()==1)
		{
			driver.findElement(By.xpath(locator)).click();
			waitForSync(2);
			String locator2=xls_Read.getCellValue(sheetName, "lnk_awbcount;xpath");
			locator=locator2.replace("*", data(ULD));
			//Verifying the no of awbs inside a ULD
			if(driver.findElements(By.xpath(locator)).size()==AWB.length)
			{
			
				String locator1=xls_Read.getCellValue(sheetName, "lnk_awb;xpath");
				locator1=locator1.replace("uldno", data(ULD)).replace("awbno",AWB[i]);
				
				driver.findElement(By.xpath(locator1)).click();
				switchToWindow("storeParent");
				waitForSync(5);
				switchToWindow("child");
				driver.switchTo().frame("popUpFrame");
				String Bigref=getAttributeWebElement(sheetName, "txt_bigrefno;xpath", "Big reference number", "value", screenName);
				if(Bigref.equals(bigref[i]))
				{
					writeExtent("Pass","Successfully verified the BIGREFNo on "+screenName);
					
				}
				else
				{
				writeExtent("Fail","Failed to verify big Reference number on "+screenName);
				}
				
				
				switchToWindow("closeChild");
				waitForSync(4);
				switchToWindow("getParent");
							
			}else
			writeExtent("Fail","Failed to verify AWB count inside a ULD on "+screenName);	
			}
		else
		writeExtent("Fail","Failed to verify expand button against ULD on"+screenName);	
			}
	}catch(Exception e)
		{
		writeExtent("Fail","Failed to verify AWB count inside a ULD on "+screenName);	
		}
	}
	/**
	 * @author A-8783
	 * @throws Exception
	 */
	public void switchWindowBack() throws Exception{
		switchToWindow("closeChild");
		waitForSync(4);
		switchToWindow("getParent");
	}


    /**@author A-10328
	 * Description- click ULD to get the information
	 * @param ULD
	 * throws Exception
	 */

public void clickULDDetails(String ULD)throws Exception
	

{
		

String clickULD=xls_Read.getCellValue(sheetName, "btn_clickULD;xpath");
clickULD=clickULD.replace("*", data(ULD));
driver.findElement(By.xpath(clickULD)).click();
waitForSync(2);

}
/**@author A-10328
 * Description - verify floating pallet is checked 
 * @throws InterruptedException
*/
	
	


public void verifyFloatingPalletIsChecked() throws InterruptedException
	

{
		
	try
	

	{
	switchToWindow("storeParent");
	waitForSync(5);
	switchToWindow("child");
	driver.switchTo().frame("popUpFrame");
	By actualFloatingPallet=getElement(sheetName, "chk_floatingpallet;xpath");

	boolean checked = driver.findElement(actualFloatingPallet).isSelected();
	if (checked){
	onPassUpdate(screenName, "", "",
	"Floating pallet is  checked ",
	"Floating pallet is  checked ");

	} else {
	onFailUpdate(screenName, "", "",
	"Floating pallet is not  checked",
	"Floating pallet is not  checked");
	}

}
catch(Exception e)
{

}
			
	
}



/**@author A-10328
* Description - verify no of positions
* @param expposition
*/




public void verifynoofpositions(String expposition)


{

String actposition=xls_Read.getCellValue(sheetName, "txt_position;xpath");
String actual = driver.findElement(By.xpath(actposition)).getAttribute("value");
verifyScreenTextWithExactMatch(screenName, data(expposition),actual, "Verification of position  ","Verification of position ");
waitForSync(2);


}

/**@author A-10328
 * Description - verify commerical linkage is empty
 * @throws Exception
 */
	
public void verifyCommericalLinkageisEmpty() throws Exception


{
		
String locator=xls_Read.getCellValue(sheetName, "txt_commericallink;xpath");
String CommericalLinkageValue =driver.findElement(By.xpath(locator)).getAttribute("value");
if(CommericalLinkageValue.isEmpty())
{
	writeExtent("Pass", "Commerical Linkage value is empty in  "+screenName);
	}
	else
	{
	writeExtent("Fail", "Commerical Linkage value is not empty in "+screenName);
			
	}
	switchToWindow("closeChild");
	waitForSync(4);
	switchToWindow("getParent");
}
	/**
	 * @author A-7271
	 * @param flightNumber
	 * @param flightDate
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : list flight details
	 */
	public void listFlightDetails(String flightNumber,String flightDate) throws InterruptedException, IOException
	{
		enterValueInTextbox(sheetName, "inbx_flightNumber;xpath", data(flightNumber),"Flight Number ", screenName);
		enterValueInTextbox(sheetName, "inbx_flightDate;xpath", data(flightDate),"Flight Date ", screenName);
		enterValueInTextbox(sheetName, "inbx_searchorigin;id", data("Origin"),"Flight Date ", screenName);
		clickWebElement(sheetName, "btn_flightSearch;xpath", "Search flight", screenName);
		waitForSync(3);
		
	}
	/**
	 * @author A-9844
	 * @param ULD 
	 * @param AWB 
	 * @throws Exception
	 * Desc : Verifying awb does not exists inside the uld
	 */
	public void verifyAwbNotPresentInsideULD(String ULD,String[] AWB) throws Exception
	{

		try
		{

			//checking whether expand arrow icon is present for the ULD
			String expandbtn=xls_Read.getCellValue(sheetName, "btn_expand;xpath");
			expandbtn=expandbtn.replace("*", data(ULD));
			if(driver.findElements(By.xpath(expandbtn)).size()==1)
			{
				driver.findElement(By.xpath(expandbtn)).click();
				waitForSync(2);

				//Verifying the no of awbs inside a ULD

				for(int i=0;i<AWB.length;i++)
				{
					String actawb=xls_Read.getCellValue(sheetName, "lnk_awb;xpath");
					actawb=actawb.replace("uldno", data(ULD)).replace("awbno",AWB[i]);
					if(driver.findElements(By.xpath(actawb)).size()==0)

						writeExtent("Pass","Successfully verified "+ AWB[i] +"is not present inside the ULD "+ULD+" on "+screenName );
					else
						writeExtent("Fail","AWB "+ AWB[i] +" is present inside a ULD on "+screenName);
				}
				//closing expand button
				driver.findElement(By.xpath(expandbtn)).click();
			}

			else
			{
				writeExtent("Fail","Failed to verify expand button against ULD on"+screenName+".No details are present inside the ULD on"+screenName);	
			}

		}catch(Exception e)
		{
			writeExtent("Fail","Failed to verify AWB details are not inside a ULD on "+screenName);	
		}

	}

	/**
	 * @author A-10690
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description : Verify the size of ULD in the listed flight
	 */
	public void verifyULDCount(int uldcount) throws InterruptedException, AWTException {

		try
		{
			String locator = xls_Read.getCellValue(sheetName, "table_ULDsize;xpath");
			

			if((driver.findElements(By.xpath(locator)).size()==uldcount)){

				writeExtent("Pass","Successfully verified the ULD count");
				
				
			}else
			{
				writeExtent("Fail","failed to verify the uld count"+uldcount);
			}
		}

		catch(Exception e)
		{
			writeExtent("Fail","Failed to verify ULD count");
		}
	}

	
	/**
	 *  @author A-10690
	 * Description... Verify ULD  Details 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @throws InterruptedException
	 * @throws IOException 
	 */
  public void verifyULDDetails(int verfCols[],String actVerfValues[],String pmKey)
			 throws InterruptedException, IOException {
	
		
			waitForSync(1);
			verify_tbl_records_multiple_cols(sheetName, "table_ULDDetails;xpath", "//td", verfCols, pmKey, actVerfValues);	
			

	}

	/**
	 * @author A-9847
	 * @Desc To verify the DG details of a DG shipment retrieved from Cafeed
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmkey
	 * @throws IOException
	 */
	public void verifyAwbDGDetails(int verfCols[], String actVerfValues[],String pmkey) throws IOException
	{
		verify_tbl_records_multiple_cols(sheetName, "table_awbDgDetails;xpath", "//td", verfCols, data(pmkey),
				actVerfValues);
		waitForSync(3);
	}


	/***
	 * 
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @param msgType
	 * @param isAssertreq
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyFlightDetails(int verfCols[], String actVerfValues[],
			String pmKey,boolean isAssertreq) throws InterruptedException, IOException {
		
		verify_tbl_records_multiple_cols(sheetName, "table_flightDetails;xpath",
				"//td", verfCols, pmKey, actVerfValues,isAssertreq);
	}
	/**
	 * @author A-9847
	 * @Desc To click on createDG option and to enter the full Awb number
	 * @param awbPrefix
	 * @param awbNumber
	 * @throws Exception
	 */
	
	public void enterAWBDetails(String awbPrefix,String awbNumber) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElementByWebDriver(sheetName, "lnk_createDG;xpath", "Create DG", screenName);
		switchToWindow("switchToChildWindow");
		switchToFrame("frameName","popUpFrame");
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id",data(awbPrefix),"AWB Prefix", screenName);
		enterValueInTextbox(sheetName, "inbx_awbNumber;id",data(awbNumber),"AWB Number", screenName);
	}
	
	
	/**
	 * @author A-9847
	 * @Desc To enter the Origin and Destination on Create DG pop-up window
	 * @param origin
	 * @param destination
	 * @throws Exception
	 */
	public void enterOrgDest(String origin,String destination) throws Exception
	{
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_origin;id",data(origin),"Origin", screenName);
		enterValueInTextbox(sheetName, "inbx_destination;id",data(destination),"Destination", screenName);
	}
	
	/**
	 * @author A-9847
	 * @Desc To enter the UNID number on Create DG pop-up window
	 * @param unid
	 * @throws Exception
	 */
	public void enterUNID(String unid) throws Exception
	{
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_unidnumber;id",data(unid),"UNID Number", screenName);
		
	}
	
	
	/**
	 * @author A-9847
	 * @Desc To enter the pieces, weight per package, and unit on Create DG pop-up window
	 * @param pcs
	 * @param wgtPerPkg
	 * @param unit
	 * @throws Exception
	 */
	public void enterPcsWgtUnit(String pcs,String wgtPerPkg,String unit) throws Exception
	{
		try{
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_pieces;id",data(pcs),"Pieces", screenName);
		enterValueInTextbox(sheetName, "inbx_quantityPerKg;id",data(wgtPerPkg),"Quantity per Package", screenName);
		clickWebElementByWebDriver(sheetName, "inbx_unit;id", "Unit", screenName);
		waitForSync(1);
		String locator=xls_Read.getCellValue(sheetName, "inbx_unit;xpath");
		locator=locator.replace("*", data(unit));
		driver.findElement(By.xpath(locator)).click();
		writeExtent("Pass","Selected "+data(unit)+ " as unit on "+screenName);
		}
		
		catch(Exception e){
			writeExtent("Fail","Failed to enter the details on "+screenName);	
		}
		
	}
	
	/**
	 * @author A-9847
	 * @Desc To click on getUnidDetails link on Create DG pop-up window 
	 * @throws Exception
	 */
	public void clickGetUnidDetails() throws Exception{
		
		clickWebElementByWebDriver(sheetName, "btn_getUnidDetails;xpath", "Get UNID Details", screenName);
		switchToFrame("default");	
		switchToWindow("storeFirstChild");
		switchToWindow("childWindow2");	
		waitForSync(2);
	}
	
	
	/**
	 * @author A-9847
	 * @Desc To select a particular row based on the UNID type given(Cargo, Limited, Pax_Cargo) on the UNID details window
	 * @param type
	 * @throws InterruptedException
	 */
	public void selectUnidDetails(String type) throws InterruptedException{
		try{
		String locator=xls_Read.getCellValue(sheetName, "btn_selectUNID;xpath");
		locator=locator.replace("*", data(type));
		driver.findElement(By.xpath(locator)).click();			
		waitForSync(2);
		writeExtent("Pass","Successfully selected UNID details on "+screenName);
		}
		catch(Exception e){
			writeExtent("Fail","Failed to select UNID details on "+screenName);
		}
	}
	
	/**
	 * @author A-9847
	 * @Desc To click on OK link on UNID details window
	 * @throws Exception
	 */
	public void clickOK() throws Exception{
	
	clickWebElementByWebDriver(sheetName, "btn_ok;xpath", "Ok Button", screenName);
	switchToWindow("getFirstChild");
	switchToFrame("frameName","popUpFrame");
	}
	
	/**
	 * @author A-9847
	 * @Desc To click on save button and verify the ACK message on Create DG pop-up window and to close the window
	 * @throws Exception
	 */
	public void clickSave() throws Exception{
		
        clickWebElementByWebDriver(sheetName, "btn_save;xpath", "Save Button", screenName);        
        waitForSync(4);
        //to handle embargo warning
        acceptCafeedAlertSwitchtoChildPopup();
                                        
        String ackMsg=getElementText(sheetName, "div_ackMsg;xpath", "Acknowledge Message", screenName);                   
        System.out.println(ackMsg);
        verifyScreenTextWithExactMatch(sheetName, "ACK - DGSL has been saved successfully", ackMsg, "Verify DGSL saved Successfully ", "Acknowledge Message");
        closeBrowser();
        switchToWindow("getParent");
        switchToFrame("default");

	}
	
	


	/***
	 * 
	 * 
	 * @param verfCols
	 * @param actVerfValues
	 * @param pmKey
	 * @param msgType
	 * @param isAssertreq
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	public void verifyAWBDetails(int verfCols[], String actVerfValues[],
			String pmKey,boolean isAssertreq) throws InterruptedException, IOException {
		
		verify_tbl_records_multiple_cols(sheetName, "table_awbDetails;xpath",
				"//td", verfCols, pmKey, actVerfValues,isAssertreq);
	}
	/**
	 * @author A-7271
	 * @param uld
	 * Desc : expand ULD
	 */
	public void expandULD(String uld)
	{
		String locator=xls_Read.getCellValue(sheetName, "table_flightDetails;xpath");
		
		try
		{
		List<WebElement> ele=driver.findElements(By.xpath(locator));
		int rowCount=1;
		
		for(WebElement element:ele)
		{
			if(element.getText().replaceAll(" ", "").contains(data(uld)))
			
				break;
			rowCount++;
		}
		
		//Finding the dynamic xpath of expand button and clicking
		String dynXpath="("+locator+")["+rowCount+"]//td[1]//img";
		
		driver.findElement(By.xpath(dynXpath)).click();
		
		writeExtent("Pass","Expanded the ULD "+data(uld)+" on "+screenName);
		
		}
		
		catch(Exception e)
		{
			writeExtent("Fail","Could not expand the ULD "+data(uld)+" on "+screenName);
		}
		

		
		
		
	}
	
	/**
	 * @author A-7271
	 * Desc : click DG/SL link
	 */
	public void clickDGSL()
	{
		String locator=xls_Read.getCellValue(sheetName, "lnk_DGSL;xpath");
		Actions actionDriver = new Actions(driver);
		WebElement ele=driver.findElement(By.xpath(locator));
		actionDriver.moveToElement(ele).build().perform();
		waitForSync(1);
		
		
		
		
		
	}
	/**
	 * @author A-7271
	 * @throws InterruptedException
	 * @throws IOException
	 * Desc : click create dg link
	 */
	public void clickCreateDG() throws InterruptedException, IOException
	{
		clickWebElementByWebDriver(sheetName, "lnk_createDG;xpath", "Create DG", screenName);
		waitForSync(5);
		
	}
	
	/**
	 * @author A-7271
	 * @param awbPrefix
	 * @param awbNumber
	 * @throws Exception
	 * Desc : enter DG details
	 */
	public void enterDGDetails(String awbPrefix,String awbNumber) throws Exception
	{
		switchToWindow("storeParent");
		clickWebElementByWebDriver(sheetName, "lnk_createDG;xpath", "Create DG", screenName);
		switchToWindow("switchToChildWindow");
		switchToFrame("frameName","popUpFrame");
		waitForSync(1);
		enterValueInTextbox(sheetName, "inbx_awbPrefix;id",data(awbPrefix),"AWB Prefix", screenName);
		
	}
}