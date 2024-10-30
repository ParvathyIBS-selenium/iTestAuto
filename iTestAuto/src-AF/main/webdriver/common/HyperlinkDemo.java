package common;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
 
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;


public class HyperlinkDemo extends JFrame {

static String s2 = System.getProperty("user.dir");

	//MODULE
	    private String text = "Select Module";
	    private JLabel hyperlink = new JLabel(text);
	    
	    //SUITE
	    private String text2 = "Select Suite";
	    private JLabel hyperlink2 = new JLabel(text2);
	    
	    //Test case
	    private String text3 = "Select Testcase";
	    private JLabel hyperlink3 = new JLabel(text3);
	    
	    public HyperlinkDemo() throws HeadlessException {
	        super();
	        setTitle("Selective Execution");
	 
	        hyperlink.setForeground(Color.BLUE.darker());
	        hyperlink.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        
	        
	        hyperlink2.setForeground(Color.RED.darker());
	        hyperlink2.setCursor(new Cursor(Cursor.HAND_CURSOR));
	        
	        hyperlink3.setForeground(Color.BLACK.darker());
	        hyperlink3.setCursor(new Cursor(Cursor.HAND_CURSOR));
	 
	        hyperlink.addMouseListener(new MouseAdapter() {
	 
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            File file = new File(s2+"\\src\\resources\\TestCase\\Module.xlsx");  
	                try {
	                Desktop desktop = Desktop.getDesktop(); 
	                desktop.open(file);  
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	            }
	 
	            @Override
	            public void mouseExited(MouseEvent e) {
	                hyperlink.setText(text);
	              
	            }
	 
	            @Override
	            public void mouseEntered(MouseEvent e) {
	                hyperlink.setText("<html><a href=''>" + text + "</a></html>");
	             
	            }
	 
	        });
	        hyperlink2.addMouseListener(new MouseAdapter() {
	         
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            File file = new File(s2+"\\src\\resources\\TestCase\\Suite.xlsx");  
	                try {
	                Desktop desktop = Desktop.getDesktop(); 
	                desktop.open(file);  
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	            }
	 
	            @Override
	            public void mouseExited(MouseEvent e) {
	                
	                hyperlink2.setText(text2);
	            }
	 
	            @Override
	            public void mouseEntered(MouseEvent e) {
	               
	                hyperlink2.setText("<html><a href=''>" + text2 + "</a></html>");
	            }
	 
	        });
	        
	        hyperlink3.addMouseListener(new MouseAdapter() {
	         
	            @Override
	            public void mouseClicked(MouseEvent e) {
	            File file = new File(s2+"\\src\\resources\\TestCase\\TestCase.xlsx");  
	                try {
	                Desktop desktop = Desktop.getDesktop(); 
	                desktop.open(file);  
	                } catch (IOException e1) {
	                    e1.printStackTrace();
	                }
	            }
	 
	            @Override
	            public void mouseExited(MouseEvent e) {
	                
	                hyperlink3.setText(text3);
	            }
	 
	            @Override
	            public void mouseEntered(MouseEvent e) {
	               
	                hyperlink3.setText("<html><a href=''>" + text3 + "</a></html>");
	            }
	 
	        });
	 
	        setLayout(new FlowLayout());
	        getContentPane().add(hyperlink);
	        getContentPane().add(hyperlink2);
	        getContentPane().add(hyperlink3);
	 
	        setSize(500, 200);
	        setLocationRelativeTo(null);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    }
	 
	 
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	 
	            @Override
	            public void run() {
	                new HyperlinkDemo().setVisible(true);;
	            }
	        });;
	    }
	}



