import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExtentReports2 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	
	
	public static void mergeReport(String reportName,String cases,String order,BufferedReader br1,PrintWriter pw) throws IOException
	{
	
        String line1 = br1.readLine(); 
        boolean entered=false;
        int noOfTestcases=Integer.parseInt(cases);
        int i=1;
        if(order.equals("first"))
        {
        	
        
        	while (line1 != null ) 


        	{
        	
        		if(line1 .equals("<ul class='collapsible node-list' data-collapsible='accordion'>"))
        		{ 
        			if(i==noOfTestcases)
        			{
        				break;
        			}

        			else if(i!=noOfTestcases)
        			{

        				i=i+1;
        				pw.println(line1); 
        				line1 = br1.readLine(); 
        			}


        		} 



        		else
        		{

        			pw.println(line1); 
        			line1 = br1.readLine(); 
        		}

        	}

        }
        
        else if(order.equals("last"))
        {
        
        	while (line1 != null ) 
         	   
            { 
        		
                if(line1.startsWith("<li class='collection-item test displayed")) 
                { 
                	 
                	
                	 entered=true;
                	 pw.println(line1); 
                	 line1 = br1.readLine(); 
                } 
                
                else if(entered==true)
                {
                	 pw.println(line1); 
                     line1 = br1.readLine(); 
                }
                else
                {
                	 line1 = br1.readLine(); 
                }
              
            }
        }
        
        else
        {
        	
        	while (line1 != null ) 
          	   
            { 
        		
             
        		
        		
        		if(line1.startsWith("<li class='collection-item test displayed")) 
        		{ 


        			entered=true;
        			pw.println(line1); 
        			line1 = br1.readLine(); 
        		} 

        		else if(entered==true)
        		{
        			if(line1 .equals("<ul class='collapsible node-list' data-collapsible='accordion'>"))
        			{ 
        				if(i==noOfTestcases)
        				{
        					break;
        				}

        				else 
        				{

        					i=i+1;
        					pw.println(line1); 
        					line1 = br1.readLine(); 
        				}


        			} 
        			else
        			{
        				pw.println(line1); 
        				line1 = br1.readLine(); 
        			}

        		}
        		else


        		{
        			line1 = br1.readLine(); 
        		}



            }
        }
        
        br1.close(); 
        
       
	}
	public static void main(String[] args) throws IOException {
		
		  String folderName="D:\\Reports\\";  // Give the path , where extent reports to be merged are stored
		RenameFiles.renameFile("D:\\Reports");
		 // PrintWriter object for file3.txt 
        PrintWriter pw = new PrintWriter(folderName+"mergedReport.txt"); 
      
     

		InputStream inp= new FileInputStream(folderName+"Testcases.xlsx");
		XSSFWorkbook wb=new XSSFWorkbook(inp);
		
		XSSFSheet sheet = wb.getSheet("Sheet1");
		
		XSSFRow row = null;
		XSSFCell reportName = null;
		XSSFCell cases = null;
		XSSFCell order = null;
		       // Making the object of excel row
				row = sheet.getRow(0);
		 
				
				
				int rowCount = sheet.getLastRowNum() ;
				System.out.println("Row Count :- " + rowCount);
				
				//Rename File
				
				
				for(int i=0;i<rowCount;i++)
				{
					 row = sheet.getRow(i+1);
					
					 //Report name
					  reportName = row.getCell(0);
					  
					 
					  
					  //Cases
					  cases = row.getCell(1);
					  System.out.println(cases);
					
					  //Order
					  order = row.getCell(2);
					
				      // BufferedReader object for file1.txt 
				      BufferedReader br1 = new BufferedReader(new FileReader(folderName+reportName+".txt"));
				      mergeReport(reportName.toString(),cases.toString(),order.toString(),br1,pw);
				}
				
				
				  pw.flush(); 
	        	  pw.close(); 
			     
		
	}

}
