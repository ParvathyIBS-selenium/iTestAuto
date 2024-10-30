package screens;



import java.awt.AWTException;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import common.CustomFunctions;
import common.ExcelReadWrite;
import common.Xls_Read;

public class BuildUpListHHT extends CustomFunctions {
	
	String sheetName = "BuildUpListHHT";
	String screenName = "BuildUpListHHT";
	

	public BuildUpListHHT(WebDriver driver, ExcelReadWrite excelReadWrite, Xls_Read xls_Read2) {
		super(driver, excelReadWrite, xls_Read2);
		
		
	}
	
	/**
	 * @author A-9478
	 * @throws InterruptedException
	 * @throws AWTException
	 * Desc: Invoking the hht Build up list screen
	 * @throws IOException 
	 */
	public void invokeBuildUpListScreen() throws InterruptedException, AWTException, IOException
	{		
		clickActionInHHT("buildUpListhht_menu;xpath",proppathhht,"Build Up List menu",screenName);
	}
	
	/**
	 * @author A-9478
	 * @param carrCode
	 * @param flightNo
	 * @param flightDate
	 * @throws AWTException
	 * @throws InterruptedException
	 * @throws IOException 
	 * Desc: List by flight details
	 */
	public void listByFlightDetails(String carrCode,String flightNo,String flightDate) throws AWTException, InterruptedException, IOException
	{
		
		waitForSync(5);
		enterValueInHHT("buildUpListhht_inbx_carrierCode;accessibilityId",proppathhht,data(carrCode),"Carrier Code",screenName);
		waitForSync(2);
		enterValueInHHT("buildUpListhht_inbx_flightNumber;accessibilityId",proppathhht,data(flightNo),"Flight No",screenName);
		waitForSync(2);
		if(flightDate.equals("currentDay"))
		{
			clickActionInHHT("buildUpListhht_btn_currentDate;xpath",proppathhht,"Current Date",screenName);
		}

		else if(flightDate.equals("nextDay"))
		{
			clickActionInHHT("buildUpListhht_btn_nextDate;xpath",proppathhht,"Next Date",screenName);
		}
		waitForSync(5);		
		waitTillMobileElementDisplay(proppathhht,"buildUpListhht_btn_moreOptions;xpath","xpath");
	}
	
	
	/**
     * Desc : Clicking More Options Button
     *@author A-9478
     * @throws AWTException
     * @throws InterruptedException
	 * @throws IOException 
     */
     public void clickMoreOptions() throws AWTException, InterruptedException, IOException
     {
              waitForSync(5);
              
          clickActionInHHT("buildUpListhht_btn_moreOptions;xpath",proppathhht,"More Options",screenName);  
              waitForSync(6); 
     }
     
     /**
      * Desc : Select ULD after listing
      *@author A-9478
      * @throws AWTException
      * @throws InterruptedException
 	 * @throws IOException 
      */
      public void selectULD(String ULDNum) throws AWTException, InterruptedException, IOException
      {    
    	  try
    	  {
    		  String locatorValue="";
     		 String locatorCheck=getPropertyValue(proppathhht, "buildUpListhht_txt_Mins");
     		 if(androiddriver.findElements(By.xpath(locatorCheck)).size()==0)
     			 locatorValue=getPropertyValue(proppathhht, "buildUpListhht_btn_SelectULD1;xpath");
     		 else
     			 locatorValue=getPropertyValue(proppathhht, "buildUpListhht_btn_SelectULD;xpath");
     		 locatorValue=locatorValue.replace("ULD", data(ULDNum));
    		 androiddriver.findElement(By.xpath(locatorValue)).click();
    		 waitForSync(3);
    		 writeExtent("Pass", "Selected ULD number : "+data(ULDNum)+" in "+screenName);                
            
    	  }
    	  catch(Exception e)
    	  {
    		  writeExtent("Fail", "Couldn't select ULD number : "+data(ULDNum)+" in "+screenName);
    	  }
    	  
      }
      
