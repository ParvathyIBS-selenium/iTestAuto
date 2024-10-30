package common;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.commons.io.FileUtils;

public class HistogramPanel extends JPanel
{
    private int histogramHeight = 200;
    private int barWidth = 50;
    private int barGap = 10;

    private JPanel barPanel;
    private JPanel labelPanel;

    private List<Bar> bars = new ArrayList<Bar>();
    
    public static List<String> tcName=new ArrayList<String>();
    public static List<String> tcStatus=new ArrayList<String>();
    
    public static String projPath=System.getProperty("user.dir");
   
    public HistogramPanel()
    {
        setBorder( new EmptyBorder(10, 10, 10, 10) );
        setLayout( new BorderLayout() );

        barPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
        Border inner = new EmptyBorder(10, 10, 0, 10);
        Border compound = new CompoundBorder(outer, inner);
        barPanel.setBorder( compound );

        labelPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
        labelPanel.setBorder( new EmptyBorder(5, 10, 0, 10) );

        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }

    public void addHistogramColumn(String label, int value, Color color)
    {
        Bar bar = new Bar(label, value, color);
        bars.add( bar );
    }
  
    public static File changeExtension(File file, String extension) {
        String filename = file.getName();
        
        System.out.println(filename);

        if (filename.contains(".")) {
            filename = filename.substring(0, filename.lastIndexOf('.'));
        }
        filename += "." + extension;

        file.renameTo(new File(file.getParentFile(), filename));
        return new File(file.getParentFile(), filename);
    }
    public void layoutHistogram()
    {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 0;

        for (Bar bar: bars)
            maxValue = Math.max(maxValue, bar.getValue());

        for (Bar bar: bars)
        {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setVerticalAlignment(JLabel.BOTTOM);
            int barHeight = (bar.getValue() * histogramHeight) / maxValue;
            Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight);
            label.setIcon( icon );
            barPanel.add( label );

            JLabel barLabel = new JLabel( bar.getLabel() );
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add( barLabel );
        }
    }

    private class Bar
    {
        private String label;
        private int value;
        private Color color;

        public Bar(String label, int value, Color color)
        {
            this.label = label;
            this.value = value;
            this.color = color;
        }

        public String getLabel()
        {
            return label;
        }

        public int getValue()
        {
            return value;
        }

        public Color getColor()
        {
            return color;
        }
    }

    private class ColorIcon implements Icon
    {
        private int shadow = 3;

        private Color color;
        private int width;
        private int height;

        public ColorIcon(Color color, int width, int height)
        {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconWidth()
        {
            return width;
        }

        public int getIconHeight()
        {
            return height;
        }

        public void paintIcon(Component c, Graphics g, int x, int y)
        {
            g.setColor(color);
            g.fillRect(x, y, width - shadow, height);
            g.setColor(Color.GRAY);
            g.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
        }
    }

    private static void createAndShowGUI(List<String> tc,List<String> status)
    {
        HistogramPanel panel = new HistogramPanel();

        for(int i=0;i<tc.size();i++)
        {
        	if(status.get(i).equals("pass"))
        	{
        		panel.addHistogramColumn(tc.get(i), 100, Color.GREEN);
        	}
        	else
        	{
        		panel.addHistogramColumn(tc.get(i), 100, Color.RED);
        	}

        }
       
        panel.layoutHistogram();

        JFrame frame = new JFrame("Execution Progress");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add( panel );
        frame.setLocationByPlatform( true );
        frame.pack();
        frame.setVisible( true );
    }
    public static void copyFileUsingApache(File from, File to) throws IOException{ 
    	
    	FileUtils.copyFile(from, to); 
    	
    }

   
public static void getExecutionResults(File file) throws IOException
{
	BufferedReader br1 = new BufferedReader(new FileReader(file));
	
	String line1 = br1.readLine(); 
	String tc="";String status="";
	
	while (line1 != null ) 
        
    { 
		
        //TC NAME
        if(line1.startsWith("<span class='test-name'>")) 
        { 
        	
        	tc=line1.split("<span class='test-name'>")[1].split("</span>")[0].trim();
        	tcName.add(tc);
        	line1 = br1.readLine(); 
        }
        
        //STATUS
        else if(line1.startsWith("<span class='test-status")) 
        { 
        	
           // <span class='test-status label right outline capitalize pass'>pass</span>

        	status=line1.split("'>")[1].split("</span>")[0].trim();
        	tcStatus.add(status);
        	line1 = br1.readLine(); 
        }
        
        else
        {
        	line1 = br1.readLine(); 
        }
	
}
	br1.close(); 
}

public static String getPropertyValue(String s3, String Key) {
	Properties prop = new Properties();
	String s2 = System.getProperty("user.dir");
	String path = s2 + s3;
	try {
		prop.load(new FileInputStream(path));
	} catch (Exception e) {

	}
	String value = prop.getProperty(Key);
	return value;
}
    public static void main(String[] args) throws IOException
    {
    	
    	String fileName=getPropertyValue("\\src\\resources\\GlobalVariable.properties","extent_report_name");
    
    File fromFile=new File(projPath+"\\reports\\html\\"+fileName);
    File toFile=new File(projPath+"extentreporttext\\"+fileName);
    
    
    copyFileUsingApache(fromFile, toFile);
    
   //Change File extension
    File file=changeExtension(toFile,"txt");
    getExecutionResults(file);
    
   
    
    
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
               createAndShowGUI(tcName,tcStatus);
            }
        });
    }
}

