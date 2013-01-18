import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hazjelzo extends JLabel{
	
	private Image lekep;
	private Haz tulajdonos;
	
	public Hazjelzo(Image kep,Haz haz)
	{
		super(new ImageIcon(kep));
		this.lekep = kep;
		this.tulajdonos = haz;
	}
	
	public Haz getHaz()
	{
		return this.tulajdonos;
	}
	
	public Image getLekep(){
		return this.lekep;
	}
	
	public String toString()
	{
		String s;
		s = "Hazjelzo: " + tulajdonos.getNev();
		return s;
	}
	
}
