package common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

public class AfterSuite {
	 static String s2 = System.getProperty("user.dir");
	 public static ArrayList<String> failedCases=new ArrayList<String>();
	

	/**
	 * @param args
	 */
	
	 public static void renameFile()
	 {
			File file = new File(s2+"\\reports\\html\\Reports\\ExtentReport_02_Dec_2020__18_23_54.html");
		 
	        file.renameTo(new File(s2+"\\reports\\html\\Reports\\ExtentReport_02_Dec_2020__18_23_54.txt"));
	 }
	public static void copyReport()
	{
		File source = new File(s2+"\\reports\\html\\ExtentReport_02_Dec_2020__18_23_54.html");
		File dest = new File(s2+"\\reports\\html\\Reports\\ExtentReport_02_Dec_2020__18_23_54.html");
		try {
		    FileUtils.copyFile(source, dest);
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void storeFailedCases() throws IOException
	{
		 BufferedReader br1 = new BufferedReader(new FileReader(s2+"\\reports\\html\\Reports\\ExtentReport_02_Dec_2020__18_23_54.txt"));
		  String line= br1.readLine(); 
		  int i=1;
		  int lineNum=0;
		  String testCase="";
		  
		  while (line != null ) 
      	{
			  if(line.contains("<span class='test-name'>"))
      		{ 
				 lineNum=i;
				 testCase=line.split(">")[1].split("</")[0];
      		}
			  
			  else if(i==(lineNum+1))
	           {
				  if(line.contains("fail"))
				  {
					  failedCases.add(testCase);
					  
				  }
			   }
			  
			 
		 line = br1.readLine(); 
			  
			 i=i+1; 
      	}
		  
		  br1.close();   
	}
	public static void main(String[] args) throws IOException {
		/*// TODO Auto-generated method stub
		copyReport();
		renameFile();
		storeFailedCases();
		
		System.out.println(failedCases.size());
		
		for(int i=0;i<failedCases.size();i++)
		{
			System.out.println(failedCases.get(i));
		}*/
		
	
	        int[] numbers = new int[5];
	        numbers[2]=50;
	        System.out.println(Arrays.toString(numbers));
	    
	}

}
