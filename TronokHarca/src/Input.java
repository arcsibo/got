import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.text.*;


public class Input extends JFrame {
    
	
	static JFormattedTextField tf;
	static String title = Tabla.aktHaz.getNev()+ " licitál:";
	static int count;
	public Input jomagam = this;
	
    public Input() {
    	
    	setTitle(title);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Licit :");
        tf = new JFormattedTextField();
        
        tf.setColumns(10);
        panel.add(label);
        panel.add(tf);
        JButton button = new JButton();
        button.setLabel("OK");
        button.addActionListener(buttonAL);
        panel.add(button);
        getContentPane().add(panel, BorderLayout.SOUTH);
        pack();
    }
    
    ActionListener buttonAL = new ActionListener() {
    	public void actionPerformed(ActionEvent arg0) {
    		if (getNumber() == -1) return;
    		System.out.println(getNumber());
    		title(Tabla.aktHaz.getNev()+" licitál:");
    		Tabla.aktHaz.setLicit(getNumber());
    		tf.setText(null);
    		
    		System.out.println(Tabla.countKiskor+".kor");
    		if(Tabla.countKiskor>=5)
    		{
    			switch(count)
    			{
    			case 0:
    				Kartya.licitVastron();
    				Tabla.got.updateJatekPanel();
    				break;
    			case 1:
    				Kartya.licitKard();
    				Tabla.got.updateJatekPanel();
    			case 2:
    				Kartya.licitHollo();
    			}
    			count++;
    		}
    		
    		
    		Tabla.kovHaz();
    		Tabla.got.updateHaz();
    		
    		if(count >= 3)
    		{
    			Tabla.got.updateJatekPanel();
    			Tabla.licitalas = false;
    			count = 0;
    			Tabla.TERVEZES = true;
    			Tabla.portyazas = true;
    			jomagam.dispose();
    		}
    	}
    };
    
    public static int getNumber()
    {
    	try {return Integer.parseInt(tf.getText());}
    	catch (NumberFormatException e)
    	{
    		//System.out.println("Hibás szám!");
    		return -1;
    	}
    }
    
    public void title(String s)
    {
    	setTitle(s);
    }
    

}
