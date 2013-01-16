import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hazjelzo extends JLabel{
	
	private Image kep;
	private Haz tulajdonos;
	
	public Hazjelzo(Image kep,Haz haz)
	{
		super(new ImageIcon(kep));
		this.kep = kep;
		this.tulajdonos = haz;
	}
	
	public Haz getHaz()
	{
		return this.tulajdonos;
	}
	
	public String toString()
	{
		String s;
		s = "Hazjelzo: " + tulajdonos.getNev();
		return s;
	}
	
}