      /**
       * Desc : click on '>' next to ULD
       * @author A-9478
       * @throws AWTException
       * @throws InterruptedException
       * @throws IOException 
       */
       public void clickOnULD(String ULDNum) throws AWTException, InterruptedException, IOException
       {    
     	  try
     	  {
     		  String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_btn_clickULD;xpath");
         	  locatorValue=locatorValue.replace("ULD", data(ULDNum));
         	  androiddriver.findElement(By.xpath(locatorValue)).click();
         	  waitForSync(3);
         	  writeExtent("Pass", "Clicked on ULD number : "+data(ULDNum)+" in "+screenName);                
     	  }
     	  catch(Exception e)
     	  {
     		 captureScreenShot("Android");
     		  writeExtent("Fail", "Couldn't click on  ULD number : "+data(ULDNum)+" in "+screenName);
     	  }
     	  
       }
       
       /**
        * Desc : Verify AWB Number
        * @author A-9478
        * @throws AWTException
        * @throws InterruptedException
        * @throws IOException 
        */
        public void verifyAWB(String AWBNo) throws AWTException, InterruptedException, IOException
        {    
      	  try
      	  {
      		  String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_AWB;xpath");
          	  locatorValue=locatorValue.replace("AWB", data(AWBNo));
          	  if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
          	  {
          		writeExtent("Pass", "Successfully verified AWB Number "+data(AWBNo)+" in "+screenName);
          	  }
          	  else
          	  {
          		captureScreenShot("Android");
        		  writeExtent("Fail", "Couldn't verify AWB number : "+data(AWBNo)+" in "+screenName);
          	  }
          	                  
      	  }
      	  catch(Exception e)
      	  {
      		 captureScreenShot("Android");
      		  writeExtent("Fail", "Couldn't verify AWB number : "+data(AWBNo)+" in "+screenName);
      	  }
      	  
        }
        
        /**
         * Desc : Verify ULD Number
         * @author A-9478
         * @throws AWTException
         * @throws InterruptedException
         * @throws IOException 
         */
         public void verifyULD(String ULDNum) throws AWTException, InterruptedException, IOException
         {    
       	  try
       	  {
       		  String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_ULDNum;xpath");
           	  locatorValue=locatorValue.replace("ULD", data(ULDNum));
           	  if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
           	  {
           		writeExtent("Pass", "Successfully verified ULD Number "+data(ULDNum)+" in "+screenName);
           	  }
           	  else
           	  {
           		captureScreenShot("Android");
         		  writeExtent("Fail", "Couldn't verify ULD number : "+data(ULDNum)+" in "+screenName);
           	  }
           	                  
       	  }
       	  catch(Exception e)
       	  {
       		 captureScreenShot("Android");
       		  writeExtent("Fail", "Couldn't verify ULD number : "+data(ULDNum)+" in "+screenName);
       	  }
       	  
         }
     	/**
          * Desc : Verify shipment level instruction for each awb in build up list screen
          * @author A-10690
          * @param uldnumber
          * @param expected instructions
          * @throws AWTException
          * @throws InterruptedException
          * @throws IOException 
          */
          public void verifyShipmentlevelInstruction(String AWB,String texts[]) throws AWTException, InterruptedException, IOException
          {    
       		
           		try{


           			String locatorICON=getPropertyValue(proppathhht, "buildUpListhht_info;xpath");
           			locatorICON=locatorICON.replace("*", data(AWB));
           			androiddriver.findElement(By.xpath(locatorICON)).click();
           			waitForSync(3);

           			
           			for(int i=0;i<texts.length;i++)
           			{
           				String locatorText=getPropertyValue(proppathhht, "buildUpListhht_instructions;xpath");
           				locatorText=locatorText.replace("*", texts[i]);

           				scrollInMobileDevice(texts[i]);
           				waitForSync(3);
           				System.out.println(androiddriver.findElements(By.xpath(locatorText)).size());
           				if(androiddriver.findElements(By.xpath(locatorText)).size()==1)
           				{
           					writeExtent("Pass", "Successfully verified shipment level instruction " +texts[i]+" on "+ screenName);
           					System.out.println("Successfully verified shipment level instruction " +texts[i]);
           				}
           				else
           					writeExtent("Fail", "Failed to verify shipment level instruction " + screenName);


           			}
           			clickActionInHHT("buildUpListhht_instructionsClose;xpath",proppathhht,"Popup close",screenName);
           			waitForSync(2);


           		}

           		catch(Exception e){
           			writeExtent("Fail", "Failed to verify shipment level instructions on " + screenName);
           		}
           	}
          
          
          

