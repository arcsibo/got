import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//VÁGTÁZÓ HALOTTKÉMEK!!!!!!!!

public class VHK extends JLabel{
	
	public double x=0;
	public double y=0;
	public Image kep;
	
	public VHK(double x, double y, Image kep)
	{
		super( new ImageIcon(kep));
		
		this.x = x;
		this.y = y;
		this.kep = kep;
		
	}

}
