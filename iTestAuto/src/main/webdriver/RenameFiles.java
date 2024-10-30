import java.io.File;


public class RenameFiles {

	/**
	 * @param args
	 */
	
	
	public static void renameFile(String filePath)
	{
		
		// change file names in 'Directory':
        String absolutePath = filePath;
        File dir = new File(absolutePath);
        File[] filesInDir = dir.listFiles();
        int i = 0;
    
        for(File file:filesInDir) {
        	
        	if(!file.getName().contains("Testcases.xlsx"))
        	{
        		
        		if(!file.getName().contains("mergedReport.txt"))
            	{		
            	
            i++;
            String name = file.getName();
            String newName = "ExtentReport_" + i + ".txt";
            String newPath = absolutePath + "\\" + newName;
            file.renameTo(new File(newPath));
            System.out.println(name + " changed to " + newName);
            	}
            	
        	}
        }
	}
	
}