         /**
          * Desc : Verify HHT Screen
          * @author A-9478
          * @throws InterruptedException
          */
          public void verifyHHTScreenName(String Screen) throws InterruptedException, IOException
          {    
              try
              {
                    String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_ScreenName;xpath");
                    locatorValue=locatorValue.replace("SCREEN", Screen);
                    waitForSync(2);
                    if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
                    {
                        writeExtent("Pass", "Successfully verified screen name "+Screen);
                    }
                    else
                    {
                          captureScreenShot("Android");
                          writeExtent("Fail", "Couldn't verify screen name : "+Screen);
                    }
              }
        	  catch(Exception e)
        	  {
        		 captureScreenShot("Android");
        		  writeExtent("Fail", "Couldn't verify screen name : "+Screen);
        	  }
        	  
          }
          /**
           * Desc : Verify task status is automatically completed in build up list hht screen
           * @author A-10690
           * @param uldnumber
           * @throws AWTException
           * @throws InterruptedException
           * @throws IOException 
           */
          public void verifyTaskStatusCompleted(String uldNo) throws AWTException, InterruptedException, IOException
          {    
        	  try
        	  {
        		  String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_ULDStatus;xpath");
        		  locatorValue=locatorValue.replace("ULD", data(uldNo));
        		  if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
        		  {



        			  writeExtent("Pass", "Successfully verified task status completed for "+data(uldNo)+" in "+screenName);

        		  }
        		  else
        		  {
        			  captureScreenShot("Android");
        			  writeExtent("Fail", "Couldn't verify task status completed for  "+data(uldNo)+" in "+screenName);
        		  }

        	  }
        	  catch(Exception e)
        	  {
        		  captureScreenShot("Android");
        		  writeExtent("Fail", "Couldn't verify task status completed for : "+data(uldNo)+" in "+screenName);
        	  }

           }
           /**
            * Desc : Verify group level instruction displayed against uld /bulk in build up list screen
            * @author A-10690
            * @param uldnumber
            * @param expected instruction
            * @throws AWTException
            * @throws InterruptedException
            * @throws IOException 
            */
            public void verifyGroupLevelInstruction(String uld,String texts[]) throws AWTException, InterruptedException, IOException
            {    
         		
             		try{


             			String locatorICON=getPropertyValue(proppathhht, "buildUpListgrouplevelhht_info;xpath");
             			locatorICON=locatorICON.replace("*", data(uld));
             			androiddriver.findElement(By.xpath(locatorICON)).click();
             			waitForSync(3);

             			
             			for(int i=0;i<texts.length;i++)
             			{
             				String locatorText=getPropertyValue(proppathhht, "buildUpListhht_instructions;xpath");
             				locatorText=locatorText.replace("*", texts[i]);

             				scrollInMobileDevice(texts[i]);
             				waitForSync(3);
             				System.out.println(androiddriver.findElements(By.xpath(locatorText)).size());
             				if(androiddriver.findElements(By.xpath(locatorText)).size()==1)
             				{
             					writeExtent("Pass", "Successfully verified group level instruction " +texts[i]+" on "+ screenName);
             					System.out.println("Successfully verified group level instruction " +texts[i]);
             				}
             				else
             					writeExtent("Fail", "Failed to verify group level instruction " + screenName);


             			}
             			clickActionInHHT("buildUpListhht_instructionsClose;xpath",proppathhht,"Popup close",screenName);
             			waitForSync(2);


             		}

             		catch(Exception e){
             			writeExtent("Fail", "Failed to verify group level instructions on " + screenName);
             		}
             	}


