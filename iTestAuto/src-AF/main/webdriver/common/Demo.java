package common;

import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.stream.Stream;
import java.time.ZonedDateTime;
import java.text.ParseException;

import jmapps.ui.TextView;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Demo {
	
	
	
	private static final String DATE_FORMAT = "dd-MMM-yyyy HH:mm";
	public static AndroidDriver androiddriver;
	public static List<Object> list=new ArrayList<Object>();
	
	public static Date min(Date... dates) {
	    return Collections.min(Arrays.asList(dates));
	}
	
	
    @SuppressWarnings("unused")
	public static void main(String[] args) throws ParseException, IOException, InterruptedException, AWTException {
    	
    
    	/**************** 1.How to convert a string in to date and vice versa*****/
    	
//    	String sDate1="18-Jul-2024 23:00";  
//    	String sDate2="18-Aug-2024 17:00"; 
//    	
//        Date date1=new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(sDate1);  
//        Date date2=new SimpleDateFormat("dd-MMM-yyyy HH:mm").parse(sDate2);  
//        Date datef= min(date1,date2);
//        
//        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm");  
//        String strDate = dateFormat.format(datef);  
//        
//        System.out.println(strDate);
        
        /*************************************************************************/
    	
    	/***************** 2.Largest number in an integer array with streams******/
    	
//    	int a[]={22,29,1,5};
//    	
//    	   int max = Arrays.stream(a).max().getAsInt();
//    	   System.out.println(max);
//    	
    	   /*************************************************************************/
    	   
    	
    	
    	
    	   /***************** 3.Sorting in reverse order******/
//    	  Integer test[]={22,89,100};
//    	  
//    	Arrays.sort(test,Collections.reverseOrder());  
//    	
//    	System.out.println(test[0]);
//    	
/*
       *//***************************************************/
    	
    	/*******Unique letters in a string******/
//    	String inputString = "BBaaeelldduunngg";
//    	
//    	Set<Character> distinctChars = new HashSet<>();
//        for (char ch : inputString.toCharArray()) {
//            distinctChars.add(ch);
//        }
//       System.out.println(distinctChars);
    	
    	String s="H e l l o ";
   
    	/********************* Reversal of a string **********/
//    	String s= "Parvathy";
//    	
//    	StringBuffer s1=new StringBuffer(s);
//    	s1.reverse();
//    	
//    	System.out.println(s1);
//    	
//    	double d=1.2;
//    	
//    	int i=(int) d;
//    	
    	
    	
//    	String str="Parvathy";
//    	
//    	// Creating a HashMap containing char
//        // as a key and occurrences as a value
      Map<Character, Integer> map
            = new HashMap<Character, Integer>();
// 
//        // Converting given string into
//        // a char array
//        char[] charArray = str.toCharArray();
// 
//        // Checking each character
//        // of charArray
//        for (char c : charArray) {
// 
//            if (map.containsKey(c)) {
// 
//                // If character is present
//                // in map incrementing it's
//                // count by 1
//                map.put(c, map.get(c) + 1);
//            }
//            else {
// 
//                // If character is not present
//                // in map putting this
//                // character into map with
//                // 1 as it's value.
//                map.put(c, 1);
//            }
//        }
//    			
//     // Traverse the HashMap, check
//        // if the count of the character
//        // is greater than 1 then print
//        // the character and its frequency
//        for (Map.Entry<Character, Integer> entry :
//             map.entrySet()) {
// 
//            if (entry.getValue() > 1) {
//                System.out.println(entry.getKey()
//                                   + " : "
//                                   + entry.getValue());
//            }
//        }
//    }
    	
    	
    	
      /**  Date date = new Date();

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		date = c.getTime();

		DateFormat fmt = new SimpleDateFormat(DATE_FORMAT);
		String fromattedDate = fmt.format(date);
		System.out.println(fromattedDate);
		*//****************************************************//*

        // To TimeZone America/New_York
        SimpleDateFormat sdfAmerica = new SimpleDateFormat(DATE_FORMAT);
        TimeZone tzInAmerica = TimeZone.getTimeZone("Europe/Amsterdam");
        sdfAmerica.setTimeZone(tzInAmerica);
        String sDateInAmerica = sdfAmerica.format(date); // Convert to String first
        System.out.println("Date (New York) (String) : " + sDateInAmerica);
        System.out.println(sDateInAmerica.split(" ")[0]);*/
    	
    	
       /* List <String> flightDetails=new ArrayList<String>();
        
        flightDetails.add("AF");
        flightDetails.add("4043");
        flightDetails.add("14-JAN-2021");
        flightDetails.add("AMS");
        flightDetails.add("AMS-CDG");
        flightDetails.add("33X");
        flightDetails.add("PP6");
        flightDetails.add("T0");
        flightDetails.add("C");
        
		String fileContent="";
		
		File file2 = new File("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\jmeter\\csv\\createFlight_FLT003.csv");
		
		if(file2.exists())
		{
			file2.delete();
		}
		
		for(int i=0;i<flightDetails.size();i++)
		{
			
			fileContent=fileContent+flightDetails.get(i)+",";
		}
		fileContent=fileContent.substring(0,fileContent.length()-1);
		System.out.println(fileContent);
		BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\jmeter\\csv\\createFlight_FLT003.txt"));
		writer.write(fileContent);
		writer.close();
		
		File file  = new File("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\jmeter\\csv\\createFlight_FLT003.txt"); // handler to your ZIP file
		 // destination dir of your file
		file.renameTo(file2);
		
	//Starting jmeter
		String path="D:\\Jmeter3.0\\apache-jmeter-3.0\\apache-jmeter-3.0\\bin\\JMETER_Selenium.bat";
		Runtime runtime = Runtime.getRuntime();
		
		runtime.exec("cmd /c start "+ path);
		*/
    	
    	
//    	 DesiredCapabilities cap = new DesiredCapabilities();
//		    cap.setCapability("noReset","true"); 
//			cap.setCapability("deviceName","emulator-5554");
//			cap.setCapability("platformVersion","8.0"); 
//			cap.setCapability("platformName", "Android"); 
//			cap.setCapability(MobileCapabilityType.APP, "D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10_TestRehearsal"+"\\lib\\"+"staging_icargo"+".apk");
//			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
//			cap.setCapability("appPackage", "com.icargo");
//			cap.setCapability("appActivity", "com.icargo.MainActivity");
//	        cap.setCapability("newCommandTimeout", 60*15);
//	        cap.setCapability("unicodeKeyboard", true);
//	        cap.setCapability("resetKeyboard", true);
//	        cap.setCapability("autoGrantPermissions", true);
//	        cap.setCapability("adbExecTimeout",50000 );
//	        String SCC ="VAL";
//	      androiddriver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), cap);
//           Thread.sleep(10000);
//           
           
           
           
          // ((FindsByAndroidUIAutomator<MobileElement>) androiddriver).findElementByAndroidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textContains(\""+SCC+"\").instance(0))");
         //  androiddriver.findElement(By.xpath("//android.widget.TextView[@text='VAL']/../following-sibling::android.view.ViewGroup//android.view.ViewGroup")).click();
//          
//           Thread.sleep(10000);
//          String val=androiddriver.findElement(By.xpath("//android.widget.TextView[@text='Remarks']/..")).getSize().toString();
//          System.out.println("bounds are "+val);
//           TouchAction touchAction = new TouchAction(androiddriver);
//           System.out.println(startx+" "+starty);
//           System.out.println("Entering swipe");
//
//               System.out.println("Swipe from "+startx +" " +starty +"to" +endx +" " +endy );
              // touchAction.press(46, 200).waitAction().moveTo(1000,1200).release().perform();
           
          // AndroidElement element = androiddriver.findElement(By.xpath("xpath of canvas"));

//           int height=androiddriver.manage().window().getSize().getHeight();
//           int width=androiddriver.manage().window().getSize().getWidth();
//           
//           int x=(int) (width*0.5);
//           int y=(int) (height*0.5);
//          TouchAction act= new TouchAction(androiddriver).longPress(x, y).moveTo((x+150), (y+150)).release().perform();
       
    /*	String sc2 = System.getProperty("user.dir");
		String pathc = sc2 + "\\lib\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", pathc);
    	WebDriver wd=new ChromeDriver();
    	
    	wd.get("http://google.com");
    	
    	wd.close();
    	System.out.println(wd);
    	wd.get("http://google.com");*/
    	
    	/*if(wd.toString().contains("null"))
    	{
    		 wd=new ChromeDriver();
    		wd.get("http://google.com");
    	}
    	else
    	{
    		System.out.println("enterrr");
    	}*/
	/*	
    	String sc2 = System.getProperty("user.dir");
		String pathc = sc2 + "\\lib\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", pathc);
    	//WebDriver wd=new ChromeDriver();
    	
    	//wd.get("https://extranet-public.airfrance.fr/dana/home/index.cgi");
		
		ChromeOptions options=new ChromeOptions();
		options.setExperimentalOption("debuggerAddress", "192.168.132.63:9014");
		WebDriver wd=new ChromeDriver(options);
		*/
		//wd.findElement(By.xpath("//b[contains(.,'WEB_icargo-rc4-rct.afklcargo.com')]")).click();
		
    	/*String sc2 = System.getProperty("user.dir");
		String pathc = sc2 + "\\lib\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", pathc);
		ChromeDriver wd=new ChromeDriver();
    	Capabilities capabilities=wd.getCapabilities();
    	   Map<String, ?> asMap=capabilities.asMap();
    	
    	  

    	   for( Entry<String, ?> entry : asMap.entrySet() ){
    		    System.out.println( entry.getKey() + " => " + entry.getValue() );
    		}
    			
    	
    	wd.get("https://Letcode.in/edit");
    	wd.findElement(By.id("fullName")).sendKeys("Parvathy");*/
    	/*
    	String sc2 = System.getProperty("user.dir");
		String pathc = sc2 + "\\lib\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", pathc);
    	//WebDriver wd=new ChromeDriver();
    	
    	//wd.get("https://extranet-public.airfrance.fr/dana/home/index.cgi");
		
		ChromeOptions options=new ChromeOptions();
		options.setBinary("/path/to/other/chrome/binary");
		options.setExperimentalOption("debuggerAddress", "localhost:9014");
		WebDriver wd=new ChromeDriver(options);*/
    	
    /*	String sc2 = System.getProperty("user.dir");
		String pathc = sc2 + "\\lib\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", pathc);
		WebDriver wd=new ChromeDriver();
		
		wd.get("https://cgomon-rc4-rct.airfrance.fr/");*/
    	
    	//System.out.println("value"+"\n"+"value2");
         
    	
    /*	ZoneId zoneId = ZoneId.of( "Europe/Amsterdam" );
    	LocalDate today = LocalDate.now( zoneId );  // 2016-06-25
    	
    	System.out.println(today);
    	
    	YearMonth currentYearMonth = YearMonth.from( today );  // 2016-06
    	LocalDate lastDayOfCurrentYearMonth = currentYearMonth.atEndOfMonth();
    	System.out.println(lastDayOfCurrentYearMonth);*/
    	
    	
    	
//    	String pc2="20";
//    	
//    	String totalPcs=String.valueOf(Integer.parseInt(pc1)+Integer.parseInt(pc2));
//    	
//    	System.out.println(totalPcs);
//    	
//    	String vol="1.5";
//    	String vol2="1";
//    	
//    	String volume=String.valueOf(Double.parseDouble(vol)+Double.parseDouble(vol2));
//    	
//    	System.out.println(volume);
    	
    	
//    	String curr="\"EUR\"";
//    	
//    String s="<GrandTotalAmount currencyID="+curr+">5574.67</GrandTotalAmount>";
//    System.out.println(s);
//    
//    
//    String SCCs="GEN PER SPX ECC";
//	
//	String expectedSCCs[]={"ECC","GEN","SPX","PER"};
//	
//	String actualSCCs[]=SCCs.split(" ");
//	
//	
//	    
//	if(Arrays.asList(expectedSCCs).containsAll(Arrays.asList(actualSCCs)))
//	{
//		System.out.println("true");
//	}
//	else
//	{
//		System.out.println("false");
//	}
//	
//    	
//    	
//    }
    	
   	float f= 1.4f;
   	int i=( int)f;
//    	
//    	System.out.println(i);
//    	
    	
//    	int i=2;
//    	float f=i;
//    	
//    	System.out.println(f);
    	
    	
//    	int []i={1,2,5,6,5};
//    	
//    	HashSet<Integer> s=new HashSet<Integer>();
//    	
//    	for(int j=0;j<i.length;j++)
//    	{
//    		s.add(i[j]);
//    	}
//    System.out.println(s);
    	
//    	HashMap<String,String> hp=new HashMap<String,String>();
//    	hp.put("name", "Parvathy");
//    	hp.put("Roll no", "SCC");
//    	hp.put("emp id", "AAA");
//    	
//    	
//    	TreeMap<String,String> tm=new  TreeMap<String,String> (hp); 
//    	Iterator itr=tm.keySet().iterator();         
//    	while(itr.hasNext())    
//    	{    
//    	String key=(String) itr.next();  
//    	System.out.println(key + "     "+hp.get(key));  
//    	}    
//    	}  
//    	
   

//        // read text file to HashMap
//        Map<String, String> mapFromFile
//            = HashMapFromTextFile();
//  
//          System.out.println(mapFromFile);
//   }
//    
//    public static Map<String, String> HashMapFromTextFile()
//    {
//  
//        Map<String, String> map
//            = new HashMap<String, String>();
//        BufferedReader br = null;
//  
//        try {
//  
//            // create file object
//            File file = new File("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\src\\resources\\TestData\\DataLoad\\xfwb\\xfwb\\File_1.txt");
//  
//            // create BufferedReader object from the File
//            br = new BufferedReader(new FileReader(file));
//  
//            String line = null;
//            String newLine=null;
//  
//            // read file line by line
//            while ((line = br.readLine()) != null) {
//            	newLine=newLine+line;
//              
//                    map.put("textContents", newLine);
//            }
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        finally {
//  
//            // Always close the BufferedReader
//            if (br != null) {
//                try {
//                    br.close();
//                }
//                catch (Exception e) {
//                };
//            }
//        }
//  
//        return map;
//    }
//
//    /**
//     * 
//     * @param url
//     * @param timeout
//     * @return
//     * @throws MalformedURLException
//     * @throws IOException
//     */
//	public static boolean pingURL(String url, int timeout) throws MalformedURLException, IOException {
//	    url = url.replaceFirst("^https", "http"); // Otherwise an exception may be thrown on invalid SSL certificates.
//
//	   try {
//	        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
//	        connection.setConnectTimeout(timeout);
//	        connection.setReadTimeout(timeout);
//	        connection.setRequestMethod("HEAD");
//	        int responseCode = connection.getResponseCode();
//	        System.out.println("responseCode "+responseCode);
//	        return (200 <= responseCode && responseCode <= 399);
//	    } catch (IOException exception) {
//	        return false;
//	    }
    	
    	
    	
    	
    	
    }
}
    
    
   

