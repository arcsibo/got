import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.text.*;


public class Input extends JFrame {
    
	
	static JFormattedTextField tf;
	static String title = Tabla.aktHaz.getNev()+ " licitál:";
	
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
    		
    		System.out.println(getNumber());
    		Tabla.kovHaz();
    		Tabla.got.updateHaz();
    		
    		title(Tabla.aktHaz.getNev()+" licitál:");
    		Tabla.aktHaz.setLicit(getNumber());
    		tf.setText(null);
    		
    		
    	}
    };
    
    public static int getNumber()
    {
    	return Integer.parseInt(tf.getText());
    }
    
    public void title(String s)
    {
    	setTitle(s);
    }
    

}