       /**
               * Desc : Verify AWB is retained after the uld status is completed
               * @author A-10690
               * @throws AWTException
               * @throws InterruptedException
               * @throws IOException 
               */
               public void verifyAWBIsPresent(String uldNum,String AWBNo) throws AWTException, InterruptedException, IOException
               {    
            	   try
            	   {

            		   String locatorValue1=getPropertyValue(proppathhht, "buildUpListhht_btn_verifyuld;xpath");
            		   locatorValue1=locatorValue1.replace("ULD", data(uldNum));
            		   androiddriver.findElement(By.xpath(locatorValue1)).click();
            		   String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_AWB;xpath");
            		   locatorValue=locatorValue.replace("AWB", data(AWBNo));
            		   if(androiddriver.findElements(By.xpath(locatorValue)).size()!=1)
            			   waitForSync(2);
            		   if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
            		   {
            			   writeExtent("Pass", "Successfully verified AWB Number "+data(AWBNo)+" in "+screenName);
            		   }
            		   else
            		   {
            			   captureScreenShot("Android");
            			   writeExtent("Fail", "Couldn't verify AWB number : "+data(AWBNo)+" in "+screenName);
            		   }

            	   }
            	   catch(Exception e)
            	   {
            		   captureScreenShot("Android");
            		   writeExtent("Fail", "Couldn't verify AWB number : "+data(AWBNo)+" in "+screenName);
            	   }
               }
           /**
            * Desc : Verify task status d in build up list hht screen
            * @author A-10690
            * @param uldnumber
            * @param expected status
            * @throws AWTException
            * @throws InterruptedException
            * @throws IOException 
            */
            public void verifyTaskStatus(String uldNo,String status) throws AWTException, InterruptedException, IOException
            {    
          	  try
          	  {
          		  String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_ULDAllStatus;xpath");
              	  locatorValue=locatorValue.replace("ULD", data(uldNo));
              	locatorValue=  locatorValue.replace("*", data(status));
              	  if(androiddriver.findElements(By.xpath(locatorValue)).size()>0)
              	  {
              		writeExtent("Pass", "Successfully verified task status "+data(status) +"for"+data(uldNo)+" in "+screenName);
              	  }
              	  else
              	  {
              		captureScreenShot("Android");
            		  writeExtent("Fail", "Couldn't verify task status "+data(status) +"for"+data(uldNo)+" in "+screenName);
              	  }
              	                  
          	  }
          	  catch(Exception e)
          	  {
          		 captureScreenShot("Android");
          		  writeExtent("Fail", "Couldn't verify task status"+data(status) +"for"+data(uldNo)+" in "+screenName);
          	  }
          	  
            }

        /**
         * Desc : Verify AWB count
         * @author A-9478
         * @throws AWTException
         * @throws InterruptedException
         * @throws IOException 
         */
         public void verifyAWBCount(String AWBCount) throws AWTException, InterruptedException, IOException
         {    
       	  try
       	  {
       		  String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_AWBCount;xpath");           	  
           	  if(androiddriver.findElement(By.xpath(locatorValue)).getText().equals("AWBCount"))
           	  {
           		writeExtent("Pass", "Successfully verified AWB Count "+data(AWBCount)+" in "+screenName);
           	  }
           	                  
       	  }
       	  catch(Exception e)
       	  {
       		 captureScreenShot("Android");
       		  writeExtent("Fail", "Couldn't verify AWB count : "+data(AWBCount)+" in "+screenName);
       	  }
       	  
         }
         
