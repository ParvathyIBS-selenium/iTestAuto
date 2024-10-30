package screens;

import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class ULDEnquiryHHT extends CustomFunctions {
	
	String sheetName = "ULDEnquiryHHT";
	String screenName = "ULD Enquiry HHT";
	

	public ULDEnquiryHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the ULD Enquiry HHT screen
	 * @throws IOException 
	 */
	public void invokeULDEnquiryHHTScreen() throws InterruptedException, AWTException, IOException {
	
		scrollInMobileDevice("ULD Enquiry");	
		clickActionInHHT("uldenquiryhht_menu;xpath",proppathhht,"ULD Enquiry menu",screenName);
		waitForSync(5);
	}
	
	/**
	 * @author A-9478
	 * @param awbNumber
	 * Desc : Enter ULD number
	 * @throws IOException 
	 */
	
	public void enterULDNumber(String ULDNo) throws IOException
	{
		enterValueInHHT("uldenquiryhht_inbx_ULD;accessibilityId",proppathhht,data(ULDNo),"ULD Number",screenName);
		waitForSync(10); 
	}
	
	

	/**
	 * @author A-7271
	 * @param Storage Unit
	 * Desc : Enter storage unit
	 * @throws IOException 
	 * @throws InterruptedException 
	 * Desc : verifying the warehouse details
	 */
	public void verifyAWBNumber(String AWBNo) throws IOException, InterruptedException
	{
		/*******AWBNo********/
		try
		{
			String locator=getPropertyValue(proppathhht, "uldenquirthht_txt_AWBNumber;xpath");
			locator=locator.replace("AWBNO", data(AWBNo));	
			int size=androiddriver.findElements(By.xpath(locator)).size();
			if(size==1)
			{
				writeExtent("Pass", "Successfully verified AWB Number "+data(AWBNo)+" in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Couldn't verify AWB Number "+data(AWBNo)+" in "+screenName);
			}
		}
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Couldn't verify AWB Number "+data(AWBNo)+" in "+screenName);
		}
		
	}
	
	public void verifyManifestedPiecesAndWeight(String mfPcs,String mfWgt,String AWBNo) throws IOException
	{
		/*********Manifested pieces and weight******/
		try
		{
			String locator1=getPropertyValue(proppathhht, "uldenquiryhht_txt_mfPcs;xpath");	
			String locator2=getPropertyValue(proppathhht, "uldenquiryhht_txt_mfWgt;xpath");
			locator1=locator1.replace("AWB", data(AWBNo));
			locator2=locator2.replace("AWB", data(AWBNo));
			String actualMfPcs = androiddriver.findElement(By.xpath(locator1)).getText();
			String actualMfWgt = androiddriver.findElement(By.xpath(locator2)).getText();
			if(actualMfPcs.equals(data(mfPcs)) && actualMfWgt.equals(data(mfWgt)))
			{
				writeExtent("Pass", "Successfully verified manifested pieces "+data(mfPcs)+" and weight "+data(mfWgt)+" for "+data(AWBNo)+"in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Couldn't verify manifested pieces "+data(mfPcs)+" and weight "+data(mfWgt)+" for "+data(AWBNo)+" in "+screenName);
			}
		}
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Couldn't verify manifested pieces "+data(mfPcs)+" and weight "+data(mfWgt)+" for "+data(AWBNo)+" in "+screenName);
		}
	}

	public void verifyReceivedPiecesAndWeight(String rcvdPcs,String rcvdWgt,String AWBNo) throws IOException
	{
		/*********Received pieces and weight******/
		try
		{
			String locator1=getPropertyValue(proppathhht, "uldenquiryhht_txt_rcdPcs;xpath");	
			String locator2=getPropertyValue(proppathhht, "uldenquiryhht_txt_rcdWgt;xpath");
			locator1 = locator1.replace("AWB", data(AWBNo));
			locator2 = locator2.replace("AWB", data(AWBNo));
			String actualRcvdPcs = androiddriver.findElement(By.xpath(locator1)).getText();
			String actualRcvdMfWgt = androiddriver.findElement(By.xpath(locator2)).getText();
			if(actualRcvdPcs.equals(data(rcvdPcs)) && actualRcvdMfWgt.equals(data(rcvdWgt)))
			{
				writeExtent("Pass", "Successfully verified received pieces "+data(rcvdPcs)+" and weight "+data(rcvdWgt)+" for "+data(AWBNo)+"in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Couldn't verify received pieces "+data(rcvdPcs)+" and weight "+data(rcvdWgt)+" for "+data(AWBNo)+" in "+screenName);
			}
		}
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Couldn't verify received pieces "+data(rcvdPcs)+" and weight "+data(rcvdWgt)+" for "+data(AWBNo)+" in "+screenName);
		}
	}
	public void verifyOriginAndDestination(String AWBNo,String origin, String dest) throws IOException, InterruptedException
	{
		/***********Origin and Destination****************/
		try
		{
			String locator=getPropertyValue(proppathhht, "uldenquiryhht_txt_origin;xpath");
			locator=locator.replace("AWB", data(AWBNo));
			String locator1=getPropertyValue(proppathhht, "uldenquiryhht_txt_destination;xpath");
			locator1=locator1.replace("AWB", data(AWBNo));
			String o=androiddriver.findElement(By.xpath(locator)).getText();
			String d=androiddriver.findElement(By.xpath(locator1)).getText();
			if(o.equals(data(origin)) && d.equals(data(dest)))
			{
				writeExtent("Pass", "Successfully verified origin "+data(origin)+" and destination "+data(dest)+" for "+data(AWBNo)+" in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Couldn't verify origin "+data(origin)+" and destination "+data(dest)+" for "+data(AWBNo)+" in "+screenName);
			}
		}
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Couldn't verify origin "+data(origin)+" and destination "+data(dest)+" for "+data(AWBNo)+" in "+screenName);
		}
	}
	public void verifyFlightNumber(String flightno,String ULDNum) throws IOException, InterruptedException
	{
		/***********Flight Number****************/
		try
		{
			String locator=getPropertyValue(proppathhht, "uldenquiryhht_txt_FlightNo;xpath");
			locator=locator.replace("ULD", data(ULDNum));	
			String s=androiddriver.findElement(By.xpath(locator)).getText();
			if(s.equals(data(flightno)))
			{
				writeExtent("Pass", "Successfully verified flight number "+data(flightno)+" in "+screenName);
			}
			else
			{
				captureScreenShot("Android");
				writeExtent("Fail", "Couldn't verify flight number "+data(flightno)+" in "+screenName);
			}
		}
		catch(Exception e)
		{
			captureScreenShot("Android");
			writeExtent("Fail", "Couldn't verify flight number "+data(flightno)+" in "+screenName);
		}
	}
		
	}


