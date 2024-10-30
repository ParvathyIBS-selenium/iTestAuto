package common;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class InermediateReport {

	/**
	 * @param args
	 * @throws IOException 
	 * 
	 */
	//<tr>
	//<th width="10%">TC2</th>
	//<th width="10%">Pass</th>
	//</tr>
	 public static List<String> tcName=new ArrayList<String>();
	 public static List<String> tcStatus=new ArrayList<String>();
	public static void main(String[] args) throws IOException {
		
		tcName.add("TC1");
		tcStatus.add("Pass");
		tcName.add("TC2");
		tcStatus.add("Pass");
		tcName.add("TC3");
		tcStatus.add("Pass");
		int i=1;
		String wid="10%";
		 BufferedReader br1 = new BufferedReader(new FileReader("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\reports\\intermediateText\\ExtentReport_10_Jan_2021__17_56_42.txt"));
		// PrintWriter pw = new PrintWriter("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\reports\\intermediateText\\ExtentReport_10_Jan_2021__17_56_42.txt"); 
		 
		 String line1 = br1.readLine(); 
		 System.out.println(line1);
		 String newLine="";
		 
		 while (line1 != null ) 
     	{
				if(i==12)
				{
					for(int j=0;j<tcStatus.size();j++)
					{
					newLine=newLine+"<tr>"+"\n"+"<th width="+wid+">"+tcName.get(j)+"</th>"+"\n"+"<th width="+wid+">"+tcStatus.get(j)+"</th>"+"\n"+"</tr>";
				      
					}
					
					line1=newLine.replaceAll("\n", System.lineSeparator());
				      System.out.println(line1);
					break;
				}
		 
				else
				{
					
					line1=br1.readLine(); 
        			i=i+1;
				}

	}
		 System.out.println(line1);
		 FileWriter writer = new FileWriter("D:\\SEL_WS_SVN\\workspace\\iTestFinal_4.10\\reports\\intermediateText\\ExtentReport_10_Jan_2021__17_56_42.txt");
		 String fileContent="<html>"+"\n"+"<head>"+"\n"+"<title>Test Report</title>"+
				 "\n"+"<head>"+"\n"+"<body>"+"\n"+"<h3>Test results</h3>"+"\n"+
						 "<table>"+"\n"+"<tr>"+"\n"+"<th width="+wid+">Test Case</th>"+"\n"+
				 "<th width="+wid+">Result</th>"+"\n"+"</tr>"+"\n";
		 
		 writer.write(fileContent.replaceAll("\n", System.lineSeparator())+line1);
			writer.close();
			
}
}