         /**
          * Desc : Verify SCC
          * @author A-9478
          * @throws AWTException
          * @throws InterruptedException
          * @throws IOException 
          */
          public void verifySCC(String SCC,String AWBNo) throws AWTException, InterruptedException, IOException
          {    
        	  try
        	  {
        		  String locatorValue=getPropertyValue(proppathhht, "buildUpListhht_txt_SCC;xpath");           	  
        		  locatorValue = locatorValue.replace("AWB", data(AWBNo));
        		  if(androiddriver.findElement(By.xpath(locatorValue)).getText().equals(data(SCC)))
            	  {
            		writeExtent("Pass", "Successfully verified SCC "+data(SCC)+" in "+screenName);
            	  }
            	                  
        	  }
        	  catch(Exception e)
        	  {
        		 captureScreenShot("Android");
        		  writeExtent("Fail", "Couldn't verify SCC : "+data(SCC)+" in "+screenName);
        	  }
        	  
          }
          /**
      	 * @author A-9844
      	 * @Desc To verify the flight instruction displayed
      	 * @param flightNo
      	 * @param texts
      	 */
      	public void verifyFlightInstruction(String flightNo,String texts[]){

      		try{


      			String locatorICON=getPropertyValue(proppathhht, "buildUpListhht_info;xpath");
      			locatorICON=locatorICON.replace("*", data(flightNo));
      			androiddriver.findElement(By.xpath(locatorICON)).click();
      			waitForSync(3);

      			
      			for(int i=0;i<texts.length;i++)
      			{
      				String locatorText=getPropertyValue(proppathhht, "buildUpListhht_instructions;xpath");
      				locatorText=locatorText.replace("*", texts[i]);

      				scrollInMobileDevice(texts[i]);
      				waitForSync(3);
      				System.out.println(androiddriver.findElements(By.xpath(locatorText)).size());
      				if(androiddriver.findElements(By.xpath(locatorText)).size()==1)
      				{
      					writeExtent("Pass", "Successfully verified flight instruction: " +texts[i]+" on "+ screenName);
      					System.out.println("Successfully verified flight instruction: " +texts[i]);
      				}
      				else
      					writeExtent("Fail", "Failed to verify all the flight instructions on " + screenName);


      			}
      			clickActionInHHT("buildUpListhht_instructionsClose;xpath",proppathhht,"Popup close",screenName);
      			waitForSync(2);


      		}

      		catch(Exception e){
      			writeExtent("Fail", "Failed to verify flight instructions on " + screenName);
      		}
      	}
         /**
          * Desc : Verify Pieces and weight
          * @author A-9478
          * @throws AWTException
          * @throws InterruptedException
          * @throws IOException 
          */
          public void verifyPiecesAndWeight(String Pieces,String Weight) throws AWTException, InterruptedException, IOException
          {    
        	  try
        	  {
        		  String locator1=getPropertyValue(proppathhht, "buildUpListhht_txt_Pieces;xpath");           	  
        		  String locator2=getPropertyValue(proppathhht, "buildUpListhht_txt_Weight;xpath");
        		  if(androiddriver.findElement(By.xpath(locator1)).getText().equals(data(Pieces)) && androiddriver.findElement(By.xpath(locator2)).getText().equals(data(Weight)))
            	  {
            		writeExtent("Pass", "Successfully verified pieces "+data(Pieces)+" and weight "+data(Weight)+" in "+screenName);
            	  }
        		  else
        		  {
        			  captureScreenShot("Android");
        			  writeExtent("Fail", "Couldn't verify pieces "+data(Pieces)+" and weight "+data(Weight)+" in "+screenName);
        		  }
            	                  
        	  }
        	  catch(Exception e)
        	  {
        		  captureScreenShot("Android");
    			  writeExtent("Fail", "Couldn't verify pieces "+data(Pieces)+" and weight "+data(Weight)+" in "+screenName);
        	  }
        	  
          }
          
