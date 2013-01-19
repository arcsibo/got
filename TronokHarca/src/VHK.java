import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//VÁGTÁZÓ HALOTTKÉMEK!!!!!!!!

public class VHK extends JLabel{
	
	public String tipus;
	public int x;
	public int y;
	private Image kep;
	
	public VHK(String tipus, int x, int y, Image kep)
	{
		super( new ImageIcon(kep));
		
		this.tipus = tipus;
		
		this.setBounds(x, y, kep.getWidth(null), kep.getHeight(null));
	}

}
