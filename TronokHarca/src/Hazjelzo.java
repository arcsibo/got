import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Hazjelzo extends JLabel{
	
	private Image lekep;
	public Image kep;
	private Haz tulajdonos;
	
	private Hazjelzo jomagam = this;
	
	public boolean katt;
	
	public boolean tablanVan = false;
	
	public Hazjelzo(Image kep,Haz haz)
	{
		super(new ImageIcon(kep));
		this.lekep = kep;
		this.tulajdonos = haz;
		
		this.addMouseListener(mL);
	}
	
	public Haz getHaz()
	{
		return this.tulajdonos;
	}
	
	public Image getKep()
	{
		return this.lekep;
	}
	
	public String toString()
	{
		String s;
		s = "Hazjelzo: " + tulajdonos.getNev();
		return s;
	}
	
	MouseListener mL = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
        if (!change.checkClick(arg0.getX(), arg0.getY(), jomagam)) return;
			
			jomagam.performClick(arg0.getX(),arg0.getY());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	void performClick(int x, int y)
	{
		if (tablanVan) return;
		
		if (!Tabla.hazjelzoLerakas)
		{
			Tabla.got.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(jomagam.getKep(), new Point(x,y), ""));
			Tabla.hazjelzoLerakas = true;
			Tabla.hazJelzoAmitLeraksz = jomagam;
		}
		
		//Visszarakjuk
		else
		{
			Tabla.got.setCursor(Tabla.defCursor);
			Tabla.hazjelzoLerakas = false;
			Tabla.hazJelzoAmitLeraksz = null;
		}
	}
	
}