          /**
           * Desc : Verify origin and destination
           * @author A-9478
           * @throws AWTException
           * @throws InterruptedException
           * @throws IOException 
           */
           public void verifyOriginAndDestination(String AWBNo,String Origin,String Destination) throws AWTException, InterruptedException, IOException
           {    
         	  try
         	  {
         		  String locator1=getPropertyValue(proppathhht, "buildUpListhht_txt_Origin;xpath");           	  
         		  String locator2=getPropertyValue(proppathhht, "buildUpListhht_txt_Destination;xpath");
         		  locator1 = locator1.replace("AWB", data(AWBNo));
         		  locator2 = locator2.replace("AWB", data(AWBNo));
         		  if(androiddriver.findElement(By.xpath(locator1)).getText().equals(data(Origin)) && androiddriver.findElement(By.xpath(locator2)).getText().equals(data(Destination)))
             	  {
             		writeExtent("Pass", "Successfully verified origin "+data(Origin)+" and destination "+data(Destination)+" in "+screenName);
             	  }
         		  else
         		  {
         			  captureScreenShot("Android");
         			  writeExtent("Fail", "Couldn't verify origin "+data(Origin)+" and destination "+data(Destination)+" in "+screenName);
         		  }
             	                  
         	  }
         	  catch(Exception e)
         	  {
         		  captureScreenShot("Android");
     			  writeExtent("Fail", "Couldn't verify origin "+data(Origin)+" and destination "+data(Destination)+" in "+screenName);
         	  }
         	  
           }
     
     /**
     * Desc : Clicking Assign button
     * @author A-9478
     * @throws AWTException
     * @throws InterruptedException
     * @throws IOException 
     */
     public void clickAssign() throws AWTException, InterruptedException, IOException
     {
           clickActionInHHT("buildUpListhht_btn_Assign;xpath",proppathhht,"Assign",screenName);               
     }
     
     /**
      * Desc : Clicking Build Up button
      * @author A-9478
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException 
      */
      public void clickBuildUp() throws AWTException, InterruptedException, IOException
      {
            clickActionInHHT("buildUpListhht_btn_BuildUp;xpath",proppathhht,"Build Up",screenName);               
            waitForSync(4);
      }
     /**
      * Desc : Clicking Task Complete
      * @author A-9478
      * @throws AWTException
      * @throws InterruptedException
      * @throws IOException 
      */
      public void clickTaskComplete() throws AWTException, InterruptedException, IOException
      {
               
               clickActionInHHT("buildUpListhht_btn_TaskComplete;xpath",proppathhht,"Task Complete",screenName); 
               waitForSync(2); 
                  
      } 
      
     /**
  	 * @author A-9478
  	 * @param screenName
  	 * Desc : Verify error message in hht screen
  	 * @throws IOException 
  	 */
  	public void verifyErrorMessage(String message) throws IOException
  	{
  		try
  		{
  			String locator1=getPropertyValue(proppathhht, "buildUpListhht_txt_verifyWarningMessage;xpath");
            locator1=locator1.replace("Warning", message);
  			if(androiddriver.findElements(By.xpath(locator1)).size()>0)
  			{
  				writeExtent("Pass", "Successfully verified error message "+message+" in "+screenName);
  			}
  			else
  			{
  				captureScreenShot("Android");
  				writeExtent("Fail", "Couldn't verify error message "+message+" in "+screenName);
  			}
             
  			closeErrorMessage(message);
  		}
  		
  		catch(Exception e)
  		{
  			captureScreenShot("Android");
  			writeExtent("Fail", "Couldn't verify error message "+message+" in "+screenName);
  		}
  	}
  	
  	 /**
  	 * @author A-9478
  	 * @param screenName
  	 * Desc : Verify warning message in hht screen
  	 * @throws IOException 
  	 */
  	public void closeErrorMessage(String message) throws IOException
  	{
  		try
  		{
  			String locator2=getPropertyValue(proppathhht, "buildUpListhht_btn_closeWarningMessage;xpath");
            locator2=locator2.replace("Warning", message);
            androiddriver.findElement(By.xpath(locator2)).click();
  			writeExtent("Pass", "Successfully closed error message "+message+" in "+screenName);  			
  		}
  		
  		catch(Exception e)
  		{
  			captureScreenShot("Android");
  			writeExtent("Fail", "Couldn't close warning message "+message+" in "+screenName);
  		}
  	}
	
}
